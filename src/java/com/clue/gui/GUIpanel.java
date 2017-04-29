package com.clue.gui;

import java.awt.*;
import javax.swing.*;

public class GUIpanel extends JPanel 
{
	public static final Color cRoom = new Color(214,217,223);
	public static final Color cHallway = new Color(176,196,222);
	public static final Color cBlack = new Color(0,0,0);

	public static final Color cMsScarlett = new Color(220,20,60);
	public static final Color cColMustard = new Color(225,225,0);
	public static final Color cMrsWhite = new Color(255,255,255);
	public static final Color cMrGreen = new Color(0,201,87);
	public static final Color cMrsPeacock = new Color(67,110,238);
	public static final Color cProfPlum = new Color(142,56,142);

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
		initializeBoardColor();
		
		// Initialize Player Color positions
		initializePlayerColors();
		
	}
	

	// Initialize player locations to center room for now
	private void initializePlayerColors()
	{
		this.gameGrid[6][6]=cMsScarlett;
		this.gameGrid[6][7]=cColMustard;
		this.gameGrid[6][8]=cMrsWhite;
		this.gameGrid[8][6]=cMrGreen;
		this.gameGrid[8][7]=cMrsPeacock;
		this.gameGrid[8][8]=cProfPlum;
	}
	
	// Set board colors based on room vs. hallway
	private void initializeBoardColor()
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
	}
    
	@Override
	// Make sure grid gets painted like we want
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        
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
