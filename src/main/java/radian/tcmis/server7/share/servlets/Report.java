package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;

public class Report extends TcmisServlet{
  //Client Version
  String action = null;
  Vector data = null;
  Vector staticData = null;

  String facility = null;
  String begDay = null;
  String begMon = null;
  String begYear = null;
  String endDay = null;
  String endMon = null;
  String endYear = null;
  String sortBy = null;
  String groupByMonth = null;
  String reportId = null;

  public Report(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    action = null;
    data = null;
    staticData = null;
    facility = null;
    begDay = null;
    begMon = null;
    begYear = null;
    endDay = null;
    endMon = null;
    endYear = null;
    sortBy = null;
    groupByMonth = null;
    reportId = null;
  }
  protected void getResult(HttpInput httpI){
    action = httpI.getString("ACTION");
    facility = httpI.getString("FACILITY");
    begDay = httpI.getString("BEGDAY");
    begMon = httpI.getString("BEGMONTH");
    begYear = httpI.getString("BEGYEAR");
    endDay = httpI.getString("ENDDAY");
    endMon = httpI.getString("ENDMONTH");
    endYear = httpI.getString("ENDYEAR");
    sortBy = httpI.getString("SORTBY");
    groupByMonth = httpI.getString("GROUPBYMONTH");
    reportId = httpI.getString("REPORT_ID");
    if(action.equalsIgnoreCase("MSDS")) {
      goMSDS();
    }else if(action.equalsIgnoreCase("LISTS")) {
      goGetLists();
    }else if(action.equalsIgnoreCase("REPORTABLE_USAGE")) {
      goReportableUsage();
    }else if(action.equalsIgnoreCase("VOC")) {
      goVOCUsage();
    }
  }
  protected void goReportableUsage() {
    try{
      //data = ReportData.getReportableUsageReport(db,reportId,facility,begMon,begYear,endMon,endYear,sortBy,groupByMonth.equalsIgnoreCase("true"));

      staticData = new Vector();
      if(BothHelpObjs.isBlankString(facility) || facility.equalsIgnoreCase("all")){
        staticData.addElement("All Facilities");
      }else{
        Facility f = new Facility(db);
        f.setFacilityId(facility);
        f.load();
        staticData.addElement(f.getFacilityName());
      }
    }catch(Exception e){e.printStackTrace();}
  }

  protected void goVOCUsage() {
    try{
      //data = ReportData.getVocUsageReport(db,facility,begMon,begYear,endMon,endYear,sortBy,groupByMonth.equalsIgnoreCase("true"));
      staticData = new Vector();
      if(BothHelpObjs.isBlankString(facility) || facility.equalsIgnoreCase("all")){
        staticData.addElement("All Facilities");
      }else{
        Facility f = new Facility(db);
        f.setFacilityId(facility);
        f.load();
        staticData.addElement(f.getFacilityName());
      }
    }catch(Exception e){e.printStackTrace();}

  }


  protected void goMSDS() {
    try{
      data = ReportData.getMSDSReport(db,facility);
    }catch(Exception e){e.printStackTrace();}
  }
  protected void goGetLists() {
    data = new Vector();
    Hashtable h = new Hashtable();
    try {
      h = ReportData.getReportLists(db);
    }catch(Exception e){e.printStackTrace();return;}
    for (Enumeration E = h.keys();E.hasMoreElements();){
      Object tmp = E.nextElement();
      data.addElement(tmp);
      data.addElement(h.get(tmp));
    }
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.printStart();
      out.println(data);
      out.printEnd();
      out.printStart();
      out.println(staticData);
      out.printEnd();
    }catch(Exception e){e.printStackTrace();}
  }

  protected  int getServInt(){
    return TcmisOutputStreamServer.REPORT;
  }
}
