package com.clue.app;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.clue.app.Config;
import com.clue.app.Logger;
import com.clue.route.Message;
import com.clue.route.NetworkMessageHandler;
import com.clue.route.Router;

import javax.swing.JFrame;
import javax.swing.UIManager;
import org.pushingpixels.substance.api.skin.*;

/**
* Hello World client
* Connects REQ socket to tcp://localhost:5559
* Sends "Hello" to server, expects "World" back
*/
public class Client implements Runnable {

  private static Logger logger = new Logger(Client.class);
  private static Config config = Config.getInstance();
  
  public void run() {
    logger.debug("running client.");
    NetworkMessageHandler net = new NetworkMessageHandler();
    
    JFrame.setDefaultLookAndFeelDecorated(true);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
        try {
          // Skin options available here:
          // https://github.com/kirill-grouchnikov/substance/blob/master/www/docs/skins/toneddown.md
          
					// UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
          UIManager.setLookAndFeel(new SubstanceModerateLookAndFeel());
				} catch (Exception e) {
					System.out.println("Substance Graphite failed to initialize");
				}
				JFrame f = com.clue.gui.GUI.getInstance();
				// f.setIconImage(new ImageIcon(imageURL).getImage());
			}
		});
  }
}
