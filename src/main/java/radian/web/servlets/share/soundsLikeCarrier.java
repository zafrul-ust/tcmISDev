package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 08-27-03 Code cleanup and removed true from request.getsession()
 * 06-04-04 Also sorting the results by Hub
 */


public class soundsLikeCarrier
{
		private ServerResourceBundle bundle = null;
		private TcmISDBModel db = null;
		private String nematid_Servlet = "";
	private PrintWriter out = null;
		public soundsLikeCarrier(ServerResourceBundle b, TcmISDBModel d)
		{
				bundle = b;
				db = d;
		}
		 public void doResult(HttpServletRequest request, HttpServletResponse response)
					throws ServletException,  IOException
		{
		out = response.getWriter();
		response.setContentType("text/html");

		String inventoryGroup=request.getParameter( "inventoryGroup" );
		if ( inventoryGroup == null )
			inventoryGroup="";
		inventoryGroup = inventoryGroup.trim();

		String carrierID=request.getParameter( "carrierID" );
		if ( carrierID == null )
			carrierID="";
		carrierID = carrierID.trim();

	 String searchString=request.getParameter( "searchString" );
		if ( searchString == null )
			searchString="";
		searchString = searchString.trim();

	 String Action=request.getParameter( "Action" );
		if ( Action == null )
			Action="";
		Action = Action.trim();

		if ("okupdate".equalsIgnoreCase(Action))
		{
		buildsendSupplier(carrierID);
		}
		else if ("Search".equalsIgnoreCase(Action))
		{
		buildLikeSupplier("Search",carrierID,inventoryGroup,searchString);
		}
		else
		{
		buildLikeSupplier("",carrierID,inventoryGroup,searchString);
		}
		out.close();
	}

	public void buildsendSupplier( String date )
	{
		//StringBuffer Msgn=new StringBuffer();
		//StringBuffer Msgbody=new StringBuffer();
		DBResultSet dbrs=null;
		ResultSet rs=null;

		nematid_Servlet=bundle.getString( "PURCHASE_ORDER_CARRIER" );
		out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Carrier","",false ) );
		printJavaScripts();
		out.println( "// -->\n </SCRIPT>\n" );

		//StringBuffer Msgtemp=new StringBuffer();
		//Build the Java Script Here and Finish the thing
		out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
		out.println( "<!--\n" );
		out.println( "function sendSupplierData()\n" );
		out.println( "{\n" );

		try
		{
			dbrs=db.doQuery( "Select * from carrier_info  where lower(CARRIER_CODE) = lower('" + date + "') " );
			rs=dbrs.getResultSet();

			while ( rs.next() )
			{
				String carrier_code=rs.getString( "CARRIER_CODE" ) == null ? "" : rs.getString( "CARRIER_CODE" ).trim();
				String company_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
        if (company_id.equalsIgnoreCase("Radian"))
        {
          company_id = "Haas TCM";
        }
        //String ship_to_loc_id = rs.getString("SHIP_TO_LOC_ID")==null?"":rs.getString("SHIP_TO_LOC_ID").trim();
				//String bill_to_loc_id = rs.getString("BILL_TO_LOC_ID")==null?"":rs.getString("BILL_TO_LOC_ID").trim();
				String account=rs.getString( "ACCOUNT" ) == null ? "" : rs.getString( "ACCOUNT" ).trim();
				//String notes = rs.getString("NOTES")==null?"":rs.getString("NOTES").trim();
				String carrier_name=HelpObjs.addescapesForJavascript(rs.getString( "CARRIER_NAME" ) == null ? "" : rs.getString( "CARRIER_NAME" ).trim());
				String hub=rs.getString( "HUB" ) == null ? "" : rs.getString( "HUB" ).trim();

				out.println( "selectedRow = opener.document.getElementById(\"carrierID\");\n" );
				out.println( "selectedRow.className = \"HEADER\";\n" );
				out.println( "selectedRow.value = \"" + carrier_code + "\";\n" );
				out.println( "selectedRow = opener.document.getElementById(\"carrieraccount\");\n" );
				out.println( "selectedRow.value = \"" + account + "\";\n" );
				out.println( "selectedRow = opener.document.getElementById(\"validcarrier\");\n" );
				out.println( "selectedRow.value = \"Yes\";\n" );
				out.println( "selectedRow = opener.document.getElementById(\"carrierline1\");\n" );
				out.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + hub + "/" + company_id + "</FONT>\";\n" );
				out.println( "selectedRow = opener.document.getElementById(\"carrierline2\");\n" );
				out.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + carrier_name + "/" + account + "</FONT>\";\n" );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if (dbrs!= null) {dbrs.close();}
		}

