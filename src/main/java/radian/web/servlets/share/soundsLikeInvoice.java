package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
//import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import java.util.Collection;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 10-29-02
 * Add a check on submit for address change to check for State abbrivation which was causing some errors
 * as people entered wrong state codes for the US
 *
 * added the trim() method for all request.getparameter as I wanted to strip them of leading spaces.
 *
 * Increased the font size for the Date pickers.
 *
 * 11-20-02
 * making changes to add supplier billing address
 *
 * 11-25-02
 * don't let the user 'Edit' the 'Remit To'
 *
 * 11-26-02
 * adding refused amount and reorganize fields
 *
 * 12-14-02
 * adding invoice reference to add invoice line and update invoice line
 * 04-22-03 - apnote was not being changed to override the " \n and stuff like that so callinh helpopbjs.addspacesforjavascript method
 * 04-24-03 - addescapesForJavascript for supplier ID
 * 04-28-03 - Changing new supplier billing location id from BILLING to BILL..
 * 05-19-03 - Using double instead of floats clenup code and added Date sent for payment and invoice url
 * 07-10-03 - Code Clanup and adding the option to choose a different supplier to enter the invoice if there are multiple supplier associated with a po
 * 08-02-03 - Making the selected invoice on the page the default selected one
 * 08-06-03 - Geting the voucherbillingaddress from the voucher table instead of the supplier table which was the old way of doing it.
 * 08-12-03 - If there is a carrier supplier id on a PO and the first invoice entered is for the carrier it does not show the other supplier. Fixed it.
 * 08-19-03 - Sending Error emails
 * 10-23-03 - Cannot change billing address after voucher sent to Haas
 * 12-22-03 - Fixed table borders overlaping
 * 03-02-04 - Changes to allow additional charge RMA Credit
 * 05-06-04 - Showing multiple suppliers if they exist from a query, not restricting to only to hass carriers
 * 08-17-04 - Changed the invocie URL Query
 * 03-02-05 - Showing the Currency ID and allowing to choose a currency when Editing / Entering an Invoice
 * 08-22-05 - Change the invoice number on AP page if invoice is 'In Progress'
 */

