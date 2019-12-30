
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
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class CancelConfirmDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();
  public JTextArea textArea1 = new JTextArea();

  Frame parent;
  JButton noB = new JButton();
  public boolean yesFlag = false;
  JTextField textT = new JTextField();
  StaticJLabel passL = new StaticJLabel();
  JPasswordField tPass = new JPasswordField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  StaticJLabel reasonL = new StaticJLabel();
  JTextArea reasonT = new JTextArea();

  public CancelConfirmDlg(Frame frame, String title, boolean modal) {
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

  public CancelConfirmDlg(Frame frame) {
    this(frame, "", false);
  }

  public CancelConfirmDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public CancelConfirmDlg(Frame frame, String title) {
    this(frame, title, false);
  }

  private void jbInit() throws Exception{
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    panel1.addKeyListener(new CancelConfirmDlg_panel1_keyAdapter(this));
    bOk.setActionCommand("");
    bOk.setText("Yes");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new CancelConfirmDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new CancelConfirmDlg_bOk_actionAdapter(this));
    textArea1.setEnabled(true);
    textArea1.setText("1234567890123456789012345678901234567890");
    textArea1.setEditable(false);
    textArea1.setMargin(new Insets(3,3,3,3));

    textT.setEnabled(false);
    textT.setVisible(false);

    passL.setText("Password: ");
    tPass.setEnabled(false);
    tPass.setVisible(false);

    reasonL.setText("Reasons: (240 chars max.)");

    noB.setText("No");
    noB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    noB.addActionListener(new CancelConfirmDlg_noB_actionAdapter(this));
    textArea1.addKeyListener(new CancelConfirmDlg_textArea1_keyAdapter(this));
    panel1.setLayout(gridBagLayout1);
    reasonT.setLineWrap(true);
    reasonT.setWrapStyleWord(true);
    reasonT.setMaximumSize(new Dimension(340, 80));
    reasonT.setMinimumSize(new Dimension(340, 80));
    reasonT.setPreferredSize(new Dimension(340, 80));

    panel1.setMaximumSize(new Dimension(340, 280));
    panel1.setMinimumSize(new Dimension(340, 280));
    panel1.setPreferredSize(new Dimension(340, 280));
    panel1.add(textArea1,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(reasonL,  new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(reasonT,  new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
    panel1.add(passL,  new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(tPass,  new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 55, 5, 30), 0, 0));
    panel1.add(bOk,  new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel1.add(noB,  new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

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
	 if (reasonT.getText().length() <= 240)
	 {
	   yesFlag=true;
	   setVisible( false );
	 }
	 else
	 {
	   GenericDlg dlg = new GenericDlg(parent,"Reason too Long",true);
	   dlg.setMsg("The reason you entered is too long.\nPlease reduce the length\nto less 240 chars and try again.");
	   dlg.setVisible(true);
	   return;
	}
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

class CancelConfirmDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  CancelConfirmDlg adaptee;

  CancelConfirmDlg_bOk_actionAdapter(CancelConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class CancelConfirmDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  CancelConfirmDlg adaptee;

  CancelConfirmDlg_bOk_keyAdapter(CancelConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class CancelConfirmDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  CancelConfirmDlg adaptee;

  CancelConfirmDlg_panel1_keyAdapter(CancelConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

class CancelConfirmDlg_textArea1_keyAdapter extends java.awt.event.KeyAdapter {
  CancelConfirmDlg adaptee;

  CancelConfirmDlg_textArea1_keyAdapter(CancelConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.textArea1_keyPressed(e);
  }
}

class CancelConfirmDlg_noB_actionAdapter implements java.awt.event.ActionListener{
  CancelConfirmDlg adaptee;

  CancelConfirmDlg_noB_actionAdapter(CancelConfirmDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.noB_actionPerformed(e);
  }
}




























