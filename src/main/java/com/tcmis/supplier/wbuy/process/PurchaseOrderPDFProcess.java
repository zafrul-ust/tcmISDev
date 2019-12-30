package com.tcmis.supplier.wbuy.process;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import oracle.jdbc.OracleTypes;

import com.actuate.ereport.common.IViewerInterface;
import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbConnectionException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.erw.RegisterTable;
import com.tcmis.supplier.wbuy.appdata.CommentsData;
import com.tcmis.supplier.wbuy.appdata.FlowData;
import com.tcmis.supplier.wbuy.appdata.MoreCommentsData;
import com.tcmis.supplier.wbuy.appdata.PoData;

/**
 * 01-14-03
 * Adding the new PROJECTED_DELIVERY_DATE
 * <p/>
 * 02-06-03
 * Adding buyer email
 * 04-07-03 - changed the view to po_line_confirm_print_view
 * 04-08-03 - aDDED NO SUBSTITUTIONS ALLOWED WHATSOEVER ON THE po
 * 05-21-03 - Cleanup code and removed data from code
 * 06-13-03 - Adding a random number to the PO name and changed code to reflect changes to inventory group stuff
 * 09-03-03 - Changing the shippinf information if the shipper is pre pay and add or vendor truck
 * 09-22-03 - Fax MSDS message bug
 * 10-31-03 - Frieght Billing address adding more comments for conriming order and amended order
 * 11-11-03 - Showing the Third address line for the thier party billing address
 * 11-26-03 - Showing Haas TCM for companyid when it is radian
 * 12-02-03 - Dropsships can have third party billing
 * 12-04-03 - Changes to print Blanket Orders
 * 12-16-03 - Updating a table with the imagename and PO file URL
 * 01-28-04 - Making relative URLs to go to the property file to get hosturl
 * 03-05-04 - Seperating SPECs by ; instead of , as the spec ID can have ,s in them
 * 03-14-04 - Alllowing to print PO line in draft status
 */

public class PurchaseOrderPDFProcess extends BaseProcess {

    private ACJEngine erw = null;
    private ACJOutputProcessor eClient = null;
    private TemplateManager tm = null;
    private Vector Flow_Data = new Vector();
    private String radianItems = "";
    private String msdsmessage = "";
    private String currencyid = "";
    private int COAcount = 0;
    private int lineCount = 1;        //this is for keeping track of line number to display in the line requirement section of the pdf
    private ResourceLibrary tcmisResourceLoader;
    // private ServerResourceBundle bundle = null;
    private ResultSet dbrs = null;


    public PurchaseOrderPDFProcess(String client) {

        super(client);
        erw = null;
        tm = null;
        eClient = null;
        tcmisResourceLoader = new ResourceLibrary("tcmISWebResource");
    }

    // old
    protected Hashtable getAddressInfo_old(String po, String bpo) throws Exception {
        Hashtable AddressInfoh = new Hashtable();

        try {
            int numberRec = 0;
            String poquery = "";
            if (po.length() > 0 && !"N".equalsIgnoreCase(po)) {
                poquery = "Select INVENTORY_GROUP,SUPPLIER_CONTACT_PHONE,RADIAN_PO,BO,EVER_CONFIRMED,SUPPLIER,SUPPLIER_CONTACT_ID,SUPPLIER_CONTACT_NAME,BUYER,BUYER_NAME,SHIP_TO_COMPANY_ID, ";
                poquery += "SHIP_TO_LOCATION_ID,BRANCH_PLANT,FREIGHT_ON_BOARD,PAYMENT_TERMS,CARRIER,CARRIER_ACCOUNT,CUSTOMER_PO, ";
                poquery += "to_char(DATE_SENT,'mm/dd/yyyy') DATE_SENT,to_char(DATE_ACCEPTED,'mm/dd/yyyy') DATE_ACCEPTED,PO_TERMS_AND_CONDITIONS, ";
                poquery += "to_char(BO_START_DATE,'mm/dd/yyyy') BO_START_DATE,to_char(BO_END_DATE,'mm/dd/yyyy') BO_END_DATE, ";
                poquery += "SUPPLIER_NAME,SUPPLIER_CONTACT_NAME,CARRIER_COMPANY_ID,CARRIER_ACCOUNT,CARRIER_NAME,CARRIER_HUB,SUPPLIER_COUNTRY_ABBREV,SUPPLIER_STATE_ABBREV, ";
                poquery += "SUPPLIER_ADDRESS_LINE_1,SUPPLIER_ADDRESS_LINE_2,SUPPLIER_ADDRESS_LINE_3,SUPPLIER_CITY,SUPPLIER_ZIP,SUPPLIER_LOCATION_DESC,SHIPTO_COUNTRY_ABBREV, ";
                poquery += "SUPPLIER_PHONE,SUPPLIER_CONTACT_FAX,SHIPTO_STATE_ABBREV,SHIPTO_ADDRESS_LINE_1,SHIPTO_ADDRESS_LINE_2,SHIPTO_ADDRESS_LINE_3,SHIPTO_CITY,SHIPTO_ZIP,SHIPTO_LOCATION_DESC ";
                poquery += "from po_header_view where radian_po = " + po + " ";
            } else {
                poquery = "Select '' INVENTORY_GROUP,SUPPLIER_CONTACT_PHONE,'' RADIAN_PO,BPO BO,EVER_CONFIRMED,SUPPLIER,SUPPLIER_CONTACT_ID,SUPPLIER_CONTACT_NAME,BUYER,BUYER_NAME,'' SHIP_TO_COMPANY_ID, ";
                poquery += "'' SHIP_TO_LOCATION_ID,'' BRANCH_PLANT,FREIGHT_ON_BOARD,PAYMENT_TERMS,'' CARRIER,'' CARRIER_ACCOUNT,'' CUSTOMER_PO, ";
                poquery += "to_char(START_DATE,'mm/dd/yyyy') DATE_SENT,to_char(END_DATE,'mm/dd/yyyy') DATE_ACCEPTED,PO_TERMS_AND_CONDITIONS, ";
                poquery += "to_char(START_DATE,'mm/dd/yyyy') BO_START_DATE,to_char(END_DATE,'mm/dd/yyyy') BO_END_DATE, ";
                poquery += "SUPPLIER_NAME,SUPPLIER_CONTACT_NAME, '' CARRIER_COMPANY_ID,'' CARRIER_ACCOUNT,'' CARRIER_NAME,'' CARRIER_HUB,SUPPLIER_COUNTRY_ABBREV,SUPPLIER_STATE_ABBREV, ";
                poquery += "SUPPLIER_ADDRESS_LINE_1,SUPPLIER_ADDRESS_LINE_2,SUPPLIER_ADDRESS_LINE_3,SUPPLIER_CITY,SUPPLIER_ZIP,SUPPLIER_LOCATION_DESC,SHIPTO_COUNTRY_ABBREV, ";
                poquery += "SUPPLIER_PHONE,SUPPLIER_CONTACT_FAX,'' SHIPTO_STATE_ABBREV,'' SHIPTO_ADDRESS_LINE_1,'' SHIPTO_ADDRESS_LINE_2,'' SHIPTO_ADDRESS_LINE_3,'' SHIPTO_CITY,'' SHIPTO_ZIP,'' SHIPTO_LOCATION_DESC ";
                poquery += "from bpo_header_view where bpo = " + bpo + " ";
            }

            dbrs = doQuery(poquery);

            while (dbrs.next()) {
                //INVENTORY_GROUP
                AddressInfoh.put("INVENTORY_GROUP", dbrs.getString("INVENTORY_GROUP") == null ? "" : dbrs.getString("INVENTORY_GROUP").trim());
                //SUPPLIER_CONTACT_PHONE
                AddressInfoh.put("SUPPLIER_CONTACT_PHONE", dbrs.getString("SUPPLIER_CONTACT_PHONE") == null ? "" : dbrs.getString("SUPPLIER_CONTACT_PHONE").trim());
                //SUPPLIER_CONTACT_FAX
                AddressInfoh.put("SUPPLIER_CONTACT_FAX", dbrs.getString("SUPPLIER_CONTACT_FAX") == null ? "" : dbrs.getString("SUPPLIER_CONTACT_FAX").trim());
                //RADIAN_PO
                AddressInfoh.put("RADIAN_PO", dbrs.getString("RADIAN_PO") == null ? "" : dbrs.getString("RADIAN_PO"));
                //dbrs
                AddressInfoh.put("ORIGINAL_ORDER_NUM", dbrs.getString("BO") == null ? "" : dbrs.getString("BO"));
                //SUPPLIER
                AddressInfoh.put("SUPPLIER", dbrs.getString("SUPPLIER") == null ? "" : dbrs.getString("SUPPLIER"));
                //SUPPLIER_CONTACT_ID
                AddressInfoh.put("SUPPLIER_CONTACT_ID", dbrs.getString("SUPPLIER_CONTACT_ID") == null ? "" : dbrs.getString("SUPPLIER_CONTACT_ID"));
                //BUYER
                AddressInfoh.put("BUYER", dbrs.getString("BUYER") == null ? "" : dbrs.getString("BUYER"));
                //SHIP_TO_COMPANY_ID
                AddressInfoh.put("SHIP_TO_COMPANY_ID", dbrs.getString("SHIP_TO_COMPANY_ID") == null ? "" : dbrs.getString("SHIP_TO_COMPANY_ID"));
                //SHIP_TO_LOCATION_ID
                AddressInfoh.put("SHIP_TO_LOCATION_ID", dbrs.getString("SHIP_TO_LOCATION_ID") == null ? "" : dbrs.getString("SHIP_TO_LOCATION_ID"));
                //BRANCH_PLANT
                AddressInfoh.put("branch_plant", dbrs.getString("BRANCH_PLANT") == null ? "" : dbrs.getString("BRANCH_PLANT"));
                //FREIGHT_ON_BOARD
                AddressInfoh.put("FREIGHT_ON_BOARD", dbrs.getString("FREIGHT_ON_BOARD") == null ? "" : dbrs.getString("FREIGHT_ON_BOARD"));
                //PAYMENT_TERMS
                AddressInfoh.put("PAYMENT_TERMS", dbrs.getString("PAYMENT_TERMS") == null ? "" : dbrs.getString("PAYMENT_TERMS"));
                //CARRIER
                AddressInfoh.put("CARRIER", dbrs.getString("CARRIER") == null ? "" : dbrs.getString("CARRIER"));
                //DATE_SENT
                AddressInfoh.put("DATE_SENT", dbrs.getString("DATE_SENT") == null ? "" : dbrs.getString("DATE_SENT"));
                //DATE_ACCEPTED
                AddressInfoh.put("DATE_ACCEPTED", dbrs.getString("DATE_ACCEPTED") == null ? "" : dbrs.getString("DATE_ACCEPTED"));
                //TERMS_AND_CONDITIONS
                AddressInfoh.put("TERMS_AND_CONDITIONS", dbrs.getString("PO_TERMS_AND_CONDITIONS") == null ? "" : dbrs.getString("PO_TERMS_AND_CONDITIONS"));
                //BO_START_DATE
                AddressInfoh.put("BO_START_DATE", dbrs.getString("BO_START_DATE") == null ? "" : dbrs.getString("BO_START_DATE"));
                //BO_END_DATE
                AddressInfoh.put("BO_END_DATE", dbrs.getString("BO_END_DATE") == null ? "" : dbrs.getString("BO_END_DATE"));
                //CUSTOMER_PO
                AddressInfoh.put("CUSTOMER_PO", dbrs.getString("CUSTOMER_PO") == null ? "" : dbrs.getString("CUSTOMER_PO"));
                //SUPPLIER_NAME
                AddressInfoh.put("vendor_name", dbrs.getString("SUPPLIER_NAME") == null ? "" : dbrs.getString("SUPPLIER_NAME"));
                //SUPPLIER_CONTACT_NAME
                AddressInfoh.put("SUPPLIER_CONTACT_NAME", dbrs.getString("SUPPLIER_CONTACT_NAME") == null ? "" : dbrs.getString("SUPPLIER_CONTACT_NAME"));
                //BUYER_NAME
                AddressInfoh.put("buyer", dbrs.getString("BUYER_NAME") == null ? "" : dbrs.getString("BUYER_NAME"));
                //EVER_CONFIRMED
                AddressInfoh.put("EVER_CONFIRMED", dbrs.getString("EVER_CONFIRMED") == null ? "" : dbrs.getString("EVER_CONFIRMED"));
                //CARRIER_COMPANY_ID
                AddressInfoh.put("CARRIER_COMPANY_ID", dbrs.getString("CARRIER_COMPANY_ID") == null ? "" : dbrs.getString("CARRIER_COMPANY_ID"));
                //CARRIER_ACCOUNT
                AddressInfoh.put("CARRIER_ACCOUNT", dbrs.getString("CARRIER_ACCOUNT") == null ? "" : dbrs.getString("CARRIER_ACCOUNT"));
                //CARRIER_NAME
                AddressInfoh.put("CARRIER_NAME", dbrs.getString("CARRIER_NAME") == null ? "" : dbrs.getString("CARRIER_NAME"));
                //CARRIER_HUB
                AddressInfoh.put("CARRIER_HUB", dbrs.getString("CARRIER_HUB") == null ? "" : dbrs.getString("CARRIER_HUB"));
                //SUPPLIER_COUNTRY_ABBREV
                AddressInfoh.put("SUPPLIER_COUNTRY_ABBREV", dbrs.getString("SUPPLIER_COUNTRY_ABBREV") == null ? "" : dbrs.getString("SUPPLIER_COUNTRY_ABBREV"));
                //SUPPLIER_STATE_ABBREV
                AddressInfoh.put("vendor_state", dbrs.getString("SUPPLIER_STATE_ABBREV") == null ? "" : dbrs.getString("SUPPLIER_STATE_ABBREV"));
                //SUPPLIER_ADDRESS_LINE_1
                AddressInfoh.put("vendor_address1", dbrs.getString("SUPPLIER_ADDRESS_LINE_1") == null ? "" : dbrs.getString("SUPPLIER_ADDRESS_LINE_1"));
                //SUPPLIER_ADDRESS_LINE_2
                AddressInfoh.put("vendor_address2", dbrs.getString("SUPPLIER_ADDRESS_LINE_2") == null ? "" : dbrs.getString("SUPPLIER_ADDRESS_LINE_2"));
                //SUPPLIER_ADDRESS_LINE_3
                AddressInfoh.put("vendor_address3", dbrs.getString("SUPPLIER_ADDRESS_LINE_3") == null ? "" : dbrs.getString("SUPPLIER_ADDRESS_LINE_3"));
                //SUPPLIER_CITY
                AddressInfoh.put("vendor_city", dbrs.getString("SUPPLIER_CITY") == null ? "" : dbrs.getString("SUPPLIER_CITY"));
                //SUPPLIER_ZIP
                AddressInfoh.put("vendor_zipcode", dbrs.getString("SUPPLIER_ZIP") == null ? "" : dbrs.getString("SUPPLIER_ZIP"));
                //SUPPLIER_LOCATION_DESC
                AddressInfoh.put("SUPPLIER_LOCATION_DESC", dbrs.getString("SUPPLIER_LOCATION_DESC") == null ? "" : dbrs.getString("SUPPLIER_LOCATION_DESC"));
                //SHIPTO_COUNTRY_ABBREV
                AddressInfoh.put("SHIPTO_COUNTRY_ABBREV", dbrs.getString("SHIPTO_COUNTRY_ABBREV") == null ? "" : dbrs.getString("SHIPTO_COUNTRY_ABBREV"));
                //SHIPTO_STATE_ABBREV
                AddressInfoh.put("shipto_state", dbrs.getString("SHIPTO_STATE_ABBREV") == null ? "" : dbrs.getString("SHIPTO_STATE_ABBREV"));
                //SHIPTO_ADDRESS_LINE_1
                AddressInfoh.put("shipto_address1", dbrs.getString("SHIPTO_ADDRESS_LINE_1") == null ? "" : dbrs.getString("SHIPTO_ADDRESS_LINE_1"));
                //SHIPTO_ADDRESS_LINE_2
                AddressInfoh.put("shipto_address2", dbrs.getString("SHIPTO_ADDRESS_LINE_2") == null ? "" : dbrs.getString("SHIPTO_ADDRESS_LINE_2"));
                //SHIPTO_ADDRESS_LINE_3
                AddressInfoh.put("shipto_address3", dbrs.getString("SHIPTO_ADDRESS_LINE_3") == null ? "" : dbrs.getString("SHIPTO_ADDRESS_LINE_3"));
                //SHIPTO_CITY
                AddressInfoh.put("shipto_city", dbrs.getString("SHIPTO_CITY") == null ? "" : dbrs.getString("SHIPTO_CITY"));
                //SHIPTO_ZIP
                AddressInfoh.put("shipto_zip", dbrs.getString("SHIPTO_ZIP") == null ? "" : dbrs.getString("SHIPTO_ZIP"));
                //SHIPTO_LOCATION_DESC
                AddressInfoh.put("shipto_company", dbrs.getString("SHIPTO_LOCATION_DESC") == null ? "" : dbrs.getString("SHIPTO_LOCATION_DESC"));
                //SUPPLIER_PHONE
                AddressInfoh.put("SUPPLIER_PHONE", dbrs.getString("SUPPLIER_PHONE") == null ? "" : dbrs.getString("SUPPLIER_PHONE"));

                numberRec += 1;
            }
        }
        catch (SQLException sqle) {
            log.error("Error InSQL AddressInfo:");
            sqle.printStackTrace();
            throw sqle;
        }
        finally {
            if (dbrs != null) {
                dbrs.close();
            }
        }
        return AddressInfoh;
    }

    // new

