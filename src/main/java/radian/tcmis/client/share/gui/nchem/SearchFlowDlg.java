
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui.nchem;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.net.*;
import javax.swing.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import com.borland.jbcl.layout.*;

public class SearchFlowDlg extends JDialog {
  XYLayout xYLayout1 = new XYLayout();


  //search
  Hashtable flowSelected = new Hashtable();

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  TextField queryT = new TextField();
  JRadioButton flowDescC = new JRadioButton();
  JButton searchB = new JButton();
  JButton okB = new JButton();


  //Table

  final String[] searchCols = {"ID","Description"};
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
  ButtonGroup radioGrp = new ButtonGroup();

  SearchFlowDlg_LoadThread lg;
  JButton applyB = new JButton();
  JComboBox flowTypeCombo = new JComboBox();
  JRadioButton flowNameC = new JRadioButton();
  StaticJLabel jLabel1 = new StaticJLabel();

  //trong 3/20
  String catalog;

  public SearchFlowDlg(Frame frame, String title, boolean modal) {
    super(frame, "Flow Down Search", modal);
    parent = frame;
    try {
      jbInit();
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public SearchFlowDlg(Frame frame) {
    this(frame, "", false);
  }

  public SearchFlowDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public SearchFlowDlg(Frame frame, String title) {
    this(frame, title, false);
  }

  public void setGrandParent(CmisApp m){
    grandParent = m;
  }

  //trong 3/20
  public void setCatalog(String catalog) {
    this.catalog = catalog;
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
    flowDescC.setText("Flow Down Description");

    //type  pafckiging quality
    flowTypeCombo.addItem("Quality");
    flowTypeCombo.addItem("Packaging");



    applyB.setText("Apply");
    applyB.addActionListener(new SearchFlowDlg_applyB_actionAdapter(this));
    applyB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));
    flowNameC.setText("Flow Down Name");
    jLabel1.setText("Type:");

    searchB.setText("Search");
    searchB.addActionListener(new SearchFlowDlg_searchB_actionAdapter(this));
    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    okB.setText("Ok");
    okB.addActionListener(new SearchFlowDlg_okB_actionAdapter(this));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    cancelC.setText("Cancel");
    cancelC.addActionListener(new SearchFlowDlg_cancelC_actionAdapter(this));
    cancelC.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    panel1.setLayout(xYLayout1);
    panel2.setLayout(new BorderLayout());
    panel1.add(panel2, new XYConstraints(5, 64, 490, 173));
    panel1.add(queryT, new XYConstraints(6, 15, 140, -1));
    panel1.add(flowDescC, new XYConstraints(149, 5, -1, -1));
    panel1.add(searchB, new XYConstraints(18, 254, 102, 35));
    panel1.add(applyB, new XYConstraints(137, 254, 102, 35));
    panel1.add(okB, new XYConstraints(255, 254, 102, 35));
    panel1.add(cancelC, new XYConstraints(374, 254, 102, 35));
    panel1.add(flowTypeCombo, new XYConstraints(337, 27, 157, 23));
    panel1.add(flowNameC, new XYConstraints(149, 27, 154, 23));
    panel1.add(jLabel1, new XYConstraints(338, 8, -1, -1));

    radioGrp.add(flowNameC);
    radioGrp.add(flowDescC);

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
    searchTable.getColumn("Description").setWidth(300);
    searchTable.getColumn("Description").setMinWidth(200);
    searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    searchTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
    searchTable.sizeColumnsToFit(0);
    searchTable.revalidate();
    searchTable.addMouseListener(new SearchFlowDlg_searchTable_mouseAdapter(this));
    jPanel =  new JScrollPane(searchTable);

    panel2.add(jPanel,BorderLayout.CENTER);
    panel2.validate();
    //set check mark
    flowDescC.setSelected(true);

  }

  public void loadIt(){
    lg = new SearchFlowDlg_LoadThread(this);
    lg.start();
  }

