
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
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.util.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class AdminUseApprovalGroupPanel extends AdminInputPanel { //JPanel { //
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  FacilityCombo facC = new FacilityCombo();
  JComboBox groupDescC = new JComboBox();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton addUserB = new JButton();
  JButton delUserB = new JButton();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel jPanel3 = new JPanel();
  JButton addGrpB = new JButton();
  JButton editGrpB = new JButton();
  JButton delGrpB = new JButton();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  JPanel jPanel4 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel jPanel5 = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();
  boolean facCLoaded = false;
  boolean dbDataLoaded = false;
  boolean adminGroups = false;
  boolean loadingGroupCombo;
  JList myList;
  WaitingDlg wd;

  Object[][] groupData;
  Object[][] facGroupMembers;
  Object[][] tableData;
  final String[] colHead = new String[] {"Name"};
  JTable myTable;

  public AdminUseApprovalGroupPanel(AdminWin aw,boolean adminGroups) {
    super(aw,adminGroups?"Administrative Groups":"Use Approval Groups");
    try  {
      this.adminGroups = adminGroups;
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    facC.setPreferredSize(new Dimension(110, 27));
    facC.setMinimumSize(new Dimension(110, 27));
    //facC.setCmisApp(getCmisApp());
    facC.setUseAllFacilities(adminGroups);
    facC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        facC_actionPerformed(e);
      }
    });
    groupDescC.setMaximumSize(new Dimension(160, 27));
    groupDescC.setPreferredSize(new Dimension(160, 27));
    groupDescC.setMinimumSize(new Dimension(160, 27));
    jPanel1.setMaximumSize(new Dimension(200, 200));
    jPanel1.setPreferredSize(new Dimension(200, 200));
    groupDescC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        groupDescC_actionPerformed(e);
      }
    });

    jLabel1.setText("Members:");
    jLabel2.setText("Group Description:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Facility:");
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel1.setLayout(borderLayout1);
    jPanel2.setLayout(verticalFlowLayout1);
    addUserB.setText("Add");
    addUserB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addUserB_actionPerformed(e);
      }
    });
    delUserB.setText("Delete");
    delUserB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delUserB_actionPerformed(e);
      }
    });
    jPanel3.setPreferredSize(new Dimension(106, 120));
    jPanel3.setMinimumSize(new Dimension(106, 120));
    jPanel3.setLayout(verticalFlowLayout2);
    addGrpB.setText("Add");
    addGrpB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addGrpB_actionPerformed(e);
      }
    });
    editGrpB.setText("Edit");
    editGrpB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editGrpB_actionPerformed(e);
      }
    });
    delGrpB.setText("Delete");
    delGrpB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delGrpB_actionPerformed(e);
      }
    });
    jPanel5.setLayout(gridBagLayout2);
    jPanel4.setLayout(gridBagLayout1);
    this.setLayout(gridBagLayout3);
    ClientHelpObjs.makeDefaultColors(this);

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    jPanel2.setMaximumSize(new Dimension(140, 130));
    jPanel2.setPreferredSize(new Dimension(136, 120));
    addUserB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));
    delUserB.setIcon(ResourceLoader.getImageIcon("images/button/minus.gif"));
    addGrpB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));
    delGrpB.setIcon(ResourceLoader.getImageIcon("images/button/minus.gif"));
    editGrpB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif"));

    addUserB.setPreferredSize(new Dimension(125,29));
    delUserB.setPreferredSize(new Dimension(125,29));
    addGrpB.setPreferredSize(new Dimension(125,29));
    delGrpB.setPreferredSize(new Dimension(125,29));
    editGrpB.setPreferredSize(new Dimension(125,29));

    validate();
    this.add(jPanel4, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 20), 25, 3));
    jPanel4.add(groupDescC, new GridBagConstraints2(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(6, 3, 35, 3), 0, 0));
    jPanel4.add(jLabel2, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(12, 0, 0, 3), 0, 0));
    jPanel4.add(jLabel3, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 11, 3), 0, 0));
    jPanel4.add(facC, new GridBagConstraints2(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 5, 4), 0, 0));
    jPanel4.add(jPanel3, new GridBagConstraints2(2, 0, 1, 2, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 3, 20, 60), 0, 0));
    jPanel3.add(addGrpB, null);
    jPanel3.add(editGrpB, null);
    jPanel3.add(delGrpB, null);
    this.add(jPanel5, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 20, 1, 20), 0, 179));
    jPanel5.add(jPanel1, new GridBagConstraints2(1, 0, 1, 3, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(3, 3, 10, 3), 0, 0));
    jPanel1.add(jsp, BorderLayout.CENTER);
    jPanel5.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(12, 15, 0, 0), 0, 0));
    jPanel5.add(jPanel2, new GridBagConstraints2(2, 0, 1, 2, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 12, 3), 0, 0));
    jPanel2.add(addUserB, null);
    jPanel2.add(delUserB, null);
    ClientHelpObjs.makeDefaultColors(this);
  }


  public void loadIt(){
    //this.startLoad();
  }

  public void setVisible(boolean b){
    super.setVisible(b);
    if(b && !dbDataLoaded){
      this.startLoad();
      wd = new WaitingDlg(getCmisApp().getMain(),"Loading","Loading User Group Information");
      CenterComponent.centerCompOnScreen(wd);
      wd.setVisible(true);
    }
  }


  public void loadThisPanel(){
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    String action = adminGroups?"LOAD_ADMIN_GROUP_INFO":"LOAD_USE_APPROVAL_GROUP_INFO";
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
      return;
    }
    Vector g = (Vector)result.get("DATA");
    if(g.size() > 1){
      groupData = BothHelpObjs.get2DArrayFromVector(g,3);
    }else{
      groupData = new Object[0][0];
    }

    Vector m = (Vector)result.get("OUT_DATA");
    if(m.size() > 1){
      facGroupMembers = BothHelpObjs.get2DArrayFromVector(m,4);
    }else{
      facGroupMembers = new Object[0][0];
    }
    /*if(adminGroups){
    for(int i=0;i<facGroupMembers.length;i++){
      for(int j=0;j<facGroupMembers[i].length;j++){
        System.out.print(":"+facGroupMembers[i][j].toString());
      }
      System.out.println("");
    }
    } */
    dbDataLoaded = true;
    ClientHelpObjs.setEnabledContainer(this,true);
    this.revalidate();
    //loadDone();
    loadGroupCombo();
    if(wd != null){
      wd.setVisible(false);
      wd = null;
    }
    repaint();
  }

  void loadTable(){
    delUserB.setEnabled(false);
    String groupID = getSelectedGroupID();
    Vector v = new Vector();
    Vector v1 = new Vector();
      for(int i=0;i<facGroupMembers.length;i++){
        if(facC.getSelectedFacId().equals(facGroupMembers[i][1].toString())){
          if(facGroupMembers[i][2].toString().equals(groupID)){
            v.addElement(facGroupMembers[i][0]);
            v1.addElement(facGroupMembers[i][0]);
            v.addElement(facGroupMembers[i][3]);
          }
        }
      }
    if(v.size() < 1){
      tableData = null;
    }else{
      tableData = BothHelpObjs.get2DArrayFromVector(v,2);
    }
    jPanel1.remove(jsp);
    v1 = BothHelpObjs.sort(v1);
    myList = new JList(v1);
    myList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    myList.addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e) {
        delUserB.setEnabled(myList.getSelectedIndices().length > 0);
      }
    });
    jsp = new JScrollPane(myList);
    jPanel1.add(jsp, BorderLayout.CENTER);
    jPanel1.validate();
    repaint();
  }

  boolean doDeleteGroup(){
    boolean retVal = false;
    ConfirmDlg cd = new ConfirmDlg(getCmisApp().getMain(),"Confirm Group Delete",true);
    cd.setMsg("You are about to delete the group \""+groupDescC.getSelectedItem().toString()+"\" and all it's members?");
    cd.setVisible(true);
    if(!cd.getConfirmationFlag())return false;

    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
      Hashtable query = new Hashtable();
      query.put("ACTION","DELETE_GROUP");
      query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String
      query.put("GROUP_NAME",getSelectedGroupID());
      query.put("GROUP_DESC",groupDescC.getSelectedItem().toString());
      query.put("FACILITY_ID",facC.getSelectedFacId());
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         GenericDlg.showAccessDenied(getCmisApp().getMain());
         return retVal;
      }
      Vector info = (Vector) result.get("DATA");
      String yesNo = (String)info.elementAt(0);
      GenericDlg gd = new GenericDlg(getCmisApp().getMain(),"Group Status",true);
      if(yesNo.equalsIgnoreCase("true")) {
        gd.setMsg("The group was deleted.");
        retVal = true;
      }else if(yesNo.equalsIgnoreCase("child")){
        gd.setMsg("The group can not be deleted because it is still in use.");
        retVal = true;
      }else{
        gd.setMsg("The group was not deleted.");
      }
      gd.show();
    } catch (Exception ie) {
      //getCmisApp().getMain().stopImage();
      ie.printStackTrace();}
    return retVal;
  }

  void loadGroupCombo(){
    loadingGroupCombo = true;
    Vector v = new Vector();
    for(int i=0;i<groupData.length;i++){
      if(groupData[i][0].toString().equalsIgnoreCase(facC.getSelectedFacId())){
        v.addElement(groupData[i][2].toString());
      }
    }
    v = BothHelpObjs.sort(v);
    if(groupDescC.getItemCount() > 0){
      groupDescC.removeAllItems();
      groupDescC.revalidate();
    }
    groupDescC = ClientHelpObjs.loadChoiceFromVector(groupDescC,v);
    groupDescC.revalidate();
    boolean b = groupDescC.getItemCount() > 0;
    if(b){
      groupDescC.setSelectedIndex(0);
      groupDescC.revalidate();
    }

    editGrpB.setEnabled(b);
    delGrpB.setEnabled(b);
    addUserB.setEnabled(b);
    addGrpB.setEnabled(true);
    loadingGroupCombo = false;
    loadTable();
      }

   String getSelectedGroupID(){
    if(groupDescC.getItemCount() == 0)return "";
    String s = groupDescC.getSelectedItem().toString();
    for(int i=0;i<groupData.length;i++){
      if(groupData[i][2].toString().equals(s)){
        return groupData[i][1].toString();
      }
    }
    return "";
  }

  public synchronized void setFacCombo(FacilityCombo fc){
    if(adminGroups && !getCmisApp().getGroupMembership().isSuperUser()){
      this.adminWin.removeButton(this.aipButton);
      return;
    }
    facC.setCmisApp(getCmisApp());
    facC.restrictToAdminFacilities(!getCmisApp().getGroupMembership().isSuperUser());
    fc.loadAnotherOne(facC);
    facC.setSelectedIndex(0);
    facCLoaded = true;
    loadDone();
  }

  void loadDone(){
    //if(facCLoaded && dbDataLoaded){
      //loadGroupCombo();
      this.enableButton(true);
    //}
  }

  void goUpdateGroup(boolean updating){
    String id = "";
    String desc = "";
    String title = "Use Approval Group";
    if(updating){
      title = "Editing "+title;
      id = this.getSelectedGroupID();
      desc = groupDescC.getSelectedItem().toString();
    }else{
      title = "Adding "+title;
    }
    String type = adminGroups?"Admin":"Approval";
    AddUseApprovalGroupDlg a = new AddUseApprovalGroupDlg(this.getCmisApp().getMain(),title,true,getCmisApp(),facC.getSelectedFacId(),facC.getSelectedItem().toString(),id,desc,type,updating);
    CenterComponent.centerCompOnScreen(a);
    a.setVisible(true);
    if(a.changeMade()){
      if(adminGroups){
        facGroupMembers = null;
      }
      loadIt();
    }
  }

  boolean goAddDeleteUsers(boolean adding){
    Vector ids = new Vector();
    if(adding){
      SearchPersonnelMult spm = new SearchPersonnelMult(getCmisApp().getMain(),"Select New Group Members",true);
      //spm.setFacility(facC.getSelectedFacId());
      spm.setCmis(getCmisApp());
      CenterComponent.centerCompOnScreen(spm);
      spm.setVisible(true);
      if(spm.getCanceled())return false;
      Hashtable h = spm.getSelections();
      Enumeration e = h.keys();
      while(e.hasMoreElements()){
        ids.addElement(e.nextElement().toString());
      }
    }else{
      ConfirmDlg gd = new ConfirmDlg(getCmisApp().getMain(),"Confirm Deletion",true);
      gd.setMsg("Do you want to delete the selected members from the group?");
      gd.setVisible(true);
      if(!gd.getConfirmationFlag())return false;
      ids = getSelectedIds();
    }
    if(ids.size() < 1)return false;

    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION","ADD_DEL_GROUP_MEMBERS"); //String, String
    query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String
    String s = "";
    if(adding){
      s = "ADD";
    }else{
      s = "DEL";
    }
    query.put("FUNCTION",s); //String, String
    query.put("FACILITY_ID",facC.getSelectedFacId());
    query.put("GROUP_NAME",getSelectedGroupID());

    String PIDS = "";
    for(int i=0;i<ids.size();i++){
      PIDS = PIDS + BothHelpObjs.VALUE_VALUE_DEL + ids.elementAt(i).toString();
    }
    query.put("PERSONNEL_IDS",PIDS);
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return false;
    }
    Vector v1 = (Vector)result.get("DATA");
    String s1 = v1.elementAt(0).toString();
    String msg = "";
    String title = "";
    if(s1.equals("true")){
      if(adding){
        msg = "Group member add function was completed successfully.";
        title = "Add Results";
      }else{
        msg = "Group member delete function was completed successfully.";
        title = "Deletion Results";
      }
    }else{
      if(adding){
        msg = "Group member add function was NOT completed.";
        title = "Add Results";
      }else{
        msg = "Group member delete function was NOT completed.";
        title = "Deletion Results";
      }
    }
    GenericDlg gd = new GenericDlg(getCmisApp().getMain(),title,true);
    gd.setMsg(msg);
    gd.setVisible(true);
    return true;
  }


  Vector getSelectedIds(){
    Vector v = new Vector();
    Object[] oa = myList.getSelectedValues();
    for(int i=0;i<oa.length;i++){
      for(int j=0;j<tableData.length;j++){
        if(tableData[j][0].toString().equals(oa[i].toString())){
          v.addElement(tableData[j][1].toString());
        }
      }
    }
    return v;
  }

  void facC_actionPerformed(ActionEvent e) {
    if(!facCLoaded)return;
    loadGroupCombo();
  }

  void groupDescC_actionPerformed(ActionEvent e) {
    if(loadingGroupCombo)return;
    loadTable();
  }

  void addGrpB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goUpdateGroup(false);
  }

  void editGrpB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goUpdateGroup(true);
  }

  void delGrpB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(doDeleteGroup()) loadIt();
  }

  void addUserB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(goAddDeleteUsers(true))loadIt();
  }

  void delUserB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(goAddDeleteUsers(false))loadIt();
  }

}

