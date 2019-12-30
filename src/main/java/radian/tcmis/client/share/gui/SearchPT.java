//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui Fcomponents


package radian.tcmis.client.share.gui;

//import com.borland.dx.dataset.Variant;
import java.beans.*;

import com.borland.jb.util.Trace;

//import java.awt.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;

import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.appData.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.GridBagConstraints2;
import javax.swing.text.*;
import java.math.BigDecimal;

import javax.swing.tree.TreePath;
import radian.tcmis.client.share.gui.nchem.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;
import java.io.File;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class SearchPT extends JPanel {
  // basket
  AttributiveCellTableModel catModel;
  JPopupMenu popup;
  JWindow dragWin;
  boolean loading = true;
  //Thread
  Thread thread ;

  boolean search = true;

  public String[] baskColsNew = {"Work Area","Part Number","Part Qty","Example Packaging","Item"};
  public int[] baskColWidths = {100,80,70,100,55};
  public int[] baskColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING};
  Hashtable baskStyle;
  public String[] catColsNew = {"Part","Description","Type","Shelf Life @ Storage Temp","Base Price"};
  public int[] catColWidths = {100,80,70,100,55};
  public int[] catColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING};
  Hashtable catStyle;
  MultiSpanCellTable basketTableNew;
  int CART_PART_COL = 0;
  int CART_QTY_COL = 0;
  int CART_EXT_PRICE_COL = 0;       //2-26-02
  int CART_ITEM_COL = 0;
  int CART_CATALOG_COL = 0;
  int CART_CATALOG_COMPANY_ID_COL = 0;
  int CART_FACILITY_COL = 0;
  int CART_WORK_AREA_COL = 0;
  int CART_PR_COL = 0;
  int CART_PACKAGING_COL = 0;
  int CART_TYPE_COL = 0;
  int CART_EXAMPLE_ITEM_COL = 0;
  int CART_REQUIRED_DATE_COL = 0;
  int CART_CATALOG_PRICE_COL = 0;
  int CART_UNIT_PRICE_COL = 0;
  int CART_MIN_BUY_COL = 0;       //2-26-02
  int CART_PART_GROUP_COL = 0;
  int CART_PART_DESC_COL = 0;
  int CART_NOTE_COL = 0;
  int CART_REAL_NOTE_COL = 0;
  int CART_CRITICAL_COL = 0;
  int CART_INVENTORY_GROUP_COL = 0;
  int CART_DISPENSED_UOM_COL = 0;
  int CART_DISPENSED_ITEM_TYPE_COL = 0;
  int CART_CURRENCY_ID_COL = 0;
  int CART_ORDER_QUANTITY_RULE_COL = 0;
  int CART_LINE_ITEM_COL = 0;

  //trong 1-29-01
  // A - active, I - inactive, B - both
  //String workAreaStatus = "A";
  String currentScreen = "Catalog";

  final String SELECT_WORK_AREA = "Select a Work Area";
  //catalog
  final String[] catCols = {"Item #","Facility","Part Number","Material Desc.","Grade","Manufacturer","Mfg Part No.","Packaging","LPP","Type","Shelf Life","Appr. Status","MSDS","SPEC","MAT_ID","SPEC_ID"};
  //                          0           1          2           3                4            5          6                7        8      9        10          11      12      13         14
  //final String[] catCols = {"Catalog","Group","Part","Description","Type","Shelf Life @ Storage Temp","Base Price","# per Part","Item","# per Item","Component Description","Packaging","Manufacturer","Mfg Part No.","Status"};
  final Long catColTypeFlagNew = new Long("111111111111111");
  final Long catColTypeFlag = new Long("1111111181111111");
  //Basket
  String[] baskCols = {"Item","Part Number","Material Desc.","Work Area","Qty","Packaging","Work Area ID","LPP"};
  final int baskEditableFlag = 16;
  final String[] qty = {"1","2","3","5","6","7","8","9","10"};
  //final Long baskColTypeFlag = new Long("2111281");
  final Long baskColTypeFlag = new Long("21112811");     //2-15-01 adding lpp
  Hashtable priceHash = new Hashtable();
  //progress
  int progBarValue = 0;
  Vector cats = new Vector();
  int next=0; int back=0;
  //parent
  CmisApp parent;

  //table column index
  int FACILITY_COL = 0;
  int WORK_AREA_COL = 0;
  int GROUP_COL = 0;
  int PART_COL = 0;
  int DESC_COL = 0;
  int TYPE_COL = 0;
  int SHELF_COL = 0;
  int BASE_COL = 0;
  int CATALOG_PRICE_COL = 0;
  int MIN_BUY_COL = 0;          //2-26-02
  int ITEM_PART_COL = 0;
  int ITEM_COL = 0;
  int COMPONENT_DESC_COL = 0;
  int PACKAGING_COL = 0;
  int GRADE_COL = 0;
  int MANUFACTURER_COL = 0;
  int MPN_COL = 0;
  int STATUS_COL = 0;
  int CATALOG_COL = 0;
  int CATALOG_COMPANY_COL = 0;
  int MATERIAL_COL = 0;
  int MSDS_ONLINE_COL = 0;
  int PART_GROUP_COL = 0;
  int ITEM_GROUP_COL = 0;
  int COMMENT_COL = 0;
  int PART_COLOR_COL = 0;
  int EXAMPLE_PACKAGING_COL = 0;
  int PART_ITEM_ROW_COL = 0;
  int AVAL_QTY_COL = 0;           //2-05-02
  int INVENTORY_GROUP_COL = 0;
  int SIZE_UNIT_OPTION_COL = 0;
  int ITEM_TYPE_COL = 0;
  int CURRENCY_ID_COL = 0;
  int CREATE_MR_FROM_CATALOG_COL = 0;
  int BASELINE_RESET_COL = 0;
  int BASELINE_EXPIRATION_COL = 0;
  int INVENTORY_GROUP_NAME_COL = 0;
  int ORDER_QUANTITY_COL = 0;
  int ORDER_QUANTITY_RULE_COL = 0;
  int MEDIAN_LEAD_TIME_COL = 0;

  String searchFacility = "";
  String searchWorkArea = "";
  String searchWorkAreaDesc = "";
  boolean partViewLoaded = false;
  boolean detailLoaded = false;
  Hashtable searchResultH;



  FacilityCombo facChoice = new FacilityCombo();
  WorkAreaCombo appChoice = facChoice.getWorkAreaCombo();
  boolean facChoiceLoaded = false;
  boolean dataLoaded = false;

  StaticJLabel userLabel = new StaticJLabel();
  StaticJLabel facilityL = new StaticJLabel();
  StaticJLabel workAreaL = new StaticJLabel();
  DataJLabel userName = new DataJLabel();
  JRadioButton facilityCheck = new JRadioButton();
  JRadioButton oFacilityCheck = new JRadioButton();
  ButtonGroup facCheckGroup = new ButtonGroup();

  JRadioButton eEvalCheck = new JRadioButton();  //trong 3/14/00
  JRadioButton catCheck = new JRadioButton();  //trong 3/29

  JCheckBox wAreaCheck = new JCheckBox();
  JTextField itemText = new JTextField();
  StaticJLabel label3 = new StaticJLabel();
  JButton cancelB = new JButton();

  JButton nmB = new JButton();
  JButton nspB = new JButton();
  JButton npB = new JButton();
  JButton naB = new JButton();
  JButton ngB = new JButton();

  //trong 3/1/00
  JButton evalB = new JButton();
  String eval = new String("normal");
  JButton ctcB = new JButton();
  String actionEval = new String("normal");
  JButton roeB = new JButton();
  JPanel eEvalPane = new JPanel();      //trong 3/30
  GridLayout gridLayout5 = new GridLayout();    //trong 3/30
  //3-27-02 Eng Eval
  JPanel engEvalPane = null;
  MultiSpanCellTable itemCatalogTable = null;
  String[] itemCatCols;
  int[] itemCatColWidths;
  int[] itemCatColTypes;
  SearchPT_itemCatalogTable_mouseAdapter itemCatalogTableMouseAdapter = null;
  String searchPartCatText = "";
  //String searchPartCatFac = "";
  //String searchPartCatWA = "";
  String searchItemCatText = "";
  String searchItemCatFac = "";
  String searchItemCatWA = "";
  String searchPartCatMsg = "";
  String searchItemCatMsg = "";
  boolean searchPartCatButtonActive = true;
  boolean searchItemCatButtonActive = true;
  JCheckBox itemCatEvalCheck = null;
  JCheckBox itemCatRequestorCheck = null;
  JCheckBox itemCatFacCheck = null;
  JCheckBox itemCatWACheck = null;

  Hashtable accSysH = new Hashtable(); //trong 4/8
  String mySelectedAccSys = "";
  Hashtable prefAccSysH = new Hashtable();    //trong 4/9

  JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
  JPanel top = new JPanel();
  JPanel bottom = new JPanel();

  int basketLine = 0;
  int basketSel = 0;
  int catSel = 0;
  long lastClick = 0;
  int selectedRow = 0;

  JPanel basketPane = new JPanel();
  JPanel nchemPane = new JPanel();

  //3-21-02 Ariba Repair
  JPanel aribaRepairPane = null;
  JButton aribaRepairSearchB = null;
  JButton aribaRepairSubmitB = null;
  MultiSpanCellTable aribaRepairBasketTable;
  Boolean aribaRepair = null;
  JTextField aribaDateT = null;
  JScrollPane aribaRepairJPS = null;
  MultiSpanCellTable aribaRepairTable = null;
  AttributiveCellTableModel aribaRepairModel;

  int metaItem = 0;
  int basketMax = 7;

  //Swing
  JScrollPane jScrollPane1 = new JScrollPane();
  //CmisTable catTable = new CmisTable();

  MultiSpanCellTable catTableNew;

  JButton searchB = new JButton();
  JButton addItem = new JButton();
  JButton basketRemove = new JButton();
  JButton addPR = new JButton();

  //TableOrganizer tOrg = new TableOrganizer();
  //TableSorter  sorterCat = new TableSorter();
  //CmisTableModel cmisTModelCat = new CmisTableModel();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jScrollPane2 = new JScrollPane();
  //CmisTable basketTable = new CmisTable();

  //CmisTableModel cmisTModelBask = new CmisTableModel();
  //TableSorter  sorterBask = new TableSorter();
  JPanel panel2 = new JPanel();
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();

  Boolean moreChargenum1 = new Boolean(false);

  boolean isDragging = false;
  JPanel groupBox1 = new JPanel();
  JPanel groupBox2 = new JPanel();
  JTabbedPane groupBox3 = new JTabbedPane();

  StaticJLabel label1 = new StaticJLabel();
  JPanel groupBox4 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel panel3 = new JPanel();
  JPanel panel4 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel panel5 = new JPanel();
  BorderLayout borderLayout6 = new BorderLayout();
  JPanel panel6 = new JPanel();
  JPanel panel7 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  BorderLayout borderLayout8 = new BorderLayout();
  GridLayout gridLayout2 = new GridLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridLayout gridLayout3 = new GridLayout();
  ImageIcon ss = new ImageIcon();
  GridLayout gridLayout4 = new GridLayout();

  // report fields
  String reportName;
  String reportFac;
  String reportWorkArea;
  String reportSearch;
  JTextField jTextField1 = new JTextField();

  //new catalog design
  JComboBox catalogC = new JComboBox();
  StaticJLabel catalogL = new StaticJLabel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JCheckBox activeCheck = new JCheckBox();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  boolean updateMR = false;
  int prNum = 0;
  Vector updateMRInfo = new Vector();
  int partRowCount = 0;
  int itemRowCount = 0;
  JComboBox viewC = new JComboBox();
  AttributiveCellTableModel modelDetail;
  AttributiveCellTableModel modelPart;
  Hashtable tableAttribute;
  String editAccountSysID = "";

  Vector facInEComList = new Vector();
  Boolean ecomByPass = new Boolean(false);
  Boolean displayEngEval = new Boolean(false);
  Hashtable facilityCatalog = null;
  Vector catAddFacSingleAppList = new Vector();

  Hashtable canCreateMRAppH = null;
  boolean processingData = false;

  //POS - Point of sale
  Hashtable posFacilityH = null;
  String pointOfSaleName = "";
  String lastSelectedCatalog = "";
  DataJLabel customerT = new DataJLabel();
  StaticJLabel customerL = new StaticJLabel();
  JButton customerB = new JButton();
  Integer customerID = new Integer(0);
  boolean customerSearch = false;
  JCheckBox posApprovedForWACheck = new JCheckBox();
  JComboBox posFacC = new JComboBox();
  JComboBox posWAC = new JComboBox();
  Hashtable posCustFacWaInfo = new Hashtable();
  boolean loadingCatalogC = false;
  Hashtable posChargeInfoH = new Hashtable();
  JComboBox posLimitedWAC = new JComboBox();
  DataJLabel customerLimitT = new DataJLabel();
  StaticJLabel customerLimitL = new StaticJLabel();
  String approverRequired = "n";
  CatalogActionListener catalogActionListener = null;
  JButton clearCustomerB = new JButton();
  MultiSpanCellTable catPOSTable;
  String searchPOSCatMsg = "";
  String searchPOSFacility = "";
  String searchPOSWorkArea = "";
  String searchPOSWorkAreaDesc = "";
  Hashtable selectedSearchPartDataH = new Hashtable();
  Hashtable selectedSearchPOSDataH = new Hashtable();
  Hashtable selectedSearchItemDataH = new Hashtable();
  String catalogInShoppingCart = "";
  Integer shoppingCartForUserID = new Integer(0);
  SearchPT_facChoice_itemAdapter partFacChoiceItemAdapter;
  SearchPT_POSC_itemAdapter POSCItemAdapter;

  TreePath path;

  public void setUpdateMR(boolean b) {
    updateMR = b;
  }
  public void setPrNumber(int pr) {
    prNum = pr;
  }

  public SearchPT(CmisApp cmis) {
    this.parent = cmis;
    facChoice.setCmisApp(cmis);
    try {
      jbInit();
      facChoice.setUseAllFacilities(false);
      facChoice.setUseAllWorkAreas(false);
      facChoice.setForceSelectWorkArea(true);
      facChoice.setLoadType(FacilityCombo.PREF_FACS);
      facChoice.addPropertyChangeListener(new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
          if(e.getPropertyName().equalsIgnoreCase("loaded")) {
            dataLoaded(facChoice);
          }
        }
      });
      //sending a flag to decided whether to display Inactive work area
      facChoice.setCurrentScreen(this.currentScreen);
      facChoice.loadFacilityData();

      accSysH = (Hashtable)facChoice.getAccSysH();
      prefAccSysH = (Hashtable)facChoice.getPrefAccSysH();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  void itemCatalogTable_mouseReleased(MouseEvent e) {
    int firstIndex = itemCatalogTable.getSelectedRow();
    int lastIndex = firstIndex;
    if (e.isMetaDown()) {
      Point p = e.getPoint();
      firstIndex = itemCatalogTable.rowAtPoint(p);
    }
    String itemID = (String)itemCatalogTable.getModel().getValueAt(firstIndex,0);
    //determine where the first index going to start
    for (int i = firstIndex -1; i >= 0 ; i--) {
      String tempItemID = (String)itemCatalogTable.getModel().getValueAt(i,0);
      if (itemID.equals(tempItemID)) {
        firstIndex = i;
      }else {
        break;
      }
    }
    //determine where the last index going to end
    for (int i = firstIndex + 1; i < itemCatalogTable.getRowCount(); i++) {
      String tempItemID = (String)itemCatalogTable.getModel().getValueAt(i,0);
      if (itemID.equals(tempItemID)) {
        lastIndex = i;
      }else {
        break;
      }
    }
    itemCatalogTable.setRowSelectionInterval(firstIndex,lastIndex);
    itemCatalogTable.repaint();
    itemCatalogTable.validate();

    //if any part of an item is a sample size then user can't request for 'new part' of this item
    int[] selectedRows = itemCatalogTable.getSelectedRows();
    boolean sampleSizing = false;
    for (int j = 0; j < selectedRows.length; j++) {
      if ("Y".equalsIgnoreCase((String)itemCatalogTable.getModel().getValueAt(selectedRows[j],10))) {    //sample size
        sampleSizing = true;
        break;
      }
    }
    //enable new chem button
    String tmpSelectedFacilityID = facChoice.getSelectedFacId();
    Vector catalogAddAllowedV = (Vector) facilityCatalog.get(tmpSelectedFacilityID+"CATALOG_ADD_ALLOWED");
    if (catalogAddAllowedV != null) {
      if (catalogAddAllowedV.contains("Y") && canReqChem()) {
       npB.setEnabled(!sampleSizing);
       nspB.setEnabled(true);
      }else {
        npB.setEnabled(false);
        nspB.setEnabled(false);
      }
    }else {
      npB.setEnabled(false);
      nspB.setEnabled(false);
    }
    roeB.setEnabled(true);
  }

  void itemCatalogTable_mouseClicked(MouseEvent e) {
    int firstIndex = itemCatalogTable.getSelectedRow();
    int lastIndex = firstIndex;
    if (e.isMetaDown()) {
      Point p = e.getPoint();
      firstIndex = itemCatalogTable.rowAtPoint(p);
    }

    String itemID = (String)itemCatalogTable.getModel().getValueAt(firstIndex,0);
    //determine where the first index going to start
    for (int i = firstIndex -1; i >= 0 ; i--) {
      String tempItemID = (String)itemCatalogTable.getModel().getValueAt(i,0);
      if (itemID.equals(tempItemID)) {
        firstIndex = i;
      }else {
        break;
      }
    }
    //determine where the last index going to end
    for (int i = firstIndex + 1; i < itemCatalogTable.getRowCount(); i++) {
      String tempItemID = (String)itemCatalogTable.getModel().getValueAt(i,0);
      if (itemID.equals(tempItemID)) {
        lastIndex = i;
      }else {
        break;
      }
    }
    itemCatalogTable.setRowSelectionInterval(firstIndex,lastIndex);
    itemCatalogTable.repaint();
    itemCatalogTable.validate();

    //if any part of an item is a sample size then user can't request for 'new part' of this item
    int[] selectedRows = itemCatalogTable.getSelectedRows();
    boolean sampleSizing = false;
    for (int j = 0; j < selectedRows.length; j++) {
      if ("Y".equalsIgnoreCase((String)itemCatalogTable.getModel().getValueAt(selectedRows[j],10))) {
        sampleSizing = true;
        break;
      }
    }
    //enable new chem button
    String tmpSelectedFacilityID = facChoice.getSelectedFacId();
    Vector catalogAddAllowedV = (Vector) facilityCatalog.get(tmpSelectedFacilityID+"CATALOG_ADD_ALLOWED");
    if (catalogAddAllowedV != null) {
      if (catalogAddAllowedV.contains("Y") && canReqChem()) {
       npB.setEnabled(!sampleSizing);
       nspB.setEnabled(true);
      }else {
        npB.setEnabled(false);
        nspB.setEnabled(false);
      }
    }else {
      npB.setEnabled(false);
      nspB.setEnabled(false);
    }
    roeB.setEnabled(true);

    if (e.isMetaDown()) {
      Point p = e.getPoint();
      int selRow = itemCatalogTable.rowAtPoint(p);
      ItemCatComponentMetaPopUp cmpu = new ItemCatComponentMetaPopUp(parent,
              itemCatalogTable.getModel().getValueAt(selRow,1).toString(),        //component desc
              itemCatalogTable.getModel().getValueAt(selRow,0).toString(),"",    //item ID and I don't need facility_id
              itemCatalogTable.getModel().getValueAt(selRow,9).toString(),       //MSDS on line
              itemCatalogTable.getModel().getValueAt(selRow,11).toString().equalsIgnoreCase("y")); //Eng Eval
        panel1.add(cmpu);
        cmpu.show(itemCatalogTable,e.getX(),e.getY());
        return;
    }
  }

  void searchItemCatalog() {
    //global variables
    searchItemCatText = BothHelpObjs.makeBlankFromNull(itemText.getText());
    searchItemCatFac = (String)facChoice.getSelectedItem();
    searchItemCatWA = (String)appChoice.getSelectedItem();
    String tmpSelFa = facChoice.getSelectedFacId();
    Hashtable tmpInnerH = null;
    if (selectedSearchItemDataH.containsKey(tmpSelFa)) {
      tmpInnerH = (Hashtable)selectedSearchItemDataH.get(tmpSelFa);
    }else {
      tmpInnerH = new Hashtable(11);
    }
    tmpInnerH.put("WORK_AREA",searchItemCatWA);
    if (facInEComList.contains(facChoice.getSelectedFacId().toString())) {
      tmpInnerH.put("PREVIOUS_ORDER",new Boolean(false));
      tmpInnerH.put("EVAL_REQUESTOR",new Boolean(false));
      tmpInnerH.put("EVAL_FACILITY",new Boolean(false));
      tmpInnerH.put("EVAL_WORK_AREA",new Boolean(false));
    }else {
      tmpInnerH.put("PREVIOUS_ORDER",new Boolean(itemCatEvalCheck.isSelected()));
      tmpInnerH.put("EVAL_REQUESTOR",new Boolean(itemCatRequestorCheck.isSelected()));
      tmpInnerH.put("EVAL_FACILITY",new Boolean(itemCatFacCheck.isSelected()));
      tmpInnerH.put("EVAL_WORK_AREA",new Boolean(itemCatWACheck.isSelected()));
    }
    tmpInnerH.put("SEARCH_TEXT",(String)itemText.getText());
    selectedSearchItemDataH.put(tmpSelFa,tmpInnerH);

    //disable until user click on a row
    nspB.setEnabled(false);
    npB.setEnabled(false);
    naB.setEnabled(false);
    ngB.setEnabled(false);
    roeB.setEnabled(false);

    long sTime = new java.util.Date().getTime();
    TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",parent);
    Hashtable query = new Hashtable();
    query.put("ACTION","GET_ITEM_CATALOG_TABLE_DATA"); //String, String
    query.put("USER_ID",parent.getUserId());  //String, Integer
    query.put("FACILITY_ID",facChoice.getSelectedFacId());
    query.put("WORK_AREA_ID",appChoice.getSelectedWorkAreaID());
    query.put("SEARCH_TEXT",BothHelpObjs.makeBlankFromNull(itemText.getText()));
    //4-18-02
    //if (itemCatEvalCheck.isVisible()) {
    if (itemCatEvalCheck != null) {
      query.put("PREVIOUS_ORDER",new Boolean(itemCatEvalCheck.isSelected()));
      query.put("EVAL_REQUESTOR",new Boolean(itemCatRequestorCheck.isSelected()));
      query.put("EVAL_FACILITY",new Boolean(itemCatFacCheck.isSelected()));
      query.put("EVAL_WORK_AREA",new Boolean(itemCatWACheck.isSelected()));
    }else {
      query.put("PREVIOUS_ORDER",new Boolean(false));
    }

    Hashtable result = cgi.getResultHash(query);
    if (result == null && !parent.getMain().stopped){
      GenericDlg.showAccessDenied(parent.getMain());
      return;
    }

    Boolean b = (Boolean)result.get("RETURN_CODE");
    if (!b.booleanValue()) {
      GenericDlg gd = new GenericDlg(parent.getMain(),"Error",true);
      gd.setMsg((String)result.get("RETURN_MSG"));
      gd.setVisible(true);
      return;
    }
    //first remove catalog table from scrollpane
    panel1.remove(jScrollPane1);
    panel1.validate();

    buildItemCatalogTable((Hashtable) result.get("SEARCH_RESULT"));
    if (itemCatalogTable.getRowCount() < 1) {
      GenericDlg err = new GenericDlg(parent.getMain(),"Not Found",true);
      err.setMsg("No records found.");
      err.show();
    }

    jScrollPane1 = new JScrollPane(itemCatalogTable);
    panel1.add(jScrollPane1, BorderLayout.CENTER);

    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date(min));
    searchItemCatMsg = "Records Found: "+itemCatalogTable.getRowCount()+" -- Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
    parent.getMain().countLabel.put(new Integer(Main.SEARCHPT),searchItemCatMsg);
    parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.SEARCHPT)));

    searchB.setEnabled(false);
    searchItemCatButtonActive = false;
    this.validate();
    panel1.validate();
  }

  public Hashtable getCatTableStyle() {
    return this.catStyle;
  }

  void buildItemCatalogTable(Hashtable itemCatSearchResult) {
    AttributiveCellTableModel model;
    if (itemCatSearchResult == null) {
      model = new AttributiveCellTableModel(itemCatCols,0);
    }else {
      Integer rowSize = (Integer)itemCatSearchResult.get("ROW_COUNT");
      model = new AttributiveCellTableModel(itemCatCols,rowSize.intValue());
      CellSpan cellCombineAtt = (CellSpan)model.getCellAttribute();
      ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
      //remove row - perpare for coloring of alternate lines
      for (int k = rowSize.intValue() -1; k >= 0; k--){
        model.removeRow(k);
      }
      //fill table
      String lastItem = "";
      boolean colorRow = true;
      int rowNum = 0;
      Vector itemCatDataV = (Vector)itemCatSearchResult.get("ITEM_LIST");
      for (int i = 0; i < itemCatDataV.size(); i++) {
        String key = (String)itemCatDataV.elementAt(i);
        Vector v = (Vector)itemCatSearchResult.get(key);
        int[] rows = new int[v.size()];
        for (int j = 0; j < v.size(); j++) {
          String[] oa = (String[])v.elementAt(j);
          oa[0] = key;
          model.addRow(oa);
          rows[j] = rowNum;
          rowNum++;
        }

        //combining item
        if (rows.length > 1) {
          int[] itemCols = new int[]{0};
          cellCombineAtt.combine(rows,itemCols);
        }
        //color alternate row
        if (colorRow) {
          for (int k = 0; k < model.getColumnCount(); k++) {
            int[] cols = new int[]{k};
            cellAtt.setBackground(new Color(224,226,250),rows,cols);
          }
          colorRow = false;
        }else {
          colorRow = true;
        }
      }
    }
    model.setColumnPrefWidth(itemCatColWidths);
    model.setColumnType(itemCatColTypes);
    itemCatalogTable = new MultiSpanCellTable(model);
    //itemCatalogTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    //itemCatalogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    itemCatalogTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    //NEED TO FIND A WAY TO DISABLE MOUSE DRAG IN A JTABLE
    //********

    if (itemCatalogTableMouseAdapter == null) {
      itemCatalogTableMouseAdapter = new SearchPT_itemCatalogTable_mouseAdapter(this);
    }
    itemCatalogTable.addMouseListener(itemCatalogTableMouseAdapter);
    itemCatalogTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] itemCatRenderers = new AttributiveCellRendererLine[1];
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    itemCatRenderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = itemCatalogTable.getColumnModel();
    int i = 0;
    for (i = 0; i < itemCatalogTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(itemCatRenderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    itemCatalogTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),itemCatRenderers[0].getBorder());
    Enumeration enum1 = itemCatalogTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    itemCatalogTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<itemCatalogTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      itemCatalogTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      itemCatalogTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        itemCatalogTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        itemCatalogTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
  }   //end of method

  void displayPartSearchComponents() {
    //setting facility and work area
    /*if (!BothHelpObjs.isBlankString(searchPartCatFac)) {
      facChoice.setSelectedItem(searchPartCatFac);
      appChoice.setSelectedItem(searchPartCatWA);
    }
    if (!BothHelpObjs.isBlankString(searchFacility)) {
      facChoice.setSelectedItem(searchFacility);
      appChoice.setSelectedItem(searchWorkArea);
    }*/

    //setting search text
    if (!BothHelpObjs.isBlankString(searchPartCatText)) {
      itemText.setText(searchPartCatText);
    }
    //search button
    searchB.setEnabled(searchPartCatButtonActive);
    searchB.setEnabled(true);
    itemText.setEnabled(true);

    if (facInEComList.contains(facChoice.getSelectedFacId().toString())) {
      //user had something in shopping cart
      if (basketTableNew.getRowCount() > 0) {
        facChoice.setEnabled(false);
        appChoice.setEnabled(true);
        facilityCheck.setEnabled(true);
        if (!appChoice.getSelectedItem().toString().startsWith("Select") && facilityCheck.isSelected()) {
          wAreaCheck.setEnabled(true);
        }
        oFacilityCheck.setEnabled(true);
        if (oFacilityCheck.isSelected()) {
          activeCheck.setEnabled(true);
          appChoice.setEnabled(false);
        }
      }else {
        facilityCheck.setEnabled(true);
        if (!appChoice.getSelectedItem().toString().startsWith("Select") && facilityCheck.isSelected()) {
          wAreaCheck.setEnabled(true);
        }
        oFacilityCheck.setEnabled(true);
        //if 'All Catalog' is selected than leave facility and work area comboboxes disable
        if (oFacilityCheck.isSelected()) {
          activeCheck.setEnabled(true);
          facChoice.setEnabled(false);
          appChoice.setEnabled(false);
        }else {
          facChoice.setEnabled(true);
          appChoice.setEnabled(true);
        }
      }
    }else {
      //if 'All Catalog' is selected than leave facility and work area comboboxes disable
      if (oFacilityCheck.isSelected()) {
        facChoice.setEnabled(false);
        appChoice.setEnabled(false);
      }else {
        facChoice.setEnabled(true);
        appChoice.setEnabled(true);
      }

      //first remove last selected catalog search components
      if (lastSelectedCatalog.startsWith("Item")) {
        groupBox1.remove(itemCatEvalCheck);
        groupBox1.remove(itemCatRequestorCheck);
        groupBox1.remove(itemCatFacCheck);
        groupBox1.remove(itemCatWACheck);
      }else if (lastSelectedCatalog.startsWith("POS")) {
        groupBox1.remove(customerL);
        groupBox1.remove(customerT);
        groupBox1.remove(customerB);
        groupBox1.remove(clearCustomerB);
        groupBox1.remove(posApprovedForWACheck);
        if ("y".equalsIgnoreCase(approverRequired)) {
          groupBox1.remove(customerLimitT);
          groupBox1.remove(customerLimitL);
        }
        approverRequired = "n";
        if (customerT.getText().length() > 0) {
          groupBox1.remove(posFacC);
          groupBox1.remove(posWAC);
          groupBox1.add(facChoice, new GridBagConstraints2(1, 2, 2, 1, 1.0, 0.0
               ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
          groupBox1.add(appChoice, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
              ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
        }
        customerT.setText("");
        customerID = new Integer(0);
      }
      //then add part catalog search components
      groupBox1.add(facilityCheck, new GridBagConstraints2(0, 5, 2, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 0), 0, 0));
      groupBox1.add(wAreaCheck, new GridBagConstraints2(0, 5, 3, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 156, 2, 0), 0, 0));
      groupBox1.add(oFacilityCheck, new GridBagConstraints2(0, 6, 2, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 0), 0, 0));
      groupBox1.add(activeCheck, new GridBagConstraints2(0, 6, 2, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 220, 2, 0), 0, 0));
      groupBox1.validate();
    }
    parent.getMain().countLabel.put(new Integer(Main.SEARCHPT),searchPartCatMsg);
    parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.SEARCHPT)));
  } //end of method

  void displayItemSearchComponents() {
    //setting facility and work area
    if (!BothHelpObjs.isBlankString(searchItemCatFac)) {
      facChoice.setSelectedItem(searchItemCatFac);
      appChoice.setSelectedItem(searchItemCatWA);
    }
    facChoice.setEnabled(true);
    appChoice.setEnabled(true);
    //setting search text
    if (!BothHelpObjs.isBlankString(searchItemCatText)) {
      itemText.setText(searchItemCatText);
    }
    //setting search button
    searchB.setEnabled(searchItemCatButtonActive);
    searchB.setEnabled(true);
    itemText.setEnabled(true);

    if (facInEComList.contains(facChoice.getSelectedFacId().toString())) {
      facilityCheck.setEnabled(false);
      wAreaCheck.setEnabled(false);
      oFacilityCheck.setEnabled(false);
      activeCheck.setEnabled(false);
    }else {
      if (lastSelectedCatalog.startsWith("Part")) {
        //first remove part catalog search components
        groupBox1.remove(facilityCheck);
        groupBox1.remove(wAreaCheck);
        groupBox1.remove(oFacilityCheck);
        groupBox1.remove(activeCheck);
      }else if (lastSelectedCatalog.startsWith("POS")) {
        groupBox1.remove(customerL);
        groupBox1.remove(customerT);
        groupBox1.remove(customerB);
        groupBox1.remove(clearCustomerB);
        groupBox1.remove(posApprovedForWACheck);
        if ("y".equalsIgnoreCase(approverRequired  )) {
          groupBox1.remove(customerLimitT);
          groupBox1.remove(customerLimitL);
        }
        approverRequired = "n";
        if (customerT.getText().length() > 0) {
          groupBox1.remove(posFacC);
          groupBox1.remove(posWAC);
          groupBox1.add(facChoice, new GridBagConstraints2(1, 2, 2, 1, 1.0, 0.0
               ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
          groupBox1.add(appChoice, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
              ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
        }
        customerT.setText("");
        customerID = new Integer(0);
      }
      //then add item catalog search components
      if (itemCatEvalCheck == null) {
        itemCatEvalCheck = new JCheckBox("Only Previous Eval Orders for:");
        itemCatEvalCheck.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            itemCatEvalCheck_actionPerformed(e);
          }
        });
        itemCatEvalCheck.setFont(new Font("Dialog", 0, 10));
        itemCatEvalCheck.setSelected(false);
        itemCatRequestorCheck = new JCheckBox(userName.getText());
        itemCatRequestorCheck.setFont(new Font("Dialog", 0, 10));
        itemCatRequestorCheck.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            itemCatRequestorCheck_actionPerformed(e);
          }
        });
        itemCatFacCheck = new JCheckBox(facChoice.getSelectedItem().toString());
        itemCatFacCheck.setFont(new Font("Dialog", 0, 10));
        itemCatFacCheck.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            itemCatFacCheck_actionPerformed(e);
          }
        });
        itemCatWACheck = new JCheckBox(appChoice.getSelectedItem().toString());
        itemCatWACheck.setFont(new Font("Dialog", 0, 10));
        itemCatWACheck.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            itemCatWACheck_actionPerformed(e);
          }
        });
      }else {
        itemCatFacCheck.setText(facChoice.getSelectedItem().toString());
        itemCatWACheck.setText(appChoice.getSelectedItem().toString());
      }
      itemCatWACheck.setMaximumSize(new Dimension(160,21));
      itemCatWACheck.setMinimumSize(new Dimension(160,21));
      itemCatWACheck.setPreferredSize(new Dimension(160,21));
      groupBox1.add(itemCatEvalCheck, new GridBagConstraints2(0, 5, 2, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 0), 0, 0));
      groupBox1.add(itemCatRequestorCheck, new GridBagConstraints2(0, 5, 3, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 175, 2, 0), 0, 0));
      groupBox1.add(itemCatFacCheck, new GridBagConstraints2(0, 6, 2, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 25, 2, 0), 0, 0));
      groupBox1.add(itemCatWACheck, new GridBagConstraints2(0, 6, 2, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 160, 2, 0), 0, 0));
      groupBox1.validate();
    }
    parent.getMain().countLabel.put(new Integer(Main.SEARCHPT),searchItemCatMsg);
    parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.SEARCHPT)));
  } //end of method

  void displayPOSSearchComponents() {
    //first remove last selected catalog search components
    if (lastSelectedCatalog.startsWith("Part")) {
      groupBox1.remove(facilityCheck);
      groupBox1.remove(wAreaCheck);
      groupBox1.remove(oFacilityCheck);
      groupBox1.remove(activeCheck);
    }else if (lastSelectedCatalog.startsWith("Item")) {
      groupBox1.remove(itemCatEvalCheck);
      groupBox1.remove(itemCatRequestorCheck);
      groupBox1.remove(itemCatFacCheck);
      groupBox1.remove(itemCatWACheck);
    }

    searchB.setEnabled(true);
    itemText.setEnabled(true);
    String selectedPOS = catalogC.getSelectedItem().toString();
    selectedPOS = selectedPOS.substring(selectedPOS.indexOf(": ")+2);
    if ("Y".equalsIgnoreCase((String)posFacilityH.get(facChoice.getSelectedFacId()+selectedPOS))) {
      //if customer is fill in then remove normal facility and work area drop down
      //and add POS facility and work area drop down
      if (customerT.getText().length() > 0) {
        addItem.setEnabled(true);
        groupBox1.remove(facChoice);
        groupBox1.remove(appChoice);
        posApprovedForWACheck.setSelected(true);
        groupBox1.add(posFacC, new GridBagConstraints2(1, 2, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
        groupBox1.add(posWAC, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
      }else {
        addItem.setEnabled(false);
        groupBox1.remove(posFacC);
        groupBox1.remove(posWAC);
        posApprovedForWACheck.setEnabled(false);
        groupBox1.add(facChoice, new GridBagConstraints2(1, 2, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
        groupBox1.add(appChoice, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
      }
      groupBox1.add(customerL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
      groupBox1.add(customerT, new GridBagConstraints2(1, 1, 2, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
      groupBox1.add(customerB, new GridBagConstraints2(0, 1, 2, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 217, 5, 0), 0, 0));
      clearCustomerB.setEnabled(customerID.intValue() != 0);
      groupBox1.add(clearCustomerB, new GridBagConstraints2(0, 1, 2, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 255, 5, 0), 0, 0));
      posApprovedForWACheck.setText(" Work Area Approved Only");
      posApprovedForWACheck.setFont(new Font("Dialog", 0, 10));
      groupBox1.add(posApprovedForWACheck, new GridBagConstraints2(0, 6, 2, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 20, 2, 0), 0, 0));
      if ("y".equalsIgnoreCase(approverRequired)) {
        customerLimitL.setText("Balance: ");
        customerLimitT.setText((String)posChargeInfoH.get("CUSTOMER_COST_LIMIT"));
        groupBox1.add(customerLimitL, new GridBagConstraints2(1, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 120, 2, 0), 0, 0));
        groupBox1.add(customerLimitT, new GridBagConstraints2(1, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 180, 2, 0), 0, 0));
      }
      groupBox1.validate();
    }else {
      addItem.setEnabled(false);
      customerT.setText("");
      customerID = new Integer(0);
      if (customerB.isVisible()) {
        groupBox1.remove(customerL);
        groupBox1.remove(customerT);
        groupBox1.remove(customerB);
        groupBox1.remove(clearCustomerB);
        groupBox1.remove(posApprovedForWACheck);
      }
      groupBox1.add(customerT, new GridBagConstraints2(1, 6, 2, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 6, 10, 0), 0, 0));
      groupBox1.validate();
    }
    parent.getMain().countLabel.put(new Integer(Main.SEARCHPT),searchPOSCatMsg);
    parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.SEARCHPT)));
  } //end of method

  void displaySearchComponents() {
    String selectedCatalog = catalogC.getSelectedItem().toString();
    if (selectedCatalog.startsWith("Part")) {
      displayPartSearchComponents();
    }else if (selectedCatalog.startsWith("Item")) {
      displayItemSearchComponents();
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      displayPOSSearchComponents();
    } //end of if POS
    ClientHelpObjs.makeDefaultColors(groupBox3);
  }   //end of method

  void clearCustomerB_actionPerformed(ActionEvent e) {
    if (customerSearch) {
      return;
    }
    customerT.setText("");
    customerID = new Integer(0);
    setPOSCatalogToDropDown(true);
    catalogC.setSelectedItem(lastSelectedCatalog);
    displaySearchComponents();
    clearPOSCatalogTable();
    panel1.validate();
    this.validate();
  }

  void clearPOSCatalogTable() {
    AttributiveCellTableModel tmpModel = (AttributiveCellTableModel)catPOSTable.getModel();
    while (catPOSTable.getRowCount() > 0) {
      tmpModel.removeRow(0);
    }
  }

  void customerB_actionPerformed(ActionEvent e) {
    if (customerSearch) {
      return;
    }
    CustomerLookupThread tw = new CustomerLookupThread(this);
    tw.start();
  }

  void customerLookup() {
    customerSearch = true;
    SearchPersonnel sp = new SearchPersonnel(parent.getMain(),"Personnel Lookup",true);
    sp.setGrandParent(parent);
    sp.setPersonType("PERSONNEL");
    CenterComponent.centerCompOnScreen(sp);
    sp.setVisible(true);
    if (sp.getOkFlag()) {
      if (sp.getValueId() != null) {
        customerT.setText(sp.getValueLast() + ", " + sp.getValueFirst());
        customerID = sp.getValueId();
        getCustomerInfo();
        if ("POS".equalsIgnoreCase(catalogInShoppingCart)) {
          if (shoppingCartForUserID.intValue() != customerID.intValue()) {
            clearShoppingCart();
          }
        }
      }else {
        customerT.setText("");
        customerID = new Integer(0);
        setPOSCatalogToDropDown(true);
        catalogC.setSelectedItem(lastSelectedCatalog);
        clearShoppingCart();
      }
    }
    customerSearch = false;
    sp.dispose();
    displaySearchComponents();
  }

  void getCustomerInfo() {
    CustomerFacilityInfoThread tw = new CustomerFacilityInfoThread(this);
    tw.start();
  }

  void getCustomerFacilityInfo() {
    TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",parent);
    Hashtable query = new Hashtable();
    query.put("ACTION","GET_POS_CUSTOMER_DATA"); //String, String
    query.put("USER_ID",(new Integer(parent.getUserId().intValue())).toString()); //String, String
    query.put("CUSTOMER_ID",customerID.toString());
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(parent.getMain());
      return;
    }
    //show error message if server ran into problem while loading data
    if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
      GenericDlg.showMessage((String)result.get("RETURN_MSG"));
      customerID = new Integer(0);
      customerT.setText("");
      clearCustomerB.setEnabled(false);
      return;
    }
    Hashtable posDataH = (Hashtable) result.get("POS_DATA");
    posCustFacWaInfo = (Hashtable) posDataH.get("CUSTOMER_FACILITY_INFO");
    //check to make sure that there is at least one facility
    Vector facNameV = (Vector) posCustFacWaInfo.get("FACILITY_NAME");
    if (facNameV.size() == 0) {
      GenericDlg.showMessage("Customer is not approved\nto order to any of your Point of Sale.");
      customerID = new Integer(0);
      customerT.setText("");
      clearCustomerB.setEnabled(false);
      return;
    }
    posFacC.removeAllItems();
    posFacC = ClientHelpObjs.loadChoiceFromVector(posFacC,facNameV);
    POSCItemAdapter = new SearchPT_POSC_itemAdapter(this);
    posFacC.addItemListener(POSCItemAdapter);
    loadWorkArea();
    //setting color for customer facilities and work areas
    posFacC.setForeground(facChoice.getForeground());
    posWAC.setForeground(facChoice.getForeground());
    posFacC.setFont(facChoice.getFont());
    posWAC.setFont(facChoice.getFont());
    posWAC.addItemListener(new java.awt.event.ItemListener(){
      public void itemStateChanged(ItemEvent evt) {
        POSWorkAreaItemChange(evt);
      }
    });

    //clerk point of sale
    Vector v = (Vector) posDataH.get("CLERK_POS_INFO");
    setClerkCatalogDropDown(v);
  } //end of method

  void POSWorkAreaItemChange(ItemEvent evt) {
    if (evt.getStateChange() == evt.SELECTED) {
      posApprovedForWACheck.setEnabled(!posWAC.getSelectedItem().toString().startsWith("Select"));
    }
  }

  void setClerkCatalogDropDown(Vector clerkPOSCatalogV) {
    loadingCatalogC = true;
    //otherwise, change catalog drop down depending on selected facility
    //first remove everything in list
    //next remove everything from catalog list
    for (int i = catalogC.getItemCount() -1; i >= 0; i--) {
      catalogC.removeItemAt(i);
    }
    //next add POS for facility
    for (int j = 0; j < clerkPOSCatalogV.size(); j++) {
      catalogC.insertItemAt(pointOfSaleName+(String)clerkPOSCatalogV.elementAt(j), catalogC.getItemCount());
    }
    catalogC.setSelectedItem(lastSelectedCatalog);
    loadingCatalogC = false;
  } //end of method

  String getCustomerFacilityID(String facDesc) {
    Vector facDescV = (Vector) posCustFacWaInfo.get("FACILITY_NAME");
    Vector facIDV = (Vector) posCustFacWaInfo.get("FACILITY");
    int index = facDescV.indexOf(facDesc);
    if (index < 0 ) {
      index = 0;
    }
    return (facIDV.elementAt(index).toString());
  }

  String getCustomerFacilityDesc(String facID) {
    Vector facDescV = (Vector) posCustFacWaInfo.get("FACILITY_NAME");
    Vector facIDV = (Vector) posCustFacWaInfo.get("FACILITY");
    int index = facIDV.indexOf(facID);
    if (index < 0 ) {
      index = 0;
    }
    return (facDescV.elementAt(index).toString());
  }

  String getCustomerWorkAreaID() {
    String fac = getCustomerFacilityID(posFacC.getSelectedItem().toString());
    Hashtable app = (Hashtable) posCustFacWaInfo.get(fac);
    Vector appId = (Vector)app.get("APPLICATION");
    if (posWAC.isVisible()) {
      return ( (String) appId.elementAt(posWAC.getSelectedIndex()));
    }else {
      return ( (String) appId.elementAt(posLimitedWAC.getSelectedIndex()));
    }
  }

  void loadWorkArea() {
    posWAC.removeAllItems();
    String fac = getCustomerFacilityID(posFacC.getSelectedItem().toString());
    Hashtable app = (Hashtable) posCustFacWaInfo.get(fac);
    posWAC = ClientHelpObjs.loadChoiceFromVector(posWAC,(Vector)app.get("APPLICATION_DESC"));
    posWAC.setSelectedIndex(0);
  }


  void itemCatWACheck_actionPerformed(ActionEvent e) {
    this.searchB.setEnabled(true);
  }
  void itemCatFacCheck_actionPerformed(ActionEvent e) {
    this.searchB.setEnabled(true);
  }
  void itemCatRequestorCheck_actionPerformed(ActionEvent e) {
    this.searchB.setEnabled(true);
  }
  void itemCatEvalCheck_actionPerformed(ActionEvent e) {
    this.searchB.setEnabled(true);
    if (itemCatEvalCheck.isSelected()) {
      itemCatRequestorCheck.setEnabled(true);
      itemCatFacCheck.setEnabled(true);
      itemCatWACheck.setEnabled(true);
    }else {
      itemCatRequestorCheck.setEnabled(false);
      itemCatRequestorCheck.setSelected(false);
      itemCatFacCheck.setEnabled(false);
      itemCatFacCheck.setSelected(false);
      itemCatWACheck.setEnabled(false);
      itemCatWACheck.setSelected(false);
    }
  }   //end of method

  void catalogC_actionPerformed() {
    if (loadingCatalogC) return;
    String selectedCatalog = catalogC.getSelectedItem().toString();
    if (lastSelectedCatalog.equals(selectedCatalog)) return;           //if user doesn't change catalog then do nothing
    //disable new chem buttons until user click on a row
    if (canReqChem()){
      //if selected facility can create catalog add to catalog
      String tmpSelectedFacilityID = facChoice.getSelectedFacId();
      Vector catalogAddAllowedV = (Vector) facilityCatalog.get(tmpSelectedFacilityID+"CATALOG_ADD_ALLOWED");
      if (catalogAddAllowedV != null) {
        if (catalogAddAllowedV.contains("Y")) {
          nmB.setEnabled(true);
          if (selectedCatalog.startsWith("Part")) {
            if(catTableNew.getSelectedRow()>-1 && catTableNew.getSelectedRow()<catTableNew.getRowCount()
              && viewC.getSelectedItem().toString().equalsIgnoreCase("Detail")){
              nspB.setEnabled(true);
              npB.setEnabled(true);
              //user can request for work area approval if the selected catalog is in the selected facility
              Vector catalogPerFacility = (Vector)facilityCatalog.get(facChoice.getSelectedFacId());
              if (catalogPerFacility.size() > 0) {
                naB.setEnabled(catalogPerFacility.contains((String)catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),this.CATALOG_COL)));
              }else {
                naB.setEnabled(false);
              }
            }else {
              nspB.setEnabled(false);
              npB.setEnabled(false);
              naB.setEnabled(false);
            }
            //just in case this button is not avaliable
            if (roeB != null) {
              roeB.setEnabled(false);
            }
          }else if (selectedCatalog.startsWith("Item")) {
            if (itemCatalogTable == null) {
              nspB.setEnabled(false);
              npB.setEnabled(false);
              naB.setEnabled(false);
            }else {
              if(itemCatalogTable.getSelectedRow()>-1 && itemCatalogTable.getSelectedRow()<itemCatalogTable.getRowCount()){
                //just in case this button is not avaliable
                if (roeB != null) {
                  roeB.setEnabled(true);
                }
                nspB.setEnabled(true);
                //if any part of an item is a sample size then user can't request for 'new part' of this item
                int[] selectedRows = itemCatalogTable.getSelectedRows();
                boolean sampleSizing = false;
                for (int j = 0; j < selectedRows.length; j++) {
                  if ("Y".equalsIgnoreCase((String)itemCatalogTable.getModel().getValueAt(selectedRows[j],10))) {
                    sampleSizing = true;
                    break;
                  }
                }
                npB.setEnabled(!sampleSizing);
                naB.setEnabled(false);
              }else {
                nspB.setEnabled(false);
                npB.setEnabled(false);
                naB.setEnabled(false);
              }
            }
          }else if (selectedCatalog.startsWith("POS")) {
            if(catPOSTable.getSelectedRow()>-1 && catPOSTable.getSelectedRow()<catPOSTable.getRowCount()) {
              nspB.setEnabled(true);
              npB.setEnabled(true);
              //user can request for work area approval if the selected catalog is in the selected facility
              Vector catalogPerFacility = (Vector)facilityCatalog.get(facChoice.getSelectedFacId());
              if (catalogPerFacility.size() > 0) {
                naB.setEnabled(catalogPerFacility.contains((String)catPOSTable.getModel().getValueAt(catPOSTable.getSelectedRow(),this.CATALOG_COL)));
              }else {
                naB.setEnabled(false);
              }
            }else {
              nspB.setEnabled(false);
              npB.setEnabled(false);
              naB.setEnabled(false);
            }
            //just in case this button is not avaliable
            if (roeB != null) {
              roeB.setEnabled(false);
            }
          } //end of POS catalog
        }else { //end of catalogAddAllowedV contains Y
          //the only option for catalog_add_allowed = Y for all catalog(s) for selected facility is New Work Area Approval
          nmB.setEnabled(false);
          nspB.setEnabled(false);
          npB.setEnabled(false);
          if (selectedCatalog.startsWith("Part")) {
            if (catTableNew.getSelectedRow() > -1 && catTableNew.getSelectedRow() < catTableNew.getRowCount()
                && viewC.getSelectedItem().toString().equalsIgnoreCase("Detail")) {
              //user can request for work area approval if the selected catalog is in the selected facility
              Vector catalogPerFacility = (Vector) facilityCatalog.get(facChoice.getSelectedFacId());
              if (catalogPerFacility.size() > 0) {
                naB.setEnabled(catalogPerFacility.contains( (String) catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(), this.CATALOG_COL)));
              } else {
                naB.setEnabled(false);
              }
            }else {
              naB.setEnabled(false);
            }
          }else if (selectedCatalog.startsWith("POS")) {
            if(catPOSTable.getSelectedRow()>-1 && catPOSTable.getSelectedRow()<catPOSTable.getRowCount()) {
              //user can request for work area approval if the selected catalog is in the selected facility
              Vector catalogPerFacility = (Vector)facilityCatalog.get(facChoice.getSelectedFacId());
              if (catalogPerFacility.size() > 0) {
                naB.setEnabled(catalogPerFacility.contains((String)catPOSTable.getModel().getValueAt(catPOSTable.getSelectedRow(),this.CATALOG_COL)));
              }else {
                naB.setEnabled(false);
              }
            }else {
              naB.setEnabled(false);
            }
          } //end of POS catalog
        } //end of catalogAddAllowedV does not contains Y
      }else { //end of catalogAddAllowedV not is null
        nmB.setEnabled(false);
        nspB.setEnabled(false);
        npB.setEnabled(false);
        naB.setEnabled(false);
      } //end of catalogAddAllowedV is null
    }else {      //else users do not have permission to create new catalog add
      nmB.setEnabled(false);
      nspB.setEnabled(false);
      npB.setEnabled(false);
      naB.setEnabled(false);
    }

    //first remove catalog table from scrollpane
    panel1.remove(jScrollPane1);
    panel1.validate();

    if (selectedCatalog.startsWith("Part")) {
      //clearShoppingCart();
      groupBox3.setEnabledAt(groupBox3.indexOfComponent(basketPane),true);
      //part catalog table
      if (catTableNew.getRowCount() == 0) {
        buildEmptyPartCatalogTable();
      }
      jScrollPane1 = new JScrollPane(catTableNew);
      panel1.add(jScrollPane1, BorderLayout.CENTER);
      displaySearchComponents();
      //add new catalog add tab
      displayNewChemTab();
    }else if (selectedCatalog.startsWith("Item")) {
      groupBox3.setEnabledAt(groupBox3.indexOfComponent(basketPane),false);
      //item catalog table
      if (itemCatalogTable == null) {
        buildItemCatalogTable(null);
      }
      jScrollPane1 = new JScrollPane(itemCatalogTable);
      panel1.add(jScrollPane1, BorderLayout.CENTER);
      displaySearchComponents();
      //add new catalog add tab
      displayNewChemTab();
      groupBox3.setSelectedComponent(nchemPane);
    }else if (selectedCatalog.startsWith("POS")) {
      //DON'T CLEAR THINGS WHEN CUSTOMER IS ENTERED
      if (customerT.getText().length() > 0) {
        lastSelectedCatalog = selectedCatalog;
        return;
      }
      //clearShoppingCart();
      groupBox3.setEnabledAt(groupBox3.indexOfComponent(basketPane),true);
      //uses the same table as part catalog table
      if (catPOSTable.getRowCount() == 0) {
        buildEmptyPOSCatalogTable();
      }
      jScrollPane1 = new JScrollPane(catPOSTable);
      panel1.add(jScrollPane1, BorderLayout.CENTER);
      //remove tabs that are not needed
      int tabCount = groupBox3.getComponentCount();
      for (int i = tabCount - 1; i >= 1; i--) {
        groupBox3.removeTabAt(i);
      }
      displaySearchComponents();
    }
    lastSelectedCatalog = selectedCatalog;
    setShoppingCartFacLogic();
    setSelectedFacilityInfo();
    setShoppingCartButtonLogic();
    groupBox3.validate();
    panel1.validate();
    this.validate();
  }  //end of method

  //this method will determine whether or not to show delete or check out button
  void setShoppingCartButtonLogic() {
    String selectedCatalog = catalogC.getSelectedItem().toString();
    if (selectedCatalog.startsWith("Part")) {
      if (basketTableNew.getRowCount() > 0 && "Part".equalsIgnoreCase(catalogInShoppingCart)) {
        basketRemove.setEnabled(true);
        addPR.setEnabled(true);
      }else {
        basketRemove.setEnabled(false);
        addPR.setEnabled(false);
      }
    }else if (selectedCatalog.startsWith("POS")) {
      if (basketTableNew.getRowCount() > 0 && "POS".equalsIgnoreCase(catalogInShoppingCart)) {
        basketRemove.setEnabled(true);
        addPR.setEnabled(true);
      }else {
        if (basketTableNew.getRowCount() > 0) {
          basketRemove.setEnabled(true);
        }else {
          basketRemove.setEnabled(false);
        }
        addPR.setEnabled(false);
      }
    }
  } //end of method

  //this method will determine if users should allow to change facility
  void setShoppingCartFacLogic() {
    String selectedCatalog = catalogC.getSelectedItem().toString();
    if (selectedCatalog.startsWith("Part")) {
      if (basketTableNew.getRowCount() > 0 && "Part".equalsIgnoreCase(catalogInShoppingCart)) {
        facChoice.removeItemListener(partFacChoiceItemAdapter);
        facChoice.setSelectedFacId(searchFacility);
        facChoice.setEnabled(false);
        setPOSCatalogToDropDown(false);
        facChoice.addItemListener(partFacChoiceItemAdapter);
      }else {
        facChoice.setEnabled(true);
      }
    }else if (selectedCatalog.startsWith("Item")) {
      facChoice.setEnabled(true);
    }else if (selectedCatalog.startsWith("POS")) {
      if (basketTableNew.getRowCount() > 0 && "POS".equalsIgnoreCase(catalogInShoppingCart)) {
        if (customerT.getText().length() > 0) {
          posFacC.removeItemListener(POSCItemAdapter);
          posFacC.setSelectedItem(getCustomerFacilityDesc(searchPOSFacility));
          posFacC.setEnabled(false);
          posFacC.addItemListener(POSCItemAdapter);
        }else {
          facChoice.setEnabled(true);
        }
      }else {
        facChoice.setEnabled(true);
      }
    }
  } //end of method

  //this method will remove all of the row from shopping cart
  void clearShoppingCart() {
    if (basketTableNew.isEditing()) {
      basketTableNew.getCellEditor().stopCellEditing();
    }
    AttributiveCellTableModel model = (AttributiveCellTableModel)basketTableNew.getModel();
    for (int i = 0; i < basketTableNew.getRowCount(); i++) {
      model.removeRow(0);
    }
    // allow facility choice again
    if (basketTableNew.getModel().getRowCount() <= 0){
       addPR.setEnabled(false);
       basketRemove.setEnabled(false);
       catalogInShoppingCart = "";
       shoppingCartForUserID = new Integer(0);
    }
    // allow user to select facility and work area
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      if (facilityCheck.isSelected()) {
        facChoice.setEnabled(true);
        appChoice.setEnabled(true);
      }else {
        facChoice.setEnabled(false);
        appChoice.setEnabled(false);
      }
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      facChoice.setEnabled(true);
      appChoice.setEnabled(true);
      if ("y".equalsIgnoreCase(approverRequired)) {
        groupBox1.remove(customerLimitT);
        groupBox1.remove(customerLimitL);
      }
    }
  } //end of method

  void buildEmptyPartCatalogTable() {
    AttributiveCellTableModel model = new AttributiveCellTableModel(catColsNew,0);
    model.setColumnPrefWidth(catColWidths);
    model.setColumnType(catColTypes);
    catTableNew = new MultiSpanCellTable(model);
    catTableNew.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    catTableNew.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    catTableNew.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
    catTableNew.getTableHeader().setResizingAllowed(true);
    catTableNew.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRenderer[] catRenderers = new AttributiveCellRenderer[1];
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    catRenderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = catTableNew.getColumnModel();
    int i = 0;
    for (i = 0; i < catTableNew.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(catRenderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    catTableNew.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),catRenderers[0].getBorder());
    Enumeration enum1 = catTableNew.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    catTableNew.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<catTableNew.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      catTableNew.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      catTableNew.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        catTableNew.getColumn(model.getColumnName(i)).setMinWidth(width);
        catTableNew.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
  } //end of method

  void buildEmptyPOSCatalogTable() {
    AttributiveCellTableModel model = new AttributiveCellTableModel(catColsNew,0);
    model.setColumnPrefWidth(catColWidths);
    model.setColumnType(catColTypes);
    catPOSTable = new MultiSpanCellTable(model);
    catPOSTable.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    catPOSTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    catPOSTable.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
    catPOSTable.getTableHeader().setResizingAllowed(true);
    catPOSTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRenderer[] catRenderers = new AttributiveCellRenderer[1];
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    catRenderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = catPOSTable.getColumnModel();
    int i = 0;
    for (i = 0; i < catPOSTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(catRenderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    catPOSTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),catRenderers[0].getBorder());
    Enumeration enum1 = catPOSTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    catPOSTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<catPOSTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      catPOSTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      catPOSTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        catPOSTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        catPOSTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
  } //end of method

  boolean showEvalWarning() {
    boolean result = false;
    ConfirmNewDlg cdlg = new ConfirmNewDlg(this.parent.getMain(),"Confirmation", true);
    cdlg.setMsg("Engineering Evaluation material not for production use.");
    cdlg.setVisible(true);
    //if user mistakingly hit submit without enter use approval data then allow them to enter data
    if (!cdlg.yesFlag) {
      result = false;
    }else {
      result = true;
    }
    if (cdlg != null) {
      cdlg = null;
    }
    return result;
  }  //end of methd

  void evalB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    GenericDlg err;
    if (appChoice.getSelectedItem().equals("All") ||
        appChoice.getSelectedItem().toString().equalsIgnoreCase("All - Unrestricted") ||   //trong
        appChoice.getSelectedItem().equals(SELECT_WORK_AREA)){
        String no = new String("You need to choose a valid work area.");
        err = new GenericDlg(parent.getMain(),"Invalid Work Area",true);
        err.setMsg(no);
        err.setVisible(true);
        return;
    }

    //show warning message
    if (!showEvalWarning()) {
      return;
    }
    showAccSysDlg();
    eval = "Evaluation";
    actionEval = "newEval";
    //goNewMaterial();
    SearchPTNewChemThread st = new SearchPTNewChemThread(this,"NewMaterial");
    st.start();
  }

  void roeB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    GenericDlg err;
    if (appChoice.getSelectedItem().equals("All") ||
        appChoice.getSelectedItem().toString().equalsIgnoreCase("All - Unrestricted") ||   //trong
        appChoice.getSelectedItem().equals(SELECT_WORK_AREA)){
        String no = new String("You need to choose a valid work area.");
        err = new GenericDlg(parent.getMain(),"Invalid Work Area",true);
        err.setMsg(no);
        err.setVisible(true);
        return;
    }
    //show warning message
    if (!showEvalWarning()) {
      return;
    }
    showAccSysDlg();
    eval = "Evaluation";
    actionEval = "orderEvalFromItem";
    SearchPTNewChemThread st = new SearchPTNewChemThread(this,"NewPart");
    st.start();
  }

  void displayEngEvalTab(boolean display) {
    int indexOfEngEvalTab = groupBox3.indexOfComponent(engEvalPane);
    if (display) {
      //if eng eval tab not display for last facility then display tab
      if (indexOfEngEvalTab < 0) {
        engEvalPane = new JPanel();
        engEvalPane.setLayout(new GridBagLayout());
        JTextArea engEvalT = new JTextArea();
        engEvalT.setDoubleBuffered(true);
        engEvalT.setLineWrap(true);
        engEvalT.setWrapStyleWord(true);
        engEvalT.setEnabled(true);
        engEvalT.setEditable(false);
        engEvalT.setMargin(new Insets(7,7,7,7));
        engEvalT.setBackground(evalB.getBackground());
        engEvalT.setText("Items not assigned to your catalog may be allowed restricted use for evaluation "+
                         "purposes.  The approval process is much shorter than adding a new Catalog Part.  Later, if desired,"+
                         "the item can be converted to a Catalog Part.");
        engEvalPane.add(engEvalT,new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
                ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        evalB.setText("Add New Item and Place Evaluation Order");
        evalB.setFont(new Font("Dialog", 0, 10));
        ss = ResourceLoader.getImageIcon("images/button/jde.gif","Engineering Evaluation");
        evalB.setIcon(ss);
        /*
        evalB.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            evalB_actionPerformed(e);
          }
        });*/
        engEvalPane.add(evalB,new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
                ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
        roeB.setText("Place Evaluation Order from Item Catalog");
        roeB.setFont(new Font("Dialog", 0, 10));
        ss = ResourceLoader.getImageIcon("images/button/jde.gif","Engineering Evaluation");
        roeB.setIcon(ss);
        /*
        roeB.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            roeB_actionPerformed(e);
          }
        });*/
        engEvalPane.add(roeB,new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
        ss = ResourceLoader.getImageIcon("images/button/nwmat.gif","Engineering Evaluation");
        groupBox3.addTab("Engineering Evaluation",ss,engEvalPane,"Engineering Evaluation");
      }
      groupBox3.validate();
    }else {
      if (indexOfEngEvalTab > 0) {
        engEvalPane = null;
        groupBox3.remove(indexOfEngEvalTab);
      }
    }
  }

  void displayShoppingTab() {
    ss = ResourceLoader.getImageIcon("images/button/cart.gif","Material Request");
    groupBox3.addTab("Shopping Cart",ss,basketPane,"Order Chemicals");
    groupBox3.validate();
  }

  void displayNewChemTab() {
    if (groupBox3.indexOfComponent(nchemPane) < 0) {
      ss = ResourceLoader.getImageIcon("images/button/nwmat.gif","New Material");
      groupBox3.addTab("Catalog Add",ss,nchemPane,"Add to Catalog");
      groupBox3.validate();
    }
    groupBox3.setEnabledAt(groupBox3.indexOfComponent(nchemPane),true);
  }

  //3-21-02
  void displayAribaRepairTab() {
    //ariba repair main panel
    aribaRepairPane = new JPanel();
    aribaRepairPane.setLayout(new BorderLayout());
    //top panel
    StaticJLabel aribaL = new StaticJLabel("MRs with no DO...     MR submitted before(mm/dd/yyyy): ");
    aribaDateT = new JTextField();
    aribaDateT.setMaximumSize(new Dimension(130, 21));
    aribaDateT.setMinimumSize(new Dimension(130, 21));
    aribaDateT.setPreferredSize(new Dimension(130, 21));
    aribaDateT.setFont(new Font("Dialog", 0, 10));
    JPanel aribaRepairTopPane = new JPanel();
    aribaRepairTopPane.add(aribaL,null);
    aribaRepairTopPane.add(aribaDateT,null);
    //middle panel
    String[] aribaTableCols = {"Requestor","Work Area","MR","Line","Date","Part","Desc","Qty","Ext. Price"};
    int[] aribaTableColWidths = {100,100,50,40,80,100,150,50,90};
    int[] aribaTableColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING,
                              BothHelpObjs.TABLE_COL_TYPE_STRING};
    aribaRepairModel = new AttributiveCellTableModel(aribaTableCols,0); //length of table can be set by server
    int i = 0;
    aribaRepairModel.setColumnPrefWidth(aribaTableColWidths);
    aribaRepairModel.setColumnType(aribaTableColTypes);
    aribaRepairTable = new MultiSpanCellTable(aribaRepairModel);
    aribaRepairTable.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    aribaRepairTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    aribaRepairTable.getTableHeader().setResizingAllowed(true);
    aribaRepairTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Color color = (Color)baskStyle.get("BASKET_COLOR");
    Integer t = (Integer)baskStyle.get("BASKET_INSET_TOP");
    Integer l = (Integer)baskStyle.get("BASKET_INSET_LEFT");
    Integer b = (Integer)baskStyle.get("BASKET_INSET_BOTTOM");
    Integer r = (Integer)baskStyle.get("BASKET_INSET_RIGHT");
    Integer a = (Integer)baskStyle.get("BASKET_INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = aribaRepairTable.getColumnModel();
    for (i = 0; i < aribaRepairTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)baskStyle.get("BASKET_FONT_NAME");
    Integer fontStyle = (Integer)baskStyle.get("BASKET_FONT_STYLE");
    Integer fontSize = (Integer)baskStyle.get("BASKET_FONT_SIZE");
    aribaRepairTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer baskRenderer = new MultiLineHeaderRenderer((Color)baskStyle.get("BASKET_FOREGROUND"),(Color)baskStyle.get("BASKET_BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = aribaRepairTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(baskRenderer);
    }
    // set column widths
    aribaRepairTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<aribaRepairTable.getColumnCount();i++){
      int width = aribaRepairModel.getColumnWidth(i);
      aribaRepairTable.getColumn(aribaRepairModel.getColumnName(i)).setPreferredWidth(width);
      aribaRepairTable.getColumn(aribaRepairModel.getColumnName(i)).setWidth(width);
      if (width==0){
        aribaRepairTable.getColumn(aribaRepairModel.getColumnName(i)).setMinWidth(width);
        aribaRepairTable.getColumn(aribaRepairModel.getColumnName(i)).setMaxWidth(width);
      }
    }
    aribaRepairJPS = new JScrollPane(aribaRepairTable);
    //bottom panel
    JPanel aribaRepairBottomPane = new JPanel();
    aribaRepairSearchB = new JButton("Search");
    aribaRepairSearchB.setFont(new Font("Dialog", 0, 10));
    aribaRepairSearchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif","Search"));
    aribaRepairSearchB.setMaximumSize(new Dimension(95, 24));
    aribaRepairSearchB.setMinimumSize(new Dimension(95, 24));
    aribaRepairSearchB.setPreferredSize(new Dimension(95, 24));
    aribaRepairSearchB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        aribaRepairSearchB_actionPerformed(e);
      }
    });
    aribaRepairSubmitB = new JButton("Resubmit MRs");
    aribaRepairSubmitB.setFont(new Font("Dialog", 0, 10));
    aribaRepairSubmitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Submit"));
    aribaRepairSubmitB.setMaximumSize(new Dimension(125, 24));
    aribaRepairSubmitB.setMinimumSize(new Dimension(125, 24));
    aribaRepairSubmitB.setPreferredSize(new Dimension(125, 24));
    aribaRepairSubmitB.setEnabled(false);
    aribaRepairSubmitB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        aribaRepairSubmitB_actionPerformed(e);
      }
    });
    aribaRepairBottomPane.add(aribaRepairSearchB,null);
    aribaRepairBottomPane.add(aribaRepairSubmitB,null);
    //adding top and bottom panel to main panel
    aribaRepairPane.add(aribaRepairTopPane, BorderLayout.NORTH);
    aribaRepairPane.add(aribaRepairJPS, BorderLayout.CENTER);
    aribaRepairPane.add(aribaRepairBottomPane, BorderLayout.SOUTH);
    ss = ResourceLoader.getImageIcon("images/button/refresh.gif","Ariba Repair");
    groupBox3.addTab("Ariba Repair",ss,aribaRepairPane,"Ariba Repair");
    groupBox3.validate();
  }

  void aribaRepairSubmitB_actionPerformed(ActionEvent e) {
    if (!processingData) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      SearchPTLoadThread st = new SearchPTLoadThread(this, "aribaRepairSubmit");
      st.start();
    }
  }

  void goAribaRepairSubmit() {
    processingData = true;
    //first build data
    if (aribaRepairTable.getSelectedRowCount() < 0) {
      GenericDlg gd = new GenericDlg(parent.getMain(),"Error",true);
      gd.setMsg("Please select row(s) you want to resubmit.");
      gd.setVisible(true);
      processingData = false;
      return;
    }
    int[] selRow = aribaRepairTable.getSelectedRows();
    Vector dataV = new Vector(20);
    for (int i = 0; i < selRow.length; i++) {
      Hashtable h = new Hashtable(2);
      h.put("MR",(String)aribaRepairTable.getValueAt(selRow[i],2));
      h.put("LINE",(String)aribaRepairTable.getValueAt(selRow[i],3));
      dataV.addElement(h);
    }

    try {
      parent.startCheckoutExitWaitDlg("Saving Data...","Saving Data...");
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","ARIBA_REPAIR_SUBMIT"); //String, String
      query.put("USERID",(new Integer(parent.getUserId().intValue())).toString()); //String, String
      query.put("RESUBMIT_DATA", dataV);
      query.put("PAYLOAD_ID",parent.getPayLoadID());
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        parent.stopCheckoutExitWaitDlg();
        GenericDlg.showAccessDenied(parent.getMain());
        processingData = false;
        return;
      }
      aribaRepairTable.clearSelection();
      parent.stopCheckoutExitWaitDlg();
      Boolean b = (Boolean)result.get("RETURN_CODE");
      if (!b.booleanValue()) {
        GenericDlg gd = new GenericDlg(parent.getMain(),"Error",true);
        gd.setMsg((String)result.get("RETURN_MSG"));
        gd.setVisible(true);
        processingData = false;
        return;
      }
      //call request window
      Boolean postingMsg = (Boolean)result.get("POSTING_MSG");
      if (postingMsg.booleanValue()) {
        boolean comeFromOracle = false;
        if (result.containsKey("COME_FROM_ORACLE")) {
          comeFromOracle = ((Boolean)result.get("COME_FROM_ORACLE")).booleanValue();
        }
        if (!comeFromOracle) {
          parent.startCheckoutExitWaitDlg("Returning to Ariba...","Returning to Ariba...");
          int count = 0;
          ClientHelpObjs clientHelpObjs = new ClientHelpObjs();
          Hashtable postingV = clientHelpObjs.punchOutSendPostWriteToFile((String)result.get("URL"),(String)result.get("XML_DOC"),"application/x-www-form-urlencoded","PunchOut.html",this,true);   // the boolean parameter - tell us wether this is a first time in or not
          Integer rspcode = (Integer)postingV.get("RESPONSE_CODE");
          while(count < 5) {
            if ((rspcode.intValue() == 301 || rspcode.intValue() == 302)) {
              postingV = clientHelpObjs.punchOutSendPostWriteToFile((String)postingV.get("URL"),(String)result.get("XML_DOC"),"application/x-www-form-urlencoded","PunchOut.html",this,false);   // the boolean parameter - tell us wether this is a first time in or not
              rspcode = (Integer)postingV.get("RESPONSE_CODE");
              count++;
            }else {
              break;
            }
          }
          new URLCall((String)postingV.get("FILE"),parent,true);
        }
        parent.setAribaLogon(false);
        parent.exit();
      }
    }catch (Exception ee) {
      ee.printStackTrace();
      GenericDlg gd = new GenericDlg(parent.getMain(),"Error",true);
      gd.setMsg("Problem occurred while posting message to server.");
      gd.setVisible(true);
    }
    processingData = false;
  }   //end of method

  void aribaRepairSearchB_actionPerformed(ActionEvent e) {
    if (!processingData) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      SearchPTLoadThread st = new SearchPTLoadThread(this, "aribaRepairSearch");
      st.start();
    }
  }

  void goAribaRepairSearch() {
    processingData = true;
    long sTime = new java.util.Date().getTime();
    String mySearchDate = aribaDateT.getText();
    if (!BothHelpObjs.isBlankString(mySearchDate)) {
      if (!BothHelpObjs.isDateFormatRight(mySearchDate)) {
        GenericDlg gd = new GenericDlg(parent.getMain(),"Error",true);
        gd.setMsg("Please enter date in the following format (mm/dd/yyyy).");
        gd.setVisible(true);
        processingData = false;
        return;
      }
    }
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","ARIBA_REPAIR_SEARCH"); //String, String
      query.put("USERID",(new Integer(parent.getUserId().intValue())).toString()); //String, String
      query.put("SEARCH_TEXT",mySearchDate);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        processingData = false;
        return;
      }
      Boolean b = (Boolean)result.get("RETURN_CODE");
      if (!b.booleanValue()) {
        GenericDlg gd = new GenericDlg(parent.getMain(),"Error",true);
        gd.setMsg((String)result.get("RETURN_MSG"));
        gd.setVisible(true);
        processingData = false;
        return;
      }
      buildAribaRepairTable((Hashtable)result.get("SEARCH_RESULT"),sTime);
    } catch (Exception e){
      e.printStackTrace();
    }
    processingData = false;
  }

  void aribaRepairTable_mouseClicked(MouseEvent e) {
    aribaRepairSubmitB.setEnabled(true);
    /* this does not work as expected (well not yet anyway).
    int initIndex = aribaRepairTable.getSelectedRow();
    int lastIndex = initIndex;
    String selectedMR = (String)aribaRepairTable.getValueAt(initIndex,2);
    for (int i = initIndex + 1; i < aribaRepairTable.getRowCount(); i++) {
      String nextSelectedMR = (String)aribaRepairTable.getValueAt(i,2);
      if (selectedMR.equals(nextSelectedMR)) {
        lastIndex = i;
      }else {
        break;
      }
    }
    aribaRepairTable.setRowSelectionInterval(initIndex,lastIndex);
    */
  }

  void buildAribaRepairTable(Hashtable searchH, long sTime) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    aribaRepairSubmitB.setEnabled(false);
    Vector tableDataV = (Vector)searchH.get("TABLE_DATA");
    String[] colHeads = (String[])searchH.get("COL_HEADS");
    int[] colTypes = (int[])searchH.get("COL_TYPES");
    int[] colWidths = (int[])searchH.get("COL_WIDTHS");
    aribaRepairModel = new AttributiveCellTableModel(colHeads,tableDataV.size());
    ColoredCell cellAribaAtt = (ColoredCell)aribaRepairModel.getCellAttribute();
    for (int k = tableDataV.size() -1; k >= 0; k--){
      aribaRepairModel.removeRow(k);
    }

    int i = 0;
    //fill table model
    for (i = 0; i < tableDataV.size(); i++) {
      Object[] oa = new Object[aribaRepairModel.getColumnCount()];
      Hashtable tableDataH = (Hashtable)tableDataV.elementAt(i);
      int colCount = 0;
      oa[colCount++] = (String)tableDataH.get("REQUESTOR");
      oa[colCount++] = (String)tableDataH.get("WORK_AREA");
      oa[colCount++] = (String)tableDataH.get("MR");
      oa[colCount++] = (String)tableDataH.get("LINE");
      oa[colCount++] = (String)tableDataH.get("DATE");
      oa[colCount++] = (String)tableDataH.get("PART");
      oa[colCount++] = (String)tableDataH.get("DESC");
      oa[colCount++] = (String)tableDataH.get("QTY");
      oa[colCount++] = (String)tableDataH.get("EXT_PRICE");

      aribaRepairModel.addRow(oa);
      //color alternate row
      if (i % 2 == 0) {
        int[] rows = new int[]{i};
        for (int j = 0; j < aribaRepairModel.getColumnCount(); j++) {
          int[] cols = new int[]{j};
          cellAribaAtt.setBackground(new Color(224,226,250),rows,cols);
        }
      }
    }

    aribaRepairModel.setColumnPrefWidth(colWidths);
    aribaRepairModel.setColumnType(colTypes);
    aribaRepairTable = new MultiSpanCellTable(aribaRepairModel);
    aribaRepairTable.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    aribaRepairTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    aribaRepairTable.getTableHeader().setResizingAllowed(true);
    aribaRepairTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Color color = (Color)baskStyle.get("BASKET_COLOR");
    Integer t = (Integer)baskStyle.get("BASKET_INSET_TOP");
    Integer l = (Integer)baskStyle.get("BASKET_INSET_LEFT");
    Integer b = (Integer)baskStyle.get("BASKET_INSET_BOTTOM");
    Integer r = (Integer)baskStyle.get("BASKET_INSET_RIGHT");
    Integer a = (Integer)baskStyle.get("BASKET_INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = aribaRepairTable.getColumnModel();
    for (i = 0; i < aribaRepairTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)baskStyle.get("BASKET_FONT_NAME");
    Integer fontStyle = (Integer)baskStyle.get("BASKET_FONT_STYLE");
    Integer fontSize = (Integer)baskStyle.get("BASKET_FONT_SIZE");
    aribaRepairTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer baskRenderer = new MultiLineHeaderRenderer((Color)baskStyle.get("BASKET_FOREGROUND"),(Color)baskStyle.get("BASKET_BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = aribaRepairTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(baskRenderer);
    }
    // set column widths
    aribaRepairTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<aribaRepairTable.getColumnCount();i++){
      int width = aribaRepairModel.getColumnWidth(i);
      aribaRepairTable.getColumn(aribaRepairModel.getColumnName(i)).setPreferredWidth(width);
      aribaRepairTable.getColumn(aribaRepairModel.getColumnName(i)).setWidth(width);
      if (width==0){
        aribaRepairTable.getColumn(aribaRepairModel.getColumnName(i)).setMinWidth(width);
        aribaRepairTable.getColumn(aribaRepairModel.getColumnName(i)).setMaxWidth(width);
      }
    }
    aribaRepairTable.addMouseListener(new SearchPT_aribaRepairTable_mouseAdapter(this));
    aribaRepairPane.remove(aribaRepairJPS);
    aribaRepairPane.validate();
    aribaRepairJPS = new JScrollPane(aribaRepairTable);
    aribaRepairPane.add(aribaRepairJPS, BorderLayout.CENTER);
    aribaRepairPane.validate();

    if (aribaRepairModel.getRowCount() == 0 ) {
      GenericDlg err = new GenericDlg(parent.getMain(),"Not Found",true);
      err.setMsg("No records found.");
      err.show();
    }
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date(min));
    String tmsg = "Records Found: "+aribaRepairModel.getRowCount()+" -- Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
    parent.getMain().countLabel.put(new Integer(Main.SEARCHPT),tmsg);
    parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.SEARCHPT)));
  }


  public void loadIt(){
    parent.stopWaitDlg();
    SearchPTLoadThread st = new SearchPTLoadThread(this,"load");
    st.start();
  }

  public void loadItAction() {
      try {
        ClientHelpObjs.setEnabledContainer(this,false);
        parent.getMain().countLabel.put(new Integer(Main.SEARCHPT),"");
        parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.SEARCHPT)));

        loadInitialFields();
        buildMyComponents();

        //3-21-02
        if (aribaRepair.booleanValue() && parent.getAribaLogon()) {
          displayAribaRepairTab();
        }

        if (updateMR) {
          buildUpdateMR();
        }
        searchB.requestFocus();
      }catch (Exception e) {
        e.printStackTrace();
      }

      groupBox1.validate();
      top.setMinimumSize(new Dimension(0,0));
      top.setPreferredSize(new Dimension(top.getPreferredSize().width,(parent.getMain().getSize().height/2)));
      bottom.setMinimumSize(new Dimension(0,0));
      appChoice.addPropertyChangeListener(new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
          if(e.getPropertyName().equalsIgnoreCase("loaded") ||
             e.getPropertyName().equalsIgnoreCase("selection changed")) {
            setCheckBoxLabels();
          }
        }
      });
      ClientHelpObjs.setEnabledContainer(this,true);

      loading = false;
      if (appChoice.getItemCount() > 1) {
        wAreaCheck.setEnabled(false);
      }
      activeCheck.setSelected(false);
      activeCheck.setEnabled(false);
      partFacChoiceItemAdapter = new SearchPT_facChoice_itemAdapter(this);
      facChoice.addItemListener(partFacChoiceItemAdapter);
      appChoice.addItemListener(new SearchPT_appChoice_itemAdapter(this));
      facChoice.setCatalogScreen(this,true);    //set this so that when work area loaded it will call setWorkAreaActionChanged() for facility with one work area
      if (displayEngEval.booleanValue()) {
        displayEngEvalTab(canCreateMR() && canReqChem());
      }
      //protect new chem
      String tmpSelectedFacilityID = facChoice.getSelectedFacId();
      Vector catalogAddAllowedV = (Vector) facilityCatalog.get(tmpSelectedFacilityID+"CATALOG_ADD_ALLOWED");
      if (catalogAddAllowedV != null) {
        if (catalogAddAllowedV.contains("Y")) {
          nmB.setEnabled(canReqChem());
        }else {
          nmB.setEnabled(false);
        }
      }else {
        nmB.setEnabled(false);
      }
      nspB.setEnabled(false);
      npB.setEnabled(false);
      naB.setEnabled(false);
      ngB.setEnabled(false);
      roeB.setEnabled(false);
      if(!canCreateMR()) {
        addItem.setEnabled(false);
        basketRemove.setEnabled(false);
        addPR.setEnabled(false);
        catalogInShoppingCart = "";
        shoppingCartForUserID = new Integer(0);
      } else {
        addItem.setEnabled(true);
        if (basketTableNew.getRowCount() > 0) {
          basketRemove.setEnabled(true);
          addPR.setEnabled(true);
          facChoice.setEnabled(false);
          catalogInShoppingCart = "Part";
          shoppingCartForUserID = new Integer(0);
        }else {
          basketRemove.setEnabled(false);
          addPR.setEnabled(false);
          catalogInShoppingCart = "";
          shoppingCartForUserID = new Integer(0);
        }
      }
      this.revalidate();
      this.repaint();
      ClientHelpObjs.setComboBoxUpdateUi(this);
  } //end of method

  public void jbInit() throws Exception{

    facChoice.setMaximumSize(new Dimension(75, 24));
    facChoice.setMinimumSize(new Dimension(75, 24));
    facChoice.setPreferredSize(new Dimension(75, 24));
    appChoice.setMaximumSize(new Dimension(75, 24));
    appChoice.setMinimumSize(new Dimension(75, 24));
    appChoice.setPreferredSize(new Dimension(75, 24));
    catalogC.setMaximumSize(new Dimension(75, 24));
    catalogC.setMinimumSize(new Dimension(75, 24));
    catalogC.setPreferredSize(new Dimension(75, 24));
    posFacC.setMaximumSize(new Dimension(75, 24));
    posFacC.setMinimumSize(new Dimension(75, 24));
    posFacC.setPreferredSize(new Dimension(75, 24));
    posWAC.setMaximumSize(new Dimension(75, 24));
    posWAC.setMinimumSize(new Dimension(75, 24));
    posWAC.setPreferredSize(new Dimension(75, 24));
    posLimitedWAC.setMaximumSize(new Dimension(75, 24));
    posLimitedWAC.setMinimumSize(new Dimension(75, 24));
    posLimitedWAC.setPreferredSize(new Dimension(75, 24));

    //POS
    customerL.setText("Customer: ");
    customerT.setMaximumSize(new Dimension(180,21));
    customerT.setMinimumSize(new Dimension(180,21));
    customerT.setPreferredSize(new Dimension(180,21));
    customerB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        customerB_actionPerformed(e);
      }
    });
    customerB.setMaximumSize(new Dimension(35, 21));
    customerB.setMinimumSize(new Dimension(35,21));
    customerB.setPreferredSize(new Dimension(35,21));
    customerB.setText("");
    customerB.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/search_small.gif","Search");
    customerB.setIcon(ss);

    clearCustomerB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearCustomerB_actionPerformed(e);
      }
    });
    clearCustomerB.setMaximumSize(new Dimension(65, 21));
    clearCustomerB.setMinimumSize(new Dimension(65,21));
    clearCustomerB.setPreferredSize(new Dimension(65,21));
    clearCustomerB.setFont(new Font("Dialog", 0, 10));
    clearCustomerB.setText("Clear");
    clearCustomerB.setEnabled(false);

    this.addKeyListener(new SearchPT_this_keyAdapter(this));
    this.setLayout(new BorderLayout());
    this.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        this_componentResized(e);
      }
    });
    this.setPreferredSize(new Dimension(760, 240));
    userLabel.setText("Name:");
    facilityL.setText("Facility:");
    workAreaL.setText("Work Area:");
    itemText.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        itemText_keyPressed(e);
      }
    });

    itemText.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        itemText_mousePressed();
      }
    });

    panel2.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        panel2_focusLost(e);
      }
    });


    // --------------------- buttons
    searchB.setText("Search");
    searchB.setRolloverEnabled(true);
    searchB.setToolTipText("Search the Catalog");
    ss = ResourceLoader.getImageIcon("images/button/search.gif","Search");
    searchB.setIcon(ss);

    //trong
    searchB.setMaximumSize(new Dimension(95, 24));
    searchB.setMinimumSize(new Dimension(95, 24));
    searchB.setPreferredSize(new Dimension(95, 24));


    nmB.setText("New Material");
    nspB.setText("New Size / Packing");
    npB.setText("New Catalog Part");
    naB.setText("New Work Area Approval");
    ngB.setText("New User Approval");

    nmB.setEnabled(false);
    ss = ResourceLoader.getImageIcon("images/button/nwmat.gif","New Material");
    nmB.setIcon(ss);

    ngB.setEnabled(false);
    ss = ResourceLoader.getImageIcon("images/button/nwgroup.gif","New Material");
    ngB.setIcon(ss);

    nspB.setEnabled(false);
    ss = ResourceLoader.getImageIcon("images/button/nwpack.gif","New Size-Packing");
    nspB.setIcon(ss);

    npB.setEnabled(false);
    ss = ResourceLoader.getImageIcon("images/button/nwprt.gif","New Part");
    npB.setIcon(ss);

    naB.setEnabled(false);
    ss = ResourceLoader.getImageIcon("images/button/nwapp.gif","New Approval");
    naB.setIcon(ss);

    naB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        naB_actionPerformed(e);
      }
    });
    npB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        npB_actionPerformed(e);
      }
    });
    nspB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nspB_actionPerformed(e);
      }
    });
    nmB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nmB_actionPerformed(e);
      }
    });
    ngB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ngB_actionPerformed(e);
      }
    });

    evalB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        evalB_actionPerformed(e);
      }
    });

    roeB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        roeB_actionPerformed(e);
      }
    });


    basketPane.setLayout(new BorderLayout());
    groupBox1.setLayout(gridBagLayout1);
    groupBox1.setBorder(ClientHelpObjs.groupBox("Approval Information / Search Criteria"));
    groupBox2.setLayout(gridBagLayout2);
    groupBox2.setBorder(ClientHelpObjs.groupBox("Search Criteria"));
    groupBox3.setTabPlacement(JTabbedPane.BOTTOM);
    groupBox3.setFont(new java.awt.Font("Dialog", 0, 10));


    //basketPane.setBorder(ClientHelpObjs.groupBox("Shopping Cart"));

    addItem.setText("Add to Cart");
    addItem.setRolloverEnabled(true);
    label1.setVisible(false);
    label1.setText(".. Please Wait ...");
    groupBox4.setLayout(borderLayout3);
    groupBox4.setBorder(ClientHelpObjs.groupBox("Catalog"));
    gridLayout1.setRows(2);
    gridLayout1.setColumns(1);
    gridLayout2.setColumns(2);
    gridLayout3.setColumns(1);
    gridLayout3.setRows(2);
    panel6.setLayout(gridBagLayout4);
    panel5.setLayout(borderLayout6);
    panel4.setLayout(borderLayout5);
    panel3.setLayout(gridBagLayout3);
    searchB.addKeyListener(new SearchPT_searchB_keyAdapter(this));
    searchB.addActionListener(new SearchPT_searchB_actionAdapter(this));
    addItem.addActionListener(new SearchPT_addItem_actionAdapter(this));
    addItem.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add"));
    addItem.setText("Add");
    addItem.setToolTipText(" Add to cart  ");

    userName.setText("");
    //appChoice.addItemListener(new SearchPT_appChoice_itemAdapter(this));
    //commented out 10-01-01 until we know what to do with this
    groupBox3.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        ClientHelpObjs.setTabbedPaneForegroundColor(groupBox3);
      }
    });

    facilityCheck.setText(" Active for Facility");
    itemText.setMaximumSize(new Dimension(130, 24));
    itemText.setMinimumSize(new Dimension(130, 24));
    itemText.setPreferredSize(new Dimension(130, 24));
    jTextField1.setText("jTextField1");
    panel3.setMaximumSize(new Dimension(300, 300));
    panel3.setMinimumSize(new Dimension(300, 300));
    panel3.setPreferredSize(new Dimension(300, 300));
    panel4.setMaximumSize(new Dimension(250, 200));
    panel4.setMinimumSize(new Dimension(250, 250));
    panel4.setPreferredSize(new Dimension(250, 250));
    viewC.setMaximumSize(new Dimension(75, 24));
    viewC.setMinimumSize(new Dimension(75, 24));
    viewC.setPreferredSize(new Dimension(75, 24));

    facCheckGroup.add(facilityCheck);

    facilityCheck.addItemListener(new SearchPT_facilityCheck_itemAdapter(this));
    //oFacilityCheck.setFont(new Font("Dialog", 0, 10));
    oFacilityCheck.setText(" All Catalogs");
    facCheckGroup.add(oFacilityCheck);

    oFacilityCheck.addItemListener(new SearchPT_oFacilityCheck_itemAdapter(this));
    //wAreaCheck.setFont(new Font("Dialog", 0, 9));
    wAreaCheck.setText(" Work Area Approved Only");
    wAreaCheck.addItemListener(new SearchPT_wAreaCheck_itemAdapter(this));

    //trong 3/14/00
    wAreaCheck.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        wAreaCheck_actionPerformed(e);
      }
    });

    catCheck.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        catCheck_itemStateChanged(e);
      }
    });


    label3.setText("Search Text:");
    basketRemove.setRolloverEnabled(true);
    basketRemove.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif","Delete"));
    basketRemove.setText("Delete Line");
    basketRemove.addKeyListener(new SearchPT_basketRemove_keyAdapter(this));
    basketRemove.addActionListener(new SearchPT_basketRemove_actionAdapter(this));
    addPR.setRolloverEnabled(true);
    addPR.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Submit"));
    addPR.setText("Check Out");
    addPR.setToolTipText(" Check Out  ");
    addPR.addKeyListener(new SearchPT_addPR_keyAdapter(this));
    addPR.addActionListener(new SearchPT_addPR_actionAdapter(this));
    cancelB.setText("Cancel");
    itemText.setToolTipText("  Search Text  ");

    panel1.setLayout(borderLayout1);
    panel2.setLayout(borderLayout2);
    gridLayout4.setColumns(2);
    gridLayout4.setVgap(2);
    gridLayout4.setRows(2);
    nchemPane.setLayout(gridLayout4);
    //trong 3/30
    gridLayout5.setColumns(1);
    gridLayout5.setVgap(1);
    gridLayout5.setRows(3);
    eEvalPane.setLayout(gridLayout5);

    // put split on
    split.setContinuousLayout(false);
    split.setDividerSize(10);
    split.setTopComponent(top);
    split.setBottomComponent(bottom);
    top.setLayout(new BorderLayout());
    bottom.setLayout(new BorderLayout());
    top.add(panel6,BorderLayout.CENTER);
    bottom.add(panel5,BorderLayout.CENTER);

    this.add(split);

    catalogL.setText("Catalog Type:");
    activeCheck.setText(" Active Only");

    panel6.add(panel3, new GridBagConstraints2(0, 0, 1, 1, 0.2, 0.2
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel3.add(groupBox1, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    groupBox1.add(userLabel, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0));
    groupBox1.add(userName, new GridBagConstraints2(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 0), 0, 0));
    groupBox1.add(facilityL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0));
    groupBox1.add(facChoice, new GridBagConstraints2(1, 2, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
    groupBox1.add(workAreaL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0));
    groupBox1.add(appChoice, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
    groupBox1.add(catalogL, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0));
    groupBox1.add(catalogC, new GridBagConstraints2(1, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
    groupBox1.add(facilityCheck, new GridBagConstraints2(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 0), 0, 0));
    groupBox1.add(wAreaCheck, new GridBagConstraints2(0, 5, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 156, 2, 0), 0, 0));
    groupBox1.add(oFacilityCheck, new GridBagConstraints2(0, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 0), 0, 0));
    groupBox1.add(activeCheck, new GridBagConstraints2(0, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 220, 2, 0), 0, 0));
    groupBox1.add(itemText, new GridBagConstraints2(0, 7, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 105, 2, 0), 0, 0));
    groupBox1.add(searchB, new GridBagConstraints2(0, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 0), 0, 0));

    groupBox1.add(viewC, new GridBagConstraints2(0, 6, 3, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 240, 2, 0), 0, 0));

    panel6.add(panel4, new GridBagConstraints2(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    basketPane.add(panel2, BorderLayout.CENTER);
    basketPane.add(panel7, BorderLayout.SOUTH);

    nchemPane.add(nmB);
    nchemPane.add(nspB);
    nchemPane.add(npB);
    nchemPane.add(naB);

    //trong 3/30
    eEvalPane.add(evalB,null);
    eEvalPane.add(roeB,null);
    eEvalPane.add(ctcB,null);



    ss = ResourceLoader.getImageIcon("images/button/cart.gif","Material Request");
    groupBox3.addTab("Shopping Cart",ss,basketPane,"Order Chemicals");
    //commented out 10-01-01 until we know what to do with this
    ss = ResourceLoader.getImageIcon("images/button/nwmat.gif","New Material");
    groupBox3.addTab("Catalog Add",ss,nchemPane,"Add to Catalog");

    panel4.add(groupBox3, BorderLayout.CENTER);

    panel7.add(addItem, null);
    panel7.add(basketRemove, null);
    panel7.add(addPR, null);

    panel5.add(groupBox4, BorderLayout.CENTER);
    groupBox4.add(panel1, BorderLayout.CENTER);

    addItem.setEnabled(false);
    addPR.setEnabled(false);
    basketRemove.setEnabled(false);

    searchB.setToolTipText("Search the Catalog");
    searchB.repaint();

    //Colors
    // labels go Black
    // keep username
    Color keep = userName.getForeground();
    /*
    top.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        top_componentResized(e);
      }
    }); */
    // -- rfz  basketTable.setPreferredScrollableViewportSize(new Dimension(30, 30));
    // rfz catTable.setMaximumSize(new Dimension(567567, 567567));
    // rfz catTable.setPreferredSize(new Dimension(740, 240));
    // rfz jScrollPane1.setPreferredSize(new Dimension(4, 240));
    //this.setMinimumSize(new Dimension(1, 240));
    //bottom.setMinimumSize(new Dimension(20, 240));
    ClientHelpObjs.makeDefaultColors(this);

    // Name and border text keeps blue
    userName.setForeground(keep);
    ClientHelpObjs.setTabbedPaneForegroundColor(groupBox3);
  }

  void wAreaCheck_actionPerformed(ActionEvent e) {

  }

  void buildMyComponents(){
    buildEmptyPartCatalogTable();
    buildEmptyPOSCatalogTable();
    jScrollPane1 =  new JScrollPane(catTableNew);
    panel1.add(jScrollPane1, BorderLayout.CENTER);
    panel1.validate();

    // basket table
    int i = 0;
    Integer baskRow = (Integer)baskStyle.get("BASKET_TABLE_ROW");
    //2-27-02 I am defining this to be global so I can turn editing flag on and off whenever.
    catModel = new AttributiveCellTableModel(baskColsNew,baskRow.intValue()); //length of table can be set by server
    for (i = catModel.getRowCount()-1; i >= 0; i--) {
      catModel.removeRow(i);
    }
    Integer editF = (Integer)baskStyle.get("EDITABLE_FLAG");
    catModel.setEditableFlag(editF.intValue());
    catModel.setColumnPrefWidth(baskColWidths);
    catModel.setColumnType(baskColTypes);
    basketTableNew = new MultiSpanCellTable(catModel);

    basketTableNew.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    basketTableNew.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    basketTableNew.getTableHeader().setResizingAllowed(true);
    basketTableNew.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Color color = (Color)baskStyle.get("BASKET_COLOR");
    Integer t = (Integer)baskStyle.get("BASKET_INSET_TOP");
    Integer l = (Integer)baskStyle.get("BASKET_INSET_LEFT");
    Integer b = (Integer)baskStyle.get("BASKET_INSET_BOTTOM");
    Integer r = (Integer)baskStyle.get("BASKET_INSET_RIGHT");
    Integer a = (Integer)baskStyle.get("BASKET_INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = basketTableNew.getColumnModel();
    for (i = 0; i < basketTableNew.getColumnCount(); i++) {
      if (i == CART_CRITICAL_COL) {
        cm.getColumn(i).setCellRenderer(new CartCheckBoxCellRenderer());
        JCheckBox check = new JCheckBox();
        check.setHorizontalAlignment(JLabel.CENTER);
        cm.getColumn(i).setCellEditor(new CheckBoxCellEditor(check));
      }else if (i == CART_PACKAGING_COL) {
        cm.getColumn(i).setCellRenderer(new JComboBoxCellRenderer());
        cm.getColumn(i).setCellEditor(new JComboBoxCellEditor(new JComboBox()));
      }else{
        cm.getColumn(i).setCellRenderer(renderers[0]);
      }
    }
    //font and foreground and background color for columns heading
    String fontName = (String)baskStyle.get("BASKET_FONT_NAME");
    Integer fontStyle = (Integer)baskStyle.get("BASKET_FONT_STYLE");
    Integer fontSize = (Integer)baskStyle.get("BASKET_FONT_SIZE");
    basketTableNew.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer baskRenderer = new MultiLineHeaderRenderer((Color)baskStyle.get("BASKET_FOREGROUND"),(Color)baskStyle.get("BASKET_BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = basketTableNew.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(baskRenderer);
    }
    // set column widths
    basketTableNew.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<basketTableNew.getColumnCount();i++){
      int width = catModel.getColumnWidth(i);
      basketTableNew.getColumn(catModel.getColumnName(i)).setPreferredWidth(width);
      basketTableNew.getColumn(catModel.getColumnName(i)).setWidth(width);
      if (width==0){
        basketTableNew.getColumn(catModel.getColumnName(i)).setMinWidth(width);
        basketTableNew.getColumn(catModel.getColumnName(i)).setMaxWidth(width);
      }
    }
    basketTableNew.addMouseListener(new SearchPT_basketTable_mouseAdapter(this));
    jScrollPane2 = new JScrollPane(basketTableNew);

    jScrollPane2.setMaximumSize(new Dimension(800,180));
    panel2.add(jScrollPane2, BorderLayout.CENTER);
    panel2.addMouseListener(new SearchPT_panel2_mouseAdapter(this));

    // Drag Win
    dragWin = new JWindow(parent.getMain());
    dragWin.setBackground(new Color(255, 255, 255));
    setDragWin("Starting");

    //check box default
    facilityCheck.setSelected(true);
    catCheck.setSelected(true); //trong
  }

  JComboBox getComboDetail(String sizeUnitOptions, String s) {
    JComboBox j = new JComboBox();
    Vector v = new Vector();
    if (BothHelpObjs.isBlankString(sizeUnitOptions)) {
      v.addElement("No Size Unit");
    }else {
      StringTokenizer st = new StringTokenizer(sizeUnitOptions,";");
      if (st.countTokens() == 1) {
        v.addElement(sizeUnitOptions);
      }else {
        while (st.hasMoreElements()) {
          v.addElement(st.nextToken());
        }
      }
    }
    if (v.size() > 1) {
      v.insertElementAt("Select One",0);
    }

    j = ClientHelpObjs.loadChoiceFromVector(j,v);
    if (v.contains(s)) {
      j.setSelectedItem(s);
    }else {
      j.setSelectedIndex(0);
    }
    j.setEditable(false);
    j.setMaximumRowCount(v.size());
    j.revalidate();
    return j;
  }

  void buildUpdateMR() {
    String facil = "";
    if (updateMRInfo.size() < 1) return;
    for (int j = 0; j < updateMRInfo.size(); j++) {
      Hashtable h = (Hashtable)updateMRInfo.elementAt(j);
      Object[] oa = new Object[basketTableNew.getColumnCount()];
      oa[CART_WORK_AREA_COL] = (String)h.get("WORK_AREA_ID");
      oa[1] = (String)h.get("WORK_AREA");
      oa[CART_PART_COL] = (String)h.get("PART_NO");
      oa[CART_QTY_COL] = (String)h.get("QTY");
      if (!BothHelpObjs.isBlankString((String)h.get("EXT_PRICE"))) {
        String extPrice = (String)h.get("EXT_PRICE");
        String[] priceInfo = separatePriceFromCurrency(extPrice);
        extPrice = priceInfo[0];
        BigDecimal amt = new BigDecimal(extPrice);
        amt = amt.setScale(4,BigDecimal.ROUND_HALF_UP);
        oa[CART_EXT_PRICE_COL] = amt.toString()+priceInfo[1];
      }else {
        oa[CART_EXT_PRICE_COL] = "";
      }

      oa[CART_PACKAGING_COL] = getComboDetail((String)h.get("PACKAGING"),(String)h.get("PACKAGING"));
      oa[CART_DISPENSED_UOM_COL] = (String)h.get("PACKAGING");
      oa[CART_DISPENSED_ITEM_TYPE_COL] = "";                                    //this column is only use in dispensing catalog
      oa[CART_REQUIRED_DATE_COL] = (String)h.get("REQUIRED_DATE");
      oa[CART_ITEM_COL] = (String)h.get("ITEM_ID");
      mySelectedAccSys = (String)h.get("ACCOUNT_SYS_ID");
      facil = (String)h.get("FACILITY_ID");
      oa[CART_FACILITY_COL] = facil;
      oa[CART_PR_COL] = (String)h.get("PR_NUMBER");
      oa[CART_LINE_ITEM_COL] = (String)h.get("LINE_ITEM");
      oa[CART_CATALOG_COL] = (String)h.get("CATALOG");
      oa[CART_CATALOG_COMPANY_ID_COL] = (String)h.get("CATALOG_COMPANY_ID");
      oa[CART_TYPE_COL] = (String)h.get("TYPE");
      oa[CART_EXAMPLE_ITEM_COL] = (String)h.get("EXAMPLE_ITEM_ID");
      oa[CART_CATALOG_PRICE_COL] = (String)h.get("CATALOG_PRICE");
      oa[CART_UNIT_PRICE_COL] = (String)h.get("UNIT_PRICE");
      oa[CART_MIN_BUY_COL] = BothHelpObjs.makeBlankFromNull((String)h.get("MIN_BUY"));                    //2-26-02
      oa[CART_PART_GROUP_COL] = (String)h.get("PART_GROUP");
      oa[CART_INVENTORY_GROUP_COL] = (String)h.get("INVENTORY_GROUP");
      //12-15-01
      oa[CART_PART_DESC_COL] = (String)h.get("PART_DESC");
      String notes = (String)h.get("NOTES");
      if (!BothHelpObjs.isBlankString(notes)) {
        oa[CART_NOTE_COL] = "+";
      }else {
        oa[CART_NOTE_COL] = "";
      }
      oa[CART_REAL_NOTE_COL] = notes;
      oa[CART_CRITICAL_COL] = new Boolean("Y".equalsIgnoreCase((String)h.get("CRITICAL")));
      oa[CART_CURRENCY_ID_COL] = (String)h.get("CURRENCY_ID");
      oa[CART_ORDER_QUANTITY_RULE_COL] = (String)h.get("ORDER_QUANTITY_RULE");

      addRowToTable(basketTableNew,oa);
    }
    //set shopping cart editable
    setCatEditableFlag();
    //block change facilityid
    facChoice.setSelectedFacId(facil);
    facChoice.setEnabled(false);
    if (parent.getAribaLogon() && updateMR && ("Production".equalsIgnoreCase(editAccountSysID) || "Production - ERS".equalsIgnoreCase(editAccountSysID))) {
      addPR.setEnabled(false);
      addPR.requestFocus();
      basketRemove.setEnabled(false);
    }else {
      addPR.setEnabled(true);
      addPR.requestFocus();
      basketRemove.setEnabled(true);
    }
    catalogInShoppingCart = "Part";
    shoppingCartForUserID = new Integer(0);
  }

  void loadInitialFields(){
    Hashtable result = parent.loadAllData;
    if (result == null) {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","LOAD_ALL_DATA"); //String, String
      query.put("USERID",(new Integer(parent.getUserId().intValue())).toString()); //String, String
      query.put("UPDATE_MR",new Boolean(updateMR));
      query.put("PR_NUMBER", new Integer(prNum));
      query.put("PAYLOAD_ID",parent.getPayLoadID());
      query.put("ARIBA_LOGON",new Boolean(parent.getAribaLogon()));
      query.put("NEW","new");
      result = cgi.getResultHash(query);
    }
    if (result == null){
      GenericDlg.showAccessDenied(parent.getMain());
      return;
    }
    userName.setText((String)((Vector) result.get("USER_NAME")).elementAt(0));

    //get list of work areas where I can create MR for
    canCreateMRAppH = (Hashtable) result.get("CAN_CREATE_MR_APP");
    posFacilityH = (Hashtable) result.get("CAN_CREATE_FOR_POS");
    pointOfSaleName = (String) result.get("POINT_OF_SALE_NAME");

    //12-04-01
    Boolean editMR = (Boolean)result.get("UPDATE_MR");
    updateMR = editMR.booleanValue();
    //12-04-01 set this so I could decide whether to allow user change shopping cart data
    editAccountSysID = (String)result.get("ACCOUNT_SYS_ID");

    //3-21-02
    aribaRepair = (Boolean)result.get("ARIBA_REPAIR");
    ecomByPass = (Boolean)result.get("ECOM_BY_PASS");

    // do not remove this right now. There other screens depending on it   - rfz
    // START
    Vector facIDV = (Vector) result.get("FAC_ID");
    Vector facNameV = (Vector) result.get("FAC_NAME");
    Hashtable facXref = new Hashtable();
    for (int i=0;i<facIDV.size();i++){ // build hashtable
       facXref.put((String)facNameV.elementAt(i),(String)facIDV.elementAt(i));
    }
    // keep this on cmisapp
    parent.setXfac(facXref);
    // DONE

    // keep pref fac and pref app on cmis
    parent.setPrefFac(((Vector) result.get("FAC_USER")).elementAt(0).toString());
    parent.setPrefApp(((String) result.get("APP_USER")));                          //7-03-02

    //loading the catalog type and catalog view from server
    catalogC = ClientHelpObjs.loadChoiceFromVector(catalogC,(Vector)result.get("CATALOG_TYPE"));
    catalogActionListener = new CatalogActionListener(this);
    catalogC.addActionListener(catalogActionListener);
    lastSelectedCatalog = catalogC.getSelectedItem().toString();

    viewC = ClientHelpObjs.loadChoiceFromVector(viewC,(Vector)result.get("CATALOG_VIEW"));
    if (viewC.getItemCount() == 1) {
      viewC.setVisible(false);
    }

    //loading shopping cart table info from server
    baskColsNew = (String[])result.get("BASKET_COL");
    baskColWidths = (int[])result.get("BASKET_COL_WIDTHS");
    baskColTypes =  (int[])result.get("BASKET_COL_TYPES");
    baskStyle = (Hashtable)result.get("BASKET_STYLE");

    Hashtable keyCol = (Hashtable)result.get("KEY_COLUMNS");
    CART_PART_COL = ((Integer)keyCol.get("Part Number")).intValue();
    CART_QTY_COL = ((Integer)keyCol.get("Part Qty")).intValue();
    CART_EXT_PRICE_COL = ((Integer)keyCol.get("Ext. Price")).intValue();
    CART_ITEM_COL = ((Integer)keyCol.get("Item")).intValue();
    CART_CATALOG_COL = ((Integer)keyCol.get("Catalog")).intValue();
    CART_CATALOG_COMPANY_ID_COL = ((Integer)keyCol.get("Catalog Company ID")).intValue();
    CART_FACILITY_COL = ((Integer)keyCol.get("Facility")).intValue();
    CART_WORK_AREA_COL = ((Integer)keyCol.get("Work Area ID")).intValue();
    CART_PR_COL = ((Integer)keyCol.get("PR")).intValue();
    CART_LINE_ITEM_COL = ((Integer)keyCol.get("Line Item")).intValue();
    CART_PACKAGING_COL = ((Integer)keyCol.get("Example Packaging")).intValue();
    CART_TYPE_COL = ((Integer)keyCol.get("Type")).intValue();
    CART_EXAMPLE_ITEM_COL = ((Integer)keyCol.get("Example Item")).intValue();
    CART_REQUIRED_DATE_COL = ((Integer)keyCol.get("Date Needed")).intValue();
    CART_CATALOG_PRICE_COL = ((Integer)keyCol.get("Catalog Price")).intValue();
    CART_UNIT_PRICE_COL = ((Integer)keyCol.get("Unit Price")).intValue();
    CART_MIN_BUY_COL = ((Integer)keyCol.get("Min Buy")).intValue();
    CART_PART_GROUP_COL = ((Integer)keyCol.get("Part Group")).intValue();
    CART_INVENTORY_GROUP_COL = ((Integer)keyCol.get("Inventory Group")).intValue();
    CART_PART_DESC_COL = ((Integer)keyCol.get("Part Desc.")).intValue();
    CART_NOTE_COL = ((Integer)keyCol.get("Notes")).intValue();
    CART_REAL_NOTE_COL = ((Integer)keyCol.get("Real Notes")).intValue();
    CART_CRITICAL_COL = ((Integer)keyCol.get("Critical")).intValue();
    CART_DISPENSED_UOM_COL = ((Integer)keyCol.get("Dispensed UOM")).intValue();
    CART_DISPENSED_ITEM_TYPE_COL = ((Integer)keyCol.get("Dispensed Item Type")).intValue();
    CART_CURRENCY_ID_COL = ((Integer)keyCol.get("Currency ID")).intValue();
    CART_ORDER_QUANTITY_RULE_COL = ((Integer)keyCol.get("Order Quantity Rule")).intValue();

    //part catalog
    catColsNew = (String[])result.get("CATALOG_COL");
    catColWidths = (int[])result.get("CATALOG_COL_WIDTHS");
    catColTypes =  (int[])result.get("CATALOG_COL_TYPES");
    //item catalog
    itemCatCols = (String[])result.get("ITEM_CATALOG_COL");
    itemCatColWidths = (int[])result.get("ITEM_CATALOG_COL_WIDTHS");
    itemCatColTypes =  (int[])result.get("ITEM_CATALOG_COL_TYPES");
    catStyle = (Hashtable)result.get("CATALOG_STYLE");


    //list of fac pref that can create MR (e.i. facilities definited as Ecommerce)
    facInEComList = (Vector)result.get("FAC_IN_ECOM_LIST");
    catAddFacSingleAppList = (Vector)result.get("CAT_ADD_FAC_SINGLE_APP_LIST");

    if (updateMR) {
      updateMRInfo = (Vector)result.get("UPDATE_MR_INFO");
    }

    displayEngEval = (Boolean)result.get("ENG_EVAL");

    //facility catalog list
    facilityCatalog = (Hashtable)result.get("FACILITY_CATALOG");
    dataLoaded(this);
    //Whether I can see point of sale catalog
    setPOSCatalogToDropDown(true);
    stateChanged("Search");
  } //end of method

  void dataLoaded(Object o){
    if(o == facChoice){
      facChoiceLoaded = true;
    }
    if(o == this){
      dataLoaded = true;
    }
    if(facChoiceLoaded && dataLoaded){
      facChoice.setSelectedFacId(parent.getPrefFac());
      //7-03-02
      if (!BothHelpObjs.isBlankString(parent.getPrefApp())) {
        appChoice.setSelectedItem(parent.getPrefApp());
      }
    }
  }

  public void printTableData() {
    ReportOptionDlg rod = new ReportOptionDlg(parent.getMain());
    rod.setCmisApp(parent);
    String mySearchT = BothHelpObjs.makeBlankFromNull(this.itemText.getText().toString());
    if (BothHelpObjs.isBlankString(mySearchT) || mySearchT == null) {
      mySearchT = new String("");
    }
    rod.setFacId(this.facChoice.getSelectedFacId().toString());
    rod.setWorkArea(this.appChoice.getSelectedWorkAreaID().toString());
    rod.setWaName(this.appChoice.getSelectedItem().toString());
    rod.setFacName(this.facChoice.getSelectedItem().toString());
    rod.setScreen("Catalog");
    rod.setCatalogType(catalogC.getSelectedItem().toString());
    rod.setSearchText(mySearchT);
    if ("Item Catalog".equalsIgnoreCase(catalogC.getSelectedItem().toString())) {
      if (facInEComList.contains(facChoice.getSelectedFacId().toString())) {
        rod.setAllFacilities(false);
        rod.setEngEvalCat(false);
        rod.setApproved(false);
        rod.setActive(false);
      }else {
        rod.setAllFacilities(itemCatEvalCheck.isSelected());
        rod.setEngEvalCat(itemCatRequestorCheck.isSelected());
        rod.setApproved(itemCatFacCheck.isSelected());
        rod.setActive(itemCatWACheck.isSelected());
      }
    }else {
      rod.setAllFacilities(oFacilityCheck.isSelected());       //all catalog
      rod.setEngEvalCat(eEvalCheck.isSelected());
      rod.setApproved(wAreaCheck.isSelected());
      rod.setActive(activeCheck.isSelected());
    }
    rod.loadIt();
  } //end of method

  void setCheckBoxLabels(){
    /* if(!facChoiceLoaded)return;
    // Labels
    facilityCheck.setSelected(true);
    //wAreaCheck.setSelected(true);
    stateChanged("Refresh");

    //trong just learn it from Rodrigo -- about thread.
    while (facChoice.isLoading()){
      try { Thread.currentThread().sleep(500); } catch (Exception e){};
    }
    appChoice = facChoice.getWorkAreaCombo();
    if (appChoice.getSelectedItem().equals(SELECT_WORK_AREA)){
        //wAreaCheck.setSelected(false);
        facilityCheck.setText(facChoice.getSelectedFacName());
    }else{


      facilityCheck.setText(facChoice.getSelectedFacName()+"  ("+appChoice.getSelectedItem()+")");

      if (catCheck.isSelected())
        wAreaCheck.setSelected(true);
      wAreaCheck.setEnabled(true);
    } */
  }

  //protected AttributiveCellTableModel buildSearchTableNewDetail(Hashtable h) {
  protected void buildSearchTableDetail(Hashtable h) {
    //long sTime = new java.util.Date().getTime();
    partRowCount = 0;
    itemRowCount = 0;
    Vector tableData = (Vector)h.get("TABLE_DATA");
    String[] colHeads = (String[])h.get("COL_HEADS");
    int[] colTypes = (int[])h.get("COL_TYPES");
    int[] colWidths = (int[])h.get("COL_WIDTHS_DETAIL");
    Color pColor = (Color)h.get("PART_COLOR");
    Color itemCompColor = (Color)h.get("ITEM_COMP_COLOR");
    int rowNum = tableData.size();
    //AttributiveCellTableModel model = new AttributiveCellTableModel(colHeads,rowNum);
    modelDetail = new AttributiveCellTableModel(colHeads,rowNum);
    for (int k = rowNum -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }

    //if there are records
    if (tableData.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      String lastPartItemGroup = "";
      String lastPartGroup = "";
      Vector partRows = new Vector();
      partRows.addElement(new Integer(0));
      boolean partColor = false;
      boolean itemColor = false;
      Vector itemRows = new Vector();
      itemRows.addElement(new Integer(0));
      int[] cols = {0};
      int row = 0;
      for (int ii = 0; ii < tableData.size(); ii++) {
        Vector data = (Vector)tableData.elementAt(ii);
        Object[] oa = new Object[colHeads.length];

        String part = (String)data.elementAt(0);
        String item = (String)data.elementAt(1);
        String partGroup = (String)data.elementAt(2);
        String partItemGroup = (String)data.elementAt(3);
        int k = 0;
        for (int j = 4; j < data.size(); j++) {
          if (j == DESC_COL+4) {
            //oa[k++] = new JTextArea("this is a test. check this out.");
            oa[k++] = new JTextArea(BothHelpObjs.makeBlankFromNull((String)data.elementAt(j)));
          }else{
            oa[k++] = (String)data.elementAt(j);
          }
        }
        modelDetail.addRow(oa);
        //preparing logic for grouping
        if(!partGroup.equals(lastPartGroup)) {
            int[] rows = new int[partRows.size()];
            for (int i = 0; i < partRows.size(); i++) {
              Integer r = (Integer)partRows.elementAt(i);
              rows[i] = r.intValue();
            }
            if (partColor) {
              modelDetail.setValueAt("y",rows[0],PART_COLOR_COL);
              for (int i = 0; i < ITEM_PART_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(pColor,rows,cols);
              }
              if (partRows.size() > 1) {
                modelDetail.setValueAt("y",rows[0],GROUP_COL);
              }else {
                modelDetail.setValueAt("n",rows[0],GROUP_COL);
              }
              partRows.removeAllElements();
              partColor = false;
            }else {
              modelDetail.setValueAt("n",rows[0],PART_COLOR_COL);
              for (int i = 0; i < ITEM_PART_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              if (partRows.size() > 1) {
                modelDetail.setValueAt("y",rows[0],GROUP_COL);
              }else {
                modelDetail.setValueAt("n",rows[0],GROUP_COL);
              }
              partRows.removeAllElements();
              partColor = true;
            }
            partRows.addElement(new Integer(row));
            partRowCount++;
        }else {
          partRows.addElement(new Integer(row));
        }

        if(!partItemGroup.equals(lastPartItemGroup)) {
            int[] rows = new int[itemRows.size()];
            for (int i = 0; i < itemRows.size(); i++) {
              Integer r = (Integer)itemRows.elementAt(i);
              rows[i] = r.intValue();
            }
            if (itemColor) {
              //color for item
              for (int i = ITEM_PART_COL; i <= ITEM_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(itemCompColor,rows,cols);
              }
              //color for component
              for (int i = ITEM_COL+1; i < colHeads.length; i++) {
                cols = new int[]{i};
                cellColorAtt.setBackground(itemCompColor,rows,cols);
              }
              //set y if item is a kit
              if (itemRows.size() > 1) {
                modelDetail.setValueAt("y",rows[0],ITEM_GROUP_COL);
              }else {
                modelDetail.setValueAt("n",rows[0],ITEM_GROUP_COL);
              }
              itemRows.removeAllElements();
              itemColor = false;
            }else {
              //color for item
              for (int i = ITEM_PART_COL; i <= ITEM_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              //color for component
              for (int i = ITEM_COL+1; i < colHeads.length; i++) {
                cols = new int[]{i};
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              //set y if item is a kit
              if (itemRows.size() > 1) {
                modelDetail.setValueAt("y",rows[0],ITEM_GROUP_COL);
              }else {
                modelDetail.setValueAt("n",rows[0],ITEM_GROUP_COL);
              }
              itemRows.removeAllElements();
              itemColor = true;
            }
            itemRows.addElement(new Integer(row));
            itemRowCount++;
        }else {
          itemRows.addElement(new Integer(row));
        }

        row++;
        lastPartItemGroup = partItemGroup;
        lastPartGroup = partGroup;
      } //end of for

      //clean up part record (last record)
      if (partRows.size() > 0){
        int[] rows = new int[partRows.size()];
            for (int i = 0; i < partRows.size(); i++) {
              Integer r = (Integer)partRows.elementAt(i);
              rows[i] = r.intValue();
            }
            if (partColor) {
              modelDetail.setValueAt("y",rows[0],PART_COLOR_COL);
              for (int i = 0; i < ITEM_PART_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(pColor,rows,cols);
              }
              if (partRows.size() > 1) {
                modelDetail.setValueAt("y",rows[0],GROUP_COL);
              }else {
                modelDetail.setValueAt("n",rows[0],GROUP_COL);
              }
              partRows.removeAllElements();
              partColor = false;
            }else {
              modelDetail.setValueAt("n",rows[0],PART_COLOR_COL);
              for (int i = 0; i < ITEM_PART_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              if (partRows.size() > 1) {
                modelDetail.setValueAt("y",rows[0],GROUP_COL);
              }else {
                modelDetail.setValueAt("n",rows[0],GROUP_COL);
              }
              partRows.removeAllElements();
              partColor = true;
            }
          partRowCount++;
      }
      //clean up item record (last record)
      if (itemRows.size() > 0) {
        int[] rows = new int[itemRows.size()];
            for (int i = 0; i < itemRows.size(); i++) {
              Integer r = (Integer)itemRows.elementAt(i);
              rows[i] = r.intValue();
            }
            if (itemColor) {
              //color for item
              for (int i = ITEM_PART_COL; i <= ITEM_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(itemCompColor,rows,cols);
              }
              //color for component
              for (int i = ITEM_COL+1; i < colHeads.length; i++) {
                cols = new int[]{i};
                cellColorAtt.setBackground(itemCompColor,rows,cols);
              }
              //set y if item is a kit
              if (itemRows.size() > 1) {
                modelDetail.setValueAt("y",rows[0],ITEM_GROUP_COL);
              }else {
                modelDetail.setValueAt("n",rows[0],ITEM_GROUP_COL);
              }
              itemRows.removeAllElements();
              itemColor = false;
            }else {
              //color for item
              for (int i = ITEM_PART_COL; i <= ITEM_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              //color for component
              for (int i = ITEM_COL+1; i < colHeads.length; i++) {
                cols = new int[]{i};
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              //set y if item is a kit
              if (itemRows.size() > 1) {
                modelDetail.setValueAt("y",rows[0],ITEM_GROUP_COL);
              }else {
                modelDetail.setValueAt("n",rows[0],ITEM_GROUP_COL);
              }
              itemRows.removeAllElements();
              itemColor = true;
            }
          itemRowCount++;
      }
    }
    modelDetail.setColumnPrefWidth(colWidths);
    modelDetail.setColumnType(colTypes);
    //long eTime = new java.util.Date().getTime();
    //long min = (eTime-sTime);
    //GregorianCalendar cal = new GregorianCalendar();
    //cal.setTime(new Date(min));
    //System.out.println("Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds");
  }

  protected void buildSearchTableNewDetail() {
    //long sTime = new java.util.Date().getTime();
    partRowCount = 0;
    itemRowCount = 0;
    Hashtable tableData = (Hashtable)searchResultH.get("TABLE_DATA");
    Integer compCount = (Integer)searchResultH.get("COMP_COUNT");
    String[] colHeads = (String[])searchResultH.get("COL_HEADS");
    int[] colTypes = (int[])searchResultH.get("COL_TYPES");
    int[] colWidths = (int[])searchResultH.get("COL_WIDTHS_DETAIL");
    Color pColor = (Color)searchResultH.get("PART_COLOR");
    Color itemCompColor = (Color)searchResultH.get("ITEM_COMP_COLOR");
    Color bulkGasColor = (Color)searchResultH.get("BULK_GAS_COLOR");        //2-05-02
    int rowNum = compCount.intValue();
    modelDetail = new AttributiveCellTableModel(colHeads,rowNum);
    for (int k = rowNum -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    //if there are records
    if (rowNum > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();

      String comment = "";
      String catalogID = "";
      String catalogCompanyId = "";
      String partNo = "";
      String partGroup = "";
      String partDesc = "";
      String partGroupNo = "";
      String type = "";
      String shelfLife = "";
      String basePrice = "";
      String catalogPrice = "";
      String minBuy = "";      //2-26-02
      String examplePkg = "";
      String itemPerPart = "";
      String itemID = "";
      String compDesc = "";
      String packaging = "";
      String grade = "";
      String mfg = "";
      String mpn = "";
      String status = "";
      String msds = "";
      String material = "";
      String avalQty = "";                  //2-05-02
      Integer itemCount = new Integer(0);
      String inventoryGroup = "";
      String sizeUnitOption = "";
      String itemType = "";
      String currencyID = "";
      String createMR = "";
      String baselineReset = "";
      String baselineExpiration = "";
      String inventoryGroupName = "";
      String orderQuantity = "";
      String orderQuantityRule = "";
      String medianLeadTime = "";

      Enumeration enum1 = tableData.keys();
      //******** sort part number
      Vector tmpPartNumber = new Vector();
      while (enum1.hasMoreElements()) {
        tmpPartNumber.addElement(enum1.nextElement().toString());
      }
      tmpPartNumber = BothHelpObjs.sort(tmpPartNumber);
      int rowCount = 0;
      boolean partColorred = true;
      boolean itemColorred = true;
      for (int jk = 0; jk < tmpPartNumber.size(); jk++) {
        Hashtable partH = (Hashtable)tableData.get((String)tmpPartNumber.elementAt(jk));
        basePrice = (String)partH.get("UNIT_PRICE");
        catalogPrice = (String)partH.get("CATALOG_PRICE");
        minBuy = (String)partH.get("MIN_BUY");          //2-26-02
        partDesc = (String)partH.get("PART_DESC");
        partNo = (String)partH.get("PART_NO");
        partGroup = (String)partH.get("PART_GROUP");
        catalogID = (String)partH.get("CATALOG_ID");
        catalogCompanyId = (String)partH.get("CATALOG_COMPANY_ID");
        Integer partGroupCount = (Integer)partH.get("PART_GROUP_COUNT");
        int numOfCompPerPartGroup = 0;
        boolean itemGroup = false;
        for (int i = 0; i < partGroupCount.intValue(); i++) {
          Hashtable partGroupH = (Hashtable)partH.get(partGroup);
          if (partGroupH == null) continue;
          type = (String)partGroupH.get("PART_TYPE");
          partGroupNo = (String)partGroupH.get("PART_GROUP_NO");
          inventoryGroup = (String)partGroupH.get("INVENTORY_GROUP");
          currencyID = (String)partGroupH.get("CURRENCY_ID");
          comment = (String)partGroupH.get("COMMENT");
          itemCount = (Integer)partGroupH.get("ITEM_COUNT");
          for (int j = 0; j < itemCount.intValue(); j++) {
            Hashtable itemH = (Hashtable)partGroupH.get(partGroup+(j+1));
            if (itemH == null) continue;
            itemPerPart = (String)itemH.get("ITEM_PER_PART");
            itemID = (String)itemH.get("ITEM");
            status = (String)itemH.get("STATUS");
            Vector compV = (Vector)itemH.get("COMP_VECTOR");
            itemGroup = compV.size() > 1;
            numOfCompPerPartGroup += compV.size();
            int[] itemRows = new int[compV.size()];
            //look into component vector and get first shelf for item
            Vector tmpCompV = (Vector)compV.firstElement();
            String firstComponentShelfLife = (String)tmpCompV.firstElement();
            boolean componentHasSameShelfLife = true;
            //now go thru the rest of components
            for (int k = 0; k < compV.size(); k++) {
              itemRows[k] = rowCount;
              Object[] oa = new Object[colHeads.length];
              Vector compDV = (Vector)compV.elementAt(k);
              int c = 0;
              shelfLife = (String)compDV.elementAt(c++);
              compDesc = (String)compDV.elementAt(c++);
              examplePkg = (String)compDV.elementAt(c++);
              grade = (String)compDV.elementAt(c++);
              mfg = (String)compDV.elementAt(c++);
              mpn = (String)compDV.elementAt(c++);
              msds = (String)compDV.elementAt(c++);
              material = (String)compDV.elementAt(c++);
              avalQty = (String)compDV.elementAt(c++);
              sizeUnitOption = (String)compDV.elementAt(c++);
              itemType = (String)compDV.elementAt(c++);
              createMR = (String)compDV.elementAt(c++);
              baselineReset = (String)compDV.elementAt(c++);
              baselineExpiration = (String)compDV.elementAt(c++);
              inventoryGroupName = (String)compDV.elementAt(c++);
              orderQuantity = (String)compDV.elementAt(c++);
              orderQuantityRule = (String)compDV.elementAt(c++);
              medianLeadTime = (String)compDV.elementAt(c++);

              //compare shelf life to check whether all component has the same or different shelf life
              if (!firstComponentShelfLife.equalsIgnoreCase(shelfLife)) {
                componentHasSameShelfLife = false;
              }

              //add objects to column
              oa[this.COMMENT_COL] = comment;
              oa[this.PART_GROUP_COL] = partGroupNo;
              oa[this.INVENTORY_GROUP_COL] = inventoryGroup;
              oa[this.CURRENCY_ID_COL] = currencyID;
              oa[this.CATALOG_COL] = catalogID;
              oa[this.CATALOG_COMPANY_COL] = catalogCompanyId;
              if (itemCount.intValue() > 1) {
                oa[this.GROUP_COL] = "y";
              }else {
                oa[this.GROUP_COL] = "n";
              }
              oa[this.PART_COL] = partNo;
              oa[this.DESC_COL] = new JTextArea(partDesc);
              oa[this.TYPE_COL] = type;
              oa[this.BASE_COL] = basePrice;
              oa[this.CATALOG_PRICE_COL] = catalogPrice;
              oa[this.MIN_BUY_COL] = minBuy;          //2-26-02
              oa[this.SHELF_COL] = shelfLife;
              oa[this.ITEM_PART_COL] = itemPerPart;
              oa[this.ITEM_COL] = itemID;
              oa[this.COMPONENT_DESC_COL] = compDesc;
              oa[this.PACKAGING_COL] = examplePkg;
              oa[this.GRADE_COL] = grade;
              oa[this.MANUFACTURER_COL] = mfg;
              oa[this.MPN_COL] = mpn;
              oa[this.STATUS_COL] = status;
              oa[this.MSDS_ONLINE_COL] = msds;
              oa[this.MATERIAL_COL] = material;
              if (itemGroup) {
                oa[this.ITEM_GROUP_COL] = "y";
              }else {
                oa[this.ITEM_GROUP_COL] = "n";
              }
              //12-06-01 create an extra column for part row for each item
              Integer ttmp = new Integer(rowCount);
              oa[this.PART_ITEM_ROW_COL] = ttmp.toString();
              oa[this.AVAL_QTY_COL] = avalQty;
              oa[this.SIZE_UNIT_OPTION_COL] = sizeUnitOption;
              oa[this.ITEM_TYPE_COL] = itemType;
              oa[this.CREATE_MR_FROM_CATALOG_COL] = createMR;
              oa[this.BASELINE_RESET_COL] = baselineReset;
              oa[this.BASELINE_EXPIRATION_COL] = baselineExpiration;
              oa[this.INVENTORY_GROUP_NAME_COL] = inventoryGroupName;
              oa[this.ORDER_QUANTITY_COL] = orderQuantity;
              oa[this.ORDER_QUANTITY_RULE_COL] = orderQuantityRule;
              oa[this.MEDIAN_LEAD_TIME_COL] = medianLeadTime;

              modelDetail.addRow(oa);
              rowCount++;
            }
            //combining item
            if (itemColorred) {
              //don't merge shelf life if each componens contain different shelf life
              int itemStartingPosition = this.ITEM_PART_COL;
              if (!componentHasSameShelfLife) {
                itemStartingPosition = this.ITEM_PART_COL;
              }else {
                //set color
                for (int n = itemStartingPosition; n < this.ITEM_PART_COL; n++) {
                  int[] itemCols = new int[]{n};
                  if (!modelDetail.getValueAt(itemRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                       modelDetail.getValueAt(itemRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                    cellColorAtt.setBackground(bulkGasColor,itemRows,itemCols);
                  }else {
                    cellColorAtt.setBackground(itemCompColor,itemRows,itemCols);
                  }
                }
              }
              //for (int m = this.ITEM_PART_COL; m < this.COMPONENT_DESC_COL; m++) {
              for (int m = itemStartingPosition; m < this.COMPONENT_DESC_COL; m++) {
                int[] itemCols = new int[]{m};
                cellAtt.combine(itemRows,itemCols);
                if (!modelDetail.getValueAt(itemRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                     modelDetail.getValueAt(itemRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                  cellColorAtt.setBackground(bulkGasColor,itemRows,itemCols);
                }else {
                  cellColorAtt.setBackground(itemCompColor,itemRows,itemCols);
                }
              }
              //coloring components
              for (int ii = this.COMPONENT_DESC_COL; ii < this.STATUS_COL; ii++) {
                int[] compCols = new int[]{ii};
                //2-05-02
                if (!modelDetail.getValueAt(itemRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                     modelDetail.getValueAt(itemRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                  cellColorAtt.setBackground(bulkGasColor,itemRows,compCols);
                }else {
                  cellColorAtt.setBackground(itemCompColor,itemRows,compCols);
                }
              }
              //combining and color status
              int[] itemCols = new int[]{this.STATUS_COL};
              cellAtt.combine(itemRows,itemCols);
              //2-05-02
              if (!modelDetail.getValueAt(itemRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                   modelDetail.getValueAt(itemRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                cellColorAtt.setBackground(bulkGasColor,itemRows,itemCols);
              }else {
                cellColorAtt.setBackground(itemCompColor,itemRows,itemCols);
              }

              //12-06-01 figure out what part an item belong to
              int tempRow = itemRows[0];
              Integer myPartItemRow = new Integer(tempRow);
              for (int mm = 1; mm < itemRows.length; mm++) {
                int moreRow = itemRows[mm];
                modelDetail.setValueAt(myPartItemRow.toString(),moreRow,this.PART_ITEM_ROW_COL);
              }

              itemColorred = false;
            }else {
              //don't merge shelf life if each componens contain different shelf life
              int itemStartingPosition = this.ITEM_PART_COL;
              if (!componentHasSameShelfLife) {
                itemStartingPosition = this.ITEM_PART_COL;
              }else {
                //set color
                for (int n = itemStartingPosition; n < this.ITEM_PART_COL; n++) {
                  int[] itemCols = new int[]{n};
                  if (!modelDetail.getValueAt(itemRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                       modelDetail.getValueAt(itemRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                    cellColorAtt.setBackground(bulkGasColor,itemRows,itemCols);
                  }else {
                    cellColorAtt.setBackground(itemCompColor,itemRows,itemCols);
                  }
                }
              }
              //for (int m = this.ITEM_PART_COL; m < this.COMPONENT_DESC_COL; m++) {
              for (int m = itemStartingPosition; m < this.COMPONENT_DESC_COL; m++) {
                int[] itemCols = new int[]{m};
                cellAtt.combine(itemRows,itemCols);
                if (!modelDetail.getValueAt(itemRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                     modelDetail.getValueAt(itemRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                  cellColorAtt.setBackground(bulkGasColor,itemRows,itemCols);
                }else {
                  cellColorAtt.setBackground(new Color(255,255,255),itemRows,itemCols);
                }
              }
              //coloring components
              for (int ii = this.COMPONENT_DESC_COL; ii < this.STATUS_COL; ii++) {
                int[] compCols = new int[]{ii};
                //2-05-02
                if (!modelDetail.getValueAt(itemRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                     modelDetail.getValueAt(itemRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                  cellColorAtt.setBackground(bulkGasColor,itemRows,compCols);
                }else {
                  cellColorAtt.setBackground(new Color(255,255,255),itemRows,compCols);
                }
              }
              //combining and color status and part item row
              int[] itemCols = new int[]{this.STATUS_COL};
              cellAtt.combine(itemRows,itemCols);
              //2-05-02
              if (!modelDetail.getValueAt(itemRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                   modelDetail.getValueAt(itemRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                cellColorAtt.setBackground(bulkGasColor,itemRows,itemCols);
              }else {
                cellColorAtt.setBackground(new Color(255,255,255),itemRows,itemCols);
              }

              //12-06-01 figure out what part an item belong to
              int tempRow = itemRows[0];
              Integer myPartItemRow = new Integer(tempRow);
              for (int mm = 1; mm < itemRows.length; mm++) {
                int moreRow = itemRows[mm];
                modelDetail.setValueAt(myPartItemRow.toString(),moreRow,this.PART_ITEM_ROW_COL);
              }

              itemColorred = true;
            }
          } //end of item count for loop
          //combining and color part
          if (partColorred) {
            int[] partRows = new int[numOfCompPerPartGroup];
            for (int q = 0; q < numOfCompPerPartGroup; q++) {
              partRows[q] = rowCount - (numOfCompPerPartGroup - q);
            }
            modelDetail.setValueAt("y",partRows[0],PART_COLOR_COL);
            for (int w = 0; w < this.ITEM_PART_COL; w++) {
              int[] partCols = new int[]{w};
              cellAtt.combine(partRows,partCols);
              //2-05-02
              if (!modelDetail.getValueAt(partRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                   modelDetail.getValueAt(partRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                cellColorAtt.setBackground(bulkGasColor,partRows,partCols);
              }else {
                cellColorAtt.setBackground(pColor,partRows,partCols);
              }
            }
            partColorred = false;
          }else {
            int[] partRows = new int[numOfCompPerPartGroup];
            for (int q = 0; q < numOfCompPerPartGroup; q++) {
              partRows[q] = rowCount - (numOfCompPerPartGroup - q);
            }
            modelDetail.setValueAt("n",partRows[0],PART_COLOR_COL);
            for (int w = 0; w < this.ITEM_PART_COL; w++) {
              int[] partCols = new int[]{w};
              cellAtt.combine(partRows,partCols);
              //2-05-02
              if (!modelDetail.getValueAt(partRows[0],this.AVAL_QTY_COL).toString().equalsIgnoreCase("0") &&
                   modelDetail.getValueAt(partRows[0],this.STATUS_COL).toString().equalsIgnoreCase("approved")) {
                cellColorAtt.setBackground(bulkGasColor,partRows,partCols);
              }else {
                cellColorAtt.setBackground(new Color(255,255,255),partRows,partCols);
              }
            }
            partColorred = true;
          }
        }
        partRowCount++;
        itemRowCount += itemCount.intValue();
      }
    }
    modelDetail.setColumnPrefWidth(colWidths);
    modelDetail.setColumnType(colTypes);

    /*
    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date(min));
    System.out.println("Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds"+"-start:"+sTime+"+end:"+eTime);
    */
  }

  protected void buildSearchTableNewPart() {
    long sTime = new java.util.Date().getTime();
    partRowCount = 0;
    itemRowCount = 0;
    Hashtable tableData = (Hashtable)searchResultH.get("TABLE_DATA");
    Integer partCount = (Integer)searchResultH.get("PART_COUNT");
    String[] colHeads = (String[])searchResultH.get("COL_HEADS");
    int[] colTypes = (int[])searchResultH.get("COL_TYPES");
    int[] colWidths = (int[])searchResultH.get("COL_WIDTHS");
    Color pColor = (Color)searchResultH.get("PART_COLOR");
    Color itemCompColor = (Color)searchResultH.get("ITEM_COMP_COLOR");
    int rowNum = partCount.intValue();
    modelPart = new AttributiveCellTableModel(colHeads,rowNum);
    for (int k = rowNum -1; k >= 0; k--){
      modelPart.removeRow(k);
    }
    //if there are records
    if (rowNum > 0) {
      CellSpan cellAtt = (CellSpan)modelPart.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelPart.getCellAttribute();

      String partColor = "";
      String comment = "";
      String catalogID = "";
      String catalogCompanyId = "";
      String partNo = "";
      String partGroup = "";
      String partDesc = "";
      String partGroupNo = "";
      String type = "";
      String shelfLife = "";
      String basePrice = "";
      String catalogPrice = "";
      String minBuy = "";         //2-26-02
      String examplePkg = "";
      String itemPerPart = "";
      String itemID = "";
      String status = "";
      Integer itemCount = new Integer(0);
      String inventoryGroup = "";
      String currencyID = "";

      Enumeration enum1 = tableData.keys();
      //******** sort part number
      Vector tmpPartNumber = new Vector();
      while (enum1.hasMoreElements()) {
        tmpPartNumber.addElement(enum1.nextElement().toString());
      }
      tmpPartNumber = BothHelpObjs.sort(tmpPartNumber);

      int count = 0;
      //while (enum1.hasMoreElements()) {
      for (int jk = 0 ; jk < tmpPartNumber.size(); jk++) {
        Object[] oa = new Object[colHeads.length];
        //String key = enum1.nextElement().toString();
        Hashtable partH = (Hashtable)tableData.get((String)tmpPartNumber.elementAt(jk));
        basePrice = (String)partH.get("UNIT_PRICE");
        catalogPrice = (String)partH.get("CATALOG_PRICE");
        minBuy = (String)partH.get("MIN_BUY");
        partDesc = (String)partH.get("PART_DESC");
        shelfLife = (String)partH.get("SHELF_LIFE");
        partNo = (String)partH.get("PART_NO");
        partGroup = (String)partH.get("PART_GROUP");
        catalogID = (String)partH.get("CATALOG_ID");
        catalogCompanyId = (String)partH.get("CATALOG_COMPANY_ID");
        Integer partGroupCount = (Integer)partH.get("PART_GROUP_COUNT");
        boolean itemGroup = false;
        for (int i = 0; i < partGroupCount.intValue(); i++) {
          Hashtable partGroupH = (Hashtable)partH.get(partGroup);
          type = (String)partGroupH.get("PART_TYPE");
          partGroupNo = (String)partGroupH.get("PART_GROUP_NO");
          inventoryGroup = (String)partGroupH.get("INVENTORY_GROUP");
          currencyID = (String)partGroupH.get("CURRENCY_ID");
          comment = (String)partGroupH.get("COMMENT");
          itemCount = (Integer)partGroupH.get("ITEM_COUNT");
          Hashtable itemH = (Hashtable)partGroupH.get(partGroup+1);
          itemPerPart = (String)itemH.get("ITEM_PER_PART");
          itemID = (String)itemH.get("ITEM");
          status = (String)itemH.get("STATUS");
          Vector compV = (Vector)itemH.get("COMP_VECTOR");
          Vector compDV = (Vector)compV.firstElement();
          examplePkg = (String)compDV.elementAt(1);
          itemGroup = compV.size() > 1;
        }
        //add objects to column
        if (count % 2 == 0) {
          oa[PART_COLOR_COL] = "y";
        }else {
          oa[PART_COLOR_COL] = "n";
        }
        oa[this.COMMENT_COL] = comment;
        oa[this.PART_GROUP_COL] = partGroupNo;
        oa[this.INVENTORY_GROUP_COL] = inventoryGroup;
        oa[this.CURRENCY_ID_COL] = currencyID;
        oa[this.CATALOG_COL] = catalogID;
        oa[this.CATALOG_COMPANY_COL] = catalogCompanyId;
        if (itemCount.intValue() > 1) {
          oa[this.GROUP_COL] = "y";
        }else {
          oa[this.GROUP_COL] = "n";
        }
        oa[this.PART_COL] = partNo;
        oa[this.DESC_COL] = new JTextArea(partDesc);

        /*
        StyleContext context = new StyleContext();
        StyledDocument doc = new DefaultStyledDocument(context);
        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style,StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(style,10);
        try {
          doc.insertString(doc.getLength(),partDesc,style);
        }catch(BadLocationException e) {
          e.printStackTrace();
        }
        JTextPane tPane = new JTextPane();
        tPane.setText(partDesc);
        oa[this.DESC_COL] = tPane;  */

        oa[this.TYPE_COL] = type;
        oa[this.SHELF_COL] = shelfLife;
        oa[this.BASE_COL] = basePrice;
        oa[this.CATALOG_PRICE_COL] = catalogPrice;
        oa[this.MIN_BUY_COL] = minBuy;          //2-26-02
        oa[this.ITEM_PART_COL] = itemPerPart;
        oa[this.PACKAGING_COL] = examplePkg;
        oa[this.ITEM_COL] = itemID;
        oa[this.STATUS_COL] = status;
        if (itemGroup) {
          oa[this.ITEM_GROUP_COL] = "y";
          if (itemPerPart.startsWith("Each")) {
            oa[this.EXAMPLE_PACKAGING_COL] = "Kit";
          }else {
            oa[this.EXAMPLE_PACKAGING_COL] = itemPerPart+" x Kit";
          }
        }else {
          oa[this.ITEM_GROUP_COL] = "n";
          if (itemPerPart.startsWith("Each")) {
            oa[this.EXAMPLE_PACKAGING_COL] = examplePkg;
          }else {
            oa[this.EXAMPLE_PACKAGING_COL] = itemPerPart+" x "+examplePkg;
          }
        }
        modelPart.addRow(oa);
        if (count % 2 == 0) {
          int[] rows = new int[]{count};
          int[] cols = new int[colHeads.length];
          for (int k = 0; k < colHeads.length; k++) {
            cols[k] = k;
          }
          cellColorAtt.setBackground(pColor,rows,cols);
        }
        count++;
        partRowCount++;
        itemRowCount += itemCount.intValue();
      }
    }
    modelPart.setColumnPrefWidth(colWidths);
    modelPart.setColumnType(colTypes);
    /*
    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date(min));
    System.out.println("Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds");*/
  }

  protected void searchCatalogNew() {
    //users have to enter some search text if they want to search 'All Catalog'
    if (oFacilityCheck.isSelected()) {
      if (BothHelpObjs.isBlankString(itemText.getText())) {
        GenericDlg err = new GenericDlg(parent.getMain(),"All Catalogs Search",true);
        err.setMsg(" Please limit your search by typing\n something in the search text area.");
        err.show();
        return;
      }
    }

    //global variables
    searchPartCatText = BothHelpObjs.makeBlankFromNull(itemText.getText());
    //searchPartCatFac = (String)facChoice.getSelectedItem();
    //searchPartCatWA = (String)appChoice.getSelectedItem();
    partViewLoaded = false;
    detailLoaded = false;
    //disable until user click on a row
    nspB.setEnabled(false);
    npB.setEnabled(false);
    naB.setEnabled(false);
    ngB.setEnabled(false);

    long sTime = new java.util.Date().getTime();
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CATALOG_TABLE,parent);
    Hashtable query = new Hashtable();
    query.put("ACTION","GET_TABLE_DATA"); //String, String
    query.put("USER_ID",parent.getUserId());  //String, Integer
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      query.put("FACILITY_ID",facChoice.getSelectedFacId());
      query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      if (customerT.getText().length() > 0) {
        query.put("FACILITY_ID",getCustomerFacilityID(posFacC.getSelectedItem().toString()));
        query.put("WORK_AREA", getCustomerWorkAreaID());
      }else {
        query.put("FACILITY_ID",facChoice.getSelectedFacId());
        query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());
      }
      query.put("POS_APPROVED_ONLY",new Boolean(posApprovedForWACheck.isSelected()));
    }
    query.put("CATALOG_TYPE",catalogC.getSelectedItem().toString());
    query.put("ACTIVE_SELECTED",new Boolean(facilityCheck.isSelected()));
    query.put("APPROVED",new Boolean(wAreaCheck.isSelected()));
    query.put("ALL_CATALOG",new Boolean(this.oFacilityCheck.isSelected()));
    query.put("ACTIVE",new Boolean(this.activeCheck.isSelected()));
    query.put("SEARCH_TEXT",BothHelpObjs.makeBlankFromNull(itemText.getText()));
    query.put("CUSTOMER_ID",customerID);
    Hashtable result = cgi.getResultHash(query);
    if (result == null && !parent.getMain().stopped){
      GenericDlg.showAccessDenied(parent.getMain());
      return;
    }

    searchResultH = (Hashtable) result.get("SEARCH_RESULT");

    //column keys
    Hashtable keyCol = (Hashtable) searchResultH.get("KEY_COLUMNS");
    GROUP_COL = ((Integer)keyCol.get("Group")).intValue();
    PART_COL = ((Integer)keyCol.get("Part")).intValue();
    DESC_COL = ((Integer)keyCol.get("Description")).intValue();
    CATALOG_PRICE_COL = ((Integer)keyCol.get("Catalog Price")).intValue();
    BASE_COL = ((Integer)keyCol.get("Unit Price")).intValue();
    MIN_BUY_COL = ((Integer)keyCol.get("Min Buy")).intValue();          //2-26-02
    ITEM_PART_COL = ((Integer)keyCol.get("# per Part")).intValue();
    ITEM_COL = ((Integer)keyCol.get("Item")).intValue();
    PACKAGING_COL = ((Integer)keyCol.get("Packaging")).intValue();
    GRADE_COL = ((Integer)keyCol.get("Grade")).intValue();
    STATUS_COL = ((Integer)keyCol.get("Status")).intValue();
    CATALOG_COL = ((Integer)keyCol.get("Catalog")).intValue();
    CATALOG_COMPANY_COL = ((Integer)keyCol.get("Catalog Company ID")).intValue();
    TYPE_COL = ((Integer)keyCol.get("Type")).intValue();
    MATERIAL_COL = ((Integer)keyCol.get("Material")).intValue();
    MSDS_ONLINE_COL = ((Integer)keyCol.get("MSDS")).intValue();
    PART_GROUP_COL = ((Integer)keyCol.get("Part Group")).intValue();
    INVENTORY_GROUP_COL = ((Integer)keyCol.get("Inventory Group")).intValue();
    CURRENCY_ID_COL = ((Integer)keyCol.get("Currency ID")).intValue();
    ITEM_GROUP_COL = ((Integer)keyCol.get("Item Group")).intValue();
    COMMENT_COL = ((Integer)keyCol.get("Comment")).intValue();
    PART_COLOR_COL = ((Integer)keyCol.get("Part Color")).intValue();                //even those it's set up to change BUT don't change it coz TextCellRender need it to be at the first position
    EXAMPLE_PACKAGING_COL = ((Integer)keyCol.get("Example Packaging")).intValue();
    SHELF_COL = ((Integer)keyCol.get("Shelf Life @ Storage Temp")).intValue();
    COMPONENT_DESC_COL = ((Integer)keyCol.get("Component Description")).intValue();
    MANUFACTURER_COL = ((Integer)keyCol.get("Manufacturer")).intValue();
    MPN_COL = ((Integer)keyCol.get("Mfg Part No.")).intValue();
    this.PART_ITEM_ROW_COL = ((Integer)keyCol.get("Part Item Row")).intValue();
    this.AVAL_QTY_COL = ((Integer)keyCol.get("Bulk Gas")).intValue();             //2-05-02
    SIZE_UNIT_OPTION_COL = ((Integer)keyCol.get("Size Unit Option")).intValue();
    ITEM_TYPE_COL = ((Integer)keyCol.get("Item Type")).intValue();
    CREATE_MR_FROM_CATALOG_COL = ((Integer)keyCol.get("Create MR")).intValue();
    BASELINE_RESET_COL = ((Integer)keyCol.get("Baseline Reset")).intValue();
    BASELINE_EXPIRATION_COL = ((Integer)keyCol.get("Baseline Expiration")).intValue();
    INVENTORY_GROUP_NAME_COL = ((Integer)keyCol.get("Inventory Group Name")).intValue();
    ORDER_QUANTITY_COL = ((Integer)keyCol.get("Order Quantity")).intValue();
    ORDER_QUANTITY_RULE_COL = ((Integer)keyCol.get("Order Quantity Rule")).intValue();
    MEDIAN_LEAD_TIME_COL = ((Integer)keyCol.get("Median Lead Time")).intValue();

    //determine which table to build
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      buildPartCatalogTable();
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      buildPOSCatalogTable();
    }

    searchB.setEnabled(false);
    searchPartCatButtonActive = false;
    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date(min));
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      searchPartCatMsg = "Records Found: "+(itemRowCount)+" Item(s) in "+(partRowCount)+" Part(s) -- Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
      parent.getMain().countLabel.put(new Integer(Main.SEARCHPT),searchPartCatMsg);
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      searchPOSCatMsg = "Records Found: "+(itemRowCount)+" Item(s) in "+(partRowCount)+" Part(s) -- Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
      parent.getMain().countLabel.put(new Integer(Main.SEARCHPT),searchPOSCatMsg);
    }
    parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.SEARCHPT)));
    this.validate();
    panel1.validate();
    stateChanged("Refresh");
    ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    viewC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewC_actionPerformed(e);
      }
    });
  } //end of method

  protected void buildPOSCatalogTable() {
    AttributiveCellTableModel model;
    buildSearchTableNewDetail();
    model = modelDetail;
    tableAttribute = (Hashtable) searchResultH.get("TABLE_ATTRIBUTE");
    //global variables
    String tmpSelWADesc = "";
    if (customerT.getText().length() > 0) {
      searchPOSFacility = getCustomerFacilityID(posFacC.getSelectedItem().toString());
      searchPOSWorkArea = getCustomerWorkAreaID();
      tmpSelWADesc = (String)posWAC.getSelectedItem();
      searchPOSWorkAreaDesc = tmpSelWADesc;
    }else {
      searchPOSFacility = facChoice.getSelectedFacId();
      searchPOSWorkArea = appChoice.getSelectedWorkAreaID();
      tmpSelWADesc = (String)appChoice.getSelectedItem();
      searchPOSWorkAreaDesc = tmpSelWADesc;
    }
    Hashtable tmpInnerH = null;
    if (selectedSearchPOSDataH.containsKey(searchPOSFacility)) {
      tmpInnerH = (Hashtable)selectedSearchPOSDataH.get(searchPOSFacility);
    }else {
      tmpInnerH = new Hashtable(7);
    }
    if (customerT.getText().length() > 0) {
      Vector v = new Vector(catalogC.getItemCount());
      for (int i = 0; i < catalogC.getItemCount(); i++) {
        String selPOS = (String)catalogC.getItemAt(i);
        selPOS = selPOS.substring(selPOS.indexOf(": ")+2);
        v.addElement(selPOS);
      }
      tmpInnerH.put("CLERK_CATALOG_INFO",v);
    }
    tmpInnerH.put("CUSTOMER_NAME",customerT.getText());
    tmpInnerH.put("CUSTOMER_ID",customerID);
    tmpInnerH.put("WORK_AREA",tmpSelWADesc);
    tmpInnerH.put("CATALOG_TYPE",(String)catalogC.getSelectedItem());
    tmpInnerH.put("APPROVED",new Boolean(posApprovedForWACheck.isSelected()));
    tmpInnerH.put("SEARCH_TEXT",BothHelpObjs.makeBlankFromNull(itemText.getText()));
    selectedSearchPOSDataH.put(searchPOSFacility,tmpInnerH);

    if (model == null || model.getRowCount() <= 0) {
      GenericDlg err = new GenericDlg(parent.getMain(),"Not Found",true);
      err.setMsg("No records found.");
      err.show();
      catPOSTable = new MultiSpanCellTable(model);
      catPOSTable.setIntercellSpacing(new Dimension(0,0));
      setPOSTableStyle(model);

      panel1.remove(jScrollPane1);
      panel1.validate();
      jScrollPane1 = new JScrollPane(catPOSTable);
      panel1.add(jScrollPane1, BorderLayout.CENTER);
      catPOSTable.repaint();
      panel1.validate();
    }else {
      catPOSTable = new MultiSpanCellTable(model);
      catPOSTable.addMouseListener(new SearchPT_catPOSTable_mouseAdapter(this));
      catPOSTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
      catPOSTable.getTableHeader().setResizingAllowed(true);

      catPOSTable.setRowHeight(catPOSTable.getRowHeight()*1);
      setPOSTableStyleHighLightRow(model);      //select the entire row

      panel1.remove(jScrollPane1);
      panel1.validate();
      jScrollPane1 = new JScrollPane(catPOSTable);
      panel1.add(jScrollPane1, BorderLayout.CENTER);
      catPOSTable.repaint();
      panel1.validate();
    } //end of else there are data returned from search
  } //end of method

  protected void buildPartCatalogTable() {
    AttributiveCellTableModel model;
    if (viewC.getSelectedItem().toString().equalsIgnoreCase("Detail")) {
      buildSearchTableNewDetail();
      detailLoaded = true;
      model = modelDetail;
    }else {
      buildSearchTableNewPart();
      partViewLoaded = true;
      model = modelPart;
    }

    //global variables
    searchFacility = facChoice.getSelectedFacId();
    searchWorkArea = appChoice.getSelectedWorkAreaID();
    searchWorkAreaDesc = (String)appChoice.getSelectedItem();
    Hashtable tmpInnerH = null;
    if (selectedSearchPartDataH.containsKey(searchFacility)) {
      tmpInnerH = (Hashtable)selectedSearchPartDataH.get(searchFacility);
    }else {
      tmpInnerH = new Hashtable(11);
    }
    tmpInnerH.put("WORK_AREA",(String)appChoice.getSelectedItem());
    tmpInnerH.put("CATALOG_TYPE",(String)catalogC.getSelectedItem());
    tmpInnerH.put("ACTIVE_SELECTED",new Boolean(facilityCheck.isSelected()));
    tmpInnerH.put("APPROVED",new Boolean(wAreaCheck.isSelected()));
    tmpInnerH.put("ALL_CATALOG",new Boolean(this.oFacilityCheck.isSelected()));
    tmpInnerH.put("ACTIVE",new Boolean(this.activeCheck.isSelected()));
    tmpInnerH.put("SEARCH_TEXT",BothHelpObjs.makeBlankFromNull(itemText.getText()));
    selectedSearchPartDataH.put(searchFacility,tmpInnerH);

    tableAttribute = (Hashtable) searchResultH.get("TABLE_ATTRIBUTE");
    if (model == null || model.getRowCount() <= 0) {
      GenericDlg err = new GenericDlg(parent.getMain(),"Not Found",true);
      err.setMsg("No records found.");
      err.show();
      catTableNew = new MultiSpanCellTable(model);
      catTableNew.setIntercellSpacing(new Dimension(0,0));
      setTableStyle(model);

      panel1.remove(jScrollPane1);
      panel1.validate();
      jScrollPane1 = new JScrollPane(catTableNew);
      panel1.add(jScrollPane1, BorderLayout.CENTER);
      catTableNew.repaint();
      panel1.validate();
    }else {
      catTableNew = new MultiSpanCellTable(model);
      catTableNew.addMouseListener(new SearchPT_catTable_mouseAdapter(this));
      catTableNew.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
      catTableNew.getTableHeader().setResizingAllowed(true);

      if (viewC.getSelectedItem().toString().startsWith("Part")) {
        catTableNew.setRowHeight(catTableNew.getRowHeight()*2);
        setPartTableStyleHighLightRow(model);      //select the entire row
      }else {
        setTableStyle(model);     //select only the selected cell
      }

      panel1.remove(jScrollPane1);
      panel1.validate();
      jScrollPane1 = new JScrollPane(catTableNew);
      panel1.add(jScrollPane1, BorderLayout.CENTER);
    } //end of else there are data returned from search
  } //end of method

  protected void setTableStyle(AttributiveCellTableModel model) {
      //control by server
      //cell border
      catTableNew.setIntercellSpacing(new Dimension(0,0));
      Vector borderStyle = (Vector)tableAttribute.get("BORDER_STYLE");
      AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[borderStyle.size()];

      for (int k = 0; k < borderStyle.size(); k++) {
        Hashtable hh = (Hashtable)borderStyle.elementAt(k);
        Color color = (Color)hh.get("BORDER_COLOR");
        Integer t = (Integer)hh.get("TOP_INSETS");
        Integer l = (Integer)hh.get("LEFT_INSETS");
        Integer b = (Integer)hh.get("BOTTOM_INSETS");
        Integer r = (Integer)hh.get("RIGHT_INSETS");
        Integer a = (Integer)hh.get("ALIGN");
        renderers[k] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
      }
      TableColumnModel m = catTableNew.getColumnModel();
      int i = 0;
      for (i = 0; i <= STATUS_COL; i++) {
        if (i == DESC_COL) {
          m.getColumn(i).setCellRenderer(new TextCellRenderSelect());       //10-02-01  testing line wrap on a textArea
        }else if (i < ITEM_PART_COL) {                                  //part border
          m.getColumn(i).setCellRenderer(renderers[0]);
        }else {                                              //item border and component
          m.getColumn(i).setCellRenderer(renderers[1]);
        }
      }

      //font and foreground and background color for columns heading
      String fontName = (String)tableAttribute.get("FONT_NAME");
      Integer fontStyle = (Integer)tableAttribute.get("FONT_STYLE");
      Integer fontSize = (Integer)tableAttribute.get("FONT_SIZE");
      catTableNew.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableAttribute.get("HEADER_FOREGROUND"),(Color)tableAttribute.get("HEADER_BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = catTableNew.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
      }

      // set column widths
      catTableNew.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
      for(i=0;i<catTableNew.getColumnCount();i++){
        int width = model.getColumnWidth(i);
        catTableNew.getColumn(model.getColumnName(i)).setPreferredWidth(width);
        catTableNew.getColumn(model.getColumnName(i)).setWidth(width);
        if (width==0){
          catTableNew.getColumn(model.getColumnName(i)).setMinWidth(width);
          catTableNew.getColumn(model.getColumnName(i)).setMaxWidth(width);
        }
      }
  }

  protected void setPartTableStyleHighLightRow(AttributiveCellTableModel model) {
      //control by server
      //cell border
      catTableNew.setIntercellSpacing(new Dimension(0,0));
      Vector borderStyle = (Vector)tableAttribute.get("BORDER_STYLE");
      AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[borderStyle.size()];

      for (int k = 0; k < borderStyle.size(); k++) {
        Hashtable hh = (Hashtable)borderStyle.elementAt(k);
        Color color = (Color)hh.get("BORDER_COLOR");
        Integer t = (Integer)hh.get("TOP_INSETS");
        Integer l = (Integer)hh.get("LEFT_INSETS");
        Integer b = (Integer)hh.get("BOTTOM_INSETS");
        Integer r = (Integer)hh.get("RIGHT_INSETS");
        Integer a = (Integer)hh.get("ALIGN");
        renderers[k] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
      }
      TableColumnModel m = catTableNew.getColumnModel();
      int i = 0;
      for (i = 0; i <= STATUS_COL; i++) {
        if (i == DESC_COL) {
          m.getColumn(i).setCellRenderer(new TextCellRender());       //10-02-01  testing line wrap on a textArea
        }else if (i < ITEM_PART_COL) {                                  //part border
          m.getColumn(i).setCellRenderer(renderers[0]);
        }else {                                              //item border and component
          m.getColumn(i).setCellRenderer(renderers[1]);
        }
      }

      //font and foreground and background color for columns heading
      String fontName = (String)tableAttribute.get("FONT_NAME");
      Integer fontStyle = (Integer)tableAttribute.get("FONT_STYLE");
      Integer fontSize = (Integer)tableAttribute.get("FONT_SIZE");
      catTableNew.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableAttribute.get("HEADER_FOREGROUND"),(Color)tableAttribute.get("HEADER_BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = catTableNew.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
      }

      // set column widths
      catTableNew.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
      for(i=0;i<catTableNew.getColumnCount();i++){
        int width = model.getColumnWidth(i);
        catTableNew.getColumn(model.getColumnName(i)).setPreferredWidth(width);
        catTableNew.getColumn(model.getColumnName(i)).setWidth(width);
        if (width==0){
          catTableNew.getColumn(model.getColumnName(i)).setMinWidth(width);
          catTableNew.getColumn(model.getColumnName(i)).setMaxWidth(width);
        }
      }
  }  //end of method

  protected void setPOSTableStyle(AttributiveCellTableModel model) {
      //control by server
      //cell border
      catPOSTable.setIntercellSpacing(new Dimension(0,0));
      Vector borderStyle = (Vector)tableAttribute.get("BORDER_STYLE");
      AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[borderStyle.size()];

      for (int k = 0; k < borderStyle.size(); k++) {
        Hashtable hh = (Hashtable)borderStyle.elementAt(k);
        Color color = (Color)hh.get("BORDER_COLOR");
        Integer t = (Integer)hh.get("TOP_INSETS");
        Integer l = (Integer)hh.get("LEFT_INSETS");
        Integer b = (Integer)hh.get("BOTTOM_INSETS");
        Integer r = (Integer)hh.get("RIGHT_INSETS");
        Integer a = (Integer)hh.get("ALIGN");
        renderers[k] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
      }
      TableColumnModel m = catPOSTable.getColumnModel();
      int i = 0;
      for (i = 0; i <= STATUS_COL; i++) {
        if (i == DESC_COL) {
          m.getColumn(i).setCellRenderer(new TextCellRenderSelect());       //10-02-01  testing line wrap on a textArea
        }else if (i < ITEM_PART_COL) {                                  //part border
          m.getColumn(i).setCellRenderer(renderers[0]);
        }else {                                              //item border and component
          m.getColumn(i).setCellRenderer(renderers[1]);
        }
      }

      //font and foreground and background color for columns heading
      String fontName = (String)tableAttribute.get("FONT_NAME");
      Integer fontStyle = (Integer)tableAttribute.get("FONT_STYLE");
      Integer fontSize = (Integer)tableAttribute.get("FONT_SIZE");
      catPOSTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableAttribute.get("HEADER_FOREGROUND"),(Color)tableAttribute.get("HEADER_BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = catPOSTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
      }

      // set column widths
      catPOSTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
      for(i=0;i<catPOSTable.getColumnCount();i++){
        int width = model.getColumnWidth(i);
        catPOSTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
        catPOSTable.getColumn(model.getColumnName(i)).setWidth(width);
        if (width==0){
          catPOSTable.getColumn(model.getColumnName(i)).setMinWidth(width);
          catPOSTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
        }
      }
  } //end of method

  protected void setPOSTableStyleHighLightRow(AttributiveCellTableModel model) {
      //control by server
      //cell border
      catPOSTable.setIntercellSpacing(new Dimension(0,0));
      Vector borderStyle = (Vector)tableAttribute.get("BORDER_STYLE");
      AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[borderStyle.size()];

      for (int k = 0; k < borderStyle.size(); k++) {
        Hashtable hh = (Hashtable)borderStyle.elementAt(k);
        Color color = (Color)hh.get("BORDER_COLOR");
        Integer t = (Integer)hh.get("TOP_INSETS");
        Integer l = (Integer)hh.get("LEFT_INSETS");
        Integer b = (Integer)hh.get("BOTTOM_INSETS");
        Integer r = (Integer)hh.get("RIGHT_INSETS");
        Integer a = (Integer)hh.get("ALIGN");
        renderers[k] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
      }
      TableColumnModel m = catPOSTable.getColumnModel();
      int i = 0;
      for (i = 0; i <= STATUS_COL; i++) {
        if (i == DESC_COL) {
          m.getColumn(i).setCellRenderer(new TextCellRender());       //10-02-01  testing line wrap on a textArea
        }else if (i < ITEM_PART_COL) {                                  //part border
          m.getColumn(i).setCellRenderer(renderers[0]);
        }else {                                              //item border and component
          m.getColumn(i).setCellRenderer(renderers[1]);
        }
      }

      //font and foreground and background color for columns heading
      String fontName = (String)tableAttribute.get("FONT_NAME");
      Integer fontStyle = (Integer)tableAttribute.get("FONT_STYLE");
      Integer fontSize = (Integer)tableAttribute.get("FONT_SIZE");
      catPOSTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableAttribute.get("HEADER_FOREGROUND"),(Color)tableAttribute.get("HEADER_BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = catPOSTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
      }

      // set column widths
      catPOSTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
      for(i=0;i<catPOSTable.getColumnCount();i++){
        int width = model.getColumnWidth(i);
        catPOSTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
        catPOSTable.getColumn(model.getColumnName(i)).setWidth(width);
        if (width==0){
          catPOSTable.getColumn(model.getColumnName(i)).setMinWidth(width);
          catPOSTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
        }
      }
  }  //end of method

  void addRowToTable(MultiSpanCellTable myTable,Object[] data) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    int row = model.getRowCount();
    model.insertRow(row,data);

    int cond = row % 2;
    if (cond == 0) {
      int[] rows = new int[]{row};
      for (int j = 0; j < model.getColumnCount(); j++) {
        int[] cols = new int[]{j};
        cellAtt.setBackground(new Color(224,226,250),rows,cols);
      }
    }
  } //end of method

  String getSelectedWorkAreaID() {
    String result = "";
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      result = appChoice.getSelectedWorkAreaID().toString();
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      result = getCustomerWorkAreaID();
    }
    return result;
  } //end of method

  protected void addBasket(int row, MultiSpanCellTable currentTable) {
    // This stops the editing on the basket table
    // and ensures any values changed are kept even
    // if the user doesn't press ENTER after changing
    if (basketTableNew.isEditing()) {
      basketTableNew.getCellEditor().stopCellEditing();
    }

    String selWorkAreaID = getSelectedWorkAreaID();
    String fpn = (String) currentTable.getModel().getValueAt(row,PART_COL);
    String partGroupNo = (String)currentTable.getModel().getValueAt(row,PART_GROUP_COL);
    String selPartType = (String) currentTable.getModel().getValueAt(row,TYPE_COL);    //1-15-02
    //2-05-02
    if ("BG".equalsIgnoreCase(selPartType) || "SF".equalsIgnoreCase(selPartType) || "TC".equalsIgnoreCase(selPartType)) {
      if (currentTable.getModel().getValueAt(row,this.AVAL_QTY_COL).toString().equalsIgnoreCase("0")) {
        GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
        err.setMsg("The selected part is not available for issue.");
        err.setVisible(true);
        return;
      }
    }
    //Add Row
    for (int i=0;i<basketTableNew.getModel().getRowCount();i++){
       String typeInCart = (String)basketTableNew.getModel().getValueAt(i,this.CART_TYPE_COL);
       //1-15-02 users can't mix service fee with any other part
       if ("SF".equalsIgnoreCase(typeInCart)) {
        if (!selPartType.equals(typeInCart)) {
          GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
          err.setMsg("Service fee cannot be on the same request with other chemicals.");
          err.setVisible(true);
          return;
        }
       }else {
        if ("SF".equalsIgnoreCase(selPartType)) {
          GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
          err.setMsg("Service fee cannot be on the same request with other chemicals.");
          err.setVisible(true);
          return;
        }
       }
       if ("TC".equalsIgnoreCase(typeInCart)) {
        if (!selPartType.equals(typeInCart)) {
          GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
          err.setMsg("Tank lease cannot be on the same request with other chemicals.");
          err.setVisible(true);
          return;
        }
       }else {
        if ("TC".equalsIgnoreCase(selPartType)) {
          GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
          err.setMsg("Tank Lease cannot be on the same request with other chemicals.");
          err.setVisible(true);
          return;
        }
       }
       if ("BG".equalsIgnoreCase(typeInCart)) {
        if (!selPartType.equals(typeInCart)) {
          GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
          err.setMsg("The selected part cannot be on the same request with other chemicals.");
          err.setVisible(true);
          return;
        }
       }else {
        if ("BG".equalsIgnoreCase(selPartType)) {
          GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
          err.setMsg("The selected part cannot be on the same request with other chemicals.");
          err.setVisible(true);
          return;
        }
       }
    }

    Object[] oa = new Object[basketTableNew.getColumnCount()];
    oa[CART_WORK_AREA_COL] = selWorkAreaID;
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      oa[1] = appChoice.getSelectedItem().toString();
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      //oa[1] = posLimitedWAC.getSelectedItem().toString();
      oa[1] = posWAC.getSelectedItem().toString();
    }

    oa[CART_PART_COL] = fpn;
    oa[CART_PART_DESC_COL] = ((JTextArea)currentTable.getModel().getValueAt(row,DESC_COL)).getText();
    //2-05-02
    Double cqty = new Double(1.0);
    if ("BG".equalsIgnoreCase(selPartType) || "SF".equalsIgnoreCase(selPartType) || "TC".equalsIgnoreCase(selPartType)) {
      oa[CART_QTY_COL] = (String) currentTable.getModel().getValueAt(row,this.AVAL_QTY_COL);
      cqty = new Double((String) currentTable.getModel().getValueAt(row,this.AVAL_QTY_COL));
    }else{
      String orderQuantity = (String) currentTable.getModel().getValueAt(row,this.ORDER_QUANTITY_COL);
      if (orderQuantity != null) {
        if (orderQuantity.length() > 0) {
          oa[CART_QTY_COL] = orderQuantity;
          cqty = new Double(orderQuantity);
        }else {
          oa[CART_QTY_COL] = "1";
          cqty = new Double(1.0);
        }
      }else {
        oa[CART_QTY_COL] = "1";
        cqty = new Double(1.0);
      }
    }
    String partType = (String)currentTable.getModel().getValueAt(row,TYPE_COL);

    String catalogPrice = (String)currentTable.getModel().getValueAt(row,CATALOG_PRICE_COL);
    //if facility is an e-com site than users can put part onto the shopping cart without catalog price
    if (facInEComList.contains(facChoice.getSelectedFacId().toString())) {
      if (BothHelpObjs.isBlankString(catalogPrice)) {
        GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
        err.setMsg("Catalog price is missing for the selected part.\nContact tcmIS's customer service representative for more info.");
        err.setVisible(true);
        return;
      }
    }
    //calculate the extended price here
    if (!BothHelpObjs.isBlankString(catalogPrice)) {
      String[] priceInfo = separatePriceFromCurrency(catalogPrice);
      catalogPrice = priceInfo[0];
      Float baseP = new Float(catalogPrice);
      float extP = cqty.floatValue() * baseP.floatValue();
      String minB = BothHelpObjs.makeBlankFromNull((String)currentTable.getModel().getValueAt(row,MIN_BUY_COL));
      if ("OOR".equalsIgnoreCase(partType) && !BothHelpObjs.isBlankString(minB)) {
        Double minBV = new Double(minB);
        if (extP < minBV.doubleValue()) {
          extP = minBV.floatValue();
        }
      }
      BigDecimal amt = new BigDecimal(extP);
      amt = amt.setScale(4,BigDecimal.ROUND_HALF_UP);
      oa[CART_EXT_PRICE_COL] = amt.toString()+priceInfo[1];
    }else {
      oa[CART_EXT_PRICE_COL] = "";
    }

    String itemGroup = (String) currentTable.getModel().getValueAt(row,ITEM_GROUP_COL);
    String bundle = (String) currentTable.getModel().getValueAt(row,ITEM_PART_COL);
    String tmpPKG = "";
    if ("y".equalsIgnoreCase(itemGroup)) {
      if (bundle.startsWith("Each")) {
        tmpPKG = "Kit";
      }else {
        tmpPKG = bundle+" x Kit";
      }
    }else {
      if (bundle.startsWith("Each")) {
        tmpPKG = (String) currentTable.getModel().getValueAt(row,PACKAGING_COL);
      }else {
        tmpPKG = bundle +" x "+ (String)currentTable.getModel().getValueAt(row,PACKAGING_COL);
      }
    }
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      String tmpSizeUnitOption = tmpPKG;
      oa[CART_PACKAGING_COL] = getComboDetail(tmpSizeUnitOption,tmpPKG);
      oa[CART_DISPENSED_UOM_COL] = tmpPKG;
      oa[CART_DISPENSED_ITEM_TYPE_COL] = "";
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      String itemType = (String)currentTable.getModel().getValueAt(row,ITEM_TYPE_COL);
      if ("MD".equalsIgnoreCase(itemType) || "MB".equalsIgnoreCase(itemType)) {
        String tmpSizeUnitOption = (String)currentTable.getModel().getValueAt(row,SIZE_UNIT_OPTION_COL);
        String tmp = tmpPKG.substring(tmpPKG.indexOf(" ")+1);
        String tmpSizeUnit = tmp.substring(0,tmp.indexOf(" "));
        oa[CART_PACKAGING_COL] = getComboDetail(tmpSizeUnitOption,tmpSizeUnit);
        oa[CART_DISPENSED_UOM_COL] = tmpPKG;
      }else {
        String tmpSizeUnitOption = tmpPKG;
        oa[CART_PACKAGING_COL] = getComboDetail(tmpSizeUnitOption,tmpPKG);
        oa[CART_DISPENSED_UOM_COL] = tmpPKG;
      }
      oa[CART_DISPENSED_ITEM_TYPE_COL] = itemType;
    }else {
      oa[CART_PACKAGING_COL] = "";
      oa[CART_DISPENSED_UOM_COL] = "";
      oa[CART_DISPENSED_ITEM_TYPE_COL] = "";
    }


    if (parent.getAribaLogon()) {
      oa[CART_REQUIRED_DATE_COL] = getDefaultRequiredDate(partType);
    }else {
      oa[CART_REQUIRED_DATE_COL] = "";
    }

    String group = (String)currentTable.getModel().getValueAt(row,GROUP_COL);
    if ("y".equalsIgnoreCase(group)) {
      oa[CART_ITEM_COL] = "Various";
    }else {
      oa[CART_ITEM_COL] = currentTable.getModel().getValueAt(row,ITEM_COL).toString();
    }
    oa[CART_FACILITY_COL] = facChoice.getSelectedFacId().toString();
    oa[CART_PR_COL] = "";
    oa[CART_LINE_ITEM_COL] = "";
    oa[CART_CATALOG_COL] = (String)currentTable.getModel().getValueAt(row,CATALOG_COL);
    oa[CART_CATALOG_COMPANY_ID_COL] = (String)currentTable.getModel().getValueAt(row,CATALOG_COMPANY_COL);
    oa[CART_TYPE_COL] = partType;
    if ("y".equalsIgnoreCase(group)) {
      oa[CART_EXAMPLE_ITEM_COL] = (String)currentTable.getModel().getValueAt(row,ITEM_COL);
    }else {
      oa[CART_EXAMPLE_ITEM_COL] = "";
    }
    oa[CART_CATALOG_PRICE_COL] = catalogPrice;
    oa[CART_UNIT_PRICE_COL] = (String)currentTable.getModel().getValueAt(row,BASE_COL);
    oa[CART_MIN_BUY_COL] = BothHelpObjs.makeBlankFromNull((String)currentTable.getModel().getValueAt(row,MIN_BUY_COL));
    oa[CART_PART_GROUP_COL] = partGroupNo;
    oa[CART_INVENTORY_GROUP_COL] = (String)currentTable.getModel().getValueAt(row,INVENTORY_GROUP_COL);
    oa[CART_CURRENCY_ID_COL] = (String)currentTable.getModel().getValueAt(row,CURRENCY_ID_COL);
    oa[CART_NOTE_COL] = "";
    oa[CART_REAL_NOTE_COL] = "";
    oa[CART_CRITICAL_COL] = new Boolean(false);
    oa[CART_ORDER_QUANTITY_RULE_COL] = (String)currentTable.getModel().getValueAt(row,ORDER_QUANTITY_RULE_COL);
    addRowToTable(basketTableNew,oa);

    //block change facilityid
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      facChoice.setEnabled(false);
      catalogInShoppingCart = "Part";
      shoppingCartForUserID = parent.getUserId();
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      posFacC.setEnabled(false);
      customerB.setEnabled(false);
      clearCustomerB.setEnabled(false);
      catalogInShoppingCart = "POS";
      shoppingCartForUserID = customerID;
    }
    addPR.setEnabled(true);
    addPR.requestFocus();
    basketRemove.setEnabled(true);
    //2-27-02
    setCatEditableFlag();
    //check to make sure that customer limit is okay
    if ("y".equalsIgnoreCase(approverRequired)) {
      if (!calculateCostLimit()) {
        return;
      }
    } //end of approver is required
  } //end of method

  String[] separatePriceFromCurrency(String tmpPrice) {
    String[] result = new String[2];
    StringTokenizer st = new StringTokenizer(tmpPrice," ");
    if (st.countTokens() > 1) {
      result[0] = st.nextElement().toString();
      result[1] = " "+st.nextElement().toString();
    }else {
      result[0] = st.nextElement().toString();
      result[1] = "";
    }
    return result;
  } //end of method

  //2-27-02 Allow user to edit qty column if part type is not 'NBO'
  void setCatEditableFlag() {
    for(int i = 0; i < basketTableNew.getModel().getRowCount(); i++) {
      String pType = (String)basketTableNew.getModel().getValueAt(i,this.CART_TYPE_COL);
      String orderQuantityRule = (String)basketTableNew.getModel().getValueAt(i,this.CART_ORDER_QUANTITY_RULE_COL);
      if ("BG".equalsIgnoreCase(pType) || "SF".equalsIgnoreCase(pType) || "TC".equalsIgnoreCase(pType)) {
        //2-27-02 disable editing qty col
        int ed = BothHelpObjs.mypow(2,this.CART_REQUIRED_DATE_COL) + BothHelpObjs.mypow(2,this.CART_CRITICAL_COL);
        catModel.setEditableFlag(ed);

      }else {
        //if this the POS request then allow users to pick dispensing unit
        if ("POS".equalsIgnoreCase(catalogInShoppingCart)) {
          int ed = BothHelpObjs.mypow(2, this.CART_QTY_COL) +
              BothHelpObjs.mypow(2, this.CART_PACKAGING_COL) +
              BothHelpObjs.mypow(2, this.CART_REQUIRED_DATE_COL) +
              BothHelpObjs.mypow(2, this.CART_CRITICAL_COL);
          catModel.setEditableFlag(ed);
        }else {
          int ed = BothHelpObjs.mypow(2, this.CART_QTY_COL) +
                   BothHelpObjs.mypow(2, this.CART_REQUIRED_DATE_COL) +
                   BothHelpObjs.mypow(2, this.CART_CRITICAL_COL);
          catModel.setEditableFlag(ed);
          String key = (new Integer(i)).toString()+(new Integer(this.CART_QTY_COL)).toString();
          if ("Fixed".equalsIgnoreCase(orderQuantityRule)) {
            catModel.addCellNotEditable(key);
          }else {
            //remove cell from list of cells not editable
            catModel.removeCellNotEditable(key);
          }
        }
      }
    }
  } //end of method

  String getDefaultRequiredDate(String partType) {
    Calendar calendar = Calendar.getInstance();
    if(BothHelpObjs.isMinMax(partType)){  // 24 hours
      if (parent.getResourceBundle().get("APP_NAME").toString().equalsIgnoreCase("Seagate")) {
          //I don't need to set anything here because for Seagate the MM could be order and deliver on the same day (Calendar.getInstance())
      }else {
        if (calendar.get(Calendar.HOUR_OF_DAY) < 13) {
          if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ||
            (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            calendar.add(Calendar.DATE,3);
          }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE,2);
          }else {
            calendar.add(Calendar.DATE,1);
          }
        }else {
          if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) ||
            (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
            calendar.add(Calendar.DATE,4);
          }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE,3);
          } else {
            calendar.add(Calendar.DATE,2);
          }
        }
      } //end of not seagate
    } else {     // 21 days
      if (calendar.get(Calendar.HOUR_OF_DAY) < 13) {
        if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ||
            (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
          calendar.add(Calendar.DATE,24);
        }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
          calendar.add(Calendar.DATE,23);
        }else {
          calendar.add(Calendar.DATE,21);
        }
      }else {
        if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) ||
            (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
          calendar.add(Calendar.DATE,25);
        }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
          calendar.add(Calendar.DATE,23);
        } else {
          calendar.add(Calendar.DATE,22);
        }
      }
    }
    String bdate = new String((calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.YEAR));
    return bdate;
  }

  boolean canCreateMR() {
    boolean createMRAllowed = false;
    //if no facility then return false
    if(facChoice == null || facChoice.getItemCount() == 0) return false;
    //select the first facility ID in the list
    if(facChoice.getSelectedIndex() < 0) facChoice.setSelectedIndex(0);

    //if user come from ariba then he/she is allow to create Material Request
    if (facInEComList.contains(facChoice.getSelectedFacId().toString())) {
      if (parent.getAribaLogon()) {
        if (updateMR && ("Production".equalsIgnoreCase(editAccountSysID) || "Production - ERS".equalsIgnoreCase(editAccountSysID))) {
          createMRAllowed = false;
        }else {
          createMRAllowed = true;
        }
      }else if(ecomByPass.booleanValue()) {
        createMRAllowed = true;
      }else {
        createMRAllowed = false;
      }
    }else {
        //createMRAllowed = parent.groupMembership.isGroupMember("GenerateOrders",facChoice.getSelectedFacId());
        createMRAllowed = canCreateMRForApplication();
    }
    return createMRAllowed;
  }

  boolean canCreateMRForApplication() {
    boolean createMRAllowed = false;
    String tmpFacID = facChoice.getSelectedFacId();
     if (canCreateMRAppH.containsKey(tmpFacID)) {
       Vector tmpV = (Vector) canCreateMRAppH.get(tmpFacID);
       if (tmpV.contains(appChoice.getSelectedWorkAreaID())) {
         createMRAllowed = true;
       }else {
         createMRAllowed = false;
       }
     }else {
       createMRAllowed = false;
     }
     return createMRAllowed;
  } //end of method

  protected void setDragWin(String item){
   JLabel l = new JLabel();
   l.setText("+ "+item);
   l.setFont(new Font("Dialog", 0, 9));
   Rectangle rec = l.getBounds();
   rec.height = rec.height - 3;
   rec.width = rec.width - 1;
   l.setBounds(rec);
   dragWin.getContentPane().removeAll();
   dragWin.getContentPane().add(l,BorderLayout.CENTER);
   dragWin.setSize(l.getMinimumSize());
   dragWin.validate();
  }

  Vector buildMRData() {
     Vector mrData = new Vector(4);
    // This stops the editing on the basket table
    // and ensures any values changed are kept even
    // if the user doesn't press ENTER after changing
    //ChangeEvent ce = new ChangeEvent(panel2);
    //basketTableNew.editingStopped(new ChangeEvent(basketTableNew));
    if (basketTableNew.isEditing()) {
      basketTableNew.getCellEditor().stopCellEditing();
    }
    calculateExtendedPrice();
    Hashtable items = new Hashtable();
    Hashtable lineBasket;
    //int test;
    BigDecimal test = new BigDecimal("0");
    String msg;
    if (basketTableNew.getModel().getRowCount()<1){
      String no = new String("Please add part to the basket first.");
      GenericDlg err = new GenericDlg(parent.getMain(),"Missing Data",true);
      err.setMsg(no);
      err.setVisible(true);
      processingData = false;
      mrData.insertElementAt("Error",0);
      return mrData;
    }

    boolean newMR = true;
    String PRNum = "";
    for (int i=0;i<basketTableNew.getModel().getRowCount();i++) {
        lineBasket = new Hashtable();
        lineBasket.put("itemID",(String)basketTableNew.getModel().getValueAt(i,CART_ITEM_COL));
        lineBasket.put("partID",(String)basketTableNew.getModel().getValueAt(i,CART_PART_COL));
        lineBasket.put("application",(String)basketTableNew.getModel().getValueAt(i,CART_WORK_AREA_COL));
        lineBasket.put("type",(String)basketTableNew.getModel().getValueAt(i,CART_TYPE_COL));
        lineBasket.put("catalog",(String)basketTableNew.getModel().getValueAt(i,CART_CATALOG_COL));
        lineBasket.put("catalogCompanyId",(String)basketTableNew.getModel().getValueAt(i,CART_CATALOG_COMPANY_ID_COL));
        lineBasket.put("packaging",((JComboBox) basketTableNew.getModel().getValueAt(i,CART_PACKAGING_COL)).getSelectedItem().toString());
        lineBasket.put("exampleItemID",(String)basketTableNew.getModel().getValueAt(i,CART_EXAMPLE_ITEM_COL));
        //required date
        //make sure that the date is in the right format
        String tmpDate = (String)basketTableNew.getModel().getValueAt(i,CART_REQUIRED_DATE_COL);
        if (tmpDate != null) {
          if (tmpDate.length() > 0) {
            if (!BothHelpObjs.isDateFormatRight(tmpDate)) {
              GenericDlg.showMessage("The date you entered is invalid.\n Please check and re-enter another date\n in the following format: mm/dd/yyyy (i.e. 09/21/2003).");
              processingData = false;
              setSelectedRow(i);
              mrData.insertElementAt("Error",0);
              return mrData;
            }
          }
        }
        lineBasket.put("requiredDate",tmpDate);
        lineBasket.put("catalogPrice",(String)basketTableNew.getModel().getValueAt(i,CART_CATALOG_PRICE_COL));
        lineBasket.put("unitPrice",(String)basketTableNew.getModel().getValueAt(i,CART_UNIT_PRICE_COL));
        lineBasket.put("partGroup",(String)basketTableNew.getModel().getValueAt(i,CART_PART_GROUP_COL));
        lineBasket.put("inventoryGroup",(String)basketTableNew.getModel().getValueAt(i,CART_INVENTORY_GROUP_COL));
        lineBasket.put("note",(String)basketTableNew.getModel().getValueAt(i,CART_REAL_NOTE_COL));
        lineBasket.put("critical",(Boolean)basketTableNew.getModel().getValueAt(i,CART_CRITICAL_COL));

        String pr = (String)basketTableNew.getModel().getValueAt(i,CART_PR_COL);
        lineBasket.put("pr_number",BothHelpObjs.makeBlankFromNull(pr));
        if (!BothHelpObjs.isBlankString(pr)) {           //if the pr column has a string then this is not a new material request
          newMR = false;
          PRNum = pr;
        }
        lineBasket.put("lineItem",(String)basketTableNew.getModel().getValueAt(i,CART_LINE_ITEM_COL));

        try {
           //we want to treat qty as a bigdecimal, but we do not want users to enter floating qty
           test = new BigDecimal(basketTableNew.getModel().getValueAt(i,CART_QTY_COL).toString());
           int test2 = Integer.parseInt(basketTableNew.getModel().getValueAt(i,CART_QTY_COL).toString());
        } catch (Exception e){
           String no = new String("Please enter a whole number for quantity.");
           GenericDlg err = new GenericDlg(parent.getMain(),"Missing Data",true);
           err.setMsg(no);
           err.setVisible(true);
           processingData = false;
           setSelectedRow(i);
           mrData.insertElementAt("Error",0);
           return mrData;
        }
        lineBasket.put("qty",test);
        lineBasket.put("extPrice",(String)basketTableNew.getModel().getValueAt(i,CART_EXT_PRICE_COL));
        lineBasket.put("currencyID",(String)basketTableNew.getModel().getValueAt(i,CART_CURRENCY_ID_COL));
        lineBasket.put("orderQuantityRule",(String)basketTableNew.getModel().getValueAt(i,CART_ORDER_QUANTITY_RULE_COL));
        items.put(new Integer(i),lineBasket);
    }

    //ask user for account sys if this is a new request
    if (newMR) {
      showAccSysDlg();
      this.repaint();
      this.revalidate();
    }
    //everthing okay
    mrData.insertElementAt("Okay",0);
    mrData.insertElementAt(new Boolean(newMR),1);
    mrData.insertElementAt(PRNum,2);
    mrData.insertElementAt(items,3);
    return mrData;
  } //end of method

  Vector buildPOSMRData() {
     Vector mrData = new Vector(4);
    // This stops the editing on the basket table
    // and ensures any values changed are kept even
    // if the user doesn't press ENTER after changing
    //ChangeEvent ce = new ChangeEvent(panel2);
    //basketTableNew.editingStopped(new ChangeEvent(basketTableNew));
    if (basketTableNew.isEditing()) {
      basketTableNew.getCellEditor().stopCellEditing();
    }
    calculateExtendedPrice();
    Hashtable items = new Hashtable();
    Hashtable lineBasket;
    //double test;
    String msg;
    if (basketTableNew.getModel().getRowCount()<1){
      String no = new String("Please add part to the basket first.");
      GenericDlg err = new GenericDlg(parent.getMain(),"Missing Data",true);
      err.setMsg(no);
      err.setVisible(true);
      processingData = false;
      mrData.insertElementAt("Error",0);
      return mrData;
    }

    boolean newMR = true;
    String PRNum = "";
    for (int i=0;i<basketTableNew.getModel().getRowCount();i++) {
        lineBasket = new Hashtable();
        lineBasket.put("itemID",(String)basketTableNew.getModel().getValueAt(i,CART_ITEM_COL));
        lineBasket.put("partID",(String)basketTableNew.getModel().getValueAt(i,CART_PART_COL));
        lineBasket.put("application",(String)basketTableNew.getModel().getValueAt(i,CART_WORK_AREA_COL));
        lineBasket.put("type",(String)basketTableNew.getModel().getValueAt(i,CART_TYPE_COL));
        lineBasket.put("catalog",(String)basketTableNew.getModel().getValueAt(i,CART_CATALOG_COL));
        lineBasket.put("catalogCompanyId",(String)basketTableNew.getModel().getValueAt(i,CART_CATALOG_COMPANY_ID_COL));
        lineBasket.put("packaging",(String)basketTableNew.getModel().getValueAt(i,CART_DISPENSED_UOM_COL));
        lineBasket.put("DISPENSED_UOM",((JComboBox) basketTableNew.getModel().getValueAt(i,CART_PACKAGING_COL)).getSelectedItem().toString());
        lineBasket.put("MR_FOR_POS",new Boolean(true));
        lineBasket.put("exampleItemID",(String)basketTableNew.getModel().getValueAt(i,CART_EXAMPLE_ITEM_COL));
        //required date
        //make sure that the date is in the right format
        String tmpDate = (String)basketTableNew.getModel().getValueAt(i,CART_REQUIRED_DATE_COL);
        if (tmpDate != null) {
          if (tmpDate.length() > 0) {
            if (!BothHelpObjs.isDateFormatRight(tmpDate)) {
              GenericDlg.showMessage("The date you entered for line "+(i+1)+" is invalid.\n Please check and re-enter another date\n in the following format: mm/dd/yyyy (i.e. 09/21/2003).");
              processingData = false;
              setSelectedRow(i);
              mrData.insertElementAt("Error",0);
              return mrData;
            }
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
            try {
              cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
              if ( d.parse(cdate) > d.parse(tmpDate)) {
                 GenericDlg.showMessage("The date you entered for line "+(i+1)+" is invalid.\n Please check and re-enter another date.\n Note: Date Needed can't be in the past.");
                 processingData = false;
                 setSelectedRow(i);
                 mrData.insertElementAt("Error",0);
                 return mrData;
              }
            }catch (Exception e) {
              GenericDlg.showMessage("The date you entered for line "+(i+1)+" is invalid.\n Please check and re-enter another date\n in the following format: mm/dd/yyyy (i.e. 09/21/2003).");
              processingData = false;
              setSelectedRow(i);
              mrData.insertElementAt("Error",0);
              return mrData;
            }
          }else {
            //otherwise default it to today
            Calendar cal = Calendar.getInstance();
            tmpDate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
          }
        }else {
          //otherwise default it to today
          Calendar cal = Calendar.getInstance();
          tmpDate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
        }
        lineBasket.put("requiredDate",tmpDate);
        lineBasket.put("catalogPrice",(String)basketTableNew.getModel().getValueAt(i,CART_CATALOG_PRICE_COL));
        lineBasket.put("unitPrice",(String)basketTableNew.getModel().getValueAt(i,CART_UNIT_PRICE_COL));
        lineBasket.put("partGroup",(String)basketTableNew.getModel().getValueAt(i,CART_PART_GROUP_COL));
        lineBasket.put("inventoryGroup",(String)basketTableNew.getModel().getValueAt(i,CART_INVENTORY_GROUP_COL));
        lineBasket.put("note",(String)basketTableNew.getModel().getValueAt(i,CART_REAL_NOTE_COL));
        lineBasket.put("critical",(Boolean)basketTableNew.getModel().getValueAt(i,CART_CRITICAL_COL));

        String pr = (String)basketTableNew.getModel().getValueAt(i,CART_PR_COL);
        lineBasket.put("pr_number",BothHelpObjs.makeBlankFromNull(pr));
        if (!BothHelpObjs.isBlankString(pr)) {           //if the pr column has a string then this is not a new material request
          newMR = false;
          PRNum = pr;
        }
        lineBasket.put("lineItem",(String)basketTableNew.getModel().getValueAt(i,CART_LINE_ITEM_COL));

        BigDecimal test;
        String itemType = (String)basketTableNew.getModel().getValueAt(i,CART_DISPENSED_ITEM_TYPE_COL);
        try {
           //if MD or MB then it is okay for clerk to enter floating number for qty
           test = new BigDecimal(basketTableNew.getModel().getValueAt(i,CART_QTY_COL).toString());
           if (!"MD".equalsIgnoreCase(itemType) && !"MB".equalsIgnoreCase(itemType)) {
             try {
               //if MA or MP then only accept whole number - not floating number
               int test2 = Integer.parseInt(test.toString());
             }catch (Exception e){
               String no = new String("Please enter a whole number for quantity.");
               GenericDlg err = new GenericDlg(parent.getMain(),"Missing Data",true);
               err.setMsg(no);
               err.setVisible(true);
               processingData = false;
               setSelectedRow(i);
               mrData.insertElementAt("Error",0);
               return mrData;
             }
           }
        } catch (Exception e){
           String no = new String("Please enter a number for quantity.");
           GenericDlg err = new GenericDlg(parent.getMain(),"Missing Data",true);
           err.setMsg(no);
           err.setVisible(true);
           processingData = false;
           setSelectedRow(i);
           mrData.insertElementAt("Error",0);
           return mrData;
        }
        lineBasket.put("DISPENSED_ITEM_TYPE",itemType);
        lineBasket.put("qty",test);
        lineBasket.put("extPrice",(String)basketTableNew.getModel().getValueAt(i,CART_EXT_PRICE_COL));
        lineBasket.put("currencyID",(String)basketTableNew.getModel().getValueAt(i,CART_CURRENCY_ID_COL));
        items.put(new Integer(i),lineBasket);
    }

    //everthing okay
    mrData.insertElementAt("Okay",0);
    mrData.insertElementAt(new Boolean(newMR),1);
    mrData.insertElementAt(PRNum,2);
    mrData.insertElementAt(items,3);
    return mrData;
  } //end of method

  String getPOSChargeNumberInfo() {
    //if selected work area(s) not directed charge then ask user for charge info
    if (basketTableNew.getRowCount() > 0) {
      String tmpWorkArea = (String)basketTableNew.getModel().getValueAt(0,CART_WORK_AREA_COL);
      Vector directedChargeWA = (Vector) posChargeInfoH.get("DIRECTED_CHARGE_WORK_AREA");
      if (!directedChargeWA.contains(tmpWorkArea)) {
        posChargeInfoH.put("DIRECTED_CHARGE",new Boolean(false));
        ChargeNumberDlg cnd = new ChargeNumberDlg(parent.getMain(),searchPOSFacility,"Enter Charge Info.",searchPOSWorkArea,posChargeInfoH);
        cnd.setAccountSysID(mySelectedAccSys);
        cnd.setParent(parent);
        cnd.loadChargeInfo();
        CenterComponent.centerCompOnScreen(cnd);
        cnd.setVisible(true);
        if (!cnd.yesFlag) {
          return "Canceled";
        }
      }else {
        posChargeInfoH.put("DIRECTED_CHARGE",new Boolean(true));
      }
    }else {
      return "No Record";
    }
    return "Okay";
  } //end of method

  void showPOSPickWin(Hashtable pickInfo) {
    pickInfo.put("USER_ID",parent.getUserId());
    pickInfo.put("CUSTOMER_NANE",customerT.getText());
    pickInfo.put("CUSTOMER_ID",customerID);
    pickInfo.put("REQUESTOR_LIMIT",(String)posChargeInfoH.get("CUSTOMER_COST_LIMIT"));
    pickInfo.put("APPROVER_REQUIRED",(String)posChargeInfoH.get("APPROVER_REQUIRED"));
    //get addition info for title
    Vector posPickInfo = (Vector) pickInfo.get("PICK_INFO");
    Hashtable tmpH = (Hashtable) posPickInfo.firstElement();
    String title = (String)tmpH.get("HUB")+" - "+(String)tmpH.get("INVENTORY_GROUP")+":  Pick Information for MR "+(String)pickInfo.get("PR_NUMBER");
    pickInfo.put("HUB",(String)tmpH.get("HUB"));
    pickInfo.put("INVENTORY_GROUP",(String)tmpH.get("INVENTORY_GROUP"));
    POSPickWin dlg = new POSPickWin(parent.getMain(),title,true,pickInfo);
    dlg.setGrandParent(parent);
    dlg.setVisible(true);
    //reset everytime clerk check out
    if ("y".equalsIgnoreCase(approverRequired)) {
      groupBox1.remove(customerLimitT);
      groupBox1.remove(customerLimitL);
    }
    approverRequired = "n";
  } //end of method

  boolean auditPOSSizeUnitOK() {
    boolean result = true;
    for (int i=0;i<basketTableNew.getModel().getRowCount();i++) {
      if ("Select One".equalsIgnoreCase(((JComboBox) basketTableNew.getModel().getValueAt(i,CART_PACKAGING_COL)).getSelectedItem().toString())) {
        GenericDlg.showMessage("Please select a dispensing unit 'Example Packaging' for line item "+(i+1));
        result = false;
        break;
      }
    }
    return result;
  } //end of method

  void buildPOSRequest(){
    processingData = true;
    //long sTime = new java.util.Date().getTime();
    //check to make sure that customer limit is okay
    if ("y".equalsIgnoreCase(approverRequired)) {
      if (!calculateCostLimit()) {
        processingData = false;
        return;
      }
    } //end of approver is required
    //audit Size Unit
    if (!auditPOSSizeUnitOK()) {
      processingData = false;
      return;
    }
    //HERE IS THE LOGIC FOR GET DATA FROM SHOPPING CART
    Vector mrData = buildPOSMRData();
    //do nothing if build data return an error
    if (!"Okay".equalsIgnoreCase((String)mrData.firstElement())) {
      processingData = false;
      return;
    }
    //determine whether work areas use directed charge
    //if not directed then ask user for it
    String posChargeMsg = getPOSChargeNumberInfo();
    //do nothing if get charge number return an error
    if (!"Okay".equalsIgnoreCase(posChargeMsg)) {
      processingData = false;
      return;
    }

    Hashtable items = (Hashtable)mrData.elementAt(3);
    Hashtable result = new Hashtable();
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","BUILD_POS_REQUEST");                                        //String, String
      query.put("USER_ID",parent.getUserId());                                         //String, Integer
      query.put("CUSTOMER_ID",customerID);                                            //String, Integer
      query.put("FACILITY_ID",(String) posChargeInfoH.get("FACILITY_ID"));                  //String, String
      query.put("ACCOUNT_SYS_ID",mySelectedAccSys);                                          //String, String
      query.put("PART_ITEM_DETAIL",items);                                             //String, Hashtable
      boolean drtdChrg = ((Boolean) posChargeInfoH.get("DIRECTED_CHARGE")).booleanValue();
      if (!drtdChrg) {
        query.put("CHARGE_NUMBER",(Hashtable) posChargeInfoH.get("CHARGE_NUMBER"));
        query.put("CHARGE_TYPE",(String) posChargeInfoH.get("CHARGE_TYPE"));
        query.put("ACC_TYPE_H",(Hashtable) posChargeInfoH.get("ACC_TYPE_H"));
        query.put("PACK",(Hashtable) posChargeInfoH.get("PACK"));
        if (posChargeInfoH.containsKey("PO_NUMBER")) {
          query.put("PO_NUMBER",(String) posChargeInfoH.get("PO_NUMBER"));
          query.put("RELEASE_NUMBER",(String) posChargeInfoH.get("RELEASE_NUMBER"));
        }else {
          query.put("PO_NUMBER","");
          query.put("RELEASE_NUMBER","");
        }
      }
      query.put("DIRECTED_CHARGE",new Boolean(drtdChrg));
      result = cgi.getResultHash(query);
      if (result == null){
       GenericDlg.showAccessDenied(parent.getMain());
       processingData = false;
       return;
      }
      //if server ran into error while submitting request
      if (!((Boolean) result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String) result.get("RETURN_MSG"));
        processingData = false;
        return;
      }
      //everything is okay go ahead and remove items from shopping cart
      deleteBasketAll();
      //show pick list
      showPOSPickWin((Hashtable)result.get("PICK_INFO"));
    } catch (Exception e){
      e.printStackTrace();
    }
    processingData = false;
  } //end of method

  void buildRequest(){
    processingData = true;
    //long sTime = new java.util.Date().getTime();

    //HERE IS THE LOGIC FOR GET DATA FROM SHOPPING CART
    boolean newMR = true;
    String PRNum = "";
    String msg;
    Hashtable items = new Hashtable();
    Vector mrData = buildMRData();
    //do nothing if build data return an error
    if (!"Okay".equalsIgnoreCase((String)mrData.firstElement())) {
      processingData = false;
      return;
    }
    newMR = ((Boolean)mrData.elementAt(1)).booleanValue();
    PRNum = (String)mrData.elementAt(2);
    items = (Hashtable)mrData.elementAt(3);


    String myPRNum = "0";
    String myMsg = "";
    Hashtable result = new Hashtable();
    CheckoutDlg chkoutDlg = null;
    boolean requiredMRScreen = true;
    try {
      if (parent.getAribaLogon()) {
        parent.startCheckoutExitWaitDlg("Saving Data...","Saving Data...");
      }

      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","BUILD_REQUEST");                //String, String
      query.put("USERID",parent.getUserId());             //String, Integer
      query.put("FACID",facChoice.getSelectedFacId());    //String, String
      query.put("NEW_MR",new Boolean(newMR));             //String, Boolean
      query.put("PR_NUMBER",PRNum);                       //String, String
      //trong 4/8
      query.put("ACC_SYS",mySelectedAccSys);              //String, String
      query.put("PAY_LOAD_ID",parent.getPayLoadID());     //String, String
      query.put("ARIBA_LOGON",new Boolean(parent.getAribaLogon()));   //String, Boolean
      query.put("ECOM_BY_PASS",ecomByPass);       //String, Boolean

      query.put("PART_ITEM_DETAIL",items);
      result = cgi.getResultHash(query);

      if (result == null){
        //parent.getMain().stopImage();
        if (parent.getAribaLogon()) {
          parent.stopCheckoutExitWaitDlg();
        }
       GenericDlg.showAccessDenied(parent.getMain());
       processingData = false;
       return;
      }

      if (parent.getAribaLogon()) {
        parent.stopCheckoutExitWaitDlg();
        //2-06-02 if there a problem then display msg and stop
        Boolean b = (Boolean)result.get("RETURN_CODE");
        if (!b.booleanValue()) {
          GenericDlg gd = new GenericDlg(parent.getMain(),"Error",true);
          gd.setMsg((String)result.get("RETURN_MSG"));
          gd.setVisible(true);
          processingData = false;
          return;
        }
      }

      Vector vvv = (Vector) result.get("REQUEST_RETURN");
      myPRNum = (String)vvv.elementAt(0);
      myMsg = (String)vvv.elementAt(1);
      msg = myMsg;
      requiredMRScreen = ((Boolean)result.get("REQUIRED_MR_SCREEN")).booleanValue();
    } catch (Exception e){
      msg = e.getMessage();
    }

    if (!"0".equals(myPRNum)) {
      //empty the basket
      deleteBasketAll();
      //call request window
      Integer temp = new Integer(myPRNum);
      //show MR screen if facility and account sys required MR screen
      if (requiredMRScreen) {
        parent.getMain().goRequest(0, temp.intValue(), (Hashtable) result.get("REQUEST_DATA"));
      }else {
        //if login from ariba then don't display MR screen
        if (parent.getAribaLogon()) {
          Boolean postingMsg = (Boolean) result.get("POSTING_MSG");
          if (postingMsg.booleanValue()) {
            //sendPost message to their server
            try {
              boolean comeFromOracle = false;
              if (result.containsKey("COME_FROM_ORACLE")) {
                comeFromOracle = ( (Boolean) result.get("COME_FROM_ORACLE")).booleanValue();
              }
              if (!comeFromOracle) {
                parent.startCheckoutExitWaitDlg("Returning to Ariba...", "Returning to Ariba...");
                int count = 0;
                ClientHelpObjs clientHelpObjs = new ClientHelpObjs();
                Hashtable postingV = clientHelpObjs.punchOutSendPostWriteToFile( (String) result.get("URL"), (String) result.get("XML_DOC"), "application/x-www-form-urlencoded", "PunchOut.html", this, true); // the boolean parameter - tell us wether this is a first time in or not
                Integer rspcode = (Integer) postingV.get("RESPONSE_CODE");
                while (count < 5) {
                  if ( (rspcode.intValue() == 301 || rspcode.intValue() == 302)) {
                    postingV = clientHelpObjs.punchOutSendPostWriteToFile( (String) postingV.get("URL"), (String) result.get("XML_DOC"), "application/x-www-form-urlencoded", "PunchOut.html", this, false); // the boolean parameter - tell us wether this is a first time in or not
                    rspcode = (Integer) postingV.get("RESPONSE_CODE");
                    count++;
                  } else {
                    break;
                  }
                }
                new URLCall( (String) postingV.get("FILE"), parent, true);
              }
            } catch (Exception ee) {
              ee.printStackTrace();
              GenericDlg gd = new GenericDlg(parent.getMain(), "Error", true);
              gd.setMsg("Problem occurred while posting message to server.");
              gd.setVisible(true);
            }
          } //end of posting msg
          //unset ariba flag and close tcmIS
          parent.setAribaLogon(false);
          parent.exit();
        } else if (ecomByPass.booleanValue()) {
          GenericDlg gd = new GenericDlg(parent.getMain(), "Notes", true);
          gd.setMsg("Your tcmIS request ID is " + myPRNum + ".\nRemember to go back and resubmit this request ID into\nyour E-Commerce system once it is operational again.");
          gd.setVisible(true);
        }
      } //end of MR screen is not required
    } //end of prNum is not 0
    processingData = false;
  } //end of method

  public void sendAribaResponseCodeToServer(int rspcode) throws Exception {
     try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",parent);
      Hashtable query = new Hashtable();
      query.put("USER_ID",parent.getUserId().toString());
      query.put("ACTION","LOG_PUNCHOUT");
      query.put("PAY_LOAD_ID",parent.getPayLoadID());
      query.put("RESPONSE_CODE",new Integer(rspcode));

      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(parent.getMain());
      }
     }catch(Exception e){
      throw e;
     }
  }

  void setReportFields() {
    if(oFacilityCheck.isSelected()) {
      reportFac = "All Facilities";
      reportWorkArea = "All Work Areas";
    }else{
      reportFac = facChoice.getSelectedItem().toString();
      reportWorkArea = appChoice.getSelectedItem().toString();
    }
    reportName = new String(userName.getText());
    String I = itemText.getText();
    if(BothHelpObjs.isBlankString(I)) {
      reportSearch = "";
    }else{
      reportSearch = new String("All Items Containing '" + I + "'");
    }
  }

  boolean auditAddToBasket(int cell) {
    boolean result = false;
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      result = partAuditAddToBasket(cell);
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      result = posAuditAddToBasket(cell);
    }
    return result;
  } //end of method

  void reSetPOSWorkAreaDropDown(Vector directedChargeWA) {
    //if work area uses directed charge then remove all work areas that do not use
    //directed charge and vise versa
    int selectedIndex = posWAC.getSelectedIndex();
    String fac = getCustomerFacilityID(posFacC.getSelectedItem().toString());
    Hashtable app = (Hashtable) posCustFacWaInfo.get(fac);
    Vector waDescV = (Vector)app.get("APPLICATION_DESC");
    Vector waIDV = (Vector)app.get("APPLICATION");
    boolean directedCharge = directedChargeWA.contains(searchPOSWorkArea);
    Object[] oa = new Object[waIDV.size()];
    for (int i = 0; i < waIDV.size(); i++) {
      if (directedCharge) {
        if (directedChargeWA.contains(waIDV.elementAt(i).toString())) {
          oa[i] = new ComboBoxCanEnableItem(waDescV.elementAt(i).toString(),true);
        }else {
          oa[i] = new ComboBoxCanEnableItem(waDescV.elementAt(i).toString(),false);
        }
      }else {
        if (directedChargeWA.contains(waIDV.elementAt(i).toString())) {
          oa[i] = new ComboBoxCanEnableItem(waDescV.elementAt(i).toString(),false);
        }else {
          oa[i] = new ComboBoxCanEnableItem(waDescV.elementAt(i).toString(),true);
        }
      }
    }
    posWAC.setVisible(false);
    posLimitedWAC = new JComboBox(oa);
    posLimitedWAC.setRenderer(new ComboBoxEnableRenderer());
    //posLimitedWAC.addActionListener(new ComboBoxEnableListener(posWAC));
    posLimitedWAC.setSelectedIndex(selectedIndex);
    groupBox1.remove(posWAC);
    posLimitedWAC.setVisible(true);
    posLimitedWAC.setForeground(posFacC.getForeground());
    posLimitedWAC.setFont(posFacC.getFont());
    groupBox1.add(posLimitedWAC, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
    groupBox1.validate();
  } //end of method

  boolean getPOSChargeInfo() {
    boolean result = false;
    //first ask user which accounting system to use if facility
    //contains more than one
    showAccSysDlg();
    //next ask user for charge info (if not directed)
    try {
      parent.startCheckoutExitWaitDlg("Loading Data...","Loading Data...");
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew", parent);
      Hashtable query = new Hashtable();
      query.put("ACTION", "GET_POS_CHARGE_INFO"); //String, String
      query.put("CUSTOMER_ID",customerID);
      query.put("FACILITY_ID", this.searchPOSFacility); //String, String
      query.put("WORK_AREA_ID", this.searchPOSWorkArea);
      query.put("ACCOUNT_SYS_ID", this.mySelectedAccSys);
      Hashtable dataH = cgi.getResultHash(query);
      if (dataH == null) {
        GenericDlg.showAccessDenied(parent.getMain());
        parent.stopCheckoutExitWaitDlg();
        return false;
      }
      if (!((Boolean)dataH.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)dataH.get("RETURN_MSG"));
        parent.stopCheckoutExitWaitDlg();
        return false;
      }
      posChargeInfoH = (Hashtable) dataH.get("POS_CHARGE_INFO");
      //set cost limit
      approverRequired = (String)posChargeInfoH.get("APPROVER_REQUIRED");
      if ("y".equalsIgnoreCase(approverRequired)) {
        customerLimitL.setText("Balance: ");
        customerLimitT.setText((String)posChargeInfoH.get("CUSTOMER_COST_LIMIT"));
        groupBox1.add(customerLimitL, new GridBagConstraints2(1, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 120, 2, 0), 0, 0));
        groupBox1.add(customerLimitT, new GridBagConstraints2(1, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 180, 2, 0), 0, 0));
        groupBox1.validate();
      }
      Vector directedChargeWA = (Vector) posChargeInfoH.get("DIRECTED_CHARGE_WORK_AREA");
      if (directedChargeWA.size() > 0 ) {
        reSetPOSWorkAreaDropDown(directedChargeWA);
      }
      parent.stopCheckoutExitWaitDlg();
      result = true;
    }catch (Exception e) {
      e.printStackTrace();
      parent.stopCheckoutExitWaitDlg();
      result = false;
    }
    this.repaint();
    this.revalidate();
    return result;
  }


  boolean posAuditAddToBasket(int cell) {
    boolean result = true;
    GenericDlg err;
    if (catalogInShoppingCart.length() > 0) {
      if ("Part".equalsIgnoreCase(catalogInShoppingCart)) {
        err = new GenericDlg(parent.getMain(),"Warning",true);
        err.setMsg("Shopping cart contains Part Catalog material(s).\n\nPlease clear Customer and select Part Catalog...\nor\nDelete data from shopping cart and try again.");
        err.setVisible(true);
        return false;
      }else {
        if (shoppingCartForUserID.intValue() != 0) {
          if (shoppingCartForUserID.intValue() != customerID.intValue()) {
            err = new GenericDlg(parent.getMain(),"Warning",true);
            err.setMsg("Shopping cart contains POS material(s) another customer.\nPlease delete data from shopping cart and try again.");
            err.setVisible(true);
            return false;
          }
        }
      }
    }

    //customer is fill in
    if (customerID.intValue() == 0) {
      err = new GenericDlg(parent.getMain(),"",true);
      err.setMsg("Please see your "+catalogC.getSelectedItem().toString()+" dispenser for these parts.");
      err.setVisible(true);
      return false;
    }
    //part is approved for selected facility and work area
    if (!"approved".equalsIgnoreCase(((String) catPOSTable.getModel().getValueAt(catSel,STATUS_COL)))) {
      err = new GenericDlg(parent.getMain(),"Not approved",true);
      err.setMsg("This part is not approved for this application");
      err.setVisible(true);
      return false;
    }

    if (!(searchPOSWorkArea.equals(getCustomerWorkAreaID()))) {
        err = new GenericDlg(parent.getMain(),"Unknown Work Area and approval",true);
        err.setMsg("Please select a Work Area and click Search again.");
        err.setVisible(true);
        return false;
    }
    // change!!
    if (!(searchPOSFacility.equals(getCustomerFacilityID(posFacC.getSelectedItem().toString())))) {
        err = new GenericDlg(parent.getMain(),"Unknown Facility, Work Area and approval",true);
        err.setMsg("Please select a Facility, Work Area and click Search again.");
        err.setVisible(true);
        return false;
    }

    //ask for accounting system
    //ask user for account sys if this is a new request
    if (basketTableNew.getRowCount() < 1) {
      getPOSChargeInfo();
      this.repaint();
      this.revalidate();
    }
    return result;
  } //end of method

  boolean calculateCostLimit() {
    String tmpLimit = (String)posChargeInfoH.get("CUSTOMER_COST_LIMIT");
    String[] priceInfo = separatePriceFromCurrency(tmpLimit);
    tmpLimit = priceInfo[0];
    GenericDlg err;
    boolean result = true;
    if (!"Unlimited".equalsIgnoreCase(tmpLimit) && !"-1".equalsIgnoreCase(tmpLimit)) {
      try {
        float remainder = Float.parseFloat(tmpLimit);
        float catalogPrice = 0;
        //first stop editing on table
        if (basketTableNew.isEditing()) {
          basketTableNew.getCellEditor().stopCellEditing();
        }
        for(int i = 0;i < basketTableNew.getRowCount();i++) {
          String tmpCatalogPrice = (String)basketTableNew.getModel().getValueAt(i,CART_CATALOG_PRICE_COL);
          if (!BothHelpObjs.isBlankString(tmpCatalogPrice)) {
            catalogPrice += Float.parseFloat(tmpCatalogPrice) * Integer.parseInt((String)basketTableNew.getModel().getValueAt(i,CART_QTY_COL));
          }
        }
        remainder = remainder - catalogPrice;
        if ( remainder < 0) {
          err = new GenericDlg(parent.getMain(),"Exceeded Cost Limit",true);
          err.setMsg("You have exceeded customer cost limit.\nPlease redo qty/remove item from shopping cart.");
          err.setVisible(true);
          result = false;
        }
        BigDecimal amt = new BigDecimal(remainder);
        amt = amt.setScale(4,BigDecimal.ROUND_HALF_UP);
        customerLimitT.setText(amt.toString()+priceInfo[1]);
      }catch (Exception e) {
        err = new GenericDlg(parent.getMain(),"Error",true);
        err.setMsg("An error occurred while trying to calculate balance.\nPlease refresh screen and try again.");
        err.setVisible(true);
        result = false;
      }
    } //end of user have some dollar limit
    return result;
  } //end of method

  boolean partAuditAddToBasket(int cell){
    if(!canCreateMR()) {
      return false;
    }
    GenericDlg err;
    if (catalogInShoppingCart.length() > 0) {
      if ("POS".equalsIgnoreCase(catalogInShoppingCart)) {
        err = new GenericDlg(parent.getMain(),"Warning",true);
        err.setMsg("Shopping cart contains POS material(s).\nPlease select POS catalog or\ndelete data from shopping cart and try again.");
        err.setVisible(true);
        return false;
      }
    }
    if (!(searchWorkArea.equals(appChoice.getSelectedWorkAreaID()))) {
        err = new GenericDlg(parent.getMain(),"Unknown Work Area and approval",true);
        err.setMsg("Please select a Work Area and click Search again.");
        err.setVisible(true);
        return false;
    }
    // change!!
    if (!(searchFacility.equals(facChoice.getSelectedFacId()))) {
        err = new GenericDlg(parent.getMain(),"Unknown Facility, Work Area and approval",true);
        err.setMsg("Please select a Facility, Work Area and click Search again.");
        err.setVisible(true);
        return false;
    }
    //checking the status
    if ((((String) catTableNew.getModel().getValueAt(catSel,STATUS_COL)).equalsIgnoreCase("approved") || ((String) catTableNew.getModel().getValueAt(catSel,STATUS_COL)).startsWith("All"))
          && "Y".equalsIgnoreCase((String)catTableNew.getModel().getValueAt(catSel,CREATE_MR_FROM_CATALOG_COL))) {
      //checking the user comments
      String comment = (String)catTableNew.getModel().getValueAt(catSel,this.COMMENT_COL);
      if (!BothHelpObjs.isBlankString(comment)) {
        ConfirmNewDlg md = new ConfirmNewDlg(parent.getMain(),"Comment",true);
        md.setMsg(comment);
        md.setVisible(true);
        if(md.getConfirmationFlag()){
          return true;
        }else {
          return false;
        }
      }else {
        return true;
      }
    }else if ((((String) catTableNew.getModel().getValueAt(catSel,STATUS_COL)).equalsIgnoreCase("approved") || ((String) catTableNew.getModel().getValueAt(catSel,STATUS_COL)).startsWith("All"))
          && "N".equalsIgnoreCase((String)catTableNew.getModel().getValueAt(catSel,CREATE_MR_FROM_CATALOG_COL))) {
       err = new GenericDlg(parent.getMain(),"Cannot order",true);
       err.setMsg("You cannot order this part through this method.");
       err.setVisible(true);
       return false;
    }else {
       err = new GenericDlg(parent.getMain(),"Not approved",true);
       err.setMsg("This part is not approved for this application");
       err.setVisible(true);
       return false;
    }
  } //end of method

  public void stateChanged(String label){
   // approve status changed, let's change label

    if(label.equalsIgnoreCase("refresh") &&
      searchB.getText().equalsIgnoreCase("search")) {
      return;
    }
   searchB.setText(label);
    /*3-19-02
   if (label.equals("Loading ...")){
       parent.getMain().startImage();
   } else {
       try {
           parent.getMain().stopImage();
       } catch (Exception e){};
   }   */
  }

  void deleteBasketAll() {
    AttributiveCellTableModel model = (AttributiveCellTableModel)basketTableNew.getModel();
    while(basketTableNew.getRowCount() > 0) {
      model.removeRow(0);
    }
    // allow facility choice again
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      facChoice.setEnabled(true);
      facChoice.validate();
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      posFacC.setEnabled(true);
      customerB.setEnabled(true);
      clearCustomerB.setEnabled(customerID.intValue() != 0);
      posLimitedWAC.setVisible(false);
      groupBox1.remove(posLimitedWAC);
      posWAC.setVisible(true);
      groupBox1.add(posWAC, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
      if ("y".equalsIgnoreCase(approverRequired)) {
        groupBox1.remove(customerLimitT);
        groupBox1.remove(customerLimitL);
      }
      groupBox1.validate();
    }
    addPR.setEnabled(false);
    basketRemove.setEnabled(false);
    catalogInShoppingCart = "";
    shoppingCartForUserID = new Integer(0);
  }

  void deleteBasket(){
    if (basketTableNew.isEditing()) {
      basketTableNew.getCellEditor().stopCellEditing();
    }
    AttributiveCellTableModel model = (AttributiveCellTableModel)basketTableNew.getModel();
    int x = basketTableNew.getSelectedRow();
    model.removeRow(x);
    //remove cell from list of cells not editable
    String key = (new Integer(x)).toString()+(new Integer(this.CART_QTY_COL)).toString();
    catModel.removeCellNotEditable(key);
    // allow facility choice again
    if (basketTableNew.getModel().getRowCount() <= 0){
      if (catalogC.getSelectedItem().toString().startsWith("Part")) {
        facChoice.setEnabled(true);
        facChoice.validate();
      }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
        posFacC.setEnabled(true);
        customerB.setEnabled(true);
        clearCustomerB.setEnabled(customerID.intValue() != 0);
        posLimitedWAC.setVisible(false);
        groupBox1.remove(posLimitedWAC);
        posWAC.setVisible(true);
        groupBox1.add(posWAC, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
        if ("y".equalsIgnoreCase(approverRequired)) {
          groupBox1.remove(customerLimitT);
          groupBox1.remove(customerLimitL);
        }
        groupBox1.validate();
      }
      addPR.setEnabled(false);
      basketRemove.setEnabled(false);
      catalogInShoppingCart = "";
      shoppingCartForUserID = new Integer(0);
    }
  }

  void goNewMaterial(){
    String reqid="0";
    if (appChoice.getSelectedItem().toString().equalsIgnoreCase("Select a Work Area")
        && catAddFacSingleAppList.contains(facChoice.getSelectedFacId())) {
       GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
       err.setMsg("Please select a work area.");
       err.setVisible(true);
       return;
    }

    Hashtable result = null;
    try {
      //parent.getMain().startImage();
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","BUILD_NEW_MATERIAL"); //String, String
      query.put("USER_ID",parent.getUserId().toString());
      query.put("CAT_ID",facChoice.getSelectedFacId());
      query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());     //12-08-01
      query.put("IS_EVAL",new Boolean(eval.equalsIgnoreCase("evaluation")));
      query.put("CATALOG_ID","");
      query.put("CATALOG_COMPANY_ID","");
      if (eval.equalsIgnoreCase("evaluation")){
        query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());
        query.put("EVAL_TYPE",new String("NewEval"));
        query.put("ACC_SYS_ID",mySelectedAccSys);
      }
      result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      if (result.containsKey("MESSAGE")){ // error
        GenericDlg.showMessage((String) result.get("MESSAGE") );
        //parent.getMain().stopImage();
        return;
      }
      reqid = (String) result.get("REQ_ID");
      //parent.getMain().stopImage();
    } catch (Exception e){
      e.printStackTrace();
      GenericDlg.showMessage( "Error:"+e.getMessage() );
    }

    if (eval.equalsIgnoreCase("evaluation")) {
      //GenericDlg.showMessage( "Engineering Evaluation Request created." );
      parent.getMain().setNewChemEval("newEval");
      eval = new String("normal");
    } else {
      //GenericDlg.showMessage( "New Material Request created." );
      parent.getMain().setNewChemEval("normal");
    }
    parent.getMain().goNewChemMat(0,Integer.parseInt(reqid),result);
  }

  void goNewSizePacking(){
    if (!checkSelectedTable()) return;

    String reqid="0";
    Hashtable result = null;
    try {
      //parent.getMain().startImage();
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","BUILD_NEW_SIZE"); //String, String
      query.put("USER_ID",parent.getUserId().toString());
      if (catalogC.getSelectedItem().toString().startsWith("Part")) {
        query.put("ITEM_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),ITEM_COL).toString());
        query.put("CATALOG_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),CATALOG_COL).toString());
        query.put("CATALOG_COMPANY_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),CATALOG_COMPANY_COL).toString());
      }else if (catalogC.getSelectedItem().toString().startsWith("Item")) {
        query.put("ITEM_ID",itemCatalogTable.getModel().getValueAt(itemCatalogTable.getSelectedRow(),0).toString());
        query.put("CATALOG_ID","");
        query.put("CATALOG_COMPANY_ID","");
      }
      query.put("CAT_ID",facChoice.getSelectedFacId());
      query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());     //12-08-01
      query.put("IS_EVAL",new Boolean(false));   //trong
      result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      if (result.containsKey("MESSAGE")){ // error
        GenericDlg.showMessage((String) result.get("MESSAGE") );
        //parent.getMain().stopImage();
        return;
      }
      reqid = (String) result.get("REQ_ID");
      //parent.getMain().stopImage();
    } catch (Exception e){
      e.printStackTrace();
      GenericDlg.showMessage( "Error:"+e.getMessage() );
    }
    //GenericDlg.showMessage( "New Size/Packing Request created." );
    parent.getMain().goNewChemMat(0,Integer.parseInt(reqid),result);
  }

  void goNewPart(){
    if (!checkSelectedTable()) return;

    String reqid="0";
    Hashtable result = null;
    try {
      //parent.getMain().startImage();

      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","BUILD_NEW_PART"); //String, String
      query.put("USER_ID",parent.getUserId().toString());
      if (catalogC.getSelectedItem().toString().startsWith("Part")) {
        query.put("ITEM_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),ITEM_COL).toString());
        query.put("CATALOG_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),CATALOG_COL).toString());
        query.put("CATALOG_COMPANY_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),CATALOG_COMPANY_COL).toString());
      }else if (catalogC.getSelectedItem().toString().startsWith("Item")) {
        query.put("ITEM_ID",itemCatalogTable.getModel().getValueAt(itemCatalogTable.getSelectedRow(),0).toString());
        query.put("CATALOG_ID","");
        query.put("CATALOG_COMPANY_ID","");
      }
      query.put("CAT_ID",facChoice.getSelectedFacId());
      query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());     //12-08-01
      query.put("EVAL_TYPE",actionEval);
      query.put("IS_EVAL",new Boolean(eval.equalsIgnoreCase("evaluation")));
      /*
      if (actionEval.equalsIgnoreCase("convertEval") || actionEval.equalsIgnoreCase("oldEval")){
        query.put("REQ_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),6));
      } */
      if (eval.equalsIgnoreCase("evaluation")){
        query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());
        if (!actionEval.equalsIgnoreCase("convertEval")){
          query.put("ACC_SYS_ID",mySelectedAccSys);
        }
      }

      result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      if (result.containsKey("MESSAGE")){ // error
        GenericDlg.showMessage((String) result.get("MESSAGE") );
        //parent.getMain().stopImage();
        return;
      }
      reqid = (String) result.get("REQ_ID");
      //parent.getMain().stopImage();
    } catch (Exception e){
      e.printStackTrace();
      GenericDlg.showMessage( "Error:"+e.getMessage() );
    }
    //trong 3/21
    if (actionEval.equalsIgnoreCase("convertEval")) {
      //GenericDlg.showMessage( "Converting Engineering Evaluation to Catalog." );
      parent.getMain().setNewChemEval(actionEval);
    }else if (actionEval.equalsIgnoreCase("oldEval") || actionEval.equalsIgnoreCase("newEval")){
      //GenericDlg.showMessage( "Engineering Evaluation Request created." );
      parent.getMain().setNewChemEval(actionEval);
    }else {
      //GenericDlg.showMessage( "New Part Request created." );
      parent.getMain().setNewChemEval("normal");
    }
    parent.getMain().goNewChemMat(0,Integer.parseInt(reqid),result);
  }


  void moveToRequestScreen(Hashtable result){
      int test;
      String msg;
      String[] returnValue = new String[2];

      //parent.getMain().stopImage();

      if (result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        return;
      }

      returnValue[1] =((Vector) result.get("DATA")).elementAt(1).toString();
      returnValue[0] =((Vector) result.get("DATA")).elementAt(0).toString();
      msg = returnValue[1];

      GenericDlg err = new GenericDlg(parent.getMain(),"Result",true);
      err.setMsg(msg);
      err.setVisible(true);
      if (!returnValue[0].equals("0")){
      //call request material window
        parent.getMain().goNewChemMat(0,Integer.parseInt(returnValue[0]));
      }
  }

  void goNewApproval(){
    //long sTime = new java.util.Date().getTime();      //2-27-02
    if (!checkSelectedTable()) return;
    //user can request for work area approval if the selected catalog is in the selected facility
    Vector catalogPerFacility = (Vector)facilityCatalog.get(facChoice.getSelectedFacId());
    if (catalogPerFacility.size() > 0) {
      if (!catalogPerFacility.contains((String)catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),this.CATALOG_COL))) {
        GenericDlg err = new GenericDlg(parent.getMain(),"Invalid Catalog",true);
        err.setMsg("The selected Catalog is not supports by the selected Facility.\nClick on 'New Catalog Part' instead.");
        err.setVisible(true);
        return;
      }
    }else {
      GenericDlg err = new GenericDlg(parent.getMain(),"Invalid Catalog",true);
      err.setMsg("The selected Catalog is not supports by the selected Facility.\nClick on 'New Catalog Part' instead.");
      err.setVisible(true);
      return;
    }

    String reqid="0";
    Hashtable result = null;      //3-13-02
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","BUILD_NEW_APPROVAL"); //String, String
      query.put("USER_ID",parent.getUserId().toString());
      query.put("ITEM_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),ITEM_COL).toString());
      query.put("CAT_ID",facChoice.getSelectedFacId());
      query.put("WORK_AREA",appChoice.getSelectedWorkAreaID());     //12-08-01
      query.put("CAT_PART_NO",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),PART_COL).toString());
      query.put("PART_GROUP_NO",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),PART_GROUP_COL).toString());  //12-18-01
      query.put("ITEM_TYPE",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),TYPE_COL).toString());
      query.put("CATALOG_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),CATALOG_COL).toString());
      query.put("CATALOG_COMPANY_ID",catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),CATALOG_COMPANY_COL).toString());
      query.put("IS_EVAL",new Boolean(false));    //trong

      //3-13-02 Hashtable result = cgi.getResultHash(query);
      result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      if (result.containsKey("MESSAGE")){ // error
        GenericDlg.showMessage((String) result.get("MESSAGE") );
        return;
      }
      reqid = (String) result.get("REQ_ID");
    } catch (Exception e){
      e.printStackTrace();
      GenericDlg.showMessage( "Error:"+e.getMessage() );
    }
    //GenericDlg.showMessage( "New Approval Request created." );
    parent.getMain().goNewChemMat(0,Integer.parseInt(reqid),result);

    /*
    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date(min));
    System.out.println("---- final duration goNewApproval (SearchPT): "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds");
    */
  }


  void goNewGroup(){
        if (!checkSelectedTable()) return;
  }

  boolean checkSelectedTable(){
    //buttons
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      if (catTableNew.getSelectedRow()<0 || catTableNew.getSelectedRow()>catTableNew.getRowCount()){
        GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
        err.setMsg("Please select a part first.");
        err.setVisible(true);
        return false;
      }
    }else if (catalogC.getSelectedItem().toString().startsWith("Item")) {
      if (itemCatalogTable.getSelectedRow()<0 || itemCatalogTable.getSelectedRow()>itemCatalogTable.getRowCount()){
        GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
        err.setMsg("Please select an item first.");
        err.setVisible(true);
        return false;
      }
    }

    if (appChoice.getSelectedItem().toString().equalsIgnoreCase("Select a Work Area") &&
        catAddFacSingleAppList.contains(facChoice.getSelectedFacId().toString())) {
       GenericDlg err = new GenericDlg(parent.getMain(),"Error",true);
       err.setMsg("Please select a work area.");
       err.setVisible(true);
       return false;
    }
    return true;
  }

  void runThread(){
     SearchPTThread st = new SearchPTThread(this);
     st.start();
  }

  void basketGrid_actionPerformed(ActionEvent e) {
  }

  void itemText_mousePressed() {
    searchB.setEnabled(true);
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      searchPartCatButtonActive = true;
    }else if (catalogC.getSelectedItem().toString().startsWith("Item")) {
      searchItemCatButtonActive = true;
    }
  }

  void searchB_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     runThread();
  } //end of method

  void facChoice_itemStateChanged(ItemEvent e) {
   if (e.getStateChange() == e.SELECTED) {
    boolean b = canCreateMR();
    addItem.setEnabled(b);
    if(!b){
      addItem.setEnabled(b);
      basketRemove.setEnabled(b);
      addPR.setEnabled(b);
    }

    if (displayEngEval.booleanValue()) {
      displayEngEvalTab(b);
    }

    if (canReqChem()){
      //if selected facility can create catalog add to catalog
      String tmpSelectedFacilityID = facChoice.getSelectedFacId();
      Vector catalogAddAllowedV = (Vector) facilityCatalog.get(tmpSelectedFacilityID+"CATALOG_ADD_ALLOWED");
      if (catalogAddAllowedV != null) {
        if (catalogAddAllowedV.contains("Y")) {
          nmB.setEnabled(true);
          if (catalogC.getSelectedItem().toString().startsWith("Part")) {
            if (!searchFacility.equals(facChoice.getSelectedItem())) {
              searchB.setEnabled(true);
              searchPartCatButtonActive = true;
            }
            if(catTableNew.getSelectedRow()>-1 && catTableNew.getSelectedRow()<catTableNew.getRowCount()
              && viewC.getSelectedItem().toString().equalsIgnoreCase("Detail")){
              nspB.setEnabled(true);
              npB.setEnabled(true);
              ngB.setEnabled(true);
              //user can request for work area approval if the selected catalog is in the selected facility
              Vector catalogPerFacility = (Vector)facilityCatalog.get(facChoice.getSelectedFacId());
              if (catalogPerFacility.size() > 0) {
                naB.setEnabled(catalogPerFacility.contains((String)catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),this.CATALOG_COL)));
              }else {
                naB.setEnabled(false);
              }
            }
          }else if (catalogC.getSelectedItem().toString().startsWith("Item")) {
            searchItemCatFac = (String)facChoice.getSelectedItem();
            if(itemCatalogTable.getSelectedRow()>-1 && itemCatalogTable.getSelectedRow()<itemCatalogTable.getRowCount()){
              nspB.setEnabled(true);
              npB.setEnabled(true);
              naB.setEnabled(false);
            }
            if (itemCatEvalCheck != null) {
              if (this.itemCatEvalCheck.isVisible()) {
                if (!searchFacility.equals(facChoice.getSelectedItem())) {
                  searchB.setEnabled(true);
                }
              }
            }
          } //end of item catalog
        }else { //end of catalogAddAllowedV contains Y
          //the only option for catalog_add_allowed = Y for all catalog(s) for selected facility is New Work Area Approval
          nmB.setEnabled(false);
          nspB.setEnabled(false);
          npB.setEnabled(false);
          naB.setEnabled(false);
          if (catalogC.getSelectedItem().toString().startsWith("Part")) {
            if (!searchFacility.equals(facChoice.getSelectedItem())) {
              searchB.setEnabled(true);
              searchPartCatButtonActive = true;
            }
            if(catTableNew.getSelectedRow()>-1 && catTableNew.getSelectedRow()<catTableNew.getRowCount()
              && viewC.getSelectedItem().toString().equalsIgnoreCase("Detail")){
              //user can request for work area approval if the selected catalog is in the selected facility
              Vector catalogPerFacility = (Vector)facilityCatalog.get(facChoice.getSelectedFacId());
              if (catalogPerFacility.size() > 0) {
                naB.setEnabled(catalogPerFacility.contains((String)catTableNew.getModel().getValueAt(catTableNew.getSelectedRow(),this.CATALOG_COL)));
              }else {
                naB.setEnabled(false);
              }
            }else {
              naB.setEnabled(false);
            }
          }else if (catalogC.getSelectedItem().toString().startsWith("Item")) {
            searchItemCatFac = (String)facChoice.getSelectedItem();
            if (itemCatEvalCheck != null) {
              if (this.itemCatEvalCheck.isVisible()) {
                if (!searchFacility.equals(facChoice.getSelectedItem())) {
                  searchB.setEnabled(true);
                }
              }
            }
          }
        } //end of catalogAddAllowedV does not contains Y
      }else { //end of catalogAddAllowedV not is null
        nmB.setEnabled(false);
        nspB.setEnabled(false);
        npB.setEnabled(false);
        ngB.setEnabled(false);
        naB.setEnabled(false);
        if (catalogC.getSelectedItem().toString().startsWith("Part")) {
          if (!searchFacility.equals(facChoice.getSelectedItem())) {
            searchB.setEnabled(true);
            searchPartCatButtonActive = true;
          }
        }else if (catalogC.getSelectedItem().toString().startsWith("Item")) {
          searchItemCatFac = (String)facChoice.getSelectedItem();
          if (itemCatEvalCheck != null) {
            if (this.itemCatEvalCheck.isVisible()) {
              if (!searchFacility.equals(facChoice.getSelectedItem())) {
                searchB.setEnabled(true);
              }
            }
          }
        } //end of item catalog
      } //end of catalogAddAllowed is null
      //regardless if catalogAddAllowed also show evaluation buttons with below logic
      //display evaluation buttons
      if (eEvalCheck.isSelected()) {
        evalB.setEnabled(true);
        if(catTableNew.getSelectedRow()>-1 && catTableNew.getSelectedRow()<catTableNew.getRowCount()){
          ctcB.setEnabled(true);
          roeB.setEnabled(true);
        } else {
          ctcB.setEnabled(false);
          roeB.setEnabled(false);
        }
      } else {
        evalB.setEnabled(true);
        ctcB.setEnabled(false);
        roeB.setEnabled(false);
      }
    }else{
       nmB.setEnabled(false);
       nspB.setEnabled(false);
       npB.setEnabled(false);
       naB.setEnabled(false);
       ngB.setEnabled(false);
       //trong
       evalB.setEnabled(false);
    }

    //comment out 10-01-01 until we know what to do
    if (canCreateMR() && !canReqChem()){
      evalB.setEnabled(true);
      if (groupBox3.indexOfComponent(nchemPane) > 0) {
        groupBox3.setEnabledAt(groupBox3.indexOfComponent(nchemPane), false); //not allowing user to add thing to catalog
      }
    } else {
      displayNewChemTab();
    }

    if (catalogC.getSelectedItem().toString().startsWith("Item")) {
      if (itemCatFacCheck != null) {
        if (itemCatFacCheck.isVisible()) {
          this.itemCatFacCheck.setText((String)facChoice.getSelectedItem());
          this.itemCatWACheck.setText("Work Area");
        }
      }
    }

    //Whether I can see point of sale catalog
    setPOSCatalogToDropDown(true);
   } //end of item change is selected
  }   //end of method

  public void setSelectedFacilityInfo() {
    String selectedCatalog = catalogC.getSelectedItem().toString();
    if (selectedCatalog.startsWith("Part")) {
      setSelectedPartFacilityInfo();
    }else if (selectedCatalog.startsWith("Item")) {
      setSelectedItemFacilityInfo();
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      setSelectedPOSFacilityInfo();
    } //end of if POS

  } //end of method

  void setSelectedPOSFacilityInfo() {
    String tmpSelFacID = "";
    if (customerT.getText().length() > 0) {
      tmpSelFacID = getCustomerFacilityID(posFacC.getSelectedItem().toString());
    }else {
      tmpSelFacID = facChoice.getSelectedFacId();
    }
    if (selectedSearchPOSDataH.containsKey(tmpSelFacID)) {
      Hashtable tmpInnerH = (Hashtable)selectedSearchPOSDataH.get(tmpSelFacID);
      //customerT.setText((String)tmpInnerH.get("CUSTOMER_NAME"));
      //customerID = (Integer)tmpInnerH.get("CUSTOMER_ID");
      if (customerT.getText().length() > 0) {
        addItem.setEnabled(true);
        groupBox1.remove(facChoice);
        groupBox1.remove(appChoice);
        posApprovedForWACheck.setSelected(true);
        posApprovedForWACheck.setEnabled(true);
        clearCustomerB.setEnabled(true);
        groupBox1.add(posFacC, new GridBagConstraints2(1, 2, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
        groupBox1.add(posWAC, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
        posWAC.setSelectedItem((String)tmpInnerH.get("WORK_AREA"));
        Vector v = (Vector)tmpInnerH.get("CLERK_CATALOG_INFO");
        setClerkCatalogDropDown(v);
      }else {
        addItem.setEnabled(false);
        groupBox1.remove(posFacC);
        groupBox1.remove(posWAC);
        //posApprovedForWACheck.setEnabled(false);
        //clearCustomerB.setEnabled(false);
        groupBox1.add(facChoice, new GridBagConstraints2(1, 2, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
        groupBox1.add(appChoice, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 2, 0), 0, 0));
        appChoice.setSelectedItem((String)tmpInnerH.get("WORK_AREA"));
        setPOSCatalogToDropDown(true);
      }
      catalogC.setSelectedItem((String)tmpInnerH.get("CATALOG_TYPE"));
      posApprovedForWACheck.setSelected(((Boolean)tmpInnerH.get("APPROVED")).booleanValue());
      itemText.setText((String)tmpInnerH.get("SEARCH_TEXT"));
    }else {
      //customerT.setText("");
      //customerID = new Integer(0);
      appChoice.setSelectedIndex(0);
      //posApprovedForWACheck.setSelected(true);
      //posApprovedForWACheck.setEnabled(false);
      //clearCustomerB.setEnabled(false);
    }
  } //end of method

  void setSelectedPartFacilityInfo() {
    String tmpSelFacID = facChoice.getSelectedFacId();
    if (selectedSearchPartDataH.containsKey(tmpSelFacID)) {
      Hashtable tmpInnerH = (Hashtable)selectedSearchPartDataH.get(tmpSelFacID);
      appChoice.setSelectedItem((String)tmpInnerH.get("WORK_AREA"));
      facilityCheck.setSelected(((Boolean)tmpInnerH.get("ACTIVE_SELECTED")).booleanValue());
      wAreaCheck.setSelected(((Boolean)tmpInnerH.get("APPROVED")).booleanValue());
      oFacilityCheck.setSelected(((Boolean)tmpInnerH.get("ALL_CATALOG")).booleanValue());
      activeCheck.setSelected(((Boolean)tmpInnerH.get("ACTIVE")).booleanValue());
      itemText.setText((String)tmpInnerH.get("SEARCH_TEXT"));
    }else {
      appChoice.setSelectedIndex(0);
      facilityCheck.setSelected(true);
      wAreaCheck.setSelected(true);
      oFacilityCheck.setSelected(false);
      activeCheck.setSelected(false);
    }
    setWorkAreaActionChanged();
  } //end of method

  void setSelectedItemFacilityInfo() {
    String tmpSelFacID = facChoice.getSelectedFacId();
    if (facInEComList.contains(facChoice.getSelectedFacId().toString())) {
      if (selectedSearchItemDataH.containsKey(tmpSelFacID)) {
        Hashtable tmpInnerH = (Hashtable)selectedSearchItemDataH.get(tmpSelFacID);
        appChoice.setSelectedItem((String)tmpInnerH.get("WORK_AREA"));
        itemText.setText((String)tmpInnerH.get("SEARCH_TEXT"));
      }else {
        appChoice.setSelectedIndex(0);
      }
    }else {
      if (selectedSearchItemDataH.containsKey(tmpSelFacID)) {
        Hashtable tmpInnerH = (Hashtable)selectedSearchItemDataH.get(tmpSelFacID);
        appChoice.setSelectedItem((String)tmpInnerH.get("WORK_AREA"));
        itemCatEvalCheck.setSelected(((Boolean)tmpInnerH.get("PREVIOUS_ORDER")).booleanValue());
        itemCatRequestorCheck.setSelected(((Boolean)tmpInnerH.get("EVAL_REQUESTOR")).booleanValue());
        itemCatFacCheck.setSelected(((Boolean)tmpInnerH.get("EVAL_FACILITY")).booleanValue());
        itemCatWACheck.setSelected(((Boolean)tmpInnerH.get("EVAL_WORK_AREA")).booleanValue());
        itemText.setText((String)tmpInnerH.get("SEARCH_TEXT"));
      }else {
        appChoice.setSelectedIndex(0);
        itemCatEvalCheck.setSelected(false);
        itemCatRequestorCheck.setSelected(false);
        itemCatFacCheck.setSelected(false);
        itemCatWACheck.setSelected(false);
      }
    }
  } //end of method

  void setPOSCatalogToDropDown(boolean callCatalogAction) {
    loadingCatalogC = true;
    //don't change catalog drop down if customer name is filled in
    if (customerT.isVisible() && customerT.getText().length() > 0) {
      return;
    }
    //first remove listener
    catalogC.removeActionListener(catalogActionListener);
    //next remove everything from catalog list
    for (int i = catalogC.getItemCount() -1; i >= 0; i--) {
      catalogC.removeItemAt(i);
    }
    //now add Part and item catalogs to list
    catalogC.insertItemAt("Part Catalog",0);
    catalogC.insertItemAt("Item Catalog",1);
    String firstPOS = "";
    //otherwise, change catalog drop down depending on selected facility
    String tmpSelectedFacID = facChoice.getSelectedFacId();
    if (posFacilityH.containsKey(tmpSelectedFacID)) {
      //next add POS for facility
      Vector tmpV = (Vector) posFacilityH.get(tmpSelectedFacID);
      for (int j = 0; j < tmpV.size(); j++) {
        catalogC.insertItemAt(pointOfSaleName+(String)tmpV.elementAt(j), catalogC.getItemCount());
        if (firstPOS.length() < 1) {
          firstPOS = pointOfSaleName+(String)tmpV.elementAt(j);
        }
      }
      //add all pos inventory if more than one defined for facility
      if (tmpV.size() > 1) {
        catalogC.insertItemAt("POS: All POS for "+facChoice.getSelectedItem().toString(), catalogC.getItemCount());
      }
    }
    catalogC.setSelectedItem(lastSelectedCatalog);
    if (catalogC.getSelectedIndex() < 0) {
      if (firstPOS.length() > 0) {
        catalogC.setSelectedItem(firstPOS);
      }else {
        catalogC.setSelectedIndex(0);
      }
    }
    catalogC.addActionListener(catalogActionListener);
    loadingCatalogC = false;
    if (callCatalogAction) {
      catalogC_actionPerformed();
    }
  } //end of method

  void nmB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    eval = "normal";
    actionEval = "normal";
    //goNewMaterial();
    SearchPTNewChemThread st = new SearchPTNewChemThread(this,"NewMaterial");
    st.start();
  }

  void nspB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    eval = "normal";
    actionEval = "normal";
    //goNewSizePacking();
    SearchPTNewChemThread st = new SearchPTNewChemThread(this,"NewSizePackaging");
    st.start();
  }

  void npB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    eval = "normal";
    actionEval = "normal";
    //goNewPart();
    SearchPTNewChemThread st = new SearchPTNewChemThread(this,"NewPart");
    st.start();
  }

  void naB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    eval = "normal";
    actionEval = "normal";
    //goNewApproval();
    SearchPTNewChemThread st = new SearchPTNewChemThread(this,"NewApproval");
    st.start();
  }

  void ngB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    eval = "normal";
    actionEval = "normal";
    goNewGroup();
  }

  //This method is not really used right now
  void appChoice_itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == e.SELECTED) {
      setWorkAreaActionChanged();
    }
  }

  void appChoice_actionPerformed(ActionEvent e) {
    setWorkAreaActionChanged();
  } //end of method

  void setWorkAreaActionChanged() {
    if (!appChoice.loading) {
      if (catalogC.getSelectedItem().toString().startsWith("Part")) {
        /*if (!searchPartCatWA.equals(appChoice.getSelectedItem())) {
          searchB.setEnabled(true);
          searchPartCatButtonActive = true;
        }
        searchPartCatFac = (String)facChoice.getSelectedItem();
        searchPartCatWA = (String)appChoice.getSelectedItem();
        */
        if (!searchWorkArea.equals(appChoice.getSelectedItem())) {
          searchB.setEnabled(true);
          searchPartCatButtonActive = true;
        }

        if (appChoice.getSelectedItem().equals("All") ||
          appChoice.getSelectedItem().toString().equalsIgnoreCase("All - Unrestricted") ||   //trong
          appChoice.getSelectedItem().equals(SELECT_WORK_AREA)){
          wAreaCheck.setSelected(false);
          wAreaCheck.setEnabled(false);

          addItem.setEnabled(false);
          basketRemove.setEnabled(false);
          addPR.setEnabled(false);
          if (displayEngEval.booleanValue()) {
              displayEngEvalTab(false);
          }
        } else {
          wAreaCheck.setSelected(true);
          wAreaCheck.setEnabled(true);

          if (canCreateMRForApplication()) {
            addItem.setEnabled(true);
            basketRemove.setEnabled(true);
            addPR.setEnabled(true);
            if (displayEngEval.booleanValue() && canReqChem()) {
              displayEngEvalTab(true);
            }
          }else {
            addItem.setEnabled(false);
            basketRemove.setEnabled(false);
            addPR.setEnabled(false);
            if (displayEngEval.booleanValue()) {
              displayEngEvalTab(false);
            }
          }
        }
      }else if (catalogC.getSelectedItem().toString().startsWith("Item")) {
        searchItemCatFac = (String)facChoice.getSelectedItem();
        searchItemCatWA = (String)appChoice.getSelectedItem();
        if (itemCatEvalCheck != null) {
          if (this.itemCatEvalCheck.isVisible()) {
            this.itemCatFacCheck.setText((String)facChoice.getSelectedItem());
            this.itemCatWACheck.setText((String)appChoice.getSelectedItem());
            if (!searchWorkArea.equals(appChoice.getSelectedItem())) {
              searchB.setEnabled(true);
            }
          }
        }
        if (canCreateMRForApplication()) {
          if (displayEngEval.booleanValue() && canReqChem()) {
            displayEngEvalTab(true);
          }
        }else {
          if (displayEngEval.booleanValue()) {
            displayEngEvalTab(false);
          }
        }
      }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
        //searchPartCatFac = (String)facChoice.getSelectedItem();
        //searchPartCatWA = (String)appChoice.getSelectedItem();
        searchB.setEnabled(true);
        itemText.setEnabled(true);
        if (appChoice.getSelectedItem().equals("All") ||
          appChoice.getSelectedItem().toString().equalsIgnoreCase("All - Unrestricted") ||
          appChoice.getSelectedItem().equals(SELECT_WORK_AREA)){
          addItem.setEnabled(false);
          basketRemove.setEnabled(false);
          addPR.setEnabled(false);
        } else {
          //if (canCreateMRForApplication()) {
          if (customerT.isVisible() && customerT.getText().length() > 0) {
            addItem.setEnabled(true);
            basketRemove.setEnabled(false);
            addPR.setEnabled(false);
          }else {
            addItem.setEnabled(false);
            basketRemove.setEnabled(false);
            addPR.setEnabled(false);
          }
        }
      } //end of if catalog type
    }else {
      if (appChoice.getItemCount() == 1) {
        wAreaCheck.setSelected(true);
        wAreaCheck.setEnabled(true);
      }else {
        wAreaCheck.setSelected(false);
        wAreaCheck.setEnabled(false);
      }

      if (canCreateMRForApplication()) {
        addItem.setEnabled(true);
        basketRemove.setEnabled(true);
        addPR.setEnabled(true);
        if (displayEngEval.booleanValue() && canReqChem()) {
          displayEngEvalTab(true);
        }
      }else {
        addItem.setEnabled(false);
        basketRemove.setEnabled(false);
        addPR.setEnabled(false);
        if (displayEngEval.booleanValue()) {
          displayEngEvalTab(false);
        }
      }
    }
  } //end of method

  void setSelectedRow(int x){
    ListSelectionModel lsm = catTableNew.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    catTableNew.scrollRectToVisible(catTableNew.getCellRect(x,0,false));
  }

  void catTable_mouseReleased(MouseEvent e) {
    int firstIndex = catTableNew.getSelectedRow();
    int lastIndex = firstIndex;
    if (e.isMetaDown()) {
      Point p = e.getPoint();
      firstIndex = catTableNew.rowAtPoint(p);
    }
    String selectedCatPartGroup = (String)catTableNew.getModel().getValueAt(firstIndex,PART_COL)+
                                  (String)catTableNew.getModel().getValueAt(firstIndex,CATALOG_COL)+
                                  (String)catTableNew.getModel().getValueAt(firstIndex,CATALOG_COMPANY_COL)+
                                  (String)catTableNew.getModel().getValueAt(firstIndex,PART_GROUP_COL);
    //determine where the first index going to start
    for (int i = firstIndex -1; i >= 0 ; i--) {
      String tempCatPartGroup = (String)catTableNew.getModel().getValueAt(i,PART_COL)+
                                (String)catTableNew.getModel().getValueAt(i,CATALOG_COL)+
                                (String)catTableNew.getModel().getValueAt(i,CATALOG_COMPANY_COL)+
                                (String)catTableNew.getModel().getValueAt(i,PART_GROUP_COL);
      if (selectedCatPartGroup.equals(tempCatPartGroup)) {
        firstIndex = i;
      }else {
        break;
      }
    }
    //determine where the last index going to end
    for (int i = firstIndex + 1; i < catTableNew.getRowCount(); i++) {
      String tempCatPartGroup = (String)catTableNew.getModel().getValueAt(i,PART_COL)+
                                (String)catTableNew.getModel().getValueAt(i,CATALOG_COL)+
                                (String)catTableNew.getModel().getValueAt(i,CATALOG_COMPANY_COL)+
                                (String)catTableNew.getModel().getValueAt(i,PART_GROUP_COL);
      if (selectedCatPartGroup.equals(tempCatPartGroup)) {
        lastIndex = i;
      }else {
        break;
      }
    }
    catTableNew.setRowSelectionInterval(firstIndex,lastIndex);
    catTableNew.repaint();
    catTableNew.validate();
  }

  void catTable_mouseClicked(MouseEvent e) {
    int firstIndex = catTableNew.getSelectedRow();
    int lastIndex = firstIndex;
    if (e.isMetaDown()) {
      Point p = e.getPoint();
      firstIndex = catTableNew.rowAtPoint(p);
    }
    String selectedCatPartGroup = (String)catTableNew.getModel().getValueAt(firstIndex,PART_COL)+
                                  (String)catTableNew.getModel().getValueAt(firstIndex,CATALOG_COL)+
                                  (String)catTableNew.getModel().getValueAt(firstIndex,CATALOG_COMPANY_COL)+
                                  (String)catTableNew.getModel().getValueAt(firstIndex,PART_GROUP_COL);
    //determine where the first index going to start
    for (int i = firstIndex -1; i >= 0 ; i--) {
      String tempCatPartGroup = (String)catTableNew.getModel().getValueAt(i,PART_COL)+
                                (String)catTableNew.getModel().getValueAt(i,CATALOG_COL)+
                                (String)catTableNew.getModel().getValueAt(i,CATALOG_COMPANY_COL)+
                                (String)catTableNew.getModel().getValueAt(i,PART_GROUP_COL);
      if (selectedCatPartGroup.equals(tempCatPartGroup)) {
        firstIndex = i;
      }else {
        break;
      }
    }
    //determine where the last index going to end
    for (int i = firstIndex + 1; i < catTableNew.getRowCount(); i++) {
      String tempCatPartGroup = (String)catTableNew.getModel().getValueAt(i,PART_COL)+
                                (String)catTableNew.getModel().getValueAt(i,CATALOG_COL)+
                                (String)catTableNew.getModel().getValueAt(i,CATALOG_COMPANY_COL)+
                                (String)catTableNew.getModel().getValueAt(i,PART_GROUP_COL);
      if (selectedCatPartGroup.equals(tempCatPartGroup)) {
        lastIndex = i;
      }else {
        break;
      }
    }
    catTableNew.setRowSelectionInterval(firstIndex,lastIndex);
    catTableNew.repaint();
    catTableNew.validate();

    //Table clicked
    JFrame F;
    catSel = catTableNew.getSelectedRow();
    // Check for RightClick buttom
    if (e.isMetaDown()) {
      Point p = e.getPoint();
      catSel = catTableNew.rowAtPoint(p);
      int colSel = catTableNew.columnAtPoint(p);
      if (colSel <= MIN_BUY_COL) {        //2-26-02 change from BASE_COL to MIN_BUY_COL
        PartMetaPopUp pmpu = new PartMetaPopUp(parent,catTableNew.getModel().getValueAt(catSel,PART_COL).toString(),searchFacility,
            catTableNew.getModel().getValueAt(catSel,PART_GROUP_COL).toString(),catTableNew.getModel().getValueAt(catSel,this.CATALOG_COL).toString(),
            catTableNew.getModel().getValueAt(catSel,COMMENT_COL).toString(),true,catTableNew.getModel().getValueAt(catSel,this.TYPE_COL).toString());
        jScrollPane1.add(pmpu);
        pmpu.setCatalogCompanyId((String)catTableNew.getModel().getValueAt(catSel,CATALOG_COMPANY_COL));
        pmpu.setPackaging(catTableNew.getModel().getValueAt(catSel,this.PACKAGING_COL).toString());
        pmpu.setTradeName(catTableNew.getModel().getValueAt(catSel,this.COMPONENT_DESC_COL).toString());
        pmpu.setMfg(catTableNew.getModel().getValueAt(catSel,this.MANUFACTURER_COL).toString());
        pmpu.setMaterialID(catTableNew.getModel().getValueAt(catSel,MATERIAL_COL).toString());
        pmpu.setWorkarea(searchWorkArea);
        pmpu.setWorkAreaDesc(searchWorkAreaDesc);
        pmpu.setAllCatalog(oFacilityCheck.isSelected());
        pmpu.setBaselineReset(catTableNew.getModel().getValueAt(catSel,BASELINE_RESET_COL).toString());
        pmpu.setBaselineExpiration(catTableNew.getModel().getValueAt(catSel,BASELINE_EXPIRATION_COL).toString());
        pmpu.setInventoryGroup(catTableNew.getModel().getValueAt(catSel,INVENTORY_GROUP_COL).toString());
        pmpu.setInventoryGroupName(catTableNew.getModel().getValueAt(catSel,INVENTORY_GROUP_NAME_COL).toString());
        pmpu.setMedianLeadTime(catTableNew.getModel().getValueAt(catSel,MEDIAN_LEAD_TIME_COL).toString());
        pmpu.show(catTableNew,e.getX(),e.getY());
        return;
      }else if (colSel > ITEM_COL) {
        ComponentMetaPopUp cmpu = new ComponentMetaPopUp(parent,
              catTableNew.getModel().getValueAt(catSel,MATERIAL_COL).toString(),
              catTableNew.getModel().getValueAt(catSel,ITEM_COL).toString(),searchFacility,
              catTableNew.getModel().getValueAt(catSel,MSDS_ONLINE_COL).toString());
        jScrollPane1.add(cmpu);
        cmpu.show(catTableNew,e.getX(),e.getY());
        return;
      }else if (colSel > MIN_BUY_COL && colSel <= ITEM_COL) {    //2-26-02 change from BASE_COL to MIN_BUY_COL
        ItemMetaPopUp mpu = new ItemMetaPopUp(parent,catTableNew.getModel().getValueAt(catSel,ITEM_COL).toString(),searchFacility,
          catTableNew.getModel().getValueAt(catSel,PART_COL).toString(),catTableNew.getModel().getValueAt(catSel,this.CATALOG_COL).toString(),
          catTableNew.getModel().getValueAt(catSel,PART_GROUP_COL).toString());
        jScrollPane1.add(mpu);
        mpu.setCatalogCompanyId((String)catTableNew.getModel().getValueAt(catSel,CATALOG_COMPANY_COL));
        mpu.setPackaging(catTableNew.getModel().getValueAt(catSel,this.PACKAGING_COL).toString());
        mpu.setTradeName(catTableNew.getModel().getValueAt(catSel,this.COMPONENT_DESC_COL).toString());
        mpu.setMfg(catTableNew.getModel().getValueAt(catSel,this.MANUFACTURER_COL).toString());
        mpu.setInventoryGroup(catTableNew.getModel().getValueAt(catSel,INVENTORY_GROUP_COL).toString());
        mpu.setWorkArea(searchWorkArea);
        mpu.setAllCatalog(oFacilityCheck.isSelected());
        mpu.show(catTableNew,e.getX(),e.getY());
        return;
      }
    }

    if (oFacilityCheck.isSelected() && e.getClickCount() == 2) {
      GenericDlg err = new GenericDlg(parent.getMain(),"Audit.",true);
      err.setMsg("'All Catalogs' option is for displaying purpose only.\nPlease do another search with 'Active for selected Facility/Work Area' selected.");
      err.setVisible(true);
      return;
    }

    //12-06-01
    //if (e.getClickCount() == 2 && !eEvalCheck.isSelected() && catTableNew.getSelectedColumn() < ITEM_COL) {  //trong added eEvalcheck test condition
    if (e.getClickCount() == 2 && !eEvalCheck.isSelected()) {
      catSel = Integer.parseInt((String)catTableNew.getModel().getValueAt(catSel,this.PART_ITEM_ROW_COL));  //this tell me what line the part for this item is on
      if (auditAddToBasket(catSel)){
        addBasket(catSel,catTableNew);
      }
    }

    //buttons
    if (canReqChem() && catTableNew.getSelectedRow()>-1 &&
        catTableNew.getSelectedRow()<catTableNew.getRowCount() &&
        viewC.getSelectedItem().toString().equalsIgnoreCase("Detail")){
       if (catCheck.isSelected()){
         //if selected facility can create catalog add to catalog
         String tmpSelectedFacilityID = facChoice.getSelectedFacId();
         Vector catalogAddAllowedV = (Vector) facilityCatalog.get(tmpSelectedFacilityID+"CATALOG_ADD_ALLOWED");
         if (catalogAddAllowedV != null) {
           if (catalogAddAllowedV.contains("Y")) {
             nspB.setEnabled(true);
             npB.setEnabled(true);
             ngB.setEnabled(true);
             nmB.setEnabled(true);
             //user can request for work area approval if the selected catalog is in the selected facility
             Vector catalogPerFacility = (Vector)facilityCatalog.get(facChoice.getSelectedFacId());
             if (catalogPerFacility.size() > 0) {
               naB.setEnabled(catalogPerFacility.contains((String)catTableNew.getModel().getValueAt(catSel,this.CATALOG_COL)));
             }else {
               naB.setEnabled(false);
             }
           }else {    //end of catalogAddAllowedV contains Y
             nmB.setEnabled(false);
             nspB.setEnabled(false);
             npB.setEnabled(false);
             //user can request for work area approval if the selected catalog is in the selected facility
             Vector catalogPerFacility = (Vector)facilityCatalog.get(facChoice.getSelectedFacId());
             if (catalogPerFacility.size() > 0) {
               naB.setEnabled(catalogPerFacility.contains((String)catTableNew.getModel().getValueAt(catSel,this.CATALOG_COL)));
             }else {
               naB.setEnabled(false);
             }
           } //end of catalogAddAllowedV does not contains Y
         }else {  //end of catalogAddAllowedV is not null
           nmB.setEnabled(false);
           nspB.setEnabled(false);
           npB.setEnabled(false);
           naB.setEnabled(false);
         } //end of catalogAddAllowedV is null
       } else {  //end of catCheck is selected
        ctcB.setEnabled(true);
        roeB.setEnabled(true);
       }
    }

    if (canCreateMR() && eEvalCheck.isSelected() &&
        catTableNew.getSelectedRow()>-1 && catTableNew.getSelectedRow()<catTableNew.getRowCount()){
      roeB.setEnabled(true);
    }
  } //end of method

   void catPOSTable_mouseReleased(MouseEvent e) {
     int firstIndex = catPOSTable.getSelectedRow();
     int lastIndex = firstIndex;
     if (e.isMetaDown()) {
       Point p = e.getPoint();
       firstIndex = catPOSTable.rowAtPoint(p);
     }
     String selectedCatPartGroup = (String)catPOSTable.getModel().getValueAt(firstIndex,PART_COL)+
                                   (String)catPOSTable.getModel().getValueAt(firstIndex,CATALOG_COL)+
                                   (String)catPOSTable.getModel().getValueAt(firstIndex,CATALOG_COMPANY_COL)+
                                   (String)catPOSTable.getModel().getValueAt(firstIndex,PART_GROUP_COL);
     //determine where the first index going to start
     for (int i = firstIndex -1; i >= 0 ; i--) {
       String tempCatPartGroup = (String)catPOSTable.getModel().getValueAt(i,PART_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,CATALOG_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,CATALOG_COMPANY_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,PART_GROUP_COL);
       if (selectedCatPartGroup.equals(tempCatPartGroup)) {
         firstIndex = i;
       }else {
         break;
       }
     }
     //determine where the last index going to end
     for (int i = firstIndex + 1; i < catPOSTable.getRowCount(); i++) {
       String tempCatPartGroup = (String)catPOSTable.getModel().getValueAt(i,PART_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,CATALOG_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,CATALOG_COMPANY_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,PART_GROUP_COL);
       if (selectedCatPartGroup.equals(tempCatPartGroup)) {
         lastIndex = i;
       }else {
         break;
       }
     }
     catPOSTable.setRowSelectionInterval(firstIndex,lastIndex);
     catPOSTable.repaint();
     catPOSTable.validate();
   } //end of method

   void catPOSTable_mouseClicked(MouseEvent e) {
     int firstIndex = catPOSTable.getSelectedRow();
     int lastIndex = firstIndex;
     if (e.isMetaDown()) {
       Point p = e.getPoint();
       firstIndex = catPOSTable.rowAtPoint(p);
     }
     String selectedCatPartGroup = (String)catPOSTable.getModel().getValueAt(firstIndex,PART_COL)+
                                   (String)catPOSTable.getModel().getValueAt(firstIndex,CATALOG_COL)+
                                   (String)catPOSTable.getModel().getValueAt(firstIndex,CATALOG_COMPANY_COL)+
                                   (String)catPOSTable.getModel().getValueAt(firstIndex,PART_GROUP_COL);
     //determine where the first index going to start
     for (int i = firstIndex -1; i >= 0 ; i--) {
       String tempCatPartGroup = (String)catPOSTable.getModel().getValueAt(i,PART_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,CATALOG_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,CATALOG_COMPANY_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,PART_GROUP_COL);
       if (selectedCatPartGroup.equals(tempCatPartGroup)) {
         firstIndex = i;
       }else {
         break;
       }
     }
     //determine where the last index going to end
     for (int i = firstIndex + 1; i < catPOSTable.getRowCount(); i++) {
       String tempCatPartGroup = (String)catPOSTable.getModel().getValueAt(i,PART_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,CATALOG_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,CATALOG_COMPANY_COL)+
                                 (String)catPOSTable.getModel().getValueAt(i,PART_GROUP_COL);
       if (selectedCatPartGroup.equals(tempCatPartGroup)) {
         lastIndex = i;
       }else {
         break;
       }
     }
     catPOSTable.setRowSelectionInterval(firstIndex,lastIndex);
     catPOSTable.repaint();
     catPOSTable.validate();

     //Table clicked
     JFrame F;
     catSel = catPOSTable.getSelectedRow();
     // Check for RightClick buttom
     if (e.isMetaDown()) {
       Point p = e.getPoint();
       catSel = catPOSTable.rowAtPoint(p);
       int colSel = catPOSTable.columnAtPoint(p);
       //11-17-01 doesn't work setSelectedRow(catSel);
       if (colSel <= MIN_BUY_COL) {        //2-26-02 change from BASE_COL to MIN_BUY_COL
         PartMetaPopUp pmpu = new PartMetaPopUp(parent,catPOSTable.getModel().getValueAt(catSel,PART_COL).toString(),searchPOSFacility,
             catPOSTable.getModel().getValueAt(catSel,PART_GROUP_COL).toString(),catPOSTable.getModel().getValueAt(catSel,this.CATALOG_COL).toString(),
             catPOSTable.getModel().getValueAt(catSel,COMMENT_COL).toString(),true,catPOSTable.getModel().getValueAt(catSel,this.TYPE_COL).toString());
         jScrollPane1.add(pmpu);
         pmpu.setCatalogCompanyId((String)catPOSTable.getModel().getValueAt(catSel,CATALOG_COMPANY_COL));
         pmpu.setPackaging(catPOSTable.getModel().getValueAt(catSel,this.PACKAGING_COL).toString());
         pmpu.setTradeName(catPOSTable.getModel().getValueAt(catSel,this.COMPONENT_DESC_COL).toString());
         pmpu.setMfg(catPOSTable.getModel().getValueAt(catSel,this.MANUFACTURER_COL).toString());
         pmpu.setMaterialID(catPOSTable.getModel().getValueAt(catSel,MATERIAL_COL).toString());
         pmpu.setWorkarea(searchPOSWorkArea);
         pmpu.setWorkAreaDesc(searchPOSWorkAreaDesc);
         pmpu.setAllCatalog(false);
         pmpu.setInventoryGroup(catPOSTable.getModel().getValueAt(catSel,this.INVENTORY_GROUP_COL).toString());
         pmpu.setInventoryGroupName(catPOSTable.getModel().getValueAt(catSel,this.INVENTORY_GROUP_NAME_COL).toString());
         pmpu.show(catPOSTable,e.getX(),e.getY());
         return;
       }else if (colSel > ITEM_COL) {
         ComponentMetaPopUp cmpu = new ComponentMetaPopUp(parent,
               catPOSTable.getModel().getValueAt(catSel,MATERIAL_COL).toString(),
               catPOSTable.getModel().getValueAt(catSel,ITEM_COL).toString(),searchPOSFacility,
               catPOSTable.getModel().getValueAt(catSel,MSDS_ONLINE_COL).toString());
         jScrollPane1.add(cmpu);
         cmpu.show(catPOSTable,e.getX(),e.getY());
         return;
       }else if (colSel > MIN_BUY_COL && colSel <= ITEM_COL) {    //2-26-02 change from BASE_COL to MIN_BUY_COL
         ItemMetaPopUp mpu = new ItemMetaPopUp(parent,catPOSTable.getModel().getValueAt(catSel,ITEM_COL).toString(),searchPOSFacility,
           catPOSTable.getModel().getValueAt(catSel,PART_COL).toString(),catPOSTable.getModel().getValueAt(catSel,this.CATALOG_COL).toString(),
           catPOSTable.getModel().getValueAt(catSel,this.PART_GROUP_COL).toString());
         jScrollPane1.add(mpu);
         mpu.setCatalogCompanyId((String)catPOSTable.getModel().getValueAt(catSel,CATALOG_COMPANY_COL));
         mpu.setPackaging(catPOSTable.getModel().getValueAt(catSel,this.PACKAGING_COL).toString());
         mpu.setTradeName(catPOSTable.getModel().getValueAt(catSel,this.COMPONENT_DESC_COL).toString());
         mpu.setMfg(catPOSTable.getModel().getValueAt(catSel,this.MANUFACTURER_COL).toString());
         mpu.setInventoryGroup(catTableNew.getModel().getValueAt(catSel,INVENTORY_GROUP_COL).toString());
         mpu.setWorkArea(searchPOSWorkArea);
         mpu.setAllCatalog(false);
         mpu.show(catPOSTable,e.getX(),e.getY());
         return;
       }
     }

     if (oFacilityCheck.isSelected() && e.getClickCount() == 2) {
       GenericDlg err = new GenericDlg(parent.getMain(),"Audit.",true);
       err.setMsg("'All Catalogs' option is for displaying purpose only.\nPlease do another search with 'Active for selected Facility/Work Area' selected.");
       err.setVisible(true);
       return;
     }

     //12-06-01
     //if (e.getClickCount() == 2 && !eEvalCheck.isSelected() && catTableNew.getSelectedColumn() < ITEM_COL) {  //trong added eEvalcheck test condition
     if (e.getClickCount() == 2 && !eEvalCheck.isSelected()) {
       catSel = Integer.parseInt((String)catPOSTable.getModel().getValueAt(catSel,this.PART_ITEM_ROW_COL));  //this tell me what line the part for this item is on
       if (auditAddToBasket(catSel)){
         addBasket(catSel,catPOSTable);
       }
     }

     //buttons
     if (canReqChem() && catPOSTable.getSelectedRow()>-1 &&
         catPOSTable.getSelectedRow()<catPOSTable.getRowCount() &&
         viewC.getSelectedItem().toString().equalsIgnoreCase("Detail")){
        if (catCheck.isSelected()){
          //if selected facility can create catalog add to catalog
          String tmpSelectedFacilityID = facChoice.getSelectedFacId();
          Vector catalogAddAllowedV = (Vector) facilityCatalog.get(tmpSelectedFacilityID+"CATALOG_ADD_ALLOWED");
          if (catalogAddAllowedV != null) {
            if (catalogAddAllowedV.contains("Y")) {
              nspB.setEnabled(true);
              npB.setEnabled(true);
              ngB.setEnabled(true);
              nmB.setEnabled(true);
              //user can request for work area approval if the selected catalog is in the selected facility
              Vector catalogPerFacility = (Vector)facilityCatalog.get(facChoice.getSelectedFacId());
              if (catalogPerFacility.size() > 0) {
                naB.setEnabled(catalogPerFacility.contains((String)catPOSTable.getModel().getValueAt(catSel,this.CATALOG_COL)));
              }else {
                naB.setEnabled(false);
              }
            }else {    //end of catalogAddAllowedV contains Y
              nmB.setEnabled(false);
              nspB.setEnabled(false);
              npB.setEnabled(false);
              naB.setEnabled(false);
            } //end of catalogAddAllowedV does not contains Y
          }else {  //end of catalogAddAllowedV is not null
            nmB.setEnabled(false);
            nspB.setEnabled(false);
            npB.setEnabled(false);
            naB.setEnabled(false);
          } //end of catalogAddAllowedV is null
        } else {
         ctcB.setEnabled(true);
         roeB.setEnabled(true);
        }
     }

     if (canCreateMR() && eEvalCheck.isSelected() &&
         catPOSTable.getSelectedRow()>-1 && catPOSTable.getSelectedRow()<catPOSTable.getRowCount()){
       roeB.setEnabled(true);
     }
  } //end of method

  boolean canReqChem(){
    boolean canReqChem = false;
    if(facChoice == null || facChoice.getItemCount() == 0) return false;
    if(facChoice.getSelectedIndex() < 0) facChoice.setSelectedIndex(0);
    return (parent.groupMembership.isGroupMember("CreateNewChemical",facChoice.getSelectedFacId()));
  }

  //2-26-02
  void calculateExtendedPrice(){
    int r = basketTableNew.getRowCount();
    for(int i=0;i<r;i++) {
      if (!BothHelpObjs.isBlankString((String)basketTableNew.getModel().getValueAt(i,this.CART_CATALOG_PRICE_COL))) {
        try{
          String partType = basketTableNew.getModel().getValueAt(i,this.CART_TYPE_COL).toString();
          String cqty = basketTableNew.getModel().getValueAt(i,this.CART_QTY_COL).toString();
          Float baseP = new Float(basketTableNew.getModel().getValueAt(i,this.CART_CATALOG_PRICE_COL).toString());
          Float tmpQty = new Float(cqty);
          float extP = tmpQty.floatValue() * baseP.floatValue();
          String minB = BothHelpObjs.makeBlankFromNull(basketTableNew.getModel().getValueAt(i,this.CART_MIN_BUY_COL).toString());
          if ("OOR".equalsIgnoreCase(partType) && !BothHelpObjs.isBlankString(minB)) {
            Double minBV = new Double(minB);
            if (extP < minBV.doubleValue()) {
              extP = minBV.floatValue();
            }
          }
          BigDecimal amt = new BigDecimal(extP);
          amt = amt.setScale(4,BigDecimal.ROUND_HALF_UP);
          String[] priceInfo = separatePriceFromCurrency(basketTableNew.getModel().getValueAt(i,this.CART_EXT_PRICE_COL).toString());
          basketTableNew.getModel().setValueAt(amt.toString()+priceInfo[1],i,this.CART_EXT_PRICE_COL);
        }catch(Exception e){
          e.printStackTrace();
          basketTableNew.getModel().setValueAt(new String("0.0000"),i,this.CART_EXT_PRICE_COL);
        }
      }else {
        basketTableNew.getModel().setValueAt("",i,this.CART_EXT_PRICE_COL);
      }
    }
    basketTableNew.repaint();
  }

  void setSplitLocation(){
      // divide on half
      split.setDividerLocation((double) 0.50);
      this.revalidate();
  }

  void basketRemove_actionPerformed(ActionEvent e) {
    if (!processingData) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      SearchPTLoadThread st = new SearchPTLoadThread(this, "deleteLine");
      st.start();
    }
  }

  void deleteLine() {
    processingData = true;
    int cRow = basketTableNew.getSelectedRow();
    if (cRow >= 0 ) {
      String cPR = basketTableNew.getModel().getValueAt(cRow,CART_PR_COL).toString();
      if (!BothHelpObjs.isBlankString(cPR)) {
        int selectedLineItem = -1;
        try {
          selectedLineItem = Integer.parseInt(basketTableNew.getModel().getValueAt(cRow, CART_LINE_ITEM_COL).toString());
        }catch (Exception e) {
          selectedLineItem = -1;
        }
        if (basketTableNew.getRowCount() == 1 ) {
          if (deleteLineFromMR("DELETE_MR_SEARCH",cPR,selectedLineItem)) {
            deleteBasket();
          }
        }else{
          if (deleteLineFromMR("DELETE_LINE_SEARCH",cPR,selectedLineItem)) {
            deleteBasket();
            reorganizeLineItemForExistingMR();
          }
        }
      }else {
        deleteBasket();
      }
    }else {
      GenericDlg g = new GenericDlg(parent.getMain(),"Select a row",true);
      g.setMsg("Please select the line you wanted to delete.");
      g.setVisible(true);
      processingData = false;
      return;
    }
    processingData = false;
  }

  void reorganizeLineItemForExistingMR() {
    int count = 1;
    for (int i=0;i<basketTableNew.getModel().getRowCount();i++) {
       if (!BothHelpObjs.isBlankString((String) basketTableNew.getModel().getValueAt(i, CART_PR_COL))) {
         basketTableNew.getModel().setValueAt((new Integer(count++)).toString(),i,CART_LINE_ITEM_COL);
       }
    }
  }

  boolean deleteLineFromMR(String action, String cPR, int cRow) {
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.MATERIAL_REQUEST,parent);
      Hashtable query = new Hashtable();
      query.put("USER_ID",parent.getUserId().toString());
      query.put("FUNCTION",action);
      query.put("SELECTED_LINE",new Integer(cRow));
      query.put("REQ_NUM",cPR);                       //String, String
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        return false;
      }
      Hashtable h = (Hashtable)result.get("RETURN_CODE");
      Boolean b = (Boolean)h.get("SUCCEEDED");
      if (!b.booleanValue()) {
        GenericDlg gd = new GenericDlg(parent.getMain(),"Error",true);
        gd.setMsg(h.get("MSG").toString());
        gd.setVisible(true);
        return false;
      }
    }catch(Exception e){
      GenericDlg gd = new GenericDlg(parent.getMain(),"Error",true);
      gd.setMsg("An error occurred while trying to delete line from shopping cart.\nPlease check your data and try again.");
      gd.setVisible(true);
      e.printStackTrace();
      return false;
    }
    return true;
  }

  void jComboBox1_mouseClicked(MouseEvent e) {
  }

  void basketTable_mouseClicked(MouseEvent e) {
    int col = ((JTable)e.getSource()).columnAtPoint(new Point(e.getX(),e.getY()));
    int row = ((JTable)e.getSource()).rowAtPoint(new Point(e.getX(),e.getY()));

    if (basketTableNew.isEditing()){
      basketTableNew.getCellEditor().stopCellEditing();
    }

    calculateExtendedPrice();       //2-26-02
    //check to make sure that customer limit is okay
    if ("y".equalsIgnoreCase(approverRequired) && col != CART_QTY_COL) {
      if (!calculateCostLimit()) {
        return;
      }
    } //end of approver is required

    if(col == CART_NOTE_COL && e.getClickCount() > 1){
      EditDlg RjW = new EditDlg(parent.getMain(),"Enter Notes",true, "Enter notes:");
      String tmp = (String)basketTableNew.getModel().getValueAt(row,CART_REAL_NOTE_COL);
      if(!BothHelpObjs.isBlankString(tmp))RjW.setText(tmp);
      RjW.setVisible(true);
      if(!BothHelpObjs.isBlankString(RjW.getText())){
        basketTableNew.getModel().setValueAt("+",row,col);
        basketTableNew.getModel().setValueAt(RjW.getText(),row,CART_REAL_NOTE_COL);
      }else{
        basketTableNew.getModel().setValueAt("",row,col);
        basketTableNew.getModel().setValueAt("",row,CART_REAL_NOTE_COL);
      }
      RjW.dispose();
    }
    basketTableNew.repaint();
  }

  void panel2_mouseClicked(MouseEvent e) {
  }

  void basketTable_mouseReleased(MouseEvent e) {
  }

  //trong 4/10
  public void showAccSysDlg() {
     boolean selected = false;
     String fac = facChoice.getSelectedFacId();
     String msg;
     if (parent.getResourceBundle().get("APP_NAME").toString().equalsIgnoreCase("Seagate")) {
      msg = "a group ";
     }else {
      msg = "an Account System ";
     }

     String title = "Select "+msg+"for: " + fac;
     Hashtable accSysTT = (Hashtable)accSysH.get(fac);
     Vector vv = (Vector)accSysTT.get("ACC_SYS_V");
     String prefAccSys = (String)prefAccSysH.get(fac);
     if (vv.size() == 1) {
      mySelectedAccSys = (String)vv.elementAt(0);
     } else {
      while (!selected) {
        AccountSystemDlg asd = new AccountSystemDlg(parent.getMain(),title,true);
        for (int i = 0; i < vv.size(); i++)
          asd.accSysC.addItem(vv.elementAt(i).toString());
        if (vv.size() > 1 && BothHelpObjs.isBlankString(prefAccSys)) {
          asd.accSysC.addItem("Select "+msg);
          asd.accSysC.setSelectedItem("Select "+msg);
        } else if (prefAccSys.length() > 0) {
          asd.accSysC.setSelectedItem(prefAccSys);
        } else {
          asd.accSysC.setSelectedIndex(0);
        }
        asd.setVisible(true);
        mySelectedAccSys = asd.getSelectedAccSys();
        if (!mySelectedAccSys.equalsIgnoreCase("Select "+msg))
          selected = true;
      }
     }
   }


  // Add PR
  void addPR_actionPerformed(ActionEvent e) {
    if (!processingData) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      SearchPTLoadThread st = new SearchPTLoadThread(this, "checkOut");
      st.start();
    }
  }

  void facilityCheck_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
    if (facilityCheck.isSelected()) {
      facChoice.setEnabled(true);
      appChoice.setEnabled(true);
      wAreaCheck.setEnabled(true);
      if ((appChoice.getSelectedItem().equals("All") ||
        appChoice.getSelectedItem().toString().equalsIgnoreCase("All - Unrestricted") ||   //trong
        appChoice.getSelectedItem().equals(SELECT_WORK_AREA))){
        wAreaCheck.setEnabled(false);
        wAreaCheck.setSelected(false);
      }else {
        wAreaCheck.setEnabled(true);
        wAreaCheck.setSelected(true);
      }
      activeCheck.setSelected(false);
      activeCheck.setEnabled(false);
    }else {
      facChoice.setEnabled(false);
      appChoice.setEnabled(false);
      wAreaCheck.setSelected(false);
      wAreaCheck.setEnabled(false);
      activeCheck.setSelected(true);
      activeCheck.setEnabled(true);
    }
    searchB.setEnabled(true);
  }

  void oFacilityCheck_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
    if (facilityCheck.isSelected()) {
        facChoice.setEnabled(true);
        appChoice.setEnabled(true);
        wAreaCheck.setEnabled(true);
        if ((appChoice.getSelectedItem().equals("All") ||
          appChoice.getSelectedItem().toString().equalsIgnoreCase("All - Unrestricted") ||   //trong
          appChoice.getSelectedItem().equals(SELECT_WORK_AREA))){
          wAreaCheck.setEnabled(false);
          wAreaCheck.setSelected(false);
        }else {
          wAreaCheck.setEnabled(true);
          wAreaCheck.setSelected(true);
        }
        activeCheck.setSelected(false);
        activeCheck.setEnabled(false);
    } else {
        facChoice.setEnabled(false);
        appChoice.setEnabled(false);
        wAreaCheck.setEnabled(false);
        wAreaCheck.setSelected(false);
        activeCheck.setSelected(true);
        activeCheck.setEnabled(true);
    }
    searchB.setEnabled(true);
  }

  void basketTable_editingStopped(ChangeEvent e) {
      calculateExtendedPrice();
  }

  void wAreaCheck_itemStateChanged(ItemEvent e) {
     stateChanged("Refresh");
  }

  void itemText_textValueChanged(TextEvent e) {
    //item text
    stateChanged("Refresh");
  }

  void searchB_keyPressed(KeyEvent e) {
    //enter on button
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        runThread();
    }
  }

  void this_keyPressed(KeyEvent e) {
    //enter on this
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        runThread();
    }
  }

  void basketRemove_keyPressed(KeyEvent e) {
    //enter on remove
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        deleteBasket();
    }
  }

  void addPR_keyPressed(KeyEvent e) {
    //enter on create
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (!processingData) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        SearchPTLoadThread st = new SearchPTLoadThread(this, "checkOut");
        st.start();
      }
    }
  } //end of method

  void addItem_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    //add item to cart
    //determine which table to select from
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      catSel = catTableNew.getSelectedRow();
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      catSel = catPOSTable.getSelectedRow();
    }
    //users can not order if all catalogs is selected
    if (oFacilityCheck.isSelected()) {
      GenericDlg err = new GenericDlg(parent.getMain(),"Audit.",true);
      err.setMsg("'All Catalogs' option is for displaying purpose only.\nPlease do another search with 'Active for selected Facility/Work Area' selected.");
      err.setVisible(true);
      return;
    }

    if (catSel < 0){
      GenericDlg err = new GenericDlg(parent.getMain(),"Audit.",true);
      err.setMsg("Please select a part first.");
      err.setVisible(true);
      return;
    }
    //determine which table to select from
    if (catalogC.getSelectedItem().toString().startsWith("Part")) {
      catSel = Integer.parseInt((String)catTableNew.getModel().getValueAt(catSel,this.PART_ITEM_ROW_COL));  //12-06-01 this tell me what line the part for this item is on
    }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
      catSel = Integer.parseInt((String)catPOSTable.getModel().getValueAt(catSel,this.PART_ITEM_ROW_COL));  //12-06-01 this tell me what line the part for this item is on
    }
    if (auditAddToBasket(catSel)){
      //determine which table to select from
      if (catalogC.getSelectedItem().toString().startsWith("Part")) {
        addBasket(catSel,catTableNew);
      }else if (catalogC.getSelectedItem().toString().startsWith("POS")) {
        addBasket(catSel,catPOSTable);
      }
    }
  } //end of method

  void panel2_focusLost(FocusEvent e) {
  }

  void itemText_keyPressed(KeyEvent e) {
    searchB.setEnabled(true);
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        runThread();
    }
  }

  void this_componentResized(ComponentEvent e) {
    // rfz -- catTable.setBounds(0,0,panel1.getWidth()-1,panel1.getHeight()-1);
    //catTable.sizeColumnsToFit(10);
    panel1.validate();
    panel1.repaint();
  }

  void top_componentResized(ComponentEvent e) {
    if (loading) return;
    Dimension d = top.getSize();
    if (d.height < 120){
       top.setVisible(false);
    } else {
       top.setVisible(true);
    }
  }

  void eEvalCheck_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
  }
  void catCheck_itemStateChanged(ItemEvent e) {
    stateChanged("Refresh");
  }

  void viewC_actionPerformed(ActionEvent e) {
    //CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    //disable until user click on a row
    nspB.setEnabled(false);
    npB.setEnabled(false);
    naB.setEnabled(false);
    ngB.setEnabled(false);

    AttributiveCellTableModel model;
    if (viewC.getSelectedItem().toString().equalsIgnoreCase("Detail")) {
      if (!detailLoaded) {
        buildSearchTableNewDetail();
        detailLoaded = true;
      }
      model = modelDetail;
    }else {
      if (!partViewLoaded) {
        buildSearchTableNewPart();
        partViewLoaded = true;
      }
      model = modelPart;
    }

    catTableNew = new MultiSpanCellTable(model);
    catTableNew.addMouseListener(new SearchPT_catTable_mouseAdapter(this));
    catTableNew.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    catTableNew.getTableHeader().setResizingAllowed(true);

    if (viewC.getSelectedItem().toString().startsWith("Part")) {
      catTableNew.setRowHeight(catTableNew.getRowHeight()*2);
      setPartTableStyleHighLightRow(model);
    }else {
      setTableStyle(model);
    }


    panel1.remove(jScrollPane1);
    panel1.validate();
    jScrollPane1 = new JScrollPane(catTableNew);
    panel1.add(jScrollPane1, BorderLayout.CENTER);
    catTableNew.repaint();
    this.validate();
    panel1.validate();
    //CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  } //end of method
}   //end of class

