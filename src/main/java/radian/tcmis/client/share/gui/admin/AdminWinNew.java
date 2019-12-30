package radian.tcmis.client.share.gui.admin;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */
import java.awt.*;
//import com.borland.jbcl.view.*;
import radian.tcmis.client.share.gui.*;
import javax.swing.*;

import radian.tcmis.client.share.helpers.*;

public class AdminWinNew extends JPanel{
  CmisApp cmis;
  JPanel tocP = new JPanel();
  JTabbedPane adminWinTab = new JTabbedPane();
  AdminWorkAreaPanel adminWorkArea;
  JPanel testP = new JPanel();
  JButton jButton1 = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  //Vector facIDs = null;
  //Vector facNames = null;

  //public AdminWinNew(CmisApp cmis, Vector facIDs, Vector facNames) {
  public AdminWinNew(CmisApp cmis) {
    this.cmis = cmis;
    //facIDs = facIDs;
    //facNames = facNames;
    try {
      //adminWorkArea = new AdminWorkAreaPanel(this,facIDs,facNames);
      adminWorkArea = new AdminWorkAreaPanel(this);
      jbInit();
      loadItAction();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    this.setSize(750,450);
    tocP.setBorder(ClientHelpObjs.groupBox(""));
    tocP.setLayout(gridBagLayout2);
    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    jButton1.setText("TEST");
    this.add(tocP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    adminWinTab.setFont(new java.awt.Font("Dialog", 0, 10));
    testP.add(jButton1, null);
    adminWinTab.add("Work Area",adminWorkArea);
    tocP.add(adminWinTab, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    ClientHelpObjs.makeDefaultColors(this);
  }

  public CmisApp getCmisApp(){
    return cmis;
  }
  public void loadItAction(){
    tocP.setVisible(true);
    this.validate();
    this.repaint();
  }

  public void loadIt(){
    AdminWinNewLoadThread rw = new AdminWinNewLoadThread(this);
    rw.start();
  }

  public void stateChanged(String s){
  }
} //end of class

 /*
  public void printScreenData()
  {
    String myTitle = currentAIP.getTitle();
    System.out.println("------------ what's screen is it: "+myTitle);
    if (myTitle.equalsIgnoreCase("Work Area")){printWorkArea();}
    if (myTitle.equalsIgnoreCase("Approvers")){printApprovers();}
    if (myTitle.equalsIgnoreCase("Use Approval Groups")){printUseAppGroup();}
    if (myTitle.equalsIgnoreCase("New Chemical Approval")){printNewChemApp();}
    if (myTitle.equalsIgnoreCase("Administrative Groups")){printAdminGroup();}
  }

  void printWorkArea()
  {
    AdminWorkAreaPanel workAreaP = (AdminWorkAreaPanel)inputPanels.elementAt(0);
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setFacId(workAreaP.facC.getSelectedFacId().toString());

    rod.setScreen("Work_Area");
    rod.loadIt(false);
  }

  void printApprovers()
  {
    AdminApproverPanel Approver = (AdminApproverPanel)inputPanels.elementAt(1);
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setFacId(Approver.facC.getSelectedFacId().toString());

    rod.setScreen("Approvers");
    rod.loadIt(false);
  }

  void printUseAppGroup()
  {
    AdminUseApprovalGroupPanel AppGroup = (AdminUseApprovalGroupPanel)inputPanels.elementAt(2);
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setFacId(AppGroup.facC.getSelectedFacId().toString());
    rod.setGroupDesc(AppGroup.groupDescC.getSelectedItem().toString());

    rod.setScreen("Use_App_Group");
    rod.loadIt(false);
  }

  void printNewChemApp()
  {
    AdminNewChemPanel NewChem = (AdminNewChemPanel)inputPanels.elementAt(3);
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setFacId(NewChem.facC.getSelectedFacId().toString());

    rod.setScreen("New_Chem_App");
    rod.loadIt(false);
  }

  void printAdminGroup()
  {
    AdminUseApprovalGroupPanel AdminGroup = (AdminUseApprovalGroupPanel)inputPanels.elementAt(4);
    ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
    rod.setCmisApp(cmis);
    rod.setFacId(AdminGroup.facC.getSelectedFacId().toString());
    rod.setGroupDesc(AdminGroup.groupDescC.getSelectedItem().toString());
    rod.setScreen("Admin_Group");
    rod.loadIt(false);
  }

  // Nawaz


  public void showThisPanel(AdminInputPanel aip)
  {
    if(currentAIP != null)
    {
      currentAIP.setVisible(false);
      inputP.remove(currentAIP);
    }
    //inputP.removeAll();
    inputP.validate();
    titleL.setText(aip.getTitle());
    inputP.add(aip,BorderLayout.CENTER);
    inputP.validate();
    aip.revalidate();
    aip.setVisible(true);
    this.revalidate();
    //aip.repaint();
    //repaint();
    currentAIP = aip;

  }

   synchronized void facCLoaded(){
    if(facCLoaded)return;
    facCLoaded = true;
    for(int x=0;x<inputPanels.size();x++) {
      ((AdminInputPanel)inputPanels.elementAt(x)).setFacCombo(facC);
    }
  }

  public void stateChanged(String s){
  }
}  //end of class */

class AdminWinNewLoadThread extends Thread {
 AdminWinNew parent;
  public AdminWinNewLoadThread(AdminWinNew parent){
     super("AdminWinLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}


