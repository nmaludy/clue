package com.clue.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.clue.app.Config;
import com.clue.app.Logger;
import com.clue.app.Instance;

import com.clue.proto.Msg;

import com.clue.route.Router;

public class TurnEndFrame extends JFrame implements ComponentListener {

  private static Logger logger = new Logger(DisproveFrame.class);
  private static Config config = Config.getInstance();
  
  private JFrame parent;
  private TurnEndPanel panel;
  
  public TurnEndFrame(JFrame parent, Router router) {
    this.parent = parent;
    setTitle( "Turn End" );
    setSize( 400, 380 );
    setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    panel = new TurnEndPanel( this, router  );
    this.add( panel );
    this.addComponentListener(this);

    java.net.URL imageURL = this.getClass().getResource("/images/clue_icon.png");
    setIconImage(new ImageIcon(imageURL).getImage());    
  }

  public void setDisproveResponse(Msg.DisproveResponse resp) {
    panel.setDisproveResponse(resp);
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
