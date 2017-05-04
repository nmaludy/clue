package com.clue.gui;

import com.clue.app.Instance;
import com.clue.app.Logger;

import com.clue.route.Message;
import com.clue.route.MessageHandler;
import com.clue.route.Router;
import com.clue.route.SubscriptionAllIncoming;

import com.clue.proto.Msg;
import com.clue.proto.Data;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;

public class GUI extends JFrame implements ActionListener, MessageHandler {

  private static Logger logger = new Logger(GUI.class);

  private JButton connectButton = new JButton("Connect");
  private JButton startButton = new JButton("Start");
  private JButton moveButton = new JButton("Move");
  private JButton suggestionButton = new JButton("Suggestions & Accustations");
  private JButton testTurnEndButton = new JButton("Test: Turn End");
  
  private GUIpanel panel = new GUIpanel();
  private NotebookPanel notebook = new NotebookPanel();
  private CluesPanel cluesPanel = new CluesPanel();
  private Router router = Router.getInstance();
  private ConnectFrame connectFrame = new ConnectFrame(this);
  private MoveFrame MoveFrame = new MoveFrame(this, router );
  private SuggestionFrame SuggestionFrame = new SuggestionFrame(this, router );
  private DisproveFrame DisproveFrame = new DisproveFrame(this, router );
  private TurnEndFrame TurnEndFrame = new TurnEndFrame(this, router );

  /*
   * Initialze and layout all GUI components.
   */
  private GUI() {
    super();
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);

    layout.setAutoCreateGaps(true);
    layout.setHorizontalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
          .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(connectButton)
            .addComponent(startButton)
            .addComponent(moveButton)
            .addComponent(suggestionButton)
            .addComponent(testTurnEndButton)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(panel, 800, 800, 800)
            .addComponent(notebook))

          .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(cluesPanel))));

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(connectButton)
            .addComponent(startButton)
            .addComponent(moveButton)
            .addComponent(suggestionButton)
            .addComponent(testTurnEndButton) )
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(panel, 800, 800, 800)
            .addComponent(notebook))
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addComponent(cluesPanel))));

    layout.linkSize(SwingConstants.VERTICAL, connectButton, moveButton, suggestionButton);
    
    connectButton.addActionListener(this);
    startButton.addActionListener(this);
    moveButton.addActionListener(this);
    suggestionButton.addActionListener(this);
    testTurnEndButton.addActionListener(this);

    setTitle("Clue-less : " + ClientState.getInstance().getName());
    pack();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    centerFrame(this);
    setVisible(true);

    // Initialize our frames in a jacked up terrible because otherwise Linux
    // will render them as blank boxe
    connectFrame.setState(JFrame.ICONIFIED);
    connectFrame.setVisible(false);
    MoveFrame.setState(JFrame.ICONIFIED);
    MoveFrame.setVisible(false);
    SuggestionFrame.setState(JFrame.ICONIFIED);
    SuggestionFrame.setVisible(false);
    DisproveFrame.setState(JFrame.ICONIFIED);
    DisproveFrame.setVisible(false);
    TurnEndFrame.setState(JFrame.ICONIFIED);
    TurnEndFrame.setVisible(false);

    URL imageURL = this.getClass().getResource("/images/clue_icon.png");
    setIconImage(new ImageIcon(imageURL).getImage());

    router.register(new SubscriptionAllIncoming(), this);
  }

  private static class SingletonHelper {
    private static final GUI INSTANCE = new GUI();
  }
  
  public static GUI getInstance() {
    return SingletonHelper.INSTANCE;
  }

  /*
   * Handle events for button presses
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(connectButton)) {
      connect();
    } else if (e.getSource().equals(startButton)) {
      start();
    } else if (e.getSource().equals(moveButton)) {
      move();
    } else if (e.getSource().equals(suggestionButton)) {
      suggestion();
    } else if (e.getSource().equals(testTurnEndButton)) {
      turnEnd(null);
    }
  }

  public void connect() {
    connectFrame.setVisible( true );
    connectFrame.setState(JFrame.NORMAL);
  }

  public void start() {
    Msg.StartGameRequest req = Msg.StartGameRequest.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.StartGameRequest.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(Instance.getServerId())
                   .build())
        .build();
    router.route(new Message(req.getHeader(), req));
  }

  public void move() {
    MoveFrame.setVisible( true );
    MoveFrame.setState(JFrame.NORMAL);
  }

  public void suggestion() {
    SuggestionFrame.setVisible( true );
    SuggestionFrame.setState(JFrame.NORMAL);
  }

  public void disprove(Msg.DisproveRequest disprove) {
    DisproveFrame.setVisible( true );
    DisproveFrame.setState(JFrame.NORMAL);
    DisproveFrame.setDisproveRequest(disprove);
  }

  public void turnEnd(Msg.DisproveResponse resp) {
    TurnEndFrame.setVisible( true );
    TurnEndFrame.setState(JFrame.NORMAL);
    TurnEndFrame.setDisproveResponse(resp);
  }

  
  public void handleGameEnd(Msg.GameEnd req) {
	  logger.debug("winner is : " + req.getClientWinner());
	  
	  // if gameEnd.clientWinner equals your clientID print you win else print who won the game. 
	  if (req.getClientWinner() == Instance.getId())
	  {
		  JOptionPane.showMessageDialog(null, "You have solved the mystery. Game Over", "Surprise Surprise: Game Overrrr",
				  JOptionPane.INFORMATION_MESSAGE);
		  
	  }
	  else{
		  
		  JOptionPane.showMessageDialog(null, "The mystery has been solved.. You Lose", "Surprise Surprise: Game Overrrr",
				  JOptionPane.INFORMATION_MESSAGE);
		  
	  }
	  
 
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
    logger.debug("got message on thread: " + Thread.currentThread().getName());
    logger.debug("msg header = " + msg.getHeader().toString());

    // Here is how to handle messages of a specific type
    String msg_type = msg.getHeader().getMsgType();
    if (msg_type.equals(Msg.ConnectRequest.getDescriptor().getFullName())) 
    {
    	logger.debug("handleMessage() - explicitly handling message of type: " + msg_type);
    } 
    else if (msg_type.equals(Msg.GameState.getDescriptor().getFullName())) 
    {
      logger.debug("handleMessage() - explicitly handling message of type: " + msg_type);
      Msg.GameState state = (Msg.GameState)msg.getMessage();
      for (Data.Player player : state.getPlayersList())
      {
        panel.movePlayer(player.getSuspect(), player.getLocation());
      }        
    }
    else if (msg_type.equals(Msg.DisproveRequest.getDescriptor().getFullName())) 
    {
    	logger.debug("handleMessage() - explicitly handling message of type: " + msg_type);
      disprove((Msg.DisproveRequest)msg.getMessage());
    }
    else if (msg_type.equals(Msg.DisproveResponse.getDescriptor().getFullName())) 
    {
    	logger.debug("handleMessage() - explicitly handling message of type: " + msg_type);
      turnEnd((Msg.DisproveResponse)msg.getMessage());
    } 
    else 
    {
    	logger.debug("handleMessage() - got unhandled message type: " + msg_type);
    }
  }

  /**
   * 
   * Center the GUI frame in the middle of the user's screen
   * @param w is the window of the GUI
   */
  public void centerFrame(Window w)
  {
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();
    int xLoc = (d.width - w.getWidth())/2;
    int yLoc = (d.height - w.getHeight())/2;
    //setLocation(xLoc, yLoc);
  }


}
