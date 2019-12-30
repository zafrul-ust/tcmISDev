//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import radian.tcmis.client.share.gui.delivery.*;

import radian.tcmis.client.share.helpers.*;
import java.beans.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.XYLayout;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import java.math.BigDecimal;

public class TrackWin extends JPanel   {

  FacilityCombo facC = new FacilityCombo();
  WorkAreaCombo appChoice = facC.getWorkAreaCombo();

  //trong 1-29-01
  // A - active, I - inactive, B - both
  //String workAreaStatus = "B";
  String currentScreen = "OrderTrack";

  CmisApp cmis;
  boolean dataLoaded = false;
  boolean facCLoaded = false;

  static int STATUS =0;
  static int ITEM_ID = 0;
  static int FAC_ID = 0;
  static int WORK_AREA = 0;
  static int PN_COL = 0;
  static int TYPE =0;
  static int NEEDED =0;
  static int DELIVERED =0;
  static int LAST_DEL =0;
  static int MR_NUM_COL =0;
  static int REQUESTER =0;
  static int APPROVER =0;
  static int CRIT_COL = 0;
  static int CANCELED =0;
  static int CANCEL_REQ =0;
  static int LINE_ITEM = 0;
  static int NOTES = 0;
  static int REAL_NOTES = 0;
  static int CANCEL_REQUESTOR = 0;
  static int SUBMITTED = 0;

  //this
  BorderLayout borderLayout4 = new BorderLayout();
  ImageIcon ss = new ImageIcon();
  ImageIcon searchIcon = new ImageIcon();
  ImageIcon searchIconOver = new ImageIcon();

  //top
  JPanel panel4 = new JPanel();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JButton reqSearchB = new JButton();
  JButton appSearchB = new JButton();
  JButton searchB = new JButton();
  StaticJLabel reqLabel = new StaticJLabel("Requestor:");
  StaticJLabel aprLabel = new StaticJLabel("Approver:");
  StaticJLabel facLabel = new StaticJLabel("Facility:");
  StaticJLabel appLabel = new StaticJLabel("Work Area:");
  StaticJLabel itemLabel = new StaticJLabel("Search Text:");
  JTextField itemText = new JTextField("");
  JComboBox typeC = new JComboBox();
  JComboBox contentC = new JComboBox();

    // type ahead
  NextNameTextField aprT = new NextNameTextField();
  NextNameTextField whoT = new NextNameTextField("PERSONNEL");
  JCheckBox myApprovalC = new JCheckBox();
  JCheckBox criticalC = new JCheckBox();
  ButtonGroup radioButtonGroup1 = new ButtonGroup();
  JRadioButton draftRB = new JRadioButton();
  JRadioButton openRB = new JRadioButton();
  JRadioButton deliveredLast30DaysRB = new JRadioButton();
  JRadioButton deliveredGT30DaysRB = new JRadioButton();
  JRadioButton cancelledRB = new JRadioButton();
  StaticJLabel reqIDLabel = new StaticJLabel("Request ID:");
  JTextField reqIDText = new JTextField("");
  JRadioButton anyRB = new JRadioButton();
  StaticJLabel dayL = new StaticJLabel("days");
  JTextField dayText = new JTextField("");

  //middle
  JPanel panel2 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane JSP = new JScrollPane();
  CmisTable trackTable = new CmisTable();
  DbTableModel ctm = new DbTableModel();
  TableSorter sorterTrack = new TableSorter();
  ColorCellRenderer colorTableRenderer = new ColorCellRenderer();
  CheckBoxCellRenderer checkTableRenderer = new CheckBoxCellRenderer();

  //bottom
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton statusB = new JButton();
  JButton mrB = new JButton();
    //legend
  JPanel jPanel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  JButton jButton4 = new JButton();

  boolean mrAllocation = false;

  public TrackWin(CmisApp cmis) {
    this.cmis = cmis;
    try {
      jbInit();
      ClientHelpObjs.setEnabledContainer(this,false);
      aprT.setParent(cmis);
      whoT.setParent(cmis);
      facC.setCmisApp(cmis);
      facC.setUseAllFacilities(true);
      facC.setUseAllWorkAreas(true);
      facC.addPropertyChangeListener(new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
          if(e.getPropertyName().equalsIgnoreCase("loaded")) {
            facCLoaded();
          }
        }
      });

      //trong 1-29-01
      //facC.setWorkAreaStatus(workAreaStatus);
      facC.setCurrentScreen(this.currentScreen);

