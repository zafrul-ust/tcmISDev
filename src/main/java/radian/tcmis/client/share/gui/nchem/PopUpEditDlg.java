package radian.tcmis.client.share.gui.nchem;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import javax.swing.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import radian.tcmis.client.share.gui.*;

public class PopUpEditDlg extends JDialog {
  XYLayout xYLayout1 = new XYLayout();
  StaticJLabel rejLabel = new StaticJLabel();
  public JTextArea rejArea = new JTextArea();
  JButton B = new JButton();
  String label = new String(" ");
  JPanel paneM = new JPanel();
  boolean editText = true;

  public PopUpEditDlg(Frame frame, String title, boolean modal) {
    this(frame,title,modal,"",true);
  }

  public PopUpEditDlg(Frame frame, String title, boolean modal, String label, boolean editableText) {
      super(frame, title, modal);
      this.label= label;
      editText = editableText;
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

  public PopUpEditDlg(Frame frame) {
    this(frame, "", false);
  }

  public PopUpEditDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public PopUpEditDlg(Frame frame, String title) {
    this(frame, title, false);
  }


  public void jbInit() throws Exception{
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    xYLayout1.setWidth(400);
    xYLayout1.setHeight(300);
    B.setText("Ok");
    B.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    B.addActionListener(new PopUpEditDlg_B_actionAdapter(this));
    B.addKeyListener(new PopUpEditDlg_B_keyAdapter(this));
    rejLabel.setText(label);
    paneM.setLayout(xYLayout1);
    rejArea.setLineWrap(true);
    rejArea.setWrapStyleWord(true);
    rejArea.setEnabled(editText);
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

  public void setEditText(boolean b) {
    editText = b;
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

class PopUpEditDlg_B_keyAdapter extends java.awt.event.KeyAdapter {
  PopUpEditDlg adaptee;

  PopUpEditDlg_B_keyAdapter(PopUpEditDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.B_keyPressed(e);
  }
}

class PopUpEditDlg_B_actionAdapter implements java.awt.event.ActionListener {
  PopUpEditDlg adaptee;

  PopUpEditDlg_B_actionAdapter(PopUpEditDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.B_actionPerformed(e);
  }
}
