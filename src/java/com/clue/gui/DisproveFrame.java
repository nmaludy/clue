package com.clue.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.clue.app.Config;
import com.clue.app.Logger;
import com.clue.app.Instance;

import com.clue.proto.Msg;

import com.clue.route.Router;

public class DisproveFrame extends JFrame implements ComponentListener {

  private static Logger logger = new Logger(DisproveFrame.class);
  private static Config config = Config.getInstance();
  
  private JFrame parent;
  private DisprovePanel panel;
  
  public DisproveFrame(JFrame parent, Router router) {
    this.parent = parent;
    setTitle( "Disprove" );
    setSize( 850, 300 );
    setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    panel = new DisprovePanel( this, router  );
    this.add( panel );
    this.addComponentListener(this);
  }

  public void setDisproveRequest(Msg.DisproveRequest req) {
    panel.setDisproveRequest(req);
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
