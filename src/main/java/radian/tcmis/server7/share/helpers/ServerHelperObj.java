package radian.tcmis.server7.share.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;

public class ServerHelperObj  {

   protected TcmISDBModel db;

   public ServerHelperObj()  throws java.rmi.RemoteException {

   }

   public ServerHelperObj(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Hashtable getFacilityWorkAreaList(Integer userId, String status, String constraint) throws Exception {
     Hashtable result = new Hashtable();
     Vector facilityV = new Vector();
     Vector facilityDescV = new Vector();
     String query = "select a.facility_id,nvl(a.facility_name,a.facility_id) facility_name,a.application,a.application_desc";
     if (BothHelpObjs.isBlankString(constraint)) {
       query += " from my_workarea_facility_view a where a.personnel_id = "+userId+" and a.status = '"+status+"'";
     }else {
       query += constraint+" and a.personnel_id = "+userId+" and a.status = '"+status+"'";
     }
     query += " order by facility_name,application_desc";

     DBResultSet dbrs = null;
     ResultSet rs = null;
     String lastFacId = "";
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         String fac = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
         if (!fac.equals(lastFacId)) {
           facilityV.addElement(fac);
           facilityDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
           lastFacId = fac;
         }
         String app = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
         String desc = BothHelpObjs.makeBlankFromNull(rs.getString("application_desc"));
         if (result.containsKey(fac)) {
           Hashtable h = (Hashtable)result.get(fac);
           Vector appId = (Vector)h.get("APPLICATION");
           Vector appDesc = (Vector)h.get("APPLICATION_DESC");
           if (!appId.contains(app)) {
             appId.addElement(app);
             appDesc.addElement(desc);
             if (!appId.contains("Select a Work Area")) {
                appId.insertElementAt("Select a Work Area",0);
                appDesc.insertElementAt("Select a Work Area",0);
              }
           }
           h.put("APPLICATION",appId);
           h.put("APPLICATION_DESC",appDesc);
           result.put(fac,h);
         }else {
           Hashtable h = new Hashtable();
           Vector appId = new Vector();
           Vector appDesc = new Vector();
           appId.addElement(app);
           appDesc.addElement(desc);
           h.put("APPLICATION",appId);
           h.put("APPLICATION_DESC",appDesc);
           result.put(fac,h);
         }
       }
     } catch (Exception e) {
       e.printStackTrace(); throw e;
     } finally {
       dbrs.close();
     }
     result.put("FACILITY",facilityV);
     result.put("FACILITY_NAME",facilityDescV);
     //System.out.println("------------ my result: "+result);
     return result;
   } //end of method

} //end of class