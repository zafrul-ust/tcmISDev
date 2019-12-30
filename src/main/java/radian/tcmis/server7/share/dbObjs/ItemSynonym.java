
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class ItemSynonym  {

   protected TcmISDBModel db;
   protected String search_string;
   protected Integer item_id;

   public ItemSynonym()  throws java.rmi.RemoteException {
       
   
   }

   public ItemSynonym(TcmISDBModel  db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel  db) {
     this.db = db;
   }

   public void setItemId(int id) {
     this.item_id = new Integer(id);
   }

   public Integer getItemId() {
     return this.item_id;
   }

   public void setSearchString(String s) {
     this.search_string = s;
   }

   public String getSearchString() {
     return this.search_string;
   }

   public Vector load()  throws Exception {
     DBResultSet dbrs = null;
      ResultSet rs = null;
     Vector V = new Vector();
// CBK begin
     String query = new String("select distinct item_id from item_synonym where lower(search_string) like lower('%" + this.search_string + "%')");
// CBK end
     try {
     	 dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	     while(rs.next()) {
	       V.addElement(new Integer(rs.getInt(1)));
	     }
       
       
     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
     return V;
   }
}






























