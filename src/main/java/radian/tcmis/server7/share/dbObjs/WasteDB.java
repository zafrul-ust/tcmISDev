package radian.tcmis.server7.share.dbObjs;

//import radian.tcmis.server7.client.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.sql.*;

/*
SQLWKS> describe waste
Column Name                    Null?    Type
------------------------------ -------- ----
FACILITY_ID                    NOT NULL VARCHAR2(30)
WORK_AREA                      NOT NULL VARCHAR2(30)
WASTE_ID                       NOT NULL VARCHAR2(80)
PROCESS_DESC                            VARCHAR2(400)
WASTE_NAME                              VARCHAR2(20)
WASTE_CATEGORY_ID                       VARCHAR2(20)
WASTE_TYPE_ID                           VARCHAR2(20)
*/

public class WasteDB {
  protected TcmISDBModel db;
  protected String facilityId;
  protected String workArea;
  protected String wasteId;
  protected String processDesc;
  protected String wasteName;
  protected String wasteCategoryId;
  protected String wasteTypeId;

  public WasteDB(TcmISDBModel db) {
    this.db = db;
  }

  public WasteDB() {
  }

  //set methods
  public void setFacilityId(String s){facilityId = s;}
  public void setWorkArea(String s){workArea = s;}
  public void setWasteId(String s){wasteId = s;}
  public void setProcessDesc(String s){processDesc = s;}
  public void setWasteName(String s){wasteName = s;}
  public void setWasteCategoryId(String s){wasteCategoryId = s;}
  public void setWasteTypeId(String s){wasteTypeId = s;}

  //get methods
  public String getFacilityId(){return facilityId;}
  public String getWorkArea(){return workArea;}
  public String getWasteId(){return wasteId;}
  public String getProcessDesc(){return processDesc;}
  public String getWasteName(){return wasteName;}
  public String getWasteCategoryId(){return wasteCategoryId;}
  public String getWasteTypeId(){return wasteTypeId;}

  public void load() throws Exception{
    String query = "select * from waste where facility_id = '"+getFacilityId()+"'";
    query = query + "and work_area = '"+getWorkArea()+"'";
    query = query + "and waste_id = '"+getWasteId()+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try{
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while(rs.next()){
        setWasteCategoryId(BothHelpObjs.makeBlankFromNull(rs.getString("waste_category_id")));
        setWasteTypeId(BothHelpObjs.makeBlankFromNull(rs.getString("waste_type_id")));
        setWasteName(BothHelpObjs.makeBlankFromNull(rs.getString("waste_name")));
        setProcessDesc(BothHelpObjs.makeBlankFromNull(rs.getString("process_desc")));
      }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
  }
}