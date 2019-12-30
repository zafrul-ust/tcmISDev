package radian.web.servlets.pos;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.process.PurchaseOrderProcess;

import oracle.jdbc.OracleTypes;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 01-14-03
 * Adding the new PROJECTED_DELIVERY_DATE
 *
 * 02-06-03
 * Adding buyer email
 * 04-07-03 - changed the view to po_line_confirm_print_view
 * 04-08-03 - aDDED NO SUBSTITUTIONS ALLOWED WHATSOEVER ON THE po
 * 05-21-03 - Cleanup code and removed data from code
 * 06-13-03 - Adding a random number to the PO name and changed code to reflect changes to inventory group stuff
 * 09-03-03 - Changing the shippinf information if the shipper is pre pay and add or vendor truck
 * 09-22-03 - Fax MSDS message bug
 * 10-31-03 - Frieght Billing address adding more comments for confirming order and amended order
 * 11-11-03 - Showing the Third address line for the thier party billing address
 * 11-26-03 - Showing Haas TCM for companyid when it is radian
 * 12-02-03 - Dropsships can have third party billing
 * 12-04-03 - Changes to print Blanket Orders
 * 12-16-03 - Updating a table with the imagename and PO file URL
 * 01-28-04 - Making relative URLs to go to the property file to get hosturl
 * 03-05-04 - Seperating SPECs by ; instead of , as the spec ID can have ,s in them
 * 03-14-04 - Alllowing to print PO line in draft status
 * 08-03-04 - Printing flow downs as add'l quality requirements
 * 11-04-04 - Added shipping notice
 * 01-25-05 - If there is no person defined for ShippingNotice; say send shipping notices to Buyer Above
 * 03-15-05 - Making PO_TERMS_AND_CONDITIONS on the printed PO come from the database
 * 11-03-05 - Taking away Flow Down Id from the flow down desc
 * 08-04-06 - Printing SPEC_ID_DISPLAY for the specs.
 * 08-16-06 - Fixed the need to press enter in po line notes to print them right on the PO
*/


public class PoCreate
{
    private ACJEngine erw = null;
    private ACJOutputProcessor eClient = null;
    private TemplateManager tm = null;
    private Vector Flow_Data = new Vector();
    private String radianItems = "";
    private String msdsmessage = "";
    private String defaultCurrencyId = "";
    private int COAcount = 0;
    private int lineCount = 1;        //this is for keeping track of line number to display in the line requirement section of the pdf
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    //private DBResultSet dbrs = null;
    //private ResultSet rs = null;
    private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

