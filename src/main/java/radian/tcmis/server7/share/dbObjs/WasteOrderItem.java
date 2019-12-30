package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
/*
SQLWKS> desc waste_order_item
------------------------------ -------- ----
ORDER_NO                       NOT NULL NUMBER(38)
ITEM_ID                        NOT NULL NUMBER(38)
QUANTITY                                NUMBER(38)
PRICE                                   NUMBER
LAST_PRICE_PAID                         NUMBER
SHIPMENT_ID                             NUMBER(38)
CHARGE_TYPE                             CHAR(1)
GENERATION_POINT                        VARCHAR2(30)
ACCOUNT_SYS_ID                          VARCHAR2(12)
*/

import java.util.*;
import java.sql.*;

public class WasteOrderItem {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected String orderNo;
   protected String itemId;
   protected String qty;
   protected String price;
   protected String lpp;
   protected String shipmentId;
   protected String chargeType;
   protected String generationPt;
   protected String actSysId;
   protected String vendorId;

   public WasteOrderItem(TcmISDBModel db){
      this.db = db;
   }
   public WasteOrderItem(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public String getOrderNo(){return orderNo;}
   public String getItemId(){return itemId;}
   public String getQty(){return qty;}
   public String getPrice(){return price;}
   public String getLpp(){return lpp;}
   public String getShipmentId(){return shipmentId;}
   public String getChargeType(){return chargeType;}
   public String getGenerationPt(){return generationPt;}
   public String getActSysId(){return actSysId;}
   public String getVendorId(){return vendorId;}

   // set Methods
   public void setOrderNo(String s){orderNo = s;}
   public void setItemId(String s){itemId = s;}
   public void setQty(String s){qty = s;}
   public void setPrice(String s){price = s;}
   public void setLpp(String s){lpp = s;}
   public void setShipmentId(String s){shipmentId = s;}
   public void setChargeType(String s){chargeType = s;}
   public void setGenerationPt(String s){generationPt = s;}
   public void setActSysId(String s){actSysId = s;}
   public void setVendorId(String s){vendorId = s;}

   public Hashtable getScreenData() {
    Hashtable result = new Hashtable();
    /*
    WasteRequest wr = new WasteRequest(db);
    wr.setWasteRequestId(reqNum);
    wr.load();

    // load the head Hashtable
    header.put("REQ_NUM",new Integer(reqNum));
    header.put("ACC_SYS_ID",wr.getAccSysId());

    // requestor info
    header.put("REQ_ID",new Integer(wr.getRequesterId()));
    Personnel p = new Personnel(db);
    p.setPersonnelId(wr.getRequesterId());
    p.load();
    header.put("REQ_NAME",p.getFullName());

    // date requested and status
    String s = wr.getRequestDate();
    String dr = "";   //*********** get current date.
    dr = wr.getNowDate();
    dr = BothHelpObjs.formatDate("toCharfromNum",dr);
    String sd = "";
    String viewType = "VIEW";
    if(BothHelpObjs.isBlankString(s)){
      sd = "Draft";
      viewType = "REQUEST";
    }else{
      sd = "Submitted";
      dr = BothHelpObjs.formatDate("toCharfromDB",s);
    }

    header.put("DATE",dr);
    header.put("STATUS_DESC",sd);
    header.put("VIEW_TYPE",viewType);

    header.put("QTY_COL",new Integer(WR_QTY_COL));

    WasteRequestLineItem wrli = new WasteRequestLineItem(db);
    wrli.setWasteRequestId(wr.getWasteRequestId());
    wrli.load();

    Facility f = new Facility(db);
    WasteProfile wastep = new WasteProfile(db); //*******
    wastep.setWasteItemId(wrli.getWasteItemId());
    wastep.load();
    f.setFacilityId(wr.getFacId());    //******
    f.load();
    header.put("FAC_NAME",f.getFacilityName());
    header.put("FAC_ID",wr.getFacId());
    header.put("FACILITY_ID",wr.getFacId());

    //trong 4/17
    PrRules pr1 = new PrRules(db);
    pr1.setFacilityId(wr.getFacId());
    pr1.setAccountSysId(wr.getAccSysId());
    pr1.load();
    header.put("ACC_TYPE_H",pr1.getAccTypeH());
    Hashtable pack = pr1.packAccSysType(header);
    header.put("PACK",pack);   */


    return result;
   }

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update waste_order_item set " + col + "=");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.intValue();
         break;
       case DATE:
         if (val.equals("nowDate")){
           query = query + " SYSDATE";
         } else {
           query = query + " to_date('"+val+"','MM/dd/yyyy HH24:MI:SS')";
         }
         break;
       case STRING:
         query = query + "'" + val + "'";
         break;
       case NULLVAL:
         query = query + null;
         break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query += " where order_no = "+getOrderNo();
     query += " and item_id = "+getItemId();
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
   }


