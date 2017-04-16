package com.clue.app;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZMQ.Socket;

// import java.util.Arrays;

import com.clue.route.NetworkMessageHandler;

/**
 * Simple request-reply broker
 *
 */
public class Server implements Runnable {

  private static Logger logger = new Logger(Server.class);

  public void run() {
    logger.debug("running server.");
    NetworkMessageHandler net = new NetworkMessageHandler();
    net.join();
    logger.debug("finished server.");
  }
  
  // public void run() {
  //   logger.debug("running server");
  //   //  Prepare our context and sockets
    
  //   logger.debug("creating context");
  //   Context context = ZMQ.context(1);
    
  //   logger.debug("creating socket");
  //   Socket backend  = context.socket(ZMQ.ROUTER);
    
  //   logger.debug("binding to tcp://*:5559");
  //   backend.bind("tcp://*:5559");

  //   logger.debug("launch and connect broker.");

  //   //  Initialize poll set
  //   Poller items = new Poller(1);
  //   int socket_id = items.register(backend, Poller.POLLIN);

  //   boolean is_identity_part = true;
  //   String identity_message = new String();
    
  //   boolean more = false;
  //   String message;

  //   //  Switch messages between sockets
  //   while (!Thread.currentThread().isInterrupted()) {            
  //     //  poll and memorize multipart detection
  //     logger.debug("sleeping and waiting for data");
  //     int num_polled = items.poll();
  //     logger.debug("Number polled items = " + Integer.toString(num_polled));

  //     if (items.pollin(socket_id)) {
  //       is_identity_part = true;
  //       while (true) {
  //         // receive message
  //         message = backend.recvStr();
  //         more = backend.hasReceiveMore();
            
  //         logger.debug("message hash: " +
  //                           Arrays.hashCode(message.getBytes()));
            
  //         logger.debug("Message: " + message );
  //         for (int j=0; j<message.length(); j++) {
  //           String hex = Integer.toHexString(message.charAt(j));
  //           logger.debug("Hex value is " + hex);
  //         }
  //         logger.debug("");

  //         if (is_identity_part) {
  //           is_identity_part = false;
  //           // ignore the first byte in the identity part...
  //           // it's the "envelope" marker
  //           identity_message = message;
            
  //           logger.debug("identity size = " + Integer.toString(identity_message.length()));
  //           logger.debug("got identity: " + identity_message );
  //           logger.debug("identity hash: " +
  //                             Arrays.hashCode(identity_message.getBytes()));
  //         }
          
  //         // Broker it
  //         if (more) {
  //           logger.debug("Multi part message");
  //         } else {
            
  //           logger.debug("Sending ");
  //           backend.sendMore(identity_message);
  //           backend.send("Hellloooo");
  //           break;
  //         }
  //       }
  //     } else {
  //       logger.debug("our socket didn't have any data to read");
  //     }
  //   }
    
  //   //  We never get here but clean up anyhow
  //   backend.close();
  //   context.term();
  // }
}
