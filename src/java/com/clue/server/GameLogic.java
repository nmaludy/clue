package com.clue.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

  private enum TurnType {
    START, NEXT
  }

  private Router router;
  private Msg.GameState.Builder state;
  private Data.Solution solution;
  private ArrayList<Data.Location> roomLocations;

  private Msg.PlayerSuggestion currentSuggestion = null;
  private ArrayList<Integer> currentSuggestionClientList = null;
  private int currentSuggestionIndex = -1;

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

    this.roomLocations = new ArrayList<Data.Location>();
    roomLocations.add(Data.Location.LOC_STUDY);
    roomLocations.add(Data.Location.LOC_HALL);
    roomLocations.add(Data.Location.LOC_LOUNGE);
    roomLocations.add(Data.Location.LOC_LIBRARY);
    roomLocations.add(Data.Location.LOC_BILLIARD_ROOM);
    roomLocations.add(Data.Location.LOC_DINING_ROOM);
    roomLocations.add(Data.Location.LOC_CONSERVATORY);
    roomLocations.add(Data.Location.LOC_BALLROOM);
    roomLocations.add(Data.Location.LOC_KITCHEN);
    
    this.router = Router.getInstance();
    this.router.register(new SubscriptionAllIncoming(), this);
  }

  public static String suspectToString(Data.Suspect suspect) {
    switch (suspect) {
      case SUS_NONE:          return "None";
      case SUS_MISS_SCARLETT: return "Miss. Scarlett";
      case SUS_COL_MUSTARD:   return "Col. Mustard";
      case SUS_MRS_WHITE:     return "Mrs. White";
      case SUS_MR_GREEN:      return "Mr. Green";
      case SUS_MRS_PEACOCK:   return "Mrs. Peacock";
      case SUS_PROF_PLUM:     return "Prof. Plum";
    }
    return "Invalid";
  }

  
  public static Data.Location suspectStartingLocation(Data.Suspect suspect) {
    // see picture in game description PDF
    switch (suspect) {
      case SUS_NONE:          return Data.Location.LOC_NONE;
      case SUS_MISS_SCARLETT: return Data.Location.LOC_HOME;
      case SUS_COL_MUSTARD:   return Data.Location.LOC_HOME;
      case SUS_MRS_WHITE:     return Data.Location.LOC_HOME;
      case SUS_MR_GREEN:      return Data.Location.LOC_HOME;
      case SUS_MRS_PEACOCK:   return Data.Location.LOC_HOME;
      case SUS_PROF_PLUM:     return Data.Location.LOC_HOME;
    }
    return Data.Location.LOC_NONE;
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
      player.setInactive(false);
      player.setMadeFalseAccusation(false);
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
      player.setSuspect( req.getSuspect() );
      // assign starting location based on picture in PDF
      player.setLocation( suspectStartingLocation(req.getSuspect()) );
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
      ArrayList<Data.Suspect> all_suspects = new ArrayList<Data.Suspect>(suspects);

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

      // create a list of clues to be revealed
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

      // add all of the inactive characters
      int inactive_client_id = 100;
      for (Data.Suspect suspect : all_suspects) {

        Data.Player.Builder suspect_player = null;
        for (Data.Player.Builder player : state.getPlayersBuilderList()) {
          if ( player.getSuspect() == suspect ) {
            suspect_player = player;
            break;
          }
        }

        if (suspect_player == null) {
          Data.Player.Builder player = state.addPlayersBuilder();
          player.setClientId( inactive_client_id++ );
          player.setName( "* " + suspectToString(suspect) + " *" );
          player.setMadeFalseAccusation(false);
          player.setInactive(true);
          player.setSuspect(suspect);
          player.setLocation( suspectStartingLocation(suspect) );
        }
      }

      
      // choose a player to start and
      // tell all clients that the game has started
      logger.debug("handleStartGameRequest() - starting game, choosing player to go first.. ");
      advanceTurn(TurnType.START);
    }
  }

  private void advanceTurn(TurnType turnType) {
    
    ArrayList<Integer> client_ids = new ArrayList<Integer>();
    for (Data.Player.Builder player : state.getPlayersBuilderList()) {
      // if player made a false accusation, they can no longer take
      // a turn.
      // @note: they are still kept in the list because they still
      // need to be able to disprove suggestions
      if ( player.getInactive() == false &&
           player.getMadeFalseAccusation() == false ) {
        client_ids.add(player.getClientId());
      }
    }

    // sort the list 0...n
    Collections.sort(client_ids);

    // find the current client's turn
    int prev_client_id = state.getClientCurrentTurn();
    int next_index = 0;

    // Randomly choose who goes first
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    if (turnType == TurnType.START) {
      state.setStatus(Data.GameStatus.GAME_IN_PROGRESS);

      // Determine if a player has chosen Miss Scarlett
      int miss_scarlett_idx = -1;
      for (int i = 0; i < client_ids.size(); ++i) {
        int client_id = client_ids.get(i);
        Data.Player.Builder player = getPlayerById(client_id);
        if (player.getSuspect() == Data.Suspect.SUS_MISS_SCARLETT &&
            player.getInactive() == false) {
          miss_scarlett_idx = i;
          break;
        }
      }

      // Miss Scarlett goes first, if she's playing
      // otherwise a random player is chosen
      if (miss_scarlett_idx >= 0) {
        logger.debug("advanceTurn() - Miss Scarlett is playing, she goes first!");
        next_index = miss_scarlett_idx;
      } else {
        logger.debug("advanceTurn() - Miss Scarlett is NOT playing picking at random!");
        next_index = ThreadLocalRandom.current().nextInt(0, client_ids.size());
      }
      
    } else {
      int current_index = client_ids.indexOf(prev_client_id);
      next_index = ++current_index;
      if (next_index >= client_ids.size()) {
        next_index = 0;
      }
    }

    int next_client_id = client_ids.get(next_index);
    
    logger.debug("advanceTurn() - previous player's turn = " + prev_client_id);
    logger.debug("advanceTurn() - next player's turn = " + next_client_id);
    
    state.setClientPreviousTurn(prev_client_id);
    state.setClientCurrentTurn(next_client_id);

    // Send updated gate state (next player's turn)
    sendCurrentGameState(Instance.getBroadcastId());
    
    // send a message directly to the player whose turn it is
    Msg.PlayerTurn msg = Msg.PlayerTurn.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.PlayerTurn.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(next_client_id)
                   .build())
        .setClientPreviousTurn(prev_client_id)
        .setClientCurrentTurn(next_client_id)
        .build();
    router.route(new Message(msg.getHeader(), msg));
  }

  private void handlePlayerMove(Msg.PlayerMove move) {
    Data.Player.Builder player = getPlayerById(move.getHeader().getSource());
    player.setLocation(move.getDestination());
    	
    // Send updated game state (player moved)
    sendCurrentGameState(Instance.getBroadcastId());

    // if the player mvoed into a hallway advance to the next player
    switch (move.getDestination()) {
      case LOC_HALLWAY_0:
      case LOC_HALLWAY_1:
      case LOC_HALLWAY_2:
      case LOC_HALLWAY_3:
      case LOC_HALLWAY_4:
      case LOC_HALLWAY_5:
      case LOC_HALLWAY_6:
      case LOC_HALLWAY_7:
      case LOC_HALLWAY_8:
      case LOC_HALLWAY_9:
      case LOC_HALLWAY_10:
      case LOC_HALLWAY_11:
        logger.debug("handlePlayerMove() - Player moved into a hallway: "+
                     move.getDestination().name());
        advanceTurn(TurnType.NEXT);
        break;
      default:
        logger.debug("handlePlayerMove() - Player moved into a room: "+
                     move.getDestination().name());
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
	  logger.debug("handleAccusationRequest()  - client's guess: " + req.getSolution().toString());
    logger.debug("handleAccusationRequest()  - solution : " + this.solution.toString()); 
    
	  
	  // check solution message to see if room, weapon, and suspect matches
    // the game's solution
    // if it matches then the player wins
    // else the player "fails"
	  if (req.getSolution().getWeapon() == this.solution.getWeapon() &&
        req.getSolution().getSuspect() == this.solution.getSuspect() &&
        req.getSolution().getLocation() == this.solution.getLocation())
	  {
		  // build gameEnd message
		  Msg.GameEnd.Builder response = Msg.GameEnd.newBuilder()
          .setHeader(Msg.Header.newBuilder()
                     .setMsgType(Msg.GameEnd.getDescriptor().getFullName())
                     .setSource(Instance.getId())
                     .setDestination(Instance.getBroadcastId())
                     .build())
          .setClientWinner(client_id);
		  
		  // send response to back to all clients
		  Msg.GameEnd msg = response.build();
		  router.route(new Message(msg.getHeader(), msg));

      // @todo send game finished
      
	  }
	  else
	  {
      // Mark the player as having made a false accusation
      Data.Player.Builder player = getPlayerById(client_id);
      player.setMadeFalseAccusation(true);
      
		  // build PlayerAccusationFailed msg
      Msg.PlayerAccusationFailed.Builder response = Msg.PlayerAccusationFailed.newBuilder()
	        .setHeader(Msg.Header.newBuilder()
	                   .setMsgType(Msg.PlayerAccusationFailed.getDescriptor().getFullName())
	                   .setSource(Instance.getId())
	                   .setDestination(req.getHeader().getSource())
	                   .build());

      // @todo handle player accusation failure
      // they are still in the game, but can't move, make suggestions, or make
      // accusation *they still need to be able to disprove*
		  
      // Send response back to client from the (playerAccusationRequest req)
      Msg.PlayerAccusationFailed msg = response.build();
      router.route(new Message(msg.getHeader(), msg));

      // go to next player
      advanceTurn(TurnType.NEXT);
	  }
  }

  public void handlePlayerSuggestion(Msg.PlayerSuggestion req) {
    // move the player who is the character in the suggestion to the
    // room in the suggestion    
    for (Data.Player.Builder player : state.getPlayersBuilderList()) {      
      if (req.getSolution().getSuspect() == player.getSuspect()) {
        Msg.PlayerMove move = Msg.PlayerMove.newBuilder()
            .setHeader(Msg.Header.newBuilder()
                       .setMsgType(Msg.DisproveRequest.getDescriptor().getFullName())
                       .setSource(player.getClientId())
                       .setDestination(Instance.getId())
                       .build())
            .setDestination(req.getSolution().getLocation())
            .build();
        handlePlayerMove(move);
      }
    }
    
    // Round-robin send Msg.DisproveRequest to clients

    currentSuggestion = req;
    currentSuggestionClientList = new ArrayList<Integer>();
    currentSuggestionIndex = 0;

    // idea... if client id is x:   0 .. x .. n
    // we want to start with player x+1, go to player n, then wrap around to 0
    // then stop at player x-1

    // first add everyone to a list
    ArrayList<Integer> tmpList = new ArrayList<Integer>();
    int clientId = req.getHeader().getSource();
    for (Data.Player.Builder player : state.getPlayersBuilderList()) {
      if (player.getClientId() != clientId &&
          player.getInactive() == false) {
        currentSuggestionClientList.add(player.getClientId());
      }
    }

    // sort the list 0...n
    Collections.sort(currentSuggestionClientList);

    // remove the ids < client id and put them into tmplist
    while (!currentSuggestionClientList.isEmpty()) {
      if (currentSuggestionClientList.get(0) < clientId) {
        tmpList.add(currentSuggestionClientList.remove(0));
      } else {
        break;
      }
    }

    // now currentSuggestionClientList has ids x+1..n (in order)
    // also, tmpList has ids 0...x-1 (in order)
    // appending tmpList to the end of currentSuggestionClientList and
    // we will have x+1..n,0...x-1
    currentSuggestionClientList.addAll(tmpList);

    logger.debug("handlePlayerSuggestion() - disproval client order: "
                 + String.join(",",
                               currentSuggestionClientList.stream()
                               .map(i -> Integer.toString(i))
                               .collect(Collectors.toList())));

    // send disprove request
    sendDisproveRequestToCurrentPlayer();
  }

  public void sendDisproveRequestToCurrentPlayer() {
    int client_id = currentSuggestionClientList.get(currentSuggestionIndex);
    Msg.DisproveRequest msg = Msg.DisproveRequest.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.DisproveRequest.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(client_id)
                   .build())
        .setGuess(currentSuggestion.getSolution().toBuilder().build())
        .build();
    
    logger.debug("sendDisproveRequestToCurrentPlayer() - send disproval to client "
                 + Integer.toString(client_id));
    router.route(new Message(msg.getHeader(), msg));
  }

  public void handleDisproveResponse(Msg.DisproveResponse resp) {
    // If receive back a Msg.DisproveResponse with all clues set to XXX_NONE
    //   Goto next player
    // Else
    //   Send Msg.DisproveResponse back to player who made suggestion
    // If we get to end and no one has disproved send back Msg.DisproveResponse with all clues set to XXX_NONE

    if (resp.getResponse().getLocation() == Data.Location.LOC_NONE &&
        resp.getResponse().getWeapon() == Data.Weapon.WPN_NONE &&
        resp.getResponse().getSuspect() == Data.Suspect.SUS_NONE) {
      ++currentSuggestionIndex;
      if (currentSuggestionIndex < currentSuggestionClientList.size()) {
        // goto next player
        sendDisproveRequestToCurrentPlayer();
      } else {
        // send back DisproveResponse to original with all NONE
        Msg.DisproveResponse msg = Msg.DisproveResponse.newBuilder()
            .setHeader(Msg.Header.newBuilder()
                       .setMsgType(Msg.DisproveResponse.getDescriptor().getFullName())
                       .setSource(Instance.getId())
                       .setDestination(currentSuggestion.getHeader().getSource())
                       .build())
            .setGuess(resp.getGuess().toBuilder().build())
            .setResponse(Data.Solution.newBuilder()
                         .setClientId(Instance.getId())
                         .setWeapon(Data.Weapon.WPN_NONE)
                         .setSuspect(Data.Suspect.SUS_NONE)
                         .setLocation(Data.Location.LOC_NONE)
                         .build())
            .build();

        currentSuggestion = null;
        currentSuggestionClientList = null;
        currentSuggestionIndex = -1;
        
        router.route(new Message(msg.getHeader(), msg));
      }
    } else {
      // send back DisproveResponse to original with the solution
      Msg.DisproveResponse msg = Msg.DisproveResponse.newBuilder()
          .setHeader(Msg.Header.newBuilder()
                     .setMsgType(Msg.DisproveResponse.getDescriptor().getFullName())
                     .setSource(Instance.getId())
                     .setDestination(currentSuggestion.getHeader().getSource())
                     .build())
          // copy the guess from the response and forward it on
          .setGuess(resp.getGuess().toBuilder().build())
          .setResponse(resp.getResponse().toBuilder().build())
          .build();
      
      currentSuggestion = null;
      currentSuggestionClientList = null;
      currentSuggestionIndex = -1;
      
      router.route(new Message(msg.getHeader(), msg));
    }
  }

  public void handlePassTurn(Msg.PassTurn pass) {
    if (pass.getClientId() != state.getClientCurrentTurn()) {
      logger.error("handlePassTurn() - Received a Msg.PassTurn from a client " +
                   "out of turn... state.clientCurrentTurn = " +
                   Integer.toString(state.getClientCurrentTurn()) +
                   "  pass.getClientId = " +
                   Integer.toString(pass.getClientId()));
      return;
    }

    // move to next player's turn
    advanceTurn(TurnType.NEXT);
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
      
    } else if (msg_type.equals(Msg.PlayerMove.getDescriptor().getFullName())) {
      handlePlayerMove((Msg.PlayerMove)msg.getMessage());
    	
    } else if (msg_type.equals(Msg.PlayerSuggestion.getDescriptor().getFullName())) {
      handlePlayerSuggestion((Msg.PlayerSuggestion)msg.getMessage());

    } else if (msg_type.equals(Msg.DisproveResponse.getDescriptor().getFullName())) {
      handleDisproveResponse((Msg.DisproveResponse)msg.getMessage());

    } else if (msg_type.equals(Msg.PassTurn.getDescriptor().getFullName())) {
      handlePassTurn((Msg.PassTurn)msg.getMessage());

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
