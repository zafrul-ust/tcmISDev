//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components

package radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import com.borland.jbcl.layout.*;

public class EngineeringEvalRejectDlg extends JDialog {
  CmisApp cmis ;
  XYLayout xYLayout1 = new XYLayout();
  StaticJLabel rejLabel = new StaticJLabel();
  JTextArea rejArea = new JTextArea();
  JButton okB = new JButton();
  String label = new String(" ");
  JPanel paneM = new JPanel();
  JButton cancelB = new JButton();
  boolean rejectEval = false;

  public EngineeringEvalRejectDlg(Frame frame, String title, boolean modal) {
    this(frame,title,modal,"");
  }

  public EngineeringEvalRejectDlg(Frame frame, String title, boolean modal, String label) {
      super(frame, title, modal);
      this.label= label;
      try {
        jbInit();
        getContentPane().add(paneM);
        pack();
        CenterComponent.centerCompOnScreen(this);

         //colors and fonts
         ClientHelpObjs.makeDefaultColors(this);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
  }

  public EngineeringEvalRejectDlg(Frame frame) {
    this(frame, "", false);
  }

  public EngineeringEvalRejectDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public EngineeringEvalRejectDlg(Frame frame, String title) {
    this(frame, title, false);
  }

  public void setParent(CmisApp c){
    cmis = c;
  }


  public void jbInit() throws Exception{
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    xYLayout1.setWidth(400);
    xYLayout1.setHeight(300);
    okB.setText("Update");
    okB.setFont(new java.awt.Font("Dialog", 0, 10));
    okB.setMaximumSize(new Dimension(103, 24));
    okB.setMinimumSize(new Dimension(103, 24));
    okB.setPreferredSize(new Dimension(103, 24));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));
    okB.addActionListener(new EngineeringEvalRejectDlg_okB_actionAdapter(this));
    okB.addKeyListener(new EngineeringEvalRejectDlg_okB_keyAdapter(this));
    rejLabel.setText(label);
    paneM.setLayout(xYLayout1);
    cancelB.setFont(new java.awt.Font("Dialog", 0, 10));
    cancelB.setMaximumSize(new Dimension(99, 24));
    cancelB.setMinimumSize(new Dimension(99, 24));
    cancelB.setPreferredSize(new Dimension(99, 24));
    cancelB.setText("Cancel");
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addActionListener(new EngineeringEvalRejectDlg_cancelB_actionAdapter(this));
    rejArea.setLineWrap(true);
    rejArea.setWrapStyleWord(true);
    paneM.add(rejLabel,new XYConstraints(10,20,380,20));
    paneM.add(rejArea,new XYConstraints(10,50,380,200));
    paneM.add(okB, new XYConstraints(47, 259, 152, -1));
    paneM.add(cancelB, new XYConstraints(204, 259, -1, -1));

  }

  public String getText() {
    return rejArea.getText();
  }

  public void setText(String text) {
    rejArea.setText(text);
  }

  void okB_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (BothHelpObjs.isBlankString(rejArea.getText())) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg("Please enter reason(s) for rejecting this engineering evaluation.");
        gd.setVisible(true);
        return;
      }else {
        this.setVisible(false);
      }
    }
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (BothHelpObjs.isBlankString(rejArea.getText())) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please enter reason(s) for rejecting this engineering evaluation.");
      gd.setVisible(true);
      return;
    }else {
      rejectEval = true;
      this.setVisible(false);
    }
  }

  void this_windowClosing(WindowEvent e) {
    rejectEval = false;
    this.setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    rejectEval = false;
    this.setVisible(false);
  }
}

class EngineeringEvalRejectDlg_okB_keyAdapter extends java.awt.event.KeyAdapter {
  EngineeringEvalRejectDlg adaptee;

  EngineeringEvalRejectDlg_okB_keyAdapter(EngineeringEvalRejectDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.okB_keyPressed(e);
  }
}

class EngineeringEvalRejectDlg_okB_actionAdapter implements java.awt.event.ActionListener {
  EngineeringEvalRejectDlg adaptee;

  EngineeringEvalRejectDlg_okB_actionAdapter(EngineeringEvalRejectDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class EngineeringEvalRejectDlg_cancelB_actionAdapter implements java.awt.event.ActionListener {
  EngineeringEvalRejectDlg adaptee;

  EngineeringEvalRejectDlg_cancelB_actionAdapter(EngineeringEvalRejectDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}



























