package radian.tcmis.client.share.gui.nchem;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import radian.tcmis.both1.helpers.resource.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.httpCgi.*;

public class ProcessDetail extends JDialog {
  //Frame parent;
  CmisApp grandParent;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel mainP = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel topP = new JPanel();
  JPanel bottomP = new JPanel();
  JButton okB = new JButton();
  JButton saveB = new JButton();
  JButton cancelB = new JButton();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JTabbedPane processDetailTab = new JTabbedPane();
  String currentFacID = "";
  String currentFacDesc = "";
  String currentWorkAreaID = "";
  String currentWorkAreaDesc = "";
  Hashtable facWorkAreaInfoH = new Hashtable(20);
  OperationDefinition optDef;
  EmissionPoint emissionPt;
  MaterialUsed mtlUsed;
  ProcessDetailWaste waste;
  Training training;
  PPE ppe;
  boolean loadDataOk = true;
  String requestID = "";
  String requestStatus = "";
  String viewLevel = "";
  Component lastSelectedComponent;
  boolean cancelled = false;
  ApprPane apprPane;
  Vector editableProcess = new Vector();

  public ProcessDetail(Frame frame, String title, boolean modal,CmisApp cmis) {
    super(frame, title, modal);
    grandParent = cmis;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setRequestID(String s) {
    this.requestID = s;
  }

  public void setRequestStatus(String s) {
    this.requestStatus = s;
  }

  public void setViewLevel(String s) {
    this.viewLevel = s;
  }

  public void setApprPane(ApprPane ap) {
    this.apprPane = ap;
  }

  void jbInit() throws Exception {
    this.setResizable(false);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    this.getContentPane().setLayout(gridBagLayout1);
    mainP.setLayout(gridBagLayout2);
    ImageIcon ss=ResourceLoader.getImageIcon( "images/button/ok.gif" );
    okB.setMaximumSize(new Dimension(150, 25));
    okB.setMinimumSize(new Dimension(150, 25));
    okB.setPreferredSize(new Dimension(150, 25));
    okB.setIcon( ss );
    okB.setText("Save and Close");
    okB.addActionListener(new ProcessDetail_okB_actionAdapter(this));
    ss=ResourceLoader.getImageIcon( "images/button/save.gif" );
    saveB.setMaximumSize(new Dimension(102, 25));
    saveB.setMinimumSize(new Dimension(102, 25));
    saveB.setPreferredSize(new Dimension(102, 25));
    saveB.setIcon( ss );
    saveB.setText("Save");
    saveB.addActionListener(new ProcessDetail_saveB_actionAdapter(this));
    ss=ResourceLoader.getImageIcon( "images/button/cancel.gif" );
    cancelB.setMaximumSize(new Dimension(102, 25));
    cancelB.setMinimumSize(new Dimension(102, 25));
    cancelB.setPreferredSize(new Dimension(102, 25));
    cancelB.setIcon( ss );
    cancelB.setText("Cancel");
    cancelB.addActionListener(new ProcessDetail_cancelB_actionAdapter(this));
    bottomP.setLayout(gridBagLayout3);
    mainP.setMaximumSize(new Dimension(720,530));
    mainP.setMinimumSize(new Dimension(720,530));
    mainP.setPreferredSize(new Dimension(720,530));
    topP.setMaximumSize(new Dimension(720,500));
    topP.setMinimumSize(new Dimension(720,500));
    topP.setPreferredSize(new Dimension(720,30));
    bottomP.setMaximumSize(new Dimension(720,30));
    bottomP.setMinimumSize(new Dimension(720,30));
    bottomP.setPreferredSize(new Dimension(720,30));
    processDetailTab.setMinimumSize(new Dimension(710, 485));
    processDetailTab.setMinimumSize(new Dimension(710, 485));
    processDetailTab.setPreferredSize(new Dimension(710, 485));
    processDetailTab.setRequestFocusEnabled(true);
    //mainP.setBorder(ClientHelpObjs.groupBox(""));
    this.getContentPane().add(mainP,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    mainP.add(topP,    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    mainP.add(bottomP,   new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    bottomP.add(okB,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    bottomP.add(saveB,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    bottomP.add(cancelB,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));

    topP.add(processDetailTab,null);
    processDetailTab.setFont(new java.awt.Font("Dialog", 0, 10));
    processDetailTab.setTabPlacement(JTabbedPane.TOP);
    processDetailTab.setBorder(ClientHelpObjs.groupBox(""));
    //first set operation definition tab
    buildOperationDefinitionTab();  //initailizing all the components for list panel.
    processDetailTab.addTab("Process Definition",optDef);
    processDetailTab.addTab("Emission Points",emissionPt);
    processDetailTab.addTab("Materails Used",mtlUsed);
    processDetailTab.addTab("Waste",waste);
    processDetailTab.addTab("Training",training);
    processDetailTab.addTab("PPE",ppe);
    //then set labels tab
    processDetailTab.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e) {
        tab_changedPerformed(e);
      }
    });
    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    CenterComponent.centerCompOnScreen(this);
  }  //end of method

  //make sure that the entered is correct
  String saveNAuditSelectedComponent() {
    if (lastSelectedComponent.equals(optDef)) {
      optDef.saveCurrentData();
      return (optDef.auditData());
    }else if (lastSelectedComponent.equals(emissionPt)) {
      emissionPt.saveCurrentData();
      return (emissionPt.auditData());
    }else if (lastSelectedComponent.equals(mtlUsed)) {
      mtlUsed.saveCurrentData();
      return (mtlUsed.auditData());
    }else if (lastSelectedComponent.equals(waste)) {
      waste.saveCurrentData();
      return (waste.auditData());
    }else if (lastSelectedComponent.equals(training)) {
      training.saveCurrentData();
      return (training.auditData());
    }else if (lastSelectedComponent.equals(ppe)) {
      ppe.saveCurrentData();
      return (ppe.auditData());
    }
    return "OK";
  } //end of method

  void tab_changedPerformed(ChangeEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    Component comp = processDetailTab.getSelectedComponent();
    //before moving to another tab, we must audit the selected tab to make sure that the data is correct
    if (!"Viewer".equalsIgnoreCase(this.viewLevel)) {
      if (!comp.equals(lastSelectedComponent)) {
        String msg = saveNAuditSelectedComponent();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          processDetailTab.setSelectedComponent(lastSelectedComponent);
          return;
        }
      }
    }

    //load process for tab from Operation Description
    if (!comp.equals(optDef)) {
      //get process ID and name from operation definitio tab
      int tmpProcessCount = optDef.processTable.getRowCount();
      Vector tmpProcessIDV = new Vector(tmpProcessCount);
      Vector tmpProcessNameV = new Vector(tmpProcessCount);
      for (int i = 0; i < tmpProcessCount; i++) {
        tmpProcessIDV.addElement((String) optDef.processTable.getModel().getValueAt(i,optDef.processCol));
        if ("Editable".equalsIgnoreCase((String) optDef.processTable.getModel().getValueAt(i,optDef.processLockCol))) {
          tmpProcessNameV.addElement((String) optDef.processTable.getModel().getValueAt(i,optDef.processNameCol)+" (Editable)");
        }else {
          tmpProcessNameV.addElement((String) optDef.processTable.getModel().getValueAt(i,optDef.processNameCol));
        }
      }
      //if no process is define, this should not happen coz a work will required at least one process
      if (tmpProcessIDV.size() < 1) {
        tmpProcessIDV.addElement("11223344");
        tmpProcessNameV.addElement("No Process Defined.");
      }
      //if there are two or more processes that add 'Select a Process'
      if (tmpProcessNameV.size() > 1) {
        tmpProcessNameV.insertElementAt("Select a Process",0);
        tmpProcessIDV.insertElementAt("11223344",0);
      }

      if (comp.equals(emissionPt)) {
        emissionPt.processC.removeAllItems();
        emissionPt.processC.removeItemListener(emissionPt.cbil);
        emissionPt.processC = ClientHelpObjs.loadChoiceFromVector(emissionPt.processC,tmpProcessNameV);
        emissionPt.addB.setEnabled(tmpProcessNameV.size() == 1);
        emissionPt.processC.addItemListener(emissionPt.cbil);
        emissionPt.setViewLevel(viewLevel);
        emissionPt.setProcessIDV(tmpProcessIDV);
        emissionPt.currentFacDesc = this.currentFacDesc;
        emissionPt.currentWorkAreaDesc = this.currentWorkAreaDesc;
        emissionPt.setRequestID(requestID);
        emissionPt.loadData(currentFacID,currentWorkAreaID,(Hashtable)facWorkAreaInfoH.get(currentFacID+currentWorkAreaID),editableProcess);
      }else if (comp.equals(mtlUsed)) {
        mtlUsed.processC.removeAllItems();
        mtlUsed.processC.removeItemListener(mtlUsed.cbil);
        mtlUsed.processC = ClientHelpObjs.loadChoiceFromVector(mtlUsed.processC, tmpProcessNameV);
        mtlUsed.addB.setEnabled(tmpProcessNameV.size() == 1);
        mtlUsed.processC.addItemListener(mtlUsed.cbil);
        mtlUsed.setViewLevel(viewLevel);
        mtlUsed.setProcessIDV(tmpProcessIDV);
        mtlUsed.currentFacDesc = this.currentFacDesc;
        mtlUsed.currentWorkAreaDesc = this.currentWorkAreaDesc;
        mtlUsed.setRequestID(requestID);
        mtlUsed.loadData(currentFacID,currentWorkAreaID,(Hashtable)facWorkAreaInfoH.get(currentFacID+currentWorkAreaID),editableProcess);
      }else if (comp.equals(waste)) {
        waste.processC.removeAllItems();
        waste.processC.removeItemListener(waste.cbil);
        waste.processC = ClientHelpObjs.loadChoiceFromVector(waste.processC, tmpProcessNameV);
        waste.addB.setEnabled(tmpProcessNameV.size() == 1);
        waste.processC.addItemListener(waste.cbil);
        waste.setViewLevel(viewLevel);
        waste.setProcessIDV(tmpProcessIDV);
        waste.currentFacDesc = this.currentFacDesc;
        waste.currentWorkAreaDesc = this.currentWorkAreaDesc;
        waste.setRequestID(requestID);
        waste.loadData(currentFacID,currentWorkAreaID,(Hashtable)facWorkAreaInfoH.get(currentFacID+currentWorkAreaID),editableProcess);
      }else if (comp.equals(training)) {
        training.processC.removeAllItems();
        training.processC.removeItemListener(training.cbil);
        training.processC = ClientHelpObjs.loadChoiceFromVector(training.processC, tmpProcessNameV);
        training.addB.setEnabled(tmpProcessNameV.size() == 1);
        training.processC.addItemListener(training.cbil);
        training.setViewLevel(viewLevel);
        training.setProcessIDV(tmpProcessIDV);
        training.currentFacDesc = this.currentFacDesc;
        training.currentWorkAreaDesc = this.currentWorkAreaDesc;
        training.setRequestID(requestID);
        training.loadData(currentFacID,currentWorkAreaID,(Hashtable)facWorkAreaInfoH.get(currentFacID+currentWorkAreaID),editableProcess);
      }else if (comp.equals(ppe)) {
        ppe.processC.removeAllItems();
        ppe.processC.removeItemListener(ppe.cbil);
        ppe.processC = ClientHelpObjs.loadChoiceFromVector(ppe.processC, tmpProcessNameV);
        ppe.addB.setEnabled(tmpProcessNameV.size() == 1);
        ppe.processC.addItemListener(ppe.cbil);
        ppe.setViewLevel(viewLevel);
        ppe.setProcessIDV(tmpProcessIDV);
        ppe.currentFacDesc = this.currentFacDesc;
        ppe.currentWorkAreaDesc = this.currentWorkAreaDesc;
        ppe.setRequestID(requestID);
        ppe.loadData(currentFacID,currentWorkAreaID,(Hashtable)facWorkAreaInfoH.get(currentFacID+currentWorkAreaID),editableProcess);
      }
    }
    lastSelectedComponent = comp;
  }  //end of method

  void buildOperationDefinitionTab() {
    optDef = new OperationDefinition(grandParent,currentFacDesc,currentWorkAreaDesc);
    optDef.setViewLevel(viewLevel);
    emissionPt = new EmissionPoint(grandParent);
    mtlUsed = new MaterialUsed(grandParent);
    waste = new ProcessDetailWaste(grandParent);
    training = new Training(grandParent);
    ppe = new PPE(grandParent);
  }

  public void setFacWorkAreaDesc(String facDesc, String waDesc) {
    currentFacDesc = facDesc;
    currentWorkAreaDesc = waDesc;
  }

  public void setFacWorkAreaIDs(String facID, String waID) {
    currentFacID = facID;
    currentWorkAreaID = waID;
    if (!facWorkAreaInfoH.containsKey(facID+waID)) {
      Hashtable h = new Hashtable(10);
      h.put("LOADED",new Boolean(false));
      facWorkAreaInfoH.put(facID+waID,h);
    }
  }

  public void loadIt(Hashtable catAddDataH){
    //pdlt = new ProcessDetail_LoadThread(this,catAddDataH);
    //pdlt.start();
    loadItAction(catAddDataH);
  }

  public void loadItAction(Hashtable catAddDataH) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    Hashtable h = null;
    boolean loaded = false;
    //if we haven't load info to facility-work area then go the server and get the data
    if (!loaded) {
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj", grandParent);
      Hashtable query = new Hashtable(8);
      query.put("ACTION", "PROCESS_DETAIL"); //String, String
      query.put("FACILITY_ID", currentFacID);
      query.put("WORK_AREA_ID", currentWorkAreaID);
      query.put("USER_ID",grandParent.getUserId());
      query.put("REQUEST_ID",requestID);
      query.put("REQUEST_STATUS",requestStatus);
      query.put("VIEW_LEVEL",viewLevel);
      if (!"Viewer".equalsIgnoreCase(viewLevel)) {
        query.put("CAT_ADD_DATA", catAddDataH);
      }
      Hashtable result = cgi.getResultHash(query);
      if (result == null) {
        String no = new String("Access denied. Your session is corrupted, please restart the application.");
        GenericDlg err = new GenericDlg(grandParent.getMain(), "Denied", true);
        err.setMsg(no);
        err.setVisible(true);
        CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
        loadDataOk = false;
        return;
      }
      h = (Hashtable)result.get("PROCESS_DATA");
      if (!"OK".equalsIgnoreCase((String)h.get("MSG"))) {
        h.put("LOADED",new Boolean(false));
        GenericDlg err = new GenericDlg(grandParent.getMain(), "Error", true);
        err.setMsg((String)h.get("MSG"));
        err.setVisible(true);
        CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
        loadDataOk = false;
        facWorkAreaInfoH.put(currentFacID+currentWorkAreaID,h);
        return;
      }
      h.put("LOADED",new Boolean(true));
      facWorkAreaInfoH.put(currentFacID+currentWorkAreaID,h);
    }
    loadDataOk = true;
    if ("Viewer".equalsIgnoreCase(viewLevel)) {
      okB.setText("Close");
      saveB.setEnabled(false);
      cancelB.setEnabled(false);
    }else {
      okB.setText("Save and Close");
      saveB.setEnabled(true);
      cancelB.setEnabled(true);
    }
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    processDetailTab.setSelectedComponent(optDef);
    lastSelectedComponent = optDef;
    optDef.setViewLevel(viewLevel);
    optDef.setApprPane(apprPane);
    optDef.loadData(currentFacID,currentWorkAreaID,h,editableProcess);
    ClientHelpObjs.setComboBoxUpdateUi(this);
  } //end of method

