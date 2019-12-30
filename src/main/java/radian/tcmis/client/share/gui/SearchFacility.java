
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
import java.util.*;
import java.net.*;

import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class SearchFacility extends JDialog {
  XYLayout xYLayout1 = new XYLayout();


  //search
  //String facId;
  //String facName;
  Vector facIds;
  Vector facNames;

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  TextField queryT = new TextField();
  JRadioButton facIdC = new JRadioButton();
  JRadioButton facNameC = new JRadioButton();
  ButtonGroup checkboxGroup1 = new ButtonGroup();
  JButton searchB = new JButton();
  JButton okB = new JButton();
  boolean allowMult = false;
  boolean restrictToAdminFacs = false;


  //Table

  final String[] searchCols = {"ID","Name"};
  final Long colTypeFlag = new Long("11");

  int searchEditableFlag = 0;
  Frame parent;
  CmisApp grandParent;

  CmisTable searchTable = new CmisTable();

  TableOrganizer tOrg = new TableOrganizer();
  TableSorter  sorterSearch = new TableSorter();
  CmisTableModel cmisTModelSearch = new CmisTableModel();
  JScrollPane jPanel = new JScrollPane();
  JButton cancelC = new JButton();

  SearchFacilityLoadThread lg ;

  public SearchFacility(Frame frame, String title, boolean modal) {
    super(frame, "Facility Search", true);
    parent = frame;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public SearchFacility(Frame frame) {
    this(frame, "", false);
  }

  public SearchFacility(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public SearchFacility(Frame frame, String title) {
    this(frame, title, false);
  }

  public void setGrandParent(CmisApp m){
    grandParent = m;
  }

  public void setMultipleSelection(boolean b) {
    this.allowMult = b;
    if(allowMult) {
      searchTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
  }

  public boolean getMultipleSelection() {
    return this.allowMult;
  }

  private void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
*/
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
    facIdC.setText("Facility ID");
    checkboxGroup1.add(facIdC);
    searchB.setText("Search");
    searchB.addActionListener(new SearchFacility_searchB_actionAdapter(this));
    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    okB.setText("Ok");
    okB.addActionListener(new SearchFacility_okB_actionAdapter(this));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    facNameC.setText("Facility Name");
    checkboxGroup1.add(facNameC);
    cancelC.setText("Cancel");
    cancelC.addActionListener(new SearchFacility_cancelC_actionAdapter(this));
    cancelC.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    panel1.setLayout(xYLayout1);
    panel2.setLayout(new BorderLayout());
    panel1.add(panel2, new XYConstraints(5, 64, 490, 173));
    panel1.add(queryT, new XYConstraints(14, 12, 182, -1));
    panel1.add(facIdC, new XYConstraints(201, 12, -1, -1));
    panel1.add(facNameC, new XYConstraints(299, 12, -1, -1));
    panel1.add(searchB, new XYConstraints(40, 254, 102, 35));
    panel1.add(okB, new XYConstraints(196, 254, 102, 35));
    panel1.add(cancelC, new XYConstraints(352, 254, 102, 35));
    //build table
    buildTable();

    this.getContentPane().add(panel1);

    pack();

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    CenterComponent.centerCompOnScreen(this);
  }

  void buildTable(){
    cmisTModelSearch = new CmisTableModel(searchCols);
    sorterSearch = new TableSorter(cmisTModelSearch);
    sorterSearch.setColTypeFlag(colTypeFlag.longValue());
    searchTable = new CmisTable(sorterSearch);
    tOrg = new TableOrganizer(searchTable);
    searchTable = (CmisTable) tOrg.getJTable();

    if(allowMult) {
      searchTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    searchTable.getColumn("ID").setMinWidth(90);
    searchTable.getColumn("Name").setWidth(300);
    searchTable.getColumn("Name").setMinWidth(200);
    searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    searchTable.sizeColumnsToFit(0);
    searchTable.revalidate();
    searchTable.addMouseListener(new SearchFacility_searchTable_mouseAdapter(this));
    jPanel =  new JScrollPane(searchTable);


    panel2.add(jPanel,BorderLayout.CENTER);
    panel2.validate();
    //set check mark
    facIdC.setSelected(true);

  }

  public void loadIt(){
    lg = new SearchFacilityLoadThread(this);
    lg.start();
  }

  void SearchFacilities(){
    //3-20-02 grandParent.getMain().startImage();
    try {
    // begin http insert
    InetAddress ip = InetAddress.getLocalHost();
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.SEARCH_INFO, grandParent);

    Hashtable myQuery = new Hashtable();
    myQuery.put("ACTION","SEARCH_FACILITY");
    myQuery.put("QUERY",queryT.getText());

    String searchBy = null;
    if (facIdC.isSelected()){
      searchBy = "FACILITY_ID";
    }else if (facNameC.isSelected()){
      searchBy = "FACILITY_NAME";
    }
    myQuery.put("SEARCHBY",searchBy);

    //if (grandParent.getResourceBundle().getString("DEBUG").equals("true")) // // System.out.println("query:" + myQuery);
    Hashtable result = cgi.getResultHash(myQuery);
    Vector v = (Vector)result.get("DATA");
    Object[][] data = HttpFormatter.vectorTo2DArray(v,2);
    if(restrictToAdminFacs && data != null && data.length > 1){
      Vector a = grandParent.getGroupMembership().getAdminFacilities();
      Vector va = new Vector();
      for(int i=0;i<a.size();i++){
        String myFac = a.elementAt(i).toString();
        for(int j=0;j<data.length;j++){
          if(data[j][0].toString().equalsIgnoreCase(myFac)){
            va.addElement(data[j][0]);
            va.addElement(data[j][1]);
            break;
          }
        }
      }
      data = BothHelpObjs.get2DArrayFromVector(va,2);
    }
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
     if(allowMult) {
       searchTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
     }
     searchTable.getColumn("ID").setMinWidth(90);
     searchTable.getColumn("Name").setWidth(300);
     searchTable.getColumn("Name").setMinWidth(200);
     searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
     searchTable.addMouseListener(new SearchFacility_searchTable_mouseAdapter(this));
     panel2.remove(jPanel);
     panel2.validate();
     jPanel = new JScrollPane(searchTable);

     panel2.add(jPanel, BorderLayout.CENTER);
     this.validate();
     panel2.validate();
     //3-20-02 grandParent.getMain().stopImage();
     ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    } catch (Exception e1) { e1.printStackTrace(); }
  }
  public Vector getFacIds(){
     buildVectors();
     return facIds;
  }

  public Vector getFacNames(){
     buildVectors();
     return facNames;
  }

  public String getFacId(){
      buildVectors();
    return (String)facIds.elementAt(0);
  }

  public String getFacName(){
     buildVectors();
     return (String)facNames.elementAt(0);
  }

  public void restrictToAdminFacs(boolean b){
    restrictToAdminFacs = b;
  }

  void buildVectors() {
    facIds = new Vector();
    facNames = new Vector();
    if(searchTable.getSelectedRowCount() < 1) {
      facIds.addElement("");
      facNames.addElement("");
      return;
    }
    int[] seaSel = searchTable.getSelectedRows();
    int rows = searchTable.getSelectedRowCount();
    for(int q=0;q<rows;q++) {
      facIds.insertElementAt((String) cmisTModelSearch.getValueAt(seaSel[q],0),q);
      facNames.insertElementAt((String) cmisTModelSearch.getValueAt(seaSel[q],1),q);
    }
  }




  void selectResult(){
  if (true) return;
    facIds = new Vector();
    facNames = new Vector();
    int[] seaSel = searchTable.getSelectedRows();
    int rows = searchTable.getSelectedRowCount();
    for(int q=0;q<rows;q++) {
      facIds.insertElementAt((String) cmisTModelSearch.getValueAt(seaSel[q],0),q);
      facNames.insertElementAt((String) cmisTModelSearch.getValueAt(seaSel[q],1),q);
    }
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(searchTable.getSelectedRowCount() > 0) {
      selectResult();
    }
    lg.stop();
    this.setVisible(false);
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    SearchFacilities();
  }


  void searchTable_mouseClicked(MouseEvent e) {
     //selected result
     selectResult();
     if (e.getClickCount() == 2) {
       this.setVisible(false);
     }
  }

  void cancelC_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    searchTable.clearSelection();
    lg.stop();
    setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    facIds.setElementAt((String)null,0);
    lg.stop();
    setVisible(false);
  }

  void queryT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       SearchFacilities();
     }
  }

}

class SearchFacility_okB_actionAdapter implements java.awt.event.ActionListener{
  SearchFacility adaptee;

  SearchFacility_okB_actionAdapter(SearchFacility adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class SearchFacility_searchB_actionAdapter implements java.awt.event.ActionListener{
  SearchFacility adaptee;

  SearchFacility_searchB_actionAdapter(SearchFacility adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchB_actionPerformed(e);
  }
}

class SearchFacility_searchTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchFacility adaptee;

  SearchFacility_searchTable_mouseAdapter(SearchFacility adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.searchTable_mouseClicked(e);
  }
}

class SearchFacility_cancelC_actionAdapter implements java.awt.event.ActionListener{
  SearchFacility adaptee;

  SearchFacility_cancelC_actionAdapter(SearchFacility adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelC_actionPerformed(e);
  }
}

class SearchFacilityLoadThread extends Thread {
 SearchFacility parent;

  public SearchFacilityLoadThread(SearchFacility parent){
     super("SearchFacilityLoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.SearchFacilities();
  }
}





























