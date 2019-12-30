
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import javax.swing.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import java.net.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class ChangePasswordDlg extends JDialog {


  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JLabel label1 = new JLabel();
  JLabel label3 = new JLabel();
  JLabel label4 = new JLabel();
  public JTextField userT = new JTextField();
  JPasswordField nPassT = new JPasswordField();
  JPasswordField n2PassT = new JPasswordField();
  JButton buttonControl1 = new JButton();
  JButton buttonControl2 = new JButton();

  protected String changedUserID = "";
  protected boolean passwordChanged = false;


  //CmisApp parent = null;
  Frame frame = null;
  GenericDlg err = null;
  CmisApp parent = null;
  Integer personnelId;


  public ChangePasswordDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    this.frame = frame;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }



  public ChangePasswordDlg(Frame frame) {
    this(frame, "", false);
  }

  public ChangePasswordDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public ChangePasswordDlg(Frame frame, String title) {
    this(frame, title, false);
  }

  public void setParent(CmisApp p) {
    this.parent = p;
  }

  public void setPersonnelId(Integer id) {
    this.personnelId = id;
  }

  private void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
*/
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    xYLayout1.setWidth(385);
    xYLayout1.setHeight(227);
    label1.setText("User Name:");
    label3.setText("New Password:");
    label4.setText("New Password Again:");
    userT.setEnabled(false);
    nPassT.setEchoChar('*');
    n2PassT.setEchoChar('*');
    n2PassT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        n2PassT_keyPressed(e);
      }
    });
    buttonControl1.setText("Ok");
    buttonControl1.addKeyListener(new ChangePasswordDlg_buttonControl1_keyAdapter(this));
    buttonControl1.addActionListener(new ChangePasswordDlg_buttonControl1_actionAdapter(this));
    buttonControl2.setText("Cancel");
    buttonControl2.addKeyListener(new ChangePasswordDlg_buttonControl2_keyAdapter(this));
    buttonControl2.addActionListener(new ChangePasswordDlg_buttonControl2_actionAdapter(this));
    buttonControl1.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    buttonControl2.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));

    panel1.setLayout(xYLayout1);
    panel1.add(label1, new XYConstraints(76, 28, -1, -1));
    panel1.add(label3, new XYConstraints(56, 67, -1, -1));
    panel1.add(label4, new XYConstraints(22, 106, -1, -1));
    panel1.add(userT, new XYConstraints(173, 28, 181, -1));
    panel1.add(nPassT, new XYConstraints(173, 65, 181, -1));
    panel1.add(n2PassT, new XYConstraints(173, 104, 181, -1));
    panel1.add(buttonControl1, new XYConstraints(32, 164, 135, 39));
    panel1.add(buttonControl2, new XYConstraints(218, 164, 135, 39));

    getContentPane().add(panel1);

    pack();

    CenterComponent.centerCompOnComp(frame,this);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

  }

  /*
  public void setParent(Object parent){
    this.parent = (CmisApp) parent;
  }
  */

  void changePassword(){
       if (userT.getText().length() == 0){
         String no = new String("Please enter the user name.");
         err = new GenericDlg(frame,"Error",true);
         err.setMsg(no);
         err.setVisible(true);
         userT.requestFocus();
         return;
       }
       if (!(new String(nPassT.getPassword()).equals(new String(n2PassT.getPassword())))){
           String no = new String("New password does not match. Please try again.");
           err = new GenericDlg(frame,"Error",true);
           err.setMsg(no);
           err.setVisible(true);
           nPassT.requestFocus();
           return;
       }
    String changed = new String("");
    try {
      InetAddress ip= InetAddress.getLocalHost();
      TcmisHttpConnection cgi = new TcmisHttpConnection("UserProfileNew",parent);
      Hashtable query = new Hashtable();
      query.put("LOGON_ID",userT.getText().trim()); //String, String
      query.put("USER_ID",personnelId); //String, Integer
      query.put("NEW_PASSWORD",new String(nPassT.getPassword()).trim());
      query.put("ACTION","CHANGE_PASSWORD");
      Hashtable result = cgi.getResultHash(query);
      changed = (String) result.get("MSG");
    } catch (Exception e) {
      e.printStackTrace();
    }

    if ("OK".equalsIgnoreCase(changed)){
      String no = new String("Password successfully changed.");
      changedUserID = userT.getText();
      passwordChanged = true;
      err = new GenericDlg(frame,"Success",true);
      err.setMsg(no);
      err.setVisible(true);
      this.setVisible(false);
    } else {
      String no = new String("Wrong information, please try again.");
      err = new GenericDlg(frame,"Error",true);
      err.setMsg(no);
      err.setVisible(true);
      userT.requestFocus();
    }
    return;
  }

  public boolean pwChanged() {
    return passwordChanged;
  }
  public String getUserId() {
    return changedUserID;
  }



  void this_windowClosing(WindowEvent e) {
    this.setVisible(false);
  }


  void buttonControl2_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

  void buttonControl2_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         this.setVisible(false);
    }
  }

  void buttonControl1_actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        changePassword();
  }

  void buttonControl1_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         changePassword();
    }
  }

  void n2PassT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         changePassword();
    }
  }

}

class ChangePasswordDlg_buttonControl2_actionAdapter implements java.awt.event.ActionListener{
  ChangePasswordDlg adaptee;

  ChangePasswordDlg_buttonControl2_actionAdapter(ChangePasswordDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.buttonControl2_actionPerformed(e);
  }
}

class ChangePasswordDlg_buttonControl2_keyAdapter extends java.awt.event.KeyAdapter {
  ChangePasswordDlg adaptee;

  ChangePasswordDlg_buttonControl2_keyAdapter(ChangePasswordDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.buttonControl2_keyPressed(e);
  }
}

class ChangePasswordDlg_buttonControl1_actionAdapter implements java.awt.event.ActionListener{
  ChangePasswordDlg adaptee;

  ChangePasswordDlg_buttonControl1_actionAdapter(ChangePasswordDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.buttonControl1_actionPerformed(e);
  }
}

class ChangePasswordDlg_buttonControl1_keyAdapter extends java.awt.event.KeyAdapter {
  ChangePasswordDlg adaptee;

  ChangePasswordDlg_buttonControl1_keyAdapter(ChangePasswordDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.buttonControl1_keyPressed(e);
  }
}





























