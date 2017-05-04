package com.clue.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.util.ArrayList;

import com.clue.app.Config;
import com.clue.app.Logger;
import com.clue.app.Instance;

import com.clue.proto.Data;
import com.clue.proto.Msg;
import com.clue.route.Message;
import com.clue.route.Router;

public class MovePanel extends JPanel implements ActionListener, ComponentListener {

  private static Logger logger = new Logger(MovePanel.class);
  private static Config config = Config.getInstance();
  
  private Router router = null;
  private MoveFrame frame = null;
  private Data.Location location = null;

  private ButtonGroup roomButtonGroup;
  
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

  private JRadioButton Hallway0Button;
  private JRadioButton Hallway1Button;
  private JRadioButton Hallway2Button;
  private JRadioButton Hallway3Button;
  private JRadioButton Hallway4Button;
  private JRadioButton Hallway5Button;
  private JRadioButton Hallway6Button;
  private JRadioButton Hallway7Button;
  private JRadioButton Hallway8Button;
  private JRadioButton Hallway9Button;
  private JRadioButton Hallway10Button;
  private JRadioButton Hallway11Button;

  // Submit Button
  private JButton submitButton = new JButton("Submit");

  // Panel Constructor
  public MovePanel( Router routerIn, MoveFrame frameIn ) {
    router = routerIn;
    frame = frameIn;


    // Create Room buttons
    StudyButton = new JRadioButton( "Study" );
    HallButton = new JRadioButton( "Hall" );
    LoungeButton = new JRadioButton( "Lounge" );
    LibraryButton = new JRadioButton( "Library" );
    BilliardRoomButton = new JRadioButton( "Billiard Room" );
    DiningRoomButton = new JRadioButton( "Dining Room" );
    ConservatoryButton = new JRadioButton( "Conservatory" );
    BallroomButton = new JRadioButton( "Ballroom" );
    KitchenButton = new JRadioButton( "Kitchen" );

    Hallway0Button = new JRadioButton( "Hallway 0" );
    Hallway1Button = new JRadioButton( "Hallway 1" );
    Hallway2Button = new JRadioButton( "Hallway 2" );
    Hallway3Button = new JRadioButton( "Hallway 3" );
    Hallway4Button = new JRadioButton( "Hallway 4" );
    Hallway5Button = new JRadioButton( "Hallway 5" );
    Hallway6Button = new JRadioButton( "Hallway 6" );
    Hallway7Button = new JRadioButton( "Hallway 7" );
    Hallway8Button = new JRadioButton( "Hallway 8" );
    Hallway9Button = new JRadioButton( "Hallway 9" );
    Hallway10Button = new JRadioButton( "Hallway 10" );
    Hallway11Button = new JRadioButton( "Hallway 11" );


    roomButtonGroup = new ButtonGroup();
    roomButtonGroup.add( StudyButton );
    roomButtonGroup.add( HallButton );
    roomButtonGroup.add( LoungeButton );
    roomButtonGroup.add( LibraryButton );
    roomButtonGroup.add( BilliardRoomButton );
    roomButtonGroup.add( DiningRoomButton );
    roomButtonGroup.add( ConservatoryButton );
    roomButtonGroup.add( BallroomButton );
    roomButtonGroup.add( KitchenButton );

    roomButtonGroup.add( Hallway0Button );
    roomButtonGroup.add( Hallway1Button );
    roomButtonGroup.add( Hallway2Button );
    roomButtonGroup.add( Hallway3Button );
    roomButtonGroup.add( Hallway4Button );
    roomButtonGroup.add( Hallway5Button );
    roomButtonGroup.add( Hallway6Button );
    roomButtonGroup.add( Hallway7Button );
    roomButtonGroup.add( Hallway8Button );
    roomButtonGroup.add( Hallway9Button );
    roomButtonGroup.add( Hallway10Button );
    roomButtonGroup.add( Hallway11Button );

    // Add ActionListeners for each radio button
    StudyButton.addActionListener(this);
    HallButton.addActionListener(this);
    LoungeButton.addActionListener(this);
    LibraryButton.addActionListener(this);
    BilliardRoomButton.addActionListener(this);
    DiningRoomButton.addActionListener(this);
    ConservatoryButton.addActionListener(this);
    BallroomButton.addActionListener(this);
    KitchenButton.addActionListener(this);

    Hallway0Button.addActionListener(this);
    Hallway1Button.addActionListener(this);
    Hallway2Button.addActionListener(this);
    Hallway3Button.addActionListener(this);
    Hallway4Button.addActionListener(this);
    Hallway5Button.addActionListener(this);
    Hallway6Button.addActionListener(this);
    Hallway7Button.addActionListener(this);
    Hallway8Button.addActionListener(this);
    Hallway9Button.addActionListener(this);
    Hallway10Button.addActionListener(this);
    Hallway11Button.addActionListener(this);

    submitButton.addActionListener(this);

    // By default disable submit button, until location is selected
    submitButton.setEnabled(false);

    setLayout(new GridLayout(6,5));
    this.add( StudyButton );
    this.add( Hallway0Button );
    this.add( LibraryButton );
    this.add( Hallway1Button );
    this.add( HallButton );
    this.add( Hallway2Button );
    this.add( new Panel() );
    this.add( Hallway3Button );
    this.add( new Panel() );
    this.add( Hallway4Button );
    this.add( LoungeButton );
    this.add( Hallway5Button );
    this.add( BilliardRoomButton );
    this.add( Hallway6Button );
    this.add( DiningRoomButton );
    this.add( Hallway7Button );
    this.add( new Panel() );
    this.add( Hallway8Button );
    this.add( new Panel() );
    this.add( Hallway9Button );
    this.add( ConservatoryButton );
    this.add( Hallway10Button );
    this.add( BallroomButton );
    this.add( Hallway11Button );
    this.add( KitchenButton );
    this.add( new Panel() );
    this.add( new Panel() );
    this.add( submitButton );
    this.add( new Panel() );
    this.add( new Panel() );

    
    frame.addComponentListener(this);
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
    Data.Location moveTo = null;

    // Rooms
    if ( StudyButton.isSelected() ) {
      moveTo = Data.Location.LOC_STUDY;
    }
    if ( HallButton.isSelected() ) {
      moveTo = Data.Location.LOC_HALL;
    }
    if ( LoungeButton.isSelected() ) {
      moveTo = Data.Location.LOC_LOUNGE;
    }
    if ( LibraryButton.isSelected() ) {
      moveTo = Data.Location.LOC_LIBRARY;
    }
    if ( BilliardRoomButton.isSelected() ) {
      moveTo = Data.Location.LOC_BILLIARD_ROOM;
    }
    if ( DiningRoomButton.isSelected() ) {
      moveTo = Data.Location.LOC_DINING_ROOM;
    }
    if ( ConservatoryButton.isSelected() ) {
      moveTo = Data.Location.LOC_CONSERVATORY;
    }
    if ( BallroomButton.isSelected() ) {
      moveTo = Data.Location.LOC_BALLROOM;
    }
    if ( KitchenButton.isSelected() ) {
      moveTo = Data.Location.LOC_KITCHEN;
    }

    // Hallways
    if ( Hallway0Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_0;
    }
    if ( Hallway1Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_1;
    }
    if ( Hallway2Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_2;
    }
    if ( Hallway3Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_3;
    }
    if ( Hallway4Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_4;
    }
    if ( Hallway5Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_5;
    }
    if ( Hallway6Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_6;
    }
    if ( Hallway7Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_7;
    }
    if ( Hallway8Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_8;
    }
    if ( Hallway9Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_9;
    }
    if ( Hallway10Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_10;
    }
    if ( Hallway11Button.isSelected() ) {
      moveTo = Data.Location.LOC_HALLWAY_11;
    }

    // After selecting a location, enable the submit button
    if ( moveTo != null )
    {
      submitButton.setEnabled(true);
    }

    // After submitting, send move message
    if ( e.getSource().equals(submitButton) )
    {
      Msg.PlayerMove mv = Msg.PlayerMove.newBuilder()
          .setHeader(Msg.Header.newBuilder()
                     .setMsgType(Msg.PlayerMove.getDescriptor().getFullName())
                     .setSource(Instance.getId())
                     .setDestination(Instance.getServerId())
                     .build())
          .setDestination(moveTo)
          .build();
      router.route(new Message(mv.getHeader(), mv));

      frame.setVisible(false);
    }
  }


