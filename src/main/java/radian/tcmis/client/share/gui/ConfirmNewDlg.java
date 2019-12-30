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
public class ConfirmNewDlg extends JDialog {
  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();
  public JTextArea textArea1 = new JTextArea();

  Frame parent;
  JButton noB = new JButton();
  public boolean yesFlag = false;
  JScrollPane jsp = new JScrollPane();

  public ConfirmNewDlg(Frame frame, String title, boolean modal) {
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

  public ConfirmNewDlg(Frame frame) {
    this(frame, "", false);
  }

  public ConfirmNewDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public ConfirmNewDlg(Frame frame, String title) {
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
    panel1.addKeyListener(new ConfirmNewDlg_panel1_keyAdapter(this));
    bOk.setActionCommand("");
    bOk.setText("Continue");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new ConfirmNewDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new ConfirmNewDlg_bOk_actionAdapter(this));
    textArea1.setEnabled(true);
    textArea1.setText("1234567890123456789012345678901234567890");
    textArea1.setEditable(false);
    textArea1.setMargin(new Insets(3,3,3,3));




    noB.setText("Cancel");
    noB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    noB.addActionListener(new ConfirmNewDlg_noB_actionAdapter(this));
    textArea1.addKeyListener(new ConfirmNewDlg_textArea1_keyAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(bOk, new XYConstraints(18, 180, 118, 35));
    panel1.add(jsp, new XYConstraints(26, 18, 265, 150));
    jsp.getViewport().add(textArea1);
    panel1.add(noB, new XYConstraints(176, 180, 118, 35));
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

  void bOk_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     yesFlag = true;
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

class ConfirmNewDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  ConfirmNewDlg adaptee;

  ConfirmNewDlg_bOk_actionAdapter(ConfirmNewDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class ConfirmNewDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  ConfirmNewDlg adaptee;

  ConfirmNewDlg_bOk_keyAdapter(ConfirmNewDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class ConfirmNewDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  ConfirmNewDlg adaptee;

  ConfirmNewDlg_panel1_keyAdapter(ConfirmNewDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

class ConfirmNewDlg_textArea1_keyAdapter extends java.awt.event.KeyAdapter {
  ConfirmNewDlg adaptee;

  ConfirmNewDlg_textArea1_keyAdapter(ConfirmNewDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.textArea1_keyPressed(e);
  }
}

class ConfirmNewDlg_noB_actionAdapter implements java.awt.event.ActionListener{
  ConfirmNewDlg adaptee;

  ConfirmNewDlg_noB_actionAdapter(ConfirmNewDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.noB_actionPerformed(e);
  }
}



























