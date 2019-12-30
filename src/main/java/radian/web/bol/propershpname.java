package radian.web.bol;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import javax.servlet.http.HttpSession;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      Haas TCM
 * @author       Nawaz Shaik
 */

public class propershpname
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
	private boolean intcmIsApplication = false;

  public propershpname( ServerResourceBundle b,TcmISDBModel d )
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
	PrintWriter out=response.getWriter();
	response.setContentType( "text/html" );
	HttpSession session = request.getSession();
	PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
	Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
	intcmIsApplication = false;
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

	String CompNum=request.getParameter( "CompNum" );
	if ( CompNum == null )
	{
	  CompNum="0";
	}

	String item_id=request.getParameter( "item_id" );
	if ( item_id == null )
	  item_id="";
	item_id=item_id.trim();

	String hazmat_id=request.getParameter( "hazmat_id" );
	if ( hazmat_id == null )
	  hazmat_id="";
	hazmat_id=hazmat_id.trim();

	if ( "Search".equalsIgnoreCase( Action ) )
	{
	  searchshipnames( "Search",searchString,CompNum,out );
	}
	else if ( "findpropershpname".equalsIgnoreCase( Action ) )
	{
	  buildsenddotName( item_id,out );
	}
	else if ( "showmultiplenames".equalsIgnoreCase( Action ) )
	{
	  buildmultiplenames( hazmat_id,out,CompNum );
	}
	else
	{
	  searchshipnames( "",searchString,CompNum,out );
	}
	out.close();
  }

  private void buildmultiplenames( String hazmat_id,PrintWriter dotout,String control_name )
  {

	String query="select distinct PROPER_SHIPPING_NAME from CFR_49_proper_shipping_name where hazmat_id = '"+hazmat_id+"' ";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Hashtable result=null;
	Vector ResultV=new Vector();
	int count = 0;
	String propshipname = "";

	try
	{
	  dbrs=db.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		result=new Hashtable();
		String facn= ( rs.getString( "PROPER_SHIPPING_NAME" ) == null ? "" : rs.getString( "PROPER_SHIPPING_NAME" ) );
		String faci= ( rs.getString( "PROPER_SHIPPING_NAME" ) == null ? "" : rs.getString( "PROPER_SHIPPING_NAME" ) );
		result.put( faci,facn );
		ResultV.addElement( result );
		propshipname = faci;
		count++;
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

   if (count == 1)
   {
	 dotout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Send Proper Shipping Name","",intcmIsApplication ) );
	 dotout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
	 dotout.println( "<!--\n" );
	 dotout.println( "function sendSupplierData()\n" );
	 dotout.println( "{\n" );
	 dotout.println( "opnshpname = opener.document.getElementById(\"ground_shipping_name"+control_name+"\");\n" );
	 dotout.println( "opnshpname.value = \"" + propshipname + "\";\n" );
	 dotout.println( "window.close();\n" );
	 dotout.println( " }\n" );
	 dotout.println( "// -->\n</SCRIPT>\n" );
	 dotout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	 dotout.println( "<CENTER><BR><BR>\n" );
	 dotout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMousedotout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	 dotout.println( "</FORM></BODY></HTML>\n" );
   }
   else
   {
	 dotout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Proper Shipping Name","dotshipname.js",intcmIsApplication ) );
	 dotout.println( "</HEAD>  \n" );
	 dotout.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>Please Select Proper Shipping Name</B>\n" ) );
	 dotout.println( "<FORM method=\"POST\" NAME=\"selpropershipingname\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"\">\n" );
	 dotout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	 dotout.println( "<TR VALIGN=\"MIDDLE\">\n" );
	 dotout.println( "<TD HEIGHT=45 WIDTH=\"15%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	 dotout.println( "<B>Proper Shipping Name:</B>&nbsp;\n" );
	 dotout.println( "</TD>\n" );
	 dotout.println( "<TD HEIGHT=45 WIDTH=\"20%\" CLASS=\"announce\">\n" );
	 dotout.println( "<SELECT CLASS=\"HEADER\"  NAME=\"propershipingname\" size=\"1\">\n" );
	 dotout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	 dotout.println( radian.web.HTMLHelpObj.getDropDown( ResultV,"" ) );
	 dotout.println( "</SELECT>\n" );
	 dotout.println( "</TD>\n" );
	 dotout.println( "</TR>" );
	 dotout.println( "</TABLE>\n" );
	 dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"CompNum\" ID=\"CompNum\" VALUE=\"" + control_name + "\">\n" );
	 dotout.println( "<BR>\n" );
	 dotout.println( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Ok\" OnClick=\"sendpropshpnamevalue()\" NAME=\"sendterv\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
	 dotout.println( "<INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Cancel\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n" );
	 dotout.println( "</FORM></BODY></HTML>\n" );
   }
	return;
  }

  public void buildsenddotName( String item_id,PrintWriter dotout )
  {
	dotout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Send Proper Shipping Name","",intcmIsApplication ) );
	dotout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
	dotout.println( "<!--\n" );
	dotout.println( "function sendSupplierData()\n" );
	dotout.println( "{\n" );

	DBResultSet dbrs=null;
	ResultSet searchRs=null;
	String dotname= "";

	try
	  {
		if ( item_id.trim().length() > 0 )
		{
		  String query=" select DOT from part_dot_view where item_id = " + item_id + " order by HAZARDOUS";

		  dbrs=db.doQuery( query );
		  searchRs=dbrs.getResultSet();
		  int count=0;

		  while ( searchRs.next() )
		  {
			//String ITEM_ID = searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID"));
			//String tmpPACKAGING=searchRs.getString( "PACKAGING" ) == null ? "" : searchRs.getString( "PACKAGING" );
			//String tmpHAZARDOUS=searchRs.getString( "HAZARDOUS" ) == null ? "" : searchRs.getString( "HAZARDOUS" );
			String DOT=searchRs.getString( "DOT" ) == null ? "" : searchRs.getString( "DOT" );
		   //String ERG=searchRs.getString( "ERG" ) == null ? "" : searchRs.getString( "ERG" ) );
		   //String MATERIAL_DESC=searchRs.getString( "MATERIAL_DESC" ) == null ? "" : searchRs.getString( "MATERIAL_DESC" ) );
		   //String ITEM_DESC=searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ) );
		   //String tmpnetweightlb = searchRs.getString( "NET_WEIGHT_LB" ) == null ? "" : searchRs.getString( "NET_WEIGHT_LB" );
		   //String ORM_D=searchRs.getString( "ORM_D" ) == null ? "" : searchRs.getString( "ORM_D" ) );
		   //String BULK_PKG_MARINE_POLLUTANT=searchRs.getString( "BULK_PKG_MARINE_POLLUTANT" ) == null ? "" : searchRs.getString( "BULK_PKG_MARINE_POLLUTANT" ) );

			String tempdotname=DOT;

			if ( count > 0 )
			{
			  dotname+="<BR>" + tempdotname;
			}
			else
			{
			  dotname=tempdotname;
			}

			count++;
		  }
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

	dotout.println( "finalshipnamecell = opener.document.getElementById(\"finalshipname\");\n" );
	dotout.println( "finalshipnamecell.innerHTML = \"" + dotname + "\";\n" );

	dotout.println( "window.close();\n" );
	dotout.println( " }\n" );
	dotout.println( "// -->\n</SCRIPT>\n" );
	//dotout.println( Msgtemp );
	dotout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	dotout.println( "<CENTER><BR><BR>\n" );
	dotout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMousedotout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	dotout.println( "</FORM></BODY></HTML>\n" );
	//out.println( Msgbody );

	return;
  }

  public void searchshipnames( String SearchFlag,String SearchString,String control_name,PrintWriter dotout )
  {
	//StringBuffer Msgl=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet searchRs=null;
	String branchPlant="";

	String nematid_Servlet= radian.web.tcmisResourceLoader.getnewtapurl();
	dotout.println( radian.web.HTMLHelpObj.printHTMLHeader( "DOT Shipping Names","dotshipname.js",intcmIsApplication ) );

	dotout.println( "<BODY>\n" );
	dotout.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
	dotout.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
	dotout.println("</DIV>\n");
	dotout.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

	dotout.println( "<FORM method=\"POST\" NAME=\"pshipnames\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+radian.web.tcmisResourceLoader.getpropshipnamesurl()+"\">\n" );
	dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"CompNum\" ID=\"CompNum\" VALUE=\"" + control_name + "\">\n" );
	dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"hazardclass\" VALUE=\"\">\n" );
	dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"intdnumber\" VALUE=\"\">\n" );
	dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"pkggroup\" VALUE=\"\">\n" );
	dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"hazmat_id\" VALUE=\"\">\n" );
	dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"shpnamecount\" VALUE=\"\">\n" );
	dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"symbol\" VALUE=\"\">\n" );
	dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" ID=\"Action\" VALUE=\"\">\n" );
	dotout.println( "<TABLE BORDER=\"0\" width=\"680\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	dotout.println( "<TR>\n" );
	dotout.println( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text:</B></TD>\n" );
	if ( "Search".equalsIgnoreCase( SearchFlag ) )
	{
	  dotout.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + SearchString + "\" SIZE=\"40\">\n" );
	}
	else
	{
	  dotout.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"\" SIZE=\"40\">\n" );
	}
	dotout.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return namesearch(this)\" NAME=\"SearchButton\">&nbsp;</TD>\n");

	dotout.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Description:</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"shpname\" value=\"\" SIZE=\"75\" readonly>\n" );
	dotout.println( "</TR>\n" );
	dotout.println( "</TABLE><TABLE BORDER=\"0\" width=\"662\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	dotout.println( "<TR>\n" );
	dotout.println( "<TH WIDTH=\"432\"><B>Description</B></FONT></TH>\n" );
	dotout.println( "<TH WIDTH=\"84\"><B>Hazard Class</B></FONT></TH>\n" );
	dotout.println( "<TH WIDTH=\"77\"><B>Identification Number</B></FONT></TH>\n" );
	dotout.println( "<TH WIDTH=\"69\"><B>Packing Group</B></FONT></TH>\n" );
	dotout.println( "</TR></TABLE>\n" );

	//open scrolling table
	dotout.println( "<TABLE CLASS=\"newmoveup\" WIDTH=\"680\">\n" );
	dotout.println( "<TBODY>\n" );
	dotout.println( "<TR>\n" );
	dotout.println( "<TD VALIGN=\"TOP\">\n" );
	dotout.println( "<DIV ID=\"orderdetail\" CLASS=\"newscroll_column350\">\n" );
	//Write code to insert rows here
	dotout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"newmoveup\">\n" );

	int totalrecords=0;
	int count=0;

	String query="";

	if ( "Search".equalsIgnoreCase( SearchFlag ) )
	{
	  SearchString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
	  String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"HAZARDOUS_MATERIAL_DESCRIPTION" );
	  query+="select SHIPPING_NAME_COUNT, SYMBOL, HAZARDOUS_MATERIAL_DESCRIPTION, HAZARD_CLASS, IDENTIFICATION_NUMBER, PACKING_GROUP, HAZMAT_ID, PROPER_SHIPPING_NAME, PICKABLE from CFR_49_HAZARDOUS_MATERIAL_VIEW  x where "+wherese+" order by HAZMAT_ID asc";
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

		  String symbol=searchRs.getString( "SYMBOL" ) == null ? "" : searchRs.getString( "SYMBOL" );
		  String shippinname=searchRs.getString( "HAZARDOUS_MATERIAL_DESCRIPTION" ) == null ? "" : searchRs.getString( "HAZARDOUS_MATERIAL_DESCRIPTION" );
		  String hazard_class=searchRs.getString( "HAZARD_CLASS" ) == null ? "" : searchRs.getString( "HAZARD_CLASS" );
		  String ident_number=searchRs.getString( "IDENTIFICATION_NUMBER" ) == null ? "" : searchRs.getString( "IDENTIFICATION_NUMBER" );
		  String packing_group=searchRs.getString( "PACKING_GROUP" ) == null ? "" : searchRs.getString( "PACKING_GROUP" );
		  //String label_code=searchRs.getString( "LABEL_CODE" ) == null ? "" : searchRs.getString( "LABEL_CODE" );
		  //String special_provision=searchRs.getString( "SPECIAL_PROVISION" ) == null ? "" : searchRs.getString( "SPECIAL_PROVISION" );
		  //String packaging_exception=searchRs.getString( "PACKAGING_EXCEPTION" ) == null ? "" : searchRs.getString( "PACKAGING_EXCEPTION" );
		  //String packaging_nonbulk=searchRs.getString( "PACKAGING_NONBULK" ) == null ? "" : searchRs.getString( "PACKAGING_NONBULK" );
		  //String packaging_bulk=searchRs.getString( "PACKAGING_BULK" ) == null ? "" : searchRs.getString( "PACKAGING_BULK" );
		  //String quantity_limitation_passenger=searchRs.getString( "QUANTITY_LIMITATION_PASSENGER" ) == null ? "" :searchRs.getString( "QUANTITY_LIMITATION_PASSENGER" );
		  //String quantity_limitation_cargo=searchRs.getString( "QUANTITY_LIMITATION_CARGO" ) == null ? "" :searchRs.getString( "QUANTITY_LIMITATION_CARGO" );
		  //String vessel_stowage_location=searchRs.getString( "VESSEL_STOWAGE_LOCATION" ) == null ? "" : searchRs.getString( "VESSEL_STOWAGE_LOCATION" );
		  //String vessel_stowage_other=searchRs.getString( "VESSEL_STOWAGE_OTHER" ) == null ? "" : searchRs.getString( "VESSEL_STOWAGE_OTHER" );
		  String hazmat_id=searchRs.getString( "HAZMAT_ID" ) == null ? "" : searchRs.getString( "HAZMAT_ID" );
		  String shpnamecount=searchRs.getString( "SHIPPING_NAME_COUNT" ) == null ? "" : searchRs.getString( "SHIPPING_NAME_COUNT" );

		  String Color=" ";
		  if ( count % 2 == 0 )
		  {
			Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }
		  else
		  {
			Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }

		  if ("Forbidden".equalsIgnoreCase(hazard_class))
		  {
			  Color="BGCOLOR=\"#FF9999\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }

		  dotout.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addshipingname('" + HelpObjs.addescapesForJavascript(shippinname) + "','" + hazard_class + "','" + ident_number + "','" + packing_group + "','"+hazmat_id+"','"+shpnamecount+"','"+symbol+"')\" BORDERCOLOR=\"black\">\n" );

		  if (!(hazard_class.trim().length() > 0))
		  {
			shippinname = "<I>"+shippinname+"</I>";
		  }

		  dotout.println( "<TD " + Color + " WIDTH=\"450\" ALIGN=\"LEFT\">" );
		  dotout.println( shippinname );
		  dotout.println( "</TD>\n" );
		  dotout.println( "<TD " + Color + " WIDTH=\"82\" ALIGN=\"LEFT\">" );
		  dotout.println( hazard_class );
		  dotout.println( "</TD>\n" );
		  dotout.println( "<TD " + Color + " WIDTH=\"77\" ALIGN=\"LEFT\">" );
		  dotout.println( ident_number );
		  dotout.println( "</TD>\n" );
		  dotout.println( "<TD " + Color + " WIDTH=\"72\" ALIGN=\"LEFT\">" );
		  dotout.println( packing_group );
		  dotout.println( "</TD>\n" );
		  dotout.println( "</TR>\n" );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		dotout.println( "An Error Occured While Querying the Database" );
		dotout.println( "</BODY>\n</HTML>\n" );
		return ;
	  }
	  finally
	  {
		if (dbrs!= null) {dbrs.close();}
	  }

	if ( totalrecords == 0 )
	{
	  dotout.println( "<TR><TD>No Records Found</TD></TR>\n" );
	}
  }
	dotout.println( "</TABLE>\n" );

	//close scrolling table
	dotout.println( "</DIV>\n" );
	dotout.println( "</TD>\n" );
	dotout.println( "</TR>\n" );
	dotout.println( "</TBODY>\n" );
	dotout.println( "</TABLE>\n" );
	dotout.println( "<CENTER>\n<BR><BR>" );
	try
	{
	  if ( this.getupdateStatus() )
	  {
		dotout.println( "<INPUT TYPE=\"button\" VALUE=\"Ok\" onclick= \"sendshipingname()\" NAME=\"UpdateButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
	  }
	  else
	  {
		dotout.println( "&nbsp;\n" );
	  }
	}
	catch ( Exception ex )
	{
	}
	dotout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	dotout.println("<BR><A HREF=\"http://hazmat.dot.gov\" TARGET=\"sfffd\">DOT HAZMAT Safety Homepage</A>\n");
	dotout.println( "</FORM></BODY></HTML>\n" );

	return;
  }
}
