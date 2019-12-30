package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.*;
//import com.borland.jbcl.view.*;
import java.beans.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class PartNoSelectDlg extends JDialog {
  CmisApp cmis;
  String listId;
  String listDesc;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel bottomP = new JPanel();
  JPanel topP = new JPanel();
  JButton okB = new JButton();
  XYLayout xYLayout2 = new XYLayout();
  JPanel textP = new JPanel();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();
  JButton cancelB = new JButton();
  CmisTable myTable;
  JButton searchB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  JTextField queryT = new JTextField();
  StaticJLabel jLabel2 = new StaticJLabel();
  //JComboBox catalogC = new JComboBox();
  boolean canceled = false;
  String partNo = "";
  String[] colHead = new String[]{"Item ID","Part No","Description","Grade","Manufacturer"};
  Vector vID = null;
  DbTableModel model = null;
  String facilityID = null;

  public PartNoSelectDlg(CmisApp cmis, String facDesc) {
    super(cmis.getMain(), "Search part no for: "+facDesc, true);
    this.cmis = cmis;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setFacilityID(String val) {
    this.facilityID = val;
  }

  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    searchB.setMaximumSize(new Dimension(102, 35));
    searchB.setMinimumSize(new Dimension(102, 35));
    searchB.setPreferredSize(new Dimension(102, 35));
    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    cancelB.setMaximumSize(new Dimension(102, 35));
    cancelB.setMinimumSize(new Dimension(102, 35));
    cancelB.setPreferredSize(new Dimension(102, 35));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    okB.setMaximumSize(new Dimension(80, 35));
    okB.setMinimumSize(new Dimension(80, 35));
    okB.setPreferredSize(new Dimension(80, 35));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    okB.setEnabled(false);

    this.setResizable(false);
    this.getContentPane().setSize(520,400);
    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(420);
    xYLayout1.setWidth(520);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    topP.setLayout(xYLayout2);
    textP.setLayout(gridBagLayout1);
    jPanel1.setLayout(borderLayout1);
    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    searchB.setText("Search");
    searchB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        searchB_actionPerformed(e);
      }
    });
    jLabel1.setText("Search Text:");
    //jLabel2.setText("Catalog:");
    queryT.setMaximumSize(new Dimension(150, 21));
    queryT.setMinimumSize(new Dimension(150, 21));
    queryT.setPreferredSize(new Dimension(150, 21));
    queryT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        queryT_keyPressed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(bottomP, new XYConstraints(10, 366, 500, 45));
    bottomP.add(searchB, null);
    bottomP.add(okB, null);
    bottomP.add(cancelB, null);
    panel1.add(topP, new XYConstraints(10, 10, 500, 345));
    topP.add(textP, new XYConstraints(0, 0, 500, 80));
    /*
    textP.add(jLabel2, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 0), 0, 0));
    textP.add(catalogC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));*/
    textP.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 0), 0, 0));
    textP.add(queryT, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jPanel1, new XYConstraints(10, 80, 480, 265));
    jPanel1.add(jsp, BorderLayout.CENTER);
  }
    /*
  void loadItAction(){
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialMatrix",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_CATALOG");
      //query.put("QUERY_STRING",queryT.getText()); //String, String
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Hashtable h = (Hashtable)result.get("LIST_CATALOGS");
      vID = (Vector)h.get("CATALOG_ID");
      Vector vDesc = (Vector)h.get("CATALOG_DESC");
      if (vDesc.size() < 1) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"No Record",true);
        gd.setMsg("No record found.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
      }else {
        catalogC = ClientHelpObjs.loadChoiceFromVector(catalogC,vDesc);
      }
      this.repaint();
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    }catch(Exception e){e.printStackTrace();}
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),true);
    okB.setEnabled(false);
    Color c = new Color(102,102,153);
    catalogC.setForeground(c);
  }   */


  void loadData(){
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialMatrix",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","SEARCH_PART_NO");
      query.put("SEARCH_TEXT",queryT.getText()); //String, String
      query.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(this.facilityID));
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      Boolean b = (Boolean)result.get("SUCCEED");
      if (!b.booleanValue()) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"No Record",true);
        gd.setMsg("No record found.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        okB.setEnabled(false);
      }else {
        buildTableModel((Hashtable) result.get("SEARCH_DATA"));
        //model = (DbTableModel)result.get("DATA_MODEL");
        okB.setEnabled(true);
        buildTable((Hashtable)result.get("TABLE_STYLE"));
      }
      this.repaint();
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    }catch(Exception e){e.printStackTrace();}
  }

  void buildTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    model = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      model.addRow((Object[]) data.elementAt(i));
    }
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
  }  //end of method

  void buildTable(Hashtable Result1){
    jPanel1.removeAll();
    jsp = new JScrollPane();
    model.setEditableFlag(0);
    TableSorter sorter = new TableSorter(model);
    myTable = new CmisTable(sorter);
    sorter.addMouseListenerToHeaderInTable(myTable);

    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    //Hashtable chargeTableStyle = (Hashtable)Result1.get("TABLE_STYLE");
    Color color = (Color)Result1.get("COLOR");
    Integer t = (Integer)Result1.get("INSET_TOP");
    Integer l = (Integer)Result1.get("INSET_LEFT");
    Integer b = (Integer)Result1.get("INSET_BOTTOM");
    Integer r = (Integer)Result1.get("INSET_RIGHT");
    Integer a = (Integer)Result1.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = myTable.getColumnModel();
    for (int i = 0; i < myTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)Result1.get("FONT_NAME");
    Integer fontStyle = (Integer)Result1.get("FONT_STYLE");
    Integer fontSize = (Integer)Result1.get("FONT_SIZE");
    myTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)Result1.get("FOREGROUND"),(Color)Result1.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = myTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }


    sorter.setColTypeFlag(model.getColumnTypesString());
    for(int i=0;i<myTable.getColumnCount();i++){
      if (model.getColumnWidth(i)==0) {
        myTable.getColumn(myTable.getColumnName(i)).setWidth(model.getColumnWidth(i));
        myTable.getColumn(myTable.getColumnName(i)).setMaxWidth(model.getColumnWidth(i));
        myTable.getColumn(myTable.getColumnName(i)).setMinWidth(model.getColumnWidth(i));
      }
      myTable.getColumn(myTable.getColumnName(i)).setPreferredWidth(model.getColumnWidth(i));
    }
    myTable.getTableHeader().setResizingAllowed(true);
    myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    myTable.getTableHeader().setReorderingAllowed(true);
    jsp.getViewport().setView(myTable);
    myTable.setVisible(true);
    jsp.setVisible(true);
    myTable.validate();
    jsp.revalidate();
    jPanel1.add(jsp,BorderLayout.CENTER);
    jPanel1.revalidate();

    if(myTable.getRowCount() == 0){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"No Records Found",true);
      gd.setMsg("There were no Part No that matched the search criteria.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
    }
  }
  public boolean isCanceled(){
    return canceled;
  }
  public String getCasNumber(){
    return partNo;
  }
  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(myTable.getSelectedRowCount()<1){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Select a part number",true);
      gd.setMsg("Please select a part number.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }
    partNo = myTable.getValueAt(myTable.getSelectedRow(),1).toString();
    this.setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    canceled = true;
    this.setVisible(false);
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    /*
    if (vID.size() > 1) {
      if (catalogC.getSelectedIndex() > 0) {
        loadData();
      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Select a catalog",true);
        gd.setMsg("Please select a catalog.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
      }
    }else if (vID.size() == 1) {
      loadData();
    }  */
    loadData();
  }

  void queryT_keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      loadData();
    }
    /*
    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      if (vID.size() > 1) {
        if (catalogC.getSelectedIndex() > 0) {
          loadData();
        }else {
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Select a catalog",true);
          gd.setMsg("Please select a catalog.");
          CenterComponent.centerCompOnScreen(gd);
          gd.setVisible(true);
        }
      }else if (vID.size() == 1) {
        loadData();
      }
    }*/
  }
}

