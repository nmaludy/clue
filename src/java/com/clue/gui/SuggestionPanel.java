package com.clue.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import com.clue.app.Instance;
import com.clue.proto.Data;
import com.clue.proto.Msg;
import com.clue.route.Message;
import com.clue.route.Router;

public class SuggestionPanel extends JPanel implements ActionListener {

	private Router router = null;
	  
    // Room buttons    
    private JRadioButton StudyButton;
    private JRadioButton HallButton;
    private JRadioButton LoungeButton;
    private JRadioButton LibraryButton;
    private JRadioButton BilliardRoomButton;
    private JRadioButton DiningRoomButton;
    private JRadioButton ConservatoryButton;
    private JRadioButton BallroomButton;
    private JRadioButton KitchenButton;

    // Weapon buttons    
    private JRadioButton CandlestickButton;
    private JRadioButton KnifeButton;
    private JRadioButton LeadPipeButton;
    private JRadioButton RevolverButton;
    private JRadioButton RopeButton;
    private JRadioButton WrenchButton;
    
    // Suspect Buttons
    private JRadioButton MsScarlettButton;
    private JRadioButton ColMustardButton;
    private JRadioButton MrsWhiteButton;
    private JRadioButton MrGreenButton;
    private JRadioButton MrsPeacockButton;
    private JRadioButton ProfPlumButton;
    
    // Submit Buttons
    private JButton suggestionButton = new JButton("Submit Suggestion");
    private JButton accusationButton = new JButton("Submit Accusation");

