/*
 * VvItemTypeProcess.java
 *
 * Created on July 8, 2004, 2:56 PM
 */

package com.tcmis.supplier.wbuy.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbConnectionException;
import com.tcmis.common.framework.BaseProcess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.Collection;

/**
 *
 * @author  mike.najera
 */
public class VvItemTypeProcess extends BaseProcess {
   
  /** Creates a new instance of VvItemTypeProcess */
  public VvItemTypeProcess(String client) {
    super(client);
  }
   
  /*
  public Vector getaddchargeType()
  {
     String query="select ITEM_TYPE,TYPE_DESC from global.vv_item_type where MA_ADD_CHARGE = 'y'";
     ResultSet rs=null;
     Hashtable result=null;
     Vector ResultV=new Vector();
     try {
	rs=doQuery( query );
	while ( rs.next() ) {
	   //result = new Hashtable();
	   //String faci  = (rs.getString("FREIGHT_ON_BOARD")==null?"":rs.getString("FREIGHT_ON_BOARD"));
	   String facn= ( rs.getString( "ITEM_TYPE" ) == null ? "" : rs.getString( "ITEM_TYPE" ) );
	   //result.put(facn,faci);
	   ResultV.addElement( facn );
	}
     } catch ( Exception e ) {
	log.error("Exception querying global.vv_item_type: "+e);
     } finally {
	if ( rs != null ) {
           try {
              rs.close();
           } catch (SQLException sqle) {
              log.error("getaddchargetype: sql exception in close Rs: " + sqle); 
           }
	}
     }
     return ResultV;
  }

   public Vector getitemtype() {
      String query="select ITEM_TYPE from global.vv_item_type order by item_type";
      ResultSet rs=null;
      Hashtable result=null;
      Vector ResultV=new Vector();
      try {
         rs=doQuery( query );
	 while ( rs.next() ) {
            result=new Hashtable();
            String facn= ( rs.getString( "ITEM_TYPE" ) == null ? "" : rs.getString( "ITEM_TYPE" ) );		 
            result.put( facn,facn );
            ResultV.addElement( result );
	 }
      } catch ( Exception e ) {
	 e.printStackTrace();
      } finally {
	 if ( rs != null ) {
            try {
               rs.close();
            } catch (SQLException sqle) {
               log.error("getitemtype: sql exception in close Rs: " + sqle); 
            }
	 }    
      }
      return ResultV;
   }
/*
   private ResultSet doQuery(String query) throws BaseException {
      ResultSet rs = null;
      try {
         DbManager dbManager = new DbManager(this.getClient());
         Connection conn = dbManager.getConnection();
         Statement stmt = conn.createStatement();
           
         rs = stmt.executeQuery(query);         
      } catch (DbConnectionException dbce) {
         log.error("Db Connection Exception executing internal order process query: " + dbce);
      } catch (SQLException sqle) {
         log.error("SQL Exception executing internal order process query: " + sqle);
      }
      
      return rs;
   }   
 */  
  public Vector getaddchargeType()
  {
     String query="select ITEM_TYPE,TYPE_DESC from global.vv_item_type where MA_ADD_CHARGE = 'y'";
     Collection rs =null;
     Hashtable result=null;
     Vector ResultV=new Vector();
     Iterator iter = null;
     VvItemTypeBean bean = null;
     try {
	rs=doQuery(query);
        if (rs!=null) {
           iter = rs.iterator();
           while ( iter.hasNext() ) {
              bean = (VvItemTypeBean) iter.next();
              String facn=  ( bean.getItemType() == null ? "" : bean.getItemType() );
              //String facn= ( rs.getString( "ITEM_TYPE" ) == null ? "" : rs.getString( "ITEM_TYPE" ) );
              ResultV.addElement( facn );
           }
        }
     } catch ( Exception e ) {
	log.error("Exception querying global.vv_item_type: "+e);
     }
     return ResultV;
  }

   public Vector getitemtype() {
      String query="select ITEM_TYPE,TYPE+DESC from global.vv_item_type order by item_type";
      Collection rs=null;
      Hashtable result=null;
      Vector ResultV=new Vector();
      Iterator iter = null;
      VvItemTypeBean bean = null;      
      try {
        rs=doQuery(query);
        if (rs!=null) {
           iter = rs.iterator();
           while ( iter.hasNext() ) {
             bean = (VvItemTypeBean) iter.next();
             String facn= ( bean.getItemType() == null ? "" : bean.getItemType() );		 
             result.put( facn,facn );
             ResultV.addElement( result );
	    }
        }
      } catch ( Exception e ) {
	 e.printStackTrace();
      }
      return ResultV;
   }
   
   private Collection doQuery(String query) throws BaseException {
      Vector results = null;
      ResultSet rs = null;
      DbManager dbManager = null;
      Connection conn = null;
      try {
         dbManager = new DbManager(this.getClient());
         conn = dbManager.getConnection();
         Statement stmt = conn.createStatement();           
         rs = stmt.executeQuery(query);         
      } catch (DbConnectionException dbce) {
         log.error("Db Connection Exception executing internal order process query: " + dbce);
      } catch (SQLException sqle) {
         log.error("SQL Exception executing internal order process query: " + sqle);
      }      
      if (rs !=null) {
         try {
            results = new Vector(10);         
            while (rs.next()) {
               results.add(new VvItemTypeBean(rs.getString( "ITEM_TYPE" ), rs.getString( "TYPE_DESC" )));
            }
            rs.close();
         } catch (SQLException sqle) {
            log.error("SQL EXception reading results from VViTemTypeBean");
         }
      }
      dbManager.returnConnection(conn);
      return results;      
   }
   
   private class VvItemTypeBean {
      private String itemType;
      private String typeDesc;
      
      VvItemTypeBean() {}
      
      VvItemTypeBean (String type, String desc) {
         this.itemType=type;
         this.typeDesc=desc;
      }
      
      /**
       * Getter for property itemType.
       * @return Value of property itemType.
       */
      public java.lang.String getItemType() {
         return itemType;
      }
      
      /**
       * Setter for property itemType.
       * @param itemType New value of property itemType.
       */
      public void setItemType(java.lang.String itemType) {
         this.itemType = itemType;
      }
      
      /**
       * Getter for property typeDesc.
       * @return Value of property typeDesc.
       */
      public java.lang.String getTypeDesc() {
         return typeDesc;
      }
      
      /**
       * Setter for property typeDesc.
       * @param typeDesc New value of property typeDesc.
       */
      public void setTypeDesc(java.lang.String typeDesc) {
         this.typeDesc = typeDesc;
      }
      
   }
   
   
}
