//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import java.awt.event.*;
import radian.tcmis.client.share.helpers.*;
import javax.swing.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class GetLabelTypeDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  JRadioButton hazardousRB = new JRadioButton("Hazardous Waste");
  JRadioButton travelerRB = new JRadioButton("Traveler");
  JButton cancelB = new JButton();
  CmisApp cmis;
  StaticJLabel jLabel1 = new StaticJLabel("Select a label:");
  boolean okClicked = false;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public GetLabelTypeDlg(JFrame frame, String title) {
    super(frame, title,true);
    parent = frame;

    try {
      jbInit();
      pack();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setParent(CmisApp c) {
    cmis = c;
  }

  public boolean okClicked() {
    return okClicked;
  }

  private void jbInit() throws Exception {
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    bOk.setMaximumSize(new Dimension(80, 21));
    bOk.setMinimumSize(new Dimension(80, 21));
    bOk.setPreferredSize(new Dimension(80, 21));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOk_actionPerformed(e);
      }
    });

    panel1.setLayout(gridBagLayout1);
    panel1.setMaximumSize(new Dimension(340, 130));
    panel1.setMinimumSize(new Dimension(340, 130));
    panel1.setPreferredSize(new Dimension(340, 130));
    cancelB.setMaximumSize(new Dimension(105, 24));
    cancelB.setMinimumSize(new Dimension(105, 21));
    cancelB.setPreferredSize(new Dimension(105, 21));
    cancelB.setText("Cancel");
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    hazardousRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        hazardousRB_actionPerformed(e);
      }
    });
    travelerRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        travelerRB_actionPerformed(e);
      }
    });
    hazardousRB.setSelected(false);
    travelerRB.setSelected(false);
    panel1.add(jLabel1,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(hazardousRB,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(travelerRB,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(bOk,  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(cancelB,  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 90, 5, 5), 0, 0));

    bOk.requestFocus();

    getContentPane().add(panel1);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }

  void bOk_actionPerformed(ActionEvent e) {
    okClicked = true;
    boolean failed = false;
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (!hazardousRB.isSelected() && !travelerRB.isSelected()) {
      failed = true;
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      gd.setMsg("Please select a label.");
      gd.setVisible(true);
    }

    if (!failed) {
      setVisible(false);
    }
  }

  void this_windowClosing(WindowEvent e) {
    okClicked = false;
    this.setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    okClicked = false;
    this.setVisible(false);
  }

  void hazardousRB_actionPerformed(ActionEvent e) {
    if (hazardousRB.isSelected()) {
      travelerRB.setSelected(false);
    }
  }

  void travelerRB_actionPerformed(ActionEvent e) {
    if (travelerRB.isSelected()) {
      hazardousRB.setSelected(false);
    }
  }

}
