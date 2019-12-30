package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;

public class AribaRepairObj  {

   protected TcmISDBModel db;

   public AribaRepairObj()  throws java.rmi.RemoteException {

   }

   public AribaRepairObj(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Vector doSearch(String searchText)  throws Exception {
      Vector result = new Vector();
      String query = "select * from mr_needing_approval_view";
      if (!BothHelpObjs.isBlankString(searchText)) {
        query += " where release_date < to_date('"+searchText+"','mm/dd/yyyy')";
      }
      query += " order by pr_number,to_number(line_item)";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()) {
          Hashtable h = new Hashtable(9);
          h.put("REQUESTOR",BothHelpObjs.makeBlankFromNull(rs.getString("requestor_name")));
          h.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(rs.getString("application_desc")));
          h.put("MR",BothHelpObjs.makeBlankFromNull(rs.getString("pr_number")));
          h.put("LINE",BothHelpObjs.makeBlankFromNull(rs.getString("line_item")));
          h.put("DATE",BothHelpObjs.formatDate("toCharfromDB",rs.getString("release_date")));
          h.put("PART",BothHelpObjs.makeBlankFromNull(rs.getString("fac_part_no")));
          h.put("DESC",BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
          h.put("QTY",BothHelpObjs.makeBlankFromNull(rs.getString("quantity")));
          h.put("EXT_PRICE",BothHelpObjs.makeBlankFromNull(rs.getString("extended_price")));
          result.addElement(h);
        }
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      } finally{
        dbrs.close();
      }
      return result;
   }  //end of method

   public boolean resubmitRequests(Vector dataV, String payLoadID) throws Exception {
      boolean result = false;
      String query = "update request_line_item set payload_id = '"+payLoadID+"' where ";
      String where = "";
      for (int i = 0; i < dataV.size(); i++) {
        Hashtable h = (Hashtable)dataV.elementAt(i);
        where += "(pr_number = "+(String)h.get("MR")+" and line_item = "+(String)h.get("LINE")+") or ";
      }
      //removing the last or
      where = where.substring(0,where.length()-3);
      try {
        HelpObjs.insertUpdateTable(db,query+where);
        result = true;
      }catch (Exception e) {
        e.printStackTrace();
      }
      return result;
   }
} //end of class