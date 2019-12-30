//tle:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui.nchem;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.client.share.helpers.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.beans.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.GridBagConstraints2;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class NChemTrackWin extends JPanel  {

  FacilityCombo facC = new FacilityCombo();
  WorkAreaCombo appChoice = facC.getWorkAreaCombo();
  boolean facCLoaded = false;
  boolean dataLoaded = false;

  //trong 1-29-01
  // A - active, I - inactive, B - both
  //String workAreaStatus = "B";
  String currentScreen = "NewChemTrack";

  Color foreground = null;
  StaticJLabel reqLabel = new StaticJLabel("Requestor:");
  StaticJLabel aprLabel = new StaticJLabel("Approver:");
  StaticJLabel facLabel = new StaticJLabel("Facility:");
  StaticJLabel appLabel = new StaticJLabel("Work Area:");
  StaticJLabel itemLabel = new StaticJLabel("Search Text:");
  JTextField itemText = new JTextField("");
  Vector aps = new Vector();
  Vector drafts = new Vector();
  Vector needApp = new Vector();
  Vector pend = new Vector();
  Vector issue = new Vector();
  boolean updatedA = false;
  Vector whoV = new Vector();
  boolean updatedW = false;
  boolean loading = true;
  int trackSel = 0;
  CmisApp parent;

  String requestor  = null;
  String approver  = new String("");
  String releaser  = new String("");
  String reqID = null;
  String aprID = null;
  String relID = null;
  JScrollPane JSP = new JScrollPane();
  NChemTrackWin_TrackTable trackTable = new NChemTrackWin_TrackTable();

  TableOrganizer tOrg = new TableOrganizer();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel panel2 = new JPanel();
  NChemTrackWin_TableModel nctwModel;
  TableSorter sorterTrack;
  JButton searchPB1 = new JButton();
  JButton searchPB2 = new JButton();
  JButton searchPB3 = new JButton();
  JPanel jPanel1 = new JPanel();
  JButton mrDetailB = new JButton();
  ImageIcon searchIcon = new ImageIcon();
  ImageIcon searchIconOver = new ImageIcon();
  JPanel groupBox1 = new JPanel();
  JCheckBox draftC = new JCheckBox();
  JCheckBox newMatC = new JCheckBox();
  JCheckBox newPartC = new JCheckBox();
  JPanel panel4 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();

  ImageIcon ss = new ImageIcon();

  // type ahead
  NextNameTextField aprT = new NextNameTextField();
  NextNameTextField whoT = new NextNameTextField("PERSONNEL");
  JCheckBox pendC = new JCheckBox();

  JTextArea jTextArea1 = new JTextArea();
  JCheckBox newGroupC = new JCheckBox();
  JCheckBox apprC = new JCheckBox();
  JCheckBox rejC = new JCheckBox();
  StaticJLabel reqIDL = new StaticJLabel();
  JTextField reqIdT = new JTextField();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JCheckBox needAppC = new JCheckBox();
  JCheckBox newSizeC = new JCheckBox();
  JCheckBox newApprovalC = new JCheckBox();
  JButton appDetailB = new JButton();
  JCheckBox banC = new JCheckBox();

  public NChemTrackWin(CmisApp cmis) {
    parent = cmis;
    try {
      jbInit();
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

      //facC.loadIt();
      facC.loadFacilityData();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loadItAction(){
     CursorChange.setCursor(parent.getMain(),Cursor.WAIT_CURSOR);
     ClientHelpObjs.setEnabledContainer(this,false);
     panel2.setVisible(false);
     panel4.setVisible(false);

     whoT.setParent(parent);
     aprT.setParent(parent);
     loadScreen();
     dataLoaded = true;
     loadDone();
  }

  public void loadIt(){
    NChemTrackWin_LoadThread iT = new NChemTrackWin_LoadThread(this);
    iT.start();
  }
  void facCLoaded(){
    facCLoaded = true;
    loadDone();
  }
  public void loadDone(){
    if(dataLoaded && facCLoaded){
      ClientHelpObjs.setEnabledContainer(this,true);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      panel2.setVisible(true);
      panel4.setVisible(true);
      this.validate();
      this.repaint();
      this.revalidate();
      CursorChange.setCursor(parent.getMain(),Cursor.DEFAULT_CURSOR);
    }
  }

  public void jbInit() throws Exception {
    this.setLayout(borderLayout4);

    this.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        this_componentResized(e);
      }
    });
    appChoice.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    facC.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    aprT.setActionType("NEW_CHEM_APPROVERS");
    whoT.setMaximumSize(new Dimension(300, 19));
    whoT.setMinimumSize(new Dimension(60, 19));
    whoT.setPreferredSize(new Dimension(90, 19));
    whoT.setFac("");
    aprT.setMaximumSize(new Dimension(300, 19));
    aprT.setMinimumSize(new Dimension(60, 19));
    aprT.setPreferredSize(new Dimension(90, 19));
    aprT.setFac("");

    reqLabel.setHorizontalAlignment(4);
    aprLabel.setHorizontalAlignment(4);
    itemLabel.setHorizontalAlignment(4);
    itemText.setMaximumSize(new Dimension(300, 19));
    itemText.setMinimumSize(new Dimension(60, 19));
    itemText.setPreferredSize(new Dimension(90, 19));
    itemText.setText("");
    appLabel.setText("Work Area:");
    groupBox1.setLayout(gridBagLayout3);

    searchPB1.addActionListener(new NChemTrackWin_searchPB1_actionAdapter(this));
    searchPB1.setText("");
    searchPB1.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/search_small.gif","Search");
    searchPB1.setMaximumSize(new Dimension(35, 19));
    searchPB1.setMinimumSize(new Dimension(35, 19));
    searchPB1.setPreferredSize(new Dimension(35, 19));
    searchPB1.setIcon(ss);

    searchPB2.addActionListener(new NChemTrackWin_searchPB2_actionAdapter(this));
    searchPB2.setText("");
    searchPB2.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/search_small.gif","Search");
    searchPB2.setMaximumSize(new Dimension(35, 19));
    searchPB2.setMinimumSize(new Dimension(35, 19));
    searchPB2.setPreferredSize(new Dimension(35, 19));
    searchPB2.setIcon(ss);
    searchPB3.setMaximumSize(new Dimension(200, 23));
    searchPB3.setText("Search");
    searchPB3.setRolloverEnabled(true);
    searchPB3.addKeyListener(new NChemTrackWin_searchPB3_keyAdapter(this));
    searchPB3.addActionListener(new NChemTrackWin_searchPB3_actionAdapter(this));

    ss = ResourceLoader.getImageIcon("images/button/search.gif","Search");
    searchPB3.setIcon(ss);

    appChoice.addItemListener(new NChemTrackWin_appChoice_itemAdapter(this));
    facC.addItemListener(new NChemTrackWin_facC_itemAdapter(this));

    //JSP = new JScrollPane(trackTable);
    JSP = new JScrollPane();
    facLabel.setHorizontalAlignment(4);
    appLabel.setHorizontalAlignment(4);
    searchPB2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        searchPB2_keyPressed(e);
      }
    });
    searchPB1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        searchPB1_keyPressed(e);
      }
    });
    jTextArea1.setText("jTextArea1");
    newGroupC.setText(" Pending QC");
    //newGroupC.setBackground(Color.white);
    newGroupC.setSelected(true);
    newGroupC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        newGroupC_itemStateChanged(e);
      }
    });
    apprC.setText(" Ready to Order");
    //apprC.setBackground(Color.white);
    apprC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        apprC_itemStateChanged(e);
      }
    });
    rejC.setText(" Rejected");
    //rejC.setBackground(Color.white);
    rejC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        rejC_itemStateChanged(e);
      }
    });
    pendC.setText(" Pending Pricing");
    pendC.setSelected(true);
    //pendC.setBackground(Color.white);
    //pendC.setFont(new Font("Dialog", 0, 11));
    pendC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        pendC_itemStateChanged(e);
      }
    });
    itemText.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
        itemText_keyPressed(e);
      }
    });
    searchPB3.setPreferredSize(new Dimension(150, 23));
    newPartC.setText(" Draft (New Part No)");
    //newPartC.setBackground(Color.white);
    //newPartC.setSelected(true);
    newPartC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        newPartC_itemStateChanged(e);
      }
    });
    //newPartC.setFont(new Font("Dialog", 0, 11));
    //panel4.setBackground(SystemColor.control);
    aprT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        stateChanged("Refresh");
        this_keyPressed(e);
      }
    });
    whoT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
      stateChanged("Refresh");
        this_keyPressed(e);
      }
    });
    panel4.setLayout(gridBagLayout4);

    newMatC.setText(" Draft (New Material)");
    //newMatC.setBackground(Color.white);
    //newMatC.setSelected(true);
    newMatC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        newMatC_itemStateChanged(e);
      }
    });

    draftC.setText(" Pending Approval");
    draftC.setSelected(true);
    draftC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        draftC_itemStateChanged(e);
      }
    });
    //draftC.setFont(new Font("Dialog", 0, 11));
    //rejC.setFont(new Font("Dialog", 0, 11));
    reqIDL.setText("Request ID:");
    reqIDL.setHorizontalAlignment(4);
    reqIdT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    groupBox1.setBorder(ClientHelpObjs.groupBox("Status"));
    groupBox1.setMaximumSize(new Dimension(244, 127));
    groupBox1.setMinimumSize(new Dimension(244, 127));
    groupBox1.setPreferredSize(new Dimension(244, 127));

    searchPB3.setSelected(true);
    mrDetailB.setText("Cat Add Detail");
    mrDetailB.setRolloverEnabled(true);
    mrDetailB.setEnabled(false);

    mrDetailB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mrDetailB_actionPerformed(e);
      }
    });
    searchPB3.setMinimumSize(new Dimension(150, 23));

    panel2.setLayout(borderLayout1);
    appDetailB.setEnabled(false);
    facC.setMaximumSize(new Dimension(300, 21));
    facC.setMinimumSize(new Dimension(80, 21));
    facC.setPreferredSize(new Dimension(100, 21));
    appChoice.setMaximumSize(new Dimension(300, 21));
    appChoice.setMinimumSize(new Dimension(80, 21));
    appChoice.setPreferredSize(new Dimension(100, 21));
    panel2.add(JSP, BorderLayout.CENTER);

    Vector myV = new Vector();
    banC.setText(" Pending Part No.");
    banC.setSelected(true);
    banC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        banC_itemStateChanged(e);
      }
    });
    appDetailB.setText("Approval Detail");
    appDetailB.addActionListener(new NChemTrackWin_appDetailB_actionAdapter(this));
    pendC.setSelected(true);
    newGroupC.setSelected(true);
    newApprovalC.setText(" Draft (New Approval)");
    //newApprovalC.setSelected(true);
    newApprovalC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        newApprovalC_itemStateChanged(e);
      }
    });
    newSizeC.setText(" Draft (New Size/Pkg)");
    //newSizeC.setSelected(true);
    newSizeC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        newSizeC_itemStateChanged(e);
      }
    });
    needAppC.setText(" Show all requests needing my approval");
    needAppC.setActionCommand("needAppC");
    needAppC.setVisible(true);
    //needAppC.setFont(new Font("Dialog", 0, 11));
    needAppC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        needAppC_actionPerformed(e);
      }
    });
    buildTable(myV);

    panel2.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(searchPB3, null);
    jPanel1.add(mrDetailB, null);
    jPanel1.add(appDetailB, null);
    this.add(panel4, BorderLayout.NORTH);

    panel4.add(whoT, new GridBagConstraints2(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 3, 2, 3), 0, 0));
    panel4.add(searchPB1, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(7, 3, 2, 0), 0, 0));
    panel4.add(aprT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 2, 3), 0, 0));
    panel4.add(searchPB2, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 2, 0), 0, 0));
    panel4.add(reqLabel, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 5, 3, 3), 0, 0));
    panel4.add(aprLabel, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 3, 3), 0, 0));
    panel4.add(itemText, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 0, 3), 0, 0));
    panel4.add(itemLabel, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 3, 3), 0, 0));
    panel4.add(needAppC, new GridBagConstraints2(1, 3, 4, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));

    panel4.add(facC, new GridBagConstraints2(4, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 3, 2, 5), 0, 0));
    panel4.add(appChoice, new GridBagConstraints2(4, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 2, 5), 0, 0));
    panel4.add(facLabel, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 0, 3, 3), 0, 0));
    panel4.add(appLabel, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 2, 3), 0, 0));
    panel4.add(reqIdT, new GridBagConstraints2(4, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 0, 5), 0, 0));
    panel4.add(reqIDL, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 0, 5), 0, 0));


    panel4.add(groupBox1, new GridBagConstraints2(6, 0, 1, 4, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));



    groupBox1.add(newMatC, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 3, 0, 0), 0, 0));
    groupBox1.add(newSizeC, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 0, 0), 0, 0));
    groupBox1.add(newPartC, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0));
    groupBox1.add(newApprovalC, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

    groupBox1.add(draftC, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0));
    groupBox1.add(newGroupC, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    groupBox1.add(pendC, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0));
    groupBox1.add(banC, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

    groupBox1.add(apprC, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0));
    groupBox1.add(rejC, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    this.add(panel2, BorderLayout.CENTER);

    panel2.validate();

    //trong
    mrDetailB.setEnabled(false);
    appDetailB.setEnabled(false);
    //colors and fonts

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle =  BothHelpObjs.getTableStyle();
    Color color = (Color)chargeTableStyle.get("COLOR");
    Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = trackTable.getColumnModel();
    for (int i1 = 0; i1 < trackTable.getColumnCount(); i1++) {
      cm.getColumn(i1).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
    trackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = trackTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }


    ClientHelpObjs.makeDefaultColors(this);
  }
    /*
  void loadScreen() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CATALOG,parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","LOAD_ALL_DATA"); //String, String
      query.put("USERID",(new Integer(parent.getUserId().intValue())).toString()); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
      }
      // set the focus
      whoT.requestFocus();
      //parent.getMain().stopImage();      //3-19-02
    } catch (Exception e) {
      e.printStackTrace();
    }
    loading = false;
  } */

  void loadScreen() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.NEW_CHEM_TRACKING,parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","LOAD_INITIAL_DATA"); //String, String
      query.put("USERID",parent.getUserId().toString()); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
      }
      Boolean returnCode = (Boolean)result.get("RETURN_CODE");
      if (returnCode.booleanValue()) {
        String name = result.get("USER_NAME").toString();
        Vector v = new Vector();
        v.addElement("firstName");
        v.addElement("lastName");
        v.addElement(parent.getUserId().toString());
        whoT.setWhoV(v);
        whoT.setUpdating();
        whoT.setText(name);

        int draftOrder = 1;
        int newGroupOrder = 1;
        int pendOrder = 1;
        int banOrder = 1;
        Vector statusV = (Vector)result.get("STATUSES");
        for (int i = 0; i < statusV.size(); i++) {
          String[] oa = (String[])statusV.elementAt(i);
          if ("5".equalsIgnoreCase(oa[0])) {
            newGroupC.setText(oa[1]);
            setStatusPosition(newGroupC,Integer.parseInt(oa[2]));
          }else if ("6".equalsIgnoreCase(oa[0])) {
            draftC.setText(oa[1]);
            setStatusPosition(draftC,Integer.parseInt(oa[2]));
          }else if ("8".equalsIgnoreCase(oa[0])) {
            pendC.setText(oa[1]);
            setStatusPosition(pendC,Integer.parseInt(oa[2]));
          }else if ("11".equalsIgnoreCase(oa[0])) {
            banC.setText(oa[1]);
            setStatusPosition(banC,Integer.parseInt(oa[2]));
          }
        }
      }else {
        if (parent.getResourceBundle().get("APP_NAME").toString().equalsIgnoreCase("Seagate")) {
          draftC.setText("Pending CRA");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    loading = false;
  }

  public void setStatusPosition(JCheckBox c, int position) {
    switch(position) {
      case 1:
        groupBox1.add(c, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0));
      break;
      case 2:
        groupBox1.add(c, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
      break;
      case 3:
        groupBox1.add(c, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0));
      break;
      case 4:
        groupBox1.add(c, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
      break;
      default:
      break;
    }
  }

  public void printScreenData(){
    ReportOptionDlg rod = new ReportOptionDlg(parent.getMain());
    rod.setCmisApp(parent);

    //load requestor and approver IDs
    boolean who = whoT.getText().trim().length() > 0;
    boolean whoap = aprT.getText().trim().length() > 0;
    String requestor = "0";
    String approver = "0";
    if (who && whoT.getWhoV().size() == 3) {
      requestor = (String) whoT.getWhoV().elementAt(2);
    }else if(!who){
      requestor = "";
    }
    if (whoap && aprT.getWhoV().size() == 3) {
      approver = (String) aprT.getWhoV().elementAt(2);
    }else if(!whoap){
      approver = "";
    }
    rod.setRequestor(requestor);
    rod.setRequestorName(whoT.getText());
    rod.setApprover(approver);
    rod.setApproverName(aprT.getText());
    rod.setFacId(facC.getSelectedFacId());
    rod.setWorkArea(appChoice.getSelectedWorkAreaID());
    rod.setRequestId(reqIdT.getText().trim());
    rod.setScreen("NCT");

    String tmp = null;
    String tmp2 = "";

    tmp = BothHelpObjs.makeBlankFromNull(itemText.getText().trim());
    if (BothHelpObjs.isBlankString(tmp)) {
      tmp2 = new String("");
    }else {
      tmp2 = tmp;
    }
    rod.setSearchText(tmp2);

   boolean draft = false;
   boolean newPartNumber = false;
   boolean newGroup = false;
   boolean pendingApproval = false;
   boolean reject = false;
   boolean newMaterial = false;
   boolean newSize = false;
   boolean newApproval = false;
   boolean approvedC = false;
   boolean banned = false;
   boolean showAllRequests = false;
   rod.setDraft(draftC.isSelected());
   rod.setNewPartNumber(newPartC.isSelected());
   rod.setNewGroup(newGroupC.isSelected());
   rod.setPendingApproval(pendC.isSelected());
   rod.setReject(rejC.isSelected());
   rod.setNewMaterial(newMatC.isSelected());
   rod.setNewSize(newSizeC.isSelected());
   rod.setNewApproval(newApprovalC.isSelected());
   rod.setApprovedC(apprC.isSelected());
   rod.setBanned(banC.isSelected());
   rod.setShowAllRequests(needAppC.isSelected());

   //Nawaz 01-17-02
   rod.setWaName(this.appChoice.getSelectedItem().toString());
   rod.setFacName(this.facC.getSelectedItem().toString());

   //load status box vector
    int i = 0;
    if(draftC.isSelected()) i=i+1;          //this is actually 'Pending Approval(DRS,Raytheon,SWA...) or CRA(Seagate)'
    if(newMatC.isSelected()) i=i+2;
    if(newPartC.isSelected()) i=i+4;
    if(newGroupC.isSelected()) i=i+8;       //this is actually 'Pending QC'
    if(apprC.isSelected()) i=i+16;
    if(rejC.isSelected()) i=i+32;
    if(pendC.isSelected()) i=i+64;          //this is actually 'Pending Pricing'
    if(newSizeC.isSelected()) i=i+128;
    if(newApprovalC.isSelected()) i=i+256;
    if(banC.isSelected()) i=i+512;           //this is actually 'Pending PN'

    String temp = (new Integer(i)).toString();
    rod.setselection_status(temp);

    String n = needAppC.isSelected()?"T":"F";
    rod.setneed_approval(n);
   rod.loadIt();
  }



  public void stateChanged(String label){
    if(loading)return;
    parent.getMain().countLabel.put(new Integer(Main.NEW_CHEM_TRACKING),"");
    parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.NEW_CHEM_TRACKING)));

    if(label.equalsIgnoreCase("refresh") &&
      searchPB3.getText().equalsIgnoreCase("search")) {
      return;
    }
    searchPB3.setText(label);
  }

  void fillTable() {
     NewChemTrackThread tT = new NewChemTrackThread(this,"FillTable");
     tT.start();
  }

  void fillTableAction() {

    stateChanged("Loading ...");
    long sTime = new java.util.Date().getTime();
    ClientHelpObjs.setEnabledContainer(this,false);
    // create connection and hashtable
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.NEW_CHEM_TRACKING,parent);
    Hashtable query = new Hashtable();
    query.put("ACTION","LOAD_TABLE"); //String, String

    //load requestor and approver IDs
    requestor = whoT.getText().trim();
    approver = aprT.getText().trim();
    boolean who = whoT.getText().trim().length() > 0;
    boolean whoap = aprT.getText().trim().length() > 0;
    reqID = "0";
    aprID = "0";
    if (who && whoT.getWhoV().size() == 3) {
      reqID = (String) whoT.getWhoV().elementAt(2);
    }else if(!who){
      reqID = "";
    }
    if (whoap && aprT.getWhoV().size() == 3) {
      aprID = (String) aprT.getWhoV().elementAt(2);
    }else if(!whoap){
      aprID = "";
    }
    query.put("REQUESTOR",reqID);
    query.put("APPROVER",aprID);
    query.put("USERID",parent.getMain().getUserId().toString());

    //load facility and work area
    query.put("FACILITY",facC.getSelectedFacId());
    query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());

    //load request ID
    query.put("REQ_ID",reqIdT.getText().trim());

    //load search string
    query.put("SEARCH_STRING",itemText.getText().trim());

    // load need approval
    String n = needAppC.isSelected()?"true":"false";
    query.put("NEED_APPROVAL",n);

    //load status box vector
    int i = 0;
    if(draftC.isSelected()) i=i+1;          //this is actually 'Pending Approval(DRS,Raytheon,SWA...) or CRA(Seagate)'
    if(newMatC.isSelected()) i=i+2;
    if(newPartC.isSelected()) i=i+4;
    if(newGroupC.isSelected()) i=i+8;       //this is actually 'Pending QC'
    if(apprC.isSelected()) i=i+16;
    if(rejC.isSelected()) i=i+32;
    if(pendC.isSelected()) i=i+64;          //this is actually 'Pending Pricing'
    if(newSizeC.isSelected()) i=i+128;
    if(newApprovalC.isSelected()) i=i+256;
    if(banC.isSelected()) i=i+512;           //this is actually 'Pending PN'

    String temp = (new Integer(i)).toString();
    query.put("STATUS_SELECTION",temp);


    Hashtable result = cgi.getResultHash(query);
    if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         ClientHelpObjs.setEnabledContainer(this,true);
         return;
    }

    Vector dataV = (Vector) result.get("DATA");
    int numRecs;
    try{
      numRecs = Integer.parseInt((String)result.get("NUM_RECS"));
    }catch(Exception e) {
      numRecs = 0;
    }
    if(numRecs == 0) dataV = new Vector();
    buildTable(dataV);

    // do some display stuff...
    stateChanged("Refresh");
    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date(min));
    String tmsg = "";
    if(numRecs == 0) {
      tmsg = "Records Found: 0";
    }else{
      tmsg = "Records Found: " + numRecs + "     Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
    }
    parent.getMain().countLabel.put(new Integer(Main.NEW_CHEM_TRACKING),tmsg);
    parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.NEW_CHEM_TRACKING)));
    ClientHelpObjs.setEnabledContainer(this,true);
    ClientHelpObjs.setComboBoxUpdateUi(this);
    ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    enableHeaderInfo();
    if (numRecs == 0){
      String no = new String("No records found.");
      GenericDlg err = new GenericDlg(parent.getMain(),"Not Found",true);
      err.setMsg(no);
      err.setVisible(true);
      stateChanged("Refresh");
      return;
    }
   } //end of method

  void setColWidths() {
    trackTable.getColumn(trackTable.getColumnName(0)).setWidth(40);
    trackTable.getColumn(trackTable.getColumnName(0)).setPreferredWidth(40);
    trackTable.getColumn(trackTable.getColumnName(1)).setWidth(85);
    trackTable.getColumn(trackTable.getColumnName(1)).setPreferredWidth(85);
    trackTable.getColumn(trackTable.getColumnName(2)).setWidth(85);
    trackTable.getColumn(trackTable.getColumnName(2)).setPreferredWidth(85);
    trackTable.getColumn(trackTable.getColumnName(3)).setWidth(105);
    trackTable.getColumn(trackTable.getColumnName(3)).setPreferredWidth(105);
    trackTable.getColumn(trackTable.getColumnName(4)).setWidth(85);
    trackTable.getColumn(trackTable.getColumnName(4)).setPreferredWidth(85);
    trackTable.getColumn(trackTable.getColumnName(5)).setWidth(110);
    trackTable.getColumn(trackTable.getColumnName(5)).setPreferredWidth(110);
    trackTable.getColumn(trackTable.getColumnName(6)).setWidth(60);
    trackTable.getColumn(trackTable.getColumnName(6)).setPreferredWidth(60);
    trackTable.getColumn(trackTable.getColumnName(7)).setWidth(45);
    trackTable.getColumn(trackTable.getColumnName(7)).setPreferredWidth(45);
    trackTable.getColumn(trackTable.getColumnName(8)).setWidth(135);
    trackTable.getColumn(trackTable.getColumnName(8)).setPreferredWidth(135);
    trackTable.getColumn(trackTable.getColumnName(9)).setWidth(130);
    trackTable.getColumn(trackTable.getColumnName(9)).setPreferredWidth(130);
    trackTable.getColumn(trackTable.getColumnName(10)).setWidth(110);
    trackTable.getColumn(trackTable.getColumnName(10)).setPreferredWidth(110);
    trackTable.getColumn(trackTable.getColumnName(11)).setWidth(70);
    trackTable.getColumn(trackTable.getColumnName(11)).setPreferredWidth(70);
    trackTable.getColumn(trackTable.getColumnName(12)).setWidth(85);
    trackTable.getColumn(trackTable.getColumnName(12)).setPreferredWidth(85);
  }

  int buildTable(Vector v) {
    String[] colNames = getColNames();
    String colTypes = getColTypes();
    if(v == null || v.size() < 1) {
      NChemTrackWin_TableModel nctwModel = new NChemTrackWin_TableModel(getColNames());
      //cmisTModelTrack = new CmisTableModel(getColNames());
      sorterTrack = new TableSorter(nctwModel);
      trackTable = new NChemTrackWin_TrackTable ((TableModel) sorterTrack);
      trackTable.setParent(this);
      sorterTrack.setColTypeFlag(colTypes);
      sorterTrack.addMouseListenerToHeaderInTable(trackTable);
      trackTable.addMouseListener(new NChemTrackWin_trackTable_mouseAdapter(this));

        //Nawaz 09/19/01
      AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
      Hashtable chargeTableStyle =  BothHelpObjs.getTableStyle();
      Color color = (Color)chargeTableStyle.get("COLOR");
      Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
      Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
      Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
      Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
      Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
      renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
      //font and foreground and background color for columns heading
      String fontName = (String)chargeTableStyle.get("FONT_NAME");
      Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
      Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
      trackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = trackTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }

      panel2.remove(JSP);
      panel2.validate();
      JSP = new JScrollPane(trackTable);

      panel2.add(JSP, BorderLayout.CENTER);
      panel2.validate();
      setColWidths();

      return 0;
    }

    // build an object array from the vector
    Hashtable h = BothHelpObjs.getNewChemTrackCols();
    int cols = h.size();
    Object[][] oa1 = BothHelpObjs.get2DArrayFromVector(v,cols);
    int recsFound = oa1.length;
    //System.out.println("before cast");
    oa1 = ClientHelpObjs.castTableArray(oa1,colTypes);
    //System.out.println("after cast");

    //cmisTModelTrack = new CmisTableModel(oa1,getColNames());
    NChemTrackWin_TableModel nctwModel = new NChemTrackWin_TableModel(oa1,colNames);
    sorterTrack = new TableSorter(nctwModel);
    sorterTrack.setColTypeFlag(colTypes);
    trackTable = new NChemTrackWin_TrackTable ((TableModel) sorterTrack);
    trackTable.setParent(this);
    sorterTrack.addMouseListenerToHeaderInTable(trackTable);

      //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle =  BothHelpObjs.getTableStyle();
    Color color = (Color)chargeTableStyle.get("COLOR");
    Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    //font and foreground and background color for columns heading
    String fontName = (String)chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
    trackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = trackTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }


    //trackTable.setParent(this);

    //copied from another pgm
    trackTable.getTableHeader().setReorderingAllowed(true);
    trackTable.getTableHeader().setResizingAllowed(true);
    trackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    //trackTable.setCellSelectionEnabled(false);
    //trackTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    trackTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    // end copied part

    trackTable.addMouseListener(new NChemTrackWin_trackTable_mouseAdapter(this));
    trackTable.addKeyListener(new NChemTrackWin_trackTable_keyListener(this));

    setColWidths();
    panel2.remove(JSP);
    panel2.validate();

    JSP = new JScrollPane(trackTable);

    panel2.add(JSP, BorderLayout.CENTER);
    panel2.validate();

    return recsFound;
  }

  String[] getColNames() {
    String cust = parent.getResourceBundle().get("APP_NAME").toString();
    String[] s = {"Req ID","Requestor","Requested","Status","Facility","Work Area","Part No.","Item ID","Material Description","Manufacturer","Packaging","Mfg Part No",cust+" Req ID"};
    return s;
  }
  String getColTypes() {
    //return "21311111121";
    //return "11311111121";
    return "2131111111111";
  }

  void resetAprRel() {
    Vector v = new Vector();
    v.addElement("");
    v.addElement("");
    v.addElement("");
    aprT.setText("");
    aprT.setWhoV(v);
  }

  void setDetailButton(){
    trackSel = trackTable.getSelectedRow();
    if(trackSel > -1) {
      mrDetailB.setEnabled(true);
    }
    String s = trackTable.getModel().getValueAt(trackSel,3).toString();
    appDetailB.setEnabled(s.startsWith("Pending") || s.startsWith("Ready") || s.startsWith("Rejected") || s.startsWith("Approved"));
  }

  void trackTable_mouseClicked(MouseEvent e) {
    setDetailButton();
  }

  void stopTableEditing(){
  }

  void S_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    //action
   mrDetailB.setEnabled(false);
   appDetailB.setEnabled(false);
   fillTable();
  }

  void whoChoice_itemStateChanged(ItemEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void aprChoice_itemStateChanged(ItemEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void facC_itemStateChanged(ItemEvent e) {
    if (loading) return;
    stateChanged("Refresh");
    resetAprRel();
  }

  void appChoice_itemStateChanged(ItemEvent e) {
    //changed
    stateChanged("Refresh");
    resetAprRel();
  }

  void itemText_textValueChanged(TextEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void S_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       fillTable();
    }
  }

  void searchPB1_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       goChooseRequestor();
    }
  }

  void searchPB1_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goChooseRequestor();
  }

  void goChooseRequestor() {
    //choose requestor
    SearchPersonnel dlg = new SearchPersonnel(parent.getMain(),"Search",true);
    dlg.setGrandParent(parent);
    dlg.setFacility(facC.getSelectedFacId());
    dlg.setPersonType("PERSONNEL");
    dlg.setVisible(true);
    if (dlg.getValueId() != null){
      whoT.setText(dlg.getValueLast()+", "+dlg.getValueFirst());
      requestor = new String(dlg.getValueFirst()+" "+dlg.getValueLast());
      reqID = dlg.getValueId().toString();
      Vector v = new Vector();
      v.addElement("first Name");
      v.addElement("last Name");
      v.addElement(reqID);
      whoT.setWhoV(v);
    }
  }

  void searchPB2_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       goChooseApprover();
    }
  }


  void searchPB2_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goChooseApprover();
  }

  void goChooseApprover() {
     //choose approver
    SearchPersonnel dlg = new SearchPersonnel(parent.getMain(),"Search",true);
    dlg.setGrandParent(parent);
    String facTest = facC.getSelectedFacId();
    dlg.setPersonType("NEW_CHEM_APPROVERS");
    dlg.setVisible(true);
    if (dlg.getValueId() != null){
      aprT.setText(dlg.getValueLast()+", "+dlg.getValueFirst());
      approver = new String(dlg.getValueFirst()+" "+dlg.getValueLast());
      aprID = dlg.getValueId().toString();
      Vector v = new Vector();
      v.addElement("first Name");
      v.addElement("last Name");
      v.addElement(aprID);
      aprT.setWhoV(v);
    }
  }

  void goApprovalDetail() {
    trackSel = trackTable.getSelectedRow();
    ApprDetail dlg = new ApprDetail(parent.getMain(),"Approval Detail",true,parent);
    //dlg.setGrandParent(parent);
    dlg.setRequestId(trackTable.getModel().getValueAt(trackSel,0).toString());
    dlg.loadIt();
    dlg.show();
  }


  boolean isTableSelected(){
    trackSel = trackTable.getSelectedRow();
    if (trackSel < 0) {
      return false;
    }
    return true;
  }

  void mrDetailB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    NewChemTrackThread tT = new NewChemTrackThread(this,"NewChemDetail");
    tT.start();
  }

  void goNewChemDetail() {
    if (!isTableSelected()) return;
    trackSel = trackTable.getSelectedRow();
    parent.getMain().goNewChemMat(0,(new Integer(trackTable.getModel().getValueAt(trackSel,0).toString())).intValue(),null);
  }

  /*
  void mrDetailB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (!isTableSelected()) return;
    trackSel = trackTable.getSelectedRow();
    //if (checkIfGoodFac(trackSel)){

    parent.getMain().goNewChemMat(0,(new Integer(trackTable.getModel().getValueAt(trackSel,0).toString())).intValue(),null);
    /*} else {
       // can not go to the window
       String no = new String("This request is for a facility that you do not have access.\nPlease contact your administrator if you think you need to see it.");
       GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
       err.setMsg(no);
       err.setVisible(true);
    }

  } */

  boolean checkIfGoodFac(int row){
      if (true) return true; // method not used
      boolean ok = false;
      String fac = (String) trackTable.getModel().getValueAt(row,4);
      if (fac.trim().length()<=0) return true; // not done yet
      Hashtable catXfac = parent.getCatxFac();
      Enumeration E;
      for (E=catXfac.keys();E.hasMoreElements();){
         Hashtable tt = (Hashtable) catXfac.get(E.nextElement());
         if (tt.containsKey(fac)){
           ok = true;
           break;
         }
      }

      return ok;
  }

  void draftC_itemStateChanged(ItemEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void newMatC_itemStateChanged(ItemEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void pissC_itemStateChanged(ItemEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void newPartC_itemStateChanged(ItemEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void this_componentResized(ComponentEvent e) {
    //trackTable.setBounds(0,0,panel2.getWidth()-1,panel2.getHeight()-1);
    //trackTable.sizeColumnsToFit(false);
  }

  void this_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       //stopTableEditing();
       fillTable();
    }
  }

  void itemText_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       //stopTableEditing();
       fillTable();
     }
  }

  void pendC_itemStateChanged(ItemEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void newGroupC_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
  }

  void apprC_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
  }

  void rejC_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
  }

  void needAppC_actionPerformed(ActionEvent e) {
    enableHeaderInfo();
  }

  void enableHeaderInfo() {
    boolean b = !needAppC.isSelected();
    whoT.setEnabled(b);
    aprT.setEnabled(b);
    facC.setEnabled(b);
    appChoice.setEnabled(b);
    itemText.setEnabled(b);
    reqIdT.setEnabled(b);
    searchPB1.setEnabled(b);
    searchPB2.setEnabled(b);
    reqLabel.setEnabled(b);
    aprLabel.setEnabled(b);
    facLabel.setEnabled(b);
    appLabel.setEnabled(b);
    itemLabel.setEnabled(b);
    reqIDL.setEnabled(b);
    ClientHelpObjs.setEnabledContainer(groupBox1,b);
    if(foreground == null) {
      foreground = facC.getForeground();
    }
    if(b){
      facC.setForeground(foreground);
      appChoice.setForeground(foreground);
    }else{
      facC.setForeground(Color.gray);
      appChoice.setForeground(Color.gray);
    }
    this.repaint();
  }

  void newSizeC_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
  }

  void newApprovalC_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
  }

  void appDetailB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (!isTableSelected()) return;
    goApprovalDetail();
  }

  void banC_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
  }

}

