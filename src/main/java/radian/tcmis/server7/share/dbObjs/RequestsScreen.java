/*
SQLWKS> describe eval_oor_buy
Column Name                    Null?    Type
------------------------------ -------- ----
BRANCH_PLANT                            VARCHAR2(12)
ITEM_ID                                 NUMBER(8)
ORDER_QUANTITY                          NUMBER
RADIAN_PO                               NUMBER
PROMISED_DATE                           DATE
PART_ID                                 VARCHAR2(30)
ITEM_DESC                               CHAR(61)
ITEM_TYPE                               CHAR(3)
UOM                                     CHAR(2)
PRIORITY                                CHAR(1)
ASSIGNED_BUYER                          VARCHAR2(20)
FACILITY                                VARCHAR2(30)
SALES_ORDER                             NUMBER(8)
RAYTHEON_PO                             CHAR(25)
BUY_ORDER_INSERTED                      DATE

*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import radian.tcmis.server7.share.db.*;
import java.util.Date;
import java.awt.Color;
import oracle.jdbc.OracleTypes;

/**
 * <p>Title: Request Screen</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Trong NGo
 * @version 1.0
 *
 * 09-15-03 : Passing the cancel notes when a MR is requested for cancel
 */
public class RequestsScreen  {
   protected TcmISDBModel db;
   String user;
   int userID;
   String facility;
   String workArea;
   String item;
   boolean oFac,fac,oWork,work,appr;
   boolean allDirected = false;
   String directed_d = "";
   String directed_i = "";
   private Vector origMRQuantityV = new Vector(30);
   private Vector newMRQuantityV = new Vector(30);
	private String lineItem;

	//6-07-01
   boolean isReleaseApprover = false;

   //trong 3/3/00
   int PrNumber;

   Hashtable evalInfo = null;

   public RequestsScreen() throws Exception {
   }

   public RequestsScreen(TcmISDBModel db) throws Exception {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setOrigMRQuantity(Vector v) {
     origMRQuantityV = v;
   }

   public void setNewMRQuantity(Vector v) {
     newMRQuantityV = v;
   }

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public String buildEngEvalDockDeliverySql(String facId, String appId) {
     String query = "";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       //get dock and delivery point selection rule
       query = "select nvl(dock_selection_rule,' ') dock_selection_rule,nvl(delivery_point_selection_rule,' ') delivery_point_selection_rule,"+
               "nvl(location_id,' ') location_id,nvl(delivery_point,' ') delivery_point"+
               " from fac_loc_app where facility_id = '"+facId+"' and application = '"+appId+"'";
       dbrs = db.doQuery(query);
       rs = dbrs.getResultSet();
       String dockSelectionRule = "";
       String deliveryPointSelectionRule = "";
       String locationId = "";
       String deliveryPoint = "";
       while (rs.next()) {
         dockSelectionRule = rs.getString("dock_selection_rule");
         deliveryPointSelectionRule = rs.getString("delivery_point_selection_rule");
         locationId = rs.getString("location_id");
         deliveryPoint = rs.getString("delivery_point");
       }

       query = "select l.location_id,l.location_desc,fldp.delivery_point,nvl(fldp.delivery_point_desc,fldp.delivery_point) delivery_point_display" +
           " from fac_loc_app fla, fac_loc_delivery_point fldp, facility_dock fd, location l" +
           " where fla.facility_id = '" + facId + "' and fla.application = '" + appId + "' and fla.facility_id = fldp.facility_id and fldp.status = 'A'"+
           " and fldp.facility_id = fd.facility_id and fldp.location_id = fd.dock_location_id and fd.status = 'A' and fd.dock_location_id = l.location_id";
       //fixed - dock and delivery point
       if ("FIXED".equalsIgnoreCase(dockSelectionRule) && "FIXED".equalsIgnoreCase(deliveryPointSelectionRule)) {
         //test this in case the rules are set wrong
         if (!BothHelpObjs.isBlankString(locationId)) {
           query += " and fla.location_id = fldp.location_id";
         }
         if (!BothHelpObjs.isBlankString(deliveryPoint)) {
           query += " and fla.delivery_point = fldp.delivery_point";
         }
       }else if ("FIXED".equalsIgnoreCase(dockSelectionRule)) {
         //test this in case the rules are set wrong
         if (!BothHelpObjs.isBlankString(locationId)) {
           query += " and fla.location_id = fldp.location_id";
         }
       }
     }catch (Exception e) {
       //if error then get all docks and delivery points for facility
       query = "select a.location_id,a.location_desc,b.delivery_point,nvl(b.delivery_point_desc,b.delivery_point) delivery_point_display"+
               " from location a, fac_loc_delivery_point b, facility_dock c "+
               "where a.location_id = b.location_id and a.location_id = c.dock_location_id and c.facility_id = b.facility_id and c.status = 'A' "+
               "and b.status = 'A' and c.facility_id = '"+facId+"' order by location_desc,delivery_point_display";
     }finally {
       dbrs.close();
     }
     return query;
   } //end of method

