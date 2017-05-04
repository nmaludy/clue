package com.clue.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.clue.route.Router;

public class SuggestionFrame extends JFrame implements ComponentListener
{
  private JFrame parent;

  public SuggestionFrame(JFrame parent, Router router)
  {
    this.parent = parent;
    setTitle( "Suggestion" );
    setSize( 850, 300 );
    setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    JPanel panel = new SuggestionPanel( this, router  );
    this.add( panel );
    this.addComponentListener(this);
    
    java.net.URL imageURL = this.getClass().getResource("/images/clue_icon.png");
    setIconImage(new ImageIcon(imageURL).getImage());
  }

  @Override
  public void componentHidden(ComponentEvent event) {
  }

  @Override
  public void componentMoved(ComponentEvent event) {
  }

  @Override
  public void componentResized(ComponentEvent event) {
  }

  @Override
  public void componentShown(ComponentEvent event) {
    FrameUtils.centerChildOnParent(this, parent);
  }
}