  void okB_actionPerformed(ActionEvent e) {
    if ("Viewer".equalsIgnoreCase(viewLevel)) {
      lastSelectedComponent = optDef;
      emissionPt.lastSelectedProcessID = "";
      mtlUsed.lastSelectedProcessID = "";
      waste.lastSelectedProcessID = "";
      training.lastSelectedProcessID = "";
      ppe.lastSelectedProcessID = "";
      this.setVisible(false);
    }else {
      //save data and close window
      //save the current selected tab
      Component comp = processDetailTab.getSelectedComponent();
      if (comp.equals(optDef)) {
        String msg = optDef.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        optDef.saveCurrentData();
      }else if (comp.equals(emissionPt)) {
        String msg = emissionPt.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        emissionPt.saveCurrentData();
      }else if (comp.equals(mtlUsed)) {
        String msg = mtlUsed.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        mtlUsed.saveCurrentData();
      }else if (comp.equals(waste)) {
        String msg = waste.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        waste.saveCurrentData();
      }else if (comp.equals(training)) {
        String msg = training.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        training.saveCurrentData();
      }else if (comp.equals(ppe)) {
        String msg = ppe.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        ppe.saveCurrentData();
      }
      //go save data close window if save is successful
      if (SaveCurrentData("SaveNClose")) {
        cancelled = false;
        lastSelectedComponent = optDef;
        emissionPt.lastSelectedProcessID = "";
        mtlUsed.lastSelectedProcessID = "";
        waste.lastSelectedProcessID = "";
        training.lastSelectedProcessID = "";
        ppe.lastSelectedProcessID = "";
        this.setVisible(false);
      }
    }
  } //end of method

