//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.admin;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;
import java.util.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AddFinanceApprovalDlg extends JDialog {
//public class AddFinanceApprovalDlg extends JPanel {
  FacilityCombo facC = new FacilityCombo();
  Vector approverV = new Vector();
  boolean facCLoaded = false;
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JButton goB = new JButton();
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JButton addB = new JButton();
  JButton editB = new JButton();
  JButton delB = new JButton();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JPanel jPanel5 = new JPanel();
  JScrollPane jsp = new JScrollPane();
  JPanel approverPanel = new JPanel();
  JPanel empty = new JPanel();
  String currentFac = "";
  JTree appTree;
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JLabel emptyL = new JLabel("Select 'Get Approvers' to load Approver information.");
  CmisApp cmis;
  GridBagLayout gridBagLayout6 = new GridBagLayout();
  GridBagLayout gridBagLayout7 = new GridBagLayout();
  JButton okB = new JButton();
  JLabel currencyL = new JLabel();
  String currency = "";
  JButton alternateApproverB = new JButton();

  public AddFinanceApprovalDlg(Frame frame, String title, boolean modal,CmisApp cmis) {
    super(frame, title, modal);
    this.cmis = cmis;
    try  {
      jbInit();
      facC.setCmisApp(cmis);
      facC.setUseAllFacilities(false);
      if(!cmis.getGroupMembership().isSuperUser()) facC.restrictToAdminFacilities(true);
        synchronized (this){
         facC.loadIt();
        }
    }catch (Exception ex) {
      ex.printStackTrace();
    }
    getContentPane().add(jPanel1);
    pack();
    // Colors
    ClientHelpObjs.makeDefaultColors(this);
    validate();
    repaint();
    System.out.println("-------- financial approval facility list: "+facC.getItemCount()+ " first fac: "+(String)facC.getItemAt(0));
  }


  void jbInit() throws Exception {
    ClientHelpObjs.makeDefaultColors(this);
    validate();
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/add.gif");
    addB.setFont(new java.awt.Font("Dialog", 0, 10));
    addB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/minus.gif");
    delB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/delete.gif");
    editB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/submit.gif");
    goB.setIcon(ss);

    empty.setBackground(Color.white);
    empty.setLayout(new BorderLayout());
    emptyL.setHorizontalAlignment(SwingConstants.CENTER);
    emptyL.setHorizontalTextPosition(SwingConstants.CENTER);
    facC.setMinimumSize(new Dimension(150, 23));
    okB.setMaximumSize(new Dimension(500, 40));
    okB.setMinimumSize(new Dimension(500, 40));
    okB.setPreferredSize(new Dimension(500, 40));
    jPanel5.setLayout(gridBagLayout7);
    jPanel5.setMaximumSize(new Dimension(500, 50));
    jPanel5.setMinimumSize(new Dimension(500, 50));
    jPanel5.setPreferredSize(new Dimension(540, 50));
    okB.setMaximumSize(new Dimension(100, 23));
    okB.setMinimumSize(new Dimension(100, 23));
    okB.setPreferredSize(new Dimension(100, 23));
    okB.setSelected(true);
    okB.setText("OK");
    ss = ResourceLoader.getImageIcon("images/button/checked.gif");
    okB.setIcon(ss);
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    empty.add(emptyL,BorderLayout.NORTH);

    jPanel1.setMaximumSize(new Dimension(500, 465));
    jPanel1.setMinimumSize(new Dimension(500, 465));
    jPanel1.setPreferredSize(new Dimension(500, 465));
    jPanel1.setLayout(gridBagLayout2);
    jPanel1.setBorder(ClientHelpObjs.groupBox(""));
    goB.setMaximumSize(new Dimension(130, 23));
    goB.setMinimumSize(new Dimension(130, 23));
    goB.setPreferredSize(new Dimension(130, 23));
    goB.setText("Get Approvers");
    goB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        goB_actionPerformed(e);
      }
    });
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Facility:");
    facC.setMaximumSize(new Dimension(150, 23));
    facC.setPreferredSize(new Dimension(150, 23));
    facC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        facC_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout3);
    jPanel3.setMaximumSize(new Dimension(120, 32767));
    jPanel3.setMinimumSize(new Dimension(120, 120));
    jPanel3.setPreferredSize(new Dimension(120, 200));
    jPanel3.setLayout(gridBagLayout5);
    addB.setMaximumSize(new Dimension(100, 23));
    addB.setMinimumSize(new Dimension(100, 23));
    addB.setPreferredSize(new Dimension(100, 23));
    addB.setText("Add");
    addB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });
    editB.setMaximumSize(new Dimension(100, 23));
    editB.setMinimumSize(new Dimension(100, 23));
    editB.setPreferredSize(new Dimension(100, 23));
    editB.setText("Edit");
    editB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editB_actionPerformed(e);
      }
    });
    delB.setMaximumSize(new Dimension(100, 23));
    delB.setMinimumSize(new Dimension(100, 23));
    delB.setPreferredSize(new Dimension(100, 23));
    delB.setText("Delete");
    delB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delB_actionPerformed(e);
      }
    });
    alternateApproverB.setMaximumSize(new Dimension(100, 23));
    alternateApproverB.setMinimumSize(new Dimension(100, 23));
    alternateApproverB.setPreferredSize(new Dimension(100, 23));
    alternateApproverB.setText("Alt Approver");
    alternateApproverB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        alternateApproverB_actionPerformed(e);
      }
    });

    addB.setEnabled(false);
    editB.setEnabled(false);
    delB.setEnabled(false);
    alternateApproverB.setEnabled(false);

    jPanel4.setLayout(gridBagLayout4);
    okB.setLayout(gridBagLayout6);
    jsp.getViewport().setView(empty);
    /*this.add(jPanel1, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); */
    jPanel1.add(goB, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 9, 3, 3), 0, 0));
    jPanel1.add(facC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 3, 3, 3), 0, 0));
    jPanel1.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 15, 3, 3), 0, 0));
    jPanel1.add(jPanel2, new GridBagConstraints2(0, 1, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jPanel4, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel4.add(jsp, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 15, 10, 3), 0, 0));
    //bottom panel
    jPanel1.add(jPanel5, new GridBagConstraints2(0, 2, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel5.add(currencyL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    jPanel5.add(okB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    //right side panel
    jPanel2.add(jPanel3, new GridBagConstraints2(1, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 3, 5, 5), 0, 0));
    jPanel3.add(addB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
    jPanel3.add(delB, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
    jPanel3.add(editB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel3.add(alternateApproverB, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
  }


  public void setFacCombo(FacilityCombo fc){
    facC.setCmisApp(cmis);
    facC.setUseAllFacilities(false);
    if(!cmis.getGroupMembership().isSuperUser()){
      facC.restrictToAdminFacilities(true);
    }
    fc.loadAnotherOne(facC);
    facC.setSelectedIndex(0);
    facCLoaded = true;
    loaded();

  }

  public void loadIt(){
    this.startLoadTree();
  }

  public void loadThisPanel(){
  }

  void loaded(){
    if(facCLoaded){
      //this.enableButton(true);
      ClientHelpObjs.makeDefaultColors(this);
    }
  }

  void startLoadTree(){
    AddFinanceApprovalLoadThread aalt = new AddFinanceApprovalLoadThread(this);
    aalt.start();
  }

  void loadTree(){
    ClientHelpObjs.setEnabledContainer(this,false);
    currentFac = facC.getSelectedItem().toString();
    TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","LOAD_APPROVER_TREE"); //String, String
    query.put("FAC_ID",facC.getSelectedFacId()); //String, String
    //System.out.println("------- my select fac: "+facC.getSelectedFacId()+" - "+facC.getSelectedItem().toString());
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }

    approverV = new Vector();
    Vector hold = new Vector();

    Boolean facNeedsApprovers = (Boolean)result.get("FAC_NEEDS_APPROVERS");

     if(!facNeedsApprovers.booleanValue()){
      jsp.getViewport().remove(approverPanel);    //trong 3/29
      approverPanel.removeAll();                  //trong 3/29
      approverPanel.setBackground(Color.white);
      approverPanel.setLayout(new BorderLayout());
      JLabel label1 = new JLabel();
      label1.setText("This Facility does not use Finance Approvers.");
      label1.setHorizontalAlignment(SwingConstants.CENTER);
      label1.setHorizontalTextPosition(SwingConstants.CENTER);
      approverPanel.add(label1,BorderLayout.NORTH);
      jsp.getViewport().setView(approverPanel);
      ClientHelpObjs.setEnabledContainer(this,true);
      addB.setEnabled(false);
      editB.setEnabled(false);
      delB.setEnabled(false);
      alternateApproverB.setEnabled(false);
      approverPanel.revalidate();              //trong 3/29
      jsp.revalidate();                        //trong 3/29
      this.validate();
      return;
    }

    Vector v = (Vector)result.get("APPROVERS");
    for(int i=0;i<v.size();i++){
      AdminApprover myAa = new AdminApprover((Hashtable)v.elementAt(i));
      hold.addElement(myAa);
      approverV.addElement(myAa);
    }
    hold = sortApprovers(hold);
    AdminApprover root = new AdminApprover(facC.getSelectedItem().toString());
    // get all super approvers
    int numSuper = 0;
    for(int i=hold.size();i>0;i--){
      if(i>hold.size())continue;
      AdminApprover aa = (AdminApprover) hold.elementAt(i-1);
      //if(aa.getApprovalLimit() < 0) {
      numSuper++;
      root.add(aa);
      aa.recursiveAdd(hold);
      //}
    }

    appTree = new JTree(root);
    appTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    for(int i=appTree.getRowCount();i>0;i--){
      appTree.expandRow(i-1);
    }
    appTree.addMouseListener(new MouseListener(){
      public void mouseClicked(MouseEvent p0){setButtons();}
      public void mousePressed(MouseEvent p0){setButtons();}
      public void mouseReleased(MouseEvent p0){setButtons();}
      public void mouseEntered(MouseEvent p0){}
      public void mouseExited(MouseEvent p0){}
    });

    //currency note
    if (((Boolean)result.get("SHOW_CURRENCY")).booleanValue()) {
      currency = (String)result.get("CURRENCY");
      currencyL.setText("Note: Order/Approval limit is in "+currency);
    }

    approverPanel = new JPanel();
    approverPanel.setLayout(new BorderLayout());
    approverPanel.add(appTree);
    jsp.getViewport().setView(approverPanel);
    ClientHelpObjs.setEnabledContainer(this,true);
    addB.setEnabled(false);
    editB.setEnabled(false);
    delB.setEnabled(false);
    alternateApproverB.setEnabled(false);
    this.validate();
    ClientHelpObjs.makeDefaultColors(this);
  }

  void setButtons(){
    if(appTree.getSelectionCount()>0){
      addB.setEnabled(true);
      editB.setEnabled(true);
      delB.setEnabled(true);
      /*This is where I need to check to see if selected node it an approver or just a user
      JTree jt = (JTree)appTree.getLastSelectedPathComponent();
      TreePath tp = appTree.getSelectionPath();
      System.out.println("---- selected node: "+jt.getModel().isLeaf(tp.getLastPathComponent()));
      */
      alternateApproverB.setEnabled(true);
    }else{
      addB.setEnabled(false);
      editB.setEnabled(false);
      delB.setEnabled(false);
      alternateApproverB.setEnabled(false);
    }
  }

  void goUpdateAddDelete(String action){
    if(action.equalsIgnoreCase("delete")){
      goDelete();
    }else{
      goAddUpdate(action);
    }
  }

  void goAddUpdate(String action){
    AddApproverDlg aad = new AddApproverDlg(cmis,approverV,facC.getSelectedItem().toString(),facC.getSelectedFacId(),currency);
    TreePath tp = appTree.getSelectionPath();
    AdminApprover aa = (AdminApprover)tp.getLastPathComponent();
    aad.setApprover(aa,action);
    CenterComponent.centerCompOnScreen(aad);
    aad.setVisible(true);
    if(!aad.getCanceled()){
      startLoadTree();
    }
  }

  void goDelete(){
    TreePath tp = appTree.getSelectionPath();
    AdminApprover aa = (AdminApprover)tp.getLastPathComponent();

    // check for deleting a unlimited amount approver with children
    if(aa.getApprovalLimit() == -1 && aa.getChildCount() > 0) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Deleting",true);
      gd.setMsg("You cannot delete an approver with unlimited approval authority until you assign all sub-approvers to another super approver.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }
    ConfirmDlg cd = new ConfirmDlg(cmis.getMain(),"Confirm Deletion",true);
    cd.setMsg("Do you want to delete "+aa.getName()+" as an approver for "+facC.getSelectedItem().toString()+"?");
    cd.setVisible(true);
    CenterComponent.centerCompOnScreen(cd);
    if(!cd.getConfirmationFlag()){
      return;
    }
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",cmis);
    Hashtable query = new Hashtable();
    query.put("ADMIN_ID",cmis.getUserId());
    query.put("APPROVER_ID",new Integer(aa.getId()).toString());
    query.put("ACTION","DELETE_APPROVER"); //String, String
    query.put("FAC_ID",facC.getSelectedFacId()); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
    }

    String status = result.get("STATUS").toString();
    String msg = result.get("MSG").toString();
    if ("OK".equalsIgnoreCase(status)) {
      //show user success message and refresh tree
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Approver", true);
      gd.setMsg(msg);
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      startLoadTree();
    }else {
      //Show user error message
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Approver", true);
      gd.setMsg(msg);
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      ClientHelpObjs.setEnabledContainer(this,true);
    }
  }

  void facC_actionPerformed(ActionEvent e) {
    if(!facCLoaded)return;
    if(facC.getSelectedItem().toString().equals(currentFac)){
      jsp.getViewport().setView(approverPanel);
      addB.setEnabled(false);
      editB.setEnabled(false);
      delB.setEnabled(false);
      alternateApproverB.setEnabled(false);
    }else{
      jsp.getViewport().setView(empty);
      addB.setEnabled(false);
      editB.setEnabled(false);
      delB.setEnabled(false);
      alternateApproverB.setEnabled(false);
    }
    jsp.revalidate();
    this.validate();
  }

  Vector sortApprovers(Vector in){
    //approverV
    Hashtable h = new Hashtable();
    Vector tmp = new Vector();
    for(int i=0;i<in.size();i++){
      AdminApprover aa = (AdminApprover) in.elementAt(i);
      tmp.addElement(aa.toString());
      h.put(aa.toString(),aa);
    }
    tmp = BothHelpObjs.sort(tmp);
    // now reverse it
    Vector tmp2 = new Vector();
    for(int i=tmp.size();i>0;i--){
      tmp2.addElement(tmp.elementAt(i-1));
    }
    in = new Vector();
    for(int i=0;i<tmp2.size();i++){
      String key = tmp2.elementAt(i).toString();
      in.addElement(h.get(key));
    }
    //System.out.println("in:"+in.toString());
    return in;
  }

  void goB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    startLoadTree();
  }

  void addB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    AddFinanceApprovalAddThread aaat = new AddFinanceApprovalAddThread(this, "add");
    aaat.start();
  }

  void editB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    AddFinanceApprovalAddThread aaat = new AddFinanceApprovalAddThread(this, "update");
    aaat.start();
  }

  void delB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    AddFinanceApprovalAddThread aaat = new AddFinanceApprovalAddThread(this, "delete");
    aaat.start();
  }

  void alternateApproverB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    TreePath tp = appTree.getSelectionPath();
    AdminApprover aa = (AdminApprover)tp.getLastPathComponent();
    AlternateApproverDlg aaDlg = new AlternateApproverDlg(cmis,facC.getSelectedFacId(),aa.getName(),new Integer(aa.getId()));
    CenterComponent.centerCompOnScreen(aaDlg);
    aaDlg.setVisible(true);
  }


  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  } //end of method
} //end of class

class AddFinanceApprovalLoadThread extends Thread{
  AddFinanceApprovalDlg aap;
  public AddFinanceApprovalLoadThread(AddFinanceApprovalDlg aap){
    this.aap = aap;
  }
  public void run(){
    aap.loadTree();
  }
}
class AddFinanceApprovalAddThread extends Thread{
  AddFinanceApprovalDlg aap;
  String action = "";
  public AddFinanceApprovalAddThread(AddFinanceApprovalDlg aap,String action){
    this.aap = aap;
    this.action = action;
  }
  public void run(){
    aap.goUpdateAddDelete(action);
  }
}
