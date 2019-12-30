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
import radian.tcmis.both1.helpers.*;
import javax.swing.*;
import radian.tcmis.client.share.gui.*;
import java.util.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class GetDateNTextDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  JTextField dateT = new JTextField();
  JLabel dateL = new JLabel();
  JTextField textT = new JTextField();
  JLabel textL = new JLabel();
  JButton cancelB = new JButton();
  CmisApp cmis;
  StaticJLabel jLabel1 = new StaticJLabel();
  boolean okClicked = false;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public GetDateNTextDlg(JFrame frame, String title) {
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

  public void setDateLabel(String label) {
    dateL.setText(label);
  }

  public void setTextLabel(String label) {
    textL.setText(label);
  }

  private void jbInit() throws Exception {
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    bOk.setMaximumSize(new Dimension(80, 24));
    bOk.setMinimumSize(new Dimension(80, 24));
    bOk.setPreferredSize(new Dimension(80, 24));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOk_actionPerformed(e);
      }
    });

    panel1.setLayout(gridBagLayout1);
    panel1.setMaximumSize(new Dimension(340, 100));
    panel1.setMinimumSize(new Dimension(340, 100));
    panel1.setPreferredSize(new Dimension(340, 100));

    cancelB.setMaximumSize(new Dimension(145, 24));
    cancelB.setMinimumSize(new Dimension(145, 24));
    cancelB.setPreferredSize(new Dimension(145, 24));
    cancelB.setText("Cancel");
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    dateL.setText("Date:");
    dateT.setMaximumSize(new Dimension(53, 21));
    dateT.setMinimumSize(new Dimension(53, 21));
    dateT.setPreferredSize(new Dimension(53, 21));
    jLabel1.setText("mm/dd/yyyy");
    textL.setText("Text:");
    textT.setMaximumSize(new Dimension(53, 21));
    textT.setMinimumSize(new Dimension(53, 21));
    textT.setPreferredSize(new Dimension(53, 21));

    panel1.add(dateL,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(dateT,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jLabel1,  new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    panel1.add(textL,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(textT,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(bOk,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel1.add(cancelB,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 90, 5, 5), 0, 0));

    bOk.requestFocus();

    getContentPane().add(panel1);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }

  void loadItAction() {
    CursorChange.setCursor(cmis.getMain(), Cursor.WAIT_CURSOR);
    Calendar cal = Calendar.getInstance();
    String cdate = new String( (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
    dateT.setText(cdate);
    CursorChange.setCursor(cmis.getMain(), Cursor.DEFAULT_CURSOR);
  }

  void bOk_actionPerformed(ActionEvent e) {
    okClicked = true;
    boolean failed = false;
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String tmpDate = dateT.getText().toString();
    if (BothHelpObjs.isBlankString(tmpDate)) {
      failed = true;
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      gd.setMsg("Please enter a '" + dateL.getText() + "'.");
      gd.setVisible(true);
    } else if (!BothHelpObjs.isDateFormatRight(tmpDate)) {
      failed = true;
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      gd.setMsg("Please enter '" + dateL.getText() + "' in the following format 'mm/dd/yyyy.");
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

}
