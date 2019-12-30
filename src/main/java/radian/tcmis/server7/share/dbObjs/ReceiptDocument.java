package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;

public class ReceiptDocument {

  protected TcmISDBModel db;

  public ReceiptDocument() throws java.rmi.RemoteException {

  }

  public ReceiptDocument(TcmISDBModel db) throws java.rmi.RemoteException {
    this.db = db;
  }

  public void setDB(TcmISDBModel db) {
    this.db = db;
  }

  public Vector getReceiptDocument(Hashtable inData) throws Exception {
    String query = "select * from receipt_document_view where receipt_id = "+(String)inData.get("RECEIPT_ID");
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector dataV = new Vector(13);
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      String name = new String("");
      while (rs.next()) {
        Object[] oa = new Object[4];
        oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("document_type_desc"));
        oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("document_name"));
        oa[2] = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("document_date")));
        oa[3] = BothHelpObjs.makeBlankFromNull(rs.getString("document_url"));
        dataV.addElement(oa);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return dataV;
  }

} //end of class