public class soundsLikeInvoice
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    //private DBResultSet dbrs = null;
    //private ResultSet rs = null;
    private boolean allowupdate;
    private String nematid_Servlet = "";
    private String privatepersonnelId = "";
		private boolean voucherQcUpdate;
		private boolean resetInvoiceStatus;
		private boolean intcmIsApplication = false;

	private static org.apache.log4j.Logger polog = org.apache.log4j.Logger.getLogger(soundsLikeInvoice.class);

    public soundsLikeInvoice(ServerResourceBundle b, TcmISDBModel d)
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

     public void doResult(HttpServletRequest request, HttpServletResponse response,PrintWriter out) throws ServletException,  IOException
    {
    //PrintWriter out = response.getWriter();
    response.setContentType("text/html");
    HttpSession invoicesession = request.getSession();
    privatepersonnelId= invoicesession.getAttribute( "PERSONNELID" ) == null ? "" : invoicesession.getAttribute( "PERSONNELID" ).toString();
		PersonnelBean personnelBean = invoicesession.getAttribute("personnelBean") == null ? null : (PersonnelBean) invoicesession.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		intcmIsApplication = false;
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    String polineunitprice=request.getParameter( "polineunitprice" );
    if ( polineunitprice == null )
      polineunitprice="";
    polineunitprice=polineunitprice.trim();

    String notvouchered=request.getParameter( "notvouchered" );
    if ( notvouchered == null )
      notvouchered="";
    notvouchered=notvouchered.trim();

    String invoiceId=request.getParameter( "invoiceId" );
    if ( invoiceId == null )
      invoiceId="";
    invoiceId=invoiceId.trim();

    String radianpo=request.getParameter( "radianpo" );
    if ( radianpo == null )
      radianpo="";
    radianpo=radianpo.trim();

    String itemID=request.getParameter( "itemID" );
    if ( itemID == null )
      itemID="";
    itemID=itemID.trim();

    String voucherbillinglocId=request.getParameter( "voucherbillinglocId" );
    if ( voucherbillinglocId == null )
      voucherbillinglocId="";
    voucherbillinglocId=voucherbillinglocId.trim();

    String newbillingId=request.getParameter( "newbillingId" );
    if ( newbillingId == null )
      newbillingId="";
    newbillingId=newbillingId.trim();

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action=Action.trim();

    String Ordertaker=request.getParameter( "Ordertaker" );
    if ( Ordertaker == null )
      Ordertaker="";
    Ordertaker=Ordertaker.trim();

    String suppID=request.getParameter( "suppID" );
    if ( suppID == null )
      suppID="";
    suppID=suppID.trim();

    String haascarrier=request.getParameter( "haascarrier" );
    if ( haascarrier == null )
      haascarrier="";
    haascarrier=haascarrier.trim();

    String carriersuppid=request.getParameter( "carriersuppid" );
    if ( carriersuppid == null )
      carriersuppid="";
    carriersuppid = carriersuppid.trim();

    //polog.info("Here suppID  " + suppID + " Action   "+Action+"  Ordertaker  "+Ordertaker+"  invoiceId  "+invoiceId+"    carriersuppid   "+carriersuppid);

    if ("searchbillingAddress".equalsIgnoreCase(Action))
    {
      String withUpdate = "";
      try{withUpdate = request.getParameter("withUpdate").toString().trim();}catch (Exception e1){withUpdate = "";}
      buildSearchBillingAddres(suppID,withUpdate,out,"Approved");
    }
    else if ("getInvoiceData".equalsIgnoreCase(Action))
    {
    buildsendInvocieDetails(personnelBean,invoiceId,radianpo,"Yes",out);
    }
    else if ("Update".equalsIgnoreCase(Action))
    {
    updateInvoice(personnelBean,request,out);
    }
    else if ("undoReceiptInvocieLine".equalsIgnoreCase(Action))
    {
    undoReceiptInvocieLine(request,out);
    }
    else if ("undoInvocieLine".equalsIgnoreCase(Action))
    {
    undoInvocieLine(request,out);
    }
    else if ("Updatebillingaddress".equalsIgnoreCase(Action))
    {
    updatebillingaddress(request,out);
    }
    else if ("updateaddcorrectionline".equalsIgnoreCase(Action))
    {
    updateaddcorrectionline(request,out);
    }
    else if ("updateaddinvoiceline".equalsIgnoreCase(Action))
    {
    updateaddinvoiceline(request,out);
    }
    else if ("updatermaline".equalsIgnoreCase(Action))
    {
    updatermaline(request,out);
    }
	else if ("updateaddchgline".equalsIgnoreCase(Action))
	{
	updateaddchgline(request,out);
	}
    else if ("addinvoiceLine".equalsIgnoreCase(Action))
    {
     buildaddinvoiceLine(suppID,itemID,radianpo,polineunitprice,notvouchered,invoiceId,out);
    }
    else if ("addcreditcorrectionline".equalsIgnoreCase(Action))
    {
     addcreditcorrectionline(suppID,itemID,radianpo,invoiceId,out);
    }
    else if ("addrmaline".equalsIgnoreCase(Action))
    {
     addrmaline(suppID,itemID,radianpo,invoiceId,out);
    }
	else if ("addaddchgrtn".equalsIgnoreCase(Action))
	{
	 addaddchgrtn(suppID,itemID,radianpo,invoiceId,out);
	}
    else if ("editbillingAddress".equalsIgnoreCase(Action))  //new
		{
		 Hashtable initialstData = new Hashtable ();
		 try {
			initialstData = radian.web.poHelpObj.getNewSupplierInitialData(db);
		 }
		 catch (Exception ex) {
		 }
		 invoicesession.setAttribute( "STATE_COUNTRY",initialstData);
		 Hashtable vvcountrystate= ( Hashtable ) invoicesession.getAttribute( "STATE_COUNTRY" );
		 buildeditBillingadd(suppID,invoiceId,radianpo,voucherbillinglocId,newbillingId,out,vvcountrystate);
    }
    else if ("showinvoicesummary".equalsIgnoreCase(Action))
    {
    showinvoicesummary(suppID,invoiceId,radianpo,out);
    }
    else if ("editinvoice".equalsIgnoreCase(Action))
    {
    Vector vvPayment = (Vector)invoicesession.getAttribute("VV_PAYMENT");
	Vector vvCurrency= ( Vector ) invoicesession.getAttribute( "VV_CURRENCY" );

    buildNewInvoice(suppID,invoiceId,vvPayment,radianpo,haascarrier,carriersuppid,out,vvCurrency);
    }
    else if ("deletethis".equalsIgnoreCase(Action))
    {
    builddeletethis(suppID,invoiceId,out);
    }
    else if ("New".equalsIgnoreCase(Action))
    {
     Vector vvPayment = (Vector)invoicesession.getAttribute("VV_PAYMENT");
	 Vector vvCurrency= ( Vector ) invoicesession.getAttribute( "VV_CURRENCY" );

     buildNewInvoice(suppID,invoiceId,vvPayment,radianpo,haascarrier,carriersuppid,out,vvCurrency);
    }
    else if ("billingAddressVerified".equalsIgnoreCase(Action))
    {
      String voucherID = "";
      try{voucherID = request.getParameter("voucherID").toString().trim();}catch (Exception e1){voucherID = "";}
      billingAddressVerified(voucherID,out);
    }
    else if ("billingAddressUpdateVerified".equalsIgnoreCase(Action))
    {
      String voucherID = "";
      String voucherBillingLocID = "";
      try{voucherID = request.getParameter("voucherID").toString().trim();}catch (Exception e1){voucherID = "";}
      try{voucherBillingLocID = request.getParameter("voucherBillLocID").toString().trim();}catch (Exception e1){voucherBillingLocID = "";}
      billingAddressUpdateVerified(voucherID,voucherBillingLocID,out);
    }
    else if ("updateSupplierBillingLocation".equalsIgnoreCase(Action))
    {
        String supplier = "";
        String supplierBillingLocationId = "";
        String withUpdate = "";
        String locationStatus = "";
        String sapVendorCode = "";
        try{supplier = request.getParameter("supplier").toString().trim();}catch (Exception e1){supplier = "";}
        try{supplierBillingLocationId = request.getParameter("supplierBillingLocationId").toString().trim();}catch (Exception e1){supplierBillingLocationId = "";}
        try{withUpdate = request.getParameter("withUpdate").toString().trim();}catch (Exception e1){withUpdate = "";}
        try{locationStatus = request.getParameter("locationStatus").toString().trim();}catch (Exception e1){locationStatus = "";}
        try{sapVendorCode = request.getParameter("sapVendorCode").toString().trim();}catch (Exception e1){sapVendorCode = "";}
        updateSupplierBillingLocation(supplier,supplierBillingLocationId,locationStatus,withUpdate,sapVendorCode,out);
    }else if ("showLocation".equalsIgnoreCase(Action))
    {
        String withUpdate = "";
        String locationStatus = "";
        try{
            withUpdate = request.getParameter("withUpdate").toString().trim();
            locationStatus = request.getParameter("locationStatus").toString().trim();
        }catch (Exception e1){
            withUpdate = "";
            locationStatus = "";
        }
        buildSearchBillingAddres(suppID,withUpdate,out,locationStatus);
    }
    
    out.close();
  }

    public static Vector getTaxItems(TcmISDBModel dbObj3)
   {
	 String query= "";
	 query="select * from tax_item_view order by ITEM_DESC asc";

	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 Hashtable result=null;
	 Vector ResultV=new Vector();
	 try
	 {
	   dbrs=dbObj3.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
		 result=new Hashtable();
		 String faci= ( rs.getString( "ITEM_DESC" ) == null ? "" : rs.getString( "ITEM_DESC" ) );
		 String facn  = (rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID"));
		 result.put( faci,facn );
		 ResultV.addElement( result );
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
	 return ResultV;
   }

  public void billingAddressUpdateVerified( String voucherID,String voucherBillingLocID,PrintWriter apout )
  {
    //StringBuffer Msg=new StringBuffer( "" );
    if ( voucherID.length() > 0 )
    {
      try
      {
        db.doUpdate( "update voucher set verified = 'y',voucher_billing_location = '" + voucherBillingLocID + "' where voucher_id = " + voucherID );
        //checkSapVendorCode(voucherBillingLocID);
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
        polog.info("Cannot update voucher "+e.getMessage()+"");
      }
    }
    apout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Billing Address Search","appopopups.js",intcmIsApplication ) );
    apout.println( "<FORM METHOD=\"POST\" name=\"editbillingAddress\" action=\"Testing\">\n" );
    apout.println( "<BODY onLoad=\"updateVerifiedBillingAddressComplete()\">\n" );
    apout.println( "</BODY></FORM></HTML>\n" );
    return;
  }

  public void billingAddressVerified( String voucherID,PrintWriter apout )
  {
    //StringBuffer Msg=new StringBuffer( "" );
    if ( voucherID.length() > 0 )
    {
      try
      {
        db.doUpdate( "update voucher set verified = 'y' where voucher_id = " + voucherID );
      }
      catch ( Exception e )
      {
        polog.info("Cannot update voucher "+e.getMessage()+"");
        //e.printStackTrace();
      }
    }
    apout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Billing Address Verified","appopopups.js",intcmIsApplication ) );
    apout.println( "<FORM METHOD=\"POST\" name=\"editbillingAddress\" action=\"Testing\">\n" );
    apout.println( "<BODY onLoad=\"updateVerifiedBillingAddressComplete()\">\n" );
    apout.println( "</BODY></FORM></HTML>\n" );
    return;
  }

  public void updatebillingaddress(HttpServletRequest request1,PrintWriter apout)
  {
    //StringBuffer Msgn = new StringBuffer();

    String suppID = "";
    try{suppID = request1.getParameter("suppID").toString().trim();}catch (Exception e1){suppID = "";}

    String billinglocationid = "";
    try{billinglocationid = request1.getParameter("billinglocationid").toString().trim();}catch (Exception e1){billinglocationid = "";}

    String locationdesc = "";
    try{locationdesc = request1.getParameter("locationdesc").toString().trim();}catch (Exception e1){locationdesc = "";}
		//locationdesc = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(locationdesc);

    String addressline1 = "";
    try{addressline1 = request1.getParameter("addressline1").toString().trim();}catch (Exception e1){addressline1 = "";}

    String addressline2 = "";
    try{addressline2 = request1.getParameter("addressline2").toString().trim();}catch (Exception e1){addressline2 = "";}

    String addressline3 = "";
    try{addressline3 = request1.getParameter("addressline3").toString().trim();}catch (Exception e1){addressline3 = "";}

    String city = "";
    try{city = request1.getParameter("city").toString().trim();}catch (Exception e1){city = "";}

    String stateabbrev = "";
    try{stateabbrev = request1.getParameter("state_abbrev").toString().trim();}catch (Exception e1){stateabbrev = "";}
		if ("None".equalsIgnoreCase(stateabbrev)){stateabbrev = "";}

    String zip = "";
    try{zip = request1.getParameter("zip").toString().trim();}catch (Exception e1){zip = "";}

    String zipplus = "";
    try{zipplus = request1.getParameter("zipplus").toString().trim();}catch (Exception e1){zipplus = "";}
    if (zipplus.trim().length() > 0)
    {
      zip = zip + "-" + zipplus;
    }
    String countryabbrev = "";
    try{countryabbrev = request1.getParameter("country_abbrev").toString().trim();}catch (Exception e1){countryabbrev = "";}

    String newbillinglocationid = "";
    try{newbillinglocationid = request1.getParameter("newbillinglocationid").toString().trim();}catch (Exception e1){newbillinglocationid = "";}
    newbillinglocationid = newbillinglocationid.trim();

    boolean updatesucess = false;
    try
    {
	if ( newbillinglocationid.trim().length() > 0 )
        {
            if ( suppID.length() > 0 )
            {
              //11-25-02 first insert into parent table    
              String insertnewloc = "";
              insertnewloc = "insert into customer.location (COUNTRY_ABBREV,STATE_ABBREV,ADDRESS_LINE_1,ADDRESS_LINE_2,ADDRESS_LINE_3,CITY,ZIP,LOCATION_DESC,LOCATION_ID,COMPANY_ID) values ('"+countryabbrev+"','"+stateabbrev+"','"+addressline1.replaceAll("'", "''")+"','"+addressline2.replaceAll("'", "''")+"','"+addressline3.replaceAll("'", "''")+"','"+city.replaceAll("'", "''")+"','"+zip+"','"+locationdesc.replaceAll("'", "''")+"','"+newbillinglocationid+"','SUPPLIER') ";
              db.doUpdate(insertnewloc);
              //then insert into child table
              String insertnewBillingid = "";
              insertnewBillingid = "insert into supplier_billing_location (SUPPLIER,BILLING_LOCATION_ID,STATUS,REQUESTER,REQUEST_DATE) values ('"+suppID+"','"+newbillinglocationid+"','Open',"+privatepersonnelId+",sysdate)";
              db.doUpdate(insertnewBillingid);
              sendnoticemail(newbillinglocationid);
            }
        }
        else
        {
            if ( (billinglocationid.length() > 0) && (suppID.length() > 0) )
            {
              String updItemquery = "";
              updItemquery = "update customer.location set COUNTRY_ABBREV='"+countryabbrev+"',STATE_ABBREV='"+stateabbrev+"',ADDRESS_LINE_1='"+addressline1.replaceAll("'", "''")+"',ADDRESS_LINE_2='"+addressline2.replaceAll("'", "''")+"',ADDRESS_LINE_3='"+addressline3.replaceAll("'", "''")+"',CITY='"+city.replaceAll("'", "''")+"',ZIP='"+zip+"',LOCATION_DESC='"+locationdesc.replaceAll("'", "''")+"' where LOCATION_ID = '"+billinglocationid+"' ";

              db.doUpdate(updItemquery);
            }
        }
      updatesucess = true;
    }
    catch (Exception e)
    {
	//e.printStackTrace();
  polog.info("Cannot update location "+e.getMessage()+"");
  updatesucess = false;
    }

    //StringBuffer Msgbody = new StringBuffer();
    nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",intcmIsApplication));
    printJavaScripts(apout);
    apout.println("// -->\n </SCRIPT>\n");

    //StringBuffer Msgtemp = new StringBuffer();

    apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    apout.println("<!--\n");

    apout.println("function sendSupplierData()\n");
    apout.println("{\n");

    if ( newbillinglocationid.trim().length() > 0 )
    {
      apout.println("voucherbillinglocId = opener.document.getElementById(\"voucherbillinglocId\");\n");
      apout.println("voucherbillinglocId.value = '"+newbillinglocationid+"';\n");
    }

    apout.println("selectedRow = opener.document.getElementById(\"shiptoline1\");\n");
    apout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+locationdesc+"</FONT>\";\n");

    apout.println("selectedRow = opener.document.getElementById(\"shiptoline2\");\n");
    apout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+addressline1+"</FONT>\";\n");

    if (addressline2.length() >0)
    {
      apout.println("selectedRow = opener.document.getElementById(\"shiptoline3\");\n");
      apout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+addressline2+"</FONT>\";\n");

      apout.println("selectedRow = opener.document.getElementById(\"shiptoline4\");\n");
      apout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+city+", "+stateabbrev+" "+zip+" "+countryabbrev+"</FONT>\";\n");
    }
    else
    {
      apout.println("selectedRow = opener.document.getElementById(\"shiptoline3\");\n");
      apout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+city+", "+stateabbrev+" "+zip+" "+countryabbrev+"</FONT>\";\n");

      apout.println("selectedRow = opener.document.getElementById(\"shiptoline4\");\n");
      apout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n");
    }

    apout.println("window.close();\n");
    apout.println(" }\n");

    apout.println("// -->\n</SCRIPT>\n");
    //apout.println(Msgtemp);
    if (updatesucess)
    {
      apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
    }
    else
    {
      apout.println("<BODY><BR><BR>\n");
      apout.println("An Error Occured Please Check Your Data and Try Again.\n");
    }
	apout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<CENTER><BR><BR>\n");
    apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    apout.println("</FORM></BODY></HTML>\n");
    //apout.println(Msgbody);

    return;

  }


  public void checkSapVendorCode(String supplierBillingLocation,String supplierId)
  {
    try
    {
      int testnumber=DbHelpers.countQuery( db,"select count(*) from supplier_billing_location where SUPPLIER='"+supplierId+"' and BILLING_LOCATION_ID = '"+supplierBillingLocation+"' and sap_vendor_code is null and REQUESTER is null" );
      if ( testnumber > 0 )
      {
        String updItemquery = "";
        updItemquery = "update supplier_billing_location set STATUS='Open',REQUESTER="+privatepersonnelId+",REQUEST_DATE=sysdate where SUPPLIER='"+supplierId+"' AND BILLING_LOCATION_ID = '"+supplierBillingLocation+"' ";
        db.doUpdate(updItemquery);
        sendnoticemail(supplierBillingLocation);
      }       
    }
    catch (Exception e)
    {
	//e.printStackTrace();
    }
  }

    private void sendnoticemail(String supplierBillingLocation)
    {
     DBResultSet dbrs=null;
	 ResultSet rs=null;
     String billAddress= "";
	 String supplierName = "";
     String requestorName = "";
     try
	 {
	   dbrs=db.doQuery( "   select * from SUPPLIER_BILLING_LOCATION_VIEW where billing_location_id = '"+supplierBillingLocation+"'" );
	   rs=dbrs.getResultSet();
	   while ( rs.next() )
	   {
         billAddress= ( rs.getString( "TCM_SUPPLIER_BILL_ADDRESS" ) == null ? "" : rs.getString( "TCM_SUPPLIER_BILL_ADDRESS" ) );
		 supplierName  = (rs.getString("SUPPLIER_NAME")==null?"":rs.getString("SUPPLIER_NAME"));
         requestorName  = (rs.getString("REQUESTER_NAME")==null?"":rs.getString("REQUESTER_NAME"));
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

    String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	wwwHome = wwwHome.substring(0, wwwHome.length() - 1);
	String resulturl ="https://www.tcmis.com/tcmIS/supply/newremittomain.do";

	String emailArray = "";
	String query="select distinct a.personnel_id,b.email from user_group_member a, personnel b where a.personnel_id = b.personnel_id and a.user_group_id = 'newRemitTo'";
	dbrs=null;
	rs=null;
	int loop = 0;

	try
	{
	  dbrs=db.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		String faci=rs.getString( "email" ) != null ? rs.getString( "email" ) : "";
		if ( loop > 0 )
		{
		  emailArray+=",";
		}

	    if (faci.length() > 0 )
		{
		  emailArray += ""+faci+"";
		  loop++;
		}
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ){dbrs.close();}
	}

	String[] statusaray = new String[] {emailArray};
	radian.tcmis.server7.share.helpers.HelpObjs.javaSendMail("","New Remit To address entered for supplier "+supplierName+" waiting approval",
            statusaray,"New Remit To address entered for supplier "+supplierName+" waiting approval.\n\nAddress: "+billAddress+" \n\nPlease click on the link below.\n\n\n"+resulturl+"\n\n\n\n\n"+supplierBillingLocation+"");
  }

  public void updateaddcorrectionline(HttpServletRequest request1,PrintWriter apout)
  {
    //StringBuffer Msgn = new StringBuffer();

    String suppID = "";
    try{suppID = request1.getParameter("suppID").toString().trim();}catch (Exception e1){suppID = "";}

    String radianpo = "";
    try{radianpo = request1.getParameter("radianpo").toString().trim();}catch (Exception e1){radianpo = "";}

    String invoices = "";
    try{invoices = request1.getParameter("invoices").toString().trim();}catch (Exception e1){invoices = "";}
    invoices = invoices.trim();

    String invoicecreditqty = "";
    try{invoicecreditqty = request1.getParameter("invoicecreditqty").toString().trim();}catch (Exception e1){invoicecreditqty = "";}

    String invoiceCreditUnitPrice = "";
    try{invoiceCreditUnitPrice = request1.getParameter("invcreditunitprice").toString().trim();}catch (Exception e1){invoiceCreditUnitPrice = "";}

    //DecimalFormat unitpriceformat =new DecimalFormat( "####.000###" );
		if (invoiceCreditUnitPrice.trim().length() >0)
		{
			BigDecimal invCreditUnitPrice = new BigDecimal(invoiceCreditUnitPrice);
			invoiceCreditUnitPrice = "" + invCreditUnitPrice.setScale(6,4) + "";
	    //unitpriceformat.format( Double.parseDouble( invcreditunitprice ) );
		}

    String invoicedebitqty = "";
    try{invoicedebitqty = request1.getParameter("invoicedebitqty").toString().trim();}catch (Exception e1){invoicedebitqty = "";}

    String invoiceDebitUnitPrice = "";
    try{invoiceDebitUnitPrice = request1.getParameter("invdebitunitprice").toString().trim();}catch (Exception e1){invoiceDebitUnitPrice = "";}
		if (invoiceDebitUnitPrice.trim().length() >0)
		{
			BigDecimal invDebitUnitPrice = new BigDecimal(invoiceDebitUnitPrice);
			invoiceDebitUnitPrice = "" + invDebitUnitPrice.setScale(6,4) + "";
	    //unitpriceformat.format( Double.parseDouble( invcreditunitprice ) );
		}
    //invdebitunitprice = unitpriceformat.format( Double.parseDouble( invdebitunitprice ) );

    String itemID = "";
    try{itemID = request1.getParameter("itemID").toString().trim();}catch (Exception e1){itemID = "";}
    itemID = itemID.trim();

    //12-14-02
    String invoiceReference = "";
    try{invoiceReference = request1.getParameter("invoiceReference").toString().trim();}catch (Exception e1){invoiceReference = "";}
    invoiceReference = invoiceReference.trim();

    boolean updatesucess=false;
	DBResultSet dbrs = null;
    ResultSet rs = null;
    try
    {
      //insert row for credit
      dbrs=db.doQuery( "select nvl(max(VOUCHER_LINE),0)+1 VOUCHER_LINE from VOUCHER_LINE where VOUCHER_ID = '" + invoices + "' " );
      rs=dbrs.getResultSet();
      String voucherline="";
      while ( rs.next() )
      {
        voucherline=rs.getString( "VOUCHER_LINE" ) == null ? "" : rs.getString( "VOUCHER_LINE" ).trim();
      }

      String tmpCredit="Y";
      if ( invoicecreditqty.length() > 0 && invoicedebitqty.length() > 0 )
      {
        tmpCredit="N";
      }

      //12-19-02 getting voucher_credit_seq so I can tell which pair goes with which
      dbrs=db.doQuery( "select voucher_credit_seq.nextval voucher_pair_seq from dual" );
      rs=dbrs.getResultSet();
      String voucherPairSeq="";
      while ( rs.next() )
      {
        voucherPairSeq=rs.getString( "voucher_pair_seq" ) == null ? "" : rs.getString( "voucher_pair_seq" ).trim();
      }

      if ( ( invoices.length() > 0 ) && ( itemID.length() > 0 ) )
      {
        String updItemquery="";
        //12-14-02
        if ( invoiceReference.length() > 0 )
        {
          updItemquery="insert into VOUCHER_LINE (VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,invoice_reference,credit,credit_pair)" +
                       " values (" + invoices + "," + voucherline + "," + itemID + ",-" + invoicecreditqty + "," + invoiceCreditUnitPrice + ",sysdate," +
                       privatepersonnelId + ",'" + invoiceReference + "','" + tmpCredit + "'," + voucherPairSeq + ")";
        }
        else
        {
          updItemquery=
             "insert into VOUCHER_LINE (VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,credit,credit_pair)" +
             " values (" + invoices + "," + voucherline + "," + itemID + ",-" + invoicecreditqty + "," + invoiceCreditUnitPrice + ",sysdate," +
             privatepersonnelId + ",'" + tmpCredit + "'," + voucherPairSeq + ")";
        }

        db.doUpdate( updItemquery );
      }

      //insert row for debit if needed
      if ( invoicedebitqty.length() > 0 )
      {
        dbrs=db.doQuery( "select nvl(max(VOUCHER_LINE),0)+1 VOUCHER_LINE from VOUCHER_LINE where VOUCHER_ID = '" + invoices + "' " );
        rs=dbrs.getResultSet();
        while ( rs.next() )
        {
          voucherline=rs.getString( "VOUCHER_LINE" ) == null ? "" : rs.getString( "VOUCHER_LINE" ).trim();
        }

        if ( ( invoices.length() > 0 ) && ( itemID.length() > 0 ) )
        {
          String updItemquery="";
          //12-14-02
          if ( invoiceReference.length() > 0 )
          {
            updItemquery=
               "insert into VOUCHER_LINE (VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,invoice_reference,credit_pair)" +
               " values (" + invoices + "," + voucherline + "," + itemID + "," + invoicedebitqty + "," + invoiceDebitUnitPrice + ",sysdate," +
               privatepersonnelId + ",'" + invoiceReference + "'," + voucherPairSeq + ")";
          }
          else
          {
            updItemquery=
               "insert into VOUCHER_LINE (VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,credit_pair)" +
               " values (" + invoices + "," + voucherline + "," + itemID + "," + invoicedebitqty + "," + invoiceDebitUnitPrice + ",sysdate," +
               privatepersonnelId + "," + voucherPairSeq + ")";
          }
          db.doUpdate( updItemquery );
        }
      }

      updatesucess=true;
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      polog.info("Cannot update voucher line "+e.getMessage()+"");
      updatesucess=false;
    }
    finally
    {
      if (dbrs != null)  { dbrs.close(); }
    }

    //StringBuffer Msgbody = new StringBuffer();
    nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",intcmIsApplication));
    printJavaScripts(apout);
    apout.println("// -->\n </SCRIPT>\n");

    //StringBuffer Msgtemp = new StringBuffer();

    apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    apout.println("<!--\n");
    apout.println("function sendSupplierData()\n");
    apout.println("{\n");
    apout.println("opener.actionValue('updateapstuff'); \n");
    apout.println("opener.document.purchaseorder.submit(); \n");
    apout.println("window.close();\n");
    apout.println(" }\n");
    apout.println("// -->\n</SCRIPT>\n");
    //apout.println(Msgtemp);
    if (updatesucess)
    {
      apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
    }
    else
    {
      apout.println("<BODY><BR><BR>\n");
      apout.println("An Error Occured Please Check Your Data and Try Again.\n");
    }
	apout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<CENTER><BR><BR>\n");
    apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    apout.println("</FORM></BODY></HTML>\n");
    //apout.println(Msgbody);

    return;
  }

  public void updatermaline(HttpServletRequest request1,PrintWriter apout)
  {
    //StringBuffer Msgn = new StringBuffer();

    String suppID = "";
    try{suppID = request1.getParameter("suppID").toString().trim();}catch (Exception e1){suppID = "";}

    String radianpo = "";
    try{radianpo = request1.getParameter("radianpo").toString().trim();}catch (Exception e1){radianpo = "";}

    String invoices = "";
    try{invoices = request1.getParameter("invoices").toString().trim();}catch (Exception e1){invoices = "";}
    invoices = invoices.trim();

    String invoicecreditqty = "";
    try{invoicecreditqty = request1.getParameter("invoicecreditqty").toString().trim();}catch (Exception e1){invoicecreditqty = "";}

    String invoiceCreditUnitPrice = "";
    try{invoiceCreditUnitPrice = request1.getParameter("invcreditunitprice").toString().trim();}catch (Exception e1){invoiceCreditUnitPrice = "";}
    //DecimalFormat unitpriceformat =new DecimalFormat( "####.000###" );
		if (invoiceCreditUnitPrice.trim().length() >0)
		{
			BigDecimal invCreditUnitPrice = new BigDecimal(invoiceCreditUnitPrice);
			invoiceCreditUnitPrice = "" + invCreditUnitPrice.setScale(6,4) + "";
	    //unitpriceformat.format( Double.parseDouble( invcreditunitprice ) );
		}
    //invoiceCreditUnitPrice = unitpriceformat.format( Double.parseDouble( invcreditunitprice ) );

    String itemID = "";
    try{itemID = request1.getParameter("itemID").toString().trim();}catch (Exception e1){itemID = "";}
    itemID = itemID.trim();

    //12-14-02
    String invoiceReference = "";
    try{invoiceReference = request1.getParameter("invoiceReference").toString().trim();}catch (Exception e1){invoiceReference = "";}
    invoiceReference = invoiceReference.trim();

    boolean updatesucess=false;
	DBResultSet dbrs = null;
    ResultSet rs = null;
    try
    {
      //insert row for credit
      dbrs=db.doQuery( "select nvl(max(VOUCHER_LINE),0)+1 VOUCHER_LINE from VOUCHER_LINE where VOUCHER_ID = '" + invoices + "' " );
      rs=dbrs.getResultSet();
      String voucherline="";
      while ( rs.next() )
      {
        voucherline=rs.getString( "VOUCHER_LINE" ) == null ? "" : rs.getString( "VOUCHER_LINE" ).trim();
      }

      String tmpCredit="Y";
      if ( invoicecreditqty.length() > 0 )
      {
        tmpCredit="N";
      }

      //12-19-02 getting voucher_credit_seq so I can tell which pair goes with which
      dbrs=db.doQuery( "select voucher_credit_seq.nextval voucher_pair_seq from dual" );
      rs=dbrs.getResultSet();
      String voucherPairSeq="";
      while ( rs.next() )
      {
        voucherPairSeq=rs.getString( "voucher_pair_seq" ) == null ? "" : rs.getString( "voucher_pair_seq" ).trim();
      }

      if ( ( invoices.length() > 0 ) && ( itemID.length() > 0 ) )
      {
        String updItemquery="";
        //12-14-02
        if ( invoiceReference.length() > 0 )
        {
          updItemquery="insert into VOUCHER_LINE (CREDIT,VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,invoice_reference,credit_pair)" +
                       " values ('Y'," + invoices + "," + voucherline + "," + itemID + ",-" + invoicecreditqty + "," + invoiceCreditUnitPrice + ",sysdate," +
                       privatepersonnelId + ",'" + invoiceReference + "'," + voucherPairSeq + ")";
        }
        else
        {
          updItemquery=
             "insert into VOUCHER_LINE (CREDIT,VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,credit_pair)" +
             " values ('Y'," + invoices + "," + voucherline + "," + itemID + ",-" + invoicecreditqty + "," + invoiceCreditUnitPrice + ",sysdate," +
             privatepersonnelId + "," + voucherPairSeq + ")";
        }

        db.doUpdate( updItemquery );
      }

      updatesucess=true;
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      polog.info("Cannot update voucher line "+e.getMessage()+"");
      updatesucess=false;
    }
    finally
    {
     if (dbrs != null)  { dbrs.close(); }
    }

    //StringBuffer Msgbody = new StringBuffer();
    nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",intcmIsApplication));
    printJavaScripts(apout);
    apout.println("// -->\n </SCRIPT>\n");

    //StringBuffer Msgtemp = new StringBuffer();
    apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    apout.println("<!--\n");
    apout.println("function sendSupplierData()\n");
    apout.println("{\n");
    apout.println("opener.actionValue('updateapstuff'); \n");
    apout.println("opener.document.purchaseorder.submit(); \n");
    apout.println("window.close();\n");
    apout.println(" }\n");
    apout.println("// -->\n</SCRIPT>\n");
    //apout.println(Msgtemp);
    if (updatesucess)
    {
      apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
    }
    else
    {
      apout.println("<BODY><BR><BR>\n");
      apout.println("An Error Occured Please Check Your Data and Try Again.\n");
    }

	apout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<CENTER><BR><BR>\n");
    apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    apout.println("</FORM></BODY></HTML>\n");
    //apout.println(Msgbody);

    return;
  }

  public void updateaddchgline(HttpServletRequest request1,PrintWriter apout)
	{
	  //StringBuffer Msgn = new StringBuffer();

	  String suppID = "";
	  try{suppID = request1.getParameter("suppID").toString().trim();}catch (Exception e1){suppID = "";}

	  String radianpo = "";
	  try{radianpo = request1.getParameter("radianpo").toString().trim();}catch (Exception e1){radianpo = "";}

	  String invoices = "";
	  try{invoices = request1.getParameter("invoices").toString().trim();}catch (Exception e1){invoices = "";}
	  invoices = invoices.trim();

	  String invoicecreditqty = "";
	  try{invoicecreditqty = request1.getParameter("invoicecreditqty").toString().trim();}catch (Exception e1){invoicecreditqty = "";}

	  String invoiceCreditUnitPrice = "";
	  try{invoiceCreditUnitPrice = request1.getParameter("invcreditunitprice").toString().trim();}catch (Exception e1){invoiceCreditUnitPrice = "";}
		if (invoiceCreditUnitPrice.trim().length() >0)
		{
			BigDecimal invCreditUnitPrice = new BigDecimal(invoiceCreditUnitPrice);
			invoiceCreditUnitPrice = "" + invCreditUnitPrice.setScale(6,4) + "";
	    //unitpriceformat.format( Double.parseDouble( invcreditunitprice ) );
		}
	  //DecimalFormat unitpriceformat =new DecimalFormat( "####.000###" );
	  //invcreditunitprice = unitpriceformat.format( Double.parseDouble( invcreditunitprice ) );

	  String itemID = "";
	  try{itemID = request1.getParameter("itemID").toString().trim();}catch (Exception e1){itemID = "";}
	  itemID = itemID.trim();

	  //12-14-02
	  String invoiceReference = "";
	  try{invoiceReference = request1.getParameter("invoiceReference").toString().trim();}catch (Exception e1){invoiceReference = "";}
	  invoiceReference = invoiceReference.trim();

	  boolean updatesucess=false;
	  DBResultSet dbrs = null;
	  ResultSet rs = null;
	  try
	  {
		//insert row for credit
		dbrs=db.doQuery( "select nvl(max(VOUCHER_LINE),0)+1 VOUCHER_LINE from VOUCHER_LINE where VOUCHER_ID = '" + invoices + "' " );
		rs=dbrs.getResultSet();
		String voucherline="";
		while ( rs.next() )
		{
		  voucherline=rs.getString( "VOUCHER_LINE" ) == null ? "" : rs.getString( "VOUCHER_LINE" ).trim();
		}

		String tmpCredit="Y";
		if ( invoicecreditqty.length() > 0 )
		{
		  tmpCredit="N";
		}

		//12-19-02 getting voucher_credit_seq so I can tell which pair goes with which
		dbrs=db.doQuery( "select voucher_credit_seq.nextval voucher_pair_seq from dual" );
		rs=dbrs.getResultSet();
		String voucherPairSeq="";
		while ( rs.next() )
		{
		  voucherPairSeq=rs.getString( "voucher_pair_seq" ) == null ? "" : rs.getString( "voucher_pair_seq" ).trim();
		}

		if ( ( invoices.length() > 0 ) && ( itemID.length() > 0 ) )
		{
		  String updItemquery="";
		  //12-14-02
		  if ( invoiceReference.length() > 0 )
		  {
			updItemquery="insert into VOUCHER_LINE (CREDIT,VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,invoice_reference,credit_pair)" +
						 " values ('N'," + invoices + "," + voucherline + "," + itemID + "," + invoicecreditqty + "," + invoiceCreditUnitPrice + ",sysdate," +
						 privatepersonnelId + ",'" + invoiceReference + "'," + voucherPairSeq + ")";
		  }
		  else
		  {
			updItemquery=
			   "insert into VOUCHER_LINE (CREDIT,VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,credit_pair)" +
			   " values ('N'," + invoices + "," + voucherline + "," + itemID + "," + invoicecreditqty + "," + invoiceCreditUnitPrice + ",sysdate," +
			   privatepersonnelId + "," + voucherPairSeq + ")";
		  }

		  db.doUpdate( updItemquery );
		}

		updatesucess=true;
	  }
	  catch ( Exception e )
	  {
		//e.printStackTrace();
    polog.info("Cannot update voucher line "+e.getMessage()+"");
    updatesucess=false;
	  }
	  finally
	  {
	   if (dbrs != null)  { dbrs.close(); }
	  }

	  //StringBuffer Msgbody = new StringBuffer();
	  nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
	  apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",intcmIsApplication));
	  printJavaScripts(apout);
	  apout.println("// -->\n </SCRIPT>\n");

	  //StringBuffer Msgtemp = new StringBuffer();
	  apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
	  apout.println("<!--\n");
	  apout.println("function sendSupplierData()\n");
	  apout.println("{\n");
	  apout.println("opener.actionValue('updateapstuff'); \n");
	  apout.println("opener.document.purchaseorder.submit(); \n");
	  apout.println("window.close();\n");
	  apout.println(" }\n");
	  apout.println("// -->\n</SCRIPT>\n");
	  //apout.println(Msgtemp);
	  if (updatesucess)
	  {
		apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
	  }
	  else
	  {
		apout.println("<BODY><BR><BR>\n");
		apout.println("An Error Occured Please Check Your Data and Try Again.\n");
	  }
	  apout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
	  apout.println("<CENTER><BR><BR>\n");
	  apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
	  apout.println("</FORM></BODY></HTML>\n");
	  //apout.println(Msgbody);

	  return;
	}

  public void undoReceiptInvocieLine(HttpServletRequest request1,PrintWriter apout)
  {
    //StringBuffer Msgn = new StringBuffer();

    String suppID = "";
    try{suppID = request1.getParameter("suppID").toString().trim();}catch (Exception e1){suppID = "";}

    String radianpo = "";
    try{radianpo = request1.getParameter("radianpo").toString().trim();}catch (Exception e1){radianpo = "";}

    String invoices = "";
    try{invoices = request1.getParameter("invoices").toString().trim();}catch (Exception e1){invoices = "";}
    invoices = invoices.trim();

    String receiptvoucherId = "";
    try{receiptvoucherId = request1.getParameter("receiptvoucherId").toString().trim();}catch (Exception e1){receiptvoucherId = "";}

    String receiptvoucherline = "";
    try{receiptvoucherline = request1.getParameter("receiptvoucherline").toString().trim();}catch (Exception e1){receiptvoucherline = "";}

    String receiptid = "";
    try{receiptid = request1.getParameter("receiptid").toString().trim();}catch (Exception e1){receiptid = "";}

    String itemID = "";
    try{itemID = request1.getParameter("itemID").toString().trim();}catch (Exception e1){itemID = "";}
    itemID = itemID.trim();

    boolean updatesucess = false;

    CallableStatement pstmt = null;
    Connection con = db.getConnection();

    if ( receiptvoucherId.length() > 0 && receiptvoucherline.length() > 0 && receiptid.length() > 0 )
    {
      polog.info( "call UNDO_VOUCHER_RECEIPT receiptvoucherId  " + receiptvoucherId + "  receiptvoucherline  " + receiptvoucherline + " receiptid  " + receiptid + "" );
      try
      {
        pstmt=con.prepareCall( "call UNDO_VOUCHER_RECEIPT(?,?,?,?)" );

        pstmt.setInt( 1,Integer.parseInt( receiptvoucherId ) );
        pstmt.setBigDecimal( 2,new BigDecimal( receiptvoucherline ) );
        pstmt.setInt( 3,Integer.parseInt( receiptid ) );

        pstmt.registerOutParameter( 4,java.sql.Types.VARCHAR );

        pstmt.execute();

        String errorcode=pstmt.getString( 4 );

        if ( errorcode == null )
        {
          updatesucess=true;
        }
        else
        {
				  polog.info( "Result from UNDO_VOUCHER_RECEIPT procedure Error Code:  " + errorcode );
          updatesucess=false;
        }
      }
      catch ( Exception e )
      {
        updatesucess=false;
        //e.printStackTrace();
        radian.web.emailHelpObj.sendSujataemail("Error Calling UNDO_VOUCHER_RECEIPT in Accounts Payable Page ","Error Calling UNDO_VOUCHER_RECEIPT\nError Msg:\n"+e.getMessage()+"\n radianpo  "+radianpo+"  VoucherID  "+receiptvoucherId+" Voucher Line "+receiptvoucherline+"  receiptid  "+receiptid+"\nUser : "+privatepersonnelId+"");
      }
      finally
      {
        if ( pstmt != null )
        {
          try
          {
            pstmt.close();
          }
          catch ( Exception se )
          {
            se.printStackTrace();
          }
        }
      }
    }

    //StringBuffer Msgbody = new StringBuffer();
    nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",intcmIsApplication));
    printJavaScripts(apout);
    apout.println("// -->\n </SCRIPT>\n");

    //StringBuffer Msgtemp = new StringBuffer();
    apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    apout.println("<!--\n");
    apout.println("function sendSupplierData()\n");
    apout.println("{\n");
    apout.println("opener.actionValue('updateapstuff'); \n");
    apout.println("opener.document.purchaseorder.submit(); \n");
    apout.println("window.close();\n");
    apout.println(" }\n");
    apout.println("// -->\n</SCRIPT>\n");
    //apout.println(Msgtemp);
    if (updatesucess)
    {
      apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
    }
    else
    {
      apout.println("<BODY><BR><BR>\n");
      apout.println("An Error Occured Please Check Your Data and Try Again.\n");
    }
	apout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<CENTER><BR><BR>\n");
    apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    apout.println("</FORM></BODY></HTML>\n");
    //apout.println(Msgbody);

    return;
  }

  public void undoInvocieLine(HttpServletRequest request1,PrintWriter apout)
  {
    //StringBuffer Msgn = new StringBuffer();

    String suppID = "";
    try{suppID = request1.getParameter("suppID").toString().trim();}catch (Exception e1){suppID = "";}

    String radianpo = "";
    try{radianpo = request1.getParameter("radianpo").toString().trim();}catch (Exception e1){radianpo = "";}

    String invoices = "";
    try{invoices = request1.getParameter("invoices").toString().trim();}catch (Exception e1){invoices = "";}
    invoices = invoices.trim();

    String itemvoucherId = "";
    try{itemvoucherId = request1.getParameter("itemvoucherId").toString().trim();}catch (Exception e1){itemvoucherId = "";}

    String itemvoucherline = "";
    try{itemvoucherline = request1.getParameter("itemvoucherline").toString().trim();}catch (Exception e1){itemvoucherline = "";}

    String itemID = "";
    try{itemID = request1.getParameter("itemID").toString().trim();}catch (Exception e1){itemID = "";}
    itemID = itemID.trim();

    boolean updatesucess = false;

    CallableStatement pstmt = null;
    Connection con = db.getConnection();

    if ( itemvoucherId.length() > 0 && itemvoucherline.length() > 0 )
    {
      polog.info( "call UNDO_VOUCHER_LINE itemvoucherId  " + itemvoucherId + "  itemvoucherline  " + itemvoucherline + " " );
      try
      {
        pstmt=con.prepareCall( "call UNDO_VOUCHER_LINE(?,?,?)" );

        pstmt.setInt( 1,Integer.parseInt( itemvoucherId ) );
        pstmt.setBigDecimal( 2,new BigDecimal( itemvoucherline ) );

        pstmt.registerOutParameter( 3,java.sql.Types.VARCHAR );

        pstmt.execute();

        String errorcode=pstmt.getString( 3 );

        if ( errorcode == null )
        {
          updatesucess=true;
        }
        else
        {
				  polog.info( "Result from UNDO_VOUCHER_LINE procedure Error Code:  " + errorcode );
          updatesucess=false;
        }
      }
      catch ( Exception e )
      {
        updatesucess=false;
        //e.printStackTrace();
        radian.web.emailHelpObj.sendSujataemail("Error Calling UNDO_VOUCHER_LINE in Accounts Payable Page ","Error Calling UNDO_VOUCHER_LINE\nError Msg:\n"+e.getMessage()+"\n radianpo  "+radianpo+"  VoucherID  "+itemvoucherId+" Voucher Line "+itemvoucherline+"\nUser : "+privatepersonnelId+"");
      }
      finally
      {
        if ( pstmt != null )
        {
          try
          {
            pstmt.close();
          }
          catch ( Exception se )
          {
            se.printStackTrace();
          }
        }
      }
    }

    //StringBuffer Msgbody = new StringBuffer();
    nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",intcmIsApplication));
    printJavaScripts(apout);
    apout.println("// -->\n </SCRIPT>\n");

    //StringBuffer Msgtemp = new StringBuffer();
    apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    apout.println("<!--\n");
    apout.println("function sendSupplierData()\n");
    apout.println("{\n");
    apout.println("opener.actionValue('updateapstuff'); \n");
    apout.println("opener.document.purchaseorder.submit(); \n");
    apout.println("window.close();\n");
    apout.println(" }\n");
    apout.println("// -->\n</SCRIPT>\n");
    //apout.println(Msgtemp);
    if (updatesucess)
    {
      apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
    }
    else
    {
      apout.println("<BODY><BR><BR>\n");
      apout.println("An Error Occured Please Check Your Data and Try Again.\n");
    }
	apout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<CENTER><BR><BR>\n");
    apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    apout.println("</FORM></BODY></HTML>\n");
    //apout.println(Msgbody);

    return;
  }

  public void updateaddinvoiceline(HttpServletRequest request1,PrintWriter apout)
  {
    //StringBuffer Msgn = new StringBuffer();

    String suppID = "";
    try{suppID = request1.getParameter("suppID").toString().trim();}catch (Exception e1){suppID = "";}

    String radianpo = "";
    try{radianpo = request1.getParameter("radianpo").toString().trim();}catch (Exception e1){radianpo = "";}

    String invoices = "";
    try{invoices = request1.getParameter("invoices").toString().trim();}catch (Exception e1){invoices = "";}
    invoices = invoices.trim();

    String invoiceUnitPrice = "";
    try{invoiceUnitPrice = request1.getParameter("invunitprice").toString().trim();}catch (Exception e1){invoiceUnitPrice = "";}
		if (invoiceUnitPrice.trim().length() >0)
		{
			 BigDecimal invUnitPrice = new BigDecimal(invoiceUnitPrice);
			 invoiceUnitPrice = "" + invUnitPrice.setScale(6,4) + "";
		   //unitpriceformat.format( Double.parseDouble( invcreditunitprice ) );
		}
    //DecimalFormat unitpriceformat =new DecimalFormat( "####.000###" );
    //invunitprice = unitpriceformat.format( Double.parseDouble( invunitprice ) );

    String invoiceqty = "";
    try{invoiceqty = request1.getParameter("invoiceqty").toString().trim();}catch (Exception e1){invoiceqty = "";}

    String itemID = "";
    try{itemID = request1.getParameter("itemID").toString().trim();}catch (Exception e1){itemID = "";}
    itemID = itemID.trim();

    String polineunitprice = "";
    try{polineunitprice = request1.getParameter("polineunitprice").toString().trim();}catch (Exception e1){polineunitprice = "";}
    polineunitprice = polineunitprice.trim();

    String notvouchered = "";
    try{notvouchered = request1.getParameter("notvouchered").toString().trim();}catch (Exception e1){notvouchered = "";}
    notvouchered = notvouchered.trim();

    //12-14-02
    String invoiceReference = "";
    try{invoiceReference = request1.getParameter("invoiceReference").toString().trim();}catch (Exception e1){invoiceReference = "";}
    invoiceReference = invoiceReference.trim();

    boolean updatesucess = false;
	DBResultSet dbrs = null;
    ResultSet rs = null;
    String voucherline="";
    try
    {
      dbrs=db.doQuery( "select nvl(max(VOUCHER_LINE),0)+1 VOUCHER_LINE from VOUCHER_LINE where VOUCHER_ID = '" + invoices + "' " );
      rs=dbrs.getResultSet();
      while ( rs.next() )
      {
        voucherline=rs.getString( "VOUCHER_LINE" ) == null ? "" : rs.getString( "VOUCHER_LINE" ).trim();
      }

      //12-19-02 getting voucher_credit_seq so I can tell which pair goes with which
      dbrs=db.doQuery( "select voucher_credit_seq.nextval voucher_pair_seq from dual" );
      rs=dbrs.getResultSet();
      String voucherPairSeq="";
      while ( rs.next() )
      {
        voucherPairSeq=rs.getString( "voucher_pair_seq" ) == null ? "" : rs.getString( "voucher_pair_seq" ).trim();
      }

      if ( ( invoices.length() > 0 ) && ( itemID.length() > 0 ) )
      {
        String updItemquery="";
        //12-14-02
        if ( invoiceReference.length() > 0 )
        {
          updItemquery=
             "insert into VOUCHER_LINE (VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,invoice_reference,credit_pair)" +
             " values (" + invoices + "," + voucherline + "," + itemID + "," + invoiceqty + "," + invoiceUnitPrice + ",sysdate," + privatepersonnelId + ",'" +
             invoiceReference + "'," + voucherPairSeq + ")";
        }
        else
        {
          updItemquery=
             "insert into VOUCHER_LINE (VOUCHER_ID,VOUCHER_LINE,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,AP_USER_ID,credit_pair)" +
             " values (" + invoices + "," + voucherline + "," + itemID + "," + invoiceqty + "," + invoiceUnitPrice + ",sysdate," + privatepersonnelId + "," +
             voucherPairSeq + ")";
        }
        db.doUpdate( updItemquery );
      }

      updatesucess=true;
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      polog.info("Cannot update voucher line "+e.getMessage()+"");
      updatesucess=false;
    }
    finally
    {
      if (dbrs != null)  { dbrs.close(); }
    }

    CallableStatement pstmt=null;
    Connection con=db.getConnection();
    BigDecimal invUnitPrice=new BigDecimal( invoiceUnitPrice );
    BigDecimal poUnitPrice=new BigDecimal( polineunitprice );

    if ( !invUnitPrice.equals(poUnitPrice) )
    {
      try
      {
        pstmt=con.prepareCall( "call P_UPDATE_POLINE_UNITPRICE(?,?)" );
        pstmt.setInt( 1,Integer.parseInt( invoices ) );
        pstmt.setBigDecimal( 2,new BigDecimal( voucherline ) );
        //polog.info("radianpo  : "+radianpo+"  itemID  "+itemID+"   invoiceUnitPrice  "+invoiceUnitPrice+"   poUnitPrice  "+poUnitPrice+"");
        pstmt.execute();
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        radian.web.emailHelpObj.sendSujataemail("Error Calling P_UPDATE_POLINE_UNITPRICE in Accounts Payable Page ","Error Calling P_UPDATE_POLINE_UNITPRICE\nError Msg:\n"+e.getMessage()+"\n radianpo  "+radianpo+"  VoucherID  "+invoices+" Voucher Line "+voucherline+"\nUser : "+privatepersonnelId+"");
      }
      finally
      {
        if ( pstmt != null )
        {
          try
          {
            pstmt.close();
          }
          catch ( Exception se )
          {
            se.printStackTrace();
          }
        }
      }
    }

    //StringBuffer Msgbody = new StringBuffer();
    nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",intcmIsApplication));
    printJavaScripts(apout);
    apout.println("// -->\n </SCRIPT>\n");

    //StringBuffer Msgtemp = new StringBuffer();
    apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    apout.println("<!--\n");
    apout.println("function sendSupplierData()\n");
    apout.println("{\n");
    apout.println("opener.actionValue('updateapstuff'); \n");
    apout.println("opener.document.purchaseorder.submit(); \n");
    apout.println("window.close();\n");
    apout.println(" }\n");
    apout.println("// -->\n</SCRIPT>\n");
    //apout.println(Msgtemp);
    if (updatesucess)
    {
      apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
    }
    else
    {
      apout.println("<BODY><BR><BR>\n");
      apout.println("An Error Occured Please Check Your Data and Try Again.\n");
    }
	apout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<CENTER><BR><BR>\n");
    apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    apout.println("</FORM></BODY></HTML>\n");
    //apout.println(Msgbody);

    return;
  }

  public void updateInvoice(PersonnelBean personnelBean,HttpServletRequest request1,PrintWriter apout)
  {
    boolean result = false;
    //StringBuffer Msgn = new StringBuffer();

    String suppID = "";
    try{suppID = request1.getParameter("suppID").toString().trim();}catch (Exception e1){suppID = "";}

    String radianpo = "";
    try{radianpo = request1.getParameter("radianpo").toString().trim();}catch (Exception e1){radianpo = "";}

    String contactId = "";
    try{contactId = request1.getParameter("invoices").toString().trim();}catch (Exception e1){contactId = "";}
    contactId = contactId.trim();

    String invoices = "";
    try{invoices = request1.getParameter("invoices").toString().trim();}catch (Exception e1){invoices = "";}

    String invoicedate = "";
    try{invoicedate = request1.getParameter("invoicedate").toString().trim();}catch (Exception e1){invoicedate = "";}

    String invdatereceived = "";
    try{invdatereceived = request1.getParameter("invdatereceived").toString().trim();}catch (Exception e1){invdatereceived = "";}

    String invamount = "";
    try{invamount = request1.getParameter("invamount").toString().trim();}catch (Exception e1){invamount = "";}

    String paymentterms = "";
    try{paymentterms = request1.getParameter("paymentterms").toString().trim();}catch (Exception e1){paymentterms = "";}

    String refinvoice = "";
    try{refinvoice = request1.getParameter("refinvoice").toString().trim();}catch (Exception e1){refinvoice = "";}

    String invstatus = "";
    try{invstatus = request1.getParameter("invstatus").toString().trim();}catch (Exception e1){invstatus = "";}

    String vouchNumber = "";
    try{vouchNumber = request1.getParameter("vouchNumber").toString().trim();}catch (Exception e1){vouchNumber = "";}

    String originaltotal = "";
    try{originaltotal = request1.getParameter("originaltotal").toString().trim();}catch (Exception e1){originaltotal = "";}

    String apcomments = "";
    try{apcomments = request1.getParameter("apcomments").toString().trim();}catch (Exception e1){apcomments = "";}
    apcomments = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(apcomments);

    String voucherbillinglocId = "";
    try{voucherbillinglocId = request1.getParameter("voucherbillinglocId").toString().trim();}catch (Exception e1){voucherbillinglocId = "";}
    voucherbillinglocId = voucherbillinglocId.trim();

    String invNetAmount = "";
    try{invNetAmount = request1.getParameter("netAmount").toString().trim();}catch (Exception e1){invNetAmount = "";}

	String currency = "";
    try{currency = request1.getParameter("currency").toString().trim();}catch (Exception e1){currency = "";}


    String taxItemId1 = "";
    taxItemId1= request1.getParameter("taxItemId1");
    if (taxItemId1 == null)
    taxItemId1 = "";
    String taxAmount1 = "";
    taxAmount1= request1.getParameter("taxAmount1");
    if (taxAmount1 == null)
    taxAmount1 = "";
    String taxCurrency1 = "";
    taxCurrency1= request1.getParameter("taxCurrency1");
    if (taxCurrency1 == null)
    taxCurrency1 = "";
    String taxItemId2 = "";
    taxItemId2= request1.getParameter("taxItemId2");
    if (taxItemId2 == null)
    taxItemId2 = "";
    String taxAmount2 = "";
    taxAmount2= request1.getParameter("taxAmount2");
    if (taxAmount2 == null)
    taxAmount2 = "";
    String taxCurrency2 = "";
    taxCurrency2= request1.getParameter("taxCurrency2");
    if (taxCurrency2 == null)
    taxCurrency2 = "";

    //if (apcomments.length() > 399){apcomments = apcomments.substring(0,399);}

    boolean undateorInsert = false;
    CallableStatement pstmt = null;
    Connection con = db.getConnection();

    if ( "Cancelled".equalsIgnoreCase( invstatus ) && vouchNumber.length() > 0 )
    {
      try
      {
        pstmt=con.prepareCall( "call P_VOUCHER_CANCEL(?,?)" );

        pstmt.setInt( 1,Integer.parseInt( vouchNumber ) );

        pstmt.registerOutParameter( 2,java.sql.Types.VARCHAR );

        pstmt.execute();

        String errorcode=pstmt.getString( 2 );
        polog.info( "Result from P_VOUCHER_CANCEL procedure Voucher Number: "+vouchNumber+" Error Code:  " + errorcode );

        if ( errorcode == null )
        {
          result=true;
        }
        else
        {
          result=false;
        }
      }
      catch ( Exception e )
      {
        result=false;
        radian.web.emailHelpObj.sendSujataemail("Error Calling P_VOUCHER_CANCEL in Accounts Payable Page ","Error Calling P_VOUCHER_CANCEL\nError Msg:\n"+e.getMessage()+"\n radianpo  "+radianpo+"  VoucherID  "+vouchNumber+" \nUser : "+privatepersonnelId+"");
        //e.printStackTrace();
      }
      finally
      {
        if ( pstmt != null )
        {
          try
          {
            pstmt.close();
          }
          catch ( Exception se )
          {
            se.printStackTrace();
          }
        }
      }
    }
    else
    {
      try
      {
        int testnumber=DbHelpers.countQuery( db,"select count(*) from voucher where radian_po = " + radianpo + " and VOUCHER_ID = '" + vouchNumber + "'" );
        if ( testnumber > 0 )
        {
          undateorInsert=true;
        }

        if ( ( radianpo.length() > 0 ) && ( contactId.length() > 0 ) )
        {
          String updItemquery="";
          if ( undateorInsert )
          {
            updItemquery="update voucher set SUPPLIER = '"+suppID+"',VOUCHER_BILLING_LOCATION='" + voucherbillinglocId + "',SUPPLIER_INVOICE_ID='" + contactId +
                         "',INVOICE_DATE=to_date('" + invoicedate + "','MM/DD/YYYY'),DATE_INVOICE_RECEIVED=to_date('" + invdatereceived +
                         "','MM/DD/YYYY'),INVOICE_AMOUNT =" + invamount + ",NET_INVOICE_AMOUNT=" + invNetAmount + ",PAYMENT_TERMS='" + paymentterms +
                         "',ORIGINAL_INVOICE_ID='" + refinvoice + "',STATUS='" + invstatus + "',AP_NOTE='" + apcomments + "',AP_USER_ID=" +
                         privatepersonnelId + ",CURRENCY_ID='"+currency+"',DATE_LAST_UPDATED=sysdate where RADIAN_PO = '" + radianpo + "' and VOUCHER_ID = '" + vouchNumber + "'";
            db.doUpdate( updItemquery );
            //checkSapVendorCode(voucherbillinglocId);
            String taxDelteQuery1 ="delete from VOUCHER_TAX where VOUCHER_ID = " + vouchNumber + "";
            db.doUpdate( taxDelteQuery1 );
            if (taxItemId1.length() > 0 && taxAmount1.length() >0 && taxCurrency1.length() > 0)
            {
              String taxQuery1 = "INSERT INTO TCM_OPS.VOUCHER_TAX (VOUCHER_ID, TAX_AMOUNT, TAX_ITEM_ID,TAX_CURRENCY_ID) VALUES " +
                    "("+vouchNumber+" ,"+taxAmount1+" , "+taxItemId1+", '"+taxCurrency1+"')";
              db.doUpdate( taxQuery1 );
            }
            if (taxItemId2.length() > 0 && taxAmount2.length() >0 && taxCurrency2.length() > 0)
            {
                String taxQuery2 = "INSERT INTO TCM_OPS.VOUCHER_TAX (VOUCHER_ID, TAX_AMOUNT, TAX_ITEM_ID,TAX_CURRENCY_ID) VALUES " +
                    "("+vouchNumber+" ,"+taxAmount2+" , "+taxItemId2+", '"+taxCurrency2+"')";
              db.doUpdate( taxQuery2 );
            }
            /*if (taxItemId1.length() > 0 && taxAmount1.length() >0 && taxCurrency1.length() > 0)
            {
              String taxUpdateQuery1 = "UPDATE VOUCHER_TAX SET TAX_AMOUNT="+taxAmount1+",TAX_ITEM_ID="+taxItemId1+", TAX_CURRENCY_ID ='"+taxCurrency1+"'"+
                     "where VOUCHER_ID = '" + vouchNumber + "'";
              db.doUpdate( taxUpdateQuery1 );
            }
            if (taxItemId2.length() > 0 && taxAmount2.length() >0 && taxCurrency2.length() > 0)
            {
               String taxUpdateQuery2 = "UPDATE VOUCHER_TAX SET TAX_AMOUNT="+taxAmount2+",TAX_ITEM_ID="+taxItemId2+", TAX_CURRENCY_ID ='"+taxCurrency2+"'"+
                     "where VOUCHER_ID = '" + vouchNumber + "'";
               db.doUpdate( taxUpdateQuery2 );
            }*/
          }
          else
          {
            int vouchernumber=DbHelpers.getNextVal( db,"voucher_seq" );
            updItemquery="insert into voucher (VOUCHER_BILLING_LOCATION,VOUCHER_ID,SUPPLIER,SUPPLIER_INVOICE_ID,INVOICE_DATE,DATE_INVOICE_RECEIVED,INVOICE_AMOUNT,NET_INVOICE_AMOUNT,PAYMENT_TERMS,ORIGINAL_INVOICE_ID,STATUS,RADIAN_PO,AP_NOTE,AP_USER_ID,CURRENCY_ID,DATE_LAST_UPDATED) values ('" +
                         voucherbillinglocId + "'," + vouchernumber + ",'" + suppID + "','" + contactId + "',to_date('" + invoicedate +
                         "','MM/DD/YYYY'),to_date('" + invdatereceived + "','MM/DD/YYYY')," + invamount + "," + invNetAmount + ",'" + paymentterms + "','" +
                         refinvoice + "','" + invstatus + "','" + radianpo + "','" + apcomments + "'," + privatepersonnelId + ",'"+currency+"',sysdate)";
            db.doUpdate( updItemquery );
            checkSapVendorCode(voucherbillinglocId,suppID);
            if (taxItemId1.length() > 0 && taxAmount1.length() >0 && taxCurrency1.length() > 0)
            {
              String taxQuery1 = "INSERT INTO TCM_OPS.VOUCHER_TAX (VOUCHER_ID, TAX_AMOUNT, TAX_ITEM_ID,TAX_CURRENCY_ID) VALUES " +
                    "("+vouchernumber+" ,"+taxAmount1+" , "+taxItemId1+", '"+taxCurrency1+"')";
              db.doUpdate( taxQuery1 );
            }
            if (taxItemId2.length() > 0 && taxAmount2.length() >0 && taxCurrency2.length() > 0)
            {
                String taxQuery2 = "INSERT INTO TCM_OPS.VOUCHER_TAX (VOUCHER_ID, TAX_AMOUNT, TAX_ITEM_ID,TAX_CURRENCY_ID) VALUES " +
                    "("+vouchernumber+" ,"+taxAmount2+" , "+taxItemId2+", '"+taxCurrency2+"')";
              db.doUpdate( taxQuery2 );
            }
          }
        }

        result=true;
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
        polog.info("Cannot insert/update  into voucher "+e.getMessage()+"");
        result=false;
      }
      finally
      {

      }
    }

    if (originaltotal.equalsIgnoreCase(invamount) && !("Cancelled".equalsIgnoreCase(invstatus)) )
    {
      buildsendInvocieDetails(personnelBean,vouchNumber,radianpo,"Yes",apout);
    }
    else
    {
      //StringBuffer Msgbody = new StringBuffer();
      nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
      apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",intcmIsApplication));
      printJavaScripts(apout);
      apout.println("// -->\n </SCRIPT>\n");

      //StringBuffer Msgtemp = new StringBuffer();
      apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
      apout.println("<!--\n");
      apout.println("function sendSupplierData()\n");
      apout.println("{\n");
      apout.println("opener.actionValue('updateapstuff'); \n");
      apout.println("opener.document.purchaseorder.submit(); \n");
      apout.println("window.close();\n");
      apout.println(" }\n");
      apout.println("// -->\n</SCRIPT>\n");
      //apout.println(Msgtemp);
      if (result)
      {
        apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
      }
      else
      {
        apout.println("<BODY><BR><BR>\n");
        apout.println("An Error Occured Please Check Your Data and Try Again.\n");
      }
	  apout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
      apout.println("<CENTER><BR><BR>\n");
      apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      apout.println("</FORM></BODY></HTML>\n");
      //apout.println(Msgbody);
    }
    return;
  }

  public void builddeletethis( String supplierID,String contactId,PrintWriter apout )
  {
    //StringBuffer Msgn=new StringBuffer();
    if ( contactId.length() > 0 )
    {
      try
      {
        db.doUpdate( "delete from supplier_contact where SUPPLIER = '" + supplierID + "' and CONTACT_ID = '" + contactId + "' and CONTACT_TYPE <> 'In Active'" );
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
        polog.info("Cannot delete from supplier contact "+e.getMessage()+"");
      }
      finally
      {

      }
    }
    return;
  }

    public void updateSupplierBillingLocation( String supplier,String supplierBillingLocationId, String locationStatus, String withUpdate, String sapVendorCode, PrintWriter apout )
    {
        try
        {
            boolean requestApproval = false;
            String query = "update supplier_billing_location";
            if ("InActivate".equalsIgnoreCase(locationStatus))
                query += " set status = 'Inactive',last_updated_by="+privatepersonnelId+",last_updated_date=sysdate";
            else {
                if (sapVendorCode.length() > 0)
                    query += " set status = 'Approved',last_updated_by="+privatepersonnelId+",last_updated_date=sysdate";
                else {
                    query += " set status = 'Open',requester="+privatepersonnelId+",request_date=sysdate";
                    requestApproval = true;
                }
            }
            query += " where supplier = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(supplier)+"' and billing_location_id = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(supplierBillingLocationId)+"'";
            db.doUpdate(query);
            //request sap vendor code
            if (requestApproval) {
                sendnoticemail(supplierBillingLocationId);
            }
        }
        catch ( Exception e )
        {
            polog.info("Cannot update supplier billing location "+e.getMessage()+"");
        }
        buildSearchBillingAddres(supplier,withUpdate,apout,"Approved");
    }

   public void buildSearchBillingAddres( String supplierID, String withUpdate,PrintWriter apout, String locationStatus)
     {
      //StringBuffer Msgl = new StringBuffer();
      String jsupplierID = HelpObjs.addescapesForJavascript(supplierID);

      nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");

      apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Supplier Billing Address","",intcmIsApplication));
      apout.println("<SCRIPT SRC=\"/clientscripts/appopopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
      apout.println("</HEAD>\n");
      apout.println("<BODY>\n");
      apout.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"supplierdesc\" VALUE=\"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"addline1\" VALUE=\"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"addline2\" VALUE=\"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"addline3\" VALUE=\"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"mfgphonenum\" VALUE=\"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"paymentterms\" VALUE=\"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"suppID\" VALUE=\""+jsupplierID+"\">\n");
      apout.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      apout.println("<TR>\n");
      apout.println("<TD WIDTH=\"10%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Supplier:</B></TD>\n");
      apout.println("<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"SearchString\" value=\""+supplierID+"\" SIZE=\"50\" readonly></TD>\n");
      apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"editbillingAddress\" value=\"New\" OnClick=\"ShowSearch(name,this)\"></TD></TR>\n");
      apout.println("<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Billing Location:</B></FONT></TD><TD WIDTH=\"70%\" COLSPAN=\"2\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"supplierid\" value=\"\" SIZE=\"25\" readonly>\n");
      apout.println("</TR>\n");
      apout.println("</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      apout.println("<TR>\n");
      apout.println("<TH WIDTH=\"10%\"><B>Location Desc</B></FONT></TH>\n");
      apout.println("<TH WIDTH=\"10%\"><B>Address Line 1</B></FONT></TH>\n");
      apout.println("<TH WIDTH=\"10%\"><B>Address Line 2</B></FONT></TH>\n");
      apout.println("<TH WIDTH=\"10%\"><B>City, State ZIP Country</B></FONT></TH>\n");
      apout.println("<TH WIDTH=\"5%\"><B>Status</B></FONT></TH>\n");
      apout.println("<TH WIDTH=\"10%\"><B>ERP Vendor Code</B></FONT></TH>\n");
      apout.println("</TR></TABLE>\n");

      //open scrolling table
      apout.println("<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      apout.println("<TBODY>\n");
      apout.println("<TR>\n");
      apout.println("<TD VALIGN=\"TOP\">\n");
      apout.println("<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n");

      //Write code to insert rows here
      apout.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n");

      int totalrecords = 0;
      int count = 0;
	  DBResultSet dbrs = null;
      ResultSet rs = null;
      String query = "select sbl.SUPPLIER,sbl.BILLING_LOCATION_ID,sbl.sap_vendor_code,sbl.status,loc.* from supplier_billing_location sbl, customer.location loc where sbl.SUPPLIER = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(supplierID)+"' and sbl.BILLING_LOCATION_ID=loc.LOCATION_ID";
      if ("Approved".equals(locationStatus))
        query += " and nvl(sbl.status,'X') = 'Approved'";
      query += " order by nvl(status,'Q'),location_desc,city,sap_vendor_code";
      try
      {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();
        //System.out.print( "Finished The Querry\n " );

        while ( rs.next() )
        {
          totalrecords+=1;
          count+=1;

          String supplier = BothHelpObjs.makeSpaceFromNull(rs.getString("SUPPLIER")).trim();
          String supplierbillingId=BothHelpObjs.makeSpaceFromNull( rs.getString( "BILLING_LOCATION_ID" ) ).trim();
          String country_abbrev=rs.getString( "COUNTRY_ABBREV" ) == null ? "" : rs.getString( "COUNTRY_ABBREV" );
          String state_abbrev=rs.getString( "STATE_ABBREV" ) == null ? "" : rs.getString( "STATE_ABBREV" );
          String address_line_1=rs.getString( "ADDRESS_LINE_1" ) == null ? "" : rs.getString( "ADDRESS_LINE_1" );
          String address_line_2=rs.getString( "ADDRESS_LINE_2" ) == null ? "" : rs.getString( "ADDRESS_LINE_2" );
          String city=rs.getString( "CITY" ) == null ? "" : rs.getString( "CITY" );
          String zip=rs.getString( "ZIP" ) == null ? "" : rs.getString( "ZIP" );
          String location_desc=rs.getString( "LOCATION_DESC" ) == null ? "" : rs.getString( "LOCATION_DESC" );
          String status=rs.getString( "status" ) == null ? "" : rs.getString( "status" );
          String sap_vendor_code=rs.getString( "sap_vendor_code" ) == null ? "" : rs.getString( "sap_vendor_code" );

          String Color=" ";
          if ( count % 2 == 0 )
          {
            Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }
          else
          {
            Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }

          apout.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"selectedSupplierLocation('"+HelpObjs.addescapesForJavascript(supplier)+
                       "','" + HelpObjs.addescapesForJavascript(supplierbillingId) +
                       "','" + HelpObjs.addescapesForJavascript( location_desc ) +
                       "','" + HelpObjs.addescapesForJavascript( address_line_1 ) +
                       "','" + HelpObjs.addescapesForJavascript( address_line_2 ) +
                       "','" + HelpObjs.addescapesForJavascript( city + ", " + state_abbrev + " " + zip + " " + country_abbrev ) +
                       "','" + HelpObjs.addescapesForJavascript( status ) +
                       "','" + HelpObjs.addescapesForJavascript( sap_vendor_code ) +
                       "')\" BORDERCOLOR=\"black\">\n" );
          apout.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
          apout.println( location_desc );
          apout.println( "</TD>\n" );
          apout.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
          apout.println( address_line_1 );
          apout.println( "</TD>\n" );
          apout.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
          apout.println( address_line_2 );
          apout.println( "</TD>\n" );
          apout.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
          apout.println( "" + city + ", " + state_abbrev + " " + zip + " " + country_abbrev + "" );
          apout.println( "</TD>\n" );
          apout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
          apout.println( status );
          apout.println( "</TD>\n" );
          apout.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
          apout.println( sap_vendor_code );
          apout.println( "</TD>\n" );
          apout.println( "</TR>\n" );
        }
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
        polog.info("error in query "+e.getMessage()+"");
        apout.println( "An Error Occured While Querying the Database" );
        apout.println( "</BODY>\n</HTML>\n" );
        return;
      }
      finally
      {
       if (dbrs != null)  { dbrs.close(); }
	  }


      if (totalrecords==0){apout.println("<TR><TD>No Records Found</TD></TR>\n");}

      //this is to get the counter for when user creating new location record
      try
      {
        totalrecords=DbHelpers.countQuery( db,"select count(*) from supplier_billing_location sbl where sbl.SUPPLIER = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(supplierID)+"'" );
      } catch (Exception e) {
         totalrecords = 100;
      }

      apout.println("</TABLE>\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"newBillingLocId\" VALUE=\""+supplierID+"-BILL"+totalrecords+"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"withUpdate\" ID=\"withUpdate\" VALUE=\""+withUpdate+"\">\n");

      //close scrolling table
      apout.println("</DIV>\n");
      apout.println("</TD>\n");
      apout.println("</TR>\n");
      apout.println("</TBODY>\n");
      apout.println("</TABLE>\n");

      if ("yes".equalsIgnoreCase(withUpdate)) {
        apout.println("<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"sendSupplierWithUpdate(this.form)\" type=\"button\">\n");
      }else {
        apout.println("<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"sendSupplier(this.form)\" type=\"button\">\n");
      }
      apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\">\n");

      apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      apout.println("<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" STYLE=\"display: none;\" name=\"activateLocation\" id=\"activateLocation\" value=\"Activate Location\" OnClick=\"updateSupplierBillingLocation('Activate')\" type=\"button\">\n");
      apout.println("<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" STYLE=\"display: none;\" name=\"inactivateLocation\" id=\"inactivateLocation\" value=\"Inactivate Location\" OnClick=\"updateSupplierBillingLocation('InActivate')\" type=\"button\">\n");

      apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      if ("All".equals(locationStatus))
        apout.println("<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"showActiveLocation\" id=\"showActiveLocation\" value=\"Show Approved Locations\" OnClick=\"showLocation('Approved')\" type=\"button\">\n");
      else
         apout.println("<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"showAllLocation\" id=\"showAllLocation\" value=\"Show all Locations\" OnClick=\"showLocation('All')\" type=\"button\">\n");

      apout.println("</CENTER>\n");

      apout.println("</FORM></BODY></HTML>\n");

      return;

    }

   public void buildeditBillingadd(String supplierID,String contactId,String radianPO, String vocBillingLocId, String newSuppbillingId,PrintWriter apout,Hashtable vvcountrystate)
   {
    //StringBuffer Msgn = new StringBuffer();
	DBResultSet dbrs = null;
    ResultSet rs = null;
    nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");

    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Edit Billing Address","accountspayable.js",intcmIsApplication));
    apout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    apout.println("<SCRIPT SRC=\"/clientscripts/appopopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    apout.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    printJavaScripts(apout);
		apout.println(radian.web.JSHelpObj.createCountryStateJs(vvcountrystate));
    apout.println("// -->\n </SCRIPT>\n");

    String countryabbrev = "";
    String stateabbrev = "";
    String addressline1 = "";
    String addressline2 = "";
    String addressline3 = "";
    String city = "";
    String zip = "";
    String locationdesc = "";
    String billinglocationid = "";

    if (newSuppbillingId.trim().length() > 0)
    {
      vocBillingLocId = newSuppbillingId;
    }

    try
    {
      //dbrs = db.doQuery("select loc.*,sup.BILLING_LOCATION_ID from customer.location loc, supplier sup where sup.supplier = '"+supplierID+"' and loc.location_id = sup.BILLING_LOCATION_ID ");
      dbrs = db.doQuery("select loc.* from customer.location loc where loc.location_id = '"+vocBillingLocId+"'");

      rs=dbrs.getResultSet();

      while(rs.next())
      {
	 countryabbrev = rs.getString("COUNTRY_ABBREV")==null?"":rs.getString("COUNTRY_ABBREV").trim();
	 stateabbrev = rs.getString("STATE_ABBREV")==null?"":rs.getString("STATE_ABBREV").trim();
	 addressline1 = rs.getString("ADDRESS_LINE_1")==null?"":rs.getString("ADDRESS_LINE_1").trim();
	 addressline2 = rs.getString("ADDRESS_LINE_2")==null?"":rs.getString("ADDRESS_LINE_2").trim();
	 addressline3 = rs.getString("ADDRESS_LINE_3")==null?"":rs.getString("ADDRESS_LINE_3").trim();
	 city = rs.getString("CITY")==null?"":rs.getString("CITY").trim();
	 zip = rs.getString("ZIP")==null?"":rs.getString("ZIP").trim();
	 locationdesc = rs.getString("LOCATION_DESC")==null?"":rs.getString("LOCATION_DESC").trim();
	 billinglocationid = rs.getString("LOCATION_ID")==null?"":rs.getString("LOCATION_ID").trim();
      }
    }
    catch (Exception e)
    {
	//e.printStackTrace();
      polog.info("error in query "+e.getMessage()+"");
    }
    finally
    {
      if (dbrs != null)  { dbrs.close(); }
    }

    apout.println("<BODY>\n");
    apout.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Updatebillingaddress\">\n");

    apout.println("<INPUT TYPE=\"hidden\" NAME=\"suppID\" VALUE=\""+supplierID+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"contactid\" VALUE=\""+contactId+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"radianpo\" VALUE=\""+radianPO+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"billinglocationid\" VALUE=\""+billinglocationid+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"newbillinglocationid\" VALUE=\""+newSuppbillingId+"\">\n");

    apout.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"3\" CELLSPACING=\"0\">\n");
    apout.println("<TR><TD COLSPAN=\"4\"><B>Edit Billing Address:</B></TD></TR>\n");

    apout.println("<TR>\n");
    apout.println("<TD WIDTH=\"15%\" COLSPAN=\"4\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline1\" CLASS=\"texareaannounce\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"locationdesc\" ID=\"locationdesc\" value=\""+locationdesc+"\" size=\"40\" MAXLENGTH=\"40\"></TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    apout.println("<TD WIDTH=\"15%\" COLSPAN=\"4\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline2\" CLASS=\"texareaannounce\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"addressline1\" ID=\"addressline1\" value=\""+addressline1+"\" size=\"40\" MAXLENGTH=\"40\"></TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    apout.println("<TD WIDTH=\"15%\" COLSPAN=\"4\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline3\" CLASS=\"texareaannounce\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"addressline2\" ID=\"addressline2\" value=\""+addressline2+"\" size=\"40\" MAXLENGTH=\"40\"></TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    apout.println("<TD WIDTH=\"15%\" COLSPAN=\"4\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline4\" CLASS=\"texareaannounce\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"addressline3\" ID=\"addressline3\" value=\""+addressline3+"\" size=\"40\" MAXLENGTH=\"40\"></TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    apout.println("<TD WIDTH=\"5%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline5\" CLASS=\"texareaannounce\">\n");
    apout.println("<B>City:</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"city\" ID=\"city\" value=\""+city+"\" size=\"10\" MAXLENGTH=\"30\">\n");
		apout.println("</TD>\n");
    //apout.println("State:<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"stateabbrev\" ID=\"stateabbrev\" value=\""+stateabbrev+"\" size=\"2\" MAXLENGTH=\"30\">\n");

	  if ( countryabbrev.trim().length() == 0 )
		{
		 countryabbrev="USA";
		}
		apout.println( "<TD WIDTH=\"8%\" ALIGN=\"right\" CLASS=\"texareaannounce\"><B>State/Province:</B></TD>\n" );
		apout.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"texareaannounce\">\n" );
		apout.println( "<SELECT CLASS=\"HEADER\"  NAME=\"state_abbrev\" ID=\"stateabbrev\" size=\"1\">\n" );
		apout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
		Hashtable fh= ( Hashtable ) vvcountrystate.get( countryabbrev );
		Vector invidV= ( Vector ) fh.get( "STATE_ABBREV" );
		Vector invidName= ( Vector ) fh.get( "STATE" );

		for ( int i=0; i < invidV.size(); i++ )
		{
		String preSelect="";
		String wacid= ( String ) invidV.elementAt( i );
		String invgName= ( String ) invidName.elementAt( i );

		if ( stateabbrev.equalsIgnoreCase( wacid ) )
		{
			preSelect="selected";
		}
		apout.println( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + " ("+wacid+")</option>\n" );
		}
		apout.println( "</SELECT>\n" );
		//suppout.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"state_abbrev\" value=\"\" SIZE=\"5\">\n" );
		apout.println( "</TD>\n" );

		apout.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"texareaannounce\">\n" );
        apout.println("<B>Zip:&nbsp;&nbsp;</B><INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"zip\" ID=\"zip\" value=\""+zip+"\" size=\"5\" MAXLENGTH=\"30\">\n");
        apout.println("-</B><INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"zipplus\" ID=\"zipplus\" value=\""+zip+"\" size=\"5\" MAXLENGTH=\"30\">\n");
        apout.println("</TD>\n");
		apout.println("</TR>\n");

		apout.println("<TR>\n");
    //apout.println("&nbsp;&nbsp;Country:<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"countryabbrev\" ID=\"countryabbrev\" value=\""+countryabbrev+"\" size=\"3\" MAXLENGTH=\"3\"></TD>\n");
		apout.println( "<TD WIDTH=\"8%\" ALIGN=\"left\" COLSPAN=\"4\" CLASS=\"texareaannounce\"><B>Country:</B>&nbsp;&nbsp;\n" );
		apout.println("<SELECT CLASS=\"HEADER\"  NAME=\"country_abbrev\" ID=\"countryabbrev\" size=\"1\" OnChange=\"countrychanged(document.SupplierLike.country_abbrev)\">\n");
		Hashtable countrylist = (Hashtable) vvcountrystate.get("COUNTRY");
		apout.println(radian.web.HTMLHelpObj.sorthashbyValue(countrylist.entrySet(),countryabbrev));
		apout.println("</SELECT>\n");
		//suppout.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"country_abbrev\" value=\"\" SIZE=\"5\">\n" );
		apout.println( "</TD>\n" );
    apout.println("</TR>\n");

    apout.println("</TABLE>\n");
    apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" onclick= \"return checkAddStateCode()\" value=\"OK\" name=\"CreateNew\">\n");
    //apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n");
    apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    apout.println("</FORM></BODY></HTML>\n");

    return;
   }

   public void addcreditcorrectionline(String supplierID,String itemId,String radianPO,String selinvocieid,PrintWriter apout)
   {
    //StringBuffer Msgn = new StringBuffer();
    nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
		DBResultSet dbrs = null;
    ResultSet rs = null;
    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Add Credit/Correction Line:","accountspayable.js",intcmIsApplication));
    apout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    apout.println("<SCRIPT SRC=\"/clientscripts/appopopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    apout.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    printJavaScripts(apout);
    apout.println("// -->\n </SCRIPT>\n");

    int invociecount = 0;
    String invoicedropdown = "";
    String hiddenremainging = "";

    try
    {
      String invoiceQuery = "";
      invoiceQuery = "select RADIAN_PO,SUPPLIER_INVOICE_ID,VOUCHER_ID,REMAINING_PRICE,VOUCHER_STATUS ";
      invoiceQuery += " from unfinished_invoice_view where radian_po = "+radianPO+" and VOUCHER_STATUS = 'In Progress' ";

      dbrs = db.doQuery(invoiceQuery);

      rs=dbrs.getResultSet();

      while(rs.next())
      {
	invociecount ++;
	//String voucher_status = rs.getString("VOUCHER_STATUS")==null?"":rs.getString("VOUCHER_STATUS").trim();
	//String radian_po = rs.getString("RADIAN_PO")==null?"":rs.getString("RADIAN_PO").trim();
	//String item_id = rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID").trim();
	String supplier_invoice_id = rs.getString("SUPPLIER_INVOICE_ID")==null?"":rs.getString("SUPPLIER_INVOICE_ID").trim();
	String remaining_price = rs.getString("REMAINING_PRICE")==null?"":rs.getString("REMAINING_PRICE").trim();
	String voucherid = rs.getString("VOUCHER_ID")==null?"":rs.getString("VOUCHER_ID").trim();
    String invselected="";
    if ( voucherid.equalsIgnoreCase( selinvocieid ) )
    {
      invselected="selected";
    }

	invoicedropdown += "<OPTION "+invselected+" VALUE=\""+voucherid+"\">"+supplier_invoice_id+"</OPTION>\n";
	hiddenremainging += "<INPUT TYPE=\"hidden\" NAME=\""+voucherid+"\" VALUE=\""+remaining_price+"\">\n";
      }
    }
    catch (Exception e)
    {
	//e.printStackTrace();
      polog.info("error in query "+e.getMessage()+"");
    }
    finally
    {
      if (dbrs != null)  { dbrs.close(); }
    }

    apout.println("<BODY>\n");
    apout.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"updateaddcorrectionline\">\n");

    apout.println("<INPUT TYPE=\"hidden\" NAME=\"suppID\" VALUE=\""+supplierID+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"supplierid\" VALUE=\""+supplierID+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"radianpo\" VALUE=\""+radianPO+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"itemID\" VALUE=\""+itemId+"\">\n");

    if (invociecount > 0)
    {
    apout.println("<B>Add Credit/Correction Line:</B>\n");
    apout.println("<TABLE BORDER=\"0\" width=\"100%\" BGCOLOR=\"#999999\" CELLSPACING=\"1\" CELLPADDING=\"2\">\n");
    apout.println("<TR>\n");
    //Invoice
    apout.println("<TD WIDTH=\"6%\" ALIGN=\"RIGHT\" COLSPAN=\"2\" CLASS=\"InwhiteR\">\n");
    apout.println("<B>Invoice:</B>\n");
    apout.println("</TD>\n");

    apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"InwhiteL\">\n");
    apout.println("<SELECT CLASS=\"HEADER\" NAME=\"invoices\" ID=\"invoices\" size=\"1\">\n");
    apout.println(invoicedropdown);
    apout.println("</SELECT>\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    //12-14-02 adding invoice reference
    apout.println("<TR>\n");
    //Invoice Reference
    apout.println("<TD WIDTH=\"6%\" ALIGN=\"RIGHT\" COLSPAN=\"2\" CLASS=\"InwhiteR\">\n");
    apout.println("<B>Reference:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"InwhiteL\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoiceReference\" ID=\"invoiceReference\" value=\"\" size=\"10\">\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    apout.println("<TD WIDTH=\"6%\" ALIGN=\"CENTER\" COLSPAN=\"2\" CLASS=\"Inwhite\">\n");
    apout.println("<B>Qty</B>\n");
    apout.println("</TD>\n");

    apout.println("<TD WIDTH=\"10%\" ALIGN=\"CENTER\" CLASS=\"Inwhite\">\n");
    apout.println("<B>Unit Price</B>\n");
    apout.println("</TD>\n");

    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Invoice Qty
    apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"Inwhite\">\n");
    apout.println("<B>Credit:</B>\n");
    apout.println("</TD>\n");
    //Invoice Unit Price
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhite\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoicecreditqty\" ID=\"invoicecreditqty\" value=\"\" size=\"10\">\n");
    apout.println("</TD>\n");

    apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhite\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invcreditunitprice\" ID=\"invcreditunitprice\" value=\"\" size=\"10\">\n");
    apout.println("</TD>\n");

    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Invoice Qty
    apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"Inwhite\">\n");
    apout.println("<B>Debit:</B>\n");
    apout.println("</TD>\n");
    //Invoice Unit Price
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhite\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoicedebitqty\" ID=\"invoicedebitqty\" value=\"\" size=\"10\">\n");
    apout.println("</TD>\n");

    apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhite\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invdebitunitprice\" ID=\"invdebitunitprice\" value=\"\" size=\"10\">\n");
    apout.println("</TD>\n");

    apout.println("</TR>\n");


    apout.println("</TABLE>\n");
    apout.println(hiddenremainging);
    apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" onclick= \"return checkCreditCorrectiontotals(name,this)\" name=\"CreateNew\">\n");
    //apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n");
    apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    apout.println("</FORM></BODY></HTML>\n");
    }
    else
    {
      apout.println("<CENTER><BR><BR><B>No Open Invoices Found To Add Line</B><BR><BR><BR>\n");
      apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      apout.println("</FORM></BODY></HTML>\n");
    }

    return;
   }

   public void addrmaline(String supplierID,String itemId,String radianPO,String selinvocieid,PrintWriter apout)
     {
      //StringBuffer Msgn = new StringBuffer();
      nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
	  DBResultSet dbrs = null;
      ResultSet rs = null;
      apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Add RMA Line","accountspayable.js",intcmIsApplication));
      apout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
      apout.println("<SCRIPT SRC=\"/clientscripts/appopopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
      apout.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
      printJavaScripts(apout);
      apout.println("// -->\n </SCRIPT>\n");

      int invociecount = 0;

      String invoicedropdown = "";
      String hiddenremainging = "";

      try
      {
        String invoiceQuery = "";
        invoiceQuery = "select RADIAN_PO,SUPPLIER_INVOICE_ID,VOUCHER_ID,REMAINING_PRICE,VOUCHER_STATUS ";
        invoiceQuery += " from unfinished_invoice_view where radian_po = "+radianPO+" and VOUCHER_STATUS = 'In Progress' ";

        dbrs = db.doQuery(invoiceQuery);

        rs=dbrs.getResultSet();

        while(rs.next())
        {
      invociecount ++;
      //String voucher_status = rs.getString("VOUCHER_STATUS")==null?"":rs.getString("VOUCHER_STATUS").trim();
      //String radian_po = rs.getString("RADIAN_PO")==null?"":rs.getString("RADIAN_PO").trim();
      //String item_id = rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID").trim();
      String supplier_invoice_id = rs.getString("SUPPLIER_INVOICE_ID")==null?"":rs.getString("SUPPLIER_INVOICE_ID").trim();
      String remaining_price = rs.getString("REMAINING_PRICE")==null?"":rs.getString("REMAINING_PRICE").trim();
      String voucherid = rs.getString("VOUCHER_ID")==null?"":rs.getString("VOUCHER_ID").trim();
      String invselected="";
      if ( voucherid.equalsIgnoreCase( selinvocieid ) )
      {
        invselected="selected";
      }

      invoicedropdown += "<OPTION "+invselected+" VALUE=\""+voucherid+"\">"+supplier_invoice_id+"</OPTION>\n";
      hiddenremainging += "<INPUT TYPE=\"hidden\" NAME=\""+voucherid+"\" VALUE=\""+remaining_price+"\">\n";
        }
      }
      catch (Exception e)
      {
      //e.printStackTrace();
      polog.info("error in query "+e.getMessage()+"");
      }
      finally
      {
        if (dbrs != null)  { dbrs.close(); }
      }

      apout.println("<BODY>\n");
      apout.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"updatermaline\">\n");

      apout.println("<INPUT TYPE=\"hidden\" NAME=\"suppID\" VALUE=\""+supplierID+"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"supplierid\" VALUE=\""+supplierID+"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"radianpo\" VALUE=\""+radianPO+"\">\n");
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"itemID\" VALUE=\""+itemId+"\">\n");

      if (invociecount > 0)
      {
      apout.println("<B>Add RMA Line:</B>\n");
      apout.println("<TABLE BORDER=\"0\" width=\"100%\" BGCOLOR=\"#999999\" CELLSPACING=\"1\" CELLPADDING=\"2\">\n");
      apout.println("<TR>\n");
      //Invoice
      apout.println("<TD WIDTH=\"6%\" ALIGN=\"RIGHT\" COLSPAN=\"2\" CLASS=\"InwhiteR\">\n");
      apout.println("<B>Invoice:</B>\n");
      apout.println("</TD>\n");

      apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"InwhiteL\">\n");
      apout.println("<SELECT CLASS=\"HEADER\" NAME=\"invoices\" ID=\"invoices\" size=\"1\">\n");
      apout.println(invoicedropdown);
      apout.println("</SELECT>\n");
      apout.println("</TD>\n");
      apout.println("</TR>\n");

      //12-14-02 adding invoice reference
      apout.println("<TR>\n");
      //Invoice Reference
      apout.println("<TD WIDTH=\"6%\" ALIGN=\"RIGHT\" COLSPAN=\"2\" CLASS=\"InwhiteR\">\n");
      apout.println("<B>Reference:</B>\n");
      apout.println("</TD>\n");
      apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"InwhiteL\">\n");
      apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoiceReference\" ID=\"invoiceReference\" value=\"\" size=\"10\">\n");
      apout.println("</TD>\n");
      apout.println("</TR>\n");

      apout.println("<TR>\n");
      apout.println("<TD WIDTH=\"6%\" ALIGN=\"CENTER\" COLSPAN=\"2\" CLASS=\"Inwhite\">\n");
      apout.println("<B>Qty</B>\n");
      apout.println("</TD>\n");

      apout.println("<TD WIDTH=\"10%\" ALIGN=\"CENTER\" CLASS=\"Inwhite\">\n");
      apout.println("<B>Unit Price</B>\n");
      apout.println("</TD>\n");

      apout.println("</TR>\n");

      apout.println("<TR>\n");
      //Invoice Qty
      apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"Inwhite\">\n");
      apout.println("<B>RMA:</B>\n");
      apout.println("</TD>\n");
      //Invoice Unit Price
      apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhite\">\n");
      apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoicecreditqty\" ID=\"invoicecreditqty\" value=\"\" size=\"10\">\n");
      apout.println("</TD>\n");

      apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhite\">\n");
      apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invcreditunitprice\" ID=\"invcreditunitprice\" value=\"\" size=\"10\">\n");
      apout.println("</TD>\n");

      apout.println("</TR>\n");

      apout.println("</TABLE>\n");
      apout.println(hiddenremainging);
      apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" onclick= \"return checkrmatotals(name,this)\" name=\"CreateNew\">\n");
      //apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n");
      apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      apout.println("</FORM></BODY></HTML>\n");
      }
      else
      {
        apout.println("<CENTER><BR><BR><B>No Open Invoices Found To Add Line</B><BR><BR><BR>\n");
        apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
        apout.println("</FORM></BODY></HTML>\n");
      }

      return;
     }

	 public void addaddchgrtn(String supplierID,String itemId,String radianPO,String selinvocieid,PrintWriter apout)
		  {
		   //StringBuffer Msgn = new StringBuffer();
		   nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
		   DBResultSet dbrs = null;
           ResultSet rs = null;
		   apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Add Additional Charge Credit Line","accountspayable.js",intcmIsApplication));
		   apout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
		   apout.println("<SCRIPT SRC=\"/clientscripts/appopopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
		   apout.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
		   printJavaScripts(apout);
		   apout.println("// -->\n </SCRIPT>\n");

		   int invociecount = 0;

		   String invoicedropdown = "";
		   String hiddenremainging = "";

		   try
		   {
			 String invoiceQuery = "";
			 invoiceQuery = "select RADIAN_PO,SUPPLIER_INVOICE_ID,VOUCHER_ID,REMAINING_PRICE,VOUCHER_STATUS ";
			 invoiceQuery += " from unfinished_invoice_view where radian_po = "+radianPO+" and VOUCHER_STATUS = 'In Progress' ";

			 dbrs = db.doQuery(invoiceQuery);

			 rs=dbrs.getResultSet();

			 while(rs.next())
			 {
		   invociecount ++;
		   //String voucher_status = rs.getString("VOUCHER_STATUS")==null?"":rs.getString("VOUCHER_STATUS").trim();
		   //String radian_po = rs.getString("RADIAN_PO")==null?"":rs.getString("RADIAN_PO").trim();
		   //String item_id = rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID").trim();
		   String supplier_invoice_id = rs.getString("SUPPLIER_INVOICE_ID")==null?"":rs.getString("SUPPLIER_INVOICE_ID").trim();
		   String remaining_price = rs.getString("REMAINING_PRICE")==null?"":rs.getString("REMAINING_PRICE").trim();
		   String voucherid = rs.getString("VOUCHER_ID")==null?"":rs.getString("VOUCHER_ID").trim();
		   String invselected="";
		   if ( voucherid.equalsIgnoreCase( selinvocieid ) )
		   {
			 invselected="selected";
		   }

		   invoicedropdown += "<OPTION "+invselected+" VALUE=\""+voucherid+"\">"+supplier_invoice_id+"</OPTION>\n";
		   hiddenremainging += "<INPUT TYPE=\"hidden\" NAME=\""+voucherid+"\" VALUE=\""+remaining_price+"\">\n";
			 }
		   }
		   catch (Exception e)
		   {
		   //e.printStackTrace();
       polog.info("error in query "+e.getMessage()+"");
       }
		   finally
		   {
			 if (dbrs != null)  { dbrs.close(); }
		   }

		   apout.println("<BODY>\n");
		   apout.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
		   apout.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"updateaddchgline\">\n");

		   apout.println("<INPUT TYPE=\"hidden\" NAME=\"suppID\" VALUE=\""+supplierID+"\">\n");
		   apout.println("<INPUT TYPE=\"hidden\" NAME=\"supplierid\" VALUE=\""+supplierID+"\">\n");
		   apout.println("<INPUT TYPE=\"hidden\" NAME=\"radianpo\" VALUE=\""+radianPO+"\">\n");
		   apout.println("<INPUT TYPE=\"hidden\" NAME=\"itemID\" VALUE=\""+itemId+"\">\n");

		   if (invociecount > 0)
		   {
		   apout.println("<B>Add Additional Charge Credit Line:</B>\n");
		   apout.println("<TABLE BORDER=\"0\" width=\"100%\" BGCOLOR=\"#999999\" CELLSPACING=\"1\" CELLPADDING=\"2\">\n");
		   apout.println("<TR>\n");
		   //Invoice
		   apout.println("<TD WIDTH=\"6%\" ALIGN=\"RIGHT\" COLSPAN=\"2\" CLASS=\"InwhiteR\">\n");
		   apout.println("<B>Invoice:</B>\n");
		   apout.println("</TD>\n");

		   apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"InwhiteL\">\n");
		   apout.println("<SELECT CLASS=\"HEADER\" NAME=\"invoices\" ID=\"invoices\" size=\"1\">\n");
		   apout.println(invoicedropdown);
		   apout.println("</SELECT>\n");
		   apout.println("</TD>\n");
		   apout.println("</TR>\n");

		   //12-14-02 adding invoice reference
		   apout.println("<TR>\n");
		   //Invoice Reference
		   apout.println("<TD WIDTH=\"6%\" ALIGN=\"RIGHT\" COLSPAN=\"2\" CLASS=\"InwhiteR\">\n");
		   apout.println("<B>Reference:</B>\n");
		   apout.println("</TD>\n");
		   apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"InwhiteL\">\n");
		   apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoiceReference\" ID=\"invoiceReference\" value=\"\" size=\"10\">\n");
		   apout.println("</TD>\n");
		   apout.println("</TR>\n");

		   apout.println("<TR>\n");
		   apout.println("<TD WIDTH=\"6%\" ALIGN=\"CENTER\" COLSPAN=\"2\" CLASS=\"Inwhite\">\n");
		   apout.println("<B>Qty</B>\n");
		   apout.println("</TD>\n");

		   apout.println("<TD WIDTH=\"10%\" ALIGN=\"CENTER\" CLASS=\"Inwhite\">\n");
		   apout.println("<B>Unit Price</B>\n");
		   apout.println("</TD>\n");

		   apout.println("</TR>\n");

		   apout.println("<TR>\n");
		   //Invoice Qty
		   apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"Inwhite\">\n");
		   apout.println("<B>Add. Charge Credit:</B>\n");
		   apout.println("</TD>\n");
		   //Invoice Unit Price
		   apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhite\">\n");
		   apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoicecreditqty\" ID=\"invoicecreditqty\" value=\"\" size=\"10\">\n");
		   apout.println("</TD>\n");

		   apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhite\">\n");
		   apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invcreditunitprice\" ID=\"invcreditunitprice\" value=\"\" size=\"10\">\n");
		   apout.println("</TD>\n");

		   apout.println("</TR>\n");

		   apout.println("</TABLE>\n");
		   apout.println(hiddenremainging);
		   apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" onclick= \"return checkaddchgtotals(name,this)\" name=\"CreateNew\">\n");
		   //apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n");
		   apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
		   apout.println("</FORM></BODY></HTML>\n");
		   }
		   else
		   {
			 apout.println("<CENTER><BR><BR><B>No Open Invoices Found To Add Line</B><BR><BR><BR>\n");
			 apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
			 apout.println("</FORM></BODY></HTML>\n");
		   }

		   return;
		  }

   public void buildaddinvoiceLine(String supplierID,String itemId,String radianPO, String polineunitprice,String notvouchered,String selinvocie,PrintWriter apout)
   {
    //StringBuffer Msgn = new StringBuffer();
	DBResultSet dbrs = null;
    ResultSet rs = null;
    nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");

    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Add Invoice Line","accountspayable.js",intcmIsApplication));
    apout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    apout.println("<SCRIPT SRC=\"/clientscripts/appopopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    apout.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");

    printJavaScripts(apout);
    apout.println("// -->\n </SCRIPT>\n");

    int invociecount = 0;
    String invoicedropdown = "";
    String hiddenremainging = "";

    try
    {
      String invoiceQuery = "";
      invoiceQuery = "select RADIAN_PO,SUPPLIER_INVOICE_ID,VOUCHER_ID,REMAINING_PRICE,VOUCHER_STATUS ";
      invoiceQuery += " from unfinished_invoice_view where radian_po = "+radianPO+" and VOUCHER_STATUS = 'In Progress' ";

      dbrs = db.doQuery(invoiceQuery);

      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        invociecount++;
        //String voucher_status = rs.getString("VOUCHER_STATUS")==null?"":rs.getString("VOUCHER_STATUS").trim();
        //String radian_po = rs.getString("RADIAN_PO")==null?"":rs.getString("RADIAN_PO").trim();
        //String item_id = rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID").trim();
        String supplier_invoice_id=rs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : rs.getString( "SUPPLIER_INVOICE_ID" ).trim();
        String remaining_price=rs.getString( "REMAINING_PRICE" ) == null ? "" : rs.getString( "REMAINING_PRICE" ).trim();
        String voucherid=rs.getString( "VOUCHER_ID" ) == null ? "" : rs.getString( "VOUCHER_ID" ).trim();
        String invselected="";
        if ( voucherid.equalsIgnoreCase( selinvocie ) )
        {
          invselected="selected";
        }

        invoicedropdown+="<OPTION "+invselected+" VALUE=\"" + voucherid + "\">" + supplier_invoice_id + "</OPTION>\n";
        hiddenremainging+="<INPUT TYPE=\"hidden\" NAME=\"" + voucherid + "\" VALUE=\"" + remaining_price + "\">\n";
      }
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      polog.info("error in query "+e.getMessage()+"");
    }
    finally
    {
     if (dbrs != null)  { dbrs.close(); }
    }

    apout.println("<BODY>\n");
    apout.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"updateaddinvoiceline\">\n");

    apout.println("<INPUT TYPE=\"hidden\" NAME=\"suppID\" VALUE=\""+supplierID+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"supplierid\" VALUE=\""+supplierID+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"radianpo\" VALUE=\""+radianPO+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"itemID\" VALUE=\""+itemId+"\">\n");

    apout.println("<INPUT TYPE=\"hidden\" NAME=\"polineunitprice\" VALUE=\""+polineunitprice+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"notvouchered\" VALUE=\""+notvouchered+"\">\n");

    if (invociecount > 0)
    {
    apout.println("<B>Add Invoice Line:</B>\n");
    apout.println("<TABLE BORDER=\"0\" width=\"100%\" BGCOLOR=\"#999999\" CELLSPACING=\"1\" CELLPADDING=\"2\">\n");

    apout.println("<TR>\n");
    //Invoice
    apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"Inwhiter\">\n");
    apout.println("<B>Invoice:</B>\n");
    apout.println("</TD>\n");

    apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"Inwhitel\">\n");
    apout.println("<SELECT CLASS=\"HEADER\" NAME=\"invoices\" ID=\"invoices\" size=\"1\">\n");
    apout.println(invoicedropdown);
    apout.println("</SELECT>\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    //12-14-02 adding invoice reference
    apout.println("<TR>\n");
    //Invoice Reference
    apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"Inwhiter\">\n");
    apout.println("<B>Reference:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhitel\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoiceReference\" ID=\"invoiceReference\" value=\"\" size=\"10\">\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Invoice Qty
    apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"Inwhiter\">\n");
    apout.println("<B>Qty:</B>\n");
    apout.println("</TD>\n");

    apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhitel\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoiceqty\" ID=\"invoiceqty\" value=\"\" size=\"10\">\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Unit Price
    apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"Inwhiter\">\n");
    apout.println("<B>Unit Price:</B>\n");
    apout.println("</TD>\n");

    apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"Inwhitel\">\n");
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invunitprice\" ID=\"invunitprice\" value=\"\" size=\"10\">\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("</TABLE>\n");
    apout.println(hiddenremainging);
    apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" onclick= \"return checkInvocietotals(name,this)\" name=\"CreateNew\">\n");
    //apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n");
    apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    apout.println("</FORM></BODY></HTML>\n");
    }
    else
    {
      apout.println("<CENTER><BR><BR><B>No Open Invoices Found To Add Line</B><BR><BR><BR>\n");
      apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      apout.println("</FORM></BODY></HTML>\n");
    }

    return;
   }

   public void showinvoicesummary(String supplierID,String contactId,String radianPO,PrintWriter apout)
   {
    //StringBuffer Msgn = new StringBuffer();
		nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
		DBResultSet dbrs = null;
    ResultSet rs = null;
    apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Accounts Payable New/Edit Invocie","accountspayable.js",intcmIsApplication));
    apout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    apout.println("<SCRIPT SRC=\"/clientscripts/appopopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    apout.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    printJavaScripts(apout);
    apout.println("// -->\n </SCRIPT>\n");

    String sortby = "";
    String voucherid = "";
    String supplier_invoice_id = "";
    String original_invoice_id = "";
    String invoice_date = "";
    String date_invoice_received = "";
    String radian_po = "";
    String customer_ref_po = "";
    String invoice_file_path = "";
    String net_invoice_amount = "";
    String invoiceAmount = "";
    String pymt_term_start_date = "";
    String date_to_pay = "";
    String remit_to_loc_id = "";
    String supplier_contact_id = "";
    String original_supplier = "";
    String paymentterms = "Net 45";
    String apnote = "";
    String datesentforpymt = "";
    String voucherbillingaddress = "";
	  String currencyid="";
		String approverName = "";
		String approvedDate = "";
	  String qcDate="";
		String qcUser = "";
		String origApproverId="";
    String uploadStatus="";

    if ( contactId.length() > 0 )
    {
      try
      {
        String invoiceQuery="";
        invoiceQuery="select UPLOAD_STATUS,ORIG_APPROVER,APPROVER, to_char(APPROVED_DATE,'mm/dd/yyyy') APPROVED_DATE, QC_USER, to_char(QC_DATE,'mm/dd/yyyy') QC_DATE, AP_USER_NAME, AP_APPROVER_NAME, AP_QC_USER_NAME,CURRENCY_ID,VOUCHER_BILLING_LOCATION,VOUCHER_ID,PAYMENT_TERMS,SUPPLIER,SUPPLIER_INVOICE_ID,ORIGINAL_INVOICE_ID,to_char(INVOICE_DATE,'mm/dd/yyyy') INVOICE_DATE1,INVOICE_DATE,to_char(DATE_INVOICE_RECEIVED,'mm/dd/yyyy') DATE_INVOICE_RECEIVED,RADIAN_PO,CUSTOMER_REF_PO, ";
        invoiceQuery+="AP_NOTE,INVOICE_FILE_PATH,NET_INVOICE_AMOUNT,PYMT_TERM_START_DATE,DATE_TO_PAY,REMIT_TO_LOC_ID,SUPPLIER_CONTACT_ID,ORIGINAL_SUPPLIER,STATUS,INVOICE_AMOUNT";
        invoiceQuery+=",to_char(DATE_SENT_TO_HASS,'mm/dd/yyyy') DATE_SENT_TO_HASS from voucher_view where radian_po = " + radianPO + " and VOUCHER_ID = '" + contactId + "' order by INVOICE_DATE desc";
        //invoiceQuery += " from voucher where radian_po = "+radianPO+" and status in ('In Progress','Approved')";

        dbrs=db.doQuery( invoiceQuery );

        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          voucherid=rs.getString( "VOUCHER_ID" ) == null ? "" : rs.getString( "VOUCHER_ID" ).trim();
          supplier_invoice_id=rs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : rs.getString( "SUPPLIER_INVOICE_ID" ).trim();
          original_invoice_id=rs.getString( "ORIGINAL_INVOICE_ID" ) == null ? "" : rs.getString( "ORIGINAL_INVOICE_ID" ).trim();
          invoice_date=rs.getString( "INVOICE_DATE1" ) == null ? "" : rs.getString( "INVOICE_DATE1" ).trim();
          date_invoice_received=rs.getString( "DATE_INVOICE_RECEIVED" ) == null ? "" : rs.getString( "DATE_INVOICE_RECEIVED" ).trim();
          radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
          customer_ref_po=rs.getString( "CUSTOMER_REF_PO" ) == null ? "" : rs.getString( "CUSTOMER_REF_PO" ).trim();
          invoice_file_path=rs.getString( "INVOICE_FILE_PATH" ) == null ? "" : rs.getString( "INVOICE_FILE_PATH" ).trim();
          net_invoice_amount=rs.getString( "NET_INVOICE_AMOUNT" ) == null ? "" : rs.getString( "NET_INVOICE_AMOUNT" ).trim();
          invoiceAmount=rs.getString( "INVOICE_AMOUNT" ) == null ? "" : rs.getString( "INVOICE_AMOUNT" ).trim();
          pymt_term_start_date=rs.getString( "PYMT_TERM_START_DATE" ) == null ? "" : rs.getString( "PYMT_TERM_START_DATE" ).trim();
          date_to_pay=rs.getString( "DATE_TO_PAY" ) == null ? "" : rs.getString( "DATE_TO_PAY" ).trim();
          remit_to_loc_id=rs.getString( "REMIT_TO_LOC_ID" ) == null ? "" : rs.getString( "REMIT_TO_LOC_ID" ).trim();
          supplier_contact_id=rs.getString( "SUPPLIER_CONTACT_ID" ) == null ? "" : rs.getString( "SUPPLIER_CONTACT_ID" ).trim();
          original_supplier=rs.getString( "ORIGINAL_SUPPLIER" ) == null ? "" : rs.getString( "ORIGINAL_SUPPLIER" ).trim();
          sortby=rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ).trim();
          paymentterms=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).trim();
          apnote=rs.getString( "AP_NOTE" ) == null ? "" : rs.getString( "AP_NOTE" ).trim();
          datesentforpymt=rs.getString( "DATE_SENT_TO_HASS" ) == null ? "" : rs.getString( "DATE_SENT_TO_HASS" ).trim();
          voucherbillingaddress=rs.getString( "VOUCHER_BILLING_LOCATION" ) == null ? "" : rs.getString( "VOUCHER_BILLING_LOCATION" ).trim();
					currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();
					approverName=rs.getString( "AP_APPROVER_NAME" ) == null ? "" : rs.getString( "AP_APPROVER_NAME" ).trim();
					approvedDate=rs.getString( "APPROVED_DATE" ) == null ? "" : rs.getString( "APPROVED_DATE" ).trim();
					qcDate=rs.getString( "QC_DATE" ) == null ? "" : rs.getString( "QC_DATE" ).trim();
					qcUser=rs.getString( "AP_QC_USER_NAME" ) == null ? "" : rs.getString( "AP_QC_USER_NAME" ).trim();
				  origApproverId=rs.getString( "ORIG_APPROVER" ) == null ? "" : rs.getString( "ORIG_APPROVER" ).trim();
          uploadStatus=rs.getString( "UPLOAD_STATUS" ) == null ? "" : rs.getString( "UPLOAD_STATUS" ).trim();
        }
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
        polog.info("error in query "+e.getMessage()+"");
      }
      finally
      {
        if (dbrs != null)  { dbrs.close(); }
      }
    }

    if (! (paymentterms.length() > 0 )){paymentterms = "Net 45";}

    String countryabbrev = "";
    String stateabbrev = "";
    String addressline1 = "";
    String addressline2 = "";
    String addressline3 = "";
    String city = "";
    String zip = "";
    String locationdesc = "";

    try
    {
//      dbrs = db.doQuery("select loc.* from customer.location loc, supplier sup where sup.supplier = '"+supplierID+"' and loc.location_id = sup.BILLING_LOCATION_ID ");
      dbrs = db.doQuery("select loc.* from customer.location loc where loc.location_id = '" + voucherbillingaddress + "'");

      rs=dbrs.getResultSet();

      while(rs.next())
      {
	 countryabbrev = rs.getString("COUNTRY_ABBREV")==null?"":rs.getString("COUNTRY_ABBREV").trim();
	 stateabbrev = rs.getString("STATE_ABBREV")==null?"":rs.getString("STATE_ABBREV").trim();
	 addressline1 = rs.getString("ADDRESS_LINE_1")==null?"":rs.getString("ADDRESS_LINE_1").trim();
	 addressline2 = rs.getString("ADDRESS_LINE_2")==null?"":rs.getString("ADDRESS_LINE_2").trim();
	 addressline3 = rs.getString("ADDRESS_LINE_3")==null?"":rs.getString("ADDRESS_LINE_3").trim();
	 city = rs.getString("CITY")==null?"":rs.getString("CITY").trim();
	 zip = rs.getString("ZIP")==null?"":rs.getString("ZIP").trim();
	 locationdesc = rs.getString("LOCATION_DESC")==null?"":rs.getString("LOCATION_DESC").trim();
      }
    }
    catch (Exception e)
    {
	    //e.printStackTrace();
      polog.info("error in query "+e.getMessage()+"");
    }
    finally
    {
      if (dbrs != null)  { dbrs.close(); }
    }

    if (!(addressline2.length() > 0))
    {
      addressline2 = addressline3;
      addressline3 = "";
    }

    apout.println("<BODY>\n");
    apout.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Update\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"suppID\" VALUE=\""+supplierID+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"supplierid\" VALUE=\""+supplierID+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"contactid\" VALUE=\""+contactId+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"radianpo\" VALUE=\""+radianPO+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"vouchNumber\" VALUE=\""+voucherid+"\">\n");
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"originaltotal\" VALUE=\""+net_invoice_amount+"\">\n");
    apout.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"3\" CELLSPACING=\"0\">\n");
    apout.println("<TR><TD COLSPAN=\"4\"><B>Invoice Summary:</B></TD></TR>\n");

    apout.println("<TR>\n");
    //Invoice
    apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Invoice:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invoices\" ID=\"invoices\" value=\""+supplier_invoice_id+"\" size=\"20\" MAXLENGTH=\"30\" readonly>\n");
    apout.println("</TD>\n");
    //Invoice Date
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Inv Date:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invoicedate\" ID=\"invoicedate\" value=\""+invoice_date+"\" size=\"10\" readonly>\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
	//Currency
	apout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	apout.println( "<B>Currency:</B>\n" );
	apout.println( "</TD>\n" );
	apout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">"+currencyid+"\n" );
	apout.println( "</TD>\n" );

    //Date Sent for Payment
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Date Sent for Payment:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"datesentforpayment\" ID=\"datesentforpayment\" value=\""+datesentforpymt+"\" size=\"10\" readonly>\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Ref Invoice
    apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Ref Invoice:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"refinvoice\" ID=\"refinvoice\" value=\""+original_invoice_id+"\" size=\"10\" MAXLENGTH=\"30\" readonly>\n");
    apout.println("</TD>\n");
    //Date Received
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Date Received:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invdatereceived\" ID=\"invdatereceived\" value=\""+date_invoice_received+"\" size=\"10\" readonly>\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Remit To Line 1
    apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n");
    apout.println("<B>Remit To:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"20%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline1\" CLASS=\"texareaannounce\">\n");
    apout.println(""+locationdesc+"</TD>\n");
    //Amount
    BigDecimal voucherTotal = new BigDecimal("0");
    if (invoiceAmount.length() > 0)
    {
      voucherTotal = new BigDecimal(invoiceAmount);
    }

    apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Inv Amt Excl Tax:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invamount\" ID=\"invamount\" value=\""+voucherTotal.setScale(4,4)+"\" size=\"10\" readonly>\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Remit To Line 2
    apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n");
    apout.println("&nbsp;&nbsp;\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"20%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline2\" CLASS=\"texareaannounce\">\n");
    apout.println(""+addressline1+"</TD>\n");
    //Refused
    //calculate refused
    voucherTotal = new BigDecimal("0");
    if (invoiceAmount.length() > 0) {
      BigDecimal tmpAmt = new BigDecimal (invoiceAmount);
      BigDecimal tmpNetAmt = new BigDecimal("0");
      if (net_invoice_amount.length() > 0) {
        tmpNetAmt = new BigDecimal(net_invoice_amount);
      }
      voucherTotal = tmpAmt.subtract(tmpNetAmt);
    }
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Refused:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"refused\" ID=\"refused\" value=\""+voucherTotal.setScale(4,4)+"\" size=\"10\" readonly>\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Remit To Line 3
    apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"20%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline3\" CLASS=\"texareaannounce\">\n");
    apout.println(""+addressline2+"</TD>\n");
    //Net Amount
    if (net_invoice_amount.length() > 0)
    {
      voucherTotal = new BigDecimal(net_invoice_amount);
    }
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Net Amt:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"netAmount\" ID=\"netAmount\" value=\""+voucherTotal.setScale(4,4)+"\" size=\"10\" readonly>\n");
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Remit To Line 4
    apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"20%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline4\" CLASS=\"texareaannounce\">\n");
    apout.println(""+city+", "+stateabbrev+" "+zip+" "+countryabbrev+"</TD>\n");

    //Payment Terms
    apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Terms:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println(paymentterms);
    apout.println("</TD>\n");
    apout.println("</TR>\n");

    apout.println("<TR>\n");
    //Status
    apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Status:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invstatus\" ID=\"invstatus\" value=\""+sortby+"\" size=\"10\" MAXLENGTH=\"30\" readonly>\n");
    apout.println("</TD>\n");

  	//Approved By
		apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
		apout.println("<B>Approved By:<B>\n");
		apout.println("</TD>\n");
		apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
		if (approvedDate.length() > 0)
		{
			 apout.println(""+approverName+" on "+approvedDate+"\n");
	  }
		else
		{
			 apout.println("&nbsp;\n");
		}
		apout.println("</TD>\n");
    apout.println("</TR>\n");

		apout.println("<TR>\n");
		//Blank
		apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
		apout.println("&nbsp;\n");
		apout.println("</TD>\n");
		apout.println("<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n");
		apout.println("&nbsp;\n");
		apout.println("</TD>\n");

	//QCed By
	apout.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	apout.println("<B>QCed By:<B>\n");
	apout.println("</TD>\n");
	apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
	if (qcDate.length() > 0)
	{
	 apout.println(""+qcUser+" on "+qcDate+"\n");
	}
	else
	{
	 apout.println("&nbsp;\n");
	}
	apout.println("</TD>\n");
	apout.println("</TR>\n");

    apout.println("<TR>\n");
    //AP Notes
    apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    apout.println("<B>Comments:</B>\n");
    apout.println("</TD>\n");
    apout.println("<TD WIDTH=\"20%\" ALIGN=\"left\" COLSPAN=\"3\" CLASS=\"announce\">\n");
    apout.println(apnote);
    apout.println("</TD>\n");
    apout.println("</TR>\n");


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
	taxQuery = "select x.*,y.item_desc from VOUCHER_TAX x, item y where x.VOUCHER_ID = "+contactId+" and x.TAX_ITEM_ID = y.item_id";

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
    polog.info("error in query "+e.getMessage()+"");
  }
  finally
  {
    if ( dbrs != null ) {dbrs.close();}
  }

  //Vector vatItems = getTaxItems(db);
