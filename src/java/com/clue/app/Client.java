package com.clue.app;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.clue.app.Logger;
import com.clue.route.Message;
import com.clue.route.NetworkMessageHandler;

import com.clue.proto.Msg.*;

/**
* Hello World client
* Connects REQ socket to tcp://localhost:5559
* Sends "Hello" to server, expects "World" back
*/
public class Client implements Runnable {

  private static Logger logger = new Logger(Client.class);

  public void run() {
    logger.debug("running client.");
    NetworkMessageHandler net = new NetworkMessageHandler();
    Header hdr = Header.newBuilder()
        .setMsgType(ConnectRequest.getDescriptor().getFullName())
        .setSource(1)
        .setDestination(0)
        .build();
    ConnectRequest req = ConnectRequest.newBuilder()
        .setHeader(hdr)
        .setName("test")
        .build();
    net.sendMessage(new Message(hdr, req));
    net.join();
    
    logger.debug("finished client.");
  }

  // private static Random rand = new Random(System.currentTimeMillis());  
  // public void run() {
  //   logger.debug("running client.");
  //   logger.debug("startign up zmq.");
  //   Context context = ZMQ.context(1);
    
  //   //  Socket to talk to server
  //   logger.debug("creating socket.");
  //   Socket requester = context.socket(ZMQ.DEALER);
    
  //   String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
  //   requester.setIdentity(identity.getBytes (ZMQ.CHARSET));
    
  //   logger.debug("connecting to server.");
  //   requester.connect("tcp://localhost:5559");
        
  //   logger.debug("launch and connect client.");

      
  //   for (int request_nbr = 0; request_nbr < 10; request_nbr++) {
  //     requester.sendMore(""); // message envelope marker
  //     requester.send("Hello", 0);
  //     String reply = requester.recvStr(0);
  //     logger.debug("Received reply " + request_nbr + " [" + reply + "]");
  //   }
        
  //   requester.close();
  //   context.term();
  // }
}
