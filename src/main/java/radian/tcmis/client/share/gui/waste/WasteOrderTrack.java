package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.appData.*;
import java.beans.*;
import javax.swing.table.*;
import java.util.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;
import java.awt.event.*;
import javax.swing.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
//Waste order Tracking Screen

public class WasteOrderTrack extends JPanel {
  StaticJLabel wasteOrderL = new StaticJLabel("Waste Order:");
  JTextField wasteOrderT = new JTextField("");
  StaticJLabel vendorL = new StaticJLabel("Vendor:");
  StaticJLabel dateGL = new StaticJLabel("");
  JTextField afterT = new JTextField("");
  StaticJLabel dateLL = new StaticJLabel("");
  JTextField beforeT = new JTextField("");

  CmisApp cmis;
  boolean dataLoaded = false;
  boolean facCLoaded = false;

  static int STATUS_COL =0;
  static int ORDER_NO_COL = 0;

  JScrollPane JSP = new JScrollPane();
  CmisTable trackTable = new CmisTable();
  DbTableModel ctm = new DbTableModel();
  TableSorter sorterTrack = new TableSorter();

  Vector vendorId = null;
  Hashtable initialInfoH = null;
  String lastSelectedFac = new String("");


  public static final String[] colHeads = {"Status","Order","Vendor","Submitted",
                                           "Resubmitted","Cancelled","Shipped","Copy1 Return","Cert of Disp.","Invoice"};
  public static final int[] colWidths = {62,45,110,78,78,78,78,78,80,50};
  String colTypes = null;


  BorderLayout borderLayout1 = new BorderLayout();
  JPanel panel2 = new JPanel();
  JButton reqSearchB = new JButton();
  JButton searchB = new JButton();
  JPanel jPanel1 = new JPanel();
  JButton statusB = new JButton();
  JButton mrB = new JButton();
  ImageIcon searchIcon = new ImageIcon();
  ImageIcon searchIconOver = new ImageIcon();
  JPanel groupBox1 = new JPanel();
  JCheckBox generateC = new JCheckBox();
  JCheckBox cancelledC = new JCheckBox();
  JCheckBox pickupC = new JCheckBox();
  JCheckBox submittedC = new JCheckBox();
  JCheckBox criticalC = new JCheckBox();
  JCheckBox deliveredC = new JCheckBox();
  JCheckBox certOfDestC = new JCheckBox();
  JCheckBox archivedC = new JCheckBox();
  JPanel panel1 = new JPanel();
  JPanel panel3 = new JPanel();
  JPanel panel4 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel panel5 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();

  ImageIcon ss = new ImageIcon();

  // type ahead
  //NextNameTextField whoT = new NextNameTextField("PERSONNEL");

  /*WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
  WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();
  */
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  JPanel jPanel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JCheckBox copy1C = new JCheckBox();
  JCheckBox invoiceC = new JCheckBox();
  StaticJLabel jLabel3 = new StaticJLabel();
  JButton jButton4 = new JButton();
  JComboBox vendor = new JComboBox();
  JComboBox facilityC = new JComboBox();
  StaticJLabel facilityL = new StaticJLabel();
  JComboBox storageC = new JComboBox();
  StaticJLabel storageL = new StaticJLabel();

  public WasteOrderTrack(CmisApp cmis) {
    this.cmis = cmis;
    try {
      jbInit();
      getInitialInfo();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loadIt(){
    WasteOrderTrackLoadThread iT = new WasteOrderTrackLoadThread(this);
    iT.start();
  }
  public void loadDone(){
    if(dataLoaded && facCLoaded){
      ClientHelpObjs.setEnabledContainer(this,true);
      mrB.setEnabled(false);
      this.revalidate();
    }
  }

  public void jbInit() throws Exception {
    this.setLayout(borderLayout4);

    wasteOrderT.setMinimumSize(new Dimension(100, 21));
    wasteOrderT.setPreferredSize(new Dimension(100, 21));
    wasteOrderT.setMaximumSize(new Dimension(100, 21));
    wasteOrderT.setText("");
    wasteOrderT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        wasteOrderT_keyPressed(e);
      }
    });

