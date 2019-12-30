package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;


public abstract class WasteNewContainer {
    final static int STATE_CODE_COL = 0;
    final static int PROFILE_COL = 1;
    final static int DESC_COL = 2;
    final static int PACK_COL = 3;
    final static int WASTE_ITEM_ID_COL = 4;

    final static String[] colH = {"State Waste Code","Profile Id","Description","Packaging","Waste Item Id"};
    final static int[] colT = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING};
    final static int[] colW = {150,100,200,94,0};

  protected TcmISDBModel db;

  public WasteNewContainer(TcmISDBModel db){
      this.db = db;
  }
  public void setDb(TcmISDBModel db){
      this.db = db;
  }


  //running with one query
  public static Hashtable getNewContainerProfile(TcmISDBModel db,String facilityId, String vendorId) throws Exception{
    Hashtable result = new Hashtable();
    Vector dataV = new Vector();
    String query = "select state_waste_codes,vendor_profile_id,description,packaging,waste_item_id from waste_catalog_view where facility_id = '"+facilityId+"'";
    query += " and vendor_profile_id != 'UNKNOWN'";
    query += " and vendor_id = '"+vendorId+"' order by vendor_profile_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa = new Object[colH.length];
        oa[STATE_CODE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("state_waste_codes"));
        oa[PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
        oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
        oa[PACK_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
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
  public static Hashtable getNewLabPackContainerProfile(TcmISDBModel db,String orderNo,String vendorId,String facilityId) throws Exception{
    Hashtable result = new Hashtable();
    Vector dataV = new Vector();
    String query = "select state_waste_codes,vendor_profile_id,description,packaging,waste_item_id,facility_id,vendor_id from waste_catalog_view";
    query += " where lab_pack = 'Y' and vendor_id = '"+vendorId+"'";
    query += " and facility_id = '"+facilityId+"'";
    query += " order by vendor_profile_id";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa = new Object[colH.length];
        oa[STATE_CODE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("state_waste_codes"));
        oa[PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
        oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
        oa[PACK_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
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

}