class SearchPTLoadThread extends Thread {
  SearchPT parent;
  String action = "";
  public SearchPTLoadThread(SearchPT parent, String action){
     super("SearchPTLoadThread");
     this.parent = parent;
     this.action = action;
  }

  public void run(){
    if ("load".equalsIgnoreCase(action)) {
     parent.loadItAction();
    }else if ("checkOut".equalsIgnoreCase(action)) {
      if (parent.catalogC.getSelectedItem().toString().startsWith("Part")) {
        parent.buildRequest();
      }else if (parent.catalogC.getSelectedItem().toString().startsWith("POS")) {
        parent.buildPOSRequest();
      }
    }else if ("deleteLine".equalsIgnoreCase(action)) {
     parent.deleteLine();
    }else if ("aribaRepairSubmit".equalsIgnoreCase(action)) {
     parent.goAribaRepairSubmit();
    }else if ("aribaRepairSearch".equalsIgnoreCase(action)) {
     parent.goAribaRepairSearch();
    }
  }
}


class SearchPT_basketGrid_actionAdapter implements java.awt.event.ActionListener {
  SearchPT adaptee;

  SearchPT_basketGrid_actionAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.basketGrid_actionPerformed(e);
  }
}
 /*
class SearchPT_itemText_actionAdapter implements java.awt.event.ActionListener{
  SearchPT adaptee;

  SearchPT_itemText_actionAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.itemText_actionPerformed(e);
  }
}  */

 class SearchPT_POSC_itemAdapter implements java.awt.event.ItemListener {
   SearchPT adaptee;

   SearchPT_POSC_itemAdapter(SearchPT adaptee) {
     this.adaptee = adaptee;
   }

   public void itemStateChanged(ItemEvent e) {
     adaptee.loadWorkArea();
     adaptee.setSelectedFacilityInfo();
   }
 }

