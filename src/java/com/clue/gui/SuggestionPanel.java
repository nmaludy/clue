package com.clue.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import com.clue.app.Config;
import com.clue.app.Logger;
import com.clue.app.Instance;

import com.clue.proto.Data;
import com.clue.proto.Msg;

import com.clue.route.Message;
import com.clue.route.Router;

public class SuggestionPanel extends JPanel implements ActionListener, ComponentListener {

  private static Logger logger = new Logger(SuggestionPanel.class);
  private static Config config = Config.getInstance();

  private JFrame parent = null;
  private Router router = null;

  // Button groups
  private ButtonGroup SuspectGroup;
  private ButtonGroup WeaponGroup;
  private ButtonGroup RoomGroup;
  
  // Suspect Buttons
  private JRadioButton MsScarlettButton;
  private JRadioButton ColMustardButton;
  private JRadioButton MrsWhiteButton;
  private JRadioButton MrGreenButton;
  private JRadioButton MrsPeacockButton;
  private JRadioButton ProfPlumButton;
  
  // Weapon buttons    
  private JRadioButton CandlestickButton;
  private JRadioButton KnifeButton;
  private JRadioButton LeadPipeButton;
  private JRadioButton RevolverButton;
  private JRadioButton RopeButton;
  private JRadioButton WrenchButton;
  
  // Room buttons    
  private JRadioButton StudyButton;
  private JRadioButton HallButton;
  private JRadioButton LoungeButton;
  private JRadioButton LibraryButton;
  private JRadioButton BilliardRoomButton;
  private JRadioButton DiningRoomButton;
  private JRadioButton ConservatoryButton;
  private JRadioButton BallroomButton;
  private JRadioButton KitchenButton;
    
  // Submit Buttons
  private JButton suggestionButton = new JButton("Submit Suggestion");
  private JButton accusationButton = new JButton("Submit Accusation");

