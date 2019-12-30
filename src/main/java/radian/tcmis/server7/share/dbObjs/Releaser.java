
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class Releaser  {

   protected TcmISDBModel db;
   protected String facility_id;
   protected Integer personnel_id;

   public Releaser()  throws java.rmi.RemoteException {

   }

   public Releaser(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setFacilityId(String id) {
     this.facility_id = id;
   }

   public String getFacilityId() {
     return facility_id;
   }

   public void setPersonnelId(int id) {
     this.personnel_id = new Integer(id);
   }

   public Integer getPersonnelId() {
     return personnel_id;
   }


   public Hashtable retrieve()  throws Exception {
      String query = "select p.personnel_id,p.first_name,p.last_name from user_group_member r,personnel p where r.facility_id = '" + facility_id + "' and r.personnel_id = p.personnel_id and r.user_group_id = 'Releaser'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Integer ID;
      Hashtable H = new Hashtable();
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         String name = new String("");
         while (rs.next()) {
            ID = new Integer((int)rs.getInt(1));
            name = new String(rs.getString(2) + " " + rs.getString(3));
            H.put(ID,name);
         }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
      return H;
   }

   public Vector getAllReleasers() throws Exception{
      String query = "select p.personnel_id from user_group_member r,personnel p where r.facility_id = '" + facility_id + "' and r.personnel_id = p.personnel_id and r.user_group_id = 'Releaser'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector rels = new Vector();
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         String name = new String("");
         while (rs.next()) {
            Personnel p = new Personnel(db);
            p.setPersonnelId(rs.getInt("personnel_id"));
            p.load();
            rels.addElement(p);
          }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
      return rels;
   }

   public Hashtable retrieveRaw()  throws Exception {
      String query = "select unique r.personnel_id,p.first_name,p.last_name from user_group_member r,personnel p where f.personnel_id = p.personnel_id and r.user_group_id = 'Releaser'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Integer ID;
      Hashtable H = new Hashtable();
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         String name = new String("");
         while (rs.next()) {
            ID = new Integer((int)rs.getInt(1));
            name = new String(rs.getString(2) + " " + rs.getString(3));
            H.put(name,ID);
         }


     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();
             }
      return H;
   }

   public boolean isValid(String fac, Integer num)  throws Exception {
      String query = "select count(*) from user_group_member where user_group_id = 'Releaser' and facility_id = '" + fac + "' and personnel_id =" +  num;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      int count = 0;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()) {
            count = (int)rs.getInt(1);
            break;
         }


    } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();
             }
      return (count>0);
   }

   public Integer getPreferredForFacility(String facID)   throws Exception {
      String query = "select personnel_id from user_group_member where user_group_id = 'Releaser' and facility_id = '"+facID+"'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Integer ID = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()) {
            ID = new Integer((int)rs.getInt("personnel_id"));
            break;
         }
      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();
             }
      return ID;
   }

}





























