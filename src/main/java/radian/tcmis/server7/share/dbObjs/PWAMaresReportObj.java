package radian.tcmis.server7.share.dbObjs;

import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class PWAMaresReportObj {
  private TcmISDBModel db;
  public static final String[] HEADER_COLUMN = {"PART_NUMBER","PO_NUM","PS_QTY","PS_DATE","PACKING_SLIP_NO",
                                                "CURE_DATE","SHIPMENT_ID","MFG_LOT_NUM","MFG_EXP_DATE","SUPPLIER_NAME"};

  public PWAMaresReportObj(TcmISDBModel db) throws java.rmi.RemoteException {
    this.db = db;
  }

  public Hashtable getPWAMaresReportData() {
    String query = "select * from pwa_mares_report_view where transaction_date > '1-apr-2005'";
    return (getMaresReportData(query));
  } //end of method

  private Hashtable getMaresReportData(String query) {
    Hashtable reportDataH = new Hashtable(5);
    Vector rowDataV = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector partNumber = new Vector(31);
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Vector columnDataV = new Vector(HEADER_COLUMN.length);
        //get data for each columns
        for (int i = 0; i < HEADER_COLUMN.length; i++) {
          columnDataV.addElement((String)rs.getString(HEADER_COLUMN[i]));
          //keep track of part number to send to Chris Doskos
          if ("PART_NUMBER".equalsIgnoreCase(HEADER_COLUMN[i])) {
            partNumber.addElement((String)rs.getString(HEADER_COLUMN[i]));
          }
        }
        rowDataV.addElement(columnDataV);
      }
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db,"Error while getting data PWA mares report","Error while getting data PWA mares report",86030,false);
    }finally {
      dbrs.close();
    }
    //send out warning email if now record found
    if (rowDataV.size() < 1) {
      HelpObjs.sendMail(db,"PWA mares report no record found",query,86030,false);
    }
    reportDataH.put("DATA",rowDataV);
    reportDataH.put("HEADER_COLUMN",HEADER_COLUMN);
    reportDataH.put("PART_NUMBER",partNumber);
    return reportDataH;
  } //end of method

} //end of class