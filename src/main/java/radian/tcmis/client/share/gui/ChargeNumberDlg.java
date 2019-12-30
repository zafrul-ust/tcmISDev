
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
import java.beans.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class ChargeNumberDlg extends JDialog {
  CmisApp cmis;
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JPanel panel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel centerP = new JPanel();
  JPanel bottomP = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel leftP = new JPanel();
  JScrollPane partJSP = new JScrollPane();
  JPanel rightP = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  StaticJLabel facilityL = new StaticJLabel();
  DataJLabel facIdL = new DataJLabel();
  JButton ccardB = new JButton();
  JPanel topP = new JPanel();
  StaticJLabel actSysL = new StaticJLabel();
  DataJLabel actSysT = new DataJLabel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JRadioButton directR = new JRadioButton();
  JRadioButton indirectR = new JRadioButton();
  JTable chargeNumTable;
  TableSorter sorter = new TableSorter();
  String facilityId;
  String workAreaID;
  String lastActSysId = new String("");
  Hashtable actInfoH;
  Hashtable chargeH = new Hashtable();
  String accountSysID = "";
  JComboBox poC = new JComboBox();
  StaticJLabel poL = new StaticJLabel();
  JTextField releaseT = new JTextField();
  StaticJLabel releaseL = new StaticJLabel();
  boolean yesFlag = false;

  public ChargeNumberDlg (JFrame fr,String facilityId, String title, String workAreaID, Hashtable h) {
    super(fr, title, true);
    this.facilityId = facilityId;
    this.workAreaID = workAreaID;
    this.actInfoH = h;
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

  public void setParent(CmisApp c){
    cmis = c;
  }

  public void setAccountSysID (String s) {
    accountSysID = s;
    actSysT.setText(s);
  }

  public void loadIt(){
    ChargeNumberDlgLoadThread x = new ChargeNumberDlgLoadThread(this);
    x.start();
  }

  void loadItAction() {

  }

  void jbInit() throws Exception {
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
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
    centerP.setLayout(gridBagLayout4);
    topP.setLayout(gridBagLayout3);
    actSysL.setText("Account Sys:");
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
    facilityL.setText("Facility:");
    facIdL.setText("");
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

    poL.setText("BPO/PO:");
    poC.setMaximumSize(new Dimension(150, 23));
    poC.setMinimumSize(new Dimension(150, 23));
    poC.setPreferredSize(new Dimension(150, 23));
    releaseL.setText("Line:");
    releaseT.setMaximumSize(new Dimension(150, 23));
    releaseT.setMinimumSize(new Dimension(150, 23));
    releaseT.setPreferredSize(new Dimension(150, 23));

    getContentPane().add(panel1);
    //top panel
    panel1.add(topP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 8, 0), 0, 0));
    topP.add(actSysL, new GridBagConstraints2(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 11, 0), 0, 0));
    topP.add(actSysT, new GridBagConstraints2(1, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 11, 0), 0, 0));
    topP.add(facilityL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 0), 0, 0));
    topP.add(facIdL, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    //center panel
    panel1.add(centerP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 5, 13, 0), 0, 0));
    centerP.add(leftP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    leftP.add(partJSP, new GridBagConstraints2(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 4, 0, 6), 0, 0));
    centerP.add(rightP, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    rightP.add(directR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
    rightP.add(indirectR, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 70, 0, 0), 0, 0));
    rightP.add(ccardB, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 0));
    rightP.add(poL, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(30, 5, 0, 0), 0, 0));
    rightP.add(poC, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(30, 50, 0, 0), 0, 0));
    rightP.add(releaseL, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 25, 0, 0), 0, 0));
    rightP.add(releaseT, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 30, 0, 0), 0, 0));
    //bottom panel
    panel1.add(bottomP, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 0), 0, 0));
    bottomP.add(okB, null);
    bottomP.add(cancelB, null);
    panel1.setBorder(ClientHelpObjs.groupBox(""));
  }

  void loadChargeInfo() {
    try {
      actInfoH.put("TABLE_STYLE",(Hashtable)BothHelpObjs.getTableStyle());
      facIdL.setText(facilityId);
      leftP.setVisible(false);
      ccardB.setVisible(false);
      buildChargeDetail();
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        return;
    }
  }

  void buildChargeDetail() {
    String chargeType = null;
    Hashtable pack = (Hashtable) actInfoH.get("PACK");
    if (pack.containsKey(accountSysID+"d") && pack.containsKey(accountSysID+"i")) {
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
    Hashtable tmpH = (Hashtable)pack.get(accountSysID+chargeType);
    String needPo = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PO_REQUIRED"));
    String prRequired = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PR_ACCOUNT_REQUIRED"));
    //System.out.println("========== add into buildchargedetail needPo: "+needPo+ " pr_account_required: "+prRequired);
    //show charge table
    if (prRequired.equalsIgnoreCase("y")) {
      leftP.setVisible(true);
      if (!lastActSysId.equals(accountSysID)) {
        buildChargeNumber(chargeType);
      }
    }else {
      leftP.setVisible(false);
    }
    //show credit info
    if (needPo.equalsIgnoreCase("c")) {
      ccardB.setVisible(true);
      poL.setVisible(false);
      poC.setVisible(false);
      releaseL.setVisible(false);
      releaseT.setVisible(false);
    }else if (needPo.equalsIgnoreCase("p")) {
      ccardB.setVisible(false);
      poL.setVisible(true);
      poC.setVisible(true);
      releaseL.setVisible(true);
      releaseT.setVisible(true);
      buildPOComboBox();
    }else {
      ccardB.setVisible(false);
      poL.setVisible(false);
      poC.setVisible(false);
      releaseL.setVisible(false);
      releaseT.setVisible(false);
    }
    lastActSysId = accountSysID;
  }

  void buildPOComboBox() {
    Vector v = (Vector) actInfoH.get("ALL_PO");
    v = BothHelpObjs.sort(v);
    //making sure the Select a PO is the first one on list
    if (v.contains("Select a PO")) {
      v.remove("Select a PO");
      v.insertElementAt("Select a PO",0);
    }
    if (poC.getItemCount() > 0) poC.removeAllItems();
    poC = ClientHelpObjs.loadChoiceFromVector(poC,v);
    if(!BothHelpObjs.isBlankString((String)actInfoH.get("PREF_PO"))){
      poC.setSelectedItem(actInfoH.get("PREF_PO").toString());
    }else{
      poC.setSelectedIndex(0);
    }
    poC.setFont(actSysT.getFont());
    poC.setForeground(actSysT.getForeground());
  } //end of method

  void buildChargeNumber(String chargeType) {
    boolean directedCharge = false;
    Hashtable directedH = null;

    String[] chargeCols = new String[]{};
    Vector chargeV = new Vector();
    Vector percentV = null;
    Vector colHeader = new Vector();
    Hashtable pack = (Hashtable) actInfoH.get("PACK");
    Hashtable tmpH = (Hashtable)pack.get(accountSysID+chargeType);
    colHeader = (Vector)tmpH.get("LABELS");
    Hashtable actTypeH = (Hashtable) actInfoH.get("ACC_TYPE_H");
    Hashtable actH = (Hashtable)actTypeH.get(accountSysID);
    Vector chargeTypeV = (Vector) actH.get("CHARGE_TYPE");
    Vector display1V = (Vector) actH.get("DISPLAY_1");
    Vector display2V = (Vector) actH.get("DISPLAY_2");
    String display1 = (String)display1V.elementAt(chargeTypeV.indexOf(chargeType));
    String display2 = (String)display2V.elementAt(chargeTypeV.indexOf(chargeType));
    if ("y".equalsIgnoreCase(display1) || "y".equalsIgnoreCase(display2)) {
      chargeV = (Vector)actInfoH.get("CHARGE_NUMBER_"+chargeType.toUpperCase());
    }

    chargeCols = new String[colHeader.size()];
    for (int i = 0; i < colHeader.size(); i++) {
      chargeCols[i] = colHeader.elementAt(i).toString();
    }

    int numRows = 0;
    if (!"y".equalsIgnoreCase(display1) && !"y".equalsIgnoreCase(display2)) {
      numRows = 35;
    }else {
      if (chargeV.size() > 0 ) {
        numRows = chargeV.size();
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
      Hashtable h = (Hashtable) chargeV.elementAt(i);
      if (display1.equalsIgnoreCase("y")) {
        oa[i][r++] = (String) h.get("ACCT_NUM_1");
      }else {
        oa[i][r++] = "";
      }
      if (colHeader.size() == 3) {
        if (display2.equalsIgnoreCase("y")) {
          oa[i][r++] = (String) h.get("ACCT_NUM_2");
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

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(chargeNumTable.isEditing()){
      chargeNumTable.getCellEditor().stopCellEditing();
    }
    if (!passAudit()) {
      return;
    }else {
      saveCurrentChargeNumber();
      yesFlag = true;
      setVisible(false);
    }
  } //end of method

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    yesFlag = false;
    setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    yesFlag = false;
    this.setVisible(false);
  }

  void saveCurrentChargeNumber() {
    String chargeType = "";
    if (directR.isVisible() ) {
      chargeType = directR.isSelected()?"d":"i";
    }else {
      chargeType = "d";
    }
    actInfoH.put("CHARGE_TYPE",chargeType);
    Hashtable pack = (Hashtable) actInfoH.get("PACK");
    Hashtable tmpH = (Hashtable) pack.get(accountSysID+chargeType);
    String prRequired = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PR_ACCOUNT_REQUIRED"));
    String validCharge1 = (String) tmpH.get("VALID_1");
    String validCharge2 = (String) tmpH.get("VALID_2");
    if (prRequired.equalsIgnoreCase("y")) {
      //chargenum
      Vector v = new Vector();
      int numCols = chargeNumTable.getColumnCount();
      for(int i=0;i<chargeNumTable.getRowCount();i++){
        Hashtable f = new Hashtable();
        String a2 = "";
        int mc = 0;
        String a1 = chargeNumTable.getModel().getValueAt(i,mc++).toString();
        if(numCols == 3){
          a2 = chargeNumTable.getModel().getValueAt(i,mc++).toString();
        }
        String ps = chargeNumTable.getModel().getValueAt(i,mc++).toString();

        if(BothHelpObjs.isBlankString(ps)) {
          if ("y".equalsIgnoreCase(validCharge1) || "y".equalsIgnoreCase(validCharge2)) {
            continue;
          }
        }
        //save charge numbers that contain percentage
        f.put("ACCT_NUM_1",a1);
        if(numCols == 3){
          f.put("ACCT_NUM_2",a2);
        }
        f.put("PERCENTAGE",ps);
        v.addElement(f);
      } //end of for
      if(chargeType.equalsIgnoreCase("i")){
        chargeH.put("CHARGE_NUM_INDIRECT",v);
      }else {
        chargeH.put("CHARGE_NUM_DIRECT",v);
      }
    }  //end if
    actInfoH.put("CHARGE_NUMBER",chargeH);
  } //end of method

  public boolean passAudit() {
    String msg = "";
    //audit charge number
    String chargeType = "";
    if (directR.isVisible() ) {
      chargeType = directR.isSelected()?"d":"i";
    }else {
      chargeType = "d";
    }

    int cTotal = 0;
    Hashtable pack = (Hashtable) actInfoH.get("PACK");
    Hashtable tmpH = (Hashtable) pack.get(accountSysID+chargeType);
    String needPo = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PO_REQUIRED"));
    String prRequired = BothHelpObjs.makeBlankFromNull((String)tmpH.get("PR_ACCOUNT_REQUIRED"));
    String validCharge1 = (String) tmpH.get("VALID_1");
    String validCharge2 = (String) tmpH.get("VALID_2");
    //check charge numbers
    if (prRequired.equalsIgnoreCase("y")) {
      boolean containChargeNumber = false;
      int numCols = chargeNumTable.getColumnCount();
      for(int j = 0;j < chargeNumTable.getRowCount() ;j++){
        String a2 = "";
        int mc = 0;
        String a1 = chargeNumTable.getModel().getValueAt(j,mc++).toString();
        if(numCols == 3){
          a2 = chargeNumTable.getModel().getValueAt(j,mc++).toString();
        }
        String ps = chargeNumTable.getModel().getValueAt(j,mc++).toString();

        if(BothHelpObjs.isBlankString(ps)) {
          if ("y".equalsIgnoreCase(validCharge1) || "y".equalsIgnoreCase(validCharge2)) {
            continue;
          }
        }

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
        //the flag below will tell me whether or not user enter some charge number
        if (a1.length() > 0 || a2.length() > 0) {
          containChargeNumber = true;
        }
      }
      //check to see if charge numbers need validation
      if ("y".equalsIgnoreCase(validCharge1) || "y".equalsIgnoreCase(validCharge2)) {
        if(cTotal != 100){
          msg = "The sum of charge percentages must be 100.";
        }
      }else {         //validation charge number not required
        if (containChargeNumber) {
          if (cTotal != 100) {
            msg = "The sum of charge percentages must be 100.";
          }
        } //end of container charge number
      }
    } //end of pr account is required
    //check credit card info
    if ("p".equalsIgnoreCase(needPo)) {
      if ("Select a PO".equalsIgnoreCase(poC.getSelectedItem().toString())) {
        msg = "Please select a PO.";
      }else {
        actInfoH.put("PO_NUMBER",poC.getSelectedItem().toString());
        actInfoH.put("RELEASE_NUMBER",releaseT.getText());
      }
    }else if ("c".equalsIgnoreCase(needPo)) {
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
      //11-05-01 buildChargeNumber("d");
      buildChargeDetail();
    }else {
      indirectR.setSelected(true);
      //11-05-01 buildChargeNumber("i");
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
    Hashtable tmpH = (Hashtable)actInfoH.get(accountSysID+chargeType);
    CreditCardInfoDlg cc = new CreditCardInfoDlg(cmis.getMain(),"Credit Card Information",this,(Hashtable)tmpH.get("CREDIT_CARD_INFO"),"");
    cc.setParent(cmis);
    cc.loadIt();
    CenterComponent.centerCompOnScreen(cc);
    cc.setVisible(true);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }


}

class ChargeNumberDlgLoadThread extends Thread {
  ChargeNumberDlg parent;
  public ChargeNumberDlgLoadThread (ChargeNumberDlg parent){
     super("ChargeNumberDlgLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class ChargeNumberDlg_mouseAdapter extends java.awt.event.MouseAdapter {
  ChargeNumberDlg adaptee;
  ChargeNumberDlg_mouseAdapter(ChargeNumberDlg adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.containerTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}

