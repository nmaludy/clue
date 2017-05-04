package com.clue.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.clue.app.Instance;
import com.clue.proto.Msg;
import com.clue.route.Message;

public class InvalidMovePanel extends JPanel implements ActionListener
{

    // Error Message Field
	private JTextField message = new JTextField("Invalid move. Please try again.");

    // Okay Button
    private JButton okButton = new JButton("Okay");
    
    // Frame (allows closing after hitting okay).
	private InvalidMoveFrame frame = null;
    
    // Panel Constructor
    public InvalidMovePanel( InvalidMoveFrame frameIn )
    {
    	frame = frameIn;

    	okButton.addActionListener(this);

		setLayout(new GridLayout(2,1));
		this.add( message );
		this.add( okButton );
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

        // After submitting, send move message
        if ( e.getSource().equals(okButton) )
        {
        	frame.setVisible(false);
        }
    }
}