  boolean SaveCurrentData(String action) {
    if (editableProcess != null) {
      if (editableProcess.size() < 1) {
        //NOTHING TO DO
        return true;
      }
    }else {
      //NOTHING TO DO
      return true;
    }
    //reset/stop timer
    if ("Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
        "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
      if ("SaveNClose".equalsIgnoreCase(action)) {
        apprPane.stopTimer();
      }else {
        apprPane.resetTimer();
      }
    }
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj", grandParent);
    Hashtable query = new Hashtable(10);
    query.put("ACTION", "SAVE_PROCESS_DETAIL"); //String, String
    query.put("FACILITY_ID", currentFacID);
    query.put("WORK_AREA_ID", currentWorkAreaID);
    query.put("USER_ID",grandParent.getUserId());
    query.put("REQUEST_ID",requestID);
    query.put("REQUEST_STATUS",requestStatus);
    query.put("CURRENT_DATA",(Hashtable)facWorkAreaInfoH.get(currentFacID+currentWorkAreaID));
    query.put("VIEW_LEVEL",viewLevel);
    query.put("EDITABLE_PROCESS",editableProcess);
    query.put("TIMER_ACTION",action);
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      String no = new String("Access denied. Your session is corrupted, please restart the application.");
      GenericDlg err = new GenericDlg(grandParent.getMain(), "Denied", true);
      err.setMsg(no);
      err.setVisible(true);
      CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
      return false;
    }
    String msg = (String) result.get("MSG");
    if (!"OK".equalsIgnoreCase(msg)) {
      GenericDlg err = new GenericDlg(grandParent.getMain(), "Error", true);
      err.setMsg(msg);
      err.setVisible(true);
      CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
      return false;
    }else {
      GenericDlg.showMessage("Data was successfully saved.");
    }
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    return true;
  } //end of method

  void saveB_actionPerformed(ActionEvent e) {
    if ("Viewer".equalsIgnoreCase(viewLevel)) {
      saveB.setEnabled(false);
    }else {
      //save the current selected tab
      Component comp = processDetailTab.getSelectedComponent();
      if (comp.equals(optDef)) {
        String msg = optDef.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        optDef.saveCurrentData();
      }else if (comp.equals(emissionPt)) {
        String msg = emissionPt.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        emissionPt.saveCurrentData();
      }else if (comp.equals(mtlUsed)) {
        String msg = mtlUsed.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        mtlUsed.saveCurrentData();
      }else if (comp.equals(waste)) {
        String msg = waste.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        waste.saveCurrentData();
      }else if (comp.equals(training)) {
        String msg = training.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        training.saveCurrentData();
      }else if (comp.equals(ppe)) {
        String msg = ppe.auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          GenericDlg.showMessage(msg);
          return;
        }
        ppe.saveCurrentData();
      }
      //Now save data
      SaveCurrentData("Save");
    }
  }

  void cancelB_actionPerformed(ActionEvent e) {
    Hashtable h = (Hashtable)facWorkAreaInfoH.get(currentFacID+currentWorkAreaID);
    h.put("LOADED",new Boolean(false));
    cancelled = true;
    lastSelectedComponent = optDef;
    emissionPt.lastSelectedProcessID = "";
    mtlUsed.lastSelectedProcessID = "";
    waste.lastSelectedProcessID = "";
    training.lastSelectedProcessID = "";
    ppe.lastSelectedProcessID = "";
    //stop timer
    if ("Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
        "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
      apprPane.stopTimer();
    }
    this.setVisible(false);
  }  //end of method

  void this_windowClosing(WindowEvent e) {
    Hashtable h = (Hashtable)facWorkAreaInfoH.get(currentFacID+currentWorkAreaID);
    h.put("LOADED",new Boolean(false));
    cancelled = true;
    lastSelectedComponent = optDef;
    emissionPt.lastSelectedProcessID = "";
    mtlUsed.lastSelectedProcessID = "";
    waste.lastSelectedProcessID = "";
    training.lastSelectedProcessID = "";
    ppe.lastSelectedProcessID = "";
    this.setVisible(false);
  } //end of method
}  //end of class

class ProcessDetail_okB_actionAdapter implements java.awt.event.ActionListener {
  ProcessDetail adaptee;

  ProcessDetail_okB_actionAdapter(ProcessDetail adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class ProcessDetail_saveB_actionAdapter implements java.awt.event.ActionListener {
  ProcessDetail adaptee;

  ProcessDetail_saveB_actionAdapter(ProcessDetail adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.saveB_actionPerformed(e);
  }
}

class ProcessDetail_cancelB_actionAdapter implements java.awt.event.ActionListener {
  ProcessDetail adaptee;

  ProcessDetail_cancelB_actionAdapter(ProcessDetail adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}



