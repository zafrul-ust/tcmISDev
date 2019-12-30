//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Waste Management Screen

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.GridBagConstraints2;
import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.appData.*;
import java.beans.*;
import javax.swing.table.*;
import java.util.*;
import java.text.*;
import java.awt.Component;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;
import java.awt.event.*;
import javax.swing.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class WasteManage extends JPanel {
  CmisApp cmis;
  int rNum = 0;

  CmisTable wasteTable;
  CmisTable cartTable;
  TableSorter sorter = new TableSorter();
  JPanel panel1 = new JPanel();
  Hashtable initialInfoH = null;
  CmisTable labPackOrderTable;

  //lab pack
  JPanel labP = new JPanel();
  String lastStorageArea = "";
  String lastVendorId = "";
  boolean storageVendorLoaded = false;

  public static final String[] colHeads = {"Rad Profile","Order","Facility","Generation Pt","Vend Profile","Vendor","Description","Waste Mgmt Opt",
                                           "Mgmt Opt Location","Packaging","Waste Category","Waste Type","LPP","Transferred Date"};
  public static final int[] colW = {60,65,50,60,80,120,130,100,100,75,100,75,50,75};
  String [] cartCols = new String[]{"Work Area","Vend Profile","Description","Container ID","Packaging",
                                    "LPP","Profile Id","Waste Request Id","Line Item","Container Id","Container Type","Lab Pack Service Date"};
  int[] colWidths = {0,90,232,75,75,0,0,0,0,0,0,0};

  final int CART_WORK_AREA = 0;
  final int CART_VENDOR_PROFILE_ID = 1;
  final int CART_PROFILE_DESC = 2;
  final int CART_QTY = 3;
  final int CART_PACKAGING = 4;
  final int CART_LPP = 5;
  final int CART_PROFILE_ID = 6;
  final int CART_WASTE_REQUEST_ID = 7;
  final int CART_LINE_ITEM = 8;
  final int CART_CONTAINER_ID = 9;
  final int CART_CONTAINER_TYPE = 10;
  final int CART_LP_SERVICE_DATE = 11;

  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel topP = new JPanel();
  JPanel bottomP = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel leftP = new JPanel();
  JPanel rightP = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  StaticJLabel facilityL = new StaticJLabel();
  StaticJLabel requesterL = new StaticJLabel();
  DataJLabel requesterT = new DataJLabel();
  JButton searchB = new JButton();
  FacilityCombo facC = new FacilityCombo();
  JScrollPane jsp = new JScrollPane();
  JPanel jPanel1 = new JPanel();
  JScrollPane cartJsp = new JScrollPane();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton addB = new JButton();
  JButton removeB = new JButton();
  JButton createB = new JButton();
  StaticJLabel vendorL = new StaticJLabel();
  JComboBox vendor = new JComboBox();
  JComboBox storageC = new JComboBox();
  StaticJLabel storageL = new StaticJLabel();
  JComboBox facilityC = new JComboBox();


  // columns
  int WASTE_ID_COL = 0;
  int WASTE_ITEM_ID_COL = 0;
  int VENDOR_PROFILE_ID_COL = 0;
  int VENDOR_COL = 0;
  int DESC_COL = 0;
  int LPP_COL = 0;
  int WORK_AREA_COL = 0;
  int WASTE_REQUEST_ID_COL = 0;
  int LINE_ITEM_COL = 0;
  int PACKAGING_COL = 0;
  int ORDER_NUMBER_COL = 0;
  int CONTAINER_ID_COL = 0;
  int CONTAINER_TYPE_COL = 0;
  int SEAL_CONTAINER_COL = 0;
  int STATE_WASTE_CODE_COL = 0;
  int SIZE_UNIT_COL = 0;
  int TSDF_COL = 0;

  //the maximum rows select at the same time
  final int MAXSELECTEDROW = 100;
  Vector vendorId = null;
  String lastSelectedFac = new String("");
  JButton newContainerB = new JButton();
  JTabbedPane cartTab = new JTabbedPane();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  GridBagLayout gridBagLayout6 = new GridBagLayout();

  Boolean allowDelete = new Boolean(false);
  JButton addLabPackB = new JButton();

  String preferredServiceDate = "";
  int estimatedNumDrum = 0;
  JPanel labPackP = new JPanel();
  JButton goToOrderB = new JButton();
  JScrollPane labPackJSP = new JScrollPane();
  GridBagLayout gridBagLayout7 = new GridBagLayout();

  String lastLabPackOrder = "0";
  Vector labPackOption = new Vector();

  public WasteManage() {
    try{
      jbInit();
    }catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setCmis(CmisApp cmis){
    this.cmis = cmis;
    dataLoaded();
  }

  void tab_changedPerformed(ChangeEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    Component comp = cartTab.getSelectedComponent();
    if (comp.equals(labP)) {
      setupLabPack(true);
    }
  }

  void goLabPack() {
    String selectedStorage = storageC.getSelectedItem().toString();
    String selectedVendor = vendor.getSelectedItem().toString();
    if (selectedStorage.startsWith("Select") || selectedVendor.startsWith("Select")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please select both Storage Area and Vendor.");
      gd.setVisible(true);
      cartTab.setSelectedIndex(0);
      return;
    }else {
      if (!lastStorageArea.equals(selectedStorage) || !lastVendorId.equals(selectedVendor)) {
        loadLabPack();
        lastStorageArea = selectedStorage;
        lastVendorId = selectedVendor;
      }
    }
  }
  void loadLabPack() {
    try {
      CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","LOAD_LAB_PACK"); //String, String
      query.put("FACILITY_ID",facilityC.getSelectedItem().toString());
      query.put("STORAGE_AREA",getStorageAreaId());
      query.put("VENDOR",getVendorId());
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        cartTab.setSelectedIndex(0);
        return;
      }
      Boolean succ = (Boolean)result.get("SUCCEEDED");
      if (succ.booleanValue()) {
        labPackJSP.getViewport().remove(labPackOrderTable);
        DbTableModel ctm = buildTableModel((Hashtable) result.get("SEARCH_DATA"));
        sorter = new TableSorter(ctm);
        labPackOrderTable = new CmisTable(sorter);
        sorter.setColTypeFlag(ctm.getColumnTypesString());
        sorter.addMouseListenerToHeaderInTable(labPackOrderTable);
        labPackOrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        labPackOrderTable.addMouseListener(new WasteManage_labPackOrderTable_mouseAdapter(this));

        //Nawaz 09/19/01
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
        labPackOrderTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = labPackOrderTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }
        labPackOrderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        labPackOrderTable.getTableHeader().setReorderingAllowed(true);
        // set column widths
        for(int i=0;i<labPackOrderTable.getColumnCount();i++){
          int width = ctm.getColumnWidth(i);
          labPackOrderTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
          labPackOrderTable.getColumn(ctm.getColumnName(i)).setWidth(width);
          if (width==0){
            labPackOrderTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
            labPackOrderTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
          }
        }
        labPackJSP.getViewport().setView(labPackOrderTable);

        if (labPackOrderTable.getRowCount() < 1) {
          GenericDlg gd = new GenericDlg(cmis.getMain(),"No Record Found",true);
          gd.setMsg("There are no open lab pack request\nfor the selected facility,storage area, and vendor");
          gd.setVisible(true);
          cartTab.setSelectedIndex(0);
        }else {
          setLabPackSelectedRow(0);
        }
      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg(result.get("MSG").toString());
        gd.setVisible(true);
        cartTab.setSelectedIndex(0);
      }
      CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
    }catch (Exception e) {
      e.printStackTrace();
      cartTab.setSelectedIndex(0);
      return;
    }
  }


  public void printScreenData(){
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setFacId(facilityC.getSelectedItem().toString());
    String storageArea = null;
    if (storageC.getSelectedItem().toString().startsWith("Select")) {
      storageArea = new String("");
    }else {
      storageArea = this.getStorageAreaId();
    }
    rod.setStorageArea(storageArea);
    rod.setVendorId(getVendorId());
    rod.setVendorName(vendor.getSelectedItem().toString());

    rod.setRequestorName(requesterT.getText());
    rod.setScreen("WM");
    rod.loadIt();
  }

  private void dataLoaded(){
    TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","GET_INITIAL_INFO"); //String, String
    query.put("USER_ID",cmis.getUserId());
    Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
    }

    requesterT.setText((String)result.get("USER_NAME").toString());
    requesterT.setEnabled(true);

    initialInfoH = (Hashtable)result.get("INITIAL_INFO");
    String prefFac = (String)result.get("PREF_FAC");

    allowDelete = (Boolean)result.get("ALLOW_DELETE");
    labPackOption = (Vector)initialInfoH.get("LAB_PACK_OPTION");
    Vector facilityIdV = (Vector)initialInfoH.get("FACILITY_ID");
    facilityC = ClientHelpObjs.loadChoiceFromVector(facilityC,facilityIdV);
    facilityC.setSelectedItem(prefFac);
    facilityC.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        storageVendorLoaded = false;
        loadStorageVendorArea();
      }
    });
    facilityC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
        storageVendorLoaded = false;
        loadStorageVendorArea();
      }
    });

    storageC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        storageC_actionPerformed(e);
      }
    });
    vendor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        vendor_actionPerformed(e);
      }
    });

    facilityC.setEnabled(true);

    //Nawaz 09/19/01
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
    wasteTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = wasteTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    enum1 = cartTable.getColumnModel().getColumns();
    cartTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    enum1 = labPackOrderTable.getColumnModel().getColumns();
    labPackOrderTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    enableButtons();
    ClientHelpObjs.makeDefaultColors(this);
  }

  private void loadStorageVendorArea() {
    String facId = facilityC.getSelectedItem().toString();
    if (!lastSelectedFac.equals(facId)) {
      storageC.removeAllItems();
      Hashtable locationInfo = (Hashtable)initialInfoH.get(facId);
      Vector locationDesc = (Vector)locationInfo.get("LOCATION_DESC");
      storageC = ClientHelpObjs.loadChoiceFromVector(storageC,locationDesc);
      if (storageC.getItemCount() > 1) {
        storageC.insertItemAt("Select a Storage Area",0);
        storageC.setSelectedIndex(0);
      }

      vendor.removeAllItems();
      Vector vendorDesc = (Vector)locationInfo.get("COMPANY_NAME");
      vendor = ClientHelpObjs.loadChoiceFromVector(vendor,vendorDesc);
    }
    lastSelectedFac = facId;
    storageC.setEnabled(true);
    vendor.setEnabled(true);
    vendor.setToolTipText(vendor.getSelectedItem().toString()+ "  ");
    storageVendorLoaded = true;
    setupLabPack(false);
  }

  private void setupLabPack(boolean search) {
    String key = facilityC.getSelectedItem().toString();
    if (!storageC.getSelectedItem().toString().startsWith("Select")) {
      key += getStorageAreaId();
    }
    if (!vendor.getSelectedItem().toString().startsWith("Select")) {
      key += getVendorId();
    }
    if (labPackOption.contains(key)) {
     addLabPackB.setEnabled(true);
     boolean labPackExist = false;
     for (int i = 0; i < cartTab.getComponentCount(); i++) {
       if (cartTab.getComponentAt(i).equals(labP)){
         labPackExist = true;
         break;
       }
     }
     //add lab pack tab if it's not there
     if (!labPackExist) {
       cartTab.add(labP,"Pending Lab Pack Order");
     }
     if (search) {
       cartTab.setSelectedComponent(labP);
       goLabPack();
     }
   }else {
      addLabPackB.setEnabled(false);
      for (int i = 0; i < cartTab.getComponentCount(); i++) {
        if (cartTab.getComponentAt(i).equals(labP)){
          cartTab.remove(i);
        }
      }
    }
  }  //end of method

  private void enableButtons(){
    addB.setEnabled(wasteTable.getSelectedRowCount()>0 && !BothHelpObjs.isBlankString(vendor.getSelectedItem().toString()));
    removeB.setEnabled(cartTable.getSelectedRowCount()>0);
    createB.setEnabled(cartTable.getRowCount()>0);
    vendor.setEnabled(cartTable.getRowCount()<1);
    storageC.setEnabled(cartTable.getRowCount()<1);
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
    cartTable.getColumn("Container ID").setWidth(75);
    cartTable.getColumn("Container ID").setMinWidth(75);
    cartTable.getColumn("Container ID").setMaxWidth(75);
    cartTable.getColumn("Container ID").setPreferredWidth(75);
    cartTable.getColumn("Vend Profile").setWidth(80);
    cartTable.getColumn("Vend Profile").setMinWidth(80);
    cartTable.getColumn("Vend Profile").setMaxWidth(80);
    cartTable.getColumn("Vend Profile").setPreferredWidth(80);
    cartTable.getColumn("Packaging").setWidth(80);
    cartTable.getColumn("Packaging").setMinWidth(80);
    cartTable.getColumn("Packaging").setMaxWidth(80);
    cartTable.getColumn("Packaging").setPreferredWidth(80);
    cartTable.getColumn("Packaging").setWidth(80);
    cartTable.getColumn("Container Id").setMinWidth(0);
    cartTable.getColumn("Container Id").setMaxWidth(0);
    cartTable.getColumn("Container Id").setPreferredWidth(0);
    cartTable.getColumn("Container Type").setMinWidth(0);
    cartTable.getColumn("Container Type").setMaxWidth(0);
    cartTable.getColumn("Container Type").setPreferredWidth(0);
    cartTable.getColumn("Lab Pack Service Date").setMinWidth(0);
    cartTable.getColumn("Lab Pack Service Date").setMaxWidth(0);
    cartTable.getColumn("Lab Pack Service Date").setPreferredWidth(0);
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
    facilityL.setHorizontalAlignment(SwingConstants.RIGHT);
    facilityL.setText("Facility:");
    searchB.setText("Search");
    searchB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchB_actionPerformed(e);
      }
    });
    rightP.setLayout(gridBagLayout5);
    jPanel1.setLayout(flowLayout1);
    addB.setText("Container");
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
    createB.setText("Generate Order");
    createB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createB_actionPerformed(e);
      }
    });
    addB.setEnabled(false);
    addB.setFont(new java.awt.Font("Dialog", 0, 10));
    addB.setMaximumSize(new Dimension(115, 25));
    addB.setMinimumSize(new Dimension(115, 25));
    addB.setPreferredSize(new Dimension(115, 25));
    addB.setToolTipText(" Add Container  ");
    removeB.setEnabled(false);
    removeB.setFont(new java.awt.Font("Dialog", 0, 10));
    removeB.setMaximumSize(new Dimension(100, 25));
    removeB.setMinimumSize(new Dimension(100, 25));
    removeB.setPreferredSize(new Dimension(100, 25));
    createB.setEnabled(false);
    createB.setFont(new java.awt.Font("Dialog", 0, 10));
    createB.setMaximumSize(new Dimension(130, 25));
    createB.setMinimumSize(new Dimension(130, 25));
    createB.setPreferredSize(new Dimension(130, 25));
    requesterT.setEnabled(false);

    vendor.setMaximumSize(new Dimension(180, 24));
    vendor.setMinimumSize(new Dimension(180, 24));
    vendor.setPreferredSize(new Dimension(180, 24));
    /*
    vendor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        vendor_actionPerformed(e);
      }
    }); */

    requesterT.setMaximumSize(new Dimension(180, 24));
    requesterT.setMinimumSize(new Dimension(180, 24));
    requesterT.setPreferredSize(new Dimension(180, 24));
    facC.setEnabled(false);
    facC.setMaximumSize(new Dimension(180, 24));
    facC.setMinimumSize(new Dimension(180, 24));
    facC.setPreferredSize(new Dimension(180, 24));
    facilityC.setMaximumSize(new Dimension(180, 24));
    facilityC.setMinimumSize(new Dimension(180, 24));
    facilityC.setPreferredSize(new Dimension(180, 24));
    facilityC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        facilityC_actionPerformed(e);
      }
    });

    DbTableModel ta = new DbTableModel(colHeads);
    ta.setColumnPrefWidth(colW);
    wasteTable = new CmisTable(ta);
    jsp.getViewport().setView(wasteTable);
    wasteTable.addMouseListener(new WasteManage_wasteTable_mouseAdapter(this));
    wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    wasteTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    labPackOrderTable = new CmisTable(ta);
    labPackJSP.getViewport().setView(labPackOrderTable);

    DbTableModel ctm = new DbTableModel(cartCols);
    // ctm.setEditableFlag(4);
    ctm.setColumnPrefWidth(colWidths);
    cartTable = new CmisTable(ctm);
    cartTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    cartTable.sizeColumnsToFit(true);
    cartTable.getTableHeader().setReorderingAllowed(true);
    // set column widths
    cartTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(int i=0;i<cartTable.getColumnCount();i++){
      int width = ctm.getColumnWidth(i);
      cartTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
      cartTable.getColumn(ctm.getColumnName(i)).setWidth(width);
      if (width==0){
        cartTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
        cartTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
      }
    }


    cartJsp.getViewport().setView(cartTable);
    cartTable.addMouseListener(new WasteManage_cartTable_mouseAdapter(this));

    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif","Search"));
    addB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add"));
    removeB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif","Delete"));
    createB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Submit"));

    storageC.setMaximumSize(new Dimension(180, 24));
    storageC.setMinimumSize(new Dimension(180, 24));
    storageC.setPreferredSize(new Dimension(180, 24));
    /*
    storageC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        storageC_actionPerformed(e);
      }
    }); */
    storageL.setFont(new java.awt.Font("Dialog", 0, 10));
    storageL.setText("Storage Area:");
    newContainerB.setText("New Container");
    newContainerB.setMaximumSize(new Dimension(100, 27));
    newContainerB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add"));
    newContainerB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newContainerB_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout4);




    labP.setLayout(gridBagLayout6);

    labP.setBorder(ClientHelpObjs.groupBox("Open Lab Pack Request"));


    cartTab.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        tab_changedPerformed(e);
      }
    });

    addLabPackB.setFont(new java.awt.Font("Dialog", 0, 10));
    addLabPackB.setMaximumSize(new Dimension(100, 25));
    addLabPackB.setMinimumSize(new Dimension(100, 25));
    addLabPackB.setPreferredSize(new Dimension(100, 25));
    addLabPackB.setText("Lab Pack");
    addLabPackB.setEnabled(false);
    addLabPackB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add Lab Pack"));
    addLabPackB.setToolTipText(" Add Lab Pack  ");
    addLabPackB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addLabPackB_actionPerformed(e);
      }
    });
    labPackP.setMaximumSize(new Dimension(200, 50));
    labPackP.setMinimumSize(new Dimension(200, 50));
    labPackP.setPreferredSize(new Dimension(200, 50));
    labPackP.setLayout(gridBagLayout7);
    goToOrderB.setText("Go To Order");
    goToOrderB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Lab Pack"));
    goToOrderB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goToOrderB_actionPerformed(e);
      }
    });
    cartTab.setFont(new java.awt.Font("Dialog", 0, 10));
    this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 25));
    topP.add(rightP, new GridBagConstraints2(1, 0, 1, 1, 0.7, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));
    rightP.add(cartTab, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));
    cartTab.add(jPanel2, "Shipment Order Request");
    //cartTab.add(labP, "Pending Lab Pack Order");
    labP.add(labPackP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    labPackP.add(labPackJSP, new GridBagConstraints2(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    labP.add(goToOrderB, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 40, 0, 0), 0, 0));
    cartTab.setTabPlacement(JTabbedPane.BOTTOM);
    jPanel2.add(jPanel1, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(addB, null);
    jPanel1.add(addLabPackB, null);
    jPanel1.add(removeB, null);
    jPanel1.add(createB, null);
    jPanel2.add(cartJsp, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(leftP, new GridBagConstraints2(0, 0, 1, 3, 0.4, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 154));
    leftP.add(requesterL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(facilityL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(vendorL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(searchB, new GridBagConstraints2(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
    leftP.add(requesterT, new GridBagConstraints2(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    //leftP.add(facC, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
    //        ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(facilityC, new GridBagConstraints2(1, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(vendor, new GridBagConstraints2(1, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(storageC, new GridBagConstraints2(1, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(storageL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(newContainerB, new GridBagConstraints2(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 0), 0, 0));
    this.add(bottomP, new GridBagConstraints2(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 265));
    bottomP.add(jsp, BorderLayout.CENTER);
  }

  void vendor_actionPerformed(ActionEvent e) {
    if (storageVendorLoaded) {
      Component comp = cartTab.getSelectedComponent();
      if (comp.equals(labP)) {
        setupLabPack(true);
      }else {
        setupLabPack(false);
      }
    }
  }

  public String getStorageAreaId() {
    String key = facilityC.getSelectedItem().toString();
    Hashtable locationInfo = (Hashtable)initialInfoH.get(key);
    Vector wasteLocationId = (Vector)locationInfo.get("WASTE_LOCATION_ID");
    int pos = storageC.getSelectedIndex();
    String storageId = null;
    if (storageC.getItemAt(0).toString().equalsIgnoreCase("Select a Storage Area")) {
      storageId = wasteLocationId.elementAt(pos-1).toString();
    }else {
      storageId = wasteLocationId.elementAt(pos).toString();
    }
    return storageId;
  }

  public String getVendorId() {
    String key = facilityC.getSelectedItem().toString();
    Hashtable vendorInfo = (Hashtable)initialInfoH.get(key);
    Vector vendId = (Vector)vendorInfo.get("VENDOR_ID");
    int pos = vendor.getSelectedIndex();
    String vId = vendId.elementAt(pos).toString();
    return vId;
  }

  DbTableModel buildTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    DbTableModel ctm = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      ctm.addRow((Object[]) data.elementAt(i));
    }
    ctm.setColumnPrefWidth(colWidths);
    ctm.setColumnType(colTypes);
    return ctm;
  }  //end of method

  void doSearch(){
    long sTime = new java.util.Date().getTime();
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","SEARCH"); //String, String
      query.put("FAC_ID",facilityC.getSelectedItem().toString());
      query.put("STORAGE_AREA",getStorageAreaId());
      query.put("VENDOR",getVendorId());
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      jsp.getViewport().remove(wasteTable);
      DbTableModel ctm = buildTableModel((Hashtable) result.get("SEARCH_DATA"));
      sorter = new TableSorter(ctm);
      wasteTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(wasteTable);
      wasteTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      wasteTable.addMouseListener(new WasteManage_wasteTable_mouseAdapter(this));

      //Nawaz 09/19/01
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
      wasteTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = wasteTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }
      wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      wasteTable.setCellSelectionEnabled(false);
      wasteTable.getTableHeader().setReorderingAllowed(true);
      WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
      WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();
      wasteTable.setDefaultRenderer(String.class, colorTableRenderer);
      wasteTable.setDefaultRenderer(Integer.class, colorTableRenderer);
      wasteTable.setDefaultRenderer(Double.class, colorTableRenderer);
      wasteTable.setDefaultRenderer(Boolean.class, checkTableRenderer);

      JCheckBox check = new JCheckBox();
      check.setHorizontalAlignment(JLabel.CENTER);
      wasteTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));

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
      WASTE_ITEM_ID_COL = ((Integer)h.get("WASTE_ITEM_ID")).intValue();
      VENDOR_PROFILE_ID_COL = ((Integer)h.get("VENDOR_PROFILE_ID")).intValue();
      VENDOR_COL = ((Integer)h.get("VENDOR_ID")).intValue();
      LPP_COL = ((Integer)h.get("LPP")).intValue();
      WORK_AREA_COL = ((Integer)h.get("WORK_AREA")).intValue();
      WASTE_REQUEST_ID_COL = ((Integer)h.get("WASTE_REQUEST_ID")).intValue();
      LINE_ITEM_COL = ((Integer)h.get("LINE_ITEM")).intValue();
      DESC_COL = ((Integer)h.get("WASTE_PROFILE_DESC")).intValue();
      PACKAGING_COL = ((Integer)h.get("PACKAGING")).intValue();
      ORDER_NUMBER_COL = ((Integer)h.get("ORDER_NUMBER")).intValue();
      CONTAINER_ID_COL = ((Integer)h.get("CONTAINER_ID")).intValue();
      CONTAINER_TYPE_COL = ((Integer)h.get("BULK_OR_CONTAINER")).intValue();
      SEAL_CONTAINER_COL = ((Integer)h.get("SELECTED_ROW")).intValue();
      STATE_WASTE_CODE_COL = ((Integer)h.get("STATE_WASTE_CODE")).intValue();
      SIZE_UNIT_COL = ((Integer)h.get("SIZE_UNIT")).intValue();
      TSDF_COL = ((Integer)h.get("TSDF")).intValue();

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
      ClientHelpObjs.setComboBoxUpdateUi(this);
      enableButtons();
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
      this.revalidate();
    }
  } //end of method

  protected void createShippmentRequest(){
    if(cartTable.getRowCount()<1)return;
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","CREATE_ORDER_ID"); //String, String
      query.put("SHIPMENT_REQUEST",buildShipmentVector());
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
        /*
        gd.setTitle("Waste Order Created");
        gd.setMsg("A new Waste order was created.");
        gd.setVisible(true); */
        removeAllFromCart();
        cmis.getMain().goWasteOrder(0,wr);
      }else{
        gd.setTitle("Error creating Waste Order");
        gd.setMsg("A Waste Order Request could not be created. Please check your data and try again.");
        gd.setVisible(true);
      }
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      ClientHelpObjs.setEnabledContainer(this,true);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      enableButtons();
      this.revalidate();
    }
  }
  private Vector buildShipmentVector(){
    Vector v = new Vector();
    for(int i=0;i<cartTable.getRowCount();i++){
      Hashtable h = new Hashtable();
      h.put("CONTAINER_ID",cartTable.getModel().getValueAt(i,CART_CONTAINER_ID).toString());
      h.put("CONTAINER_TYPE",cartTable.getModel().getValueAt(i,CART_CONTAINER_TYPE).toString());
      h.put("LAB_PACK_SERVICE_DATE",cartTable.getModel().getValueAt(i,CART_LP_SERVICE_DATE).toString());
      v.addElement(h);
    }
    return v;
  }
     /*
   private Vector buildShipmentVector(){
    Vector v = new Vector();
    for(int i=0;i<cartTable.getRowCount();i++){
      Hashtable h = new Hashtable();
      String containerTypeS = cartTable.getModel().getValueAt(i,CART_CONTAINER_TYPE).toString();
      h.put("CONTAINER_TYPE",containerTypeS);
      if (containerTypeS.equalsIgnoreCase("b")){
        h.put("CONTAINER_ID",cartTable.getModel().getValueAt(i,CART_CONTAINER_ID).toString());
      } else {
        h.put("CONTAINER_ID",new Integer(cartTable.getModel().getValueAt(i,CART_CONTAINER_ID).toString()));
      }

      v.addElement(h);
    }
    return v;
  }*/

  private Hashtable getShipmentInfo(){
    Hashtable h = new Hashtable();
    h.put("FACILITY_ID",facilityC.getSelectedItem().toString());
    h.put("STORAGE_ID",getStorageAreaId());
    h.put("VENDOR_ID",getVendorId());
    return h;
  }

  private void doRightClick(JTable table, MouseEvent e){
     //Table clicked
    JFrame F;
    int catSel = table.getSelectedRow();
    WasteMetaPopUp mpu = new WasteMetaPopUp(cmis,
    Integer.parseInt(table.getModel().getValueAt(catSel,WASTE_ITEM_ID_COL).toString())); // Waste Profile Id
    panel1.add(mpu);

    boolean containerOnOrder = false;

    int[] row = new int[MAXSELECTEDROW];
    row   = wasteTable.getSelectedRows();
    Vector containerV = new Vector();
    for (int i = 0; i < row.length; i++) {
      containerV.addElement(wasteTable.getModel().getValueAt(row[i],this.CONTAINER_ID_COL).toString());
      String temp = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),ORDER_NUMBER_COL).toString();
      if (!BothHelpObjs.isBlankString(temp)) {
        containerOnOrder = true;
      }
    }
    mpu.setContainerId(containerV);

    mpu.setWasteManagement(this);
    mpu.setFacilityId(facilityC.getSelectedItem().toString());
    mpu.setVendorId(this.getVendorId());
    mpu.setStorageLocation(this.getStorageAreaId());

    String ctype = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),CONTAINER_TYPE_COL).toString();
    //bulk waste can not use any of the following right mouse option and only one row can be selected at a time
    if (ctype.equalsIgnoreCase("C") && row.length == 1) {
      String vendorProfileId = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),VENDOR_PROFILE_ID_COL).toString();
      //profile change
      mpu.enableMenu("ChgProfile");
      String pack = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),PACKAGING_COL).toString();
      mpu.setPackaging(pack);
      mpu.setFromWasteManage(true);
      mpu.setSelectedRow(wasteTable.getSelectedRow());
      mpu.setChangeTsdfProfile("Y".equalsIgnoreCase(wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),TSDF_COL).toString()));

      Boolean isOpen = (Boolean)wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),SEAL_CONTAINER_COL);
      String onOrder = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),ORDER_NUMBER_COL).toString();
      //need to find out if container is unsealed and only alowing user to edit one container at a time.
      if (BothHelpObjs.isBlankString(onOrder) && !isOpen.booleanValue() && !vendorProfileId.equalsIgnoreCase("UNKNOWN") &&
          isSameProfile(wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),this.VENDOR_PROFILE_ID_COL).toString())) {
        mpu.enableMenu("EmptyInto");
        mpu.setVendorProfileId(wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),this.VENDOR_PROFILE_ID_COL).toString());
      }
      //if the container is not on order and it is unseal
      if (BothHelpObjs.isBlankString(onOrder) && isOpen.booleanValue()) {
        mpu.enableMenu("AddInto");
        String vendorProfileDesc = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),VENDOR_PROFILE_ID_COL).toString();
        vendorProfileDesc = vendorProfileDesc + " - " + wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),DESC_COL).toString();
        mpu.setVendorProfileDesc(vendorProfileDesc);
        mpu.setStateWasteCode(wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),this.STATE_WASTE_CODE_COL).toString());
        mpu.setPackaging(wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),this.PACKAGING_COL).toString());
        mpu.setSizeUnit(BothHelpObjs.makeBlankFromNull(wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),this.SIZE_UNIT_COL).toString()));
        mpu.setSelectedRow(wasteTable.getSelectedRow());
      }
      //if the container is unseal
      if (isOpen.booleanValue()) {
        mpu.enableMenu("SealContainer");
        mpu.setSelectedRow(wasteTable.getSelectedRow());
      }
    }
    //allow multiple rows to be selected
    if (ctype.equalsIgnoreCase("C")) {
      //display delete container if the following is true
      if (!containerOnOrder && allowDelete.booleanValue()) {
        mpu.enableMenu("DeleteContainer");
      }
      mpu.enableMenu("PrintLabel");
    }

    mpu.show(table,e.getX(),e.getY());
    return;
  }

  public boolean isSameProfile(String profile) {
    boolean result = false;
    for (int i = 0; i < wasteTable.getRowCount(); i++) {
      Boolean open = (Boolean)wasteTable.getModel().getValueAt(i,SEAL_CONTAINER_COL);
      if (open.booleanValue()) {
        String openProfile = wasteTable.getModel().getValueAt(i,VENDOR_PROFILE_ID_COL).toString();
        if (openProfile.equals(profile)) {
          result = true;
          break;
        }
      }
    }
    return result;
  }

  void setSelectedRow(int x){
    ListSelectionModel lsm = wasteTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    wasteTable.scrollRectToVisible(wasteTable.getCellRect(x,0,false));
  }

  void setLabPackSelectedRow(int x){
    ListSelectionModel lsm = labPackOrderTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    labPackOrderTable.scrollRectToVisible(labPackOrderTable.getCellRect(x,0,false));
  }

  private boolean canAddToCart(){
    if(cartTable.getRowCount() < 1 && wasteTable.getSelectedRowCount() > 0){
      return true;
    }
    if(wasteTable.getSelectedRowCount() < 1)return false;
    String cid = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),CONTAINER_ID_COL).toString();
    for(int i=0;i<cartTable.getRowCount();i++){
      if(cid.equals(cartTable.getModel().getValueAt(i,CART_CONTAINER_ID).toString())){
        return false;
      }
    }
    return true;
  }
  /*
  private boolean canAddToCart(){
    if(cartTable.getRowCount() < 1 && wasteTable.getSelectedRowCount() > 0){
      return true;
    }
    if(wasteTable.getSelectedRowCount() < 1)return false;
    String wri = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),WASTE_REQUEST_ID_COL).toString();
    String li =  wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),LINE_ITEM_COL).toString();
    String cType = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),CONTAINER_TYPE_COL).toString();
    // String v = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),VENDOR).toString();
    for(int i=0;i<cartTable.getRowCount();i++){
      if(wri.equals(cartTable.getModel().getValueAt(i,CART_WASTE_REQUEST_ID).toString()) &&
         li.equals(cartTable.getModel().getValueAt(i,CART_LINE_ITEM).toString())){
        return false;
      }
      /*if(!cType.equals(cartTable.getModel().getValueAt(i,CART_CONTAINER_TYPE).toString())) {
        return false;
      }
    }
    return true;
  }*/
  private void addToCart(){
    // must be selected
    if(wasteTable.getSelectedRowCount()<1)return;

    // cannot already be in the cart
    if(!canAddToCart()){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Container Already Selected.",true);
      gd.setMsg("You already selected this waste container.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }

    //only one vendor per order.
    String v = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),VENDOR_COL).toString();
    if(!(v.equalsIgnoreCase(vendor.getSelectedItem().toString()))){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"In valid vendor.",true);
      gd.setMsg("You may only use one Vendor per order.");
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
        gd.setMsg("Double click on 'Order' column to go to order.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }

      //can't put UNKNOWN profile into shopping cart
      String vProfileId = wasteTable.getModel().getValueAt(row[i],VENDOR_PROFILE_ID_COL).toString();
      if (vProfileId.equalsIgnoreCase("UNKNOWN")){
        wasteTable.setRowSelectionInterval(row[i],row[i]);
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Unknown Profile.",true);
        gd.setMsg(" Unknown profile can not be added to this order.\n You can change unknown profile by using right mouse click\n and select 'Change Profile'.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }
      //can't add unseal container to order
      Boolean isOpen = (Boolean)wasteTable.getModel().getValueAt(row[i],SEAL_CONTAINER_COL);
      if (isOpen.booleanValue()) {
        wasteTable.setRowSelectionInterval(row[i],row[i]);
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Unseal Container.",true);
        gd.setMsg(" The container you selected is unsealed.\n You can seal it for pickup by using\n right mouse click and select 'Seal Container'.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }


     // if ((vendor.getSelectedItem().toString().equals(wasteTable.getModel().getValueAt(row[i],VENDOR_COL))) &&
       //   (waC.getSelectedWorkAreaID().toString().equals(wasteTable.getModel().getValueAt(row[i],WORK_AREA_COL)))) {
       if ((vendor.getSelectedItem().toString().equals(wasteTable.getModel().getValueAt(row[i],VENDOR_COL)))) {
        oa[CART_WORK_AREA] = wasteTable.getModel().getValueAt(row[i],WORK_AREA_COL);
        oa[CART_QTY] =  wasteTable.getModel().getValueAt(row[i],CONTAINER_ID_COL);
        oa[CART_PACKAGING] = wasteTable.getModel().getValueAt(row[i],PACKAGING_COL);
        oa[CART_PROFILE_DESC] = wasteTable.getModel().getValueAt(row[i],DESC_COL);
        oa[CART_LPP] = wasteTable.getModel().getValueAt(row[i],LPP_COL);
        oa[CART_VENDOR_PROFILE_ID] = wasteTable.getModel().getValueAt(row[i],VENDOR_PROFILE_ID_COL);
        oa[CART_PROFILE_ID] = wasteTable.getModel().getValueAt(row[i],WASTE_ITEM_ID_COL);
        oa[CART_WASTE_REQUEST_ID] = wasteTable.getModel().getValueAt(row[i],WASTE_REQUEST_ID_COL);
        oa[CART_LINE_ITEM] = wasteTable.getModel().getValueAt(row[i],LINE_ITEM_COL);
        oa[CART_CONTAINER_ID] = wasteTable.getModel().getValueAt(row[i],CONTAINER_ID_COL);
        oa[CART_CONTAINER_TYPE] = wasteTable.getModel().getValueAt(row[i],CONTAINER_TYPE_COL);
        oa[CART_LP_SERVICE_DATE] = "";
        DbTableModel model = (DbTableModel)cartTable.getModel();
        model.addRow(oa);
        enableButtons();
      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"ERROR.",true);
        gd.setMsg("The waste you selected is not in your storage area.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }
    }
  }

  public void addLabPackToCart(){
    DbTableModel model = (DbTableModel)cartTable.getModel();
    Object[] oa = new Object[cartCols.length];
    oa[CART_WORK_AREA] = getStorageAreaId();
    oa[CART_QTY] = "";
    oa[CART_PACKAGING] = "";
    oa[CART_PROFILE_DESC] = "Lab pack request for "+this.estimatedNumDrum+" drums";
    oa[CART_LPP] = "";
    oa[CART_VENDOR_PROFILE_ID] = "Unknown";
    oa[CART_PROFILE_ID] = "";
    oa[CART_WASTE_REQUEST_ID] = "";
    oa[CART_LINE_ITEM] = "";
    oa[CART_CONTAINER_ID] = (new Integer(estimatedNumDrum)).toString();
    oa[CART_CONTAINER_TYPE] = "L";
    oa[CART_LP_SERVICE_DATE] = this.preferredServiceDate;
    model.addRow(oa);
    enableButtons();
    cartTable.revalidate();
  }

  private void removeFromCart(){
    int[] r = cartTable.getSelectedRows();
    int x = cartTable.getSelectedRowCount();
    for(int i= x-1; i >= 0;i--){
      removeFromCart(r[i]);
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
    if (storageC.getSelectedItem().toString().equalsIgnoreCase("Select a Storage Area")){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Select your Storage Area.",true);
      gd.setMsg("Please select your Storage Area.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }
    WasteManageLoadThread wplt = new WasteManageLoadThread(this,"search");
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
    WasteManageLoadThread wplt = new WasteManageLoadThread(this,"createWR");
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
        doRightClick(wasteTable,e);
      }else if(e.getClickCount() == 2){
        addToCart();
      }
    }
    enableButtons();
  }
  void cartTable_mouseClicked(MouseEvent e){
    if(e.isMetaDown()){
      doRightClick(cartTable,e);
    }
    enableButtons();
  }

  void labPackOrderTable_mouseClicked(MouseEvent e){
    if ((e.getClickCount() == 2) ) {
      String num = labPackOrderTable.getModel().getValueAt(labPackOrderTable.getSelectedRow(),0).toString();
      if (!BothHelpObjs.isBlankString(num)) {
        Integer orderNum = new Integer(num);
        cmis.getMain().goWasteOrder(0,orderNum.intValue());
      }
    }
  }

  void facilityC_actionPerformed(ActionEvent e) {
    Component comp = cartTab.getSelectedComponent();
    if (comp.equals(labP)) {
      setupLabPack(true);
    }
  }

  void storageC_actionPerformed(ActionEvent e) {
    if (storageVendorLoaded) {
      Component comp = cartTab.getSelectedComponent();
      if (comp.equals(labP)) {
        setupLabPack(true);
      }else {
        setupLabPack(false);
      }
    }
  }

  void newContainerB_actionPerformed(ActionEvent e) {
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    String facilityId = (String)facilityC.getSelectedItem();
    if (storageC.getSelectedItem().toString().equalsIgnoreCase("Select a Storage Area")){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Select your Storage Area.",true);
      gd.setMsg("Please select your Storage Area.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
      return;
    }
    NewContainer nc = new NewContainer(cmis.getMain(),facilityC.getSelectedItem().toString(),"New Container",this,getStorageAreaId(),getVendorId());
    nc.setParent(cmis);
    nc.loadIt();
    CenterComponent.centerCompOnScreen(nc);
    nc.setVisible(true);
    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
  }

  void addLabPackB_actionPerformed(ActionEvent e) {
    if (facilityC.getSelectedItem().toString().startsWith("Select")){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please select a facility.");
      gd.setVisible(true);
      return;
    }
    //if (getStorageAreaId().startsWith("Select")) {
    if (storageC.getSelectedItem().toString().startsWith("Select")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please select a storage area.");
      gd.setVisible(true);
      return;
    }
    if (vendor.getSelectedItem().toString().startsWith("Select")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please select a vendor.");
      gd.setVisible(true);
      return;
    }
    for (int i = 0; i < cartTable.getRowCount(); i++) {
      String labPack = cartTable.getModel().getValueAt(i,CART_CONTAINER_TYPE).toString();
      if (labPack.equalsIgnoreCase("L")) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg("A lab pack request already exist on this order.");
        gd.setVisible(true);
        return;
      }
    }

    LabPackRequestDlg lpr = new LabPackRequestDlg(cmis.getMain(),"Lab Pack Request",this);
    CenterComponent.centerCompOnScreen(lpr);
    lpr.setParent(cmis);
    lpr.setVisible(true);
  }

  void goToOrderB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (labPackOrderTable.getRowCount() > 0) {
      String num = labPackOrderTable.getModel().getValueAt(labPackOrderTable.getSelectedRow(),0).toString();
      //NOTE -- this will not work if user already click on the order before
      if (!BothHelpObjs.isBlankString(num) && !lastLabPackOrder.equals(num)) {
        lastLabPackOrder = num;
        Integer orderNum = new Integer(num);
        cmis.getMain().goWasteOrder(0,orderNum.intValue());
      }
    }
  }   //end of method

} //end of class

class WasteManageLoadThread extends Thread{
  WasteManage wp;
  String action;
  public WasteManageLoadThread(WasteManage wp,String action){
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
class WasteManage_wasteTable_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteManage adaptee;
  WasteManage_wasteTable_mouseAdapter(WasteManage adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.wasteTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}
class WasteManage_cartTable_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteManage adaptee;
  WasteManage_cartTable_mouseAdapter(WasteManage adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.cartTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}
class WasteManage_labPackOrderTable_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteManage adaptee;
  WasteManage_labPackOrderTable_mouseAdapter(WasteManage adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.labPackOrderTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}
