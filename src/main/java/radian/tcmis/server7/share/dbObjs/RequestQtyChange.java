package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class RequestQtyChange  {

   protected TcmISDBModel db;

   public RequestQtyChange()  throws java.rmi.RemoteException {
     
   }

   public RequestQtyChange(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Hashtable getQtyChangeInfo(String mrNumber, String lineItem) throws Exception {
    Hashtable result = new Hashtable(4);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String qtyOpen = "";
      String qtyOnHand = "";
      String qtyOnBO = "";
      String qtyOnPO = "";
      //qty open
      String query = "select fx_open_qty("+Integer.parseInt(mrNumber)+","+Integer.parseInt(lineItem)+") qty_open from dual";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        qtyOpen = BothHelpObjs.makeBlankFromNull(rs.getString("qty_open"));
      }
      //qty on hand
      query = "select fx_receipt_alloc_qty("+Integer.parseInt(mrNumber)+","+Integer.parseInt(lineItem)+") qty_on_hand from dual";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        qtyOnHand = BothHelpObjs.makeBlankFromNull(rs.getString("qty_on_hand"));
      }
      //qty on BO
      query = "select fx_bo_alloc_qty("+Integer.parseInt(mrNumber)+","+Integer.parseInt(lineItem)+") qty_on_bo from dual";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        qtyOnBO = BothHelpObjs.makeBlankFromNull(rs.getString("qty_on_bo"));
      }
      //qty on PO
      query = "select fx_po_alloc_qty("+Integer.parseInt(mrNumber)+","+Integer.parseInt(lineItem)+") qty_on_po from dual";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        qtyOnPO = BothHelpObjs.makeBlankFromNull(rs.getString("qty_on_po"));
      }

      result.put("QTY_OPEN",qtyOpen);
      result.put("QTY_ON_HAND",qtyOnHand);
      result.put("QTY_ON_BO",qtyOnBO);
      result.put("QTY_ON_PO",qtyOnPO);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return result;
   }  //end of method


}   //end of class





























