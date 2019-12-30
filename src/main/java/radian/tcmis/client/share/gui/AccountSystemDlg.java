//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import radian.tcmis.client.share.helpers.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class AccountSystemDlg extends JDialog {
  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  boolean yesFlag = false;
  public JComboBox accSysC = new JComboBox();
  String selectedAccSys = null;

  public AccountSystemDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    parent = frame;
    try {
      jbInit();
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public AccountSystemDlg(Frame frame) {
    this(frame, "", false);
  }

  public AccountSystemDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public AccountSystemDlg(Frame frame, String title) {
    this(frame, title, false);
  }

  public String getSelectedAccSys(){
    return selectedAccSys;
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
    xYLayout1.setWidth(310);
    xYLayout1.setHeight(134);
    panel1.addKeyListener(new AccountSystemDlg_panel1_keyAdapter(this));
    bOk.setMaximumSize(new Dimension(53, 24));
    bOk.setMinimumSize(new Dimension(53, 24));
    bOk.setPreferredSize(new Dimension(53, 24));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new AccountSystemDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new AccountSystemDlg_bOk_actionAdapter(this));




    panel1.setLayout(xYLayout1);
    panel1.setMaximumSize(new Dimension(340, 100));
    panel1.setMinimumSize(new Dimension(340, 100));
    panel1.setPreferredSize(new Dimension(340, 100));
    panel1.add(accSysC, new XYConstraints(78, 17, 213, 32));
    panel1.add(bOk, new XYConstraints(106, 61, 118, 35));
    bOk.requestFocus();

    getContentPane().add(panel1);



    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    pack();
    CenterComponent.centerCompOnScreen(this);
  }

  public void setYesButtonText(String s){
    bOk.setText(s);
  }

  public boolean getConfirmationFlag(){
     return yesFlag;
  }
  public void setButtonFocus(boolean b){
      bOk.requestFocus();
  }


  void bOk_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     yesFlag = true;

     selectedAccSys = accSysC.getSelectedItem().toString();


     setVisible(false);
  }

  void bOk_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         yesFlag = true;
         setVisible(false);
     }
  }

  void panel1_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         yesFlag = false;
         setVisible(false);
     }
  }

  void this_windowClosing(WindowEvent e) {
    yesFlag = false;
    this.setVisible(false);
  }

}

class AccountSystemDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  AccountSystemDlg adaptee;

  AccountSystemDlg_bOk_actionAdapter(AccountSystemDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class AccountSystemDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  AccountSystemDlg adaptee;

  AccountSystemDlg_bOk_keyAdapter(AccountSystemDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class AccountSystemDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  AccountSystemDlg adaptee;

  AccountSystemDlg_panel1_keyAdapter(AccountSystemDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}



