package com.clue.gui;

import java.awt.*;
import javax.swing.*;

import com.clue.app.Config;
import com.clue.app.Logger;

import com.clue.proto.Data;
import com.clue.proto.Msg;

import com.clue.route.Message;
import com.clue.route.MessageHandler;
import com.clue.route.Router;
import com.clue.route.SubscriptionAllIncoming;

public class NotebookPanel extends JPanel implements MessageHandler
{
  private static Logger logger = new Logger(NotebookPanel.class);
  private static Config config = Config.getInstance();


  // Heading Labels
  private JLabel NotebookLabel;
  private JLabel SuspectsLabel;
  private JLabel WeaponsLabel;
  private JLabel RoomsLabel;

  // Rooms
  private JLabel StudyLabel;
  private JLabel HallLabel;
  private JLabel LoungeLabel;
  private JLabel LibraryLabel;
  private JLabel BilliardRoomLabel;
  private JLabel DiningRoomLabel;
  private JLabel ConservatoryLabel;
  private JLabel BallroomLabel;
  private JLabel KitchenLabel;

  private JCheckBox StudyCheckBox;
  private JCheckBox HallCheckBox;
  private JCheckBox LoungeCheckBox;
  private JCheckBox LibraryCheckBox;
  private JCheckBox BilliardRoomCheckBox;
  private JCheckBox DiningRoomCheckBox;
  private JCheckBox ConservatoryCheckBox;
  private JCheckBox BallroomCheckBox;
  private JCheckBox KitchenCheckBox;


  // Weapons
  private JLabel KnifeLabel;
  private JLabel CandlestickLabel;
  private JLabel RevolverLabel;
  private JLabel LeadPipeLabel;
  private JLabel RopeLabel;
  private JLabel WrenchLabel;

  private JCheckBox KnifeCheckBox;
  private JCheckBox CandlestickCheckBox;
  private JCheckBox RevolverCheckBox;
  private JCheckBox LeadPipeCheckBox;
  private JCheckBox RopeCheckBox;
  private JCheckBox WrenchCheckBox;

  // Suspects
  private JLabel MrGreenLabel;
  private JLabel MrsWhiteLabel;
  private JLabel ColMustardLabel;
  private JLabel MissScarletLabel;
  private JLabel MrsPeacockLabel;
  private JLabel ProfPlumLabel;

  private JCheckBox MrGreenCheckBox;
  private JCheckBox MrsWhiteCheckBox;
  private JCheckBox ColMustardCheckBox;
  private JCheckBox MissScarletCheckBox;
  private JCheckBox MrsPeacockCheckBox;
  private JCheckBox ProfPlumCheckBox;