  // Panel Constructor
  public SuggestionPanel( JFrame parent, Router routerIn )
  {
    this.parent = parent;
    this.router = routerIn;
    
    // Create Suspect buttons
    MsScarlettButton = new JRadioButton( "Ms. Scarlett" );
    ColMustardButton = new JRadioButton( "Col. Mustard" );
    MrsWhiteButton   = new JRadioButton( "Mrs. White" );
    MrGreenButton    = new JRadioButton( "Mr. Green" );
    MrsPeacockButton = new JRadioButton( "Mrs. Peacock" );
    ProfPlumButton   = new JRadioButton( "Prof. Plum" );
    
    // Create Weapon buttons
    CandlestickButton = new JRadioButton( "Candlestick" );
    KnifeButton       = new JRadioButton( "Knife" );
    LeadPipeButton    = new JRadioButton( "Lead Pipe" );
    RevolverButton    = new JRadioButton( "Revolver" );
    RopeButton        = new JRadioButton( "Rope" );
    WrenchButton      = new JRadioButton( "Wrench" );      

    // Create Room buttons
    StudyButton        = new JRadioButton( "Study" );
    HallButton         = new JRadioButton( "Hall" );
    LoungeButton       = new JRadioButton( "Lounge" );
    LibraryButton      = new JRadioButton( "Library" );
    BilliardRoomButton = new JRadioButton( "Billiard Room" );
    DiningRoomButton   = new JRadioButton( "Dining Room" );
    ConservatoryButton = new JRadioButton( "Conservatory" );
    BallroomButton     = new JRadioButton( "Ballroom" );
    KitchenButton      = new JRadioButton( "Kitchen" );

    // Create a button group & add buttons
    SuspectGroup = new ButtonGroup();
    SuspectGroup.add( MsScarlettButton );
    SuspectGroup.add( ColMustardButton );
    SuspectGroup.add( MrsWhiteButton );
    SuspectGroup.add( MrGreenButton );
    SuspectGroup.add( MrsPeacockButton );
    SuspectGroup.add( ProfPlumButton );
    
    WeaponGroup = new ButtonGroup();
    WeaponGroup.add( CandlestickButton );
    WeaponGroup.add( KnifeButton );
    WeaponGroup.add( LeadPipeButton );
    WeaponGroup.add( RevolverButton );
    WeaponGroup.add( RopeButton );
    WeaponGroup.add( WrenchButton );
    
    RoomGroup = new ButtonGroup();
    RoomGroup.add( StudyButton );
    RoomGroup.add( HallButton );
    RoomGroup.add( LoungeButton );
    RoomGroup.add( LibraryButton );
    RoomGroup.add( BilliardRoomButton );
    RoomGroup.add( DiningRoomButton );
    RoomGroup.add( ConservatoryButton );
    RoomGroup.add( BallroomButton );
    RoomGroup.add( KitchenButton );

    // Create the button border
    Border buttonSuspectBorder = BorderFactory.createEtchedBorder();
    buttonSuspectBorder = BorderFactory.createTitledBorder( buttonSuspectBorder, "Suspect" );
    
    Border buttonWeaponBorder = BorderFactory.createEtchedBorder();
    buttonWeaponBorder = BorderFactory.createTitledBorder( buttonWeaponBorder, "Weapon" );
    
    Border buttonRoomBorder = BorderFactory.createEtchedBorder();
    buttonRoomBorder = BorderFactory.createTitledBorder( buttonRoomBorder, "Room" );
    

    // Create a button panel & add buttons
    JPanel buttonSuspectPanel = new JPanel();
    buttonSuspectPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
    buttonSuspectPanel.add( MrGreenButton );
    buttonSuspectPanel.add( MrsWhiteButton );
    buttonSuspectPanel.add( ColMustardButton );
    buttonSuspectPanel.add( MsScarlettButton );
    buttonSuspectPanel.add( MrsPeacockButton );
    buttonSuspectPanel.add( ProfPlumButton );
    buttonSuspectPanel.setBorder( buttonSuspectBorder );
    
    JPanel buttonWeaponPanel = new JPanel();
    buttonWeaponPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
    buttonWeaponPanel.add( KnifeButton );
    buttonWeaponPanel.add( CandlestickButton );
    buttonWeaponPanel.add( RevolverButton );
    buttonWeaponPanel.add( LeadPipeButton );
    buttonWeaponPanel.add( RopeButton );
    buttonWeaponPanel.add( WrenchButton );
    buttonWeaponPanel.setBorder( buttonWeaponBorder );

    JPanel buttonRoomPanel = new JPanel();
    buttonRoomPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
    buttonRoomPanel.add( StudyButton );
    buttonRoomPanel.add( HallButton );
    buttonRoomPanel.add( LoungeButton );
    buttonRoomPanel.add( LibraryButton );
    buttonRoomPanel.add( BilliardRoomButton );
    buttonRoomPanel.add( DiningRoomButton );
    buttonRoomPanel.add( ConservatoryButton );
    buttonRoomPanel.add( BallroomButton );
    buttonRoomPanel.add( KitchenButton );
    buttonRoomPanel.setBorder( buttonRoomBorder );

    JPanel submitButtonPanel = new JPanel();
    submitButtonPanel.setLayout( new FlowLayout( FlowLayout.CENTER ) );
    submitButtonPanel.add( suggestionButton );
    submitButtonPanel.add( accusationButton );
    
    // Add ActionListeners for each radio button
    MsScarlettButton.addActionListener(this);
    ColMustardButton.addActionListener(this);
    MrsWhiteButton.addActionListener(this);
    MrGreenButton.addActionListener(this);
    MrsPeacockButton.addActionListener(this);
    ProfPlumButton.addActionListener(this);
    
    CandlestickButton.addActionListener(this);
    KnifeButton.addActionListener(this);
    LeadPipeButton.addActionListener(this);
    RevolverButton.addActionListener(this);
    RopeButton.addActionListener(this);
    WrenchButton.addActionListener(this);
    
    StudyButton.addActionListener(this);
    HallButton.addActionListener(this);
    LoungeButton.addActionListener(this);
    LibraryButton.addActionListener(this);
    BilliardRoomButton.addActionListener(this);
    DiningRoomButton.addActionListener(this);
    ConservatoryButton.addActionListener(this);
    BallroomButton.addActionListener(this);
    KitchenButton.addActionListener(this);

    suggestionButton.addActionListener(this);
    accusationButton.addActionListener(this);
        
    //Default Submit Buttons to disabled
    suggestionButton.setEnabled(false);
    accusationButton.setEnabled(false);
    
    // Add panels to the main panel and arrange layout
    this.setLayout(new GridLayout(4,1));
    //this.setLayout( new BorderLayout() );
    this.add( buttonSuspectPanel );
    this.add( buttonWeaponPanel );
    this.add( buttonRoomPanel );
    this.add( submitButtonPanel );
    
    parent.addComponentListener(this);
  }
    

