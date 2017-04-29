package com.clue.gui;

import java.awt.*;
import javax.swing.*;

public class NotebookPanel extends JPanel 
{
	// Heading Labels
    private JLabel NotebookLabel;
    private JLabel SuspectsLabel;
    private JLabel WeaponsLabel;
    private JLabel RoomsLabel;
    
    // Rooms
    private JLabel StudyLabel;
    private JLabel HallLabel;
    private JLabel LoungeLabel;
    private JLabel LibraryLabel;
    private JLabel BilliardRoomLabel;
    private JLabel DiningRoomLabel;
    private JLabel ConservatoryLabel;
    private JLabel BallroomLabel;
    private JLabel KitchenLabel;

    // Weapons
	private JLabel KnifeLabel;
	private JLabel CandlestickLabel;
	private JLabel RevolverLabel;
	private JLabel LeadPipeLabel;
	private JLabel RopeLabel;
	private JLabel WrenchLabel;

	// Suspect Labels
	private JLabel MrGreenLabel;
	private JLabel MrsWhiteLabel;
	private JLabel ColMustardLabel;
	private JLabel MissScarletLabel;
	private JLabel MrsPeacockLabel;
	private JLabel ProfPlumLabel;

	public NotebookPanel()
	{
		// Layout for notebook
		setLayout(new GridLayout(25,1));
		
		// Create Labels
		// Headings
		NotebookLabel 		= new JLabel( "Notebook   ", SwingConstants.CENTER );
		SuspectsLabel 		= new JLabel( "Suspects:" );
		WeaponsLabel 		= new JLabel( "Weapons:" );
		RoomsLabel 			= new JLabel( "Rooms:" );

		// Rooms
		StudyLabel 			= new JLabel( "Study" );
		HallLabel 			= new JLabel( "Hall" );
		LoungeLabel 		= new JLabel( "Lounge" );
		LibraryLabel 		= new JLabel( "Library" );
		BilliardRoomLabel 	= new JLabel( "Billiard Room" );
		DiningRoomLabel 	= new JLabel( "Dining Room" );
		ConservatoryLabel 	= new JLabel( "Conservatory" );
		BallroomLabel 		= new JLabel( "Ballroom" );
		KitchenLabel 		= new JLabel( "Kitchen" );
		
		// Weapons
		KnifeLabel			= new JLabel( "Knife" );
		CandlestickLabel	= new JLabel( "Candlestick" );
		RevolverLabel		= new JLabel( "Revolver" );
		LeadPipeLabel		= new JLabel( "Lead Pipe" );
		RopeLabel			= new JLabel( "Rope" );
		WrenchLabel			= new JLabel( "Wrench" );
		
		// Suspects
		MrGreenLabel		= new JLabel( "Mr. Green" );
		MrsWhiteLabel		= new JLabel( "Mrs. White" );
		ColMustardLabel		= new JLabel( "Col. Mustard" );
		MissScarletLabel	= new JLabel( "Miss. Scarlet" );
		MrsPeacockLabel		= new JLabel( "Mrs. Peacock" );
		ProfPlumLabel		= new JLabel( "Prof. Plum" );
		
		// Set Fonts for headings
		NotebookLabel.setFont(new Font("Serif", Font.BOLD, 24));
		SuspectsLabel.setFont(new Font("Serif", Font.BOLD, 18));
		WeaponsLabel.setFont(new Font("Serif", Font.BOLD, 18));
		RoomsLabel.setFont(new Font("Serif", Font.BOLD, 18));

		// Add labels to panel
		add(NotebookLabel);
		add(SuspectsLabel);
		add(MrGreenLabel);
		add(MrsWhiteLabel);
		add(ColMustardLabel);
		add(MissScarletLabel);
		add(MrsPeacockLabel);
		add(ProfPlumLabel);
		add(WeaponsLabel);
		add(KnifeLabel);
		add(CandlestickLabel);
		add(RevolverLabel);
		add(LeadPipeLabel);
		add(RopeLabel);
		add(WrenchLabel);
		add(RoomsLabel);
		add(StudyLabel);
	    add(HallLabel);
	    add(LoungeLabel);
	    add(LibraryLabel);
	    add(BilliardRoomLabel);
	    add(DiningRoomLabel);
	    add(ConservatoryLabel);
	    add(BallroomLabel);
	    add(KitchenLabel);
	}
	
	// Function for striking through text of clues that are known to not be in case file
	public void strikeThrough()
	{
		ProfPlumLabel.setText("<html><strike>Prof. Plum</strike></html>");
		RevolverLabel.setText("<html><strike>Revolver</strike></html>");
		WrenchLabel.setText("<html><strike>Wrench</strike></html>");
		LoungeLabel.setText("<html><strike>Lounge</strike></html>");
		BallroomLabel.setText("<html><strike>Ballroom</strike></html>");
	}
    
}