		out.println( "window.close();\n" );
		out.println( " }\n" );
		out.println( "// -->\n</SCRIPT>\n" );
		//out.println( Msgtemp );
		//out.println("<BODY><BR><BR>\n");
		out.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	out.println( "<FORM METHOD=\"POST\" id=\"revDateLike\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
		out.println( "<CENTER><BR><BR>\n" );
		out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
		out.println( "</FORM></BODY></HTML>\n" );
		//out.println( Msgbody );

		return;
	 }

	 public void buildLikeSupplier( String SearchFlag,String carrierCode,String inventoryGroup,String searchString )
	 {
		 //StringBuffer Msgl=new StringBuffer();
		 DBResultSet dbrs=null;
		 ResultSet rs=null;
		 carrierCode=carrierCode.trim();

		 boolean foundsupplier=false;

		 if ( ( carrierCode.trim().length() > 0 ) && ( !"Search".equalsIgnoreCase( SearchFlag ) ) )
		 {
			 try
			 {
		 int test_number=DbHelpers.countQuery( db,"select count(*) from carrier_info where lower(CARRIER_CODE) = lower('" + carrierCode + "') and (inventory_group='"+inventoryGroup+"' or inventory_group='ALL') and status='ACTIVE' " );

				 if ( test_number == 1 )
				 {
					 foundsupplier=true;
				 }
			 }
			 catch ( Exception e )
			 {
				 e.printStackTrace();
				 out.println( "<BODY><BR><BR>\n" );
				 out.println( "<FONT FACE=\"Arial\" SIZE=\"3\">Error Occured.<BR>\n" );
				 out.println( "Please Try Again.\n" );
				 out.println( "<CENTER><BR><BR>\n" );
				 out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
				 out.println( "</FORM></BODY></HTML>\n" );
				 return;
			 }
		 }

		 if ( foundsupplier )
		 {
			 buildsendSupplier( carrierCode);
			 return;
		 }

		 nematid_Servlet=bundle.getString( "PURCHASE_ORDER_CARRIER" );

		 out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Carrier","",false ) );
		 printJavaScripts();
		 out.println( "function ShowSearch(name,entered)\n" );
		 out.println( "{\n" );
		 out.println( " with (entered)\n" );
		 out.println( " {\n" );
		 out.println( " loc = \"" + nematid_Servlet + "&Action=\" + name.toString() + \"&searchString=\";\n" );
		 out.println( " loc = loc + window.document.SupplierLike.SearchString.value;\n" );
		 out.println( " loc = loc + \"&carrierID=\"; \n" );
		 out.println( " loc = loc + window.document.SupplierLike.supplierid.value;\n" );
		 out.println( " loc = loc + \"&inventoryGroup=" + inventoryGroup + "\"; \n" );
		 //out.println( " loc = loc + \"&shiptoid=" + shipTo + "\"; \n" );
		 out.println( " }\n" );
		 //out.println(" alert(loc);\n");
		 out.println( "  window.location.replace(loc);\n" );
		 out.println( " }\n" );
		 out.println( "// -->\n </SCRIPT>\n" );
		 out.println( "<BODY onload=\"javascript:window.resizeTo(700,420)\">\n" );
		 out.println( "<FORM METHOD=\"POST\" id=\"SupplierLike\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
		 out.println( "<INPUT TYPE=\"hidden\" ID=\"addline1\" NAME=\"addline1\" VALUE=\"\">\n" );
		 out.println( "<INPUT TYPE=\"hidden\" ID=\"addline2\" NAME=\"addline2\" VALUE=\"\">\n" );
		 out.println( "<INPUT TYPE=\"hidden\" ID=\"carrieraccount\" NAME=\"carrieraccount\" VALUE=\"\">\n" );
		 out.println( "<INPUT TYPE=\"hidden\" ID=\"inventoryGroup\" NAME=\"inventoryGroup\" VALUE=\"" + inventoryGroup + "\">\n" );
		 out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	 out.println( "<TR>\n" );
	 if ( "Search".equalsIgnoreCase( SearchFlag ) )
		{
		out.println("<TD WIDTH=\"20%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"SearchString\" name=\"SearchString\" value=\""+searchString+"\" SIZE=\"25\"></TD><TD WIDTH=\"70%\" ALIGN=\"LEFT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"Search\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
		}
		else
		{
		out.println("<TD WIDTH=\"20%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"SearchString\" name=\"SearchString\" value=\""+carrierCode+"\" SIZE=\"25\"></TD><TD WIDTH=\"70%\" ALIGN=\"LEFT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"Search\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
		}
	 out.println( "</TR>\n" );
		 out.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Shipper Code:</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" id=\"supplierid\" name=\"supplierid\" value=\"\" SIZE=\"25\" readonly>\n" );
		 out.println( "</TR>\n" );
		 out.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
		 out.println( "<TR>\n" );
		 out.println( "<TH WIDTH=\"15%\"><B>Code</B></FONT></TH>\n" );
		 out.println( "<TH WIDTH=\"35%\"><B>Carrier Name</B></FONT></TH>\n" );
		 out.println( "<TH WIDTH=\"15%\"><B>Company</B></FONT></TH>\n" );
     out.println( "<TH WIDTH=\"5%\"><B>Inventory Group</B></FONT></TH>\n" );
     //out.println( "<TH WIDTH=\"10%\"><B>Ship To</B></FONT></TH>\n" );
		 out.println( "<TH WIDTH=\"20%\"><B>Notes</B></FONT></TH>\n" );
		 out.println( "</TR></TABLE>\n" );

		 //open scrolling table
		 out.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
		 out.println( "<TBODY>\n" );
		 out.println( "<TR>\n" );
		 out.println( "<TD VALIGN=\"TOP\">\n" );
		 out.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );

		 //Write code to insert rows here
		 out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

		 int totalrecords=0;
		 int count=0;
		 String query="";

	 if ( "Search".equalsIgnoreCase( SearchFlag ) )
	 {
		carrierCode = searchString;
	 }

		 query+="select * from carrier_info where lower(CARRIER_CODE||CARRIER_NAME) like lower('%" + carrierCode + "%') and (inventory_group='"+inventoryGroup+"' or inventory_group='ALL')  and status='ACTIVE' order by company_id,ship_to_loc_id,carrier_name,carrier_code ";
	 try
	 {
		 dbrs=db.doQuery( query );
		 rs=dbrs.getResultSet();

				 while ( rs.next() )
				 {
					 totalrecords+=1;
					 count+=1;

					 String carrier_code=rs.getString( "CARRIER_CODE" ) == null ? "" : rs.getString( "CARRIER_CODE" ).trim();
					 String company_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
           if (company_id.equalsIgnoreCase("Radian"))
           {
            company_id = "Haas TCM";
           }
					 //String ship_to_loc_id=rs.getString( "SHIP_TO_LOC_ID" ) == null ? "" : rs.getString( "SHIP_TO_LOC_ID" ).trim();
					 //String bill_to_loc_id = rs.getString("BILL_TO_LOC_ID")==null?"":rs.getString("BILL_TO_LOC_ID").trim();
					 String account=rs.getString( "ACCOUNT" ) == null ? "" : rs.getString( "ACCOUNT" ).trim();
					 //String notes = rs.getString("NOTES")==null?"":rs.getString("NOTES").trim();
					 String carrier_name=HelpObjs.addescapesForJavascript(rs.getString( "CARRIER_NAME" ) == null ? "" : rs.getString( "CARRIER_NAME" ).trim());
					 String hub=rs.getString( "HUB" ) == null ? "" : rs.getString( "HUB" ).trim();
					 String notes=rs.getString( "NOTES" ) == null ? "" : rs.getString( "NOTES" ).trim();
           String inventoryGroupEach=rs.getString( "inventory_group" ) == null ? "" : rs.getString( "inventory_group" ).trim();

					 String Color=" ";
					 if ( count % 2 == 0 )
					 {
						 Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
					 }
					 else
					 {
						 Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
					 }

					 out.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('" + carrier_code + "','" + hub + "/" + company_id + "','" + HelpObjs.addescapesForJavascript( carrier_name ) + "/" + account + "','" + account + "')\" BORDERCOLOR=\"black\">\n" );
					 out.println( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
					 out.println( carrier_code );
					 out.println( "</TD>\n" );
					 out.println( "<TD " + Color + " WIDTH=\"35%\" ALIGN=\"LEFT\">" );
					 out.println( carrier_name );
					 out.println( "</TD>\n" );
					 out.println( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
					 out.println(company_id);
					 out.println( "</TD>\n" );
           out.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
					 out.println(inventoryGroupEach);
					 out.println( "</TD>\n" );
           /*out.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
					 out.println( ship_to_loc_id );
					 out.println( "</TD>\n" );*/
					 out.println( "<TD " + Color + " WIDTH=\"20%\" ALIGN=\"LEFT\">" );
					 out.println( notes );
					 out.println( "</TD>\n" );
					 out.println( "</TR>\n" );
				 }
			 }
			 catch ( Exception e )
			 {
				 e.printStackTrace();
				 out.println( "An Error Occured While Querying the Database" );
				 out.println( "</BODY>\n</HTML>\n" );
				 return;
			 }
			 finally
			 {
				 if (dbrs!= null) {dbrs.close();}
			 }

		 if ( totalrecords == 0 )
		 {
			 out.println( "<TR><TD>No Records Found</TD></TR>\n" );
		 }
		 out.println( "</TABLE>\n" );

		 //close scrolling table
		 out.println( "</DIV>\n" );
		 out.println( "</TD>\n" );
		 out.println( "</TR>\n" );
		 out.println( "</TBODY>\n" );
		 out.println( "</TABLE>\n" );
		 out.println( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"okupdate\" name=\"okupdate\" value=\"Ok\" OnClick=\"sendCarrier(this.form)\" type=\"button\">\n" );
		 out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
		 out.println( "</FORM></BODY></HTML>\n" );

		 return;
	 }

	 protected void printJavaScripts()
	 {
		 //StringBuffer Msglt=new StringBuffer();

		 out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
		 out.println( "<!--\n" );
		 out.println( "function cancel()\n" );
		 out.println( "{\n" );
		 out.println( "window.close();\n" );
		 out.println( "}\n" );
		 out.println( "function sendCarrier(entered)\n" );
		 out.println( "{\n" );
		 out.println( " with (entered)\n" );
		 out.println( " {\n" );
		 out.println( " eval( testmfgid = \"window.document.SupplierLike.supplierid\")\n" );
		 out.println( " if ( (eval(testmfgid.toString())).value.length > 0 )\n" );
		 out.println( "{" );
		 out.println( "selectedRow = opener.document.getElementById(\"carrierID\");\n" );
		 out.println( "selectedRow.className = \"HEADER\";\n" );
		 out.println( "selectedRow.value = window.document.SupplierLike.supplierid.value;\n" );

		 out.println( "selectedRow = opener.document.getElementById(\"carrieraccount\");\n" );
		 out.println( "selectedRow.value = window.document.SupplierLike.carrieraccount.value;\n" );

		 out.println( "selectedRow = opener.document.getElementById(\"validcarrier\");\n" );
		 out.println( "selectedRow.value = \"Yes\";\n" );

		 out.println( "selectedRow = opener.document.getElementById(\"carrierline1\");\n" );
		 out.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.addline1.value + \"<FONT>\" ;\n" );

		 out.println( "selectedRow = opener.document.getElementById(\"carrierline2\");\n" );
		 out.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.addline2.value + \"<FONT>\" ;\n" );

		 out.println( "window.close();\n" );
		 out.println( " }\n" );
		 out.println( " }\n" );

		 out.println( "}\n" );

		 out.println( "function addsupplierID(matidvalue,addline1,addline2,carrieraccount)\n" );
		 out.println( "{\n" );
		 out.println( "document.SupplierLike.supplierid.value = matidvalue;\n" );
		 out.println( "document.SupplierLike.addline1.value = addline1;\n" );
		 out.println( "document.SupplierLike.addline2.value = addline2;\n" );
		 out.println( "document.SupplierLike.carrieraccount.value = carrieraccount;\n" );
		 out.println( "}\n" );

		 return;
	 }
}
