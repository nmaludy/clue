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

public class GUI extends JFrame implements ActionListener, MessageHandler {

  private static Logger logger = new Logger(GUI.class);

  private JButton connectButton = new JButton("Connect");
  private JButton moveButton = new JButton("Move");
  private JButton suggestionButton = new JButton("Suggestions & Accustations");
  private GUIpanel panel = new GUIpanel();
  private NotebookPanel notebook = new NotebookPanel();
  private CluesPanel cluesPanel = new CluesPanel();
  private Router router = Router.getInstance();
  private ConnectFrame connectFrame = new ConnectFrame();
  private MoveFrame MoveFrame = new MoveFrame( router );
  private SuggestionFrame SuggestionFrame = new SuggestionFrame( router );

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
            .addComponent(moveButton)
            .addComponent(suggestionButton)
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
            .addComponent(moveButton)
            .addComponent(suggestionButton))
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(panel, 800, 800, 800)
            .addComponent(notebook))
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addComponent(cluesPanel))));

    layout.linkSize(SwingConstants.VERTICAL, connectButton, moveButton, suggestionButton);

    notebook.strikeThrough();
    
    connectButton.addActionListener(this);
    moveButton.addActionListener(this);
    suggestionButton.addActionListener(this);

    setTitle("Clue-less");
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
    } else if (e.getSource().equals(moveButton)) {
      move();
    } else if (e.getSource().equals(suggestionButton)) {
      suggestion();
    }
  }

  public void connect() {
    
    connectFrame.setVisible( true );
    connectFrame.setState(JFrame.NORMAL);
  }

  public void move() {
    MoveFrame.setVisible( true );
    MoveFrame.setState(JFrame.NORMAL);
  }

  public void suggestion() {
    SuggestionFrame.setVisible( true );
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
    if (msg_type.equals(Msg.ConnectRequest.getDescriptor().getFullName())) {
      logger.debug("handleMessage() - explicitly handling message of type: " + msg_type);
    } else {
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
