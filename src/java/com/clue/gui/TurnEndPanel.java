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

public class TurnEndPanel extends JPanel implements ActionListener, ComponentListener {

  private static Logger logger = new Logger(TurnEndPanel.class);
  private static Config config = Config.getInstance();

  private JFrame parent = null;
  private Router router = null;

  // Labels
  private JLabel suggestionSuspectLabel;
  private JLabel suggestionWeaponLabel;
  private JLabel suggestionRoomLabel;

  private JLabel disprovalSuspectLabel;
  private JLabel disprovalWeaponLabel;
  private JLabel disprovalRoomLabel;

  private JLabel resultLabel;

  // Buttons
  private JButton accusationButton = new JButton("Make Accusation");
  private JButton passButton = new JButton("Pass");

  private Msg.DisproveResponse response = null;

  // Panel Constructor
  public TurnEndPanel( JFrame parent, Router routerIn )
  {
    this.parent = parent;
    this.router = routerIn;
    
    // Labels
    suggestionSuspectLabel = new JLabel("None", SwingConstants.LEADING);
    suggestionWeaponLabel = new JLabel("None", SwingConstants.LEADING);
    suggestionRoomLabel = new JLabel("None", SwingConstants.LEADING);

    disprovalSuspectLabel = new JLabel("None", SwingConstants.LEADING);
    disprovalWeaponLabel = new JLabel("None", SwingConstants.LEADING);
    disprovalRoomLabel = new JLabel("None", SwingConstants.LEADING);

    resultLabel = new JLabel("", SwingConstants.CENTER);
 
    // Create the borders
    Border suggestionBorder = BorderFactory.createEtchedBorder();
    suggestionBorder = BorderFactory.createTitledBorder( suggestionBorder, "Suggestion" );

    Border disprovalBorder = BorderFactory.createEtchedBorder();
    disprovalBorder = BorderFactory.createTitledBorder( disprovalBorder, "Disproval" );
    

    // Suggestion Panel
    JPanel suggestionPanel = new JPanel();
    suggestionPanel.setLayout( new SpringLayout() );
    suggestionPanel.add( new JLabel("Suspect:") );
    suggestionPanel.add( suggestionSuspectLabel );
    suggestionPanel.add( new JLabel("Weapon:") );
    suggestionPanel.add( suggestionWeaponLabel );
    suggestionPanel.add( new JLabel("Room:") );
    suggestionPanel.add( suggestionRoomLabel );
    suggestionPanel.setBorder( suggestionBorder );
    // make it a grid
    SpringUtilities.makeCompactGrid(suggestionPanel,
                                    3, 2,  // rows, cols
                                    6, 6,  // initX, initY
                                    6, 6); // xPad, yPad

    // Disproval Panel
    JPanel disprovalPanel = new JPanel();
    disprovalPanel.setLayout( new SpringLayout() );
    disprovalPanel.add( new JLabel("Suspect:") );
    disprovalPanel.add( disprovalSuspectLabel );
    disprovalPanel.add( new JLabel("Weapon:") );
    disprovalPanel.add( disprovalWeaponLabel );
    disprovalPanel.add( new JLabel("Room:") );
    disprovalPanel.add( disprovalRoomLabel );
    disprovalPanel.setBorder( disprovalBorder );
    // make it a grid
    SpringUtilities.makeCompactGrid(disprovalPanel,
                                    3, 2,  // rows, cols
                                    6, 6,  // initX, initY
                                    6, 6); // xPad, yPad

    
    // Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout( new FlowLayout( FlowLayout.CENTER ) );
    buttonPanel.add( accusationButton );
    buttonPanel.add( passButton );

    // Add ActionListeners for each radio button
    accusationButton.addActionListener(this);
    passButton.addActionListener(this);

    //Default Submit Buttons to disabled
    accusationButton.setEnabled(true);
    passButton.setEnabled(true);

    // Add panels to the main panel and arrange layout
    // this.setLayout(new GridLayout(3,1));
    
    this.setLayout( new SpringLayout() );
    this.add( resultLabel );
    this.add( suggestionPanel );
    this.add( disprovalPanel );
    this.add( buttonPanel );
    SpringUtilities.makeCompactGrid(this,
                                    4, 1,  // rows, cols
                                    6, 6,  // initX, initY
                                    6, 6); // xPad, yPad

    parent.addComponentListener(this);
  }

  public void setDisproveResponse(Msg.DisproveResponse resp) {
    this.response = resp;
    boolean b_disproven = false;

    // Update suggestion labels
    suggestionSuspectLabel.setText(
          ClientState.suspectToString(response.getGuess().getSuspect()));
    suggestionRoomLabel.setText(
          ClientState.locationToString(response.getGuess().getLocation()));
    suggestionWeaponLabel.setText(
          ClientState.weaponToString(response.getGuess().getWeapon()));

    // Update disproval labels
    if (response.getResponse().getSuspect() != Data.Suspect.SUS_NONE) {
      disprovalSuspectLabel.setText(
          ClientState.suspectToString(response.getResponse().getSuspect()));
      b_disproven = true;
    }
    if (response.getResponse().getLocation() != Data.Location.LOC_NONE) {
      disprovalRoomLabel.setText(
          ClientState.locationToString(response.getResponse().getLocation()));
      b_disproven = true;
    }
    if (response.getResponse().getWeapon() != Data.Weapon.WPN_NONE) {
      disprovalWeaponLabel.setText(
          ClientState.weaponToString(response.getResponse().getWeapon()));
      b_disproven = true;
    }

    // Update instructions
    String message = new String();
    if (b_disproven) {
      Data.Player player = ClientState.getInstance().getPlayerById(
          resp.getResponse().getClientId());
      message = "Your suggestion was disproven by '" + player.getName() + "'!";
      logger.debug("disproveResponse() - disproven = " + resp.getResponse().toString());
    } else {
      message = "No one disproved your suggestion!";
      logger.debug("disproveResponse() - Nothing was disproven!");
    }
    resultLabel.setText("<html><div style='text-align: center;'>"
                        + message + "<br>"
                        + "Would you like to make an accusation or pass?"
                        + "</div></html>");
  }


  /**
   * This method handles events caused by the user
   * selecting or deselecting radio buttons or
   * checkboxes
   *
   * @param e is an ActionEvent caused generated by an action listener
   */
  public void actionPerformed(ActionEvent e) {
    if ( e.getSource().equals(accusationButton) ) {
      GUI.getInstance().accusation(response.getGuess());
      parent.setVisible(false);
    }

    // Pass our turn
    if ( e.getSource().equals(passButton) ) {
      Msg.PassTurn msg = Msg.PassTurn.newBuilder()
          .setHeader(Msg.Header.newBuilder()
                     .setMsgType(Msg.PassTurn.getDescriptor().getFullName())
                     .setSource(Instance.getId())
                     .setDestination(Instance.getServerId())
                     .build())
          .setClientId(Instance.getId())
          .build();
      router.route(new Message(msg.getHeader(), msg));
      parent.setVisible(false);
    }
  }

  @Override
  public void componentHidden(ComponentEvent event) {
    logger.debug("Component hidden");
    
    suggestionSuspectLabel.setText("None");
    suggestionWeaponLabel.setText("None");
    suggestionRoomLabel.setText("None");

    disprovalSuspectLabel.setText("None");
    disprovalWeaponLabel.setText("None");
    disprovalRoomLabel.setText("None");
    
    resultLabel.setText("");
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