    // Panel Constructor
    public SuggestionPanel( Router routerIn )
    {
    	router = routerIn;
    	
	    // Create Room buttons
    	StudyButton = new JRadioButton( "Study" );
    	HallButton = new JRadioButton( "Hall" );
    	LoungeButton = new JRadioButton( "Lounge" );
    	LibraryButton = new JRadioButton( "Library" );
    	BilliardRoomButton = new JRadioButton( "Billiard Room" );
    	DiningRoomButton = new JRadioButton( "Dining Room" );
    	ConservatoryButton = new JRadioButton( "Conservatory" );
    	BallroomButton = new JRadioButton( "Ballroom" );
    	KitchenButton = new JRadioButton( "Kitchen" );

	    // Create Weapon buttons
        CandlestickButton = new JRadioButton( "Candlestick" );
        KnifeButton = new JRadioButton( "Knife" );
        LeadPipeButton = new JRadioButton( "Lead Pipe" );
        RevolverButton = new JRadioButton( "Revolver" );
        RopeButton = new JRadioButton( "Rope" );
        WrenchButton = new JRadioButton( "Wrench" );

	    // Create Suspect buttons
        MsScarlettButton = new JRadioButton( "Ms. Scarlett" );
        ColMustardButton = new JRadioButton( "Col. Mustard" );
        MrsWhiteButton = new JRadioButton( "Mrs. White" );
        MrGreenButton = new JRadioButton( "Mr. Green" );
        MrsPeacockButton = new JRadioButton( "Mrs. Peacock" );
        ProfPlumButton = new JRadioButton( "Prof. Plum" );
    	

        // Create a button group & add buttons
        ButtonGroup RoomGroup = new ButtonGroup();
        RoomGroup.add( StudyButton );
        RoomGroup.add( HallButton );
        RoomGroup.add( LoungeButton );
        RoomGroup.add( LibraryButton );
        RoomGroup.add( BilliardRoomButton );
        RoomGroup.add( DiningRoomButton );
        RoomGroup.add( ConservatoryButton );
        RoomGroup.add( BallroomButton );
        RoomGroup.add( KitchenButton );

        ButtonGroup WeaponGroup = new ButtonGroup();
        WeaponGroup.add( CandlestickButton );
        WeaponGroup.add( KnifeButton );
        WeaponGroup.add( LeadPipeButton );
        WeaponGroup.add( RevolverButton );
        WeaponGroup.add( RopeButton );
        WeaponGroup.add( WrenchButton );

        ButtonGroup SuspectGroup = new ButtonGroup();
        SuspectGroup.add( MsScarlettButton );
        SuspectGroup.add( ColMustardButton );
        SuspectGroup.add( MrsWhiteButton );
        SuspectGroup.add( MrGreenButton );
        SuspectGroup.add( MrsPeacockButton );
        SuspectGroup.add( ProfPlumButton );

        // Create the button border
        Border buttonRoomBorder = BorderFactory.createEtchedBorder();
        buttonRoomBorder = BorderFactory.createTitledBorder( buttonRoomBorder, "Room" );

        Border buttonWeaponBorder = BorderFactory.createEtchedBorder();
        buttonWeaponBorder = BorderFactory.createTitledBorder( buttonWeaponBorder, "Weapon" );

        Border buttonSuspectBorder = BorderFactory.createEtchedBorder();
        buttonSuspectBorder = BorderFactory.createTitledBorder( buttonSuspectBorder, "Suspect" );
        

        // Create a button panel & add buttons
        JPanel buttonRoomPanel = new JPanel();
        buttonRoomPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        buttonRoomPanel.add( StudyButton );
        buttonRoomPanel.add( HallButton );
        buttonRoomPanel.add( LoungeButton );
        buttonRoomPanel.add( LibraryButton );
        buttonRoomPanel.add( BilliardRoomButton );
        buttonRoomPanel.add( DiningRoomButton );
        buttonRoomPanel.add( ConservatoryButton );
        buttonRoomPanel.add( BallroomButton );
        buttonRoomPanel.add( KitchenButton );
        buttonRoomPanel.setBorder( buttonRoomBorder );

        JPanel buttonWeaponPanel = new JPanel();
        buttonWeaponPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        buttonWeaponPanel.add( CandlestickButton );
        buttonWeaponPanel.add( KnifeButton );
        buttonWeaponPanel.add( LeadPipeButton );
        buttonWeaponPanel.add( RevolverButton );
        buttonWeaponPanel.add( RopeButton );
        buttonWeaponPanel.add( WrenchButton );
        buttonWeaponPanel.setBorder( buttonWeaponBorder );

        JPanel buttonSuspectPanel = new JPanel();
        buttonSuspectPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        buttonSuspectPanel.add( MsScarlettButton );
        buttonSuspectPanel.add( ColMustardButton );
        buttonSuspectPanel.add( MrsWhiteButton );
        buttonSuspectPanel.add( MrGreenButton );
        buttonSuspectPanel.add( MrsPeacockButton );
        buttonSuspectPanel.add( ProfPlumButton );
        buttonSuspectPanel.setBorder( buttonSuspectBorder );

        // Add ActionListeners for each radio button
        StudyButton.addActionListener(this);
        HallButton.addActionListener(this);
        LoungeButton.addActionListener(this);
        LibraryButton.addActionListener(this);
        BilliardRoomButton.addActionListener(this);
        DiningRoomButton.addActionListener(this);
        ConservatoryButton.addActionListener(this);
        BallroomButton.addActionListener(this);
        KitchenButton.addActionListener(this);

        CandlestickButton.addActionListener(this);
        KnifeButton.addActionListener(this);
        LeadPipeButton.addActionListener(this);
        RevolverButton.addActionListener(this);
        RopeButton.addActionListener(this);
        WrenchButton.addActionListener(this);

        MsScarlettButton.addActionListener(this);
        ColMustardButton.addActionListener(this);
        MrsWhiteButton.addActionListener(this);
        MrGreenButton.addActionListener(this);
        MrsPeacockButton.addActionListener(this);
        ProfPlumButton.addActionListener(this);

        suggestionButton.addActionListener(this);
        accusationButton.addActionListener(this);
        
        //Default Submit Buttons to disabled
        suggestionButton.setEnabled(false);
        accusationButton.setEnabled(false);
        
        // Add panels to the main panel and arrange layout
		this.setLayout(new GridLayout(5,1));
        //this.setLayout( new BorderLayout() );
        this.add( buttonRoomPanel );
        this.add( buttonWeaponPanel );
        this.add( buttonSuspectPanel );
        this.add( suggestionButton );
        this.add( accusationButton );
    }
    

