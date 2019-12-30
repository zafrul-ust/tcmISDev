

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
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class SearchAddress extends JDialog {
  XYLayout xYLayout1 = new XYLayout();


  //search
  String locId;
  String locAdd;

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JTextField queryT = new JTextField();
  JRadioButton facIdC = new JRadioButton();
  JRadioButton facNameC = new JRadioButton();
  ButtonGroup checkboxGroup1 = new ButtonGroup();
  JButton searchB = new JButton();
  JButton okB = new JButton();


  //Table

  final String[] searchCols = {"ID","Address"};
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

  SearchAddressLoadThread lg;

  public SearchAddress(Frame frame, String title, boolean modal) {
    super(frame, "Location Search", modal);
    parent = frame;
    try {
      jbInit();
      //synchronized (this)
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public SearchAddress(Frame frame) {
    this(frame, "", false);
  }

  public SearchAddress(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public SearchAddress(Frame frame, String title) {
    this(frame, title, false);
  }

  public void setGrandParent(CmisApp m){
    grandParent = m;
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
    facIdC.setText("Location ID");
    checkboxGroup1.add(facIdC);
    searchB.setText("Search");
    searchB.addActionListener(new SearchAddress_searchB_actionAdapter(this));
    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    okB.setText("Ok");
    okB.addActionListener(new SearchAddress_okB_actionAdapter(this));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    facNameC.setText("Location Address");
    checkboxGroup1.add(facNameC);
    cancelC.setText("Cancel");
    cancelC.addActionListener(new SearchAddress_cancelC_actionAdapter(this));
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

    searchTable.getColumn("ID").setMinWidth(90);
    searchTable.getColumn("Address").setWidth(300);
    searchTable.getColumn("Address").setMinWidth(200);
    searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    searchTable.sizeColumnsToFit(0);
    searchTable.revalidate();

    searchTable.addMouseListener(new SearchAddress_searchTable_mouseAdapter(this));
    jPanel =  new JScrollPane(searchTable);

    panel2.add(jPanel,BorderLayout.CENTER);
    panel2.validate();
    //set check mark
    facIdC.setSelected(true);

     //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

  }

  public void loadIt(){
    lg = new SearchAddressLoadThread(this);
    lg.start();
  }

  void SearchAddresss(){
    //3-20-02 grandParent.getMain().startImage();
    String searchBy = null;
    try {

      // begin http insert
      InetAddress ip = InetAddress.getLocalHost();
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.SEARCH_INFO, grandParent);

      if (facIdC.isSelected()){
        searchBy = "LOCATION_ID";
      } else if (facNameC.isSelected()){
        searchBy = "LOCATION_ADDRESS";
      }
      Hashtable myQuery = new Hashtable();
      myQuery.put("ACTION","SEARCH_ADDRESS");
      myQuery.put("SEARCHBY",searchBy);
      myQuery.put("QUERY",queryT.getText());

      Hashtable result = cgi.getResultHash(myQuery);
      Vector v = (Vector)result.get("DATA");
      Object[][] data = HttpFormatter.vectorTo2DArray(v,2);

      //End http insert

      /*  RMI code
      LocationInt f = (LocationInt) grandParent.getDataGetter().getRemoteObject(RemoteObjectNames.LOCATION);
      if (facIdC.getState()){
        searchBy = "LOCATION_ID";
      } else if (facNameC.getState()){
        searchBy = "LOCATION_ADDRESS";
      }
      Object[][] data;
      data = f.getAllLocations(queryT.getText(),searchBy); */

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

      searchTable.getColumn("ID").setMinWidth(90);
      searchTable.getColumn("Address").setWidth(300);
      searchTable.getColumn("Address").setMinWidth(200);
      searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      searchTable.addMouseListener(new SearchAddress_searchTable_mouseAdapter(this));
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

  public String getLocId(){
     return locId;
  }

  public String getLocAddress(){
     return locAdd;
  }

  void selectResult(){
    int  seaSel = searchTable.getSelectedRow();
    if (seaSel >= 0){
       locId = (String) cmisTModelSearch.getValueAt(seaSel,0);
       locAdd = (String) cmisTModelSearch.getValueAt(seaSel,1);
    }
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    selectResult();
    setVisible(false);
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    SearchAddresss();
  }


  void searchTable_mouseClicked(MouseEvent e) {
     //selected result
     selectResult();
     if (e.getClickCount() == 2) {
       this.setVisible(false);
     }
  }

  void cancelC_actionPerformed(ActionEvent e) {
    locId  = (String)null;
    lg.stop();
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    locId  = (String)null;
    lg.stop();
    this.setVisible(false);
  }

  void queryT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       SearchAddresss();
     }

  }

}

class SearchAddress_okB_actionAdapter implements java.awt.event.ActionListener{
  SearchAddress adaptee;

  SearchAddress_okB_actionAdapter(SearchAddress adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class SearchAddress_searchB_actionAdapter implements java.awt.event.ActionListener{
  SearchAddress adaptee;

  SearchAddress_searchB_actionAdapter(SearchAddress adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchB_actionPerformed(e);
  }
}

class SearchAddress_searchTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchAddress adaptee;

  SearchAddress_searchTable_mouseAdapter(SearchAddress adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.searchTable_mouseClicked(e);
  }
}

class SearchAddress_cancelC_actionAdapter implements java.awt.event.ActionListener{
  SearchAddress adaptee;

  SearchAddress_cancelC_actionAdapter(SearchAddress adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelC_actionPerformed(e);
  }
}

class SearchAddressLoadThread extends Thread {
 SearchAddress parent;

  public SearchAddressLoadThread(SearchAddress parent){
     super("SearchAddressLoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.SearchAddresss();
  }
}




























