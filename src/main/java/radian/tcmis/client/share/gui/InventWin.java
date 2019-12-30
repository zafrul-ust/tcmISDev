
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Descripti           on:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.GridBagConstraints2;

import java.util.*;
import java.text.SimpleDateFormat;
//import com.borland.jbcl.view.*;
//import radian.tcmis.client.share.reports.*;
import java.beans.*;

import javax.swing.*;
import javax.swing.table.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class InventWin extends JPanel{
  //Vector facs = new Vector();
  static InventFloat IF;

  CmisApp parent;
  String userFac = new String();
  //Hashtable facXref = new Hashtable();
  //Hashtable wareXref = new Hashtable();
  //Hashtable facXware = new Hashtable();
  String preferredWare;
  int invSel = 0;
  long lastClick = 0;
  JPanel holder = new JPanel();
  JScrollPane JSP = new JScrollPane();
  //CmisTable invTable = new CmisTable();
  MultiSpanCellTable invTable;
  DbTableModel ctm = new DbTableModel();
  TableSorter sorterInv = new TableSorter();
  String[] colHeads = null;
  int[] colWidths = null;
  String colTypes = null;

  static int ITEM_ID =0;
  static int HUB =0;
  static int PACKAGING =0;
  static int FAC_ID =0;
  static int MAT_ID =0;
  static int SPEC_ID =0;
  static int SPEC_ONLINE =0;
  static int MSDS_ONLINE =0;
  static int PART_NUM =0;

  //table column index
  int FACILITY_COL = 0;
  int WORK_AREA_COL = 0;
  int GROUP_COL = 0;
  int PART_COL = 0;
  int DESC_COL = 0;
  int TYPE_COL = 0;
  int SHELF_COL = 0;
  int BASE_COL = 0;
  int ITEM_PART_COL = 0;
  int ITEM_COL = 0;
  int COMPONENT_COL = 0;
  int PACKAGING_COL = 0;
  int MANUFACTURER_COL = 0;
  int PART_NUMBER_COL = 0;
  int STATUS_COL = 0;
  int CATALOG_COL = 0;
  int CATALOG_COMPANY_ID_COL = 0;
  int MATERIAL_COL = 0;
  int MSDS_ONLINE_COL = 0;
  int PART_GROUP_COL = 0;
  int ITEM_GROUP_COL = 0;
  int COMMENT_COL = 0;
  int COMP_DESC_COL = 0;
  int PART_COLOR_COL = 0;
  int BIN_COL = 0;
  int LOT_COL = 0;
  int INVENTORY_GROUP_COL = 0;
  String searchFacility = "";
  String searchWorkArea = "";
  final Long colTypeFlagNew = new Long("1111111111111111");


  //FacilityCombo facChoice = new FacilityCombo();
  //FacilityCombo wareChoice = new FacilityCombo();
  //boolean facComboLoaded = false;
  //boolean wareComboLoaded = false;
  //boolean prefWareLoaded = false;
  //Hashtable prefWareHash;

  BorderLayout borderLayout1 = new BorderLayout();
  JTextField wareText = new JTextField();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  StaticJLabel facLabel = new StaticJLabel();
  StaticJLabel wareLabel = new StaticJLabel();
  JTextField itemText = new JTextField();
  StaticJLabel itemLabel = new StaticJLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel jPanel3 = new JPanel();
  JCheckBox onHandCheck = new JCheckBox();
  DataJLabel eDate = new DataJLabel();
  DataJLabel aDate = new DataJLabel();
  JButton S = new JButton();
  JTextField byDate = new JTextField();
  JTextField expDate = new JTextField();
  StaticJLabel expLabel1 = new StaticJLabel();
  StaticJLabel expLabel2 = new StaticJLabel();
  JCheckBox onOrderCheck = new JCheckBox();
  StaticJLabel byLabel1 = new StaticJLabel();
  StaticJLabel byLabel2 = new StaticJLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  ImageIcon ss = new ImageIcon();
  String searchFacId = "";
  String searchedWareHouse = "";

  // report fields
  String rptSearchText;
  String rptFac;
  String rptWarehouse;
  String rptExpDays;
  String rptArrDays;
  boolean rptExp;
  boolean rptArr;
  StaticJLabel jLabel1 = new StaticJLabel();
  JTextField exp2Date = new JTextField();
  StaticJLabel jLabel2 = new StaticJLabel();
  DataJLabel e2Date = new DataJLabel();
  JCheckBox allPartsC = new JCheckBox();
  JPanel jPanel4 = new JPanel();
  JButton detailB = new JButton();
  JRadioButton itemRB = new JRadioButton();
  JRadioButton binRB = new JRadioButton();
  JRadioButton lotRB = new JRadioButton();

  JComboBox facC = new JComboBox();
  JComboBox warehouseC = new JComboBox();
  Hashtable dataH;

  public InventWin(CmisApp cmis) {
    this.parent = cmis;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/search.gif");
    detailB.setEnabled(false);

    this.setLayout(gridBagLayout4);

    facLabel.setText("Facility:");
    wareLabel.setText("Inventory:");
    itemLabel.setText("Search Text:");
    onHandCheck.setSelected(true);
    S.addActionListener(new InventWin_S_actionAdapter(this));
    S.addKeyListener(new InventWin_S_keyAdapter(this));
    ss = ResourceLoader.getImageIcon("images/button/submit.gif","Search");
    S.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/search.gif","Item Detail");
    detailB.setIcon(ss);

    onHandCheck.addKeyListener(new InventWin_key_press_adapter(this));
    //wareChoice.addKeyListener(new InventWin_key_press_adapter(this));
    //facChoice.addKeyListener(new InventWin_key_press_adapter(this));
    //wareChoice.addItemListener(new InventWin_wareChoice_itemAdapter(this));
    //facChoice.addActionListener(new InventWin_FacChoice_ActionListener(this));
    warehouseC.addKeyListener(new InventWin_key_press_adapter(this));
    facC.addKeyListener(new InventWin_key_press_adapter(this));
    warehouseC.addItemListener(new InventWin_wareChoice_itemAdapter(this));
    facC.addActionListener(new InventWin_FacChoice_ActionListener(this));


    itemText.addKeyListener(new InventWin_key_press_adapter(this));
    onHandCheck.addItemListener(new InventWin_onHandCheck_itemAdapter(this));
    S.setRolloverEnabled(true);
    S.setToolTipText("Click here to start search");
    byDate.addKeyListener(new InventWin_key_press_adapter(this));
    expDate.addKeyListener(new InventWin_key_press_adapter(this));

    expLabel1.setText("Expires within");
    expLabel2.setText("days");
    onOrderCheck.setText("On order");
    onOrderCheck.addItemListener(new InventWin_onOrderCheck_itemAdapter(this));
    onOrderCheck.setSelected(true);
    onOrderCheck.addKeyListener(new InventWin_key_press_adapter(this));
    byLabel1.setText("Arrives within");
    byLabel2.setText("days");
    S.setText("Search");
    onHandCheck.addItemListener(new InventWin_onHandCheck_itemAdapter(this));
    onHandCheck.setText("On hand");
    jPanel3.setLayout(gridBagLayout2);
    jPanel2.setLayout(gridBagLayout1);
    jPanel1.setLayout(gridBagLayout3);
    holder.setPreferredSize(new Dimension(730, 253));
    jPanel1.setMaximumSize(new Dimension(2147483647, 126));
    itemText.setMaximumSize(new Dimension(160, 21));
    itemText.setMinimumSize(new Dimension(160, 21));
    itemText.setPreferredSize(new Dimension(160, 21));
    facC.setMaximumSize(new Dimension(160, 21));
    facC.setMinimumSize(new Dimension(160, 21));
    facC.setPreferredSize(new Dimension(160, 21));
    warehouseC.setMaximumSize(new Dimension(160, 21));
    warehouseC.setMinimumSize(new Dimension(160, 21));
    warehouseC.setPreferredSize(new Dimension(160, 21));
    aDate.setMaximumSize(new Dimension(100, 21));
    aDate.setMinimumSize(new Dimension(100, 21));
    aDate.setPreferredSize(new Dimension(100, 21));
    eDate.setMaximumSize(new Dimension(100, 21));
    eDate.setMinimumSize(new Dimension(100, 21));
    eDate.setPreferredSize(new Dimension(100, 21));
    expDate.setMaximumSize(new Dimension(60, 21));
    expDate.setMinimumSize(new Dimension(60, 21));
    expDate.setPreferredSize(new Dimension(60, 21));

    byDate.setMaximumSize(new Dimension(60, 21));
    byDate.setMinimumSize(new Dimension(60, 21));
    byDate.setPreferredSize(new Dimension(60, 21));

    jLabel1.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Expires after");
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel2.setText("days");
    exp2Date.setMaximumSize(new Dimension(60, 21));
    exp2Date.setMinimumSize(new Dimension(60, 21));
    exp2Date.setPreferredSize(new Dimension(60, 21));

    e2Date.setPreferredSize(new Dimension(100, 21));
    e2Date.setMinimumSize(new Dimension(100, 21));
    e2Date.setMaximumSize(new Dimension(100, 21));
    allPartsC.setText("Show all Part Numbers");
    jPanel4.setMaximumSize(new Dimension(1000, 35));
    jPanel4.setMinimumSize(new Dimension(45, 35));
    jPanel4.setPreferredSize(new Dimension(400, 35));
    detailB.setText("Item Detail");
    detailB.addActionListener(new InventWin_detailB_Item_Listener(this));
    itemRB.setText(" By Item");
    itemRB.setFont(new java.awt.Font("Dialog", 0, 10));
    itemRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        itemRB_actionPerformed(e);
      }
    });
    binRB.setText(" By Item, Bin");
    binRB.setFont(new java.awt.Font("Dialog", 0, 10));
    binRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        binRB_actionPerformed(e);
      }
    });
    lotRB.setText(" By Item, Bin, Lot");
    lotRB.setFont(new java.awt.Font("Dialog", 0, 10));
    lotRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        lotRB_actionPerformed(e);
      }
    });
    this.add(jPanel1, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 5), 0, 5));
    holder.setLayout(borderLayout1);
    holder.add(JSP, BorderLayout.CENTER);
    this.add(jPanel4, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel4.add(S, null);
    jPanel4.add(detailB, null);
    this.add(holder, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(15, 0, 0, 0), 0, 0));
    jPanel1.add(jPanel2, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    jPanel2.add(itemText, new GridBagConstraints2(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
    jPanel2.add(facC, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 0), 0, 0));
    jPanel2.add(warehouseC, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 0, 0), 0, 0));
    jPanel3.add(onHandCheck, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 15, 0, 0), 0, 0));
    jPanel3.add(expDate, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    jPanel3.add(onOrderCheck, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 15, 0, 0), 0, 0));
    jPanel3.add(byDate, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 3, 0, 3), 0, 0));


    jPanel3.add(aDate, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0));
    jPanel3.add(eDate, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    jPanel2.add(facLabel, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 3), 0, 0));
    jPanel2.add(wareLabel, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 3), 0, 0));
    jPanel2.add(itemLabel, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 30, 0, 3), 0, 0));
    jPanel1.add(jPanel3, new GridBagConstraints2(1, 0, 1, 2, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, -4, 3), 0, 0));
    jPanel3.add(expLabel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(expLabel2, new GridBagConstraints(3, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(byLabel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel3.add(byLabel2, new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel3.add(jLabel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(exp2Date, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    jPanel3.add(jLabel2, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(e2Date, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(allPartsC, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));

    setInvTableWidths(true);

    holder.setVisible(true);
    holder.validate();

    //Don't know how to deal with this option so I won't show it to the user 9-30
    allPartsC.setVisible(false);
    detailB.setVisible(false);

    //Colors
    ClientHelpObjs.makeDefaultColors(this);
  }

  public void setParent(Object parent){
    this.parent = (CmisApp) parent;
  }

  protected void setTable(){
    invTable.addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
        invTable_mouseClicked(e);
      }
    });
    invTable.setToolTipText(null);
    invTable.getTableHeader().setReorderingAllowed(true);
    invTable.getTableHeader().setResizingAllowed(true);
    invTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    invTable.setCellSelectionEnabled(false);
    invTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    invTable.getColumnModel();
    invTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    invTable.setDefaultRenderer(JTextArea.class, new TextCellRender());

  }

  void dataLoaded(){
    ClientHelpObjs.setEnabledContainer(this,true);
    holder.setVisible(true);
    jPanel1.setVisible(true);
    jPanel4.setVisible(true);
    this.setVisible(true);
    this.validate();
    this.repaint();

    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    ClientHelpObjs.setComboBoxUpdateUi(this);
    S.setEnabled(true);
    detailB.setEnabled(false);
    this.revalidate();
  }

  public void loadItAction(){
     holder.setVisible(false);
     jPanel1.setVisible(false);
     jPanel4.setVisible(false);
     loadPrefWare();
     itemRB.setSelected(true);
     binRB.setSelected(false);
     lotRB.setSelected(false);
  }

  public void loadIt(){
    InventWinLoadThread iT = new InventWinLoadThread(this);
    iT.start();
  }

  public void stateChanged(String label){
    if (parent == null) return;
    parent.getMain().countLabel.put(new Integer(Main.INVENTORY),"");
    parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.INVENTORY)));
    if(S == null || (label.equalsIgnoreCase("refresh") &&
      S.getText().equalsIgnoreCase("search"))) {
      return;
    }
    S.setText(label);
  }

  void loadPrefWare() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("InventoryObj",parent);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_INITIAL_DATA"); //String, String
      query.put("USERID",(new Integer(parent.getUserId().intValue())).toString()); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        return;
      }
      dataH = (Hashtable) result.get("DATA");
      Vector facilityNameV = (Vector) dataH.get("FACILITY_NAME");
      Vector warehouseV = (Vector) dataH.get("WAREHOUSE");
      facC = ClientHelpObjs.loadChoiceFromVector(facC,facilityNameV);
      warehouseC = ClientHelpObjs.loadChoiceFromVector(warehouseC,warehouseV);
      setWarehouse("Refresh");
    } catch (Exception e) {
      e.printStackTrace();
    }
    dataLoaded();
  }

  void fillTable(){
    InventThread iT = new InventThread(this);
    iT.start();
  }

  protected AttributiveCellTableModel buildSearchTableNew(Hashtable h) {
    Vector tableData = (Vector)h.get("TABLE_DATA");
    String[] colHeads = (String[])h.get("COL_HEADS");
    int[] colTypes = (int[])h.get("COL_TYPES");
    int[] colWidths = (int[])h.get("COL_WIDTHS");
    Color pColor = (Color)h.get("PART_COLOR");
    Color itemCompColor = (Color)h.get("ITEM_COMP_COLOR");
    int rowNum = tableData.size();
    AttributiveCellTableModel model = new AttributiveCellTableModel(colHeads,rowNum);
    for (int k = rowNum -1; k >= 0; k--){
      model.removeRow(k);
    }
    //if there are some records from the search constaint
    if (tableData.size() > 0) {
      CellSpan cellAtt = (CellSpan)model.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)model.getCellAttribute();
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
        model.addRow(oa);
        //preparing logic for grouping
        if(!partGroup.equals(lastPartGroup)) {
            int[] rows = new int[partRows.size()];
            for (int i = 0; i < partRows.size(); i++) {
              Integer r = (Integer)partRows.elementAt(i);
              rows[i] = r.intValue();
            }
            if (partColor) {
              model.setValueAt("y",rows[0],PART_COLOR_COL);
              for (int i = 0; i < ITEM_PART_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(pColor,rows,cols);
              }
              if (partRows.size() > 1) {
                model.setValueAt("y",rows[0],GROUP_COL);
              }else {
                model.setValueAt("n",rows[0],GROUP_COL);
              }
              partRows.removeAllElements();
              partColor = false;
            }else {
              model.setValueAt("n",rows[0],PART_COLOR_COL);
              for (int i = 0; i < ITEM_PART_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              if (partRows.size() > 1) {
                model.setValueAt("y",rows[0],GROUP_COL);
              }else {
                model.setValueAt("n",rows[0],GROUP_COL);
              }
              partRows.removeAllElements();
              partColor = true;
            }
            partRows.addElement(new Integer(row));
        }else {
          //System.out.println("\n\n how many times this get call: "+row);
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
              //for (int i = ITEM_PART_COL; i < COMP_DESC_COL; i++) {
              //1-31-02
              int binPosition = COMP_DESC_COL;
              if (binRB.isSelected() || lotRB.isSelected()) {
                binPosition = BIN_COL;
              }
              for (int i = ITEM_PART_COL; i < binPosition; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(itemCompColor,rows,cols);
              }
              //color for component
              //for (int i = COMP_DESC_COL; i < colHeads.length; i++) {
              for (int i = binPosition; i < colHeads.length; i++) {
                cols = new int[]{i};
                cellColorAtt.setBackground(itemCompColor,rows,cols);
              }
              //set y if item is a kit
              if (itemRows.size() > 1) {
                model.setValueAt("y",rows[0],ITEM_GROUP_COL);
              }else {
                model.setValueAt("n",rows[0],ITEM_GROUP_COL);
              }
              itemRows.removeAllElements();
              itemColor = false;
            }else {
              //color for item
              //or (int i = ITEM_PART_COL; i < COMP_DESC_COL; i++) {
              //1-31-02
              int binPosition = COMP_DESC_COL;
              if (binRB.isSelected() || lotRB.isSelected()) {
                binPosition = BIN_COL;
              }
              for (int i = ITEM_PART_COL; i < binPosition; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              //color for component
              //for (int i = COMP_DESC_COL; i < colHeads.length; i++) {
              for (int i = binPosition; i < colHeads.length; i++) {
                cols = new int[]{i};
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              //set y if item is a kit
              if (itemRows.size() > 1) {
                model.setValueAt("y",rows[0],ITEM_GROUP_COL);
              }else {
                model.setValueAt("n",rows[0],ITEM_GROUP_COL);
              }
              itemRows.removeAllElements();
              itemColor = true;
            }
            itemRows.addElement(new Integer(row));
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
              model.setValueAt("y",rows[0],PART_COLOR_COL);
              for (int i = 0; i < ITEM_PART_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(pColor,rows,cols);
              }
              if (partRows.size() > 1) {
                model.setValueAt("y",rows[0],GROUP_COL);
              }else {
                model.setValueAt("n",rows[0],GROUP_COL);
              }
              partRows.removeAllElements();
              partColor = false;
            }else {
              model.setValueAt("n",rows[0],PART_COLOR_COL);
              for (int i = 0; i < ITEM_PART_COL; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              if (partRows.size() > 1) {
                model.setValueAt("y",rows[0],GROUP_COL);
              }else {
                model.setValueAt("n",rows[0],GROUP_COL);
              }
              partRows.removeAllElements();
              partColor = true;
            }
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
              //for (int i = ITEM_PART_COL; i < COMP_DESC_COL; i++) {
              //1-31-02
              int binPosition = COMP_DESC_COL;
              if (binRB.isSelected() || lotRB.isSelected()) {
                binPosition = BIN_COL;
              }
              for (int i = ITEM_PART_COL; i < binPosition; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(itemCompColor,rows,cols);
              }
              //color for component
              for (int i = binPosition; i < colHeads.length; i++) {
                cols = new int[]{i};
                cellColorAtt.setBackground(itemCompColor,rows,cols);
              }
              //set y if item is a kit
              if (itemRows.size() > 1) {
                model.setValueAt("y",rows[0],ITEM_GROUP_COL);
              }else {
                model.setValueAt("n",rows[0],ITEM_GROUP_COL);
              }
              itemRows.removeAllElements();
              itemColor = false;
            }else {
              //color for item
              //1-31-02
              //for (int i = ITEM_PART_COL; i < COMP_DESC_COL; i++) {
              int binPosition = COMP_DESC_COL;
              if (binRB.isSelected() || lotRB.isSelected()) {
                binPosition = BIN_COL;
              }
              for (int i = ITEM_PART_COL; i < binPosition; i++) {
                cols = new int[]{i};
                cellAtt.combine(rows,cols);
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              //color for component
              //for (int i = COMP_DESC_COL; i < colHeads.length; i++) {
              for (int i = binPosition; i < colHeads.length; i++) {
                cols = new int[]{i};
                cellColorAtt.setBackground(new Color(255,255,255),rows,cols);
              }
              //set y if item is a kit
              if (itemRows.size() > 1) {
                model.setValueAt("y",rows[0],ITEM_GROUP_COL);
              }else {
                model.setValueAt("n",rows[0],ITEM_GROUP_COL);
              }
              itemRows.removeAllElements();
              itemColor = true;
            }
      }
    }
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
    return model;
  }

  String getSelectedFacilityID() {
    Vector facilityIDV = (Vector) dataH.get("FACILITY_ID");
    return ((String) facilityIDV.elementAt(facC.getSelectedIndex()));
  }

  String getSelectedHubID() {
    return (String)warehouseC.getSelectedItem();
  }

  void fillTableAction() {
    stateChanged("Loading ...");
    int i;
    boolean fac,ware,eD,pD,oH,oO;
    fac = ware = eD = pD = oH = oO = false;

    Object[][] data = null;
    Hashtable result = new Hashtable();
    String facility = getSelectedFacilityID();
    searchFacId = new String(facility);
    if (!facility.equals("") && !facility.equals("All")) {
      fac = true;
    }
    String warehouse = getSelectedHubID();
    if (!warehouse.equals("") && !warehouse.equals("All")) {
      ware = true;
    }
    String item = itemText.getText();

    String expVal = "";
    String exp2Val = "";
    if (onHandCheck.isSelected()){
      oH =true;
      expVal = expDate.getText();
      if (expVal.length() > 0){
        try {
          Integer test = new Integer(expVal);
          eD = true;
        } catch (Exception e){
          GenericDlg err = new GenericDlg(parent.getMain(),"Audit",true);
          err.setMsg("Please enter a number for \"Expires within\" field");
          err.setVisible(true);
          return;
        }
      }
      exp2Val = exp2Date.getText();
      if (exp2Val.length() > 0){
        try {
          Integer test = new Integer(exp2Val);
          eD = true;
        } catch (Exception e){
          GenericDlg err = new GenericDlg(parent.getMain(),"Audit",true);
          err.setMsg("Please enter a number for \"Expires after\" field");
          err.setVisible(true);
          return;
        }
      }
    } else {
      expVal = "";
      exp2Val = "";
      eD = false;
    }

    String promVal = "";
    if (onOrderCheck.isSelected()){
      oO =true;
      promVal = byDate.getText();
      if (promVal.length() > 0){
        try {
          Integer test = new Integer(promVal);
          pD = true;
        } catch (Exception e){
          GenericDlg err = new GenericDlg(parent.getMain(),"Audit",true);
          err.setMsg("Please enter a number for \"Arrives within\" field");
          err.setVisible(true);
          return;
        }
      } else {
        pD = false;
      }
    } else {
      promVal = "";
      pD = false;
    }
    boolean noRecs = false;
    try {
       //save this global variable for right mouse click
       searchedWareHouse = BothHelpObjs.makeBlankFromNull(warehouse);
       long sTime = new java.util.Date().getTime();
       ClientHelpObjs.setEnabledContainer(this,false);
       TcmisHttpConnection cgi = new TcmisHttpConnection("InventoryObj",this.parent);
       Hashtable query = new Hashtable();
       query.put("ACTION","GET_TABLE_DATA"); //String, String
       Hashtable dataH = new Hashtable();
       dataH.put("USER_ID",parent.getUserId());    //String, Integer
       dataH.put("FACILITY",BothHelpObjs.makeBlankFromNull(facility));
       dataH.put("USER_FAC",BothHelpObjs.makeBlankFromNull(userFac));
       dataH.put("WAREHOUSE",BothHelpObjs.makeBlankFromNull(warehouse));
       dataH.put("ITEM",BothHelpObjs.makeBlankFromNull(item));
       dataH.put("EXP_VAL",BothHelpObjs.makeBlankFromNull(expVal));
       dataH.put("EXP2_VAL",BothHelpObjs.makeBlankFromNull(exp2Val));
       dataH.put("PROM_VAL",BothHelpObjs.makeBlankFromNull(promVal));
       dataH.put("FAC_BOL",new Boolean(fac));
       dataH.put("WARE_BOL",new Boolean(ware));
       dataH.put("EXP_BOL",new Boolean(eD));
       dataH.put("PROM_BOL",new Boolean(pD));
       dataH.put("ON_HOLD_BOL",new Boolean(oH));
       dataH.put("ON_ORDER_BOL",new Boolean(oO));
       dataH.put("ALL_PARTS",new Boolean(this.allPartsC.isSelected()));
       if (itemRB.isSelected()) {
        dataH.put("ORDER_BY","ITEM");
       }else if (binRB.isSelected()) {
        dataH.put("ORDER_BY","BIN");
       }else {
        dataH.put("ORDER_BY","LOT");
       }

       //System.out.println(dataH);
       query.put("DATA",dataH);

       result = cgi.getResultHash(query);
       if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
       }

       Hashtable h = (Hashtable) result.get("TABLE_DATA");
       // Dates
       String efd = BothHelpObjs.makeBlankFromNull((String) result.get("EXPIRE"));
       String e2fd= BothHelpObjs.makeBlankFromNull((String) result.get("EXPIRE2"));
       String pfd = BothHelpObjs.makeBlankFromNull((String) result.get("PROMISSED"));
       eDate.setText("");
       if (efd.length()>0){
           eDate.setText("(" + efd+")");
       }
       e2Date.setText("");
       if (e2fd.length()>0){
           e2Date.setText("(" + e2fd+")");
       }
       aDate.setText("");
       if (pfd.length()>0){
           aDate.setText("(" + pfd+")");
       }

       Hashtable keyCol = (Hashtable) h.get("KEY_COLUMNS");
       GROUP_COL = ((Integer)keyCol.get("Group")).intValue();
       PART_COL = ((Integer)keyCol.get("Part")).intValue();
       DESC_COL = ((Integer)keyCol.get("Description")).intValue();
       ITEM_PART_COL = ((Integer)keyCol.get("# per Part")).intValue();
       ITEM_COL = ((Integer)keyCol.get("Item")).intValue();
       PACKAGING_COL = ((Integer)keyCol.get("Packaging")).intValue();
       CATALOG_COL = ((Integer)keyCol.get("Catalog")).intValue();
       CATALOG_COMPANY_ID_COL = ((Integer)keyCol.get("Catalog Company ID")).intValue();
       INVENTORY_GROUP_COL = ((Integer)keyCol.get("Inventory Group")).intValue();
       TYPE_COL = ((Integer)keyCol.get("Type")).intValue();
       MATERIAL_COL = ((Integer)keyCol.get("Material")).intValue();
       MSDS_ONLINE_COL = ((Integer)keyCol.get("MSDS")).intValue();
       PART_GROUP_COL = ((Integer)keyCol.get("Part Group")).intValue();
       ITEM_GROUP_COL = ((Integer)keyCol.get("Item Group")).intValue();
       COMP_DESC_COL = ((Integer)keyCol.get("Component Description")).intValue();
       PART_COLOR_COL = ((Integer)keyCol.get("Part Color")).intValue();
       MANUFACTURER_COL = ((Integer)keyCol.get("Manufacturer")).intValue();

       if (lotRB.isSelected()) {
        BIN_COL = ((Integer)keyCol.get("Bin")).intValue();
        LOT_COL = ((Integer)keyCol.get("Lot")).intValue();
       }else if (binRB.isSelected()) {
        BIN_COL = ((Integer)keyCol.get("Bin")).intValue();
       }

       AttributiveCellTableModel model = buildSearchTableNew(h);                               //9-25-01 get data from server and build model here
       Hashtable tableAttribute = (Hashtable) h.get("TABLE_ATTRIBUTE");

      if (model.getRowCount() <= 0) {
        GenericDlg err = new GenericDlg(parent.getMain(),"Not Found",true);
        err.setMsg("No records found.");
        err.show();
        invTable = new MultiSpanCellTable(model);
        invTable.setIntercellSpacing(new Dimension(0,0));
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

        //font and foreground and background color for columns heading
        String fontName = (String)tableAttribute.get("FONT_NAME");
        Integer fontStyle = (Integer)tableAttribute.get("FONT_STYLE");
        Integer fontSize = (Integer)tableAttribute.get("FONT_SIZE");
        invTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableAttribute.get("HEADER_FOREGROUND"),(Color)tableAttribute.get("HEADER_BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = invTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
        }

        // set column widths
        invTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
        for(i=0;i<invTable.getColumnCount();i++){
          int width = model.getColumnWidth(i);
          invTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
          invTable.getColumn(model.getColumnName(i)).setWidth(width);
          if (width==0){
            invTable.getColumn(model.getColumnName(i)).setMinWidth(width);
            invTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
          }
        }

        holder.remove(JSP);
        JSP = new JScrollPane(invTable);
        holder.add(JSP, BorderLayout.CENTER);
        holder.revalidate();
        stateChanged("No Recs Found");
      }else {
        this.searchFacility = facility;

        //sorterInv = new TableSorter(model);
        invTable = new MultiSpanCellTable(model);
        //sorterInv.setColTypeFlag(colTypeFlagNew.longValue());
        //invTable.addMouseListenerToHeaderInTable(invTable,sorterInv);
        invTable.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            invTable_mouseClicked(e);
          }
          public void mouseReleased(MouseEvent e) {
            invTable_mouseReleased(e);
          }
        });
        invTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        invTable.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
        invTable.getTableHeader().setResizingAllowed(true);

        //control by server
        //cell border
        invTable.setIntercellSpacing(new Dimension(0,0));
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
        TableColumnModel m = invTable.getColumnModel();
        i = 0;
        for (i = 0; i < invTable.getColumnCount(); i++) {
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
        invTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableAttribute.get("HEADER_FOREGROUND"),(Color)tableAttribute.get("HEADER_BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = invTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
        }

        // set column widths
        invTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
        for(i=0;i<invTable.getColumnCount();i++){
          int width = model.getColumnWidth(i);
          invTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
          invTable.getColumn(model.getColumnName(i)).setWidth(width);
          if (width==0){
            invTable.getColumn(model.getColumnName(i)).setMinWidth(width);
            invTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
          }
        }

        holder.remove(JSP);
        JSP = new JScrollPane(invTable);
        holder.add(JSP, BorderLayout.CENTER);
        holder.revalidate();

        stateChanged("Search");
        loadReportFields();

        long eTime = new java.util.Date().getTime();
        long min = (eTime-sTime);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date(min));
        String tmsg = "Records Found: " + invTable.getRowCount() + "     Search Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds";
        parent.getMain().countLabel.put(new Integer(Main.INVENTORY),tmsg);
        parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.INVENTORY)));
      }
    } catch (Exception e){
      GenericDlg.showAccessDenied(parent.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      e.printStackTrace();
      System.out.println("InventWin:fillTable->Got error");
    }
    ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    // this makes the cursor work better, but it's just a kludge...
    ClientHelpObjs.setEnabledContainer(this,true);
    ClientHelpObjs.setComboBoxUpdateUi(this);
    detailB.setEnabled(false);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);

    checkLogic("onHand");
    checkLogic("onOrder");
  }

  void setColTypes(){
    sorterInv.setColTypeFlag(colTypes);
  }

  void setColWidths(){
    for(int i=0;i<colHeads.length;i++){
      invTable.getColumn(colHeads[i]).setPreferredWidth(colWidths[i]);
      invTable.getColumn(colHeads[i]).setWidth(colWidths[i]);
      if (colWidths[i]==0){
         invTable.getColumn(colHeads[i]).setMinWidth(colWidths[i]);
         invTable.getColumn(colHeads[i]).setMaxWidth(colWidths[i]);
      }
    }
  }


  void loadReportFields() {
    rptSearchText = itemText.getText();
    rptFac = facC.getSelectedItem().toString();
    rptWarehouse = warehouseC.getSelectedItem().toString();
    rptExpDays = expDate.getText();
    rptArrDays = byDate.getText();
    rptExp = onHandCheck.isSelected();
    rptArr = onOrderCheck.isSelected();
  }

  void printScreenData() {
    ReportOptionDlg rod = new ReportOptionDlg(parent.getMain());
    rod.setCmisApp(parent);

    boolean fac,ware,eD,pD,oH,oO;
    fac = ware = eD = pD = oH = oO = false;


    String facility = getSelectedFacilityID();
    searchFacId = new String(facility);
    if (!facility.equals("") && !facility.equals("All")) {
      fac = true;
    }
    String warehouse = getSelectedHubID();
    if (!warehouse.equals("") && !warehouse.equals("All")) {
      ware = true;
    }

    String item = itemText.getText();

    String expVal = "";
    String exp2Val = "";
    if (onHandCheck.isSelected()){
      oH =true;
      expVal = expDate.getText();
      if (expVal.length() > 0){
        try {
          Integer test = new Integer(expVal);
          eD = true;
        } catch (Exception e){
          expVal = "";
        }
      }
      exp2Val = exp2Date.getText();
      if (exp2Val.length() > 0){
        try {
          Integer test = new Integer(exp2Val);
          eD = true;
        } catch (Exception e){
          exp2Val = "";
        }
      }
    } else {
      expVal = "";
      exp2Val = "";
      eD = false;
    }

    String promVal = "";
    if (onOrderCheck.isSelected()){
      oO =true;
      promVal = byDate.getText();
      if (promVal.length() > 0){
        try {
          Integer test = new Integer(promVal);
          pD = true;
        } catch (Exception e){
          promVal = "";
        }
      } else {
        pD = false;
      }
    } else {
      promVal = "";
      pD = false;
    }

    rod.setFacId(getSelectedFacilityID());
    rod.setSearchText(BothHelpObjs.makeBlankFromNull(itemText.getText()));

    rod.setWareHouse(getSelectedHubID());
    rod.setOnHand(onHandCheck.isSelected());
    rod.setOnOrder(onOrderCheck.isSelected());
    rod.setExpDate(BothHelpObjs.makeBlankFromNull(expVal));
    rod.setExp2Date(BothHelpObjs.makeBlankFromNull(exp2Val));
    rod.setByDate(BothHelpObjs.makeBlankFromNull(promVal));
    rod.setAllParts(allPartsC.isSelected());

    rod.setUserFac(BothHelpObjs.makeBlankFromNull(userFac));
    rod.setItem(BothHelpObjs.makeBlankFromNull(item));
    rod.setFacBol(fac);
    rod.setWareBol(ware);
    rod.setExpBol(eD);
    rod.setPromBol(pD);
    rod.setOnHoldBol(oH);
    rod.setOnOrderBol(oO);
    if (itemRB.isSelected()) {
      rod.setOrderBy("ITEM");
    }else if (binRB.isSelected()) {
      rod.setOrderBy("BIN");
    }else {
      rod.setOrderBy("LOT");
    }

    //System.out.println(" Values   "+fac+ware+eD+pD+oH+oO);
    rod.setScreen("Inventory");
    //Nawaz 01-17-02
    rod.setWaName(warehouseC.getSelectedItem().toString());
    rod.setFacName(facC.getSelectedItem().toString());
    rod.loadIt();
  }

  void invTable_mouseReleased(MouseEvent e) {
    int firstIndex = invTable.getSelectedRow();
    int lastIndex = firstIndex;
    if (e.isMetaDown()) {
      Point p = e.getPoint();
      firstIndex = invTable.rowAtPoint(p);
    }
    String selectedPart = (String)invTable.getModel().getValueAt(firstIndex,PART_COL);
    String selectedPartGroup = (String)invTable.getModel().getValueAt(firstIndex,PART_GROUP_COL);
    //System.out.println("--- mouse released: "+selectedPart+ " - "+selectedPartGroup);
    //determine where the first index going to start
    for (int i = firstIndex -1; i >= 0 ; i--) {
      String tempPart = (String)invTable.getModel().getValueAt(i,PART_COL);
      String tempPartGroup = (String)invTable.getModel().getValueAt(i,PART_GROUP_COL);
      if (selectedPart.equals(tempPart) && selectedPartGroup.equals(tempPartGroup)) {
        firstIndex = i;
      }else {
        break;
      }
    }
    //determine where the last index going to end
    for (int i = firstIndex + 1; i < invTable.getRowCount(); i++) {
      String tempPart = (String)invTable.getModel().getValueAt(i,PART_COL);
      String tempPartGroup = (String)invTable.getModel().getValueAt(i,PART_GROUP_COL);
      if (selectedPart.equals(tempPart) && selectedPartGroup.equals(tempPartGroup)) {
        lastIndex = i;
      }else {
        break;
      }
    }
    invTable.setRowSelectionInterval(firstIndex,lastIndex);
    invTable.repaint();
    invTable.validate();
  }

  void invTable_mouseClicked(MouseEvent e) {
    int firstIndex = invTable.getSelectedRow();
    int lastIndex = firstIndex;
    if (e.isMetaDown()) {
      Point p = e.getPoint();
      firstIndex = invTable.rowAtPoint(p);
    }
    String selectedPart = (String)invTable.getModel().getValueAt(firstIndex,PART_COL);
    String selectedPartGroup = (String)invTable.getModel().getValueAt(firstIndex,PART_GROUP_COL);
    //determine where the first index going to start
    for (int i = firstIndex -1; i >= 0 ; i--) {
      String tempPart = (String)invTable.getModel().getValueAt(i,PART_COL);
      String tempPartGroup = (String)invTable.getModel().getValueAt(i,PART_GROUP_COL);
      if (selectedPart.equals(tempPart) && selectedPartGroup.equals(tempPartGroup)) {
        firstIndex = i;
      }else {
        break;
      }
    }
    //determine where the last index going to end
    for (int i = firstIndex + 1; i < invTable.getRowCount(); i++) {
      String tempPart = (String)invTable.getModel().getValueAt(i,PART_COL);
      String tempPartGroup = (String)invTable.getModel().getValueAt(i,PART_GROUP_COL);
      if (selectedPart.equals(tempPart) && selectedPartGroup.equals(tempPartGroup)) {
        lastIndex = i;
      }else {
        break;
      }
    }
    invTable.setRowSelectionInterval(firstIndex,lastIndex);
    invTable.repaint();
    invTable.validate();

    JTable tempTable = (JTable) e.getSource();
    invSel = tempTable.getSelectedRow();

    int catSel = 0;
    if(e.isMetaDown()) {
      Point p = e.getPoint();
      catSel = invTable.rowAtPoint(p);
      int colSel = invTable.columnAtPoint(p);
      //System.out.println("-------- point postion: "+p+" column: "+catTableNew.columnAtPoint(p)+" row: "+catSel);
      if (colSel <= TYPE_COL) {
        PartMetaPopUp pmpu = new PartMetaPopUp(parent,invTable.getModel().getValueAt(catSel,PART_COL).toString(),searchFacility,
            invTable.getModel().getValueAt(catSel,PART_GROUP_COL).toString(),invTable.getModel().getValueAt(catSel,this.CATALOG_COL).toString(),"",false,
            invTable.getModel().getValueAt(catSel,this.TYPE_COL).toString());
        holder.add(pmpu);
        pmpu.setCatalogCompanyId(invTable.getModel().getValueAt(catSel,this.CATALOG_COMPANY_ID_COL).toString());
        pmpu.setPackaging(invTable.getModel().getValueAt(catSel,this.PACKAGING_COL).toString());
        pmpu.setTradeName(invTable.getModel().getValueAt(catSel,this.COMP_DESC_COL).toString());
        pmpu.setMfg(invTable.getModel().getValueAt(catSel,this.MANUFACTURER_COL).toString());
        pmpu.setMaterialID(invTable.getModel().getValueAt(catSel,MATERIAL_COL).toString());
        pmpu.show(invTable,e.getX(),e.getY());
        return;
      }else if (colSel >= COMP_DESC_COL) {
        ComponentMetaPopUp cmpu = new ComponentMetaPopUp(parent,
              invTable.getModel().getValueAt(catSel,MATERIAL_COL).toString(),
              invTable.getModel().getValueAt(catSel,ITEM_COL).toString(),searchFacility,
              invTable.getModel().getValueAt(catSel,MSDS_ONLINE_COL).toString());
        holder.add(cmpu);
        cmpu.show(invTable,e.getX(),e.getY());
        return;
      }else if (colSel > TYPE_COL && colSel < COMP_DESC_COL) {
        InventoryItemMetaPopUp mpu = new InventoryItemMetaPopUp(parent,invTable.getModel().getValueAt(catSel,ITEM_COL).toString(),searchFacility,
                invTable.getModel().getValueAt(catSel,PART_COL).toString(),invTable.getModel().getValueAt(catSel,this.CATALOG_COL).toString());
        holder.add(mpu);
        mpu.show(invTable,e.getX(),e.getY());
        mpu.setCatalogCompanyId(invTable.getModel().getValueAt(catSel,this.CATALOG_COMPANY_ID_COL).toString());
        mpu.setPackaging(invTable.getModel().getValueAt(catSel,this.PACKAGING_COL).toString());
        mpu.setTradeName(invTable.getModel().getValueAt(catSel,this.COMP_DESC_COL).toString());
        mpu.setMfg(invTable.getModel().getValueAt(catSel,this.MANUFACTURER_COL).toString());
        mpu.setInventoryGroup(invTable.getModel().getValueAt(catSel,this.INVENTORY_GROUP_COL).toString());
        mpu.setCatalogCompanyId(invTable.getModel().getValueAt(catSel,this.CATALOG_COMPANY_ID_COL).toString());
        mpu.setPartGroupNo(invTable.getModel().getValueAt(catSel,this.PART_GROUP_COL).toString());
        return;
      }
    }
  }

  void goInventFloat(){
    synchronized (this){
      if (IF != null){
        IF.setVisible(false);
        IF.dispose();
        IF = null;
      }
    }
    InventWin_InventFloatThread ef = new InventWin_InventFloatThread(this);
    ef.start();
  }

  void loadInventFloatAction() {
      IF = new InventFloat(parent.getMain(),parent);
      IF.setItem((new Integer(BothHelpObjs.makeZeroFromNull(invTable.getModel().getValueAt(invSel,ITEM_ID).toString()))).intValue());
      IF.setHub(BothHelpObjs.makeBlankFromNull(invTable.getModel().getValueAt(invSel,HUB).toString()));
      IF.setFacilityId(getSelectedFacilityID());
      IF.loadIt();
  }

  public void itemStateChanged(ItemEvent e) {
     stateChanged("Refresh");
  }

  void checkLogic(String flag){
     stateChanged("Refresh");
     if (onHandCheck.isSelected() || onOrderCheck.isSelected()){
       setExpLabel(onOrderCheck.isSelected());
       setByLabel(onHandCheck.isSelected());
       return;
     }
     if (flag.equals("onHand")){
       onOrderCheck.setSelected(true);
       setExpLabel(onOrderCheck.isSelected());
       setByLabel(onHandCheck.isSelected());
       return;
     }
     if (flag.equals("onOrder")){
       onHandCheck.setSelected(true);
       setExpLabel(onOrderCheck.isSelected());
       setByLabel(onHandCheck.isSelected());
       return;
     }
     return;
  }

  protected void setExpLabel(boolean set) {
    byLabel1.setEnabled(set);
    byLabel2.setEnabled(set);
    byDate.setEnabled(set);
  }
  protected void setByLabel(boolean set) {
    expLabel1.setEnabled(set);
    expLabel2.setEnabled(set);
    expDate.setEnabled(set);

    jLabel1.setEnabled(set);
    jLabel2.setEnabled(set);
    exp2Date.setEnabled(set);
  }

  void setInvTableWidths() {
    setInvTableWidths(false);
  }

  void setInvTableWidths(boolean fake) {
 }


  void S_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     detailB.setEnabled(false);
     fillTable();
  }

  void itemText_textValueChanged(TextEvent e) {
    //change state
    stateChanged("Refresh");
  }

  void setWarehouse(String action) {
    Vector facilityIDV = (Vector) dataH.get("FACILITY_ID");
    String facilityID = (String) facilityIDV.elementAt(facC.getSelectedIndex());
    if (dataH.containsKey(facilityID)) {
      Vector facWareV = (Vector) dataH.get(facilityID);
      if (facWareV.size() > 0) {
        System.out.println("-------- first item");
        warehouseC.setSelectedItem((String)facWareV.firstElement());
      }else {
        warehouseC.setSelectedItem("All Hubs");
        System.out.println("-------- no item");
      }
    }else {
      warehouseC.setSelectedItem("All Hubs");
      System.out.println("-------- not in hashtable");
    }
    //changed
    stateChanged(action);
  }  //end of method

  void wareChoice_itemStateChanged(ItemEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void expDate_textValueChanged(TextEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void byDate_textValueChanged(TextEvent e) {
    //changed
    stateChanged("Refresh");
  }

  void onHandCheck_itemStateChanged(ItemEvent e) {
    //check box changed
    checkLogic("onHand");
  }

  void onOrderCheck_itemStateChanged(ItemEvent e) {
    //check box changed
    checkLogic("onOrder");
  }

  void this_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        fillTable();
    }
  }

  void JSP_componentResized(ComponentEvent e) {
    holder.validate();
    setInvTableWidths(invTable.getColumnCount() < 9);
    invTable.validate();
    JSP.validate();
    holder.validate();
    holder.repaint();
  }

  void detailB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goInventFloat();
  }

  void itemRB_actionPerformed(ActionEvent e) {
    if (itemRB.isSelected()) {
      binRB.setSelected(false);
      lotRB.setSelected(false);
    }else {
      itemRB.setSelected(true);
    }
  }

  void binRB_actionPerformed(ActionEvent e) {
    if (binRB.isSelected()) {
      itemRB.setSelected(false);
      lotRB.setSelected(false);
    }else {
      binRB.setSelected(true);
    }
  }

  void lotRB_actionPerformed(ActionEvent e) {
    if (lotRB.isSelected()) {
      itemRB.setSelected(false);
      binRB.setSelected(false);
    }else {
      lotRB.setSelected(true);
    }
  }
}

