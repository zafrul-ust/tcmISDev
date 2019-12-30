
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
public class LoadingPanel extends JPanel {
  JPanel bevelPanel1 = new JPanel();
  JPanel bevelPanel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JProgressBar jProgressBar1 = new JProgressBar();
  XYLayout xYLayout1 = new XYLayout();
  BorderLayout borderLayout3 = new BorderLayout();

  // erase water LensAnfyWrapper lPanel = null;
  XYLayout xYLayout2 = new XYLayout();
  JButton bCancel = new JButton();
  JLabel name = new JLabel();
  JLabel ver = new JLabel();
  JLabel pac = new JLabel();
  JLabel cent = new JLabel();
  JLabel bytes = new JLabel();
  JButton jButton1 = new JButton();

  //String wDir = new String(System.getProperty("user.dir"));
  String imagesDir = null;

  public LoadingPanel() {
    try  {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void myInit(){
    /*wDir = new String(System.getProperty("user.dir"));
    imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
  }

  void jbInit() throws Exception {
    myInit();
    this.setLayout(xYLayout2);
    bevelPanel2.setLayout(borderLayout2);
    jPanel1.setLayout(xYLayout1);
    bevelPanel1.setLayout(borderLayout3);

    xYLayout2.setWidth(278);
    xYLayout2.setHeight(344);
    bCancel.setText("Cancel");
    name.setText("name");
    ver.setText("Version: 0.00.00");
    pac.setText("Package: 99 of 99");
    cent.setText("99%");
    bytes.setText("1000 bytes of 1000 ");
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    bCancel.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));

    jButton1.setIcon(ResourceLoader.getImageIcon("images/gif/tcmissquare.gif"));
    this.setBackground(Color.lightGray);
    // erase water lPanel.setBackground(Color.lightGray);
    // erase water bevelPanel1.add(lPanel,BorderLayout.CENTER);

    jPanel1.setPreferredSize(new Dimension(276, 80));
    jPanel1.setMinimumSize(new Dimension(50, 50));

    jPanel1.add(name, new XYConstraints(5, 34, 260, -1));
    jPanel1.add(ver, new XYConstraints(5, 2, 117, -1));
    jPanel1.add(pac, new XYConstraints(159, 2, 113, -1));
    jPanel1.add(bytes, new XYConstraints(6, 65, 260, -1));
    bevelPanel2.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jProgressBar1, new XYConstraints(5, 100, 206, -1));
    jPanel1.add(cent, new XYConstraints(217, 100, 53, -1));
    this.add(jButton1, new XYConstraints(2, 2, 274, 164));
    this.add(bevelPanel1, new XYConstraints(107, 302, -1, -1));

    this.add(bCancel, new XYConstraints(85, 304, 109, 37));
    this.add(bevelPanel2, new XYConstraints(0, 167, -1, 130));

    this.setSize(new Dimension(278,298));

    //Colors
    ClientHelpObjs.makeDefaultColors(this);
  }

  public void setMessage(String ver, String pac,String name){
    this.ver.setText(ver);
    this.name.setText(name);
    this.pac.setText(pac);
    this.revalidate();
  }

  public void defineBar(long oSize){
    jProgressBar1.setMaximum((int)oSize);
    jProgressBar1.setMinimum(0);
    jProgressBar1.setValue(0);
    jProgressBar1.revalidate();
    jProgressBar1.repaint();
    this.revalidate();
  }

  public void setBarMax(int oSize){
    jProgressBar1.setMaximum(oSize);
  }

  public void setBarMin(int oSize){
    jProgressBar1.setMinimum(oSize);
  }

  public void updateProcessBar(int going,  String cent, String bytes){
    jProgressBar1.setValue(going);
    this.cent.setText(cent);
    this.bytes.setText(bytes);
    jProgressBar1.revalidate();
    this.revalidate();
  }

  void bCancel_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    System.exit(0);
  }
}

















