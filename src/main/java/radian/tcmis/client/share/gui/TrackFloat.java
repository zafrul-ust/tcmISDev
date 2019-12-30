
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description
//Rajput Changes 9/28/2001
//Changed logic to read data from DbTableModel received from server

package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
//import radian.tcmis.client.share.reports.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class TrackFloat extends JDialog {

  String so = null;
  String mRLine;

  //Raj
  JScrollPane JSP = new JScrollPane();
  DbTableModel ctmLeft  = new DbTableModel();
  DbTableModel ctmRight  = new DbTableModel();
  DbTableModel ctmCenter = new DbTableModel();
  TableSorter sorterTrack = new TableSorter();
  ColorCellRenderer colorTableRenderer = new ColorCellRenderer();
  CheckBoxCellRenderer checkTableRenderer = new CheckBoxCellRenderer();

  //

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton okB = new JButton();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel leftPane = new JPanel();
  JPanel rightPane = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane orderJsp = new JScrollPane();
  JScrollPane delJsp = new JScrollPane();
  BorderLayout borderLayout3 = new BorderLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  StaticJLabel jLabel4 = new StaticJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  StaticJLabel jLabel6 = new StaticJLabel();
  StaticJLabel jLabel7 = new StaticJLabel();
  StaticJLabel jLabel8 = new StaticJLabel();
  StaticJLabel jLabel9 = new StaticJLabel();
  StaticJLabel jLabel11 = new StaticJLabel();
  StaticJLabel jLabel12 = new StaticJLabel();
  StaticJLabel jLabel13 = new StaticJLabel();
  XYLayout xYLayout1 = new XYLayout();
  DataJLabel mrL = new DataJLabel();
  DataJLabel submitDateL = new DataJLabel();
  DataJLabel needByL = new DataJLabel();
  DataJLabel statusL = new DataJLabel();
  DataJLabel issueDateL = new DataJLabel();
  DataJLabel facL = new DataJLabel();
  DataJLabel partNumL = new DataJLabel();
  DataJLabel matDescL = new DataJLabel();
  DataJLabel requestorL = new DataJLabel();
  DataJLabel approverL = new DataJLabel();
  DataJLabel workAreaL = new DataJLabel();
  DataJLabel typeL = new DataJLabel();
  DataJLabel qtyL = new DataJLabel();

  CmisTable centerTable = new CmisTable();
  TableSorter sorterCenter = new TableSorter();

  CmisTable rightTable = new CmisTable();
  TableSorter sorterRight = new TableSorter();

  CmisTable leftTable = new CmisTable();
  TableSorter sorterLeft = new TableSorter();

  String[] colHeadsR = null;
  int[] colWidthsR = null;
  String colTypesR = null;
  String[] colHeadsC = null;
  int[] colWidthsC = null;
  String colTypesC = null;
  String[] colHeadsL = null;
  int[] colWidthsL = null;
  String colTypesL = null;

  static int QTY_R =0;
  static int SHIP_DATE_R =0;
  static int LOT_R =0;
  static int EXP_DATE_R =0;
  static int QTY_L =0;
  static int STATUS_L =0;
  static int EST_SHIP_DATE_L =0;
  static int NOTES_L =0;
  static int REAL_NOTES_L =0;
  static int DESCRIPTION_C =0;
  static int PACKAGING_C =0;
  static int GRADE_C =0;

  static final int MIN_MAX = 0;
  static final int OOR     = 1;
  static final int MIN_MAX_NONE = 2;
  static final int OOR_NONE     = 3;

  int type = -1;
  int itemID = -1;

  CmisApp cmis ;
  JScrollPane partJSP = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  XYLayout xYLayout4 = new XYLayout();
  JPanel cancelPanel = new JPanel();
  JLabel jLabel17 = new JLabel();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel18 = new JLabel();
  JLabel canByL = new JLabel();
  JLabel jLabel20 = new JLabel();
  JLabel canOnL = new JLabel();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JButton rptB = new JButton();
  StaticJLabel jlabel12 = new StaticJLabel();
  DataJLabel poL = new DataJLabel();

  Hashtable headerH = null;


  public TrackFloat(JFrame fr, String so,int type,int itemID, String mrLine) {
    // the TrackFloat cannot be modal or
    // the report module will not work
    // until the window is closed
    //super(fr, "Order Detail", true);
    super(fr, "MR Line Status", false);

    this.mRLine = mrLine;
    this.so = so;
    this.type = type;
    this.itemID = itemID;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(648, 514));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public TrackFloat(JFrame fr, Hashtable h) {
    // the TrackFloat cannot be modal or
    // the report module will not work
    // until the window is closed
    super(fr, "MR Line Status", false);
    try  {
      headerH = h;
      jbInit();
      pack();
      this.setSize(new Dimension(648, 514));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }


  public void setParent(CmisApp c){
    cmis = c;
  }

  public void loadItThread(){
    TrackFloatLoadThread x = new TrackFloatLoadThread(this);
    x.start();
  }

  void jbInit() throws Exception {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/search.gif");
    rptB.setIcon(ss);
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout2);
    leftPane.setLayout(borderLayout1);
    rightPane.setLayout(borderLayout2);
    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("MR#:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Status:");
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Need By:");
    jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel4.setText("Requester:");
    jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel5.setText("Submit Date:");
    jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel6.setText("Approver:");
    jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel7.setText("Part #:");
    jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel8.setText("Material Desc.:");
    jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel9.setText("Type:");
    jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel11.setText("Facility:");
    jLabel12.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel12.setText("Work Area:");
    jLabel13.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel13.setText("Issue Date:");
    //
    jPanel6.setLayout(xYLayout4);
    jLabel17.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel17.setForeground(Color.white);
    jLabel17.setText("Cancellation Requested");
    cancelPanel.setLayout(xYLayout2);
    jLabel18.setForeground(Color.white);
    jLabel18.setText("By:");
    canByL.setForeground(Color.white);
    canByL.setText("                 ");
    jLabel20.setForeground(Color.white);
    jLabel20.setText("On:");
    canOnL.setForeground(Color.white);
    canOnL.setText("               ");
    cancelPanel.setBackground(Color.darkGray);
    jPanel7.setLayout(borderLayout5);
    centerTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        centerTable_mouseClicked(e);
      }
    });
    rightTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        rightTable_mouseClicked(e);
      }
    });
    leftTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        leftTable_mouseClicked(e);
      }
    });
    rptB.setText("View Report");
    rptB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rptB_actionPerformed(e);
      }
    });
    jlabel12.setHorizontalAlignment(SwingConstants.RIGHT);
    jlabel12.setText("Quantity:");
    getContentPane().add(panel1);
    panel1.add(cancelPanel, BorderLayout.NORTH);
    cancelPanel.add(jLabel17, new XYConstraints(237, 4, -1, -1));
    cancelPanel.add(jLabel18, new XYConstraints(33, 21, 23, -1));
    cancelPanel.add(canByL, new XYConstraints(58, 21, 181, -1));
    cancelPanel.add(canOnL, new XYConstraints(446, 21, 183, -1));
    cancelPanel.add(jLabel20, new XYConstraints(416, 21, 26, -1));
    panel1.add(jPanel6, BorderLayout.EAST);
    jPanel6.add(jPanel1, new XYConstraints(-6, 0, 639, 100));
    //top
    jPanel1.add(jLabel1, new XYConstraints(4, 4, 75, -1));
    jPanel1.add(mrL, new XYConstraints(87, 3, 57, -1));
    jPanel1.add(jLabel5, new XYConstraints(193, 4, 79, -1));
    jPanel1.add(submitDateL, new XYConstraints(279, 4, -1, -1));
    jPanel1.add(jLabel2, new XYConstraints(443, 4, 43, -1));
    jPanel1.add(statusL, new XYConstraints(491, 4, 97, -1));
    //left
    jPanel1.add(jLabel4, new XYConstraints(21, 25, 75, -1));
    jPanel1.add(requestorL, new XYConstraints(102, 24, 150, -1));
    jPanel1.add(jLabel11, new XYConstraints(21, 43, 75, -1));
    jPanel1.add(facL, new XYConstraints(102, 42, 150, -1));
    jPanel1.add(jLabel12, new XYConstraints(21, 61, 75, -1));
    jPanel1.add(workAreaL, new XYConstraints(102, 60, 150, -1));
    jPanel1.add(jLabel7, new XYConstraints(21, 78, 75, -1));
    jPanel1.add(partNumL, new XYConstraints(102, 77, 160, -1));
    //jPanel1.add(jLabel8, new XYConstraints(4, 94, 75, -1));
    //jPanel1.add(matDescL, new XYConstraints(86, 93, 275, -1));
    //right
    jPanel1.add(jLabel3, new XYConstraints(359, 26, 50, -1));
    jPanel1.add(needByL, new XYConstraints(415, 25, 186, -1));
    jPanel1.add(jLabel6, new XYConstraints(359, 44, 50, -1));
    jPanel1.add(approverL, new XYConstraints(415, 43, 189, -1));
    jPanel1.add(jLabel9, new XYConstraints(359, 62, 50, -1));
    jPanel1.add(typeL, new XYConstraints(415, 61, 190, -1));
    jPanel1.add(jlabel12, new XYConstraints(354, 79, 55, -1));
    jPanel1.add(qtyL, new XYConstraints(415, 78, 47, -1));
    //jPanel1.add(jlabel12, new XYConstraints(388, 96, 21, 10));
    //jPanel1.add(qtyL, new XYConstraints(412, 93, 125, -1));
    //part desc
    //jPanel6.add(jPanel7, new XYConstraints(3, 115, 620, 104));
    jPanel6.add(jPanel7, new XYConstraints(3, 105, 620, 160));
    jPanel7.add(partJSP, BorderLayout.CENTER);
    //order and deliery status
    jPanel6.add(jPanel2, new XYConstraints(-2, 256, 638, 193));
    jPanel2.add(leftPane, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    leftPane.add(orderJsp, BorderLayout.CENTER);
    jPanel2.add(rightPane, new GridBagConstraints2(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    rightPane.add(delJsp, BorderLayout.CENTER);
    jPanel6.add(okB, new XYConstraints(282, 450, -1, 35));

    // keep cancel panel down
    cancelPanel.setVisible(false);
  }

  void loadItAction() {
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("TrackObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_FLOAT_INFO"); //String, String
      query.put("MR_LINE",(String)headerH.get("MR_LINE"));  //String, String
      query.put("ITEM_ID",(String)headerH.get("ITEM_ID")); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      Hashtable searchData = (Hashtable) result.get("SEARCH_DATA");
      Hashtable centerH = (Hashtable) searchData.get("PART_DESC");
      Hashtable leftH   = (Hashtable) searchData.get("ORDER_STATUS");
      Hashtable rightH  = (Hashtable) searchData.get("DELIVERY_STATUS");
      AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
      Hashtable tableStyle = (Hashtable)result.get("TABLE_STYLE");
      Color color = (Color)tableStyle.get("COLOR");
      Integer t = (Integer)tableStyle.get("INSET_TOP");
      Integer l = (Integer)tableStyle.get("INSET_LEFT");
      Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
      Integer r = (Integer)tableStyle.get("INSET_RIGHT");
      Integer a = (Integer)tableStyle.get("INSET_ALIGN");
      renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
      //font and foreground and background color for columns heading
      String fontName = (String)tableStyle.get("FONT_NAME");
      Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
      Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
      //
      // cancel
      boolean canceled = ((BothHelpObjs.makeBlankFromNull((String) headerH.get("CANCEL_REQUEST"))).length()>0);
      if (canceled){
          canOnL.setText(BothHelpObjs.makeBlankFromNull((String) headerH.get("CANCEL_REQUEST")));
          canByL.setText(BothHelpObjs.makeBlankFromNull((String) headerH.get("CANCEL_REQUESTOR")));
          cancelPanel.setVisible(true);
          this.setSize(new Dimension(648, 560));
          this.repaint();
      }
      // header info
      mrL.setText((String) headerH.get("MR_LINE"));
      submitDateL.setText((String) headerH.get("SUBMIT_DATE"));
      statusL.setText((String) headerH.get("STATUS"));
      requestorL.setText((String) headerH.get("REQUESTOR"));
      needByL.setText((String) headerH.get("NEED_DATE"));
      facL.setText((String) headerH.get("FACILITY"));
      approverL.setText((String) headerH.get("APPROVER"));
      workAreaL.setText((String) headerH.get("WORK_AREA"));
      typeL.setText((String) headerH.get("TYPE"));
      partNumL.setText((String) headerH.get("PART_NO"));
      qtyL.setText(BothHelpObjs.makeBlankFromNull((String) headerH.get("QUANTITY")));
      int totalDel = BothHelpObjs.makeZeroFromNull((String) headerH.get("DELIVERED"));

      //Left Table
      leftPane.setBorder(ClientHelpObjs.groupBox("Order Status"));
      Hashtable keyColsL = (Hashtable) leftH.get("KEY_COLS");
      NOTES_L = Integer.parseInt((String) keyColsL.get("Notes"));
      REAL_NOTES_L = Integer.parseInt((String) keyColsL.get("Real Notes"));
      QTY_L = Integer.parseInt((String) keyColsL.get("Qty"));
      STATUS_L = Integer.parseInt((String) keyColsL.get("Status"));
      EST_SHIP_DATE_L = Integer.parseInt((String) keyColsL.get("Est. Ship Date"));
      buildLeftTableModel(leftH);
      sorterLeft = new TableSorter(ctmLeft);
      sorterLeft.setColTypeFlag(ctmLeft.getColumnTypesString());
      leftTable = new CmisTable(sorterLeft);
      //table style
      TableColumnModel cmLeft = leftTable.getColumnModel();
      for (int i = 0; i < leftTable.getColumnCount(); i++) {
        cmLeft.getColumn(i).setCellRenderer(renderers[0]);
      }
      leftTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRendererLeft = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enumLeft = leftTable.getColumnModel().getColumns();
      while (enumLeft.hasMoreElements()) {
        ((TableColumn)enumLeft.nextElement()).setHeaderRenderer(tableRendererLeft);
      }
      for(int i=0;i<leftTable.getColumnCount();i++){
         int width = ctmLeft.getColumnWidth(i);
         leftTable.getColumn(ctmLeft.getColumnName(i)).setPreferredWidth(width);
         leftTable.getColumn(ctmLeft.getColumnName(i)).setWidth(width);
         if (width==0){
           leftTable.getColumn(ctmLeft.getColumnName(i)).setMinWidth(width);
           leftTable.getColumn(ctmLeft.getColumnName(i)).setMaxWidth(width);
         }
      }
      //end
      sorterLeft.addMouseListenerToHeaderInTable(leftTable);
      setTableL();
      leftTable.revalidate();
      orderJsp.getViewport().setView(leftTable);
      orderJsp.revalidate();

      // delivery status - Right Table
      rightPane.setBorder(ClientHelpObjs.groupBox("Delivery Status - ( "+totalDel+" of "+qtyL.getText()+" )"));
      buildRightTableModel(rightH);
      sorterRight = new TableSorter(ctmRight);
      sorterRight.setColTypeFlag(ctmRight.getColumnTypesString());
      rightTable = new CmisTable(sorterRight);
      //table style
      TableColumnModel cmRight = rightTable.getColumnModel();
      for (int i = 0; i < rightTable.getColumnCount(); i++) {
        cmRight.getColumn(i).setCellRenderer(renderers[0]);
      }
      rightTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRendererRight = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enumRight = rightTable.getColumnModel().getColumns();
      while (enumRight.hasMoreElements()) {
        ((TableColumn)enumRight.nextElement()).setHeaderRenderer(tableRendererRight);
      }
      for(int i=0;i<rightTable.getColumnCount();i++){
         int width = ctmRight.getColumnWidth(i);
         rightTable.getColumn(ctmRight.getColumnName(i)).setPreferredWidth(width);
         rightTable.getColumn(ctmRight.getColumnName(i)).setWidth(width);
         if (width==0){
           rightTable.getColumn(ctmRight.getColumnName(i)).setMinWidth(width);
           rightTable.getColumn(ctmRight.getColumnName(i)).setMaxWidth(width);
         }
      }
      sorterRight.addMouseListenerToHeaderInTable(rightTable);
      setTableR();
      Hashtable keyColsR = (Hashtable) rightH.get("KEY_COLS");
      QTY_R = Integer.parseInt((String) keyColsR.get("Qty"));
      SHIP_DATE_R = Integer.parseInt((String) keyColsR.get("Ship Date"));
      LOT_R = Integer.parseInt((String) keyColsR.get("Lot"));
      EXP_DATE_R = Integer.parseInt((String) keyColsR.get("Exp. Date"));
      rightTable.revalidate();
      delJsp.getViewport().setView(rightTable);
      delJsp.revalidate();

      //Center Table
      jPanel7.setBorder(ClientHelpObjs.groupBox("Part Description"));
      Hashtable keyColsC = (Hashtable) centerH.get("KEY_COLS");
      DESCRIPTION_C = Integer.parseInt((String) keyColsC.get("Description"));
      PACKAGING_C = Integer.parseInt((String) keyColsC.get("Packaging"));
      GRADE_C = Integer.parseInt((String) keyColsC.get("Grade"));
      buildCenterTableModel(centerH);
      sorterCenter = new TableSorter(ctmCenter);
      sorterCenter.setColTypeFlag(ctmCenter.getColumnTypesString());
      centerTable = new CmisTable(sorterCenter);
      //table style
      TableColumnModel cmCenter = centerTable.getColumnModel();
      for (int i = 0; i < centerTable.getColumnCount(); i++) {
        cmCenter.getColumn(i).setCellRenderer(renderers[0]);
      }
      centerTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRendererCenter = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enumCenter = centerTable.getColumnModel().getColumns();
      while (enumCenter.hasMoreElements()) {
        ((TableColumn)enumCenter.nextElement()).setHeaderRenderer(tableRendererCenter);
      }
      for(int i=0;i<centerTable.getColumnCount();i++){
         int width = ctmCenter.getColumnWidth(i);
         centerTable.getColumn(ctmCenter.getColumnName(i)).setPreferredWidth(width);
         centerTable.getColumn(ctmCenter.getColumnName(i)).setWidth(width);
         if (width==0){
             centerTable.getColumn(ctmCenter.getColumnName(i)).setMinWidth(width);
             centerTable.getColumn(ctmCenter.getColumnName(i)).setMaxWidth(width);
         }
      }
      sorterCenter.addMouseListenerToHeaderInTable(centerTable);
      setTableC();
      centerTable.revalidate();
      partJSP.getViewport().setView(centerTable);
      partJSP.revalidate();

      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
  } //end of method

  void buildCenterTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    ctmCenter = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      ctmCenter.addRow((Object[]) data.elementAt(i));
    }
    ctmCenter.setColumnPrefWidth(colWidths);
    ctmCenter.setColumnType(colTypes);
  }  //end of method

  void buildLeftTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    ctmLeft = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      ctmLeft.addRow((Object[]) data.elementAt(i));
    }
    ctmLeft.setColumnPrefWidth(colWidths);
    ctmLeft.setColumnType(colTypes);
  }  //end of method

  void buildRightTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    ctmRight = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      ctmRight.addRow((Object[]) data.elementAt(i));
    }
    ctmRight.setColumnPrefWidth(colWidths);
    ctmRight.setColumnType(colTypes);
  }  //end of method



  void goViewReport(){
    /*TrackMmOorFrame tmof = new TrackMmOorFrame(cmis,"Order Tracking Detail",leftTable.getModel(),rightTable.getModel(),centerTable.getModel());
    String s = "";
    if(type==MIN_MAX){
      s = "Inventory Status";
    }else{
      s ="Order Status";
    }
    tmof.setStaticInfo(mrL.getText(),issueDateL.getText(),requestorL.getText(),facL.getText(),
                       partNumL.getText(),matDescL.getText(),soL.getText(),statusL.getText(),needByL.getText(),
                       approverL.getText(),workAreaL.getText(),typeL.getText(),qtyL.getText(),lastUpdateL.getText(),promDateL.getText(),revPromDateL.getText(),poL.getText(),s);
    tmof.setInventoryStatusColums(QTY_L,STATUS_L,EST_SHIP_DATE_L,REAL_NOTES_L);
    tmof.setDeliveryStatusColums(QTY_R,SHIP_DATE_R,LOT_R,EXP_DATE_R);
    tmof.setPartDescColums(DESCRIPTION_C,PACKAGING_C,GRADE_C);
    tmof.setVisible(true);
    */
  }