  public NotebookPanel()
  {
    // Layout for notebook
    setLayout(new GridLayout(25,1));

    // Create Labels
    // Headings
    NotebookLabel = new JLabel( "Notebook   ", SwingConstants.CENTER );
    SuspectsLabel = new JLabel( "Suspects:" );
    WeaponsLabel  = new JLabel( "Weapons:" );
    RoomsLabel    = new JLabel( "Rooms:" );

    // Rooms
    StudyLabel        = new JLabel( "Study" );
    HallLabel         = new JLabel( "Hall" );
    LoungeLabel       = new JLabel( "Lounge" );
    LibraryLabel      = new JLabel( "Library" );
    BilliardRoomLabel = new JLabel( "Billiard Room" );
    DiningRoomLabel   = new JLabel( "Dining Room" );
    ConservatoryLabel = new JLabel( "Conservatory" );
    BallroomLabel     = new JLabel( "Ballroom" );
    KitchenLabel      = new JLabel( "Kitchen" );

    StudyCheckBox        = new JCheckBox();
    HallCheckBox         = new JCheckBox();
    LoungeCheckBox       = new JCheckBox();
    LibraryCheckBox      = new JCheckBox();
    BilliardRoomCheckBox = new JCheckBox();
    DiningRoomCheckBox   = new JCheckBox();
    ConservatoryCheckBox = new JCheckBox();
    BallroomCheckBox     = new JCheckBox();
    KitchenCheckBox      = new JCheckBox();

    StudyCheckBox.setEnabled(false);
    HallCheckBox.setEnabled(false);
    LoungeCheckBox.setEnabled(false);
    LibraryCheckBox.setEnabled(false);
    BilliardRoomCheckBox.setEnabled(false);
    DiningRoomCheckBox.setEnabled(false);
    ConservatoryCheckBox.setEnabled(false);
    BallroomCheckBox.setEnabled(false);
    KitchenCheckBox.setEnabled(false);


    // Weapons
    KnifeLabel       = new JLabel( "Knife" );
    CandlestickLabel = new JLabel( "Candlestick" );
    RevolverLabel    = new JLabel( "Revolver" );
    LeadPipeLabel    = new JLabel( "Lead Pipe" );
    RopeLabel        = new JLabel( "Rope" );
    WrenchLabel      = new JLabel( "Wrench" );

    KnifeCheckBox       = new JCheckBox();
    CandlestickCheckBox = new JCheckBox();
    RevolverCheckBox    = new JCheckBox();
    LeadPipeCheckBox    = new JCheckBox();
    RopeCheckBox        = new JCheckBox();
    WrenchCheckBox      = new JCheckBox();

    KnifeCheckBox.setEnabled(false);
    CandlestickCheckBox.setEnabled(false);
    RevolverCheckBox.setEnabled(false);
    LeadPipeCheckBox.setEnabled(false);
    RopeCheckBox.setEnabled(false);
    WrenchCheckBox.setEnabled(false);


    // Suspects
    MrGreenLabel     = new JLabel( "Mr. Green" );
    MrsWhiteLabel    = new JLabel( "Mrs. White" );
    ColMustardLabel  = new JLabel( "Col. Mustard" );
    MissScarletLabel = new JLabel( "Miss. Scarlet" );
    MrsPeacockLabel  = new JLabel( "Mrs. Peacock" );
    ProfPlumLabel    = new JLabel( "Prof. Plum" );

    MrGreenCheckBox     = new JCheckBox();
    MrsWhiteCheckBox    = new JCheckBox();
    ColMustardCheckBox  = new JCheckBox();
    MissScarletCheckBox = new JCheckBox();
    MrsPeacockCheckBox  = new JCheckBox();
    ProfPlumCheckBox    = new JCheckBox();

    MrGreenCheckBox.setEnabled(false);
    MrsWhiteCheckBox.setEnabled(false);
    ColMustardCheckBox.setEnabled(false);
    MissScarletCheckBox.setEnabled(false);
    MrsPeacockCheckBox.setEnabled(false);
    ProfPlumCheckBox.setEnabled(false);

    // Set Fonts for headings
    NotebookLabel.setFont(new Font("Serif", Font.BOLD, 24));
    SuspectsLabel.setFont(new Font("Serif", Font.BOLD, 18));
    WeaponsLabel.setFont(new Font("Serif", Font.BOLD, 18));
    RoomsLabel.setFont(new Font("Serif", Font.BOLD, 18));

    // Add labels to panel
    add(NotebookLabel);

    add(SuspectsLabel);

    add( createCluePanel(MrGreenCheckBox, MrGreenLabel) );
    add( createCluePanel(MrsWhiteCheckBox, MrsWhiteLabel) );
    add( createCluePanel(ColMustardCheckBox, ColMustardLabel) );
    add( createCluePanel(MissScarletCheckBox, MissScarletLabel) );
    add( createCluePanel(MrsPeacockCheckBox, MrsPeacockLabel) );
    add( createCluePanel(ProfPlumCheckBox, ProfPlumLabel) );

    add(WeaponsLabel);

    add( createCluePanel(KnifeCheckBox, KnifeLabel) );
    add( createCluePanel(CandlestickCheckBox, CandlestickLabel) );
    add( createCluePanel(RevolverCheckBox, RevolverLabel) );
    add( createCluePanel(LeadPipeCheckBox, LeadPipeLabel) );
    add( createCluePanel(RopeCheckBox, RopeLabel) );
    add( createCluePanel(WrenchCheckBox, WrenchLabel) );

    add(RoomsLabel);

    add( createCluePanel(StudyCheckBox, StudyLabel) );
    add( createCluePanel(HallCheckBox, HallLabel) );
    add( createCluePanel(LoungeCheckBox, LoungeLabel) );
    add( createCluePanel(LibraryCheckBox, LibraryLabel) );
    add( createCluePanel(BilliardRoomCheckBox, BilliardRoomLabel) );
    add( createCluePanel(DiningRoomCheckBox, DiningRoomLabel) );
    add( createCluePanel(ConservatoryCheckBox, ConservatoryLabel) );
    add( createCluePanel(BallroomCheckBox, BallroomLabel) );
    add( createCluePanel(KitchenCheckBox, KitchenLabel) );

    Router.getInstance().register(new SubscriptionAllIncoming(), this);
  }

