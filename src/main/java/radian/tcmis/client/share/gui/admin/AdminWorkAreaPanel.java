
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.admin;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;
import java.awt.Component;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AdminWorkAreaPanel extends AdminInputPanel { //JPanel { //
//public class AdminWorkAreaPanel extends JPanel { //
  String[] colHead = new String[] {"Work Area ID","Work Area Description","Location","Deliver To ID","Deliver To Description","Status"};
  int[] colWidth = new int[] {75,180,120,80,180,80};
  FacilityCombo facC = new FacilityCombo();
  JButton addB = new JButton("Add");
  JButton saveB = new JButton("Save");
  StaticJLabel facL = new StaticJLabel("Facility:");
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  boolean facCLoaded = false;
  boolean dbDataLoaded = false;
  boolean tableLoaded = false;
  JScrollPane jsp = new JScrollPane();
  JTable myTable = new JTable();
  String lastFacId = "";

  Object[][] workAreaData;
  Vector locIds = new Vector();
  Vector myFacLocIds = new Vector();
  protected int FAC_COL = 0;
  protected int WA_ID_COL = 1;
  protected int WA_DESC_COL = 2;
  protected int LOCATION_COL = 3;
  protected int DELIVERY_ID_COL = 4;
  protected int DELIVERY_DESC_COL = 5;
  protected int WORKAREA_STATUS_COL = 6;
  protected int CHANGED_COL = 7;
  Vector statusV = null;

  JComboBox fac = new JComboBox();
  Vector facIDs;
  Vector facNames;
  AdminWinNew adminWin;
  BorderLayout borderLayout2 = new BorderLayout();

  public AdminWorkAreaPanel(AdminWinNew aw) {
    super(aw,"Work Areas");
    adminWin = aw;
    try  {
      jbInit();
      loadThisPanel();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    jPanel1.setLayout(gridBagLayout2);
    facL.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel2.setLayout(borderLayout2);
    saveB.setMaximumSize(new Dimension(90, 23));
    saveB.setText("Save");
    saveB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveB_actionPerformed(e);
      }
    });
    facC.setMaximumSize(new Dimension(120, 27));
    facC.setPreferredSize(new Dimension(120, 27));
    facC.setMinimumSize(new Dimension(120, 27));
    facC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        facC_actionPerformed(e);
      }
    });
    addB.setMaximumSize(new Dimension(90, 23));
    addB.setText("Add");
    addB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });

    addB.setPreferredSize(new Dimension(90, 23));
    saveB.setPreferredSize(new Dimension(90, 23));
    addB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));
    addB.setMinimumSize(new Dimension(90, 23));
    saveB.setIcon(ResourceLoader.getImageIcon("images/button/save.gif"));
    saveB.setMinimumSize(new Dimension(90, 23));

    fac.setMaximumSize(new Dimension(32767, 23));
    fac.setMinimumSize(new Dimension(180, 23));
    fac.setPreferredSize(new Dimension(180, 23));

    //top
    this.add(jPanel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(facL, new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 3, 5, 3), 0, 0));
    jPanel1.add(fac, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 3, 5, 20), 0, 0));
    jPanel1.add(saveB, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 20, 5, 5), 0, 0));
    jPanel1.add(addB, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 3, 5, 5), 0, 0));
    //bottom
    this.add(jPanel2, new GridBagConstraints2(0, 1, 1, 1, 3.0, 3.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
    jPanel2.add(jsp, BorderLayout.CENTER);

    ClientHelpObjs.makeDefaultColors(this);
    saveB.setEnabled(false);
    addB.setEnabled(false);
    validate();
  }


  public void setFacCombo(FacilityCombo fc){
    this.getButton().setText("Work Area");

    facC.setCmisApp(getCmisApp());
    facC.setUseAllFacilities(false);
    if(!getCmisApp().getGroupMembership().isSuperUser()){
      facC.restrictToAdminFacilities(true);
    }
    fc.loadAnotherOne(facC);
    facC.setSelectedIndex(0);
    facCLoaded = true;
    loaded();
  }
  public void loadIt(){
    this.startLoad();
  }

  public void loadThisPanel(){
    ClientHelpObjs.setEnabledContainer(this,false);
    dbDataLoaded = false;
    tableLoaded = false;
    TcmisHttpConnection cgi = new TcmisHttpConnection("AdminNew",adminWin.cmis);
    Hashtable query = new Hashtable();
    String action = "LOAD_WORK_AREA_INFO";
    query.put("ACTION",action); //String, String
    query.put("USERID",(new Integer(adminWin.cmis.getUserId().intValue())).toString()); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      if(!this.oopsReload){
        oopsReload = true;
        loadThisPanel();
        return;
      }
      GenericDlg.showAccessDenied(adminWin.cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }

    facIDs = (Vector)result.get("FACILITY_ID");
    facNames = (Vector)result.get("FACILITY_NAME");
    fac.removeAllItems();
    fac = ClientHelpObjs.loadChoiceFromVector(fac,facNames);
    fac.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fac_actionPerformed(e);
      }
    });

    Vector v = (Vector)result.get("DATA");
    if(v.size() < 2){
      workAreaData = new Object[1][1];
      workAreaData[0][0] = "";
    }else{
      Object[][] oa = BothHelpObjs.get2DArrayFromVector(v,7);
      workAreaData = new Object[oa.length][8];
      for(int i=0;i<oa.length;i++){
        workAreaData[i][FAC_COL] = oa[i][FAC_COL];
        workAreaData[i][WA_ID_COL] = oa[i][WA_ID_COL];
        workAreaData[i][WA_DESC_COL] = oa[i][WA_DESC_COL];
        workAreaData[i][LOCATION_COL] = oa[i][LOCATION_COL];
        workAreaData[i][DELIVERY_ID_COL] = oa[i][DELIVERY_ID_COL];
        workAreaData[i][DELIVERY_DESC_COL] = oa[i][DELIVERY_DESC_COL];
        workAreaData[i][WORKAREA_STATUS_COL] = oa[i][WORKAREA_STATUS_COL];
        workAreaData[i][CHANGED_COL] = "";
      }
    }
    statusV = (Vector)result.get("STATUS");
    dbDataLoaded = true;
    loaded();

    locIds = (Vector)result.get("LOCATIONS");
    locIds = BothHelpObjs.sort(locIds);
  } //end of method

  public void printScreenData() {


  }

  void fac_actionPerformed(ActionEvent e) {
    System.out.println("------------ fac combo action: ");
    loadTable();
  }

  void saveChanges(){
    getChanges();
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION","SAVE_WORK_AREA_INFO"); //String, String
    query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String

    String locs = "";
    String appids = "";
    String appnames = "";
    String facs = "";
    String myStatus = "";
    String deliveryID = "";
    String deliveryDesc = "";
    for(int i=0;i<workAreaData.length;i++){
      if(workAreaData[i][CHANGED_COL].toString().equalsIgnoreCase("X")){
        facs = facs + BothHelpObjs.VALUE_VALUE_DEL + workAreaData[i][FAC_COL].toString();
        appids = appids + BothHelpObjs.VALUE_VALUE_DEL + workAreaData[i][WA_ID_COL].toString();
        appnames = appnames + BothHelpObjs.VALUE_VALUE_DEL + workAreaData[i][WA_DESC_COL].toString();
        locs = locs + BothHelpObjs.VALUE_VALUE_DEL + workAreaData[i][LOCATION_COL].toString();
        myStatus = myStatus + BothHelpObjs.VALUE_VALUE_DEL + workAreaData[i][WORKAREA_STATUS_COL].toString();   //trong 1-29-01
        deliveryID = deliveryID + BothHelpObjs.VALUE_VALUE_DEL + workAreaData[i][DELIVERY_ID_COL].toString();
        deliveryDesc = deliveryDesc + BothHelpObjs.VALUE_VALUE_DEL + workAreaData[i][DELIVERY_DESC_COL].toString();
      }
    }
    query.put("LOCATIONS",locs);
    query.put("FAC_IDS",facs);
    query.put("WORK_AREA_IDS",appids);
    query.put("WORK_AREA_DESC",appnames);
    query.put("WORKAREA_STATUS",myStatus);   //trong 1-29-01
    query.put("DELIVERY_ID",deliveryID);
    query.put("DELIVERY_DESC",deliveryDesc);

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }
    Vector v = (Vector)result.get("DATA");
    String msg = "";
    if(v == null || v.isEmpty() || !v.elementAt(0).toString().equalsIgnoreCase("true")){
      msg = "The requested update function was not completed.";
    }else{
      msg = "The requested update function was completed.";
      for(int i=0;i<workAreaData.length;i++){
        workAreaData[i][CHANGED_COL] = "";
      }
    }
    GenericDlg cd = new GenericDlg(getCmisApp().getMain());
    cd.setMsg(msg);
    CenterComponent.centerCompOnScreen(cd);
    cd.setVisible(true);
    loadTable();
  }

  void deleteWorkArea(){
    int row = myTable.getSelectedRow();
    if(row < 0){
      GenericDlg gd = new GenericDlg(getCmisApp().getMain(),"Select Work Area",true);
      gd.setMsg("You must select a work area to delete.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }
    ConfirmDlg cd = new ConfirmDlg(getCmisApp().getMain(),"Confirm Delete",true);
    cd.setMsg("Do you want to delete the selected work area?");
    CenterComponent.centerCompOnScreen(cd);
    cd.setVisible(true);
    if(!cd.getConfirmationFlag())return;

    String myAppId =  myTable.getValueAt(row,0).toString();
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION","DELETE_WORK_AREA"); //String, String
    query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String
    query.put("LOCATION_ID",myAppId);
    query.put("FACILITY_ID",facC.getSelectedFacId());

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }
    Vector v = (Vector)result.get("DATA");
    String msg = "";

    if(v == null || v.isEmpty() || !v.elementAt(0).toString().equalsIgnoreCase("true")){
      msg = "The selected work area was NOT deleted.";
    }else{
      msg = "The selected work area was deleted.";
      removeFromList(myAppId,facC.getSelectedFacId());
    }
    GenericDlg gd = new GenericDlg(getCmisApp().getMain(),"Deletion Result",true);
    gd.setMsg(msg);
    CenterComponent.centerCompOnScreen(gd);
    gd.setVisible(true);
    loadTable();
  }


  void addWorkArea(){
    AddWorkAreaDlg awad = new AddWorkAreaDlg(getCmisApp().getMain(),"Add Work Area",true,facC.getSelectedFacId(),facC.getSelectedItem().toString(),myFacLocIds,getCmisApp());
    CenterComponent.centerCompOnScreen(awad);
    awad.setVisible(true);
    if(awad.getRecAdded()){
      Object[][] oa = new Object[workAreaData.length + 1][8];
      for(int i=0;i<workAreaData.length;i++){
        oa[i] = workAreaData[i];
      }
      oa[workAreaData.length][FAC_COL] = awad.getFacId();
      oa[workAreaData.length][WA_ID_COL] = awad.getWorkAreaId();
      oa[workAreaData.length][WA_DESC_COL] = awad.getWorkAreaDesc();
      oa[workAreaData.length][LOCATION_COL] = awad.getLocId();
      oa[workAreaData.length][DELIVERY_ID_COL] = "";
      oa[workAreaData.length][DELIVERY_DESC_COL] = "";
      oa[workAreaData.length][WORKAREA_STATUS_COL] = "Active";
      oa[workAreaData.length][CHANGED_COL] = "";
      workAreaData = oa;
      loadTable();
    }
  }

  void loadTable(){
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    if(tableLoaded){
      getChanges();
    }
    loadMyFacLocIds();
    /*
    lastFacId = facC.getSelectedFacId();
    String id = facC.getSelectedFacId();
    */
    lastFacId = fac.getSelectedItem().toString();
    String id = fac.getSelectedItem().toString();

    int rows = getNumRows(id);
    //int cols = 3;
    int cols = 6;
    int rowI = 0;
    Object[][] data = new Object[rows][cols];
    for(int i=0;i<workAreaData.length;i++){
      if(!workAreaData[i][FAC_COL].toString().equals(id)) continue;
      data[rowI][0] = (Object)workAreaData[i][WA_ID_COL];  // ID
      data[rowI][1] = (Object)workAreaData[i][WA_DESC_COL];  // Desc
      //data[rowI][2] = getComboBox(workAreaData[i][LOCATION_COL].toString());  // Location
      data[rowI][2] = workAreaData[i][LOCATION_COL].toString();  // Location
      //data[rowI][3] = getDeliverComboBox(workAreaData[i][DELIVERY_ID_COL].toString());  // Deliver to ID
      data[rowI][3] = workAreaData[i][DELIVERY_ID_COL].toString();  // Deliver to ID
      data[rowI][4] = workAreaData[i][DELIVERY_DESC_COL].toString();  // Deliver to Desc
      //data[rowI][5] = getStatusComboBox(workAreaData[i][WORKAREA_STATUS_COL].toString());     //status
      data[rowI][5] = workAreaData[i][WORKAREA_STATUS_COL].toString();
      rowI++;
    }
    buildTable(data);
    ClientHelpObjs.setEnabledContainer(this,true);
    this.revalidate();
    tableLoaded = true;
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  JComboBox getDeliveryPointComboBox(String s){
    JComboBox j = new JComboBox();
    j = ClientHelpObjs.loadChoiceFromVector(new JComboBox(),statusV);
    j.setSelectedItem(s);
    j.setEditable(false);
    j.setMaximumRowCount(statusV.size());
    j.revalidate();
    return j;
  }

  //trong 1-29-01
  JComboBox getStatusComboBox(String s){
    JComboBox j = new JComboBox();
    j = ClientHelpObjs.loadChoiceFromVector(new JComboBox(),statusV);
    j.setSelectedItem(s);
    j.setEditable(false);
    j.setMaximumRowCount(statusV.size());
    j.revalidate();
    return j;
  }

  void loadMyFacLocIds(){
    //String facId = facC.getSelectedFacId();
    String facId = fac.getSelectedItem().toString();
    myFacLocIds = new Vector();
    for(int i=0;i<workAreaData.length;i++){
      if(!workAreaData[i][FAC_COL].toString().equals(facId)) continue;
      if(!myFacLocIds.contains(workAreaData[i][LOCATION_COL]))myFacLocIds.addElement(workAreaData[i][LOCATION_COL]);
    }
  }

  void getChanges(){
    if(myTable.isEditing()){
      myTable.editingStopped(new ChangeEvent(myTable));
    }
    int rows = myTable.getModel().getRowCount();

    // step through the table
    for(int i=0;i<rows;i++){
      // step through the master data array
      for(int j=0;j<workAreaData.length;j++){
        //must match facility
        if(!workAreaData[j][FAC_COL].toString().equals(lastFacId)){
          continue;
        }
        // must match work area id
        if(!workAreaData[j][WA_ID_COL].toString().equals(myTable.getModel().getValueAt(i,WA_ID_COL).toString())){
          continue;
        }
        // only update if a change is made
        if(workAreaData[j][WA_DESC_COL].toString().equals(myTable.getModel().getValueAt(i,WA_DESC_COL).toString())
          && workAreaData[j][LOCATION_COL].toString().equals(((JComboBox)myTable.getModel().getValueAt(i,LOCATION_COL)).getSelectedItem().toString())
          && workAreaData[j][DELIVERY_ID_COL].toString().equals(((JComboBox)myTable.getModel().getValueAt(i,DELIVERY_ID_COL)).getSelectedItem().toString())
          && workAreaData[j][DELIVERY_DESC_COL].toString().equals(((JComboBox)myTable.getModel().getValueAt(i,DELIVERY_DESC_COL)).getSelectedItem().toString())
          && workAreaData[j][WORKAREA_STATUS_COL].toString().equals(((JComboBox)myTable.getModel().getValueAt(i,WORKAREA_STATUS_COL)).getSelectedItem().toString())){
            continue;
        }
        workAreaData[j][WA_DESC_COL] = myTable.getModel().getValueAt(i,WA_DESC_COL).toString();
        workAreaData[j][LOCATION_COL] = ((JComboBox)myTable.getModel().getValueAt(i,LOCATION_COL)).getSelectedItem().toString();
        workAreaData[j][DELIVERY_ID_COL] = ((JComboBox)myTable.getModel().getValueAt(i,DELIVERY_ID_COL)).getSelectedItem().toString();
        workAreaData[j][DELIVERY_DESC_COL] = ((JComboBox)myTable.getModel().getValueAt(i,DELIVERY_DESC_COL)).getSelectedItem().toString();
        workAreaData[j][WORKAREA_STATUS_COL] = ((JComboBox)myTable.getModel().getValueAt(i,WORKAREA_STATUS_COL)).getSelectedItem().toString();
        // change switch
        //System.out.println("marked as changed");
        workAreaData[j][CHANGED_COL] = "X";
        break;
      }
    }
  }

  void removeFromList(String app, String fac){
    for(int i=0;i<workAreaData.length;i++){
      if(workAreaData[i][FAC_COL].toString().equals(fac) &&
         workAreaData[i][WA_ID_COL].toString().equals(app)){
        // set so it won't get shown anymore
        workAreaData[i][FAC_COL] = "**DELETED**";
        return;
      }
    }
  }

  void loaded(){
    //if(dbDataLoaded && facCLoaded){
      loadTable();
      //this.enableButton(true);
    //}
  }

  JComboBox getComboBox(String s){
    JComboBox j = new JComboBox();
    j = ClientHelpObjs.loadChoiceFromVector(new JComboBox(),myFacLocIds);
    j.setSelectedItem(s);
    j.setEditable(false);
    j.setMaximumRowCount(9);
    j.revalidate();
    return j;
  }

  int getNumRows(String id){
    int j=0;
    for(int i=0;i<workAreaData.length;i++){
      if(workAreaData[i][FAC_COL].toString().equals(id))j++;
    }
    return j;
  }

  void facC_actionPerformed(ActionEvent e) {
    if(!facCLoaded)return;
    facC.setEnabled(false);
    WorkAreaTable_load_thread w = new WorkAreaTable_load_thread(this);
    w.start();
  }

  void addB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    addWorkArea();
  }

  void saveB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    saveChanges();
  }

  void buildTable(Object[][] data){
    jPanel2.remove(jsp);
    jPanel2.revalidate();

    CmisTableModel dtm;
    if(data == null || data.length < 1){
      dtm = new CmisTableModel(colHead);
    }else{
      dtm = new CmisTableModel(colHead,data);
    }
    dtm.setEditableFlag(BothHelpObjs.mypow(2,WA_DESC_COL-1)+BothHelpObjs.mypow(2,LOCATION_COL-1)+BothHelpObjs.mypow(2,WORKAREA_STATUS_COL-1));
    TableSorter sorter = new TableSorter(dtm);
    sorter.setColTypeFlag("119119");
    myTable = null;
    myTable = new JTable(sorter);
    myTable.setCellSelectionEnabled(true);
    myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    AdminWorkArea_BoxCellEditor boxEditor = new AdminWorkArea_BoxCellEditor(new JComboBox());
    myTable.setDefaultEditor(JComboBox.class,boxEditor);
    myTable.setDefaultRenderer(JComboBox.class,new AdminWorkArea_TableRenderer());

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    myTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum2 = myTable.getColumnModel().getColumns();
    while (enum2.hasMoreElements()) {
      ((TableColumn)enum2.nextElement()).setHeaderRenderer(tableRenderer);
    }
    //columns width
    for(int i=0;i<myTable.getColumnCount();i++){
      if (colWidth[i]==0){
         myTable.getColumn(myTable.getColumnName(i)).setWidth(colWidth[i]);
         myTable.getColumn(myTable.getColumnName(i)).setMaxWidth(colWidth[i]);
         myTable.getColumn(myTable.getColumnName(i)).setMinWidth(colWidth[i]);
      }
      myTable.getColumn(myTable.getColumnName(i)).setPreferredWidth(colWidth[i]);
    }

    sorter.addMouseListenerToHeaderInTable(myTable);
    jsp = new JScrollPane(myTable);
    jPanel2.add(jsp,BorderLayout.CENTER);
    myTable.revalidate();
    jsp.revalidate();
    jPanel2.revalidate();
    this.revalidate();
    //repaint();
  }

  void reActivateB_actionPerformed(ActionEvent e) {

  }

  void disableB_actionPerformed(ActionEvent e) {

  }
} //end of class

