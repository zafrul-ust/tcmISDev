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
import java.net.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.math.BigDecimal;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import com.borland.jbcl.layout.*;
public class POSPickWin extends JDialog {
  //Table
  final String[] searchCols = {"Inventory\nGroup"," \n ","Part\nNumber","Part\nGroup","Part\nDescription"," \nPrice","Total\nOrdered","Total\nPicked","Delivered\nDate"," \nType"," \nPackaging"," \nItem"," \nRID"," \nBin","Expiration\nDate","Current\nInventory","Qty\nPicked"," \nClose"," \nHub"," \nMR-Line"};
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  final int[] colWidths = {0,15,60,0,130,35,46,43,60,0,73,85,41,60,60,50,44,60,0,0};
  static final int INVENTORY_GROUP_COL = 0;
  static final int LINE_COL = 1;
  static final int PART_NUMBER_COL = 2;
  static final int PART_GROUP_COL = 3;
  static final int PART_DESC_COL = 4;
  static final int PRICE_COL = 5;
  static final int ORDERED_QTY_COL = 6;
  static final int PICKED_QTY_COL = 7;
  static final int DELIVER_DATE_COL = 8;
  static final int ITEM_TYPE_COL = 9;
  static final int PACKAGING_COL = 10;
  static final int ITEM_COL = 11;
  static final int RID_COL = 12;
  static final int BIN_COL = 13;
  static final int EXP_DATE_COL = 14;
  static final int QTY_ON_HAND_COL = 15;
  static final int ACTUAL_QTY_COL = 16;
  static final int CLOSE_COL = 17;
  static final int HUB_COL = 18;
  static final int MR_LINE_COL = 19;

  Frame parent;
  CmisApp grandParent;

  JScrollPane jPanel = new JScrollPane();
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton cancelB = new JButton();
  JButton okB = new JButton();

  StaticJLabel facL = new StaticJLabel();
  DataJLabel facT = new DataJLabel();
  StaticJLabel requestorL = new StaticJLabel();
  DataJLabel requestorT = new DataJLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  Hashtable posPickData = null;
  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String requestorName = "";
  String facilityName = "";
  StaticJLabel requestorLimitL = new StaticJLabel();
  DataJLabel requestorLimitT = new DataJLabel();
  StaticJLabel remainingLimitL = new StaticJLabel();
  DataJLabel remainingLimitT = new DataJLabel();
  String requestorLimit = "";
  String approverRequired = "";
  Vector pickReceiptData = null;

