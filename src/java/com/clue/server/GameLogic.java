package com.clue.server;

import com.clue.app.Instance;
import com.clue.app.Logger;
import com.clue.app.Config;

import com.clue.route.Message;
import com.clue.route.MessageHandler;
import com.clue.route.Router;
import com.clue.route.SubscriptionAllIncoming;

import com.clue.proto.Data;
import com.clue.proto.Msg;

public class GameLogic implements MessageHandler {

  private static Logger logger = new Logger(GameLogic.class);
  private static Config config = Config.getInstance();

  private Router router;
  private Msg.GameState.Builder state;

  public GameLogic() {
    this.state = Msg.GameState.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.GameState.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(Instance.getBroadcastId()));
    
    this.router = Router.getInstance();
    this.router.register(new SubscriptionAllIncoming(), this);
  }

  private void sendCurrentGameState(int clientId) {
    Msg.GameState.Builder bldr = state.clone();
    bldr.getHeaderBuilder().setDestination(clientId);    
    Msg.GameState msg = bldr.build();
    router.route(new Message(msg.getHeader(), msg));
  }

  private Data.Player.Builder getPlayerById(int clientId) {
    for (Data.Player.Builder player : state.getPlayersBuilderList()) {
      if (player.getClientId() == clientId) {
        return player;
      }
    }
    return null;
  }

  public void handleConnectionRequest(Msg.ConnectRequest req) {
    logger.debug("handleConnectionRequest() - received connection request from: "
                 + Integer.toString(req.getClientId()));
    
    Msg.ConnectResponse.Builder response = Msg.ConnectResponse.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.ConnectResponse.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(req.getHeader().getSource())
                   .build());
    
    // by default we assume it's a unique ID
    response.setGoodConnection(true);

    // check if this Client ID has already been taken
    // if so then the connection is not good
    if (getPlayerById(req.getClientId()) != null) {
      logger.warn("handleConnectionRequest() - found duplicate client! "
                  + Integer.toString(req.getClientId()));
      response.setGoodConnection(false);
    }

    // update our game state with the new client
    if (response.getGoodConnection()) {
      logger.debug("handleConnectionRequest() - connetion good! "
                   + Integer.toString(req.getClientId()));
      Data.Player.Builder player = state.addPlayersBuilder();
      player.setClientId(req.getClientId());
      player.setName(req.getName());

      player = getPlayerById(req.getClientId());
      logger.debug("handleConnectionRequest() - retrieval test "
                   + Integer.toString(req.getClientId()));
    }

    // Send response back to client
    Msg.ConnectResponse msg = response.build();
    router.route(new Message(msg.getHeader(), msg));
  }


  public void handleSuspectClaimRequest(Msg.SuspectClaimRequest req) {
    int client_id = req.getHeader().getSource();
    logger.debug("handleSuspectClaimRequest() - received request from: "
                 + Integer.toString(client_id));
    logger.debug("handleSuspectClaimRequest() - client trying to claim suspct: "
                 + req.getSuspect().name());
    
    Msg.SuspectClaimResponse.Builder response = Msg.SuspectClaimResponse.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.SuspectClaimResponse.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(req.getHeader().getSource())
                   .build());
    
    // by default we assume it's a unique ID
    response.setClaimSuccess(true);

    // check if this Client ID has already been taken
    // if so then the connecti1n is not good
    for (Data.Player.Builder player : state.getPlayersBuilderList()) {
      if (player.getSuspect() == req.getSuspect()) {
        logger.warn("handleSuspectClaimRequest() - suspect already claimed by "
                    + Integer.toString(player.getClientId()));
        response.setClaimSuccess(false);
        break;
      }
    }

    // update our game state with the new client
    if (response.getClaimSuccess()) {
      logger.debug("handleSuspectClaimRequest() - claim good! "
                   + Integer.toString(client_id));
      Data.Player.Builder player = getPlayerById(client_id);
      player.setSuspect(req.getSuspect());
    }

    // Send response back to client
    Msg.SuspectClaimResponse msg = response.build();
    router.route(new Message(msg.getHeader(), msg));

    // Send updated gate state (player chose a suspect)
    sendCurrentGameState(Instance.getBroadcastId());
  }
  
  
  @Override
  public void handleMessage(Message msg) {
    // Here is how to handle messages of a specific type
    String msg_type = msg.getHeader().getMsgType();
    logger.debug("handleMessage() - received message of type: " + msg_type);
    
    if (msg_type.equals(Msg.ConnectRequest.getDescriptor().getFullName())) {
      handleConnectionRequest((Msg.ConnectRequest)msg.getMessage());
    } else if (msg_type.equals(Msg.SuspectClaimRequest.getDescriptor().getFullName())) {
      handleSuspectClaimRequest((Msg.SuspectClaimRequest)msg.getMessage());
    } else if (msg_type.equals(Msg.GameStateRequest.getDescriptor().getFullName())) {
      sendCurrentGameState(msg.getHeader().getSource());
    } else if (msg_type.equals(Msg.PlayerMove.getDescriptor().getFullName())) {
      
    	Data.Location.Identity location = Msg.PlayerMove.getDefaultInstance().getDestination();
    	
    	
        for (Data.Player player : state.getPlayersList())
        {
        	/* PSEUDOCODE
        	if (player == Message Sender)
        	{
        		State.thisPlayer.Location = location (defined above as location within PlayerMove message)
        	}
        	*/
        }
    	
    	
    
    } else {
      logger.debug("handleMessage() - got unhandled message type: " + msg_type);
    }
  }

  // // This is an example
  // public void echoBackToSender(Message msg) {
  //   logger.debug("EchoReplyHandler received message");
    
  //   //.setDestination(msg.getHeader().getSource())
  //   Msg.Header hdr = Msg.Header.newBuilder()
  //       .setMsgType(msg.getMessage().getDescriptorForType().getFullName())
  //       .setSource(Instance.getId())
  //       .setDestination(Instance.getBroadcastId())
  //       .build();

  //   com.google.protobuf.Message proto = msg.getMessage().toBuilder()
  //       .setField(msg.getMessage().getDescriptorForType().findFieldByName("header"), hdr)
  //       .build();

  //   logger.debug("header  =  " + hdr.toString());
  //   logger.debug("proto   =  " + proto.toString());
  //   router.route(new Message(hdr, proto));
  // }
}
