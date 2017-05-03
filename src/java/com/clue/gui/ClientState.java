package com.clue.gui;

import com.clue.app.Config;
import com.clue.app.Logger;
import com.clue.app.Instance;

import com.clue.route.Message;
import com.clue.route.MessageHandler;
import com.clue.route.Router;
import com.clue.route.SubscriptionAllIncoming;

import com.clue.proto.Data;
import com.clue.proto.Msg;

public class ClientState implements MessageHandler {

  private static Logger logger = new Logger(ClientState.class);
  private static Config config = Config.getInstance();

  private boolean bConnected;  
  private Router router;
  private Msg.GameState state;
  
  private ClientState() {
    bConnected = false;
    state = Msg.GameState.getDefaultInstance();
        
    router = Router.getInstance();
    router.register(new SubscriptionAllIncoming(), this);

    connect();
  }

  private static class SingletonHelper {
    private static final ClientState INSTANCE = new ClientState();
  }
  
  public static ClientState getInstance() {
    return SingletonHelper.INSTANCE;
  }

  public boolean isConnected() {
    return bConnected;
  }
  
  public void connect() {
    // @todo get name from a GUI
    String name = config.getProperty("instance.name", String.class);
    Msg.ConnectRequest req = Msg.ConnectRequest.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.ConnectRequest.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(Instance.getServerId())
                   .build())
        .setClientId(Instance.getId())
        .setName(name)
        .build();
    router.route(new Message(req.getHeader(), req));
  }

  public void handleConnection(Msg.ConnectResponse response) {
    if (response.getGoodConnection()) {
      bConnected = true;
      logger.debug("handleMessage() - connection = GOOD");
    } else {
      bConnected = false;
      logger.error("handleMessage() - connection = BAD : "
                   + "Client ID already in use: "
                   + Integer.toString(Instance.getId()));
    }
  }

  public Msg.GameState gameState() {
    return state;
  }

  public void handleGameState(Msg.GameState response) {
    state = response;
  }
    
  @Override
  public boolean shouldCallHandleOnGuiThread() {
    return true;
  }

  /**
   * @brief This is where all message "handling" should be done
   * @param msg 
   */
  @Override
  public void handleMessage(Message msg) {
    String msg_type = msg.getHeader().getMsgType();
    if (msg_type.equals(Msg.ConnectResponse.getDescriptor().getFullName())) {
      handleConnection((Msg.ConnectResponse)msg.getMessage());
    }
    else if (msg_type.equals(Msg.GameState.getDescriptor().getFullName())) {
      handleGameState((Msg.GameState)msg.getMessage());
    }
  }

}
