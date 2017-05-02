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

public class MovePanel extends JPanel implements ActionListener {

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

    private JRadioButton Hallway0Button;
    private JRadioButton Hallway1Button;
    private JRadioButton Hallway2Button;
    private JRadioButton Hallway3Button;
    private JRadioButton Hallway4Button;
    private JRadioButton Hallway5Button;
    private JRadioButton Hallway6Button;
    private JRadioButton Hallway7Button;
    private JRadioButton Hallway8Button;
    private JRadioButton Hallway9Button;
    private JRadioButton Hallway10Button;
    private JRadioButton Hallway11Button;
    
    // Submit Button
    private JButton submitButton = new JButton("Submit");

    // Panel Constructor
    public MovePanel( Router routerIn )
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

    	Hallway0Button = new JRadioButton( "Hallway 0" );
    	Hallway1Button = new JRadioButton( "Hallway 1" );
    	Hallway2Button = new JRadioButton( "Hallway 2" );
    	Hallway3Button = new JRadioButton( "Hallway 3" );
    	Hallway4Button = new JRadioButton( "Hallway 4" );
    	Hallway5Button = new JRadioButton( "Hallway 5" );
    	Hallway6Button = new JRadioButton( "Hallway 6" );
    	Hallway7Button = new JRadioButton( "Hallway 7" );
    	Hallway8Button = new JRadioButton( "Hallway 8" );
    	Hallway9Button = new JRadioButton( "Hallway 9" );
    	Hallway10Button = new JRadioButton( "Hallway 10" );
    	Hallway11Button = new JRadioButton( "Hallway 11" );
    	

        // Create a button group & add buttons
        ButtonGroup sizeGroup1 = new ButtonGroup();
        ButtonGroup sizeGroup2 = new ButtonGroup();
        sizeGroup1.add( StudyButton );
        sizeGroup1.add( HallButton );
        sizeGroup1.add( LoungeButton );
        sizeGroup1.add( LibraryButton );
        sizeGroup1.add( BilliardRoomButton );
        sizeGroup1.add( DiningRoomButton );
        sizeGroup1.add( ConservatoryButton );
        sizeGroup1.add( BallroomButton );
        sizeGroup1.add( KitchenButton );
        
        sizeGroup2.add( Hallway0Button );
        sizeGroup2.add( Hallway1Button );
        sizeGroup2.add( Hallway2Button );
        sizeGroup2.add( Hallway3Button );
        sizeGroup2.add( Hallway4Button );
        sizeGroup2.add( Hallway5Button );
        sizeGroup2.add( Hallway6Button );
        sizeGroup2.add( Hallway7Button );
        sizeGroup2.add( Hallway8Button );
        sizeGroup2.add( Hallway9Button );
        sizeGroup2.add( Hallway10Button );
        sizeGroup2.add( Hallway11Button );

        // Create the Room button border
        Border buttonRoomBorder = BorderFactory.createEtchedBorder();
        buttonRoomBorder = BorderFactory.createTitledBorder( buttonRoomBorder, "Rooms" );
        Border buttonHallwayBorder = BorderFactory.createEtchedBorder();
        buttonHallwayBorder = BorderFactory.createTitledBorder( buttonHallwayBorder, "Hallways" );
        

        // Create a button panel & add buttons
        JPanel buttonPanel1 = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        
        buttonPanel1.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        buttonPanel1.add( StudyButton );
        buttonPanel1.add( HallButton );
        buttonPanel1.add( LoungeButton );
        buttonPanel1.add( LibraryButton );
        buttonPanel1.add( BilliardRoomButton );
        buttonPanel1.add( DiningRoomButton );
        buttonPanel1.add( ConservatoryButton );
        buttonPanel1.add( BallroomButton );
        buttonPanel1.add( KitchenButton );
        buttonPanel1.setBorder( buttonRoomBorder );
        
        buttonPanel2.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        buttonPanel2.add( Hallway0Button );
        buttonPanel2.add( Hallway1Button );
        buttonPanel2.add( Hallway2Button );
        buttonPanel2.add( Hallway3Button );
        buttonPanel2.add( Hallway4Button );
        buttonPanel2.add( Hallway5Button );
        buttonPanel2.add( Hallway6Button );
        buttonPanel2.add( Hallway7Button );
        buttonPanel2.add( Hallway8Button );
        buttonPanel2.add( Hallway9Button );
        buttonPanel2.add( Hallway10Button );
        buttonPanel2.add( Hallway11Button );
        buttonPanel2.setBorder( buttonHallwayBorder );

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

