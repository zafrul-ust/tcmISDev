
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.admin;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.util.*;
import radian.tcmis.client.share.gui.*;

public class AdminLocationPanel extends AdminInputPanel { //JPanel { //

  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JComboBox locC = new JComboBox();
  JTextField add1T = new JTextField();
  JTextField add2T = new JTextField();
  JTextField add3T = new JTextField();
  JTextField cityT = new JTextField();
  JTextField locCodeT = new JTextField();
  JTextField zipT = new JTextField();
  JComboBox clientC = new JComboBox();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();
  JComboBox stateC = new JComboBox();
  JPanel jPanel1 = new JPanel();
  JButton newB = new JButton();
  JButton deleteB = new JButton();
  JButton saveB = new JButton();
  JPanel myP = new JPanel();


  Vector country;
  Vector states;
  boolean firstTime = true;
  boolean childFound = false;

  Object[][] locData;
  JComboBox countryC = new JComboBox();

  public AdminLocationPanel(AdminWin aw) {
    super(aw,"Addresses");
    try  {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    myP.setLayout(new BorderLayout());
    jLabel1.setText("Client:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Location Code:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Country:");
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel4.setText("City:");
    jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel5.setText("(3):");
    jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel6.setText("(2):");
    jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel7.setText("Address(1):");
    jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel8.setText("Address ID:");
    jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
    add1T.setPreferredSize(new Dimension(63, 24));
    add1T.setMinimumSize(new Dimension(4, 24));
    add2T.setPreferredSize(new Dimension(63, 24));
    add2T.setMinimumSize(new Dimension(4, 24));
    add3T.setPreferredSize(new Dimension(63, 24));
    add3T.setMinimumSize(new Dimension(4, 24));
    cityT.setPreferredSize(new Dimension(63, 24));
    cityT.setMinimumSize(new Dimension(4, 24));
    locCodeT.setPreferredSize(new Dimension(63, 24));
    locCodeT.setMinimumSize(new Dimension(4, 24));
    jLabel9.setText("State:");
    jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel10.setText("Zip:");
    jLabel10.setHorizontalAlignment(SwingConstants.RIGHT);
    zipT.setPreferredSize(new Dimension(63, 24));
    zipT.setMinimumSize(new Dimension(4, 24));
    stateC.setPreferredSize(new Dimension(80, 27));
    stateC.setMinimumSize(new Dimension(60, 27));
    newB.setText("New Address");
    newB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newB_actionPerformed(e);
      }
    });
    deleteB.setText("Delete");
    deleteB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteB_actionPerformed(e);
      }
    });
    saveB.setText("Save");
    saveB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveB_actionPerformed(e);
      }
    });
    myP.add(locC,BorderLayout.CENTER);
    this.setLayout(gridBagLayout1);
    this.add(jLabel1, new GridBagConstraints2(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel2, new GridBagConstraints2(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(jLabel3, new GridBagConstraints2(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel4, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel5, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel6, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel7, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel8, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 14, 0), 0, 0));
    this.add(add1T, new GridBagConstraints2(1, 1, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    this.add(myP, new GridBagConstraints2(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(3, 3, 14, 3), 0, 0));
    this.add(add2T, new GridBagConstraints2(1, 2, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    this.add(add3T, new GridBagConstraints2(1, 3, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    this.add(cityT, new GridBagConstraints2(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    this.add(locCodeT, new GridBagConstraints2(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    this.add(clientC, new GridBagConstraints2(1, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    this.add(jLabel9, new GridBagConstraints2(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 12, 3, 3), 0, 0));
    this.add(jLabel10, new GridBagConstraints2(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(stateC, new GridBagConstraints2(3, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    this.add(zipT, new GridBagConstraints2(3, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    this.add(jPanel1, new GridBagConstraints2(0, 8, 4, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(25, 0, 0, 0), 0, 0));
    jPanel1.add(saveB, null);
    jPanel1.add(newB, null);
    jPanel1.add(deleteB, null);
    this.add(countryC, new GridBagConstraints2(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    ClientHelpObjs.makeDefaultColors(this);
  }

  void goLocation(String s, boolean updating){
  }

  public void loadIt(){
    this.startLoad();
  }

  public void loadThisPanel(){
    String hold = "";
    if(firstTime){
      if(getCmisApp().getGroupMembership().isRadian()){
        clientC.addItem("Radian");
      }
      clientC.addItem(getCmisApp().getResourceBundle().getString("APP_NAME"));
      firstTime = false;
    }else{
     add1T.setText("");
     add2T.setText("");
     add3T.setText("");
     cityT.setText("");
     locCodeT.setText("");
     zipT.setText("");
      if(locC.getSelectedIndex() < 0){
        hold = "";
      }else{
        hold = locC.getSelectedItem().toString();
      }
    }

    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION","LOAD_ALL_LOCATIONS"); //String, String
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
      return;
    }
    if(stateC.getItemCount() < 1) {
      states = (Vector)result.get("STATE_VECTOR");
      stateC = ClientHelpObjs.loadChoiceFromVector(stateC,states);
      stateC.setSelectedIndex(0);
    }

    if(country == null || country.size() < 1){
      country = BothHelpObjs.sort((Vector)result.get("DATA"));
      for(int i=0;i<country.size();i++){
        countryC.addItem(country.elementAt(i).toString());
      }
    }

    Vector v = (Vector)result.get("LOCATIONS");

    locData = BothHelpObjs.get2DArrayFromVector(v,10);
    this.enableButton(true);
    loadLocCombo();
    if(BothHelpObjs.isBlankString(hold)){
      locC.setSelectedIndex(0);
    }else{
      locC.setSelectedItem(hold);
    }
    loadDataFields();
    ClientHelpObjs.setEnabledContainer(this,true);

    if(!getCmisApp().getGroupMembership().isRadian() || !getCmisApp().getGroupMembership().isSuperUser() ) disableNonRadianComps();
    repaint();
  }

  void loadLocCombo(){
    boolean b = false;
    if(locC != null){
      b = locC.isEnabled();
    }
    Vector v = new Vector();
    for(int i=0;i<locData.length;i++){
      v.addElement(locData[i][0].toString());
    }
    v = BothHelpObjs.sort(v);
    myP.remove(locC);
    myP.validate();
    locC = new JComboBox(v);
    locC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        locC_actionPerformed(e);
      }
    });
    myP.add(locC,BorderLayout.CENTER);
    locC.setEnabled(b);
    locC.repaint();
    myP.validate();
    myP.repaint();
  }

  void loadDataFields(){
    int n =0;
    for(int i=0;i<locData.length;i++){
      if(locData[i][0].toString().equals(locC.getSelectedItem().toString())){
        n = i;
        break;
      }
    }
    add1T.setText(locData[n][1].toString());
    add2T.setText(locData[n][2].toString());
    add3T.setText(locData[n][3].toString());
    cityT.setText(locData[n][4].toString());
    stateC.setSelectedItem(locData[n][5].toString());
    if(BothHelpObjs.isBlankString(locData[n][6].toString())){
      countryC.setSelectedItem("USA");
    }else{
      countryC.setSelectedItem(locData[n][6].toString());
    }
    zipT.setText(locData[n][7].toString());
    locCodeT.setText(locData[n][8].toString());
    clientC.setSelectedItem(locData[n][9].toString());
  }

  void goNewLocation(){
    AddLocationDlg ald = new AddLocationDlg(getCmisApp().getMain(),"New Address",true);
    ald.setCmisApp(getCmisApp());
    CenterComponent.centerCompOnScreen(ald);
    ald.setVisible(true);
    if(ald.getCanceled())return;
    String newOne = ald.getNewLocId();
    int nowSize = locData.length;
    Object[][] oa = new Object[nowSize + 1][10];
    for(int i=0;i<nowSize;i++){
      oa[i] = locData[i];
    }
    oa[nowSize] = new Object[]{newOne,"","","","",stateC.getItemAt(0).toString(),"USA","","",getCmisApp().getResourceBundle().getString("APP_NAME")};
    locData = oa;
    loadLocCombo();
    locC.setSelectedItem(newOne);
  }
  void printLocData(){
    System.out.println("locData length:"+locData.length);
    for(int i=0;i<locData.length;i++){
      System.out.println("locID:"+locData[i][0].toString());
    }
  }

  void goSaveLocation(){
    GenericDlg mgd = new GenericDlg(getCmisApp().getMain(),"Update Status",true);
    if(updateLocation("UPDATE_LOCATION")){
      mgd.setMsg("The location was successfully updated.");
    }else{
      mgd.setMsg("The location was NOT updated.");
    }
    mgd.setVisible(true);
    loadIt();
  }

  void goDeleteLocation(){
    ConfirmDlg gd = new ConfirmDlg(getCmisApp().getMain(),"Confirm Deletion",true);
    gd.setMsg("Are you sure you want to delete the "+locC.getSelectedItem().toString()+" address?");
    gd.setVisible(true);
    if(!gd.getConfirmationFlag()){
      return;
    }
    GenericDlg mgd = new GenericDlg(getCmisApp().getMain(),"Deletion Status",true);
    boolean reload = true;
    if(updateLocation("DELETE_LOCATION")){
      mgd.setMsg("The location was successfully deleted.");
    }else if(childFound){
      mgd.setMsg("This location is being used and cannot be deleted.");
      childFound = false;
      reload = false;
    }else{
      mgd.setMsg("The location was NOT deleted.");
    }
    mgd.setVisible(true);
    if(reload){
      locC.setSelectedIndex(-1);
      loadIt();
    }
  }

  boolean updateLocation(String action){
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION",action); //String, String
    query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String

    query.put("LOCATION_ID",locC.getSelectedItem().toString());
    query.put("ADDRESS_1",add1T.getText());
    query.put("ADDRESS_2",add2T.getText());
    query.put("ADDRESS_3",add3T.getText());
    query.put("CITY",cityT.getText());
    query.put("STATE",stateC.getSelectedItem().toString());
    query.put("COUNTRY",countryC.getSelectedItem().toString());
    query.put("ZIP",zipT.getText());
    query.put("CLIENT_LOC_CODE",locCodeT.getText());
    query.put("CLIENT",clientC.getSelectedItem().toString());

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return false;
    }
    Vector v = (Vector)result.get("DATA");
    String s = v.elementAt(0).toString();
    boolean returnVal = false;
    if(s.equalsIgnoreCase("true")){
      returnVal = true;
    }
    if(s.equalsIgnoreCase("child")){
      childFound = true;
    }
    return returnVal;
  }

  void disableNonRadianComps(){
    clientC.setVisible(false);
    locCodeT.setVisible(false);
    jLabel1.setVisible(false);
    jLabel2.setVisible(false);
  }

  void saveB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goSaveLocation();
  }

  void newB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goNewLocation();
  }

  void deleteB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goDeleteLocation();
  }

  void locC_actionPerformed(ActionEvent e) {
    if(locC.getSelectedIndex() < 0)return;
    loadDataFields();
  }
}

