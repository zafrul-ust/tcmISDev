package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;


public class WasteAddInto {

  protected TcmISDBModel db;
  protected String facilityID = "";

  public void setFacilityId(String val) {
    this.facilityID = val;
  }
  public String getFacilityId() {
    return this.facilityID;
  }

  public WasteAddInto(TcmISDBModel db){
      this.db = db;
  }
  public void setDb(TcmISDBModel db){
      this.db = db;
  }

  Hashtable getWasteLocDirectedCharge()throws Exception{
    Hashtable h = new Hashtable();
    String query = "select * from wst_loc_direct_charge_view where facility_id = '"+getFacilityId()+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastActSys = "";
      String lastGenPt = "";
      while(rs.next()){
        String actSys = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
        String genPt = BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id"));
        String cType = BothHelpObjs.makeBlankFromNull(rs.getString("charge_type"));
        if (h.containsKey(actSys+cType+genPt)) {
          Vector v = (Vector)h.get(actSys+cType+genPt);
          Hashtable innerH = new Hashtable();
          innerH.put("BANNED",BothHelpObjs.makeBlankFromNull(rs.getString("banned")));
          innerH.put("ACT_NUM_1",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
          innerH.put("ACT_NUM_2",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
          innerH.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
          v.addElement(innerH);
          h.put(actSys+cType+genPt,v);
        }else {
          Vector v = new Vector();
          Hashtable innerH = new Hashtable();
          innerH.put("BANNED",BothHelpObjs.makeBlankFromNull(rs.getString("banned")));
          innerH.put("ACT_NUM_1",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
          innerH.put("ACT_NUM_2",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
          innerH.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
          v.addElement(innerH);
          h.put(actSys+cType+genPt,v);
        }
      }
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{dbrs.close();}

    return h;
  }

  public Integer getCurrentAmount(Integer toContainerId) throws Exception{
    String query = "select amount from waste_accumulation where accumulation_id = "+toContainerId.intValue();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int cAmount = 0;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        cAmount += BothHelpObjs.makeZeroFromNull(rs.getString("amount"));
      }
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{dbrs.close();}
    return (new Integer(cAmount));
  }

  public Hashtable getGenerationPts(String facilityId)throws Exception{
    Hashtable result = new Hashtable();
    Vector wasteLocationIdV = new Vector();
    Vector locationDescV = new Vector();

    String query = "select waste_location_id,decode(location_desc,null,waste_location_id,waste_location_id || ' - ' ||location_desc)  location_desc from waste_location where facility_id = '"+facilityId+"'";
    query += " and status = 'A' order by location_desc";
    //query += " and location_type = 'generation' and status = 'A' order by location_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        wasteLocationIdV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id")));
        locationDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("location_desc")));
      }
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{dbrs.close();}

    result.put("WASTE_LOCATION_ID",wasteLocationIdV);
    result.put("LOCATION_DESC",locationDescV);
    return result;
  }