/*
  void setColTypesR(){
    sorterRight.setColTypeFlag(colTypesR);
  }

  void setColWidthsR(){
    for(int i=0;i<colHeadsR.length;i++){
      rightTable.getColumn(colHeadsR[i]).setPreferredWidth(colWidthsR[i]);
      rightTable.getColumn(colHeadsR[i]).setWidth(colWidthsR[i]);
      if (colWidthsR[i]==0){
         rightTable.getColumn(colHeadsR[i]).setMinWidth(colWidthsR[i]);
         rightTable.getColumn(colHeadsR[i]).setMaxWidth(colWidthsR[i]);
      }
    }
  }
*/

  protected void setTableR(){
    rightTable.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        rightTable_mouseClicked(e);
      }
    });
    rightTable.setToolTipText(null);
    rightTable.getTableHeader().setReorderingAllowed(true);
    rightTable.getTableHeader().setResizingAllowed(true);
    rightTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    rightTable.setCellSelectionEnabled(false);
    rightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

/*
  void setColTypesL(){
    sorterLeft.setColTypeFlag(colTypesL);
  }

  void setColWidthsL(){
    for(int i=0;i<colHeadsL.length;i++){
      leftTable.getColumn(colHeadsL[i]).setPreferredWidth(colWidthsL[i]);
      leftTable.getColumn(colHeadsL[i]).setWidth(colWidthsL[i]);
      if (colWidthsL[i]==0){
         leftTable.getColumn(colHeadsL[i]).setMinWidth(colWidthsL[i]);
         leftTable.getColumn(colHeadsL[i]).setMaxWidth(colWidthsL[i]);
      }
    }
  }
*/

  protected void setTableL(){
    leftTable.addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
        leftTable_mouseClicked(e);
      }
    });
    leftTable.setToolTipText(null);
    leftTable.getTableHeader().setReorderingAllowed(true);
    leftTable.getTableHeader().setResizingAllowed(true);
    leftTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    leftTable.setCellSelectionEnabled(false);
    leftTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

