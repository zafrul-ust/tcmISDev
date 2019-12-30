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
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class POSAddBinWin extends JDialog {
  Frame parent;
  CmisApp grandParent;
  JPanel panel1 = new JPanel();
  JButton cancelB = new JButton();
  JButton okB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  String inventoryGroup = "";
  StaticJLabel binL = new StaticJLabel();
  JComboBox binC = new JComboBox();
  JButton addBinB = new JButton();
  boolean doneFlag = false;

  public POSAddBinWin(Frame frame, String title, boolean modal, String inventoryGroup) {
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

    addBinB.setMaximumSize(new Dimension(23, 23));
    addBinB.setMinimumSize(new Dimension(23, 23));
    addBinB.setPreferredSize(new Dimension(23, 23));
    addBinB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));
    addBinB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addBinB_actionPerformed(e);
      }
    });

    panel1.setLayout(gridBagLayout1);
    binL.setText("Bin: ");
    binC.setMaximumSize(new Dimension(143, 23));
    binC.setMinimumSize(new Dimension(143, 23));
    binC.setPreferredSize(new Dimension(143, 23));

    panel1.add(binC, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 50, 100, 5), 0, 0));
    panel1.add(addBinB, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 210, 100, 5), 0, 0));
    panel1.add(okB, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 30, 9, 0), 0, 0));
    panel1.add(cancelB, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 150, 9, 5), 0, 0));
    this.getContentPane().add(panel1);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  } //end of method

  void loadIt() {
    POSAddBinWinLoadThread iT = new POSAddBinWinLoadThread(this);
    iT.start();
  }

  void loadItAction() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_POS_BIN_DATA"); //String, String
      query.put("INVENTORY_GROUP",inventoryGroup);
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        doneFlag = false;
        return;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        doneFlag = false;
        return;
      }
      Vector v = (Vector)result.get("BIN");
      if (v.size() == 0) {
        GenericDlg.showMessage("No record found.");
        doneFlag = false;
        return;
      }
      binC.removeAllItems();
      binC = ClientHelpObjs.loadChoiceFromVector(binC,v);
      DataJLabel tmpT = new DataJLabel();
      tmpT.setText("TEST");
      binC.setForeground(tmpT.getForeground());
      binC.setFont(tmpT.getFont());
    }catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  void addBinB_actionPerformed(ActionEvent e) {
    POSNewBinWin pnbw = new POSNewBinWin(grandParent.getMain(),"Create new Bin for: "+inventoryGroup,true,inventoryGroup);
    pnbw.setGrandParent(grandParent);
    CenterComponent.centerCompOnScreen(pnbw);
    pnbw.setVisible(true);
    if (pnbw.doneFlag) {
      String newBin = pnbw.binT.getText();
      binC.insertItemAt(newBin,binC.getItemCount());
      binC.setSelectedItem(newBin);
      DataJLabel tmpT = new DataJLabel();
      tmpT.setText("TEST");
      binC.setForeground(tmpT.getForeground());
      binC.setFont(tmpT.getFont());
    }
  } //end of method

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    doneFlag = false;
    this.setVisible(false);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (binC.getSelectedItem().toString().startsWith("Select")) {
      GenericDlg.showMessage("Please select a bin.");
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

class POSAddBinWinLoadThread extends Thread {
 POSAddBinWin parent;
  public POSAddBinWinLoadThread(POSAddBinWin parent){
     super("POSAddBinWinLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}



