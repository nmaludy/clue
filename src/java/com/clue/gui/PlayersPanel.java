package com.clue.gui;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

  private JFrame parent;
  
  private GridBagLayout panelLayout;
  
  // Headings
  private JLabel playersHeadingLabel;
  private JLabel fillLabel;

  private Icon turnIcon;
  private Icon transparentIcon;
  
  // Players
  private HashMap<Integer, JLabel> playerNameLabels;
  private HashMap<Integer, ColorIcon> playerColorIcons;
  private HashMap<Integer, JLabel> playerColorLabels;
  private HashMap<Integer, JLabel> playerTurnLabels;

  public PlayersPanel(JFrame parent) {
    this.parent = parent;
    
    playerNameLabels = new HashMap<Integer, JLabel>();
    playerColorIcons = new HashMap<Integer, ColorIcon>();
    playerColorLabels = new HashMap<Integer, JLabel>();
    playerTurnLabels = new HashMap<Integer, JLabel>();

    URL imageURL = this.getClass().getResource("/images/clue_icon.png");
    turnIcon = getScaledImageIcon( new ImageIcon(imageURL), 25, 25);
    transparentIcon = createTransparentIcon(25, 25);
    
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
      JLabel colorLabel = new JLabel( colorIcon );
      JLabel turnLabel = new JLabel( transparentIcon );
    
      playerNameLabels.put(player.getClientId(), nameLabel);
      playerColorIcons.put(player.getClientId(), colorIcon);
      playerColorLabels.put(player.getClientId(), colorLabel);
      playerTurnLabels.put(player.getClientId(), turnLabel);
    
      JPanel panel = new JPanel();
      FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
      layout.setHgap(1);
      layout.setVgap(1);
      panel.setLayout(layout);
      panel.add(turnLabel);
      panel.add(colorLabel);
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

      parent.revalidate();
      parent.pack();
      parent.repaint();
    }
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
        if (player.getInactive() == true ||
            player.getMadeFalseAccusation() == true) {
          
          int client_id = player.getClientId();
          if (playerNameLabels.containsKey(client_id)) {
            JLabel label = playerNameLabels.get(client_id);
            label.setEnabled(false);
          }
        }
      }

      updateTurnIcons(state);
    }
  }

  public void updateTurnIcons(Msg.GameState state) {
    for (Map.Entry<Integer, JLabel> entry : playerTurnLabels.entrySet()) {
      int client_id = entry.getKey();
      JLabel label  = entry.getValue();

      if (client_id == state.getClientCurrentTurn()) {
        label.setIcon( turnIcon );
      } else {
        label.setIcon( transparentIcon );
      }
    }
    revalidate();
    repaint();

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

  public static BufferedImage createTransparentImage(int width, int height)
  {
    return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
  }
  
  public static Icon createTransparentIcon(int width, int height)
  {
    return new ImageIcon(createTransparentImage(width, height));
  }
}
