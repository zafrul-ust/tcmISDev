

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class UpdateDlg extends JDialog {

  String nowVersion;
  boolean mandatory;
  public boolean update = false;

  JPanel panel1 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JLabel line1L = new JLabel();
  JLabel line2L = new JLabel();

  CmisApp par = null;
  XYLayout xYLayout1 = new XYLayout();

  public UpdateDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();

      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public UpdateDlg(Frame frame) {
    this(frame, "", false);
  }

  public UpdateDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public UpdateDlg(Frame frame, String title) {
    this(frame, title, false);
  }


  public UpdateDlg(CmisApp p, String title,String version,boolean m) {
    this(new JFrame(), title, true);
    this.par = p;
    nowVersion = version;
    mandatory = m;
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

    okB.setText("Update Now");
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    okB.addKeyListener(new UpdateDlg_okB_keyAdapter(this));
    okB.addActionListener(new UpdateDlg_okB_actionAdapter(this));
    cancelB.setText("Update Later");
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addKeyListener(new UpdateDlg_cancelB_keyAdapter(this));
    cancelB.addActionListener(new UpdateDlg_cancelB_actionAdapter(this));
    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(173);
    xYLayout1.setWidth(297);
    line2L.setHorizontalAlignment(SwingConstants.CENTER);
    panel1.add(line1L, new XYConstraints(38, 7, 221, 59));
    panel1.add(okB, new XYConstraints(2, 120, 141, 42));
    panel1.add(cancelB, new XYConstraints(150, 120, 141, 42));
    panel1.add(line2L, new XYConstraints(38, 56, 223, 42));
    line1L.setText("tcmIS has been changed. You will need");
    line2L.setText(" to update your local copy.");
    getContentPane().add(panel1);

  }

  public void showMe(){
    if (mandatory){
        cancelB.setText("Cancel");
    }
    CenterComponent.centerCompOnComp(par,this);
    this.setVisible(true);
  }

  public void paint(Graphics g){
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    try {
      g.drawImage(ResourceLoader.getImageIcon("images/button/stop.gif").getImage(),0,0,this);
    } catch (Exception e){}
// CBK - begin
    super.paint(g);
// CBK - end
  }

  void okB_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    update = true;
    this.setVisible(false);
  }

  void okB_keyPressed(KeyEvent e) {
    update = true;
    this.setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    update = false;
    this.setVisible(false);

  }

  void cancelB_keyPressed(KeyEvent e) {
    update = false;
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    update = false;
    this.setVisible(false);
  }

}

class UpdateDlg_okB_actionAdapter implements java.awt.event.ActionListener {
  UpdateDlg adaptee;

  UpdateDlg_okB_actionAdapter(UpdateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class UpdateDlg_okB_keyAdapter extends java.awt.event.KeyAdapter {
  UpdateDlg adaptee;

  UpdateDlg_okB_keyAdapter(UpdateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.okB_keyPressed(e);
  }
}

class UpdateDlg_cancelB_actionAdapter implements java.awt.event.ActionListener {
  UpdateDlg adaptee;

  UpdateDlg_cancelB_actionAdapter(UpdateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class UpdateDlg_cancelB_keyAdapter extends java.awt.event.KeyAdapter {
  UpdateDlg adaptee;

  UpdateDlg_cancelB_keyAdapter(UpdateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.cancelB_keyPressed(e);
  }
}




