    protected Hashtable getAddressInfo(String po, String bpo) throws Exception {
        Hashtable AddressInfoh = new Hashtable();
        //DBResultSet dbrs = null;
        try {
            int numberRec = 0;
            String poquery = "";

            if (po.length() > 0 && !"N".equalsIgnoreCase(po)) {
                poquery = "Select BILL_TO_COMPANY_ID, BILL_TO_LOCATION_ID, OPERATING_ENTITY_SHORT_NAME,PO_IMAGE_URL, HOME_COMPANY_NAME,INVENTORY_GROUP,SUPPLIER_CONTACT_PHONE,RADIAN_PO,BO,EVER_CONFIRMED,SUPPLIER,SUPPLIER_CONTACT_ID,SUPPLIER_CONTACT_NAME,BUYER,BUYER_NAME,SHIP_TO_COMPANY_ID, ";
                poquery += "SHIP_TO_LOCATION_ID,BRANCH_PLANT,FREIGHT_ON_BOARD,PAYMENT_TERMS,CARRIER,CARRIER_ACCOUNT,CUSTOMER_PO, ";
                poquery += "to_char(DATE_SENT,'mm/dd/yyyy') DATE_SENT,to_char(DATE_ACCEPTED,'mm/dd/yyyy') DATE_ACCEPTED,PO_TERMS_AND_CONDITIONS, ";
                poquery += "to_char(BO_START_DATE,'mm/dd/yyyy') BO_START_DATE,to_char(BO_END_DATE,'mm/dd/yyyy') BO_END_DATE, ";
                poquery += "SUPPLIER_NAME,SUPPLIER_CONTACT_NAME,CARRIER_COMPANY_ID,CARRIER_ACCOUNT,CARRIER_NAME,CARRIER_HUB,SUPPLIER_COUNTRY_ABBREV,SUPPLIER_STATE_ABBREV, ";
                poquery += "SUPPLIER_ADDRESS_LINE_1,SUPPLIER_ADDRESS_LINE_2,SUPPLIER_ADDRESS_LINE_3,SUPPLIER_CITY,SUPPLIER_ZIP,SUPPLIER_LOCATION_DESC,SHIPTO_COUNTRY_ABBREV, ";
                poquery += "SUPPLIER_PHONE,SUPPLIER_CONTACT_FAX,SHIPTO_STATE_ABBREV,SHIPTO_ADDRESS_LINE_1,SHIPTO_ADDRESS_LINE_2,SHIPTO_ADDRESS_LINE_3,SHIPTO_CITY,SHIPTO_ZIP,SHIPTO_LOCATION_DESC ";
                poquery += "from po_header_view where radian_po = " + po + " ";
            } else {
                poquery = "Select BILL_TO_COMPANY_ID, BILL_TO_LOCATION_ID, OPERATING_ENTITY_SHORT_NAME,PO_IMAGE_URL, HOME_COMPANY_NAME,'' INVENTORY_GROUP,SUPPLIER_CONTACT_PHONE,'' RADIAN_PO,BPO BO,EVER_CONFIRMED,SUPPLIER,SUPPLIER_CONTACT_ID,SUPPLIER_CONTACT_NAME,BUYER,BUYER_NAME,'' SHIP_TO_COMPANY_ID, ";
                poquery += "'' SHIP_TO_LOCATION_ID,'' BRANCH_PLANT,FREIGHT_ON_BOARD,PAYMENT_TERMS,'' CARRIER,'' CARRIER_ACCOUNT,'' CUSTOMER_PO, ";
                poquery += "to_char(START_DATE,'mm/dd/yyyy') DATE_SENT,to_char(END_DATE,'mm/dd/yyyy') DATE_ACCEPTED,PO_TERMS_AND_CONDITIONS, ";
                poquery += "to_char(START_DATE,'mm/dd/yyyy') BO_START_DATE,to_char(END_DATE,'mm/dd/yyyy') BO_END_DATE, ";
                poquery += "SUPPLIER_NAME,SUPPLIER_CONTACT_NAME, '' CARRIER_COMPANY_ID,'' CARRIER_ACCOUNT,'' CARRIER_NAME,'' CARRIER_HUB,SUPPLIER_COUNTRY_ABBREV,SUPPLIER_STATE_ABBREV, ";
                poquery += "SUPPLIER_ADDRESS_LINE_1,SUPPLIER_ADDRESS_LINE_2,SUPPLIER_ADDRESS_LINE_3,SUPPLIER_CITY,SUPPLIER_ZIP,SUPPLIER_LOCATION_DESC,SHIPTO_COUNTRY_ABBREV, ";
                poquery += "SUPPLIER_PHONE,SUPPLIER_CONTACT_FAX,'' SHIPTO_STATE_ABBREV,'' SHIPTO_ADDRESS_LINE_1,'' SHIPTO_ADDRESS_LINE_2,'' SHIPTO_ADDRESS_LINE_3,'' SHIPTO_CITY,'' SHIPTO_ZIP,'' SHIPTO_LOCATION_DESC ";
                poquery += "from bpo_header_view where bpo = " + bpo + " ";
            }

            dbrs = doQuery(poquery);
            // ResultSet dbrs=dbrs.getResultSet();

            while (dbrs.next()) {
                //INVENTORY_GROUP
                AddressInfoh.put("INVENTORY_GROUP", dbrs.getString("INVENTORY_GROUP") == null ? "" : dbrs.getString("INVENTORY_GROUP").trim());
                //SUPPLIER_CONTACT_PHONE
                AddressInfoh.put("SUPPLIER_CONTACT_PHONE", dbrs.getString("SUPPLIER_CONTACT_PHONE") == null ? "" : dbrs.getString("SUPPLIER_CONTACT_PHONE").trim());
                //SUPPLIER_CONTACT_FAX
                AddressInfoh.put("SUPPLIER_CONTACT_FAX", dbrs.getString("SUPPLIER_CONTACT_FAX") == null ? "" : dbrs.getString("SUPPLIER_CONTACT_FAX").trim());
                //RADIAN_PO
                AddressInfoh.put("RADIAN_PO", dbrs.getString("RADIAN_PO") == null ? "" : dbrs.getString("RADIAN_PO"));
                //BO
                AddressInfoh.put("ORIGINAL_ORDER_NUM", dbrs.getString("BO") == null ? "" : dbrs.getString("BO"));
                //SUPPLIER
                AddressInfoh.put("SUPPLIER", dbrs.getString("SUPPLIER") == null ? "" : dbrs.getString("SUPPLIER"));
                //SUPPLIER_CONTACT_ID
                AddressInfoh.put("SUPPLIER_CONTACT_ID", dbrs.getString("SUPPLIER_CONTACT_ID") == null ? "" : dbrs.getString("SUPPLIER_CONTACT_ID"));
                //BUYER
                AddressInfoh.put("BUYER", dbrs.getString("BUYER") == null ? "" : dbrs.getString("BUYER"));
                //SHIP_TO_COMPANY_ID
                AddressInfoh.put("SHIP_TO_COMPANY_ID", dbrs.getString("SHIP_TO_COMPANY_ID") == null ? "" : dbrs.getString("SHIP_TO_COMPANY_ID"));
                //SHIP_TO_LOCATION_ID
                AddressInfoh.put("SHIP_TO_LOCATION_ID", dbrs.getString("SHIP_TO_LOCATION_ID") == null ? "" : dbrs.getString("SHIP_TO_LOCATION_ID"));
                //BRANCH_PLANT
                AddressInfoh.put("branch_plant", dbrs.getString("BRANCH_PLANT") == null ? "" : dbrs.getString("BRANCH_PLANT"));
                //FREIGHT_ON_BOARD
                AddressInfoh.put("FREIGHT_ON_BOARD", dbrs.getString("FREIGHT_ON_BOARD") == null ? "" : dbrs.getString("FREIGHT_ON_BOARD"));
                //PAYMENT_TERMS
                AddressInfoh.put("PAYMENT_TERMS", dbrs.getString("PAYMENT_TERMS") == null ? "" : dbrs.getString("PAYMENT_TERMS"));
                //CARRIER
                AddressInfoh.put("CARRIER", dbrs.getString("CARRIER") == null ? "" : dbrs.getString("CARRIER"));
                //DATE_SENT
                AddressInfoh.put("DATE_SENT", dbrs.getString("DATE_SENT") == null ? "" : dbrs.getString("DATE_SENT"));
                //DATE_ACCEPTED
                AddressInfoh.put("DATE_ACCEPTED", dbrs.getString("DATE_ACCEPTED") == null ? "" : dbrs.getString("DATE_ACCEPTED"));
                //TERMS_AND_CONDITIONS
                AddressInfoh.put("TERMS_AND_CONDITIONS", dbrs.getString("PO_TERMS_AND_CONDITIONS") == null ? "" : dbrs.getString("PO_TERMS_AND_CONDITIONS"));
                //BO_START_DATE
                AddressInfoh.put("BO_START_DATE", dbrs.getString("BO_START_DATE") == null ? "" : dbrs.getString("BO_START_DATE"));
                //BO_END_DATE
                AddressInfoh.put("BO_END_DATE", dbrs.getString("BO_END_DATE") == null ? "" : dbrs.getString("BO_END_DATE"));
                //CUSTOMER_PO
                AddressInfoh.put("CUSTOMER_PO", dbrs.getString("CUSTOMER_PO") == null ? "" : dbrs.getString("CUSTOMER_PO"));
                //SUPPLIER_NAME
                AddressInfoh.put("vendor_name", dbrs.getString("SUPPLIER_NAME") == null ? "" : dbrs.getString("SUPPLIER_NAME"));
                //SUPPLIER_CONTACT_NAME
                AddressInfoh.put("SUPPLIER_CONTACT_NAME", dbrs.getString("SUPPLIER_CONTACT_NAME") == null ? "" : dbrs.getString("SUPPLIER_CONTACT_NAME"));
                //BUYER_NAME
                AddressInfoh.put("buyer", dbrs.getString("BUYER_NAME") == null ? "" : dbrs.getString("BUYER_NAME"));
                //EVER_CONFIRMED
                AddressInfoh.put("EVER_CONFIRMED", dbrs.getString("EVER_CONFIRMED") == null ? "" : dbrs.getString("EVER_CONFIRMED"));
                //CARRIER_COMPANY_ID
                AddressInfoh.put("CARRIER_COMPANY_ID", dbrs.getString("CARRIER_COMPANY_ID") == null ? "" : dbrs.getString("CARRIER_COMPANY_ID"));
                //CARRIER_ACCOUNT
                AddressInfoh.put("CARRIER_ACCOUNT", dbrs.getString("CARRIER_ACCOUNT") == null ? "" : dbrs.getString("CARRIER_ACCOUNT"));
                //CARRIER_NAME
                AddressInfoh.put("CARRIER_NAME", dbrs.getString("CARRIER_NAME") == null ? "" : dbrs.getString("CARRIER_NAME"));
                //CARRIER_HUB
                AddressInfoh.put("CARRIER_HUB", dbrs.getString("CARRIER_HUB") == null ? "" : dbrs.getString("CARRIER_HUB"));
                //SUPPLIER_COUNTRY_ABBREV
                AddressInfoh.put("SUPPLIER_COUNTRY_ABBREV", dbrs.getString("SUPPLIER_COUNTRY_ABBREV") == null ? "" : dbrs.getString("SUPPLIER_COUNTRY_ABBREV"));
                //SUPPLIER_STATE_ABBREV
                AddressInfoh.put("vendor_state", dbrs.getString("SUPPLIER_STATE_ABBREV") == null ? "" : dbrs.getString("SUPPLIER_STATE_ABBREV"));
                //SUPPLIER_ADDRESS_LINE_1
                AddressInfoh.put("vendor_address1", dbrs.getString("SUPPLIER_ADDRESS_LINE_1") == null ? "" : dbrs.getString("SUPPLIER_ADDRESS_LINE_1"));
                //SUPPLIER_ADDRESS_LINE_2
                AddressInfoh.put("vendor_address2", dbrs.getString("SUPPLIER_ADDRESS_LINE_2") == null ? "" : dbrs.getString("SUPPLIER_ADDRESS_LINE_2"));
                //SUPPLIER_ADDRESS_LINE_3
                AddressInfoh.put("vendor_address3", dbrs.getString("SUPPLIER_ADDRESS_LINE_3") == null ? "" : dbrs.getString("SUPPLIER_ADDRESS_LINE_3"));
                //SUPPLIER_CITY
                AddressInfoh.put("vendor_city", dbrs.getString("SUPPLIER_CITY") == null ? "" : dbrs.getString("SUPPLIER_CITY"));
                //SUPPLIER_ZIP
                AddressInfoh.put("vendor_zipcode", dbrs.getString("SUPPLIER_ZIP") == null ? "" : dbrs.getString("SUPPLIER_ZIP"));
                //SUPPLIER_LOCATION_DESC
                AddressInfoh.put("SUPPLIER_LOCATION_DESC", dbrs.getString("SUPPLIER_LOCATION_DESC") == null ? "" : dbrs.getString("SUPPLIER_LOCATION_DESC"));
                //SHIPTO_COUNTRY_ABBREV
                AddressInfoh.put("SHIPTO_COUNTRY_ABBREV", dbrs.getString("SHIPTO_COUNTRY_ABBREV") == null ? "" : dbrs.getString("SHIPTO_COUNTRY_ABBREV"));
                //SHIPTO_STATE_ABBREV
                AddressInfoh.put("shipto_state", dbrs.getString("SHIPTO_STATE_ABBREV") == null ? "" : dbrs.getString("SHIPTO_STATE_ABBREV"));
                //SHIPTO_ADDRESS_LINE_1
                AddressInfoh.put("shipto_address1", dbrs.getString("SHIPTO_ADDRESS_LINE_1") == null ? "" : dbrs.getString("SHIPTO_ADDRESS_LINE_1"));
                //SHIPTO_ADDRESS_LINE_2
                AddressInfoh.put("shipto_address2", dbrs.getString("SHIPTO_ADDRESS_LINE_2") == null ? "" : dbrs.getString("SHIPTO_ADDRESS_LINE_2"));
                //SHIPTO_ADDRESS_LINE_3
                AddressInfoh.put("shipto_address3", dbrs.getString("SHIPTO_ADDRESS_LINE_3") == null ? "" : dbrs.getString("SHIPTO_ADDRESS_LINE_3"));
                //SHIPTO_CITY
                AddressInfoh.put("shipto_city", dbrs.getString("SHIPTO_CITY") == null ? "" : dbrs.getString("SHIPTO_CITY"));
                //SHIPTO_ZIP
                AddressInfoh.put("shipto_zip", dbrs.getString("SHIPTO_ZIP") == null ? "" : dbrs.getString("SHIPTO_ZIP"));
                //SHIPTO_LOCATION_DESC
                AddressInfoh.put("shipto_company", dbrs.getString("SHIPTO_LOCATION_DESC") == null ? "" : dbrs.getString("SHIPTO_LOCATION_DESC"));
                //SUPPLIER_PHONE
                AddressInfoh.put("SUPPLIER_PHONE", dbrs.getString("SUPPLIER_PHONE") == null ? "" : dbrs.getString("SUPPLIER_PHONE"));
                //BILL_TO_COMPANY_ID
                AddressInfoh.put("BILL_TO_COMPANY_ID", dbrs.getString("BILL_TO_COMPANY_ID") == null ? "" : dbrs.getString("BILL_TO_COMPANY_ID"));
                //BILL_TO_LOCATION_ID
                AddressInfoh.put("BILL_TO_LOCATION_ID", dbrs.getString("BILL_TO_LOCATION_ID") == null ? "" : dbrs.getString("BILL_TO_LOCATION_ID"));
                //OPERATING_ENTITY_SHORT_NAME
                AddressInfoh.put("OPERATING_ENTITY_SHORT_NAME", dbrs.getString("OPERATING_ENTITY_SHORT_NAME") == null ? "" : dbrs.getString("OPERATING_ENTITY_SHORT_NAME"));
                //PO_IMAGE_URL
                AddressInfoh.put("PO_IMAGE_URL", dbrs.getString("PO_IMAGE_URL") == null ? "" : dbrs.getString("PO_IMAGE_URL"));
                //HOME_COMPANY_NAME
                AddressInfoh.put("HOME_COMPANY_NAME", dbrs.getString("HOME_COMPANY_NAME") == null ? "" : dbrs.getString("HOME_COMPANY_NAME"));

                numberRec += 1;
            }
            //dbrs.close();
        }
        catch (SQLException sqle) {
            log.error("Error InSQL AddressInfo:");
            sqle.printStackTrace();
            throw sqle;
        }
        finally {
            if (dbrs != null) {
                dbrs.close();
            }
        }
        return AddressInfoh;
    }

    //old

