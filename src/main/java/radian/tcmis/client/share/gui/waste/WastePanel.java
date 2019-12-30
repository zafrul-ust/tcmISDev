
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  This is the Waste Catalog Screen.

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
//import radian.tcmis.client.share.reports.*;
import java.awt.event.*;
import javax.swing.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class WastePanel extends JPanel {
  CmisApp cmis;

  CmisTable wasteTable;
  CmisTable cartTable;
  TableSorter sorter = new TableSorter();

  //trong 4/17
  String mySelectedAccSys;
  Hashtable initialInfoH = null;

  public static final String[] colHeads = {"State Waste Code","Description","Packaging","Vend Profile","Vendor","Waste Category","Waste Type","Waste Mgmt Opt","Rad Profile","Facility","lbs Full"};
  public static final int[] colWidths = {125,110,80,110,75,115,110,70,70,80,50};

  String [] cartCols = new String[]{"Accumulation Pt","Vend Profile","Description","Qty","Packaging","Profile Id","Currency ID"};
  public static final int[] catColWidths = {90,80,150,40,80,0,0};

  final int CART_WORK_AREA = 0;
  final int CART_VENDOR_PROFILE = 1;
  final int CART_DESCRIPTION = 2;
  final int CART_QTY = 3;
  final int CART_PACKAGING = 4;
  final int CART_PROFILE_ID = 5;
  final int CART_CURRENCY_ID = 6;

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
  JComboBox facilityC = new JComboBox();
  JComboBox generateC = new JComboBox();
  JScrollPane jsp = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JScrollPane cartJsp = new JScrollPane();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton addB = new JButton();
  JButton removeB = new JButton();
  JButton createB = new JButton();
  StaticJLabel searchL = new StaticJLabel();
  JTextField searchF = new JTextField();
  JPanel panel1 = new JPanel();
  String lastSelectedFac = new String("");

  //trong 5/1
  JCheckBox previousC = new JCheckBox();

  // columns
  int WASTE_ID;
  int PROFILE_ID;
  int VENDOR_PROFILE;
  int VENDOR;
  int DESCRIPTION;
  int PACKAGING;
  int WORK_AREA;
  int CURRENCY_ID;


  public WastePanel() {
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

   public void printScreenData() {
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setFacId(this.facilityC.getSelectedItem().toString());
    String generatePt = null;
    if (generateC.getSelectedItem().toString().equalsIgnoreCase("Select an Accumulation Pt")) {
      generatePt = "";
    } else {
      generatePt = getGenerationAreaId();
    }
    rod.setGenerationPt(generatePt);
    rod.setPreviousTransfer(previousC.isSelected());
    String mySearchT = BothHelpObjs.makeBlankFromNull(searchF.getText().toString());
    if (BothHelpObjs.isBlankString(mySearchT) || mySearchT == null) {
      mySearchT = new String("");
    }
    rod.setSearchText(mySearchT);
    rod.setRequestorName(requesterT.getText());
    rod.setScreen("WC");
    rod.loadIt();
  }

  private void dataLoaded(){
    TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",cmis);
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

    Vector facilityIdV = (Vector)initialInfoH.get("FACILITY_ID");
    facilityC = ClientHelpObjs.loadChoiceFromVector(facilityC,facilityIdV);
    facilityC.setSelectedItem(prefFac);
    facilityC.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        loadGenerationArea();
      }
    });
    facilityC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
        loadGenerationArea();
      }
    });

    facilityC.setEnabled(true);

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
    enableButtons();
    ClientHelpObjs.makeDefaultColors(this);
  }

  private void loadGenerationArea() {
    //System.out.println("================== how many time do i get call");
    String facId = facilityC.getSelectedItem().toString();
    if (!lastSelectedFac.equals(facId)) {
      generateC.removeAllItems();
      Hashtable locationInfo = (Hashtable)initialInfoH.get(facId);
      Vector locationDesc = (Vector)locationInfo.get("LOCATION_DESC");
      generateC = ClientHelpObjs.loadChoiceFromVector(generateC,locationDesc);
      if (locationDesc.size() > 1 ) {
        generateC.insertItemAt("Select an Accumulation Pt",0);
        generateC.setSelectedIndex(0);
      }
    }
    lastSelectedFac = facId;
    generateC.setEnabled(true);
  }
  public String getGenerationAreaId() {
    String key = facilityC.getSelectedItem().toString();
    Hashtable locationInfo = (Hashtable)initialInfoH.get(key);
    Vector wasteLocationId = (Vector)locationInfo.get("WASTE_LOCATION_ID");
    int pos = generateC.getSelectedIndex();
    String generateId;
    if (wasteLocationId.size() > 1) {
      generateId = wasteLocationId.elementAt(pos-1).toString();
    }else {
      generateId = wasteLocationId.elementAt(pos).toString();
    }
    return generateId;
  }

  //trong 4/10
  public void showAccSysDlg() {
    boolean selected = false;
    String prefAccSys = new String("");
    String fac = facilityC.getSelectedItem().toString();
    String title = "Select an Account System for: " + fac;
    Hashtable acctSys = (Hashtable)initialInfoH.get(fac);
    Vector vv = (Vector)acctSys.get("ACCOUNT_SYS_ID");
    Vector prefAccV = (Vector)acctSys.get("PREF_ACC_SYS");
    int pos = 0;
    //going thru preferred account sys to see if the account is user pref acc sys
    for (int i = 0; i < prefAccV.size(); i++) {
      String tmp = prefAccV.elementAt(i).toString();
      if (tmp.equalsIgnoreCase("y")) {
        pos = i;
      }
    }
    prefAccSys = vv.elementAt(pos).toString();

    if (vv.size() == 1) {
      mySelectedAccSys = (String)vv.elementAt(0);
    } else {
      while (!selected) {
        AccountSystemDlg asd = new AccountSystemDlg(cmis.getMain(),title,true);
        for (int i = 0; i < vv.size(); i++)
          asd.accSysC.addItem(vv.elementAt(i).toString());
        if (vv.size() > 1 && BothHelpObjs.isBlankString(prefAccSys)) {
          asd.accSysC.addItem("Select an Account System");
          asd.accSysC.setSelectedItem("Select an Account System");
        } else if (prefAccSys.length() > 0) {
          asd.accSysC.setSelectedItem(prefAccSys);
        } else {
          asd.accSysC.setSelectedIndex(0);
        }
        asd.setVisible(true);
        mySelectedAccSys = asd.getSelectedAccSys();
        if (!mySelectedAccSys.equalsIgnoreCase("Select an Account System"))
          selected = true;
      }
    }
  }

  private void enableButtons(){
    addB.setEnabled(wasteTable.getSelectedRowCount()>0 && !BothHelpObjs.isBlankString(generateC.getSelectedItem().toString()));
    removeB.setEnabled(cartTable.getSelectedRowCount()>0);
    createB.setEnabled(cartTable.getRowCount()>0);
    facilityC.setEnabled(cartTable.getRowCount()<1);
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    topP.setMaximumSize(new Dimension(32767, 170));
    topP.setMinimumSize(new Dimension(10, 170));
    topP.setPreferredSize(new Dimension(450, 170));
    topP.setLayout(gridBagLayout2);
    bottomP.setLayout(borderLayout1);
    leftP.setLayout(gridBagLayout3);
    searchL.setHorizontalAlignment(SwingConstants.RIGHT);
    searchL.setText("Search:");
    requesterL.setHorizontalAlignment(SwingConstants.RIGHT);
    requesterL.setText("Requester:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Facility:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Accumulation Pt:");
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
    createB.setText("Generate Waste Mgt. Request");
    createB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createB_actionPerformed(e);
      }
    });
    addB.setEnabled(false);
    removeB.setEnabled(false);
    createB.setEnabled(false);
    searchB.setEnabled(true);
    requesterT.setEnabled(false);

    searchF.setMaximumSize(new Dimension(180, 24));
    searchF.setMinimumSize(new Dimension(180, 24));
    searchF.setPreferredSize(new Dimension(180, 24));
    searchF.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseClicked(MouseEvent e) {
        searchFieldClicked(e);
      }
    });

    searchF.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        searchF_keyPressed(e);
      }
    });


    requesterT.setMaximumSize(new Dimension(160, 24));
    requesterT.setMinimumSize(new Dimension(160, 24));
    requesterT.setPreferredSize(new Dimension(160, 24));
    facilityC.setEnabled(false);
    facilityC.setMaximumSize(new Dimension(160, 24));
    facilityC.setMinimumSize(new Dimension(160, 24));
    facilityC.setPreferredSize(new Dimension(160, 24));
    generateC.setEnabled(false);
    generateC.setMaximumSize(new Dimension(160, 24));
    generateC.setMinimumSize(new Dimension(160, 24));
    generateC.setPreferredSize(new Dimension(160, 24));

    DbTableModel ctm = new DbTableModel(cartCols);
    ctm.setEditableFlag(8);
    ctm.setColumnPrefWidth(catColWidths);
    cartTable = new CmisTable(ctm);
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
    cartTable.addMouseListener(new WastePanel_cartTable_mouseAdapter(this));

    DbTableModel ta = new DbTableModel(colHeads);
    ta.setColumnPrefWidth(colWidths);
    wasteTable = new CmisTable(ta);
    wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


    jsp.getViewport().setView(wasteTable);
    wasteTable.addMouseListener(new WastePanel_wasteTable_mouseAdapter(this));


    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif","Search"));
    addB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add"));
    removeB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif","Delete"));
    createB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Submit"));

    //trong 5/1
    previousC.setText("Previous Transfer Only");



    this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 25));
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
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(requesterT, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 85, 3, 3), 0, 0));
    leftP.add(jLabel1, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(facilityC, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 85, 3, 3), 0, 0));
    leftP.add(jLabel2, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(generateC, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 85, 3, 3), 0, 0));
    leftP.add(searchL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    leftP.add(searchF, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 85, 3, 3), -2, 0));
    leftP.add(searchB, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 3, 0), 0, 0));
    leftP.add(previousC, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 110, 3, 3), 0, 0));

    this.add(bottomP, new GridBagConstraints2(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 265));
    bottomP.add(jsp, BorderLayout.CENTER);
  }

   public void searchFieldClicked(MouseEvent e) {
  //  searchF.setText(null);
  //  searchB.setEnabled(true);
  }

  void doSearch(){
    long sTime = new java.util.Date().getTime();
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","SEARCH"); //String, String
      query.put("FAC_ID",facilityC.getSelectedItem().toString());
      if (generateC.getSelectedItem().toString().equalsIgnoreCase("Select an Accumulation Pt")) {
        query.put("WORK_AREA",new String(""));
      } else {
        query.put("WORK_AREA",getGenerationAreaId());
      }
      query.put("SEARCH_STRING",searchF.getText());
      query.put("PREVIOUS_TRANSFER",new Boolean(previousC.isSelected()));
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

      wasteTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      wasteTable.addMouseListener(new WastePanel_wasteTable_mouseAdapter(this));
      wasteTable.getTableHeader().setReorderingAllowed(true);
      wasteTable.getTableHeader().setResizingAllowed(true);

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
      }
      // columns
      Hashtable h = (Hashtable) result.get("KEY_COLS");
      PROFILE_ID = ((Integer)h.get("PROFILE_ID")).intValue();
      VENDOR_PROFILE = ((Integer)h.get("VENDOR_PROFILE")).intValue();
      VENDOR = ((Integer)h.get("VENDOR")).intValue();
      PACKAGING = ((Integer)h.get("PACKAGING")).intValue();
      DESCRIPTION =  ((Integer)h.get("DESCRIPTION")).intValue();
      CURRENCY_ID = ((Integer)h.get("CURRENCY_ID")).intValue();

      long eTime = new java.util.Date().getTime();
      long min = (eTime-sTime);
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(new Date(min));
      String tmsg = "Records Found: " + wasteTable.getRowCount() + "     Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
      cmis.getMain().countLabel.put(new Integer(Main.WASTE),tmsg);
      cmis.getMain().setStatusBarText((String)cmis.getMain().countLabel.get(new Integer(Main.WASTE)));

    }catch(Exception e){
      e.printStackTrace();
    }finally{
      ClientHelpObjs.setEnabledContainer(this,true);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      enableButtons();
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
      this.revalidate();
    }
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

  protected void createWasteRequest(){
    if(cartTable.getRowCount()<1)return;
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","CREATE_WASTE_REQUEST"); //String, String
      query.put("WASTE_REQUEST",buildWasteRequestVector());
      query.put("FAC_ID",facilityC.getSelectedItem().toString()); //String, String
      query.put("USER_ID",cmis.getUserId()); //String, String

      //trong 5/3
      query.put("ACC_SYS_ID",mySelectedAccSys);


      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      int wr = 0;
      if(result.containsKey("WASTE_REQUEST_NUM")){
        wr = ((Integer)result.get("WASTE_REQUEST_NUM")).intValue();
      }

      GenericDlg gd = new GenericDlg(cmis.getMain(),true);
      CenterComponent.centerCompOnScreen(gd);
      if(wr>0){
        removeAllFromCart();
        cmis.getMain().goWasteRequest(0,wr,(Hashtable)result.get("REQUEST_DATA"));
      }else{
        gd.setTitle("Error creating Waste Request");
        gd.setMsg("A Waste Management Request could not be created. Please check your data and try again.");
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
  private Vector buildWasteRequestVector(){
    if (cartTable.isEditing()) {
      cartTable.getCellEditor().stopCellEditing();
    }
    Vector v = new Vector();
    for(int i=0;i<cartTable.getRowCount();i++){
      Hashtable h = new Hashtable();
      h.put("WORK_AREA",cartTable.getModel().getValueAt(i,CART_WORK_AREA).toString());
      h.put("FACILITY_ID",facilityC.getSelectedItem().toString());
      h.put("WASTE_ITEM_ID",new Integer(cartTable.getModel().getValueAt(i,CART_PROFILE_ID).toString()));
      h.put("QTY",new Integer(cartTable.getModel().getValueAt(i,CART_QTY).toString()));
      h.put("CURRENCY_ID",cartTable.getModel().getValueAt(i,CART_CURRENCY_ID).toString());
      v.addElement(h);
    }
    return v;
  }

  private void doRightClick(JTable table, MouseEvent e){
     //Table clicked
    JFrame F;
    int catSel = table.getSelectedRow();
          WasteMetaPopUp mpu = new WasteMetaPopUp(cmis,Integer.parseInt(table.getModel().getValueAt(catSel,PROFILE_ID).toString())); // Waste Profile Id

          panel1.add(mpu);
          //Integer itemNo = new Integer(catTable.getModel().getValueAt(catSel, 0).toString());
          //metaItem = itemNo.intValue();
          mpu.show(table,e.getX(),e.getY());

         return;
  }

  private boolean canAddToCart(){
    if(cartTable.getRowCount() < 1 && wasteTable.getSelectedRowCount() > 0){
      return true;
    }
    if(wasteTable.getSelectedRowCount() < 1)return false;
    String rpi = wasteTable.getModel().getValueAt(wasteTable.getSelectedRow(),PROFILE_ID).toString();
    String currentAccPt = generateC.getSelectedItem().toString();
    for(int i=0;i<cartTable.getRowCount();i++){
      if(rpi.equals(cartTable.getModel().getValueAt(i,CART_PROFILE_ID).toString()) &&
         currentAccPt.equals(cartTable.getModel().getValueAt(i,CART_WORK_AREA).toString())){
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
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Profile Already Selected.",true);
      gd.setMsg("You have already selected this profile for the selected accumulation pt.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }

    //waste must be in the selected work area.
    if(generateC.getSelectedItem().toString().equalsIgnoreCase("Select an Accumulation Pt") ||
       BothHelpObjs.isBlankString(generateC.getSelectedItem().toString())){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Select an Accumulation Pt.",true);
      gd.setMsg("Please select an accumulation pt.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }

    int row = wasteTable.getSelectedRow();
    Object[] oa = new Object[cartCols.length];
    oa[CART_WORK_AREA] =  getGenerationAreaId();
    oa[CART_QTY] =  "1";
    oa[CART_PACKAGING] = wasteTable.getModel().getValueAt(row,PACKAGING);
    oa[CART_VENDOR_PROFILE] = wasteTable.getModel().getValueAt(row,VENDOR_PROFILE);
    oa[CART_PROFILE_ID] = wasteTable.getModel().getValueAt(row,PROFILE_ID);
    oa[CART_DESCRIPTION] = wasteTable.getModel().getValueAt(row,DESCRIPTION);
    oa[CART_CURRENCY_ID] = wasteTable.getModel().getValueAt(row,CURRENCY_ID);
    DbTableModel model = (DbTableModel)cartTable.getModel();
    model.addRow(oa);
    enableButtons();
  }

  private void removeFromCart(){
    int[] r = cartTable.getSelectedRows();
    int x = cartTable.getSelectedRowCount();
    for(int i= x - 1; i >= 0;i--){
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
    WastePanelLoadThread wplt = new WastePanelLoadThread(this,"search");
    wplt.start();
  }

  void searchF_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      WastePanelLoadThread wplt = new WastePanelLoadThread(this,"search");
      wplt.start();
    }
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
    if (cartTable.isEditing()) {
      cartTable.getCellEditor().stopCellEditing();
    }

    //trong 4/17
    showAccSysDlg();

    WastePanelLoadThread wplt = new WastePanelLoadThread(this,"createWR");
    wplt.start();
  }
  void wasteTable_mouseClicked(MouseEvent e){
    if(e.isMetaDown()){
      doRightClick(wasteTable,e);
    }else if(e.getClickCount() == 2){
      addToCart();
    }
    enableButtons();
  }
  void cartTable_mouseClicked(MouseEvent e){
    if(e.isMetaDown()){
      doRightClick(cartTable,e);
    }
    enableButtons();
  }

  void waC_actionPerformed(ActionEvent e) {
    enableButtons();
  }
}
class WastePanelLoadThread extends Thread{
  WastePanel wp;
  String action;
  public WastePanelLoadThread(WastePanel wp,String action){
    super();
    this.wp = wp;
    this.action = action;
  }
  public void run(){
    if(action.equalsIgnoreCase("search")){
      wp.doSearch();
    }else if(action.equalsIgnoreCase("createwr")){
      wp.createWasteRequest();
    }
  }
}
class WastePanel_wasteTable_mouseAdapter extends java.awt.event.MouseAdapter {
  WastePanel adaptee;
  WastePanel_wasteTable_mouseAdapter(WastePanel adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.wasteTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}
class WastePanel_cartTable_mouseAdapter extends java.awt.event.MouseAdapter {
  WastePanel adaptee;
  WastePanel_cartTable_mouseAdapter(WastePanel adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.cartTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}

