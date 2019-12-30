package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.sql.*;

public abstract class WasteCodes {
  public static final String EPA = "epa";
  public static final String STATE = "state";

  // get description methods
  protected static String getEpaWasteCodeDescription(TcmISDBModel db,String id)throws Exception{
    return getWasteCodeDescription(db,id,EPA);
  }
  protected static String getStateWasteCodeDescription(TcmISDBModel db,String id)throws Exception{
    return getWasteCodeDescription(db,id,STATE);
  }
  protected static String getWasteCodeDescription(TcmISDBModel db,String id,String type)throws Exception{
     String desc = "";
     String query = "select * from "+type+"_waste_code where "+type+"_waste_code_id = '"+id+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         desc = BothHelpObjs.makeBlankFromNull(rs.getString(type+"_waste_code_description"));
       }
     }catch (Exception e) {e.printStackTrace();throw e;
     }finally{dbrs.close();}
     return desc;
  }

  // is valid methods
  protected static boolean isValidEpaWasteCode(TcmISDBModel db,String id)throws Exception{
    return isValidWasteCode(db,id,EPA);
  }
  protected static boolean isValidStateWasteCode(TcmISDBModel db,String id)throws Exception{
    return isValidWasteCode(db,id,STATE);
  }
  protected static boolean isValidWasteCode(TcmISDBModel db,String id,String type)throws Exception{
    String query = "select count(*) from "+type+"_waste_code where "+type+"_waste_code_id = '"+id+"'";
    return DbHelpers.countQuery(db,query)>0;
  }

}