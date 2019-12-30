/*
SQLWKS> describe price_quote_request
Column Name                    Null?    Type
------------------------------ -------- ----
REQUEST_ID                     NOT NULL NUMBER
ITEM_ID                        NOT NULL NUMBER
REQUESTOR                      NOT NULL NUMBER
REQUEST_DATE                   NOT NULL DATE
FACILITY_ID                             VARCHAR2(30)
QUOTER                                  VARCHAR2(20)
*/

package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class PriceRequest {

   protected TcmISDBModel db;
   int requestId;
   Integer itemId;
   int requestorId;
   String dateRequested;
   String facId;
   String quoter;

   public PriceRequest() {
   }
   public PriceRequest(TcmISDBModel db) {
     this.db = db;
   }
   public void setDB(TcmISDBModel db){
     this.db = db;
   }
   public void setRequestorId (int s) {
     requestorId = s;
   }
   void setRequestId (int s) {
     requestId = s;
   }
   public void setFacId (String s) {
     facId = new String(s);
   }
   void setQuoter (String s) {
     if(s==null)return;
     quoter = new String(s);
   }
   public void setItemId (int s) {
     itemId = new Integer(s);
   }
   void setDateRequested (String s) {
     dateRequested = new String(s);
   }
   private TcmISDBModel getDB(){
     return db;
   }
   public int getRequestId() {
     return requestId;
   }
   public int getRequestorId() {
     return requestorId;
   }
   public String getFacId() {
     return facId;
   }
   public String getQuoter() {
     return quoter;
   }
   public int getItemId() {
     return itemId.intValue();
   }
   public String getDateRequested() {
     return dateRequested;
   }

   /** this is a non-static version of the priceIsRequested method */
   public boolean isRequested() {
     return PriceRequest.priceIsRequested(this);
   }

   /** loads the price request object */
   public void load() throws Exception{
     if(!PriceRequest.priceIsRequested(db,itemId.intValue(), facId)) {
       this.setRequestorId(0);
       this.setDateRequested("");
       return;
     }
     String query = "select * from price_quote_request_insert ";
     query = query + "where item_id = '"+itemId.toString()+"' and facility_id = '"+facId+"'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       if (rs.next()){
         this.setRequestorId((int)rs.getInt("requestor"));
         this.setDateRequested(rs.getString("request_date"));
         this.setRequestId(rs.getInt("request_id"));
         this.setQuoter(rs.getString("quoter"));
       }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
      return;
   }

   /** Returns true when the given part(defined by itemID, facItemID and facID)
       is on the price request table and a price has NOT been found. */
   public static boolean priceIsRequested(TcmISDBModel db, int itemId, String facId) {
     try{
       int x = DbHelpers.countQuery(db,"select count(*) from price_quote_request_insert where facility_id = '"+facId+"' and item_id = '"+itemId+"'");
       return x > 0;
     }catch(Exception e) {return false;}
   }
   public static boolean priceIsRequested(PriceRequest pr) {
     return PriceRequest.priceIsRequested(pr.getDB(),pr.getItemId(),pr.getFacId());
   }

   /** adds a price request to the price request table. This method
       will not add a duplicate to the price request table.*/
   boolean add() throws Exception{
     if(PriceRequest.priceIsRequested(db,itemId.intValue(),facId)) return false;
     //4-12-01 inserting to a new column requestor_company_id
     String companyId = db.getClient();
     if (companyId.equalsIgnoreCase("Ray")) {
      companyId = "RAYTHEON";
     }
     String query = "insert into price_quote_request_insert ";
     query = query + "(request_id, requestor_company_id, item_id, facility_id, requestor, request_date) ";
     query = query + "select price_quote_seq.nextval,'"+companyId+"','"+itemId.toString()+"','"+facId+"','"+requestorId+"',sysdate from dual";
     /*
     String query = "insert into price_quote_request_insert ";
     query = query + "(request_id, item_id, facility_id, requestor, request_date) ";
     query = query + "select price_quote_seq.nextval,'"+itemId.toString()+"','"+facId+"','"+requestorId+"',sysdate from dual";*/
     DBResultSet dbrs = null;
     //System.out.println("\n\n=========== price quote request: "+query);
     try {
        db.doUpdate(query);

     }catch (Exception e) {
     e.printStackTrace();
     throw e;
       }
     return true;
   }

   public void updateCatalogSnapshot() {
    String query = "update catalog_snapshot set price = 'Requested' where item_id = '"+itemId+"'";
    query += " and facility_id = '"+this.facId+"'";
    try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
     }
   }

   /** calls the add method and returns a Vector of the request info */
   public Vector getRequestPriceInfo() throws Exception {
     Vector v = new Vector(7);

     boolean added = false;
     try{
       added = add();
       load();
     }catch(Exception e) {e.printStackTrace(); throw e;
       }
     // item ID  - 0
     v.addElement(itemId.toString());

     // facility name - 1
     String facName = "";
     try {
       Facility f = new Facility(db);
       f.setFacilityId(facId);
       f.load();
       facName = f.getFacilityName();
     }catch(Exception e) {facName = "";}
     v.addElement(facName);

     // quoter  - 2
     v.addElement(getQuoter());

    // date requested
    String date = getDateRequested();
    v.addElement(BothHelpObjs.formatDate("toCharfromDB",date));

    // requestor name
    String reqName = "";
    try{
      Personnel p = new Personnel(db);
      p.setPersonnelId(getRequestorId());
      p.load();
      reqName = p.getFullName();
    }catch(Exception e) {reqName = "";}
    v.addElement(reqName);

    // request ID
    v.addElement((new Integer(getRequestId())).toString());

    // new addition ???
    String stat = "";
    if(added){
      stat = "NEW";
    }else{
      stat = "STATUS";
    }
    v.addElement(stat);
    return v;
   }
}