      facC.loadFacilityData();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loadItActionForDialUp(){
    ClientHelpObjs.setEnabledContainer(this,false);

    String[]  tmpColHeads = new String[]
        {"Status",     "Facility",    "Workarea",
         "Part Num.",  "Part Desc",   "Type",             "MR Line",
         "Notes",      "Released",    "Needed",           "Picked",     "Delivered",          "Last Del.",        "PO",
         "Requester",  "Crit",        "Catalog",          "Item",       "Approver",
         "Canceled",   "RealNotes",   "Cancel Requested", "DPAS",       "Cancel Requestor",   "Submitted Date"};

    ctm = new DbTableModel(tmpColHeads);
    trackTable = new CmisTable(ctm);
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = trackTable.getColumnModel();
    for (int i = 0; i < trackTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    trackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = trackTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    panel2.remove(JSP);
    setTable();
    JSP.getViewport().setView(trackTable);
    panel2.add(JSP, BorderLayout.CENTER);
    panel2.revalidate();

    Vector v = new Vector(4);
    v.addElement("Part");
    v.addElement("Description");
    v.addElement("Material Request No.");
    v.addElement("Item");
    Vector v1 = new Vector(2);
    v1.addElement("is");
    v1.addElement("contains");

    typeC = ClientHelpObjs.loadChoiceFromVector(typeC,v);
    contentC = ClientHelpObjs.loadChoiceFromVector(contentC,v1);
    contentC.setSelectedItem("contains");
    dayText.setText("30");

    dataLoaded = true;
    loadDone();
    //cmis.getMain().stopImage();   //3-19-02
  }

  public void loadItAction(){
    System.out.println("-------- here in loaditaction: "+facC.isLoading());
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("TrackObj",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","GET_USER_NAME"); //String, String
    query.put("USER_ID",cmis.getUserId().toString());
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }
    String name = result.get("NAME").toString();
    Vector v = new Vector();
    v.addElement("firstName");
    v.addElement("lastName");
    v.addElement(cmis.getUserId().toString());
    whoT.setWhoV(v);
    whoT.setUpdating();
    whoT.setText(name);

    ctm = new DbTableModel((String[])result.get("HEADER_COL"));
    trackTable = new CmisTable(ctm);
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable tableStyle = (Hashtable)result.get("TABLE_STYLE");
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = trackTable.getColumnModel();
    for (int i = 0; i < trackTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    trackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = trackTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    panel2.remove(JSP);
    setTable();
    JSP.getViewport().setView(trackTable);
    panel2.add(JSP, BorderLayout.CENTER);
    panel2.revalidate();

    typeC = ClientHelpObjs.loadChoiceFromVector(typeC,(Vector)result.get("SEARCH_TYPE"));
    contentC = ClientHelpObjs.loadChoiceFromVector(contentC,(Vector)result.get("SEARCH_CONTENT"));
    contentC.setSelectedItem("contains");
    dayText.setText((String)result.get("DAYS"));

    dataLoaded = true;
    loadDone();
  } //end of method

  public void loadIt(){
    //loadItActionForDialUp();
    TrackWinLoadThread iT = new TrackWinLoadThread(this);
    iT.start();
  }
  public void loadDone(){
    if(dataLoaded && facCLoaded){
      ClientHelpObjs.setEnabledContainer(this,true);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      mrB.setEnabled(false);
      statusB.setEnabled(false);
      dayText.setEnabled(deliveredGT30DaysRB.isSelected());
      this.revalidate();
    }
  }

  public void jbInit() throws Exception {
    this.setLayout(borderLayout4);
    appLabel.setHorizontalAlignment(4);
    itemText.setPreferredSize(new Dimension(100, 21));
    itemText.setMaximumSize(new Dimension(300, 19));
    itemText.setText("");
    itemText.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        itemText_keyPressed(e);
      }
    });
    reqSearchB.setMaximumSize(new Dimension(35, 19));
    reqSearchB.setText("");
    reqSearchB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        reqSearchB_actionPerformed(e);
      }
    });
    reqSearchB.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/search_small.gif","Search");
    reqSearchB.setIcon(ss);

    appSearchB.setMaximumSize(new Dimension(35, 19));
    appSearchB.setText("");
    appSearchB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        appSearchB_actionPerformed(e);
      }
    });
    appSearchB.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/search_small.gif","Search");
    appSearchB.setIcon(ss);
    searchB.setMaximumSize(new Dimension(100, 23));
    searchB.setText("Search");
    searchB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchB_actionPerformed(e);
      }
    });
    searchB.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/search.gif","Search");
    searchB.setIcon(ss);

    ss = ResourceLoader.getImageIcon("images/button/nwprt.gif","Search");
    mrB.setIcon(ss);

    ss = ResourceLoader.getImageIcon("images/button/help.gif","Status");
    statusB.setIcon(ss);

    appSearchB.setPreferredSize(new Dimension(35, 19));
    appSearchB.setMinimumSize(new Dimension(35, 19));
    reqSearchB.setMinimumSize(new Dimension(35, 21));
    reqSearchB.setPreferredSize(new Dimension(35, 21));
    itemText.setMinimumSize(new Dimension(60, 21));
    facC.setMaximumSize(new Dimension(300, 21));
    facC.setPreferredSize(new Dimension(200, 21));
    facC.setMinimumSize(new Dimension(200, 21));
    appChoice.setMaximumSize(new Dimension(300, 21));
    appChoice.setPreferredSize(new Dimension(100, 21));
    appChoice.setMinimumSize(new Dimension(60, 21));
    jPanel1.setLayout(gridBagLayout4);
    searchB.setPreferredSize(new Dimension(100, 23));
    aprT.setPreferredSize(new Dimension(90, 19));
    aprT.setMaximumSize(new Dimension(300, 19));
    aprT.setMinimumSize(new Dimension(60, 19));
    whoT.setPreferredSize(new Dimension(90, 21));
    whoT.setMaximumSize(new Dimension(300, 19));
    whoT.setMinimumSize(new Dimension(90, 21));

    panel4.setLayout(gridBagLayout5);

    searchB.setSelected(true);

    mrB.setText("Material Request");
    mrB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mrB_actionPerformed(e);
      }
    });
    mrB.setRolloverEnabled(true);
    mrB.setEnabled(false);
    mrB.setMaximumSize(new Dimension(185, 23));
    mrB.setMinimumSize(new Dimension(185, 23));
    mrB.setPreferredSize(new Dimension(185, 23));

    statusB.setText("MR Line Allocation");
    statusB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        statusB_actionPerformed(e);
      }
    });
    statusB.setRolloverEnabled(true);
    statusB.setEnabled(false);
    statusB.setMaximumSize(new Dimension(140, 23));
    statusB.setMinimumSize(new Dimension(140, 23));
    statusB.setPreferredSize(new Dimension(140, 23));
    searchB.setMinimumSize(new Dimension(100, 23));

     // setting up the table
    ctm = new DbTableModel();
    sorterTrack = new TableSorter(ctm);
    trackTable = new CmisTable(sorterTrack);
    sorterTrack.addMouseListenerToHeaderInTable(trackTable);
    setTable();
    JSP.getViewport().setView(trackTable);

    panel2.setLayout(borderLayout1);
    myApprovalC.setText("Orders needing my Approval");
    myApprovalC.setFont(new java.awt.Font("Dialog", 0, 10));
    myApprovalC.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        myApprovalC_actionPerformed(e);
      }
    });
    jButton1.setBackground(Color.pink);
    jButton1.setMaximumSize(new Dimension(11, 11));
    jButton1.setMinimumSize(new Dimension(11, 11));
    jButton1.setPreferredSize(new Dimension(11, 11));
    jButton2.setBackground(Color.orange);
    jButton2.setMaximumSize(new Dimension(11, 11));
    jButton2.setMinimumSize(new Dimension(11, 11));
    jButton2.setPreferredSize(new Dimension(11, 11));
    jLabel1.setText("Critical orders");
    jLabel2.setText("Cancelled / Rejected");
    jPanel2.setLayout(xYLayout1);
    jLabel3.setText("Pending Cancellation");
    jButton4.setBackground(Color.darkGray);
    jButton4.setMaximumSize(new Dimension(11, 11));
    jButton4.setMinimumSize(new Dimension(11, 11));
    jButton4.setPreferredSize(new Dimension(11, 11));
    jButton4.setText("Priority");
    //top
    draftRB.setText("Draft");
    draftRB.setFont(new java.awt.Font("Dialog", 0, 10));
    draftRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        draftRB_actionPerformed(e);
      }
    });
    openRB.setText("Open");
    openRB.setFont(new java.awt.Font("Dialog", 0, 10));
    openRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        openRB_actionPerformed(e);
      }
    });
    deliveredLast30DaysRB.setText("Delivered last 30 days");
    deliveredLast30DaysRB.setFont(new java.awt.Font("Dialog", 0, 10));
    deliveredGT30DaysRB.setText("Fully Delivered last");
    deliveredGT30DaysRB.setFont(new java.awt.Font("Dialog", 0, 10));
    deliveredGT30DaysRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        deliveredGT30DaysRB_actionPerformed(e);
      }
    });
    cancelledRB.setText("Cancelled / Rejected");
    cancelledRB.setFont(new java.awt.Font("Dialog", 0, 10));
    cancelledRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        cancelledRB_actionPerformed(e);
      }
    });
    anyRB.setText("Any");
    anyRB.setFont(new java.awt.Font("Dialog", 0, 10));
    anyRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        anyRB_actionPerformed(e);
      }
    });
    criticalC.setText("Critical Only");
    criticalC.setFont(new java.awt.Font("Dialog", 0, 10));
    reqIDText.setMaximumSize(new Dimension(300, 19));
    reqIDText.setMinimumSize(new Dimension(60, 19));
    reqIDText.setPreferredSize(new Dimension(90, 19));
    reqIDText.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        reqIDText_keyPressed(e);
      }
    });
    typeC.setMinimumSize(new Dimension(130, 21));
    typeC.setPreferredSize(new Dimension(130, 21));
    typeC.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        typeC_actionPerformed(e);
      }
    });
    contentC.setMinimumSize(new Dimension(80, 21));
    contentC.setPreferredSize(new Dimension(80, 21));
    itemLabel.setText("Search by:");
    dayText.setMinimumSize(new Dimension(40, 21));
    dayText.setPreferredSize(new Dimension(40, 21));
    jPanel2.setMinimumSize(new Dimension(390, 41));
    jPanel2.setPreferredSize(new Dimension(390, 41));
    this.add(panel4, BorderLayout.NORTH);
      //requestor columns
    panel4.add(reqLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 5, 3, 3), 0, 0));
    panel4.add(whoT, new GridBagConstraints2(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 3, 2, 3), 0, 0));
    panel4.add(reqSearchB, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(7, 3, 2, 0), 0, 0));
    /*
    panel4.add(aprLabel, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 3, 3), 0, 0));
    panel4.add(aprT, new GridBagConstraints2(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 2, 3), 0, 0));
    panel4.add(appSearchB, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 2, 0), 0, 0));

    panel4.add(reqIDLabel, new GridBagConstraints2(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    panel4.add(reqIDText, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 2, 3), 0, 0));
    */
    panel4.add(itemLabel, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    panel4.add(typeC, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 60, 0, 0), 0, 0));
    panel4.add(contentC, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 200, 0, 3), 0, 0));
      //legend
    panel4.add(jPanel2, new GridBagConstraints(0, 3, 5, 2, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 2, 5), 0, 0));
      //facility columns
    panel4.add(facLabel, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 0, 3, 3), 0, 0));
    panel4.add(facC, new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 3, 2, 5), 0, 0));
    panel4.add(appLabel, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 3, 3), 0, 0));
    panel4.add(appChoice, new GridBagConstraints(4, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 2, 5), 0, 0));
    /*
    panel4.add(itemLabel, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 3, 0, 3), 0, 0));
    */
    panel4.add(itemText, new GridBagConstraints(4, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 0, 5), 0, 0));
    panel4.add(criticalC, new GridBagConstraints(4, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 0, 5), 0, 0));
    panel4.add(myApprovalC, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 3, 0, 0), 0, 0));
      //status columns
    panel4.add(draftRB, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 70, 0, 0), 0, 0));
    panel4.add(openRB, new GridBagConstraints2(6, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
    /*
    panel4.add(deliveredLast30DaysRB, new GridBagConstraints(5, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 35), 0, 0));
    */
    panel4.add(deliveredGT30DaysRB, new GridBagConstraints(5, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
    panel4.add(dayText, new GridBagConstraints(5, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 135, 0, 0), 0, 0));
    panel4.add(dayL, new GridBagConstraints(5, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 180, 0, 10), 0, 0));
    panel4.add(cancelledRB, new GridBagConstraints(5, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
    //I'll put this back when Nish query will handle this
    panel4.add(anyRB, new GridBagConstraints(5, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
    //anyRB.setSelected(true);
    openRB.setSelected(true);
    radioButtonGroup1.add(draftRB);
    radioButtonGroup1.add(openRB);
    //radioButtonGroup1.add(deliveredLast30DaysRB);
    radioButtonGroup1.add(deliveredGT30DaysRB);
    radioButtonGroup1.add(cancelledRB);
    radioButtonGroup1.add(anyRB);

    //middle
    panel2.add(JSP, BorderLayout.CENTER);
    this.add(panel2, BorderLayout.CENTER);
    panel2.validate();
    //bottom
    jPanel2.setBorder(ClientHelpObjs.groupBox("Legend"));
    jPanel2.add(jButton1, new XYConstraints(9, 1, -1, -1));
    jPanel2.add(jButton2, new XYConstraints(114, 1, -1, -1));
    jPanel2.add(jLabel3, new XYConstraints(129, 0, -1, -1));
    jPanel2.add(jLabel1, new XYConstraints(29, 0, -1, -1));
    jPanel2.add(jLabel2, new XYConstraints(274, 0, -1, -1));
    jPanel2.add(jButton4, new XYConstraints(259, 1, -1, -1));
    panel2.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(searchB, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    jPanel1.add(mrB, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(statusB, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    searchB.setSize(searchB.getWidth(),mrB.getHeight());
    statusB.setSize(statusB.getWidth(),mrB.getHeight());
    jPanel1.validate();


    // Tooltip
    ToolTipManager.sharedInstance().setEnabled(false);
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }

  protected void setTable(){
    trackTable.addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
        trackTable_mouseClicked(e);
      }
    });
    trackTable.setToolTipText(null);
    trackTable.getTableHeader().setReorderingAllowed(true);
    trackTable.getTableHeader().setResizingAllowed(true);
    trackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    trackTable.setCellSelectionEnabled(false);
    trackTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    trackTable.getColumnModel();
    trackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    trackTable.setDefaultRenderer(String.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Double.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Boolean.class, checkTableRenderer);
  }

  //changes by Dmitriy
  public void printScreenData(){
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setRequestor(whoT.getText().length()>0?whoT.getPersonnelId():"");
    rod.setRequestorName(BothHelpObjs.makeBlankFromNull(whoT.getText()));
    rod.setApproverName(BothHelpObjs.makeBlankFromNull(aprT.getText()));
    rod.setApprover(aprT.getText().length()>0?aprT.getPersonnelId():"");
    rod.setFacId(facC.getSelectedFacId());
    rod.setWorkArea(appChoice.getSelectedWorkAreaID());

    rod.setScreen("OT");

    String tmp = null;
    String tmp2 = "";

    tmp = BothHelpObjs.makeBlankFromNull(itemText.getText().trim());
    if (BothHelpObjs.isBlankString(tmp)) {
      tmp2 = new String("");
    }else {
      tmp2 = tmp;
    }
    rod.setSearchText(tmp2);
    rod.setRequestId(BothHelpObjs.makeBlankFromNull(reqIDText.getText().trim()));
    rod.setSearchType((String)typeC.getSelectedItem());
    rod.setSearchContent((String)contentC.getSelectedItem());

    rod.setCriticalOnly(criticalC.isSelected());
    rod.setNeedApproval(myApprovalC.isSelected());

    rod.setAnyStatus(anyRB.isSelected());
    rod.setDraft(draftRB.isSelected());
    rod.setSubmitted(openRB.isSelected());
    rod.setArchived(deliveredGT30DaysRB.isSelected());
    rod.setCancelled(cancelledRB.isSelected());

    try {
      if ("Item".equalsIgnoreCase((String)typeC.getSelectedItem()) || "Request ID".equalsIgnoreCase((String)typeC.getSelectedItem()) ) {
        String tmpSearch = itemText.getText().trim();
        if (!BothHelpObjs.isBlankString(tmpSearch)) {
          int tmp3 = Integer.parseInt(tmpSearch);
        }
      }
    }catch (Exception e3) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
      g.setMsg("Please enter an integer for the search text field.");
      g.setVisible(true);
      dayText.setEnabled(deliveredGT30DaysRB.isSelected());
      return;
    }

    try {
      if (deliveredGT30DaysRB.isSelected()) {
        String tmpDay = dayText.getText().trim();
        if (BothHelpObjs.isBlankString(tmpDay)) {
          GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
          g.setMsg("Please enter an integer for the days field.");
          g.setVisible(true);
          dayText.setEnabled(deliveredGT30DaysRB.isSelected());
          return;
        }else {
          int tmp3 = Integer.parseInt(tmpDay);
        }
        rod.setDays(tmpDay);
      }
    }catch (Exception eee) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
      g.setMsg("Please enter an integer for the days field.");
      g.setVisible(true);
      dayText.setEnabled(deliveredGT30DaysRB.isSelected());
      return;
    }

    rod.setWaName(this.appChoice.getSelectedItem().toString());
    rod.setFacName(this.facC.getSelectedItem().toString());

   rod.loadIt();
  } //end of method

  public void stateChanged(String s){
  }

  void fillTable() {
    TrackThread tT = new TrackThread(this);
    tT.start();
  }

  void fillTableAction() {
   try{
    long sTime = new java.util.Date().getTime();
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("TrackObj",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","SEARCH"); //String, String
    query.put("REQUESTOR",whoT.getText().trim().length()>0?whoT.getPersonnelId():"");
    query.put("NEED_MY_APPROVAL",new Boolean(myApprovalC.isSelected()));
    query.put("FACILITY",facC.getSelectedFacId());
    query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());
    query.put("SEARCH_TEXT",itemText.getText().trim());
    query.put("STATUS_DRAFT",new Boolean(draftRB.isSelected()));
    query.put("STATUS_CANCELLED",new Boolean(cancelledRB.isSelected()));
    query.put("STATUS_ANY",new Boolean(anyRB.isSelected()));
    query.put("STATUS_OPEN",new Boolean(openRB.isSelected())); // go as issue also
    query.put("STATUS_CRIT_ONLY",new Boolean(criticalC.isSelected()));
    query.put("STATUS_ARCHIVED",new Boolean(deliveredGT30DaysRB.isSelected()));

    try {
      if ("Item".equalsIgnoreCase((String)typeC.getSelectedItem()) || "Material Request No.".equalsIgnoreCase((String)typeC.getSelectedItem()) ) {
        String tmpSearch = itemText.getText().trim();
        if (!BothHelpObjs.isBlankString(tmpSearch)) {
          int tmp = Integer.parseInt(tmpSearch);
        }
      }
    }catch (Exception e3) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
      g.setMsg("Please enter an integer for the search text field.");
      g.setVisible(true);
      ClientHelpObjs.setEnabledContainer(this,true);
      dayText.setEnabled(deliveredGT30DaysRB.isSelected());
      return;
    }

    try {
      if (deliveredGT30DaysRB.isSelected()) {
        String tmpDay = dayText.getText().trim();
        if (BothHelpObjs.isBlankString(tmpDay)) {
          GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
          g.setMsg("Please enter an integer for the days field.");
          g.setVisible(true);
          ClientHelpObjs.setEnabledContainer(this,true);
          dayText.setEnabled(deliveredGT30DaysRB.isSelected());
          return;
        }else {
          int tmp = Integer.parseInt(tmpDay);
        }
        query.put("DAYS",tmpDay);
      }
    }catch (Exception eee) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
      g.setMsg("Please enter an integer for the days field.");
      g.setVisible(true);
      ClientHelpObjs.setEnabledContainer(this,true);
      dayText.setEnabled(deliveredGT30DaysRB.isSelected());
      return;
    }
    query.put("USER_ID",cmis.getUserId().toString());
    query.put("SEARCH_TYPE",(String)typeC.getSelectedItem());
    query.put("SEARCH_CONTENT",(String)contentC.getSelectedItem());
    Hashtable result = cgi.getResultHash(query);
    ClientHelpObjs.setEnabledContainer(this,true);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      dayText.setEnabled(deliveredGT30DaysRB.isSelected());
      return;
    }

    //Trong changes
    Hashtable searchData = (Hashtable) result.get("SEARCH_DATA");
    buildTableModel(searchData);
    //ctm = (DbTableModel) result.get("DATA_MODEL");

    sorterTrack = new TableSorter(ctm);
    sorterTrack.setColTypeFlag(ctm.getColumnTypesString());
    trackTable = new CmisTable(sorterTrack);

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable tableStyle = (Hashtable)searchData.get("TABLE_STYLE");
    //Hashtable tableStyle = (Hashtable)result.get("TABLE_STYLE");
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
    trackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = trackTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    // set column widths
    for(int i=0;i<trackTable.getColumnCount();i++){
      int width = ctm.getColumnWidth(i);
      trackTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
      trackTable.getColumn(ctm.getColumnName(i)).setWidth(width);
      if (width==0){
        trackTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
        trackTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
      }
    }

    sorterTrack.addMouseListenerToHeaderInTable(trackTable);
    setTable();
    if (trackTable.getModel().getRowCount()<=0){
       GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
       g.setMsg("No Records found.");
       g.setVisible(true);
       ClientHelpObjs.setEnabledContainer(this,true);
       dayText.setEnabled(deliveredGT30DaysRB.isSelected());
       // empty table
        // setting up the table
       panel2.remove(JSP);
       JSP.getViewport().setView(trackTable);
       panel2.add(JSP, BorderLayout.CENTER);
       panel2.revalidate();
       //disable or enable search criteria according to if needing my approval is check
       ClientHelpObjs.setEnabledContainer(panel4,!myApprovalC.isSelected());
       myApprovalC.setEnabled(true);
       panel4.revalidate();
       return;
    }
    // clumns
    Hashtable keyCols = (Hashtable) searchData.get("KEY_COLS");
    //Hashtable keyCols = (Hashtable) result.get("KEY_COLS");

    STATUS = Integer.parseInt((String) keyCols.get("STATUS"));
    ITEM_ID = Integer.parseInt((String) keyCols.get("ITEM_ID"));
    FAC_ID = Integer.parseInt((String) keyCols.get("FAC_ID"));
    WORK_AREA = Integer.parseInt((String) keyCols.get("WORK_AREA"));
    PN_COL = Integer.parseInt((String) keyCols.get("PN_COL"));
    TYPE = Integer.parseInt((String) keyCols.get("TYPE"));
    NEEDED = Integer.parseInt((String) keyCols.get("NEEDED"));
    DELIVERED = Integer.parseInt((String) keyCols.get("DELIVERED"));
    LAST_DEL = Integer.parseInt((String) keyCols.get("LAST_DEL"));
    MR_NUM_COL = Integer.parseInt((String) keyCols.get("MR_NUM_COL"));
    REQUESTER = Integer.parseInt((String) keyCols.get("REQUESTER"));
    APPROVER = Integer.parseInt((String) keyCols.get("APPROVER"));
    CRIT_COL = Integer.parseInt((String) keyCols.get("CRIT_COL"));
    CANCELED = Integer.parseInt((String) keyCols.get("CANCELED"));
    CANCEL_REQ = Integer.parseInt((String) keyCols.get("CANCEL_REQ"));
    CANCEL_REQUESTOR = Integer.parseInt((String) keyCols.get("CANCEL_REQUESTOR"));
    NOTES = Integer.parseInt((String) keyCols.get("NOTES"));
    REAL_NOTES = Integer.parseInt((String) keyCols.get("REAL_NOTES"));
    SUBMITTED = Integer.parseInt((String) keyCols.get("SUBMITTED_DATE"));

    try { panel2.remove(JSP); } catch (Exception e) {};
    JSP = new JScrollPane(trackTable);
    panel2.add(JSP, BorderLayout.CENTER);
    panel2.revalidate();

    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date(min));
    String tmsg = "Records Found: " + trackTable.getRowCount() + "     Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
    cmis.getMain().countLabel.put(new Integer(Main.TRACKING),tmsg);
    cmis.getMain().setStatusBarText((String)cmis.getMain().countLabel.get(new Integer(Main.TRACKING)));
   } catch (Exception e){
     GenericDlg.showAccessDenied(cmis.getMain());
     ClientHelpObjs.setEnabledContainer(this,true);
     dayText.setEnabled(deliveredGT30DaysRB.isSelected());
     e.printStackTrace();
   }

   mrB.setEnabled(false);
   statusB.setEnabled(false);
   dayText.setEnabled(deliveredGT30DaysRB.isSelected());
   this.revalidate();
   ClientHelpObjs.setComboBoxUpdateUi(this);
   ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
   //disable or enable search criteria according to if needing my approval is check
   ClientHelpObjs.setEnabledContainer(panel4,!myApprovalC.isSelected());
   myApprovalC.setEnabled(true);
   panel4.revalidate();
  } //end of method

  void buildTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_COL");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTH");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPE");
    Vector data = (Vector) searchData.get("TABLE_DATA");
    ctm = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      ctm.addRow((Object[]) data.elementAt(i));
    }
    ctm.setColumnPrefWidth(colWidths);
    ctm.setColumnType(colTypes);
  }  //end of method

  void createDetailWindow(){
    Hashtable h = new Hashtable();
    try {
       h.put("ITEM_ID",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),ITEM_ID)));
       h.put("MR_LINE",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),MR_NUM_COL)));
       h.put("SUBMIT_DATE",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),SUBMITTED)));
       h.put("STATUS",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),STATUS)));
       h.put("REQUESTOR",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),REQUESTER)));
       h.put("NEED_DATE",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),NEEDED)));
       h.put("FACILITY",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),FAC_ID)));
       h.put("APPROVER",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),APPROVER)));
       h.put("WORK_AREA",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),WORK_AREA)));
       h.put("TYPE",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),TYPE)));
       h.put("PART_NO",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),PN_COL)));
       String del = BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),DELIVERED));
       h.put("QUANTITY",del.substring(del.indexOf("of")+3));
       h.put("DELIVERED",del.substring(0,del.indexOf("of")-1));
       h.put("CANCEL_REQUEST",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),CANCEL_REQ)));
       h.put("CANCEL_REQUESTOR",BothHelpObjs.makeBlankFromNull((String)trackTable.getModel().getValueAt(trackTable.getSelectedRow(),CANCEL_REQUESTOR)));
    } catch (Exception e){
       GenericDlg gg = new GenericDlg(cmis.getMain(),"Error",true);
       gg.setMsg("Can not determine item ID. Please contact tcmis@tcmis.com");
       gg.setVisible(true);
       e.printStackTrace();
       return;
    }

    TrackFloat tf = new TrackFloat(cmis.getMain(),h);
    tf.setParent(cmis);
    tf.loadItThread();
    CenterComponent.centerCompOnScreen(tf);
    tf.setVisible(true);
  }

  void goChooseRequestor() {
    //choose requestor
    SearchPersonnel dlg = new SearchPersonnel(cmis.getMain(),"Search",true);
    dlg.setGrandParent(cmis);
    dlg.setFacility(facC.getSelectedFacId());
    dlg.setPersonType("PERSONNEL");
    dlg.setVisible(true);
    if (dlg.getValueId() != null){
      whoT.setUpdating();
      whoT.setText(dlg.getValueLast()+", "+dlg.getValueFirst());
      Vector v = new Vector();
      v.addElement("first Name");
      v.addElement("last Name");
      v.addElement(dlg.getValueId());
      whoT.setWhoV(v);
    }
  }

  void goChooseApprover() {     //choose approver
    SearchPersonnel dlg = new SearchPersonnel(cmis.getMain(),"Search",true);
    dlg.setGrandParent(cmis);
    String facTest = new String(facC.getSelectedFacId());
    if (facTest.length() <=1){
         dlg.setPersonType("PERSONNEL");
    } else {
         dlg.setFacility(facTest);
         dlg.setPersonType("APPROVERS");
    }
    dlg.setVisible(true);
    if (dlg.getValueId() != null){
      aprT.setUpdating();
      aprT.setText(dlg.getValueLast()+", "+dlg.getValueFirst());
      Vector v = new Vector();
      v.addElement("first Name");
      v.addElement("last Name");
      v.addElement(dlg.getValueId());
      aprT.setWhoV(v);
    }
  }
  void mrB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    /*stopTableEditing();
    if (!isTableSelected()) return;
    int i = trackTable.getSelectedRow();
    String mrLine = trackTable.getModel().getValueAt(i,MR_NUM_COL).toString();
    mrLine = mrLine.substring(0,mrLine.indexOf("-"));
    cmis.getMain().goRequest(0,(new Integer(mrLine)).intValue(),null);
    */
    goMaterialRequestDetail();
  }
  void goMaterialRequestDetail() {
    stopTableEditing();
    if (!isTableSelected()) return;
    int i = trackTable.getSelectedRow();
    String mrLine = trackTable.getModel().getValueAt(i,MR_NUM_COL).toString();
    mrLine = mrLine.substring(0,mrLine.indexOf("-"));
    cmis.getMain().goRequest(0,(new Integer(mrLine)).intValue(),null);
  }
  void facCLoaded(){
    facCLoaded = true;
    loadDone();
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    fillTable();
  }

  void myApprovalC_actionPerformed(ActionEvent e) {
    ClientHelpObjs.setEnabledContainer(panel4,!myApprovalC.isSelected());
    myApprovalC.setEnabled(true);
    panel4.revalidate();
  }

  void reqSearchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    personnelLookupStart(whoT);
  }

  void appSearchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    personnelLookupStart(aprT);
  }
  void personnelLookupStart(NextNameTextField nntf){
    TrackWinWhoLookupThread tw = new TrackWinWhoLookupThread(this,nntf);
    tw.start();
  }
  void personnelLookup(NextNameTextField nntf){
    SearchPersonnel sp = new SearchPersonnel(cmis.getMain(),"Personnel Lookup",true);
    sp.setGrandParent(cmis);
    sp.setPersonType("PERSONNEL");
    sp.setVisible(true);
    if (sp.getValueId() != null){
      nntf.setUpdating();
      nntf.setText(sp.getValueLast()+", "+sp.getValueFirst());
      Vector v = new Vector();
      v.addElement("first Name");
      v.addElement("last Name");
      v.addElement(sp.getValueId().toString());
      nntf.setWhoV(v);
    }
  }
  void stopTableEditing(){
    if (trackTable==null) return;
    if (trackTable.isEditing()){
      trackTable.getCellEditor().stopCellEditing();
    }
  }

  boolean isTableSelected(){
    return trackTable.getSelectedRowCount() > 0;
  }

  void trackTable_mouseClicked(MouseEvent e) {
    int i   = trackTable.getSelectedRow();
    int col = trackTable.getSelectedColumn();

    if(e.isMetaDown()) {
      if (isTableSelected()) {
        String tt = trackTable.getModel().getValueAt(i,STATUS).toString();
        boolean showStatusDetail = false;
        boolean showSchedule = false;
        if ("SCH".equalsIgnoreCase(trackTable.getModel().getValueAt(i,TYPE).toString())) {
          showSchedule = true;
        }
        if (!"Draft".equalsIgnoreCase(tt) && !tt.equalsIgnoreCase("Pending Finance Approval") && !tt.equalsIgnoreCase("Pending Use Approval")) {
          showStatusDetail = true;
        }
        TrackWinMetaPopUp mpu = new TrackWinMetaPopUp(this.cmis,this,showSchedule,showStatusDetail);
        JSP.add(mpu);
        mpu.show(trackTable,e.getX(),e.getY());
        return;
      }
    }

    String tt = trackTable.getModel().getValueAt(i,STATUS).toString();
    if(isTableSelected()){
      if(BothHelpObjs.isBlankString(trackTable.getModel().getValueAt(i,MR_NUM_COL).toString())){
        mrB.setEnabled(false);
      }else{
        mrB.setEnabled(true);
      }
      if(BothHelpObjs.isBlankString(trackTable.getModel().getValueAt(i,STATUS).toString())){
        statusB.setEnabled(false);
      }else{
        if (!"Draft".equalsIgnoreCase(tt) && !tt.equalsIgnoreCase("Pending Finance Approval") && !tt.equalsIgnoreCase("Pending Use Approval")) {
          statusB.setEnabled(true);
        } else {
          statusB.setEnabled(false);
        }
      }
    }else{
      mrB.setEnabled(false);
      statusB.setEnabled(false);
    }
    if(col == CRIT_COL && ("Draft".equalsIgnoreCase(tt) || "In Progress".equalsIgnoreCase(tt) || tt.startsWith("Pending"))) {
      updateCriticalFlag();
    }
    if(col == NOTES && trackTable.getModel().getValueAt(i,NOTES).toString().trim().equalsIgnoreCase("+")){
      DisplayTextDlg dlg = new DisplayTextDlg(cmis.getMain(),"Notes");
      dlg.setMsg(trackTable.getModel().getValueAt(i,REAL_NOTES).toString());
      dlg.setVisible(true);
    }
  } //end of method

  void showRequestStatus(){
    try {
      String mrLine = trackTable.getModel().getValueAt(trackTable.getSelectedRow(),MR_NUM_COL).toString();
      String client = cmis.getResourceBundle().get("CLIENT_INITIALS").toString();
      if ("Boeing".equalsIgnoreCase(client)) {
       client = "ula";
      }
      String url = "https://www.tcmis.com/tcmIS/"+client.toLowerCase()+"/mrallocationreportmain.do?companyId="+cmis.companyId+
                   "&mrNumber="+mrLine.substring(0,mrLine.indexOf("-"));
      //don't pass line_item to server if users pick to see mr allocation
      if (!mrAllocation) {
        url += "&lineItem="+mrLine.substring(mrLine.indexOf("-") + 1);
      }

      new URLCall(url,cmis,false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  /*
  void showRequestStatus() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("TrackObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_MR_ALLOCATION_DATA"); //String, String
      String mrLine = trackTable.getModel().getValueAt(trackTable.getSelectedRow(),MR_NUM_COL).toString();
      query.put("PR_NUMBER",mrLine.substring(0,mrLine.indexOf("-")));
      //don't pass line_item to server if users pick to see mr allocation
      if (!mrAllocation) {
        query.put("LINE_ITEM", mrLine.substring(mrLine.indexOf("-") + 1));
      }
      query.put("USER_ID",cmis.getUserId().toString());
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }
      Boolean b = (Boolean)result.get("SUCCEED");
      if (b.booleanValue()) {
        String tmpFac = trackTable.getModel().getValueAt(trackTable.getSelectedRow(),this.FAC_ID).toString();
        String tmpRequestor = trackTable.getModel().getValueAt(trackTable.getSelectedRow(),this.REQUESTER).toString();
        MRAllocationReport dlg = new MRAllocationReport(cmis.getMain(),"MR Line Allocation",true,(Vector)result.get("MR_ALLOCATION_INFO"),tmpRequestor,tmpFac);
        dlg.setGrandParent(cmis);
        dlg.setVisible(true);
      }else {
        GenericDlg dlg = new GenericDlg(cmis.getMain(),"Error");
        dlg.setMsg("tcmIS encounter an error while loading MR allocation data, please try again.\nContact your Customer Representative if problem is presistent.");
        dlg.setVisible(true);
      }
    }catch (Exception e) {
      GenericDlg dlg = new GenericDlg(cmis.getMain(),"Error");
      dlg.setMsg("tcmIS encounter an error while loading MR allocation data please try again.\nContact your Customer Representative if problem is presistent.");
      dlg.setVisible(true);
    }
  }
  */

  void showScheduleDelivery() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("TrackObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_SCHEDULE_DELIVERY"); //String, String
      String mrLine = trackTable.getModel().getValueAt(trackTable.getSelectedRow(),MR_NUM_COL).toString();
      query.put("PR_NUMBER",mrLine.substring(0,mrLine.indexOf("-")));
      query.put("USER_ID",cmis.getUserId().toString());
      query.put("LINE_ITEM",mrLine.substring(mrLine.indexOf("-")+1));
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }
      Boolean b = (Boolean)result.get("SUCCEED");
      if (b.booleanValue()) {
        Hashtable scheduleDeliveryInfo = (Hashtable)result.get("SCHEDULE_DELIVERY_INFO");
        boolean submitted = scheduleDeliveryInfo.get("STATUS").toString().equalsIgnoreCase("posubmit");
        DeliverySchedule ds = new DeliverySchedule(cmis,(Vector)scheduleDeliveryInfo.get("DELIVERY_SCHEDULE"),new BigDecimal(scheduleDeliveryInfo.get("QTY").toString()),mrLine.substring(0,mrLine.indexOf("-")),
                                                   scheduleDeliveryInfo.get("CAT_PART_NO").toString(),scheduleDeliveryInfo.get("ITEM_DESC").toString(),
                                                   scheduleDeliveryInfo.get("STATUS_DESC").toString(),scheduleDeliveryInfo.get("ITEM_TYPE").toString(),
                                                   scheduleDeliveryInfo.get("ITEM_ID").toString(),(Calendar)result.get("NOW"),submitted,mrLine.substring(mrLine.indexOf("-")+1));
        CenterComponent.centerCompOnScreen(ds);
        boolean viewonly = false;
        if(cmis.getUserId().intValue() == Integer.parseInt((String)scheduleDeliveryInfo.get("REQUESTOR")) ||
           cmis.getGroupMembership().isSuperUser()){
          //allow edit
        }else{
          viewonly = true;
          ds.setViewOnly(true);
        }
        ds.setAllowQtyChange(true);
        ds.setVisible(true);
      }else {
        GenericDlg dlg = new GenericDlg(cmis.getMain(),"Error");
        dlg.setMsg("tcmIS encounter an error while loading schedule delivery, please try again.\nContact your Customer Representative if problem is presistent.");
        dlg.setVisible(true);
      }
    }catch (Exception e) {
      GenericDlg dlg = new GenericDlg(cmis.getMain(),"Error");
      dlg.setMsg("tcmIS encounter an error while loading schedule delivery, please try again.\nContact your Customer Representative if problem is presistent.");
      dlg.setVisible(true);
    }
  }

  void updateCriticalFlag(){
    TrackWin_CritThread c = new TrackWin_CritThread(this,trackTable.getSelectedRow(),trackTable.getSelectedColumn());
    c.start();
  }

  void updateCriticalFlagAction(int row,int col){
    TcmisHttpConnection cgi = new TcmisHttpConnection("TrackObj",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","UPDATE_CRITICAL"); //String, String
    String mrLine = trackTable.getModel().getValueAt(row,MR_NUM_COL).toString();
    query.put("MR_NUM",mrLine.substring(0,mrLine.indexOf("-")));
    query.put("CRIT",new Boolean(!((Boolean)trackTable.getModel().getValueAt(row,col)).booleanValue()));
    query.put("USER_ID",cmis.getUserId().toString());
    query.put("LINE_ITEM",mrLine.substring(mrLine.indexOf("-")+1));
    //query.put("SO_NUMBER",trackTable.getModel().getValueAt(row,SO_COL).toString());
    //query.put("PART_NUM",trackTable.getModel().getValueAt(row,PN_COL).toString());
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }
    Boolean b = (Boolean)result.get("RESULT");

    trackTable.getModel().setValueAt(b,row,col);
    trackTable.repaint();
  }

  void statusB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    //rather than calling rm status call mr allocation instead
    //createDetailWindow();
    this.mrAllocation = false;
    showRequestStatus();
  }

  void itemText_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        fillTable();
    }
  }
  void reqIDText_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        fillTable();
    }
  }

  void deliveredGT30DaysRB_actionPerformed(ActionEvent e) {
    dayText.setEnabled(deliveredGT30DaysRB.isSelected());
  }

  void draftRB_actionPerformed(ActionEvent e) {
    dayText.setEnabled(deliveredGT30DaysRB.isSelected());
  }

  void openRB_actionPerformed(ActionEvent e) {
    dayText.setEnabled(deliveredGT30DaysRB.isSelected());
  }

  void cancelledRB_actionPerformed(ActionEvent e) {
    dayText.setEnabled(deliveredGT30DaysRB.isSelected());
  }

  void anyRB_actionPerformed(ActionEvent e) {
    dayText.setEnabled(deliveredGT30DaysRB.isSelected());
  }

  void typeC_actionPerformed(ActionEvent e) {
    contentC.setSelectedItem("contains");
  } //end of method
} //end of class

class TrackThread extends Thread {
  TrackWin parent;
  public TrackThread(TrackWin parent){
     super("TrackThread");
     this.parent = parent;
  }
  public void run(){
     parent.fillTableAction();
  }
}

class TrackWinLoadThread extends Thread {
 TrackWin parent;
  public TrackWinLoadThread(TrackWin parent){
     super("TrackWinLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}
class TrackWinWhoLookupThread extends Thread {
 TrackWin parent;
 NextNameTextField nntf;
  public TrackWinWhoLookupThread(TrackWin parent,NextNameTextField nntf){
     super("TrackWinWhoLookupThread");
     this.parent = parent;
     this.nntf = nntf;
  }
  public void run(){
     parent.personnelLookup(nntf);
  }
}
class TrackWin_CritThread extends Thread {
  TrackWin parent;
  int row;
  int col;
  public TrackWin_CritThread(TrackWin parent,int row, int col){
     super("TrackWinCritThread");
     this.parent = parent;
     this.row = row;
     this.col = col;
  }
  public void run(){
     parent.updateCriticalFlagAction(row,col);
  }
}

