package com.clue.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class InvalidMoveFrame extends JFrame implements ComponentListener
{
  private JFrame parent;

  public InvalidMoveFrame(JFrame parent)
  {
	    this.parent = parent;
	    setTitle( "Invalid Move" );
	    setSize( 1250, 200 );
	    setDefaultCloseOperation( MoveFrame.HIDE_ON_CLOSE );
	    JPanel panel = new InvalidMovePanel( this );
	    this.add( panel );
	    this.addComponentListener(this);
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
