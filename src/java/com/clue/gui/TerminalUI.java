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
  
  private int clueBoard[][];
	
	private int boardXDimension = 39;
	private int boardYDimension = 21;
	
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
  }
	
	public void buildClueBoard()
	{
		// instantiate the array
		clueBoard = new int [boardYDimension][boardXDimension];
		
		for ( int i=0; i<boardYDimension; i++ )
		{
			for ( int j=0; j<boardXDimension; j++ )
			{
				clueBoard[i][j] = 1;
			}
		}
		
	} // end of buildclueBoard
	
	
	public void buildClueRooms()
	{
		// Build the Study
		for ( int i=2; i<6; i++ )
		{
			for ( int j=2; j<9; j++ )
			{
				clueBoard[i][j] = 2;
			}
		} 
		
		// Build the Study Room empty space
		for (int i=3; i<5; i++)
		{ 			
			for( int j=3; j<8; j++)
			{
				clueBoard[i][j] = 5;
			}
		}
		
		
		// Build the Hall
		for ( int i=2; i<6; i++)
		{
			for ( int j=16; j<23; j++)
			{
				clueBoard[i][j] = 2;
			}
		}
		
		// Build the Hall empty space
		for ( int i=3; i<5; i++)
		{
			for ( int j=17; j<22; j++)
			{
				clueBoard[i][j] = 5;
			}
		}
		
		// Build the Lounge
		for ( int i=2; i<6; i++)
		{
			for ( int j=30; j<37; j++)
			{
				clueBoard[i][j] = 2;
			}
		}
		
		//Build the Lounge empty space
		for ( int i=3; i<5; i++)
		{
			for ( int j=31; j<36; j++)
			{
				clueBoard[i][j] = 5;
			}
		}
		
		// Build the Library
		for ( int i=9; i<13; i++)
		{
			for ( int j=2; j<9; j++)
			{
				clueBoard[i][j] = 2;
			}
		}
		
		//Build the Library empty space
		for ( int i=10; i<12; i++)
		{
			for ( int j=3; j<8; j++)
			{
				clueBoard[i][j] = 5;
			}
		}
		
		// Build the Billiard Room
		for ( int i=9; i<13; i++)
		{
			for ( int j=16; j<23; j++)
			{
				clueBoard[i][j] = 2;
			}
		}
		
		//Build the Billiard Room empty space
		for ( int i=10; i<12; i++)
		{
			for ( int j=17; j<22; j++)
			{
				clueBoard[i][j] = 5;
			}
		}
		
		
		// Build the Dining Room
		for ( int i=9; i<13; i++)
		{
			for ( int j=30; j<37; j++)
			{
				clueBoard[i][j] = 2;
			}
		}
		
		//Build the Dining Room empty space
		for ( int i=10; i<12; i++)
		{
			for ( int j=31; j<36; j++)
			{
				clueBoard[i][j] = 5;
			}
		}
		
		// Build the Conservatory
		for ( int i=16; i<20; i++)
		{
			for ( int j=2; j<9; j++)
			{
				clueBoard[i][j] = 2;
			}
		}
		
		//Build the Conservatory empty space
		for ( int i=17; i<19; i++)
		{
			for ( int j=3; j<8; j++)
			{
				clueBoard[i][j] = 5;
			}
		}
		
		// Build the Ballroom
		for ( int i=16; i<20; i++)
		{
			for ( int j=16; j<23; j++)
			{
				clueBoard[i][j] = 2;
			}
		}
		
		//Build the Ballroom empty space
		for ( int i=17; i<19; i++)
		{
			for ( int j=17; j<22; j++)
			{
				clueBoard[i][j] = 5;
			}
		}
		
		// Build the Kitchen
		for ( int i=16; i<20; i++)
		{
			for ( int j=30; j<37; j++)
			{
				clueBoard[i][j] = 2;
			}
		}
		
		//Build the Kitchen empty space
		for ( int i=17; i<19; i++)
		{
			for ( int j=31; j<36; j++)
			{
				clueBoard[i][j] = 5;
			}
		}
		
			
	}
	
	public void buildClueHallways()
	{
		// Build the Study-Hall Hallway Passage
		for ( int i=3; i<5; i++)
		{
			for ( int j=9; j<16; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		
		// Build the Hall-Lounge Room Hallway Passage
		for ( int i=3; i<5; i++)
		{
			for ( int j=23; j<30; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		// Build the Library Room-Billiard Room Hallway Passage
		for ( int i=10; i<12; i++)
		{
			for ( int j=9; j<16; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		// Build the Billiard Room-Dining Room Hallway Passage
		for ( int i=10; i<12; i++)
		{
			for ( int j=23; j<30; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		// Build the Conservatory-Ballroom Hallway Passage
		for ( int i=17; i<19; i++)
		{
			for ( int j=9; j<16; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		// Build the Ballroom-Kitchen Hallway Passage
		for ( int i=17; i<19; i++)
		{
			for ( int j=23; j<30; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		
		// Build the Study-Library Hallway Passage
		for ( int i=6; i<9; i++)
		{
			for ( int j=4; j<7; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		// Build the Hall-Billiard Room Hallway Passage
		for ( int i=6; i<9; i++)
		{
			for ( int j=18; j<21; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		// Build the Lounge-Dining Room Hallway Passage
		for ( int i=6; i<9; i++)
		{
			for ( int j=32; j<35; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		// Build the Library-Conservatory Hallway Passage
		for ( int i=13; i<16; i++)
		{
			for ( int j=4; j<7; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		// Build the Billiard Room-Ballroom Hallway Passage
		for ( int i=13; i<16; i++)
		{
			for ( int j=18; j<21; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
		
		// Build the Dining Room-Kitchen Hallway Passage
		for ( int i=13; i<16; i++)
		{
			for ( int j=32; j<35; j++)
			{
				clueBoard[i][j] = 4;
			}
		}
	}
	
	
	public void printClueBoard()
	{
		System.out.println();
		
		for ( int i=0; i<boardYDimension; i++)
		{
			for ( int j=0; j<boardXDimension; j++)
			{
				if ( clueBoard[i][j] == 1 )
				{ // background grid
					
					System.out.print(" ");
				}
				else if (clueBoard[i][j] == 2)
				{ // rooms
					
					System.out.print("0");
				}
				else if (clueBoard[i][j] == 3)
				{ // not being used
					
					System.out.print("=");
				}
				else if (clueBoard[i][j] == 4)
				{ // hallway passage
					
					System.out.print("+");
				}
				else if (clueBoard[i][j] == 5)
				{ // empty space inside of rooms
					
					System.out.print(" ");
				}
			}
			
			System.out.println();
		}
		
		System.out.println();
	} // end of printClueBoard()
	
	public void printGameStatus()
	{
		// print out response of game move
		
		System.out.println("Game Status: Your Turn.");
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
