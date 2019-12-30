//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;


import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;

import radian.tcmis.client.share.helpers.*;
import java.beans.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.GridBagConstraints2;


public class EvalMRPane extends JPanel {
  CmisApp cmis;
  NewChem nchem = null;
  JPanel topP = new JPanel();
  JPanel middleP = new JPanel();
  JPanel bottomP = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  JTextField userT = new JTextField();
  JTextField deptT = new JTextField();
  StaticJLabel jLabel4 = new StaticJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  StaticJLabel jLabel6 = new StaticJLabel();
  DataJLabel actSysL = new DataJLabel();
  DataJLabel facL = new DataJLabel();
  DataJLabel waL = new DataJLabel();
  StaticJLabel jLabel7 = new StaticJLabel();
  StaticJLabel jLabel8 = new StaticJLabel();
  StaticJLabel jLabel9 = new StaticJLabel();
  DataJLabel itemL = new DataJLabel();
  DataJLabel extPriceL = new DataJLabel();
  StaticJLabel jLabel10 = new StaticJLabel();
  StaticJLabel jLabel11 = new StaticJLabel();
  StaticJLabel jLabel12 = new StaticJLabel();
  //DataJLabel qtyL = new DataJLabel();
  JTextField qtyT = new JTextField();
  JCheckBox critCheck = new JCheckBox();
  JButton noteB = new JButton();
  JPanel leftP = new JPanel();
  JPanel rightP = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JScrollPane previousJSP = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JScrollPane chargeJSP = new JScrollPane();

