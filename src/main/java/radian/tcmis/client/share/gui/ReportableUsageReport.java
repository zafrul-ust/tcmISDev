/*
 /* Title:
  /* Version:
   /* Description:
    /*
     *  Copyright 2000
     *
     *  <URS - Radian Corporation>
     *  All rights reserved
     */
    package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import com.borland.jbcl.layout.*;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

/**
 *  Description of the Class
 *
 *@author     Shaik_Nawaz
 *@created    December 6, 2000
 */
public class ReportableUsageReport extends ReportInputPanel
//public class ReportableUsageReport extends JPanel
{
  //JPanel {  //
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  MonthCombo begMonC = new MonthCombo(0);
  YearCombo begYearC = new YearCombo( -2, 6, -1);
  MonthCombo endMonthC = new MonthCombo( -1);
  YearCombo endYearC = new YearCombo( -2, 6, 0);
  StaticJLabel jLabel2 = new StaticJLabel();
  ChemListsCombo groupC = new ChemListsCombo(cmis);
  ButtonGroup bg = new ButtonGroup();
  JPanel topP = new JPanel();
  JPanel bottomP = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JRadioButton listRB = new JRadioButton();
  JRadioButton singleRB = new JRadioButton();
  JRadioButton allRB = new JRadioButton();
  StaticJLabel jLabel4 = new StaticJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  StaticJLabel jLabel7 = new StaticJLabel();
  JComboBox orderC = new JComboBox();
  JPanel groupP = new JPanel();
  JButton groupB = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();
  JTree groupTree = new JTree();
  int[] groupBy = {
      0, 1, 2};
  int[] groupByVoc = {
      0, 1};
  String[] orderBy = new String[] {
      "Part Number", "Delivery Point", "Work Area"};
  String[] orderByVoc = new String[] {
      "Part Number", "Work Area"};
  JButton listB = new JButton();
  JButton selectChemB = new JButton();
  StaticJLabel jLabel6 = new StaticJLabel();
  JTextField casT = new JTextField();
  boolean isVoc = false;
  JRadioButton vocRB = new JRadioButton();
  boolean firstTime = true;
  Hashtable dataH;
  JComboBox facilityC = new JComboBox();
  JComboBox workAreaC = new JComboBox();

  /**
   *  Constructor for the ReportableUsageReport object
   *
   *@param  cmis  Description of Parameter
   */
  public ReportableUsageReport(CmisApp cmis) {
    this(cmis, false);
  }

  /**
   *  Constructor for the ReportableUsageReport object
   *
   *@param  cmis         Description of Parameter
   *@param  isVocReport  Description of Parameter
   */
  public ReportableUsageReport(CmisApp cmis, boolean isVocReport) {
    super(cmis, "Share", "Formatted Usage");
    isVoc = isVocReport;

    if (isVoc) {
      setReportTitle("Formatted VOC Usage");
      groupBy = groupByVoc;
      orderBy = orderByVoc;
    }
  }

  public String getFacilityDesc(String facID) {
    Vector facIDV = (Vector) dataH.get("FACILITY_ID");
    Vector facNameV = (Vector) dataH.get("FACILITY_NAME");
    int index = facIDV.indexOf(facID);
    if (index < 0) {
      index = 0;
    }
    return ( (String) facNameV.elementAt(index));
  }

  public String getFacilityID() {
    Vector facIDV = (Vector) dataH.get("FACILITY_ID");
    return ( (String) facIDV.elementAt(facilityC.getSelectedIndex()));
  }

  public String getWorkAreaID() {
    Hashtable innerH = (Hashtable) dataH.get(getFacilityID());
    Vector workIDV = (Vector) innerH.get("WORK_AREA_ID");
    return ( (String) workIDV.elementAt(workAreaC.getSelectedIndex()));
  }

  public void loadWorkArea() {
    Hashtable innerH = (Hashtable) dataH.get(getFacilityID());
    Vector workDescV = (Vector) innerH.get("WORK_AREA_DESC");
    workAreaC.removeAllItems();
    workAreaC = ClientHelpObjs.loadChoiceFromVector(workAreaC, workDescV);
    workAreaC.setSelectedIndex(0);
  }

  public void loadIt() {
    ReportableReportThread aw = new ReportableReportThread(this);
    aw.start();
  } //end of method

  void facility_itemStateChanged(ItemEvent e) {
    loadWorkArea();
  } //end of method

  void loadItAction() {
    TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj", cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION", "GET_FORMATTED_INITAL_DATA"); //String, String
    query.put("USERID", (new Integer(cmis.getUserId().intValue())).toString()); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      String no = new String("Access denied. Your session is corrupted, please restart the application.");
      GenericDlg err = new GenericDlg(cmis.getMain(), "Denied", true);
      err.setMsg(no);
      err.setVisible(true);
      return;
    }
    dataH = (Hashtable) result.get("DATA");
    Vector facNameV = (Vector) dataH.get("FACILITY_NAME");
    facilityC.removeAllItems();
    facilityC = ClientHelpObjs.loadChoiceFromVector(facilityC, facNameV);
    facilityC.setSelectedItem(getFacilityDesc(cmis.getPrefFac()));
    loadWorkArea();
    ClientHelpObjs.setComboBoxUpdateUi(this);
  } //end of method

  public void loadScreen() {
    if (firstTime) {
      if (isVoc) {
        setReportTitle("Formatted VOC Usage");
        groupBy = groupByVoc;
        orderBy = orderByVoc;
      }
      try {
        jbInit();

        String action = "GET_FORMATTED_INITAL_DATA";
        if (!isVoc) {
          groupC.loadIt();
        }
        loadIt();
        cmis.getMain().stopImage();
      } catch (Exception e) {
        e.printStackTrace();
      }
      firstTime = false;
      begYearC.removeAllItems();
      begYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), -1);
      endYearC.removeAllItems();
      endYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), 0);
    }
  }

  /**
   *  Sets the FacCombo attribute of the ReportableUsageReport object
   *
   *@param  fc  The new FacCombo value
   */
  public void setFacCombo(FacilityCombo fc) {
  }

  /**
   *  Description of the Method
   */
  public void showReport() {
    if (!checkDates(new Integer(begMonC.getSelectedIndex()).toString(),
                    begYearC.getSelectedItem().toString(),
                    new Integer(endMonthC.getSelectedIndex()).toString(),
                    endYearC.getSelectedItem().toString())) {
      showBadDatesDlg();
      return;
    }

    if (singleRB.isSelected()) {
      if (BothHelpObjs.isBlankString(casT.getText())) {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
        gd.setMsg("You must select a chemical.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }
    }
    String myTitle;
    if (isVoc) {
      myTitle = "VOC Usage Report";
    } else {
      myTitle = groupC.getSelectedListDesc();
    }

    ReportOptionDlg rurf = new ReportOptionDlg(cmis.getMain());
    rurf.setScreen("VocUsage Report");
    rurf.setFacilityWorkArea(getFacilityID(), facilityC.getSelectedItem().toString(), getWorkAreaID(), workAreaC.getSelectedItem().toString());
    rurf.setBegMonthYear(new Integer(begMonC.getSelectedIndex()).toString(), begYearC.getSelectedItem().toString());
    rurf.setEndMonthYear(new Integer(endMonthC.getSelectedIndex()).toString(), endYearC.getSelectedItem().toString());
    String s = orderC.getSelectedItem().toString();

    if (this.isVoc) {
      if (s.equals(orderBy[0])) {
        rurf.setSortBy(ReportOptionDlg.ORDER_BY_PART_NUM);
      } else if (s.equals(orderBy[1])) {
        rurf.setSortBy(ReportOptionDlg.ORDER_BY_WORK_AREA);
      }
    } else {
      if (s.equals(orderBy[0])) {
        rurf.setSortBy(ReportOptionDlg.ORDER_BY_PART_NUM);
      } else if (s.equals(orderBy[1])) {
        rurf.setSortBy(ReportOptionDlg.ORDER_BY_DEL_POINT);
      } else if (s.equals(orderBy[2])) {
        rurf.setSortBy(ReportOptionDlg.ORDER_BY_WORK_AREA);
      }
    }
    rurf.setGroupBy(groupBy);

    if (isVoc) {
      rurf.setChemType(ReportOptionDlg.VOC);
    } else if (listRB.isSelected()) {
      rurf.setChemType(ReportOptionDlg.LIST);
      rurf.setChemListId(groupC.getSelectedListId());
      rurf.setChemListDesc(groupC.getSelectedListDesc());
    } else if (singleRB.isSelected()) {
      rurf.setChemType(ReportOptionDlg.CAS_NUM_SEARCH);
      rurf.setCasNum(casT.getText());
    } else {
      rurf.setChemType(ReportOptionDlg.ALL);
    }

    rurf.setCmisApp(cmis);
    rurf.loadIt();
  } //end of method

  /**
   *  Description of the Method
   */
  void buildTree() {
    String[] myGroupString;
    if (isVoc) {
      myGroupString = new String[] {
          "Facility", "Work Area", "By Month"};
    } else {
      myGroupString = new String[] {
          "Facility", "Work Area", "CAS #/SARA Group", "Delivery Point", "By Month"};
    }

    Groupie root = new Groupie(myGroupString[groupBy[0]]);
    Groupie last = root;
    for (int i = 1; i < groupBy.length; i++) {
      Groupie g = new Groupie(myGroupString[groupBy[i]]);
      last.add(g);
      last = g;
    }
    groupTree = new JTree(root);

    groupTree.getUI();
    ComponentUI treeUI = groupTree.getUI();

    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
    if (treeUI instanceof BasicTreeUI) {
      ( (BasicTreeUI) treeUI).setExpandedIcon(null);
      ( (BasicTreeUI) treeUI).setCollapsedIcon(null);
    }
    renderer.setDisabledIcon(renderer.getClosedIcon());
    renderer.setLeafIcon(renderer.getClosedIcon());

    renderer.setOpenIcon(null);
    renderer.setDisabledIcon(null);
    renderer.setLeafIcon(null);

    groupTree.setCellRenderer(renderer);
    expandAll(root);
    groupTree.setEnabled(false);
    groupTree.setEditable(false);
    groupTree.validate();
    jsp.getViewport().setView(groupTree);

  }

  /**
   *  Description of the Method
   *
   *@param  tNode  Description of Parameter
   */
  void expandAll(TreeNode tNode) {
    TreePath tp = new TreePath( ( (DefaultMutableTreeNode) tNode).getPath());
    groupTree.expandPath(tp);
    for (int i = 0; i < tNode.getChildCount(); i++) {
      expandAll(tNode.getChildAt(i));
    }
  }

  /**
   *  Description of the Method
   */
  void radioButtonClicked() {
    groupC.setEnabled(listRB.isSelected());
    listB.setEnabled(listRB.isSelected());
    selectChemB.setEnabled(singleRB.isSelected());
    casT.setEnabled(singleRB.isSelected());
    jLabel6.setEnabled(singleRB.isSelected());
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void groupB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    ReportGroupDlg rgd = new ReportGroupDlg(cmis, groupBy, isVoc);
    CenterComponent.centerCompOnScreen(rgd);
    rgd.setVisible(true);
    groupBy = rgd.getGroupBy();
    //buildTree();
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void listRB_actionPerformed(ActionEvent e) {
    if (listRB.isSelected()) {
      vocRB.setSelected(false);
    }
    radioButtonClicked();
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void singleRB_actionPerformed(ActionEvent e) {
    if (singleRB.isSelected()) {
      vocRB.setSelected(false);
    }
    radioButtonClicked();
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void allRB_actionPerformed(ActionEvent e) {
    if (allRB.isSelected()) {
      vocRB.setSelected(false);
    }
    radioButtonClicked();
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void listB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    ChemListDlg cld = new ChemListDlg(cmis, groupC);
    CenterComponent.centerCompOnScreen(cld);
    cld.setVisible(true);
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void selectChemB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    ChemSelectDlg csd = new ChemSelectDlg(cmis);
    CenterComponent.centerCompOnScreen(csd);
    csd.setVisible(true);
    if (!csd.isCanceled()) {
      casT.setText(csd.getCasNumber());
    }
  }

  /**
   *  Description of the Method
   *
   *@exception  Exception  Description of Exception
   */
  private void jbInit() throws Exception {
    groupB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));
    listB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    selectChemB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));

    //groupC.loadIt();
    jLabel2.setText("Ending Month:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    groupC.setMaximumSize(new Dimension(500, 24));
    groupC.setMinimumSize(new Dimension(350, 24));
    groupC.setPreferredSize(new Dimension(350, 24));
    jLabel1.setText("Facility:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);

    listRB.addActionListener(
        new java.awt.event.ActionListener() {

      /**
       *  Description of the Method
       *
       *@param  e  Description of Parameter
       */
      public void actionPerformed(ActionEvent e) {
        listRB_actionPerformed(e);
      }
    });
    singleRB.addActionListener(
        new java.awt.event.ActionListener() {

      /**
       *  Description of the Method
       *
       *@param  e  Description of Parameter
       */
      public void actionPerformed(ActionEvent e) {
        singleRB_actionPerformed(e);
      }
    });
    allRB.addActionListener(
        new java.awt.event.ActionListener() {

      /**
       *  Description of the Method
       *
       *@param  e  Description of Parameter
       */
      public void actionPerformed(ActionEvent e) {
        allRB_actionPerformed(e);
      }
    });
    listB.setText("View List");
    listB.addActionListener(
        new java.awt.event.ActionListener() {

      /**
       *  Description of the Method
       *
       *@param  e  Description of Parameter
       */
      public void actionPerformed(ActionEvent e) {
        listB_actionPerformed(e);
      }
    });
    selectChemB.setText("Select Chemical");
    selectChemB.addActionListener(
        new java.awt.event.ActionListener() {

      /**
       *  Description of the Method
       *
       *@param  e  Description of Parameter
       */
      public void actionPerformed(ActionEvent e) {
        selectChemB_actionPerformed(e);
      }
    });
    jLabel6.setText(" CAS Number:");
    casT.setMaximumSize(new Dimension(100, 21));
    casT.setMinimumSize(new Dimension(100, 21));
    casT.setPreferredSize(new Dimension(100, 21));
    vocRB.setText("VOC");
    vocRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        vocRB_actionPerformed(e);
      }
    });
    bg.add(listRB);
    bg.add(singleRB);
    bg.add(allRB);
    listRB.setSelected(true);

    topP.setMaximumSize(new Dimension(32767, 115));
    topP.setMinimumSize(new Dimension(10, 115));
    topP.setPreferredSize(new Dimension(700, 115));
    topP.setLayout(gridBagLayout3);
    bottomP.setMaximumSize(new Dimension(32767, 1000));
    bottomP.setMinimumSize(new Dimension(10, 400));
    bottomP.setPreferredSize(new Dimension(200, 400));
    bottomP.setLayout(gridBagLayout2);
    listRB.setText(" List");
    singleRB.setText(" Single Chemical");
    allRB.setText("All");
    jLabel4.setText("Work Area:");
    workAreaC.setMaximumSize(new Dimension(200, 24));
    workAreaC.setMinimumSize(new Dimension(200, 24));
    workAreaC.setPreferredSize(new Dimension(200, 24));
    facilityC.setMaximumSize(new Dimension(200, 24));
    facilityC.setMinimumSize(new Dimension(200, 24));
    facilityC.setPreferredSize(new Dimension(200, 24));
    facilityC.addItemListener(new Report_facility_itemAdapter(this));
    jLabel5.setText("Group By:");
    jLabel7.setText("Order By:");

    topP.setBorder(ClientHelpObjs.groupBox("Chemicals to report"));
    bottomP.setBorder(ClientHelpObjs.groupBox("Report Parameters"));

    orderC.setModel(new DefaultComboBoxModel(orderBy));
    orderC.setSelectedIndex(0);
    orderC.setMaximumSize(new Dimension(150, 24));
    orderC.setMinimumSize(new Dimension(150, 24));
    orderC.setPreferredSize(new Dimension(150, 24));
    groupP.setMaximumSize(new Dimension(200, 100));
    groupP.setMinimumSize(new Dimension(200, 100));
    groupP.setPreferredSize(new Dimension(200, 100));
    groupP.setLayout(borderLayout1);
    groupB.setText("Set Group");
    groupB.addActionListener(
        new java.awt.event.ActionListener() {
      /**
       *  Description of the Method
       *
       *@param  e  Description of Parameter
       */
      public void actionPerformed(ActionEvent e) {
        groupB_actionPerformed(e);
      }
    });
    jLabel3.setText("Begining Month:");
    begMonC.setMaximumSize(new Dimension(100, 24));
    begMonC.setMinimumSize(new Dimension(100, 24));
    begMonC.setPreferredSize(new Dimension(100, 24));
    begYearC.setMaximumSize(new Dimension(70, 24));
    begYearC.setMinimumSize(new Dimension(70, 24));
    begYearC.setPreferredSize(new Dimension(70, 24));
    endMonthC.setMaximumSize(new Dimension(100, 24));
    endMonthC.setMinimumSize(new Dimension(100, 24));
    endMonthC.setPreferredSize(new Dimension(100, 24));
    endYearC.setMaximumSize(new Dimension(70, 24));
    endYearC.setMinimumSize(new Dimension(70, 24));
    endYearC.setPreferredSize(new Dimension(70, 24));

    this.setLayout(new BorderLayout());

    bottomP.add(jLabel3, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 10, 3, 3), 0, 0));
    bottomP.add(begMonC, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(begYearC, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(endMonthC, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(endYearC, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(jLabel2, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(jLabel4, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(workAreaC, new GridBagConstraints2(1, 1, 2, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(facilityC, new GridBagConstraints2(1, 0, 2, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(jLabel5, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(jLabel7, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(orderC, new GridBagConstraints2(1, 3, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(groupP, new GridBagConstraints2(1, 2, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    groupP.add(jsp, BorderLayout.CENTER);
    jsp.getViewport().add(groupTree, null);
    bottomP.add(groupB, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(topP, BorderLayout.NORTH);
    topP.add(singleRB, new GridBagConstraints2(0, 1, 2, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(listRB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(allRB, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(groupC, new GridBagConstraints2(1, 0, 4, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(listB, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel6, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(casT, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(selectChemB, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(bottomP, BorderLayout.CENTER);

    radioButtonClicked();
    bottomP.validate();
    if (isVoc) {
      topP.setVisible(false);
    }
    topP.validate();
    this.validate();
    buildTree();
    ClientHelpObjs.makeDefaultColors(this);
  }

  void vocRB_actionPerformed(ActionEvent e) {
    if (vocRB.isSelected()) {
      allRB.setSelected(false);
      listRB.setSelected(false);
      singleRB.setSelected(false);
    } else {
      listRB.setSelected(true);
    }
    radioButtonClicked();
  }

  public void openTemplate() {
  }

  public void saveTemplate() {
  }

  public void clearTemplate() {
  }

}

class ReportableReportThread extends Thread {
  ReportableUsageReport parent;
  public ReportableReportThread(ReportableUsageReport parent) {
    super("ReportTableReportThread");
    this.parent = parent;
  }

  public void run() {
    parent.loadItAction();
  }
}

class Report_facility_itemAdapter implements java.awt.event.ItemListener {
  ReportableUsageReport adaptee;

  Report_facility_itemAdapter(ReportableUsageReport adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.facility_itemStateChanged(e);
  }
}

/**
 *  Description of the Class
 *
 *@author     Shaik_Nawaz
 *@created    December 6, 2000
 */
class Groupie extends DefaultMutableTreeNode {
  String s;
  /**
   *  Constructor for the Groupie object
   *
   *@param  s  Description of Parameter
   */
  public Groupie(String s) {
    this.s = s;
  }

  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public String toString() {
    return s;
  }
}
