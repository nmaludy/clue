package com.clue.route;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZMQ.Socket;

import com.clue.app.Logger;
import com.clue.app.Instance;
import com.clue.app.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.clue.proto.Msg;

public class Transport implements Runnable {

  private static Logger logger = new Logger(Transport.class);
  private static Config config = Config.getInstance();
  
  private static Random rand = new Random(System.currentTimeMillis());

  public final Instance.Type type;
  public Worker worker = null;

  private Transport() {
    this.type = Instance.Type.CLIENT;
  }

  public Transport(Instance.Type type, TransportMessageHandler msgHandler) {
    this.type = type;
    this.worker = new Worker(msgHandler);
  }

  public void run() {
    worker.start();
    Runtime.getRuntime().addShutdownHook(new ShutdownHook(worker));
  }

  public void join() {
    try {
      worker.join();
    } catch (InterruptedException e) {
      worker.interrupt();
    }
  }

  public void send(TransportMessage message) {
    worker.send(message);
  }

  private class Worker extends Thread {
    Context context = null;
    Notifier notifier = null;
    ConcurrentLinkedQueue<TransportMessage> outboundQueue = null;
    Serializer serializer = null;
    Socket socket = null;

    public Worker(TransportMessageHandler msgHandler) {
      super("Transport.Worker");
      int num_io_threads = 1;
      this.context = ZMQ.context(num_io_threads);
      this.notifier = new Notifier(context, msgHandler);
      this.outboundQueue = new ConcurrentLinkedQueue<TransportMessage>();
      this.serializer = new Serializer();
    }

    public void connect() {
      //  Prepare our context and sockets
      logger.debug("creating context");
      logger.debug("creating socket");
      
      String port = config.getProperty("server.port", String.class);
      String protocol = config.getProperty("server.protocol", String.class);
      String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
      logger.debug("identity = " + identity);
      
      switch (type) {
        case SERVER:
          socket = context.socket(ZMQ.ROUTER);
          // socket.setIdentity(identity.getBytes(ZMQ.CHARSET));
          String bind = config.getProperty("server.bind", String.class);
          String bind_str = protocol + "://" + bind + ":" + port;
          logger.debug("binding to " + bind_str);
          socket.bind(bind_str);
          break;
        case CLIENT:
          socket = context.socket(ZMQ.DEALER);
          socket.setIdentity(identity.getBytes(ZMQ.CHARSET));
          String host = config.getProperty("server.host", String.class);
          String connect_str = protocol + "://" + host + ":" + port;
          logger.debug("connecting to server.");
          socket.connect(connect_str);
          break;
      }

      logger.debug("launch and connect broker.");
    }

    // Our ZMQ Protocol
    //
    // Client API sends
    //  - NULL
    //  - Header header
    //  - String data
    //
    // Client sends (under the hood)
    //  - Client's Socket Identity
    //  - NULL
    //  - Header header
    //  - String data
    //
    // Server API receives
    //  - Client's socket identity
    //  - NULL
    //  - Header header
    //  - String data
    //
    // Server API sends
    //  - Client's socket identity (target recipient)
    //  - NULL
    //  - Header header
    //  - String data
    //
    // Server under the hood does:
    //  - Find's client socket identity that matches identity and sends:
    //  - NULL
    //  - Header header
    //  - String data
    //
    // Client API receives
    //  - NULL
    //  - Header header
    //  - String data
    public void run() {
      logger.debug("starting up zmq server");

      connect();

      //  Initialize poll set
      Poller poller = new Poller(2);      
      int socket_id = poller.register(socket, Poller.POLLIN);
      int notifier_id = poller.register(notifier.rcvrSocket(), Poller.POLLIN);

      boolean b_term_context = true;

      while (!Thread.currentThread().isInterrupted()) {
        // poll and memorize multipart detection
        try {
          int num_polled = poller.poll(100);
        } catch (Exception ex) {
          logger.info("Exception in poll() - exiting transport worker thread");
          b_term_context = false;
          break;
        }

        // Got message(s) to send
        if (poller.pollin(notifier_id)) {
          TransportMessage msg = outboundQueue.poll();
          while (msg != null) {
            // remove the notification from the notifier queue to ack
            // that we've received it
            notifier.clearRecv();

            // send the message to zmq
            zmqSend(msg);

            // try pulling another message from the queue
            // this will return null if the queue is empty
            msg = outboundQueue.poll();
          }
        }

        // Got data from socket
        if (poller.pollin(socket_id)) {
          // read data from the socket
          TransportMessage tport_msg = zmqRecv();

          // notify our callback to serialize and route
          if (tport_msg != null) {
            notifier.getCallback().handleTransportMessage(tport_msg);
          }
        }
      } // while thread is not interrupted
      // We never get here but clean up anyhow
    
      logger.debug("shuttig down zmq server");
      socket.close();
      if (b_term_context) {
        context.term();
      }
      context = null;
      logger.debug("finishedzmq server");
    }

