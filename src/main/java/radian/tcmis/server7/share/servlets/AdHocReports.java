package radian.tcmis.server7.share.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.AdHocReportsDBObj;
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
public class AdHocReports extends TcmisServletObj {
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;
  Vector reportfields = new Vector();
  Vector reportData = new Vector();
  private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
  int reportsize = 0;
  int NoRecords = 0;

  public AdHocReports(ServerResourceBundle b, TcmISDBModel d) {
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
    String function = (String) inData.get("ACTION");
    if (function.equalsIgnoreCase("GET_USAGE_TEMPLATE_INFO")) {
      getUsageTemplateInfo();
    } else if (function.equalsIgnoreCase("GET_TEMPLATES")) {
      getUsageTemplate();
    } else if (function.equalsIgnoreCase("UPDATE_TEMPLATE")) {
      updateUsageTemplate();
    } else if (function.equalsIgnoreCase("LOAD_USAGE_TEMPLATE_INFO")) {
      loadUsageTemplate();
    } else if (function.equalsIgnoreCase("CREATE_REPORT")) {
      //createReport();
      createReporttest();
    } else if (function.equalsIgnoreCase("SEARCH_MANUFACTURER")) {
      searchManufacturer();
    }
    //System.out.println("outData:"+mySendObj);
  }