class NChemTrackWin_trackTable_mouseAdapter extends java.awt.event.MouseAdapter {
  NChemTrackWin adaptee;

  NChemTrackWin_trackTable_mouseAdapter(NChemTrackWin adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.trackTable_mouseClicked(e);
  }
}

class NChemTrackWin_searchPB3_actionAdapter implements java.awt.event.ActionListener{
  NChemTrackWin adaptee;

  NChemTrackWin_searchPB3_actionAdapter(NChemTrackWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.S_actionPerformed(e);
  }
}



class NChemTrackWin_facC_itemAdapter implements java.awt.event.ItemListener{
  NChemTrackWin adaptee;

  NChemTrackWin_facC_itemAdapter(NChemTrackWin adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.facC_itemStateChanged(e);
  }
}

class NChemTrackWin_appChoice_itemAdapter implements java.awt.event.ItemListener{
  NChemTrackWin adaptee;

  NChemTrackWin_appChoice_itemAdapter(NChemTrackWin adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.appChoice_itemStateChanged(e);
  }
}

/** this thread is for filling the table */
class NewChemTrackThread extends Thread {
  NChemTrackWin parent;
  String action = "";
  public NewChemTrackThread(NChemTrackWin parent, String action){
     super("NewChemTrackThread");
     this.parent = parent;
     this.action = action;
  }

