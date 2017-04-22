package com.clue.server;

import com.clue.app.Instance;
import com.clue.app.Logger;
import com.clue.app.Config;

import com.clue.route.Message;
import com.clue.route.MessageHandler;
import com.clue.route.Router;
import com.clue.route.SubscriptionAllIncoming;

import com.clue.proto.Msg;

public class GameLogic implements MessageHandler {

  private static Logger logger = new Logger(GameLogic.class);
  private static Config config = Config.getInstance();

  private Router router;

  public GameLogic() {
    this.router = Router.getInstance();
    this.router.register(new SubscriptionAllIncoming(), this);
  }


  public void echoBackToSender(Message msg) {
    logger.debug("EchoReplyHandler received message");
    Msg.Header hdr = Msg.Header.newBuilder()
        .setMsgType(msg.getMessage().getDescriptorForType().getFullName())
        .setSource(Instance.getId())
        .setDestination(msg.getHeader().getSource())
        .build();

    com.google.protobuf.Message proto = msg.getMessage().toBuilder()
        .setField(msg.getMessage().getDescriptorForType().findFieldByName("header"), hdr)
        .build();

    logger.debug("header  =  " + hdr.toString());
    logger.debug("proto   =  " + proto.toString());
    router.route(new Message(hdr, proto));
  }
  
  @Override
  public void handleMessage(Message msg) {
    echoBackToSender(msg);

    // Here is how to handle messages of a specific type
    String msg_type = msg.getHeader().getMsgType();
    if (msg_type.equals(Msg.ConnectRequest.getDescriptor().getFullName())) {
      logger.debug("handleMessage() - explicitly handling message of type: " + msg_type);
    } else {
      logger.debug("handleMessage() - got unhandled message type: " + msg_type);
    }
  }
}
