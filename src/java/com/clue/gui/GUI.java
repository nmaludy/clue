package com.clue.gui;

import com.clue.app.Instance;
import com.clue.app.Logger;

import com.clue.route.Message;
import com.clue.route.MessageHandler;
import com.clue.route.Router;
import com.clue.route.SubscriptionAllIncoming;

import com.clue.proto.Msg;
import com.clue.proto.Data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JButton suggestionButton = new JButton("Suggestion");
  private Router router = Router.getInstance();

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
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(connectButton)
						.addComponent(moveButton)
						.addComponent(suggestionButton))));


		layout.linkSize(SwingConstants.VERTICAL, connectButton, moveButton, suggestionButton);

		connectButton.addActionListener(this);
		moveButton.addActionListener(this);
		suggestionButton.addActionListener(this);

		setTitle("Clue-less");
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);

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
    Msg.ConnectRequest req = Msg.ConnectRequest.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.ConnectRequest.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(Instance.getServerId())
                   .build())
        .setName("test")
        .build();
    router.route(new Message(req.getHeader(), req));
  }

  public void move() {
    Msg.PlayerMove mv = Msg.PlayerMove.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.PlayerMove.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(Instance.getServerId())
                   .build())
        .setDestination(Data.Location.Identity.LOC_STUDY)
        .build();
    router.route(new Message(mv.getHeader(), mv));
  }

  public void suggestion() {
    Msg.PlayerSuggestion sg = Msg.PlayerSuggestion.newBuilder()
        .setHeader(Msg.Header.newBuilder()
                   .setMsgType(Msg.PlayerSuggestion.getDescriptor().getFullName())
                   .setSource(Instance.getId())
                   .setDestination(Instance.getServerId())
                   .build())
        .setGuess(Data.Guess.newBuilder()
                  .setClientId(Instance.getId())
                  .setSolution(Data.Solution.newBuilder()
                               .setWeapon(Data.Weapon.Identity.WPN_KNIFE)
                               .setSuspect(Data.Suspect.Identity.SUS_MRS_PEACOCK)
                               .setLocation(Data.Location.Identity.LOC_KITCHEN)
                               .build())
                  .build())
        .build();
    router.route(new Message(sg.getHeader(), sg));
  }

  public void handleMessage(Message msg) {
    // @note this WILL be called on a background thread, so the
    // following code is scheduled to run back onto the GUI EventDispatch thread
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          handleMessageGuiThread(msg);
        }
      });
  }

  /**
   * @brief This is where all message "handling" should be done
   * @param msg 
   */
  public void handleMessageGuiThread(Message msg) {
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


}
