
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
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class AdminNewChemPanel extends AdminInputPanel { //JPanel { //
  GridBagLayout gbl = new GridBagLayout();
  GridBagLayout gb2 = new GridBagLayout();
  GridBagLayout gb3 = new GridBagLayout();
  GridBagLayout gb4 = new GridBagLayout();
  GridBagLayout gb5 = new GridBagLayout();
  FacilityCombo facC = new FacilityCombo();
  StaticJLabel facL = new StaticJLabel();
  JButton addB = new JButton();
  JButton delB = new JButton();
  boolean facCLoaded = false;
  boolean roleInfoLoaded = false;
  Vector approvalRoles;
  Vector facXRole;
  JPanel holderP = new JPanel();
  JScrollPane jsp = new JScrollPane();
  Object[][] approverInfo;
  BorderLayout borderLayout1 = new BorderLayout();
  JTable myTable;
  boolean reloading = false;
  String[] colHead = new String[]{"Role","Name"};
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();

  public AdminNewChemPanel(AdminWin aw) {
    super(aw,"New Chemical Approval");
    try  {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
       /*
  public void setVisible(boolean b){
    if(b){
      loadTable();
    }
    super.setVisible(b);
  }  */

  void jbInit() throws Exception {
    this.setLayout(gbl);
    holderP.setLayout(new BorderLayout());

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/search_small.gif","Search");


    facC.setUseAllFacilities(false);
    facC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        facC_actionPerformed(e);
      }
    });

    facL.setText("Facility:");
    facL.setHorizontalAlignment(SwingConstants.RIGHT);
    addB.setText("Add/Modify");     //trong 3/27
    addB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });
    delB.setText("Delete");
    delB.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        delB_actionPerformed(e);
      }
    });

    addB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));
    delB.setIcon(ResourceLoader.getImageIcon("images/button/minus.gif"));

    jPanel1.add(facL);
    jPanel1.add(facC);
    jPanel2.add(addB);
    jPanel2.add(delB);
    addB.setPreferredSize(new Dimension(130,29));
    delB.setPreferredSize(new Dimension(130,29));

    add(holderP, new GridBagConstraints2(0, 1, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(15, 15, 15, 15), 0, 0));
    add(jPanel1, new GridBagConstraints2(0, 0, 5, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(15, 0, 0, 0), 0, 0));
    add(jPanel2, new GridBagConstraints2(0, 2, 5, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 4, 0), 0, 0));
    validate();

    ClientHelpObjs.makeDefaultColors(this);
  }

  public synchronized void setFacCombo(FacilityCombo fc){
    int i=0;
    while (i<1){
      facC.setCmisApp(getCmisApp());
      facC.setUseAllFacilities(false);
      facC.setUseAllWorkAreas(false);
      facC.restrictToAdminFacilities(!getCmisApp().getGroupMembership().isSuperUser());
      facC = fc.loadAnotherOne(facC);
      //FacilityCombo temp = fc.loadAnotherOne(facC);
      //facC = temp;
      i++;
      //System.out.println("super facC size:"+fc.getItemCount());
      //System.out.println("this facC size:"+facC.getItemCount());
      if (facC.getItemCount()>0){
        try {
            if (facC.getSelectedIndex()<0) facC.setSelectedIndex(0);
            facCLoaded = true;
            break;
        } catch (Exception e) {
            continue;
        }
      }
    }
    comboLoaded();
  }

  void comboLoaded(){
    if(facCLoaded && roleInfoLoaded) {
      loadTable();
      this.enableButton(true);
    }
  }

  public void loadIt(){
    this.startLoad();
  }
  public void loadThisPanel() {
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION","LOAD_APPROVAL_ROLES"); //String, String
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
    approvalRoles = (Vector)result.get("APP_NAMES");
    facXRole = (Vector)result.get("FAC_X_APP");
    Vector tableData = (Vector)result.get("DATA");

    //System.out.println("=========== approvalroles: "+approvalRoles+" facXrole: "+facXRole+" tableData: "+tableData+" size "+tableData.size());

    approverInfo = BothHelpObjs.get2DArrayFromVector(tableData,4);

    //System.out.println("=========== got here:");
    //loadTable();

    roleInfoLoaded = true;
    comboLoaded();

    if(reloading) {
      loadTable();
      reloading = false;
    }

    ClientHelpObjs.setEnabledContainer(this,true);
    repaint();
  }
  void facC_actionPerformed(ActionEvent e) {
    if (!facCLoaded) return;
    loadTable();
  }


  void loadTable(){
    holderP.removeAll();
    jsp.removeAll();
    Object[][] oa = new Object[0][0];
    boolean noData = false;
    if(approverInfo == null) {
      noData = true;
    }else{
      //System.out.println("here");
      //System.out.println(facC.getItemCount());
      int x=0;
      try {
        //Thread.currentThread().sleep(2000);
        //System.out.flush();
        //if(facC.getSelectedIndex() < 0) facC.setSelectedIndex(0);
        for(int i=0;i<approverInfo.length;i++){
          if(approverInfo[i][0].toString().equalsIgnoreCase(facC.getSelectedItem().toString())){
            x++;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      };
      //System.out.println("there");
      if(x==0){
        noData = true;
      }else{
        int r=0;
        oa = new Object[x][2];
        for(int i=0;i<approverInfo.length;i++){
          if(approverInfo[i][0].toString().equalsIgnoreCase(facC.getSelectedItem().toString())){
            for(int n=1;n<3;n++){
              oa[r][n-1] = approverInfo[i][n];
            }
            r++;
          }
        }
      }
    }
    CmisTableModel dtm;
    if(noData){
      dtm = new CmisTableModel(colHead);
    }else{
      dtm = new CmisTableModel(colHead,oa);
    }
    dtm.setEditableFlag(0);
    TableSorter sorter = new TableSorter(dtm);
    sorter.setColTypeFlag("11");
    myTable = new JTable(sorter);
    myTable.setCellSelectionEnabled(false);
    sorter.addMouseListenerToHeaderInTable(myTable);
    jsp = new JScrollPane(myTable);
    holderP.add(jsp,BorderLayout.CENTER);
    myTable.validate();
    jsp.validate();
    holderP.validate();
    this.validate();
    repaint();
  }

  void showConfirm(String title, String msg){
    GenericDlg gd = new GenericDlg(getCmisApp().getMain(),title,true);
    gd.setMsg(msg);
    gd.setVisible(true);
  }


  boolean doAddDeleteAction(String action, String facId, String role, String pid){
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION",action); //String, String
    query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String
    query.put("PERSONNEL_ID",pid);
    query.put("FACILITY_ID",facId);
    query.put("ROLE",role);
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      return false;
    }
    Vector v = (Vector) result.get("DATA");
    if(v == null || v.isEmpty() || !v.elementAt(0).toString().equalsIgnoreCase("true")) return false;
    return true;
  }

  boolean deleteApprovers(){
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.ADMIN,getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION","DELETE_APPROVER"); //String, String
    query.put("USERID",(new Integer(getCmisApp().getUserId().intValue())).toString()); //String, String

    String pid = "";
    String facID = "";
    String role = "";

    int[] ia = myTable.getSelectedRows();
    for(int i=0;i<ia.length;i++){
      facID = facID + BothHelpObjs.VALUE_VALUE_DEL + facC.getSelectedFacId();
      role = role + BothHelpObjs.VALUE_VALUE_DEL + myTable.getModel().getValueAt(ia[i],myTable.getColumn(colHead[0]).getModelIndex());
      pid = pid + BothHelpObjs.VALUE_VALUE_DEL + getPID(ia[i]);
    }
    query.put("PERSONNEL_IDS",pid);
    query.put("FAC_IDS",facID);
    query.put("ROLES",role);

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      return false;
    }
    Vector v = (Vector) result.get("DATA");
    if(v == null || v.isEmpty() || !v.elementAt(0).toString().equalsIgnoreCase("true")) return false;
    return true;
  }

  String getPID(int r){
    for(int i=0;i<approverInfo.length;i++){
      if(approverInfo[i][0].toString().equals(facC.getSelectedFacId()) &&
         approverInfo[i][1].toString().equals(myTable.getModel().getValueAt(r,myTable.getColumn(colHead[0]).getModelIndex()).toString()) &&
         approverInfo[i][2].toString().equals(myTable.getModel().getValueAt(r,myTable.getColumn(colHead[1]).getModelIndex()).toString()) ){
         return approverInfo[i][3].toString();
      }
    }
    return "0";
  }

  void addB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    AddChemApprDlg acad = new AddChemApprDlg(getCmisApp(),facC.getSelectedFacId());
    acad.loadIt();
    CenterComponent.centerCompOnComp(getCmisApp().getMain(),acad);
    acad.setVisible(true);
    if(!acad.getCanceled()){
      reloading = true;
      loadIt();
    }
  }

  void delB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    int q = myTable.getSelectedRowCount();
    if(q<1){
      GenericDlg gd = new GenericDlg(getCmisApp().getMain(),"Please Select Approver Name",true);
      gd.setMsg("You must select an approver.");
      gd.setVisible(true);
      return;
    }
    ConfirmDlg cd = new ConfirmDlg(getCmisApp().getMain(),"Delete Chemical Approver",true);
    cd.setMsg("Do you want to delete the selected chemical approvers?");
    cd.setVisible(true);
    if(cd.getConfirmationFlag()){
      GenericDlg gd = new GenericDlg(getCmisApp().getMain(),true);
      gd.setTitle("Approver Deletion Results");
      String msg = "";
      if(deleteApprovers()){
        msg = "The approvers were successfully deleted.";
      }else{
        msg = "The approvers were NOT deleted.";
      }
      gd.setMsg(msg);
      gd.setVisible(true);
      reloading = true;
      loadIt();
    }
  }

}

