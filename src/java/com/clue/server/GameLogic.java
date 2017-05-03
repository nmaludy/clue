package com.clue.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

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
  private Data.Solution solution;

  public GameLogic() {
    this.state = Msg.GameState.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.GameState.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(Instance.getBroadcastId()))
        .setStatus(Data.GameStatus.GAME_NOT_STARTED)
        .setClientPreviousTurn(-1)
        .setClientCurrentTurn(-1);
    this.solution = null;
    
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

  public void handleStartGameRequest(Msg.StartGameRequest req) {
    int client_id = req.getHeader().getSource();
    logger.debug("handleStartGameRequest() - received request from: "
                 + Integer.toString(client_id));

    if (state.getStatus() != Data.GameStatus.GAME_IN_PROGRESS) {
      // Generate solution
      ArrayList<Data.Suspect> suspects = new ArrayList<Data.Suspect>();
      suspects.add(Data.Suspect.SUS_MISS_SCARLETT);
      suspects.add(Data.Suspect.SUS_COL_MUSTARD);
      suspects.add(Data.Suspect.SUS_MRS_WHITE);
      suspects.add(Data.Suspect.SUS_MR_GREEN);
      suspects.add(Data.Suspect.SUS_MRS_PEACOCK);
      suspects.add(Data.Suspect.SUS_PROF_PLUM);

      ArrayList<Data.Location> locations = new ArrayList<Data.Location>();
      locations.add(Data.Location.LOC_STUDY);
      locations.add(Data.Location.LOC_HALL);
      locations.add(Data.Location.LOC_LOUNGE);
      locations.add(Data.Location.LOC_LIBRARY);
      locations.add(Data.Location.LOC_BILLIARD_ROOM);
      locations.add(Data.Location.LOC_DINING_ROOM);
      locations.add(Data.Location.LOC_CONSERVATORY);
      locations.add(Data.Location.LOC_BALLROOM);
      locations.add(Data.Location.LOC_KITCHEN);

      ArrayList<Data.Weapon> weapons = new ArrayList<Data.Weapon>();
      weapons.add(Data.Weapon.WPN_CANDLESTICK);
      weapons.add(Data.Weapon.WPN_KNIFE);
      weapons.add(Data.Weapon.WPN_LEAD_PIPE);
      weapons.add(Data.Weapon.WPN_REVOLVER);
      weapons.add(Data.Weapon.WPN_ROPE);
      weapons.add(Data.Weapon.WPN_WRENCH);

      // Randomly shuffle all collections
      Collections.shuffle(suspects);
      Collections.shuffle(locations);
      Collections.shuffle(weapons);

      // Create a solution from the shuffled collections
      this.solution = Data.Solution.newBuilder()
          .setSuspect(suspects.remove(0))
          .setLocation(locations.remove(0))
          .setWeapon(weapons.remove(0))
          .build();
      logger.debug("handleStartGameRequest() - created solution: "
                   + solution.toString());

      // Reveal the remaining clues to the clients playing the game
      ArrayList<Msg.RevealClues.Builder> reveal_list =
          new ArrayList<Msg.RevealClues.Builder>();
      for (Data.Player.Builder player : state.getPlayersBuilderList()) {
        reveal_list.add(Msg.RevealClues.newBuilder()
                        .setHeader(Msg.Header.newBuilder()
                                   .setMsgType(Msg.RevealClues.getDescriptor().getFullName())
                                   .setSource(Instance.getId())
                                   .setDestination(player.getClientId())));
      }

      int index = 0;
      while (!suspects.isEmpty()) {
        Msg.RevealClues.Builder reveal = reveal_list.get(index++ % reveal_list.size());
        reveal.getCluesBuilder().addSuspects(suspects.remove(0));
      }
      
      index = 0;
      while (!locations.isEmpty()) {
        Msg.RevealClues.Builder reveal = reveal_list.get(index++ % reveal_list.size());
        reveal.getCluesBuilder().addLocations(locations.remove(0));
      }
      
      index = 0;
      while (!weapons.isEmpty()) {
        Msg.RevealClues.Builder reveal = reveal_list.get(index++ % reveal_list.size());
        reveal.getCluesBuilder().addWeapons(weapons.remove(0));
      }

      // Send reveal messages to clients
      for (Msg.RevealClues.Builder reveal : reveal_list) {
        Msg.RevealClues msg = reveal.build();
        logger.debug("handleStartGameRequest() - revealing clues to player: "
                     + msg.toString());
        router.route(new Message(msg.getHeader(), msg));
      }
      
      
      // Randomly choose who goes first
      // nextInt is normally exclusive of the top value,
      // so add 1 to make it inclusive
      int current_client_id =
          ThreadLocalRandom.current().nextInt(0, state.getPlayersBuilderList().size());
      state.setStatus(Data.GameStatus.GAME_IN_PROGRESS);
      state.setClientCurrentTurn(current_client_id);

      logger.debug("handleStartGameRequest() - starting game, player going first = "
                   + current_client_id);

      // tell all clients that the game has started
      sendCurrentGameState(Instance.getBroadcastId());
    }
  }
  
  /*
   * this feature is for handling an accusation. @TODO: fix handling msg
   * 1. ask nick about PlayerAccusation vs Guess
   * 
   */
  public void handlePlayerAccusationRequest(Msg.PlayerAccusation req){
	  int client_id = req.getHeader().getSource();
	  
	  logger.debug("handleAccusationRequest() - received accusation request from: "
                + Integer.toString(client_id));
	  
	  logger.debug("handleAccusationRequest()  - client's guess: " + req.getGuess().getSolution().getSuspect().name() + ", " +
		  		req.getGuess().getSolution().getLocation().name() + ", " + req.getGuess().getSolution().getWeapon().name() ); 
	  
	  
	  if (req.getGuess().getSolution().equals(this.solution))
	  {
		  // build gameEnd message
		  Msg.GameEnd.Builder response = Msg.GameEnd.newBuilder()
		   .setHeader(Msg.Header.newBuilder()
		              .setMsgType(Msg.GameEnd.getDescriptor().getFullName())
		              .setSource(Instance.getId())
		              .setDestination(Instance.getBroadcastId())
		              .build());
		  
		  // send response to back to all clients
		  Msg.GameEnd msg = response.build();
		  router.route(new Message(msg.getHeader(), msg));            
	  }
	  else
	  {
		  // build PlayerAccusationFailed msg 
		  
		   Msg.PlayerAccusationFailed.Builder response = Msg.PlayerAccusationFailed.newBuilder()
	        .setHeader(Msg.Header.newBuilder()
	                   .setMsgType(Msg.PlayerAccusationFailed.getDescriptor().getFullName())
	                   .setSource(Instance.getId())
	                   .setDestination(req.getHeader().getSource())
	                   .build());
		  
		    // Send response back to client from the (playerAccusationRequest req)
		    Msg.PlayerAccusationFailed msg = response.build();
		    router.route(new Message(msg.getHeader(), msg));
		  
	  }
	  
	  
	  /* check solution message to see if room, weapon, and/or suspect matches and make the player reveal ??? */
	  // Read in the room, weapon, suspect from the player accusation message and prompt reveal */
	  Data.Location room = req.getGuess().getSolution().getLocation();
	  Data.Weapon weapon = req.getGuess().getSolution().getWeapon();
	  Data.Suspect suspect = req.getGuess().getSolution().getSuspect();
	  
	  System.out.println(room + " " + weapon + " " + suspect);
	  
	   
	  
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
      
    } else if (msg_type.equals(Msg.StartGameRequest.getDescriptor().getFullName())) {
      handleStartGameRequest((Msg.StartGameRequest)msg.getMessage());
      
    } else if (msg_type.equals(Msg.PlayerAccusation.getDescriptor().getFullName())) {
    	handlePlayerAccusationRequest((Msg.PlayerAccusation)msg.getMessage());
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
  