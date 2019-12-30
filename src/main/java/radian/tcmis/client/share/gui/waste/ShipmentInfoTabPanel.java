//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.GridBagConstraints2;
import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.appData.*;
import java.beans.*;
import javax.swing.table.*;
import java.util.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;
import java.awt.event.*;
import javax.swing.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class ShipmentInfoTabPanel extends JPanel {
  CmisApp cmis;
  int orderNo;

  DbTableModel model;
  DbTableModel containerModel;
  DbTableModel costModel;
  DbTableModel ctm;
  TableSorter sorter;
  CmisTable rliTable;
  CmisTable costTable;
  CmisTable manLineTable;
  JTable formReturnedT = new JTable();
  Vector manifestV = new Vector();
  Vector copyV = new Vector();
  Vector certV = new Vector();
  Vector containerV = null;
  String containerShipmentId = null;
  String bulkContainer = null;
  Hashtable containerInfoH = null;
  String showTransCost = null;
  Hashtable bulkTableStyleH = null;

  int ACTUAL_AMOUNT_COL = 0;
  int ACTUAL_DATE_COL = 0;
  int MANIFEST_COL = 0;
  int STATE_COL = 0;
  int COPY_RETURN_COL = 0;
  int DISPOSITION_COL = 0;
  int SHIPMENT_ID_COL = 0;
  int BULK_ID_COL = 0;
  int DESC_COL = 0;
  int PACKAGING_COL = 0;
  int PLANNED_AMT_COL = 0;
  int PLANNED_DATE_COL = 0;
  int VENDOR_ORDER_NO_COL = 0;
  int COUNTRY_COL = 0;
  int PROFILE_COL = 0;
  int QTY_COL = 0;

  //shipment tab components
  GridBagLayout gridBagLayout1 = new GridBagLayout(); // shipment tab
  JButton ssubmitB = new JButton();
  //right panel
  GridBagLayout gridBagLayout2 = new GridBagLayout(); //right panel
  JPanel bulkP = new JPanel();
  JScrollPane transferJsp = new JScrollPane();
  StaticJLabel transferL = new StaticJLabel();
  //left panel
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel containerP = new JPanel();
  StaticJLabel costL = new StaticJLabel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JPanel laborP = new JPanel();
  StaticJLabel shippedDateL = new StaticJLabel();
  JTextField actualF = new JTextField();
  StaticJLabel dateL = new StaticJLabel();
  JPanel formReturnedP = new JPanel();
  JScrollPane formJsp = new JScrollPane();
  DataJLabel vendorOrderT = new DataJLabel();
  StaticJLabel vendorOrderL = new StaticJLabel();
  DataJLabel plannedT = new DataJLabel();
  StaticJLabel plannedL = new StaticJLabel();
  StaticJLabel plannedD = new StaticJLabel();
  StaticJLabel manifestNoL = new StaticJLabel();
  JTextField manifestNoF = new JTextField();
  StaticJLabel stateL = new StaticJLabel();
  JComboBox stateC = new JComboBox();
  StaticJLabel copyReturnedL = new StaticJLabel();
  JTextField copyReturnedF = new JTextField();
  JComboBox countryC = new JComboBox();
  StaticJLabel countryL = new StaticJLabel();
  StaticJLabel shipmentIdL = new StaticJLabel();
  DataJLabel shipmentIdT = new DataJLabel();
  StaticJLabel jLabel1 = new StaticJLabel();
  JTextField transF = new JTextField();
  JScrollPane costJsp = new JScrollPane();
  GridBagLayout gridBagLayout5 = new GridBagLayout();

  Vector manifestStateId = new Vector();
  Vector manifestStateDesc = new Vector();

  public ShipmentInfoTabPanel() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public ShipmentInfoTabPanel(CmisApp cmis) {
    this.cmis = cmis;
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setCmis(CmisApp cmis) {
    this.cmis = cmis;
    loadIt();
  }

  public void setOrderNo(int orderNo) {
    this.orderNo = orderNo;
  }

  public void loadIt() {
    ShipmentInfoLoadThread silt = new ShipmentInfoLoadThread(this, "load");
    silt.start();
  }

  private void jbInit() throws Exception {
    ssubmitB.setText("Update");
    ssubmitB.setFont(new java.awt.Font("Dialog", 0, 10));
    ssubmitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif", "Submit"));
    ssubmitB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ssubmitB_actionPerformed(e);
      }
    });

    //Nawaz 09-27-01 to make the update butoon not bold
    ClientHelpObjs.makeDefaultColors(this);

    this.setLayout(gridBagLayout1);
    bulkP.setMaximumSize(new Dimension(150, 270));
    bulkP.setMinimumSize(new Dimension(150, 270));
    bulkP.setPreferredSize(new Dimension(150, 270));

    //initializing form return table.
    //initFormReturnedTable();
    manLineTable = new CmisTable();
    formJsp.getViewport().setView(manLineTable);

    //initializing transfer info table.
    rliTable = new CmisTable();
    transferJsp.getViewport().setView(rliTable);

    costTable = new CmisTable();
    costJsp.getViewport().setView(costTable);

    bulkP.setBorder(ClientHelpObjs.groupBox("Bulk Waste Information"));
    formReturnedP.setLayout(gridBagLayout5);
    laborP.setLayout(gridBagLayout4);
    laborP.setMaximumSize(new Dimension(243, 103));
    containerP.setMaximumSize(new Dimension(200, 180));
    containerP.setMinimumSize(new Dimension(200, 180));
    containerP.setPreferredSize(new Dimension(200, 180));
    /*
         vendorOrderF.setMaximumSize(new Dimension(80, 21));
         vendorOrderF.setMinimumSize(new Dimension(80, 21));
         vendorOrderF.setPreferredSize(new Dimension(80, 21));
     */
    vendorOrderL.setFont(new java.awt.Font("Dialog", 0, 10));
    vendorOrderL.setText("Vendor Order No:");
    /*
         plannedF.setMaximumSize(new Dimension(80, 21));
         plannedF.setMinimumSize(new Dimension(80, 21));
         plannedF.setPreferredSize(new Dimension(80, 21));
     */
    plannedL.setFont(new java.awt.Font("Dialog", 0, 10));
    plannedL.setText("Planned Pickup Date:");
    plannedD.setFont(new java.awt.Font("Dialog", 0, 10));
    //plannedD.setText("(mm/dd/yyyy)");
    //plannedF.setToolTipText("Use: mm/dd/yyyy  ");

    manifestNoL.setText("Manifest No:");
    manifestNoF.setFont(new java.awt.Font("SansSerif", 0, 10));
    manifestNoF.setMaximumSize(new Dimension(80, 21));
    manifestNoF.setMinimumSize(new Dimension(80, 21));
    manifestNoF.setPreferredSize(new Dimension(80, 21));
    manifestNoF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        manifestNoF_actionPerformed(e);
      }
    });
    manifestNoF.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        manifestNoF_mousePressed();
      }
    });
    stateL.setText("State:");
    stateC.setFont(new java.awt.Font("SansSerif", 0, 10));
    stateC.setMaximumSize(new Dimension(80, 21));
    stateC.setMinimumSize(new Dimension(80, 21));
    stateC.setPreferredSize(new Dimension(80, 21));
    stateC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        stateC_actionPerformed(e);
      }
    });
    copyReturnedL.setText("Copy Returned Date:");
    copyReturnedF.setFont(new java.awt.Font("SansSerif", 0, 10));
    copyReturnedF.setMinimumSize(new Dimension(80, 21));
    copyReturnedF.setPreferredSize(new Dimension(80, 21));
    copyReturnedF.setToolTipText("Use: mm/dd/yyyy  ");
    copyReturnedF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        copyReturnedF_actionPerformed(e);
      }
    });
    copyReturnedF.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        copyReturnedF_mousePressed();
      }
    });
    countryC.setFont(new java.awt.Font("SansSerif", 0, 10));
    countryC.setMaximumSize(new Dimension(80, 21));
    countryC.setMinimumSize(new Dimension(80, 21));
    countryC.setPreferredSize(new Dimension(80, 21));
    countryC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        countryC_actionPerformed(e);
      }
    });
    countryL.setText("Country:");
    shipmentIdL.setText("Shipment Id:");
    jLabel1.setText("Transportation Cost:");
    transF.setFont(new java.awt.Font("SansSerif", 0, 10));
    transF.setMaximumSize(new Dimension(80, 21));
    transF.setMinimumSize(new Dimension(80, 21));
    transF.setPreferredSize(new Dimension(80, 21));
    transF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        transF_actionPerformed(e);
      }
    });
    transF.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        transF_mousePressed();
      }
    });
    formJsp.setMinimumSize(new Dimension(50, 60));
    formJsp.setPreferredSize(new Dimension(50, 60));
    costJsp.setMinimumSize(new Dimension(50, 60));
    costJsp.setPreferredSize(new Dimension(50, 60));
    actualF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actualF_actionPerformed(e);
      }
    });
    actualF.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        actualF_mousePressed();
      }
    });

    actualF.setFont(new java.awt.Font("SansSerif", 0, 10));
    this.add(ssubmitB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(7, 100, 0, 50), 0, 0));
    //bottom panel
    this.add(bulkP, new GridBagConstraints2(0, 2, 2, 1, 0.5, 0.5
                                            , GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
    bulkP.setLayout(gridBagLayout2);
    bulkP.add(transferJsp, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
    //top panel
    containerP.setBorder(ClientHelpObjs.groupBox("Container Waste Information"));
    this.add(containerP, new GridBagConstraints2(0, 1, 2, 1, 1.0, 0.0
                                                 , GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    containerP.setLayout(gridBagLayout3);

    shippedDateL.setText("Actual Pickup Date:");
    //dateL.setText("(mm/dd/yyyy)");
    actualF.setToolTipText("Use: mm/dd/yyyy   ");
    actualF.setMaximumSize(new Dimension(80, 21));
    actualF.setMinimumSize(new Dimension(80, 21));
    actualF.setPreferredSize(new Dimension(80, 21));
    containerP.add(laborP, new GridBagConstraints2(1, 1, 1, 1, 0.0, 1.0
        , GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 50), 0, 0));

    laborP.add(shippedDateL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    laborP.add(actualF, new GridBagConstraints2(1, 3, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    laborP.add(dateL, new GridBagConstraints2(2, 3, 1, 3, 0.0, 0.0
                                              , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    laborP.add(vendorOrderT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    laborP.add(vendorOrderL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    laborP.add(plannedT, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    laborP.add(plannedL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    laborP.add(plannedD, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    laborP.add(manifestNoL, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    laborP.add(manifestNoF, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    laborP.add(stateL, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    laborP.add(stateC, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    laborP.add(copyReturnedL, new GridBagConstraints2(3, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 25, 0, 0), 0, 0));
    laborP.add(copyReturnedF, new GridBagConstraints2(4, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    laborP.add(countryC, new GridBagConstraints2(4, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    laborP.add(countryL, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    laborP.add(shipmentIdL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    laborP.add(shipmentIdT, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    laborP.add(jLabel1, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    laborP.add(transF, new GridBagConstraints2(1, 4, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    containerP.add(formReturnedP, new GridBagConstraints2(2, 1, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    formReturnedP.add(formJsp, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
    //formReturnedP.add(costJsp, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
    //        ,GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));

  }

  public void initFormReturnedTable() {
    String[] columnNames = {
        "Man. Line No.", "Disposal Date"};
    ctm = new DbTableModel(columnNames);
    int row = 10;
    while (row > 0) {
      Object[] oa = new Object[2];
      oa[0] = new String("");
      oa[1] = new String("");
      ctm.addRow(oa);
      row--;
    }
    ctm.setEditableFlag(3);
    formReturnedT = new JTable(ctm);
    formReturnedT.setToolTipText("Date Format:  mm/dd/yyyy");
  }

  public Vector getContainerInfo() {
    Vector result = new Vector();
    if (manLineTable.isEditing()) {
      manLineTable.getCellEditor().stopCellEditing();
    }
    for (int i = 0; i < manLineTable.getRowCount(); i++) {
      Hashtable h = new Hashtable();
      h.put("MANIFEST_LINE_NO", BothHelpObjs.makeBlankFromNull(manLineTable.getModel().getValueAt(i, 0).toString()));
      h.put("COD", BothHelpObjs.makeBlankFromNull(manLineTable.getModel().getValueAt(i, 1).toString()));
      result.addElement(h);
    }
    return result;
  }

  public Vector getBulkInfo() {
    Vector result = new Vector();
    if (rliTable.isEditing()) {
      rliTable.getCellEditor().stopCellEditing();
    }
    for (int i = 0; i < rliTable.getRowCount(); i++) {
      Hashtable h = new Hashtable();
      h.put("ACTUAL_AMOUNT", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.ACTUAL_AMOUNT_COL).toString()));
      h.put("ACTUAL_PICKUP", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.ACTUAL_DATE_COL).toString()));
      h.put("MANIFEST_NO", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.MANIFEST_COL).toString()));
      h.put("COPY_RETURNED", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.COPY_RETURN_COL).toString()));
      h.put("SHIPMENT_ID", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.SHIPMENT_ID_COL).toString()));
      h.put("BULK_ID", rliTable.getModel().getValueAt(i, this.BULK_ID_COL).toString());
      h.put("STATE",getManifestStateIdFromDesc(((JComboBox) rliTable.getModel().getValueAt(i,STATE_COL)).getSelectedItem().toString()));
      h.put("DISPOSITION", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.DISPOSITION_COL).toString()));
      h.put("PLANNED_AMOUNT", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.PLANNED_AMT_COL).toString()));
      h.put("VENDOR_ORDER_NO", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.VENDOR_ORDER_NO_COL).toString()));
      h.put("PLANNED_PICKUP", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.PLANNED_DATE_COL).toString()));
      h.put("COUNTRY", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.COUNTRY_COL).toString()));
      h.put("PROFILE", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.PROFILE_COL).toString()));
      h.put("PACKAGING", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.PACKAGING_COL).toString()));
      h.put("DESCRIPTION", BothHelpObjs.makeBlankFromNull(rliTable.getModel().getValueAt(i, this.DESC_COL).toString()));
      result.addElement(h);
    }
    return result;
  }

  void ssubmitB_actionPerformed(ActionEvent e) {
    //check to see if user enter the right format for containers
    String actualDat = BothHelpObjs.makeBlankFromNull(actualF.getText());
    if (!BothHelpObjs.isBlankString(actualDat)) {
      if (!BothHelpObjs.isDateFormatRight(actualDat)) {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
        gd.setMsg("Invalid Date Format. Please enter an actual\n pickup date in the following format(MM/DD/YYYY)");
        gd.setVisible(true);
        return;
      }
    }
    String manifestRet = BothHelpObjs.makeBlankFromNull(copyReturnedF.getText());
    if (!BothHelpObjs.isBlankString(manifestRet)) {
      if (!BothHelpObjs.isDateFormatRight(manifestRet)) {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
        gd.setMsg("Invalid Date Format. Please enter a copy\n returned date in the following format(MM/DD/YYYY)");
        gd.setVisible(true);
        return;
      }
    }
    //

    goUpdateShipment();
  }

  public void goUpdateShipment() {
    try {
      ClientHelpObjs.setEnabledContainer(this, false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement", cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION", "UPDATE_SHIPMENT"); //string,string
      query.put("BULK_CONTAINER", bulkContainer);
      if (bulkContainer.equalsIgnoreCase("container") ||
          bulkContainer.equalsIgnoreCase("both")) {
        query.put("CONTAINER_SHIPMENT_ID", containerShipmentId);
        query.put("ACTUAL_PICKUP", BothHelpObjs.makeBlankFromNull(actualF.getText()));
        query.put("MANIFEST_ID", BothHelpObjs.makeBlankFromNull(manifestNoF.getText()));
        query.put("STATE", BothHelpObjs.makeBlankFromNull(getManifestStateIdFromDesc(stateC.getSelectedItem().toString())));
        query.put("COUNTRY", BothHelpObjs.makeBlankFromNull(countryC.getSelectedItem().toString()));
        query.put("MANIFEST_RETURN", BothHelpObjs.makeBlankFromNull(copyReturnedF.getText()));
        query.put("CONTAINER_FORM_INFO", getContainerInfo()); //string,vector
      }
      if (bulkContainer.equalsIgnoreCase("bulk") ||
          bulkContainer.equalsIgnoreCase("both")) {
        query.put("BULK_INFO", getBulkInfo()); //string,vector
      }
      query.put("ORDER_NO", new Integer(this.orderNo));

      //transportation cost
      query.put("SHOW_TRANS_COST", BothHelpObjs.makeBlankFromNull(showTransCost));
      if (showTransCost.equalsIgnoreCase("Y")) {
        String trans = BothHelpObjs.makeBlankFromNull(transF.getText());
        if (!BothHelpObjs.isBlankString(trans)) {
          int transCost = 0;
          try {
            transCost = Integer.parseInt(trans);
            if (transCost < 0) {
              GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
              gd.setMsg("Please enter a position number for transportation cost");
              gd.setVisible(true);
              return;
            } else {
              query.put("TRANS_COST", trans);
            }
          } catch (Exception e1) {
            GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
            gd.setMsg("Please enter a position number for transportation cost");
            gd.setVisible(true);
            return;
          }
        } else {
          query.put("TRANS_COST", trans);
        }
      }

      Hashtable result = cgi.getResultHash(query);
      ClientHelpObjs.setEnabledContainer(this, true);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      String msg = (String) result.get("SUCCEEDED");
      if (msg.equalsIgnoreCase("succeeded")) {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Succeeded", true);
        gd.setMsg("Waste shipment was successfully updated.");
        gd.setVisible(true);
      } else {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
        gd.setMsg("Waste shipment update failed.");
        gd.setVisible(true);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ClientHelpObjs.setEnabledContainer(this, true);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      this.revalidate();
    }

  }

  public void loadItAction(String action) {
    CursorChange.setCursor(this, Cursor.WAIT_CURSOR);
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement", cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION", "GET_SHIPMENT_INFO_TAB"); //String, String
      query.put("MANAGER_ID", cmis.getUserId());
      query.put("SHIPMENT_NUM", new Integer(orderNo));

      Hashtable result = cgi.getResultHash(query);
      ClientHelpObjs.setEnabledContainer(this, true);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      showTransCost = (String) result.get("SHOW_TRANS_COST");

      Hashtable ht = new Hashtable();
      ht = (Hashtable) result.get("SCREEN_DATA");
      bulkContainer = (String) ht.get("BULK_CONTAINER");
      //setting manifest states
      manifestStateId = (Vector) ht.get("STATE_VECTOR");
      manifestStateDesc = (Vector) ht.get("STATE_DESC_VECTOR");
      if (bulkContainer.equalsIgnoreCase("container") || bulkContainer.equalsIgnoreCase("both")) {
        containerV = (Vector) ht.get("CONTAINER_ID");
        containerShipmentId = (String) ht.get("CONTAINER_SHIPMENT_ID");
        containerInfoH = (Hashtable) ht.get("CONTAINER_INFO_H");
        containerInfoH.put("TABLE_STYLE", (Hashtable) result.get("TABLE_STYLE"));
        bulkTableStyleH = new Hashtable(1);
        bulkTableStyleH.put("TABLE_STYLE", (Hashtable) result.get("TABLE_STYLE"));

        Vector countryV = (Vector) ht.get("COUNTRY_VECTOR");
        countryC = ClientHelpObjs.loadChoiceFromVector(countryC, countryV);
        stateC = ClientHelpObjs.loadChoiceFromVector(stateC, manifestStateDesc);
        buildContainerInfo();
        shipmentIdT.setText(containerShipmentId);
        buildContainerTableModel(ht);

        buildManifestLineTable();
        buildCostTableModel(ht);
        QTY_COL = ( (Integer) ht.get("QTY_COL")).intValue();
      } else {
        bulkTableStyleH = new Hashtable(1);
        bulkTableStyleH.put("TABLE_STYLE", (Hashtable) result.get("TABLE_STYLE"));
      }

      // columns
      Hashtable h = (Hashtable) ht.get("KEY_COLS");
      ACTUAL_AMOUNT_COL = ( (Integer) h.get("Actual Amt.")).intValue();
      ACTUAL_DATE_COL = ( (Integer) h.get("Actual Date")).intValue();
      MANIFEST_COL = ( (Integer) h.get("Manifest")).intValue();
      STATE_COL = ( (Integer) h.get("State")).intValue();
      COPY_RETURN_COL = ( (Integer) h.get("Copy Returned")).intValue();
      DISPOSITION_COL = ( (Integer) h.get("Disposition")).intValue();
      SHIPMENT_ID_COL = ( (Integer) h.get("Shipment ID")).intValue();
      BULK_ID_COL = ( (Integer) h.get("Bulk ID")).intValue();
      DESC_COL = ( (Integer) h.get("Description")).intValue();
      PACKAGING_COL = ( (Integer) h.get("Packaging")).intValue();
      PLANNED_AMT_COL = ( (Integer) h.get("Planned Amt.")).intValue();
      PLANNED_DATE_COL = ( (Integer) h.get("Planned Date")).intValue();
      VENDOR_ORDER_NO_COL = ( (Integer) h.get("Vendor Order No")).intValue();
      COUNTRY_COL = ( (Integer) h.get("Country")).intValue();
      PROFILE_COL = ( (Integer) h.get("Profile")).intValue();

      buildBulkTableModel(ht);
      buildTransferSummary();

      if (bulkContainer.equalsIgnoreCase("bulk")) {
        this.remove(containerP);
      }

      ClientHelpObjs.makeDefaultColors(this);
      ClientHelpObjs.setEnabledContainer(this, true);
      ssubmitB.setVisible(false);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ClientHelpObjs.setEnabledContainer(this, true);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      this.revalidate();
    }
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
  }

  JComboBox getManifestStateComboBox(String s) {
    JComboBox j = new JComboBox();
    j = ClientHelpObjs.loadChoiceFromVector(j, manifestStateDesc);
    j.setSelectedItem(getManifestStateDescFromId(s));
    j.setEditable(false);
    j.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rliTable.repaint();
      }
    });
    j.revalidate();
    return j;
  }


  String getManifestStateDescFromId(String value) {
    System.out.println(value);
    int index = manifestStateId.indexOf(value);
    if (index < 0) {
      index = 0;
    }
    return manifestStateDesc.elementAt(index).toString();
  } //end of method

  String getManifestStateIdFromDesc(String value) {
    int index = manifestStateDesc.indexOf(value);
    if (index < 0) {
      index = 0;
    }
    return manifestStateId.elementAt(index).toString();
  } //end of method


  void buildContainerTableModel(Hashtable searchData) {
    String[] colHeads = (String[]) searchData.get("CONTAINER_MANIFEST_LINE_TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("CONTAINER_MANIFEST_LINE_TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("CONTAINER_MANIFEST_LINE_TABLE_TYPES");
    Vector data = (Vector) searchData.get("CONTAINER_MANIFEST_LINE_TABLE_DATA");

    containerModel = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      containerModel.addRow( (Object[]) data.elementAt(i));
    }
    containerModel.setColumnPrefWidth(colWidths);
    containerModel.setColumnType(colTypes);
  } //end of method

  void buildCostTableModel(Hashtable searchData) {
    String[] colHeads = (String[]) searchData.get("COST_TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("COST_TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("COST_TABLE_TYPES");
    Vector data = (Vector) searchData.get("COST_TABLE_DATA");

    costModel = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      costModel.addRow( (Object[]) data.elementAt(i));
    }
    costModel.setColumnPrefWidth(colWidths);
    costModel.setColumnType(colTypes);
  } //end of method

  void buildBulkTableModel(Hashtable searchData) {
    String[] colHeads = (String[]) searchData.get("BULK_TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("BULK_TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("BULK_TABLE_TYPES");
    Vector data = (Vector) searchData.get("BULK_TABLE_DATA");
    model = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      Object[] oa = (Object[]) data.elementAt(i);
      oa[STATE_COL] = this.getManifestStateComboBox(oa[STATE_COL].toString());
      model.addRow(oa);
      //model.addRow( (Object[]) data.elementAt(i));
    }
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
  } //end of method

  public void buildContainerInfo() {
    vendorOrderT.setText( (String) containerInfoH.get("VENDOR_SHIPMENT_ID"));
    plannedT.setText( (String) containerInfoH.get("PLANNED_PICKUP_DATE"));
    actualF.setText( (String) containerInfoH.get("ACTUAL_PICKUP_DATE"));
    manifestNoF.setText( (String) containerInfoH.get("MANIFEST_ID"));
    copyReturnedF.setText( (String) containerInfoH.get("COPY_RETURNED"));
    countryC.setSelectedItem( (String) containerInfoH.get("COUNTRY"));
    stateC.setSelectedItem(getManifestStateDescFromId((String) containerInfoH.get("STATE")));
    if (showTransCost.equalsIgnoreCase("Y")) {
      jLabel1.setVisible(true);
      transF.setVisible(true);
      transF.setText( (String) containerInfoH.get("TRANS_COST"));
    } else {
      jLabel1.setVisible(false);
      transF.setVisible(false);
    }

  }

  //trong 12-7-00
  public Hashtable getContainerReportInfo() {
    Hashtable h = new Hashtable();
    h.put("SHIPMENT_ID", shipmentIdT.getText());
    h.put("VENDOR_ORDER_NO", vendorOrderT.getText());
    h.put("PLANNED_PICKUP_DATE", plannedT.getText());
    h.put("ACTUAL_PICKUP_DATE", actualF.getText());
    h.put("MANIFEST_NO", manifestNoF.getText());
    h.put("STATE", getManifestStateIdFromDesc(stateC.getSelectedItem().toString()));
    h.put("COUNTRY", countryC.getSelectedItem().toString());
    h.put("COPY_RETURNED_DATE", copyReturnedF.getText());
    h.put("MANIFEST_LINE_INFO", getContainerInfo());

    return h;
  }

  public void printScreenData(Hashtable headerInfo) {
    CursorChange.setCursor(this, Cursor.WAIT_CURSOR);
    try {
      //ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement", cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION", "PRINT_SCREEN"); //String, String
      query.put("USER_ID", cmis.getUserId());
      query.put("HEADER_INFO", headerInfo); //String, Hashtable
      query.put("CONTAINER_INFO", getContainerReportInfo()); //String, Hashtable
      query.put("BULK_INFO", getBulkInfo()); //String, Vector
      Hashtable result = cgi.getResultHash(query);
      ClientHelpObjs.setEnabledContainer(this, true);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Hashtable h = (Hashtable) result.get("RETURN_CODE");
      Boolean b = (Boolean) h.get("SUCCEEDED");
      if (b.booleanValue()) {
        try {
          // From the stand alone application
          String url = (String) result.get("URL");
          new URLCall(url, cmis);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
        gd.setMsg(h.get("MSG").toString());
        gd.setVisible(true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ClientHelpObjs.setEnabledContainer(this, true);
      this.revalidate();
    }
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
  }

  public boolean preAudit() {
    String deliveryDate = (String) actualF.getText();

    //try to parse the string to see if date entered is valid
    char d1 = deliveryDate.charAt(0);

    Character d2 = new Character(d1);
    Integer nn1 = new Integer(d1);

    if (deliveryDate.length() < 1) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      gd.setMsg("Please enter shipped date.");
      gd.setVisible(true);
      return false;
    }

    Date d = new Date();
    Calendar cal = Calendar.getInstance();
    String cdate = new String( (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));

    long n1 = d.parse(deliveryDate);
    long n2 = d.parse(cdate);
    if (n2 > n1) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      gd.setMsg("The date you entered is invalid!\n Please check and re-enter it again.");
      gd.setVisible(true);
      return false;
    }
    return true;
  }

  public void buildManifestLineTable() {
    formJsp.getViewport().remove(manLineTable);
    containerModel.setEditableFlag(2);
    manLineTable = new CmisTable(containerModel);
    manLineTable.setToolTipText("Date Format: mm/dd/yyyy");
    manLineTable.addMouseListener(new ShipmentCostTable_manLineTable_mouseAdapter(this));

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable) containerInfoH.get("TABLE_STYLE");
    Color color = (Color) chargeTableStyle.get("COLOR");
    Integer t = (Integer) chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer) chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer) chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer) chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer) chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(), l.intValue(), b.intValue(), r.intValue()), a.intValue());

    //font and foreground and background color for columns heading
    String fontName = (String) chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer) chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer) chargeTableStyle.get("FONT_SIZE");
    manLineTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    Enumeration enum1 = manLineTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    // set column widths
    for (int i = 0; i < manLineTable.getColumnCount(); i++) {
      int width = containerModel.getColumnWidth(i);
      manLineTable.getColumn(containerModel.getColumnName(i)).setPreferredWidth(width);
      manLineTable.getColumn(containerModel.getColumnName(i)).setWidth(width);
      if (width == 0) {
        manLineTable.getColumn(containerModel.getColumnName(i)).setMinWidth(width);
        manLineTable.getColumn(containerModel.getColumnName(i)).setMaxWidth(width);
      }
    }
    formJsp.getViewport().setView(manLineTable);
  }

  public int mypow(int y, int x) {
    int value = 1;
    for (int i = 0; i < x; i++) {
      value *= y;
    }
    return value;
  }

  public void buildTransferSummary() {
    transferJsp.getViewport().remove(rliTable);
    int num = mypow(2, ACTUAL_AMOUNT_COL) + mypow(2, ACTUAL_DATE_COL) + mypow(2, MANIFEST_COL) + mypow(2, STATE_COL) + mypow(2, COPY_RETURN_COL) + mypow(2, DISPOSITION_COL);
    model.setEditableFlag(num);
    sorter = new TableSorter(model);
    rliTable = new CmisTable(sorter);
    rliTable.setToolTipText("Date Format: mm/dd/yyyy");
    sorter.setColTypeFlag(model.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(rliTable);


    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable) bulkTableStyleH.get("TABLE_STYLE");
    Color color = (Color) chargeTableStyle.get("COLOR");
    Integer t = (Integer) chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer) chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer) chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer) chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer) chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(), l.intValue(), b.intValue(), r.intValue()), a.intValue());
    //font and foreground and background color for columns heading
    String fontName = (String) chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer) chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer) chargeTableStyle.get("FONT_SIZE");
    rliTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    Enumeration enum1 = rliTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    rliTable.getTableHeader().setResizingAllowed(true);
    rliTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    rliTable.setCellSelectionEnabled(false);
    rliTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    rliTable.getTableHeader().setReorderingAllowed(true);
    rliTable.addMouseListener(new ShipmentInfoTab_rliTable_mouseAdapter(this));

    WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
    rliTable.setDefaultRenderer(String.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Double.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Float.class, colorTableRenderer);

    rliTable.setDefaultRenderer(JComboBox.class,new JComboBoxCellRenderer());
    rliTable.setDefaultEditor(JComboBox.class,new JComboBoxCellEditor(new JComboBox()));

    // set column widths
    for (int i = 0; i < rliTable.getColumnCount(); i++) {
      int width = model.getColumnWidth(i);
      rliTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      rliTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width == 0) {
        rliTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        rliTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }

    transferJsp.getViewport().setView(rliTable);

    bulkP.repaint();
  }

  public void buildCostTable() {
    costJsp.getViewport().remove(costTable);
    costModel.setEditableFlag(BothHelpObjs.mypow(2, QTY_COL));
    sorter = new TableSorter(costModel);
    costTable = new CmisTable(sorter);
    sorter.setColTypeFlag(costModel.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(costTable);
    costTable.getTableHeader().setResizingAllowed(true);
    costTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    costTable.setCellSelectionEnabled(false);
    costTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    costTable.getTableHeader().setReorderingAllowed(true);
    costTable.addMouseListener(new ShipmentCostTable_costTable_mouseAdapter(this));
    // set column widths
    for (int i = 0; i < costTable.getColumnCount(); i++) {
      int width = costModel.getColumnWidth(i);
      costTable.getColumn(costModel.getColumnName(i)).setPreferredWidth(width);
      costTable.getColumn(costModel.getColumnName(i)).setWidth(width);
      if (width == 0) {
        costTable.getColumn(costModel.getColumnName(i)).setMinWidth(width);
        costTable.getColumn(costModel.getColumnName(i)).setMaxWidth(width);
      }
    }
    costJsp.getViewport().setView(costTable);
  }

  public Vector getCostInfo() {
    Vector result = new Vector();
    if (costTable.isEditing()) {
      costTable.getCellEditor().stopCellEditing();
    }
    for (int i = 0; i < costTable.getRowCount(); i++) {
      Hashtable h = new Hashtable();
      h.put("QUANTITY", BothHelpObjs.makeBlankFromNull(costTable.getModel().getValueAt(i, 2).toString()));
      h.put("COST_TYPE", BothHelpObjs.makeBlankFromNull(costTable.getModel().getValueAt(i, 3).toString()));
      result.addElement(h);
    }
    return result;
  }

  void rliTable_mouseClicked(MouseEvent e) {
    if (rliTable.getSelectedColumn() >= ACTUAL_AMOUNT_COL) {
      ssubmitB.setVisible(true);
    }
  }

  void costTable_mouseClicked(MouseEvent e) {
    if (costTable.getSelectedColumn() == QTY_COL) {
      cmis.refreshShipmentCost = true;
      cmis.refreshVendorInvoice = true;
      ssubmitB.setVisible(true);
    }
  }

  void manLineTable_mouseClicked(MouseEvent e) {
    if (manLineTable.getSelectedColumn() == 1) {
      ssubmitB.setVisible(true);
    }
  }

  void transF_mousePressed() {
    cmis.refreshShipmentCost = true;
    cmis.refreshVendorInvoice = true;
    ssubmitB.setVisible(true);
  }

  void transF_actionPerformed(ActionEvent e) {
    cmis.refreshShipmentCost = true;
    cmis.refreshVendorInvoice = true;
    ssubmitB.setVisible(true);
  }

  void actualF_mousePressed() {
    ssubmitB.setVisible(true);
  }

  void actualF_actionPerformed(ActionEvent e) {
    ssubmitB.setVisible(true);
  }

  void manifestNoF_mousePressed() {
    ssubmitB.setVisible(true);
  }

  void manifestNoF_actionPerformed(ActionEvent e) {
    ssubmitB.setVisible(true);
  }

  void stateC_actionPerformed(ActionEvent e) {
    ssubmitB.setVisible(true);
  }

  void countryC_actionPerformed(ActionEvent e) {
    ssubmitB.setVisible(true);
  }

  void copyReturnedF_mousePressed() {
    ssubmitB.setVisible(true);
  }

  void copyReturnedF_actionPerformed(ActionEvent e) {
    ssubmitB.setVisible(true);
  }
} //end of class

