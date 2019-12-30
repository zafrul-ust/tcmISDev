package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2002
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-01-02
 *
 *Cahnged the getData code to return nothing if no option in the search criteria is choosen did not want to dump the whole voucher table on the screen
 *Aslo made a default 100 days invoice age
 * 12-22-03 - Code Refactoring, moved vv_stuff to different class
 * 01-30-04 - Showing only Active Payment Terms for PO pages and showing all for AP pages
 */


public class invoiceReport
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private PrintWriter out = null;
    private String thedecidingRandomNumber = null;
    private String privatepersonnelId = "";
    private boolean allowupdate;
    private boolean intcmIsApplication = false;

    public void setupdateStatus(boolean id)
    {
      this.allowupdate = id;
    }

    private boolean getupdateStatus()  throws Exception
    {
     return this.allowupdate;
    }

    public invoiceReport(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    //Process the HTTP Post request passed from whoever called it
    public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession session )
       throws ServletException,IOException
    {
      out=response.getWriter();
      response.setContentType( "text/html" );

      PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
      Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
      intcmIsApplication = false;
      if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
      {
       intcmIsApplication = true;
      }

      String personnelid=session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
      privatepersonnelId=personnelid;

      String User_Action=null;
      User_Action=request.getParameter( "UserAction" );
      if ( User_Action == null )
        User_Action="New";
      User_Action=User_Action.trim();

      String searchinvoice=request.getParameter( "searchinvoice" );
      if ( searchinvoice == null )
        searchinvoice="";
      searchinvoice=searchinvoice.trim();

      String searchsupplier=request.getParameter( "searchsupplier" );
      if ( searchsupplier == null )
        searchsupplier="";
      searchsupplier=searchsupplier.trim();

      String showonlynopo=request.getParameter( "showonlynopo" );
      if ( showonlynopo == null )
        showonlynopo="";
      showonlynopo=showonlynopo.trim();

      String showonlytobeqced=request.getParameter( "showonlytobeqced" );
      if ( showonlytobeqced == null )
       showonlytobeqced="";
      showonlytobeqced=showonlytobeqced.trim();

      String invoiceage=request.getParameter( "invoiceage" );
      if ( invoiceage == null )
        invoiceage="";
      invoiceage=invoiceage.trim();

      String User_Sort=request.getParameter( "sortBy" );
      if ( User_Sort == null )
        User_Sort="";
      User_Sort=User_Sort.trim();

      String status=request.getParameter( "status" );
      if ( status == null )
        status="In Progress";
      status=status.trim();

			String approvedFrom=request.getParameter( "approvedFrom" );
			if ( approvedFrom == null )
				approvedFrom="";
			approvedFrom=approvedFrom.trim();

			String approvedTo=request.getParameter( "approvedTo" );
			if ( approvedTo == null )
				approvedTo="";
			approvedTo=approvedTo.trim();

      String lorclerkname=request.getParameter( "lorclerkname" );
      if ( lorclerkname == null )
        lorclerkname="";
      lorclerkname=lorclerkname.trim();

      if ( !lorclerkname.equalsIgnoreCase( "All" ) && lorclerkname.trim().length() < 1 )
      {
        lorclerkname=personnelid;
      }

      thedecidingRandomNumber=request.getParameter( "thedecidingRandomNumber" );
      if ( thedecidingRandomNumber == null )
        thedecidingRandomNumber="";
      thedecidingRandomNumber=thedecidingRandomNumber.trim();

      try
      {
        String donevvstuff=session.getAttribute( "VVAPSTUFF" ) == null ? "" : session.getAttribute( "VVAPSTUFF" ).toString();
        if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
        {
          session.setAttribute( "VV_PAYMENT",radian.web.vvHelpObj.getPaymentTerms(db,true) );
          session.setAttribute( "VV_VOUCHERSTATUS",radian.web.vvHelpObj.getvvvouvherstatus(db) );
          session.setAttribute( "LORCLERKNAMES",radian.web.HTMLHelpObj.getLorClerknames( db ) );
          session.setAttribute( "VVAPSTUFF","Yes" );
        }

        Random rand=new Random();
        int tmpReq=rand.nextInt();
        Integer tmpReqNum=new Integer( tmpReq );

        {
          if ( thedecidingRandomNumber.length() > 0 )
          {
            String randomnumberfromsesion=session.getAttribute( "RANDOMNUMBERCOOKIE" ) == null ? "" : session.getAttribute( "RANDOMNUMBERCOOKIE" ).toString();

            if ( randomnumberfromsesion.equalsIgnoreCase( thedecidingRandomNumber ) )
            {
              thedecidingRandomNumber=tmpReqNum.toString();
              session.setAttribute( "RANDOMNUMBERCOOKIE",thedecidingRandomNumber );
            }
            else
            {
              thedecidingRandomNumber=tmpReqNum.toString();
              session.setAttribute( "RANDOMNUMBERCOOKIE",thedecidingRandomNumber );
            }
          }
          else
          {
            thedecidingRandomNumber=tmpReqNum.toString();
            session.setAttribute( "RANDOMNUMBERCOOKIE",thedecidingRandomNumber );
          }
        }

        if ( User_Action.equalsIgnoreCase( "New" ) )
        {
          Vector compnayids=new Vector();
          Vector vvPayment=new Vector();
          Vector vvvouvherstatus=new Vector();

          donevvstuff="";
          donevvstuff=session.getAttribute( "VVAPSTUFF" ) == null ? "" : session.getAttribute( "VVAPSTUFF" ).toString();

          if ( donevvstuff.equalsIgnoreCase( "Yes" ) )
          {
            vvPayment= ( Vector ) session.getAttribute( "VV_PAYMENT" );
            vvvouvherstatus= ( Vector ) session.getAttribute( "VV_VOUCHERSTATUS" );
          }
          else
          {
            vvPayment=radian.web.vvHelpObj.getPaymentTerms(db,true);
            vvvouvherstatus=radian.web.vvHelpObj.getvvvouvherstatus(db);
            session.setAttribute( "VV_PAYMENT",vvPayment );
            session.setAttribute( "VV_VOUCHERSTATUS",vvvouvherstatus );
            session.setAttribute( "LORCLERKNAMES",radian.web.HTMLHelpObj.getLorClerknames( db ) );
            session.setAttribute( "VVAPSTUFF","Yes" );
          }

          if ( invoiceage.trim().length() < 1 )
          {
            invoiceage="100";
          }
          buildHeader( session,User_Action,searchinvoice,User_Sort,status,searchsupplier,invoiceage,showonlynopo,lorclerkname,showonlytobeqced,approvedFrom,approvedTo);
          radian.web.HTMLHelpObj.printHTMLSelect();

          compnayids=null;
          vvvouvherstatus=null;
          vvPayment=null;
        }
        else if ( User_Action.equalsIgnoreCase( "Search" ) )
        {
          Vector searchdata=new Vector();

          searchdata=this.getSearchData( searchinvoice,User_Sort,status,searchsupplier,invoiceage,lorclerkname,showonlynopo,showonlytobeqced,approvedFrom,approvedTo );

          Hashtable sum= ( Hashtable ) ( searchdata.elementAt( 0 ) );

          int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
          if ( 0 == count )
          {
            buildHeader( session,User_Action,searchinvoice,User_Sort,status,searchsupplier,invoiceage,showonlynopo,lorclerkname,showonlytobeqced,approvedFrom,approvedTo);
            out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );

            //clean up
            searchdata=null;
          }
          else
          {
            buildHeader( session,User_Action,searchinvoice,User_Sort,status,searchsupplier,invoiceage,showonlynopo,lorclerkname,showonlytobeqced,approvedFrom,approvedTo);
            buildDetails( searchdata,session,showonlynopo);

            //clean up
            searchdata=null;
          } //when there are open orders for hub
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "New Buy Order",intcmIsApplication ) );
      }
      finally
      {
        out.close();
      }

      return;
    }

    private Vector getSearchData( String searchinvoice,String sortby,String status,String searchsupplier,String invoiceAge,String lorclerkname,
		 String showOnlyWithAvailableReceipts,String showonlytobeqced,String approvedFrom,String approvedTo)
       throws Exception
    {
      Vector Data=new Vector();
      Hashtable VoucherH=new Hashtable();
      Hashtable summary=new Hashtable();
      boolean flagforwhere=false;
      boolean flagforassociations=false;
      flagforassociations=true;
      DBResultSet dbrs=null;
			searchsupplier = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(searchsupplier);

      int count=0;
      summary.put( "TOTAL_NUMBER",new Integer( count ) );
      Data.addElement( summary );
      int num_rec=0;

      try
      {
        String Query_Statement="";
        Query_Statement="Select a.*, to_char(a.INVOICE_DATE ,'mm/dd/yyyy') INVOICE_DATE1,to_char(a.QC_DATE ,'mm/dd/yyyy') QC_DATE1,to_char(a.APPROVED_DATE ,'mm/dd/yyyy') APPROVED_DATE1 ";

        Query_Statement+=" from voucher_report_view a where ";

        if ( searchinvoice.trim().length() > 0 )
        {
          if ( flagforwhere )
            Query_Statement+="and a.SUPPLIER_INVOICE_ID = '" + searchinvoice + "' ";
          else
          {
            Query_Statement+=" a.SUPPLIER_INVOICE_ID = '" + searchinvoice + "' ";
            flagforwhere=true;
          }
        }

        if ( status.length() > 0 && !"All".equalsIgnoreCase( status ) )
        {
          if ( flagforwhere )
            Query_Statement+="and a.STATUS = '" + status + "' ";
          else
          {
            Query_Statement+=" a.STATUS = '" + status + "' ";
            flagforwhere=true;
          }
        }

        if ( searchsupplier.trim().length() > 0 )
        {
          if ( flagforwhere )
            Query_Statement+="and lower(a.SUPPLIER_NAME) like lower('%" + searchsupplier + "%') ";
          else
          {
            Query_Statement+=" lower(a.SUPPLIER_NAME) like lower('%" + searchsupplier + "%') ";
            flagforwhere=true;
          }
        }

        if ( invoiceAge.length() > 0 )
        {
          if ( flagforwhere )
            Query_Statement+="and a.INVOICE_DATE >= sysdate - " + invoiceAge + " ";
          else
          {
            Query_Statement+=" a.INVOICE_DATE >= sysdate - " + invoiceAge + " ";
            flagforwhere=true;
          }
        }

        if ( lorclerkname.length() > 0 && !"All".equalsIgnoreCase( lorclerkname ) )
        {
          if ( flagforwhere )
            Query_Statement+="and a.AP_USER_ID = " + lorclerkname + " ";
          else
          {
            Query_Statement+=" a.AP_USER_ID = " + lorclerkname + " ";
            flagforwhere=true;
          }
        }

	if ( showOnlyWithAvailableReceipts.length() > 0 && "Yes".equalsIgnoreCase( showOnlyWithAvailableReceipts ) )
	{
	 if ( flagforwhere )
		Query_Statement+="and a.AVAILABLE_RECEIPTS = 'Y' ";
	 else
	 {
		Query_Statement+=" a.AVAILABLE_RECEIPTS = 'Y' ";
		flagforwhere=true;
	 }
	}

	if ( showonlytobeqced.length() > 0 && "Yes".equalsIgnoreCase( showonlytobeqced ) )
	{
	 if ( flagforwhere )
		Query_Statement+="and a.QC_DATE is null ";
	 else
	 {
		Query_Statement+=" a.QC_DATE is null ";
		flagforwhere=true;
	 }

	 if (approvedFrom.length() == 10 && approvedTo.length() == 10)
	 {
		Query_Statement+="and a.APPROVED_DATE > TO_DATE('"+approvedFrom+"', 'MM/DD/YYYY') and a.APPROVED_DATE <= TO_DATE('"+approvedTo+" 23:59', 'MM/DD/RRRR hh24:mi:ss') ";
	 }
	 else if (approvedFrom.length() == 10)
	 {
		Query_Statement+="and a.APPROVED_DATE > TO_DATE('"+approvedFrom+"', 'MM/DD/YYYY') ";
	 }
	 else if (approvedTo.length() == 10)
	 {
		Query_Statement+="and a.APPROVED_DATE <= TO_DATE('"+approvedTo+" 23:59', 'MM/DD/RRRR hh24:mi:ss') ";
	 }
	 }

	Query_Statement+=" order by a." + sortby + " ";

        if ( flagforwhere )
        {
          dbrs=db.doQuery( Query_Statement );
          ResultSet searchRs=dbrs.getResultSet();

          while ( searchRs.next() )
          {
            VoucherH=new Hashtable();
            num_rec++;
            //RADIAN_PO
            VoucherH.put( "RADIAN_PO",searchRs.getString( "RADIAN_PO" ) == null ? "" : searchRs.getString( "RADIAN_PO" ) );
            //BUYER_NAME
            VoucherH.put( "BUYER_NAME",searchRs.getString( "BUYER_NAME" ) == null ? "" : searchRs.getString( "BUYER_NAME" ) );
            //BUYER_EMAIL
            VoucherH.put( "BUYER_EMAIL",searchRs.getString( "BUYER_EMAIL" ) == null ? "" : searchRs.getString( "BUYER_EMAIL" ) );
            //BUYER_PHONE
            VoucherH.put( "BUYER_PHONE",searchRs.getString( "BUYER_PHONE" ) == null ? "" : searchRs.getString( "BUYER_PHONE" ) );
            //SUPPLIER_NAME
            VoucherH.put( "SUPPLIER_NAME",searchRs.getString( "SUPPLIER_NAME" ) == null ? "" : searchRs.getString( "SUPPLIER_NAME" ) );
            //SUPPLIER_INVOICE_ID
            VoucherH.put( "SUPPLIER_INVOICE_ID",searchRs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : searchRs.getString( "SUPPLIER_INVOICE_ID" ) );
            //INVOICE_DATE
            VoucherH.put( "INVOICE_DATE",searchRs.getString( "INVOICE_DATE1" ) == null ? "" : searchRs.getString( "INVOICE_DATE1" ) );
            //PO_TERMS
            VoucherH.put( "PO_TERMS",searchRs.getString( "PO_TERMS" ) == null ? "" : searchRs.getString( "PO_TERMS" ) );
            //SUPPLIER_TERMS
            VoucherH.put( "SUPPLIER_TERMS",searchRs.getString( "SUPPLIER_TERMS" ) == null ? "" : searchRs.getString( "SUPPLIER_TERMS" ) );
            //NET_INVOICE_AMOUNT
            VoucherH.put( "NET_INVOICE_AMOUNT",searchRs.getString( "NET_INVOICE_AMOUNT" ) == null ? "" : searchRs.getString( "NET_INVOICE_AMOUNT" ) );
            //AVAILABLE_RECEIPTS
            VoucherH.put( "AVAILABLE_RECEIPTS",searchRs.getString( "AVAILABLE_RECEIPTS" ) == null ? "" : searchRs.getString( "AVAILABLE_RECEIPTS" ) );
            //STATUS
            VoucherH.put( "STATUS",searchRs.getString( "STATUS" ) == null ? "" : searchRs.getString( "STATUS" ) );
            //AP_NOTE
            VoucherH.put( "AP_NOTE",searchRs.getString( "AP_NOTE" ) == null ? "" : searchRs.getString( "AP_NOTE" ) );
            // AP_USER_ID
						VoucherH.put( "AP_USER_ID",searchRs.getString( "AP_USER_ID" ) == null ? "" : searchRs.getString( "AP_USER_ID" ) );
						// APPROVED_DATE
            VoucherH.put( "APPROVED_DATE",searchRs.getString( "APPROVED_DATE1" ) == null ? "" : searchRs.getString( "APPROVED_DATE1" ) );
						// QC_DATE
            VoucherH.put( "QC_DATE",searchRs.getString( "QC_DATE1" ) == null ? "" : searchRs.getString( "QC_DATE1" ) );
						// QC_USER
            VoucherH.put( "QC_USER",searchRs.getString( "QC_USER" ) == null ? "" : searchRs.getString( "QC_USER" ) );
						// AP_APPROVER_NAME
            VoucherH.put( "AP_APPROVER_NAME",searchRs.getString( "AP_APPROVER_NAME" ) == null ? "" : searchRs.getString( "AP_APPROVER_NAME" ) );
						// AP_QC_USER_NAME
            VoucherH.put( "AP_QC_USER_NAME",searchRs.getString( "AP_QC_USER_NAME" ) == null ? "" : searchRs.getString( "AP_QC_USER_NAME" ) );
						// VOUCHER_ID
            VoucherH.put( "VOUCHER_ID",searchRs.getString( "VOUCHER_ID" ) == null ? "" : searchRs.getString( "VOUCHER_ID" ) );

            Data.addElement( VoucherH );
          }
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        throw e;
      }
      finally
      {
        //statement.close();
        if ( dbrs != null )
        {
          dbrs.close();
        }
      }

      Hashtable recsum=new Hashtable();
      recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
      Data.setElementAt( recsum,0 );

      return Data;
    }

    private void buildHeader( HttpSession session,String useraction,String searchinvoice,String sortby,String status,String searchsupplier,
                                String invoiceAge,String showonlynopo,String lorclerkname,String showonlytobeqced,String approvedFrom,String approvedTo )
    {
      //StringBuffer Msg=new StringBuffer();

      if ( !"Search".equalsIgnoreCase( useraction ) )
      {
        showonlynopo="Yes";
        status="In Progress";
      }

      try
      {
        out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Invoice Report","purchasereq.js",intcmIsApplication ) );
        out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );
				out.println( "<SCRIPT SRC=\"/js/accountspayable/invoicereport.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
				out.println( "<SCRIPT SRC=\"/js/calendar/newcalendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
				out.println( "<SCRIPT SRC=\"/js/calendar/AnchorPosition.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
				out.println( "<SCRIPT SRC=\"/js/calendar/PopupWindow.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
        out.println( "<BODY>\n" );
        out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
        out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
        out.println( "</DIV>\n" );
        out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
        out.println( radian.web.HTMLHelpObj.printApTitleBar( "<B>Invoice Report</B>\n" ) );
        out.println( "<FORM method=\"POST\" NAME=\"purchasereq\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString( "INVOICE_REPORTS" ) + "\">\n" );

        //start table #1
        out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        out.println( "<TR>\n" );

        //Invoice
        out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Invoice:</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"searchinvoice\" value=\"" + searchinvoice + "\" SIZE=\"25\"></TD>\n" );

        //Status
        out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Status:</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
        String allbuyerselected="";
        if ( "All".equalsIgnoreCase( status ) )
        {
          allbuyerselected="selected";
        }

	      if ("Yes".equalsIgnoreCase( showonlytobeqced ))
				{
						out.println("<SELECT CLASS=\"HEADER\" NAME=\"status\" size=\"1\" disabled>\n");
				}
				else
				{
						out.println("<SELECT CLASS=\"HEADER\" NAME=\"status\" size=\"1\">\n");
	      }
        out.println( "<OPTION " + allbuyerselected + " VALUE=\"All\">All</OPTION>\n" );
        out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "VV_VOUCHERSTATUS" ),status ) );
        out.println( "</SELECT>\n" );
        out.println( "</TD>\n" );

        //Search
        out.println( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\">\n" );
        out.println( "   <INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "</TR>\n" );
        out.println( "<TR>\n" );

        //Supplier
        out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Supplier:</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"searchsupplier\" value=\"" + searchsupplier + "\" SIZE=\"25\"></TD>\n" );

        //Invoice Age
        out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Invoice Age:</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
        out.println( "<INPUT CLASS=\"HEADER\" type=\"text\" name=\"invoiceage\" value=\"" + invoiceAge + "\" SIZE=\"5\">&nbsp;&nbsp;&nbsp;Days</TD>\n" );
        out.println( "</TD>\n" );

        //Show Only With Available Receipts
        out.println( "<TD WIDTH=\"20%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
        out.println( "<INPUT type=\"checkbox\" name=\"showonlynopo\" value=\"Yes\" " + ( showonlynopo.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
        out.println( "&nbsp;Show Only With Available Receipts\n" );
        out.println( "</TD>\n" );
        out.println( "</TR>\n" );
        out.println( "<TR>\n" );

        //LOR Clerk Names
        out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Name:</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
        String alllorselected="";
        if ( "All".equalsIgnoreCase( lorclerkname ) )
        {
          alllorselected="selected";
        }
        out.println( "<SELECT CLASS=\"HEADER\" NAME=\"lorclerkname\" size=\"1\">\n" );
        out.println( "<OPTION " + alllorselected + " VALUE=\"All\">All</OPTION>\n" );
        out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "LORCLERKNAMES" ),lorclerkname ) );
        out.println( "</SELECT>\n" );
        out.println( "</TD>\n" );

        //Sort
        out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Sort By:</B>&nbsp;&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
        out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"sortBy\" size=\"1\">\n" );
        if ( sortby.equalsIgnoreCase( "RADIAN_PO,SUPPLIER_INVOICE_ID" ) )
        {
          out.println( "<option selected value=\"RADIAN_PO,SUPPLIER_INVOICE_ID\">PO/Invoice</option>\n" );
          out.println( "<option value=\"SUPPLIER_INVOICE_ID\">Invoice</option>\n" );
          out.println( "<option value=\"SUPPLIER_NAME,SUPPLIER_INVOICE_ID\">Supplier/Invoice</option>\n" );
          out.println( "<option value=\"INVOICE_DATE\">Invoice Date</option>\n" );
	  out.println( "<option value=\"APPROVED_DATE\">Approved Date</option>\n" );
        }
        else if ( sortby.equalsIgnoreCase( "SUPPLIER_INVOICE_ID" ) )
        {
          out.println( "<option value=\"RADIAN_PO,SUPPLIER_INVOICE_ID\">PO/Invoice</option>\n" );
          out.println( "<option selected value=\"SUPPLIER_INVOICE_ID\">Invoice</option>\n" );
          out.println( "<option value=\"SUPPLIER_NAME,SUPPLIER_INVOICE_ID\">Supplier/Invoice</option>\n" );
          out.println( "<option value=\"INVOICE_DATE\">Invoice Date</option>\n" );
	  out.println( "<option value=\"APPROVED_DATE\">Approved Date</option>\n" );
        }
        else if ( sortby.equalsIgnoreCase( "SUPPLIER_NAME,SUPPLIER_INVOICE_ID" ) )
        {
          out.println( "<option value=\"RADIAN_PO,SUPPLIER_INVOICE_ID\">PO/Invoice</option>\n" );
          out.println( "<option value=\"SUPPLIER_INVOICE_ID\">Invoice</option>\n" );
          out.println( "<option selected value=\"SUPPLIER_NAME,SUPPLIER_INVOICE_ID\">Supplier/Invoice</option>\n" );
          out.println( "<option value=\"INVOICE_DATE\">Invoice Date</option>\n" );
	  out.println( "<option value=\"APPROVED_DATE\">Approved Date</option>\n" );
        }
        else if ( sortby.equalsIgnoreCase( "INVOICE_DATE" ) )
        {
          out.println( "<option value=\"RADIAN_PO,SUPPLIER_INVOICE_ID\">PO/Invoice</option>\n" );
          out.println( "<option value=\"SUPPLIER_INVOICE_ID\">Invoice</option>\n" );
          out.println( "<option value=\"SUPPLIER_NAME,SUPPLIER_INVOICE_ID\">Supplier/Invoice</option>\n" );
          out.println( "<option selected value=\"INVOICE_DATE\">Invoice Date</option>\n" );
	  out.println( "<option value=\"APPROVED_DATE\">Approved Date</option>\n" );
        }
        else if ( sortby.equalsIgnoreCase( "APPROVED_DATE" ) )
        {
          out.println( "<option value=\"RADIAN_PO,SUPPLIER_INVOICE_ID\">PO/Invoice</option>\n" );
          out.println( "<option value=\"SUPPLIER_INVOICE_ID\">Invoice</option>\n" );
          out.println( "<option value=\"SUPPLIER_NAME,SUPPLIER_INVOICE_ID\">Supplier/Invoice</option>\n" );
          out.println( "<option value=\"INVOICE_DATE\">Invoice Date</option>\n" );
	  out.println( "<option selected value=\"APPROVED_DATE\">Approved Date</option>\n" );
        }
        else
        {
         out.println( "<option selected value=\"RADIAN_PO,SUPPLIER_INVOICE_ID\">PO/Invoice</option>\n" );
         out.println( "<option value=\"SUPPLIER_INVOICE_ID\">Invoice</option>\n" );
         out.println( "<option value=\"SUPPLIER_NAME,SUPPLIER_INVOICE_ID\">Supplier/Invoice</option>\n" );
         out.println( "<option value=\"INVOICE_DATE\">Invoice Date</option>\n" );
         out.println( "<option value=\"APPROVED_DATE\">Approved Date</option>\n" );
        }

        out.println( "</SELECT>\n" );
        out.println( "</TD>\n" );
	//Show Only That Need to be QCed
	out.println( "<TD WIDTH=\"20%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
	out.println( "<INPUT type=\"checkbox\" name=\"showonlytobeqced\" value=\"Yes\" " + ( showonlytobeqced.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + " onclick= \"freezeInvoiceStatus()\">\n" );
	out.println( "&nbsp;Show Only Invoices to be QCed\n" );
	out.println( "</TD>\n" );

        out.println( "</TR>\n" );
      	out.println( "<TR>\n" );
	//
	out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "&nbsp;\n" );
	out.println( "</TD>\n" );
	out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">&nbsp;</TD>\n" );

	//
	out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "&nbsp;\n" );
	out.println( "</TD>\n" );
	out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
	out.println( "&nbsp;</TD>\n" );
	out.println( "</TD>\n" );

	//Approved Dates
	out.println( "<TD WIDTH=\"20%\" ALIGN=\"LEFT\" CLASS=\"announce\">" );
	if ("Yes".equalsIgnoreCase( showonlytobeqced ))
	{
			out.println("<div id=\"approvedDates\">");
	}
	else
	{
			out.println("<div id=\"approvedDates\" style=\"display: none;\">");
	}
	out.println("Approved From:&nbsp;\n<input type=\"text\" CLASS=\"DETAILS\" name=\"approvedFrom\" id=\"approvedFrom\" value=\""+approvedFrom+"\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"linkapprovedFrom\" onclick=\"return getCalendar(document.purchasereq.approvedFrom);\">&diams;</a></FONT>\n");
	out.println("&nbsp;To:&nbsp;\n<input type=\"text\" CLASS=\"DETAILS\" name=\"approvedTo\" id=\"approvedTo\" value=\""+approvedTo+"\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"linkapprovedTo\" onclick=\"return getCalendar(document.purchasereq.approvedTo);\">&diams;</a></FONT>\n");
	out.println( "</div></TD>\n" );
	out.println( "</TR>\n" );

        out.println( "</TABLE>\n" );
        out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n" );
        out.println( "<tr><td>" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\"\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );
        out.println( "</TD></tr>" );
        out.println( "</table>\n" );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "Invoice Report",intcmIsApplication ) );
      }

      return;
    }

    private void buildDetails( Vector data,HttpSession session,String showonlyYes )
    {
      StringBuffer Msg=new StringBuffer();

      out.println( "<INPUT TYPE=\"hidden\" NAME=\"radianPO\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"lineID\" VALUE=\" \">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemnum\" VALUE=\" \">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"numofHubs\" VALUE=\" \">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"ammendment\" VALUE=\" \">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemfromLine\" VALUE=\" \">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"shipTofromLine\" VALUE=\" \">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"hubfromLine\" VALUE=\" \">\n" );

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

        out.println( "<INPUT TYPE=\"hidden\" NAME=\"totalRecords\" ID=\"totalRecords\" VALUE=\"" + total + "\">\n" );
        out.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        /*out.println( "<tr align=\"center\">\n" );
        out.println( "<TH WIDTH=\"3%\"  HEIGHT=\"25\">PO</TH>\n" );
        out.println( "<TH width=\"5%\"  height=\"38\">Buyer</TH>\n" );
        out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Email</TH>\n" );
        out.println( "<TH WIDTH=\"8%\"  HEIGHT=\"25\">Phone</TH>\n" );
        out.println( "<TH width=\"10%\"  height=\"38\">Supplier</TH>\n" );
        out.println( "<TH width=\"5%\"  height=\"38\">Invoice</TH>\n" );
        out.println( "<TH width=\"5%\"  height=\"38\">Invoice Date</TH>\n" );
        out.println( "<TH width=\"5%\"  height=\"38\">Our Terms</TH>\n" );
        out.println( "<TH width=\"5%\"  height=\"38\">Supplier Terms</TH>\n" );
        out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Amount</TH>\n" );
        out.println( "<TH width=\"5%\"  height=\"38\">Available Receipts</TH>\n" );
        out.println( "<TH width=\"5%\"  height=\"38\">AP User</TH>\n" );
        out.println( "<TH width=\"15%\"  height=\"38\">Comment</TH>\n" );
        out.println( "</tr>\n" );*/

        int i=0; //used for detail lines
        int lineadded=0;
        for ( int loop=0; loop < total; loop++ )
        {
          i++;
          boolean createHdr=false;
					if ( loop % 10 == 0 )
					{
						createHdr=true;
					}

					if ( createHdr )
					 {
						 out.println( "<tr align=\"center\">\n" );
						 out.println( "<TH WIDTH=\"3%\"  HEIGHT=\"25\">PO</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">Buyer</TH>\n" );
						 out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Email</TH>\n" );
						 out.println( "<TH WIDTH=\"8%\"  HEIGHT=\"25\">Phone</TH>\n" );
						 out.println( "<TH width=\"10%\"  height=\"38\">Supplier</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">Invoice</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">Invoice Date</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">Our Terms</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">Supplier Terms</TH>\n" );
						 out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Amount</TH>\n" );
						 out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Status</TH>\n" );
						 out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Approved By<br>(Date)</TH>\n" );
						 out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Qced By<br>(Date)</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">Available Receipts</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">AP User</TH>\n" );
						 out.println( "<TH width=\"15%\"  height=\"38\">Comment</TH>\n" );
						 out.println( "</tr>\n" );
						 createHdr=false;
					 }


          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          // get main information
          String radianpo=hD.get( "RADIAN_PO" ).toString();
          String buyer=hD.get( "BUYER_NAME" ).toString();
          String buyeremail=hD.get( "BUYER_EMAIL" ).toString();
          String buyerphone=hD.get( "BUYER_PHONE" ).toString().trim();
          String suppliername=hD.get( "SUPPLIER_NAME" ).toString();
          String supplierinvoid=hD.get( "SUPPLIER_INVOICE_ID" ).toString();
          String invociedate=hD.get( "INVOICE_DATE" ).toString();
          String poterms=hD.get( "PO_TERMS" ).toString().trim();
          String suppliertemrs=hD.get( "SUPPLIER_TERMS" ).toString();
          String netinvamount=hD.get( "NET_INVOICE_AMOUNT" ).toString();
          String availablereceipts=hD.get( "AVAILABLE_RECEIPTS" ).toString();
					if ("N".equalsIgnoreCase( availablereceipts ))
					{
					 availablereceipts = "No";
      		}
					else if ("Y".equalsIgnoreCase( availablereceipts ))
					{
					 availablereceipts = "Yes";
					}

          String status=hD.get( "STATUS" ).toString();
          String comments=hD.get( "AP_NOTE" ).toString();
          String apUserId=hD.get( "AP_USER_ID" ).toString();
					String approvedDate=hD.get( "APPROVED_DATE" ).toString();
					String qcDate=hD.get( "QC_DATE" ).toString();
					String qcUserName=hD.get( "AP_QC_USER_NAME" ).toString();
					String approverName=hD.get( "AP_APPROVER_NAME" ).toString();
					String voucherId=hD.get( "VOUCHER_ID" ).toString();

          String Color="";
          String itemColor="";
          if ( loop % 2 == 0 )
          {
            Color="CLASS=\"Inwhite";
            itemColor="INVISIBLEHEADWHITE";
          }
          else
          {
            Color="CLASS=\"Inblue";
            itemColor="INVISIBLEHEADBLUE";
          }

          //if (!( "Yes".equalsIgnoreCase( showonlyYes ) && "N".equalsIgnoreCase( availablereceipts ) ))
          //{
            out.println( "<tr align=\"center\">\n" );

						String radianpourl="/tcmIS/Invoice/AccountsPayable?po=" + radianpo + "&Action=searchlike&subUserAction=po";
						String radianposelectedinvoiceurl="/tcmIS/Invoice/AccountsPayable?po=" + radianpo + "&selectedInvoiceId="+voucherId+"&Action=searchlike&subUserAction=po";

            if ( radianpo.trim().length() > 0 )
            {
              //www
              out.println( "<td " + Color + "\" width=\"3%\"><A onclick=\"javascript:window.open('" + radianpourl + "')\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\">" + radianpo + "</A></td>\n" );
            }
            else
            {
              out.println( "<td " + Color + "\" width=\"3%\">&nbsp;</td>\n" );
            }

            //out.println("<td "+Color+"\" width=\"3%\">"+radianpo+"</td>\n");
            out.println( "<td " + Color + "\" width=\"5%\">" + buyer + "</td>\n" );
            out.println( "<td " + Color + "l\" width=\"5%\">" + buyeremail + "</td>\n" );
            out.println( "<td " + Color + "l\" width=\"8%\">" + buyerphone + "</td>\n" );
            out.println( "<td " + Color + "l\" width=\"10%\">" + suppliername + "</td>\n" );
            out.println( "<td " + Color + "\" width=\"3%\"><A onclick=\"javascript:window.open('" + radianposelectedinvoiceurl + "')\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\">" + supplierinvoid + "</A></td>\n" );
            out.println( "<td " + Color + "\" width=\"5%\">" + invociedate + "</td>\n" );
            out.println( "<td " + Color + "\" width=\"5%\">" + poterms + "</td>\n" );
            out.println( "<td " + Color + "\" width=\"5%\">" + suppliertemrs + "</td>\n" );
            out.println( "<td " + Color + "r\" width=\"5%\">" + netinvamount + "</td>\n" );
						out.println( "<td " + Color + "\" width=\"5%\">" + status + "</td>\n" );
						if (approvedDate != null && approvedDate.length() > 0)
						{
						 out.println("<td " + Color + "l\" width=\"5%\">" + approverName + "<br>("+approvedDate+")</td>\n");
						}
						else
						{
						 out.println("<td " + Color + "l\" width=\"5%\">&nbsp;</td>\n");
 		        }
						if (qcDate != null && qcDate.length() > 0)
						{
						 out.println("<td " + Color + "l\" width=\"5%\">" + qcUserName + "<br>("+qcDate+")</td>\n");
						}
						else
						{
						 out.println("<td " + Color + "l\" width=\"5%\">&nbsp;</td>\n");
  	 	      }
            out.println( "<td " + Color + "\" width=\"5%\">" + availablereceipts + "</td>\n" );
            out.println( "<td " + Color + "l\" width=\"5%\">" );
            Vector apUserName= ( Vector ) session.getAttribute( "LORCLERKNAMES" );
            for ( int z=0; z < apUserName.size(); z++ )
            {
              Hashtable data1= ( Hashtable ) apUserName.elementAt( z );
              Enumeration E;
              for ( E=data1.keys(); E.hasMoreElements(); )
              {
                String key= ( String ) E.nextElement();
                if ( apUserId.equalsIgnoreCase( data1.get( key ).toString() ) )
                {
                  out.println( key );
                }
              }
            }
            apUserName=null;
            out.println( "</td>\n" );
            out.println( "<td " + Color + "l\" width=\"15%\">" + comments + "</td>\n" );
            out.println( "</tr>\n" );
          //}
        }

        out.println( "</table>\n" );
        out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        out.println( "<tr>" );
        out.println( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
        out.println( "</TD></tr>" );
        out.println( "</table>\n" );
        out.println( "</form>\n" );
        out.println( "</body></html>\n" );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "Invoice Report",intcmIsApplication ) );
      }

      return;
    }
  }