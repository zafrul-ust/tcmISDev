package radian.web.cabinets;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 06-29-04 - Showing the status as picked if an MR is on a Picklist
 */

public class cabUploadConfirm
{
  private TcmISDBModel db=null;
  PrintWriter out = null;

  private boolean intcmIsApplication = false;

  public cabUploadConfirm( TcmISDBModel d )
  {
	db=d;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
	out=response.getWriter();
	response.setContentType( "text/html" );
  HttpSession session = request.getSession();
	PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
	Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
	if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
	{
	 intcmIsApplication = true;
	}

	String User_Session=request.getParameter( "session" );
	if ( User_Session == null )
	  User_Session="New";

	String mrslist=request.getParameter( "mrslist" );
	if ( mrslist == null )
	  mrslist="";
	mrslist=mrslist.trim();

	StringTokenizer st = new StringTokenizer(mrslist, ",");
	String searchmrlist = "'";
	if(st.countTokens() > 1)
	{
		while(st.hasMoreTokens())
		{
			String tok = st.nextToken();
			searchmrlist += tok + "','";
		}
		searchmrlist = searchmrlist.substring(0, searchmrlist.length() - 2);
	}
	else if (mrslist.length() > 0)
	{
	  searchmrlist = "'"+mrslist+"'";
	}
	else
	{
	  searchmrlist = "' '";
	}

	String User_Action=request.getParameter( "UserAction" );
	if ( User_Action == null )
	  User_Action="New";
	User_Action=User_Action.trim();

	try
	{
	  if ( User_Action.equalsIgnoreCase( "Search" ) )
	  {
		buildHeader( mrslist);
		if (searchmrlist.trim().length() > 3)
		{
		  buildDetails( searchmrlist );
		}
		else
		{
		  out.println( "No MRs were created or No number was entered to search");
		}
		out.println( "</body></html>\n" );
	  }
	  else
	  {
		buildHeader( mrslist);
		out.println( "</body></html>\n" );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  out.close();
	}
  }

private void buildDetails( String mrnumbers )
{
  //StringBuffer Msg=new StringBuffer();
  DBResultSet dbrs=null;
  ResultSet searchRs=null;
  int count=0;

  out.println( "<TABLE BORDER=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
  out.println( "<TD HEIGHT=20 WIDTH=\"100%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  out.println( "<B>MRs Created:</B>&nbsp;\n" );
  out.println( "</TD>\n" );
  out.println( "</TR>\n" );
  out.println( "</TABLE>\n" );

  out.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");
  String Color="CLASS=\"white";

  try
  {
	/*String query="SELECT rql.application, rql.fac_part_no, rql.inventory_group, rql.item_id,rql.line_item, rql.notes, rql.pr_number, rql.quantity, ";
	query+="rql.request_line_status, rql.required_datetime,rql.ship_to_location_id, pr.company_id, pr.end_user, pr.facility_id, ";
	query+="pr.requestor ,i.item_desc,fx_kit_packaging(i.item_id) packaging FROM customer.request_line_item rql, customer.purchase_request pr , item i ";
	query+="WHERE pr.pr_number = rql.pr_number and rql.pr_number in ("+mrnumbers+") and i.item_id=nvl(rql.item_id,rql.example_item_id) ";
	query+="ORDER BY rql.pr_number ";*/

	String query="SELECT rql.application, rql.fac_part_no, rql.inventory_group, rql.item_id,rql.line_item, rql.notes, rql.pr_number, rql.quantity,  ";
	query+="decode(k.QUANTITY_ISSUED,null,rql.request_line_status,decode(k.QUANTITY_DELIVERED,0,'Picked',decode(k.QUANTITY_DELIVERED-k.QUANTITY_ISSUED,0,'Delivered','Partial Del.'))) request_line_status, ";
	query+="rql.required_datetime,rql.ship_to_location_id, pr.company_id, pr.end_user, pr.facility_id,pr.requestor ,i.item_desc,fx_kit_packaging(i.item_id) packaging ";
	query+="FROM customer.request_line_item rql, customer.purchase_request pr , item i ,issue_key k WHERE pr.pr_number = rql.pr_number and pr.pr_number in ("+mrnumbers+") and i.item_id=nvl(rql.item_id,rql.example_item_id) and ";
	query+="rql.pr_number=k.pr_number(+) and rql.line_item=k.line_item(+) and rql.company_id=k.company_id(+) ORDER BY rql.pr_number ";

	dbrs=db.doQuery( query );
	searchRs=dbrs.getResultSet();

	while ( searchRs.next() )
	{
	  boolean createHdr=false;
	  if ( count % 10 == 0 )
	  {
		createHdr=true;
	  }

	  if ( createHdr )
	  {
		out.println( "<tr align=\"center\">\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">MR-Line</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Facility</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Part Num</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Item<BR>(Inven Grp)</TH>\n" );
		out.println( "<TH width=\"25%\"  height=\"38\">Item Desc</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Packaging</TH>\n" );
		out.println( "<TH width=\"2%\"  height=\"38\">Qty</TH>\n" );
		//out.println( "<TH width=\"5%\"  height=\"38\">End User</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Ship To</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Status</TH>\n" );
		out.println( "</tr>\n" );
	  }

	  String application = searchRs.getString("APPLICATION")==null?"&nbsp;":searchRs.getString("APPLICATION");
	  String fac_part_no = searchRs.getString("FAC_PART_NO")==null?"&nbsp;":searchRs.getString("FAC_PART_NO");
	  String inventory_group = searchRs.getString("INVENTORY_GROUP")==null?"&nbsp;":searchRs.getString("INVENTORY_GROUP");
	  String item_id = searchRs.getString("ITEM_ID")==null?"&nbsp;":searchRs.getString("ITEM_ID");
	  String line_item = searchRs.getString("LINE_ITEM")==null?"&nbsp;":searchRs.getString("LINE_ITEM");
	  //String notes = searchRs.getString("NOTES")==null?"&nbsp;":searchRs.getString("NOTES");
	  String pr_number = searchRs.getString("PR_NUMBER")==null?"&nbsp;":searchRs.getString("PR_NUMBER");
	  String quantity = searchRs.getString("QUANTITY")==null?"&nbsp;":searchRs.getString("QUANTITY");
	  String request_line_status = searchRs.getString("REQUEST_LINE_STATUS")==null?"&nbsp;":searchRs.getString("REQUEST_LINE_STATUS");
	  //String required_datetime = searchRs.getString("REQUIRED_DATETIME")==null?"&nbsp;":searchRs.getString("REQUIRED_DATETIME");
	  String ship_to_location_id = searchRs.getString("SHIP_TO_LOCATION_ID")==null?"&nbsp;":searchRs.getString("SHIP_TO_LOCATION_ID");
	  //String company_id = searchRs.getString("COMPANY_ID")==null?"&nbsp;":searchRs.getString("COMPANY_ID");
	  //String end_user = searchRs.getString("END_USER")==null?"&nbsp;":searchRs.getString("END_USER");
	  String facility_id = searchRs.getString("FACILITY_ID")==null?"&nbsp;":searchRs.getString("FACILITY_ID");
	  //String requestor = searchRs.getString("REQUESTOR")==null?"&nbsp;":searchRs.getString("REQUESTOR");
	  String itemdesc = searchRs.getString("ITEM_DESC")==null?"&nbsp;":searchRs.getString("ITEM_DESC");
	  String packaging = searchRs.getString("PACKAGING")==null?"&nbsp;":searchRs.getString("PACKAGING");


	  out.println( "<tr align=\"center\">\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + pr_number +"-"+ line_item+"</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + facility_id + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + application + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + fac_part_no + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + item_id + "<BR>(" + inventory_group + ")</TD>\n" );
	  //out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + notes + "</TD>\n" );
	  out.println( "<TD " + Color + "l\" width=\"25%\" HEIGHT=\"20\">" + itemdesc + "</TD>\n" );
	  out.println( "<TD " + Color + "l\" width=\"5%\" HEIGHT=\"20\">" + packaging + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"2%\" HEIGHT=\"20\">" + quantity + "</TD>\n" );
	  //out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + end_user + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + ship_to_location_id + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + request_line_status + "</TD>\n" );
	  //out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + searchRs.getString("REQUIRED_DATETIME")==null?"":searchRs.getString("REQUIRED_DATETIME") + "</TD>\n" );
	  //out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + searchRs.getString("COMPANY_ID")==null?"":searchRs.getString("COMPANY_ID") + "</TD>\n" );
	  //out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + searchRs.getString("REQUESTOR")==null?"":searchRs.getString("REQUESTOR") + "</TD>\n" );
	  out.println( "</tr>\n" );

	  count++;
	  if ( ( count ) % 2 == 0 )
	  {
		Color="CLASS=\"white";
	  }
	  else
	  {
		Color="CLASS=\"blue";
	  }
	}

  }
  catch ( Exception e )
  {
	e.printStackTrace();
	out.println(radian.web.HTMLHelpObj.printHTMLError());
  }
  finally
  {
	if ( dbrs != null ){dbrs.close();}
  }

  out.println( "</TABLE>\n" );

  out.println( "<BR><BR><TABLE BORDER=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
  out.println( "<TD HEIGHT=20 WIDTH=\"100%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  out.println( "<B>Allocation Results:</B>&nbsp;These are subject to change, check after 30 Mins to see updated values.\n" );
  out.println( "</TD>\n" );
  out.println( "</TR>\n" );
  out.println( "</TABLE>\n" );

  out.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"50%\" CLASS=\"moveup\" ALIGN=\"LEFT\">\n");
  count = 0;

  try
  {
	String query="select vv.DESCRIPTION,h.HUB_NAME,mr.COMPANY_ID, mr.PR_NUMBER, mr.LINE_ITEM, mr.DOC_TYPE, mr.STATUS, mr.QUANTITY, mr.HUB, mr.CATALOG_ID, mr.CAT_PART_NO, mr.INVENTORY_GROUP, mr.ITEM_ID ";
	query+=" from mr_allocation mr, hub h ,vv_allocation_doc_type vv where  mr.hub = h.BRANCH_PLANT and pr_number in ("+mrnumbers+") ";
	query+=" and mr.DOC_TYPE = vv.DOC_TYPE order by pr_number";

	dbrs=db.doQuery( query );
	searchRs=dbrs.getResultSet();

	/*ResultSetMetaData rsMeta1 = searchRs.getMetaData();
   for(int i =1; i<=rsMeta1.getColumnCount(); i++)
   {
	 //System.out.println("out.println( \"<td \" + Color + \" width=\\\"5%\\\" height=\\\"35\\\">\" + searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\") + \"</TD>xxx\" );");
	 System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\");");

   }*/

	while ( searchRs.next() )
	{
	  boolean createHdr=false;
	  if ( count % 10 == 0 )
	  {
		createHdr=true;
	  }

	  if ( createHdr )
	  {
		out.println( "<tr align=\"left\">\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">MR-Line</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Item<BR>(Inven Grp)</TH>\n" );
		out.println( "<TH width=\"2%\"  height=\"38\">Qty</TH>\n" );
		out.println( "<TH width=\"3%\"  height=\"38\">Allocation Type</TH>\n" );
		//out.println( "<TH width=\"5%\"  height=\"38\">Status</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Hub</TH>\n" );
		out.println( "</tr>\n" );
	  }

		//String company_id=searchRs.getString( "COMPANY_ID" ) ==null?"&nbsp;" : searchRs.getString( "COMPANY_ID" );
		String pr_number=searchRs.getString( "PR_NUMBER" ) ==null?"&nbsp;" : searchRs.getString( "PR_NUMBER" );
		String line_item=searchRs.getString( "LINE_ITEM" ) ==null?"&nbsp;" : searchRs.getString( "LINE_ITEM" );
		String doc_type=searchRs.getString( "DOC_TYPE" ) ==null?"&nbsp;" : searchRs.getString( "DOC_TYPE" );
		//String status=searchRs.getString( "STATUS" ) ==null?"&nbsp;" : searchRs.getString( "STATUS" );
		String quantity=searchRs.getString( "QUANTITY" ) ==null?"&nbsp;" : searchRs.getString( "QUANTITY" );
		//String hub=searchRs.getString( "HUB" ) ==null?"&nbsp;" : searchRs.getString( "HUB" );
		//String catalog_id=searchRs.getString( "CATALOG_ID" ) ==null?"&nbsp;" : searchRs.getString( "CATALOG_ID" );
		//String cat_part_no=searchRs.getString( "CAT_PART_NO" ) ==null?"&nbsp;" : searchRs.getString( "CAT_PART_NO" );
		String inventory_group=searchRs.getString( "INVENTORY_GROUP" ) ==null?"&nbsp;" : searchRs.getString( "INVENTORY_GROUP" );
		String item_id=searchRs.getString( "ITEM_ID" ) ==null?"&nbsp;" : searchRs.getString( "ITEM_ID" );
		String hubname=searchRs.getString( "HUB_NAME" ) ==null?"&nbsp;" : searchRs.getString( "HUB_NAME" );
	    String typedesc=searchRs.getString( "DESCRIPTION" ) ==null?"&nbsp;" : searchRs.getString( "DESCRIPTION" );

	  out.println( "<tr align=\"left\">\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + pr_number +"-"+ line_item+"</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + item_id + "<BR>(" + inventory_group + ")</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"2%\" HEIGHT=\"20\">" + quantity + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"3%\" HEIGHT=\"20\">" + typedesc + "</TD>\n" );
	  //out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + status + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + hubname + "</TD>\n" );

	  //out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + searchRs.getString("COMPANY_ID")==null?"":searchRs.getString("COMPANY_ID") + "</TD>\n" );
	  //out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + searchRs.getString("CATALOG_ID")==null?"":searchRs.getString("CATALOG_ID") + "</TD>\n" );
	  //out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + searchRs.getString("CAT_PART_NO")==null?"":searchRs.getString("CAT_PART_NO") + "</TD>\n" );

	  out.println( "</tr>\n" );

	  count++;
	  if ( ( count ) % 2 == 0 )
	  {
		Color="CLASS=\"white";
	  }
	  else
	  {
		Color="CLASS=\"blue";
	  }
	}

  }
  catch ( Exception e )
  {
	e.printStackTrace();
	out.println(radian.web.HTMLHelpObj.printHTMLError());
  }
  finally
  {
	if ( dbrs != null ){dbrs.close();}
  }

  if (count ==0)
  {
	out.println( "<TD>No Allocation Records Were Found.</TD>\n" );
  }
  out.println( "</TABLE>\n" );
  return;
}

//Build HTML Header
private void buildHeader( String mrnumbers)
{
  //StringBuffer Msg=new StringBuffer();
  out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Automated MRs/Allocation Report","cabupconfirm.js",intcmIsApplication ) );
  out.println( "</HEAD>  \n" );
  out.println( "<BODY>\n" );
  out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
  out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
  out.println( "</DIV>\n" );
  out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
  out.println( radian.web.HTMLHelpObj.PrintTitleBar( "Automated MRs/Allocation Report\n" ) );

  out.println( "<FORM METHOD=\"POST\" NAME=\"cabupconfirm\" ACTION=\"" + radian.web.tcmisResourceLoader.getcabupconfirmurl() + "\">\n" );
  out.println( "<TABLE BORDER=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
  out.println( "<TR VALIGN=\"MIDDLE\">\n" );

  out.println( "<TD HEIGHT=30 WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  out.println( "<B>MR Numbers:</B>&nbsp;\n" );
  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"mrslist\" value=\"" + mrnumbers + "\" size=\"30\">\n" );
  out.println( "</TD>\n" );

  out.println( "<TD HEIGHT=30 WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
  out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick=\"return actionValue(name,this)\" NAME=\"Search\">\n" );
  out.println( "</TD>\n" );
  out.println( "</TR>\n" );

  out.println( "<TR VALIGN=\"MIDDLE\" ALIGN=\"LEFT\">\n" );
  out.println( "<TD HEIGHT=10 WIDTH=\"30%\" ALIGN=\"LEFT\" CLASS=\"announce\" COLSPAN=\"2\">\n" );
  out.println( "You can enter multiple MR Numbers seperated by commas.\n" );
  out.println( "</TD>\n" );
  out.println( "</TR>\n" );

  out.println( "</TABLE>\n" );
  out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n" );
  out.println( "</FORM>\n" );

  return;
}

}
