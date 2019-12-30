
package radian.tcmis.server7.share.dbObjs;


 

import java.util.*;
import java.text.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
public class InvFloatScreen  {

   protected TcmISDBModel db;
   String expDate,promDate;
   String facility;
   String warehouse;
   String item;
   boolean eD,fac,pD,ware,hosed;

   public InvFloatScreen() throws Exception {
   }

   public InvFloatScreen(TcmISDBModel db) throws Exception {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Hashtable getTableData(int col1,int col2,String item,String facility,
                              boolean fac,String partnum) throws Exception {
// // System.out.println("********* Here3:"+partnum);
    String where = new String("");
    boolean itemON = false;
    boolean facON = false;
    hosed = false;
    Vector data1 = new Vector();
    Vector data2 = new Vector();
    Object[] dataT1 = null;
    Object[] dataT2 = null;
    Vector temp1 = new Vector();
    try {
      Inventory inv = new Inventory(db);
      Vector IV = inv.getDetail(new Integer(item),facility,fac,partnum);
      int i;
      String tmp = new String();
      if (IV != (Vector)null) {
        if (IV.size() > 0) {
          for (i=0;i<IV.size();i++) {
            dataT1 = new Object[col1];
            dataT2 = new Object[col2];
            inv = new Inventory(db);
            inv = (Inventory)IV.elementAt(i);
            tmp = inv.getExpireDate();
            if (tmp != null) {
               if (tmp.startsWith("199") || tmp.startsWith("20")) {
                  tmp = tmp.substring(0,tmp.indexOf(" "));
                  SimpleDateFormat S = new SimpleDateFormat("yyyy-MM-dd");
                  SimpleDateFormat T = new SimpleDateFormat("MMM dd, yyyy");
                  java.util.Date D = S.parse(tmp,new ParsePosition(0));
                  tmp = T.format(D);
               }
            }

            if (!inv.getOnHand().toString().equals("0")) {
              dataT1[0] = inv.getWarehouse();
              dataT1[1] = inv.getOnHand().toString();
              dataT1[2] = inv.getLotNum();
              dataT1[3] = tmp;
              data1.addElement(dataT1);
            }

            // get last promised date
            tmp = inv.getRevPromDate();
            if(BothHelpObjs.isBlankString(tmp)){
              tmp = inv.getPromiseDate();
            }
            if (tmp != null) {
               if (tmp.startsWith("199") || tmp.startsWith("20")) {
                  tmp = tmp.substring(0,tmp.indexOf(" "));
                  SimpleDateFormat S = new SimpleDateFormat("yyyy-MM-dd");
                  SimpleDateFormat T = new SimpleDateFormat("MMM dd, yyyy");
                  java.util.Date D = S.parse(tmp,new ParsePosition(0));
                  tmp = T.format(D);
               }
            }

            if (!inv.getOnOrder().toString().equals("0")) {
              dataT2 = new Object[col2];
              dataT2[0] = inv.getWarehouse();
              dataT2[1] = inv.getOnOrder().toString();
              dataT2[3] = tmp;
              if (inv.getPONumber().toString().equals("0")) {
                dataT2[2] = new String("");
              } else {
                dataT2[2] = inv.getPONumber();
              }
              data2.addElement(dataT2);
              dataT2 = null;
            }
          }
        } else {
          hosed = true;
        }
      } else {
        hosed = true;
      }
    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
       
           
    }
    Hashtable result = new Hashtable();
    data1.trimToSize();

    Object[][] datao1 = new Object[data1.size()][col1];
    for (int i=0;i<data1.size();i++){
      datao1[i] = (Object[])data1.elementAt(i);
    }
    data2.trimToSize();
    Object[][] datao2 = new Object[data2.size()][col2];
    for (int i=0;i<data2.size();i++){
      datao2[i] = (Object[])data2.elementAt(i);
    }

    result.put("DATA1",datao1);
    result.put("DATA2",datao2);
    return result;
  }

  public String[] getFieldData(int dim,String item,String facility,boolean fac) throws Exception{
    String[] fieldData = new String[dim];
    try {
      Catalog inv = new Catalog(db);
      String where = " item_id = " + item;
      if (fac) {
        where = where + " and facility_id = '" + facility + "'";
      }
      Vector V = inv.retrieve(where,null);
      if (V != null) {
        if (V.size() > 0) {
          inv = (Catalog)V.elementAt(0);
          fieldData[0] = inv.getMaterialDesc();
          fieldData[1] = inv.getMfgDesc();
          if (fac) {
            fieldData[2] = inv.getFacItemId();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      
           
    }
    return fieldData;
  }


}






























