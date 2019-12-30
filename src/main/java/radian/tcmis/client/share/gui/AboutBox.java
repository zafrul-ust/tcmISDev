
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import radian.tcmis.client.share.helpers.*;
import javax.swing.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class AboutBox extends Dialog {

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel panel3 = new JPanel();
  JPanel panel4 = new JPanel();
  JButton imageControl1 = new JButton();
  Label label1 = new Label();
  Label label2 = new Label();
  Label label3 = new Label();
  String product = new String();
  String copyright = "Haas TCM Copyright (c) 1998";
  String comments = "Total Chemical Management Information System";
  XYLayout xYLayout1 = new XYLayout();
  JButton bClose = new JButton();
  XYLayout xYLayout2 = new XYLayout();
  Main parent = null;

  public AboutBox(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      add(panel1);
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    this.parent = (Main) frame;
    product = new String("tcmIS - Version "+parent.getCmisApp().getResourceBundle().getString("VERSION"));
    label1.setText(product);
  }

  public AboutBox(Frame frame) {
    this(frame, "", false);
  }

  public AboutBox(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public AboutBox(Frame frame, String title) {
    this(frame, title, false);
  }

  public AboutBox(Main frame, String title) {
    this((Frame)frame, title, false);
  }

  private void jbInit() throws Exception{
    this.setResizable(false);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    panel3.setLayout(xYLayout1);
    panel1.setSize(new Dimension(528, 387));
    panel2.setLayout(xYLayout2);
    imageControl1.setText("");
    imageControl1.setIcon(ResourceLoader.getImageIcon("images/gif/haastcm.gif"));
    label1.setText(product);
    label2.setText(copyright);
    label3.setText(comments);
    bClose.setText("Close");

    xYLayout2.setWidth(414);
    xYLayout2.setHeight(280);   //189

    bClose.setRolloverEnabled(true);
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif","Ok");
    bClose.setIcon(ss);
    bClose.addActionListener(new AboutBox_bClose_actionAdapter(this));

    panel1.setLayout(borderLayout1);
    panel1.add(panel3, BorderLayout.CENTER);
    panel3.add(label2, new XYConstraints(150, 295, -1, -1));
    panel3.add(label3, new XYConstraints(122, 317, -1, -1));
    panel3.add(panel2, new XYConstraints(39, 10, 460, 280));
    panel2.add(imageControl1, new XYConstraints(0, 0, 414, 280));
    panel1.add(panel4, BorderLayout.SOUTH);
    panel4.add(bClose, null);
    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }


  void bClose_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
    dispose();
  }

  void this_windowClosing(WindowEvent e) {
    this.setVisible(false);
    dispose();
  }

}


class AboutBox_bClose_actionAdapter implements java.awt.event.ActionListener {
  AboutBox adaptee;

  AboutBox_bClose_actionAdapter(AboutBox adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bClose_actionPerformed(e);
  }
}