    protected Hashtable getPoInfo_old(String po1, String bpo, Connection connection2, Vector addchargeslist, String HubNumber,

                                      String ShipTo, String shiptoloccompanyId, String bpoenddate, String invengrp, String headerNote) throws Exception {

        Vector poInfo = new Vector();

        Hashtable PoInfoh = new Hashtable();

        Hashtable FinalPoInfoh = new Hashtable();

        Vector specFlowv = new Vector();

        Hashtable specflowd = new Hashtable();


        float order_total1 = 0;

        float order_total12 = 0;

        DecimalFormat lineTotal = new DecimalFormat("####.0000");

        DecimalFormat poTotal = new DecimalFormat("####.00");

        boolean creatingblanketpo1 = false;


        try {

            int numberRec = 0;

            int count = 0;


            String polinedetail = "";

            boolean printCanceledLine = false;


            if (po1.length() > 0 && !"N".equalsIgnoreCase(po1) && "0".equalsIgnoreCase(headerNote)) {

                polinedetail += "select to_char(VENDOR_SHIP_DATE,'mm/dd/yyyy') VENDOR_SHIP_DATE,RADIAN_PO,ITEM_TYPE,PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(NEED_DATE,'mm/dd/yyyy') NEED_DATE,to_char(PROMISED_DATE,'mm/dd/yyyy') PROMISED_DATE, ";

                polinedetail += "ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";

                polinedetail += "PR_SHIP_TO,PR_COMPANY_ID,REMAINING_SHELF_LIFE_PERCENT,MSDS_REQUESTED_DATE,GENERIC_COC,GENERIC_COA,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,PO_LINE_NOTE,ITEM_DESC,PACKAGING, ";

                polinedetail += "PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED from po_line_detail_view where RADIAN_PO = " + po1 + " order by PO_LINE asc"; //po_line_confirm_view

            } else if (po1.length() > 0 && !"N".equalsIgnoreCase(po1)) {

                polinedetail += "select to_char(VENDOR_SHIP_DATE,'mm/dd/yyyy') VENDOR_SHIP_DATE,RADIAN_PO,ITEM_TYPE,PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(NEED_DATE,'mm/dd/yyyy') NEED_DATE,to_char(PROMISED_DATE,'mm/dd/yyyy') PROMISED_DATE, ";

                polinedetail += "ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";

                polinedetail += "PR_SHIP_TO,PR_COMPANY_ID,REMAINING_SHELF_LIFE_PERCENT,MSDS_REQUESTED_DATE,GENERIC_COC,GENERIC_COA,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,PO_LINE_NOTE,ITEM_DESC,PACKAGING, ";

                polinedetail += "PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED from po_line_confirm_print_view where RADIAN_PO = " + po1 + " order by PO_LINE asc"; //po_line_confirm_view

            } else {

                creatingblanketpo1 = true;

                polinedetail += "select '' VENDOR_SHIP_DATE,BPO RADIAN_PO,ITEM_TYPE,BPO_LINE PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,'' NEED_DATE,'" + bpoenddate + "' PROMISED_DATE, ";

                polinedetail += "ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,'' DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";

                polinedetail += "'' PR_SHIP_TO,'' PR_COMPANY_ID,REMAINING_SHELF_LIFE_PERCENT,MSDS_REQUESTED_DATE,GENERIC_COC,GENERIC_COA,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,BPO_LINE_NOTE PO_LINE_NOTE,ITEM_DESC,PACKAGING, ";

                polinedetail += "PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED from bpo_line_confirm_view where BPO = " + bpo + " order by BPO_LINE asc";

            }


            dbrs = doQuery(polinedetail);


            while (dbrs.next()) {

                PoInfoh = new Hashtable();

                count++;


                String quantity = dbrs.getString("QUANTITY") == null ? "" : dbrs.getString("QUANTITY").trim();

                String unit_price = dbrs.getString("UNIT_PRICE") == null ? "" : dbrs.getString("UNIT_PRICE").trim();

                String item_type = dbrs.getString("ITEM_TYPE") == null ? "" : dbrs.getString("ITEM_TYPE").trim();

                String qtyreceived = dbrs.getString("QUANTITY_RECEIVED") == null ? "" : dbrs.getString("QUANTITY_RECEIVED").trim();

                String item_id = dbrs.getString("ITEM_ID") == null ? "" : dbrs.getString("ITEM_ID").trim();

                String amendment = dbrs.getString("AMENDMENT") == null ? "" : dbrs.getString("AMENDMENT").trim();

                String prshipto = dbrs.getString("PR_SHIP_TO") == null ? "" : dbrs.getString("PR_SHIP_TO").trim();

                String packaging = dbrs.getString("PACKAGING") == null ? "" : dbrs.getString("PACKAGING").trim();

                String shelflifepercent = dbrs.getString("REMAINING_SHELF_LIFE_PERCENT") == null ? "" : dbrs.getString("REMAINING_SHELF_LIFE_PERCENT").trim();


                float qtyF = 0;

                float qtyintreceieved = 0;

                if (quantity.trim().length() > 0) {

                    qtyF = Float.parseFloat(quantity);

                }

                if (qtyreceived.trim().length() > 0) {

                    qtyintreceieved = Float.parseFloat(qtyreceived);

                }


                if ((qtyF == 0) && !"0".equalsIgnoreCase(amendment)) {

                    printCanceledLine = true;

                }


                if ((qtyF > 0) || creatingblanketpo1 || printCanceledLine) {

                    if (quantity.length() > 0 && unit_price.length() > 0) {

                        float unitPrice = Float.parseFloat(unit_price);

                        order_total12 = (qtyF * unitPrice);

                        order_total1 += order_total12;

                    }


                    if (!addchargeslist.contains(item_type)) {

                        specflowd = new Hashtable();

                        specflowd.put("LINE", "" + count + "");

                        specflowd.put("ITEMID", "" + item_id + "");

                        specflowd.put("AMMENDMEANT", "" + amendment + "");

                        specflowd.put("QUANTITY", "" + quantity + "");

                        specflowd.put("PRSHIPTO", "" + prshipto + "");

                        specflowd.put("SHELFLIFEPER", "" + shelflifepercent + "");

                        specFlowv.addElement(specflowd);

                    } else {

                        packaging = "";

                    }


                    PoInfoh.put("TOTAL_PRICE_PAID", "" + lineTotal.format(order_total12) + "");

                    //RADIAN_PO

                    PoInfoh.put("RADIAN_PO", dbrs.getString("RADIAN_PO") == null ? "" : dbrs.getString("RADIAN_PO"));

                    //PO_LINE

                    PoInfoh.put("LINE_ITEM", dbrs.getString("PO_LINE") == null ? "" : dbrs.getString("PO_LINE"));

                    //AMENDMENT

                    PoInfoh.put("AMENDMENT", dbrs.getString("AMENDMENT") == null ? "" : dbrs.getString("AMENDMENT"));

                    //ITEM_ID

                    PoInfoh.put("ITEM_ID", dbrs.getString("ITEM_ID") == null ? "" : dbrs.getString("ITEM_ID"));

                    //QUANTITY

                    PoInfoh.put("QTY", dbrs.getString("QUANTITY") == null ? "" : dbrs.getString("QUANTITY"));

                    //UNIT_PRICE

                    PoInfoh.put("UNIT_PRICE", dbrs.getString("UNIT_PRICE") == null ? "" : dbrs.getString("UNIT_PRICE"));

                    //NEED_DATE

                    PoInfoh.put("NEED_DATE", dbrs.getString("NEED_DATE") == null ? "" : dbrs.getString("NEED_DATE"));

                    //PROMISED_DATE

                    PoInfoh.put("SCHED_PICK", dbrs.getString("PROMISED_DATE") == null ? "" : dbrs.getString("PROMISED_DATE"));

                    //ALLOWED_PRICE_VARIANCE

                    PoInfoh.put("ALLOWED_PRICE_VARIANCE", dbrs.getString("ALLOWED_PRICE_VARIANCE") == null ? "" : dbrs.getString("ALLOWED_PRICE_VARIANCE"));

                    //MFG_PART_NO

                    PoInfoh.put("MFG_PART_NO", dbrs.getString("MFG_PART_NO") == null ? "" : dbrs.getString("MFG_PART_NO"));

                    //SUPPLIER_PART_NO

                    PoInfoh.put("SUPPLIER_PART_NO", dbrs.getString("SUPPLIER_PART_NO") == null ? "" : dbrs.getString("SUPPLIER_PART_NO"));

                    //DPAS_RATING

                    PoInfoh.put("DPAS_RATING", dbrs.getString("DPAS_RATING") == null ? "" : dbrs.getString("DPAS_RATING"));

                    //QUALITY_FLOW_DOWNS

                    PoInfoh.put("QUALITY_FLOW_DOWNS", dbrs.getString("QUALITY_FLOW_DOWNS") == null ? "" : dbrs.getString("QUALITY_FLOW_DOWNS"));

                    //PACKAGING_FLOW_DOWNS

                    PoInfoh.put("PACKAGING_FLOW_DOWNS", dbrs.getString("PACKAGING_FLOW_DOWNS") == null ? "" : dbrs.getString("PACKAGING_FLOW_DOWNS"));

                    //PO_LINE_NOTE

                    PoInfoh.put("PO_LINE_NOTE", dbrs.getString("PO_LINE_NOTE") == null ? "" : dbrs.getString("PO_LINE_NOTE"));

                    //ITEM_DESC

                    PoInfoh.put("LINE_DESC", dbrs.getString("ITEM_DESC") == null ? "" : dbrs.getString("ITEM_DESC"));

                    //ITEM_TYPE

                    PoInfoh.put("ITEM_TYPE", item_type);

                    //PACKAGING

                    PoInfoh.put("UOM", packaging);

                    //PO_LINE_STATUS

                    PoInfoh.put("PO_LINE_STATUS", dbrs.getString("PO_LINE_STATUS") == null ? "" : dbrs.getString("PO_LINE_STATUS"));

                    //QUANTITY_RECEIVED

                    PoInfoh.put("QUANTITY_RECEIVED", qtyreceived);

                    //QUANTITY_VOUCHERED

                    PoInfoh.put("QUANTITY_VOUCHERED", dbrs.getString("QUANTITY_VOUCHERED") == null ? "" : dbrs.getString("QUANTITY_VOUCHERED"));

                    //SUPPLIER_QTY

                    PoInfoh.put("SUPPLIER_QTY", dbrs.getString("SUPPLIER_QTY") == null ? "" : dbrs.getString("SUPPLIER_QTY"));

                    //SUPPLIER_PKG

                    PoInfoh.put("SUPPLIER_PKG", dbrs.getString("SUPPLIER_PKG") == null ? "" : dbrs.getString("SUPPLIER_PKG"));

                    //SUPPLIER_UNIT_PRICE

                    PoInfoh.put("SUPPLIER_UNIT_PRICE", dbrs.getString("SUPPLIER_UNIT_PRICE") == null ? "" : dbrs.getString("SUPPLIER_UNIT_PRICE"));

                    //EVER_CONFIRMED

                    PoInfoh.put("EVER_CONFIRMED", dbrs.getString("EVER_CONFIRMED") == null ? "" : dbrs.getString("EVER_CONFIRMED"));

                    //MSDS_REQUESTED_DATE

                    PoInfoh.put("MSDS_REQUESTED_DATE", dbrs.getString("MSDS_REQUESTED_DATE") == null ? "" : dbrs.getString("MSDS_REQUESTED_DATE"));

                    //PR_SHIP_TO

                    PoInfoh.put("PR_SHIP_TO", dbrs.getString("PR_SHIP_TO") == null ? "" : dbrs.getString("PR_SHIP_TO"));

                    //PR_COMPANY_ID

                    PoInfoh.put("PR_COMPANY_ID", dbrs.getString("PR_COMPANY_ID") == null ? "" : dbrs.getString("PR_COMPANY_ID"));

                    //GENERIC_COC

                    PoInfoh.put("GENERIC_COC", dbrs.getString("GENERIC_COC") == null ? "" : dbrs.getString("GENERIC_COC"));

                    //GENERIC_COA

                    PoInfoh.put("GENERIC_COA", dbrs.getString("GENERIC_COA") == null ? "" : dbrs.getString("GENERIC_COA"));

                    //REMAINING_SHELF_LIFE_PERCENT

                    PoInfoh.put("REMAINING_SHELF_LIFE_PERCENT", shelflifepercent);

                    //VENDOR_SHIP_DATE

                    PoInfoh.put("VENDOR_SHIP_DATE", dbrs.getString("VENDOR_SHIP_DATE") == null ? "" : dbrs.getString("VENDOR_SHIP_DATE"));


                    numberRec += 1;

                    poInfo.addElement(PoInfoh);


                }

            }

            dbrs.close();


            FinalPoInfoh.put("LINES", poInfo);

            FinalPoInfoh.put("TOTAL_PRICE", "" + poTotal.format(order_total1) + "");

            FinalPoInfoh.put("NOOFLINES", "" + numberRec + "");


        }

        catch (NumberFormatException nfe) {

            log.error("Error InNumber Format of PO Query:");

            nfe.printStackTrace();

            throw nfe;

        }

        catch (SQLException sqle) {

            log.error("Error In SQL Format of PO Query:");
            ;

            sqle.printStackTrace();

            throw sqle;

        }

        finally {

            dbrs.close();

        }


        for (int specflowLine = 0; specflowLine < specFlowv.size(); specflowLine++) {

            Hashtable specflowLineData = new Hashtable();

            specflowLineData = (Hashtable) specFlowv.elementAt(specflowLine);


            String lineItemid = specflowLineData.get("ITEMID").toString().trim();

            String shelflifeper = specflowLineData.get("SHELFLIFEPER").toString().trim();

            String linsinJs = specflowLineData.get("LINE").toString().trim();

            String quantity = specflowLineData.get("QUANTITY").toString().trim();

            float qtyF = 0;

            if (quantity.trim().length() > 0) {

                qtyF = Float.parseFloat(quantity);

            }


            if (qtyF > 0) {

                if (specflowLine > 0) {

                    radianItems += ",";

                }

                radianItems += lineItemid;


                if (shelflifeper.trim().length() > 0) {

                    Hashtable SpecData = new Hashtable();

                    if (po1.length() > 0 && !"N".equalsIgnoreCase(po1)) {

                        SpecData.put("FLOW_DOWN_DESC", "" + shelflifeper + "% SHELF LIFE IS REQUIRED UPON DELIVERY.");

                    } else {

                        SpecData.put("FLOW_DOWN_DESC", "SHELF LIFE REQUIREMENT WILL BE GIVEN ON RELEASE PURCHASE ORDERS.");

                    }


                    SpecData.put("CONTENT", "");

                    SpecData.put("LINE_ITEM", "" + Integer.parseInt(linsinJs) * 1000 + "");

                    Flow_Data.addElement(SpecData);

                } else if (creatingblanketpo1) {

                    Hashtable SpecData = new Hashtable();

                    SpecData.put("FLOW_DOWN_DESC", "SHELF LIFE REQUIREMENT WILL BE GIVEN ON RELEASE PURCHASE ORDERS.");

                    SpecData.put("CONTENT", "");

                    SpecData.put("LINE_ITEM", "" + Integer.parseInt(linsinJs) * 1000 + "");

                    Flow_Data.addElement(SpecData);

                }


                getMSDSRevisionDate(lineItemid);

            }

        }


        for (int specflowLine = 0; specflowLine < specFlowv.size(); specflowLine++) {

            Hashtable specflowLineData = new Hashtable();

            specflowLineData = (Hashtable) specFlowv.elementAt(specflowLine);


            String lineItemid = specflowLineData.get("ITEMID").toString().trim();

            String linsinJs = specflowLineData.get("LINE").toString().trim();

            String ammenNumber = specflowLineData.get("AMMENDMEANT").toString().trim();

            String prShipto = specflowLineData.get("PRSHIPTO").toString().trim();


            String quantity = specflowLineData.get("QUANTITY").toString().trim();

            float qtyF = 0;

            if (quantity.trim().length() > 0) {

                qtyF = Float.parseFloat(quantity);

            }


            if (qtyF > 0) {

                if (prShipto.trim().length() > 0) {

                    ShipTo = prShipto;

                }


                if ((lineItemid.length() > 0) && !lineItemid.equalsIgnoreCase("0")) {

                    buildsendSpecs(HubNumber, linsinJs, lineItemid, po1, bpo, ShipTo, ammenNumber, shiptoloccompanyId, invengrp);

                    buildsendFlowdowns(linsinJs, lineItemid, po1, bpo, ShipTo, ammenNumber, shiptoloccompanyId);

                }

            }

        }


        return FinalPoInfoh;

    }