class InventWin_invTable_mouseAdapter extends java.awt.event.MouseAdapter {
  InventWin adaptee;

  InventWin_invTable_mouseAdapter(InventWin adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.invTable_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
    adaptee.invTable_mouseReleased(e);
  }
}

class InventThread extends Thread {
  InventWin parent;

  public InventThread(InventWin parent){
     super("InventThread");
     this.parent = parent;
  }

  public void run(){
     parent.fillTableAction();
  }
}




class InventWinLoadThread extends Thread {
 InventWin parent;

  public InventWinLoadThread(InventWin parent){
     super("InventWinLoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadItAction();
  }
}

class InventWin_InventFloatThread extends Thread {
 InventWin parent;

  public InventWin_InventFloatThread(InventWin parent){
     super("InventFloatThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadInventFloatAction();
  }
}

class InventWin_S_actionAdapter implements java.awt.event.ActionListener {
  InventWin adaptee;


  InventWin_S_actionAdapter(InventWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.S_actionPerformed(e);
  }
}

class InventWin_S_keyAdapter extends java.awt.event.KeyAdapter {
  InventWin adaptee;


  InventWin_S_keyAdapter(InventWin adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.this_keyPressed(e);
  }
}

class InventWin_onOrderCheck_itemAdapter implements java.awt.event.ItemListener{
  InventWin adaptee;