    private void zmqSend(TransportMessage msg) {
      byte[] header = serializer.convertToBinary(msg.getHeader());
      byte[] data   = msg.getData();
      
      // send the message
      if (type == Instance.Type.SERVER) {
        ArrayList<Integer> clients = new ArrayList<Integer>();

        int client_id_int = msg.getHeader().getDestination();

        // check if this is the broadcast ID
        if (client_id_int == Instance.getBroadcastId()) {
          // if it is, then send this message to every client
          clients.addAll(ClientRegistry.getInstance().allClientIds());
        } else {
          // otherwise just send it to one client specified in
          // the destination
          clients.add(new Integer(client_id_int));
        }
        
        for (Integer client_id : clients) {
          // if we're the server, we need to tell ZMQ which client the
          // message needs to go to.
          String rcvr_identity = ClientRegistry.getInstance()
              .getTransportIdentityInteger(client_id);
          if (rcvr_identity == null) {
            logger.error("Unable to find client identity in the registry: "
                         + client_id.toString());
            logger.error("Dropping message with header: " + msg.getHeader().toString());
            return;
          }
          
          // send the message to this recipient
          zmqSendRaw(rcvr_identity, header, data);
        } // end for each client
      } else {

        // client should not send broadcast messages 
        if (msg.getHeader().getDestination() != Instance.getBroadcastId()) {
          zmqSendRaw(new String(), header, data);
        } else {
          logger.error("Trying to send broadcast message from client");
        }
      }
    } // end zmqSend()

    private void zmqSendRaw(String rcvrIdentity, byte[] header, byte[] data) {
      // send the message
      if (type == Instance.Type.SERVER) {
        socket.sendMore(rcvrIdentity);
      }
      socket.sendMore(""); // message envelope marker
      socket.sendMore(header);
      socket.send(data, 0);
    }

    private TransportMessage zmqRecv() {
      // receive the message (all parts)
      ArrayList<byte[]> message_parts = new ArrayList<byte[]>();
          
      // receive message (all parts)
      do {
        message_parts.add(socket.recv());
      } while (socket.hasReceiveMore());

      logger.debug("received number of parts: " + Integer.toString(message_parts.size()));

      TransportMessage tport_msg = null;
      try {
        if (type == Instance.Type.CLIENT && message_parts.size() == 3) {
          // read message from socket
          byte[] envelope = message_parts.get(0);
          byte[] msg_hdr  = message_parts.get(1);
          byte[] msg_data = message_parts.get(2);

          logger.debug("envelope = " + envelope);
          logger.debug("header = " + msg_hdr);

          // parse header
          Msg.Header header = serializer.convertToHeader(msg_hdr);
          logger.debug("header   =  " + header.toString());

          // create message to send to serializer
          tport_msg = new TransportMessage(header, msg_data);
          
        } else if (type == Instance.Type.SERVER && message_parts.size() == 4) {
          // read message from socket
          byte[] sndr_identity_raw = message_parts.get(0);
          byte[] envelope = message_parts.get(1);
          byte[] msg_hdr  = message_parts.get(2);
          byte[] msg_data = message_parts.get(3);
          
          String sndr_identity = new String(sndr_identity_raw, ZMQ.CHARSET);
          logger.debug("identity =  " + sndr_identity);

          // parse header
          Msg.Header header = serializer.convertToHeader(msg_hdr);
          logger.debug("header   =  " + header.toString());

          // Register this client's identity so we maintain a mapping
          // between the ZMQ "transport identity" and the "client ID"
          ClientRegistry.getInstance().register(sndr_identity, header.getSource());

          // create message to send to serializer
          tport_msg = new TransportMessage(header, msg_data);
        } else {
          logger.error("Received a message with < 4 parts! (ignorning)");
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException ex) {
        logger.debug("received invalid message header:" + ex.getMessage());
      }
      return tport_msg;
    }

    public void send(TransportMessage message) {
      outboundQueue.add(message);
      notifier.sndNotify();
    }

    public void kill() {
      notifier.sndNotify();
    }

    public void printHex(String message) {
      logger.debug("Message: " + message );
      for (int j = 0; j < message.length(); j++) {
        String hex = Integer.toHexString(message.charAt(j)).toUpperCase();
        logger.debug("message[" + Integer.toString(j) + "] = 0x" + hex);
      }
    }
  } // class Worker

  protected class ShutdownHook extends Thread {
    private Worker worker = null;
    
    public ShutdownHook(Worker worker) {
      super("Transport.ShutdownHook");
      this.worker = worker;
    }
    
    @Override
    public void run() {
      logger.info("Interrupt received, killing Transport (Network)...");
      try {
        worker.interrupt();
        worker.kill();
        worker.join();
      } catch (InterruptedException e) {
      }
    }
  }
} // class Transport

