package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.util.*;
import java.sql.ResultSet;
import java.math.BigDecimal;

import radian.tcmis.server7.share.db.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;


public class InvoiceData {
   protected TcmISDBModel db;
   public InvoiceData(TcmISDBModel  db){
     this.db = db;
   }

   public InvoiceData() {

   }

  public void openTemplate(Hashtable userSelectedData, String userID, String templateName) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = "select facility_id,application,account_sys_id,charge_type,charge_number_1,charge_number_2,search_by,search_text,invoice,source_hub,item_type,"+
                   "invoice_period,to_char(start_date,'mm/dd/yyyy') start_date,to_char(end_date,'mm/dd/yyyy') end_date,commodity,date_delivered_group_by,report_field,"+
                   "to_char(invoice_start_date,'mm/dd/yyyy') invoice_start_date,to_char(invoice_end_date,'mm/dd/yyyy') invoice_end_date,nvl(requestor,'0') requestor,requestor_name"+
                   " from cost_report_template where personnel_id = "+userID+" and report_name = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(templateName)+"'";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int count = 0;
      //first set template name
      userSelectedData.put("TEMPLATE_NAME",templateName);
      String reportField = "";
      while (rs.next()) {
        userSelectedData.put("REQUESTOR_ID",rs.getString("requestor"));
        userSelectedData.put("REQUESTOR_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("requestor_name")));
        userSelectedData.put("HUB_C",BothHelpObjs.makeBlankFromNull(rs.getString("source_hub")));
        userSelectedData.put("FACILITY",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
        userSelectedData.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(rs.getString("application")));
        userSelectedData.put("ACCOUNT_SYS",BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
        if ("d".equalsIgnoreCase(rs.getString("charge_type"))) {
          userSelectedData.put("DIRECT","Yes");
          userSelectedData.put("INDIRECT","");
        }else {
          userSelectedData.put("DIRECT","");
          userSelectedData.put("INDIRECT","Yes");
        }
        userSelectedData.put("CHARGE_NUMBER_1",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
        userSelectedData.put("CHARGE_NUMBER_2",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
        userSelectedData.put("SEARCH_BY",BothHelpObjs.makeBlankFromNull(rs.getString("search_by")));
        userSelectedData.put("SEARCH_TEXT",BothHelpObjs.makeBlankFromNull(rs.getString("search_text")));
        userSelectedData.put("INVOICE_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("invoice")));
        userSelectedData.put("INVOICE_PERIOD_C",BothHelpObjs.makeBlankFromNull(rs.getString("invoice_period")));
        userSelectedData.put("INVOICE_START_DATE",BothHelpObjs.makeBlankFromNull(rs.getString("invoice_start_date")));
        userSelectedData.put("INVOICE_END_DATE",BothHelpObjs.makeBlankFromNull(rs.getString("invoice_end_date")));
        userSelectedData.put("START_DATE",BothHelpObjs.makeBlankFromNull(rs.getString("start_date")));
        userSelectedData.put("END_DATE",BothHelpObjs.makeBlankFromNull(rs.getString("end_date")));
        //userSelectedData.put("MATERIAL_CATEGORY_C",BothHelpObjs.makeBlankFromNull(rs.getString("category_id")));
        Vector invoiceTypeV = new Vector();
        String tmpInvoiceType = BothHelpObjs.makeBlankFromNull(rs.getString("commodity"));
        StringTokenizer stz = new StringTokenizer(tmpInvoiceType,";");
        while (stz.hasMoreElements()) {
          invoiceTypeV.addElement((String)stz.nextElement());
        }
        userSelectedData.put("COMMODITY_C",invoiceTypeV);
        //item type
        Vector itemTypeV = new Vector();
        String tmpItemType = BothHelpObjs.makeBlankFromNull(rs.getString("item_type"));
        stz = new StringTokenizer(tmpItemType,";");
        while (stz.hasMoreElements()) {
          itemTypeV.addElement((String)stz.nextElement());
        }
        userSelectedData.put("ITEM_TYPE_C",itemTypeV);
        String tmp = rs.getString("date_delivered_group_by");
        if ("Day".equalsIgnoreCase(tmp)) {
          userSelectedData.put("DATE_DELIVERED_GROUP_BY_D","Yes");
          userSelectedData.put("DATE_DELIVERED_GROUP_BY_M","");
          userSelectedData.put("DATE_DELIVERED_GROUP_BY_Y","");
        }else if ("Month".equalsIgnoreCase(tmp)) {
          userSelectedData.put("DATE_DELIVERED_GROUP_BY_D","");
          userSelectedData.put("DATE_DELIVERED_GROUP_BY_M","Yes");
          userSelectedData.put("DATE_DELIVERED_GROUP_BY_Y","");
        }else {
          userSelectedData.put("DATE_DELIVERED_GROUP_BY_D","");
          userSelectedData.put("DATE_DELIVERED_GROUP_BY_M","");
          userSelectedData.put("DATE_DELIVERED_GROUP_BY_Y","Yes");
        }
        reportField = BothHelpObjs.makeBlankFromNull(rs.getString("report_field"));
      }
      //now parse report fields
      if (reportField.length() > 0) {
        StringTokenizer st = new StringTokenizer(reportField,";");
        while (st.hasMoreTokens()) {
          userSelectedData.put(st.nextElement(),"Yes");
          count++;
        }
        //finally update report field count
        userSelectedData.put("REPORT_FIELD_COUNT",(new Integer(count)).toString());
      }
    }catch (Exception e) {
      throw e;
    }finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }
  }

  public void saveTemplate(Hashtable userSelectedData, String userID) throws Exception {
    try {
      String reportName = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)userSelectedData.get("TEMPLATE_NAME"));
      //first delete template
      String query = "delete from cost_report_template where personnel_id = "+userID+" and report_name = '"+reportName+"'";
      db.doUpdate(query);
      //next parse user selected data and save
      String chargeType = "d";
      if ("Yes".equalsIgnoreCase((String)userSelectedData.get("DIRECT"))) {
        chargeType = "d";
      }else {
        chargeType = "i";
      }
      query = "insert into cost_report_template (personnel_id,report_name,";
      String value = " values("+userID+",'"+reportName+"',";

      String tmp = BothHelpObjs.makeBlankFromNull((String)userSelectedData.get("REQUESTOR_NAME"));
      if (tmp.length() > 0) {
        query += "requestor_name,requestor,";
        value += "'"+tmp+"',"+(String)userSelectedData.get("REQUESTOR_ID")+",";
      }
      tmp = BothHelpObjs.makeBlankFromNull((String)userSelectedData.get("HUB_C"));
      if (tmp.length() > 0 && !"All Reporting Groups".equalsIgnoreCase(tmp)) {
        query += "source_hub,";
        value += "'"+tmp+"',";
      }
      tmp = BothHelpObjs.makeBlankFromNull((String)userSelectedData.get("FACILITY"));
      if (tmp.length() > 0 && !"All Facilities".equalsIgnoreCase(tmp)) {
        query += "facility_id,";
        value += "'"+tmp+"',";
      }
      tmp = (String)userSelectedData.get("WORK_AREA");
      if (tmp.length() > 0 && !"All Work Areas".equalsIgnoreCase(tmp) && !"None".equalsIgnoreCase(tmp)) {
        query += "application,";
        value += "'"+tmp+"',";
      }
      tmp = (String)userSelectedData.get("ACCOUNT_SYS");
      if (tmp.length() > 0 && !"All Accounting Systems".equalsIgnoreCase(tmp) && !"None".equalsIgnoreCase(tmp)) {
        query += "account_sys_id,";
        value += "'"+tmp+"',";
      }
      tmp = (String)userSelectedData.get("SEARCH_BY");
      if (tmp.length() > 0 && !"All".equalsIgnoreCase(tmp) && !"None".equalsIgnoreCase(tmp)) {
        query += "search_by,";
        value += "'"+tmp+"',";
      }
      tmp = (String)userSelectedData.get("INVOICE_PERIOD_C");
      if (tmp.length() > 0 && !"All".equalsIgnoreCase(tmp) && !"None".equalsIgnoreCase(tmp)) {
        query += "invoice_period,";
        value += tmp+",";
      }
      /*
      tmp = (String)userSelectedData.get("MATERIAL_CATEGORY_C");
      if (tmp.length() > 0 && !"All".equalsIgnoreCase(tmp) && !"None".equalsIgnoreCase(tmp)) {
        query += "category_id,";
        value += tmp+",";
      }*/
      Vector tmpV = (Vector)userSelectedData.get("COMMODITY_C");
      tmp = "";
      int jj = 0;
      for (jj = 0; jj < tmpV.size(); jj++) {
        tmp += (String)tmpV.elementAt(jj)+";";
      }
      //removing the last commas ','
      if (tmp.length() > 0 ) {
        tmp = tmp.substring(0,tmp.length()-1);
      }
      if (tmp.length() > 0) {
        query += "commodity,";
        value += "'"+tmp+"',";
      }
      tmpV = null;
      //item type
      tmpV = (Vector)userSelectedData.get("ITEM_TYPE_C");
      tmp = "";
      for (jj = 0; jj < tmpV.size(); jj++) {
        tmp += (String)tmpV.elementAt(jj)+";";
      }
      //removing the last commas ','
      if (tmp.length() > 0 ) {
        tmp = tmp.substring(0,tmp.length()-1);
      }
      if (tmp.length() > 0 ) {
        query += "item_type,";
        value += "'"+tmp+"',";
      }
      tmp = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)userSelectedData.get("CHARGE_NUMBER_1"));
      if (tmp.length() > 0) {
        query += "charge_number_1,";
        value += "'"+tmp+"',";
      }
      tmp = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)userSelectedData.get("CHARGE_NUMBER_2"));
      if (tmp.length() > 0) {
        query += "charge_number_2,";
        value += "'"+tmp+"',";
      }
      tmp = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)userSelectedData.get("SEARCH_TEXT"));
      if (tmp.length() > 0) {
        query += "search_text,";
        value += "'"+tmp+"',";
      }
      tmp = (String)userSelectedData.get("INVOICE_NUMBER");
      if (tmp.length() > 0) {
        query += "invoice,";
        value += tmp+",";
      }
      tmp = (String)userSelectedData.get("INVOICE_START_DATE");
      if (tmp.length() > 1) {
        query += "invoice_start_date,";
        value += "to_date('"+tmp+"','mm/dd/yyyy'),";
      }
      tmp = (String)userSelectedData.get("INVOICE_END_DATE");
      if (tmp.length() > 1) {
        query += "invoice_end_date,";
        value += "to_date('"+tmp+"','mm/dd/yyyy'),";
      }
      tmp = (String)userSelectedData.get("START_DATE");
      if (tmp.length() > 1) {
        query += "start_date,";
        value += "to_date('"+tmp+"','mm/dd/yyyy'),";
      }
      tmp = (String)userSelectedData.get("END_DATE");
      if (tmp.length() > 1) {
        query += "end_date,";
        value += "to_date('"+tmp+"','mm/dd/yyyy'),";
      }
      //date delivered group by
      if ("Yes".equalsIgnoreCase((String)userSelectedData.get("DATE_DELIVERED_GROUP_BY_D"))) {
        query += "date_delivered_group_by,";
        value += "'Day',";
      }else if ("Yes".equalsIgnoreCase((String)userSelectedData.get("DATE_DELIVERED_GROUP_BY_M"))) {
        query += "date_delivered_group_by,";
        value += "'Month',";
      }else {
        query += "date_delivered_group_by,";
        value += "'Year',";
      }

      //REPORT FIELDS
      String reportField = "";
      tmp = (String)userSelectedData.get("INVOICE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "INVOICE;";
      }
      tmp = (String)userSelectedData.get("INVOICE_TYPE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "INVOICE_TYPE;";
      }
      tmp = (String)userSelectedData.get("INVOICE_DATE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "INVOICE_DATE;";
      }
      tmp = (String)userSelectedData.get("INVOICE_PERIOD");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "INVOICE_PERIOD;";
      }
      tmp = (String)userSelectedData.get("INVOICE_LINE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "INVOICE_LINE;";
      }
      tmp = (String)userSelectedData.get("ACCOUNTING_SYSTEM");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "ACCOUNTING_SYSTEM;";
      }
      tmp = (String)userSelectedData.get("CHARGE_NUM_1");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "CHARGE_NUM_1;";
      }
      tmp = (String)userSelectedData.get("CHARGE_NUM_2");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "CHARGE_NUM_2;";
      }
      tmp = (String)userSelectedData.get("PO");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "PO;";
      }
      tmp = (String)userSelectedData.get("SPLIT_CHARGE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "SPLIT_CHARGE;";
      }
      tmp = (String)userSelectedData.get("HUB");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "HUB;";
      }
      tmp = (String)userSelectedData.get("FAC");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "FAC;";
      }
      tmp = (String)userSelectedData.get("WA");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "WA;";
      }
      tmp = (String)userSelectedData.get("REQUESTOR");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "REQUESTOR;";
      }
      tmp = (String)userSelectedData.get("FINANCE_APPROVER");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "FINANCE_APPROVER;";
      }
      tmp = (String)userSelectedData.get("MR_LINE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "MR_LINE;";
      }
      tmp = (String)userSelectedData.get("WASTE_ORDER_NO");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "WASTE_ORDER_NO;";
      }
      tmp = (String)userSelectedData.get("WASTE_MANIFEST_ID");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "WASTE_MANIFEST_ID;";
      }
      tmp = (String)userSelectedData.get("PART_NUMBER");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "PART_NUMBER;";
      }
      tmp = (String)userSelectedData.get("PART_DESC");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "PART_DESC;";
      }
      tmp = (String)userSelectedData.get("ITEM");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "ITEM;";
      }
      tmp = (String)userSelectedData.get("ITEM_DESC");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "ITEM_DESC;";
      }
      tmp = (String)userSelectedData.get("ITEM_TYPE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "ITEM_TYPE;";
      }
      tmp = (String)userSelectedData.get("MATERIAL_CATEGORY");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "MATERIAL_CATEGORY;";
      }
      tmp = (String)userSelectedData.get("MFG_LOT");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "MFG_LOT;";
      }
      tmp = (String)userSelectedData.get("REFERENCE_NUMBER");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "REFERENCE_NUMBER;";
      }
      tmp = (String)userSelectedData.get("RECEIPT_ID");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "RECEIPT_ID;";
      }
      tmp = (String)userSelectedData.get("DATE_DELIVERED");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "DATE_DELIVERED;";
      }
      tmp = (String)userSelectedData.get("QUANTITY");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "QUANTITY;";
      }
      tmp = (String)userSelectedData.get("INVOICE_UNIT_PRICE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "INVOICE_UNIT_PRICE;";
      }
      tmp = (String)userSelectedData.get("UNIT_REBATE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "UNIT_REBATE;";
      }
      tmp = (String)userSelectedData.get("TOTAL_ADDITIONAL_CHARGE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "TOTAL_ADDITIONAL_CHARGE;";
      }
      tmp = (String)userSelectedData.get("SERVICE_FEE");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "SERVICE_FEE;";
      }
      tmp = (String)userSelectedData.get("PEI_AMOUNT");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "PEI_AMOUNT;";
      }
      tmp = (String)userSelectedData.get("NET_AMOUNT");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "NET_AMOUNT;";
      }
      tmp = (String)userSelectedData.get("MATERIAL_SAVINGS");
      if ("Yes".equalsIgnoreCase(tmp)) {
        reportField += "MATERIAL_SAVINGS;";
      }
      //removing the last simicolon ';' and putting query string together
      if (reportField.length() > 2) {
        reportField = reportField.substring(0,reportField.length()-1);
        value += "'"+reportField+"')";
        query += "report_field) " + value;
      }else {   //no report field
        value = value.substring(0,value.length()-1) + ")";
        query = query.substring(0,query.length()-1) + ") " + value;
      }
      //insert record into cost_report_template
      db.doUpdate(query);
    }catch (Exception e) {
      throw e;
    }
  }

  private String getOriginalWhere(Vector origSqlFieldV, Vector origSqlValueV) {
    String result = "";
    for (int i = 0; i < origSqlFieldV.size(); i++) {
      String tmpCol = (String)origSqlFieldV.elementAt(i);
      String tmpVal = (String)origSqlValueV.elementAt(i);
      if ("requestor".equalsIgnoreCase(tmpCol)) {
        result += "requestor = "+tmpVal+" and ";
      }else if ("cost_report_group".equalsIgnoreCase(tmpCol)) {
        result += tmpVal+" and ";
      }else if ("facility_id".equalsIgnoreCase(tmpCol)) {
        result += tmpVal+" and ";
      }else if ("application".equalsIgnoreCase(tmpCol)) {
        result += "application = '"+tmpVal+"' and ";
      }else if ("account_sys_id".equalsIgnoreCase(tmpCol)) {
        result += "account_sys_id = '"+tmpVal+"' and ";
      }else if ("charge_type".equalsIgnoreCase(tmpCol)) {
        result += "lower(charge_type) = '"+tmpVal+"' and ";
      }else if ("account_number".equalsIgnoreCase(tmpCol)) {
        result += "upper(account_number) like upper('%"+tmpVal+"%') and ";
      }else if ("account_number2".equalsIgnoreCase(tmpCol)) {
        result += "upper(account_number2) like upper('%"+tmpVal+"%') and ";
      }else if ("po_number".equalsIgnoreCase(tmpCol)) {
        result += "upper(po_number) like upper('%"+tmpVal+"%') and ";
      }else if ("mfg_desc".equalsIgnoreCase(tmpCol)) {
        result += "upper(mfg_desc) like upper('%"+tmpVal+"%') and ";
      }else if ("cat_part_no".equalsIgnoreCase(tmpCol)) {
        result += "upper(cat_part_no) like upper('%"+tmpVal+"%') and ";
      }else if ("part_description".equalsIgnoreCase(tmpCol)) {
        result += "upper(part_description) like upper('%"+tmpVal+"%') and ";
      }else if ("item_id".equalsIgnoreCase(tmpCol)) {
        result += "item_id like '%"+tmpVal+"%' and ";
      }else if ("item_desc".equalsIgnoreCase(tmpCol)) {
        result += "upper(item_desc) like upper('%"+tmpVal+"%') and ";
      }else if ("invoice".equalsIgnoreCase(tmpCol)) {
        result += "invoice like '%"+tmpVal+"%' and ";
      }else if ("invoice_period".equalsIgnoreCase(tmpCol)) {
        result += "invoice_period = "+tmpVal+" and ";
      }else if ("invoice_date1".equalsIgnoreCase(tmpCol)) {
        //look ahead to see if invoice_date2 is vector
        if (origSqlFieldV.contains("invoice_date2")) {
          String tmp2 = (String)origSqlValueV.elementAt(++i);
          result += "invoice_date between to_date('"+tmpVal+"','mm/dd/yyyy') and to_date('"+tmp2+"','mm/dd/yyyy') and ";
        }else {
          result += "invoice_date > to_date('"+tmpVal+"','mm/dd/yyyy') and ";
        }
      }else if ("invoice_date2".equalsIgnoreCase(tmpCol)) {
        result += "invoice_date < to_date('"+tmpVal+"','mm/dd/yyyy') and ";
      }else if ("date_delivered1".equalsIgnoreCase(tmpCol)) {
        //look ahead to see if date_delivered2 is vector
        if (origSqlFieldV.contains("date_delivered2")) {
          String tmp2 = (String)origSqlValueV.elementAt(++i);
          result += "date_delivered between to_date('"+tmpVal+"','mm/dd/yyyy') and to_date('"+tmp2+"','mm/dd/yyyy') and ";
        }else {
          result += "date_delivered > to_date('"+tmpVal+"','mm/dd/yyyy') and ";
        }
      }else if ("date_delivered2".equalsIgnoreCase(tmpCol)) {
        result += "date_delivered < to_date('"+tmpVal+"','mm/dd/yyyy') and ";
      }else if ("commodity".equalsIgnoreCase(tmpCol)) {
        result += "commodity in ("+tmpVal+") and ";
      }else if ("item_type".equalsIgnoreCase(tmpCol)) {
        result += "item_type in ("+tmpVal+") and ";
      }
    } //end of loop
    //remove the last 'and '
    if (result.length() > 0) {
      result = result.substring(0,result.length()-4);
    }

    return result;
  } //end of method

  public StringBuffer getAdditionalCharge(Vector sqlFieldV, Vector sqlValueV, Vector origSqlFieldV, Vector origSqlValueV) throws Exception {
    //column as number
    Vector numberFields = new Vector(19);
    numberFields.addElement("invoice");
    numberFields.addElement("invoice_period");
    numberFields.addElement("invoice_line");
    numberFields.addElement("issue_id");
    numberFields.addElement("total_sales_tax");
    numberFields.addElement("item_id");
    numberFields.addElement("pr_number");
    numberFields.addElement("category_id");
    numberFields.addElement("receipt_id");
    numberFields.addElement("additional_charge_amount");
    //columns as to_number
    Vector toNumberFields = new Vector(7);
    toNumberFields.addElement("unit_rebate");
    toNumberFields.addElement("percent_split_charge");
    toNumberFields.addElement("invoice_unit_price");
    //columns to trim leading or traiting blank space
    Vector trimFields = new Vector(11);
    trimFields.addElement("application");
    trimFields.addElement("account_number");
    trimFields.addElement("account_number2");
    trimFields.addElement("po_number");
    trimFields.addElement("mfg_lot");
    trimFields.addElement("requestor");
    trimFields.addElement("packaging");
    trimFields.addElement("additional_charge_desc");
    //columns to round
    Vector roundFields = new Vector(11);
    roundFields.addElement("total_add_charge");
    roundFields.addElement("net_amount");
    roundFields.addElement("total_rebate");
    //column with percent split charge in calculation
    Vector perSplitCharge = new Vector(7);
    perSplitCharge.addElement("quantity");
    perSplitCharge.addElement("total_add_charge");
    perSplitCharge.addElement("service_fee");
    perSplitCharge.addElement("pei_amount");
    perSplitCharge.addElement("net_amount");
    perSplitCharge.addElement("total_rebate");

    StringBuffer Msg = new StringBuffer();
    DBResultSet dbrs = null;
    //putting the where together
    String where = "";
    String having = "";
    String havingCol = "";
    for (int i = 0; i < sqlFieldV.size(); i++) {
      String col = sqlFieldV.elementAt(i).toString();
      String tmpVal = sqlValueV.elementAt(i).toString().trim();
      //ship column with sum

      //ship of value is empty
      if (BothHelpObjs.isBlankString(tmpVal)) {
        continue;
      }
      if ("invoice_date".equalsIgnoreCase(col)) {
        where += "to_char("+col+",'mm/dd/yyyy') = '"+tmpVal+"' and ";
      }else if ("date_delivered".equalsIgnoreCase(col)) {
        if (tmpVal.length() == 10) {     //yyyy-mm-dd
          where += "to_char("+col+",'yyyy-mm-dd') = '"+tmpVal+"' and ";
        }else if (tmpVal.length() == 7) {  //yyyy-mm
          where += "to_char("+col+",'yyyy-mm') = '"+tmpVal+"' and ";
        }else {
          where += "to_char("+col+",'yyyy') = '"+tmpVal+"' and ";
        }
      }else {
        //if column is a number then don't use single quote
        if (numberFields.contains(col)) {
          where += col+" = "+tmpVal+" and ";
        }else if (toNumberFields.contains(col)) {
          where += "to_number("+col+") = "+tmpVal+" and ";
        }else if (trimFields.contains(col)) {
          where += "trim("+col+") = '"+HelpObjs.singleQuoteToDouble(tmpVal)+"' and ";
        }else if (roundFields.contains(col) || perSplitCharge.contains(col)) {
          continue;
        }else {
          where += col+" = '"+HelpObjs.singleQuoteToDouble(tmpVal)+"' and ";
        }
      }
    }
    //removing the last commas ','
    if (where.length() > 0) {
      where = where.substring(0,where.length()-4);
    }
    //also add original where clause to constrant
    String origWhere = getOriginalWhere(origSqlFieldV,origSqlValueV);
    if (origWhere.length() > 0) {
      where += " and "+origWhere;
    }

    ResultSet rs = null;
    String query = "select currency_id,additional_charge_desc";
    if (sqlFieldV.contains("percent_split_charge")) {
      query += ",sum(additional_charge_amount) amount";
    }else {
      query += ",sum(additional_charge_amount * percent_split_charge) amount";
    }
    /*add having and having column to sql as well
    if (havingCol.length() > 0) {
      //then remove the last commas ',' from havingCol and the last 'and ' from having
      query += ","+havingCol.substring(0,havingCol.length()-1);
      having = having.substring(0,having.length()-4);
    }*/
    query += " from cost_report_add_charge_view where "+where+
             " group by additional_charge_desc,currency_id";
    /*now add having to sql
    if (having.length() > 0 ) {
      query += " having "+having;
    }*/
    try {
      //System.out.println("----- addition charge: "+query);
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int count = 0;

      Hashtable totalPerCurrency = new Hashtable(5);
      while (rs.next()) {
        Msg.append("<TR>\n");
        String tmpCurrencyID = BothHelpObjs.makeBlankFromNull(rs.getString("currency_id"));
        BigDecimal tmp = rs.getBigDecimal("amount");
        //add total per currency
        if (totalPerCurrency.containsKey(tmpCurrencyID)) {
          BigDecimal tmpTotal = (BigDecimal) totalPerCurrency.get(tmpCurrencyID);
          tmpTotal = tmpTotal.add(tmp);
          totalPerCurrency.put(tmpCurrencyID,tmpTotal);
        }else {
          BigDecimal tmpTotal = new BigDecimal(0);
          tmpTotal = tmpTotal.add(tmp);
          totalPerCurrency.put(tmpCurrencyID,tmpTotal);
        }
        //fill in rest of the data
        if (count % 2 == 0) {
          Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("additional_charge_desc")));Msg.append("</FONT></TD>\n");
          Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(tmp.setScale(6,BigDecimal.ROUND_HALF_UP));Msg.append("</FONT></TD>\n");
          Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(tmpCurrencyID);Msg.append("</FONT></TD>\n");
        }else {
          Msg.append("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("additional_charge_desc")));Msg.append("</FONT></TD>\n");
          Msg.append("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(tmp.setScale(6,BigDecimal.ROUND_HALF_UP));Msg.append("</FONT></TD>\n");
          Msg.append("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(tmpCurrencyID);Msg.append("</FONT></TD>\n");
        }
        Msg.append("</TR>\n");
        count++;
      }
      //finally insert total
      Msg.append("<TR>\n");
      //for each currency display total
      Enumeration enume = totalPerCurrency.keys();
      while (enume.hasMoreElements()) {
        String key = enume.nextElement().toString();
        BigDecimal totalCharge = (BigDecimal) totalPerCurrency.get(key);
        if (count % 2 == 0) {
          Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\"><B>Total:</B></FONT></TD>\n");
          Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\"><B>");Msg.append(totalCharge.setScale(6,BigDecimal.ROUND_HALF_UP));Msg.append("</B></FONT></TD>\n");
          Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\"><B>");Msg.append(key);Msg.append("</B></FONT></TD>\n");
        }else {
          Msg.append("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\"><B>Total:</B></FONT></TD>\n");
          Msg.append("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\"><B>");Msg.append(totalCharge.setScale(6,BigDecimal.ROUND_HALF_UP));Msg.append("</B></FONT></TD>\n");
          Msg.append("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\"><B>");Msg.append(key);Msg.append("</B></FONT></TD>\n");
        }
        Msg.append("</TR>\n");
        count++;
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }
    return Msg;
  } //end of method

  public StringBuffer getAdditionalCharge(String invoice, String line) throws Exception {
    StringBuffer Msg = new StringBuffer();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = "select iacd.additional_charge_desc,round(sum(iacd.additional_charge_amount * idetail.percent_split_charge),2) amount from invoice_add_charge_detail iacd, invoice_detail idetail"+
                   " where iacd.invoice = "+invoice+" and iacd.invoice_line = "+line+" and iacd.invoice = idetail.invoice and iacd.invoice_line = idetail.invoice_line"+
                   " and iacd.issue_id = idetail.issue_id and iacd.item_type = idetail.item_type and iacd.pr_number = idetail.pr_number and iacd.line_item = idetail.line_item"+
                   " group by additional_charge_desc";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int count = 0;
      float totalCharge = 0;
      while (rs.next()) {
        Msg.append("<TR>\n");
        totalCharge += rs.getFloat("amount");
        if (count % 2 == 0) {
          Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("additional_charge_desc")));Msg.append("</FONT></TD>\n");
          Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("amount")));Msg.append("</FONT></TD>\n");
        }else {
          Msg.append("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("additional_charge_desc")));Msg.append("</FONT></TD>\n");
          Msg.append("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("amount")));Msg.append("</FONT></TD>\n");
        }
        Msg.append("</TR>\n");
        count++;
      }
      //finally insert total
      Msg.append("<TR>\n");
      if (count % 2 == 0) {
        Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\"><B>Total:</B></FONT></TD>\n");
        Msg.append("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\"><B>");Msg.append(totalCharge);Msg.append("</B></FONT></TD>\n");
      }else {
        Msg.append("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\"><B>Total:</B></FONT></TD>\n");
        Msg.append("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\"><B>");Msg.append(totalCharge);Msg.append("</B></FONT></TD>\n");
      }
      Msg.append("</TR>\n");

    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }
    return Msg;
  } //end of method

  private Vector getAdditionalFields(Vector sqlFields, Vector reportFields) {
    Vector v = new Vector(11);
    //part description
    if (sqlFields.contains("part_description") && !sqlFields.contains("cat_part_no")) {
      v.addElement("cat_part_no");
      sqlFields.addElement("cat_part_no");
      reportFields.addElement("cat_part_no");
    }
    //item description
    if (sqlFields.contains("fx_display_item_desc(item_id)") && !sqlFields.contains("item_id")) {
      v.addElement("item_id");
      sqlFields.addElement("item_id");
      reportFields.addElement("item_id");
    }
    //manufacturer
    if (sqlFields.contains("mfg_desc")) {
      v.addElement("mfg_id");
      sqlFields.addElement("mfg_id");
      reportFields.addElement("mfg_id");
    }
    //work area desc
    if (sqlFields.contains("application_desc")) {
      v.addElement("application");
      sqlFields.addElement("application");
      reportFields.addElement("application");
    }

    return v;
  } //end of method

  public Vector getReportDataNew(Hashtable userSelectedData, String personnelID) throws Exception {
    Vector result = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = "";
    String groupBy = "";
    String where = "";
    //temporary keep this for additional charge drill down
    Vector sqlCols = new Vector(51);
    Vector sqlVals = new Vector(51);

    boolean needGroupBy = false;
    try {
      //build select statement
      Vector v = (Vector)userSelectedData.get("SQL_FIELDS");
      //determine whether part_desc, item_desc and/or manufacturer is one of the field user is interested in
      Vector additionalFields = getAdditionalFields(v,(Vector)userSelectedData.get("REPORT_FIELDS"));

      for (int i = 0; i < v.size(); i++) {
        String tmp = (String)v.elementAt(i);
        if (tmp.equalsIgnoreCase("quantity")) {
          //if split charge
          if (v.contains("percent_split_charge")) {
            tmp = "sum("+tmp+")";
          }else {
            tmp = "sum(percent_split_charge * "+tmp+")";
          }
          needGroupBy = true;
        }else if (tmp.equalsIgnoreCase("total_add_charge") ||
				      tmp.equalsIgnoreCase("total_sales_tax") ||
						tmp.equalsIgnoreCase("service_fee") ||
                  tmp.equalsIgnoreCase("pei_amount")) {
          if (v.contains("percent_split_charge")) {
            tmp = "to_char(sum("+tmp+"),'9999999999999999999.999990')";
          }else {
            tmp = "to_char(sum(percent_split_charge * "+tmp+"),'9999999999999999999.999990')";
          }
          needGroupBy = true;
        }else if (tmp.equalsIgnoreCase("net_amount")) {
          tmp = "to_char(sum("+tmp+"),'9999999999999999999.999990')";
          needGroupBy = true;
        }else if (tmp.equalsIgnoreCase("total_rebate")) {
          tmp = "to_char(sum("+tmp+"),'9999999999999999999.999990')";
          needGroupBy = true;
        }else {
          //don't add item_desc to group by
          if (!tmp.equalsIgnoreCase("fx_display_item_desc(item_id)")) {
            groupBy += tmp + ",";
          }
        }
        query += tmp+",";
      }
      //removing the last commas ','
      if (query.length() > 2) {
        query = query.substring(0,query.length()-1);
      }

      //build where clause
      String tmp = (String)userSelectedData.get("REQUESTOR_ID");
      if (tmp.length() > 0 && !"0".equalsIgnoreCase(tmp)) {
        where += "requestor = "+tmp+ " and ";
        sqlCols.addElement("requestor");
        sqlVals.addElement(tmp);
      }
      tmp = (String)userSelectedData.get("HUB_C");
      if (!"All Reporting Groups".equalsIgnoreCase(tmp) && !"None".equalsIgnoreCase(tmp)) {
        String tmpVal = "facility_id in (select facility_id from cost_report_group where cost_report_group = '"+tmp+"')";
        where += tmpVal + " and ";
        sqlCols.addElement("cost_report_group");
        sqlVals.addElement(tmpVal);
      }
      tmp = (String)userSelectedData.get("FACILITY");
      String tmp2 = "";
      if (!"All Facilities".equalsIgnoreCase(tmp) && !"None".equalsIgnoreCase(tmp)) {
        String tmpVal = "facility_id = '"+tmp+"'";
        where +=  tmpVal + " and ";
        sqlCols.addElement("facility_id");
        sqlVals.addElement(tmpVal);
      }else if ("All Facilities".equalsIgnoreCase(tmp)) {
        String tmpVal = "facility_id in (select ugm.facility_id from user_group_member ugm, user_group_member_application ugma"+
                        " where ugm.user_group_id = 'CostReport' and ugm.personnel_id = "+personnelID+
                        " and ugm.facility_id = ugma.facility_id"+
                        " and ugm.personnel_id = ugma.personnel_id and ugma.user_group_id in ('GenerateOrders','DisplayApplication')) ";
        where += tmpVal + " and ";
        sqlCols.addElement("facility_id");
        sqlVals.addElement(tmpVal);
      }
      tmp = (String)userSelectedData.get("WORK_AREA");
      if (!"All Work Areas".equalsIgnoreCase(tmp) && !"None".equalsIgnoreCase(tmp)) {
        where += "application = '"+tmp+"' and ";
        sqlCols.addElement("application");
        sqlVals.addElement(tmp);
      }
      tmp = (String)userSelectedData.get("ACCOUNT_SYS");
      if (!"All Accounting Systems".equalsIgnoreCase(tmp) && !"None".equalsIgnoreCase(tmp)) {
        where += "account_sys_id = '"+tmp+"' and ";
        sqlCols.addElement("account_sys_id");
        sqlVals.addElement(tmp);
        //determine whether to deal with charge number or PO/BPO
        if ("p".equalsIgnoreCase((String)userSelectedData.get("PO_REQUIRED"))) {
          tmp = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)userSelectedData.get("CHARGE_NUMBER_1"));         //po uses charge number 1 text field
          tmp = tmp.trim();
          if (tmp.length() > 0) {
            tmp = (String)userSelectedData.get("DIRECT");
            if ("Yes".equalsIgnoreCase(tmp)) {
              where += "lower(charge_type) = 'd' and ";
              sqlCols.addElement("charge_type");
              sqlVals.addElement("d");
            }else if ("Yes".equalsIgnoreCase((String)userSelectedData.get("INDIRECT"))) {
              where += "lower(charge_type) = 'i' and ";
              sqlCols.addElement("charge_type");
              sqlVals.addElement("i");
            }
            where += "lower(po_number) like lower('%"+(String)userSelectedData.get("CHARGE_NUMBER_1")+"%') and ";
            sqlCols.addElement("po_number");
            sqlVals.addElement((String)userSelectedData.get("CHARGE_NUMBER_1"));
          }
        }else {
          boolean containsChargeN = false;
          tmp = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)userSelectedData.get("CHARGE_NUMBER_1"));
          tmp = tmp.trim();
          if (tmp.length() > 0) {
            where += "lower(account_number) like lower('%"+tmp+"%') and ";
            containsChargeN = true;
            sqlCols.addElement("account_number");
            sqlVals.addElement(tmp);
          }
          tmp = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)userSelectedData.get("CHARGE_NUMBER_2"));
          tmp = tmp.trim();
          if (tmp.length() > 0) {
            where += "lower(account_number2) like lower('%"+tmp+"%') and ";
            containsChargeN = true;
            sqlCols.addElement("account_number2");
            sqlVals.addElement(tmp);
          }
          if (containsChargeN) {
            tmp = (String)userSelectedData.get("DIRECT");
            if ("Yes".equalsIgnoreCase(tmp)) {
              where += "lower(charge_type) = 'd' and ";
              sqlCols.addElement("charge_type");
              sqlVals.addElement("d");
            }else if ("Yes".equalsIgnoreCase((String)userSelectedData.get("INDIRECT"))) {
              where += "lower(charge_type) = 'i' and ";
              sqlCols.addElement("charge_type");
              sqlVals.addElement("i");
            }
          }
        } //end of charge number
      }

      tmp = (String)userSelectedData.get("SEARCH_BY");
      tmp2 = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)userSelectedData.get("SEARCH_TEXT"));
      tmp2 = tmp2.trim();
      if ("Mfg".equalsIgnoreCase(tmp)) {
        if (tmp2.length() > 0) {
          where += "lower(mfg_desc) like lower('%"+tmp2+"%') and ";
          sqlCols.addElement("mfg_desc");
          sqlVals.addElement(tmp2);
        }
      }else if ("Part Number".equalsIgnoreCase(tmp)) {
        if (tmp2.length() > 0) {
          where += "lower(cat_part_no) like lower('%"+tmp2+"%') and ";
          sqlCols.addElement("cat_part_no");
          sqlVals.addElement(tmp2);
        }
      }else if ("Item".equalsIgnoreCase(tmp)) {
        if (tmp2.length() > 0) {
          where += "item_id like '%"+tmp2+"%' and ";
          sqlCols.addElement("item_id");
          sqlVals.addElement(tmp2);
        }
      }else if ("Item Type".equalsIgnoreCase(tmp)) {
        if (tmp2.length() > 0) {
          where += "lower(item_type) like lower('%"+tmp2+"%') and ";
          sqlCols.addElement("item_type");
          sqlVals.addElement(tmp2);
        }
      }else if ("Part Description".equalsIgnoreCase(tmp)) {
        if (tmp2.length() > 0) {
          where += "lower(part_description) like lower('%"+tmp2+"%') and ";
          sqlCols.addElement("part_description");
          sqlVals.addElement(tmp2);
        }
      }else if ("Item Description".equalsIgnoreCase(tmp)) {
        if (tmp2.length() > 0) {
          where += "lower(item_desc) like lower('%"+tmp2+"%') and ";
          sqlCols.addElement("item_desc");
          sqlVals.addElement(tmp2);
        }
      }else if ("Reference".equalsIgnoreCase(tmp)) {
        if (tmp2.length() > 0) {
          where += "lower(reference_number) like lower('%"+tmp2+"%') and ";
          sqlCols.addElement("reference_number");
          sqlVals.addElement(tmp2);
        }
      }else if ("MR-Line".equalsIgnoreCase(tmp)) {
        if (tmp2.length() > 0) {
          //if user entered mr-line then split screen
          if (tmp2.indexOf("-") > 0) {
            String[] tmpMrLine = tmp2.split("-");
            if (tmpMrLine.length > 1) {
              where += "pr_number like lower('%" + tmpMrLine[0].trim() + "%') and ";
              sqlCols.addElement("pr_number");
              sqlVals.addElement("pr_number");
              where += "line_item like lower('%" + tmpMrLine[1].trim() + "%') and ";
              sqlCols.addElement("line_item");
              sqlVals.addElement("line_item");
            }else {
              where += "pr_number like lower('%" + tmpMrLine[0].trim() + "%') and ";
              sqlCols.addElement("pr_number");
              sqlVals.addElement("pr_number");
            }

          }else {
            where += "pr_number like lower('%" + tmp2 + "%') and ";
            sqlCols.addElement("pr_number");
            sqlVals.addElement("pr_number");
          }
        }
      }

      //
      tmp = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)userSelectedData.get("INVOICE_NUMBER"));
      tmp = tmp.trim();
      if (tmp.length() > 0) {
        where += "invoice like '%"+tmp+"%' and ";
        sqlCols.addElement("invoice");
        sqlVals.addElement(tmp);
      }
      tmp = (String)userSelectedData.get("INVOICE_PERIOD_C");
      tmp = tmp.trim();
      if (!"All".equalsIgnoreCase(tmp) && tmp.length() > 0) {
        where += "invoice_period = "+tmp+" and ";
        sqlCols.addElement("invoice_period");
        sqlVals.addElement(tmp);
      }
      //invoice date
      tmp = (String)userSelectedData.get("INVOICE_START_DATE");
      tmp = tmp.trim();
      tmp2 = (String)userSelectedData.get("INVOICE_END_DATE");
      tmp2 = tmp2.trim();
      if (tmp.length() > 0 && tmp2.length() > 0) {
        where += "(invoice_date between to_date('"+tmp+"','mm/dd/yyyy') and to_date('"+tmp2+"','mm/dd/yyyy')) and ";
        sqlCols.addElement("invoice_date1");
        sqlVals.addElement(tmp);
        sqlCols.addElement("invoice_date2");
        sqlVals.addElement(tmp2);
      }else if (tmp.length() > 0) {
        where += "invoice_date > to_date('"+tmp+"','mm/dd/yyyy') and ";
        sqlCols.addElement("invoice_date1");
        sqlVals.addElement(tmp);
      }else if (tmp2.length() > 0) {
        where += "invoice_date < to_date('"+tmp2+"','mm/dd/yyyy') and ";
        sqlCols.addElement("invoice_date2");
        sqlVals.addElement(tmp2);
      }
      //delivered date
      tmp = (String)userSelectedData.get("START_DATE");
      tmp = tmp.trim();
      tmp2 = (String)userSelectedData.get("END_DATE");
      tmp2 = tmp2.trim();
      if (tmp.length() > 0 && tmp2.length() > 0) {
        where += "(date_delivered between to_date('"+tmp+"','mm/dd/yyyy') and to_date('"+tmp2+"','mm/dd/yyyy')) and ";
        sqlCols.addElement("date_delivered1");
        sqlVals.addElement(tmp);
        sqlCols.addElement("date_delivered2");
        sqlVals.addElement(tmp2);
      }else if (tmp.length() > 0) {
        where += "date_delivered > to_date('"+tmp+"','mm/dd/yyyy') and ";
        sqlCols.addElement("date_delivered1");
        sqlVals.addElement(tmp);
      }else if (tmp2.length() > 0) {
        where += "date_delivered < to_date('"+tmp2+"','mm/dd/yyyy') and ";
        sqlCols.addElement("date_delivered2");
        sqlVals.addElement(tmp2);
      }
      //commodity
      Vector tmpTypeV = (Vector)userSelectedData.get("COMMODITY_C");
      tmp = "";
      int iii = 0;
      for (iii = 0; iii < tmpTypeV.size(); iii++) {
        if (!"All Types".equalsIgnoreCase((String)tmpTypeV.elementAt(iii))) {
          tmp += "'"+(String)tmpTypeV.elementAt(iii)+"',";
        }
      }
      //removing the last commas ','
      if (tmp.length() > 0 ) {
        tmp = tmp.substring(0,tmp.length()-1);
        where += "commodity in ("+tmp+") and ";
        sqlCols.addElement("commodity");
        sqlVals.addElement(tmp);
      }
      tmpTypeV = null;
      //item type
      tmpTypeV = (Vector)userSelectedData.get("ITEM_TYPE_C");
      tmp = "";
      for (iii = 0; iii < tmpTypeV.size(); iii++) {
        if (!"All Types".equalsIgnoreCase((String)tmpTypeV.elementAt(iii))) {
          if ("MA".equalsIgnoreCase((String)tmpTypeV.elementAt(iii))) {
            tmp += "'"+(String)tmpTypeV.elementAt(iii)+"','DU','OB',";
          }else {
            tmp += "'"+(String)tmpTypeV.elementAt(iii)+"',";
          }
        }
      }
      //removing the last commas ','
      if (tmp.length() > 0 ) {
        tmp = tmp.substring(0,tmp.length()-1);
        where += "item_type in ("+tmp+") and ";
        sqlCols.addElement("item_type");
        sqlVals.addElement(tmp);
      }

      //build sql
      query = "select "+query+" from cost_report_view ";
      if (where.length() > 0) {
        where = where.substring(0,where.length()-4);
        query += " where "+where;
      }

      //if group by required - remove the last commas ','
      if (needGroupBy) {
        if (groupBy.length() > 2) {
          groupBy = groupBy.substring(0,groupBy.length()-1);
          query += " group by "+groupBy;
        }
      }

      String orderBy = (String)userSelectedData.get("ORDER_BY");
      if (!BothHelpObjs.isBlankString(orderBy)) {
        query += " order by "+orderBy;
      }

      //System.out.println("------ cost report: "+query);
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int totalAddChargeCol = v.indexOf("total_add_charge");
		int totalSalesTaxCol = v.indexOf("total_sales_tax");
		int serviceFeeCol = v.indexOf("service_fee");
      int netAmountCol = v.indexOf("net_amount");
      int materialSavingCol = v.indexOf("total_rebate");
      int peiAmountCol = v.indexOf("pei_amount");
      double totalAddCharge = 0;
		double totalSalesTax = 0;
		double totalServiceFee = 0;
      double totalNetAmount = 0;
      double totalMaterialSaving = 0;
      double totalPeiAmount = 0;
      Hashtable totalPerCurrency = new Hashtable(11);

      while (rs.next()) {
        Vector tmpV = new Vector(v.size());
        for (int j = 1; j <= v.size(); j++) {
          tmpV.addElement(BothHelpObjs.makeSpaceFromNull(rs.getString(j)));
        }
        result.addElement(tmpV);

        //calculating total
        if (totalAddChargeCol >= 0) {
          totalAddCharge += rs.getDouble(totalAddChargeCol+1);
        }
		  if (totalSalesTaxCol >= 0) {
          totalSalesTax += rs.getDouble(totalSalesTaxCol+1);
        }
		  if (serviceFeeCol >= 0) {
          totalServiceFee += rs.getDouble(serviceFeeCol+1);
        }
        if (netAmountCol >= 0) {
          totalNetAmount += rs.getDouble(netAmountCol+1);
        }
        if (materialSavingCol >= 0) {
          totalMaterialSaving += rs.getDouble(materialSavingCol+1);
        }
        if (peiAmountCol >= 0) {
          totalPeiAmount += rs.getDouble(peiAmountCol+1);
        }
        //calculating differently if we show currency
        if (v.contains("currency_id")) {
          String tmpCurrency = BothHelpObjs.makeBlankFromNull(rs.getString("currency_id"));

          if (totalAddChargeCol >= 0) {
            if (totalPerCurrency.containsKey(tmpCurrency)) {
              Hashtable innerH = (Hashtable) totalPerCurrency.get(tmpCurrency);
              if (innerH.containsKey("Total Additional Charge")) {
                Double totalVal = (Double)innerH.get("Total Additional Charge");
                totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(totalAddChargeCol+1))+totalVal.doubleValue());
                innerH.put("Total Additional Charge",totalVal);
              }else {
                Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(totalAddChargeCol+1)));
                innerH.put("Total Additional Charge",totalVal);
              }
            }else {
              Hashtable innerH = new Hashtable(7);
              Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(totalAddChargeCol+1)));
              innerH.put("Total Additional Charge",totalVal);
              totalPerCurrency.put(tmpCurrency,innerH);
            }
          }
          //total sales tax
          if (totalSalesTaxCol >= 0) {
            if (totalPerCurrency.containsKey(tmpCurrency)) {
              Hashtable innerH = (Hashtable) totalPerCurrency.get(tmpCurrency);
              if (innerH.containsKey("Total Sales Tax")) {
                Double totalVal = (Double)innerH.get("Total Sales Tax");
                totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(totalSalesTaxCol+1))+totalVal.doubleValue());
                innerH.put("Total Sales Tax",totalVal);
              }else {
                Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(totalSalesTaxCol+1)));
                innerH.put("Total Sales Tax",totalVal);
              }
            }else {
              Hashtable innerH = new Hashtable(7);
              Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(totalSalesTaxCol+1)));
              innerH.put("Total Sales Tax",totalVal);
              totalPerCurrency.put(tmpCurrency,innerH);
            }
          }			  
			 //service fee
          if (serviceFeeCol >= 0) {
            if (totalPerCurrency.containsKey(tmpCurrency)) {
              Hashtable innerH = (Hashtable) totalPerCurrency.get(tmpCurrency);
              if (innerH.containsKey("Total Service Fee")) {
                Double totalVal = (Double)innerH.get("Total Service Fee");
                totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(serviceFeeCol+1))+totalVal.doubleValue());
                innerH.put("Total Service Fee",totalVal);
              }else {
                Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(serviceFeeCol+1)));
                innerH.put("Total Service Fee",totalVal);
              }
            }else {
              Hashtable innerH = new Hashtable(7);
              Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(serviceFeeCol+1)));
              innerH.put("Total Service Fee",totalVal);
              totalPerCurrency.put(tmpCurrency,innerH);
            }
          }
          //PEI AMOUNT
          if (peiAmountCol >= 0) {
            if (totalPerCurrency.containsKey(tmpCurrency)) {
              Hashtable innerH = (Hashtable) totalPerCurrency.get(tmpCurrency);
              if (innerH.containsKey("Total PEI Amount")) {
                Double totalVal = (Double)innerH.get("Total PEI Amount");
                totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(peiAmountCol+1))+totalVal.doubleValue());
                innerH.put("Total PEI Amount",totalVal);
              }else {
                Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(peiAmountCol+1)));
                innerH.put("Total PEI Amount",totalVal);
              }
            }else {
              Hashtable innerH = new Hashtable(7);
              Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(peiAmountCol+1)));
              innerH.put("Total PEI Amount",totalVal);
              totalPerCurrency.put(tmpCurrency,innerH);
            }
          }
          //NET AMOUNT
          if (netAmountCol >= 0) {
            if (totalPerCurrency.containsKey(tmpCurrency)) {
              Hashtable innerH = (Hashtable) totalPerCurrency.get(tmpCurrency);
              if (innerH.containsKey("Total Net Amount")) {
                Double totalVal = (Double)innerH.get("Total Net Amount");
                totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(netAmountCol+1))+totalVal.doubleValue());
                innerH.put("Total Net Amount",totalVal);
              }else {
                Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(netAmountCol+1)));
                innerH.put("Total Net Amount",totalVal);
              }
            }else {
              Hashtable innerH = new Hashtable(7);
              Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(netAmountCol+1)));
              innerH.put("Total Net Amount",totalVal);
              totalPerCurrency.put(tmpCurrency,innerH);
            }
          }
          //material saving
          if (materialSavingCol >= 0) {
            if (totalPerCurrency.containsKey(tmpCurrency)) {
              Hashtable innerH = (Hashtable) totalPerCurrency.get(tmpCurrency);
              if (innerH.containsKey("Total Material Savings")) {
                Double totalVal = (Double)innerH.get("Total Material Savings");
                totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(materialSavingCol+1))+totalVal.doubleValue());
                innerH.put("Total Material Savings",totalVal);
              }else {
                Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(materialSavingCol+1)));
                innerH.put("Total Material Savings",totalVal);
              }
            }else {
              Hashtable innerH = new Hashtable(7);
              Double totalVal = new Double(BothHelpObjs.makeFloatFromNull(rs.getString(materialSavingCol+1)));
              innerH.put("Total Material Savings",totalVal);
              totalPerCurrency.put(tmpCurrency,innerH);
            }
          }
        } //end of showing currency
      } //end of while
      //add total to data set
      result.addElement(new Double(totalAddCharge));
		result.addElement(new Double(totalSalesTax));
		result.addElement(new Double(totalServiceFee));
      result.addElement(new Double(totalNetAmount));
      result.addElement(new Double(totalMaterialSaving));
      result.addElement(new Double(totalPeiAmount));
      //**** ADD where clause to last element
      Hashtable h = new Hashtable(5);
      h.put("SQL_COLS",sqlCols);
      h.put("SQL_VALS",sqlVals);
      h.put("ADDITIONAL_FIELDS",additionalFields);
      //I will go ahead and put the currency data in this hashtable h
      h.put("TOTAL_PER_CURRENCY",totalPerCurrency);
      result.addElement(h);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }
    return result;
  } //end of method

  public Hashtable getInvoiceInitialData(String userID) throws Exception {
    String query = "select * from cost_report_initial_view where personnel_id = "+userID+
                   " order by cost_report_group,facility_name, application_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    try{
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector hubID = new Vector();
      Vector hubDesc = new Vector();
      String lastHub = "";
      //String lastFacID = "";
      String lastAccountType = "";
      Hashtable actInfo = new Hashtable();
      while (rs.next()) {
        String tmpFacID = rs.getString("facility_id");
        String tmpHub = rs.getString("cost_report_group");
        if (!tmpHub.equalsIgnoreCase(lastHub)) {
          //hub info
          hubID.addElement(tmpHub);
          hubDesc.addElement(tmpHub);
          Hashtable fh = new Hashtable(3);
          //facility data
          Vector facID = new Vector();
          Vector facDesc = new Vector();
          facID.addElement(tmpFacID);
          facDesc.addElement(rs.getString("facility_name"));
          Hashtable h = new Hashtable(3);
          Vector actSys = new Vector(5);
          //application data
          Vector application = new Vector();
          Vector applicationDesc = new Vector();
          actSys.addElement(rs.getString("account_sys_id"));
          application.addElement(rs.getString("application"));
          applicationDesc.addElement(HelpObjs.addescapesForJavascript(rs.getString("application_desc")));
          h.put("APPLICATION",application);
          h.put("APPLICATION_DESC",applicationDesc);
          h.put("ACCOUNTING_SYSTEM",actSys);
          fh.put(tmpFacID,h);
          //putting them all together
          fh.put("FACILITY_ID",facID);
          fh.put("FACILITY_DESC",facDesc);
          result.put(tmpHub,fh);
        }else {   //hub already exist
          Hashtable fh = (Hashtable)result.get(tmpHub);
          Vector facID = (Vector)fh.get("FACILITY_ID");
          Vector facDesc = (Vector)fh.get("FACILITY_DESC");
          if (!facID.contains(tmpFacID)) {
            facID.addElement(tmpFacID);
            facDesc.addElement(rs.getString("facility_name"));
            Hashtable h = new Hashtable(3);
            Vector actSys = new Vector(5);
            Vector application = new Vector();
            Vector applicationDesc = new Vector();
            actSys.addElement(rs.getString("account_sys_id"));
            application.addElement(rs.getString("application"));
            applicationDesc.addElement(HelpObjs.addescapesForJavascript(rs.getString("application_desc")));
            h.put("APPLICATION",application);
            h.put("APPLICATION_DESC",applicationDesc);
            h.put("ACCOUNTING_SYSTEM",actSys);
            fh.put(tmpFacID,h);
          }else {
            Hashtable h = (Hashtable)fh.get(tmpFacID);
            Vector actSys = (Vector)h.get("ACCOUNTING_SYSTEM");
            Vector application = (Vector)h.get("APPLICATION");
            Vector applicationDesc = (Vector)h.get("APPLICATION_DESC");
            if (!actSys.contains(rs.getString("account_sys_id"))) {
              actSys.addElement(rs.getString("account_sys_id"));
            }
            if (!application.contains(rs.getString("application"))) {
              application.addElement(rs.getString("application"));
              applicationDesc.addElement(HelpObjs.addescapesForJavascript(rs.getString("application_desc")));
            }
            h.put("APPLICATION",application);
            h.put("APPLICATION_DESC",applicationDesc);
            h.put("ACCOUNTING_SYSTEM",actSys);
            fh.put(tmpFacID,h);
          }
          fh.put("FACILITY_ID",facID);
          fh.put("FACILITY_DESC",facDesc);
          result.put(tmpHub,fh);
        }
        lastHub = tmpHub;

        //account info
        String tmpAccountType = rs.getString("account_type");
        if (!actInfo.containsKey(tmpAccountType)) {
          Vector v = new Vector(3);
          v.addElement(rs.getString("po_required"));
          v.addElement(rs.getString("charge_label_1"));
          v.addElement(rs.getString("charge_label_2"));
          actInfo.put(tmpAccountType,v);
        }
        lastAccountType = tmpAccountType;
      } //end of while

      /*sort data - either sort with database or do it my self
      facID = BothHelpObjs.sort(facID);
      facDesc = BothHelpObjs.sort(facDesc);
      for(int j = 0; j < facID.size(); j++) {
        String myFacID = (String)facID.elementAt(j);
        Hashtable h = (Hashtable)result.get(myFacID);
        h.put("APPLICATION",BothHelpObjs.sort((Vector)h.get("APPLICATION")));
        h.put("APPLICATION_DESC",BothHelpObjs.sort((Vector)h.get("APPLICATION_DESC")));
        h.put("ACCOUNTING_SYSTEM",BothHelpObjs.sort((Vector)h.get("ACCOUNTING_SYSTEM")));
        result.put(myFacID,h);
      }*/

      //adding all facilities
      if (hubID.size() > 1) {
        hubID.insertElementAt("All Reporting Groups",0);
        hubDesc.insertElementAt("All Reporting Groups",0);
        Hashtable fh = new Hashtable(3);
        Vector facID = new Vector(1);
        Vector facDesc = new Vector(1);
        facID.insertElementAt("All Facilities",0);
        facDesc.insertElementAt("All Facilities",0);
        Hashtable h = new Hashtable(3);
        Vector actSys = new Vector(1);
        Vector application = new Vector(1);
        Vector applicationDesc = new Vector(1);
        actSys.addElement("All Accounting Systems");
        application.addElement("All Work Areas");
        applicationDesc.addElement("All Work Areas");
        h.put("APPLICATION",application);
        h.put("APPLICATION_DESC",applicationDesc);
        h.put("ACCOUNTING_SYSTEM",actSys);
        fh.put("FACILITY_ID",facID);
        fh.put("FACILITY_DESC",facDesc);
        fh.put("All Facilities",h);
        result.put("All Reporting Groups",fh);
      }
      result.put("HUB_ID",hubID);
      result.put("HUB_DESC",hubDesc);
      //account info
      result.put("ACCOUNT_INFO",actInfo);
      //search by
      Vector v = new Vector(7);
      v.addElement("MR-Line");
      v.addElement("Mfg");
      v.addElement("Part Number");
      v.addElement("Part Description");
      v.addElement("Item");
      v.addElement("Item Description");
      v.addElement("Reference");
      result.put("SEARCH_BY",v);
      /*material category
      query = "select category_id,category_desc from vv_category order by category_desc";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector categoryID = new Vector();
      Vector categoryDesc = new Vector();
      while (rs.next()) {
        categoryID.addElement(rs.getString("category_id"));
        categoryDesc.addElement(rs.getString("category_desc"));
      }
      result.put("MATERIAL_CATEGORY_ID",categoryID);
      result.put("MATERIAL_CATEGORY_DESC",categoryDesc);
      */
      //commodity
      query = "select distinct commodity from invoice where commodity is not null order by commodity";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector comV = new Vector();
      while (rs.next()) {
        comV.addElement(rs.getString("commodity"));
      }
      result.put("COMMODITY",comV);
      //item_type
      //commodity
      query = "select item_type,type_desc from global.vv_item_type where commodity is not null and item_type not in ('DU','OB') order by type_desc";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector itemTypeID = new Vector();
      Vector itemTypeDesc = new Vector();
      while (rs.next()) {
        itemTypeID.addElement(rs.getString("item_type"));
        itemTypeDesc.addElement(rs.getString("type_desc"));
      }
      result.put("ITEM_TYPE_ID",itemTypeID);
      result.put("ITEM_TYPE_DESC",itemTypeDesc);

      //invoice periods
      query = "select invoice_period,invoice_period || ' : ' || to_char(start_date,'mm/dd/yyyy') || ' - ' || to_char(end_date,'mm/dd/yyyy') invoice_date from invoice_period order by invoice_period";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector invoicePeriod = new Vector();
      Vector invoicePeriodDesc = new Vector();
      while (rs.next()) {
        invoicePeriod.addElement(rs.getString("invoice_period"));
        invoicePeriodDesc.addElement(rs.getString("invoice_date"));
      }
      result.put("INVOICE_PERIOD",invoicePeriod);
      result.put("INVOICE_PERIOD_DESC",invoicePeriodDesc);

      //template list
      query = "select report_name from cost_report_template where personnel_id = "+userID+" order by report_name";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String reportName = "";
      while (rs.next()) {
        reportName += rs.getString("report_name") + ";";
      }
      //removing the last semicolon ';'
      if (reportName.length() > 1) {
        reportName = reportName.substring(0,reportName.length()-1);
      }
      result.put("TEMPLATE_LIST",reportName);
    }catch (Exception e) {
      e.printStackTrace();
    }finally{
      if (dbrs != null) {
        dbrs.close();
      }
    }
    return result;
  } //end of method
} //end of class


