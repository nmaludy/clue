package com.clue.app;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.clue.app.Logger;
import com.clue.route.Message;
import com.clue.route.NetworkMessageHandler;

import com.clue.proto.Msg.*;
import com.clue.proto.Data.*;

/**
* Hello World client
* Connects REQ socket to tcp://localhost:5559
* Sends "Hello" to server, expects "World" back
*/
public class GUI implements Runnable {

  private static Logger logger = new Logger(GUI.class);

  public void run() {
    logger.debug("running GUI.");

    // GUI
    System.out.print("hi there");
    // build the gameboard here using array
    
    
    // create method to print the array
    
    
    // create method to make a move
    
    
    
    logger.debug("finished GUI.");
  }
}
