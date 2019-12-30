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
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class MaterialMatrix extends ReportInputPanel
//public class MaterialMatrix extends JPanel
{

  GridBagLayout gridBagLayout1 = new GridBagLayout();
  ButtonGroup bg = new ButtonGroup();
  JPanel bottomP = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();

  JComboBox facilityC = new JComboBox();
  JComboBox workAreaC = new JComboBox();

  JButton forwardB = new JButton();
  JPanel reportListP = new JPanel();
  JScrollPane reportListJSP = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JButton backB = new JButton();
  CmisTable chemRightTable;

  JPanel chemicalListP = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane chemicalListJSP = new JScrollPane();
  CmisTable leftTable;

  String[] colHeadR = {
      "Report Lists"
  };
  String[] colHeadL = {
      "Chemical Lists"
  };

  String[] colHeadR1 = {
      "Report Fields"
  };
  String[] colHeadL1 = {
      "Base Fields"
  };
  String[] formating = {
      "Yes", "xx.x %", "xx.x +/- y %", "lbs"};
  Vector baseField = new Vector();
  Vector chemicalList = new Vector();

  final static int MAXSELECTEDROW = 50;
  Hashtable templateInfoH = null;
  JScrollPane reportFieldJSP = new JScrollPane();
  CmisTable rightTable;
  JButton forwardB1 = new JButton();
  JButton backB1 = new JButton();
  JPanel reportFieldP = new JPanel();
  JScrollPane baseFieldJSP = new JScrollPane();
  CmisTable chemLeftTable;
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  JRadioButton interactiveR = new JRadioButton();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel baseFieldP = new JPanel();
  JRadioButton batchR = new JRadioButton();
  JPanel bottomP1 = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  MonthCombo endMonthC = new MonthCombo( -1);
  YearCombo endYearC = new YearCombo( -2, 6, 0);
  JRadioButton partNumberRB = new JRadioButton();
  StaticJLabel end = new StaticJLabel();
  JPanel topP = new JPanel();
  JRadioButton allPartsApprovedRB = new JRadioButton();
  //JComboBox workareacombo = new JComboBox();
  StaticJLabel workarea = new StaticJLabel();
  StaticJLabel begin = new StaticJLabel();
  YearCombo begYearC = new YearCombo( -2, 6, -1);
  JRadioButton allPartsUsedRB = new JRadioButton();
  MonthCombo begMonC = new MonthCombo(0);
  //JComboBox facilitycombo = new JComboBox();
  GridBagLayout gridBagLayoutT = new GridBagLayout();
  StaticJLabel facility = new StaticJLabel();
  JTextField partNoT = new JTextField();
  //JButton partNumB = new JButton();
  DataJLabel tempL = new DataJLabel();

  String lastFacID = "";
  StaticJLabel FormatL = new StaticJLabel();
  JComboBox formatJC = new JComboBox(formating);
  boolean firstTime = true;
  StaticJLabel batchNameL = new StaticJLabel();
  JTextField batchNameT = new JTextField();

  JRadioButton partsInventoryRB = new JRadioButton();
  MonthCombo inventoryMonC = new MonthCombo(0);
  JComboBox inventoryDayC = new JComboBox();
  YearCombo inventoryYearC = new YearCombo( -2, 6, -1);
  StaticJLabel inventoryL = new StaticJLabel();
  JComboBox partC = new JComboBox();

  StaticJLabel reportingEntityL = new StaticJLabel();
  JComboBox reportingEntityC = new JComboBox();
  ItemListener reportingEntityItemListener;

  //formatJC.insadd("Nawaz)
  /**
   *  Constructor for the MaterialMatrix object
   *
   *@param  cmis  Description of Parameter
   */
  public MaterialMatrix(CmisApp cmis) {
    this(cmis, false);
  }

  /**
   *  Constructor for the MaterialMatrix object
   *
   *@param  cmis         Description of Parameter
   *@param  isVocReport  Description of Parameter
   */
  public MaterialMatrix(CmisApp cmis, boolean isVocReport) {
    super(cmis, "Share", "Ad Hoc Material Matrix");
  }

  public void loadScreen() {
    if (firstTime) {
      try {
        jbInit();
        facilityC.addItemListener(new java.awt.event.ItemListener() {
          public void itemStateChanged(ItemEvent evt) {
            facilityItemChanged();
            clearPartNo();
          }
        });
        loadIt();
      } catch (Exception e) {
        e.printStackTrace();
      }
      firstTime = false;
    }
  }

  void facilityItemChanged() {
    loadReportingEntity();
    if (!reportingEntityC.isVisible()) {
      loadWorkArea();
    }
  } //end of method

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
          tempYear = Integer.parseInt( (String) inventoryYearC.getSelectedItem());
        } else {
          tempYear = Integer.parseInt( (String) inventoryYearC.getSelectedItem());
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

  void inventoryMonC_actionPerformed(ActionEvent e) {
    int dayIndex = inventoryDayC.getSelectedIndex();
    inventoryDayC.removeAllItems();
    int monthIndex = inventoryMonC.getSelectedIndex();
    int yearIndex = begYearC.getSelectedIndex();
    inventoryDayC = ClientHelpObjs.loadChoiceFromVector(inventoryDayC, getDays(monthIndex, yearIndex, "begin"));
    if (dayIndex < inventoryDayC.getItemCount()) {
      inventoryDayC.setSelectedIndex(dayIndex);
    } else {
      inventoryDayC.setSelectedIndex(0);
    }
  }

  void inventoryYearC_actionPerformed(ActionEvent e) {
    int dayIndex = inventoryDayC.getSelectedIndex();
    inventoryDayC.removeAllItems();
    int monthIndex = inventoryMonC.getSelectedIndex();
    int yearIndex = inventoryYearC.getSelectedIndex();
    inventoryDayC = ClientHelpObjs.loadChoiceFromVector(inventoryDayC, getDays(monthIndex, yearIndex, "begin"));
    if (dayIndex < inventoryDayC.getItemCount()) {
      inventoryDayC.setSelectedIndex(dayIndex);
    } else {
      inventoryDayC.setSelectedIndex(0);
    }
  }

  int getInventoryDayIndex() {
    Calendar cal = Calendar.getInstance();
    return (cal.get(Calendar.DAY_OF_MONTH) - 1);
  }

  boolean auditInventoryDay() {
    boolean result = true;
    Calendar cal = Calendar.getInstance();
    int monthIndex = inventoryMonC.getSelectedIndex();
    int dayIndex = inventoryDayC.getSelectedIndex();
    String inventoryYear = inventoryYearC.getSelectedItem().toString();
    try {
      //Month
      Integer tmpMonth = new Integer(monthIndex);
      tmpMonth = new Integer(tmpMonth.intValue());
      //day
      Integer tmpDay = new Integer(dayIndex);
      tmpDay = new Integer(tmpDay.intValue() + 1);
      //year
      Integer tmpYear = new Integer(inventoryYear);
      Calendar selectedCal = Calendar.getInstance();
      selectedCal.set(tmpYear.intValue(), tmpMonth.intValue(), tmpDay.intValue());
      //now make sure that selected date is not in the future
      Date tmpDate = cal.getTime();
      Date selectedDate = selectedCal.getTime();
      if (tmpDate.getTime() < selectedDate.getTime()) {
        GenericDlg.showMessage("Parts in Inventory must be today or any time in the past.");
        result = false;
      } else {
        result = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public void loadIt() {
    MaterialMatrixLoadThread ahrlt = new MaterialMatrixLoadThread(this, "load");
    ahrlt.start();
  }

  public void loadItAction(String action) {
    CursorChange.setCursor(this, Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this, false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialMatrix", cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION", "GET_MATRIX_TEMPLATE_INFO"); //String, String
      query.put("USERID", cmis.getUserId().toString());
      Hashtable result = cgi.getResultHash(query);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this, true);
        return;
      }
      templateInfoH = (Hashtable) result.get("MATRIX_TEMPLATE_INFO");
      templateInfoH.put("TABLE_STYLE", (Hashtable) result.get("TABLE_STYLE"));
      reportingEntityItemListener = new Report_MaterialMatrixReportingEntity_actionAdapter(this);
      buildTable();
      buildComboBoxes();
      begYearC.removeAllItems();
      begYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), -1);
      endYearC.removeAllItems();
      endYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), 0);

      //display Parts in Inventory if CAL
      if (! ( (Boolean) templateInfoH.get("DISPLAY_PARTS_IN_INVENTORY")).booleanValue()) {
        this.topP.remove(partsInventoryRB);
        this.topP.remove(inventoryMonC);
        this.topP.remove(inventoryDayC);
        this.topP.remove(inventoryYearC);
        this.topP.remove(inventoryL);
      } else {
        inventoryDayC.removeAllItems();
        inventoryDayC = ClientHelpObjs.loadChoiceFromVector(inventoryDayC, getDays(inventoryMonC.getSelectedIndex(), inventoryYearC.getSelectedIndex(), "begin"));
        inventoryDayC.setSelectedIndex(getInventoryDayIndex());
        inventoryYearC.removeAllItems();
        inventoryYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), 0);
      }
    } catch (Exception e) {
      e.printStackTrace();
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this, true);
      return;
    }

    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
    ClientHelpObjs.setEnabledContainer(this, true);
    radioButtonClicked();
    this.repaint();
    ClientHelpObjs.setComboBoxUpdateUi(this);
  }

  public String getFacilityDesc(String facID) {
    Hashtable dataH = (Hashtable) templateInfoH.get("FACILITIES_INFO");
    Vector facIDV = (Vector) dataH.get("FACILITY");
    Vector facNameV = (Vector) dataH.get("FACILITY_NAME");
    int index = facIDV.indexOf(facID);
    if (index < 0) {
      index = 0;
    }
    return ( (String) facNameV.elementAt(index));
  }

  public String getFacilityID() {
    Hashtable dataH = (Hashtable) templateInfoH.get("FACILITIES_INFO");
    Vector facIDV = (Vector) dataH.get("FACILITY");
    return ( (String) facIDV.elementAt(facilityC.getSelectedIndex()));
  }

  public String getWorkAreaID() {
    Hashtable dataH = (Hashtable) templateInfoH.get("FACILITIES_INFO");
    Hashtable h = (Hashtable) dataH.get(getFacilityID());
    String reportEntity = "No Reporting Entity";
    if (reportingEntityC.isVisible()) {
      reportEntity = getReportingEntityId( (String) reportingEntityC.getSelectedItem());
    }
    Hashtable innerH = (Hashtable) h.get(reportEntity);
    Vector workIDV = (Vector) innerH.get("APPLICATION");
    return ( (String) workIDV.elementAt(workAreaC.getSelectedIndex()));
  }

  public String getWorkAreaDesc(String waID) {
    Hashtable dataH = (Hashtable) templateInfoH.get("FACILITIES_INFO");
    Hashtable h = (Hashtable) dataH.get(getFacilityID());
    String reportEntity = "No Reporting Entity";
    if (reportingEntityC.isVisible()) {
      reportEntity = getReportingEntityId( (String) reportingEntityC.getSelectedItem());
    }
    Hashtable innerH = (Hashtable) h.get(reportEntity);
    Vector workIDV = (Vector) innerH.get("APPLICATION");
    Vector workDescV = (Vector) innerH.get("APPLICATION_DESC");
    int index = workIDV.indexOf(waID);
    if (index < 0) {
      index = 0;
    }
    return ( (String) workDescV.elementAt(index));
  }

  public void loadWorkArea() {
    Hashtable dataH = (Hashtable) templateInfoH.get("FACILITIES_INFO");
    Hashtable h = (Hashtable) dataH.get(getFacilityID());
    String reportEntity = "No Reporting Entity";
    if (reportingEntityC.isVisible()) {
      reportEntity = getReportingEntityId( (String) reportingEntityC.getSelectedItem());
    }
    Hashtable innerH = (Hashtable) h.get(reportEntity);
    Vector workDescV = (Vector) innerH.get("APPLICATION_DESC");
    workAreaC.removeAllItems();
    workAreaC = ClientHelpObjs.loadChoiceFromVector(workAreaC, workDescV);
    workAreaC.setSelectedIndex(0);
  } //end of method

  public void loadTemplate(String template) {
    CursorChange.setCursor(this, Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this, false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialMatrix",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION", "LOAD_MATRIX_TEMPLATE_INFO"); //String, String
      query.put("USER_ID", cmis.getUserId()); //String, Integer
      query.put("TEMPLATE", template); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this, true);
        return;
      }
      Hashtable infoH = (Hashtable) result.get("TEMPLATE_INFO");
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
    super.setReportTitle("Ad Hoc Material Matrix - " + template);
    ClientHelpObjs.setComboBoxUpdateUi(this);
  }

  public void loadData(Hashtable data) {
    String partN = (String) data.get("PART_NUMBER");
    String partOperator = (String) data.get("PART_OPERATOR");
    String type = (String) data.get("TYPE");
    String beginY = (String) data.get("BEGIN_YEAR");
    String beginM = (String) data.get("BEGIN_MONTH");
    String endY = (String) data.get("END_YEAR");
    String endM = (String) data.get("END_MONTH");
    String facID = (String) data.get("FACILITY_ID");
    String workArea = (String) data.get("WORK_AREA");
    String reportEntity = (String) data.get("REPORTING_ENTITY");
    Vector report = (Vector) data.get("REPORT_FIELDS");
    Vector report1 = (Vector) data.get("REPORT_FIELDS1");
    String formater = (String) data.get("FORMAT");
    String inventoryMonth = (String) data.get("INVENTORY_MONTH");
    String inventoryDay = (String) data.get("INVENTORY_DAY");
    String inventoryYear = (String) data.get("INVENTORY_YEAR");

    //selecting report type
    if (type.equalsIgnoreCase("Part Number")) {
      partNumberRB.setSelected(true);
      allPartsApprovedRB.setSelected(false);
      allPartsUsedRB.setSelected(false);
      partsInventoryRB.setSelected(false);
    } else if (type.equalsIgnoreCase("All Used")) {
      allPartsUsedRB.setSelected(true);
      allPartsApprovedRB.setSelected(false);
      partNumberRB.setSelected(false);
      partsInventoryRB.setSelected(false);
    } else if (type.equalsIgnoreCase("Parts in Inventory")) {
      partsInventoryRB.setSelected(true);
      allPartsApprovedRB.setSelected(false);
      partNumberRB.setSelected(false);
      allPartsUsedRB.setSelected(false);
    } else {
      allPartsApprovedRB.setSelected(true);
      partNumberRB.setSelected(false);
      allPartsUsedRB.setSelected(false);
      partsInventoryRB.setSelected(false);
    }

    //part no
    partC.setSelectedItem(partOperator);
    if (!BothHelpObjs.isBlankString(partN)) {
      partNoT.setText(partN);
    } else {
      partNoT.setText("");
    }

    if (facID.startsWith("All")) {
      facilityC.setSelectedIndex(0);
    } else {
      facilityC.setSelectedItem(getFacilityDesc(facID));
    }
    facilityC.revalidate();
    loadReportingEntity();
    reportingEntityC.setSelectedItem(getReportingEntityDesc(reportEntity));
    reportingEntityC.revalidate();
    if (!reportingEntityC.isVisible()) {
      loadWorkArea();
    }
    workAreaC.setSelectedItem(getWorkAreaDesc(workArea));
    workAreaC.revalidate();

    //date
    Integer b1 = new Integer(beginY);
    Integer bm = new Integer(beginM);
    Integer ey = new Integer(endY);
    Integer em = new Integer(endM);
    Integer fmt = new Integer(formater);

    formatJC.removeAllItems();
    Vector tempV = new Vector(formating.length);
    for (int i = 0; i < formating.length; i++) {
      tempV.addElement(formating[i]);
    }
    formatJC = ClientHelpObjs.loadChoiceFromVector(formatJC, tempV);
    formatJC.setSelectedIndex(fmt.intValue());
    begMonC.setSelectedIndex(bm.intValue());
    begMonC.revalidate();
    Color c = new Color(102, 102, 153);
    begMonC.setForeground(c);

    begYearC.setSelectedIndex(b1.intValue());
    begYearC.revalidate();
    begYearC.setForeground(c);

    endMonthC.setSelectedIndex(em.intValue());
    endMonthC.revalidate();
    endMonthC.setForeground(c);

    endYearC.setSelectedIndex(ey.intValue());
    endYearC.revalidate();
    endYearC.setForeground(c);

    if (!BothHelpObjs.isBlankString(inventoryMonth)) {
      Integer im = new Integer(inventoryMonth);
      Integer id = new Integer(inventoryDay);
      Integer iy = new Integer(inventoryYear);
      inventoryMonC.setSelectedIndex(im.intValue());
      inventoryMonC.revalidate();
      inventoryMonC.setForeground(c);
      inventoryDayC.setSelectedIndex(id.intValue());
      inventoryDayC.revalidate();
      inventoryDayC.setForeground(c);
      inventoryYearC.setSelectedIndex(iy.intValue());
      inventoryYearC.revalidate();
      inventoryYearC.setForeground(c);
    }

    {
      chemicalListJSP.getViewport().remove(chemLeftTable);
      reportListJSP.getViewport().remove(chemRightTable);

      baseFieldJSP.getViewport().remove(leftTable);
      reportFieldJSP.getViewport().remove(rightTable);

      DbTableModel modelR = new DbTableModel(colHeadR);
      DbTableModel modelR1 = new DbTableModel(colHeadR1);
      //get model from database
      //Vector baseField = report1;
      //Vector chemicalList = report;

      DbTableModel modelL = new DbTableModel(colHeadL);
      DbTableModel modelL1 = new DbTableModel(colHeadL1);

      //make a copy of base_fields
      Object[] oaL = new Object[colHeadR.length];
      for (int i = 0; i < report.size(); i++) {
        oaL[0] = report.elementAt(i);
        modelR.addRow(oaL);
      }

      Object[] oaL2 = new Object[colHeadL.length];
      for (int i = 0; i < chemicalList.size(); i++) {
        oaL2[0] = chemicalList.elementAt(i);
        if (! (report.contains(oaL2[0]))) {
          modelL.addRow(oaL2);
        }
      }
      //System.out.println("It is here at line 305");
      Object[] oaL1 = new Object[colHeadR1.length];
      for (int i = 0; i < report1.size(); i++) {
        oaL1[0] = report1.elementAt(i);
        modelR1.addRow(oaL1);
      }

      //System.out.println("It is here at line 313");
      Object[] oaL3 = new Object[colHeadL1.length];
      for (int i = 0; i < baseField.size(); i++) {
        oaL3[0] = baseField.elementAt(i);
        if (! (report1.contains(oaL3[0]))) {
          modelL1.addRow(oaL3);
        }
      }

      rightTable = new CmisTable(modelR1);
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

      leftTable = new CmisTable(modelL1);
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

      chemRightTable = new CmisTable(modelR);
      //Nawaz 09/19/01
      cm = chemRightTable.getColumnModel();
      for (int i = 0; i < chemRightTable.getColumnCount(); i++) {
        cm.getColumn(i).setCellRenderer(renderers[0]);
      }
      chemRightTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
      tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
      enum1 = chemRightTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }

      chemRightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

      chemLeftTable = new CmisTable(modelL);
      //Nawaz 09/19/01
      cm = chemLeftTable.getColumnModel();
      for (int i = 0; i < chemLeftTable.getColumnCount(); i++) {
        cm.getColumn(i).setCellRenderer(renderers[0]);
      }
      chemLeftTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
      tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
      enum1 = chemLeftTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }
      chemLeftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

      chemicalListJSP.getViewport().setView(chemLeftTable);
      reportListJSP.getViewport().setView(chemRightTable);

      baseFieldJSP.getViewport().setView(leftTable);
      reportFieldJSP.getViewport().setView(rightTable);
    }
  } //end of method

  void setComboColor() {
    Color keep = tempL.getForeground();
    facilityC.setForeground(keep);
    reportingEntityC.setForeground(keep);
    workAreaC.setForeground(keep);
    begMonC.setForeground(keep);
    begYearC.setForeground(keep);
    endMonthC.setForeground(keep);
    endYearC.setForeground(keep);
  }

  /**
   *  Sets the FacCombo attribute of the MaterialMatrix object
   *
   *@param  fc  The new FacCombo value
   */
  public void setFacCombo(FacilityCombo fc) {
  /*
       facC.setUseAllFacilities(true);
       facC.setUseFacilityWide(true);
       facC.setUseAllWorkAreas(true);
       facC = fc.loadAnotherOne(facC);
       facC.setSelectedItem(cmis.getPrefFac());*/
   }

  public void openTemplate() {
    showOpenTemplateDlg();
  }

  public void showOpenTemplateDlg() {
    OpenTemplateDlg otd = new OpenTemplateDlg(cmis,
                                              "Open Template",
                                              true, "MaterialMatrix");
    if (otd.loadData()) {
      otd.setVisible(true);
      if (otd.isChanged()) {
        String selectedTemplate = otd.getSelectedTemplate();
        if (!BothHelpObjs.isBlankString(selectedTemplate)) {
          //super.setReportTitle("Ad Hoc MATRIX - "+selectedTemplate);
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
    SaveTemplateDlg std = new SaveTemplateDlg(cmis, "Save Template", true, "MaterialMatrix");
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
    super.setReportTitle("Ad Hoc Matrix");
    reSetAllComponents();
  }

  public void reSetAllComponents() {
    //re-setting top
    partNumberRB.setSelected(true);
    allPartsUsedRB.setSelected(false);
    allPartsApprovedRB.setSelected(false);
    partsInventoryRB.setSelected(false);
    inventoryMonC.setEnabled(false);
    inventoryDayC.setEnabled(false);
    inventoryYearC.setEnabled(false);

    //re-setting middle
    facilityC.setSelectedItem(getFacilityDesc(cmis.getPrefFac()));
    workAreaC.setSelectedIndex(0);
    begMonC.reFresh(0);
    begMonC.setEnabled(false);
    begYearC.removeAllItems();
    begYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), -1);
    begYearC.setEnabled(false);
    endMonthC.reFresh( -1);
    endMonthC.setEnabled(false);
    endYearC.removeAllItems();
    endYearC.reFresh(cmis.yearOfReport.intValue(), cmis.numberOfYear.intValue(), 0);
    endYearC.setEnabled(false);
    buildTable();
    //re-setting combo
    partNoT.setText("");
    partC.setSelectedIndex(0);
    interactiveR.setSelected(true);
    batchR.setSelected(false);
    batchNameL.setEnabled(false);
    batchNameT.setEnabled(false);
  }

  /**
   *  Description of the Method
   */
  public void showReport() {
    if (partNumberRB.isSelected()) {
      if (!checkDates(new Integer(begMonC.getSelectedIndex()).toString(),
                      begYearC.getSelectedItem().toString(),
                      new Integer(endMonthC.getSelectedIndex()).toString(),
                      endYearC.getSelectedItem().toString())) {
        showBadDatesDlg();
        return;
      }
    } else if (partsInventoryRB.isSelected()) {
      if (!auditInventoryDay()) {
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

        //ClientHelpObjs.setEnabledContainer(this,false);
        TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialMatrix", cmis);
        Hashtable query = new Hashtable();
        query.put("ACTION", "CREATE_REPORT"); //String, String
        query.put("SELECTED_DATA", currentDataH); //String, Hashtable
        query.put("USER_ID", cmis.getMain().getUserId().toString());
        query.put("WHICH_SCREEN", "MATERIAL_MATRIX");
        Hashtable result = cgi.getResultHash(query);
        //ClientHelpObjs.setEnabledContainer(this, true);
        if (result == null) {
          GenericDlg.showAccessDenied(cmis.getMain());
          return;
        }
        Boolean suc = (Boolean) result.get("SUCCEED");
        //Boolean display = (Boolean)result.get("DISPLAY");
        String msg = (String) result.get("MSG");

        if (suc.booleanValue()) {
          try {
            String url = (String) result.get("URL");
            new URLCall(url, cmis);
            //5-10-01 giving the user some response so they know what to do
            GenericDlg gd = new GenericDlg(cmis.getMain(), "Action Succeeded", true);
            gd.setMsg(msg);
            CenterComponent.centerCompOnScreen(gd);
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
        query.put("WHICH_SCREEN", "MATERIAL_MATRIX");
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
        gd.setMsg(" Your report has been sent to the server for processing,\n an email will notify you upon completion.\n You may now resume other activities within tcmIS.\n Batch report(s) that is older then two weeks will be deleted.");
        gd.setVisible(true);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      //ClientHelpObjs.setEnabledContainer(this, true);
      this.revalidate();
    }
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
    radioButtonClicked();
  }

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
    if (partNumberRB.isSelected()) {
      currentData.put("TYPE", "Part Number");
    } else if (allPartsUsedRB.isSelected()) {
      currentData.put("TYPE", "All Used");
    } else if (partsInventoryRB.isSelected()) {
      currentData.put("TYPE", "Parts in Inventory");
      currentData.put("INVENTORY_MONTH", new Integer(inventoryMonC.getSelectedIndex()));
      currentData.put("INVENTORY_DAY", new Integer(inventoryDayC.getSelectedIndex()));
      currentData.put("INVENTORY_YEAR", new Integer(inventoryYearC.getSelectedIndex()));
      currentData.put("INVENTORY_YEARN", inventoryYearC.getSelectedItem());
    } else {
      currentData.put("TYPE", "All Approved");
    }

    currentData.put("FACILITY_ID", getFacilityID());
    currentData.put("FACILITY_DESC", facilityC.getSelectedItem().toString());
    if (reportingEntityC.isVisible()) {
      currentData.put("REPORTING_ENTITY", getReportingEntityId( (String) reportingEntityC.getSelectedItem()));
      currentData.put("REPORTING_ENTITY_DESC", (String) reportingEntityC.getSelectedItem());
      currentData.put("REPORTING_ENTITY_LABEL",reportingEntityL.getText());
    } else {
      currentData.put("REPORTING_ENTITY", "");
      currentData.put("REPORTING_ENTITY_DESC","");
      currentData.put("REPORTING_ENTITY_LABEL","");
    }
    currentData.put("WORK_AREA", getWorkAreaID());
    currentData.put("WORK_AREA_DESC", workAreaC.getSelectedItem().toString());
    currentData.put("PART_NUMBER", BothHelpObjs.makeBlankFromNull(partNoT.getText()).trim());
    currentData.put("PART_OPERATOR", partC.getSelectedItem().toString());
    currentData.put("BEGIN_MONTH", new Integer(begMonC.getSelectedIndex()));
    currentData.put("BEGIN_YEAR", new Integer(begYearC.getSelectedIndex()));
    currentData.put("END_MONTH", new Integer(endMonthC.getSelectedIndex()));
    currentData.put("END_YEAR", new Integer(endYearC.getSelectedIndex()));

    currentData.put("BEGIN_YEARN", (begYearC.getSelectedItem()));
    currentData.put("END_YEARN", (endYearC.getSelectedItem()));
    currentData.put("FORMAT", new Integer(formatJC.getSelectedIndex()));

    Vector v = new Vector();
    for (int i = 0; i < chemRightTable.getRowCount(); i++) {
      v.addElement(chemRightTable.getValueAt(i, 0));
    }
    currentData.put("REPORT_FIELDS", v);

    Vector v1 = new Vector();
    for (int i = 0; i < rightTable.getRowCount(); i++) {
      v1.addElement(rightTable.getValueAt(i, 0));
    }
    currentData.put("REPORT_FIELDS1", v1);

    return currentData;
  }

  /**
   *  Description of the Method
   */
  void radioButtonClicked() {
    partNumberRB.setSelected(partNumberRB.isSelected());
    //partNumB.setEnabled(partNumberRB.isSelected());
    partNoT.setEnabled(partNumberRB.isSelected());
    partC.setEnabled(partNumberRB.isSelected());
    begMonC.setEnabled(allPartsUsedRB.isSelected());
    begYearC.setEnabled(allPartsUsedRB.isSelected());
    endMonthC.setEnabled(allPartsUsedRB.isSelected());
    endYearC.setEnabled(allPartsUsedRB.isSelected());
    this.batchNameL.setEnabled(this.batchR.isSelected());
    this.batchNameT.setEnabled(this.batchR.isSelected());
    allPartsApprovedRB.setSelected(allPartsApprovedRB.isSelected());
    inventoryMonC.setEnabled(partsInventoryRB.isSelected());
    inventoryDayC.setEnabled(partsInventoryRB.isSelected());
    inventoryYearC.setEnabled(partsInventoryRB.isSelected());
    if (this.allPartsUsedRB.isSelected() || this.partsInventoryRB.isSelected()) {
      //add lbs to format drop down if not already there
      if (formatJC.getItemCount() < 4) {
        formatJC.removeItem("lbs");
      }
    } else {
      //remove lbs from format drop down
      if (formatJC.getItemCount() == 4) {
        formatJC.removeItem("lbs");
      }
    }
  }

  void removeRowFromTable(CmisTable myTable, int row) {
    DbTableModel model = (DbTableModel) myTable.getModel();
    model.removeRow(row);
  }

  void addRowToTable(CmisTable myTable, Object[] data, int selectedRow) {
    DbTableModel model = (DbTableModel) myTable.getModel();

    if (selectedRow >= 0) {
      model.insertRow(selectedRow + 1, data);
    } else {
      model.addRow(data);
    }
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void partNumberRB_actionPerformed(ActionEvent e) {
    allPartsUsedRB.setSelected(false);
    allPartsApprovedRB.setSelected(false);
    partNumberRB.setSelected(true);
    partNoT.setEnabled(true);
    partC.setEnabled(true);
    begMonC.setEnabled(false);
    begYearC.setEnabled(false);
    endMonthC.setEnabled(false);
    endYearC.setEnabled(false);
    partsInventoryRB.setSelected(false);
    inventoryMonC.setEnabled(false);
    inventoryDayC.setEnabled(false);
    inventoryYearC.setEnabled(false);
    //remove lbs from format drop down
    if (formatJC.getItemCount() == 4) {
      formatJC.removeItem("lbs");
    }

  } //end of method

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void allPartsUsedRB_actionPerformed(ActionEvent e) {
    allPartsUsedRB.setSelected(true);
    allPartsApprovedRB.setSelected(false);
    partNumberRB.setSelected(false);
    partNoT.setEnabled(false);
    partC.setEnabled(false);
    begMonC.setEnabled(true);
    begYearC.setEnabled(true);
    endMonthC.setEnabled(true);
    endYearC.setEnabled(true);
    partsInventoryRB.setSelected(false);
    inventoryMonC.setEnabled(false);
    inventoryDayC.setEnabled(false);
    inventoryYearC.setEnabled(false);
    //add lbs to format drop down if not already there
    if (formatJC.getItemCount() < 4) {
      formatJC.addItem("lbs");
    }
  } //end of method

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void allPartsApprovedRB_actionPerformed(ActionEvent e) {
    allPartsUsedRB.setSelected(false);
    allPartsApprovedRB.setSelected(true);
    partNumberRB.setSelected(false);
    //partNumB.setEnabled(false);
    partNoT.setEnabled(false);
    partC.setEnabled(false);
    begMonC.setEnabled(false);
    begYearC.setEnabled(false);
    endMonthC.setEnabled(false);
    endYearC.setEnabled(false);
    partsInventoryRB.setSelected(false);
    inventoryMonC.setEnabled(false);
    inventoryDayC.setEnabled(false);
    inventoryYearC.setEnabled(false);
    //remove lbs from format drop down
    if (formatJC.getItemCount() == 4) {
      formatJC.removeItem("lbs");
    }
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  void partsInventoryRB_actionPerformed(ActionEvent e) {
    partsInventoryRB.setSelected(true);
    inventoryMonC.setEnabled(true);
    inventoryDayC.setEnabled(true);
    inventoryYearC.setEnabled(true);
    allPartsUsedRB.setSelected(false);
    allPartsApprovedRB.setSelected(false);
    partNumberRB.setSelected(false);
    //partNumB.setEnabled(false);
    partNoT.setEnabled(false);
    partC.setEnabled(false);
    begMonC.setEnabled(false);
    begYearC.setEnabled(false);
    endMonthC.setEnabled(false);
    endYearC.setEnabled(false);
    //add lbs to format drop down if not already there
    if (formatJC.getItemCount() < 4) {
      formatJC.addItem("lbs");
    }
  }

  /**
   *  Description of the Method
   *
   *@exception  Exception  Description of Exception
   */
  private void jbInit() throws Exception {
    this.setMinimumSize(new Dimension(10, 515));
    this.setPreferredSize(new Dimension(700, 515));

    topP.setMaximumSize(new Dimension(32767, 1000));
    topP.setMinimumSize(new Dimension(600, 115));
    topP.setPreferredSize(new Dimension(600, 115));
    topP.setLayout(gridBagLayoutT);

    bottomP.setMaximumSize(new Dimension(32767, 1000));
    bottomP.setMinimumSize(new Dimension(200, 125));
    bottomP.setPreferredSize(new Dimension(200, 125));
    bottomP.setLayout(gridBagLayout2);

    bottomP1.setLayout(gridBagLayout3);
    bottomP1.setPreferredSize(new Dimension(200, 125));
    bottomP1.setMinimumSize(new Dimension(200, 125));
    bottomP1.setMaximumSize(new Dimension(32767, 1000));
    batchR.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        batchR_actionPerformed(e);
      }
    }
    );
    batchR.setText("Batch Generation");
    baseFieldP.setPreferredSize(new Dimension(200, 80));
    baseFieldP.setMinimumSize(new Dimension(200, 80));
    baseFieldP.setMaximumSize(new Dimension(200, 80));
    baseFieldP.setLayout(borderLayout3);
    interactiveR.setSelected(true);
    interactiveR.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        interactiveR_actionPerformed(e);
      }
    }
    );
    interactiveR.setText("Interactive Generation");
    reportFieldP.setLayout(borderLayout4);
    reportFieldP.setMaximumSize(new Dimension(160, 80));
    reportFieldP.setMinimumSize(new Dimension(160, 80));
    reportFieldP.setPreferredSize(new Dimension(160, 80));
    backB1.setIcon(ResourceLoader.getImageIcon("images/button/prev1.gif"));
    backB1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        backB1_actionPerformed(e);
      }
    }
    );
    backB1.setMaximumSize(new Dimension(35, 35));
    backB1.setMinimumSize(new Dimension(35, 35));
    backB1.setPreferredSize(new Dimension(35, 35));
    forwardB1.setMaximumSize(new Dimension(35, 35));
    forwardB1.setMinimumSize(new Dimension(35, 35));
    forwardB1.setPreferredSize(new Dimension(35, 35));
    forwardB1.setIcon(ResourceLoader.getImageIcon("images/button/next1.gif"));
    forwardB1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        forwardB1_actionPerformed(e);
      }
    }
    );
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

    reportListP.setLayout(borderLayout2);
    backB.setMaximumSize(new Dimension(35, 35));
    backB.setMinimumSize(new Dimension(35, 35));
    backB.setPreferredSize(new Dimension(35, 35));
    chemicalListP.setLayout(borderLayout1);
    chemicalListP.setMaximumSize(new Dimension(180, 80));
    chemicalListP.setMinimumSize(new Dimension(180, 80));
    chemicalListP.setPreferredSize(new Dimension(180, 80));
    reportListP.setMaximumSize(new Dimension(160, 80));
    reportListP.setMinimumSize(new Dimension(160, 80));
    reportListP.setPreferredSize(new Dimension(160, 80));

    forwardB.addActionListener(
        new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        forwardB_actionPerformed(e);
      }
    }
    );
    this.setLayout(gridBagLayout4);

    jLabel1.setPreferredSize(new Dimension(144, 5));
    partNumberRB.addActionListener(new java.awt.event.ActionListener() {

      /**
       *  Description of the Method
       *
       *@param  e  Description of Parameter
       */
      public void actionPerformed(ActionEvent e) {
        partNumberRB_actionPerformed(e);
      }
    }
    );
    partNumberRB.setText("Part Number");

    end.setHorizontalAlignment(SwingConstants.RIGHT);
    allPartsApprovedRB.setText("All Parts Approved");
    allPartsApprovedRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        allPartsApprovedRB_actionPerformed(e);
      }
    }
    );
    partNumberRB.setSelected(true);
    begin.setText("Begin:");
    begMonC.setMaximumSize(new Dimension(90, 20));
    begMonC.setMinimumSize(new Dimension(90, 20));
    begMonC.setPreferredSize(new Dimension(90, 20));
    begMonC.setOpaque(true);
    begYearC.setMinimumSize(new Dimension(65, 20));
    begYearC.setPreferredSize(new Dimension(65, 20));
    begYearC.setMaximumSize(new Dimension(65, 20));
    begYearC.setOpaque(true);

    end.setText("End:");
    endMonthC.setMaximumSize(new Dimension(90, 20));
    endMonthC.setMinimumSize(new Dimension(90, 20));
    endMonthC.setPreferredSize(new Dimension(90, 20));
    endMonthC.setOpaque(true);
    endYearC.setMaximumSize(new Dimension(65, 20));
    endYearC.setMinimumSize(new Dimension(65, 20));
    endYearC.setPreferredSize(new Dimension(65, 20));
    endYearC.setOpaque(true);

    allPartsUsedRB.addActionListener(new java.awt.event.ActionListener() {
      /**
       *  Description of the Method
       *
       *@param  e  Description of Parameter
       */
      public void actionPerformed(ActionEvent e) {
        allPartsUsedRB_actionPerformed(e);
      }
    }
    );

    partsInventoryRB.addActionListener(new java.awt.event.ActionListener() {
      /**
       *  Description of the Method
       *
       *@param  e  Description of Parameter
       */
      public void actionPerformed(ActionEvent e) {
        partsInventoryRB_actionPerformed(e);
      }
    }
    );
    inventoryMonC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        inventoryMonC_actionPerformed(e);
      }
    });
    inventoryYearC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        inventoryYearC_actionPerformed(e);
      }
    });
    inventoryMonC.setMaximumSize(new Dimension(90, 20));
    inventoryMonC.setMinimumSize(new Dimension(90, 20));
    inventoryMonC.setPreferredSize(new Dimension(90, 20));
    inventoryMonC.setOpaque(true);
    inventoryDayC.setMaximumSize(new Dimension(45, 20));
    inventoryDayC.setMinimumSize(new Dimension(45, 20));
    inventoryDayC.setPreferredSize(new Dimension(45, 20));
    inventoryDayC.setOpaque(true);
    inventoryYearC.setMaximumSize(new Dimension(65, 20));
    inventoryYearC.setMinimumSize(new Dimension(65, 20));
    inventoryYearC.setPreferredSize(new Dimension(65, 20));
    inventoryYearC.setOpaque(true);
    inventoryL.setText("On:");

    facility.setText("Facility :");
    facilityC.setMaximumSize(new Dimension(130, 20));
    facilityC.setMinimumSize(new Dimension(130, 20));
    facilityC.setPreferredSize(new Dimension(130, 20));
    facilityC.setOpaque(true);
    workarea.setText("Work Area :");
    workAreaC.setMaximumSize(new Dimension(150, 20));
    workAreaC.setMinimumSize(new Dimension(150, 20));
    workAreaC.setPreferredSize(new Dimension(150, 20));
    workAreaC.setOpaque(true);
    allPartsUsedRB.setText("All Parts Used");
    allPartsUsedRB.setSelected(false);
    partsInventoryRB.setText("Parts in Inventory");
    partsInventoryRB.setSelected(false);

    partNoT.setMaximumSize(new Dimension(125, 20));
    partNoT.setMinimumSize(new Dimension(125, 20));
    partNoT.setPreferredSize(new Dimension(125, 20));
    partC.setMaximumSize(new Dimension(75, 20));
    partC.setMinimumSize(new Dimension(75, 20));
    partC.setPreferredSize(new Dimension(75, 20));
    partC.setOpaque(true);

    FormatL.setText("List Format:");
    formatJC.setMaximumSize(new Dimension(90, 20));
    formatJC.setMinimumSize(new Dimension(90, 20));
    formatJC.setPreferredSize(new Dimension(90, 20));
    formatJC.setOpaque(true);

    batchNameL.setText("Rpt Name :");
    batchNameL.setEnabled(false);
    batchNameT.setMaximumSize(new Dimension(100, 20));
    batchNameT.setMinimumSize(new Dimension(100, 20));
    batchNameT.setPreferredSize(new Dimension(100, 20));
    batchNameT.setText("");
    batchNameT.setEnabled(false);

    reportingEntityL.setText("Rpt Entity:");
    reportingEntityC.setMaximumSize(new Dimension(120, 20));
    reportingEntityC.setMinimumSize(new Dimension(120, 20));
    reportingEntityC.setPreferredSize(new Dimension(120, 20));
    reportingEntityC.setOpaque(true);

    this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    topP.add(facility, new GridBagConstraints2(0, 0, 4, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(facilityC, new GridBagConstraints2(0, 0, 4, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 47, 0, 0), 0, 0));
    topP.add(workarea, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    topP.add(workAreaC, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    topP.add(partNumberRB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
    topP.add(partC, new GridBagConstraints2(1, 1, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(7, 5, 0, 5), 0, 0));
    topP.add(partNoT, new GridBagConstraints2(1, 1, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(7, 85, 0, 5), 0, 0));
    topP.add(allPartsUsedRB, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
    topP.add(begin, new GridBagConstraints2(1, 2, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 3, 3, 3), 0, 0));
    topP.add(begMonC, new GridBagConstraints2(1, 2, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 43, 3, 3), 0, 0));
    topP.add(begYearC, new GridBagConstraints2(1, 2, 3, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 143, 3, 5), 0, 0));
    topP.add(end, new GridBagConstraints2(4, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 3, 3, 3), 0, 0));
    topP.add(endMonthC, new GridBagConstraints2(5, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 3, 3, 3), 0, 0));
    topP.add(endYearC, new GridBagConstraints2(5, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 100, 3, 5), 0, 0));
    topP.add(allPartsApprovedRB, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
    topP.add(FormatL, new GridBagConstraints2(5, 3, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 0), 0, 0));
    topP.add(formatJC, new GridBagConstraints2(5, 3, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    topP.add(partsInventoryRB, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
    topP.add(inventoryL, new GridBagConstraints2(1, 4, 4, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 3, 0), 0, 0));
    topP.add(inventoryMonC, new GridBagConstraints2(1, 4, 4, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 43, 3, 0), 0, 0));
    topP.add(inventoryDayC, new GridBagConstraints2(1, 4, 4, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 138, 3, 0), 0, 0));
    topP.add(inventoryYearC, new GridBagConstraints2(1, 4, 4, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 187, 3, 0), 0, 0));

    this.add(bottomP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    bottomP.add(forwardB, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 10, 40, 10), 0, 0));
    bottomP.add(reportListP, new GridBagConstraints2(2, 0, 1, 1, 1.0, 1.0
                                              ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    reportListP.add(reportListJSP, BorderLayout.CENTER);
    bottomP.add(backB, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(55, 10, 10, 10), 0, 0));
    bottomP.add(chemicalListP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    chemicalListP.add(chemicalListJSP, BorderLayout.CENTER);
    bottomP.add(jLabel1, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
                                               ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(bottomP1, new GridBagConstraints2(0, 2, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    bottomP1.add(forwardB1, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 10, 40, 10), 0, 0));
    bottomP1.add(reportFieldP, new GridBagConstraints2(2, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    reportFieldP.add(reportFieldJSP, BorderLayout.CENTER);
    bottomP1.add(backB1, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(45, 10, 0, 10), 0, 0));
    bottomP1.add(interactiveR, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    bottomP1.add(batchR, new GridBagConstraints2(0, 1, 2, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 150, 3, 3), 0, 0));
    bottomP1.add(baseFieldP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    baseFieldP.add(baseFieldJSP, BorderLayout.CENTER);
    bottomP1.add(batchNameL, new GridBagConstraints2(2, 1, GridBagConstraints.REMAINDER, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
    bottomP1.add(batchNameT, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 88, 0, 3), 0, 0));



    bottomP.validate();
    this.validate();

    DbTableModel modelL = new DbTableModel(colHeadL);
    chemLeftTable = new CmisTable(modelL);
    chemicalListJSP.getViewport().setView(chemLeftTable);
    DbTableModel modelR = new DbTableModel(colHeadR);
    chemRightTable = new CmisTable(modelR);
    reportListJSP.getViewport().setView(chemRightTable);

    DbTableModel modelL1 = new DbTableModel(colHeadL1);
    leftTable = new CmisTable(modelL1);
    baseFieldJSP.getViewport().setView(leftTable);
    DbTableModel modelR1 = new DbTableModel(colHeadR1);
    rightTable = new CmisTable(modelR1);
    reportFieldJSP.getViewport().setView(rightTable);

    ClientHelpObjs.makeDefaultColors(this);

    bottomP1.validate();
    bottomP.validate();
    topP.validate();
  }

  void buildTable() {

    chemicalListJSP.getViewport().remove(chemLeftTable);
    reportListJSP.getViewport().remove(chemRightTable);

    baseFieldJSP.getViewport().remove(leftTable);
    reportFieldJSP.getViewport().remove(rightTable);

    DbTableModel modelR = new DbTableModel(colHeadR);
    DbTableModel modelR1 = new DbTableModel(colHeadR1);
    //get model from database

    baseField = (Vector) templateInfoH.get("BASE_FIELDS");
    chemicalList = (Vector) templateInfoH.get("CHEMICAL_LIST");

    DbTableModel modelL = new DbTableModel(colHeadL);
    DbTableModel modelL1 = new DbTableModel(colHeadL1);

    //make a copy of base_fields
    Object[] oaL = new Object[colHeadL.length];
    for (int i = 0; i < chemicalList.size(); i++) {
      oaL[0] = chemicalList.elementAt(i);
      modelL.addRow(oaL);
    }

    Object[] oaL1 = new Object[colHeadL1.length];
    for (int i = 0; i < baseField.size(); i++) {
      oaL1[0] = baseField.elementAt(i);
      modelL1.addRow(oaL1);
    }

    rightTable = new CmisTable(modelR1);
    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable) templateInfoH.get("TABLE_STYLE");
    Color color = (Color) chargeTableStyle.get("COLOR");
    Integer t = (Integer) chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer) chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer) chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer) chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer) chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(), l.intValue(), b.intValue(), r.intValue()), a.intValue());
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

    leftTable = new CmisTable(modelL1);
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

    chemRightTable = new CmisTable(modelR);
    //Nawaz 09/19/01
    cm = chemRightTable.getColumnModel();
    for (int i = 0; i < chemRightTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    chemRightTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    enum1 = chemRightTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    chemRightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    chemLeftTable = new CmisTable(modelL);
    //Nawaz 09/19/01
    cm = chemLeftTable.getColumnModel();
    for (int i = 0; i < chemLeftTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    chemLeftTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    enum1 = chemLeftTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    chemLeftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    chemicalListJSP.getViewport().setView(chemLeftTable);
    reportListJSP.getViewport().setView(chemRightTable);

    baseFieldJSP.getViewport().setView(leftTable);
    reportFieldJSP.getViewport().setView(rightTable);
  }

  void buildComboBoxes() {
    Hashtable dataH = (Hashtable) templateInfoH.get("FACILITIES_INFO");
    Vector facNameV = (Vector) dataH.get("FACILITY_NAME");
    facilityC.removeAllItems();
    facilityC = ClientHelpObjs.loadChoiceFromVector(facilityC, facNameV);
    facilityC.setSelectedItem(getFacilityDesc(cmis.getPrefFac()));
    loadReportingEntity();
    if (!reportingEntityC.isVisible()) {
      loadWorkArea();
    }
    partC = ClientHelpObjs.loadChoiceFromVector(partC, (Vector) templateInfoH.get("SEARCH_OPERATOR"));
  } //end of method

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

  String getReportingEntityDesc(String reportingEntityId) {
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITIES_INFO");
    Hashtable h = (Hashtable) facApp.get(getFacilityID());
    Vector reportEntityDescV = (Vector) h.get("REPORTING_ENTITY_DESC");
    Vector reportEntityIdV = (Vector) h.get("REPORTING_ENTITY_ID");
    int index = reportEntityIdV.indexOf(reportingEntityId);
    if (index < 0) {
      index = 0;
    }
    return (reportEntityDescV.elementAt(index).toString());
  } //end of method


  String getReportingEntityId(String reportingEntityDesc) {
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITIES_INFO");
    Hashtable h = (Hashtable) facApp.get(getFacilityID());
    Vector reportEntityDescV = (Vector) h.get("REPORTING_ENTITY_DESC");
    Vector reportEntityIdV = (Vector) h.get("REPORTING_ENTITY_ID");
    int index = reportEntityDescV.indexOf(reportingEntityDesc);
    if (index < 0) {
      index = 0;
    }
    return (reportEntityIdV.elementAt(index).toString());
  } //end of method

  void loadReportingEntity() {
    Hashtable facApp = (Hashtable) templateInfoH.get("FACILITIES_INFO");
    Hashtable h = (Hashtable) facApp.get(getFacilityID());
    String reportingEntityLabel = (String) h.get("REPORTING_ENTITY_LABEL");
    Vector reportEntityV = (Vector) h.get("REPORTING_ENTITY_DESC");
    if (!BothHelpObjs.isBlankString(reportingEntityLabel)) {
      reportingEntityC.removeItemListener(reportingEntityItemListener);
      reportingEntityC.removeAllItems();
      reportingEntityL.setText(reportingEntityLabel + ":");
      reportingEntityC = ClientHelpObjs.loadChoiceFromVector(reportingEntityC, reportEntityV);
      topP.add(reportingEntityL, new GridBagConstraints2(0, 0, 4, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 180, 0, 0), 0, 0));
      topP.add(reportingEntityC, new GridBagConstraints2(0, 0, 4, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 237, 0, 0), 0, 0));
      reportingEntityL.setVisible(true);
      reportingEntityC.setVisible(true);
      reportingEntityC.setSelectedIndex(0);
      reportingEntityC.addItemListener(reportingEntityItemListener);
      reportingEntityC.setForeground(facilityC.getForeground());
      reportingEntityC.setFont(facilityC.getFont());
      facilityC.setMaximumSize(new Dimension(130, 20));
      facilityC.setMinimumSize(new Dimension(130, 20));
      facilityC.setPreferredSize(new Dimension(130, 20));
      loadWorkArea();
    } else {
      facilityC.setMaximumSize(new Dimension(200, 20));
      facilityC.setMinimumSize(new Dimension(200, 20));
      facilityC.setPreferredSize(new Dimension(200, 20));
      reportingEntityL.setVisible(false);
      reportingEntityC.setVisible(false);
      topP.remove(reportingEntityL);
      topP.remove(reportingEntityC);
    }
  } //end of method


  void forwardB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (chemLeftTable.getSelectedRow() < 0) {
      GenericDlg g = new GenericDlg(cmis.getMain(), "Select row(s)", true);
      g.setMsg(" Please select row(s) that you want to move.");
      g.setVisible(true);
    } else {
      int[] selectedRows = new int[MAXSELECTEDROW];
      selectedRows = chemLeftTable.getSelectedRows();
      Vector myDataV = new Vector();

      //save data
      for (int i = 0; i < selectedRows.length; i++) {
        //System.out.println("== selected rows: "+selectedRows[i]);
        String tmpData = chemLeftTable.getModel().getValueAt(selectedRows[i], 0).toString();
        myDataV.addElement(tmpData);
      }
      //System.out.println("==forwardB_actionPerformed data: "+myDataV);
      //removing data from table
      for (int j = 0; j < selectedRows.length; j++) {
        //System.out.println("== deleting selected rows: "+selectedRows[j]);
        removeRowFromTable(chemLeftTable, selectedRows[j] - j);
      }
      //move data to table
      for (int k = 0; k < myDataV.size(); k++) {
        String tmpData2 = (String) myDataV.elementAt(k);
        Object[] oa = new Object[colHeadR.length];
        oa[0] = tmpData2;
        addRowToTable(chemRightTable, oa, chemRightTable.getSelectedRow());
      }
    }
  }

  void backB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (chemRightTable.getSelectedRow() < 0) {
      GenericDlg g = new GenericDlg(cmis.getMain(), "Select a row", true);
      g.setMsg(" Please select row(s) that you want to move.");
      g.setVisible(true);
    } else {
      int[] selectedRows = new int[MAXSELECTEDROW];
      selectedRows = chemRightTable.getSelectedRows();
      //getting the data from table
      Vector myDataV = new Vector();
      for (int i = 0; i < selectedRows.length; i++) {
        String tmpData = chemRightTable.getModel().getValueAt(selectedRows[i], 0).toString();
        myDataV.addElement(tmpData);
      }
      //remove data from table
      for (int k = 0; k < selectedRows.length; k++) {
        removeRowFromTable(chemRightTable, selectedRows[k] - k);
      }
      //now move data to
      for (int j = 0; j < myDataV.size(); j++) {
        String tmpData2 = (String) myDataV.elementAt(j);
        Object[] oa = new Object[colHeadR.length];
        oa[0] = tmpData2;
        addRowToTable(chemLeftTable, oa, chemLeftTable.getSelectedRow());
      }
    }
  }

  void forwardB1_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);

    if (leftTable.getSelectedRow() < 0) {
      GenericDlg g = new GenericDlg(cmis.getMain(), "Select row(s)", true);
      g.setMsg(" Please select row(s) that you want to move.");
      g.setVisible(true);
    } else {
      int[] selectedRows = new int[MAXSELECTEDROW];
      selectedRows = leftTable.getSelectedRows();
      Vector myDataV = new Vector();

      //save data
      for (int i = 0; i < selectedRows.length; i++) {
        //System.out.println("== selected rows: "+selectedRows[i]);
        String tmpData = leftTable.getModel().getValueAt(selectedRows[i], 0).toString();
        /*
                         if ((tmpData.equalsIgnoreCase("Quantity") || tmpData.equalsIgnoreCase("Month"))
                && !(allPartsUsedRB.isSelected()))
                         {
            GenericDlg g = new GenericDlg(cmis.getMain(),"Invalid Selection",true);
            g.setMsg(" Please select 'All Parts Used' before selecting Quantity and/or Month.");
            g.setVisible(true);
            return;
                         }
                         if (tmpData.equalsIgnoreCase("Work Area") && waC.getSelectedWorkAreaID().equalsIgnoreCase("All") && allPartsApprovedRB.isSelected()) {
          GenericDlg g = new GenericDlg(cmis.getMain(),"Invalid Selection",true);
          g.setMsg(" You must select a work area first.");
          g.setVisible(true);
          return;
                         }
         */
        myDataV.addElement(tmpData);

      }
      //System.out.println("== forwardB1_actionPerformed data: "+myDataV);
      //removing data from table
      for (int j = 0; j < selectedRows.length; j++) {
        removeRowFromTable(leftTable, selectedRows[j] - j);
      }
      //move data to table
      for (int k = 0; k < myDataV.size(); k++) {
        String tmpData2 = (String) myDataV.elementAt(k);
        Object[] oa = new Object[colHeadR.length];
        oa[0] = tmpData2;
        addRowToTable(rightTable, oa, rightTable.getSelectedRow());
      }
    }
  }

  void backB1_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (rightTable.getSelectedRow() < 0) {
      GenericDlg g = new GenericDlg(cmis.getMain(), "Select a row", true);
      g.setMsg(" Please select row(s) that you want to move.");
      g.setVisible(true);
    } else {
      int[] selectedRows = new int[MAXSELECTEDROW];
      selectedRows = rightTable.getSelectedRows();
      //getting the data from table
      Vector myDataV = new Vector();
      for (int i = 0; i < selectedRows.length; i++) {
        String tmpData = rightTable.getModel().getValueAt(selectedRows[i], 0).toString();
        myDataV.addElement(tmpData);
      }
      //remove data from table
      for (int k = 0; k < selectedRows.length; k++) {
        removeRowFromTable(rightTable, selectedRows[k] - k);
      }
      //now move data to
      for (int j = 0; j < myDataV.size(); j++) {
        String tmpData2 = (String) myDataV.elementAt(j);
        Object[] oa = new Object[colHeadR.length];
        oa[0] = tmpData2;
        addRowToTable(leftTable, oa, leftTable.getSelectedRow());
      }
    }
  }

  void clearPartNo() {
    String fID = getFacilityID();
    if (!lastFacID.equals(fID)) {
      partNoT.setText("");
    }
    lastFacID = fID;
  }

} //end of class

class Report_MaterialMatrixReportingEntity_actionAdapter implements java.awt.event.ItemListener {
  MaterialMatrix adaptee;

  Report_MaterialMatrixReportingEntity_actionAdapter(MaterialMatrix adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent evt) {
    adaptee.loadWorkArea();
  }
}


class MaterialMatrixLoadThread extends Thread {
  MaterialMatrix ahur;
  String action;
  public MaterialMatrixLoadThread(MaterialMatrix ahur, String action) {
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