   public Hashtable getEngEvalMRInfo(int requestID) throws Exception {
    Hashtable result = new Hashtable(50);
    String[] chargeCols = {"Charge Number 1","Charge Number 2","%"};
    int[] chargeColWidths = {96,96,80};
    int[] chargeColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING};
    String[] preOrderCols = {"Facility","Work Area","User Group","Qty","Date","Requestor - Phone"};
    int[] preOrderColWidths = {110,130,100,70,100,200};
    int[] preOrderColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING};
    result.put("CHARGE_COLS",chargeCols);
    result.put("CHARGE_COL_WIDTHS",chargeColWidths);
    result.put("CHARGE_COL_TYPES",chargeColTypes);
    result.put("PREORDER_COLS",preOrderCols);
    result.put("PREORDER_COL_WIDTHS",preOrderColWidths);
    result.put("PREORDER_COL_TYPES",preOrderColTypes);

    String query = "select decode(b.request_line_status,'Draft Eval','Request',b.request_line_status) pr_status,b.line_item,a.pr_number,a.end_user,a.department,a.account_sys_id,a.facility_id,c.application,"+
                   "c.application_desc application_display,b.item_id,decode(b.catalog_price,null,'',b.catalog_price*b.quantity||' '||b.currency_id) extended_price,b.quantity,b.critical,b.notes,b.currency_id,b.catalog_price,"+
                   "b.ship_to_location_id,b.delivery_point,decode(b.charge_type,null,'d',b.charge_type) charge_type,b.po_number,b.release_number,to_char(b.required_datetime, 'mm/dd/yyyy') required_datetime,"+
                   "decode(c.dock_selection_rule,'FIXED',c.location_id,'') location_id,a.company_id  "+
                   "from purchase_request a, request_line_item b, fac_loc_app c where a.pr_number = b.pr_number and "+
                   "a.facility_id = c.facility_id and b.application = c.application and a.request_id = "+requestID;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Hashtable headerInfo = new Hashtable(60);
      while(rs.next()) {
        headerInfo.put("PR_STATUS",BothHelpObjs.makeBlankFromNull(rs.getString("pr_status")));
        headerInfo.put("PR_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("pr_number")));
        headerInfo.put("LINE_ITEM",BothHelpObjs.makeBlankFromNull(rs.getString("line_item")));
        headerInfo.put("END_USER",BothHelpObjs.makeBlankFromNull(rs.getString("end_user")));
        headerInfo.put("DEPARTMENT",BothHelpObjs.makeBlankFromNull(rs.getString("department")));
        headerInfo.put("ACC_SYS_ID",BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
        headerInfo.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
        headerInfo.put("APPLICATION",BothHelpObjs.makeBlankFromNull(rs.getString("application")));
        headerInfo.put("APPLICATION_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
        headerInfo.put("LOCATION_ID",BothHelpObjs.makeBlankFromNull(rs.getString("location_id")));
        headerInfo.put("ITEM_ID",BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        headerInfo.put("ITEM_DESC","TBD");
        headerInfo.put("EXTENDED_PRICE",BothHelpObjs.makeBlankFromNull(rs.getString("extended_price")));
        headerInfo.put("QTY",BothHelpObjs.makeBlankFromNull(rs.getString("quantity")));
        headerInfo.put("CRITICAL",BothHelpObjs.makeBlankFromNull(rs.getString("critical")));
        headerInfo.put("NOTES",BothHelpObjs.makeBlankFromNull(rs.getString("notes")));
        headerInfo.put("CHARGE_TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("charge_type")));
        headerInfo.put("SHIP_TO_LOCATION",BothHelpObjs.makeBlankFromNull(rs.getString("ship_to_location_id")));
        headerInfo.put("DELIVERY_POINT",BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point")));
        headerInfo.put("PO_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("po_number")));
        headerInfo.put("RELEASE_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("release_number")));
        headerInfo.put("NEED_DATE",BothHelpObjs.makeBlankFromNull(rs.getString("required_datetime")));
        headerInfo.put("CATALOG_PRICE",BothHelpObjs.makeBlankFromNull(rs.getString("catalog_price")));
        headerInfo.put("CURRENCY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("currency_id")));
		  headerInfo.put("COMPANY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("company_id")));
		}
      //get currency list
      headerInfo.put("CURRENCY_LIST",HelpObjs.getCurrencyList(db,"currency_description",(String)headerInfo.get("CURRENCY_ID"), rs.getString("pr_number")));

      result.put("HEADER_INFO",headerInfo);
      //use fac_loc_app rule to get docks and delivery points
      query = buildEngEvalDockDeliverySql((String)headerInfo.get("FACILITY_ID"),(String)headerInfo.get("APPLICATION"));
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector locID = new Vector(5);
      Vector locDesc = new Vector(5);
      String lastLocID = "";
      Hashtable locDelH = new Hashtable(10);
      while(rs.next()) {
        String currentLocID = BothHelpObjs.makeBlankFromNull(rs.getString("location_id"));
        if (lastLocID.equals(currentLocID)){
          Vector delID = (Vector)locDelH.get(currentLocID+"_ID");
          Vector delDesc = (Vector)locDelH.get(currentLocID+"_DESC");
          delID.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point")));
          delDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point_display")));
          locDelH.put(currentLocID+"_ID",delID);
          locDelH.put(currentLocID+"_DESC",delDesc);
        }else {
          locID.addElement(currentLocID);
          locDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("location_desc")));
          Vector delID = new Vector();
          Vector delDesc = new Vector();
          delID.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point")));
          delDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point_display")));
          locDelH.put(currentLocID+"_ID",delID);
          locDelH.put(currentLocID+"_DESC",delDesc);
        }
        lastLocID = currentLocID;
      }
      //add dock to list if dock is not in list
      String tmpDock = (String) headerInfo.get("SHIP_TO_LOCATION");
      if (tmpDock.length() > 0) {
        if (!locID.contains(tmpDock)) {
          locID.addElement(tmpDock);
          locDesc.addElement(tmpDock);
          String tmpDelivery = (String) headerInfo.get("DELIVERY_POINT");
          if (tmpDelivery.length() > 0) {
           Vector tmpDelIDV = new Vector(1);
           Vector tmpDelDescV = new Vector(1);
           tmpDelIDV.addElement(tmpDelivery);
           tmpDelDescV.addElement(tmpDelivery);
           locDelH.put(tmpDock+"_ID",tmpDelIDV);
           locDelH.put(tmpDock+"_DESC",tmpDelDescV);
          }else {
            tmpDelivery = "Select one";
            Vector tmpDelIDV = new Vector(1);
            Vector tmpDelDescV = new Vector(1);
            tmpDelIDV.addElement(tmpDelivery);
            tmpDelDescV.addElement(tmpDelivery);
            locDelH.put(tmpDock+"_ID",tmpDelIDV);
            locDelH.put(tmpDock+"_DESC",tmpDelDescV);
          }
        }
      }
      //if more then on dock add Select One
      if (locID.size() > 1) {
        locID.insertElementAt("Select One",0);
        locDesc.insertElementAt("Select One",0);
        Vector tmpDelIDV = new Vector(1);
        Vector tmpDelDescV = new Vector(1);
        tmpDelIDV.addElement("Select One");
        tmpDelDescV.addElement("Select One");
        locDelH.put("Select One"+"_ID",tmpDelIDV);
        locDelH.put("Select One"+"_DESC",tmpDelDescV);
      }
      //now put it all together
      result.put("DELIVERY_INFO",locDelH);
      result.put("LOCATION_ID",locID);
      result.put("LOCATION_DESC",locDesc);
      //deliver date
      if (BothHelpObjs.isBlankString((String)headerInfo.get("NEED_DATE"))) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) < 13) {
          if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ||
              (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            calendar.add(Calendar.DATE,24);
          }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE,23);
          }else {
            calendar.add(Calendar.DATE,21);
          }
        }else {
          if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) ||
              (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
            calendar.add(Calendar.DATE,25);
          }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE,23);
          } else {
            calendar.add(Calendar.DATE,22);
          }
        }
        String bdate = new String((calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.YEAR));
        //headerInfo.put("NEED_DATE",BothHelpObjs.formatDate("toCharfromNum",bdate));
        headerInfo.put("NEED_DATE",bdate);
      }    //end of if need_date is blank string

		//get charge number
      getChargeNumberInfo(result,"1",(String)headerInfo.get("CHARGE_TYPE"));

		//get PO
      LineItemView rli = new LineItemView();
		rli.setCompanyId((String)headerInfo.get("COMPANY_ID"));
		rli.setApplication((String)headerInfo.get("APPLICATION"));
      rli.setPO((String)headerInfo.get("PO_NUMBER"));
      rli.setPRNumber(Integer.parseInt((String)headerInfo.get("PR_NUMBER")));
      rli.setLineItem((String)headerInfo.get("LINE_ITEM"));
		Vector poV = getPO(rli,(String)headerInfo.get("FACILITY_ID"),(String)headerInfo.get("ACC_SYS_ID"),result);
      String myPo = "";
      if (poV.size() > 0) {
        myPo = poV.elementAt(0).toString();
      }
      headerInfo.put("PO_NUMBER",myPo);
      result.put("ALL_PO",poV);

      //now get previous evaluation orders
      Vector previousOrderV = new Vector();
        query = "select f.facility_name,fla.application_desc application_display,"+
                "caug.user_group_id,rli.quantity qty,carn.submit_date,p.last_name||', '||p.first_name||' - '||p.phone  requestor"+
                " from catalog_add_request_new carn, catalog_add_item cai, catalog_add_user_group caug, facility f, fac_loc_app fla, personnel p,"+
					 "purchase_request pr, request_line_item rli"+
					 " where carn.request_id = caug.request_id and caug.facility_id = f.facility_id and caug.facility_id = fla.facility_id and"+
                " caug.work_area = fla.application and carn.requestor = p.personnel_id and carn.request_status = 12"+
					 " and carn.company_id = pr.company_id and carn.request_id = pr.request_id and pr.company_id = rli.company_id and pr.pr_number = rli.pr_number"+
					 " and carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and (cai.company_id,cai.item_id) ="+
					 " (select company_id,item_id from catalog_add_item where item_id <> 442554 and line_item = 1 and part_id = 1 and request_id = "+requestID+")"+
                " order by f.facility_name,application_display";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
          String[] oa = new String[preOrderCols.length];
          int c = 0;
          oa[c++] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_name"));
          oa[c++] = BothHelpObjs.makeBlankFromNull(rs.getString("application_display"));
          oa[c++] = BothHelpObjs.makeBlankFromNull(rs.getString("user_group_id"));
          oa[c++] = BothHelpObjs.makeBlankFromNull(rs.getString("qty"));
          oa[c++] = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("submit_date")));
          oa[c++] = BothHelpObjs.makeBlankFromNull(rs.getString("requestor"));
          previousOrderV.addElement(oa);
        }
      result.put("PREVIOUS_ORDERS",previousOrderV);
    }catch(Exception e){
      e.printStackTrace();
      throw(e);
    }finally{
      dbrs.close();
    }
    return result;
   }  //end of method getEngEvalMRInfo

  void getChargeNumberInfo(Hashtable result, String lineItem, String chargeType) throws Exception {
    try {
      Hashtable headerInfo = (Hashtable)result.get("HEADER_INFO");
      Hashtable chargeInfo = new Hashtable(50);
      boolean canEditD = false;
      boolean canEditI = false;
      PrRules pr = new PrRules(db);
      pr.setFacilityId((String)headerInfo.get("FACILITY_ID"));
      pr.setAccountSysId((String)headerInfo.get("ACC_SYS_ID"));
      pr.load();
      headerInfo.put("ACC_TYPE_H",pr.getAccTypeH());
      headerInfo.put("PACK",pr.packAccSysType(headerInfo));

      PRAccount PRA = new PRAccount(db) ;
      PRA.setPRNumber(Integer.parseInt((String)headerInfo.get("PR_NUMBER")));
      PRA.setLineItem(lineItem);
      Vector prav = PRA.getMultiAcctNumbers();
      Vector cnums = new Vector();
      Vector dCnV = new Vector();
      Vector iCnV = new Vector();
      Vector CnV = new Vector();
      for(int q=0;q<prav.size();q++){
        PRAccount myPra = (PRAccount)prav.elementAt(q);
        Hashtable prh = new Hashtable();
        prh.put("ACCT_NUM_1",myPra.getAccountNumber());
        prh.put("ACCT_NUM_2",myPra.getAccountNumber2());
        prh.put("PERCENTAGE",myPra.getPct());
        prh.put("CHARGE_TYPE",chargeType);
        cnums.addElement(prh);
      }
      //show charge number if charge number is in pr_account
      if (cnums.size() > 0 ) {
        Hashtable packH = (Hashtable)headerInfo.get("PACK");
        Hashtable accSysTypeH = (Hashtable)packH.get((String)headerInfo.get("ACC_SYS_ID")+chargeType);
        accSysTypeH.put("PR_ACCOUNT_REQUIRED","y");
      }

      if("d".equalsIgnoreCase(chargeType)){
        dCnV = cnums;
      }else if("i".equalsIgnoreCase(chargeType)){
        iCnV = cnums;
      }else{
        CnV = cnums;
      }

      if("REQUEST".equalsIgnoreCase((String)headerInfo.get("PR_STATUS"))){        //this is for eng eval for section 4 of 4 so it is okay.
        getDirectedChargeTypeInfoEval(chargeInfo,headerInfo);
        setDirectedChargeTypeEval(chargeInfo,headerInfo,chargeType);

        if (!allDirected) {                 //only uses directed charge number
          // get pre defined charge numbers
          ChargeNumber cn = new ChargeNumber(db);
          Vector cnV = cn.getChargeNumsForFacNew(headerInfo,"Material");
          for(int r=0;r<cnV.size();r++){
            ChargeNumber myCN = (ChargeNumber)cnV.elementAt(r);
            Hashtable cnh = new Hashtable();
            cnh.put("ACCT_NUM_1",myCN.getAccountNumber());
            cnh.put("ACCT_NUM_2",myCN.getAccountNumber2());
            //if there is only one charge number then set percentage to 100 trong 7-19
            if (cnV.size() == 1) {
              cnh.put("PERCENTAGE","100");
            }else {
              cnh.put("PERCENTAGE","");
            }
            //don't need this cnh.put("CHARGE_TYPE",myCN.getChargeType());

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
          Hashtable tempH = (Hashtable)headerInfo.get("ACC_TYPE_H");
          Hashtable tempH2 = (Hashtable)tempH.get((String)headerInfo.get("ACC_SYS_ID"));
          Vector display1 = (Vector)tempH2.get("DISPLAY_1");
          Vector display2 = (Vector)tempH2.get("DISPLAY_2");
          Vector chargeTypeV = (Vector)tempH2.get("CHARGE_TYPE");
          for (int ii = 0; ii < chargeTypeV.size(); ii++) {
            if (chargeTypeV.elementAt(ii).toString().equalsIgnoreCase("i")) {
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
        }
      }else{
        canEditD = false;
        canEditI = false;
      }

      if (directed_d.equalsIgnoreCase("Y")) {
        chargeInfo.put("CAN_EDIT_CHARGE_NUM_D",new Boolean(false));
      }else {
        chargeInfo.put("CAN_EDIT_CHARGE_NUM_D",new Boolean(canEditD));
      }
      if (directed_i.equalsIgnoreCase("Y")) {
        chargeInfo.put("CAN_EDIT_CHARGE_NUM_I",new Boolean(false));
      }else {
        chargeInfo.put("CAN_EDIT_CHARGE_NUM_I",new Boolean(canEditI));
      }

      if(BothHelpObjs.isBlankString(chargeType))chargeType = "";
      chargeInfo.put("CHARGE_NUM",CnV);
      chargeInfo.put("CHARGE_NUM_DIRECT",dCnV);
      chargeInfo.put("CHARGE_NUM_INDIRECT",iCnV);

		if (directed_d.equalsIgnoreCase("Y")) {
			result.put("CHARGE_TYPE", "d");
		}else if (directed_i.equalsIgnoreCase("Y")) {
			result.put("CHARGE_TYPE", "i");
		}

		result.put("CHARGE_INFO"+lineItem,chargeInfo);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
   } //end of method getChargeNumberInfo


   //this method return xml document and url for seagate punchout
   public Hashtable getPunchoutOrderMessage(String payLoadID) throws Exception {
    Hashtable result = new Hashtable();
    result.put("document","");
    result.put("url","");
    String query = "select browser_form_post from punchout_order_message where payload_id = '"+payLoadID+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        //result.put("document",BothHelpObjs.makeBlankFromNull(rs.getString("xml_document")));
        result.put("url",BothHelpObjs.makeBlankFromNull(rs.getString("browser_form_post")));
      }
    }catch(Exception e){
      e.printStackTrace();
      throw(e);
    }finally{
      dbrs.close();
    }
    return result;
   }

   //This method return finance status of this request
   public String financeRejectedPR(int prNum) throws Exception {
    String result = "no";
    String query = "select count(*) from request_line_item where use_approval_status != 'rejected' and pr_number = "+prNum;
    try {
      int count = DbHelpers.countQuery(db,query);
      if (count > 0) {
        result = "rejected";
      }else {
        result = "no";
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
    return result;
   }

  public Hashtable getDPASValues() throws Exception {
    Hashtable result = new Hashtable();
    String query = "select dpas_code,dpas_code || ' - ' ||displayed_description  displayed_desc from vv_dpas";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector idV = new Vector();
    Vector descV = new Vector();
    descV.addElement("No Priority");
    idV.addElement("None");
    /*
    descV.addElement("Defense Aircraft");
    descV.addElement("Defense Missiles");
    descV.addElement("Defense Navigational");
    descV.addElement("Defense Weapons");
    descV.addElement("Defense Ammunition");
    idV.addElement("DO-A1");
    idV.addElement("DO-A2");
    idV.addElement("DO-A3");
    idV.addElement("DO-A5");
    idV.addElement("DO-A6"); */
    // can't do this until Nish enter data
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        idV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("dpas_code")));
        descV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("displayed_desc")));
      }
    }catch(Exception e){
      e.printStackTrace();
      throw(e);
    }finally{
      dbrs.close();
    }
    result.put("DPAS_ID",idV);
    result.put("DPAS_DESC",descV);
    return (result);
   }

  public void getHeaderInfo(int userID, int prNumber, Hashtable header) throws Exception {
    try {
      String query = "select pr.pr_number,pr.account_sys_id,pr.engineering_evaluation,pr.end_user,pr.department,pr.contact_info,"+
                     "pr.requestor,p.last_name||', '||p.first_name requestor_name,pr.credit_card_type,pr.credit_card_number,"+
                     "pr.credit_card_name,to_char(pr.credit_card_expiration_date,'MM/yyyy') credit_card_expiration_date,pr.facility_id,f.facility_name,f.preferred_warehouse,"+
                     "pr.request_date,pr.pr_status,prs.pr_status_desc,pr.requested_finance_approver,pr.requested_releaser"+
                     ",to_char(nvl(pr.submitted_date,sysdate),'mm/dd/yyyy') submitted_date,f.ecommerce"+
                     " from purchase_request pr,facility f,personnel p,vv_pr_status prs where pr.pr_number = "+prNumber+" and"+
                     " pr.facility_id = f.facility_id and pr.pr_status = prs.pr_status and pr.requestor = p.personnel_id";

      DBResultSet dbrs = null;
      int requestorID = 0;
      String facilityID = "";
      String status = "";
      try {
        dbrs = db.doQuery(query);
        ResultSet rs=dbrs.getResultSet();
        while(rs.next()) {
          header.put("ACC_SYS_ID",BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
          header.put("REQ_NUM",rs.getString("pr_number"));
          header.put("EVAL",new Boolean("Y".equalsIgnoreCase(rs.getString("engineering_evaluation"))));
          header.put("END_USER",BothHelpObjs.makeBlankFromNull(rs.getString("end_user")));
          header.put("DEPARTMENT",BothHelpObjs.makeBlankFromNull(rs.getString("department")));
          header.put("CONTACT_INFO",BothHelpObjs.makeBlankFromNull(rs.getString("contact_info")));
          header.put("REQUESTOR",BothHelpObjs.makeBlankFromNull(rs.getString("requestor_name")));
          requestorID = BothHelpObjs.makeZeroFromNull(rs.getString("requestor"));
          header.put("REQUESTOR_ID",new Integer(requestorID));
          header.put("FACILITY",BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
          facilityID = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
          header.put("FACILITY_ID",facilityID);
          header.put("HUB",BothHelpObjs.makeBlankFromNull(rs.getString("preferred_warehouse")));
          header.put("DATE",BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("request_date"))));
          status = BothHelpObjs.makeBlankFromNull(rs.getString("pr_status"));
          header.put("STATUS",status);
          header.put("STATUS_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("pr_status_desc")));
          //Credit Card Info
          Hashtable ccH = new Hashtable();
          Hashtable creditCardTypeH = HelpObjs.getCreditCardType(db);
          ccH.put("CREDIT_CARD_TYPE_ID",(Vector)creditCardTypeH.get("CREDIT_CARD_TYPE_ID"));
          ccH.put("CREDIT_CARD_TYPE_DESC",(Vector)creditCardTypeH.get("CREDIT_CARD_TYPE_DESC"));
          ccH.put("CREDIT_CARD_TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_type")));
          ccH.put("CREDIT_CARD_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_number")));
          ccH.put("CREDIT_CARD_EXP_DATE",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_expiration_date")));
          ccH.put("CREDIT_CARD_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_name")));
          header.put("CREDIT_CARD_INFO",ccH);
          header.put("SUBMITTED_DATE",rs.getString("submitted_date"));
			 header.put("ECOMMERCE",rs.getString("ecommerce"));
		  }
      }catch (Exception ex) {
        ex.printStackTrace();
      }finally {
        dbrs.close();
      }

      //Type of view
      Personnel thisUser = new Personnel(db);
      thisUser.setPersonnelId(userID);
      thisUser.setFacilityId(facilityID);
      //if current user is requestor alternate requestor then set requestor or MR to this user
      if (thisUser.isAlternateRequestor(prNumber)) {
        header.put("REQUESTOR_ID",new Integer(userID));
        requestorID = userID;
      }

      String viewType = "VIEW";
      if (status.equals("entered")){ // incomplete
		  if (((Boolean)header.get("EVAL")).booleanValue()) {
		  	 header.put("STATUS_DESC","Pending Use Approval");
		  }else {
			if (requestorID == userID){
          	viewType = "REQUEST";
        	}
		  }
		  header.put("PENDING_FINANCE","pending");       //override default setting
      }else if (status.equals("compchk")){  // to be approved
        if ("Y".equalsIgnoreCase((String)header.get("ECOMMERCE"))) {
          viewType = "VIEW";
        }else {
          FinanceApproverList fal = new FinanceApproverList(db);
          if(fal.canApprovePR(prNumber ,new Integer(userID))){
            viewType = "APPROVE";
          }
        }
        header.put("PENDING_FINANCE","pending");       //override default setting
      }else if (status.equals("approved")){  // to be released
        if(thisUser.isReleaser()){
          viewType = "RELEASE";
        }
        header.put("PENDING_FINANCE","approved");
      }else if (status.equals("rejected")){  // rejected
        if (requestorID == userID){
          viewType = "VIEW";
        }
        header.put("PENDING_FINANCE",financeRejectedPR(prNumber));
      }else if (status.equals("posubmit")){  // done
        viewType = "VIEW";
        header.put("PENDING_FINANCE","approved");
      }else if (status.equals("compchk2")){
        header.put("PENDING_FINANCE","approved");
      }else if (status.equals("compchk3")){
        viewType = "VIEW";
        header.put("PENDING_FINANCE","pending");
      }

      //if user is both finance approver and user approver then show finance approver data first
      if (!status.equals("compchk") && !"APPROVE".equalsIgnoreCase(viewType) && !status.equals("rejected")){
        if (UseApprover.needMyUseApproval(db,prNumber,userID)) {     //if request line need my use approval
          viewType = "USE_APPROVE";
			 header.put("USE_APPROVAL_TYPE","useLimit");
		  }else if (ListApprover.needMyListApproval(db,prNumber,"",(new Integer(userID)).toString())) {
			 viewType = "USE_APPROVE";
			 header.put("USE_APPROVAL_TYPE","listLimit");
		  }
      }
      header.put("VIEW_TYPE",viewType);

      //find out whether or not I can edit MR
      if (!"REQUEST".equalsIgnoreCase(viewType)) {
        if (((Boolean)header.get("EVAL")).booleanValue()) {
          //approvers cannot edit Engineering Evaluation orders.
          header.put("CAN_EDIT_MR",new Boolean(false));
          header.put("CAN_EDIT_MR_QTY",new Boolean(false));
        }else {
          if (requestorID == userID) {
            header.put("CAN_EDIT_MR",new Boolean(true));
          }else {
            header.put("CAN_EDIT_MR", new Boolean(HelpObjs.countQuery(db, "select count(*) from user_group_member where user_group_id = 'EditMR' and facility_id = '" + facilityID + "' and personnel_id = " + userID) > 0));
          }
          header.put("CAN_EDIT_MR_QTY", new Boolean(HelpObjs.countQuery(db, "select count(*) from user_group_member where user_group_id = 'EditMRQty' and facility_id = '" + facilityID + "' and personnel_id = " + userID) > 0));
        }
      }else {
		  if (((Boolean)header.get("EVAL")).booleanValue()) {
			 header.put("CAN_EDIT_MR",new Boolean(false));
          header.put("CAN_EDIT_MR_QTY",new Boolean(false));
		  }else {
		  	 header.put("CAN_EDIT_MR",new Boolean(true));
       	 header.put("CAN_EDIT_MR_QTY",new Boolean(true));
		  }
		}

      //weekend deliveries
      header.put("ALLOW_WEEKEND_DELIVERIES", new Boolean(HelpObjs.countQuery(db, "select count(*) from facility where always_open = 'Y' and facility_id = '" + facilityID + "'") < 1));

      //DPAS values
      header.put("DPAS_VALUES",getDPASValues());
      header.put("DEL_TYPES",DeliverySchedule.getAllDeliveryTypes());

      //trong 4/5
      PrRules pr = new PrRules(db);
      pr.setFacilityId(facilityID);
      pr.setAccountSysId(header.get("ACC_SYS_ID").toString());
      pr.load();
      header.put("ACC_TYPE_H",pr.getAccTypeH());
      Hashtable pack = pr.packAccSysType(header);
      header.put("PACK",pack);

      //1-21-03 getting MR allocation info
      if (status.equals("posubmit")) {
		  //no longer need to call this because we are using the web mr allocation
		  //header.put("MR_ALLOCATION_INFO",getMRAllocationInfo(prNumber));
		  header.put("MR_ALLOCATION_INFO",new Vector(0));
		}

      //show currency
      if (HelpObjs.showCurrency(db)) {
        header.put("CURRENCY_ID",HelpObjs.getCurrency(db,facilityID));
      }else {
        header.put("CURRENCY_ID","");
      }
      //get list of currency
      header.put("CURRENCY_LIST",HelpObjs.getCurrencyList(db,"currency_description",(String)header.get("CURRENCY_ID"), "" + prNumber));

    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Vector getMRAllocationInfo(int prNumber) throws Exception {
      Vector result = new Vector(50);
      String query = "";
      Connection connect1 = null;
      CallableStatement cs = null;
      try {
        connect1 = db.getConnection();
        connect1.commit();
        connect1.setAutoCommit(false);                       //since we will be writing data to a temporary table turn off auto commit
        cs = connect1.prepareCall("{call pkg_mr_allocation_report.PR_MR_ALLOCATION_REPORT (?,?)}");
        cs.setInt(1,prNumber);
        cs.registerOutParameter(2, OracleTypes.VARCHAR);
        cs.execute();
        query = (String)cs.getObject(2);

        Statement st = connect1.createStatement();
		  //adding this to give us the flexibility of show allocation by line
		  if (!BothHelpObjs.isBlankString(lineItem)) {
		  	query = "select * from ("+query+") where line_item = "+lineItem;
		  }
		  ResultSet rs = st.executeQuery(query);
        while(rs.next()) {
          Hashtable h = new Hashtable(17);
          h.put("MR_LINE",rs.getString("pr_number")+"-"+rs.getString("line_item"));
          h.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(rs.getString("application_desc")));
          h.put("CAT_PART_NO",rs.getString("fac_part_no"));
          h.put("ITEM_TYPE",rs.getString("item_type"));
          h.put("DELIVERY_TYPE",rs.getString("delivery_type"));
          h.put("NEED_DATE",rs.getString("required_datetime"));
          h.put("ORDERED_QTY",rs.getString("order_quantity"));
          h.put("SOURCE",BothHelpObjs.makeBlankFromNull(rs.getString("ref")));
          h.put("ALLOCATED_QTY",BothHelpObjs.makeBlankFromNull(rs.getString("allocated_quantity")));
          h.put("PROJECTED_DELIVERY",BothHelpObjs.makeBlankFromNull(rs.getString("allocation_reference_date")));
          h.put("NOTES",BothHelpObjs.makeBlankFromNull(rs.getString("notes")));
          h.put("STATUS",BothHelpObjs.makeBlankFromNull(rs.getString("status")));
          h.put("RECEIPT_DOCUMENT",BothHelpObjs.makeBlankFromNull(rs.getString("receipt_document")));
          result.addElement(h);
        }
      }catch(Exception e){
        e.printStackTrace();
        radian.web.emailHelpObj.sendtrongemail("Error while get data for MR allocation report","MR number: "+prNumber);
      }finally{
        connect1.commit();
        connect1.setAutoCommit(true);
        cs.close();
      }
      return result;
    }

  public Vector getMRAllocationInfoOld(int prNumber) {
    Vector result = new Vector(50);
    String query = "select * from mr_allocation_report_view where pr_number = "+prNumber+" and allocated_quantity > 0 order by to_number(line_item),reference_date";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        Hashtable h = new Hashtable(11);
        h.put("MR_LINE",rs.getString("pr_number")+"-"+rs.getString("line_item"));
        h.put("WORK_AREA",rs.getString("application_desc"));
        h.put("CAT_PART_NO",rs.getString("fac_part_no"));
        h.put("ITEM_TYPE",rs.getString("item_type"));
        h.put("DELIVERY_TYPE",rs.getString("delivery_type"));
        h.put("NEED_DATE",rs.getString("required_datetime"));
        h.put("ORDERED_QTY",rs.getString("order_quantity"));
        h.put("SOURCE",BothHelpObjs.makeBlankFromNull(rs.getString("ref")));
        h.put("ALLOCATED_QTY",BothHelpObjs.makeBlankFromNull(rs.getString("allocated_quantity")));
        h.put("PROJECTED_DELIVERY",BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("reference_date"))));
        h.put("NOTES",BothHelpObjs.makeBlankFromNull(rs.getString("notes")));
        h.put("STATUS",BothHelpObjs.makeBlankFromNull(rs.getString("status")));
        result.addElement(h);
      }
    }catch(Exception e){
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("Error while get data for MR allocation report","MR number: "+prNumber);
    }finally{
      dbrs.close();
    }
    return result;
  }

  //switch this when ready - this method improve the number to query
  public Vector getScreenData(int userID,int rNum) throws Exception {
     Vector outputVector = new Vector();
     Hashtable header = new Hashtable();
     Vector detail = new Vector();
     boolean canEditI = false;
     boolean canEditD = false;

     //4-26-01 set extended price -> 'tbd' if item with pkg = 'fill up bulk'
     boolean contFillUpBulk = false;
    try {
      getHeaderInfo(userID,rNum,header);
      Boolean evalFlag = (Boolean)header.get("EVAL");
      if (evalFlag.booleanValue()){
        detail = getEvalScreenData(header,rNum);
      }else {
        //get list of line item that might be dropship_override
        Vector dropShipOverrideLineItems = getDropShipOverrideLineItems((String)header.get("STATUS"),(String)header.get("VIEW_TYPE"),rNum);
        //Item Table
        RequestItems RI = new RequestItems(db) ;
        RI.setPRNumber(rNum);

        //HERE is where I need to do the logic to get line item depending on the user(s) privileges
        //i.e. if user is an usage approver then only display the line item needing his/her approval
        //Vector lineItems = RI.getItems();     //this will do for now
        String viewType = (String)header.get("VIEW_TYPE");
        String facId = (String)header.get("FACILITY_ID");
        Vector lineItems;
        if (viewType.equalsIgnoreCase("USE_APPROVE")) {
			 RI.setUseApprovalType((String)header.get("USE_APPROVAL_TYPE"));
			 lineItems = RI.getItemsForUseApprover(header,userID,rNum);
		  }else {
          lineItems = RI.getItems();
        }

        LineItemView rli;
        Hashtable cNum = new Hashtable();
        Hashtable cPor = new Hashtable();
        Enumeration E;

        boolean mrFullyDelivered = true;
        boolean mrCancelled = true;
        for (int i=0;i<lineItems.size();i++) {
          rli = (LineItemView)lineItems.elementAt(i);
          Hashtable tli = new Hashtable();
          String ct = BothHelpObjs.makeBlankFromNull(rli.getChargeType());
          if(BothHelpObjs.isBlankString(ct)){
            ct = "d";
          }
          tli.put("CHARGE_TYPE",ct);
          tli.put("LINE_NUM",rli.getLineItem());
          tli.put("PART_NUM",rli.getFacPartNo());
          tli.put("DPAS",rli.getDPAS());
          //******* check
          tli.put("CANCEL_STATUS",rli.getCancelStatus());
          if (!"canceled".equalsIgnoreCase(rli.getCancelStatus())) {
            mrCancelled = false;
          }
          tli.put("WORK_AREA",rli.getApplicationDisplay());
          tli.put("WORK_AREA_ID",rli.getApplication());  //trong 1-31-01
          tli.put("SCRAP_FLAG",new Boolean(rli.getScrap() != null &&  rli.getScrap().equalsIgnoreCase("y")));
          tli.put("CRIT_FLAG",new Boolean(rli.getCritical() != null &&  rli.getCritical().equalsIgnoreCase("y")));
          tli.put("QTY",rli.getQuantity().toString());
          tli.put("USE_APPROVAL_STATUS",BothHelpObjs.makeBlankFromNull(rli.getUseApprovaltatus()));
          tli.put("USE_APPROVER",BothHelpObjs.makeBlankFromNull(rli.getUseApprover()));
          tli.put("USE_APPROVAL_DATE",BothHelpObjs.makeBlankFromNull(rli.getUseApprovalDate()));
          tli.put("USE_APPROVAL_COMMENT",BothHelpObjs.makeBlankFromNull(rli.getUseApprovalComment()));
          tli.put("DOC_NUM",rli.getDocNum());
          tli.put("DOC_STATE",rli.getDocState());
          tli.put("NOTES",rli.getNotes()==null?"":rli.getNotes());
          tli.put("CATALOG_ID",rli.getCatalogID());
			 tli.put("CATALOG_COMPANY_ID",rli.getCatalogCompanyId());
			 tli.put("COMPANY_ID",rli.getCompanyId());
			 String myPrice = rli.getUnitPrice();
          if(BothHelpObjs.isBlankString(myPrice) || "Requested".equalsIgnoreCase(myPrice)) {
            tli.put("UNIT_PRICE","");
          }else{
            tli.put("UNIT_PRICE",myPrice);
          }
          String currentCatalogPrice = rli.getCurrentCatalogPrice();
          if(BothHelpObjs.isBlankString(currentCatalogPrice) || "Requested".equalsIgnoreCase(currentCatalogPrice)) {
            tli.put("CURRENT_CATALOG_PRICE","");
          }else{
            tli.put("CURRENT_CATALOG_PRICE",currentCatalogPrice);
          }
          tli.put("CURRENT_CURRENCY_ID",rli.getCurrentCurrencyId());
          tli.put("CURRENCY_ID",rli.getCurrencyID());
          tli.put("ITEM_TYPE",BothHelpObjs.isBlankString(rli.getRequestedItemType())?"":rli.getRequestedItemType());
          tli.put("CUSTOMER_REQUISITION_NUMBER",rli.getCustomerRequisitionNumber());
          tli.put("TOTAL_INVOICE_PREP_QTY",rli.getTotalInvoicePrepQty());
          tli.put("TOTAL_QTY_ISSUED",rli.getTotalQtyIssued());
          tli.put("TOTAL_QTY_ALLOCATED",rli.getTotalQtyAllocated());
          if (!rli.getTotalQtyDelivered().equals(rli.getQuantity())) {
            mrFullyDelivered = false;
          }
          //Relax shelfLife requirement on OOR orders
          if(BothHelpObjs.isOOR(tli.get("ITEM_TYPE").toString())){
            setRelaxShelfLifeForOOR(header,tli,rli);
          }else{
            tli.put("CAN_RELAX_SHELF_LIFE",new Boolean(false));
            tli.put("RELAX_SHELF_LIFE",new Boolean(new Boolean("y".equalsIgnoreCase(rli.getRelaxShelfLife()))));
          }
          //If current line item is in list of dropship_override line items
          //then set can_relax_shelf_life = false and relax_shelf_life = false
          if (dropShipOverrideLineItems.contains(rli.getLineItem())) {
            tli.put("CAN_RELAX_SHELF_LIFE",new Boolean(false));
            tli.put("RELAX_SHELF_LIFE",new Boolean(false));
            rli.setDropShipOverride(true);
          }

          //******** Delivery Date  check
          if (((String)rli.getRequiredDatetime()) == null){
            tli.put("DEL_DATE",rli.getDefNeedByDate());
          }else {
            tli.put("DEL_DATE",(String)rli.getRequiredDatetime());
          }

          //both charge type has catalog_price; however, if the charge type is display
          //then the price and currency come from current_catalog_price (it is set in GUI)
          tli.put("PO_UNIT_PRICEd",rli.getUnitOfSalePrice());
          tli.put("PO_UNIT_PRICE_CURRENCY_IDd",rli.getCurrencyID());
          tli.put("PO_UNIT_PRICEi",rli.getUnitOfSalePrice());
          tli.put("PO_UNIT_PRICE_CURRENCY_IDi",rli.getCurrencyID());
          tli.put("CONVERSION_FACTOR",HelpObjs.getConversionFactor(db,rli.getCurrencyID(),(String)header.get("CURRENCY_ID"),(String)header.get("SUBMITTED_DATE"), rNum));


          tli.put("PO_UOMd",rli.getUnitOfSale());
          tli.put("PO_QTYd",rli.getPoQty());
          tli.put("PO_UOSQPEd",rli.getUnitOfSaleQuantityPerEach());
          tli.put("PO_UOMi",rli.getUnitOfSale());
          tli.put("PO_QTYi",rli.getPoQty());
          tli.put("PO_UOSQPEi",rli.getUnitOfSaleQuantityPerEach());
          //tli.put("UNIT_OF_SALE_PRICE",rli.getUnitOfSalePrice());
          //tli.put("AUDIT_UNIT_OF_SALE",rli.getAuditUnitOfSale());
          //tli.put("AUDIT_UNIT_OF_SALE_QUANTITY_PER_EACH",rli.getAuditUnitOfSaleQuantityPerEach());
          tli.put("ITEM_DISPLAY_PKG_STYLE",rli.getItemDisplayPkgStyle());


          //charge number
          //order quantity rule
          tli.put("ORDER_QUANTITY_RULE",rli.getOrderQuantityRule());

			 tli.put("LIST_APPROVAL_STATUS",rli.getListApprovalStatus());

			 PRAccount PRA = new PRAccount(db) ;
          PRA.setPRNumber(rNum);
          PRA.setLineItem(tli.get("LINE_NUM").toString());
          Vector prav = PRA.getMultiAcctNumbers();
          Vector cnums = new Vector();
          Vector dCnV = new Vector();
          Vector iCnV = new Vector();
          Vector CnV = new Vector();
          for(int q=0;q<prav.size();q++){
            PRAccount myPra = (PRAccount)prav.elementAt(q);
            Hashtable prh = new Hashtable();
            prh.put("ACCT_NUM_1",myPra.getAccountNumber());
            prh.put("ACCT_NUM_2",myPra.getAccountNumber2());
            prh.put("PERCENTAGE",myPra.getPct());
            prh.put("CHARGE_TYPE",tli.get("CHARGE_TYPE").toString());
            cnums.addElement(prh);
          }
          //show charge number if charge number is in pr_account
          if (cnums.size() > 0 ) {
            Hashtable packH = (Hashtable)header.get("PACK");
            Hashtable accSysTypeH = (Hashtable)packH.get((String)header.get("ACC_SYS_ID")+ct);
            accSysTypeH.put("PR_ACCOUNT_REQUIRED","y");
          }
          if(tli.get("CHARGE_TYPE").toString().equalsIgnoreCase("d")){
            dCnV = cnums;
          }else if(tli.get("CHARGE_TYPE").toString().equalsIgnoreCase("i")){
            iCnV = cnums;
          }

			 if (((Boolean)header.get("CAN_EDIT_MR")).booleanValue()) {
            tli.put("CAT_PART_NO",rli.getFacPartNo());                          //1-09-03 add this so I can lookup for directed charge number by part (cost element)
            getDirectedChargeTypeInfo(tli,header);
            setDirectedChargeType(tli,header,ct);
				if (!allDirected) {                 //only uses directed charge number
              // get pre defined charge numbers
              ChargeNumber cn = new ChargeNumber(db);
              Vector cnV = cn.getChargeNumsForFacNew(header,"Material");
              for(int r=0;r<cnV.size();r++){
                ChargeNumber myCN = (ChargeNumber)cnV.elementAt(r);
                Hashtable cnh = new Hashtable();
                cnh.put("ACCT_NUM_1",myCN.getAccountNumber());
                cnh.put("ACCT_NUM_2",myCN.getAccountNumber2());
                //if one charge number then set percentage -> 100%
                if (cnV.size() == 1) {
                  cnh.put("PERCENTAGE","100");
                } else {
                  cnh.put("PERCENTAGE","");
                }
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

              //this will handle editing of direct/indirect all combination
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
            }
          }else{
            canEditD = false;
            canEditI = false;
          }

          if (directed_d.equalsIgnoreCase("Y")) {
            tli.put("CAN_EDIT_CHARGE_NUM_D",new Boolean(false));
          }else {
            tli.put("CAN_EDIT_CHARGE_NUM_D",new Boolean(canEditD));
          }
          if (directed_i.equalsIgnoreCase("Y")) {
            tli.put("CAN_EDIT_CHARGE_NUM_I",new Boolean(false));
          }else {
            tli.put("CAN_EDIT_CHARGE_NUM_I",new Boolean(canEditI));
          }

          if(BothHelpObjs.isBlankString(ct))ct = "";
          if(ct.equalsIgnoreCase("d")){
            tli.put("CHARGE_NUM_DIRECT",dCnV);
            tli.put("CHARGE_NUM",CnV);
            tli.put("CHARGE_NUM_INDIRECT",iCnV);

          }else if(ct.equalsIgnoreCase("i")){
            tli.put("CHARGE_NUM_INDIRECT",iCnV);
            tli.put("CHARGE_NUM",CnV);
            tli.put("CHARGE_NUM_DIRECT",dCnV);

          }else{
            tli.put("CHARGE_NUM",CnV);
            tli.put("CHARGE_NUM_DIRECT",dCnV);
            tli.put("CHARGE_NUM_INDIRECT",iCnV);

          }

			 //PO
          Vector poV = getPO(rli,facId,header.get("ACC_SYS_ID").toString(),tli);
          String myPo = "";
          if (poV.size() > 0) {
            myPo = poV.elementAt(0).toString();
          }
          tli.put("PO"+ct,myPo);
          tli.put("ALL_POS",poV);
          tli.put("RELEASE_NUM"+ct,rli.getReleaseNumber());

			 if (BothHelpObjs.isBlankString(rli.getExampleItemId())) {
            tli.put("ITEM_NUM",BothHelpObjs.makeBlankFromNull(rli.getItemId().toString()));
          }else {
            tli.put("ITEM_NUM","Various");
          }
          tli.put("DELIVER_TO",rli.getDeliveryPoint()==null?"":rli.getDeliveryPoint());

          //******* Material Description Hash check (can't do anything else here)
          Catalog myCat = new Catalog(db);
          Vector myV = myCat.getPartInfoForItem(rli.getItemId().intValue());
          tli.put("MAT_DESC",myV);
          tli.put("ITEM_DESC",rli.getPartDescription());  //it's really part description

          //4-26-01 checking to see if line item pkg is 'fill up bulk'
          for (int ii = 0; ii < myV.size(); ii++) {
            Hashtable myhh = (Hashtable)myV.elementAt(ii);
            String myTmp = (String)myhh.get("PKG");
            if (myTmp.indexOf("fill up bulk") >= 0) {
              contFillUpBulk = true;
            }
          }

          //********* get All deliver to  check
          getDeliveryPoints(tli,facId,rli,null,viewType,((Boolean)header.get("CAN_EDIT_MR")).booleanValue(),false);

          //get delivery types
          String tmp = rli.getDeliveryType();
          if(BothHelpObjs.isBlankString(tmp)){
            tmp = DeliverySchedule.onOrBeforeString;
          }
          tli.put("DELIVERY_TYPE",tmp);

          //******* if not a scheduled delivery, the
          // delivery schedule will be an empty Vector   check
          if (DeliverySchedule.onOrBeforeString.equalsIgnoreCase(tmp)) {
            tli.put("DELIVERY_SCHEDULE",new Vector(0));
          }else {
            DeliverySchedule ds = new DeliverySchedule(db);
            ds.setPrNum(rNum);
            ds.setLineNum(rli.getLineItem());
            ds.load();
            tli.put("DELIVERY_SCHEDULE",ds.getSchedule());
          }

			 detail.addElement(tli);
        } //end of for each line item

        //it doesn't matter what permission user has, if an MR is cancelled
        //no more changes can be made
        if (mrCancelled) {
          header.put("CAN_EDIT_MR",new Boolean(false));
          header.put("CAN_EDIT_MR_QTY",new Boolean(false));
        }
        //can change MR qty if it is fully delivered
        if (mrFullyDelivered) {
          header.put("CAN_EDIT_MR_QTY",new Boolean(false));
        }
     }//end of else - it is not eval
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    }

    //4-26-01
    header.put("CONTAINS_FILL_UP_BULK",new Boolean(contFillUpBulk));

    header.put("TABLE_STYLE",getTableStyle((String)header.get("STATUS"),rNum));
    header.put("DPAS_FLAG",new Boolean(false));         //11-15-01 for now set to false

    outputVector.addElement(header);
    outputVector.addElement(detail);

    return outputVector;
   } //end of method

   Vector getDropShipOverrideLineItems(String status, String viewType, int rNum) {
     Vector v = new Vector(37);
     //if the MR is in draft status then get all line items that might be dropship_override
     //cpi.drop_ship_override = 'Y' or fla.drop_ship = 'Y'
     if ("entered".equalsIgnoreCase(status) && "REQUEST".equalsIgnoreCase(viewType)) {
       DBResultSet dbrs = null;
       ResultSet rs = null;
       try {
         String query = "select line_item from rli_drop_ship_override_view where pr_number = "+rNum;
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()) {
           v.addElement(rs.getString("line_item"));
         }
       }catch (Exception sql) {
         sql.printStackTrace();
       }finally {
         dbrs.close();
       }
     }
     return v;
   } //end of method

   void setRelaxShelfLifeForOOR(Hashtable header, Hashtable tli, LineItemView rli) {
     //if the MR is in draft status then set the relax shelf life flag according to
     //logic below.  Otherwise, relax shelf is whatever was set in rli
     if ("entered".equalsIgnoreCase((String)header.get("STATUS")) && "REQUEST".equalsIgnoreCase((String)header.get("VIEW_TYPE"))) {
       DBResultSet dbrs = null;
       ResultSet rs = null;
       boolean canRelaxShelfLife = false;
       int remainingShelfLife = 0;
       int minimumShelfLife = 0;
       try {
         String tempQuery = "select available,remaining_shelf_life,relax_shelf_life from available_cat_part_item_view"+
                           " where catalog_id = '"+rli.getCatalogID()+"' and cat_part_no = '"+rli.getFacPartNo()+"' and"+
                           " part_group_no = "+rli.getPartGroupNo()+" and inventory_group = '"+rli.getInventoryGroup()+"'";
         dbrs = db.doQuery(tempQuery);
         rs=dbrs.getResultSet();
         while (rs.next()) {
           remainingShelfLife = rs.getInt("remaining_shelf_life");
           minimumShelfLife = rs.getInt("relax_shelf_life");
           canRelaxShelfLife = true;
           break;            //I only care about the minimum days
         }
       }catch (Exception sql) {
         sql.printStackTrace();
         tli.put("CAN_RELAX_SHELF_LIFE",new Boolean(false));
         tli.put("RELAX_SHELF_LIFE",new Boolean("y".equalsIgnoreCase(rli.getRelaxShelfLife())));
       }finally {
         dbrs.close();
       }

       //check to see if there stock available
       if (canRelaxShelfLife) {
         if (remainingShelfLife == 123456) {                     //indefinite shelf life if stock then deliver without asking
           tli.put("CAN_RELAX_SHELF_LIFE",new Boolean(false));
           tli.put("SHELF_LIFE_CONFIRMED","N");
           tli.put("RELAX_SHELF_LIFE",new Boolean(true));
         }else if (remainingShelfLife == -1) {                   //testing - don't ask and don't deliver
           tli.put("CAN_RELAX_SHELF_LIFE",new Boolean(false));
           tli.put("SHELF_LIFE_CONFIRMED","N");
           tli.put("RELAX_SHELF_LIFE",new Boolean(false));
         }else if (remainingShelfLife > minimumShelfLife) {      //remaining shelf life is greater than minimum shelf life required then ask user
           tli.put("CAN_RELAX_SHELF_LIFE",new Boolean(true));
           tli.put("SHELF_LIFE_CONFIRMED","N");
           tli.put("RELAX_SHELF_LIFE",new Boolean("y".equalsIgnoreCase(rli.getRelaxShelfLife())));
           tli.put("REMAINING_SHELF_LIFE",new Integer(remainingShelfLife));
         }else {                                                 //else don't ask and don't relax shelf life
           tli.put("CAN_RELAX_SHELF_LIFE",new Boolean(false));
           tli.put("RELAX_SHELF_LIFE",new Boolean(false));
         }
       }else {
         tli.put("CAN_RELAX_SHELF_LIFE",new Boolean(false));
         tli.put("RELAX_SHELF_LIFE",new Boolean(false));
       }
     }else {
       tli.put("CAN_RELAX_SHELF_LIFE",new Boolean(false));
       tli.put("RELAX_SHELF_LIFE",new Boolean("y".equalsIgnoreCase(rli.getRelaxShelfLife())));
     }
	} //end of method

   void getDeliveryPoints(Hashtable tli, String facID, LineItemView liv, RequestLineItem rli, String viewType, boolean canEditMR, boolean eval) throws Exception {
     try {
       String application = "";
       String deliveryPoint = "";
       String locationID = "";
       String catalogId = "";
       String facPartNo = "";
       String partGroupNo = "";
       if (eval) {
         application = rli.getApplication();
         deliveryPoint = rli.getDeliveryPoint();
         locationID = rli.getShipToLocation();
         catalogId = rli.getCatalogID();
         facPartNo = rli.getFacPartNo();
         partGroupNo = rli.getPartGoupNo();
       }else {
         application = liv.getApplication();
         deliveryPoint = liv.getDeliveryPoint();
         locationID = liv.getShipToLocationId();
         catalogId = liv.getCatalogID();
         facPartNo = liv.getFacPartNo();
         partGroupNo = liv.getPartGroupNo();
       }

       boolean managedWorkArea = HelpObjs.countQuery(db,"select count(*) from fac_loc_app where managed_use_approval = 'YES' and facility_id = '"+facID+"' and application = '"+application+"'") > 0;
       DeliveryPoint dp = new DeliveryPoint(db);
       dp.setFacilityId(facID);
       dp.setStatus("A");
       Vector delTo = new Vector(0);

       if (managedWorkArea) {
         //check edi_shipto_mapping to see if there default ship to
         //if edi_shipto_mapping returns data then use that
         Hashtable tmpDockNDelPoint = dp.getDockNDelPointFromEdiShipto(facID, application, catalogId, facPartNo, partGroupNo);
         if ("OK".equalsIgnoreCase(tmpDockNDelPoint.get("SUCCESS").toString())) {
           if (!BothHelpObjs.isBlankString(tmpDockNDelPoint.get("LOCATION_ID").toString()) && BothHelpObjs.isBlankString(locationID)) {
             locationID = tmpDockNDelPoint.get("LOCATION_ID").toString();
           }
           if (BothHelpObjs.isBlankString(deliveryPoint)) {
             if (!BothHelpObjs.isBlankString(tmpDockNDelPoint.get("DELIVERY_POINT").toString())) {
               deliveryPoint = tmpDockNDelPoint.get("DELIVERY_POINT").toString();
               tli.put("DELIVER_TO", deliveryPoint);
             }
           }
           delTo = (Vector)tmpDockNDelPoint.get("DOCK_N_DELIVER_TO_INFO");
           if (tmpDockNDelPoint.containsKey("ALL_DOCKS")) {
             tli.put("ALL_DOCKS",(Vector)tmpDockNDelPoint.get("ALL_DOCKS"));
           }
         }
         //end of if managed work area
       }
       //get default dock and delivery point if empty
       if (BothHelpObjs.isBlankString(locationID)) {
         locationID = dp.getDefaultDockForWorkArea(facID, application);
       }
       if (BothHelpObjs.isBlankString(deliveryPoint)) {
         deliveryPoint = dp.getDefaultDeliverToForWorkArea(facID, application);
         tli.put("DELIVER_TO", deliveryPoint);
       }
       //get fixed dock and delivery point for work area
       if (delTo.size() < 1) {
         delTo = dp.getFixedDockNDelPointForWorkArea(facID, application);
       }
       //if not record found, then get delivery points for preferred dock for work area
       if (delTo.size() < 1) {
         delTo = dp.getDelPointsForDock(facID, application);
       }
       //if this still return no record, then get list of docks and delivery points for facility
       if (delTo.size() < 1) {
         Vector allDocks = new Vector();
         tli.put("ALL_DELIVER_TO", dp.getDocksNDeliveryPointsForFacility(facID, allDocks));
         tli.put("ALL_DOCKS", allDocks);
       } else { //Allow requestor to have to option of picking scrap.  Otherwise, put the scrap/obsolete delivery point together with other del. for display
         //all dock
         Vector allDocks = new Vector(5);
         Hashtable temp = (Hashtable) delTo.firstElement();
         allDocks.addElement( (String) temp.get("DOCK"));
         locationID = (String) temp.get("DOCK");

         Hashtable wasteObsoleteDel = dp.getWasteObsoleteDeliveryPoint(facID);
         //if requestor is looking at the request, if before submit he/she can change her mind.
         if (viewType.equalsIgnoreCase("REQUEST") ||
             ( (viewType.equalsIgnoreCase("APPROVE") ||
                viewType.equalsIgnoreCase("RELEASE") ||
                viewType.equalsIgnoreCase("USE_APPROVE")) &&
               canEditMR)) {
           //dock
           tli.put("SCRAP_OBSOLETE_DOCK", (Vector) wasteObsoleteDel.get("DOCK"));
           //delivery point
           tli.put("SCRAP_OBSOLETE_DEL_TO", (Vector) wasteObsoleteDel.get("DELIVERY_POINT"));
         } else { //Otherwise, put everything together and display
           //dock
           Vector tmpWasteDock = (Vector) wasteObsoleteDel.get("DOCK");
           for (int k = 0; k < tmpWasteDock.size(); k++) {
             allDocks.addElement( (String) tmpWasteDock.elementAt(k));
           }
           //delivery to
           Vector tmpWasteObsolete = (Vector) wasteObsoleteDel.get("DELIVERY_POINT");
           for (int jj = 0; jj < tmpWasteObsolete.size(); jj++) {
             delTo.addElement( (Hashtable) tmpWasteObsolete.elementAt(jj));
           }
         }
         tli.put("ALL_DELIVER_TO", delTo);
         if (!tli.containsKey("ALL_DOCKS")) {
           tli.put("ALL_DOCKS", allDocks);
         }
       }

       //if request line item does not contains a ship to location
       //then go get it.
       //otherwise, if ship to location in rli is not in the list of dock
       //then add this ship to location to list
       if(!BothHelpObjs.isBlankString(locationID)){
         String tmpDock = locationID;
         tli.put("DOCK",locationID);
         Vector tmpAllDocks = (Vector) tli.get("ALL_DOCKS");
         //if dock in request line item not dock list
         //then add to list
         if (!tmpAllDocks.contains(tmpDock)) {
           tmpAllDocks.addElement(tmpDock);
           //now add delivery point for that dock
           Vector tmpDelivery = (Vector) tli.get("ALL_DELIVER_TO");
           Hashtable tmpDelH = new Hashtable();
           tmpDelH.put("DOCK", tmpDock);
           if (BothHelpObjs.isBlankString(deliveryPoint)) {
             tmpDelH.put("DEL_POINT", "Select one");
             tmpDelH.put("DEL_POINT_DESC", "Select one");
           } else {
             tmpDelH.put("DEL_POINT", deliveryPoint);
             tmpDelH.put("DEL_POINT_DESC", deliveryPoint);
           }
           tmpDelivery.addElement(tmpDelH);
         }
       }else {
         tli.put("DOCK","");
       }

       //finally,
       Vector tmpAllDocks = (Vector) tli.get("ALL_DOCKS");
       //if there are more then one dock than add Select One
       if (tmpAllDocks.size() > 1) {
         String tmp = "Select One";
         tmpAllDocks.insertElementAt(tmp,0);
         Vector tmpDel = (Vector) tli.get("ALL_DELIVER_TO");
         Hashtable tmpH = new Hashtable();
         tmpH.put("DOCK", tmp);
         tmpH.put("DEL_POINT", tmp);
         tmpH.put("DEL_POINT_DESC", tmp);
         tmpDel.addElement(tmpH);
       }
     }catch (Exception e) {
       throw e;
     }
   } //end of method

   public Hashtable getTableStyle(String stat, int prNumber) {
      Hashtable h = new Hashtable();
      String[] cols;
      int[] colWidths;
      boolean showCustomerRequisition = false;
		boolean showListLimit = false;
		try {
        showCustomerRequisition = HelpObjs.countQuery(db,"select count(*) from purchase_request pr, pr_rules p where pr.pr_number = "+prNumber+
                            " and pr.account_sys_id = p.account_sys_id and pr.facility_id = p.facility_id and p.customer_requisition in ('Required','Display')") > 0;
      }catch (Exception e) {
        e.printStackTrace();
        showCustomerRequisition = false;
      }
		try {
        showListLimit = HelpObjs.countQuery(db,"select count(*) from purchase_request pr, facility f where pr.pr_number = "+prNumber+
                            " and pr.facility_id = f.facility_id and f.list_approval ='Y'") > 0;
      }catch (Exception e) {
        e.printStackTrace();
        showListLimit = false;
      }
		if ("entered".equalsIgnoreCase(stat)) {
        cols = new String[]{"Line","DPAS","Work Area","Part #","Description","Type","Qty","Crit.","Notes","Item #","Canceled","Cancel Requested","Order Quantity Rule","Cust. Req.","Status","List Status"};
        if (showCustomerRequisition) {
          colWidths = new int[] {30,0,130,100,200,55,35,45,35,0,0,0,0,100,0,0}; //for now don't show dpas column
        }else {
          colWidths = new int[] {30,0,180,100,230,65,45,45,35,0,0,0,0,0,0,0}; //for now don't show dpas column
        }
      }else {
        cols = new String[]{"Line","DPAS","Work Area","Part #","Description","Type","Qty","Crit.","Notes","Item #","Canceled","Cancel Requested","Order Quantity Rule","Cust. Req.","Status","List Status"};
        if (showCustomerRequisition && showListLimit) {
          colWidths = new int[]{30,0,110,100,170,55,35,45,35,0,0,0,0,90,60,90};      //for now don't show dpas column
		  }else if (showCustomerRequisition) {
          colWidths = new int[]{30,0,110,100,170,55,35,45,35,0,0,0,0,90,60,0};      //for now don't show dpas column
		  }else if (showListLimit) {
          colWidths = new int[]{30,0,110,100,170,55,35,45,35,0,0,0,0,0,60,90};      //for now don't show dpas column
		  }else {
          colWidths = new int[]{30,0,140,100,230,55,35,45,35,0,0,0,0,0,60,0};      //for now don't show dpas column
        }
      }
      h.put("TABLE_COLUMNS",cols);
      h.put("TABLE_COL_WIDTHS",colWidths);
      h.put("TABLE_COL_TYPES","1111111144111111");
      Hashtable hh = new Hashtable();
      for (int i = 0; i < cols.length; i++) {
        hh.put(cols[i],new Integer(i));
      }
      h.put("KEY_COLUMNS",hh);
      //border color and inset
      h.put("COLOR",new Color(255,255,255));
      h.put("INSET_TOP",new Integer(0));
      h.put("INSET_LEFT",new Integer(1));
      h.put("INSET_BOTTOM",new Integer(1));
      h.put("INSET_RIGHT",new Integer(1));
      h.put("INSET_ALIGN",new Integer(3));
      //font and foreground and background color for columns heading
      h.put("FONT_NAME","Dialog");
      h.put("FONT_STYLE",new Integer(0));
      h.put("FONT_SIZE",new Integer(10));
      h.put("FOREGROUND",new Color(255,255,255));
      h.put("BACKGROUND",new Color(0,0,66));
      return h;
   }

   //trong 1-31-01
   public void setDirectedChargeType(Hashtable tli, Hashtable header,String chargeType) {
    Hashtable h1 = (Hashtable)header.get("ACC_TYPE_H");
    Hashtable innerH = (Hashtable)h1.get((String)header.get("ACC_SYS_ID"));
    Vector ct = (Vector)innerH.get("CHARGE_TYPE");
    directed_d = "";
    directed_i = "";
    for (int i = 0; i < ct.size(); i++) {
      String myType = (String)ct.elementAt(i);
      if (myType.equalsIgnoreCase("d")) {
        directed_d = (String)tli.get("DIRECTED_TYPEd");
      }else {
        directed_i = (String)tli.get("DIRECTED_TYPEi");
      }
    }
	 if(directed_d.equalsIgnoreCase("Y") && directed_i.equalsIgnoreCase("Y")) {
	 	//if both are directed then use chargeType if it is not empty.  If chargeType is empty then
		//default to "d"
		if (!BothHelpObjs.isBlankString(chargeType)) {
			tli.put("CHARGE_TYPE",chargeType);
		}else {
			tli.put("CHARGE_TYPE","d");
		}
	 }else if (directed_d.equalsIgnoreCase("Y")) {
      tli.put("CHARGE_TYPE","d");
    }else if (directed_i.equalsIgnoreCase("Y")) {
      tli.put("CHARGE_TYPE","i");
    }else {
      tli.put("CHARGE_TYPE",chargeType);
    }

    //whether or not getting pre-define charge number
    if ((directed_d.equalsIgnoreCase("Y") && !directed_i.equalsIgnoreCase("N")) || (directed_i.equalsIgnoreCase("Y") && !directed_d.equalsIgnoreCase("N"))) {
      allDirected = true;
    }else {
      allDirected = false;
    }
   }

   //1-09-03 changes
   public void getDirectedChargeTypeInfo(Hashtable tli, Hashtable header) throws Exception {
    String accSysID = (String)header.get("ACC_SYS_ID");
    DirectedCharge dc = new DirectedCharge(db);
    dc.setFacilityId((String)header.get("FACILITY_ID"));
    dc.setAccountSysId(accSysID);
    dc.setApplication((String)tli.get("WORK_AREA_ID"));
	 dc.setCompanyId((String)tli.get("COMPANY_ID"));

	 Hashtable h1 = (Hashtable)header.get("ACC_TYPE_H");
    Hashtable innerH = (Hashtable)h1.get(accSysID);
    Vector ct = (Vector)innerH.get("CHARGE_TYPE");
    for (int i = 0; i < ct.size(); i++) {
      String myT = (String)ct.elementAt(i);
      dc.setChargeType(myT);
      if (dc.isDirected()) {   //there is at least a record in directed charge
        if (dc.isBanned()) {   // but that account sys is ban for this work area
          tli.put("DIRECTED_TYPE"+myT,"B");
        }else {   //else this work area uses directed charge
          dc.setCatPartNo((String)tli.get("CAT_PART_NO"));
          dc.setCatalogID((String)tli.get("CATALOG_ID"));
			 dc.setCatalogCompanyId((String)tli.get("CATALOG_COMPANY_ID"));
			 Vector v = dc.getDirectedChargeNumber();
          if (v.size() < 1) {
            tli.put("DIRECTED_TYPE"+myT,"N");
          }else {
            tli.put("DIRECTED_TYPE"+myT,"Y");
            Vector directedChargeNumberV = new Vector();
            for (int j = 0; j < v.size(); j++) {
              Hashtable chargeH = new Hashtable();
              Hashtable myHash = (Hashtable)v.elementAt(j);
              chargeH.put("ACCT_NUM_1",(String)myHash.get("ACCT_NUM_1"));
              chargeH.put("ACCT_NUM_2",(String)myHash.get("ACCT_NUM_2"));
              chargeH.put("PERCENTAGE",(String)myHash.get("PERCENTAGE"));
              directedChargeNumberV.addElement(chargeH);
            }
            tli.put("DIRECTED_CHARGE_NUMBER"+myT,directedChargeNumberV);
            //if charge number is in directed charge then pr_account is required
            if (directedChargeNumberV.size() > 0) {
              Hashtable packH = (Hashtable)header.get("PACK");
              Hashtable accSysTypeH = (Hashtable)packH.get(accSysID+myT);
              accSysTypeH.put("PR_ACCOUNT_REQUIRED","y");
            }
          } //end of else vector contains charge numbers
        }
      }else {
        tli.put("DIRECTED_TYPE"+myT,"N");
      }
    }
   }

  public void setDirectedChargeTypeEval(Hashtable h, Hashtable header,String chargeType) {
    Hashtable h1 = (Hashtable)header.get("ACC_TYPE_H");
    Hashtable innerH = (Hashtable)h1.get((String)header.get("ACC_SYS_ID"));
    Vector ct = (Vector)innerH.get("CHARGE_TYPE");
    directed_d = "";
    directed_i = "";
    for (int i = 0; i < ct.size(); i++) {
      String myType = (String)ct.elementAt(i);
      if (myType.equalsIgnoreCase("d")) {
        directed_d = (String)h.get("DIRECTED_TYPEd");
      }else {
        directed_i = (String)h.get("DIRECTED_TYPEi");
      }
    }

	 if(directed_d.equalsIgnoreCase("Y") && directed_i.equalsIgnoreCase("Y")) {
	 	//if both are directed then use chargeType if it is not empty.  If chargeType is empty then
		//default to "d"
		if (!BothHelpObjs.isBlankString(chargeType)) {
			h.put("CHARGE_TYPE",chargeType);
		}else {
			h.put("CHARGE_TYPE","d");
		}
	 }else if (directed_d.equalsIgnoreCase("Y")) {
      h.put("CHARGE_TYPE","d");
    }else if (directed_i.equalsIgnoreCase("Y")) {
      h.put("CHARGE_TYPE","i");
    }else {
      h.put("CHARGE_TYPE",chargeType);
    }

    //whether or not getting pre-define charge number
    if ((directed_d.equalsIgnoreCase("Y") && !directed_i.equalsIgnoreCase("N")) || (directed_i.equalsIgnoreCase("Y") && !directed_d.equalsIgnoreCase("N"))) {
      allDirected = true;
    }else {
      allDirected = false;
    }
   }
  public void getDirectedChargeTypeInfoEval(Hashtable h, Hashtable header) throws Exception {
    String accSysID = (String)header.get("ACC_SYS_ID");
    DirectedCharge dc = new DirectedCharge(db);
    dc.setFacilityId((String)header.get("FACILITY_ID"));
    dc.setAccountSysId(accSysID);
    dc.setApplication((String)header.get("APPLICATION"));
	 dc.setCompanyId((String)header.get("COMPANY_ID"));

	 Hashtable h1 = (Hashtable)header.get("ACC_TYPE_H");
    Hashtable innerH = (Hashtable)h1.get(accSysID);
    Vector ct = (Vector)innerH.get("CHARGE_TYPE");
    for (int i = 0; i < ct.size(); i++) {
      String myT = (String)ct.elementAt(i);
      dc.setChargeType(myT);
      if (dc.isDirected()) {   //there is at least a record in directed charge
        if (dc.isBanned()) {   // but that account sys is ban for this work area
          h.put("DIRECTED_TYPE"+myT,"B");
        }else {   //else this work area uses directed charge
          Vector v = dc.retrieveAll();
          if (v.size() > 0) {
            h.put("DIRECTED_TYPE"+myT,"Y");
            Vector directedChargeNumberV = new Vector();
            for (int j = 0; j < v.size(); j++) {
              Hashtable chargeH = new Hashtable();
              DirectedCharge myDC = (DirectedCharge)v.elementAt(j);
              String number_1 = myDC.getChargeNumber1();
              String number_2 = myDC.getChargeNumber2();
              String pct = myDC.getPct();
              chargeH.put("ACCT_NUM_1",number_1);
              chargeH.put("ACCT_NUM_2",number_2);
              chargeH.put("PERCENTAGE",pct);
              directedChargeNumberV.addElement(chargeH);
            }
            h.put("DIRECTED_CHARGE_NUMBER"+myT,directedChargeNumberV);
            //if charge number is in directed charge then pr_account is required
            if (directedChargeNumberV.size() > 0) {
              Hashtable packH = (Hashtable)header.get("PACK");
              Hashtable accSysTypeH = (Hashtable)packH.get(accSysID+myT);
              accSysTypeH.put("PR_ACCOUNT_REQUIRED","y");
            }
          }else {
            h.put("DIRECTED_TYPE"+myT,"N");
          }
        }
      }else {
        h.put("DIRECTED_TYPE"+myT,"N");
      }
    }
   }


   //trong 3/6/00
  public Vector getEvalScreenData(Hashtable header,int rNum) throws Exception{
    Vector detail = new Vector();
    Hashtable h = new Hashtable();
    boolean canEditD = false;
    boolean canEditI = false;
    try {
    PurchaseRequest pr = new PurchaseRequest(db);
    pr.setPRNumber(rNum);
    pr.load();
    String facId = pr.getFacilityId();

    RequestLineItem rli = new RequestLineItem(db);
    rli.setPRNumber(rNum);
    rli.setLineItem("1");
    rli.load();

    GlobalItem gi = new GlobalItem(db);
    gi.setItemId(rli.getItemId().intValue());
    gi.load();

	 header.put("COMPANY_ID",rli.getCompanyId());
	 h.put("LINE_NUM",rli.getLineItem());
    h.put("ITEM_NUM",rli.getItemId());
    h.put("ITEM_TYPE",rli.getRequestedItemType());
    h.put("PART_NUM", rli.getFacPartNo());
    h.put("CANCEL_STATUS",rli.getCancelStatus());
    h.put("VIEW_TYPE",header.get("VIEW_TYPE").toString());

	 ApplicationView app = new ApplicationView(db);
    app.setApplication(rli.getApplication());
    app.setFacilityId(facId);
    app.load();
    String ad = app.getApplicationDesc();
    h.put("WORK_AREA",rli.getApplication()+((ad!=null&&ad.length()>0)?("-"+ad):""));
    h.put("WORK_AREA_ID",rli.getApplication());
    h.put("WORK_AREA_DESC",new String(""));
    h.put("CRIT_FLAG",new Boolean(rli.getCritical() != null &&rli.getCritical().equalsIgnoreCase("y")));
    h.put("SCRAP_FLAG",new Boolean(rli.getScrap() != null &&rli.getScrap().equalsIgnoreCase("y")));
    h.put("QTY",rli.getQuantity().toString());
    h.put("UNIT_PRICE","0");
    h.put("DOC_NUM",rli.getDocNum());
    h.put("DOC_STATE",rli.getDocState());
    h.put("ORDER_QUANTITY_RULE",rli.getOrderQuantityRule());
    h.put("CUSTOMER_REQUISITION_NUMBER",rli.getCustomerRequisitionNumber());

    //DPAS
    h.put("DPAS",rli.getDPAS());

    if (BothHelpObjs.isBlankString(rli.getRequiredDatetime())) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DATE,21);
      String bdate = new String((calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.YEAR));
      //h.put("DEL_DATE",BothHelpObjs.formatDate("toCharfromNum",bdate));
      h.put("DEL_DATE",bdate);
    }else {
      //h.put("DEL_DATE",BothHelpObjs.formatDate("toCharfromDB",rli.getRequiredDatetime()));
      h.put("DEL_DATE",rli.getRequiredDatetime());
    }

	 String ct = BothHelpObjs.makeBlankFromNull(rli.getChargeType());
	 // Notes
    h.put("NOTES",rli.getNotes()==null?"":rli.getNotes());
    h.put("CURRENCY_ID",rli.getCurrencyID());
    h.put("CONVERSION_FACTOR",HelpObjs.getConversionFactor(db,rli.getCurrencyID(),(String)header.get("CURRENCY_ID"),(String)header.get("SUBMITTED_DATE"), rNum));

    //then the price and currency come from current_catalog_price (it is set in GUI)
    h.put("PO_UNIT_PRICEd","");
    h.put("PO_UNIT_PRICE_CURRENCY_IDd",rli.getCurrencyID());
    h.put("PO_UNIT_PRICEi","");
    h.put("PO_UNIT_PRICE_CURRENCY_IDi",rli.getCurrencyID());

    h.put("PO_UOMd","");
    h.put("PO_QTYd","");
    h.put("PO_UOSQPEd","");
    h.put("PO_UOMi","");
    h.put("PO_QTYi","");
    h.put("PO_UOSQPEi","");
    h.put("ITEM_DISPLAY_PKG_STYLE","");

    //charge number
    if(BothHelpObjs.isBlankString(ct)){
      h.put("CHARGE_TYPE","d");
    }else {
      h.put("CHARGE_TYPE",ct);
    }

    PRAccount PRA = new PRAccount(db) ;
    PRA.setPRNumber(rNum);
    PRA.setLineItem(h.get("LINE_NUM").toString());
    Vector prav = PRA.getMultiAcctNumbers();
    Vector cnums = new Vector();
    Vector dCnV = new Vector();
    Vector iCnV = new Vector();
    Vector CnV = new Vector();
    for(int q=0;q<prav.size();q++){
      PRAccount myPra = (PRAccount)prav.elementAt(q);
      Hashtable prh = new Hashtable();
      prh.put("ACCT_NUM_1",myPra.getAccountNumber());
      prh.put("ACCT_NUM_2",myPra.getAccountNumber2());
      prh.put("PERCENTAGE",myPra.getPct());
      prh.put("CHARGE_TYPE",h.get("CHARGE_TYPE").toString());
      cnums.addElement(prh);
    }
    //show charge number if charge number is in pr_account
    if (cnums.size() > 0 ) {
      Hashtable packH = (Hashtable)header.get("PACK");
      Hashtable accSysTypeH = (Hashtable)packH.get((String)header.get("ACC_SYS_ID")+ct);
      accSysTypeH.put("PR_ACCOUNT_REQUIRED","y");
    }
    if(h.get("CHARGE_TYPE").toString().equalsIgnoreCase("d")){
      dCnV = cnums;
    }else if(h.get("CHARGE_TYPE").toString().equalsIgnoreCase("i")){
      iCnV = cnums;
    }else{
      CnV = cnums;
    }

   if(header.get("VIEW_TYPE").toString().equalsIgnoreCase("REQUEST")){
    //trong 1-31-01
    getDirectedChargeTypeInfoEval(h,header);
    setDirectedChargeTypeEval(h,header,ct);

    if (!allDirected) {                 //only uses directed charge number
      // get pre defined charge numbers
      ChargeNumber cn = new ChargeNumber(db);
      Vector cnV = cn.getChargeNumsForFacNew(header,"Material");
      for(int r=0;r<cnV.size();r++){
        ChargeNumber myCN = (ChargeNumber)cnV.elementAt(r);
        Hashtable cnh = new Hashtable();
        cnh.put("ACCT_NUM_1",myCN.getAccountNumber());
        cnh.put("ACCT_NUM_2",myCN.getAccountNumber2());
        //if there is only one charge number then set percentage to 100 trong 7-19
        if (cnV.size() == 1) {
          cnh.put("PERCENTAGE","100");
        }else {
          cnh.put("PERCENTAGE","");
        }
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
    }
   }else{
    //tli.put("CAN_EDIT_CHARGE_NUM",new Boolean(false));
    //canEdit = false;
    canEditD = false;
    canEditI = false;
   }

    if (directed_d.equalsIgnoreCase("Y")) {
      h.put("CAN_EDIT_CHARGE_NUM_D",new Boolean(false));
    }else {
      h.put("CAN_EDIT_CHARGE_NUM_D",new Boolean(canEditD));
    }
    if (directed_i.equalsIgnoreCase("Y")) {
      h.put("CAN_EDIT_CHARGE_NUM_I",new Boolean(false));
    }else {
      h.put("CAN_EDIT_CHARGE_NUM_I",new Boolean(canEditI));
    }

    if(BothHelpObjs.isBlankString(ct))ct = "";
    if(ct.equalsIgnoreCase("d")){
      h.put("CHARGE_NUM_DIRECT",dCnV);
      h.put("CHARGE_NUM",CnV);
      h.put("CHARGE_NUM_INDIRECT",iCnV);
    }else if(ct.equalsIgnoreCase("i")){
      h.put("CHARGE_NUM_INDIRECT",iCnV);
      h.put("CHARGE_NUM",CnV);
      h.put("CHARGE_NUM_DIRECT",dCnV);
    }else{
      h.put("CHARGE_NUM",CnV);
      h.put("CHARGE_NUM_DIRECT",dCnV);
      h.put("CHARGE_NUM_INDIRECT",iCnV);
    }

	     //get PO
    LineItemView liv = new LineItemView(db);
	 liv.setCompanyId(rli.getCompanyId());
	 liv.setApplication(rli.getApplication());
    liv.setPO(rli.getPONumber());
    liv.setPRNumber(rNum);
    liv.setLineItem((String)h.get("LINE_NUM"));
	 h.put("CHARGE_TYPE",ct);
	 Vector poV = getPO(liv,facId,header.get("ACC_SYS_ID").toString(),h);    //trong 7-19
    String myPo = "";
    if (poV.size() > 0) {
      myPo = poV.elementAt(0).toString();
    }
    h.put("PO"+ct,myPo);
    h.put("ALL_POS",poV);
	 // Release Number
    h.put("RELEASE_NUM"+ct,rli.getReleaseNumber());

    h.put("CAN_RELAX_SHELF_LIFE",new Boolean(false));
    h.put("RELAX_SHELF_LIFE",new Boolean(false));

    //Material Description Hash
    CatalogAddItemNew cain = new CatalogAddItemNew(db);
    Vector myV = cain.getPartInfoForEvalRequest(pr.getRequestId().intValue());
    h.put("MAT_DESC",myV);
    if(myV.size() > 1){
      h.put("ITEM_DESC","Kit");
    }else if(myV.size() == 1){
      h.put("ITEM_DESC",((Hashtable)myV.elementAt(0)).get("DESC").toString());
    }else{
      h.put("ITEM_DESC","");
    }

    //get all deliver to
    //user are not allow to place eval order for scrap delivery point
    getDeliveryPoints(h,facId,null,rli,"View",false,true);

    //get delivery types
    String tmp = rli.getDeliveryType();
    if(BothHelpObjs.isBlankString(tmp)){
      tmp = DeliverySchedule.onOrBeforeString;
    }
    h.put("DELIVERY_TYPE",tmp);

    // if not a scheduled delivery, the
    // delivery schedule will be an empty Vector
    DeliverySchedule ds = new DeliverySchedule(db);
    ds.setPrNum(rNum);
    ds.setLineNum(rli.getLineItem());
    ds.load();
    h.put("DELIVERY_SCHEDULE",ds.getSchedule());
    h.put("DELIVER_TO",BothHelpObjs.makeBlankFromNull(rli.getDeliveryPoint()));

    detail.addElement(h);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    }
    return (detail);
   }

   public Hashtable deleteLineItem(int rNum,String val){
      Boolean go = new Boolean(true);
      Hashtable result = new Hashtable();
      try {
         PRAccount PRA = new PRAccount(db);
         PRA.setPRNumber(rNum);
         if (PRA.isSingle()){
           result.put("MSG","There is only one item on this request. Cancel the request instead.");
           go = new Boolean(false);
           result.put("GO",go);
           return result;
         }
         RequestLineItem RLI = new RequestLineItem(db);
         RLI.setPRNumber(rNum);
         RLI.deleteLineItem(val);

         Hashtable cNum;
         Hashtable pNum;
      } catch (Exception e){
         result.put("MSG", "Error on deleting item.");
         go = new Boolean(false);
         result.put("GO",go);
         return result;
      }
      result.put("MSG", "");
      go = new Boolean(true);
      result.put("GO",go);
      return result;
   }

  float getNBOPercentageDenominator(Vector cv) {
    float perDen = 0;
    for(int j=0;j<cv.size();j++){
      Hashtable ch = (Hashtable)cv.elementAt(j);
      if(ch.get("ACCT_NUM_1") == null ||
        ch.get("PERCENTAGE") == null ){
        continue;
      }
      try {
        perDen += Float.parseFloat(ch.get("PERCENTAGE").toString());
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
    return perDen;
  } //end of method

  public Hashtable submitNBORequest(String action,Hashtable prInfo, Vector lineItems,String userId,String dbClient)throws Exception{
     Hashtable outH = new Hashtable();
     Boolean returnCode = new Boolean(true);
     String msg = "";
     PurchaseRequest PR = new PurchaseRequest(db);
     int rNum = Integer.parseInt(prInfo.get("REQ_NUM").toString());
     PR.setPRNumber(rNum);
     PR.load();

     String nextStatus = "POSUBMIT";

     Vector vvv = getPrRules(prInfo,lineItems);
     Boolean app = (Boolean)vvv.elementAt(0);
     Boolean rel = (Boolean)vvv.elementAt(1);
     Boolean po = (Boolean)vvv.elementAt(2);

     //if first time then insert credit card into purchase_request
     boolean creditCardInserted = false;
     if(action.equalsIgnoreCase("SAVE")){
       //just save the info, no audits
       String tmp = prInfo.get("END_USER").toString();
       if (!BothHelpObjs.isBlankString(tmp)) {
        PR.insert("end_user",tmp,PurchaseRequest.STRING);
       }
       tmp = prInfo.get("DEPARTMENT").toString();
       if (!BothHelpObjs.isBlankString(tmp)) {
        PR.insert("department",tmp,PurchaseRequest.STRING);
       }
       tmp = prInfo.get("CONTACT_INFO").toString();
       if (!BothHelpObjs.isBlankString(tmp)) {
        PR.insert("contact_info",tmp,PurchaseRequest.STRING);
       }

		 /*
		 if(prInfo.get("APPROVER") != null){
         PR.insert("requested_finance_approver",prInfo.get("APPROVER").toString(),PurchaseRequest.STRING);
       }
		 */
		 //stamp -1 as financial approver
		 PR.insert("requested_finance_approver","-1",PurchaseRequest.STRING);

		 //first removing all pr_account for the given request
       PRAccount pra = new PRAccount(db);
       pra.setPRNumber(rNum);
       pra.delete();
       for(int i=0;i<lineItems.size();i++){
         Hashtable h = (Hashtable)lineItems.elementAt(i);
         RequestLineItem RLI = new RequestLineItem(db);
         RLI.setPRNumber(rNum);
         RLI.setLineItem(h.get("LINE_NUM").toString());
         RLI.load();
         RLI.updateOne("quantity",h.get("QTY").toString(),RequestLineItem.BIGDECIMAL);
         RLI.updateOne("critical",((Boolean)h.get("CRIT_FLAG")).booleanValue()?"y":"n",RequestLineItem.STRING);
         RLI.updateOne("notes",BothHelpObjs.changeSingleQuotetoTwoSingleQuote(h.get("NOTES").toString()==null?"":h.get("NOTES").toString()),RequestLineItem.STRING);
         RLI.updateOne("scrap",((Boolean)h.get("SCRAP_FLAG")).booleanValue()?"y":"n",RequestLineItem.STRING);
         //updating pay load ID if the request came from Ariba
         if ("Seagate".equalsIgnoreCase(db.getClient())) {
          Boolean aribaUser = (Boolean)prInfo.get("ARIBA_LOGON");
          if (aribaUser.booleanValue()) {
            String payLoadID = (String)prInfo.get("PAY_LOAD_ID");
            //payLoadID = "0";    //2-06-02 TEST NEED TO REMOVE
            if (!"0".equalsIgnoreCase(payLoadID) && !BothHelpObjs.isBlankString(payLoadID)) {
              RLI.updateOne("payload_id",payLoadID,RequestLineItem.STRING);
            }else {
              radian.web.emailHelpObj.sendtrongemail("Error Pay Load ID","Seagate user creating request without pay load ID for pr_number: "+rNum);
              outH.put("RETURN_CODE",new Boolean(false));
              outH.put("MSG","Critical error.\nPlease exit tcmIS and punchout again from Ariba.");
              return outH;
            }
          }
         }
         //DPAS
         String tmpDpas = (String)h.get("DPAS");
         if (!"None".equalsIgnoreCase(tmpDpas)) {
          RLI.updateOne("dpas_code",tmpDpas,RequestLineItem.STRING);
         }

         String cType = (String)h.get("CHARGE_TYPE");
         String accSysId = (String)prInfo.get("ACC_SYS_ID");
         String key1 = accSysId + cType;
         Hashtable packH = (Hashtable)prInfo.get("PACK");
         Hashtable accSysTypeH = (Hashtable)packH.get(key1);
         String npo = (String)accSysTypeH.get("PO_REQUIRED");
         String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
         if(cType == null)cType = "";
         if(npo.equalsIgnoreCase("p") || npo.equalsIgnoreCase("b")){
           RLI.updateOne("po_number",h.get("PO"+cType).toString()==null?"":h.get("PO"+cType).toString(),RequestLineItem.STRING);
           RLI.updateOne("release_number",h.get("RELEASE_NUM"+cType).toString()==null?"":h.get("RELEASE_NUM"+cType).toString(),RequestLineItem.STRING);
         }else if ("c".equalsIgnoreCase(npo)) {     //10-03-01 inserting credit card info
          if (!creditCardInserted) {             //should only insert credit card one time
            Hashtable ccH = (Hashtable)prInfo.get("CREDIT_CARD_INFO");
            String ccTmp = (String)ccH.get("CREDIT_CARD_TYPE");
            if (!BothHelpObjs.isBlankString(ccTmp)) {
              PR.insert("credit_card_type",ccTmp,PurchaseRequest.STRING);
            }
            ccTmp = (String)ccH.get("CREDIT_CARD_NUMBER");
            if (!BothHelpObjs.isBlankString(ccTmp)) {
              PR.insert("credit_card_number",ccTmp,PurchaseRequest.INT);
            }
            ccTmp = (String)ccH.get("CREDIT_CARD_EXP_DATE");
            if (!BothHelpObjs.isBlankString(ccTmp)) {
              PR.insert("credit_card_expiration_date",ccTmp,PurchaseRequest.DATE);
            }
            ccTmp = (String)ccH.get("CREDIT_CARD_NAME");
            if (!BothHelpObjs.isBlankString(ccTmp)) {
              PR.insert("credit_card_name",ccTmp,PurchaseRequest.STRING);
            }
            creditCardInserted = true;
          }
         }
         if ("y".equalsIgnoreCase(prRequired)) {     //10-30-01 uses charge number
            Vector cv = new Vector();
            if(cType.equalsIgnoreCase("i")){
              cv = (Vector)h.get("CHARGE_NUM_INDIRECT");
            }else if(cType.equalsIgnoreCase("d")){
              cv = (Vector)h.get("CHARGE_NUM_DIRECT");
            }
            //send myself an email if mr does not have any charge number and one is required
            if (cv.size() < 1) {
              HelpObjs.sendMail(db,"No charge Numer","pr number: "+rNum,86030,false);
            }
            //get percentage denominator
            float perDen = getNBOPercentageDenominator(cv);
            for(int j=0;j<cv.size();j++){
              Hashtable ch = (Hashtable)cv.elementAt(j);
              if(ch.get("ACCT_NUM_1") == null ||
                ch.get("PERCENTAGE") == null ){
                continue;
              }
              String pc = ch.get("PERCENTAGE").toString();
              String a1 = ch.get("ACCT_NUM_1").toString();

              if(!BothHelpObjs.isBlankString(pc) &&
                 !BothHelpObjs.isBlankString(a1)){
                pra.setLineItem(h.get("LINE_NUM").toString());
                ChargeNumber cn = new ChargeNumber(db);
                cn.setFacilityId(prInfo.get("FACILITY_ID").toString());
                cn.setChargeType(h.get("CHARGE_TYPE").toString());
                cn.setAccountNumber(a1);
                if(!(ch.get("ACCT_NUM_2") == null)) {
                  String a2 = ch.get("ACCT_NUM_2").toString();
                  if(!BothHelpObjs.isBlankString(a2)){
                    cn.setAccountNumber2(a2);
                    pra.setAccountNumber2(a2);
                  }
                }
                //calculate percentage
                Float tmpPer = new Float(pc);
                Float percent = new Float((tmpPer.floatValue()*100)/perDen);
                //create pr_account record
                pra.setAccountNumber(a1);
                pra.setPct(percent.toString());
                pra.setChargeType(cType);    //10-03-02
                pra.insert();
                pra.commit();
              }
            }  //end of inner for each charge number
         } //end of else if uses charge number

         // relax 80%shelf life
         Boolean rsl = (Boolean)h.get("RELAX_SHELF_LIFE");
         RLI.updateOne("relax_shelf_life",rsl.booleanValue()?"y":"n",RequestLineItem.STRING);

         String dType = h.get("DELIVERY_TYPE").toString();
         if(dType.equalsIgnoreCase("hold")){
           RLI.updateOne("delivery_type","hold",RequestLineItem.STRING);
           RLI.updateOne("required_datetime","",RequestLineItem.STRING); //trong 3/9/00 change required_date_time -> required_datetime
           RLI.updateOne("delivery_point","",RequestLineItem.STRING);
           RLI.updateOne("ship_to_location_id","",RequestLineItem.STRING);
         }else{
           RLI.updateOne("ship_to_location_id",h.get("DOCK").toString(),RequestLineItem.STRING);
           //6-07-01 change require_datetime if it is < sysdate when releaser release it.
           if (isReleaseApprover) {
            String myDelD = h.get("DEL_DATE").toString();
            //Date d = new Date();
            Calendar cal = Calendar.getInstance();
            String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
            if (Date.parse(cdate) > Date.parse(myDelD)) {
              LineItemView liv = new LineItemView(db);
              liv.setFacilityID(PR.getFacilityId());
              liv.setPRNumber(PR.getPRNumber().intValue());
              liv.setCatalogID(RLI.getCatalogID());
				  liv.setCatalogCompanyId(RLI.getCatalogCompanyId());
				  liv.setItemType(RLI.getItemType());
              liv.setFacPartNo(RLI.getFacPartNo());
              liv.setPartGroupNo(RLI.getPartGoupNo());
              liv.setInventoryGroup(RLI.getInventoryGroup());
              String newNeedDate = liv.getDefNeedByDate();
              RLI.updateOne("required_datetime",newNeedDate,RequestLineItem.DATE);
              String esub = "Material request: "+RLI.getPRNumber().toString()+" was approved/released";
              String emsg = "Your material request "+RLI.getPRNumber().toString()+" was approved/released\n";
              emsg += "after your specified need date.  tcmIS updated your need date to "+newNeedDate+".\n";
              emsg += "If you don't need this material please cancel it in tcmIS.";
              HelpObjs.sendMail(db,esub,emsg,Integer.parseInt(PR.getRequestor().toString()),false);
            }else {
              RLI.updateOne("required_datetime",h.get("DEL_DATE").toString(),RequestLineItem.DATE);
            }
           }else {
            RLI.updateOne("required_datetime",h.get("DEL_DATE").toString(),RequestLineItem.DATE);
           }
           RLI.updateOne("delivery_point",h.get("DELIVER_TO").toString(),RequestLineItem.STRING);
           RLI.updateOne("delivery_type",h.get("DELIVERY_TYPE").toString(),RequestLineItem.STRING);
           // Delivery Schedule
           DeliverySchedule ds = new DeliverySchedule(db);
           ds.setPrNum(rNum);
           ds.setLineNum(RLI.getLineItem());
           ds.saveSchedule((Vector)h.get("DELIVERY_SCHEDULE"),PR.getRequestor().intValue(),dbClient);
         }
         RLI.updateOne("charge_type",h.get("CHARGE_TYPE").toString(),RequestLineItem.STRING);
       }  //end of for each line item
       //trong 3/29 set it to zero as default
       outH.put("ERROR_LINE",new Integer(0));
       outH.put("ERROR_ROW",new Integer(0));
       outH.put("ERROR_COL",new Integer(0));

       outH.put("RETURN_CODE",returnCode);
       outH.put("MSG",msg);
       outH.put("NEXT_STATUS",PR.getPRStatus());
       return outH;
     }else if(action.equalsIgnoreCase("SUBMIT")){
        boolean useApproverNeeded = false;
        ChargeNumber cn = new ChargeNumber(db);
        for (int i = 0;i<lineItems.size();i++){
         Hashtable h = (Hashtable)lineItems.elementAt(i);
         // charge Number Lookup
         Vector cv = new Vector();
         String cType = h.get("CHARGE_TYPE").toString();

         //10-03-01 this is where I need to audit credit card info
         String accSysId = (String)prInfo.get("ACC_SYS_ID");
         String key1 = accSysId + cType;
         Hashtable packH = (Hashtable)prInfo.get("PACK");
         Hashtable accSysTypeH = (Hashtable)packH.get(key1);
         String npo = (String)accSysTypeH.get("PO_REQUIRED");
         if ("c".equalsIgnoreCase(npo)) {
            Hashtable ccH = (Hashtable)prInfo.get("CREDIT_CARD_INFO");
            if (BothHelpObjs.isBlankString((String)ccH.get("CREDIT_CARD_TYPE")) || BothHelpObjs.isBlankString((String)ccH.get("CREDIT_CARD_NUMBER")) ||
                BothHelpObjs.isBlankString((String)ccH.get("CREDIT_CARD_EXP_DATE")) || BothHelpObjs.isBlankString((String)ccH.get("CREDIT_CARD_NAME"))) {
              msg =  "Please enter credit card information for this purchase request.";
              outH.put("ERROR_LINE",new Integer(i+1));
              outH.put("ERROR_ROW",new Integer(0));
              outH.put("ERROR_COL",new Integer(0));
              outH.put("RETURN_CODE",new Boolean(false));
              outH.put("MSG",msg);
              return outH;
            }
         }


         if(cType.equalsIgnoreCase("i")){
          cv = (Vector)h.get("CHARGE_NUM_INDIRECT");
         }else if(cType.equalsIgnoreCase("d")){
          cv = (Vector)h.get("CHARGE_NUM_DIRECT");
         }

         Vector chargeV = new Vector();
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
          Vector vv = myCN.checkChargeNumberNew(myCN,prInfo);
          Boolean val1 = (Boolean)vv.elementAt(0);
          Boolean val2 = (Boolean)vv.elementAt(1);
          if (!val1.booleanValue()){
            msg =  "Charge number is invalid.";
            outH.put("ERROR_LINE",new Integer(i+1));
            outH.put("ERROR_ROW",new Integer(j));
            outH.put("ERROR_COL",new Integer(0));
            outH.put("RETURN_CODE",new Boolean(false));
            outH.put("MSG",msg);
            return outH;
          }
          if (!val2.booleanValue()){
            msg =  "Charge number is invalid.";
            outH.put("ERROR_LINE",new Integer(i+1));
            outH.put("ERROR_ROW",new Integer(j));
            outH.put("ERROR_COL",new Integer(1));
            outH.put("RETURN_CODE",new Boolean(false));
            outH.put("MSG",msg);
            return outH;
          }
         }

         //audit use approval here
         //if (needUseApproval(PR.getRequestor().toString(),PR.getFacilityId(),PR.getPRNumber(),i,h.get("QTY").toString())) {   //pr_number,line_item,quantity
          //useApproverNeeded = true;
         //}
			//don't need to check since this is nobuyorders
			db.doUpdate("update request_line_item set use_approval_status = 'approved',use_approver = "+PR.getRequestor().toString()+",use_approval_date = sysdate"+
				         ",list_approval_status = 'Approved',list_approval_date = sysdate where pr_number = "+PR.getPRNumber()+" and line_item = '"+(i+1)+"'");
		 } //end of for each line item
       FinanceApproverList fal = new FinanceApproverList(db);
       Integer approver = fal.getApprover(PR.getFacilityId(),getExtendedPrice(lineItems),PR.getRequestor());
       if(approver.intValue() > 0){
         prInfo.put("APPROVER",approver.toString());
       }
       //2-06-02
       //submitRequest("SAVE",prInfo,lineItems,userId,dbClient);
       Hashtable hhh = submitNBORequest("SAVE",prInfo,lineItems,userId,dbClient);
       Boolean b = (Boolean)hhh.get("RETURN_CODE");
       if (!b.booleanValue()) {
          outH.put("RETURN_CODE",new Boolean(false));
          outH.put("MSG","Critical error.\nPlease exit tcmIS and punchout again from Ariba.");
          return outH;
       }

       if (useApproverNeeded) {
        if(nextStatus.equalsIgnoreCase("APPROVE")){
          sendMsgToApprovers(Integer.parseInt(prInfo.get("REQ_NUM").toString()));
          PR.insert("pr_status","compchk",PurchaseRequest.STRING);
          updateRequestLineStatus(rNum,0,"Pending Finance Approval","Open");
        }else {
          PR.insert("pr_status","compchk2",PurchaseRequest.STRING);
          updateRequestLineStatus(rNum,0,"Pending Use Approval","Open");
        }
        sendMsgToUseApprovers(Integer.parseInt(prInfo.get("REQ_NUM").toString()));
        nextStatus = "USE_APPROVE";
       }else {
        if(nextStatus.equalsIgnoreCase("APPROVE")){
          sendMsgToApprovers(Integer.parseInt(prInfo.get("REQ_NUM").toString()));
          PR.insert("pr_status","compchk",PurchaseRequest.STRING);
          updateRequestLineStatus(rNum,0,"Pending Finance Approval","Open");
        }else if(nextStatus.equalsIgnoreCase("RELEASE")){
          sendMsgToReleasers(Integer.parseInt(prInfo.get("REQ_NUM").toString()),PR.getRequestor().toString());
          PR.insert("pr_status","approved",PurchaseRequest.STRING);
          updateRequestLineStatus(rNum,0,"Pending Release","Open");
        }else if(nextStatus.equalsIgnoreCase("POSUBMIT")){
          //11-14-01
          if ("Seagate".equalsIgnoreCase(db.getClient())) {
				 Boolean aribaUser = (Boolean)prInfo.get("ARIBA_LOGON");
          	 if (aribaUser.booleanValue()) {
					//if client is Seagate and using production as their cost center then update the pr_status to submitted
					//if ("Production".equalsIgnoreCase(prInfo.get("ACC_SYS_ID").toString())) {
					//1-15-02 if client is Seagate and using Production as their cost center and the part type is not 'SF' (Service Fee) then
					//update pr_status to submitted.

					//2-05-02
					boolean noBuyOrder = HelpObjs.countQuery(db,"select count(*) from request_line_item where item_type in ('BG','SF','TC') and pr_number = "+rNum) > 0;
					if (noBuyOrder) {
					  //first insert into issue
					  String val = "";
					  try {
						 //next
						 PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
						 updateRequestLineStatus(rNum,0,"In Progress","Open");
						 Vector bv = new Vector(2);
						 Integer noBuyOrderR = new Integer(rNum);
						 bv.addElement(noBuyOrderR.toString());
						 bv.addElement("error_val");
						 val = db.doInvoiceProcedure("p_issue_no_buy_order_po_mr",bv);            //bad method name due to my false
						 if (!"OK".equalsIgnoreCase(val)) {
							PR.insert("pr_status","entered",PurchaseRequest.STRING);
							radian.web.emailHelpObj.sendtrongemail("p_issue_no_buy_order_po_mr error (pr_number: "+rNum+")",val);
							outH.put("RETURN_CODE",new Boolean(false));
							outH.put("MSG","Critical error.\nAn email has been sent to system admin.\nIf you don't hear anything within two hours.\nPlease contact your tcmIS CSR.");
							return outH;
						 }
					  }catch (Exception ee3) {
						 ee3.printStackTrace();
						 PR.insert("pr_status","entered",PurchaseRequest.STRING);
						 radian.web.emailHelpObj.sendtrongemail("p_issue_no_buy_order_po_mr error (pr_number: "+rNum+")",val);
						 outH.put("RETURN_CODE",new Boolean(false));
						 outH.put("MSG","Critical error.\nAn email has been sent to system admin.\nIf you don't hear anything within two hours.\nPlease contact your tcmIS CSR.");
						 return outH;
					  }
					}else if ("Production".equalsIgnoreCase(prInfo.get("ACC_SYS_ID").toString()) ||
								 "Production - ERS".equalsIgnoreCase(prInfo.get("ACC_SYS_ID").toString())) {
					  try {
						 updateRequestLineStatus(rNum,0,"In Progress","Open");
						 db.doUpdate("update request_line_item set release_date = sysdate where pr_number = "+rNum);
						 PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
						 String[] args = new String[1];
						 Integer currentRNum = new Integer(rNum);
						 args[0] = currentRNum.toString();
						 db.doProcedure("p_mr_allocate",args);
					  }catch(Exception dbe) {
						 radian.web.emailHelpObj.sendtrongemail("p_mr_allocate error (pr_number: "+rNum+")","Error occured while trying to call procedure");
					  }
					}else {   //otherwise pending approval
					  PR.insert("pr_status","compchk",PurchaseRequest.STRING);
					  updateRequestLineStatus(rNum,0,"Pending Finance Approval","Open");
					}
				}else {
					PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
            	updateRequestLineStatus(rNum,0,"In Progress","Open");
				}
			 }else {   //other clients
            PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
            updateRequestLineStatus(rNum,0,"In Progress","Open");
          }
        }
       }

       PR.insert("submitted_date","nowDate",PurchaseRequest.DATE);
       outH.put("NEXT_STATUS",nextStatus);
       outH.put("RETURN_CODE",new Boolean(true));
     }else if(action.equalsIgnoreCase("APPROVE")){
       PR.insert("requested_finance_approver",userId,PurchaseRequest.STRING);
       //if the status of purchase request haven't change then continue, otherwise
       if (!nextStatus.equalsIgnoreCase("rejected")) {
        //check to see if this request still need use approval
        if (RequestLineItem.needUseApproval(db,Integer.parseInt(prInfo.get("REQ_NUM").toString()))) {
          PR.insert("pr_status","compchk2",PurchaseRequest.STRING);
          updateRequestLineStatus(rNum,0,"Pending Use Approval","Open");
        }else {
          if(rel.booleanValue()){
            sendMsgToReleasers(Integer.parseInt(prInfo.get("REQ_NUM").toString()),PR.getRequestor().toString());
            PR.insert("pr_status","approved",PurchaseRequest.STRING);
            updateRequestLineStatus(rNum,0,"Pending Release","Open");
          }else{
            // don't mark pr_status as "posubmit" until we get a doc number
            // on the rli in the salesOrder object
            //6-07-01
            isReleaseApprover = true;
            submitRequest("SAVE",prInfo,lineItems,userId,dbClient);
            //trong 4/10
            PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
            updateRequestLineStatus(rNum,0,"In Progress","Open");
          }
        }
       }
       //3-05-02 I DON'T think I need this -> PR.commit();
       //3-05-02 I DON'T think I need this -> PR.load();
       outH.put("RETURN_CODE",new Boolean(true));
       outH.put("NEXT_STATUS",nextStatus);
     }else if(action.equalsIgnoreCase("RELEASE")){
       if(!hasNeededReleaseNum(prInfo,lineItems)) {
         msg =  "Please enter PO and reference numbers.";
         outH.put("RETURN_CODE",new Boolean(false));
         outH.put("MSG",msg);
         //trong 3/29 set it to zero as default
         outH.put("ERROR_LINE",new Integer(0));
         outH.put("ERROR_ROW",new Integer(0));
         outH.put("ERROR_COL",new Integer(0));
         return outH;
       }
       //6-07-01
       isReleaseApprover = true;

       submitRequest("SAVE",prInfo,lineItems,userId,dbClient);
       PR.insert("requested_releaser",userId,PurchaseRequest.STRING);
       // don't mark pr_status as "posubmit" until we get a doc number
       // on the rli in the salesOrder object
       //trong 4/10
       PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
       updateRequestLineStatus(rNum,0,"In Progress","Open");
       //3-05-02 I DON'T think I need this -> PR.commit();
       //3-05-02 I DON'T think I need this -> PR.load();
       outH.put("RETURN_CODE",new Boolean(true));
       outH.put("NEXT_STATUS","posubmit");
     }else if (action.equalsIgnoreCase("USE_APPROVE")) {
       RequestLineItem rli = new RequestLineItem(db);
       rli.setPRNumber(PR.getPRNumber().intValue());
       for (int i = 0;i<lineItems.size();i++){
         Hashtable h = (Hashtable)lineItems.elementAt(i);
         String partNo = (String)h.get("PART_NUM");
         rli.setFacPartNo(partNo);
         rli.approveUsage(userId);
       }
       //if finance approver decided to reject purchase request while use approver is looking at it
       if (!PR.getPRStatus().equalsIgnoreCase("rejected")) {
        //need to determine whether the request still required finance approval and/or releaser
        if (RequestLineItem.needUseApproval(db,Integer.parseInt(prInfo.get("REQ_NUM").toString())) ) {
          outH.put("NEXT_STATUS","compchk2");
        }else {
          if (PR.getPRStatus().equalsIgnoreCase("compchk")) {
            outH.put("NEXT_STATUS","compchk");
          }else {
            if (rel.booleanValue()) {
              sendMsgToReleasers(Integer.parseInt(prInfo.get("REQ_NUM").toString()),PR.getRequestor().toString());
              PR.insert("pr_status","approved",PurchaseRequest.STRING);
              updateRequestLineStatus(rNum,0,"Pending Release","Open");
              outH.put("NEXT_STATUS","approved");
            }else {
              PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
              outH.put("NEXT_STATUS","posubmit");
              updateRequestLineStatus(rNum,0,"In Progress","Open");
            }
          }
        }
       }else {
        outH.put("NEXT_STATUS","rejected");       //return 'rejected' status
       }
       outH.put("RETURN_CODE",new Boolean(true));
     }else if (action.equalsIgnoreCase("REJECT_USE_APPROVE")) {
       RequestLineItem rli = new RequestLineItem(db);
       rli.setPRNumber(PR.getPRNumber().intValue());
       String rejectComment = (String)prInfo.get("REJECT_COMMENT");
       //this store the part no that use approver doesn't like
       Vector partNoV = new Vector();
       for (int i = 0;i<lineItems.size();i++){
         Hashtable h = (Hashtable)lineItems.elementAt(i);
         String partNo = (String)h.get("PART_NUM");
         rli.setFacPartNo(partNo);
         rli.rejectUsage(userId,rejectComment);
         partNoV.addElement(partNo);
       }

       //if finance approver decided to reject purchase request while use approver is looking at it
       if (!PR.getPRStatus().equalsIgnoreCase("rejected")) {
        sendRejectMsgToUser(PR.getPRNumber().intValue(),partNoV);
        //check to see if all line item was rejected
        if (RequestLineItem.allLineRejectedByUseApprover(db,PR.getPRNumber().intValue(),(String)prInfo.get("USE_APPROVAL_TYPE"))) {
          PR.insert("pr_status","rejected",PurchaseRequest.STRING);
          PR.insert("rejection_reason","All line items were rejected by use approver",PurchaseRequest.STRING);
          updateRequestLineStatus(rNum,0,"Rejected","Rejected");
          outH.put("NEXT_STATUS","rejected");
        }else {
			  if (RequestLineItem.linePendingUseApprover(db,PR.getPRNumber().intValue(),(String)prInfo.get("USE_APPROVAL_TYPE"))) {
			  	outH.put("NEXT_STATUS","rejected");
			  }else {
				PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
				outH.put("NEXT_STATUS","rejected");
			  }
        }
       }else {
        outH.put("NEXT_STATUS","rejected");
       }
       outH.put("RETURN_CODE",new Boolean(true));
     }else if (action.equalsIgnoreCase("REJECT_LINE_USE_APPROVE")) {
       RequestLineItem rli = new RequestLineItem(db);
       rli.setPRNumber(PR.getPRNumber().intValue());
       Integer rejectedLine = (Integer)prInfo.get("USE_REJECTED_LINE");
       String rejectComment = (String)prInfo.get("REJECT_COMMENT");
       Hashtable h = (Hashtable)lineItems.elementAt(rejectedLine.intValue()-1);
       String partNo = (String)h.get("PART_NUM");
       rli.setFacPartNo(partNo);
       rli.rejectUsage(userId,rejectComment);

       //this store the part no that use approver doesn't like
       Vector partNoV = new Vector();
       partNoV.addElement(partNo);

       //if finance approver decided to reject purchase request while use approver is looking at it
       if (!PR.getPRStatus().equalsIgnoreCase("rejected")) {
        sendRejectMsgToUser(PR.getPRNumber().intValue(),partNoV);
        //check to see if all line item was rejected
        if (RequestLineItem.allLineRejectedByUseApprover(db,PR.getPRNumber().intValue(),(String)prInfo.get("USE_APPROVAL_TYPE"))) {
          PR.insert("pr_status","rejected",PurchaseRequest.STRING);
          PR.insert("rejection_reason","All line items were rejected by use approver",PurchaseRequest.STRING);
          updateRequestLineStatus(rNum,0,"Rejected","Rejected");
          outH.put("NEXT_STATUS","rejected");
        }else {
          if (RequestLineItem.linePendingUseApprover(db,PR.getPRNumber().intValue(),(String)prInfo.get("USE_APPROVAL_TYPE"))) {
			  	outH.put("NEXT_STATUS","rejected");
			  }else {
				PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
				outH.put("NEXT_STATUS","rejected");
			  }
        }
       }else {
        outH.put("NEXT_STATUS","rejected");
       }
       outH.put("RETURN_CODE",new Boolean(true));
     }

     //trong 3/29 set it to zero as default
     outH.put("ERROR_LINE",new Integer(0));
     outH.put("ERROR_ROW",new Integer(0));
     outH.put("ERROR_COL",new Integer(0));
     return outH;
   }

   public Hashtable submitRequest(String action,Hashtable prInfo, Vector lineItems,String userId,String dbClient)throws Exception{
     Hashtable outH = new Hashtable();
     Boolean returnCode = new Boolean(true);
     String msg = "";
     int rNum = Integer.parseInt(prInfo.get("REQ_NUM").toString());

     //if action is submit and current user is not the orginial requestor then change requestor of MR
     //to this user (alterante requestor) and everything else should remain unchange
     if(action.equalsIgnoreCase("SUBMIT")){
       Personnel thisUser = new Personnel(db);
       thisUser.setPersonnelId(Integer.parseInt(userId));
       if (thisUser.isAlternateRequestor(rNum)) {
         db.doUpdate("update purchase_request set requestor = "+userId+" where pr_number = "+rNum);
       }
     }

     PurchaseRequest PR = new PurchaseRequest(db);
     PR.setPRNumber(rNum);
     PR.load();
     String nextStatus = "";
     if (prInfo.containsKey("CALL_FROM_APPROVER_EDIT_MR")) {
       nextStatus = getNextStatus(PR, prInfo, lineItems,userId, action);
     }else {
       nextStatus = getNextStatus(PR, prInfo, lineItems,PR.getRequestor().toString(), action);
     }
     Vector vvv = getPrRules(prInfo,lineItems);
     Boolean app = (Boolean)vvv.elementAt(0);
     Boolean rel = (Boolean)vvv.elementAt(1);
     Boolean po = (Boolean)vvv.elementAt(2);
     //trong 4/9 setting pref_acc_sys
     FacPref fp = new FacPref(db);
     fp.setFacId(prInfo.get("FACILITY_ID").toString());
     fp.setUserId(PR.getRequestor().toString());
     fp.insert("PREFERRED_ACCOUNT_SYS_ID",prInfo.get("ACC_SYS_ID").toString(),fp.STRING);
     //if first time then insert credit card into purchase_request
     boolean creditCardInserted = false;
     String tempPRVal = "";
     String tempRLIVal = "";
     boolean ecomerce = false;
     if(action.equalsIgnoreCase("SAVE")){
       //check to see if facility is an ecomerce facility
       ecomerce = HelpObjs.countQuery(db,"select count(*) from facility where ecommerce = 'y' and facility_id = '"+prInfo.get("FACILITY_ID").toString()+"'") > 0;
       //just save the info, no audits
       String tmp = prInfo.get("END_USER").toString();
       if (!BothHelpObjs.isBlankString(tmp)) {
        tempPRVal += "end_user = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tmp)+"',";
       }
       tmp = prInfo.get("DEPARTMENT").toString();
       if (!BothHelpObjs.isBlankString(tmp)) {
        tempPRVal += "department = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tmp)+"',";
       }
       tmp = prInfo.get("CONTACT_INFO").toString();
       if (!BothHelpObjs.isBlankString(tmp)) {
        tempPRVal += "contact_info = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tmp)+"',";
       }
       if(prInfo.get("APPROVER") != null){
         tempPRVal += "requested_finance_approver = "+prInfo.get("APPROVER").toString()+",";
       }
       //remove the last commas and update
       if (tempPRVal.length() > 1) {
         tempPRVal = tempPRVal.substring(0,tempPRVal.length()-1);
         db.doUpdate("update purchase_request set "+tempPRVal+" where pr_number = "+rNum);
       }
       tempPRVal = "";      //reset val to blank

       //trong 3/8/00
       if (PR.getEngineeringEval()) {
          ChargeNumber cn = new ChargeNumber(db);
          for (int i = 0;i<lineItems.size();i++){
            Hashtable h = (Hashtable)lineItems.elementAt(i);
            //qty
            try {
              Integer tI = new Integer(h.get("QTY").toString());
              if (tI.intValue() <= 0){
                msg =  "Please enter a valid quantity.";
                outH.put("RETURN_CODE",new Boolean(false));
                outH.put("MSG",msg);
                //trong 3/29 set it to zero as default
                outH.put("ERROR_LINE",new Integer(0));
                outH.put("ERROR_ROW",new Integer(0));
                outH.put("ERROR_COL",new Integer(0));
                return outH;
              }
            } catch (Exception e){
              msg =  "Please enter a valid quantity.";
              outH.put("RETURN_CODE",new Boolean(false));
              outH.put("MSG",msg);
              //trong 3/29 set it to zero as default
              outH.put("ERROR_LINE",new Integer(0));
              outH.put("ERROR_ROW",new Integer(0));
              outH.put("ERROR_COL",new Integer(0));
              return outH;
            }

            // charge Number Lookup
            //trong 4/9
            Vector cv = new Vector();
            String cType = h.get("CHARGE_TYPE").toString();

            if(cType.equalsIgnoreCase("i")){
              cv = (Vector)h.get("CHARGE_NUM_INDIRECT");
            }else if(cType.equalsIgnoreCase("d")){
              cv = (Vector)h.get("CHARGE_NUM_DIRECT");
            }

            Vector chargeV = new Vector();
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
              Vector vv = myCN.checkChargeNumberNew(myCN,prInfo);
              Boolean val1 = (Boolean)vv.elementAt(0);
              Boolean val2 = (Boolean)vv.elementAt(1);
              if (!val1.booleanValue()){
                msg =  "Charge number is invalid.";
                outH.put("ERROR_LINE",new Integer(i+1));
                outH.put("ERROR_ROW",new Integer(j));
                outH.put("ERROR_COL",new Integer(0));
                outH.put("RETURN_CODE",new Boolean(false));
                outH.put("MSG",msg);
                return outH;
              }
              if (!val2.booleanValue()){
                msg =  "Charge number is invalid.";
                outH.put("ERROR_LINE",new Integer(i+1));
                outH.put("ERROR_ROW",new Integer(j));
                outH.put("ERROR_COL",new Integer(1));
                outH.put("RETURN_CODE",new Boolean(false));
                outH.put("MSG",msg);
                return outH;
              }
            }
          }
       } //end of if eng eval
       //first removing all pr_account for the given request
       PRAccount pra = new PRAccount(db);
       pra.setPRNumber(rNum);
       pra.delete();
       for(int i=0;i<lineItems.size();i++){
         tempRLIVal = "";
         Hashtable h = (Hashtable)lineItems.elementAt(i);
         RequestLineItem RLI = new RequestLineItem(db);
         RLI.setPRNumber(rNum);
         RLI.setLineItem(h.get("LINE_NUM").toString());
         RLI.load();
         tempRLIVal += "quantity = "+h.get("QTY").toString()+",critical = '"+(((Boolean)h.get("CRIT_FLAG")).booleanValue()?"y":"n")+"',"+
                       "notes = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote((h.get("NOTES").toString()==null?"":h.get("NOTES").toString()))+"',scrap = '"+(((Boolean)h.get("SCRAP_FLAG")).booleanValue()?"y":"n")+"',";
         //updating pay load ID if the request came from Ariba
         if ("Seagate".equalsIgnoreCase(db.getClient())) {
          Boolean aribaUser = (Boolean)prInfo.get("ARIBA_LOGON");
          if (aribaUser.booleanValue()) {
            String payLoadID = (String)prInfo.get("PAY_LOAD_ID");
            //payLoadID = "0";    //2-06-02 TEST NEED TO REMOVE
            if (!"0".equalsIgnoreCase(payLoadID) && !BothHelpObjs.isBlankString(payLoadID)) {
              tempRLIVal += "payload_id = '"+payLoadID+"',";
            }else {
              radian.web.emailHelpObj.sendtrongemail("Error Pay Load ID","Seagate user creating request without pay load ID for pr_number: "+rNum);
              outH.put("RETURN_CODE",new Boolean(false));
              outH.put("MSG","Critical error.\nPlease exit tcmIS and punchout again from Ariba.");
              return outH;
            }
          }
         }else if ("FEC".equalsIgnoreCase(db.getClient())) {
           Boolean aribaUser = (Boolean)prInfo.get("ARIBA_LOGON");
           if (aribaUser.booleanValue()) {
             String payLoadID = (String)prInfo.get("PAY_LOAD_ID");
             if (!"0".equalsIgnoreCase(payLoadID) && !BothHelpObjs.isBlankString(payLoadID)) {
               tempRLIVal += "payload_id = '"+payLoadID+"',";
             }else {
               radian.web.emailHelpObj.sendtrongemail("Error No Hook URL","FEC user creating request without hook URL for pr_number: "+rNum);
               outH.put("RETURN_CODE",new Boolean(false));
               outH.put("MSG","Critical error.\nPlease exit tcmIS and punchout again thru EBP.");
               return outH;
             }
           } //end of ecommerce logon
         } //end of FEC
         //DPAS
         String tmpDpas = (String)h.get("DPAS");
         if (!"None".equalsIgnoreCase(tmpDpas)) {
          tempRLIVal += "dpas_code = '"+tmpDpas+"',";
         }
         String cType = (String)h.get("CHARGE_TYPE");
         String accSysId = (String)prInfo.get("ACC_SYS_ID");
         String key1 = accSysId + cType;
         Hashtable packH = (Hashtable)prInfo.get("PACK");
         Hashtable accSysTypeH = (Hashtable)packH.get(key1);
         String npo = (String)accSysTypeH.get("PO_REQUIRED");
         String prRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
         if(cType == null)cType = "";
         if(npo.equalsIgnoreCase("p") || npo.equalsIgnoreCase("b")){
           //setting catalog price
           setCatalogPriceForChargeType(h,cType);
           tempRLIVal += "po_number = '"+(h.get("PO"+cType).toString()==null?"":h.get("PO"+cType).toString())+"',"+
                         "release_number = '"+(h.get("RELEASE_NUM"+cType).toString()==null?"":h.get("RELEASE_NUM"+cType).toString())+"',";
           if (h.containsKey("PO_UNIT_PRICE"+cType)) {
             String poUnitPrice = (String)h.get("PO_UNIT_PRICE"+cType);
             String poUnitPriceCurrency = (String)h.get("PO_UNIT_PRICE_CURRENCY_ID"+cType);
             String poQty = (String)h.get("PO_QTY"+cType);
             //if poUnitPrice and poQty is not empty then I have data to calculate catalog_price and quoted_price
             if (!BothHelpObjs.isBlankString(poUnitPrice) && !BothHelpObjs.isBlankString(poQty)) {
               String tmpPrice = "("+poQty+"/"+h.get("QTY").toString()+") * "+poUnitPrice;
               tempRLIVal += "catalog_price = " + tmpPrice + ",quoted_price = " + tmpPrice + ",";
             }
             //unit_of_sale_price
             if (!BothHelpObjs.isBlankString(poUnitPrice)) {
               tempRLIVal += "unit_of_sale_price = "+poUnitPrice+",";
             }else {
               tempRLIVal += "unit_of_sale_price = null,";
             }
             //currency
             if (!"Select One".equalsIgnoreCase(poUnitPriceCurrency) && !BothHelpObjs.isBlankString(poUnitPriceCurrency)) {
               tempRLIVal += "currency_id = '" + poUnitPriceCurrency + "',";
             }
             //po qty
             if (!BothHelpObjs.isBlankString(poQty)) {
               tempRLIVal += "unit_of_sale_quantity_per_each = "+poQty+"/"+h.get("QTY").toString()+",";
             }else {
               tempRLIVal += "unit_of_sale_quantity_per_each = null,";
             }
             //po UOM
             String poUom = (String)h.get("PO_UOM"+cType);
             if (!BothHelpObjs.isBlankString(poUom)) {
               tempRLIVal += "unit_of_sale = '"+poUom+"',";
             }else {
               tempRLIVal += "unit_of_sale = null,";
             }
           } //end of po_unit_price is in hashtable
         }else if ("c".equalsIgnoreCase(npo)) {     //10-03-01 inserting credit card info
          if (!creditCardInserted) {             //should only insert credit card one time
            Hashtable ccH = (Hashtable)prInfo.get("CREDIT_CARD_INFO");
            String ccTmp = (String)ccH.get("CREDIT_CARD_TYPE");
            if (!BothHelpObjs.isBlankString(ccTmp)) {
              tempPRVal += "credit_card_type = '"+ccTmp+"',";
            }
            ccTmp = (String)ccH.get("CREDIT_CARD_NUMBER");
            if (!BothHelpObjs.isBlankString(ccTmp)) {
              tempPRVal += "credit_card_number = "+ccTmp+",";
            }
            ccTmp = (String)ccH.get("CREDIT_CARD_EXP_DATE");
            if (!BothHelpObjs.isBlankString(ccTmp)) {
              tempPRVal += "credit_card_expiration_date = to_date('"+ccTmp+"','MM/yyyy'),";
            }
            ccTmp = (String)ccH.get("CREDIT_CARD_NAME");
            if (!BothHelpObjs.isBlankString(ccTmp)) {
              tempPRVal += "credit_card_name = '"+ccTmp+"',";
            }
            //remove the last commas and update
            if (tempPRVal.length() > 1) {
              tempPRVal = tempPRVal.substring(0,tempPRVal.length()-1);
              db.doUpdate("update purchase_request set "+tempPRVal+" where pr_number = "+rNum);
            }
            tempPRVal = "";      //reset val to blank
            creditCardInserted = true;
          }
         }
         if ("y".equalsIgnoreCase(prRequired)) {     //10-30-01 uses charge number
            Vector cv = new Vector();
            if(cType.equalsIgnoreCase("i")){
              cv = (Vector)h.get("CHARGE_NUM_INDIRECT");
            }else if(cType.equalsIgnoreCase("d")){
              cv = (Vector)h.get("CHARGE_NUM_DIRECT");
            }
            //if this is an ecomerce facility then check to see if there directed charge
            if (ecomerce && cv.size() < 1) {
              if (h.containsKey("DIRECTED_CHARGE_NUMBER"+cType.toLowerCase())) {
                cv = (Vector)h.get("DIRECTED_CHARGE_NUMBER"+cType.toLowerCase());
              }
            }

				for(int j=0;j<cv.size();j++){
              Hashtable ch = (Hashtable)cv.elementAt(j);
              if(ch.get("ACCT_NUM_1") == null ||
                ch.get("PERCENTAGE") == null ){
                continue;
              }
              String pc = ch.get("PERCENTAGE").toString();
              String a1 = ch.get("ACCT_NUM_1").toString();

              if(!BothHelpObjs.isBlankString(pc) &&
                 !BothHelpObjs.isBlankString(a1)){
                pra.setLineItem(h.get("LINE_NUM").toString());
                ChargeNumber cn = new ChargeNumber(db);
                cn.setFacilityId(prInfo.get("FACILITY_ID").toString());
                cn.setChargeType(h.get("CHARGE_TYPE").toString());
                cn.setAccountNumber(a1);
                if(!(ch.get("ACCT_NUM_2") == null)) {
                  String a2 = ch.get("ACCT_NUM_2").toString();
                  if(!BothHelpObjs.isBlankString(a2)){
                    cn.setAccountNumber2(a2);
                    pra.setAccountNumber2(a2);
                  }
                }
                pra.setAccountNumber(a1);
                pra.setPct(pc);
                pra.setChargeType(cType);    //10-03-02
                pra.insert();
                pra.commit();
              }
            }  //end of inner for each charge number
         } //end of else if uses charge number

         // relax 80%shelf life
         Boolean rsl = (Boolean)h.get("RELAX_SHELF_LIFE");
         tempRLIVal += "relax_shelf_life = '"+(rsl.booleanValue()?"y":"n")+"',";

			String dType = h.get("DELIVERY_TYPE").toString();
         if(dType.equalsIgnoreCase("hold")){
           tempRLIVal += "delivery_type = 'hold',required_datetime = null,delivery_point = null,ship_to_location_id = null,";
         }else{
           if (!BothHelpObjs.isBlankString(h.get("DOCK").toString())) {
             if (!"Select One".equalsIgnoreCase(h.get("DOCK").toString())) {
               tempRLIVal += "ship_to_location_id = '" + h.get("DOCK").toString() + "',";
             }else {
               tempRLIVal += "ship_to_location_id = null,";
             }
           }else {
             tempRLIVal += "ship_to_location_id = null,";
           }
           //6-07-01 change require_datetime if it is < sysdate when releaser release it.
           if (isReleaseApprover) {
            String myDelD = h.get("DEL_DATE").toString();
            //Date d = new Date();
            Calendar cal = Calendar.getInstance();
            String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
            if (Date.parse(cdate) > Date.parse(myDelD)) {
              LineItemView liv = new LineItemView(db);
              liv.setFacilityID(PR.getFacilityId());
              liv.setPRNumber(PR.getPRNumber().intValue());
              liv.setCatalogID(RLI.getCatalogID());
				  liv.setCatalogCompanyId(RLI.getCatalogCompanyId());
				  liv.setItemType(RLI.getItemType());
              liv.setFacPartNo(RLI.getFacPartNo());
              liv.setPartGroupNo(RLI.getPartGoupNo());
              liv.setInventoryGroup(RLI.getInventoryGroup());
              String newNeedDate = liv.getDefNeedByDate();
              tempRLIVal += "required_datetime = to_date('"+newNeedDate+"','MM/dd/yyyy'),";
              String esub = "Material request: "+RLI.getPRNumber().toString()+" was approved/released";
              String emsg = "Your material request "+RLI.getPRNumber().toString()+" was approved/released\n";
              emsg += "after your specified need date.  tcmIS updated your need date to "+newNeedDate+".\n";
              emsg += "If you don't need this material please cancel it in tcmIS.";
              HelpObjs.sendMail(db,esub,emsg,Integer.parseInt(PR.getRequestor().toString()),false);
            }else {
              tempRLIVal += "required_datetime = to_date('"+h.get("DEL_DATE").toString()+"','MM/dd/yyyy'),";
            }
           }else {
            tempRLIVal += "required_datetime = to_date('"+h.get("DEL_DATE").toString()+"','MM/dd/yyyy'),";
           }
           tempRLIVal += "delivery_point = '"+h.get("DELIVER_TO").toString()+"',delivery_type = '"+h.get("DELIVERY_TYPE").toString()+"',";
           // Delivery Schedule
           DeliverySchedule ds = new DeliverySchedule(db);
           ds.setPrNum(rNum);
           ds.setLineNum(RLI.getLineItem());
           ds.saveSchedule((Vector)h.get("DELIVERY_SCHEDULE"),PR.getRequestor().intValue(),dbClient);
         }
         //customer requisition number
         if (h.containsKey("CUSTOMER_REQUISITION_NUMBER")) {
           String customerRequisitionNumber = h.get("CUSTOMER_REQUISITION_NUMBER").toString();
           if (BothHelpObjs.isBlankString(customerRequisitionNumber)) {
             tempRLIVal += "customer_requisition_number = null,";
           }else {
             tempRLIVal += "customer_requisition_number = '"+customerRequisitionNumber+"',";
           }
         }

         tempRLIVal += "last_updated = sysdate,last_updated_by = "+userId+",charge_type = '"+h.get("CHARGE_TYPE").toString()+"'";
         //Now update request_line_item
         db.doUpdate("update request_line_item set "+tempRLIVal+" where pr_number = "+rNum+ " and line_item = '"+h.get("LINE_NUM").toString()+"'");
       }  //end of for each line item
       //trong 3/29 set it to zero as default
       outH.put("ERROR_LINE",new Integer(0));
       outH.put("ERROR_ROW",new Integer(0));
       outH.put("ERROR_COL",new Integer(0));

       outH.put("RETURN_CODE",returnCode);
       outH.put("MSG",msg);
       outH.put("NEXT_STATUS",PR.getPRStatus());
       return outH;
     }else if(action.equalsIgnoreCase("SUBMIT")){
       //get list of line item that might be dropship_override
        Vector dropShipOverrideLineItems = getDropShipOverrideLineItems("entered","REQUEST",rNum);
        boolean useApproverNeeded = false;
        ChargeNumber cn = new ChargeNumber(db);
        for (int i = 0;i<lineItems.size();i++){
         Hashtable h = (Hashtable)lineItems.elementAt(i);
         String cType = h.get("CHARGE_TYPE").toString();
         //setting catalog price
         //setCatalogPriceForChargeType(h,cType);
         // charge Number Lookup
         String accSysId = (String)prInfo.get("ACC_SYS_ID");
         String key1 = accSysId + cType;
         Hashtable packH = (Hashtable)prInfo.get("PACK");
         Hashtable accSysTypeH = (Hashtable)packH.get(key1);
         String npo = (String)accSysTypeH.get("PO_REQUIRED");
         //frist audit credit card info if required
         if ("c".equalsIgnoreCase(npo)) {
            Hashtable ccH = (Hashtable)prInfo.get("CREDIT_CARD_INFO");
            if (!creditCardInfoAuditOkay(ccH,outH,i+1)) {
              return outH;
            }
         }
         //next audit charge number info
         if (!chargeNumberAuditOkay(prInfo,h,outH,i+1,cType)) {
           return outH;
         }
         //additional validation
         if (!additionalChargeNumberValidation(prInfo,h,outH,i+1,cType,accSysId)) {
           return outH;
         }

         //audit use approval here
         if (prInfo.containsKey("CALL_FROM_APPROVER_EDIT_MR")) {
           if (needUseApproval(userId,PR.getFacilityId(),PR.getPRNumber(),i,h.get("QTY").toString(),(Boolean)h.get("SCRAP_FLAG"))) {
             useApproverNeeded = true;
           }
         }else {
           if (needUseApproval(PR.getRequestor().toString(),PR.getFacilityId(),PR.getPRNumber(),i,h.get("QTY").toString(),(Boolean)h.get("SCRAP_FLAG"))) {
             useApproverNeeded = true;
           }
         }

         //if the part is drop ship orderride then update item type = OOR and relax shelf life = n
         boolean dropShipOverrideAlreadySet = false;
         String[] dropShipOverRide = dropShipOverride(PR.getPRNumber(),i+1);
         if (dropShipOverrideLineItems.contains((new Integer(i+1)).toString())) {
          String query = "update request_line_item set relax_shelf_life = 'n',drop_ship_override = 'Y' where pr_number = "+PR.getPRNumber()+" and line_item = '"+(i+1)+"'";
          db.doUpdate(query);
          h.put("RELAX_SHELF_LIFE",new Boolean(false));
          dropShipOverrideAlreadySet = true;
         }

         //if estitablist stock flag = 'x' then set relax shelf life = 'y'
         if ("X".equalsIgnoreCase(dropShipOverRide[1])) {
          if (!dropShipOverrideAlreadySet) {
            String query = "update request_line_item set item_type = 'OOR',relax_shelf_life = 'y' where pr_number = " + PR.getPRNumber() + " and line_item = '" + (i + 1) + "'";
            db.doUpdate(query);
            h.put("RELAX_SHELF_LIFE",new Boolean(true));
          }else {
            h.put("RELAX_SHELF_LIFE", new Boolean(false));
          }
         }

         //1-23-03 if schedule of MM or newMM then set item_type = 'OOR', relax_shelf_life and notify user_group -> ScheduleMMNotification
         if ((BothHelpObjs.isMinMax((String)h.get("ITEM_TYPE"))) && "Schedule".equalsIgnoreCase((String)h.get("DELIVERY_TYPE")) ) {
          if (!dropShipOverrideAlreadySet) {
            String query = "update request_line_item set item_type = 'OOR',relax_shelf_life = 'y' where pr_number = " + PR.getPRNumber() + " and line_item = '" + (i + 1) + "'";
            db.doUpdate(query);
            h.put("RELAX_SHELF_LIFE",new Boolean(true));
          }else {
            h.put("RELAX_SHELF_LIFE",new Boolean(false));
          }
          notifyScheduleMM(PR.getPRNumber(), i + 1,(String) h.get("SCHEDULE_MM_REASON"),(String) h.get("PART_NUM"),(String) h.get("ITEM_NUM"));
         }
       } //end of for each line item

       FinanceApproverList fal = new FinanceApproverList(db);
       Integer approver = new Integer(0);
       if (prInfo.containsKey("CALL_FROM_APPROVER_EDIT_MR")) {
         approver = fal.getApprover(PR.getFacilityId(),getExtendedPrice(lineItems),new Integer(userId));
       }else {
         approver = fal.getApprover(PR.getFacilityId(),getExtendedPrice(lineItems),PR.getRequestor());
       }
       if(approver.intValue() > 0){
         prInfo.put("APPROVER",approver.toString());
       }
       Hashtable hhh = submitRequest("SAVE",prInfo,lineItems,userId,dbClient);
       Boolean b = (Boolean)hhh.get("RETURN_CODE");
       if (!b.booleanValue()) {
          outH.put("RETURN_CODE",new Boolean(false));
          outH.put("MSG","Critical error.\nPlease exit tcmIS and try again.");
          return outH;
       }
       //set submit status
       if (!setSubmitNextStatus(prInfo,PR,nextStatus,useApproverNeeded,rNum,outH)) {
         return outH;
       }
       nextStatus = (String)prInfo.get("NEXT_STATUS");
       PR.insert("submitted_date","nowDate",PurchaseRequest.DATE);
       //send mr notification for global warming potential
       sendMrWithGlobalWarmingPotentialNotification(rNum);
       outH.put("NEXT_STATUS",nextStatus);
       outH.put("RETURN_CODE",new Boolean(true));
     }else if ("UPDATE".equalsIgnoreCase(action)) {
       for (int i = 0;i<lineItems.size();i++){
         Hashtable h = (Hashtable) lineItems.elementAt(i);
         String cType = h.get("CHARGE_TYPE").toString();
         // charge Number Lookup
         String accSysId = (String) prInfo.get("ACC_SYS_ID");
         String key1 = accSysId + cType;
         Hashtable packH = (Hashtable) prInfo.get("PACK");
         Hashtable accSysTypeH = (Hashtable) packH.get(key1);
         String npo = (String) accSysTypeH.get("PO_REQUIRED");
         //frist audit credit card info if required
         if ("c".equalsIgnoreCase(npo)) {
           Hashtable ccH = (Hashtable) prInfo.get("CREDIT_CARD_INFO");
           if (!creditCardInfoAuditOkay(ccH, outH, i + 1)) {
             return outH;
           }
         }
         //next audit charge number info
         if (!chargeNumberAuditOkay(prInfo, h, outH, i + 1, cType)) {
           return outH;
         }
         //additional validation
         if (!additionalChargeNumberValidation(prInfo,h,outH,i+1,cType,accSysId)) {
           return outH;
         }
       }
       Hashtable hhh = submitRequest("SAVE",prInfo,lineItems,userId,dbClient);
       Boolean b = (Boolean)hhh.get("RETURN_CODE");
       if (!b.booleanValue()) {
          outH.put("RETURN_CODE",new Boolean(false));
          outH.put("MSG","Critical error.\nPlease exit tcmIS and try again.");
          return outH;
       }
       outH.put("NEXT_STATUS",nextStatus);
       outH.put("RETURN_CODE",new Boolean(true));
     }else if(action.equalsIgnoreCase("APPROVE")){
       String prTmpVal = "update purchase_request set requested_finance_approver = "+userId+",finance_approved_date = sysdate,last_updated = sysdate,last_updated_by = "+userId;
       //if the status of purchase request haven't change then continue, otherwise
       if (!nextStatus.equalsIgnoreCase("rejected")) {
         String changeValue = getChangeData(rNum);
         if (!"N".equalsIgnoreCase(changeValue)) {
           prInfo.put("CALL_FROM_APPROVER_EDIT_MR","FROM_FINANCE_APPROVER");
           Hashtable tmpH = submitRequest("SUBMIT",prInfo,lineItems,userId,dbClient);
           nextStatus = (String) tmpH.get("NEXT_STATUS");
           Boolean tmpDone = (Boolean) tmpH.get("RETURN_CODE");
           if (tmpDone.booleanValue()) {
             sendUserChangedNotification(rNum,userId,changeValue,PR.getRequestor());
             outH.put("RETURN_CODE",new Boolean(true));
             outH.put("NEXT_STATUS",nextStatus);
           }else {
             String tmpMsg = "Approver: "+userId+"\npr_number: "+rNum+"\n"+(String)tmpH.get("MSG");
             radian.web.emailHelpObj.sendtrongemail("Error while finance approver change data and approved",tmpMsg);
             outH.put("RETURN_CODE",new Boolean(false));
             outH.put("MSG",(String)tmpH.get("MSG"));
             outH.put("NEXT_STATUS",nextStatus);
           }
         }else {
           //check to see if this request still need use approval
           if (RequestLineItem.needUseApproval(db,Integer.parseInt(prInfo.get("REQ_NUM").toString()))) {
             prTmpVal += ",pr_status = 'compchk2'";
             String additionalWhere = "and (use_approval_status = 'pending' or list_approval_status = 'Pending') and category_status in ('Draft','Open')";
             updateRequestLineStatus(rNum, 0, "Pending Use Approval", "Open",additionalWhere);
             //release line(s) that does not require use_approval
             if (nextStatus.equalsIgnoreCase("POSUBMIT")){
               RequestLineItem rli = new RequestLineItem(db);
               rli.releaseLineItem(rNum,"and use_approval_status = 'approved' and list_approval_status = 'Approved'");
               rli = null;
             }
             nextStatus = "USE_APPROVE";
           }else {
             if (rel.booleanValue()) {
               sendMsgToReleasers(Integer.parseInt(prInfo.get("REQ_NUM").toString()),PR.getRequestor().toString());
               prTmpVal += ",pr_status = 'approved'";
               updateRequestLineStatus(rNum, 0, "Pending Release", "Open");
               nextStatus = "RELEASE";
             }else {
               isReleaseApprover = true;
               submitRequest("SAVE", prInfo, lineItems, userId, dbClient);
               prTmpVal += ",pr_status = 'posubmit'";
               String additionalWhere = "and category_status in ('Draft','Open')";
               updateRequestLineStatus(rNum, 0, "In Progress", "Open",additionalWhere);
               nextStatus = "POSUBMIT";
             }
           }
           outH.put("RETURN_CODE",new Boolean(true));
           outH.put("NEXT_STATUS",nextStatus);
         }  //end of no change
       }  //end of not rejected
       //update purchase request with last update by and status
       prTmpVal += " where pr_number = "+rNum;
       db.doUpdate(prTmpVal);
     }else if(action.equalsIgnoreCase("RELEASE")){
       if(!hasNeededReleaseNum(prInfo,lineItems)) {
         msg =  "Please enter PO and reference numbers.";
         outH.put("RETURN_CODE",new Boolean(false));
         outH.put("MSG",msg);
         outH.put("ERROR_LINE",new Integer(0));
         outH.put("ERROR_ROW",new Integer(0));
         outH.put("ERROR_COL",new Integer(0));
         return outH;
       }
       String prTmpVal = "update purchase_request set requested_releaser = "+userId+",mr_released_date = sysdate,last_updated = sysdate,last_updated_by = "+userId;
       String changeValue = getChangeData(rNum);
       if (!"N".equalsIgnoreCase(changeValue)) {
         prInfo.put("CALL_FROM_APPROVER_EDIT_MR","FROM_RELEASER");
         Hashtable tmpH = submitRequest("SUBMIT",prInfo,lineItems,userId,dbClient);
         nextStatus = (String) tmpH.get("NEXT_STATUS");
         Boolean tmpDone = (Boolean) tmpH.get("RETURN_CODE");
         if (tmpDone.booleanValue()) {
           sendUserChangedNotification(rNum,userId,changeValue,PR.getRequestor());
           outH.put("RETURN_CODE",new Boolean(true));
           outH.put("NEXT_STATUS",nextStatus);
         }else {
           String tmpMsg = "Approver: "+userId+"\npr_number: "+rNum+"\n"+(String)tmpH.get("MSG");
           radian.web.emailHelpObj.sendtrongemail("Error while releaser change data and approved",tmpMsg);
           outH.put("RETURN_CODE",new Boolean(false));
           outH.put("MSG",(String)tmpH.get("MSG"));
           outH.put("NEXT_STATUS",nextStatus);
         }
       }else {
         isReleaseApprover = true;
         submitRequest("SAVE", prInfo, lineItems, userId, dbClient);
         prTmpVal += ",pr_status = 'posubmit'";
         String additionalWhere = "and category_status in ('Draft','Open')";
         updateRequestLineStatus(rNum, 0, "In Progress", "Open",additionalWhere);
         outH.put("RETURN_CODE", new Boolean(true));
         outH.put("NEXT_STATUS", "posubmit");
       }  //end of no change
       prTmpVal += " where pr_number = "+rNum;
       db.doUpdate(prTmpVal);
     }else if (action.equalsIgnoreCase("USE_APPROVE")) {
       RequestLineItem rli = new RequestLineItem(db);
       rli.setPRNumber(PR.getPRNumber().intValue());
       //set the real line item so I can update the database correctly
       //i.e. I only show use approver line item that pending his/her approval
       //if MR contain three line items and line 1 and 3 is pending approval and line 2 is approved
       //I display to approver as line 1 and 2
       Vector tmpLineV = (Vector)prInfo.get("LINE_ITEM_WAITING_FOR_APPROVAL");
       for (int jj = 0; jj < tmpLineV.size(); jj++) {
         Hashtable tmpH = (Hashtable)lineItems.elementAt(jj);
         tmpH.put("LINE_NUM",tmpLineV.elementAt(jj));
       }
       //check to see whether approver approved all line waiting approval or certain line
       if ("All".equalsIgnoreCase((String)prInfo.get("USE_APPROVED_TYPE"))) {
			ListApprover listApprover = new ListApprover(db);
			for (int i = 0;i<lineItems.size();i++){
           Hashtable h = (Hashtable)lineItems.elementAt(i);
           rli.setLineItem((String)h.get("LINE_NUM"));
           rli.setFacPartNo((String)h.get("PART_NUM"));
			  if ("useLimit".equalsIgnoreCase((String)prInfo.get("USE_APPROVAL_TYPE"))) {
			  	rli.approveUsage(userId);
				//CHECK TO SEE IF USER CAN ALSO APPROVE LIST LIMIT IF IT IS PENDING FOR LIST LIMIT
				if (ListApprover.needMyListApproval(db,PR.getPRNumber().intValue(),(String)h.get("LINE_NUM"),userId)) {
					listApprover.approveListUsage(PR.getPRNumber(),(String)h.get("LINE_NUM"),userId,"");
				}
			  }else {
				listApprover.approveListUsage(PR.getPRNumber(),(String)h.get("LINE_NUM"),userId,"");
				//CHECK TO SEE IF USER CAN ALSO APPROVE USE LIMIT IF IT IS PENDING FOR USE LIMIT
				if (UseApprover.needMyUseApproval(db,PR.getPRNumber().intValue(),(new Integer(userId)).intValue())) {
					rli.approveUsage(userId);
				}

			  }
			}
       }else {
         Integer tmpRow = (Integer)prInfo.get("USE_APPROVAL_APPROVED_LINE");
         Hashtable h = (Hashtable)lineItems.elementAt(tmpRow.intValue());
         rli.setLineItem((String)h.get("LINE_NUM"));
         rli.setFacPartNo((String)h.get("PART_NUM"));
         if ("useLimit".equalsIgnoreCase((String)prInfo.get("USE_APPROVAL_TYPE"))) {
				rli.approveUsage(userId);
				//CHECK TO SEE IF USER CAN ALSO APPROVE LIST LIMIT IF IT IS PENDING FOR LIST LIMIT
				if (ListApprover.needMyListApproval(db,PR.getPRNumber().intValue(),(String)h.get("LINE_NUM"),userId)) {
					ListApprover listApprover = new ListApprover(db);
					listApprover.approveListUsage(PR.getPRNumber(),(String)h.get("LINE_NUM"),userId,"");
				}
			  }else {
				ListApprover listApprover = new ListApprover(db);
				listApprover.approveListUsage(PR.getPRNumber(),(String)h.get("LINE_NUM"),userId,"");
				//CHECK TO SEE IF USER CAN ALSO APPROVE USE LIMIT IF IT IS PENDING FOR USE LIMIT
				if (UseApprover.needMyUseApproval(db,PR.getPRNumber().intValue(),(new Integer(userId)).intValue())) {
					rli.approveUsage(userId);
				}
			  }
       }
       String prTmpVal = "update purchase_request set last_updated = sysdate,last_updated_by = "+userId;
       //if finance approver decided to reject purchase request while use approver is looking at it
       if (!PR.getPRStatus().equalsIgnoreCase("rejected")) {
        String changeValue = getChangeData(rNum);
        if (!"N".equalsIgnoreCase(changeValue)) {
          prInfo.put("CALL_FROM_APPROVER_EDIT_MR","FROM_USE_APPROVER");
          Hashtable tmpH = submitRequest("SUBMIT",prInfo,lineItems,userId,dbClient);
          nextStatus = (String) tmpH.get("NEXT_STATUS");
          Boolean tmpDone = (Boolean) tmpH.get("RETURN_CODE");
          if (tmpDone.booleanValue()) {
            sendUserChangedNotification(rNum,userId,changeValue,PR.getRequestor());
            outH.put("RETURN_CODE",new Boolean(true));
            outH.put("NEXT_STATUS",nextStatus);
          }else {
            String tmpMsg = "Approver: "+userId+"\npr_number: "+rNum+"\n"+(String)tmpH.get("MSG");
            radian.web.emailHelpObj.sendtrongemail("Error while releaser change data and approved",tmpMsg);
            outH.put("RETURN_CODE",new Boolean(false));
            outH.put("MSG",(String)tmpH.get("MSG"));
            outH.put("NEXT_STATUS",nextStatus);
          }
        }else {
          //need to determine whether the request still required finance approval and/or releaser
          if (RequestLineItem.needUseApproval(db,Integer.parseInt(prInfo.get("REQ_NUM").toString())) ) {
            outH.put("NEXT_STATUS","compchk2");
            //release line item that just approved
            rli.releaseLineItem(rNum,"and use_approval_status = 'approved' and list_approval_status = 'Approved'");
          }else {
            if (PR.getPRStatus().equalsIgnoreCase("compchk")) {
              outH.put("NEXT_STATUS","compchk");
            }else {
              if (rel.booleanValue()) {
                sendMsgToReleasers(Integer.parseInt(prInfo.get("REQ_NUM").toString()),PR.getRequestor().toString());
                prTmpVal += ",pr_status = 'approved'";
                String additionalWhere = "and category_status in ('Draft','Open')";
                updateRequestLineStatus(rNum,0,"Pending Release","Open",additionalWhere);
                outH.put("NEXT_STATUS","approved");
              }else {
                prTmpVal += ",pr_status = 'posubmit'";
                outH.put("NEXT_STATUS","posubmit");
                String additionalWhere = "and category_status in ('Draft','Open')";
                updateRequestLineStatus(rNum,0,"In Progress","Open",additionalWhere);
              }
            }
          }  //end of not needing use approval
        }  //end of no change
       }else {
        outH.put("NEXT_STATUS","rejected");       //return 'rejected' status
       }  //end of rejected
       //update purchase request
       prTmpVal += " where pr_number = "+rNum;
       db.doUpdate(prTmpVal);
       outH.put("RETURN_CODE",new Boolean(true));
     }else if (action.equalsIgnoreCase("REJECT_USE_APPROVE")) {
       RequestLineItem rli = new RequestLineItem(db);
       rli.setPRNumber(PR.getPRNumber().intValue());
       String rejectComment = (String)prInfo.get("REJECT_COMMENT");
       //set the real line item so I can update the database correctly
       //i.e. I only show use approver line item that pending his/her approval
       //if MR contain three line items and line 1 and 3 is pending approval and line 2 is approved
       //I display to approver as line 1 and 2
       Vector tmpLineV = (Vector)prInfo.get("LINE_ITEM_WAITING_FOR_APPROVAL");
       for (int jj = 0; jj < tmpLineV.size(); jj++) {
         Hashtable tmpH = (Hashtable)lineItems.elementAt(jj);
         tmpH.put("LINE_NUM",tmpLineV.elementAt(jj));
       }
       //this store the part no that use approver doesn't like
       Vector partNoV = new Vector();
		 ListApprover listApprover = new ListApprover(db);
		 for (int i = 0;i<lineItems.size();i++){
         Hashtable h = (Hashtable)lineItems.elementAt(i);
         String partNo = (String)h.get("PART_NUM");
         rli.setFacPartNo(partNo);
         rli.setLineItem((String)h.get("LINE_NUM"));
			if ("useLimit".equalsIgnoreCase((String)prInfo.get("USE_APPROVAL_TYPE"))) {
				rli.rejectUsage(userId,rejectComment);
			}else {
				listApprover.rejectListUsage(PR.getPRNumber(),(String)h.get("LINE_NUM"),userId,rejectComment);
			}
			partNoV.addElement(partNo);
       }

       //if finance approver decided to reject purchase request while use approver is looking at it
       if (!PR.getPRStatus().equalsIgnoreCase("rejected")) {
        sendRejectMsgToUser(PR.getPRNumber().intValue(),partNoV);
        //check to see if all line item was rejected
        if (RequestLineItem.allLineRejectedByUseApprover(db,PR.getPRNumber().intValue(),(String)prInfo.get("USE_APPROVAL_TYPE"))) {
          PR.insert("pr_status","rejected",PurchaseRequest.STRING);
          PR.insert("rejection_reason","All line items were rejected by use approver",PurchaseRequest.STRING);
          outH.put("NEXT_STATUS","rejected");
        }else {
          if (RequestLineItem.linePendingUseApprover(db,PR.getPRNumber().intValue(),(String)prInfo.get("USE_APPROVAL_TYPE"))) {
			  	outH.put("NEXT_STATUS","rejected");
			  }else {
				PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
				outH.put("NEXT_STATUS","rejected");
			  }
        }
       }else {
         outH.put("NEXT_STATUS","rejected");
       }
       outH.put("RETURN_CODE",new Boolean(true));
     }else if (action.equalsIgnoreCase("REJECT_LINE_USE_APPROVE")) {
       RequestLineItem rli = new RequestLineItem(db);
       rli.setPRNumber(PR.getPRNumber().intValue());
       Integer rejectedLine = (Integer)prInfo.get("USE_APPROVAL_REJECT_LINE");
       String rejectComment = (String)prInfo.get("REJECT_COMMENT");
       //set the real line item so I can update the database correctly
       //i.e. I only show use approver line item that pending his/her approval
       //if MR contain three line items and line 1 and 3 is pending approval and line 2 is approved
       //I display to approver as line 1 and 2
       Vector tmpLineV = (Vector)prInfo.get("LINE_ITEM_WAITING_FOR_APPROVAL");
       for (int jj = 0; jj < tmpLineV.size(); jj++) {
         Hashtable tmpH = (Hashtable)lineItems.elementAt(jj);
         tmpH.put("LINE_NUM",tmpLineV.elementAt(jj));
       }
       //
       Hashtable h = (Hashtable)lineItems.elementAt(rejectedLine.intValue());
       String partNo = (String)h.get("PART_NUM");
       rli.setFacPartNo(partNo);
       rli.setLineItem((String)h.get("LINE_NUM"));
		 if ("useLimit".equalsIgnoreCase((String)prInfo.get("USE_APPROVAL_TYPE"))) {
			rli.rejectUsage(userId,rejectComment);
		 }else {
			ListApprover listApprover = new ListApprover(db);
			listApprover.rejectListUsage(PR.getPRNumber(),(String)h.get("LINE_NUM"),userId,rejectComment);
		 }
		 //this store the part no that use approver doesn't like
       Vector partNoV = new Vector();
       partNoV.addElement(partNo);
       //if finance approver decided to reject purchase request while use approver is looking at it
       if (!PR.getPRStatus().equalsIgnoreCase("rejected")) {
        sendRejectMsgToUser(PR.getPRNumber().intValue(),partNoV);
        //check to see if all line item was rejected
        if (RequestLineItem.allLineRejectedByUseApprover(db,PR.getPRNumber().intValue(),(String)prInfo.get("USE_APPROVAL_TYPE"))) {
          PR.insert("pr_status","rejected",PurchaseRequest.STRING);
          PR.insert("rejection_reason","All line items were rejected by use approver",PurchaseRequest.STRING);
          outH.put("NEXT_STATUS","rejected");
        }else {
          if (RequestLineItem.linePendingUseApprover(db,PR.getPRNumber().intValue(),(String)prInfo.get("USE_APPROVAL_TYPE"))) {
			  	outH.put("NEXT_STATUS","rejected");
			  }else {
				PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
				outH.put("NEXT_STATUS","rejected");
			  }
        }
       }else {
         outH.put("NEXT_STATUS","rejected");
       }
       outH.put("RETURN_CODE",new Boolean(true));
     }

     //trong 3/29 set it to zero as default
     outH.put("ERROR_LINE",new Integer(0));
     outH.put("ERROR_ROW",new Integer(0));
     outH.put("ERROR_COL",new Integer(0));
     return outH;
   }

   /* If MR screen is required then I am not inserting data for rli.catalog_price and rli.quoted_price.
    * So at the time of submit, if there is no po_unit_price just populate it with unit_price
    * because this is the only way to insert/update rli.catalog_price and rli.quoted_price
    */
   void setCatalogPriceForChargeType(Hashtable h, String type) {
     if (!h.containsKey("PO_UNIT_PRICE"+type)) {
       h.put("PO_UNIT_PRICE"+type,h.get("UNIT_PRICE").toString());
       h.put("PO_UNIT_PRICE_CURRENCY_ID"+type,h.get("CURRENCY_ID").toString());
     }
     h.put("UNIT_PRICE_STATUS"+type,"Required");
   } //end of method

   boolean setSubmitNextStatus(Hashtable prInfo,PurchaseRequest PR, String nextStatus, boolean useApproverNeeded, int rNum, Hashtable outH) {
     boolean result = true;
     try {
       if (useApproverNeeded) {
         if(nextStatus.equalsIgnoreCase("APPROVE")){
           sendMsgToApprovers(Integer.parseInt(prInfo.get("REQ_NUM").toString()));
           PR.insert("pr_status","compchk",PurchaseRequest.STRING);
           updateRequestLineStatus(rNum,0,"Pending Finance Approval","Open");
			  prInfo.put("NEXT_STATUS","APPROVE");
         }else {
           PR.insert("pr_status","compchk2",PurchaseRequest.STRING);
			  String additionalWhere = " and (use_approval_status = 'pending' or list_approval_status = 'Pending')";
			  updateRequestLineStatus(rNum,0,"Pending Use Approval","Open",additionalWhere);
			  //if MR does not required financial approvers or releasers
           //then release line item the do not required use approvers
           if (nextStatus.equalsIgnoreCase("POSUBMIT")){
             RequestLineItem rli = new RequestLineItem(db);
             rli.releaseLineItem(rNum,"and use_approval_status = 'approved' and list_approval_status = 'Approved'");
             rli = null;
           }
			  sendMsgToUseApprovers(Integer.parseInt(prInfo.get("REQ_NUM").toString()));
           prInfo.put("NEXT_STATUS","USE_APPROVE");
			}
       }else {
         if(nextStatus.equalsIgnoreCase("APPROVE")){
           sendMsgToApprovers(Integer.parseInt(prInfo.get("REQ_NUM").toString()));
           PR.insert("pr_status","compchk",PurchaseRequest.STRING);
           updateRequestLineStatus(rNum,0,"Pending Finance Approval","Open");
           prInfo.put("NEXT_STATUS","APPROVE");
         }else if(nextStatus.equalsIgnoreCase("RELEASE")){
           sendMsgToReleasers(Integer.parseInt(prInfo.get("REQ_NUM").toString()),PR.getRequestor().toString());
           PR.insert("pr_status","approved",PurchaseRequest.STRING);
           updateRequestLineStatus(rNum,0,"Pending Release","Open");
           prInfo.put("NEXT_STATUS","RELEASE");
         }else if(nextStatus.equalsIgnoreCase("POSUBMIT")){
           if ("Seagate".equalsIgnoreCase(db.getClient())) {
				  Boolean aribaUser = (Boolean)prInfo.get("ARIBA_LOGON");
          	  if (aribaUser.booleanValue()) {
					 //if client is Seagate and using production as their cost center then update the pr_status to submitted
					 //if ("Production".equalsIgnoreCase(prInfo.get("ACC_SYS_ID").toString())) {
					 //1-15-02 if client is Seagate and using Production as their cost center and the part type is not 'SF' (Service Fee) then
					 //update pr_status to submitted.
					 boolean noBuyOrder = HelpObjs.countQuery(db,"select count(*) from request_line_item where item_type in ('BG','SF','TC','SP') and pr_number = "+rNum) > 0;
					 if (noBuyOrder) {
						//first insert into issue
						String val = "";
						try {
						  //next
						  PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
						  updateRequestLineStatus(rNum,0,"In Progress","Open");
						  prInfo.put("NEXT_STATUS","POSUBMIT");
						  Vector bv = new Vector(2);
						  Integer noBuyOrderR = new Integer(rNum);
						  bv.addElement(noBuyOrderR.toString());
						  bv.addElement("error_val");
						  val = db.doInvoiceProcedure("p_issue_no_buy_order_po_mr",bv);            //bad method name due to my false
						  if (!"OK".equalsIgnoreCase(val)) {
							 PR.insert("pr_status","entered",PurchaseRequest.STRING);
							 prInfo.put("NEXT_STATUS","DRAFT");
							 radian.web.emailHelpObj.sendtrongemail("p_issue_no_buy_order_po_mr error (pr_number: "+rNum+")",val);
							 outH.put("RETURN_CODE",new Boolean(false));
							 outH.put("MSG","Critical error.\nAn email has been sent to system admin.\nIf you don't hear anything within two hours.\nPlease contact your tcmIS CSR.");
							 return false;
						  }
						}catch (Exception ee3) {
						  ee3.printStackTrace();
						  PR.insert("pr_status","entered",PurchaseRequest.STRING);
						  prInfo.put("NEXT_STATUS","DRAFT");
						  radian.web.emailHelpObj.sendtrongemail("p_issue_no_buy_order_po_mr error (pr_number: "+rNum+")",val);
						  outH.put("RETURN_CODE",new Boolean(false));
						  outH.put("MSG","Critical error.\nAn email has been sent to system admin.\nIf you don't hear anything within two hours.\nPlease contact your tcmIS CSR.");
						  return false;
						}
					 }else if ("Production".equalsIgnoreCase(prInfo.get("ACC_SYS_ID").toString()) ||
								  "Production - ERS".equalsIgnoreCase(prInfo.get("ACC_SYS_ID").toString())) {
						try {
						  updateRequestLineStatus(rNum,0,"In Progress","Open");
						  db.doUpdate("update request_line_item set release_date = sysdate where pr_number = "+rNum);
						  PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
						  prInfo.put("NEXT_STATUS","POSUBMIT");
						  String[] args = new String[1];
						  Integer currentRNum = new Integer(rNum);
						  args[0] = currentRNum.toString();
						  db.doProcedure("p_mr_allocate",args);
						}catch(Exception dbe) {
						  radian.web.emailHelpObj.sendtrongemail("p_mr_allocate error (pr_number: "+rNum+")","Error occured while trying to call procedure");
						}
					 }else {   //otherwise pending approval
						PR.insert("pr_status","compchk",PurchaseRequest.STRING);
						updateRequestLineStatus(rNum,0,"Pending Finance Approval","Open");
						prInfo.put("NEXT_STATUS","APPROVE");
					 }
				  }else {
						PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
             		updateRequestLineStatus(rNum,0,"In Progress","Open");
						prInfo.put("NEXT_STATUS","POSUBMIT");	
				  }
			  }else if ("FEC".equalsIgnoreCase(db.getClient())) {
             Boolean aribaUser = (Boolean)prInfo.get("ARIBA_LOGON");
             if (aribaUser.booleanValue()) {
               PR.insert("pr_status","compchk",PurchaseRequest.STRING);
               updateRequestLineStatus(rNum,0,"Pending Finance Approval","Open","and request_line_status = 'Draft' and category_status = 'Draft'");
               prInfo.put("NEXT_STATUS","APPROVE");
               boolean requiredMRScreen = HelpObjs.countQuery(db,"select count(*) from pr_rules p,purchase_request pr where p.mr_screen_required = 'Y' and p.facility_id = pr.facility_id and p.account_sys_id = pr.account_sys_id and pr.pr_number = "+rNum) > 0;
               if (requiredMRScreen) {
                 db.doUpdate("insert into punchout_order_message (payload_id,punchout,pr_number) select payload_id,'Y',pr_number from request_line_item where line_item = 1 and pr_number = "+rNum);
               }
             }
           }else {   //other clients
             PR.insert("pr_status","posubmit",PurchaseRequest.STRING);
             updateRequestLineStatus(rNum,0,"In Progress","Open");
             prInfo.put("NEXT_STATUS","POSUBMIT");
           }
         }
       }
     }catch (Exception e) {
       e.printStackTrace();
       try {
         PR.insert("pr_status", "entered", PurchaseRequest.STRING);
         radian.web.emailHelpObj.sendtrongemail("Error occur while setNextStatus", "MR: "+rNum);
         outH.put("RETURN_CODE", new Boolean(false));
         outH.put("MSG", "Critical error.\nAn email has been sent to system admin.\nIf you don't hear anything within two hours.\nPlease contact your tcmIS CSR.");
         return false;
       }catch (Exception e3) {
         e3.printStackTrace();
       }
     }
     return result;
   } //end of method

    String getAdditionalValidationFunction(String cType, String accSysId) {
        String result = "";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            StringBuilder query = new StringBuilder("select additional_validation_function from account_sys where");
            query.append(" account_sys_id = '").append(accSysId).append("' and charge_type = '").append(cType).append("'");
            dbrs = db.doQuery(query.toString());
            rs=dbrs.getResultSet();
            while(rs.next()) {
                result = rs.getString("additional_validation_function");
                break;
            }
        }catch (Exception e) {
            radian.web.emailHelpObj.sendtrongemail("Failed to get additioncal validation function","Account Sys : "+accSysId+", charge type: "+cType);
        }finally {
            if (dbrs != null) {
                dbrs.close();
            }
        }
        return result;
    } //end of method

    String getResultFromFunction(String query) {
        String result = "OK";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while(rs.next()) {
                result = rs.getString("additional_validate");
                break;
            }
        }catch (Exception e) {
            radian.web.emailHelpObj.sendtrongemail("Failed to get value from validation function",query);
        }finally {
            if (dbrs != null) {
                dbrs.close();
            }
        }
        return result;
    } //end of method

    boolean additionalChargeNumberValidation(Hashtable prInfo, Hashtable h, Hashtable outH, int row, String cType, String accSysId) {
        boolean result = true;
        try {
            String tmpVal = getAdditionalValidationFunction(cType,accSysId);
            if (!BothHelpObjs.isBlankString(tmpVal)) {
                //parse string to determine how to use it
                //example: fx_ray_apex_validate(charge_number_1,charge_number_2...)
                String chargeInfo = tmpVal.substring(tmpVal.indexOf("(")+1,tmpVal.indexOf(")"));
                if (!BothHelpObjs.isBlankString(chargeInfo)) {
                    Vector cv = new Vector();
                    if (cType.equalsIgnoreCase("i")) {
                        cv = (Vector) h.get("CHARGE_NUM_INDIRECT");
                    }else if (cType.equalsIgnoreCase("d")) {
                        cv = (Vector) h.get("CHARGE_NUM_DIRECT");
                    }
                    for (int j = 0; j < cv.size(); j++) {
                        Hashtable cHash = (Hashtable) cv.elementAt(j);
                        if (!cHash.containsKey("PERCENTAGE") ||
                            cHash.get("PERCENTAGE") == null ||
                            BothHelpObjs.isBlankString(cHash.get("PERCENTAGE").toString())) {
                            continue;
                        }
                        StringBuilder query = new StringBuilder("select ").append(tmpVal.substring(0,tmpVal.indexOf("(")+1));
                        //loop each function
                        String[] chargeNumber = chargeInfo.split(",");
                        StringBuilder tmp2 = new StringBuilder("");
                        for (int i = 0; i < chargeNumber.length; i++) {
                            if ("charge_number_1".equals(chargeNumber[i])) {
                                if (!BothHelpObjs.isBlankString(tmp2.toString()))
                                    tmp2.append(",");
                                tmp2.append("'").append(HelpObjs.singleQuoteToDouble(cHash.get("ACCT_NUM_1").toString())).append("'");
                            }else if ("charge_number_2".equals(chargeNumber[i])) {
                                if (!BothHelpObjs.isBlankString(tmp2.toString()))
                                    tmp2.append(",");
                                if (!cHash.containsKey("ACCT_NUM_2") || cHash.get("ACCT_NUM_2") == null) {
                                  tmp2.append("''");
                                 } else {
                                   tmp2.append("'").append(HelpObjs.singleQuoteToDouble(cHash.get("ACCT_NUM_2").toString())).append("'");
                                 }
                            }
                        } //end of for loop
                        //put thing together
                        if (!BothHelpObjs.isBlankString(tmp2.toString())) {
                            query.append(tmp2).append(") additional_validate from dual");
                            String functionResult = getResultFromFunction(query.toString());
                            if (!"OK".equals(functionResult)) {
                                outH.put("ERROR_LINE", new Integer(row));
                                outH.put("ERROR_ROW", new Integer(j));
                                outH.put("ERROR_COL", new Integer(0));
                                outH.put("RETURN_CODE", new Boolean(false));
                                if ("label.costcenterpoolingerror".equals(functionResult))
                                    outH.put("MSG", "Please enter a valid Statistical Order for Pooling Cost Center.");
                                else if ("label.statisticalordererror".equals(functionResult))
                                    outH.put("MSG", "Please enter a non-zero Cost Center for Statistical Order.");
                                else
                                    outH.put("MSG", functionResult);
                                result = false;
                            }
                        }
                    } //end of each pr_account record
                }
            }
     }catch(Exception e) {
       e.printStackTrace();
       outH.put("ERROR_LINE", new Integer(row));
       outH.put("ERROR_ROW", new Integer(0));
       outH.put("ERROR_COL", new Integer(0));
       outH.put("RETURN_CODE", new Boolean(false));
       outH.put("MSG", "Error occur while validating charge number.");
       result = false;
     }
     return result;
   } //end of method 

   boolean chargeNumberAuditOkay(Hashtable prInfo, Hashtable h, Hashtable outH, int row, String cType) {
     boolean result = true;
     try {
       Vector cv = new Vector();
       if (cType.equalsIgnoreCase("i")) {
         cv = (Vector) h.get("CHARGE_NUM_INDIRECT");
       }
       else if (cType.equalsIgnoreCase("d")) {
         cv = (Vector) h.get("CHARGE_NUM_DIRECT");
       }
       Vector chargeV = new Vector();
       for (int j = 0; j < cv.size(); j++) {
         Hashtable cHash = (Hashtable) cv.elementAt(j);
         if (!cHash.containsKey("PERCENTAGE") ||
             cHash.get("PERCENTAGE") == null ||
             BothHelpObjs.isBlankString(cHash.get("PERCENTAGE").toString())) {
           continue;
         }
         ChargeNumber myCN = new ChargeNumber(db);
         myCN.setChargeType(cType);
         myCN.setAccountNumber(cHash.get("ACCT_NUM_1").toString());
         if (!cHash.containsKey("ACCT_NUM_2") ||
             cHash.get("ACCT_NUM_2") == null) {
           myCN.setAccountNumber2("");
         }
         else {
           myCN.setAccountNumber2(cHash.get("ACCT_NUM_2").toString());
         }
         Vector vv = myCN.checkChargeNumberNew(myCN, prInfo);
         Boolean val1 = (Boolean) vv.elementAt(0);
         Boolean val2 = (Boolean) vv.elementAt(1);
         if (!val1.booleanValue()) {
           outH.put("ERROR_LINE", new Integer(row));
           outH.put("ERROR_ROW", new Integer(j));
           outH.put("ERROR_COL", new Integer(0));
           outH.put("RETURN_CODE", new Boolean(false));
           outH.put("MSG", "Charge number is invalid.");
           result = false;
         }
         if (!val2.booleanValue()) {
           outH.put("ERROR_LINE", new Integer(row));
           outH.put("ERROR_ROW", new Integer(j));
           outH.put("ERROR_COL", new Integer(1));
           outH.put("RETURN_CODE", new Boolean(false));
           outH.put("MSG", "Charge number is invalid.");
           result = false;
         }
       }
     }catch(Exception e) {
       e.printStackTrace();
       outH.put("ERROR_LINE", new Integer(row));
       outH.put("ERROR_ROW", new Integer(0));
       outH.put("ERROR_COL", new Integer(0));
       outH.put("RETURN_CODE", new Boolean(false));
       outH.put("MSG", "Error occur while validating charge number.");
       result = false;
     }
     return result;
   } //end of method

   boolean creditCardInfoAuditOkay(Hashtable ccH, Hashtable outH, int row) {
     boolean result = true;
     if (BothHelpObjs.isBlankString((String)ccH.get("CREDIT_CARD_TYPE")) || BothHelpObjs.isBlankString((String)ccH.get("CREDIT_CARD_NUMBER")) ||
         BothHelpObjs.isBlankString((String)ccH.get("CREDIT_CARD_EXP_DATE")) || BothHelpObjs.isBlankString((String)ccH.get("CREDIT_CARD_NAME"))) {
        outH.put("ERROR_LINE",new Integer(row));
        outH.put("ERROR_ROW",new Integer(0));
        outH.put("ERROR_COL",new Integer(0));
        outH.put("RETURN_CODE",new Boolean(false));
        outH.put("MSG","Please enter credit card information for this purchase request.");
        result = false;
     }
     return result;
   } //end of method


   void sendUserChangedNotification(int prNumber, String approverID, String msg, Integer requestorID) {
     //if approver is requestor then he/she does not need email
     if (approverID.equals(requestorID.toString())) {
       return;
     }
     DBResultSet dbrs = null;
      ResultSet rs = null;
      String emailMsg = "Your material request was changed by:";
      try {
        //first get approver info
        String query = "select last_name||', '||first_name full_name,nvl(email,' ') email,nvl(phone,' ') phone from personnel where personnel_id = "+approverID;
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
          emailMsg += "\nApprover: "+rs.getString("full_name");
          emailMsg += "\nEmail:    "+rs.getString("email");
          emailMsg += "\nPhone:    "+rs.getString("phone");
        }
        emailMsg += "\nSee changes below:\n"+msg;
        HelpObjs.sendMail(db,"Your material request changed by approver",emailMsg,requestorID.intValue(),false);
      }catch (Exception e) {
        radian.web.emailHelpObj.sendtrongemail("Notify user of change make by approver","MR : "+prNumber+", approver: "+approverID+", message: "+msg);
      }finally {
        if (dbrs != null) {
          dbrs.close();
        }
      }
   } //end of method

   String getChangeData(int prNumber) {
     String result = "";
     for (int i = 0; i < origMRQuantityV.size(); i++) {
       String tmpOrig = (String) origMRQuantityV.elementAt(i);
       String tmpNew = (String) newMRQuantityV.elementAt(i);
       if (!tmpOrig.equals(tmpNew)) {
         result += "      Line: "+(i+1)+"  -  Original quantity: "+tmpOrig+"    New quantity: "+tmpNew;
       }
       //update request_line_item to cancelled if approver change quantity to zero
       if ("0".equalsIgnoreCase(tmpNew)) {
         try {
           db.doUpdate("update request_line_item set cancel_status = 'canceled',cancel_authorizer = -1,cancel_action_date = sysdate,"+
                       "cancel_comment = decode(cancel_comment,null,'',cancel_comment||'; ')||'Set to canceled because approver change qty to zero',request_line_status = 'Cancelled',category_status = 'Cancelled'"+
                       " where pr_number = "+prNumber+" and line_item = '"+(i+1)+"'");
         }catch(Exception e) {
           e.printStackTrace();
           radian.web.emailHelpObj.sendtrongemail("Error while updating request line item in getChangeData()","PR Number: "+prNumber+"   line: "+(i+1));
         }
       }
     }
     if (result.length() < 1) {
       result = "N";
     }
     return result;
   } //end of method

   void notifyScheduleMM(Integer prNumber, int lineItem, String reason, String facPartNo, String itemID) {
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector members = new Vector(10);
      try {
        String query = "select ugm.personnel_id,p.email from personnel p, user_group_member ugm,request_line_item rli where ugm.user_group_id = 'ScheduleMM' "+
                       "and ugm.facility_id = rli.inventory_group and rli.pr_number = "+prNumber+" and line_item = '"+lineItem+"' and ugm.personnel_id = p.personnel_id";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
          members.addElement(rs.getString("email"));
        }
      }catch (Exception e) {
        radian.web.emailHelpObj.sendtrongemail("Schedule Delivery of MM notification email failed","MR : "+prNumber+", line: "+lineItem);
      }finally {
        if (dbrs != null) {
          dbrs.close();
        }
      }
      if (members.size() < 1) {
        radian.web.emailHelpObj.sendtrongemail("Schedule Delivery of MM notification no member","MR : "+prNumber+", line: "+lineItem);
      }else {
        String[] receiver = new String[members.size()];
        for (int i = 0; i < members.size(); i++) {
          receiver[i] = (String)members.elementAt(i);
        }
        if ("increase".equalsIgnoreCase(reason)) {
          reason = "Increase demand";
        }else {
          reason = "Meet current demand";
        }
        HelpObjs.sendMail("Schedule Delivery of MM","MR-Line: "+prNumber.toString()+"-"+lineItem+"\nPart number: "+facPartNo+"\nItem ID: "+itemID+"\nReason: "+reason,receiver);
      }
   }  //end of method

   String[] dropShipOverride(Integer MRNumber, int MRLineItem) {
    String[] result = new String[2];
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select nvl(c.drop_ship_override, 'N') drop_ship_override,c.ESTABLISHED_STOCK_FLAG from catalog_part_inventory c,request_line_item l"+
                     " where l.catalog_id = c.catalog_id (+) and l.catalog_company_id = c.catalog_company_id(+) and l.fac_part_no = c.cat_part_no (+) and l.inventory_group = c.inventory_group (+) and"+
                     " l.part_group_no = c.part_group_no (+) and l.pr_number = "+MRNumber.intValue()+" and l.line_item = '"+MRLineItem+"'";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        result[0] = rs.getString("drop_ship_override");
        result[1] = BothHelpObjs.makeBlankFromNull(rs.getString("ESTABLISHED_STOCK_FLAG"));
      }
    }catch (Exception e) {
      result[0] = "N";
      result[1] = "";
    }finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }
    return result;
   }

   void updateRequestLineStatus(int prNo, int lineNo, String lineStatus, String catStatus ) throws Exception {
    try {
      if (lineNo > 0) {
        db.doUpdate("update request_line_item set request_line_status = '"+lineStatus+"',category_status = '"+catStatus+"' where category_status not in ('Canceled','Cancelled') and pr_number = "+prNo+ " and line_item = "+lineNo);
      }else {
        db.doUpdate("update request_line_item set request_line_status = '"+lineStatus+"',category_status = '"+catStatus+"' where category_status not in ('Canceled','Cancelled') and pr_number = "+prNo);
      }
    }catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("Error while updating request line status ("+prNo+")","Error while updating request line status");
    }
   }

   void updateRequestLineStatus(int prNo, int lineNo, String lineStatus, String catStatus, String additionalWhere ) throws Exception {
    try {
      if (lineNo > 0) {
        db.doUpdate("update request_line_item set request_line_status = '"+lineStatus+"',category_status = '"+catStatus+"' where category_status not in ('Canceled','Cancelled') and pr_number = "+prNo+ " and line_item = "+lineNo+" "+additionalWhere);
      }else {
        db.doUpdate("update request_line_item set request_line_status = '"+lineStatus+"',category_status = '"+catStatus+"' where category_status not in ('Canceled','Cancelled') and pr_number = "+prNo+" "+additionalWhere);
      }
    }catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("Error while updating request line status ("+prNo+")","Error while updating request line status");
    }
   }

   Vector getBulkGasReceiptInfo(int prNo) throws Exception {
    String query = "select a.*,rli.pr_number,rli.line_item from catalog_inventory_view a, request_line_item rli ";
    query += "where a.cat_part_no = rli.fac_part_no and rli.pr_number = "+prNo;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector result = new Vector(10);
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        String[] val = new String[8];
        int i = 0;
        val[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("pr_number"));
        val[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("line_item"));
        val[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("hub"));
        val[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("receipt_id"));
        val[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        val[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("date_of_receipt"));       //date_picked is the date_of_received
        val[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("quantity_received"));
        val[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("date_of_receipt"));       //date_delivered is the date_of_received
        result.addElement(val);
      }
    }catch(Exception e){
      radian.web.emailHelpObj.sendtrongemail("Error Bulk Gas","Error occurs while running the following query:\n"+query);
      e.printStackTrace();
    }finally{
      dbrs.close();
    }
    return result;
   }

   //this method decide whether to pass the line request along or it need use approval
	//when user submit MR
	public boolean needUseApproval(String userID, String facID, Integer prNum, int lineNum, String qty, Boolean scrap) throws Exception {
    boolean result = false;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      lineNum = lineNum+1;
		//skip use approver if this is a scrap line
	 	if (!scrap.booleanValue()) {
			String query = "select fx_mr_approval_over_limit("+prNum.intValue()+",'"+lineNum+"') over_limit from dual";
			dbrs = db.doQuery(query);
			rs=dbrs.getResultSet();
			String val = "";
			while(rs.next()) {
			  val = rs.getString("over_limit");
			}

			if ("Y".equalsIgnoreCase(val)) {
			  //check to see if the requestor is also an use approver
			  UseApprover ua = new UseApprover(db);
			  if (ua.requesterIsUseApprover(userID,facID,prNum.intValue(),lineNum)) {
				 db.doUpdate("update request_line_item set use_approval_status = 'approved',use_approver = "+userID+",use_approval_date = sysdate,charge_approval_status = 'Approved',charge_approval_date = sysdate"+
						     " where pr_number = "+prNum.intValue()+" and line_item = '"+lineNum+"'");
				 result = false;
			  }else {
				 db.doUpdate("update request_line_item set use_approval_status = 'pending',charge_approval_status = 'Approved',charge_approval_date = sysdate where pr_number = "+prNum.intValue()+" and line_item = '"+lineNum+"'");
				 result = true;
			  }
			}else {
			  db.doUpdate("update request_line_item set use_approval_status = 'approved',use_approver = "+userID+",use_approval_date = sysdate,charge_approval_status = 'Approved',charge_approval_date = sysdate"+
								 " where pr_number = "+prNum.intValue()+" and line_item = '"+lineNum+"'");
			  result = false;
			}
		}else {
			db.doUpdate("update request_line_item set use_approval_status = 'approved',use_approver = "+userID+",use_approval_date = sysdate,charge_approval_status = 'Approved',charge_approval_date = sysdate"+
                      " where pr_number = "+prNum.intValue()+" and line_item = '"+lineNum+"'");
        result = false;
		}
	 }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      if (dbrs != null) {
          dbrs.close();
        }
    }

	 //check to see if need list approval
	 if (needListApproval(userID,facID,prNum,lineNum,scrap)) {
		 result = true;
	 }

	 return result;
   }

	String getCompanyId() {
		String result = db.getClient();
		DBResultSet dbrs = null;
    	ResultSet rs = null;
		 try {
			String query = "select user from dual";
			dbrs = db.doQuery(query);
			rs=dbrs.getResultSet();
			while(rs.next()) {
			  result = rs.getString("user");
			}
		}catch (Exception e) {
      	e.printStackTrace();
    	}finally {
      	if (dbrs != null) {
          dbrs.close();
        }
    	}
		return result;
	}

	//this method decide whether to pass the line request along or it need list approval
   public boolean needListApproval(String userID, String facID, Integer prNum, int lineNum, Boolean scrap) throws Exception {
    	boolean result = false;
	 	//skip list approver if this is a scrap line
	 	if (!scrap.booleanValue()) {
		 CallableStatement cs = null;
		 try {
			Connection connect1 = db.getConnection();
			cs = connect1.prepareCall("{call p_rli_fac_list_release(?,?,?,?)}");
			cs.setString(1,getCompanyId());
			cs.setInt(2,prNum.intValue());
			cs.setString(3,(new Integer(lineNum)).toString());
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.execute();
			String value = (String) cs.getObject(4);
			if (BothHelpObjs.isBlankString(value) || "Approved".equalsIgnoreCase(value)) {
				db.doUpdate("update request_line_item set list_approval_status = 'Approved',list_approval_date = sysdate"+
								 " where pr_number = "+prNum.intValue()+" and line_item = '"+lineNum+"'");
				result = false;
			}else {
				db.doUpdate("update request_line_item set list_approval_status = 'Pending' where pr_number = "+prNum.intValue()+" and line_item = '"+lineNum+"'");
				result = true;
			}
		 }catch (Exception e) {
			e.printStackTrace();
		 } finally {
			cs.close();
		 }
	 	}else {
			db.doUpdate("update request_line_item set list_approval_status = 'Approved',list_approval_date = sysdate"+
					   " where pr_number = "+prNum.intValue()+" and line_item = '"+lineNum+"'");
			result = false;
	 	}
	 	return result;
   } //end of method

	//trong 3/28
   public int getNumericFromAlpha(String val) {
    Integer num;
    if (val.equalsIgnoreCase("y") || val.equalsIgnoreCase("n")) {
      num = new Integer(0);
    } else {
      num = new Integer(val);
    }
    return num.intValue();
   }



   public void setReleaseInfo(Hashtable headerInfo,Vector detailInfo){
     try{
     int prNum = Integer.parseInt(headerInfo.get("REQ_NUM").toString());

      for (int i = 0;i<detailInfo.size();i++){
         Hashtable dHash = (Hashtable) detailInfo.elementAt(i);
         RequestLineItem RLI = new RequestLineItem(db);
         RLI.setPRNumber(prNum);
         RLI.setLineItem(dHash.get("LINE_NUM").toString());
         RLI.load();
         String query = "update request_line_item set ";
         String type = (String)dHash.get("CHARGE_TYPE");
         String accSysId = (String)headerInfo.get("ACC_SYS_ID");
         String key1 = accSysId + type;
         Hashtable packH = (Hashtable)headerInfo.get("PACK");
         Hashtable accSysTypeH = (Hashtable)packH.get(key1);
         String needPO = (String)accSysTypeH.get("PO_REQUIRED");

         if(needPO.equalsIgnoreCase("p")){
           query += "po_number = '"+(String)dHash.get("PO"+type)+"'";
           if (HelpObjs.countQuery(db,"select count(*) from pr_rules where account_sys_id = '"+accSysId+"' and facility_id = '"+(String)headerInfo.get("FACILITY")+
                                "' and charge_type = '"+type+"' and po_seq_required <> 'N'") > 0){
             if (BothHelpObjs.isBlankString((String)dHash.get("RELEASE_NUM"+type))) {
              query += ",release_number = to_char(customer_po_release_seq.nextval)";
             }else {
              query += ",release_number = '"+(String)dHash.get("RELEASE_NUM"+type)+"'";
             }
            }
         }else {
           query += "po_number = null,release_number = null";
         }
         query += ",release_date = sysdate where release_date is null"+
                  " and pr_number = "+prNum+ " and line_item = "+(String)dHash.get("LINE_NUM");
         db.doUpdate(query);
      }
      //updating billing_method for MR
      String query = "update request_line_item rli set billing_method=(select billing_method from catalog_part_inventory cpi"+
                     " where cpi.catalog_id=rli.catalog_id and cpi.catalog_company_id = rli.catalog_company_id and cpi.cat_part_no=rli.fac_part_no and cpi.part_group_no=rli.part_group_no and"+
                     " cpi.inventory_group=rli.inventory_group) where rli.billing_method is null and rli.pr_number = "+prNum;
      db.doUpdate(query);

      //send mr notification to user_group
      sendMrNotification(prNum);
     }catch(Exception e){
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("Error while trying to releasing MR -"+(String)headerInfo.get("REQ_NUM"),e.getMessage());
     }
   } //end of method

   void sendMrWithGlobalWarmingPotentialNotification(int prNum) {
     try {
       String[] args = new String[4];
       args[0] = "GWP";
       args[1] = "GWPNotification";
       args[2] = getCompanyIdForMr(prNum);
       args[3] = (new Integer(prNum)).toString();
       db.doProcedure("p_mr_list_factor_notify",args);
     }catch (Exception e) {
       e.printStackTrace();
       radian.web.emailHelpObj.sendtrongemail("Error while trying to send MR Global Warming Potential notification -"+prNum,e.getMessage());
     }
   } //end of mthod

   String getCompanyIdForMr(int prNum) {
     String result = "";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select company_id from purchase_request where pr_number = "+prNum;
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
         result = rs.getString("company_id");
       }
     }catch (Exception e) {
       e.printStackTrace();
     }finally {
		  if (dbrs != null) {
          dbrs.close();
        }
	  }
     return result;
   } //end of method

   void sendMrNotification(int prNum) {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select nvl(p.email,'tngo@haastcm.com') email,rli.customer_requisition_number,rli.po_number,rli.release_number po_line,rli.quantity,"+
                      "rli.catalog_price ||' ' || rli.currency_id unit_price, fx_packaging(nvl(rli.item_id,rli.example_item_id)) packaging,"+
                      "to_char(rli.REQUIRED_DATETIME,'MM/dd/yyyy') need_date,rli.pr_number,rli.line_item"+
                      " from personnel p, request_line_item rli,purchase_request pr, pr_rules r,user_group_member ugm"+
                      " where rli.pr_number = "+prNum+" and rli.pr_number = pr.pr_number and pr.facility_id = r.facility_id"+
                      " and pr.account_sys_id = r.account_sys_id and rli.charge_type = r.charge_type and pr.facility_id = ugm.facility_id"+
                      " and r.MR_NOTIFICATION_USER_GROUP = ugm.user_group_id and ugm.personnel_id = p.personnel_id"+
                      " order by email,line_item";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        Hashtable msgH = new Hashtable(5);
        while(rs.next()) {
          String email = rs.getString("email");
          if (msgH.containsKey(email)) {
            String msg = (String)msgH.get(email);
            msg += "Customer Requistion  : "+BothHelpObjs.makeBlankFromNull(rs.getString("customer_requisition_number")) + "\n";
            msg += "Customer PO Number   : "+BothHelpObjs.makeBlankFromNull(rs.getString("po_number")) + "\n";
            msg += "Customer PO Line     : "+BothHelpObjs.makeBlankFromNull(rs.getString("po_line")) + "\n";
            msg += "Quantity             : "+BothHelpObjs.makeBlankFromNull(rs.getString("quantity")) + "\n";
            msg += "Unit Price           : "+BothHelpObjs.makeBlankFromNull(rs.getString("unit_price")) + "\n";
            msg += "Packaging            : "+BothHelpObjs.makeBlankFromNull(rs.getString("packaging")) + "\n";
            msg += "Need Date            : "+BothHelpObjs.makeBlankFromNull(rs.getString("need_date")) + "\n";
            msg += "Haas Material Request: "+BothHelpObjs.makeBlankFromNull(rs.getString("pr_number")) + "\n";
            msg += "Haas MR Line         : "+BothHelpObjs.makeBlankFromNull(rs.getString("line_item")) + "\n\n\n";
          }else {
            String msg = "";
            msg += "Customer Requistion  : "+BothHelpObjs.makeBlankFromNull(rs.getString("customer_requisition_number")) + "\n";
            msg += "PO Number            : "+BothHelpObjs.makeBlankFromNull(rs.getString("po_number")) + "\n";
            msg += "PO Line              : "+BothHelpObjs.makeBlankFromNull(rs.getString("po_line")) + "\n";
            msg += "Quantity             : "+BothHelpObjs.makeBlankFromNull(rs.getString("quantity")) + "\n";
            msg += "Unit Price           : "+BothHelpObjs.makeBlankFromNull(rs.getString("unit_price")) + "\n";
            msg += "Packaging            : "+BothHelpObjs.makeBlankFromNull(rs.getString("packaging")) + "\n";
            msg += "Need Date            : "+BothHelpObjs.makeBlankFromNull(rs.getString("need_date")) + "\n";
            msg += "Haas Material Request: "+BothHelpObjs.makeBlankFromNull(rs.getString("pr_number")) + "\n";
            msg += "Haas MR Line         : "+BothHelpObjs.makeBlankFromNull(rs.getString("line_item")) + "\n\n\n";
            msgH.put(email,msg);
          }
        }
        //sending out email
        if (msgH.size() > 0) {
          Enumeration enuma = msgH.keys();
          while (enuma.hasMoreElements()) {
            String email = enuma.nextElement().toString();
            String[] rec = {email};
            String msg = (String)msgH.get(email);
            HelpObjs.sendMail("Notification for Haas Material Request: " + prNum, msg, rec);
          }
        }
     }catch (Exception e) {
       e.printStackTrace();
       radian.web.emailHelpObj.sendtrongemail("Error while trying to send MR notification -"+prNum,e.getMessage());
     }finally{
       dbrs.close();
     }
   } //end of mthod

   //trong 8-28 try to make share code client independent
   public String getDefaultPo(String fId) throws Exception {
    String query = "select company_id from facility where facility_id = '"+fId+"'";
    String result = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
      while(rs.next()) {
        result = BothHelpObjs.makeBlankFromNull(rs.getString("company_id"));
      }
    }catch(Exception e){
      e.printStackTrace();
      throw(e);
    }finally{
      dbrs.close();
    }
    return (result.toUpperCase());
   }


   //trong 4/10
   public Vector getPrRules(Hashtable prInfo, Vector itemInfo) {
    String accSysId = (String)prInfo.get("ACC_SYS_ID");
    Hashtable pack = (Hashtable)prInfo.get("PACK");
    Vector result = new Vector();
    Boolean app = new Boolean(false);
    Boolean rel = new Boolean(false);
    Boolean poRequired = new Boolean (false);
    Boolean prAccRequired = new Boolean (false);
	 boolean checkedMrIsForCabinet = false;

	 for (int i = 0 ; i < itemInfo.size(); i++){
      Hashtable h = (Hashtable)itemInfo.elementAt(i);
      String type = (String)h.get("CHARGE_TYPE");
      String key = accSysId + type;
      Hashtable innerH = (Hashtable)pack.get(key);
      String approver = (String)innerH.get("APPROVER_REQUIRED");
      String releaser = (String)innerH.get("RELEASER_REQUIRED");
      String po = (String)innerH.get("PO_REQUIRED");
      String pra = (String)innerH.get("PR_ACCOUNT_REQUIRED");
      if (!approver.equalsIgnoreCase("n")) {
        app = new Boolean(true);
      }
      if (!releaser.equalsIgnoreCase("n")){
		  if (!checkedMrIsForCabinet) {
			  //if every lines in MR is for cabinet then releaser can be bi-pass
			  if (mrIsForCabinet(Integer.parseInt(prInfo.get("REQ_NUM").toString()))) {
				 rel = new Boolean(false);
			  }else {
				 rel = new Boolean(true);
			  }
			  checkedMrIsForCabinet = true;
		  }
		}
      if (!po.equalsIgnoreCase("n")){
        poRequired = new Boolean(true);
      }
      if (!pra.equalsIgnoreCase("n")){
        prAccRequired = new Boolean(true);
      }
    }
    result.addElement(app);
    result.addElement(rel);
    result.addElement(poRequired);
    result.addElement(prAccRequired);
    return result;
   }

   protected boolean mrIsForCabinet(int mrNum) {
     boolean result = false;
     try {
       String query = "select count(*) from (select facility_id,application from request_line_item where pr_number = "+mrNum+
                      " minus"+
                      " select facility_id,application from fac_loc_app where allow_stocking = 'Y' and (facility_id,application) in"+
                      " (select facility_id,application from request_line_item where pr_number = "+mrNum+"))";
        //If the above sql returns 0 then MR is for cabinet; otherwise, returns false
        if (HelpObjs.countQuery(db,query) == 0) {
          result = true;
        }
     }catch (Exception e) {
       e.printStackTrace();
     }
     return result;
   } //end of method

   protected String getNextStatus(PurchaseRequest pr,Hashtable prInfo,Vector itemInfo, String userId, String action){
     try{
       pr.load();

       Personnel p = new Personnel(db);
       p.setPersonnelId(Integer.parseInt(userId));
       p.setFacilityId(pr.getFacilityId());
       boolean needsApprover = true;
       Vector vv = getPrRules(prInfo,itemInfo);
       Boolean app = (Boolean)vv.elementAt(0);
       Boolean rel = (Boolean)vv.elementAt(1);
       if(action.equalsIgnoreCase("submit")){
         if(app.booleanValue()){           //----
           FinanceApproverList fal = new FinanceApproverList(db);
           needsApprover = !fal.canApprove(pr.getFacilityId(),this.getExtendedPrice(itemInfo),new Integer(userId));
           if(needsApprover){
             return "APPROVE";
           }else if(rel.booleanValue()){    //-----
             if (prInfo.containsKey("CALL_FROM_APPROVER_EDIT_MR")) {
               if ("FROM_RELEASER".equalsIgnoreCase((String)prInfo.get("CALL_FROM_APPROVER_EDIT_MR"))) {    //I am the releaser and I change data then it doesn't have to
                 return "POSUBMIT";                                                                        //stop at my queque, go ahead and proceed
               }else {
                 return "RELEASE";
               }
             }else {
               return "RELEASE";
             }
           }else{
             return "POSUBMIT";
           }
         }else if(rel.booleanValue()){      //----
           return "RELEASE";
         }else{
           return "POSUBMIT";
         }
       }else if(action.equals("APPROVE")){
         if(rel.booleanValue()){      //-----
           return "RELEASE";
         }else{
            if (pr.getPRStatus().equalsIgnoreCase("rejected")) {
              return "rejected";
            }else {
              return "POSUBMIT";
            }
         }
       }else if(action.equalsIgnoreCase("RELEASE")){
         return "POSUBMIT";
       }else if (action.equalsIgnoreCase("UPDATE")) {
         if ("posubmit".equalsIgnoreCase(pr.getPRStatus())) {
           return "POSUBMIT";
         }
       }
     }catch (Exception e){e.printStackTrace();}
       return "";
   }


  public String getShipTo(String facId)   throws Exception  {
      //Location
      Facility FLA = new Facility(db);
      FLA.setFacilityId(facId);
      FLA.load();

      Location loc = new Location(db);
      loc.setLocationId((String) FLA.getShippingLocation());
      loc.load();
      String tmpShip = new String(loc.getAddressLine1() + "\n");
      if (loc.getAddressLine2() != (String) null) {
         tmpShip = tmpShip + new String(loc.getAddressLine2() + "\n");
      }
      if (loc.getAddressLine3() != (String) null) {
         tmpShip = tmpShip + new String(loc.getAddressLine3() + "\n");
      }
      tmpShip = tmpShip + new String(loc.getCity() + ", " + loc.getStateAbbrev() + " " + loc.getZip());
      return tmpShip;
  }

  //If work area used directed PO then use that PO; otherwise, get POs from fac_account_sys_po
  // this returns a Vector of all the possible POs for a work area
  // the first element is the one that is selected
  Vector getPO(LineItemView rli, String facId, String accSys, Hashtable lineH) throws Exception {
    Vector v = new Vector();
    String pref = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
		//first try to get PO from directed charge
		String tmpCompanyId = "";
		String tmpChargeType = "";
		String tmpCatalogCompanyId = "";
		String tmpCatalogId = "";
		String tmpFacPartNo = "";
		String tmpPartGroupNo = "";
		if (!BothHelpObjs.isBlankString(rli.getCompanyId())) {
			tmpCompanyId = rli.getCompanyId();
		}
		if (!BothHelpObjs.isBlankString((String)lineH.get("CHARGE_TYPE"))) {
			tmpChargeType = (String)lineH.get("CHARGE_TYPE");
		}
		if (!BothHelpObjs.isBlankString(rli.getCatalogCompanyId())) {
			tmpCatalogCompanyId = rli.getCatalogCompanyId();
		}
		if (!BothHelpObjs.isBlankString(rli.getCatalogID())) {
			tmpCatalogId = rli.getCatalogID();
		}
		if (!BothHelpObjs.isBlankString(rli.getFacPartNo())) {
			tmpFacPartNo = rli.getFacPartNo();
		}
		if (!BothHelpObjs.isBlankString(rli.getPartGroupNo())) {
			tmpPartGroupNo = rli.getPartGroupNo();
		}
		StringBuilder query = new StringBuilder("select po_number from table (pkg_directed_charge_util.fx_get_directed_charges('").append(tmpCompanyId).append("','");
						query.append(facId).append("','").append(rli.getApplication()).append("','','");
						query.append(accSys);
						query.append("','").append(tmpChargeType).append("','Material','").append(tmpCatalogCompanyId).append("','").append(tmpCatalogId);
						query.append("','").append(tmpFacPartNo).append("','").append(tmpPartGroupNo).append("'))");
		dbrs = db.doQuery(query.toString());
	   rs = dbrs.getResultSet();
		String tmpPONumber = "";
		while (rs.next()) {
			tmpPONumber = BothHelpObjs.makeBlankFromNull(rs.getString("po_number"));
			if (tmpPONumber.length() > 0) {
				break;
			}
		}

		//if PO is catalog_part_directed_charge or directed_charge
      //then user can not chance
      if (tmpPONumber.length() > 0) {
        v.addElement(tmpPONumber);
        lineH.put("EDIT_PO",new Boolean(false));
      }else {
        //otherwise fac_account_sys_po
        String query2 = "select po_number, preferred from fac_account_sys_po "+
                "where status = 'A' and facility_id = '" + facId + "' and account_sys_id = '"+accSys+"'"+
                " order by po_number";
        dbrs = db.doQuery(query2);
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
        lineH.put("EDIT_PO",new Boolean(v.size()<1));    //allow users to edit PO if no PO in fac_account_sys_po
      }  //end of not directed PO
    } catch (Exception e) {
       throw e;
    } finally{
      dbrs.close();
    }
    String selected = rli.getPO();
    if (selected.length() > 0) {
      int index = v.indexOf(selected);
      if (index >= 0) {
        v.removeElementAt(index);
      }
      pref = selected;
    }else {
      int index = v.indexOf(pref);
      if (index >= 0) {
        v.removeElementAt(index);
      }
    }
    //put preferred PO in first entry
    if (pref.length() > 0) {
      v.insertElementAt(pref, 0);
    }else {
      if (v.size() > 1) {
        v.insertElementAt("Select a PO",0);
      }
    }
    return v;
  } //end of method

  // this returns a Vector of all the possible POs for a work area
  // the first element is the one that is selected
  Vector getPOOld(LineItemView rli, String facId, String accSys) throws Exception {
    Vector v = new Vector();

    String pref = new String("");
    String q1 = new String("select po_number, preferred from fac_account_sys_po ");
    // before q1 = q1 + "where facility_id = '" + facId + "' and (application = '" + rli.getApplication() + "' or application = 'All') ";
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

    String selected = rli.getPO();
    if (selected.length() > 0) {
      pref = selected;
    } else {
      pref = " ";
    }

    try {
       v.insertElementAt(pref,0);
    }catch(Exception e){
       v.insertElementAt(" ",0);
    }

    return v;
  }

  public int isAllowed(Vector groups,String facId,String app,String part,int qty) throws Exception {
    RequestLineItem rli = new RequestLineItem(db);
    int allowed = rli.getAllowedQty(groups,facId,app,part);
    if (allowed == 0 || (allowed > 0 && qty > allowed) ) {
      return 0;
    }
    return allowed;
  }

  public Hashtable checkByPass(Hashtable prInfo, Vector itemInfo) throws Exception {
    Hashtable outH = new Hashtable();
    String msg;
    String linePO;

    int rNum = Integer.parseInt(prInfo.get("REQ_NUM").toString());
    String facId = prInfo.get("FACILITY").toString();

    // groups for is allowed flag
    PurchaseRequest PR = new PurchaseRequest(db);
    PR.setPRNumber(rNum);
    PR.load();
    Integer user = PR.getRequestor();
    Personnel P = new Personnel(db);
    P.setPersonnelId(user.intValue());
    Vector groups = P.getUserGroups();
    Vector vv = getPrRules(prInfo,itemInfo);
    Boolean approver = (Boolean)vv.elementAt(0);
    Boolean relRequired = (Boolean)vv.elementAt(1);
    Boolean poRequired = (Boolean)vv.elementAt(1);
    for (int i = 0;i<itemInfo.size();i++){
      Hashtable h = (Hashtable)itemInfo.elementAt(i);
      /*DON'T NEED TO AUDIT THIS 9-19-03
      String dT = new String();
      dT = h.get("DEL_DATE").toString();
      //dates
      if (((String) BothHelpObjs.formatDate("toNumfromChar",dT)).length() == 0) {
        msg =  "Date " + dT + " is invalid. Please use the MM/DD/YYYY format.";
        outH.put("PASSED_AUDITS",new Boolean(false));
        outH.put("MSG",msg);
        return outH;
      }*/
      //qty
      try {
        Integer tI = new Integer(h.get("QTY").toString());
        if (tI.intValue() <= 0){
          msg =  "Please enter a valid quantity.";
          outH.put("PASSED_AUDITS",new Boolean(false));
          outH.put("MSG",msg);
          return outH;
        }
      } catch (Exception e){
        msg =  "Please enter a valid quantity.";
        outH.put("PASSED_AUDITS",new Boolean(false));
        outH.put("MSG",msg);
        return outH;
      }
    }

    boolean need = false;
    if(approver.booleanValue()){
      FinanceApproverList fal = new FinanceApproverList(db);
      need = !fal.canApprove(facId,this.getExtendedPrice(itemInfo),user);
    }

    P.setFacilityId(facId);
    if (!need) {
      if(!relRequired.booleanValue()){
        if(!poRequired.booleanValue()){
          outH.put("NEXT_STATUS","POSUBMIT");
        }else{
          if(!this.hasNeededPos(prInfo,itemInfo)){
            msg =  "You must enter a PO and reference number.";
            outH.put("PASSED_AUDITS",new Boolean(false));
            outH.put("MSG",msg);
            return outH;
          }
          outH.put("NEXT_STATUS","POSUBMIT");
        }
      }else{
        outH.put("NEXT_STATUS","RELEASE");
      }
    }else {
      outH.put("NEXT_STATUS","APPROVE");
    }
    outH.put("PASSED_AUDITS",new Boolean(true));
    return outH;
  }

  String trimHiphen(String str){
     if (str == null) return str;
     String res = new String(str);
     if (res.length()==0) return res;

     if (res.indexOf("-") == 0){
        res = new String(res.substring(1));
     } else if (res.lastIndexOf("-") == res.length()-1){
        res = new String(res.substring(0,res.length()-1));
     }
     return res;
  }

  public boolean hasNeededPos(Hashtable headerInfo,Vector itemInfo) throws Exception{

    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    Hashtable pack = (Hashtable)headerInfo.get("PACK");
    for(int i=0;i<itemInfo.size();i++){
      Hashtable h = (Hashtable)itemInfo.elementAt(i);

      String type = (String)h.get("CHARGE_TYPE");
      String key = accSysId + type;
      Hashtable innerH = (Hashtable)pack.get(key);
      String po = (String)innerH.get("PO_REQUIRED");
      if(!po.equalsIgnoreCase("n")){

        if(BothHelpObjs.isBlankString(h.get("PO"+type).toString())){
          return false;
        }
      }
    }
    return true;
  }
  public boolean hasNeededReleaseNum(Hashtable headerInfo,Vector itemInfo) throws Exception{

    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    Hashtable pack = (Hashtable)headerInfo.get("PACK");
    for(int i=0;i<itemInfo.size();i++){
      Hashtable h = (Hashtable)itemInfo.elementAt(i);
      String type = (String)h.get("CHARGE_TYPE");
      String key = accSysId + type;
      Hashtable innerH = (Hashtable)pack.get(key);
      String po = (String)innerH.get("PO_REQUIRED");
      if(!po.equalsIgnoreCase("n")){
        if (HelpObjs.countQuery(db,"select count(*) from pr_rules where account_sys_id = '"+accSysId+"' and facility_id = '"+(String)headerInfo.get("FACILITY")+
                                "' and charge_type = '"+type+"' and po_seq_required = 'U'") > 0) {
          if(BothHelpObjs.isBlankString(h.get("PO"+type).toString()) ||
             BothHelpObjs.isBlankString(h.get("RELEASE_NUM"+type).toString())){
            return false;
          }
        }
      }
    }
    return true;
  }
  public boolean needsReleaser(Hashtable headerInfo,Vector itemInfo) throws Exception{

    Vector vv = getPrRules(headerInfo,itemInfo);
    Boolean releaser = (Boolean)vv.elementAt(1);
    boolean result = false;
    if(releaser.booleanValue()) {
      result = true;
    }
    return result;
  }

  boolean hasThisChargeNum(Vector ofHash, ChargeNumber cn){
    for(int i=0;i<ofHash.size();i++){
      Hashtable h = (Hashtable)ofHash.elementAt(i);
      //String t = h.get("CHARGE_TYPE").toString();
      //if(!cn.getChargeType().equalsIgnoreCase(t))continue;
      String a1 = h.get("ACCT_NUM_1").toString();

      if(!cn.getAccountNumber().equalsIgnoreCase(a1))continue;
      String a2 = h.get("ACCT_NUM_2").toString();

      if(BothHelpObjs.isBlankString(cn.getAccountNumber2()) &&
         BothHelpObjs.isBlankString(a2)) return true;
      if(cn.getAccountNumber2().equalsIgnoreCase(a2))return true;
    }
    return false;
  }

  public void sendRejectMsgToUser(int reqNum, Vector partNo) throws Exception{
    //UseApprover ua = new UseApprover(db);
    String subject = "Your Material Request #"+reqNum+" was rejected.";
    String msg = "Your Material Request #"+reqNum;
    if (partNo.size() > 0) {
      msg += "\n  part no.: ";
      for (int i = 0; i < partNo.size(); i++) {
        msg += partNo.elementAt(i).toString()+",";
      }
      //removing the last commas
      msg = msg.substring(0,msg.length()-1);
    }
    msg += " was rejected";
    PurchaseRequest PR = new PurchaseRequest(db);
    PR.setPRNumber(reqNum);
    PR.load();
    int userID = 86030;
    if (PR.getRequestor().intValue() < 1) {
      userID = 86030;
      msg += "\nThere is no requestor for this purchase request.";
    }else {
      userID = PR.getRequestor().intValue();
    }
    HelpObjs.sendMail(db,subject,msg,userID,false);
  }

  void sendMsgToUseApprovers(int reqNum) throws Exception{
    UseApprover ua = new UseApprover(db);
    String msg = "Material Request #"+reqNum+" is waiting for your use approval.";
    String subject = new String(msg);
    String[] rec;
    Vector approvers = ua.getUseApproverInfo(reqNum);
    if (approvers.size() > 0) {
      rec = new String[approvers.size()];
		for (int i = 0 ; i < approvers.size(); i++) {
			Hashtable h = (Hashtable)approvers.elementAt(i);
			rec[i] = (String)h.get("EMAIL");
		}
	 	msg += "\n\nhttps://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase()+"/Register\n";
    	HelpObjs.sendMail(subject,msg,rec);
	 }
	 //send email to list approvers
	 ListApprover listApprover = new ListApprover(db);
	 listApprover.sendMsgToListApprovers(reqNum);
  } //end of method

  void sendMsgToRequestorNAdmin(int reqNum, Integer requestorID, String facilityID) {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select personnel_id,nvl(email,' ') email,last_name||', '||first_name full_name,nvl(phone,' ') phone"+
                     " from personnel where personnel_id in"+
                     " ("+
                     "select personnel_id from user_group_member where facility_id = '"+facilityID+"' and user_group_id = 'Administrator'"+
                     " union select "+requestorID.intValue()+" from dual"+
                     ")";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String admin = "";
      String[] receiver = new String[1];
      Vector cc = new Vector(41);
      String msg = "";
      while (rs.next()){
        String ID = rs.getString("personnel_id");
        String fullName = rs.getString("full_name");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        //check if requestor
        if (requestorID.toString().equals(ID)) {
          receiver[0] = ID;

        }
      }

    }catch (Exception e){
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("Material request status is compchk","MR - "+reqNum+" failed while trying to notify admin that requestor is not in approval tree.");
    }finally {
      dbrs.close();
    }
  } //end of method

  void sendMsgToApprovers(int reqNum) throws Exception{
    PurchaseRequest PR = new PurchaseRequest(db);
    PR.setPRNumber(reqNum);
    PR.load();
    //check to make sure that requestor is in approval tree
    if("0".equalsIgnoreCase(PR.getFinancialApprover().toString())) {
       //notify user and admins that user is no in approval tree
       //sendMsgToRequestorNAdmin(reqNum,PR.getRequestor(),PR.getFacilityId());
       radian.web.emailHelpObj.sendtrongemail("Material request status is compchk","MR - "+reqNum+" requestor is not in approval tree.");
       return;
    }

    FinanceApproverList fal = new FinanceApproverList(db);
    Hashtable approverH = fal.getAllApprovers(getExtendedPriceForPR(PR.getPRNumber().intValue()),PR);
    Integer approver = (Integer)approverH.get("APPROVER");
    if(approver.intValue() == PR.getRequestor().intValue() ||
       approver.intValue() == 0){
      if (PR.getRequestor().intValue() == -1) {
        radian.web.emailHelpObj.sendtrongemail("Material request status is compchk","MR - "+reqNum+" approver: "+approver.intValue());
      }
      return;
    }

    String msg = "Material Request #"+reqNum+" is waiting for your approval.";
    String subject = new String(msg);
    String[] receiver = new String[1];
    //additional approver(s)
    Vector otherApproversV = (Vector) approverH.get("OTHER_APPROVERS");
    Vector alternateApproversV = (Vector) approverH.get("ALTERNATE_APPROVERS");
    String[] cc = new String[0];
    //first get approver email address
    Hashtable approverDetail = fal.getApproversInfo(approver,new Vector(0));
    if (((Boolean)approverDetail.get("OKAY")).booleanValue()) {
      Vector tmpAppV = (Vector)approverDetail.get("APPROVER_EMAIL");
      int i = 0;
      receiver = new String[tmpAppV.size()];
      for (i = 0; i < tmpAppV.size(); i++) {
        receiver[i] = (String)tmpAppV.elementAt(i);
      }
      //get name and email addresses for alternate approvers
      Hashtable altApproverDetail = fal.getApproversInfo(new Integer(0),alternateApproversV);
      Vector tmpAltEmailV = new Vector(23);
      if (((Boolean)altApproverDetail.get("OKAY")).booleanValue()) {
        Vector tmpAppNameV = (Vector)altApproverDetail.get("APPROVER_NAME");
        msg += "\n\n     Alternate approver(s): \n";
        for (i = 0; i < tmpAppNameV.size(); i++) {
          msg += "          "+(String)tmpAppNameV.elementAt(i)+"\n";
        }
        tmpAltEmailV = (Vector)altApproverDetail.get("APPROVER_EMAIL");
      }
      //next get other approvers name and email addresses
      Hashtable otherApproverDetail = fal.getApproversInfo(new Integer(0),otherApproversV);
      Vector tmpOtherEmailV = new Vector(23);
      if (((Boolean)otherApproverDetail.get("OKAY")).booleanValue()) {
        Vector tmpAppNameV = (Vector)otherApproverDetail.get("APPROVER_NAME");
        msg += "\n\n     F.Y.I. approver(s): (No Action Required)\n";
        for (i = 0; i < tmpAppNameV.size(); i++) {
          msg += "          "+(String)tmpAppNameV.elementAt(i)+"\n";
        }
        tmpOtherEmailV = (Vector)otherApproverDetail.get("APPROVER_EMAIL");
      }
      //add alternate approvers and other approvers to list should exist
      if (tmpAltEmailV.size() > 0 || tmpOtherEmailV.size() > 0) {
        cc = new String[tmpAltEmailV.size()+tmpOtherEmailV.size()];
        //add alternate approvers to cc list
        int count = 0;
        for (i = 0; i < tmpAltEmailV.size(); i++) {
          cc[count++] = (String)tmpAltEmailV.elementAt(i);
        }
        //next add other approvers to cc list
        for (i = 0; i < tmpOtherEmailV.size(); i++) {
          cc[count++] = (String)tmpOtherEmailV.elementAt(i);
        }
      }
    }
    msg += "\n\nhttps://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase()+"/Register\n";
    HelpObjs.javaSendMail(subject,msg,receiver,cc);
  } //end of method

  void sendMsgToReleasers(int reqNum,String userId) throws Exception{
    PurchaseRequest PR = new PurchaseRequest(db);
    PR.setPRNumber(reqNum);
    PR.load();
    Personnel P = new Personnel(db);
    P.setPersonnelId((new Integer(userId)).intValue());
    P.setFacilityId(PR.getFacilityId());
    String userName = P.getUserName(db,userId);

    // if the person sending it to the release stage
    // is a releaser, don't send a message
    // this could be the approver or the requestor
    if(P.isReleaser()){
      if (PR.getRequestor().intValue() == -1) {
        radian.web.emailHelpObj.sendtrongemail("Material request status is approved","MR - "+reqNum);
      }
      return;
    }

    Releaser r = new Releaser(db);
    r.setFacilityId(PR.getFacilityId());
    // vector of Personnel objects
    Vector v = r.getAllReleasers();
    // if the requestor is a releaser, only send a message to
    // the requestor. Otherwise, send a message to all releasers
    Vector v1 = new Vector();
    for(int i=0;i<v.size();i++){
      Personnel p = (Personnel)v.elementAt(i);
      if(p.getPersonnelId().intValue() == PR.getRequestor().intValue()){
        v1.addElement(p);
        break;
      }
    }
    if(v1.size() == 0){
      v1 = v;
    }

    String msg = "Material Request #"+reqNum+" ("+PR.getFacilityId()+" - "+userName+") is waiting to be released.";
    String subject = new String(msg);
    msg += "\n\nhttps://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase()+"/Register\n";
    for(int i=0;i<v1.size();i++){
      Personnel p = (Personnel)v1.elementAt(i);
      HelpObjs.sendMail(db,subject,msg,p.getPersonnelId().intValue(),false);
    }
  }

  double getExtendedPrice(Vector lineItems){
    double ePrice = 0.0;
    for(int i=0;i<lineItems.size();i++){
      Hashtable h = (Hashtable)lineItems.elementAt(i);

      //I am not calculating the rejected line item into the extended price
      if (h.containsKey("USE_APPROVAL_STATUS")) {
        String useAppStat = (String)h.get("USE_APPROVAL_STATUS");
        if (useAppStat.equalsIgnoreCase("rejected")) continue;
      }

      double p = 0.0;
      int q = 0;
      try{
        Double D = new Double(h.get("UNIT_PRICE").toString());
        p = D.doubleValue();
        Integer I = new Integer(h.get("QTY").toString());
        q = I.intValue();
        ePrice = ePrice + (p*q);
      }catch(Exception e){}
    }
    return ePrice;
  }
  double getExtendedPriceForPR(int reqNum)throws Exception{
     RequestLineItem rli = new RequestLineItem(db);
     rli.setPRNumber(reqNum);
     int n = rli.getNumLines(reqNum);
     double ePrice = 0.00;
     PurchaseRequest pr = new PurchaseRequest(db);
     pr.setPRNumber(reqNum);
     pr.load();
     Catalog c = new Catalog(db);
     c.setFacilityId(pr.getFacilityId());
     for(int i=0;i<n;i++){
       rli.setLineItem((new Integer(i+1)).toString());
       rli.load();

       if (rli.getUseApprovaltatus().equalsIgnoreCase("rejected")) continue;

       try{
         c.setItemId(rli.getItemId().intValue());
         c.setFacItemId(rli.getFacPartNo());          //trong 2-7-01
         c.loadNew(pr.getPRNumber());
         double p = (new Double(c.getPrice())).doubleValue();
         int q = rli.getQuantity().intValue();
         //get request
         ePrice = ePrice + (p*q);
       }catch(Exception e){}
     }
    return ePrice;
  }

  public void requestCancel(String reqNum, String lineNum, String user, String dbClient,String cancelnotes)
        throws Exception
    {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            cancelnotes=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( cancelnotes );
            String query = " update request_line_item set CANCEL_COMMENT=decode(cancel_comment,null,'',cancel_comment||'; ')||'"+cancelnotes+"', cancel_request=sysdate  , cancel_requester=" + user + " , cancel_status='rqcancel'" + " , request_line_status='Pending Cancellation'" + " where pr_number =" + reqNum + " and line_item='" + lineNum + "'";
            db.doUpdate(query);
            query = "select p.last_name||', '||p.first_name  full_name,p.phone from personnel p where p.personnel_id = " + user;
            dbrs = db.doQuery(query);
            rs = dbrs.getResultSet();
            String fullName = "";
            String phone;
            for(phone = ""; rs.next(); phone = BothHelpObjs.makeBlankFromNull(rs.getString("phone")))
                fullName = BothHelpObjs.makeBlankFromNull(rs.getString("full_name"));

            String m = "MR " + reqNum + " line " + lineNum + "\n\n";
            m = m + fullName + "(" + phone + ") has requested that Material Request " + reqNum + ", line " + lineNum + " be canceled.\n";
            //m = m + "Please review this order at:" + url + "\n\n";
            //m = m + "If this order may canceled please select \"Approve\". ";
            m = m + "If this order cannot be canceled without incurring additional costs, ";
            m = m + "Please contact the requestor," + fullName + ", and discuss possible options.\n";
            m = m + "\t**** Please do not reject the cancellation request without speaking ";
            m = m + "to the requester first.\n\n";
            /* I am blocking this out since it takes forever to return
            query = "select a.item_id,a.radian_po,a.branch_plant,a.pr_number,a.qty_open,b.last_name||', '||b.first_name full_name"+
                    " from mr_buy_order_po_view a, personnel b where mr_number = " + reqNum + " and mr_line_item = '" + lineNum + "' and a.buyer_id = b.personnel_id(+)";
            dbrs = db.doQuery(query);
            for(rs = dbrs.getResultSet(); rs.next();)
            {
                m = m + ("Item ID:      " + BothHelpObjs.makeBlankFromNull(rs.getString("item_id")) + "\n");
                m = m + ("Radian PO:    " + BothHelpObjs.makeBlankFromNull(rs.getString("radian_po")) + "\n");
                m = m + ("Branch Plant: " + BothHelpObjs.makeBlankFromNull(rs.getString("branch_plant")) + "\n");
                m = m + ("PR Number:    " + BothHelpObjs.makeBlankFromNull(rs.getString("pr_number")) + "\n");
                m = m + ("Qty Open:     " + BothHelpObjs.makeBlankFromNull(rs.getString("qty_open")) + "\n");
                m = m + ("Buyer   :     " + BothHelpObjs.makeBlankFromNull(rs.getString("full_name")) + "\n");
            }*/
            String s = "Material Request " + reqNum + " line " + lineNum + " requests cancellation.";
            if (HelpObjs.countQuery(db,"select count(*) from request_line_item where cancel_status = 'rqcancel' and pr_number = "+reqNum+" and line_item = '"+lineNum+"'") > 0) {
              radian.web.emailHelpObj.sendsteveemail(s, m);
            }
        }catch(Exception e){
            e.printStackTrace();
            radian.web.emailHelpObj.sendtrongemail("Error occured while trying to request cancel", "pr_number: " + reqNum + " line_item: " + lineNum + " requestor: " + user);
            throw e;
        }
        finally
        {
            dbrs.close();
        }
    }

  //trong 3/22
  public String buildNewChemFromEngEvalRequest(Hashtable inData){
     String request_id = (String)inData.get("REQ_ID");
     Enumeration E;
     Hashtable matHash = (Hashtable) inData.get("MAT_DATA");
     Hashtable partHash = (Hashtable) inData.get("PART_DATA");
     Hashtable apprHash = (Hashtable) inData.get("APPR_DATA");

     //getting facility & workArea from apprHash
     Hashtable allData = (Hashtable) apprHash.get("ALL_DATA");
     String facilityKey = null;
     for (E = allData.keys(); E.hasMoreElements();){
      facilityKey = (String) E.nextElement();
     }
     Hashtable facInfo = (Hashtable) allData.get(facilityKey);

     //getting part data from partHash
     Hashtable partData = (Hashtable) partHash.get("PART_DATA");

     String msg = new String("");
     String[] returnValue = new String[2];
     int nx = 0;
     try {
      // create new item
      // pass desc1, desc2, client, eval="Y", test="Y" or "N"

      //trong 4/3
      Integer myR = new Integer(request_id);
      CatalogAddRequestNew catR = new CatalogAddRequestNew(db);
      catR.setRequestId(myR.intValue());
      catR.load();


      Facility FAC = new Facility(db);
      FAC.setFacilityId(catR.getEngEvalFacilityId());
      FAC.load();
      // Client
      String branch = FAC.getPreferredWare();
      String client="";
      if (branch.trim().equalsIgnoreCase("salem")) client="NE";
      if (branch.trim().equalsIgnoreCase("tucson")) client="Tuc";
      if (branch.trim().equalsIgnoreCase("dallas")) client="Dal";
      if (branch.trim().equalsIgnoreCase("gd"))     client="GD";

      // add item_id and part_number into matHash
      CatalogAddRequestNew car = new CatalogAddRequestNew(db);
      Integer myReq = new Integer(request_id);
      car.setRequestId(myReq.intValue());
      car.load();
      Integer myItem_id = car.getItemId();

      matHash.put("ITEM_ID",myItem_id.toString());
      matHash.put("PART_NUMBER",car.getCatPartNum());

      //5-07-01  create MR when needed
      if (this.createMR()) {
        // create a MR
        PurchaseRequest PRI = new PurchaseRequest(db);
        nx = PRI.getNext();
        if ( nx == 0) {
          String error = "No PR Produced";
          return error;
        }

        setPrNumber(nx);
        matHash.put("PR_NUMBER",String.valueOf(nx));

        PRI.setPRNumber(nx);
        PRI.setRequestor(Integer.parseInt((String)inData.get("USER_ID")));
        PRI.setFacilityId(catR.getEngEvalFacilityId());
        PRI.setShipTo(BothHelpObjs.makeBlankFromNull(FAC.getShippingLocation()));
        PRI.setPRStatus("entered");
        PRI.setRequestDate(new String("nowDate")); // sysdate update
        PRI.setPONumber("");
        PRI.setForwardTo("");
        PRI.setAccSysId(car.getAccSysId());
        PRI.insert();
        PRI.insert("ENGINEERING_EVALUATION",new String("Y"),PRI.STRING);
        PRI.insert("request_id",request_id,PRI.STRING);
        PRI.commit();

        RequestLineItem RLI = new RequestLineItem(db);
        RLI.setPRNumber(nx);
        RLI.setLineItem("1");
        RLI.setItemId(Integer.parseInt((String)matHash.get("ITEM_ID")));
        RLI.setApplication(catR.getEngEvalWorkArea());
        RLI.setQuantity(new BigDecimal((String)partData.get("initQty"))); //quantity
        RLI.setFacPartNo((String)matHash.get("PART_NUMBER"));
        RLI.setItemType((String)partData.get("OORMM"));    //"item_type"
        RLI.insertEngEval();
        RLI.commit();
      }

     } catch (Exception e){
      e.printStackTrace();
      return "Error creating material request. Error: " + e.getMessage();
    }
    return null;
  }

  //5-06-01
  public boolean createMR() throws Exception {
    String query = "select feature_mode from tcmis_feature where lower(feature) = lower('createMR')";
    String mode = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        mode = BothHelpObjs.makeBlankFromNull(rs.getString("feature_mode"));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
     return ("1".equalsIgnoreCase(mode));
  }

  //6-28-01 return pr_number if purchase request exist for a given eval, otherwise return 0
  public String getMaterialRequestForEvalRequest(String reqId) throws Exception {
    String result = "0";
    String query = "select pr_number from purchase_request where request_id = "+reqId;
    DBResultSet dbrs = null;
    ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        result = rs.getString("pr_number");
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}

     return result;
  }

  public int getPrNumber() {
    return PrNumber;
  }
  public void setPrNumber(int D) {
    this.PrNumber = D;
  }

 public Hashtable getAllEvalInfo(){
     return evalInfo;
  }

}