/*
if (!tmpFacID.equalsIgnoreCase(lastFacID)) {
            facID.addElement(tmpFacID);
            facDesc.addElement(rs.getString("facility_name"));
            Hashtable h = new Hashtable(3);
            Vector actSys = new Vector(5);
            Vector application = new Vector();
            Vector applicationDesc = new Vector();
            actSys.addElement(rs.getString("account_sys_id"));
            application.addElement(rs.getString("application"));
            applicationDesc.addElement(rs.getString("application_desc"));
            h.put("APPLICATION",application);
            h.put("APPLICATION_DESC",applicationDesc);
            h.put("ACCOUNTING_SYSTEM",actSys);
            result.put(tmpFacID,h);
          }else {
            Hashtable h = (Hashtable)result.get(tmpFacID);
            Vector actSys = (Vector)h.get("ACCOUNTING_SYSTEM");
            Vector application = (Vector)h.get("APPLICATION");
            Vector applicationDesc = (Vector)h.get("APPLICATION_DESC");
            if (!actSys.contains(rs.getString("account_sys_id"))) {
              actSys.addElement(rs.getString("account_sys_id"));
            }
            if (!application.contains(rs.getString("application"))) {
              application.addElement(rs.getString("application"));
              applicationDesc.addElement(rs.getString("application_desc"));
            }
            h.put("APPLICATION",application);
            h.put("APPLICATION_DESC",applicationDesc);
            h.put("ACCOUNTING_SYSTEM",actSys);
            result.put(tmpFacID,h);
          }
*/