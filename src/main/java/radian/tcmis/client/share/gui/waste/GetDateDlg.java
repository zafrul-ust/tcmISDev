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
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.*;
import java.util.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class GetDateDlg extends JDialog {
  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  JTextField dateT = new JTextField();
  JLabel dateL = new JLabel();
  JButton cancelB = new JButton();
  CmisApp cmis;
  StaticJLabel jLabel1 = new StaticJLabel();
  boolean okClicked = false;

  public GetDateDlg(JFrame frame, String title) {
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

  private void jbInit() throws Exception {
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    xYLayout1.setWidth(460);
    xYLayout1.setHeight(179);
    bOk.setMaximumSize(new Dimension(53, 24));
    bOk.setMinimumSize(new Dimension(53, 24));
    bOk.setPreferredSize(new Dimension(53, 24));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOk_actionPerformed(e);
      }
    });

    panel1.setLayout(xYLayout1);
    panel1.setMaximumSize(new Dimension(340, 100));
    panel1.setMinimumSize(new Dimension(340, 100));
    panel1.setPreferredSize(new Dimension(340, 100));
    dateL.setText("Date:");
    cancelB.setMaximumSize(new Dimension(53, 24));
    cancelB.setMinimumSize(new Dimension(53, 24));
    cancelB.setPreferredSize(new Dimension(53, 24));
    cancelB.setText("Cancel");
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    jLabel1.setText("mm/dd/yyyy");
    panel1.add(dateT, new XYConstraints(108, 17, 101, 30));
    panel1.add(dateL, new XYConstraints(41, 18, 67, 30));
    panel1.add(bOk, new XYConstraints(35, 61, 95, 28));
    panel1.add(cancelB, new XYConstraints(140, 61, 108, 28));
    panel1.add(jLabel1, new XYConstraints(211, 18, 67, 25));
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