/*
  void setColTypesC(){
    sorterLeft.setColTypeFlag(colTypesC);
  }

  void setColWidthsC(){
    for(int i=0;i<colHeadsC.length;i++){
      centerTable.getColumn(colHeadsC[i]).setPreferredWidth(colWidthsC[i]);
      centerTable.getColumn(colHeadsC[i]).setWidth(colWidthsC[i]);
      if (colWidthsC[i]==0){
         centerTable.getColumn(colHeadsC[i]).setMinWidth(colWidthsC[i]);
         centerTable.getColumn(colHeadsC[i]).setMaxWidth(colWidthsC[i]);
      }
    }
  }

*/
  protected void setTableC(){
    centerTable.addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
        centerTable_mouseClicked(e);
      }
    });
    centerTable.setToolTipText(null);
    centerTable.getTableHeader().setReorderingAllowed(true);
    centerTable.getTableHeader().setResizingAllowed(true);
    centerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    centerTable.setCellSelectionEnabled(false);
    centerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }

  void centerTable_mouseClicked(MouseEvent e) {

  }

  void rightTable_mouseClicked(MouseEvent e) {

  }

  void leftTable_mouseClicked(MouseEvent e) {
     int c = leftTable.getSelectedColumn();
     int r = leftTable.getSelectedRow();
     if (c==NOTES_L){
        if (leftTable.getModel().getValueAt(r,c).toString().equals("+")){
          DisplayTextDlg d = new DisplayTextDlg(cmis.getMain(),"Notes",true);
          d.setMsg(leftTable.getModel().getValueAt(r,REAL_NOTES_L).toString());
          d.setVisible(true);
        }
     }
  }

  void rptB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goViewReport();
  }
}

class TrackFloatLoadThread extends Thread {
  TrackFloat parent;
  public TrackFloatLoadThread(TrackFloat parent){
     super("TrackFloatLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

