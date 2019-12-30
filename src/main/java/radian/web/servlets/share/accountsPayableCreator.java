package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcmis.common.admin.beans.PersonnelBean;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

import java.util.Locale;
import java.math.BigDecimal;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-20-02
 * make changes so that can not edit address from main page
 *
 * 1-25-02
 * change editBillingAddress to searchbillingAddress since user are not allow to edit 'Remit To' addresses, they could only add new one
 * also add 'verified' button, user need to verify that the 'Remit To' address is valid
 * 05-19-03 Doing commit before calling the procedure clean up code and added date sent for payment and invoice URL
 * 07-10-03 Adding PO Net Terms and haas carrier stuff
 * 08-06-03 Added the Ship To Address
 * 08-15-03 - Sending error emails to Nish and code cleanup
 * 10-23-03 - Can not change billing address if the voucher is approved and sent to Haas
 * 12-18-03 - Fixed table borders overlaping
 * 12-22-03 - Code Refactoring, moved vv_stuff to different class
 * 01-30-04 - Showing only Active Payment Terms for PO pages and showing all for AP pages
 * 03-23-04 - Dividing voucherqty by JDE factor for creditqtys.
 * 09-06-04 - Providing a link to expedite notes page
 * 01-10-05 - Showing Consigned flag.
 * 04-26-05 - Showing Home Company Name
 */