class SearchPT_facChoice_itemAdapter implements java.awt.event.ItemListener {
  SearchPT adaptee;

  SearchPT_facChoice_itemAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.facChoice_itemStateChanged(e);
  }
}

class SearchPT_appChoice_itemAdapter implements java.awt.event.ItemListener {
  SearchPT adaptee;

  SearchPT_appChoice_itemAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.appChoice_itemStateChanged(e);
  }
  /*
  public void actionPerformed(ItemEvent e) {
    adaptee.appChoice_actionPerformed(e);
  } */
}


class SearchPT_catTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchPT adaptee;

  SearchPT_catTable_mouseAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.catTable_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
    adaptee.catTable_mouseReleased(e);
  }
      /*
  public void mousePressed(MouseEvent e) {
    adaptee.catTable_mousePressed(e);
  }   */
}

class SearchPT_catPOSTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchPT adaptee;

  SearchPT_catPOSTable_mouseAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.catPOSTable_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
    adaptee.catPOSTable_mouseReleased(e);
  }
} //end of class

class SearchPT_basketRemove_actionAdapter implements java.awt.event.ActionListener{
  SearchPT adaptee;

  SearchPT_basketRemove_actionAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.basketRemove_actionPerformed(e);
  }
}


class SearchPT_jComboBox1_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchPT adaptee;

  SearchPT_jComboBox1_mouseAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.jComboBox1_mouseClicked(e);
  }
}