  /**
   * This method handles events caused by the user
   * selecting or deselecting radio buttons or
   * checkboxes
   * 
   * @param e is an ActionEvent caused generated by an action listener
   */
  public void actionPerformed(ActionEvent e)
  {
    boolean selectedRoom    = false;
    boolean selectedWeapon  = false;
    boolean selectedSuspect = false;
      
    com.clue.proto.Data.Location sRoom    = null;
    com.clue.proto.Data.Weapon   sWeapon  = null;
    com.clue.proto.Data.Suspect  sSuspect = null;
        
    // Determine message based on selection
      
    // Rooms
    if (StudyButton.isSelected() )
    {
      sRoom = Data.Location.LOC_STUDY;
      selectedRoom = true;
    }
        
    if (HallButton.isSelected() )
    {
      sRoom = Data.Location.LOC_HALL;
      selectedRoom = true;
    }
        
    if (LoungeButton.isSelected() )
    {
      sRoom = Data.Location.LOC_LOUNGE;
      selectedRoom = true;
    }

    if (LibraryButton.isSelected() )
    {
      sRoom = Data.Location.LOC_LIBRARY;
      selectedRoom = true;
    }
        
    if (BilliardRoomButton.isSelected() )
    {
      sRoom = Data.Location.LOC_BILLIARD_ROOM;
      selectedRoom = true;
    }
        
    if (DiningRoomButton.isSelected() )
    {
      sRoom = Data.Location.LOC_DINING_ROOM;
      selectedRoom = true;
    }

    if (ConservatoryButton.isSelected() )
    {
      sRoom = Data.Location.LOC_CONSERVATORY;
      selectedRoom = true;
    }
        
    if (BallroomButton.isSelected() )
    {
      sRoom = Data.Location.LOC_BALLROOM;
      selectedRoom = true;
    }
        
    if (KitchenButton.isSelected() )
    {
      sRoom = Data.Location.LOC_KITCHEN;
      selectedRoom = true;
    }
        
    // Weapons
    if (CandlestickButton.isSelected() )
    {
      sWeapon = Data.Weapon.WPN_CANDLESTICK;
      selectedWeapon = true;
    }
        
    if (KnifeButton.isSelected() )
    {
      sWeapon = Data.Weapon.WPN_KNIFE;
      selectedWeapon = true;
    }
        
    if (LeadPipeButton.isSelected() )
    {
      sWeapon = Data.Weapon.WPN_LEAD_PIPE;
      selectedWeapon = true;
    }
        
    if (RevolverButton.isSelected() )
    {
      sWeapon = Data.Weapon.WPN_REVOLVER;
      selectedWeapon = true;
    }

    if (RopeButton.isSelected() )
    {
      sWeapon = Data.Weapon.WPN_ROPE;
      selectedWeapon = true;
    }
        
    if (WrenchButton.isSelected() )
    {
      sWeapon = Data.Weapon.WPN_WRENCH;
      selectedWeapon = true;
    }
        
    // Suspects
    if (MsScarlettButton.isSelected() )
    {
      sSuspect = Data.Suspect.SUS_MISS_SCARLETT;
      selectedSuspect = true;
    }
        
    if (ColMustardButton.isSelected() )
    {
      sSuspect = Data.Suspect.SUS_COL_MUSTARD;
      selectedSuspect = true;
    }
        
    if (MrsWhiteButton.isSelected() )
    {
      sSuspect = Data.Suspect.SUS_MRS_WHITE;
      selectedSuspect = true;
    }

    if (MrGreenButton.isSelected() )
    {
      sSuspect = Data.Suspect.SUS_MR_GREEN;
      selectedSuspect = true;
    }
        
    if (MrsPeacockButton.isSelected() )
    {
      sSuspect = Data.Suspect.SUS_MRS_PEACOCK;
      selectedSuspect = true;
    }
        
    if (ProfPlumButton.isSelected() )
    {
      sSuspect = Data.Suspect.SUS_PROF_PLUM;
      selectedSuspect = true;
    }
        
    // If each type of clue is selected
    if (selectedRoom && selectedWeapon && selectedSuspect)
    {
      suggestionButton.setEnabled(true);
      accusationButton.setEnabled(true);
    }

    // Send Suggestion
    if ( e.getSource().equals(suggestionButton) )
    {
      Msg.PlayerSuggestion sg = Msg.PlayerSuggestion.newBuilder()
          .setHeader(Msg.Header.newBuilder()
                     .setMsgType(Msg.PlayerSuggestion.getDescriptor().getFullName())
                     .setSource(Instance.getId())
                     .setDestination(Instance.getServerId())
                     .build())
          .setSolution(Data.Solution.newBuilder()
                       .setClientId(Instance.getId())
                       .setWeapon(sWeapon)
                       .setSuspect(sSuspect)
                       .setLocation(sRoom)
                       .build())
          .build();
      router.route(new Message(sg.getHeader(), sg));
      parent.setVisible(false);
    }
        
    // Send Accusation
    if ( e.getSource().equals(accusationButton) )
    {
      Msg.PlayerAccusation sg = Msg.PlayerAccusation.newBuilder()
          .setHeader(Msg.Header.newBuilder()
                     .setMsgType(Msg.PlayerAccusation.getDescriptor().getFullName())
                     .setSource(Instance.getId())
                     .setDestination(Instance.getServerId())
                     .build())
          .setSolution(Data.Solution.newBuilder()
                       .setClientId(Instance.getId())
                       .setWeapon(sWeapon)
                       .setSuspect(sSuspect)
                       .setLocation(sRoom)
                       .build())
          .build();
      router.route(new Message(sg.getHeader(), sg));
      parent.setVisible(false);
    }
        
  }

