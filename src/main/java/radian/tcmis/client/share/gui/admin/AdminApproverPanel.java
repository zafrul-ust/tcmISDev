
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
public class AdminApproverPanel extends AdminInputPanel {
//public class AdminApproverPanel extends JPanel {
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
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();
  JPanel approverPanel = new JPanel();
  JPanel empty = new JPanel();
  String currentFac = "";
  JTree appTree;
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JLabel emptyL = new JLabel("Select 'Get Approvers' to load Approver information.");

  public AdminApproverPanel(AdminWin aw) {
    super(aw,"Financial Approvers");
    try  {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    ClientHelpObjs.makeDefaultColors(this);
    validate();

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
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
    empty.add(emptyL,BorderLayout.NORTH);

    jPanel1.setMaximumSize(new Dimension(32767, 65));
    jPanel1.setMinimumSize(new Dimension(60, 65));
    jPanel1.setPreferredSize(new Dimension(500, 65));
    jPanel1.setLayout(gridBagLayout2);
    goB.setMaximumSize(new Dimension(300, 24));
    goB.setMinimumSize(new Dimension(111, 24));
    goB.setPreferredSize(new Dimension(140, 24));
    goB.setText("Get Approvers");
    goB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        goB_actionPerformed(e);
      }
    });
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Facility:");
    facC.setMaximumSize(new Dimension(32767, 21));
    facC.setPreferredSize(new Dimension(135, 24));
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
    addB.setMaximumSize(new Dimension(100, 25));
    addB.setMinimumSize(new Dimension(100, 25));
    addB.setPreferredSize(new Dimension(100, 25));
    addB.setText("Add");
    addB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });
    editB.setMaximumSize(new Dimension(100, 25));
    editB.setMinimumSize(new Dimension(100, 25));
    editB.setPreferredSize(new Dimension(100, 25));
    editB.setText("Edit");
    editB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        editB_actionPerformed(e);
      }
    });
    delB.setMaximumSize(new Dimension(100, 25));
    delB.setMinimumSize(new Dimension(100, 25));
    delB.setPreferredSize(new Dimension(100, 25));
    delB.setText("Delete");
    delB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        delB_actionPerformed(e);
      }
    });

    addB.setEnabled(false);
    editB.setEnabled(false);
    delB.setEnabled(false);

    jPanel4.setLayout(gridBagLayout4);
    jPanel5.setLayout(borderLayout2);
    jsp.getViewport().setView(empty);
    this.add(jPanel1, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(goB, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 9, 3, 3), 0, 0));
    jPanel1.add(facC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(jPanel2, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jPanel4, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel4.add(jPanel5, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 60, 10, 3), 0, 0));
    jPanel5.add(jsp, BorderLayout.CENTER);
    jPanel2.add(jPanel3, new GridBagConstraints2(1, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 3, 5, 5), 0, 0));
    jPanel3.add(addB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
    jPanel3.add(delB, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
    jPanel3.add(editB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
  }


  public void setFacCombo(FacilityCombo fc){
    facC.setCmisApp(getCmisApp());
    facC.setUseAllFacilities(false);
    if(!getCmisApp().getGroupMembership().isSuperUser()){
      facC.restrictToAdminFacilities(true);
    }
    fc.loadAnotherOne(facC);
    facC.setSelectedIndex(0);
    facCLoaded = true;
    loaded();

  }

  public void loadIt(){
    this.startLoad();
  }

  public void loadThisPanel(){
  }

  void loaded(){
    if(facCLoaded){
      this.enableButton(true);
      ClientHelpObjs.makeDefaultColors(this);
    }
  }

  void startLoadTree(){
    AdminApproverLoadThread aalt = new AdminApproverLoadThread(this);
    aalt.start();
  }

  void loadTree(){
    ClientHelpObjs.setEnabledContainer(this,false);
    currentFac = facC.getSelectedItem().toString();
    TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",getCmisApp());
    Hashtable query = new Hashtable();
    query.put("ACTION","LOAD_APPROVER_TREE"); //String, String
    query.put("FAC_ID",facC.getSelectedFacId()); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(getCmisApp().getMain());
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
      approverPanel.revalidate();              //trong 3/29
      jsp.revalidate();                        //trong 3/29
      this.revalidate();
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
      if(aa.getCostLimit() < 0){
        numSuper++;
        root.add(aa);
        aa.recursiveAdd(hold);
      }
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
    approverPanel = new JPanel();
    approverPanel.setLayout(new BorderLayout());
    approverPanel.add(appTree);
    jsp.getViewport().setView(approverPanel);
    ClientHelpObjs.setEnabledContainer(this,true);
    //addB.setEnabled(true);
    addB.setEnabled(false);
    editB.setEnabled(false);
    delB.setEnabled(false);
    this.revalidate();
    ClientHelpObjs.makeDefaultColors(this);
  }

  void setButtons(){
    if(appTree.getSelectionCount()>0){
      addB.setEnabled(true);
      editB.setEnabled(true);
      delB.setEnabled(true);
    }else{
      //addB.setEnabled(true);
      addB.setEnabled(false);
      editB.setEnabled(false);
      delB.setEnabled(false);
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
    AddApproverDlg aad = new AddApproverDlg(getCmisApp(),approverV,facC.getSelectedItem().toString(),facC.getSelectedFacId(),"");
    //if(action.equalsIgnoreCase("update")){
      TreePath tp = appTree.getSelectionPath();
      AdminApprover aa = (AdminApprover)tp.getLastPathComponent();
      aad.setApprover(aa,action);
    //}
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
    if(aa.getCostLimit() == -1 && aa.getChildCount() > 0) {
      GenericDlg gd = new GenericDlg(getCmisApp().getMain(),"Deleting",true);
      gd.setMsg("You cannot delete an approver with unlimited approval authority until you assign all sub-approvers to another super approver.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }
    ConfirmDlg cd = new ConfirmDlg(getCmisApp().getMain(),"Confirm Deletion",true);
    cd.setMsg("Do you want to delete "+aa.getName()+" as an approver for "+facC.getSelectedItem().toString()+"?");
    cd.setVisible(true);
    CenterComponent.centerCompOnScreen(cd);
    if(!cd.getConfirmationFlag()){
      return;
    }
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",getCmisApp());
    Hashtable query = new Hashtable();
    query.put("APPROVER_ID",new Integer(aa.getId()).toString());
    query.put("ACTION","DELETE_APPROVER"); //String, String
    query.put("FAC_ID",facC.getSelectedFacId()); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
    }

    String status = result.get("STATUS").toString();
    String msg = result.get("MSG").toString();

    boolean retVal = true;
    GenericDlg gd = new GenericDlg(getCmisApp().getMain(),"Approver",true);
    gd.setMsg(msg);
    CenterComponent.centerCompOnScreen(gd);
    gd.setVisible(true);
    //ClientHelpObjs.setEnabledContainer(this,true);
    startLoadTree();
  }

  void facC_actionPerformed(ActionEvent e) {
    if(!facCLoaded)return;
    if(facC.getSelectedItem().toString().equals(currentFac)){
      jsp.getViewport().setView(approverPanel);
      //addB.setEnabled(true);
      addB.setEnabled(false);
      editB.setEnabled(false);
      delB.setEnabled(false);
    }else{
      jsp.getViewport().setView(empty);
      addB.setEnabled(false);
      editB.setEnabled(false);
      delB.setEnabled(false);
    }
    jsp.revalidate();
    this.revalidate();
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
    AdminApproverAddThread aaat = new AdminApproverAddThread(this, "add");
    aaat.start();
  }

  void editB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    AdminApproverAddThread aaat = new AdminApproverAddThread(this, "update");
    aaat.start();
  }

  void delB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    AdminApproverAddThread aaat = new AdminApproverAddThread(this, "delete");
    aaat.start();
  }
}

class AdminApproverLoadThread extends Thread{
  AdminApproverPanel aap;
  public AdminApproverLoadThread(AdminApproverPanel aap){
    this.aap = aap;
  }
  public void run(){
    aap.loadTree();
  }
}
class AdminApproverAddThread extends Thread{
  AdminApproverPanel aap;
  String action = "";
  public AdminApproverAddThread(AdminApproverPanel aap,String action){
    this.aap = aap;
    this.action = action;
  }
  public void run(){
    aap.goUpdateAddDelete(action);
  }
}