  @Override
  public void componentHidden(ComponentEvent event) {
    logger.debug("Component hidden");
    roomButtonGroup.clearSelection();
    submitButton.setEnabled(false);
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
    Data.Player my_player = ClientState.getInstance().getPlayerById(Instance.getId());
    setLocation(my_player.getLocation());
  }

  private void setLocation(Data.Location location) {
    this.location = location;
    enableValidMoves();
  }

  public void enableValidMoves() {
    if (this.location == null) {
      return;
    }
    
    // disable all
    StudyButton.setEnabled(false);
    HallButton.setEnabled(false);
    LoungeButton.setEnabled(false);
    LibraryButton.setEnabled(false);
    BilliardRoomButton.setEnabled(false);
    DiningRoomButton.setEnabled(false);
    ConservatoryButton.setEnabled(false);
    BallroomButton.setEnabled(false);
    KitchenButton.setEnabled(false);

    Hallway0Button.setEnabled(false);
    Hallway1Button.setEnabled(false);
    Hallway2Button.setEnabled(false);
    Hallway3Button.setEnabled(false);
    Hallway4Button.setEnabled(false);
    Hallway5Button.setEnabled(false);
    Hallway6Button.setEnabled(false);
    Hallway7Button.setEnabled(false);
    Hallway8Button.setEnabled(false);
    Hallway9Button.setEnabled(false);
    Hallway10Button.setEnabled(false);
    Hallway11Button.setEnabled(false);

    JRadioButton current_button = null;
    ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>();
    switch (this.location) {
      case LOC_NONE:
        logger.error("enableValidMoves() - Unable to choose a valid move because"
                     + " the location is set to LOC_NONE!");
        break;
      case LOC_STUDY:
        current_button = StudyButton;
        buttons.add(Hallway0Button);
        buttons.add(Hallway2Button);
        buttons.add(KitchenButton); // secret passage
        break;
      case LOC_HALL:
        current_button = HallButton;
        buttons.add(Hallway1Button);
        buttons.add(Hallway4Button);
        buttons.add(ConservatoryButton); // secret passage
        break;
      case LOC_LOUNGE:
        current_button = LoungeButton;
        buttons.add(Hallway2Button);
        buttons.add(Hallway5Button);
        buttons.add(Hallway7Button);
        break;
      case LOC_LIBRARY:
        current_button = LibraryButton;
        buttons.add(Hallway0Button);
        buttons.add(Hallway1Button);
        buttons.add(Hallway3Button);
        break;
      case LOC_BILLIARD_ROOM:
        current_button = BilliardRoomButton;
        buttons.add(Hallway3Button);
        buttons.add(Hallway5Button);
        buttons.add(Hallway6Button);
        buttons.add(Hallway8Button);
        break;
      case LOC_DINING_ROOM:
        current_button = DiningRoomButton;
        buttons.add(Hallway4Button);
        buttons.add(Hallway6Button);
        buttons.add(Hallway9Button);
        break;
      case LOC_CONSERVATORY:
        current_button = ConservatoryButton;
        buttons.add(Hallway7Button);
        buttons.add(Hallway10Button);
        buttons.add(HallButton); // secret passage
        break;
      case LOC_BALLROOM:
        current_button = BallroomButton;
        buttons.add(Hallway8Button);
        buttons.add(Hallway10Button);
        buttons.add(Hallway11Button);
        break;
      case LOC_KITCHEN:
        current_button = KitchenButton;
        buttons.add(Hallway9Button);
        buttons.add(Hallway11Button);
        buttons.add(StudyButton); // secret passage
        break;
      case LOC_HALLWAY_0:
        current_button = Hallway0Button;
        buttons.add(StudyButton);
        buttons.add(LibraryButton);
        break;
      case LOC_HALLWAY_1:
        current_button = Hallway1Button;
        buttons.add(LibraryButton);
        buttons.add(HallButton);
        break;
      case LOC_HALLWAY_2:
        current_button = Hallway2Button;
        buttons.add(StudyButton);
        buttons.add(LoungeButton);
        break;
      case LOC_HALLWAY_3:
        current_button = Hallway3Button;
        buttons.add(LibraryButton);
        buttons.add(BilliardRoomButton);
        break;
      case LOC_HALLWAY_4:
        current_button = Hallway4Button;
        buttons.add(HallButton);
        buttons.add(DiningRoomButton);
        break;
      case LOC_HALLWAY_5:
        current_button = Hallway5Button;
        buttons.add(LoungeButton);
        buttons.add(BilliardRoomButton);
        break;
      case LOC_HALLWAY_6:
        current_button = Hallway6Button;
        buttons.add(BilliardRoomButton);
        buttons.add(DiningRoomButton);
        break;
      case LOC_HALLWAY_7:
        current_button = Hallway7Button;
        buttons.add(LoungeButton);
        buttons.add(ConservatoryButton);
        break;
      case LOC_HALLWAY_8:
        current_button = Hallway8Button;
        buttons.add(BilliardRoomButton);
        buttons.add(BallroomButton);
        break;
      case LOC_HALLWAY_9:
        current_button = Hallway9Button;
        buttons.add(DiningRoomButton);
        buttons.add(KitchenButton);
        break;
      case LOC_HALLWAY_10:
        current_button = Hallway10Button;
        buttons.add(ConservatoryButton);
        buttons.add(BallroomButton);
        break;
      case LOC_HALLWAY_11:
        current_button = Hallway11Button;
        buttons.add(BallroomButton);
        buttons.add(KitchenButton);
        break;
    }

    // disable the current button
    current_button.setEnabled(false);

    // enable the valid moves
    for (JRadioButton button : buttons) {
      button.setEnabled(true);
    }

    // disable all hallways that are currently occupied
    Msg.GameState state = ClientState.getInstance().gameState();
    for (Data.Player player : state.getPlayersList()) {
      JRadioButton hallway_button = null;
      switch (player.getLocation()) {
        case LOC_NONE:
          logger.error("enableValidMoves() - Unable to choose a valid move because"
                       + " the location is set to LOC_NONE!");
          break;
        case LOC_HALLWAY_0:
          hallway_button = Hallway0Button;
          break;
        case LOC_HALLWAY_1:
          hallway_button = Hallway1Button;
          break;
        case LOC_HALLWAY_2:
          hallway_button = Hallway2Button;
          break;
        case LOC_HALLWAY_3:
          hallway_button = Hallway3Button;
          break;
        case LOC_HALLWAY_4:
          hallway_button = Hallway4Button;
          break;
        case LOC_HALLWAY_5:
          hallway_button = Hallway5Button;
          break;
        case LOC_HALLWAY_6:
          hallway_button = Hallway6Button;
          break;
        case LOC_HALLWAY_7:
          hallway_button = Hallway7Button;
          break;
        case LOC_HALLWAY_8:
          hallway_button = Hallway8Button;
          break;
        case LOC_HALLWAY_9:
          hallway_button = Hallway9Button;
          break;
        case LOC_HALLWAY_10:
          hallway_button = Hallway10Button;
          break;
        case LOC_HALLWAY_11:
          hallway_button = Hallway11Button;
          break;
        default:
          break;
      }
      
      if (hallway_button != null) {
        hallway_button.setEnabled(false);
      }
    }

  }
}
