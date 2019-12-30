package radian.tcmis.client.share.gui.nchem;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1999
 * Company:      Radian
 * @author Rodrigo Zerlotti
 * @version
 */

import radian.tcmis.both1.helpers.resource.ResourceLoader;
import java.awt.*;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;

public class SearchDirectedChargeDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JButton okB = new JButton();

  int searchEditableFlag = 0;
  Frame parent;
  CmisApp grandParent;

  JButton cancelC = new JButton();
  SearchDirectedChargeDlg_LoadThread lg;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  JTextField costElementT = new JTextField();
  StaticJLabel jLabel2 = new StaticJLabel();
  DataJLabel materialIDL = new DataJLabel();
  String catalogID = "";
  String partNo = "";
  String costElement = "";

  public SearchDirectedChargeDlg(Frame frame, String partNo,String costElement, boolean modal) {
    super(frame, "Charge Number Search", modal);
    parent = frame;
    this.partNo = partNo;
    this.costElement = costElement;
    try {
      jbInit();
      pack();
      this.setSize(new Dimension(300, 150));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setGrandParent(CmisApp m){
    grandParent = m;
  }

  private void jbInit() throws Exception{
    okB.setText("Ok");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    okB.setMaximumSize(new Dimension(79, 25));
    okB.setMinimumSize(new Dimension(79, 25));
    okB.setPreferredSize(new Dimension(79, 25));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    cancelC.setText("Cancel");
    cancelC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    cancelC.setMaximumSize(new Dimension(103, 25));
    cancelC.setMinimumSize(new Dimension(103, 25));
    cancelC.setPreferredSize(new Dimension(103, 25));
    cancelC.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    panel1.setLayout(gridBagLayout1);
    jLabel1.setText("Charge Number: ");
    jLabel2.setText("User Part Number: ");
    materialIDL.setText(partNo);
    costElementT.setText(costElement);
    this.getContentPane().add(panel1);
    if (partNo.length() > 0 ) {
      panel1.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 5, 5, 0), 0, 0));
      panel1.add(materialIDL, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 0), 0, 0));
      panel1.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
      panel1.add(costElementT, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
      panel1.add(okB, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
      panel1.add(cancelC, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
    }else {
      panel1.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
      panel1.add(costElementT, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
      panel1.add(okB, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
      panel1.add(cancelC, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 95, 5, 5), 0, 0));
    }

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    this.pack();
    CenterComponent.centerCompOnScreen(this);
  }

  public void loadIt(){
    if (partNo.length() > 0 && costElement.length() == 0) {
      lg = new SearchDirectedChargeDlg_LoadThread(this);
      lg.start();
    }
  }

  void searchDirectedCharge(){
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("SearchInfoNew", grandParent);
      Hashtable myQuery = new Hashtable();
      myQuery.put("ACTION","SEARCH_CATALOG_DIRECTED_CHARGE");
      myQuery.put("CATALOG_ITEM_ID",partNo);
      myQuery.put("CATALOG_ID",catalogID);
      Hashtable result = cgi.getResultHash(myQuery);
      if (result == null){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return;
      }
      String costElement = (String)result.get("COST_ELEMENT");
      if (!BothHelpObjs.isBlankString(costElement)) {
        costElementT.setText(costElement);
        costElementT.setEnabled(true);
      }else {
        costElementT.setText("");
        costElementT.setEnabled(true);
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (costElementT.getText().length() < 1 ) {
      GenericDlg gd = new GenericDlg(grandParent.getMain(),"Unknown Charge Number",true);
      gd.setMsg("Please enter a Charge Number.");
      gd.setVisible(true);
      return;
    }
    this.setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }
} //end of class

class SearchDirectedChargeDlg_LoadThread extends Thread {
 SearchDirectedChargeDlg parent;

  public SearchDirectedChargeDlg_LoadThread(SearchDirectedChargeDlg parent){
     super("SearchDirectedChargeDlg_LoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.searchDirectedCharge();
  }
}