if (taxcount > 0)
{
  apout.println("<TR><td COLSPAN=\"2\" ></td><td COLSPAN=\"2\" ><TABLE BORDER=\"0\" width=\"300\" CELLPADDING=\"3\" CELLSPACING=\"0\">\n");
  apout.println("<tr><TH WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  apout.println("<B>Tax Type</B>\n");
  apout.println("</TH>\n");
  apout.println("<TH WIDTH=\"15%\" ALIGN=\"right\" CLASS=\"announce\">\n");
  apout.println("<B>Amount</B>\n");
  apout.println("</TH>\n");
  apout.println("<TH WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  apout.println("<B>Currency</B>\n");
  apout.println("</TH></tr>\n");

  apout.println("<tr><TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  apout.println(taxItemId1);
  apout.println("</TD>\n");

  apout.println("<TD WIDTH=\"15%\" ALIGN=\"right\" CLASS=\"announce\">\n");
  apout.println(taxAmount1);
  apout.println("</TD>\n");

  apout.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
  apout.println(taxCurrency1);
  apout.println("</tr>\n");

  apout.println("<tr><TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  apout.println(taxItemId2);
  apout.println("</TD>\n");

  apout.println("<TD WIDTH=\"15%\" ALIGN=\"right\" CLASS=\"announce\">\n");
  apout.println(taxAmount2);
  apout.println("</TD>\n");
  apout.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
  apout.println(taxCurrency2);
  apout.println("</tr>\n");
  apout.println("</TD></TABLE></td></TR>\n");
}
       
  apout.println("</TABLE>\n");

    int count = 0;
    BigDecimal orderTotal = new BigDecimal("0");
    //DecimalFormat lineTotal = new DecimalFormat("####.0000##");

    try
    {
	//open scrolling table
	apout.println("<TABLE CLASS=\"columnar\" WIDTH=\"100%\">\n");
	apout.println("<TBODY>\n");
	apout.println("<TR>\n");
	apout.println("<TD VALIGN=\"TOP\" COLSPAN=\"17\">\n");
	apout.println("<DIV ID=\"orderdetail\" CLASS=\"scroll_column450\">\n");

	apout.println("<TABLE ID=\"tcmbuytable\"BORDER=\"0\" BGCOLOR=\"#999999\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");

	String itemnotesquery = "select VOUCHER_ID,VOUCHER_LINE,ITEM_DESC,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,TRANSACTION_DATE,to_char(REC_QUANTITY_MATCH_DATE,'mm/dd/yyyy') REC_QUANTITY_MATCH_DATE,to_char(REC_UNIT_PRICE_MATCH_DATE,'mm/dd/yyyy') REC_UNIT_PRICE_MATCH_DATE from voucher_line_view where VOUCHER_ID = "+voucherid+" order by TRANSACTION_DATE desc";
	dbrs = db.doQuery(itemnotesquery);
	rs=dbrs.getResultSet();

    while ( rs.next() )
    {
      //String voucher_id = rs.getString("VOUCHER_ID")==null?"":rs.getString("VOUCHER_ID").trim();
      //String voucher_line = rs.getString("VOUCHER_LINE")==null?"":rs.getString("VOUCHER_LINE").trim();
      String item_id=rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ).trim();
      String quantity_invoiced=rs.getString( "QUANTITY_INVOICED" ) == null ? "" : rs.getString( "QUANTITY_INVOICED" ).trim();
      String invoice_unit_price=rs.getString( "INVOICE_UNIT_PRICE" ) == null ? "" : rs.getString( "INVOICE_UNIT_PRICE" ).trim();
      //String transaction_date = rs.getString("TRANSACTION_DATE")==null?"":rs.getString("TRANSACTION_DATE").trim();
      //String rec_quantity_match_date = rs.getString("REC_QUANTITY_MATCH_DATE")==null?"":rs.getString("REC_QUANTITY_MATCH_DATE").trim();
      //String rec_unit_price_match_date = rs.getString("REC_UNIT_PRICE_MATCH_DATE")==null?"":rs.getString("REC_UNIT_PRICE_MATCH_DATE").trim();
      String itemdesc=rs.getString( "ITEM_DESC" ) == null ? "" : rs.getString( "ITEM_DESC" ).trim();

      BigDecimal total= new BigDecimal("0");
      BigDecimal unitPrice= new BigDecimal("0");

      if ( quantity_invoiced.length() > 0 && invoice_unit_price.length() > 0 )
      {
        BigDecimal qtyF= new BigDecimal( quantity_invoiced );
        unitPrice=new BigDecimal( invoice_unit_price );
        total= ( qtyF.multiply(unitPrice) );
        orderTotal = orderTotal.add(total);
      }

      boolean createHdr=false;

      if ( count % 10 == 0 )
      {
        createHdr=true;
      }

      count+=1;
      if ( createHdr )
      {
        apout.println( "<TR>\n" );
        apout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>Item</B></TH>\n" );
        apout.println( "<TH WIDTH=\"15%\" HEIGHT=\"25\"><B>Item Desc </B></TH>\n" );
        apout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>Quantity</B></TH>\n" );
        apout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>Unit Price</B></TH>\n" );
        apout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>Ext Price</B></TH>\n" );
        apout.println( "</TR>\n" );
      }

      String Color=" ";
      if ( count % 2 == 0 )
      {
        Color="CLASS=\"Inblue";
      }
      else
      {
        Color="CLASS=\"Inwhite";
      }

      apout.println( "<TR>\n" );
      apout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + item_id + "</TD>\n" );
      apout.println( "<TD " + Color + "l\" WIDTH=\"15%\">" + itemdesc + "</TD>\n" );
      apout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + quantity_invoiced + "</TD>\n" );
      apout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + unitPrice.setScale(6,4) + "</TD>\n" );
      apout.println( "<TD " + Color + "r\" WIDTH=\"5%\">" + total.setScale(4,4) + "</TD>\n" );

      apout.println( "</TR>\n" );
    }
  }
  catch ( Exception e )
  {
    //e.printStackTrace();
    polog.info("error in query "+e.getMessage()+"");
  }
  finally
  {
   if (dbrs != null)  { dbrs.close(); }
  }

  if ( count == 0 )
  {
    apout.println( "<TR>\n" );
    apout.println( "<TD CLASS=\"Inwhite\" WIDTH=\"5%\" COLSPAN=\"5\">No Records Found</TD>\n" );
    apout.println( "</TR>\n" );
  }
  else
  {
    apout.println( "<TR>\n" );
    apout.println( "<TD CLASS=\"Inwhiter\" WIDTH=\"5%\" COLSPAN=\"4\">Total Invoiced:</TD>\n" );
    apout.println( "<TD CLASS=\"Inwhiter\" WIDTH=\"5%\" COLSPAN=\"1\">" + orderTotal.setScale(4,4) + "</TD>\n" );
    apout.println( "</TR>\n" );
  }

  apout.println( "</TABLE>\n" );

  //close scrolling table
  apout.println( "</DIV>\n" );
  apout.println( "</TD>\n" );
  apout.println( "</TR>\n" );
  apout.println( "</TBODY>\n" );
  apout.println( "</TABLE>\n" );

  apout.println("<CENTER><BR><BR>\n");
  apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
  apout.println("</FORM></BODY></HTML>\n");

  return;
 }

 public void buildNewInvoice(String supplierID,String voucherId,Vector vvPayment1,String radianPO,String haascarrier,String carriesupid,PrintWriter apout,Vector vvCurrency)
 {
  //StringBuffer Msgn = new StringBuffer();
  nematid_Servlet = bundle.getString("INVOICE_PURCHASE_ORDER_ORDERTAKER");
  DBResultSet dbrs = null;
  ResultSet rs = null;
  apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Accounts Payable New/Edit Invoice","accountspayable.js",intcmIsApplication));
  apout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
  apout.println("<SCRIPT SRC=\"/clientscripts/appopopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
  apout.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
  printJavaScripts(apout);
	printSupplierInvoiceIdArray(apout,radianPO);
  apout.println("// -->\n </SCRIPT>\n");

  boolean editinvocie = false;
  String sortby = "";
  String voucherid = "";
  String supplier_invoice_id = "";
  String original_invoice_id = "";
  String invoice_date = "";
  String date_invoice_received = "";
  String radian_po = "";
  String customer_ref_po = "";
  String invoice_file_path = "";
  String net_invoice_amount = "";
  String pymt_term_start_date = "";
  String date_to_pay = "";
  String remit_to_loc_id = "";
  String supplier_contact_id = "";
  String original_supplier = "";
  String paymentterms = "Net 45";
  String apnote = "";
  String voucherbillingaddress = "";
  String invoiceAmount = "";
  String currencyid="";
  //DecimalFormat poTotal = new DecimalFormat("####.00##");

  if (voucherId.length() > 0)
  {
    editinvocie = true;
  }

  if ( editinvocie )
  {
    try
    {
      String invoiceQuery="";
      invoiceQuery="select CURRENCY_ID,VOUCHER_ID,VOUCHER_BILLING_LOCATION,PAYMENT_TERMS,SUPPLIER,SUPPLIER_INVOICE_ID,ORIGINAL_INVOICE_ID,to_char(INVOICE_DATE,'mm/dd/yyyy') INVOICE_DATE1,INVOICE_DATE,to_char(DATE_INVOICE_RECEIVED,'mm/dd/yyyy') DATE_INVOICE_RECEIVED,RADIAN_PO,CUSTOMER_REF_PO, ";
      invoiceQuery+="INVOICE_FILE_PATH,NET_INVOICE_AMOUNT,AP_NOTE,PYMT_TERM_START_DATE,DATE_TO_PAY,REMIT_TO_LOC_ID,SUPPLIER_CONTACT_ID,ORIGINAL_SUPPLIER,STATUS,invoice_amount";
      invoiceQuery+=" from voucher where radian_po = " + radianPO + " and VOUCHER_ID = '" + voucherId + "' order by INVOICE_DATE desc";
      //invoiceQuery += " from voucher where radian_po = "+radianPO+" and status in ('In Progress','Approved')";

      dbrs=db.doQuery( invoiceQuery );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        voucherid=rs.getString( "VOUCHER_ID" ) == null ? "" : rs.getString( "VOUCHER_ID" ).trim();
        supplier_invoice_id=rs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : rs.getString( "SUPPLIER_INVOICE_ID" ).trim();
        original_invoice_id=rs.getString( "ORIGINAL_INVOICE_ID" ) == null ? "" : rs.getString( "ORIGINAL_INVOICE_ID" ).trim();
        invoice_date=rs.getString( "INVOICE_DATE1" ) == null ? "" : rs.getString( "INVOICE_DATE1" ).trim();
        date_invoice_received=rs.getString( "DATE_INVOICE_RECEIVED" ) == null ? "" : rs.getString( "DATE_INVOICE_RECEIVED" ).trim();
        radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
        customer_ref_po=rs.getString( "CUSTOMER_REF_PO" ) == null ? "" : rs.getString( "CUSTOMER_REF_PO" ).trim();
        invoice_file_path=rs.getString( "INVOICE_FILE_PATH" ) == null ? "" : rs.getString( "INVOICE_FILE_PATH" ).trim();
        net_invoice_amount=rs.getString( "NET_INVOICE_AMOUNT" ) == null ? "" : rs.getString( "NET_INVOICE_AMOUNT" ).trim();
        pymt_term_start_date=rs.getString( "PYMT_TERM_START_DATE" ) == null ? "" : rs.getString( "PYMT_TERM_START_DATE" ).trim();
        date_to_pay=rs.getString( "DATE_TO_PAY" ) == null ? "" : rs.getString( "DATE_TO_PAY" ).trim();
        remit_to_loc_id=rs.getString( "REMIT_TO_LOC_ID" ) == null ? "" : rs.getString( "REMIT_TO_LOC_ID" ).trim();
        supplier_contact_id=rs.getString( "SUPPLIER_CONTACT_ID" ) == null ? "" : rs.getString( "SUPPLIER_CONTACT_ID" ).trim();
        original_supplier=rs.getString( "ORIGINAL_SUPPLIER" ) == null ? "" : rs.getString( "ORIGINAL_SUPPLIER" ).trim();
        sortby=rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ).trim();
        paymentterms=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).trim();
        apnote=rs.getString( "AP_NOTE" ) == null ? "" : rs.getString( "AP_NOTE" ).trim();
        invoiceAmount=rs.getString( "INVOICE_AMOUNT" ) == null ? "" : rs.getString( "INVOICE_AMOUNT" ).trim();
        voucherbillingaddress=rs.getString( "VOUCHER_BILLING_LOCATION" ) == null ? "" : rs.getString( "VOUCHER_BILLING_LOCATION" ).trim();
				currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();
      }
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      polog.info("error in query "+e.getMessage()+"");
    }
    finally
    {
      if (dbrs != null)  { dbrs.close(); }
    }
  }
  else
  {
    try
    {
      dbrs=db.doQuery( "select HAAS_CARRIER,CARRIER_SUPPLIER_ID,PAYMENT_TERMS,SUPPLIER from po_header_view where radian_po = " + radianPO + " " );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        paymentterms=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).trim();
        haascarrier=rs.getString( "HAAS_CARRIER" ) == null ? "" : rs.getString( "HAAS_CARRIER" ).trim();
        carriesupid=rs.getString( "CARRIER_SUPPLIER_ID" ) == null ? "" : rs.getString( "CARRIER_SUPPLIER_ID" ).trim();
        supplierID=rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ).trim();
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
    finally
    {
     if (dbrs != null)  { dbrs.close(); }
    }

	try
	{
	  dbrs=db.doQuery( "select CURRENCY_ID from po_line where radian_po = " + radianPO + " and SUPPLIER = '" + supplierID + "' " );
	  rs=dbrs.getResultSet();

	  while ( rs.next() )
	  {
		currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if (dbrs != null)  { dbrs.close(); }
	}
  }

  if (!(paymentterms.length() > 0 )){paymentterms = "Net 45";}

  //int suppbillingaddcount = 0;
  //String suppbillinglocation = "";

  /*if ( !editinvocie )
  {
    try
    {
      String suppbillloc="select SUPPLIER,BILLING_LOCATION_ID from supplier_billing_location where SUPPLIER = '" + supplierID + "' and rownum = 1";

      dbrs=db.doQuery( suppbillloc );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        //String supplierinvocieid = rs.getString("SUPPLIER")==null?"":rs.getString("SUPPLIER").trim();
        suppbillinglocation=rs.getString( "BILLING_LOCATION_ID" ) == null ? "" : rs.getString( "BILLING_LOCATION_ID" ).trim();

        suppbillingaddcount++;
      }
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      polog.info("error in query "+e.getMessage()+"");
    }
    finally
    {
      if (dbrs != null)  { dbrs.close(); }
    }
    voucherbillingaddress=suppbillinglocation;
  }*/

  String countryabbrev = "";
  String stateabbrev = "";
  String addressline1 = "";
  String addressline2 = "";
  String addressline3 = "";
  String city = "";
  String zip = "";
  String locationdesc = "";

  if (voucherbillingaddress != null && voucherbillingaddress.trim().length() > 0)
  {
  try
  {
    //dbrs = db.doQuery("select loc.* from customer.location loc, supplier sup where sup.supplier = '"+supplierID+"' and loc.location_id = sup.BILLING_LOCATION_ID ");
    dbrs=db.doQuery( "select loc.* from customer.location loc where loc.location_id = '" + voucherbillingaddress + "'" );
    rs=dbrs.getResultSet();

    while ( rs.next() )
    {
      countryabbrev=rs.getString( "COUNTRY_ABBREV" ) == null ? "" : rs.getString( "COUNTRY_ABBREV" ).trim();
      stateabbrev=rs.getString( "STATE_ABBREV" ) == null ? "" : rs.getString( "STATE_ABBREV" ).trim();
      addressline1=rs.getString( "ADDRESS_LINE_1" ) == null ? "" : rs.getString( "ADDRESS_LINE_1" ).trim();
      addressline2=rs.getString( "ADDRESS_LINE_2" ) == null ? "" : rs.getString( "ADDRESS_LINE_2" ).trim();
      addressline3=rs.getString( "ADDRESS_LINE_3" ) == null ? "" : rs.getString( "ADDRESS_LINE_3" ).trim();
      city=rs.getString( "CITY" ) == null ? "" : rs.getString( "CITY" ).trim();
      zip=rs.getString( "ZIP" ) == null ? "" : rs.getString( "ZIP" ).trim();
      locationdesc=rs.getString( "LOCATION_DESC" ) == null ? "" : rs.getString( "LOCATION_DESC" ).trim();
    }
  }
  catch ( Exception e )
  {
    //e.printStackTrace();
    polog.info("error in query "+e.getMessage()+"");
  }
  finally
  {
    if (dbrs != null)  { dbrs.close(); }
  }
  }
   
  if (!(addressline2.length() > 0))
  {
    addressline2 = addressline3;
    addressline3 = "";
  }

  apout.println("<BODY>\n");
  apout.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Update\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"suppID\" VALUE=\""+supplierID+"\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"supplierid\" VALUE=\""+supplierID+"\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"contactid\" VALUE=\""+voucherId+"\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"radianpo\" VALUE=\""+radianPO+"\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"vouchNumber\" VALUE=\""+voucherid+"\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"originaltotal\" VALUE=\""+net_invoice_amount+"\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"voucherbillinglocId\" VALUE=\""+voucherbillingaddress+"\">\n");

  apout.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>New/Edit Invoice</B>\n" ) );

  apout.println("<BR><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"3\" CELLSPACING=\"0\" CLASS=\"moveup\">\n");

  String displaysuppname = "";
  StringBuffer Msgsucar = new StringBuffer();

  try
  {
    String suppnames="";
    /*if ( "Y".equalsIgnoreCase( haascarrier ) && carriesupid.length() > 0 )
    {
      suppnames="select SUPPLIER,SUPPLIER_NAME from supplier where supplier in ('" + carriesupid + "','" + supplierID + "')";
    }
    else if ( supplierID.length() > 0 )
    {
      suppnames="select SUPPLIER,SUPPLIER_NAME from supplier where supplier in ('" + supplierID + "')";
    }*/

	/*suppnames = "select x.supplier, x.supplier_name from supplier x where x.supplier in (select c.supplier from po p, carrier_info c where p.carrier=c.CARRIER_CODE and ";
	suppnames +="c.HAAS_VENDOR='Y' and p.radian_po="+radianPO+") union	select x.supplier, x.supplier_name from supplier x ";
	suppnames += "where x.supplier in (select xx.supplier from po_line xx where xx.radian_po="+radianPO+") ";*/

	suppnames = "select SUPPLIER, SUPPLIER_NAME from all_po_supplier_view where radian_po="+radianPO+"";

    dbrs=db.doQuery( suppnames );
    rs=dbrs.getResultSet();

    while ( rs.next() )
    {
      String supplierid1=rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ).trim();
      String suppliername1=rs.getString( "SUPPLIER_NAME" ) == null ? "" : rs.getString( "SUPPLIER_NAME" ).trim();
      displaysuppname = suppliername1;

      String selectedsup="";
      if ( supplierid1.equalsIgnoreCase( supplierID ) )
      {
        selectedsup="selected";
      }

      Msgsucar.append( "<OPTION VALUE=\"" + supplierid1 + "\" " + selectedsup + ">" + suppliername1 + "</OPTION>\n" );
    }
  }
  catch ( Exception e )
  {
    //e.printStackTrace();
    polog.info("error in query "+e.getMessage()+"");
  }
  finally
  {
    if ( dbrs != null ) {dbrs.close();}
  }

  apout.println("<TR><TD WIDTH=\"3%\" ALIGN=\"RIGHT\"><B>Supplier:</B></TD>\n");
  //if ( "Y".equalsIgnoreCase(haascarrier) && carriesupid.length() > 0)
  {
    apout.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
    apout.println( "<SELECT CLASS=\"HEADER\" NAME=\"choicesuppid\" ID=\"choicesuppid\" ONCHANGE=\"changesuppid()\" size=\"1\">\n" );
    apout.println( Msgsucar );
    apout.println( "</SELECT>\n" );

	apout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	apout.println( "<B>Currency:</B>\n" );
	apout.println( "</TD>\n" );

	apout.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	apout.println( "<SELECT CLASS=\"HEADER\" NAME=\"currency\" ID=\"currency\" size=\"1\">\n" );
	apout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	apout.println( radian.web.HTMLHelpObj.getDropDown( vvCurrency,currencyid ) );
	apout.println( "</SELECT>\n" );

	apout.println( "</TD>\n" );

    apout.println("</TR>\n");
  }
  /*else
  {
    apout.println( "<TD WIDTH=\"75%\" COLSPAN=\"3\" ALIGN=\"left\" CLASS=\"announce\">"+displaysuppname+"</TD></TR>" );
  }*/

  //first row
  apout.println("<TR>\n");
  //Invoice
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Invoice:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  if ( editinvocie && !"In Progress".equalsIgnoreCase(sortby))
  {
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invoices\" ID=\"invoices\" value=\""+supplier_invoice_id+"\" size=\"20\" MAXLENGTH=\"30\" readonly>\n");
  }
  else
  {
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoices\" ID=\"invoices\" value=\""+supplier_invoice_id+"\" size=\"20\" MAXLENGTH=\"30\">\n");
  }
  apout.println("</TD>\n");
  //Invoice Date
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Inv Date:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoicedate\" ID=\"invoicedate\" value=\""+invoice_date+"\" size=\"10\" MAXLENGTH=\"10\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"invoicedatelink\" onClick=\"return getCalendar(document.SupplierLike.invoicedate);\">&diams;</a></FONT>\n");
  //apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoicedate\" ID=\"invoicedate\" value=\""+invoice_date+"\" onChange=\"checkChangedDate()\" size=\"10\" MAXLENGTH=\"10\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"invoicedatelink\" onClick=\"return getCalendar(document.SupplierLike.invoicedate);\">&diams;</a></FONT>\n");
  apout.println("</TD>\n");
  apout.println("</TR>\n");

  //11-26-02 second row add new row here
  apout.println("<TR>\n");
  //Ref Invoice
  apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Ref Invoice:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"refinvoice\" ID=\"refinvoice\" value=\""+original_invoice_id+"\" size=\"10\" MAXLENGTH=\"30\">\n");
  apout.println("</TD>\n");
  //Date Received
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Date Received:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invdatereceived\" ID=\"invdatereceived\" value=\""+date_invoice_received+"\" size=\"10\" MAXLENGTH=\"10\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"invdatereceivedlink\" onClick=\"return getCalendar(document.SupplierLike.invdatereceived);\">&diams;</a></FONT>\n");
  apout.println("</TD>\n");
  apout.println("</TR>\n");

  //third row
  apout.println("<TR>\n");
  //Remit To Line 1
  apout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n");
  apout.println("<B>Remit To:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline1\" CLASS=\"texareaannounce\">\n");
  apout.println("<FONT COLOR=\"Maroon\">"+locationdesc+"</FONT></TD>\n");
  //Amount
  BigDecimal voucherTotal = new BigDecimal("0");
  if (invoiceAmount.length() > 0)
  {
    voucherTotal = new BigDecimal(invoiceAmount);
  }
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Inv Amt Excl Tax:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
  if ( editinvocie )
  {
    apout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invamount\" ID=\"invamount\" value=\"" + voucherTotal.setScale(4,4) + "\" onChange=\"changeNetInvAmount()\" size=\"10\">\n" );
  }
  else
  {
    apout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invamount\" ID=\"invamount\" value=\"\" onChange=\"changeNetInvAmount()\" size=\"10\">\n" );
  }
  //apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invamount\" ID=\"invamount\" value=\""+invoiceAmount+"\" size=\"10\">\n");
  apout.println("</TD>\n");
  apout.println("</TR>\n");

  //fourth row
  apout.println("<TR>\n");
  //Remit To Line 2
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n");
  apout.println("&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"searchbillingAddress()\">\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline2\" CLASS=\"texareaannounce\">\n");
  apout.println("<FONT COLOR=\"Maroon\">"+addressline1+"</FONT></TD>\n");
  //refused
  voucherTotal = new BigDecimal("0");
  if (invoiceAmount.length() > 0) {
    BigDecimal tmpAmt =new BigDecimal (invoiceAmount);
    BigDecimal tmpNetAmt = new BigDecimal("0");
    if (net_invoice_amount.length() > 0) {
      tmpNetAmt = new BigDecimal(net_invoice_amount);
    }
      voucherTotal = tmpAmt.subtract(tmpNetAmt);
  }
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Refused:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
  if ( editinvocie )
  {
    apout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"refused\" ID=\"refused\" value=\"" + voucherTotal.setScale(4,4) + "\" onChange=\"changeNetInvAmount()\" size=\"10\">\n" );
  }
  else
  {
    apout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"refused\" ID=\"refused\" value=\"\" onChange=\"changeNetInvAmount()\" size=\"10\">\n" );
  }
  apout.println("</TD>\n");
  apout.println("</TR>\n");

  //fifth row
  apout.println("<TR>\n");
  //Remit To Line 3
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n");
  apout.println("&nbsp;\n");  //11-25-02
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline3\" CLASS=\"texareaannounce\">\n");
  apout.println("<FONT COLOR=\"Maroon\">"+addressline2+"</FONT></TD>\n");
  //net amount
  voucherTotal = new BigDecimal("0");
  if (net_invoice_amount.length() > 0)
  {
    voucherTotal = new BigDecimal(net_invoice_amount);
  }
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Net Amt:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
  apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"netAmount\" ID=\"netAmount\" value=\""+voucherTotal.setScale(4,4)+"\" size=\"10\" MAXLENGTH=\"30\" readonly>\n");
  apout.println("</TD>\n");
  apout.println("</TR>\n");

  //sixth row
  apout.println("<TR>\n");
  //Remit To Line 4
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n");
  apout.println("&nbsp;\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline4\" CLASS=\"texareaannounce\">\n");
  apout.println("<FONT COLOR=\"Maroon\">"+city+", "+stateabbrev+" "+zip+" "+countryabbrev+"</FONT></TD>\n");
  //Payment Terms
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Terms:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  //apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"paymentterms\" ID=\"paymentterms\" value=\"\" size=\"10\" readonly>\n");
  apout.println("<SELECT CLASS=\"HEADER\" NAME=\"paymentterms\" ID=\"paymentterms\" size=\"1\">\n");
  //apout.println("<OPTION VALUE=\"None\">Please Select</OPTION>\n");
  apout.println(radian.web.HTMLHelpObj.getDropDown(vvPayment1,paymentterms));
  apout.println("</SELECT>\n");
  apout.println("</TD>\n");
  apout.println("</TR>\n");

  apout.println("<TR>\n");
  //Blank
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("&nbsp;\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  apout.println("&nbsp;\n");
  apout.println("</TD>\n");
  //Status
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Status</B>\n");
  apout.println("</TD>\n");

  apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  if (voucherId.length() > 0)
  {
    apout.println("<SELECT CLASS=\"HEADER\" NAME=\"invstatus\" ID=\"invstatus\" size=\"1\">\n");

    if ( sortby.equalsIgnoreCase("In Progress"))
    {
    apout.println("<option selected value=\"In Progress\">In Progress</option>\n");
    apout.println("<option value=\"Cancelled\">Cancelled</option>\n");
    }
    else if ( sortby.equalsIgnoreCase("Cancelled"))
    {
    apout.println("<option value=\"In Progress\">In Progress</option>\n");
    apout.println("<option selected value=\"Cancelled\">Cancelled</option>\n");
    }
    else
    {
    apout.println("<option selected value=\"In Progress\">In Progress</option>\n");
    apout.println("<option value=\"Cancelled\">Cancelled</option>\n");
    }
  apout.println("</SELECT>\n");
  }
  else
  {
    //apout.println("<SELECT CLASS=\"HEADER\" NAME=\"invstatus\" ID=\"invstatus\" size=\"1\" DISABLED>\n");
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invstatus\" ID=\"invstatus\" value=\"In Progress\" size=\"10\" MAXLENGTH=\"30\" readonly>\n");
  }
  apout.println("</TD>\n");

  apout.println("</TR>\n");

  String taxItemId1 = "";
  String taxAmount1 = "";
  String taxCurrency1 = "";
  String taxItemId2 = "";
  String taxAmount2 = "";
  String taxCurrency2 = "";
  int taxcount = 0;

  if ( editinvocie)
  {
  try
  {
    String taxQuery="";
	taxQuery = "select * from VOUCHER_TAX where VOUCHER_ID = '" + voucherId + "'";

    dbrs=db.doQuery( taxQuery );
    rs=dbrs.getResultSet();

    while ( rs.next() )
    {
      if (taxcount ==0)
      {
       taxItemId1=rs.getString( "TAX_ITEM_ID" ) == null ? "" : rs.getString( "TAX_ITEM_ID" ).trim();
       taxAmount1=rs.getString( "TAX_AMOUNT" ) == null ? "" : rs.getString( "TAX_AMOUNT" ).trim();
       taxCurrency1=rs.getString( "TAX_CURRENCY_ID" ) == null ? "" : rs.getString( "TAX_CURRENCY_ID" ).trim();
      }
      else if (taxcount ==1)
      {
       taxItemId2=rs.getString( "TAX_ITEM_ID" ) == null ? "" : rs.getString( "TAX_ITEM_ID" ).trim();
       taxAmount2=rs.getString( "TAX_AMOUNT" ) == null ? "" : rs.getString( "TAX_AMOUNT" ).trim();
       taxCurrency2=rs.getString( "TAX_CURRENCY_ID" ) == null ? "" : rs.getString( "TAX_CURRENCY_ID" ).trim();   
      }
      taxcount ++;
    }
  }
  catch ( Exception e )
  {
    //e.printStackTrace();
    polog.info("error in query "+e.getMessage()+"");
  }
  finally
  {
    if ( dbrs != null ) {dbrs.close();}
  }
  }

  Vector vatItems = getTaxItems(db);
  apout.println("<TR><td COLSPAN=\"2\" ></td><td COLSPAN=\"2\" ><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"3\" CELLSPACING=\"0\">\n");
  apout.println("<tr><TH WIDTH=\"50%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Tax Type</B>\n");
  apout.println("</TH>\n");
  apout.println("<TH WIDTH=\"50%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  apout.println("<B>Amount</B>\n");
  apout.println("</TH>\n");
  apout.println("<TH WIDTH=\"50%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Currency</B>\n");
  apout.println("</TH></tr>\n");

  apout.println("<tr><TD WIDTH=\"50%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  if ( editinvocie && !"In Progress".equalsIgnoreCase(sortby))
  {
    apout.println(taxItemId1);
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"taxItemId1\" ID=\"taxItemId1\" VALUE=\""+taxItemId1+"\">\n");
  }
  else
  {
    apout.println("<SELECT CLASS=\"HEADER\" NAME=\"taxItemId1\" ID=\"taxItemId1\" size=\"1\">\n");
    apout.println(radian.web.HTMLHelpObj.getDropDown(vatItems,taxItemId1));
    apout.println("</SELECT>\n");
  }
  apout.println("</TD>\n");

  apout.println("<TD WIDTH=\"50%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  if ( editinvocie && !"In Progress".equalsIgnoreCase(sortby))
  {
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"taxAmount1\" ID=\"taxAmount1\" value=\""+taxAmount1+"\" size=\"4\" MAXLENGTH=\"30\" readonly>\n");
  }
  else
  {
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"taxAmount1\" ID=\"taxAmount1\" value=\""+taxAmount1+"\" size=\"4\" MAXLENGTH=\"30\">\n");
  }
  apout.println("</TD>\n");

  apout.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  if ( editinvocie && !"In Progress".equalsIgnoreCase(sortby))
  {
  apout.println(taxCurrency1);
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"taxCurrency1\" ID=\"taxCurrency1\" VALUE=\""+taxCurrency1+"\">\n");
  }
  else
  {
  apout.println( "<SELECT CLASS=\"HEADER\" NAME=\"taxCurrency1\" ID=\"taxCurrency1\" size=\"1\">\n" );
  apout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
  apout.println( radian.web.HTMLHelpObj.getDropDown( vvCurrency,taxCurrency1 ) );
  apout.println( "</SELECT>\n" );
  apout.println( "</TD>\n" );
  }
  apout.println("</tr>\n");
     
  apout.println("<tr><TD WIDTH=\"50%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  if ( editinvocie && !"In Progress".equalsIgnoreCase(sortby))
  {
    apout.println(taxItemId2);
    apout.println("<INPUT TYPE=\"hidden\" NAME=\"taxItemId2\" ID=\"taxItemId2\" VALUE=\""+taxItemId2+"\">\n");
  }
  else
  {
   apout.println("<SELECT CLASS=\"HEADER\" NAME=\"taxItemId2\" ID=\"taxItemId2\" size=\"1\">\n");
   apout.println(radian.web.HTMLHelpObj.getDropDown(vatItems,taxItemId2));
   apout.println("</SELECT>\n");            
  }
  apout.println("</TD>\n");

  apout.println("<TD WIDTH=\"50%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  if ( editinvocie && !"In Progress".equalsIgnoreCase(sortby))
  {
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"taxAmount2\" ID=\"taxAmount2\" value=\""+taxAmount2+"\" size=\"4\" MAXLENGTH=\"30\" readonly>\n");
  }
  else
  {
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"taxAmount2\" ID=\"taxAmount2\" value=\""+taxAmount2+"\" size=\"4\" MAXLENGTH=\"30\">\n");
  }
  apout.println("</TD>\n");
  apout.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  if ( editinvocie && !"In Progress".equalsIgnoreCase(sortby))
  {
      apout.println(taxCurrency2);
      apout.println("<INPUT TYPE=\"hidden\" NAME=\"taxCurrency2\" ID=\"taxCurrency2\" VALUE=\""+taxCurrency2+"\">\n");
  }
  else
  {
   apout.println( "<SELECT CLASS=\"HEADER\" NAME=\"taxCurrency2\" ID=\"taxCurrency2\" size=\"1\">\n" );
   apout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
   apout.println( radian.web.HTMLHelpObj.getDropDown( vvCurrency,taxCurrency2 ) );
   apout.println( "</SELECT>\n" );
   apout.println( "</TD>\n" );
  }
  apout.println("</tr>\n");

  apout.println("</TD></TABLE></td></TR>\n");

  apout.println("<TR>\n");

  //AP Notes
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Comments:</B>\n");
  apout.println("</TD>\n");

  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" COLSPAN=\"3\" CLASS=\"announce\">\n");
  apout.println("<TEXTAREA name=\"apcomments\" rows=\"3\" cols=\"80\">"+apnote+"</TEXTAREA></TD>\n");
  apout.println("</TD>\n");

  apout.println("</TR>\n");


  apout.println("</TABLE>\n");
  apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" onclick= \"return checkInvocieValues(name,this)\" name=\"CreateNew\">\n");
  //apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n");
  apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
  apout.println("</FORM></BODY></HTML>\n");

  return;
 }

 public void buildsendInvocieDetails(PersonnelBean personnelBean,String voucherId,String radianPo,String buildAddress,PrintWriter apout )
 {
   //StringBuffer Msgn=new StringBuffer();
   //StringBuffer Msgbody=new StringBuffer();
   //DecimalFormat poTotal=new DecimalFormat( "####.00##" );

   nematid_Servlet=bundle.getString( "INVOICE_PURCHASE_ORDER_ORDERTAKER" );
   apout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Order Taker","",intcmIsApplication ) );

   printJavaScripts(apout);
   apout.println( "// -->\n </SCRIPT>\n" );

   //Build the Java Script Here and Finish the thing
   apout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
   apout.println( "<!--\n" );
   apout.println( "function sendSupplierData()\n" );
   apout.println( "{\n" );
   String vouchersupplier="";
   String voucherbillingaddress="";
   DBResultSet dbrs = null;
   ResultSet rs = null;
   boolean isReversed = false;

	 String hubName = "";
	 try {
		dbrs = db.doQuery("select HUB_NAME,HAAS_CARRIER,CARRIER_SUPPLIER_ID,PAYMENT_TERMS,SUPPLIER from po_header_view where radian_po = " +radianPo + " ");
		rs = dbrs.getResultSet();

	 while (rs.next()) {
		hubName = rs.getString("HUB_NAME") == null ? "" : rs.getString("HUB_NAME").trim();
	 }

	}
	catch (Exception e) {
	 //e.printStackTrace();
    polog.info("error in query "+e.getMessage()+"");
  }
	finally {
	 if (dbrs != null) {
		dbrs.close();
	 }
	}

	try
	{
     String invoiceQuery="";
     invoiceQuery="select UPLOAD_STATUS,ORIG_APPROVER,APPROVER, to_char(APPROVED_DATE,'mm/dd/yyyy') APPROVED_DATE, QC_USER, to_char(QC_DATE,'mm/dd/yyyy') QC_DATE, AP_USER_NAME, AP_APPROVER_NAME, AP_QC_USER_NAME,CURRENCY_ID,verified,VOUCHER_BILLING_LOCATION,AP_NOTE,VOUCHER_ID,PAYMENT_TERMS,SUPPLIER,SUPPLIER_INVOICE_ID,ORIGINAL_INVOICE_ID,to_char(INVOICE_DATE,'mm/dd/yyyy') INVOICE_DATE1,INVOICE_DATE,to_char(DATE_INVOICE_RECEIVED,'mm/dd/yyyy') DATE_INVOICE_RECEIVED,RADIAN_PO,CUSTOMER_REF_PO, ";
     invoiceQuery+="INVOICE_FILE_PATH,INVOICE_AMOUNT,NET_INVOICE_AMOUNT,PYMT_TERM_START_DATE,DATE_TO_PAY,REMIT_TO_LOC_ID,SUPPLIER_CONTACT_ID,ORIGINAL_SUPPLIER,STATUS";
     invoiceQuery+=",to_char(DATE_SENT_TO_HASS,'mm/dd/yyyy') DATE_SENT_TO_HASS,nvl(INVOICE_URL,fx_iq_image_url(radian_po,supplier_invoice_id)) INVOICE_URL,SAP_IMPORT_FLAG from voucher_view where radian_po = " + radianPo + " and VOUCHER_ID = '" + voucherId + "' order by INVOICE_DATE desc";

     dbrs=db.doQuery( invoiceQuery );
     rs=dbrs.getResultSet();

     while ( rs.next() )
     {
       //String voucherid=rs.getString( "VOUCHER_ID" ) == null ? "" : rs.getString( "VOUCHER_ID" ).trim();
       vouchersupplier=rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ).trim();
       String supplier_invoice_id=rs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : rs.getString( "SUPPLIER_INVOICE_ID" ).trim();
       String original_invoice_id=rs.getString( "ORIGINAL_INVOICE_ID" ) == null ? "" : rs.getString( "ORIGINAL_INVOICE_ID" ).trim();
       String invoice_date=rs.getString( "INVOICE_DATE1" ) == null ? "" : rs.getString( "INVOICE_DATE1" ).trim();
       String date_invoice_received=rs.getString( "DATE_INVOICE_RECEIVED" ) == null ? "" : rs.getString( "DATE_INVOICE_RECEIVED" ).trim();
       //String radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
       //String customer_ref_po=rs.getString( "CUSTOMER_REF_PO" ) == null ? "" : rs.getString( "CUSTOMER_REF_PO" ).trim();
       //String invoice_file_path=rs.getString( "INVOICE_FILE_PATH" ) == null ? "" : rs.getString( "INVOICE_FILE_PATH" ).trim();
       String net_invoice_amount=rs.getString( "NET_INVOICE_AMOUNT" ) == null ? "" : rs.getString( "NET_INVOICE_AMOUNT" ).trim();
       String invoiceAmount=rs.getString( "INVOICE_AMOUNT" ) == null ? "" : rs.getString( "INVOICE_AMOUNT" ).trim();
       //String pymt_term_start_date=rs.getString( "PYMT_TERM_START_DATE" ) == null ? "" : rs.getString( "PYMT_TERM_START_DATE" ).trim();
       //String date_to_pay=rs.getString( "DATE_TO_PAY" ) == null ? "" : rs.getString( "DATE_TO_PAY" ).trim();
       //String remit_to_loc_id=rs.getString( "REMIT_TO_LOC_ID" ) == null ? "" : rs.getString( "REMIT_TO_LOC_ID" ).trim();
       //String supplier_contact_id=rs.getString( "SUPPLIER_CONTACT_ID" ) == null ? "" : rs.getString( "SUPPLIER_CONTACT_ID" ).trim();
       //String original_supplier=rs.getString( "ORIGINAL_SUPPLIER" ) == null ? "" : rs.getString( "ORIGINAL_SUPPLIER" ).trim();
       String status=rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ).trim();
       String paymentterms=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).trim();
       String apnote= HelpObjs.addescapesForJavascript(rs.getString( "AP_NOTE" ) == null ? "" : rs.getString( "AP_NOTE" ).trim());
       voucherbillingaddress=rs.getString( "VOUCHER_BILLING_LOCATION" ) == null ? "" : rs.getString( "VOUCHER_BILLING_LOCATION" ).trim();
       String datesentforpymt = rs.getString("DATE_SENT_TO_HASS")==null?"":rs.getString("DATE_SENT_TO_HASS").trim();
       String invoiceurl = rs.getString("INVOICE_URL")==null?"":rs.getString("INVOICE_URL").trim();
			 String currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();
			 String approverName=rs.getString( "AP_APPROVER_NAME" ) == null ? "" : rs.getString( "AP_APPROVER_NAME" ).trim();
			 String approvedDate=rs.getString( "APPROVED_DATE" ) == null ? "" : rs.getString( "APPROVED_DATE" ).trim();
			 String qcDate=rs.getString( "QC_DATE" ) == null ? "" : rs.getString( "QC_DATE" ).trim();
			 String qcUser=rs.getString( "AP_QC_USER_NAME" ) == null ? "" : rs.getString( "AP_QC_USER_NAME" ).trim();
       String origApproverId=rs.getString( "ORIG_APPROVER" ) == null ? "" : rs.getString( "ORIG_APPROVER" ).trim();
       String uploadStatus=rs.getString( "UPLOAD_STATUS" ) == null ? "" : rs.getString( "UPLOAD_STATUS" ).trim();
       String sapImportFlag = rs.getString( "SAP_IMPORT_FLAG" ) == null ? "" : rs.getString( "SAP_IMPORT_FLAG" ).trim();
       
       DBResultSet dbrsReversed = db.doQuery("select 'X' from voucher where radian_po = "+radianPo+" and supplier_invoice_id "+(supplier_invoice_id.indexOf("CORR")== -1 ?"like '"+ supplier_invoice_id + "%CORR%": "= '" + supplier_invoice_id)+"' and status in ( 'Approved', 'In Progress')");
	   ResultSet rsGetWasReversed = dbrsReversed.getResultSet();
	    while ( rsGetWasReversed.next() )
	    {
	  	 isReversed = rsGetWasReversed.getString("'X'") == null ? false : true;
	    }
       
       

       apout.println( "paymentterms = opener.document.getElementById(\"invoicedate\");\n" );
       apout.println( "paymentterms.value = \"" + invoice_date + "\";\n" );

       apout.println( "paymentterms = opener.document.getElementById(\"refinvoice\");\n" );
       apout.println( "paymentterms.value = \"" + original_invoice_id + "\";\n" );

       apout.println( "paymentterms = opener.document.getElementById(\"invdatereceived\");\n" );
       apout.println( "paymentterms.value = \"" + date_invoice_received + "\";\n" );

       apout.println( "paymentterms = opener.document.getElementById(\"invstatus\");\n" );
       apout.println( "paymentterms.value = \"" + status + "\";\n" );

       apout.println("datesentforpayment = opener.document.getElementById(\"datesentforpayment\");\n");
       apout.println("datesentforpayment.value = \""+datesentforpymt+"\";\n");

			 apout.println("currency = opener.document.getElementById(\"currency\");\n");
       apout.println("currency.value = \""+currencyid+"\";\n");

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

	   if ( datesentforpymt.length() > 0 )
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

		if (personnelBean.getPermissionBean().hasFacilityPermission("VoucherQC", hubName,null)) {
		 this.setVoucherQcStatus(true);
		}
		else {
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
				 if(!isReversed && "Approved".equalsIgnoreCase( status ) && sapImportFlag.equalsIgnoreCase("Y"))
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

       String invoiceurlhtml = "";
       if (invoiceurl.length() > 0)
       {
       StringTokenizer st = new StringTokenizer(invoiceurl," ");
       if(st.countTokens() > 1)
       {
          while(st.hasMoreTokens())
          {
              String tok = st.nextToken();
              invoiceurlhtml += "<A onclick=\\\"javascript:window.open('"+tok+"')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\"><U>"+supplier_invoice_id+"</U></A>&nbsp;&nbsp;";
          }
       }
       else
       {
          invoiceurlhtml = "<A onclick=\\\"javascript:window.open('"+invoiceurl+"')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\"><U>"+supplier_invoice_id+"</U></A>";
       }
       }

       apout.println("invoiceurl = opener.document.getElementById(\"invoiceurl\");\n");
       apout.println("invoiceurl.innerHTML = \""+invoiceurlhtml+"\";\n");

       apout.println("uploadStatus = opener.document.getElementById(\"uploadStatus\");\n");
       apout.println("uploadStatus.innerHTML = \""+uploadStatus+"\";\n");

       BigDecimal voucherTotal= new BigDecimal("0");
       if ( invoiceAmount.length() > 0 )
       {
         voucherTotal=new BigDecimal( invoiceAmount );
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
         //if (tmpNetAmt > 0) {
         voucherTotal=tmpAmt.subtract(tmpNetAmt);
         //}
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
       apout.println( "paymentterms.value = \"" + paymentterms + "\";\n" );

       apout.println( "invoicecomments = opener.document.getElementById(\"invoicecomments\");\n" );
       apout.println( "invoicecomments.innerHTML = \"" + apnote + "\";\n" );


       //11-25-02
       if ( "y".equalsIgnoreCase( rs.getString( "verified" ) ) )
       {
         apout.println( "opener.document.purchaseorder.VerifiedB.disabled = true;\n" );
       }
       else
       {
         apout.println( "opener.document.purchaseorder.VerifiedB.disabled = false;\n" );
       }

     }
   }
   catch ( Exception e )
   {
     //e.printStackTrace();
     polog.info("error in query "+e.getMessage()+"");
   }
   finally
   {
     if (dbrs != null)  { dbrs.close(); }
   }

       if (voucherId != null && voucherId.length() > 0)
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
	taxQuery = "select x.*,y.item_desc from VOUCHER_TAX x, item y where x.VOUCHER_ID = "+voucherId+" and x.TAX_ITEM_ID = y.item_id";

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
   if ( "Yes".equalsIgnoreCase( buildAddress ) )
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
        apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + supplieraddressline1 + "</FONT>\";\n" );

        if ( supplieraddressline2.length() > 0 )
        {
          apout.println( "selectedRow = opener.document.getElementById(\"supplierline3\");\n" );
          apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + supplieraddressline2 + "</FONT>\";\n" );

          apout.println( "selectedRow = opener.document.getElementById(\"supplierline4\");\n" );
          apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + suppliercity + ", " + supplierstateabbrev + " " + supplierzip +
                          " " + suppliercountryabbrev + "</FONT>\";\n" );
        }
        else
        {
          apout.println( "selectedRow = opener.document.getElementById(\"supplierline3\");\n" );
          apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + suppliercity + ", " + supplierstateabbrev + " " + supplierzip +
                          " " + suppliercountryabbrev + "</FONT>\";\n" );
        }

        apout.println( "selectedRow = opener.document.getElementById(\"supplierline4\");\n" );
        apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n" );

      }
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      polog.info("error in query "+e.getMessage()+"");
    }
    finally
    {
      if (dbrs != null)  { dbrs.close(); }
    }

     try
     {
       //dbrs = db.doQuery("Select * from location_to_hub_view where LOCATION_ID = '"+ship_to_location_id+"' ");
       dbrs=db.doQuery( "select loc.* from customer.location loc where loc.location_id = '" + voucherbillingaddress + "'" );
       //dbrs = db.doQuery("select loc.* from customer.location loc, supplier sup where sup.supplier = '"+supplier+"' and loc.location_id = sup.BILLING_LOCATION_ID ");

       rs=dbrs.getResultSet();

       while ( rs.next() )
       {
         String countryabbrev=rs.getString( "COUNTRY_ABBREV" ) == null ? "" : rs.getString( "COUNTRY_ABBREV" ).trim();
         String stateabbrev=rs.getString( "STATE_ABBREV" ) == null ? "" : rs.getString( "STATE_ABBREV" ).trim();
         String addressline1=rs.getString( "ADDRESS_LINE_1" ) == null ? "" : rs.getString( "ADDRESS_LINE_1" ).trim();
         String addressline2=rs.getString( "ADDRESS_LINE_2" ) == null ? "" : rs.getString( "ADDRESS_LINE_2" ).trim();
         //String addressline3=rs.getString( "ADDRESS_LINE_3" ) == null ? "" : rs.getString( "ADDRESS_LINE_3" ).trim();
         String city=rs.getString( "CITY" ) == null ? "" : rs.getString( "CITY" ).trim();
         String zip=rs.getString( "ZIP" ) == null ? "" : rs.getString( "ZIP" ).trim();
         String locationdesc=rs.getString( "LOCATION_DESC" ) == null ? "" : rs.getString( "LOCATION_DESC" ).trim();

         apout.println( "voucherbillinglocId = opener.document.getElementById(\"voucherbillinglocId\");\n" );
         apout.println( "voucherbillinglocId.value = \"" + voucherbillingaddress + "\";\n" );

         apout.println( "selectedRow = opener.document.getElementById(\"shiptoline1\");\n" );
         apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + locationdesc + "</FONT>\";\n" );

         apout.println( "selectedRow = opener.document.getElementById(\"shiptoline2\");\n" );
         apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + addressline1 + "</FONT>\";\n" );

         if ( addressline2.length() > 0 )
         {
           apout.println( "selectedRow = opener.document.getElementById(\"shiptoline3\");\n" );
           apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + addressline2 + "</FONT>\";\n" );

           apout.println( "selectedRow = opener.document.getElementById(\"shiptoline4\");\n" );
           apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + city + ", " + stateabbrev + " " + zip + " " + countryabbrev +
                           "</FONT>\";\n" );
         }
         else
         {
           apout.println( "selectedRow = opener.document.getElementById(\"shiptoline3\");\n" );
           apout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + city + ", " + stateabbrev + " " + zip + " " + countryabbrev +
                           "</FONT>\";\n" );

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
      if (dbrs != null)  { dbrs.close(); }
     }
   }

   apout.println( "window.close();\n" );
   apout.println( " }\n" );

   apout.println( "// -->\n</SCRIPT>\n" );
   //apout.println( Msgtemp );
   //apout.println("<BODY><BR><BR>\n");
   apout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
   apout.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
   apout.println( "<INPUT TYPE=\"hidden\" NAME=\"material\" VALUE=\"" + voucherId + "\">\n" );
   apout.println( "<INPUT TYPE=\"hidden\" NAME=\"revisionDate\" VALUE=\"" + radianPo + "\">\n" );

   apout.println( "<CENTER><BR><BR>\n" );
   apout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
   apout.println( "</FORM></BODY></HTML>\n" );
   //apout.println( Msgbody );

   return;
 }

  protected void printJavaScripts(PrintWriter apout)
  {
  //StringBuffer Msglt = new StringBuffer();

  apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
  apout.println("<!--\n");

  apout.println("function cancel()\n");
  apout.println("{\n");
  apout.println("window.close();\n");
  apout.println("}\n");

  apout.println("function addsupplierID(matidvalue,addline1,nickname,contactphoneno,contactfaxno)\n");
  apout.println("{\n");
  apout.println("document.SupplierLike.supplierid.value = nickname;\n");
  apout.println("document.SupplierLike.contactname.value = addline1;\n");
  apout.println("document.SupplierLike.contactid.value = addline1;\n");
  apout.println("document.SupplierLike.contactphoneno.value = contactphoneno;\n");
  apout.println("document.SupplierLike.contactfaxno.value = contactfaxno;\n");

  apout.println("}\n");

  return;
  }

 protected void printSupplierInvoiceIdArray(PrintWriter apout,String radianPO)
 {
	DBResultSet dbrs = null;
	ResultSet rs = null;

	apout.println("var supplierInvoiceIdArray = new Array(");
	int supplierInvoiceIdArrayCount = 0;

	try
	{
	 dbrs=db.doQuery( "select distinct SUPPLIER_INVOICE_ID from voucher where radian_po = " + radianPO + "" );
	 rs=dbrs.getResultSet();

	 while ( rs.next() )
	 {
		String supplierInvoiceId=rs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : rs.getString( "SUPPLIER_INVOICE_ID" ).trim();
		if (supplierInvoiceIdArrayCount > 0)
		{
		 apout.println(",");
		}
		apout.println("\""+supplierInvoiceId+"\"");
		supplierInvoiceIdArrayCount++;
	 }
	}
	catch ( Exception e )
	{
	 //e.printStackTrace();
    polog.info("error in query "+e.getMessage()+"");
  }
	finally
	{
	 if (dbrs != null)  { dbrs.close(); }
	}

	apout.println(")");
	return;
 }
}