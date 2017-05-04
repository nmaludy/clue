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

  private Router router;
  private Msg.GameState.Builder state;
  private Data.Solution solution;

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

  public void handlePlayerSuggestion(Msg.PlayerSuggestion req) {
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
      if (player.getClientId() != clientId) {
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
      
    } else if (msg_type.equals(Msg.PlayerMove.getDescriptor().getFullName())) {
    	
    	Data.Player.Builder playerMove = getPlayerById(msg.getHeader().getSource());
    	Msg.PlayerMove move_msg = (Msg.PlayerMove)msg.getMessage();
    	
    	/*********DETERMINE VALID MOVES*********/
    	boolean validMove = true;

    	// Check for valid moves based on current location
    	// Current location is the Study
		if (( playerMove.getLocation() == Data.Location.LOC_STUDY ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_0 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_2 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_KITCHEN   )    ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 0
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_0 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_STUDY ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_LIBRARY )   ) )
		{
			validMove = true;
		}
    	// Current location is the Library
		else if (( playerMove.getLocation() == Data.Location.LOC_LIBRARY ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_0 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_1 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_3 )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 1
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_1 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_LIBRARY ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALL )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hall
		else if (( playerMove.getLocation() == Data.Location.LOC_HALL ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_1 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_4 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_CONSERVATORY )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 2
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_2 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_STUDY ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_LOUNGE )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 3
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_3 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_LIBRARY ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_BILLIARD_ROOM )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 4
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_4 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALL ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_DINING_ROOM )   ) )
		{
			validMove = true;
		}
    	// Current location is the Lounge
		else if (( playerMove.getLocation() == Data.Location.LOC_LOUNGE ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_2 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_5 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_7 )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 5
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_5 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_LOUNGE ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_BILLIARD_ROOM )   ) )
		{
			validMove = true;
		}
    	// Current location is the Billiard Room
		else if (( playerMove.getLocation() == Data.Location.LOC_BILLIARD_ROOM ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_3 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_5 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_6 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_8 )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 6
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_6 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_DINING_ROOM ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_BILLIARD_ROOM )   ) )
		{
			validMove = true;
		}
    	// Current location is the Dining Room
		else if (( playerMove.getLocation() == Data.Location.LOC_DINING_ROOM ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_4 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_6 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_9 )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 7
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_7 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_CONSERVATORY ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_LOUNGE )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 8
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_8 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_BALLROOM ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_BILLIARD_ROOM )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 9
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_9 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_KITCHEN ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_DINING_ROOM )   ) )
		{
			validMove = true;
		}
    	// Current location is the Conservatory
		else if (( playerMove.getLocation() == Data.Location.LOC_CONSERVATORY ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_7 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_10 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALL )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 10
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_10 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_CONSERVATORY ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_BALLROOM )   ) )
		{
			validMove = true;
		}
    	// Current location is the Ballroom
		else if (( playerMove.getLocation() == Data.Location.LOC_BALLROOM ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_10 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_8 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_11 )   ) )
		{
			validMove = true;
		}
    	// Current location is the Hallway 11
		else if (( playerMove.getLocation() == Data.Location.LOC_HALLWAY_11 ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_KITCHEN ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_BALLROOM )   ) )
		{
			validMove = true;
		}
    	// Current location is the Kitchen
		else if (( playerMove.getLocation() == Data.Location.LOC_KITCHEN ) && 
				( ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_11 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_HALLWAY_9 ) ||
				  ( move_msg.getDestination() == Data.Location.LOC_STUDY )   ) )
		{
			validMove = true;
		}
		else if ( playerMove.getLocation() == Data.Location.LOC_NONE )
		{
			validMove = true;
		}
    	/*********DETERMINE INVALID MOVES*********/
		else
		{
			validMove = false;
		}
		
		// Then check if the desired destination is a hallway && that hallway is occupied
		// then trigger invalid move
    	for (Data.Player player : state.getPlayersList())
        {
    		if (( player.getLocation() == Data.Location.LOC_HALLWAY_0 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_1 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_2 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_3 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_4 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_5 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_6 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_7 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_8 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_9 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_10 || 
    				player.getLocation() == Data.Location.LOC_HALLWAY_11 ) &&
    				( move_msg.getDestination() == player.getLocation() ) )
    		{
	          	validMove = false;
	        }
        }

    	/*********IF VALID, THEN MOVE*********/
    	if (validMove)
    	{
    		playerMove.setLocation(move_msg.getDestination());
    	}
    	/*********ELSE SEND INVALID MOVE MESSAGE*********/
    	else
    	{
    		// Send invalid move message?
    	    logger.debug("[************DEBUG**********] - invalid destination in move message.");

            Msg.InvalidMove invMv = Msg.InvalidMove.newBuilder()
                    .setHeader(Msg.Header.newBuilder()
                               .setMsgType(Msg.InvalidMove.getDescriptor().getFullName())
                               .setSource(Instance.getId())
                               .setDestination(move_msg.getHeader().getSource())
                               .build())
                    .setDestination(Data.Location.LOC_NONE)
                    .build();
                router.route(new Message(invMv.getHeader(), invMv));
    	}
    	
    	// Send updated game state (player moved)
    	sendCurrentGameState(Instance.getBroadcastId());
    
    } else if (msg_type.equals(Msg.PlayerSuggestion.getDescriptor().getFullName())) {
      handlePlayerSuggestion((Msg.PlayerSuggestion)msg.getMessage());

    } else if (msg_type.equals(Msg.DisproveResponse.getDescriptor().getFullName())) {
      handleDisproveResponse((Msg.DisproveResponse)msg.getMessage());

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
