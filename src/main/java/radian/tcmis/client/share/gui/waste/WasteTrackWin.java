//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  This is the Waste Transfer Management Screen

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
public class WasteTrackWin extends JPanel {
  CmisApp cmis;

  CmisTable wasteTrackTable;
  TableSorter sorter = new TableSorter();

  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel topP = new JPanel();
  JPanel bottomP = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  StaticJLabel requesterL = new StaticJLabel();
  DataJLabel requesterT = new DataJLabel();
  JScrollPane jsp = new JScrollPane();
  FlowLayout flowLayout1 = new FlowLayout();
  JTextField dateF = new JTextField();
  StaticJLabel daysL = new StaticJLabel();
  StaticJLabel cboxL = new StaticJLabel();
  StaticJLabel facilityL = new StaticJLabel();
  //FacilityCombo facC = new FacilityCombo();
  //WorkAreaCombo waC = facC.getWorkAreaCombo();

  JComboBox facC = new JComboBox();
  JCheckBox jCheckBox1 = new JCheckBox();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel jPanel1 = new JPanel();
  JButton update = new JButton();
  JButton search = new JButton();
  JButton wasteRD = new JButton();
  JPanel panel1 = new JPanel();

  String days_since_pickup = "";
  int STATUS = 0;
  int TRANSFERRED = 0;
  int NOTES = 0;
  int REAL_NOTES = 0;
  int WASTE_REQUEST_ID = 0;
  int LINE_ITEM = 0;
  int TRANSFER_DATE = 0;
  int REQUEST_NUM_COL = 0;
  int RADIAN_PROFILE = 0;
  int CONTAINER_ID = 0;
  int TO_LOCATION_ID = 0;
  int REQUEST_DATE = 0;
  JLabel transferFromL = new JLabel();
  JComboBox transferFromC = new JComboBox();

  //the maximum number of row selected at the same time
  final int MAXSELECTEDROW = 100;
  Boolean isWasteManager = null;
  Boolean isWastePickup = null;
  DbTableModel ctm;
  boolean pickedUp = false;
  JCheckBox check = new JCheckBox();
  Boolean allowDelete = new Boolean(false);

  public WasteTrackWin() {
    try{
    jbInit();
    }catch(Exception ex) {
      ex.printStackTrace();
    }
  }


  public void setCmis(CmisApp cmis){
    this.cmis = cmis;
    loadIt();
  }

  void facC_actionPerformed(ActionEvent e) {

  }

  public void loadIt(){
    /*
    WasteTrackWinLoadThread wtlt = new WasteTrackWinLoadThread(this,"load");
    wtlt.start(); */
    loadItAction("load");
  }

  public void loadItAction(String action) {
    if (action.equalsIgnoreCase("load")) {
      fillWasteTrackTable(action);
    } else if (action.equalsIgnoreCase("search")){
      fillWasteTrackTable(action);
    } else {
      fillWasteTrackTable(action);
    }
    wasteRD.setEnabled(false);
    if ((!isWasteManager.booleanValue() && !isWastePickup.booleanValue()) || pickedUp) {
        update.setEnabled(false);
    }
  }

 public void printScreenData(){
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setRequestorName(this.requesterT.getText().toString());
    rod.setFacId(this.facC.getSelectedItem().toString());
    rod.setTransferred(jCheckBox1.isSelected());
    String myDays = BothHelpObjs.makeBlankFromNull(dateF.getText().toString());
    if (BothHelpObjs.isBlankString(myDays) || myDays == null) {
      myDays = new String("");
    }
    rod.setDaysSincePickup(myDays);
    rod.setScreen("WT");
    rod.loadIt();
  }


  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    topP.setMaximumSize(new Dimension(32767, 170));
    topP.setMinimumSize(new Dimension(10, 170));
    topP.setPreferredSize(new Dimension(450, 170));
    topP.setLayout(gridBagLayout2);
    bottomP.setLayout(borderLayout1);

