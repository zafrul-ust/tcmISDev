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
import java.net.*;
import java.beans.*;
import javax.swing.tree.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import com.borland.jbcl.layout.*;
import javax.swing.table.*;

import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.*;
//import radian.tcmis.client.share.reports.appData.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AdHocUsageReport extends ReportInputPanel
//public class AdHocUsageReport extends JPanel
{
  JComboBox begDayC = new JComboBox();
  JComboBox endDayC = new JComboBox();
  JComboBox locC = new JComboBox();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  JComboBox facC = new JComboBox();
  JComboBox waC = new JComboBox();

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

  JButton forwardB = new JButton();
  JButton listB = new JButton();
  JButton selectChemB = new JButton();
  StaticJLabel jLabel6 = new StaticJLabel();
  JTextField casT = new JTextField();
  boolean isVoc = false;
  JPanel rightP = new JPanel();
  JScrollPane reportFieldJSP = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JButton backB = new JButton();
  CmisTable rightTable;
  JRadioButton interactiveR = new JRadioButton();
  JRadioButton batchR = new JRadioButton();
  StaticJLabel jLabel7 = new StaticJLabel();
  JTextField partNoT = new JTextField();
  StaticJLabel jLabel8 = new StaticJLabel();
  JTextField mfgT = new JTextField();
  StaticJLabel jLabel9 = new StaticJLabel();
  JComboBox categoryC = new JComboBox();
  JPanel leftP = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane baseFieldJSP = new JScrollPane();
  CmisTable leftTable;
  String[] colHeadR = {
      "Report Fields", "Compatibility", "Color"};
  String[] colHeadL = {
      "Base Fields", "Compatibility", "Color"};
  Vector baseField = new Vector();
  Vector compatibility = new Vector(); //6-15-01
  Vector color = new Vector();

  Hashtable templateInfoH = null;
  String mfgID = "";
  String lastFacID = "";

  boolean firstTime = true;
  StaticJLabel batchNameL = new StaticJLabel();
  JTextField batchNameT = new JTextField();
  StaticJLabel repCatL = new StaticJLabel();
  JComboBox repCatC = new JComboBox();
  StaticJLabel locL = new StaticJLabel();
  JComboBox partC = new JComboBox();
  JComboBox mfgC = new JComboBox();

  StaticJLabel reportingEntityL = new StaticJLabel();
  JComboBox reportingEntityC = new JComboBox();
  StaticJLabel dockL = new StaticJLabel();
  JComboBox dockC = new JComboBox();
  StaticJLabel deliveryPtL = new StaticJLabel();
  JComboBox deliveryPtC = new JComboBox();

  ItemListener repCatItemListener;
  ItemListener repWorkAreaItemListener;
  ItemListener reportingEntityItemListener;
  ItemListener dockItemListener;

  boolean isWACItemListen = false;
  /**
   *  Constructor for the AdHocUsageReport object
   *
   *@param  cmis  Description of Parameter
   */
  public AdHocUsageReport(CmisApp cmis) {
    this(cmis, false);
  }

  /**
   *  Constructor for the AdHocUsageReport object
   *
   *@param  cmis         Description of Parameter
   *@param  isVocReport  Description of Parameter
   */
  public AdHocUsageReport(CmisApp cmis, boolean isVocReport) {
    super(cmis, "Share", "Ad Hoc Usage");
    isVoc = isVocReport;
  }

  public void loadScreen() {
    if (firstTime) {
      try {
        jbInit();
        loadIt();
      } catch (Exception e) {
        e.printStackTrace();
      }
      firstTime = false;
    }
  }

  public void loadIt() {
    AdHocReportLoadThread ahrlt = new AdHocReportLoadThread(this, "load");
    ahrlt.start();
  }

  public void loadItAction(String action) {
    CursorChange.setCursor(this, Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this, false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("AdHocReport", cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION", "GET_USAGE_TEMPLATE_INFO"); //String, String
      query.put("USER_ID", cmis.getUserId()); //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this, true);
        return;
      }

      templateInfoH = (Hashtable) result.get("USAGE_TEMPLATE_INFO");
      //Nawaz 09/20/01
      templateInfoH.put("TABLE_STYLE", (Hashtable) result.get("TABLE_STYLE"));
      //initializind item listeners
      reportingEntityItemListener = new Report_ReportingEntity_actionAdapter(this);
      dockItemListener = new Report_Dock_actionAdapter(this);

      buildTable();
      buildComboBoxes();
    } catch (Exception e) {
      e.printStackTrace();
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this, true);
      return;
    }
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
    ClientHelpObjs.setEnabledContainer(this, true);
    radioButtonClicked();
    begYearC.removeAllItems();
    begYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), -1);
    begDayC.removeAllItems();
    begDayC = ClientHelpObjs.loadChoiceFromVector(begDayC, getDays(begMonC.getSelectedIndex(), begYearC.getSelectedIndex(), "begin"));
    endYearC.removeAllItems();
    endYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), 0);
    endDayC.removeAllItems();
    Vector days = getDays(endMonthC.getSelectedIndex(), endYearC.getSelectedIndex(), "end");
    endDayC = ClientHelpObjs.loadChoiceFromVector(endDayC, days);
    endDayC.setSelectedIndex(days.size() - 1);
    ClientHelpObjs.setComboBoxUpdateUi(this);
  }

  public void loadTemplate(String template) {
    CursorChange.setCursor(this, Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this, false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("AdHocReport",
          cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION", "LOAD_USAGE_TEMPLATE_INFO"); //String, String
      query.put("USER_ID", cmis.getUserId()); //String, Integer
      query.put("TEMPLATE", template); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this, true);
        return;
      }

      Hashtable infoH = (Hashtable) result.get("TEMPLATE_INFO");
      //Nawaz 09/20/01
      infoH.put("TABLE_STYLE", (Hashtable) result.get("TABLE_STYLE"));
      loadData(infoH);

    } catch (Exception e) {
      e.printStackTrace();
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this, true);
      return;
    }
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
    ClientHelpObjs.setEnabledContainer(this, true);
    radioButtonClicked();
    super.setReportTitle("Ad Hoc Usage - " + template);
    ClientHelpObjs.setComboBoxUpdateUi(this);
  }

  public void loadData(Hashtable data) {
    String mfg = (String) data.get("MANUFACTURER");
    String mfgId = (String) data.get("MANUFACTURER_ID");
    String mfgOperator = (String) data.get("MFG_OPERATOR");
    String partN = (String) data.get("PART_NO");
    String partOperator = (String) data.get("PART_OPERATOR");
    String type = (String) data.get("TYPE");
    String beginY = (String) data.get("BEGIN_YEAR");
    String beginM = (String) data.get("BEGIN_MONTH");
    String endY = (String) data.get("END_YEAR");
    String endM = (String) data.get("END_MONTH");
    String facID = (String) data.get("FACILITY_ID");
    String workArea = (String) data.get("WORK_AREA");
    String method = (String) data.get("METHOD");
    String reportEntity = (String) data.get("REPORTING_ENTITY");
    String dock = (String) data.get("DOCK");
    String deliveryPt = (String) data.get("DELIVERY_POINT");
    String category = (String) data.get("CATEGORY");
    String CASList = (String) data.get("CAS_LIST");
    Vector report = (Vector) data.get("REPORT_FIELDS");

    //Vector base = (Vector)data.get("BASE_FIELD");

    //selecting report type
    if (type.equalsIgnoreCase("List")) {
      listRB.setSelected(true);
      singleRB.setSelected(false);
      allRB.setSelected(false);

      if (!BothHelpObjs.isBlankString(CASList)) {
        groupC.setSelectedItem(groupC.getListIdDesc(CASList));
      }
    } else if (type.equalsIgnoreCase("Single")) {
      listRB.setSelected(false);
      singleRB.setSelected(true);
      allRB.setSelected(false);

      if (!BothHelpObjs.isBlankString(CASList)) {
        casT.setText(CASList);
      }
    } else {
      listRB.setSelected(false);
      singleRB.setSelected(false);
      allRB.setSelected(true);
    }

    //selecting conditions
    //manufacturer
    mfgC.setSelectedItem(mfgOperator);
    if (!BothHelpObjs.isBlankString(mfg)) {
      mfgT.setText(mfg);
      this.mfgID = mfgId;
    } else {
      mfgT.setText("");
      this.mfgID = "";
    }
    //part no
    partC.setSelectedItem(partOperator);
    if (!BothHelpObjs.isBlankString(partN)) {
      partNoT.setText(partN);
    } else {
      partNoT.setText("");
    }

    if (facID.startsWith("All")) {
      facC.setSelectedIndex(0);
    } else {
      facC.setSelectedItem(getFacilityDesc(facID));
    }
    facC.revalidate();
    loadReportingEntity();
    reportingEntityC.setSelectedItem(getReportingEntityDesc(reportEntity));
    reportingEntityC.revalidate();
    if (!reportingEntityC.isVisible()) {
      loadWorkArea();
    }
    waC.setSelectedItem(getWorkAreaDesc(workArea));
    waC.revalidate();
    loadDock();
    String dockDesc = getDockDesc(dock);
    dockC.setSelectedItem(dockDesc);
    deliveryPtC.setSelectedItem(getDeliveryPtDesc(dockDesc,deliveryPt));
    dockC.revalidate();
    deliveryPtC.revalidate();

    //date
    Integer b1 = new Integer(beginY);
    Integer bm = new Integer(beginM);
    Integer ey = new Integer(endY);
    Integer em = new Integer(endM);

    begMonC.setSelectedIndex(bm.intValue());
    begMonC.revalidate();
    begYearC.setSelectedIndex(b1.intValue());
    begYearC.revalidate();
    begDayC.removeAllItems();
    begDayC = ClientHelpObjs.loadChoiceFromVector(begDayC, getDays(bm.intValue(), b1.intValue(), "begin"));

    endMonthC.setSelectedIndex(em.intValue());
    endMonthC.revalidate();
    endYearC.setSelectedIndex(ey.intValue());
    endYearC.revalidate();
    endDayC.removeAllItems();
    endDayC = ClientHelpObjs.loadChoiceFromVector(endDayC, getDays(em.intValue(), ey.intValue(), "end"));
    //other combo boxes
    if (reportingEntityC.isVisible()) {
      reportingEntityC.setSelectedItem(reportEntity);
      reportingEntityC.revalidate();
    }
    dockC.setSelectedItem(dock);
    dockC.revalidate();
    deliveryPtC.setSelectedItem(deliveryPt);
    deliveryPtC.revalidate();

    categoryC.setSelectedItem(category);
    categoryC.revalidate();

    begDayC.setSelectedItem(new Integer( (String) data.get("BEGIN_DAY")));
    endDayC.setSelectedItem(new Integer( (String) data.get("END_DAY")));
    if (locC.isVisible() && repCatC.isVisible()) {
      locC.setSelectedItem( (String) data.get("LOCATION"));
      repCatC.setSelectedItem( (String) data.get("REPORT_CATEGORY"));
    }

    baseFieldJSP.getViewport().remove(leftTable);
    reportFieldJSP.getViewport().remove(rightTable);

    AttributiveCellTableModel modelL = new AttributiveCellTableModel(colHeadL, baseField.size()); //{
    ColoredCell cellAtt = (ColoredCell) modelL.getCellAttribute();
    AttributiveCellTableModel modelR = new AttributiveCellTableModel(colHeadR, baseField.size());
    //Because we need to set color for each cells.  The only I know how to do this is first
    //create a table model that is as long as the number of base fields.  Even when we call
    //remove row method the table model still remember and allow us to set color for each cells
    //Removing row from bottom up.
    for (int k = baseField.size() - 1; k >= 0; k--) {
      modelR.removeRow(k);
      modelL.removeRow(k);
    }
    //make a copy of base_fields
    Object[] oaL1 = new Object[colHeadR.length];
    for (int i = 0; i < report.size(); i++) {
      oaL1[0] = report.elementAt(i);
      oaL1[1] = compatibility.elementAt(baseField.indexOf(oaL1[0])); //6-15-01
      oaL1[2] = color.elementAt(baseField.indexOf(oaL1[0]));
      modelR.addRow(oaL1);
    }

    Object[] oaL3 = new Object[colHeadL.length];
    for (int i = 0; i < baseField.size(); i++) {
      oaL3[0] = baseField.elementAt(i);
      if (! (report.contains(oaL3[0]))) {
        oaL3[1] = compatibility.elementAt(i);
        oaL3[2] = color.elementAt(i);
        modelL.addRow(oaL3);
      }
    }

    rightTable = new CmisTable(modelR);
    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable) data.get("TABLE_STYLE");
    Color color = (Color) chargeTableStyle.get("COLOR");
    Integer t = (Integer) chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer) chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer) chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer) chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer) chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(), l.intValue(), b.intValue(), r.intValue()), a.intValue());
    //font and foreground and background color for columns heading
    String fontName = (String) chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer) chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer) chargeTableStyle.get("FONT_SIZE");
    rightTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    Enumeration enum1 = rightTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    rightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    rightTable.setDefaultRenderer(String.class, new AttributiveCellRenderer());
    rightTable.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
    rightTable.getColumn("Report Fields").setWidth(220);
    rightTable.getColumn("Report Fields").setMinWidth(220);
    rightTable.getColumn("Report Fields").setMaxWidth(220);
    rightTable.getColumn("Compatibility").setWidth(0);
    rightTable.getColumn("Compatibility").setMinWidth(0);
    rightTable.getColumn("Compatibility").setMaxWidth(0);
    rightTable.getColumn("Color").setWidth(0);
    rightTable.getColumn("Color").setMinWidth(0);
    rightTable.getColumn("Color").setMaxWidth(0);
    rightTable.setToolTipText(" If you select a row, new fields will be added after.  ");

    leftTable = new CmisTable(modelL);
    leftTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    enum1 = leftTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    leftTable.setCellSelectionEnabled(false);
    leftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    leftTable.setDefaultRenderer(String.class, new AttributiveCellRenderer());
    leftTable.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
    leftTable.getColumn("Base Fields").setWidth(190);
    leftTable.getColumn("Base Fields").setMinWidth(190);
    leftTable.getColumn("Base Fields").setMaxWidth(190);
    leftTable.getColumn("Compatibility").setWidth(0);
    leftTable.getColumn("Compatibility").setMinWidth(0);
    leftTable.getColumn("Compatibility").setMaxWidth(0);
    leftTable.getColumn("Color").setWidth(0);
    leftTable.getColumn("Color").setMinWidth(0);
    leftTable.getColumn("Color").setMaxWidth(0);

    baseFieldJSP.getViewport().setView(leftTable);
    reportFieldJSP.getViewport().setView(rightTable);

    reSetColoredCell(leftTable);
    reSetColoredCell(rightTable);
  } //end of method

  void setComboColor() {
    DataJLabel tmp = new DataJLabel();
    tmp.setText("temp");
    Color keep = tmp.getForeground();
    reportingEntityC.setForeground(keep);
    dockC.setForeground(keep);
    deliveryPtC.setForeground(keep);
    facC.setForeground(keep);
    waC.setForeground(keep);
    groupC.setForeground(keep);
    categoryC.setForeground(keep);
    begMonC.setForeground(keep);
    begYearC.setForeground(keep);
    endMonthC.setForeground(keep);
    endYearC.setForeground(keep);
  }

  /**
   *  Sets the FacCombo attribute of the AdHocUsageReport object
   *
   *@param  fc  The new FacCombo value
   */
  public void setFacCombo(FacilityCombo fc) {
  }

  public void openTemplate() {
    showOpenTemplateDlg();
  }

  public void showOpenTemplateDlg() {
    OpenTemplateDlg otd = new OpenTemplateDlg(cmis,
                                              "Open Template",
                                              true, "AdHocReport");
    if (otd.loadData()) {
      otd.setVisible(true);
      if (otd.isChanged()) {
        String selectedTemplate = otd.getSelectedTemplate();
        if (!BothHelpObjs.isBlankString(selectedTemplate)) {
          //super.setReportTitle("Ad Hoc Usage - "+selectedTemplate);
          loadTemplate(selectedTemplate);
        }
      }
    }
  }

  public void saveTemplate() {
    showSaveTemplateDlg();
  }

  public void showSaveTemplateDlg() {
    //save selected info
    Hashtable currentDataH = saveSelectedData();
    SaveTemplateDlg std = new SaveTemplateDlg(cmis, "Save Template", true, "AdHocReport");
    String currentT = super.getTitle();
    String currentTemplate = "";
    if (currentT.indexOf("-") > 0) {
      currentTemplate = currentT.substring(currentT.indexOf("-") + 2);
      std.updateL.setText(currentTemplate);
      std.updateRB.setSelected(true);
      std.newRB.setSelected(false);
      std.newT.setEnabled(false);
    } else {
      std.newRB.setSelected(true);
      std.newT.setEnabled(true);
      std.updateRB.setSelected(false);
      std.updateRB.setEnabled(false);
    }
    std.setSelectedData(currentDataH);
    std.setVisible(true);
    if (std.isChanged()) {
      String newTemplate = std.getTemplateName();
      if (!newTemplate.equals(currentTemplate)) {
        loadTemplate(newTemplate);
      }
    }
  }

  public void clearTemplate() {
    super.setReportTitle("Ad Hoc Usage");
    reSetAllComponents();
  }

  public void reSetAllComponents() {
    //re-setting top
    listRB.setSelected(false);
    singleRB.setSelected(false);
    allRB.setSelected(true);
    radioButtonClicked();

    //re-setting middle
    //facC.setSelectedIndex(0);
    facC.setSelectedItem(getFacilityDesc(cmis.getPrefFac()));
    waC.setSelectedIndex(0);
    begMonC.reFresh(0);
    begYearC.removeAllItems();
    begYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), -1);
    begDayC.removeAllItems();
    begDayC = ClientHelpObjs.loadChoiceFromVector(begDayC, getDays(begMonC.getSelectedIndex(), begYearC.getSelectedIndex(), "begin"));
    endMonthC.reFresh( -1);
    endYearC.removeAllItems();
    endYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), 0);
    endDayC.removeAllItems();
    Vector days = getDays(endMonthC.getSelectedIndex(), endYearC.getSelectedIndex(), "end");
    endDayC = ClientHelpObjs.loadChoiceFromVector(endDayC, days);
    endDayC.setSelectedIndex(days.size() - 1);
    if (locC.isVisible() && repCatC.isVisible()) {
      locC.setSelectedIndex(0);
      repCatC.setSelectedIndex(0);
    }

    buildTable();

    //re-setting combo
    groupC.setSelectedIndex(0);
    casT.setText("");
    if (reportingEntityC.isVisible()) {
      reportingEntityC.setSelectedIndex(0);
    }
    dockC.setSelectedIndex(0);
    deliveryPtC.setSelectedIndex(0);
    categoryC.setSelectedIndex(0);
    partNoT.setText("");
    partC.setSelectedIndex(0);
    mfgT.setText("");
    this.mfgID = "";
    mfgC.setSelectedIndex(0);
    interactiveR.setSelected(true);
    batchR.setSelected(false);
    batchNameL.setEnabled(false);
    batchNameT.setEnabled(false);

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
        gd.setMsg(" You must select a chemical.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }
    }

    if (rightTable.getRowCount() < 1) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      gd.setMsg(" Please define report fields.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
      return;
    }

    String myTitle;
    Hashtable currentDataH = saveSelectedData();

    CursorChange.setCursor(this, Cursor.WAIT_CURSOR);
    try {
      if (interactiveR.isSelected()) {
        TcmisHttpConnection cgi = new TcmisHttpConnection("AdHocReport", cmis);
        Hashtable query = new Hashtable();
        query.put("ACTION", "CREATE_REPORT"); //String, String
        query.put("SELECTED_DATA", currentDataH); //String, Hashtable
        query.put("WHICH_SCREEN", "ADHOC_USAGE");
        query.put("USER_ID", cmis.getUserId().toString()); //String, String
        Hashtable result = cgi.getResultHash(query);
        if (result == null) {
          GenericDlg.showAccessDenied(cmis.getMain());
          return;
        }
        Boolean suc = (Boolean) result.get("SUCCEED");
        String msg = (String) result.get("MSG");
        if (suc.booleanValue()) {
          try {
            String url = (String) result.get("URL");
            new URLCall(url, cmis);
            //5-10-01 give the user some response so they know what to do.
            GenericDlg gd = new GenericDlg(cmis.getMain(), "Action Succeeded", true);
            gd.setMsg(msg);
            gd.setVisible(true);
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
          gd.setMsg(msg);
          CenterComponent.centerCompOnScreen(gd);
          gd.setVisible(true);
        }
      } else {
        Hashtable query = new Hashtable();
        query.put("ACTION", "CREATE_REPORT"); //String, String
        query.put("WHICH_SCREEN", "ADHOC_USAGE");
        query.put("USER_ID", cmis.getMain().getUserId().toString());
        query.put("SELECTED_DATA", currentDataH); //String, Hashtable
        query.put("REPORT_NAME", BothHelpObjs.makeBlankFromNull(batchNameT.getText()));

        TcmisHttpConnection cgi = new TcmisHttpConnection("AdHocBatch", cmis);
        Hashtable result = cgi.getResultHash(query);
        if (result == null) {
          GenericDlg.showAccessDenied(cmis.getMain());
          return;
        }
        //System.out.println("-------- got here again: "+result);
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Action Succeeded", true);
        gd.setMsg(" Your report has been sent to the server for processing,\n an email will notify you upon completion.\n You may now resume other activities within tcmIS.\n Batch report(s) that is older than two weeks will be deleted.");
        gd.setVisible(true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ClientHelpObjs.setEnabledContainer(this, true);
      this.revalidate();
    }
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
    radioButtonClicked();
    ClientHelpObjs.setComboBoxUpdateUi(this);
  } //end of method

  //trong 2-19-01
  /**
   *  Description of the Method
   */
  public Hashtable saveSelectedData() {
    Hashtable currentData = new Hashtable();
    //getting method
    if (interactiveR.isSelected()) {
      currentData.put("METHOD", "Active");
    } else {
      currentData.put("METHOD", "Batch");
    }

    //what type of report
    if (isVoc) {
      currentData.put("CHEM_TYPE", "VOC");
    } else if (listRB.isSelected()) {
      currentData.put("CHEM_TYPE", "List");
      currentData.put("LIST_ID", groupC.getSelectedListId());
    } else if (singleRB.isSelected()) {
      currentData.put("CHEM_TYPE", "Single");
      currentData.put("CAST_NUMBER", casT.getText());
    } else {
      currentData.put("CHEM_TYPE", "All");
    }
    currentData.put("FACILITY_ID", getFacilityID(facC.getSelectedItem().toString()));
    currentData.put("FACILITY_DESC",facC.getSelectedItem().toString());
    currentData.put("WORK_AREA", getWorkAreaID());
    currentData.put("WORK_AREA_DESC", waC.getSelectedItem());
    if (reportingEntityC.isVisible()) {
      currentData.put("REPORTING_ENTITY", getReportingEntityId( (String) reportingEntityC.getSelectedItem()));
      currentData.put("REPORTING_ENTITY_DESC", (String) reportingEntityC.getSelectedItem());
      currentData.put("REPORTING_ENTITY_LABEL",reportingEntityL.getText());
    } else {
      currentData.put("REPORTING_ENTITY", "");
      currentData.put("REPORTING_ENTITY_DESC","");
      currentData.put("REPORTING_ENTITY_LABEL","");
    }
    currentData.put("PART_NO", BothHelpObjs.makeBlankFromNull(partNoT.getText()).trim());
    currentData.put("PART_OPERATOR", partC.getSelectedItem().toString());
    currentData.put("DOCK", getDockID( (String) dockC.getSelectedItem()));
    currentData.put("DOCK_DESC", (String) dockC.getSelectedItem());
    currentData.put("DELIVERY_POINT", getDeliveryPtID( (String) dockC.getSelectedItem(), (String) deliveryPtC.getSelectedItem()));
    currentData.put("DELIVERY_POINT_DESC", (String) deliveryPtC.getSelectedItem());
    currentData.put("MANUFACTURER", BothHelpObjs.makeBlankFromNull(mfgT.getText()).trim());
    currentData.put("MANUFACTURER_ID", BothHelpObjs.makeBlankFromNull(this.mfgID));
    currentData.put("MFG_OPERATOR", mfgC.getSelectedItem().toString());
    currentData.put("BEGIN_MONTH", new Integer(begMonC.getSelectedIndex()));
    currentData.put("BEGIN_YEAR", new Integer(begYearC.getSelectedIndex()));
    currentData.put("END_MONTH", new Integer(endMonthC.getSelectedIndex()));
    currentData.put("END_YEAR", new Integer(endYearC.getSelectedIndex()));
    currentData.put("BEGIN_YEARN", (begYearC.getSelectedItem()));
    currentData.put("END_YEARN", (endYearC.getSelectedItem()));


    //Only save if company uses these field
    if (locC.isVisible() && repCatC.isVisible()) {
      currentData.put("LOCATION", locC.getSelectedItem());
      currentData.put("REPORT_CATEGORY", repCatC.getSelectedItem());
    }
    currentData.put("BEGIN_DAY", begDayC.getSelectedItem().toString());
    currentData.put("END_DAY", endDayC.getSelectedItem().toString());

    currentData.put("CATEGORY", getCategoryID());
    currentData.put("CATEGORY_DESC", categoryC.getSelectedItem().toString());
    Vector v = new Vector();
    for (int i = 0; i < rightTable.getRowCount(); i++) {
      v.addElement(rightTable.getValueAt(i, 0));
    }
    currentData.put("REPORT_FIELDS", v);

    return currentData;
  }

  String getCategoryID() {
    Vector categoryID = (Vector) templateInfoH.get("CATEGORY_ID");
    return ( (String) categoryID.elementAt(categoryC.getSelectedIndex()));
  }

  String getDeliveryPtDesc(String dockDesc, String deliveryPtId) {
    Hashtable dockDeliveryPt = (Hashtable) templateInfoH.get("FACILITY_DOCK_DELIVERY_POINT");
    Hashtable h = (Hashtable) dockDeliveryPt.get(getFacilityID(facC.getSelectedItem().toString()));
    Hashtable innerH = (Hashtable) h.get(getDockID(dockDesc));
    Vector dpIdV = (Vector) innerH.get("DELIVERY_POINT_ID");
    Vector dpDescV = (Vector) innerH.get("DELIVERY_POINT_DESC");
    int index = dpIdV.indexOf(deliveryPtId);
    if (index < 0) {
      index = 0;
    }
    return (dpDescV.elementAt(index).toString());
  } //end of method

  String getDeliveryPtID(String dockDesc, String deliveryPtDesc) {
    Hashtable dockDeliveryPt = (Hashtable) templateInfoH.get("FACILITY_DOCK_DELIVERY_POINT");
    Hashtable h = (Hashtable) dockDeliveryPt.get(getFacilityID(facC.getSelectedItem().toString()));
    Hashtable innerH = (Hashtable) h.get(getDockID(dockDesc));
    Vector dpIdV = (Vector) innerH.get("DELIVERY_POINT_ID");
    Vector dpDescV = (Vector) innerH.get("DELIVERY_POINT_DESC");
    int index = dpDescV.indexOf(deliveryPtDesc);
    if (index < 0) {
      index = 0;
    }
    return (dpIdV.elementAt(index).toString());
  } //end of method

  String getDockDesc(String dockId) {
    Hashtable dockDeliveryPt = (Hashtable) templateInfoH.get("FACILITY_DOCK_DELIVERY_POINT");
    Hashtable h = (Hashtable) dockDeliveryPt.get(getFacilityID(facC.getSelectedItem().toString()));
    Vector dockIdV = (Vector) h.get("DOCK_ID");
    Vector dockDescV = (Vector) h.get("DOCK_DESC");
    int index = dockIdV.indexOf(dockId);
    if (index < 0) {
      index = 0;
    }
    return (dockDescV.elementAt(index).toString());
  } //end of method

  String getDockID(String dockDesc) {
    Hashtable dockDeliveryPt = (Hashtable) templateInfoH.get("FACILITY_DOCK_DELIVERY_POINT");
    Hashtable h = (Hashtable) dockDeliveryPt.get(getFacilityID(facC.getSelectedItem().toString()));
    Vector dockIdV = (Vector) h.get("DOCK_ID");
    Vector dockDescV = (Vector) h.get("DOCK_DESC");
    int index = dockDescV.indexOf(dockDesc);
    if (index < 0) {
      index = 0;
    }
    return (dockIdV.elementAt(index).toString());
  } //end of method

  /**
   *  Description of the Method
   */
  void radioButtonClicked() {
    groupC.setEnabled(listRB.isSelected());
    listB.setEnabled(listRB.isSelected());
    selectChemB.setEnabled(singleRB.isSelected());
    casT.setEnabled(singleRB.isSelected());
    jLabel6.setEnabled(singleRB.isSelected());
    this.batchNameL.setEnabled(this.batchR.isSelected());
    this.batchNameT.setEnabled(this.batchR.isSelected());
  }

  void forwardB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (leftTable.getSelectedRow() < 0) {
      GenericDlg g = new GenericDlg(cmis.getMain(), "Select row(s)", true);
      g.setMsg(" Please select row(s) that you want to move.");
      g.setVisible(true);
    } else {
      //int[] selectedRows = new int[MAXSELECTEDROW];
      int[] selectedRows = new int[baseField.size()];
      selectedRows = leftTable.getSelectedRows();
      Vector myDataV = new Vector();
      Vector myCompV = new Vector();
      Vector myColorV = new Vector();
      GenericDlg g = new GenericDlg(cmis.getMain(), "Error", true);
      //save data
      boolean contChem = false;
      for (int i = 0; i < selectedRows.length; i++) {
        //System.out.println("== selected rows: "+selectedRows[i]);
        String tmpData = leftTable.getModel().getValueAt(selectedRows[i], 0).toString();
        if (tmpData.startsWith("CAS") || tmpData.equalsIgnoreCase("Chemical Name")) {
          contChem = true;
        }
        myDataV.addElement(tmpData);
        myCompV.addElement(leftTable.getModel().getValueAt(selectedRows[i], 1).toString()); //6-15-01 compatibility
        myColorV.addElement( (Color) leftTable.getModel().getValueAt(selectedRows[i], 2));
      }

      //if user select both 'chemical' and 'material weight'
      if (contChem) {
        if (myCompV.contains("N") || myCompV.contains("I")) {
          g.setMsg("Non-black fields are incompatible with each other.\nBlack fields are compatible with all colors.");
          g.setVisible(true);
          return;
        }
      }
      if (myCompV.contains("S")) {
        if (myCompV.contains("I") || myCompV.contains("N")) {
          g.setMsg("Non-black fields are incompatible with each other.\nBlack fields are compatible with all colors.");
          g.setVisible(true);
          return;
        }
      } else if (myCompV.contains("N")) {
        if (myCompV.contains("I") || myCompV.contains("S")) {
          g.setMsg("Non-black fields are incompatible with each other.\nBlack fields are compatible with all colors.");
          g.setVisible(true);
          return;
        }
      } else if (myCompV.contains("I")) {
        if (myCompV.contains("N") || myCompV.contains("S")) {
          g.setMsg("Non-black fields are incompatible with each other.\nBlack fields are compatible with all colors.");
          g.setVisible(true);
          return;
        }
      }

      //check report field table with the same condition as above
      boolean contChem2 = false;
      boolean contMat = false;
      boolean contItem = false;
      boolean contSpeciate = false;
      for (int n = 0; n < rightTable.getRowCount(); n++) {
        String tmpField = rightTable.getModel().getValueAt(n, 0).toString();
        String tmpComp = rightTable.getModel().getValueAt(n, 1).toString();
        if (tmpField.startsWith("CAS") || tmpField.equalsIgnoreCase("Chemical Name")) {
          contChem2 = true;
        }
        if (tmpComp.equalsIgnoreCase("N")) {
          contMat = true;
        } else if (tmpComp.equalsIgnoreCase("I")) {
          contItem = true;
        } else if (tmpComp.equalsIgnoreCase("S")) {
          contSpeciate = true;
        }
      }
      //if report contains speciated
      if (contSpeciate) {
        if (myCompV.contains("N") || myCompV.contains("I")) {
          g.setMsg("Non-black fields are incompatible with each other.\nBlack fields are compatible with all colors.");
          g.setVisible(true);
          return;
        }
      } else if (contMat) {
        if (contChem || myCompV.contains("I")) {
          g.setMsg("Non-black fields are incompatible with each other.\nBlack fields are compatible with all colors.");
          g.setVisible(true);
          return;
        }
      } else if (contItem) {
        if (contChem || myCompV.contains("N")) {
          g.setMsg("Non-black fields are incompatible with each other.\nBlack fields are compatible with all colors.");
          g.setVisible(true);
          return;
        }
      }

      //cannot move chemical low, high, avg over if user did not select CAS or Chemical Name
      if (!contChem && !contChem2) {
        if (myCompV.contains("S")) {
          String msg = " You must select CAS Number or Chemical Name before selecting speciated colunm(s).";
          g.setMsg(msg);
          g.setVisible(true);
          return;
        }
      }

      //removing data from table
      for (int j = 0; j < selectedRows.length; j++) {
        //System.out.println("== deleting selected rows: "+selectedRows[j]);
        removeRowFromTable(leftTable, selectedRows[j] - j);
      }
      //move data to table
      for (int k = 0; k < myDataV.size(); k++) {
        //String tmpData2 = (String)myDataV.elementAt(k);
        Object[] oa = new Object[colHeadR.length];
        oa[0] = (String) myDataV.elementAt(k);
        oa[1] = (String) myCompV.elementAt(k);
        oa[2] = (Color) myColorV.elementAt(k);
        addRowToTable(rightTable, oa, rightTable.getSelectedRow());
        //addRowToTable(rightTable,oa);
      }
    }
    reSetColoredCell(leftTable);
    reSetColoredCell(rightTable);
  }

  void reSetColoredCell(CmisTable myTable) {
    AttributiveCellTableModel model = (AttributiveCellTableModel) myTable.getModel();
    ColoredCell cellAtt = (ColoredCell) model.getCellAttribute();
    for (int i = 0; i < myTable.getRowCount(); i++) {
      cellAtt.setForeground( (Color) myTable.getModel().getValueAt(i, 2), i, 0);
    }
    myTable.repaint();
  }

  void removeRowFromTable(CmisTable myTable, int row) {
    AttributiveCellTableModel model = (AttributiveCellTableModel) myTable.getModel();
    model.removeRow(row);
    //myTable.revalidate();
    //myTable.repaint();
  }

  /*
       void removeRowFromTable(CmisTable myTable,int row)
       {
    DbTableModel model = (DbTableModel)myTable.getModel();
    model.removeRow(row);
       }*/

  void addRowToTable(CmisTable myTable, Object[] data, int selectedRow) {
    AttributiveCellTableModel model = (AttributiveCellTableModel) myTable.getModel();
    ColoredCell cellAtt = (ColoredCell) model.getCellAttribute();
    if (selectedRow >= 0) {
      model.insertRow(selectedRow + 1, data);
      cellAtt.setForeground( (Color) data[2], selectedRow + 1, 0);
    } else {
      model.addRow(data);
      cellAtt.setForeground( (Color) data[2], model.getRowCount(), 0);
    }

    //cellAtt.setForeground(Color.cyan,0,0);
    //myTable.revalidate();
    //myTable.repaint();
  }

  /*
       void addRowToTable(CmisTable myTable,Object[] data, int selectedRow)
       {
      DbTableModel model = (DbTableModel)myTable.getModel();
      if (selectedRow >= 0) {
        model.insertRow(selectedRow+1,data);
      }else {
        model.addRow(data);
      }
       }*/

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void listRB_actionPerformed(ActionEvent e) {
    radioButtonClicked();
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void singleRB_actionPerformed(ActionEvent e) {
    radioButtonClicked();
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void allRB_actionPerformed(ActionEvent e) {
    radioButtonClicked();
  }

  /**
   *  Description of the Method
   *
   *@param Gives the list of chemical in a chemical list
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
    listB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    selectChemB.setMaximumSize(new Dimension(35, 23));
    selectChemB.setMinimumSize(new Dimension(35, 23));
    selectChemB.setPreferredSize(new Dimension(35, 23));
    selectChemB.setIcon(ResourceLoader.getImageIcon("images/button/search_small.gif"));

    forwardB.setMaximumSize(new Dimension(35, 35));
    forwardB.setMinimumSize(new Dimension(35, 35));
    forwardB.setPreferredSize(new Dimension(35, 35));
    forwardB.setIcon(ResourceLoader.getImageIcon("images/button/next1.gif"));
    backB.setIcon(ResourceLoader.getImageIcon("images/button/prev1.gif"));
    backB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        backB_actionPerformed(e);
      }
    }
    );

    groupC.loadIt();
    jLabel2.setText("End:");
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
    }
    );
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
    }
    );
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
    }
    );
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
    }
    );
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
    }
    );
    jLabel6.setText(" CAS Number:");
    casT.setMaximumSize(new Dimension(100, 23));
    casT.setMinimumSize(new Dimension(100, 23));
    casT.setPreferredSize(new Dimension(100, 23));

    reportingEntityL.setText("Reporting Entity:");
    reportingEntityC.setMaximumSize(new Dimension(120, 22));
    reportingEntityC.setMinimumSize(new Dimension(120, 22));
    reportingEntityC.setPreferredSize(new Dimension(120, 22));
    //dockL.setMaximumSize(new Dimension(45, 15));
    //dockL.setMinimumSize(new Dimension(39, 15));
    //dockL.setPreferredSize(new Dimension(39, 15));
    dockL.setText("Dock:");
    dockC.setMaximumSize(new Dimension(120, 22));
    dockC.setMinimumSize(new Dimension(120, 22));
    dockC.setPreferredSize(new Dimension(120, 22));
    //deliveryPtL.setMaximumSize(new Dimension(50, 15));
    //deliveryPtL.setMinimumSize(new Dimension(48, 15));
    //deliveryPtL.setPreferredSize(new Dimension(48, 15));
    deliveryPtL.setText("Del. Pt:");
    deliveryPtC.setMaximumSize(new Dimension(120, 22));
    deliveryPtC.setMinimumSize(new Dimension(120, 22));
    deliveryPtC.setPreferredSize(new Dimension(120, 22));

    rightP.setLayout(borderLayout2);
    backB.setMaximumSize(new Dimension(35, 35));
    backB.setMinimumSize(new Dimension(35, 35));
    backB.setPreferredSize(new Dimension(35, 35));
    interactiveR.setText("Interactive Generation");
    interactiveR.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        interactiveR_actionPerformed(e);
      }
    }
    );
    batchR.setText("Batch Generation");
    batchR.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        batchR_actionPerformed(e);
      }
    }
    );
    jLabel7.setText("Part No:");
    partNoT.setMaximumSize(new Dimension(120, 23));
    partNoT.setMinimumSize(new Dimension(120, 23));
    partNoT.setPreferredSize(new Dimension(120, 23));
    partC.setMaximumSize(new Dimension(75, 23));
    partC.setMinimumSize(new Dimension(75, 23));
    partC.setPreferredSize(new Dimension(75, 23));
    jLabel8.setText("Mfg:");
    mfgT.setEditable(true);
    mfgT.setMaximumSize(new Dimension(140, 23));
    mfgT.setMinimumSize(new Dimension(140, 23));
    mfgT.setPreferredSize(new Dimension(140, 23));
    mfgC.setMaximumSize(new Dimension(75, 23));
    mfgC.setMinimumSize(new Dimension(75, 23));
    mfgC.setPreferredSize(new Dimension(75, 23));

    jLabel9.setText("Material Category:");
    categoryC.setMaximumSize(new Dimension(240, 22));
    categoryC.setMinimumSize(new Dimension(220, 22));
    categoryC.setPreferredSize(new Dimension(220, 22));
    leftP.setLayout(borderLayout1);
    begMonC.setMaximumSize(new Dimension(32767, 22));
    begMonC.setMinimumSize(new Dimension(90, 22));
    begMonC.setPreferredSize(new Dimension(90, 22));
    begMonC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        begMonC_actionPerformed(e);
      }
    });
    endMonthC.setMaximumSize(new Dimension(32767, 22));
    endMonthC.setMinimumSize(new Dimension(90, 22));
    endMonthC.setPreferredSize(new Dimension(90, 22));
    endMonthC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        endMonthC_actionPerformed(e);
      }
    });
    begYearC.setMaximumSize(new Dimension(32767, 22));
    begYearC.setMinimumSize(new Dimension(65, 22));
    begYearC.setPreferredSize(new Dimension(65, 22));
    begYearC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        begYearC_actionPerformed(e);
      }
    });
    leftP.setMaximumSize(new Dimension(160, 80));
    leftP.setMinimumSize(new Dimension(160, 80));
    leftP.setPreferredSize(new Dimension(160, 80));
    rightP.setMaximumSize(new Dimension(160, 80));
    rightP.setMinimumSize(new Dimension(160, 80));
    rightP.setPreferredSize(new Dimension(160, 80));
    endYearC.setMaximumSize(new Dimension(32767, 22));
    endYearC.setMinimumSize(new Dimension(65, 22));
    endYearC.setPreferredSize(new Dimension(65, 22));
    endYearC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        endYearC_actionPerformed(e);
      }
    });

    batchNameL.setText("Rpt Name :");
    batchNameL.setEnabled(false);
    batchNameT.setMaximumSize(new Dimension(150, 21));
    batchNameT.setMinimumSize(new Dimension(150, 21));
    batchNameT.setPreferredSize(new Dimension(150, 21));
    batchNameT.setText("");
    batchNameT.setEnabled(false);
    begDayC.setMaximumSize(new Dimension(45, 22));
    begDayC.setMinimumSize(new Dimension(45, 22));
    begDayC.setPreferredSize(new Dimension(45, 22));
    endDayC.setMaximumSize(new Dimension(45, 22));
    endDayC.setMinimumSize(new Dimension(45, 22));
    endDayC.setPreferredSize(new Dimension(45, 22));
    repCatL.setText("");
    locC.setMaximumSize(new Dimension(120, 22));
    locC.setMinimumSize(new Dimension(120, 22));
    locC.setPreferredSize(new Dimension(120, 22));
    repCatC.setMaximumSize(new Dimension(120, 22));
    repCatC.setMinimumSize(new Dimension(120, 22));
    repCatC.setPreferredSize(new Dimension(120, 22));
    locL.setText("");
    bg.add(listRB);
    bg.add(singleRB);
    bg.add(allRB);

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
    waC.setMaximumSize(new Dimension(120, 22));
    waC.setMinimumSize(new Dimension(120, 22));
    waC.setPreferredSize(new Dimension(120, 22));
    facC.setMaximumSize(new Dimension(120, 22));
    facC.setMinimumSize(new Dimension(120, 22));
    facC.setPreferredSize(new Dimension(120, 22));

    forwardB.addActionListener(
        new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        forwardB_actionPerformed(e);
      }
    }
    );
    jLabel3.setText("Begin:");
    this.setLayout(new BorderLayout());

    this.add(topP, BorderLayout.NORTH);
    topP.add(singleRB, new GridBagConstraints2(0, 1, 2, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(listRB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(allRB, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(groupC, new GridBagConstraints2(1, 0, 5, 1, 1.0, 0.0
                                             , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(listB, new GridBagConstraints2(6, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel6, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(casT, new GridBagConstraints2(3, 1, 2, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(selectChemB, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    this.add(bottomP, BorderLayout.CENTER);
    //column 1
    bottomP.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(facC, new GridBagConstraints2(1, 0, 3, 1, 1.0, 0.0
                                              , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(jLabel4, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(waC, new GridBagConstraints2(1, 1, 3, 1, 1.0, 0.0
                                             , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(repCatL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    bottomP.add(repCatC, new GridBagConstraints2(1, 2, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(locL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 3), 0, 0));
    bottomP.add(locC, new GridBagConstraints2(1, 3, 1, 1, 1.0, 0.0
                                              , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(jLabel7, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(partC, new GridBagConstraints2(1, 4, 3, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(partNoT, new GridBagConstraints2(1, 4, 3, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 80, 3, 3), 0, 0));
    bottomP.add(leftP, new GridBagConstraints2(0, 5, 3, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 10, 3, 0), 0, 0));
    leftP.add(baseFieldJSP, BorderLayout.CENTER);
    bottomP.add(interactiveR, new GridBagConstraints2(1, 6, 2, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));

    //column 2
    bottomP.add(reportingEntityL, new GridBagConstraints2(2, 0, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
    bottomP.add(reportingEntityC, new GridBagConstraints2(3, 0, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
    bottomP.add(dockL, new GridBagConstraints2(2, 1, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
    bottomP.add(dockC, new GridBagConstraints2(3, 1, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
    bottomP.add(deliveryPtL, new GridBagConstraints2(2, 2, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
    bottomP.add(deliveryPtC, new GridBagConstraints2(3, 2, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
    bottomP.add(jLabel9, new GridBagConstraints2(3, 3, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(jLabel8, new GridBagConstraints2(3, 4, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(batchR, new GridBagConstraints2(3, 6, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(forwardB, new GridBagConstraints2(3, 5, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 30, 0), 0, 0));
    bottomP.add(backB, new GridBagConstraints2(3, 5, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(50, 3, 3, 0), 0, 0));

    //column 3
    bottomP.add(jLabel3, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(begMonC, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
    bottomP.add(begDayC, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 92, 3, 0), 0, 0));
    bottomP.add(begYearC, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 140, 3, 3), 0, 0));
    bottomP.add(jLabel2, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(endMonthC, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
    bottomP.add(endDayC, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 92, 3, 0), 0, 0));
    bottomP.add(endYearC, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 140, 3, 3), 0, 0));
    bottomP.add(categoryC, new GridBagConstraints2(4, 3, 2, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(mfgC, new GridBagConstraints2(4, 4, 2, 1, 0.0, 0.0
                                              , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    bottomP.add(mfgT, new GridBagConstraints2(4, 4, 2, 1, 0.0, 0.0
                                              , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 80, 3, 3), 0, 0));
    bottomP.add(rightP, new GridBagConstraints2(4, 5, 2, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 20), 0, 0));
    rightP.add(reportFieldJSP, BorderLayout.CENTER);
    bottomP.add(batchNameL, new GridBagConstraints2(4, 6, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    bottomP.add(batchNameT, new GridBagConstraints2(4, 6, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 65, 0, 3), 0, 0));

    bottomP.validate();

    if (isVoc) {
      topP.setVisible(false);
    }
    topP.validate();
    this.validate();

    listRB.setSelected(false);
    allRB.setSelected(true);
    singleRB.setSelected(false);
    radioButtonClicked();
    interactiveR.setSelected(true);
    DbTableModel modelL = new DbTableModel(colHeadL);
    leftTable = new CmisTable(modelL);
    baseFieldJSP.getViewport().setView(leftTable);
    DbTableModel modelR = new DbTableModel(colHeadR);
    rightTable = new CmisTable(modelR);
    reportFieldJSP.getViewport().setView(rightTable);
    ClientHelpObjs.makeDefaultColors(this);
  } //end of method

  void buildTable() {
    baseFieldJSP.getViewport().remove(leftTable);
    reportFieldJSP.getViewport().remove(rightTable);

    //get model from database
    Hashtable baseFieldInfo = (Hashtable) templateInfoH.get("BASE_FIELD_INFO"); //6-15-01
    baseField = (Vector) baseFieldInfo.get("BASE_FIELDS");
    compatibility = (Vector) baseFieldInfo.get("COMPATIBILITY");
    color = (Vector) baseFieldInfo.get("COLOR");

    AttributiveCellTableModel modelL = new AttributiveCellTableModel(colHeadL, baseField.size());
    ColoredCell cellAtt = (ColoredCell) modelL.getCellAttribute();
    AttributiveCellTableModel modelR = new AttributiveCellTableModel(colHeadR, baseField.size());
    //Because we need to set color for each cells.  The only I know how to do this is first
    //create a table model that is as long as the number of base fields.  Even when we call
    //remove row method the table model still remember and allow us to set color for each cells
    //Removing row from bottom up.
    for (int k = baseField.size() - 1; k >= 0; k--) {
      modelR.removeRow(k);
      modelL.removeRow(k);
    }
    //make a copy of base_fields
    Object[] oaL = new Object[colHeadL.length];
    for (int i = 0; i < baseField.size(); i++) {

      oaL[0] = baseField.elementAt(i);
      oaL[1] = compatibility.elementAt(i);
      oaL[2] = color.elementAt(i);
      modelL.addRow(oaL);
      cellAtt.setForeground( (Color) color.elementAt(i), i, 0);
    }

    rightTable = new CmisTable(modelR);

    //Nawaz 09/19/01
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable chargeTableStyle = (Hashtable) templateInfoH.get("TABLE_STYLE");
    Color color = (Color) chargeTableStyle.get("COLOR");
    Integer t = (Integer) chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer) chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer) chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer) chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer) chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(), l.intValue(), b.intValue(), r.intValue()), a.intValue());
    TableColumnModel cm = rightTable.getColumnModel();
    for (int i = 0; i < rightTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String) chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer) chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer) chargeTableStyle.get("FONT_SIZE");
    rightTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    Enumeration enum1 = rightTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    rightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    rightTable.getColumn("Report Fields").setWidth(220);
    rightTable.getColumn("Report Fields").setMinWidth(220);
    rightTable.getColumn("Report Fields").setMaxWidth(220);
    rightTable.getColumn("Compatibility").setWidth(0);
    rightTable.getColumn("Compatibility").setMinWidth(0);
    rightTable.getColumn("Compatibility").setMaxWidth(0);
    rightTable.getColumn("Color").setWidth(0);
    rightTable.getColumn("Color").setMinWidth(0);
    rightTable.getColumn("Color").setMaxWidth(0);
    rightTable.setToolTipText(" If you select a row, new fields will be added after.  ");

    leftTable = new CmisTable(modelL);
    leftTable.setCellSelectionEnabled(false);

    //Nawaz 09/19/01

    cm = leftTable.getColumnModel();
    for (int i = 0; i < leftTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    leftTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    enum1 = leftTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    leftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    leftTable.getColumn("Base Fields").setWidth(190);
    leftTable.getColumn("Base Fields").setMinWidth(190);
    leftTable.getColumn("Base Fields").setMaxWidth(190);
    leftTable.getColumn("Compatibility").setWidth(0);
    leftTable.getColumn("Compatibility").setMinWidth(0);
    leftTable.getColumn("Compatibility").setMaxWidth(0);
    leftTable.getColumn("Color").setWidth(0);
    leftTable.getColumn("Color").setMinWidth(0);
    leftTable.getColumn("Color").setMaxWidth(0);

    baseFieldJSP.getViewport().setView(leftTable);
    reportFieldJSP.getViewport().setView(rightTable);

  }

  void buildComboBoxes() {
    Vector categoryV = (Vector) templateInfoH.get("CATEGORY");
    categoryC = ClientHelpObjs.loadChoiceFromVector(categoryC, categoryV);
    partC = ClientHelpObjs.loadChoiceFromVector(partC, (Vector) templateInfoH.get("SEARCH_OPERATOR"));
    mfgC = ClientHelpObjs.loadChoiceFromVector(mfgC, (Vector) templateInfoH.get("SEARCH_OPERATOR"));
    //preselect the first element
    categoryC.setSelectedIndex(0);

    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    facC.removeAllItems();
    facC = ClientHelpObjs.loadChoiceFromVector(facC, (Vector) facApp.get("FACILITY_NAME"));
    facC.setSelectedItem(getFacilityDesc(cmis.getPrefFac()));
    loadReportingEntity();
    if (!reportingEntityC.isVisible()) {
      loadWorkArea();
    }
    facC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
        facilityItemChanged();
      }
    });

    if ("SWA".equalsIgnoreCase(cmis.getResourceBundle().getString("CLIENT_INITIALS").toString())) {
      repCatL.setText("Rpt. Cat.:");
      locL.setText("Loc:");
      repWorkAreaItemListener = new Report_WorkArea_actionAdapter(this,true);
      waC.addItemListener(repWorkAreaItemListener);
      repCatItemListener = new Report_Category_actionAdapter(this);
      repCatC.addItemListener(repCatItemListener);
    } else {
      repCatL.setText("");
      locL.setText("");
      repWorkAreaItemListener = new Report_WorkArea_actionAdapter(this,false);
      waC.addItemListener(repWorkAreaItemListener);
      repCatC.setVisible(false);
      locC.setVisible(false);
      locL.setVisible(false);
      repCatL.setVisible(false);
    }
    loadDock();
  } //end of method

  String getDockForSelectedWA() {
    String result = "";
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    String fac = getFacilityID(facC.getSelectedItem().toString());
    Hashtable h = (Hashtable) facApp.get(fac);
    String reportEntity = "No Reporting Entity";
    if (reportingEntityC.isVisible()) {
      reportEntity = getReportingEntityId( (String) reportingEntityC.getSelectedItem());
    }
    Hashtable innerH = (Hashtable) h.get(reportEntity);
    result = (String) innerH.get("DEFAULT_DOCK"+getWorkAreaID());
    return result;
  } //end of method

  void loadDock() {
    Hashtable dockDeliveryPt = (Hashtable) templateInfoH.get("FACILITY_DOCK_DELIVERY_POINT");
    Hashtable h = (Hashtable) dockDeliveryPt.get(getFacilityID(facC.getSelectedItem().toString()));
    Vector dockIdV = (Vector) h.get("DOCK_ID");
    /*Dock is not links to work area
    Vector dockDescV;
    String dockForWA = getDockForSelectedWA();
    if (dockIdV.contains(dockForWA)) {
      dockDescV = new Vector(1);
      dockDescV.addElement(getDockDesc(dockForWA));
    }else {
      dockDescV = (Vector) h.get("DOCK_DESC");
    }
    */
    Vector dockDescV = (Vector) h.get("DOCK_DESC");
    dockC.removeItemListener(dockItemListener);
    dockC.removeAllItems();
    dockC = ClientHelpObjs.loadChoiceFromVector(dockC, dockDescV);
    dockC.setSelectedIndex(0);
    dockC.addItemListener(dockItemListener);
    if (reportingEntityC.isVisible() && locC.isVisible()) {
      //under location on second column
      bottomP.add(dockL, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bottomP.add(dockC, new GridBagConstraints2(3, 1, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
      facC.setMaximumSize(new Dimension(120, 22));
      facC.setMinimumSize(new Dimension(120, 22));
      facC.setPreferredSize(new Dimension(120, 22));
      waC.setMaximumSize(new Dimension(120, 22));
      waC.setMinimumSize(new Dimension(120, 22));
      waC.setPreferredSize(new Dimension(120, 22));
      dockC.setMaximumSize(new Dimension(120, 22));
      dockC.setMinimumSize(new Dimension(120, 22));
      dockC.setPreferredSize(new Dimension(120, 22));
    } else if (reportingEntityC.isVisible() || locC.isVisible()) {
      //second column
      bottomP.add(dockL, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bottomP.add(dockC, new GridBagConstraints2(3, 0, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
      facC.setMaximumSize(new Dimension(120, 22));
      facC.setMinimumSize(new Dimension(120, 22));
      facC.setPreferredSize(new Dimension(120, 22));
      waC.setMaximumSize(new Dimension(120, 22));
      waC.setMinimumSize(new Dimension(120, 22));
      waC.setPreferredSize(new Dimension(120, 22));
      dockC.setMaximumSize(new Dimension(120, 22));
      dockC.setMinimumSize(new Dimension(120, 22));
      dockC.setPreferredSize(new Dimension(120, 22));
    } else {
      //under work area on first column
      bottomP.add(dockL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bottomP.add(dockC, new GridBagConstraints2(1, 2, 3, 1, 1.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
      facC.setMaximumSize(new Dimension(230, 22));
      facC.setMinimumSize(new Dimension(230, 22));
      facC.setPreferredSize(new Dimension(230, 22));
      waC.setMaximumSize(new Dimension(230, 22));
      waC.setMinimumSize(new Dimension(230, 22));
      waC.setPreferredSize(new Dimension(230, 22));
      dockC.setMaximumSize(new Dimension(230, 22));
      dockC.setMinimumSize(new Dimension(230, 22));
      dockC.setPreferredSize(new Dimension(230, 22));
    }
    loadDeliveryPoint();
  } //end of method

  String getDeliveryPtForSelectedWA() {
    String result = "";
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    String fac = getFacilityID(facC.getSelectedItem().toString());
    Hashtable h = (Hashtable) facApp.get(fac);
    String reportEntity = "No Reporting Entity";
    if (reportingEntityC.isVisible()) {
      reportEntity = getReportingEntityId( (String) reportingEntityC.getSelectedItem());
    }
    Hashtable innerH = (Hashtable) h.get(reportEntity);
    result = (String) innerH.get("DEFAULT_DELIVERY_POINT"+getWorkAreaID());
    return result;
  } //end of method

  void loadDeliveryPoint() {
    Hashtable dockDeliveryPt = (Hashtable) templateInfoH.get("FACILITY_DOCK_DELIVERY_POINT");
    Hashtable h = (Hashtable) dockDeliveryPt.get(getFacilityID(facC.getSelectedItem().toString()));
    Hashtable innerH = (Hashtable) h.get(getDockID( (String) dockC.getSelectedItem()));
    Vector dpIdV = (Vector) innerH.get("DELIVERY_POINT_ID");
    /*Delivery points do not link by work area
    Vector dpDescV;
    String defaultDeliveryPt = getDeliveryPtForSelectedWA();
    if (dpIdV.contains(defaultDeliveryPt)) {
      dpDescV = new Vector(1);
      dpDescV.addElement(getDeliveryPtDesc((String)dockC.getSelectedItem(),defaultDeliveryPt));
    }else {
      dpDescV = (Vector) innerH.get("DELIVERY_POINT_DESC");
    }
    */
    Vector dpDescV = (Vector) innerH.get("DELIVERY_POINT_DESC");
    deliveryPtC.removeAllItems();
    deliveryPtC = ClientHelpObjs.loadChoiceFromVector(deliveryPtC, dpDescV);
    deliveryPtC.setSelectedIndex(0);
    if (reportingEntityC.isVisible() && locC.isVisible()) {
      //below dock on second column
      bottomP.add(deliveryPtL, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bottomP.add(deliveryPtC, new GridBagConstraints2(3, 2, 1, 1, 1.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
      deliveryPtC.setMaximumSize(new Dimension(120, 22));
      deliveryPtC.setMinimumSize(new Dimension(120, 22));
      deliveryPtC.setPreferredSize(new Dimension(120, 22));
    } else if (reportingEntityC.isVisible() || locC.isVisible()) {
      //below dock on second column
      bottomP.add(deliveryPtL, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bottomP.add(deliveryPtC, new GridBagConstraints2(3, 1, 1, 1, 1.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
      deliveryPtC.setMaximumSize(new Dimension(120, 22));
      deliveryPtC.setMinimumSize(new Dimension(120, 22));
      deliveryPtC.setPreferredSize(new Dimension(120, 22));
    } else {
      //first column
      bottomP.add(deliveryPtL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bottomP.add(deliveryPtC, new GridBagConstraints2(1, 3, 3, 1, 1.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
      deliveryPtC.setMaximumSize(new Dimension(160, 22));
      deliveryPtC.setMinimumSize(new Dimension(160, 22));
      deliveryPtC.setPreferredSize(new Dimension(160, 22));
    }
  } //end of method

  void facilityItemChanged() {
    loadReportingEntity();
    if (!reportingEntityC.isVisible()) {
      loadWorkArea();
    }
    loadDock();
  } //end of method

  void loadReportingEntity() {
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    Hashtable h = (Hashtable) facApp.get(getFacilityID(facC.getSelectedItem().toString()));
    String reportingEntityLabel = (String) h.get("REPORTING_ENTITY_LABEL");
    Vector reportEntityV = (Vector) h.get("REPORTING_ENTITY_DESC");
    if (!BothHelpObjs.isBlankString(reportingEntityLabel)) {
      reportingEntityC.removeItemListener(reportingEntityItemListener);
      reportingEntityC.removeAllItems();
      reportingEntityL.setText(reportingEntityLabel + ":");
      reportingEntityC = ClientHelpObjs.loadChoiceFromVector(reportingEntityC, reportEntityV);
      bottomP.add(reportingEntityL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
      bottomP.add(reportingEntityC, new GridBagConstraints2(1, 1, 1, 1, 1.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
      reportingEntityL.setVisible(true);
      reportingEntityC.setVisible(true);
      reportingEntityC.setSelectedIndex(0);
      reportingEntityC.addItemListener(reportingEntityItemListener);
      loadWorkArea();
    } else {
      /*
             bottomP.add(reportingEntityL, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
             bottomP.add(reportingEntityC, new GridBagConstraints2(3, 2, 1, 1, 1.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
       */
      reportingEntityL.setVisible(false);
      reportingEntityC.setVisible(false);
    }
  } //end of method

  String getReportingEntityDesc(String reportingEntityId) {
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    Hashtable h = (Hashtable) facApp.get(getFacilityID(facC.getSelectedItem().toString()));
    Vector reportEntityDescV = (Vector) h.get("REPORTING_ENTITY_DESC");
    Vector reportEntityIdV = (Vector) h.get("REPORTING_ENTITY_ID");
    int index = reportEntityIdV.indexOf(reportingEntityId);
    if (index < 0) {
      index = 0;
    }
    return (reportEntityDescV.elementAt(index).toString());
  } //end of method


  String getReportingEntityId(String reportingEntityDesc) {
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    Hashtable h = (Hashtable) facApp.get(getFacilityID(facC.getSelectedItem().toString()));
    Vector reportEntityDescV = (Vector) h.get("REPORTING_ENTITY_DESC");
    Vector reportEntityIdV = (Vector) h.get("REPORTING_ENTITY_ID");
    int index = reportEntityDescV.indexOf(reportingEntityDesc);
    if (index < 0) {
      index = 0;
    }
    return (reportEntityIdV.elementAt(index).toString());
  } //end of method

  String getFacilityID(String facDesc) {
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    Vector facDescV = (Vector) facApp.get("FACILITY_NAME");
    Vector facIDV = (Vector) facApp.get("FACILITY");
    int index = facDescV.indexOf(facDesc);
    if (index < 0) {
      index = 0;
    }
    return (facIDV.elementAt(index).toString());
  }

  String getFacilityDesc(String facID) {
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    Vector facDescV = (Vector) facApp.get("FACILITY_NAME");
    Vector facIDV = (Vector) facApp.get("FACILITY");
    int index = facIDV.indexOf(facID);
    if (index < 0) {
      index = 0;
    }
    return (facDescV.elementAt(index).toString());
  } //end of method

  String getWorkAreaID() {
    //System.out.println("here in get work area id ");
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    String fac = getFacilityID(facC.getSelectedItem().toString());
    Hashtable h = (Hashtable) facApp.get(fac);
    String reportEntity = "No Reporting Entity";
    if (reportingEntityC.isVisible()) {
      reportEntity = getReportingEntityId( (String) reportingEntityC.getSelectedItem());
    }
    Hashtable innerH = (Hashtable) h.get(reportEntity);
    Vector appId = (Vector) innerH.get("APPLICATION");
    return ( (String) appId.elementAt(waC.getSelectedIndex()));
  } //end of method

  String getWorkAreaDesc(String workArea) {
    //System.out.println("here in get work area id ");
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    String fac = getFacilityID(facC.getSelectedItem().toString());
    Hashtable h = (Hashtable) facApp.get(fac);
    String reportEntity = "No Reporting Entity";
    if (reportingEntityC.isVisible()) {
      reportEntity = getReportingEntityId( (String) reportingEntityC.getSelectedItem());
    }
    Hashtable innerH = (Hashtable) h.get(reportEntity);
    Vector appId = (Vector) innerH.get("APPLICATION");
    Vector appDesc = (Vector) innerH.get("APPLICATION_DESC");
    int index = appId.indexOf(workArea);
    if (index < 0) {
      index = 0;
    }
    return ( (String) appDesc.elementAt(index));
  } //end of method



  void loadWorkArea() {
    waC.removeItemListener(repWorkAreaItemListener);
    waC.removeAllItems();
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITY_WORK_AREA");
    String fac = getFacilityID(facC.getSelectedItem().toString());
    Hashtable h = (Hashtable) facApp.get(fac);
    String reportEntity = "No Reporting Entity";
    if (reportingEntityC.isVisible()) {
      reportEntity = getReportingEntityId( (String) reportingEntityC.getSelectedItem());
      bottomP.add(jLabel4, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bottomP.add(waC, new GridBagConstraints2(1, 2, 3, 1, 1.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    } else {
      bottomP.add(jLabel4, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
      bottomP.add(waC, new GridBagConstraints2(1, 1, 3, 1, 1.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    }
    Hashtable innerH = (Hashtable) h.get(reportEntity);
    waC = ClientHelpObjs.loadChoiceFromVector(waC, (Vector) innerH.get("APPLICATION_DESC"));
    waC.setSelectedIndex(0);
    waC.addItemListener(repWorkAreaItemListener);
    if ("SWA".equalsIgnoreCase(cmis.getResourceBundle().getString("CLIENT_INITIALS").toString())) {
      loadReportCategory();
      loadLocation();
    }
  } //end of method

  void loadReportCategory() {
    //System.out.println("------- how many time do I get call loadReportCategory");
    repCatC.removeItemListener(repCatItemListener);
    repCatC.removeAllItems();
    String facId = getFacilityID(facC.getSelectedItem().toString());
    String waId = getWorkAreaID();
    Hashtable h = (Hashtable) templateInfoH.get("REPORT_CATEGORY");
    Vector v = (Vector) h.get(facId + waId);
    repCatC = ClientHelpObjs.loadChoiceFromVector(repCatC, v);
    repCatC.addItemListener(repCatItemListener);
    if (reportingEntityC.isVisible()) {
      bottomP.add(repCatL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
          , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 3), 0, 0));
      bottomP.add(repCatC, new GridBagConstraints2(1, 3, 1, 1, 1.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    } else {
      bottomP.add(repCatL, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bottomP.add(repCatC, new GridBagConstraints2(1, 2, 1, 1, 1.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    }
  } //end of method

  void loadLocation() {
    //System.out.println("------- how many time do I get call loadLocation");
    locC.removeAllItems();
    String facId = getFacilityID(facC.getSelectedItem().toString());
    String waId = getWorkAreaID();
    if (repCatC.getComponentCount() < 1) {
      return;
    }
    String category = repCatC.getSelectedItem().toString();
    Hashtable h = (Hashtable) templateInfoH.get("LOCATION");
    Vector v = (Vector) h.get(facId + waId + category);
    locC = ClientHelpObjs.loadChoiceFromVector(locC, v);
    if (reportingEntityC.isVisible()) {
      bottomP.add(locL, new GridBagConstraints2(2, 0, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
      bottomP.add(locC, new GridBagConstraints2(3, 0, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
    } else {
      bottomP.add(locL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 3), 0, 0));
      bottomP.add(locC, new GridBagConstraints2(1, 3, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    }
  }

  void interactiveR_actionPerformed(ActionEvent e) {
    if (interactiveR.isSelected()) {
      batchR.setSelected(false);
      batchNameL.setEnabled(false);
      batchNameT.setEnabled(false);
    } else {
      batchR.setSelected(true);
    }
  }

  void batchR_actionPerformed(ActionEvent e) {
    if (batchR.isSelected()) {
      interactiveR.setSelected(false);
      batchNameL.setEnabled(true);
      batchNameT.setEnabled(true);
    } else {
      interactiveR.setSelected(true);

    }
  }

  void backB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (rightTable.getSelectedRow() < 0) {
      GenericDlg g = new GenericDlg(cmis.getMain(), "Select a row", true);
      g.setMsg("Please select row(s) that you want to move.");
      g.setVisible(true);
    } else {
      int[] selectedRows = new int[baseField.size()];
      selectedRows = rightTable.getSelectedRows();
      //getting the data from table
      boolean contChem = false;
      Vector selectedIndex = new Vector();
      Vector myDataV = new Vector();
      for (int i = 0; i < selectedRows.length; i++) {
        String tmpData = rightTable.getModel().getValueAt(selectedRows[i], 0).toString();
        myDataV.addElement(tmpData);
        myDataV.addElement(rightTable.getModel().getValueAt(selectedRows[i], 1).toString());
        myDataV.addElement( (Color) rightTable.getModel().getValueAt(selectedRows[i], 2));

        //Check to see if user decided to remove CAS or Chemical Name from report field table
        if (tmpData.startsWith("CAS") || tmpData.equalsIgnoreCase("Chemical Name")) {
          contChem = true;
        }
        selectedIndex.addElement(new Integer(selectedRows[i]));
      }

      //check report field table to see if Chemical Low,high, still remain
      boolean contChem2 = false;
      boolean contSpecitated = false;
      for (int n = 0; n < rightTable.getRowCount(); n++) {
        //if current has been selected then continue
        if (selectedIndex.contains(new Integer(n))) {
          continue;
        }
        String tmpField = rightTable.getModel().getValueAt(n, 0).toString();
        String tmpComp = rightTable.getModel().getValueAt(n, 1).toString();
        if (tmpField.startsWith("CAS") || tmpField.equalsIgnoreCase("Chemical Name")) {
          contChem2 = true;
        } else if (tmpComp.equalsIgnoreCase("S")) {
          contSpecitated = true;
        }

      }
      //cannot leave chemical low, high, avg in report field without CAS or Chemical Name
      if (contChem && !contChem2 && contSpecitated) {
        GenericDlg g = new GenericDlg(cmis.getMain(), "Error", true);
        String msg = " You must have CAS Number or Chemical Name in report field\n with speciated columns.";
        g.setMsg(msg);
        g.setVisible(true);
        return;
      }

      //remove data from table
      for (int k = 0; k < selectedRows.length; k++) {
        removeRowFromTable(rightTable, selectedRows[k] - k);
      }
      //now move data to
      for (int j = 0; j < myDataV.size(); j++) {
        //String tmpData2 = (String)myDataV.elementAt(j);
        Object[] oa = new Object[colHeadR.length];
        oa[0] = (String) myDataV.elementAt(j);
        oa[1] = (String) myDataV.elementAt(++j);
        oa[2] = (Color) myDataV.elementAt(++j);
        addRowToTable(leftTable, oa, leftTable.getSelectedRow());
      }
    }
    reSetColoredCell(leftTable);
    reSetColoredCell(rightTable);
  }

  void mfgSearchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    MfgSelectDlg msd = new MfgSelectDlg(cmis);
    CenterComponent.centerCompOnScreen(msd);
    msd.setVisible(true);
    if (!msd.isCanceled()) {
      mfgT.setText(msd.mfgDesc);
      mfgID = msd.mfgID;
    }
  }

  void clearPartNo() {
    String fID = getFacilityID(facC.getSelectedItem().toString());
    if (!lastFacID.equals(fID)) {
      partNoT.setText("");
    }
    lastFacID = fID;
  }

  void partNoB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String facDesc = facC.getSelectedItem().toString();
    if (facDesc.startsWith("All Facilities")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),
                                     "Select a facility", true);
      gd.setMsg("Please select a facility.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
    } else {
      PartNoSelectDlg msd = new PartNoSelectDlg(cmis, facDesc);
      //msd.loadItAction();
      msd.setFacilityID(getFacilityID(facC.getSelectedItem().toString()));
      CenterComponent.centerCompOnScreen(msd);
      msd.setVisible(true);
      if (!msd.canceled) {
        partNoT.setText(BothHelpObjs.makeBlankFromNull(msd.partNo));
      }
    }
  }

  void begMonC_actionPerformed(ActionEvent e) {
    begDayC.removeAllItems();
    int monthIndex = begMonC.getSelectedIndex();
    int yearIndex = begYearC.getSelectedIndex();
    begDayC = ClientHelpObjs.loadChoiceFromVector(begDayC, getDays(monthIndex, yearIndex, "begin"));
  }

  void begYearC_actionPerformed(ActionEvent e) {
    begDayC.removeAllItems();
    int monthIndex = begMonC.getSelectedIndex();
    int yearIndex = begYearC.getSelectedIndex();
    begDayC = ClientHelpObjs.loadChoiceFromVector(begDayC, getDays(monthIndex, yearIndex, "begin"));
  }

  void endMonthC_actionPerformed(ActionEvent e) {
    endDayC.removeAllItems();
    int monthIndex = endMonthC.getSelectedIndex();
    int yearIndex = endYearC.getSelectedIndex();
    Vector days = getDays(monthIndex, yearIndex, "end");
    endDayC = ClientHelpObjs.loadChoiceFromVector(endDayC, days);
    endDayC.setSelectedIndex(days.size() - 1);
  }

  void endYearC_actionPerformed(ActionEvent e) {
    endDayC.removeAllItems();
    int monthIndex = endMonthC.getSelectedIndex();
    int yearIndex = endYearC.getSelectedIndex();
    Vector days = getDays(monthIndex, yearIndex, "end");
    endDayC = ClientHelpObjs.loadChoiceFromVector(endDayC, days);
    endDayC.setSelectedIndex(days.size() - 1);
  }

  Vector getDays(int monthIndex, int yearIndex, String whichYear) {
    Vector days = new Vector();
    if (monthIndex == 0 || monthIndex == 2 ||
        monthIndex == 4 || monthIndex == 6 ||
        monthIndex == 7 || monthIndex == 9 ||
        monthIndex == 11) {
      for (int i = 1; i < 32; i++) {
        days.addElement(new Integer(i));
      }
    } else if (monthIndex == 1) {
      int tempYear = 1;
      try {
        if ("end".equalsIgnoreCase(whichYear)) {
          tempYear = Integer.parseInt( (String) endYearC.getSelectedItem());
        } else {
          tempYear = Integer.parseInt( (String) begYearC.getSelectedItem());
        }
      } catch (Exception e) {
        tempYear = 1;
      }
      //if (yearIndex == 5 || yearIndex == 1) {       //leap year 2000,2004
      if (tempYear % 4 == 0) {
        for (int i = 1; i < 30; i++) {
          days.addElement(new Integer(i));
        }
      } else {
        for (int i = 1; i < 29; i++) {
          days.addElement(new Integer(i));
        }
      }
    } else {
      for (int i = 1; i < 31; i++) {
        days.addElement(new Integer(i));
      }
    }
    return days;
  } //end of method

}

class Report_Category_actionAdapter implements java.awt.event.ItemListener {
  AdHocUsageReport adaptee;

  Report_Category_actionAdapter(AdHocUsageReport adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent evt) {
    //System.out.println("======= item state change report category ");
    adaptee.loadLocation();
  }

}

class Report_Dock_actionAdapter implements java.awt.event.ItemListener {
  AdHocUsageReport adaptee;

  Report_Dock_actionAdapter(AdHocUsageReport adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent evt) {
    adaptee.loadDeliveryPoint();
  }
}

class Report_ReportingEntity_actionAdapter implements java.awt.event.ItemListener {
  AdHocUsageReport adaptee;

  Report_ReportingEntity_actionAdapter(AdHocUsageReport adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent evt) {
    adaptee.loadWorkArea();
  }
}

class Report_WorkArea_actionAdapter implements java.awt.event.ItemListener {
  AdHocUsageReport adaptee;
  boolean showRptCategoryNLocation = false;

  Report_WorkArea_actionAdapter(AdHocUsageReport adaptee, boolean showRptCategoryNLocation) {
    this.adaptee = adaptee;
    this.showRptCategoryNLocation = showRptCategoryNLocation;
  }

  public void itemStateChanged(ItemEvent evt) {
    if (showRptCategoryNLocation) {
      adaptee.loadReportCategory();
      adaptee.loadLocation();
    }
    //dock is no longer link to work area
    //adaptee.loadDock();
  }
} //end of class

class AdHocReportLoadThread extends Thread {
  AdHocUsageReport ahur;
  String action;
  public AdHocReportLoadThread(AdHocUsageReport ahur, String action) {
    super();
    this.ahur = ahur;
    this.action = action;
  }

  public void run() {
    if (action.equalsIgnoreCase("load")) {
      ahur.loadItAction("load");
    } else if (action.equalsIgnoreCase("reload")) {
      ahur.loadItAction("reload");
    }
  }
}