  InventWin_onOrderCheck_itemAdapter(InventWin adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.onOrderCheck_itemStateChanged(e);
  }
}

class InventWin_expDate_textAdapter implements java.awt.event.TextListener{
  InventWin adaptee;


  InventWin_expDate_textAdapter(InventWin adaptee) {
    this.adaptee = adaptee;
  }

  public void textValueChanged(TextEvent e) {
    adaptee.expDate_textValueChanged(e);
  }
}

class InventWin_byDate_textAdapter implements java.awt.event.TextListener{
  InventWin adaptee;


  InventWin_byDate_textAdapter(InventWin adaptee) {
    this.adaptee = adaptee;
  }

  public void textValueChanged(TextEvent e) {
    adaptee.byDate_textValueChanged(e);
  }
}

class InventWin_onHandCheck_itemAdapter implements java.awt.event.ItemListener{
  InventWin adaptee;


  InventWin_onHandCheck_itemAdapter(InventWin adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.onHandCheck_itemStateChanged(e);
  }
}

class InventWin_itemText_textAdapter implements java.awt.event.TextListener{
  InventWin adaptee;


  InventWin_itemText_textAdapter(InventWin adaptee) {
    this.adaptee = adaptee;
  }

  public void textValueChanged(TextEvent e) {
    adaptee.itemText_textValueChanged(e);
  }
}

class InventWin_wareChoice_itemAdapter implements java.awt.event.ItemListener{
  InventWin adaptee;


  InventWin_wareChoice_itemAdapter(InventWin adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.wareChoice_itemStateChanged(e);
  }
}
class InventWin_key_press_adapter extends java.awt.event.KeyAdapter {
  InventWin adaptee;
  InventWin_key_press_adapter(InventWin adaptee){
    this.adaptee = adaptee;
  }
  public void keyPressed(KeyEvent e) {
    adaptee.this_keyPressed(e);
  }
}

class InventWin_FacChoice_ActionListener implements ActionListener {
  InventWin adaptee;
  public InventWin_FacChoice_ActionListener(InventWin adaptee) {
    this.adaptee = adaptee;
  }
   public void actionPerformed(ActionEvent e){
    adaptee.setWarehouse("Refresh");
   }
}
class InventWin_detailB_Item_Listener implements ActionListener {
  InventWin adaptee;
  InventWin_detailB_Item_Listener(InventWin adaptee){
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent ae){
    adaptee.detailB_actionPerformed(ae);
  }
}



