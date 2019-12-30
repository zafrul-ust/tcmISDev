package radian.tcmis.server7.share.dbObjs;

import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class PWAWeeklyUsageReportObj {
  private TcmISDBModel db;
  public static final String[] HEADER_COLUMN = {"FACILITY","WORK_AREA","PARENT_MATERIAL_ID","QTY_DELIVERED",
                                                "DATE_DELIVERED","MATERIAL_REQUEST","ITEM","KIT"};
  public PWAWeeklyUsageReportObj(TcmISDBModel db) throws java.rmi.RemoteException {
    this.db = db;
  }

  public Hashtable getRegularPWAWeeklyReportData(int startDay, int endDay) {
    String query = "select * from pwa_usage_view where ship_confirm_start = trunc(sysdate) -"+startDay+
                   " and ship_confirm_stop = trunc(sysdate) -"+endDay;
    return (getWeeklyReportData(query));
  } //end of method

  public Hashtable getCreditPWAWeeklyReportData(int startDay, int endDay) {
    String query = "select * from pwa_usage_credit_view where ship_confirm_start = trunc(sysdate) -"+startDay+
                   " and ship_confirm_stop = trunc(sysdate) -"+endDay;
    return (getWeeklyReportData(query));
  } //end of method


  private Hashtable getWeeklyReportData(String query) {
    Hashtable reportDataH = new Hashtable(5);
    Vector rowDataV = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Vector columnDataV = new Vector(HEADER_COLUMN.length);
        //get data for each columns
        for (int i = 0; i < HEADER_COLUMN.length; i++) {
          columnDataV.addElement((String)rs.getString(HEADER_COLUMN[i]));
        }
        rowDataV.addElement(columnDataV);
      }
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db,"Error while getting data PWA weekly usage report","Error while getting data PWA weekly usage report",86030,false);
    }finally {
      dbrs.close();
    }
    //send out warning email if now record found
    if (rowDataV.size() < 1) {
      HelpObjs.sendMail(db,"PWA weekly usage report no record found",query,86030,false);
    }
    reportDataH.put("DATA",rowDataV);
    reportDataH.put("HEADER_COLUMN",HEADER_COLUMN);
    return reportDataH;
  } //end of method

} //end of class