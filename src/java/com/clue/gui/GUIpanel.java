package com.clue.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.clue.proto.Data;

public class GUIpanel extends JPanel implements ActionListener
{

	// Timer to trigger repainting the game grid 
    Timer timer=new Timer(1000, this);
    
	public static final Color cRoom = new Color(214,217,223);
	public static final Color cHallway = new Color(176,196,222);
	public static final Color cBlack = new Color(0,0,0);

	public static final Color cMsScarlett = new Color(220,20,60);
	public static final Color cColMustard = new Color(225,225,0);
	public static final Color cMrsWhite = new Color(255,255,255);
	public static final Color cMrGreen = new Color(0,201,87);
	public static final Color cMrsPeacock = new Color(67,110,238);
	public static final Color cProfPlum = new Color(142,56,142);

	// Suspect Positions;
	public int[] pMsScarlett = {-1,-1};
	public int[] pColMustard = {-1,-1};
	public int[] pMrsWhite = {-1,-1};
	public int[] pMrGreen = {-1,-1};
	public int[] pMrsPeacock = {-1,-1};
	public int[] pProfPlum = {-1,-1};

	public static final int NUM_ROWS = 15;
	public static final int NUM_COLS = 15;
	
	public static final int PREFERRED_GRID_SIZE_PIXELS = 10;
	
	private final Color[][] gameGrid;

    private JLabel StudyLabel;
    private JLabel HallLabel;
    private JLabel LoungeLabel;
    private JLabel LibraryLabel;
    private JLabel BilliardRoomLabel;
    private JLabel DiningRoomLabel;
    private JLabel ConservatoryLabel;
    private JLabel BallroomLabel;
    private JLabel KitchenLabel;

    private JLabel Hallway0Label;
    private JLabel Hallway1Label;
    private JLabel Hallway2Label;
    private JLabel Hallway3Label;
    private JLabel Hallway4Label;
    private JLabel Hallway5Label;
    private JLabel Hallway6Label;
    private JLabel Hallway7Label;
    private JLabel Hallway8Label;
    private JLabel Hallway9Label;
    private JLabel Hallway10Label;
    private JLabel Hallway11Label;
    
	
	public GUIpanel()
	{
		// Timer to trigger repainting the game grid 
		timer.start();// Start the timer here.
		
		// Set layout to 5 by 5 for 9 rooms with hallways between
		setLayout(new GridLayout(5,5));

		// Create Room Labels
		StudyLabel 			= new JLabel( "Study", SwingConstants.CENTER );
		HallLabel 			= new JLabel( "Hall", SwingConstants.CENTER );
		LoungeLabel 		= new JLabel( "Lounge", SwingConstants.CENTER );
		LibraryLabel 		= new JLabel( "Library", SwingConstants.CENTER );
		BilliardRoomLabel 	= new JLabel( "Billiard Room", SwingConstants.CENTER );
		DiningRoomLabel 	= new JLabel( "Dining Room", SwingConstants.CENTER );
		ConservatoryLabel 	= new JLabel( "Conservatory", SwingConstants.CENTER );
		BallroomLabel 		= new JLabel( "Ballroom", SwingConstants.CENTER );
		KitchenLabel 		= new JLabel( "Kitchen", SwingConstants.CENTER );
		Hallway0Label 		= new JLabel( "0", SwingConstants.CENTER );
		Hallway1Label 		= new JLabel( "1", SwingConstants.CENTER );
		Hallway2Label 		= new JLabel( "2", SwingConstants.CENTER );
		Hallway3Label 		= new JLabel( "3", SwingConstants.CENTER );
		Hallway4Label 		= new JLabel( "4", SwingConstants.CENTER );
		Hallway5Label 		= new JLabel( "5", SwingConstants.CENTER );
		Hallway6Label 		= new JLabel( "6", SwingConstants.CENTER );
		Hallway7Label 		= new JLabel( "7", SwingConstants.CENTER );
		Hallway8Label 		= new JLabel( "8", SwingConstants.CENTER );
		Hallway9Label 		= new JLabel( "9", SwingConstants.CENTER );
		Hallway10Label 		= new JLabel( "10", SwingConstants.CENTER );
		Hallway11Label 		= new JLabel( "11", SwingConstants.CENTER );
		
		// Set Room Label fonts
		StudyLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		HallLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		LoungeLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		LibraryLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		BilliardRoomLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		DiningRoomLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		ConservatoryLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		BallroomLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		KitchenLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		
		// Add Room Labels to game board
		add(StudyLabel);
		add(Hallway0Label);
		add(LibraryLabel);
		add(Hallway1Label);
		add(HallLabel);
		add(Hallway2Label); add(new JLabel( " " )); add(Hallway3Label); add(new JLabel( " " )); add(Hallway4Label);
		add(LoungeLabel);
		add(Hallway5Label);
		add(BilliardRoomLabel);
		add(Hallway6Label);
		add(DiningRoomLabel);
		add(Hallway7Label); add(new JLabel( " " )); add(Hallway8Label); add(new JLabel( " " )); add(Hallway9Label);
		add(ConservatoryLabel);
		add(Hallway10Label);
		add(BallroomLabel);
		add(Hallway11Label);
		add(KitchenLabel);
		
		// Color Grid of Game Board
		this.gameGrid = new Color[NUM_ROWS][NUM_COLS];
		
		// Initialize board with rooms and hallways
		redrawBoardColor();
		
		// Initialize Player Color positions
		//initializePlayerColors();
		
	}
	
