
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.sql.*;

public class ClientPart {

   protected TcmISDBModel db;
   protected String search_string = null;
   protected Integer item_id = null;
   protected String part_id = null;
   protected String spec_id = null;
   protected String part_desc = null;
   protected String facility_id = null;
   protected String stocked = null;


   public ClientPart()  throws java.rmi.RemoteException {

   }

   public ClientPart(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db) {
     this.db = db;
   }

   public void setItemId(int id) {
     this.item_id = new Integer(id);
   }

   public Integer getItemId() {
     return this.item_id;
   }

   public void setPartId(String id) {
     this.part_id = id;
   }

   public String getPartId() {
     return this.part_id;
   }

   public void setSpecId(String id) {
     this.spec_id =id;
   }

   public String getSpecId() {
     return this.spec_id;
   }

   public void setFacilityId(String id) {
     this.facility_id = id;
   }

   public String getFacilityId() {
     return this.facility_id;
   }

   public void load()  throws Exception {
      String query = "select a.facility_id, a.spec_id, a.stocked from fac_item a, catalog_part_item_group b where b.item_id = " + item_id;
      query = query + " and a.fac_part_no = '"+part_id+"' and b.catalog_id ='"+facility_id+"' ";
      query = query + " and b.catalog_id = a.facility_id and b.cat_part_no = a.fac_part_no ";

      // // // System.out.println("query:"+query);
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           facility_id = rs.getString(1);
           spec_id = rs.getString(2);
           stocked = rs.getString(3);
         }

      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       } finally{
         dbrs.close();
       }
   }

    public Integer getMaterialId(String materialDesc)  throws Exception {
      String query = "select a.material_id from part a, material b where a.item_id = " + item_id;
      query = query + " and b.trade_name = '"+materialDesc+"' and a.material_id = b.material_id ";
      // // // System.out.println("query:"+query);
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Integer result = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           result = new Integer((int) rs.getInt(1));
         }

      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
       }

      return result;
   }

}






