    /**
     * This method handles events caused by the user
     * selecting or deselecting radio buttons or
     * checkboxes
     * 
     * @param e is an ActionEvent caused generated by an action listener
     */
    public void actionPerformed(ActionEvent e)
    {
    	boolean selectedRoom = false;
    	boolean selectedWeapon = false;
    	boolean selectedSuspect = false;
    	
    	com.clue.proto.Data.Location.Identity sRoom = null;
    	com.clue.proto.Data.Weapon.Identity sWeapon = null;
    	com.clue.proto.Data.Suspect.Identity sSuspect = null;
        
        // Determine message based on selection
    	
    	// Rooms
        if (StudyButton.isSelected() )
        {
        	sRoom = Data.Location.Identity.LOC_STUDY;
        	selectedRoom = true;
        }
        
        if (HallButton.isSelected() )
        {
        	sRoom = Data.Location.Identity.LOC_HALL;
        	selectedRoom = true;
        }
        
        if (LoungeButton.isSelected() )
        {
        	sRoom = Data.Location.Identity.LOC_LOUNGE;
        	selectedRoom = true;
        }

        if (LibraryButton.isSelected() )
        {
        	sRoom = Data.Location.Identity.LOC_LIBRARY;
        	selectedRoom = true;
        }
        
        if (BilliardRoomButton.isSelected() )
        {
        	sRoom = Data.Location.Identity.LOC_BILLIARD_ROOM;
        	selectedRoom = true;
        }
        
        if (DiningRoomButton.isSelected() )
        {
        	sRoom = Data.Location.Identity.LOC_DINING_ROOM;
        	selectedRoom = true;
        }

        if (ConservatoryButton.isSelected() )
        {
        	sRoom = Data.Location.Identity.LOC_CONSERVATORY;
        	selectedRoom = true;
        }
        
        if (BallroomButton.isSelected() )
        {
        	sRoom = Data.Location.Identity.LOC_BALLROOM;
        	selectedRoom = true;
        }
        
        if (KitchenButton.isSelected() )
        {
        	sRoom = Data.Location.Identity.LOC_KITCHEN;
        	selectedRoom = true;
        }
        
        // Weapons
        if (CandlestickButton.isSelected() )
        {
        	sWeapon = Data.Weapon.Identity.WPN_CANDLESTICK;
        	selectedWeapon = true;
        }
        
        if (KnifeButton.isSelected() )
        {
        	sWeapon = Data.Weapon.Identity.WPN_KNIFE;
        	selectedWeapon = true;
        }
        
        if (LeadPipeButton.isSelected() )
        {
        	sWeapon = Data.Weapon.Identity.WPN_LEAD_PIPE;
        	selectedWeapon = true;
        }
        
        if (RevolverButton.isSelected() )
        {
        	sWeapon = Data.Weapon.Identity.WPN_REVOLVER;
        	selectedWeapon = true;
        }

        if (RopeButton.isSelected() )
        {
        	sWeapon = Data.Weapon.Identity.WPN_ROPE;
        	selectedWeapon = true;
        }
        
        if (WrenchButton.isSelected() )
        {
        	sWeapon = Data.Weapon.Identity.WPN_WRENCH;
        	selectedWeapon = true;
        }
        
        // Suspects
        if (MsScarlettButton.isSelected() )
        {
        	sSuspect = Data.Suspect.Identity.SUS_MISS_SCARLETT;
        	selectedSuspect = true;
        }
        
        if (ColMustardButton.isSelected() )
        {
        	sSuspect = Data.Suspect.Identity.SUS_COL_MUSTARD;
        	selectedSuspect = true;
        }
        
        if (MrsWhiteButton.isSelected() )
        {
        	sSuspect = Data.Suspect.Identity.SUS_MRS_WHITE;
        	selectedSuspect = true;
        }

        if (MrGreenButton.isSelected() )
        {
        	sSuspect = Data.Suspect.Identity.SUS_MR_GREEN;
        	selectedSuspect = true;
        }
        
        if (MrsPeacockButton.isSelected() )
        {
        	sSuspect = Data.Suspect.Identity.SUS_MRS_PEACOCK;
        	selectedSuspect = true;
        }
        
        if (ProfPlumButton.isSelected() )
        {
        	sSuspect = Data.Suspect.Identity.SUS_PROF_PLUM;
        	selectedSuspect = true;
        }
        
        // If each type of clue is selected
        if (selectedRoom && selectedWeapon && selectedSuspect)
        {
            suggestionButton.setEnabled(true);
            accusationButton.setEnabled(true);
        }

        // Send Suggestion
        if ( e.getSource().equals(suggestionButton) )
        {
	        Msg.PlayerSuggestion sg = Msg.PlayerSuggestion.newBuilder()
		            .setHeader(Msg.Header.newBuilder()
		                       .setMsgType(Msg.PlayerSuggestion.getDescriptor().getFullName())
		                       .setSource(Instance.getId())
		                       .setDestination(Instance.getServerId())
		                       .build())
		            .setGuess(Data.Guess.newBuilder()
		                      .setClientId(Instance.getId())
		                      .setSolution(Data.Solution.newBuilder()
		                                   .setWeapon(sWeapon)
		                                   .setSuspect(sSuspect)
		                                   .setLocation(sRoom)
		                                   .build())
		                      .build())
		            .build();
		        router.route(new Message(sg.getHeader(), sg));
        }
        
        // Send Accusation
        if ( e.getSource().equals(accusationButton) )
        {
	        Msg.PlayerAccusation sg = Msg.PlayerAccusation.newBuilder()
		            .setHeader(Msg.Header.newBuilder()
		                       .setMsgType(Msg.PlayerAccusation.getDescriptor().getFullName())
		                       .setSource(Instance.getId())
		                       .setDestination(Instance.getServerId())
		                       .build())
		            .setGuess(Data.Guess.newBuilder()
		                      .setClientId(Instance.getId())
		                      .setSolution(Data.Solution.newBuilder()
		                                   .setWeapon(sWeapon)
		                                   .setSuspect(sSuspect)
		                                   .setLocation(sRoom)
		                                   .build())
		                      .build())
		            .build();
		        router.route(new Message(sg.getHeader(), sg));
        }
        
    }

}
