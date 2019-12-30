package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.soundsLikeInvoice;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2006
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 */

public class EditInvoiceTerms
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private boolean allowupdate;
    private boolean intcmIsApplication = false;
		private boolean voucherQcUpdate;
		private boolean resetInvoiceStatus;
		private String nematid_Servlet = "";
		private String privatepersonnelId = "";
		private static org.apache.log4j.Logger polog = org.apache.log4j.Logger.getLogger(soundsLikeInvoice.class);

    public EditInvoiceTerms(ServerResourceBundle b, TcmISDBModel d)
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


     public void doResult(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException
    {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
    HttpSession invoicesession = request.getSession();

    PersonnelBean personnelBean = (PersonnelBean) invoicesession.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    intcmIsApplication = false;
    if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
    {
     intcmIsApplication = true;
    }

    privatepersonnelId= invoicesession.getAttribute( "PERSONNELID" ) == null ? "" : invoicesession.getAttribute( "PERSONNELID" ).toString();
		//PersonnelBean personnelBean = invoicesession.getAttribute("personnelBean") == null ? null : (PersonnelBean) invoicesession.getAttribute("personnelBean");

    String invoiceId=request.getParameter( "invoiceId" );
    if ( invoiceId == null )
      invoiceId="";
    invoiceId=invoiceId.trim();

    String radianpo=request.getParameter( "radianpo" );
    if ( radianpo == null )
      radianpo="";
    radianpo=radianpo.trim();

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action=Action.trim();

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

    if ("Update".equalsIgnoreCase(Action) || "qcVoucher".equalsIgnoreCase(Action))
    {
    updateInvoice(personnelBean,request,out);
    }
    else if ("editinvoice".equalsIgnoreCase(Action))
    {
			 Vector vvPayment = (Vector)invoicesession.getAttribute("VV_PAYMENT");
			 Vector vvCurrency= ( Vector ) invoicesession.getAttribute( "VV_CURRENCY" );

			 buildNewInvoice(personnelBean,suppID,invoiceId,vvPayment,radianpo,haascarrier,carriersuppid,out,vvCurrency);
    }
    out.close();
  }

  public void updateInvoice(PersonnelBean personnelBean,HttpServletRequest request1,PrintWriter apout)
  {
    boolean result = false;

    String radianpo = "";
    try{radianpo = request1.getParameter("radianpo").toString().trim();}catch (Exception e1){radianpo = "";}

    String paymentterms = "";
    try{paymentterms = request1.getParameter("paymentterms").toString().trim();}catch (Exception e1){paymentterms = "";}

    String invstatus = "";
    try{invstatus = request1.getParameter("invstatus").toString().trim();}catch (Exception e1){invstatus = "";}

		String updateAction=request1.getParameter( "Action" );
		if ( updateAction == null )
			updateAction="";
		updateAction=updateAction.trim();

    String vouchNumber = "";
    try{vouchNumber = request1.getParameter("vouchNumber").toString().trim();}catch (Exception e1){vouchNumber = "";}

    String apcomments = "";
    try{apcomments = request1.getParameter("apcomments").toString().trim();}catch (Exception e1){apcomments = "";}
    apcomments = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(apcomments);

	  //polog.info("Action   "+updateAction+"");
  	/*try
		{
			 String updItemquery="";
			 updItemquery="update voucher set PAYMENT_TERMS='" + paymentterms +"',AP_NOTE='" + apcomments + "',AP_USER_ID=" +
				privatepersonnelId + ",DATE_LAST_UPDATED=sysdate where RADIAN_PO = '" + radianpo + "' and VOUCHER_ID = '" + vouchNumber + "'";
			 db.doUpdate( updItemquery );
			 result=true;
		}
		catch ( Exception e )
		{
			 e.printStackTrace();
			 result=false;
  	}
		finally
    {

    }*/

      String procErrorCode = "";
      if ( "In Progress".equalsIgnoreCase( invstatus ) && vouchNumber.length() > 0 )
		{
			 procErrorCode = pVoucherQc(vouchNumber,"REACTIVATE",privatepersonnelId);
		}
		else if ("qcVoucher".equalsIgnoreCase( updateAction ) && vouchNumber.length() > 0 )
		{
			 procErrorCode = pVoucherQc(vouchNumber,"QC",privatepersonnelId);
		}

			//StringBuffer Msgbody = new StringBuffer();
			nematid_Servlet = bundle.getString("INVOICE_EDIT_PAYMENT_TERMS");
      apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",intcmIsApplication));
      printJavaScripts(apout);
      apout.println("// -->\n </SCRIPT>\n");

      if (procErrorCode != null && procErrorCode.equalsIgnoreCase("OK"))
      {
      //StringBuffer Msgtemp = new StringBuffer();
      apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
      apout.println("<!--\n");
      apout.println("function sendSupplierData()\n");
      apout.println("{\n");
      apout.println("opener.getPoInvoice("+vouchNumber+"); \n");
      //apout.println("opener.document.purchaseorder.submit(); \n");
      apout.println("window.close();\n");
      apout.println(" }\n");
      apout.println("// -->\n</SCRIPT>\n");
      //apout.println(Msgtemp);

        apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
				/*soundsLikeInvoice sendInvoiceDetails = new soundsLikeInvoice(bundle,db);
				try {
				 sendInvoiceDetails.setupdateStatus(this.getupdateStatus());
				}
				catch (Exception ex) {
				}
				sendInvoiceDetails.buildsendInvocieDetails(personnelBean,vouchNumber,radianpo,"Yes",apout);*/
      }
      else
      {
      apout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
      apout.println("<!--\n");
      apout.println("function sendSupplierData()\n");
      apout.println("{\n");
      if (procErrorCode != null && !procErrorCode.equalsIgnoreCase("NO CALL"))
      {
        procErrorCode =  HelpObjs.addescapesForJavascript(procErrorCode);
        apout.println( "messagerow = opener.document.getElementById(\"messagerow\");\n" );          
        apout.println( "messagerow.innerHTML = \"<FONT SIZE=\\\"3\\\" FACE=\\\"Arial\\\" COLOR=\\\"#fc0303\\\"><B>" + procErrorCode + "</B></FONT>\";\n" );
        apout.println("window.close();\n");
      }
      apout.println(" }\n");
      apout.println("// -->\n</SCRIPT>\n");

        apout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
        apout.println("An Error Occured Please Check Your Data and Try Again.\n");
      }
				apout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
				apout.println("<CENTER><BR><BR>\n");
				apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
				apout.println("</FORM></BODY></HTML>\n");

	return;
 }

	public String pVoucherQc(String voucherId, String action, String personnelId) {
	 boolean result = false;
     String errorcode = "";
     if (voucherId.trim().length() < 1) {
		result = false;
        errorcode = "NO CALL";
        return errorcode;
	 }

	 CallableStatement cs = null;
	 try {
		Connection connect1 = null;
		connect1 = db.getConnection();
		polog.info("Calling P_VOUCHER_QC for "+action+" for voucherId "+voucherId+" ");
		cs = connect1.prepareCall("{call P_VOUCHER_QC (?,?,?,?)}");
		cs.setInt(1, Integer.parseInt(voucherId));
		cs.setString(2, action);
		cs.setInt(3, Integer.parseInt(personnelId));
        cs.registerOutParameter( 4,java.sql.Types.VARCHAR );

        cs.execute();

        String procErrorCode=cs.getString( 4 );
        if (procErrorCode != null && procErrorCode.equalsIgnoreCase("OK"))
        {
            errorcode = "OK";
        }
        else
        {
           errorcode = procErrorCode;
        }

        result = true;
		}
	 catch (Exception e) {
		//e.printStackTrace();
		radian.web.emailHelpObj.sendnawazemail(
		 "Error Calling P_VOUCHER_QC in AP Page",
		 "Error occured while calling P_VOUCHER_QC for voucherId " + voucherId +
		 "\nAction "+action+" \nUser Id "+personnelId+"\n\nError Msg:\n" + e.getMessage() + "\n");
		result = false;
        errorcode ="Error Calling P_VOUCHER_QC in AP Page";
     }
	 finally {
		if (cs != null) {
		 try {
			cs.close();
		 }
		 catch (SQLException se) {}
		}
  }
		return errorcode;
 }

 public void buildNewInvoice(PersonnelBean personnelBean,String supplierID,String contactId,Vector vvPayment1,String radianPO,String haascarrier,String carriesupid,PrintWriter apout,Vector vvCurrency)
 {
  nematid_Servlet = bundle.getString("INVOICE_EDIT_PAYMENT_TERMS");
  DBResultSet dbrs = null;
  ResultSet rs = null;
  apout.println(radian.web.HTMLHelpObj.printHTMLHeader("Accounts Payable New/Edit Invoice","accountspayable.js",intcmIsApplication));
  apout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
  apout.println("<SCRIPT SRC=\"/clientscripts/appopopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	apout.println("<SCRIPT SRC=\"/clientscripts/voucherqc.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
  apout.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
  printJavaScripts(apout);
  apout.println("// -->\n </SCRIPT>\n");

  boolean editinvocie = false;
  String voucherStatus = "";
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
	String approverId = "";
	String approverName = "";
	String approvedDate = "";
	String qcDate="";
	String qcUser = "";
	String hubName = "";
	String origApproverId = "";
  String uploadStatus="";
        
  if (contactId.length() > 0)
  {
    editinvocie = true;
  }

  if ( editinvocie )
  {
    try
    {
      String invoiceQuery="";
      invoiceQuery="select UPLOAD_STATUS,ORIG_APPROVER,APPROVER, to_char(APPROVED_DATE,'mm/dd/yyyy') APPROVED_DATE, QC_USER, to_char(QC_DATE,'mm/dd/yyyy') QC_DATE, AP_USER_NAME, AP_APPROVER_NAME, AP_QC_USER_NAME,CURRENCY_ID,VOUCHER_ID,VOUCHER_BILLING_LOCATION,PAYMENT_TERMS,SUPPLIER,SUPPLIER_INVOICE_ID,ORIGINAL_INVOICE_ID,to_char(INVOICE_DATE,'mm/dd/yyyy') INVOICE_DATE1,INVOICE_DATE,to_char(DATE_INVOICE_RECEIVED,'mm/dd/yyyy') DATE_INVOICE_RECEIVED,RADIAN_PO,CUSTOMER_REF_PO, ";
      invoiceQuery+="INVOICE_FILE_PATH,NET_INVOICE_AMOUNT,AP_NOTE,PYMT_TERM_START_DATE,DATE_TO_PAY,REMIT_TO_LOC_ID,SUPPLIER_CONTACT_ID,ORIGINAL_SUPPLIER,STATUS,invoice_amount";
      invoiceQuery+=" from voucher_view where radian_po = " + radianPO + " and VOUCHER_ID = '" + contactId + "' order by INVOICE_DATE desc";
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
        voucherStatus=rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ).trim();
        paymentterms=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).trim();
        apnote=rs.getString( "AP_NOTE" ) == null ? "" : rs.getString( "AP_NOTE" ).trim();
        invoiceAmount=rs.getString( "INVOICE_AMOUNT" ) == null ? "" : rs.getString( "INVOICE_AMOUNT" ).trim();
        voucherbillingaddress=rs.getString( "VOUCHER_BILLING_LOCATION" ) == null ? "" : rs.getString( "VOUCHER_BILLING_LOCATION" ).trim();
				currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();
 			  approverId=rs.getString( "APPROVER" ) == null ? "" : rs.getString( "APPROVER" ).trim();
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
      e.printStackTrace();
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


		try {
		 dbrs = db.doQuery("select HUB_NAME,HAAS_CARRIER,CARRIER_SUPPLIER_ID,PAYMENT_TERMS,SUPPLIER from po_header_view where radian_po = " +
			radianPO + " ");
		 rs = dbrs.getResultSet();

		 while (rs.next()) {
			/*paymentterms=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).trim();
						 haascarrier=rs.getString( "HAAS_CARRIER" ) == null ? "" : rs.getString( "HAAS_CARRIER" ).trim();
						 carriesupid=rs.getString( "CARRIER_SUPPLIER_ID" ) == null ? "" : rs.getString( "CARRIER_SUPPLIER_ID" ).trim();
				supplierID=rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ).trim();*/
			hubName = rs.getString("HUB_NAME") == null ? "" : rs.getString("HUB_NAME").trim();
		 }
			 }
		catch (Exception e) {
		 e.printStackTrace();
		}
		finally {
		 if (dbrs != null) {
			dbrs.close();
		 }
	}

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

  if (!(paymentterms.length() > 0 )){paymentterms = "Net 45";}

  int suppbillingaddcount = 0;
  String suppbillinglocation = "";

  if ( !editinvocie )
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
      e.printStackTrace();
    }
    finally
    {
      if (dbrs != null)  { dbrs.close(); }
    }
    voucherbillingaddress=suppbillinglocation;
  }

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
    e.printStackTrace();
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
  apout.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\" onSubmit=\"return SubmitOnlyOnce()\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Update\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"suppID\" VALUE=\""+supplierID+"\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"supplierid\" VALUE=\""+supplierID+"\">\n");
  apout.println("<INPUT TYPE=\"hidden\" NAME=\"contactid\" VALUE=\""+contactId+"\">\n");
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
				Msgsucar.append( suppliername1 );
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

  apout.println("<TR><TD WIDTH=\"3%\" ALIGN=\"RIGHT\"><B>Supplier:</B></TD>\n");
  {
    apout.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
    //apout.println( "<SELECT CLASS=\"HEADER\" NAME=\"choicesuppid\" ID=\"choicesuppid\" ONCHANGE=\"changesuppid()\" size=\"1\">\n" );
    apout.println( Msgsucar );
    //apout.println( "</SELECT>\n" );
	apout.println( "</TD>\n" );
	apout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	apout.println( "<B>Currency:</B>\n" );
	apout.println( "</TD>\n" );

	apout.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	apout.println(currencyid);
/*	apout.println( "<SELECT CLASS=\"HEADER\" NAME=\"currency\" ID=\"currency\" size=\"1\">\n" );
	apout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	apout.println( radian.web.HTMLHelpObj.getDropDown( vvCurrency,currencyid ) );
	apout.println( "</SELECT>\n" );
*/
	apout.println( "</TD>\n" );

    apout.println("</TR>\n");
  }

  //first row
  apout.println("<TR>\n");
  //Invoice
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Invoice:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  if ( editinvocie && !"In Progress".equalsIgnoreCase(voucherStatus))
  {
    apout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invoices\" ID=\"invoices\" value=\""+supplier_invoice_id+"\" size=\"20\" MAXLENGTH=\"30\" readonly>\n");
  }
  /*else
  {
    apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoices\" ID=\"invoices\" value=\""+supplier_invoice_id+"\" size=\"20\" MAXLENGTH=\"30\">\n");
  }*/
  apout.println("</TD>\n");
  //Invoice Date
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Inv Date:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n");
	apout.println(invoice_date);
  //apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoicedate\" ID=\"invoicedate\" value=\""+invoice_date+"\" size=\"10\" MAXLENGTH=\"10\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"invoicedatelink\" onClick=\"return getCalendar(document.SupplierLike.invoicedate);\">&diams;</a></FONT>\n");
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
	apout.println(original_invoice_id);
  //apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"refinvoice\" ID=\"refinvoice\" value=\""+original_invoice_id+"\" size=\"10\" MAXLENGTH=\"30\">\n");
  apout.println("</TD>\n");
  //Date Received
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Date Received:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n");
	apout.println(date_invoice_received);
  //apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invdatereceived\" ID=\"invdatereceived\" value=\""+date_invoice_received+"\" size=\"10\" MAXLENGTH=\"10\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"invdatereceivedlink\" onClick=\"return getCalendar(document.SupplierLike.invdatereceived);\">&diams;</a></FONT>\n");
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
  apout.println("<B>Inv Amt:</B>\n");
  apout.println("</TD>\n");
  apout.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
	apout.println( voucherTotal.setScale(4,4));
  /*if ( editinvocie )
  {
    apout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invamount\" ID=\"invamount\" value=\"" + voucherTotal.setScale(4,4) + "\" onChange=\"changeNetInvAmount()\" size=\"10\">\n" );
  }
  else
  {
    apout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invamount\" ID=\"invamount\" value=\"\" onChange=\"changeNetInvAmount()\" size=\"10\">\n" );
  }*/
  //apout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invamount\" ID=\"invamount\" value=\""+invoiceAmount+"\" size=\"10\">\n");
  apout.println("</TD>\n");
  apout.println("</TR>\n");

  //fourth row
  apout.println("<TR>\n");
  //Remit To Line 2
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n");
  //apout.println("&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"searchbillingAddress()\">\n");
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
	apout.println(voucherTotal.setScale(4,4));
  /*if ( editinvocie )
  {
    apout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"refused\" ID=\"refused\" value=\"" + voucherTotal.setScale(4,4) + "\" onChange=\"changeNetInvAmount()\" size=\"10\">\n" );
  }
  else
  {
    apout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"refused\" ID=\"refused\" value=\"\" onChange=\"changeNetInvAmount()\" size=\"10\">\n" );
  }*/
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
  apout.println("<B>Status:</B>\n");
  apout.println("</TD>\n");

  apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n");
	try {
	 if (qcDate.trim().length() == 0 && this.getVoucherQcStatus()) {
		apout.println("<SELECT CLASS=\"HEADER\" NAME=\"invstatus\" ID=\"invstatus\" onChange=\"removeQcButton()\" size=\"1\">\n");

		if (voucherStatus.equalsIgnoreCase("Approved")) {
		 apout.println("<option selected value=\"Approved\">Approved</option>\n");
		 //apout.println("<option value=\"Cancelled\">Cancelled</option>\n");
		 apout.println("<option value=\"In Progress\">In Progress</option>\n");
		}
		else if (voucherStatus.equalsIgnoreCase("Cancelled")) {
		 //apout.println("<option value=\"Approved\">Approved</option>\n");
		 apout.println("<option selected value=\"Cancelled\">Cancelled</option>\n");
		 apout.println("<option value=\"In Progress\">In Progress</option>\n");
		}
		else {
		 //apout.println("<option value=\"Approved\">Approved</option>\n");
		 //apout.println("<option value=\"Cancelled\">Cancelled</option>\n");
		 apout.println("<option selected value=\"In Progress\">In Progress</option>\n");
		}
		apout.println("</SELECT>\n");
	 }
	 else {
		apout.println(voucherStatus);
	 }
	}
	catch (Exception ex) {
	}
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
	//Approved By
	apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	apout.println("<B>Approved By:</B>\n");
	apout.println("</TD>\n");

	apout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n");
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
  //AP Notes
  apout.println("<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
  apout.println("<B>Comments:</B>\n");
  apout.println("</TD>\n");

  apout.println("<TD WIDTH=\"15%\" ALIGN=\"left\" COLSPAN=\"3\" CLASS=\"announce\">\n");
  apout.println("<TEXTAREA name=\"apcomments\" rows=\"3\" cols=\"80\">"+apnote+"</TEXTAREA></TD>\n");
  apout.println("</TD>\n");

  apout.println("</TR>\n");


  apout.println("</TABLE>\n");
  apout.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Save\" name=\"CreateNew\">\n");
	try {
	 if (qcDate.trim().length() == 0 && this.getVoucherQcStatus() &&	voucherStatus.equalsIgnoreCase("Approved") && !approverId.equalsIgnoreCase(privatepersonnelId)) {
		apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"voucherQcButton\" value=\"QC\" OnClick=\"updateActionToQC()\" type=\"submit\">\n");
	 }
	}
	catch (Exception ex1) {
	}
  apout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
  apout.println("</FORM></BODY></HTML>\n");

  return;
 }

 protected void printJavaScripts(PrintWriter apout)
 {
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
}