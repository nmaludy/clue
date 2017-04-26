package com.clue.gui;

import java.util.Scanner;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.clue.app.Config;
import com.clue.app.Logger;

import com.clue.route.Message;
import com.clue.route.MessageHandler;
import com.clue.route.Router;
import com.clue.route.SubscriptionAllIncoming;

import com.clue.proto.Msg;
import com.clue.proto.Data;

public class TerminalUI implements MessageHandler, Runnable
{

  private static Logger logger = new Logger(TerminalUI.class);
  private static Config config = Config.getInstance();

  private ConcurrentLinkedQueue<Message> msgQueue = new ConcurrentLinkedQueue<Message>();
  private Router router = Router.getInstance();
  
  private String clueBoard[][];
	
	private int boardXDimension = 80;
	private int boardYDimension = 37;
	
	// @toDo: currentX & Y currently not being used. This will probably have to be the user's character location
	private int currentX = 0;
	private int currentY = 0;

  public TerminalUI() {
    router.register(new SubscriptionAllIncoming(), this);
  }
	
  public void run() {
    //		char choice;
    //		
    //		// add menu items ===> pick character, start game etc etc
    //		do
    //		{
    //			System.out.println("================================");
    //			System.out.println("=                              =");
    //			System.out.println("=        Welcome to Clue       =");
    //			System.out.println("=                              =");
    //            System.out.println("=                              =");
    //            System.out.println("=         Main Menu            =");
    //            System.out.println("=         1. Join Game         =");
    //            System.out.println("=         2. Exit              =");
    //            System.out.println("=                              =");
    //            System.out.println("=                              =");
    //			System.out.println("================================");
    //		    
    //			
    //		choice = (char)System.in.read();	
    //		}while (choice < '1' || choice > '3');
    //		
    //		switch( choice )
    //		{
    //			case '1':
    //			{
    //				// instantiate the clue board (array)
    //				buildClueBoard();
    //				buildClueRooms();
    //				buildClueHallways();
    //				
    //				// print the clue board 
    //				printClueBoard();
    //				
    //			}
    //			
    //			case '2':
    //			{
    //				break;
    //			}
    //		}

		// instantiate the clue board (array)
		buildClueBoard();
		buildClueRooms();
		buildClueHallways();
		
		// print the clue board 
		printClueBoard();

    // @todo jeremy grab messages from queue in this loop
    // while (userDidntQuit()) { 
    //   Message msg = msgQueue.poll();
    //   if (msg != null) {
    //     handleMessageTerminalThread(msg);
    //   }

    //   Thread.sleep(100); // 100ms
    // }
		
  } // end of run
	
