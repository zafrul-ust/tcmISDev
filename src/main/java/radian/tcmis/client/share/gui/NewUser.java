//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class NewUser extends JDialog {

  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  CheckboxGroup checkboxGroup1 = new CheckboxGroup();
  JButton createB = new JButton();
  JButton cancelB = new JButton();

  Hashtable facXref = new Hashtable();

  JFrame parent;
  CmisApp grandParent;

  Integer uid;
  JComboBox facC = new JComboBox();
  StaticJLabel label3 = new StaticJLabel();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  StaticJLabel midInitL = new StaticJLabel();
  JTextField midInitT = new JTextField();
  JTextField firstNameT = new JTextField();
  JTextField lastNameT = new JTextField();
  XYLayout xYLayout2 = new XYLayout();
  StaticJLabel jLabel3 = new StaticJLabel();
  JTextField loginT = new JTextField();
  StaticJLabel emailL = new StaticJLabel();
  JTextField emailT = new JTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public NewUser(JFrame frame, String title, boolean modal) {
    super(frame, "New User", modal);
    parent = frame;
    try {
      jbInit();
      pack();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setGrandParent(CmisApp m) {
    grandParent = m;
  }

  private void jbInit() throws Exception {
    this.setResizable(false);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    xYLayout1.setWidth(445);
    xYLayout1.setHeight(314);

    jLabel1.setText("First Name: *");
    firstNameT.setPreferredSize(new Dimension(120, 21));
    midInitL.setText("Middle Initial:");
    midInitT.setPreferredSize(new Dimension(50, 21));
    jLabel2.setText("Last Name: *");
    lastNameT.setPreferredSize(new Dimension(120, 21));
    emailL.setText("Email: *");
    emailT.setPreferredSize(new Dimension(120, 21));
    jLabel3.setText("Logon ID: *");
    loginT.setPreferredSize(new Dimension(120, 21));
    label3.setText("Preferred Facility: *");

    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel2.setLayout(gridBagLayout1);
    createB.setMaximumSize(new Dimension(97, 25));
    createB.setText("Create");
    createB.setPreferredSize(new Dimension(97, 25));
    createB.addActionListener(new NewUser_createB_actionAdapter(this));
    createB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));
    createB.setMinimumSize(new Dimension(97, 25));
    cancelB.setMaximumSize(new Dimension(97, 25));
    cancelB.setText("Cancel");
    cancelB.setPreferredSize(new Dimension(97, 25));
    cancelB.addActionListener(new NewUser_cancelB_actionAdapter(this));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.setMinimumSize(new Dimension(97, 25));
    panel1.setLayout(xYLayout1);
    jPanel2.setMinimumSize(new Dimension(347, 180));
    jPanel2.setPreferredSize(new Dimension(393, 180));
    panel1.setMaximumSize(new Dimension(445, 210));
    panel1.setMinimumSize(new Dimension(445, 210));
    panel1.setPreferredSize(new Dimension(445, 210));
    panel1.add(jPanel2, new XYConstraints(10, 10, 425, 160));

    jPanel2.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
    jPanel2.add(firstNameT, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 0), 0, 0));
    jPanel2.add(midInitL, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
    jPanel2.add(midInitT, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 0), 0, 0));
    jPanel2.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
    jPanel2.add(lastNameT, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 0), 0, 0));
    jPanel2.add(emailL, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
    jPanel2.add(emailT, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 0), 0, 0));
    jPanel2.add(jLabel3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
    jPanel2.add(loginT, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 0), 0, 0));
    jPanel2.add(label3, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(facC, new GridBagConstraints(1, 4, 1, 1, 3.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));

    panel1.add(jPanel1, new XYConstraints(10, 164, 425, 52));
    jPanel1.add(createB, null);
    jPanel1.add(cancelB, null);
    getContentPane().add(panel1);

    CenterComponent.centerCompOnScreen(this);
    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }

  public Integer getUId() {
    return uid;
  }

  public String getFacId() {
    return (String) facXref.get(facC.getSelectedItem());
  }

  public void setFacs(Hashtable facs) {
    facXref = facs;
  }

  void loadIt() {
    if (facXref.size() <= 0) {
      return;
    }
    for (Enumeration E = facXref.keys(); E.hasMoreElements(); ) {
      facC.addItem( (String) E.nextElement());
    }
    facC = ClientHelpObjs.choiceSort(facC);
    firstNameT.requestFocus();
  }

  boolean auditFields() {
    String msg = "";
    if (BothHelpObjs.isBlankString(firstNameT.getText())) {
      msg = "You must enter a first name.";
    }
    if (BothHelpObjs.isBlankString(lastNameT.getText())) {
      msg = "You must enter a last name.";
    }
    if (BothHelpObjs.isBlankString(loginT.getText())) {
      msg = "You must enter a login Name.";
    }
    if (BothHelpObjs.isBlankString(emailT.getText())) {
      msg = "You must enter an email address.";
    }else {
      //checking format _@_._
      String tmp = emailT.getText().trim();
      int indexOfAt = tmp.indexOf("@");
      int indexOfDot = -1;
      if (tmp.length() > indexOfAt && indexOfAt > 0) {
        indexOfDot = tmp.lastIndexOf(".");
      }else {
        msg = "Please enter a valid email format\n(example: tcmis@haastcm.com)";
      }
      if (indexOfDot > indexOfAt+1 && indexOfDot < tmp.length()-1) {
        msg = ""; //format is okay
      }else {
        msg = "Please enter a valid email format\n(example: tcmis@haastcm.com)";
      }
    } //end of checking email format

    if (BothHelpObjs.isBlankString(msg)) {
      return true;
    }

    GenericDlg gd = new GenericDlg(grandParent.getMain(), "Add New User", true);
    gd.setMsg(msg);
    gd.setVisible(true);
    return false;
  }

  boolean insertNewUser() {
    if (!auditFields()) {
      return false;
    }

    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("UserProfileNew", grandParent);
      Hashtable query = new Hashtable();

      query.put("ACTION", "CREATE_NEW_USER");
      query.put("ADMIN_USER_ID",grandParent.getUserId());  //admin user_id
      query.put("PREF_FACILITY_ID", facXref.get(facC.getSelectedItem()).toString());
      query.put("LAST_NAME", lastNameT.getText().trim());
      query.put("FIRST_NAME", firstNameT.getText().trim());
      query.put("MI", midInitT.getText().trim());
      query.put("LOGON_ID", loginT.getText().trim());
      query.put("EMAIL", emailT.getText().trim());

      Hashtable result = cgi.getResultHash(query);
      String[] data = (String[])result.get("DATA");
      String msg = data[1];
      if (!"0".equals(data[0]) && !BothHelpObjs.isBlankString(data[0]) ) {
        uid = new Integer(data[0]);
        return true;
      } else {
        GenericDlg dlg = new GenericDlg(grandParent.getMain(), "Error", true);
        dlg.setMsg(msg);
        dlg.setVisible(true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  void createB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (insertNewUser()) {
      setVisible(false);
    }
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    uid = null;
    setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    uid = null;
    this.setVisible(false);
  }

  /* 10-25-01
     void uidT_textValueChanged(TextEvent e) {
     try{
       Integer tmpI = new Integer(uidT.getText());
    } catch (Exception e1){
       String msg = new String("Please enter a valid number.");
       GenericDlg dlg = new GenericDlg(grandParent.getMain(),"Error",true);
       dlg.setMsg(msg);
       dlg.setVisible(true);
       uidT.requestFocus();
    }
     }*/

}

class NewUser_createB_actionAdapter implements java.awt.event.ActionListener {
  NewUser adaptee;

  NewUser_createB_actionAdapter(NewUser adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.createB_actionPerformed(e);
  }
}

class NewUser_cancelB_actionAdapter implements java.awt.event.ActionListener {
  NewUser adaptee;

  NewUser_cancelB_actionAdapter(NewUser adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}
/*
 class NewUser_uidT_textAdapter implements java.awt.event.TextListener{
  NewUser adaptee;
  NewUser_uidT_textAdapter(NewUser adaptee) {
    this.adaptee = adaptee;
  }
  public void textValueChanged(TextEvent e) {
    adaptee.uidT_textValueChanged(e);
  }
 }*/
