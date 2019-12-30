
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;


 

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.*;
public class InventTable  {

   protected TcmISDBModel db;
   String expDate,promDate;
   String facility;
   String warehouse;
   String item;
   boolean eD,fac,pD,ware,hosed;

   public InventTable() throws java.rmi.RemoteException {
       
   }

   public InventTable(TcmISDBModel db) throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Hashtable getTableData(int col,String facility,String userFac,String warehouse,
                             String itemIn,String expDate,String promDate,
                             boolean fac,boolean ware,boolean eD,boolean pD,
                             boolean oH, boolean oO) throws Exception {
// CBK start
    String item = HelpObjs.singleQuoteToDouble(itemIn);
// CBK end

    String where = new String("");
    boolean itemON = false;
    boolean facON = false;
    hosed = false;
    Object[][] tableData = null;
    Hashtable result = new Hashtable();
    try {
      if ( item != (String) null && item.length() > 0) {
        ItemSynonym syn = new ItemSynonym(db);
        syn.setSearchString(item);
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
          itemON = true;
        } else {
          hosed = true;
        }
      }
      if (fac) {
        if (itemON) {
          where = where + " and ";
        }
        where = where + " facility_id = '" + facility + "'";
        facON = true;
      }
      if (ware) {
        if (itemON || facON) {
          where = where + " and ";
        }
        where = where + " warehouse = '" + warehouse + "'";
      }
      /*
      if (oO){
         if (ware || itemON || facON) {
           where = where + " and ";
         }
         where = where + " on_order > 0 ";
      }

      if (oH){
         if (oO || ware || itemON || facON) {
           where = where + " and ";
         }
         where = where + " on_hand > 0 ";
      }
      */

      Inventory inv = new Inventory(db);
      String fdate = null;
      String pdate = null;
      String nowdate ;
      SimpleDateFormat simple = new SimpleDateFormat("MM/dd/yyyy");
      simple.setTimeZone(TimeZone.getDefault());
      SimpleDateFormat simple2 = new SimpleDateFormat("yyyyDDD");
      simple2.setTimeZone(TimeZone.getDefault());
      ParsePosition pos = new ParsePosition(0);
      java.util.Date nowD;
      java.util.Date newD;
      Integer days;
      long timeNow = 0;

      try {
          PurchaseRequest PRI = new  PurchaseRequest(db);
          nowdate = PRI.getNowDate();
          nowD = simple.parse(nowdate,pos);
          timeNow = nowD.getTime();

      } catch (Exception e){  throw e;
        
      }
      if (eD) {
          days = new Integer(expDate);
          newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
          fdate  = new String(simple2.format(newD));

          result.put("EXPIRE",BothHelpObjs.formatDate("toCharfromOrder",fdate));
          if (ware || itemON || facON) {
             where = where + " and ";
          }
          where = where + " expire_date <= to_date('"+fdate+"','yyyyDDD') ";
      }
      if (pD) {
          days = new Integer(promDate);
          newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
          pdate  = new String(simple2.format(newD));

          result.put("PROMISSED",BothHelpObjs.formatDate("toCharfromOrder",pdate));
          if (eD || ware || itemON || facON) {
             where = where + " and ";
          }
          //where = where + " promised_date <= to_date('"+pdate+"','yyyyDDD') ";
          where = where + " (promised_date <= to_date('"+pdate+"','yyyyDDD') and rev_prom_date is null ";
          where = where + "or (rev_prom_date like '%%/%%/%%' and to_date(rev_prom_date,'mm/dd/yy') <= to_date('"+pdate+"','yyyyDDD')))";
      }
      if (where.indexOf("=") < 0) {where = "item_id > 0 ";}
      Vector tableRows = new Vector();
      int i;
      if (!hosed) {
      Vector IV = inv.retrieveSum(where,"item_id",fdate,pdate);
      if (IV != null) {
        if (IV.size() > 0) {
          Inventory tinv;
          Integer I = new Integer(0);
          int lastItem = 0;
          int j = -1;
          Object[] data;
          for (i=0;i<IV.size();i++) {
            data = new Object[col];
            tinv = (Inventory)IV.elementAt(i);
            if (lastItem == tinv.getItemId().intValue()) {
              continue;
            }
            if ((oH && tinv.getOnHand().intValue() == 0) && !oO){
              continue;
            }
            if ((oO && tinv.getOnOrder().intValue() == 0) && !oH){
              continue;
            }
            lastItem = tinv.getItemId().intValue();
            j++;
            I = new Integer(j);
            data[0] = tinv.getItemId();
            if (fac || userFac.equals(tinv.getFacilityId())) {
              data[1] = tinv.getFacPartNo();
            } else {
              data[1] = new String();
            }
            data[2] = tinv.getTradeName();
            String packing = new String("");

            if (new Float(tinv.getPartSize()) != null && !(new Float(tinv.getPartSize())).toString().equals("0.0")){
               packing = (new Float(tinv.getPartSize())).toString();
            }
            if (tinv.getSizeUnit() != null){
               packing = packing + " " + tinv.getSizeUnit();
            }
            if (tinv.getPkgStyle() != null){
               packing = packing + " " + tinv.getPkgStyle();
            }
            data[3] = packing;
            data[4] = tinv.getMfgDesc();
            data[5] = tinv.getWarehouse();
            data[6] = tinv.getOnHand();
            data[7] = tinv.getOnOrder();

            Catalog c = new Catalog(db);
            c.setItemId(tinv.getItemId().intValue());
            c.setFacilityId(tinv.getFacilityId());
            c.load();

            data[8] = c.getFacilityId(); // facID
            data[9] = c.getMatId(); // matID
            data[10] = c.getSpecId(); // specID
            data[11] = c.getSpecOn(); // hasSpec
            data[12] = c.getMsdsOn(); // hasMSDS
            tableRows.addElement(data);
          }
        } else {
          hosed = true;
        }
       } else {
        hosed = true;
       }
      }
      if (!hosed) {
        tableData = new Object[tableRows.size()][col];
        for (i=0;i<tableRows.size();i++) {
          tableData[i] = (Object[])tableRows.elementAt(i);
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       
    }
    if (tableData != null) result.put("DATA",tableData);
    return result;
  }

  public Hashtable loadFacs(int userId) throws Exception{
    Hashtable result = new Hashtable();
    try {
      //facs
      Personnel personnel = new Personnel(db);
      personnel.setPersonnelId(userId);
      personnel.load();
      //facs
      Hashtable facXref = (Hashtable) personnel.getRelatedFacilities();
      Hashtable facXware = new Hashtable();
      Facility fac = new Facility(db);
      for (Enumeration E=facXref.keys();E.hasMoreElements();){
          fac.setFacilityId(facXref.get(E.nextElement().toString()).toString());
          fac.load();
          if (fac.getPreferredWare() != null){
            facXware.put(fac.getFacilityId(),fac.getPreferredWare());
          }
      }

      result.put("preferred_ware",facXware);
      
      return result;
    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
       
           
    }
  }

  public Hashtable loadWare() throws Exception {
    Hashtable result = new Hashtable();
    try {
      //inv
      Facility fac = new Facility(db);
      Vector facs = new Vector();
      Hashtable facXref = new Hashtable();
      facs = fac.getAllWarehouseIds();
      facs.trimToSize();
      for (int i=0;i<facs.size();i++){
        fac.setFacilityId((String)facs.elementAt(i));
        fac.load();
        if (fac.getFacilityName() != (String)null){
          facXref.put(fac.getFacilityName(),(String)facs.elementAt(i));
        }
      }
      result.put("warehouse",facXref);
      return result;
    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
       
           
    }
  }

  /*
  public Hashtable getApplication(String fac){
    Hashtable result = new Hashtable();
    try{
      ApplicationView ap =  new ApplicationView(db);
      ap.setFacilityId(fac);
      ap.load();
      Hashtable appXref = new Hashtable();
      Hashtable H = ap.getApplications();
      Enumeration E;
      String key;
      String appLabel;
      if (H.size() > 0) {
         for (E = H.keys();E.hasMoreElements();) {
            key = (String) E.nextElement();
            appLabel = key + "/" + H.get(key).toString();
            appXref.put(appLabel,key); // to be used by basket table
         }
      }

      return appXref;
    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      return null;
    }
  }
  */


}






