  public void buildClueBoard()
	{
		// instantiate the array
		clueBoard = new String  [boardYDimension][boardXDimension];
		
		/*
		 *  *****  Background grid (optional - we can just take this out honestly) *******
		 */
		
		for ( int i=0; i<boardYDimension; i++ )
		{
			for ( int j=0; j<boardXDimension; j++ )
			{
				clueBoard[i][j] = " ";
			}
		}
		
	} // end of buildclueBoard
	
	
	public void buildClueRooms()
	{
		// Build the Study
		for ( int i=2; i<9; i++ )
		{
			for ( int j=2; j<18; j++ )
			{
				clueBoard[i][j] = "0";
			}
		} 
		
		// Build the Study Room empty space
		for (int i=3; i<8; i++)
		{ 			
			for( int j=3; j<17; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		
		// Build the Hall
		for ( int i=2; i<9; i++)
		{
			for ( int j=32; j<48; j++)
			{
				clueBoard[i][j] = "0";
			}
		}
		
		// Build the Hall empty space
		for ( int i=3; i<8; i++)
		{
			for ( int j=33; j<47; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		// Build the Lounge
		for ( int i=2; i<9; i++)
		{
			for ( int j=62; j<78; j++)
			{
				clueBoard[i][j] = "0";
			}
		}
		
		//Build the Lounge empty space
		for ( int i=3; i<8; i++)
		{
			for ( int j=63; j<77; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		// Build the Library
		for ( int i=15; i<22; i++)
		{
			for ( int j=2; j<18; j++)
			{
				clueBoard[i][j] = "0";
			}
		}
		
		//Build the Library empty space
		for ( int i=16; i<21; i++)
		{
			for ( int j=3; j<17; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		// Build the Billiard Room
		for ( int i=15; i<22; i++)
		{
			for ( int j=32; j<48; j++)
			{
				clueBoard[i][j] = "0";
			}
		}
		
		//Build the Billiard Room empty space
		for ( int i=16; i<21; i++)
		{
			for ( int j=33; j<47; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		
		// Build the Dining Room
		for ( int i=15; i<22; i++)
		{
			for ( int j=62; j<78; j++)
			{
				clueBoard[i][j] = "0";
			}
		}
		
		//Build the Dining Room empty space
		for ( int i=16; i<21; i++)
		{
			for ( int j=63; j<77; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		// Build the Conservatory
		for ( int i=28; i<35; i++)
		{
			for ( int j=2; j<18; j++)
			{
				clueBoard[i][j] = "0";
			}
		}
		
		//Build the Conservatory empty space
		for ( int i=29; i<34; i++)
		{
			for ( int j=3; j<17; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		// Build the Ballroom
		for ( int i=28; i<35; i++)
		{
			for ( int j=32; j<48; j++)
			{
				clueBoard[i][j] = "0";
			}
		}
		
		//Build the Ballroom empty space
		for ( int i=29; i<34; i++)
		{
			for ( int j=33; j<47; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		// Build the Kitchen
		for ( int i=28; i<35; i++)
		{
			for ( int j=62; j<78; j++)
			{
				clueBoard[i][j] = "0";
			}
		}
		
		//Build the Kitchen empty space
		for ( int i=29; i<34; i++)
		{
			for ( int j=63; j<77; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
			
	}
	
	public void buildClueHallways()
	{
		// Build the Study-Hall Hallway Passage
		for ( int i=4; i<7; i++)
		{
			for ( int j=18; j<32; j++)
			{
				clueBoard[i][j] = "-";
			}
		}
		
		// Build empty space for Study-Hall Hallway Passage
		for (int i=5; i<6; i++)
		{
			for (int j=18; j<32; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		
		// Build the Hall-Lounge Room Hallway Passage
		for ( int i=4; i<7; i++)
		{
			for ( int j=48; j<62; j++)
			{
				clueBoard[i][j] = "-";
			}
		}
		
		// Build empty space for Hall-Lounge Hallway Passage
		for (int i=5; i<6; i++)
		{
			for (int j=48; j<62; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		
		// Build the Library Room-Billiard Room Hallway Passage
		for ( int i=17; i<20; i++)
		{
			for ( int j=18; j<32; j++)
			{
				clueBoard[i][j] = "-";
			}
		}
			
		// Build empty space Library Room-Billiard RoomHallway Passage
		for (int i=18; i<19; i++)
		{
			for (int j=18; j<32; j++)
			{
				clueBoard[i][j] = "-";
			}
		}
		
		// Build the Billiard Room-Dining Room Hallway Passage
		for ( int i=17; i<20; i++)
		{
			for ( int j=48; j<62; j++)
			{
				clueBoard[i][j] = "-";
			}
		}
		
		// Build empty space Billiard Room Hallway Passage
		for (int i=18; i<19; i++)
		{
			for (int j=48; j<62; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		// Build the Conservatory-Ballroom Hallway Passage
		for ( int i=30; i<33; i++)
		{
			for ( int j=18; j<32; j++)
			{
				clueBoard[i][j] = "-";
			}
		}
		
		// Build empty space Conservatory-Ballroom Hallway Passage
		for (int i=31; i<32; i++)
		{
			for (int j=18; j<32; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		// Build the Ballroom-Kitchen Hallway Passage
		for ( int i=30; i<33; i++)
		{
			for ( int j=48; j<62; j++)
			{
				clueBoard[i][j] = "-";
			}
		}
		
		// Build empty space Ballroom-Kitchen Hallway Passage
		for (int i=31; i<32; i++)
		{
			for (int j=48; j<62; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		
		// Build the Study-Library Hallway Passage
		for (int i=9; i<15; i++)
		{
			for (int j=8; j<12; j++)
			{
				clueBoard[i][j] = "|";
			}
		}
		
		// Build the empty space Study-Library Hallway Passage
		for (int i=9; i<15; i++)
		{
			for (int j=9; j<11; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		// Build the Hall-Billiard Room Hallway Passage
		for ( int i=9; i<15; i++)
		{
			for ( int j=38; j<42; j++)
			{
				clueBoard[i][j] = "|";
			}
		}
		
		// Build the empty space Hall-Billiard Room Hallway Passage
		for (int i=9; i<15; i++)
		{
			for (int j=39; j<41; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		
		// Build the Lounge-Dining Room Hallway Passage
		for ( int i=9; i<15; i++)
		{
			for ( int j=68; j<72; j++)
			{
				clueBoard[i][j] = "|";
			}
		}
		
		// Build the empty space Lounge-Dining Room Hallway Passage
		for (int i=9; i<15; i++)
		{
			for (int j=69; j<71; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		
		// Build the Library-Conservatory Hallway Passage
		for ( int i=22; i<28; i++)
		{
			for ( int j=8; j<12; j++)
			{
				clueBoard[i][j] = "|";
			}
		}
		
		// Build the empty space Library-Conservatory Hallway Passage
		for (int i=22; i<28; i++)
		{
			for (int j=9; j<11; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		
		// Build the Billiard Room-Ballroom Hallway Passage
		for ( int i=22; i<28; i++)
		{
			for ( int j=38; j<42; j++)
			{
				clueBoard[i][j] = "|";
			}
		}
		
		// Build the Billiard Room-Ballroom Hallway Passage
		for (int i=22; i<28; i++)
		{
			for (int j=39; j<41; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
		
		// Build the Dining Room-Kitchen Hallway Passage
		for ( int i=22; i<28; i++)
		{
			for ( int j=68; j<72; j++)
			{
				clueBoard[i][j] = "|";
			}
		}
		
		// Build the Dining Room-Kitchen Hallway Passage
		for (int i=22; i<28; i++)
		{
			for (int j=69; j<71; j++)
			{
				clueBoard[i][j] = " ";
			}
		}
		
	} //end of buildClueHallways()
	
	
	public void writeRoomNames()
	{
		// Study 
		clueBoard[3][6] = "S";
		clueBoard[3][7] = "t";
		clueBoard[3][8] = "u";
		clueBoard[3][9] = "d";
		clueBoard[3][10] = "y";
		
		// Hall
		clueBoard[3][35] = "H";
		clueBoard[3][36] = "a";
		clueBoard[3][37] = "l";
		clueBoard[3][38] = "l";
		
		// Lounge
		clueBoard[3][64] = "L";
		clueBoard[3][65] = "o";
		clueBoard[3][66] = "u";
		clueBoard[3][67] = "n";
		clueBoard[3][68] = "g";
		clueBoard[3][69] = "e";
		
		// Library (17)
		clueBoard[16][6] = "L";
		clueBoard[16][7] = "i";
		clueBoard[16][8] = "b";
		clueBoard[16][9] = "r";
		clueBoard[16][10] = "a";
		clueBoard[16][11] = "r";
		clueBoard[16][12] = "y";
		
		
		// Billiard Room
		clueBoard[16][35] = "B";
		clueBoard[16][36] = "i";
		clueBoard[16][37] = "l";
		clueBoard[16][38] = "l";
		clueBoard[16][39] = "i";
		clueBoard[16][40] = "a";
		clueBoard[16][41] = "r";
		clueBoard[16][42] = "d";
		clueBoard[17][35] = "R";
		clueBoard[17][36] = "o";
		clueBoard[17][37] = "o";
		clueBoard[17][38] = "m";
		
		// Dining Room
		clueBoard[16][65] = "D";
		clueBoard[16][66] = "i";
		clueBoard[16][67] = "n";
		clueBoard[16][68] = "i";
		clueBoard[16][69] = "n";
		clueBoard[16][70] = "g";
		clueBoard[17][65] = "R";
		clueBoard[17][66] = "o";
		clueBoard[17][67] = "o";
		clueBoard[17][68] = "m";
		
		// Conservatory (33)
		clueBoard[33][4] = "C";
		clueBoard[33][5] = "o";
		clueBoard[33][6] = "n";
		clueBoard[33][7] = "s";
		clueBoard[33][8] = "e";
		clueBoard[33][9] = "r";
		clueBoard[33][10] = "v";
		clueBoard[33][11] = "a";
		clueBoard[33][12] = "t";
		clueBoard[33][13] = "o";
		clueBoard[33][14] = "r";
		clueBoard[33][15] = "y";
		
		// Ballroom
		clueBoard[33][35] = "B";
		clueBoard[33][36] = "a";
		clueBoard[33][37] = "l";
		clueBoard[33][38] = "l";
		clueBoard[33][39] = "r";
		clueBoard[33][40] = "o";
		clueBoard[33][41] = "o";
		clueBoard[33][42] = "m";

		
		// Kitchen
		clueBoard[33][65] = "K";
		clueBoard[33][66] = "i";
		clueBoard[33][67] = "t";
		clueBoard[33][68] = "c";
		clueBoard[33][69] = "h";
		clueBoard[33][70] = "e";
		clueBoard[33][71] = "n";
		
	}
	
	
	public void printClueBoard()
	{
		System.out.println();
		
		for ( int i=0; i<boardYDimension; i++)
		{
			for ( int j=0; j<boardXDimension; j++)
			{			
				System.out.print(clueBoard[i][j]);	
			}
			
			System.out.println();
		}
		
		System.out.println();
	} // end of printClueBoard()
	
	public void printGameStatus()
	{
		// print your character (profile data)
		System.out.println("Character: " + "Mr. Plum?");
		
		
		// print out response of game move
		System.out.println("Game Status: Your Turn.");
		
		// Print game controls
		System.out.println("Game Controls:");
		System.out.println(" 1. Make Player Move");
		System.out.println(" 2. Make Suggestion");
		System.out.println(" 3. Make Accusation");
		System.out.println(" 4. Help");
		System.out.println(" 5. Quit");
		
		
	}

  
  public void handleMessage(Message msg) {
    // add msg to queue to be popped off later within run()
    msgQueue.add(msg);
  }

  /**
   * @brief This is where all message "handling" should be done
   * @param msg 
   */
  public void handleMessageTerminalThread(Message msg) {
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

} // end of class TerminalClue
