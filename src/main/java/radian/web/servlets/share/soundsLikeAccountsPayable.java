package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
//import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-20-02
 * getting the billing address from the voucher tbale instead of the join query
 *
 * 11-25-02
 * if verified != 'y' then allow user to verified, otherwise disable 'Verified' button
 *
 * 12-14-02
 * add reference to builditemmatching
 * 04-07-03 - Amount refused was not being calculated correctly fixed it.
 * 04-22-03 - apnote was not being changed to override the " \n and stuff like that so callinh helpopbjs.addspacesforjavascript method
 * 04-28-03 - Changed the order by on item match and receipt match queries
 * 05-09-03 - Need to decide which invoces to show in the drop down how much unit prce to match
 * 05-12-03 - Increase the Reference size being show on the screen
 * 05-19-03 - Using double instead of floats clenup code and added Date sent for payment and invoice url
 * 06-04-03 - Sorting the invoice drop down by the supplier_invocie_id and making the supplierinvocie id searchabe
 * 07-10-03 - Added the Haas carrier and carrier supplier Id
 * 08-02-03 - Code refactoring created ap helpobj and changed the sort by of invocie to DATE_LAST_UPDATED
 * 08-06-03 - Selecting the latest invoice entered but changed the sort by back to SUPPLIER_INVOICE_ID. Added the ship to address and the capability
 * to show all invoices and receipt or just the ones that need attention
 * 08-07-03 - Fixed a JavaScript Error
 * 10-23-03 - Cannot change billing address after voucher sent to Haas
 * 02-11-03 - Releasing with the changes to make the showonly open invocie user group works
 * 03-02-04 - Changes to allow additional charge RMA Credit
 * 08-17-04 - Changed the invocie URL Query
 * 09-06-04 - Providing a link to expedite notes page
 * 01-10-05 - Showing Currency Id and Consigned flag.
 * 04-26-05 - Showing Home Company Name
 */