class SearchPT_basketTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchPT adaptee;

  SearchPT_basketTable_mouseAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.basketTable_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
    adaptee.basketTable_mouseReleased(e);
  }
}

class SearchPT_itemCatalogTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchPT adaptee;

  SearchPT_itemCatalogTable_mouseAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.itemCatalogTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
    adaptee.itemCatalogTable_mouseReleased(e);
  }
}

class SearchPT_aribaRepairTable_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchPT adaptee;

  SearchPT_aribaRepairTable_mouseAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.aribaRepairTable_mouseClicked(e);
  }
}

class SearchPT_panel2_mouseAdapter extends java.awt.event.MouseAdapter {
  SearchPT adaptee;

  SearchPT_panel2_mouseAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.panel2_mouseClicked(e);
  }
}
 /*
class SearchPT_catTable_mouseMotionAdapter extends java.awt.event.MouseMotionAdapter {
  SearchPT adaptee;

  SearchPT_catTable_mouseMotionAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseDragged(MouseEvent e) {
    adaptee.catTable_mouseDragged(e);
  }
}*/

class SearchPT_addPR_actionAdapter implements java.awt.event.ActionListener{
  SearchPT adaptee;

  SearchPT_addPR_actionAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.addPR_actionPerformed(e);
  }
}

class SearchPT_facilityCheck_itemAdapter implements java.awt.event.ItemListener {
  SearchPT adaptee;