  public POSPickWin(Frame frame, String title, boolean modal, Hashtable data) {
    super(frame, title, modal);
    parent = frame;
    posPickData = data;
    requestorName = (String) data.get("CUSTOMER_NANE");
    facilityName = (String) data.get("FACILITY_ID");
    requestorLimit = (String) data.get("REQUESTOR_LIMIT");
    approverRequired = (String) data.get("APPROVER_REQUIRED");
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
    panel1.setMaximumSize(new Dimension(760, 470));
    panel1.setPreferredSize(new Dimension(760, 470));
    panel1.setMinimumSize(new Dimension(760, 470));
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    cancelB.setText("Close Window");
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
    okB.setText("Process");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    okB.setMaximumSize(new Dimension(100, 25));
    okB.setMinimumSize(new Dimension(100, 25));
    okB.setPreferredSize(new Dimension(100, 25));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));

    facL.setText("Facility: ");
    panel1.setLayout(gridBagLayout1);
    panel2.setLayout(new BorderLayout());
    requestorL.setText("Customer: ");
    requestorT.setText(requestorName);
    facT.setText(facilityName);
    requestorLimitL.setText("Cost Limit: ");
    requestorLimitT.setText(requestorLimit);
    remainingLimitL.setText("Remaining Limit: ");
    remainingLimitT.setText(requestorLimit);
    if (!"y".equalsIgnoreCase(approverRequired)) {
      requestorLimitL.setVisible(false);
      requestorLimitT.setVisible(false);
      remainingLimitL.setVisible(false);
      remainingLimitT.setVisible(false);
    }

    panel1.add(facL, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 5, 5), 0, 0));
    panel1.add(facT, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    panel1.add(requestorLimitL, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 260, 5, 5), 0, 0));
    panel1.add(requestorLimitT, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 325, 5, 5), 0, 0));
    panel1.add(remainingLimitL, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 260, 5, 5), 0, 0));
    panel1.add(remainingLimitT, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 360, 5, 5), 0, 0));
    panel1.add(requestorL, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 5, 5), 0, 0));
    panel1.add(requestorT, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    panel1.add(panel2, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 2, 5, 2), 0, 0));
    panel1.add(okB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 180, 9, 0), 0, 0));
    panel1.add(cancelB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 290, 9, 5), 0, 0));
    //build table
    buildTableNew();
    calculatePickedQtyNLimit();
    this.getContentPane().add(panel1);
    panel1.setBorder(ClientHelpObjs.groupBox(""));

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    this.pack();
    CenterComponent.centerCompOnScreen(this);
  }

  void buildTableNew() {
    Vector posPickInfo = (Vector) posPickData.get("PICK_INFO");
    //close this window no data to display
    if (posPickInfo.size() < 1) {
      GenericDlg gd = new GenericDlg(grandParent.getMain(),"No Data Found",true);
      gd.setMsg("No data found.");
      gd.setVisible(true);
      this.setVisible(false);
    }

    modelDetail = new AttributiveCellTableModel(searchCols,posPickInfo.size());
    for (int k = posPickInfo.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    int ed = BothHelpObjs.mypow(2,this.DELIVER_DATE_COL) + BothHelpObjs.mypow(2,this.ACTUAL_QTY_COL);
    modelDetail.setEditableFlag(ed);            //allow user to edit deliver date and actual qty columns
    if (posPickInfo.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      CellFont cellFont =(CellFont)modelDetail.getCellAttribute();
      String lastMRLine = "";
      String lastItemID = "";
      Vector combineRow = new Vector(50);
      Vector combineItemRow = new Vector(50);
      int combineCol = this.ITEM_TYPE_COL;
      Calendar cal = Calendar.getInstance();
      String deliverDate = (cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR);
      for (int i = 0; i < posPickInfo.size(); i++) {
        Object[] oa = new Object[searchCols.length];
        Hashtable h = (Hashtable)posPickInfo.elementAt(i);
        String currentMRLine = (String)h.get("MR_LINE");
        String currentItemID = (String)h.get("ITEM_ID");
        oa[this.INVENTORY_GROUP_COL] = (String)h.get("INVENTORY_GROUP");
        oa[this.LINE_COL] = (String)h.get("LINE");
        oa[this.PART_NUMBER_COL] = (String)h.get("CAT_PART_NO");
        oa[this.PART_GROUP_COL] = (String)h.get("PART_GROUP");
        oa[this.PART_DESC_COL] = (String)h.get("PART_DESC");
        oa[this.PRICE_COL] = (String)h.get("CATALOG_PRICE");
        oa[this.ORDERED_QTY_COL] = (String)h.get("ORDERED_QTY");
        oa[this.PICKED_QTY_COL] = "0";
        oa[this.DELIVER_DATE_COL] = deliverDate;
        String tmpItemType = (String)h.get("ITEM_TYPE");
        oa[this.ITEM_TYPE_COL] = tmpItemType;
        oa[this.ITEM_COL] = currentItemID+" ("+tmpItemType+")";
        oa[this.PACKAGING_COL] = (String)h.get("PACKAGING");
        oa[this.RID_COL] = (String)h.get("RID");
        oa[this.BIN_COL] = (String)h.get("BIN");
        oa[this.EXP_DATE_COL] = (String)h.get("EXP_DATE");
        oa[this.QTY_ON_HAND_COL] = (String)h.get("QTY_ON_HAND");
        oa[this.ACTUAL_QTY_COL] = (String)h.get("ACTUAL_QTY");
        if ("MD".equalsIgnoreCase(tmpItemType)) {
          oa[this.CLOSE_COL] = "Close";
        }else {
          oa[this.CLOSE_COL] = "";
        }
        oa[this.HUB_COL] = (String)h.get("HUB");
        oa[this.MR_LINE_COL] = currentMRLine;
        modelDetail.addRow(oa);

        //set cell font
        if ("MD".equalsIgnoreCase(tmpItemType) || "MP".equalsIgnoreCase(tmpItemType)) {
          if ("MD".equalsIgnoreCase(tmpItemType)) {
            //item ID and close
            int[] fontRows = {i};
            int[] fontCols = {ITEM_COL,CLOSE_COL};
            Font font = new Font(tmpItemType,Font.BOLD,10);
            cellFont.setFont(font, fontRows, fontCols);
          }else {
            //item ID
            Font font = new Font(tmpItemType,Font.BOLD,10);
            cellFont.setFont(font, i, ITEM_COL);
          }
        }
        //set row color
        if (i % 2 == 0) {
          int[] row = new int[]{i};
          for (int ii = combineCol; ii < searchCols.length; ii++) {
            int[] col = new int[]{ii};
            cellColorAtt.setBackground(new Color(224,250,226),row,col);
          }
        }else {
          int[] row = new int[]{i};
          for (int ii = combineCol; ii < searchCols.length; ii++) {
            int[] col = new int[]{ii};
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
        //set row color or line that does not have any inventory
        if (BothHelpObjs.isBlankString((String)h.get("QTY_ON_HAND"))) {
          int[] row = new int[]{i};
          for (int ii = 0; ii < searchCols.length; ii++) {
            int[] col = new int[]{ii};
            cellColorAtt.setBackground(new Color(224,170,180),row,col);
          }
        }

        //logic for combining row
        if (lastMRLine.equalsIgnoreCase(currentMRLine)) {
          //part combine
          Vector v = (Vector)combineRow.lastElement();
          v.addElement(new Integer(i));
          //item combine
          if (lastItemID.equalsIgnoreCase(currentItemID)) {
            Vector v2 = (Vector)combineItemRow.lastElement();
            v2.addElement(new Integer(i));
          }else {
            Vector v2 = new Vector(10);
            v2.addElement(new Integer(i));
            combineItemRow.addElement(v2);
          }
        }else {
          //part combine
          Vector v = new Vector(10);
          v.addElement(new Integer(i));
          combineRow.addElement(v);
          //item combine
          Vector v2 = new Vector(10);
          v2.addElement(new Integer(i));
          combineItemRow.addElement(v2);
        }
        lastMRLine = currentMRLine;
        lastItemID = currentItemID;
      } //end of for

      //combining part info
      combineCol = this.ITEM_TYPE_COL;
      for (int j = 0; j < combineRow.size(); j++) {
        Vector v = (Vector)combineRow.elementAt(j);
        int[] row = new int[v.size()];
        //setting combine rows
        for (int k = 0; k < v.size(); k++) {
          Integer tmp = (Integer)v.elementAt(k);
          row[k] = tmp.intValue();
        }
        //setting combine columns
        for (int i = 0; i < combineCol; i++) {
          int[] col = new int[]{i};
          cellAtt.combine(row,col);
          if (j % 2 == 0 ) {
            cellColorAtt.setBackground(new Color(224,226,250),row,col);
          }else {
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
      }
      //combining item info
      int combineItemCol = this.RID_COL;
      for (int j = 0; j < combineItemRow.size(); j++) {
        Vector v = (Vector)combineItemRow.elementAt(j);
        int[] row = new int[v.size()];
        //setting combine rows
        for (int k = 0; k < v.size(); k++) {
          Integer tmp = (Integer)v.elementAt(k);
          row[k] = tmp.intValue();
        }
        //setting combine columns
        for (int i = combineCol; i < combineItemCol; i++) {
          int[] col = new int[]{i};
          cellAtt.combine(row,col);
          if (j % 2 == 0 ) {
            cellColorAtt.setBackground(new Color(255,255,200),row,col);
          }else {
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
      }

      modelDetail.setColumnPrefWidth(colWidths);
      modelDetail.setColumnType(colTypes);
      displayTable = new MultiSpanCellTable(modelDetail);
      panel2.remove(jPanel);
      panel2.validate();
      jPanel = new JScrollPane(displayTable);
      panel2.add(jPanel, BorderLayout.CENTER);
      panel2.validate();
      displayTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      displayTable.getTableHeader().setResizingAllowed(true);
      displayTable.addMouseListener(new POS_table_mouseAdapter(this));
      setTableStyle(modelDetail);
    } //end of if data
  } //end of Method

  JComboBox getComboDetail(String sizeUnitOptions, String s) {
    JComboBox j = new JComboBox();
    Vector v = new Vector();
    if (BothHelpObjs.isBlankString(sizeUnitOptions)) {
      v.addElement("No Size Unit");
    }else {
      StringTokenizer st = new StringTokenizer(sizeUnitOptions,";");
      if (st.countTokens() == 1) {
        v.addElement(sizeUnitOptions);
      }else {
        while (st.hasMoreElements()) {
          v.addElement(st.nextToken());
        }
      }
    }
    if (v.size() > 1) {
      v.insertElementAt("Select One",0);
    }

    j = ClientHelpObjs.loadChoiceFromVector(j,v);
    if (v.contains(s)) {
      j.setSelectedItem(s);
    }else {
      j.setSelectedIndex(0);
    }
    j.setEditable(false);
    j.setMaximumRowCount(v.size());
    j.revalidate();
    return j;
  }

  protected void setTableStyle(AttributiveCellTableModel model) {
      //control by server
      //cell border
      displayTable.setIntercellSpacing(new Dimension(0,0));
      AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
      Hashtable hh = BothHelpObjs.getTableStyle();
      Color color = (Color)hh.get("COLOR");
      Integer t = (Integer)hh.get("INSET_TOP");
      Integer l = (Integer)hh.get("INSET_LEFT");
      Integer b = (Integer)hh.get("INSET_BOTTOM");
      Integer r = (Integer)hh.get("INSET_RIGHT");
      Integer a = (Integer)hh.get("INSET_ALIGN");
      renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());

      TableColumnModel m = displayTable.getColumnModel();
      int i = 0;
      for (i = 0; i < displayTable.getColumnCount(); i++) {
        m.getColumn(i).setCellRenderer(renderers[0]);
      }

      //font and foreground and background color for columns heading
      String fontName = (String)hh.get("FONT_NAME");
      Integer fontStyle = (Integer)hh.get("FONT_STYLE");
      Integer fontSize = (Integer)hh.get("FONT_SIZE");
      displayTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)hh.get("FOREGROUND"),(Color)hh.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = displayTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
      }

      // set column widths
      displayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
      for(i=0;i<displayTable.getColumnCount();i++){
        int width = model.getColumnWidth(i);
        displayTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
        displayTable.getColumn(model.getColumnName(i)).setWidth(width);
        if (width==0){
          displayTable.getColumn(model.getColumnName(i)).setMinWidth(width);
          displayTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
        }
      }
      displayTable.validate();
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    ConfirmNewDlg cdlg = new ConfirmNewDlg(this.grandParent.getMain(),"Confirmation", true);
    cdlg.setMsg("Closing this window will automatically\ncancel this material request.\nDo you want to continue?");
    cdlg.setVisible(true);
    if (!cdlg.yesFlag) {
      return;
    }else {
      cancelPickRequest();
    }
    this.setVisible(false);
  } //end of method

  void cancelPickRequest() {
    //stop editing table
    if (displayTable.isEditing()) {
      displayTable.getCellEditor().stopCellEditing();
    }
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","CANCEL_POS_PICK_DATA"); //String, String
      query.put("CLERK",grandParent.getUserId());  //String, Integer
      query.put("MR_LINE",(String)displayTable.getModel().getValueAt(0,MR_LINE_COL));
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        return;
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  Hashtable getCurrentData() {
    Hashtable h = new Hashtable();
    for (int i = 0; i < displayTable.getRowCount(); i++) {
      String mrLine = (String)displayTable.getModel().getValueAt(i,MR_LINE_COL);
      if (h.containsKey(mrLine)) {
        Vector v = (Vector)h.get(mrLine);
        Hashtable innerH = new Hashtable(7);
        innerH.put("ORDERED_QTY",(String)displayTable.getModel().getValueAt(i,ORDERED_QTY_COL));
        innerH.put("PICKED_QTY",(String)displayTable.getModel().getValueAt(i,PICKED_QTY_COL));
        innerH.put("ITEM_ID",(String)displayTable.getModel().getValueAt(i,ITEM_COL));
        innerH.put("RECEIPT_ID",(String)displayTable.getModel().getValueAt(i,RID_COL));
        innerH.put("ACTUAL_QTY",(String)displayTable.getModel().getValueAt(i,ACTUAL_QTY_COL));
        innerH.put("HUB",(String)displayTable.getModel().getValueAt(i,HUB_COL));
        innerH.put("DELIVER_DATE",(String)displayTable.getModel().getValueAt(i,DELIVER_DATE_COL));
        v.addElement(innerH);
      }else {
        Vector v = new Vector();
        Hashtable innerH = new Hashtable(7);
        innerH.put("ORDERED_QTY",(String)displayTable.getModel().getValueAt(i,ORDERED_QTY_COL));
        innerH.put("PICKED_QTY",(String)displayTable.getModel().getValueAt(i,PICKED_QTY_COL));
        innerH.put("ITEM_ID",(String)displayTable.getModel().getValueAt(i,ITEM_COL));
        innerH.put("RECEIPT_ID",(String)displayTable.getModel().getValueAt(i,RID_COL));
        innerH.put("ACTUAL_QTY",(String)displayTable.getModel().getValueAt(i,ACTUAL_QTY_COL));
        innerH.put("HUB",(String)displayTable.getModel().getValueAt(i,HUB_COL));
        innerH.put("DELIVER_DATE",(String)displayTable.getModel().getValueAt(i,DELIVER_DATE_COL));
        v.addElement(innerH);
        h.put(mrLine,v);
      }
    }
    return h;
  }

  boolean processPickRequest() {
    boolean returnVal = false;
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","PROCESS_POS_PICK_DATA"); //String, String
      query.put("CLERK",grandParent.getUserId());  //String, Integer
      query.put("PICKED_DATA",getCurrentData());
      query.put("HUB",(String)posPickData.get("HUB"));
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return false;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        return false;
      }
      //SHOW RECEIPT HERE
      pickReceiptData = (Vector)result.get("RECEIPT_DATA");
      posPickData.put("HUB_NAME",(String)result.get("HUB_NAME"));
      returnVal = true;
    }catch (Exception e) {
      e.printStackTrace();
    }
    return returnVal;
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    //stop editing table
    if (displayTable.isEditing()) {
      displayTable.getCellEditor().stopCellEditing();
    }
    //next calculate picked qty and cost limit
    if (!preProcessAudit()) {
      return;
    }
    //send process request to server
    if (!processPickRequest()) {
      return;
    }
    //show receipt window
    POSPickReport ppr = new POSPickReport(grandParent.getMain(),"Customer Receipt",true,pickReceiptData);
    ppr.setGrandParent(grandParent);
    ppr.setHub((String)posPickData.get("HUB"));
    ppr.setHubName((String)posPickData.get("HUB_NAME"));
    ppr.setInventoryGroup((String)posPickData.get("INVENTORY_GROUP"));
    ppr.setFacilityID((String)posPickData.get("FACILITY_ID"));
    ppr.loadIt();
    CenterComponent.centerCompOnScreen(ppr);
    ppr.setVisible(true);
    //close window
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    ConfirmNewDlg cdlg = new ConfirmNewDlg(this.grandParent.getMain(),"Confirmation", true);
    cdlg.setMsg("Closing this window will automatically\ncancel this material request.\nDo you want to continue?");
    cdlg.setVisible(true);
    if (!cdlg.yesFlag) {
      return;
    }else {
      cancelPickRequest();
    }
    setVisible(false);
  } //end of method

  boolean auditQty() {
    boolean result = true;
    for (int i = 0; i < displayTable.getRowCount(); i++) {
      String qtyPicked = (String)displayTable.getModel().getValueAt(i,ACTUAL_QTY_COL);
      if (!BothHelpObjs.isBlankString(qtyPicked)) {
        try {
          float f = Float.parseFloat(qtyPicked);
          if (f < 0) {
            GenericDlg.showMessage("Please enter a positive number for Qty Picked.");
            result = false;
            break;
          }
        }catch (Exception e) {
          e.printStackTrace();
          GenericDlg.showMessage("Please enter a positive number for Qty Picked.");
          result = false;
          break;
        }
      }
    }
    return result;
  }

  boolean auditDeliverDate() {
    boolean result = true;
    for (int i = 0; i < displayTable.getRowCount(); i++) {
      String deliverDate = (String)displayTable.getModel().getValueAt(i,DELIVER_DATE_COL);
      if (!BothHelpObjs.isBlankString(deliverDate)) {
        //first check to see if it is in the right format
        try {
          if (!BothHelpObjs.isDateFormatRight(deliverDate,"mm/dd/yyyy")) {
            GenericDlg.showMessage("Delivered Date is in the wrong format.\nPlease enter it in for following format mm/dd/yyyy.");
            result = false;
            break;
          }
        }catch (Exception e) {
          GenericDlg.showMessage("Delivered Date is in the wrong format.\nPlease enter it in for following format mm/dd/yyyy.");
          result = false;
          break;
        }
        try {
          //now check to make sure it is in the past
          String[] myDate = deliverDate.split("/");
          BigDecimal month = new BigDecimal(myDate[0]);
          BigDecimal day = new BigDecimal(myDate[1]);
          BigDecimal year = new BigDecimal(myDate[2]);
          Calendar today = Calendar.getInstance();
          if (year.intValue() == today.get(Calendar.YEAR)) {
            //the reason for the -1 is because calendar starts with 0 (Janurary)
            if ( (month.intValue() - 1) == today.get(Calendar.MONTH)) {
              if (day.intValue() > today.get(Calendar.DATE)) {
                GenericDlg.showMessage("Delivered Date you entered cannot be a future date.");
                result = false;
                break;
              }
            }else if  ( (month.intValue() - 1) > today.get(Calendar.MONTH)) {
              GenericDlg.showMessage("Delivered Date you entered cannot be a future date.");
              result = false;
              break;
            }
          }else if (year.intValue() > today.get(Calendar.YEAR)) {
            GenericDlg.showMessage("Delivered Date you entered cannot be a future date.");
            result = false;
            break;
          }
        }catch (Exception ee) {
          GenericDlg.showMessage("Delivered Date is in the wrong format.\nPlease enter it in for following format mm/dd/yyyy.");
          result = false;
          break;
        }
      }else {
        GenericDlg.showMessage("Please enter a Delivered Date.");
        result = false;
        break;
      }
    }
    return result;
  }


  boolean preProcessAudit() {
    //make sure deliver date <= today and format is correct
    if(!auditDeliverDate()) {
       return false;
    }
    //audit qty picked to make sure it is greater than zero
    if (!auditQty()) {
      return false;
    }
    //calculate picked qty and cost limit
    if (!calculatePickedQtyNLimit()) {
      return false;
    }
    //audit remaining amount
    if ("y".equalsIgnoreCase(approverRequired)) {
      if (!"Unlimited".equalsIgnoreCase(requestorLimit) && !"-1".equalsIgnoreCase(requestorLimit)) {
        try {
          float remainingAmt = Float.parseFloat(remainingLimitT.getText());
          if (remainingAmt < 0) {
            GenericDlg.showMessage("You have exceeded customer cost limit.\nPlease redo picked qty and try again.");
            return false;
          }
        }catch (Exception ee) {
          ee.printStackTrace();
          GenericDlg.showMessage("An error occurred while trying to audit remaining limt.");
          return false;
        }
      }
    }

    //next show picked message
    int firstIndex = 0;
    int lastIndex = firstIndex;
    String msg = "";
    do {
      int[] index = getGroupIndex(firstIndex);
      firstIndex = index[0];
      lastIndex = index[1];
      try {
        String mrLine = displayTable.getModel().getValueAt(firstIndex,MR_LINE_COL).toString();
        String orderedQty = displayTable.getModel().getValueAt(firstIndex,ORDERED_QTY_COL).toString();
        String pickedQty = displayTable.getModel().getValueAt(firstIndex,PICKED_QTY_COL).toString();
        String qtyAvailable = displayTable.getModel().getValueAt(firstIndex,QTY_ON_HAND_COL).toString();
        //make sure that clerk did not enter any qty to row with no inventory
        if (BothHelpObjs.isBlankString(qtyAvailable)) {
          String tmpActualQty = displayTable.getModel().getValueAt(firstIndex,ACTUAL_QTY_COL).toString();
          if (!BothHelpObjs.isBlankString(tmpActualQty)) {
            if (!"0".equalsIgnoreCase(tmpActualQty)) {
              GenericDlg.showMessage("You entered a qty for row with no inventory.\nPlease check data and try again.");
              return false;
            }
          }
        }

        float difQty = Float.parseFloat(orderedQty) - Float.parseFloat(pickedQty);
        //cleck picked more than ordered
        if (difQty < 0) {
          Float tmp = new Float(difQty*-1);
          msg += "For: "+mrLine+" you picked "+tmp.toString()+" more than qty ordered.\n";
        }
        //cleck picked less than ordered
        if (difQty > 0) {
          msg += "For: "+mrLine+"        "+difQty+" not picked.\n";
        }
      }catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showMessage("An error occurred while trying to process data.\nPlease check data and try again.");
        return false;
      }
      firstIndex = lastIndex+1;
    }while (firstIndex < displayTable.getRowCount());
    //show message
    if (msg.length() > 0) {
      ConfirmNewDlg md = new ConfirmNewDlg(grandParent.getMain(),"Warning",true);
      md.setMsg(msg);
      md.setVisible(true);
      if(!md.getConfirmationFlag()){
        return false;
      }
    }
    return true;
  } //end of method

  int[] getGroupIndex(int firstIndex) {
    int[] index = new int[2];
    int lastIndex = firstIndex;
    String selectedCatPartGroup = (String) displayTable.getModel().getValueAt(firstIndex, MR_LINE_COL);
    //determine where the first index going to start
    for (int i = firstIndex - 1; i >= 0; i--) {
      String tempCatPartGroup = (String) displayTable.getModel().getValueAt(i,MR_LINE_COL);
      if (selectedCatPartGroup.equals(tempCatPartGroup)) {
        firstIndex = i;
      }else {
        break;
      }
    }
    //determine where the last index going to end
    for (int i = firstIndex + 1; i < displayTable.getRowCount(); i++) {
      String tempCatPartGroup = (String) displayTable.getModel().getValueAt(i,MR_LINE_COL);
      if (selectedCatPartGroup.equals(tempCatPartGroup)) {
        lastIndex = i;
      }else {
        break;
      }
    }
    index[0] = firstIndex;
    index[1] = lastIndex;
    return index;
  } //end of method

  boolean calculatePickedQtyNLimit() {
    int firstIndex = 0;
    int lastIndex = firstIndex;
    do {
      int[] index = getGroupIndex(firstIndex);
      firstIndex = index[0];
      lastIndex = index[1];
      //now sum up the actual qty entered
      float pickedQty = 0;
      for (int k = firstIndex; k <= lastIndex; k++) {
        try {
          String tmp = displayTable.getValueAt(k,ACTUAL_QTY_COL).toString();
          if (!BothHelpObjs.isBlankString(tmp)) {
            pickedQty += Float.parseFloat(tmp);
          }
        }catch (Exception e) {
          //e.printStackTrace();
          GenericDlg.showMessage("Quantity picked has to be a positive number.");
          //set actual qty to blank when user enter an invalid number
          displayTable.setValueAt("",k,ACTUAL_QTY_COL);
          calculatePickedQtyNLimit();
          return false;
        }
      }
      //set picked qty
      String tmpVal = (new Float(pickedQty)).toString();
      displayTable.setValueAt(tmpVal,firstIndex,PICKED_QTY_COL);

      //if approver is required then check remaining limit
      if ("y".equalsIgnoreCase(approverRequired)) {
        float remainingAmt = 0;
        if (!"Unlimited".equalsIgnoreCase(requestorLimit) && !"-1".equalsIgnoreCase(requestorLimit)) {
          try {
            remainingAmt = Float.parseFloat(requestorLimit);
            String tmpPrice = displayTable.getValueAt(firstIndex,PRICE_COL).toString();
            if (!BothHelpObjs.isBlankString(tmpPrice)) {
              remainingAmt -= Float.parseFloat(tmpPrice) * pickedQty;
            }
          }catch (Exception ee) {
            ee.printStackTrace();
            remainingAmt -= 0;
          }
          remainingLimitT.setText((new Float(remainingAmt)).toString());
        }
      }
      firstIndex = lastIndex+1;
    }while (firstIndex < displayTable.getRowCount());
    return true;
  } //end of method

  void table_mouseClicked(MouseEvent e) {
    //Table clicked
    Point p = e.getPoint();
    int rowSel = displayTable.rowAtPoint(p);
    int colSel = displayTable.columnAtPoint(p);
    //repack or dispense item
    if (e.getClickCount() > 0 && colSel == ITEM_COL) {
      String tmpItemType = displayTable.getModel().getValueAt(rowSel,ITEM_TYPE_COL).toString();
      if ("MP".equalsIgnoreCase(tmpItemType) || "MD".equalsIgnoreCase(tmpItemType)) {
        goItemClicked(tmpItemType,displayTable.getModel().getValueAt(rowSel,colSel).toString(),displayTable.getModel().getValueAt(rowSel,INVENTORY_GROUP_COL).toString());
      }
    }
    //close item
    if (e.getClickCount() > 0 && colSel == CLOSE_COL) {
      String tmpVal = displayTable.getModel().getValueAt(rowSel,colSel).toString();
      if ("Close".equalsIgnoreCase(tmpVal)) {
        ConfirmNewDlg cdlg = new ConfirmNewDlg(this.grandParent.getMain(),"Confirmation", true);
        cdlg.setMsg("        *** Warning ***\nClosing this source: "+displayTable.getModel().getValueAt(rowSel,RID_COL).toString()+" will not make it\navaliable for dispensing/repackaging.");
        cdlg.setVisible(true);
        if (!cdlg.yesFlag) {
          return;
        }
        goCloseReceipt(rowSel);
      }
    }
    //next calculate picked qty
    calculatePickedQtyNLimit();

    int firstIndex = 0;
    int lastIndex = firstIndex;
    firstIndex = displayTable.getSelectedRow();
    //determining the first and last index
    int[] index = getGroupIndex(firstIndex);
    firstIndex = index[0];
    lastIndex = index[1];

    displayTable.setRowSelectionInterval(firstIndex,lastIndex);
    displayTable.repaint();
    displayTable.validate();

  } //end of method

  void goCloseReceipt(int rowSel) {
    if (displayTable.isEditing()) {
      displayTable.getCellEditor().stopCellEditing();
    }
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","POS_CLOSE_RECEIPT"); //String, String
      query.put("ITEM_ID",displayTable.getModel().getValueAt(rowSel,ITEM_COL).toString());
      query.put("INVENTORY_GROUP",displayTable.getModel().getValueAt(rowSel,INVENTORY_GROUP_COL).toString());
      query.put("RECEIPT_ID",displayTable.getModel().getValueAt(rowSel,RID_COL).toString());
      query.put("BIN",displayTable.getModel().getValueAt(rowSel,BIN_COL).toString());
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        return;
      }
      deleteReceipt(rowSel);
    }catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  void deleteReceipt(int row){
    if (displayTable.isEditing()) {
      displayTable.getCellEditor().stopCellEditing();
    }
    AttributiveCellTableModel model = (AttributiveCellTableModel)displayTable.getModel();
    model.removeRow(row);
  } //end of method

  void goItemClicked(String tmpItemType, String itemID, String inventoryGroup ) {
    itemID = itemID.substring(0,itemID.indexOf(" ("));
    if ("MP".equalsIgnoreCase(tmpItemType)) {
      POSRepackWin prw = new POSRepackWin(grandParent.getMain(),"Repack Item: "+itemID+"     - Inventory Group: "+inventoryGroup,true,itemID);
      prw.setGrandParent(grandParent);
      prw.setInventoryGroup(inventoryGroup);
      prw.loadIt();
      CenterComponent.centerCompOnScreen(prw);
      prw.setVisible(true);
      if (prw.doneFlag) {
        goRefreshTable();
      }
    }else {
      POSTapWin ptw = new POSTapWin(grandParent.getMain(),"Tap Item: "+itemID+"     - Inventory Group: "+inventoryGroup,true,itemID);
      ptw.setGrandParent(grandParent);
      ptw.setInventoryGroup(inventoryGroup);
      ptw.loadIt();
      CenterComponent.centerCompOnScreen(ptw);
      ptw.setVisible(true);
      if (ptw.doneFlag) {
        goRefreshTable();
      }
    }
  } //end of method

  void goRefreshTable() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","REFRESH_PICK_DATA"); //String, String
      query.put("PR_NUMBER",(String)posPickData.get("PR_NUMBER"));
      query.put("FACILITY_ID",(String)posPickData.get("FACILITY_ID"));
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        return;
      }
      posPickData.remove("PICK_INFO");
      posPickData.put("PICK_INFO",(Vector)result.get("PICK_DATA"));
      buildTableNew();
    }catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  void table_mouseReleased(MouseEvent e) {
    int firstIndex = 0;
    int lastIndex = firstIndex;
    Point p = e.getPoint();
    firstIndex = displayTable.rowAtPoint(p);
    //determining the first and last index
    int[] index = getGroupIndex(firstIndex);
    firstIndex = index[0];
    lastIndex = index[1];

    displayTable.setRowSelectionInterval(firstIndex,lastIndex);
    displayTable.repaint();
    displayTable.validate();

    //next calculate picked qty and cost limit
    //calculatePickedQtyNLimit();
  } //end of method

  void table_mousePressed(MouseEvent e) {
    int firstIndex = displayTable.getSelectedRow();
    int lastIndex = firstIndex;
    Point p = e.getPoint();
    firstIndex = displayTable.rowAtPoint(p);
    //determining the first and last index
    int[] index = getGroupIndex(firstIndex);
    firstIndex = index[0];
    lastIndex = index[1];

    displayTable.setRowSelectionInterval(firstIndex,lastIndex);
    displayTable.repaint();
    displayTable.validate();
  } //end of method

} //end of class

class POS_table_mouseAdapter extends java.awt.event.MouseAdapter {
  POSPickWin adaptee;

  POS_table_mouseAdapter(POSPickWin adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.table_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
    adaptee.table_mouseReleased(e);
  }

  public void mousePressed(MouseEvent e) {
    adaptee.table_mousePressed(e);
  }

} //end of method