  //3-27-01 getting both generation and storage area for add into screen
  public Hashtable getGenerationStoragePts(String facilityId)throws Exception{
    Hashtable result = new Hashtable();
    Vector wasteLocationIdV = new Vector();
    Vector locationDescV = new Vector();

    String query = "select waste_location_id,decode(location_desc,null,waste_location_id,waste_location_id || ' - ' ||location_desc)  location_desc from waste_location where facility_id = '"+facilityId+"'";
    query += " and status = 'A' order by location_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        wasteLocationIdV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id")));
        locationDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("location_desc")));
      }
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{dbrs.close();}

    result.put("WASTE_LOCATION_ID",wasteLocationIdV);
    result.put("LOCATION_DESC",locationDescV);
    return result;
  }


  public static Hashtable insertChargeNumber(TcmISDBModel db, int reqId, int lineItem, String chargeType, Hashtable ca)throws Exception{
    //update the acount for each line item.
    WRAccount wra = new WRAccount(db);
    wra.setWasteRequestId(reqId);
    Vector v = new Vector();
    if(chargeType.equalsIgnoreCase("i")){
      v = (Vector)ca.get("CHARGE_NUM_INDIRECT");
    }else {
      v = (Vector)ca.get("CHARGE_NUM_DIRECT");
    }
    for(int k = 0; k < v.size(); k++) {
      Hashtable wrla = (Hashtable)v.elementAt(k);
      if (wrla.get("ACCT_NUM_1") == null || wrla.get("PERCENTAGE") == null){
        continue;
      }

      wra.setLineItem(lineItem);
      String a1 = wrla.get("ACCT_NUM_1").toString();
      String pct = wrla.get("PERCENTAGE").toString();
      if(!BothHelpObjs.isBlankString(pct) &&
         !BothHelpObjs.isBlankString(a1)){
        if (wrla.get("ACCT_NUM_2") != null){
          //wra.insert("account_number2",wrla.get("ACCT_NUM_2").toString(),WRAccount.STRING); //trong 8-29
          wra.setAccountNumber2(wrla.get("ACCT_NUM_2").toString());    //8-9-01 duuuh don't know why I did what I did on the line above and truely it wouldn't work.
        }
        wra.setAccountNumber(a1);
        wra.setPct(pct);
        wra.setChargeType(chargeType);      //10-07-02
        try {
          wra.insert();
        }catch (Exception e1) {
          e1.printStackTrace();
          Hashtable rCode = new Hashtable();
          rCode.put("RETURN_CODE", new Boolean(false));
          rCode.put("MSG", "Error on updating the account for each line item.");
          return rCode;
        }
      }
    }
    Hashtable rCode = new Hashtable();
    rCode.put("RETURN_CODE", new Boolean(true));
    rCode.put("MSG", "Updating account complete.");
    return rCode;
  }

  public static Hashtable checkChargeNumber(TcmISDBModel db, String facilityId, String actSysId, String cType, Hashtable chargeH) throws Exception{
    Vector cv = new Vector();
    String msg = null;
    Hashtable outH = new Hashtable();
    if (cType.equalsIgnoreCase("i")) {
      cv = (Vector)chargeH.get("CHARGE_NUM_INDIRECT");
    }else {
      cv = (Vector)chargeH.get("CHARGE_NUM_DIRECT");
    }

    Hashtable headerInfo = new Hashtable();
    headerInfo.put("FACILITY_ID",facilityId);
    headerInfo.put("ACC_SYS_ID",actSysId);
    headerInfo.put("CHARGE_TYPE",cType);
    headerInfo = WasteAddInto.getChargeInfo(db,headerInfo);

    for(int j=0;j<cv.size();j++){
      Hashtable cHash = (Hashtable)cv.elementAt(j);
      if(!cHash.containsKey("PERCENTAGE") ||
         cHash.get("PERCENTAGE") == null ||
         BothHelpObjs.isBlankString(cHash.get("PERCENTAGE").toString())){
        continue;
      }

      ChargeNumber myCN = new ChargeNumber(db);
      myCN.setChargeType(cType);
      myCN.setAccountNumber(cHash.get("ACCT_NUM_1").toString());
      if(!cHash.containsKey("ACCT_NUM_2") ||
         cHash.get("ACCT_NUM_2") == null){
        myCN.setAccountNumber2("");
      }else{
        myCN.setAccountNumber2(cHash.get("ACCT_NUM_2").toString());
      }

      Vector vv = myCN.checkChargeNumberNew(myCN,headerInfo);
      Boolean val1 = (Boolean)vv.elementAt(0);
      Boolean val2 = (Boolean)vv.elementAt(1);
      if (!val1.booleanValue()){
        msg =  "Charge number is invalid.";
        outH.put("ERROR_ROW",new Integer(j));
        outH.put("ERROR_COL",new Integer(0));
        outH.put("RETURN_CODE",new Boolean(false));
        outH.put("MSG",msg);
        return outH;
      }
      if (!val2.booleanValue()){
        msg =  "Charge number 2 is invalid.";
        outH.put("ERROR_ROW",new Integer(j));
        outH.put("ERROR_COL",new Integer(1));
        outH.put("RETURN_CODE",new Boolean(false));
        outH.put("MSG",msg);
        return outH;
      }
    }

    outH.put("RETURN_CODE",new Boolean(true));
    outH.put("MSG","Charge numbers are valid.");
    return outH;
  }

  public static Hashtable getChargeInfo(TcmISDBModel db, Hashtable h)throws Exception {
    String actSysId = (String)h.get("ACC_SYS_ID");
    String cType = (String)h.get("CHARGE_TYPE");
    String query = "select charge_id_1,charge_id_2,charge_valid_1,charge_valid_2 from account_sys ";
    query += "where account_sys_id = '"+actSysId+"' ";
    query += "and charge_type = '"+cType+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable innerH = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        innerH.put("CHARGE_ID_1",BothHelpObjs.makeBlankFromNull(rs.getString("charge_id_1")));
        innerH.put("CHARGE_ID_2",BothHelpObjs.makeBlankFromNull(rs.getString("charge_id_2")));
        innerH.put("VALID_1",BothHelpObjs.makeBlankFromNull(rs.getString("charge_valid_1")));
        innerH.put("VALID_2",BothHelpObjs.makeBlankFromNull(rs.getString("charge_valid_2")));
      }
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{dbrs.close();}

    Hashtable tmp = new Hashtable();
    tmp.put(actSysId+cType,innerH);
    h.put("PACK",tmp);
    return h;
  }

  public Hashtable getAddIntoInfo(String facilityId) throws Exception{
    Hashtable result = new Hashtable();
    Vector actSys = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;

    String query = "select * from wst_account_sys_view where facility_id = '"+facilityId+"'";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        String actSysId = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
        if (!actSys.contains(actSysId)){
          actSys.addElement(actSysId);
        }
        Hashtable actInfo = new Hashtable();
        Vector labelV = new Vector();
        String chargeT = BothHelpObjs.makeBlankFromNull(rs.getString("charge_type"));
        //charge label
        String label1 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_label_1"));
        labelV.addElement(label1);
        String label2 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_label_2"));
        if (BothHelpObjs.isBlankString(label2)) {
          labelV.addElement(new String("%"));
        }else {
          labelV.addElement(label2);
          labelV.addElement(new String("%"));
        }
        //charge id
        Vector chargeIdV = new Vector();
        String chargeId1 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_id_1"));
        chargeIdV.addElement(chargeId1);
        String chargeId2 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_id_2"));
        if (!BothHelpObjs.isBlankString(chargeId2)) {
          chargeIdV.addElement(chargeId2);
        }

        String valid1 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_valid_1"));
        String valid2 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_valid_2"));
        String display1 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_display_1"));
        String display2 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_display_2"));
        actInfo.put("CHARGE_LABEL",labelV);
        actInfo.put("CHARGE_ID",chargeIdV);
        actInfo.put("VALID_1",valid1);
        actInfo.put("VALID_2",valid2);
        actInfo.put("DISPLAY_1",display1);
        actInfo.put("DISPLAY_2",display2);

        //11-05-01
        String needPo = BothHelpObjs.makeBlankFromNull(rs.getString("po_required"));
        actInfo.put("PO_REQUIRED",needPo);
        actInfo.put("PR_ACCOUNT_REQUIRED",BothHelpObjs.makeBlankFromNull(rs.getString("pr_account_required")));
        if (needPo.equalsIgnoreCase("c")) {
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
          ccH.put("CREDIT_CARD_TYPE","");
          ccH.put("CREDIT_CARD_NUMBER","");
          ccH.put("CREDIT_CARD_EXP_DATE","");
          ccH.put("CREDIT_CARD_NAME","");
          actInfo.put("CREDIT_CARD_INFO",ccH);
        }

        result.put(actSysId+chargeT,actInfo);

      }
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{dbrs.close();}

    result.put("ACCOUNT_SYS",actSys);
    result = getChargeNumberInfo(result,facilityId);
    //System.out.println("\n\n------------ result: "+result);
    //result.put("WASTE_LOC_DIRECTED_CHARGE",getWasteLocDirectedCharge());
    return result;
  }

  // 3-13-01
  public Hashtable getDirectedChargeNumber(String facID,String locID,String actSysID, String chargeType, String display)throws Exception {
    Hashtable result = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = "select waste_location_id,percent";
    String orderBy = " order by waste_location_id";
    if ("Both".equalsIgnoreCase(display)) {
      query += ",charge_number_1,charge_number_2";
      orderBy += ",charge_number_1,charge_number_2";
    }else if ("1".equalsIgnoreCase(display)) {
      query += ",charge_number_1";
      orderBy += ",charge_number_1";
    }else if ("2".equalsIgnoreCase(display)) {
      query += ",charge_number_2";
      orderBy += ",charge_number_2";
    }
    query += " from waste_directed_charge where facility_id = '"+facID+"' and account_sys_id = '"+actSysID+"' and "+
             "charge_type = '"+chargeType+"'";
    if (locID.length() > 1) {
      query += " and waste_location_id = '"+locID+"'";
    }
    query += orderBy;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastLocID = "";
      boolean directedCharge = false;
      while(rs.next()){
        String currentLocID = rs.getString("waste_location_id");
        if (lastLocID.equals(currentLocID)) {
          Hashtable h = (Hashtable)result.get(actSysID+chargeType+currentLocID);
          Vector chargeNum1 = null;
          Vector chargeNum2 = null;
          Vector percent = (Vector)h.get("PERCENT");
          percent.addElement(rs.getString("percent"));
          if ("Both".equalsIgnoreCase(display)) {
            chargeNum1 = (Vector)h.get("CHARGE_NUMBER_1");
            chargeNum2 = (Vector)h.get("CHARGE_NUMBER_2");
            chargeNum1.addElement(rs.getString("charge_number_1"));
            chargeNum2.addElement(rs.getString("charge_number_2"));
            h.put("CHARGE_NUMBER_1",chargeNum1);
            h.put("CHARGE_NUMBER_2",chargeNum2);
            h.put("PERCENT",percent);
            result.put(actSysID+chargeType+currentLocID,h);
          }else if ("1".equalsIgnoreCase(display)) {
            chargeNum1 = (Vector)h.get("CHARGE_NUMBER_1");
            chargeNum1.addElement(rs.getString("charge_number_1"));
            h.put("CHARGE_NUMBER_1",chargeNum1);
            h.put("PERCENT",percent);
            result.put(actSysID+chargeType+currentLocID,h);
          }else if ("2".equalsIgnoreCase(display)) {
            chargeNum2 = (Vector)h.get("CHARGE_NUMBER_2");
            chargeNum2.addElement(rs.getString("charge_number_2"));
            h.put("CHARGE_NUMBER_2",chargeNum2);
            h.put("PERCENT",percent);
            result.put(actSysID+chargeType+currentLocID,h);
          }
        }else {
          Hashtable h = new Hashtable(3);
          Vector chargeNum1 = null;
          Vector chargeNum2 = null;
          Vector percent = new Vector(5);
          percent.addElement(rs.getString("percent"));
          if ("Both".equalsIgnoreCase(display)) {
            chargeNum1 = new Vector(5);
            chargeNum2 = new Vector(5);
            chargeNum1.addElement(rs.getString("charge_number_1"));
            chargeNum2.addElement(rs.getString("charge_number_2"));
            h.put("CHARGE_NUMBER_1",chargeNum1);
            h.put("CHARGE_NUMBER_2",chargeNum2);
            h.put("PERCENT",percent);
            result.put(actSysID+chargeType+currentLocID,h);
          }else if ("1".equalsIgnoreCase(display)) {
            chargeNum1 = new Vector(5);
            chargeNum1.addElement(rs.getString("charge_number_1"));
            h.put("CHARGE_NUMBER_1",chargeNum1);
            h.put("PERCENT",percent);
            result.put(actSysID+chargeType+currentLocID,h);
          }else if ("2".equalsIgnoreCase(display)) {
            chargeNum2 = new Vector(5);
            chargeNum2.addElement(rs.getString("charge_number_2"));
            h.put("CHARGE_NUMBER_2",chargeNum2);
            h.put("PERCENT",percent);
            result.put(actSysID+chargeType+currentLocID,h);
          }
        }
        lastLocID = currentLocID;
      }

      query = "select count(*) from waste_directed_charge wdc, waste_location wl"+
              " where wl.facility_id = wdc.facility_id(+) and wl.waste_location_id = wdc.waste_location_id(+) and"+
              " wdc.waste_location_id is null and wl.facility_id = '"+facID+"' and wl.status = 'A'";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      boolean allDirected = false;
      while(rs.next()){
        allDirected = rs.getInt(1) == 0;
      }
      result.put("ALL_DIRECTED",new Boolean(allDirected));
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return result;
  } //end of method


  Vector getPo(String actSys, String facilityID) {
    Vector result = new Vector(30);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select po_number,preferred from fac_account_sys_po where status = 'A' and facility_id = '"+facilityID+"' and account_sys_id = '"+actSys+"' order by po_number";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      boolean allDirected = false;
      while(rs.next()){
        String pref = rs.getString("preferred");
        if ("Y".equalsIgnoreCase(pref)) {
          result.insertElementAt(rs.getString("po_number"),0);
        }else {
          result.addElement(rs.getString("po_number"));
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      result.addElement("Not Assigned");
    }finally {
      dbrs.close();
    }
    if (result.size() == 0) {
      result.addElement("Not Assigned");
    }
    return result;
  } //end of method

  public Hashtable getChargeNumberInfo(Hashtable result, String facilityID) throws Exception{
    Vector actSysV = (Vector)result.get("ACCOUNT_SYS");
    //System.out.println("\n\n--------- account sys: "+result);
    for (int i = 0; i < actSysV.size(); i++) {
      boolean POrequired = false;
      String actSys = actSysV.elementAt(i).toString();
      //indirect charge number
      if (result.containsKey(actSys+"i")) {
        Hashtable actInfo = (Hashtable)result.get(actSys+"i");
        //check to see if account sys require PO
        if ("p".equalsIgnoreCase((String)actInfo.get("PO_REQUIRED"))) {
          POrequired = true;
        }
        Vector chargeIdV = (Vector)actInfo.get("CHARGE_ID");
        boolean sameChargeID = false;
        if (chargeIdV.size() == 2) {
          String firstChargeID = (String)chargeIdV.elementAt(0);
          if (firstChargeID.equalsIgnoreCase((String)chargeIdV.elementAt(1))) {
            sameChargeID = true;
          }else {
            sameChargeID = false;
          }
        }else {
          sameChargeID = true;
        }

        if ("y".equalsIgnoreCase(actInfo.get("DISPLAY_1").toString()) && "y".equalsIgnoreCase(actInfo.get("DISPLAY_2").toString())) {
          Hashtable directedChargeNumber = getDirectedChargeNumber(facilityID,"",actSys,"i","Both");
          Boolean allDirected = (Boolean)directedChargeNumber.get("ALL_DIRECTED");
          if (!allDirected.booleanValue()) {
            Hashtable chargeNumber = null;
            if (sameChargeID) {
              chargeNumber = getChargeNumber((String)chargeIdV.firstElement(),"Both");
              if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
                actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
              }
              if (chargeNumber.containsKey("CHARGE_NUMBER_2")) {
                actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_2"));
              }
            }else {
              //getting charge number 1
              chargeNumber = getChargeNumber((String)chargeIdV.elementAt(0),"1");
              if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
                actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
              }
              //getting charge number 2 separately
              chargeNumber = getChargeNumber((String)chargeIdV.elementAt(1),"2");
              if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
                actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
              }
            }
          }
          actInfo.put("DIRECTED_CHARGE_NUMBER",directedChargeNumber);
          result.put(actSys+"i",actInfo);
        }else if ("y".equalsIgnoreCase(actInfo.get("DISPLAY_1").toString())) {
          Hashtable directedChargeNumber = getDirectedChargeNumber(facilityID,"",actSys,"i","1");
          Boolean allDirected = (Boolean)directedChargeNumber.get("ALL_DIRECTED");
          if (!allDirected.booleanValue()) {
            Hashtable chargeNumber = getChargeNumber((String)chargeIdV.elementAt(0),"1");
            if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
              actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
            }
          }
          actInfo.put("DIRECTED_CHARGE_NUMBER",directedChargeNumber);
          result.put(actSys+"i",actInfo);
        }else if ("y".equalsIgnoreCase(actInfo.get("DISPLAY_2").toString())) {
          Hashtable directedChargeNumber = getDirectedChargeNumber(facilityID,"",actSys,"i","2");
          Boolean allDirected = (Boolean)directedChargeNumber.get("ALL_DIRECTED");
          if (!allDirected.booleanValue()) {
            Hashtable chargeNumber = getChargeNumber((String)chargeIdV.elementAt(0),"2");
            if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
              actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
            }
          }
          actInfo.put("DIRECTED_CHARGE_NUMBER",directedChargeNumber);
          result.put(actSys+"i",actInfo);
        }else {
          actInfo.put("DIRECTED_CHARGE_NUMBER",new Hashtable(0));
          result.put(actSys+"i",actInfo);
        }
      } //end of charge type = "i"

      if (result.containsKey(actSys+"d")) {
        Hashtable actInfo = (Hashtable)result.get(actSys+"d");
        //check to see if account sys require PO
        if ("p".equalsIgnoreCase((String)actInfo.get("PO_REQUIRED"))) {
          POrequired = true;
        }
        Vector chargeIdV = (Vector)actInfo.get("CHARGE_ID");
        boolean sameChargeID = false;
        if (chargeIdV.size() == 2) {
          String firstChargeID = (String)chargeIdV.elementAt(0);
          if (firstChargeID.equalsIgnoreCase((String)chargeIdV.elementAt(1))) {
            sameChargeID = true;
          }else {
            sameChargeID = false;
          }
        }else {
          sameChargeID = true;
        }

        if ("y".equalsIgnoreCase(actInfo.get("DISPLAY_1").toString()) && "y".equalsIgnoreCase(actInfo.get("DISPLAY_2").toString())) {
          Hashtable directedChargeNumber = getDirectedChargeNumber(facilityID,"",actSys,"d","Both");
          Boolean allDirected = (Boolean)directedChargeNumber.get("ALL_DIRECTED");
          if (!allDirected.booleanValue()) {
            Hashtable chargeNumber = null;
            if (sameChargeID) {
              chargeNumber = getChargeNumber((String)chargeIdV.firstElement(),"Both");
              if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
                actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
              }
              if (chargeNumber.containsKey("CHARGE_NUMBER_2")) {
                actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_2"));
              }
            }else {
              //getting charge number 1
              chargeNumber = getChargeNumber((String)chargeIdV.elementAt(0),"1");
              if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
                actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
              }
              //getting charge number 2 separately
              chargeNumber = getChargeNumber((String)chargeIdV.elementAt(1),"2");
              if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
                actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
              }
            }
          }
          actInfo.put("DIRECTED_CHARGE_NUMBER",directedChargeNumber);
          result.put(actSys+"d",actInfo);
        }else if ("y".equalsIgnoreCase(actInfo.get("DISPLAY_1").toString())) {
          Hashtable directedChargeNumber = getDirectedChargeNumber(facilityID,"",actSys,"d","1");
          Boolean allDirected = (Boolean)directedChargeNumber.get("ALL_DIRECTED");
          if (!allDirected.booleanValue()) {
            Hashtable chargeNumber = getChargeNumber((String)chargeIdV.elementAt(0),"1");
            if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
              actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
            }
          }
          actInfo.put("DIRECTED_CHARGE_NUMBER",directedChargeNumber);
          result.put(actSys+"d",actInfo);
        }else if ("y".equalsIgnoreCase(actInfo.get("DISPLAY_2").toString())) {
          Hashtable directedChargeNumber = getDirectedChargeNumber(facilityID,"",actSys,"d","2");
          Boolean allDirected = (Boolean)directedChargeNumber.get("ALL_DIRECTED");
          if (!allDirected.booleanValue()) {
            Hashtable chargeNumber = getChargeNumber((String)chargeIdV.elementAt(0),"2");
            if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
              actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
            }
          }
          actInfo.put("DIRECTED_CHARGE_NUMBER",directedChargeNumber);
          result.put(actSys+"d",actInfo);
        }else {
          actInfo.put("DIRECTED_CHARGE_NUMBER",new Hashtable(0));
          result.put(actSys+"d",actInfo);
        }
      }
      //using PO
      if (POrequired) {
        result.put(actSys+"PO",getPo(actSys,facilityID));
      }
    }
    return result;
  }

    public Hashtable getChargeNumberInfoOld(Hashtable result) throws Exception{
    Vector actSysV = (Vector)result.get("ACCOUNT_SYS");
    //System.out.println("\n\n--------- account sys: "+result);
    for (int i = 0; i < actSysV.size(); i++) {
      String actSys = actSysV.elementAt(i).toString();
      if (result.containsKey(actSys+"i")) {
        Hashtable actInfo = (Hashtable)result.get(actSys+"i");
        Vector chargeIdV = (Vector)actInfo.get("CHARGE_ID");
        boolean sameChargeID = false;
        if (chargeIdV.size() == 2) {
          String firstChargeID = (String)chargeIdV.elementAt(0);
          if (firstChargeID.equalsIgnoreCase((String)chargeIdV.elementAt(1))) {
            sameChargeID = true;
          }else {
            sameChargeID = false;
          }
        }else {
          sameChargeID = true;
        }

        if (actInfo.get("DISPLAY_1").toString().equalsIgnoreCase("y")) {
          Hashtable directedChargeNumber = getDirectedChargeNumber("","",actSys,"i","");
          Hashtable chargeNumber = getChargeNumber(chargeIdV.elementAt(0).toString(),"");
          if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
            actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
          }
          if (chargeNumber.containsKey("CHARGE_NUMBER_2")) {
            actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_2"));
          }
          result.put(actSys+"i",actInfo);
        }

        if (actInfo.get("DISPLAY_2").toString().equalsIgnoreCase("y")) {
          Hashtable chargeNumber = getChargeNumber(chargeIdV.elementAt(1).toString(),"");
          if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
            actInfo.put(chargeIdV.elementAt(1).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
          }
          if (chargeNumber.containsKey("CHARGE_NUMBER_2")) {
            actInfo.put(chargeIdV.elementAt(1).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_2"));
          }
          result.put(actSys+"i",actInfo);
        }
      }

      if (result.containsKey(actSys+"d")) {
        Hashtable actInfo = (Hashtable)result.get(actSys+"d");
        Vector chargeIdV = (Vector)actInfo.get("CHARGE_ID");
        if (actInfo.get("DISPLAY_1").toString().equalsIgnoreCase("y")) {
          Hashtable chargeNumber = getChargeNumber(chargeIdV.elementAt(0).toString(),"");
          if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
            actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
          }
          if (chargeNumber.containsKey("CHARGE_NUMBER_2")) {
            actInfo.put(chargeIdV.elementAt(0).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_2"));
          }
          result.put(actSys+"d",actInfo);
        }

        if (actInfo.get("DISPLAY_2").toString().equalsIgnoreCase("y")) {
          Hashtable chargeNumber = getChargeNumber(chargeIdV.elementAt(1).toString(),"");
          if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
            actInfo.put(chargeIdV.elementAt(1).toString()+"CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
          }
          if (chargeNumber.containsKey("CHARGE_NUMBER_2")) {
            actInfo.put(chargeIdV.elementAt(1).toString()+"CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_2"));
          }
          result.put(actSys+"d",actInfo);
        }
        //System.out.println("\n\n--------- charge info: "+actInfo);
      }
    }
    return result;
  }

  public Hashtable getChargeNumber(String chargeId, String display) throws Exception{
    Hashtable result = new Hashtable();

    Vector chargeNumberV1 = new Vector();
    Vector chargeNumberV2 = new Vector();
    String select = "select ";
    String from = " from charge_number where charge_id = '"+chargeId+"'";
    String orderBy = " order by ";
    if ("Both".equalsIgnoreCase(display)) {
      select += "charge_number_1,charge_number_2";
      orderBy += "charge_number_1,charge_number_2";
    }else {
      select += "charge_number_1";
      orderBy += "charge_number_1";
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(select+from+orderBy);
      rs=dbrs.getResultSet();
      while(rs.next()){
        if ("Both".equalsIgnoreCase(display)) {
          chargeNumberV1.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
          chargeNumberV2.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
        }else {
          chargeNumberV1.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }

    if (chargeNumberV1.size() > 0) {
      result.put("CHARGE_NUMBER_1",chargeNumberV1);
    }
    if (chargeNumberV2.size() > 0) {
      result.put("CHARGE_NUMBER_2",chargeNumberV2);
    }
    return result;
  }

  public Hashtable getChargeNumberOld(String chargeId) throws Exception{
    Hashtable result = new Hashtable();
    Vector chargeNumberV1 = new Vector();
    Vector chargeNumberV2 = new Vector();
    String query = "select charge_number_1,charge_number_2 from charge_number where charge_id = '"+chargeId+"'";
    query += " order by charge_number_1";
    DBResultSet dbrs = null;
    ResultSet rs = null;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        String number1 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1"));
        if (!BothHelpObjs.isBlankString(number1)) {
          chargeNumberV1.addElement(number1);
        }
        String number2 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2"));
        if (!BothHelpObjs.isBlankString(number2)) {
          chargeNumberV2.addElement(number1);
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }

    if (chargeNumberV1.size() > 0) {
      result.put("CHARGE_NUMBER_1",chargeNumberV1);
    }
    if (chargeNumberV2.size() > 0) {
      result.put("CHARGE_NUMBER_2",chargeNumberV2);
    }
    return result;
  } //end of method
} //end of class

/*
      if (result.containsKey(actSys+"d")) {
        Hashtable actInfo = (Hashtable)result.get(actSys+"d");
        Vector chargeIdV = (Vector)actInfo.get("CHARGE_ID");
        if (actInfo.get("DISPLAY_1").toString().equalsIgnoreCase("y") ||
            actInfo.get("DISPLAY_2").toString().equalsIgnoreCase("y")) {
          Hashtable chargeNumber = getChargeNumber(chargeIdV.elementAt(0).toString());
          if (chargeNumber.containsKey("CHARGE_NUMBER_1")) {
            actInfo.put("CHARGE_NUMBER_1",(Vector)chargeNumber.get("CHARGE_NUMBER_1"));
          }
          if (chargeNumber.containsKey("CHARGE_NUMBER_2")) {
            actInfo.put("CHARGE_NUMBER_2",(Vector)chargeNumber.get("CHARGE_NUMBER_2"));
          }
        }
        result.put(actSys+"d",actInfo);
      }*/