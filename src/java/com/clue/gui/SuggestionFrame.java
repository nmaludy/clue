package com.clue.gui;

import java.awt.*;
import javax.swing.*;

import com.clue.route.Router;

public class SuggestionFrame extends JFrame 
{

    public SuggestionFrame(Router router)
    {
        setTitle( "Suggestion" );
        setSize( 850, 350 );
        setDefaultCloseOperation( MoveFrame.HIDE_ON_CLOSE );
        centerFrame(this);
        JPanel panel = new SuggestionPanel( router );
        this.add( panel );
    }

    
    /**
     * 
     * Center the GUI frame in the middle of the user's screen
     * @param w is the window of the GUI
     */
    public void centerFrame(Window w)
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int xLoc = (d.width - w.getWidth())/2;
        int yLoc = (d.height - w.getHeight())/2;
        setLocation(xLoc, yLoc);
    }
}
