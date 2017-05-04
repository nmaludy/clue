package com.clue.gui;

import java.util.HashSet;
import java.util.Set;

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

  private HashSet<Data.Location> myLocations;
  private HashSet<Data.Weapon> myWeapons;
  private HashSet<Data.Suspect> mySuspects;
  
  private HashSet<Data.Location> notebookLocations;
  private HashSet<Data.Weapon> notebookWeapons;
  private HashSet<Data.Suspect> notebookSuspects;
  
  private ClientState() {
    bConnected = false;
    state = Msg.GameState.getDefaultInstance();

    myLocations = new HashSet<Data.Location>();
    myWeapons = new HashSet<Data.Weapon>();
    mySuspects = new HashSet<Data.Suspect>();
    
    notebookLocations = new HashSet<Data.Location>();
    notebookWeapons = new HashSet<Data.Weapon>();
    notebookSuspects = new HashSet<Data.Suspect>();
        
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

  public String getName() {
    return config.getProperty("instance.name", String.class);
  }
  
  public void connect() {
    // @todo get name from a GUI
    Msg.ConnectRequest req = Msg.ConnectRequest.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.ConnectRequest.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(Instance.getServerId())
                   .build())
        .setClientId(Instance.getId())
        .setName(getName())
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

  public Set<Data.Location> myLocationClues() {
    return myLocations;
  }
  public Set<Data.Weapon> myWeaponClues() {
    return myWeapons;
  }
  public Set<Data.Suspect> mySuspectClues() {
    return mySuspects;
  }

  public Set<Data.Location> notebookLocationClues() {
    return notebookLocations;
  }
  public Set<Data.Weapon> notebookWeaponClues() {
    return notebookWeapons;
  }
  public Set<Data.Suspect> notebookSuspectClues() {
    return notebookSuspects;
  }

  public Data.Player getPlayerById(int clientId) {
    for (Data.Player player : state.getPlayersList()) {
      if (player.getClientId() == clientId) {
        return player;
      }
    }
    return null;
  }

  public static String locationToString(Data.Location location) {
    switch (location) {
      case LOC_NONE:          return "None";
      case LOC_STUDY:         return "Study";
      case LOC_HALL:          return "Hall";
      case LOC_LOUNGE:        return "Lounge";
      case LOC_LIBRARY:       return "Library";
      case LOC_BILLIARD_ROOM: return "Billiard Room";
      case LOC_DINING_ROOM:   return "Dining Room";
      case LOC_CONSERVATORY:  return "Conservatory";
      case LOC_BALLROOM:      return "Ballroom";
      case LOC_KITCHEN:       return "Kitchen";
      default:
        logger.error("addLocation() - got invalid location clue revealed: "
                     + location.name());
        return "Invalid";
    }
  }

  public static String weaponToString(Data.Weapon weapon) {
    switch (weapon) {
      case WPN_NONE:        return "None";
      case WPN_CANDLESTICK: return "Candlestick";
      case WPN_KNIFE:       return "Knife";
      case WPN_LEAD_PIPE:   return "Lead Pipe";
      case WPN_REVOLVER:    return "Revolver";
      case WPN_ROPE:        return "Rope";
      case WPN_WRENCH:      return "Wrench";
    }
    return "Invalid";
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
    else if (msg_type.equals(Msg.RevealClues.getDescriptor().getFullName())) {
      Msg.RevealClues reveal = (Msg.RevealClues)msg.getMessage();
      myLocations.addAll(reveal.getClues().getLocationsList());
      myWeapons.addAll(reveal.getClues().getWeaponsList());
      mySuspects.addAll(reveal.getClues().getSuspectsList());
      
      notebookLocations.addAll(reveal.getClues().getLocationsList());
      notebookWeapons.addAll(reveal.getClues().getWeaponsList());
      notebookSuspects.addAll(reveal.getClues().getSuspectsList());
    }
    else if (msg_type.equals(Msg.DisproveResponse.getDescriptor().getFullName())) {
      Msg.DisproveResponse disprove = ((Msg.DisproveResponse)msg.getMessage());
      notebookLocations.add(disprove.getResponse().getLocation());
      notebookWeapons.add(disprove.getResponse().getWeapon());
      notebookSuspects.add(disprove.getResponse().getSuspect());
    }
  }

}
