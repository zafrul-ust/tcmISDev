package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;
import java.text.*;

public class WasteMaterialRequest {

  protected TcmISDBModel db;
  protected String facilityID = "";

  String directed_d = "";
  String directed_i = "";
  boolean allDirected = false;

  //requested Waste Material
  final String[] colH = {" ","Delete","Item ID","Vendor Part No","Qty","Description","Packaging","Specification","Price","Account Sys"};
  final int[] colT = {BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING};
  final int[] colW = {20,40,60,120,50,255,100,100,80,0};
  final int LINE_COL = 0;
  final int DELETE_COL = 1;
  final int ITEM_COL = 2;
  final int PART_NO_COL = 3;
  final int QTY_COL = 4;
  final int DESC_COL = 5;
  final int PACKAGING_COL = 6;
  final int DOT_COL = 7;
  final int LPP_COL = 8;
  final int ACT_COL = 9;

  final String[] catalogColH = {"Qty","Order","Item ID","Vendor Part No","Description","Packaging","Specification","Price","Pick"};
  final int[] catalogColT = {BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                        BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                        BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING};
  final int[] catalogColW = {40,40,60,90,215,105,100,60,0};

  public void setFacilityId(String val) {
    this.facilityID = val;
  }
  public String getFacilityId() {
    return this.facilityID;
  }

  public WasteMaterialRequest(TcmISDBModel db){
      this.db = db;
  }
  public void setDb(TcmISDBModel db){
      this.db = db;
  }

  public Vector getAccountSysID(String facID) throws Exception{
    String query = "select account_sys_id from pr_rules where facility_id = '"+facID+"' and status = 'A'";
    Vector result = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        result.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}

