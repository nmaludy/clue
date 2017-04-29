package com.clue.gui;

import java.awt.*;

import javax.swing.*;

public class CluesPanel  extends JPanel 
{
	public CluesPanel()
	{
		// Place holder for displaying player's own clues
		setLayout(new GridLayout(2,5));
		
		add(new JLabel( "  " )); add(new JLabel( "  " ));
		add(new JLabel( " My Clues: " )); 
		add(new JLabel( "  " )); add(new JLabel( "  " ));
		
		add(new JLabel( "Prof. Plum" ));
		add(new JLabel( "Revolver" ));
		add(new JLabel( "Wrench" ));
		add(new JLabel( "Lounge" ));
		add(new JLabel( "Ballroom" ));

	}

}