  protected void searchManufacturer() {
    Vector v = new Vector();
    try {
      String searchT = (String) inData.get("SEARCH_TEXT");
      AdHocReportsDBObj ahr = new AdHocReportsDBObj(db);
      v = ahr.searchManufacturer(searchT);
      mySendObj.put("MANUFACTURER_INFO", v);
      //Nawaz 19/09/01 new changes to gui tables
      mySendObj.put("TABLE_STYLE", BothHelpObjs.getTableStyle());
    } catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("MANUFACTURER_INFO", v);
    }
  }

  protected void createReporttest() {
    try {
      String client = db.getClient().toString().trim();
      String userID = (String) inData.get("USER_ID").toString().trim();
      AdHocReportsDBObj adhocDBObj = new AdHocReportsDBObj(db);
      String sessionID = adhocDBObj.createReportTemplate(userID, inData);
      mySendObj.put("SUCCEED", new Boolean(true));
      mySendObj.put("MSG", "Your report was sent to the server for processing.\nIt will automatically be sent to you upon completion.\nYou may now resume other activities within tcmIS.");
      String url = "/tcmIS/servlet/radian.web.servlets.reports.WebUsageAdhoc?sessionID=" + sessionID + "&client=" + client + "&userID=" + userID;
      mySendObj.put("URL", url);
    } catch (Exception e) {
      mySendObj.put("SUCCEED", new Boolean(false));
      mySendObj.put("MSG", "An error occurred while generating the report.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
    }

  }

  public Hashtable createReportNew(String sessionID, String userID) {
    Hashtable OutData = new Hashtable();
    try {
      AdHocReportsDBObj adhocDBObj = new AdHocReportsDBObj(db);
      Hashtable tmpReportData = new Hashtable(5);
      Hashtable innerH = adhocDBObj.loadReportTemplate(sessionID);
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
    Hashtable SelectedData = (Hashtable)RepData.get("SELECTED_DATA");
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
    File file = File.createTempFile("AdhocUsage" + userId + "", ".xls", dir);
    PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));

    try {
      Hashtable retH = new Hashtable();
      String whichascreen = (String) RepData.get("WHICH_SCREEN");
      reportfields = (Vector) SelectedData.get("REPORT_FIELDS");
      reoprtlog.info("createReport   Start " + file.getName() + " ");

      modifyTemplate(SelectedData, "Usage Report", pw);
      AdHocReportsDBObj ahr = new AdHocReportsDBObj(db);
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
      OutData.put("MSG",
                  "Error occur while Generating Report.\nPlease try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).");
      throw e;
    }
    return OutData;
  }

  protected void loadUsageTemplate() {
    try {
      AdHocReportsDBObj ahr = new AdHocReportsDBObj(db);
      Hashtable h = ahr.loadTemplateInfo(inData);
      mySendObj.put("TEMPLATE_INFO", h);
      //Nawaz 19/09/01 new changes to gui tables
      mySendObj.put("TABLE_STYLE", BothHelpObjs.getTableStyle());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void updateUsageTemplate() {
    try {
      AdHocReportsDBObj ahr = new AdHocReportsDBObj(db);
      Hashtable h = ahr.saveTemplate(inData);
      Boolean b = (Boolean) h.get("SUCCESS");
      String msg = (String) h.get("MSG");
      Boolean b1 = (Boolean) h.get("OVERRIDE");
      mySendObj.put("SUCCESS", b);
      mySendObj.put("MSG", msg);
      mySendObj.put("OVERRIDE", b1);
      /*
                   if (b.booleanValue()) {
        mySendObj.put("MSG","Template was successfully saved.");
                   }else {
        mySendObj.put("MSG","Template name already exist.\nDo you want to override it?");
                   } */
    } catch (Exception e) {
      e.printStackTrace();
      mySendObj.put("SUCCESS", new Boolean(false));
      mySendObj.put("OVERRIDE", new Boolean(false));
      mySendObj.put("MSG",
                    "An error occurred while saving the template.\nIf the problem recurs, please contact your tcmIS Customer Service Representative(CSR).");
    }
  }

  public void modifyTemplate(Hashtable SelectedData, String nameofrpt, PrintWriter pw) throws Exception {
    reportfields = (Vector) SelectedData.get("REPORT_FIELDS");
    String endYear = SelectedData.get("END_YEARN").toString();
    String endMonth = SelectedData.get("END_MONTH").toString();
    String beginYear = SelectedData.get("BEGIN_YEARN").toString();
    String beginMonth = SelectedData.get("BEGIN_MONTH").toString();
    String beginDay = SelectedData.get("BEGIN_DAY").toString();
    String endDay = SelectedData.get("END_DAY").toString();
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

    String begin = beginMonth + " " + beginDay + " " + beginYear;
    String End = endMonth + " " + endDay + " " + endYear;

    int sizeofcol = reportfields.size() - 2;
    pw.println("<FONT FACE=\"Arial\">");
    pw.println("<TABLE BORDER=\"0\">\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"" + reportfields.size() + "\" ALIGN=\"CENTER\" ><B><FONT size=\"4\" >" + nameofrpt + "</FONT></B></TD>\n");
    pw.println("</TR>\n");
    pw.println("<TR><TD COLSPAN=\"" + reportfields.size() + "\" ALIGN=\"RIGHT\"><FONT size=\"3\" >Date: " + cdate + "</FONT></TD></TR\n");
    pw.println("<TR><TD COLSPAN=\"" + reportfields.size() + "\" ALIGN=\"RIGHT\" ><FONT size=\"3\" >Time: " + time + "</FONT></TD></TR>\n");
    pw.println("</TABLE>\n");

    pw.println("<TABLE BORDER=\"0\">\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"2\" ALIGN=\"CENTER\" ><B><FONT size=\"3\" >Selection Criteria</FONT></B></TD>\n");
    pw.println("</TR>\n");
    pw.println("</TABLE>\n");
    pw.println("<TABLE BORDER=\"1\" CELLPADDING=\"3\">\n");

    type = (String) SelectedData.get("CHEM_TYPE");
    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\" ><B>Chemicals: </B></TD>\n");

    if (type.equalsIgnoreCase("List")) {
      pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("LIST_ID") == null ? "&nbsp;" : (String) SelectedData.get("LIST_ID")) + "</I></TD>\n");
    } else if (type.equalsIgnoreCase("Single")) {
      pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("CAST_NUMBER") == null ? "&nbsp;" : (String) SelectedData.get("CAST_NUMBER")) + "</I></TD>\n");
    } else {
      pw.println("<TD  ALIGN=\"LEFT\"><I>All</I></TD>\n");
    }
    pw.println("</TR>\n");

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Facility: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("FACILITY_DESC") == null ? "&nbsp;" : (String) SelectedData.get("FACILITY_DESC")) + "</I></TD>\n");
    pw.println("</TR>\n");

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Work Area: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("WORK_AREA_DESC") == null ? "&nbsp;" : (String) SelectedData.get("WORK_AREA_DESC")) + "</I></TD>\n");
    pw.println("</TR>\n");

    if (db.getClient().equalsIgnoreCase("SWA")) {
      pw.println("<TR>\n");
      pw.println("<TD  ALIGN=\"RIGHT\"><B>Report Category: </B></TD>\n");
      pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("REPORT_CATEGORY") == null ? "&nbsp;" : (String) SelectedData.get("REPORT_CATEGORY")) + "</I></TD>\n");
      pw.println("</TR>\n");

      pw.println("<TR>\n");
      pw.println("<TD  ALIGN=\"RIGHT\"><B>Location: </B></TD>\n");
      pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("LOCATION") == null ? "&nbsp;" : (String) SelectedData.get("LOCATION")) + "</I></TD>\n");
      pw.println("</TR>\n");

    }

    if (!BothHelpObjs.isBlankString(reportingEntityDesc)) {
      if (!"No Reporting Entity".equalsIgnoreCase(reportingEntityDesc)) {
        pw.println("<TR>\n");
        String reportingEntityLabel = SelectedData.get("REPORTING_ENTITY_LABEL").toString();
        if (BothHelpObjs.isBlankString(reportingEntityLabel)) {
          pw.println("<TD  ALIGN=\"RIGHT\"><B>Reporting Entity: </B></TD>\n");
        }else {
          pw.println("<TD  ALIGN=\"RIGHT\"><B>"+reportingEntityLabel+" </B></TD>\n");
        }
        pw.println("<TD  ALIGN=\"LEFT\"><I>" + reportingEntityDesc + "</I></TD>\n");
        pw.println("</TR>\n");
      }
    }

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Dock: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("DOCK_DESC") == null ? "&nbsp;" : (String) SelectedData.get("DOCK_DESC")) + "</I></TD>\n");
    pw.println("</TR>\n");

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Delivery Point: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("DELIVERY_POINT_DESC") == null ? "&nbsp;" : (String) SelectedData.get("DELIVERY_POINT_DESC")) + "</I></TD>\n");
    pw.println("</TR>\n");

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Part No: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("PART_NO") == "" ? "&nbsp;" : (String) SelectedData.get("PART_NO")) + "</I></TD>\n");
    pw.println("</TR>\n");

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Category: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("CATEGORY_DESC") == null ? "&nbsp;" : (String) SelectedData.get("CATEGORY_DESC")) + "</I></TD>\n");
    pw.println("</TR>\n");

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Manufacturer: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + ( (String) SelectedData.get("MANUFACTURER") == null ? "&nbsp;" : (String) SelectedData.get("MANUFACTURER")) + "</I></TD>\n");
    pw.println("</TR>\n");

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>Begin: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + begin + "</I></TD>\n");
    pw.println("</TR>\n");

    pw.println("<TR>\n");
    pw.println("<TD  ALIGN=\"RIGHT\"><B>End: </B></TD>\n");
    pw.println("<TD  ALIGN=\"LEFT\"><I>" + End + "</I></TD>\n");
    pw.println("</TR>\n");
    pw.println("</TABLE>\n");

    pw.println("<TABLE BORDER=\"0\">\n");
    pw.println("<TR><TD>&nbsp;</TD></TR\n");
    pw.println("<TR><TD>&nbsp;</TD></TR\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"" + reportfields.size() + "\" ALIGN=\"CENTER\" ><B><FONT size=\"3\" >Results</FONT></B></TD>\n");
    pw.println("</TR>\n");

    pw.println("</TABLE>\n");
    pw.println("<TABLE BORDER=\"1\">\n");

    try {
      pw.println("<TR>\n");
      for (int i = 0; i < reportfields.size(); i++) {
        String lable0 = reportfields.elementAt(i).toString();
        pw.println("<TD  ><B><FONT size=\"2\" >" + lable0 + "</FONT></B></TD>\n");
      }
      pw.println("</TR>\n");
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
  }

  protected void getUsageTemplate() {
    try {
      AdHocReportsDBObj ahr = new AdHocReportsDBObj(db);
      Vector v = ahr.getTemplate(inData);
      mySendObj.put("LIST_TEMPLATES", v);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void getUsageTemplateInfo() {
    try {
      AdHocReportsDBObj ahr = new AdHocReportsDBObj(db);
      Hashtable h = ahr.getUsageTemplateInfo(inData);
      mySendObj.put("USAGE_TEMPLATE_INFO", h);
      //Nawaz 19/09/01 new changes to gui tables
      mySendObj.put("TABLE_STYLE", BothHelpObjs.getTableStyle());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