	public void movePlayer(Data.Suspect suspect, Data.Location location)
	{
		// Suspects
		Data.Suspect dataMsScarlett = Data.Suspect.SUS_MISS_SCARLETT;
		Data.Suspect dataColMustard = Data.Suspect.SUS_COL_MUSTARD;
		Data.Suspect dataMrsWhite = Data.Suspect.SUS_MRS_WHITE;
		Data.Suspect dataMrGreen = Data.Suspect.SUS_MR_GREEN;
		Data.Suspect dataMrsPeacock = Data.Suspect.SUS_MRS_PEACOCK;
		Data.Suspect dataProfPlum = Data.Suspect.SUS_PROF_PLUM;
		
		// Rooms
		Data.Location dataBallroom = Data.Location.LOC_BALLROOM;
		Data.Location dataBilliardRoom = Data.Location.LOC_BILLIARD_ROOM;
		Data.Location dataConservatory = Data.Location.LOC_CONSERVATORY;
		Data.Location dataDiningRoom = Data.Location.LOC_DINING_ROOM;
		Data.Location dataHall = Data.Location.LOC_HALL;
		Data.Location dataKitchen = Data.Location.LOC_KITCHEN;
		Data.Location dataLibrary = Data.Location.LOC_LIBRARY;
		Data.Location dataLounge = Data.Location.LOC_LOUNGE;
		Data.Location dataStudy = Data.Location.LOC_STUDY;
		Data.Location dataHallway0 = Data.Location.LOC_HALLWAY_0;
		Data.Location dataHallway1 = Data.Location.LOC_HALLWAY_1;
		Data.Location dataHallway2 = Data.Location.LOC_HALLWAY_2;
		Data.Location dataHallway3 = Data.Location.LOC_HALLWAY_3;
		Data.Location dataHallway4 = Data.Location.LOC_HALLWAY_4;
		Data.Location dataHallway5 = Data.Location.LOC_HALLWAY_5;
		Data.Location dataHallway6 = Data.Location.LOC_HALLWAY_6;
		Data.Location dataHallway7 = Data.Location.LOC_HALLWAY_7;
		Data.Location dataHallway8 = Data.Location.LOC_HALLWAY_8;
		Data.Location dataHallway9 = Data.Location.LOC_HALLWAY_9;
		Data.Location dataHallway10 = Data.Location.LOC_HALLWAY_10;
		Data.Location dataHallway11 = Data.Location.LOC_HALLWAY_11;
		
		// Place Ms. Scarlett in Upper Left Corner of Room Or Center of Hallway
		if (suspect == dataMsScarlett)
		{
			if 		(location == dataBallroom) 		{ pMsScarlett[0]=12; 	pMsScarlett[1]=6; }
			else if (location == dataBilliardRoom) 	{ pMsScarlett[0]=6; 	pMsScarlett[1]=6; }
			else if (location == dataConservatory) 	{ pMsScarlett[0]=12;	pMsScarlett[1]=0; }
			else if (location == dataDiningRoom) 	{ pMsScarlett[0]=6; 	pMsScarlett[1]=12; }
			else if (location == dataHall) 			{ pMsScarlett[0]=0;		pMsScarlett[1]=12; }
			else if (location == dataKitchen) 		{ pMsScarlett[0]=12; 	pMsScarlett[1]=12; }
			else if (location == dataLibrary) 		{ pMsScarlett[0]=0;		pMsScarlett[1]=6; }
			else if (location == dataLounge) 		{ pMsScarlett[0]=6; 	pMsScarlett[1]=0; }
			else if (location == dataStudy) 		{ pMsScarlett[0]=0;		pMsScarlett[1]=0; }
			else if (location == dataHallway0) 		{ pMsScarlett[0]=1; 	pMsScarlett[1]=4; }
			else if (location == dataHallway1) 		{ pMsScarlett[0]=1;		pMsScarlett[1]=10; }
			else if (location == dataHallway2) 		{ pMsScarlett[0]=4; 	pMsScarlett[1]=1; }
			else if (location == dataHallway3) 		{ pMsScarlett[0]=4;		pMsScarlett[1]=7; }
			else if (location == dataHallway4) 		{ pMsScarlett[0]=4; 	pMsScarlett[1]=13; }
			else if (location == dataHallway5) 		{ pMsScarlett[0]=7;		pMsScarlett[1]=4; }
			else if (location == dataHallway6) 		{ pMsScarlett[0]=7; 	pMsScarlett[1]=10; }
			else if (location == dataHallway7) 		{ pMsScarlett[0]=10;	pMsScarlett[1]=1; }
			else if (location == dataHallway8) 		{ pMsScarlett[0]=10; 	pMsScarlett[1]=7; }
			else if (location == dataHallway9) 		{ pMsScarlett[0]=10;	pMsScarlett[1]=13; }
			else if (location == dataHallway10)		{ pMsScarlett[0]=13; 	pMsScarlett[1]=4; }
			else if (location == dataHallway11)		{ pMsScarlett[0]=13;	pMsScarlett[1]=10; }
		}
		// Place Col. Mustard in Upper Middle of Room Or Center of Hallway
		else if (suspect == dataColMustard)
		{
			if 		(location == dataBallroom) 		{ pColMustard[0]=12; 	pColMustard[1]=6+1; }
			else if (location == dataBilliardRoom) 	{ pColMustard[0]=6; 	pColMustard[1]=6+1; }
			else if (location == dataConservatory) 	{ pColMustard[0]=12;	pColMustard[1]=0+1; }
			else if (location == dataDiningRoom) 	{ pColMustard[0]=6; 	pColMustard[1]=12+1; }
			else if (location == dataHall) 			{ pColMustard[0]=0;		pColMustard[1]=12+1; }
			else if (location == dataKitchen) 		{ pColMustard[0]=12; 	pColMustard[1]=12+1; }
			else if (location == dataLibrary) 		{ pColMustard[0]=0;		pColMustard[1]=6+1; }
			else if (location == dataLounge) 		{ pColMustard[0]=6; 	pColMustard[1]=0+1; }
			else if (location == dataStudy) 		{ pColMustard[0]=0;		pColMustard[1]=0+1; }
			else if (location == dataHallway0) 		{ pColMustard[0]=1; 	pColMustard[1]=4; }
			else if (location == dataHallway1) 		{ pColMustard[0]=1;		pColMustard[1]=10; }
			else if (location == dataHallway2) 		{ pColMustard[0]=4; 	pColMustard[1]=1; }
			else if (location == dataHallway3) 		{ pColMustard[0]=4;		pColMustard[1]=7; }
			else if (location == dataHallway4) 		{ pColMustard[0]=4; 	pColMustard[1]=13; }
			else if (location == dataHallway5) 		{ pColMustard[0]=7;		pColMustard[1]=4; }
			else if (location == dataHallway6) 		{ pColMustard[0]=7; 	pColMustard[1]=10; }
			else if (location == dataHallway7) 		{ pColMustard[0]=10;	pColMustard[1]=1; }
			else if (location == dataHallway8) 		{ pColMustard[0]=10; 	pColMustard[1]=7; }
			else if (location == dataHallway9) 		{ pColMustard[0]=10;	pColMustard[1]=13; }
			else if (location == dataHallway10)		{ pColMustard[0]=13; 	pColMustard[1]=4; }
			else if (location == dataHallway11)		{ pColMustard[0]=13;	pColMustard[1]=10; }
		}
		// Place Mrs. White in Upper Right of Room Or Center of Hallway
		else if (suspect == dataMrsWhite)
		{
			if 		(location == dataBallroom) 		{ pMrsWhite[0]=12; 		pMrsWhite[1]=6+2; }
			else if (location == dataBilliardRoom) 	{ pMrsWhite[0]=6; 		pMrsWhite[1]=6+2; }
			else if (location == dataConservatory) 	{ pMrsWhite[0]=12;		pMrsWhite[1]=0+2; }
			else if (location == dataDiningRoom) 	{ pMrsWhite[0]=6; 		pMrsWhite[1]=12+2; }
			else if (location == dataHall) 			{ pMrsWhite[0]=0;		pMrsWhite[1]=12+2; }
			else if (location == dataKitchen) 		{ pMrsWhite[0]=12; 		pMrsWhite[1]=12+2; }
			else if (location == dataLibrary) 		{ pMrsWhite[0]=0;		pMrsWhite[1]=6+2; }
			else if (location == dataLounge) 		{ pMrsWhite[0]=6; 		pMrsWhite[1]=0+2; }
			else if (location == dataStudy) 		{ pMrsWhite[0]=0;		pMrsWhite[1]=0+2; }
			else if (location == dataHallway0) 		{ pMrsWhite[0]=1; 		pMrsWhite[1]=4; }
			else if (location == dataHallway1) 		{ pMrsWhite[0]=1;		pMrsWhite[1]=10; }
			else if (location == dataHallway2) 		{ pMrsWhite[0]=4; 		pMrsWhite[1]=1; }
			else if (location == dataHallway3) 		{ pMrsWhite[0]=4;		pMrsWhite[1]=7; }
			else if (location == dataHallway4) 		{ pMrsWhite[0]=4; 		pMrsWhite[1]=13; }
			else if (location == dataHallway5) 		{ pMrsWhite[0]=7;		pMrsWhite[1]=4; }
			else if (location == dataHallway6) 		{ pMrsWhite[0]=7; 		pMrsWhite[1]=10; }
			else if (location == dataHallway7) 		{ pMrsWhite[0]=10;		pMrsWhite[1]=1; }
			else if (location == dataHallway8) 		{ pMrsWhite[0]=10; 		pMrsWhite[1]=7; }
			else if (location == dataHallway9) 		{ pMrsWhite[0]=10;		pMrsWhite[1]=13; }
			else if (location == dataHallway10)		{ pMrsWhite[0]=13; 		pMrsWhite[1]=4; }
			else if (location == dataHallway11)		{ pMrsWhite[0]=13;		pMrsWhite[1]=10; }
		}
		// Place Mr. Green in Lower Left Corner of Room Or Center of Hallway
		else if (suspect == dataMrGreen)
		{
			if 		(location == dataBallroom) 		{ pMrGreen[0]=12+2; 	pMrGreen[1]=6; }
			else if (location == dataBilliardRoom) 	{ pMrGreen[0]=6+2; 		pMrGreen[1]=6; }
			else if (location == dataConservatory) 	{ pMrGreen[0]=12+2;		pMrGreen[1]=0; }
			else if (location == dataDiningRoom) 	{ pMrGreen[0]=6+2; 		pMrGreen[1]=12; }
			else if (location == dataHall) 			{ pMrGreen[0]=0+2;		pMrGreen[1]=12; }
			else if (location == dataKitchen) 		{ pMrGreen[0]=12+2; 	pMrGreen[1]=12; }
			else if (location == dataLibrary) 		{ pMrGreen[0]=0+2;		pMrGreen[1]=6; }
			else if (location == dataLounge) 		{ pMrGreen[0]=6+2; 		pMrGreen[1]=0; }
			else if (location == dataStudy) 		{ pMrGreen[0]=0+2;		pMrGreen[1]=0; }
			else if (location == dataHallway0) 		{ pMrGreen[0]=1; 		pMrGreen[1]=4; }
			else if (location == dataHallway1) 		{ pMrGreen[0]=1;		pMrGreen[1]=10; }
			else if (location == dataHallway2) 		{ pMrGreen[0]=4; 		pMrGreen[1]=1; }
			else if (location == dataHallway3) 		{ pMrGreen[0]=4;		pMrGreen[1]=7; }
			else if (location == dataHallway4) 		{ pMrGreen[0]=4; 		pMrGreen[1]=13; }
			else if (location == dataHallway5) 		{ pMrGreen[0]=7;		pMrGreen[1]=4; }
			else if (location == dataHallway6) 		{ pMrGreen[0]=7; 		pMrGreen[1]=10; }
			else if (location == dataHallway7) 		{ pMrGreen[0]=10;		pMrGreen[1]=1; }
			else if (location == dataHallway8) 		{ pMrGreen[0]=10; 		pMrGreen[1]=7; }
			else if (location == dataHallway9) 		{ pMrGreen[0]=10;		pMrGreen[1]=13; }
			else if (location == dataHallway10)		{ pMrGreen[0]=13; 		pMrGreen[1]=4; }
			else if (location == dataHallway11)		{ pMrGreen[0]=13;		pMrGreen[1]=10; }
		}
		// Place Mrs. Peacock in Lower Middle of Room Or Center of Hallway
		else if (suspect == dataMrsPeacock)
		{
			if 		(location == dataBallroom) 		{ pMrsPeacock[0]=12+2; 	pMrsPeacock[1]=6+1; }
			else if (location == dataBilliardRoom) 	{ pMrsPeacock[0]=6+2; 	pMrsPeacock[1]=6+1; }
			else if (location == dataConservatory) 	{ pMrsPeacock[0]=12+2;	pMrsPeacock[1]=0+1; }
			else if (location == dataDiningRoom) 	{ pMrsPeacock[0]=6+2; 	pMrsPeacock[1]=12+1; }
			else if (location == dataHall) 			{ pMrsPeacock[0]=0+2;	pMrsPeacock[1]=12+1; }
			else if (location == dataKitchen) 		{ pMrsPeacock[0]=12+2; 	pMrsPeacock[1]=12+1; }
			else if (location == dataLibrary) 		{ pMrsPeacock[0]=0+2;	pMrsPeacock[1]=6+1; }
			else if (location == dataLounge) 		{ pMrsPeacock[0]=6+2; 	pMrsPeacock[1]=0+1; }
			else if (location == dataStudy) 		{ pMrsPeacock[0]=0+2;	pMrsPeacock[1]=0+1; }
			else if (location == dataHallway0) 		{ pMrsPeacock[0]=1; 	pMrsPeacock[1]=4; }
			else if (location == dataHallway1) 		{ pMrsPeacock[0]=1;		pMrsPeacock[1]=10; }
			else if (location == dataHallway2) 		{ pMrsPeacock[0]=4; 	pMrsPeacock[1]=1; }
			else if (location == dataHallway3) 		{ pMrsPeacock[0]=4;		pMrsPeacock[1]=7; }
			else if (location == dataHallway4) 		{ pMrsPeacock[0]=4; 	pMrsPeacock[1]=13; }
			else if (location == dataHallway5) 		{ pMrsPeacock[0]=7;		pMrsPeacock[1]=4; }
			else if (location == dataHallway6) 		{ pMrsPeacock[0]=7; 	pMrsPeacock[1]=10; }
			else if (location == dataHallway7) 		{ pMrsPeacock[0]=10;	pMrsPeacock[1]=1; }
			else if (location == dataHallway8) 		{ pMrsPeacock[0]=10; 	pMrsPeacock[1]=7; }
			else if (location == dataHallway9) 		{ pMrsPeacock[0]=10;	pMrsPeacock[1]=13; }
			else if (location == dataHallway10)		{ pMrsPeacock[0]=13; 	pMrsPeacock[1]=4; }
			else if (location == dataHallway11)		{ pMrsPeacock[0]=13;	pMrsPeacock[1]=10; }
		}
		// Place Prof. Plum in Lower Right of Room Or Center of Hallway
		else if (suspect == dataProfPlum)
		{
			if 		(location == dataBallroom) 		{ pProfPlum[0]=12+2; 	pProfPlum[1]=6+2; }
			else if (location == dataBilliardRoom) 	{ pProfPlum[0]=6+2; 	pProfPlum[1]=6+2; }
			else if (location == dataConservatory) 	{ pProfPlum[0]=12+2;	pProfPlum[1]=0+2; }
			else if (location == dataDiningRoom) 	{ pProfPlum[0]=6+2; 	pProfPlum[1]=12+2; }
			else if (location == dataHall) 			{ pProfPlum[0]=0+2;		pProfPlum[1]=12+2; }
			else if (location == dataKitchen) 		{ pProfPlum[0]=12+2; 	pProfPlum[1]=12+2; }
			else if (location == dataLibrary) 		{ pProfPlum[0]=0+2;		pProfPlum[1]=6+2; }
			else if (location == dataLounge) 		{ pProfPlum[0]=6+2; 	pProfPlum[1]=0+2; }
			else if (location == dataStudy) 		{ pProfPlum[0]=0+2;		pProfPlum[1]=0+2; }
			else if (location == dataHallway0) 		{ pProfPlum[0]=1; 		pProfPlum[1]=4; }
			else if (location == dataHallway1) 		{ pProfPlum[0]=1;		pProfPlum[1]=10; }
			else if (location == dataHallway2) 		{ pProfPlum[0]=4; 		pProfPlum[1]=1; }
			else if (location == dataHallway3) 		{ pProfPlum[0]=4;		pProfPlum[1]=7; }
			else if (location == dataHallway4) 		{ pProfPlum[0]=4; 		pProfPlum[1]=13; }
			else if (location == dataHallway5) 		{ pProfPlum[0]=7;		pProfPlum[1]=4; }
			else if (location == dataHallway6) 		{ pProfPlum[0]=7; 		pProfPlum[1]=10; }
			else if (location == dataHallway7) 		{ pProfPlum[0]=10;		pProfPlum[1]=1; }
			else if (location == dataHallway8) 		{ pProfPlum[0]=10; 		pProfPlum[1]=7; }
			else if (location == dataHallway9) 		{ pProfPlum[0]=10;		pProfPlum[1]=13; }
			else if (location == dataHallway10)		{ pProfPlum[0]=13; 		pProfPlum[1]=4; }
			else if (location == dataHallway11)		{ pProfPlum[0]=13;		pProfPlum[1]=10; }
		}
	}
	