  MultiSpanCellTable chargeTable = null;
  String[] chargeCols = {"Charge Number 1","Charge Number 2","%"};
  int[] chargeColWidths = {96,96,80};
  int[] chargeColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  MultiSpanCellTable preOrderTable = null;
  String[] preOrderCols = {"Facility","Work Area","User Group","Qty","Date","Requestor"};
  int[] preOrderColWidths = {110,130,100,70,100,150};
  int[] preOrderColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING};
  JRadioButton directRB = new JRadioButton();
  JRadioButton inDirectRB = new JRadioButton();
  StaticJLabel jLabel13 = new StaticJLabel();
  StaticJLabel jLabel14 = new StaticJLabel();
  StaticJLabel jLabel15 = new StaticJLabel();
  JTextField deliverByT = new JTextField();
  JComboBox dockC = new JComboBox();
  JComboBox deliverC = new JComboBox();
  StaticJLabel jLabel16 = new StaticJLabel();
  StaticJLabel jLabel17 = new StaticJLabel();
  JTextField poDetailT = new JTextField();
  JComboBox poC = new JComboBox();
  JPanel leftBottomP = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel leftTopP = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  Hashtable engEvalMRInfo;
  JTextArea descT = new JTextArea();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  boolean usesDirectedCharge = false;
  boolean loadingDetail = false;
  JTextField poT = new JTextField();

  StaticJLabel unitPriceL = new StaticJLabel();
  JTextField unitPriceT = new JTextField();
  JComboBox currencyC = new JComboBox();

  String selectedDock = "";
  String selectedDeliveryPoint = "";

  public EvalMRPane() {
    super();
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setCmisApp(CmisApp cmis) {
    this.cmis = cmis;
  }

  public void setNewChem(NewChem n) {
    this.nchem = n;
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    //this.setBorder(ClientHelpObjs.groupBox(""));
    topP.setLayout(gridBagLayout2);
    topP.setBorder(ClientHelpObjs.groupBox(" Header Info: "));
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel1.setText("End User:");
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel2.setText("Department:");
    userT.setFont(new java.awt.Font("SansSerif", 0, 10));
    userT.setMaximumSize(new Dimension(80, 21));
    userT.setMinimumSize(new Dimension(80, 21));
    userT.setPreferredSize(new Dimension(80, 21));
    deptT.setFont(new java.awt.Font("SansSerif", 0, 10));
    deptT.setMaximumSize(new Dimension(80, 21));
    deptT.setMinimumSize(new Dimension(80, 21));
    deptT.setPreferredSize(new Dimension(80, 21));
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel4.setText("Actg Sys:");
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel5.setText("Facility:");
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel6.setText("Work Area:");
    facL.setText("jLabel7");
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel7.setText("Item:");
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel8.setText("Desc:");
    jLabel9.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel9.setText("Ext. Price:");
    itemL.setText("jLabel10");
    jLabel10.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel10.setText("Qty:");
    jLabel11.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel11.setText("Critical:");
    jLabel12.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel12.setText("Notes:");
    noteB.setFont(new java.awt.Font("Dialog", 0, 10));
    noteB.setBorder(null);
    noteB.setMaximumSize(new Dimension(50, 21));
    noteB.setMinimumSize(new Dimension(50, 21));
    noteB.setPreferredSize(new Dimension(50, 21));
    noteB.setText("Click Here");
    noteB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        noteB_actionPerformed(e);
      }
    });
    noteB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        CursorChange.setCursor(cmis.getMain(),Cursor.HAND_CURSOR);
      }
      public void mouseExited(MouseEvent e) {
        CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
      }
    });
    middleP.setLayout(gridBagLayout3);
    middleP.setBorder(ClientHelpObjs.groupBox(" Charges and Delivery Info: "));
    middleP.setPreferredSize(new Dimension(610, 150));
    leftP.setLayout(borderLayout3);
    rightP.setLayout(gridBagLayout4);
    bottomP.setBorder(ClientHelpObjs.groupBox(" Previous Evaluation Orders: "));
    bottomP.setPreferredSize(new Dimension(610, 135));
    bottomP.setLayout(borderLayout1);
    directRB.setText(" Direct   ");
    directRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        directRB_actionPerformed(e);
      }
    });
    inDirectRB.setText(" Indirect  ");
    inDirectRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        inDirectRB_actionPerformed(e);
      }
    });
    qtyT.setFont(new java.awt.Font("Dialog", 0, 10));
    qtyT.setMaximumSize(new Dimension(30, 21));
    qtyT.setMinimumSize(new Dimension(30, 21));
    qtyT.setPreferredSize(new Dimension(30, 21));
    jLabel13.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel13.setText("Delivery By:");
    jLabel14.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel14.setText("Dock:");
    jLabel15.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel15.setText("Deliver to:");
    deliverByT.setFont(new java.awt.Font("Dialog", 0, 10));
    deliverByT.setMaximumSize(new Dimension(120, 21));
    deliverByT.setMinimumSize(new Dimension(120, 21));
    deliverByT.setPreferredSize(new Dimension(120, 21));
    dockC.setFont(new java.awt.Font("Dialog", 0, 10));
    dockC.setMaximumSize(new Dimension(120, 21));
    dockC.setMinimumSize(new Dimension(120, 21));
    dockC.setPreferredSize(new Dimension(120, 21));
    dockC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dockC_actionPerformed(e);
      }
    });
    deliverC.setFont(new java.awt.Font("Dialog", 0, 10));
    deliverC.setMaximumSize(new Dimension(120, 21));
    deliverC.setMinimumSize(new Dimension(120, 21));
    deliverC.setPreferredSize(new Dimension(120, 21));
    jLabel16.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel16.setText("PO:");
    jLabel17.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel17.setText("Line:");
    poDetailT.setFont(new java.awt.Font("Dialog", 0, 10));
    poDetailT.setMaximumSize(new Dimension(120, 21));
    poDetailT.setMinimumSize(new Dimension(120, 21));
    poDetailT.setPreferredSize(new Dimension(120, 21));
    poC.setFont(new java.awt.Font("Dialog", 0, 10));
    poC.setMaximumSize(new Dimension(120, 21));
    poC.setMinimumSize(new Dimension(120, 21));
    poC.setPreferredSize(new Dimension(120, 21));
    poT.setFont(new java.awt.Font("Dialog", 0, 10));
    poT.setMaximumSize(new Dimension(120, 21));
    poT.setMinimumSize(new Dimension(120, 21));
    poT.setPreferredSize(new Dimension(120, 21));

    leftBottomP.setLayout(borderLayout2);
    leftTopP.setMinimumSize(new Dimension(137, 38));
    leftTopP.setPreferredSize(new Dimension(137, 38));
    leftTopP.setLayout(gridBagLayout5);
    descT.setLineWrap(true);
    descT.setPreferredSize(new Dimension(80, 30));
    descT.setWrapStyleWord(true);
    descT.setDoubleBuffered(true);
    descT.setBackground(dockC.getBackground());
    descT.setMinimumSize(new Dimension(80, 27));
    descT.setEditable(false);
    descT.setFont(new java.awt.Font("Dialog", 0, 10));
    leftP.setMinimumSize(new Dimension(215, 120));
    leftP.setPreferredSize(new Dimension(215, 120));
    actSysL.setText("test");
    rightP.setMinimumSize(new Dimension(260, 120));
    rightP.setPreferredSize(new Dimension(260, 120));
    this.add(topP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(userT, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(deptT, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel5, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(facL, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(waL, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel7, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel8, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel9, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(itemL, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(extPriceL, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel11, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel12, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(critCheck, new GridBagConstraints(7, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(noteB, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(descT, new GridBagConstraints(1, 2, 7, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    this.add(middleP, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    middleP.add(leftP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    leftP.add(leftBottomP, BorderLayout.CENTER);
    leftBottomP.add(chargeJSP, BorderLayout.CENTER);
    leftP.add(leftTopP, BorderLayout.NORTH);
    leftTopP.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    leftTopP.add(actSysL, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
    leftTopP.add(directRB, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    leftTopP.add(inDirectRB, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 70, 0, 0), 0, 0));
    middleP.add(rightP, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
    rightP.add(jLabel10, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    rightP.add(qtyT, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 3, 10), 0, 0));
    rightP.add(jLabel13, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    rightP.add(deliverByT, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 3, 3), 0, 0));
    rightP.add(jLabel14, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    rightP.add(dockC, new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 3, 3), 0, 0));
    rightP.add(jLabel15, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    rightP.add(deliverC, new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 3, 3), 0, 0));
    rightP.add(jLabel16, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    rightP.add(poC, new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 3, 3), 0, 0));
    rightP.add(poT, new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 3, 3), 0, 0));
    rightP.add(jLabel17, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    rightP.add(poDetailT, new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 30, 3, 3), 0, 0));

    unitPriceL.setText("PO Unit Price:");
    unitPriceT.setPreferredSize(new Dimension(50, 19));
    unitPriceT.setMaximumSize(new Dimension(50, 19));
    unitPriceT.setMinimumSize(new Dimension(50, 19));
    unitPriceT.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        unitPriceT_actionPreformed();
      }
    });
    currencyC.setPreferredSize(new Dimension(80, 19));
    currencyC.setMaximumSize(new Dimension(80, 19));
    currencyC.setMinimumSize(new Dimension(80, 19));

    rightP.add(unitPriceL, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    rightP.add(unitPriceT, new GridBagConstraints(1, 4, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 3, 3), 0, 0));
    rightP.add(currencyC, new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 3, 3), 0, 0));


    this.add(bottomP, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    bottomP.add(previousJSP, BorderLayout.CENTER);
  } //end of method

  void unitPriceT_actionPreformed() {
    try {
      if (qtyT.getText().trim().length() > 0 && unitPriceT.getText().trim().length() > 0) {
        double tmp = Double.parseDouble(unitPriceT.getText());
        double tmpQty = Double.parseDouble(qtyT.getText());
        extPriceL.setText((new Double(tmpQty*tmp)).toString()+" "+currencyC.getSelectedItem().toString());
      }else {
        extPriceL.setText("");
      }
    }catch(Exception e) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("PO Unit Price must be a real number. (Example 13.75)");
      gd.setVisible(true);
    }
  } //end of method


  //trong 3/25
  public void allowEdit(boolean f) {

  }

  public void setEngEvalMRData(Hashtable h) {
    this.engEvalMRInfo = h;
  }

  void setHeaderInfo() {
    Hashtable headerInfo = (Hashtable)engEvalMRInfo.get("HEADER_INFO");
    topP.setBorder(ClientHelpObjs.groupBox(" Header Info - Material Request: "+(String)headerInfo.get("PR_NUMBER")));
    userT.setText((String)headerInfo.get("END_USER"));
    deptT.setText((String)headerInfo.get("DEPARTMENT"));
    actSysL.setText((String)headerInfo.get("ACC_SYS_ID"));
    facL.setText((String)headerInfo.get("FACILITY_ID"));
    waL.setText((String)headerInfo.get("APPLICATION_DESC"));
    if ("442554".equalsIgnoreCase((String)headerInfo.get("ITEM_ID"))) {
      itemL.setText("");
    }else {
      itemL.setText((String)headerInfo.get("ITEM_ID"));
    }
    descT.setText((String)headerInfo.get("ITEM_DESC"));
    extPriceL.setText((String)headerInfo.get("EXTENDED_PRICE"));
    qtyT.setText((String)headerInfo.get("QTY"));
    critCheck.setSelected("Y".equalsIgnoreCase((String)headerInfo.get("CRITICAL")));
    if (!BothHelpObjs.isBlankString((String)headerInfo.get("NOTES"))) {
      noteB.setText("+");
    }else {
      noteB.setText("Click Here");
    }
    deliverByT.setText((String)headerInfo.get("NEED_DATE"));

    if ("d".equalsIgnoreCase((String)headerInfo.get("CHARGE_TYPE"))) {
      directRB.setSelected(true);
      inDirectRB.setSelected(false);
    }else {
      directRB.setSelected(false);
      inDirectRB.setSelected(true);
    }
    selectedDock = (String)headerInfo.get("SHIP_TO_LOCATION");
    selectedDeliveryPoint = (String)headerInfo.get("DELIVERY_POINT");
    chargeTypeChanged(true);

  } //end of method

  void setDockDeliverInfo() {
    loadingDetail = true;
    Hashtable dockDeliverInfo = (Hashtable)engEvalMRInfo.get("DELIVERY_INFO");
    Vector locationID = (Vector)engEvalMRInfo.get("LOCATION_ID");
    dockC = ClientHelpObjs.loadChoiceFromVector(dockC,(Vector)engEvalMRInfo.get("LOCATION_DESC"));
    String tmpLocationId = (String)locationID.elementAt(0);
    if (!BothHelpObjs.isBlankString(selectedDock)) {
      if (dockDeliverInfo.containsKey(selectedDock)) {
        tmpLocationId = selectedDock;
        dockC.setSelectedItem(getSelectedDockDescFromID(selectedDock));
      }
    }
    Vector delDesc = (Vector)dockDeliverInfo.get(tmpLocationId+"_DESC");
    if (delDesc.size() > 1) {
      Vector delID = (Vector)dockDeliverInfo.get(tmpLocationId+"_ID");
      if (!delID.contains("Select One")) {
        delID.insertElementAt("Select One",0);
        delDesc.insertElementAt("Select One",0);
      }
    }
    deliverC = ClientHelpObjs.loadChoiceFromVector(deliverC,delDesc);
    deliverC.setSelectedItem(getSelectedDeliveryToDescFromID(selectedDeliveryPoint));
    loadingDetail = false;
  }

  void dockC_actionPerformed(ActionEvent e) {
    if(loadingDetail)return;
    setDeliverToCombo();
  }

  void setDeliverToCombo() {
    String dock = this.getSelectedDockID();
    Hashtable dockDeliverInfo = (Hashtable)engEvalMRInfo.get("DELIVERY_INFO");
    Vector delDesc = (Vector)dockDeliverInfo.get(dock+"_DESC");
    if (delDesc.size() > 1) {
      Vector delID = (Vector)dockDeliverInfo.get(dock+"_ID");
      if (!delID.contains("Select One")) {
        delID.insertElementAt("Select One",0);
        delDesc.insertElementAt("Select One",0);
      }
    }
    if(deliverC.getItemCount() > 0) {
      deliverC.removeAllItems();
    }
    deliverC = ClientHelpObjs.loadChoiceFromVector(deliverC,delDesc);
    deliverC.setSelectedItem(getSelectedDeliveryToDescFromID(selectedDeliveryPoint));
  } //end of method


  String getSelectedDockID() {
    Vector locationID = (Vector)engEvalMRInfo.get("LOCATION_ID");
    return ((String)locationID.elementAt(dockC.getSelectedIndex()));
  }
  String getSelectedDeliveryToID() {
    Hashtable dockDeliverInfo = (Hashtable)engEvalMRInfo.get("DELIVERY_INFO");
    Vector deliverToID = (Vector)dockDeliverInfo.get(getSelectedDockID()+"_ID");
    return ((String)deliverToID.elementAt(deliverC.getSelectedIndex()));
  }

  String getSelectedDockDescFromID(String dockId) {
    Vector locationID = (Vector)engEvalMRInfo.get("LOCATION_ID");
    Vector locationDesc = (Vector)engEvalMRInfo.get("LOCATION_DESC");
    if (locationID.indexOf(dockId) >= 0) {
      return ( (String) locationDesc.elementAt(locationID.indexOf(dockId)));
    }else {
      return "";
    }
  }

  String getSelectedDeliveryToDescFromID(String deliveryPointDesc) {
    Hashtable dockDeliverInfo = (Hashtable)engEvalMRInfo.get("DELIVERY_INFO");
    String dockId = getSelectedDockID();
    Vector deliverToID = (Vector)dockDeliverInfo.get(dockId+"_ID");
    Vector deliverToDesc = (Vector)dockDeliverInfo.get(dockId+"_DESC");
    if (deliverToID.indexOf(deliveryPointDesc) >= 0) {
      return ( (String) deliverToID.elementAt(deliverToID.indexOf(deliveryPointDesc)));
    }else {
      return "";
    }
  }


  public void setPane(){
    //System.out.println("------- here in evalMRPane setPane");
    //first set tables columns, widths, and types
    chargeCols = (String[])engEvalMRInfo.get("CHARGE_COLS");
    chargeColWidths = (int[])engEvalMRInfo.get("CHARGE_COL_WIDTHS");
    chargeColTypes = (int[])engEvalMRInfo.get("CHARGE_COL_TYPES");
    preOrderCols = (String[])engEvalMRInfo.get("PREORDER_COLS");
    preOrderColWidths = (int[])engEvalMRInfo.get("PREORDER_COL_WIDTHS");
    preOrderColTypes = (int[])engEvalMRInfo.get("PREORDER_COL_TYPES");

    //next set header info
    setHeaderInfo();
    setDockDeliverInfo();

    //previous evaluation orders
    //bottomP.remove(previousJSP);
    buildPreOrderTable();
    //previousJSP = new JScrollPane(preOrderTable);
    //bottomP.add(previousJSP, BorderLayout.CENTER);
    //bottomP.validate();
  } //end of method

  void noteB_actionPerformed(ActionEvent e) {
    EditDlg RjW = new EditDlg(cmis.getMain(),"Enter Notes",true, "Enter notes:");
    Hashtable headerInfo = (Hashtable)engEvalMRInfo.get("HEADER_INFO");
    String tmp = (String)headerInfo.get("NOTES");
    if(!BothHelpObjs.isBlankString(tmp))RjW.setText(tmp);
    RjW.setVisible(true);
    if(!BothHelpObjs.isBlankString(RjW.getText())){
      headerInfo.put("NOTES",RjW.getText());
      noteB.setText("+");
    }else{
      noteB.setText("Click Here");
      headerInfo.put("NOTES","");
    }
    RjW.dispose();
  }    //end of method

  Vector getChargeNumbers() {
    if (chargeTable.isEditing()) {
      chargeTable.getCellEditor().stopCellEditing();
    }
    Vector chargeV = new Vector(chargeTable.getRowCount());
    for (int i = 0; i < chargeTable.getRowCount(); i++) {
      if (chargeTable.getColumnCount() == 3) {
        String[] oa = new String[3];
        String num1 = (String)chargeTable.getModel().getValueAt(i,0);
        String num2 = (String)chargeTable.getModel().getValueAt(i,1);
        String ps = (String)chargeTable.getModel().getValueAt(i,2);
        if (BothHelpObjs.isBlankString(num1) && BothHelpObjs.isBlankString(num2) && BothHelpObjs.isBlankString(ps)) {
          continue;
        }else {
          oa[0] = num1.trim();
          oa[1] = num2.trim();
          oa[2] = ps.trim();
        }
        chargeV.addElement(oa);
      }else {
        String[] oa = new String[2];
        String num1 = (String)chargeTable.getModel().getValueAt(i,0);
        String ps = (String)chargeTable.getModel().getValueAt(i,1);
        if (BothHelpObjs.isBlankString(num1) && BothHelpObjs.isBlankString(ps)) {
          continue;
        }else {
          oa[0] = num1.trim();
          oa[1] = ps.trim();
        }
        chargeV.addElement(oa);
      }
    }
    return chargeV;
  }   //end of method

  public String auditMR() {
    String result = "";
    //check qty
    try {
      String tmp = qtyT.getText();
      //BigDecimal test = new BigDecimal(tmp);
      int num = Integer.parseInt(tmp);
      //if(test.compareTo(new BigDecimal("0")) <= 0) {
      if (num <= 0) {
        return "Please enter a whole number greater than zero for qty.";
      }
    }catch (Exception e) {
      return "Please enter a whole number greater than zero for qty.";
    }

    //check charge number
    String type = "";
    if (directRB.isSelected()) {
      type = "d";
    }else {
      type = "i";
    }
    Hashtable headerInfo = (Hashtable)engEvalMRInfo.get("HEADER_INFO");
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable)headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String prAccountRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    String poRequired = (String)accSysTypeH.get("PO_REQUIRED");
    String validCharge1 = (String)accSysTypeH.get("VALID_1");
    String validCharge2 = (String)accSysTypeH.get("VALID_2");
    String unitPriceRequired = (String)accSysTypeH.get("UNIT_PRICE_REQUIRED");
    if ("y".equalsIgnoreCase(prAccountRequired)) {
      boolean containChargeNumber = false;
      Vector cn = getChargeNumbers();
      int cTotal = 0;
      //holder so I can check to see if user type in the same charge number
      Vector chargeNumberAuditV = new Vector(cn.size());
      for(int j=0;j<cn.size();j++){
        String[] oa = (String[])cn.elementAt(j);
        String num1 = "";
        String num2 = "";
        String ps = "";
        if (oa.length == 3) {
          num1 = oa[0];
          num2 = oa[1];
          ps = oa[2];
        }else {
          num1 = oa[0];
          ps = oa[1];
        }

        if(BothHelpObjs.isBlankString(ps)) {
          if (validCharge1.equalsIgnoreCase("y") || validCharge2.equalsIgnoreCase("y")) {
            continue;
          }
        }

        if (oa.length == 3) {
          if(BothHelpObjs.isBlankString(num1) && BothHelpObjs.isBlankString(num2)){
            if(BothHelpObjs.isBlankString(ps)){
              continue;
            }else{
              result = "You must enter a charge number with each percentage.";
              return result;
            }
          }
        }else {
          if(BothHelpObjs.isBlankString(num1)){
            if(BothHelpObjs.isBlankString(ps)){
              continue;
            }else{
              result = "You must enter a charge number with each percentage.";
              return result;
            }
          }
        }
        //users can't enter the same charge number twice
        if (chargeNumberAuditV.contains(num1+num2)) {
          result = "You entered the same charge number twice.\nPlease check your charge numbers and re-submit.";
          return result;
        }
        chargeNumberAuditV.addElement(num1+num2);

        int p = 0;
        try{
          p = Integer.parseInt(ps);
        }catch(Exception e){
          result = "You must enter only numbers in the percentage column.";
          return result;
        }
        if(p<0){
          result = "You cannot enter negative numbers in the percentage column.";
          return result;
        }
        cTotal = cTotal + p;
        //the flag below will tell me whether or not user enter some charge number
        if (num1.length() > 0 || num1.length() > 0) {
          containChargeNumber = true;
        }
      } //end of charge number for loop

      //now check to see whether or not validation required for charge number
      if (validCharge1.equalsIgnoreCase("y") || validCharge2.equalsIgnoreCase("y")) {
        if(cTotal != 100){
          result = "The sum of charge percentages must be 100.";
          return result;
        }
      }else {
        if (containChargeNumber) {
          if (cTotal != 100) {
            result = "The sum of charge percentages must be 100.";
            return result;
          }
        } //end of container charge number
      } //else charge number - valid not required
    } //end of charge number is required

    if ("p".equalsIgnoreCase(poRequired)) {
      if (poT.isVisible()) {
        String tmpPO = poT.getText().trim();
        if (tmpPO.length() < 1) {
          result = "Please enter a PO (30 chars maximum) for the given line item.";
          return result;
        }else {
          if (tmpPO.length() > 30) {
            result = "Your PO is more than 30 characters.\nPlease enter a PO (30 chars maximum) for the given line item.";
            return result;
          }
        }
      }else {
        if (BothHelpObjs.isBlankString((String)poC.getSelectedItem()) || poC.getSelectedItem().toString().startsWith("Select")) {
          result = "Please assign a PO to this request.";
          return result;
        }
      }
      //po unit price
      if ("y".equalsIgnoreCase(unitPriceRequired)) {
        try {
          double tmp = Double.parseDouble(unitPriceT.getText());
        }catch(Exception e) {
          result = "PO Unit Price must be a real number. (Example 13.75)";
          return result;
        }
        //currency
        if ("Select One".equalsIgnoreCase((String)currencyC.getSelectedItem())) {
          result = "Please select a currency for PO Unit Price.";
          return result;
        }
      } //po unit price
    } //end of PO is required

    // Making sure that the date entered is correct!!!
    String deliveryDate = this.deliverByT.getText();
    if (!BothHelpObjs.isDateFormatRight(deliveryDate)) {
      result = "The date you entered is invalid.\n Please check and re-enter another date\n in the following format: mm/dd/yyyy (i.e. 09/21/2003).";
      return result;
    }
    try {
      Date d = new Date();
      Calendar cal = Calendar.getInstance();
      String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
      long n1 =  d.parse(deliveryDate);
      long n2 = d.parse(cdate);
      cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
      if ( d.parse(cdate) > d.parse(deliveryDate)) {
        result = "The date you entered is in the past.\n Please check and re-enter another date.";
        return result;
      }
    }catch (Exception de) {
      result = "The date you entered is invalid.\n Please check and re-enter another date.";
      return result;
    }

    //make sure user select a delivery point
    if (deliverC.getSelectedItem().toString().startsWith("Select")) {
      result = "Please select a delivery point.";
      return result;
    }
    return result;
  }  //end of method

  public Hashtable buildData() {
    Hashtable MRInfoH = new Hashtable();
    MRInfoH.put("END_USER",userT.getText());
    MRInfoH.put("DEPARTMENT",deptT.getText());
    Hashtable headerInfo = (Hashtable)engEvalMRInfo.get("HEADER_INFO");
    MRInfoH.put("PR_NUMBER",(String)headerInfo.get("PR_NUMBER"));
    MRInfoH.put("NOTES",(String)headerInfo.get("NOTES"));
    MRInfoH.put("PACK",(Hashtable)headerInfo.get("PACK"));
    MRInfoH.put("ACC_SYS_ID",(String)headerInfo.get("ACC_SYS_ID"));
    MRInfoH.put("CRITICAL",new Boolean(critCheck.isSelected()));
    MRInfoH.put("QTY",qtyT.getText());
    MRInfoH.put("DOCK",getSelectedDockID());
    MRInfoH.put("DELIVER_TO",getSelectedDeliveryToID());
    MRInfoH.put("NEED_DATE",deliverByT.getText());
    MRInfoH.put("CHARGE_TYPE",directRB.isSelected()?"d":"i");
    //charge number
    if (chargeTable != null) {
      if (chargeTable.isVisible()) {
        MRInfoH.put("CHARGE_NUMBER", getChargeNumbers());
      }
    }
    //PO number
    if (poC.isVisible()) {
      MRInfoH.put("PO_NUMBER", (String) poC.getSelectedItem());
      MRInfoH.put("RELEASE_NUMBER", poDetailT.getText().trim());
    }else if (poT.isVisible()) {
      MRInfoH.put("PO_NUMBER", poT.getText().trim());
      MRInfoH.put("RELEASE_NUMBER", poDetailT.getText().trim());
    }
    MRInfoH.put("CATALOG_ID",nchem.partPP.getCatalogID());
    MRInfoH.put("CATALOG_COMPANY_ID",nchem.partPP.getCatalogCompanyId());
    //po unit price
    if (unitPriceT.isVisible()) {
      MRInfoH.put("PO_UNIT_PRICE",new BigDecimal(unitPriceT.getText().trim()));
      MRInfoH.put("PO_CURRENCY_ID",(String)currencyC.getSelectedItem());
    }

    return MRInfoH;
  }

  void buildEmptyChargeNumTable(){
    leftBottomP.setVisible(true);   //trong 7-19
    Hashtable tmph = (Hashtable)engEvalMRInfo.get("CHARGE_INFO"+"1");   //for now hardcode to line 1
    Hashtable headerInfo = (Hashtable)engEvalMRInfo.get("HEADER_INFO");
    Hashtable myH = (Hashtable)headerInfo.get("ACC_TYPE_H");
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    Hashtable innerH = (Hashtable)myH.get(accSysId);
    Hashtable accTypeH = (Hashtable)headerInfo.get("PACK");

    Vector chargeV = new Vector();
    Vector colHeader = new Vector();

    String numChargeTypes = (String)innerH.get("NUM_CHARGE_TYPES");
    Integer number = new Integer(numChargeTypes);
    String mt = "";
    if (number.intValue() == 2) {
      if (directRB.isSelected()) {
        mt = "d";
      }else {
        mt = "i";
      }
    }else {
      mt = "d";
    }
    String accType = accSysId + mt;
    Hashtable hh = (Hashtable)accTypeH.get(accType);
    colHeader = (Vector)hh.get("LABELS");

    chargeCols = new String[colHeader.size()];
    for(int i=0;i<colHeader.size();i++){
      chargeCols[i] = colHeader.elementAt(i).toString();
    }
    //setting cols widths
    if(colHeader.size() == 3){
      chargeColWidths = new int[]{85,90,80};
    }else{
      chargeColWidths = new int[]{175,80};
    }
    // determine the number of rows
    int numRows = 2;
    AttributiveCellTableModel model = new AttributiveCellTableModel(chargeCols,numRows);
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    for (int k = numRows -1; k >= 0; k--){
      model.removeRow(k);
    }
    // set all columns to empty strings
    for(int i=0;i<numRows;i++){
      Object[] oa = new Object[colHeader.size()];
      for (int j = 0 ; j < colHeader.size(); j++) {
        oa[j] = "";
      }
      model.addRow(oa);
      if (i % 2 == 0) {
        for (int k = 0; k < model.getColumnCount(); k++) {
          int[] cols = new int[]{k};
          int[] rows = new int[]{i};
          cellAtt.setBackground(new Color(224,226,250),rows,cols);
        }
      }
    }
    model.setColumnPrefWidth(chargeColWidths);
    model.setColumnType(chargeColTypes);
    chargeTable = new MultiSpanCellTable(model);
    chargeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    chargeTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] chargeRenderers = new AttributiveCellRendererLine[1];
    Hashtable catStyle = cmis.getMain().search.getCatTableStyle();
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    chargeRenderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = chargeTable.getColumnModel();
    int i = 0;
    for (i = 0; i < chargeTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(chargeRenderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    chargeTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),chargeRenderers[0].getBorder());
    Enumeration enum1 = chargeTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    chargeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<chargeTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      chargeTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      chargeTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        chargeTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        chargeTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    chargeTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseClicked(MouseEvent e) {
        //int selectedRow = chargeNumTable.getSelectedRow();
        //int selectedCol = chargeNumTable.getSelectedColumn();
        buildChargeTable();
        //setSelectedRowForChargeTable(selectedRow,selectedCol);
      }
    });
    leftBottomP.remove(chargeJSP);
    chargeJSP = new JScrollPane(chargeTable);
    leftBottomP.add(chargeJSP, BorderLayout.CENTER);
    leftBottomP.validate();
  } //end of method

  void chargeTypeChanged(boolean firstTime){
     //trong 7-19
    String type = "";
    Hashtable h = (Hashtable)engEvalMRInfo.get("CHARGE_INFO"+"1");   //for now hardcode to line 1
    String directedChargeD = (String)h.get("DIRECTED_TYPEd");
    String directedChargeI = (String)h.get("DIRECTED_TYPEi");
    if ("y".equalsIgnoreCase(directedChargeD) || "y".equalsIgnoreCase(directedChargeI)) {
      if ("y".equalsIgnoreCase(directedChargeD)) {
        directRB.setSelected(true);
        inDirectRB.setSelected(false);
        type = "d";
      }else {
        directRB.setSelected(false);
        inDirectRB.setSelected(true);
        type = "i";
      }
    }else {
      if (directRB.isSelected()) {
        type = "d";
      }else {
        type = "i";
      }
    }


    Hashtable headerInfo = (Hashtable)engEvalMRInfo.get("HEADER_INFO");
    //update charge_type
    headerInfo.put("CHARGE_TYPE",type);
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable)headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String prAccountRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    String poRequired = (String)accSysTypeH.get("PO_REQUIRED");
    String unitPriceRequired = (String)accSysTypeH.get("UNIT_PRICE_REQUIRED");
    if (prAccountRequired.equalsIgnoreCase("y")) {
      if (!firstTime) {
        buildEmptyChargeNumTable();
      }else {
        buildChargeTable();
      }
    }else {
      leftBottomP.setVisible(false);
      directRB.setVisible(false);
      inDirectRB.setVisible(false);
    }
    if (poRequired.equalsIgnoreCase("p")) {
      if (((Boolean)engEvalMRInfo.get("EDIT_PO")).booleanValue()) {
        String tmpPO = (String)headerInfo.get("PO_NUMBER");
        if (!BothHelpObjs.isBlankString(tmpPO)) {
          poT.setText(tmpPO);
        }else {
          poT.setText("");
        }
        poT.setVisible(true);
        poC.setVisible(false);
      }else {
        Vector v = (Vector)engEvalMRInfo.get("ALL_PO");
        if(!BothHelpObjs.isBlankString((String)headerInfo.get("PO_NUMBER"))){
          if(!v.contains((String)headerInfo.get("PO_NUMBER"))){
            v.addElement((String)headerInfo.get("PO_NUMBER"));
          }
        }
        v = BothHelpObjs.sort(v);
        if (poC.getItemCount() > 0) poC.removeAllItems();
        poC = ClientHelpObjs.loadChoiceFromVector(poC,v);
        if(!BothHelpObjs.isBlankString((String)headerInfo.get("PO_NUMBER"))){
          poC.setSelectedItem((String)headerInfo.get("PO_NUMBER"));
        }else{
          poC.setSelectedIndex(0);
        }
        poC.setVisible(true);    //trong 4/9
        poT.setVisible(false);
      }
      poC.setEditable(false);
      jLabel16.setVisible(true);
      poDetailT.setText((String)headerInfo.get("RELEASE_NUMBER"));
      poDetailT.setVisible(true);
      poDetailT.setEnabled(true);
      jLabel17.setVisible(true);
      //po unit price
      if ("y".equalsIgnoreCase(unitPriceRequired)) {
        Hashtable tmpCurrencyList = (Hashtable)headerInfo.get("CURRENCY_LIST");
        Vector currencyIDV = (Vector)tmpCurrencyList.get("CURRENCY_ID");
        currencyC.removeAllItems();
        if (!currencyIDV.contains("Select One")) {
          currencyIDV.insertElementAt("Select One", 0);
          ((Vector)tmpCurrencyList.get("CURRENCY_DESC")).insertElementAt("Select One",0);
          ((Vector)tmpCurrencyList.get("CONVERSION_FACTOR")).insertElementAt("Select One",0);
        }
        currencyC = ClientHelpObjs.loadChoiceFromVector(currencyC,currencyIDV);
        if (currencyIDV.contains((String)headerInfo.get("CURRENCY_ID"))) {
          currencyC.setSelectedItem((String)headerInfo.get("CURRENCY_ID"));
        }
        unitPriceT.setText((String)headerInfo.get("CATALOG_PRICE"));

        unitPriceL.setVisible(true);
        unitPriceT.setVisible(true);
        currencyC.setVisible(true);
      }else {
        unitPriceL.setVisible(false);
        unitPriceT.setVisible(false);
        currencyC.setVisible(false);
      }
    }else {
      poC.setVisible(false);  //trong 4/9
      jLabel16.setVisible(false);
      poDetailT.setVisible(false);
      jLabel17.setVisible(false);
      poT.setVisible(false);
      unitPriceL.setVisible(false);
      unitPriceT.setVisible(false);
      currencyC.setVisible(false);
    }
  }  //end of method

  void buildChargeTable() {
    //System.out.println("here in buildItemCatalogTable: ");
    Hashtable tmph = (Hashtable)engEvalMRInfo.get("CHARGE_INFO"+"1");   //for now hardcode to line 1
    Hashtable headerInfo = (Hashtable)engEvalMRInfo.get("HEADER_INFO");
    Hashtable myH = (Hashtable)headerInfo.get("ACC_TYPE_H");
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    Hashtable innerH = (Hashtable)myH.get(accSysId);
    Hashtable accTypeH = (Hashtable)headerInfo.get("PACK");
    String viewType = (String)headerInfo.get("PR_STATUS");

    Vector chargeV = new Vector();
    Vector colHeader = new Vector();

    //trong 4/7
    Integer editable = new Integer(0);
    String numChargeTypes = (String)innerH.get("NUM_CHARGE_TYPES");
    Integer number = new Integer(numChargeTypes);
    String directedChargeD = (String)tmph.get("DIRECTED_TYPEd");
    String directedChargeI = (String)tmph.get("DIRECTED_TYPEi");

    if( number.intValue()== 2) {
      if(directRB.isSelected()){
        if (viewType.equalsIgnoreCase("REQUEST")) {
          //if (tmph.get("DIRECTED_TYPEd").toString().equalsIgnoreCase("Y")) {
          if ("y".equalsIgnoreCase(directedChargeD)) {
            chargeV = (Vector)tmph.get("DIRECTED_CHARGE_NUMBERd");
          }else {
            chargeV = (Vector)tmph.get("CHARGE_NUM_DIRECT");
          }
        }else {
          chargeV = (Vector)tmph.get("CHARGE_NUM_DIRECT");
        }
        String accType = accSysId + "d";
        Hashtable hh = (Hashtable)accTypeH.get(accType);
        colHeader = (Vector)hh.get("LABELS");
        editable = (Integer)hh.get("EDIT_TABLE");
      }else{
        if (viewType.equalsIgnoreCase("REQUEST")) {
          //if (tmph.get("DIRECTED_TYPEi").toString().equalsIgnoreCase("Y")) {
          if ("y".equalsIgnoreCase(directedChargeI)) {
            chargeV = (Vector)tmph.get("DIRECTED_CHARGE_NUMBERi");
          }else {
            chargeV = (Vector)tmph.get("CHARGE_NUM_INDIRECT");
          }
        }else {
          chargeV = (Vector)tmph.get("CHARGE_NUM_INDIRECT");
        }
        String accType = accSysId + "i";
        Hashtable hh = (Hashtable)accTypeH.get(accType);
        colHeader = (Vector)hh.get("LABELS");
        editable = (Integer)hh.get("EDIT_TABLE");
      }

    }else{
      if (viewType.equalsIgnoreCase("REQUEST")) {
        //if (tmph.get("DIRECTED_TYPEd").toString().equalsIgnoreCase("Y")) {
        if ("y".equalsIgnoreCase(directedChargeD)) {
        chargeV = (Vector)tmph.get("DIRECTED_CHARGE_NUMBERd");
        }else {
          chargeV = (Vector)tmph.get("CHARGE_NUM_DIRECT");
        }
      }else {
        chargeV = (Vector)tmph.get("CHARGE_NUM_DIRECT");
      }
      String accType = accSysId + "d";
      Hashtable hh = (Hashtable)accTypeH.get(accType);
      colHeader = (Vector)hh.get("LABELS");
      editable = (Integer)hh.get("EDIT_TABLE");
    }

    chargeCols = new String[colHeader.size()];
    for(int i=0;i<colHeader.size();i++){
      chargeCols[i] = colHeader.elementAt(i).toString();
    }

    // determine the number of rows
    boolean emptyChargeTable = true;
    int numRows = 0;
    if(viewType.equalsIgnoreCase("REQUEST")){
      boolean canEditD = false;
      boolean canEditI = false;
      if( number.intValue()== 2) {
        canEditD = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        canEditI = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_I")).booleanValue();
        if (directRB.isSelected()) {
          if (canEditD) {
            numRows = 35;
          }else {
            numRows = chargeV.size();
            emptyChargeTable = false;
          }
        }else {
          if (canEditI) {
            numRows = 35;
          }else {
            numRows = chargeV.size();
            emptyChargeTable = false;
          }
        }
      }else {
        canEditD = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        if (canEditD) {
          numRows = 35;
        }else {
          numRows = chargeV.size();
          emptyChargeTable = false;
        }
      }
    }else{
      numRows = chargeV.size();
      emptyChargeTable = false;
    }

    AttributiveCellTableModel model = new AttributiveCellTableModel(chargeCols,numRows);
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    for (int k = numRows -1; k >= 0; k--){
      model.removeRow(k);
    }
    //decide whether to populate charge table with predefine charge number or use need to enter them
    if (emptyChargeTable) {
      // set all columns to empty strings
      for(int i=0;i<numRows;i++){
        Object[] oa = new Object[colHeader.size()];
        for (int j = 0 ; j < colHeader.size(); j++) {
          oa[j] = "";
        }
        model.addRow(oa);
        if (i % 2 == 0) {
          for (int k = 0; k < model.getColumnCount(); k++) {
            int[] cols = new int[]{k};
            int[] rows = new int[]{i};
            cellAtt.setBackground(new Color(224,226,250),rows,cols);
          }
        }
      }
    }else {
      for(int i=0;i<chargeV.size();i++){
        Hashtable h = (Hashtable)chargeV.elementAt(i);
        Object[] tmpoa = new Object[colHeader.size()];
        int r = 0;
        tmpoa[r++] = h.get("ACCT_NUM_1").toString();
        if(colHeader.size() == 3){
          tmpoa[r++] = h.get("ACCT_NUM_2").toString();
        }
        //if there is only one charge number then set default percent to 100
        if (chargeV.size() == 1) {
          tmpoa[r++] = "100";
        }else {
          tmpoa[r++] = h.get("PERCENTAGE").toString();
        }
        model.addRow(tmpoa);

        if (i % 2 == 0) {
          for (int k = 0; k < model.getColumnCount(); k++) {
            int[] cols = new int[]{k};
            int[] rows = new int[]{i};
            cellAtt.setBackground(new Color(224,226,250),rows,cols);
          }
        }
      }
    }
    //setting cols widths
    if(colHeader.size() == 3){
      chargeColWidths = new int[]{85,90,80};
    }else{
      chargeColWidths = new int[]{175,80};
    }
    //setting editable flag
    if(viewType.equalsIgnoreCase("REQUEST")){
      boolean bd = false;
      boolean bi = false;
      String myDI = "";
      String myDD = "";
      if( number.intValue()== 2) {
        bd = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        bi = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_I")).booleanValue();
        myDI = (String)tmph.get("DIRECTED_TYPEi");
        myDD = (String)tmph.get("DIRECTED_TYPEd");
        if (directRB.isSelected()) {
          if (bd) {
            if(chargeCols.length == 2) model.setEditableFlag(3);
            if(chargeCols.length == 3) model.setEditableFlag(editable.intValue());
          }else {
            if (myDD.equalsIgnoreCase("Y")) {
              model.setEditableFlag(0);
            }else {
              if(chargeCols.length == 2) model.setEditableFlag(2);
              if(chargeCols.length == 3) model.setEditableFlag(4);
            }
          }
        }else {
          if (bi) {
            if(chargeCols.length == 2) model.setEditableFlag(3);
            if(chargeCols.length == 3) model.setEditableFlag(editable.intValue());
          }else {
            if (myDI.equalsIgnoreCase("Y")) {
              model.setEditableFlag(0);
            }else {
              if(chargeCols.length == 2) model.setEditableFlag(2);
              if(chargeCols.length == 3) model.setEditableFlag(4);
            }
          }
        }
      }else {
        bd = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        myDD = (String)tmph.get("DIRECTED_TYPEd");
        if (bd) {
          if(chargeCols.length == 2) model.setEditableFlag(3);
          if(chargeCols.length == 3) model.setEditableFlag(editable.intValue());
        }else {
          if (myDD.equalsIgnoreCase("Y")) {
            model.setEditableFlag(0);
          }else {
            if(chargeCols.length == 2) model.setEditableFlag(2);
            if(chargeCols.length == 3) model.setEditableFlag(4);
          }
        }
      }
    }else{
      model.setEditableFlag(0);
    }
    //setting charge type
    if (number.intValue() == 2) {
      if (directRB.isSelected()) {
        directRB.setSelected(true);
        inDirectRB.setSelected(false);
      }else {
        inDirectRB.setSelected(true);
        directRB.setSelected(false);
      }
      directRB.setVisible(true);
      inDirectRB.setVisible(true);
      //disable charge type selection if work area uses directed charge
      if ("y".equalsIgnoreCase(directedChargeD) ||
          "y".equalsIgnoreCase(directedChargeI)) {
        directRB.setEnabled(false);
        inDirectRB.setEnabled(false);
        usesDirectedCharge = true;
      }else {
        usesDirectedCharge = false;
        directRB.setEnabled(true);
        inDirectRB.setEnabled(true);
      }
    }else {
      directRB.setSelected(true);
      inDirectRB.setSelected(false);
      directRB.setVisible(false);
      inDirectRB.setVisible(false);
    }

    model.setColumnPrefWidth(chargeColWidths);
    model.setColumnType(chargeColTypes);
    chargeTable = new MultiSpanCellTable(model);
    chargeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    chargeTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] chargeRenderers = new AttributiveCellRendererLine[1];
    Hashtable catStyle = cmis.getMain().search.getCatTableStyle();
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    chargeRenderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = chargeTable.getColumnModel();
    int i = 0;
    for (i = 0; i < chargeTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(chargeRenderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    chargeTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),chargeRenderers[0].getBorder());
    Enumeration enum1 = chargeTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    chargeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<chargeTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      chargeTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      chargeTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        chargeTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        chargeTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    leftBottomP.remove(chargeJSP);
    chargeJSP = new JScrollPane(chargeTable);
    leftBottomP.add(chargeJSP, BorderLayout.CENTER);
    leftBottomP.validate();
  }   //end of method

  void buildPreOrderTable() {
    //System.out.println("here in buildItemCatalogTable: ");
    Vector previousOrderV = (Vector)engEvalMRInfo.get("PREVIOUS_ORDERS");
    AttributiveCellTableModel model = new AttributiveCellTableModel(preOrderCols,previousOrderV.size());
    model = new AttributiveCellTableModel(preOrderCols,previousOrderV.size());
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    //remove row - perpare for coloring of alternate lines
    for (int k = previousOrderV.size() -1; k >= 0; k--){
      model.removeRow(k);
    }
    //fill table
    for (int i = 0; i < previousOrderV.size(); i++) {
      int[] rows = new int[]{i};
      String[] oa = (String[])previousOrderV.elementAt(i);
      model.addRow(oa);
      //color alternate row
      if (i % 2 == 0) {
        for (int k = 0; k < model.getColumnCount(); k++) {
          int[] cols = new int[]{k};
          cellAtt.setBackground(new Color(224,226,250),rows,cols);
        }
      }
    }
    model.setColumnPrefWidth(preOrderColWidths);
    model.setColumnType(preOrderColTypes);
    preOrderTable = new MultiSpanCellTable(model);
    preOrderTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    preOrderTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] preOrderRenderers = new AttributiveCellRendererLine[1];
    Hashtable catStyle = cmis.getMain().search.getCatTableStyle();
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    preOrderRenderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = preOrderTable.getColumnModel();
    int i = 0;
    for (i = 0; i < preOrderTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(preOrderRenderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    preOrderTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),preOrderRenderers[0].getBorder());
    Enumeration enum1 = preOrderTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    preOrderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<preOrderTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      preOrderTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      preOrderTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        preOrderTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        preOrderTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    bottomP.remove(previousJSP);
    previousJSP = new JScrollPane(preOrderTable);
    bottomP.add(previousJSP, BorderLayout.CENTER);
    bottomP.validate();
  }

  void directRB_actionPerformed(ActionEvent e) {
    if (directRB.isSelected()) {
      inDirectRB.setSelected(false);
    }else {
      directRB.setSelected(true);
    }
    chargeTypeChanged(false);
  }

  void inDirectRB_actionPerformed(ActionEvent e) {
    if (inDirectRB.isSelected()) {
      directRB.setSelected(false);
    }else {
      inDirectRB.setSelected(true);
    }
    chargeTypeChanged(false);
  }   //end of method

}   //end of class


