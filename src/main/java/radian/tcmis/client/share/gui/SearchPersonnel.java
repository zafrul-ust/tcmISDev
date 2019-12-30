

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.GridBagConstraints2;

import java.util.*;
import java.net.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class SearchPersonnel extends JDialog {
  //Table
  final String[] searchCols = {"User ID","Login Name","First Name","Last Name","Phone","Email"};
  final Long colTypeFlag = new Long("211111");
  final int[] colWidth = {0,90,190,190,20,20};
  static final int PERSONNEL_ID_COL = 0;
  static final int LOGIN_NAME_COL = 1;
  static final int FIRST_NAME_COL = 2;
  static final int LAST_NAME_COL = 3;
  static final int PHONE_COL = 4;
  static final int EMAIL_COL = 5;

  int searchEditableFlag = 0;
  Frame parent;
  CmisApp grandParent;
  int row;

  CmisTable searchTable = new CmisTable();

  TableOrganizer tOrg = new TableOrganizer();
  TableSorter  sorterSearch = new TableSorter();
  CmisTableModel cmisTModelSearch = new CmisTableModel();
  JScrollPane jPanel = new JScrollPane();

  //search
  String personType;
  String facility;
  String valueLast;
  String valueFirst;
  Integer valueId;
  String phone;
  String email;

  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JTextField queryT = new JTextField();
  JRadioButton lastC = new JRadioButton();
  JRadioButton firstC = new JRadioButton();
  JRadioButton idC = new JRadioButton();
  ButtonGroup checkboxGroup1 = new ButtonGroup();
  JButton searchB = new JButton();
  JButton cancelB = new JButton();
  JButton okB = new JButton();

  StaticJLabel facL = new StaticJLabel();
  JComboBox facC = new JComboBox();
  Vector facilityIDList = null;
  boolean okFlag = false;

  public SearchPersonnel(Frame frame, String title, boolean modal) {
    super(frame, "Name Search", modal);
    parent = frame;
    try {
      jbInit();

      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public SearchPersonnel(Frame frame) {
    this(frame, "", false);
  }

  public SearchPersonnel(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public SearchPersonnel(Frame frame, String title) {
    this(frame, title, false);
  }

  public void setGrandParent(CmisApp m){
    grandParent = m;
  }

  public boolean getOkFlag() {
    return okFlag;
  } //end of method

  private void jbInit() throws Exception{
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    xYLayout1.setWidth(500);
    xYLayout1.setHeight(300);
    queryT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        queryT_keyPressed(e);
      }
    });
    lastC.setText("Last Name");
    checkboxGroup1.add(lastC);
    searchB.setText("Search");
    searchB.addActionListener(new SearchPersonnel_searchB_actionAdapter(this));
    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    cancelB.setText("Cancel");
    cancelB.addActionListener(new SearchPersonnel_cancelB_actionAdapter(this));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    okB.setText("Ok");
    okB.addActionListener(new SearchPersonnel_okB_actionAdapter(this));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    firstC.setText("First Name");
    checkboxGroup1.add(firstC);
    idC.setText("Login Name");
    checkboxGroup1.add(idC);
    facL.setText("Facility: ");
    panel1.setLayout(xYLayout1);
    panel2.setLayout(new BorderLayout());
    panel1.add(facL, new XYConstraints(201, 12, -1, -1));
    panel1.add(facC, new XYConstraints(240, 8, 150, -1));
    panel1.add(queryT, new XYConstraints(14, 35, 182, -1));
    panel1.add(lastC, new XYConstraints(201, 35, -1, -1));
    panel1.add(firstC, new XYConstraints(299, 35, -1, -1));
    panel1.add(idC, new XYConstraints(391, 35, -1, -1));
    panel1.add(panel2, new XYConstraints(5, 64, 490, 173));
    panel1.add(searchB, new XYConstraints(54, 256, 102, 35));
    panel1.add(okB, new XYConstraints(191, 256, 102, 35));
    panel1.add(cancelB, new XYConstraints(328, 256, 102, 35));
    //build table
    buildTable();
    queryT.requestFocus();
    this.getContentPane().add(panel1);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    this.pack();
    CenterComponent.centerCompOnScreen(this);
  }

  void buildTable(){
    cmisTModelSearch = new CmisTableModel(searchCols);
    sorterSearch = new TableSorter(cmisTModelSearch);
    sorterSearch.setColTypeFlag(colTypeFlag.longValue());
    searchTable = new CmisTable(sorterSearch);
    tOrg = new TableOrganizer(searchTable);
    searchTable = (CmisTable) tOrg.getJTable();

    searchTable.getColumn("User ID").setMinWidth(0);
    searchTable.getColumn("User ID").setMaxWidth(0);
    searchTable.getColumn("User ID").setPreferredWidth(0);
    searchTable.getColumn("Login Name").setWidth(90);
    searchTable.getColumn("Login Name").setPreferredWidth(90);
    searchTable.getColumn("First Name").setWidth(200);
    searchTable.getColumn("Last Name").setWidth(200);
    searchTable.getColumn("First Name").setPreferredWidth(200);
    searchTable.getColumn("Last Name").setPreferredWidth(200);
    searchTable.addMouseListener(new SearchPersonnel_searchTable_mouseAdapter(this));

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
    searchTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = searchTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    searchTable.revalidate();

    jPanel =  new JScrollPane(searchTable);

    panel2.add(jPanel,BorderLayout.CENTER);
    panel2.validate();

    //set check mark
    lastC.setSelected(true);
  }

  public void setFacility(String fac){
     facility = fac;
  }

  public void setPersonType(String type){
     personType = type;
     facC = ClientHelpObjs.loadChoiceFromVector(facC,grandParent.userFacilityNameList);
     //facilityIDList = grandParent.userFacilityIDList;
     facilityIDList = new Vector(grandParent.userFacilityIDList.size());
     for (int i = 0; i < grandParent.userFacilityIDList.size(); i++) {
      facilityIDList.addElement((String)grandParent.userFacilityIDList.elementAt(i));
     }
     facC.insertItemAt("All Facilities",0);
     facC.setSelectedIndex(0);
     if (!"All".equalsIgnoreCase((String)facilityIDList.firstElement())) {
      facilityIDList.insertElementAt("All",0);
     }
  }

  public String getValueFirst(){
     return valueFirst;
  }
  public String getValueLast(){
     return valueLast;
  }
  public Vector getValues(){
     Vector temp = new Vector();
     temp.addElement(valueLast);
     temp.addElement(valueFirst);
     temp.addElement(valueId);
     return temp;
  }

  public Integer getValueId(){
     return valueId;
  }

  public String getPhone(){
     return phone;
  }

  public String getEmail(){
     return email;
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    valueLast = null;
    valueFirst = null;
    valueId  = null;
    phone = null;
    email = null;
    okFlag = false;
    this.setVisible(false);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    int  seaSel = searchTable.getSelectedRow();
    if (seaSel >= 0){
      Integer id = new Integer(cmisTModelSearch.getValueAt(seaSel,PERSONNEL_ID_COL).toString());
      valueLast = cmisTModelSearch.getValueAt(seaSel,LAST_NAME_COL).toString();
      valueFirst = cmisTModelSearch.getValueAt(seaSel,FIRST_NAME_COL).toString();
      phone = cmisTModelSearch.getValueAt(seaSel,PHONE_COL).toString();
      email = cmisTModelSearch.getValueAt(seaSel,EMAIL_COL).toString();
      valueId = id;
     }
    okFlag = true;
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    valueLast = null;
    valueFirst = null;
    valueId  = null;
    phone = null;
    email = null;
    okFlag = false;
    setVisible(false);
  }

  void searchB_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    doSearch();
  }

  void doSearch() {
    String searchBy;
    try {
      // begin http insert
      InetAddress ip = InetAddress.getLocalHost();
      TcmisHttpConnection cgi;
      Hashtable myQuery = new Hashtable();
      if ("PERSONNEL".equalsIgnoreCase(personType)) {
        cgi = new TcmisHttpConnection("SearchInfoNew", grandParent);
        myQuery.put("ACTION","SEARCH_PERSONNEL");
        myQuery.put("FACILITY_ID",(String)facilityIDList.elementAt(facC.getSelectedIndex()));
        myQuery.put("SEARCH_TEXT",queryT.getText());
        myQuery.put("USER_ID",grandParent.getUserId());
      }else {
        cgi = new TcmisHttpConnection(TcmisHttpConnection.SEARCH_INFO, grandParent);
        myQuery.put("ACTION","SEARCH_PERSONNEL");
        myQuery.put("PERSONTYPE",personType);
        myQuery.put("FACILITY",facility==null?"":facility);
        myQuery.put("QUERY",queryT.getText());
      }

      if (lastC.isSelected()){
        searchBy = "LASTNAME";
      } else if (firstC.isSelected()){
        searchBy = "FIRSTNAME";
      } else {
        searchBy = "ID";
      }
      myQuery.put("SEARCHBY",searchBy);

      Hashtable result = cgi.getResultHash(myQuery);
      Vector v = (Vector)result.get("DATA");
      Object[][] data = HttpFormatter.vectorTo2DArray(v,6);

      if (data == null || data.length == 0){
       String no = new String("No records found.");
       GenericDlg err = new GenericDlg(parent,"Not Found",true);
       err.setMsg(no);
       err.setVisible(true);
       cmisTModelSearch = new CmisTableModel(searchCols);
     } else  {
       cmisTModelSearch = new CmisTableModel(searchCols,data);
     }


     sorterSearch = new TableSorter(cmisTModelSearch);
     sorterSearch.setColTypeFlag(colTypeFlag.longValue());
     searchTable = new CmisTable (sorterSearch);
     tOrg = new TableOrganizer(searchTable);
     searchTable = (CmisTable) tOrg.getJTable();
     searchTable.setColTypeFlag(colTypeFlag.longValue());


     searchTable.getColumn("User ID").setMinWidth(0);
     searchTable.getColumn("User ID").setMaxWidth(0);
     searchTable.getColumn("User ID").setPreferredWidth(0);
     searchTable.getColumn("Login Name").setWidth(90);
     searchTable.getColumn("Login Name").setPreferredWidth(90);
     searchTable.getColumn("First Name").setWidth(200);
     searchTable.getColumn("Last Name").setWidth(200);
     searchTable.getColumn("First Name").setPreferredWidth(200);
     searchTable.getColumn("Last Name").setPreferredWidth(200);
     searchTable.getColumn("Phone").setMinWidth(70);
     searchTable.getColumn("Phone").setMaxWidth(70);
     searchTable.getColumn("Phone").setPreferredWidth(70);
     searchTable.getColumn("Email").setMinWidth(70);
     searchTable.getColumn("Email").setMaxWidth(70);
     searchTable.getColumn("Email").setPreferredWidth(70);

     searchTable.addMouseListener(new SearchPersonnel_searchTable_mouseAdapter(this));

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
      searchTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = searchTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }
     searchTable.revalidate();
     panel2.remove(jPanel);
     panel2.validate();
     jPanel =  new JScrollPane(searchTable);

     panel2.add(jPanel, BorderLayout.CENTER);
     this.validate();
     panel2.validate();
     ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    } catch (Exception e1) { e1.printStackTrace(); }
  }

  void searchTable_mouseClicked(MouseEvent e) {
     //selected result
     int  seaSel = searchTable.getSelectedRow();
     if (seaSel >= 0){
       Integer id = new Integer(cmisTModelSearch.getValueAt(seaSel,PERSONNEL_ID_COL).toString());
       valueLast = cmisTModelSearch.getValueAt(seaSel,LAST_NAME_COL).toString();
       valueFirst = cmisTModelSearch.getValueAt(seaSel,FIRST_NAME_COL).toString();
       phone = cmisTModelSearch.getValueAt(seaSel,PHONE_COL).toString();
       email = cmisTModelSearch.getValueAt(seaSel,EMAIL_COL).toString();
       valueId = id;
       okFlag = true;
     }
     if (e.getClickCount() == 2) {
       this.setVisible(false);
     }

  }

  void queryT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       doSearch();
     }
  }
}

class SearchPersonnel_cancelB_actionAdapter implements java.awt.event.ActionListener{
  SearchPersonnel adaptee;

  SearchPersonnel_cancelB_actionAdapter(SearchPersonnel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}
class SearchPersonnel_okB_actionAdapter implements java.awt.event.ActionListener{
  SearchPersonnel adaptee;

  SearchPersonnel_okB_actionAdapter(SearchPersonnel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class SearchPersonnel_searchB_actionAdapter implements java.awt.event.ActionListener{
  SearchPersonnel adaptee;

  SearchPersonnel_searchB_actionAdapter(SearchPersonnel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchB_actionPerformed(e);
  }
}

class SearchPersonnel_searchTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchPersonnel adaptee;

  SearchPersonnel_searchTable_mouseAdapter(SearchPersonnel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.searchTable_mouseClicked(e);
  }
}
