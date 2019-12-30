package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;
/*
SQLWKS> desc waste_mgmt_request
Column Name                    Null?    Type
------------------------------ -------- ----
WASTE_REQUEST_ID               NOT NULL NUMBER(38)
ACCOUNT_SYS_ID                          VARCHAR2(12)
REQUESTER_ID                   NOT NULL NUMBER(38)
REQUEST_DATE                            DATE
STATUS                                  VARCHAR2(10)
*/
public class WasteRequest {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected int wasteRequestId;
   protected String facId;
   protected int requesterId;
   protected String requestDate;
   protected String accSysId;
   protected String status;
   protected String poNumber;
   protected String creditCardType;
   protected String creditCardNumber;
   protected String creditCardExpDate;
   protected String creditCardName;
   protected String accumulationStartDate;
   protected String wasteTag;

   public WasteRequest(TcmISDBModel db){
      this.db = db;
   }
   public WasteRequest(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public int getWasteRequestId(){return wasteRequestId;}
   public String getFacId(){return facId;}
   public int getRequesterId(){return requesterId;}
   public String getRequestDate(){return requestDate;}
   public String getStatus(){return status;}
   public String getAccSysId(){return accSysId;}
   public String getPoNumber(){return poNumber;}
   public String getCreditCardType() {return this.creditCardType;}
   public String getCreditCardNumber() {return this.creditCardNumber;}
   public String getCreditCardExpDate() {return this.creditCardExpDate;}
   public String getCreditCardName() {return this.creditCardName;}
   public String getAccumulationStartDate() {return this.accumulationStartDate;}
   public String getWasteTag() {return this.wasteTag;}

   // set Methods
   public void setWasteRequestId(int s){wasteRequestId = s;}
   public void setFacId(String s){facId = s;}
   public void setRequesterId(int s){requesterId = s;}
   public void setRequestDate(String s){requestDate = s;}
   public void setStatus(String s){status = s;}
   public void setAccSysId(String s){accSysId = s;}
   public void setPoNumber(String s){poNumber = s;}
   public void setCreditCardType(String s) {this.creditCardType = s;}
   public void setCreditCardNumber(String s) {this.creditCardNumber = s;}
   public void setCreditCardExpDate(String s) {this.creditCardExpDate = s;}
   public void setCreditCardName(String s) {this.creditCardName = s;}
   public void setAccumulationStartDate(String s) {this.accumulationStartDate = s;}
    public void setWasteTag(String s) {this.wasteTag = s;}


  //running with one query
  public static Hashtable getInitialInfo(TcmISDBModel db,int userId) throws Exception{
    Hashtable result = new Hashtable();
    Vector facilityId = new Vector();
    Hashtable storageInfo = new Hashtable();
    String query = "select * from waste_facility_acct_sys_view where personnel_id = "+userId;
    query += " and status = 'A' and tsdf = 'N'";
    query += " order by facility_id,waste_location_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastFac = "";
      int count = 0;
      while(rs.next()){
       String facId = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
       if (!lastFac.equals(facId)) {
        facilityId.addElement(facId);
       }

       if(result.containsKey(facId)) {
        String temp1 = "";
        String temp2 = "";
        String accSysId = "";
        String lastAccSysId = "";
        String prefAcc = "";
        Vector locV = new Vector();
        Vector descV = new Vector();
        Vector accountSysId = new Vector();
        Vector prefAccSys = new Vector();
        Hashtable h = (Hashtable)result.get(facId);

        locV = (Vector)h.get("WASTE_LOCATION_ID");
        descV = (Vector)h.get("LOCATION_DESC");
        temp1 = rs.getString("waste_location_id");
        if (!locV.contains(temp1)) {
          locV.addElement(temp1);

          temp2 = BothHelpObjs.makeBlankFromNull(rs.getString("location_desc"));
          descV.addElement(temp2);
        }

        accountSysId = (Vector)h.get("ACCOUNT_SYS_ID");
        lastAccSysId = (String)accountSysId.lastElement();
        accSysId = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
        prefAcc = BothHelpObjs.makeBlankFromNull(rs.getString("preferred_account_sys"));
        //if (!lastAccSysId.equals(accSysId)) {
        if (!accountSysId.contains(accSysId)) {

          if (facId.equalsIgnoreCase("Lemmon Ave")){        //if the facility is Lemmon Ave then
            if (accSysId.equalsIgnoreCase("JDE")) {         //put JDE as default account sys
              accountSysId.insertElementAt(accSysId,0);
            }
          } else {                                          //other facility account sys will be alpha sorted

          accountSysId.addElement(accSysId);

          }

          prefAccSys.addElement(prefAcc);
          h.put("ACCOUNT_SYS_ID",accountSysId);
          h.put("PREF_ACC_SYS",prefAccSys);
        }
        h.put("LOCATION_DESC",descV);
        h.put("WASTE_LOCATION_ID",locV);
        result.put(facId,h);
       } else {
        Hashtable h = new Hashtable();
        Vector locV = new Vector();
        Vector descV = new Vector();
        String temp1 = "";
        String temp2 = "";
        String accSysId = "";
        String prefAcc = "";
        String lastAccSysId = "";
        Vector accountSysId = new Vector();
        Vector prefAccSys = new Vector();

        temp1 = rs.getString("waste_location_id");
        locV.addElement(temp1);

        temp2 = BothHelpObjs.makeBlankFromNull(rs.getString("location_desc"));
        descV.addElement(temp2);

        accSysId = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
        accountSysId.addElement(accSysId);
        prefAcc = BothHelpObjs.makeBlankFromNull(rs.getString("preferred_account_sys"));
        prefAccSys.addElement(prefAcc);

        h.put("LOCATION_DESC",descV);
        h.put("WASTE_LOCATION_ID",locV);
        h.put("ACCOUNT_SYS_ID",accountSysId);
        h.put("PREF_ACC_SYS",prefAccSys);
        result.put(facId,h);
       }

       lastFac = facId;
      }

    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("FACILITY_ID",facilityId);
    return result;
  }


  public Vector rebuildWasteLineItem(Vector dv, Vector v){
    Vector newV = new Vector();
    //int j = 0;
    for (int i = 0; i < v.size(); i++){
      if(dv.contains(new Integer(i+1))){
        continue;
      }
      newV.addElement(v.elementAt(i));
     // newV[j] = v[i];
    }
    return newV;
  }

  //trong 8-24 adding new container when user change qty
  void addNewContainer(int number, int reqNum, int lineItem, String generationPoint, String facilityId) throws Exception{
     try{
       //first get waste_item_id from previous container for this line_item
       int wasteItemId = WasteContainer.getWasteItemIdForWasteRequestLine(db,reqNum,lineItem);
       for (int i = 0; i < number; i++) {
        WasteContainer.createWasteContainer(db,reqNum,lineItem,generationPoint,facilityId,wasteItemId);
       }
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }
  }
  void deleteContainer(int number, int reqNum, int lineItem) throws Exception {
    try {
      WasteContainer.deleteContainer(db,reqNum,lineItem,number);
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    }
  }


  //public Hashtable submitRequest(String action, Vector delInfo, Vector chargeV, Integer reqNum, String facility_name,Hashtable headerInfo) throws Exception {
  public Hashtable submitRequest(String action, Vector chargeV, Hashtable headerInfo) throws Exception {
    Hashtable rHash = new Hashtable();
    Integer reqNum = (Integer)headerInfo.get("REQ_NUM");
    String facilityId = (String)headerInfo.get("FAC_ID");
    Integer requesterId = (Integer)headerInfo.get("REQ_ID");
    String prefAccSys = (String)headerInfo.get("ACC_SYS_ID");

    int lineNum = 0;
    Integer num = new Integer(1);
    Vector v = new Vector();

    //trong 4/9 setting pref_acc_sys
    FacPref fp = new FacPref(db);
    fp.setFacId(facilityId);
    fp.setUserId(requesterId.toString());
    fp.insert("PREFERRED_ACCOUNT_SYS_ID",prefAccSys,fp.STRING);

    //deleting all charge number associate with this waste request
    WRAccount wra = new WRAccount(db);
    wra.setWasteRequestId(reqNum.intValue());
    try {
      wra.delete();
    }catch (Exception ext) {
      ext.printStackTrace();
      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED", new Boolean(false));
      rCode.put("MSG", "Error on deleting the account for each line item.");

      rCode.put("ERROR_LINE",new Integer(0));
      rCode.put("ERROR_ROW",new Integer(0));
      rCode.put("ERROR_COL",new Integer(0));

      rHash.put("RETURN_CODE",rCode);
      return rHash;
    }
    //if first time then insert credit card into purchase_request
    boolean creditCardInserted = false;

    WasteRequestLineItem wrli = new WasteRequestLineItem(db);
    wrli.setWasteRequestId(reqNum.intValue());

    //trong 8-16
    Vector travelerIds = new Vector();
    try {
      travelerIds = wrli.getTravelerIds(reqNum.intValue());
    }catch (Exception et){
      et.printStackTrace();
    }

    //updating the note & quantity for each line items
    if(action.equalsIgnoreCase("SAVE")) {
        try {
           for (int i = 0; i < chargeV.size(); i++) {
            Hashtable myt = (Hashtable)chargeV.elementAt(i);
            Integer amount = (Integer)myt.get("QTY");
            String quantity = new String(amount.toString());
            Integer replaceC = (Integer)myt.get("REPLACE_CONTAINER");
            String container = new String(replaceC.toString());
            String sealDate = (String)myt.get("SEAL_DATE");

            String requestedTransferD = (String)myt.get("REQUESTED_TRANSFER_DATE");
            Boolean requestedTransferTAm = (Boolean)myt.get("REQUESTED_TRANSFER_TIME_AM");

            String chargeType = (String)myt.get("CHARGE_TYPE");
            lineNum = i + 1;
            wrli.setLineItem(lineNum);
            String wrliQuery = "";

            Hashtable currentLineInfo = wrli.getCurrentInfo();
            String oldQ = (String)currentLineInfo.get("CURRENT_QTY");
            Integer oldQty = new Integer(oldQ);
            String generationPoint = (String)currentLineInfo.get("GENERATION_POINT");
            if (oldQty.intValue() < amount.intValue()) {
              int dif = amount.intValue() - oldQty.intValue();
              addNewContainer(dif,reqNum.intValue(),lineNum,generationPoint,facilityId);
            }
            if (oldQty.intValue() > amount.intValue()) {
              int dif = oldQty.intValue() - amount.intValue();
              deleteContainer(dif,reqNum.intValue(),lineNum);
            }

            wrliQuery += "quantity="+quantity;
            wrliQuery += ",replace_container="+container;
            wrliQuery += ",note='"+HelpObjs.singleQuoteToDouble((String)myt.get("NOTES"))+"'";
            wrliQuery += ",seal_date=to_date('"+sealDate+"','MM/dd/yyyy')";
            wrliQuery += ",charge_type='"+chargeType+"'";
            wrliQuery += ",requested_transfer_date=to_date('"+requestedTransferD+"','MM/dd/yyyy')";
            if (requestedTransferTAm.booleanValue()) {
              wrliQuery += ",requested_transfer_time='A'";
            }else {
              wrliQuery += ",requested_transfer_time='P'";
            }
            if (myt.containsKey("WASTE_TAG")) {
              wrliQuery += ",tag_number='"+HelpObjs.singleQuoteToDouble((String)myt.get("WASTE_TAG"))+"'";
            }

            String accSysId = (String)headerInfo.get("ACC_SYS_ID");
            String key1 = accSysId + chargeType;
            Hashtable packH = (Hashtable)headerInfo.get("PACK");
            Hashtable accSysTypeH = (Hashtable)packH.get(key1);
            String npo = (String)accSysTypeH.get("PO_REQUIRED");
            String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
            //this is where I need to put info into waste_request_line_item
            if(chargeType == null)chargeType = "";

            if(npo.equalsIgnoreCase("p") || npo.equalsIgnoreCase("b")){      //using blanket PO or PO
              wrliQuery += ",po_number='"+myt.get("PO").toString()==null?"":myt.get("PO").toString()+"'";
              wrliQuery += ",release_number='"+myt.get("RELEASE_NUM").toString()==null?(new Integer(HelpObjs.getNextValFromSeq(db,"customer_po_release_seq"))).toString():myt.get("RELEASE_NUM").toString()+"'";
            }else if ("c".equalsIgnoreCase(npo)) {            // inserting credit card info
              if (!creditCardInserted) {
                String wmrQuery = "";
                this.setWasteRequestId(reqNum.intValue());
                Hashtable ccH = (Hashtable)headerInfo.get("CREDIT_CARD_INFO");
                String ccTmp = (String)ccH.get("CREDIT_CARD_TYPE");
                if (!BothHelpObjs.isBlankString(ccTmp)) {
                  wmrQuery += ",credit_card_type='"+ccTmp+"'";
                }
                ccTmp = (String)ccH.get("CREDIT_CARD_NUMBER");
                if (!BothHelpObjs.isBlankString(ccTmp)) {
                  wmrQuery += ",credit_card_number="+ccTmp;
                }
                ccTmp = (String)ccH.get("CREDIT_CARD_EXP_DATE");
                if (!BothHelpObjs.isBlankString(ccTmp)) {
                  wmrQuery += ",credit_card_expiration_date=to_date('"+ccTmp+"','MM/dd/yyyy')";
                }
                ccTmp = (String)ccH.get("CREDIT_CARD_NAME");
                if (!BothHelpObjs.isBlankString(ccTmp)) {
                  wmrQuery += ",credit_card_name='"+HelpObjs.singleQuoteToDouble(ccTmp)+"'";
                }
                //update credit card info
                if (wmrQuery.length() > 1) {
                  wmrQuery = "update waste_management_request set "+wmrQuery.substring(1)+" where waste_request_id = "+reqNum.intValue();
                  db.doUpdate(wmrQuery);
                }
                creditCardInserted = true;
              }
            }
            if ("y".equalsIgnoreCase(prRequired)) {   //using charge no
              //update the acount for each line item.
              if(myt.get("CHARGE_TYPE").toString().equalsIgnoreCase("i")){
                v = (Vector)myt.get("CHARGE_NUM_INDIRECT");
              }else if (myt.get("CHARGE_TYPE").toString().equalsIgnoreCase("d")){
                v = (Vector)myt.get("CHARGE_NUM_DIRECT");
              }
              for(int k = 0; k < v.size(); k++) {
                Hashtable wrla = (Hashtable)v.elementAt(k);
                if (wrla.get("ACCT_NUM_1") == null || wrla.get("PERCENTAGE") == null){
                  continue;
                }

                num = new Integer(i + 1);
                wra.setLineItem(num.intValue());
                String a1 = wrla.get("ACCT_NUM_1").toString();
                String pct = wrla.get("PERCENTAGE").toString();
                if(!BothHelpObjs.isBlankString(pct) &&
                   !BothHelpObjs.isBlankString(a1)){
                  if (wrla.get("ACCT_NUM_2") != null){
                    wra.setAccountNumber2((String)wrla.get("ACCT_NUM_2"));    //6-20-01 this is the right way
                  }
                  wra.setAccountNumber(a1);
                  wra.setPct(pct);
                  wra.setChargeType((String)myt.get("CHARGE_TYPE"));    //10-07-02
                  try {
                    wra.insert();
                  }catch (Exception e1) {
                    e1.printStackTrace();
                    Hashtable rCode = new Hashtable();
                    rCode.put("SUCCEEDED", new Boolean(false));
                    rCode.put("MSG", "Error on updating the account for each line item.");

                    rCode.put("ERROR_LINE",new Integer(k));
                    rCode.put("ERROR_ROW",new Integer(0));
                    rCode.put("ERROR_COL",new Integer(0));

                    rHash.put("RETURN_CODE",rCode);
                    return rHash;
                  }
                }
              }  //end of inner for
            }   //end of else if uses charge number

            //updating traveler id for each line item
            String fromFacilityId = (String)myt.get("FROM_FACILITY_SELECTED_ID");
            String fromLocationId = (String)myt.get("FROM_LOCATION_SELECTED_ID");
            String toFacilityId = (String)myt.get("TO_FACILITY_SELECTED_ID");
            String toLocationId = (String)myt.get("TO_LOCATION_SELECTED_ID");
            String trId = travelerIds.elementAt(i).toString();
            Integer travId = new Integer(trId);
            int tId = travId.intValue();

            String wtQuery = "request_date=sysdate";
            if (!BothHelpObjs.isBlankString(fromFacilityId) && !"Select One".equals(fromFacilityId)) {
              wtQuery += ",from_facility_id = '" +fromFacilityId + "'";
            }
            if (!BothHelpObjs.isBlankString(fromLocationId) && !"Select One".equals(fromLocationId)) {
              wtQuery += ",from_location_id='"+fromLocationId+"'";
            }
            if (!BothHelpObjs.isBlankString(toFacilityId) && !"Select One".equals(toFacilityId)) {
              wtQuery += ",to_facility_id = '" +toFacilityId + "'";
            }
            if (!BothHelpObjs.isBlankString(toLocationId) && !"Select One".equals(toLocationId)) {
              wtQuery += ",to_location_id = '"+toLocationId+"'";
            }

            //run update
            //first waste_request_line_item
            if (wrliQuery.length() > 1) {
              wrliQuery = "update waste_request_line_item set "+wrliQuery+" where waste_request_id = "+reqNum.intValue()+" and line_item = "+lineNum;
              db.doUpdate(wrliQuery);
            }
            //next waste_traveler
            if (wtQuery.length() > 1) {
              wtQuery = "update waste_traveler set "+wtQuery+" where traveler_id = "+tId;
              db.doUpdate(wtQuery);
            }
            //update accumulation start date for container
            Vector containerId = (Vector)myt.get("CONTAINER_IDS");
            Vector aDate = (Vector)myt.get("ACCUMULATION_DATE");
            String wcQuery = "update waste_container set accumulation_start_date = ";
            String whereByGroup = "";
            for (int jj = 0; jj < containerId.size(); jj++) {
              String id = (String)containerId.elementAt(jj);
              //if user entered something for accumulation start date then update it container by container
              //other set accumulation start date = null for all containers that do not have a date
              if (!BothHelpObjs.isBlankString((String)aDate.elementAt(jj))) {
                String tmpDate = (String)aDate.elementAt(jj);
                db.doUpdate(wcQuery+"to_date('"+tmpDate+"','MM/dd/yyyy') where container_id = "+id);
              }else {
                whereByGroup += id+",";
              }
            }
            //removing the last commas ','
            if (whereByGroup.length() > 1) {
              whereByGroup = whereByGroup.substring(0,whereByGroup.length()-1);
              db.doUpdate(wcQuery+"null where container_id in ("+whereByGroup+")");
            }

           }  //end of for (each line)
        }catch (Exception e) {
          e.printStackTrace();
          Hashtable rCode = new Hashtable();
          rCode.put("SUCCEEDED", new Boolean(false));
          rCode.put("MSG", "Error on updating each line item note.");

          rCode.put("ERROR_LINE",new Integer(lineNum-1));
          rCode.put("ERROR_ROW",new Integer(0));
          rCode.put("ERROR_COL",new Integer(0));

          rHash.put("RETURN_CODE",rCode);
          return rHash;
        }
      }

      if (action.equalsIgnoreCase("SUBMIT")) {

        submitRequest("SAVE",chargeV,headerInfo);

        ChargeNumber cn = new ChargeNumber(db);
        String msg;
          for (int k = 0; k < chargeV.size(); k++){
            Hashtable h = (Hashtable)chargeV.elementAt(k);
            int lineItem = k + 1;
            cn = new ChargeNumber(db);
            Vector cv = new Vector();
            String cType = h.get("CHARGE_TYPE").toString();
            if(cType.equalsIgnoreCase("i")){
              cv = (Vector)h.get("CHARGE_NUM_INDIRECT");
            }else if(cType.equalsIgnoreCase("d")){
              cv = (Vector)h.get("CHARGE_NUM_DIRECT");
            }
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
                Hashtable rCode = new Hashtable();
                msg =  "Charge number is invalid.";
                rCode.put("ERROR_LINE",new Integer(j+1));
                rCode.put("ERROR_ROW",new Integer(k));
                rCode.put("ERROR_COL",new Integer(0));
                rCode.put("SUCCEEDED",new Boolean(false));
                rCode.put("MSG",msg);
                rHash.put("RETURN_CODE",rCode);
                WasteTravelerContainer.deleteAll(db,travelerIds);
                return rHash;
              }
              if (!val2.booleanValue()){
                Hashtable rCode = new Hashtable();
                msg =  "Charge number is invalid.";
                rCode.put("ERROR_LINE",new Integer(j+1));
                rCode.put("ERROR_ROW",new Integer(k));
                rCode.put("ERROR_COL",new Integer(1));
                rCode.put("SUCCEEDED",new Boolean(false));
                rCode.put("MSG",msg);
                rHash.put("RETURN_CODE",rCode);
                WasteTravelerContainer.deleteAll(db,travelerIds);
                return rHash;
              }
            }

            //checking to see if waste manager also transfer the containers
            Boolean b = (Boolean)h.get("TRANSFERRED");

            //updating traveler id for each line item
            String fromLocationId = (String)h.get("FROM_LOCATION_SELECTED_ID");
            String toFacilityId = (String)h.get("TO_FACILITY_SELECTED_ID");
            String toLocationId = (String)h.get("TO_LOCATION_SELECTED_ID");
            String trId = travelerIds.elementAt(k).toString();
            Integer travId = new Integer(trId);
            int tId = travId.intValue();
            //create traveler container
            WasteContainer wc = new WasteContainer(db);
            Vector containerIds = wc.getContainerIdTable(reqNum.intValue(),lineItem);
            for (int count = 0; count < containerIds.size(); count++){
              Integer cId = (Integer)containerIds.elementAt(count);
              WasteTravelerContainer wtc = new WasteTravelerContainer(db);
              wtc.setTravelerId(tId);
              wtc.setContainerId(cId.intValue());
              if (!wtc.travelerContainerExist()) {
                WasteTravelerContainer.createWasteTravelerContainer(db,tId,cId.intValue());
              }
            } //end of for each container

            //if transfer is selected
            if (b.booleanValue()) {
              //traveler_container
              db.doUpdate("update traveler_container set transfer_date = sysdate where traveler_id = "+tId);

              //waste_container
              String wcQuery = "";
              if (!BothHelpObjs.isBlankString(toFacilityId) && !"Select One".equals(toFacilityId)) {
                wcQuery += ",current_facility_id = '" +toFacilityId + "'";
              }
              if (!BothHelpObjs.isBlankString(toLocationId) && !"Select One".equals(toLocationId)) {
                wcQuery += ",current_location_id = '"+toLocationId+"'";
              }
              //update current location
              if (wcQuery.length() > 1) {
                //remove the first "," - wcQuery.substring(1)
                wcQuery = "update waste_container set "+wcQuery.substring(1)+" where waste_requesT_id = "+reqNum.intValue()+" and line_item = "+lineItem;
                db.doUpdate(wcQuery);
              }
            } //if transfer is selected
          } //end of for each line item

        //put the current date in each waste request.
        db.doUpdate("update waste_mgmt_request set status = 'submitted',request_date = sysdate where waste_request_id ="+reqNum.intValue());
      } //end of submit

