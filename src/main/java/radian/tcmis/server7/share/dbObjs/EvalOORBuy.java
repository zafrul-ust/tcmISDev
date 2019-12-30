/*
SQLWKS> describe eval_oor_buy
Column Name                    Null?    Type
------------------------------ -------- ----
BRANCH_PLANT                            VARCHAR2(12)
ITEM_ID                                 NUMBER(8)
ORDER_QUANTITY                          NUMBER
RADIAN_PO                               NUMBER
PROMISED_DATE                           DATE
PART_ID                                 VARCHAR2(30)
ITEM_DESC                               CHAR(61)
ITEM_TYPE                               CHAR(3)
UOM                                     CHAR(2)
PRIORITY                                CHAR(1)
ASSIGNED_BUYER                          VARCHAR2(20)
FACILITY                                VARCHAR2(30)
SALES_ORDER                             NUMBER(8)
RAYTHEON_PO                             CHAR(25)
BUY_ORDER_INSERTED                      DATE

*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class EvalOORBuy  {

   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;

   protected String sales_order;
   protected TcmISDBModel db;


   public EvalOORBuy()  throws java.rmi.RemoteException {

   }

   public EvalOORBuy(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setSalesOrder(String so) {
     this.sales_order = so;
   }

   public void delete()  throws Exception {
     String query = "delete eval_oor_buy where sales_order = '" + sales_order + "'";
     DBResultSet dbrs = null;
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       throw e;
     }
   }

   public void load()  throws Exception {
      String query = "select * from eval_oor_buy where sales_order = '" + sales_order + "'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           // need to implement
         }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
      return;
   }

   public void insert()  throws Exception {
     DBResultSet dbrs = null;
     String dummy = new String("");

     String query = new String("insert into eval_oor_buy (sales_order)");
     query = query + " values ('" + sales_order + "')";


     try {
       db.doUpdate(query);

    } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       }
   }

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;

     String query = new String("update eval_oor_buy set " + col + " = ");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.toString();
         break;
       case DATE:
         try {
           ResultSet rs = null;
           dbrs = db.doQuery("select to_char(sysdate,'HH24:Mi:SS') from dual");   rs=dbrs.getResultSet();

           if (rs.next()) {
	       val = val + " " + rs.getString(1);
           }
           
           
        } catch (Exception e){
        e.printStackTrace();
         throw e;
       } finally{
             dbrs.close();
           }
         if (val.equals("nowDate")){
           query = query + " SYSDATE ";
         } else {
           query = query + " to_date('"+val+"','MM/dd/yy HH24:MI:SS')";
         }
         break;
       case STRING:
         query = query + "'" + val + "'";
         break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query = query + "  where sales_order = '" + sales_order + "'";
     try {
       db.doUpdate(query);
    } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       } 
   }
}




