  void searchFlowDowns(){
    CursorChange.setCursor(this.getContentPane(),Cursor.WAIT_CURSOR);

    //grandParent.getMain().startImage();
    String searchBy = null;
    try {
      // begin http insert
      InetAddress ip = InetAddress.getLocalHost();
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.SEARCH_INFO, grandParent);

      Hashtable myQuery = new Hashtable();
      myQuery.put("ACTION","SEARCH_FLOW_DOWN");
      myQuery.put("QUERY",queryT.getText());
      myQuery.put("CATALOG",catalog); //trong 3/20 telling the search object which catalog to search

      if (flowNameC.isSelected()){
        searchBy = "FLOW_DOWN";
      } else if (flowDescC.isSelected()){
        searchBy = "FLOW_DOWN_DESC";
      }
      myQuery.put("SEARCHBY",searchBy);
      myQuery.put("TYPE",(String) flowTypeCombo.getSelectedItem());

      //if (grandParent.getResourceBundle().getString("DEBUG").equals("true")) // // System.out.println("query:" + myQuery);
      Hashtable result = cgi.getResultHash(myQuery);
      Vector v = (Vector)result.get("DATA");
      for(int q=0;q<v.size();q++) {
        if(((String)v.elementAt(q)).equalsIgnoreCase("All")){
          v.removeElementAt(q+1);
          v.removeElementAt(q);
          break;
        }
      }
      Object[][] data = HttpFormatter.vectorTo2DArray(v,2);

      boolean boing = false;
      if (data == null || data.length == 0){
       String no = new String("No records found.");
       GenericDlg err = new GenericDlg(parent,"Not Found",true);
       err.setMsg(no);
       ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
       boing = true;
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
      searchTable.getColumn("Description").setWidth(300);
      searchTable.getColumn("Description").setMinWidth(200);
      searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      searchTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
      searchTable.addMouseListener(new SearchFlowDlg_searchTable_mouseAdapter(this));
      panel2.remove(jPanel);
      panel2.validate();
      jPanel = new JScrollPane(searchTable);

      panel2.add(jPanel, BorderLayout.CENTER);
      this.validate();
      panel2.validate();
      //grandParent.getMain().stopImage();
      if(!boing) ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));

      CursorChange.setCursor(this.getContentPane(),Cursor.DEFAULT_CURSOR);
    } catch (Exception e1) { e1.printStackTrace(); }
  }

  public String getFlowName(String desc){
     for (Enumeration E=flowSelected.keys();E.hasMoreElements();){
        String k = E.nextElement().toString();
        if (flowSelected.get(k).toString().equals(desc)) return k;
     }
     return null;
  }

  public String getFlowDesc(String k){
     return (String) flowSelected.get(k);
  }

  public Hashtable getFlowDown(){
     return flowSelected;
  }

  void selectResult(){
    int[]  seaSel = searchTable.getSelectedRows();
    if (seaSel.length <= 0) return;

    for (int i=0;i<seaSel.length;i++){
       String n = (String) cmisTModelSearch.getValueAt(seaSel[i],0);
       String d = (String) cmisTModelSearch.getValueAt(seaSel[i],1);
       if (flowSelected.containsKey(n)) flowSelected.remove(n);
       flowSelected.put(n,d);
    }
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    selectResult();
    this.setVisible(false);
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    loadIt();
  }


  void searchTable_mouseClicked(MouseEvent e) {
     //selected result
     if (e.getClickCount() == 2) {
       selectResult();
       this.setVisible(false);
     }
  }

  void cancelC_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    //flowSelected = new Hashtable();
    //lg.stop();
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    flowSelected = new Hashtable();
    lg.stop();
    setVisible(false);
  }

  void queryT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       loadIt();
     }

  }

  void applyB_actionPerformed(ActionEvent e) {
     selectResult();
  }


}

class SearchFlowDlg_okB_actionAdapter implements java.awt.event.ActionListener{
  SearchFlowDlg adaptee;

  SearchFlowDlg_okB_actionAdapter(SearchFlowDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class SearchFlowDlg_searchB_actionAdapter implements java.awt.event.ActionListener{
  SearchFlowDlg adaptee;

  SearchFlowDlg_searchB_actionAdapter(SearchFlowDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchB_actionPerformed(e);
  }
}

class SearchFlowDlg_searchTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchFlowDlg adaptee;

  SearchFlowDlg_searchTable_mouseAdapter(SearchFlowDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.searchTable_mouseClicked(e);
  }
}

class SearchFlowDlg_cancelC_actionAdapter implements java.awt.event.ActionListener{
  SearchFlowDlg adaptee;

  SearchFlowDlg_cancelC_actionAdapter(SearchFlowDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelC_actionPerformed(e);
  }
}

class SearchFlowDlg_LoadThread extends Thread {
 SearchFlowDlg parent;

  public SearchFlowDlg_LoadThread(SearchFlowDlg parent){
     super("SearchFlowDlg_LoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.searchFlowDowns();
  }
}

class SearchFlowDlg_applyB_actionAdapter implements java.awt.event.ActionListener {
  SearchFlowDlg adaptee;


  SearchFlowDlg_applyB_actionAdapter(SearchFlowDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.applyB_actionPerformed(e);
  }
}