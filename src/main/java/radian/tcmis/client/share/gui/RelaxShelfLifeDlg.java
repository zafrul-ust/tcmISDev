

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class RelaxShelfLifeDlg extends JDialog {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  StaticJLabel duhT1 = new StaticJLabel();
  StaticJLabel duhT3 = new StaticJLabel();
  JLabel duhT0 = new JLabel();

  Hashtable lineItemH;
  CmisApp cmis;
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JRadioButton stockRB = new JRadioButton();
  JRadioButton newRB = new JRadioButton();
  String proceedFlag = "cancel";


  public RelaxShelfLifeDlg(Frame frame,Hashtable h, CmisApp c) {
    super(frame, "Material On Hand...", true);
    try  {
      cmis = c;
      lineItemH = h;
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    this.setResizable(false);
    panel1.setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout2);
    panel1.setMaximumSize(new Dimension(350, 175));
    panel1.setPreferredSize(new Dimension(350, 175));
    panel1.setMinimumSize(new Dimension(350, 175));
    duhT1.setFont(new java.awt.Font("Dialog", 0, 12));
    duhT1.setText("There is stock in the hub with at least");
    duhT3.setFont(new java.awt.Font("Dialog", 0, 12));
    duhT3.setText((Integer)lineItemH.get("REMAINING_SHELF_LIFE")+" days shelf life.");
    okB.setMaximumSize(new Dimension(130, 25));
    okB.setMinimumSize(new Dimension(130, 25));
    okB.setPreferredSize(new Dimension(130, 25));
    okB.setText("Continue");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));

    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    stockRB.setText("Send in next delivery from available stock");
    stockRB.setMnemonic('0');
    stockRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        stockRB_actionPerformed(e);
      }
    });
    newRB.setText("Don't send from stock - route to purchasing");
    newRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newRB_actionPerformed(e);
      }
    });
    cancelB.setMaximumSize(new Dimension(110, 25));
    cancelB.setMinimumSize(new Dimension(110, 25));
    cancelB.setPreferredSize(new Dimension(110, 25));
    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));

    duhT0.setFont(new java.awt.Font("Dialog", 1, 12));
    duhT0.setText("Line "+(String)lineItemH.get("LINE_NUM")+": ");
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(duhT0, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 5), 0, 0));
    jPanel1.add(duhT1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 20, 5, 5), 0, 0));
    jPanel1.add(duhT3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel1.add(stockRB, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 20, 5, 5), 0, 0));
    jPanel1.add(newRB, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel1.add(okB, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 40, 5, 5), 0, 0));
    jPanel1.add(cancelB, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 10, 5, 40), 0, 0));
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    if ("Y".equalsIgnoreCase((String)lineItemH.get("SHELF_LIFE_CONFIRMED"))) {
      Boolean tmpB = (Boolean)lineItemH.get("RELAX_SHELF_LIFE");
      if (tmpB.booleanValue()) {
        stockRB.setSelected(true);
        newRB.setSelected(false);
      }else {
        newRB.setSelected(true);
        stockRB.setSelected(false);
      }
    } //end of if shelf life confirmed by user
  } //end of method

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (!stockRB.isSelected() && !newRB.isSelected()) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please choose one of the option above before proceed.");
      gd.setVisible(true);
      return;
    }
    //SAVE DATA HERE
    if (stockRB.isSelected()) {
      lineItemH.put("RELAX_SHELF_LIFE",new Boolean(true));
    }else {
      lineItemH.put("RELAX_SHELF_LIFE",new Boolean(false));
    }
    proceedFlag = "ok";
    lineItemH.put("SHELF_LIFE_CONFIRMED","Y");
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    proceedFlag = "cancel";
    lineItemH.put("SHELF_LIFE_CONFIRMED","N");
    this.setVisible(false);
  }

  public String getProceedFlag() {
    return proceedFlag;
  }

  void stockRB_actionPerformed(ActionEvent e) {
    if (stockRB.isSelected()) {
      stockRB.setSelected(true);
      newRB.setSelected(false);
    }else {
      stockRB.setSelected(false);
      newRB.setSelected(false);
    }
  }

  void newRB_actionPerformed(ActionEvent e) {
    if (newRB.isSelected()) {
      newRB.setSelected(true);
      stockRB.setSelected(false);
    }else {
      newRB.setSelected(false);
      stockRB.setSelected(false);
    }
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    proceedFlag = "cancel";
    lineItemH.put("SHELF_LIFE_CONFIRMED","N");
    this.setVisible(false);
  }

} //end of class
