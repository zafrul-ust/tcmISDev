package radian.tcmis.server7.share.servlets;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.MaterialMatrixDBObj;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.helpers.HelpObjs;
import java.io.PrintWriter;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 06-25-04 Adding log statements to trace a memory usage issue
 */
public class MaterialMatrix extends TcmisServletObj {
  //Client Version
  private String function = null;
  private Hashtable mySendObj = null;
  private Hashtable inData = null;
  private Vector reportfields = new Vector();
  private Vector reportData = new Vector();
  private int reportsize = 0;
  private int NoRecords = 0;
  private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

  public MaterialMatrix(ServerResourceBundle b, TcmISDBModel d) {
    super();
    bundle = b;
    db = d;
  }

  protected void resetAllVars() {
    function = null;
    inData = null;
  }

  protected void print(TcmisOutputStreamServer out) {
    try {
      out.sendObject( (Hashtable) mySendObj);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void getResult() {
    mySendObj = new Hashtable();
    // using myRecvObj you get the function the way you want
    inData = (Hashtable) myRecvObj;
    //System.out.println("in data: " + inData);
    String function = (String) inData.get("ACTION");
    if (function.equalsIgnoreCase("GET_MATRIX_TEMPLATE_INFO")) {
      getMatrixTemplateInfo();
    } else if (function.equalsIgnoreCase("GET_TEMPLATES")) {
      getMatrixTemplate();
    } else if (function.equalsIgnoreCase("UPDATE_TEMPLATE")) {
      updateMatrixTemplate();
    } else if (function.equalsIgnoreCase("LOAD_MATRIX_TEMPLATE_INFO")) {
      loadMatrixTemplate();
    } else if (function.equalsIgnoreCase("CREATE_REPORT")) {
      createReporttest();
      //createReport();
    } else if (function.equalsIgnoreCase("GET_CATALOG")) {
      getCatalog();
    } else if (function.equalsIgnoreCase("SEARCH_PART_NO")) {
      searchPartNo();
    }
    //System.out.println("outData:"+mySendObj);
  }

  protected void searchPartNo() {
    try {
      String facilityID = (String) inData.get("FACILITY_ID");
      String searchT = (String) inData.get("SEARCH_TEXT");
      MaterialMatrixDBObj mmo = new MaterialMatrixDBObj(db);
      mySendObj.put("SEARCH_DATA", mmo.searchPartNo(facilityID, searchT));
      mySendObj.put("SUCCEED", new Boolean(true));
      mySendObj.put("TABLE_STYLE", BothHelpObjs.getTableStyle());
    } catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCEED", new Boolean(false));
    }
  }

  protected void getCatalog() {
    try {
      MaterialMatrixDBObj ahr = new MaterialMatrixDBObj(db);
      Hashtable h = ahr.getCatalog();
      mySendObj.put("LIST_CATALOGS", h);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void createReporttest() {
    try {
      String client = db.getClient().toString().trim();
      String userID = (String) inData.get("USER_ID").toString().trim();
      MaterialMatrixDBObj mmDBObj = new MaterialMatrixDBObj(db);
      String sessionID = mmDBObj.createReportTemplate(userID, inData);
      mySendObj.put("SUCCEED", new Boolean(true));
      mySendObj.put("MSG", "Your report was sent to the server for processing.\nIt will automatically be sent to you upon completion.\nYou may now resume other activities within tcmIS.");
      String url = "/tcmIS/servlet/radian.web.servlets.reports.WebMaterialMatrix?sessionID=" + sessionID + "&client=" + client + "&userID=" + userID;
      mySendObj.put("URL", url);
    } catch (Exception e) {
      mySendObj.put("SUCCEED", new Boolean(false));
      mySendObj.put("MSG", "An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
    }
  } //end of method

  public Hashtable createReportNew(String sessionID, String userID) {
    Hashtable OutData = new Hashtable();
    try {
      MaterialMatrixDBObj mmDBObj = new MaterialMatrixDBObj(db);
      Hashtable tmpReportData = new Hashtable(5);
      Hashtable innerH = mmDBObj.loadReportTemplate(sessionID);
      innerH.put("METHOD", "Active");
      tmpReportData.put("SELECTED_DATA", innerH);
      tmpReportData.put("USER_ID", userID);
      OutData = createReport(tmpReportData);
      if ( ( (Boolean) OutData.get("SUCCEED")).booleanValue()) {
        HelpObjs.deleteDataFromTable(db, "report_hash_table", sessionID);
      }
    } catch (Exception e) {
      e.printStackTrace();
      OutData.put("SUCCEED", new Boolean(false));
      OutData.put("MSG", "An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
    }
    return OutData;
  } //end of method

  public Hashtable createReport(Hashtable RepData) throws Exception {
    Hashtable OutData = new Hashtable();
    Hashtable dbResult = new Hashtable();
    String url = "";
    Hashtable SelectedData = (Hashtable) RepData.get("SELECTED_DATA");
    String userId = (String) RepData.get("USER_ID");
    String method = SelectedData.get("METHOD").toString();

    String serverPath = "";
    String writefilepath = radian.web.tcmisResourceLoader.getsavelreportpath();
    String tempwritefilepath = radian.web.tcmisResourceLoader.getsaveltempreportpath();
    if (method != null && method.equalsIgnoreCase("Batch")) {
      serverPath = writefilepath;
    } else {
      serverPath = tempwritefilepath;
    }

    File dir = new File(serverPath);
    File file = File.createTempFile("AdhocMaterialMatrix" + userId + "", ".xls", dir);
    PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));
    try {
      Hashtable retH = new Hashtable();
      reportfields = (Vector) SelectedData.get("REPORT_FIELDS");
      //adding chemical list to report fields
      Vector reportfields1 = (Vector) SelectedData.get("REPORT_FIELDS1");
      for (int i = 0; i < reportfields.size(); i++) {
        reportfields1.addElement(reportfields.elementAt(i));
      }
      reportfields = reportfields1;
      reoprtlog.info("createReport   Start " + file.getName() + " ");
      modifyTemplate(SelectedData, "Material Matrix Report", reportfields, pw);
      MaterialMatrixDBObj ahr = new MaterialMatrixDBObj(db);
      dbResult = ahr.getReportData(db, SelectedData, userId, pw);
      pw.println("</TABLE></FONT>");
      pw.close();
      if (! (dbResult.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
        String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
        wwwHome = wwwHome.substring(0, (wwwHome.length() - 1));
        url = wwwHome;

        String urlpath = radian.web.tcmisResourceLoader.getreporturl();
        String tempurlpath = radian.web.tcmisResourceLoader.gettempreporturl();
        if (method.equalsIgnoreCase("Batch")) {
          url += urlpath + file.getName();
        } else {
          url += tempurlpath + file.getName();
        }
      } else {
        NoRecords = 1;
      }

      reoprtlog.info("createReport    Done  " + file.getName() + "");
      if (url.length() > 0) {
        OutData.put("SUCCEED", new Boolean(true));
      } else {
        OutData.put("SUCCEED", new Boolean(false));
        if ( (dbResult.get("RECORDS").toString().trim().equalsIgnoreCase("Error"))) {
          OutData.put("MSG",
                      " Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).");
        } else if ( (dbResult.get("RECORDS").toString().trim().equalsIgnoreCase("0"))) {
          OutData.put("MSG",
                      " No records were found matching your search criteria.\n Please choose different criteria and try again.");
        }
      }
      OutData.put("URL", url);
    } catch (Exception e) {
      e.printStackTrace();
      pw.close();
      OutData.put("SUCCEED", new Boolean(false));
      OutData.put("MSG", "An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
    }
    return OutData;
  }

  protected void loadMatrixTemplate() {
    try {
      MaterialMatrixDBObj ahr = new MaterialMatrixDBObj(db);
      Hashtable h = ahr.loadTemplateInfo(inData);
      mySendObj.put("TEMPLATE_INFO", h);
      //Nawaz 19/09/01 new changes to gui tables
      mySendObj.put("TABLE_STYLE", BothHelpObjs.getTableStyle());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void updateMatrixTemplate() {
    try {
      MaterialMatrixDBObj ahr = new MaterialMatrixDBObj(db);
      Hashtable h = ahr.saveTemplate(inData);
      Boolean b = (Boolean) h.get("SUCCESS");
      String msg = (String) h.get("MSG");
      Boolean b1 = (Boolean) h.get("OVERRIDE");
      mySendObj.put("SUCCESS", b);
      mySendObj.put("MSG", msg);
      mySendObj.put("OVERRIDE", b1);
    } catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCESS", new Boolean(false));
      mySendObj.put("OVERRIDE", new Boolean(false));
      mySendObj.put("MSG", "An error occurred while saving the template.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
    }
  }

  protected void getMatrixTemplate() {
    try {
      MaterialMatrixDBObj ahr = new MaterialMatrixDBObj(db);
      Vector v = ahr.getTemplate(inData);
      mySendObj.put("LIST_TEMPLATES", v);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void getMatrixTemplateInfo() {
    try {
      MaterialMatrixDBObj ahr = new MaterialMatrixDBObj(db);
      Hashtable h = ahr.getMatrixTemplateInfo(inData);
      mySendObj.put("MATRIX_TEMPLATE_INFO", h);
      //Nawaz 19/09/01 new changes to gui tables
      mySendObj.put("TABLE_STYLE", BothHelpObjs.getTableStyle());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void modifyTemplate(Hashtable SelectedData, String nameofrpt, Vector reportfieldsv, PrintWriter pw) throws Exception {
    String endYear = SelectedData.get("END_YEARN").toString();
    String endMonth = SelectedData.get("END_MONTH").toString();
    String beginYear = SelectedData.get("BEGIN_YEARN").toString();
    String beginMonth = SelectedData.get("BEGIN_MONTH").toString();
    String reportingEntityDesc = SelectedData.get("REPORTING_ENTITY_DESC").toString();

    String type = " ";
    String format = " ";

    if (beginMonth.equalsIgnoreCase("0")) {
      beginMonth = "JAN";
    } else if (beginMonth.equalsIgnoreCase("1")) {
      beginMonth = "FEB";
    } else if (beginMonth.equalsIgnoreCase("2")) {
      beginMonth = "MAR";
    } else if (beginMonth.equalsIgnoreCase("3")) {
      beginMonth = "APR";
    } else if (beginMonth.equalsIgnoreCase("4")) {
      beginMonth = "MAY";
    } else if (beginMonth.equalsIgnoreCase("5")) {
      beginMonth = "JUN";
    } else if (beginMonth.equalsIgnoreCase("6")) {
      beginMonth = "JUL";
    } else if (beginMonth.equalsIgnoreCase("7")) {
      beginMonth = "AUG";
    } else if (beginMonth.equalsIgnoreCase("8")) {
      beginMonth = "SEP";
    } else if (beginMonth.equalsIgnoreCase("9")) {
      beginMonth = "OCT";
    } else if (beginMonth.equalsIgnoreCase("10")) {
      beginMonth = "NOV";
    } else if (beginMonth.equalsIgnoreCase("11")) {
      beginMonth = "DEC";
    }

    if (endMonth.equalsIgnoreCase("0")) {
      endMonth = "JAN";
    } else if (endMonth.equalsIgnoreCase("1")) {
      endMonth = "FEB";
    } else if (endMonth.equalsIgnoreCase("2")) {
      endMonth = "MAR";
    } else if (endMonth.equalsIgnoreCase("3")) {
      endMonth = "APR";
    } else if (endMonth.equalsIgnoreCase("4")) {
      endMonth = "MAY";
    } else if (endMonth.equalsIgnoreCase("5")) {
      endMonth = "JUN";
    } else if (endMonth.equalsIgnoreCase("6")) {
      endMonth = "JUL";
    } else if (endMonth.equalsIgnoreCase("7")) {
      endMonth = "AUG";
    } else if (endMonth.equalsIgnoreCase("8")) {
      endMonth = "SEP";
    } else if (endMonth.equalsIgnoreCase("9")) {
      endMonth = "OCT";
    } else if (endMonth.equalsIgnoreCase("10")) {
      endMonth = "NOV";
    } else if (endMonth.equalsIgnoreCase("11")) {
      endMonth = "DEC";
    }

    Date d = new Date();
    Calendar cal = Calendar.getInstance();
    String cdate = new String( (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
    int am_pm = cal.get(Calendar.AM_PM);
    String am = "";
    if (am_pm == 0) {
      am = "AM";
    } else if (am_pm == 1) {
      am = "PM";
    }
    int min = cal.get(Calendar.MINUTE);
    int hours = cal.get(Calendar.HOUR);
    String time = "";

    if (hours < 10) {
      time += "0" + hours;
    } else {
      time += hours;
    }
    if (min < 10) {
      time += ":0" + min;
    } else {
      time += ":" + min;
    }
    time += " " + am;

    String begin = beginMonth + " " + beginYear;
    String End = endMonth + " " + endYear;

    int sizeofcol = reportfieldsv.size() - 2;

    pw.println("<FONT FACE=\"Arial\">");
    pw.println("<TABLE BORDER=\"0\">\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"" + reportfieldsv.size() + "\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >" + nameofrpt + "</FONT></B></TD>\n");
    pw.println("</TR>\n");
    pw.println("<TR><TD COLSPAN=\"" + reportfieldsv.size() + "\" ALIGN=\"RIGHT\"><FONT size=\"4\" >Date: " + cdate + "</FONT></TD></TR\n");
    pw.println("<TR><TD COLSPAN=\"" + reportfieldsv.size() + "\" ALIGN=\"RIGHT\" ><FONT size=\"4\" >Time: " + time + "</FONT></TD></TR>\n");
    pw.println("</TABLE>\n");

    pw.println("<TABLE BORDER=\"0\">\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"2\" ALIGN=\"CENTER\" ><B><FONT size=\"4\" >Selection Criterion</FONT></B></TD>\n");
    pw.println("</TR>\n");
    pw.println("</TABLE>\n");
    pw.println("<TABLE BORDER=\"1\" CELLPADDING=\"3\">\n");

    type = (String) SelectedData.get("TYPE");

    pw.println("<TR>\n");

    if (type.equalsIgnoreCase("Part Number")) {
      pw.println("<TD  ALIGN=\"RIGHT\" ><B>Part: </B></TD>\n");
      pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("PART_NO") == null ? "&nbsp;" : (String) SelectedData.get("PART_NO")) + "</I></TD>\n");
    } else if (type.equalsIgnoreCase("All Used")) {
      pw.println("<TD  ALIGN=\"LEFT\"><I>All Used</I></TD>\n");
    } else if (type.equalsIgnoreCase("All Approved")) {
      pw.println("<TD  ALIGN=\"LEFT\"><I>All Approved</I></TD>\n");
    }
    pw.println("</TR>\n");

    format = SelectedData.get("FORMAT").toString().trim();
    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\" ><B>Format: </B></TD>\n");

    if (format.equalsIgnoreCase("0")) {
      pw.println("<TD  ALIGN=\"LEFT\"><I>Yes</I></TD>\n");
    } else if (format.equalsIgnoreCase("1")) {
      pw.println("<TD  ALIGN=\"LEFT\"><I>xx.x</I></TD>\n");
    } else if (format.equalsIgnoreCase("2")) {
      pw.println("<TD  ALIGN=\"LEFT\"><I>xx.x +/- y</I></TD>\n");
    } else if (format.equalsIgnoreCase("3")) {
      pw.println("<TD  ALIGN=\"LEFT\"><I>lbs</I></TD>\n");
    }

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Facility: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("FACILITY_DESC") == null ? "&nbsp;" : (String) SelectedData.get("FACILITY_DESC")) + "</I></TD>\n");
    pw.println("</TR>\n");
    if (!BothHelpObjs.isBlankString(reportingEntityDesc)) {
      if (!"No Reporting Entity".equalsIgnoreCase(reportingEntityDesc)) {
        pw.println("<TR>\n");
        String reportingEntityLabel = SelectedData.get("REPORTING_ENTITY_LABEL").toString();
        if (BothHelpObjs.isBlankString(reportingEntityLabel)) {
          pw.println("<TD  ALIGN=\"RIGHT\"><B>Reporting Entity: </B></TD>\n");
        } else {
          pw.println("<TD  ALIGN=\"RIGHT\"><B>" + reportingEntityLabel + " </B></TD>\n");
        }
        pw.println("<TD  ALIGN=\"LEFT\"><I>" + reportingEntityDesc + "</I></TD>\n");
        pw.println("</TR>\n");
      }
    }
    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Work Area: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("WORK_AREA_DESC") == null ? "&nbsp;" : (String) SelectedData.get("WORK_AREA_DESC")) + "</I></TD>\n");
    pw.println("</TR>\n");

    if (type.equalsIgnoreCase("All Used")) {
      pw.println("<TR>\n");
      pw.println("<TD  ALIGN=\"RIGHT\"><B>Begin: </B></TD>\n");
      pw.println("<TD  ALIGN=\"LEFT\"><I>" + begin + "</I></TD>\n");
      pw.println("</TR>\n");

      pw.println("<TR>\n");
      pw.println("<TD  ALIGN=\"RIGHT\"><B>End: </B></TD>\n");
      pw.println("<TD  ALIGN=\"LEFT\"><I>" + End + "</I></TD>\n");
      pw.println("</TR>\n");
    } else if ("Parts In Inventory".equalsIgnoreCase(type)) {
      // inventory date
      String inventoryD = "";
      try {
        Integer inventoryMonth = new Integer(SelectedData.get("INVENTORY_MONTH").toString());
        Integer inventoryDay = new Integer(SelectedData.get("INVENTORY_DAY").toString());
        String inventoryYear = (String) SelectedData.get("INVENTORY_YEARN");
        inventoryMonth = new Integer(inventoryMonth.intValue() + 1);
        String sm = new String(inventoryMonth.toString());
        if (sm.length() < 2) {
          sm = "0" + sm;
        }
        inventoryDay = new Integer(inventoryDay.intValue() + 1);
        String sd = new String(inventoryDay.toString());
        if (sd.length() < 2) {
          sd = "0" + sd;
        }
        inventoryD = sm + "-" + sd + "-" + inventoryYear;
      } catch (Exception e) {
        e.printStackTrace();
        inventoryD = "";
      }
      pw.println("<TR>\n");
      pw.println("<TD  ALIGN=\"RIGHT\"><B>Parts in Inventory On: </B></TD>\n");
      pw.println("<TD  ALIGN=\"LEFT\"><I>" + inventoryD + "</I></TD>\n");
      pw.println("</TR>\n");
    }

    pw.println("</TABLE>\n");

    pw.println("<TABLE BORDER=\"0\">\n");
    pw.println("<TR><TD>&nbsp;</TD></TR\n");
    pw.println("<TR><TD>&nbsp;</TD></TR\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"" + reportfields.size() + "\" ALIGN=\"CENTER\" ><B><FONT size=\"4\" >Results</FONT></B></TD>\n");
    pw.println("</TR>\n");

    pw.println("</TABLE>\n");
    pw.println("<TABLE BORDER=\"1\">\n");

    try {
      pw.println("<TR>\n");
      for (int i = 0; i < reportfieldsv.size(); i++) {
        String lable0 = reportfieldsv.elementAt(i).toString();
        pw.println("<TD  ><B><FONT size=\"4\" >" + lable0 + "</FONT></B></TD>\n");
      }
      pw.println("</TR>\n");
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
  }
}
