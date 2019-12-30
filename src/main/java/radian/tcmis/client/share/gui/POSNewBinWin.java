package radian.tcmis.client.share.gui;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class POSNewBinWin extends JDialog {
  Frame parent;
  CmisApp grandParent;
  JPanel panel1 = new JPanel();
  JButton cancelB = new JButton();
  JButton okB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  String inventoryGroup = "";
  StaticJLabel hubL = new StaticJLabel();
  DataJLabel hubT = new DataJLabel();
  StaticJLabel binL = new StaticJLabel();
  JTextField binT = new JTextField();
  boolean doneFlag = false;

  public POSNewBinWin(Frame frame, String title, boolean modal, String inventoryGroup) {
    super(frame, title, modal);
    parent = frame;
    this.inventoryGroup = inventoryGroup;
    try {
      jbInit();
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setGrandParent(CmisApp m){
    grandParent = m;
  }

  private void jbInit() throws Exception{
    panel1.setMaximumSize(new Dimension(300, 200));
    panel1.setPreferredSize(new Dimension(300, 200));
    panel1.setMinimumSize(new Dimension(300, 200));
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    cancelB.setMaximumSize(new Dimension(143, 25));
    cancelB.setMinimumSize(new Dimension(143, 25));
    cancelB.setPreferredSize(new Dimension(143, 25));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.setMnemonic('0');

    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    okB.setMaximumSize(new Dimension(100, 25));
    okB.setMinimumSize(new Dimension(100, 25));
    okB.setPreferredSize(new Dimension(100, 25));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));

    panel1.setLayout(gridBagLayout1);
    hubL.setText("Hub: ");
    binL.setText("Bin: ");
    binT.setMaximumSize(new Dimension(143, 25));
    binT.setMinimumSize(new Dimension(143, 25));
    binT.setPreferredSize(new Dimension(143, 25));

    panel1.add(hubL, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(30, 50, 10, 5), 0, 0));
    panel1.add(hubT, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(30, 70, 10, 5), 0, 0));
    panel1.add(binL, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 50, 20, 5), 0, 0));
    panel1.add(binT, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 70, 20, 5), 0, 0));
    panel1.add(okB, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 30, 9, 0), 0, 0));
    panel1.add(cancelB, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 150, 9, 5), 0, 0));
    this.getContentPane().add(panel1);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  } //end of method

  boolean createNewBin() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","CREATE_NEW_POS_BIN"); //String, String
      query.put("INVENTORY_GROUP",inventoryGroup);
      query.put("BIN",binT.getText());
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        doneFlag = false;
        return false;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        doneFlag = false;
        return false;
      }
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  } //end of method

  void addBinB_actionPerformed(ActionEvent e) {

  } //end of method

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    doneFlag = false;
    this.setVisible(false);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (BothHelpObjs.isBlankString(binT.getText())) {
      doneFlag = false;
      GenericDlg.showMessage("Please enter a Bin.");
      return;
    }
    if (!createNewBin()) {
      doneFlag = false;
      return;
    }
    doneFlag = true;
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    doneFlag = false;
    setVisible(false);
  } //end of method

} //end of class