  public void run(){
    if ("FillTable".equalsIgnoreCase(action)) {
      parent.fillTableAction();
    }else if ("NewChemDetail".equalsIgnoreCase(action)) {
      parent.goNewChemDetail();
    }
  }
}

class NChemTrackWin_searchPB3_keyAdapter extends java.awt.event.KeyAdapter {
  NChemTrackWin adaptee;

  NChemTrackWin_searchPB3_keyAdapter(NChemTrackWin adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.S_keyPressed(e);
  }
}


/** this method is for loading the initial table */
class NChemTrackWin_LoadThread extends Thread {
 NChemTrackWin parent;

  public NChemTrackWin_LoadThread(NChemTrackWin parent){
     super("NChemTrackWin_LoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadItAction();
  }
}

class NChemTrackWin_searchPB1_actionAdapter implements java.awt.event.ActionListener{
  NChemTrackWin adaptee;

  NChemTrackWin_searchPB1_actionAdapter(NChemTrackWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchPB1_actionPerformed(e);
  }
}

class NChemTrackWin_searchPB2_actionAdapter implements java.awt.event.ActionListener{
  NChemTrackWin adaptee;

  NChemTrackWin_searchPB2_actionAdapter(NChemTrackWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchPB2_actionPerformed(e);
  }
}

class NChemTrackWin_TableModel extends DefaultTableModel {

