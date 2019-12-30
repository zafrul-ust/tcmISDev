//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
//import radian.tcmis.client.share.reports.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
//import radian.tcmis.client.share.reports.appData.*;
import java.beans.*;
import radian.tcmis.client.share.gui.*;
import java.text.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class WasteAddEditMaterialRequest extends JDialog {
  CmisTable newWasteMaterialTable;
  CmisApp cmis;
  //CmisTable chargeTable;
  JTable chargeNumTable;
  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel centerP = new JPanel();
  JPanel topP = new JPanel();
  StaticJLabel actSysL = new StaticJLabel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel chargeP = new JPanel();
  JScrollPane partJSP = new JScrollPane();
  JPanel detailP = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();

  String facilityId;
  String storageLocation;
  int PROFILE_DESC_COL = 0;
  int PROFILE_ID_COL = 0;
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  String vendorId;
  int selectedR;
  Integer addIntoContainerId = null;
  JRadioButton directR = new JRadioButton();
  JRadioButton indirectR = new JRadioButton();
  String vendorProfileDesc = null;
  String lastActSysId = new String("");
  Hashtable actInfoH;
  Hashtable chargeH = new Hashtable();
  String pack = null;
  String stateWasteCode = null;
  StaticJLabel facilityL = new StaticJLabel();
  DataJLabel facIdL = new DataJLabel();
  Vector wasteLocationIdV = null;
  DataJLabel actSysT = new DataJLabel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JButton addB = new JButton();
  JButton deleteB = new JButton();
  JButton ccardB = new JButton();
  JScrollPane detailJSP = new JScrollPane();
  JPanel jPanel1 = new JPanel();
  int orderNo = 0;
  DataJLabel storageT = new DataJLabel();
  StaticJLabel storageL = new StaticJLabel();
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel extendT = new DataJLabel();

  String shipmentID;
  Integer selectedLine = new Integer(0);
  public Hashtable headerInfo;
  Hashtable chargeNumbers;
  Vector actSysIDV;
  StaticJLabel itemLpp = new StaticJLabel();

  WasteOrder wasteOrder;
  Boolean listChanged = new Boolean(false);
  Boolean rowDeleted = new Boolean(false);

  JComboBox generationPtC = new JComboBox();
  Hashtable generationPts;
  StaticJLabel generationL = new StaticJLabel();
  Hashtable tableStyle;

  boolean deletingData = false;

  int LINE_COL = 0;
  int DELETE_COL = 0;
  int ITEM_COL = 0;
  int PART_NO_COL = 0;
  int QTY_COL = 0;
  int DESC_COL = 0;
  int PACKAGING_COL = 0;
  int DOT_COL = 0;
  int LPP_COL = 0;
  int ACT_COL = 0;
  int CTYPE_COL = 0;

  JPanel chargeTypeP = new JPanel();
  GridBagLayout gridBagLayout6 = new GridBagLayout();

  String lastChargeType = "";
  String lastAccumulationPt = "";
  Waste_Generate_itemAdapter wgi;
  JComboBox poC = new JComboBox();
  StaticJLabel poL = new StaticJLabel("BPO/PO: ");

  public WasteAddEditMaterialRequest(JFrame fr, String title, WasteOrder wo) {
    super(fr, title, false);
    this.wasteOrder = wo;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(700, 500));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setOrderNo(int val) {
    this.orderNo = val;
  }

  public void setAddIntoContainerId(Integer val) {
    this.addIntoContainerId = val;
  }

  public void setVendorProfileDesc(String val) {
    this.vendorProfileDesc = val;
  }

  public void setStateWasteCode(String val) {
    this.stateWasteCode = val;
  }

  public void setPackaging(String val) {
    this.pack = val;
  }

  public void setParent(CmisApp c){
    cmis = c;
  }

  public void setFacilityID(String val) {
    this.facilityId = val;
  }

  public void setVendorID(String val) {
    this.vendorId = val;
  }

  public void setShipmentID(String val) {
    this.shipmentID = val;
  }
   /*
  public void setActSysIDV(Vector asv) {
    this.actSysIDV = asv;
  }  */

  public void loadIt(){
    loadItAction();
    /*
    WasteAddEditMaterialRequestLoadThread x = new WasteAddEditMaterialRequestLoadThread(this);
    x.start(); */
  }

  void jbInit() throws Exception {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    panel1.setLayout(gridBagLayout1);
    centerP.setMaximumSize(new Dimension(150, 100));
    centerP.setMinimumSize(new Dimension(150, 100));
    centerP.setPreferredSize(new Dimension(150, 100));
    centerP.setLayout(gridBagLayout4);
    topP.setMaximumSize(new Dimension(450, 125));
    topP.setMinimumSize(new Dimension(450, 125));
    topP.setPreferredSize(new Dimension(450, 125));
    topP.setLayout(gridBagLayout3);
    topP.setBorder(ClientHelpObjs.groupBox("Waste Material Request"));

    actSysL.setText("Account Sys:");
    //chargeTable = new CmisTable();
    chargeNumTable = new JTable();
    partJSP.getViewport().setView(chargeNumTable);
    newWasteMaterialTable = new CmisTable();
    detailJSP.getViewport().setView(newWasteMaterialTable);

    panel1.setMaximumSize(new Dimension(500, 500));
    panel1.setMinimumSize(new Dimension(500, 500));
    panel1.setPreferredSize(new Dimension(500, 500));
    chargeP.setMaximumSize(new Dimension(250, 140));
    chargeP.setMinimumSize(new Dimension(250, 140));
    chargeP.setPreferredSize(new Dimension(250, 140));
    chargeP.setLayout(gridBagLayout5);
    //chargeP.setBorder(ClientHelpObjs.groupBox("Charge Detail"));

    detailP.setMaximumSize(new Dimension(200, 100));
    detailP.setMinimumSize(new Dimension(120, 100));
    detailP.setPreferredSize(new Dimension(200, 100));
    detailP.setLayout(gridBagLayout2);
    detailP.setBorder(ClientHelpObjs.groupBox("Line Item Detail"));

    partJSP.setMaximumSize(new Dimension(230, 120));
    partJSP.setMinimumSize(new Dimension(220, 120));
    partJSP.setPreferredSize(new Dimension(220, 120));
    directR.setText("  Direct  ");
    directR.setFont(new java.awt.Font("Dialog", 0, 11));
    directR.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        directR_actionPerformed(e);
      }
    });
    indirectR.setText("  Indirect  ");
    indirectR.setFont(new java.awt.Font("Dialog", 0, 11));
    indirectR.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        indirectR_actionPerformed(e);
      }
    });
    facilityL.setText("Facility:");
    facIdL.setText("Facility ID");
    actSysT.setText("Account Sys ID");
    okB.setFont(new java.awt.Font("Dialog", 0, 11));
    okB.setText("OK");
    okB.setMaximumSize(new Dimension(90, 25));
    okB.setMinimumSize(new Dimension(90, 25));
    okB.setPreferredSize(new Dimension(90, 25));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/checked.gif","OK"));
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    cancelB.setFont(new java.awt.Font("Dialog", 0, 11));
    cancelB.setText("Cancel");
    cancelB.setMaximumSize(new Dimension(90, 25));
    cancelB.setMinimumSize(new Dimension(90, 25));
    cancelB.setPreferredSize(new Dimension(90, 25));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel2.gif","Cancel"));
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    addB.setFont(new java.awt.Font("Dialog", 0, 11));
    addB.setText("Add");
    addB.setMaximumSize(new Dimension(90, 25));
    addB.setMinimumSize(new Dimension(90, 25));
    addB.setPreferredSize(new Dimension(90, 25));
    addB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Save"));
    addB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });
    deleteB.setFont(new java.awt.Font("Dialog", 0, 11));
    deleteB.setText("Delete");
    deleteB.setMaximumSize(new Dimension(90, 25));
    deleteB.setMinimumSize(new Dimension(90, 25));
    deleteB.setPreferredSize(new Dimension(90, 25));
    deleteB.setIcon(ResourceLoader.getImageIcon("images/button/minus.gif","Delete"));
    deleteB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteB_actionPerformed(e);
      }
    });
    ccardB.setFont(new java.awt.Font("Dialog", 0, 10));
    ccardB.setText("Credit Card Information");
    ccardB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ccardB_actionPerformed(e);
      }
    });
    storageT.setText("jLabel1");
    storageL.setText("Storage Area:");
    jLabel1.setText("Extended LPP:");
    extendT.setText("jLabel2");

    itemLpp.setText("0 @ 0.00/Unit = 0.00");

    chargeTypeP.setLayout(gridBagLayout6);
    generationPtC.setFont(new java.awt.Font("Dialog", 0, 10));
    generationPtC.setMaximumSize(new Dimension(32767, 22));
    generationPtC.setMinimumSize(new Dimension(124, 22));
    generationPtC.setPreferredSize(new Dimension(128, 22));
    generationL.setText("Accumulation Pt:");
    poC.setFont(new java.awt.Font("Dialog", 0, 10));
    getContentPane().add(panel1);
    panel1.add(topP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(actSysL, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    topP.add(facilityL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    topP.add(facIdL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 80), 0, 0));
    topP.add(actSysT, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 10), 0, 0));
    topP.add(okB, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 50, 0, 5), 0, 0));
    topP.add(cancelB, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 50, 0, 5), 0, 0));
    topP.add(addB, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
    topP.add(deleteB, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    topP.add(ccardB, new GridBagConstraints2(4, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 40, 5, 5), 0, 0));
    topP.add(storageT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
    topP.add(storageL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    topP.add(jLabel1, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    topP.add(extendT, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));

    panel1.add(centerP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 0, 13, 0), 0, 0));
    centerP.add(detailP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 0), 0, 0));
    detailP.add(detailJSP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    centerP.add(chargeTypeP, new GridBagConstraints2(0, 1, 1, 1, 0.25, 0.25
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    chargeTypeP.add(directR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    chargeTypeP.add(indirectR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 110, 5, 5), 0, 0));
    chargeTypeP.add(itemLpp, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 200, 5, 5), 0, 0));
    chargeTypeP.add(generationL, new GridBagConstraints2(0, 0, 1, 1, 0.5, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 350, 5, 5), 0, 0));
    chargeTypeP.add(generationPtC, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 440, 5, 5), 0, 0));

    centerP.add(chargeP, new GridBagConstraints2(0, 2, 1, 1, 0.5, 0.5
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    /*
    chargeP.add(itemLpp, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 200, 5, 5), 0, 0));
    chargeP.add(directR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    chargeP.add(indirectR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 110, 5, 5), 0, 0));
    */
    chargeP.add(partJSP, new GridBagConstraints2(0, 1, 1, 1, 0.5, 0.25
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 4, 0, 6), 0, 0));
    jPanel1.add(poL);
    poC.setMaximumSize(new Dimension(200, 22));
    poC.setMinimumSize(new Dimension(200, 22));
    poC.setPreferredSize(new Dimension(200, 22));
    jPanel1.add(poC);
    //Vector tempV = new Vector(1);
    //tempV.addElement("Not Assigned");
    //poC = ClientHelpObjs.loadChoiceFromVector(poC,tempV);
    chargeP.add(jPanel1, new GridBagConstraints2(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
  }

  void generationPtC_actionPerformed(ActionEvent e) {
    buildChargeDetail(false);
  }

  void loadItAction() {
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_REQUESTED_WASTE_MATERIAL"); //String, String
      query.put("ORDER_NO",new Integer(this.orderNo));  //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      detailJSP.getViewport().remove(newWasteMaterialTable);

      Hashtable infoH = (Hashtable)result.get("REQUESTED_WASTE_MATERIAL_INFO");
      chargeNumbers = (Hashtable)infoH.get("CHARGE_NUMBERS");
      headerInfo = (Hashtable)infoH.get("HEADER_INFO");
      actSysIDV = (Vector)headerInfo.get("ACT_SYS_ID");
      generationPts = (Hashtable)result.get("GENERATION_PTS");
      Vector genDesc = (Vector)generationPts.get("LOCATION_DESC");
      if (genDesc.size() > 1) {
        genDesc.insertElementAt("Select One",0);
        Vector genID = (Vector)generationPts.get("WASTE_LOCATION_ID");
        genID.insertElementAt("Select One",0);
      }
      generationPtC = ClientHelpObjs.loadChoiceFromVector(generationPtC,genDesc);
      wgi = new Waste_Generate_itemAdapter(this);
      generationPtC.addActionListener(wgi);

      DbTableModel ctm = buildTableModel(infoH);
      newWasteMaterialTable = new CmisTable(ctm);
      newWasteMaterialTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
      tableStyle = (Hashtable)result.get("TABLE_STYLE");
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
      newWasteMaterialTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = newWasteMaterialTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }

      WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
      WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();
      newWasteMaterialTable.setDefaultRenderer(String.class, colorTableRenderer);
      newWasteMaterialTable.setDefaultRenderer(Integer.class, colorTableRenderer);
      newWasteMaterialTable.setDefaultRenderer(Double.class, colorTableRenderer);
      newWasteMaterialTable.setDefaultRenderer(Float.class, colorTableRenderer);
      newWasteMaterialTable.setDefaultRenderer(Boolean.class, checkTableRenderer);
      newWasteMaterialTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      newWasteMaterialTable.setCellSelectionEnabled(false);
      newWasteMaterialTable.getTableHeader().setReorderingAllowed(true);
      newWasteMaterialTable.setColTypeFlag(ctm.getColumnTypesString());

      JCheckBox check = new JCheckBox();
      check.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          check_actionPerformed(e);
        }
      });
      check.setHorizontalAlignment(JLabel.CENTER);
      newWasteMaterialTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));

      // set column widths
      for(int i=0;i<newWasteMaterialTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        newWasteMaterialTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        newWasteMaterialTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          newWasteMaterialTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          newWasteMaterialTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }


      //column
      Hashtable keyCols = (Hashtable)infoH.get("KEY_COLS");
      DELETE_COL = ((Integer)keyCols.get("Delete")).intValue();
      ITEM_COL = ((Integer)keyCols.get("Item ID")).intValue();
      PART_NO_COL = ((Integer)keyCols.get("Vendor Part No")).intValue();
      QTY_COL = ((Integer)keyCols.get("Qty")).intValue();
      DESC_COL = ((Integer)keyCols.get("Description")).intValue();
      PACKAGING_COL = ((Integer)keyCols.get("Packaging")).intValue();
      DOT_COL = ((Integer)keyCols.get("Specification")).intValue();
      LPP_COL = ((Integer)keyCols.get("Price")).intValue();
      ACT_COL = ((Integer)keyCols.get("Account Sys")).intValue();

      ctm.setEditableFlag(BothHelpObjs.mypow(2,QTY_COL) + BothHelpObjs.mypow(2,DELETE_COL));
      if (newWasteMaterialTable.getRowCount() > 0) {
        actSysT.setText((String)newWasteMaterialTable.getModel().getValueAt(0,ACT_COL));
        setExtendedPrice();
        facIdL.setText(facilityId);
        storageT.setText((String)headerInfo.get("WASTE_LOCATION_ID"));
        setSelectedRow(0);
        actSysT.setText((String)actSysIDV.firstElement());
        selectedLine = new Integer(0);
        setItemPrice();
        saveDefaultData();
        buildChargeDetail(true);  //11-01-01
      }else {
        facIdL.setText(facilityId);
        storageT.setText((String)headerInfo.get("WASTE_LOCATION_ID"));
        actSysT.setText("");
        extendT.setText("");
      }

      detailJSP.getViewport().setView(newWasteMaterialTable);
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }

    newWasteMaterialTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        listSelectionChanged();  //11-01-01
      }
    });
    newWasteMaterialTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseClicked(MouseEvent e) {
        tableClicked(e);
      }
    });

    ccardB.setEnabled(false);
    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
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


  void check_actionPerformed(ActionEvent e) {
    //newWasteMaterialTable.getModel().setValueAt(new Boolean(true),newWasteMaterialTable.getSelectedRow(),DELETE_COL);
    newWasteMaterialTable.repaint();
  }

  public String getWasteLocationId() {
    String result = "";
    int pos = generationPtC.getSelectedIndex();
    Vector wasteLocationIdV = (Vector)generationPts.get("WASTE_LOCATION_ID");
    result = (String)wasteLocationIdV.elementAt(pos);
    return result;
  }

  public String getWasteLocationDesc(String s) {
    String result = "";
    s = BothHelpObjs.makeBlankFromNull(s);
    Vector wasteLocationIdV = (Vector)generationPts.get("WASTE_LOCATION_ID");
    int pos = 0;
    for (int i = 0; i < wasteLocationIdV.size(); i++) {
      if (s.equals((String)wasteLocationIdV.elementAt(i))) {
        pos = i;
        break;
      }
    }
    Vector genDesc = (Vector)generationPts.get("LOCATION_DESC");
    result = (String)genDesc.elementAt(pos);

    return result;
  }

  void saveCurrentDetail(String currentChargeType, String currentLocationID) {
    String currentItemId = BothHelpObjs.makeBlankFromNull(newWasteMaterialTable.getModel().getValueAt(selectedLine.intValue(),ITEM_COL).toString());
    Hashtable currentChargeInfoH = new Hashtable(5);

    //11-05-01 determine whether to show charge numbers or credit card info
    String currentActSysId = actSysT.getText();
    Hashtable myh = (Hashtable)headerInfo.get("ACC_TYPE_H"+currentActSysId);
    String key1 = currentActSysId + currentChargeType;
    Hashtable packH = (Hashtable)headerInfo.get("PACK"+currentActSysId);
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String needPo = (String)accSysTypeH.get("PO_REQUIRED");
    String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if (chargeNumbers.containsKey(currentItemId)) {
      chargeNumbers.remove(currentItemId);
    }

    Vector v = new Vector();
    if ( prRequired.equalsIgnoreCase("y")) {
      for (int i = 0; i < chargeNumTable.getRowCount(); i++) {
        Hashtable h = new Hashtable();
        int col = chargeNumTable.getColumnCount();
        if (col == 3) {
          if (BothHelpObjs.isBlankString((String)chargeNumTable.getModel().getValueAt(i,2))) continue;
          h.put("ACT_NUM_1",BothHelpObjs.makeBlankFromNull((String)chargeNumTable.getModel().getValueAt(i,0)));
          h.put("ACT_NUM_2",BothHelpObjs.makeBlankFromNull((String)chargeNumTable.getModel().getValueAt(i,1)));
          h.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull((String)chargeNumTable.getModel().getValueAt(i,2)));
        }else {
          //skip if user haven't fill in the percentage for charge number
          if (BothHelpObjs.isBlankString((String)chargeNumTable.getModel().getValueAt(i,1))) continue;
          h.put("ACT_NUM_1",BothHelpObjs.makeBlankFromNull((String)chargeNumTable.getModel().getValueAt(i,0)));
          h.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull((String)chargeNumTable.getModel().getValueAt(i,1)));
        }
        v.addElement(h);
      }
      currentChargeInfoH.put("CHARGE_NUMBER",v);
      currentChargeInfoH.put("DEFAULT_PO","");
      currentChargeInfoH.put("CHARGE_TYPE",currentChargeType);
      currentChargeInfoH.put("ACCUMULATION_PT",currentLocationID);
      currentChargeInfoH.put("DIRECTED_CHARGE",new Boolean(false));
    }

    if ("c".equalsIgnoreCase(needPo)) {
      currentChargeInfoH.put("CHARGE_NUMBER",v);
      currentChargeInfoH.put("DEFAULT_PO","");
      currentChargeInfoH.put("CHARGE_TYPE",currentChargeType);
      currentChargeInfoH.put("ACCUMULATION_PT",currentLocationID);
      currentChargeInfoH.put("DIRECTED_CHARGE",new Boolean(false));
    }else if ("p".equalsIgnoreCase(needPo)) {
      currentChargeInfoH.put("CHARGE_NUMBER",v);
      currentChargeInfoH.put("DEFAULT_PO",(String)poC.getSelectedItem());
      currentChargeInfoH.put("CHARGE_TYPE",currentChargeType);
      currentChargeInfoH.put("ACCUMULATION_PT",currentLocationID);
      currentChargeInfoH.put("DIRECTED_CHARGE",new Boolean(false));
    }

    chargeNumbers.put(currentItemId,currentChargeInfoH);
  }   //end of method

  void listSelectionChanged() {
    if (!deletingData) {
      if (newWasteMaterialTable.isEditing()) {
        newWasteMaterialTable.getCellEditor().stopCellEditing();
      }
      int col = newWasteMaterialTable.getSelectedColumn();
      int row = newWasteMaterialTable.getSelectedRow();
      int clickedLineNum = newWasteMaterialTable.getSelectedRow();
      if(selectedLine.intValue() == clickedLineNum ){
        System.out.println("------- list selection changed same row do nothing");
      }else{
        if(chargeNumTable.isEditing()){
          chargeNumTable.getCellEditor().stopCellEditing();
        }
        setSelectedRow(row);
        saveCurrentDetail(lastChargeType,lastAccumulationPt);
        selectedLine = new Integer(clickedLineNum);
        buildChargeDetail(true);
        setItemPrice();
        setExtendedPrice();
      }
    }
  }

  void tableClicked(MouseEvent e) {
    /*
    int col = ((JTable)e.getSource()).columnAtPoint(new Point(e.getX(),e.getY()));
    int row = ((JTable)e.getSource()).rowAtPoint(new Point(e.getX(),e.getY()));
    if(col == this.QTY_COL && e.getClickCount() > 1){
      setExtendedPrice();
    } */
  }

  void setSelectedRow(int x){
    ListSelectionModel lsm = newWasteMaterialTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    newWasteMaterialTable.scrollRectToVisible(newWasteMaterialTable.getCellRect(x,0,false));
    setItemPrice();
  }


  void setExtendedPrice() {
    double f = 0.00;
    for(int i=0;i<newWasteMaterialTable.getRowCount();i++){
      try{
        int qty = Integer.parseInt(newWasteMaterialTable.getModel().getValueAt(i,QTY_COL).toString());
        String temp = BothHelpObjs.makeBlankFromNull(newWasteMaterialTable.getModel().getValueAt(i,LPP_COL).toString());
        Float lpp;
        if (BothHelpObjs.isBlankString(temp)) {
          lpp = new Float(0.0);
        }else {
          lpp = new Float(temp);
        }

        float price = lpp.floatValue() * qty;
        f = f + price;
      }catch(Exception e){e.printStackTrace();}
    }
    extendT.setText(formatPrice(f));
  }
  void setItemPrice() {
    String cp = "";
    double f = 0.00;
    int rowNum = newWasteMaterialTable.getSelectedRow();
    try{
      int qty = Integer.parseInt(newWasteMaterialTable.getModel().getValueAt(rowNum,QTY_COL).toString());
      String temp = BothHelpObjs.makeBlankFromNull(newWasteMaterialTable.getModel().getValueAt(rowNum,LPP_COL).toString());
      Float lpp;
      if (BothHelpObjs.isBlankString(temp)) {
        lpp = new Float(0.0);
      }else {
        lpp = new Float(temp);
      }

      float price = lpp.floatValue() * qty;
      f = f + price;
      cp += qty + " @ "+lpp+"/Unit = ";
    }catch(Exception e){e.printStackTrace();}
    itemLpp.setText(cp + formatPrice(f));
  }

  String formatPrice(double d){
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    nf.setMinimumIntegerDigits(1);
    return nf.format(d).toString();
  }

  void saveDefaultData() {
    String currentActSysId = actSysT.getText();
    Hashtable h = (Hashtable)headerInfo.get("ACT_TYPE_H"+currentActSysId);
    Hashtable chargeInfoH = (Hashtable)headerInfo.get("CHARGE_NUMBER"+currentActSysId);

    String selectedGenPt = (String)headerInfo.get("WASTE_LOCATION_ID");
    String selectedChargeType = (String)chargeInfoH.get("CHARGE_TYPE");
    Vector v = new Vector(0);
    //if generation point uses directed charge then it will override everything else
    Hashtable directedChargeInfoHd = new Hashtable(0);
    Hashtable directedChargeInfoHi = new Hashtable(0);
    if (chargeInfoH.containsKey("DIRECTED_CHARGE_NUMBERd")) {
      directedChargeInfoHd = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
    }
    if (chargeInfoH.containsKey("DIRECTED_CHARGE_NUMBERi")) {
      directedChargeInfoHi = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
    }
    if (directedChargeInfoHd.containsKey(selectedGenPt)) {
      selectedChargeType = "d";
    }else if (directedChargeInfoHi.containsKey(selectedGenPt)) {
      selectedChargeType = "i";
    }
    //figure out if charge type require account and/or po
    Hashtable packH = (Hashtable)headerInfo.get("PACK"+currentActSysId);
    Hashtable accSysTypeH = (Hashtable)packH.get(currentActSysId + selectedChargeType);
    String needPo = (String)accSysTypeH.get("PO_REQUIRED");
    String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    //if require account number
    if ("y".equalsIgnoreCase(prRequired)) {
      //getting default charge numbers
      if (selectedChargeType.equalsIgnoreCase("d")) {
        if (directedChargeInfoHd.containsKey(selectedGenPt)) {
          v = (Vector)directedChargeInfoHd.get(selectedGenPt);
        }
      }else {
        //if indirect is using directed charge
        if (directedChargeInfoHi.containsKey(selectedGenPt)) {
          v = (Vector)directedChargeInfoHi.get(selectedGenPt);
        }
      }
    }
    //check to see whether account sys uses PO
    String defaultPo = "";
    if ("p".equalsIgnoreCase(needPo)) {
      Vector tmpPo = (Vector)headerInfo.get(currentActSysId+"PO");
      defaultPo = (String)tmpPo.firstElement();
    }

    //save info to charge number for each item that not already in charge number
    for (int i = 0; i < newWasteMaterialTable.getRowCount(); i++) {
      String currentItemId = BothHelpObjs.makeBlankFromNull(newWasteMaterialTable.getModel().getValueAt(i,ITEM_COL).toString());
      if (!chargeNumbers.containsKey(currentItemId)) {
        Hashtable hhh = new Hashtable(5);
        hhh.put("CHARGE_NUMBER",v);
        hhh.put("DEFAULT_PO",defaultPo);
        hhh.put("CHARGE_TYPE",selectedChargeType);
        hhh.put("ACCUMULATION_PT",selectedGenPt);
        hhh.put("DIRECTED_CHARGE",new Boolean(v.size() > 0));
        chargeNumbers.put(currentItemId,hhh);
      }
    } //end of for loop
  } //end of method

  void buildChargeDetail(boolean fromLineSelChanged) {
    generationPtC.removeActionListener(wgi);
    String currentActSysId = actSysT.getText();
    Hashtable h = (Hashtable)headerInfo.get("ACT_TYPE_H"+currentActSysId);
    Hashtable innerH = (Hashtable)h.get(currentActSysId);
    Hashtable chargeInfoH = (Hashtable)headerInfo.get("CHARGE_NUMBER"+currentActSysId);
    String numChargeType = BothHelpObjs.makeBlankFromNull((String)innerH.get("NUM_CHARGE_TYPES"));
    if (numChargeType.equalsIgnoreCase("2")) {
      directR.setVisible(true);
      indirectR.setVisible(true);
    }else {
      directR.setVisible(false);
      indirectR.setVisible(false);
    }
    boolean useDirectedCharge = false;
    String selectedGenPt = "";
    if (lastAccumulationPt.length() < 1) {
      selectedGenPt = (String)headerInfo.get("WASTE_LOCATION_ID");
    }else {
      selectedGenPt = getWasteLocationId();
    }
    String selectedChargeType = (String)chargeInfoH.get("CHARGE_TYPE");
    //if this is not the firsttime then get charge type from user's selection
    if (lastChargeType.length() > 0) {
      if (directR.isVisible()) {
        if (directR.isSelected()) {
          selectedChargeType = "d";
        }else {
          selectedChargeType = "i";
        }
      }
    }

    //if generation point uses directed charge then it will override everything else
    Hashtable directedChargeInfoHd = new Hashtable(0);
    Hashtable directedChargeInfoHi = new Hashtable(0);
    if (chargeInfoH.containsKey("DIRECTED_CHARGE_NUMBERd")) {
      directedChargeInfoHd = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
    }
    if (chargeInfoH.containsKey("DIRECTED_CHARGE_NUMBERi")) {
      directedChargeInfoHi = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
    }
    if (directedChargeInfoHd.containsKey(selectedGenPt)) {
      selectedChargeType = "d";
      //useDirectedCharge = true;
    }else if (directedChargeInfoHi.containsKey(selectedGenPt)) {
      selectedChargeType = "i";
      //useDirectedCharge = true;
    }


    //determine where to load the charge numbers
    Vector v = null;
    String defaultPo = "";
    int row = newWasteMaterialTable.getSelectedRow();
    String currentItemId = BothHelpObjs.makeBlankFromNull(newWasteMaterialTable.getModel().getValueAt(row,ITEM_COL).toString());
    if (chargeNumbers.containsKey(currentItemId)) {
      Hashtable currentChargeNumInfoH = (Hashtable)chargeNumbers.get(currentItemId);
      v = (Vector)currentChargeNumInfoH.get("CHARGE_NUMBER");
      defaultPo = (String)currentChargeNumInfoH.get("DEFAULT_PO");
      //if user entered in one charge type and later decided to charge it
      if (v.size() > 0) {
        String userSelectedChargeType = (String)currentChargeNumInfoH.get("CHARGE_TYPE");
        //String userSelectedGenPt = getWasteLocationDesc((String)currentChargeNumInfoH.get("ACCUMULATION_PT"));
        String userSelectedGenPt = (String)currentChargeNumInfoH.get("ACCUMULATION_PT");
        if (fromLineSelChanged) {
          selectedChargeType = userSelectedChargeType;
          selectedGenPt = userSelectedGenPt;
          generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set accumulation point to user selected accumulation point
          if (directedChargeInfoHd.containsKey(selectedGenPt)) {
            useDirectedCharge = true;
          }else if (directedChargeInfoHi.containsKey(selectedGenPt)) {
            useDirectedCharge = true;
          }else {
            useDirectedCharge = false;
          }
          //if not directed charge then get the rest of the charge number
          if (!useDirectedCharge) {
            Vector tmpV = new Vector(0);
            if (selectedChargeType.equalsIgnoreCase("d")) {
              tmpV = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
            }else {
              tmpV = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
            }
            for (int i = 0; i < tmpV.size(); i++) {
              Hashtable tmpH = (Hashtable)tmpV.elementAt(i);
              v.addElement(tmpH);
            }
            tmpV = null;
          }
        }else {
          if (selectedChargeType.equalsIgnoreCase(userSelectedChargeType) && selectedGenPt.equalsIgnoreCase(userSelectedGenPt)) {
            selectedChargeType = userSelectedChargeType;
            selectedGenPt = userSelectedGenPt;
            generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set accumulation point to user selected accumulation point
            if (directedChargeInfoHd.containsKey(selectedGenPt)) {
              useDirectedCharge = true;
            }else if (directedChargeInfoHi.containsKey(selectedGenPt)) {
              useDirectedCharge = true;
            }else {
              useDirectedCharge = false;
            }
            //if not directed charge then get the rest of the charge number
            if (!useDirectedCharge) {
              Vector tmpV = new Vector(0);
              if (selectedChargeType.equalsIgnoreCase("d")) {
                tmpV = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
              }else {
                tmpV = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
              }
              for (int i = 0; i < tmpV.size(); i++) {
                Hashtable tmpH = (Hashtable)tmpV.elementAt(i);
                v.addElement(tmpH);
              }
              tmpV = null;
            }
          }else {
            //if direct is using directed charge
            if (selectedChargeType.equalsIgnoreCase("d")) {
              //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
              if (directedChargeInfoHd.containsKey(selectedGenPt)) {
                v = (Vector)directedChargeInfoHd.get(selectedGenPt);
                useDirectedCharge = true;
              }else {
                v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
              }
              selectedChargeType = "d";
            }else {
              //if indirect is using directed charge
              //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
              if (directedChargeInfoHi.containsKey(selectedGenPt)) {
                v = (Vector)directedChargeInfoHi.get(selectedGenPt);
                useDirectedCharge = true;
              }else {
                v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
              }
              selectedChargeType = "i";
            }
            generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set default accumulation point to storage area
          }
        } //end of not from line selection changed
      }else {
        //using directed charge
        //if direct is using directed charge
        if (selectedChargeType.equalsIgnoreCase("d")) {
          //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
          if (directedChargeInfoHd.containsKey(selectedGenPt)) {
            v = (Vector)directedChargeInfoHd.get(selectedGenPt);
            useDirectedCharge = true;
          }else {
            v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
          }
          selectedChargeType = "d";
        }else {
          //if indirect is using directed charge
          //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
          if (directedChargeInfoHi.containsKey(selectedGenPt)) {
            v = (Vector)directedChargeInfoHi.get(selectedGenPt);
            useDirectedCharge = true;
          }else {
            v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
          }
          selectedChargeType = "i";
        }
        generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set default accumulation point to storage area
      } //end of first time user doesn't have to a chance to enter any data yet
    }else {   //else first time
      //using directed charge
      //if direct is using directed charge
      if (selectedChargeType.equalsIgnoreCase("d")) {
        //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
        if (directedChargeInfoHd.containsKey(selectedGenPt)) {
          v = (Vector)directedChargeInfoHd.get(selectedGenPt);
          useDirectedCharge = true;
        }else {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
        }
        selectedChargeType = "d";
      }else { //if indirect is using directed charge
        //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
        if (directedChargeInfoHi.containsKey(selectedGenPt)) {
          v = (Vector)directedChargeInfoHi.get(selectedGenPt);
          useDirectedCharge = true;
        }else {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
        }
        selectedChargeType = "i";
      }
      generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set default accumulation point to storage area
    }
    if (selectedChargeType.equalsIgnoreCase("d")) {
      directR.setSelected(true);
      indirectR.setSelected(false);
      lastChargeType = "d";
    }else {
      indirectR.setSelected(true);
      directR.setSelected(false);
      lastChargeType = "i";
    }
    //disable charge type if generation point uses directed charge
    directR.setEnabled(!useDirectedCharge);
    indirectR.setEnabled(!useDirectedCharge);
    lastAccumulationPt = selectedGenPt;

    //11-05-01 determine whether to show charge numbers or credit card info
    Hashtable myh = (Hashtable)headerInfo.get("ACC_TYPE_H"+currentActSysId);
    String key1 = currentActSysId + selectedChargeType;
    Hashtable packH = (Hashtable)headerInfo.get("PACK"+currentActSysId);
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String needPo = (String)accSysTypeH.get("PO_REQUIRED");
    String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if (prRequired.equalsIgnoreCase("y")) {
      chargeP.setVisible(true);
      buildChargeNumber(v,selectedChargeType);
    }else {
      chargeP.setVisible(false);
    }

    if ("c".equalsIgnoreCase(needPo)) {
      ccardB.setEnabled(true);
      poC.setVisible(false);
      poL.setVisible(false);
    }else if ("p".equalsIgnoreCase(needPo)) {
      ccardB.setEnabled(false);
      poL.setVisible(true);
      poC.removeAllItems();
      poC.setVisible(true);
      poC = ClientHelpObjs.loadChoiceFromVector(poC,(Vector)headerInfo.get(currentActSysId+"PO"));
      poC.setSelectedItem(defaultPo);
    }else {
      ccardB.setEnabled(false);
      poC.setVisible(false);
      poL.setVisible(false);
    }
    generationPtC.addActionListener(wgi);
  }   //end of method

  void buildChargeDetail32603(boolean fromLineSelChanged) {
    generationPtC.removeActionListener(wgi);
    String currentActSysId = actSysT.getText();
    Hashtable h = (Hashtable)headerInfo.get("ACT_TYPE_H"+currentActSysId);
    Hashtable innerH = (Hashtable)h.get(currentActSysId);
    Hashtable chargeInfoH = (Hashtable)headerInfo.get("CHARGE_NUMBER"+currentActSysId);
    String numChargeType = BothHelpObjs.makeBlankFromNull((String)innerH.get("NUM_CHARGE_TYPES"));
    if (numChargeType.equalsIgnoreCase("2")) {
      directR.setVisible(true);
      indirectR.setVisible(true);
    }else {
      directR.setVisible(false);
      indirectR.setVisible(false);
    }

    boolean useDirectedCharge = false;
    String selectedGenPt = "";
    String selectedChargeType = "";
    //determine where to load the charge numbers
    Vector v = null;
    int row = newWasteMaterialTable.getSelectedRow();
    String currentItemId = BothHelpObjs.makeBlankFromNull(newWasteMaterialTable.getModel().getValueAt(row,ITEM_COL).toString());
    if (chargeNumbers.containsKey(currentItemId)) {
      Hashtable currentChargeInfoH = (Hashtable)chargeNumbers.get(currentItemId);
      useDirectedCharge = ((Boolean)currentChargeInfoH.get("DIRECTED_CHARGE")).booleanValue();
      selectedGenPt = (String)currentChargeInfoH.get("ACCUMULATION_PT");
      selectedChargeType = (String)currentChargeInfoH.get("CHARGE_TYPE");
      v = (Vector)currentChargeInfoH.get("CHARGE_NUMBER");
      if (v.size() < 0) {
        if (selectedChargeType.equalsIgnoreCase("d")) {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
          selectedChargeType = "d";
        }else {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
          selectedChargeType = "i";
        }
      }else {
        System.out.println("----------CONTAIN CHARGE NUMBERS Append charge number if not directed!!!!!!!");
      }
      generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));
    }else {   //else first time
      System.out.println("---------- ITEM NOT IN CHARGE NUMBER NOT SUPPOSE TO BE HERE!!!!!!!");
    }

    if (selectedChargeType.equalsIgnoreCase("d")) {
      directR.setSelected(true);
      indirectR.setSelected(false);
      lastChargeType = "d";
    }else {
      indirectR.setSelected(true);
      directR.setSelected(false);
      lastChargeType = "i";
    }
    //disable charge type if generation point uses directed charge
    directR.setEnabled(!useDirectedCharge);
    indirectR.setEnabled(!useDirectedCharge);
    lastAccumulationPt = selectedGenPt;

    //11-05-01 determine whether to show charge numbers or credit card info
    Hashtable myh = (Hashtable)headerInfo.get("ACC_TYPE_H"+currentActSysId);
    String key1 = currentActSysId + selectedChargeType;
    Hashtable packH = (Hashtable)headerInfo.get("PACK"+currentActSysId);
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String needPo = (String)accSysTypeH.get("PO_REQUIRED");
    String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if (prRequired.equalsIgnoreCase("y")) {
      chargeP.setVisible(true);
      buildChargeNumber(v,selectedChargeType);
    }else {
      chargeP.setVisible(false);
    }

    if ("c".equalsIgnoreCase(needPo)) {
      ccardB.setEnabled(true);
      poC.setVisible(false);
      poL.setVisible(false);
    }else if ("p".equalsIgnoreCase(needPo)) {
      ccardB.setEnabled(false);
      poL.setVisible(true);
      poC.removeAllItems();
      poC.setVisible(true);
      poC = ClientHelpObjs.loadChoiceFromVector(poC,(Vector)headerInfo.get(currentActSysId+"PO"));
      if (chargeNumbers.containsKey(currentItemId+"PO")) {
        poC.setSelectedItem((String)chargeNumbers.get(currentItemId+"PO"));
      }
      //ClientHelpObjs.setEnabledContainer(poC,true);
    }else {
      ccardB.setEnabled(false);
      poC.setVisible(false);
      poL.setVisible(false);
    }
    generationPtC.addActionListener(wgi);
  }   //end of method

  void buildChargeDetail32503(boolean fromLineSelChanged) {
    generationPtC.removeActionListener(wgi);
    String currentActSysId = actSysT.getText();
    Hashtable h = (Hashtable)headerInfo.get("ACT_TYPE_H"+currentActSysId);
    Hashtable innerH = (Hashtable)h.get(currentActSysId);
    Hashtable chargeInfoH = (Hashtable)headerInfo.get("CHARGE_NUMBER"+currentActSysId);
    String numChargeType = BothHelpObjs.makeBlankFromNull((String)innerH.get("NUM_CHARGE_TYPES"));
    if (numChargeType.equalsIgnoreCase("2")) {
      directR.setVisible(true);
      indirectR.setVisible(true);
    }else {
      directR.setVisible(false);
      indirectR.setVisible(false);
    }
    boolean useDirectedCharge = false;
    String selectedGenPt = "";
    if (lastAccumulationPt.length() < 1) {
      selectedGenPt = (String)headerInfo.get("WASTE_LOCATION_ID");
    }else {
      selectedGenPt = getWasteLocationId();
    }
    String selectedChargeType = (String)chargeInfoH.get("CHARGE_TYPE");
    //if this is not the firsttime then get charge type from user's selection
    if (lastChargeType.length() > 0) {
      if (directR.isVisible()) {
        if (directR.isSelected()) {
          selectedChargeType = "d";
        }else {
          selectedChargeType = "i";
        }
      }
    }

    //if generation point uses directed charge then it will override everything else
    Hashtable directedChargeInfoHd = new Hashtable(0);
    Hashtable directedChargeInfoHi = new Hashtable(0);
    if (chargeInfoH.containsKey("DIRECTED_CHARGE_NUMBERd")) {
      directedChargeInfoHd = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
    }
    if (chargeInfoH.containsKey("DIRECTED_CHARGE_NUMBERi")) {
      directedChargeInfoHi = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
    }
    if (directedChargeInfoHd.containsKey(selectedGenPt)) {
      selectedChargeType = "d";
      //useDirectedCharge = true;
    }else if (directedChargeInfoHi.containsKey(selectedGenPt)) {
      selectedChargeType = "i";
      //useDirectedCharge = true;
    }


    //determine where to load the charge numbers
    Vector v = null;
    int row = newWasteMaterialTable.getSelectedRow();
    String currentItemId = BothHelpObjs.makeBlankFromNull(newWasteMaterialTable.getModel().getValueAt(row,ITEM_COL).toString());
    if (chargeNumbers.containsKey(currentItemId)) {
      v = (Vector)chargeNumbers.get(currentItemId);
      //if user entered in one charge type and later decided to charge it
      if (v.size() > 0) {
        Hashtable hh = (Hashtable)v.firstElement();
        String userSelectedChargeType = (String)hh.get("CHARGE_TYPE");
        String userSelectedGenPt = getWasteLocationDesc((String)hh.get("ACCUMULATION_PT"));
        if (fromLineSelChanged) {
          selectedChargeType = userSelectedChargeType;
          selectedGenPt = userSelectedGenPt;
          generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set accumulation point to user selected accumulation point
          if (directedChargeInfoHd.containsKey(selectedGenPt)) {
            useDirectedCharge = true;
          }else if (directedChargeInfoHi.containsKey(selectedGenPt)) {
            useDirectedCharge = true;
          }else {
            useDirectedCharge = false;
          }
        }else {
          if (selectedChargeType.equalsIgnoreCase(userSelectedChargeType) && selectedGenPt.equalsIgnoreCase(userSelectedGenPt)) {
            selectedChargeType = userSelectedChargeType;
            selectedGenPt = userSelectedGenPt;
            generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set accumulation point to user selected accumulation point
            if (directedChargeInfoHd.containsKey(selectedGenPt)) {
              useDirectedCharge = true;
            }else if (directedChargeInfoHi.containsKey(selectedGenPt)) {
              useDirectedCharge = true;
            }else {
              useDirectedCharge = false;
            }
          }else {
            //if direct is using directed charge
            if (selectedChargeType.equalsIgnoreCase("d")) {
              //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
              if (directedChargeInfoHd.containsKey(selectedGenPt)) {
                v = (Vector)directedChargeInfoHd.get(selectedGenPt);
                useDirectedCharge = true;
              }else {
                v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
              }
              selectedChargeType = "d";
            }else {
              //if indirect is using directed charge
              //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
              if (directedChargeInfoHi.containsKey(selectedGenPt)) {
                v = (Vector)directedChargeInfoHi.get(selectedGenPt);
                useDirectedCharge = true;
              }else {
                v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
              }
              selectedChargeType = "i";
            }
            generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set default accumulation point to storage area
          }
        } //end of not from line selection changed
      }else {
        //using directed charge
        //if direct is using directed charge
        if (selectedChargeType.equalsIgnoreCase("d")) {
          //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
          if (directedChargeInfoHd.containsKey(selectedGenPt)) {
            v = (Vector)directedChargeInfoHd.get(selectedGenPt);
            useDirectedCharge = true;
          }else {
            v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
          }
          selectedChargeType = "d";
        }else {
          //if indirect is using directed charge
          //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
          if (directedChargeInfoHi.containsKey(selectedGenPt)) {
            v = (Vector)directedChargeInfoHi.get(selectedGenPt);
            useDirectedCharge = true;
          }else {
            v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
          }
          selectedChargeType = "i";
        }
        generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set default accumulation point to storage area
      } //end of first time user doesn't have to a chance to enter any data yet
    }else {   //else first time
      //using directed charge
      //if direct is using directed charge
      if (selectedChargeType.equalsIgnoreCase("d")) {
        //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
        if (directedChargeInfoHd.containsKey(selectedGenPt)) {
          v = (Vector)directedChargeInfoHd.get(selectedGenPt);
          useDirectedCharge = true;
        }else {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
        }
        selectedChargeType = "d";
      }else { //if indirect is using directed charge
        //Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
        if (directedChargeInfoHi.containsKey(selectedGenPt)) {
          v = (Vector)directedChargeInfoHi.get(selectedGenPt);
          useDirectedCharge = true;
        }else {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
        }
        selectedChargeType = "i";
      }
      generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set default accumulation point to storage area
    }
    if (selectedChargeType.equalsIgnoreCase("d")) {
      directR.setSelected(true);
      indirectR.setSelected(false);
      lastChargeType = "d";
    }else {
      indirectR.setSelected(true);
      directR.setSelected(false);
      lastChargeType = "i";
    }
    //disable charge type if generation point uses directed charge
    directR.setEnabled(!useDirectedCharge);
    indirectR.setEnabled(!useDirectedCharge);
    lastAccumulationPt = selectedGenPt;

    //11-05-01 determine whether to show charge numbers or credit card info
    Hashtable myh = (Hashtable)headerInfo.get("ACC_TYPE_H"+currentActSysId);
    String key1 = currentActSysId + selectedChargeType;
    Hashtable packH = (Hashtable)headerInfo.get("PACK"+currentActSysId);
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String needPo = (String)accSysTypeH.get("PO_REQUIRED");
    String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if (prRequired.equalsIgnoreCase("y")) {
      chargeP.setVisible(true);
      buildChargeNumber(v,selectedChargeType);
    }else {
      chargeP.setVisible(false);
    }

    if ("c".equalsIgnoreCase(needPo)) {
      ccardB.setEnabled(true);
      poC.setVisible(false);
      poL.setVisible(false);
    }else if ("p".equalsIgnoreCase(needPo)) {
      ccardB.setEnabled(false);
      poL.setVisible(true);
      poC.removeAllItems();
      poC.setVisible(true);
      poC = ClientHelpObjs.loadChoiceFromVector(poC,(Vector)headerInfo.get(currentActSysId+"PO"));
      if (chargeNumbers.containsKey(currentItemId+"PO")) {
        poC.setSelectedItem((String)chargeNumbers.get(currentItemId+"PO"));
      }
      //ClientHelpObjs.setEnabledContainer(poC,true);
    }else {
      ccardB.setEnabled(false);
      poC.setVisible(false);
      poL.setVisible(false);
    }
    generationPtC.addActionListener(wgi);
  }   //end of method

  void buildChargeDetailNew() {
    generationPtC.removeActionListener(wgi);
    String currentActSysId = actSysT.getText();
    Hashtable h = (Hashtable)headerInfo.get("ACT_TYPE_H"+currentActSysId);
    Hashtable innerH = (Hashtable)h.get(currentActSysId);
    Hashtable chargeInfoH = (Hashtable)headerInfo.get("CHARGE_NUMBER"+currentActSysId);
    //String directedI = "";
    //String directedD = "";
    String numChargeType = BothHelpObjs.makeBlankFromNull((String)innerH.get("NUM_CHARGE_TYPES"));
    if (numChargeType.equalsIgnoreCase("2")) {
      directR.setVisible(true);
      indirectR.setVisible(true);
      //directedD = (String)chargeInfoH.get("DIRECTED_TYPEd");
      //directedI = (String)chargeInfoH.get("DIRECTED_TYPEi");
    }else {
      directR.setVisible(false);
      indirectR.setVisible(false);
      //directedD = (String)chargeInfoH.get("DIRECTED_TYPEd");
    }
    boolean useDirectedCharge = false;
    String selectedGenPt = "";
    if (lastAccumulationPt.length() < 1) {
      selectedGenPt = (String)headerInfo.get("WASTE_LOCATION_ID");
    }else {
      selectedGenPt = getWasteLocationId();
    }
    String selectedChargeType = (String)chargeInfoH.get("CHARGE_TYPE");
    //if this is not the firsttime then get charge type from user's selection
    if (lastChargeType.length() > 0) {
      if (directR.isVisible()) {
        if (directR.isSelected()) {
          selectedChargeType = "d";
        }else {
          selectedChargeType = "i";
        }
      }
    }

    //if generation point uses directed charge then it will override everything else
    Hashtable directedChargeInfoHd = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
    Hashtable directedChargeInfoHi = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
    if (directedChargeInfoHd.containsKey(selectedGenPt)) {
      selectedChargeType = "d";
      useDirectedCharge = true;
    }else if (directedChargeInfoHi.containsKey(selectedGenPt)) {
      selectedChargeType = "i";
      useDirectedCharge = true;
    }
    directedChargeInfoHd = null;
    directedChargeInfoHi = null;

    //determine where to load the charge numbers
    Vector v = null;
    int row = newWasteMaterialTable.getSelectedRow();
    String currentItemId = BothHelpObjs.makeBlankFromNull(newWasteMaterialTable.getModel().getValueAt(row,ITEM_COL).toString());
    if (chargeNumbers.containsKey(currentItemId)) {
      v = (Vector)chargeNumbers.get(currentItemId);
      /*if user entered a charge number then find which charge type user picked
      boolean containUserData = false;
      if (v.size() > 0) {
        Hashtable hhh = (Hashtable)v.firstElement();
        String userSelectedChargeType = (String)hhh.get("CHARGE_TYPE");
        String userSelectedGenPt = getWasteLocationDesc((String)hhh.get("ACCUMULATION_PT"));
        if (selectedChargeType.equalsIgnoreCase(userSelectedChargeType)) {
          //&& selectedGenPt.equalsIgnoreCase(userSelectedGenPt)) {
          containUserData = true;
        }
      }*/
      //if user entered in one charge type and later decided to charge it
      //if (v.size() > 0 && containUserData) {
      if (v.size() > 0) {
        Hashtable hh = (Hashtable)v.firstElement();
        String userSelectedChargeType = (String)hh.get("CHARGE_TYPE");
        String userSelectedGenPt = getWasteLocationDesc((String)hh.get("ACCUMULATION_PT"));
        if (selectedChargeType.equalsIgnoreCase(userSelectedChargeType) && selectedGenPt.equalsIgnoreCase(userSelectedGenPt)) {
          selectedChargeType = userSelectedChargeType;
          selectedGenPt = userSelectedGenPt;
          generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set accumulation point to user selected accumulation point
        }else {
          //if direct is using directed charge
          if (selectedChargeType.equalsIgnoreCase("d")) {
            Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
            if (directedChargeInfoH.containsKey(selectedGenPt)) {
              v = (Vector)directedChargeInfoH.get(selectedGenPt);
            }else {
              v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
            }
            selectedChargeType = "d";
          }else {
            //if indirect is using directed charge
            Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
            if (directedChargeInfoH.containsKey(selectedGenPt)) {
              v = (Vector)directedChargeInfoH.get(selectedGenPt);
            }else {
              v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
            }
            selectedChargeType = "i";
          }
          generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set default accumulation point to storage area
        }
      }else {
        //using directed charge
        //if direct is using directed charge
        if (selectedChargeType.equalsIgnoreCase("d")) {
          Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
          if (directedChargeInfoH.containsKey(selectedGenPt)) {
            v = (Vector)directedChargeInfoH.get(selectedGenPt);
          }else {
            v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
          }
          selectedChargeType = "d";
        }else {
          //if indirect is using directed charge
          Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
          if (directedChargeInfoH.containsKey(selectedGenPt)) {
            v = (Vector)directedChargeInfoH.get(selectedGenPt);
          }else {
            v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
          }
          selectedChargeType = "i";
        }
        generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set default accumulation point to storage area
      } //end of first time user doesn't have to a chance to enter any data yet
    }else {   //else first time
      //using directed charge
      //if direct is using directed charge
      if (selectedChargeType.equalsIgnoreCase("d")) {
        Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
        if (directedChargeInfoH.containsKey(selectedGenPt)) {
          v = (Vector)directedChargeInfoH.get(selectedGenPt);
        }else {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
        }
        selectedChargeType = "d";
      }else { //if indirect is using directed charge
        Hashtable directedChargeInfoH = (Hashtable)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
        if (directedChargeInfoH.containsKey(selectedGenPt)) {
          v = (Vector)directedChargeInfoH.get(selectedGenPt);
        }else {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
        }
        selectedChargeType = "i";
      }
      generationPtC.setSelectedItem(getWasteLocationDesc(selectedGenPt));   //set default accumulation point to storage area
    }
    if (selectedChargeType.equalsIgnoreCase("d")) {
      directR.setSelected(true);
      indirectR.setSelected(false);
      lastChargeType = "d";
    }else {
      indirectR.setSelected(true);
      directR.setSelected(false);
      lastChargeType = "i";
    }
    //disable charge type if generation point uses directed charge
    /*
    if (useDirectedCharge) {
      directR.setEnabled(false);
      indirectR.setEnabled(false);
    }else {
      directR.setEnabled(true);
      indirectR.setEnabled(true);
    }*/
    directR.setEnabled(!useDirectedCharge);
    indirectR.setEnabled(!useDirectedCharge);
    lastAccumulationPt = selectedGenPt;

    //11-05-01 determine whether to show charge numbers or credit card info
    Hashtable myh = (Hashtable)headerInfo.get("ACC_TYPE_H"+currentActSysId);
    String key1 = currentActSysId + selectedChargeType;
    Hashtable packH = (Hashtable)headerInfo.get("PACK"+currentActSysId);
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String needPo = (String)accSysTypeH.get("PO_REQUIRED");
    String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if (prRequired.equalsIgnoreCase("y")) {
      chargeP.setVisible(true);
      buildChargeNumber(v,selectedChargeType);
    }else {
      chargeP.setVisible(false);
    }

    if (needPo.equalsIgnoreCase("c")) {
      ccardB.setEnabled(true);
    }else {
      ccardB.setEnabled(false);
    }
    generationPtC.addActionListener(wgi);
  }   //end of method

  void buildChargeDetailOld() {
    String currentActSysId = actSysT.getText();
    Hashtable h = (Hashtable)headerInfo.get("ACT_TYPE_H"+currentActSysId);
    Hashtable innerH = (Hashtable)h.get(currentActSysId);
    Hashtable chargeInfoH = (Hashtable)headerInfo.get("CHARGE_NUMBER"+currentActSysId);
    String directedI = "";
    String directedD = "";
    //whether or not to should the radio buttons and charge type
    String numChargeType = BothHelpObjs.makeBlankFromNull((String)innerH.get("NUM_CHARGE_TYPES"));
    if (numChargeType.equalsIgnoreCase("2")) {
      directR.setVisible(true);
      indirectR.setVisible(true);
      directedD = (String)chargeInfoH.get("DIRECTED_TYPEd");
      directedI = (String)chargeInfoH.get("DIRECTED_TYPEi");
    }else {
      directR.setVisible(false);
      indirectR.setVisible(false);
      directedD = (String)chargeInfoH.get("DIRECTED_TYPEd");
    }
    String selectedChargeType = (String)chargeInfoH.get("CHARGE_TYPE");
    if ("B".equalsIgnoreCase(directedD)) {
      directR.setEnabled(false);
    }
    if ("B".equalsIgnoreCase(directedI)) {
      indirectR.setEnabled(false);
    }



    //if this is not the firsttime then get charge type from user's selection
    if (lastChargeType.length() > 0) {
      if (directR.isVisible()) {
        if (directR.isSelected()) {
          selectedChargeType = "d";
        }else {
          selectedChargeType = "i";
        }
      }
    }

    //determine where to load the charge numbers
    Vector v = null;
    int row = newWasteMaterialTable.getSelectedRow();
    String currentItemId = BothHelpObjs.makeBlankFromNull(newWasteMaterialTable.getModel().getValueAt(row,ITEM_COL).toString());
    if (chargeNumbers.containsKey(currentItemId)) {
      v = (Vector)chargeNumbers.get(currentItemId);
      String currentChargeType = "";
      //if user entered in one charge type and later decided to charge it
      if (v.size() <= 0) {
        //using directed charge
        if ("Y".equalsIgnoreCase(directedD) || "Y".equalsIgnoreCase(directedI)) {
          //if direct is using directed charge
          if (selectedChargeType.equalsIgnoreCase("d") && "Y".equalsIgnoreCase(directedD)) {
            v = (Vector)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
            currentChargeType = "d";
          //if indirect is using directed charge
          }else if (selectedChargeType.equalsIgnoreCase("i") && "Y".equalsIgnoreCase(directedI)) {
            v = (Vector)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
            currentChargeType = "i";
          }
        //not using directed charge
        }else {
          if (selectedChargeType.equalsIgnoreCase("d")) {
            v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
            currentChargeType = "d";
          }else {
            v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
            currentChargeType = "i";
          }
        }
        generationPtC.setSelectedItem(getWasteLocationDesc(storageT.getText().toString()));
      }else {
        Hashtable hh = (Hashtable)v.firstElement();
        currentChargeType = BothHelpObjs.makeBlankFromNull((String)hh.get("CHARGE_TYPE"));
        generationPtC.setSelectedItem(getWasteLocationDesc((String)hh.get("ACCUMULATION_PT")));
      }
      selectedChargeType = currentChargeType;
    }else {
      //using directed charge
      if (directedD.equalsIgnoreCase("Y") || "Y".equalsIgnoreCase(directedI)) {
        //if direct is using directed charge
        if (selectedChargeType.equalsIgnoreCase("d") && "Y".equalsIgnoreCase(directedD)) {
          v = (Vector)chargeInfoH.get("DIRECTED_CHARGE_NUMBERd");
          selectedChargeType = "d";
          //if indirect is using directed charge
        }else if (selectedChargeType.equalsIgnoreCase("i") && "Y".equalsIgnoreCase(directedI)) {
          v = (Vector)chargeInfoH.get("DIRECTED_CHARGE_NUMBERi");
          selectedChargeType = "i";
        }
      //not using directed charge
      }else {
        if (selectedChargeType.equalsIgnoreCase("d")) {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_DIRECT");
          selectedChargeType = "d";
        }else {
          v = (Vector)chargeInfoH.get("CHARGE_NUM_INDIRECT");
          selectedChargeType = "i";
        }
      }
      generationPtC.setSelectedItem(getWasteLocationDesc(storageT.getText().toString()));
    }
    if (selectedChargeType.equalsIgnoreCase("d")) {
      directR.setSelected(true);
      indirectR.setSelected(false);
      lastChargeType = "d";
    }else {
      indirectR.setSelected(true);
      directR.setSelected(false);
      lastChargeType = "i";
    }
    lastAccumulationPt = getWasteLocationId();

    //11-05-01 determine whether to show charge numbers or credit card info
    Hashtable myh = (Hashtable)headerInfo.get("ACC_TYPE_H"+currentActSysId);
    String key1 = currentActSysId + selectedChargeType;
    Hashtable packH = (Hashtable)headerInfo.get("PACK"+currentActSysId);
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String needPo = (String)accSysTypeH.get("PO_REQUIRED");
    String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if (prRequired.equalsIgnoreCase("y")) {
      chargeP.setVisible(true);
      buildChargeNumber(v,selectedChargeType);
    }else {
      chargeP.setVisible(false);
    }

    if (needPo.equalsIgnoreCase("c")) {
      ccardB.setEnabled(true);
    }else {
      ccardB.setEnabled(false);
    }
  }   //end of method

  //return a hashtable of item id(s) and line number that the user wants to order
  Hashtable getItemIDsFromTable() {
    Hashtable result = new Hashtable();
    Vector v = new Vector();
    Vector lineV = new Vector();
    for (int i = 0; i < newWasteMaterialTable.getRowCount(); i++) {
      Boolean b = (Boolean)newWasteMaterialTable.getModel().getValueAt(i,DELETE_COL);
      if (!b.booleanValue()) {
        v.addElement(newWasteMaterialTable.getModel().getValueAt(i,ITEM_COL).toString());
        lineV.addElement(new Integer(i));
      }
    }
    result.put("ITEM_IDS",v);
    result.put("LINE_NUMBER",lineV);

    return result;
  }

  void buildChargeNumber(Vector chargeNums, String chargeType) {
    String currentActSysId = actSysT.getText();
    String[] chargeCols = new String[]{};
    Vector chargeV = new Vector();
    Vector chargeV2 = new Vector();
    Vector percentV = new Vector();
    Vector colHeader = new Vector();

    Hashtable actTypeH = (Hashtable)headerInfo.get("ACT_TYPE_H"+currentActSysId);
    Hashtable actInfoH = (Hashtable)actTypeH.get(currentActSysId);
    Hashtable packH = (Hashtable)headerInfo.get("PACK"+currentActSysId);
    Hashtable innerH = (Hashtable)packH.get(currentActSysId+chargeType);
    colHeader = (Vector)innerH.get("LABELS");
    Vector chargeTypeV = (Vector)actInfoH.get("CHARGE_TYPE");
    int pos = chargeTypeV.indexOf(chargeType);
    Vector display1V = (Vector)actInfoH.get("DISPLAY_1");
    Vector display2V = (Vector)actInfoH.get("DISPLAY_2");

    String display1 = (String)display1V.elementAt(pos);
    String display2 = (String)display2V.elementAt(pos);
    Vector tmpChargeNum = new Vector(5);
    for (int i = 0; i < chargeNums.size(); i++) {
      Hashtable hh = (Hashtable)chargeNums.elementAt(i);
      String tmpCharge1 = BothHelpObjs.makeBlankFromNull((String)hh.get("ACT_NUM_1"));
      String tmpCharge2 = BothHelpObjs.makeBlankFromNull((String)hh.get("ACT_NUM_2"));
      String tmpPer = BothHelpObjs.makeBlankFromNull((String)hh.get("PERCENTAGE"));
      //the logic below is to prevent me from displaying a charge numbers more than one time
      if (tmpPer.length() > 0) {
        String tmpVal = "";
        if (tmpCharge1.length() > 0) {
          tmpVal += tmpCharge1;
        }
        if (tmpCharge2.length() > 0) {
          tmpVal += tmpCharge2;
        }
        if (tmpVal.length() > 0) {
          if (!tmpChargeNum.contains(tmpVal)) {
            tmpChargeNum.addElement(tmpVal);
          }
        }
      }else {
        String tmpVal = "";
        if (tmpCharge1.length() > 0) {
          tmpVal += tmpCharge1;
        }
        if (tmpCharge2.length() > 0) {
          tmpVal += tmpCharge2;
        }
        if (tmpVal.length() > 0) {
          if (tmpChargeNum.contains(tmpVal)) {
            continue;
          }
        }
      }

      chargeV.addElement(tmpCharge1);
      chargeV2.addElement(tmpCharge2);
      percentV.addElement(tmpPer);
    }
    chargeCols = new String[colHeader.size()];
    for (int i = 0; i < colHeader.size(); i++) {
      chargeCols[i] = colHeader.elementAt(i).toString();
    }

    int numRows = 0;
    if (!display1.equalsIgnoreCase("y") && !display2.equalsIgnoreCase("y")) {
      numRows = 35;
    }else {
      if (chargeV.size() > 0 ) {
        numRows = chargeV.size();
      }else {
        numRows = chargeV2.size();
      }
    }

    //setting all columns to empty string
    Object[][] oa = new Object[numRows][colHeader.size()];
    for (int j = 0; j < oa.length; j++) {
      for (int k = 0; k < chargeCols.length; k++){
        oa[j][k] = "";
      }
    }

    for (int i = 0; i < chargeV.size(); i++) {
      int r = 0;
      //display charge number from user input
      if (display1.equalsIgnoreCase("y") || !BothHelpObjs.isBlankString((String)chargeV.elementAt(i))) {
        oa[i][r++] = chargeV.elementAt(i).toString();
      }else {
        oa[i][r++] = "";
      }
      if (colHeader.size() == 3) {
        //display charge number from user input
        if (display2.equalsIgnoreCase("y") || !BothHelpObjs.isBlankString((String)chargeV.elementAt(i))) {
          oa[i][r++] = chargeV2.elementAt(i).toString();
        }else {
          oa[i][r++] = "";
        }
      }
      //set percent to 100 if only one charge number is use
      if (chargeV.size() == 1) {
        oa[i][r++] = "100";
      }else {
        oa[i][r++] = percentV.elementAt(i).toString();              //percentage
      }
    }

    int[] colWidths = new int[]{0};
    if (colHeader.size() == 3) {
      colWidths = new int[]{71,71,48};
    }else {
      colWidths = new int[]{142,48};
    }

    partJSP.getViewport().remove(chargeNumTable);
    //CmisTableModel ctm;
    //ctm = new CmisTableModel(chargeCols,oa);
    DbTableModel ctm = new DbTableModel(chargeCols,oa);
    if (numRows == 35) {
      if (chargeCols.length == 2) ctm.setEditableFlag(3);
      if (chargeCols.length == 3) ctm.setEditableFlag(7);
    }else {
      if (chargeCols.length == 2) ctm.setEditableFlag(2);
      if (chargeCols.length == 3) ctm.setEditableFlag(4);
    }

    TableSorter sorter = new TableSorter(ctm);
    chargeNumTable = new CmisTable(sorter);
    sorter.setColTypeFlag(ctm.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(chargeNumTable);
    chargeNumTable.setCellSelectionEnabled(true);

    partJSP.getViewport().setView(chargeNumTable);
    for (int i = 0; i < chargeNumTable.getColumnCount(); i++) {
      chargeNumTable.getColumn(chargeNumTable.getColumnName(i)).setPreferredWidth(colWidths[i]);
    }

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
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
    chargeNumTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = chargeNumTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }


    chargeNumTable.validate();
    partJSP.validate();
    chargeP.validate();

  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    if (newWasteMaterialTable.getRowCount() < 1) {
      this.setVisible(false);
      return;
    }
    //11-01-01
    if(chargeNumTable.isEditing()){
      chargeNumTable.getCellEditor().stopCellEditing();
    }
    saveCurrentDetail(lastChargeType,lastAccumulationPt);

    Hashtable h = getLineItemInfo();  //save info to compare if user still have waste material on order

    if (!passAudit()) {     //11-01-01
      CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
      return;
    }else {
      if (updateRequest()) {
        if (listChanged.booleanValue() || rowDeleted.booleanValue()) {
          if (BothHelpObjs.isBlankString(wasteOrder.containerShippedOn)) {
            wasteOrder.submitB.setEnabled(true);
          }
        }
        Vector myItems = (Vector)h.get("ITEM_IDS");
        Vector myDeleteItems = (Vector)h.get("DELETE_ITEM_IDS");
        if (myItems.size() < 1 && myDeleteItems.size() == newWasteMaterialTable.getRowCount()) {
          wasteOrder.addMaterialB.setText("Add Waste Material");
        }else {
          wasteOrder.addMaterialB.setText("Edit Waste Material");
        }
        setVisible(false);
      }else {
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        return;
      }
    }
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  boolean deleteItem(Vector deleteItemV) {
    boolean val = false;
    try {
      CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","DELETE_WASTE_MATERIAL_ITEM"); //String, String
      query.put("ORDER_NO",new Integer(this.orderNo));  //String, Integer
      query.put("ITEM_IDS",deleteItemV);                //String, Vector
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
      }
      Boolean b = (Boolean)result.get("RETURN_CODE");
      if(!b.booleanValue()){
        val = false;
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg(result.get("MSG").toString());
        gd.setVisible(true);
      }else{
        val = true;
        GenericDlg rd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
        rd.setMsg(result.get("MSG").toString());
        rd.setVisible(true);
        rowDeleted = new Boolean(true);
      }
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
    }
    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
    return val;
  }

  boolean updateRequest() {
    boolean val = true;
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","CREATE_WASTE_MATERIAL_REQUEST"); //String, String
      query.put("FACILITY_ID",facilityId);          //String, String
      query.put("ACT_SYS_ID",actSysT.getText());    //String, String
      query.put("USER_ID",cmis.getUserId());       //String, Integer
      query.put("ORDER_NO",new Integer(this.orderNo));  //String, Integer
      query.put("SHIPMENT_ID",this.shipmentID);         //String, String
      query.put("LINE_ITEM_INFO",getLineItemInfo());   //String, Hashtable
      query.put("CHARGE_NUMBERS",this.chargeNumbers);  //String, Hashtable   11-01-01
      query.put("ACT_SYS_INFO",headerInfo.get("PACK"+actSysT.getText()));  //String, Hashtable
      query.put("CREDIT_CARD_INFO",(Hashtable)headerInfo.get("CREDIT_CARD_INFO"));  //String, Hashtable 11-05-01
      query.put("VENDOR_ID",this.vendorId);         //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
      }
      Boolean b = (Boolean)result.get("RETURN_CODE");
      if(!b.booleanValue()){
        val = false;
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg(result.get("MSG").toString());
        gd.setVisible(true);
        Integer errorLine = (Integer)result.get("ERROR_LINE");
        auditFailed(errorLine.intValue());
      }else{
        GenericDlg rd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
        rd.setMsg(result.get("MSG").toString());
        rd.setVisible(true);
        listChanged = (Boolean)result.get("LIST_CHANGED");
      }
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
    }
    return val;
  }

  Hashtable getLineItemInfo() {
    Hashtable h = new Hashtable();
    if(newWasteMaterialTable.isEditing()){
      newWasteMaterialTable.getCellEditor().stopCellEditing();
    }
    Vector itemIDV = new Vector();
    Vector qtyV = new Vector();
    Vector priceV = new Vector();
    Vector deleteItemV = new Vector();
    for (int i = 0; i < newWasteMaterialTable.getRowCount(); i++) {
      Boolean b = (Boolean)newWasteMaterialTable.getModel().getValueAt(i,DELETE_COL);
      if (b.booleanValue()) {
        deleteItemV.addElement(newWasteMaterialTable.getModel().getValueAt(i,ITEM_COL).toString());
      }else {
        itemIDV.addElement(newWasteMaterialTable.getModel().getValueAt(i,ITEM_COL).toString());
        qtyV.addElement(newWasteMaterialTable.getModel().getValueAt(i,QTY_COL).toString());
        priceV.addElement(newWasteMaterialTable.getModel().getValueAt(i,LPP_COL).toString());
      }
    }
    h.put("ITEM_IDS",itemIDV);
    h.put("QTY",qtyV);
    h.put("PRICE",priceV);
    h.put("DELETE_ITEM_IDS",deleteItemV);
    return h;
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }

  //this method is not use
  void saveCurrentChargeNumber() {
    //chargenum
    Vector v = new Vector();
    int numCols = chargeNumTable.getColumnCount();
    for(int i=0;i<chargeNumTable.getRowCount();i++){
      Hashtable f = new Hashtable();
      int mc = 0;
      f.put("ACCT_NUM_1",chargeNumTable.getModel().getValueAt(i,mc++).toString());
      if(numCols == 3){
        f.put("ACCT_NUM_2",chargeNumTable.getModel().getValueAt(i,mc++).toString());
      }
      f.put("PERCENTAGE",chargeNumTable.getModel().getValueAt(i,mc++).toString());
      v.addElement(f);
    }

    if (indirectR.isVisible() ) {
      if(indirectR.isSelected()){
        chargeH.put("CHARGE_NUM_INDIRECT",v);
      }else {
        chargeH.put("CHARGE_NUM_DIRECT",v);
      }
    }else{
      chargeH.put("CHARGE_NUM_DIRECT",v);
    }
  }

  public boolean passAudit() {
    String msg = "";
    Hashtable myH = getItemIDsFromTable();
    Vector v = (Vector)myH.get("ITEM_IDS");
    Vector lineV = (Vector)myH.get("LINE_NUMBER");

    String actSysId = actSysT.getText();
    Hashtable pack = (Hashtable)headerInfo.get("PACK"+actSysId);

    for (int i = 0; i < v.size(); i++) {
      String myItemID = (String)v.elementAt(i);
      Integer line = (Integer)lineV.elementAt(i);
      String myChargeT = "d";
      if (!chargeNumbers.containsKey(myItemID)) {
        int myLine = line.intValue();
        myLine += 1;
        msg = "Line "+myLine+" is missing charge information.";
        auditFailed(line.intValue());
        break;
      }else {
        Hashtable hh = (Hashtable)chargeNumbers.get(myItemID);
        Vector vv = (Vector)hh.get("CHARGE_NUMBER");
        myChargeT = hh.get("CHARGE_TYPE").toString();
        Hashtable tmpH = (Hashtable)pack.get(actSysId+myChargeT);
        String needPo = (String)tmpH.get("PO_REQUIRED");
        String prRequired = (String)tmpH.get("PR_ACCOUNT_REQUIRED");
        //audit accumulation point
        String accPt = (String)hh.get("ACCUMULATION_PT");
        if ("Select One".equalsIgnoreCase(accPt)) {
          msg = "Please select an accumulation point for the given line item.";
          auditFailed(line.intValue());
          break;
        }

        //audit charge number
        if (prRequired.equalsIgnoreCase("y")) {
          int cTotal = 0;
          //holder so I can check to see if user type in the same charge number
          Vector chargeNumberAuditV = new Vector(vv.size());
          for (int j = 0; j < vv.size(); j++) {
            Hashtable cHash = (Hashtable)vv.elementAt(j);
            String a1 = cHash.get("ACT_NUM_1").toString();
            String a2;
            if(cHash.get("ACT_NUM_2") == null){
              a2 = "";
            }else{
              a2 = cHash.get("ACT_NUM_2").toString();
            }
            String ps = cHash.get("PERCENTAGE").toString();
            if(BothHelpObjs.isBlankString(ps))continue;

            //users can't enter the same charge number twice
            if (chargeNumberAuditV.contains(a1+a2)) {
              msg = "You entered the same charge number twice.\nPlease check your charge numbers and re-submit.";
              auditFailed(line.intValue());
              break;
            }
            chargeNumberAuditV.addElement(a1+a2);

            //users must enter a percentage for charge number
            if(BothHelpObjs.isBlankString(a1) &&
              BothHelpObjs.isBlankString(a2)){
              if(BothHelpObjs.isBlankString(ps)){
                continue;
              }else{
                msg = "You must enter a charge number with each percentage.";
                auditFailed(line.intValue());
                break;
              }
            }
            int p = 0;
            try{
              p = Integer.parseInt(ps);
            }catch(Exception e){
              msg = "You must enter only numbers in the percentage column.";
              auditFailed(line.intValue());
              break;
            }
            if(p<0){
              msg = "You cannot enter negative numbers in the percentage column.";
              auditFailed(line.intValue());
              break;
            }
            cTotal = cTotal + p;
          } //end of loop
          //make sure that sum of percent is 100
          if(BothHelpObjs.isBlankString(msg)){
            if (cTotal != 100) {
              msg = "The sum of charge percentages must be 100.";
              auditFailed(line.intValue());
              break;
            }
          }
        } //end of account required

        //audit credit card information
        if ("c".equalsIgnoreCase(needPo)) {
          Hashtable creditCardInfo = (Hashtable)headerInfo.get("CREDIT_CARD_INFO");
          String creditCardNumber = (String)creditCardInfo.get("CREDIT_CARD_NUMBER");
          if (BothHelpObjs.isBlankString(creditCardNumber)) {
            msg = "Please enter credit card information for the given line item.";
            auditFailed(line.intValue());
            break;
          }else {
            break;
          }
        }else if ("p".equalsIgnoreCase(needPo)) {
          String selectedPo = BothHelpObjs.makeBlankFromNull((String)poC.getSelectedItem());
          if ("Select One".equalsIgnoreCase(selectedPo) || selectedPo.length() == 0) {
            msg = "Please select a BPO/BO for the given line item.";
            auditFailed(line.intValue());
            break;
          }
        } //end of needPo
      } //end of item in vector
    } //end of for each item

    if(msg.length() > 0){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg(msg);
      gd.setVisible(true);
      return false;
    }
    return true;
  }

  void auditFailed(int i){
    selectedLine = new Integer(i);
    setSelectedRow(selectedLine.intValue());
    buildChargeDetail(true);
  }

  void containerTable_mouseClicked(MouseEvent e) {

  }

  void indirectR_actionPerformed(ActionEvent e) {
    if (indirectR.isSelected()){
      directR.setSelected(false);
    }else {
      directR.setSelected(true);
    }
    buildChargeDetail(false);
  }

  void directR_actionPerformed(ActionEvent e) {
    if (directR.isSelected()) {
      indirectR.setSelected(false);
    }else {
      indirectR.setSelected(true);
    }
    buildChargeDetail(false);
  }


  void addB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    if(chargeNumTable.isEditing()){
      chargeNumTable.getCellEditor().stopCellEditing();
    }
    saveCurrentDetail(lastChargeType,lastAccumulationPt);
    showWasteMaterialCatalog();
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }
  void showWasteMaterialCatalog() {
    if (newWasteMaterialTable.getRowCount() > 0) {
      saveCurrentDetail(lastChargeType,lastAccumulationPt);
    }
    Vector v = getExistingItem();
    WasteMaterialCatalog wmc = new WasteMaterialCatalog(cmis.getMain(),this,"Waste Material Profiles",this.facilityId,this.vendorId);
    wmc.setShipmentId(this.shipmentID);
    wmc.setOrderNo(this.orderNo);
    wmc.setParent(cmis);
    if (BothHelpObjs.isBlankString(actSysT.getText())) {
      wmc.setActSysIDs(actSysIDV);
    }
    wmc.setActSysId(BothHelpObjs.makeBlankFromNull(actSysT.getText()));
    wmc.setCurrentItem(v);
    wmc.loadItAction();
    CenterComponent.centerCompOnScreen(wmc);
    wmc.setVisible(true);
  }

  Vector getExistingItem() {
    Vector result = new Vector();
    for (int i = 0; i < newWasteMaterialTable.getRowCount(); i++) {
      String myItem = newWasteMaterialTable.getModel().getValueAt(i,ITEM_COL).toString();
      result.addElement(myItem);
    }
    return result;
  }

  void addLineItem(Vector wasteItemV) {
    int rowNum = newWasteMaterialTable.getRowCount();
    DbTableModel model = (DbTableModel)newWasteMaterialTable.getModel();
    for (int i = 0; i < wasteItemV.size(); i++) {
      Hashtable h = (Hashtable)wasteItemV.elementAt(i);
      Object[] oa = new Object[newWasteMaterialTable.getColumnCount()];
      oa[0] = new Integer(newWasteMaterialTable.getRowCount()+1);
      oa[DELETE_COL] = new Boolean(false);
      oa[ITEM_COL] = (String)h.get("ITEM_ID");
      oa[PART_NO_COL] = (String)h.get("VENDOR_PART_NO");
      oa[QTY_COL] = new Integer((String)h.get("QTY"));
      oa[DESC_COL] = (String)h.get("DESC");
      oa[PACKAGING_COL] = (String)h.get("PACKAGING");
      oa[DOT_COL] = (String)h.get("SPEC");;
      oa[LPP_COL] = new Float((String)h.get("CURRENT_PRICE"));
      oa[ACT_COL] = actSysT.getText();
      //oa[CTYPE_COL] = "charge type";
      model.addRow(oa);
    }
    newWasteMaterialTable.revalidate();

    setExtendedPrice();
    saveDefaultData();
    if(rowNum == 0) {
      auditFailed(0);
    }else {
      auditFailed(rowNum);
    }
  }

  void deleteB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    deletingData = true;
    if(newWasteMaterialTable.isEditing()){
      newWasteMaterialTable.getCellEditor().stopCellEditing();
    }
    if(chargeNumTable.isEditing()){
      chargeNumTable.getCellEditor().stopCellEditing();
    }
    Vector deleteItemV = new Vector();
    Vector rowDeleteV = new Vector();
    for(int i = 0; i < newWasteMaterialTable.getRowCount(); i++) {
      Boolean b = (Boolean)newWasteMaterialTable.getModel().getValueAt(i,DELETE_COL);
      if (b.booleanValue()) {
        deleteItemV.addElement((String)newWasteMaterialTable.getModel().getValueAt(i,ITEM_COL));
        rowDeleteV.addElement(new Integer(i));
      }
    }
    if (deleteItemV.size() < 1) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please select item(s) you want to delete.");
      gd.setVisible(true);
    }else {
      if (deleteItem(deleteItemV)) {
        //now remove item from table as well as chargeNumber(Hashtable -- item_id)
        ClientHelpObjs.setEnabledContainer(this,false);
        for (int j = deleteItemV.size()-1; j >= 0 ; j--) {
          //first remove item from table
          Integer myRow = (Integer)rowDeleteV.elementAt(j);
          removeItemFromTable(myRow.intValue());
          reBuildTableLineNumber();
          //next remove item from chargeNumber
          chargeNumbers.remove((String)deleteItemV.elementAt(j));
        }
        ClientHelpObjs.setEnabledContainer(this,true);
      }
    }
    setExtendedPrice();
    buildChargeDetail(true);
    deletingData = false;
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  void reBuildTableLineNumber() {
    for (int i = 0; i < newWasteMaterialTable.getRowCount(); i++) {
      newWasteMaterialTable.getModel().setValueAt(new Integer(i+1),i,0);
    }
    setSelectedRow(0);
    selectedLine = new Integer(0);
  }

  void removeItemFromTable(int row) {
    DbTableModel myModel = (DbTableModel)newWasteMaterialTable.getModel();
    myModel.removeRow(row);
  }

  void ccardB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    WasteCreditCardInfoDlg cc = new WasteCreditCardInfoDlg(cmis.getMain(),"Credit Card Information",this,(Hashtable)headerInfo.get("CREDIT_CARD_INFO"),(String)headerInfo.get("REQ_NAME"));
    cc.setParent(cmis);
    cc.loadIt();
    CenterComponent.centerCompOnScreen(cc);
    cc.setVisible(true);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }


}

class WasteAddEditMaterialRequestLoadThread extends Thread {
  WasteAddEditMaterialRequest parent;
  public WasteAddEditMaterialRequestLoadThread(WasteAddEditMaterialRequest parent){
     super("WasteAddEditMaterialRequestLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class WasteAddEditMaterialRequest_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteAddEditMaterialRequest adaptee;
  WasteAddEditMaterialRequest_mouseAdapter(WasteAddEditMaterialRequest adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.containerTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}

class Waste_Generate_itemAdapter implements java.awt.event.ActionListener {
  WasteAddEditMaterialRequest adaptee;

  Waste_Generate_itemAdapter(WasteAddEditMaterialRequest adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.generationPtC_actionPerformed(e);
  }
}

