
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui.admin;

import java.awt.*;
import java.beans.*;

import java.util.*;
//import com.borland.jbcl.view.*;
import radian.tcmis.client.share.gui.*;

import javax.swing.*;

import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;

public class AdminWin extends JPanel{
  CmisApp cmis;
  JPanel tocP = new JPanel();
  JPanel rightSide = new JPanel();
  JPanel inputP = new JPanel();
  Vector inputPanels;
  ButtonGroup bg = new ButtonGroup();
  StaticJLabel titleL = new StaticJLabel();
  JPanel titleP = new JPanel();
  FacilityCombo facC;
  boolean facCLoaded = false;
  AdminInputPanel currentAIP = null;
  int numButtonsEnabled = 0;

  PaneLayout paneLayout1 = new PaneLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();

  public AdminWin(CmisApp cmis) {
    this.cmis = cmis;
    try {
      jbInit();
      loadPanels();
      loadItAction();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception {
    titleL.setText("Please Select an Action");
    titleP.add(titleL);
    facC = new FacilityCombo();
    facC.setLoadType(FacilityCombo.STANDARD_WITH_HUBS);
    facC.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equalsIgnoreCase("loaded")) {
          facCLoaded();
        }
      }
    });
    inputPanels = getInputPanels();

    this.setLayout(gridBagLayout1);
    this.setSize(750,450);
    inputP.setLayout(new BorderLayout());
    rightSide.setLayout(gridBagLayout2);
    //9-25-01 rightSide.setBorder(new BevelBorder(BevelBorder.RAISED));
    rightSide.setBorder(ClientHelpObjs.groupBox(""));
    //9-25-01 tocP.setBorder(new BevelBorder(BevelBorder.RAISED));
    tocP.setBorder(ClientHelpObjs.groupBox(""));
    tocP.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,5,5,true,false));


    for(int i=0;i<inputPanels.size();i++) {
      JToggleButton jtb = ((AdminInputPanel)inputPanels.elementAt(i)).getButton();
      jtb.setSize(70,-1);
      bg.add(jtb);
      tocP.add(jtb);
    }

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    this.add(tocP, new GridBagConstraints2(0, 0, 1, 2, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
    this.add(rightSide,new GridBagConstraints2(1,0,1,1,1.0,1.0
            ,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,new Insets(0,0,0,0),200,200));

    rightSide.add(titleP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 200, 5));
    rightSide.add(inputP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 200, 165));

    ClientHelpObjs.makeDefaultColors(this);
  }

  void loadPanels(){
    facC.setCmisApp(cmis);
    facC.loadIt();
    for(int i=0;i<inputPanels.size();i++){
      //System.out.println("----- here in load panels of adminWin");
      ((AdminInputPanel)inputPanels.elementAt(i)).loadIt();
    }
  }
  public void removeButton(JToggleButton jtb){
    bg.remove(jtb);
    tocP.remove(jtb);
    tocP.revalidate();
  }

  public void buttonEnabled(){
    /*numButtonsEnabled++;
    int numButtons = tocP.getComponentCount();
    //System.out.println("num:"+ numButtons+"\nnumEnabled:"+numButtonsEnabled);
    if(numButtonsEnabled < numButtons){
     cmis.getMain().startImage();
    }else{
     cmis.getMain().stopImage();
    } */
  }

  public CmisApp getCmisApp(){
    return cmis;
  }
  public void loadItAction(){
    loadScreenData();
    //cmis.getMain().stopImage();
    tocP.setVisible(true);
    inputP.setVisible(true);
    this.validate();
    this.repaint();
  }

  public void loadIt(){
    AdminWinLoadThread rw = new AdminWinLoadThread(this);
    rw.start();
  }

  void loadScreenData() {
  }

  public Vector getInputPanels() {
    Vector v = new Vector();
    // work area
    //v.addElement(new AdminWorkAreaPanel(this));
    // location
    //v.addElement(new AdminLocationPanel(this));
    // Order Approvers
    v.addElement(new AdminApproverPanel(this));

    // Use Approval Groups
    v.addElement(new AdminUseApprovalGroupPanel(this,false));
    // New Chem approvers
    v.addElement(new AdminNewChemPanel(this));
    // Admin groups
    v.addElement(new AdminUseApprovalGroupPanel(this,true));
    // Drop Facilities
    //    v.addElement(new AdminDropFacilityPanel(this));
    // Facilities
    //    v.addElement(new AdminFacilityPanel(this));

    //trong changes: adding the admin waste pickup location panel
    //     v.addElement(new AdminWastePickupLocation(this));



  return v;
  }

  // Nawaz

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
}

class AdminWinLoadThread extends Thread {
 AdminWin parent;
  public AdminWinLoadThread(AdminWin parent){
     super("AdminWinLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}
class NoAdminSelected extends JPanel {
  GridBagLayout gbl = new GridBagLayout();
  StaticJLabel l = new StaticJLabel();

  NoAdminSelected() {
    super();
    this.setLayout(gbl);
    l.setText("Please Select a Report");
    this.add(l);
    this.validate();
  }
}

