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

public class TcmISMessageDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();
  public JTextArea textArea1 = new JTextArea();

  Frame parent;
  JButton noB = new JButton();
  public boolean yesFlag = false;
  JScrollPane jScrollPane1 = new JScrollPane();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public TcmISMessageDlg(Frame frame, String title, boolean modal) {
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

  public TcmISMessageDlg(Frame frame) {
    this(frame, "", false);
  }

  public TcmISMessageDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public TcmISMessageDlg(Frame frame, String title) {
    this(frame, title, false);
  }

  private void jbInit() throws Exception{
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    panel1.addKeyListener(new TcmISMessageDlg_panel1_keyAdapter(this));
    bOk.setFont(new java.awt.Font("Dialog", 0, 10));
    bOk.setMaximumSize(new Dimension(120, 23));
    bOk.setMinimumSize(new Dimension(120, 23));
    bOk.setPreferredSize(new Dimension(120, 23));
    bOk.setActionCommand("");
    bOk.setText("Continue");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new TcmISMessageDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new TcmISMessageDlg_bOk_actionAdapter(this));
    textArea1.setEnabled(true);
    textArea1.setText("1234567890123456789012345678901234567890");
    textArea1.setEditable(false);
    textArea1.setMargin(new Insets(3,3,3,3));

    noB.setText("Exit");
    noB.setFont(new java.awt.Font("Dialog", 0, 10));
    noB.setMaximumSize(new Dimension(120, 23));
    noB.setMinimumSize(new Dimension(120, 23));
    noB.setPreferredSize(new Dimension(120, 23));
    noB.setIcon(ResourceLoader.getImageIcon("images/button/canel.gif"));
    noB.addActionListener(new TcmISMessageDlg_noB_actionAdapter(this));
    textArea1.addKeyListener(new TcmISMessageDlg_textArea1_keyAdapter(this));
    panel1.setLayout(gridBagLayout1);
    panel1.setMaximumSize(new Dimension(500, 400));
    panel1.setMinimumSize(new Dimension(500, 400));
    panel1.setPreferredSize(new Dimension(500, 400));
    jScrollPane1.setMaximumSize(new Dimension(80, 50));
    jScrollPane1.setMinimumSize(new Dimension(80, 50));
    jScrollPane1.setPreferredSize(new Dimension(80, 50));
    panel1.add(bOk, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 130, 10, 10), 0, 0));
    panel1.add(noB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 270, 10, 10), 0, 0));
    panel1.add(jScrollPane1, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    jScrollPane1.getViewport().add(textArea1, null);
    bOk.requestFocus();

    getContentPane().add(panel1);



    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    pack();
    CenterComponent.centerCompOnScreen(this);
  }

  public void setMsg(String S) {
     textArea1.setText(ClientHelpObjs.wrapString(S));
     //textArea1.setText(S);
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

class TcmISMessageDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  TcmISMessageDlg adaptee;

  TcmISMessageDlg_bOk_actionAdapter(TcmISMessageDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class TcmISMessageDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  TcmISMessageDlg adaptee;

  TcmISMessageDlg_bOk_keyAdapter(TcmISMessageDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class TcmISMessageDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  TcmISMessageDlg adaptee;

  TcmISMessageDlg_panel1_keyAdapter(TcmISMessageDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

class TcmISMessageDlg_textArea1_keyAdapter extends java.awt.event.KeyAdapter {
  TcmISMessageDlg adaptee;

  TcmISMessageDlg_textArea1_keyAdapter(TcmISMessageDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.textArea1_keyPressed(e);
  }
}

class TcmISMessageDlg_noB_actionAdapter implements java.awt.event.ActionListener{
  TcmISMessageDlg adaptee;

  TcmISMessageDlg_noB_actionAdapter(TcmISMessageDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.noB_actionPerformed(e);
  }
}



























