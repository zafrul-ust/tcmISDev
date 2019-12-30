// This snippet creates a new dialog box
// with buttons on the bottom.

//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:

package  radian.tcmis.client.share.gui.admin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.*;
import java.util.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AddApproverDlg extends JDialog {
  CmisApp cmis;
  Hashtable approvers;
  AdminApprover myApprover;
  String facName;
  String facId;
  boolean adding = true;
  boolean canceled = true;
  boolean changed = false;

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton button1 = new JButton();
  JButton button2 = new JButton();
  Border border1;
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridLayout gridLayout1 = new GridLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel facL = new DataJLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  StaticJLabel jLabel2 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  StaticJLabel jLabel4 = new StaticJLabel();
  JComboBox limitC = new JComboBox();
  JComboBox superC = new JComboBox();
  NextNameTextField approverT = new NextNameTextField("PERSONNEL");
  JButton searchB = new JButton();
  boolean moveChildren = true;

  final String SELECT_COST = "Select or Enter a Order Limit";
  final String SELECT_SUPER = "Select a Super Approver";
  final String SELECT_NONE = "None";

  StaticJLabel approvalLimitL = new StaticJLabel();
  JComboBox approvalLimitC = new JComboBox();
  final String SELECT_APPROVAL = "Select or Enter a Approval Limit";
  String currency = "";

  public AddApproverDlg(CmisApp cmis,Vector approverV,String facName,String facId, String currency){
    super(cmis.getMain(), "Approver Information", true);
    this.cmis = cmis;
    buildApproverHash(approverV);
    this.facName = facName;
    this.facId = facId;
    this.currency = currency;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  public void setCurrency(String s) {
    currency = s;
  } //end of method

  public void setApprover(AdminApprover aa, String action){
    //System.out.println("---------- action: "+action);
    if (action.equalsIgnoreCase("update")) {
      adding = false;
      searchB.setEnabled(false);
      myApprover = aa;
      approverT.setText(myApprover.getName());
      approverT.setEditable(false);
      Vector v = new Vector();
      v.addElement(myApprover.getName());
      v.addElement("");
      v.addElement(new Integer(myApprover.getId()).toString());
      approverT.setWhoV(v);
      limitC.setSelectedItem(aa.getFormatedCostLimit());
      approvalLimitC.setSelectedItem((aa.getFormatedapprovalLimit()));
    }
    try{
      superC.removeItem(myApprover.toString());
    }catch(Exception e){}
    try{
      if (action.equalsIgnoreCase("update")) {
        superC.setSelectedItem(getAdminApprover(aa.getSuperId()).toString());
      }else {
        AdminApprover tmpA = getAdminApprover(aa.getId());
        if (tmpA != null) {
          //admin selected an approver - add children to selected approver
          superC.setSelectedItem(tmpA.toString());
          //superC.setSelectedItem(getAdminApprover(aa.getId()).toString());
        }else {
          //admin selected the facility level - add new unlimited approver
          //first set approval limit combo box to unlimited and freeze this drop down
          approvalLimitC.addItem("Unlimited");
          approvalLimitC.setSelectedItem("Unlimited");
          approvalLimitC.setEnabled(false);
          //next set Next Level to None
          //try{superC.removeItem(SELECT_NONE);}catch(Exception ex){}
          //superC.addItem(SELECT_NONE);
          superC.setSelectedItem(SELECT_NONE);
        }
        //always freeze Next Level approver when add new user
        superC.setEnabled(false);
      }
    }catch(Exception e){superC.setSelectedIndex(0);}
  }

  private void jbInit() throws Exception {
    approverT.setParent(cmis);
    facL.setText(facName);
    buildComboBoxes();

    border1 = BorderFactory.createRaisedBevelBorder();
    jPanel1.setLayout(gridLayout1);
    panel2.setBorder(border1);
    panel2.setLayout(gridBagLayout2);
    button1.setText("OK");
    button1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        button1_actionPerformed(e);
      }
    });
    button2.setText("Cancel");
    button2.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        button2_actionPerformed(e);
      }
    });

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    button1.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    button2.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/search_small.gif");
    searchB.setIcon(ss);

    gridLayout1.setHgap(4);
    panel1.setLayout(gridBagLayout1);
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Facility:");
    facL.setMaximumSize(new Dimension(150, 19));
    facL.setMinimumSize(new Dimension(150, 19));
    facL.setPreferredSize(new Dimension(150, 19));
    jLabel2.setText("User Name:");
    if (currency.length() > 0) {
      jLabel3.setText("User Order Limit ("+currency+"):");
      jLabel4.setText("Next Level ("+currency+"):");
      approvalLimitL.setText("User Approval Limit ("+currency+"):");
    }else {
      jLabel3.setText("User Order Limit:");
      jLabel4.setText("Next Level:");
      approvalLimitL.setText("User Approval Limit:");
    }
    approverT.setMaximumSize(new Dimension(200, 19));
    approverT.setMinimumSize(new Dimension(200, 19));
    approverT.setPreferredSize(new Dimension(200, 19));
    searchB.setMaximumSize(new Dimension(35, 19));
    searchB.setMinimumSize(new Dimension(35, 19));
    searchB.setPreferredSize(new Dimension(35, 19));
    searchB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchB_actionPerformed(e);
      }
    });
    limitC.setEditable(true);
    limitC.setMaximumSize(new Dimension(200, 21));
    limitC.setMinimumSize(new Dimension(200, 21));
    limitC.setPreferredSize(new Dimension(200, 21));
    approvalLimitC.setEditable(true);
    approvalLimitC.setMaximumSize(new Dimension(200, 21));
    approvalLimitC.setMinimumSize(new Dimension(200, 21));
    approvalLimitC.setPreferredSize(new Dimension(200, 21));
    superC.setMaximumSize(new Dimension(200, 21));
    superC.setMinimumSize(new Dimension(200, 21));
    superC.setPreferredSize(new Dimension(200, 21));
    panel1.setMaximumSize(new Dimension(400, 270));
    panel1.setMinimumSize(new Dimension(400, 270));
    panel1.setPreferredSize(new Dimension(400, 270));
    panel1.add(panel2, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints2.CENTER, GridBagConstraints2.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    panel2.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(facL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(jLabel2, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(approverT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(searchB, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(jLabel3, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(limitC,   new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(approvalLimitL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(approvalLimitC,  new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(jLabel4, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(superC,  new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    panel1.add(jPanel1, new GridBagConstraints2(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints2.CENTER, GridBagConstraints2.NONE, new Insets(4, 8, 4, 8), 0, 0));
    jPanel1.add(button1, null);
    jPanel1.add(button2, null);
    getContentPane().add(panel1);
  }

  boolean goAddUpdate(){
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",cmis);
    Hashtable query = new Hashtable();
    String action = "UPDATE_APPROVER";
    query.put("ADMIN_ID",cmis.getUserId());
    query.put("APPROVER_ID",approverT.getPersonnelId());
    query.put("ACTION",action); //String, String
    query.put("FAC_ID",facId); //String, String
    query.put("COST_LIMIT",limitC.getSelectedItem().toString());
    query.put("APPROVAL_LIMIT",approvalLimitC.getSelectedItem().toString());
    query.put("MOVE_CHILDREN",new Boolean(moveChildren));
    String sId = "";
    if(!superC.getSelectedItem().toString().equalsIgnoreCase(SELECT_SUPER) && !superC.getSelectedItem().toString().equalsIgnoreCase(SELECT_NONE)){
      AdminApprover myAa = getAdminApprover(superC.getSelectedItem().toString());
      sId = new Integer(myAa.getId()).toString();
    }
    query.put("SUPER_APPROVER",sId);
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return false;
    }

    String status = result.get("STATUS").toString();
    String msg = result.get("MSG").toString();

    boolean retVal = true;
    if(status.equalsIgnoreCase("no")){
      retVal = false;
    }
    GenericDlg gd = new GenericDlg(cmis.getMain(),"Approver",true);
    gd.setMsg(msg);
    CenterComponent.centerCompOnScreen(gd);
    gd.setVisible(true);
    ClientHelpObjs.setEnabledContainer(this,true);
    return retVal;
  }

  boolean auditData(){
    //first audit order limit
    if (!auditOrderLimit()) {
        return false;
    }
    //next audit approval limit
    if (!auditApprovalLimit()) {
      return false;
    }
    return true;
  }

  boolean auditOrderLimit() {
    String msg = "";
    if(limitC.getSelectedItem().toString().equals(SELECT_COST)){
      msg = "You must select a order limit.";
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg(msg);
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return false;
    }else {
      //otherwise, make sure the entered limit is valid
      if (!"unlimited".equalsIgnoreCase(limitC.getSelectedItem().toString())) {
        String tmpOrderLimit = this.getSelectedUserOrderApprovalLimit(limitC.getSelectedItem().toString());
        //make sure that approval limit is a valid number
        Float tmpLimit = new Float(0);
        try{
          tmpLimit = new Float(tmpOrderLimit);
        }catch(Exception e){
          msg = "Please enter only numbers in order limit.";
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg(msg);
          CenterComponent.centerCompOnScreen(gd);
          gd.setVisible(true);
          return false;
        }
      }
    }
    return true;
  } //end of method

  boolean auditApprovalLimit() {
    String msg = "";
    if (approvalLimitC.getSelectedItem().toString().equals(SELECT_APPROVAL)){
      msg = "You must select a approval limit.";
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg(msg);
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return false;
    }else {
      //otherwise, check to make sure that user need approver or not
      //if approval limit is not unlimited than user require an approver
      if (!approvalLimitC.getSelectedItem().toString().equalsIgnoreCase("unlimited")) {
        if(superC.getSelectedItem().toString().equals(SELECT_NONE) || superC.getSelectedItem().toString().equals(SELECT_SUPER)){
          msg = "You must select a Next Level approver.";
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg(msg);
          CenterComponent.centerCompOnScreen(gd);
          gd.setVisible(true);
          return false;
        }else {            //make sure selected approver's approval limit is greater than user's approval limit
          String tmpApprovalLimit = this.getSelectedUserOrderApprovalLimit(approvalLimitC.getSelectedItem().toString());
          //make sure that approval limit is a valid number
          Float tmpLimit = new Float(0);
          try{
            tmpLimit = new Float(tmpApprovalLimit);
          }catch(Exception e){
            msg = "Please enter only numbers in approval limit.";
            GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
            gd.setMsg(msg);
            CenterComponent.centerCompOnScreen(gd);
            gd.setVisible(true);
            return false;
          }
          //check to see if selected approver's approval limit is greater than user's approval limit
          AdminApprover aa = getAdminApprover(superC.getSelectedItem().toString());
          if (aa != null) {
            if (aa.getApprovalLimit() != -1.0) {
              if (aa.getApprovalLimit() <= tmpLimit.floatValue()) {
                msg = "User Approval Limit can't be greater than or equal to Next Level approval limit.";
                GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
                gd.setMsg(msg);
                CenterComponent.centerCompOnScreen(gd);
                gd.setVisible(true);
                return false;
              }
            }
          }
        }
      }else {  //user approval limit is unlimited, than he/she has to be at the top to the tree, in anther word - Next level should be None or Select an Approver
        if(!superC.getSelectedItem().toString().equals(SELECT_NONE) && !superC.getSelectedItem().toString().equals(SELECT_SUPER)){
          msg = "Please select 'None' for Next Level.\nUser with Unlimited approval limit\ndoes not require Next Level approver.";
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg(msg);
          CenterComponent.centerCompOnScreen(gd);
          gd.setVisible(true);
          return false;
        }
      }
    }
    return true;
  } //end of method

  String getSelectedUserOrderApprovalLimit(String selectedLimit) {
    StringBuffer sb = new StringBuffer(selectedLimit);
    StringBuffer sb2 = new StringBuffer();
    for(int i=0;i<sb.length();i++){
      if(sb.charAt(i) == '$' || sb.charAt(i) == ','){
        continue;
      }else if(sb.charAt(i) == '.'){
        break;
      }
      try {
        sb2.append(sb.charAt(i));
      }catch(Exception e) {

      }
    }
    return (sb2.toString());
  } //end of method

  void buildApproverHash(Vector v){
    approvers = new Hashtable();
    for(int i=0;i<v.size();i++){
      AdminApprover aa = (AdminApprover)v.elementAt(i);
      if(aa.isFac())continue;
      approvers.put(aa.getName(),aa);
    }
  }

  void buildComboBoxes(){
    Hashtable amounts = new Hashtable();
    Hashtable approvalLimits = new Hashtable();
    Vector names = new Vector();
    for(Enumeration E=approvers.keys();E.hasMoreElements();){
      String tmp = new String(E.nextElement().toString());
      AdminApprover aa = (AdminApprover)approvers.get(tmp);
      if(aa.isFac() || aa.getApprovalLimit() == 0)continue;
      amounts.put(aa.getFormatedCostLimit(),"!");
      approvalLimits.put(aa.getFormatedapprovalLimit(),"!");
      names.addElement(aa.toString());
    }
    names = BothHelpObjs.sort(names);
    names.insertElementAt(SELECT_SUPER,0);
    names.insertElementAt(SELECT_NONE,1);
    ClientHelpObjs.loadChoiceFromVector(superC,names);

    Vector myV = new Vector();
    for(Enumeration E=amounts.keys();E.hasMoreElements();){
        myV.addElement(E.nextElement().toString());
    }
    myV = BothHelpObjs.sort(myV);
    myV.insertElementAt(SELECT_COST,0);
    ClientHelpObjs.loadChoiceFromVector(limitC,myV);
    //approval limit
    Vector myApprovalV = new Vector();
    for(Enumeration E=approvalLimits.keys();E.hasMoreElements();){
        myApprovalV.addElement(E.nextElement().toString());
    }
    myApprovalV = BothHelpObjs.sort(myApprovalV);
    myApprovalV.insertElementAt(SELECT_APPROVAL,0);
    ClientHelpObjs.loadChoiceFromVector(approvalLimitC,myApprovalV);
    approvalLimitC.setSelectedIndex(0);
    superC.setSelectedIndex(0);
  } //end of method

  public boolean getCanceled(){
    return canceled;
  }
  AdminApprover getAdminApprover(int i){
    for(Enumeration E=approvers.keys();E.hasMoreElements();){
      String tmp = new String(E.nextElement().toString());
      AdminApprover aa = (AdminApprover)approvers.get(tmp);
      if(aa.getId() == i)return aa;
    }
    return null;
  }
  AdminApprover getAdminApprover(String s){
    for(Enumeration E=approvers.keys();E.hasMoreElements();){
      String tmp = new String(E.nextElement().toString());
      AdminApprover aa = (AdminApprover)approvers.get(tmp);
      if(aa.toString().equals(s))return aa;
    }
    return null;
  }

  void goChooseApprover(){
    //choose requestor
    SearchPersonnel dlg = new SearchPersonnel(cmis.getMain(),"Search",true);
    dlg.setGrandParent(cmis);
    dlg.setPersonType("PERSONNEL");
    dlg.setVisible(true);
    if (dlg.getValueId() != null){
      approverT.setUpdating();
      approverT.setText(dlg.getValueLast()+", "+dlg.getValueFirst());
      String id = dlg.getValueId().toString();
      Vector v = new Vector();
      v.addElement("first Name");
      v.addElement("last Name");
      v.addElement(id);
      approverT.setWhoV(v);
    }
  }

  void button1_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(!auditData())return;
    if(goAddUpdate()){
      canceled = false;
      setVisible(false);
    }
  }

  void button2_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    canceled = true;
    setVisible(false);
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goChooseApprover();
  } //end of method
} //end of class
