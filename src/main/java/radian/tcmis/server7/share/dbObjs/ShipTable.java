
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.db.*;
 

import java.util.*;
import java.sql.*;

public class ShipTable  {

   protected TcmISDBModel db;
   String item;
   boolean hosed;

   public ShipTable() throws java.rmi.RemoteException {
       
   }

   public ShipTable(TcmISDBModel db) throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Object [][] getTableData(int col,String itemStr,String facility,boolean item,boolean fac) throws Exception{
    String where = new String("");
    hosed = false;
    Object[][] tableData = null;
    try {
      if (item) {
        ItemSynonym syn = new ItemSynonym(db);
        syn.setSearchString(itemStr);
        Vector synV = syn.load();
        if (synV.size() > 0) {
          int j;
          where = where + "(";
          for (j=0;j<synV.size();j++) {
            where = where + "item_id = " + synV.elementAt(j).toString();
            if (j < synV.size() - 1) {
              where = where + " or ";
            }
          }
          where = where + ")";
        } else {
          hosed = true;
        }
      }
      if (!hosed) {
        if (fac) {
          if (item) {
            where = where + " and ";
          }
          where = where + " facility_id = '" + facility + "'";
        }
        if (where.indexOf("=") < 0) {where = where + " item_id > 0 ";}
        Catalog ship = new Catalog(db);
        Vector SH = ship.retrieve(where,(String)null);
        int i;
        tableData = new Object[SH.size()][col];
        if (SH != (Vector)null) {
          if (SH.size() > 0) {
            Catalog tship;
            Integer I = new Integer(0);
            for (i=0;i<SH.size();i++) {
              tship = (Catalog)SH.elementAt(i);
              tableData[i][0] = tship.getItemId();
              tableData[i][1] = tship.getFacItemId();
              tableData[i][2] = tship.getMaterialDesc();
              tableData[i][3] = new Float(tship.getPartSize());
              tableData[i][4] = tship.getSizeUnit();
              tableData[i][5] = tship.getPkgStyle();
              tableData[i][6] = tship.getMfgDesc();
            }
          } else {
            hosed = true;
          }
        } else {
          hosed = true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null); throw e;
       }
    return tableData;
  }

  public Hashtable getFacChoice() throws Exception{
    Vector facs = new Vector();
    Hashtable facXref = new Hashtable();
    try {
      Facility fac = new Facility(db);
      facs = fac.getAllFacilityIds();
      facs.trimToSize();
      int i;
      for (i=0;i<facs.size();i++){
        fac.setFacilityId((String)facs.elementAt(i));
        fac.load();
        if (fac.getFacilityName() != (String)null){
          facXref.put(fac.getFacilityName(),(String)facs.elementAt(i));
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null); throw e;
       } 
    facXref.put(new String(""),new String(""));
    return facXref;
  }

  public boolean hasShippingAir(Integer item)  throws Exception {
    String query = "select item_id from air_shipping_view where item_id = " + item.toString();

    DBResultSet dbrs = null;
      ResultSet rs = null;
    int flag =0;
    try {
      dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
      while (rs.next()){
        flag =  1;
        break;
      }
      
      
      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);       
  throw e;
       } finally{
             dbrs.close();
            }
      return flag > 0;

  }

  public boolean hasShippingLand(Integer item)  throws Exception {
    String query = "select item_id from land_shipping_view where item_id = " + item.toString();

    DBResultSet dbrs = null;
      ResultSet rs = null;
    int flag =0;
    try {
      dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
      while (rs.next()){
        flag =  1;
        break;
      }
      
     
       } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null); 
  throw e;
       } finally{
             dbrs.close();
             }
      return flag > 0;

  }



}





























