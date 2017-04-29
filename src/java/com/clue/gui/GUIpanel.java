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
		add(new JLabel( " " ));
		add(LibraryLabel);
		add(new JLabel( " " ));
		add(HallLabel);
		add(new JLabel( " " )); add(new JLabel( " " )); add(new JLabel( " " )); add(new JLabel( " " )); add(new JLabel( " " ));
		add(LoungeLabel);
		add(new JLabel( " " ));
		add(BilliardRoomLabel);
		add(new JLabel( " " ));
		add(DiningRoomLabel);
		add(new JLabel( " " )); add(new JLabel( " " )); add(new JLabel( " " )); add(new JLabel( " " )); add(new JLabel( " " ));
		add(ConservatoryLabel);
		add(new JLabel( " " ));
		add(BallroomLabel);
		add(new JLabel( " " ));
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