    dateF.setMaximumSize(new Dimension(80, 24));
    dateF.setMinimumSize(new Dimension(80, 24));
    dateF.setPreferredSize(new Dimension(80, 24));
    dateF.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseClicked(MouseEvent e) {
        dateFieldClicked(e);
      }
    });
    dateF.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        dateF_keyPressed(e);
      }
    });


    requesterT.setMaximumSize(new Dimension(180, 24));
    requesterT.setMinimumSize(new Dimension(180, 24));
    requesterT.setPreferredSize(new Dimension(180, 24));

    facC.setMaximumSize(new Dimension(180, 24));
    facC.setMinimumSize(new Dimension(180, 24));
    facC.setPreferredSize(new Dimension(180, 24));
    //facC.setSelectedFacId("");
    /*waC.setMaximumSize(new Dimension(180, 24));
    waC.setMinimumSize(new Dimension(180, 24));
    waC.setPreferredSize(new Dimension(180, 24));
      */
    wasteTrackTable = new CmisTable();
    jsp.getViewport().setView(wasteTrackTable);
    //wasteTrackTable.addMouseListener(new WasteTrackWin_wasteTrackTable_mouseAdapter(this));

    //CmisTableModel ctm = new CmisTableModel(cartCols);
    daysL.setText("Days");
    cboxL.setText("  Picked up within");
    facilityL.setText("Facility:");
    requesterL.setText("User:");
    update.setText("Update Now");
    update.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Update"));
    update.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        update_actionPerformed(e);
      }
    });

    search.setText("Search");
    search.setIcon(ResourceLoader.getImageIcon("images/button/search.gif","Search"));
    search.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        search_actionPerformed(e);
      }
    });

    wasteRD.setText("Waste Request Detail");
    wasteRD.setIcon(ResourceLoader.getImageIcon("images/button/trash_can_tab.gif","WasteRD"));
    wasteRD.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        wasteRD_actionPerformed(e);
      }
    });
    wasteRD.setEnabled(false);
    update.setEnabled(false);

    /*transferFromL.setText("Transfer From:");
    transferFromC.setMaximumSize(new Dimension(150, 24));
    transferFromC.setMinimumSize(new Dimension(150, 24));
    transferFromC.setPreferredSize(new Dimension(150, 24));
    */
    this.add(bottomP, new GridBagConstraints2(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(7, 5, 5, 5), 0, 265));
    bottomP.add(jsp, BorderLayout.CENTER);

    this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 1, 0), 0, -95));
    topP.add(requesterL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
    topP.add(requesterT, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 100, 5, 0), 0, 0));
    topP.add(facilityL, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 0, 10, 0), 0, 0));
    topP.add(facC, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 50, 10, 0), 0, 0));
    /*topP.add(workAreaL, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 200, 10, 10), 0, 0));
    topP.add(waC, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 250, 10, 0), 0, 0));
    */
    topP.add(cboxL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 10, 0), 0, 0));
    topP.add(daysL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 185, 10, 5), 0, 0));
    topP.add(dateF, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 100, 10, 0), 0, 0));
    topP.add(jCheckBox1, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 10, 0), 0, 0));
    topP.add(update, new GridBagConstraints2(2, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 105, 10, 0), 0, 0));
    topP.add(search, new GridBagConstraints2(2, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 10, 0), 0, 0));
    topP.add(wasteRD, new GridBagConstraints2(2, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 235, 10, 0), 0, 0));
    topP.add(transferFromL, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 235, 0, 5), 0, 0));
    /*topP.add(transferFromC, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 330, 0, 0), 0, 0));
   */
  }

   public void dateFieldClicked(MouseEvent e) {
  //  dateF.setText(null);
    jCheckBox1.setSelected(true);
  }

  void fillWasteTrackTable(String action){

    Vector updateVector = new Vector();
    String facility = cmis.getPrefFac();
    pickedUp = jCheckBox1.isSelected();

    if (action.equalsIgnoreCase("update")){
      updateVector = getCheckItem();
      if (updateVector.size() < 1) {
        GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
        g.setMsg("Please mark at least one container as transferred.");
        g.setVisible(true);
        return;
      }
      facility = facC.getSelectedItem().toString();
    } else if (action.equalsIgnoreCase("search")){
      facility = facC.getSelectedItem().toString();
    }

    if(!jCheckBox1.isSelected()) {
      days_since_pickup = "";
    }

    long sTime = new java.util.Date().getTime();
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteTrack",cmis);
      Hashtable query = new Hashtable();

      query.put("ACTION",action); //String, String
      query.put("DAYS_SINCE_PICKUP",days_since_pickup);
      query.put("FACILITY_ID",facility);
      query.put("USER_ID",cmis.getUserId());
      query.put("UPDATE_VECTOR",updateVector);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      requesterT.setText((String)result.get("USER_NAME"));
      requesterT.setEnabled(true);
      isWasteManager = (Boolean)result.get("IS_WASTE_MANAGER");
      isWastePickup = (Boolean)result.get("IS_WASTE_PICKUP");
      allowDelete = (Boolean)result.get("ALLOW_DELETE");        //3-5-03
      Vector prefFacV = (Vector)result.get("PREFER_FAC");
      if (facC.getItemCount() <= 0) {
        facC = ClientHelpObjs.loadChoiceFromVector(facC,prefFacV);
      }
      facC.setSelectedItem(cmis.getPrefFac());

      jsp.getViewport().remove(wasteTrackTable);
      buildTableModel((Hashtable) result.get("SEARCH_DATA"));
      sorter = new TableSorter(ctm);
      wasteTrackTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(wasteTrackTable);
      wasteTrackTable.setCellSelectionEnabled(false);
      wasteTrackTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      wasteTrackTable.getTableHeader().setReorderingAllowed(true);
      wasteTrackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

      //putting the checkbox in the table
      WasteTrackColorCellRenderer colorTableRenderer = new WasteTrackColorCellRenderer();
      WasteTrackCheckBoxCellRenderer checkTableRenderer = new WasteTrackCheckBoxCellRenderer();
      wasteTrackTable.setDefaultRenderer(String.class, colorTableRenderer);
      wasteTrackTable.setDefaultRenderer(Integer.class, colorTableRenderer);
      wasteTrackTable.setDefaultRenderer(Double.class, colorTableRenderer);
      wasteTrackTable.setDefaultRenderer(Boolean.class, checkTableRenderer);

      //JCheckBox check = new JCheckBox();
      check.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          check_actionPerformed(e);
        }
      });
      check.setHorizontalAlignment(JLabel.CENTER);
      wasteTrackTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));


      wasteTrackTable.addMouseListener(new WasteTrackWin_wasteTrackTable_mouseAdapter(this));

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
    wasteTrackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = wasteTrackTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    ClientHelpObjs.makeDefaultColors(this);

      // set column widths
      for(int i=0;i<wasteTrackTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        wasteTrackTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        wasteTrackTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          wasteTrackTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          wasteTrackTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }
      jsp.getViewport().setView(wasteTrackTable);
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));

      if (action.equalsIgnoreCase("update")) {
        GenericDlg g = new GenericDlg(cmis.getMain(),"Update Succeeded",true);
        g.setMsg("Your request was successfully updated.");
        g.setVisible(true);
      }

      if (wasteTrackTable.getModel().getRowCount()<=0 && !action.equalsIgnoreCase("load")){
        GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
        g.setMsg("There are no more waste to transfer.");
        g.setVisible(true);
        update.setEnabled(false);
      }

      //column
      Hashtable keyCols = (Hashtable)result.get("COLUMN_KEY");

      STATUS = Integer.parseInt((String) keyCols.get("Status"));
      TRANSFERRED = Integer.parseInt((String) keyCols.get("Transferred"));
      NOTES = Integer.parseInt((String) keyCols.get("Note"));
      REAL_NOTES = Integer.parseInt((String) keyCols.get("Real Notes"));
      WASTE_REQUEST_ID = Integer.parseInt((String) keyCols.get("Waste Request Id"));  //Brian
      LINE_ITEM = Integer.parseInt((String) keyCols.get("Line Item"));
      TRANSFER_DATE = Integer.parseInt((String) keyCols.get("Transfer Date"));
      REQUEST_NUM_COL = Integer.parseInt((String) keyCols.get("Waste Request Id"));  //Brian Same Data
      RADIAN_PROFILE = Integer.parseInt((String) keyCols.get("Waste Item Id"));
      CONTAINER_ID = Integer.parseInt((String) keyCols.get("Container Id"));
      TO_LOCATION_ID = Integer.parseInt((String) keyCols.get("To"));
      REQUEST_DATE =  Integer.parseInt((String) keyCols.get("Request Date"));

      // only the waste manager/pickup to check the 'picked up' checkbox.
      if ((isWasteManager.booleanValue() || isWastePickup.booleanValue()) && !pickedUp) {
        int pos = mypow(2,TRANSFERRED);
        ctm.setEditableFlag(pos);
      }

      long eTime = new java.util.Date().getTime();
      long min = (eTime-sTime);
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(new Date(min));
      String tmsg = "Records Found: " + wasteTrackTable.getRowCount() + "     Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
      cmis.getMain().countLabel.put(new Integer(Main.WASTE_TRACKING),tmsg);
      cmis.getMain().setStatusBarText((String)cmis.getMain().countLabel.get(new Integer(Main.WASTE_TRACKING)));

    }catch(Exception e){
      e.printStackTrace();
    }finally{
      ClientHelpObjs.setEnabledContainer(this,true);
      ClientHelpObjs.makeDefaultColors(this);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      this.revalidate();
    }
  }

  void buildTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    ctm = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      ctm.addRow((Object[]) data.elementAt(i));
    }
    ctm.setColumnPrefWidth(colWidths);
    ctm.setColumnType(colTypes);
  }  //end of method


  public int mypow(int y, int x) {
   int value = 1;
   for (int i = 0; i < x; i++) {
    value *= y;
   }
   return value;
  }

  private void doRightClick(MouseEvent e){
     //Table clicked
    JFrame F;
    int catSel = wasteTrackTable.getSelectedRow();
    WasteMetaPopUp mpu = new WasteMetaPopUp(cmis,
          Integer.parseInt(wasteTrackTable.getModel().getValueAt(catSel,this.RADIAN_PROFILE).toString())); // Waste Profile Id

    int[] row = new int[MAXSELECTEDROW];
    row   = wasteTrackTable.getSelectedRows();
    Vector containerV = new Vector();
    boolean allowPrintLabel = true;
    for (int i = 0; i < row.length; i++) {
      containerV.addElement(wasteTrackTable.getModel().getValueAt(row[i],this.CONTAINER_ID).toString());
      if (!"Submitted".equalsIgnoreCase(wasteTrackTable.getModel().getValueAt(row[i],STATUS).toString())) {
        allowPrintLabel = false;
      }
    }
    mpu.setContainerId(containerV);
    panel1.add(mpu);
    if (allowPrintLabel) {
      mpu.enableMenu("PrintLabel");
    }
    //3-05-03
    //display delete container if the following is true
    if (allowDelete.booleanValue()) {
      mpu.setWasteTrackWin(this);
      mpu.enableMenu("DeleteContainer");
    }

    mpu.show(wasteTrackTable,e.getX(),e.getY());
    return;
  }

  void wasteTrackTable_mouseClicked(MouseEvent e){
    if(e.isMetaDown()){
      doRightClick(e);
    }
    int row   = wasteTrackTable.getSelectedRow();
    int col = wasteTrackTable.getSelectedColumn();

    //allow the user to change the pickup date
    if(col == TRANSFER_DATE && e.getClickCount() > 1){
      EditDlg RjW = new EditDlg(cmis.getMain(),"Change Pickup Date",true, "Enter Another Pickup Date:(MM/DD/YY)");
      String tmp = wasteTrackTable.getModel().getValueAt(row,TRANSFER_DATE).toString();
      if(!BothHelpObjs.isBlankString(tmp)) {
        RjW.setText(tmp);
      }
      RjW.setVisible(true);
      if(!BothHelpObjs.isBlankString(RjW.getText())){
          wasteTrackTable.getModel().setValueAt(RjW.getText(),row,col);
      }else{
          wasteTrackTable.getModel().setValueAt(tmp,row,col);
      }
      RjW.dispose();
    }

    //show the user the requester's notes
    if(col == NOTES && wasteTrackTable.getModel().getValueAt(row,NOTES).toString().trim().equalsIgnoreCase("+")){
      GenericDlg dlg = new GenericDlg(cmis.getMain(),"Notes");
      dlg.setMsg(wasteTrackTable.getModel().getValueAt(row,REAL_NOTES).toString());
      dlg.setVisible(true);
    }
    wasteRD.setEnabled(true);
  }

  void search_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (!preUpdateAudit()){
      return;
    }
    Vector updateVector = new Vector();
    String facility = cmis.getPrefFac();

    pickedUp = jCheckBox1.isSelected();

    facility = facC.getSelectedItem().toString();

    if(!jCheckBox1.isSelected()) {
      days_since_pickup = "";
    }
    long sTime = new java.util.Date().getTime();
    try{
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteTrack",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","search"); //String, String
      query.put("DAYS_SINCE_PICKUP",days_since_pickup);
      query.put("FACILITY_ID",facility);
      query.put("USER_ID",cmis.getUserId());

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      isWasteManager = (Boolean)result.get("IS_WASTE_MANAGER");
      isWastePickup = (Boolean)result.get("IS_WASTE_PICKUP");
      jsp.getViewport().remove(wasteTrackTable);
      buildTableModel((Hashtable) result.get("SEARCH_DATA"));
      sorter = new TableSorter(ctm);
      wasteTrackTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(wasteTrackTable);
      wasteTrackTable.setCellSelectionEnabled(false);
      wasteTrackTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      wasteTrackTable.getTableHeader().setReorderingAllowed(true);
      wasteTrackTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

      //putting the checkbox in the table
      WasteTrackColorCellRenderer colorTableRenderer = new WasteTrackColorCellRenderer();
      WasteTrackCheckBoxCellRenderer checkTableRenderer = new WasteTrackCheckBoxCellRenderer();
      wasteTrackTable.setDefaultRenderer(String.class, colorTableRenderer);
      wasteTrackTable.setDefaultRenderer(Integer.class, colorTableRenderer);
      wasteTrackTable.setDefaultRenderer(Double.class, colorTableRenderer);
      wasteTrackTable.setDefaultRenderer(Boolean.class, checkTableRenderer);

      //JCheckBox check = new JCheckBox();
      check.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          check_actionPerformed(e);
        }
      });
      check.setHorizontalAlignment(JLabel.CENTER);
      wasteTrackTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));


      wasteTrackTable.addMouseListener(new WasteTrackWin_wasteTrackTable_mouseAdapter(this));

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
    /*TableColumnModel cm = wasteTrackTable.getColumnModel();
    for (int i = 0; i < wasteTrackTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }*/
    //font and foreground and background color for columns heading
    String fontName = (String)chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
    wasteTrackTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = wasteTrackTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    ClientHelpObjs.makeDefaultColors(this);

      // set column widths
      for(int i=0;i<wasteTrackTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        wasteTrackTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        wasteTrackTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          wasteTrackTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          wasteTrackTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }
      jsp.getViewport().setView(wasteTrackTable);
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));

      if (wasteTrackTable.getModel().getRowCount()<=0){
        GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
        g.setMsg("There are no more waste to transfer.");
        g.setVisible(true);
        update.setEnabled(false);
      }


      // only the waste manager/pickup to check the 'picked up' checkbox.
      if ((isWasteManager.booleanValue() || isWastePickup.booleanValue()) && !pickedUp) {
        int pos = mypow(2,TRANSFERRED);
        ctm.setEditableFlag(pos);
      }

      long eTime = new java.util.Date().getTime();
      long min = (eTime-sTime);
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(new Date(min));
      String tmsg = "Records Found: " + wasteTrackTable.getRowCount() + "     Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
      cmis.getMain().countLabel.put(new Integer(Main.WASTE_TRACKING),tmsg);
      cmis.getMain().setStatusBarText((String)cmis.getMain().countLabel.get(new Integer(Main.WASTE_TRACKING)));

      wasteRD.setEnabled(false);
      update.setEnabled(false);

    }catch(Exception eee){
      eee.printStackTrace();
    }finally{
      ClientHelpObjs.setEnabledContainer(this,true);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      this.revalidate();
    }
  }

  void wasteRD_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    int i = wasteTrackTable.getSelectedRow();
    if (i > -1) {
      cmis.getMain().goWasteRequest(0,(new Integer(wasteTrackTable.getModel().getValueAt(i,REQUEST_NUM_COL).toString())).intValue());
    }
  }

  void dateF_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      if (!preUpdateAudit()){
        return;
      }
      WasteTrackWinLoadThread wtlt = new WasteTrackWinLoadThread(this,"search");
      wtlt.start();
    }
  }

  void check_actionPerformed(ActionEvent e) {
   if (BothHelpObjs.isBlankString(wasteTrackTable.getModel().getValueAt(wasteTrackTable.getSelectedRow(),REQUEST_DATE).toString())) {
      wasteTrackTable.getModel().setValueAt(new Boolean(false),wasteTrackTable.getSelectedRow(),TRANSFERRED);
    }
  }

  void update_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (!preUpdateAudit()){
      return;
    }
    loadItAction("update");
  }

  public Vector getCheckItem() {
    Vector updateVector = new Vector();
    for(int i = 0; i < wasteTrackTable.getRowCount(); i++) {
      if (wasteTrackTable.getModel().getValueAt(i,TRANSFERRED).toString().equalsIgnoreCase("true")){
        Hashtable h = new Hashtable();
        h.put("WASTE_REQUEST_ID",(String)wasteTrackTable.getModel().getValueAt(i,WASTE_REQUEST_ID));
        h.put("LINE_ITEM",(String)wasteTrackTable.getModel().getValueAt(i,LINE_ITEM));
        h.put("TRANSFER_DATE",(String)wasteTrackTable.getModel().getValueAt(i,TRANSFER_DATE));
        h.put("CONTAINER_ID",(String)wasteTrackTable.getModel().getValueAt(i,CONTAINER_ID));
        h.put("TO_LOCATION_ID",(String)wasteTrackTable.getModel().getValueAt(i,TO_LOCATION_ID));
        updateVector.addElement(h);
      }
    }
    return updateVector;
  }

  public boolean preUpdateAudit() {
    String msg = "";
    int days = 0;
    boolean check = jCheckBox1.isSelected();
    if (check) {
      days_since_pickup = (String)dateF.getText();
      if (check && days_since_pickup == null) {
        msg = "You must enter a days since pickup.";
      }

      try {
        days = Integer.parseInt(days_since_pickup);
      }catch(Exception e){
        msg = "Enter numeric number for the days.";
      }
      if (days < 0) {
        msg = "Enter only positive numeric number for the days.";
      }
    }

    if(msg.length() > 0){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg(msg);
      gd.setVisible(true);
      return false;
    }
    return true;
  }
}

class WasteTrackWinLoadThread extends Thread{
  WasteTrackWin wp;
  String action;
  public WasteTrackWinLoadThread(WasteTrackWin wp,String action){
    super();
    this.wp = wp;
    this.action = action;
  }
  public void run(){
    if(action.equalsIgnoreCase("load")){
      wp.loadItAction("load");
    }else if(action.equalsIgnoreCase("update")){
      wp.loadItAction("update");
    }else {
      wp.loadItAction("search");
    }
  }
}

class WasteTrackWin_wasteTrackTable_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteTrackWin adaptee;
  WasteTrackWin_wasteTrackTable_mouseAdapter(WasteTrackWin adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.wasteTrackTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}


