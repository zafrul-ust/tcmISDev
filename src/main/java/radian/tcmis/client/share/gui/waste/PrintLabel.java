package radian.tcmis.client.share.gui.waste;

import javax.swing.*;
import java.awt.*;

//import java.awt.print.*;
//import java.awt.geom.*;
//import java.awt.font.*;
//import java.awt.Graphics;
//import java.awt.PrintGraphics;
//import java.awt.PrintJob;
//import java.awt.Font;
//import java.awt.GridLayout;
//import java.awt.GraphicsEnvironment;
//import java.awt.Color;
//import java.awt.Dimension;

import radian.tcmis.client.share.httpCgi.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class PrintLabel extends JDialog{

  CmisApp cmis;

  String container_id = "";
  String fac = "";
  String trav = "";
  String prof = "";
  String vend = "";
  String desc = "";
  String dot = "";
  String wa = "";
  String mo = "";
  String total = null;

  Vector v = null;
  Vector containerId = null;

  int nav = 0;
  int c = 1;
  int n = 0;

  boolean navigation = false;
  boolean first = false;
  boolean previous = false;
  boolean next = false;
  boolean last = false;

  public static final int FIRST = 1;
  public static final int PREVIOUS = 2;
  public static final int NEXT = 3;
  public static final int LAST = 4;

  public PrintLabel(CmisApp cmis) {this.cmis = cmis;}

  public void setContainerId(Vector v){containerId = v;};

  private boolean invokedStandalone = false;
  JFrame f = new JFrame("Raytheon Waste Chemical Traveler");
  JPanel jPanel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel5 = new JPanel();
  JLabel jLabel12 = new JLabel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel8 = new JPanel();
  JPanel jPanel9 = new JPanel();
  JPanel jPanel10 = new JPanel();
  JPanel jPanel11 = new JPanel();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout7 = new XYLayout();
  JTextPane jTextPane1 = new JTextPane();
  JTextPane jTextPane2 = new JTextPane();
  JPanel jPanel12 = new JPanel();
  JLabel jLabel10 = new JLabel();
  XYLayout xYLayout8 = new XYLayout();
  JButton jButton1 = new JButton();
  XYLayout xYLayout9 = new XYLayout();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  JLabel jLabel11 = new JLabel();
  JLabel jLabel13 = new JLabel();
  JLabel jLabel14 = new JLabel();
  JLabel jLabel15 = new JLabel();
  JLabel jLabel16 = new JLabel();
  JLabel jLabel17 = new JLabel();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel13 = new JPanel();
  JLabel jLabel18 = new JLabel();
  XYLayout xYLayout10 = new XYLayout();
  JLabel jLabel19 = new JLabel();
  XYLayout xYLayout11 = new XYLayout();
  JPanel jPanel15 = new JPanel();
  XYLayout xYLayout12 = new XYLayout();
  XYLayout xYLayout13 = new XYLayout();
  JPanel jPanel14 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel jPanel16 = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  XYLayout xYLayout14 = new XYLayout();
  JLabel jLabel20 = new JLabel();
  JTextPane jTextPane3 = new JTextPane();
  JLabel jLabel21 = new JLabel();
  JLabel jLabel22 = new JLabel();
  JLabel jLabel23 = new JLabel();

  /*String wDir = new String(System.getProperty("user.dir"));
  String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") + (new String("/button/") + (new String("/navigation/")));
*/
  private void jbInit(Integer current) throws Exception {
    f.setSize(443,570);
    f.setContentPane(jPanel1);
    f.setEnabled(true);
    f.setResizable(false);
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel1.setMinimumSize(new Dimension(443, 570));
    jPanel1.setPreferredSize(new Dimension(443, 570));
    jPanel1.setLayout(xYLayout1);
    jLabel1.setText("Management Option");
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setForeground(new java.awt.Color(0, 0, 0));
    jLabel3.setText("Vendor");
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel3.setForeground(new java.awt.Color(0, 0, 0));
    xYLayout1.setHeight(576);
    xYLayout1.setWidth(430);
    jLabel2.setText("Work Area");
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel2.setForeground(new java.awt.Color(0, 0, 0));
    jLabel4.setText("Facility");
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel4.setForeground(new java.awt.Color(0, 0, 0));
    jLabel5.setText("Description");
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel5.setForeground(new java.awt.Color(0, 0, 0));
    jLabel6.setText("Profile");
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel6.setForeground(new java.awt.Color(0, 0, 0));

    jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel7.setForeground(new java.awt.Color(52, 52, 153));
    jLabel7.setText(fac);
    jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel8.setForeground(new java.awt.Color(52, 52, 153));
    jLabel8.setText(prof);
    jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel9.setForeground(Color.black);
    jLabel9.setText(vend);
    jPanel2.setLayout(xYLayout3);
    jPanel3.setBackground(Color.white);
    jPanel3.setBorder(BorderFactory.createEtchedBorder());
    jPanel3.setMinimumSize(new Dimension(30, 31));
    jPanel3.setPreferredSize(new Dimension(200, 38));
    jPanel3.setLayout(xYLayout4);
    jPanel4.setBackground(Color.white);
    jPanel4.setBorder(BorderFactory.createEtchedBorder());
    jPanel4.setPreferredSize(new Dimension(230, 38));
    jPanel4.setLayout(xYLayout14);
    jLabel12.setBackground(Color.black);
    jLabel12.setFont(new java.awt.Font("SansSerif", 1, 16));
    jLabel12.setForeground(Color.red);
    jLabel12.setText("Raytheon");
    jPanel5.setBackground(Color.black);
    jPanel5.setPreferredSize(new Dimension(200, 38));
    jPanel5.setLayout(xYLayout13);
    jPanel2.setBorder(BorderFactory.createLineBorder(Color.black));
    jPanel2.setPreferredSize(new Dimension(500, 48));
    jPanel8.setBackground(Color.white);
    jPanel8.setBorder(BorderFactory.createEtchedBorder());
    jPanel8.setMinimumSize(new Dimension(59, 31));
    jPanel8.setLayout(xYLayout5);
    jPanel7.setBackground(Color.white);
    jPanel7.setBorder(BorderFactory.createEtchedBorder());
    jPanel7.setLayout(xYLayout2);
    jPanel9.setBackground(Color.white);
    jPanel9.setBorder(BorderFactory.createEtchedBorder());
    jPanel9.setLayout(xYLayout6);
    jPanel10.setBackground(Color.white);
    jPanel10.setBorder(BorderFactory.createEtchedBorder());
    jPanel10.setLayout(xYLayout7);
    jPanel11.setLayout(xYLayout9);
    jTextPane1.setDisabledTextColor(new java.awt.Color(52, 52, 155));
    jTextPane1.setBackground(new java.awt.Color(200, 200, 200));
    jTextPane1.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextPane1.setEnabled(true);
    jTextPane1.setSelectionColor(Color.gray);
    jTextPane1.setForeground(new java.awt.Color(52, 52, 155));
    jTextPane1.setEditable(false);
    jTextPane1.setSelectedTextColor(Color.yellow);
    jTextPane1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jTextPane2.setDisabledTextColor(new java.awt.Color(52, 52, 155));
    jTextPane2.setBackground(new java.awt.Color(200, 200, 200));
    jTextPane2.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextPane2.setEnabled(true);
    jTextPane2.setSelectionColor(Color.gray);
    jTextPane2.setForeground(new java.awt.Color(52, 52, 155));
    jTextPane2.setEditable(false);
    jTextPane2.setSelectedTextColor(Color.yellow);
    jTextPane2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jTextPane1.setText(desc);
    jTextPane2.setText(mo);
    jTextPane3.setText(dot);
    jTextPane3.setForeground(new java.awt.Color(52, 52, 155));
    jTextPane3.setEditable(false);
    jTextPane3.setSelectedTextColor(Color.yellow);
    jTextPane3.setFont(new java.awt.Font("SansSerif", 1, 12));
    jPanel11.setBorder(BorderFactory.createLineBorder(Color.black));
    jPanel12.setLayout(xYLayout8);
    jPanel12.setBackground(Color.white);
    jPanel12.setBorder(BorderFactory.createEtchedBorder());
    jLabel10.setText("DOT");
    jLabel10.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel10.setForeground(new java.awt.Color(0, 0, 0));


    ImageIcon ss1 = null;
//    ImageIcon ss1 = new ImageIcon(imagesDir + "/button/navigation/first1.gif");
if (navigation){
    ss1 = ResourceLoader.getImageIcon("images/button/first1.gif");
    jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        jButton2_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        jButton2_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        jButton2_mouseClicked(e);
      }
    });
}else{
    ss1 = ResourceLoader.getImageIcon("images/button/first3.gif");
}
    jButton2.setBorder(null);
    jButton2.setMaximumSize(new Dimension(35, 35));
    jButton2.setMinimumSize(new Dimension(35, 35));
    jButton2.setPreferredSize(new Dimension(35, 35));
    jButton2.setIcon(ss1);


    /*String wDir2 = new String(System.getProperty("user.dir"));
    String imagesDir2 = wDir2 + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss2 = null;
if (navigation){
    ss2 = ResourceLoader.getImageIcon("images/button/prev1.gif");
    jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        jButton1_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        jButton1_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        jButton1_mouseClicked(e);
      }
    });
}else{
    ss2 = ResourceLoader.getImageIcon("images/button/prev_no.gif");
}
//    ImageIcon ss2 = new ImageIcon(imagesDir2 + "/button/navigation/prev1.gif");
    jButton1.setBorder(null);
    jButton1.setMaximumSize(new Dimension(35, 35));
    jButton1.setMinimumSize(new Dimension(35, 35));
    jButton1.setPreferredSize(new Dimension(35, 35));
    jButton1.setIcon(ss2);


    /*String wDir3 = new String(System.getProperty("user.dir"));
    String imagesDir3 = wDir3 + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss3 = null;
if (navigation){
    ss3 = ResourceLoader.getImageIcon("images/button/next1.gif");
    jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        jButton3_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        jButton3_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        jButton3_mouseClicked(e);
      }
    });
}else{
    ss3 = ResourceLoader.getImageIcon("images/button/next_no.gif");
}
//    ImageIcon ss3 = new ImageIcon(imagesDir3 + "/button/navigation/next1.gif");
    jButton3.setBorder(null);
    jButton3.setMaximumSize(new Dimension(35, 35));
    jButton3.setMinimumSize(new Dimension(35, 35));
    jButton3.setPreferredSize(new Dimension(35, 35));
    jButton3.setIcon(ss3);


    /*String wDir4 = new String(System.getProperty("user.dir"));
    String imagesDir4 = wDir4 + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss4 = null;
if (navigation){
    ss4 = ResourceLoader.getImageIcon("images/button/last1.gif");
    jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        jButton4_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        jButton4_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        jButton4_mouseClicked(e);
      }
    });
}else{
    ss4 = ResourceLoader.getImageIcon("images/button/last_no.gif");
}
//    ImageIcon ss4 = new ImageIcon(imagesDir4 + "/button/navigation/last1.gif");
    jButton4.setBorder(null);
    jButton4.setMaximumSize(new Dimension(35, 35));
    jButton4.setMinimumSize(new Dimension(35, 35));
    jButton4.setPreferredSize(new Dimension(35, 35));
    jButton4.setIcon(ss4);


    /*String wDir5 = new String(System.getProperty("user.dir"));
    String imagesDir5 = wDir5 + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss5 = ResourceLoader.getImageIcon("images/button/print1.gif");
//    ImageIcon ss5 = new ImageIcon(imagesDir5 + "/button/navigation/print1.gif");
    jButton5.setBorder(null);
    jButton5.setMaximumSize(new Dimension(35, 35));
    jButton5.setMinimumSize(new Dimension(35, 35));
    jButton5.setPreferredSize(new Dimension(35, 35));
    jButton5.setIcon(ss5);
    jButton5.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mousePressed(MouseEvent e) {
        jButton5_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        jButton5_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        jButton5_mouseClicked(e);
      }
    });

    /*String wDir6 = new String(System.getProperty("user.dir"));
    String imagesDir6 = wDir6 + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss6 = ResourceLoader.getImageIcon("images/button/search1.gif");
//    ImageIcon ss6 = new ImageIcon(imagesDir6 + "/button/navigation/search1.gif");
    jButton6.setBorder(null);
    jButton6.setMaximumSize(new Dimension(35, 35));
    jButton6.setMinimumSize(new Dimension(35, 35));
    jButton6.setPreferredSize(new Dimension(35, 35));
    jButton6.setIcon(ss6);
    jButton6.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mousePressed(MouseEvent e) {
        jButton6_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        jButton6_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        jButton6_mouseClicked(e);
      }
    });

    jLabel11.setForeground(Color.blue);
    jLabel11.setText("Displaying");
    jLabel13.setForeground(Color.red);
    jLabel13.setText(current.toString());
    jLabel14.setForeground(Color.blue);
    jLabel14.setText("of");
    jLabel15.setForeground(Color.red);
    jLabel15.setText(total);
    jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel16.setForeground(new java.awt.Color(0, 125, 150));
    jLabel16.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel16.setText("Container ID: " + container_id);
    jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel17.setForeground(new java.awt.Color(0, 125, 150));
    jLabel17.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel17.setText("Traveler ID: " + trav);
    jPanel6.setBackground(Color.white);
    jPanel6.setBorder(BorderFactory.createEtchedBorder());
    jPanel6.setLayout(xYLayout10);
    jPanel13.setBackground(Color.white);
    jPanel13.setBorder(BorderFactory.createEtchedBorder());
    jPanel13.setLayout(xYLayout11);
    jLabel18.setFont(new java.awt.Font("Dialog", 1, 15));
    jLabel18.setForeground(new java.awt.Color(0, 125, 150));
    jLabel18.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel18.setText(trav);
    jLabel19.setFont(new java.awt.Font("Dialog", 1, 20));
    jLabel19.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel9.setForeground(new java.awt.Color(52, 52, 155));
    jLabel19.setText("Raytheon Waste Traveler");
    jPanel15.setBackground(Color.white);
    jPanel15.setBorder(BorderFactory.createEtchedBorder());
    jPanel15.setLayout(xYLayout12);
    jPanel14.setLayout(gridLayout1);
    jPanel14.setBorder(BorderFactory.createEtchedBorder());
    jPanel16.setLayout(gridLayout2);
    jPanel16.setBorder(BorderFactory.createEtchedBorder());
    jLabel20.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel20.setForeground(new java.awt.Color(52, 52, 155));
    jLabel20.setText(wa);
    jTextPane3.setDisabledTextColor(new java.awt.Color(52, 52, 155));
    jTextPane3.setBackground(new java.awt.Color(200, 200, 200));
    jTextPane3.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextPane3.setSelectionColor(Color.gray);
    jLabel21.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel21.setForeground(Color.black);
    jLabel21.setText("Traveler ID");
//========================Barcode Generator===========================//
//    GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
//    Font envfonts[] = gEnv.getAllFonts();

//    jLabel22.setFont(new java.awt.Font("SKANDATA C39", 0, 55));
//========================Barcode Generator===========================//
    jLabel22.setForeground(Color.black);
    jLabel22.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel22.setText(container_id);
    jLabel23.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel23.setForeground(Color.gray);
    jLabel23.setHorizontalAlignment(SwingConstants.CENTER);
//    jLabel23.setText(container_id);
    jPanel1.add(jPanel11, new XYConstraints(6, 5, 417, 65));
    jPanel11.add(jLabel11, new XYConstraints(8, 43, 64, 13));
    jPanel11.add(jLabel13, new XYConstraints(70, 43, 15, 13));
    jPanel11.add(jLabel14, new XYConstraints(86, 43, 15, 13));
    jPanel11.add(jLabel15, new XYConstraints(103, 43, 18, 13));
    jPanel11.add(jLabel17, new XYConstraints(267, 35, 136, -1));
    jPanel11.add(jLabel16, new XYConstraints(267, 13, 126, 18));
    jPanel11.add(jPanel14, new XYConstraints(4, 4, 130, 33));
    jPanel14.add(jButton2, null);
    jPanel14.add(jButton1, null);
    jPanel14.add(jButton3, null);
    jPanel14.add(jButton4, null);
    jPanel11.add(jPanel16, new XYConstraints(179, 4, 66, 31));
    jPanel16.add(jButton5, null);
    jPanel16.add(jButton6, null);
    jPanel1.add(jPanel2, new XYConstraints(27, 79, 374, 485));
    jPanel2.add(jPanel15, new XYConstraints(7, 399, 358, 78));
    jPanel15.add(jLabel22, new XYConstraints(4, 5, 345, 50));
    jPanel15.add(jLabel23, new XYConstraints(82, 60, 182, 15));
    jPanel2.add(jPanel13, new XYConstraints(7, 6, 358, 37));
    jPanel13.add(jLabel19, new XYConstraints(38, 0, 268, -1));
    jPanel2.add(jPanel10, new XYConstraints(7, 137, 358, 84));
    jPanel10.add(jTextPane1, new XYConstraints(6, 23, 343, 49));
    jPanel10.add(jLabel5, new XYConstraints(144, 5, -1, -1));
    jPanel2.add(jPanel3, new XYConstraints(7, 71, 179, -1));
    jPanel3.add(jLabel7, new XYConstraints(2, 17, 126, 13));
    jPanel3.add(jLabel4, new XYConstraints(1, 3, -1, -1));
    jPanel2.add(jPanel4, new XYConstraints(185, 71, 180, -1));
    jPanel4.add(jLabel2, new XYConstraints(5, 1, -1, -1));
    jPanel4.add(jLabel20, new XYConstraints(58, 10, 94, 20));
    jPanel2.add(jPanel7, new XYConstraints(7, 103, 179, 36));
    jPanel7.add(jLabel6, new XYConstraints(2, 5, -1, -1));
    jPanel7.add(jLabel8, new XYConstraints(36, 9, 135, 17));
    jPanel2.add(jPanel8, new XYConstraints(184, 107, 181, 32));
    jPanel8.add(jLabel3, new XYConstraints(5, 1, -1, -1));
    jPanel8.add(jLabel9, new XYConstraints(47, 2, 116, 20));
    jPanel2.add(jPanel6, new XYConstraints(7, 35, 231, 46));
    jPanel6.add(jLabel18, new XYConstraints(57, 8, 165, 23));
    jPanel6.add(jLabel21, new XYConstraints(1, 8, 53, 14));
    jPanel2.add(jPanel5, new XYConstraints(231, 38, 133, 36));
    jPanel5.add(jLabel12, new XYConstraints(29, 9, -1, -1));
    jPanel2.add(jPanel9, new XYConstraints(7, 217, 358, 73));
    jPanel9.add(jTextPane2, new XYConstraints(7, 24, 342, 42));
    jPanel9.add(jLabel1, new XYConstraints(124, 5, -1, -1));
    jPanel2.add(jPanel12, new XYConstraints(7, 285, 358, 118));
    jPanel12.add(jLabel10, new XYConstraints(165, 5, -1, -1));
    jPanel12.add(jTextPane3, new XYConstraints(7, 25, 343, 81));

  }

  void jButton2_mousePressed(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/first2.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/first2.gif");
  jButton2.setIcon(ss);
  }

  void jButton2_mouseReleased(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/first1.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/first1.gif");
  jButton2.setIcon(ss);
  }

  void jButton2_mouseClicked(MouseEvent e) {
  nav = 1;
  labelData();
  }

  void jButton1_mousePressed(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/prev2.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/prev2.gif");
  jButton1.setIcon(ss);
  }

  void jButton1_mouseReleased(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/prev1.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/prev1.gif");
  jButton1.setIcon(ss);
  }

  void jButton1_mouseClicked(MouseEvent e) {
  nav = 2;
  labelData();
  }

  void jButton3_mousePressed(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/next2.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/next2.gif");
  jButton3.setIcon(ss);
  }

  void jButton3_mouseReleased(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/next1.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/next1.gif");
  jButton3.setIcon(ss);
  }

  void jButton3_mouseClicked(MouseEvent e) {
  nav = 3;
  labelData();
  }

  void jButton4_mousePressed(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/last2.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/last2.gif");
  jButton4.setIcon(ss);
  }

  void jButton4_mouseReleased(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/last1.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/last1.gif");
  jButton4.setIcon(ss);
  }

  void jButton4_mouseClicked(MouseEvent e) {
  nav = 4;
  labelData();
  }

  void jButton5_mousePressed(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/print2.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/print2.gif");
  jButton5.setIcon(ss);
  }

  void jButton5_mouseReleased(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/print1.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/print1.gif");
  jButton5.setIcon(ss);
  }

  void jButton5_mouseClicked(MouseEvent e) {
    GetLabelTypeDlg getLabelTypeDlg = new GetLabelTypeDlg(cmis.getMain(),"Select a label");
    getLabelTypeDlg.setParent(cmis);
    CenterComponent.centerCompOnScreen(getLabelTypeDlg);
    getLabelTypeDlg.setVisible(true);
    System.out.println("got here: "+getLabelTypeDlg.okClicked());
    if (getLabelTypeDlg.okClicked()) {
      String labelType = "traveler";
      if (getLabelTypeDlg.hazardousRB.isSelected()) {
        labelType = "hazardous";
      }
      goUrl(labelType);
    }
  }

  void jButton6_mousePressed(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/search2.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/search2.gif");
  jButton6.setIcon(ss);
  }

  void jButton6_mouseReleased(MouseEvent e) {
//ImageIcon ss = ResourceLoader.getImageIcon("images/button//button/navigation/search1.gif");
  ImageIcon ss = ResourceLoader.getImageIcon("images/button/search1.gif");
  jButton6.setIcon(ss);
  }

  void jButton6_mouseClicked(MouseEvent e) {
   WasteSearchBox ws = new WasteSearchBox(cmis);
   ws.loadIt();
   f.dispose();
  }


  public void loadIt(){

  if (nav == 0){

      TcmisHttpConnection cgi = new TcmisHttpConnection("WastePrintLabel",cmis);
      Hashtable query = new Hashtable();
      query.put("CONTAINER_ID",containerId);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

    v = (Vector)result.get("CONTAINER_INFO");
    nav = 1;
  }

    labelData();
    Integer current = new Integer(c);

    try  {
    jbInit(current);
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    CenterComponent.centerCompOnScreen(f);
    f.pack();
    f.setVisible(true);
  }

protected void labelData(){

    if (v.size() == 1){
    Hashtable h = (Hashtable)v.elementAt(0);
    navigation = false;
    total = "1";
    c = 1;
        fac = h.get("FACILITY").toString();
        trav = h.get("TRAVELER_ID").toString();
        prof = h.get("VENDOR_PROFILE_ID").toString();
        vend = h.get("VENDOR_ID").toString();
        desc = h.get("DESCRIPTION").toString();
        dot = h.get("DOT").toString();
        wa = h.get("WORK_AREA").toString();
        mo = h.get("MANAGEMENT_OPTION").toString();
        container_id = h.get("CONTAINER_ID").toString();

    }else{
     Integer t = new Integer(0);
     total = t.toString(v.size());

     if ((c+1)>=v.size()){
          jButton3.setEnabled(false);
          jButton4.setEnabled(false);
     }else{
          jButton3.setEnabled(true);
          jButton4.setEnabled(true);
     }
     if ((n-1)<=0){
          jButton1.setEnabled(false);
          jButton2.setEnabled(false);
     }else{
          jButton1.setEnabled(true);
          jButton2.setEnabled(true);
     }
     navigation = true;
     switch(nav){
      case FIRST:
          c = 1;
          n = 0;
          Hashtable firstH = (Hashtable)v.elementAt(0);
          fac = firstH.get("FACILITY").toString();
          trav = firstH.get("TRAVELER_ID").toString();
          prof = firstH.get("VENDOR_PROFILE_ID").toString();
          vend = firstH.get("VENDOR_ID").toString();
          desc = firstH.get("DESCRIPTION").toString();
          dot = firstH.get("DOT").toString();
          wa = firstH.get("WORK_AREA").toString();
          mo = firstH.get("MANAGEMENT_OPTION").toString();
          container_id = firstH.get("CONTAINER_ID").toString();
          jButton1.setEnabled(false);
          jButton2.setEnabled(false);
          jButton3.setEnabled(true);
          jButton4.setEnabled(true);
      break;

      case PREVIOUS:
          c = (c - 1);
          n = (n - 1);
          Hashtable prevH = (Hashtable)v.elementAt(n);
          fac = prevH.get("FACILITY").toString();
          trav = prevH.get("TRAVELER_ID").toString();
          prof = prevH.get("VENDOR_PROFILE_ID").toString();
          vend = prevH.get("VENDOR_ID").toString();
          desc = prevH.get("DESCRIPTION").toString();
          dot = prevH.get("DOT").toString();
          wa = prevH.get("WORK_AREA").toString();
          mo = prevH.get("MANAGEMENT_OPTION").toString();
          container_id = prevH.get("CONTAINER_ID").toString();
          jButton3.setEnabled(true);
          jButton4.setEnabled(true);
      break;

      case NEXT:
          c = (c + 1);
          n = (n + 1);
          Hashtable nextH = (Hashtable)v.elementAt(n);
          fac = nextH.get("FACILITY").toString();
          trav = nextH.get("TRAVELER_ID").toString();
          prof = nextH.get("VENDOR_PROFILE_ID").toString();
          vend = nextH.get("VENDOR_ID").toString();
          desc = nextH.get("DESCRIPTION").toString();
          dot = nextH.get("DOT").toString();
          wa = nextH.get("WORK_AREA").toString();
          mo = nextH.get("MANAGEMENT_OPTION").toString();
          container_id = nextH.get("CONTAINER_ID").toString();
          jButton1.setEnabled(true);
          jButton2.setEnabled(true);
      break;

      case LAST:
          c = v.size();
          n = (v.size() - 1);
          Hashtable lastH = (Hashtable)v.lastElement();
          fac = lastH.get("FACILITY").toString();
          trav = lastH.get("TRAVELER_ID").toString();
          prof = lastH.get("VENDOR_PROFILE_ID").toString();
          vend = lastH.get("VENDOR_ID").toString();
          desc = lastH.get("DESCRIPTION").toString();
          dot = lastH.get("DOT").toString();
          wa = lastH.get("WORK_AREA").toString();
          mo = lastH.get("MANAGEMENT_OPTION").toString();
          container_id = lastH.get("CONTAINER_ID").toString();
          jButton1.setEnabled(true);
          jButton2.setEnabled(true);
          jButton3.setEnabled(false);
          jButton4.setEnabled(false);
      break;
     }
   }

Integer current = new Integer(c);
    jTextPane1.setText(desc);
    jTextPane2.setText(mo);
    jTextPane3.setText(dot);

    jLabel7.setText(fac);
    jLabel8.setText(prof);
    jLabel9.setText(vend);
    jLabel13.setText(current.toString());
    jLabel16.setText("Container ID: " + container_id);
    jLabel17.setText("Traveler ID: " + trav);
    jLabel18.setText(trav);
    jLabel20.setText(wa);
    jLabel22.setText(container_id);
}
public void goUrl(String labelType) {
    int kind = 0;
      kind = URLGrab.LABEL;
    ClientHelpObjs.goLabelURL(cmis,this.cmis.getResourceBundle().getString("APP_NAME"),containerId,kind,labelType);
  }
}