    protected Hashtable getPoInfo(String po1, String bpo, Vector addchargeslist, String HubNumber,

                                  String ShipTo, String shiptoloccompanyId, String bpoenddate, String invengrp, String headerNote) throws Exception {

        Vector poInfo = new Vector();

        Hashtable PoInfoh = new Hashtable();

        Hashtable FinalPoInfoh = new Hashtable();

        Vector specFlowv = new Vector();

        Hashtable specflowd = new Hashtable();

        //	  DBResultSet dbrs = null;


        float order_total1 = 0;

        float order_total12 = 0;

        DecimalFormat lineTotal = new DecimalFormat("####.0000");

        DecimalFormat poTotal = new DecimalFormat("####.00");

        boolean creatingblanketpo1 = false;


        try {

            int numberRec = 0;

            int count = 0;


            String polinedetail = "";

            boolean printCanceledLine = false;


            if (po1.length() > 0 && !"N".equalsIgnoreCase(po1) && ("0".equalsIgnoreCase(headerNote) || "1".equalsIgnoreCase(headerNote))) {

                polinedetail += "select CURRENCY_ID,to_char(VENDOR_SHIP_DATE,'mm/dd/yyyy') VENDOR_SHIP_DATE,RADIAN_PO,ITEM_TYPE,PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(NEED_DATE,'mm/dd/yyyy') NEED_DATE,to_char(PROMISED_DATE,'mm/dd/yyyy') PROMISED_DATE, ";

                polinedetail += "ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";

                polinedetail += "PR_SHIP_TO,PR_COMPANY_ID,REMAINING_SHELF_LIFE_PERCENT,MSDS_REQUESTED_DATE,GENERIC_COC,GENERIC_COA,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,PO_LINE_NOTE,ITEM_DESC,PACKAGING, ";

                polinedetail += "PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED from po_line_detail_view where RADIAN_PO = " + po1 + " order by PO_LINE asc"; //po_line_confirm_view

            } else if (po1.length() > 0 && !"N".equalsIgnoreCase(po1)) {

                polinedetail += "select CURRENCY_ID,to_char(VENDOR_SHIP_DATE,'mm/dd/yyyy') VENDOR_SHIP_DATE,RADIAN_PO,ITEM_TYPE,PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(NEED_DATE,'mm/dd/yyyy') NEED_DATE,to_char(PROMISED_DATE,'mm/dd/yyyy') PROMISED_DATE, ";

                polinedetail += "ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";

                polinedetail += "PR_SHIP_TO,PR_COMPANY_ID,REMAINING_SHELF_LIFE_PERCENT,MSDS_REQUESTED_DATE,GENERIC_COC,GENERIC_COA,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,PO_LINE_NOTE,ITEM_DESC,PACKAGING, ";

                polinedetail += "PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED from po_line_confirm_print_view where RADIAN_PO = " + po1 + " order by PO_LINE asc"; //po_line_confirm_view

            } else {

                creatingblanketpo1 = true;

                polinedetail += "select CURRENCY_ID,'' VENDOR_SHIP_DATE,BPO RADIAN_PO,ITEM_TYPE,BPO_LINE PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,'' NEED_DATE,'" + bpoenddate + "' PROMISED_DATE, ";

                polinedetail += "ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,'' DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";

                polinedetail += "'' PR_SHIP_TO,'' PR_COMPANY_ID,REMAINING_SHELF_LIFE_PERCENT,MSDS_REQUESTED_DATE,GENERIC_COC,GENERIC_COA,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,BPO_LINE_NOTE PO_LINE_NOTE,ITEM_DESC,PACKAGING, ";

                polinedetail += "PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED from bpo_line_confirm_view where BPO = " + bpo + " order by BPO_LINE asc";

            }

            // dbrs=db.doQuery( polinedetail );

            ResultSet lineResultRs = doQuery(polinedetail);


            while (lineResultRs.next()) {

                PoInfoh = new Hashtable();

                count++;


                String quantity = lineResultRs.getString("QUANTITY") == null ? "" : lineResultRs.getString("QUANTITY").trim();

                String unit_price = lineResultRs.getString("UNIT_PRICE") == null ? "" : lineResultRs.getString("UNIT_PRICE").trim();

                String item_type = lineResultRs.getString("ITEM_TYPE") == null ? "" : lineResultRs.getString("ITEM_TYPE").trim();

                String qtyreceived = lineResultRs.getString("QUANTITY_RECEIVED") == null ? "" : lineResultRs.getString("QUANTITY_RECEIVED").trim();

                String item_id = lineResultRs.getString("ITEM_ID") == null ? "" : lineResultRs.getString("ITEM_ID").trim();

                String amendment = lineResultRs.getString("AMENDMENT") == null ? "" : lineResultRs.getString("AMENDMENT").trim();

                String prshipto = lineResultRs.getString("PR_SHIP_TO") == null ? "" : lineResultRs.getString("PR_SHIP_TO").trim();

                String packaging = lineResultRs.getString("PACKAGING") == null ? "" : lineResultRs.getString("PACKAGING").trim();

                String shelflifepercent = lineResultRs.getString("REMAINING_SHELF_LIFE_PERCENT") == null ? "" : lineResultRs.getString("REMAINING_SHELF_LIFE_PERCENT").trim();


                float qtyF = 0;

                float qtyintreceieved = 0;

                if (quantity.trim().length() > 0) {

                    qtyF = Float.parseFloat(quantity);

                }

                if (qtyreceived.trim().length() > 0) {

                    qtyintreceieved = Float.parseFloat(qtyreceived);

                }


                if ((qtyF == 0) && !"0".equalsIgnoreCase(amendment)) {

                    printCanceledLine = true;

                }


                if ((qtyF > 0) || creatingblanketpo1 || printCanceledLine) {

                    if (quantity.length() > 0 && unit_price.length() > 0) {

                        float unitPrice = Float.parseFloat(unit_price);

                        order_total12 = (qtyF * unitPrice);

                        order_total1 += order_total12;

                    }


                    if (!addchargeslist.contains(item_type)) {

                        specflowd = new Hashtable();

                        specflowd.put("LINE", "" + count + "");

                        specflowd.put("ITEMID", "" + item_id + "");

                        specflowd.put("AMMENDMEANT", "" + amendment + "");

                        specflowd.put("QUANTITY", "" + quantity + "");

                        specflowd.put("PRSHIPTO", "" + prshipto + "");

                        specflowd.put("SHELFLIFEPER", "" + shelflifepercent + "");

                        specFlowv.addElement(specflowd);

                    } else {

                        packaging = "";

                    }


                    PoInfoh.put("TOTAL_PRICE_PAID", "" + lineTotal.format(order_total12) + "");

                    //RADIAN_PO

                    PoInfoh.put("RADIAN_PO", lineResultRs.getString("RADIAN_PO") == null ? "" : lineResultRs.getString("RADIAN_PO"));

                    //PO_LINE

                    PoInfoh.put("LINE_ITEM", lineResultRs.getString("PO_LINE") == null ? "" : lineResultRs.getString("PO_LINE"));

                    //AMENDMENT

                    PoInfoh.put("AMENDMENT", lineResultRs.getString("AMENDMENT") == null ? "" : lineResultRs.getString("AMENDMENT"));

                    //ITEM_ID

                    PoInfoh.put("ITEM_ID", lineResultRs.getString("ITEM_ID") == null ? "" : lineResultRs.getString("ITEM_ID"));

                    //QUANTITY

                    PoInfoh.put("QTY", lineResultRs.getString("QUANTITY") == null ? "" : lineResultRs.getString("QUANTITY"));

                    //UNIT_PRICE

                    PoInfoh.put("UNIT_PRICE", lineResultRs.getString("UNIT_PRICE") == null ? "" : lineResultRs.getString("UNIT_PRICE"));

                    //NEED_DATE

                    PoInfoh.put("NEED_DATE", lineResultRs.getString("NEED_DATE") == null ? "" : lineResultRs.getString("NEED_DATE"));

                    //PROMISED_DATE

                    PoInfoh.put("SCHED_PICK", lineResultRs.getString("PROMISED_DATE") == null ? "" : lineResultRs.getString("PROMISED_DATE"));

                    //ALLOWED_PRICE_VARIANCE

                    PoInfoh.put("ALLOWED_PRICE_VARIANCE", lineResultRs.getString("ALLOWED_PRICE_VARIANCE") == null ? "" : lineResultRs.getString("ALLOWED_PRICE_VARIANCE"));

                    //MFG_PART_NO

                    PoInfoh.put("MFG_PART_NO", lineResultRs.getString("MFG_PART_NO") == null ? "" : lineResultRs.getString("MFG_PART_NO"));

                    //SUPPLIER_PART_NO

                    PoInfoh.put("SUPPLIER_PART_NO", lineResultRs.getString("SUPPLIER_PART_NO") == null ? "" : lineResultRs.getString("SUPPLIER_PART_NO"));

                    //DPAS_RATING

                    PoInfoh.put("DPAS_RATING", lineResultRs.getString("DPAS_RATING") == null ? "" : lineResultRs.getString("DPAS_RATING"));

                    //QUALITY_FLOW_DOWNS

                    PoInfoh.put("QUALITY_FLOW_DOWNS", lineResultRs.getString("QUALITY_FLOW_DOWNS") == null ? "" : lineResultRs.getString("QUALITY_FLOW_DOWNS"));

                    //PACKAGING_FLOW_DOWNS

                    PoInfoh.put("PACKAGING_FLOW_DOWNS", lineResultRs.getString("PACKAGING_FLOW_DOWNS") == null ? "" : lineResultRs.getString("PACKAGING_FLOW_DOWNS"));

                    //PO_LINE_NOTE

                    PoInfoh.put("PO_LINE_NOTE", lineResultRs.getString("PO_LINE_NOTE") == null ? "" : lineResultRs.getString("PO_LINE_NOTE"));

                    //ITEM_DESC

                    PoInfoh.put("LINE_DESC", lineResultRs.getString("ITEM_DESC") == null ? "" : lineResultRs.getString("ITEM_DESC"));

                    //ITEM_TYPE

                    PoInfoh.put("ITEM_TYPE", item_type);

                    //PACKAGING

                    PoInfoh.put("UOM", packaging);

                    //PO_LINE_STATUS

                    PoInfoh.put("PO_LINE_STATUS", lineResultRs.getString("PO_LINE_STATUS") == null ? "" : lineResultRs.getString("PO_LINE_STATUS"));

                    //QUANTITY_RECEIVED

                    PoInfoh.put("QUANTITY_RECEIVED", qtyreceived);

                    //QUANTITY_VOUCHERED

                    PoInfoh.put("QUANTITY_VOUCHERED", lineResultRs.getString("QUANTITY_VOUCHERED") == null ? "" : lineResultRs.getString("QUANTITY_VOUCHERED"));

                    //SUPPLIER_QTY

                    PoInfoh.put("SUPPLIER_QTY", lineResultRs.getString("SUPPLIER_QTY") == null ? "" : lineResultRs.getString("SUPPLIER_QTY"));

                    //SUPPLIER_PKG

                    PoInfoh.put("SUPPLIER_PKG", lineResultRs.getString("SUPPLIER_PKG") == null ? "" : lineResultRs.getString("SUPPLIER_PKG"));

                    //SUPPLIER_UNIT_PRICE

                    PoInfoh.put("SUPPLIER_UNIT_PRICE", lineResultRs.getString("SUPPLIER_UNIT_PRICE") == null ? "" : lineResultRs.getString("SUPPLIER_UNIT_PRICE"));

                    //EVER_CONFIRMED

                    PoInfoh.put("EVER_CONFIRMED", lineResultRs.getString("EVER_CONFIRMED") == null ? "" : lineResultRs.getString("EVER_CONFIRMED"));

                    //MSDS_REQUESTED_DATE

                    PoInfoh.put("MSDS_REQUESTED_DATE", lineResultRs.getString("MSDS_REQUESTED_DATE") == null ? "" : lineResultRs.getString("MSDS_REQUESTED_DATE"));

                    //PR_SHIP_TO

                    PoInfoh.put("PR_SHIP_TO", lineResultRs.getString("PR_SHIP_TO") == null ? "" : lineResultRs.getString("PR_SHIP_TO"));

                    //PR_COMPANY_ID

                    PoInfoh.put("PR_COMPANY_ID", lineResultRs.getString("PR_COMPANY_ID") == null ? "" : lineResultRs.getString("PR_COMPANY_ID"));

                    //GENERIC_COC

                    PoInfoh.put("GENERIC_COC", lineResultRs.getString("GENERIC_COC") == null ? "" : lineResultRs.getString("GENERIC_COC"));

                    //GENERIC_COA

                    PoInfoh.put("GENERIC_COA", lineResultRs.getString("GENERIC_COA") == null ? "" : lineResultRs.getString("GENERIC_COA"));

                    //REMAINING_SHELF_LIFE_PERCENT

                    PoInfoh.put("REMAINING_SHELF_LIFE_PERCENT", shelflifepercent);

                    //VENDOR_SHIP_DATE

                    PoInfoh.put("VENDOR_SHIP_DATE", lineResultRs.getString("VENDOR_SHIP_DATE") == null ? "" : lineResultRs.getString("VENDOR_SHIP_DATE"));

                    //CURRENCY_ID

                    currencyid = lineResultRs.getString("CURRENCY_ID") == null ? "" : lineResultRs.getString("CURRENCY_ID");

                    PoInfoh.put("CURRENCY_ID", currencyid);


                    numberRec += 1;

                    poInfo.addElement(PoInfoh);


                }

            }

            //lineResultRs.close();


            FinalPoInfoh.put("LINES", poInfo);

            FinalPoInfoh.put("TOTAL_PRICE", "" + poTotal.format(order_total1) + "");

            FinalPoInfoh.put("NOOFLINES", "" + numberRec + "");


        }

        catch (NumberFormatException nfe) {

            log.error("Error InNumber Format of PO Query:");

            nfe.printStackTrace();

            throw nfe;

        }

        catch (SQLException sqle) {

            log.error("Error In SQL Format of PO Query:");
            ;

            sqle.printStackTrace();

            throw sqle;

        }

        finally {

            if (dbrs != null) {
                dbrs.close();
            }

        }


        for (int specflowLine = 0; specflowLine < specFlowv.size(); specflowLine++) {

            Hashtable specflowLineData = new Hashtable();

            specflowLineData = (Hashtable) specFlowv.elementAt(specflowLine);


            String lineItemid = specflowLineData.get("ITEMID").toString().trim();

            String shelflifeper = specflowLineData.get("SHELFLIFEPER").toString().trim();

            String linsinJs = specflowLineData.get("LINE").toString().trim();

            String quantity = specflowLineData.get("QUANTITY").toString().trim();

            float qtyF = 0;

            if (quantity.trim().length() > 0) {

                qtyF = Float.parseFloat(quantity);

            }


            if (qtyF > 0) {

                if (specflowLine > 0) {

                    radianItems += ",";

                }

                radianItems += lineItemid;


                if (shelflifeper.trim().length() > 0) {

                    Hashtable SpecData = new Hashtable();

                    if (po1.length() > 0 && !"N".equalsIgnoreCase(po1)) {

                        SpecData.put("FLOW_DOWN_DESC", "AT LEAST " + shelflifeper + "% SHELF LIFE IS REQUIRED UPON DELIVERY.");

                    } else {

                        SpecData.put("FLOW_DOWN_DESC", "SHELF LIFE REQUIREMENT WILL BE GIVEN ON RELEASE PURCHASE ORDERS.");

                    }


                    SpecData.put("CONTENT", "");

                    SpecData.put("LINE_ITEM", "" + Integer.parseInt(linsinJs) * 1000 + "");

                    Flow_Data.addElement(SpecData);

                } else if (creatingblanketpo1) {

                    Hashtable SpecData = new Hashtable();

                    SpecData.put("FLOW_DOWN_DESC", "SHELF LIFE REQUIREMENT WILL BE GIVEN ON RELEASE PURCHASE ORDERS.");

                    SpecData.put("CONTENT", "");

                    SpecData.put("LINE_ITEM", "" + Integer.parseInt(linsinJs) * 1000 + "");

                    Flow_Data.addElement(SpecData);

                }


                getMSDSRevisionDate(lineItemid);

            }

        }


        for (int specflowLine = 0; specflowLine < specFlowv.size(); specflowLine++) {

            Hashtable specflowLineData = new Hashtable();

            specflowLineData = (Hashtable) specFlowv.elementAt(specflowLine);


            String lineItemid = specflowLineData.get("ITEMID").toString().trim();

            String linsinJs = specflowLineData.get("LINE").toString().trim();

            String ammenNumber = specflowLineData.get("AMMENDMEANT").toString().trim();

            String prShipto = specflowLineData.get("PRSHIPTO").toString().trim();


            String quantity = specflowLineData.get("QUANTITY").toString().trim();

            float qtyF = 0;

            if (quantity.trim().length() > 0) {

                qtyF = Float.parseFloat(quantity);

            }


            if (qtyF > 0) {

                if (prShipto.trim().length() > 0) {

                    ShipTo = prShipto;

                }


                if ((lineItemid.length() > 0) && !lineItemid.equalsIgnoreCase("0")) {

                    buildsendSpecs(HubNumber, linsinJs, lineItemid, po1, bpo, ShipTo, ammenNumber, shiptoloccompanyId, invengrp);

                    buildsendFlowdowns(linsinJs, lineItemid, po1, bpo, ShipTo, ammenNumber, shiptoloccompanyId);

                }

            }

        }


        return FinalPoInfoh;

    }


    public void buildsendSpecs2(String Hubname, String lineID, String itemID, String radianpo, String radianbpo,
                                String shipToLoc, String ammenNum, String shiptocompanyid, String invengrp) {

        StringBuilder Msgtemp = new StringBuilder();
        DbManager dbManager = null;
        Connection connect1 = null;
        CallableStatement cs = null;
        ResultSet specrs = null;
        Msgtemp.append("{\n");
        int specLineCount = 0; //this keep track of where to insert the data into flow_data so that we can display them in the right order.
        String genericCoc = "";
        String genericCoa = "";
        int rowNumber = Integer.parseInt(lineID) * 1000;
        try {
            String genericcocQ = "";
            if (radianpo.length() > 0 && !"N".equalsIgnoreCase(radianpo)) {
                genericcocQ = "Select GENERIC_COC,GENERIC_COA from po_line_detail_view where RADIAN_PO = " + radianpo + " and PO_LINE = " + rowNumber + " and AMENDMENT=" + ammenNum + " ";
            } else {
                genericcocQ = "Select GENERIC_COC,GENERIC_COA from bpo_line_detail_view where BPO = " + radianbpo + " and BPO_LINE = " + rowNumber + " and AMENDMENT=" + ammenNum + " ";
            }

            dbrs = doQuery(genericcocQ);

            while (dbrs.next()) {
                genericCoc = dbrs.getString("GENERIC_COC") == null ? "" : dbrs.getString("GENERIC_COC").trim();
                genericCoa = dbrs.getString("GENERIC_COA") == null ? "" : dbrs.getString("GENERIC_COA").trim();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (dbrs != null)
                    dbrs.close();
            }
            catch (SQLException sqle) {
                log.error("SQL Exception on close in build send specs (Generic C of A/C)");
            }
        }

        //Generic COC and/or COA
        String genericSpecNote = "";
        if ((genericCoc.equalsIgnoreCase("Y")) && (genericCoa.equalsIgnoreCase("Y"))) {
            genericSpecNote = "Your general C of C and C of A / Test Report";
            COAcount++;
        } else if (genericCoc.equalsIgnoreCase("Y")) {
            genericSpecNote = "Your general C of C";
        } else if (genericCoa.equalsIgnoreCase("Y")) {
            genericSpecNote = "Your general C of A / Test Report";
            COAcount++;
        }
        //putting it together
        if (genericSpecNote.length() > 1) {
            Hashtable SpecData = new Hashtable();
            SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + genericSpecNote);
            SpecData.put("CONTENT", "");
            SpecData.put("LINE_ITEM", "" + rowNumber + "");
            Flow_Data.insertElementAt(SpecData, specLineCount);
            specLineCount++;
            lineCount++;
        }