  SearchPT_facilityCheck_itemAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.facilityCheck_itemStateChanged(e);
  }
}

class SearchPT_oFacilityCheck_itemAdapter implements java.awt.event.ItemListener {
  SearchPT adaptee;

  SearchPT_oFacilityCheck_itemAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.oFacilityCheck_itemStateChanged(e);
  }
}


class SearchPT_wAreaCheck_itemAdapter implements java.awt.event.ItemListener {
  SearchPT adaptee;

  SearchPT_wAreaCheck_itemAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.wAreaCheck_itemStateChanged(e);
  }
}


class SearchPT_itemText_textAdapter implements java.awt.event.TextListener{
  SearchPT adaptee;

  SearchPT_itemText_textAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void textValueChanged(TextEvent e) {
    adaptee.itemText_textValueChanged(e);
  }
}

/** Load data
*/
class SearchPTThread extends Thread {
  SearchPT parent;
  boolean runF = true;

  public SearchPTThread(SearchPT parent){
     super("SearchPTThread");
     this.parent = parent;
  }

  public void run(){
    //parent.searchCatalog(null);
    if (parent.catalogC.getSelectedItem().toString().startsWith("Part")) {
      parent.searchCatalogNew();
    }else if (parent.catalogC.getSelectedItem().toString().startsWith("Item")) {
      parent.searchItemCatalog();
    }else if (parent.catalogC.getSelectedItem().toString().startsWith("POS")) {
      parent.searchCatalogNew();
    }
  }
}

