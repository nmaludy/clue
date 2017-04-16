package com.clue.route;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZMQ.Socket;

import com.clue.app.Logger;
import com.clue.app.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.clue.proto.Msg.Header;

public class Transport implements Runnable {

  private static Logger logger = new Logger(Transport.class);
  private static Config config = Config.getInstance();
  
  private static Random rand = new Random(System.currentTimeMillis());

  public enum Type {
    CLIENT,
    SERVER
  }

  public final Type type;
  public Worker worker = null;

  private Transport() {
    this.type = Type.CLIENT;
  }

  public Transport(Type type, MessageHandler msgHandler) {
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

    public Worker(MessageHandler msgHandler) {
      super("Transport.Worker");
      int num_io_threads = 1;
      this.context = ZMQ.context(num_io_threads);
      this.notifier = new Notifier(context, msgHandler);
      this.outboundQueue = new ConcurrentLinkedQueue<TransportMessage>();
      this.serializer = new Serializer();
    }
        
    public void run() {
      logger.debug("starting up zmq server");
    
      //  Prepare our context and sockets
      logger.debug("creating context");
      logger.debug("creating socket");
      
      String port = config.getProperty("server.port", String.class);
      String protocol = config.getProperty("server.protocol", String.class);
      String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
      logger.debug("identity = " + identity);
      
      Socket socket = null;
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

      //  Initialize poll set
      Poller items = new Poller(2);
      int socket_id = items.register(socket, Poller.POLLIN);
      int notifier_id = items.register(notifier.rcvrSocket(), Poller.POLLIN);

      while (!Thread.currentThread().isInterrupted()) {
        // poll and memorize multipart detection
        int num_polled = items.poll(100);

        // Got message to send
        if (items.pollin(notifier_id)) {
          TransportMessage msg = outboundQueue.poll();
          while (msg != null) {
            // remove the notification from the notifier queue to ack
            // that we've received it
            notifier.clearRecv();

            // send the message
            socket.sendMore(""); // message envelope marker
            socket.sendMore(serializer.convertToBinary(msg.getHeader()));
            socket.send(msg.getData(), 0);

            // try pulling another message from the queue
            // this will return null if the queue is empty
            msg = outboundQueue.poll();
          } 
        }

        // Got data from socket
        if (items.pollin(socket_id)) {
          ArrayList<String> message_parts = new ArrayList<String>();
          
          // receive message (all parts)
          do {
            message_parts.add(socket.recvStr());
          } while (socket.hasReceiveMore());

          logger.debug("received number of parts: " + Integer.toString(message_parts.size()));

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
          try {
            if (type == Type.CLIENT && message_parts.size() == 3) {
              String envelope = message_parts.get(0);
              String msg_hdr  = message_parts.get(1);
              String msg_data = message_parts.get(2);
              Header header = serializer.convertToHeader(msg_hdr);
              logger.debug("header   =  " + header.toString());

              notifier.getCallback().receiveMessage(
                  new TransportMessage(header, msg_data.getBytes()));
            
            } else if (type == Type.SERVER && message_parts.size() == 4) {
              String sndr_identity = message_parts.get(0);
              String envelope = message_parts.get(1);
              String msg_hdr  = message_parts.get(2);
              String msg_data = message_parts.get(3);
              Header header = serializer.convertToHeader(msg_hdr);
              logger.debug("identity =  " + sndr_identity);
              logger.debug("header   =  " + header.toString());

              notifier.getCallback().receiveMessage(
                  new TransportMessage(header, msg_data.getBytes()));

              // Just as a test, send this back to the client
              socket.sendMore(sndr_identity);
              socket.sendMore("");
              socket.sendMore(msg_hdr);
              socket.send(msg_data);

              // @todo deserializer
            } else {
              logger.error("Received a message with < 4 parts! (ignorning)");
            }
          } catch (com.google.protobuf.InvalidProtocolBufferException ex) {
            logger.debug("received invalid message header");
          }
        }
      }
    
      // We never get here but clean up anyhow
    
      logger.debug("shuttig down zmq server");
      socket.close();
      context.term();
      context = null;
      logger.debug("finishedzmq server");
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
      System.out.println("W: interrupt received, killing server...");
      try {
        worker.interrupt();
        worker.kill();
        worker.join();
      } catch (InterruptedException e) {
      }
    }
  }
} // class Transport

