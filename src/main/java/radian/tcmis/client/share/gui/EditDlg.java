

//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components

package radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import javax.swing.*;

//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class EditDlg extends JDialog {
  XYLayout xYLayout1 = new XYLayout();
  StaticJLabel rejLabel = new StaticJLabel();
  public JTextArea rejArea = new JTextArea();
  JButton B = new JButton();
  String label = new String(" ");
  JPanel paneM = new JPanel();

  public EditDlg(Frame frame, String title, boolean modal) {
    this(frame,title,modal,"");
  }

  public EditDlg(Frame frame, String title, boolean modal, String label) {
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

  public EditDlg(Frame frame) {
    this(frame, "", false);
  }

  public EditDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public EditDlg(Frame frame, String title) {
    this(frame, title, false);
  }


  public void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
*/
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    xYLayout1.setWidth(400);
    xYLayout1.setHeight(300);
    B.setText("Ok");
    B.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    B.addActionListener(new EditDlg_B_actionAdapter(this));
    B.addKeyListener(new EditDlg_B_keyAdapter(this));
    rejLabel.setText(label);
    paneM.setLayout(xYLayout1);
    rejArea.setLineWrap(true);
    rejArea.setWrapStyleWord(true);
    paneM.add(rejLabel,new XYConstraints(10,20,380,20));
    paneM.add(rejArea,new XYConstraints(10,50,380,200));
    paneM.add(B, new XYConstraints(124, 258, 152, 35));

  }

  public String getText() {
    return rejArea.getText();
  }

  public void setText(String text) {
    rejArea.setText(text);
  }

  void B_keyPressed(KeyEvent e) {
    //ok
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       this.setVisible(false);
    }
  }

  void B_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    this.setVisible(false);
  }
}

class EditDlg_B_keyAdapter extends java.awt.event.KeyAdapter {
  EditDlg adaptee;

  EditDlg_B_keyAdapter(EditDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.B_keyPressed(e);
  }
}

class EditDlg_B_actionAdapter implements java.awt.event.ActionListener {
  EditDlg adaptee;

  EditDlg_B_actionAdapter(EditDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.B_actionPerformed(e);
  }
}
