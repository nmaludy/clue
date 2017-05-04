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

public class DisprovePanel extends JPanel implements ActionListener, ComponentListener {

  private static Logger logger = new Logger(DisprovePanel.class);
  private static Config config = Config.getInstance();

  private JFrame parent = null;
  private Router router = null;

  // Label
  private JLabel InstructionsLabel;
  
  // Button groups
  private ButtonGroup ButtonGroup;

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
  private JButton disproveButton = new JButton("Disprove");
  private JButton passButton = new JButton("Pass");

  private Msg.DisproveRequest request;

  // Panel Constructor
  public DisprovePanel( JFrame parent, Router routerIn )
  {
    this.parent = parent;
    this.router = routerIn;
    this.request = null;

    // Create Labels
    InstructionsLabel = new JLabel("", SwingConstants.CENTER);
    
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
    ButtonGroup = new ButtonGroup();
    ButtonGroup.add( MsScarlettButton );
    ButtonGroup.add( ColMustardButton );
    ButtonGroup.add( MrsWhiteButton );
    ButtonGroup.add( MrGreenButton );
    ButtonGroup.add( MrsPeacockButton );
    ButtonGroup.add( ProfPlumButton );

    ButtonGroup.add( CandlestickButton );
    ButtonGroup.add( KnifeButton );
    ButtonGroup.add( LeadPipeButton );
    ButtonGroup.add( RevolverButton );
    ButtonGroup.add( RopeButton );
    ButtonGroup.add( WrenchButton );

    ButtonGroup.add( StudyButton );
    ButtonGroup.add( HallButton );
    ButtonGroup.add( LoungeButton );
    ButtonGroup.add( LibraryButton );
    ButtonGroup.add( BilliardRoomButton );
    ButtonGroup.add( DiningRoomButton );
    ButtonGroup.add( ConservatoryButton );
    ButtonGroup.add( BallroomButton );
    ButtonGroup.add( KitchenButton );

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
    submitButtonPanel.add( disproveButton );
    submitButtonPanel.add( passButton );

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

    disproveButton.addActionListener(this);
    passButton.addActionListener(this);

    //Default Submit Buttons to disabled
    disproveButton.setEnabled(false);
    passButton.setEnabled(false);

    // Add panels to the main panel and arrange layout
    this.setLayout(new GridLayout(5,1));
    //this.setLayout( new BorderLayout() );
    this.add( InstructionsLabel );
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
    Data.Location sRoom    = Data.Location.LOC_NONE;
    Data.Weapon   sWeapon  = Data.Weapon.WPN_NONE;
    Data.Suspect  sSuspect = Data.Suspect.SUS_NONE;

    // Determine message based on selection

    // Rooms
    if (StudyButton.isSelected()) {
      sRoom = Data.Location.LOC_STUDY;
    }
    if (HallButton.isSelected()) {
      sRoom = Data.Location.LOC_HALL;
    }
    if (LoungeButton.isSelected()) {
      sRoom = Data.Location.LOC_LOUNGE;
    }
    if (LibraryButton.isSelected()) {
      sRoom = Data.Location.LOC_LIBRARY;
    }
    if (BilliardRoomButton.isSelected()) {
      sRoom = Data.Location.LOC_BILLIARD_ROOM;
    }
    if (DiningRoomButton.isSelected()) {
      sRoom = Data.Location.LOC_DINING_ROOM;
    }
    if (ConservatoryButton.isSelected()) {
      sRoom = Data.Location.LOC_CONSERVATORY;
    }
    if (BallroomButton.isSelected()) {
      sRoom = Data.Location.LOC_BALLROOM;
    }
    if (KitchenButton.isSelected()) {
      sRoom = Data.Location.LOC_KITCHEN;
    }

    // Weapons
    if (CandlestickButton.isSelected()) {
      sWeapon = Data.Weapon.WPN_CANDLESTICK;
    }
    if (KnifeButton.isSelected()) {
      sWeapon = Data.Weapon.WPN_KNIFE;
    }
    if (LeadPipeButton.isSelected()) {
      sWeapon = Data.Weapon.WPN_LEAD_PIPE;
    }
    if (RevolverButton.isSelected()) {
      sWeapon = Data.Weapon.WPN_REVOLVER;
    }
    if (RopeButton.isSelected()) {
      sWeapon = Data.Weapon.WPN_ROPE;
    }
    if (WrenchButton.isSelected()) {
      sWeapon = Data.Weapon.WPN_WRENCH;
    }

    // Suspects
    if (MsScarlettButton.isSelected()) {
      sSuspect = Data.Suspect.SUS_MISS_SCARLETT;
    }
    if (ColMustardButton.isSelected()) {
      sSuspect = Data.Suspect.SUS_COL_MUSTARD;
    }
    if (MrsWhiteButton.isSelected()) {
      sSuspect = Data.Suspect.SUS_MRS_WHITE;
    }
    if (MrGreenButton.isSelected()) {
      sSuspect = Data.Suspect.SUS_MR_GREEN;
    }
    if (MrsPeacockButton.isSelected()) {
      sSuspect = Data.Suspect.SUS_MRS_PEACOCK;
    }
    if (ProfPlumButton.isSelected()) {
      sSuspect = Data.Suspect.SUS_PROF_PLUM;
    }

    // If each type of clue is selected
    if (sRoom    != Data.Location.LOC_NONE ||
        sWeapon  != Data.Weapon.WPN_NONE ||
        sSuspect != Data.Suspect.SUS_NONE) {
      disproveButton.setEnabled(true);
    }

    // Send Disprove
    if ( e.getSource().equals(disproveButton) ||
         e.getSource().equals(passButton) ) {
      Msg.DisproveResponse msg = Msg.DisproveResponse.newBuilder()
          .setHeader(Msg.Header.newBuilder()
                     .setMsgType(Msg.DisproveResponse.getDescriptor().getFullName())
                     .setSource(Instance.getId())
                     .setDestination(Instance.getServerId())
                     .build())
          .setGuess(request.getGuess().toBuilder().build())
          .setResponse(Data.Solution.newBuilder()
                       .setClientId(Instance.getId())
                       .setWeapon(sWeapon)
                       .setSuspect(sSuspect)
                       .setLocation(sRoom)
                       .build())
          .build();
      router.route(new Message(msg.getHeader(), msg));
      parent.setVisible(false);
    }
  }

