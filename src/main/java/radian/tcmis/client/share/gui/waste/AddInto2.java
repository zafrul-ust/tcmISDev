
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
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class AddInto2 extends JDialog {
  JPanel panel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel centerP = new JPanel();
  JPanel bottomP = new JPanel();
  JPanel topP = new JPanel();
  StaticJLabel toProfileL = new StaticJLabel();
  JLabel profileL = new JLabel();
  StaticJLabel actSysL = new StaticJLabel();
  JComboBox actSysC = new JComboBox();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JPanel leftP = new JPanel();
  JScrollPane partJSP = new JScrollPane();
  JPanel rightP = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  StaticJLabel amountL = new StaticJLabel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JTextField amountT = new JTextField();
  JLabel unitL = new JLabel();
  CmisApp cmis;
  //CmisTable chargeTable;
  JTable chargeNumTable;
  TableSorter sorter = new TableSorter();
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
  StaticJLabel statewcL = new StaticJLabel();
  JLabel stateL = new JLabel();
  String stateWasteCode = null;
  StaticJLabel facilityL = new StaticJLabel();
  JLabel facIdL = new JLabel();
  StaticJLabel generateL = new StaticJLabel();
  JComboBox generateC = new JComboBox();
  Vector wasteLocationIdV = null;
  StaticJLabel currentAmountL = new StaticJLabel();
  JLabel cAmountL = new JLabel();

  Hashtable wasteLocDirectedCharge = null;
  String lastGenPt = new String("");
  String sizeUnit;
  String lastChargeType = "";

  JButton ccardB = new JButton();

  public AddInto2(JFrame fr,String facilityId, String title, String storageLocation, int selectedR) {
    super(fr, title, false);
    this.facilityId = facilityId;
    this.storageLocation = storageLocation;
    this.selectedR = selectedR;
    //this.vendorId = vendor;

    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(500,340));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
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

  public void setSizeUnit(String val) {
    this.sizeUnit = val;
  }

  public void setParent(CmisApp c){
    cmis = c;
  }

  public void loadIt(){
    AddInto2LoadThread x = new AddInto2LoadThread(this);
    x.start();
  }

  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setFont(new java.awt.Font("Dialog", 0, 10));
    okB.setMaximumSize(new Dimension(79, 25));
    okB.setMinimumSize(new Dimension(79, 25));
    okB.setPreferredSize(new Dimension(79, 25));
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setFont(new java.awt.Font("Dialog", 0, 10));
    cancelB.setMaximumSize(new Dimension(97, 25));
    cancelB.setMinimumSize(new Dimension(97, 25));
    cancelB.setPreferredSize(new Dimension(97, 25));
    cancelB.setIcon(ss);
    panel1.setLayout(gridBagLayout1);
    centerP.setMaximumSize(new Dimension(150, 100));
    centerP.setMinimumSize(new Dimension(150, 100));
    centerP.setPreferredSize(new Dimension(150, 100));
    centerP.setLayout(gridBagLayout4);
    topP.setMaximumSize(new Dimension(450, 125));
    topP.setMinimumSize(new Dimension(450, 125));
    topP.setPreferredSize(new Dimension(450, 125));
    topP.setLayout(gridBagLayout3);
    toProfileL.setText("To Profile:");
    profileL.setFont(new java.awt.Font("Dialog", 0, 10));
    profileL.setText("Test Profile");
    actSysL.setText("Account Sys:");
    //chargeTable = new CmisTable();
    chargeNumTable = new JTable();
    partJSP.getViewport().setView(chargeNumTable);
    panel1.setMaximumSize(new Dimension(500, 400));
    panel1.setMinimumSize(new Dimension(500, 400));
    panel1.setPreferredSize(new Dimension(500, 400));
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    leftP.setMaximumSize(new Dimension(250, 100));
    leftP.setMinimumSize(new Dimension(250, 100));
    leftP.setPreferredSize(new Dimension(250, 100));
    leftP.setLayout(gridBagLayout5);
    rightP.setMaximumSize(new Dimension(200, 100));
    rightP.setMinimumSize(new Dimension(120, 100));
    rightP.setPreferredSize(new Dimension(200, 100));
    rightP.setLayout(gridBagLayout2);
    amountL.setText("Amount:");
    amountT.setMaximumSize(new Dimension(70, 21));
    amountT.setMinimumSize(new Dimension(70, 21));
    amountT.setPreferredSize(new Dimension(70, 21));
    unitL.setFont(new java.awt.Font("Dialog", 0, 10));
    unitL.setText("");
    actSysC.setFont(new java.awt.Font("Dialog", 0, 10));
    actSysC.setMaximumSize(new Dimension(100, 21));
    actSysC.setMinimumSize(new Dimension(100, 21));
    actSysC.setPreferredSize(new Dimension(100, 21));
    actSysC.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        actSysC_actionPerformed(e);
      }
    });
    partJSP.setMaximumSize(new Dimension(230, 150));
    partJSP.setMinimumSize(new Dimension(220, 150));
    partJSP.setPreferredSize(new Dimension(220, 150));
    directR.setText("Direct");
    directR.setFont(new java.awt.Font("Dialog", 0, 10));
    directR.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        directR_actionPerformed(e);
      }
    });
    indirectR.setText("Indirect");
    indirectR.setFont(new java.awt.Font("Dialog", 0, 10));
    indirectR.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        indirectR_actionPerformed(e);
      }
    });
    statewcL.setText("State Waste Code:");
    stateL.setFont(new java.awt.Font("Dialog", 0, 10));
    stateL.setText("");
    facilityL.setText("Facility:");
    generateL.setText("Accumulation Pts:");
    facIdL.setFont(new java.awt.Font("Dialog", 0, 10));
    facIdL.setText("");
    generateC.setFont(new java.awt.Font("Dialog", 0, 10));
    generateC.setMaximumSize(new Dimension(170, 21));
    generateC.setMinimumSize(new Dimension(170, 21));
    generateC.setPreferredSize(new Dimension(170, 21));
    currentAmountL.setText("Current Amount:");
    cAmountL.setFont(new java.awt.Font("Dialog", 0, 10));
    ccardB.setFont(new java.awt.Font("Dialog", 0, 10));
    ccardB.setMaximumSize(new Dimension(163, 25));
    ccardB.setMinimumSize(new Dimension(163, 25));
    ccardB.setPreferredSize(new Dimension(163, 25));
    ccardB.setText("Credit Card Information");
    ccardB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        ccardB_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(centerP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 5, 13, 0), 0, 0));
    centerP.add(leftP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    leftP.add(partJSP, new GridBagConstraints2(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 4, 0, 6), 0, 0));
    /*
    leftP.add(directR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
    leftP.add(indirectR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 70, 0, 0), 0, 0));
    */
    centerP.add(rightP, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    rightP.add(directR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
    rightP.add(indirectR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 70, 0, 0), 0, 0));
    rightP.add(ccardB, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
    /*
    rightP.add(cAmountL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 0), 0, 0));
    rightP.add(currentAmountL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 0), 0, 0));
    /*rightP.add(amountL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    rightP.add(amountT, new GridBagConstraints2(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    rightP.add(unitL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 120, 5, 5), 0, 0));
    rightP.add(unitC, new GridBagConstraints2(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    */
    panel1.add(bottomP, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 0), 0, 0));
    bottomP.add(okB, null);
    bottomP.add(cancelB, null);
    panel1.add(topP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 8, 0), 0, 0));
    topP.add(toProfileL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 0, 0), 0, 0));
    topP.add(profileL, new GridBagConstraints2(1, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    topP.add(actSysL, new GridBagConstraints2(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 11, 0), 0, 0));
    topP.add(actSysC, new GridBagConstraints2(1, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 11, 0), 0, 0));
    topP.add(amountL, new GridBagConstraints2(1, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 130, 11, 0), 0, 0));
    topP.add(amountT, new GridBagConstraints2(1, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 175, 11, 0), 0, 0));
    topP.add(unitL, new GridBagConstraints2(1, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 250, 11, 0), 0, 0));
    topP.add(statewcL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 0, 0), 0, 0));
    topP.add(stateL, new GridBagConstraints2(1, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    topP.add(facilityL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 0), 0, 0));
    topP.add(facIdL, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    topP.add(generateL, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 90, 5, 0), 0, 0));
    topP.add(generateC, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 180, 5, 0), 0, 0));
    topP.add(cAmountL, new GridBagConstraints2(1, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 175, 5, 0), 0, 0));
    topP.add(currentAmountL, new GridBagConstraints2(1, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 95, 5, 0), 0, 0));
  }

  void loadItAction() {
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_ADD_INTO_INFO"); //String, String
      query.put("FAC_ID",this.facilityId); //String, String
      query.put("TO_CONTAINER_ID",addIntoContainerId);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      actInfoH = (Hashtable)result.get("ADD_INTO_INFO");
      actInfoH.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));
      wasteLocDirectedCharge = (Hashtable)actInfoH.get("WASTE_LOC_DIRECTED_CHARGE");
      Integer currentAmount = (Integer)result.get("CURRENT_AMOUNT");
      Hashtable generatePtsH = (Hashtable)result.get("GENERATION_PTS");
      Vector actSysV = (Vector)actInfoH.get("ACCOUNT_SYS");
      actSysV = BothHelpObjs.sort(actSysV);
      actSysC = ClientHelpObjs.loadChoiceFromVector(actSysC,actSysV);
      if (actSysV.size() > 1) {
        actSysC.insertItemAt("Select One",0);
        actSysC.setSelectedIndex(0);
      }
      actSysC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
        //System.out.println("in item state Change");
        buildChargeDetail();
      }
      });
      actSysC.setEnabled(true);

      profileL.setText(this.vendorProfileDesc);
      stateL.setText(stateWasteCode);
      unitL.setText(sizeUnit);
      facIdL.setText(this.facilityId);

      //trong 10-4
      cAmountL.setText(currentAmount.toString()+ " "+sizeUnit);

      //trong 9-30
      wasteLocationIdV = (Vector)generatePtsH.get("WASTE_LOCATION_ID");
      Vector locationDescV = (Vector)generatePtsH.get("LOCATION_DESC");
      //System.out.println("++++++++++++ here is it: "+generatePtsH);
      generateC = ClientHelpObjs.loadChoiceFromVector(generateC,locationDescV);
      if (locationDescV.size() > 1) {
        generateC.insertItemAt("Select One",0);
        generateC.setSelectedIndex(0);
      }
      generateC.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(ItemEvent evt) {
          //System.out.println("in item state Change");
          buildChargeDetail();
        }
      });

      //11-05-01 begin
      leftP.setVisible(false);
      ccardB.setVisible(false);
      buildChargeDetail();
      //end

      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();

    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
  }

  void buildChargeDetail() {
    String actSysId = actSysC.getSelectedItem().toString();
    String genPt = generateC.getSelectedItem().toString();
    String chargeType = null;

    if (actSysId.equalsIgnoreCase("Select One")) {
      leftP.setVisible(false);
      ccardB.setVisible(false);
      directR.setVisible(false);
      indirectR.setVisible(false);
      return;
    }

    if (actInfoH.containsKey(actSysC.getSelectedItem().toString()+"d") &&
        actInfoH.containsKey(actSysC.getSelectedItem().toString()+"i")) {
      directR.setVisible(true);
      indirectR.setVisible(true);
      if (!directR.isSelected() && !indirectR.isSelected()) {
        directR.setSelected(true);
      }
    }else {
      directR.setVisible(false);
      indirectR.setVisible(false);
    }

    if (directR.isVisible()) {
      chargeType = directR.isSelected()?"d":"i";
    }else {
      chargeType = "d";
    }

    //11-05-01 begin
    Hashtable tmpH = (Hashtable)actInfoH.get(actSysId+chargeType);
    String needPo = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PO_REQUIRED"));
    String prRequired = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PR_ACCOUNT_REQUIRED"));
    //show charge table
    if (prRequired.equalsIgnoreCase("y")) {
      leftP.setVisible(true);
      if (!lastActSysId.equals(actSysId) || !lastGenPt.equals(genPt) || !lastChargeType.equals(chargeType)) {
        buildChargeNumber(chargeType);
      }
    }else {
      leftP.setVisible(false);
    }
    //show credit info
    if (needPo.equalsIgnoreCase("c")) {
      ccardB.setVisible(true);
    }else {
      ccardB.setVisible(false);
    }
    //end of 11-05-01

    lastActSysId = actSysId;
    lastGenPt = genPt;
    lastChargeType = chargeType;
  }

  void buildChargeNumber(String chargeType) {
    String actSysId = actSysC.getSelectedItem().toString();
    String genPt = generateC.getSelectedItem().toString();
    boolean directedCharge = false;
    Hashtable directedH = null;

    String[] chargeCols = new String[]{};
    Vector chargeV = new Vector();
    Vector chargeV2 = new Vector();
    Vector percentV = null;
    Vector colHeader = new Vector();
    Hashtable actTypeH = (Hashtable)actInfoH.get(actSysId+chargeType);
    colHeader = (Vector)actTypeH.get("CHARGE_LABEL");
    String display1 = (String)actTypeH.get("DISPLAY_1");

    //11-06-01
    Vector chargeIDV = (Vector)actTypeH.get("CHARGE_ID");

    if (display1.equalsIgnoreCase("y")) {
      if (wasteLocDirectedCharge.containsKey(actSysId+chargeType+genPt)) {
        directedH = getWasteLocDirectedCharge(actSysId+chargeType+genPt);
        chargeV = (Vector)directedH.get("CHARGE_NUM_1");
        percentV = (Vector)directedH.get("PERCENT");
        directedCharge = true;
      }else {
        chargeV = (Vector)actTypeH.get(chargeIDV.elementAt(0).toString()+"CHARGE_NUMBER_1");
      }
    }
    String display2 = (String)actTypeH.get("DISPLAY_2");
    if (display2.equalsIgnoreCase("y")) {
      if (wasteLocDirectedCharge.containsKey(actSysId+chargeType+genPt)) {
        chargeV2 = (Vector)directedH.get("CHARGE_NUM_2");
      }else {
        chargeV2 = (Vector)actTypeH.get(chargeIDV.elementAt(1).toString()+"CHARGE_NUMBER_2");
      }
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
      if (display1.equalsIgnoreCase("y")) {
        oa[i][r++] = chargeV.elementAt(i).toString();
      }else {
        oa[i][r++] = "";
      }
      if (colHeader.size() == 3) {
        if (display2.equalsIgnoreCase("y")) {
          oa[i][r++] = chargeV2.elementAt(i).toString();
        }else {
          oa[i][r++] = "";
        }
      }
       //fill the percent if directed charge is use
      if (directedCharge) {
        oa[i][r++] = percentV.elementAt(i).toString();
      }else if (chargeV.size() == 1) {
        oa[i][r++] = "100";              //percentage
      }else {
        oa[i][r++] = "";
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

    //chargeNumTable = new JTable(ctm);
    TableSorter sorter = new TableSorter(ctm);
    chargeNumTable = new CmisTable(sorter);
    sorter.setColTypeFlag(ctm.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(chargeNumTable);
    chargeNumTable.setCellSelectionEnabled(true);

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable)actInfoH.get("TABLE_STYLE");
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
    chargeNumTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = chargeNumTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    partJSP.getViewport().setView(chargeNumTable);
    for (int i = 0; i < chargeNumTable.getColumnCount(); i++) {
      chargeNumTable.getColumn(chargeNumTable.getColumnName(i)).setPreferredWidth(colWidths[i]);
    }

    chargeNumTable.validate();
    partJSP.validate();
    leftP.validate();
  }

  Hashtable getWasteLocDirectedCharge(String key) {
    Hashtable h = new Hashtable();
    Vector v = (Vector)wasteLocDirectedCharge.get(key);
    Vector cnum1 = new Vector();
    Vector cnum2 = new Vector();
    Vector percent = new Vector();
    for (int i = 0; i < v.size(); i++) {
      Hashtable innerH = (Hashtable)v.elementAt(i);
      cnum1.addElement((String)innerH.get("ACT_NUM_1"));
      cnum2.addElement((String)innerH.get("ACT_NUM_2"));
      percent.addElement((String)innerH.get("PERCENTAGE"));
    }
    h.put("CHARGE_NUM_1",cnum1);
    h.put("CHARGE_NUM_2",cnum2);
    h.put("PERCENT",percent);
    return h;
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(chargeNumTable.isEditing()){
      chargeNumTable.getCellEditor().stopCellEditing();
    }
    saveCurrentChargeNumber();
    if (!passAudit()) {
      return;
    }else {
      if (updateRequest()) {
        setVisible(false);
      }else {
        return;
      }
    }
  }

  boolean updateRequest() {
    boolean val = true;
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","UPDATE_ADD_INTO"); //String, String
      query.put("FACILITY_ID",facilityId);
      query.put("GENERATION_POINT",getWasteLocationId());
      query.put("USER_ID",cmis.getUserId());
      query.put("TO_CONTAINER_ID",addIntoContainerId);
      String temp = BothHelpObjs.makeBlankFromNull(amountT.getText());
      Integer amount = new Integer(temp);
      query.put("AMOUNT",amount);
      query.put("ACCOUNT_SYS",actSysC.getSelectedItem().toString());
      String chargeType = "";
      if (indirectR.isVisible()) {
        if (indirectR.isSelected()) {
          query.put("CHARGE_TYPE","i");
          chargeType = "i";
        }else {
          query.put("CHARGE_TYPE","d");
          chargeType = "d";
        }
      }else {
        query.put("CHARGE_TYPE","d");
        chargeType = "d";
      }
      //query.put("CHARGE_NUMBER",chargeH);
      //11-05-01
      Hashtable tmpH = (Hashtable)actInfoH.get(actSysC.getSelectedItem().toString()+chargeType);
      String needPo = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PO_REQUIRED"));
      String prRequired = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PR_ACCOUNT_REQUIRED"));
      if (prRequired.equalsIgnoreCase("y")) {
        query.put("CHARGE_NUMBER",chargeH);
      }
      if (needPo.equalsIgnoreCase("c")) {
        query.put("CREDIT_CARD_INFO",(Hashtable)tmpH.get("CREDIT_CARD_INFO"));
      }
      query.put("PO_REQUIRED",needPo);
      query.put("PR_ACCOUNT_REQUIRED",prRequired);

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
        GenericDlg rd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
        rd.setMsg(result.get("MSG").toString());
        rd.setVisible(true);
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

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }

  void saveCurrentChargeNumber() {
    String chargeType = "";
    if (directR.isVisible() ) {
      chargeType = directR.isSelected()?"d":"i";
    }else {
      chargeType = "d";
    }

    Hashtable tmpH = (Hashtable)actInfoH.get(actSysC.getSelectedItem().toString()+chargeType);
    String prRequired = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PR_ACCOUNT_REQUIRED"));
    System.out.println("========== add into save current charge number pr_account_required: "+prRequired);
    if (prRequired.equalsIgnoreCase("y")) {
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
      } //end of for
      if(chargeType.equalsIgnoreCase("i")){
        chargeH.put("CHARGE_NUM_INDIRECT",v);
      }else {
        chargeH.put("CHARGE_NUM_DIRECT",v);
      }
    }  //end if
  } //end of method

  public String getWasteLocationId() {
    String result = "";
    int pos = generateC.getSelectedIndex();
    if (wasteLocationIdV.size() > 1) {
      result = (String)wasteLocationIdV.elementAt(pos-1);
    }else {
      result = (String)wasteLocationIdV.firstElement();
    }
    return result;
  }

  public boolean passAudit() {
    String msg = "";

    if (actSysC.getSelectedItem().toString().equalsIgnoreCase("Select One")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please select an account system.");
      gd.setVisible(true);
      return false;
    }

    if (generateC.getSelectedItem().toString().equalsIgnoreCase("Select One")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please select an accumulation point.");
      gd.setVisible(true);
      return false;
    }

    String amount = amountT.getText();
    if (BothHelpObjs.isBlankString(amount)) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please a positive number for amount.");
      gd.setVisible(true);
      return false;
    }
    try {
      int tmp = Integer.parseInt(amount);
    }catch(Exception ee) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("Please a positive number for amount.");
      gd.setVisible(true);
      return false;
    }

    //audit charge number
    Vector cn = new Vector();
    String chargeType = "";
    if (indirectR.isVisible()) {
      if (indirectR.isSelected()) {
        cn = (Vector)chargeH.get("CHARGE_NUM_INDIRECT");
        chargeType = "i";
      }else {
        cn = (Vector)chargeH.get("CHARGE_NUM_DIRECT");
        chargeType = "d";
      }
    }else {
      cn = (Vector)chargeH.get("CHARGE_NUM_DIRECT");
      chargeType = "d";
    }

    int cTotal = 0;
    //11-05-01
    Hashtable tmpH = (Hashtable)actInfoH.get(actSysC.getSelectedItem().toString()+chargeType);
    String needPo = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PO_REQUIRED"));
    String prRequired = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PR_ACCOUNT_REQUIRED"));
    //check charge numbers
    if (prRequired.equalsIgnoreCase("y")) {
      for(int j = 0;j < chargeNumTable.getRowCount() ;j++){
        Hashtable cHash = (Hashtable)cn.elementAt(j);
        String a1 = cHash.get("ACCT_NUM_1").toString();
        String a2;
        if(cHash.get("ACCT_NUM_2") == null){
          a2 = "";
        }else{
          a2 = cHash.get("ACCT_NUM_2").toString();
        }
        String ps = cHash.get("PERCENTAGE").toString();
        if(BothHelpObjs.isBlankString(ps))continue;
        if(BothHelpObjs.isBlankString(a1) &&
           BothHelpObjs.isBlankString(a2)){
          if(BothHelpObjs.isBlankString(ps)){
            continue;
          }else{
            msg = "You must enter a charge number with each percentage.";
            break;
          }
        }
        int p = 0;
        try{
          p = Integer.parseInt(ps);
        }catch(Exception e){
          msg = "You must enter only numbers in the percentage column.";
          break;
        }
        if(p<0){
          msg = "You cannot enter negative numbers in the percentage column.";
          break;
        }
        cTotal = cTotal + p;
      }
      if(cTotal != 100){
        msg = "The sum of charge percentages must be 100.";
      }
    }
    //check credit card info
    if (needPo.equalsIgnoreCase("c")) {
      Hashtable creditCardInfo = (Hashtable)tmpH.get("CREDIT_CARD_INFO");
      if (BothHelpObjs.isBlankString((String)creditCardInfo.get("CREDIT_CARD_NUMBER"))) {
        msg = "Please enter credit card for the given request.";
      }
    }

    if (msg.length() > 0) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg(msg);
      gd.setVisible(true);
      return false;
    }

    return true;
  }

  void containerTable_mouseClicked(MouseEvent e) {

  }

  void actSysC_actionPerformed(ActionEvent e) {

  }

  void indirectR_actionPerformed(ActionEvent e) {
    if (indirectR.isSelected()){
      directR.setSelected(false);
      buildChargeDetail();
    }else {
      directR.setSelected(true);
      buildChargeDetail();
    }
  }

  void directR_actionPerformed(ActionEvent e) {
    if (directR.isSelected()) {
      indirectR.setSelected(false);
      buildChargeDetail();
    }else {
      indirectR.setSelected(true);
      buildChargeDetail();
    }
  }

  void ccardB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    String chargeType = "";
    if (directR.isVisible()) {
      chargeType = directR.isSelected()?"d":"i";
    }else {
      chargeType = "d";
    }
    Hashtable tmpH = (Hashtable)actInfoH.get(actSysC.getSelectedItem().toString()+chargeType);
    CreditCardInfoDlg cc = new CreditCardInfoDlg(cmis.getMain(),"Credit Card Information",this,(Hashtable)tmpH.get("CREDIT_CARD_INFO"),"");
    cc.setParent(cmis);
    cc.loadIt();
    CenterComponent.centerCompOnScreen(cc);
    cc.setVisible(true);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }


}

class AddInto2LoadThread extends Thread {
  AddInto2 parent;
  public AddInto2LoadThread(AddInto2 parent){
     super("AddInto2LoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class AddInto2_mouseAdapter extends java.awt.event.MouseAdapter {
  AddInto2 adaptee;
  AddInto2_mouseAdapter(AddInto2 adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.containerTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}