/** Create new catalog add
*/
class SearchPTNewChemThread extends Thread {
  SearchPT parent;
  boolean runF = true;
  String action = "";
  public SearchPTNewChemThread(SearchPT parent, String action){
     super("SearchPTNewChemThread");
     this.parent = parent;
     this.action = action;
  }
  public void run(){
    if ("NewMaterial".equalsIgnoreCase(action)) {
      parent.goNewMaterial();
    }else if ("NewSizePackaging".equalsIgnoreCase(action)) {
      parent.goNewSizePacking();
    }else if ("NewPart".equalsIgnoreCase(action)) {
      parent.goNewPart();
    }else if ("NewApproval".equalsIgnoreCase(action)) {
      parent.goNewApproval();
    }
  }
}

class SearchPT_this_keyAdapter extends java.awt.event.KeyAdapter {
  SearchPT adaptee;

  SearchPT_this_keyAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.this_keyPressed(e);
  }
}

class SearchPT_basketRemove_keyAdapter extends java.awt.event.KeyAdapter {
  SearchPT adaptee;

  SearchPT_basketRemove_keyAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.basketRemove_keyPressed(e);
  }
}

class SearchPT_addPR_keyAdapter extends java.awt.event.KeyAdapter {
  SearchPT adaptee;

  SearchPT_addPR_keyAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.addPR_keyPressed(e);
  }
}

