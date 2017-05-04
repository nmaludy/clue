package com.clue.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.net.URL;

import com.clue.app.Instance;
import com.clue.app.Logger;

import com.clue.proto.Data;
import com.clue.proto.Msg;

import com.clue.route.Router;

public class GameEndFrame extends JFrame implements ActionListener, ComponentListener {
  private static Logger logger = new Logger(GameEndFrame.class);

  private JFrame parent;

  private JLabel label;
  private JButton exitButton;

  public GameEndFrame( JFrame parent, Router router )
  {
    this.parent = parent;
        
    setTitle( "Game Over" );
    setSize( 300, 150 );
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    // Create Label
    label = new JLabel();
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JPanel labelPanel = new JPanel();
    labelPanel.setLayout(new BorderLayout());
    labelPanel.add( label, BorderLayout.CENTER );

    // Create button
    exitButton = new JButton("Exit");
    exitButton.addActionListener(this);
    exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    this.add( labelPanel );
    this.add( exitButton );

    this.addComponentListener(this);

    URL imageURL = this.getClass().getResource("/images/clue_icon.png");
    setIconImage(new ImageIcon(imageURL).getImage());
  }

  public void setGameEnd(Msg.GameEnd msg) {
    logger.debug("winner is : " + msg.getClientWinner());
	  String message;
    
	  // if gameEnd.clientWinner equals your clientID print you win
    // else print who won the game. 
	  if (msg.getClientWinner() == Instance.getId()) {
      setLabelIcon( label, "/images/happy.png" );
      message = "Congratulations, you have won by solving the mystery!";
	  } else {
      setLabelIcon( label, "/images/unhappy.png" );
      Data.Player winner = ClientState.getInstance().getPlayerById(msg.getClientWinner());
      String name = winner.getName();
      message = "Sorry, you have lost.<br> " + name + " solved the mystery.";
	  }
    label.setText("<html><div style='text-align: center;'>"
                  + message
                  +  "</div></html>");
  }

  private void setLabelIcon(JLabel label, String iconPath) {
    URL imageURL = this.getClass().getResource(iconPath);
    ImageIcon icon = new ImageIcon(imageURL);
    ImageIcon resized_icon = getScaledImageIcon(icon, 64, 64);
    label.setIcon(resized_icon);
  }

  private ImageIcon getScaledImageIcon(ImageIcon srcIcon, int w, int h){
    Image srcImg = srcIcon.getImage();
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();

    return new ImageIcon(resizedImg);
  }
  
  public void actionPerformed(ActionEvent e) {
    if ( e.getSource().equals(exitButton) ) {
      parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
    }
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
