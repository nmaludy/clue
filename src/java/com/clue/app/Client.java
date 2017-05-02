package com.clue.app;

import com.clue.app.Config;
import com.clue.app.Logger;
import com.clue.route.NetworkMessageHandler;

import javax.swing.JFrame;
import javax.swing.UIManager;
import org.pushingpixels.substance.api.skin.*;

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

        // initiallize the game state listener and connect to the server
        com.clue.gui.ClientState.getInstance();

        // create and show the GUI
				JFrame f = com.clue.gui.GUI.getInstance();
			}
		});
  }
}
