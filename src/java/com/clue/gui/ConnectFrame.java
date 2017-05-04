package com.clue.gui;

import java.awt.*;
import java.awt.event.*;
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

public class ConnectFrame extends JFrame implements ActionListener, ComponentListener, MessageHandler
{
  private static Logger logger = new Logger(ConnectFrame.class);
  private static Config config = Config.getInstance();

  private JFrame parent;
  private Router router;
  private SuspectButtonPanel suspectButtons;

  private JLabel errorLabel;
  private JButton okButton;
  private JButton cancelButton;

  private boolean isConnected;

  public ConnectFrame(JFrame parent)
  {
    this.parent = parent;
    setTitle( "Connect" );
    setSize( 600, 180 );
    setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    centerFrame(this);
    
    this.isConnected = false;

    // Create suspect radio buttons panel
    suspectButtons = new SuspectButtonPanel();
    // disable any suspect buttons that are already taken
    handleGameState(ClientState.getInstance().gameState());
    
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

    this.addComponentListener(this);
    
    java.net.URL imageURL = this.getClass().getResource("/images/clue_icon.png");
    setIconImage(new ImageIcon(imageURL).getImage());
  }

  public void ok() {
    if (suspectButtons.getSuspect() == null) {
      errorLabel.setText("ERROR: Please select a suspect!");
      errorLabel.setVisible(true);
      return;
    }

    if (ClientState.getInstance().isConnected()) {
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
    else if (msg_type.equals(Msg.SuspectClaimResponse.getDescriptor().getFullName())) {
      handleSuspectClaim((Msg.SuspectClaimResponse)msg.getMessage());
    }
  }

  public void handleGameState(Msg.GameState state) {
    suspectButtons.disableClaimedSuspects(state);
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

  @Override
  public void componentHidden(ComponentEvent event) {
    logger.debug("Component hidden");
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
    FrameUtils.centerChildOnParent(this, parent);
  }

}
