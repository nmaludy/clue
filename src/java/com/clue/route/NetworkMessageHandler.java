package com.clue.route;

import com.clue.app.Config;
import com.clue.app.Logger;

public class NetworkMessageHandler implements MessageHandler, TransportMessageHandler {
  
  private static Logger logger = new Logger(NetworkMessageHandler.class);
  private static Config config = Config.getInstance();
  
  private Serializer serializer;
  private Transport transport;
  private Router router;
  
  public NetworkMessageHandler() {
    String instance_type = config.getProperty("instance.type", String.class);
    Transport.Type type = Transport.Type.CLIENT;
    if (instance_type.toLowerCase().equals("client")) {
      type = Transport.Type.CLIENT;
    } else if (instance_type.toLowerCase().equals("server")) {
      type = Transport.Type.SERVER;
    }
    this.router = Router.getInstance();
    this.router.register(new SubscriptionAllOutgoing(), this);
    this.serializer = new Serializer();
    this.transport = new Transport(type, this);
    this.transport.run();
  }

  @Override  
  public void handleTransportMessage(TransportMessage tportMsg) {
    logger.debug("receiveMessage");
    try {
      Message msg = serializer.convertToMessage(tportMsg);
      logger.debug("receiveMessage() - " + msg.getMessage().toString());
      router.route(msg);
    } catch (com.google.protobuf.InvalidProtocolBufferException ex) {
      logger.debug("receiveMessage() - received invalid message with header: "
                   + tportMsg.getHeader().toString());
    }
  }

  @Override
  public void handleMessage(Message msg) {
    logger.debug("sendMessage");
    TransportMessage tport_msg = serializer.convertToBinary(msg);
    transport.send(tport_msg);
  }

  public void waitForBackgroundThread() {
    transport.join();
  }

}
