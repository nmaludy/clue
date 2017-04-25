package com.clue.app;

import com.clue.app.Config;
import com.clue.app.Logger;

import com.clue.gui.TerminalUI;

import com.clue.route.NetworkMessageHandler;

public class ClientTerminal implements Runnable {

  private static Logger logger = new Logger(ClientTerminal.class);
  private static Config config = Config.getInstance();
  
  public void run() {
    logger.debug("running terminal client.");
    NetworkMessageHandler net = new NetworkMessageHandler();

    TerminalUI term = new TerminalUI();
    term.run();
  }
}