/*
void buildTable(){
    jPanel1.removeAll();
    //jsp.removeAll();
    jsp = new JScrollPane();
    Object[][] oa = new Object[v.size()][2];
    for(int i=0;i<v.size();i++){
      Hashtable h = (Hashtable)v.elementAt(i);
      oa[i][0] = h.get("MANUFACTURER").toString();
      oa[i][1] = h.get("NAME").toString();
    }
    CmisTableModel dtm = new CmisTableModel(colHead,oa);
    dtm.setEditableFlag(0);
    TableSorter sorter = new TableSorter(dtm);
    sorter.setColTypeFlag("11");
    myTable = new JTable(sorter);
    sorter.addMouseListenerToHeaderInTable(myTable);
    myTable.setCellSelectionEnabled(false);
    myTable.getColumn(colHead[0]).setPreferredWidth(136);
    myTable.getColumn(colHead[1]).setPreferredWidth(324);
    myTable.getTableHeader().setResizingAllowed(true);
    //myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    myTable.getTableHeader().setReorderingAllowed(false);
    jsp.getViewport().setView(myTable);
    myTable.setVisible(true);
    jsp.setVisible(true);
    myTable.validate();
    jsp.revalidate();
    jPanel1.add(jsp,BorderLayout.CENTER);
    jPanel1.revalidate();

    if(v.size()==0){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"No Records Found",true);
      gd.setMsg("There were no Part No that matched the search criteria.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
    }
  }
*/
