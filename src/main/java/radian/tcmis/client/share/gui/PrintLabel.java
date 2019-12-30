package radian.tcmis.client.share.gui;

import javax.swing.*;
import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;
import java.awt.event.*;

public class PrintLabel extends JDialog{

  public PrintLabel() {loadIt();}

  String fac = "";
  String prof = "";
  String vend = "";
  String desc = "";
  String dot = "";
  String wa = "";
  String mo = "";

  private boolean invokedStandalone = false;
  JFrame f = new JFrame("Raytheon Waste Chemical Traveler");
  JPanel jPanel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel jLabel1 = new JLabel();
  JButton jButton2 = new JButton();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  TitledBorder titledBorder1;
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JComboBox jComboBox2 = new JComboBox();
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
  FlowLayout flowLayout3 = new FlowLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout7 = new XYLayout();
  Border border1;
  JTextPane jTextPane1 = new JTextPane();
  JTextPane jTextPane2 = new JTextPane();
  Border border2;
  TitledBorder titledBorder2;

//Set Methods
public void setFacility(String x){fac=x;}
public void setVendor(String x){vend=x;}
public void setProfile(String x){prof=x;}
public void setDescription(String x){desc=x;}
public void setDot(String x){dot=x;}
public void setWorkArea(String x){wa=x;}
public void setManagementOption(String x){mo=x;}

//Get Methods
public String getFacilty(){return fac;}
public String getVendor(){return vend;}
public String getProfile(){return prof;}
public String getDescription(){return desc;}
public String getDot(){return dot;}
public String getManagementOption(){return mo;}

