package com.clue.gui;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

import java.net.URL;
import java.util.HashMap;

import com.clue.app.Config;
import com.clue.app.Logger;

import com.clue.proto.Data;
import com.clue.proto.Msg;

import com.clue.route.Message;
import com.clue.route.MessageHandler;
import com.clue.route.Router;
import com.clue.route.SubscriptionAllIncoming;

public class PlayersPanel extends JPanel implements MessageHandler
{
  private static Logger logger = new Logger(PlayersPanel.class);
  private static Config config = Config.getInstance();

  private GridBagLayout panelLayout;
  
  // Headings
  private JLabel playersHeadingLabel;
  private JLabel fillLabel;
  
  // Players
  private HashMap<Integer, JLabel> playerNameLabels;
  private HashMap<Integer, ColorIcon> playerColorIcons;

  public PlayersPanel() {
    playerNameLabels = new HashMap<Integer, JLabel>();
    playerColorIcons = new HashMap<Integer, ColorIcon>();

    // Layout
    panelLayout = new GridBagLayout();
    setLayout(panelLayout);

    // Create Labels
    playersHeadingLabel = new JLabel( "Players", SwingConstants.CENTER );
    playersHeadingLabel.setFont(new Font("Serif", Font.BOLD, 24));

    fillLabel = new JLabel();

    // Add labels to panel
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.NORTHWEST;
    c.gridx = 0;
    c.gridy = 0;
    add( playersHeadingLabel, c );
    
    c.anchor = GridBagConstraints.NORTHWEST;
    c.gridx = 0;
    c.gridy = 1;
    c.weighty = 1.0;
    add( fillLabel, c );
      
    Router.getInstance().register(new SubscriptionAllIncoming(), this);
  }

  private void createPlayerPanel(Data.Player player) {
    if (!player.hasSuspect() ||
        player.getSuspect() == null ||
        player.getSuspect() == Data.Suspect.SUS_NONE) {
      return;
    }
    
    int client_id = player.getClientId();

    if (!playerNameLabels.containsKey(client_id)) {
      
      logger.debug("createPlayerPanel() - adding player: " + player.getName());
      JLabel nameLabel = new JLabel(player.getName());
      Color color = GUIpanel.getSuspectColor(player.getSuspect());
      ColorIcon colorIcon = new ColorIcon(25, 25, color);

      nameLabel.setIcon(colorIcon);
    
      playerNameLabels.put(player.getClientId(), nameLabel);
      playerColorIcons.put(player.getClientId(), colorIcon);
    
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout(FlowLayout.LEFT));
      panel.add(nameLabel);

      remove(fillLabel);
      
      GridBagConstraints c = new GridBagConstraints();
      c.anchor = GridBagConstraints.NORTHWEST;
      c.gridx = 0;
      c.gridy = playerNameLabels.size();
      add( panel, c );

      c.anchor = GridBagConstraints.NORTHWEST;
      c.gridx = 0;
      c.gridy = playerNameLabels.size() + 1;
      c.weighty = 1.0;
      add( fillLabel, c );

      revalidate();
      repaint();
    }
  }


  @Override
  public boolean shouldCallHandleOnGuiThread() {
    return true;
  }

  @Override
  public void handleMessage(Message msg) {
    // Here is how to handle messages of a specific type
    String msg_type = msg.getHeader().getMsgType();

    if (msg_type.equals(Msg.GameState.getDescriptor().getFullName())) {
      Msg.GameState state = (Msg.GameState)msg.getMessage();
      logger.debug("handleMessage() - got state: " + state.toString());
      for (Data.Player player : state.getPlayersList()) {
        createPlayerPanel(player);
      }
    }
  }

  public class ColorIcon implements Icon {
    private int width;
    private int height;
    
    private int borderWidth;

    private Color color;
    private Color borderColor;

    public ColorIcon() {
      this(32, 16);
    }

    public ColorIcon(int w, int h) {
      this(w, h, Color.BLACK);
    }

    public ColorIcon(int w, int h, Color c) {
      width   = w;
      height  = h;
      color   = c;
      borderWidth = 4;
      borderColor = Color.BLACK;
    }

    public Color getColor() {
      return color;
    }
    
    public void setColor(Color c) {
      color = c;
    }

    public Color getBorderColor() {
      return borderColor;
    }
    
    public void setBorderColor(Color c) {
      borderColor = c;
    }

    public int getBorderWidth() {
      return borderWidth;
    }

    public void setBorderWidth(int w) {
      borderWidth = w;
    }
    
    @Override
    public int getIconWidth() {
      return width;
    }

    @Override    
    public int getIconHeight() {
      return height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
      g.setColor(borderColor);      
      g.fillRect(x, y, width, height);

      x += borderWidth / 2;
      y += borderWidth / 2;

      int w = width  - borderWidth;
      int h = height - borderWidth;
      
      g.setColor(color);
      g.fillRect(x, y, w, h);
    }
  }

}
