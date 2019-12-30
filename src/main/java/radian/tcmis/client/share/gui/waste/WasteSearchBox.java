package radian.tcmis.client.share.gui.waste;

import javax.swing.*;
import java.awt.*;

import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.*;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;

public class WasteSearchBox extends JDialog{

  String container_id = "";
  String traveler_id = "";

  CmisApp cmis;

  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();

public WasteSearchBox(CmisApp cmis) {this.cmis = cmis;}

  private void jbInit() throws Exception{
    xYLayout1.setWidth(306);
    xYLayout1.setHeight(124);
    jButton1.setFont(new java.awt.Font("SansSerif", 1, 10));
    jButton1.setMaximumSize(new Dimension(53, 24));
    jButton1.setMinimumSize(new Dimension(53, 24));
    jButton1.setPreferredSize(new Dimension(53, 24));
    jButton1.setText("Search");
    jButton1.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jButton1_mouseClicked(e);
      }
    });

    panel1.setLayout(xYLayout1);
    panel1.setBorder(BorderFactory.createEtchedBorder());
    panel1.setMaximumSize(new Dimension(306, 124));
    panel1.setMinimumSize(new Dimension(306, 124));
    panel1.setPreferredSize(new Dimension(306, 124));
    jButton2.setText("Cancel");
    jButton2.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jButton2_mouseClicked(e);
      }
    });
    jButton2.setPreferredSize(new Dimension(53, 24));
    jButton2.setMinimumSize(new Dimension(53, 24));
    jButton2.setFont(new java.awt.Font("SansSerif", 1, 10));
    jButton2.setMaximumSize(new Dimension(53, 24));
    jTextField1.setBackground(Color.gray);
    jTextField1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jTextField1.setForeground(Color.yellow);
    jTextField2.setForeground(Color.yellow);
    jTextField2.setBackground(Color.gray);
    jTextField2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setForeground(new java.awt.Color(52, 52, 150));
    jLabel1.setText("Container ID:");
    jLabel2.setText("Traveler ID:");
    jLabel2.setForeground(new java.awt.Color(52, 52, 150));
    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 22));
    jLabel3.setForeground(Color.red);
    jLabel3.setBorder(BorderFactory.createLineBorder(Color.black));
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel3.setText("Search");
    panel1.add(jButton1, new XYConstraints(57, 95, 75, 19));
    panel1.add(jTextField2, new XYConstraints(104, 70, 189, 18));
    panel1.add(jButton2, new XYConstraints(174, 95, 75, 19));
    panel1.add(jTextField1, new XYConstraints(104, 46, 189, 18));
    panel1.add(jLabel2, new XYConstraints(16, 70, 85, -1));
    panel1.add(jLabel1, new XYConstraints(15, 47, 85, -1));
    panel1.add(jLabel3, new XYConstraints(3, 4, 295, 34));
    jButton1.requestFocus();

    getContentPane().add(panel1);

    CenterComponent.centerCompOnScreen(this);
    jButton2.requestFocus();
  }

  void jButton1_mouseClicked(MouseEvent e) {
     PrintLabel pl = new PrintLabel(cmis);
     Vector v = new Vector();

     String test1 = jTextField1.getText().toString();
     String test2 = jTextField2.getText().toString();

     BothHelpObjs.makeBlankFromNull(test1);
     BothHelpObjs.makeBlankFromNull(test2);

    if (!test1.equalsIgnoreCase("")){
     v.addElement(test1);
   }else if ((!test2.equalsIgnoreCase("")) && (test1.equalsIgnoreCase(""))){
     v.addElement("traveler");
     v.addElement(test2);
    }
     pl.setContainerId(v);
     pl.loadIt();
     this.dispose();
  }

  void jButton2_mouseClicked(MouseEvent e) {
    this.dispose();
  }

  public void loadIt(){
    try {
      jbInit();
      this.setVisible(true);
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}

