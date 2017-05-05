package com.clue.gui;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

import java.net.URL;

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

  private enum ClueType {
    REVEAL, DISPROVAL
  }

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

    setLabelIcon( StudyLabel, "/images/rooms/study.png" );
    setLabelIcon( HallLabel, "/images/rooms/hall.png" );
    setLabelIcon( LoungeLabel, "/images/rooms/lounge.png" );
    setLabelIcon( LibraryLabel, "/images/rooms/library.png" );
    setLabelIcon( BilliardRoomLabel, "/images/rooms/billiard_room.png" );
    setLabelIcon( DiningRoomLabel, "/images/rooms/dining_room.png" );
    setLabelIcon( ConservatoryLabel, "/images/rooms/conservatory.png" );
    setLabelIcon( BallroomLabel, "/images/rooms/ballroom.png" );
    setLabelIcon( KitchenLabel, "/images/rooms/kitchen.png" );

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

    setLabelIcon( KnifeLabel, "/images/weapons/knife.png" );
    setLabelIcon( CandlestickLabel, "/images/weapons/candlestick.png" );
    setLabelIcon( RevolverLabel, "/images/weapons/revolver.png" );
    setLabelIcon( LeadPipeLabel, "/images/weapons/lead_pipe.png" );
    setLabelIcon( RopeLabel, "/images/weapons/rope.png" );
    setLabelIcon( WrenchLabel, "/images/weapons/wrench.png" );

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
    MissScarletLabel = new JLabel( "Miss. Scarlett" );
    MrsPeacockLabel  = new JLabel( "Mrs. Peacock" );
    ProfPlumLabel    = new JLabel( "Prof. Plum" );

    setLabelIcon( MrGreenLabel, "/images/suspects/mr_green.png" );
    setLabelIcon( MrsWhiteLabel, "/images/suspects/mrs_white.png" );
    setLabelIcon( ColMustardLabel, "/images/suspects/col_mustard.png" );
    setLabelIcon( MissScarletLabel, "/images/suspects/miss_scarlett.png" );
    setLabelIcon( MrsPeacockLabel, "/images/suspects/mrs_peacock.png" );
    setLabelIcon( ProfPlumLabel, "/images/suspects/prof_plum.png" );

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

  private void setLabelIcon(JLabel label, String iconPath) {
    URL imageURL = this.getClass().getResource(iconPath);
    ImageIcon icon = new ImageIcon(imageURL);
    ImageIcon resized_icon = getScaledImageIcon(icon, 25, 25);
    label.setIcon(resized_icon);
  }

  private ImageIcon getScaledImageIcon(ImageIcon srcIcon, int w, int h){
    Image srcImg = srcIcon.getImage();
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();

    return new ImageIcon(resizedImg);
  }

  private JPanel createCluePanel(JCheckBox checkBox, JLabel label) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    panel.add(checkBox);
    panel.add(label);
    return panel;
  }

  public void disproveClues(Data.Solution solution) {
    if (solution.hasLocation()) {
      addLocation(solution.getLocation(), ClueType.DISPROVAL);
    }
    if (solution.hasWeapon()) {
      addWeapon(solution.getWeapon(), ClueType.DISPROVAL);
    }
    if (solution.hasSuspect()) {
      addSuspect(solution.getSuspect(), ClueType.DISPROVAL);
    }
  }

  public void revealClues(Data.Clues clues) {
    // Rooms
    for (Data.Location location : clues.getLocationsList()) {
      logger.debug("revealClues() - revelaed location: " + location.name());
      addLocation(location, ClueType.REVEAL);
    }

    // Weapons
    for (Data.Weapon weapon : clues.getWeaponsList()) {
      logger.debug("revealClues() - revelaed weapon: " + weapon.name());
      addWeapon(weapon, ClueType.REVEAL);
    }

    // Suspects
    for (Data.Suspect suspect : clues.getSuspectsList()) {
      logger.debug("revealClues() - revelaed suspect: " + suspect.name());
      addSuspect(suspect, ClueType.REVEAL);
    }
  }

  public void addLocation(Data.Location location, ClueType clueType) {
    JLabel label = null;
    
    switch (location) {
      case LOC_NONE:
        break;
      case LOC_STUDY:
        label = StudyLabel;
        StudyLabel.setEnabled(false);
        StudyCheckBox.setSelected(true);
        break;
      case LOC_HALL:
        label = HallLabel;
        HallLabel.setEnabled(false);
        HallCheckBox.setSelected(true);
        break;
      case LOC_LOUNGE:
        label = LoungeLabel;
        LoungeLabel.setEnabled(false);
        LoungeCheckBox.setSelected(true);
        break;
      case LOC_LIBRARY:
        label = LibraryLabel; 
        LibraryLabel.setEnabled(false);
        LibraryCheckBox.setSelected(true);
        break;
      case LOC_BILLIARD_ROOM:
        label = BilliardRoomLabel;
        BilliardRoomLabel.setEnabled(false);
        BilliardRoomCheckBox.setSelected(true);
        break;
      case LOC_DINING_ROOM:
        label = DiningRoomLabel;
        DiningRoomLabel.setEnabled(false);
        DiningRoomCheckBox.setSelected(true);
        break;
      case LOC_CONSERVATORY:
        label = ConservatoryLabel;
        ConservatoryLabel.setEnabled(false);
        ConservatoryCheckBox.setSelected(true);
        break;
      case LOC_BALLROOM:
        label = BallroomLabel;
        BallroomLabel.setEnabled(false);
        BallroomCheckBox.setSelected(true);
        break;
      case LOC_KITCHEN:
        label = KitchenLabel;
        KitchenLabel.setEnabled(false);
        KitchenCheckBox.setSelected(true);
        break;
      default:
        logger.error("addLocation() - got invalid location clue revealed: "
                     + location.name());
        break;
    }
    
    if (label != null &&
        clueType == ClueType.REVEAL &&
        !label.getText().toLowerCase().contains("<html>")) {
      label.setText("<html><u>" + label.getText() + "</u></html>");
    }
  }

  public void addWeapon(Data.Weapon weapon, ClueType clueType) {
    JLabel label = null;
    
    switch (weapon) {
      case WPN_NONE:
        break;
      case WPN_CANDLESTICK:
        label = CandlestickLabel;
        CandlestickLabel.setEnabled(false);
        CandlestickCheckBox.setSelected(true);
        break;
      case WPN_KNIFE:
        label = KnifeLabel;
        KnifeLabel.setEnabled(false);
        KnifeCheckBox.setSelected(true);
        break;
      case WPN_LEAD_PIPE:
        label = LeadPipeLabel;
        LeadPipeLabel.setEnabled(false);
        LeadPipeCheckBox.setSelected(true);
        break;
      case WPN_REVOLVER:
        label = RevolverLabel;
        RevolverLabel.setEnabled(false);
        RevolverCheckBox.setSelected(true);
        break;
      case WPN_ROPE:
        label = RopeLabel;
        RopeLabel.setEnabled(false);
        RopeCheckBox.setSelected(true);
        break;
      case WPN_WRENCH:
        label = WrenchLabel;
        WrenchLabel.setEnabled(false);
        WrenchCheckBox.setSelected(true);
        break;
    }
    
    if (label != null &&
        clueType == ClueType.REVEAL &&
        !label.getText().toLowerCase().contains("<html>")) {
      label.setText("<html><u>" + label.getText() + "</u></html>");
    }
  }

  public void addSuspect(Data.Suspect suspect, ClueType clueType) {
    JLabel label = null;
    
    switch (suspect) {
      case SUS_NONE:
        break;
      case SUS_MISS_SCARLETT:
        label = MissScarletLabel;
        MissScarletLabel.setEnabled(false);
        MissScarletCheckBox.setSelected(true);
        break;
      case SUS_COL_MUSTARD:
        label = ColMustardLabel;
        ColMustardLabel.setEnabled(false);
        ColMustardCheckBox.setSelected(true);
        break;
      case SUS_MRS_WHITE:
        label = MrsWhiteLabel;
        MrsWhiteLabel.setEnabled(false);
        MrsWhiteCheckBox.setSelected(true);
        break;
      case SUS_MR_GREEN:
        label = MrGreenLabel;
        MrGreenLabel.setEnabled(false);
        MrGreenCheckBox.setSelected(true);
        break;
      case SUS_MRS_PEACOCK:
        label = MrsPeacockLabel;
        MrsPeacockLabel.setEnabled(false);
        MrsPeacockCheckBox.setSelected(true);
        break;
      case SUS_PROF_PLUM:
        label = ProfPlumLabel;
        ProfPlumLabel.setEnabled(false);
        ProfPlumCheckBox.setSelected(true);
        break;
    }

    if (label != null &&
        clueType == ClueType.REVEAL &&
        !label.getText().toLowerCase().contains("<html>")) {
      label.setText("<html><u>" + label.getText() + "</u></html>");
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
      revealClues(reveal.getClues());
    }
    else if (msg_type.equals(Msg.DisproveResponse.getDescriptor().getFullName())) {
      Msg.DisproveResponse disprove = (Msg.DisproveResponse)msg.getMessage();
      logger.debug("handleMessage() - got disprove respone: " + disprove.toString());
      disproveClues(disprove.getResponse());

      // @todo what if all are NONE? should show GUI to make accusation
    }
  }
}
