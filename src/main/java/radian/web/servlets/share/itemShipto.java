package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.Collection;
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
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * show location_desc and address line 1,2,3 and City, State, Zip
 */

public class itemShipto {
 private ServerResourceBundle bundle = null;
 private TcmISDBModel db = null;
 private boolean intcmIsApplication = false;

 public itemShipto(ServerResourceBundle b, TcmISDBModel d) {
	bundle = b;
	db = d;
 }

 public void doResult(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	response.setContentType("text/html");
	HttpSession itinvsession = request.getSession();

        PersonnelBean personnelBean = (PersonnelBean) itinvsession.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

	Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection) itinvsession.
	 getAttribute("hubInventoryGroupOvBeanCollection"));

	String Action = request.getParameter("Action");
	if (Action == null)
	 Action = "";
	Action = Action.trim();

	String searchString = request.getParameter("SearchString");
	if (searchString == null)
	 searchString = "";
	searchString = searchString.trim();

	String plantid = request.getParameter("plantid");
	if (plantid == null)
	 plantid = "";
	plantid = plantid.trim();

	String buildingid = request.getParameter("buildingid");
	if (buildingid == null)
	 buildingid = "";
	buildingid = buildingid.trim();

	String storagearea = request.getParameter("storagearea");
	if (storagearea == null)
	 storagearea = "";
	storagearea = storagearea.trim();

	String invengrp = request.getParameter("invengrp");
	if (invengrp == null)
	 invengrp = "";
	invengrp = invengrp.trim();

	String HubName = request.getParameter("HubName");
	if (HubName == null)
	 HubName = "";
	HubName = HubName.trim();

	String itemid = request.getParameter("itemid");
	if (itemid == null)
	 itemid = "";
	itemid = itemid.trim();

	String searchlike = request.getParameter("searchlike");
	if (searchlike == null)
	 searchlike = "";
	searchlike = searchlike.trim();

    String sourceHubName = request.getParameter("sourceHubName");
	if (sourceHubName == null)
	 sourceHubName = "";
	sourceHubName = sourceHubName.trim();

    HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess("hub");
	boolean iscollection = hubInventoryGroupProcess.isCollection(
	 hubInventoryGroupOvBeanCollection, HubName, invengrp);

	if ("getstorageadd".equalsIgnoreCase(Action)) {
	 out.println(buildstorageadd(plantid, buildingid, storagearea));
	}
	else if ("getshipto".equalsIgnoreCase(Action)) {
	 out.println(buildLikeShipto(HubName, searchString,sourceHubName));
	}
	else if ("getitemid".equalsIgnoreCase(Action)) {
	 if ("ITEM_ID".equalsIgnoreCase(searchlike)) {
		out.println(buildLikeitemId(iscollection,HubName, plantid, buildingid, invengrp, searchString,
		 searchlike));
	 }
	 else if ("CAT_PART_NO".equalsIgnoreCase(searchlike)) {
		out.println(buildLikePartNumber(iscollection,HubName, plantid, buildingid, invengrp, searchString,
		 searchlike));
	 }
	}
	else if ("okupdate".equalsIgnoreCase(Action) || "getinvgroups".equalsIgnoreCase(Action)) {
	 out.println(buildsendinvgrps(plantid, buildingid,sourceHubName));
	}

	out.close();
 }

  public StringBuffer buildstorageadd ( String selplantid,String selbldingid,String storagearea )
  {
	StringBuffer Msgn=new StringBuffer();
	StringBuffer Msgbody=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Msgn.append( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Ship To","poshipto.js",intcmIsApplication ) );
	Msgbody.append( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"\">\n" );
	StringBuffer Msgtemp=new StringBuffer();
	Msgtemp.append( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
	Msgtemp.append( "<!--\n" );
	Msgtemp.append( "function sendSupplierData()\n" );
	Msgtemp.append( "{\n" );
	Msgtemp.append( sendstorageadd( selplantid,selbldingid,storagearea ) );
	Msgtemp.append( "window.close();\n" );
	Msgtemp.append( " }\n" );
	Msgtemp.append( "// -->\n</SCRIPT>\n" );
	Msgn.append( Msgtemp );
	Msgn.append( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	Msgbody.append( "<CENTER><BR><BR>\n" );
	Msgbody.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	Msgbody.append( "</FORM></BODY></HTML>\n" );
	Msgn.append( Msgbody );

	return Msgn;
  }


  public StringBuffer buildsendinvgrps ( String selplantid,String selbldingid,String hubName )
  {
	StringBuffer Msgn=new StringBuffer();
	StringBuffer Msgbody=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Msgn.append( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Ship To","poshipto.js",intcmIsApplication ) );
	Msgbody.append( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"\">\n" );
	StringBuffer Msgtemp=new StringBuffer();
	Msgtemp.append( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
	Msgtemp.append( "<!--\n" );
	Msgtemp.append( "function sendSupplierData()\n" );
	Msgtemp.append( "{\n" );
	Msgtemp.append( buildinventorygrp( selplantid,selbldingid, hubName) );
	Msgtemp.append( "window.close();\n" );
	Msgtemp.append( " }\n" );
	Msgtemp.append( "// -->\n</SCRIPT>\n" );
	Msgn.append( Msgtemp );
	Msgn.append( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	Msgbody.append( "<CENTER><BR><BR>\n" );
	Msgbody.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	Msgbody.append( "</FORM></BODY></HTML>\n" );
	Msgn.append( Msgbody );

	return Msgn;
  }

  public StringBuffer buildinventorygrp( String selctplant,String selbldgid,String hubName)
  {
	StringBuffer Msgtemp=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Msgtemp.append( "{\n" );
	Msgtemp.append( "opener.removeAllOptionItem(opener.document.getElementById(\"invengrp\"));\n" );
	Msgtemp.append( "opener.addOptionItem(opener.document.getElementById(\"invengrp\"),'All','All Storage Areas');\n" );
	try
	{
	  String query = "Select distinct INVENTORY_GROUP,STORAGE_AREA from item_inventory_header_view where lower(PREFERRED_WAREHOUSE) = lower('"+hubName+"') and lower(PLANT_ID) = lower('" + selctplant + "') ";

	  if ( ! ( selbldgid.trim().length() == 0 || "All Buildings".equalsIgnoreCase( selbldgid ) ) )
	  {
		query +=" and lower(BLDG_ID) = lower('" + selbldgid + "') ";
	  }

	  dbrs=db.doQuery( query );
	  rs=dbrs.getResultSet();

	  while ( rs.next() )
	  {
		String invgrp=rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ).trim();
		String invgrpname=rs.getString( "STORAGE_AREA" ) == null ? "" : rs.getString( "STORAGE_AREA" ).trim();
		Msgtemp.append( "opener.addOptionItem(opener.document.getElementById(\"invengrp\"),'" + invgrp + "','" + invgrpname + "');\n" );
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

	Msgtemp.append( "if (opener.document.iteminventory.invengrp.length == 2)\n" );
	Msgtemp.append( "	{\n" );
	Msgtemp.append( "	   opener.document.iteminventory.invengrp.selectedIndex = 1;\n" );
	Msgtemp.append( "	}\n" );

	/*try
	{
	  dbrs=db.doQuery( "Select distinct LOCATION_DESC || ',  Attn:' || ATTENTION || ',  ' || ADDRESS_LINE_1 || ',  ' || ADDRESS_LINE_2 || ',  ' || ADDRESS_LINE_3 || ',  ' || CITY || ',  ' || STATE_ABBREV || '-' || ZIP || ',  ' || COUNTRY_ABBREV ADDRESS from item_inventory_header_view where lower(PLANT_ID) = lower('" + Hub + "') and lower(BLDG_ID) = lower('" + shipTo + "') and lower(STORAGE_AREA) = lower('" + storagearea + "') " );
	  rs=dbrs.getResultSet();
	  String buildgaddressline = "";
	  while ( rs.next() )
	  {
		buildgaddressline=rs.getString( "ADDRESS" ) == null ? "" : rs.getString( "ADDRESS" ).trim();
	  }
	  Msgtemp.append( "buildingaddress = opener.document.getElementById(\"buildingaddress\");\n" );
	  Msgtemp.append( "buildingaddress.innerHTML = \"" + buildgaddressline + "\";\n" );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ){dbrs.close();}
	}*/

	Msgtemp.append( " }\n" );

	return Msgtemp;
  }

  public StringBuffer sendstorageadd( String Hub,String shipTo,String storagearea )
  {
	StringBuffer Msgtemp=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet rs=null;
    Msgtemp.append( "{\n" );
	try
	{
	  dbrs=db.doQuery( "Select distinct LOCATION_DESC || ',  Attn:' || ATTENTION || ',  ' || ADDRESS_LINE_1 || ',  ' || ADDRESS_LINE_2 || ',  ' || ADDRESS_LINE_3 || ',  ' || CITY || ',  ' || STATE_ABBREV || '-' || ZIP || ',  ' || COUNTRY_ABBREV ADDRESS from item_inventory_header_view where lower(PLANT_ID) = lower('" + Hub + "') and lower(BLDG_ID) = lower('" + shipTo + "') and lower(INVENTORY_GROUP) = lower('" + storagearea + "') " );
	  rs=dbrs.getResultSet();
	  String buildgaddressline = "";
	  while ( rs.next() )
	  {
		buildgaddressline=rs.getString( "ADDRESS" ) == null ? "" : rs.getString( "ADDRESS" ).trim();
	  }
	  Msgtemp.append( "buildingaddress = opener.document.getElementById(\"buildingaddress\");\n" );
	  Msgtemp.append( "buildingaddress.innerHTML = \"" + buildgaddressline + "\";\n" );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ){dbrs.close();}
	}

	Msgtemp.append( " }\n" );

	return Msgtemp;
  }

  public StringBuffer buildsendShipto( String shiptoid,String hubnum, String sourceHubName )
  {
	StringBuffer Msgn=new StringBuffer();
	StringBuffer Msgbody=new StringBuffer();

	Msgn.append( radian.web.HTMLHelpObj.printHTMLHeader( "Item Ship To","iteminventory.js",intcmIsApplication ) );
	Msgbody.append( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"\">\n" );

	StringBuffer Msgtemp=new StringBuffer();
	Msgtemp.append( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
	Msgtemp.append( "<!--\n" );
	Msgtemp.append( "function sendSupplierData()\n" );
	Msgtemp.append( "{\n" );
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Msgtemp.append( "{\n" );

	try
	{
	  dbrs=db.doQuery( "Select * from item_inventory_header_view  where lower(BLDG_ID) = lower('" + shiptoid + "') and BRANCH_PLANT = " + hubnum + "" );
	  rs=dbrs.getResultSet();

	  while ( rs.next() )
	  {
		String location_id=rs.getString( "BLDG_ID" ) == null ? "" : rs.getString( "LOCATION_ID" ).trim();

		Msgtemp.append( "shiptoid = opener.document.getElementById(\"shiptoid\");\n" );
		Msgtemp.append( "shiptoid.className = \"HEADER\";\n" );
		Msgtemp.append( "shiptoid.value = \"" + location_id + "\";\n" );

		Msgtemp.append( "validshipto = opener.document.getElementById(\"validshipto\");\n" );
		Msgtemp.append( "validshipto.value = \"Yes\";\n" );
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

	Msgtemp.append( " }\n" );
	Msgtemp.append( buildinventorygrp( hubnum,shiptoid,sourceHubName ) );
	Msgtemp.append( "window.close();\n" );
	Msgtemp.append( " }\n" );
	Msgtemp.append( "// -->\n</SCRIPT>\n" );
	Msgn.append( Msgtemp );
	Msgn.append( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	Msgbody.append( "<CENTER><BR><BR>\n" );
	Msgbody.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	Msgbody.append( "</FORM></BODY></HTML>\n" );
	Msgn.append( Msgbody );

	return Msgn;
  }

  public StringBuffer buildLikeShipto( String hubnum,String SearchString,String sourceHubName )
  {
    StringBuffer Msgl=new StringBuffer();
    DBResultSet dbrs=null;
    ResultSet rs=null;

    boolean foundshipto=false;
    String branchPlant="";

    if ( ( SearchString.trim().length() > 0 ) )
    {
      try
      {
        int test_number=DbHelpers.countQuery( db,"select count(*) from item_inventory_header_view where lower(BLDG_ID) = lower('" + SearchString + "') and BRANCH_PLANT = "+hubnum+" " );

        if ( test_number == 1 )
        {
          foundshipto=true;
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        Msgl.append( "<BODY><BR><BR>\n" );
        Msgl.append( "<FONT FACE=\"Arial\" SIZE=\"3\">Error Occured.<BR>\n" );
        Msgl.append( "Please Try Again.\n" );
        Msgl.append( "<CENTER><BR><BR>\n" );
        Msgl.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
        Msgl.append( "</FORM></BODY></HTML>\n" );
        return Msgl;
      }
    }

    if ( foundshipto )
    {
      Msgl.append( buildsendShipto( SearchString,hubnum,sourceHubName ) );
      return Msgl;
    }

    Msgl.append( radian.web.HTMLHelpObj.printHTMLHeader( "Item Ship To","iteminventory.js",intcmIsApplication ) );
    Msgl.append( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    Msgl.append( "<!--\n" );
    Msgl.append( "function ShowSearch(name,entered)\n" );
    Msgl.append( "{\n" );
    Msgl.append( " with (entered)\n" );
    Msgl.append( " {\n" );
    Msgl.append( " loc = \"" + radian.web.tcmisResourceLoader.getitemshiptourl() + "&Action=\" + name.toString() + \"&SearchString=\";\n" );
    Msgl.append( " loc = loc + window.document.SupplierLike.SearchString.value;\n" );
    Msgl.append( " loc = loc + \"&shiptoid=\"; \n" );
    Msgl.append( " loc = loc + window.document.SupplierLike.shiptoid.value;\n" );
    Msgl.append( " loc = loc + \"&HubName=" + hubnum + "\"; \n" );
    Msgl.append( " }\n" );
    Msgl.append( "  if (name.toString() == \"okupdate\")\n" );
    Msgl.append( "  {\n" );
    Msgl.append( "   eval( testmfgid = \"window.document.SupplierLike.shiptoid\")\n" );
    Msgl.append( "   if ( (eval(testmfgid.toString())).value.length > 0 )\n" );
    Msgl.append( "   { \n" );
    Msgl.append( "    sendShipto(entered);\n" );
	Msgl.append( "    window.location.replace(loc);\n" );
    Msgl.append( "   }\n" );
    Msgl.append( "  }\n" );
    Msgl.append( " else \n" );
    Msgl.append( " {\n" );
    Msgl.append( "  window.location.replace(loc);\n" );
    Msgl.append( " }\n" );
    Msgl.append( " }\n" );
    Msgl.append( "// -->\n </SCRIPT>\n" );
    Msgl.append( "<BODY>\n" );
    Msgl.append( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + radian.web.tcmisResourceLoader.getitemshiptourl() + "Session=Update\">\n" );

    Msgl.append( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
    Msgl.append( "<TR>\n" );
    Msgl.append( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text:</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + SearchString + "\" SIZE=\"40\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"getshipto\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n" );
    Msgl.append( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Ship To Id:</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"shiptoid\" value=\"\" SIZE=\"25\" readonly>\n" );
    Msgl.append( "</TR>\n" );
    Msgl.append( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
    Msgl.append( "<TR>\n" );
    Msgl.append( "<TH WIDTH=\"15%\"><B>Plant</B></FONT></TH>\n" );
    Msgl.append( "<TH WIDTH=\"35%\"><B>Building</B></FONT></TH>\n" );
    Msgl.append( "</TR></TABLE>\n" );

    //open scrolling table
    Msgl.append( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
    Msgl.append( "<TBODY>\n" );
    Msgl.append( "<TR>\n" );
    Msgl.append( "<TD VALIGN=\"TOP\">\n" );
    Msgl.append( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );
    //Write code to insert rows here
    Msgl.append( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

    int totalrecords=0;
    String query="";
	SearchString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
	String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"BLDG_ID" );

	query+="select distinct PLANT_ID,BLDG_ID from item_inventory_header_view where "+wherese+" and BRANCH_PLANT = "+hubnum+" order by BLDG_ID asc";

    try
    {
      dbrs=db.doQuery( query );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        totalrecords+=1;

        String location_id=rs.getString( "BLDG_ID" ) == null ? "" : rs.getString( "BLDG_ID" ).trim();
        String plant_id=rs.getString( "PLANT_ID" ) == null ? "" : rs.getString( "PLANT_ID" ).trim();

        String location_id1=HelpObjs.findReplace( location_id,"#","%23" );
		location_id1=HelpObjs.findReplace( location_id,"&","%26" );

        String Color=" ";
        if ( totalrecords % 2 == 0 )
        {
          Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
        }
        else
        {
          Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
        }

        Msgl.append( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addshiptoID('" + HelpObjs.addescapesForJavascript(location_id1) + "')\" BORDERCOLOR=\"black\">\n" );
        Msgl.append( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
        Msgl.append( plant_id );
        Msgl.append( "</TD>\n" );
        Msgl.append( "<TD " + Color + " WIDTH=\"35%\" ALIGN=\"LEFT\">" );
        Msgl.append( location_id );
        Msgl.append( "</TD>\n" );
        Msgl.append( "</TR>\n" );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      Msgl.append( "An Error Occured While Querying the Database" );
      Msgl.append( "</BODY>\n</HTML>\n" );
      return Msgl;
    }
    finally
    {
      if (dbrs!= null) {dbrs.close();}
    }

    if ( totalrecords == 0 )
    {
      Msgl.append( "<TR><TD>No Records Found</TD></TR>\n" );
    }
    Msgl.append( "</TABLE>\n" );

    //close scrolling table
    Msgl.append( "</DIV>\n" );
    Msgl.append( "</TD>\n" );
    Msgl.append( "</TR>\n" );
    Msgl.append( "</TBODY>\n" );
    Msgl.append( "</TABLE>\n" );
    Msgl.append( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"ShowSearch('okupdate',this)\" type=\"button\">\n" );
    Msgl.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    Msgl.append( "</FORM></BODY></HTML>\n" );

    return Msgl;
  }

  public StringBuffer buildLikeitemId( boolean iscollection,String branchPlant,String selplantid, String selbldgid,String invengroup,String SearchString,String searchlike )
  {
	StringBuffer Msgl=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet rs=null;

	//String branchPlant="";
	Msgl.append( radian.web.HTMLHelpObj.printHTMLHeader( "Item Ship To","iteminventory.js",intcmIsApplication ) );
	Msgl.append( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
	Msgl.append( "<!--\n" );
	Msgl.append( "function ShowSearch(name,entered)\n" );
	Msgl.append( "{\n" );
	Msgl.append( " with (entered)\n" );
	Msgl.append( " {\n" );
	Msgl.append( " loc = \"" + radian.web.tcmisResourceLoader.getitemshiptourl() + "&Action=\" + name.toString() + \"&SearchString=\";\n" );
	Msgl.append( " loc = loc + window.document.SupplierLike.SearchString.value;\n" );
	Msgl.append( " loc = loc + \"&itemid=\"; \n" );
	Msgl.append( " loc = loc + window.document.SupplierLike.itemid.value;\n" );
	Msgl.append( " loc = loc + \"&HubName=" + branchPlant + "\"; \n" );
	Msgl.append( " loc = loc + \"&searchlike=" + searchlike + "\"; \n" );
	Msgl.append( " loc = loc + \"&plantid=" + HelpObjs.addescapesForJavascript(selplantid) + "\"; \n" );
	Msgl.append( " loc = loc + \"&buildingid=" + selbldgid + "\"; \n" );
	Msgl.append( " loc = loc + \"&invengrp=" + HelpObjs.addescapesForJavascript(invengroup) + "\"; \n" );
	Msgl.append( " }\n" );
	Msgl.append( "  if (name.toString() == \"okupdate\")\n" );
	Msgl.append( "  {\n" );
	Msgl.append( "   eval( testmfgid = \"window.document.SupplierLike.itemid\")\n" );
	Msgl.append( "   if ( (eval(testmfgid.toString())).value.length > 0 )\n" );
	Msgl.append( "   { \n" );
	Msgl.append( "    senditemId(entered);\n" );
	Msgl.append( "   }\n" );
	Msgl.append( "  }\n" );
	Msgl.append( " else \n" );
	Msgl.append( " {\n" );
	Msgl.append( "  window.location.replace(loc);\n" );
	Msgl.append( " }\n" );
	Msgl.append( " }\n" );
	Msgl.append( "// -->\n </SCRIPT>\n" );
	Msgl.append( "<BODY>\n" );
	Msgl.append( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + radian.web.tcmisResourceLoader.getitemshiptourl() + "Session=Update\">\n" );

	Msgl.append( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	Msgl.append( "<TR>\n" );
	Msgl.append( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text:</B></TD>\n" );
	Msgl.append( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + SearchString + "\" SIZE=\"40\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"getitemid\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n" );
	Msgl.append( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Item Id:</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"itemid\" value=\"\" SIZE=\"25\" readonly>\n" );
	Msgl.append( "</TR>\n" );
	Msgl.append( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	Msgl.append( "<TR>\n" );
	Msgl.append( "<TH WIDTH=\"5%\"><B>Item</B></FONT></TH>\n" );
	Msgl.append( "<TH WIDTH=\"15%\"><B>Description</B></FONT></TH>\n" );
	Msgl.append( "<TH WIDTH=\"10%\"><B>Packaging</B></FONT></TH>\n" );
	Msgl.append( "</TR></TABLE>\n" );

	//open scrolling table
	Msgl.append( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	Msgl.append( "<TBODY>\n" );
	Msgl.append( "<TR>\n" );
	Msgl.append( "<TD VALIGN=\"TOP\">\n" );
	Msgl.append( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );
	//Write code to insert rows here
	Msgl.append( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

	int totalrecords=0;
	SearchString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
	String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"SEARCH" );

	String query="select distinct ITEM_ID, ITEM_DESC, PACKAGING from ITEM_INVENTORY_SEARCH_VIEW where ";
	boolean falgforand=false;

	if ( ! ( selplantid.trim().length() == 0 || "All Plants".equalsIgnoreCase( selplantid ) ) )
	{
	  if ( falgforand )
		query+=" and PLANT_ID = '" + selplantid + "' ";
	  else
		query+=" PLANT_ID = '" + selplantid + "' ";
		falgforand=true;
	}

	if ( ! ( selbldgid.trim().length() == 0 || "All Buildings".equalsIgnoreCase( selbldgid ) ) )
	{
	  if ( falgforand )
		query+=" and BLDG_ID = '" + selbldgid + "' ";
	  else
		query+=" BLDG_ID = '" + selbldgid + "' ";
		falgforand=true;
	}

	 if (! (invengroup.trim().length() == 0 || "All".equalsIgnoreCase(invengroup)))
	 {
	    if (falgforand)
	     query += " and ";

	    if (iscollection) {
		query += " INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_COLLECTION where HUB = '" +
		 branchPlant +
		 "' and INVENTORY_GROUP_COLLECTION = '" + invengroup + "') ";
		falgforand = true;
	    }
	    else
	    {
		query += " INVENTORY_GROUP = '" + invengroup + "'  ";
		falgforand = true;
	    }
	}

	if ( SearchString.trim().length() > 0 )
	{
	  if ( falgforand )
		query+=" and " + wherese + " ";
	  else
		query+=" " + wherese + " ";
		falgforand=true;
	}
	else if ( "All Plants".equalsIgnoreCase( selplantid ) && "All Buildings".equalsIgnoreCase( selbldgid ) && "All".equalsIgnoreCase( invengroup ) )
	{
	  query = "";
	}
	query+=" ORDER BY ITEM_ID";

	try
	{
	  if (query.length() > 0 )
	  {
		dbrs=db.doQuery( query );
		rs=dbrs.getResultSet();

		while ( rs.next() )
		{
		  totalrecords+=1;

		  String item_id=rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ).trim();
		  String item_desc=rs.getString( "ITEM_DESC" ) == null ? "" : rs.getString( "ITEM_DESC" ).trim();
		  String packaging=rs.getString( "PACKAGING" ) == null ? "" : rs.getString( "PACKAGING" ).trim();

		  String Color=" ";
		  if ( totalrecords % 2 == 0 )
		  {
			Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }
		  else
		  {
			Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }

		  Msgl.append( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"additemID('" + item_id + "')\" BORDERCOLOR=\"black\">\n" );
		  Msgl.append( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
		  Msgl.append( item_id );
		  Msgl.append( "</TD>\n" );
		  Msgl.append( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
		  Msgl.append( item_desc );
		  Msgl.append( "</TD>\n" );
		  Msgl.append( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
		  Msgl.append( packaging );
		  Msgl.append( "</TD>\n" );

		  Msgl.append( "</TR>\n" );
		}
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  Msgl.append( "An Error Occured While Querying the Database" );
	  Msgl.append( "</BODY>\n</HTML>\n" );
	  return Msgl;
	}
	finally
	{
	  if (dbrs!= null) {dbrs.close();}
	}

	if ( totalrecords == 0 )
	{
	  Msgl.append( "<TR><TD>No Records Found</TD></TR>\n" );
	}
	Msgl.append( "</TABLE>\n" );

	//close scrolling table
	Msgl.append( "</DIV>\n" );
	Msgl.append( "</TD>\n" );
	Msgl.append( "</TR>\n" );
	Msgl.append( "</TBODY>\n" );
	Msgl.append( "</TABLE>\n" );
	Msgl.append( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"ShowSearch('okupdate',this)\" type=\"button\">\n" );
	Msgl.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	Msgl.append( "</FORM></BODY></HTML>\n" );

	return Msgl;
  }

	public StringBuffer buildLikePartNumber( boolean iscollection,String branchPlant,String selplantid, String selbldgid,String invengroup,String SearchString,String searchlike )
		{
		StringBuffer Msgl=new StringBuffer();
		DBResultSet dbrs=null;
		ResultSet rs=null;

		//String branchPlant="";
		Msgl.append( radian.web.HTMLHelpObj.printHTMLHeader( "Item Ship To","iteminventory.js",intcmIsApplication ) );
		Msgl.append( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
		Msgl.append( "<!--\n" );
		Msgl.append( "function ShowSearch(name,entered)\n" );
		Msgl.append( "{\n" );
		Msgl.append( " with (entered)\n" );
		Msgl.append( " {\n" );
		Msgl.append( " loc = \"" + radian.web.tcmisResourceLoader.getitemshiptourl() + "&Action=\" + name.toString() + \"&SearchString=\";\n" );
		Msgl.append( " loc = loc + window.document.SupplierLike.SearchString.value;\n" );
		Msgl.append( " loc = loc + \"&itemid=\"; \n" );
		Msgl.append( " loc = loc + window.document.SupplierLike.itemid.value;\n" );
		Msgl.append( " loc = loc + \"&HubName=" + branchPlant + "\"; \n" );
		Msgl.append( " loc = loc + \"&searchlike=" + searchlike + "\"; \n" );
		Msgl.append( " loc = loc + \"&plantid=" + HelpObjs.addescapesForJavascript(selplantid) + "\"; \n" );
		Msgl.append( " loc = loc + \"&buildingid=" + selbldgid + "\"; \n" );
		Msgl.append( " loc = loc + \"&invengrp=" + HelpObjs.addescapesForJavascript(invengroup) + "\"; \n" );
		Msgl.append( " }\n" );
		Msgl.append( "  if (name.toString() == \"okupdate\")\n" );
		Msgl.append( "  {\n" );
		Msgl.append( "   eval( testmfgid = \"window.document.SupplierLike.itemid\")\n" );
		Msgl.append( "   if ( (eval(testmfgid.toString())).value.length > 0 )\n" );
		Msgl.append( "   { \n" );
		Msgl.append( "    senditemId(entered);\n" );
		Msgl.append( "   }\n" );
		Msgl.append( "  }\n" );
		Msgl.append( " else \n" );
		Msgl.append( " {\n" );
		Msgl.append( "  window.location.replace(loc);\n" );
		Msgl.append( " }\n" );
		Msgl.append( " }\n" );
		Msgl.append( "// -->\n </SCRIPT>\n" );
		Msgl.append( "<BODY>\n" );
		Msgl.append( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + radian.web.tcmisResourceLoader.getitemshiptourl() + "Session=Update\">\n" );

		Msgl.append( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
		Msgl.append( "<TR>\n" );
		Msgl.append( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text:</B></TD>\n" );
		Msgl.append( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + SearchString + "\" SIZE=\"40\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"getitemid\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n" );
		Msgl.append( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Part Number:</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"itemid\" value=\"\" SIZE=\"25\" readonly>\n" );
		Msgl.append( "</TR>\n" );
		Msgl.append( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
		Msgl.append( "<TR>\n" );
		Msgl.append( "<TH WIDTH=\"5%\"><B>Part</B></FONT></TH>\n" );
		Msgl.append( "<TH WIDTH=\"15%\"><B>Description</B></FONT></TH>\n" );
		//Msgl.append( "<TH WIDTH=\"10%\"><B>Packaging</B></FONT></TH>\n" );
		Msgl.append( "</TR></TABLE>\n" );

		//open scrolling table
		Msgl.append( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
		Msgl.append( "<TBODY>\n" );
		Msgl.append( "<TR>\n" );
		Msgl.append( "<TD VALIGN=\"TOP\">\n" );
		Msgl.append( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );
		//Write code to insert rows here
		Msgl.append( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

		int totalrecords=0;
		SearchString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
		String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"SEARCH" );

		String query="select distinct CAT_PART_NO, PART_DESCRIPTION from ITEM_INVENTORY_SEARCH_VIEW where ";
		boolean falgforand=false;

		if ( ! ( selplantid.trim().length() == 0 || "All Plants".equalsIgnoreCase( selplantid ) ) )
		{
			if ( falgforand )
			query+=" and PLANT_ID = '" + selplantid + "' ";
			else
			query+=" PLANT_ID = '" + selplantid + "' ";
			falgforand=true;
		}

		if ( ! ( selbldgid.trim().length() == 0 || "All Buildings".equalsIgnoreCase( selbldgid ) ) )
		{
			if ( falgforand )
			query+=" and BLDG_ID = '" + selbldgid + "' ";
			else
			query+=" BLDG_ID = '" + selbldgid + "' ";
			falgforand=true;
		}


		 if (! (invengroup.trim().length() == 0 || "All".equalsIgnoreCase(invengroup)))
		 {
		  if (falgforand)
		    query += " and ";

    	          if (iscollection) {
			query += " INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_COLLECTION where HUB = '" +
			 branchPlant +
			 "' and INVENTORY_GROUP_COLLECTION = '" + invengroup + "') ";
			falgforand = true;
		 }
		 else
		 {
		  query += " INVENTORY_GROUP = '" + invengroup + "'  ";
		falgforand = true;
		 }
		}


		if ( SearchString.trim().length() > 0 )
		{
			if ( falgforand )
			query+=" and " + wherese + " ";
			else
			query+=" " + wherese + " ";
			falgforand=true;
		}
		else if ( "All Plants".equalsIgnoreCase( selplantid ) && "All Buildings".equalsIgnoreCase( selbldgid ) && "All".equalsIgnoreCase( invengroup ) )
		{
			query = "";
		}
		query+=" ORDER BY CAT_PART_NO";

		try
		{
			if (query.length() > 0 )
			{
			dbrs=db.doQuery( query );
			rs=dbrs.getResultSet();

			while ( rs.next() )
			{
				totalrecords+=1;

				String catPartNumber=rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ).trim();
				String partDescription=rs.getString( "PART_DESCRIPTION" ) == null ? "" : rs.getString( "PART_DESCRIPTION" ).trim();
				//String packaging=rs.getString( "PACKAGING" ) == null ? "" : rs.getString( "PACKAGING" ).trim();


				String Color=" ";
				if ( totalrecords % 2 == 0 )
				{
				Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
				}
				else
				{
				Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
				}

				Msgl.append( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"additemID('" + catPartNumber + "')\" BORDERCOLOR=\"black\">\n" );
				Msgl.append( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
				Msgl.append( catPartNumber );
				Msgl.append( "</TD>\n" );
				Msgl.append( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
				Msgl.append( partDescription );
				Msgl.append( "</TD>\n" );
				/*Msgl.append( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
				Msgl.append( packaging );
				Msgl.append( "</TD>\n" );*/

				Msgl.append( "</TR>\n" );
			}
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			Msgl.append( "An Error Occured While Querying the Database" );
			Msgl.append( "</BODY>\n</HTML>\n" );
			return Msgl;
		}
		finally
		{
			if (dbrs!= null) {dbrs.close();}
		}

		if ( totalrecords == 0 )
		{
			Msgl.append( "<TR><TD>No Records Found</TD></TR>\n" );
		}
		Msgl.append( "</TABLE>\n" );

		//close scrolling table
		Msgl.append( "</DIV>\n" );
		Msgl.append( "</TD>\n" );
		Msgl.append( "</TR>\n" );
		Msgl.append( "</TBODY>\n" );
		Msgl.append( "</TABLE>\n" );
		Msgl.append( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"ShowSearch('okupdate',this)\" type=\"button\">\n" );
		Msgl.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
		Msgl.append( "</FORM></BODY></HTML>\n" );

		return Msgl;
		}

}