  public void setDisproveRequest(Msg.DisproveRequest req) {
    this.request = req;
    
    // Update disproval section
    MsScarlettButton.setEnabled(false);
    ColMustardButton.setEnabled(false);
    MrsWhiteButton.setEnabled(false);
    MrGreenButton.setEnabled(false);
    MrsPeacockButton.setEnabled(false);
    ProfPlumButton.setEnabled(false);

    CandlestickButton.setEnabled(false);
    KnifeButton.setEnabled(false);
    LeadPipeButton.setEnabled(false);
    RevolverButton.setEnabled(false);
    RopeButton.setEnabled(false);
    WrenchButton.setEnabled(false);

    StudyButton.setEnabled(false);
    HallButton.setEnabled(false);
    LoungeButton.setEnabled(false);
    LibraryButton.setEnabled(false);
    BilliardRoomButton.setEnabled(false);
    DiningRoomButton.setEnabled(false);
    ConservatoryButton.setEnabled(false);
    BallroomButton.setEnabled(false);
    KitchenButton.setEnabled(false);

    boolean b_can_disprove = false;
    
    Data.Location location = req.getGuess().getLocation();
    if (ClientState.getInstance().myLocationClues().contains(location)) {
      switch (location) {
        case LOC_NONE:
          break;
        case LOC_STUDY:
          StudyButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case LOC_HALL:
          HallButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case LOC_LOUNGE:
          LoungeButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case LOC_LIBRARY:
          LibraryButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case LOC_BILLIARD_ROOM:
          BilliardRoomButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case LOC_DINING_ROOM:
          DiningRoomButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case LOC_CONSERVATORY:
          ConservatoryButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case LOC_BALLROOM:
          BallroomButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case LOC_KITCHEN:
          KitchenButton.setEnabled(true);
          b_can_disprove = true;
          break;
        default:
          logger.error("componentShown() - got invalid location clue revealed: "
                       + location.name());
          break;
      }
    }

    Data.Weapon weapon = req.getGuess().getWeapon();
    if (ClientState.getInstance().myWeaponClues().contains(weapon)) {
      switch (weapon) {
        case WPN_NONE:
          break;
        case WPN_CANDLESTICK:
          CandlestickButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case WPN_KNIFE:
          KnifeButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case WPN_LEAD_PIPE:
          LeadPipeButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case WPN_REVOLVER:
          RevolverButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case WPN_ROPE:
          RopeButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case WPN_WRENCH:
          WrenchButton.setEnabled(true);
          b_can_disprove = true;
          break;
      }
    }

    Data.Suspect suspect = req.getGuess().getSuspect();
    if (ClientState.getInstance().mySuspectClues().contains(suspect)) {
      switch (suspect) {
        case SUS_NONE:
          break;
        case SUS_MISS_SCARLETT:
          MsScarlettButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case SUS_COL_MUSTARD:
          ColMustardButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case SUS_MRS_WHITE:
          MrsWhiteButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case SUS_MR_GREEN:
          MrGreenButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case SUS_MRS_PEACOCK:
          MrsPeacockButton.setEnabled(true);
          b_can_disprove = true;
          break;
        case SUS_PROF_PLUM:
          ProfPlumButton.setEnabled(true);
          b_can_disprove = true;
          break;
      }
    }

    String instructions = "";
    if (b_can_disprove) {
      instructions = "Please disprove their suggestion by selecting a clue.";
    } else {
      instructions = "You're unable to disprove their suggestion, please pass your turn.";
      passButton.setEnabled(true);
    }
    
    // Update Instructions label
    Data.Player player = ClientState.getInstance().getPlayerById(req.getGuess().getClientId());
    String message = "Player '" + player.getName() + "' has made a suggestion!";
    InstructionsLabel.setText("<html><div style='text-align: center;'>"
                              + message + "<br>"
                              + instructions
                              + "</div></html>");
  }

  @Override
  public void componentHidden(ComponentEvent event) {
    logger.debug("Component hidden");
    ButtonGroup.clearSelection();
    disproveButton.setEnabled(false);
    passButton.setEnabled(false);
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
  }

}
