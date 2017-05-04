package com.clue.gui;

import java.awt.*;
import javax.swing.*;

public class FrameUtils {

  public static void centerChildOnParent(JFrame child, JFrame parent) {
    Point     pl = parent.getLocation();
    Dimension pd = parent.getSize();
    Dimension cd = child.getSize();
    
    int cx = (pl.x + (pd.width / 2) - (cd.width / 2));
    int cy = (pl.y + (pd.height / 2) - (cd.height / 2));
    child.setLocation(cx, cy);
  }
}