        Hallway0Button.addActionListener(this);
        Hallway1Button.addActionListener(this);
        Hallway2Button.addActionListener(this);
        Hallway3Button.addActionListener(this);
        Hallway4Button.addActionListener(this);
        Hallway5Button.addActionListener(this);
        Hallway6Button.addActionListener(this);
        Hallway7Button.addActionListener(this);
        Hallway8Button.addActionListener(this);
        Hallway9Button.addActionListener(this);
        Hallway10Button.addActionListener(this);
        Hallway11Button.addActionListener(this);
        
        submitButton.addActionListener(this);
        
        // By default disable submit button, until location is selected
    	submitButton.setEnabled(false);
        
        // Add panels to the main panel and arrange layout
        this.setLayout( new BorderLayout() );
        this.add( buttonPanel1, BorderLayout.NORTH );
        this.add( buttonPanel2, BorderLayout.CENTER );
        this.add( submitButton, BorderLayout.SOUTH );
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
    	com.clue.proto.Data.Location moveTo = null;
    	boolean selectedLocation = false;
        
        // Determine message based on selection
        if ( StudyButton.isSelected() )
        {
        	moveTo = Data.Location.LOC_STUDY;
        	selectedLocation = true;
        }
        
        if ( HallButton.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALL;
        	selectedLocation = true;
        }
        
        if ( LoungeButton.isSelected() )
        {
        	moveTo = Data.Location.LOC_LOUNGE;
        	selectedLocation = true;
        }

        if ( LibraryButton.isSelected() )
        {
        	moveTo = Data.Location.LOC_LIBRARY;
        	selectedLocation = true;
        }
        
        if ( BilliardRoomButton.isSelected() )
        {
        	moveTo = Data.Location.LOC_BILLIARD_ROOM;
        	selectedLocation = true;
        }
        
        if ( DiningRoomButton.isSelected() )
        {
        	moveTo = Data.Location.LOC_DINING_ROOM;
        	selectedLocation = true;
        }

        if ( ConservatoryButton.isSelected() )
        {
        	moveTo = Data.Location.LOC_CONSERVATORY;
        	selectedLocation = true;
        }
        
        if ( BallroomButton.isSelected() )
        {
        	moveTo = Data.Location.LOC_BALLROOM;
        	selectedLocation = true;
        }
        
        if ( KitchenButton.isSelected() )
        {
        	moveTo = Data.Location.LOC_KITCHEN;
        	selectedLocation = true;
        }

        if ( Hallway0Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_0;
        	selectedLocation = true;
        }
        
        if ( Hallway1Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_1;
        	selectedLocation = true;
        }

        if ( Hallway2Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_2;
        	selectedLocation = true;
        }

        if ( Hallway3Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_3;
        	selectedLocation = true;
        }

        if ( Hallway4Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_4;
        	selectedLocation = true;
        }

        if ( Hallway5Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_5;
        	selectedLocation = true;
        }

        if ( Hallway6Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_6;
        	selectedLocation = true;
        }

        if ( Hallway7Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_7;
        	selectedLocation = true;
        }

        if ( Hallway8Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_8;
        	selectedLocation = true;
        }

        if ( Hallway9Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_9;
        	selectedLocation = true;
        }

        if ( Hallway10Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_10;
        	selectedLocation = true;
        }

        if ( Hallway11Button.isSelected() )
        {
        	moveTo = Data.Location.LOC_HALLWAY_11;
        	selectedLocation = true;
        }

        // After selecting a location, enable the submit button
        if ( selectedLocation )
        {
        	submitButton.setEnabled(true);
        }
        
        // After submitting, send move message
        if ( e.getSource().equals(submitButton) )
        {
            Msg.PlayerMove mv = Msg.PlayerMove.newBuilder()
                    .setHeader(Msg.Header.newBuilder()
                               .setMsgType(Msg.PlayerMove.getDescriptor().getFullName())
                               .setSource(Instance.getId())
                               .setDestination(Instance.getServerId())
                               .build())
                    .setDestination(moveTo)
                    .build();
                router.route(new Message(mv.getHeader(), mv));
        }
        
        

    }

}
