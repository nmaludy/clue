package com.clue.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import com.clue.app.Instance;
import com.clue.proto.Data;
import com.clue.proto.Msg;

public class SuspectButtonPanel extends JPanel
{  
  // Suspect Buttons
  private JRadioButton MsScarlettButton;
  private JRadioButton ColMustardButton;
  private JRadioButton MrsWhiteButton;
  private JRadioButton MrGreenButton;
  private JRadioButton MrsPeacockButton;
  private JRadioButton ProfPlumButton;
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
    SuspectGroup.add( MsScarlettButton );
    SuspectGroup.add( ColMustardButton );
    SuspectGroup.add( MrsWhiteButton );
    SuspectGroup.add( MrGreenButton );
    SuspectGroup.add( MrsPeacockButton );
    SuspectGroup.add( ProfPlumButton );

    // Create the button border
    Border buttonSuspectBorder = BorderFactory.createEtchedBorder();
    buttonSuspectBorder = BorderFactory.createTitledBorder( buttonSuspectBorder, "Suspect" );
        
    JPanel buttonSuspectPanel = new JPanel();
    buttonSuspectPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
    buttonSuspectPanel.add( MsScarlettButton );
    buttonSuspectPanel.add( ColMustardButton );
    buttonSuspectPanel.add( MrsWhiteButton );
    buttonSuspectPanel.add( MrGreenButton );
    buttonSuspectPanel.add( MrsPeacockButton );
    buttonSuspectPanel.add( ProfPlumButton );
    buttonSuspectPanel.setBorder( buttonSuspectBorder );

    // Add panels to the main panel and arrange layout
    this.setLayout( new BorderLayout() );
    this.add( buttonSuspectPanel, BorderLayout.CENTER );
  }

  public Data.Suspect.Identity getSuspect()
  {
    Data.Suspect.Identity suspect = null;
    if (MsScarlettButton.isSelected() )
    {
      suspect = Data.Suspect.Identity.SUS_MISS_SCARLETT;
    }
    if (ColMustardButton.isSelected() )
    {
      suspect = Data.Suspect.Identity.SUS_COL_MUSTARD;
    }   
    if (MrsWhiteButton.isSelected() )
    {
      suspect = Data.Suspect.Identity.SUS_MRS_WHITE;
    }
    if (MrGreenButton.isSelected() )
    {
      suspect = Data.Suspect.Identity.SUS_MR_GREEN;
    }   
    if (MrsPeacockButton.isSelected() )
    {
      suspect = Data.Suspect.Identity.SUS_MRS_PEACOCK;
    }   
    if (ProfPlumButton.isSelected() )
    {
      suspect = Data.Suspect.Identity.SUS_PROF_PLUM;
    }
    return suspect;
  }

  public void disableClaimedSuspects(Msg.GameState state)
  {
    for (Data.Player player : state.getPlayersList())
    {
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
  }
}
