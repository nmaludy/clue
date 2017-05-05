package com.clue.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.util.*;

import com.clue.app.Logger;
import com.clue.app.Instance;

import com.clue.proto.Data;
import com.clue.proto.Msg;

public class SuspectButtonPanel extends JPanel
{
  private static Logger logger = new Logger(SuspectButtonPanel.class);

  // Suspect Buttons
  private JRadioButton MsScarlettButton;
  private JRadioButton ColMustardButton;
  private JRadioButton MrsWhiteButton;
  private JRadioButton MrGreenButton;
  private JRadioButton MrsPeacockButton;
  private JRadioButton ProfPlumButton;

  private ArrayList<JRadioButton> buttonList = new ArrayList<JRadioButton>();
  
  private ButtonGroup SuspectGroup;
  
  // Panel Constructor
  public SuspectButtonPanel()
  {
    super();
        
    // Create Suspect buttons
    MsScarlettButton = new JRadioButton( "Ms. Scarlett" );
    ColMustardButton = new JRadioButton( "Col. Mustard" );
    MrsWhiteButton = new JRadioButton( "Mrs. White" );
    MrGreenButton = new JRadioButton( "Mr. Green" );
    MrsPeacockButton = new JRadioButton( "Mrs. Peacock" );
    ProfPlumButton = new JRadioButton( "Prof. Plum" );

    // Create a button group & add buttons
    SuspectGroup = new ButtonGroup();
    SuspectGroup.add( MrGreenButton );
    SuspectGroup.add( MrsWhiteButton );
    SuspectGroup.add( ColMustardButton );
    SuspectGroup.add( MsScarlettButton );
    SuspectGroup.add( MrsPeacockButton );
    SuspectGroup.add( ProfPlumButton );

    buttonList.add( MrGreenButton );
    buttonList.add( MrsWhiteButton );
    buttonList.add( ColMustardButton );
    buttonList.add( MsScarlettButton );
    buttonList.add( MrsPeacockButton );
    buttonList.add( ProfPlumButton );

    // Create the button border
    Border buttonSuspectBorder = BorderFactory.createEtchedBorder();
    buttonSuspectBorder = BorderFactory.createTitledBorder( buttonSuspectBorder, "Suspect" );
        
    JPanel buttonSuspectPanel = new JPanel();
    buttonSuspectPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
    buttonSuspectPanel.add( MrGreenButton );
    buttonSuspectPanel.add( MrsWhiteButton );
    buttonSuspectPanel.add( ColMustardButton );
    buttonSuspectPanel.add( MsScarlettButton );
    buttonSuspectPanel.add( MrsPeacockButton );
    buttonSuspectPanel.add( ProfPlumButton );
    buttonSuspectPanel.setBorder( buttonSuspectBorder );

    // Add panels to the main panel and arrange layout
    this.setLayout( new BorderLayout() );
    this.add( buttonSuspectPanel, BorderLayout.CENTER );
  }

  public Data.Suspect getSuspect()
  {
    Data.Suspect suspect = null;
    if (MsScarlettButton.isSelected() )
    {
      suspect = Data.Suspect.SUS_MISS_SCARLETT;
    }
    if (ColMustardButton.isSelected() )
    {
      suspect = Data.Suspect.SUS_COL_MUSTARD;
    }   
    if (MrsWhiteButton.isSelected() )
    {
      suspect = Data.Suspect.SUS_MRS_WHITE;
    }
    if (MrGreenButton.isSelected() )
    {
      suspect = Data.Suspect.SUS_MR_GREEN;
    }   
    if (MrsPeacockButton.isSelected() )
    {
      suspect = Data.Suspect.SUS_MRS_PEACOCK;
    }   
    if (ProfPlumButton.isSelected() )
    {
      suspect = Data.Suspect.SUS_PROF_PLUM;
    }
    return suspect;
  }

  public void disableClaimedSuspects(Msg.GameState state)
  {
    logger.debug("disableClaimedSuspects() - disabling based on received state");
    for (Data.Player player : state.getPlayersList()) {
      logger.debug("disableClaimedSuspects() - disabling suspect: "
                   + player.getSuspect().name());
      switch (player.getSuspect())
      {
        case SUS_MISS_SCARLETT:
          MsScarlettButton.setEnabled(false);
          break;
        case SUS_COL_MUSTARD:
          ColMustardButton.setEnabled(false);
          break;
        case SUS_MRS_WHITE:
          MrsWhiteButton.setEnabled(false);
          break;
        case SUS_MR_GREEN:
          MrGreenButton.setEnabled(false);
          break;
        case SUS_MRS_PEACOCK:
          MrsPeacockButton.setEnabled(false);
          break;
        case SUS_PROF_PLUM:
          ProfPlumButton.setEnabled(false);
          break;
      }
    }

    for (JRadioButton button : buttonList) {
      if (button.isSelected() && button.isEnabled() == false) {
        SuspectGroup.clearSelection();
      }
    }
  }
}
