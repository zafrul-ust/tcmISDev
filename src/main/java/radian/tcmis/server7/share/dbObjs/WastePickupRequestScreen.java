package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

public class WastePickupRequestScreen {
  protected TcmISDBModel db;

  public WastePickupRequestScreen(TcmISDBModel db){
     this.db = db;
  }

  //trong 2-6-01
  static boolean allDirected = false;
  static String directed_d = "";
  static String directed_i = "";

  public static final int WR_ITEM_ID_COL = 0;
  public static final int WR_LINE_COL = 1;
  public static final int WR_VENDOR_PROFILE_ID_COL = 2;
  public static final int WR_PROFILE_DESC_COL = 3;
  public static final int WR_QTY_COL = 4;
  public static final int WR_PACKAGING_COL = 5;
  public static final int WR_VENDOR_ID_COL = 6;
  public static final int WR_WORK_AREA_DESC_COL = 7;
  public static final int WR_WASTE_MGMT_OPTION_COL = 8;
  public static final int WR_REPLACE_CONTAINER_COL = 9;
  public static final int WR_NOTE_COL = 10;
  public static final int WR_PICKUP_COL = 11;
  public static final int WR_WORK_AREA_ID_COL = 12;
  public static final int WR_WASTE_TAG_COL = 13;

  public static final String[] colHeads = {"Rad Profile"," ","Vend Profile","Description","Qty","Packaging","Vendor",
                                           "Accumulation Pt","Waste Mgmt Option","Replace Container","Notes","Transferred","Work Area Id","Waste Tag #"};
  public static final int[] colWidths = {0,20,80,110,30,80,110,120,100,0,40,70,0,75};
  public static final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING};

  public boolean showWasteTag(int reqNum, String facilityId) {
    boolean result = false;
    String query = "";
    if (facilityId.length() > 0) {
      query = "select count(*) from waste_vendor_facility x, waste_location y" +
              " where x.vendor_id = y.epa_id and y.tsdf = 'Y' and y.tag_number_display = 'Y'"+
              " and x.facility_Id = '"+facilityId+"'";
    }else {
      query = "select count(*) from waste_vendor_facility x, waste_location y,waste_mgmt_request z" +
              " where x.vendor_id = y.epa_id and y.tsdf = 'Y' and y.tag_number_display = 'Y'"+
              " and x.facility_Id = z.facility_id and z.waste_request_id = " + reqNum;
    }
    try {
      result = HelpObjs.countQuery(db, query) > 0;
    }catch (Exception e) {
      result = false;
    }
    return result;
  } //end of method

  public Hashtable getScreenData(int reqNum) throws Exception{
    Hashtable header = new Hashtable();
    Vector lineItems = new Vector();
    Vector dataV = new Vector();
    header.put("TABLE_HEADERS",colHeads);
    if (showWasteTag(reqNum,"")) {
      header.put("TABLE_WIDTHS", colWidths);
    }else {
      //hidden waste tag # column
      int[] colWidths = {0,20,80,110,30,80,110,120,100,0,40,70,0,0};
      header.put("TABLE_WIDTHS", colWidths);
    }
    header.put("TABLE_TYPES",colTypes);
    Hashtable containerTableH = new Hashtable();
    //getting header info
    getHeaderInfo(header,reqNum);
    //getting line item details
    getDetailInfo(header,lineItems,reqNum,dataV,containerTableH);
    //checking to see if the user is a waste manager
    UserGroup ug = new UserGroup(db);
    ug.setGroupFacId(header.get("FAC_ID").toString());
    ug.setGroupId(new String("WasteManager"));
    boolean wasteManager = ug.isWasteManager(((Integer)header.get("REQ_ID")).intValue());
    //if user is a waste pickup
    ug.setGroupId("WastePickup");
    boolean wastePickup = ug.isMember(((Integer)header.get("REQ_ID")).intValue());

    dataV.trimToSize();
    header.put("TABLE_DATA",dataV);
    Hashtable h = new Hashtable();
    h.put("HEADER_INFO",header);
    h.put("LINE_ITEMS",lineItems);
    h.put("WASTE_MANAGER",new Boolean(wasteManager));
    h.put("WASTE_PICKUP",new Boolean(wastePickup));
    h.put("CONTAINER_TABLE",containerTableH);
    return h;
  }

  Hashtable getContainerInfo(int reqNum, int lineItem) {
    Hashtable result = new Hashtable(2);
    Vector id = new Vector(31);
    Vector aDate = new Vector(31);
    String query = "select container_id,decode(accumulation_start_date,null,' ',to_char(accumulation_start_date,'MM/dd/yyyy')) accumulation_start_date"+
                   " from waste_container where waste_request_id = "+reqNum+" and line_item = "+lineItem;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()) {
        id.addElement(rs.getString("container_id").trim());
        aDate.addElement(rs.getString("accumulation_start_date").trim());
      }
    } catch (Exception e) {
       e.printStackTrace();
    } finally{
       dbrs.close();
    }
    result.put("CONTAINER_ID",id);
    result.put("ACCUMULATION_START_DATE",aDate);
    return result;
  }

  void getDetailInfo(Hashtable header, Vector lineItems, int reqNum, Vector dataV, Hashtable containerTableH) {
    boolean canEditD = false;
    boolean canEditI = false;
    String query = "select * from waste_transfer_detail_view where waste_request_id = "+reqNum+
                   " order by waste_request_id,line_item,container_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int myCount = 0;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      Hashtable wasteLocation = null;
      Vector preDefinedChargeNumber = null;
      while (rs.next()) {
        Hashtable lHash = new Hashtable();
        Object[] oa = new Object[colHeads.length];
        oa[WR_ITEM_ID_COL] = rs.getString("waste_item_id");
        oa[WR_QTY_COL] = rs.getString("quantity");
        oa[WR_PACKAGING_COL] = rs.getString("packaging");
        oa[WR_LINE_COL] = rs.getString("line_item");
        oa[WR_REPLACE_CONTAINER_COL] = rs.getString("replace_container");
        oa[WR_PICKUP_COL] = new Boolean("Y".equalsIgnoreCase(rs.getString("auto_transfer_request")));
        lHash.put("WASTE_LOCATION_ID",rs.getString("generation_point"));
        oa[WR_WORK_AREA_DESC_COL] = rs.getString("generation_point_desc"); //work area desc
        oa[WR_WORK_AREA_ID_COL] = rs.getString("generation_point");
        oa[WR_VENDOR_PROFILE_ID_COL] = rs.getString("vendor_profile_id");
        oa[WR_PROFILE_DESC_COL] = rs.getString("description");
        oa[WR_WASTE_MGMT_OPTION_COL] = rs.getString("management_option");
        String tmpNote = BothHelpObjs.makeBlankFromNull(rs.getString("note"));
        if (tmpNote.length() == 0) {
          oa[WR_NOTE_COL] = new String("");
        }else {
          oa[WR_NOTE_COL] = new String("   +");
        }
        oa[WR_VENDOR_ID_COL] = rs.getString("company_name");
        oa[WR_WASTE_TAG_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("tag_number"));
        dataV.addElement(oa);

        //container id and accumulation start date for line item
        int lineNum = rs.getInt("line_item");
        String containerKey = "LINE"+(lineNum-1);

        Hashtable containerH = getContainerInfo(reqNum,lineNum);
        containerTableH.put(containerKey+"ID",(Vector)containerH.get("CONTAINER_ID"));
        containerTableH.put(containerKey+"DATE",(Vector)containerH.get("ACCUMULATION_START_DATE"));

        // now load the detail section data
        lHash.put("LINE_NUM",new Integer(rs.getString("line_item")));
        lHash.put("UNIT_PRICE",new BigDecimal(rs.getString("last_price_paid")));
        lHash.put("CURRENCY_ID",(String)header.get("CURRENCY_ID"));

        lHash.put("PICKUP_DATE",new String(" "));
        lHash.put("QTY", rs.getString("quantity"));
        String ctt = BothHelpObjs.makeBlankFromNull(rs.getString("charge_type"));
        if(BothHelpObjs.isBlankString(ctt)){
          lHash.put("CHARGE_TYPE","d");  //trong 4/9
        }else {
          lHash.put("CHARGE_TYPE",ctt);
        }
        lHash.put("NOTES",rs.getString("note"));
        lHash.put("REPLACE_CONTAINER",rs.getString("replace_container"));
        lHash.put("SEAL_DATE",rs.getString("seal_date"));
        lHash.put("REQUESTED_TRANSFER_DATE",rs.getString("requested_transfer_date"));
        String am = BothHelpObjs.makeBlankFromNull(rs.getString("requested_transfer_time"));
        if (am.equalsIgnoreCase("A")) {
          lHash.put("REQUESTED_TRANSFER_TIME_AM",new Boolean(true));
        }else {
          lHash.put("REQUESTED_TRANSFER_TIME_AM",new Boolean(false));
        }
        //getting the from and to locations
        int travelerId = BothHelpObjs.makeZeroFromNull(rs.getString("traveler_id"));
        lHash.put("WASTE_LOCATION",getGenerationToStorage(rs.getString("facility_id"),rs.getString("generation_point")));
        if (travelerId == 0){
          lHash.put("FROM_FACILITY_SELECTED_ID",new String(""));
          lHash.put("FROM_LOCATION_SELECTED_ID", new String(""));
          lHash.put("TO_FACILITY_SELECTED_ID", new String(""));
          lHash.put("TO_LOCATION_SELECTED_ID", new String(""));
        }else {
          lHash.put("FROM_FACILITY_SELECTED_ID",rs.getString("from_facility_id"));
          lHash.put("FROM_LOCATION_SELECTED_ID", rs.getString("from_location_id"));
          lHash.put("TO_FACILITY_SELECTED_ID", rs.getString("to_facility_id"));
          lHash.put("TO_LOCATION_SELECTED_ID", rs.getString("to_location_id"));
        }
        //charge number
        String facID = rs.getString("facility_id");
        String accountSysID = rs.getString("account_sys_id");
        String enteredPO = BothHelpObjs.makeBlankFromNull(rs.getString("po_number"));

        if ("DANA".equalsIgnoreCase(db.getClient())) {
          Vector poV = getPO(null,facID,accountSysID,enteredPO);
          String myPo = poV.elementAt(0).toString();
          poV.removeElementAt(0);
          lHash.put("PO",myPo);
          lHash.put("ALL_POS",poV);
          lHash.put("RELEASE_NUM",rs.getString("release_number"));
        }else {
          lHash.put("PO",enteredPO);
          lHash.put("ALL_POS",new Vector(0));
          lHash.put("RELEASE_NUM",rs.getString("release_number"));
        }
        // charge number information
        WRAccount WRA = new WRAccount(db) ;
        WRA.setWasteRequestId(reqNum);
        WRA.setLineItem(lineNum);
        Vector prav = WRA.getMultiAcctNumbers();
        Vector cnums = new Vector();
        Vector dCnV = new Vector();
        Vector iCnV = new Vector();
        Vector CnV = new Vector();
        for(int q=0;q<prav.size();q++){
          WRAccount myPra = (WRAccount)prav.elementAt(q);
          Hashtable prh = new Hashtable();
          prh.put("ACCT_NUM_1",myPra.getAccountNumber());
          prh.put("ACCT_NUM_2",myPra.getAccountNumber2());
          prh.put("PERCENTAGE",myPra.getPct());
          prh.put("CHARGE_TYPE",rs.getString("charge_type"));
          cnums.addElement(prh);
        }
        //trong 5/4
        if(lHash.get("CHARGE_TYPE").toString().equalsIgnoreCase("d")){
          dCnV = cnums;
        }else if(lHash.get("CHARGE_TYPE").toString().equalsIgnoreCase("i")){
          iCnV = cnums;
        }else{
          //System.out.println("\n\n\n SHOULD NOT BE HERE 22222");
          //CnV = cnums;
        }
        if(header.get("VIEW_TYPE").toString().equalsIgnoreCase("REQUEST")){
          //trong 2-6-01
          getDirectedChargeTypeInfo(lHash,header);
          setDirectedChargeType(lHash,header,ctt);

          if (!allDirected) {                 //only uses directed charge number
            // get pre defined charge numbers
            Vector cnV = null;
            if (preDefinedChargeNumber == null) {
              ChargeNumber cn = new ChargeNumber(db);
              preDefinedChargeNumber = cn.getChargeNumsForFacNew(header,"Waste");
            }
            cnV = preDefinedChargeNumber;
            for(int r=0;r<cnV.size();r++){
              ChargeNumber myCN = (ChargeNumber)cnV.elementAt(r);
              Hashtable cnh = new Hashtable();
              cnh.put("ACCT_NUM_1",myCN.getAccountNumber());
              cnh.put("ACCT_NUM_2",myCN.getAccountNumber2());
              cnh.put("PERCENTAGE","");
              cnh.put("CHARGE_TYPE",myCN.getChargeType());

              if(myCN.getChargeType().equalsIgnoreCase("i")){
                if(!hasThisChargeNum(iCnV,myCN)){
                  iCnV.addElement(cnh);
                }
              }else if(myCN.getChargeType().equalsIgnoreCase("d")){
                if(!hasThisChargeNum(dCnV,myCN)){
                  dCnV.addElement(cnh);
                }
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
          }else{
            canEditD = false;
            canEditI = false;
          }
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

        String ct = rs.getString("charge_type");
        if(BothHelpObjs.isBlankString(ctt))ct = "";
        if(ct.equalsIgnoreCase("d")){
          lHash.put("CHARGE_NUM_DIRECT",dCnV);
          lHash.put("CHARGE_NUM",CnV);
          lHash.put("CHARGE_NUM_INDIRECT",iCnV);
        }else if(ct.equalsIgnoreCase("i")){
          lHash.put("CHARGE_NUM_INDIRECT",iCnV);
          lHash.put("CHARGE_NUM",CnV);
          lHash.put("CHARGE_NUM_DIRECT",dCnV);
        }else{
          lHash.put("CHARGE_NUM",CnV);
          lHash.put("CHARGE_NUM_DIRECT",dCnV);
          lHash.put("CHARGE_NUM_INDIRECT",iCnV);
        }
        lineItems.addElement(lHash);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally{
      dbrs.close();
    }

  } //end of method

  void getHeaderInfo(Hashtable header, int reqNum) {
    String query = "select wmr.waste_request_id,nvl(wmr.account_sys_id,' ') account_sys_id,nvl(to_char(wmr.requester_id),'0') requester_id,p.last_name||', '||p.first_name full_name,"+
                   "nvl(wmr.credit_card_type,' ') credit_card_type,nvl(to_char(wmr.credit_card_number),' ') credit_card_number,nvl(to_char(wmr.request_date,'mm/dd/yyyy'),' ') request_date,"+
                   "nvl(to_char(wmr.credit_card_expiration_date,'mm/dd/yyyy'),' ') credit_card_expiration_date,nvl(wmr.facility_id,' ') facility_id,nvl(f.facility_name,' ') facility_name,"+
                   "nvl(credit_card_name,' ') credit_card_name,to_char(sysdate,'mm/dd/yyyy') current_time"+
                   " from waste_mgmt_request wmr,personnel p,facility f"+
                   " where wmr.requester_id = p.personnel_id and"+
                   " wmr.facility_id = f.facility_id and wmr.waste_request_id = "+reqNum;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    //int myCount = 0;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        header.put("REQ_NUM",new Integer(reqNum));
        header.put("ACC_SYS_ID",rs.getString("account_sys_id"));

        // requestor info
        header.put("REQ_ID",new Integer(rs.getString("requester_id")));
        header.put("REQ_NAME",rs.getString("full_name"));

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
        ccH.put("CREDIT_CARD_TYPE",rs.getString("credit_card_type"));
        ccH.put("CREDIT_CARD_NUMBER",rs.getString("credit_card_number"));
        ccH.put("CREDIT_CARD_EXP_DATE",rs.getString("credit_card_expiration_date"));
        ccH.put("CREDIT_CARD_NAME",rs.getString("credit_card_name"));
        header.put("CREDIT_CARD_INFO",ccH);

        // date requested and status
        String s = rs.getString("request_date");
        String dr = rs.getString("current_time");
        String sd = "";
        String viewType = "VIEW";
        if(BothHelpObjs.isBlankString(s)){
          sd = "Draft";
          viewType = "REQUEST";
        }else{
          sd = "Submitted";
          dr = s;
        }
        header.put("DATE",dr);
        header.put("SUBMITTED_DATE",dr);
        header.put("STATUS_DESC",sd);
        header.put("VIEW_TYPE",viewType);
        header.put("QTY_COL",new Integer(WR_QTY_COL));
        header.put("FAC_NAME",rs.getString("facility_name"));
        String facID = rs.getString("facility_id");
        header.put("FAC_ID",facID);
        header.put("FACILITY_ID",facID);
        header.put("CURRENCY_ID",HelpObjs.getCurrency(db,facID));
        //account sys info
        WastePrRules pr1 = new WastePrRules(db);
        pr1.setFacilityId(facID);
        pr1.setAccountSysId(rs.getString("account_sys_id"));
        pr1.load();
        header.put("ACC_TYPE_H",pr1.getAccTypeH());
        Hashtable pack = pr1.packAccSysType(header);
        header.put("PACK",pack);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally{
      dbrs.close();
    }
  } //end of method

  //trong 1-31-01
   public void setDirectedChargeType(Hashtable lHash, Hashtable header,String chargeType) {
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
    if (chargeType.equalsIgnoreCase("d")) {
      lHash.put("CHARGE_TYPE","d");
    }else if (chargeType.equalsIgnoreCase("i")) {
      lHash.put("CHARGE_TYPE","i");
    }else {                      //first time
      if ((directed_i.equalsIgnoreCase("Y") && !directed_d.equalsIgnoreCase("Y")) || directed_d.equalsIgnoreCase("B")) {
        lHash.put("CHARGE_TYPE","i");
      }else {
        lHash.put("CHARGE_TYPE","d");
      }
    }

    //whether or not getting pre-define charge number
    if ((directed_d.equalsIgnoreCase("Y") && !directed_i.equalsIgnoreCase("N")) || (directed_i.equalsIgnoreCase("Y") && !directed_d.equalsIgnoreCase("N"))) {
      allDirected = true;
    }else {
      allDirected = false;
    }

   }
   public void getDirectedChargeTypeInfo( Hashtable lHash, Hashtable header) throws Exception {
    WasteDirectedCharge dc = new WasteDirectedCharge(db);
    dc.setFacilityId((String)header.get("FACILITY_ID"));
    dc.setAccountSysId((String)header.get("ACC_SYS_ID"));
    dc.setWasteLocationId((String)lHash.get("WASTE_LOCATION_ID"));

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
            chargeH.put("ACCT_NUM_1",number_1);
            chargeH.put("ACCT_NUM_2",number_2);
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

   public Hashtable getGenerationToStorage(String facId, String generationPt) throws Exception {
     Hashtable result = new Hashtable();
     Vector fromFacIdV = new Vector();
     Vector fromV = new Vector();
     Vector fromDesc = new Vector();
     Vector toFacIdV = new Vector();
     Vector toV = new Vector();
     Vector toDesc = new Vector();

     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       //first get transfer from locations
       String query = "select facility_id,waste_location_id,decode(location_desc,null,waste_location_id||'/'||facility_id,location_desc||'/'||facility_id) waste_location_desc"+
                      " from waste_location where (facility_id,waste_location_id) in ("+
                      " select distinct generation_facility_id,generation_location_id from waste_generation_to_storage"+
                      " where (storage_facility_id,storage_location_id) in ("+
                      " select storage_facility_id,storage_location_id from waste_generation_to_storage"+
                      " where generation_location_id = '"+generationPt+"' and generation_facility_id = '"+facId+"'))"+
                      " order by facility_id,location_desc,waste_location_id";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         fromFacIdV.addElement(rs.getString("facility_id"));
         fromV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id")));
         fromDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_desc")));
       }

       //next get transfer to locations
       query = "select facility_id,waste_location_id,decode(location_desc,null,waste_location_id||'/'||facility_id,location_desc||'/'||facility_id) waste_location_desc"+
               " from waste_location where (facility_id,waste_location_id) in ("+
               " select storage_facility_id,storage_location_id from waste_generation_to_storage"+
               " where generation_location_id = '"+generationPt+"' and generation_facility_id = '"+facId+"')"+
               " order by facility_id,location_desc,waste_location_id";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         toFacIdV.addElement(rs.getString("facility_id"));
         toV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id")));
         toDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_desc")));
       }

       //put it all together
       result.put("FROM_FACILITY_ID",fromFacIdV);
       result.put("FROM_LOCATION",fromV);
       result.put("FROM_DESC",fromDesc);
       result.put("TO_FACILITY_ID",toFacIdV);
       result.put("TO_LOCATION",toV);
       result.put("TO_DESC",toDesc);
     } catch (Exception e) {
        e.printStackTrace();
        throw e;
     } finally{
        dbrs.close();
     }
     return result;
   }

  public Vector getFromLocation( String facId) throws Exception {
    Vector result = new Vector();
    String query = "select * from waste_location where facility_id = '"+facId+"'";
    query += " and location_type = 'generation'";
    DBResultSet dbrs = null;
    ResultSet rs = null;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()) {
        result.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id")));
      }
    } catch (Exception e) {
       e.printStackTrace();
       throw e;
    } finally{
       dbrs.close();
    }
    return result;
  }

  public Hashtable getFromToLocation( String facId) throws Exception {
    Hashtable result = new Hashtable();
    Vector fromV = new Vector();
    Vector fromDesc = new Vector();
    Vector toV = new Vector();
    Vector toDesc = new Vector();
    String query = "select waste_location_id,location_desc,location_type from waste_location where facility_id = '"+facId+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()) {
        String where = BothHelpObjs.makeBlankFromNull(rs.getString("location_type"));
        if (where.equalsIgnoreCase("generation")) {
          String temp1 = BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id"));
          fromV.addElement(temp1);
          String temp2 = BothHelpObjs.makeBlankFromNull(rs.getString("location_desc"));
          fromDesc.addElement(temp1 + " - " + temp2);
        } else if (where.equalsIgnoreCase("storage")) {
          String temp1 = BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id"));
          toV.addElement(temp1);
          String temp2 = BothHelpObjs.makeBlankFromNull(rs.getString("location_desc"));
          toDesc.addElement(temp1 + " - " + temp2);

          //7-17-01 also add storage to from location i.e. storage area can also be generation point
          fromV.addElement(temp1);
          fromDesc.addElement(temp1 + " - " + temp2);
        }
      }
      result.put("FROM_LOCATION",fromV);
      result.put("FROM_DESC",fromDesc);
      result.put("TO_LOCATION",toV);
      result.put("TO_DESC",toDesc);
    } catch (Exception e) {
       e.printStackTrace();
       throw e;
    } finally{
       dbrs.close();
    }
    return result;
  }

  //trong 5/12
  // this returns a Vector of all the possible POs for a work area
  // the first element is the one that is selected
  public  Vector getPO( WasteRequestLineItem li, String facId, String accSys, String selectedPo) throws Exception {
    Vector v = new Vector();
    String pref = new String("");
    String q1 = new String("select po_number, preferred from fac_account_sys_po ");
    q1 = q1 + "where status = 'A' and facility_id = '" + facId + "' and account_sys_id = '"+accSys+"'";
    q1 = q1 + "order by po_number";
    DBResultSet dbrs = null;
    ResultSet rs = null;

    try {
      dbrs = db.doQuery(q1);
      rs=dbrs.getResultSet();
      while (rs.next()) {
        String temp = rs.getString("po_number");
        if((rs.getString("preferred")==null?false:rs.getString("preferred").equalsIgnoreCase("y"))) {
          pref = new String(temp);
        }
        if(v.contains(temp)){
          continue;
        }
        v.addElement(temp);
      }
    } catch (Exception e) {
       throw e;
    } finally{
      dbrs.close();
    }

    if (selectedPo.length() > 0) {
      pref = selectedPo;
    } else {
      pref = " ";
    }

    try {
       v.insertElementAt(pref,0);
    }catch(Exception e){
       v.insertElementAt(" ",0);
    }

    return v;
  } //end of method


  boolean hasThisChargeNum(Vector ofHash, ChargeNumber cn){
    for(int i=0;i<ofHash.size();i++){
      Hashtable h = (Hashtable)ofHash.elementAt(i);
      String t = h.get("CHARGE_TYPE").toString();
      if(!cn.getChargeType().equalsIgnoreCase(t))continue;
      String a1 = h.get("ACCT_NUM_1").toString();

      if(!cn.getAccountNumber().equalsIgnoreCase(a1))continue;
      String a2 = h.get("ACCT_NUM_2").toString();

      if(BothHelpObjs.isBlankString(cn.getAccountNumber2()) &&
         BothHelpObjs.isBlankString(a2)) return true;
      if(cn.getAccountNumber2().equalsIgnoreCase(a2))return true;
    }
    return false;
  }

}