       public NChemTrackWin_TableModel(Object[][] data, Object[] columnIds) {
          super(data,columnIds);
       }
       public NChemTrackWin_TableModel(String[] columnIds) {
          super(columnIds,0);
       }
       public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
       }
       public boolean isCellEditable(int r, int c){
            return c == 16;
       }
       public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue,row,column);
       }

}

class NChemTrackWin_TrackTable extends CmisTable {
       NChemTrackWin parent;

       public NChemTrackWin_TrackTable(Object[][] o1, Object[] o2){
         super(o1,o2);
       }
       public NChemTrackWin_TrackTable(TableModel t){
         super(t);
       }
       public NChemTrackWin_TrackTable(){
         super();
       }
       public void setParent(NChemTrackWin p) {
         parent = p;
       }

       /* 1-12-02
       public void editingStopped(ChangeEvent e){
         if (getModel().getValueAt(getEditingRow(),getEditingColumn()) == null) {
           super.editingStopped(e);
           return;
         }
       } */

       //Avoid null pointers errors
}

class NChemTrackWin_appDetailB_actionAdapter implements java.awt.event.ActionListener{
  NChemTrackWin adaptee;


  NChemTrackWin_appDetailB_actionAdapter(NChemTrackWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.appDetailB_actionPerformed(e);
  }
}

class NChemTrackWin_trackTable_keyListener implements java.awt.event.KeyListener{
  NChemTrackWin adaptee;

  NChemTrackWin_trackTable_keyListener(NChemTrackWin adaptee) {
    this.adaptee = adaptee;
  }
  public void keyPressed(KeyEvent k) {
  }
  public void keyReleased(KeyEvent k) {
    adaptee.setDetailButton();
  }
  public void keyTyped(KeyEvent k) {
  }
}

