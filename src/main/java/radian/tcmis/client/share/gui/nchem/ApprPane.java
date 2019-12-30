
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;

import radian.tcmis.client.share.helpers.*;
import java.beans.*;
import com.borland.jbcl.layout.*;


public class ApprPane extends JPanel {  // NewChemPanel {
  CmisApp cmis;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel topP = new JPanel();
  JPanel centerP = new JPanel();
  JComboBox facC = new JComboBox();
  JComboBox workAreaC = new JComboBox();
  JComboBox userGroupC = new JComboBox();
  JPanel userP = new JPanel();
  JTextField qty2T = new JTextField();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  JTextField per2T = new JTextField();
  JComboBox period2C = new JComboBox();
  JComboBox period1C = new JComboBox();
  StaticJLabel jLabel3 = new StaticJLabel();
  StaticJLabel jLabel4 = new StaticJLabel();
  JTextField qty1T = new JTextField();
  JTextField per1T = new JTextField();
  StaticJLabel jLabel5 = new StaticJLabel();
  StaticJLabel jLabel6 = new StaticJLabel();
  StaticJLabel jLabel7 = new StaticJLabel();
  JPanel fateP = new JPanel();
  JTabbedPane fateTab = new JTabbedPane();
  String[] fateCols = {"Product","Waste Water","Air","Solid Waste","Recycling",
                       "Hazardous Waste","Other","Total"};

  Vector listTableCols = new Vector();

  JScrollPane descSP = new JScrollPane();
  JTextArea procDescT = new JTextArea();
  JButton addB = new JButton();
  JButton delB = new JButton();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane listSP = new JScrollPane();

  MatPane matPane = null;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JTable listT = new JTable();

  NewChem nchem = null;

  Hashtable groups = null;
  Hashtable userXref = null;

  ApprPane_facC_actionAdapter facilityActionListener = null;

  /**
    keeps the values on the table
  */
  Hashtable tableHash = new Hashtable();

  //trong 4/1
  Vector fateTotalV = null;
  boolean myEval = false;
  String facilityId = null;
  String workArea = null;
  boolean hasFate = false;
  JPanel paintBoothP = new JPanel();
  JCheckBox jCheckBox1 = new JCheckBox();
  JTextField paintT = new JTextField();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  StaticJLabel percentL = new StaticJLabel();
  JTextArea jTextArea1 = new JTextArea();

  JButton processDetailB = new JButton("Click Here for Process Detail");
  boolean processDetailRequired = false;
  ProcessDetail pd = null;
  Vector waProcessDetailClickedV = new Vector();

  //6-06-01
  String paintBoothPercent = "";
  boolean paintBooth = false;

  //TIMER
  private javax.swing.Timer timerT = null;
  private int editTimeOut = 1680000;        //28 minutes
  private int dlgTimeOut = 60000;         //60 seconds
  private ConfirmNewDlg cdlg;
  private javax.swing.Timer dlgTimer = null;

  private int COST_ELEMENT_COL = 8;
  private int KEY_COL = 0;
  private Hashtable directedChargeH = new Hashtable(13);

  public void setPaintBooth(boolean b) {
    paintBooth = b;
  }

  public ApprPane() {
    super();
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setCmisApp(CmisApp cmis) {
    this.cmis = cmis;
  }

  private void jbInit() throws Exception {

    //Colors and fonts
    this.setLayout(borderLayout1);
    ClientHelpObjs.makeDefaultColors(this);
    topP.setLayout(gridBagLayout2);
    userP.setLayout(gridBagLayout1);
    jLabel1.setText("Qty");
    jLabel2.setText("per");
    jLabel3.setText("Qty");
    jLabel4.setText("per");
    jLabel5.setText("Facility: *");
    jLabel6.setText("Work Area: *");

    jLabel7.setText("User Group:");
    listTableCols.addElement("key");
    listTableCols.addElement("Facility");
    listTableCols.addElement("Work Area");
    listTableCols.addElement("User Group");
    listTableCols.addElement("Restriction 1");
    listTableCols.addElement("Restriction 2");
    listTableCols.addElement("Proc?");
    listTableCols.addElement("Fate?");

    topP.setMinimumSize(new Dimension(632, 250));
    topP.setPreferredSize(new Dimension(554, 250));
    addB.setText("Add/Modify");
    addB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });
    addB.setToolTipText("Add to summary");    //trong

