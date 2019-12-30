
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
public class SearchGroup extends JDialog {
  XYLayout xYLayout1 = new XYLayout();


  //search
  String groupId;
  String groupDesc;

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  TextField queryT = new TextField();
  JRadioButton groupidC = new JRadioButton();
  JRadioButton groupdescC = new JRadioButton();
  ButtonGroup checkboxGroup1 = new ButtonGroup();
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

  SearchGroupLoadThread lg;

  public SearchGroup(Frame frame, String title, boolean modal) {
    super(frame, "Group Search", modal);
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

  public SearchGroup(Frame frame) {
    this(frame, "", false);
  }

  public SearchGroup(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public SearchGroup(Frame frame, String title) {
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
    groupidC.setText("Group ID");
    checkboxGroup1.add(groupidC);
    searchB.setText("Search");
    searchB.addActionListener(new SearchGroup_searchB_actionAdapter(this));
    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    okB.setText("Ok");
    okB.addActionListener(new SearchGroup_okB_actionAdapter(this));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    groupdescC.setText("Group Description");
    checkboxGroup1.add(groupdescC);
    cancelC.setText("Cancel");
    cancelC.addActionListener(new SearchGroup_cancelC_actionAdapter(this));
    cancelC.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    panel1.setLayout(xYLayout1);
    panel2.setLayout(new BorderLayout());
    panel1.add(panel2, new XYConstraints(5, 64, 490, 173));
    panel1.add(queryT, new XYConstraints(14, 12, 182, -1));
    panel1.add(groupidC, new XYConstraints(201, 12, -1, -1));
    panel1.add(groupdescC, new XYConstraints(299, 12, -1, -1));
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
    searchTable.getColumn("Description").setWidth(300);
    searchTable.getColumn("Description").setMinWidth(200);
    searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    searchTable.addMouseListener(new SearchGroup_searchTable_mouseAdapter(this));
    searchTable.sizeColumnsToFit(0);
    searchTable.revalidate();
    jPanel =  new JScrollPane(searchTable);

    panel2.add(jPanel,BorderLayout.CENTER);
    panel2.validate();
    //set check mark
    groupidC.setSelected(true);

  }

  public void loadIt(){
    lg = new SearchGroupLoadThread(this);
    lg.start();
  }

  void searchGroups(){
    //grandParent.getMain().startImage();
    String searchBy = null;
    try {
      // begin http insert
      InetAddress ip = InetAddress.getLocalHost();
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.SEARCH_INFO, grandParent);

      Hashtable myQuery = new Hashtable();
      myQuery.put("ACTION","SEARCH_GROUPS");
      myQuery.put("QUERY",queryT.getText());

      if (groupidC.isSelected()){
        searchBy = "GROUP_ID";
      } else if (groupdescC.isSelected()){
        searchBy = "GROUP_DESC";
      }
      myQuery.put("SEARCHBY",searchBy);

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
      searchTable.getColumn("Description").setWidth(300);
      searchTable.getColumn("Description").setMinWidth(200);
      searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      searchTable.addMouseListener(new SearchGroup_searchTable_mouseAdapter(this));
      panel2.remove(jPanel);
      panel2.validate();
      jPanel = new JScrollPane(searchTable);

      panel2.add(jPanel, BorderLayout.CENTER);
      this.validate();
      panel2.validate();
      //grandParent.getMain().stopImage();
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    } catch (Exception e1) { e1.printStackTrace(); }
  }

  public String getGroupId(){
     return groupId;
  }

  public String getGroupDesc(){
     return groupDesc;
  }

  void selectResult(){
    int  seaSel = searchTable.getSelectedRow();
    if (seaSel >= 0){
       groupId = (String) cmisTModelSearch.getValueAt(seaSel,0);
       groupDesc = (String) cmisTModelSearch.getValueAt(seaSel,1);
    }
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    selectResult();
    this.setVisible(false);
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    searchGroups();
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
    groupId  = (String)null;
    lg.stop();
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    groupId  = (String)null;
    lg.stop();
    setVisible(false);
  }

  void queryT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       searchGroups();
     }

  }


}

class SearchGroup_okB_actionAdapter implements java.awt.event.ActionListener{
  SearchGroup adaptee;

  SearchGroup_okB_actionAdapter(SearchGroup adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class SearchGroup_searchB_actionAdapter implements java.awt.event.ActionListener{
  SearchGroup adaptee;

  SearchGroup_searchB_actionAdapter(SearchGroup adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchB_actionPerformed(e);
  }
}

class SearchGroup_searchTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchGroup adaptee;

  SearchGroup_searchTable_mouseAdapter(SearchGroup adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.searchTable_mouseClicked(e);
  }
}

class SearchGroup_cancelC_actionAdapter implements java.awt.event.ActionListener {
  SearchGroup adaptee;

  SearchGroup_cancelC_actionAdapter(SearchGroup adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelC_actionPerformed(e);
  }
}

class SearchGroupLoadThread extends Thread {
 SearchGroup parent;

  public SearchGroupLoadThread(SearchGroup parent){
     super("SearchGroupLoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.searchGroups();
  }
}





