  public void delete() throws Exception {
    String query = "delete waste_order_item where order_no = '"+getOrderNo()+"'";
    DBResultSet dbrs = null;
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      } finally{}
      return;
   }

   public void deleteLineItem() throws Exception {
    String query = "delete waste_order_item where order_no = '"+getOrderNo()+"'";
    query += " and item_id = '"+getItemId()+"'";
    DBResultSet dbrs = null;
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      } finally{}
      return;
   }


  public void load()throws Exception{
     String query = "select * from waste_order_item where order_no = '"+getOrderNo()+"'";
     query += " and item_id = '"+getItemId()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
          setQty(BothHelpObjs.makeBlankFromNull(rs.getString("quantity")));
          setPrice(BothHelpObjs.makeBlankFromNull(rs.getString("price")));
          setLpp(BothHelpObjs.makeBlankFromNull(rs.getString("last_price_paid")));
          setShipmentId(BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id")));
          setGenerationPt(BothHelpObjs.makeBlankFromNull(rs.getString("generation_point")));
          setChargeType(BothHelpObjs.makeBlankFromNull(rs.getString("charge_type")));
          setActSysId(BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public void getWasteOrderItem()throws Exception{
     String query = "select * from waste_order_item where order_no = '"+getOrderNo()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
          setQty(BothHelpObjs.makeBlankFromNull(rs.getString("quantity")));
          setPrice(BothHelpObjs.makeBlankFromNull(rs.getString("price")));
          setLpp(BothHelpObjs.makeBlankFromNull(rs.getString("last_price_paid")));
          setShipmentId(BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id")));
          setGenerationPt(BothHelpObjs.makeBlankFromNull(rs.getString("generation_point")));
          setChargeType(BothHelpObjs.makeBlankFromNull(rs.getString("charge_type")));
          setActSysId(BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public Vector getItemFromOrder()throws Exception{
     String query = "select item_id from waste_order_item where order_no = '"+getOrderNo()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     Vector v = new Vector();
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        v.addElement((String)BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
     return v;
   }

   public void createWasteOrderItem2()throws Exception{
     ResultSet rs = null;
     try{
       String query = "insert into waste_order_item ";
       query = query + "(order_no,item_id,quantity,customer_price,shipment_id,account_sys_id) ";
       query = query + "values ("+getOrderNo()+","+getItemId()+","+getQty()+",'"+getPrice()+"',"+getShipmentId()+",'"+getActSysId()+"')";
       db.doUpdate(query);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }
   }
   public void createWasteOrderItem()throws Exception{
     ResultSet rs = null;
     try{
       String query = "insert into waste_order_item ";
       query = query + "(order_no,item_id,quantity,price,last_price_paid,customer_price,shipment_id,account_sys_id) ";
       query = query + "select "+getOrderNo()+","+getItemId()+","+getQty()+",current_price,last_price_paid,'"+getPrice()+"',"+getShipmentId()+",'"+getActSysId()+"'";
       query = query + " from waste_vendor_item where vendor_id = '"+getVendorId()+"' and item_id = '"+getItemId()+"'";
       //System.out.println("\n\n--------- insert query: "+query);
       db.doUpdate(query);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }
   }

   public Hashtable createWasteOrderItemRequest(Hashtable inData)throws Exception {
    Hashtable result = new Hashtable();
    boolean changes = false;
    try {
      Integer orderNo = (Integer)inData.get("ORDER_NO");
      String shipmentId = (String)inData.get("SHIPMENT_ID");
      String actSysId = (String)inData.get("ACT_SYS_ID");
      String vendor = (String)inData.get("VENDOR_ID");
      setActSysId(actSysId);
      setOrderNo(orderNo.toString());
      setShipmentId(shipmentId);
      setVendorId(vendor);

      Hashtable lineItemInfoH = (Hashtable)inData.get("LINE_ITEM_INFO");
      Vector itemIDsV = (Vector)lineItemInfoH.get("ITEM_IDS");
      Vector qtyV = (Vector)lineItemInfoH.get("QTY");
      Vector priceV = (Vector)lineItemInfoH.get("PRICE");
      //contains charge info for each item
      Hashtable chargeNumbers = (Hashtable)inData.get("CHARGE_NUMBERS");
      //account sys info
      Hashtable actSysInfo = (Hashtable)inData.get("ACT_SYS_INFO");
      actSysInfo.put("ACC_SYS_ID",actSysId);

      Vector existingItemIDs = getItemFromOrder();

      //System.out.println("\n\n---- got here: "+existingItemIDs);
      for (int i = 0; i < itemIDsV.size(); i++) {
        String itemID = (String)itemIDsV.elementAt(i);
        String qty = (String)qtyV.elementAt(i);
        setItemId(itemID);
        if (existingItemIDs.contains(itemID)) {              //Ship if item already on the given order
          //update qty for existing item
          insert("quantity",qty,WasteOrderItem.INT);

          //11-06-01 allow the user to change charge number and credit card info
          //first delete record from wi_account
          WiAccount wia = new WiAccount(db);
          wia.setOrderNo(orderNo.toString());
          wia.setItemID(itemID);
          wia.deleteLineItem();
          //checking charge number and insert charge number as well as credit card info
          Hashtable currentChargeInfoH = (Hashtable)chargeNumbers.get(itemID);
          boolean bb = checkChargeCreditCardNumber(currentChargeInfoH,actSysInfo,actSysId,(Hashtable)inData.get("CREDIT_CARD_INFO"));
          //Vector cv = (Vector)chargeNumbers.get(itemID);
          //boolean bb = checkChargeCreditCardNumber(cv,actSysInfo,actSysId,(Hashtable)inData.get("CREDIT_CARD_INFO"));
          if (bb) {
            result.put("SUCCEED",new Boolean(true));
            result.put("LIST_CHANGED",new Boolean(true));
          }else {
            result.put("SUCCEED",new Boolean(false));
            result.put("MSG","Invalid charge number.");
            result.put("ERROR_LINE",new Integer(i));
            break;
          }
          continue;
        }

        changes = true;
        String price = (String)priceV.elementAt(i);
        setQty(qty);
        setPrice(price);

        //create waste order item
        createWasteOrderItem();
        //checking charge number
        Hashtable currentChargeInfoH = (Hashtable)chargeNumbers.get(itemID);
        boolean b = checkChargeCreditCardNumber(currentChargeInfoH,actSysInfo,actSysId,(Hashtable)inData.get("CREDIT_CARD_INFO"));
        //Vector cv = (Vector)chargeNumbers.get(itemID);
        //boolean b = checkChargeCreditCardNumber(cv,actSysInfo,actSysId,(Hashtable)inData.get("CREDIT_CARD_INFO"));
        if (b) {
          //System.out.println("\n\n========== everything ok");
          result.put("SUCCEED",new Boolean(true));
          result.put("LIST_CHANGED",new Boolean(true));
        }else {
          //delete waste order item record
          deleteLineItem();
          result.put("SUCCEED",new Boolean(false));
          result.put("MSG","Invalid charge number.");
          result.put("ERROR_LINE",new Integer(i));
          break;
        }
      }
      //if there is no changes
      if (!changes) {
        result.put("SUCCEED",new Boolean(true));
        result.put("LIST_CHANGED",new Boolean(false));
      }

    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return result;
  } //end of method

  boolean checkChargeCreditCardNumber(Hashtable currentChargeInfo, Hashtable actSysInfo, String actSysID, Hashtable creditCardInfo) {
    String cType = "";
    String accumPt = "";
    String defaultPo = "";
    try {
      Vector cv = (Vector)currentChargeInfo.get("CHARGE_NUMBER");
      cType = (String)currentChargeInfo.get("CHARGE_TYPE");
      accumPt = (String)currentChargeInfo.get("ACCUMULATION_PT");
      defaultPo = (String)currentChargeInfo.get("DEFAULT_PO");
      //determine whether to insert charge number or credit card
      Hashtable tmpH = (Hashtable)actSysInfo.get(actSysID+cType);
      String needPo = (String)tmpH.get("PO_REQUIRED");
      String prRequired = (String)tmpH.get("PR_ACCOUNT_REQUIRED");
      //System.out.println("\n\n------ need PO: "+needPo);
      if ("y".equalsIgnoreCase(prRequired)) {
        for(int j=0;j<cv.size();j++){
          Hashtable cHash = (Hashtable)cv.elementAt(j);
          if(!cHash.containsKey("PERCENTAGE") ||
            cHash.get("PERCENTAGE") == null ||
            BothHelpObjs.isBlankString(cHash.get("PERCENTAGE").toString())){
            continue;
          }
          ChargeNumber myCN = new ChargeNumber(db);
          String pct = (String)cHash.get("PERCENTAGE");
          myCN.setChargeType(cType);
          myCN.setAccountNumber(cHash.get("ACT_NUM_1").toString());
          if(!cHash.containsKey("ACT_NUM_2") ||
            cHash.get("ACT_NUM_2") == null){
            myCN.setAccountNumber2("");
          }else{
            myCN.setAccountNumber2(cHash.get("ACT_NUM_2").toString());
          }
          Vector vv = myCN.checkWasteMaterialChargeNumber(myCN,actSysInfo);
          Boolean val1 = (Boolean)vv.elementAt(0);
          Boolean val2 = (Boolean)vv.elementAt(1);
          if (!val1.booleanValue() || !val2.booleanValue()) {
            return false;
          }else {
            WiAccount wia = new WiAccount(db);
            wia.setOrderNo(this.getOrderNo());
            wia.setItemID(this.getItemId());
            wia.setAccountNumber(myCN.getAccountNumber());
            if (!BothHelpObjs.isBlankString(myCN.getAccountNumber2())) {
              wia.setAccountNumber2(myCN.getAccountNumber2());
            }
            wia.setPct(pct);
            wia.setChargeType(cType);
            wia.createWiAccount();
          }
        }
      }   //end of account required

      if ("c".equalsIgnoreCase(needPo)) {
        insertCreditCardInfo(creditCardInfo);
      }else if ("c".equalsIgnoreCase(needPo)) {
        insertPoNumber(defaultPo);
      }
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    try {
      //now update charge type and accumulation point for each
      this.insert("charge_type",cType,0);
      this.insert("generation_point",accumPt,0);
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  } //end of method

  void insertCreditCardInfo(Hashtable creditCardInfo) throws Exception {
    String query = "update waste_order_item set credit_card_number = '"+(String)creditCardInfo.get("CREDIT_CARD_NUMBER")+"',";
    query += "credit_card_type = '"+(String)creditCardInfo.get("CREDIT_CARD_TYPE")+"',";
    query += "credit_card_expiration_date = to_date('"+(String)creditCardInfo.get("CREDIT_CARD_EXP_DATE")+"','MM/DD/YYYY HH24:MI:SS'),";
    query += "credit_card_name = '"+(String)creditCardInfo.get("CREDIT_CARD_NAME")+"' ";
    query += "where order_no = "+this.getOrderNo()+ " and item_id = "+this.getItemId();
    try {
      db.doUpdate(query);
    }catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

  void insertPoNumber(String poNumber) throws Exception {
    String query = "update waste_order_item set po_number = '"+poNumber+"' "+
                   "where order_no = "+this.getOrderNo()+ " and item_id = "+this.getItemId();
    try {
      db.doUpdate(query);
    }catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

}  //end of class