class AdminWorkArea_BoxCellEditor extends DefaultCellEditor implements
      ItemListener, ActionListener {

    protected JComboBox currentBox = null;

	  public AdminWorkArea_BoxCellEditor(JComboBox box) {
		  super(box);
      currentBox = box;
	  }

	  // These methods implement the TableCellEditor interface
	  public Component getTableCellEditorComponent(JTable table, Object value,
						   boolean isSelected, int row, int column) {

		  //((JComboBox)editorComponent).setSelectedItem(((JComboBox)value).getSelectedItem());
      editorComponent = (JComboBox) value;
      ((JComboBox)editorComponent).setFont(table.getFont());
      ((JComboBox)editorComponent).addItemListener(this);
      ((JComboBox)editorComponent).addActionListener(this);
      currentBox = (JComboBox) editorComponent;
      currentBox.addItemListener(this);
      currentBox.addActionListener(this);
      // rfz allow editing .... currentBox.setEditable(false);
      table.setRowSelectionInterval(row,row);
      table.repaint();

		  return editorComponent;
	  }

	  public Object getCellEditorValue() {
		  return currentBox;
	  }

	  public boolean isCellEditable(EventObject evt) {
		  if (evt instanceof MouseEvent) {
			  if (((MouseEvent)evt).getClickCount() >=
							getClickCountToStart()) {
				  return true;
			  }
		  }
		  return false;
	  }


	  public void cancelCellEditing() {
        fireEditingStopped();
	  }

    public void itemStateChanged(ItemEvent e){
        fireEditingStopped();
    }
     public void actionPerformed(ActionEvent e) {
	      fireEditingStopped();
	  }
}