      Hashtable rCode = new Hashtable();
      rCode.put("SUCCEEDED", new Boolean(true));
      rHash.put("RETURN_CODE",rCode);
      return rHash;
  } //end of method


  public void updateDate() {
    String query = "update waste_mgmt_request set request_date =";
    query += " SYSDATE";
    query += " where waste_request_id = " + getWasteRequestId();
    DBResultSet dbrs = null;
    try {
      db.doQuery(query);
    }catch (Exception ex){};
  }

   public void delete() throws Exception {
    try {
      WasteRequestLineItem wrli = new WasteRequestLineItem(db);
      wrli.setWasteRequestId(getWasteRequestId());
      wrli.delete();

      String query = "delete waste_mgmt_request where waste_request_id = " + getWasteRequestId();
      DBResultSet dbrs = null;
      db.doUpdate(query);

      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
       return;
    }


   public void load()throws Exception{
     String query = "select * from waste_mgmt_request where waste_request_id = '"+getWasteRequestId()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setRequesterId(BothHelpObjs.makeZeroFromNull(rs.getString("requester_id")));
         setRequestDate(BothHelpObjs.makeBlankFromNull(rs.getString("request_date")));
         setFacId(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
         setAccSysId(BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
         setStatus(BothHelpObjs.makeBlankFromNull(rs.getString("status")));
         setPoNumber("");
         setCreditCardType(BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_type")));
         setCreditCardNumber(BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_number")));
         setCreditCardExpDate(BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_expiration_date")));
         setCreditCardName(BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_name")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   protected static WasteRequest insertWasteRequest(TcmISDBModel db,int userId,String facilityId,String accSysId)throws Exception{
     try{
       int next = DbHelpers.getNextVal(db,"waste_request_seq");
       String query = "insert into waste_mgmt_request (waste_request_id,requester_id,facility_id,account_sys_id) values ("+next+","+userId+",'"+facilityId+"','"+accSysId+"')";
       db.doUpdate(query);
       WasteRequest wr = new WasteRequest(db);
       wr.setWasteRequestId(next);
       wr.load();
       return wr;
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
   }

   //****    public static WasteRequest createWasteRequest(TcmISDBModel db,String facId, int userId,Vector wasteReqV) throws Exception{
   public static WasteRequest createWasteRequest(TcmISDBModel db,int userId,String facilityId,String accSysId,Vector wasteReqV) throws Exception{
     WasteRequest wr = null;
     try{
       wr = WasteRequest.insertWasteRequest(db,userId,facilityId,accSysId);
       for(int i=0;i<wasteReqV.size();i++){
         Hashtable h = (Hashtable)wasteReqV.elementAt(i);
         String workArea = h.get("WORK_AREA").toString();
         //String facilityId = h.get("FACILITY_ID").toString();
         int itemId = ((Integer)h.get("WASTE_ITEM_ID")).intValue();
         int qty = ((Integer)h.get("QTY")).intValue();
         WasteRequestLineItem.createWasteRequestLineItem(db,wr.getWasteRequestId(),i+1,workArea,qty,itemId,facilityId);

         //trong 8-16
         WasteTraveler wt = new WasteTraveler(db);
         wt.createWasteTraveler(userId);
         int tId = wt.getTravelerId();
         Integer travelerId = new Integer(tId);
         WasteRequestLineItem wrli = new WasteRequestLineItem(db);
         wrli.setWasteRequestId(wr.getWasteRequestId());
         wrli.setLineItem(i+1);
         wrli.insert("traveler_id",travelerId.toString(),WasteRequestLineItem.INT);

       }
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
     return wr;
   }

   //trong 9-9
   public int createAddIntoRequest(int userId,String facilityId,String actSysId,String generationPt) throws Exception{
    int next = 0;
    try {
      //first create waste_mgmt_request record
      next = DbHelpers.getNextVal(db,"waste_request_seq");
      String query = "insert into waste_mgmt_request (waste_request_id,requester_id,facility_id,request_date,status,account_sys_id) values ("+next+","+userId+",'"+facilityId+"',SYSDATE,'addInto','"+actSysId+"')";
      db.doUpdate(query);
      //second create waste_line_item record which in turn create container_id
      WasteRequestLineItem.createWasteRequestLineItem(db,next,1,generationPt,1,0,facilityId);

    }catch(Exception e){
       e.printStackTrace();
       return (0);
    }
    return (next);
   }

   //4-10-01
   public int createLabPackRequest(int userId,String facilityId,String actSysId,String generationPt,int qty,String wasteItemID) throws Exception{
    int next = 0;
    try {
      //first create waste_mgmt_request record
      next = DbHelpers.getNextVal(db,"waste_request_seq");
      String query = "insert into waste_mgmt_request (waste_request_id,requester_id,facility_id,request_date,status,account_sys_id) values ("+next+","+userId+",'"+facilityId+"',SYSDATE,'labPack','"+actSysId+"')";
      db.doUpdate(query);
      //second create waste_line_item record which in turn create container_id
      Integer wstItem = new Integer(wasteItemID);
      WasteRequestLineItem.createWasteRequestLineItem(db,next,1,generationPt,qty,wstItem.intValue(),facilityId);

    }catch(Exception e){
       e.printStackTrace();
       return (0);
    }
    return (next);
   }

   //trong 8-31
   public String createAccumulationRequest(int userId,String facilityId,int wasteItemId,String generationPt) throws Exception{
    try {
      //first create waste_mgmt_request record
      int next = DbHelpers.getNextVal(db,"waste_request_seq");
      String query = "insert into waste_mgmt_request (waste_request_id,requester_id,facility_id,request_date,status) values ("+next+","+userId+",'"+facilityId+"',SYSDATE,'accumulation')";
      db.doUpdate(query);
      //second create waste_line_item record which in turn create container_id
      WasteRequestLineItem.createWasteRequestLineItem(db,next,1,generationPt,1,wasteItemId,facilityId);
      if (!BothHelpObjs.isBlankString(wasteTag)) {
        db.doUpdate("update waste_request_line_item set tag_number = '"+HelpObjs.singleQuoteToDouble(wasteTag)+"' where waste_request_id = "+next+" and line_item = 1");
      }

      //next get newly created accumulation container id to create waste_accumulation
      WasteContainer wc = new WasteContainer(db);
      Vector temp = wc.getContainerIdTable(next,1);
      Integer cId = (Integer)temp.firstElement();
      wc.setContainerId(cId.intValue());
      wc.insert("accumulation","Y",WasteContainer.STRING);
      if (!BothHelpObjs.isBlankString(accumulationStartDate)) {
        wc.insert("accumulation_start_date",accumulationStartDate,WasteContainer.DATE);
      }
    }catch(Exception e){
       e.printStackTrace();
       return ("ERROR: creating accumulation container failed.");
    }
    return ("Accumulation container was successfully created.");
   }



    public String getNowDate()  throws Exception {
     String query = "select to_char(sysdate,'MM/dd/yyyy') from dual";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String next = new String("");
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         next = rs.getString(1);
         break;
       }
      } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();
     }
     return next;
   }

  public void insertCreditCardInfo(Hashtable creditCardInfo) throws Exception {
    String query = "update waste_mgmt_request set credit_card_number = '"+(String)creditCardInfo.get("CREDIT_CARD_NUMBER")+"',";
    query += "credit_card_type = '"+(String)creditCardInfo.get("CREDIT_CARD_TYPE")+"',";
    query += "credit_card_expiration_date = to_date('"+(String)creditCardInfo.get("CREDIT_CARD_EXP_DATE")+"','MM/DD/YYYY HH24:MI:SS'),";
    query += "credit_card_name = '"+(String)creditCardInfo.get("CREDIT_CARD_NAME")+"' ";
    query += "where waste_request_id = "+getWasteRequestId();
    try {
      db.doUpdate(query);
    }catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  } //end of method

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update waste_mgmt_request set " + col + "=");
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
     query += " where waste_request_id = " + getWasteRequestId();
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
   }

   public Hashtable getTransferNotification(Integer reqNum)throws Exception{
     Hashtable result = new Hashtable(13);
     String query = "select * from waste_xfer_notification_view where waste_request_id = "+reqNum.intValue()+
                    " order by email,from_facility_id,from_location_id";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       String lastEmail = "";
       while (rs.next()){
         String currentEmail = BothHelpObjs.makeBlankFromNull(rs.getString("email"));
         if (BothHelpObjs.isBlankString(currentEmail)) continue;
         if (currentEmail.equals(lastEmail)) {
           Vector message = (Vector)result.get(currentEmail);
           String msg = "\nFrom:       "+BothHelpObjs.makeBlankFromNull(rs.getString("from_location"));
           msg +=       "\nQuantity:   "+BothHelpObjs.makeBlankFromNull(rs.getString("quantity"));
           msg +=       "\nPackaging:  "+BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
           msg +=       "\nProfile:    "+BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
           message.add(msg);
         }else {
           Vector message = new Vector(13);
           String msg = "\nRequestor:  "+BothHelpObjs.makeBlankFromNull(rs.getString("user_name"));
           msg +=       "\nFrom:       "+BothHelpObjs.makeBlankFromNull(rs.getString("from_location"));
           msg +=       "\nQuantity:   "+BothHelpObjs.makeBlankFromNull(rs.getString("quantity"));
           msg +=       "\nPackaging:  "+BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
           msg +=       "\nProfile:    "+BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
           message.add(msg);
           result.put(currentEmail,message);
         }
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
     return result;
   } //end of method

} //end of class