  private JPanel createCluePanel(JCheckBox checkBox, JLabel label) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    panel.add(checkBox);
    panel.add(label);
    return panel;
  }


  // Function for striking through text of clues that are known to not be in case file
  public void strikeThrough()
  {
    ProfPlumLabel.setText("<html><strike>Prof. Plum</strike></html>");
    RevolverLabel.setText("<html><strike>Revolver</strike></html>");
    WrenchLabel.setText("<html><strike>Wrench</strike></html>");
    LoungeLabel.setText("<html><strike>Lounge</strike></html>");
    BallroomLabel.setText("<html><strike>Ballroom</strike></html>");
  }


  public void addClues(Data.Clues clues) {
    // Rooms
    for (Data.Location location : clues.getLocationsList()) {
      logger.debug("addClues() - revelaed location: " + location.name());
      switch (location) {
        case LOC_NONE:
          break;
        case LOC_STUDY:
          StudyLabel.setEnabled(false);
          StudyCheckBox.setSelected(true);
          break;
        case LOC_HALL:
          HallLabel.setEnabled(false);
          HallCheckBox.setSelected(true);
          break;
        case LOC_LOUNGE:
          LoungeLabel.setEnabled(false);
          LoungeCheckBox.setSelected(true);
          break;
        case LOC_LIBRARY:
          LibraryLabel.setEnabled(false);
          LibraryCheckBox.setSelected(true);
          break;
        case LOC_BILLIARD_ROOM:
          BilliardRoomLabel.setEnabled(false);
          BilliardRoomCheckBox.setSelected(true);
          break;
        case LOC_DINING_ROOM:
          DiningRoomLabel.setEnabled(false);
          DiningRoomCheckBox.setSelected(true);
          break;
        case LOC_CONSERVATORY:
          ConservatoryLabel.setEnabled(false);
          ConservatoryCheckBox.setSelected(true);
          break;
        case LOC_BALLROOM:
          BallroomLabel.setEnabled(false);
          BallroomCheckBox.setSelected(true);
          break;
        case LOC_KITCHEN:
          KitchenLabel.setEnabled(false);
          KitchenCheckBox.setSelected(true);
          break;
        default:
          logger.error("addClues() - got invalid location clue revealed: "
                       + location.name());
          break;
      }
    }

    // Weapons
    for (Data.Weapon weapon : clues.getWeaponsList()) {
      logger.debug("addClues() - revelaed weapon: " + weapon.name());
      switch (weapon) {
        case WPN_NONE:
          break;
        case WPN_CANDLESTICK:
          CandlestickLabel.setEnabled(false);
          CandlestickCheckBox.setSelected(true);
          break;
        case WPN_KNIFE:
          KnifeLabel.setEnabled(false);
          KnifeCheckBox.setSelected(true);
          break;
        case WPN_LEAD_PIPE:
          LeadPipeLabel.setEnabled(false);
          LeadPipeCheckBox.setSelected(true);
          break;
        case WPN_REVOLVER:
          RevolverLabel.setEnabled(false);
          RevolverCheckBox.setSelected(true);
          break;
        case WPN_ROPE:
          RopeLabel.setEnabled(false);
          RopeCheckBox.setSelected(true);
          break;
        case WPN_WRENCH:
          WrenchLabel.setEnabled(false);
          WrenchCheckBox.setSelected(true);
          break;
      }
    }

    // Suspects
    for (Data.Suspect suspect : clues.getSuspectsList()) {
      logger.debug("addClues() - revelaed suspect: " + suspect.name());
      switch (suspect) {
        case SUS_NONE:
          break;
        case SUS_MISS_SCARLETT:
          MissScarletLabel.setEnabled(false);
          MissScarletCheckBox.setSelected(true);
          break;
        case SUS_COL_MUSTARD:
          ColMustardLabel.setEnabled(false);
          ColMustardCheckBox.setSelected(true);
          break;
        case SUS_MRS_WHITE:
          MrsWhiteLabel.setEnabled(false);
          MrsWhiteCheckBox.setSelected(true);
          break;
        case SUS_MR_GREEN:
          MrGreenLabel.setEnabled(false);
          MrGreenCheckBox.setSelected(true);
          break;
        case SUS_MRS_PEACOCK:
          MrsPeacockLabel.setEnabled(false);
          MrsPeacockCheckBox.setSelected(true);
          break;
        case SUS_PROF_PLUM:
          ProfPlumLabel.setEnabled(false);
          ProfPlumCheckBox.setSelected(true);
          break;
      }
    }
  }


  @Override
  public boolean shouldCallHandleOnGuiThread() {
    return true;
  }

  @Override
  public void handleMessage(Message msg) {
    // Here is how to handle messages of a specific type
    String msg_type = msg.getHeader().getMsgType();
    
    if (msg_type.equals(Msg.RevealClues.getDescriptor().getFullName())) {
      Msg.RevealClues reveal = (Msg.RevealClues)msg.getMessage();
      logger.debug("handleMessage() - got clue reveal: " + reveal.toString());
      addClues(reveal.getClues());
    }
    else if (msg_type.equals(Msg.DisproveResponse.getDescriptor().getFullName())) {
      Msg.DisproveResponse disprove = (Msg.DisproveResponse)msg.getMessage();
      logger.debug("handleMessage() - got disprove respone: " + disprove.toString());
      addClues(reveal.getDisproval());

      // @todo what if all are NONE? should show GUI to make accusation
    }
  }
}