class AdminWorkArea_TableRenderer extends TcmisCellRenderer {
  public Component getTableCellRendererComponent(
                          JTable table, Object value,
                          boolean isSelected, boolean hasFocus,
                          int row, int column) {


      Component comp = super.getTableCellRendererComponent(table, value,
                          isSelected,hasFocus,
                          row,column);


      try {
        if(((JComboBox)value).getSelectedItem()!=null){
          ((JLabel) comp).setText(((JComboBox)value).getSelectedItem().toString());
        } else {
          ((JLabel) comp).setText(((JComboBox)value).getItemAt(0).toString());
        }
      } catch (Exception e){
        ((JLabel) comp).setText("");
      }

    return this;
  }
}
class WorkAreaTable_load_thread extends Thread{
  AdminWorkAreaPanel awap;
  WorkAreaTable_load_thread(AdminWorkAreaPanel awap){
    super();
    this.awap = awap;
  }
  public void run(){
    awap.loadTable();
  }
}


//6-06-01
/*
class AdminWorkArea_TableCellEditor extends DefaultCellEditor implements
      ItemListener, ActionListener {

    protected JTable currentTable = null;

	  public AdminWorkArea_TableCellEditor(JTable table) {
		  super(table);
      currentTable = table;
	  }

	  // These methods implement the TableCellEditor interface
	  public Component getTableCellEditorComponent(JTable table, Object value,
						   boolean isSelected, int row, int column) {

		  //((JComboBox)editorComponent).setSelectedItem(((JComboBox)value).getSelectedItem());
      editorComponent = (JTable) value;
      ((JTable)editorComponent).setFont(table.getFont());
      ((JTable)editorComponent).addItemListener(this);
      ((JTable)editorComponent).addActionListener(this);
      currentTable = (JTable) editorComponent;
      currentTable.addItemListener(this);
      currentTable.addActionListener(this);
      // rfz allow editing .... currentBox.setEditable(false);
      table.setRowSelectionInterval(row,row);
      table.repaint();

		  return editorComponent;
	  }

	  public Object getCellEditorValue() {
		  return currentTable;
	  }

	  public boolean isCellEditable(EventObject evt) {
		  if (evt instanceof MouseEvent) {
			  if (((MouseEvent)evt).getClickCount() >=
							getClickCountToStart()) {
				  return true;
			  }
		  }
		  return false;
	  }


	  public void cancelCellEditing() {
        fireEditingStopped();
	  }

    public void itemStateChanged(ItemEvent e){
        fireEditingStopped();
    }
     public void actionPerformed(ActionEvent e) {
	      fireEditingStopped();
	  }
}
 */

