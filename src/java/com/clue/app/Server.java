package com.clue.app;

import com.clue.route.NetworkMessageHandler;
import com.clue.server.GameLogic;

public class Server implements Runnable {

  private static Logger logger = new Logger(Server.class);

  public void run() {
    logger.debug("running server.");
    
    // starup network
    NetworkMessageHandler net = new NetworkMessageHandler();

    // startup game logic
    GameLogic logic = new GameLogic();
    
    // net.waitForBackgroundThread();
    logger.debug("finished server.");
  }

}
