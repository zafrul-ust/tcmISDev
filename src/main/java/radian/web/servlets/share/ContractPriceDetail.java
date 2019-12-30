package radian.web.servlets.share;

import java.util.Vector;
import java.util.Hashtable;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import oracle.jdbc.OracleTypes;
import java.sql.CallableStatement;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.helpers.HelpObjs;
/**
 * Title:        tcmIS
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corp
 * @author Rajendra Rajput
 * @version 1.0
 */

public class ContractPriceDetail {
    private TcmISDBModel db = null;

    public ContractPriceDetail(TcmISDBModel db) {
      this.db = db;
    } //end of method

    public void buildComboBox(Vector dataIDV, Vector dataDescV, String selected, StringBuffer Msg, boolean addSelectOne) {
      if (dataIDV.size() > 1 && addSelectOne) {
        Msg.append("<OPTION VALUE=\"None\">Select One</OPTION>\n");
      }
      for (int i = 0; i < dataIDV.size(); i++) {
        String preSelect = "";
        if (selected.equalsIgnoreCase((String)dataDescV.elementAt(i))) {
          preSelect = "selected";
        }
        Msg.append("<option "+preSelect+" value=\""+(String)dataIDV.elementAt(i)+"\">"+(String)dataDescV.elementAt(i)+"</option>\n");
      }
    } //end of method

    public String getDescFromID(Vector IDV, Vector descV, String unitOfSale) {
      String result = "";
      int index = IDV.indexOf(unitOfSale);
      if (index > -1) {
        result = (String)descV.elementAt(index);
      }
      return result;
    } //end of method

    private void buildHiddenFields(Hashtable dataH, StringBuffer MSg) {
      Enumeration enumCollec = dataH.keys();
      while (enumCollec.hasMoreElements()) {
        String key = (String) enumCollec.nextElement();
        Vector v = (Vector)dataH.get(key);
      }
    } //end of method

    public StringBuffer buildContractPriceInfo(String requestID) {
      StringBuffer Msg = new StringBuffer();
      //local variables
      String supplierName = "";
      String supplierID = "";
      String contactName = "";
      String contactID = "";
      String contractDesc = "";
      String paymentTerms = "";
      String carrierID = "";
      String carrierName = "";
      String freightID = "";
      String freightName = "";
      String consignment = "";
      String supplierPartNumber = "";
      String multipleOf = "";
      String leadTimeDays = "";
      String baseline = "";
      String selling = "";
      String purchasing = "";
      String uptoQty = "";
      String goodUntil = "";
      String purchasing2 = "";
      String uptoQty2 = "";
      String purchasing3 = "";
      String uptoQty3 = "";
      String purchasing4 = "";
      String uptoQty4 = "";
      String selectedCurrency = "";

      //first get existing data
      Hashtable pricingDataH = getPricingData(requestID);
      if (!pricingDataH.isEmpty()) {
        supplierID = (String)pricingDataH.get("SUPPLIER_ID");
        supplierName = (String)pricingDataH.get("SUPPLIER_NAME");
        contactID = (String)pricingDataH.get("CONTACT_ID");
        contactName = (String)pricingDataH.get("CONTACT_NAME");
        contractDesc = (String)pricingDataH.get("CONTRACT_DESC");
        paymentTerms = (String)pricingDataH.get("PAYMENT_TERMS");
        carrierID = (String)pricingDataH.get("CARRIER_ID");
        carrierName = (String)pricingDataH.get("CARRIER_NAME");
        freightID = (String)pricingDataH.get("FREIGHT_ON_BOARD");
        freightName = (String)pricingDataH.get("FREIGHT_ON_BOARD_DESC");
        consignment = (String)pricingDataH.get("CONSIGNMENT");
        supplierPartNumber = (String)pricingDataH.get("SUPPLIER_PART_NUMBER");
        multipleOf = (String)pricingDataH.get("MULTIPLE_OF");
        leadTimeDays = (String)pricingDataH.get("LEAD_TIME_DAYS");
        baseline = (String)pricingDataH.get("BASELINE_PRICE");
        selling = (String)pricingDataH.get("SELLING_PRICE");
        goodUntil = (String)pricingDataH.get("GOOD_UNTIL");
        selectedCurrency = (String)pricingDataH.get("CONTRACT_CURRENCY");
        purchasing = (String)pricingDataH.get("PURCHASING_PRICE");
        uptoQty = (String)pricingDataH.get("UPTO_QUANTITY");
        //break data from , (commas) separate
        StringTokenizer st = new StringTokenizer(purchasing,",");
        if (st.countTokens() > 1) {
          int count = 0;
          while (st.hasMoreElements()) {
            if (count == 0) {
              purchasing = st.nextElement().toString();
            }else if (count == 1) {
              purchasing2 = st.nextElement().toString();
            }else if (count == 2) {
              purchasing3 = st.nextElement().toString();
            }else if (count == 3) {
              purchasing4 = st.nextElement().toString();
            }
            count++;
          }
        }
        //uptoqty
        st = new StringTokenizer(uptoQty,",");
        if (st.countTokens() > 1) {
          int count = 0;
          while (st.hasMoreElements()) {
            if (count == 0) {
              uptoQty = st.nextElement().toString();
            }else if (count == 1) {
              uptoQty2 = st.nextElement().toString();
            }else if (count == 2) {
              uptoQty3 = st.nextElement().toString();
            }else if (count == 3) {
              uptoQty4 = st.nextElement().toString();
            }
            count++;
          }
        } //end of breaking uptoqty
      }
      Msg.append("<TR><TD ALIGN=\"CENTER\" BGCOLOR=\"#000066\" COLSPAN=\"4\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#ffffff\"><B>Haas Supplier Information</B></FONT></TD></TR>\n");
      //row 1
      //supplier
      Msg.append("<TR><TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Supplier: </B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT CLASS=\"INVISIBLEHEADBLUE\" TYPE=\"text\" NAME=\"supplierName\" ID=\"supplierName\" value=\""+supplierName+"\" SIZE=\"28\" READONLY>\n");
      Msg.append("</FONT>\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"supplierID\" VALUE=\""+supplierID+"\">\n");
      Msg.append("<B>&nbsp;&nbsp;&nbsp;</B>\n");
      Msg.append("<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"...\" onClick= \"searchSupplierWin()\" NAME=\"supplierSearchB\"><IMG src=\"/images/search_small.gif\" alt=\"Search\"></BUTTON>\n");
      Msg.append("</TD>\n");
      //contact Name
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Contact Name:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT CLASS=\"INVISIBLEHEADBLUE\" TYPE=\"text\" NAME=\"contactName\" ID=\"contactName\" value=\""+contactName+"\" SIZE=\"28\" READONLY>\n");
      Msg.append("<B>&nbsp;&nbsp;&nbsp;</B>\n");
      Msg.append("<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"...\" onClick= \"contactNameWin()\" NAME=\"contactB\"><IMG src=\"/images/search_small.gif\" alt=\"Search\"></BUTTON>\n");
      Msg.append("</FONT>\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"contactID\" VALUE=\""+contactID+"\">\n");
      Msg.append("</TD></TR>\n");
      //row 2
      //carrier
      Msg.append("<TR><TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Carrier:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<SELECT CLASS=\"HEADER\" NAME=\"carrierID\" ID=\"carrierID\" size=\"1\">\n");
      //fill approval role drop down
      Vector v = new Vector(3);
      v.addElement("Vendor Truck");
      v.addElement("Pre Pay and Add");
      v.addElement("Customer Managed");
      Vector idV = new Vector(2);
      idV.addElement("VT");
      idV.addElement("PPA");
      idV.addElement("CM");
      String selected = "Select One";
      if ("VT".equalsIgnoreCase(carrierID)) {
        selected = "Vendor Truck";
      }else if ("PPA".equalsIgnoreCase(carrierID)) {
        selected = "Pre Pay and Add";
      }else if ("CM".equalsIgnoreCase(carrierID)) {
        selected = "Customer Managed";
      }
      buildComboBox(idV,v,selected,Msg,true);
      Msg.append("</SELECT>\n");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");

