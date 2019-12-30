//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.httpCgi.*;
import java.beans.*;
import java.util.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;
import java.awt.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class ManageWaste extends JPanel {
  CmisApp cmis;
  int rNum = 0;

  CmisTable wasteTable;
  CmisTable cartTable;
  TableSorter sorter = new TableSorter();


  public static final String[] colHeads = {"Rad Profile","Order","Facility","Work Area","Vend Profile","Vendor","Description","Waste Mgmt Opt",
                                           "Mgmt Opt Location","Packaging","Waste Category","Waste Type","LPP","Pickup Date"};
  public static final int[] colW = {60,65,50,60,80,120,130,100,100,75,100,75,50,75};
  String [] cartCols = new String[]{"Work Area","Vend Profile","Description","Qty","Packaging",
                                    "LPP","Profile Id","Waste Request Id","Line Item"};
  int[] colWidths = {0,90,300,50,75,0,0,0,0};
  final int CART_WORK_AREA = 0;
  final int CART_VENDOR_PROFILE_ID = 1;
  final int CART_PROFILE_DESC = 2;
  final int CART_QTY = 3;
  final int CART_PACKAGING = 4;
  final int CART_LPP = 5;
  final int CART_PROFILE_ID = 6;
  final int CART_WASTE_REQUEST_ID = 7;
  final int CART_LINE_ITEM = 8;

  //the maximum number of row selected at the same time
  final int MAXSELECTEDROW = 100;

  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel topP = new JPanel();
  JPanel bottomP = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel leftP = new JPanel();
  JPanel rightP = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  StaticJLabel requesterL = new StaticJLabel();
  DataJLabel requesterT = new DataJLabel();
  JButton searchB = new JButton();
  FacilityCombo facC = new FacilityCombo();
  WorkAreaCombo waC = facC.getWorkAreaCombo();
  JScrollPane jsp = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JScrollPane cartJsp = new JScrollPane();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton addB = new JButton();
  JButton removeB = new JButton();
  JButton createB = new JButton();
  StaticJLabel vendorL = new StaticJLabel();
  JComboBox vendor = new JComboBox();

  // columns
  int WASTE_ID_COL;
  int PROFILE_ID_COL;
  int VENDOR_PROFILE_ID_COL;
  int VENDOR_COL;
  int DESC_COL;
  int LPP_COL;
  int WORK_AREA_COL;
  int WASTE_REQUEST_ID_COL;
  int LINE_ITEM_COL;
  int PACKAGING_COL;
  int ORDER_NUMBER_COL;

  public ManageWaste() {
    try{
      jbInit();
    }catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setCmis(CmisApp cmis){
    this.cmis = cmis;
    facC.setCmisApp(cmis);
    facC.setUseAllFacilities(true);
    facC.setUseAllWorkAreas(true);
    //facC.setForceSelectWorkArea(true);
    //facC.setLoadType(FacilityCombo.PREF_FACS);
    facC.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equalsIgnoreCase("loaded")) {
          dataLoaded();
        }
      }
    });
    facC.loadFacilityData();
    waC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(waC.isLoading())return;
        waC_actionPerformed(e);
      }
    });
  }

  public void printScreenData(){
  }

  private void dataLoaded(){
    TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","GETUSERNAME"); //String, String
    query.put("USER_ID",cmis.getUserId());
    Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
    }
    requesterT.setText((String)result.get("USER_NAME").toString());
    requesterT.setEnabled(true);
    //facC.setSelectedFacId(cmis.getPrefFac());
    facC.setEnabled(true);
    waC.setEnabled(true);
    enableButtons();
  }

  private void enableButtons(){
  //  searchB.setEnabled(!BothHelpObjs.isBlankString(facC.getSelectedWorkAreaID()));
    addB.setEnabled(wasteTable.getSelectedRowCount()>0 && !BothHelpObjs.isBlankString(vendor.getSelectedItem().toString()));
    removeB.setEnabled(cartTable.getSelectedRowCount()>0);
    createB.setEnabled(cartTable.getRowCount()>0);
    // facC.setEnabled(cartTable.getRowCount()<1);
    vendor.setEnabled(cartTable.getRowCount()<1);
  }

  public void setCartTableWidth(){
    cartTable.getColumn("Work Area").setWidth(0);
    cartTable.getColumn("Work Area").setMinWidth(0);
    cartTable.getColumn("Work Area").setMaxWidth(0);
    cartTable.getColumn("Work Area").setPreferredWidth(0);
    cartTable.getColumn("LPP").setWidth(0);
    cartTable.getColumn("LPP").setMinWidth(0);
    cartTable.getColumn("LPP").setMaxWidth(0);
    cartTable.getColumn("LPP").setPreferredWidth(0);
    cartTable.getColumn("Profile Id").setWidth(0);
    cartTable.getColumn("Profile Id").setMinWidth(0);
    cartTable.getColumn("Profile Id").setMaxWidth(0);
    cartTable.getColumn("Profile Id").setPreferredWidth(0);
    cartTable.getColumn("Waste Request Id").setWidth(0);
    cartTable.getColumn("Waste Request Id").setMinWidth(0);
    cartTable.getColumn("Waste Request Id").setMaxWidth(0);
    cartTable.getColumn("Waste Request Id").setPreferredWidth(0);
    cartTable.getColumn("Line Item").setWidth(0);
    cartTable.getColumn("Line Item").setMinWidth(0);
    cartTable.getColumn("Line Item").setMaxWidth(0);
    cartTable.getColumn("Line Item").setPreferredWidth(0);
    cartTable.getColumn("Qty").setWidth(40);
    cartTable.getColumn("Qty").setMinWidth(40);
    cartTable.getColumn("Qty").setMaxWidth(40);
    cartTable.getColumn("Qty").setPreferredWidth(40);
    cartTable.getColumn("Vend Profile").setWidth(80);
    cartTable.getColumn("Vend Profile").setMinWidth(80);
    cartTable.getColumn("Vend Profile").setMaxWidth(80);
    cartTable.getColumn("Vend Profile").setPreferredWidth(80);
    cartTable.getColumn("Packaging").setWidth(80);
    cartTable.getColumn("Packaging").setMinWidth(80);
    cartTable.getColumn("Packaging").setMaxWidth(80);
    cartTable.getColumn("Packaging").setPreferredWidth(80);
   /*
    //setting column widths
    for(int i=0;i<cartTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        cartTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        cartTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          cartTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          cartTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
    } */
  }

  private void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    this.setLayout(gridBagLayout1);
    topP.setMaximumSize(new Dimension(32767, 170));
    topP.setMinimumSize(new Dimension(10, 170));
    topP.setPreferredSize(new Dimension(450, 170));
    topP.setLayout(gridBagLayout2);
    bottomP.setLayout(borderLayout1);
    leftP.setLayout(gridBagLayout3);
    vendorL.setHorizontalAlignment(SwingConstants.RIGHT);
    vendorL.setText("Vendor:");
    requesterL.setHorizontalAlignment(SwingConstants.RIGHT);
    requesterL.setText("Requester:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Facility:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Work Area:");
    searchB.setText("Search");
    searchB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchB_actionPerformed(e);
      }
    });
    rightP.setLayout(borderLayout2);
    jPanel1.setLayout(flowLayout1);
    addB.setText("Add");
    addB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });
    removeB.setText("Remove");
    removeB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        removeB_actionPerformed(e);
      }
    });
    createB.setText("Generate Shipment Order");
    createB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createB_actionPerformed(e);
      }
    });
    addB.setEnabled(false);
    removeB.setEnabled(false);
    createB.setEnabled(false);
    requesterT.setEnabled(false);

    vendor.setMaximumSize(new Dimension(180, 24));
    vendor.setMinimumSize(new Dimension(180, 24));
    vendor.setPreferredSize(new Dimension(180, 24));
    vendor.addItem(new String("CLEAN HARBORS"));
    vendor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        vendor_actionPerformed(e);
      }
    });

    requesterT.setMaximumSize(new Dimension(180, 24));
    requesterT.setMinimumSize(new Dimension(180, 24));
    requesterT.setPreferredSize(new Dimension(180, 24));
    facC.setEnabled(false);
    facC.setMaximumSize(new Dimension(180, 24));
    facC.setMinimumSize(new Dimension(180, 24));
    facC.setPreferredSize(new Dimension(180, 24));
    //facC.setSelectedFacId("");
    waC.setEnabled(false);
    waC.setMaximumSize(new Dimension(180, 24));
    waC.setMinimumSize(new Dimension(180, 24));
    waC.setPreferredSize(new Dimension(180, 24));

    DbTableModel ta = new DbTableModel(colHeads);
    ta.setColumnPrefWidth(colW);
    wasteTable = new CmisTable(ta);
    jsp.getViewport().setView(wasteTable);
    wasteTable.addMouseListener(new ManageWaste_wasteTable_mouseAdapter(this));
    wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    wasteTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    DbTableModel ctm = new DbTableModel(cartCols);
   // ctm.setEditableFlag(4);
   // ctm.setColumnPrefWidth(colWidths);
   // sorter = new TableSorter(ctm);
    cartTable = new CmisTable(ctm);
   // sorter.setColTypeFlag(ctm.getColumnTypesString());
   // sorter.addMouseListenerToHeaderInTable(cartTable);
    cartTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);// .AUTO_RESIZE_OFF);
    cartTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    cartTable.sizeColumnsToFit(true);
    cartTable.getTableHeader().setReorderingAllowed(true);
    setCartTableWidth();
    cartJsp.getViewport().setView(cartTable);
    cartTable.addMouseListener(new ManageWaste_cartTable_mouseAdapter(this));

    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif","Search"));
    addB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add"));
    removeB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif","Delete"));
    createB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Submit"));

    this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 32, 0), 0, 25));
    topP.add(rightP, new GridBagConstraints2(1, 0, 1, 1, 0.7, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));
    rightP.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(addB, null);
    jPanel1.add(removeB, null);
    jPanel1.add(createB, null);
    rightP.add(cartJsp, BorderLayout.CENTER);
    topP.add(leftP, new GridBagConstraints2(0, 0, 1, 3, 0.4, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 154));
    leftP.add(requesterL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(jLabel1, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(jLabel2, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(vendorL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(searchB, new GridBagConstraints2(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    leftP.add(requesterT, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(facC, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(waC, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(vendor, new GridBagConstraints2(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(bottomP, new GridBagConstraints2(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(7, 5, 5, 5), 0, 265));
    bottomP.add(jsp, BorderLayout.CENTER);
  }

  void vendor_actionPerformed(ActionEvent e) {
      System.out.println("*** in vendor combo box do something");
   //   cmis.getMain().goWasteOrder(0,1);
  }

  void doSearch(){
    long sTime = new java.util.Date().getTime();
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","SEARCH"); //String, String
      query.put("FAC_ID",facC.getSelectedFacId());
      query.put("WORK_AREA",waC.getSelectedWorkAreaID());
      query.put("VENDOR",vendor.getSelectedItem());
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      jsp.getViewport().remove(wasteTable);
      DbTableModel ctm = (DbTableModel) result.get("DATA_MODEL");
      sorter = new TableSorter(ctm);
      wasteTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(wasteTable);
      wasteTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      wasteTable.addMouseListener(new ManageWaste_wasteTable_mouseAdapter(this));

     // wasteTable.getTableHeader(true);
      wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      wasteTable.setCellSelectionEnabled(false);
      wasteTable.getTableHeader().setReorderingAllowed(true);

      // set column widths
      for(int i=0;i<wasteTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        wasteTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        wasteTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          wasteTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          wasteTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }
      jsp.getViewport().setView(wasteTable);
      if (wasteTable.getModel().getRowCount()<=0){
        GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
        g.setMsg("No Records found.");
        g.setVisible(true);
        // empty table
        // setting up the table
        //wasteTable = new CmisTable();
        //jsp.getViewport().setView(wasteTable);
      }
      // columns
      Hashtable h = (Hashtable)result.get("KEY_COLS");
      PROFILE_ID_COL = ((Integer)h.get("RADIAN_PROFILE_ID")).intValue();
      VENDOR_PROFILE_ID_COL = ((Integer)h.get("VENDOR_PROFILE_ID")).intValue();
      VENDOR_COL = ((Integer)h.get("VENDOR_ID")).intValue();
      //DESC = ((Integer)h.get("DESC")).intValue();
      LPP_COL = ((Integer)h.get("LPP")).intValue();
      WORK_AREA_COL = ((Integer)h.get("WORK_AREA")).intValue();
      WASTE_REQUEST_ID_COL = ((Integer)h.get("WASTE_REQUEST_ID")).intValue();
      LINE_ITEM_COL = ((Integer)h.get("LINE_ITEM")).intValue();
      DESC_COL = ((Integer)h.get("WASTE_PROFILE_DESC")).intValue();
      PACKAGING_COL = ((Integer)h.get("PACKAGING")).intValue();
      ORDER_NUMBER_COL = ((Integer)h.get("ORDER_NUMBER")).intValue();

      long eTime = new java.util.Date().getTime();
      long min = (eTime-sTime);
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(new Date(min));
      String tmsg = "Records Found: " + wasteTable.getRowCount() + "     Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
      cmis.getMain().countLabel.put(new Integer(Main.MANAGE_WASTE),tmsg);
      cmis.getMain().setStatusBarText((String)cmis.getMain().countLabel.get(new Integer(Main.MANAGE_WASTE)));


    }catch(Exception e){
      e.printStackTrace();
    }finally{
      ClientHelpObjs.setEnabledContainer(this,true);
      enableButtons();
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
      this.revalidate();
    }
  }

  protected void createShippmentRequest(){
    if(cartTable.getRowCount()<1)return;
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","CREATE_SHIPMENT_ID"); //String, String
      query.put("SHIPMENT_REQUEST",buildShipmentVector());
     // query.put("FAC_ID",facC.getSelectedFacId()); //String, String
      query.put("SHIPMENT_INFO",getShipmentInfo()); //String, String
      query.put("USER_ID",cmis.getUserId()); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      int wr = 0;
      if(result.containsKey("SHIPMENT_NUM")){
        wr = ((Integer)result.get("SHIPMENT_NUM")).intValue();
      }

      GenericDlg gd = new GenericDlg(cmis.getMain(),true);
      CenterComponent.centerCompOnScreen(gd);
      if(wr>0){
        gd.setTitle("Shipment Order Created");
        gd.setMsg("A new Shipment order was created.");
        gd.setVisible(true);
        removeAllFromCart();
        cmis.getMain().goWasteOrder(0,wr);
      }else{
        gd.setTitle("Error creating Waste Request");
        gd.setMsg("A Waste Pickup Request could not be created. Please check your data and try again.");
        gd.setVisible(true);
      }
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      ClientHelpObjs.setEnabledContainer(this,true);
      enableButtons();
      this.revalidate();
    }
  }
  private Vector buildShipmentVector(){
    Vector v = new Vector();
    for(int i=0;i<cartTable.getRowCount();i++){
      Hashtable h = new Hashtable();
      h.put("WASTE_REQUEST_ID",new Integer(cartTable.getModel().getValueAt(i,CART_WASTE_REQUEST_ID).toString()));
      h.put("LINE_ITEM",new Integer(cartTable.getModel().getValueAt(i,CART_LINE_ITEM).toString()));
      v.addElement(h);
    }
    return v;
  }

  private Hashtable getShipmentInfo(){
    Hashtable h = new Hashtable();
    h.put("FACILITY_ID",facC.getSelectedFacId());
    h.put("VENDOR_ID",vendor.getSelectedItem());
    return h;
  }

  private void doRightClick(JTable table){
  }

  private boolean canAddToCart(){
    if(cartTable.getRowCount() < 1 && wasteTable.getSelectedRowCount() > 0){
      return true;
    }
    if(wasteTable.getSelectedRowCount() < 1)return false;
    String wri = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),WASTE_REQUEST_ID_COL).toString();
    String li =  wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),LINE_ITEM_COL).toString();
   // String v = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),VENDOR).toString();
    for(int i=0;i<cartTable.getRowCount();i++){
      if(wri.equals(cartTable.getModel().getValueAt(i,CART_WASTE_REQUEST_ID).toString()) &&
         li.equals(cartTable.getModel().getValueAt(i,CART_LINE_ITEM).toString())){
        return false;
      }
    }
    return true;
  }
  private void addToCart(){
    // must be selected
    if(wasteTable.getSelectedRowCount()<1)return;

    // cannot already be in the cart
    if(!canAddToCart()){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Waste line item Already Selected.",true);
      gd.setMsg("You have already selected this waste line item.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }

    //only one vendor per shipment id.
    String v = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),VENDOR_COL).toString();
    if(!(v.equalsIgnoreCase(vendor.getSelectedItem().toString()))){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"In valid vendor.",true);
      gd.setMsg("You could only use on Vendor per shipment.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }




    //int row = wasteTable.getSelectedRow();
    int[] row = new int[MAXSELECTEDROW];
    row = wasteTable.getSelectedRows();
    Object[] oa = new Object[cartCols.length];

    for (int i = 0; i < row.length; i++){
     //waste already on ordered
      String num = wasteTable.getModel().getValueAt(row[i],ORDER_NUMBER_COL).toString();
      if (num.length() > 0){
        wasteTable.setRowSelectionInterval(row[i],row[i]);
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Waste already on order.",true);
        gd.setMsg("Double click on 'Waste Order' column to go to order.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }

     // if ((vendor.getSelectedItem().toString().equals(wasteTable.getModel().getValueAt(row[i],VENDOR_COL))) &&
       //   (waC.getSelectedWorkAreaID().toString().equals(wasteTable.getModel().getValueAt(row[i],WORK_AREA_COL)))) {
       if ((vendor.getSelectedItem().toString().equals(wasteTable.getModel().getValueAt(row[i],VENDOR_COL)))) {
        oa[CART_WORK_AREA] = wasteTable.getModel().getValueAt(row[i],WORK_AREA_COL);
        oa[CART_QTY] =  "1";
        oa[CART_PACKAGING] = wasteTable.getModel().getValueAt(row[i],PACKAGING_COL);
        oa[CART_PROFILE_DESC] = wasteTable.getModel().getValueAt(row[i],DESC_COL);
        oa[CART_LPP] = wasteTable.getModel().getValueAt(row[i],LPP_COL);
        oa[CART_VENDOR_PROFILE_ID] = wasteTable.getModel().getValueAt(row[i],VENDOR_PROFILE_ID_COL);
        oa[CART_PROFILE_ID] = wasteTable.getModel().getValueAt(row[i],PROFILE_ID_COL);
        oa[CART_WASTE_REQUEST_ID] = wasteTable.getModel().getValueAt(row[i],WASTE_REQUEST_ID_COL);
        oa[CART_LINE_ITEM] = wasteTable.getModel().getValueAt(row[i],LINE_ITEM_COL);;
        // TableSorter sorter = (TableSorter)cartTable.getModel();
        //DbTableModel model = (DbTableModel)sorter.getModel();
        DbTableModel model = (DbTableModel)cartTable.getModel();
        model.addRow(oa);
        enableButtons();
      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"ERROR.",true);
        gd.setMsg("The waste you selected is not in your work area.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }
    }
  }

  private void removeFromCart(){
    int[] r = cartTable.getSelectedRows();
    for(int i=r.length;i>0;i--){
      removeFromCart(i-1);
    }
    enableButtons();

  }
  private void removeFromCart(int i){
    DbTableModel model = (DbTableModel)cartTable.getModel();
    model.removeRow(i);
  }
  private void removeAllFromCart(){
    while(cartTable.getRowCount()>0){
      removeFromCart(0);
    }
    enableButtons();
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    ManageWasteLoadThread wplt = new ManageWasteLoadThread(this,"search");
    wplt.start();
  }

  void addB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    addToCart();
  }

  void removeB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    removeFromCart();
  }

  void createB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(cartTable.isEditing()){
      cartTable.getCellEditor().stopCellEditing();
    }
    ManageWasteLoadThread wplt = new ManageWasteLoadThread(this,"createWR");
    wplt.start();
  }
  void wasteTable_mouseClicked(MouseEvent e){
    if ((e.getClickCount() == 2) && (wasteTable.getSelectedColumn() == ORDER_NUMBER_COL)) {
      String num = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),ORDER_NUMBER_COL).toString();
      if (!BothHelpObjs.isBlankString(num)) {
        Integer orderNum = new Integer(num);
        cmis.getMain().goWasteOrder(0,orderNum.intValue());
      }
    } else {
      if(e.isMetaDown()){
        doRightClick(wasteTable);
      }else if(e.getClickCount() == 2){
        addToCart();
      }
    }
    enableButtons();
  }
  void cartTable_mouseClicked(MouseEvent e){
    if(e.isMetaDown()){
      doRightClick(cartTable);
    }
    enableButtons();
  }

  void waC_actionPerformed(ActionEvent e) {
    enableButtons();
  }
}
class ManageWasteLoadThread extends Thread{
  ManageWaste wp;
  String action;
  public ManageWasteLoadThread(ManageWaste wp,String action){
    super();
    this.wp = wp;
    this.action = action;
  }
  public void run(){
    if(action.equalsIgnoreCase("search")){
      wp.doSearch();
    }else if(action.equalsIgnoreCase("createwr")){
      wp.createShippmentRequest();
    }
  }
}
class ManageWaste_wasteTable_mouseAdapter extends java.awt.event.MouseAdapter {
  ManageWaste adaptee;
  ManageWaste_wasteTable_mouseAdapter(ManageWaste adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.wasteTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}
class ManageWaste_cartTable_mouseAdapter extends java.awt.event.MouseAdapter {
  ManageWaste adaptee;
  ManageWaste_cartTable_mouseAdapter(ManageWaste adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.cartTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}