
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.admin;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AddChemApprDlg extends JDialog {
  Vector roles;
  boolean canceled = false;

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  DataJLabel facNameL = new DataJLabel();
  NextNameTextField nameT = new NextNameTextField();
  JButton searchB = new JButton();
  JScrollPane jsp = new JScrollPane();
 //before trong JList roleList = new JList();
  CmisApp cmis;
  String facId;
  javax.swing.JButton addRB = new javax.swing.JButton();
  javax.swing.JButton delRB = new javax.swing.JButton();
  JButton editB = new JButton();


  //trong 3/31
  CmisTable roleList = new CmisTable();

  String[] listTableCols = {"Role","Role Type Desc"};
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();


  public AddChemApprDlg(CmisApp cmis,String facID) {
    super(cmis.getMain(), "Add/Modify New Chemical Approver", true);    //trong 3/27
    this.cmis = cmis;
    nameT.setParent(cmis);
    this.facId = facID;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    setResizable(false);
    panel1.setLayout(gridBagLayout2);
    jPanel1.setLayout(gridBagLayout1);
    okB.setText("Add Approver");
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
    jLabel1.setText("Approver Name:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Facility:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    searchB.setText("Search");
    searchB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchB_actionPerformed(e);
      }
    });

    jsp = new JScrollPane(roleList);
    ImageIcon ss;
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR"); ;
    */
    ss = ResourceLoader.getImageIcon("images/button/ok.gif","ok");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/search.gif","search");
    searchB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif","cancel");
    cancelB.setIcon(ss);
    jsp.setMaximumSize(new Dimension(450, 90));
    jsp.setPreferredSize(new Dimension(450, 90));
    jsp.setToolTipText("");
    jsp.setMinimumSize(new Dimension(450, 90));
    addRB.setText("Add Role");
    addRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addRB_actionPerformed(e);
      }
    });
    delRB.setText("Delete Role");
    editB.setText("Modify Role");
    editB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editB_actionPerformed(e);
      }
    });
    delRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delRB_actionPerformed(e);
      }
    });
    ss = ResourceLoader.getImageIcon("images/button/add.gif","ok");
    addRB.setMaximumSize(new Dimension(120, 27));
    addRB.setMinimumSize(new Dimension(120, 27));
    addRB.setPreferredSize(new Dimension(120, 27));
    addRB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/delete.gif","ok");
    editB.setMaximumSize(new Dimension(120, 27));
    editB.setMinimumSize(new Dimension(120, 27));
    editB.setPreferredSize(new Dimension(120, 27));
    editB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/minus.gif","ok");
    delRB.setMaximumSize(new Dimension(120, 27));
    delRB.setMinimumSize(new Dimension(120, 27));
    delRB.setPreferredSize(new Dimension(120, 27));
    delRB.setIcon(ss);

    panel1.setPreferredSize(new Dimension(600, 300));
    panel1.setMaximumSize(new Dimension(600, 300));
    panel1.setMinimumSize(new Dimension(600, 300));
    jPanel1.setMaximumSize(new Dimension(580, 215));
    jPanel1.setMinimumSize(new Dimension(580, 215));
    jPanel1.setPreferredSize(new Dimension(580, 215));
    nameT.setMaximumSize(new Dimension(150, 27));
    nameT.setMinimumSize(new Dimension(150, 27));
    nameT.setPreferredSize(new Dimension(150, 27));
    getContentPane().add(panel1);
    panel1.add(jPanel1, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(15, 3, 0, 8), 0, 0));

    jPanel1.add(facNameL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(10, 160, 5, 0), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(10, 120, 5, 0), 0, 0));

    jPanel1.add(jLabel1, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(20, 5, 20, 0), 0, 0));
    jPanel1.add(nameT, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(20, 90, 12, 0), 0, 0));
    jPanel1.add(searchB, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(20, 275, 12, 5), 0, 0));

    jPanel1.add(addRB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(30, 5, 15, 5), 0, 0));
    jPanel1.add(editB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(62, 5, 15, 5), 0, 0));
    jPanel1.add(delRB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(94, 5, 0, 5), 0, 0));

    jPanel1.add(jsp, new GridBagConstraints2(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

    panel1.add(jPanel2, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 140, 6, 35), 0, 0));
    jPanel2.add(okB, null);
    jPanel2.add(cancelB, null);

    nameT.requestDefaultFocus();
    ClientHelpObjs.makeDefaultColors(this);
  }

  public void loadIt(){
    AddChemApprLoadThread aclt = new AddChemApprLoadThread(this);
    aclt.start();
  }

  public void loadScreen(){
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","LOAD_ADD_APPROVER"); //String, String
    query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
    query.put("FACILITY_ID",facId);
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      return;
    }
    facNameL.setText(((Vector)result.get("FAC_NAMES")).elementAt(0).toString());
    roles = (Vector)result.get("DATA");

    //trong 3/31
    Vector roleTypeDesc = (Vector)result.get("ROLE_TYPE_DESC");
    Vector myV = new Vector();
    myV.addElement("Role");
    myV.addElement("Role Type Desc");

    DbTableModel ctm = new DbTableModel(listTableCols);
    ctm.setEditableFlag(0);
    for(int i = 0; i < roles.size(); i++){
      Vector temp = new Vector();
      temp.addElement(roles.elementAt(i));
      temp.addElement(roleTypeDesc.elementAt(i));
      ctm.addRow(temp);
    }

    roleList = new CmisTable(ctm);
    roleList.getColumn("Role").setWidth(100);
    roleList.getColumn("Role Type Desc").setWidth(200);
    //roleList.setAutoResizeMode(CmisTable.AUTO_RESIZE_OFF);
    jsp.getViewport().add(roleList, null);

    ClientHelpObjs.setEnabledContainer(this,true);
    repaint();
  }

  public boolean getCanceled(){
    return canceled;
  }
  boolean auditInput(){
    Vector v = nameT.getWhoV();
    if(v==null || v.size() < 3 || BothHelpObjs.isBlankString(v.elementAt(2).toString())){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Please Enter Approver Name",true);
      gd.setMsg("You must enter an approver name.");
      gd.setVisible(true);
      return false;
    }

    int[] ia = roleList.getSelectedRows();     //trong 3/31 .getSelectedIndices();
    if(ia.length < 1) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Please Select a Role",true);
      gd.setMsg("You must select at least one approver role.");
      gd.setVisible(true);
      return false;
    }
    return true;
  }

  void addApprover(){
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","ADD_APPROVER"); //String, String
    query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String

    // roles
    // before trong 3/31 Object[] oa  = roleList.getSelectedValues();
    int[] myRow = new int[30];
    myRow = roleList.getSelectedRows();
    Object[] oa = new Object[30];
    for (int i = 0; i < myRow.length; i++) {
  //System.out.println("-------------------- " + roleList.getModel().getValueAt(myRow[i],0).toString());
      oa[i] = roleList.getModel().getValueAt(myRow[i],0).toString();
    }
  //System.out.println("-------------------- " + oa);


    String vS = new String("");
    for(int i=0;i<myRow.length;i++){
      String tS = oa[i].toString();
  //System.out.println("-------------------- " + tS);
      if (BothHelpObjs.isBlankString(tS))continue;
      vS = vS + (vS.length()>0?BothHelpObjs.VALUE_VALUE_DEL:"") + tS;
    }
    query.put("ROLES",vS);
    query.put("FACILITY_ID",facId);
    query.put("PERSONNEL_ID",nameT.getWhoV().elementAt(2).toString());

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(cmis.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
    }
    Vector v = (Vector) result.get("DATA");

    GenericDlg gd = new GenericDlg(cmis.getMain(),true);
    String title = "";
    String msg = "";

    if(v == null || v.isEmpty() || !v.elementAt(0).toString().equalsIgnoreCase("true")){
      gd.setTitle("Approver Add Failed");
      gd.setMsg("The new Approver was NOT added.");
    }else{
      gd.setTitle("Approver Added");
      gd.setMsg("The new Approver was added.");
    }
    gd.setVisible(true);
    return;
  }

  void goAddRole(){
    AddRoleDlg ard = new AddRoleDlg(cmis.getMain(),"Add Approval Role",true,facId);
    ard.setCmis(cmis);
    ard.setVisible(true);
    if(!ard.getCanceled())loadIt();
  }
  void goModifyRole(){
    //before trong if(roleList.getSelectedIndices().length > 1 || roleList.getSelectedIndices().length < 0){
    if(roleList.getSelectedRows().length > 1 || roleList.getSelectedRows().length < 0){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Select One Role",true);
      gd.setMsg("You must select one role to modify.");
      gd.setVisible(true);
      return;
    }
    AddRoleDlg ard = new AddRoleDlg(cmis.getMain(),"Modify Approval Role",true,facId);
    ard.setCmis(cmis);
    ard.setUpdating(true);
    ard.setRole(roleList.getValueAt(roleList.getSelectedRow(),0).toString());

    //trong
    ard.typeC.setSelectedItem(roleList.getValueAt(roleList.getSelectedRow(),1));

    ard.setVisible(true);
    if(!ard.getCanceled())loadIt();
  }


  void goDeleteRole(){
    // trong if(roleList.getSelectedIndices().length < 1){
    if(roleList.getSelectedRows().length < 1){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"No Role Selected",true);
      gd.setMsg("You must select at least one Approval Role to delete.");
      gd.setVisible(true);
      return;
    }

    ConfirmDlg cd = new ConfirmDlg(cmis.getMain(),"Confirm Deletion",true);
    cd.setMsg("Do you really want to delete selected roles?");
    cd.setVisible(true);
    if(!cd.getConfirmationFlag())return;

    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","DELETE_ROLE"); //String, String
    query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
    query.put("FACILITY_ID",facId);

    // Roles
    // trong Object[] myRoles = roleList.getSelectedValues();
    int[] myRow = new int[30];
    myRow = roleList.getSelectedRows();
    Object[] myRoles = new Object[30];
    for (int i = 0; i < myRow.length; i++) {
      myRoles[i] = roleList.getValueAt(myRow[i],0);
    }


    String vS = "";
    for(int i=0;i<myRow.length;i++){
      String tS = myRoles[i].toString();
      if (BothHelpObjs.isBlankString(tS))continue;
      vS = vS + (vS.length()>0?BothHelpObjs.VALUE_VALUE_DEL:"") + tS;
    }
    query.put("ROLES",vS);

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      return;
    }
    Vector v = (Vector) result.get("DATA");

    GenericDlg gd = new GenericDlg(cmis.getMain(),true);
    if(v == null || v.isEmpty()){
      gd.setTitle("Role Deletion Failed");
      gd.setMsg("The Roles were NOT deleted.");
    }else if(!v.contains("false")){
      gd.setTitle("Role Deletion Successful");
      gd.setMsg("All selected Roles were deleted.");
    }else{
      gd.setTitle("Role Deletion Status");
      String msg = "The following roles cannot be deleted: ";
      for(int i=0;i<v.size();i++){
        msg = msg + myRoles[i].toString() + ", "  ;
      }
      msg = msg.substring(0,msg.length() -2) + ".";
      gd.setMsg(msg);
    }
    gd.setVisible(true);
    loadIt();
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    SearchPersonnel dlg = new SearchPersonnel(cmis.getMain(),"Search",true);
    dlg.setGrandParent(cmis);
    dlg.setPersonType("PERSONNEL");
    dlg.setVisible(true);
    if (dlg.getValueId() != null){
      nameT.setUpdating();
      nameT.setText(dlg.getValueLast()+", "+dlg.getValueFirst());
      Vector v = new Vector();
      v.addElement("first Name");
      v.addElement("last Name");
      v.addElement(dlg.getValueId());
      nameT.setWhoV(v);
    }
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(!auditInput()) return;

    addApprover();
    setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    canceled = true;
    setVisible(false);
  }

  void delRB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goDeleteRole();
  }

  void addRB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goAddRole();
  }

  void editB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goModifyRole();
  }

}

class AddChemApprLoadThread extends Thread {
  AddChemApprDlg parent;
  public AddChemApprLoadThread(AddChemApprDlg parent){
     super("AdminChemLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadScreen();
  }
}

