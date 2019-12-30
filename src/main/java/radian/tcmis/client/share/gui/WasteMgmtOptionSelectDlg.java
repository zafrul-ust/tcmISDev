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
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class WasteMgmtOptionSelectDlg extends JDialog {
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
  JTable myTable;
  JButton searchB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  JTextField queryT = new JTextField();
  boolean canceled = false;
  String mgmtOptDesc = "";
  String mgmtOptID = "";
  String[] colHead = new String[]{"Management Option","Description"};
  StaticJLabel jLabel2 = new StaticJLabel();
  JComboBox vendorC = new JComboBox();
  Vector vID = null;
  String vendorID = null;

  public WasteMgmtOptionSelectDlg(CmisApp cmis) {
    super(cmis.getMain(), "Select a management option", true);
    this.cmis = cmis;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
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
    queryT.setMaximumSize(new Dimension(150, 21));
    queryT.setMinimumSize(new Dimension(150, 21));
    queryT.setPreferredSize(new Dimension(150, 21));
    queryT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        queryT_keyPressed(e);
      }
    });

    jLabel2.setText("Vendor:");
    vendorC.setMaximumSize(new Dimension(150, 21));
    vendorC.setMinimumSize(new Dimension(150, 21));
    vendorC.setPreferredSize(new Dimension(150, 21));

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
    textP.add(vendorC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));   */
    textP.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 0), 0, 0));
    textP.add(queryT, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jPanel1, new XYConstraints(10, 80, 480, 265));
    jPanel1.add(jsp, BorderLayout.CENTER);
    okB.setEnabled(false);
  }


  public String getVendorId() {
    return (this.vendorID);
  }
  public void setVendorId(String val) {
    this.vendorID = val;
  }

  void loadData(){
    Vector v = null;
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteAhocReport",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","SEARCH_WASTE_MGMT_OPTION");
      query.put("SEARCH_TEXT",queryT.getText()); //String, String
      //query.put("VENDOR_ID",getVendorId());    //String, String
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      v = (Vector)result.get("WASTE_MGMT_OPTION_INFO");
      if (v.size() < 1) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"No Record",true);
        gd.setMsg("No record found.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        //okB.setVisible(false);
      }else {
        //okB.setVisible(true);
        buildTable(v,(Hashtable)result.get("TABLE_STYLE"));
      }
      this.repaint();
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    }catch(Exception e){e.printStackTrace();}
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),true);
    if (v.size() < 1) {
      okB.setEnabled(false);
    }else {
      okB.setEnabled(true);
    }
  }

  void buildTable(Vector v,Hashtable Result1){
    jPanel1.removeAll();
    //jsp.removeAll();
    jsp = new JScrollPane();
    Object[][] oa = new Object[v.size()][2];
    for(int i=0;i<v.size();i++){
      Hashtable h = (Hashtable)v.elementAt(i);
      oa[i][0] = h.get("MGMT_OPTION_ID").toString();
      oa[i][1] = h.get("DESCRIPTION").toString();
    }
    CmisTableModel dtm = new CmisTableModel(colHead,oa);
    dtm.setEditableFlag(0);
    TableSorter sorter = new TableSorter(dtm);
    sorter.setColTypeFlag("11");
    myTable = new JTable(sorter);
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
      gd.setMsg("There were no management option that matched the search criteria.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
    }
  }
  public boolean isCanceled(){
    return canceled;
  }
  public String getMgmtOptDesc(){
    return mgmtOptDesc;
  }
  public String getMgmtID(){
    return mgmtOptID;
  }
  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(myTable.getSelectedRowCount()<1){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Select a management option",true);
      gd.setMsg("Please select a management option.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }
    mgmtOptID = myTable.getValueAt(myTable.getSelectedRow(),0).toString();
    mgmtOptDesc = myTable.getValueAt(myTable.getSelectedRow(),1).toString();
    //vendorID = getVendorId();
    this.setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    canceled = true;
    this.setVisible(false);
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    loadData();
  }


  void queryT_keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      loadData();
    }
  }
}
