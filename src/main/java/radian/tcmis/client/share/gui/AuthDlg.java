
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description


package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AuthDlg extends JDialog {
  boolean yesFlag = false;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  JTextField userT = new JTextField();
  StaticJLabel jLabel2 = new StaticJLabel();
  JPasswordField passT = new JPasswordField();
  JButton jButton1 = new JButton();
  StaticJLabel descL = new StaticJLabel();
  JButton jButton2 = new JButton();
  DataJLabel realmL = new DataJLabel();
  JCheckBox passCheck = new JCheckBox();


  public AuthDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  public AuthDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
*/
    xYLayout1.setHeight(236);
    panel1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        panel1_keyPressed(e);
      }
    });
    xYLayout1.setWidth(400);
    jLabel1.setText("UserName:");
    userT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        userT_keyPressed(e);
      }
    });
    jLabel2.setText("Password:");
    passT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        passT_keyPressed(e);
      }
    });
    jButton1.setText("OK");
    jButton1.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        jButton1_keyPressed(e);
      }
    });
    jButton1.addActionListener(new AuthDlg_jButton1_actionAdapter(this));
    descL.setText("Please enter the username/password for");
    jButton2.setText("Cancel");
    realmL.setText(" ");
    passCheck.setText("Save Password");
    passCheck.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        passCheck_keyPressed(e);
      }
    });
    jButton2.addActionListener(new AuthDlg_jButton2_actionAdapter(this));
    jButton2.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        jButton2_keyPressed(e);
      }
    });
    panel1.setLayout(xYLayout1);
    getContentPane().add(panel1);
    panel1.add(jLabel1, new XYConstraints(48, 72, -1, -1));
    panel1.add(userT, new XYConstraints(115, 72, 232, -1));
    panel1.add(jLabel2, new XYConstraints(48, 100, -1, -1));
    panel1.add(passT, new XYConstraints(115, 99, 233, -1));
    panel1.add(jButton1, new XYConstraints(40, 151, 133, 45));
    panel1.add(descL, new XYConstraints(86, 12, 257, -1));
    panel1.add(jButton2, new XYConstraints(232, 151, 133, 45));
    panel1.add(realmL, new XYConstraints(86, 34, 303, -1));
    panel1.add(passCheck, new XYConstraints(115, 123, 186, -1));

    userT.requestFocus();
    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    pack();
    CenterComponent.centerCompOnScreen(this);

    userT.requestFocus();
  }

  public String getUser(){
    return userT.getText();

  }

  public String getPass(){
    return new String(passT.getPassword());
  }

  public void setTitle(String t){
    realmL.setText(realmL.getText() + " " + t);
  }

  public boolean getConfirmationFlag(){
     return yesFlag;
  }

  void jButton1_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     yesFlag = true;
     setVisible(false);
  }
  void jButton2_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     yesFlag = false;
     setVisible(false);
  }

  void jButton1_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         yesFlag = true;
         setVisible(false);
    }
  }

  void jButton2_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         yesFlag = false;
         setVisible(false);
    }
  }

  public boolean isSavePassword(){
     return passCheck.isSelected();
  }

  void userT_keyPressed(KeyEvent e) {

  }

  void passT_keyPressed(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         yesFlag = true;
         setVisible(false);
      }
  }

  void passCheck_keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         yesFlag = true;
         setVisible(false);
      }
  }

  void panel1_keyPressed(KeyEvent e) {

  }
}

class AuthDlg_jButton1_actionAdapter implements java.awt.event.ActionListener{
  AuthDlg adaptee;


  AuthDlg_jButton1_actionAdapter(AuthDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class AuthDlg_jButton2_actionAdapter implements java.awt.event.ActionListener{
  AuthDlg adaptee;


  AuthDlg_jButton2_actionAdapter(AuthDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}



