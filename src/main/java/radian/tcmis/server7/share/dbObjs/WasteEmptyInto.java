package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;


public abstract class WasteEmptyInto {
    final static int PROFILE_COL = 0;
    final static int DESC_COL = 1;
    final static int PACK_COL = 2;
    final static int CONTAINER_COL = 3;
    final static int UNIT_COL = 4;

    final static String[] colH = {"Profile Id","Description","Packaging","Container Id","Unit"};
    final static int[] colT = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING};
    final static int[] colW = {100,270,94,80,0};

  protected TcmISDBModel db;

  public WasteEmptyInto(TcmISDBModel db){
      this.db = db;
  }
  public void setDb(TcmISDBModel db){
      this.db = db;
  }


  //running with one query
  public static Hashtable getAccumulationContainerProfile(TcmISDBModel db,String facilityId, String storageLocation,String vendorId,String vendorProfileId) throws Exception{
    Hashtable result = new Hashtable();
    Vector dataV = new Vector();
    String query = "select vendor_profile_id,description,packaging,container_id,size_unit from waste_accumulation_view where facility_id = '"+facilityId+"'";
    query += " and storage_location = '"+storageLocation+"'";
    query += " and vendor_id = '"+vendorId+"'";
    query += " and vendor_profile_id = '"+vendorProfileId+"'";
    query += " order by vendor_profile_id";       //need to change this condition after show Mike the screen
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa = new Object[colH.length];
        oa[PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
        oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
        oa[PACK_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        oa[CONTAINER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));
        oa[UNIT_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("size_unit"));
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