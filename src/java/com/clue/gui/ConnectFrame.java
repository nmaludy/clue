package com.clue.gui;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.clue.app.Config;
import com.clue.app.Logger;
import com.clue.app.Instance;

import com.clue.route.Message;
import com.clue.route.MessageHandler;
import com.clue.route.Router;
import com.clue.route.SubscriptionAllIncoming;

import com.clue.proto.Data;
import com.clue.proto.Msg;

public class ConnectFrame extends JFrame implements ActionListener, MessageHandler
{
  private static Logger logger = new Logger(ConnectFrame.class);
  private static Config config = Config.getInstance();

  private Router router;
  private SuspectButtonPanel suspectButtons;

  private JLabel errorLabel;
  private JButton okButton;
  private JButton cancelButton;

  private boolean isConnected;

  public ConnectFrame()
  {
    setTitle( "Connect" );
    setSize( 600, 180 );
    setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    centerFrame(this);
    
    this.isConnected = false;

    // Create suspect radio buttons panel
    suspectButtons = new SuspectButtonPanel();

    // Create ok & cancel buttons
    okButton = new JButton("OK");
    cancelButton = new JButton("Cancel");

    // list for button presses
    okButton.addActionListener(this);
    cancelButton.addActionListener(this);

    // Create a button panel & add buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout( new FlowLayout( FlowLayout.CENTER ) );
    buttonPanel.add( okButton );
    buttonPanel.add( cancelButton );

    // create labels for errors
    errorLabel = new JLabel();
    errorLabel.setForeground(Color.RED);
    errorLabel.setVisible(false);

    JPanel errorPanel = new JPanel();
    errorPanel.setLayout( new FlowLayout( FlowLayout.CENTER ) );
    errorPanel.add( errorLabel );
        
    this.setLayout( new BorderLayout() );
    this.add( suspectButtons, BorderLayout.NORTH );
    this.add( errorPanel, BorderLayout.CENTER );
    this.add( buttonPanel, BorderLayout.SOUTH );

    router = Router.getInstance();
    router.register(new SubscriptionAllIncoming(), this);
    
    Msg.GameStateRequest req = Msg.GameStateRequest.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.GameStateRequest.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(Instance.getServerId())
                   .build())
        .build();
    router.route(new Message(req.getHeader(), req));
  }

  public void ok() {
    if (suspectButtons.getSuspect() == null) {
      errorLabel.setText("ERROR: Please select a suspect!");
      errorLabel.setVisible(true);
      return;
    }

    if (!isConnected) {
      String name = config.getProperty("instance.name", String.class);
      Msg.ConnectRequest req = Msg.ConnectRequest.newBuilder()
          .setHeader(Msg.Header.newBuilder()
                     .setMsgType(Msg.ConnectRequest.getDescriptor().getFullName())
                     .setSource(Instance.getId())
                     .setDestination(Instance.getServerId())
                     .build())
          .setClientId(Instance.getId())
          .setName(name)
          .build();
      router.route(new Message(req.getHeader(), req));
    }
  }

  public void cancel() {
    setVisible(false);
  }

  /*
   * Handle events for button presses
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(okButton)) {
      ok();
    } else if (e.getSource().equals(cancelButton)) {
      cancel();
    }
  }

  /**
   *
   * Center the GUI frame in the middle of the user's screen
   * @param w is the window of the GUI
   */
  public void centerFrame(Window w) {
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();
    int xLoc = (d.width - w.getWidth())/2;
    int yLoc = (d.height - w.getHeight())/2;
    setLocation(xLoc, yLoc);
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
    if (msg_type.equals(Msg.GameState.getDescriptor().getFullName())) {
      handleGameState((Msg.GameState)msg.getMessage());
    }
    else if (msg_type.equals(Msg.ConnectResponse.getDescriptor().getFullName())) {
      handleConnection((Msg.ConnectResponse)msg.getMessage());
    }
    else if (msg_type.equals(Msg.SuspectClaimResponse.getDescriptor().getFullName())) {
      handleSuspectClaim((Msg.SuspectClaimResponse)msg.getMessage());
    }
  }

  public void handleGameState(Msg.GameState state) {
    suspectButtons.disableClaimedSuspects(state);
  }
    
  public void handleConnection(Msg.ConnectResponse response) {
    if (response.getGoodConnection()) {
      isConnected = true;
      logger.debug("handleMessage() - connection = GOOD");
      Msg.SuspectClaimRequest req = Msg.SuspectClaimRequest.newBuilder()
          .setHeader(Msg.Header.newBuilder()
                     .setMsgType(Msg.SuspectClaimRequest.getDescriptor().getFullName())
                     .setSource(Instance.getId())
                     .setDestination(Instance.getServerId())
                     .build())
          .setSuspect(suspectButtons.getSuspect())
          .build();
      router.route(new Message(req.getHeader(), req));
    } else {
      errorLabel.setText("ERROR: Client ID already in use: "
                         + Integer.toString(Instance.getId()));
      errorLabel.setVisible(true);
      logger.debug("handleMessage() - connection = BAD");
    }
  }

  public void handleSuspectClaim(Msg.SuspectClaimResponse  response) {
    if (response.getClaimSuccess()) {
      logger.debug("handleMessage() - suspect claim = GOOD");
      setVisible(false);
    } else {
      errorLabel.setText("ERROR: Suspect already in use!");
      errorLabel.setVisible(true);
      logger.debug("handleMessage() - suspect claim = BAD");
    }
  }

}