	// Listen for timer to repaint the game grid 
	public void actionPerformed(ActionEvent ev)
	{
	    if ( ev.getSource()==timer )
	    { 
	    	// this will call at every 1 second
	    	repaint();
	    }
	}
	
	// Set board colors based on room vs. hallway
	public void redrawBoardColor()
	{

		for (int i=0; i<NUM_ROWS; i++)
		{
			for (int j=0; j<NUM_COLS; j++)
			{
				if (i<3 && j<3)
				{
					this.gameGrid[i][j]=cRoom;
				}
				else if (i<3 && j<6)
				{
					this.gameGrid[i][j]=cHallway;
				}
				else if (i<3 && j<9)
				{
					this.gameGrid[i][j]=cRoom;
				}
				else if (i<3 && j<12)
				{
					this.gameGrid[i][j]=cHallway;
				}
				else if (i<3 && j<15)
				{
					this.gameGrid[i][j]=cRoom;
				}
				else if (i>2 && i<6)
				{
					this.gameGrid[i][j]=cHallway;
				}
				else if (i<9 && j<3)
				{
					this.gameGrid[i][j]=cRoom;
				}
				else if (i<9 && j<6)
				{
					this.gameGrid[i][j]=cHallway;
				}
				else if (i<9 && j<9)
				{
					this.gameGrid[i][j]=cRoom;
				}
				else if (i<9 && j<12)
				{
					this.gameGrid[i][j]=cHallway;
				}
				else if (i<9 && j<15)
				{
					this.gameGrid[i][j]=cRoom;
				}
				else if (i>8 && i<12)
				{
					this.gameGrid[i][j]=cHallway;
				}
				else if (i<15 && j<3)
				{
					this.gameGrid[i][j]=cRoom;
				}
				else if (i<15 && j<6)
				{
					this.gameGrid[i][j]=cHallway;
				}
				else if (i<15 && j<9)
				{
					this.gameGrid[i][j]=cRoom;
				}
				else if (i<15 && j<12)
				{
					this.gameGrid[i][j]=cHallway;
				}
				else if (i<15 && j<15)
				{
					this.gameGrid[i][j]=cRoom;
				}
				else
				{
					this.gameGrid[i][j]=cBlack;
				}
			}
		}
		
		// Top
		gameGrid[0][3]=cBlack; gameGrid[0][4]=cBlack; gameGrid[0][5]=cBlack;
		gameGrid[1][3]=cHallway; gameGrid[1][4]=cHallway; gameGrid[1][5]=cHallway;
		gameGrid[2][3]=cBlack; gameGrid[2][4]=cBlack; gameGrid[2][5]=cBlack;

		gameGrid[0][9]=cBlack; gameGrid[0][10]=cBlack; gameGrid[0][11]=cBlack;
		gameGrid[1][9]=cHallway; gameGrid[1][10]=cHallway; gameGrid[1][11]=cHallway;
		gameGrid[2][9]=cBlack; gameGrid[2][10]=cBlack; gameGrid[2][11]=cBlack;

		// 2nd
		gameGrid[3][0]=cBlack; gameGrid[3][1]=cHallway; gameGrid[3][2]=cBlack;
		gameGrid[4][0]=cBlack; gameGrid[4][1]=cHallway; gameGrid[4][2]=cBlack;
		gameGrid[5][0]=cBlack; gameGrid[5][1]=cHallway; gameGrid[5][2]=cBlack;
		
		gameGrid[3][3]=cBlack; gameGrid[3][4]=cBlack; gameGrid[3][5]=cBlack;
		gameGrid[4][3]=cBlack; gameGrid[4][4]=cBlack; gameGrid[4][5]=cBlack;
		gameGrid[5][3]=cBlack; gameGrid[5][4]=cBlack; gameGrid[5][5]=cBlack;

		gameGrid[3][6]=cBlack; gameGrid[3][7]=cHallway; gameGrid[3][8]=cBlack;
		gameGrid[4][6]=cBlack; gameGrid[4][7]=cHallway; gameGrid[4][8]=cBlack;
		gameGrid[5][6]=cBlack; gameGrid[5][7]=cHallway; gameGrid[5][8]=cBlack;

		gameGrid[3][9]=cBlack; gameGrid[3][10]=cBlack; gameGrid[3][11]=cBlack;
		gameGrid[4][9]=cBlack; gameGrid[4][10]=cBlack; gameGrid[4][11]=cBlack;
		gameGrid[5][9]=cBlack; gameGrid[5][10]=cBlack; gameGrid[5][11]=cBlack;

		gameGrid[3][12]=cBlack; gameGrid[3][13]=cHallway; gameGrid[3][14]=cBlack;
		gameGrid[4][12]=cBlack; gameGrid[4][13]=cHallway; gameGrid[4][14]=cBlack;
		gameGrid[5][12]=cBlack; gameGrid[5][13]=cHallway; gameGrid[5][14]=cBlack;

		// 3rd
		gameGrid[6][3]=cBlack; gameGrid[6][4]=cBlack; gameGrid[6][5]=cBlack;
		gameGrid[7][3]=cHallway; gameGrid[7][4]=cHallway; gameGrid[7][5]=cHallway;
		gameGrid[8][3]=cBlack; gameGrid[8][4]=cBlack; gameGrid[8][5]=cBlack;

		gameGrid[6][9]=cBlack; gameGrid[6][10]=cBlack; gameGrid[6][11]=cBlack;
		gameGrid[7][9]=cHallway; gameGrid[7][10]=cHallway; gameGrid[7][11]=cHallway;
		gameGrid[8][9]=cBlack; gameGrid[8][10]=cBlack; gameGrid[8][11]=cBlack;
		
		// 4th
		gameGrid[9][0]=cBlack; gameGrid[9][1]=cHallway; gameGrid[9][2]=cBlack;
		gameGrid[10][0]=cBlack; gameGrid[10][1]=cHallway; gameGrid[10][2]=cBlack;
		gameGrid[11][0]=cBlack; gameGrid[11][1]=cHallway; gameGrid[11][2]=cBlack;
		
		gameGrid[9][3]=cBlack; gameGrid[9][4]=cBlack; gameGrid[9][5]=cBlack;
		gameGrid[10][3]=cBlack; gameGrid[10][4]=cBlack; gameGrid[10][5]=cBlack;
		gameGrid[11][3]=cBlack; gameGrid[11][4]=cBlack; gameGrid[11][5]=cBlack;

		gameGrid[9][6]=cBlack; gameGrid[9][7]=cHallway; gameGrid[9][8]=cBlack;
		gameGrid[10][6]=cBlack; gameGrid[10][7]=cHallway; gameGrid[10][8]=cBlack;
		gameGrid[11][6]=cBlack; gameGrid[11][7]=cHallway; gameGrid[11][8]=cBlack;

		gameGrid[9][9]=cBlack; gameGrid[9][10]=cBlack; gameGrid[9][11]=cBlack;
		gameGrid[10][9]=cBlack; gameGrid[10][10]=cBlack; gameGrid[10][11]=cBlack;
		gameGrid[11][9]=cBlack; gameGrid[11][10]=cBlack; gameGrid[11][11]=cBlack;
		
		gameGrid[9][12]=cBlack; gameGrid[9][13]=cHallway; gameGrid[9][14]=cBlack;
		gameGrid[10][12]=cBlack; gameGrid[10][13]=cHallway; gameGrid[10][14]=cBlack;
		gameGrid[11][12]=cBlack; gameGrid[11][13]=cHallway; gameGrid[11][14]=cBlack;

		// 5th
		gameGrid[12][3]=cBlack; gameGrid[12][4]=cBlack; gameGrid[12][5]=cBlack;
		gameGrid[13][3]=cHallway; gameGrid[13][4]=cHallway; gameGrid[13][5]=cHallway;
		gameGrid[14][3]=cBlack; gameGrid[14][4]=cBlack; gameGrid[14][5]=cBlack;

		gameGrid[12][9]=cBlack; gameGrid[12][10]=cBlack; gameGrid[12][11]=cBlack;
		gameGrid[13][9]=cHallway; gameGrid[13][10]=cHallway; gameGrid[13][11]=cHallway;
		gameGrid[14][9]=cBlack; gameGrid[14][10]=cBlack; gameGrid[14][11]=cBlack;
		
		// Color player locations
		if (pMsScarlett[0]>-1 && pMsScarlett[1]>-1)
		{
			gameGrid[pMsScarlett[0]][pMsScarlett[1]]=cMsScarlett;
		}

		if (pColMustard[0]>-1 && pColMustard[1]>-1)
		{
			gameGrid[pColMustard[0]][pColMustard[1]]=cColMustard;
		}

		if (pMrsWhite[0]>-1 && pMrsWhite[1]>-1)
		{
			gameGrid[pMrsWhite[0]][pMrsWhite[1]]=cMrsWhite;
		}

		if (pMrGreen[0]>-1 && pMrGreen[1]>-1)
		{
			gameGrid[pMrGreen[0]][pMrGreen[1]]=cMrGreen;
		}

		if (pMrsPeacock[0]>-1 && pMrsPeacock[1]>-1)
		{
			gameGrid[pMrsPeacock[0]][pMrsPeacock[1]]=cMrsPeacock;
		}

		if (pProfPlum[0]>-1 && pProfPlum[1]>-1)
		{
			gameGrid[pProfPlum[0]][pProfPlum[1]]=cProfPlum;
		}
	}
    
	@Override
	// Make sure grid gets painted like we want
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        
        // Redraw gameGrid
        redrawBoardColor();
        
        //System.out.println("In My paintComponent");
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / NUM_COLS;
        int rectHeight = getHeight() / NUM_ROWS;

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                // Upper left corner of this terrain rect
                int x = j * rectWidth;
                int y = i * rectHeight;
                Color clueMapColor = gameGrid[i][j];
                g.setColor(clueMapColor);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }
}
