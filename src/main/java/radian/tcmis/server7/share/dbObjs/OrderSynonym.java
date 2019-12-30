package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class OrderSynonym  {

   protected TcmISDBModel db;
   protected String search_string;
   protected String sales_order;
   protected String pr_number;
   protected String line_item;

   public OrderSynonym()  throws java.rmi.RemoteException {
   }

   public OrderSynonym(TcmISDBModel  db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public OrderSynonym(TcmISDBModel db,String so, String pr, String li, String ss) {
     this.setSalesOrder(so);
     this.setPRNumber(pr);
     this.setLineItem(li);
     this.setSearchString(ss);
   }

   public void setDB(TcmISDBModel  db) {
     this.db = db;
   }

   public void setSearchString(String s) {
     this.search_string = s;
   }

   public String getSearchString() {
     return this.search_string;
   }

   public void setSalesOrder(String s) {
     if(s == null) {
       s = "";
     }
     this.sales_order = s;
   }

   public String getSalesOrder() {
     return this.sales_order;
   }

   public void setPRNumber(String s) {
     if(s == null) {
       s = "";
     }
     this.pr_number = s;
   }

   public String getPRNumber() {
     return this.pr_number;
   }
   public void setLineItem(String s) {
     if(s == null) {
       s = "";
     }
     this.line_item = s;
   }

   public String getLineItem() {
     return this.line_item;
   }

   public boolean hasSalesOrder() {
     if(this.getSalesOrder().length() < 1) {
       return false;
     }
     return true;
   }

   public Vector load()  throws Exception {
     DBResultSet dbrs = null;
      ResultSet rs = null;
     Vector V = new Vector();
     String query = new String("select sales_order, pr_number, line_item from order_synonym where lower(search_string) like lower('%" + this.search_string + "%')");
     try {
	     dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
	     while(rs.next()) {
         OrderSynonym os = new OrderSynonym(db, rs.getString("sales_order"),rs.getString("pr_number"),rs.getString("line_item"),this.search_string);
         V.addElement(os);
	     }
       
       
     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
     return V;
   }
}