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
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class DisplayTextDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();
  public JTextArea textArea1 = new JTextArea();
  Frame parent;
  public boolean yesFlag = false;
  JScrollPane jsp = new JScrollPane();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public DisplayTextDlg(Frame frame, String title, boolean modal) {
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

  public DisplayTextDlg(Frame frame) {
    this(frame, "", false);
  }

  public DisplayTextDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public DisplayTextDlg(Frame frame, String title) {
    this(frame, title, false);
  }

  private void jbInit() throws Exception{
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    panel1.addKeyListener(new DisplayTextDlg_panel1_keyAdapter(this));
    bOk.setActionCommand("");
    bOk.setText("OK");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new DisplayTextDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new DisplayTextDlg_bOk_actionAdapter(this));
    textArea1.setEnabled(true);
    textArea1.setText("1234567890123456789012345678901234567890");
    textArea1.setEditable(false);
    textArea1.setMargin(new Insets(3,3,3,3));

    textArea1.addKeyListener(new DisplayTextDlg_textArea1_keyAdapter(this));
    panel1.setLayout(gridBagLayout1);
    panel1.setMinimumSize(new Dimension(350, 350));
    panel1.setMaximumSize(new Dimension(350, 350));
    panel1.setPreferredSize(new Dimension(350, 350));
    panel1.add(bOk,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jsp,    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 1, 1));
    jsp.getViewport().add(textArea1);
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

  public boolean getConfirmationFlag(){
     return yesFlag;
  }
  public void setButtonFocus(boolean b){
      bOk.requestFocus();
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

  void this_windowClosing(WindowEvent e) {
    yesFlag = false;
    this.setVisible(false);
  }

}

class DisplayTextDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  DisplayTextDlg adaptee;

  DisplayTextDlg_bOk_actionAdapter(DisplayTextDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class DisplayTextDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  DisplayTextDlg adaptee;

  DisplayTextDlg_bOk_keyAdapter(DisplayTextDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class DisplayTextDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  DisplayTextDlg adaptee;

  DisplayTextDlg_panel1_keyAdapter(DisplayTextDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

class DisplayTextDlg_textArea1_keyAdapter extends java.awt.event.KeyAdapter {
  DisplayTextDlg adaptee;

  DisplayTextDlg_textArea1_keyAdapter(DisplayTextDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.textArea1_keyPressed(e);
  }
}



