  private void jbInit() throws Exception {
    border2 = BorderFactory.createEtchedBorder(Color.white,new java.awt.Color(134, 134, 134));
    titledBorder2 = new TitledBorder(border2,"Verify the information below, then click 'Create Label'.",1,1,new java.awt.Font("Dialog", 1, 11),new java.awt.Color(0, 0, 0));
    f.setSize(535,325);
    f.setContentPane(jPanel1);
    f.setEnabled(true);
    jPanel1.setBorder(titledBorder2);
    jPanel1.setMinimumSize(new Dimension(543, 325));
    jPanel1.setPreferredSize(new Dimension(543, 325));
    jPanel1.setLayout(xYLayout1);
    jLabel1.setText("Management Option");
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setForeground(new java.awt.Color(0, 0, 0));
    jLabel3.setText("Vendor");
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel3.setForeground(new java.awt.Color(0, 0, 0));
    jButton2.setText("Create Label");
    jButton2.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jButton2_mouseClicked(e);
      }
    });
    xYLayout1.setHeight(325);
    xYLayout1.setWidth(535);
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

    jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jComboBox2_mouseClicked(e);
      }
    });
    jComboBox2.setFont(new java.awt.Font("Dialog", 0, 10));
    jComboBox2.setAutoscrolls(true);
    jComboBox2.setToolTipText("");
    jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel7.setForeground(new java.awt.Color(52, 52, 153));
    jLabel7.setText(fac);
    jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel8.setForeground(new java.awt.Color(52, 52, 153));
    jLabel8.setText(prof);
    jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel9.setForeground(new java.awt.Color(52, 52, 153));
    jLabel9.setText(vend);
    jPanel2.setLayout(xYLayout3);
    jPanel3.setBorder(BorderFactory.createEtchedBorder());
    jPanel3.setMinimumSize(new Dimension(30, 31));
    jPanel3.setPreferredSize(new Dimension(200, 38));
    jPanel3.setLayout(xYLayout4);
    jPanel4.setBorder(BorderFactory.createEtchedBorder());
    jPanel4.setPreferredSize(new Dimension(230, 38));
    jLabel12.setBackground(Color.black);
    jLabel12.setFont(new java.awt.Font("SansSerif", 1, 16));
    jLabel12.setForeground(Color.red);
    jLabel12.setText("Raytheon");
    jPanel5.setBackground(Color.black);
    jPanel5.setPreferredSize(new Dimension(200, 38));
    jPanel2.setPreferredSize(new Dimension(500, 48));
    jPanel8.setBorder(BorderFactory.createEtchedBorder());
    jPanel8.setMinimumSize(new Dimension(59, 31));
    jPanel8.setLayout(xYLayout5);
    jPanel7.setBorder(BorderFactory.createEtchedBorder());
    jPanel7.setLayout(xYLayout2);
    jPanel9.setBorder(BorderFactory.createEtchedBorder());
    jPanel9.setLayout(xYLayout6);
    jPanel10.setBorder(BorderFactory.createEtchedBorder());
    jPanel10.setLayout(xYLayout7);
    jPanel11.setLayout(flowLayout3);
    jTextPane1.setDisabledTextColor(new java.awt.Color(52, 52, 155));
    jTextPane1.setBackground(new java.awt.Color(200, 200, 200));
    jTextPane1.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextPane1.setEnabled(true);
    jTextPane1.setSelectionColor(Color.gray);
    jTextPane1.setForeground(new java.awt.Color(52, 52, 155));
    jTextPane1.setSelectedTextColor(Color.yellow);
    jTextPane1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jTextPane2.setDisabledTextColor(new java.awt.Color(52, 52, 155));
    jTextPane2.setBackground(new java.awt.Color(200, 200, 200));
    jTextPane2.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextPane2.setEnabled(true);
    jTextPane2.setSelectionColor(Color.gray);
    jTextPane2.setForeground(new java.awt.Color(52, 52, 155));
    jTextPane2.setSelectedTextColor(Color.yellow);
    jTextPane2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jTextPane1.setText(desc);
    jTextPane2.setText(mo);
    jPanel1.add(jPanel11, new XYConstraints(91, 254, 295, -1));
    jPanel11.add(jButton2, null);
    jPanel1.add(jPanel2, new XYConstraints(12, 3, 502, 252));
    jPanel2.add(jPanel3, new XYConstraints(7, 5, 163, -1));
    jPanel3.add(jLabel7, new XYConstraints(2, 17, 126, 13));
    jPanel3.add(jLabel4, new XYConstraints(1, 3, -1, -1));
    jPanel2.add(jPanel4, new XYConstraints(166, 5, 204, -1));
    jPanel4.add(jLabel2, null);
    jPanel4.add(jComboBox2, null);
    jPanel2.add(jPanel7, new XYConstraints(7, 37, 198, 36));
    jPanel7.add(jLabel6, new XYConstraints(2, 5, -1, -1));
    jPanel7.add(jLabel8, new XYConstraints(36, 9, 154, 17));
    jPanel2.add(jPanel8, new XYConstraints(201, 41, 169, 32));
    jPanel8.add(jLabel3, new XYConstraints(5, 1, -1, -1));
    jPanel8.add(jLabel9, new XYConstraints(47, 2, 116, 20));
    jPanel2.add(jPanel10, new XYConstraints(7, 71, 431, 107));
    jPanel10.add(jLabel5, new XYConstraints(179, 5, -1, -1));
    jPanel10.add(jTextPane1, new XYConstraints(6, 23, 414, 75));
    jPanel2.add(jPanel9, new XYConstraints(7, 175, 431, 73));
    jPanel9.add(jLabel1, new XYConstraints(155, 5, -1, -1));
    jPanel9.add(jTextPane2, new XYConstraints(7, 24, 412, 42));
    jPanel2.add(jPanel5, new XYConstraints(379, 21, 125, 36));
    jPanel5.add(jLabel12, null);

    jComboBox2.setBackground(Color.lightGray);
  }

  public void loadIt(){
    fac = this.getFacilty();
    prof = this.getProfile();
    vend = this.getVendor();
    mo = this.getManagementOption();
    desc = this.getDescription();
    dot = this.getDot();
    try  {
    jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    CenterComponent.centerCompOnScreen(f);
    f.pack();
    f.setVisible(true);
  }

  void jComboBox2_mouseClicked(MouseEvent e) {

  }

  void jComboBox1_mouseClicked(MouseEvent e) {

  }

  void jButton2_mouseClicked(MouseEvent e) {

    dispose();
  }

  public static void main(String[] args) {
    PrintLabel vPrintLabel = new PrintLabel();
    vPrintLabel.invokedStandalone = true;
  }

}