public class soundsLikeAccountsPayable
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    //private DBResultSet dbrs = null;
    //private ResultSet rs = null;
    private String privatepersonnelId = "";
    private String nematid_Servlet = "";
    private  boolean allowupdate;
  	private  boolean allinvocies;
		private boolean voucherQcUpdate;
		private boolean resetInvoiceStatus;
		private boolean intcmIsApplication = false;
    private ResourceLibrary res = null; // res means resource.

    public soundsLikeAccountsPayable(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void setupdateStatus(boolean id)
    {
      this.allowupdate = id;
    }

    private boolean getupdateStatus()  throws Exception
    {
     return this.allowupdate;
    }

    public void setallinvocieStatus(boolean id)
	{
	  this.allinvocies = id;
	}

	private boolean getallinvocieStatus()  throws Exception
	{
	 return this.allinvocies;
	}

	public void setVoucherQcStatus(boolean id)
	{
	 this.voucherQcUpdate = id;
	}

 private boolean getVoucherQcStatus()  throws Exception
 {
	return this.voucherQcUpdate;
 }

 public void setResetInvoiceStatus(boolean id)
 {
	this.resetInvoiceStatus = id;
 }

 private boolean getResetInvoiceStatus()  throws Exception
					{
					 return this.resetInvoiceStatus;
					}


     public void doResult(HttpServletRequest request, HttpServletResponse response,PrintWriter out)  throws ServletException,  IOException
    {
    //PrintWriter out = response.getWriter();
    response.setContentType("text/html");
    HttpSession invoicesession = request.getSession();
    privatepersonnelId= invoicesession.getAttribute( "PERSONNELID" ) == null ? "" : invoicesession.getAttribute( "PERSONNELID" ).toString();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)invoicesession.getAttribute("tcmISLocale"));
    PersonnelBean personnelBean = (PersonnelBean) invoicesession.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		intcmIsApplication = false;
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    String selectedrowid=request.getParameter( "selectedrowid" );
    if ( selectedrowid == null )
      selectedrowid="";
    selectedrowid=selectedrowid.trim();

    String openOrders=request.getParameter( "openOrders" );
    if ( openOrders == null )
      openOrders="";
    openOrders=openOrders.trim();

    String radianBPOadd=request.getParameter( "radianBPOadd" );
    if ( radianBPOadd == null )
      radianBPOadd="";
    radianBPOadd=radianBPOadd.trim();

    String onlymypos=request.getParameter( "onlymypos" );
    if ( onlymypos == null )
      onlymypos="";
    onlymypos=onlymypos.trim();

    String unconfirmedOrders=request.getParameter( "unconfirmedOrders" );
    if ( unconfirmedOrders == null )
      unconfirmedOrders="";
    unconfirmedOrders=unconfirmedOrders.trim();

    String brokenprOrders=request.getParameter( "brokenprOrders" );
    if ( brokenprOrders == null )
      brokenprOrders="";
    brokenprOrders=brokenprOrders.trim();

    String SortBy=request.getParameter( "SortBy" );
    if ( SortBy == null )
      SortBy="";
    SortBy=SortBy.trim();

    String SortBy1=request.getParameter( "SortBy1" );
    if ( SortBy1 == null )
      SortBy1="";
    SortBy1=SortBy1.trim();

    String updateMsg=request.getParameter( "updateMsg" );
    if ( updateMsg == null )
      updateMsg="";
    updateMsg=updateMsg.trim();

    String HubName=request.getParameter( "HubName" );
    if ( HubName == null )
      HubName="";
    HubName=HubName.trim();

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action=Action.trim();

    String SearchString=request.getParameter( "SearchString" );
    if ( SearchString == null )
      SearchString="";
    SearchString=SearchString.trim();

    String SearchString1=request.getParameter( "SearchString1" );
    if ( SearchString1 == null )
      SearchString1="";
    SearchString1=SearchString1.trim();

    String radianPO=request.getParameter( "radianPO" );
    if ( radianPO == null )
      radianPO="";
    radianPO=radianPO.trim();

    String lineID=request.getParameter( "lineID" );
    if ( lineID == null )
      lineID="";
    lineID=lineID.trim();

    String itemID=request.getParameter( "itemID" );
    if ( itemID == null )
      itemID="";
    itemID=itemID.trim();

    String subUserAction=request.getParameter( "subUserAction" );
    if ( subUserAction == null )
      subUserAction="";
    subUserAction=subUserAction.trim();

		String selectedInvoiceId=request.getParameter( "selectedInvoiceId" );
		if ( selectedInvoiceId == null )
			selectedInvoiceId="";
		selectedInvoiceId=selectedInvoiceId.trim();

    String ammendment=request.getParameter( "ammendment" );
    if ( ammendment == null )
      ammendment="";
    ammendment=ammendment.trim();


    if ( "showallinvlines".equalsIgnoreCase( Action ) )
    {
      buildshowallInvlines(radianPO,itemID,lineID,true,true,out);
    }
    else if ( "showunmatchedinv".equalsIgnoreCase( Action ) )
    {
      buildshowallInvlines(radianPO,itemID,lineID,true,false,out);
    }
    else if ( "showallreceiptlines".equalsIgnoreCase( Action ) )
    {
      buildshowallInvlines(radianPO,itemID,lineID,false,true,out);
    }
    else if ( "showunmatchedrecipts".equalsIgnoreCase( Action ) )
    {
      buildshowallInvlines(radianPO,itemID,lineID,false,false,out);
    }
    else if ("okupdate".equalsIgnoreCase(Action))
    {
	buildsendSupplierpo(invoicesession,radianPO,updateMsg,selectedrowid,selectedInvoiceId,out);
    }
    else if ("recalpo".equalsIgnoreCase(Action))
    {
    buildsendSupplierpo(invoicesession,radianPO,updateMsg,selectedrowid,selectedInvoiceId,out);
    }
    else
    {
    buildsendSupplierpo(invoicesession,radianPO,updateMsg,selectedrowid,selectedInvoiceId,out);
    }
    out.close();
  }

  public void buildshowallInvlines( String radianPO,String lineItemid,String linsinJs, boolean invoreceipt,boolean showallornot,PrintWriter apout )
  {
    //StringBuffer Msgn=new StringBuffer();
    //StringBuffer Msgbsody=new StringBuffer();
    nematid_Servlet=bundle.getString( "PURCHASE_ORDER_PO" );

    apout.println( radian.web.HTMLHelpObj.printHTMLHeader( "title.accountspayableshowalllines","",intcmIsApplication,res ) );
    printJavaScripts(apout);
    apout.println( "// -->\n </SCRIPT>\n" );

    //StringBuffer Msgtemp=new StringBuffer();
    apout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    apout.println( "<!--\n" );
    apout.println( "function sendSupplierData()\n" );
    apout.println( "{\n" );
    if ( ( lineItemid.length() > 0 ) )
    {
      boolean updateststus=false;
      try
      {
        updateststus=this.getupdateStatus();
      }
      catch ( Exception ex )
      {
      }
      if (invoreceipt)
      {
        radian.web.apHelpObj.builditemmatching( db,radianPO,lineItemid,linsinJs,"",updateststus,showallornot,apout,res );
      }
      else
      {
        radian.web.apHelpObj.buildreceiptmatching( db,radianPO,lineItemid,linsinJs,updateststus,showallornot,apout,res );
      }
    }
    apout.println( "window.close();\n" );
    apout.println( " }\n" );
    apout.println( "// -->\n</SCRIPT>\n" );
    //Msgn.append( Msgtemp );
    apout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	apout.println( "<FORM METHOD=\"POST\" name=\"showallinv\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
    apout.println( "<CENTER><BR><BR>\n" );
    apout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\""+res.getString("label.ok")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    apout.println( "</FORM></BODY></HTML>\n" );
    //apout.println( Msgbody );

    //return Msgn;
  }

  public void buildsendSupplierpo( HttpSession invsession,String radianPO,String updateMessage,String selectedrow,String selectedInvoiceId,PrintWriter apout )
  {
    //StringBuffer Msgn=new StringBuffer();
    //StringBuffer Msdgbody=new StringBuffer();
    //DecimalFormat poTotal=new DecimalFormat( "####.00##" );
    nematid_Servlet=bundle.getString( "PURCHASE_ORDER_PO" );
	  DBResultSet dbrs = null;
    ResultSet rs = null;
    boolean isReversed = false;
    Long startTime = (Long) invsession.getAttribute("startTime");

    apout.println( radian.web.HTMLHelpObj.printHTMLHeader( "title.accountspayablelookup","",intcmIsApplication,res) );
    printJavaScripts(apout);
    apout.println( "// -->\n </SCRIPT>\n" );

    //StringBuffer Msgtemp=new StringBuffer();
    //Build the Java Script Here and Finish the thing
    apout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    apout.println( "<!--\n" );
    apout.println( "function sendSupplierData()\n" );
    apout.println( "{\n" );
    apout.println( "opener.removeAllLines(); \n" );

    boolean foundsupplier=false;
    if ( radianPO.trim().length() > 0 )
    {
      try
      {
        int test_number=DbHelpers.countQuery( db,"select count(*) from po_header_view where RADIAN_PO = '" + radianPO + "' " );

        if ( test_number == 1 )
        {
          foundsupplier=true;
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        apout.println( "<BODY><BR><BR>\n" );
        apout.println( "<FONT FACE=\"Arial\" SIZE=\"3\">"+res.getString("errors.header")+"<BR>\n" );
        apout.println( "<CENTER><BR><BR>\n" );
        apout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\""+res.getString("label.ok")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
        apout.println( "</FORM></BODY></HTML>\n" );
        return;
      }
    }

    if ( !foundsupplier )
    {
      apout.println( "messagerow = opener.document.getElementById(\"messagerow\");\n" );
      apout.println( "messagerow.innerHTML = \"<FONT SIZE=\\\"3\\\" FACE=\\\"Arial\\\" COLOR=\\\"#fc0303\\\"><B>"+res.getString("label.norecordsfoundforpo")+"</B></FONT>\";\n" );
    }
    else
    {
      apout.println( "messagerow = opener.document.getElementById(\"messagerow\");\n" );
      apout.println( "messagerow.innerHTML = \"<FONT SIZE=\\\"3\\\" FACE=\\\"Arial\\\" COLOR=\\\"#fc0303\\\"><B>" + updateMessage + "</B></FONT>\";\n" );

      int count=0;
      String supplier="";
      String ship_to_location_id="";
      String bo="";
      String branch_plant1="";
      String ship_to_company_id="";
			String assignCharges="";
			String hubName = "";

      try
      {
        String poquery="Select HUB_NAME,ASSIGN_CHARGES,OPERATING_ENTITY_NAME,CONSIGNED_PO,HAAS_CARRIER,CARRIER_SUPPLIER_ID,RADIAN_PO,BO,BUYER_EMAIL,BUYER_PHONE,EVER_CONFIRMED,SUPPLIER,SUPPLIER_CONTACT_ID,BUYER,BUYER_NAME,SHIP_TO_COMPANY_ID, ";
        poquery+="SHIP_TO_LOCATION_ID,BRANCH_PLANT,FREIGHT_ON_BOARD,PAYMENT_TERMS,CARRIER,CARRIER_ACCOUNT,CUSTOMER_PO, ";
        poquery+="to_char(DATE_SENT,'mm/dd/yyyy') DATE_SENT,to_char(DATE_ACCEPTED,'mm/dd/yyyy') DATE_ACCEPTED, ";
        poquery+="to_char(BO_START_DATE,'mm/dd/yyyy') BO_START_DATE,to_char(BO_END_DATE,'mm/dd/yyyy') BO_END_DATE, ";
        poquery+="SUPPLIER_NAME,SUPPLIER_CONTACT_NAME,CARRIER_COMPANY_ID,CARRIER_ACCOUNT,CARRIER_NAME,CARRIER_HUB,SUPPLIER_COUNTRY_ABBREV,SUPPLIER_STATE_ABBREV, ";
        poquery+="SUPPLIER_ADDRESS_LINE_1,SUPPLIER_ADDRESS_LINE_2,SUPPLIER_ADDRESS_LINE_3,SUPPLIER_CITY,SUPPLIER_ZIP,SUPPLIER_LOCATION_DESC,SHIPTO_COUNTRY_ABBREV, ";
        poquery+="EMAIL,SUPPLIER_PHONE,SUPPLIER_CONTACT_PHONE,SUPPLIER_CONTACT_FAX,SHIPTO_STATE_ABBREV,SHIPTO_ADDRESS_LINE_1,SHIPTO_ADDRESS_LINE_2,SHIPTO_ADDRESS_LINE_3,SHIPTO_CITY,SHIPTO_ZIP,SHIPTO_LOCATION_DESC ";
        poquery+=",special_charge_po ";
        poquery+="from po_header_view where radian_po = " + radianPO + " ";

        dbrs=db.doQuery( poquery );
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          String radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
          bo=rs.getString( "BO" ) == null ? "" : rs.getString( "BO" ).trim();
          supplier=HelpObjs.addescapesForJavascript( rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ).trim() );
          String buyername=rs.getString( "BUYER_NAME" ) == null ? "" : rs.getString( "BUYER_NAME" ).trim();
          ship_to_company_id=rs.getString( "SHIP_TO_COMPANY_ID" ) == null ? "" : rs.getString( "SHIP_TO_COMPANY_ID" ).trim();
          ship_to_location_id=rs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "" : rs.getString( "SHIP_TO_LOCATION_ID" ).trim();
          branch_plant1=rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ).trim();
          String freight_on_board=rs.getString( "FREIGHT_ON_BOARD" ) == null ? "" : rs.getString( "FREIGHT_ON_BOARD" ).trim();
          String payment_term=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).trim();
          String buyeremail=rs.getString( "BUYER_EMAIL" ) == null ? "" : rs.getString( "BUYER_EMAIL" ).trim();
          String buyerphone=rs.getString( "BUYER_PHONE" ) == null ? "" : rs.getString( "BUYER_PHONE" ).trim();
          String haascarrier=rs.getString( "HAAS_CARRIER" ) == null ? "" : rs.getString( "HAAS_CARRIER" ).trim();
          String carriersuppid=rs.getString( "CARRIER_SUPPLIER_ID" ) == null ? "" : rs.getString( "CARRIER_SUPPLIER_ID" ).trim();
		  String operatingEntityName=rs.getString( "OPERATING_ENTITY_NAME" ) == null ? "" : rs.getString( "OPERATING_ENTITY_NAME" ).trim();
		  String consignedpo=rs.getString( "CONSIGNED_PO" ) == null ? "" : rs.getString( "CONSIGNED_PO" ).trim();
          hubName = rs.getString("HUB_NAME") == null ? "" : rs.getString("HUB_NAME").trim();

		 String suppliercontactemail = rs.getString("EMAIL")==null?"":rs.getString("EMAIL").trim();
		 String supplier_contact_name = rs.getString("SUPPLIER_CONTACT_NAME")==null?"":rs.getString("SUPPLIER_CONTACT_NAME").trim();
		 String supplier_contact_phone = rs.getString("SUPPLIER_CONTACT_PHONE")==null?"":rs.getString("SUPPLIER_CONTACT_PHONE").trim();
         String specialChargePo=rs.getString( "special_charge_po" ) == null ? "" : rs.getString( "special_charge_po" ).trim();
         String supplierIdDisplay= rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ).trim();

		  if ("Y".equalsIgnoreCase(consignedpo))
			consignedpo = "Yes";
		  else
			consignedpo = "No";

          if ("Y".equalsIgnoreCase(specialChargePo))
              specialChargePo = "Yes";
          else
              specialChargePo = "No";

		  assignCharges=rs.getString( "ASSIGN_CHARGES" ) == null ? "" : rs.getString( "ASSIGN_CHARGES" ).trim();

          if ( freight_on_board.length() == 0 )
          {
            freight_on_board="None";
          }
          if ( branch_plant1.length() == 0 )
          {
            branch_plant1="None";
          }

          count++;

          apout.println( "haascarrier = opener.document.getElementById(\"haascarrier\");\n" );
          apout.println( "haascarrier.value = \"" + haascarrier + "\";\n" );

          apout.println( "carriersuppid = opener.document.getElementById(\"carriersuppid\");\n" );
          apout.println( "carriersuppid.value = \"" + carriersuppid + "\";\n" );

          apout.println( "paymentterm = opener.document.getElementById(\"poneterms\");\n" );
          apout.println( "paymentterm.innerHTML = \"" + payment_term + "\";\n" );

		  apout.println( "consignedpo = opener.document.getElementById(\"consignedpo\");\n" );
          apout.println( "consignedpo.innerHTML = \"" + consignedpo + "\";\n" );

		  apout.println( "homecompany = opener.document.getElementById(\"homecompany\");\n" );
          apout.println( "homecompany.innerHTML = \"" + operatingEntityName + "\";\n" );

          apout.println( "expeditenotes = opener.document.getElementById(\"expeditenotes\");\n" );
		  apout.println( "expeditenotes.innerHTML = \"<A onclick=\\\"javascript:window.open('https://www.tcmis.com/cgi-bin/purch/exped_lookup.cgi?po=" + radian_po + "')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\"><U>"+res.getString("label.expeditenotes")+"</U></A>\";\n");
//          apout.println( "expeditenotes.innerHTML = \"<A HREF=\\\"https://www.tcmis.com/cgi-bin/purch/exped_lookup.cgi?po=" + radian_po + "\\\">Expedite Notes</A>\";\n" );

          apout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
          apout.println( "selectedRow.className = \"HEADER\";\n" );
          apout.println( "selectedRow.value = \"" + radian_po + "\";\n" );

          apout.println( "selectedRow = opener.document.getElementById(\"validpo\");\n" );
          apout.println( "selectedRow.value = \"Yes\";\n" );

		  apout.println( "buyernameandstuff = opener.document.getElementById(\"buyernameandstuff\");\n" );
			if (buyeremail.length() > 0)
			{
			 apout.println( "buyernameandstuff.innerHTML = \"<A HREF=\\\"mailto:" + buyeremail + "\\\">" + buyername + "</A><br>(" + buyerphone + ")\";\n" );
			}
			else
			{
			 apout.println( "buyernameandstuff.innerHTML = \"" + buyername + "<br>(" + buyerphone + ")\";\n" );
		  }

		  apout.println( "supplierContactInfo = opener.document.getElementById(\"supplierContactInfo\");\n" );
			if (suppliercontactemail.length() > 0)
			{
			 apout.println( "supplierContactInfo.innerHTML = \"<A HREF=\\\"mailto:" + suppliercontactemail + "\\\">" + supplier_contact_name + "</A><br>(" + supplier_contact_phone + ")\";\n" );
			}
			else
			{
			 apout.println( "supplierContactInfo.innerHTML = \"" + supplier_contact_name + "<br>(" + supplier_contact_phone + ")\";\n" );
		  }

          String suppliername=rs.getString( "SUPPLIER_NAME" ) == null ? "" : rs.getString( "SUPPLIER_NAME" ).trim();
          String suppliercountryabbrev=rs.getString( "SUPPLIER_COUNTRY_ABBREV" ) == null ? "" : rs.getString( "SUPPLIER_COUNTRY_ABBREV" ).trim();
          String supplierstateabbrev=rs.getString( "SUPPLIER_STATE_ABBREV" ) == null ? "" : rs.getString( "SUPPLIER_STATE_ABBREV" ).trim();
          String supplieraddressline1=rs.getString( "SUPPLIER_ADDRESS_LINE_1" ) == null ? "" : rs.getString( "SUPPLIER_ADDRESS_LINE_1" ).trim();
          String supplieraddressline2=rs.getString( "SUPPLIER_ADDRESS_LINE_2" ) == null ? "" : rs.getString( "SUPPLIER_ADDRESS_LINE_2" ).trim();
          //String supplieraddressline3 = rs.getString("SUPPLIER_ADDRESS_LINE_3")==null?"":rs.getString("SUPPLIER_ADDRESS_LINE_3").trim();
          String suppliercity=rs.getString( "SUPPLIER_CITY" ) == null ? "" : rs.getString( "SUPPLIER_CITY" ).trim();
          String supplierzip=rs.getString( "SUPPLIER_ZIP" ) == null ? "" : rs.getString( "SUPPLIER_ZIP" ).trim();
          String supplierphone=rs.getString( "SUPPLIER_PHONE" ) == null ? "" : rs.getString( "SUPPLIER_PHONE" ).trim();

          apout.println( "mfgphoneno = opener.document.getElementById(\"mfgphoneno\");\n" );
          apout.println( "mfgphoneno.value = \"" + supplierphone + "\";\n" );

          apout.println( "specialCharge = opener.document.getElementById(\"specialChargePo\");\n" );
          apout.println( "specialCharge.value = \"" + specialChargePo + "\";\n" );

          apout.println( "supplierIdDisplay = opener.document.getElementById(\"supplierIdDisplay\");\n" );
          apout.println( "supplierIdDisplay.innerHTML = \"" + supplierIdDisplay + "\";\n" );

          apout.println( "selectedRow = opener.document.getElementById(\"supplierid\");\n" );
          apout.println( "selectedRow.value = \"" + supplier + "\";\n" );

          if ( supplier.trim().length() > 0 )
          {
            apout.println( "selectedRow = opener.document.getElementById(\"validsupplier\");\n" );
            apout.println( "selectedRow.value = \"Yes\";\n" );
          }
          else
          {
            apout.println( "selectedRow = opener.document.getElementById(\"validsupplier\");\n" );
            apout.println( "selectedRow.value = \"No\";\n" );

            apout.println( "supplierid = opener.document.getElementById(\"supplierid\");\n" );
            apout.println( "supplierid.className = \"INVALIDTEXT\";\n" );
          }

          apout.println( "selectedRow = opener.document.getElementById(\"supplierline1\");\n" );
          apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(suppliername) + "</FONT>\";\n" );

          apout.println( "selectedRow = opener.document.getElementById(\"supplierline2\");\n" );
          apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(supplieraddressline1) + "</FONT>\";\n" );

          if ( supplieraddressline2.length() > 0 )
          {
            apout.println( "selectedRow = opener.document.getElementById(\"supplierline3\");\n" );
            apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(supplieraddressline2) + "</FONT>\";\n" );

            apout.println( "selectedRow = opener.document.getElementById(\"supplierline4\");\n" );
            apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + suppliercity + ", " + supplierstateabbrev + " " + supplierzip + " " + suppliercountryabbrev + "</FONT>\";\n" );
          }
          else
          {
            apout.println( "selectedRow = opener.document.getElementById(\"supplierline3\");\n" );
            apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + suppliercity + ", " + supplierstateabbrev + " " + supplierzip + " " + suppliercountryabbrev + "</FONT>\";\n" );

			apout.println( "selectedRow = opener.document.getElementById(\"supplierline4\");\n" );
			apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n" );
		  }

          String shiptocountryabbrev=rs.getString( "SHIPTO_COUNTRY_ABBREV" ) == null ? "" : rs.getString( "SHIPTO_COUNTRY_ABBREV" ).trim();
          String shiptostateabbrev=rs.getString( "SHIPTO_STATE_ABBREV" ) == null ? "" : rs.getString( "SHIPTO_STATE_ABBREV" ).trim();
          String shiptoaddressline1=rs.getString( "SHIPTO_ADDRESS_LINE_1" ) == null ? "" : rs.getString( "SHIPTO_ADDRESS_LINE_1" ).trim();
          String shiptoaddressline2=rs.getString( "SHIPTO_ADDRESS_LINE_2" ) == null ? "" : rs.getString( "SHIPTO_ADDRESS_LINE_2" ).trim();
          String shiptoaddressline3 = rs.getString("SHIPTO_ADDRESS_LINE_3")==null?"":rs.getString("SHIPTO_ADDRESS_LINE_3").trim();
          String shiptocity=rs.getString( "SHIPTO_CITY" ) == null ? "" : rs.getString( "SHIPTO_CITY" ).trim();
          String shiptozip=rs.getString( "SHIPTO_ZIP" ) == null ? "" : rs.getString( "SHIPTO_ZIP" ).trim();
          String shiptolocationdesc=rs.getString( "SHIPTO_LOCATION_DESC" ) == null ? "" : rs.getString( "SHIPTO_LOCATION_DESC" ).trim();

          apout.println( "shiptoaddress = opener.document.getElementById(\"shiptoaddress\");\n" );
          apout.println( "var shpinnerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + shiptolocationdesc + "</FONT>\";\n" );
          apout.println( "shpinnerHTML += \"<BR><FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(shiptoaddressline1) + "</FONT>\";\n" );
          apout.println( "shpinnerHTML += \"<BR><FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(shiptoaddressline2) + "</FONT>\";\n" );

          if ( shiptoaddressline3.length() > 0 )
          {
            apout.println( "shpinnerHTML += \"<BR><FONT COLOR=\\\"Maroon\\\">" + shiptoaddressline3 + "</FONT>\";\n" );
            apout.println( "shpinnerHTML += \"<BR><FONT COLOR=\\\"Maroon\\\">" + shiptocity + ", " + shiptostateabbrev + " " + shiptozip + " " +  shiptocountryabbrev + "</FONT>\";\n" );
          }
          else
          {
            apout.println( "shpinnerHTML += \"<BR><FONT COLOR=\\\"Maroon\\\">" + shiptocity + ", " + shiptostateabbrev + " " + shiptozip + " " +  shiptocountryabbrev + "</FONT>\";\n" );
            apout.println( "shpinnerHTML += \"<BR><FONT COLOR=\\\"Maroon\\\"></FONT>\";\n" );
          }

          apout.println( "shiptoaddress.innerHTML = shpinnerHTML;\n" );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        if ( dbrs != null )  { dbrs.close();  }
      }

      Hashtable result=new Hashtable();
      Vector ResultV=new Vector();
      String vouchersupplier="";
      String selvoucherid = "";
      int selvoucheridindex = 0;

      if ( count > 0 )
      {
        int noofinvocies=0;
        String voucherbillingaddress="";

        apout.println( "opener.removeAllOptionItem(opener.document.getElementById(\"invoices\"));\n" );
        try
        {
				  if (selectedInvoiceId.trim().length() == 0)
					{
          String latestinvoice = "select VOUCHER_ID from voucher where radian_po = " + radianPO + " and DATE_LAST_UPDATED is not null order by DATE_LAST_UPDATED asc";
          dbrs=db.doQuery( latestinvoice );
          rs=dbrs.getResultSet();

          while ( rs.next() )
          {
            selvoucherid = rs.getString( "VOUCHER_ID" ) == null ? "" : rs.getString( "VOUCHER_ID" ).trim();
          }
					}
					else
					{
					 selvoucherid = selectedInvoiceId;
		      }				  
				
          String invoiceQuery="";
          invoiceQuery="select UPLOAD_STATUS,ORIG_APPROVER,APPROVER, to_char(APPROVED_DATE,'mm/dd/yyyy') APPROVED_DATE, QC_USER, to_char(QC_DATE,'mm/dd/yyyy') QC_DATE, AP_USER_NAME, AP_APPROVER_NAME, AP_QC_USER_NAME,CURRENCY_ID,verified,AP_NOTE,VOUCHER_BILLING_LOCATION,VOUCHER_ID,SUPPLIER,SUPPLIER_INVOICE_ID,ORIGINAL_INVOICE_ID,to_char(INVOICE_DATE,'mm/dd/yyyy') INVOICE_DATE1,INVOICE_DATE,to_char(DATE_INVOICE_RECEIVED,'mm/dd/yyyy') DATE_INVOICE_RECEIVED,RADIAN_PO,CUSTOMER_REF_PO, ";
          invoiceQuery+="PAYMENT_TERMS,INVOICE_FILE_PATH,NET_INVOICE_AMOUNT,PYMT_TERM_START_DATE,DATE_TO_PAY,REMIT_TO_LOC_ID,SUPPLIER_CONTACT_ID,ORIGINAL_SUPPLIER,STATUS,INVOICE_AMOUNT";
          invoiceQuery+=",to_char(DATE_SENT_TO_HASS,'mm/dd/yyyy') DATE_SENT_TO_HASS,nvl(INVOICE_URL,fx_iq_image_url(radian_po,supplier_invoice_id)) INVOICE_URL,SAP_IMPORT_FLAG from voucher_view where radian_po = " + radianPO + " order by SUPPLIER_INVOICE_ID asc ";

          dbrs=db.doQuery( invoiceQuery );
          rs=dbrs.getResultSet();
          
          while ( rs.next() )
          {
            noofinvocies++;

            String voucherid=rs.getString( "VOUCHER_ID" ) == null ? "" : rs.getString( "VOUCHER_ID" ).trim();
            if (selvoucherid.trim().length() == 0 && noofinvocies == 1) {selvoucherid = voucherid; }
            String supplier_invoice_id=rs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : rs.getString( "SUPPLIER_INVOICE_ID" ).trim();
            String original_invoice_id=rs.getString( "ORIGINAL_INVOICE_ID" ) == null ? "" : rs.getString( "ORIGINAL_INVOICE_ID" ).trim();
            String invoice_date=rs.getString( "INVOICE_DATE1" ) == null ? "" : rs.getString( "INVOICE_DATE1" ).trim();
            String date_invoice_received=rs.getString( "DATE_INVOICE_RECEIVED" ) == null ? "" : rs.getString( "DATE_INVOICE_RECEIVED" ).trim();
            //String radian_po = rs.getString("RADIAN_PO")==null?"":rs.getString("RADIAN_PO").trim();
            //String customer_ref_po = rs.getString("CUSTOMER_REF_PO")==null?"":rs.getString("CUSTOMER_REF_PO").trim();
            //String invoice_file_path = rs.getString("INVOICE_FILE_PATH")==null?"":rs.getString("INVOICE_FILE_PATH").trim();
            String net_invoice_amount=rs.getString( "NET_INVOICE_AMOUNT" ) == null ? "" : rs.getString( "NET_INVOICE_AMOUNT" ).trim();
            String invoiceAmount=rs.getString( "INVOICE_AMOUNT" ) == null ? "" : rs.getString( "INVOICE_AMOUNT" ).trim();
            //String pymt_term_start_date = rs.getString("PYMT_TERM_START_DATE")==null?"":rs.getString("PYMT_TERM_START_DATE").trim();
            //String date_to_pay = rs.getString("DATE_TO_PAY")==null?"":rs.getString("DATE_TO_PAY").trim();
            //String remit_to_loc_id = rs.getString("REMIT_TO_LOC_ID")==null?"":rs.getString("REMIT_TO_LOC_ID").trim();
            //String supplier_contact_id = rs.getString("SUPPLIER_CONTACT_ID")==null?"":rs.getString("SUPPLIER_CONTACT_ID").trim();
            //String original_supplier = rs.getString("ORIGINAL_SUPPLIER")==null?"":rs.getString("ORIGINAL_SUPPLIER").trim();
            String status=rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ).trim();
            String paymentterms1=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).trim();
            String apnote=HelpObjs.addescapesForJavascript( rs.getString( "AP_NOTE" ) == null ? "" : rs.getString( "AP_NOTE" ).trim() );
            String datesentforpymt=rs.getString( "DATE_SENT_TO_HASS" ) == null ? "" : rs.getString( "DATE_SENT_TO_HASS" ).trim();
            String invoiceurl=rs.getString( "INVOICE_URL" ) == null ? "" : rs.getString( "INVOICE_URL" ).trim();
						String currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();
						String approverName=rs.getString( "AP_APPROVER_NAME" ) == null ? "" : rs.getString( "AP_APPROVER_NAME" ).trim();
						String approvedDate=rs.getString( "APPROVED_DATE" ) == null ? "" : rs.getString( "APPROVED_DATE" ).trim();
						String qcDate=rs.getString( "QC_DATE" ) == null ? "" : rs.getString( "QC_DATE" ).trim();
						String qcUser=rs.getString( "AP_QC_USER_NAME" ) == null ? "" : rs.getString( "AP_QC_USER_NAME" ).trim();
						String origApproverId=rs.getString( "ORIG_APPROVER" ) == null ? "" : rs.getString( "ORIG_APPROVER" ).trim();
            String uploadStatus=rs.getString( "UPLOAD_STATUS" ) == null ? "" : rs.getString( "UPLOAD_STATUS" ).trim();
            String sapImportFlag = rs.getString( "SAP_IMPORT_FLAG" ) == null ? "" : rs.getString( "SAP_IMPORT_FLAG" ).trim();

            result=new Hashtable();
            result.put( supplier_invoice_id,supplier_invoice_id );
            ResultV.addElement( result );

            apout.println( "opener.addOptionItem(opener.document.getElementById(\"invoices\"),'" + voucherid + "','" + supplier_invoice_id + "');\n" );
            
            //if ( noofinvocies == 1 )
            if (selvoucherid.equalsIgnoreCase(voucherid))
            {
                apout.println( "var supplierInvoiceId = opener.document.getElementById(\"supplierInvoiceId\");\n" );
                apout.println( "if(supplierInvoiceId != null || supplierInvoiceId != undefined) supplierInvoiceId.value = \"" + supplier_invoice_id + "\";\n" );
                
	            DBResultSet dbrsReversed = db.doQuery("select 'X' from voucher where radian_po = "+radianPO+" and supplier_invoice_id "+(supplier_invoice_id.indexOf("CORR")== -1 ?"like '"+ supplier_invoice_id + "%CORR%": "= '" + supplier_invoice_id)+"' and status in ( 'Approved', 'In Progress')");       
	   		  	ResultSet rsGetWasReversed = dbrsReversed.getResultSet();
	            while ( rsGetWasReversed.next() )
	            {
	          	 isReversed = rsGetWasReversed.getString("'X'") == null ? false : true;
	            }
               
              selvoucheridindex = noofinvocies-1;
              //11-25-02
              if ( "y".equalsIgnoreCase( rs.getString( "verified" ) ) )
              {
                apout.println( "opener.document.purchaseorder.VerifiedB.disabled = true;\n" );
              }
              else
              {
                apout.println( "opener.document.purchaseorder.VerifiedB.disabled = false;\n" );
              }

							PersonnelBean personnelBean = invsession.getAttribute("personnelBean") == null ? null : (PersonnelBean) invsession.getAttribute("personnelBean");
								if (personnelBean.getPermissionBean().hasFacilityPermission("VoucherQC", hubName,null)) {
								 this.setVoucherQcStatus(true);
								}
								else
								{
								 this.setVoucherQcStatus(false);
								}

								if (personnelBean.getPermissionBean().hasFacilityPermission("resetInvoiceStatus", hubName,null)) {
									 this.setResetInvoiceStatus(true);
									}
									else {
									 this.setResetInvoiceStatus(false);
									}

							if ( qcDate.trim().length() == 0 && ( "Approved".equalsIgnoreCase( status ) || "Cancelled".equalsIgnoreCase( status ) ) )
							{
								apout.println( "editinvoicedata = opener.document.getElementById(\"editinvoicedata\");\n" );
								apout.println( "editinvoicedata.disabled=true;\n" );
								apout.println( "editinvoicedata.style.display=\"none\";\n" );
								
								apout.println( "voucherreverse = opener.document.getElementById(\"voucherreverse\");\n" );
								apout.println( "if(voucherreverse!= null && voucherreverse != undefined){voucherreverse.disabled=true;\n" );
								apout.println( "voucherreverse.style.display=\"none\";\n}" );
								
								if ( qcDate.trim().length() == 0 && this.getResetInvoiceStatus() )
								{
								 apout.println( "resetinvoice = opener.document.getElementById(\"resetinvoice\");\n" );
								 apout.println( "resetinvoice.disabled=false;\n" );
								 apout.println( "resetinvoice.style.display=\"\";\n" );
								}
								if ( qcDate.trim().length() == 0 && this.getVoucherQcStatus() && status.equalsIgnoreCase("Approved") && !privatepersonnelId.equalsIgnoreCase(origApproverId) )
								{
								 apout.println( "qcvoucher = opener.document.getElementById(\"qcvoucher\");\n" );
								 apout.println( "qcvoucher.disabled=false;\n" );
								 apout.println( "qcvoucher.style.display=\"\";\n" );
								}
							}
							else if ( this.getupdateStatus() )
							{
								if (qcDate.trim().length() == 0)
								{
								apout.println( "editinvoicedata = opener.document.getElementById(\"editinvoicedata\");\n" );
								apout.println( "editinvoicedata.disabled=false;\n" );
								apout.println( "editinvoicedata.style.display=\"\";\n" );
								
								 apout.println( "voucherreverse = opener.document.getElementById(\"voucherreverse\");\n" );
								 apout.println( "if(voucherreverse!= null && voucherreverse != undefined){voucherreverse.disabled=true;\n" );
								 apout.println( "voucherreverse.style.display=\"none\";\n}" );
								}
								else
								{
								 apout.println( "editinvoicedata = opener.document.getElementById(\"editinvoicedata\");\n" );
								 apout.println( "editinvoicedata.disabled=true;\n" );
								 apout.println( "editinvoicedata.style.display=\"none\";\n" );
								 
								 if(!isReversed && "Approved".equalsIgnoreCase( status )&& sapImportFlag.equalsIgnoreCase("Y"))
								 {
									 apout.println( "voucherreverse = opener.document.getElementById(\"voucherreverse\");\n" );
									 apout.println( "if(voucherreverse!= null && voucherreverse != undefined){voucherreverse.disabled=false;\n" );
									 apout.println( "voucherreverse.style.display=\"\";\n}" );
								 }
								 else
								 {
										apout.println( "voucherreverse = opener.document.getElementById(\"voucherreverse\");\n" );
										apout.println( "if(voucherreverse!= null && voucherreverse != undefined){voucherreverse.disabled=true;\n" );
										apout.println( "voucherreverse.style.display=\"none\";\n}" );
								 }
								}

								apout.println( "resetinvoice = opener.document.getElementById(\"resetinvoice\");\n" );
								apout.println( "resetinvoice.disabled=true;\n" );
								apout.println( "resetinvoice.style.display=\"none\";\n" );

								 apout.println( "qcvoucher = opener.document.getElementById(\"qcvoucher\");\n" );
								apout.println( "qcvoucher.disabled=true;\n" );
								apout.println( "qcvoucher.style.display=\"none\";\n" );
							}

              voucherbillingaddress=rs.getString( "VOUCHER_BILLING_LOCATION" ) == null ? "" : rs.getString( "VOUCHER_BILLING_LOCATION" ).trim();
              vouchersupplier=rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ).trim();

              apout.println( "paymentterms = opener.document.getElementById(\"invoicedate\");\n" );
              apout.println( "paymentterms.value = \"" + invoice_date + "\";\n" );

              apout.println( "paymentterms = opener.document.getElementById(\"refinvoice\");\n" );
              apout.println( "paymentterms.value = \"" + original_invoice_id + "\";\n" );

              apout.println( "paymentterms = opener.document.getElementById(\"invdatereceived\");\n" );
              apout.println( "paymentterms.value = \"" + date_invoice_received + "\";\n" );

              apout.println( "datesentforpayment = opener.document.getElementById(\"datesentforpayment\");\n" );
              apout.println( "datesentforpayment.value = \"" + datesentforpymt + "\";\n" );

							apout.println( "currency = opener.document.getElementById(\"currency\");\n" );
              apout.println( "currency.value = \"" + currencyid + "\";\n" );

			 if (approvedDate.length() > 0)
			 {
				apout.println( "approvedby = opener.document.getElementById(\"approvedby\");\n" );
				apout.println( "approvedby.innerHTML = \""+approverName+" on "+approvedDate+"\";\n" );
			 }
			 else
			 {
				apout.println( "approvedby = opener.document.getElementById(\"approvedby\");\n" );
				apout.println( "approvedby.innerHTML = \"\";\n" );
			 }

			 if (qcDate.length() > 0)
			 {
				apout.println( "qcedby = opener.document.getElementById(\"qcedby\");\n" );
				apout.println( "qcedby.innerHTML = \""+qcUser+" on "+qcDate+"\";\n" );
			 }
			 else
			 {
				apout.println( "qcedby = opener.document.getElementById(\"qcedby\");\n" );
				apout.println( "qcedby.innerHTML = \"&nbsp;\";\n" );
			 }

			  if (datesentforpymt.length() >0)
			  {
				apout.println( "searchsupplierlike = opener.document.getElementById(\"editsearchbillingAddress\");\n" );
				apout.println( "searchsupplierlike.disabled = true;\n" );
				apout.println( "searchsupplierlike.style.display=\"none\";\n" );
			  }
			  else
			  {
				apout.println( "searchsupplierlike = opener.document.getElementById(\"editsearchbillingAddress\");\n" );
				apout.println( "searchsupplierlike.disabled = false;\n" );
				apout.println( "searchsupplierlike.style.display=\"\";\n" );
			  }

              String invoiceurlhtml="";
              if ( invoiceurl.length() > 0 )
              {
                StringTokenizer st=new StringTokenizer( invoiceurl," " );
                if ( st.countTokens() > 1 )
                {
                  while ( st.hasMoreTokens() )
                  {
                    String tok=st.nextToken();
                    invoiceurlhtml+="<A onclick=\\\"javascript:window.open('" + tok + "')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\"><U>" + supplier_invoice_id + "</U></A>&nbsp;&nbsp;";
                  }
                }
                else
                {
                  invoiceurlhtml="<A onclick=\\\"javascript:window.open('" + invoiceurl + "')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\"><U>" + supplier_invoice_id + "</U></A>";
                }
              }
              apout.println( "invoiceurl = opener.document.getElementById(\"invoiceurl\");\n" );
              apout.println( "invoiceurl.innerHTML = \"" + invoiceurlhtml + "\";\n" );

              apout.println( "uploadStatus = opener.document.getElementById(\"uploadStatus\");\n" );
              apout.println( "uploadStatus.innerHTML = \"" + uploadStatus + "\";\n" );

              apout.println( "paymentterms = opener.document.getElementById(\"invstatus\");\n" );
              apout.println( "paymentterms.value = \"" + status + "\";\n" );

              BigDecimal voucherTotal= new BigDecimal("0");
              if ( invoiceAmount.length() > 0 )
              {
                voucherTotal= new BigDecimal( invoiceAmount );
              }

              apout.println( "paymentterms = opener.document.getElementById(\"invamount\");\n" );
              apout.println( "paymentterms.value = \"" + voucherTotal.setScale(4,4) + "\";\n" );
              //calculate refused
              voucherTotal=new BigDecimal("0");
              if ( invoiceAmount.length() > 0 )
              {
                BigDecimal tmpAmt=new BigDecimal( invoiceAmount );
                BigDecimal tmpNetAmt=new BigDecimal("0");
                if ( net_invoice_amount.length() > 0 )
                {
                  tmpNetAmt=new BigDecimal( net_invoice_amount );
                }
                voucherTotal=tmpAmt.subtract(tmpNetAmt);
              }
              apout.println( "paymentterms = opener.document.getElementById(\"refused\");\n" );
              apout.println( "paymentterms.value = \"" + voucherTotal.setScale(4,4) + "\";\n" );

              voucherTotal=new BigDecimal("0");
              if ( net_invoice_amount.length() > 0 )
              {
                voucherTotal=new BigDecimal( net_invoice_amount );
              }
              apout.println( "paymentterms = opener.document.getElementById(\"netAmount\");\n" );
              apout.println( "paymentterms.value = \"" + voucherTotal.setScale(4,4) + "\";\n" );

              apout.println( "paymentterms = opener.document.getElementById(\"paymentterms\");\n" );
              apout.println( "paymentterms.value = \"" + paymentterms1 + "\";\n" );

              apout.println( "invoicecomments = opener.document.getElementById(\"invoicecomments\");\n" );
              apout.println( "invoicecomments.innerHTML = \"" + apnote + "\";\n" );
            }
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
        }
        finally
        {
          if ( dbrs != null ) {dbrs.close();}
        }

        if (selvoucherid != null && selvoucherid.length() > 0)
        {
              String taxItemId1 = "";
  String taxAmount1 = "";
  String taxCurrency1 = "";
  String taxItemId2 = "";
  String taxAmount2 = "";
  String taxCurrency2 = "";
              int taxcount = 0;
         try
  {
    String taxQuery="";
	taxQuery = "select x.*,y.item_desc from VOUCHER_TAX x, item y where x.VOUCHER_ID = "+selvoucherid+" and x.TAX_ITEM_ID = y.item_id";

    dbrs=db.doQuery( taxQuery );
    rs=dbrs.getResultSet();

    while ( rs.next() )
    {
      if (taxcount ==0)
      {
       taxItemId1=rs.getString( "item_desc" ) == null ? "" : rs.getString( "item_desc" ).trim();
       taxAmount1=rs.getString( "TAX_AMOUNT" ) == null ? "" : rs.getString( "TAX_AMOUNT" ).trim();
       taxCurrency1=rs.getString( "TAX_CURRENCY_ID" ) == null ? "" : rs.getString( "TAX_CURRENCY_ID" ).trim();
      }
      else if (taxcount ==1)
      {
       taxItemId2=rs.getString( "item_desc" ) == null ? "" : rs.getString( "item_desc" ).trim();
       taxAmount2=rs.getString( "TAX_AMOUNT" ) == null ? "" : rs.getString( "TAX_AMOUNT" ).trim();
       taxCurrency2=rs.getString( "TAX_CURRENCY_ID" ) == null ? "" : rs.getString( "TAX_CURRENCY_ID" ).trim();
      }
      taxcount ++;
    }
  }
  catch ( Exception e )
  {
    //e.printStackTrace();
    //polog.info("error in query "+e.getMessage()+"");
  }
  finally
  {
    if ( dbrs != null ) {dbrs.close();}
  }
if (taxcount > 0)
{
        apout.println( "taxDataDiv = opener.document.getElementById(\"taxDataDiv\");\n" );
        apout.println( "taxDataDiv.style.display=\"\";\n" );

        apout.println( "taxItemId1 = opener.document.getElementById(\"taxItemId1\");\n" );
        apout.println( "taxItemId1.innerHTML = \"" + taxItemId1 + "\";\n" );

        apout.println( "taxAmount1 = opener.document.getElementById(\"taxAmount1\");\n" );
        apout.println( "taxAmount1.innerHTML = \"" + taxAmount1 + "\";\n" );

        apout.println( "taxCurrency1 = opener.document.getElementById(\"taxCurrency1\");\n" );
        apout.println( "taxCurrency1.innerHTML = \"" + taxCurrency1 + "\";\n" );

        apout.println( "taxItemId2 = opener.document.getElementById(\"taxItemId2\");\n" );
        apout.println( "taxItemId2.innerHTML = \"" + taxItemId2 + "\";\n" );

        apout.println( "taxAmount2 = opener.document.getElementById(\"taxAmount2\");\n" );
        apout.println( "taxAmount2.innerHTML = \"" + taxAmount2 + "\";\n" );

        apout.println( "taxCurrency2 = opener.document.getElementById(\"taxCurrency2\");\n" );
        apout.println( "taxCurrency2.innerHTML = \"" + taxCurrency2 + "\";\n" );
}
else
{
            apout.println( "taxDataDiv = opener.document.getElementById(\"taxDataDiv\");\n" );
        apout.println( "taxDataDiv.style.display=\"none\";\n" );

}
        }

        if ( noofinvocies == 0 )
        {
          apout.println( "opener.addOptionItem(opener.document.getElementById(\"invoices\"),'','None');\n" );
        }
        else
        {
          try
          {
            dbrs=db.doQuery( "Select * from supplier_address_view  where supplier = '" + vouchersupplier + "'" );
            rs=dbrs.getResultSet();

            while ( rs.next() )
            {
              String suppliername=rs.getString( "SUPPLIER_NAME" ) == null ? "" : rs.getString( "SUPPLIER_NAME" ).trim();
              String suppliercountryabbrev=rs.getString( "COUNTRY_ABBREV" ) == null ? "" : rs.getString( "COUNTRY_ABBREV" ).trim();
              String supplierstateabbrev=rs.getString( "STATE_ABBREV" ) == null ? "" : rs.getString( "STATE_ABBREV" ).trim();
              String supplieraddressline1=rs.getString( "ADDRESS_LINE_1" ) == null ? "" : rs.getString( "ADDRESS_LINE_1" ).trim();
              String supplieraddressline2=rs.getString( "ADDRESS_LINE_2" ) == null ? "" : rs.getString( "ADDRESS_LINE_2" ).trim();
                                          //String supplieraddressline3=rs.getString( "ADDRESS_LINE_3" ) == null ? "" : rs.getString( "ADDRESS_LINE_3" ).trim();
              String suppliercity=rs.getString( "CITY" ) == null ? "" : rs.getString( "CITY" ).trim();
              String supplierzip=rs.getString( "ZIP" ) == null ? "" : rs.getString( "ZIP" ).trim();
              String supplierphone=rs.getString( "MAIN_PHONE" ) == null ? "" : rs.getString( "MAIN_PHONE" ).trim();

              apout.println( "mfgphoneno = opener.document.getElementById(\"mfgphoneno\");\n" );
              apout.println( "mfgphoneno.value = \"" + supplierphone + "\";\n" );

              apout.println( "selectedRow = opener.document.getElementById(\"supplierid\");\n" );
              apout.println( "selectedRow.value = \"" + vouchersupplier + "\";\n" );

              if ( vouchersupplier.trim().length() > 0 )
              {
                apout.println( "selectedRow = opener.document.getElementById(\"validsupplier\");\n" );
                apout.println( "selectedRow.value = \"Yes\";\n" );
              }
              else
              {
                apout.println( "selectedRow = opener.document.getElementById(\"validsupplier\");\n" );
                apout.println( "selectedRow.value = \"No\";\n" );

                apout.println( "supplierid = opener.document.getElementById(\"supplierid\");\n" );
                apout.println( "supplierid.className = \"INVALIDTEXT\";\n" );
              }

              apout.println( "selectedRow = opener.document.getElementById(\"supplierline1\");\n" );
              apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + suppliername + "</FONT>\";\n" );

              apout.println( "selectedRow = opener.document.getElementById(\"supplierline2\");\n" );
              apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(supplieraddressline1) + "</FONT>\";\n" );

              if ( supplieraddressline2.length() > 0 )
              {
                apout.println( "selectedRow = opener.document.getElementById(\"supplierline3\");\n" );
                apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(supplieraddressline2) + "</FONT>\";\n" );

                apout.println( "selectedRow = opener.document.getElementById(\"supplierline4\");\n" );
                apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + suppliercity + ", " + supplierstateabbrev + " " + supplierzip +  " " + suppliercountryabbrev + "</FONT>\";\n" );
              }
              else
              {
                apout.println( "selectedRow = opener.document.getElementById(\"supplierline3\");\n" );
                apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + suppliercity + ", " + supplierstateabbrev + " " + supplierzip +  " " + suppliercountryabbrev + "</FONT>\";\n" );

				apout.println( "selectedRow = opener.document.getElementById(\"supplierline4\");\n" );
				apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n" );
              }
            }
          }
          catch ( Exception e )
          {
            e.printStackTrace();
          }
          finally
          {
            if ( dbrs != null )
            {
              dbrs.close();
            }
          }

          try
          {
            dbrs=db.doQuery( "select loc.* from customer.location loc where loc.location_id = '" + voucherbillingaddress + "'" );
            rs=dbrs.getResultSet();

            while ( rs.next() )
            {
              String countryabbrev=rs.getString( "COUNTRY_ABBREV" ) == null ? "" : rs.getString( "COUNTRY_ABBREV" ).trim();
              String stateabbrev=rs.getString( "STATE_ABBREV" ) == null ? "" : rs.getString( "STATE_ABBREV" ).trim();
              String addressline1=rs.getString( "ADDRESS_LINE_1" ) == null ? "" : rs.getString( "ADDRESS_LINE_1" ).trim();
              String addressline2=rs.getString( "ADDRESS_LINE_2" ) == null ? "" : rs.getString( "ADDRESS_LINE_2" ).trim();
              //String addressline3 = rs.getString("ADDRESS_LINE_3")==null?"":rs.getString("ADDRESS_LINE_3").trim();
              String city=rs.getString( "CITY" ) == null ? "" : rs.getString( "CITY" ).trim();
              String zip=rs.getString( "ZIP" ) == null ? "" : rs.getString( "ZIP" ).trim();
              String locationdesc=rs.getString( "LOCATION_DESC" ) == null ? "" : rs.getString( "LOCATION_DESC" ).trim();

              apout.println( "voucherbillinglocId = opener.document.getElementById(\"voucherbillinglocId\");\n" );
              apout.println( "voucherbillinglocId.value = \"" + voucherbillingaddress + "\";\n" );        

              apout.println( "selectedRow = opener.document.getElementById(\"shiptoline1\");\n" );
              apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + locationdesc + "</FONT>\";\n" );

              apout.println( "selectedRow = opener.document.getElementById(\"shiptoline2\");\n" );
              apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(addressline1) + "</FONT>\";\n" );

              if ( addressline2.length() > 0 )
              {
                apout.println( "selectedRow = opener.document.getElementById(\"shiptoline3\");\n" );
                apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(addressline2) + "</FONT>\";\n" );

                apout.println( "selectedRow = opener.document.getElementById(\"shiptoline4\");\n" );
                apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + city + ", " + stateabbrev + " " + zip + " " + countryabbrev + "</FONT>\";\n" );
              }
              else
              {
                apout.println( "selectedRow = opener.document.getElementById(\"shiptoline3\");\n" );
                apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + city + ", " + stateabbrev + " " + zip + " " + countryabbrev + "</FONT>\";\n" );

                apout.println( "selectedRow = opener.document.getElementById(\"shiptoline4\");\n" );
                apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n" );
              }
            }
          }
          catch ( Exception e )
          {
            e.printStackTrace();
          }
          finally
          {
            if ( dbrs != null ) { dbrs.close(); }
          }
        }

        if (selvoucherid.trim().length() > 0)
        {
          apout.println( "var myVar = "+selvoucheridindex+";\n" );
          apout.println( "opener.window.purchaseorder.invoices.options[myVar].selected = true;\n" );
        }
      }

      invsession.setAttribute( "INVOICES",ResultV );

      if ( count == 0 )
      {
        apout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
        apout.println( "selectedRow.value = \"" + radianPO + "\";\n" );

        apout.println( "messagerow = opener.document.getElementById(\"messagerow\");\n" );
        apout.println( "messagerow.innerHTML = \"<FONT SIZE=\\\"3\\\" FACE=\\\"Arial\\\" COLOR=\\\"#fc0303\\\"><B>"+res.getString("label.norecordsfoundforpo")+"</B></FONT>\";\n" );
      }
      else if ( "100".equalsIgnoreCase( branch_plant1 ) || "2201".equalsIgnoreCase( branch_plant1 ) )
      {
        apout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
        apout.println( "selectedRow.value = \"" + radianPO + "\";\n" );
        apout.println( "messagerow = opener.document.getElementById(\"messagerow\");\n" );
        apout.println( "messagerow.innerHTML = \"<FONT SIZE=\\\"3\\\" FACE=\\\"Arial\\\" COLOR=\\\"#fc0303\\\"><B>"+res.getString("label.notinventorybasedpo")+"</B></FONT>\";\n" );
	  }
	  else if ( "Y".equalsIgnoreCase( assignCharges ) )
	  {
		apout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
		apout.println( "selectedRow.value = \"" + radianPO + "\";\n" );
		apout.println( "messagerow = opener.document.getElementById(\"messagerow\");\n" );
		apout.println( "messagerow.innerHTML = \"<FONT SIZE=\\\"3\\\" FACE=\\\"Arial\\\" COLOR=\\\"#fc0303\\\"><B>"+res.getString("label.ponotviewable")+"</B></FONT>\";\n" );
	  }
      else
      {
        buildPolineDetails( radianPO,selectedrow,apout );
      }
    }

    Long currentTime = Long.valueOf(System.currentTimeMillis());
    SimpleDateFormat dateFormat =new SimpleDateFormat("HH:mm:ss");

    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    Long elapsed = Long.valueOf(currentTime.longValue() - startTime.longValue() +1000);
    apout.println( "timeElapsed = opener.document.getElementById(\"timeElapsed\");\n" );
    apout.println( "timeElapsed.innerHTML = \"Load Time: "+dateFormat.format(new Date(elapsed.longValue()))+" \";\n" );
                    
    apout.println( "window.close();\n" );
    apout.println( " }\n" );
    apout.println( "// -->\n</SCRIPT>\n" );
    //apout.println( Msgtemp );
    //apout.println( "<BODY><BR><BR>\n" );
    apout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	apout.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
	apout.println( "<INPUT TYPE=\"hidden\" NAME=\"material\" VALUE=\"\">\n" );
	apout.println( "<INPUT TYPE=\"hidden\" NAME=\"revisionDate\" VALUE=\"" + radianPO + "\">\n" );
    apout.println( "<CENTER><BR><BR>\n" );
    apout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\""+res.getString("label.ok")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    apout.println( "</FORM></BODY></HTML>\n" );
    //apout.println( Msgbody );

    //return Msgn;
  }

  public void buildPolineDetails( String radianpo,String selectedRowId,PrintWriter apout )
  {
    //StringBuffer MsgLine=new StringBuffer();
    ResultSet linedetailRs=null;
    DBResultSet lineDetaildbrs=null;
    Vector specFlowv=new Vector();
    Hashtable specflowd=new Hashtable();
    String polinedetail="";

    polinedetail+="select CURRENCY_ID,EXT_PRICE_STRING,RADIAN_PO,UNIT_PRICE,ITEM_ID,ITEM_DESC,ITEM_TYPE,PACKAGING,QTY,EXT_PRICE,SUPPLIER_PACKAGING,SUPPLIER_QTY,SUPPLIER_EXT_PRICE,ADD_CHARGE ";
    polinedetail+=" from po_item_confirm_view where RADIAN_PO = " + radianpo + " order by ADD_CHARGE asc";

    int count=0;
    //DecimalFormat poTotal=new DecimalFormat( "####.00##" );
    //DecimalFormat supplierConv=new DecimalFormat( "####.0000000" );

    try
    {
      lineDetaildbrs=db.doQuery( polinedetail );
      linedetailRs=lineDetaildbrs.getResultSet();

      while ( linedetailRs.next() )
      {
        count++;
        //String radian_po = linedetailRs.getString("RADIAN_PO")==null?"":linedetailRs.getString("RADIAN_PO").trim();
        String item_id=linedetailRs.getString( "ITEM_ID" ) == null ? "" : linedetailRs.getString( "ITEM_ID" ).trim();
        String item_desc=HelpObjs.addescapesForJavascript( linedetailRs.getString( "ITEM_DESC" ) == null ? "" : linedetailRs.getString( "ITEM_DESC" ).trim() );
        //String item_type = linedetailRs.getString("ITEM_TYPE")==null?"":linedetailRs.getString("ITEM_TYPE").trim();
        String packaging=HelpObjs.addescapesForJavascript( linedetailRs.getString( "PACKAGING" ) == null ? "" : linedetailRs.getString( "PACKAGING" ).trim() );
        String quantity=linedetailRs.getString( "QTY" ) == null ? "" : linedetailRs.getString( "QTY" ).trim();
        String ext_price=linedetailRs.getString( "EXT_PRICE" ) == null ? "" : linedetailRs.getString( "EXT_PRICE" ).trim();
        String supplier_pkg=linedetailRs.getString( "SUPPLIER_PACKAGING" ) == null ? "" : linedetailRs.getString( "SUPPLIER_PACKAGING" ).trim();
        String supplier_qty=linedetailRs.getString( "SUPPLIER_QTY" ) == null ? "" : linedetailRs.getString( "SUPPLIER_QTY" ).trim();
        String supplier_ext_price=linedetailRs.getString( "SUPPLIER_EXT_PRICE" ) == null ? "" : linedetailRs.getString( "SUPPLIER_EXT_PRICE" ).trim();
        String add_charge=linedetailRs.getString( "ADD_CHARGE" ) == null ? "" : linedetailRs.getString( "ADD_CHARGE" ).trim();
        String unitPrice=linedetailRs.getString( "UNIT_PRICE" ) == null ? "0" : linedetailRs.getString( "UNIT_PRICE" ).trim();
		String extpricestring=linedetailRs.getString( "EXT_PRICE_STRING" ) == null ? "" : linedetailRs.getString( "EXT_PRICE_STRING" ).trim();
		String currencyid=linedetailRs.getString( "CURRENCY_ID" ) == null ? "" : linedetailRs.getString( "CURRENCY_ID" ).trim();

        String Color=" ";
        String itemColor=" ";

        if ( count % 2 == 0 )
        {
          Color="CLASS=\"blue\"";
          itemColor="INVISIBLEHEADBLUE";
        }
        else
        {
          Color="CLASS=\"white\"";
          itemColor="INVISIBLEHEADWHITE";
        }

        apout.println( "opener.addLineItem(); \n" );
        apout.println( "linenumber = opener.document.getElementById(\"cell1" + count + "\");\n" );
        apout.println( "linenumber.innerHTML = \"" + item_id + "\";\n" );
        //apout.println("linenumber.innerHTML = \"<FONT COLOR=\\\"##0000ff\\\">"+item_id+"</FONT>\";\n");
        apout.println( "itemidindetail = opener.document.getElementById(\"itemidindetail" + count + "\");\n" );
        apout.println( "itemidindetail.value = \"" + item_id + "\";\n" );

        apout.println( "itemtype = opener.document.getElementById(\"itemtype" + count + "\");\n" );
        apout.println( "itemtype.value = \"" + add_charge + "\";\n" );

		if ( this.getupdateStatus() )
		{
		  if ( "Y".equalsIgnoreCase( add_charge ) )
		  {
			apout.println( "addmaterialrma = opener.document.getElementById(\"addmaterialrma" + count + "\");\n" );
			apout.println( "addmaterialrma.disabled=true;\n" );
			apout.println( "addmaterialrma.style.display=\"none\";\n" );
		  }
		  else
		  {
			apout.println( "addadchrgreturn = opener.document.getElementById(\"addadchrgreturn" + count + "\");\n" );
			apout.println( "addadchrgreturn.disabled=true;\n" );
			apout.println( "addadchrgreturn.style.display=\"none\";\n" );
		  }
		}

        apout.println( "polineunitprice = opener.document.getElementById(\"polineunitprice" + count + "\");\n" );
        apout.println( "polineunitprice.value = \"" + unitPrice + "\";\n" );

        apout.println( "selectedRow = opener.document.getElementById(\"cell2" + count + "\");\n" );
        apout.println( "selectedRow.innerHTML = \"" + item_desc + "\";\n" );

        apout.println( "selectedRow = opener.document.getElementById(\"cell3" + count + "\");\n" );
        apout.println( "selectedRow.innerHTML = \"" + packaging + "\";\n" );

        apout.println( "selectedRow = opener.document.getElementById(\"cell4" + count + "\");\n" );
        apout.println( "selectedRow.innerHTML = \"" + quantity + "\";\n" );

        BigDecimal totalextprice=new BigDecimal("0");
        if ( ext_price.length() > 0 )
        {
          totalextprice=new BigDecimal( ext_price );
        }

        BigDecimal totalsuppextprice=new BigDecimal("0");
        if ( supplier_ext_price.length() > 0 )
        {
          totalsuppextprice=new BigDecimal( supplier_ext_price );
        }

        BigDecimal ourQty=new BigDecimal("0");
        BigDecimal supplierQty=new BigDecimal("0");
        String supplierConversion="";

        if ( quantity.length() > 0 )
        {
          ourQty=new BigDecimal( quantity );
        }

        if ( supplier_qty.length() > 0 )
        {
          supplierQty=new BigDecimal( supplier_qty );
        }

        if ( supplierQty.compareTo(new BigDecimal("0")) == 1 )
        {
          supplierConversion=res.getString("label.supplierconversion")+": ";
          if ( ourQty.divide(supplierQty,4).equals(new BigDecimal("0")))
          {
            supplierConversion+="" + ourQty.divide(supplierQty,4) + res.getString("label.items")+" = 1 " + supplier_pkg + "   ...   ";
          }
          else if ( supplierQty.divide(ourQty,4).equals(new BigDecimal("0")) )
          {
            supplierConversion+="1 "+res.getString("label.item")+" = " + supplierQty.divide(ourQty,4) + " " + supplier_pkg + "   ...   ";
          }
          else
          {
            supplierConversion+="" + ourQty + res.getString("label.items")+" = " + supplierQty + " " + supplier_pkg + "   ...   ";
          }

          supplierConversion+=res.getString("label.multiplysupplierqty")+" " + ourQty.divide(supplierQty,4).setScale(7,4) + "   ...   ";
          supplierConversion+=res.getString("label.divideSupplierunitprice")+" " + ourQty.divide(supplierQty,4).setScale(7,4) + "   ...   ";
        }

        apout.println( "linetotalprice = opener.document.getElementById(\"cell6" + count + "\");\n" );
        apout.println( "linetotalprice.innerHTML = \"" + extpricestring + " "+currencyid+"\";\n" );

        apout.println( "selectedRow = opener.document.getElementById(\"cell7" + count + "\");\n" );
        apout.println( "selectedRow.innerHTML = \"" + supplier_pkg + "\";\n" );

        apout.println( "selectedRow = opener.document.getElementById(\"cell8" + count + "\");\n" );
        apout.println( "selectedRow.innerHTML = \"" + supplier_qty + "\";\n" );

		String suppextprice = "";
		if (!totalsuppextprice.equals(new BigDecimal("0")))
		{
		  suppextprice =  totalsuppextprice.setScale(4,4) + " "+currencyid;
		}
        apout.println( "supplierextprice = opener.document.getElementById(\"cell10" + count + "\");\n" );
        apout.println( "supplierextprice.innerHTML = \"" + suppextprice +"\";\n" );

        specflowd=new Hashtable();
        specflowd.put( "LINE","" + count + "" );
        specflowd.put( "ITEMID","" + item_id + "" );
        specflowd.put( "SUPPCONVERSION","" + supplierConversion + "" );
        specFlowv.addElement( specflowd );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
    finally
    {
      if ( lineDetaildbrs != null )
      {
        lineDetaildbrs.close();
      }
    }

    apout.println( "selectedRow = opener.document.getElementById(\"totallines\");\n" );
    apout.println( "selectedRow.value = \"" + count + "\";\n" );

    if ( count > 0 )
    {
      if ( selectedRowId.trim().length() == 0 )
      {
        selectedRowId="row1";
      }

      apout.println( "selecTedHere = \"" + selectedRowId + "\";\n" );
      apout.println( "try\n{\n" );
      apout.println( "selectedRow1 = opener.document.getElementById(\"" + selectedRowId + "\");\n" );
      apout.println( "selectedRow1.style.backgroundColor = \"#8a8aff\";\n" );
      apout.println( "}\n" );
      apout.println( "catch (ex)\n{\n" );
      apout.println( "selectedRow1 = opener.document.getElementById(\"row1\");\n" );
      apout.println( "selectedRow1.style.backgroundColor = \"#8a8aff\";\n" );
      apout.println( "selecTedHere = \"row1\";\n" );
      apout.println( "}\n" );

      apout.println( "selectedrowid = opener.document.getElementById(\"selectedrowid\");\n" );
      apout.println( "selectedrowid.value = selecTedHere;\n" );

      apout.println( "opener.selected_rowid = selecTedHere; \n" );

      apout.println( "var target1 = opener.document.getElementById(\"div\"+selecTedHere+\"\");\n" );
      apout.println( "target1.style.display = \"block\";\n" );
    }

    for ( int specflowLine=0; specflowLine < specFlowv.size(); specflowLine++ )
    {
      Hashtable specflowLineData=new Hashtable();
      specflowLineData= ( Hashtable ) specFlowv.elementAt( specflowLine );

      String lineItemid=specflowLineData.get( "ITEMID" ).toString().trim();
      String linsinJs=specflowLineData.get( "LINE" ).toString().trim();
      String suppConvesio=specflowLineData.get( "SUPPCONVERSION" ).toString().trim();

      if ( ( lineItemid.length() > 0 ) )
      {
        boolean updateststus=false;
        try
        {
          updateststus=this.getupdateStatus();
        }
        catch ( Exception ex )
        {
        }

		boolean shoonlyopen=true;
		try
		{
		  shoonlyopen=this.getallinvocieStatus();
		}
		catch ( Exception ex )
		{
		}

        radian.web.apHelpObj.builditemmatching( db,radianpo,lineItemid,linsinJs,suppConvesio,updateststus,true,apout,res);
        radian.web.apHelpObj.buildreceiptmatching( db,radianpo,lineItemid,linsinJs,updateststus,shoonlyopen,apout,res );
      }
    }
    //return MsgLine;
  }

  protected void printJavaScripts(PrintWriter apout)
  {
    //StringBuffer Msglt=new StringBuffer();
    apout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    apout.println( "<!--\n" );
    apout.println( "function cancel()\n" );
    apout.println( "{\n" );
    apout.println( "window.close();\n" );
    apout.println( "}\n" );
    //return Msglt;
  }
}