package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;


public class WasteChangeProfile {
    final static int PROFILE_COL = 0;
    final static int DESC_COL = 1;
    final static int WASTE_ITEM_ID_COL = 2;

    final static String[] colH = {"Profile Id","Description","Waste Item"};
    final static int[] colT = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING};
    final static int[] colW = {125,370,0};

  protected TcmISDBModel db;

  public WasteChangeProfile(TcmISDBModel db){
      this.db = db;
  }
  public void setDb(TcmISDBModel db){
      this.db = db;
  }

  public String changeProfile(String containerId, String wasteItemId) throws Exception{
    String msg = new String("");
    try {
      db.doUpdate("update waste_container set waste_item_id = "+wasteItemId+" where container_id = "+containerId);
    }catch(Exception exx){
      exx.printStackTrace();
      msg = "Can't find the waste container for container id: "+containerId;
      return msg;
    }
    return ("Profile was successfully changed.");

  }

  //running with one query
  public static Hashtable getProfile(TcmISDBModel db,String facilityId, String packaging) throws Exception{
    Hashtable result = new Hashtable();
    Vector dataV = new Vector();
    String query = "select vendor_profile_id,description,waste_item_id from waste_catalog_view where facility_id = '"+facilityId+"'";
    query += " and vendor_profile_id != 'UNKNOWN'";
    query += " and packaging = '"+packaging+"' order by vendor_profile_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa = new Object[colH.length];
        oa[PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
        oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
        oa[WASTE_ITEM_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
        dataV.addElement(oa);
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("TABLE_HEADERS",colH);
    result.put("TABLE_WIDTHS",colW);
    result.put("TABLE_TYPES",colT);
    dataV.trimToSize();
    result.put("TABLE_DATA",dataV);
    return result;
  }

  //running with one query
  public static Hashtable getTsdfProfile(TcmISDBModel db, String containerId) throws Exception{
    Hashtable result = new Hashtable();
    Vector dataV = new Vector();
    String query = "select to_vendor_profile_id,to_description,to_waste_item_id from waste_tsdf_correction_view where container_id = "+containerId+
                   " order by to_vendor_profile_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa = new Object[colH.length];
        oa[PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("to_vendor_profile_id"));
        oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("to_description"));
        oa[WASTE_ITEM_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("to_waste_item_id"));
        dataV.addElement(oa);
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("TABLE_HEADERS",colH);
    result.put("TABLE_WIDTHS",colW);
    result.put("TABLE_TYPES",colT);
    dataV.trimToSize();
    result.put("TABLE_DATA",dataV);
    return result;
  }
} //end of class

