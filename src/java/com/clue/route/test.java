    // public void runClient() {
    //   logger.debug("starting up zmq client.");
    //   context = ZMQ.context(1);
    
    //   //  Socket to talk to server
    //   logger.debug("creating socket.");
    //   Socket requester = context.socket(ZMQ.DEALER);
    
    //   String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
    //   requester.setIdentity(identity.getBytes(ZMQ.CHARSET));
    
    //   logger.debug("connecting to server.");
    //   requester.connect("tcp://localhost:5559");
        
    //   logger.debug("launch and connect client.");

    //   for (int request_nbr = 0; request_nbr < 10; request_nbr++) {
    //     requester.sendMore(""); // message envelope marker
    //     requester.send("Hello", 0);
    //     String reply = requester.recvStr(0);
    //     logger.debug("Received reply " + request_nbr + " [" + reply + "]");
    //   }
      
    //   logger.debug("shutting down zmq client.");
    //   requester.close();
    //   context.term();
    //   context = null;
    //   logger.debug("finished zmq client");
    // }

    // public void runServer() {
    //   logger.debug("starting up zmq server");
    
    //   //  Prepare our context and sockets
    //   logger.debug("creating context");
    //   context = ZMQ.context(1);
      
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
    //     int num_polled = items.poll(100);

    //     if (items.pollin(socket_id)) {
    //       is_identity_part = true;
    //       while (true) {
    //         // receive message
    //         message = backend.recvStr();
    //         more = backend.hasReceiveMore();
            
    //         logger.debug("message hash: " +
    //                      Arrays.hashCode(message.getBytes()));
            
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
    //                        Arrays.hashCode(identity_message.getBytes()));
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
    //     }
    //   }
    
    //   // We never get here but clean up anyhow
    
    //   logger.debug("shuttig down zmq server");
    //   backend.close();
    //   context.term();
    //   context = null;
    //   logger.debug("finishedzmq server");
    // }
