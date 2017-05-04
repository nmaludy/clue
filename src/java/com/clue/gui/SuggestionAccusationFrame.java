package com.clue.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.clue.route.Router;

import com.clue.proto.Data;

public class SuggestionAccusationFrame extends JFrame implements ComponentListener {
  public enum Type {
    TYPE_SUGGESTION,
    TYPE_ACCUSATION
  }
  
  private JFrame parent;
  private SuggestionAccusationPanel panel;
  private Type type;

  public SuggestionAccusationFrame(JFrame parent, Router router, Type type) {
    this.parent = parent;
    this.type = type;
    
    setTitle( getTypeName() );
    setSize( 850, 300 );
    setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    this.panel = new SuggestionAccusationPanel( this, router, type );
    this.add( panel );
    this.addComponentListener(this);
    
    java.net.URL imageURL = this.getClass().getResource("/images/clue_icon.png");
    setIconImage(new ImageIcon(imageURL).getImage());
  }

  public void setType(Type type) {
    this.type = type;
    setTitle( getTypeName() );
    panel.setType(type);
  }

  public String getTypeName() {
    return typeName(type);
  }
  
  public static String typeName(Type type) {
    switch (type) {
      case TYPE_SUGGESTION: return "Suggestion";
      case TYPE_ACCUSATION: return "Accusation";
    }
    return new String();
  }

  public void setSolution(Data.Solution solution) {
    panel.setSolution(solution);
  }

  public void setLocation(Data.Location location) {
    panel.setLocation(location);
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
