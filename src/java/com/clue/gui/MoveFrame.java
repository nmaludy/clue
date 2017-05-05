package com.clue.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.clue.route.Router;

public class MoveFrame extends JFrame implements ComponentListener
{
  private JFrame parent;

  public MoveFrame( JFrame parent, Router router )
  {
    this.parent = parent;
    setTitle( "Move" );
    setDefaultCloseOperation( MoveFrame.HIDE_ON_CLOSE );
    JPanel panel = new MovePanel( router, this );
    this.add( panel );
    this.addComponentListener(this);

    pack();

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
