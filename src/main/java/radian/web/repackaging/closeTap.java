package radian.web.repackaging;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      Haas TCM
 * @author       Nawaz Shaik
 */

public class closeTap
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  PrintWriter out = null;
  private boolean intcmIsApplication = false;
  private ResourceLibrary res = null; // res means resource.

  public closeTap( ServerResourceBundle b,TcmISDBModel d )
  {
	bundle=b;
	db=d;
  }

  private boolean allowupdate;
  public void setupdateStatus(boolean id)
  {
	this.allowupdate=id;
  }

  private boolean getupdateStatus()
	 throws Exception
  {
	return this.allowupdate;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response )
	 throws ServletException,IOException
  {
	out=response.getWriter();
	response.setContentType( "text/html" );

	HttpSession session = request.getSession();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));
	PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
	Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
	if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
	{
	 intcmIsApplication = true;
	}

	String Action=request.getParameter( "Action" );
	if ( Action == null )
	  Action="";
	Action=Action.trim();

	String searchString=request.getParameter( "SearchString" );
	if ( searchString == null )
	  searchString="";
	searchString=searchString.trim();

	String invengrp=request.getParameter( "invengrp" );
	if ( invengrp == null )
	  invengrp="";
	invengrp=invengrp.trim();

	String branch_plant=request.getParameter( "hubnum" );
	  if ( branch_plant == null )
		branch_plant="";
	  branch_plant=branch_plant.trim();

	String closetapid=request.getParameter( "closetapid" );
	  if ( closetapid == null )
		closetapid="";
	  closetapid=closetapid.trim();

	if ( "UPDATE".equalsIgnoreCase( Action ) )
	{
		out.println( buildclosetap(closetapid) );
	}
	else if ( "closetap".equalsIgnoreCase( Action ) )
	{
	  showClosetap( closetapid,invengrp,branch_plant);
	}
	else if ( "Search".equalsIgnoreCase( Action ) )
	{
	  searchopentaps( "Search",searchString,invengrp,branch_plant);
	}
	else
	{
	  searchopentaps( "",searchString,invengrp,branch_plant);
	}
	out.close();
  }

  private String buildclosetap(String receipt_id)
  {
	StringBuffer Msg=new StringBuffer();
	CallableStatement cs=null;
	boolean closeresult = false;

	try
	{
	  Connection connect1=db.getConnection();
	  cs=connect1.prepareCall( "{call P_tap_close(?,?,?)}" );
	  cs.setInt( 1,Integer.parseInt( receipt_id ) );
	  cs.registerOutParameter(2,java.sql.Types.NUMERIC);
	  cs.registerOutParameter(3,java.sql.Types.VARCHAR);
	  cs.execute();

	  String errorcode=cs.getString( 3 );
	  if ( errorcode == null )
	  {
		closeresult=true;
	  }
	  else
	  {
		closeresult=false;
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  closeresult = false;
	  radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure P_tap_close in Close Tap Screen","Error occured while running P_tap_close\n" + e.getMessage() +"\nFor Input Parameters Receipt Id: " + receipt_id + "" );
	}
	finally
	{
	  if ( cs != null )
	  {
		try
		{
		  cs.close();
		}
		catch ( Exception se )
		{
		  se.printStackTrace();
		}
	  }
	}

	  Msg.append( radian.web.HTMLHelpObj.printHTMLHeader( "label.closetap","closetap.js",intcmIsApplication,res ) );
	  Msg.append( "</HEAD>  \n" );
	  Msg.append( "<BODY>  \n" );
	  Msg.append( "<FORM method=\"POST\" NAME=\"closetap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"\">\n" );
	  Msg.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  Msg.append( "<TR VALIGN=\"MIDDLE\">\n" );
	  Msg.append( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );
	  if (closeresult)
	  {
		Msg.append( res.getString("msg.tabclosesucc")+ "&nbsp;<B>"+ res.getString("label.tapid")+":</B>&nbsp;"+receipt_id +"\n<BR><BR>"+res.getString("label.refreshmainpage") );
	  }
	  else
	  {
		Msg.append( res.getString("msg.errorclosetap")+" <B>"+res.getString("label.tapid")+":</B>&nbsp;"+receipt_id+"\n<BR><BR>"+res.getString("msg.errcontacttech") );
	  }
	  Msg.append( "</TD>\n" );
	  Msg.append( "</TR>" );
	  Msg.append( "</TABLE>\n" );
	  Msg.append( "<BR>\n" );
	  Msg.append( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\""+res.getString("label.ok")+"\" OnClick=\"submitmainpage()\" NAME=\"sendterv\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
	  Msg.append( "</CENTER>\n" );
	  Msg.append( "</BODY>  \n" );
	  Msg.append( "</HTML>\n" );

	return Msg.toString();
  }

  public void showClosetap( String closereceiptId,String invengrp,String branchplant )
  {
	//StringBuffer Msgl=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet searchRs=null;
	String branchPlant="";

	String nematid_Servlet= radian.web.tcmisResourceLoader.getnewtapurl();
	out.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.closetap","closetap.js",intcmIsApplication,res ) );

	out.println( "<BODY onload=\"javascript:window.resizeTo(670,450)\">\n" );
	out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
	out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT></center>\n");
	out.println("</DIV>\n");
	out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

	out.println( "<FORM method=\"POST\" NAME=\"closetap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+radian.web.tcmisResourceLoader.getclosetapurl()+"\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"invengrp\" ID=\"invengrp\" VALUE=\"" + invengrp + "\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"hubnum\" ID=\"hubnum\" VALUE=\"" + branchplant + "\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" ID=\"Action\" VALUE=\"\">\n" );
	out.println( "<TABLE BORDER=\"0\" width=\"603\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	out.println( "<TR>\n" );
	out.println( "<TD WIDTH=\"20%\" CLASS=\"announce\" COLSPAN=\"2\" ALIGN=\"CENTER\"><B>"+res.getString("msg.askclosetap")+":</B></TD>\n" );

	out.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>"+res.getString("label.tapid")+":</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"closetapid\" value=\""+closereceiptId+"\" SIZE=\"25\" readonly>\n" );
	out.println( "</TR>\n" );
	out.println( "</TABLE><TABLE BORDER=\"0\" width=\"603\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	out.println( "<TR>\n" );
	out.println( "<TH WIDTH=\"68\"><B>"+res.getString("label.item")+"</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"75\"><B>"+res.getString("label.packaging")+"</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"337\"><B>"+res.getString("label.description")+"</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"60\"><B>"+res.getString("label.tapid")+"</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"63\"><B>"+res.getString("label.qtyremaining")+"</B></FONT></TH>\n" );
	out.println( "</TR></TABLE>\n" );

	//open scrolling table
	out.println( "<TABLE CLASS=\"newmoveup\" WIDTH=\"619\">\n" );
	out.println( "<TBODY>\n" );
	out.println( "<TR>\n" );
	out.println( "<TD VALIGN=\"TOP\">\n" );
	out.println( "<DIV ID=\"orderdetail\" CLASS=\"newscroll_column350\">\n" );
	//Write code to insert rows here
	out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"newmoveup\">\n" );

	int totalrecords=0;
	int count=0;

	String query="";
	query+="select x.*, round( (x.QUANTITY_RECEIVED-x.QUANTITY_ISSUED),2) QUANTITY_REMAINING from tapped_inventory_view x where RECEIPT_ID = "+closereceiptId+" and HUB='"+branchplant+"' and INVENTORY_GROUP='"+invengrp+"' order by ITEM_CONVERSION_ISSUE_ID asc";

	try
	{
	  dbrs=db.doQuery( query );
	  searchRs=dbrs.getResultSet();

	  while ( searchRs.next() )
	  {
		totalrecords+=1;
		count+=1;

		String hub = searchRs.getString("HUB")==null?"":searchRs.getString("HUB");
		String inventory_group = searchRs.getString("INVENTORY_GROUP")==null?"":searchRs.getString("INVENTORY_GROUP");
		String item_id = searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID");
		String item_conversion_issue_id = searchRs.getString("ITEM_CONVERSION_ISSUE_ID")==null?"":searchRs.getString("ITEM_CONVERSION_ISSUE_ID");
		String receipt_id = searchRs.getString("RECEIPT_ID")==null?"":searchRs.getString("RECEIPT_ID");
		String quantity_received = searchRs.getString("QUANTITY_RECEIVED")==null?"":searchRs.getString("QUANTITY_RECEIVED");
		String quantity_issued = searchRs.getString("QUANTITY_ISSUED")==null?"":searchRs.getString("QUANTITY_ISSUED");
		String search_text = searchRs.getString("SEARCH_TEXT")==null?"":searchRs.getString("SEARCH_TEXT");
		String qtyremaining = searchRs.getString("QUANTITY_REMAINING")==null?"":searchRs.getString("QUANTITY_REMAINING");
		String item_pkg = searchRs.getString("ITEM_PKG")==null?"":searchRs.getString("ITEM_PKG");
		String item_desc = searchRs.getString("ITEM_DESC")==null?"":searchRs.getString("ITEM_DESC");

		String Color=" ";
		if ( count % 2 == 0 )
		{
		  Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		}
		else
		{
		  Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		}

		out.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addtapid('" + receipt_id + "')\" BORDERCOLOR=\"black\">\n" );
		out.println( "<TD " + Color + " WIDTH=\"66\" ALIGN=\"LEFT\">" );
		out.println( item_id );
		out.println( "</TD>\n" );
		out.println( "<TD " + Color + " WIDTH=\"72\" ALIGN=\"LEFT\">" );
		out.println( item_pkg );
		out.println( "</TD>\n" );
		out.println( "<TD " + Color + " WIDTH=\"342\" ALIGN=\"LEFT\">" );
		out.println( item_desc );
		out.println( "</TD>\n" );
		out.println( "<TD " + Color + " WIDTH=\"57\" ALIGN=\"LEFT\">" );
		out.println( receipt_id );
		out.println( "</TD>\n" );
		out.println( "<TD " + Color + " WIDTH=\"62\" ALIGN=\"LEFT\">" );
		out.println( qtyremaining );
		out.println( "</TD>\n" );

		out.println( "</TR>\n" );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  out.println( res.getString("err.querydatabase") );
	  out.println( "</BODY>\n</HTML>\n" );
	  //return Msgl;
	}
	finally
	{
	  if (dbrs!= null) {dbrs.close();}
	}

	if ( totalrecords == 0 )
	{
	  out.println( "<TR><TD>"+res.getString("label.norecordfound")+"</TD></TR>\n" );
	}

	out.println( "</TABLE>\n" );

	//close scrolling table
	out.println( "</DIV>\n" );
	out.println( "</TD>\n" );
	out.println( "</TR>\n" );
	out.println( "</TBODY>\n" );
	out.println( "</TABLE>\n" );
	out.println( "<CENTER>\n<BR><BR>" );
	try
	{
	  if ( this.getupdateStatus() )
	  {
		out.println( "<INPUT TYPE=\"submit\" VALUE=\""+res.getString("label.closetap")+"\" onclick= \"return closeupdate(this)\" NAME=\"UpdateButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
	  }
	  else
	  {
		out.println( "&nbsp;\n" );
	  }
	}
	catch ( Exception ex )
	{
	}
	out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\""+res.getString("label.cancel")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	out.println( "</FORM></BODY></HTML>\n" );

	return;
  }

  public void searchopentaps( String SearchFlag,String SearchString,String invengrp,String branchplant )
  {
	//StringBuffer Msgl=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet searchRs=null;
	String branchPlant="";

	String nematid_Servlet= radian.web.tcmisResourceLoader.getnewtapurl();
	out.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.closetap","closetap.js",intcmIsApplication,res ) );

	out.println( "<BODY onload=\"javascript:window.resizeTo(670,450)\">\n" );
	out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
	out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT></center>\n");
	out.println("</DIV>\n");
	out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

	out.println( "<FORM method=\"POST\" NAME=\"closetap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+radian.web.tcmisResourceLoader.getclosetapurl()+"\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"invengrp\" ID=\"invengrp\" VALUE=\"" + invengrp + "\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"hubnum\" ID=\"hubnum\" VALUE=\"" + branchplant + "\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" ID=\"Action\" VALUE=\"\">\n" );
	out.println( "<TABLE BORDER=\"0\" width=\"603\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	out.println( "<TR>\n" );
	out.println( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>"+res.getString("label.searchtext")+":</B></TD>\n" );
	if ( "Search".equalsIgnoreCase( SearchFlag ) )
	{
	  out.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + SearchString + "\" SIZE=\"40\">\n" );
	}
	else
	{
	  out.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"\" SIZE=\"40\">\n" );
	}
	out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.search")+"\" onclick= \"return closetapsearch(this)\" NAME=\"SearchButton\">&nbsp;</TD>\n");

	out.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>"+res.getString("label.tapid")+":</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"closetapid\" value=\"\" SIZE=\"25\" readonly>\n" );
	out.println( "</TR>\n" );
	out.println( "</TABLE><TABLE BORDER=\"0\" width=\"603\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	out.println( "<TR>\n" );
	out.println( "<TH WIDTH=\"68\"><B>"+res.getString("label.item")+"</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"75\"><B>"+res.getString("label.packaging")+"</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"337\"><B>"+res.getString("label.description")+"</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"60\"><B>"+res.getString("label.tapid")+"</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"63\"><B>"+res.getString("label.qtyremaining")+"</B></FONT></TH>\n" );
	out.println( "</TR></TABLE>\n" );

	//open scrolling table
	out.println( "<TABLE CLASS=\"newmoveup\" WIDTH=\"619\">\n" );
	out.println( "<TBODY>\n" );
	out.println( "<TR>\n" );
	out.println( "<TD VALIGN=\"TOP\">\n" );
	out.println( "<DIV ID=\"orderdetail\" CLASS=\"newscroll_column350\">\n" );
	//Write code to insert rows here
	out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"newmoveup\">\n" );

	int totalrecords=0;
	int count=0;

	String query="";

	if ( "Search".equalsIgnoreCase( SearchFlag ) )
	{
	  SearchString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
	  String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"SEARCH_TEXT" );
	  query+="select x.*, round( (x.QUANTITY_RECEIVED-x.QUANTITY_ISSUED),2) QUANTITY_REMAINING from tapped_inventory_view x where "+wherese+" and HUB='"+branchplant+"' and INVENTORY_GROUP='"+invengrp+"' order by ITEM_CONVERSION_ISSUE_ID asc";
	}
	else
	{

	}

	if (  "Search".equalsIgnoreCase( SearchFlag ) )
	{
	  try
	  {
		dbrs=db.doQuery( query );
		searchRs=dbrs.getResultSet();

		while ( searchRs.next() )
		{
		  totalrecords+=1;
		  count+=1;

		  String hub = searchRs.getString("HUB")==null?"":searchRs.getString("HUB");
		  String inventory_group = searchRs.getString("INVENTORY_GROUP")==null?"":searchRs.getString("INVENTORY_GROUP");
		  String item_id = searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID");
		  String item_conversion_issue_id = searchRs.getString("ITEM_CONVERSION_ISSUE_ID")==null?"":searchRs.getString("ITEM_CONVERSION_ISSUE_ID");
		  String receipt_id = searchRs.getString("RECEIPT_ID")==null?"":searchRs.getString("RECEIPT_ID");
		  String quantity_received = searchRs.getString("QUANTITY_RECEIVED")==null?"":searchRs.getString("QUANTITY_RECEIVED");
		  String quantity_issued = searchRs.getString("QUANTITY_ISSUED")==null?"":searchRs.getString("QUANTITY_ISSUED");
		  String search_text = searchRs.getString("SEARCH_TEXT")==null?"":searchRs.getString("SEARCH_TEXT");
			String qtyremaining = searchRs.getString("QUANTITY_REMAINING")==null?"":searchRs.getString("QUANTITY_REMAINING");
		  String item_pkg = searchRs.getString("ITEM_PKG")==null?"":searchRs.getString("ITEM_PKG");
		  String item_desc = searchRs.getString("ITEM_DESC")==null?"":searchRs.getString("ITEM_DESC");

		  String Color=" ";
		  if ( count % 2 == 0 )
		  {
			Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }
		  else
		  {
			Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }

		  out.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addtapid('" + receipt_id + "')\" BORDERCOLOR=\"black\">\n" );
		  out.println( "<TD " + Color + " WIDTH=\"66\" ALIGN=\"LEFT\">" );
		  out.println( item_id );
		  out.println( "</TD>\n" );
		  out.println( "<TD " + Color + " WIDTH=\"72\" ALIGN=\"LEFT\">" );
		  out.println( item_pkg );
		  out.println( "</TD>\n" );
		  out.println( "<TD " + Color + " WIDTH=\"342\" ALIGN=\"LEFT\">" );
		  out.println( item_desc );
		  out.println( "</TD>\n" );
		  out.println( "<TD " + Color + " WIDTH=\"57\" ALIGN=\"LEFT\">" );
		  out.println( receipt_id );
		  out.println( "</TD>\n" );
		  out.println( "<TD " + Color + " WIDTH=\"62\" ALIGN=\"LEFT\">" );
		  out.println( qtyremaining );
		  out.println( "</TD>\n" );

		  out.println( "</TR>\n" );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		out.println( res.getString("err.querydatabase") );
		out.println( "</BODY>\n</HTML>\n" );
		//return Msgl;
	  }
	  finally
	  {
		if (dbrs!= null) {dbrs.close();}
	  }

	if ( totalrecords == 0 )
	{
	  out.println( "<TR><TD>"+res.getString("label.norecordfound")+"</TD></TR>\n" );
	}
  }
	out.println( "</TABLE>\n" );

	//close scrolling table
	out.println( "</DIV>\n" );
	out.println( "</TD>\n" );
	out.println( "</TR>\n" );
	out.println( "</TBODY>\n" );
	out.println( "</TABLE>\n" );
	out.println( "<CENTER>\n<BR><BR>" );
	try
	{
	  if ( this.getupdateStatus() )
	  {
		out.println( "<INPUT TYPE=\"submit\" VALUE=\""+res.getString("label.closeselectedtap")+"\" onclick= \"return closeupdate(this)\" NAME=\"UpdateButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
	  }
	  else
	  {
		out.println( "&nbsp;\n" );
	  }
	}
	catch ( Exception ex )
	{
	}
	out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\""+res.getString("label.cancel")+"\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	out.println( "</FORM></BODY></HTML>\n" );

	return;
  }

}