class SearchPT_addItem_actionAdapter implements java.awt.event.ActionListener {
  SearchPT adaptee;

  SearchPT_addItem_actionAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.addItem_actionPerformed(e);
  }
}

class SearchPT_searchB_actionAdapter implements java.awt.event.ActionListener{
  SearchPT adaptee;

  SearchPT_searchB_actionAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchB_actionPerformed(e);
  }
}

class SearchPT_searchB_keyAdapter extends java.awt.event.KeyAdapter {
  SearchPT adaptee;

  SearchPT_searchB_keyAdapter(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.searchB_keyPressed(e);
  }
}

class baskTable_CellEditorListener implements CellEditorListener {
  SearchPT adaptee;
  baskTable_CellEditorListener(SearchPT x) {
    adaptee = x;
  }
  public void editingStopped(ChangeEvent e){
    adaptee.basketTable_editingStopped(e);
  }
  public void editingCanceled(ChangeEvent e){
    adaptee.basketTable_editingStopped(e);
  }
}

class SearchPT_catTableRenderer extends TcmisCellRenderer {
  public Component getTableCellRendererComponent(
                          JTable table, Object value,
                          boolean isSelected, boolean hasFocus,
                          int row, int column) {

    boolean sel = false;
    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }
    selectedFormat(sel);

    if(table.getColumnName(column).equalsIgnoreCase("LPP")) {
      this.currencyFormat(value);
      normalFontFormat();
      return this;
    }else{
      this.setHorizontalAlignment(2);
      return super.getTableCellRendererComponent(table, value,isSelected,hasFocus,row,column);
    }
  }
}

class CustomerLookupThread extends Thread {
 SearchPT parent;
  public CustomerLookupThread(SearchPT parent){
     super("CustomerLookupThread");
     this.parent = parent;
  }
  public void run(){
     parent.customerLookup();
  }
}

class CustomerFacilityInfoThread extends Thread {
 SearchPT parent;
  public CustomerFacilityInfoThread(SearchPT parent){
     super("CustomerFacilityInfoThread");
     this.parent = parent;
  }
  public void run(){
     parent.getCustomerFacilityInfo();
  }
}

class CatalogActionListener implements java.awt.event.ActionListener{
  SearchPT adaptee;

  CatalogActionListener(SearchPT adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.catalogC_actionPerformed();
  }
}