    return result;
  }

  public Vector getWasteMaterialCatalog2(String vendorID, Vector currentItem) throws Exception{
    String query = "select * from waste_vendor_item_view where vendor_id = '"+vendorID+"'";
    Vector result = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String where = "";
    for (int i = 0; i < currentItem.size(); i++) {
      where += (String)currentItem.elementAt(i) + ",";
    }
    //removing the last commas
    if (where.length() > 1) {
      where = where.substring(0,where.length()-1);
      query += " and item_id not in ("+where+")";
    }

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Hashtable h = new Hashtable();
        int count = 0;
        h.put(new Integer(count++),BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        h.put(new Integer(count++),BothHelpObjs.makeBlankFromNull(rs.getString("vendor_part_no")));
        h.put(new Integer(count++),BothHelpObjs.makeBlankFromNull(rs.getString("description")));
        h.put(new Integer(count++),BothHelpObjs.makeBlankFromNull(rs.getString("packaging")));
        h.put(new Integer(count++),BothHelpObjs.makeBlankFromNull(rs.getString("current_price")));
        h.put(new Integer(count++),new Boolean(false));
        result.addElement(h);
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}

    return result;
  }

  public Hashtable getWasteMaterialCatalog(String vendorID,Vector currentItem) throws Exception{
    String query = "select * from waste_vendor_item_view where vendor_id = '"+vendorID+"'";
    Hashtable result = new Hashtable();
    Vector dataV = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String where = "";
    for (int i = 0; i < currentItem.size(); i++) {
      where += (String)currentItem.elementAt(i) + ",";
    }
    //removing the last commas
    if (where.length() > 1) {
      where = where.substring(0,where.length()-1);
      query += " and item_id not in ("+where+")";
    }

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa = new Object[catalogColH.length];
        oa[0] = new Integer(1);
        oa[1] = "";
        oa[2] = new Integer(BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        oa[3] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_part_no"));
        oa[4] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
        oa[5] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        oa[6] = BothHelpObjs.makeBlankFromNull(rs.getString("spec_id"));
        Double lpp = null;
        String temp = BothHelpObjs.makeBlankFromNull(rs.getString("customer_price"));
        if (BothHelpObjs.isBlankString(temp)) {
          lpp = new Double("0.00");
          temp = "0.00";
        }else {
          int dot = temp.indexOf(".");
          if (dot > 0) {
            String tmp2 = temp.substring(dot);
            if (tmp2.length() >= 3) {
              temp = temp.substring(0,dot+3);
            }else {
              temp += "0";
            }
          }else {
            temp += ".00";
          }
          lpp = new Double(temp);
        }
        oa[7] = lpp;
        //oa[7] = new Float(BothHelpObjs.makeBlankFromNull(rs.getString("customer_price")));
        oa[8] = new Boolean(false);
        dataV.addElement(oa);
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("TABLE_HEADERS",catalogColH);
    result.put("TABLE_WIDTHS",catalogColW);
    result.put("TABLE_TYPES",catalogColT);
    dataV.trimToSize();
    result.put("TABLE_DATA",dataV);
    result.put("KEY_COLS",getKeyCol(catalogColH));
    return result;
  }

  public boolean wasteOrderItemExist(int orderN) {
    String query = "select count(*) from waste_order_item where order_no = "+orderN;
    int i = 0;
    try {
      i = DbHelpers.countQuery(db,query);
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
    return (i > 0);
  }

  public Vector getActSysID(String facID)throws Exception {
    String query = "select distinct(account_sys_id) from pr_rules where facility_id = '"+facID+ "' and status = 'A'";
    Vector v = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        v.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
      }
    }catch(Exception e) {
      e.printStackTrace();
      return v;
    }
    return v;
  }

  public Hashtable getRequestedWasteMaterial(Integer orderN) throws Exception{
    Hashtable header = new Hashtable();
    header.put("ORDER_NO",orderN);

    //getting facility, storage_location for given order
    WasteOrder wo = new WasteOrder(db);
    wo.setOrderNo(orderN.intValue());
    wo.load();
    header.put("FACILITY_ID",wo.getFacilityId());

    //Credit Card Info
    Hashtable ccH = new Hashtable();
    //credit card type
    Vector ccV = new Vector();
    ccV.addElement("American Express");
    ccV.addElement("Discover Card");
    ccV.addElement("Master Card");
    ccV.addElement("Visa");
    ccH.put("CREDIT_CARD_TYPE_DESC",ccV);
    Vector ccTID = new Vector();
    ccTID.addElement("American");
    ccTID.addElement("Discover");
    ccTID.addElement("Master");
    ccTID.addElement("Visa");
    ccH.put("CREDIT_CARD_TYPE_ID",ccTID);
    // 11-05-01 since the credit card info. is store in waste_order_item and we can only
    //charge the entire request to one credit card the following lines will be fill with waste_order_item_view
    //otherwise left it blank
    ccH.put("CREDIT_CARD_TYPE","");
    ccH.put("CREDIT_CARD_NUMBER","");
    ccH.put("CREDIT_CARD_EXP_DATE","");
    ccH.put("CREDIT_CARD_NAME","");
    header.put("CREDIT_CARD_INFO",ccH);

    header.put("WASTE_LOCATION_ID",wo.getStorageLocationId());
    //determine whether there is a waste material request for given order No
    if (wasteOrderItemExist(orderN.intValue())) {
      WasteOrderItem woi = new WasteOrderItem(db);
      woi.setOrderNo(orderN.toString());
      woi.getWasteOrderItem();
      Vector v = new Vector();
      v.addElement(woi.getActSysId());
      header.put("ACT_SYS_ID",v);
    }else {                                    //else get account sys for given facility
      Vector v = getActSysID((String)header.get("FACILITY_ID"));
      header.put("ACT_SYS_ID",v);
    }
    Vector vv = (Vector)header.get("ACT_SYS_ID");
    WastePrRules pr = new WastePrRules(db);
    for (int i = 0; i < vv.size(); i++) {
      String actSys = (String)vv.elementAt(i);
      String fac = (String)header.get("FACILITY_ID");
      pr.setFacilityId(fac);
      pr.setAccountSysId(actSys);
      pr.load();
      header.put("ACC_TYPE_H",pr.getAccTypeH());       //old structure, only supporting on accounting system at a time
      header.put("ACT_TYPE_H"+actSys,pr.getAccTypeH());
      header.put("ACC_SYS_ID",actSys);
      header.put("PACK"+actSys,pr.packAccSysType(header));

      Hashtable lHash = new Hashtable();
      getDirectedChargeTypeInfo(lHash,header);
      setDirectedChargeType(lHash,header);

      Vector iCnV = new Vector();
      Vector dCnV = new Vector();
      boolean canEditD = false;
      boolean canEditI = false;
      if (!allDirected) {                 //only uses directed charge number
        // get pre defined charge numbers
        ChargeNumber cn = new ChargeNumber(db);
        Vector cnV = cn.getChargeNumsForFacNew(header,"Waste");
        for(int r=0;r<cnV.size();r++){
          ChargeNumber myCN = (ChargeNumber)cnV.elementAt(r);
          Hashtable cnh = new Hashtable();
          cnh.put("ACT_NUM_1",myCN.getAccountNumber());
          cnh.put("ACT_NUM_2",myCN.getAccountNumber2());
          cnh.put("PERCENTAGE","");
          //cnh.put("CHARGE_TYPE",myCN.getChargeType());

          if(myCN.getChargeType().equalsIgnoreCase("i")){
            iCnV.addElement(cnh);
          }else if(myCN.getChargeType().equalsIgnoreCase("d")){
            dCnV.addElement(cnh);
          }
        }
        //6-22-01 this will handle editing of direct/indirect all combination
        Hashtable tempH = (Hashtable)header.get("ACC_TYPE_H");
        Hashtable tempH2 = (Hashtable)tempH.get((String)header.get("ACC_SYS_ID"));
        Vector display1 = (Vector)tempH2.get("DISPLAY_1");
        Vector display2 = (Vector)tempH2.get("DISPLAY_2");
        Vector chargeType = (Vector)tempH2.get("CHARGE_TYPE");
        for (int ii = 0; ii < chargeType.size(); ii++) {
          if (chargeType.elementAt(ii).toString().equalsIgnoreCase("i")) {
            if (display1.elementAt(ii).toString().equalsIgnoreCase("y")) {
              canEditI = iCnV.size() == 0;
            }else {         //if display is 'n' then allows edit
              canEditI = true;
            }
          }else {
            if (display1.elementAt(ii).toString().equalsIgnoreCase("y")) {
              canEditD = dCnV.size() == 0;
            }else {       //if display is 'n' then allows edit
              canEditD = true;
            }
          }
        }
      }else {
        canEditD = false;
        canEditI = false;
      }

      if (directed_d.equalsIgnoreCase("Y")) {
        lHash.put("CAN_EDIT_CHARGE_NUM_D",new Boolean(false));
      }else {
        lHash.put("CAN_EDIT_CHARGE_NUM_D",new Boolean(canEditD));
      }
      if (directed_i.equalsIgnoreCase("Y")) {
        lHash.put("CAN_EDIT_CHARGE_NUM_I",new Boolean(false));
      }else {
        lHash.put("CAN_EDIT_CHARGE_NUM_I",new Boolean(canEditI));
      }

      lHash.put("CHARGE_NUM_DIRECT",dCnV);
      lHash.put("CHARGE_NUM_INDIRECT",iCnV);

      header.put("CHARGE_NUMBER"+actSys,lHash);
    }
    //removing old structure key
    header.remove("ACC_TYPE_H");
    header.remove("ACC_SYS_ID");


    String query = "select * from waste_order_item_view where order_no = "+orderN;
    Hashtable result = new Hashtable();
    Hashtable chargeNumberH = new Hashtable();
    Vector dataV = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int count = 1;
      String lastItem = "";
      while(rs.next()){
        String currentItem = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        if (!currentItem.equalsIgnoreCase(lastItem)) {
          Object[] oa = new Object[colH.length];
          oa[LINE_COL] = new Integer(count++);
          oa[DELETE_COL] = new Boolean(false);
          oa[ITEM_COL] = currentItem;
          oa[PART_NO_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_part_no"));
          oa[QTY_COL] = new Integer(BothHelpObjs.makeBlankFromNull(rs.getString("quantity")));
          oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
          oa[PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
          oa[DOT_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("spec_id"));;
          Double lpp = null;
          String temp = BothHelpObjs.makeBlankFromNull(rs.getString("customer_price"));
          if (BothHelpObjs.isBlankString(temp)) {
            lpp = new Double("0.00");
            temp = "0.00";
          }else {
            int dot = temp.indexOf(".");
            if (dot > 0) {
              String tmp2 = temp.substring(dot);
              if (tmp2.length() >= 3) {
                temp = temp.substring(0,dot+3);
              }else {
                temp += "0";
              }
            }else {
              temp += ".00";
            }
            lpp = new Double(temp);
          }

          oa[LPP_COL] = lpp;
          oa[ACT_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
          dataV.addElement(oa);

          Hashtable hh = new Hashtable(4);
          Vector v = new Vector();
          Hashtable innerH = new Hashtable();
          innerH.put("ACT_NUM_1",BothHelpObjs.makeBlankFromNull(rs.getString("account_number")));
          innerH.put("ACT_NUM_2",BothHelpObjs.makeBlankFromNull(rs.getString("account_number2")));
          innerH.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull(rs.getString("percentage")));
          v.addElement(innerH);
          hh.put("CHARGE_NUMBER",v);
          hh.put("CHARGE_TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("charge_type")));
          hh.put("ACCUMULATION_PT",BothHelpObjs.makeBlankFromNull(rs.getString("generation_point")));
          hh.put("DEFAULT_PO","");
          chargeNumberH.put(currentItem,hh);

          //11-05-01 credit card info. is the same for all item
          if (!BothHelpObjs.isBlankString(rs.getString("credit_card_number"))) {
            ccH.put("CREDIT_CARD_TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_type")));
            ccH.put("CREDIT_CARD_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_number")));
            ccH.put("CREDIT_CARD_EXP_DATE",BothHelpObjs.formatDate("toNumfromChar",BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_expiration_date")))));
            ccH.put("CREDIT_CARD_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_name")));
            header.put("CREDIT_CARD_INFO",ccH);
          }
        }else {
          Hashtable hh = (Hashtable)chargeNumberH.get(currentItem);
          Vector v = (Vector)hh.get("CHARGE_NUMBER");
          Hashtable innerH = new Hashtable();
          innerH.put("ACT_NUM_1",BothHelpObjs.makeBlankFromNull(rs.getString("account_number")));
          innerH.put("ACT_NUM_2",BothHelpObjs.makeBlankFromNull(rs.getString("account_number2")));
          innerH.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull(rs.getString("percentage")));
          v.addElement(innerH);
          hh.put("CHARGE_NUMBER",v);
          chargeNumberH.put(currentItem,hh);

          //11-05-01 credit card info. is the same for all item
          if (!BothHelpObjs.isBlankString(rs.getString("credit_card_number"))) {
            ccH.put("CREDIT_CARD_TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_type")));
            ccH.put("CREDIT_CARD_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_number")));
            ccH.put("CREDIT_CARD_EXP_DATE",BothHelpObjs.formatDate("toNumfromChar",BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_expiration_date")))));
            ccH.put("CREDIT_CARD_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_name")));
            header.put("CREDIT_CARD_INFO",ccH);
          }
        }
        lastItem = currentItem;
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("TABLE_HEADERS",colH);
    result.put("TABLE_WIDTHS",colW);
    result.put("TABLE_TYPES",colT);
    dataV.trimToSize();
    result.put("TABLE_DATA",dataV);
    result.put("KEY_COLS",getKeyCol(colH));
    //System.out.println("\n\n------------ charge number info: "+chargeNumberH);
    result.put("HEADER_INFO",header);
    result.put("CHARGE_NUMBERS",chargeNumberH);
    return result;
  }

  String formatPrice(double d){
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    nf.setMinimumIntegerDigits(1);
    return nf.format(d).toString();
  }


   public void setDirectedChargeType(Hashtable lHash, Hashtable header) {
    Hashtable h1 = (Hashtable)header.get("ACC_TYPE_H");
    Hashtable innerH = (Hashtable)h1.get((String)header.get("ACC_SYS_ID"));
    Vector ct = (Vector)innerH.get("CHARGE_TYPE");
    String sql = "select distinct charge_type from waste_directed_charge where facility_id = '"+(String)header.get("FACILITY_ID")+"'"+
                 " and account_sys_id = '"+(String)header.get("ACC_SYS_ID")+"' and waste_location_id = '"+(String)header.get("WASTE_LOCATION_ID")+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String tmpChargeType = "";
    try {
     dbrs = db.doQuery(sql);
     rs = dbrs.getResultSet();
     while(rs.next()) {
      tmpChargeType = rs.getString("charge_type");
     }
    }catch (Exception ee) {
      ee.printStackTrace();
      tmpChargeType = "d";
    }finally {
      dbrs.close();
    }
    //just in case
    if (tmpChargeType.length() < 1) {
      lHash.put("CHARGE_TYPE","d");
    }else {
      lHash.put("CHARGE_TYPE",tmpChargeType);
    }

    //whether or not getting pre-define charge number
    String query = "select count(*) from waste_directed_charge wdc, waste_location wl"+
              " where wl.facility_id = wdc.facility_id(+) and wl.waste_location_id = wdc.waste_location_id(+) and"+
              " wdc.waste_location_id is null and wl.facility_id = '"+(String)header.get("FACILITY_ID")+"' and wl.status = 'A'";
    try {
      allDirected = HelpObjs.countQuery(db,query) == 0;
    }catch (Exception e) {
      e.printStackTrace();
      allDirected = false;
    }
   }

   public void setDirectedChargeTypeOld(Hashtable lHash, Hashtable header) {
    Hashtable h1 = (Hashtable)header.get("ACC_TYPE_H");
    Hashtable innerH = (Hashtable)h1.get((String)header.get("ACC_SYS_ID"));
    Vector ct = (Vector)innerH.get("CHARGE_TYPE");
    directed_d = "";
    directed_i = "";
    for (int i = 0; i < ct.size(); i++) {
      String myType = (String)ct.elementAt(i);
      if (myType.equalsIgnoreCase("d")) {
        directed_d = (String)lHash.get("DIRECTED_TYPEd");
      }else {
        directed_i = (String)lHash.get("DIRECTED_TYPEi");
      }
    }

    if ((directed_i.equalsIgnoreCase("Y") && !directed_d.equalsIgnoreCase("Y")) || directed_d.equalsIgnoreCase("B")) {
      lHash.put("CHARGE_TYPE","i");
    }else {
      lHash.put("CHARGE_TYPE","d");
    }

    //whether or not getting pre-define charge number
    String query = "select count(*) from waste_directed_charge wdc, waste_location wl"+
              " where wl.facility_id = wdc.facility_id(+) and wl.waste_location_id = wdc.waste_location_id(+) and"+
              " wdc.waste_location_id is null and wl.facility_id = '"+(String)header.get("FACILITY_ID")+"' and wl.status = 'A'";
    try {
      allDirected = HelpObjs.countQuery(db,query) == 0;
    }catch (Exception e) {
      e.printStackTrace();
      allDirected = false;
    }
   }

   public void getDirectedChargeTypeInfo(Hashtable lHash, Hashtable header) throws Exception {
    WasteDirectedCharge dc = new WasteDirectedCharge(db);
    dc.setFacilityId((String)header.get("FACILITY_ID"));
    dc.setAccountSysId((String)header.get("ACC_SYS_ID"));
    dc.setWasteLocationId("");        //I want to get all directed charge for facility and account_sys

    Hashtable h1 = (Hashtable)header.get("ACC_TYPE_H");
    Hashtable innerH = (Hashtable)h1.get((String)header.get("ACC_SYS_ID"));
    Vector ct = (Vector)innerH.get("CHARGE_TYPE");
    Vector poRequired = (Vector)innerH.get("PO_REQUIRED");

    for (int i = 0; i < ct.size(); i++) {
      String myT = (String)ct.elementAt(i);
      dc.setChargeType(myT);
      if (dc.isDirected()) {   //there is at least a record in directed charge
        if (dc.isBanned()) {   // but that account sys is ban for this work area
          //System.out.println("\n\n ---- account is banned for this work area");
          lHash.put("DIRECTED_TYPE"+myT,"B");
        }else {   //else this work area uses directed charge
          //System.out.println("\n\n------------ getting directed charge number");
          lHash.put("DIRECTED_TYPE"+myT,"Y");
          lHash.put("DIRECTED_CHARGE_NUMBER"+myT,dc.retrieveAllNew());
        }
      }else {
        //System.out.println("\n\n------------ do not use directed charge");
        lHash.put("DIRECTED_TYPE"+myT,"N");
      }
      //in addition get PO number if required
      if ("p".equalsIgnoreCase((String)poRequired.elementAt(i)) &&
          !header.containsKey((String)header.get("ACC_SYS_ID")+"PO")) {
        String query = "select po_number,nvl(preferred,' ') preferred from fac_account_sys_po where status = 'A' and facility_id = '"+(String)header.get("FACILITY_ID")+"'"+
                       " and account_sys_id = '"+(String)header.get("ACC_SYS_ID")+"' order by po_number";
        Vector poNumber = new Vector(20);
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          boolean allDirected = false;
          while(rs.next()){
            String tmp = rs.getString("preferred");
            if ("y".equalsIgnoreCase(tmp)) {
              poNumber.insertElementAt(rs.getString("po_number"),0);
            }else {
              poNumber.addElement(rs.getString("po_number"));
            }
          }
        }catch (Exception e) {
          e.printStackTrace();
          poNumber.addElement("Not Assigned");
        }finally {
          dbrs.close();
        }
        header.put((String)header.get("ACC_SYS_ID")+"PO",poNumber);
      }
    } //end of for loop
   }

   public void getDirectedChargeTypeInfoOld(Hashtable lHash, Hashtable header) throws Exception {
    WasteDirectedCharge dc = new WasteDirectedCharge(db);
    dc.setFacilityId((String)header.get("FACILITY_ID"));
    dc.setAccountSysId((String)header.get("ACC_SYS_ID"));
    dc.setWasteLocationId((String)header.get("WASTE_LOCATION_ID"));

    Hashtable h1 = (Hashtable)header.get("ACC_TYPE_H");
    Hashtable innerH = (Hashtable)h1.get((String)header.get("ACC_SYS_ID"));
    Vector ct = (Vector)innerH.get("CHARGE_TYPE");
    //String userChargeType = rli.getChargeType();

    for (int i = 0; i < ct.size(); i++) {
      String myT = (String)ct.elementAt(i);
      dc.setChargeType(myT);
      if (dc.isDirected()) {   //there is at least a record in directed charge
        if (dc.isBanned()) {   // but that account sys is ban for this work area
          //System.out.println("\n\n ---- account is banned for this work area");
          lHash.put("DIRECTED_TYPE"+myT,"B");
        }else {   //else this work area uses directed charge
          //System.out.println("\n\n------------ getting directed charge number");
          lHash.put("DIRECTED_TYPE"+myT,"Y");
          Vector v = dc.retrieveAll();
          Vector directedChargeNumberV = new Vector();
          for (int j = 0; j < v.size(); j++) {
            Hashtable chargeH = new Hashtable();
            WasteDirectedCharge myDC = (WasteDirectedCharge)v.elementAt(j);
            String number_1 = myDC.getChargeNumber1();
            String number_2 = myDC.getChargeNumber2();
            String pct = myDC.getPct();
            chargeH.put("ACT_NUM_1",number_1);
            chargeH.put("ACT_NUM_2",number_2);
            chargeH.put("PERCENTAGE",pct);
            directedChargeNumberV.addElement(chargeH);
          }
          lHash.put("DIRECTED_CHARGE_NUMBER"+myT,directedChargeNumberV);
        }
      }else {
        //System.out.println("\n\n------------ do not use directed charge");
        lHash.put("DIRECTED_TYPE"+myT,"N");
      }
    }
   }


  public Hashtable getKeyCol(String[] columnH) {
   Hashtable h = new Hashtable();
    for (int i=0;i<columnH.length;i++) {
      h.put(columnH[i],new Integer(i));
    }
    return h;
  }
}
