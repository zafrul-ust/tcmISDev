/*
SQLWKS> describe item
Column Name                    Null?    Type
------------------------------ -------- ----
ITEM_ID                        NOT NULL NUMBER(38)
ITEM_DESC                               VARCHAR2(100)
CATEGORY_ID                             NUMBER
REPLACEMENT_ITEM                        NUMBER(38)
STOCKING_TYPE                           CHAR(1)
REVISION_COMMENTS                       VARCHAR2(240)
*/

package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.sql.*;


public class GlobalItem {
  protected TcmISDBModel db;
  protected String item_desc;
  protected int item_id;

  public GlobalItem()  throws java.rmi.RemoteException {
   }

  public GlobalItem(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }
  public void setItemId(int N) {
    this.item_id = N;
  }
  public int getItemId() {
    return this.item_id;
  }
  public void setItemDesc(String N) {
    this.item_desc = N;
  }
  public String getItemDesc() {
    return(BothHelpObjs.makeBlankFromNull(this.item_desc));
  }

  public void load()  throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "select * from item where item_id = " + this.item_id;
     try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        while(rs.next()) {
           setItemDesc(BothHelpObjs.makeBlankFromNull(rs.getString("item_desc")));
        }
     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
     } finally{
        dbrs.close();
     }
     return;
  }
}