      /*
      Msg.append("<TR><TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Carrier:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"carrierName\" ID=\"carrierName\" value=\""+carrierName+"\" SIZE=\"28\" READONLY>\n");
      Msg.append("<B>&nbsp;&nbsp;&nbsp;</B>\n");
      Msg.append("<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"...\" onClick= \"searchCarrierWin()\" NAME=\"carrierB\"><IMG src=\"/images/search_small.gif\" alt=\"Search\"></BUTTON>\n");
      Msg.append("</FONT>\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"carrierID\" VALUE=\""+carrierID+"\">\n");
      Msg.append("</TD>\n");
      */
      //freight on board
      Msg.append("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Freight on Board:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"freightOnBoard\" ID=\"freightOnBoard\" value=\""+freightName+"\" SIZE=\"28\" READONLY>\n");
      Msg.append("</FONT>\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"freightOnBoardID\" VALUE=\""+freightID+"\">\n");
      Msg.append("</TD></TR>\n");
      //row 3
      //consignment
      Msg.append("<TR><TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Consignment:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<SELECT CLASS=\"HEADER\" NAME=\"consignment\" ID=\"consignment\" size=\"1\">\n");
      //fill approval role drop down
      v = new Vector(2);
      v.addElement("No");
      v.addElement("Yes");
      selected = "Yes";
      if ("N".equalsIgnoreCase(consignment)) {
          selected = "No";
      }
      buildComboBox(v,v,selected,Msg,true);
      Msg.append("</SELECT>\n");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //supplier part number
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Supplier Part #:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"supplierPartNumber\" value=\""+supplierPartNumber.trim()+"\" SIZE=\"30\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD></TR>\n");
      //row 4
      //multiple of
      Msg.append("<TR><TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Item Multiples:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"multipleOf\" value=\""+multipleOf.trim()+"\" SIZE=\"30\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //lead time
      Msg.append("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Lead Time Days:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"leadTime\" value=\""+leadTimeDays.trim()+"\" SIZE=\"30\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD></TR>\n");
      //row 5
      //currency
      Msg.append("<TR><TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Currency:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<SELECT CLASS=\"HEADER\" NAME=\"currency\" ID=\"currency\" size=\"1\">\n");
      //fill approval role drop down
      Hashtable h = getCurrency();
      selectedCurrency = getDescFromID((Vector)h.get("ID"),(Vector)h.get("DESC"),selectedCurrency);
      buildComboBox((Vector)h.get("ID"),(Vector)h.get("DESC"),selectedCurrency,Msg,true);
      Msg.append("</SELECT>\n");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //source type
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Source Type:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<SELECT CLASS=\"HEADER\" NAME=\"contractType\" ID=\"contractType\" size=\"1\">\n");
      //fill approval role drop down
      h = getContractType();
      buildComboBox((Vector)h.get("ID"),(Vector)h.get("DESC"),contractDesc,Msg,true);
      Msg.append("</SELECT>\n");
      Msg.append("</FONT>\n");
      Msg.append("</TD></TR>\n");
      //black line
      Msg.append("<TR><TD COLSPAN=\"4\"><HR STYLE=\"color: 'black'; height: '3'; text-align: 'center'; width: '80%'\"></TD></TR>\n");
      //table to price break
      Msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"95%\" CLASS=\"moveup\">\n");
      //good until
      selected = "";
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" COLSPAN=\"4\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Pricing Good Until:</B>\n");
      Msg.append("<B>&nbsp;</B>\n");
      Msg.append("<INPUT CLASS=\"INVISIBLEHEADBLUE\" TYPE=\"text\" NAME=\"goodUntil\" ID=\"goodUntil\" value=\""+goodUntil+"\" size=\"25\" READONLY><FONT SIZE=\"5\"><a href=\"javascript: void(0);\" ID=\"goodUntil\" onClick=\"return getCalendar(document.catAddDetail.goodUntil);\">&diams;</a></FONT>\n");
      Msg.append("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Clear\" onClick= \"clearDate(document.catAddDetail.goodUntil)\" NAME=\"clearSD\">\n");
      Msg.append("</FONT>\n");
      Msg.append("</TD></TR>\n");
      //row 1
      //Purchasing price
      Msg.append("<TR><TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Purchasing Price per Item:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"purchasing\" value=\""+purchasing.trim()+"\" SIZE=\"25\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //upto quantity
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Upto Quantity:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"uptoQuantity\" value=\""+uptoQty.trim()+"\" SIZE=\"25\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //row 2
      //Purchasing price
      Msg.append("<TR><TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Purchasing Price per Item:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"purchasing2\" value=\""+purchasing2.trim()+"\" SIZE=\"25\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //upto quantity
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Upto Quantity:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"uptoQuantity2\" value=\""+uptoQty2.trim()+"\" SIZE=\"25\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //row 3
      //Purchasing price
      Msg.append("<TR><TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Purchasing Price per Item:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"purchasing3\" value=\""+purchasing3.trim()+"\" SIZE=\"25\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //upto quantity
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Upto Quantity:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"uptoQuantity3\" value=\""+uptoQty3.trim()+"\" SIZE=\"25\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //row 4
      //Purchasing price
      Msg.append("<TR><TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Purchasing Price per Item:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"purchasing4\" value=\""+purchasing4.trim()+"\" SIZE=\"25\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //upto quantity
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      Msg.append("<B>Upto Quantity:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"uptoQuantity4\" value=\""+uptoQty4.trim()+"\" SIZE=\"25\">");
      Msg.append("</FONT>\n");
      Msg.append("</TD>\n");
      //blank line
      Msg.append("<TR><TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>&nbsp;</B>\n");
      Msg.append("</TD></TR>\n");
      Msg.append("</TABLE>\n");

      //set local variable to null
      supplierName = null;
      supplierID = null;
      contactName = null;
      contactID = null;
      contractDesc = null;
      paymentTerms = null;
      carrierID = null;
      carrierName = null;
      freightID = null;
      freightName = null;
      consignment = null;
      supplierPartNumber = null;
      multipleOf = null;
      leadTimeDays = null;
      baseline = null;
      selling = null;
      purchasing = null;
      uptoQty = null;
      goodUntil = null;
      purchasing2 = null;
      uptoQty2 = null;
      purchasing3 = null;
      uptoQty3 = null;
      purchasing4 = null;
      uptoQty4 = null;
      selectedCurrency = null;

      return Msg;
    } //end of method


    private StringBuffer getPageHeader(String message) {
      StringBuffer Msg = new StringBuffer();
      Msg.append("<TABLE BORDER=0 WIDTH=100% CLASS=\"moveupmore\">\n");
      Msg.append("<TR VALIGN=\"TOP\">\n");
      Msg.append("<TD WIDTH=\"200\">\n");
      Msg.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
      Msg.append("</TD>\n");
      Msg.append("<TD ALIGN=\"right\">\n");
      Msg.append("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
      Msg.append("</TD>\n");
      Msg.append("</TR>\n");
      Msg.append("</Table>\n");
      Msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\" CLASS=\"moveup\">\n");

      Msg.append("<TR><TD WIDTH=\"70%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">\n");
      Msg.append(message);
      Msg.append("</TD>\n");
      Msg.append("</TR>\n");
      Msg.append("</TABLE>\n");
      return Msg;
    } //end of method

    public StringBuffer buildHTMLHeader(String PageTitle) {
      StringBuffer Msg = new StringBuffer();
      Msg.append("<HTML>\n");
      Msg.append("<HEAD>\n");
      Msg.append("<TITLE>"+PageTitle+"</TITLE>\n");
      Msg.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
      Msg.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
      Msg.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");

      Msg.append("<SCRIPT SRC=\"/clientscripts/catalogAdd.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
      Msg.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
      Msg.append("</HEAD>\n");
      return Msg;
    }

    Vector getPaymentTerms() {
      Vector v = new Vector(29);
      String query = "select payment_terms from vv_payment_terms where status = 'ACTIVE' order by payment_terms";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          v.addElement(rs.getString("payment_terms"));
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        dbrs.close();
      }
      return v;
    }

    Hashtable getRequestData(String requestID) {
      Hashtable result = new Hashtable(7);
      String query = "select f.inventory_group_default, carn.item_id from facility f,catalog_add_request_new carn"+
                     " where carn.request_id = "+requestID+" and carn.eng_eval_facility_id = f.facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        int index = 0;
        while (rs.next()){
          result.put("INVENTORY_GROUP",rs.getString("inventory_group_default"));
          result.put("ITEM_ID",rs.getString("item_id"));
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        dbrs.close();
      }
      return result;
    }

    Hashtable getContractType() {
      Hashtable result = new Hashtable(2);
      Vector idV = new Vector(7);
      Vector descV = new Vector(7);
      String query = "select contract_type,contract_desc from vv_contract_type where contract_type <> 'INACTIVE' order by contract_desc";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          idV.addElement(rs.getString("contract_type"));
          descV.addElement(rs.getString("contract_desc"));
        }
        result.put("ID",idV);
        result.put("DESC",descV);
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        dbrs.close();
      }
      return result;
    } //end of method

    Hashtable getCurrency() {
      Hashtable result = new Hashtable(2);
      Vector idV = new Vector(7);
      Vector descV = new Vector(7);
      String query = "select currency_id,currency_description from vv_currency order by currency_description";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          idV.addElement(rs.getString("currency_id"));
          descV.addElement(rs.getString("currency_description"));
        }
        result.put("ID",idV);
        result.put("DESC",descV);
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        dbrs.close();
      }
      return result;
    } //end of method


    String getCatPricePercentFromBaseline(String requestID) {
      String result = "";
      String query = "select fx_cat_price_from_baseline(eng_eval_facility_id) multiplier from catalog_add_request_new where request_id ="+requestID;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          result = rs.getString("multiplier");
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        dbrs.close();
      }
      return result;
    } //end of method

    Hashtable getUnitOfSaleValues() {
      Hashtable result = new Hashtable(2);
      Vector unitID = new Vector();
      Vector unitDesc = new Vector();
      String query = "select * from vv_unit_of_sale order by unit_of_sale_desc";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          unitID.addElement(rs.getString("unit_of_sale"));
          unitDesc.addElement(rs.getString("unit_of_sale_desc"));
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        dbrs.close();
      }
      result.put("ID",unitID);
      result.put("DESC",unitDesc);
      return result;
    } //end of method

    Hashtable getPricingData(String requestID) {
      Hashtable result = new Hashtable(41);
      String query = "select supplier,nvl(supplier_name,' ') supplier_name,supplier_contact_id,supplier_contact_info,contract_type,contract_desc,"+
                     "nvl(payment_terms,' ')payment_terms,nvl(carrier,' ') carrier,nvl(carrier_name,' ') carrier_name,nvl(freight_on_board,' ') freight_on_board,nvl(freight_desc,' ') freight_desc,consignment,nvl(supplier_part_no,' ') supplier_part_no,"+
                     "nvl(to_char(multiple_of),' ') multiple_of,nvl(to_char(lead_time_days),' ') lead_time_days,nvl(to_char(baseline_price),' ') baseline_price,"+
                     "nvl(to_char(catalog_price),' ') catalog_price,nvl(to_char(item_unit_price),' ') item_unit_price,nvl(to_char(upto_quantity),' ') upto_quantity,"+
                     "nvl(to_char(end_date,'mm/dd/yyyy'),' ') end_date,nvl(to_char(unit_of_sale),' ') unit_of_sale,nvl(to_char(unit_of_sale_price),' ') unit_of_sale_price,"+
                     "nvl(facility_currency_id,' ') facility_currency_id,nvl(contract_currency_id,' ') contract_currency_id"+
                     " from dbuy_contract_request_view where request_id = "+requestID;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          result.put("SUPPLIER_ID",rs.getString("supplier"));
          result.put("SUPPLIER_NAME",rs.getString("supplier_name"));
          result.put("CONTACT_ID",rs.getString("supplier_contact_id"));
          result.put("CONTACT_NAME",rs.getString("supplier_contact_info"));
          result.put("CONTRACT_ID",rs.getString("contract_type"));
          result.put("CONTRACT_DESC",rs.getString("contract_desc"));
          result.put("PAYMENT_TERMS",rs.getString("payment_terms"));
          result.put("CARRIER_ID",rs.getString("carrier"));
          result.put("CARRIER_NAME",rs.getString("carrier_name"));
          result.put("FREIGHT_ON_BOARD",rs.getString("freight_on_board"));
          result.put("FREIGHT_ON_BOARD_DESC",rs.getString("freight_desc"));
          result.put("CONSIGNMENT",rs.getString("consignment"));
          result.put("SUPPLIER_PART_NUMBER",rs.getString("supplier_part_no"));
          result.put("MULTIPLE_OF",rs.getString("multiple_of"));
          result.put("LEAD_TIME_DAYS",rs.getString("lead_time_days"));
          result.put("BASELINE_PRICE",rs.getString("baseline_price"));
          result.put("SELLING_PRICE",rs.getString("catalog_price"));
          result.put("PURCHASING_PRICE",rs.getString("item_unit_price"));
          result.put("UPTO_QUANTITY",rs.getString("upto_quantity"));
          result.put("GOOD_UNTIL",rs.getString("end_date"));
          result.put("UNIT_OF_SALE",rs.getString("unit_of_sale"));
          result.put("UNIT_OF_SALE_QTY_PER_EACH",rs.getString("unit_of_sale_price"));
          result.put("PRICE_GROUP_CURRENCY",rs.getString("facility_currency_id"));
          result.put("CONTRACT_CURRENCY",rs.getString("contract_currency_id"));
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        dbrs.close();
      }
      return result;
    } //end of method


    public StringBuffer buildSearchCarrierWin(String personnelID, String Cat_Servlet, String searchText, String requestID) {
      StringBuffer Msg = new StringBuffer();
      Msg.append(buildHTMLHeader("Search for Carrier"));
      Msg.append("<BODY>\n");
      Msg.append(getPageHeader(""));
      Msg.append("<FORM method=\"POST\" name=\"searchCarrierWin\" action=\""+Cat_Servlet+"Session=searchCarrierWin\" onsubmit =\"return auditCarrierSearch()\">\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"companyID\" VALUE=\""+db.getClient().toLowerCase()+"\">\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"requestID\" VALUE=\""+requestID+"\">\n");
      //table
      //search text area
      Msg.append("<TABLE ALIGN=\"CENTER\" BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>Search Text:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"carrierT\" value=\""+searchText+"\" SIZE=\"30\">");
      Msg.append("<B>&nbsp;</B>\n");
      Msg.append("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" NAME=\"searchB\">\n");
      Msg.append("</TD></TR>\n");
      Msg.append("</TABLE>\n");
      //result table
      Msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"100%\" >\n");
      //table header
      Msg.append("<TR>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Carrier Name</B></FONT></TD>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Carrier Account #</B></FONT></TD>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Freight on Board</B></FONT></TD>\n");
      Msg.append("</TR>\n");
      String query = "select carrier_code,nvl(account,' ') account,nvl(carrier_name,' ') carrier_name,nvl(freight_on_board,' ') freight_on_board,"+
                       "nvl(freight_on_board_desc,' ') freight_on_board_desc from carrier_info_view where inventory_group = "+
                       "(select f.inventory_group_default from facility f, catalog_add_request_new carn"+
                       " where f.facility_id = carn.eng_eval_facility_id and carn.request_id = "+requestID+") or inventory_group = 'ALL'";
      if (searchText.length() > 0) {
        query += " and lower(carrier_code||account||carrier_name||freight_on_board||freight_on_board_desc) like lower('%"+searchText+"%')";
      }
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        int totalrecords = 0;
        int count = 0;
        while(rs.next()) {
          totalrecords += 1;
          count += 1;
          String Color = " ";
          if (count%2==0) {
            Color = "bgcolor=\"#E6E8FA\"";
          }else {
            Color = "bgcolor=\"#fcfcfc\"";
          }
          String carrierID = rs.getString("carrier_code");
          String carrierName = rs.getString("carrier_name");
          String carrierAccount = rs.getString("account");
          String freightID = rs.getString("freight_on_board");
          String freightName = rs.getString("freight_on_board_desc");

          Msg.append("<TR>\n");
          Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
          Msg.append("<A HREF=\"javascript:getCarrierID('"+carrierID+"','"+carrierName+"','"+freightID+"','"+freightName+"')\" STYLE=\"color:#0000ff\">"+carrierName+"</A>\n");
          Msg.append("</FONT></TD>\n");
          Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");Msg.append(carrierAccount);Msg.append("</FONT></TD>\n");
          Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");Msg.append(freightName);Msg.append("</FONT></TD>\n");
          Msg.append("</TR>\n");
        }
        if (totalrecords < 1) {
          Msg.append("<TD COLSPAN=\"3\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>No record found.</B></FONT></TD>\n");
        }
      }catch (Exception e) {
        Msg.append("<TD COLSPAN=\"3\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>No record found.</B></FONT></TD>\n");
      }finally {
        dbrs.close();
      }
      Msg.append("</TABLE>\n");

      Msg.append("</FORM>");
      Msg.append("</BODY>\n</HTML>\n");
      return Msg;
    } //end of method

    public StringBuffer buildPaymentTermWin( String personnelID, String Cat_Servlet) {
      StringBuffer Msg = new StringBuffer();
      Msg.append(buildHTMLHeader("Search for Payment Terms"));
      Msg.append("<BODY>\n");
      Msg.append(getPageHeader(""));
      Msg.append("<FORM method=\"POST\" name=\"PaymentTermForm\" action=\""+Cat_Servlet+"Session=changePaymentTerm\" onsubmit =\"return auditLoginPassword()\">\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"companyID\" VALUE=\""+db.getClient().toLowerCase()+"\">\n");
      //table
      Msg.append("<TABLE ALIGN=\"CENTER\" BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
      boolean changeOk = false;
      try {
        String query = "select count(*) from user_group_member where user_group_id = 'PaymentTerms' and personnel_id = "+personnelID;
        if (HelpObjs.countQuery(db,query) > 0) {
          changeOk = true;
        }
      }catch (Exception e) {
        changeOk = false;
      }
      //if user is not authorize to change than ask for user name and password of one who can
      if (!changeOk) {
        //message
        Msg.append("<TD WIDTH=\"15%\" COLSPAN=\"2\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
        Msg.append("<B>You are not authorize to change payment term. Please get someone that can or click on 'Close'.</B>\n");
        Msg.append("</TD></TR>\n");
        //login
        Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
        Msg.append("<B>Login ID:</B>\n");
        Msg.append("</TD>\n");
        Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        Msg.append("<INPUT type=\"text\" name=\"loginID\" value=\""+""+"\" SIZE=\"30\">");
        Msg.append("</TD></TR>\n");
        //password
        Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
        Msg.append("<B>Password:</B>\n");
        Msg.append("</TD>\n");
        Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        Msg.append("<INPUT type=\"password\" name=\"password\" value=\""+""+"\" SIZE=\"30\">");
        Msg.append("</TD></TR>\n");
        //buttons
        Msg.append("<TR><TD WIDTH=\"15%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\">\n");
        Msg.append("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Login\" NAME=\"loginB\">\n");
        Msg.append("</TD>\n");
        Msg.append("<TD WIDTH=\"35%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        Msg.append("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onClick= \"closeUserWin()\" NAME=\"closeB\">\n");
        Msg.append("</TD></TR>\n");
      }else {
        //combobox
        Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
        Msg.append("<B>Payment Term:</B>\n");
        Msg.append("</TD>\n");
        Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        Msg.append("<SELECT CLASS=\"HEADER\" NAME=\"paymentTerm\" ID=\"paymentTerm\" size=\"1\">\n");
        //fill approval role drop down
        Vector v = getPaymentTerms();
        String selected = "Select One";
        v.addElement(selected);
        buildComboBox(v,v,selected,Msg,true);
        Msg.append("</SELECT>\n");
        Msg.append("</TD></TR>\n");
        //buttons
        Msg.append("<TR><TD WIDTH=\"15%\" COLSPAN=\"2\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
        Msg.append("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Change\" onClick= \"applyPaymentTerm(opener.document.catAddDetail.paymentTermT)\" NAME=\"changeB\">\n");
        Msg.append("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onClick= \"closeUserWin()\" NAME=\"closeB\">\n");
        Msg.append("</TD></TR>\n");
      }
      Msg.append("</TABLE></FORM>");
      Msg.append("</BODY>\n</HTML>\n");
      return Msg;
    } //end of method

    public long getDate(String myDate,String requestID) {
      Date tempDate = new Date();
      try {
        StringTokenizer st = new StringTokenizer(myDate, "/");
        String month = st.nextElement().toString();
        String date = st.nextElement().toString();
        String year = st.nextElement().toString();
        GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(date));
        tempDate = cal.getTime();
      }catch (Exception e) {
        e.printStackTrace();
        Calendar tmpCal = Calendar.getInstance();
        GregorianCalendar cal = new GregorianCalendar(tmpCal.YEAR+1,tmpCal.MONTH,tmpCal.DATE);
        tempDate = cal.getTime();
        HelpObjs.sendMail(db,"Error in getDate",requestID+"\ndate: "+myDate,86030,false);
      }
      return tempDate.getTime();
    } //end of method

    public String createContractData(HttpServletRequest request, String personnelID, String requestID) {
      String result = "OK";
      Connection connect1 = null;
      CallableStatement cs = null;
      try {
        //first get inventory_group and item_id
        Hashtable requestDataH = getRequestData(requestID);
        //user's data
        String supplierID = "";
        String contactID = "";
        String carrierID = "";
        String contractType = "";
        String freightOnBoardID = "";
        String consignment = "";
        String goodUntil = "";
        String baseline = "";
        String selling = "";
        String supplierPartNumber = "";
        String multipleOf = "";
        String leadTimeDays = "";
        String purchasing = "";
        String uptoQty = "";
        String purchasing2 = "";
        String uptoQty2 = "";
        String purchasing3 = "";
        String uptoQty3 = "";
        String purchasing4 = "";
        String uptoQty4 = "";
        String currencyID = "";
        try{supplierID= request.getParameter("supplierID").toString();}catch (Exception e1){supplierID = "";}
        try{contactID= request.getParameter("contactID").toString();}catch (Exception e1){contactID = "";}
        try{carrierID= request.getParameter("carrierID").toString();}catch (Exception e1){carrierID = "";}
        try{contractType= request.getParameter("contractType").toString();}catch (Exception e1){contractType = "";}
        try{freightOnBoardID= request.getParameter("freightOnBoardID").toString();}catch (Exception e1){freightOnBoardID = "";}
        try{consignment= request.getParameter("consignment").toString();}catch (Exception e1){consignment = "";}
        try{goodUntil= request.getParameter("goodUntil").toString();}catch (Exception e1){goodUntil = "";}
        try{baseline= request.getParameter("baseline").toString();}catch (Exception e1){baseline = "";}
        try{selling= request.getParameter("selling").toString();}catch (Exception e1){selling = "";}
        try{supplierPartNumber= request.getParameter("supplierPartNumber").toString();}catch (Exception e1){supplierPartNumber = "";}
        try{multipleOf= request.getParameter("multipleOf").toString();}catch (Exception e1){multipleOf = "";}
        try{leadTimeDays= request.getParameter("leadTime").toString();}catch (Exception e1){leadTimeDays = "";}
        try{purchasing= request.getParameter("purchasing").toString();}catch (Exception e1){purchasing = "";}
        try{uptoQty= request.getParameter("uptoQuantity").toString();}catch (Exception e1){uptoQty = "";}
        try{purchasing2= request.getParameter("purchasing2").toString();}catch (Exception e1){purchasing2 = "";}
        try{uptoQty2= request.getParameter("uptoQuantity2").toString();}catch (Exception e1){uptoQty2 = "";}
        try{purchasing3= request.getParameter("purchasing3").toString();}catch (Exception e1){purchasing3 = "";}
        try{uptoQty3= request.getParameter("uptoQuantity3").toString();}catch (Exception e1){uptoQty3 = "";}
        try{purchasing4= request.getParameter("purchasing4").toString();}catch (Exception e1){purchasing4 = "";}
        try{uptoQty4= request.getParameter("uptoQuantity4").toString();}catch (Exception e1){uptoQty4 = "";}
        try{currencyID= request.getParameter("currency").toString();}catch (Exception e1){currencyID = "";}
        connect1 = db.getConnection();
        cs = connect1.prepareCall("{call PKG_CONTRACT_SETUP.INSERT_CONTRACT_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
        cs.setInt(1,Integer.parseInt(requestID));
        cs.setInt(2,Integer.parseInt(requestDataH.get("ITEM_ID").toString()));
        cs.setString(3,requestDataH.get("INVENTORY_GROUP").toString());
        cs.setString(4,null);
        cs.setString(5,null);
        cs.setString(6,supplierID);
        //if user does not choose Select One then pass null to procedure
        //this shouldn't happen but just in case
        if ("None".equals(carrierID)) {
          cs.setString(7, null);
        }else {
          cs.setString(7, carrierID);
        }
        cs.setString(8,null);
        cs.setInt(9,Integer.parseInt(personnelID));
        //cs.setString(10,null);         //payment term
        cs.setString(11,contractType);
        cs.setString(12,freightOnBoardID);
        if (multipleOf.length() > 0) {
          cs.setInt(13,Integer.parseInt(multipleOf));
        }else {
          cs.setString(13,null);
        }
        if (supplierPartNumber.length() > 0) {
          cs.setString(14, supplierPartNumber);
        }else {
          cs.setString(14,null);
        }
        cs.setInt(15,Integer.parseInt(contactID));
        cs.setString(16,null);
        cs.setString(17,null);
        cs.setString(18,null);
        cs.setString(19,null);
        cs.setString(20,null);
        if ("No".equalsIgnoreCase(consignment)) {
          cs.setString(21,"N");
        }else {
          cs.setString(21, "Y");
        }
        if (leadTimeDays.length() > 0) {
          cs.setInt(22,Integer.parseInt(leadTimeDays));
        }else {
          cs.setString(22,null);
        }
        cs.setString(23,null);
        cs.setString(24,null);
        long mydate = getDate(goodUntil,requestID);
        cs.setDate(25,new java.sql.Date(mydate));
        if (uptoQty.length() > 0) {
          if (uptoQty2.length() > 0) {
            uptoQty += ","+uptoQty2;
          }
          if (uptoQty3.length() > 0) {
            uptoQty += ","+uptoQty3;
          }
          if (uptoQty4.length() > 0) {
            uptoQty += ","+uptoQty4;
          }
          cs.setString(26, uptoQty);
        }else {
          cs.setString(26,null);
        }
        //item_unit_price
        if (purchasing2.length() > 0) {
          purchasing += ","+purchasing2;
        }
        if (purchasing3.length() > 0) {
          purchasing += ","+purchasing3;
        }
        if (purchasing4.length() > 0) {
          purchasing += ","+purchasing4;
        }
        cs.setString(27,purchasing);
        cs.setString(28,null);
        cs.setString(29,null);
        cs.setString(30,null);
        cs.setString(31,null);
        cs.setString(32,"Cat Add");
        if (currencyID.length() > 0) {
          cs.setString(33,currencyID);
        }else {
          cs.setString(33,null);
        }
        cs.setString(34,"Purchaser");
        cs.registerOutParameter(35, OracleTypes.VARCHAR);
        cs.execute();
        String errorMessage = (String)cs.getObject(35);
        //System.out.println("------- error message: "+errorMessage);
        if (errorMessage == null) {
          result = "OK";
        }else {
          result = errorMessage;
        }
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.sendMail(db,"Error while trying create supplier contact","Creator: "+personnelID,86030,false);
        result = "An error occurred while creating supplier contact.  Please try again.  If the problem recurs, contact your tcmIS Customer Service Representative.";
      }finally{
        try {
          cs.close();
        }catch (Exception ee) {
          ee.printStackTrace();
        }
      }
      return result;
    } //end of method

    public String[] createNewContact(HttpServletRequest request, String personnelID) {
      String[] result = new String[3];
      Connection connect1 = null;
      CallableStatement cs = null;
      try {
        String supplierID = BothHelpObjs.makeBlankFromNull(request.getParameter("supplierID"));
        String nickName = BothHelpObjs.makeBlankFromNull(request.getParameter("nickNameT"));
        String lastName = BothHelpObjs.makeBlankFromNull(request.getParameter("lastNameT"));
        String firstName = BothHelpObjs.makeBlankFromNull(request.getParameter("firstNameT"));
        String phone = BothHelpObjs.makeBlankFromNull(request.getParameter("phoneT"));
        String ext = BothHelpObjs.makeBlankFromNull(request.getParameter("extT"));
        String fax = BothHelpObjs.makeBlankFromNull(request.getParameter("faxT"));
        String email = BothHelpObjs.makeBlankFromNull(request.getParameter("emailT"));

        connect1 = db.getConnection();
        cs = connect1.prepareCall("{call PKG_CONTRACT_SETUP.MODIFY_SUPPLIER_CONTACT(?,?,?,?,?,?,?,?,?,?,?,?)}");
        cs.setString(1,supplierID);
        cs.setString(2,null);
        cs.setString(3,"Contract");
        if (firstName.length() < 1) {
          cs.setString(4,null);
        }else {
          cs.setString(4,firstName);
        }
        if (lastName.length() < 1) {
          cs.setString(5,null);
        }else {
          cs.setString(5,lastName);
        }
        if (nickName.length() < 1) {
          cs.setString(6,null);
        }else {
          cs.setString(6,nickName);
        }
        if (phone.length() < 1) {
          cs.setString(7,null);
        }else {
          cs.setString(7,phone);
        }
        if (ext.length() < 1) {
          cs.setString(8,null);
        }else {
          cs.setString(8,ext);
        }
        if (fax.length() < 1) {
          cs.setString(9,null);
        }else {
          cs.setString(9,fax);
        }
        if (email.length() < 1) {
          cs.setString(10,null);
        }else {
          cs.setString(10,email);
        }
        cs.registerOutParameter(11, OracleTypes.VARCHAR);
        cs.registerOutParameter(12, OracleTypes.NUMBER);
        cs.execute();
        String errorMessage = (String)cs.getObject(11);
        if (errorMessage == null) {
          result[0] = "OK";
        }else {
          result[0] = errorMessage;
        }

        if (lastName.length() > 0) {
          result[1] = lastName;
          result[2] = "LASTNAME";
        }else if (firstName.length() > 0) {
          result[1] = firstName;
          result[2] = "FIRSTNAME";
        }else {
          result[1] = "";
          result[2] = "LASTNAME";
        }
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.sendMail(db,"Error while trying create supplier contact","Creator: "+personnelID,86030,false);
        result[0] = "An error occurred while creating supplier contact.  Please try again.  If the problem recurs, contact your tcmIS Customer Service Representative.";
        result[1] = "";
        result[2] = "LASTNAME";
      }finally{
        try {
          cs.close();
        }catch (Exception ee) {
          ee.printStackTrace();
        }
      }
      return result;
    } //end of method

    public StringBuffer buildNewContactWin(String Cat_Servlet, String supplierID, String errorMessage, HttpServletRequest request) {
      StringBuffer Msg = new StringBuffer();
      Msg.append(buildHTMLHeader("New Contact"));
      Msg.append("<BODY>\n");
      Msg.append(getPageHeader(""));
      Msg.append("<FORM method=\"POST\" name=\"newContactWin\" action=\""+Cat_Servlet+"Session=createNewContact\" onsubmit =\"return auditNewContact()\">\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"companyID\" VALUE=\""+db.getClient().toLowerCase()+"\">\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"supplierID\" VALUE=\""+supplierID+"\">\n");
      //table
      Msg.append("<TABLE ALIGN=\"CENTER\" BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
      String nickName = "";
      String lastName = "";
      String firstName = "";
      String phone = "";
      String ext = "";
      String fax = "";
      String email = "";
      //message
      if(errorMessage.length() > 0) {
        Msg.append("<TR><TD WIDTH=\"40%\" COLSPAN=\"2\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
        Msg.append("<B>"+errorMessage+"</B>\n");
        Msg.append("</TD></TR>\n");
        nickName = BothHelpObjs.makeBlankFromNull(request.getParameter("nickNameT"));
        lastName = BothHelpObjs.makeBlankFromNull(request.getParameter("lastNameT"));
        firstName = BothHelpObjs.makeBlankFromNull(request.getParameter("firstNameT"));
        phone = BothHelpObjs.makeBlankFromNull(request.getParameter("phoneT"));
        ext = BothHelpObjs.makeBlankFromNull(request.getParameter("extT"));
        fax = BothHelpObjs.makeBlankFromNull(request.getParameter("faxT"));
        email = BothHelpObjs.makeBlankFromNull(request.getParameter("emailT"));
      }
      //nick name
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>Nick Name:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"nickNameT\" value=\""+nickName+"\" SIZE=\"30\">");
      Msg.append("</TD></TR>\n");
      //last name
      Msg.append("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>Last Name:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"lastNameT\" value=\""+lastName+"\" SIZE=\"30\">");
      Msg.append("</TD></TR>\n");
      //first name
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>First Name:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"firstNameT\" value=\""+firstName+"\" SIZE=\"30\">");
      Msg.append("</TD></TR>\n");
      //phone
      Msg.append("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>Phone:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"phoneT\" value=\""+phone+"\" SIZE=\"30\">");
      Msg.append("</TD></TR>\n");
      //phone ext
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>Ext:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"extT\" value=\""+ext+"\" SIZE=\"30\">");
      Msg.append("</TD></TR>\n");
      //fax
      Msg.append("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>fax:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"faxT\" value=\""+fax+"\" SIZE=\"30\">");
      Msg.append("</TD></TR>\n");
      //email
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>Email:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"emailT\" value=\""+email+"\" SIZE=\"30\">");
      Msg.append("</TD></TR>\n");

      //buttons
      Msg.append("<TR><TD WIDTH=\"40%\" COLSPAN=\"2\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create Contact\" NAME=\"okB\">\n");
      Msg.append("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onClick= \"closeUserWin()\" NAME=\"closeB\">\n");
      Msg.append("</TD></TR>\n");
      Msg.append("</TABLE></FORM>");
      Msg.append("</BODY>\n</HTML>\n");
      return Msg;
    } //end of method

    public StringBuffer buildContactNameWin( String Cat_Servlet, String supplierID, String searchText, String searchBy) {
      StringBuffer Msg = new StringBuffer();
      Msg.append(buildHTMLHeader("Search for Supplier Contact"));
      Msg.append("<BODY>\n");
      Msg.append(getPageHeader(""));
      Msg.append("<FORM method=\"POST\" name=\"contactNameWin\" action=\""+Cat_Servlet+"\">\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"companyID\" VALUE=\""+db.getClient().toLowerCase()+"\">\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"supplierID\" VALUE=\""+supplierID+"\">\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"Session\" VALUE=\"contactNameWin\">\n");
      //table
      Msg.append("<TABLE ALIGN=\"CENTER\" BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
      Msg.append("<TR><TD WIDTH=\"40%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchText\" ID=\"searchText\" value=\""+searchText+"\" size=\"20\">\n");
      if (searchBy.length() < 1) {
        searchBy = "LASTNAME";
      }
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"searchBy\" VALUE=\""+searchBy+"\">\n");
      Msg.append("<INPUT type=\"checkbox\" onClick= \"lastNameChecked()\" name=\"lastR\" value=\"Yes\" "+("LASTNAME".equalsIgnoreCase(searchBy)?"checked":"")+">\n");
      Msg.append("<B>Last Name</B>\n");
      Msg.append("<INPUT type=\"checkbox\" onClick= \"firstNameChecked()\" name=\"firstR\" value=\"Yes\" "+("FIRSTNAME".equalsIgnoreCase(searchBy)?"checked":"")+">\n");
      Msg.append("<B>First Name</B>\n");
      Msg.append("</TD>\n");
      Msg.append("</TR>\n");
      //buttons
      Msg.append("<TR><TD WIDTH=\"40%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onClick= \"return auditContactSearch()\" NAME=\"searchB\">\n");
      Msg.append("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"New Contact\" onClick= \"return newContactWin()\" NAME=\"newB\">\n");
      Msg.append("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onClick= \"closeUserWin()\" NAME=\"closeB\">\n");
      Msg.append("</TD></TR>\n");
      Msg.append("</TABLE>\n");
      //result table
      Msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"100%\" >\n");
      //table header
      Msg.append("<TR>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Contact Name</B></FONT></TD>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Phone</B></FONT></TD>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Email</B></FONT></TD>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Fax</B></FONT></TD>\n");
      Msg.append("</TR>\n");
      //if (searchText.length() > 0) {
        String query = "select contact_id,last_name||', '||nvl(first_name,'')||decode(nickname,null,'',first_name,'',' ('||nickname||')') contact_name,"+
                       "phone||decode(phone_extension,null,'',' ext. '||phone_extension) phone,nvl(email,' ') email,nvl(fax,' ') fax"+
                       " from supplier_contact where contact_type = 'Contract' and status = 'ACTIVE' and supplier = '"+supplierID+"'";
        if ("LASTNAME".equalsIgnoreCase(searchBy)) {
          query += " and lower(last_name) like lower('%"+searchText+"%')";
        }else {
          query += " and lower(first_name) like lower('%"+searchText+"%')";
        }
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          int totalrecords = 0;
          int count = 0;
          while(rs.next()) {
            totalrecords += 1;
            count += 1;
            String Color = " ";
            if (count%2==0) {
              Color = "bgcolor=\"#E6E8FA\"";
            }else {
              Color = "bgcolor=\"#fcfcfc\"";
            }
            String contactID = rs.getString("contact_id");
            String contactName = rs.getString("contact_name");
            String phone = rs.getString("phone");
            String email = rs.getString("email");
            String fax = rs.getString("fax");

            Msg.append("<TR>\n");
            Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
            Msg.append("<A HREF=\"javascript:getContactID('"+contactID+"','"+contactName+"','"+phone+"')\" STYLE=\"color:#0000ff\">"+contactName+"</A>\n");
            Msg.append("</FONT></TD>\n");
            Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");Msg.append(phone);Msg.append("</FONT></TD>\n");
            Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");Msg.append(email);Msg.append("</FONT></TD>\n");
            Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");Msg.append(fax);Msg.append("</FONT></TD>\n");
            Msg.append("</TR>\n");
          }
          if (totalrecords < 1) {
            Msg.append("<TD COLSPAN=\"4\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>No record found.</B></FONT></TD>\n");
          }
        }catch (Exception e) {
          Msg.append("<TD COLSPAN=\"4\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>No record found.</B></FONT></TD>\n");
        }finally {
          dbrs.close();
        }
      //}
      Msg.append("</TABLE></FORM>");
      Msg.append("</BODY>\n</HTML>\n");
      return Msg;
    } //end of method


    public StringBuffer buildSupplierSearchWin(String Cat_Servlet, String searchText) {
      StringBuffer Msg = new StringBuffer();
      Msg.append(buildHTMLHeader("Search for Supplier"));
      Msg.append("<BODY>\n");
      Msg.append(getPageHeader(""));
      Msg.append("<FORM method=\"POST\" name=\"searchSupplierWin\" action=\""+Cat_Servlet+"Session=searchSupplierWin\" onsubmit =\"return auditSupplierSearch()\">\n");
      Msg.append("<INPUT TYPE=\"hidden\" NAME=\"companyID\" VALUE=\""+db.getClient().toLowerCase()+"\">\n");
      //table
      //search text area
      Msg.append("<TABLE ALIGN=\"CENTER\" BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
      Msg.append("<TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      Msg.append("<B>Search Text:</B>\n");
      Msg.append("</TD>\n");
      Msg.append("<TD WIDTH=\"35%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      Msg.append("<INPUT type=\"text\" name=\"supplierT\" value=\""+searchText+"\" SIZE=\"30\">");
      Msg.append("<B>&nbsp;</B>\n");
      Msg.append("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" NAME=\"searchB\">\n");
      Msg.append("</TD></TR>\n");
      Msg.append("</TABLE>\n");
      //result table
      Msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"100%\" >\n");
      //table header
      Msg.append("<TR>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Supplier Name</B></FONT></TD>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Address</B></FONT></TD>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Phone</B></FONT></TD>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Email</B></FONT></TD>\n");
      Msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>E Supplier ID</B></FONT></TD>\n");
      Msg.append("</TR>\n");
      if (searchText.length() > 0) {
        String query = "select supplier,nvl(supplier_name,' ') supplier_name,nvl(main_phone,' ') main_phone,nvl(supplier_email,nvl(email,' ')) supplier_email,qualification_level,"+
                       "address_line_1||decode(address_line_2,null,'',' '||address_line_2)||' '||city||' '||state_abbrev||' '||zip supplier_address,"+
                       "nvl(e_supplier_id,' ') e_supplier_id,default_payment_terms from supplier_address_view where status = 'Active'"+
                       " and lower(supplier||supplier_name||address_line_1||address_line_2||city||state_abbrev||zip) like lower('%"+searchText+"%')";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          int totalrecords = 0;
          int count = 0;
          while(rs.next()) {
            totalrecords += 1;
            count += 1;
            String Color = " ";
            if (count%2==0) {
              Color = "bgcolor=\"#E6E8FA\"";
            }else {
              Color = "bgcolor=\"#fcfcfc\"";
            }
            String supplierID = rs.getString("supplier");
            String supplierName = rs.getString("supplier_name");
            String address = rs.getString("supplier_address");
            String phone = rs.getString("main_phone");
            String email = rs.getString("supplier_email");
            String eID = rs.getString("e_supplier_id");
            String paymentTerm = rs.getString("default_payment_terms");

            Msg.append("<TR>\n");
            Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
            Msg.append("<A HREF=\"javascript:getSupplierID('"+supplierID+"','"+supplierName+"','"+phone+"','"+paymentTerm+"')\" STYLE=\"color:#0000ff\">"+supplierName+"</A>\n");
            Msg.append("</FONT></TD>\n");
            Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");Msg.append(address);Msg.append("</FONT></TD>\n");
            Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");Msg.append(phone);Msg.append("</FONT></TD>\n");
            Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");Msg.append(email);Msg.append("</FONT></TD>\n");
            Msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");Msg.append(eID);Msg.append("</FONT></TD>\n");
            Msg.append("</TR>\n");
          }
          if (totalrecords < 1) {
            Msg.append("<TD COLSPAN=\"4\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>No record found.</B></FONT></TD>\n");
          }
        }catch (Exception e) {
          Msg.append("<TD COLSPAN=\"4\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>No record found.</B></FONT></TD>\n");
        }finally {
          dbrs.close();
        }
      }
      Msg.append("</TABLE>\n");

      Msg.append("</FORM>");
      Msg.append("</BODY>\n</HTML>\n");
      return Msg;
    } //end of method

} //end of class