    delB.setText("Remove");
    delB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delB_actionPerformed(e);
      }
    });
    delB.setMaximumSize(new Dimension(93, 27));
    delB.setMinimumSize(new Dimension(93, 27));
    delB.setPreferredSize(new Dimension(93, 27));
    delB.setToolTipText("Remove from summary"); //trong

    centerP.setLayout(borderLayout2);
    fateP.setMinimumSize(new Dimension(300, 47));
    fateP.setPreferredSize(new Dimension(350, 0));
    listSP.setPreferredSize(new Dimension(12, 100));
    centerP.setPreferredSize(new Dimension(0, 160));
    descSP.setPreferredSize(new Dimension(19, 100));
    userP.setPreferredSize(new Dimension(170, 107));
    workAreaC.setMinimumSize(new Dimension(200, 24));
    workAreaC.setPreferredSize(new Dimension(200, 24));

    facC.setMinimumSize(new Dimension(200, 24));
    facC.setPreferredSize(new Dimension(200, 24));
    fateTab.setFont(new java.awt.Font("Dialog", 0, 10));
    fateTab.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        fateTab_propertyChange(e);
      }
    });
    listT.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        listT_mouseClicked(e);
      }
    });
    qty1T.setMaximumSize(new Dimension(50, 21));
    qty1T.setMinimumSize(new Dimension(50, 21));
    qty1T.setPreferredSize(new Dimension(50, 21));
    qty2T.setMaximumSize(new Dimension(50, 21));
    qty2T.setMinimumSize(new Dimension(50, 21));
    qty2T.setPreferredSize(new Dimension(50, 21));
    per1T.setMaximumSize(new Dimension(50, 21));
    per1T.setMinimumSize(new Dimension(50, 21));
    per1T.setPreferredSize(new Dimension(50, 21));
    per2T.setMaximumSize(new Dimension(50, 21));
    per2T.setMinimumSize(new Dimension(50, 21));
    per2T.setPreferredSize(new Dimension(50, 21));
    period1C.setMaximumSize(new Dimension(100, 21));
    period1C.setMinimumSize(new Dimension(100, 21));
    period1C.setPreferredSize(new Dimension(100, 21));
    period2C.setMaximumSize(new Dimension(100, 21));
    period2C.setMinimumSize(new Dimension(100, 21));
    period2C.setPreferredSize(new Dimension(100, 21));

    jCheckBox1.setDoubleBuffered(true);
    jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jCheckBox1_actionPerformed(e);
      }
    });
    paintBoothP.setLayout(gridBagLayout3);
    paintT.setMaximumSize(new Dimension(35, 21));
    paintT.setMinimumSize(new Dimension(35, 21));
    paintT.setPreferredSize(new Dimension(35, 21));
    percentL.setText("% in Booth");
    jTextArea1.setOpaque(false);
    jTextArea1.setBorder(null);
    jTextArea1.setSelectedTextColor(Color.darkGray);
    jTextArea1.setLineWrap(true);
    jTextArea1.setPreferredSize(new Dimension(70, 51));
    jTextArea1.setWrapStyleWord(true);
    jTextArea1.setDoubleBuffered(true);
    jTextArea1.setMaximumSize(new Dimension(70, 51));
    jTextArea1.setMinimumSize(new Dimension(70, 51));
    jTextArea1.setText("Using in painting operation");
    jTextArea1.setEditable(false);

    processDetailB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        processDetailB_actionPerformed(e);
      }
    });
    processDetailB.setMaximumSize(new Dimension(140, 27));
    processDetailB.setMinimumSize(new Dimension(140, 27));
    processDetailB.setPreferredSize(new Dimension(140, 27));


    this.setFont(new java.awt.Font("Dialog", 0, 10));
    this.add(topP, BorderLayout.CENTER);
    topP.add(fateP, new GridBagConstraints2(4, 0, 1, 5, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 6, 0, 6), 0, 0));
    topP.add(facC, new GridBagConstraints2(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 7, 0, 0), 0, 0));
    topP.add(jLabel5, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 7, 0, 112), 0, 0));
    topP.add(jLabel6, new GridBagConstraints2(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 7, 0, 0), 0, 0));
    topP.add(workAreaC, new GridBagConstraints2(1, 1, 3, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(userP, new GridBagConstraints2(0, 3, 3, 2, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 7, 0, 0), 0, 0));
    userP.add(jLabel7, new GridBagConstraints2(0, 0, 4, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
    userP.add(userGroupC, new GridBagConstraints2(0, 1, 5, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 2, 0, 0), 0, 0));
    userP.add(jLabel3, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
    userP.add(jLabel1, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 2, 0, 0), 0, 0));
    userP.add(qty2T, new GridBagConstraints2(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 0, 0, 0), 0, 0));
    userP.add(jLabel4, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    userP.add(jLabel2, new GridBagConstraints2(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 0, 0, 0), 0, 0));
    userP.add(per1T, new GridBagConstraints2(3, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    userP.add(per2T, new GridBagConstraints2(3, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 0, 0, 0), 0, 0));
    userP.add(period1C, new GridBagConstraints2(4, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
    userP.add(period2C, new GridBagConstraints2(4, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(9, 6, 0, 0), 0, 0));
    userP.add(qty1T, new GridBagConstraints2(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(descSP, new GridBagConstraints2(0, 2, 3, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 7, 0, 0), 0, 0));
    topP.add(addB, new GridBagConstraints2(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(23, 0, 0, 0), 0, 0));
    topP.add(delB, new GridBagConstraints2(3, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 0, 0, 0), 0, 0));
    topP.add(paintBoothP, new GridBagConstraints2(3, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    paintBoothP.add(jCheckBox1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    paintBoothP.add(paintT, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 0), 0, 0));
    paintBoothP.add(percentL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 40, 5, 5), 0, 0));
    paintBoothP.add(jTextArea1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 27, 0, 5), 0, 0));
    procDescT.setLineWrap(true);

    descSP.getViewport().add(procDescT, null);
    fateP.setLayout(new BorderLayout());
    fateP.add(fateTab, BorderLayout.CENTER);

    userP.setBorder(ClientHelpObjs.groupBox("Pre-Approved Up to"));
    listSP.setBorder(ClientHelpObjs.groupBox("Summary"));
    fateP.setBorder(ClientHelpObjs.groupBox("Fate"));
    //trong change groupBox -> groupBoxBlue
    descSP.setBorder(ClientHelpObjs.groupBox("Use and Process description *"));

    this.add(centerP, BorderLayout.SOUTH);
    centerP.add(listSP, BorderLayout.CENTER);
    listSP.getViewport().add(listT, null);

    ClientHelpObjs.makeDefaultColors(this);
  }

  //trong 3/25
  public void allowEdit(boolean f) {
    procDescT.setEnabled(f);
    delB.setEnabled(f);
    addB.setEnabled(f);
    qty1T.setEnabled(f);
    qty2T.setEnabled(f);
    per1T.setEnabled(f);
    per2T.setEnabled(f);
    period1C.setEnabled(f);
    period2C.setEnabled(f);
    userGroupC.setEnabled(f);
    workAreaC.setEnabled(f);
    facC.setEnabled(f);
    if (paintBooth) {
      jCheckBox1.setEnabled(f);
      paintT.setEnabled(f);
    }
  }
  public void setProcFateQty(){
    procDescT.setText("");
    qty1T.setText("");
    qty2T.setText("");
    per1T.setText("");
    per2T.setText("");
    period1C.setSelectedIndex(0);
    period2C.setSelectedIndex(0);
    String key = null;
    for (int i =0;i<fateTab.getTabCount();i++){
      FatePane fp = (FatePane)fateTab.getComponentAt(i);
      JTable t = fp.getTable();
      for (int j = 0; j < t.getRowCount(); j++){
        t.getModel().setValueAt(new Integer(0),j,1);
      }
    }
  }
  void workAreaC_actionPerformed(ActionEvent e) {
    setProcFateQty();
  }
  public boolean isFatePer100(String key) {
    return false;
  }

  public boolean isEngEval() {
    return myEval;
  }
  public void setEngEval(boolean b) {
    this.myEval = b;
  }
  public void setFacilityId(String s) {
    this.facilityId = s;
  }
  public void setWorkArea(String s) {
    this.workArea = s;
  }

  public void printScreenData() {

  }


  public void setPeriod(Vector p){
     if (p.contains("Indefinite")) p.removeElement("Indefinite");
     if (p.contains("No shelf life")) p.removeElement("No shelf life");
     period1C = ClientHelpObjs.loadChoiceFromVector(period1C,p,false) ;
     period2C = ClientHelpObjs.loadChoiceFromVector(period2C,p,false) ;
  }


  public void setPane(){
    // set mat pane
    this.setMatPane(nchem.getMatPane());

    int fateCount = fateTab.getTabCount();
    int matCount  = matPane.partsNum.intValue();
    if (matCount < fateCount){
      for (int i=matCount;i<fateCount;i++){
        fateTab.removeTabAt(i);
      }
    } else if (matCount > fateCount){
      for (int i=fateCount;i<matCount;i++){
        FatePane fatePP = new FatePane(this,matPane);
        fatePP.setMinimumSize(new Dimension(300, 0));
        fatePP.setPreferredSize(new Dimension(300, 0));
        fateTab.addTab("Part "+(i+1),fatePP);
      }

    }


    topP.remove(facC);
    topP.remove(workAreaC);
    topP.validate();

    facilityActionListener = new ApprPane_facC_actionAdapter(this);

    if (!isEngEval()) {
      workAreaC.addPropertyChangeListener(new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
          if (e.getPropertyName().equalsIgnoreCase("loaded") ||
              e.getPropertyName().equalsIgnoreCase("selection changed")) {
            setProcFateQty();
          }
        }
      });
      workAreaC.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          setProcFateQty();
        }
      });
      workAreaC.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          workAreaC_actionPerformed(e);
        }
      });
    }

    workAreaC.setMinimumSize(new Dimension(200, 24));
    workAreaC.setPreferredSize(new Dimension(200, 24));
    facC.setMinimumSize(new Dimension(200, 24));
    facC.setPreferredSize(new Dimension(200, 24));
    topP.add(facC, new GridBagConstraints2(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 7, 0, 0), 0, 0));
    topP.add(workAreaC, new GridBagConstraints2(1, 1, 3, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    topP.validate();
  }  //end of method

  void displayProcessDetail() {
    Vector tmpFacID = (Vector)((Hashtable)((Hashtable)nchem.dynamicH.get("CATALOG_FAC_WA_INFO")).get(nchem.partPP.getCatalogID())).get("FACILITY_ID");
    Vector tmpFacProcessDetail = (Vector)((Hashtable)((Hashtable)nchem.dynamicH.get("CATALOG_FAC_WA_INFO")).get(nchem.partPP.getCatalogID())).get("FAC_PROCESS_DETAIL");
    descSP.getViewport().removeAll();
    if ("Y".equalsIgnoreCase((String)tmpFacProcessDetail.elementAt(tmpFacID.indexOf(getFacilityID())))) {
      descSP.getViewport().add(processDetailB, null);
      descSP.setBorder(ClientHelpObjs.groupBox(""));
      processDetailB.setEnabled(true);
      processDetailRequired = true;
    }else {
      descSP.getViewport().add(procDescT, null);
      descSP.setBorder(ClientHelpObjs.groupBox("Use and Process description *"));
      processDetailRequired = false;
    }
  }

  void processDetailB_actionPerformed(ActionEvent e) {
    String tmpWorkArea = getWorkAreaID();
    if (tmpWorkArea.startsWith("All") || tmpWorkArea.startsWith("Select")) {
      GenericDlg.showMessage("Please select a work area.");
      return;
    }
    if (BothHelpObjs.isBlankString(nchem.partPP.partNumT.getText().trim())) {
      GenericDlg.showMessage("Please enter a part number before proceed.");
      nchem.goPart();
      return;
    }
    if (pd == null) {
      pd = new ProcessDetail(cmis.getMain(),"Approval Detail for "+(String)facC.getSelectedItem()+"/"+(String)workAreaC.getSelectedItem(),true,cmis);
    }
    pd.setFacWorkAreaDesc((String)facC.getSelectedItem(),(String)workAreaC.getSelectedItem());
    pd.setFacWorkAreaIDs(getFacilityID(),tmpWorkArea);
    pd.setRequestID(nchem.reqL.getText());
    pd.setRequestStatus(nchem.currentStatus);
    pd.setViewLevel(nchem.viewL.getText());
    pd.setApprPane(this);
    pd.editableProcess = null;
    pd.editableProcess = new Vector();
    String tmpCatPartNo = nchem.partPP.partNumT.getText().trim();
    if (BothHelpObjs.isBlankString(tmpCatPartNo)) {
      tmpCatPartNo = "To Be Determined - "+nchem.reqL.getText();
    }
    pd.setTitle("Approval Detail for "+(String)facC.getSelectedItem()+"/"+(String)workAreaC.getSelectedItem()+"    Part No: "+tmpCatPartNo);
    Hashtable catAddDataH = new Hashtable(7);
    if (!"Viewer".equalsIgnoreCase(nchem.viewL.getText())) {
      //save section 1,2 and 3 if possible
      nchem.buildDataHash();
      catAddDataH.put("ACTION","DRAFT"); //String, String
      catAddDataH.put("USER_ID",cmis.getUserId().toString());
      catAddDataH.put("REQ_ID",nchem.reqL.getText());
      catAddDataH.put("MAT_DATA",nchem.matHash);
      catAddDataH.put("PART_DATA",nchem.partHash);
      catAddDataH.put("APPR_DATA",nchem.apprHash);
      catAddDataH.put("EVAL_TYPE",nchem.eval);
    }
    pd.loadItAction(catAddDataH);
    if (pd.loadDataOk) {
      //load display process data
      CenterComponent.centerCompOnScreen(pd);
      pd.setVisible(true);
      //add work area to summary line, otherwise remove work area lock
      if (!pd.cancelled) {
        waProcessDetailClickedV.addElement(getFacilityID()+tmpWorkArea);
        if (addAudit()) {
          addSelectedLine();
        } //end of addAudit
      }else {
        if ("Requestor".equalsIgnoreCase(nchem.viewL.getText())) {
          goRemoveProcessLock(nchem.reqL.getText(),getFacilityID(),tmpWorkArea);
        }
      }
    }
  } //end of method

  void startTimer() {
    //start timer
    if (timerT == null) {
      ActionListener al = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          showTimeOutDlg();
        }
      };
      timerT = new javax.swing.Timer(editTimeOut, al);
      timerT.start();
    }else {
      timerT.restart();
    }
  } //end of method

  void resetTimer() {
    if (timerT != null) {
      timerT.restart();
    }else {
      startTimer();
    }
  } //end of method

  void stopTimer() {
    if (timerT != null) {
      timerT.stop();
      timerT = null;
    }
  }

  void timeOut() {
    //make sure to stop timer and close time out messages and message timer
    if (timerT != null) {
      timerT.stop();
      timerT = null;
    }
    if (cdlg.isVisible()) {
      cdlg.setVisible(false);
      cdlg = null;
    }
    if (dlgTimer != null) {
      dlgTimer.stop();
      dlgTimer = null;
    }
    //close process detail and release work area lock
    pd.cancelled = true;
    pd.setVisible(false);
    //remove work area lock
    goRemoveProcessLock(nchem.reqL.getText(),getFacilityID(),getWorkAreaID());
    //tell the approver that we removed his/her work area lock
    GenericDlg err = new GenericDlg(cmis.getMain(), "Time Out", true);
    err.setMsg("Your session time out.\nIf you still want to work on this request.\nClick on 'Process Detail'");
    err.setVisible(true);
  } //end of method

  void showTimeOutDlg() {
    //start display timer
    if (dlgTimer == null) {
      ActionListener l = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          timeOut();
          return;
        }
      };
      dlgTimer = new javax.swing.Timer(dlgTimeOut, l);
      dlgTimer.start();
    }else {
      dlgTimer.restart();
    }
    //display time out messages
    cdlg = new ConfirmNewDlg(cmis.getMain(),"Time Out",true);
    cdlg.setMsg("Time Out.\nClick on 'Continue' if you want to continue working on this request.");
    cdlg.setVisible(true);
    if (cdlg != null) {
      if (cdlg.getConfirmationFlag()) {
        //reset work area lock
        goResetProcessLock(nchem.reqL.getText(), getFacilityID(),getWorkAreaID());
        timerT.restart();
      }else {
        timeOut();
        return;
      }
    }
    //just in case where timer is still running
    if (dlgTimer != null) {
      dlgTimer.stop();
      dlgTimer = null;
    }
  } //end of method

  void goRemoveProcessLock(String cReqID, String cFacID, String cWaID) {
    if (pd.editableProcess != null) {
      if (pd.editableProcess.size() < 1) {
        //nothing to remove
        return;
      }
    }else {
      //nothing to remove
      return;
    }
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj", cmis);
    Hashtable query = new Hashtable(7);
    query.put("ACTION", "REMOVE_APPLICATION_LOCK"); //String, String
    query.put("FACILITY_ID", cFacID);
    query.put("WORK_AREA_ID", cWaID);
    query.put("USER_ID",cmis.getUserId());
    query.put("VIEW_LEVEL",nchem.viewL.getText());
    query.put("REQUEST_ID",nchem.reqL.getText());
    query.put("EDITABLE_PROCESS",pd.editableProcess);
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      String no = new String("Access denied. Your session is corrupted, please restart the application.");
      GenericDlg err = new GenericDlg(cmis.getMain(), "Denied", true);
      err.setMsg(no);
      err.setVisible(true);
      CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
      return;
    }
    if (!"OK".equalsIgnoreCase((String)result.get("MSG"))) {
      GenericDlg.showMessage((String)result.get("MSG"));
    }
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
  } //end of method

  void goResetProcessLock(String cReqID, String cFacID, String cWaID) {
    if (pd.editableProcess != null) {
      if (pd.editableProcess.size() < 1) {
        //nothing to remove
        return;
      }
    }else {
      //nothing to remove
      return;
    }
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj", cmis);
    Hashtable query = new Hashtable(6);
    query.put("ACTION", "RESET_APPLICATION_LOCK"); //String, String
    query.put("FACILITY_ID", cFacID);
    query.put("WORK_AREA_ID", cWaID);
    query.put("USER_ID",cmis.getUserId());
    query.put("VIEW_LEVEL",nchem.viewL.getText());
    query.put("EDITABLE_PROCESS",pd.editableProcess);
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      String no = new String("Access denied. Your session is corrupted, please restart the application.");
      GenericDlg err = new GenericDlg(cmis.getMain(), "Denied", true);
      err.setMsg(no);
      err.setVisible(true);
      CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
      return;
    }
    if (!"OK".equalsIgnoreCase((String)result.get("MSG"))) {
      GenericDlg.showMessage((String)result.get("MSG"));
    }else {
      resetTimer();
    }
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
  } //end of method


  void setMatPane(MatPane ma){
     matPane = ma;
  }

  public void setNewChem(NewChem n){
     nchem = n;
  }

  public void setGroups(Hashtable h){
     groups = h;

  }

  public void setMaterialDescText(String[] t){
      for (int i=0;i<t.length;i++){
         if (BothHelpObjs.isBlankString(t[i])) continue;
         ((FatePane) this.getFateTab().getComponentAt(i)).setMatLabel(t[i]);
      }
  }

  public JTabbedPane getFateTab(){
     return this.fateTab;
  }

  public Hashtable getTableHash(){
     return tableHash;
  }

  public void setTableHash(Hashtable h){
     tableHash=h;
     Enumeration E = tableHash.keys();
     while (E.hasMoreElements()){
       String k = (String) E.nextElement();
       Hashtable hv = (Hashtable) tableHash.get(k);
       if (!BothHelpObjs.isBlankString((String)hv.get("DIRECTED_CHARGE"))) {
         directedChargeH.put(k,(String)hv.get("DIRECTED_CHARGE"));
       }
     }
  } //end of method

  protected void loadGroups(String fac){
     Vector grps = null;
     if (!groups.containsKey(fac)) return ;
     grps=(Vector) groups.get(fac);
     userGroupC.removeAllItems();
     userXref = new Hashtable();
     for(int i=0;i<grps.size();i++){
       userXref.put((String)((Hashtable)grps.elementAt(i)).get("DESC"),
                    (String)((Hashtable)grps.elementAt(i)).get("ID"));
       userGroupC.addItem((String)((Hashtable)grps.elementAt(i)).get("DESC"));
     }
     userP.revalidate();
  }

  void facC_actionPerformed(ActionEvent e) {
    Hashtable facAppForSelectedCatalog =  nchem.getPartPane().getFacAppForSelectedCatalog();
    Vector tmpFacIDV = (Vector)facAppForSelectedCatalog.get("FACILITY_ID");
    loadGroups((String)tmpFacIDV.elementAt(facC.getSelectedIndex()));
    Vector v = (Vector)facAppForSelectedCatalog.get((String)tmpFacIDV.elementAt(facC.getSelectedIndex()));
    Vector WADesc = (Vector)v.elementAt(1);
    if (WADesc.size() > 1) {
      Vector WAID = (Vector)v.elementAt(0);
      if (!WAID.contains("Select")) {
        WAID.insertElementAt("Select",0);
        WADesc.insertElementAt("Select a Work Area",0);
      }
      if (!WAID.contains("All") && "Y".equalsIgnoreCase(v.elementAt(2).toString())) {
        WAID.insertElementAt("All",1);
        WADesc.insertElementAt("All Work Areas",1);
      }
    }
    loadWorkArea((Vector)facAppForSelectedCatalog.get((String)tmpFacIDV.elementAt(facC.getSelectedIndex())));
    displayProcessDetail();
  }

  void loadWorkArea(Vector tmpWorkAreaIDV) {
    workAreaC.removeAllItems();
    workAreaC = ClientHelpObjs.loadChoiceFromVector(workAreaC,(Vector)tmpWorkAreaIDV.elementAt(1));
  }

  public String getFacilityID() {
    Hashtable facAppForSelectedCatalog =  nchem.getPartPane().getFacAppForSelectedCatalog();
    Vector tmpFacIDV = (Vector)facAppForSelectedCatalog.get("FACILITY_ID");
    return ((String)tmpFacIDV.elementAt(facC.getSelectedIndex()));
  }

  public String getFacilityDesc(String facID) {
    Hashtable facAppForSelectedCatalog =  nchem.getPartPane().getFacAppForSelectedCatalog();
    Vector tmpFacIDV = (Vector)facAppForSelectedCatalog.get("FACILITY_ID");
    Vector tmpFacDescV = (Vector)facAppForSelectedCatalog.get("FACILITY_DESC");
    int myIndex = 0;
    for (int i = 0; i < tmpFacIDV.size(); i++) {
      if (facID.equals((String)tmpFacIDV.elementAt(i))) {
        myIndex = i;
        break;
      }
    }
    return ((String)tmpFacDescV.elementAt(myIndex));
  }

  public String getWorkAreaID() {
    Hashtable facAppForSelectedCatalog =  nchem.getPartPane().getFacAppForSelectedCatalog();
    Vector tmpFacIDV = (Vector)facAppForSelectedCatalog.get("FACILITY_ID");
    Vector tmpWorkAreaV = (Vector)facAppForSelectedCatalog.get((String)tmpFacIDV.elementAt(facC.getSelectedIndex()));
    Vector tmpWorkAreaIDV = (Vector)tmpWorkAreaV.elementAt(0);
    return ((String)tmpWorkAreaIDV.elementAt(workAreaC.getSelectedIndex()));
  }

  public String getWorkAreaDesc(String facID, String waID) {
    Hashtable facAppForSelectedCatalog =  nchem.getPartPane().getFacAppForSelectedCatalog();
    Vector tmpWorkAreaV = (Vector)facAppForSelectedCatalog.get(facID);
    Vector tmpWorkAreaDescV = (Vector)tmpWorkAreaV.elementAt(1);
    Vector tmpWorkAreaIDV = (Vector)tmpWorkAreaV.elementAt(0);
    int myIndex = 0;
    for (int i = 0; i < tmpWorkAreaIDV.size(); i++) {
      if (waID.equals((String)tmpWorkAreaIDV.elementAt(i))) {
        myIndex = i;
        break;
      }
    }
    return ((String)tmpWorkAreaDescV.elementAt(myIndex));
  }

  public void canChangeCatFacWA() {
    if (listT.getRowCount() > 0) {
      facC.setEnabled(false);
      if ("Y".equals(nchem.catAddSingleApp)) {
        workAreaC.setEnabled(false);
      }
    }
  }

  public void changeFacilityWorkArea() {
    facC.removeActionListener(facilityActionListener);
    facC.removeAllItems();
    workAreaC.removeAllItems();

    Hashtable facAppForSelectedCatalog =  nchem.getPartPane().getFacAppForSelectedCatalog();
    facC = ClientHelpObjs.loadChoiceFromVector(facC,(Vector)facAppForSelectedCatalog.get("FACILITY_DESC"));
    facC.addActionListener(facilityActionListener);

    Vector tmpFacIDV = (Vector)facAppForSelectedCatalog.get("FACILITY_ID");
    Vector v = (Vector)facAppForSelectedCatalog.get((String)tmpFacIDV.elementAt(facC.getSelectedIndex()));
    Vector WADesc = (Vector)v.elementAt(1);
    if (WADesc.size() > 1) {
      Vector WAID = (Vector)v.elementAt(0);
      if (!WAID.contains("Select")) {
        WAID.insertElementAt("Select",0);
        WADesc.insertElementAt("Select a Work Area",0);
      }
      if (!WAID.contains("All") && "Y".equalsIgnoreCase(v.elementAt(2).toString())) {
        WAID.insertElementAt("All",1);
        WADesc.insertElementAt("All Work Areas",1);
      }
    }
    workAreaC = ClientHelpObjs.loadChoiceFromVector(workAreaC,WADesc);
    loadGroups((String)tmpFacIDV.elementAt(facC.getSelectedIndex()));
  }

  boolean addAudit() {
     if (workAreaC.getSelectedItem().toString().equalsIgnoreCase("Select a Work Area")){
      GenericDlg.showMessage("You must select a Work Area");
      return false;
     }

     if (processDetailRequired) {
       if (!waProcessDetailClickedV.contains(getFacilityID()+getWorkAreaID())) {
         GenericDlg.showMessage("You must enter process detail for this request.");
         return false;
       }
     }else {
       if (procDescT.getText().length() <= 0) {
         GenericDlg.showMessage("You must enter something for process description.");
         return false;
       }
     }

     //trong 8-18 if user enter something for qty then they are required to enter something for period
     String value = qty1T.getText();
     if (!BothHelpObjs.isBlankString(value)) {
      if (!isPositiveNumber(value)) {
        GenericDlg.showMessage("Please enter a positive number for 'Qty'.");
        return false;
      }
     }
     value = qty2T.getText();
     if (!BothHelpObjs.isBlankString(value)) {
      if (!isPositiveNumber(value)) {
        GenericDlg.showMessage("Please enter a positive number for 'Qty'.");
        return false;
      }
     }
     value = per1T.getText();
     if (!BothHelpObjs.isBlankString(value)) {
      if (!isPositiveNumber(value)) {
        GenericDlg.showMessage("Please enter a positive number for 'per'.");
        return false;
      }
     }
     value = per2T.getText();
     if (!BothHelpObjs.isBlankString(value)) {
      if (!isPositiveNumber(value)) {
        GenericDlg.showMessage("Please enter a positive number for 'per'.");
        return false;
      }
     }
     if ((!BothHelpObjs.isBlankString(qty1T.getText())) && (BothHelpObjs.isBlankString(per1T.getText()))) {
       GenericDlg.showMessage("You entered an amount for 'Qty', but not for 'per'.\nPlease enter a positive number for 'per'.");
       return false;
     }
     if ((!BothHelpObjs.isBlankString(qty2T.getText())) && (BothHelpObjs.isBlankString(per2T.getText()))) {
       GenericDlg.showMessage("You entered an amount for 'Qty', but not for 'per'.\nPlease enter a positive number for 'per'.");
       return false;
     }
     if ((!BothHelpObjs.isBlankString(per1T.getText())) && (BothHelpObjs.isBlankString(qty1T.getText()))) {
       GenericDlg.showMessage("You entered an amount for 'per', but not for 'Qty'.\nPlease enter a positive number for 'Qty'.");
       return false;
     }
     if ((!BothHelpObjs.isBlankString(per2T.getText())) && (BothHelpObjs.isBlankString(qty2T.getText()))) {
       GenericDlg.showMessage("You entered an amount for 'per', but not for 'Qty'.\nPlease enter a positive number for 'Qty'.");
       return false;
     }

     //6-05-01
     if (this.jCheckBox1.isSelected()) {
      String percentUsed = BothHelpObjs.makeBlankFromNull(paintT.getText());
      if (BothHelpObjs.isBlankString(percentUsed)) {
        GenericDlg.showMessage("Please enter a positive number for % used in Booth.");
        return false;
      }else {
        try {
          int myP = Integer.parseInt(percentUsed);
          if (myP < 0 || myP > 100) {
            GenericDlg.showMessage("Invalid percentage.  Please enter a percent between 0 and 100.");
            return false;
          }
        }catch (Exception myE) {
          GenericDlg.showMessage("Please enter a positive number for % used in Booth.");
          return false;
        }
      }
     }
     return true;
  } //end of method

  void addB_actionPerformed(ActionEvent e) {
    if (addAudit()) {
      addSelectedLine();
    }
  }

  boolean isPositiveNumber(String value){
    int number = 0;
    try {
      number = Integer.parseInt(value);
      if (number <= 0) {
        return false;
      }
    }catch(Exception e){
      e.printStackTrace();
      return false;
    }
    return true;
  }


  void delB_actionPerformed(ActionEvent e) {
     delSelectedLine();
  }

  void  addSelectedLine(){
     // audit fate
     for (int i=0;i<fateTab.getComponentCount();i++){
       FatePane fp = (FatePane)fateTab.getComponentAt(i);
       fp.doAudit();
     }

     String key = getFacilityID()+getWorkAreaID();
     if (tableHash.containsKey(key)) tableHash.remove(key);

     //trong 3/26 when press addB if user entered only on line then set it to the first restriction
     if (BothHelpObjs.isBlankString(qty1T.getText()) && (!BothHelpObjs.isBlankString(qty2T.getText()))) {
        qty1T.setText(qty2T.getText());
        per1T.setText(per2T.getText());
        period1C.setSelectedItem(period2C.getSelectedItem());
        qty2T.setText("");
        per2T.setText("");
        period2C.setSelectedIndex(0);
     }

     //  fills hashtable's hash
     Hashtable values = new Hashtable();
     values.put("FAC",(String) facC.getSelectedItem());
     values.put("WA", (String) workAreaC.getSelectedItem());
     values.put("FACID",getFacilityID());
     values.put("WAID",getWorkAreaID());
     values.put("PROCESS",this.procDescT.getText());
     values.put("UG",(String)userGroupC.getSelectedItem());
     values.put("UGID",(String) userXref.get((String)userGroupC.getSelectedItem()));
     values.put("QTY1",qty1T.getText());
     values.put("PER1",per1T.getText());
     values.put("UNIT1",(String)period1C.getSelectedItem());
     values.put("QTY2",qty2T.getText());
     values.put("PER2",per2T.getText());
     values.put("UNIT2",(String)period2C.getSelectedItem());
     if (jCheckBox1.isVisible()) {
      paintBooth = true;
      values.put("PAINT_BOOTH",new Boolean(this.jCheckBox1.isSelected()));
      values.put("PAINT_BOOTH_PERCENT",BothHelpObjs.makeBlankFromNull(paintT.getText()));
     }

     fateTotalV = new Vector();

     for (int i =0;i<fateTab.getTabCount();i++){
         FatePane fp = (FatePane)fateTab.getComponentAt(i);
         JTable t = fp.getTable();
         Vector v = new Vector();
         int count = 0;    //trong 4/1
         for (int j=0;j<t.getModel().getRowCount()-1;j++,count++){
            v.addElement(BothHelpObjs.makeBlankFromNull((String) t.getModel().getValueAt(j,0)));
            v.addElement(String.valueOf(BothHelpObjs.makeZeroFromNull(t.getModel().getValueAt(j,1).toString())));
         }
         values.put("FATE"+i,v);

         //trong 4/1 putting fate percent into a vector
         fateTotalV.addElement(String.valueOf(BothHelpObjs.makeZeroFromNull(t.getModel().getValueAt(count,1).toString())));
         values.put("TOTAL",fateTotalV);

     }

     tableHash.put(key,values);
     buildTable(key);

     facC.setEnabled(false);
     if ("Y".equals(nchem.catAddSingleApp)) {
      workAreaC.setEnabled(false);
     }
  }  //end of method

 public  void buildTable(String key){
     /**
       Put some data on the table
     */
     if (tableHash==null) return ;

     //directed charge
     boolean directedCharge = false;
     if (!nchem.isEval.booleanValue()) {
       String tmpCatalogId = nchem.getPartPane().getCatalogID();
       if (nchem.catalogDirectedChargeInfo.containsKey(tmpCatalogId)) {
         if ("Optional".equalsIgnoreCase( (String) nchem.catalogDirectedChargeInfo.get(tmpCatalogId)) ||
             "Required".equalsIgnoreCase( (String) nchem.catalogDirectedChargeInfo.get(tmpCatalogId))) {
           if (!listTableCols.contains("Charge Number")) {
             listTableCols.addElement("Charge Number");
           }
           COST_ELEMENT_COL = listTableCols.size() - 1;
           directedCharge = true;
         }
       }
     }

     Vector tData = new Vector();
     Enumeration E;
     int r=0;
     int count = 0;          //trong 4/1
     for (E=tableHash.keys();E.hasMoreElements();count++){
       String k = (String) E.nextElement();
       Hashtable hv = (Hashtable) tableHash.get(k);
       Vector col = new Vector();
       col.addElement(k);
       col.addElement((String)hv.get("FAC"));
       col.addElement((String)hv.get("WA"));
       col.addElement((String)hv.get("UG"));
       String myqty1 = (String)hv.get("QTY1");          //trong 3/30
       String myqty2 = (String)hv.get("QTY2");          //trong 3/30
       if (BothHelpObjs.isBlankString(myqty1) || myqty1.equalsIgnoreCase("0")){
          col.addElement("Unlimited"); //trong change " " -> 'unlimited'
       } else {
          col.addElement((String)hv.get("QTY1")+" per "+(String)hv.get("PER1")+" "+(String)hv.get("UNIT1"));
       }
       if (BothHelpObjs.isBlankString(myqty2) || myqty2.equalsIgnoreCase("0")){
          col.addElement("Unlimited"); //trong change " " -> 'unlimited
       } else {
          col.addElement((String)hv.get("QTY2")+" per "+(String)hv.get("PER2")+" "+(String)hv.get("UNIT2"));
       }
       String pro = (String)hv.get("PROCESS");
       if (pro.length() > 0) {
          col.addElement(new String("    x"));
       }else {
          col.addElement(new String(""));
       }

       isFate(hv);

       if (hasFate) {
        col.addElement(new String("    x"));
        hasFate = false;
       } else {
        col.addElement(new String(""));
       }

       if (paintBooth) {
        Boolean bbb = (Boolean)hv.get("PAINT_BOOTH");
        if (bbb.booleanValue()) {
          col.addElement((String)hv.get("PAINT_BOOTH_PERCENT")+" percent");
        }else {
          col.addElement(new String(""));
        }
       }

       if (directedCharge) {
         if (directedChargeH.containsKey(k)) {
           col.addElement(directedChargeH.get(k).toString());
           hv.put("DIRECTED_CHARGE",directedChargeH.get(k).toString());
         }else {
           col.addElement(new String(""));
           hv.put("DIRECTED_CHARGE","");
         }
       }else {
         hv.put("DIRECTED_CHARGE","");
       }

       tData.addElement(col);
     }

     CmisTableModel tm = new CmisTableModel(tData,listTableCols);
     tm.setEditableFlag(0);
     listT = new JTable(tm);
     listT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     listT.getColumn("key").setWidth(0);
     listT.getColumn("key").setMinWidth(0);
     listT.getColumn("key").setMaxWidth(0);

     // set column widths
      listT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
      listT.getColumn("Facility").setPreferredWidth(130);
      listT.getColumn("Facility").setWidth(130);
      listT.getColumn("Work Area").setPreferredWidth(200);
      listT.getColumn("Work Area").setWidth(200);
      listT.getColumn("User Group").setPreferredWidth(115);
      listT.getColumn("User Group").setWidth(115);

     if (key!=null){
       for (int i=0;i<listT.getModel().getRowCount();i++){
          if (((String)listT.getModel().getValueAt(i,0)).equals(key)){
             listT.setRowSelectionInterval(i,i);
             break;
          }
       }
     }


     listT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);   //trong 3/31 JTable.AUTO_RESIZE_ALL_COLUMNS);
     listSP.getViewport().add(listT, null);
     listT.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        listT_mouseClicked(e);
      }
      });

      AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
      Hashtable chargeTableStyle =  BothHelpObjs.getTableStyle();
      Color color = (Color)chargeTableStyle.get("COLOR");
      Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
      Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
      Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
      Integer r1 = (Integer)chargeTableStyle.get("INSET_RIGHT");
      Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
      renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r1.intValue()),a.intValue());
      //font and foreground and background color for columns heading
      String fontName = (String)chargeTableStyle.get("FONT_NAME");
      Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
      Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
      listT.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = listT.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }

      if (listT.getRowCount() > 0) {
        ListSelectionModel lsm = listT.getSelectionModel();
        lsm.setSelectionInterval(0,0);
        listT.scrollRectToVisible(listT.getCellRect(0,0,false));
        loadScreen((String)listT.getModel().getValueAt(0,0));
        facC.setEnabled(false);
        if ("Y".equals(nchem.catAddSingleApp)) {
          workAreaC.setEnabled(false);
        }
      }
  }   //end of method

  //trong 4/25
  void isFate(Hashtable h) {
    Enumeration E1;
    for (E1=h.keys();E1.hasMoreElements();){
      String key = (String) E1.nextElement();
      int total = 0;
      if (!key.startsWith("FATE")) continue;
      Vector v = (Vector) h.get(key);
      for (int i = 1; i < v.size(); i += 2) {
        Integer t = new Integer((String)v.elementAt(i));
        total += t.intValue();
      }
      if (total > 0) hasFate = true;
    }
  }


  void delSelectedLine(){
     /**
       check if hash has valus
     */
     int selected = listT.getSelectedRow();
     if (selected<0){
       GenericDlg.showMessage("Please select a row");
       return;
     }

     String key = (String)listT.getModel().getValueAt(selected,0);
     if (tableHash.containsKey(key)) tableHash.remove(key);

     buildTable(null);

     if (tableHash.size()==0 && !isEngEval() && !"Y".equals(nchem.catAddSingleApp)) {
        workAreaC.setEnabled(true);
        facC.setEnabled(true);
        if (!nchem.statusL.getText().startsWith("New Approval")) {     //user can't change anything in section 2
          nchem.getPartPane().catalogC.setEnabled(true);
        }
     }

     this.listT.repaint();
     this.centerP.repaint();

     //Remove work area lock if necessary
     if (processDetailRequired) {
       int selRow = 0;
       goRemoveProcessLock(nchem.reqL.getText(),getFacilityID(),getWorkAreaID());
       waProcessDetailClickedV.removeElement(getFacilityID()+getWorkAreaID());
     }
  }

  void eraseAllData(){

  }

  void fateTab_propertyChange(PropertyChangeEvent e) {
     FatePane fate = (FatePane) this.getFateTab().getSelectedComponent();
     fate.changeMaterialLabel(this.getFateTab().getSelectedIndex());
  }

  String auditCatalogPartDirectedCharge() {
    String msg = null;
    Enumeration E = tableHash.keys();
    while (E.hasMoreElements()){
      String k = (String) E.nextElement();
      Hashtable hv = (Hashtable) tableHash.get(k);
      if (BothHelpObjs.isBlankString((String)hv.get("DIRECTED_CHARGE"))) {
        msg = "At least one of the approval record is missing a Charge Number.";
      }
    }

    return msg;
  } //end of method

  void listT_mouseClicked(MouseEvent e) {
    int col = ((JTable)e.getSource()).columnAtPoint(new Point(e.getX(),e.getY()));
    int row = listT.getSelectedRow();
    if(e.getClickCount() > 1 && col == COST_ELEMENT_COL){
      String tmpCostElement = (String)listT.getModel().getValueAt(row,COST_ELEMENT_COL);
      SearchDirectedChargeDlg sdcd = new SearchDirectedChargeDlg(nchem.getCmis().getMain(),nchem.getPartPane().partNumT.getText(),tmpCostElement,true);
      sdcd.setGrandParent(nchem.cmis);
      sdcd.catalogID = nchem.getPartPane().getCatalogID();
      sdcd.loadIt();
      sdcd.setVisible(true);
      listT.getModel().setValueAt(sdcd.costElementT.getText(),row,COST_ELEMENT_COL);
      directedChargeH.put(listT.getModel().getValueAt(row,KEY_COL).toString(),sdcd.costElementT.getText());
      Hashtable hv = (Hashtable) tableHash.get(listT.getModel().getValueAt(row,KEY_COL).toString());
      hv.put("DIRECTED_CHARGE",sdcd.costElementT.getText());
      sdcd.dispose();
    }
    // load data on top
    CursorChange.setCursor(matPane.nchem.cmis.getMain(),Cursor.WAIT_CURSOR);
    loadScreen((String)listT.getModel().getValueAt(row,0));
    // refresh table
    CursorChange.setCursor(matPane.nchem.cmis.getMain(),Cursor.DEFAULT_CURSOR);
  } //end of method

  void loadScreen(String key){
     Hashtable hv  = (Hashtable) tableHash.get(key);
     this.facC.setSelectedItem((String)hv.get("FAC"));
     this.workAreaC.setSelectedItem((String)hv.get("WA"));
     this.userGroupC.setSelectedItem((String)hv.get("UG"));
     this.procDescT.setText((String)hv.get("PROCESS"));

     if(paintBooth) {
      Boolean pb = (Boolean)hv.get("PAINT_BOOTH");
      this.jCheckBox1.setSelected(pb.booleanValue());
      this.paintT.setText((String)hv.get("PAINT_BOOTH_PERCENT"));
      if (jCheckBox1.isSelected()) {
        paintT.setEnabled(true);
        percentL.setEnabled(true);
      }
     }

     String myqty1 = (String)hv.get("QTY1");
     String myqty2 = (String)hv.get("QTY2");
     String myper1 = (String)hv.get("PER1");
     String myper2 = (String)hv.get("PER2");
     if (myqty1.equalsIgnoreCase("0") || myqty2.equalsIgnoreCase("0") ||
         myper1.equalsIgnoreCase("0") || myper2.equalsIgnoreCase("0") ) {
      this.qty1T.setText("");
      this.qty2T.setText("");
      this.per1T.setText("");
      this.per2T.setText("");
      this.period1C.setSelectedIndex(0);
      this.period2C.setSelectedIndex(0);
     } else {
      this.qty1T.setText((String)hv.get("QTY1"));
      this.qty2T.setText((String)hv.get("QTY2"));
      this.per1T.setText((String)hv.get("PER1"));
      this.per2T.setText((String)hv.get("PER2"));
      this.period1C.setSelectedItem((String)hv.get("UNIT1"));
      this.period2C.setSelectedItem((String)hv.get("UNIT2"));
     }

     for (int i =0;i<fateTab.getTabCount();i++){
         Vector v = (Vector) hv.get("FATE"+i);
      if ( v != null) {     //put this back after test trong 4/26 -- take care of the fate stuffs
         Integer mytotalfate;
         int totalfate = 0;

         FatePane fp = (FatePane)fateTab.getComponentAt(i);
         JTable t = fp.getTable();
         int h=1;
         for (int j=0;j<t.getModel().getRowCount()-1;j++){
             if (v.size()<2){
               t.getModel().setValueAt(new Integer(0),j,1);
             } else {
               mytotalfate = new Integer(v.elementAt(h).toString());
               totalfate += mytotalfate.intValue();
               t.getModel().setValueAt(v.elementAt(h),j,1);
               h=h+2;
             }
         }
         // calculate total
         t.getModel().setValueAt(new Integer(totalfate),t.getModel().getRowCount()-1,1);  //trong
         if (totalfate > 0) hasFate = true;   //trong 4/25
         if(t.isEditing()){
          t.getCellEditor().stopCellEditing();
         }
      } // end of if
    }
  }

  void jCheckBox1_actionPerformed(ActionEvent e) {
    if (jCheckBox1.isSelected()) {
      paintT.setEnabled(true);
      percentL.setEnabled(true);
      if (BothHelpObjs.isBlankString(paintT.getText())) {
        paintT.setText(paintBoothPercent);
      }
    }else {
      paintT.setEnabled(false);
      percentL.setEnabled(false);
      paintT.setText("");
    }
  }
}

class ApprPane_facC_actionAdapter implements java.awt.event.ActionListener{
  ApprPane adaptee;

  ApprPane_facC_actionAdapter(ApprPane adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.facC_actionPerformed(e);
  }
}