public class accountsPayableCreator
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private boolean completeSuccess = true ;
    private boolean allowupdate;
    private String globalErrorCode = "";
		private boolean intcmIsApplication = false;
	  private static org.apache.log4j.Logger aplog = org.apache.log4j.Logger.getLogger("tcmis.web");
    private ResourceLibrary res = null; // res means resource.

    public accountsPayableCreator(ServerResourceBundle b, TcmISDBModel d)
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

    public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession posession,PrintWriter out )
       throws ServletException,IOException
    {
      //PrintWriter out=response.getWriter();
      response.setContentType( "text/html" );

      String branch_plant= posession.getAttribute( "BRANCH_PLANT" ) == null ? "" : posession.getAttribute( "BRANCH_PLANT" ).toString();
      String personnelid= posession.getAttribute( "PERSONNELID" ) == null ? "" : posession.getAttribute( "PERSONNELID" ).toString();
      res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)posession.getAttribute("tcmISLocale"));
      PersonnelBean personnelBean = (PersonnelBean) posession.getAttribute("personnelBean");
			Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			intcmIsApplication = false;
			if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
			{
			 intcmIsApplication = true;
			}

      String User_Action=null;
      String like="";
      String itemOrSpec="Item";
      String CompanyId="";
      String request_id=null;
      String CompNum="0";
      String subUserAction="";
      String StatusofReqdrop="";

      User_Action=request.getParameter( "Action" );
      if ( User_Action == null )
        User_Action="New";
      if ( User_Action.length() == 0 )
        User_Action="New";

      subUserAction=request.getParameter( "subUserAction" );
      if ( subUserAction == null )
        subUserAction="";

      request_id=request.getParameter( "request_id" );
      if ( request_id == null )
        request_id="";

      like=request.getParameter( "like" );
      if ( like == null )
        like="";

      itemOrSpec=request.getParameter( "isTab" );
      if ( itemOrSpec == null )
        itemOrSpec="Item";

      CompanyId=request.getParameter( "Company" );
      if ( CompanyId == null )
        CompanyId=" ";

      CompNum=request.getParameter( "CompNum" );
      if ( CompNum == null )
        CompNum="0";

      StatusofReqdrop=request.getParameter( "StatusofReq" );
      if ( StatusofReqdrop == null )
        StatusofReqdrop="";

      String userpartnumber=request.getParameter( "userpartnumber" );
      if ( userpartnumber == null )
        userpartnumber="";
      userpartnumber=userpartnumber.trim();

      String selectedrowid=request.getParameter( "selectedrowid" );
      if ( selectedrowid == null )
        selectedrowid="";
      selectedrowid=selectedrowid.trim();

	String selectedInvoiceId=request.getParameter( "selectedInvoiceId" );
	if ( selectedInvoiceId == null )
	 selectedInvoiceId="";
	selectedInvoiceId=selectedInvoiceId.trim();

      String radianPo=request.getParameter( "po" );
      if ( radianPo == null )
        radianPo="";
      radianPo=radianPo.trim();

      String validPo=request.getParameter( "validpo" );
      if ( validPo == null )
        validPo="";
      validPo=validPo.trim();

      String blanketPO=request.getParameter( "bpo" );
      if ( blanketPO == null )
        blanketPO="";
      blanketPO=blanketPO.trim();

      String validbPo=request.getParameter( "validbpo" );
      if ( validbPo == null )
        validbPo="";
      validbPo=validbPo.trim();

      String HubName=request.getParameter( "HubName" );
      if ( HubName == null )
        HubName="";
      HubName=HubName.trim();

      posession.setAttribute( "REQUEST_ID",request_id );
      Long startTime = Long.valueOf(System.currentTimeMillis());
      posession.setAttribute( "startTime",startTime );
      try
      {
		Hashtable hub_list_set = new Hashtable();
		hub_list_set= (Hashtable)posession.getAttribute("HUB_PREF_LIST");

        String donevvstuff=posession.getAttribute( "VVAPSTUFF" ) == null ? "" : posession.getAttribute( "VVAPSTUFF" ).toString();

        if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
        {
          posession.setAttribute( "VV_PAYMENT",radian.web.vvHelpObj.getPaymentTerms(db,true) );
          posession.setAttribute( "VV_VOUCHERSTATUS",radian.web.vvHelpObj.getvvvouvherstatus(db) );
          posession.setAttribute( "LORCLERKNAMES",radian.web.HTMLHelpObj.getLorClerknames( db ) );
          posession.setAttribute( "VVAPSTUFF","Yes" );
        }

        if ( User_Action.equalsIgnoreCase( "New" ) )
        {
          Vector vvPayment=new Vector();
          Vector vvvouvherstatus=new Vector();

          donevvstuff = "";
          donevvstuff=posession.getAttribute( "VVAPSTUFF" ) == null ? "" : posession.getAttribute( "VVAPSTUFF" ).toString();

          if ( donevvstuff.equalsIgnoreCase( "Yes" ) )
          {
            vvPayment= ( Vector ) posession.getAttribute( "VV_PAYMENT" );
            vvvouvherstatus= ( Vector ) posession.getAttribute( "VV_VOUCHERSTATUS" );
          }
          else
          {
            vvPayment=radian.web.vvHelpObj.getPaymentTerms(db,true);
            vvvouvherstatus=radian.web.vvHelpObj.getvvvouvherstatus(db);
            posession.setAttribute( "VV_PAYMENT",vvPayment );
            posession.setAttribute( "VV_VOUCHERSTATUS",vvvouvherstatus );
            posession.setAttribute( "LORCLERKNAMES",radian.web.HTMLHelpObj.getLorClerknames( db ) );
            posession.setAttribute( "VVAPSTUFF","Yes" );
          }

          if ( this.getupdateStatus() )
          {
            buildHeader( branch_plant,"","",User_Action,subUserAction,selectedrowid,selectedInvoiceId,out);
          }
          else
          {
            buildReadonlyHeader( branch_plant,"","",User_Action,subUserAction,selectedrowid,vvvouvherstatus,selectedInvoiceId,out);
          }

          vvPayment=null;
          vvvouvherstatus=null;
        }
        else if ( User_Action.equalsIgnoreCase( "searchlike" ) )
        {
          Vector vvPayment= ( Vector ) posession.getAttribute( "VV_PAYMENT" );
          Vector vvvouvherstatus= ( Vector ) posession.getAttribute( "VV_VOUCHERSTATUS" );

		  boolean readonly=false;
		  boolean nopermissions=false;
		  DBResultSet dbrs = null;
		  ResultSet rs = null;

		  if ( radianPo.trim().length() > 0 && "po".equalsIgnoreCase( subUserAction ) )
		  {
			String testbranchplant="";
			String dbuylockstatus="";

			try
			{
			  String query="select BRANCH_PLANT,RADIAN_PO,DBUY_LOCK_STATUS from po_header_view where RADIAN_PO = '" + radianPo + "' ";

			  dbrs=db.doQuery( query );
			  rs=dbrs.getResultSet();
			  while ( rs.next() )
			  {
				testbranchplant= ( rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ) );
				//dbuylockstatus= ( rs.getString( "DBUY_LOCK_STATUS" ) == null ? "" : rs.getString( "DBUY_LOCK_STATUS" ) );
			  }

			  Hashtable hubsetdetails= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
			  String hubnum =hubsetdetails.get( testbranchplant ) == null ? "" : hubsetdetails.get( testbranchplant ).toString();

			  //polog.info(hubnum);
			if (testbranchplant.trim().length() > 0)
			{
			  if (hubnum.length() == 0)
			  {
				nopermissions = true;
				//radian.web.emailHelpObj.sendnawazemail("No Permissions in Accounts Payable Page","No Permissions for PO "+radianPo+" Hub on PO "+testbranchplant+" Personnel Id:"+personnelid+"");
		    String[] supportEmails = new String[]{"Angela.Torres@HaasGroupIntl.com,mboze@haastcm.com,mblackburn@haastcm.com,dbertero@haastcm.com"};
  			HelpObjs.javaSendMail("","No Permissions in Accounts Payable Page",supportEmails,"No Permissions for PO "+radianPo+". Hub on PO "+testbranchplant+" for Personnel Id:"+personnelid+"");
			  }
			  else
			  {
				String hubname=radian.web.HTMLHelpObj.gethubNamefromBP( hub_list_set,testbranchplant );
				//PersonnelBean personnelBean=posession.getAttribute( "personnelBean" ) == null ? null :( PersonnelBean ) posession.getAttribute( "personnelBean" );
				if ( !personnelBean.getPermissionBean().hasFacilityPermission ("Acountspayable",hubname,null ) )
				{
				  readonly=true;
				}
				//aplog.info( "User Group Id: Acountspayable  Facility Id: " + hubname + "  " + personnelBean.getPermissionBean().hasFacilityPermission ("Acountspayable",hubname,null )+ "" );
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
		  }

		  if ( nopermissions && this.getupdateStatus())
		  {
			 buildHeader( branch_plant,radianPo,res.getString("msg.nopermviewpo"),"New",subUserAction,selectedrowid,selectedInvoiceId,out);
		  }
		  else if ( nopermissions && !this.getupdateStatus())
		  {
			buildReadonlyHeader( branch_plant,radianPo,res.getString("msg.nopermviewpo"),"New",subUserAction,selectedrowid,vvvouvherstatus,selectedInvoiceId,out);
		  }
          else if ( this.getupdateStatus() && !readonly )
          {
            buildHeader( branch_plant,radianPo,"",User_Action,subUserAction,selectedrowid,selectedInvoiceId,out);
          }
          else
          {
            buildReadonlyHeader( branch_plant,radianPo,"",User_Action,subUserAction,selectedrowid,vvvouvherstatus,selectedInvoiceId,out);
          }

          vvPayment=null;
          vvvouvherstatus=null;
        }
        else if ( User_Action.equalsIgnoreCase( "updateapstuff" ) )
        {
          Vector vvPayment= ( Vector ) posession.getAttribute( "VV_PAYMENT" );
          Vector vvvouvherstatus= ( Vector ) posession.getAttribute( "VV_VOUCHERSTATUS" );
          Hashtable synch_data=synchServerData( request );

          String blanketorpo="";
          Hashtable updateresults=new Hashtable();
          if ( ( radianPo.length() > 0 ) && ( "Yes".equalsIgnoreCase( validPo ) ) )
          {
            updateresults=updatePoDetails( synch_data,personnelid );
            blanketorpo="Purchase Order";
          }
          else
          {
            completeSuccess=false;
          }

          globalErrorCode=HelpObjs.addescapesForJavascript( globalErrorCode );
          buildHeader( branch_plant,radianPo,globalErrorCode,User_Action,subUserAction,selectedrowid,selectedInvoiceId,out);

          synch_data=null;
          updateresults=null;

          vvPayment=null;
          vvvouvherstatus=null;
        }
        else if ( User_Action.equalsIgnoreCase( "reversevoucher" ) )
        {
          String voucherId= request.getParameter("invoices");
          String supplierInvoiceId=  request.getParameter("supplierInvoiceId");
          String reverseNote=  request.getParameter("reverseNote");
          int pid = personnelBean.getPersonnelId();

		  DBResultSet dbrs = null;
		  String res = "";
		  Connection connect1=null;
		  CallableStatement cs=null;

		  try
		  {
		    connect1=db.getConnection();
		    connect1.setAutoCommit(false);
		    cs=connect1.prepareCall( "{call pkg_process_vendor_invoice.p_voucher_reverse(?,?,?,?,?,?)}" );
		    cs.setString(1,radianPo);
	        cs.setString(2,supplierInvoiceId);
	        cs.setString(3,voucherId);
	        cs.setString(4,reverseNote);
	        cs.setInt(5,pid);
            cs.registerOutParameter(6,java.sql.Types.VARCHAR );
            aplog.info("pkg_process_vendor_invoice.p_voucher_reverse("+radianPo+ "," + supplierInvoiceId + "," + voucherId + "," + reverseNote + "," + pid);
		    cs.execute();
		    connect1.setAutoCommit(true);
	        res = cs.getString(6);
		  }
		  catch ( Exception e )
		  {
		    e.printStackTrace();
		    completeSuccess=false;
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
		    if ( dbrs != null ) {dbrs.close();}
		  }

          globalErrorCode=HelpObjs.addescapesForJavascript( res );
          buildHeader( branch_plant,radianPo,globalErrorCode,User_Action,subUserAction,selectedrowid,selectedInvoiceId,out);
        }
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
      }
      finally
      {
        out.close();
      }
    }

  private void buildHeader( String hub,String radianPo1,String message,String userAction,String subUserAction,String selectedrowid,String selectedInvoiceId,PrintWriter out )
  {
    //StringBuffer Msg=new StringBuffer();

    try
    {
      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "accountsPayable","accountspayable.js",intcmIsApplication,res ) );
      out.println( "<script src=\"/clientscripts/FormChek.js\" language=\"JavaScript\"></script>\n" );
      out.println( "<script src=\"/clientscripts/appovalidation.js\" language=\"JavaScript\"></script>\n" );
      out.println( "<script src=\"/clientscripts/appopopups.js\" language=\"JavaScript\"></script>\n" );
      out.println( "<script src=\"/clientscripts/calendar.js\" language=\"JavaScript\"></script>\n" );
      out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );
      out.println( "</HEAD>  \n" );

      if ( !userAction.equalsIgnoreCase( "New" ) )
      {
        if ( radianPo1.length() > 0 || "po".equalsIgnoreCase( subUserAction ) )
        {
          out.println( "<BODY onLoad=\"recalPo('" + radianPo1 + "','" + message + "','po','" + hub + "','" + selectedrowid + "','"+selectedInvoiceId+"')\">\n" );
        }
      }
      else
      {
        out.println( "<BODY>\n" );
      }

      out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
      out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT></center>\n" );
      out.println( "</DIV>\n" );
      out.println( "<DIV ID=\"MAINPAGE\">\n" );
      out.println( radian.web.HTMLHelpObj.printApTitleBar( "<B>"+res.getString("accountsPayable")+"</B>\n" ) );
      out.println( "<FORM method=\"POST\" NAME=\"purchaseorder\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString( "INVOICE_PURCHASE_ORDER" ) + "\">\n" );
      out.println( "<P ID=\"mainpara\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" ID=\"Action\" NAME=\"Action\" VALUE=\"updateapstuff\">\n" );

      out.println( "<TABLE BORDER=\"0\" ID=\"mainheadertable\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"100%\" COLSPAN=\"8\" HEIGHT=\"5\" ALIGN=\"LEFT\" ID=\"timeElapsed\"></TD>\n" );
      out.println( "</TR>\n" );
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"100%\" COLSPAN=\"6\" HEIGHT=\"5\" ALIGN=\"CENTER\" ID=\"messagerow\"><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>" + message + "</B></FONT></TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //PO
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.po")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"po\" ID=\"po\" value=\""+radianPo1+"\" onChange=\"invalidatePo()\" size=\"8\">\n" );
      out.println( "<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchpolike\" id=\"searchpolike\" value=\"...\" OnClick=\"getPo()\"><IMG src=\"/images/search_small.gif\" alt=\"PO\"></BUTTON>\n" );
      out.println( "</TD>\n" );

      //Invoice
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<A HREF=\"javascript:showinvoicesummary()\"><B>"+res.getString("label.invoice")+":</B></A>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"25%\" ALIGN=\"left\" CLASS=\"announce\" COLSPAN=\"3\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\" NAME=\"invoices\" ID=\"invoices\" ONCHANGE=\"getInvoiceData()\" size=\"1\">\n" );
      out.println( "<OPTION VALUE=\"\">"+res.getString("label.none")+"</OPTION>\n" );
      out.println( "</SELECT>\n" );
      out.println( "<INPUT TYPE=\"button\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"newinvoice\" id=\"newinvoice\" value=\"New\" OnClick=\"getnewinvoice(this.form)\">\n" );
		out.println( "<INPUT TYPE=\"button\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"editinvoicedata\" id=\"editinvoicedata\" value=\""+res.getString("label.edit")+"\" OnClick=\"editinvoice(this.form)\">\n" );
	  out.println( "<INPUT TYPE=\"button\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"resetinvoice\" id=\"resetinvoice\" value=\""+res.getString("label.edit")+"\" STYLE=\"display: none;\">\n" );
  	  out.println( "<INPUT TYPE=\"button\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"qcvoucher\" id=\"qcvoucher\" STYLE=\"display: none;\" value=\""+res.getString("label.qc")+"\">\n" );
	  out.println( "<INPUT TYPE=\"submit\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"voucherreverse\" id=\"voucherreverse\" STYLE=\"display: none;\" value=\""+res.getString("label.reverse")+"\" OnClick=\"vRev()\">\n" );
	  out.println( "</TD>\n" );
      //Date sent for payment
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\" COLSPAN=\"1\">\n" );
      out.println( "<B>"+res.getString("label.datesentforpayment")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"datesentforpayment\" ID=\"datesentforpayment\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );

      out.println( "</TR>\n" );

      //PO Terms
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.terms")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" HEIGHT=\"5\" ALIGN=\"LEFT\" ID=\"poneterms\" CLASS=\"announce\"></TD>\n" );

	  //Consigned PO
	  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  out.println( "<B>"+res.getString("label.consigned")+":</B>\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"consignedpo\" CLASS=\"announce\"></TD>\n" );

	  //Home Company
	  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  out.println( "<B>"+res.getString("label.homecompany")+":</B>\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"homecompany\" CLASS=\"announce\"></TD>\n" );

	  //Currency
	  out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "<B>"+res.getString("label.currency")+":</B>\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"currency\" ID=\"currency\" value=\"\" size=\"10\" readonly>\n" );
	  out.println( "</TD>\n" );

      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //Supplier Line 1
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.supplier")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"supplierline1\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );

      //Remit To Line 1
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.remitto")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline1\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );

      //Invoice Date
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.invoicedate")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invoicedate\" ID=\"invoicedate\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );

      //Amount
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.invamt")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invamount\" ID=\"invamount\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //Supplier Line 2
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" ID=\"supplierIdDisplay\" CLASS=\"announce\">\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"supplierline2\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );

      //Remit To Line 2
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
        out.println( "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"editsearchbillingAddress\" id=\"editsearchbillingAddress\" value=\""+res.getString("label.search")+"\" OnClick=\"searchbillingAddressWithUpdate()\">\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline2\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );

      //Date Received
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.datereceived")+":</B>\n" );
      out.println( "</TD>\n" );

      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invdatereceived\" ID=\"invdatereceived\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );

      //Refused
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.refused")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"refused\" ID=\"refused\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //Supplier Line 3
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"supplierid\" ID=\"supplierid\" value=\"\" STYLE=\"display: none;\" onChange=\"invalidateSupplier()\" size=\"4\">\n" );
      out.println( "<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchsupplierlike\" id=\"searchsupplierlike\" value=\"...\" STYLE=\"display: none;\" OnClick=\"getSupplier(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\"Supplier\"></BUTTON>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"supplierline3\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );

      //Remit To Line 3
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      //11-25-02
      boolean verified=false;
      if ( verified )
      {
        out.println( "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"VerifiedB\" id=\"VerifiedB\" value=\"Verified\" STYLE=\"display: none;\" OnClick=\"billingAddressVerified()\" DISABLED>\n" );
      }
      else
      {
        out.println( "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"VerifiedB\" id=\"VerifiedB\" value=\"Verified\" STYLE=\"display: none;\" OnClick=\"billingAddressVerified()\">\n" );
      }
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline3\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );

      //Ref Invoice
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.refinvoice")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"refinvoice\" ID=\"refinvoice\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );

      //Net amount
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.netamt")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"netAmount\" ID=\"netAmount\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //Supplier Line 4
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "&nbsp;</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"supplierline4\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );

      //Remit To Line 4
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "&nbsp;</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline4\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );

      //Status
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.status")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invstatus\" ID=\"invstatus\" value=\"\" size=\"10\" readonly>\n" );
      out.println("</TD>\n");

      //Payment Terms
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.invterms")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"paymentterms\" ID=\"paymentterms\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"right\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.phone")+":</B>\n" );
      out.println( "</TD>\n" );

	 out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	 out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"mfgphoneno\" ID=\"mfgphoneno\" value=\"\" size=\"25\" readonly>\n" );
	 out.println( "</TD>\n" );

        out.println( "<TD WIDTH=\"3%\" ALIGN=\"right\" CLASS=\"announce\">\n" );
        out.println( "&nbsp;\n" );
        out.println( "</TD>\n" );

	 out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" nowrap=\"true\" CLASS=\"announce\">\n" );
     out.println( "<B>"+res.getString("label.nboscpo")+":</B>&nbsp;"+"<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"specialChargePo\" ID=\"specialChargePo\" value=\"\" readonly>\n" );
     out.println( "</TD>\n" );

	 //Approved By
	 out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" COLSPAN=\"2\" CLASS=\"announce\">\n" );
	 out.println( "<B>"+res.getString("label.approvedby")+":</B>\n" );
	 out.println( "</TD>\n" );
	 out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" ID=\"approvedby\" CLASS=\"announce\">\n" );
	 out.println( "&nbsp;" );
	 out.println("</TD>\n");
	 //QCed By
	 out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	 out.println( "<B>"+res.getString("label.qcedby")+":</B>\n" );
	 out.println( "</TD>\n" );
	 out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" ID=\"qcedby\" CLASS=\"announce\">\n" );
	 out.println( "&nbsp;" );
	 out.println("</TD>\n");
	 out.println( "</TR>\n" );

	 out.println( "<TR>\n" );
	 out.println( "<TD WIDTH=\"10%\" ALIGN=\"right\" CLASS=\"announce\">\n" );
	 out.println( "<B>"+res.getString("label.ordertakenby")+":</B>\n" );
	 out.println( "</TD>\n" );

	 out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" ID=\"supplierContactInfo\" CLASS=\"announce\">\n" );
	 out.println( "&nbsp;" );
	 out.println( "</TD>\n" );

      out.println( "<TD WIDTH=\"3%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\"><B>"+res.getString("label.comments")+":</B></TD>\n" );
      out.println( "<TD WIDTH=\"50%\" COLSPAN=\"6\" ID=\"invoicecomments\" ALIGN=\"LEFT\" HEIGHT=\"5\"></TD></TR>\n" );

      out.println( "<TR><TD WIDTH=\"3%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\"><B>"+res.getString("label.buyer")+":</B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" COLSPAN=\"1\" ALIGN=\"LEFT\" ID=\"buyernameandstuff\" HEIGHT=\"5\">\n" );
      out.println( "<TD WIDTH=\"5%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\"><B>"+res.getString("label.invoiceurl")+":</B></TD>\n" );
      out.println( "<TD WIDTH=\"50%\" COLSPAN=\"6\" ALIGN=\"LEFT\" ID=\"invoiceurl\" HEIGHT=\"5\">\n" );
      out.println( "</TD></TR>\n" );

      out.println( "<TR><TD WIDTH=\"3%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\">&nbsp</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" COLSPAN=\"1\" ALIGN=\"LEFT\" HEIGHT=\"5\">&nbsp</TD>\n" );
      out.println( "<TD WIDTH=\"5%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\"><B>"+res.getString("label.uploadstatus")+":</B></TD>\n" );
      out.println( "<TD WIDTH=\"50%\" COLSPAN=\"6\" ALIGN=\"LEFT\" ID=\"uploadStatus\" HEIGHT=\"5\">\n" );
      out.println( "</TD></TR>\n" );

  out.println("<TR><td COLSPAN=\"5\" ></td><td COLSPAN=\"3\" ><div id=\"taxDataDiv\" STYLE=\"display: none;\"><TABLE BORDER=\"0\" width=\"300\" CELLPADDING=\"3\" CELLSPACING=\"0\">\n");
  out.println("<tr><TH WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  out.println("<B>Tax Type</B>\n");
  out.println("</TH>\n");
  out.println("<TH WIDTH=\"15%\" ALIGN=\"right\" CLASS=\"announce\">\n");
  out.println("<B>Amount</B>\n");
  out.println("</TH>\n");
  out.println("<TH WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  out.println("<B>Currency</B>\n");
  out.println("</TH></tr>\n");

  out.println("<tr><TD WIDTH=\"15%\" ALIGN=\"left\" id=\"taxItemId1\" CLASS=\"announce\">\n");
  //out.println(taxItemId1);
  out.println("</TD>\n");

  out.println("<TD WIDTH=\"15%\" ALIGN=\"right\" id=\"taxAmount1\" CLASS=\"announce\">\n");
  ///out.println(taxAmount1);
  out.println("</TD>\n");

  out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" id=\"taxCurrency1\" CLASS=\"announce\">\n" );
  //out.println(taxCurrency1);
  out.println("</tr>\n");

  out.println("<tr><TD WIDTH=\"15%\" ALIGN=\"left\" id=\"taxItemId2\" CLASS=\"announce\">\n");
  //out.println(taxItemId2);
  out.println("</TD>\n");

  out.println("<TD WIDTH=\"15%\" ALIGN=\"right\" id=\"taxAmount2\" CLASS=\"announce\">\n");
  //out.println(taxAmount2);
  out.println("</TD>\n");
  out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" id=\"taxCurrency2\" CLASS=\"announce\">\n" );
  //out.println(taxCurrency2);
  out.println("</tr>\n");
  out.println("</TD></TABLE></div></td></TR>\n");

      out.println( "<TR>\n" );
	  out.println( "<TD WIDTH=\"5%\" ID=\"expeditenotes\" COLSPAN=\"2\" ALIGN=\"LEFT\"></TD>\n" );
      //Add Line
      out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\"  ALIGN=\"LEFT\" COLSPAN=\"7\"  VALIGN=\"MIDDLE\">\n" );
      out.println( "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.additem")+"\" onclick= \"getChargeItemDetail('')\" NAME=\"addLineButton\" ID=\"addLineButton\">&nbsp;\n" );
      out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMITW\" VALUE=\"\" onclick= \"return donothing(this)\" NAME=\"nothing\" ID=\"nothing\">\n" );
      //Update
      out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.update")+"\" onclick= \"return actionValue('updateapstuff')\" NAME=\"updateapstuff\" ID=\"updateapstuff\">&nbsp;\n" );

      out.println( "</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "</TABLE>\n" );

      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validpo\" ID=\"validpo\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validbpo\" ID=\"validbpo\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validsupplier\" ID=\"validsupplier\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validshipto\" ID=\"validshipto\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validcarrier\" ID=\"validcarrier\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validordertaker\" ID=\"validordertaker\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"totallines\" ID=\"totallines\" VALUE=\"0\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"numofHubs\" ID=\"numofHubs\" VALUE=\"0\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"voucherbillinglocId\" ID=\"voucherbillinglocId\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"selectedrowid\" ID=\"selectedrowid\" VALUE=\"row1\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"haascarrier\" ID=\"haascarrier\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"carriersuppid\" ID=\"carriersuppid\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"supplierInvoiceId\" ID=\"supplierInvoiceId\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"reverseNote\" ID=\"reverseNote\" VALUE=\"\">\n" );

	  out.println( "<BR><BR>\n" );

      out.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"922\" CLASS=\"moveup\">\n" );
      out.println( "</TABLE>\n" );
      out.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column150\">\n" );

      //Write code to insert rows here
      out.println( "<TABLE ID=\"linetable\" CLASS=\"columnar\" BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"900\">\n" );
      out.println( "</TABLE>\n" );

      //Invisible Hidden elements in the page
      out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.println( "<tr><td>" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" ID=\"UserAction\" VALUE=\"NEW\">\n" );
      out.println( "</TD></tr>" );
      out.println( "</table>\n" );

      //To show the PO Ship TO Address
      out.println( "<DIV ID=\"poshiptoaddress\">\n" );
      out.println( "<TABLE ID=\"shitpaddtable\" CLASS=\"moveup\" BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"300\">\n" );
      out.println( "<TR><TD><B>"+res.getString("label.shiptoaddress")+":</B></TD></TR>" );
      out.println( "<TR><TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"shiptoaddress\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD></TR>\n" );
      out.println( "</TABLE>\n" );

      out.println( "</P>\n" );
      out.println( "</FORM>\n" );
      out.println( "</BODY>\n" );
      out.println( "</HTML>\n" );
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
    }

    return;
  }

  private void buildReadonlyHeader( String hub,String radianPo1,String message,String userAction,String subUserAction,String selectedrowid,Vector vvvoucherStatus,String selectedInvoiceId,PrintWriter out )
  {
    //StringBuffer Msg=new StringBuffer();

    try
    {
      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Accounts Payable","readonlyaccountspayable.js",intcmIsApplication ) );
      out.println( "<script src=\"/clientscripts/FormChek.js\" language=\"JavaScript\"></script>\n" );
      out.println( "<script src=\"/clientscripts/appovalidation.js\" language=\"JavaScript\"></script>\n" );
      out.println( "<script src=\"/clientscripts/appopopups.js\" language=\"JavaScript\"></script>\n" );
      out.println( "<script src=\"/clientscripts/calendar.js\" language=\"JavaScript\"></script>\n" );
      out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );

      out.println( "</HEAD>  \n" );

      if ( !userAction.equalsIgnoreCase( "New" ) )
      {
        if ( radianPo1.length() > 0 || "po".equalsIgnoreCase( subUserAction ) )
        {
          out.println( "<BODY onLoad=\"recalPo('" + radianPo1 + "','" + message + "','po','" + hub + "','" + selectedrowid + "','"+selectedInvoiceId+"')\">\n" );
        }
      }
      else
      {
        out.println( "<BODY>\n" );
      }

      out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
      out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT></center>\n" );
      out.println( "</DIV>\n" );
      out.println( "<DIV ID=\"MAINPAGE\">\n" );
      out.println( radian.web.HTMLHelpObj.printApTitleBar( "<B>"+res.getString("accountsPayable")+"</B>\n" ) );
      out.println( "<FORM method=\"POST\" NAME=\"purchaseorder\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString( "INVOICE_PURCHASE_ORDER" ) + "\">\n" );
      out.println( "<P ID=\"mainpara\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" ID=\"Action\" VALUE=\"updateapstuff\">\n" );
      out.println( "<TABLE BORDER=\"0\" ID=\"mainheadertable\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"100%\" COLSPAN=\"8\" HEIGHT=\"5\" ALIGN=\"LEFT\" ID=\"timeElapsed\"></TD>\n" );
      out.println( "</TR>\n" );
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"100%\" COLSPAN=\"6\" HEIGHT=\"5\" ALIGN=\"CENTER\" ID=\"messagerow\"><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>" + message + "</B></FONT></TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //PO
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.po")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"po\" ID=\"po\" value=\""+radianPo1+"\" onChange=\"invalidatePo()\" size=\"8\">\n" );
      out.println( "<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchpolike\" id=\"searchpolike\" value=\"...\" OnClick=\"getPo()\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.po")+"\"></BUTTON>\n" );
      out.println( "</TD>\n" );
      //Invoice
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<A HREF=\"javascript:showinvoicesummary()\"><B>"+res.getString("label.invoice")+":</B></A>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"25%\" ALIGN=\"left\" CLASS=\"announce\" COLSPAN=\"3\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\" NAME=\"invoices\" ID=\"invoices\" ONCHANGE=\"getInvoiceData()\" size=\"1\">\n" );
      out.println( "<OPTION VALUE=\"\">"+res.getString("label.none")+"</OPTION>\n" );
      out.println( "</SELECT>\n" );
      out.println( "<INPUT TYPE=\"button\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"newinvoice\" id=\"newinvoice\" value=\""+res.getString("label.new")+"\" STYLE=\"display: none;\">\n" );
      out.println( "<INPUT TYPE=\"button\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"editinvoicedata\" id=\"editinvoicedata\" value=\""+res.getString("label.edit")+"\" STYLE=\"display: none;\">\n" );
	  out.println( "<INPUT TYPE=\"button\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"resetinvoice\" id=\"resetinvoice\" value=\""+res.getString("label.edit")+"\" STYLE=\"display: none;\">\n" );
  	  out.println( "<INPUT TYPE=\"button\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"qcvoucher\" id=\"qcvoucher\" STYLE=\"display: none;\" value=\""+res.getString("label.qc")+"\">\n" );
		out.println( "<INPUT TYPE=\"submit\" CLASS=\"LEFTSPACEBUTTON\" onmouseover=\"className='LEFTSPACEHOVER'\" onMouseout=\"className='LEFTSPACEBUTTON'\" name=\"voucherreverse\" id=\"voucherreverse\" STYLE=\"display: none;\" value=\""+res.getString("label.reverse")+"\" OnClick=\"voucherreverse()\">\n" );
	  out.println( "</TD>\n" );

      //Date sent for payment
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.datesentforpayment")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"datesentforpayment\" ID=\"datesentforpayment\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );

      out.println( "</TR>\n" );

	  //PO Terms
	  out.println( "<TR>\n" );
	  out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "<B>"+res.getString("label.terms")+":</B>\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"15%\" HEIGHT=\"5\" ALIGN=\"LEFT\" ID=\"poneterms\" CLASS=\"announce\"></TD>\n" );

	  //Consigned PO
	  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  out.println( "<B>"+res.getString("label.consigned")+":</B>\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"consignedpo\" CLASS=\"announce\"></TD>\n" );

	  //Home Company
	  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  out.println( "<B>"+res.getString("label.homecompany")+":</B>\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"homecompany\" CLASS=\"announce\"></TD>\n" );

	  //Currency
	  out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "<B>"+res.getString("label.currency")+":</B>\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"currency\" ID=\"currency\" value=\"\" size=\"10\" readonly>\n" );
	  out.println( "</TD>\n" );

	  out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //Supplier Line 1
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.supplier")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"supplierline1\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );
      //Remit To Line 1
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.remitto")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline1\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );
      //Invoice Date
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.invoicedate")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invoicedate\" ID=\"invoicedate\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      //Invoice Amount
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.invamt")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invamount\" ID=\"invamount\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //Supplier Line 2
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" ID=\"supplierIdDisplay\" CLASS=\"announce\">\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"supplierline2\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );
      //Remit To Line 2
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline2\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );
      //Date Received
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.datereceived")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invdatereceived\" ID=\"invdatereceived\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      //Refused
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.refused")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"refused\" ID=\"refused\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //Supplier Line 3
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"supplierid\" ID=\"supplierid\" value=\"\" STYLE=\"display: none;\" onChange=\"invalidateSupplier()\" size=\"4\">\n" );
      out.println( "<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchsupplierlike\" id=\"searchsupplierlike\" value=\"...\" STYLE=\"display: none;\" OnClick=\"getSupplier(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.supplier")+"\"></BUTTON>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"supplierline3\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );
      //Remit To Line 3
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "&nbsp;</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline3\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );
      //Ref Invoice
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.refinvoice")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"refinvoice\" ID=\"refinvoice\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      //Net Amount
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.netamt")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"netAmount\" ID=\"netAmount\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      //Supplier Line 4
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "&nbsp;</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"supplierline4\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );
      //Remit To Line 4
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
      out.println( "&nbsp;</TD>\n" );
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline4\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD>\n" );
      //Status
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.status")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\" NAME=\"invstatus\" ID=\"invstatus\" size=\"1\" DISABLED>\n" );
      out.println( radian.web.HTMLHelpObj.getDropDown( vvvoucherStatus,"" ) );
      out.println( "</SELECT>\n" );
      out.println( "</TD>\n" );
      //Payment Terms
      out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>"+res.getString("label.invterms")+":</B>\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"paymentterms\" ID=\"paymentterms\" value=\"\" size=\"10\" readonly>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );

			out.println( "<TR>\n" );
			out.println( "<TD WIDTH=\"10%\" ALIGN=\"right\" CLASS=\"announce\">\n" );
			out.println( "<B>"+res.getString("label.phone")+":</B>\n" );
			out.println( "</TD>\n" );

	 out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	 out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"mfgphoneno\" ID=\"mfgphoneno\" value=\"\" size=\"25\" readonly>\n" );
	 out.println( "</TD>\n" );

        out.println( "<TD WIDTH=\"3%\" ALIGN=\"right\" CLASS=\"announce\">\n" );
        out.println( "&nbsp;\n" );
        out.println( "</TD>\n" );

        out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" nowrap=\"true\" CLASS=\"announce\">\n" );
        out.println( "<B>"+res.getString("label.nboscpo")+":</B>&nbsp;"+"<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"specialChargePo\" ID=\"specialChargePo\" value=\"\" size=\"5\" readonly>\n" );
        out.println( "</TD>\n" );

			//Approved By
			out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" COLSPAN=\"2\" CLASS=\"announce\">\n" );
			out.println( "<B>"+res.getString("label.approvedby")+":</B>\n" );
			out.println( "</TD>\n" );
			out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" ID=\"approvedby\" CLASS=\"announce\">\n" );
			out.println( "&nbsp;" );
			out.println("</TD>\n");
			//QCed By
			out.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
			out.println( "<B>"+res.getString("label.qcedby")+":</B>\n" );
			out.println( "</TD>\n" );
			out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" ID=\"qcedby\" CLASS=\"announce\">\n" );
			out.println( "&nbsp;" );
			out.println("</TD>\n");
			out.println( "</TR>\n" );

      out.println( "<TR>\n" );
			out.println( "<TD WIDTH=\"10%\" ALIGN=\"right\" CLASS=\"announce\">\n" );
			out.println( "<B>"+res.getString("label.ordertakenby")+":</B>\n" );
			out.println( "</TD>\n" );

			out.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" ID=\"supplierContactInfo\" CLASS=\"announce\">\n" );
			out.println( "&nbsp;" );
			out.println( "</TD>\n" );

      out.println( "<TD WIDTH=\"3%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\"><B>"+res.getString("label.comments")+":</B></TD>\n" );
      out.println( "<TD WIDTH=\"50%\" COLSPAN=\"6\" ID=\"invoicecomments\" ALIGN=\"LEFT\" HEIGHT=\"5\"></TD></TR>\n" );

      out.println( "<TR><TD WIDTH=\"3%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\"><B>"+res.getString("label.buyer")+":</B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" COLSPAN=\"1\" ALIGN=\"LEFT\" ID=\"buyernameandstuff\" HEIGHT=\"5\">\n" );
      out.println( "<TD WIDTH=\"5%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\"><B>"+res.getString("label.invoiceurl")+":</B></TD>\n" );
      out.println( "<TD WIDTH=\"50%\" COLSPAN=\"6\" ALIGN=\"LEFT\" ID=\"invoiceurl\" HEIGHT=\"5\">\n" );
      out.println( "</TD></TR>\n" );

      out.println( "<TR><TD WIDTH=\"3%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\">&nbsp</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" COLSPAN=\"1\" ALIGN=\"LEFT\" HEIGHT=\"5\">&nbsp</TD>\n" );
      out.println( "<TD WIDTH=\"5%\" COLSPAN=\"1\" ALIGN=\"RIGHT\" HEIGHT=\"5\"><B>"+res.getString("label.uploadstatus")+":</B></TD>\n" );
      out.println( "<TD WIDTH=\"50%\" COLSPAN=\"6\" ALIGN=\"LEFT\" ID=\"uploadStatus\" HEIGHT=\"5\">\n" );
      out.println( "</TD></TR>\n" );

  out.println("<TR><td COLSPAN=\"5\" ></td><td COLSPAN=\"3\" ><div id=\"taxDataDiv\" STYLE=\"display: none;\"><TABLE BORDER=\"0\" width=\"300\" CELLPADDING=\"3\" CELLSPACING=\"0\">\n");
  out.println("<tr><TH WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  out.println("<B>Tax Type</B>\n");
  out.println("</TH>\n");
  out.println("<TH WIDTH=\"15%\" ALIGN=\"right\" CLASS=\"announce\">\n");
  out.println("<B>Amount</B>\n");
  out.println("</TH>\n");
  out.println("<TH WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
  out.println("<B>Currency</B>\n");
  out.println("</TH></tr>\n");

  out.println("<tr><TD WIDTH=\"15%\" ALIGN=\"left\" id=\"taxItemId1\" CLASS=\"announce\">\n");
  //out.println(taxItemId1);
  out.println("</TD>\n");

  out.println("<TD WIDTH=\"15%\" ALIGN=\"right\" id=\"taxAmount1\" CLASS=\"announce\">\n");
  ///out.println(taxAmount1);
  out.println("</TD>\n");

  out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" id=\"taxCurrency1\" CLASS=\"announce\">\n" );
  //out.println(taxCurrency1);
  out.println("</tr>\n");

  out.println("<tr><TD WIDTH=\"15%\" ALIGN=\"left\" id=\"taxItemId2\" CLASS=\"announce\">\n");
  //out.println(taxItemId2);
  out.println("</TD>\n");

  out.println("<TD WIDTH=\"15%\" ALIGN=\"right\" id=\"taxAmount2\" CLASS=\"announce\">\n");
  //out.println(taxAmount2);
  out.println("</TD>\n");
  out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" id=\"taxCurrency2\" CLASS=\"announce\">\n" );
  //out.println(taxCurrency2);
  out.println("</tr>\n");
  out.println("</TD></TABLE></div></td></TR>\n");
      out.println( "<TR>\n" );
	  out.println( "<TD WIDTH=\"5%\" ID=\"expeditenotes\" COLSPAN=\"2\" ALIGN=\"LEFT\"></TD>\n" );
      //Add Line
      out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\"  ALIGN=\"CENTER\" COLSPAN=\"7\"  VALIGN=\"MIDDLE\">\n" );
      out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMITW\" VALUE=\"\" onclick= \"return donothing(this)\" NAME=\"nothing\" ID=\"nothing\">\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );
      out.println( "</TABLE>\n" );

      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validpo\" ID=\"validpo\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validbpo\" ID=\"validbpo\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validsupplier\" ID=\"validsupplier\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validshipto\" ID=\"validshipto\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validcarrier\" ID=\"validcarrier\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"validordertaker\" ID=\"validordertaker\" VALUE=\"No\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"totallines\" ID=\"totallines\" VALUE=\"0\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"numofHubs\" ID=\"numofHubs\" VALUE=\"0\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"voucherbillinglocId\" ID=\"voucherbillinglocId\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"VerifiedB\" ID=\"VerifiedB\" VALUE=\"\">\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"editsearchbillingAddress\" ID=\"editsearchbillingAddress\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"selectedrowid\" ID=\"selectedrowid\" VALUE=\"row1\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"haascarrier\" ID=\"haascarrier\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"carriersuppid\" ID=\"carriersuppid\" VALUE=\"\">\n" );

	  out.println( "<BR><BR>\n" );

      out.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"922\" CLASS=\"moveup\">\n" );
      out.println( "</TABLE>\n" );
      out.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column150\">\n" );

      //Write code to insert rows here
      out.println( "<TABLE ID=\"linetable\" CLASS=\"columnar\" BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"900\">\n" );
      out.println( "</TABLE>\n" );
      out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"900\" CLASS=\"moveup\">\n" );
      out.println( "<tr>" );
      out.println( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
      out.println( "</TD></tr>" );
      out.println( "</table>\n" );

      //Invisible Hidden elements in the page
      out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.println( "<tr><td>" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" ID=\"UserAction\" VALUE=\"NEW\">\n" );
      out.println( "</TD></tr>" );
      out.println( "</table>\n" );

      //To show the PO Ship TO Address
      out.println( "<DIV ID=\"poshiptoaddress\">\n" );
      out.println( "<TABLE ID=\"shitpaddtable\" CLASS=\"moveup\" BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"300\">\n" );
      out.println( "<TR><TD><B>"+res.getString("label.shiptoaddress")+":</B></TD></TR>" );
      out.println( "<TR><TD WIDTH=\"15%\" ALIGN=\"left\" ID=\"shiptoaddress\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
      out.println( "</TD></TR>\n" );
      out.println( "</TABLE>\n" );

      out.println( "</P>\n" );
      out.println( "</FORM>\n" );
      out.println( "</BODY>\n" );
      out.println( "</HTML>\n" );
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
    }

    return;
  }

   private Hashtable updatePoDetails( Hashtable in_data,String personnelID )
   {
     Hashtable new_data=new Hashtable();

     try
     {
       Hashtable headerData=new Hashtable();
       Connection connecPo=db.getConnection();
       connecPo.setAutoCommit( false );
       headerData= ( Hashtable ) in_data.get( "HEADERDATA" );
       String po=headerData.get( "PO" ).toString().trim();
       Vector lineData=new Vector();
       lineData= ( Vector ) in_data.get( "LINEDETAILS" );
       boolean line_result=false;

       for ( int i=0; i < lineData.size(); i++ )
       {
         Hashtable hD=new Hashtable();
         hD= ( Hashtable ) lineData.elementAt( i );

         String itemtype=hD.get( "ITEMTYPE" ).toString().trim();
         Vector receiptlines= ( Vector ) hD.get( "RECEIPTLINES" );
         if ( "N".equalsIgnoreCase( itemtype ) )
         {
           line_result=updatereceiptvoc( receiptlines,personnelID,connecPo );
         }
         else
         {
           line_result=updatereceiptaddchargevoc( receiptlines,personnelID,connecPo );
         }

         if ( false == line_result )
         {
           completeSuccess=false;
         }
       }

       connecPo.commit();
       connecPo.setAutoCommit( true );
       CallableStatement pstmt=null;
       try
       {
         pstmt=connecPo.prepareCall( "call P_VOUCHER_APPROVE(?,?,?,?)" );
         pstmt.setBigDecimal( 1,new BigDecimal(""+ po +"") );
         pstmt.registerOutParameter( 2,java.sql.Types.VARCHAR );
				 pstmt.setInt( 3,Integer.parseInt( personnelID ) );
				 pstmt.setString(4, StringHandler.nullIfEmpty(in_data.get("SUPPLIERINVOICEID").toString().trim()));
         pstmt.execute();

         String errorcode=pstmt.getString( 2 );

         globalErrorCode=errorcode;

         if ( ( errorcode == null ) && completeSuccess )
         {
           completeSuccess=true;
         }
         else
         {
           aplog.info( "Result from P_VOUCHER_APPROVE procedure Error Code:  PO- " + po + "   " + errorcode );
           completeSuccess=false;
         }
       }
       catch ( Exception e )
       {
         completeSuccess=false;
         //e.printStackTrace();
         radian.web.emailHelpObj.sendSujataemail( "Error Calling P_VOUCHER_APPROVE in Accounts Payable Page","Error Calling P_VOUCHER_APPROVE\nError Msg:\n" + e.getMessage() + " \nPurchase Order:  " + po + "" );
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
             //se.printStackTrace();
           }
         }
       }

       connecPo.commit();
     }
     catch ( Exception e )
     {
       completeSuccess=false;
       //e.printStackTrace();
     }
     return new_data;
    }

    private boolean updatereceiptvoc( Vector DataAc,String personnelID,Connection con )
    {
      boolean addchargesresult=false;

      try
      {
        for ( int i=0; i < DataAc.size(); i++ )
        {
          Hashtable addchargesData=new Hashtable();
          addchargesData= ( Hashtable ) DataAc.elementAt( i );


          String voucherid=addchargesData.get( "RECVOUCHERID" ).toString().trim();
          String voucherlinenum=addchargesData.get( "RECVOUCHERLINE" ).toString().trim();
          String voucherqty=addchargesData.get( "RECEIPTLINEQTY" ).toString().trim();
          String vocreceipt=addchargesData.get( "VOCRECEIPT" ).toString().trim();
          String updatereceit=addchargesData.get( "UPDATERECEIPT" ).toString().trim();
          String voccreditreceiptqty=addchargesData.get( "RECEIPTCREDITQTY" ).toString().trim();
          String creditreceiptvoucher=addchargesData.get( "INVOCIECREDITCHOOSEN" ).toString().trim();
          String isitCreditline=addchargesData.get( "ISITCREDITLINE" ).toString().trim();
          String mainlnreceiptvoucherId=addchargesData.get( "MAINVOUCHERID" ).toString().trim();
          String mainlnreceiptvoucherline=addchargesData.get( "MAINVOUCHERLINE" ).toString().trim();

          if ( "Yes".equalsIgnoreCase( updatereceit ) )
          {
            String insertflows="";
            if ( voucherid.length() > 0 && voucherlinenum.length() > 0 && vocreceipt.length() > 0 && voucherqty.length() > 0 )
            {
              insertflows = "insert into voucher_receipt (VOUCHER_ID,VOUCHER_LINE,RECEIPT_ID,QUANTITY_VOUCHERED,TRANSACTION_DATE,AP_USER_ID,CREDIT) values \n";
              insertflows += "("+voucherid+","+voucherlinenum+","+vocreceipt+","+voucherqty+"/fx_receipt_qty_multiply_factor("+vocreceipt+"),sysdate,"+personnelID+",'"+isitCreditline+"') \n";

              Statement insertstatement = con.createStatement();
              try
              {
                insertstatement.executeUpdate( insertflows );
              }
              catch ( SQLException ex )
              {
                //radian.web.emailHelpObj.sendSujataemail("Error inserting into voucher_receipt in Accounts Payable Page","Error inserting into voucher_receipt\nError Msg:\n"+ex.getMessage()+" \nQuery: "+insertflows+"");
              }
            }
          }
          else if ( voccreditreceiptqty.length() > 0 && creditreceiptvoucher.length() > 0 )
          {
            String insertflows = "";
            insertflows = "insert into voucher_receipt (VOUCHER_ID,VOUCHER_LINE,RECEIPT_ID,QUANTITY_VOUCHERED,TRANSACTION_DATE,AP_USER_ID,CREDIT,FROZEN) values \n";
            insertflows += "("+voucherid+","+voucherlinenum+","+vocreceipt+","+voccreditreceiptqty+"/fx_receipt_qty_multiply_factor("+vocreceipt+"),sysdate,"+personnelID+",'"+isitCreditline+"','Y') \n";

            Statement insertstatement = con.createStatement();
            try
            {
              insertstatement.executeUpdate( insertflows );
            }
            catch ( SQLException ex1 )
            {
              //radian.web.emailHelpObj.sendSujataemail( "Error inserting into voucher_receipt in Accounts Payable Page","Error inserting into voucher_receipt\nError Msg:\n" + ex1.getMessage() + " \nQuery: " + insertflows + "" );
            }

            String updatevouvherfrozen = "";
            updatevouvherfrozen = "update voucher_receipt set FROZEN='Y' where VOUCHER_ID="+mainlnreceiptvoucherId+" and VOUCHER_LINE = "+mainlnreceiptvoucherline+" and RECEIPT_ID = "+vocreceipt+"";
            try
            {
              insertstatement.executeUpdate( updatevouvherfrozen );
            }
            catch ( SQLException ex2 )
            {
              radian.web.emailHelpObj.sendSujataemail("Error Updating voucher_receipt in Accounts Payable Page","Error Updating voucher_receipt\nError Msg:\n"+ex2.getMessage()+" \nQuery: "+updatevouvherfrozen+"");
            }

            //12-19-02 update price when necessary
            CallableStatement pstmt=null;
            String mainVoucherLine="";
            String mainVoucherID="";
            try
            {
              mainVoucherLine=addchargesData.get( "MAINVOUCHERLINE" ).toString().trim();
              mainVoucherID=addchargesData.get( "MAINVOUCHERID" ).toString().trim();
              pstmt=con.prepareCall( "call p_update_unitprice_credit(?,?,?,?)" );
              pstmt.setInt( 1,Integer.parseInt( voucherid ) );
              pstmt.setInt( 2,Integer.parseInt( voucherlinenum ) );
              pstmt.setInt( 3,Integer.parseInt( mainVoucherID ) );
              pstmt.setInt( 4,Integer.parseInt( mainVoucherLine ) );
              pstmt.execute();
            }
            catch ( Exception e )
            {
              //e.printStackTrace();
              radian.web.emailHelpObj.sendSujataemail("Error Calling p_update_unitprice_credit in Accounts Payable Page","Error Calling p_update_unitprice_credit\nError Msg:\n"+e.getMessage()+" \n  voucherid  "+voucherid+"  voucherlinenum  "+voucherlinenum+"  mainVoucherID  "+mainVoucherID+"   mainVoucherLine  "+mainVoucherLine+"");
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
                  //se.printStackTrace();
                }
              }
            }
            con.commit();
          } //end of inserting into voucher_receipt
        }
        addchargesresult=true;
      }
      catch ( Exception e )
      {
        addchargesresult=false;
        //e.printStackTrace();
      }

      return addchargesresult;
    }

    private boolean updatereceiptaddchargevoc( Vector DataAc,String personnelID,Connection con )
    {
      boolean addchargesresult=false;

      try
      {
        for ( int i=0; i < DataAc.size(); i++ )
        {
          Hashtable addchargesData=new Hashtable();
          addchargesData= ( Hashtable ) DataAc.elementAt( i );

          String voucherid=addchargesData.get( "RECVOUCHERID" ).toString().trim();
          String voucherlinenum=addchargesData.get( "RECVOUCHERLINE" ).toString().trim();
          String voucherqty=addchargesData.get( "RECEIPTLINEQTY" ).toString().trim();
          String vocreceipt=addchargesData.get( "VOCRECEIPT" ).toString().trim();
          String updatereceit=addchargesData.get( "UPDATERECEIPT" ).toString().trim();
          String voccreditreceiptqty=addchargesData.get( "RECEIPTCREDITQTY" ).toString().trim();
          String creditreceiptvoucher=addchargesData.get( "INVOCIECREDITCHOOSEN" ).toString().trim();
          String isitCreditline=addchargesData.get( "ISITCREDITLINE" ).toString().trim();
          String mainlnreceiptvoucherId=addchargesData.get( "MAINVOUCHERID" ).toString().trim();
          String mainlnreceiptvoucherline=addchargesData.get( "MAINVOUCHERLINE" ).toString().trim();

          if ( "Yes".equalsIgnoreCase( updatereceit ) )
          {
            String insertflows="";
            if ( voucherid.length() > 0 && voucherlinenum.length() > 0 && vocreceipt.length() > 0 && voucherqty.length() > 0 )
            {
              insertflows = "insert into voucher_receipt_add_charges (VOUCHER_ID,VOUCHER_LINE,RECEIPT_ID,QUANTITY_VOUCHERED,TRANSACTION_DATE,AP_USER_ID,CREDIT) values \n";
              insertflows += "("+voucherid+","+voucherlinenum+","+vocreceipt+","+voucherqty+",sysdate,"+personnelID+",'"+isitCreditline+"') \n";

              Statement insertstatement = con.createStatement();
              try
              {
                insertstatement.executeUpdate( insertflows );
              }
              catch ( SQLException ex )
              {
                //radian.web.emailHelpObj.sendSujataemail("Error inserting into voucher_receipt_add_charges in Accounts Payable Page","Error inserting into voucher_receipt_add_charges\nError Msg:\n"+ex.getMessage()+" \nQuery: "+insertflows+"");
              }
            }
          }
          else if (voccreditreceiptqty.length() > 0 && creditreceiptvoucher.length() > 0)
          {
            String insertflows = "";
            insertflows = "insert into voucher_receipt_add_charges (VOUCHER_ID,VOUCHER_LINE,RECEIPT_ID,QUANTITY_VOUCHERED,TRANSACTION_DATE,AP_USER_ID,CREDIT,FROZEN) values \n";
            insertflows += "("+voucherid+","+voucherlinenum+","+vocreceipt+","+voccreditreceiptqty+",sysdate,"+personnelID+",'"+isitCreditline+"','Y') \n";

            Statement insertstatement=con.createStatement();
            try
            {
              insertstatement.executeUpdate( insertflows );
            }
            catch ( SQLException ex1 )
            {
              //radian.web.emailHelpObj.sendSujataemail("Error inserting into voucher_receipt_add_charges in Accounts Payable Page","Error inserting into voucher_receipt_add_charges\nError Msg:\n"+ex1.getMessage()+" \nQuery: "+insertflows+"");
            }

            String updatevouvherfrozen = "";
            updatevouvherfrozen = "update voucher_receipt_add_charges set FROZEN='Y' where VOUCHER_ID="+mainlnreceiptvoucherId+" and VOUCHER_LINE = "+mainlnreceiptvoucherline+" and RECEIPT_ID = "+vocreceipt+"";

            try
            {
              insertstatement.executeUpdate( updatevouvherfrozen );
            }
            catch ( SQLException ex2 )
            {
              radian.web.emailHelpObj.sendSujataemail("Error Updating voucher_receipt_add_charges in Accounts Payable Page","Error Updating into voucher_receipt_add_charges\nError Msg:\n"+ex2.getMessage()+" \nQuery: "+updatevouvherfrozen+"");
            }
          }
        }
        addchargesresult=true;
      }
      catch ( Exception e )
      {
        addchargesresult=false;
        //e.printStackTrace();
      }

      return addchargesresult;
    }

  private Hashtable synchServerData( HttpServletRequest request )
  {
    Hashtable finalData=new Hashtable();
    String supplierInvoiceId=  BothHelpObjs.makeBlankFromNull(request.getParameter("supplierInvoiceId"));
    finalData.put("SUPPLIERINVOICEID", supplierInvoiceId);
    Vector lineData=new Vector();

    try
    {
      Hashtable headerData=new Hashtable();

      String po="";
      po=BothHelpObjs.makeBlankFromNull( request.getParameter( "po" ) );
      headerData.put( "PO",po );

      finalData.put( "HEADERDATA",headerData );

      String nooflines="";
      nooflines=BothHelpObjs.makeBlankFromNull( request.getParameter( "totallines" ) );

      int noofLine=Integer.parseInt( nooflines );
      int i=0; //used for detail lines
	  //aplog.info("Number of Main lines : "+noofLine+"");
      for ( int loop=0; loop < ( noofLine ); loop++ )
      {
        i++;
        Hashtable hD=new Hashtable();

        String lineitemid="";
        lineitemid=BothHelpObjs.makeBlankFromNull( request.getParameter( "itemidindetail" + i ) );
        hD.put( "LINEITEMID",lineitemid );

        String itemtype="";
        itemtype=BothHelpObjs.makeBlankFromNull( request.getParameter( "itemtype" + i ) );
        hD.put( "ITEMTYPE",itemtype );

        String noofreceiptmatching="";
        noofreceiptmatching=BothHelpObjs.makeBlankFromNull( request.getParameter( "noofreceiptmatching" + i + "" ) );
        if ( noofreceiptmatching.trim().length() > 0 )
        {
          hD.put( "NOOFMATCHINGRECEIPTS",noofreceiptmatching );


          int noofReceiptmatching=Integer.parseInt( noofreceiptmatching );
          Vector flows=new Vector();
		  //aplog.info("Number of Receipt Matching lines : "+noofreceiptmatching+"");
          for ( int loop1=0; loop1 < noofReceiptmatching; loop1++ )
          {
            Hashtable flowshD=new Hashtable();
            int loop2=loop1 + 1;

            String invoicechoosen="";
            invoicechoosen=BothHelpObjs.makeBlankFromNull( request.getParameter( "invoicechoosen" + i + "" + loop2 + lineitemid + "" ) );
            flowshD.put( "INVOCIECHOOSEN",invoicechoosen );

            String receiptinvoicqtyvoc="";
            receiptinvoicqtyvoc=BothHelpObjs.makeBlankFromNull( request.getParameter( "receiptinvoicqtyvoc" + i + "" + loop2 + lineitemid  + "" ) );
            flowshD.put( "RECEIPTLINEQTY",receiptinvoicqtyvoc );

            String invoicreditcechoosen="";
            invoicreditcechoosen=BothHelpObjs.makeBlankFromNull( request.getParameter( "invoicreditcechoosen" + i + "" + loop2 + lineitemid  + "" ) );
            flowshD.put( "INVOCIECREDITCHOOSEN",invoicreditcechoosen );

            String receiptcreditqty="";
            receiptcreditqty=BothHelpObjs.makeBlankFromNull( request.getParameter( "receiptcreditqty" + i + "" + loop2 + lineitemid  + "" ) );
            flowshD.put( "RECEIPTCREDITQTY",receiptcreditqty );

            String updatereceipt="";
            updatereceipt=BothHelpObjs.makeBlankFromNull( request.getParameter( "updatereceipt" + i + "" + loop2 + lineitemid  + "" ) );
            flowshD.put( "UPDATERECEIPT",updatereceipt );

            String vocreceiptid="";
            vocreceiptid=BothHelpObjs.makeBlankFromNull( request.getParameter("vocreceiptid" + i  + loop2 + lineitemid) );
            flowshD.put( "VOCRECEIPT",vocreceiptid );

            String receiptvoucherId="";
            receiptvoucherId=BothHelpObjs.makeBlankFromNull( request.getParameter( "receiptvoucherId" + i + "" + loop2 + lineitemid  + "" ) );
            flowshD.put( "RECVOUCHERID",receiptvoucherId );

            String receiptvoucherline="";
            receiptvoucherline=BothHelpObjs.makeBlankFromNull( request.getParameter( "receiptvoucherline" + i + "" + loop2 + lineitemid  + "" ) );
            flowshD.put( "RECVOUCHERLINE",receiptvoucherline );

            String mainlnreceiptvoucherId="";
            mainlnreceiptvoucherId=BothHelpObjs.makeBlankFromNull( request.getParameter( "mainlnreceiptvoucherId" + i + "" + loop2 + lineitemid  + "" ) );
            flowshD.put( "MAINVOUCHERID",mainlnreceiptvoucherId );

            String mainlnreceiptvoucherline="";
            mainlnreceiptvoucherline=BothHelpObjs.makeBlankFromNull( request.getParameter( "mainlnreceiptvoucherline" + i + "" + loop2 + lineitemid + "" ) );
            flowshD.put( "MAINVOUCHERLINE",mainlnreceiptvoucherline );

            String isitcreditline="";
            isitcreditline=BothHelpObjs.makeBlankFromNull( request.getParameter( "isitcreditline" + i + "" + loop2 + lineitemid  + "" ) );
            flowshD.put( "ISITCREDITLINE",isitcreditline );

            flows.addElement( flowshD );

          }
          hD.put( "RECEIPTLINES",flows );
        }
        else
        {
          hD.put( "NOOFMATCHINGRECEIPTS","0" );
        }

        lineData.addElement( hD );
      }
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
    }

    finalData.put( "LINEDETAILS",lineData );
    return finalData;
  }
}