        try {
            dbManager = new DbManager(this.getClient());
            connect1 = dbManager.getConnection();
            cs = connect1.prepareCall("{call pkg_po.Po_spec(?,?,?,?,?,?,?,?)}");
            cs.setString(1, shipToLoc);
            if (shiptocompanyid.length() > 0) {
                cs.setString(2, shiptocompanyid);
            } else {
                cs.setNull(2, OracleTypes.VARCHAR);
            }
            if (itemID.length() > 0) {
                cs.setInt(3, Integer.parseInt(itemID));
            } else {
                cs.setNull(1, OracleTypes.INTEGER);
            }
            if (radianpo.length() > 0 && !"N".equalsIgnoreCase(radianpo)) {
                cs.setInt(4, Integer.parseInt(radianpo));
            } else if (radianbpo.length() > 0) {
                cs.setInt(4, Integer.parseInt(radianbpo));
            } else {
                cs.setNull(4, OracleTypes.INTEGER);
            }

            if (lineID.length() > 0) {
                cs.setInt(5, rowNumber);
            } else {
                cs.setNull(1, OracleTypes.INTEGER);
            }
            if (ammenNum.length() > 0) {
                cs.setInt(6, Integer.parseInt(ammenNum));
            } else {
                cs.setNull(6, OracleTypes.INTEGER);
            }
            if (invengrp.length() > 0) {
                cs.setString(7, invengrp);
            } else {
                cs.setNull(7, OracleTypes.VARCHAR);
            }

            cs.registerOutParameter(8, OracleTypes.VARCHAR);
            cs.execute();

            String resultQuery = cs.getObject(8).toString();
            dbrs = doQuery(resultQuery);

            String tmpCoc = "";
            String tmpCoa = "";
            String tmpNone = "";

            Vector cocV = new Vector(10);
            Vector coaV = new Vector(10);
            Vector noneV = new Vector(10);

            int i = 0;
            while (dbrs.next()) {
                String spec_id = dbrs.getString("SPEC_ID") == null ? "" : dbrs.getString("SPEC_ID").trim();
                String coc = specrs.getString("SAVED_COC") == null ? "" : specrs.getString("SAVED_COC").trim();
                String coa = specrs.getString("SAVED_COA") == null ? "" : specrs.getString("SAVED_COA").trim();
                //String ok=dbrs.getString( "OK" ) == null ? "" : dbrs.getString( "OK" ).trim();

                /////////////////////////////////////////////////if ( ok.equalsIgnoreCase( "Y" ) ) {
                i++;
                if (coc.equalsIgnoreCase("Y") && coa.equalsIgnoreCase("Y")) {
                    tmpCoc = "C OF C required for SPEC ";
                    cocV.addElement(spec_id.toUpperCase());
                    tmpCoa = "C OF A / Test Report required for SPEC ";
                    COAcount++;
                    coaV.addElement(spec_id.toUpperCase());
                } else if (coc.equalsIgnoreCase("Y")) {
                    tmpCoc = "C OF C required for SPEC ";
                    cocV.addElement(spec_id.toUpperCase());
                } else if (coa.equalsIgnoreCase("Y")) {
                    tmpCoa = "C OF A / Test Report required for SPEC ";
                    coaV.addElement(spec_id.toUpperCase());
                    COAcount++;
                } else {
                    log.info("WBUY: error in NEW specs: " + radianpo + " , line " + lineID);
                    //tmpNone="ITEM MUST MEET SPEC ";
                    //noneV.addElement( spec_id.toUpperCase() );
                }
                //}
            } //end of while

            //11-26-02 putting the spec info together
            int j = 0;
            //first COC
            for (j = 0; j < cocV.size(); j++) {
                tmpCoc += (String) cocV.elementAt(j) + "; ";
            }
            //removing the last commas ','.  If string contain spec ID then add to list
            if (tmpCoc.indexOf(";") > 1) {
                tmpCoc = tmpCoc.substring(0, tmpCoc.length() - 2);
                Vector tmpV = breakStringApart(tmpCoc, 100, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }
            }
            //next COA
            for (j = 0; j < coaV.size(); j++) {
                tmpCoa += (String) coaV.elementAt(j) + "; ";
            }
            //removing the last commas ','.  If string contain spec ID then add to list
            if (tmpCoa.indexOf(";") > 1) {
                tmpCoa = tmpCoa.substring(0, tmpCoa.length() - 2);
                Vector tmpV = breakStringApart(tmpCoa, 100, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }
            }
            //finally MEET SPEC
            for (j = 0; j < noneV.size(); j++) {
                tmpNone += (String) noneV.elementAt(j) + "; ";
            }
            //removing the last commas ', '.  If string contain spec ID then add to list
            if (tmpNone.indexOf(";") > 1) {
                tmpNone = tmpNone.substring(0, tmpNone.length() - 2);
                Vector tmpV = breakStringApart(tmpNone, 100, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }
            }

            //add the rest of the info if there is a spec
            if (specLineCount > 0) {
                //getting fax number info
                String faxTo = "";
                String faxNumber = "";
                String tmpQuery = "";
                try {
                    tmpQuery = "select p.last_name || ', ' || p.first_name full_name, nvl(fax,'0') fax_number from location a, personnel p " +
                            "where a.company_id = p.company_id and a.location_contact = p.personnel_id and a.location_id = '" + shipToLoc + "' and " +
                            "a.company_id = '" + shiptocompanyid + "'";
                    dbrs = doQuery(tmpQuery);
                    while (dbrs.next()) {
                        faxTo = dbrs.getString("full_name") == null ? "" : dbrs.getString("full_name").trim();
                        faxNumber = dbrs.getString("fax_number") == null ? "" : dbrs.getString("fax_number").trim();
                    }
                }
                catch (Exception e) {
                    faxTo = "";
                    faxNumber = "";
                    log.error("spec query info exception building PO PDF: " + e);
                    // MailHelper.sendMail( db,"Query Error",tmpQuery,86030,false );
                }
                finally {
                    if (dbrs != null)
                        dbrs.close();
                }
                String moreInfo = "All CERTS MUST reference PO, SPEC and accompany shipment";
                //don't add the follow clause if hub doesn't want SPEC fax
                if (faxTo.length() > 3 && !"0".equalsIgnoreCase(faxNumber) && !"2101".equalsIgnoreCase(Hubname)) {
                    moreInfo += " or be FAXED IN ADVANCE to " + faxTo + " at " + faxNumber;
                }
                moreInfo += " to ensure timely processing.  SEND ALL CERTS FOR ALL LOTS SHIPPED.";
                Vector tmpV = breakStringApart(moreInfo, 100, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }

                //RMA - Return Material Authorization
                moreInfo = "We will initiate an RMA [Return Material Authorization] should acceptable certs not be received within 3 business days of delivery.";
                tmpV = breakStringApart(moreInfo, 100, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }
            } //end of if specLineCount > 0

            Hashtable SpecData1 = new Hashtable();
            SpecData1.put("FLOW_DOWN_DESC", lineCount + ") NO SUBSTITUTIONS ALLOWED WHATSOEVER.");
            SpecData1.put("CONTENT", "");
            SpecData1.put("LINE_ITEM", "" + rowNumber + "");
            Flow_Data.insertElementAt(SpecData1, specLineCount);
            lineCount++;

            //set line count for shelf life
            if (Flow_Data.size() > 0) {
                Hashtable tmpH = (Hashtable) Flow_Data.lastElement();
                String tmpShl = (String) tmpH.get("FLOW_DOWN_DESC");
                if (tmpShl.indexOf("SHELF LIFE") > 0) {
                    tmpShl = lineCount + ") " + tmpShl;
                    tmpH.put("FLOW_DOWN_DESC", tmpShl);
                }
            }

            //add line separation ****************
            if (Flow_Data.size() > 0) {
                Hashtable SpecData = new Hashtable();
                SpecData.put("FLOW_DOWN_DESC", "*********************************************************************");
                SpecData.put("CONTENT", "");
                SpecData.put("LINE_ITEM", "" + rowNumber + "");
                Flow_Data.addElement(SpecData);
            } //end of line separation
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException se) {
                }
            }
            try {
                dbrs.close();
            } catch (SQLException sqle) {
                log.error("SQL Exception on close in build send specs (Spec Data)");
            }
            if (connect1 != null) {
                try {
                    dbManager.returnConnection(connect1);
                } catch (Exception e) {
                }
            }
        }
    }

    public void buildsendSpecs(String Hubname, String lineID, String itemID, String radianpo, String radianbpo,
                               String shipToLoc, String ammenNum, String shiptocompanyid, String invengrp) {

        StringBuilder Msgtemp = new StringBuilder();
        CallableStatement cs = null;
        ResultSet specrs = null;
        Msgtemp.append("{\n");
        int count = 0;
        DbManager dbManager = null;
        Connection connect1 = null;

        int specLineCount = 0; //this keep track of where to insert the data into flow_data so that we can display them in the right order.

        ResultSet rsGenCocData = null;
        int rowNumber = Integer.parseInt(lineID) * 1000;
        try {
            dbManager = new DbManager(this.getClient());
            connect1 = dbManager.getConnection();
            cs = connect1.prepareCall("{call pkg_po.Po_spec(?,?,?,?,?,?,?,?)}");
            cs.setString(1, shipToLoc);
            if (shiptocompanyid.length() > 0) {
                cs.setString(2, shiptocompanyid);
            } else {
                cs.setNull(2, OracleTypes.VARCHAR);
            }
            if (itemID.length() > 0) {
                cs.setInt(3, Integer.parseInt(itemID));
            } else {
                cs.setNull(1, OracleTypes.INTEGER);
            }
            if (radianpo.length() > 0 && !"N".equalsIgnoreCase(radianpo)) {
                cs.setInt(4, Integer.parseInt(radianpo));
            } else if (radianbpo.length() > 0) {
                cs.setInt(4, Integer.parseInt(radianbpo));
            } else {
                cs.setNull(4, OracleTypes.INTEGER);
            }

            if (lineID.length() > 0) {
                cs.setInt(5, rowNumber);
            } else {
                cs.setNull(1, OracleTypes.INTEGER);
            }
            if (ammenNum.length() > 0) {
                cs.setInt(6, Integer.parseInt(ammenNum));
            } else {
                cs.setNull(6, OracleTypes.INTEGER);
            }
            if (invengrp.length() > 0) {
                cs.setString(7, invengrp);
            } else {
                cs.setNull(7, OracleTypes.VARCHAR);
            }

            cs.registerOutParameter(8, OracleTypes.VARCHAR);
            cs.execute();

            String resultQuery = cs.getObject(8).toString();
            // dbrs=db.doQuery( resultQuery );
            specrs = doQuery(resultQuery);

            String tmpCoc = "";
            String tmpCoa = "";
            String tmpNone = "";

            Vector cocV = new Vector(10);
            Vector coaV = new Vector(10);
            Vector noneV = new Vector(10);

            int i = 0;
            while (specrs.next()) {
                String spec_id = specrs.getString("SPEC_ID") == null ? "" : specrs.getString("SPEC_ID").trim();
                String coc = specrs.getString("SAVED_COC") == null ? "" : specrs.getString("SAVED_COC").trim();
                String coa = specrs.getString("SAVED_COA") == null ? "" : specrs.getString("SAVED_COA").trim();
                //String ok=specrs.getString( "OK" ) == null ? "" : specrs.getString( "OK" ).trim();

                //if ( ok.equalsIgnoreCase( "Y" ) ) {
                i++;
                if (coc.equalsIgnoreCase("Y") && coa.equalsIgnoreCase("Y")) {
                    tmpCoc = "Certificate of Conformance required for Specification ";
                    if (!cocV.contains(spec_id.toUpperCase())) {
                        cocV.addElement(spec_id.toUpperCase());
                    }
                    tmpCoa = "Certificate of Analysis / Test Report required for Specification ";
                    if (!coaV.contains(spec_id.toUpperCase())) {
                        COAcount++;
                        coaV.addElement(spec_id.toUpperCase());
                    }
                } else if (coc.equalsIgnoreCase("Y") && !cocV.contains(spec_id.toUpperCase())) {
                    tmpCoc = "Certificate of Conformance required for Specification ";
                    cocV.addElement(spec_id.toUpperCase());
                } else if (coa.equalsIgnoreCase("Y") && !coaV.contains(spec_id.toUpperCase())) {
                    tmpCoa = "Certificate of Analysis / Test Report required for Specification ";
                    coaV.addElement(spec_id.toUpperCase());
                    COAcount++;
                } else {
                    log.info("WBUY: error in NEW specs: " + radianpo + " , line " + lineID);
                    //tmpNone="ITEM MUST MEET SPEC ";
                    //noneV.addElement( spec_id.toUpperCase() );
                }
                //}
            } //end of while

            //11-26-02 putting the spec info together
            int j = 0;
            //first COC
            for (j = 0; j < cocV.size(); j++) {
                tmpCoc += (String) cocV.elementAt(j) + "; ";
            }
            //removing the last commas ','.  If string contain spec ID then add to list
            if (tmpCoc.indexOf(";") > 1) {
                tmpCoc = tmpCoc.substring(0, tmpCoc.length() - 2);
                Vector tmpV = breakStringApart(tmpCoc, 120, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }
            }
            //next COA
            for (j = 0; j < coaV.size(); j++) {
                tmpCoa += (String) coaV.elementAt(j) + "; ";
            }
            //removing the last commas ','.  If string contain spec ID then add to list
            if (tmpCoa.indexOf(";") > 1) {
                tmpCoa = tmpCoa.substring(0, tmpCoa.length() - 2);
                Vector tmpV = breakStringApart(tmpCoa, 120, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }
            }
            //finally MEET SPEC
            for (j = 0; j < noneV.size(); j++) {
                tmpNone += (String) noneV.elementAt(j) + "; ";
            }
            //removing the last commas ', '.  If string contain spec ID then add to list
            if (tmpNone.indexOf(";") > 1) {
                tmpNone = tmpNone.substring(0, tmpNone.length() - 2);
                Vector tmpV = breakStringApart(tmpNone, 140, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }
            }

            //add the rest of the info if there is a spec
            if (specLineCount > 0) {
                //getting fax number info
                String faxTo = "";
                String faxNumber = "";
                String tmpQuery = "";
                try {
                    tmpQuery = "select p.first_name || ' ' || p.last_name full_name, nvl(fax,'0') fax_number from customer.location a, customer.personnel p " +
                            "where a.company_id = p.company_id and a.location_contact = p.personnel_id and a.location_id = '" + shipToLoc + "' and " +
                            "a.company_id = '" + shiptocompanyid + "'";
                    // dbrs=db.doQuery( tmpQuery );
                    rsGenCocData = doQuery(tmpQuery);
                    while (rsGenCocData.next()) {
                        faxTo = rsGenCocData.getString("full_name") == null ? "" : rsGenCocData.getString("full_name").trim();
                        faxNumber = rsGenCocData.getString("fax_number") == null ? "" : rsGenCocData.getString("fax_number").trim();
                    }
                }
                catch (Exception e) {
                    faxTo = "";
                    faxNumber = "";
                    // **TODO** HelpObjs.sendMail( db,"Query Error",tmpQuery,86030,false );
                }
                finally {
                    if (dbrs != null) {
                        dbrs.close();
                    }
                }
                String moreInfo = "All Certificates MUST reference Specifications and accompany shipment";
                //don't add the follow clause if hub doesn't want SPEC fax
                if (faxTo.length() > 3 && !"0".equalsIgnoreCase(faxNumber) && !"2101".equalsIgnoreCase(Hubname)) {
                    moreInfo += " OR be FAXED IN ADVANCE to " + faxTo + " at " + faxNumber;
                }
                moreInfo += " to ensure timely processing.  SEND ALL CERTIFICATES FOR ALL LOTS SHIPPED.";
                Vector tmpV = breakStringApart(moreInfo, 100, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }

                //RMA - Return Material Authorization
                moreInfo = "We may return products at supplier's cost for which acceptable certificates are not received within 3 business days after delivery.";
                tmpV = breakStringApart(moreInfo, 120, 10);
                for (int k = 0; k < tmpV.size(); k++) {
                    Hashtable SpecData = new Hashtable();
                    if (k == 0) {
                        SpecData.put("FLOW_DOWN_DESC", lineCount + ") " + (String) tmpV.elementAt(k));
                        lineCount++;
                    } else {
                        if (lineCount > 10) {
                            SpecData.put("FLOW_DOWN_DESC", "     " + (String) tmpV.elementAt(k)); //if line count is two digit than add an extra blank space
                        } else {
                            SpecData.put("FLOW_DOWN_DESC", "    " + (String) tmpV.elementAt(k));
                        }
                    }
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.insertElementAt(SpecData, specLineCount);
                    specLineCount++;
                }
            } //end of if specLineCount > 0

            Hashtable SpecData1 = new Hashtable();
            SpecData1.put("FLOW_DOWN_DESC", lineCount + ") NO SUBSTITUTIONS ALLOWED WHATSOEVER.");
            SpecData1.put("CONTENT", "");
            SpecData1.put("LINE_ITEM", "" + rowNumber + "");
            Flow_Data.insertElementAt(SpecData1, specLineCount);
            lineCount++;

            //set line count for shelf life
            if (Flow_Data.size() > 0) {
                Hashtable tmpH = (Hashtable) Flow_Data.lastElement();
                String tmpShl = (String) tmpH.get("FLOW_DOWN_DESC");
                if (tmpShl.indexOf("SHELF LIFE") > 0) {
                    tmpShl = lineCount + ") " + tmpShl;
                    tmpH.put("FLOW_DOWN_DESC", tmpShl);
                }
            }

            //add line separation ****************
            if (Flow_Data.size() > 0) {
                Hashtable SpecData = new Hashtable();
                SpecData.put("FLOW_DOWN_DESC", "***********************************************************************************************************");
                SpecData.put("CONTENT", "");
                SpecData.put("LINE_ITEM", "" + rowNumber + "");
                Flow_Data.addElement(SpecData);
            } //end of line separation
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (cs != null) {
                try {
                    cs.close();
                }
                catch (SQLException se) {
                }
            }
            if (dbrs != null) {
                try {
                    dbrs.close();
                }
                catch (SQLException sqle) {
                    log.error("SQL Exception on close in build send specs (Spec Data)");
                }
            }
            if (connect1 != null) {
                try {
                    dbManager.returnConnection(connect1);
                } catch (Exception e) {
                }
            }
        }

        if (count == 0) {
            Msgtemp.append("insidehtml +=\"<TR>\";\n");
            Msgtemp.append("insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"7\\\">No Records Found</TD>\";\n");
            Msgtemp.append("insidehtml +=\"</TR>\";\n");
        }
    }

    //this function break the string apart into the size of line
    public Vector breakStringApart(String value, int lineSize, int numberOfLine) {
        Vector result = new Vector(numberOfLine);

        if (value.length() > lineSize) {
            boolean done = false;
            String tmpVal = "";
            StringTokenizer st = new StringTokenizer(value, " ");

            while (st.hasMoreTokens()) {
                String tmp = st.nextToken();
                if (tmpVal.length() + tmp.length() <= lineSize) {
                    tmpVal += tmp + " ";
                } else {
                    result.addElement(tmpVal);
                    tmpVal = tmp + " ";
                }
            } //end of while has more tokens
            result.addElement(tmpVal);
        } else {
            result.addElement(value);
        }
        return result;
    }  //end of method


    public void buildsendFlowdowns2(String lineID, String itemID, String radianpo, String radianbpo, String shipToLoc, String ammenNum, String shiptocompanyid) {

        CallableStatement cs = null;
        StringBuilder Msgtemp = new StringBuilder();
        DbManager dbManager = null;
        Connection connect1 = null;
        Msgtemp.append("{\n");
        try {
            int rowNumber = Integer.parseInt(lineID) * 1000;
            dbManager = new DbManager(this.getClient());
            connect1 = dbManager.getConnection();
            cs = connect1.prepareCall("{call pkg_po.Po_flowdown(?,?,?,?,?,?,?)}");
            cs.setString(1, shipToLoc);
            cs.setString(1, shipToLoc);
            if (shiptocompanyid.length() > 0) {
                cs.setString(2, shiptocompanyid);
            } else {
                cs.setNull(2, OracleTypes.VARCHAR);
            }
            if (itemID.length() > 0) {
                cs.setInt(3, Integer.parseInt(itemID));
            } else {
                cs.setNull(1, OracleTypes.INTEGER);
            }
            if (radianpo.length() > 0 && !"N".equalsIgnoreCase(radianpo)) {
                cs.setInt(4, Integer.parseInt(radianpo));
            } else if (radianbpo.length() > 0) {
                cs.setInt(4, Integer.parseInt(radianbpo));
            } else {
                cs.setNull(4, OracleTypes.INTEGER);
            }

            if (lineID.length() > 0) {
                cs.setInt(5, rowNumber);
            } else {
                cs.setNull(1, OracleTypes.INTEGER);
            }
            if (ammenNum.length() > 0) {
                cs.setInt(6, Integer.parseInt(ammenNum));
            } else {
                cs.setNull(6, OracleTypes.INTEGER);
            }
            cs.registerOutParameter(7, OracleTypes.VARCHAR);
            cs.execute();
            String resultQuery = cs.getObject(7).toString();
            dbrs = doQuery(resultQuery);
            while (dbrs.next()) {
                String flow_down = dbrs.getString("FLOW_DOWN") == null ? "" : dbrs.getString("FLOW_DOWN").trim();
                String ok = dbrs.getString("OK") == null ? "" : dbrs.getString("OK").trim();
                if (ok.equalsIgnoreCase("Y")) {
                    Hashtable SpecData = new Hashtable();
                    SpecData.put("FLOW_DOWN_DESC", "NEED FLOWDOWN " + flow_down + "");
                    SpecData.put("CONTENT", "");
                    SpecData.put("LINE_ITEM", "" + rowNumber + "");
                    Flow_Data.addElement(SpecData);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (cs != null) {
                try {
                    cs.close();
                }
                catch (SQLException se) {
                }
            }
            try {
                if (dbrs != null)
                    dbrs.close();
            }
            catch (SQLException sqle) {
                log.error("SQL Exception on close in build flow downs (spec data)");
            }
            if (connect1 != null) {
                try {
                    dbManager.returnConnection(connect1);
                } catch (Exception e) {
                }
            }
        }
    }


    public void buildsendFlowdowns(String lineID, String itemID, String radianpo, String radianbpo, String shipToLoc, String ammenNum, String shiptocompanyid) {
        CallableStatement cs = null;
        ResultSet flowRs = null;
        StringBuilder Msgtemp = new StringBuilder();
        Msgtemp.append("{\n");

        DbManager dbManager = null;
        Connection connect1 = null;
        int count = 0;
        try {
            int rowNumber = Integer.parseInt(lineID) * 1000;
            dbManager = new DbManager(this.getClient());
            connect1 = dbManager.getConnection();
            cs = connect1.prepareCall("{call pkg_po.Po_flowdown(?,?,?,?,?,?,?)}");
            cs.setString(1, shipToLoc);
            cs.setString(1, shipToLoc);
            if (shiptocompanyid.length() > 0) {
                cs.setString(2, shiptocompanyid);
            } else {
                cs.setNull(2, OracleTypes.VARCHAR);
            }
            if (itemID.length() > 0) {
                cs.setInt(3, Integer.parseInt(itemID));
            } else {
                cs.setNull(1, OracleTypes.INTEGER);
            }
            if (radianpo.length() > 0 && !"N".equalsIgnoreCase(radianpo)) {
                cs.setInt(4, Integer.parseInt(radianpo));
            } else if (radianbpo.length() > 0) {
                cs.setInt(4, Integer.parseInt(radianbpo));
            } else {
                cs.setNull(4, OracleTypes.INTEGER);
            }
            if (lineID.length() > 0) {
                cs.setInt(5, rowNumber);
            } else {
                cs.setNull(1, OracleTypes.INTEGER);
            }
            if (ammenNum.length() > 0) {
                cs.setInt(6, Integer.parseInt(ammenNum));
            } else {
                cs.setNull(6, OracleTypes.INTEGER);
            }

            cs.registerOutParameter(7, OracleTypes.VARCHAR);
            cs.execute();
            String resultQuery = cs.getObject(7).toString();
            flowRs = doQuery(resultQuery);
            Vector flowV = new Vector(10);
            while (flowRs.next()) {
                String flow_down = flowRs.getString("FLOW_DOWN") == null ? "" : flowRs.getString("FLOW_DOWN").trim();
                String flow_down_desc = flowRs.getString("FLOW_DOWN_DESC") == null ? "" : flowRs.getString("FLOW_DOWN_DESC").trim();
                String revdate = flowRs.getString("REVISION_DATE") == null ? "" : flowRs.getString("REVISION_DATE").trim();
                String ok = flowRs.getString("OK") == null ? "" : flowRs.getString("OK").trim();
                if (revdate.length() > 0) {
                    revdate = " Rev Date: " + revdate;
                }
                if (flow_down_desc.trim().length() == 0) {
                    flow_down_desc = flow_down;
                }
                if (ok.equalsIgnoreCase("Y")) {
                    count++;
                    Hashtable SpecData = new Hashtable();
                    if (count == 1) {
                        SpecData.put("FLOW_DOWN_DESC", "Add'l Quality Requirements:");
                        SpecData.put("CONTENT", "0");
                        SpecData.put("LINE_ITEM", "" + rowNumber + "");
                        Flow_Data.addElement(SpecData);
                    }
                    String tmpflowdesc = flow_down_desc + " " + revdate;
                    if (!flowV.contains(tmpflowdesc)) {
                        flowV.addElement(tmpflowdesc);
                        Vector tmpV = breakStringApart(tmpflowdesc, 140, 10);
                        for (int k = 0; k < tmpV.size(); k++) {
                            SpecData = new Hashtable();
                            String flowdDescprt = (String) tmpV.elementAt(k);
                            SpecData.put("FLOW_DOWN_DESC", "" + flowdDescprt + "");
                            SpecData.put("CONTENT", "");
                            SpecData.put("LINE_ITEM", "" + rowNumber + "");
                            Flow_Data.addElement(SpecData);
                        }
                    }
                }
            }

            if (count > 0) {
                Hashtable SpecData = new Hashtable();
                SpecData.put("FLOW_DOWN_DESC", "***********************************************************************************************************");
                SpecData.put("CONTENT", "" + (count + 1) + "");
                SpecData.put("LINE_ITEM", "" + rowNumber + "");
                Flow_Data.addElement(SpecData);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (cs != null) {
                try {
                    cs.close();
                }
                catch (SQLException se) {
                }
            }
            if (dbrs != null) {
                try {
                    dbrs.close();
                }
                catch (SQLException sqle) {
                    log.error("SQL Exception on close in build send specs (Spec Data)");
                }
            }
            if (connect1 != null) {
                try {
                    dbManager.returnConnection(connect1);
                } catch (Exception e) {
                }
            }
        }

        if (count == 0) {
            Msgtemp.append("insidehtml +=\"<TR>\";\n");
            Msgtemp.append("insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"7\\\">No Records Found</TD>\";\n");
            Msgtemp.append("insidehtml +=\"</TR>\";\n");
        }
    }

    protected Vector getPoLineNotes(Vector poLineData) throws Exception {
        Vector JDEpoInfoh = new Vector();
        Hashtable JdepoH = new Hashtable();

        //lookup purchase order information from purchase_info_data_view
        try {
            int numberRec = 0;
            for (int loop = 0; loop < poLineData.size(); loop++) {
                Hashtable poLineHash = new Hashtable();
                poLineHash = (Hashtable) poLineData.elementAt(loop);

                String radian_po = poLineHash.get("RADIAN_PO").toString();
                String po_line = poLineHash.get("LINE_ITEM").toString();
                String po_line_note = poLineHash.get("PO_LINE_NOTE").toString();
                po_line_note = StringHandler.replace(po_line_note, "\n", "<BR>");
                po_line_note = StringHandler.replace(po_line_note, "<BR>", " \n ");
                int i = 1000;

                StringTokenizer st = new StringTokenizer(po_line_note, "\n");
                if (st.countTokens() > 0) {
                    while (st.hasMoreTokens()) {
                        String tok = st.nextToken();
                        tok = tok.trim();
                        Vector poLineNoteVector = breakStringApart(tok, 100, 10);

                        for (int k = 0; k < poLineNoteVector.size(); k++) {
                            String poLineNote = (String) poLineNoteVector.elementAt(k);
                            JdepoH = new Hashtable();
                            JdepoH.put("RADIAN_PO", radian_po);
                            JdepoH.put("LINE_ITEM", po_line);
                            JdepoH.put("associate_text_window", poLineNote);
                            JdepoH.put("associate_text_window_order", "" + i + "");
                            JDEpoInfoh.addElement(JdepoH);
                            i++;
                        }
                        i++;
                    }
                } else {
                    Vector poLineNoteVector = breakStringApart(po_line_note, 100, 10);
                    for (int k = 0; k < poLineNoteVector.size(); k++) {
                        String poLineNote = (String) poLineNoteVector.elementAt(k);
                        JdepoH = new Hashtable();
                        JdepoH.put("RADIAN_PO", radian_po);
                        JdepoH.put("LINE_ITEM", po_line);
                        JdepoH.put("associate_text_window", poLineNote);
                        JdepoH.put("associate_text_window_order", "" + i + "");
                        JDEpoInfoh.addElement(JdepoH);
                        i++;
                    }
                }
                numberRec += 1;
            }
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
            System.out.println("Error In SQL Format of JDEPO Query: </P>");
            throw sqle;
        }
        return JDEpoInfoh;
    }


    protected Vector getmoreComments() {

        Vector JDEpoInfoh = new Vector();

        Hashtable JdepoH = new Hashtable();

        JdepoH = new Hashtable();


        JdepoH.put("LINE_NUM", "1000");

        JdepoH.put("TEXT_LINE", " ");

        JDEpoInfoh.addElement(JdepoH);


        return JDEpoInfoh;

    }

    /*

      * *MN: not used; commented out because of modifications to query to getBuyerInfo (this code has original calls)

      *

    protected Hashtable getBuyerInfo2( String buyer )

    {

      Hashtable BuyerInfoh=new Hashtable();



      try

      {

        int numberRec=0;



        String stmt="select * from personnel where personnel_id = " + buyer + " and company_id = 'Radian'";

        dbrs=doQuery( stmt );

        Hashtable result=new Hashtable();



        result=new Hashtable();

        while ( dbrs.next() )

        {

          String first_name=dbrs.getString( "FIRST_NAME" ) == null ? "" : dbrs.getString( "FIRST_NAME" ).trim();

          String last_name=dbrs.getString( "LAST_NAME" ) == null ? "" : dbrs.getString( "LAST_NAME" ).trim();

          String phone=dbrs.getString( "PHONE" ) == null ? "" : dbrs.getString( "PHONE" ).trim();

          String email=dbrs.getString( "EMAIL" ) == null ? "" : dbrs.getString( "EMAIL" ).trim();

          String fax=dbrs.getString( "FAX" ) == null ? "" : dbrs.getString( "FAX" ).trim();



          BuyerInfoh.put( "buyer_name","" + first_name + " " + last_name + "" );

          BuyerInfoh.put( "byrsig","" + first_name + "_" + last_name + "" );

          BuyerInfoh.put( "buyer_phone",phone );

          BuyerInfoh.put( "buyer_fax",fax );

          BuyerInfoh.put( "buyer_email",email );



          numberRec+=1;

        }

        dbrs.close();

      }

      catch ( Exception sqle )

      {

        sqle.printStackTrace();

      }

      finally

      {

          try {

             dbrs.close();

          }

          catch (SQLException sqle) {

             log.error("SQL Exception on close in get buyer info (buy info)");

          }

      }



      return BuyerInfoh;

     }

      */

    protected Hashtable getBuyerInfo(String poNum, String buyer, String invengrp) {

        Hashtable BuyerInfoh = new Hashtable();

        //  DBResultSet dbrs = null;

        try {

            int numberRec = 0;

            // String stmt="select * from personnel where personnel_id = " + buyer + " and company_id = 'Radian'";


            String stmt = "select buyer_first_name, buyer_last_name, buyer_phone, buyer_email, buyer_fax from po_header_view where radian_po=" + poNum;

            // dbrs=db.doQuery( stmt );

            ResultSet buyerInfoRs = doQuery(stmt);

            Hashtable result = new Hashtable();


            result = new Hashtable();

            while (buyerInfoRs.next()) {

                String first_name = buyerInfoRs.getString("BUYER_FIRST_NAME") == null ? "" : buyerInfoRs.getString("BUYER_FIRST_NAME").trim();  // was FIRST_NAME

                String last_name = buyerInfoRs.getString("BUYER_LAST_NAME") == null ? "" : buyerInfoRs.getString("BUYER_LAST_NAME").trim();    // was LAST_NAME

                String phone = buyerInfoRs.getString("BUYER_PHONE") == null ? "" : buyerInfoRs.getString("BUYER_PHONE").trim();                // was PHONE

                String email = buyerInfoRs.getString("BUYER_EMAIL") == null ? "" : buyerInfoRs.getString("BUYER_EMAIL").trim();                // was EMAIL

                String fax = buyerInfoRs.getString("BUYER_FAX") == null ? "" : buyerInfoRs.getString("BUYER_FAX").trim();                      // was FAX


                BuyerInfoh.put("buyer_name", "" + first_name + " " + last_name + "");

                BuyerInfoh.put("byrsig", "" + first_name + "_" + last_name + "");

                BuyerInfoh.put("buyer_phone", phone);

                BuyerInfoh.put("buyer_fax", fax);

                BuyerInfoh.put("buyer_email", email);


                numberRec += 1;

            }

            //buyerInfoRs.close();

        }

        catch (Exception sqle) {

            sqle.printStackTrace();

        }

        finally {

            if (dbrs != null) {

                try {

                    dbrs.close();

                }

                catch (SQLException sqle) {

                    log.error("SQL Exception on close in build send specs (Spec Data)");

                }

            }

        }


        try {

            int numberRec = 0;


            String stmt = "select FIRST_NAME, LAST_NAME, MID_INITIAL, EMAIL from inventory_group_expeditor_view where inventory_group='" + invengrp + "'";

            //String stmt="select FIRST_NAME, LAST_NAME, MID_INITIAL, EMAIL from personnel x, user_group_member_ig y where x.personnel_id= y.personnel_id and x.company_id = 'Radian' and ";

            //stmt+=" y.user_group_id = 'ShippingNotice' and y.inventory_group = '"+invengrp+"' ";

            // dbrs=db.doQuery( stmt );

            ResultSet buyerInfoRs = doQuery(stmt);

            Hashtable result = new Hashtable();


            result = new Hashtable();

            while (buyerInfoRs.next()) {

                String first_name = buyerInfoRs.getString("FIRST_NAME") == null ? "" : buyerInfoRs.getString("FIRST_NAME").trim();

                String last_name = buyerInfoRs.getString("LAST_NAME") == null ? "" : buyerInfoRs.getString("LAST_NAME").trim();

                //String phone=buyerInfoRs.getString( "PHONE" ) == null ? "" : buyerInfoRs.getString( "PHONE" ).trim();

                String email = buyerInfoRs.getString("EMAIL") == null ? "" : buyerInfoRs.getString("EMAIL").trim();

                //String fax=buyerInfoRs.getString( "FAX" ) == null ? "" : buyerInfoRs.getString( "FAX" ).trim();


                BuyerInfoh.put("expediter_name", "" + first_name + " " + last_name + "");

                BuyerInfoh.put("expediter_email", email);


                numberRec += 1;

            }

            //buyerInfoRs.close();

        }

        catch (Exception sqle) {

            sqle.printStackTrace();

        }

        finally {

            if (dbrs != null) {

                try {

                    dbrs.close();

                }

                catch (SQLException sqle) {

                    log.error("SQL Exception on close in build send specs (Spec Data)");

                }

            }

        }


        return BuyerInfoh;

    }


    protected String getimageseqnum(boolean creatbpo) {

        String imgaSeqnum = "";


        try {

            String stmt = "";

            if (creatbpo) {

                stmt = "select PO_IMAGE_seq.nextval IMGSEQ from dual";

            } else {

                stmt = "select PO_IMAGE_seq.nextval IMGSEQ from dual";

            }

            dbrs = doQuery(stmt);


            while (dbrs.next()) {

                imgaSeqnum = dbrs.getString("IMGSEQ") == null ? "" : dbrs.getString("IMGSEQ").trim();

            }

        }

        catch (Exception sqle) {

            sqle.printStackTrace();

        }

        finally {

            try {

                dbrs.close();

            }

            catch (SQLException sqle) {

                log.error("SQL Exception on close in get images seq num");

            }

        }


        return imgaSeqnum;

    }


    protected Hashtable getShipperinfo(String carrier, Hashtable poheaderInfo) throws Exception {

        Hashtable ShipperInfoh = new Hashtable();


        ShipperInfoh.put("company_id", poheaderInfo.get("CARRIER_COMPANY_ID").toString());

        ShipperInfoh.put("client_acct", poheaderInfo.get("CARRIER_ACCOUNT").toString());

        ShipperInfoh.put("add_note", "");

        ShipperInfoh.put("shipper_name", poheaderInfo.get("CARRIER_NAME").toString());

        //if carrier code in carrier_info is 3rd partyy billing then BILL_TO_LOC_ID should be not null

        try {

            String stmt = " select l.* from carrier_info c , location l where c.BILL_TO_LOC_ID = l.location_id and ";

            stmt += " c.company_id = l.company_id and carrier_code = '" + carrier + "' ";


            dbrs = doQuery(stmt);

            int numberRec = 0;


            while (dbrs.next()) {

                String state_abbrev = dbrs.getString("STATE_ABBREV") == null ? "" : dbrs.getString("STATE_ABBREV").trim();

                String address_line_1 = dbrs.getString("ADDRESS_LINE_1") == null ? "" : dbrs.getString("ADDRESS_LINE_1").trim();

                String address_line_2 = dbrs.getString("ADDRESS_LINE_2") == null ? "" : dbrs.getString("ADDRESS_LINE_2").trim();

                String address_line_3 = dbrs.getString("ADDRESS_LINE_3") == null ? "" : dbrs.getString("ADDRESS_LINE_3").trim();

                String city = dbrs.getString("CITY") == null ? "" : dbrs.getString("CITY").trim();

                String zip = dbrs.getString("ZIP") == null ? "" : dbrs.getString("ZIP").trim();

                String company_id = dbrs.getString("COMPANY_ID") == null ? "" : dbrs.getString("COMPANY_ID").trim();

                if ("Radian".equalsIgnoreCase(company_id)) {

                    company_id = "Haas TCM";

                }

                ShipperInfoh.put("bill1", "All shipping invoices are billed third party to:");

                ShipperInfoh.put("company", company_id);

                ShipperInfoh.put("bill_to_address1", address_line_1);

                ShipperInfoh.put("bill_to_address2", address_line_2);

                ShipperInfoh.put("bill_to_address3", address_line_3);

                ShipperInfoh.put("bill2", "" + city + ", " + state_abbrev + " " + zip + "\n\nHaas TCM will not pay freight invoices billed to our company");

                numberRec += 1;

            }


            if (numberRec == 0) {

                ShipperInfoh.put("bill1", "");

                ShipperInfoh.put("company", "");

                ShipperInfoh.put("bill_to_address1", "");

                ShipperInfoh.put("bill_to_address2", "");

                ShipperInfoh.put("bill_to_address3", "");

                ShipperInfoh.put("bill2", "");

                ShipperInfoh.put("SHIPPERCOMMENTS", "See Details Below");

            } else {

                ShipperInfoh.put("SHIPPERCOMMENTS", "3rd Party See Details Below");

            }


            dbrs.close();

        }

        catch (Exception sqle) {

            log.error("Error In  SQL ShipperInfoNull Query2 :");

            sqle.printStackTrace();

            throw sqle;

        }

        finally {

            try {

                if (dbrs != null)

                    dbrs.close();

            }

            catch (SQLException sqle) {

                log.error("SQL Exception on close in get shipper info");

            }

        }

        return ShipperInfoh;

    }


    protected Vector getMSDSRevisionDate(String itemId) {

        Vector Msdsdate = new Vector();

        boolean itemIdDone = false;


        String stmt;

        try {

            int numberRec = 0;

            stmt = "select ITEM_ID,DESCRIPTION,MANUFACTURER,PACKAGING,REVISION_DATE,ON_LINE from item_component_view where item_id =" + itemId + "";


            dbrs = doQuery(stmt);

            Hashtable result = new Hashtable();

            result = new Hashtable();


            while (dbrs.next()) {

                result = new Hashtable();

                String attach = dbrs.getString("REVISION_DATE") == null ? "" : dbrs.getString("REVISION_DATE").trim();

                result.put("REVISION_DATE", attach);

                Msdsdate.add(numberRec, result);


                if (msdsmessage.indexOf(itemId) > 0) {

                    itemIdDone = true;

                } else {

                    if (numberRec == 0) {

                        msdsmessage += "*********************************************************************\n";

                        msdsmessage += "* IF ITEM # " + itemId + " HAS MATERIAL WITH MSDS REVISION LATER THAN ";

                    }

                    if (numberRec > 1) {

                        msdsmessage = " or ";

                    }

                    msdsmessage += attach;

                }


                numberRec += 1;

            }

            //if ( !itemIdDone )

            {

                if (numberRec > 0) {

                    msdsmessage += "\nWE REQUIRE YOU TO FAX UPDATED MSDS(s) REFERENCING ITEM # " + itemId + " TO 512-519-3990\n";

                }

            }


            dbrs.close();

        }

        catch (Exception sqle) {

            sqle.printStackTrace();

        }

        finally {

            try {

                if (dbrs != null)

                    dbrs.close();

            }

            catch (SQLException sqle) {

                log.error("SQL Exception on close in get MSDS revision date");

            }

        }

        return Msdsdate;

    }


    public String buildPage2(String PurchaseOrder, String bpoNumber, String buyername, Vector addchargeslist, String headerNote, String personnelID) throws Exception {

        erw = new ACJEngine();

        eClient = new ACJOutputProcessor();


        String order_total = "";

        // Connection connection;

        String branch_plant = "";

        String shipper_name = "";

        String shelf_life = "";

        String Payment_terms = "";

        String template = "";

        String signature = "";

        boolean creatingblanketpo = false;


        if (!(PurchaseOrder.length() > 0 && !"N".equalsIgnoreCase(PurchaseOrder))) {

            creatingblanketpo = true;

        }


        erw.setX11GfxAvailibility(false);


        String fontmappath = tcmisResourceLoader.getString("fontpropertiespath");

        eClient.setPathForFontMapFile(fontmappath);


        Hashtable AddressInfo = new Hashtable();

        Vector JDEpoInfo = new Vector();

        Vector MoreComments = new Vector();

        Hashtable JDEpoInfoH = new Hashtable();

        Hashtable MSDSrevdate = new Hashtable();

        Hashtable PoInfo = new Hashtable();

        Hashtable ShipperInfo = new Hashtable();

        Hashtable PoData = new Hashtable();

        Hashtable BuyerInfo = new Hashtable();

        Hashtable Msds_Datess = new Hashtable();

        Vector Msds_Date = new Vector();

        Vector Po_Date = new Vector();


        try {

            AddressInfo = getAddressInfo(PurchaseOrder, bpoNumber);

            if (AddressInfo == null || (AddressInfo != null && AddressInfo.size() < 1)) {

                log.error("buildpage: no address info!");

                return null;

            }

        }

        catch (Exception ee) {

            log.error("buildpage: exception 1 (getAddressInfo):" + ee);

            ee.printStackTrace();

            return null;

        }


        branch_plant = AddressInfo.get("branch_plant").toString();

        String shiptoloc = AddressInfo.get("SHIP_TO_LOCATION_ID").toString();

        String bpoenddate = AddressInfo.get("BO_END_DATE").toString();

        String shiptoloccompanyId = AddressInfo.get("SHIP_TO_COMPANY_ID").toString();

        String invengrp = AddressInfo.get("INVENTORY_GROUP").toString();

        String posupplier = AddressInfo.get("SUPPLIER").toString();


        try {

            PoInfo = getPoInfo(PurchaseOrder, bpoNumber, addchargeslist, branch_plant, shiptoloc, shiptoloccompanyId, bpoenddate, invengrp, headerNote);

            order_total = PoInfo.get("TOTAL_PRICE").toString();

            if (PoInfo.get("NOOFLINES").toString().equalsIgnoreCase("0")) {

                log.error("buildpage: nooflines=0!");

                return null;

            }

        }

        catch (Exception ee1) {

            log.error("buildpage: exception2 (getPOInfo): " + ee1);

            return null;

        }


        Po_Date = (Vector) PoInfo.get("LINES");

        Hashtable PoDataTest = (Hashtable) Po_Date.elementAt(0);


        try {

            JDEpoInfo = getPoLineNotes(Po_Date);


            if (JDEpoInfo.size() < 1) {

                log.error("buildpage: no JDEPo info!");

                return null;

            }

        }

        catch (Exception ee2) {

            log.error("buildpage: exception3 (getJDEPoInfo): " + ee2);

            return null;

        }


        MoreComments = getmoreComments();

        String buyerId = AddressInfo.get("BUYER").toString();

        JDEpoInfoH = (Hashtable) JDEpoInfo.elementAt(0);

        BuyerInfo = getBuyerInfo(PurchaseOrder, buyerId, invengrp);

        shelf_life = PoDataTest.get("REMAINING_SHELF_LIFE_PERCENT").toString();

        if (shelf_life.trim().length() > 0) {

            shelf_life = shelf_life + " % shelf life required upon delivery";

        }

        Payment_terms = AddressInfo.get("PAYMENT_TERMS").toString();

        String carrier = AddressInfo.get("CARRIER").toString();


        if (!(carrier.trim().length() > 0 || creatingblanketpo)) {

            log.error("buildpage: no carrier info!");

            return null;

        }


        try {

            ShipperInfo = getShipperinfo(carrier, AddressInfo);

        }

        catch (Exception ee) {

            log.error("buildpage: excpetion4 (getShipperInfo): " + ee);

            return null;

        }


        shipper_name = ShipperInfo.get("shipper_name").toString();


        try {

            signature = tcmisResourceLoader.getString("buyerimageloc") + "" + BuyerInfo.get("byrsig").toString() + "_.gif";

        }

        catch (Exception ee2) {

            log.error("buildpage: excpetion5 (getBuyerImageLoc): " + ee2);

            return null;

        }


        String customerpoSw = AddressInfo.get("CUSTOMER_PO").toString();

        String Msds_dates = "* SHIPPING PAPERS, INVOICES, AND CARTONS MUST REFERENCE PO #";

        if (customerpoSw.trim().length() > 0) {

            Msds_dates += "s [" + customerpoSw + " , " + PurchaseOrder + "].\n";

        } else {

            Msds_dates += " " + PurchaseOrder + ".\n";

        }


        Msds_dates += "*********************************************************************";

        if (!(branch_plant.equalsIgnoreCase("2202") || branch_plant.equalsIgnoreCase("2103")))


        {

            Msds_dates += "\n* HAAS TCM ITEM # " + radianItems + " MUST APPEAR ON ALL SHIPPING DOCUMENTS. \n";

            Msds_dates += "*********************************************************************";

        }

        Msds_dates += "\n* MATERIAL SAFETY DATA SHEETS (MSDS) MUST BE ENCLOSED WITH PRODUCT.\n";

        Msds_dates += msdsmessage;


        if (creatingblanketpo) {

            Msds_dates = "\n* QUANTITY SHOWN ON BLANKET ORDER IS ESTIMATE ONLY, NOT BILLABLE.\n";

            Msds_dates += "\n* PURCHASE ORDER RELEASES FOR SPECIFIC QUANTITIES AND DELIVERY DATES WILL FOLLOW.\n";

            Msds_dates += "\n* ONLY PURCHASE ORDER RELEASES ARE BILLABLE.\n";

            Msds_dates += "\n* THIS BLANKET ORDER IS VALID THROUGH " + bpoenddate + ".\n";

        }


        String tempPath = tcmisResourceLoader.getString("savepopath");

        String logo = tcmisResourceLoader.getString("pologourl");

        String myURL = tcmisResourceLoader.getString("pofinalurl");

        template = tcmisResourceLoader.getString("potemplatepath");

        String wwwHome = tcmisResourceLoader.getString("hosturl");


        if (creatingblanketpo) {

            template = template + "bpopurchase_order_template.erw";

        } else {

            if (COAcount > 0) {

                template = template + "purchase_order_template_coa.erw";

            } else {

                template = template + "purchase_order_template.erw";

            }

        }


        try {

            erw.readTemplate(template);

        }

        catch (Exception ex) {

            log.error("buildpage: excpetion6 (readTemplate): " + ex);

            ex.printStackTrace();

            return null;

        }


        String where02 = null;

        where02 = " RADIAN_PO = " + PurchaseOrder + " AND TOTAL_PRICE_PAID > 0.1";


        String where04 = null;

        where04 = "radian_po =" + PurchaseOrder;

        String prnitType = "";

        try {

            tm = erw.getTemplateManager();


            buyername = BuyerInfo.get("buyer_name").toString();

            tm.setLabel("IMA001", logo);

            tm.setLabel("IMA000", signature);

            tm.setLabel("LAB037", buyername);

            tm.setLabel("LAB038", Payment_terms);

            tm.setLabel("LAB062", BuyerInfo.get("buyer_phone").toString());

            tm.setLabel("LBL011", BuyerInfo.get("buyer_email").toString());


            String shippername = ShipperInfo.get("shipper_name").toString();

            if ("Pre Pay and Add".equalsIgnoreCase(shippername) || "Vendor Truck".equalsIgnoreCase(shippername)) {

                tm.setLabel("LAB020", "* Shipping must be per buyer instructions. ");

                tm.setLabel("NOTES_LABLE", "");

            }


            tm.setLabel("LAB006", "" + shippername + " / " + ShipperInfo.get("client_acct").toString() + "");

            tm.setLabel("APROVEDSHIPER", shippername);

            tm.setLabel("APPROVEDACCOUNT", ShipperInfo.get("client_acct").toString());

            tm.setLabel("SHIPPERCOMMENTS", ShipperInfo.get("SHIPPERCOMMENTS").toString());

            tm.setLabel("LAB047", AddressInfo.get("SUPPLIER_CONTACT_NAME").toString());

            tm.setLabel("LBL005", "" + AddressInfo.get("SUPPLIER_CONTACT_FAX").toString() + " / " + AddressInfo.get("SUPPLIER_CONTACT_PHONE").toString() + "");

            tm.setLabel("LAB049", ShipperInfo.get("add_note").toString());

            tm.setLabel("LAB051", PurchaseOrder);

            if (PurchaseOrder.length() > 0 && !"N".equalsIgnoreCase(PurchaseOrder)) {

                tm.setLabel("PURCAHSE_ORDER_NO", PurchaseOrder);

            } else {

                tm.setLabel("PURCAHSE_ORDER_NO", bpoNumber);

            }


            tm.setLabel("LAB052", AddressInfo.get("vendor_name").toString());

            tm.setLabel("LAB053", AddressInfo.get("vendor_address1").toString());

            tm.setLabel("LAB063", AddressInfo.get("vendor_address2").toString());

            tm.setLabel("LAB064", AddressInfo.get("vendor_address3").toString());

            tm.setLabel("LAB054", AddressInfo.get("vendor_city").toString());

            tm.setLabel("LAB055", AddressInfo.get("vendor_state").toString());

            tm.setLabel("LAB056", AddressInfo.get("vendor_zipcode").toString());

            tm.setLabel("LAB026", AddressInfo.get("ORIGINAL_ORDER_NUM").toString());

            tm.setLabel("LAB057", AddressInfo.get("shipto_company").toString());

            tm.setLabel("LAB058", AddressInfo.get("shipto_address1").toString());

            tm.setLabel("LAB065", AddressInfo.get("shipto_address2").toString());

            tm.setLabel("LAB066", AddressInfo.get("shipto_address3").toString());

            tm.setLabel("LAB059", AddressInfo.get("shipto_city").toString());

            tm.setLabel("LAB060", AddressInfo.get("shipto_state").toString());

            tm.setLabel("LAB061", AddressInfo.get("shipto_zip").toString());

            tm.setLabel("FORSUM", "$" + order_total + "");

            tm.setLabel("LBL007", BuyerInfo.get("buyer_fax").toString());

            tm.setLabel("MSDS_DATE_TEST", Msds_dates);


            if ("0".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  New Order  ***");

                prnitType = "Draft";

            }

            if ("1".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  New Order  ***");

                prnitType = "New Order";

            } else if ("2".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  Confirming Order  ***");

                tm.setLabel("LBL008", "Already placed with " + AddressInfo.get("SUPPLIER_CONTACT_NAME").toString() + ". DO NOT DUPLICATE ");

                prnitType = "Confirming Order";

            } else if ("3".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  Amended Order  ***");

                tm.setLabel("LBL008", "See changes marked below");

                prnitType = "Amended Order";

            } else if ("4".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  Canceled Order  ***");

                prnitType = "Canceled Order";

            } else {

                tm.setLabel("LBL009", "");

            }

        }

        catch (Exception ex) {

            log.error("buildpage: excpetion7 (getTemplateManager/setLabel): " + ex);

            ex.printStackTrace();

            return null;

        }


        try {

            if (branch_plant.equalsIgnoreCase("2202") && "Berry Logistics".equalsIgnoreCase(shipper_name)) {

                tm.setLabel("LAB033", "Berry Logistics Phone# 877-737-1600");

            }

            //else if ( branch_plant.equalsIgnoreCase( "2101" ) || branch_plant.equalsIgnoreCase( "2102" ) || branch_plant.equalsIgnoreCase( "2103" ) )

            //else if ( !branch_plant.equalsIgnoreCase( "2202" ) )

            //{

            tm.setLabel("LAB033", ShipperInfo.get("bill1").toString());

            tm.setLabel("LAB042", ShipperInfo.get("company").toString());

            tm.setLabel("LAB043", ShipperInfo.get("bill_to_address1").toString());

            tm.setLabel("LAB044", ShipperInfo.get("bill_to_address2").toString());

            tm.setLabel("LAB045", ShipperInfo.get("bill_to_address3").toString());

            tm.setLabel("LAB034", ShipperInfo.get("bill2").toString());

            //}

        }

        catch (Exception ex) {

            log.error("buildpage: excpetion8 (branchPlant-setLabel): " + ex);

            ex.printStackTrace();

            return null;

        }


        AppDataHandler ds;


        try {

            ds = new AppDataHandler();

            RegisterTable rt[] = getData(Po_Date);

            for (int i = 0; i < rt.length; i++) {

                ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());

            }

            //canned info

            if (Flow_Data.size() == 0) {

                Hashtable SpecData = new Hashtable();

                SpecData.put("FLOW_DOWN_DESC", " ");

                SpecData.put("CONTENT", " ");

                SpecData.put("LINE_ITEM", " ");

                Flow_Data.addElement(SpecData);

            }

            RegisterTable rt2[] = getFlowData(Flow_Data);

            for (int i = 0; i < rt2.length; i++) {

                ds.RegisterTable(rt2[i].getData(), rt2[i].getName(), rt2[i].getMethods(), rt2[i].getWhere());

            }

            //buyer note

            RegisterTable rt1[] = getCommentData(JDEpoInfo);

            for (int i = 0; i < rt1.length; i++) {

                ds.RegisterTable(rt1[i].getData(), rt1[i].getName(), rt1[i].getMethods(), rt1[i].getWhere());

            }

            //more note

            RegisterTable rt3[] = getMoreCommentsData(MoreComments);

            for (int i = 0; i < rt3.length; i++) {

                ds.RegisterTable(rt3[i].getData(), rt3[i].getName(), rt3[i].getMethods(), rt3[i].getWhere());

            }


            erw.setDataSource(ds);

        }

        catch (Exception e) {

            log.error("buildpage: excpetion9 (AppDataHandler): " + e);

            e.printStackTrace();

        }


        try {

            IViewerInterface ivi = erw.generateReport();

            if (!eClient.setReportData(ivi, null))

                System.exit(0);

        }

        catch (Exception ex) {

            log.error("buildpage: excpetion10 (generateReport/setReportData): " + ex);

            ex.printStackTrace();

            return null;

        }

        /*Random rand = new Random();

    int tmpReq = rand.nextInt();

    Integer tmpReqNum = new Integer(tmpReq);*/


        String imageSeq = getimageseqnum(creatingblanketpo);

        String filename11 = "";

        if (creatingblanketpo) {

            filename11 = bpoNumber;

        } else {

            filename11 = PurchaseOrder;

        }


        try {

            eClient.generatePDF(tempPath + "po_" + filename11 + "_" + imageSeq + ".pdf", null);

        }

        catch (Exception e) {

            log.error("buildpage: excpetion11 (generatePDF): " + e);

            e.printStackTrace();

            return null;

        }


        String finalurl = wwwHome + myURL + "po_" + filename11 + "_" + imageSeq + ".pdf";

        /*

        MsgBuild.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0;url=" + finalurl + "\"></HEAD>" );

        MsgBuild.append( "<BODY></BODY></HTML>" );

           */


        try {

            String postmt = "";

            if (creatingblanketpo) {

                postmt = "insert into BPO_IMAGE (IMAGE_ID, BPO, DATE_PRINTED, FILE_URL, SUPPLIER, PRINTED_BY, PRINT_TYPE) values (" + imageSeq + "," + bpoNumber + ",sysdate,'" + finalurl + "','" + posupplier + "'," + personnelID + ",'" + prnitType + "') ";

            } else {

                postmt = "insert into PO_IMAGE (IMAGE_ID, RADIAN_PO, DATE_PRINTED, FILE_URL, SUPPLIER, PRINTED_BY, PRINT_TYPE) values (" + imageSeq + "," + PurchaseOrder + ",sysdate,'" + finalurl + "','" + posupplier + "'," + personnelID + ",'" + prnitType + "') ";

            }


            doUpdate(postmt);

        }

        catch (Exception sqle) {

            log.error("buildpage: excpetion12 (gdb.update): " + sqle);

            sqle.printStackTrace();

        }


        AddressInfo = null;

        JDEpoInfo = null;

        JDEpoInfoH = null;

        MSDSrevdate = null;

        PoInfo = null;

        ShipperInfo = null;

        PoData = null;

        BuyerInfo = null;

        Msds_Datess = null;

        Flow_Data = null;

        Msds_Date = null;

        Po_Date = null;

        Flow_Data = null;

        radianItems = "";

        msdsmessage = "";

        // return MsgBuild;

        return finalurl;

    }


    public String buildPage(String PurchaseOrder, String bpoNumber, String buyername, Vector addchargeslist, String headerNote, String personnelID) throws Exception {

        erw = new ACJEngine();

        eClient = new ACJOutputProcessor();


        StringBuilder MsgBuild = new StringBuilder();

        String order_total = "";

        // Connection connection;

        String branch_plant = "";

        String shipper_name = "";

        String shelf_life = "";

        String Payment_terms = "";

        String template = "";

        String signature = "";

        boolean creatingblanketpo = false;

        String whichorderstg = "";


        if (!(PurchaseOrder.length() > 0 && !"N".equalsIgnoreCase(PurchaseOrder))) {

            creatingblanketpo = true;

            whichorderstg = "Blanket";

        } else {

            whichorderstg = "Purchase";

        }


        erw.setX11GfxAvailibility(false);

        // String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();

        String fontmappath = tcmisResourceLoader.getString("fontpropertiespath");

        eClient.setPathForFontMapFile(fontmappath);


        Hashtable AddressInfo = new Hashtable();

        Vector JDEpoInfo = new Vector();

        Vector MoreComments = new Vector();

        Hashtable JDEpoInfoH = new Hashtable();

        Hashtable MSDSrevdate = new Hashtable();

        Hashtable PoInfo = new Hashtable();

        Hashtable ShipperInfo = new Hashtable();

        Hashtable PoData = new Hashtable();

        Hashtable BuyerInfo = new Hashtable();

        Hashtable Msds_Datess = new Hashtable();

        Vector Msds_Date = new Vector();

        Vector Po_Date = new Vector();

        Hashtable invocieAddInfo = new Hashtable();


        try {

            AddressInfo = getAddressInfo(PurchaseOrder, bpoNumber);

            if (AddressInfo.size() < 1) {

                MsgBuild.append(PrintError("There are no records matching this PO"));

                // return MsgBuild;

                return null;

            }

        }

        catch (Exception ee) {

            MsgBuild.append(PrintError("Error In AddressInfo Query:"));

            ee.printStackTrace();

            // return MsgBuild;

            return null;

        }


        branch_plant = AddressInfo.get("branch_plant").toString();

        String shiptoloc = AddressInfo.get("SHIP_TO_LOCATION_ID").toString();

        String bpoenddate = AddressInfo.get("BO_END_DATE").toString();

        String shiptoloccompanyId = AddressInfo.get("SHIP_TO_COMPANY_ID").toString();

        String invengrp = AddressInfo.get("INVENTORY_GROUP").toString();

        String posupplier = AddressInfo.get("SUPPLIER").toString();

        String fobterms = AddressInfo.get("FREIGHT_ON_BOARD").toString();

        String homecompany = AddressInfo.get("HOME_COMPANY_NAME").toString();

        String logo = AddressInfo.get("PO_IMAGE_URL").toString();

        String shortcompayname = AddressInfo.get("OPERATING_ENTITY_SHORT_NAME").toString();

        String billlocationid = AddressInfo.get("BILL_TO_LOCATION_ID").toString();


        try {

            PoInfo = getPoInfo(PurchaseOrder, bpoNumber, addchargeslist, branch_plant, shiptoloc, shiptoloccompanyId, bpoenddate, invengrp, headerNote);

            order_total = PoInfo.get("TOTAL_PRICE").toString();

            if (PoInfo.get("NOOFLINES").toString().equalsIgnoreCase("0")) {

                MsgBuild.append(PrintError("There are no open lines for this PO"));

                // return MsgBuild;

                return null;

            }

        }

        catch (Exception ee1) {

            MsgBuild.append(PrintError("Error In Po Info Query:"));

            // return MsgBuild;

            return null;

        }


        Po_Date = (Vector) PoInfo.get("LINES");

        Hashtable PoDataTest = (Hashtable) Po_Date.elementAt(0);


        try {

            JDEpoInfo = getPoLineNotes(Po_Date);


            if (JDEpoInfo.size() < 1) {

                MsgBuild.append(PrintError("A Error Occured While Formating Line Comments"));

                // return MsgBuild;

                return null;

            }

        }

        catch (Exception ee2) {

            MsgBuild.append(PrintError("Error In JDEPo Info Query:"));

            // return MsgBuild;

            return null;

        }


        MoreComments = getmoreComments();

        String buyerId = AddressInfo.get("BUYER").toString();

        JDEpoInfoH = (Hashtable) JDEpoInfo.elementAt(0);

        BuyerInfo = getBuyerInfo(PurchaseOrder, buyerId, invengrp);

        shelf_life = PoDataTest.get("REMAINING_SHELF_LIFE_PERCENT").toString();

        if (shelf_life.trim().length() > 0) {

            shelf_life = "At least " + shelf_life + " % shelf life required upon delivery";

        }

        Payment_terms = AddressInfo.get("PAYMENT_TERMS").toString();

        String carrier = AddressInfo.get("CARRIER").toString();


        if (!(carrier.trim().length() > 0 || creatingblanketpo)) {

            MsgBuild.append(PrintError("There is no Shipper Associated With This PO."));

            // return MsgBuild;

            return null;

        }


        try {

            ShipperInfo = getShipperinfo(carrier, AddressInfo);

        }

        catch (Exception ee) {

            log.error("Error getShipperinfo", ee);
            MsgBuild.append(PrintError("Error In Shipper Info Query:"));

            // return MsgBuild;

            return null;

        }


        shipper_name = ShipperInfo.get("shipper_name").toString();


        try {

            invocieAddInfo = getInvoiceaddress(billlocationid);

        }

        catch (Exception ee) {
            log.error("Error getInvoiceaddress", ee);

            MsgBuild.append(PrintError("Error In Invocie Address Query:"));

            // return MsgBuild;

            return null;

        }


        try {

            signature = tcmisResourceLoader.getString("buyerimageloc") + "" + BuyerInfo.get("byrsig").toString() + "_.gif";

        }

        catch (Exception ee2) {
            log.error("Error creating Buer sig URL", ee2);
            MsgBuild.append(PrintError("Don't Know Buyer Information."));

            // return MsgBuild;

            return null;

        }


        String customerpoSw = AddressInfo.get("CUSTOMER_PO").toString();

        String Msds_dates = "* SHIPPING PAPERS, INVOICES, AND CARTONS MUST REFERENCE PO #";

        if (customerpoSw.trim().length() > 0) {

            Msds_dates += "s [" + customerpoSw + " , " + PurchaseOrder + "].\n";

        } else {

            Msds_dates += " " + PurchaseOrder + ".\n";

        }


        Msds_dates += "*********************************************************************";

        if (!(branch_plant.equalsIgnoreCase("2202") || branch_plant.equalsIgnoreCase("2103"))) {

            Msds_dates += "\n* SHOW " + homecompany.toUpperCase() + " ITEM # " + radianItems + " ON ALL SHIPPING DOCUMENTS. \n";

            Msds_dates += "*********************************************************************";

        }

        Msds_dates += "\n* MATERIAL SAFETY DATA SHEETS (MSDS) MUST BE ENCLOSED WITH PRODUCT AS APPLICABLE.\n";

        Msds_dates += msdsmessage;


        if (creatingblanketpo) {

            Msds_dates = "\n* QUANTITY SHOWN ON BLANKET ORDER IS ESTIMATE ONLY, NOT BILLABLE.\n";

            Msds_dates += "\n* PURCHASE ORDER RELEASES FOR SPECIFIC QUANTITIES AND DELIVERY DATES WILL FOLLOW.\n";

            Msds_dates += "\n* ONLY PURCHASE ORDER RELEASES ARE BILLABLE.\n";

            Msds_dates += "\n* THIS BLANKET ORDER IS VALID THROUGH " + bpoenddate + ".\n";

        }


        String tempPath = tcmisResourceLoader.getString("savepopath");

        //String logo=radian.web.tcmisResourceLoader.getpologourl();

        String myURL = tcmisResourceLoader.getString("pofinalurl");

        template = tcmisResourceLoader.getString("potemplatepath");

        String wwwHome = tcmisResourceLoader.getString("hosturl");


        if (creatingblanketpo) {

            template = template + "bpopurchase_order_template.erw";

        } else {

            if (COAcount > 0) {

                template = template + "purchase_order_template_coa.erw";

            } else {

                template = template + "purchase_order_template.erw";

            }

        }


        try {

            erw.readTemplate(template);

        }

        catch (Exception ex) {

            MsgBuild.append("<P><B>Error!!</B></P>");

            MsgBuild.append("<P>ERROR LOADING PURCHASE ORDER TEMPLATE</P>");

            ex.printStackTrace();

            // return MsgBuild;

            return null;

        }


        String where02 = null;

        where02 = " RADIAN_PO = " + PurchaseOrder + " AND TOTAL_PRICE_PAID > 0.1";


        String where04 = null;

        where04 = "radian_po =" + PurchaseOrder;

        String prnitType = "";

        try {

            tm = erw.getTemplateManager();


            buyername = BuyerInfo.get("buyer_name").toString();

            tm.setLabel("IMA001", logo);

            tm.setLabel("COMPANY_NAME", homecompany);

            tm.setLabel("SALES_TAX_STMT", "No sales tax on " + homecompany + " orders. A copy of the resale certificate will be provided upon request.");

            tm.setLabel("IMA000", signature);

            tm.setLabel("LAB037", buyername);

            tm.setLabel("LAB038", Payment_terms);

            tm.setLabel("LAB062", BuyerInfo.get("buyer_phone").toString());

            tm.setLabel("LBL011", BuyerInfo.get("buyer_email").toString());

            tm.setLabel("ITEM_LABEL", "" + shortcompayname + " Item ID");

            if (COAcount > 0) {

                String coacomments = "";

                coacomments += "* Must include at least one of the following: Manufacturer's Part number; " + shortcompayname + " item number; Customer part number\n";

                coacomments += "* Manufacturer's Part Description and Unit Of Measure.\n";

                coacomments += "* Manufacturer's Name (NOTE:  If repacked, the original mfg name must still appear on the document.)\n";

                coacomments += "* Manufacturer's Lot Number (NOTE:  For raw materials that are repackaged, or processed and assigned a new lot number, this means that any certs provided must have enough info to link the repackaged/processed lot number back to certs provided for the component parts' lot numbers.)\n";


                tm.setLabel("COA_COMMENT", "" + coacomments + "");

            }


            tm.setLabel("TERMS_AND_CONDITIONS", AddressInfo.get("TERMS_AND_CONDITIONS").toString());


            String shippername = ShipperInfo.get("shipper_name").toString();

            if ("Pre Pay and Add".equalsIgnoreCase(shippername) || "Vendor Truck".equalsIgnoreCase(shippername)) {

                tm.setLabel("SHIPPING_NOTE", "* Shipping must be per buyer instructions. ");

                tm.setLabel("NOTES_LABLE", "");

            } else {

                String shipping_add_note = "* Shipping must be per buyer instructions.";

                shipping_add_note += homecompany + " will not pay freight invoices billed to our company.\n* Always Ship 3rd Party Unless Specified Freight Collect.";


                tm.setLabel("SHIPPING_NOTE", shipping_add_note);

            }


            tm.setLabel("LAB006", "" + shippername + " / " + ShipperInfo.get("client_acct").toString() + "");

            tm.setLabel("APROVEDSHIPER", shippername);

            tm.setLabel("APPROVEDACCOUNT", ShipperInfo.get("client_acct").toString());

            tm.setLabel("SHIPPERCOMMENTS", ShipperInfo.get("SHIPPERCOMMENTS").toString());

            tm.setLabel("LAB047", AddressInfo.get("SUPPLIER_CONTACT_NAME").toString());

            tm.setLabel("LBL005", "" + AddressInfo.get("SUPPLIER_CONTACT_FAX").toString() + " / " + AddressInfo.get("SUPPLIER_CONTACT_PHONE").toString() + "");


            tm.setLabel("LAB049", ShipperInfo.get("add_note").toString());

            tm.setLabel("LAB051", PurchaseOrder);

            if (PurchaseOrder.length() > 0 && !"N".equalsIgnoreCase(PurchaseOrder)) {

                tm.setLabel("PURCAHSE_ORDER_NO", PurchaseOrder);

                if (BuyerInfo.get("expediter_name") != null)

                    tm.setLabel("EXPDNAME", BuyerInfo.get("expediter_name").toString());

                else

                    tm.setLabel("EXPDNAME", "Buyer Above");


                if (BuyerInfo.get("expediter_email") != null)

                    tm.setLabel("EXPDEMAIL", BuyerInfo.get("expediter_email").toString());


                String invoicecomments = "";

                invoicecomments += "Invoices sent elsewhere may result in delayed payment.\n";

                invoicecomments += "Shipment sent driver collect will be refused.\n";

                invoicecomments += homecompany + " will not pay freight invoices billed to our company unless noted on purchase order.\n";


                tm.setLabel("INVOICE_COMMENTS", "" + invoicecomments + "");


                tm.setLabel("INVOICE_ADDRESS1", "" + homecompany + "");

                tm.setLabel("INVOICE_ADDRESS2", invocieAddInfo.get("ADDRESS_LINE1").toString());

                tm.setLabel("INVOICE_ADDRESS3", invocieAddInfo.get("ADDRESS_LINE2").toString());

            } else {

                tm.setLabel("PURCAHSE_ORDER_NO", bpoNumber);

            }


            tm.setLabel("LAB052", AddressInfo.get("vendor_name").toString());

            tm.setLabel("LAB053", AddressInfo.get("vendor_address1").toString());

            tm.setLabel("LAB063", AddressInfo.get("vendor_address2").toString());

            tm.setLabel("LAB064", AddressInfo.get("vendor_address3").toString());

            tm.setLabel("LAB054", AddressInfo.get("vendor_city").toString());

            tm.setLabel("LAB055", AddressInfo.get("vendor_state").toString());

            tm.setLabel("LAB056", AddressInfo.get("vendor_zipcode").toString());

            tm.setLabel("LAB026", AddressInfo.get("ORIGINAL_ORDER_NUM").toString());

            tm.setLabel("LAB057", AddressInfo.get("shipto_company").toString());

            tm.setLabel("LAB058", AddressInfo.get("shipto_address1").toString());

            tm.setLabel("LAB065", AddressInfo.get("shipto_address2").toString());

            tm.setLabel("LAB066", AddressInfo.get("shipto_address3").toString());

            tm.setLabel("LAB059", AddressInfo.get("shipto_city").toString());

            tm.setLabel("LAB060", AddressInfo.get("shipto_state").toString());

            tm.setLabel("LAB061", AddressInfo.get("shipto_zip").toString());

            tm.setLabel("FORSUM", "" + order_total + " " + currencyid + "");

            tm.setLabel("LBL007", BuyerInfo.get("buyer_fax").toString());

            tm.setLabel("MSDS_DATE_TEST", Msds_dates);


            if ("0".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  New " + whichorderstg + " Order  ***");

                prnitType = "Draft";

            }

            if ("1".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  New " + whichorderstg + " Order  ***");

                prnitType = "New Order";

            } else if ("2".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  Confirming " + whichorderstg + " Order  ***");

                tm.setLabel("LBL008", "Already placed with " + AddressInfo.get("SUPPLIER_CONTACT_NAME").toString() + ". DO NOT DUPLICATE ");

                prnitType = "Confirming Order";

            } else if ("3".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  Amended " + whichorderstg + " Order  ***");

                tm.setLabel("LBL008", "See changes marked below");

                prnitType = "Amended Order";

            } else if ("4".equalsIgnoreCase(headerNote)) {

                tm.setLabel("LBL009", "***  Canceled " + whichorderstg + " Order  ***");

                prnitType = "Canceled Order";

            } else {

                tm.setLabel("LBL009", "");

            }


            tm.setLabel("FOBTERMS", "" + fobterms + "");

            if ("EXW".equalsIgnoreCase(fobterms)) {

                tm.setLabel("FOBTERMSDESC", "Ex Works");

            } else if ("CIP".equalsIgnoreCase(fobterms)) {

                tm.setLabel("FOBTERMSDESC", "Carriage and Insurance Paid To");

            } else {

                tm.setLabel("FOBTERMSDESC", "" + fobterms + "");

            }


        }

        catch (Exception ex) {

            MsgBuild.append("<P><B>Error!!</B></P>");

            MsgBuild.append("<P>ERROR In Modifying PURCHASE ORDER TEMPLATE</P>");

            ex.printStackTrace();

            // return MsgBuild;

            return null;

        }


        try {

            if (branch_plant.equalsIgnoreCase("2202") && "Berry Logistics".equalsIgnoreCase(shipper_name)) {

                tm.setLabel("LAB033", "Berry Logistics Phone# 877-737-1600");

            }

            //else if ( branch_plant.equalsIgnoreCase( "2101" ) || branch_plant.equalsIgnoreCase( "2102" ) || branch_plant.equalsIgnoreCase( "2103" ) )

            //else if ( !branch_plant.equalsIgnoreCase( "2202" ) )

            //{

            tm.setLabel("LAB033", ShipperInfo.get("bill1").toString());

            tm.setLabel("LAB042", ShipperInfo.get("company").toString());

            tm.setLabel("LAB043", ShipperInfo.get("bill_to_address1").toString());

            tm.setLabel("LAB044", ShipperInfo.get("bill_to_address2").toString());

            tm.setLabel("LAB045", ShipperInfo.get("bill_to_address3").toString());

            tm.setLabel("LAB034", ShipperInfo.get("bill2").toString());

            //}

        }

        catch (Exception ex) {

            MsgBuild.append("<P><B>Error!!</B></P>");

            MsgBuild.append("<P>During Modify Template</P>");

            ex.printStackTrace();

            // return MsgBuild;

            return null;

        }


        AppDataHandler ds;


        try {

            ds = new AppDataHandler();

            RegisterTable rt[] = getData(Po_Date);

            for (int i = 0; i < rt.length; i++) {

                ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());

            }

            //canned info

            if (Flow_Data.size() == 0) {

                Hashtable SpecData = new Hashtable();

                SpecData.put("FLOW_DOWN_DESC", " ");

                SpecData.put("CONTENT", " ");

                SpecData.put("LINE_ITEM", " ");

                Flow_Data.addElement(SpecData);

            }

            RegisterTable rt2[] = getFlowData(Flow_Data);

            for (int i = 0; i < rt2.length; i++) {

                ds.RegisterTable(rt2[i].getData(), rt2[i].getName(), rt2[i].getMethods(), rt2[i].getWhere());

            }

            //buyer note

            RegisterTable rt1[] = getCommentData(JDEpoInfo);

            for (int i = 0; i < rt1.length; i++) {

                ds.RegisterTable(rt1[i].getData(), rt1[i].getName(), rt1[i].getMethods(), rt1[i].getWhere());

            }

            //more note

            RegisterTable rt3[] = getMoreCommentsData(MoreComments);

            for (int i = 0; i < rt3.length; i++) {

                ds.RegisterTable(rt3[i].getData(), rt3[i].getName(), rt3[i].getMethods(), rt3[i].getWhere());

            }


            erw.setDataSource(ds);

        }

        catch (Exception e) {

            e.printStackTrace();

        }


        try {

            IViewerInterface ivi = erw.generateReport();

            if (!eClient.setReportData(ivi, null))

                System.exit(0);

        }

        catch (Exception ex) {

            ex.printStackTrace();

            MsgBuild.append("<P><B>Error!!</B></P>");

            MsgBuild.append("<P>During Generating Report</P>");

            // return MsgBuild;

            return null;

        }

        /*Random rand = new Random();

    int tmpReq = rand.nextInt();

    Integer tmpReqNum = new Integer(tmpReq);*/


        String imageSeq = getimageseqnum(creatingblanketpo);

        String filename11 = "";

        if (creatingblanketpo) {

            filename11 = bpoNumber;

        } else {

            filename11 = PurchaseOrder;

        }


        try {

            eClient.generatePDF(tempPath + "po_" + filename11 + "_" + imageSeq + ".pdf", null);

        }

        catch (Exception e) {

            MsgBuild.append("<P><B>Error!!</B></P>");

            MsgBuild.append("<P>During Generating PDF Report</P>");

            e.printStackTrace();

            // return MsgBuild;

            return null;

        }


        String finalurl = wwwHome + myURL + "po_" + filename11 + "_" + imageSeq + ".pdf";

        MsgBuild.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0;url=" + finalurl + "\"></HEAD>");

        MsgBuild.append("<BODY></BODY></HTML>");


        try {

            String postmt = "";

            if (creatingblanketpo) {

                postmt = "insert into BPO_IMAGE (IMAGE_ID, BPO, DATE_PRINTED, FILE_URL, SUPPLIER, PRINTED_BY, PRINT_TYPE) values (" + imageSeq + "," + bpoNumber + ",sysdate,'" + finalurl + "','" + posupplier + "'," + personnelID + ",'" + prnitType + "') ";

            } else {

                postmt = "insert into PO_IMAGE (IMAGE_ID, RADIAN_PO, DATE_PRINTED, FILE_URL, SUPPLIER, PRINTED_BY, PRINT_TYPE) values (" + imageSeq + "," + PurchaseOrder + ",sysdate,'" + finalurl + "','" + posupplier + "'," + personnelID + ",'" + prnitType + "') ";

            }


            doUpdate(postmt);

        }

        catch (Exception sqle) {

            sqle.printStackTrace();

        }


        AddressInfo = null;

        JDEpoInfo = null;

        JDEpoInfoH = null;

        MSDSrevdate = null;

        PoInfo = null;

        ShipperInfo = null;

        PoData = null;

        BuyerInfo = null;

        Msds_Datess = null;

        Flow_Data = null;

        Msds_Date = null;

        Po_Date = null;

        Flow_Data = null;

        radianItems = "";

        msdsmessage = "";

        // return MsgBuild;

        return finalurl;

    }


    public RegisterTable[] getData(Vector reportData1) throws Exception {

        RegisterTable[] rt = new RegisterTable[1];


        try {

            rt[0] = new RegisterTable(PoData.getVector(reportData1), "PODATA", PoData.getFieldVector(), null);

        }

        catch (Exception e1) {

            e1.printStackTrace();

            throw e1;

        }

        return rt;

    }


    public RegisterTable[] getCommentData(Vector reportData1) throws Exception {

        RegisterTable[] rt = new RegisterTable[1];


        try {

            rt[0] = new RegisterTable(CommentsData.getVector(reportData1), "PURCH_JDE", CommentsData.getFieldVector(), null);

        }

        catch (Exception e1) {

            e1.printStackTrace();

            throw e1;

        }

        return rt;

    }


    public RegisterTable[] getFlowData(Vector reportData1) throws Exception {

        RegisterTable[] rt = new RegisterTable[1];


        try {

            rt[0] = new RegisterTable(FlowData.getVector(reportData1), "FLOWDATA", FlowData.getFieldVector(), null);

        }

        catch (Exception e1) {

            e1.printStackTrace();

            throw e1;

        }

        return rt;

    }


    public RegisterTable[] getMoreCommentsData(Vector reportData1) throws Exception {

        RegisterTable[] rt = new RegisterTable[1];


        try {

            rt[0] = new RegisterTable(MoreCommentsData.getVector(reportData1), "MORECOMMENTSDATA", MoreCommentsData.getFieldVector(), null);

        }

        catch (Exception e1) {

            e1.printStackTrace();

            throw e1;

        }

        return rt;

    }


    public String PrintError(String ErrMsg) {

        String errormsg = ErrMsg;

        errormsg += "<P>Please Try Again. </P>";

        errormsg += "<P>Thanks.</P>";

        log.error(ErrMsg);

        return errormsg;

    }


    protected Hashtable getInvoiceaddress(String locationid) {

        Hashtable invoiAddh = new Hashtable();

        // DBResultSet dbrs = null;

        ResultSet invocieaddrs = null;

        try {

            int numberRec = 0;


            String stmt = "select * from customer.location where location_id = '" + locationid + "'";

            // dbrs=db.doQuery( stmt );

            invocieaddrs = doQuery(stmt);

            Hashtable result = new Hashtable();


            result = new Hashtable();

            while (invocieaddrs.next()) {

                //String location_id=invocieaddrs.getString( "LOCATION_ID" ) == null ? "" : invocieaddrs.getString( "LOCATION_ID" ).trim();

                String country_abbrev = invocieaddrs.getString("COUNTRY_ABBREV") == null ? "" : invocieaddrs.getString("COUNTRY_ABBREV").trim();

                String state_abbrev = invocieaddrs.getString("STATE_ABBREV") == null ? "" : invocieaddrs.getString("STATE_ABBREV").trim();

                String address_line_1 = invocieaddrs.getString("ADDRESS_LINE_1") == null ? "" : invocieaddrs.getString("ADDRESS_LINE_1").trim();

                //String address_line_2=invocieaddrs.getString( "ADDRESS_LINE_2" ) == null ? "" : invocieaddrs.getString( "ADDRESS_LINE_2" ).trim();

                //String address_line_3=invocieaddrs.getString( "ADDRESS_LINE_3" ) == null ? "" : invocieaddrs.getString( "ADDRESS_LINE_3" ).trim();

                String city = invocieaddrs.getString("CITY") == null ? "" : invocieaddrs.getString("CITY").trim();

                String zip = invocieaddrs.getString("ZIP") == null ? "" : invocieaddrs.getString("ZIP").trim();

                //String location_desc=invocieaddrs.getString( "LOCATION_DESC" ) == null ? "" : invocieaddrs.getString( "LOCATION_DESC" ).trim();

                //String client_location_code = invocieaddrs.getString("CLIENT_LOCATION_CODE")==null?"":invocieaddrs.getString("CLIENT_LOCATION_CODE").trim();

                //String company_id=invocieaddrs.getString( "COMPANY_ID" ) == null ? "" : invocieaddrs.getString( "COMPANY_ID" ).trim();


                invoiAddh.put("ADDRESS_LINE1", "" + address_line_1 + "");

                invoiAddh.put("ADDRESS_LINE2", "" + city + " " + state_abbrev + " " + zip + "  " + country_abbrev + "");

            }

        }

        catch (Exception sqle) {
            log.error("Error", sqle);
            sqle.printStackTrace();

        }

        finally {

            try {

                if (dbrs != null)

                    dbrs.close();

            }

            catch (SQLException sqle) {

                log.error("SQL Exception on close in get MSDS revision date");

            }

        }


        return invoiAddh;

    }


    private ResultSet doQuery(String query) throws BaseException {
        ResultSet rs = null;
        DbManager dbManager = null;
        Connection conn = null;
        try {
            dbManager = new DbManager(this.getClient());
            conn = dbManager.getConnection();
            Statement stmt = conn.createStatement();
            log.debug("Query: " + query);
            rs = stmt.executeQuery(query);
        } catch (DbConnectionException dbce) {
            log.error("Db Connection Exception executing internal order process query: " + dbce);
        } catch (SQLException sqle) {
            log.error("SQL Exception executing internal order process query: " + sqle);
            sqle.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    dbManager.returnConnection(conn);
                }catch(Exception e){}
            }
        }
        return rs;
    }

    private void doUpdate(String query) throws BaseException {
        DbManager dbManager = null;
        Connection conn = null;
        try {
            dbManager = new DbManager(this.getClient());
            conn = dbManager.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (DbConnectionException dbce) {
            log.error("Db Connection Exception executing internal order process Update: " + dbce);
        } catch (SQLException sqle) {
            log.error("SQL Exception executing internal order process Update: " + sqle);
        }finally {
            if (conn != null) {
                try {
                    dbManager.returnConnection(conn);
                }catch(Exception e){}
            }
        }
    }
}

