
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.admin;

import java.awt.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.*;
//import radian.tcmis.client.client.helpers.*;


import radian.tcmis.client.share.helpers.ClientHelpObjs;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AdminFacilityPanel extends AdminInputPanel { //JPanel { //
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  CmisApp parent;
  Object[][] facData;
  Vector whID;
  Vector whName;
  Vector locs;

  JComboBox mailC = new JComboBox();
  JComboBox shipC = new JComboBox();
  StaticJLabel mailL = new StaticJLabel();
  StaticJLabel shipL = new StaticJLabel();
  StaticJLabel facL = new StaticJLabel();
  StaticJLabel wareL = new StaticJLabel();
  StaticJLabel billL = new StaticJLabel();
  JComboBox wareC = new JComboBox();
  FacilityCombo billC = new FacilityCombo();
  JComboBox companyC = new JComboBox();
  StaticJLabel companyT = new StaticJLabel();
  StaticJLabel branchL = new StaticJLabel();
  StaticJLabel jdeL = new StaticJLabel();
  JTextField jdeT = new JTextField();
  JTextField branchT = new JTextField();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  StaticJLabel facNameL = new StaticJLabel();
  JTextField facNameT = new JTextField();
  FacilityCombo facC = new FacilityCombo();
  boolean facCLoaded = false;
  boolean dbDataLoaded = false;
  boolean dropFacs = false;
  boolean reloading = false;
  String facHold = "";
  JPanel jPanel2 = new JPanel();
  JButton saveB = new JButton();
  JButton newB = new JButton();
  JButton delB = new JButton();

  public AdminFacilityPanel(AdminWin aw) {
    super(aw,"Facilities");
    try  {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);

    mailL.setText("Mail Location:");
    mailL.setHorizontalAlignment(4);
    shipL.setText("Shipping Location:");
    shipL.setHorizontalAlignment(4);
    facL.setText("Facility ID:");
    facL.setHorizontalAlignment(4);
    wareL.setText("Warehouse:");
    wareL.setHorizontalAlignment(4);
    billL.setHorizontalAlignment(4);
    companyT.setHorizontalAlignment(4);
    branchL.setHorizontalAlignment(4);
    jdeL.setHorizontalAlignment(4);
    facNameL.setText("Facility Name:");
    facNameL.setHorizontalAlignment(4);
    jdeL.setText("JDE ID:");
    branchL.setText("Branch Plant:");
    companyT.setText("Company:");
    billL.setText("Bill to Facility:");

    jdeT.setPreferredSize(new Dimension(80, 25));
    jdeT.setMaximumSize(new Dimension(80, 25));
    jdeT.setMinimumSize(new Dimension(80, 25));
    branchT.setPreferredSize(new Dimension(80, 25));
    branchT.setMaximumSize(new Dimension(80, 25));
    branchT.setMinimumSize(new Dimension(80, 25));
    wareC.setMaximumSize(new Dimension(120, 27));
    wareC.setPreferredSize(new Dimension(120, 27));
    wareC.setMinimumSize(new Dimension(120, 27));
    billC.setMaximumSize(new Dimension(120, 27));
    billC.setPreferredSize(new Dimension(120, 27));
    billC.setMinimumSize(new Dimension(120, 27));
    companyC.setMaximumSize(new Dimension(120, 27));
    companyC.setPreferredSize(new Dimension(120, 27));
    companyC.setMinimumSize(new Dimension(120, 27));
    mailC.setMaximumSize(new Dimension(120, 27));
    mailC.setPreferredSize(new Dimension(120, 27));
    mailC.setMinimumSize(new Dimension(120, 27));
    shipC.setMaximumSize(new Dimension(120, 27));
    shipC.setPreferredSize(new Dimension(120, 27));
    shipC.setMinimumSize(new Dimension(120, 27));
    facNameT.setPreferredSize(new Dimension(120, 25));
    facNameT.setMaximumSize(new Dimension(120, 25));
    facNameT.setMinimumSize(new Dimension(120, 25));
    facC.setMaximumSize(new Dimension(120, 27));
    facC.setPreferredSize(new Dimension(120, 27));
    facC.setMinimumSize(new Dimension(120, 27));
    facC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        facC_actionPerformed(e);
      }
    });
    saveB.setMaximumSize(new Dimension(83, 35));
    saveB.setText("Save");
    saveB.setPreferredSize(new Dimension(83, 35));
    saveB.setMinimumSize(new Dimension(83, 35));
    saveB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveB_actionPerformed(e);
      }
    });
    newB.setMaximumSize(new Dimension(83, 35));
    newB.setText("New");
    newB.setPreferredSize(new Dimension(83, 35));
    newB.setMinimumSize(new Dimension(83, 35));
    newB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newB_actionPerformed(e);
      }
    });
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/submit.gif");
    saveB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/add.gif");
    newB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/minus.gif");
    delB.setIcon(ss);
    delB.setMaximumSize(new Dimension(83, 35));
    delB.setText("Delete");
    delB.setPreferredSize(new Dimension(83, 35));
    delB.setMinimumSize(new Dimension(83, 35));
    delB.addActionListener(new AdminFacilityPanel_delB_actionAdapter(this));


    add(wareC, new GridBagConstraints2(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(facNameT, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 0), 0, 0));
    this.add(billC, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(mailC, new GridBagConstraints2(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(companyC, new GridBagConstraints2(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(shipC, new GridBagConstraints2(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(jdeT, new GridBagConstraints2(3, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 3, 8, 0), 0, 0));
    this.add(branchT, new GridBagConstraints2(3, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 3, 3, 0), 0, 0));
    this.add(mailL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    this.add(shipL, new GridBagConstraints2(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 8, 0, 3), 0, 0));
    this.add(facL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 15, 9), 0, 0));

    this.add(wareL, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 3), 0, 0));
    this.add(billL, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 3), 14, 19));
    this.add(branchL, new GridBagConstraints2(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(8, 1, 8, 3), 0, 0));
    this.add(companyT, new GridBagConstraints2(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 3), 0, 0));
    this.add(jdeL, new GridBagConstraints2(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(8, 0, 8, 3), 0, 0));
    add(facNameL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 3), 0, 0));
    this.add(facC, new GridBagConstraints2(2, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 15, 0), 0, 0));
    this.add(jPanel2, new GridBagConstraints2(0, 6, 4, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(30, 0, 30, 0), 0, 0));
    jPanel2.add(saveB, null);
    jPanel2.add(newB, null);
    jPanel2.add(delB, null);

    ClientHelpObjs.makeDefaultColors(this);
    validate();
  }
  void dataLoaded(){
    if(facCLoaded && dbDataLoaded){
      if(!BothHelpObjs.isBlankString(facHold)){
        facC.setSelectedFacId(facHold);
        facHold = "";
      }
      fillScreen();
      this.aipButton.setEnabled(true);
      mailC.setEnabled(true);
      mailC.revalidate();
      shipC.setEnabled(true);
      shipC.revalidate();
      wareC.setEnabled(true);
      wareC.revalidate();
      try{Thread.sleep(300);}catch(Exception e){}
      ClientHelpObjs.setEnabledContainer(this,true);
      this.revalidate();
      mailC.repaint();
      shipC.repaint();
      wareC.repaint();
      repaint();
    }
  }

  public void setFacCombo(FacilityCombo fc){
    if(!getCmisApp().getGroupMembership().isSuperUser()){
      this.adminWin.removeButton(this.aipButton);
      return;
    }
    facC.setUseAllFacilities(false);
    facC.setCmisApp(getCmisApp());
    fc.loadAnotherOne(facC);

    billC.setUseAllFacilities(false);
    billC.setCmisApp(getCmisApp());
    fc.loadAnotherOne(billC);
    billC.addItem("");

    facC.setSelectedIndex(0);
    facC.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equalsIgnoreCase("loaded")) {
          facC.loadAnotherOne(billC);
          facCLoaded = true;
          dataLoaded();
          reloading = false;
        }
      }
    });
    facCLoaded = true;
    dataLoaded();
  }

  public void loadIt(){
    if(!getCmisApp().getGroupMembership().isSuperUser()){
      this.adminWin.removeButton(this.aipButton);
      return;
    }
    this.startLoad();
  }
  public void loadThisPanel(){
    ClientHelpObjs.setEnabledContainer(this,false);
    this.revalidate();
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    String action = dropFacs?"LOAD_DROP_FAC_INFO":"LOAD_REGULAR_FAC_INFO";
    query.put("ACTION",action); //String, String
    query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      if(!this.oopsReload){
        oopsReload = true;
        loadThisPanel();
        return;
      }
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      mailC.setEnabled(true);
      mailC.revalidate();
      shipC.setEnabled(true);
      shipC.revalidate();
      wareC.setEnabled(true);
      wareC.revalidate();
      return;
    }
    if(reloading){
      facCLoaded = false;
      facC.loadIt();
    }

    Vector locs = (Vector)result.get("LOCATIONS");
    locs = BothHelpObjs.sort(locs);
    if(mailC.getItemCount() > 0){
      mailC.removeAllItems();
      mailC.revalidate();
    }
    mailC = ClientHelpObjs.loadChoiceFromVector(mailC,locs);
    mailC.revalidate();

    if(shipC.getItemCount() > 0){
      shipC.removeAllItems();
      shipC.revalidate();
    }
    shipC = ClientHelpObjs.loadChoiceFromVector(shipC,locs);
    shipC.revalidate();

    whID = (Vector)result.get("WAREHOUSE_ID");
    whName = (Vector)result.get("WAREHOUSE_ID");
    if(wareC.getItemCount() > 0){
      wareC.removeAllItems();
      wareC.revalidate();
    }
    wareC = ClientHelpObjs.loadChoiceFromVector(wareC,whName);
    wareC.revalidate();
    wareC = ClientHelpObjs.choiceSort(wareC);
    wareC.revalidate();

    Vector g = (Vector)result.get("DATA");
    if(g.size() > 1){
      facData = BothHelpObjs.get2DArrayFromVector(g,9);
    }else{
      facData = new Object[0][0];
    }

    dbDataLoaded = true;
    dataLoaded();
  }

  void saveScreen(){
    facHold = facC.getSelectedFacId();
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION","SAVE_FAC_INFO"); //String, String
    query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String
    query.put("FACILITY_ID",facC.getSelectedFacId());
    query.put("FACILITY_NAME",facNameT.getText());
    query.put("COMPANY_NAME",companyC.getSelectedItem().toString());
    query.put("MAIL_LOC",mailC.getSelectedItem().toString());
    query.put("SHIP_LOC",shipC.getSelectedItem().toString());
    query.put("BRANCH_PLANT",branchT.getText());
    query.put("BILL_TO",billC.getSelectedFacId());
    query.put("JDE",jdeT.getText());
    query.put("FAC_TYPE","");
    query.put("PREF_WAREHOUSE",this.getWarehouseId(wareC.getSelectedItem().toString()));


    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      mailC.setEnabled(true);
      mailC.revalidate();
      shipC.setEnabled(true);
      shipC.revalidate();
      wareC.setEnabled(true);
      wareC.revalidate();
      return;
    }

    String s = ((Vector)result.get("DATA")).elementAt(0).toString();
    String msg = "";
    if(s.equalsIgnoreCase("true")){
      msg = "The Facility information was saved.";
    }else{
      msg = "The Facility information was not saved.";
    }
    GenericDlg gd = new GenericDlg(getCmisApp().getMain(),"Facility Update",true);
    gd.setMsg(msg);
    gd.setVisible(true);
    reloading = true;
    startLoad();
  }

  void fillScreen(){
    if(companyC.getItemCount() == 0){
      companyC.addItem("Radian");
      companyC.addItem(getCmisApp().getResourceBundle().getString("APP_NAME").toString());
      companyC = ClientHelpObjs.choiceSort(companyC);
    }

    String myFac = facC.getSelectedFacId();
    for(int i=0;i<facData.length;i++){
      if(facData[i][0].toString().equals(myFac)){
        facNameT.setText(facData[i][1].toString());
        billC.setSelectedFacId(facData[i][2].toString());
        mailC.setSelectedItem(facData[i][3].toString());
        mailC.revalidate();
        shipC.setSelectedItem(facData[i][4].toString());
        shipC.revalidate();
        wareC.setSelectedItem(getWarehouseName(facData[i][5].toString()));
        wareC.revalidate();
        jdeT.setText(facData[i][6].toString());
        companyC.setSelectedItem(facData[i][7].toString());
        branchT.setText(facData[i][8].toString());
        break;
      }
    }
  }

  void goDeleteFac(){
    ConfirmDlg cd = new ConfirmDlg(getCmisApp().getMain(),"Delete Facility",true);
    cd.setMsg("Do you want to delete the "+facC.getSelectedItem().toString()+" facility?");
    cd.setVisible(true);
    if(!cd.getConfirmationFlag())return;

    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
      Hashtable query = new Hashtable();
      query.put("ACTION","DELETE_FACILITY");
      query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String
      query.put("FACILITY_ID",facC.getSelectedFacId());

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(getCmisApp().getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
      }

      Vector info = (Vector) result.get("DATA");
      GenericDlg gd = new GenericDlg(getCmisApp().getMain(),true);
      if(info.elementAt(0).toString().equalsIgnoreCase("true")){
        gd.setMsg("The facility was deleted.");
      }else{
        gd.setMsg("The facility was NOT deleted.");
      }
      gd.show();
    } catch (Exception ie) {
      //getCmisApp().getMain().stopImage();
      ie.printStackTrace();
    }
  }

  String getWarehouseName(String id){
    for(int i=0;i<whID.size();i++){
      if(whID.elementAt(i).toString().equals(id)){
        return whName.elementAt(i).toString();
      }
    }
    return "";
  }
  String getWarehouseId(String name){
    for(int i=0;i<whName.size();i++){
      if(whName.elementAt(i).toString().equals(name)){
        return whID.elementAt(i).toString();
      }
    }
    return "";
  }

  void goNewFacility(){
    AddFacilityDlg afd = new AddFacilityDlg(getCmisApp().getMain(),"Add Facility",true,getCmisApp());
    CenterComponent.centerCompOnScreen(afd);
    afd.setVisible(true);
    if(afd.getCanceled()) return;

    facHold = afd.getFacId();
    reloading = true;
    startLoad();
  }

  void saveB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    saveScreen();
  }

  void newB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goNewFacility();
  }

  void facC_actionPerformed(ActionEvent e) {
    if(!facCLoaded)return;
    fillScreen();
  }

  void delB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goDeleteFac();
    reloading = true;
    startLoad();
  }

}

class AdminFacilityPanel_delB_actionAdapter implements java.awt.event.ActionListener {
  AdminFacilityPanel adaptee;


  AdminFacilityPanel_delB_actionAdapter(AdminFacilityPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.delB_actionPerformed(e);
  }
}