    afterT.setMinimumSize(new Dimension(100, 21));
    afterT.setPreferredSize(new Dimension(100, 21));
    afterT.setToolTipText("mm/dd/yyyy  ");
    afterT.setMaximumSize(new Dimension(100, 21));
    afterT.setText("");
    beforeT.setMinimumSize(new Dimension(100, 21));
    beforeT.setPreferredSize(new Dimension(100, 21));
    beforeT.setToolTipText("mm/dd/yyyy  ");
    beforeT.setMaximumSize(new Dimension(100, 21));
    beforeT.setText("");

    groupBox1.setLayout(gridBagLayout3);

    searchB.setMaximumSize(new Dimension(200, 23));
    searchB.setText("Search");
    searchB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        searchB_actionPerformed(e);
      }
    });
    //searchB.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/search.gif","Search");
    searchB.setIcon(ss);

    ss = ResourceLoader.getImageIcon("images/button/status_tab.gif","Mr");
    mrB.setIcon(ss);

    jPanel1.setLayout(gridBagLayout4);
    searchB.setPreferredSize(new Dimension(100, 23));
    submittedC.setText("Submitted");

    panel5.setLayout(borderLayout2);
    panel4.setLayout(gridBagLayout5);
    panel3.setLayout(gridBagLayout2);
    panel1.setLayout(gridBagLayout1);

    certOfDestC.setText("Missing Cert Of Disposal");
    pickupC.setText("Shipped");
    invoiceC.setText("Invoiced");
    generateC.setText("Draft");
    cancelledC.setText("Cancelled");
    groupBox1.setBorder(ClientHelpObjs.groupBox("Order Status"));

    searchB.setSelected(true);

    mrB.setText("Waste Order Detail");
    mrB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        mrB_actionPerformed(e);
      }
    });
    mrB.setRolloverEnabled(true);
    mrB.setEnabled(false);

     // setting up the table
    ctm = new DbTableModel(colHeads);
    ctm.setColumnPrefWidth(colWidths);
    sorterTrack = new TableSorter(ctm);
    trackTable = new CmisTable(sorterTrack);
    sorterTrack.addMouseListenerToHeaderInTable(trackTable);

    //Nawaz 10-23-01 Testing
    WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
    WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();

    trackTable.addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
        trackTable_mouseClicked(e);
      }
    });

    trackTable.setToolTipText(null);
    trackTable.getTableHeader().setReorderingAllowed(true);
    trackTable.getTableHeader().setResizingAllowed(true);
    trackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    trackTable.setCellSelectionEnabled(false);
    trackTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    trackTable.getColumnModel();
    trackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    trackTable.setDefaultRenderer(String.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Double.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Boolean.class, checkTableRenderer);

    //setTable();

    JSP.getViewport().setView(trackTable);
    trackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    panel2.setLayout(borderLayout1);
    jPanel2.setLayout(xYLayout1);
    copy1C.setText("Missing Manifest Copy");

    dateLL.setText("Shipped Before:");
    dateGL.setText("Shipped After:");
    wasteOrderL.setText("Waste Order:");
    vendor.setMaximumSize(new Dimension(150, 21));
    vendor.setMinimumSize(new Dimension(135, 21));
    vendor.setPreferredSize(new Dimension(135, 21));
    //vendorC.addItem(new String("CLEAN HARBORS"));
    vendor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        vendor_actionPerformed(e);
      }
    });
    facilityC.setFont(new java.awt.Font("Dialog", 0, 10));
    facilityC.setMaximumSize(new Dimension(135, 21));
    facilityC.setMinimumSize(new Dimension(135, 21));
    facilityC.setPreferredSize(new Dimension(135, 21));
    facilityL.setText("Facility:");
    panel4.setMinimumSize(new Dimension(737, 110));
    panel4.setPreferredSize(new Dimension(737, 110));
    storageC.setMaximumSize(new Dimension(135, 21));
    storageC.setMinimumSize(new Dimension(135, 21));
    storageC.setPreferredSize(new Dimension(135, 21));
    storageL.setText("Storage Area:");
    panel2.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(searchB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(5, 0, 5, 5), 0, 0));
    jPanel1.add(mrB, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(JSP, BorderLayout.CENTER);
    jPanel1.validate();
    searchB.setSize(searchB.getWidth(),mrB.getHeight());
    jPanel1.validate();

    this.add(panel4, BorderLayout.NORTH);

    panel4.add(wasteOrderT, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(wasteOrderL, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(groupBox1, new GridBagConstraints2(5, 0, 1, 4, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));

    groupBox1.add(generateC, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 0, 0));
    groupBox1.add(submittedC, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 0, 0));
    groupBox1.add(cancelledC, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 0, 0));
    groupBox1.add(pickupC, new GridBagConstraints2(1, 0, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    groupBox1.add(invoiceC, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 0, 0));

    panel4.add(jPanel2, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
    panel4.add(vendorL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(dateGL, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(afterT, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(dateLL, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(beforeT, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(copy1C, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel4.add(certOfDestC, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel4.add(vendor, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(facilityC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(facilityL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(storageC, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel4.add(storageL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    this.add(panel2, BorderLayout.CENTER);
    panel2.validate();

    // Tooltip
    ToolTipManager.sharedInstance().setEnabled(false);
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    this.validate();
    this.revalidate();
    this.repaint();
  }

  protected void setTable(){
    trackTable.addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
        trackTable_mouseClicked(e);
      }
    });
    trackTable.setToolTipText(null);
    trackTable.getTableHeader().setReorderingAllowed(true);
    trackTable.getTableHeader().setResizingAllowed(true);
    trackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    trackTable.setCellSelectionEnabled(false);
    trackTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    trackTable.getColumnModel();
    trackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
    WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();

    trackTable.setDefaultRenderer(String.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Double.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Boolean.class, checkTableRenderer);
  }

 public void printScreenData(){
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setFacId(facilityC.getSelectedItem().toString());
    rod.setVendorName(vendor.getSelectedItem().toString());
    String storageArea = null;
    if (storageC.getSelectedItem().toString().startsWith("Select")) {
      storageArea = new String("");
    }else {
      storageArea = this.getStorageAreaId();
    }
    rod.setStorageArea(storageArea);
    //rod.setVendorId(getVendorId());
    rod.setScreen("WOT");

    String tmp = null;
    String tmp2 = "";
    tmp = BothHelpObjs.makeBlankFromNull(afterT.getText());
    if (BothHelpObjs.isBlankString(tmp)) {
      tmp2 = new String("");
    }else {
      tmp2 = tmp;
    }
    rod.setAfter(tmp2);

    tmp = BothHelpObjs.makeBlankFromNull(beforeT.getText());
    if (BothHelpObjs.isBlankString(tmp)) {
      tmp2 = new String("");
    }else {
      tmp2 = tmp;
    }
    rod.setBefore(tmp2);

    tmp = BothHelpObjs.makeBlankFromNull(wasteOrderT.getText());
    if (BothHelpObjs.isBlankString(tmp)) {
      tmp2 = new String("");
    }else {
      tmp2 = tmp;
    }
    rod.setWasteOrder(tmp2);

    rod.setCopy1(copy1C.isSelected());
    rod.setCOD(certOfDestC.isSelected());
    rod.setGenerated(generateC.isSelected());
    rod.setSubmitted(submittedC.isSelected());
    rod.setCancelled(cancelledC.isSelected());
    rod.setPickup(pickupC.isSelected());
    rod.setInvoiced(invoiceC.isSelected());

    rod.loadIt();
  }
  public void stateChanged(String s){
  }

  public Hashtable getSearchInfo(){
    Hashtable h = new Hashtable();
    h.put("SEARCH_FACILITY",facilityC.getSelectedItem().toString());
    //h.put("SEARCH_VENDOR",vendorId.elementAt(vendorC.getSelectedIndex()).toString());
    h.put("SEARCH_VENDOR",vendor.getSelectedItem().toString());
    // trong 9-20
    if (!storageC.getSelectedItem().toString().equalsIgnoreCase("Select a Storage Area")) {
      h.put("STORAGE_AREA",getStorageAreaId());
    }else {
      h.put("STORAGE_AREA",new String("All"));
    }
    h.put("SEARCH_SHIPMENT_NUM",BothHelpObjs.makeBlankFromNull(wasteOrderT.getText()).toString());
    h.put("SHIPPED_AFTER",BothHelpObjs.makeBlankFromNull(afterT.getText()).toString());
    h.put("SHIPPED_BEFORE",BothHelpObjs.makeBlankFromNull(beforeT.getText()).toString());
    h.put("STATUS_DRAFT",new Boolean(generateC.isSelected()));
    h.put("STATUS_SHIPPED",new Boolean(pickupC.isSelected()));
    h.put("STATUS_SUBMITTED",new Boolean(submittedC.isSelected()));
    h.put("STATUS_CANCELLED",new Boolean(cancelledC.isSelected()));
    h.put("STATUS_CERT_OF_DEST",new Boolean(certOfDestC.isSelected()));
    h.put("STATUS_INVOICE",new Boolean(invoiceC.isSelected()));
    h.put("STATUS_COPY1_RETURN",new Boolean(copy1C.isSelected()));
    return h;
  }

  void getInitialInfo() {
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteOrderTracking",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GetInitialInfo"); //String, String
      query.put("USER_ID",cmis.getUserId());
      Hashtable result = cgi.getResultHash(query);

      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      initialInfoH = (Hashtable)result.get("INITIAL_INFO");
      String prefFac = (String)result.get("PREFER_FACILITY");

      Vector facilityIdV = (Vector)initialInfoH.get("FACILITY_ID");
      facilityC = ClientHelpObjs.loadChoiceFromVector(facilityC,facilityIdV);
      facilityC.setSelectedItem(prefFac);
      facilityC.addPropertyChangeListener(new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
          loadStorageVendorArea();
        }
      });
      facilityC.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(ItemEvent evt) {
          loadStorageVendorArea();
        }
      });
      facilityC.setEnabled(true);

      generateC.setSelected(true);

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
      /*TableColumnModel cm = trackTable.getColumnModel();
      for (int i = 0; i < trackTable.getColumnCount(); i++) {
        cm.getColumn(i).setCellRenderer(renderers[0]);
      }*/
      System.out.println("Here 327");
      //font and foreground and background color for columns heading
      String fontName = (String)chargeTableStyle.get("FONT_NAME");
      Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
      Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
      trackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = trackTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }

    } catch (Exception e){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      e.printStackTrace();
    }
    ClientHelpObjs.setEnabledContainer(this,true);
    ClientHelpObjs.setComboBoxUpdateUi(this);
  }

  private void loadStorageVendorArea() {
    //System.out.println("================== how many time do i get call");
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

  void loadItAction() {
   long sTime = new java.util.Date().getTime();
   try{
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("WasteOrderTracking",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","SEARCH"); //String, String
    query.put("SEARCH_INFO", getSearchInfo());

    Hashtable result = cgi.getResultHash(query);

    ClientHelpObjs.setEnabledContainer(this,true);

    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }

    JSP.getViewport().remove(trackTable);
    DbTableModel ctm = buildTableModel((Hashtable)result.get("SEARCH_DATA"));
    TableSorter sorter = new TableSorter(ctm);
    trackTable = new CmisTable(sorter);
    sorter.setColTypeFlag(ctm.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(trackTable);
    trackTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    trackTable.addMouseListener(new WasteOrderTrack_trackTable_mouseAdapter(this));

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
      trackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = trackTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }


    trackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    trackTable.setCellSelectionEnabled(false);
    trackTable.getTableHeader().setReorderingAllowed(true);
    WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
    WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();
    trackTable.setDefaultRenderer(String.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    trackTable.setDefaultRenderer(Double.class, colorTableRenderer);

    // set column widths
    for(int i=0;i<trackTable.getColumnCount();i++){
      int width = ctm.getColumnWidth(i);
      trackTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
      trackTable.getColumn(ctm.getColumnName(i)).setWidth(width);
      if (width==0){
        trackTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
        trackTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
      }
    }

    JSP.getViewport().setView(trackTable);
    ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    if (trackTable.getModel().getRowCount()<=0){
      GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
      g.setMsg("No Records found.");
      g.setVisible(true);
    }

    // clumns
    Hashtable keyCols = (Hashtable) result.get("COLUMN_KEY");

    ORDER_NO_COL = Integer.parseInt((String) keyCols.get("ORDER_NO"));

    long eTime = new java.util.Date().getTime();
    long min = (eTime-sTime);
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date(min));
    String tmsg = "Records Found: " + trackTable.getRowCount() + "     Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
    cmis.getMain().countLabel.put(new Integer(Main.WASTE_ORDER_TRACKING),tmsg);
    cmis.getMain().setStatusBarText((String)cmis.getMain().countLabel.get(new Integer(Main.WASTE_ORDER_TRACKING)));
   } catch (Exception e){
     GenericDlg.showAccessDenied(cmis.getMain());
     ClientHelpObjs.setEnabledContainer(this,true);
     e.printStackTrace();
   }

    afterT.setToolTipText("mm/dd/yyyy  ");
    beforeT.setToolTipText("mm/dd/yyyy  ");
    mrB.setEnabled(false);

    ClientHelpObjs.makeDefaultColors(this);
    ClientHelpObjs.setComboBoxUpdateUi(this);
    this.validate();
    this.revalidate();
    this.repaint();
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


  void setColTypes(){
    sorterTrack.setColTypeFlag(colTypes);
  }



  void mrB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    stopTableEditing();
    if (!isTableSelected()) return;
    int i = trackTable.getSelectedRow();
    cmis.getMain().goWasteOrder(0,(new Integer(trackTable.getModel().getValueAt(i,ORDER_NO_COL).toString())).intValue());
  }

  void facCLoaded(){
    facCLoaded = true;
    loadDone();
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    loadItAction();
  }

  boolean preAudit() {
    String ord = wasteOrderT.getText();
    String ven = vendor.getSelectedItem().toString();
    if ((BothHelpObjs.isBlankString(ord)) && (BothHelpObjs.isBlankString(ven))) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("You must enter a 'Waste Order' number \n or a 'vendor' name. ");
      gd.setVisible(true);
      return false;
    }
    return true;
  }

  void personnelLookup(NextNameTextField nntf){
    SearchPersonnel sp = new SearchPersonnel(cmis.getMain(),"Personnel Lookup",true);
    sp.setGrandParent(cmis);
    sp.setPersonType("PERSONNEL");
    sp.setVisible(true);
    if (sp.getValueId() != null){
      nntf.setUpdating();
      nntf.setText(sp.getValueLast()+", "+sp.getValueFirst());
      Vector v = new Vector();
      v.addElement("first Name");
      v.addElement("last Name");
      v.addElement(sp.getValueId().toString());
      nntf.setWhoV(v);
    }
  }

  void stopTableEditing(){
    if (trackTable==null) return;
    if (trackTable.isEditing()){
      trackTable.getCellEditor().stopCellEditing();
    }
  }

  boolean isTableSelected(){
    return trackTable.getSelectedRowCount() > 0;
  }
  /*
  private void doRightClicked(MouseEvent e){
     //Table clicked
    JFrame F;
    int catSel = trackTable.getSelectedRow();
          WasteMetaPopUp mpu = new WasteMetaPopUp(cmis,
          Integer.parseInt(trackTable.getModel().getValueAt(catSel,WASTE_ITEM_ID_COL).toString())); // Waste Profile Id

          panel1.add(mpu);

          int[] row = new int[MAXSELECTEDROW];
          row   = trackTable.getSelectedRows();
          Vector containerV = new Vector();
          for (int i = 0; i < row.length; i++) {
            containerV.addElement(trackTable.getModel().getValueAt(row[i],this.CONTAINER_ID_COL).toString());
          }
          System.out.println("========== container id: " + containerV);
          mpu.setContainerId(containerV);

          mpu.show(trackTable,e.getX(),e.getY());
         return;
  } */


  void trackTable_mouseClicked(MouseEvent e) {
   /* if (e.isMetaDown()) {
      doRightClicked(e);
    }*/
    int i   = trackTable.getSelectedRow();
    int col = trackTable.getSelectedColumn();
    if (e.getClickCount() == 2) {
      stopTableEditing();
      cmis.getMain().goWasteOrder(0,(new Integer(trackTable.getModel().getValueAt(i,ORDER_NO_COL).toString())).intValue());
    }
    mrB.setEnabled(true);
  }
  /*
  void vendorT_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        if (preAudit())
          loadItAction();
    }
  } */

  void wasteOrderT_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        if (preAudit())
          loadItAction();
    }
  }

  void vendor_actionPerformed(ActionEvent e) {

  }
}

class WasteOrderTrackLoadThread extends Thread {
 WasteOrderTrack parent;
  public WasteOrderTrackLoadThread(WasteOrderTrack parent){
     super("WasteOrderTrackLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class WasteOrderTrack_trackTable_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteOrderTrack adaptee;
  WasteOrderTrack_trackTable_mouseAdapter(WasteOrderTrack adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.trackTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}