class ShipmentInfoLoadThread extends Thread {
  ShipmentInfoTabPanel sitp;
  String action;
  public ShipmentInfoLoadThread(ShipmentInfoTabPanel sitp, String action) {
    super();
    this.sitp = sitp;
    this.action = action;
  }

  public void run() {
    if (action.equalsIgnoreCase("load")) {
      sitp.loadItAction("load");
    } else if (action.equalsIgnoreCase("reload")) {
      sitp.loadItAction("reload");
    }
  }
}

class ShipmentInfoTab_rliTable_mouseAdapter extends java.awt.event.MouseAdapter {
  ShipmentInfoTabPanel adaptee;
  ShipmentInfoTab_rliTable_mouseAdapter(ShipmentInfoTabPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.rliTable_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
  }
}

class ShipmentCostTable_costTable_mouseAdapter extends java.awt.event.MouseAdapter {
  ShipmentInfoTabPanel adaptee;
  ShipmentCostTable_costTable_mouseAdapter(ShipmentInfoTabPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.costTable_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
  }
}

class ShipmentCostTable_manLineTable_mouseAdapter extends java.awt.event.MouseAdapter {
  ShipmentInfoTabPanel adaptee;
  ShipmentCostTable_manLineTable_mouseAdapter(ShipmentInfoTabPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.manLineTable_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
  }
}

