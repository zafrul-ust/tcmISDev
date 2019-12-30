
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.delivery;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.math.BigDecimal;
import java.util.*;
import java.text.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class DeliverySummary extends JDialog {
  JPanel panel1 = new JPanel();

  boolean submitted = false;
  boolean canceled = true;
  boolean gotoAction = false;
  boolean viewOnly = false;
  CmisApp cmis;
  Vector delInfo;
  String reqId ;
  String status;
  String type;
  String partNum;
  String itemId;
  String desc;
  BigDecimal qty;
  //CmisTable summaryTable;
  Calendar gotoCalendar;
  XYLayout xYLayout1 = new XYLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JButton newB = new JButton();
  JButton gotoB = new JButton();
  JButton deleteB = new JButton();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();
  DataJLabel descL = new DataJLabel();
  DataJLabel statusL = new DataJLabel();
  DataJLabel itemL = new DataJLabel();
  DataJLabel qtyScheduledL = new DataJLabel();
  DataJLabel reqIdL = new DataJLabel();
  DataJLabel typeL = new DataJLabel();
  DataJLabel qtyOrderedL = new DataJLabel();
  DataJLabel partNumL = new DataJLabel();
  StaticJLabel jLabel14 = new StaticJLabel();
  StaticJLabel jLabel18 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  StaticJLabel jLabel6 = new StaticJLabel();
  StaticJLabel jLabel8 = new StaticJLabel();
  StaticJLabel jLabel9 = new StaticJLabel();
  StaticJLabel jLabel1 = new StaticJLabel();
  XYLayout xYLayout2 = new XYLayout();
  Calendar firstActiveDay;

  int CALENDAR_COL = 0;
  int SCHEDULE_COL = 0;
  int SCHEDULE_QTY_COL = 0;
  int DELIVERED_COL = 0;
  int DELIVERED_QTY_COL = 0;
  int REVISED_QTY_COL = 0;
  int STATUS_COL = 0;
  int COMBINE_ROW_COL = 0;
  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable summaryTable;
  //DeliverySummaryTable summaryTable;

  public DeliverySummary(CmisApp cmis,
                         Vector delInfo,
                         String reqId,
                         String status,
                         String type,
                         String partNum,
                         String itemId,
                         String desc,
                         BigDecimal qty,
                         Calendar firstActiveDay,
                         boolean viewOnly,
                         boolean submitted) {
    super(cmis.getMain(), "Delivery Schedule Summary", true);
    this.cmis = cmis;
    this.delInfo = (Vector)delInfo.clone();
    this.reqId = reqId;
    this.status = status;
    this.type = type;
    this.partNum = partNum;
    this.itemId = itemId;
    this.desc = desc;
    this.qty = qty;
    this.firstActiveDay = firstActiveDay;
    this.viewOnly = viewOnly;
    this.submitted = submitted;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public boolean isCanceled(){
    return canceled;
  }

  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/add.gif");
    newB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/minus.gif");
    deleteB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/submit.gif");
    gotoB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setIcon(ss);
    setResizable(false);

    panel1.setLayout(xYLayout1);
    panel1.setMaximumSize(new Dimension(500, 360));
    panel1.setMinimumSize(new Dimension(500, 360));
    panel1.setPreferredSize(new Dimension(500, 360));
    jPanel1.setLayout(xYLayout2);
    jPanel2.setLayout(borderLayout1);
    jPanel3.setLayout(verticalFlowLayout1);
    newB.setText("New");
    newB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        newB_actionPerformed(e);
      }
    });
    gotoB.setText("Go To");
    gotoB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        gotoB_actionPerformed(e);
      }
    });
    deleteB.setText("Delete");
    deleteB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        deleteB_actionPerformed(e);
      }
    });
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    setHeaderLabels();
    buildTableNew();
    descL.setHorizontalTextPosition(SwingConstants.LEFT);
    statusL.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Item ID:");
    itemL.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel5.setText("Status:");
    jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel6.setText("Qty Scheduled:");
    qtyScheduledL.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel8.setText("Desc:");
    jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel9.setText("Request #:");
    typeL.setHorizontalTextPosition(SwingConstants.LEFT);
    qtyOrderedL.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel14.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel14.setText("Qty Ordered:");
    partNumL.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel18.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel18.setText("Part #:");
    jLabel1.setText("Type:");
    xYLayout1.setHeight(371);
    xYLayout1.setWidth(558);
    getContentPane().add(panel1);
    panel1.add(jPanel1, new XYConstraints(10, 10, 480, 90));
    jPanel1.add(descL, new XYConstraints(70, 59, -1, -1));
    jPanel1.add(jLabel6, new XYConstraints(351, 37, -1, -1));
    jPanel1.add(qtyScheduledL, new XYConstraints(430, 36, -1, -1));
    jPanel1.add(jLabel8, new XYConstraints(39, 60, -1, -1));
    jPanel1.add(itemL, new XYConstraints(430, 13, 56, -1));
    jPanel1.add(jLabel3, new XYConstraints(387, 14, -1, -1));
    jPanel1.add(partNumL, new XYConstraints(70, 36, 167, -1));
    jPanel1.add(jLabel18, new XYConstraints(33, 37, -1, -1));
    jPanel1.add(jLabel9, new XYConstraints(14, 14, -1, -1));
    jPanel1.add(reqIdL, new XYConstraints(70, 13, -1, -1));
    jPanel1.add(jLabel5, new XYConstraints(118, 14, -1, -1));
    jPanel1.add(statusL, new XYConstraints(156, 13, -1, -1));
    jPanel1.add(typeL, new XYConstraints(302, 13, -1, -1));
    jPanel1.add(jLabel14, new XYConstraints(238, 37, -1, -1));
    jPanel1.add(qtyOrderedL, new XYConstraints(300, 36, -1, -1));
    jPanel1.add(jLabel1, new XYConstraints(270, 14, -1, -1));
    panel1.add(jPanel2, new XYConstraints(10, 110, 355, 190));
    jPanel2.add(jsp, BorderLayout.CENTER);
    panel1.add(jPanel3, new XYConstraints(375, 110, 115, 190));
    jPanel3.add(gotoB, null);
    jPanel3.add(newB, null);
    jPanel3.add(deleteB, null);
    panel1.add(jPanel4, new XYConstraints(10, 310, 480, 40));
    jPanel4.add(okB, null);
    jPanel4.add(cancelB, null);
  }

  private void setHeaderLabels(){
    descL.setText(desc);
    statusL.setText(status);
    itemL.setText(itemId);
    qtyScheduledL.setText(getQtyScheduled().toString());
    reqIdL.setText(reqId);
    typeL.setText(type);
    qtyOrderedL.setText(qty.toString());
    partNumL.setText(partNum);
  }

  public boolean getGotoAction(){
    return gotoAction;
  }
  public Calendar getGotoCalendar(){
    return gotoCalendar;
  }
  private BigDecimal getQtyScheduled(){
    BigDecimal qty = new BigDecimal("0");
    String lastCombineRow = "";
    for(int i=0;i<delInfo.size();i++){
      Hashtable h = (Hashtable)delInfo.elementAt(i);
      //Integer I = new Integer(0);
      BigDecimal temp = new BigDecimal("0");
      //combine row
      if (h.containsKey("COMBINE_ROW")) {
        String currentCombineRow = (String)h.get("COMBINE_ROW");
        if (lastCombineRow.equalsIgnoreCase(currentCombineRow)) {     //ship to next row if it is a combine row
          lastCombineRow = currentCombineRow;
          continue;
        }else {
          lastCombineRow = currentCombineRow;
        }
      }
      if(submitted && h.containsKey("REVISED_QTY")){
        if (h.get("REVISED_QTY") != null && h.get("REVISED_QTY").toString().length() > 0) {
          temp = (BigDecimal)h.get("REVISED_QTY");
        }else {
          temp = (BigDecimal)h.get("QTY");
        }
      }else{
        if(h.get("QTY") != null) {
          temp = (BigDecimal)h.get("QTY");
        }
      }
      qty = qty.add(temp);
    }
    return qty;
  }
  private BigDecimal getQtyDelivered(){
    if(!submitted)
      return new BigDecimal("0");
    BigDecimal qty = new BigDecimal("0");
    for(int i=0;i<delInfo.size();i++){
      Hashtable h = (Hashtable)delInfo.elementAt(i);
      String qtyDelivered = (String)h.get("DELIVERED_QTY");
      if (qtyDelivered.length() > 0) {
        try {
          qty =  qty.add(new BigDecimal(qtyDelivered));
        }catch (Exception e) {
          //ignore
          System.out.println("ERROR ADDING QTY DELVIERED" + e.getMessage());
        }
      }else {
        //do nothing
      }
    }
    return qty;
  }
  private void zapZeros(){
    if(!submitted){
      for(int i=delInfo.size();i>0;i--){
        Hashtable h = (Hashtable)delInfo.elementAt(i-1);
        BigDecimal test = (BigDecimal)h.get("QTY");
        if(test.compareTo(new BigDecimal("0")) <= 0)
          delInfo.removeElementAt(i-1);
      }
    }
  }

  /*
  private void buildTable(){
    zapZeros();
    String[] colHead;
    String colTypeFlag;
    int[] colWidths;
    if(submitted){
      colHead = new String[]{"Schedule","Qty","Delivered","Qty","Revised Qty","Status","Real Qty"};
      colTypeFlag = "8888888";
      colWidths = new int[]{137,40,98,40,73,0,0};
      SCHEDULE_COL = 0;
      SCHEDULE_QTY_COL = 1;
      DELIVERED_COL = 2;
      DELIVERED_QTY_COL = 3;
      REVISED_QTY_COL = 4;
      STATUS_COL = 5;
      REAL_QTY_COL = 6;
    }else{
      colHead = new String[]{"Date","Qty"};
      colTypeFlag = "98";
      colWidths = new int[]{174,174};
      SCHEDULE_COL = 0;
      SCHEDULE_QTY_COL = 1;
    }
    Object[][] oa = new Object[delInfo.size()][colHead.length];
    for(int i=0;i<delInfo.size();i++){
      Hashtable h = (Hashtable)delInfo.elementAt(i);
      int col = 0;
      oa[i][col++] = (Calendar)h.get("DATE");
      oa[i][col++] = h.get("QTY").toString();
      if(submitted){
        oa[i][col++] = (String)h.get("DELIVERED");
        oa[i][col++] = (String)h.get("DELIVERED_QTY");
        if(h.containsKey("REVISED_QTY")){
          oa[i][col++] = h.get("REVISED_QTY").toString();
        }else{
          oa[i][col++] = "";
        }
        if(h.containsKey("STATUS")){
          oa[i][col++] = h.get("STATUS").toString();
        }else{
          oa[i][col++] = "";
        }
        oa[i][col++] = h.get("QTY").toString();       //real qty so I can compare users can't see this field
      }
    }

    DeliverySummaryTableModel ctm;
    if(delInfo.size() < 1){
      ctm = new DeliverySummaryTableModel(submitted,colHead);
    }else{
      ctm = new DeliverySummaryTableModel(submitted,colHead,oa);
    }
    if(viewOnly){
      ctm.setEditableFlag(0);
    }else if(submitted){
      ctm.setEditableFlag(0);     //this will be set upon click of revised qty column.
    }else{
      ctm.setEditableFlag(2);
    }
    /*
    TableSorter sorter = new TableSorter(ctm);
    summaryTable = new DeliverySummaryTable(sorter,this);
    sorter.setColTypeFlag(colTypeFlag);
    sorter.addMouseListenerToHeaderInTable(summaryTable);
    /
    summaryTable = new DeliverySummaryTable(ctm,this);

    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle =  BothHelpObjs.getTableStyle();
    Color color = (Color)chargeTableStyle.get("COLOR");
    Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
    Integer r1 = (Integer)chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r1.intValue()),a.intValue());
    //font and foreground and background color for columns heading
    String fontName = (String)chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
    summaryTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = summaryTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    CalendarCellRenderer ccr = new CalendarCellRenderer();
    ColorCellRenderer ctr = new ColorCellRenderer();
    summaryTable.setDefaultRenderer(Calendar.class, ccr);

    //summaryTable.setCellSelectionEnabled(false);
    //summaryTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    summaryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    summaryTable.getTableHeader().setReorderingAllowed(false);
    summaryTable.getTableHeader().setResizingAllowed(true);

    summaryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        tableSelectionChanged();
      }
    });
    //1-28-03
    if (submitted) {
      summaryTable.addMouseListener(new java.awt.event.MouseAdapter() {
       public void mouseClicked(MouseEvent e) {
          summaryTable_mouseClicked(e);
        }
      });
    }

    for(int i=0;i<colWidths.length;i++){
      summaryTable.getColumn(summaryTable.getModel().getColumnName(i)).setPreferredWidth(colWidths[i]);
    }
    //sorter.sortByColumn(0,true);
    jsp.getViewport().setView(summaryTable);
    setButtons();
  }*/

  void buildTableNew() {
    String[] colHead;
    int[] colTypes;
    int[] colWidths;
    if(submitted){
      colHead = new String[]{"Real Date","Schedule","Qty","Revised Qty","Status","Delivered","Del. Qty","Row"};
      colTypes = new int[]{BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
      colWidths = new int[]{0,85,50,65,0,85,50,0};
      CALENDAR_COL = 0;
      SCHEDULE_COL = 1;
      SCHEDULE_QTY_COL = 2;
      REVISED_QTY_COL = 3;
      STATUS_COL = 4;
      DELIVERED_COL = 5;
      DELIVERED_QTY_COL = 6;
      COMBINE_ROW_COL = 7;
    }else{
      colHead = new String[]{"Date","Schedule","Qty"};
      colTypes = new int[]{BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_DATE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
      colWidths = new int[]{0,174,174};
      CALENDAR_COL = 0;
      SCHEDULE_COL = 1;
      SCHEDULE_QTY_COL = 2;
    }
    modelDetail = new AttributiveCellTableModel(colHead,delInfo.size());
    for (int k = delInfo.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    //if there are records
    if (delInfo.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      String lastScheduleNQty = "";
      Vector combineRow = new Vector(20);
      for (int i = 0; i < delInfo.size(); i++) {
        Object[] oa = new Object[colHead.length];
        Hashtable h = (Hashtable)delInfo.elementAt(i);
        Calendar cal = (Calendar)h.get("DATE");
        oa[this.CALENDAR_COL] = cal;
        oa[this.SCHEDULE_COL] = BothHelpObjs.formatDate("toCharfromNum",(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
        oa[this.SCHEDULE_QTY_COL] = h.get("QTY").toString();
        String currentScheduleNQty = (cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR)+h.get("QTY").toString();
        if(submitted){
          oa[this.DELIVERED_COL] = (String)h.get("DELIVERED");
          oa[this.DELIVERED_QTY_COL] = (String)h.get("DELIVERED_QTY");
          if(h.containsKey("REVISED_QTY")){
            oa[this.REVISED_QTY_COL] = h.get("REVISED_QTY").toString();
          }else{
            oa[this.REVISED_QTY_COL] = "";
          }
          if(h.containsKey("STATUS")){
            oa[this.STATUS_COL] = h.get("STATUS").toString();
          }else{
            oa[this.STATUS_COL] = "";
          }
          if (h.containsKey("COMBINE_ROW")) {
            oa[this.COMBINE_ROW_COL] = h.get("COMBINE_ROW").toString();
          }else {
            oa[this.COMBINE_ROW_COL] = (new Integer(i)).toString();
          }

          //logic for combining row
          if (lastScheduleNQty.equalsIgnoreCase(currentScheduleNQty)) {
            Vector v = (Vector)combineRow.lastElement();
            v.addElement(new Integer(i));
          }else {
            Vector v = new Vector(10);
            v.addElement(new Integer(i));
            combineRow.addElement(v);
          }
          lastScheduleNQty = currentScheduleNQty;
        }
        if (i % 2 == 0) {
          int[] row = new int[]{i};
          for (int ii = this.DELIVERED_COL; ii < colHead.length; ii++) {
            int[] col = new int[]{ii};
            cellColorAtt.setBackground(new Color(224,250,226),row,col);
          }
        }else {
          int[] row = new int[]{i};
          for (int ii = this.DELIVERED_COL; ii < colHead.length; ii++) {
            int[] col = new int[]{ii};
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
        modelDetail.addRow(oa);
      } //end of for

      //combining general info
      for (int j = 0; j < combineRow.size(); j++) {
        Vector v = (Vector)combineRow.elementAt(j);
        int[] row = new int[v.size()];
        for (int k = 0; k < v.size(); k++) {
          Integer tmp = (Integer)v.elementAt(k);
          row[k] = tmp.intValue();
        }
        for (int i = 0; i < this.DELIVERED_COL; i++) {
          int[] col = new int[]{i};
          cellAtt.combine(row,col);
          if (j % 2 == 0 ) {
            cellColorAtt.setBackground(new Color(224,226,250),row,col);
          }else {
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
      }

      modelDetail.setColumnPrefWidth(colWidths);
      modelDetail.setColumnType(colTypes);
      summaryTable = new MultiSpanCellTable(modelDetail);
      jsp.getViewport().setView(summaryTable);
      summaryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      summaryTable.getTableHeader().setResizingAllowed(true);
      setTableStyle(modelDetail);

      summaryTable.addMouseListener(new java.awt.event.MouseAdapter() {
       public void mouseClicked(MouseEvent e) {
          summaryTable_mouseClicked(e);
       }
       public void mouseReleased(MouseEvent e) {
          summaryTable_mouseReleased(e);
       }
      });

      setButtons();
    } //end of if data
  } //end of Method

  protected int calculateReviseQty(int selRow) {
    int revisedQtyAllow = 0;
    try {
      String selCombineRow = (String)summaryTable.getModel().getValueAt(selRow,this.COMBINE_ROW_COL);
      int orderedQty = Integer.parseInt((String)summaryTable.getModel().getValueAt(selRow,this.SCHEDULE_QTY_COL));
      for (int i = 0; i < summaryTable.getRowCount(); i++) {
        String currentCombineRow = (String)summaryTable.getModel().getValueAt(i,this.COMBINE_ROW_COL);
        if (selCombineRow.equalsIgnoreCase(currentCombineRow)) {
          String delQty = (String)summaryTable.getModel().getValueAt(i,this.DELIVERED_QTY_COL);
          if (delQty.length() > 0) {
            revisedQtyAllow += Integer.parseInt(delQty);
          }
        }
      }
    }catch (NumberFormatException e) {
      revisedQtyAllow = 0;
    }
    return revisedQtyAllow;
  }

  protected void setTableStyle(AttributiveCellTableModel model) {
      //control by server
      //cell border
      summaryTable.setIntercellSpacing(new Dimension(0,0));
      AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];


      Hashtable hh = BothHelpObjs.getTableStyle();
      Color color = (Color)hh.get("COLOR");
      Integer t = (Integer)hh.get("INSET_TOP");
      Integer l = (Integer)hh.get("INSET_LEFT");
      Integer b = (Integer)hh.get("INSET_BOTTOM");
      Integer r = (Integer)hh.get("INSET_RIGHT");
      Integer a = (Integer)hh.get("INSET_ALIGN");
      renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());

      TableColumnModel m = summaryTable.getColumnModel();
      int i = 0;
      for (i = 0; i < summaryTable.getColumnCount(); i++) {
        m.getColumn(i).setCellRenderer(renderers[0]);
      }

      //font and foreground and background color for columns heading
      String fontName = (String)hh.get("FONT_NAME");
      Integer fontStyle = (Integer)hh.get("FONT_STYLE");
      Integer fontSize = (Integer)hh.get("FONT_SIZE");
      summaryTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)hh.get("FOREGROUND"),(Color)hh.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = summaryTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
      }

      // set column widths
      summaryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
      for(i=0;i<summaryTable.getColumnCount();i++){
        int width = model.getColumnWidth(i);
        summaryTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
        summaryTable.getColumn(model.getColumnName(i)).setWidth(width);
        if (width==0){
          summaryTable.getColumn(model.getColumnName(i)).setMinWidth(width);
          summaryTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
        }
      }
  }


  public Vector getSchedule(){
    zapZeros();
    return delInfo;
  }
  public void enteredNonNumber(){
    GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
    CenterComponent.centerCompOnScreen(gd);
    gd.setMsg("Quantity must be a number.");
    gd.setVisible(true);
  }
  void setQtyScheduled(){
    int qCol = 0;
    int q = 0;
    if(submitted){
      qCol = this.REVISED_QTY_COL;
      String lastCombineRow = "";
      for(int i=0;i<summaryTable.getModel().getRowCount();i++){
        //combine row
        String currentCombineRow = summaryTable.getModel().getValueAt(i,this.COMBINE_ROW_COL).toString();
        if (lastCombineRow.equalsIgnoreCase(currentCombineRow)) {     //ship to next row if it is a combine row
          lastCombineRow = currentCombineRow;
          continue;
        }else {
          lastCombineRow = currentCombineRow;
        }
        try{
          String tmpQty = summaryTable.getModel().getValueAt(i,qCol).toString();
          if (tmpQty.length() > 0) {
            q += Integer.parseInt(tmpQty);
          }else {
            q += Integer.parseInt(summaryTable.getModel().getValueAt(i,SCHEDULE_QTY_COL).toString());
          }
        }catch(Exception e){
          q += 0;
        }
      }
    }else{
      qCol = SCHEDULE_QTY_COL;
      for(int i=0;i<summaryTable.getModel().getRowCount();i++){
        try{
          int x = Integer.parseInt(summaryTable.getModel().getValueAt(i,qCol).toString());
          q += x;
        }catch(Exception e){
          q += 0;
        }
      }
    }
    qtyScheduledL.setText(String.valueOf(q));
  }
  private void tableSelectionChanged(){
    setButtons();
  }

  boolean auditRevisedQtyFailed() {
    boolean result = false;
    int rowFailed = 0;
    Calendar now = Calendar.getInstance();
    for(int i=0;i<summaryTable.getModel().getRowCount();i++){
      String qtyRevised = summaryTable.getModel().getValueAt(i,REVISED_QTY_COL).toString();
      if (qtyRevised.length() <= 0)  {
        continue;
      }else {
        Calendar scheduled = (Calendar)summaryTable.getModel().getValueAt(i,CALENDAR_COL);
        int qtyDelivered = calculateReviseQty(i);
        if (scheduled.getTime().getTime() < now.getTime().getTime()) {      //if past deliver date then
          try {
            int rev = Integer.parseInt(qtyRevised);
            if (qtyDelivered == 0) {           //user could only change qty to 0 (zero)
              if (rev != 0) {
                result = true;
                rowFailed = i;
                break;
              }
            }else {
              if (rev != qtyDelivered) {                         //user could only change qty to qty delivered
                result = true;
                rowFailed = i;
                break;
              }
            }
          }catch (NumberFormatException e) {
            result = true;
            rowFailed = i;
            break;
          }
        }
      }
    }

    if (result) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please check your data and try again.\nRevised qty must be equal to the delivered qty or 0 (zero).");
      gd.setVisible(true);
      summaryTable.getModel().setValueAt("",rowFailed,this.REVISED_QTY_COL);
      summaryTable.setRowSelectionInterval(rowFailed,rowFailed);
      summaryTable.repaint();
      summaryTable.validate();
      setQtyScheduled();
    }
    return result;
  }

  boolean auditScheduleQtyFailed() {
    boolean result = false;
    int qtyOr = 0;
    int qtyS = 0;
    try {
      qtyOr = Integer.parseInt(this.qtyOrderedL.getText());
      qtyS = Integer.parseInt(this.qtyScheduledL.getText());
      if (qtyOr < qtyS) {
        result = true;
      }
    }catch (Exception e) {
      result = false;
    }

    if (result) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please check your data and try again.\nQty Scheduled ("+qtyS+") must be equal to Original Qty Ordered ("+qtyOr+").");
      gd.setVisible(true);
    }
    return result;
  }

  void summaryTable_mouseReleased(MouseEvent e) {
    setButtons();
  }

  void summaryTable_mouseClicked(MouseEvent e) {
    if (submitted) {
      forceStopEditing();
      setButtons();
      setQtyScheduled();
      int row   = summaryTable.getSelectedRow();
      Point p = e.getPoint();
      int col = summaryTable.columnAtPoint(p);

      //make sure that users enter the correct revised qty
      if (auditRevisedQtyFailed()) {
        return;
      }
      saveSchedule();

      AttributiveCellTableModel ctm = (AttributiveCellTableModel)summaryTable.getModel();
      if (col == REVISED_QTY_COL) {
        Calendar scheduled = (Calendar)summaryTable.getModel().getValueAt(row,CALENDAR_COL);
        Calendar now = Calendar.getInstance();
        int qtyOrdered = 0;
        try {
          qtyOrdered = Integer.parseInt(summaryTable.getModel().getValueAt(row,SCHEDULE_QTY_COL).toString());
        }catch (NumberFormatException ee) {
          qtyOrdered = 0;
        }
        int qtyDelivered = calculateReviseQty(row);
        if (scheduled.getTime().getTime() < now.getTime().getTime()) {      //if past deliver date then
          if (qtyDelivered == qtyOrdered) {
            ctm.setEditableFlag(0);
          }else {
            if (qtyDelivered == 0) {
              ctm.setEditableFlag(BothHelpObjs.mypow(2,REVISED_QTY_COL));
            }else {
              ctm.setEditableFlag(BothHelpObjs.mypow(2,REVISED_QTY_COL));
            }
          }
        }else {         //user allow to change future qty
          ctm.setEditableFlag(BothHelpObjs.mypow(2,REVISED_QTY_COL));
        }
      }else {
        ctm.setEditableFlag(0);
      }
    }else {   //draft order
      setButtons();
      setQtyScheduled();
    }
  }

  private void goDelete(){
    // confirm deletion
    ConfirmDlg gd = new ConfirmDlg(cmis.getMain(),"Delete Delivery",true);
    CenterComponent.centerCompOnScreen(gd);
    String ds = summaryTable.getSelectedRowCount()>1?"deliveries":"delivery";
    gd.setMsg("Do you want to delete the selected "+ds+"?");
    gd.setVisible(true);
    if(!gd.getConfirmationFlag())return;

    //changes start here
    int row = summaryTable.getSelectedRow();
    Hashtable h = (Hashtable)delInfo.elementAt(row);
    if(submitted){
       if(h.containsKey("STATUS") && h.get("STATUS").toString().equalsIgnoreCase("added")){
         delInfo.removeElementAt(row);
       }else{
         h.put("STATUS","Changed");
         h.put("REVISED_QTY",new Integer(0));
         h.put("DELIVERED","");
         h.put("DELIVERED_QTY","");
         delInfo.setElementAt(h,row);
       }
    }else{
       delInfo.removeElementAt(row);
    }
    /*
    for(int j=summaryTable.getRowCount();j>0;j--){
      if(!summaryTable.isRowSelected(j-1))continue;
      Calendar c = (Calendar)summaryTable.getModel().getValueAt(j-1,CALENDAR_COL);
      int day = c.get(Calendar.DAY_OF_MONTH);
      int month = c.get(Calendar.MONTH);
      int year = c.get(Calendar.YEAR);
      for(int i=0;i<delInfo.size();i++){
        Hashtable h = (Hashtable)delInfo.elementAt(i);
        Calendar c1 = (Calendar)h.get("DATE");
        if(BothHelpObjs.sameDay(c,c1)){
           if(submitted){
             if(h.containsKey("STATUS") && h.get("STATUS").toString().equalsIgnoreCase("added")){
               delInfo.removeElementAt(i);
             }else{
               h.put("STATUS","Changed");
               h.put("REVISED_QTY",new Integer(0));
               h.put("DELIVERED","");
               h.put("DELIVERED_QTY","");
               delInfo.setElementAt(h,i);
             }
           }else{
             delInfo.removeElementAt(i);
           }
           break;
        }
      }
    } */

    buildTableNew();
    setQtyScheduled();
  }
  private void saveSchedule(){
    delInfo = new Vector();
    for(int i=0;i<summaryTable.getModel().getRowCount();i++){
      Hashtable h = new Hashtable();
      h.put("DATE",(Calendar)summaryTable.getModel().getValueAt(i,CALENDAR_COL));
      h.put("QTY",new BigDecimal(summaryTable.getModel().getValueAt(i,SCHEDULE_QTY_COL).toString()));
      if(submitted){
        h.put("STATUS",(String)summaryTable.getModel().getValueAt(i,this.STATUS_COL));
        if(summaryTable.getModel().getValueAt(i,this.REVISED_QTY_COL).toString().length() > 0) {
          h.put("REVISED_QTY",new BigDecimal(summaryTable.getModel().getValueAt(i,REVISED_QTY_COL).toString()));
        }
        h.put("DELIVERED",(String)summaryTable.getModel().getValueAt(i,this.DELIVERED_COL));
        h.put("DELIVERED_QTY",(String)summaryTable.getModel().getValueAt(i,this.DELIVERED_QTY_COL));
        h.put("COMBINE_ROW",(String)summaryTable.getModel().getValueAt(i,this.COMBINE_ROW_COL));
      }
      delInfo.addElement(h);
    }
  }
  private void setButtons(){
    //boolean multi = summaryTable.getSelectedRowCount
    if(viewOnly){
      gotoB.setEnabled(summaryTable.getSelectedRowCount() == 1);
      deleteB.setEnabled(false);
      newB.setEnabled(false);
      return;
    }
    if(summaryTable.getSelectedRowCount() > 0){
      gotoB.setEnabled(summaryTable.getSelectedRowCount() == 1);
      if(submitted){
        String combineRow = summaryTable.getValueAt(summaryTable.getSelectedRow(),this.COMBINE_ROW_COL).toString();
        boolean b = summaryTable.getValueAt((new Integer(combineRow)).intValue(),STATUS_COL).toString().equalsIgnoreCase("delivered");
        if(b){
          deleteB.setEnabled(false);
        }else{
          deleteB.setEnabled(summaryTable.getSelectedRowCount() >0);
        }
        //if order is completely filled
        if(getQtyDelivered().compareTo(qty) == 0){
          newB.setEnabled(false);
          deleteB.setEnabled(false);
        }
      }else{
        deleteB.setEnabled(summaryTable.getSelectedRowCount() >0);
      }
    }else{
      gotoB.setEnabled(false);
      deleteB.setEnabled(false);
    }
  }
  void forceStopEditing(){
    if(summaryTable.isEditing()){
      summaryTable.getCellEditor().stopCellEditing();
    }
  }
  void newB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    forceStopEditing();
    saveSchedule();
    DeliveryDayDlg ddd = new DeliveryDayDlg(cmis,partNumL.getText(),itemL.getText(),descL.getText(),firstActiveDay);
    CenterComponent.centerCompOnScreen(ddd);
    ddd.setVisible(true);
    if(ddd.isCanceled())return;
    try{
      Hashtable h = new Hashtable();
      h.put("DATE",ddd.getDay());
      if(submitted){
        h.put("QTY",new BigDecimal(0));
        h.put("STATUS","Added");
        h.put("REVISED_QTY", ddd.getQty());
        h.put("DELIVERED","");
        h.put("DELIVERED_QTY","");
        h.put("COMBINE_ROW",(new Integer(delInfo.size())).toString());
      }else{
        h.put("QTY", ddd.getQty());
      }
      delInfo.addElement(h);
    }catch(Exception ex){ex.printStackTrace();}
    buildTableNew();
    setQtyScheduled();
  }
  void deleteB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    forceStopEditing();
    goDelete();
  }
  void gotoB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    forceStopEditing();
    saveSchedule();
    gotoCalendar = (Calendar)summaryTable.getValueAt(summaryTable.getSelectedRow(),CALENDAR_COL);
    canceled = false;
    gotoAction = true;
    setVisible(false);
  }
  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    forceStopEditing();
    setQtyScheduled();
    if (submitted) {
      if (auditRevisedQtyFailed()) {
        return;
      }
    }
    if (auditScheduleQtyFailed()) {
      return;
    }

    saveSchedule();
    canceled = false;
    setVisible(false);
  }
  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    canceled = true;
    setVisible(false);
  }
}
class DeliverySummaryTable extends CmisTable{
  DeliverySummary ds;
  public DeliverySummaryTable(TableSorter ts, DeliverySummary ds){
    super(ts);
    this.ds = ds;
  }
  public DeliverySummaryTable(CmisTableModel ctm, DeliverySummary ds){
    super(ctm);
    this.ds = ds;
  }
  public void editingStopped(ChangeEvent e){
    int myRow = getEditingRow();
    int myCol = getEditingColumn();
    String oldVal = getModel().getValueAt(myRow,myCol).toString();
    super.editingStopped(e);
    String newVal = getModel().getValueAt(myRow,myCol).toString();
    try{
      if (newVal.length() > 0) {
        Integer I = new Integer(newVal);
      }
    }catch(Exception ex){
      ds.enteredNonNumber();
      getModel().setValueAt(oldVal,myRow,myCol);
    }
    ds.setQtyScheduled();
  }
}
class DeliverySummaryTableModel extends CmisTableModel{
  boolean submitted = false;
  public DeliverySummaryTableModel(boolean submitted, String[] s){
    super(s);
    this.submitted = submitted;
  }
  public DeliverySummaryTableModel(boolean submitted, String[] s,Object[][] oa){
    super(s,oa);
    this.submitted = submitted;
  }
  public boolean isCellEditable(int r, int c){
    if(!submitted)return super.isCellEditable(r,c);
    if(super.isCellEditable(r,c)){
      String s = this.getValueAt(r,2).toString();
      if(s.equalsIgnoreCase("delivered")){
        return false;
      }else{
        return true;
      }
    }
    return false;
  }
}
