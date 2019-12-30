package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class PriceQuoteRequest  {

   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;

   protected TcmISDBModel db;

   public PriceQuoteRequest()  throws java.rmi.RemoteException {

   }

   public PriceQuoteRequest(TcmISDBModel  db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void processPriceQuote()  throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "";
     try {
     	 dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       //first get all completed quote requests
       Vector requestID = new Vector();
       query ="select * from price_quote_complete";
	     while(rs.next()) {
        requestID.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("request_id")));
	     }
       //next find all new chem request that is waiting for price
       



     } catch (Exception e) {
      e.printStackTrace();
      throw e;
     } finally{
      dbrs.close();
     }
   } //end of method
}  //end of class
