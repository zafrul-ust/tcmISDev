package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 03-05-03 Removed the resize onLoad
 * 05-02-03 Searching FORMER_SUPPLIER_NAME also for looking up suppliers
 * 05-06-06 Added supplier qualifying level to the search and to the save
 * 08-27-03 Code cleanup and removed true from request.getsession()
 * 03-17-04 - Showing email address of supplier
 * 07-12-04 - Showing additional information about the supplier
 */

public class soundsLikeSupplier
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
    private  boolean allowupdate;
		private PrintWriter out = null;
    public void setupdateStatus( boolean id )
    {
      this.allowupdate=id;
    }

    private boolean getupdateStatus()
       throws Exception
    {
      return this.allowupdate;
    }

    public soundsLikeSupplier(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

     public void doResult(HttpServletRequest request, HttpServletResponse response)  throws ServletException,  IOException
    {
      out=response.getWriter();
      response.setContentType( "text/html" );

      String CompNum=request.getParameter( "CompNum" );
      if ( CompNum == null )
        CompNum="0";

      String Action=request.getParameter( "Action" );
      if ( Action == null )
        Action="";

      String searchString=request.getParameter( "SearchString" );
      if ( searchString == null )
        searchString="";

      String MfgId=request.getParameter( "MfgID" );
      if ( MfgId == null )
        MfgId="";

      String suppID=request.getParameter( "suppID" );
      if ( suppID == null )
        suppID="";

	String userAction=request.getParameter( "userAction" );
	if ( userAction == null )
	  userAction="";

	String rowNumber=request.getParameter( "rowNumber" );
	if ( rowNumber == null )
	  rowNumber="";

	String inventoryGroup=request.getParameter( "inventoryGroup" );
	if ( inventoryGroup == null )
	 inventoryGroup="";
	inventoryGroup=inventoryGroup.trim();


	if ( "sendPaymentTerms".equalsIgnoreCase( Action ) )
	{
	 buildSendPaymentTerms( suppID,inventoryGroup );
	}
	else if ( "suggestedsupp".equalsIgnoreCase( Action ) )
	{
	 String catpartno=request.getParameter( "catpartno" );
	 if ( catpartno == null )
		catpartno="";
	 String requestid=request.getParameter( "requestid" );
	 if ( requestid == null )
		requestid="";
	 String catalogid=request.getParameter( "catalogid" );
	 if ( catalogid == null )
		catalogid="";
	 showsuggestedsupp( catpartno,requestid,catalogid  );
	}
	else if ( "okupdate".equalsIgnoreCase( Action ) )
	{
	 buildsendSupplier( suppID,userAction,rowNumber,inventoryGroup );
	}
	else if ( "Search".equalsIgnoreCase( Action ) )
	{
	 buildLikeSupplier( "Search",searchString,suppID,userAction,rowNumber,inventoryGroup );
	}
	else
	{
	 buildLikeSupplier( "",searchString,suppID,userAction,rowNumber,inventoryGroup );
	}
	out.close();
 }

  public void showsuggestedsupp(String catprtno, String reqid, String catid)
  {
      //StringBuffer Msgn = new StringBuffer();
      DBResultSet dbrs = null;
      ResultSet rs = null;

      out.print(radian.web.HTMLHelpObj.printHTMLHeader("Suggested Supplier","",false));
      printJavaScripts("");
      out.print("// -->\n </SCRIPT>\n");

      String suggested_vendor="";
      String vendor_contact_email="";
      String vendor_contact_fax="";
      String vendor_contact_name="";
      String vendor_contact_phone="";
      String vendor_part_no = "";

      try
      {
        if (reqid.length() > 0)
        {
          dbrs=db.doQuery( "Select cai.SUGGESTED_VENDOR,cai.VENDOR_CONTACT_EMAIL,cai.VENDOR_CONTACT_FAX,cai.VENDOR_CONTACT_NAME,cai.VENDOR_CONTACT_PHONE,carn.VENDOR_PART_NO from customer.catalog_add_request_new carn,catalog_add_item cai where carn.REQUEST_ID = " +reqid +
				               " and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 order by LAST_UPDATED desc " );
        }
        else
        {
          dbrs=db.doQuery( "Select cai.SUGGESTED_VENDOR,cai.VENDOR_CONTACT_EMAIL,cai.VENDOR_CONTACT_FAX,cai.VENDOR_CONTACT_NAME,cai.VENDOR_CONTACT_PHONE,carn.VENDOR_PART_NO from customer.catalog_add_request_new carn, catalog_add_item cai where CATALOG_ID = '" +catid + "' and CAT_PART_NO = '" + catprtno + "' and LAST_UPDATED is not null"+
				  					" and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 order by LAST_UPDATED desc " );
        }
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          suggested_vendor=rs.getString( "SUGGESTED_VENDOR" ) == null ? "" : rs.getString( "SUGGESTED_VENDOR" ).trim();
          vendor_contact_email=rs.getString( "VENDOR_CONTACT_EMAIL" ) == null ? "" : rs.getString( "VENDOR_CONTACT_EMAIL" ).trim();
          vendor_contact_fax=rs.getString( "VENDOR_CONTACT_FAX" ) == null ? "" : rs.getString( "VENDOR_CONTACT_FAX" ).trim();
          vendor_contact_name=rs.getString( "VENDOR_CONTACT_NAME" ) == null ? "" : rs.getString( "VENDOR_CONTACT_NAME" ).trim();
          vendor_contact_phone=rs.getString( "VENDOR_CONTACT_PHONE" ) == null ? "" : rs.getString( "VENDOR_CONTACT_PHONE" ).trim();
          vendor_part_no=rs.getString( "VENDOR_PART_NO" ) == null ? "" : rs.getString( "VENDOR_PART_NO" ).trim();
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        if (dbrs != null ){dbrs.close();}
      }

      out.print("<BODY onload=\"javascript:window.resizeTo(450,380)\">\n");
      out.print("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"\">\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Update\">\n");
      out.print("<FONT FACE=\"Arial\" SIZE=\"3\">\n");
      out.print("<B>Suggested Supplier for Part : "+catprtno+"</B>\n</FONT>");
      out.print("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
      out.print("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n");
      out.print("<TR><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>\n");
      out.print("<TR><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD></TR>\n");
      out.print("<TR>\n");
      out.print("<TD WIDTH=\"10%\" height=\"25\" CLASS=\"Inwhiter\"><B>Supplier Name:  </B></TD>\n");
      out.print("<TD WIDTH=\"30%\" height=\"25\" CLASS=\"Inwhitel\">"+suggested_vendor+"\n</TD>\n");
      out.print("</TR>\n");

      out.print("<TR>\n");
      out.print("<TD WIDTH=\"10%\" height=\"25\" CLASS=\"Inbluer\"><B>Contact Name:  </B></TD>\n");
      out.print("<TD WIDTH=\"30%\" height=\"25\" CLASS=\"Inbluel\">"+vendor_contact_name+"\n</TD>\n");
      out.print("</TR>\n");

      out.print("<TR>\n");
      out.print("<TD WIDTH=\"10%\" height=\"25\" CLASS=\"Inwhiter\"><B>Contact Email:  </B></TD>\n");
      out.print("<TD WIDTH=\"30%\" height=\"25\" CLASS=\"Inwhitel\">"+vendor_contact_email+"\n</TD>\n");
      out.print("</TR>\n");

      out.print("<TR>\n");
      out.print("<TD WIDTH=\"10%\" height=\"25\" CLASS=\"Inbluer\"><B>Contact Phone/Fax:  </B></TD>\n");
      out.print("<TD WIDTH=\"30%\" height=\"25\" CLASS=\"Inbluel\">"+vendor_contact_phone+" / "+vendor_contact_fax+"\n</TD>\n");
      out.print("</TR>\n");

      out.print("<TR>\n");
      out.print("<TD WIDTH=\"10%\" height=\"25\" CLASS=\"Inwhiter\"><B>Supplier Part No:  </B></TD>\n");
      out.print("<TD WIDTH=\"30%\" height=\"25\" CLASS=\"Inwhitel\">"+vendor_part_no+"\n</TD>\n");
      out.print("</TR>\n");

      out.print("</TABLE>\n");
      out.print("<CENTER><BR><BR>&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Close\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      out.print("</FORM></BODY></HTML>\n");

      return;
     }

    public void buildsendSupplier( String supplierID,String userAction,String rowNumber,String inventoryGroup)
   {
    //StringBuffer Msgn = new StringBuffer();
    //StringBuffer Msgbody = new StringBuffer();
    DBResultSet dbrs = null;
    ResultSet rs = null;

    nematid_Servlet = bundle.getString("PURCHASE_ORDER_SUPPLIER");
    out.print(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Supplier","",false));
    printJavaScripts(rowNumber);
    out.print("// -->\n </SCRIPT>\n");

    //StringBuffer Msgtemp = new StringBuffer();
    //Build the Java Script Here and Finish the thing
    out.print("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.print("<!--\n");
    out.print("function sendSupplierData()\n");
    out.print("{\n");
		String paymentterms = "";

    try
    {
			 dbrs = db.doQuery("select fx_supplier_ig_payment_term ('"+supplierID+"','"+inventoryGroup+"') PAYMENT_TERMS from dual");
			 rs=dbrs.getResultSet();
			 while(rs.next())
			 {
					paymentterms = rs.getString("PAYMENT_TERMS")==null?"":rs.getString("PAYMENT_TERMS");
			 }

			dbrs = db.doQuery("Select * from supplier_address_view  where supplier = '"+supplierID+"'");
			rs=dbrs.getResultSet();

		while(rs.next())
		{
	  String supplier = rs.getString("SUPPLIER_NAME")==null?"":rs.getString("SUPPLIER_NAME");
	  //String location_id = rs.getString("LOCATION_ID")==null?"":rs.getString("LOCATION_ID");
	  String country_abbrev = rs.getString("COUNTRY_ABBREV")==null?"":rs.getString("COUNTRY_ABBREV");
	  String state_abbrev = rs.getString("STATE_ABBREV")==null?"":rs.getString("STATE_ABBREV");
	  String address_line_1 = rs.getString("ADDRESS_LINE_1")==null?"":rs.getString("ADDRESS_LINE_1");
	  String address_line_2 = rs.getString("ADDRESS_LINE_2")==null?"":rs.getString("ADDRESS_LINE_2");
	  //String address_line_3 = rs.getString("ADDRESS_LINE_3")==null?"":rs.getString("ADDRESS_LINE_3");
	  String city = rs.getString("CITY")==null?"":rs.getString("CITY");
	  String zip = rs.getString("ZIP")==null?"":rs.getString("ZIP");
	  //String location_desc = rs.getString("LOCATION_DESC")==null?"":rs.getString("LOCATION_DESC");
	  //String client_location_code = rs.getString("CLIENT_LOCATION_CODE")==null?"":rs.getString("CLIENT_LOCATION_CODE");
	  String mainphone = rs.getString("MAIN_PHONE")==null?"":rs.getString("MAIN_PHONE");
	  //String paymentterms = rs.getString("DEFAULT_PAYMENT_TERMS")==null?"":rs.getString("DEFAULT_PAYMENT_TERMS");
      String supprating = rs.getString("QUALIFICATION_LEVEL")==null?"":rs.getString("QUALIFICATION_LEVEL").trim();

	 if ("secondarySupplier".equalsIgnoreCase(userAction) )
	 {
	   out.println( "secondarysuppliercelldivrow = opener.document.getElementById(\"secondarysuppliercelldivrow" + rowNumber + "" + rowNumber + "\");\n" );
	   out.println( "var innHtmlline = \"<input type=\\\"text\\\" size=\\\"5\\\" CLASS=\\\"HEADER\\\" name='secondarysupplier"+rowNumber+"' id='secondarysupplier"+rowNumber+"'>\";\n" );
	   out.println( "innHtmlline =  innHtmlline +\"<BUTTON name='secondarysupplierbutton' OnClick=getSecondarySupplier('"+rowNumber+"') type=button><IMG src=\\\"/images/search_small.gif\\\" alt=\\\"Secondary Supplier\\\"></BUTTON>\";\n" );
	   out.println( "innHtmlline =  innHtmlline + \""+supplier+" \" + \""+address_line_1+" \" + \""+address_line_2+" \" + \""+city+" \" + \""+state_abbrev+"-\" + \""+zip+" \" + \""+country_abbrev+"\";\n" );
	   out.println( "secondarysuppliercelldivrow.innerHTML = innHtmlline;\n" );

	   out.println( "secondarysupplier = opener.document.getElementById(\"secondarysupplier" + rowNumber + "\");\n" );
	   out.println( "secondarysupplier.className = \"HEADER\";\n");
	   out.println( "secondarysupplier.value = \""+supplierID+"\";\n");

	   out.println( "linechange = opener.document.getElementById(\"linechange" + rowNumber + "\");\n" );
       out.println( "linechange.value = \"Yes\";\n" );
	 }
	 else
	 {
	  out.print("selectedRow = opener.document.getElementById(\"supplierid\");\n");
	  out.print("selectedRow.className = \"HEADER\";\n");
      out.print("selectedRow.value = \""+supplierID+"\";\n");

	  out.print("selectedRow = opener.document.getElementById(\"validsupplier\");\n");
      out.print("selectedRow.value = \"Yes\";\n");

      out.print( "selectedRow = opener.document.getElementById(\"validordertaker\");\n" );
      out.print( "selectedRow.value = \"No\";\n" );

      out.print( "selectedRow = opener.document.getElementById(\"faxdate\");\n" );
      out.print( "selectedRow.value = \"\";\n" );

      out.print( "selectedRow = opener.document.getElementById(\"accepteddate\");\n" );
      out.print( "selectedRow.value = \"\";\n" );

      out.print( "selectedRow = opener.document.getElementById(\"supplierline1\");\n" );
      out.print( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + supplier + "</FONT>\";\n" );

      out.print( "selectedRow = opener.document.getElementById(\"supplierline2\");\n" );
      out.print( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + address_line_1 + "</FONT>\";\n" );

	  if (address_line_2.length() > 0)
	  {
        out.print( "selectedRow = opener.document.getElementById(\"supplierline3\");\n" );
        out.print( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + address_line_2 + "</FONT>\";\n" );

        out.print( "selectedRow = opener.document.getElementById(\"supplierline4\");\n" );
        out.print("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+city+", "+state_abbrev+" "+zip+" "+country_abbrev+"</FONT>\";\n");
	  }
	  else
	  {
	    out.print("selectedRow = opener.document.getElementById(\"supplierline3\");\n");
	    out.print("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+city+", "+state_abbrev+" "+zip+" "+country_abbrev+"</FONT>\";\n");

	    out.print("selectedRow = opener.document.getElementById(\"supplierline4\");\n");
	    out.print("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n");
	  }

        out.print( "mfgphoneno = opener.document.getElementById(\"mfgphoneno\");\n" );
        out.print( "mfgphoneno.value = \"" + mainphone + "\";\n" );

        out.print( "paymentterms = opener.document.getElementById(\"paymentterms\");\n" );
        out.print( "paymentterms.value = \"" + paymentterms + "\";\n" );

        out.print( "selectedRow = opener.document.getElementById(\"suppRating\");\n" );
        out.print( "selectedRow.value = \"" + supprating + "\";\n" );
	 }
	}
    }
      catch (Exception e)
      {
	  e.printStackTrace();
      }
      finally
      {
	if (dbrs!= null) {dbrs.close();}
      }


    out.print("window.close();\n");
    out.print(" }\n");
    out.print("// -->\n</SCRIPT>\n");
    //out.print(Msgtemp);
    //out.print("<BODY><BR><BR>\n");
    out.print("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
	out.print("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
	out.print("<INPUT TYPE=\"hidden\" NAME=\"revisionDate\" VALUE=\""+supplierID+"\">\n");
    out.print("<CENTER><BR><BR>\n");
    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    out.print("</FORM></BODY></HTML>\n");
    //out.print(Msgbody);

    return;
   }

	 public void buildSendPaymentTerms(String supplierId, String inventoryGroup) {
		DBResultSet dbrs = null;
		ResultSet rs = null;

		nematid_Servlet = bundle.getString("PURCHASE_ORDER_SUPPLIER");
		out.print(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Supplier", "",false));
		//out.print("// -->\n </SCRIPT>\n");

		out.print("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
		out.print("<!--\n");
		out.print("function sendSupplierData()\n");
		out.print("{\n");
		String paymentterms = "";

		try {
		 dbrs = db.doQuery("select fx_supplier_ig_payment_term ('" + supplierId + "','" +
			inventoryGroup.trim() + "') PAYMENT_TERMS from dual");
		 rs = dbrs.getResultSet();
		 while (rs.next()) {
			paymentterms = rs.getString("PAYMENT_TERMS") == null ? "" :
			 rs.getString("PAYMENT_TERMS");
		 }

		 out.print("paymentterms = opener.document.getElementById(\"paymentterms\");\n");
		 out.print("paymentterms.value = \"" + paymentterms + "\";\n");

         out.print("supplierDefaultPaymentTerms = opener.document.getElementById(\"supplierDefaultPaymentTerms\");\n");
		 out.print("supplierDefaultPaymentTerms.value = \"" + paymentterms + "\";\n");
        }
		catch (Exception e) {
		 e.printStackTrace();
		}
		finally {
		 if (dbrs != null) {
			dbrs.close();
		 }
	}

		out.print("window.close();\n");
		out.print(" }\n");
		out.print("// -->\n</SCRIPT>\n");
		out.print("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
		out.print("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet +
		 "Session=Update\">\n");
		out.print("<INPUT TYPE=\"hidden\" NAME=\"revisionDate\" VALUE=\"" + supplierId +
		 "\">\n");
		out.print("<CENTER><BR><BR>\n");
		out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
		out.print("</FORM></BODY></HTML>\n");
		return;
	 }

     public void buildLikeSupplier( String SearchFlag, String SearchString,String MfgID1,String userAction,String rowNumber,String inventoryGroup)
     {
      //StringBuffer Msgl = new StringBuffer();
      MfgID1 = MfgID1.trim();
      DBResultSet dbrs = null;
      ResultSet rs = null;

      boolean foundsupplier=false;
      if ( ( MfgID1.trim().length() > 0 ) && ( !"Search".equalsIgnoreCase( SearchFlag ) ) )
      {
        try
        {
          int test_number=DbHelpers.countQuery( db,"select count(*) from supplier where SUPPLIER = '" + MfgID1 + "' and STATUS = 'Active'" );

          if ( test_number == 1 )
          {
            foundsupplier=true;
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          out.print( "<BODY><BR><BR>\n" );
          out.print( "<FONT FACE=\"Arial\" SIZE=\"3\">Error Occured.<BR>\n" );
          out.print( "Please Try Again.\n" );
          out.print( "<CENTER><BR><BR>\n" );
          out.print( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
          out.print( "</FORM></BODY></HTML>\n" );
          return;
        }
      }

      if ( foundsupplier )
      {
        buildsendSupplier( MfgID1,userAction,rowNumber,inventoryGroup );
        return;
      }

      nematid_Servlet = bundle.getString("PURCHASE_ORDER_SUPPLIER");
      out.print(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Supplier","",false));
      out.print("<SCRIPT SRC=\"/clientscripts/popopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.print("<SCRIPT SRC=\"/clientscripts/posupplier.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
      printJavaScripts(rowNumber);
      out.print("function ShowSearch(name,entered)\n");
      out.print("{\n");
      out.print(" with (entered)\n");
      out.print(" {\n");
      out.print(" loc = \""+nematid_Servlet+"&Action=\" + name.toString() + \"&SearchString=\";\n");
      out.print(" loc = loc + window.document.SupplierLike.SearchString.value;\n");
	  out.print(" loc = loc + \"&userAction=\"; \n");
      out.print(" loc = loc + window.document.SupplierLike.userAction.value;\n");
	  out.print(" loc = loc + \"&rowNumber=\"; \n");
      out.print(" loc = loc + window.document.SupplierLike.rowNumber.value;\n");
			out.print(" loc = loc + \"&inventoryGroup=\"; \n");
      out.print(" loc = loc + window.document.SupplierLike.inventoryGroup.value;\n");
      //out.print(" loc = loc + \"&suppID=\"; \n");
      //out.print(" loc = loc + window.document.SupplierLike.supplierid.value;\n");
      out.print("var supplierrep = window.document.SupplierLike.supplierid.value;\n");
      out.print("supplierrep = supplierrep.replace(/&/gi, \"%26\");\n");
      out.print(" loc = loc + \"&suppID=\" + supplierrep; \n");
      out.print(" }\n");
      //out.print(" alert(loc);\n");
      out.print("  window.location.replace(loc);\n");
      out.print(" }\n");
      out.print("// -->\n </SCRIPT>\n");
      out.print("<BODY>\n"); //out.print("<BODY onload=\"javascript:window.resizeTo(600,420)\">\n");
      out.print("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"supplierdesc\" VALUE=\"\">\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"addline1\" VALUE=\"\">\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"addline2\" VALUE=\"\">\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"addline3\" VALUE=\"\">\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"mfgphonenum\" VALUE=\"\">\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"paymentterms\" VALUE=\"\">\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"quallevel\" VALUE=\"\">\n");
	  out.print("<INPUT TYPE=\"hidden\" NAME=\"suppemail\" VALUE=\"\">\n");
	  out.print("<INPUT TYPE=\"hidden\" NAME=\"userAction\" VALUE=\""+userAction+"\">\n");
	  out.print("<INPUT TYPE=\"hidden\" NAME=\"rowNumber\" VALUE=\""+rowNumber+"\">\n");
		out.print("<INPUT TYPE=\"hidden\" NAME=\"inventoryGroup\" VALUE=\""+inventoryGroup+"\">\n");

      out.print("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.print("<TR>\n");
      out.print("<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text:</B></TD>\n");
      if  ("Search".equalsIgnoreCase(SearchFlag))
      {
        out.print("<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\""+SearchString+"\" SIZE=\"25\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
      }
      else
      {
        out.print("<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\""+MfgID1+"\" SIZE=\"25\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
      }

      //out.print("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"New\" value=\"New\" OnClick=\"ShowSearch(name,this)\"></TD></TR>\n");
      out.print("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\">&nbsp;</TD></TR>\n");

      out.print("<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Supplier ID:</B></FONT></TD><TD WIDTH=\"70%\" COLSPAN=\"2\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"supplierid\" value=\"\" SIZE=\"25\" readonly>\n");
      out.print("</TR>\n");
      out.print("</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.print("<TR>\n");
      out.print("<TH WIDTH=\"15%\"><B>Supplier Id</B></FONT></TH>\n");
      out.print("<TH WIDTH=\"15%\"><B>Level</B></FONT></TH>\n");
      out.print("<TH WIDTH=\"30%\"><B>Supplier Name</B></FONT></TH>\n");
      out.print("<TH WIDTH=\"45%\"><B>Supplier Address</B></FONT></TH>\n");    //12-13-02
      out.print("<TH WIDTH=\"10%\"><B>Phone</B></FONT></TH>\n");
	  out.print("<TH WIDTH=\"10%\"><B>Email</B></FONT></TH>\n");
	  out.print("<TH WIDTH=\"10%\"><B>Change Comments</B></FONT></TH>\n");
	  out.print("<TH WIDTH=\"10%\"><B>New Supplier</B></FONT></TH>\n");
	  out.print("<TH WIDTH=\"10%\"><B>Fed Tax ID</B></FONT></TH>\n");
	  out.print("<TH WIDTH=\"10%\"><B>E Supplier ID</B></FONT></TH>\n");
	  out.print("<TH WIDTH=\"10%\"><B>Account Number</B></FONT></TH>\n");

      out.print("</TR></TABLE>\n");

      //open scrolling table
      out.print("<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      out.print("<TBODY>\n");
      out.print("<TR>\n");
      out.print("<TD VALIGN=\"TOP\">\n");
      out.print("<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n");

      //Write code to insert rows here
      out.print("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n");

      int totalrecords = 0;
      int count = 0;
      String query = "";

      if  ("Search".equalsIgnoreCase(SearchFlag))
      {
        query += "Select STATUS_CHANGE_COMMENTS, NEW_SUPPLIER_ID, NEW_SUPPLIER_NAME, FEDERAL_TAX_ID, E_SUPPLIER_ID, ACCOUNT_NUMBER,SUPPLIER, SUPPLIER_NAME, COUNTRY_ABBREV, STATE_ABBREV, ADDRESS_LINE_1, ADDRESS_LINE_2, ADDRESS_LINE_3, CITY, ZIP, LOCATION_DESC, CLIENT_LOCATION_CODE, MAIN_PHONE, DEFAULT_PAYMENT_TERMS, STATUS, FORMER_SUPPLIER_NAME, QUALIFICATION_LEVEL, EMAIL, SUPPLIER_EMAIL from supplier_address_view  where lower(FORMER_SUPPLIER_NAME||ADDRESS_LINE_1||ADDRESS_LINE_2||SUPPLIER||SUPPLIER_NAME) like lower('%"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(SearchString)+"%') order by SUPPLIER_NAME asc";
        //query += "Select sup.SUPPLIER_NAME,sup.SUPPLIER,loc.* from supplier sup, location loc  where lower(SUPPLIER||SUPPLIER_NAME) like lower('%"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(SearchString)+"%') and sup.ORDER_LOCATION_ID = loc.LOCATION_ID(+) order by SUPPLIER_NAME asc";
      }
      else
      {
        query += "Select STATUS_CHANGE_COMMENTS, NEW_SUPPLIER_ID, NEW_SUPPLIER_NAME, FEDERAL_TAX_ID, E_SUPPLIER_ID, ACCOUNT_NUMBER,SUPPLIER, SUPPLIER_NAME, COUNTRY_ABBREV, STATE_ABBREV, ADDRESS_LINE_1, ADDRESS_LINE_2, ADDRESS_LINE_3, CITY, ZIP, LOCATION_DESC, CLIENT_LOCATION_CODE, MAIN_PHONE, DEFAULT_PAYMENT_TERMS, STATUS, FORMER_SUPPLIER_NAME, QUALIFICATION_LEVEL, EMAIL, SUPPLIER_EMAIL from supplier_address_view  where lower(FORMER_SUPPLIER_NAME||ADDRESS_LINE_1||ADDRESS_LINE_2||SUPPLIER||SUPPLIER_NAME) like lower('%"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(MfgID1)+"%') order by SUPPLIER_NAME asc";
      }

      if ( (!(MfgID1.trim().length() > 0)) && (!"Search".equalsIgnoreCase(SearchFlag)) )
      {

      }
      else
      {
	  try
	  {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          totalrecords+=1;
          count+=1;

          String supplier=BothHelpObjs.makeSpaceFromNull( rs.getString( "SUPPLIER" ) ).trim();
          String supplierId=BothHelpObjs.makeSpaceFromNull( rs.getString( "SUPPLIER_NAME" ) ).trim();

          //String location_id = rs.getString("LOCATION_ID")==null?"":rs.getString("LOCATION_ID");
          String country_abbrev=rs.getString( "COUNTRY_ABBREV" ) == null ? "" : rs.getString( "COUNTRY_ABBREV" );
          String state_abbrev=rs.getString( "STATE_ABBREV" ) == null ? "" : rs.getString( "STATE_ABBREV" );
          String address_line_1=rs.getString( "ADDRESS_LINE_1" ) == null ? "" : rs.getString( "ADDRESS_LINE_1" );
          String address_line_2=rs.getString( "ADDRESS_LINE_2" ) == null ? "" : rs.getString( "ADDRESS_LINE_2" );
          String address_line_3=rs.getString( "ADDRESS_LINE_3" ) == null ? "" : rs.getString( "ADDRESS_LINE_3" );
          String city=rs.getString( "CITY" ) == null ? "" : rs.getString( "CITY" );
          String zip=rs.getString( "ZIP" ) == null ? "" : rs.getString( "ZIP" );
          //String location_desc=rs.getString( "LOCATION_DESC" ) == null ? "" : rs.getString( "LOCATION_DESC" );
          //String client_location_code=rs.getString( "CLIENT_LOCATION_CODE" ) == null ? "" : rs.getString( "CLIENT_LOCATION_CODE" );
          String mainphone=rs.getString( "MAIN_PHONE" ) == null ? "" : rs.getString( "MAIN_PHONE" );
          String paymentterms=rs.getString( "DEFAULT_PAYMENT_TERMS" ) == null ? "" : rs.getString( "DEFAULT_PAYMENT_TERMS" );
          String statusa=rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" );
          //String frmsuppname=rs.getString( "FORMER_SUPPLIER_NAME" ) == null ? "" : rs.getString( "FORMER_SUPPLIER_NAME" );
          String quallevel=rs.getString( "QUALIFICATION_LEVEL" ) == null ? "" : rs.getString( "QUALIFICATION_LEVEL" );
		  String suppemail=rs.getString( "EMAIL" ) == null ? "" : rs.getString( "EMAIL" );

		  String stachgcomments=rs.getString( "STATUS_CHANGE_COMMENTS" ) == null ? "" : rs.getString( "STATUS_CHANGE_COMMENTS" );
		  //String suppemail=rs.getString( "NEW_SUPPLIER_ID" ) == null ? "" : rs.getString( "NEW_SUPPLIER_ID" );
		  String newsuppname=rs.getString( "NEW_SUPPLIER_NAME" ) == null ? "" : rs.getString( "NEW_SUPPLIER_NAME" );
		  String fedtaxid=rs.getString( "FEDERAL_TAX_ID" ) == null ? "" : rs.getString( "FEDERAL_TAX_ID" );
		  String esuppid=rs.getString( "E_SUPPLIER_ID" ) == null ? "" : rs.getString( "E_SUPPLIER_ID" );
		  String accntnum=rs.getString( "ACCOUNT_NUMBER" ) == null ? "" : rs.getString( "ACCOUNT_NUMBER" );

          String Color=" ";
          if ( count % 2 == 0 )
          {
            Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }
          else
          {
            Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }

          if ( "Inactive".equalsIgnoreCase( statusa ) )
          {
            Color="BGCOLOR=\"#c0c0c0\"";
          }

          if ("Active".equalsIgnoreCase(statusa))
          {
            out.print("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('"+supplier+"','"+HelpObjs.addescapesForJavascript(supplierId)+"','"+HelpObjs.addescapesForJavascript(address_line_1)+"','"+HelpObjs.addescapesForJavascript(address_line_2)+"','"+HelpObjs.addescapesForJavascript(city+", "+state_abbrev+" "+zip+" "+country_abbrev)+"','"+mainphone+"','"+paymentterms+"','"+quallevel+"','"+suppemail+"')\" BORDERCOLOR=\"black\">\n");
          }
          else
          {
            out.print("<TR ALIGN=\"MIDDLE\">\n");
          }

          out.print( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
          out.print( supplier );
          out.print( "</TD>\n" );
          out.print( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
          out.print( quallevel );
          out.print( "</TD>\n" );
          out.print( "<TD " + Color + " WIDTH=\"30%\" ALIGN=\"LEFT\">" );
          //out.print(supplierId);
          out.print( "<A HREF=\"javascript:showSupplierContacts('" + supplier + "')\">" + supplierId + "</A>" );

          out.print( "</TD>\n" );

          //12-13-02 add supplier address
          String tmpAddress="";
          if ( address_line_1.length() > 0 )
          {
            tmpAddress=address_line_1;
          }
          if ( address_line_2.length() > 0 )
          {
            tmpAddress+=", " + address_line_2;
          }
          if ( address_line_3.length() > 0 )
          {
            tmpAddress+=", " + address_line_3;
          }
          tmpAddress+=" " + city + " " + state_abbrev + " " + zip;
          out.print( "<TD " + Color + " WIDTH=\"45%\" ALIGN=\"LEFT\">" );
          out.print( tmpAddress );
          out.print( "</TD>\n" );

          out.print( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
          out.print( mainphone );
          out.print( "</TD>\n" );
		  out.print( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
		  out.print( suppemail );
		  out.print( "</TD>\n" );

		  out.print( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
		  out.print( stachgcomments );
		  out.print( "</TD>\n" );

		  out.print( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
		  out.print( newsuppname );
		  out.print( "</TD>\n" );

		  out.print( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
		  out.print( fedtaxid );
		  out.print( "</TD>\n" );

		  out.print( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
		  out.print( esuppid );
		  out.print( "</TD>\n" );

		  out.print( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
		  out.print( accntnum );
		  out.print( "</TD>\n" );

          out.print( "</TR>\n" );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.print( "An Error Occured While Querying the Database" );
        out.print( "</BODY>\n</HTML>\n" );
        return;
      }
      finally
      {
        if (dbrs!= null) {dbrs.close();}
      }
    }
      if (totalrecords==0){out.print("<TR><TD>No Records Found</TD></TR>\n");}
      out.print("</TABLE>\n");

      //close scrolling table
      out.print("</DIV>\n");
      out.print("</TD>\n");
      out.print("</TR>\n");
      out.print("</TBODY>\n");
      out.print("</TABLE>\n");

      out.print("<CENTER>\n");
      try
      {
        if ( this.getupdateStatus() )
        {
          out.print( "<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"sendSupplier(this.form)\" type=\"button\">\n" );
        }
      }
      catch ( Exception ex )
      {
        ex.printStackTrace();
      }
      out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");

      out.print("</FORM></BODY></HTML>\n");

      return;

    }
    protected void printJavaScripts(String compn)
    {
    //StringBuffer Msglt = new StringBuffer();

    out.print("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.print("<!--\n");
    out.print("function cancel()\n");
    out.print("{\n");
    out.print("window.close();\n");
    out.print("}\n");
    out.print("function sendSupplier(entered)\n");
    out.print("{\n");
    out.print(" with (entered)\n");
    out.print(" {\n");
    out.print(" eval( testmfgid = \"window.document.SupplierLike.supplierid\")\n");
    out.print(" if ( (eval(testmfgid.toString())).value.length > 0 )\n");
    out.print("{");

     out.println( "if (\"secondarySupplier\" == window.document.SupplierLike.userAction.value) \n" );
	 out.println( "{ \n" );
	 out.println( "secondarysuppliercelldivrow = opener.document.getElementById(\"secondarysuppliercelldivrow" + compn + "" + compn + "\");\n" );
	 out.println( "var innHtmlline = \"<input type=\\\"text\\\" size=\\\"5\\\" CLASS=\\\"HEADER\\\" name='secondarysupplier"+compn+"' id='secondarysupplier"+compn+"'>\";\n" );
	 out.println( "innHtmlline =  innHtmlline +\"<BUTTON name='secondarysupplierbutton' OnClick=getSecondarySupplier('"+compn+"') type=button><IMG src=\\\"/images/search_small.gif\\\" alt=\\\"Secondary Supplier\\\"></BUTTON>\";\n" );
	 out.println( "innHtmlline =  innHtmlline + window.document.SupplierLike.supplierdesc.value +\" \"+ window.document.SupplierLike.addline1.value +\" \"+  window.document.SupplierLike.addline2.value+ +\" \"+  window.document.SupplierLike.addline3.value;\n" );
	 out.println( "secondarysuppliercelldivrow.innerHTML = innHtmlline;\n" );

 	 out.println( "secondarysupplier = opener.document.getElementById(\"secondarysupplier" + compn + "\");\n" );
     out.println( "secondarysupplier.className = \"HEADER\";\n");
	 out.println( "secondarysupplier.value = window.document.SupplierLike.supplierid.value;\n" );

	 out.println( "linechange = opener.document.getElementById(\"linechange" + compn + "\");\n" );
	 out.println( "linechange.value = \"Yes\";\n" );
	 out.print("window.close();\n");
	 out.println( "} \n" );
	 out.println( "else" );
	 out.println( "{ \n" );
    //int compn = Integer.parseInt(controlname);

    out.print("selectedRow = opener.document.getElementById(\"supplierid\");\n");
    out.print("selectedRow.className = \"HEADER\";\n");
    out.print("selectedRow.value = window.document.SupplierLike.supplierid.value;\n");
    out.print("selectedRow = opener.document.getElementById(\"validsupplier\");\n");
    out.print("selectedRow.value = \"Yes\";\n");

    out.print("selectedRow = opener.document.getElementById(\"validordertaker\");\n");
    out.print("selectedRow.value = \"No\";\n");

    out.print("selectedRow = opener.document.getElementById(\"faxdate\");\n");
    out.print("selectedRow.value = \"\";\n");

    out.print("selectedRow = opener.document.getElementById(\"accepteddate\");\n");
    out.print("selectedRow.value = \"\";\n");

    out.print("mfgphoneno = opener.document.getElementById(\"mfgphoneno\");\n");
    out.print("mfgphoneno.value = window.document.SupplierLike.mfgphonenum.value;\n");

    out.print("paymentterms1 = opener.document.getElementById(\"paymentterms\");\n");
    out.print("paymentterms1.value = window.document.SupplierLike.paymentterms.value;\n");

    out.print("selectedRow = opener.document.getElementById(\"supplierline1\");\n");
    out.print("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.supplierdesc.value + \"<FONT>\" ;\n");

    out.print("selectedRow = opener.document.getElementById(\"supplierline2\");\n");
    out.print("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.addline1.value + \"<FONT>\" ;\n");

    out.print("if (window.document.SupplierLike.addline2.value.length > 0)");
    out.print("{");

    out.print("selectedRow = opener.document.getElementById(\"supplierline3\");\n");
    out.print("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.addline2.value + \"<FONT>\" ;\n");

    out.print("selectedRow = opener.document.getElementById(\"supplierline4\");\n");
    out.print("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.addline3.value + \"<FONT>\" ;\n");

	out.print("supplieremail = opener.document.getElementById(\"supplieremail\");\n");
	out.print("supplieremail.innerHTML = \"\" + window.document.SupplierLike.suppemail.value + \"\" ;\n");

    out.print("}");
    out.print("else");
    out.print("{");

    out.print("selectedRow = opener.document.getElementById(\"supplierline3\");\n");
    out.print("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.addline3.value + \"<FONT>\" ;\n");

    out.print("selectedRow = opener.document.getElementById(\"supplierline4\");\n");
    out.print("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"><FONT>\" ;\n");

    out.print("selectedRow = opener.document.getElementById(\"suppRating\");\n");
    out.print("selectedRow.value = \"\" + window.document.SupplierLike.quallevel.value + \"\";\n");
    /*out.print("selectedRow = opener.document.getElementById(\"xygval\");\n");
    out.print("selectedRow.value = \"\" + window.document.SupplierLike.quallevel.value + \"\";\n");*/

    out.println("}");
		out.print("sendPaymentTerms();\n");
		out.println("} \n" );
    out.print(" }\n");
    out.print(" }\n");

    out.print("}\n");

    out.print("function addsupplierID(matidvalue,matdesc,addline1,addline2,addline3,mfgphonenum,paymentterms,quallevel,suppemail)\n");
    out.print("{\n");
    out.print("document.SupplierLike.supplierid.value = matidvalue;\n");
    out.print("document.SupplierLike.supplierdesc.value = matdesc;\n");
    out.print("document.SupplierLike.addline1.value = addline1;\n");
    out.print("document.SupplierLike.addline2.value = addline2;\n");
    out.print("document.SupplierLike.addline3.value = addline3;\n");
    out.print("document.SupplierLike.mfgphonenum.value = mfgphonenum;\n");
    out.print("document.SupplierLike.paymentterms.value = paymentterms;\n");
    out.print("document.SupplierLike.quallevel.value = quallevel;\n");
    out.print("document.SupplierLike.suppemail.value = suppemail;\n");

    out.print("}\n");

    return;
    }
}