  @Override
  public void componentHidden(ComponentEvent event) {
    logger.debug("Component hidden");
    SuspectGroup.clearSelection();
    WeaponGroup.clearSelection();
    RoomGroup.clearSelection();
    suggestionButton.setEnabled(false);
    accusationButton.setEnabled(false);
  }

  @Override
  public void componentMoved(ComponentEvent event) {
  }

  @Override
  public void componentResized(ComponentEvent event) {
  }

  @Override
  public void componentShown(ComponentEvent event) {
    logger.debug("Component shown");
    for (Data.Location location : ClientState.getInstance().notebookLocationClues()) {
      switch (location) {
        case LOC_NONE:
          break;
        case LOC_STUDY:
          StudyButton.setEnabled(false);
          break;
        case LOC_HALL:
          HallButton.setEnabled(false);
          break;
        case LOC_LOUNGE:
          LoungeButton.setEnabled(false);
          break;
        case LOC_LIBRARY:
          LibraryButton.setEnabled(false);
          break;
        case LOC_BILLIARD_ROOM:
          BilliardRoomButton.setEnabled(false);
          break;
        case LOC_DINING_ROOM:
          DiningRoomButton.setEnabled(false);
          break;
        case LOC_CONSERVATORY:
          ConservatoryButton.setEnabled(false);
          break;
        case LOC_BALLROOM:
          BallroomButton.setEnabled(false);
          break;
        case LOC_KITCHEN:
          KitchenButton.setEnabled(false);
          break;
        default:
          logger.error("componentShown() - got invalid location clue revealed: "
                       + location.name());
          break;
      }
    }
    
    for (Data.Weapon weapon : ClientState.getInstance().notebookWeaponClues()) {
      switch (weapon) {
        case WPN_NONE:
          break;
        case WPN_CANDLESTICK:
          CandlestickButton.setEnabled(false);
          break;
        case WPN_KNIFE:
          KnifeButton.setEnabled(false);
          break;
        case WPN_LEAD_PIPE:
          LeadPipeButton.setEnabled(false);
          break;
        case WPN_REVOLVER:
          RevolverButton.setEnabled(false);
          break;
        case WPN_ROPE:
          RopeButton.setEnabled(false);
          break;
        case WPN_WRENCH:
          WrenchButton.setEnabled(false);
          break;
      }
    }
    
    for (Data.Suspect suspect : ClientState.getInstance().notebookSuspectClues()) {
      switch (suspect) {
        case SUS_NONE:
          break;
        case SUS_MISS_SCARLETT:
          MsScarlettButton.setEnabled(false);
          break;
        case SUS_COL_MUSTARD:
          ColMustardButton.setEnabled(false);
          break;
        case SUS_MRS_WHITE:
          MrsWhiteButton.setEnabled(false);
          break;
        case SUS_MR_GREEN:
          MrGreenButton.setEnabled(false);
          break;
        case SUS_MRS_PEACOCK:
          MrsPeacockButton.setEnabled(false);
          break;
        case SUS_PROF_PLUM:
          ProfPlumButton.setEnabled(false);
          break;
      }
    }
  }

}
