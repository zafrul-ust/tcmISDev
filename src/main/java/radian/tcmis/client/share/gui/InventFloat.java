
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
import java.text.*;
import java.net.*;
//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;

//import radian.tcmis.client.share.reports.*;
import radian.tcmis.client.share.httpCgi.*;
import javax.swing.*;
import javax.swing.table.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import com.borland.jbcl.layout.*;
import com.borland.jbcl.control.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class InventFloat extends JDialog {
  XYLayout xYLayout1 = new XYLayout();

  //load
  int metaItem=0;

  JPanel mainPanel = new JPanel();
  JPanel holder1 = new JPanel();
  BorderLayout lay = new BorderLayout();
  JPanel holder2 = new JPanel();
  //Table 1
  final int invEditableFlag1 = 0;
  final Long colTypeFlag1 = new Long("1213");
  JScrollPane JSP1 = new JScrollPane();
  CmisTable invTable1 = new CmisTable();
  TableOrganizer tOrg1 = new TableOrganizer();
  TableSorter  sorterInv1 = new TableSorter();
  String[] colHeads1 ;
  int[] colWidths1 ;
  String colTypes1 ;
  DbTableModel ctm1 ;

  //Table2
  final int invEditableFlag2 = 0;
  final Long colTypeFlag2 = new Long("1223");
  JScrollPane JSP2 = new JScrollPane();
  CmisTable invTable2 = new CmisTable();
  TableOrganizer tOrg2 = new TableOrganizer();
  TableSorter  sorterInv2 = new TableSorter();
  String[] colHeads2 ;
  int[] colWidths2 ;
  String colTypes2 ;
  DbTableModel ctm2 ;


  StaticJLabel itemLabel = new StaticJLabel();
  StaticJLabel partLabel = new StaticJLabel();
  StaticJLabel pkgLabel = new StaticJLabel();
  StaticJLabel mfgLabel = new StaticJLabel();
  StaticJLabel nameLabel = new StaticJLabel();
  DataJLabel itemVal = new DataJLabel();

  DataJLabel pkgVal= new DataJLabel();
  DataJLabel mfgVal= new DataJLabel();
  DataJLabel nameVal= new DataJLabel();

  Integer item_id = new Integer(0);
  String facility_id = new String("");
  String pkg_style = new String("");
  String partnum = new String("");
  String hub = new String("");
  JButton ok = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();

  //*********************************
  CmisApp parent = null;
  //*********************************

  Frame fr = null;
  JButton reportB = new JButton();
  JList partVal = new JList();
  JScrollPane partJSP = new JScrollPane();

  static int NOTES2 =0;
  static int REAL_NOTES2 =0;

  String packaging = "";
  String tradeName = "";
  String mfg = "";

  public InventFloat(){
     this(new Frame(),null);
  }


  public InventFloat(Frame passed,CmisApp c) {
    super(passed);
    fr = passed;
    try {
      jbInit();

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    parent = c;
  }

  public void setPackaging(String s) {
    this.packaging = s;
  }
  public void setTradeName(String s) {
    this.tradeName = s;
  }
  public void setMfg(String s) {
    this.mfg = s;
  }

  void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
*/
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    //this.setBackground(SystemColor.control);
    //this.setLayout(xYLayout1);
    this.setTitle("Inventory for ");
    mainPanel.setLayout(xYLayout1);
    //setIconImage((Image)getToolkit().getImage(new URL(ClientConstants.IMAGES_DIR+"main.gif")));
    itemLabel.setText("             Item:");
    partLabel.setText("Part #:");
    pkgLabel.setText("Packaging:");
    mfgLabel.setText("Manufacturer:");
    nameLabel.setText("Trade Name:");
    ok.setText("Ok");
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif","Search");
    ok.setIcon(ss);
    ok.addKeyListener(new InventFloat_ok_keyAdapter(this));
    ok.addActionListener(new InventFloat_ok_actionAdapter(this));
    mfgVal.setText("-");
    nameVal.setText("-");
    xYLayout1.setHeight(352);
    xYLayout1.setWidth(708);
    // InventFloat cannot be modal or the report
    // module will not work until the float window
    // is closed
    //this.setModal(true);
    this.setModal(false);

    //table1
    /*
    cmisTModelInv1 = new CmisTableModel(invCols1);
    sorterInv1 = new TableSorter(cmisTModelInv1);
    invTable1 = new CmisTable (sorterInv1);
    sorterInv1.addMouseListenerToHeaderInTable(invTable1);
    tOrg1 = new TableOrganizer(invTable1);
    invTable1 = (CmisTable)tOrg1.getJTable();
    invTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    JSP1 = new JScrollPane(invTable1);
    //table2
    cmisTModelInv2 = new CmisTableModel(invCols2);
    sorterInv2 = new TableSorter(cmisTModelInv2);
    invTable2 = new CmisTable (sorterInv2);
    sorterInv2.addMouseListenerToHeaderInTable(invTable2);
    tOrg2 = new TableOrganizer(invTable2);
    invTable2 = (CmisTable)tOrg2.getJTable();
    invTable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    JSP2 = new JScrollPane(invTable2);
    */
    reportB.setText("View Report");
    reportB.addActionListener(new InventFloat_reportB_actionAdapter(this));

    holder1.setLayout(lay);
    holder2.setLayout(borderLayout1);
    invTable2.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        invTable2_mouseClicked(e);
      }
    });
    invTable1.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        invTable1_mouseClicked(e);
      }
    });
    mainPanel.add(holder1, new XYConstraints(6, 100, 348, 169));
    holder1.add(JSP1, BorderLayout.CENTER);
    mainPanel.add(holder2, new XYConstraints(354, 100, 348, 169));
    holder2.add(JSP2, BorderLayout.CENTER);
    mainPanel.add(pkgLabel, new XYConstraints(95, 32, -1, -1));
    mainPanel.add(itemVal, new XYConstraints(182, 11, 164, -1));
    mainPanel.add(mfgVal, new XYConstraints(182, 74, 433, -1));
    mainPanel.add(pkgVal, new XYConstraints(182, 31, 180, -1));
    mainPanel.add(nameLabel, new XYConstraints(85, 53, -1, -1));
    mainPanel.add(nameVal, new XYConstraints(182, 51, 249, -1));
    mainPanel.add(mfgLabel, new XYConstraints(82, 74, -1, -1));
    mainPanel.add(itemLabel, new XYConstraints(86, 11, -1, -1));
    mainPanel.add(partLabel, new XYConstraints(378, 19, -1, -1));
    mainPanel.add(partJSP, new XYConstraints(433, 15, 180, 51));
    //10-25-01 doesn't need it anymore mainPanel.add(reportB, new XYConstraints(360, 306, 109, 28));
    mainPanel.add(ok, new XYConstraints(319, 306, 80, 28));

    holder1.setVisible(true);
    holder2.setVisible(true);

    this.getContentPane().add(mainPanel);


    ok.requestFocus();


    pack();

    CenterComponent.centerCompOnScreen(this);
    //Colors
    // labels go Black
    ClientHelpObjs.makeDefaultColors(this);



  }

  public void setItem (int item){
    metaItem = item;
  }

  public void setPartNum(String p){
    this.partnum = new String(p);
  }
  public void setHub(String p){
    this.hub = new String(p);
  }

  public void loadIt(){
    //InventFloat_LoadThread iT = new InventFloat_LoadThread(this);
    //iT.start();
    fillTableAction(metaItem,hub,facility_id);
    this.setVisible(true);
  }

  public void fillTableAction(int id,String hub, String fac) {
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("InventoryObj",parent);
      Hashtable data = new Hashtable();
      data.put("ITEM_ID",new Integer(id));
      data.put("HUB",BothHelpObjs.makeBlankFromNull(hub));
      data.put("FAC",BothHelpObjs.makeBlankFromNull(fac));

      Hashtable query = new Hashtable();
      query.put("ACTION","GET_FLOAT_DATA"); //String, String
      query.put("DATA",data);
      Hashtable result = cgi.getResultHash(query);
      ClientHelpObjs.setEnabledContainer(this,true);

      if (result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      Hashtable searchData = (Hashtable) result.get("SEARCH_DATA");

      this.setTitle("Inventory for " + tradeName);
      Vector parts = (Vector) searchData.get("PART_NUM");
      partVal.setListData(parts);
      partJSP.setViewportView(partVal);

      nameVal.setText(tradeName);
      mfgVal.setText(mfg);
      pkgVal.setText(packaging);
      itemVal.setText(String.valueOf(id));

      //left table
      colHeads1 = (String[]) result.get("COL_HEADS1");
      colWidths1 =(int[]) result.get("COL_WIDTHS1");
      colTypes1 = new String((String) result.get("COL_TYPES1"));
      buildLeftTableModel(searchData,colHeads1);
      //ctm1 = (DbTableModel) result.get("DATA_MODEL1");
      sorterInv1 = new TableSorter(ctm1);
      invTable1 = new CmisTable(sorterInv1);
      setColTypes1();
      setColWidths1();
      sorterInv1.addMouseListenerToHeaderInTable(invTable1);
      setTable1();
      AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
      Hashtable chargeTableStyle = (Hashtable)result.get("TABLE_STYLE");
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
      invTable1.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = invTable1.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }

      //right Table
      Hashtable keyCols2 = (Hashtable) result.get("KEY_COLS2");
      NOTES2 = Integer.parseInt((String) keyCols2.get("NOTES2"));
      REAL_NOTES2 = Integer.parseInt((String) keyCols2.get("REAL_NOTES2"));
      colHeads2 = (String[]) result.get("COL_HEADS2");
      colWidths2 =(int[]) result.get("COL_WIDTHS2");
      colTypes2 = new String((String) result.get("COL_TYPES2"));
      buildRightTableModel(searchData,colHeads2);
      //ctm2 = (DbTableModel) result.get("DATA_MODEL2");
      sorterInv2 = new TableSorter(ctm2);
      invTable2 = new CmisTable(sorterInv2);
      setColTypes2();
      setColWidths2();
      sorterInv2.addMouseListenerToHeaderInTable(invTable2);
      setTable2();
      //font and foreground and background color for columns heading
      invTable2.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      enum1 = invTable2.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }

      holder1.removeAll();
      holder2.removeAll();
      JSP1 = new JScrollPane(invTable1);
      JSP2 = new JScrollPane(invTable2);
      holder1.add(JSP1, BorderLayout.CENTER);
      holder2.add(JSP2, BorderLayout.CENTER);
      holder1.validate();
      holder1.setVisible(true);
      holder2.validate();
      holder2.setVisible(true);

      this.validate();
      this.repaint();
    } catch (Exception e){
      GenericDlg.showAccessDenied(this.parent.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      e.printStackTrace();
      System.out.println("InvWin:fillTable->Got error");
    }
  } //end of method

  void buildLeftTableModel(Hashtable searchData, String[] colHeads) {
    Vector data = (Vector) searchData.get("TABLE_DATA1");
    Vector headV = new Vector(colHeads.length);
    for(int i=0;i<colHeads.length;i++) {
      headV.addElement(colHeads[i]);
    }
    ctm1 = new DbTableModel();
    ctm1.setDataVector((data==null?new Vector():data),headV);
  }  //end of method

  void buildRightTableModel(Hashtable searchData, String[] colHeads) {
    Vector data = (Vector) searchData.get("TABLE_DATA2");
    Vector headV = new Vector(colHeads.length);
    for(int i=0;i<colHeads.length;i++) {
      headV.addElement(colHeads[i]);
    }
    ctm2 = new DbTableModel();
    ctm2.setDataVector((data==null?new Vector():data),headV);
  }  //end of method


  void setColTypes1(){
    sorterInv1.setColTypeFlag(colTypes1);
  }

  void setColWidths1(){
    for(int i=0;i<colHeads1.length;i++){
      invTable1.getColumn(colHeads1[i]).setPreferredWidth(colWidths1[i]);
      invTable1.getColumn(colHeads1[i]).setWidth(colWidths1[i]);
      if (colWidths1[i]==0){
         invTable1.getColumn(colHeads1[i]).setMinWidth(colWidths1[i]);
         invTable1.getColumn(colHeads1[i]).setMaxWidth(colWidths1[i]);
      }
    }
  }
  protected void setTable1(){
    invTable1.addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
        invTable1_mouseClicked(e);
      }
    });
    invTable1.setToolTipText(null);
    invTable1.getTableHeader().setReorderingAllowed(true);
    invTable1.getTableHeader().setResizingAllowed(true);
    invTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    invTable1.setCellSelectionEnabled(false);
    invTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  void setColTypes2(){
    sorterInv2.setColTypeFlag(colTypes2);
  }

  void setColWidths2(){
    for(int i=0;i<colHeads2.length;i++){
      invTable2.getColumn(colHeads2[i]).setPreferredWidth(colWidths2[i]);
      invTable2.getColumn(colHeads2[i]).setWidth(colWidths2[i]);
      if (colWidths2[i]==0){
         invTable2.getColumn(colHeads2[i]).setMinWidth(colWidths2[i]);
         invTable2.getColumn(colHeads2[i]).setMaxWidth(colWidths2[i]);
      }
    }
  }

  protected void setTable2(){
    invTable2.addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
        invTable2_mouseClicked(e);
      }
    });
    invTable2.setToolTipText(null);
    invTable2.getTableHeader().setReorderingAllowed(true);
    invTable2.getTableHeader().setResizingAllowed(true);
    invTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    invTable2.setCellSelectionEnabled(false);
    invTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  void goViewAsReport() {
    Integer ite = new Integer(this.metaItem);
    Hashtable query = new Hashtable();
    query.put("CURRENT_SCREEN","IF");
    query.put("FACILITY_ID",this.facility_id);
    query.put("HUB",this.hub);
    query.put("ITEM_ID",ite.toString());
    query.put("FILE_TYPE","PDF");
    query.put("METHOD","ACTIVE");
    query.put("USER_ID",parent.getMain().getUserId().toString());
    ClientHelpObjs.goReportURL(parent,this.parent.getResourceBundle().getString("APP_NAME"),query,URLGrab.PRINTSCREEN);
  }
  /*
  void goViewAsReport() {
    InventFloatFrame iff = new InventFloatFrame(parent,"Inventory Detail",
                                               ClientHelpObjs.getArrayFromTable(invTable1),
                                               ClientHelpObjs.getArrayFromTable(invTable2));
    System.out.println("------------------- itemVal: " + itemVal.getText());
    System.out.println("------------------- partVal: " + partVal.getSelectedValue().toString());
    System.out.println("------------------- mfgVal: " + mfgVal.getText());
    System.out.println("------------------- nameVal: " + nameVal.getText());
    System.out.println("------------------- pkgVal: " + pkgVal.getText());

    iff.setStaticInfo(itemVal.getText(),partVal.getSelectedValue().toString(),mfgVal.getText(),nameVal.getText(),pkgVal.getText());
    iff.setVisible(true);
  } */

  public void setFacilityId(String id) {
    facility_id = id;
  }

  public void setPkgStyle(String id) {
    pkg_style = id;
    pkgVal.setText(id);
  }



  void ok_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    this.setVisible(false);
  }

  void ok_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        this.setVisible(false);
     }
  }

  void reportB_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     goViewAsReport();
  }

  void invTable2_mouseClicked(MouseEvent e) {
     int c = invTable2.getSelectedColumn();
     int r = invTable2.getSelectedRow();
     if (c==NOTES2){
        if (invTable2.getModel().getValueAt(r,c).toString().equals("+")){
          GenericDlg d = new GenericDlg(parent.getMain(),"Notes",true);
          d.setMsg(invTable2.getModel().getValueAt(r,REAL_NOTES2).toString());
          d.setVisible(true);
        }
     }
  }

  void invTable1_mouseClicked(MouseEvent e) {

  }

}

class InventFloat_LoadThread extends Thread {
  InventFloat parent;

  public InventFloat_LoadThread(InventFloat parent){
     super("InventFloat_LoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.fillTableAction(parent.metaItem,parent.hub,"");
  }
}


class InventFloat_ok_actionAdapter implements java.awt.event.ActionListener{
  InventFloat adaptee;

  InventFloat_ok_actionAdapter(InventFloat adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.ok_actionPerformed(e);
  }
}

class InventFloat_ok_keyAdapter extends java.awt.event.KeyAdapter {
  InventFloat adaptee;

  InventFloat_ok_keyAdapter(InventFloat adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.ok_keyPressed(e);
  }
}

class InventFloat_reportB_actionAdapter implements java.awt.event.ActionListener {
  InventFloat adaptee;


  InventFloat_reportB_actionAdapter(InventFloat adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.reportB_actionPerformed(e);
  }
}




























