
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
import radian.tcmis.both1.helpers.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class ConfirmDlg extends JDialog {
  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();
  public JTextArea textArea1 = new JTextArea();

  Frame parent;
  JButton noB = new JButton();
  public boolean yesFlag = false;
  JTextField textT = new JTextField();
  JPasswordField tPass = new JPasswordField();

  public ConfirmDlg(Frame frame, String title, boolean modal) {
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

  public ConfirmDlg(Frame frame) {
    this(frame, "", false);
  }

  public ConfirmDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public ConfirmDlg(Frame frame, String title) {
    this(frame, title, false);
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
    xYLayout1.setHeight(232);
    panel1.addKeyListener(new ConfirmDlg_panel1_keyAdapter(this));
    bOk.setActionCommand("");
    bOk.setText("Yes");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new ConfirmDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new ConfirmDlg_bOk_actionAdapter(this));
    textArea1.setEnabled(true);
    textArea1.setText("1234567890123456789012345678901234567890");
    textArea1.setEditable(false);
    textArea1.setMargin(new Insets(3,3,3,3));

    textT.setEnabled(false);
    textT.setVisible(false);

    tPass.setEnabled(false);
    tPass.setVisible(false);

    noB.setText("No");
    noB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    noB.addActionListener(new ConfirmDlg_noB_actionAdapter(this));
    textArea1.addKeyListener(new ConfirmDlg_textArea1_keyAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(bOk, new XYConstraints(18, 180, 118, 35));
    panel1.add(textArea1, new XYConstraints(26, 18, 259, 132));
    panel1.add(noB, new XYConstraints(176, 180, 118, 35));
    panel1.add(textT, new XYConstraints(63, 156, 182, -1));
    panel1.add(tPass, new XYConstraints(63, 156, 182, -1));
    bOk.requestFocus();

    getContentPane().add(panel1);



    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    pack();
    CenterComponent.centerCompOnScreen(this);
  }

  public void setMsg(String S) {
     textArea1.setText(ClientHelpObjs.wrapString(S));
  }
  public void setYesButtonText(String s){
    bOk.setText(s);
  }
  public void setNoButtonText(String s){
    noB.setText(s);
  }

  public boolean getConfirmationFlag(){
     return yesFlag;
  }
  public void setButtonFocus(boolean b){
    if(b){
      bOk.requestFocus();
    }else{
      noB.requestFocus();
    }
  }

  public void setTextTVisible(boolean b){
    textT.setEnabled(b);
    textT.setVisible(b);
  }

  public void setPassTVisible(boolean b){
    tPass.setEnabled(b);
    tPass.setVisible(b);
  }

  //trong 3/13/00
  public void setTextT(String s) {
    textT.setText(s);
  }

  public String getTextT(){
    return textT.getText();
  }

  public String getPassT(){
    return new String(tPass.getPassword());
  }

  void bOk_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     if (textT.isVisible() && BothHelpObjs.isBlankString(textT.getText())) {
      textArea1.setText("Yes requires a logon ID.");
      return;
     }
     yesFlag = true;
     setVisible(false);
  }

  void bOk_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (textT.isVisible() && BothHelpObjs.isBlankString(textT.getText())) {
        textArea1.setText("Yes requires a logon ID.");
        return;
      }
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

  void textArea1_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         yesFlag = false;
         setVisible(false);
     }
  }

  void noB_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     yesFlag = false;
     setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    yesFlag = false;
    this.setVisible(false);
  }

}

class ConfirmDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  ConfirmDlg adaptee;

  ConfirmDlg_bOk_actionAdapter(ConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class ConfirmDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  ConfirmDlg adaptee;

  ConfirmDlg_bOk_keyAdapter(ConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class ConfirmDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  ConfirmDlg adaptee;

  ConfirmDlg_panel1_keyAdapter(ConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

class ConfirmDlg_textArea1_keyAdapter extends java.awt.event.KeyAdapter {
  ConfirmDlg adaptee;

  ConfirmDlg_textArea1_keyAdapter(ConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.textArea1_keyPressed(e);
  }
}

class ConfirmDlg_noB_actionAdapter implements java.awt.event.ActionListener{
  ConfirmDlg adaptee;

  ConfirmDlg_noB_actionAdapter(ConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.noB_actionPerformed(e);
  }
}




