    public PoCreate(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public PoCreate()
    {
    erw = null;
    tm = null;
    eClient = null;
    }

    public String PrintError(String ErrMsg)
    {
     String errormsg =ErrMsg;
     errormsg +="<P>Please Try Again. </P>";
     errormsg +="<P>Thanks.</P>";
     return errormsg;
    }

    protected Hashtable getAddressInfo( String po,String bpo ) throws Exception
    {
      Hashtable AddressInfoh=new Hashtable();
      DBResultSet dbrs = null;
      String dateFormat ="mm/dd/yyyy";

      try
      {
        int numberRec=0;
        String poquery="";

        if ( po.length() > 0 && !"N".equalsIgnoreCase( po ) )
        {
          poquery="Select SHIP_TO_DISPLAY_ADDRESS,SUPPLIER_DISPLAY_ADDRESS,OPS_ENTITY_ID,SHIP_TO_ADDRESS_CODE,OPERATIONAL_TAX_ID,HOME_COMPANY_TAX_ID, INVENTORY_GROUP_STATE_TAX_ID, FEDERAL_TAX_ID, STATE_TAX_ID,EMAIL,CRITICAL,PO_TERMS_AND_CONDITIONS,BILL_TO_COMPANY_ID, BILL_TO_LOCATION_ID, OPS_ENTITY_ALIAS,PO_IMAGE_URL, OPERATING_ENTITY_NAME,INVENTORY_GROUP,SUPPLIER_CONTACT_PHONE,RADIAN_PO,BO,EVER_CONFIRMED,SUPPLIER,SUPPLIER_CONTACT_ID,SUPPLIER_CONTACT_NAME,BUYER,BUYER_NAME,SHIP_TO_COMPANY_ID, ";
          poquery+="SHIP_TO_LOCATION_ID,BRANCH_PLANT,FREIGHT_ON_BOARD,PAYMENT_TERMS,CARRIER,CARRIER_ACCOUNT,CUSTOMER_PO, ";
          poquery+="to_char(DATE_SENT,'"+dateFormat+"') DATE_SENT,to_char(DATE_ACCEPTED,'"+dateFormat+"') DATE_ACCEPTED, ";
          poquery+="to_char(BO_START_DATE,'"+dateFormat+"') BO_START_DATE,to_char(BO_END_DATE,'"+dateFormat+"') BO_END_DATE, ";
          poquery+="SUPPLIER_NAME,SUPPLIER_CONTACT_NAME,CARRIER_COMPANY_ID,CARRIER_ACCOUNT,CARRIER_NAME,CARRIER_HUB,SUPPLIER_COUNTRY_ABBREV,SUPPLIER_STATE_ABBREV, ";
          poquery+="SUPPLIER_ADDRESS_LINE_1,SUPPLIER_ADDRESS_LINE_2,SUPPLIER_ADDRESS_LINE_3,SUPPLIER_CITY,SUPPLIER_ZIP,SUPPLIER_LOCATION_DESC,SHIPTO_COUNTRY_ABBREV, ";
          poquery+="SUPPLIER_PHONE,SUPPLIER_CONTACT_FAX,SHIPTO_STATE_ABBREV,SHIPTO_ADDRESS_LINE_1,SHIPTO_ADDRESS_LINE_2,SHIPTO_ADDRESS_LINE_3,SHIPTO_CITY,SHIPTO_ZIP,SHIPTO_LOCATION_DESC ";
          poquery+="from po_header_view where radian_po = " + po + " ";
        }
        else
        {
          poquery="Select '' SHIP_TO_DISPLAY_ADDRESS,'' SUPPLIER_DISPLAY_ADDRESS,'' OPS_ENTITY_ID, '' SHIP_TO_ADDRESS_CODE,'' OPERATIONAL_TAX_ID,'' HOME_COMPANY_TAX_ID,'' INVENTORY_GROUP_STATE_TAX_ID,'' FEDERAL_TAX_ID,'' STATE_TAX_ID,EMAIL,CRITICAL,PO_TERMS_AND_CONDITIONS,BILL_TO_COMPANY_ID, BILL_TO_LOCATION_ID, OPS_ENTITY_ALIAS,PO_IMAGE_URL, OPERATING_ENTITY_NAME,'' INVENTORY_GROUP,SUPPLIER_CONTACT_PHONE,'' RADIAN_PO,BPO BO,EVER_CONFIRMED,SUPPLIER,SUPPLIER_CONTACT_ID,SUPPLIER_CONTACT_NAME,BUYER,BUYER_NAME,'' SHIP_TO_COMPANY_ID, ";
          poquery+="'' SHIP_TO_LOCATION_ID,'' BRANCH_PLANT,FREIGHT_ON_BOARD,PAYMENT_TERMS,'' CARRIER,'' CARRIER_ACCOUNT,'' CUSTOMER_PO, ";
          poquery+="to_char(START_DATE,'"+dateFormat+"') DATE_SENT,to_char(END_DATE,'"+dateFormat+"') DATE_ACCEPTED, ";
          poquery+="to_char(START_DATE,'"+dateFormat+"') BO_START_DATE,to_char(END_DATE,'"+dateFormat+"') BO_END_DATE, ";
          poquery+="SUPPLIER_NAME,SUPPLIER_CONTACT_NAME, '' CARRIER_COMPANY_ID,'' CARRIER_ACCOUNT,'' CARRIER_NAME,'' CARRIER_HUB,SUPPLIER_COUNTRY_ABBREV,SUPPLIER_STATE_ABBREV, ";
          poquery+="SUPPLIER_ADDRESS_LINE_1,SUPPLIER_ADDRESS_LINE_2,SUPPLIER_ADDRESS_LINE_3,SUPPLIER_CITY,SUPPLIER_ZIP,SUPPLIER_LOCATION_DESC,SHIPTO_COUNTRY_ABBREV, ";
          poquery+="SUPPLIER_PHONE,SUPPLIER_CONTACT_FAX,'' SHIPTO_STATE_ABBREV,'' SHIPTO_ADDRESS_LINE_1,'' SHIPTO_ADDRESS_LINE_2,'' SHIPTO_ADDRESS_LINE_3,'' SHIPTO_CITY,'' SHIPTO_ZIP,'' SHIPTO_LOCATION_DESC ";
          poquery+="from bpo_header_view where bpo = " + bpo + " ";
        }

        dbrs=db.doQuery( poquery );
        ResultSet searchRs=dbrs.getResultSet();

        while ( searchRs.next() )
        {
          //INVENTORY_GROUP
          AddressInfoh.put( "INVENTORY_GROUP",searchRs.getString( "INVENTORY_GROUP" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP" ).trim() );
          //SUPPLIER_CONTACT_PHONE
          AddressInfoh.put( "SUPPLIER_CONTACT_PHONE",searchRs.getString( "SUPPLIER_CONTACT_PHONE" ) == null ? "" : searchRs.getString( "SUPPLIER_CONTACT_PHONE" ).trim() );
          //SUPPLIER_CONTACT_FAX
          AddressInfoh.put( "SUPPLIER_CONTACT_FAX",searchRs.getString( "SUPPLIER_CONTACT_FAX" ) == null ? "" : searchRs.getString( "SUPPLIER_CONTACT_FAX" ).trim() );
          //RADIAN_PO
          AddressInfoh.put( "RADIAN_PO",searchRs.getString( "RADIAN_PO" ) == null ? "" : searchRs.getString( "RADIAN_PO" ) );
          //BO
          AddressInfoh.put( "ORIGINAL_ORDER_NUM",searchRs.getString( "BO" ) == null ? "" : searchRs.getString( "BO" ) );
          //SUPPLIER
          AddressInfoh.put( "SUPPLIER",searchRs.getString( "SUPPLIER" ) == null ? "" : searchRs.getString( "SUPPLIER" ) );
          //SUPPLIER_CONTACT_ID
          AddressInfoh.put( "SUPPLIER_CONTACT_ID",searchRs.getString( "SUPPLIER_CONTACT_ID" ) == null ? "" : searchRs.getString( "SUPPLIER_CONTACT_ID" ) );
          //BUYER
          AddressInfoh.put( "BUYER",searchRs.getString( "BUYER" ) == null ? "" : searchRs.getString( "BUYER" ) );
          //SHIP_TO_COMPANY_ID
          AddressInfoh.put( "SHIP_TO_COMPANY_ID",searchRs.getString( "SHIP_TO_COMPANY_ID" ) == null ? "" : searchRs.getString( "SHIP_TO_COMPANY_ID" ) );
          //SHIP_TO_LOCATION_ID
          AddressInfoh.put( "SHIP_TO_LOCATION_ID",searchRs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "" : searchRs.getString( "SHIP_TO_LOCATION_ID" ) );
          //BRANCH_PLANT
          AddressInfoh.put( "branch_plant",searchRs.getString( "BRANCH_PLANT" ) == null ? "" : searchRs.getString( "BRANCH_PLANT" ) );
          //FREIGHT_ON_BOARD
          AddressInfoh.put( "FREIGHT_ON_BOARD",searchRs.getString( "FREIGHT_ON_BOARD" ) == null ? "" : searchRs.getString( "FREIGHT_ON_BOARD" ) );
          //PAYMENT_TERMS
          AddressInfoh.put( "PAYMENT_TERMS",searchRs.getString( "PAYMENT_TERMS" ) == null ? "" : searchRs.getString( "PAYMENT_TERMS" ) );
          //CARRIER
          AddressInfoh.put( "CARRIER",searchRs.getString( "CARRIER" ) == null ? "" : searchRs.getString( "CARRIER" ) );
          //DATE_SENT
          AddressInfoh.put( "DATE_SENT",searchRs.getString( "DATE_SENT" ) == null ? "" : searchRs.getString( "DATE_SENT" ) );
          //DATE_ACCEPTED
          AddressInfoh.put( "DATE_ACCEPTED",searchRs.getString( "DATE_ACCEPTED" ) == null ? "" : searchRs.getString( "DATE_ACCEPTED" ) );
          //TERMS_AND_CONDITIONS
          AddressInfoh.put( "PO_TERMS_AND_CONDITIONS",searchRs.getString( "PO_TERMS_AND_CONDITIONS" ) == null ? "" : searchRs.getString( "PO_TERMS_AND_CONDITIONS" ) );
          //BO_START_DATE
          AddressInfoh.put( "BO_START_DATE",searchRs.getString( "BO_START_DATE" ) == null ? "" : searchRs.getString( "BO_START_DATE" ) );
          //BO_END_DATE
          AddressInfoh.put( "BO_END_DATE",searchRs.getString( "BO_END_DATE" ) == null ? "" : searchRs.getString( "BO_END_DATE" ) );
          //CUSTOMER_PO
          AddressInfoh.put( "CUSTOMER_PO",searchRs.getString( "CUSTOMER_PO" ) == null ? "" : searchRs.getString( "CUSTOMER_PO" ) );
          //SUPPLIER_NAME
          AddressInfoh.put( "vendor_name",searchRs.getString( "SUPPLIER_NAME" ) == null ? "" : searchRs.getString( "SUPPLIER_NAME" ) );
          //SUPPLIER_CONTACT_NAME
          AddressInfoh.put( "SUPPLIER_CONTACT_NAME",searchRs.getString( "SUPPLIER_CONTACT_NAME" ) == null ? "" : searchRs.getString( "SUPPLIER_CONTACT_NAME" ) );
          //BUYER_NAME
          AddressInfoh.put( "buyer",searchRs.getString( "BUYER_NAME" ) == null ? "" : searchRs.getString( "BUYER_NAME" ) );
          //EVER_CONFIRMED
          AddressInfoh.put( "EVER_CONFIRMED",searchRs.getString( "EVER_CONFIRMED" ) == null ? "" : searchRs.getString( "EVER_CONFIRMED" ) );
          //CARRIER_COMPANY_ID
          AddressInfoh.put( "CARRIER_COMPANY_ID",searchRs.getString( "CARRIER_COMPANY_ID" ) == null ? "" : searchRs.getString( "CARRIER_COMPANY_ID" ) );
          //CARRIER_ACCOUNT
          AddressInfoh.put( "CARRIER_ACCOUNT",searchRs.getString( "CARRIER_ACCOUNT" ) == null ? "" : searchRs.getString( "CARRIER_ACCOUNT" ) );
          //CARRIER_NAME
          AddressInfoh.put( "CARRIER_NAME",searchRs.getString( "CARRIER_NAME" ) == null ? "" : searchRs.getString( "CARRIER_NAME" ) );
          //CARRIER_HUB
          AddressInfoh.put( "CARRIER_HUB",searchRs.getString( "CARRIER_HUB" ) == null ? "" : searchRs.getString( "CARRIER_HUB" ) );
          //SUPPLIER_COUNTRY_ABBREV
          AddressInfoh.put( "SUPPLIER_COUNTRY_ABBREV",searchRs.getString( "SUPPLIER_COUNTRY_ABBREV" ) == null ? "" : searchRs.getString( "SUPPLIER_COUNTRY_ABBREV" ) );
          //SUPPLIER_STATE_ABBREV
          AddressInfoh.put( "vendor_state",searchRs.getString( "SUPPLIER_STATE_ABBREV" ) == null ? "" : searchRs.getString( "SUPPLIER_STATE_ABBREV" ) );
          //SUPPLIER_ADDRESS_LINE_1
          AddressInfoh.put( "vendor_address1",searchRs.getString( "SUPPLIER_ADDRESS_LINE_1" ) == null ? "" : searchRs.getString( "SUPPLIER_ADDRESS_LINE_1" ) );
          //SUPPLIER_ADDRESS_LINE_2
          AddressInfoh.put( "vendor_address2",searchRs.getString( "SUPPLIER_ADDRESS_LINE_2" ) == null ? "" : searchRs.getString( "SUPPLIER_ADDRESS_LINE_2" ) );
          //SUPPLIER_ADDRESS_LINE_3
          AddressInfoh.put( "vendor_address3",searchRs.getString( "SUPPLIER_ADDRESS_LINE_3" ) == null ? "" : searchRs.getString( "SUPPLIER_ADDRESS_LINE_3" ) );
          //SUPPLIER_CITY
          AddressInfoh.put( "vendor_city",searchRs.getString( "SUPPLIER_CITY" ) == null ? "" : searchRs.getString( "SUPPLIER_CITY" ) );
          //SUPPLIER_ZIP
          AddressInfoh.put( "vendor_zipcode",searchRs.getString( "SUPPLIER_ZIP" ) == null ? "" : searchRs.getString( "SUPPLIER_ZIP" ) );
          //SUPPLIER_LOCATION_DESC
          AddressInfoh.put( "SUPPLIER_LOCATION_DESC",searchRs.getString( "SUPPLIER_LOCATION_DESC" ) == null ? "" : searchRs.getString( "SUPPLIER_LOCATION_DESC" ) );
          //SHIPTO_COUNTRY_ABBREV
          AddressInfoh.put( "SHIPTO_COUNTRY_ABBREV",searchRs.getString( "SHIPTO_COUNTRY_ABBREV" ) == null ? "" : searchRs.getString( "SHIPTO_COUNTRY_ABBREV" ) );
          //SHIPTO_STATE_ABBREV
          AddressInfoh.put( "shipto_state",searchRs.getString( "SHIPTO_STATE_ABBREV" ) == null ? "" : searchRs.getString( "SHIPTO_STATE_ABBREV" ) );
          //SHIPTO_ADDRESS_LINE_1
          AddressInfoh.put( "shipto_address1",searchRs.getString( "SHIPTO_ADDRESS_LINE_1" ) == null ? "" : searchRs.getString( "SHIPTO_ADDRESS_LINE_1" ) );
          //SHIPTO_ADDRESS_LINE_2
          AddressInfoh.put( "shipto_address2",searchRs.getString( "SHIPTO_ADDRESS_LINE_2" ) == null ? "" : searchRs.getString( "SHIPTO_ADDRESS_LINE_2" ) );
          //SHIPTO_ADDRESS_LINE_3
          AddressInfoh.put( "shipto_address3",searchRs.getString( "SHIPTO_ADDRESS_LINE_3" ) == null ? "" : searchRs.getString( "SHIPTO_ADDRESS_LINE_3" ) );
          //SHIPTO_CITY
          AddressInfoh.put( "shipto_city",searchRs.getString( "SHIPTO_CITY" ) == null ? "" : searchRs.getString( "SHIPTO_CITY" ) );
          //SHIPTO_ZIP
          AddressInfoh.put( "shipto_zip",searchRs.getString( "SHIPTO_ZIP" ) == null ? "" : searchRs.getString( "SHIPTO_ZIP" ) );
          //SHIPTO_LOCATION_DESC
          AddressInfoh.put( "shipto_company",searchRs.getString( "SHIPTO_LOCATION_DESC" ) == null ? "" : searchRs.getString( "SHIPTO_LOCATION_DESC" ) );
          //SUPPLIER_PHONE
          AddressInfoh.put( "SUPPLIER_PHONE",searchRs.getString( "SUPPLIER_PHONE" ) == null ? "" : searchRs.getString( "SUPPLIER_PHONE" ) );
		  //BILL_TO_COMPANY_ID
		  AddressInfoh.put( "BILL_TO_COMPANY_ID",searchRs.getString( "BILL_TO_COMPANY_ID" ) == null ? "" : searchRs.getString( "BILL_TO_COMPANY_ID" ) );
		  //BILL_TO_LOCATION_ID
		  AddressInfoh.put( "BILL_TO_LOCATION_ID",searchRs.getString( "BILL_TO_LOCATION_ID" ) == null ? "" : searchRs.getString( "BILL_TO_LOCATION_ID" ) );
		  //OPS_ENTITY_ALIAS
		  AddressInfoh.put( "OPS_ENTITY_ALIAS",searchRs.getString( "OPS_ENTITY_ALIAS" ) == null ? "" : searchRs.getString( "OPS_ENTITY_ALIAS" ) );
		  //PO_IMAGE_URL
		  AddressInfoh.put( "PO_IMAGE_URL",searchRs.getString( "PO_IMAGE_URL" ) == null ? "" : searchRs.getString( "PO_IMAGE_URL" ) );
		  //OPERATING_ENTITY_NAME
		  AddressInfoh.put( "HOME_COMPANY_NAME",searchRs.getString( "OPERATING_ENTITY_NAME" ) == null ? "" : searchRs.getString( "OPERATING_ENTITY_NAME" ) );
 	      //CRITICAL
		  AddressInfoh.put( "CRITICAL",searchRs.getString( "CRITICAL" ) == null ? "" : searchRs.getString( "CRITICAL" ) );
       // SUPPLIER CONTACT EMAIL
	      AddressInfoh.put( "EMAIL",searchRs.getString( "EMAIL" ) == null ? "" : searchRs.getString( "EMAIL" ) );

		// HOME_COMPANY_TAX_ID
		AddressInfoh.put( "HOME_COMPANY_TAX_ID",searchRs.getString( "HOME_COMPANY_TAX_ID" ) == null ? "" : searchRs.getString( "HOME_COMPANY_TAX_ID" ) );
		// INVENTORY_GROUP_STATE_TAX_ID
		AddressInfoh.put( "INVENTORY_GROUP_STATE_TAX_ID",searchRs.getString( "INVENTORY_GROUP_STATE_TAX_ID" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP_STATE_TAX_ID" ) );
		// FEDERAL_TAX_ID
		AddressInfoh.put( "FEDERAL_TAX_ID",searchRs.getString( "FEDERAL_TAX_ID" ) == null ? "" : searchRs.getString( "FEDERAL_TAX_ID" ) );
		// STATE_TAX_ID
		AddressInfoh.put( "STATE_TAX_ID",searchRs.getString( "STATE_TAX_ID" ) == null ? "" : searchRs.getString( "STATE_TAX_ID" ) );
		// OPERATIONAL_TAX_ID
		AddressInfoh.put( "OPERATIONAL_TAX_ID",searchRs.getString( "OPERATIONAL_TAX_ID" ) == null ? "" : searchRs.getString( "OPERATIONAL_TAX_ID" ) );
		// SHIP_TO_ADDRESS_CODE
		AddressInfoh.put( "SHIP_TO_ADDRESS_CODE",searchRs.getString( "SHIP_TO_ADDRESS_CODE" ) == null ? "" : searchRs.getString( "SHIP_TO_ADDRESS_CODE" ) );          
    //OPS_ENTITY_ID
    AddressInfoh.put( "OPS_ENTITY_ID",searchRs.getString( "OPS_ENTITY_ID" ) == null ? "" : searchRs.getString( "OPS_ENTITY_ID" ) );
    //OPS_ENTITY_ID
    AddressInfoh.put( "SHIP_TO_DISPLAY_ADDRESS",searchRs.getString( "SHIP_TO_DISPLAY_ADDRESS" ) == null ? "" : searchRs.getString( "SHIP_TO_DISPLAY_ADDRESS" ) );
    //OPS_ENTITY_ID
    AddressInfoh.put( "SUPPLIER_DISPLAY_ADDRESS",searchRs.getString( "SUPPLIER_DISPLAY_ADDRESS" ) == null ? "" : searchRs.getString( "SUPPLIER_DISPLAY_ADDRESS" ) );

          numberRec+=1;
        }
        //searchRs.close();
      }
      catch ( SQLException sqle )
      {
        //System.out.println( "Error InSQL AddressInfo:" );
        //sqle.printStackTrace();
        throw sqle;
      }
      finally
      {
        if ( dbrs != null ){ dbrs.close(); }
      }
      return AddressInfoh;
    }

    protected Hashtable getPoInfo( String po1,String bpo,Connection connection2,Vector addchargeslist,String HubNumber,
                                   String ShipTo,String shiptoloccompanyId,String bpoenddate,String invengrp,String headerNote,ResourceLibrary res,Locale printLocale,String opsEntityId,String totalCost,String totalCostCurrencyId ) throws Exception
    {
      Vector poInfo=new Vector();
      Hashtable PoInfoh=new Hashtable();
      Hashtable FinalPoInfoh=new Hashtable();
      Vector specFlowv=new Vector();
      Hashtable specflowd=new Hashtable();
      DBResultSet dbrs = null;

      BigDecimal poTotal = new BigDecimal("0");
      BigDecimal lineTotal=new BigDecimal("0");
			DecimalFormat unitPriceFormat=new DecimalFormat( "####.00####" );
			DecimalFormat lineTotalFormat=new DecimalFormat( "####.0000" );
      DecimalFormat poTotalFormat=new DecimalFormat( "####.00" );
      String dateFormat ="mm/dd/yyyy";
      boolean creatingblanketpo1=false;

			if ("2140".equalsIgnoreCase(HubNumber) || "2142".equalsIgnoreCase(HubNumber))
			{
				 Locale locale = new Locale("pt", "BR");
				 //Locale.setDefault(locale);
				 lineTotalFormat =	(DecimalFormat) NumberFormat.getNumberInstance(locale);
				 poTotalFormat =	(DecimalFormat) NumberFormat.getNumberInstance(locale);
				 unitPriceFormat =	(DecimalFormat) NumberFormat.getNumberInstance(locale);
				 lineTotalFormat.setMaximumFractionDigits(2);
				 lineTotalFormat.setMinimumFractionDigits(2);
				 poTotalFormat.setMaximumFractionDigits(2);
				 poTotalFormat.setMinimumFractionDigits(2);
				 unitPriceFormat.setMaximumFractionDigits(2);
				 unitPriceFormat.setMinimumFractionDigits(2);
				 dateFormat ="dd/mm/yyyy";
			}
			else
			{
				 unitPriceFormat.setMaximumFractionDigits(6);
				 unitPriceFormat.setMinimumFractionDigits(2);
	    }

      try
      {
        int numberRec=0;
        int count=0;

        String polinedetail="";
        boolean printCanceledLine=false;

	if ( po1.length() > 0 && !"N".equalsIgnoreCase( po1 ) && "0".equalsIgnoreCase(headerNote) )
        {
	  polinedetail+="select fx_supplier_location_desc(ship_from_location_id) SUPPLIER_SHIP_FROM_LOCATION, SHELF_LIFE_BASIS, SHELF_LIFE_DAYS,CURRENCY_DISPLAY,CURRENCY_ID,VENDOR_SHIP_DATE,RADIAN_PO,ITEM_TYPE,PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(NEED_DATE,'"+dateFormat+"') NEED_DATE,PROMISED_DATE, ";
	  polinedetail+="ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";
	  polinedetail+="PR_SHIP_TO,PR_COMPANY_ID,REMAINING_SHELF_LIFE_PERCENT,MSDS_REQUESTED_DATE,GENERIC_COC,GENERIC_COA,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,PO_LINE_NOTE,ITEM_DESC,PACKAGING, ";
	  polinedetail+="PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED, DATE_CONFIRMED, LAST_CONFIRMED from po_line_detail_view where RADIAN_PO = " + po1 + " order by PO_LINE asc"; //po_line_confirm_view
	}
        else if ( po1.length() > 0 && !"N".equalsIgnoreCase( po1 ) )
        {
          polinedetail+="select fx_supplier_location_desc(ship_from_location_id) SUPPLIER_SHIP_FROM_LOCATION, SHELF_LIFE_BASIS, SHELF_LIFE_DAYS,CURRENCY_DISPLAY,CURRENCY_ID,VENDOR_SHIP_DATE,RADIAN_PO,ITEM_TYPE,PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(NEED_DATE,'"+dateFormat+"') NEED_DATE,PROMISED_DATE, ";
          polinedetail+="ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";
          polinedetail+="PR_SHIP_TO,PR_COMPANY_ID,REMAINING_SHELF_LIFE_PERCENT,MSDS_REQUESTED_DATE,GENERIC_COC,GENERIC_COA,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,PO_LINE_NOTE,ITEM_DESC,PACKAGING, ";
          polinedetail+="PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED, DATE_CONFIRMED from po_line_confirm_print_view where RADIAN_PO = " + po1 + " order by PO_LINE asc"; //po_line_confirm_view
        }
        else
        {
          creatingblanketpo1=true;
          polinedetail+="select'' SUPPLIER_SHIP_FROM_LOCATION, '' SHELF_LIFE_BASIS, '' SHELF_LIFE_DAYS,CURRENCY_DISPLAY,CURRENCY_ID,sysdate VENDOR_SHIP_DATE,BPO RADIAN_PO,ITEM_TYPE,BPO_LINE PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,'' NEED_DATE,'" + bpoenddate + "' PROMISED_DATE, ";
          polinedetail+="ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,'' DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";
          polinedetail+="'' PR_SHIP_TO,'' PR_COMPANY_ID,REMAINING_SHELF_LIFE_PERCENT,MSDS_REQUESTED_DATE,GENERIC_COC,GENERIC_COA,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,BPO_LINE_NOTE PO_LINE_NOTE,ITEM_DESC,PACKAGING, ";
          polinedetail+="PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED, DATE_CONFIRMED from bpo_line_confirm_view where BPO = " + bpo + " order by BPO_LINE asc";
        }

        dbrs=db.doQuery( polinedetail );
        ResultSet lineResultRs=dbrs.getResultSet();
        String linesData = "";
		boolean foundCurrencyId = false;
		PurchaseOrderProcess process = new PurchaseOrderProcess(db.getClient(), printLocale.toString());
        
        while ( lineResultRs.next() )
        {
          PoInfoh=new Hashtable();
          count++;

          String quantity=lineResultRs.getString( "QUANTITY" ) == null ? "" : lineResultRs.getString( "QUANTITY" ).trim();
          String unit_price=lineResultRs.getString( "UNIT_PRICE" ) == null ? "" : lineResultRs.getString( "UNIT_PRICE" ).trim();
          String item_type=lineResultRs.getString( "ITEM_TYPE" ) == null ? "" : lineResultRs.getString( "ITEM_TYPE" ).trim();
          String qtyreceived=lineResultRs.getString( "QUANTITY_RECEIVED" ) == null ? "" : lineResultRs.getString( "QUANTITY_RECEIVED" ).trim();
          String item_id=lineResultRs.getString( "ITEM_ID" ) == null ? "" : lineResultRs.getString( "ITEM_ID" ).trim();
          String amendment=lineResultRs.getString( "AMENDMENT" ) == null ? "" : lineResultRs.getString( "AMENDMENT" ).trim();
          String prshipto=lineResultRs.getString( "PR_SHIP_TO" ) == null ? "" : lineResultRs.getString( "PR_SHIP_TO" ).trim();
          String packaging=lineResultRs.getString( "PACKAGING" ) == null ? "" : lineResultRs.getString( "PACKAGING" ).trim();
          String shelflifepercent=lineResultRs.getString( "REMAINING_SHELF_LIFE_PERCENT" ) == null ? "" : lineResultRs.getString( "REMAINING_SHELF_LIFE_PERCENT" ).trim();
		  String supplierPartNo=lineResultRs.getString( "SUPPLIER_PART_NO" ) == null ? "" : lineResultRs.getString( "SUPPLIER_PART_NO" ).trim();
          String shelfLifeBasis = lineResultRs.getString("SHELF_LIFE_BASIS")==null?"":lineResultRs.getString("SHELF_LIFE_BASIS").trim();
          String shelfLifeDays = lineResultRs.getString("SHELF_LIFE_DAYS")==null?"":lineResultRs.getString("SHELF_LIFE_DAYS").trim();

          BigDecimal qtyF=new BigDecimal("0");
          if ( quantity.trim().length() > 0 )
          {
            qtyF=new BigDecimal(quantity);
          }

          if ( ( qtyF.intValue() == 0 ) && !"0".equalsIgnoreCase( amendment ) )
          {
            printCanceledLine=true;
          }

          if ( ( qtyF.floatValue() > 0 ) || creatingblanketpo1 || printCanceledLine )
          {
            if ( !addchargeslist.contains( item_type ) )
            {
              specflowd=new Hashtable();
              specflowd.put( "LINE","" + count + "" );
              specflowd.put( "ITEMID","" + item_id + "" );
              specflowd.put( "AMMENDMEANT","" + amendment + "" );
              specflowd.put( "QUANTITY","" + quantity + "" );
              specflowd.put( "PRSHIPTO","" + prshipto + "" );
              specflowd.put( "SHELFLIFEPER","" + shelflifepercent + "" );
              specflowd.put( "SHELF_LIFE_BASIS","" + shelfLifeBasis + "" );
              specflowd.put( "SHELF_LIFE_DAYS","" + shelfLifeDays + "" );
              specflowd.put( "SUPPLIER_PART_NO","" + supplierPartNo + "" );

              specFlowv.addElement( specflowd );
            }
            else
            {
              packaging="";
            }
            
            //RADIAN_PO
            PoInfoh.put( "RADIAN_PO",lineResultRs.getString( "RADIAN_PO" ) == null ? "" : lineResultRs.getString( "RADIAN_PO" ) );
            //PO_LINE
            PoInfoh.put( "LINE_ITEM",lineResultRs.getString( "PO_LINE" ) == null ? "" : lineResultRs.getString( "PO_LINE" ) );
            //AMENDMENT
            PoInfoh.put( "AMENDMENT",lineResultRs.getString( "AMENDMENT" ) == null ? "" : lineResultRs.getString( "AMENDMENT" ) );
            //ITEM_ID
            PoInfoh.put( "ITEM_ID",lineResultRs.getString( "ITEM_ID" ) == null ? "" : lineResultRs.getString( "ITEM_ID" ) );
            //QUANTITY
            PoInfoh.put( "QTY",qtyF );
            //UNIT_PRICE
			BigDecimal unitPrice= new BigDecimal("0");
			if ( unit_price.trim().length() > 0 )
			{
				unitPrice= new BigDecimal(unit_price);
				unitPrice.setScale(6,3);
			}
			
            if (printLocale.getCountry().equalsIgnoreCase("DE"))
            {
              String dd = NumberFormat.getNumberInstance(printLocale).format(unitPrice);
              PoInfoh.put( "UNIT_PRICE",dd );
            }
            else
            {
            PoInfoh.put( "UNIT_PRICE","" + unitPriceFormat.format( unitPrice ) + "" );
            }
            //NEED_DATE
            PoInfoh.put( "NEED_DATE",lineResultRs.getString( "NEED_DATE" ) == null ? "" : lineResultRs.getString( "NEED_DATE" ) );
            //PROMISED_DATE
            Date promisedDate = lineResultRs.getDate( "PROMISED_DATE");
            String formattedPromisedDate = "";
            if (promisedDate !=null)
            {

            DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
            /*if (!printLocale.getCountry().equalsIgnoreCase("DE"))
            {
              formatter = new SimpleDateFormat("MM/dd/yyyy");
            }*/
            formattedPromisedDate = formatter.format(promisedDate);
            }
            PoInfoh.put( "SCHED_PICK",formattedPromisedDate);            
            //ALLOWED_PRICE_VARIANCE
            PoInfoh.put( "ALLOWED_PRICE_VARIANCE",lineResultRs.getString( "ALLOWED_PRICE_VARIANCE" ) == null ? "" : lineResultRs.getString( "ALLOWED_PRICE_VARIANCE" ) );
            //MFG_PART_NO
            PoInfoh.put( "MFG_PART_NO",lineResultRs.getString( "MFG_PART_NO" ) == null ? "" : lineResultRs.getString( "MFG_PART_NO" ) );
            //SUPPLIER_PART_NO
            PoInfoh.put( "SUPPLIER_PART_NO",lineResultRs.getString( "SUPPLIER_PART_NO" ) == null ? "" : lineResultRs.getString( "SUPPLIER_PART_NO" ) );
            //DPAS_RATING
            PoInfoh.put( "DPAS_RATING",lineResultRs.getString( "DPAS_RATING" ) == null ? "" : lineResultRs.getString( "DPAS_RATING" ) );
            //QUALITY_FLOW_DOWNS
            PoInfoh.put( "QUALITY_FLOW_DOWNS",lineResultRs.getString( "QUALITY_FLOW_DOWNS" ) == null ? "" : lineResultRs.getString( "QUALITY_FLOW_DOWNS" ) );
            //PACKAGING_FLOW_DOWNS
            PoInfoh.put( "PACKAGING_FLOW_DOWNS",lineResultRs.getString( "PACKAGING_FLOW_DOWNS" ) == null ? "" : lineResultRs.getString( "PACKAGING_FLOW_DOWNS" ) );
            //PO_LINE_NOTE
            PoInfoh.put( "PO_LINE_NOTE",lineResultRs.getString( "PO_LINE_NOTE" ) == null ? "" : lineResultRs.getString( "PO_LINE_NOTE" ) );
            //ITEM_DESC
            PoInfoh.put( "LINE_DESC",lineResultRs.getString( "ITEM_DESC" ) == null ? "" : lineResultRs.getString( "ITEM_DESC" ) );
            //ITEM_TYPE
            PoInfoh.put( "ITEM_TYPE",item_type );
            //PACKAGING
            PoInfoh.put( "UOM",packaging );
            //PO_LINE_STATUS
            PoInfoh.put( "PO_LINE_STATUS",lineResultRs.getString( "PO_LINE_STATUS" ) == null ? "" : lineResultRs.getString( "PO_LINE_STATUS" ) );
            //QUANTITY_RECEIVED
            PoInfoh.put( "QUANTITY_RECEIVED",qtyreceived );
            //QUANTITY_VOUCHERED
            PoInfoh.put( "QUANTITY_VOUCHERED",lineResultRs.getString( "QUANTITY_VOUCHERED" ) == null ? "" : lineResultRs.getString( "QUANTITY_VOUCHERED" ) );
            //SUPPLIER_QTY
            PoInfoh.put( "SUPPLIER_QTY",lineResultRs.getString( "SUPPLIER_QTY" ) == null ? "" : lineResultRs.getString( "SUPPLIER_QTY" ) );
            //SUPPLIER_PKG
            PoInfoh.put( "SUPPLIER_PKG",lineResultRs.getString( "SUPPLIER_PKG" ) == null ? "" : lineResultRs.getString( "SUPPLIER_PKG" ) );
            //SUPPLIER_UNIT_PRICE
            PoInfoh.put( "SUPPLIER_UNIT_PRICE",lineResultRs.getString( "SUPPLIER_UNIT_PRICE" ) == null ? "" : lineResultRs.getString( "SUPPLIER_UNIT_PRICE" ) );
            //EVER_CONFIRMED
            PoInfoh.put( "EVER_CONFIRMED",lineResultRs.getString( "EVER_CONFIRMED" ) == null ? "" : lineResultRs.getString( "EVER_CONFIRMED" ) );
            //MSDS_REQUESTED_DATE
            PoInfoh.put( "MSDS_REQUESTED_DATE",lineResultRs.getString( "MSDS_REQUESTED_DATE" ) == null ? "" : lineResultRs.getString( "MSDS_REQUESTED_DATE" ) );
            //PR_SHIP_TO
            PoInfoh.put( "PR_SHIP_TO",lineResultRs.getString( "PR_SHIP_TO" ) == null ? "" : lineResultRs.getString( "PR_SHIP_TO" ) );
            //PR_COMPANY_ID
            PoInfoh.put( "PR_COMPANY_ID",lineResultRs.getString( "PR_COMPANY_ID" ) == null ? "" : lineResultRs.getString( "PR_COMPANY_ID" ) );
            //GENERIC_COC
            PoInfoh.put( "GENERIC_COC",lineResultRs.getString( "GENERIC_COC" ) == null ? "" : lineResultRs.getString( "GENERIC_COC" ) );
            //GENERIC_COA
            PoInfoh.put( "GENERIC_COA",lineResultRs.getString( "GENERIC_COA" ) == null ? "" : lineResultRs.getString( "GENERIC_COA" ) );
            //SUPPLIER_SHIP_FROM_LOCATION
            PoInfoh.put( "SUPPLIER_SHIP_FROM_LOCATION",lineResultRs.getString( "SUPPLIER_SHIP_FROM_LOCATION" ) == null ? "" : lineResultRs.getString( "SUPPLIER_SHIP_FROM_LOCATION" ) );                      
            //REMAINING_SHELF_LIFE_PERCENT
            PoInfoh.put( "REMAINING_SHELF_LIFE_PERCENT",shelflifepercent );
            //VENDOR_SHIP_DATE
            Date vendorShipDate = lineResultRs.getDate( "VENDOR_SHIP_DATE");
            String formattedvendorShipDate = "";
            if (vendorShipDate !=null)
            {
	            DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
	            /*if (!printLocale.getCountry().equalsIgnoreCase("DE"))
	            {
	              formatter = new SimpleDateFormat("MM/dd/yyyy");
	            }*/
	            formattedvendorShipDate = formatter.format(vendorShipDate);
            }
            PoInfoh.put( "VENDOR_SHIP_DATE",formattedvendorShipDate);
			//CURRENCY_ID
			String lineCurrencyId = lineResultRs.getString( "CURRENCY_ID" ) == null ? "" : lineResultRs.getString( "CURRENCY_ID" );
			//value to be displayed on print
			PoInfoh.put( "CURRENCY_ID", lineResultRs.getString( "CURRENCY_DISPLAY" ) == null ? "" : lineResultRs.getString( "CURRENCY_DISPLAY" ) );
			
			//DATE_CONFIRMED
            Date dateConfirmed = lineResultRs.getDate( "DATE_CONFIRMED");
            if (dateConfirmed != null)
            	PoInfoh.put( "DATE_CONFIRMED",new SimpleDateFormat("MMM-dd-yyyy").format(dateConfirmed));
            
            lineTotal = ( qtyF.multiply(unitPrice));
            
            if (StringHandler.isBlankString(totalCost) || StringHandler.isBlankString(totalCostCurrencyId)) {
            	//if default currency has not been found, proceed with this step
				if (!foundCurrencyId)
					//if the var is empty, assign current currency to it
					if (StringHandler.isBlankString(defaultCurrencyId))
						defaultCurrencyId = lineCurrencyId;
					//if the var is not empty and not equals to current currency, use ops entity default currency and flip the default currency check.
					else
						if (!defaultCurrencyId.equalsIgnoreCase(lineCurrencyId)) {
							defaultCurrencyId = process.getOpsEntityHomeCurrency(opsEntityId, connection2);
							foundCurrencyId = true;
						}
				
	            //if data is ready for calculating PO's total cost, include them in var to be used by method
	            if (lineTotal.compareTo(new BigDecimal(0)) == 1 && !StringHandler.isBlankString(lineCurrencyId)) {
					ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", printLocale);
					SimpleDateFormat sdf = new SimpleDateFormat(library.getString("java.dateformat"));
					//prioritize last_confirmed date over date_confirmed date when getting conversion rate
	            	String dateToUseStr = "sysdate";
	            	try {
	            		dateToUseStr = sdf.format(lineResultRs.getDate( "LAST_CONFIRMED"));
	            	} catch (SQLException e) {
	            		if (dateConfirmed != null)
	            			dateToUseStr = sdf.format(dateConfirmed);
	            	}
					linesData += lineTotal.toPlainString() + "," + lineCurrencyId + "," + dateToUseStr + ";";
	            }
            }
            
            if (printLocale.getCountry().equalsIgnoreCase("DE"))
            {
              String dd = NumberFormat.getNumberInstance(printLocale).format(lineTotal);
              PoInfoh.put( "TOTAL_PRICE_PAID",dd );
            }
            else
            {
            PoInfoh.put( "TOTAL_PRICE_PAID","" + lineTotalFormat.format( lineTotal ) + "" );
            }
            
						
		  numberRec+=1;
		  poInfo.addElement( PoInfoh );

		}
	  }
	  FinalPoInfoh.put( "LINES",poInfo );
	  
	  if (StringHandler.isBlankString(totalCost) || StringHandler.isBlankString(totalCostCurrencyId)) {
		  defaultCurrencyId = StringHandler.isBlankString(defaultCurrencyId) ? "USD" : defaultCurrencyId;
		  if (StringHandler.isBlankString(linesData))
			  poTotal = new BigDecimal(0);
		  else
			  poTotal = process.getPoLineTotal(defaultCurrencyId, linesData, connection2);
	  } else {
		  defaultCurrencyId = totalCostCurrencyId;
		  poTotal = new BigDecimal(totalCost);
	  }
    if (printLocale.getCountry().equalsIgnoreCase("DE"))
    {
      String dd = NumberFormat.getNumberInstance(printLocale).format(poTotal);      
      FinalPoInfoh.put( "TOTAL_PRICE",dd );
    }
    else
    {
    FinalPoInfoh.put( "TOTAL_PRICE","" + poTotalFormat.format( poTotal ) + "" );
    }
    FinalPoInfoh.put( "NOOFLINES","" + numberRec + "" );

	}
	catch ( Exception nfe )
	{
	  //System.out.println( "Error InNumber Format of PO Query:" );
	  nfe.printStackTrace();
	  throw nfe;
	}
	/*catch ( SQLException sqle )
	{
	  //System.out.println( "Error In SQL Format of PO Query:" ); ;
	  //sqle.printStackTrace();
	  throw sqle;
	}*/
	finally
	{
	  if ( dbrs != null ){ dbrs.close(); }
	}

	for ( int specflowLine=0; specflowLine < specFlowv.size(); specflowLine++ )
	{
	  Hashtable specflowLineData=new Hashtable();
	  specflowLineData= ( Hashtable ) specFlowv.elementAt( specflowLine );

	  String lineItemid=specflowLineData.get( "ITEMID" ).toString().trim();
	  String shelflifeper=specflowLineData.get( "SHELFLIFEPER" ).toString().trim();
      String shelfLifeBasis=specflowLineData.get( "SHELF_LIFE_BASIS" ).toString().trim();
      String shelfLifeDays=specflowLineData.get( "SHELF_LIFE_DAYS" ).toString().trim();
      String linsinJs=specflowLineData.get( "LINE" ).toString().trim();
	  String quantity=specflowLineData.get( "QUANTITY" ).toString().trim();
	  String supplierPartNo = specflowLineData.get( "SUPPLIER_PART_NO" ).toString().trim();

	  BigDecimal qtyF=new BigDecimal("0");
	  if ( quantity.trim().length() > 0 )
	  {
		qtyF=new BigDecimal(quantity);
	  }

	  if ( qtyF.floatValue() > 0 )
	  {
		if ( specflowLine > 0 )
		{
		  radianItems+=",";
		}
		radianItems+=lineItemid;

		if ( shelflifeper.trim().length() > 0 )
		{
		  Hashtable SpecData=new Hashtable();
		  if ( po1.length() > 0 && !"N".equalsIgnoreCase( po1 ) )
		  {
        if (!shelfLifeDays.equalsIgnoreCase("-1") && shelfLifeDays.length() > 0)
        {
          if (shelfLifeBasis.equalsIgnoreCase("M"))
          {

            SpecData.put( "FLOW_DOWN_DESC",""+res.format( "label.shelflifebasisdomreq",new Object[]{shelflifeper,shelfLifeDays})+"" );
          }
          else if (shelfLifeBasis.equalsIgnoreCase("S"))
          {
            SpecData.put( "FLOW_DOWN_DESC",""+res.format( "label.shelflifebasisdosreq",new Object[]{shelflifeper,shelfLifeDays})+"" );
          }
          else if (shelfLifeBasis.equalsIgnoreCase("R"))
          {
            SpecData.put( "FLOW_DOWN_DESC",""+res.format( "label.shelflifebasisdorreq",new Object[]{shelflifeper,shelfLifeDays})+"" );
          }
          else
          {
           SpecData.put( "FLOW_DOWN_DESC",""+res.format( "label.shelfliferequired",new Object[]{shelflifeper})+"" );
          }
        }
        else
        {
          SpecData.put( "FLOW_DOWN_DESC",""+res.format( "label.shelfliferequired",new Object[]{shelflifeper})+"" );
        }
      }
		  else
		  {
			SpecData.put( "FLOW_DOWN_DESC",""+res.getString("label.shelflifeonrelease")+"" );
		  }

		  SpecData.put( "CONTENT","" );
      BigDecimal rowNumber= new BigDecimal(linsinJs);
      rowNumber = rowNumber.multiply(new BigDecimal("1000"));
      SpecData.put( "LINE_ITEM","" + rowNumber + "" );
		  Flow_Data.addElement( SpecData );
		}
		else if (creatingblanketpo1)
		{
		  Hashtable SpecData=new Hashtable();
		  SpecData.put( "FLOW_DOWN_DESC",""+res.getString("label.shelflifeonrelease")+"" );
		  SpecData.put( "CONTENT","" );
      BigDecimal rowNumber= new BigDecimal(linsinJs);
      rowNumber = rowNumber.multiply(new BigDecimal("1000"));
      SpecData.put( "LINE_ITEM","" + rowNumber + "" );
		  Flow_Data.addElement( SpecData );
		}

		if (supplierPartNo.length() > 0)
		{
		  Hashtable SpecData=new Hashtable();
		  SpecData.put( "FLOW_DOWN_DESC",""+res.getString("label.supplier")+" "+res.getString("label.partnumber")+": "+supplierPartNo+"" );
		  SpecData.put( "CONTENT","" );
      BigDecimal rowNumber= new BigDecimal(linsinJs);
      rowNumber = rowNumber.multiply(new BigDecimal("1000"));
      SpecData.put( "LINE_ITEM","" + rowNumber + "" );
		  Flow_Data.addElement( SpecData );
		}

		getMSDSRevisionDate( lineItemid,res );
	  }
	}

	for ( int specflowLine=0; specflowLine < specFlowv.size(); specflowLine++ )
	{
	  Hashtable specflowLineData=new Hashtable();
	  specflowLineData= ( Hashtable ) specFlowv.elementAt( specflowLine );

	  String lineItemid=specflowLineData.get( "ITEMID" ).toString().trim();
	  String linsinJs=specflowLineData.get( "LINE" ).toString().trim();
	  String ammenNumber=specflowLineData.get( "AMMENDMEANT" ).toString().trim();
	  String prShipto=specflowLineData.get( "PRSHIPTO" ).toString().trim();

	  String quantity=specflowLineData.get( "QUANTITY" ).toString().trim();
	  BigDecimal qtyF=new BigDecimal("0");
	  if ( quantity.trim().length() > 0 )
	  {
		qtyF=new BigDecimal( quantity );
	  }

	  if ( qtyF.floatValue() > 0 )
	  {
		if ( prShipto.trim().length() > 0 )
		{
		  ShipTo=prShipto;
		}

		if ( ( lineItemid.length() > 0 ) && !lineItemid.equalsIgnoreCase( "0" ) )
		{
		  buildsendSpecs( HubNumber,linsinJs,lineItemid,po1,bpo,ShipTo,ammenNumber,connection2,shiptoloccompanyId,invengrp,res );
		  buildsendFlowdowns( linsinJs,lineItemid,po1,bpo,ShipTo,ammenNumber,connection2,shiptoloccompanyId,res );
		}
	  }
	}

	return FinalPoInfoh;
    }

    public void buildsendSpecs( String Hubname,String lineID,String itemID,String radianpo,String radianbpo,
                                String shipToLoc,String ammenNum,Connection connect1,String shiptocompanyid,String invengrp,ResourceLibrary res )
    {
      //StringBuffer Msgtemp=new StringBuffer();
      CallableStatement cs=null;
	  DBResultSet dbrs = null;
      ResultSet specrs=null;
      //Msgtemp.append( "{\n" );
      int count=0;

      int specLineCount=0; //this keep track of where to insert the data into flow_data so that we can display them in the right order.

      //String genericCoc="";
      //String genericCoa="";
      ResultSet rsGenCocData=null;
      //int rowNumber=Integer.parseInt( lineID ) * 1000;
      BigDecimal rowNumber= new BigDecimal(lineID);
      rowNumber = rowNumber.multiply(new BigDecimal("1000"));
      /*try
      {
        String genericcocQ="";
        if ( radianpo.length() > 0 && !"N".equalsIgnoreCase( radianpo ) )

        {
          genericcocQ="Select GENERIC_COC,GENERIC_COA from po_line_detail_view where RADIAN_PO = " + radianpo + " and PO_LINE = " + rowNumber + " and AMENDMENT=" + ammenNum + " ";
        }
        else
        {
          genericcocQ="Select GENERIC_COC,GENERIC_COA from bpo_line_detail_view where BPO = " + radianbpo + " and BPO_LINE = " + rowNumber +  " and AMENDMENT=" + ammenNum + " ";
        }

        dbrs=db.doQuery( genericcocQ );
        rsGenCocData=dbrs.getResultSet();

        while ( rsGenCocData.next() )
        {
          genericCoc=rsGenCocData.getString( "GENERIC_COC" ) == null ? "" : rsGenCocData.getString( "GENERIC_COC" ).trim();
          genericCoa=rsGenCocData.getString( "GENERIC_COA" ) == null ? "" : rsGenCocData.getString( "GENERIC_COA" ).trim();
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        if ( dbrs != null ){ dbrs.close(); }
      }*/

      //Generic COC and/or COA
      /*String genericSpecNote="";
      if ( ( genericCoc.equalsIgnoreCase( "Y" ) ) && ( genericCoa.equalsIgnoreCase( "Y" ) ) )
      {
        genericSpecNote="Your general Certificate of Conformance and Certificate of Analysis / Test Report";
        COAcount++;
      }
      else if ( genericCoc.equalsIgnoreCase( "Y" ) )
      {
        genericSpecNote="Your general Certificate of Conformance";
      }
      else if ( genericCoa.equalsIgnoreCase( "Y" ) )
      {
        genericSpecNote="Your general Certificate of Analysis / Test Report";
        COAcount++;
      }*/
      //putting it together
      /*if ( genericSpecNote.length() > 1 )
      {
        Hashtable SpecData=new Hashtable();
        SpecData.put( "FLOW_DOWN_DESC",lineCount + ") " + genericSpecNote );
        SpecData.put( "CONTENT","" );
        SpecData.put( "LINE_ITEM","" + rowNumber + "" );
        Flow_Data.insertElementAt( SpecData,specLineCount );
        specLineCount++;
        lineCount++;
      }*/

      try
      {
        cs=connect1.prepareCall( "{call pkg_po.Po_spec(?,?,?,?,?,?,?,?)}" );
        cs.setString(1,shipToLoc);
        if (shiptocompanyid.length() > 0){cs.setString(2, shiptocompanyid);}else{cs.setNull(2, OracleTypes.VARCHAR);}
        if (itemID.length() > 0){cs.setInt(3, Integer.parseInt(itemID));}else{cs.setNull(1, OracleTypes.INTEGER);}
        if (radianpo.length() > 0 && !"N".equalsIgnoreCase(radianpo)) {
          cs.setBigDecimal( 4,new BigDecimal(""+ radianpo +"") );
        }else if (radianbpo.length() > 0) {
          cs.setBigDecimal( 4,new BigDecimal(""+ radianbpo +"") );
        }else {
          cs.setNull(4, OracleTypes.INTEGER);
        }

        if (lineID.length() > 0){cs.setBigDecimal(5, rowNumber);}else{cs.setNull(1, OracleTypes.INTEGER);}
        if (ammenNum.length() > 0){cs.setInt(6, Integer.parseInt(ammenNum));}else{cs.setNull(6, OracleTypes.INTEGER);}
        if (invengrp.length() > 0){cs.setString(7, invengrp);}else{cs.setNull(7, OracleTypes.VARCHAR);}

        cs.registerOutParameter(8, OracleTypes.VARCHAR);
        cs.execute();

        String resultQuery=cs.getObject( 8 ).toString();
        dbrs=db.doQuery( resultQuery );
        specrs=dbrs.getResultSet();

        String tmpCoc="";
        String tmpCoa="";
        String tmpNone="";
        String tmpPoPrint ="";

        Vector cocV=new Vector( 10 );
        Vector coaV=new Vector( 10 );
        Vector noneV=new Vector( 10 );
        Vector poPrintV=new Vector( 10 );

                int i = 0;
				while (specrs.next()) {
				 //String spec_id=specrs.getString( "SPEC_ID" ) == null ? "" : specrs.getString( "SPEC_ID" ).trim();
				 String savedCoc = specrs.getString("SAVED_COC") == null ? "" :
					specrs.getString("SAVED_COC").trim();
				 String savedCoa = specrs.getString("SAVED_COA") == null ? "" :
					specrs.getString("SAVED_COA").trim();
					//String coc=specrs.getString( "COC" ) == null ? "" : specrs.getString( "COC" ).trim();
					//String coa=specrs.getString( "COA" ) == null ? "" : specrs.getString( "COA" ).trim();
					//String ok=specrs.getString( "OK" ) == null ? "" : specrs.getString( "OK" ).trim();
				 String spec_id_display = specrs.getString("SPEC_ID_DISPLAY") == null ? "" : specrs.getString("SPEC_ID_DISPLAY").trim();
                 String poPrintStatement = specrs.getString("PO_PRINT_STATEMENT") == null ? "" : specrs.getString("PO_PRINT_STATEMENT").trim();
                 String printOnPo = specrs.getString("PRINT_ON_PO") == null ? "" : specrs.getString("PRINT_ON_PO").trim();
                 if (!"Y".equalsIgnoreCase(printOnPo))
                 {
                  savedCoc = "";
                  savedCoa = "";
                  poPrintStatement = "";
                 }
                 else if (poPrintStatement.length() > 0)
                 {
                     spec_id_display = poPrintStatement;
                 }

                 i++;
				 if (poPrintStatement.length() > 0 && ( savedCoc.equalsIgnoreCase("Y") || savedCoa.equalsIgnoreCase("Y")))
                 {
                     tmpPoPrint = "";
                     poPrintV.addElement(spec_id_display.toUpperCase());
                 }
                 else if (savedCoc.equalsIgnoreCase("Y") && savedCoa.equalsIgnoreCase("Y")) {
					tmpCoc = ""+res.getString("label.pococrequired")+" ";
					if (!cocV.contains(spec_id_display.toUpperCase())) {
					 cocV.addElement(spec_id_display.toUpperCase());
					}
					tmpCoa = ""+res.getString("label.pocoarequired")+" ";
					if (!coaV.contains(spec_id_display.toUpperCase())) {
					 COAcount++;
					 coaV.addElement(spec_id_display.toUpperCase());
					}
				 }
				 else if (savedCoc.equalsIgnoreCase("Y") && !cocV.contains(spec_id_display.toUpperCase())) {
					tmpCoc = ""+res.getString("label.pococrequired")+" ";
					cocV.addElement(spec_id_display.toUpperCase());
				 }
				 else if (savedCoa.equalsIgnoreCase("Y") && !coaV.contains(spec_id_display.toUpperCase())) {
					tmpCoa = ""+res.getString("label.pocoarequired")+" ";
					coaV.addElement(spec_id_display.toUpperCase());
					COAcount++;
				 }
				 else {
					//tmpNone = "ITEM MUST MEET Specifications ";
					//noneV.addElement(spec_id_display.toUpperCase());
					//System.out.println("This Should Not happen. Spec can not be here with nothing checked PO "+radianpo+"  lineID "+lineID+"");
				 }
				} //end of while

        //11-26-02 putting the spec info together
        int j=0;
        //first COC
        for ( j=0; j < cocV.size(); j++ )
        {
          tmpCoc+= ( String ) cocV.elementAt( j ) + "; ";
        }
        //removing the last commas ','.  If string contain spec ID then add to list
        if ( tmpCoc.indexOf( ";" ) > 1 )
        {
          tmpCoc=tmpCoc.substring( 0,tmpCoc.length() - 2 );
          Vector tmpV=breakStringApart( tmpCoc,90,10 );
          for ( int k=0; k < tmpV.size(); k++ )
          {
            Hashtable SpecData=new Hashtable();
            if ( k == 0 )
            {
              SpecData.put( "FLOW_DOWN_DESC",lineCount + ") " + ( String ) tmpV.elementAt( k ) );
              lineCount++;
            }
            else
            {
              if ( lineCount > 10 )
              {
                SpecData.put( "FLOW_DOWN_DESC","     " + ( String ) tmpV.elementAt( k ) ); //if line count is two digit than add an extra blank space
              }
              else
              {
                SpecData.put( "FLOW_DOWN_DESC","    " + ( String ) tmpV.elementAt( k ) );
              }
            }
            SpecData.put( "CONTENT","" );
            SpecData.put( "LINE_ITEM","" + rowNumber + "" );
            Flow_Data.insertElementAt( SpecData,specLineCount );
            specLineCount++;
          }
        }
        //next COA
        for ( j=0; j < coaV.size(); j++ )
        {
          tmpCoa+= ( String ) coaV.elementAt( j ) + "; ";
        }
        //removing the last commas ','.  If string contain spec ID then add to list
        if ( tmpCoa.indexOf( ";" ) > 1 )
        {
          tmpCoa=tmpCoa.substring( 0,tmpCoa.length() - 2 );
          Vector tmpV=breakStringApart( tmpCoa,90,10 );
          for ( int k=0; k < tmpV.size(); k++ )
          {
            Hashtable SpecData=new Hashtable();
            if ( k == 0 )
            {
              SpecData.put( "FLOW_DOWN_DESC",lineCount + ") " + ( String ) tmpV.elementAt( k ) );
              lineCount++;
            }
            else
            {
              if ( lineCount > 10 )
              {
                SpecData.put( "FLOW_DOWN_DESC","     " + ( String ) tmpV.elementAt( k ) ); //if line count is two digit than add an extra blank space
              }
              else
              {
                SpecData.put( "FLOW_DOWN_DESC","    " + ( String ) tmpV.elementAt( k ) );
              }
            }
            SpecData.put( "CONTENT","" );
            SpecData.put( "LINE_ITEM","" + rowNumber + "" );
            Flow_Data.insertElementAt( SpecData,specLineCount );
            specLineCount++;
          }
        }

        //next COA
        for ( j=0; j < poPrintV.size(); j++ )
        {
          tmpPoPrint+= ( String ) poPrintV.elementAt( j ) + "; ";
        }
        //removing the last commas ','.  If string contain spec ID then add to list
        if ( tmpPoPrint.indexOf( ";" ) > 1 )
        {
          tmpPoPrint=tmpPoPrint.substring( 0,tmpPoPrint.length() - 2 );
          Vector tmpV=breakStringApart( tmpPoPrint,90,10 );
          for ( int k=0; k < tmpV.size(); k++ )
          {
            Hashtable SpecData=new Hashtable();
            if ( k == 0 )
            {
              SpecData.put( "FLOW_DOWN_DESC",lineCount + ") " + ( String ) tmpV.elementAt( k ) );
              lineCount++;
            }
            else
            {
              if ( lineCount > 10 )
              {
                SpecData.put( "FLOW_DOWN_DESC","     " + ( String ) tmpV.elementAt( k ) ); //if line count is two digit than add an extra blank space
              }
              else
              {
                SpecData.put( "FLOW_DOWN_DESC","    " + ( String ) tmpV.elementAt( k ) );
              }
            }
            SpecData.put( "CONTENT","" );
            SpecData.put( "LINE_ITEM","" + rowNumber + "" );
            Flow_Data.insertElementAt( SpecData,specLineCount );
            specLineCount++;
          }
        }

        //finally MEET SPEC
        for ( j=0; j < noneV.size(); j++ )
        {
          tmpNone+= ( String ) noneV.elementAt( j ) + "; ";
        }
        //removing the last commas ', '.  If string contain spec ID then add to list
        if ( tmpNone.indexOf( ";" ) > 1 )
        {
          tmpNone=tmpNone.substring( 0,tmpNone.length() - 2 );
          Vector tmpV=breakStringApart( tmpNone,100,10 );
          for ( int k=0; k < tmpV.size(); k++ )
          {
            Hashtable SpecData=new Hashtable();
            if ( k == 0 )
            {
              SpecData.put( "FLOW_DOWN_DESC",lineCount + ") " + ( String ) tmpV.elementAt( k ) );
              lineCount++;
            }
            else
            {
              if ( lineCount > 10 )
              {
                SpecData.put( "FLOW_DOWN_DESC","     " + ( String ) tmpV.elementAt( k ) ); //if line count is two digit than add an extra blank space
              }
              else
              {
                SpecData.put( "FLOW_DOWN_DESC","    " + ( String ) tmpV.elementAt( k ) );
              }
            }
            SpecData.put( "CONTENT","" );
            SpecData.put( "LINE_ITEM","" + rowNumber + "" );
            Flow_Data.insertElementAt( SpecData,specLineCount );
            specLineCount++;
          }
        }

        //add the rest of the info if there is a spec
        if ( specLineCount > 0 )
        {
          //getting fax number info
          String faxTo="";
          String faxNumber="";
          String tmpQuery="";
          try
          {
            tmpQuery="select p.first_name || ' ' || p.last_name full_name, nvl(p.fax,'0') fax_number from customer.location a, customer.personnel p " +
                     "where a.company_id = p.company_id and a.location_contact = p.personnel_id and a.location_id = '" + shipToLoc + "' and " +
                     "a.company_id = '" + shiptocompanyid + "'";
            dbrs=db.doQuery( tmpQuery );
            rsGenCocData=dbrs.getResultSet();
            while ( rsGenCocData.next() )
            {
              faxTo=rsGenCocData.getString( "full_name" ) == null ? "" : rsGenCocData.getString( "full_name" ).trim();
              faxNumber=rsGenCocData.getString( "fax_number" ) == null ? "" : rsGenCocData.getString( "fax_number" ).trim();
            }
          }
          catch ( Exception e )
          {
            faxTo="";
            faxNumber="";
            HelpObjs.sendMail( db,"Query Error",tmpQuery,86405,false );
          }
          finally
          {
            if ( dbrs != null ){ dbrs.close(); }
          }
          String moreInfo="";
          //don't add the following clause if hub doesn't want SPEC fax
          if ( faxTo.length() > 3 && !"0".equalsIgnoreCase( faxNumber ) && !"2101".equalsIgnoreCase( Hubname ) )
          {
            moreInfo = res.format( "label.poallcertreffax",new Object[]{faxTo,faxNumber});
          }
          else
          {
            moreInfo = res.getString("label.poallcertref");
          }
          /*String moreInfo="All Certificates MUST reference Specifications and accompany shipment";
          //don't add the follow clause if hub doesn't want SPEC fax
          if ( faxTo.length() > 3 && !"0".equalsIgnoreCase( faxNumber ) && !"2101".equalsIgnoreCase( Hubname ) )
          {
            moreInfo+=" OR be FAXED IN ADVANCE to " + faxTo + " at " + faxNumber;
          }
          moreInfo+=" to ensure timely processing.  SEND ALL CERTIFICATES FOR ALL LOTS SHIPPED.";*/
          Vector tmpV=breakStringApart( moreInfo,100,10 );
          for ( int k=0; k < tmpV.size(); k++ )
          {
            Hashtable SpecData=new Hashtable();
            if ( k == 0 )
            {
              SpecData.put( "FLOW_DOWN_DESC",lineCount + ") " + ( String ) tmpV.elementAt( k ) );
              lineCount++;
            }
            else
            {
              if ( lineCount > 10 )
              {
                SpecData.put( "FLOW_DOWN_DESC","     " + ( String ) tmpV.elementAt( k ) ); //if line count is two digit than add an extra blank space
              }
              else
              {
                SpecData.put( "FLOW_DOWN_DESC","    " + ( String ) tmpV.elementAt( k ) );
              }
            }
            SpecData.put( "CONTENT","" );
            SpecData.put( "LINE_ITEM","" + rowNumber + "" );
            Flow_Data.insertElementAt( SpecData,specLineCount );
            specLineCount++;
          }

          //RMA - Return Material Authorization
          moreInfo=res.getString("label.poreturnproducts");
          tmpV=breakStringApart( moreInfo,100,10 );
          for ( int k=0; k < tmpV.size(); k++ )
          {
            Hashtable SpecData=new Hashtable();
            if ( k == 0 )
            {
              SpecData.put( "FLOW_DOWN_DESC",lineCount + ") " + ( String ) tmpV.elementAt( k ) );
              lineCount++;
            }
            else
            {
              if ( lineCount > 10 )
              {
                SpecData.put( "FLOW_DOWN_DESC","     " + ( String ) tmpV.elementAt( k ) ); //if line count is two digit than add an extra blank space
              }
              else
              {
                SpecData.put( "FLOW_DOWN_DESC","    " + ( String ) tmpV.elementAt( k ) );
              }
            }
            SpecData.put( "CONTENT","" );
            SpecData.put( "LINE_ITEM","" + rowNumber + "" );
            Flow_Data.insertElementAt( SpecData,specLineCount );
            specLineCount++;
          }
        } //end of if specLineCount > 0

        Hashtable SpecData1=new Hashtable();
        SpecData1.put( "FLOW_DOWN_DESC",lineCount + ") " + res.getString("label.nosuballowed"));
        SpecData1.put( "CONTENT","" );
        SpecData1.put( "LINE_ITEM","" + rowNumber + "" );
        Flow_Data.insertElementAt( SpecData1,specLineCount );
        lineCount++;

        //set line count for shelf life
        if ( Flow_Data.size() > 0 )
        {
          Hashtable tmpH= ( Hashtable ) Flow_Data.lastElement();
          String tmpShl= ( String ) tmpH.get( "FLOW_DOWN_DESC" );
          if ( tmpShl.indexOf( "SHELF LIFE" ) > 0 )
          {
            tmpShl=lineCount + ") " + tmpShl;
            tmpH.put( "FLOW_DOWN_DESC",tmpShl );
          }
        }

        //add line separation ****************
        if ( Flow_Data.size() > 0 )
        {
          Hashtable SpecData=new Hashtable();
          SpecData.put( "FLOW_DOWN_DESC","***********************************************************************************************************" );
          SpecData.put( "CONTENT","" );
          SpecData.put( "LINE_ITEM","" + rowNumber + "" );
          Flow_Data.addElement( SpecData );
        } //end of line separation
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
      }
      finally
      {
        if ( cs != null )
        {
          try
          {
            cs.close();
          }
          catch ( SQLException se )
          {}
        }
        if ( dbrs != null ){ dbrs.close(); }
      }

      /*if ( count == 0 )
      {
        Msgtemp.append( "insidehtml +=\"<TR>\";\n" );
        Msgtemp.append( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"7\\\">No Records Found</TD>\";\n" );
        Msgtemp.append( "insidehtml +=\"</TR>\";\n" );
      }*/
   }

    public Vector vendorQualificationStmt( String inventoryGroup)
    {
      CallableStatement cs=null;
	  DBResultSet dbrs = null;
      ResultSet vendorQualRs=null;
      int rowNumber=0;
      Vector vendorQualDatav = new Vector();
      Hashtable vendorQualData = new Hashtable();
      String qualStatement = "";
                
      /*String firstStmt = "NOTE THE SPECIFICATION AND CERTIFICATION REQUIREMENTS ON THIS PO AS HIGHLIGHTED BELOW";
      Vector tmpV=breakStringApart( firstStmt,80,10 );
      for ( int k=0; k < tmpV.size(); k++ )
      {
        vendorQualData = new Hashtable();
        if ( k == 0 )
        {
          vendorQualData.put( "VENDOR_QUAL_STMNT","  " + ( String ) tmpV.elementAt( k ) );
          lineCount++;
        }
        else
        {
          if ( lineCount > 10 )
          {
            vendorQualData.put( "VENDOR_QUAL_STMNT","  " + ( String ) tmpV.elementAt( k ) ); //if line count is two digit than add an extra blank space
          }
          else
          {
            vendorQualData.put( "VENDOR_QUAL_STMNT","  " + ( String ) tmpV.elementAt( k ) );
          }
        }
        //reoprtlog.info("rowNumber   "+rowNumber);
        vendorQualData.put( "LINE_ITEM","" + rowNumber + "" );
        vendorQualDatav.addElement(vendorQualData);
        rowNumber++;
      }*/

      try
      {
        String tmpQuery="select * from IG_VENDOR_QUAL_STMNT x where inventory_group = '" + inventoryGroup + "'";
        dbrs=db.doQuery( tmpQuery );
        vendorQualRs=dbrs.getResultSet();
        while ( vendorQualRs.next() )
        {
          qualStatement=vendorQualRs.getString( "QUAL_STMNT" ) == null ? "" : vendorQualRs.getString( "QUAL_STMNT" ).trim();
          vendorQualData = new Hashtable();
          Vector tmpV=breakStringApart( qualStatement,80,10 );
          for ( int k=0; k < tmpV.size(); k++ )
          {
            vendorQualData = new Hashtable();
            if ( k == 0 )
            {
              vendorQualData.put( "VENDOR_QUAL_STMNT","  " + ( String ) tmpV.elementAt( k ) );
              lineCount++;
            }
            else
            {
              if ( lineCount > 10 )
              {
                vendorQualData.put( "VENDOR_QUAL_STMNT","  " + ( String ) tmpV.elementAt( k ) ); //if line count is two digit than add an extra blank space
              }
              else
              {
                vendorQualData.put( "VENDOR_QUAL_STMNT","  " + ( String ) tmpV.elementAt( k ) );
              }
            }
            //reoprtlog.info("rowNumber   "+rowNumber);
            vendorQualData.put( "LINE_ITEM","" + rowNumber + "" );
            vendorQualDatav.addElement(vendorQualData);
            rowNumber++;
          }
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        if ( dbrs != null ){ dbrs.close(); }
      }

      //reoprtlog.info("rowNumber   "+rowNumber);
      if (rowNumber == 0)
      {
          vendorQualData = new Hashtable();
          vendorQualData.put( "VENDOR_QUAL_STMNT"," ");
          vendorQualData.put( "LINE_ITEM","" + rowNumber + "" );
          vendorQualDatav.addElement(vendorQualData);
      }

      return vendorQualDatav;
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
			}
			else {
			 result.addElement(tmpVal);
			 tmpVal = tmp + " ";
			}
		 } //end of while has more tokens
		 result.addElement(tmpVal);
		}
		else {
		 result.addElement(value);
		}
		return result;
   }  //end of method

   public void buildsendFlowdowns( String lineID,String itemID,String radianpo,String radianbpo,String shipToLoc,String ammenNum,Connection connect1,
                                   String shiptocompanyid, ResourceLibrary res)
   {
     CallableStatement cs=null;
	 DBResultSet dbrs = null;
     ResultSet flowRs=null;
     //StringBuffer Msgtemp=new StringBuffer();
     //Msgtemp.append( "{\n" );
     int count=0;

     //Won't show anything if the po or bpo is not a valid number
     try
     {

       //int rowNumber=Integer.parseInt( lineID ) * 1000;
       BigDecimal rowNumber= new BigDecimal(lineID);
       rowNumber = rowNumber.multiply(new BigDecimal("1000"));

       cs=connect1.prepareCall( "{call pkg_po.Po_flowdown(?,?,?,?,?,?,?)}" );

       cs.setString( 1,shipToLoc );
       cs.setString(1,shipToLoc);
       if (shiptocompanyid.length() > 0){cs.setString(2, shiptocompanyid);}else{cs.setNull(2, OracleTypes.VARCHAR);}
       if (itemID.length() > 0){cs.setInt(3, Integer.parseInt(itemID));}else{cs.setNull(1, OracleTypes.INTEGER);}
       if (radianpo.length() > 0 && !"N".equalsIgnoreCase(radianpo)){cs.setBigDecimal( 4,new BigDecimal(""+ radianpo +"") );}
       else if (radianbpo.length() > 0){cs.setBigDecimal( 4,new BigDecimal(""+ radianbpo +"") );}
       else{cs.setNull(4, OracleTypes.INTEGER);}
       if (lineID.length() > 0){cs.setBigDecimal(5, rowNumber);}else{cs.setNull(1, OracleTypes.INTEGER);}
       if (ammenNum.length() > 0){cs.setInt(6, Integer.parseInt(ammenNum));}else{cs.setNull(6, OracleTypes.INTEGER);}

       cs.registerOutParameter(7, OracleTypes.VARCHAR);
       cs.execute();

       String resultQuery=cs.getObject( 7 ).toString();
       dbrs=db.doQuery( resultQuery );
       flowRs=dbrs.getResultSet();
	   Vector flowV=new Vector( 10 );

       while ( flowRs.next() )
       {
         String flow_down=flowRs.getString( "FLOW_DOWN" ) == null ? "" : flowRs.getString( "FLOW_DOWN" ).trim();
	     String flow_down_desc=flowRs.getString( "FLOW_DOWN_DESC" ) == null ? "" :flowRs.getString( "FLOW_DOWN_DESC" ).trim();
   	     String revdate =flowRs.getString( "REVISION_DATE" ) == null ? "" : flowRs.getString( "REVISION_DATE" ).trim();
         String ok=flowRs.getString( "OK" ) == null ? "" : flowRs.getString( "OK" ).trim();
         String flowDownType=flowRs.getString( "FLOW_DOWN_TYPE" ) == null ? "" :flowRs.getString( "FLOW_DOWN_TYPE" ).trim();
	 if (revdate.length() > 0) {revdate =res.getString("label.revisiondate")+ ":" + revdate; }
	 //if (flow_down_desc.equalsIgnoreCase(flow_down)){flow_down_desc = "";}
	 if (flow_down_desc.trim().length() == 0){flow_down_desc = flow_down;}

	 if ( ok.equalsIgnoreCase( "Y" ) && !"Receiving".equalsIgnoreCase(flowDownType))
	 {
	   count++;
	   Hashtable SpecData=new Hashtable();
	   if ( count == 1 )
	   {
		 SpecData.put( "FLOW_DOWN_DESC",res.getString("label.addlreq")+ ":" );
		 SpecData.put( "CONTENT","0" );
		 SpecData.put( "LINE_ITEM","" + rowNumber + "" );
		 Flow_Data.addElement( SpecData );
	   }

	   String tmpflowdesc=flow_down_desc + " " + revdate;
	   if ( !flowV.contains( tmpflowdesc ) )
	   {
		 flowV.addElement( tmpflowdesc );

		 Vector tmpV=breakStringApart( tmpflowdesc,80,10 );
		 for ( int k=0; k < tmpV.size(); k++ )
		 {
		   SpecData=new Hashtable();
		   String flowdDescprt= ( String ) tmpV.elementAt( k );
		   SpecData.put( "FLOW_DOWN_DESC","" + flowdDescprt + "" );
		   SpecData.put( "CONTENT","" );
		   SpecData.put( "LINE_ITEM","" + rowNumber + "" );
		   Flow_Data.addElement( SpecData );

		 }
          }
		  /*SpecData=new Hashtable();
				SpecData.put( "FLOW_DOWN_DESC","" + flow_down + " " + revdate + " "+flow_down_desc+"" );
				SpecData.put( "CONTENT","" + count + "" );
				SpecData.put( "LINE_ITEM","" + rowNumber + "" );
				Flow_Data.addElement( SpecData );*/

		  /*if (flow_down_desc.length() > 0 )
				{
			   SpecData=new Hashtable();
			   SpecData.put( "FLOW_DOWN_DESC","" + flow_down_desc + "" );
			   SpecData.put( "CONTENT","" + ( count + 1 ) + "" );
			   SpecData.put( "LINE_ITEM","" + rowNumber + "" );
			   Flow_Data.addElement( SpecData );
				}*/
		}
       }

	  if ( count > 0 )
	  {
		Hashtable SpecData=new Hashtable();
		SpecData.put( "FLOW_DOWN_DESC","***********************************************************************************************************" );
		SpecData.put( "CONTENT","" + ( count + 1 ) + "" );
		SpecData.put( "LINE_ITEM","" + rowNumber + "" );
		Flow_Data.addElement( SpecData );
	  }
     }
     catch ( Exception e )
     {
       //e.printStackTrace();
     }
     finally
     {
       if ( cs != null )
       {
         try
         {
           cs.close();
         }
         catch ( SQLException se )
         {}
       }
       if ( dbrs != null ){ dbrs.close(); }
     }



     /*if ( count == 0 )
     {
       Msgtemp.append( "insidehtml +=\"<TR>\";\n" );
       Msgtemp.append( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"7\\\">No Records Found</TD>\";\n" );
       Msgtemp.append( "insidehtml +=\"</TR>\";\n" );
     }*/
   }

	 protected Vector getPoLineNotes(Vector poLineData, String opsEntityId, ResourceLibrary res) throws Exception {
		Vector JDEpoInfoh = new Vector();
		Hashtable JdepoH = new Hashtable();
        int lineCharacterSize = 100;
        
        if ( ("HAASTCMDEU".equalsIgnoreCase(opsEntityId) || "HAASSCMDEU".equalsIgnoreCase(opsEntityId) ))
        {
            lineCharacterSize = 80;
        }

        //lookup purchase order information from purchase_info_data_view
		try {
		 int numberRec = 0;
		 for (int loop = 0; loop < poLineData.size(); loop++) {
			Hashtable poLineHash = new Hashtable();
			poLineHash = (Hashtable) poLineData.elementAt(loop);

			String radian_po = poLineHash.get("RADIAN_PO").toString();
			String po_line = poLineHash.get("LINE_ITEM").toString();
			String po_line_note = poLineHash.get("PO_LINE_NOTE").toString();
			po_line_note = HelpObjs.findReplace(po_line_note, "\n", "<BR>");
			po_line_note = HelpObjs.findReplace(po_line_note, "<BR>", " \n ");
            String supplierShipFromLocaltion = poLineHash.get("SUPPLIER_SHIP_FROM_LOCATION").toString();             
            if (supplierShipFromLocaltion.length() > 0)
            {
                  po_line_note = po_line_note + " \n " + "Supplier Ship From Location:" + supplierShipFromLocaltion;
            }
            
            if ( ("HAASTCMUSA".equalsIgnoreCase(opsEntityId) || "HAASTCMUSA".equalsIgnoreCase(opsEntityId) ))
            {
                // DPAS Statement
            	String dpas_rating = poLineHash.get("DPAS_RATING").toString(); 
            	if(dpas_rating != null && dpas_rating.length() > 0)
            		po_line_note = res.format( "label.dpasstatement",new Object[]{dpas_rating}) + "\n " + po_line_note;
            }
            
            int i = 1000;

			StringTokenizer st = new StringTokenizer(po_line_note, "\n");
			if (st.countTokens() > 0) {
			 while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				tok = tok.trim();
				Vector poLineNoteVector = breakStringApart(tok, lineCharacterSize, 10);

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
			}
			else {
			 Vector poLineNoteVector = breakStringApart(po_line_note, lineCharacterSize, 10);
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
		 //sqle.printStackTrace();
		 //System.out.println("Error In SQL Format of JDEPO Query: </P>");
		 throw sqle;
		}
		return JDEpoInfoh;
   }

   protected Vector getmoreComments()
   {
     Vector JDEpoInfoh=new Vector();
     Hashtable JdepoH=new Hashtable();
     JdepoH=new Hashtable();

     JdepoH.put( "LINE_NUM","1000" );
     JdepoH.put( "TEXT_LINE"," " );
     JDEpoInfoh.addElement( JdepoH );

     return JDEpoInfoh;
   }

   protected Hashtable getBuyerInfo( String buyer,String invengrp,ResourceLibrary res )
   {
     Hashtable BuyerInfoh=new Hashtable();
	 DBResultSet dbrs = null;
     try
     {
       int numberRec=0;

       String stmt="select * from customer.personnel where personnel_id = " + buyer + " and company_id = 'Radian'";
       dbrs=db.doQuery( stmt );
       ResultSet buyerInfoRs=dbrs.getResultSet();
       Hashtable result=new Hashtable();

       result=new Hashtable();
       while ( buyerInfoRs.next() )
       {
         String first_name=buyerInfoRs.getString( "FIRST_NAME" ) == null ? "" : buyerInfoRs.getString( "FIRST_NAME" ).trim();
         String last_name=buyerInfoRs.getString( "LAST_NAME" ) == null ? "" : buyerInfoRs.getString( "LAST_NAME" ).trim();
         String phone=buyerInfoRs.getString( "PHONE" ) == null ? "" : buyerInfoRs.getString( "PHONE" ).trim();
         String email=buyerInfoRs.getString( "EMAIL" ) == null ? "" : buyerInfoRs.getString( "EMAIL" ).trim();
         String fax=buyerInfoRs.getString( "FAX" ) == null ? "" : buyerInfoRs.getString( "FAX" ).trim();

         BuyerInfoh.put( "buyer_name","" + first_name + " " + last_name + "" );
         BuyerInfoh.put( "byrsig","" + first_name + "_" + last_name + "" );
         BuyerInfoh.put( "buyer_phone",phone );
         BuyerInfoh.put( "buyer_fax",fax );
         BuyerInfoh.put( "buyer_email",email );

         numberRec+=1;
       }
       //buyerInfoRs.close();
     }
     catch ( Exception sqle )
     {
       //sqle.printStackTrace();
     }
     finally
     {
       if ( dbrs != null ){ dbrs.close(); }
     }

	try
	{
	  int numberRec=0;

	  String stmt="select FIRST_NAME, LAST_NAME, MID_INITIAL, EMAIL from  customer.personnel x, user_group_member_ig y where x.personnel_id= y.personnel_id and x.company_id = 'Radian' and x.status='A' and ";
			 stmt+=" y.user_group_id = 'ShippingNotice' and y.inventory_group = '"+invengrp+"' ";
	  dbrs=db.doQuery( stmt );
	  ResultSet buyerInfoRs=dbrs.getResultSet();
	  Hashtable result=new Hashtable();

	  result=new Hashtable();
	  while ( buyerInfoRs.next() )
	  {
		String first_name=buyerInfoRs.getString( "FIRST_NAME" ) == null ? "" : buyerInfoRs.getString( "FIRST_NAME" ).trim();
		String last_name=buyerInfoRs.getString( "LAST_NAME" ) == null ? "" : buyerInfoRs.getString( "LAST_NAME" ).trim();
		//String phone=buyerInfoRs.getString( "PHONE" ) == null ? "" : buyerInfoRs.getString( "PHONE" ).trim();
		String email=buyerInfoRs.getString( "EMAIL" ) == null ? "" : buyerInfoRs.getString( "EMAIL" ).trim();
		//String fax=buyerInfoRs.getString( "FAX" ) == null ? "" : buyerInfoRs.getString( "FAX" ).trim();

		BuyerInfoh.put( "expediter_name","" + first_name + " " + last_name + "" );
		BuyerInfoh.put( "expediter_email",email );

		numberRec+=1;
	  }

	  if (numberRec == 0)
	  {
		BuyerInfoh.put( "expediter_name",""+res.getString("label.pobuyerabove")+"" );
		BuyerInfoh.put( "expediter_email","" );
	  }
	  //buyerInfoRs.close();
	}
	catch ( Exception sqle )
	{
	  //sqle.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ){ dbrs.close(); }
	}

     return BuyerInfoh;
    }

protected Hashtable getInvoiceaddress( String locationid )
{
 Hashtable invoiAddh=new Hashtable();
 DBResultSet dbrs = null;
 ResultSet invocieaddrs = null;
 try
 {
   int numberRec=0;

   String stmt="select * from customer.location where location_id = '" + locationid + "'";
   dbrs=db.doQuery( stmt );
   invocieaddrs=dbrs.getResultSet();
   Hashtable result=new Hashtable();

   result=new Hashtable();
   while ( invocieaddrs.next() )
   {
	 //String location_id=invocieaddrs.getString( "LOCATION_ID" ) == null ? "" : invocieaddrs.getString( "LOCATION_ID" ).trim();
	 String country_abbrev=invocieaddrs.getString( "COUNTRY_ABBREV" ) == null ? "" : invocieaddrs.getString( "COUNTRY_ABBREV" ).trim();
	 String state_abbrev=invocieaddrs.getString( "STATE_ABBREV" ) == null ? "" : invocieaddrs.getString( "STATE_ABBREV" ).trim();
	 String address_line_1=invocieaddrs.getString( "ADDRESS_LINE_1" ) == null ? "" : invocieaddrs.getString( "ADDRESS_LINE_1" ).trim();
	 String address_line_2=invocieaddrs.getString( "ADDRESS_LINE_2" ) == null ? "" : invocieaddrs.getString( "ADDRESS_LINE_2" ).trim();
	 String address_line_3=invocieaddrs.getString( "ADDRESS_LINE_3" ) == null ? "" : invocieaddrs.getString( "ADDRESS_LINE_3" ).trim();
	 String city=invocieaddrs.getString( "CITY" ) == null ? "" : invocieaddrs.getString( "CITY" ).trim();
	 String zip=invocieaddrs.getString( "ZIP" ) == null ? "" : invocieaddrs.getString( "ZIP" ).trim();
	 //String location_desc=invocieaddrs.getString( "LOCATION_DESC" ) == null ? "" : invocieaddrs.getString( "LOCATION_DESC" ).trim();
	 //String client_location_code = invocieaddrs.getString("CLIENT_LOCATION_CODE")==null?"":invocieaddrs.getString("CLIENT_LOCATION_CODE").trim();
	 //String company_id=invocieaddrs.getString( "COMPANY_ID" ) == null ? "" : invocieaddrs.getString( "COMPANY_ID" ).trim();

	 invoiAddh.put( "ADDRESS_LINE1","" + address_line_1 + "" );
	 boolean countryCity = false;
	 if (address_line_2.length() >0 )
	 {
		invoiAddh.put("ADDRESS_LINE2", "" + address_line_2 + "");
	 }
	 else if (address_line_3.length() >0 )
	 {
		invoiAddh.put("ADDRESS_LINE2", "" + address_line_3 + "");
	 }
   else
	 {
		invoiAddh.put( "ADDRESS_LINE2","" + city + " " + state_abbrev + " "+ zip +"  "+country_abbrev+"" );
		invoiAddh.put("ADDRESS_LINE3", "");
		countryCity = true;
	 }

	 if (address_line_3.length() >0 )
	 {
		invoiAddh.put("ADDRESS_LINE3", "" + address_line_3 + "");
	 }
	 else if (!countryCity)
	 {
		invoiAddh.put( "ADDRESS_LINE3","" + city + " " + state_abbrev + " "+ zip +"  "+country_abbrev+"" );
		countryCity = true;
	 }

   if (countryCity)
	 {
		invoiAddh.put("ADDRESS_LINE4", "");
	 }
	 else
	 {
	 invoiAddh.put( "ADDRESS_LINE4","" + city + " " + state_abbrev + " "+ zip +"  "+country_abbrev+"" );
	 }

   }
 }
 catch ( Exception sqle )
 {
   //sqle.printStackTrace();
 }
 finally
 {
   if ( dbrs != null ){ dbrs.close(); }
 }

 return invoiAddh;
}

	protected String getimageseqnum( boolean  creatbpo )
	{
	  String imgaSeqnum = "";
	  DBResultSet dbrs = null;

	  try
	  {
		String stmt = "";
		if (creatbpo)
		{
		  stmt="select PO_IMAGE_seq.nextval IMGSEQ from dual";
	    }
		else
		{
		  stmt="select PO_IMAGE_seq.nextval IMGSEQ from dual";
	    }
		dbrs=db.doQuery( stmt );
		ResultSet buyerInfoRs=dbrs.getResultSet();

	    while ( buyerInfoRs.next() )
		{
		  imgaSeqnum =buyerInfoRs.getString( "IMGSEQ" ) == null ? "" : buyerInfoRs.getString( "IMGSEQ" ).trim();
		}
	  }
	  catch ( Exception sqle )
	  {
		//sqle.printStackTrace();
	  }
	  finally
	  {
		if ( dbrs != null ){ dbrs.close(); }
	  }

	  return imgaSeqnum;
	}

    protected Hashtable getShipperinfo( String carrier,Hashtable poheaderInfo,ResourceLibrary res ) throws Exception
    {
      Hashtable ShipperInfoh=new Hashtable();
      DBResultSet dbrs=null;
      ResultSet rs=null;

      ShipperInfoh.put( "company_id",poheaderInfo.get( "CARRIER_COMPANY_ID" ).toString() );
      ShipperInfoh.put( "client_acct",poheaderInfo.get( "CARRIER_ACCOUNT" ).toString() );
      ShipperInfoh.put( "add_note","" );
      ShipperInfoh.put( "shipper_name",poheaderInfo.get( "CARRIER_NAME" ).toString());

      //if carrier code in carrier_info is 3rd partyy billing then BILL_TO_LOC_ID should be not null
      try
      {
        String stmt=" select l.* from carrier_info c , customer.location l where c.BILL_TO_LOC_ID = l.location_id and ";
        stmt+=" c.OWNER_COMPANY_ID = l.company_id and c.carrier_code = '" + carrier + "' ";

        dbrs=db.doQuery( stmt );
        rs=dbrs.getResultSet();
        int numberRec=0;

        while ( rs.next() )
        {
          String state_abbrev=rs.getString( "STATE_ABBREV" ) == null ? "" : rs.getString( "STATE_ABBREV" ).trim();
          String address_line_1=rs.getString( "ADDRESS_LINE_1" ) == null ? "" : rs.getString( "ADDRESS_LINE_1" ).trim();
          String address_line_2=rs.getString( "ADDRESS_LINE_2" ) == null ? "" : rs.getString( "ADDRESS_LINE_2" ).trim();
	        String address_line_3=rs.getString( "ADDRESS_LINE_3" ) == null ? "" : rs.getString( "ADDRESS_LINE_3" ).trim();
          String city=rs.getString( "CITY" ) == null ? "" : rs.getString( "CITY" ).trim();
          String zip=rs.getString( "ZIP" ) == null ? "" : rs.getString( "ZIP" ).trim();
          String company_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
              
    if ("Radian".equalsIgnoreCase(company_id))
	  {
		company_id = poheaderInfo.get( "HOME_COMPANY_NAME" ).toString();
	  }
          ShipperInfoh.put( "bill1",""+res.getString("label.poshipthirdparty")+"" );
          ShipperInfoh.put( "company",company_id );
          ShipperInfoh.put( "bill_to_address1",address_line_1 );
          ShipperInfoh.put( "bill_to_address2",address_line_2 );
          ShipperInfoh.put( "bill_to_address3",address_line_3 );
          ShipperInfoh.put( "bill2","" + city + ", " + state_abbrev + " " + zip + "" );
	  //ShipperInfoh.put( "bill2","" + city + ", " + state_abbrev + " " + zip + "\n\nHaas TCM will not pay freight invoices billed to our company" );
          numberRec+=1;
        }

        if ( numberRec == 0 )
        {
          ShipperInfoh.put( "bill1","" );
          ShipperInfoh.put( "company","" );
          ShipperInfoh.put( "bill_to_address1","" );
          ShipperInfoh.put( "bill_to_address2","" );
          ShipperInfoh.put( "bill_to_address3","" );
          ShipperInfoh.put( "bill2","" );
          ShipperInfoh.put( "SHIPPERCOMMENTS",""+res.getString("label.poseelastpage")+"" );
        }
        else
        {
          ShipperInfoh.put( "SHIPPERCOMMENTS",""+res.getString("label.poseelastpage3rd")+"" );
        }

        //rs.close();
      }
      catch ( Exception sqle )
      {
        //System.out.println( "Error In  SQL ShipperInfoNull Query2 :" );
        //sqle.printStackTrace();
        throw sqle;
      }
      finally
      {
        if ( dbrs != null ){ dbrs.close(); }
      }
      return ShipperInfoh;
}

    protected Vector getMSDSRevisionDate( String itemId,ResourceLibrary res )
    {
      Vector Msdsdate=new Vector();
      boolean itemIdDone=false;
      DBResultSet dbrs = null;
      ResultSet rs = null;

      String stmt;
      try
      {
        int numberRec=0;
        stmt="select ITEM_ID,DESCRIPTION,MANUFACTURER,PACKAGING,REVISION_DATE,ON_LINE from item_component_view where item_id =" + itemId + "";

        dbrs=db.doQuery( stmt );
        rs=dbrs.getResultSet();
        Hashtable result=new Hashtable();
        result=new Hashtable();

        while ( rs.next() )
        {
          result=new Hashtable();
          String attach=rs.getString( "REVISION_DATE" ) == null ? "" : rs.getString( "REVISION_DATE" ).trim();
          result.put( "REVISION_DATE",attach );
          Msdsdate.add( numberRec,result );

          if ( msdsmessage.indexOf( itemId ) > 0 )
          {
            itemIdDone=true;
          }
          else
          {
            if ( numberRec == 0 )
            {
              msdsmessage+="*********************************************************************\n";
              msdsmessage+= res.format( "label.pomsdsrevdate",new Object[]{" "+itemId+""});
            }
            if ( numberRec > 1 )
            {
              msdsmessage=res.getString("label.or");
            }
            msdsmessage+=" "+attach;
          }

          numberRec+=1;
        }

        //if ( !itemIdDone )
        {
          if ( numberRec > 0 )
          {
            msdsmessage+="\n"+res.format( "label.pofaxmsds",new Object[]{itemId})+"\n";
          }
        }

        //rs.close();
      }
      catch ( Exception sqle )
      {
        //sqle.printStackTrace();
      }
      finally
      {
        if ( dbrs != null ){ dbrs.close(); }
      }
      return Msdsdate;
    }
    
    public StringBuffer buildPage( String PurchaseOrder,String bpoNumber,String buyername,Vector addchargeslist,
            TcmISDBModel dbObj,String headerNote,String personnelID,Locale printLocale,String localeCode )  throws Exception {
    	return buildPage(PurchaseOrder,bpoNumber,buyername,addchargeslist,dbObj,headerNote,personnelID,printLocale,localeCode,"","");
    }

    public StringBuffer buildPage( String PurchaseOrder,String bpoNumber,String buyername,Vector addchargeslist,
                                   TcmISDBModel dbObj,String headerNote,String personnelID,Locale printLocale,String localeCode,String totalCost,String totalCostCurrencyId )  throws Exception
    {
      ResourceLibrary res = null;
      res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", printLocale);
      
      erw=new ACJEngine();
      //erw.setDebugMode( false );
      //erw.DEBUG = false;
      //erw.setTargetOutputDevice( ACJConstants.PDF );

      eClient=new ACJOutputProcessor();

      StringBuffer MsgBuild=new StringBuffer();
      String order_total="";
      Connection connection;
      String branch_plant="";
      String shipper_name="";
      //String shelf_life="";
      String Payment_terms="";
      String template="";
      String signature="";
      boolean creatingblanketpo=false;
      String whichorderstg = "";

      erw.setX11GfxAvailibility( false );

      String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
      eClient.setPathForFontMapFile(fontmappath);

      Hashtable AddressInfo=new Hashtable();
      Vector JDEpoInfo=new Vector();
      Vector MoreComments=new Vector();
      Hashtable JDEpoInfoH=new Hashtable();
      Hashtable MSDSrevdate=new Hashtable();
      Hashtable PoInfo=new Hashtable();
      Hashtable ShipperInfo=new Hashtable();
      Hashtable PoData=new Hashtable();
      Hashtable BuyerInfo=new Hashtable();
      Hashtable Msds_Datess=new Hashtable();
      Vector Msds_Date=new Vector();
      Vector Po_Date=new Vector();
      Hashtable invocieAddInfo=new Hashtable();

      try
      {
        connection=dbObj.getConnection();
      }
      catch ( Exception sqle )
      {
        //sqle.printStackTrace();
        MsgBuild.append( PrintError( ""+res.getString("label.sqlexception")+": " ) );
        return MsgBuild;
      }

      try
      {
        AddressInfo=getAddressInfo( PurchaseOrder,bpoNumber );
        if ( AddressInfo.size() < 1 )
        {
          MsgBuild.append( PrintError( ""+res.getString("label.norecordsfoundforpo")+"" ) );
          return MsgBuild;
        }
      }
      catch ( Exception ee )
      {
        MsgBuild.append( PrintError( ""+res.getString("label.erroraddressquery")+": ") );
        return MsgBuild;
      }

      branch_plant=AddressInfo.get( "branch_plant" ).toString();
      String shiptoloc=AddressInfo.get( "SHIP_TO_LOCATION_ID" ).toString();
      String bpoenddate=AddressInfo.get( "BO_END_DATE" ).toString();
      String shiptoloccompanyId=AddressInfo.get( "SHIP_TO_COMPANY_ID" ).toString();
      String invengrp=AddressInfo.get( "INVENTORY_GROUP" ).toString();
      String posupplier=AddressInfo.get( "SUPPLIER" ).toString();
      String fobterms=AddressInfo.get( "FREIGHT_ON_BOARD" ).toString();
      String homecompany  = AddressInfo.get( "OPS_ENTITY_ALIAS" ).toString();
      String logo = AddressInfo.get( "PO_IMAGE_URL" ).toString();
      //String shortcompayname = AddressInfo.get( "OPERATING_ENTITY_SHORT_NAME" ).toString();
      String shortcompayname = "Haas";
      String billlocationid = AddressInfo.get( "BILL_TO_LOCATION_ID" ).toString();
	  String critical = AddressInfo.get( "CRITICAL" ).toString();
      String opsEntityId = AddressInfo.get( "OPS_ENTITY_ID" ).toString();

	  if ( ! ( PurchaseOrder.length() > 0 && !"N".equalsIgnoreCase( PurchaseOrder ) ) )
	  {
		creatingblanketpo=true;
		if ("S".equalsIgnoreCase(critical))
		{
		  whichorderstg=""+res.getString("label.critical")+" "+res.getString("label.aog")+" "+res.getString("label.blanketorder")+"";
		}
        if ("L".equalsIgnoreCase(critical))
		{
		  whichorderstg=""+res.getString("label.critical")+" "+res.getString("label.ld")+" "+res.getString("label.blanketorder")+"";
		}
        else
		{
		  whichorderstg=""+res.getString("label.blanketorder")+"";
	    }
	  }
	  else
	  {
		if ("S".equalsIgnoreCase(critical))
		{
		  whichorderstg=""+res.getString("label.critical")+" "+res.getString("label.aog")+" "+res.getString("label.purchaseorder")+"";
		}
        if ("L".equalsIgnoreCase(critical))
		{
		  whichorderstg=""+res.getString("label.critical")+" "+res.getString("label.ld")+" "+res.getString("label.purchaseorder")+"";
		}
        else
		{
		  whichorderstg=""+res.getString("label.purchaseorder")+"";
	    }
	  }

      try
      {
        PoInfo=getPoInfo( PurchaseOrder,bpoNumber,connection,addchargeslist,branch_plant,shiptoloc,shiptoloccompanyId,bpoenddate,invengrp,headerNote,res,printLocale,opsEntityId,totalCost,totalCostCurrencyId );
        order_total=PoInfo.get( "TOTAL_PRICE" ).toString();
        if ( PoInfo.get( "NOOFLINES" ).toString().equalsIgnoreCase( "0" ) )
        {
          MsgBuild.append( PrintError( res.getString("label.noopenpolines")) );
          return MsgBuild;
        }
      }
      catch ( Exception ee1 )
      {
        ee1.printStackTrace();
        MsgBuild.append( PrintError( res.getString("label.errorinfoquery")+":" ) );
        return MsgBuild;
      }

      Po_Date= ( Vector ) PoInfo.get( "LINES" );
      Hashtable PoDataTest= ( Hashtable ) Po_Date.elementAt( 0 );

      try
      {
        JDEpoInfo=getPoLineNotes( Po_Date, opsEntityId, res );

        if ( JDEpoInfo.size() < 1 )
        {
          MsgBuild.append( PrintError( res.getString("label.errorformatcomments") ) );
          return MsgBuild;
        }
      }
      catch ( Exception ee2 )
      {
        MsgBuild.append( PrintError( res.getString("label.errorjdequery")+":" ) );
        return MsgBuild;
      }

      MoreComments=getmoreComments();
      String buyerId=AddressInfo.get( "BUYER" ).toString();
      JDEpoInfoH= ( Hashtable ) JDEpoInfo.elementAt( 0 );
      BuyerInfo=getBuyerInfo( buyerId,invengrp,res );
      /*shelf_life=PoDataTest.get( "REMAINING_SHELF_LIFE_PERCENT" ).toString();
      if ( shelf_life.trim().length() > 0 )
      {
        shelf_life=""+res.format( "label.shelfliferequired",shelf_life)+"";
      }*/
      Payment_terms=AddressInfo.get( "PAYMENT_TERMS" ).toString();
      String carrier=AddressInfo.get( "CARRIER" ).toString();

      if ( ! ( carrier.trim().length() > 0 || creatingblanketpo ) )
      {
        MsgBuild.append( PrintError(res.getString("label.noshipperforpo")) );
        return MsgBuild;
      }

      try
      {
        ShipperInfo=getShipperinfo( carrier,AddressInfo,res );
      }
      catch ( Exception ee )
      {
        MsgBuild.append( PrintError( res.getString("label.errorshipquery")+":" ) );
        return MsgBuild;
      }

      shipper_name=ShipperInfo.get( "shipper_name" ).toString();


	try
	{
	  invocieAddInfo=getInvoiceaddress( billlocationid );
	}
	catch ( Exception ee )
	{
	  MsgBuild.append( PrintError( res.getString("label.errorinvoicequery")+":"  ) );
	  return MsgBuild;
	}

      try
      {
        signature=radian.web.tcmisResourceLoader.getbuyerimageloc() + "" + BuyerInfo.get( "byrsig" ).toString() + "_.gif";
      }
      catch ( Exception ee2 )
      {
        MsgBuild.append( PrintError( res.getString("label.nobuyerinfo") ) );
        return MsgBuild;
      }

      Vector vendorQualData =  vendorQualificationStmt(invengrp);

      String customerpoSw=AddressInfo.get( "CUSTOMER_PO" ).toString();
      String Msds_dates="";
      if ( customerpoSw.trim().length() > 0 && !invengrp.equalsIgnoreCase("Miami Distribution") && !"HAASTCMDEU".equalsIgnoreCase(opsEntityId) && !"HAASSCMDEU".equalsIgnoreCase(opsEntityId))
      {
        Msds_dates+=res.format( "label.poshippingpapers",new Object[]{" [" + customerpoSw + " , " + PurchaseOrder + "]"});
        Msds_dates+="\n";
      }
      else
      {
        Msds_dates+=res.format( "label.poshippingpapers",new Object[]{PurchaseOrder});
        Msds_dates+="\n";
      }

      Msds_dates+="*********************************************************************\n";
      if ("HAASTCMSIN".equalsIgnoreCase(opsEntityId))
      {
        Msds_dates+=res.format( "label.poshowitemid",new Object[]{homecompany.toUpperCase(),""+radianItems+" AND/OR PART NUMBER"});
        Msds_dates+="\n*********************************************************************\n";
      }
      else if ( ! ( branch_plant.equalsIgnoreCase( "2202" ) || branch_plant.equalsIgnoreCase( "2103" ) ) )
      {
        Msds_dates+=res.format( "label.poshowitemid",new Object[]{homecompany.toUpperCase(),radianItems});
        Msds_dates+="\n*********************************************************************\n";
      }
      //Msds_dates+=res.getString("label.poincludemsds");
      Msds_dates+="\n"+msdsmessage;
      
      if ( creatingblanketpo )
      {
        Msds_dates="\n* QUANTITY SHOWN ON BLANKET ORDER IS ESTIMATE ONLY, NOT BILLABLE.\n";
        Msds_dates+="\n* PURCHASE ORDER RELEASES FOR SPECIFIC QUANTITIES AND DELIVERY DATES WILL FOLLOW.\n";
        Msds_dates+="\n* ONLY PURCHASE ORDER RELEASES ARE BILLABLE.\n";
        Msds_dates+="\n* THIS BLANKET ORDER IS VALID THROUGH " + bpoenddate + ".\n";
      }

      String tempPath=radian.web.tcmisResourceLoader.getsavepopath();
      //String logo=radian.web.tcmisResourceLoader.getpologourl();
      String myURL=radian.web.tcmisResourceLoader.getpofinalurl();
      template=radian.web.tcmisResourceLoader.getpotemplatepath();
	  String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	  
      if ( creatingblanketpo )
      {
        template=template + "bpopurchase_order_template.erw";
        Msds_dates+="\n"+res.getString("label.oshaghsreq");
      }
      else
      {
	    template=template + "purchase_order_template";
	    String templateSuffix = "";

        if ("Rome Distribution Italy".equalsIgnoreCase(invengrp))
        {
          if ( COAcount > 0 )
         {
          templateSuffix= "_rome_dist_coa.erw";
         }
         else
         {
          templateSuffix= "_rome_dist.erw";
         }
        }
        else if ("HAASTCMBRA".equalsIgnoreCase(opsEntityId))
        {
           templateSuffix= "_portugese.erw";
    	}
        else if ( ("HAASTCMDEU".equalsIgnoreCase(opsEntityId) || "HAASSCMDEU".equalsIgnoreCase(opsEntityId) )&& localeCode.equalsIgnoreCase("de_DE"))
		{
		 if ( COAcount > 0 )
         {
          templateSuffix += "_german_coa.erw";
         }
         else
         {
          templateSuffix += "_german.erw";
         }
    	}
        else if ( ("HAASTCMDEU".equalsIgnoreCase(opsEntityId) || "HAASSCMDEU".equalsIgnoreCase(opsEntityId) ))
		{
		 if ( COAcount > 0 )
         {
          templateSuffix += "_non_us_coa.erw";
         }
         else
         {
          templateSuffix += "_non_us.erw";
         }
    	 }
         else if ("HAASTCMSIN".equalsIgnoreCase(opsEntityId))
		{
		  if ( COAcount > 0 )
          {
           templateSuffix += "_sig_coa.erw";
          }
          else
          {
           templateSuffix += "_sig.erw";
          }
    	 }
         else if ("HAASTCMPOL".equalsIgnoreCase(opsEntityId) && localeCode.equalsIgnoreCase("pl_PL"))
		 {
		  if ( COAcount > 0 )
          {
           templateSuffix += "_coa_pl_PL.erw";
          }
          else
          {
           templateSuffix += "_pl_PL.erw";
          }
    	 }
         else if ("HAASFRANCE".equalsIgnoreCase(opsEntityId) && localeCode.equalsIgnoreCase("fr_FR"))
		 {
		  if ( COAcount > 0 )
          {
           templateSuffix += "_french_coa.erw";
          }
          else
          {
           templateSuffix += "_french.erw";
          }
    	 }
         else if ("WESCOSCMUK".equalsIgnoreCase(opsEntityId))
		 {
		  if ( COAcount > 0 )
          {
           templateSuffix += "_uk_coa.erw";
          }
          else
          {
           templateSuffix += "_uk.erw";
          }
    	 }
         else if ("HAASTCMIT".equalsIgnoreCase(opsEntityId)
             || "HAASTCMSIN".equalsIgnoreCase(opsEntityId))
		 { /*use italy template for home company id 'HAASTCMIT','HAASTCMSIN'*/
				 templateSuffix= "_italy";
         if ( COAcount > 0 )
         {
          templateSuffix += "_coa.erw";
         }
         else
         {
          templateSuffix += ".erw";
         }
        }
        else if (invengrp.equalsIgnoreCase("Miami Distribution"))
        {
         if ( COAcount > 0 )
         {
          templateSuffix += "_miami_coa.erw";
         }
         else
         {
          templateSuffix += "_miami.erw";
          Msds_dates+="\n"+res.getString("label.oshaghsreq");
         }
        }
        else if (localeCode.equalsIgnoreCase("zh_CN"))
		{
		 if ( COAcount > 0 )
         {
          templateSuffix += "_china_coa.erw";
         }
         else
         {
          templateSuffix += "_china.erw";
         }
    	 }
        else
        {
         if ( COAcount > 0 )
         {
          templateSuffix += "_coa.erw";
         }
         else
         {
          templateSuffix += ".erw";
          Msds_dates+="\n"+res.getString("label.oshaghsreq");
         }
        }
        template=template + templateSuffix;
      }

      try
      {
        reoprtlog.debug("PO template --  "+template);
        erw.readTemplate( template );

      }
      catch ( Exception ex )
      {
        MsgBuild.append( "<P><B>"+res.getString("label.errorMessage")+"!!</B></P>" );
        MsgBuild.append( "<P>"+res.getString("label.errorloadingpo")+"</P>" );
        //ex.printStackTrace();
        return MsgBuild;
      }

      String where02=null;
      where02=" RADIAN_PO = " + PurchaseOrder + " AND TOTAL_PRICE_PAID > 0.1";

      String where04=null;
      where04="radian_po =" + PurchaseOrder;
	  String prnitType = "";
      try
      {
        tm=erw.getTemplateManager();

        buyername=BuyerInfo.get( "buyer_name" ).toString();
        tm.setLabel( "IMA001",logo );
				tm.setLabel( "COMPANY_NAME",homecompany );


        if (!"HAASTCMTUR".equalsIgnoreCase(opsEntityId) &&
                !"HAASSCMEIR".equalsIgnoreCase(opsEntityId) &&
                !"HAASSCMDEU".equalsIgnoreCase(opsEntityId) &&
                !"HAASTCMSIN".equalsIgnoreCase(opsEntityId) &&
                !"HAASTCMIT".equalsIgnoreCase(opsEntityId) &&
                !"HAASSCMFIN".equalsIgnoreCase(opsEntityId) &&
                !"HAASTCMIRL".equalsIgnoreCase(opsEntityId) &&
                !"HAASTCMMAL".equalsIgnoreCase(opsEntityId) &&
                !"HAASTCMAUS".equalsIgnoreCase(opsEntityId) &&
                !"HAASTCMPHL".equalsIgnoreCase(opsEntityId) &&
                !"WESCOTCMUK".equalsIgnoreCase(opsEntityId) &&                
                !"HAASFRANCE".equalsIgnoreCase(opsEntityId) && !"HAASGRPCAN".equalsIgnoreCase(opsEntityId)
                && !"HAASTCMDEU".equalsIgnoreCase(opsEntityId) && !"WESCOSCMUK".equalsIgnoreCase(opsEntityId)
                && !"HAASTCMPOL".equalsIgnoreCase(opsEntityId) && !"HAASTCMCAN".equalsIgnoreCase(opsEntityId))
        {
         tm.setLabel( "SALES_TAX_STMT",""+res.format( "label.ponosalestax",new Object[]{homecompany+""}));
        }
        tm.setLabel( "IMA000",signature );
        tm.setLabel( "LAB037",buyername );
        tm.setLabel( "LAB038",Payment_terms );
        tm.setLabel( "LAB062",BuyerInfo.get( "buyer_phone" ).toString() );
        tm.setLabel( "LBL011",BuyerInfo.get( "buyer_email" ).toString() );
		tm.setLabel( "ITEM_LABEL","" + shortcompayname + " "+res.getString("label.itemid")+"" );
		if ( COAcount > 0 )
		{
		  String coacomments="";
		  coacomments+=""+res.format( "label.pomustinclude1",new Object[]{shortcompayname})+"\n";
		  coacomments+=""+res.getString("label.pomustinclude2")+"\n";
		  coacomments+=""+res.getString("label.pomustinclude3")+"\n";
		  coacomments+=""+res.getString("label.pomustinclude4")+"\n";

		tm.setLabel( "COA_COMMENT","" + coacomments + "" );
        if (localeCode.equalsIgnoreCase("pl_PL"))
        {
          tm.setLabel( "COATESTCOMMENT",""+res.getString("label.coatestingcomments")+"" );                    
          String cocdrawingcomments="";
		  cocdrawingcomments+=""+res.getString("label.coadrawingcomment1")+"\n";
		  cocdrawingcomments+=""+res.getString("label.coadrawingcomment2")+"\n";
		  cocdrawingcomments+=""+res.getString("label.coadrawingcomment3")+"\n";
          cocdrawingcomments+=""+res.getString("label.coadrawingcomment4")+"\n";
          tm.setLabel( "COADRAWINGCOMMENT",""+cocdrawingcomments+"" );                 
        }
        if (localeCode.equalsIgnoreCase("zh_CN"))
        {
          tm.setLabel( "COATESTCOMMENT",""+res.getString("label.coatestingcomments")+"" );                    
          String cocdrawingcomments="";
		  cocdrawingcomments+=""+res.getString("label.coadrawingcomment1")+"\n";
		  cocdrawingcomments+=""+res.getString("label.coadrawingcomment2")+"\n";
		  cocdrawingcomments+=""+res.getString("label.coadrawingcomment3")+"\n";
          cocdrawingcomments+=""+res.getString("label.coadrawingcomment4")+"\n";
          tm.setLabel( "COADRAWINGCOMMENT",""+cocdrawingcomments+"" );                 
        }
      }
	  tm.setLabel( "TERMS_AND_CONDITIONS",AddressInfo.get( "PO_TERMS_AND_CONDITIONS" ).toString() );

	  String shippername=ShipperInfo.get( "shipper_name" ).toString();
	  if ( "Pre Pay and Add".equalsIgnoreCase( shippername ) || "Vendor Truck".equalsIgnoreCase( shippername ) )
	  {
		tm.setLabel( "SHIPPING_NOTE",""+res.getString("label.poshippinginst1")+"" );
		tm.setLabel( "NOTES_LABLE","" );
	  }
	  else
	  {
		String shipping_add_note=res.getString("label.poshippinginst1");
		shipping_add_note+=res.format( "label.powillnotpay",new Object[]{homecompany});
    shipping_add_note+= "\n"+res.getString("label.poshippinginst2")+"";

		tm.setLabel( "SHIPPING_NOTE",shipping_add_note );
        tm.setLabel( "NOTES_LABLE",""+res.getString("label.liabilityselfinsured")+"" );
      }

       if ("HAASTCMPOL".equalsIgnoreCase(opsEntityId) && !localeCode.equalsIgnoreCase("pl_PL"))
       {
          tm.setLabel( "POTANDCSCHANGED","" );  
       }

      if (localeCode.equalsIgnoreCase("pl_PL"))
      {
          tm.setLabel( "PURCHACEORDER",""+res.getString("label.purchaseorder")+"" );
          tm.setLabel( "MATERIALDESCLABEL",""+res.getString("label.materialdesc")+"" );
          tm.setLabel( "MFGPARTNOLABEL",""+res.getString("label.mfgpartno")+"" );
          tm.setLabel( "QTYLABEL",""+res.getString("label.qty")+"" );
          tm.setLabel( "UOMLABEL",""+res.getString("label.uom")+"" );
          tm.setLabel( "UNITCOSTLABEL",""+res.getString("label.unitcost")+"" );
          tm.setLabel( "LINETOTALLABEL",""+res.getString("label.line.total.price")+"" );
          tm.setLabel( "PROMISESHIPDATELABEL",""+res.getString("label.promised.ship.date")+"" );
          tm.setLabel( "ANYCHARGESNOTONTHISPO",""+res.getString("label.anychargesnotonthispo")+"" );
          tm.setLabel( "REQUIREMENTS",""+res.getString("label.generalrequirements")+":" );
          tm.setLabel( "GENERAL",""+res.getString("label.general")+":" );
          String nonconformancecomment1="";
		  nonconformancecomment1+=""+res.getString("label.nonconformancecomment1")+"\n";
		  nonconformancecomment1+=""+res.getString("label.nonconformancecomment2")+"\n";
		  nonconformancecomment1+=""+res.getString("label.nonconformancecomment3")+"\n";
		  tm.setLabel( "NONCONFIRMINGCOMMENT",""+nonconformancecomment1+"" );
          tm.setLabel( "ACCOUNTNUMBER",""+res.getString("label.accountnumber")+":" );
          tm.setLabel( "BILLINGINFORMATION",""+res.getString("label.billinginformation")+":" );
          tm.setLabel( "SENDINVOICESCOMMENT",""+res.getString("label.invoicecomment")+"" );
          tm.setLabel( "TERMSANDCONDITIONSLABEL",""+res.getString("label.termsandconditions")+"" );
          tm.setLabel( "INVOICE_COMMENTS1",""+res.getString("label.invoicecomment1")+"" );
          tm.setLabel( "APPROVEDSHIPPER",""+res.getString("label.approvedshipper")+":" );
          tm.setLabel( "SHIPPINGINFORMATION",""+res.getString("label.shippinginformation")+":" );
          tm.setLabel( "BUYERSIGNATURELABEL",""+res.getString("label.buyersignature")+"" );
          tm.setLabel( "BUYERSIGNATUREDATELABEL",""+res.getString("label.date")+"" );
          tm.setLabel( "BUYERPHONELABEL",""+res.getString("label.buyerphone")+":" );
          tm.setLabel( "BUYEREMAILLABEL",""+res.getString("label.buyeremail")+":" );
          tm.setLabel( "SUPPLIERCONTACTLABEL",""+res.getString("label.suppliercontact")+":" );
          tm.setLabel( "SUPPLIERCONTACTPHONELABEL",""+res.getString("label.phone")+":" );
          tm.setLabel( "SUPPLIERCONTACTEMAILLABEL",""+res.getString("label.email")+":" );
          tm.setLabel( "PURCHANCEOHNG",""+res.getString("label.purchaseorder")+"" );
          tm.setLabel( "POFOOTERLINE1",""+res.getString("label.pofooter1")+"" );
          tm.setLabel( "POFOOTERLINE2",""+res.getString("label.pofooter2")+"" );
          tm.setLabel( "POFOOTERLINE3",""+res.getString("label.pofooter3")+"" );
          tm.setLabel( "LAB000",""+res.getString("label.supplier")+":" );
          tm.setLabel( "LAB001",""+res.getString("label.paymentterms")+":" );
          tm.setLabel( "LAB003",""+res.getString("label.shipto")+":" );
          if ( COAcount > 0 ){tm.setLabel( "LBL013",""+res.getString("label.coarequired")+":" );}
          tm.setLabel( "LAB016",""+res.getString("label.releasefromblanketorder")+":" );
          tm.setLabel( "LAB046",""+res.getString("label.ordertakenby")+":" );
      }
          
      if (localeCode.equalsIgnoreCase("zh_CN") || localeCode.equalsIgnoreCase("fr_FR"))
      {
          tm.setLabel( "PURCHACEORDER",""+res.getString("label.purchaseorder")+":" );
          tm.setLabel( "LAB016",""+res.getString("label.releasefromblanketorder")+":" );
          tm.setLabel( "LAB007",""+res.getString("label.date")+":" );
          tm.setLabel( "LAB000",""+res.getString("label.supplier")+":" );
          tm.setLabel( "LAB003",""+res.getString("label.shipto")+":" );
          tm.setLabel( "LAB004",""+res.getString("label.buyername")+":" );
          tm.setLabel( "LAB046",""+res.getString("label.ordertakenby")+":" );
          tm.setLabel( "LBL004",""+res.getString("label.faxphone")+":" );
          tm.setLabel( "LAB024",""+res.getString("label.approvedshipper")+" / "+ res.getString("label.account")+":" );
          tm.setLabel( "LAB011",""+res.getString("label.ordertotal")+":" );
          tm.setLabel( "LAB001",""+res.getString("label.paymentterms")+":" );
          if ( COAcount > 0 ){tm.setLabel( "LBL012",""+res.getString("label.invoicecomment1")+":" );}
          else{tm.setLabel( "LBL012",""+res.getString("label.tradeTerms")+":"+"("+res.getString("label.tradeTerms")+")");}
          tm.setLabel( "POTANDCSCHANGED","");
          if ( COAcount > 0 ){tm.setLabel( "LBL021",""+res.getString("label.suppliercontact")+":" );}
          else{tm.setLabel( "LBL021",""+res.getString("label.specnote")+"" );}
          tm.setLabel( "LAB008",""+res.getString("label.materialdesc")+"" );
          tm.setLabel( "LINENUM",""+res.getString("label.mfgpartno")+"" );
          tm.setLabel( "LAB009",""+res.getString("label.qty")+"" );
          tm.setLabel( "LAB010",""+res.getString("label.uom")+"" );
          tm.setLabel( "LAB012",""+res.getString("label.unitcost")+"" );
          tm.setLabel( "LAB013",""+res.getString("label.line.total.price")+"" );
          tm.setLabel( "LAB014",""+res.getString("label.promised.ship.date")+"" );
          tm.setLabel( "LAB017",""+res.getString("label.anychargesnotonthispo")+"");
          tm.setLabel( "REQUIREMENTS",""+res.getString("label.generalrequirements")+":" );
          tm.setLabel( "GENERAL",""+res.getString("label.general")+":" );
          tm.setLabel( "LAB019",res.getString("label.nonconformancecomment1")+"\n\n"+res.getString("label.nonconformancecomment2")+"\n\n"+res.getString("label.nonconformancecomment4")+"\n\n"+res.getString("label.nonconformancecomment3"));
          tm.setLabel( "LAB023",""+res.getString("label.shippinginformation")+":" );
          if ( COAcount > 0 ){tm.setLabel( "LBL013",""+res.getString("label.coarequired")+":" );}
          else{tm.setLabel( "LBL013",""+res.getString("label.approvedshipper")+":" );}
          if ( COAcount > 0 ){tm.setLabel( "LBL018",""+res.getString("label.accountnumber")+":" );}
          else{tm.setLabel( "LBL015",""+res.getString("label.accountnumber")+":" );}
          tm.setLabel( "LAB028",""+res.getString("label.invoicecomment")+":" );
          if ( COAcount > 0 ){tm.setLabel( "LBL016",""+res.getString("label.approvedshipper")+":" );}
          else{tm.setLabel( "LBL016",""+res.getString("label.invoicecomment1")+":" );}
          tm.setLabel( "LAB027",""+res.getString("label.billinginformation")+":" );
          if ( COAcount > 0 ){tm.setLabel( "LBL020TC",""+res.getString("label.termsandconditions")+"" );}
          else{tm.setLabel( "LBL023TC",""+res.getString("label.termsandconditions")+"" );}
          tm.setLabel( "LAB035",""+res.getString("label.buyersignature")+"" );
          tm.setLabel( "LAB036",""+res.getString("label.date")+"" );
          tm.setLabel( "LAB039",""+res.getString("label.buyerphone")+":" );
          tm.setLabel( "LBL010",""+res.getString("label.buyeremail")+":" );
          if ( COAcount > 0 ){tm.setLabel( "LBL020",""+res.getString("label.specnote")+"" );}
          else{tm.setLabel( "LBL020",""+res.getString("label.suppliercontact")+":" );}
          tm.setLabel( "LBL022",""+res.getString("label.phone")+":" );
          if ( COAcount > 0 ){tm.setLabel( "LBL017",""+res.getString("label.tradeTerms")+":"+"("+res.getString("label.tradeTerms")+")");}
          else{tm.setLabel( "LBL017",""+res.getString("label.email")+":" );}
          tm.setLabel( "PURCHANCEOHNG",""+res.getString("label.purchaseorder")+"" );
          tm.setLabel( "LAB040",""+res.getString("label.pofooter1")+"" );
          tm.setLabel( "LBL006",""+res.getString("label.pofooter2")+"" );
          if ( COAcount > 0 ){tm.setLabel( "LBL027",""+res.getString("label.email")+":" );
          tm.setLabel( "LBL019",""+res.getString("label.pofooter3")+"" );}
          else{tm.setLabel( "LBL014",""+res.getString("label.pofooter3")+"" );}
      }

      if ( ("HAASTCMDEU".equalsIgnoreCase(opsEntityId) || "HAASSCMDEU".equalsIgnoreCase(opsEntityId) )&& localeCode.equalsIgnoreCase("de_DE"))
      {
           if ("GFT Kechnec".equalsIgnoreCase(shiptoloc))
           {
                 tm.setLabel( "GERMANACCOUNTFOOTER","Steuernummer:  011 29127387                               \n" +
                         "Umsatzsteuer-Identifikationsnummer (USt-IdNr.): SK4020269462");
           }
      }
      else if ( ("HAASTCMDEU".equalsIgnoreCase(opsEntityId) || "HAASSCMDEU".equalsIgnoreCase(opsEntityId) ))
      {
           if ("GFT Kechnec".equalsIgnoreCase(shiptoloc))
           {
                 tm.setLabel( "GERMANACCOUNTFOOTER","Company Registration Number:  011 29127387                               \n" +
                         "VAT Registration Number: SK4020269462");
           }
      }
          
      tm.setLabel( "LAB006",""+shippername+" / "+ShipperInfo.get( "client_acct" ).toString()+"" );
	  tm.setLabel( "APROVEDSHIPER",shippername);
	  tm.setLabel( "APPROVEDACCOUNT",ShipperInfo.get( "client_acct" ).toString());
	  tm.setLabel( "SHIPPERCOMMENTS",ShipperInfo.get( "SHIPPERCOMMENTS" ).toString());
	  tm.setLabel( "LAB047",AddressInfo.get( "SUPPLIER_CONTACT_NAME" ).toString() );
	  tm.setLabel( "LBL005",""+AddressInfo.get( "SUPPLIER_CONTACT_FAX" ).toString()+" / "+AddressInfo.get( "SUPPLIER_CONTACT_PHONE" ).toString()+"" );

	  tm.setLabel( "SUPPLIER_CONTACT_NAME",AddressInfo.get( "SUPPLIER_CONTACT_NAME" ).toString() );
		tm.setLabel( "SUPPLIER_CONTACT_PHONE",AddressInfo.get( "SUPPLIER_CONTACT_PHONE" ).toString() );
		tm.setLabel( "SUPPLIER_CONTACT_EMAIL",AddressInfo.get( "EMAIL" ).toString() );

	  if ( "SWA".equalsIgnoreCase( shiptoloccompanyId ) )
	  {
		tm.setLabel( "LAB049",""+res.format( "label.poswainsrt",new Object[]{ShipperInfo.get( "add_note" ).toString()+"" }));
	  }
	  else
	  {
		tm.setLabel( "LAB049",ShipperInfo.get( "add_note" ).toString() );
	  }

      if ("BA".equalsIgnoreCase(invengrp))
      {
        tm.setLabel( "LAB051","B " + PurchaseOrder );
      }
      else
      {
       tm.setLabel( "LAB051",PurchaseOrder );
      }
      Hashtable poLineHash= ( Hashtable ) ((Vector)PoInfo.get( "LINES" )).elementAt( 0 );
      tm.setLabel( "DATE_CONFIRMED", (String) poLineHash.get("DATE_CONFIRMED") );
      
      if ( PurchaseOrder.length() > 0 && !"N".equalsIgnoreCase( PurchaseOrder ) )
	  {
		tm.setLabel( "PURCAHSE_ORDER_NO",PurchaseOrder );
		tm.setLabel( "EXPDNAME",BuyerInfo.get( "expediter_name" ).toString() );
		tm.setLabel( "EXPDEMAIL",BuyerInfo.get( "expediter_email" ).toString() );

		String invoicecomments = res.format("label.poinvoicecomments",new Object[]{homecompany});
		/*invoicecomments +="Invoices sent elsewhere may result in delayed payment.\n";
		invoicecomments +="Shipment sent driver collect will be refused.\n";
		invoicecomments +=homecompany + " will not pay freight invoices billed to our company unless noted on purchase order.\n";*/

		tm.setLabel( "INVOICE_COMMENTS",""+invoicecomments+"" );
        if (!"Rome Distribution Italy".equalsIgnoreCase(invengrp))
        {
            tm.setLabel( "INVOICE_ADDRESS1",""+homecompany+"" );
            tm.setLabel( "INVOICE_ADDRESS2",invocieAddInfo.get( "ADDRESS_LINE1" ).toString() );
            tm.setLabel( "INVOICE_ADDRESS3",invocieAddInfo.get( "ADDRESS_LINE2" ).toString() );
            tm.setLabel( "INVOICE_ADDRESS4",invocieAddInfo.get( "ADDRESS_LINE3" ).toString() );
            tm.setLabel( "INVOICE_ADDRESS5",invocieAddInfo.get( "ADDRESS_LINE4" ).toString() );
         }
      }
	  else
	  {
		tm.setLabel( "PURCAHSE_ORDER_NO",bpoNumber );
	  }

	 if ("HAASTCMBRA".equalsIgnoreCase(opsEntityId))
	 {
		tm.setLabel( "SUPPLIERCNPJ",AddressInfo.get( "FEDERAL_TAX_ID" ).toString() );
		tm.setLabel( "SUPPLIERIE",AddressInfo.get( "STATE_TAX_ID" ).toString() );
		tm.setLabel( "HAASCNPJ",AddressInfo.get( "HOME_COMPANY_TAX_ID" ).toString() );
		tm.setLabel( "HAASIE",AddressInfo.get( "INVENTORY_GROUP_STATE_TAX_ID" ).toString() );
	 }
        
   /*populating VATTAXID for home company id 'HAASTCMIT','HAASTCMSIN','WESCOTCMUK'*/
   if ("HAASTCMIT".equalsIgnoreCase(opsEntityId)
       ||"HAASTCMSIN".equalsIgnoreCase(opsEntityId)
       || "WESCOTCMUK".equalsIgnoreCase(opsEntityId)
       || "HAASTCMDEU".equalsIgnoreCase(opsEntityId)
       || "WESCOSCMUK".equalsIgnoreCase(opsEntityId))
	 {
		tm.setLabel( "VATTAXID",AddressInfo.get( "OPERATIONAL_TAX_ID" ).toString() );
	 }

      //if (invengrp.equalsIgnoreCase("Miami Distribution"))
      {
        tm.setLabel( "LAB052",AddressInfo.get( "SUPPLIER_DISPLAY_ADDRESS" ).toString() );
      }
      /*else
      {
	  tm.setLabel( "LAB052",AddressInfo.get( "vendor_name" ).toString() );
	  tm.setLabel( "LAB053",AddressInfo.get( "vendor_address1" ).toString() );
	  tm.setLabel( "LAB063",AddressInfo.get( "vendor_address2" ).toString() );
	  tm.setLabel( "LAB064",AddressInfo.get( "vendor_address3" ).toString() );
	  tm.setLabel( "LAB054",AddressInfo.get( "vendor_city" ).toString()+" "+AddressInfo.get( "vendor_state" ).toString() +" "+AddressInfo.get( "vendor_zipcode" ).toString());
      }*/
      //tm.setLabel( "LAB055",AddressInfo.get( "vendor_state" ).toString() );
	  //tm.setLabel( "LAB056",AddressInfo.get( "vendor_zipcode" ).toString() );
	  tm.setLabel( "LAB026",AddressInfo.get( "ORIGINAL_ORDER_NUM" ).toString() );
    //For national welders and airgas showing the shiptolocationid on the printed PO

      //if (invengrp.equalsIgnoreCase("Miami Distribution"))
      {
        if (posupplier.equalsIgnoreCase("10022038") || posupplier.equalsIgnoreCase("10022037"))
        {
             tm.setLabel( "LAB057",AddressInfo.get( "SHIP_TO_ADDRESS_CODE" ).toString()+"\n"+AddressInfo.get( "SHIP_TO_DISPLAY_ADDRESS" ).toString() );
        }
        else
        {
            tm.setLabel( "LAB057",AddressInfo.get( "SHIP_TO_DISPLAY_ADDRESS" ).toString() );
        }
      }
      /*else
      {
        if (posupplier.equalsIgnoreCase("10022038") || posupplier.equalsIgnoreCase("10022037"))
        {
         tm.setLabel( "LAB057",AddressInfo.get( "SHIP_TO_ADDRESS_CODE" ).toString() );
        }
        else
        {
         tm.setLabel( "LAB057",AddressInfo.get( "shipto_company" ).toString() );
        }
        tm.setLabel( "LAB058",AddressInfo.get( "shipto_address1" ).toString() );
        tm.setLabel( "LAB065",AddressInfo.get( "shipto_address2" ).toString() );
  	    tm.setLabel( "LAB066",AddressInfo.get( "shipto_address3" ).toString() );
	    tm.setLabel( "LAB059",AddressInfo.get( "shipto_city" ).toString() + " "+AddressInfo.get( "shipto_state" ).toString()+ " "+ AddressInfo.get( "shipto_zip" ).toString() );
      }*/
      //tm.setLabel( "LAB060",AddressInfo.get( "shipto_state" ).toString() );
	  //tm.setLabel( "LAB061",AddressInfo.get( "shipto_zip" ).toString() );
	  tm.setLabel( "FORSUM","" + order_total + " " + defaultCurrencyId + "" );
	  tm.setLabel( "LBL007",BuyerInfo.get( "buyer_fax" ).toString() );
	  tm.setLabel( "MSDS_DATE_TEST",Msds_dates );

	  if ( "0".equalsIgnoreCase( headerNote ) )
	  {
		tm.setLabel( "LBL009","*** "+whichorderstg+" ***" );
		prnitType = "Draft";
	  }
	  else if ( "1".equalsIgnoreCase( headerNote ) )
	  {
		tm.setLabel( "LBL009","***  "+res.getString("label.new")+" "+whichorderstg+" ***" );
		prnitType = "New Order";
	  }
    else if ( "U".equalsIgnoreCase( headerNote ) )
	  {
		tm.setLabel( "LBL009","***  "+res.getString("label.urgent")+" "+whichorderstg+" ***" );
		prnitType = "Urgent Order";
	  }
    else if ( "2".equalsIgnoreCase( headerNote ) )
	  {
		tm.setLabel( "LBL009","***  "+res.getString("label.confirming")+" "+whichorderstg+" ***" );
		tm.setLabel( "LBL008",""+res.format( "label.poorderplaced",new Object[]{AddressInfo.get( "SUPPLIER_CONTACT_NAME" ).toString()+""}));
		prnitType = "Confirming Order";
	  }
	  else if ( "3".equalsIgnoreCase( headerNote ) )
	  {
		tm.setLabel( "LBL009","***  "+res.getString("label.amended")+" "+whichorderstg+" ***" );
		tm.setLabel( "LBL008",""+res.getString("label.pochangesmarked")+"" );
		prnitType = "Amended Order";
	  }
	  else if ( "4".equalsIgnoreCase( headerNote ) )
	  {
		tm.setLabel( "LBL009","***  "+res.getString("label.canceled")+" "+whichorderstg+" ***" );
		prnitType = "Canceled Order";
	  }
	  else
	  {
		tm.setLabel( "LBL009","" );
	  }

	  tm.setLabel( "FOBTERMS",""+fobterms+"" );
	  if ( "EXW".equalsIgnoreCase( fobterms ) )
	  {
		tm.setLabel( "FOBTERMSDESC",""+res.getString("label.exworks")+"" );
	  }
	  else if ( "CIP".equalsIgnoreCase( fobterms ) )
	  {
		tm.setLabel( "FOBTERMSDESC","Carriage and Insurance Paid To" );
	  }
	  else
	  {
		tm.setLabel( "FOBTERMSDESC",""+fobterms+"" );
	  }

	}
	catch ( Exception ex )
	{
	  MsgBuild.append( "<P><B>"+res.getString("label.errorMessage")+"</B></P>" );
	  MsgBuild.append( "<P>"+res.getString("label.errormodifypotemplate")+"</P>" );
	  ex.printStackTrace();
	  return MsgBuild;
	}

	try
	{
	  if ( branch_plant.equalsIgnoreCase( "2202" ) && "Berry Logistics".equalsIgnoreCase( shipper_name ) )
	  {
		tm.setLabel( "LAB033","Berry Logistics Phone# 877-737-1600" );
	  }

	  tm.setLabel( "LAB033",ShipperInfo.get( "bill1" ).toString() );
	  tm.setLabel( "LAB042",ShipperInfo.get( "company" ).toString() );
	  tm.setLabel( "LAB043",ShipperInfo.get( "bill_to_address1" ).toString() );
	  tm.setLabel( "LAB044",ShipperInfo.get( "bill_to_address2" ).toString() );
	  tm.setLabel( "LAB045",ShipperInfo.get( "bill_to_address3" ).toString() );
	  tm.setLabel( "LAB034",ShipperInfo.get( "bill2" ).toString() );
	}
	catch ( Exception ex )
	{
	  MsgBuild.append( "<P><B>Error!!</B></P>" );
	  MsgBuild.append( "<P>During Modify Template</P>" );
	  ex.printStackTrace();
	  return MsgBuild;
	}

	AppDataHandler ds;

	try
	{
	  ds=new AppDataHandler();
	  RegisterTable rt[]=getData( Po_Date );
	  for ( int i=0; i < rt.length; i++ )
	  {
		ds.RegisterTable( rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere() );
	  }

	  //canned info
	  if ( Flow_Data.size() == 0 )
	  {
		Hashtable SpecData=new Hashtable();
		SpecData.put( "FLOW_DOWN_DESC"," " );
		SpecData.put( "CONTENT"," " );
		SpecData.put( "LINE_ITEM"," " );
		Flow_Data.addElement( SpecData );
	  }
	  RegisterTable rt2[]=getFlowData( Flow_Data );
	  for ( int i=0; i < rt2.length; i++ )
	  {
		ds.RegisterTable( rt2[i].getData(),rt2[i].getName(),rt2[i].getMethods(),rt2[i].getWhere() );
	  }

	  //buyer note
	  RegisterTable rt1[]=getCommentData( JDEpoInfo );
	  for ( int i=0; i < rt1.length; i++ )
	  {
		ds.RegisterTable( rt1[i].getData(),rt1[i].getName(),rt1[i].getMethods(),rt1[i].getWhere() );
	  }

	  //more note
	  RegisterTable rt3[]=getMoreCommentsData( MoreComments );
	  for ( int i=0; i < rt3.length; i++ )
	  {
		ds.RegisterTable( rt3[i].getData(),rt3[i].getName(),rt3[i].getMethods(),rt3[i].getWhere() );
	  }

       //vendorQualData
      RegisterTable rt4[]=getVendorQualifData( vendorQualData );
	  for ( int i=0; i < rt4.length; i++ )
	  {
		ds.RegisterTable( rt4[i].getData(),rt4[i].getName(),rt4[i].getMethods(),rt4[i].getWhere() );
	  }

      erw.setDataSource( ds );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}

	try
	{
    eClient.setReportData( erw.generateReport() );
    /*IViewerInterface ivi=erw.generateReport();
	  if ( !eClient.setReportData( ivi,null ) )
		 {
				 MsgBuild.append( "<P><B>Error!!</B></P>" );
				 MsgBuild.append( "<P>During Generating Report</P>" );
				 return MsgBuild;
		 }*/
		//System.exit( 0 );
	}
	catch ( Exception ex )
	{
	  ex.printStackTrace();
	  MsgBuild.append( "<P><B>Error!!</B></P>" );
	  MsgBuild.append( "<P>During Generating Report</P>" );
	  return MsgBuild;
	}

	/*Random rand = new Random();
		   int tmpReq = rand.nextInt();
		   Integer tmpReqNum = new Integer(tmpReq);*/

	String imageSeq=getimageseqnum( creatingblanketpo );
	String filename11="";
	if ( creatingblanketpo )
	{
	  filename11=bpoNumber;
	}
	else
	{
	  filename11=PurchaseOrder;
	}

	try
	{
		
    //erw.setCacheOption(true, "po_" + filename11 + "_" + imageSeq + ".joi");
    eClient.setPDFProperty( "FileName",""+tempPath + "po_" + filename11 + "_" + imageSeq + ".pdf" );
    eClient.generatePDF();
    
    //eClient.generatePDF( tempPath + "po_" + filename11 + "_" + imageSeq + ".pdf",null );
	}
	catch ( Exception e )
	{
	  MsgBuild.append( "<P><B>Error!!</B></P>" );
	  MsgBuild.append( "<P>During Generating PDF Report</P>" );
	  e.printStackTrace();
	  return MsgBuild;
	}

	String finalurl=wwwHome + myURL + "po_" + filename11 + "_" + imageSeq + ".pdf";
	MsgBuild.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0;url=" + finalurl + "\"></HEAD>" );
	MsgBuild.append( "<BODY></BODY></HTML>" );

	try
	{
	  String postmt = "";
	  if (creatingblanketpo)
	  {
		postmt="insert into BPO_IMAGE (IMAGE_ID, BPO, DATE_PRINTED, FILE_URL, SUPPLIER, PRINTED_BY, PRINT_TYPE) values (" + imageSeq + "," + bpoNumber + ",sysdate,'" + finalurl + "','" + posupplier + "',"+personnelID+",'"+prnitType+"') ";
	  }
	  else
	  {
		postmt="insert into PO_IMAGE (IMAGE_ID, RADIAN_PO, DATE_PRINTED, FILE_URL, SUPPLIER, PRINTED_BY, PRINT_TYPE) values (" + imageSeq + "," + PurchaseOrder + ",sysdate,'" + finalurl + "','" + posupplier + "',"+personnelID+",'"+prnitType+"') ";
	  }

	  db.doUpdate( postmt );
    }
	catch ( Exception sqle )
	{
	  //sqle.printStackTrace();
    reoprtlog.info("Cannot insert into po_image PO: "+PurchaseOrder+" URL "+finalurl+" ");
  }

	AddressInfo=null;
	JDEpoInfo=null;
	JDEpoInfoH=null;
	MSDSrevdate=null;
	PoInfo=null;
	ShipperInfo=null;
	PoData=null;
	BuyerInfo=null;
	Msds_Datess=null;
	Flow_Data=null;
	Msds_Date=null;
	Po_Date=null;
	Flow_Data=null;
	radianItems="";
	msdsmessage="";

	return MsgBuild;
  }

    public RegisterTable[] getData( Vector reportData1 )  throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {
        rt[0]=new RegisterTable( PoData.getVector( reportData1 ),"PODATA",PoData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        //e1.printStackTrace();
        throw e1;
      }
      return rt;
    }

    public RegisterTable[] getCommentData( Vector reportData1 )  throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {
        rt[0]=new RegisterTable( CommentsData.getVector( reportData1 ),"PURCH_JDE",CommentsData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        //e1.printStackTrace();
        throw e1;
      }
      return rt;
    }

    public RegisterTable[] getFlowData( Vector reportData1 )  throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {
        rt[0]=new RegisterTable( FlowData.getVector( reportData1 ),"FLOWDATA",FlowData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        //e1.printStackTrace();
        throw e1;
      }
      return rt;
    }

    public RegisterTable[] getMoreCommentsData( Vector reportData1 )  throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {
        rt[0]=new RegisterTable( MoreCommentsData.getVector( reportData1 ),"MORECOMMENTSDATA",MoreCommentsData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        //e1.printStackTrace();
        throw e1;
      }
      return rt;
    }

    public RegisterTable[] getVendorQualifData( Vector reportData1 )  throws Exception
    {
      RegisterTable[] rt=new RegisterTable[1];

      try
      {
        rt[0]=new RegisterTable( VendorQualData.getVector( reportData1 ),"VENDORQUALDATA",VendorQualData.getFieldVector(),null );
      }
      catch ( Exception e1 )
      {
        //e1.printStackTrace();
        throw e1;
      }
      return rt;
    }

}
