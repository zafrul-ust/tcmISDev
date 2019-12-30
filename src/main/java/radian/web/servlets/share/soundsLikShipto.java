package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//
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
 * 08-18-03 - Code Cleanup
 * 09-15-03 - Showing the Address line 3 of the ship to address
 * 01-06-04 - Escaping & in the Location ID
 * 07-22-04 - Not setting the Hub from shipto. Getting ti from the Hub on Buy Order
 */

public class soundsLikShipto
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private String nematid_Servlet="";
  private PrintWriter out = null;
  public soundsLikShipto( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    out=response.getWriter();
    response.setContentType( "text/html" );

/*    String CompanyId=request.getParameter( "Company" );
    if ( CompanyId == null )
      CompanyId="";
    CompanyId=CompanyId.trim();*/
    
    String shiptocompanyid=request.getParameter( "shiptocompanyid" );
    if ( shiptocompanyid == null )
      shiptocompanyid="";
    shiptocompanyid=shiptocompanyid.trim();

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action=Action.trim();

    String searchString=request.getParameter( "SearchString" );
    if ( searchString == null )
      searchString="";
    searchString=searchString.trim();

    String shiptoID=request.getParameter( "shiptoID" );
    if ( shiptoID == null )
      shiptoID="";
    shiptoID=shiptoID.trim();

    String invengrp=request.getParameter( "invengrp" );
    if ( invengrp == null )
      invengrp="";
    invengrp=invengrp.trim();

    String attachedpr=request.getParameter( "attachedpr" );
    if ( attachedpr == null )
      attachedpr="";
    attachedpr=attachedpr.trim();

    if ( "inventorygrp".equalsIgnoreCase( Action ) )
    {
      buildinventorygrp( shiptoID );
    }
    else if ( "okupdate".equalsIgnoreCase( Action ) )
    {
      if ( invengrp.length() > 0 && "Y".equalsIgnoreCase( attachedpr ) )
      {
        //buildsendHubInfo( shiptoID );
      }
      else
      {
        buildinventorygrp( shiptoID );
      }
    }
    else if ( "Search".equalsIgnoreCase( Action ) )
    {
      buildLikeSupplier( "Search",searchString,shiptocompanyid,shiptoID,invengrp );
    }
    else
    {
      buildLikeSupplier( "",searchString,shiptocompanyid,shiptoID,invengrp );
    }
    out.close();
  }

  private void buildinventorygrp( String shiptolocid )
  {
    //StringBuffer Msg=new StringBuffer();
    //String hubName="";
    //String branchPlant="";
    DBResultSet dbrs=null;
    ResultSet rs=null;
    Vector rootcause=radian.web.HTMLHelpObj.createinvgrplist( db,shiptolocid );

    String currencyid ="None";
    String opsEntityId ="";
    try
    {
     //dbrs=db.doQuery( "select x.HOME_CURRENCY_ID from tcm_ops.home_company x, hub y where x.HOME_COMPANY_ID = y.HOME_COMPANY_ID and y.BRANCH_PLANT in (Select BRANCH_PLANT from location_to_hub_view where LOCATION_ID = '" + shiptolocid + "') " );
     dbrs=db.doQuery( "select distinct home_currency_id, ops_entity_id from location_to_ig_view where LOCATION_ID = '" + shiptolocid + "'" );     
     rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        currencyid=rs.getString( "HOME_CURRENCY_ID" ) == null ? "" : rs.getString( "HOME_CURRENCY_ID" ).trim();
        opsEntityId=rs.getString( "OPS_ENTITY_ID" ) == null ? "" : rs.getString( "OPS_ENTITY_ID" ).trim();
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

    try
    {
      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Inventory Group","poshipto.js",false ) );
      out.println( "</HEAD>  \n" );
      out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>Please Select Inventory Group</B>\n" ) );
      out.println( "<FORM method=\"POST\" NAME=\"invengrp\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"\">\n" );
      out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      out.println( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>Inventory Group:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println( "<TD HEIGHT=45 WIDTH=\"20%\" CLASS=\"announce\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\" ID=\"invengrpdo\" NAME=\"invengrpdo\" size=\"1\">\n" );
      out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
      out.println( radian.web.HTMLHelpObj.getDropDown( rootcause,"" ) );
      out.println( "</SELECT>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>" );
      out.println( "</TABLE>\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"currencyid\" id=\"currencyid\" VALUE=\"" + currencyid + "\">\n" );
       out.println( "<INPUT TYPE=\"hidden\" NAME=\"opsEntityId\" id=\"opsEntityId\" VALUE=\"" + opsEntityId + "\">\n" );
      //out.println( "<INPUT TYPE=\"hidden\" NAME=\"hub\" VALUE=\"" + branchPlant + "\">\n" );
      //out.println( "<INPUT TYPE=\"hidden\" NAME=\"hubname\" VALUE=\"" + hubName + "\">\n" );
      out.println( "<BR>\n" );
      out.println( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Ok\" OnClick=\"sendinvgrpvalue()\" ID=\"sendterv\" NAME=\"sendterv\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
      out.println( "<INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Cancel\" OnClick=\"cancel()\" ID=\"CloseButton\" NAME=\"CloseButton\"></CENTER>\n" );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
    return;
  }

  public void buildsendSupplier( String shiptoid, String shipToCompanyId )
  {
    //StringBuffer Msgn=new StringBuffer();
    //StringBuffer Msgbody=new StringBuffer();
    nematid_Servlet=bundle.getString( "PURCHASE_ORDER_SHIPTO" );

    out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Ship To","poshipto.js",false ) );

    //StringBuffer Msgtemp=new StringBuffer();
    out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    out.println( "<!--\n" );
    out.println( "function sendSupplierData()\n" );
    out.println( "{\n" );
    radian.web.poHelpObj.buildsendShipto( db,shiptoid,shipToCompanyId,out );
    out.println( "window.close();\n" );
    out.println( " }\n" );
    out.println( "// -->\n</SCRIPT>\n" );
    //out.println( Msgtemp );
    out.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	out.println( "<FORM METHOD=\"POST\" id=\"revDateLike\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" ID=\"revisionDate\" NAME=\"revisionDate\" VALUE=\"" + shiptoid + "\">\n" );
    out.println( "<CENTER><BR><BR>\n" );
    out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    out.println( "</FORM></BODY></HTML>\n" );
    //out.println( Msgbody );

    return;
  }

  /*public void buildsendHubInfo( String shiptoid )
  {
    //StringBuffer Msgn=new StringBuffer();
    //StringBuffer Msgbody=new StringBuffer();
    DBResultSet dbrs=null;
    ResultSet rs=null;
    nematid_Servlet=bundle.getString( "PURCHASE_ORDER_SHIPTO" );
    out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Ship To","poshipto.js" ) );
    //StringBuffer Msgtemp=new StringBuffer();
    out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    out.println( "<!--\n" );
    out.println( "function sendSupplierData()\n" );
    out.println( "{\n" );
    int numofHubs=0;

    try
    {
      dbrs=db.doQuery( "Select * from location_to_hub_view where LOCATION_ID = '" + shiptoid + "' " );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        String hubName=rs.getString( "HUB_NAME" ) == null ? "" : rs.getString( "HUB_NAME" ).trim();
        //String location_id = rs.getString("LOCATION_ID")==null?"":rs.getString("LOCATION_ID").trim();
        String branchPlant=rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ).trim();

        out.println( "hubSlectName = opener.document.getElementById(\"HubName\");\n" );
        out.println( "hubSlectName.value = \"" + branchPlant + "\";\n" );

        out.println( "HubFullName = opener.document.getElementById(\"HubFullName\");\n" );
        out.println( "HubFullName.value = \"" + hubName + "\";\n" );

        numofHubs++;
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

    out.println( "numofHubs = opener.document.getElementById(\"numofHubs\");\n" );
    out.println( "numofHubs.value = \"" + numofHubs + "\";\n" );
    out.println( "window.close();\n" );
    out.println( " }\n" );
    out.println( "// -->\n</SCRIPT>\n" );
    //out.println( Msgtemp );
    out.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	out.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"revisionDate\" VALUE=\"" + shiptoid + "\">\n" );
    out.println( "<CENTER><BR><BR>\n" );
    out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    out.println( "</FORM></BODY></HTML>\n" );
    //out.println( Msgbody );

    return;
  }*/

  public void buildLikeSupplier( String SearchFlag,String SearchString,String shipToCompanyId,String MfgID1,String invengrp )
  {
    //StringBuffer Msgl=new StringBuffer();
    MfgID1=MfgID1.trim();
    DBResultSet dbrs=null;
    ResultSet rs=null;

    boolean foundsupplier=false;
    String branchPlant="";

    if ( ( MfgID1.trim().length() > 0 ) && ( !"Search".equalsIgnoreCase( SearchFlag ) ) )
    {
      try
      {
        String shipToLocCountQuery = "";
        shipToLocCountQuery = "select count(*) from ship_to_location_view where lower(LOCATION_ID) = lower('" + MfgID1 + "') ";
        if (shipToCompanyId.length() >0)
        {
          shipToLocCountQuery +=" and COMPANY_ID = '"+shipToCompanyId+"'";
        }
        int test_number=DbHelpers.countQuery( db,shipToLocCountQuery );

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

    if ( foundsupplier && invengrp.length() > 0 )
    {
      buildsendSupplier( MfgID1,shipToCompanyId );
      return;
    }

    nematid_Servlet=bundle.getString( "PURCHASE_ORDER_SHIPTO" );
    out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Ship To","poshipto.js",false ) );
    out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    out.println( "<!--\n" );
    out.println( "function ShowSearch(name,entered)\n" );
    out.println( "{\n" );
    out.println( " with (entered)\n" );
    out.println( " {\n" );
    out.println( " loc = \"" + nematid_Servlet + "&Action=\" + name.toString() + \"&SearchString=\";\n" );
    out.println( " loc = loc + window.document.SupplierLike.SearchString.value;\n" );
    out.println( " loc = loc + \"&shiptoID=\"; \n" );
		out.println( "var suppid  =  document.getElementById(\"supplierid\");\n" );
		out.println( "var supplierrep = suppid.value;\n" );
		out.println( "supplierrep = supplierrep.replace(/&/gi, \"%26\");\n" );
		out.println( "supplierrep = supplierrep.replace(/#/gi, \"%23\");\n" );
    out.println( "loc = loc + supplierrep;\n" );
    //out.println( " loc = loc + window.document.SupplierLike.supplierid.value;\n" );
    out.println( " loc = loc + \"&shiptocompanyid=" + shipToCompanyId + "\"; \n" );
    out.println( " loc = loc + \"&invengrp=" + invengrp + "\"; \n" );
    out.println( " loc = loc + \"&attachedpr=\" + opener.document.getElementById(\"attachedpr\").value; \n" );
    out.println( " }\n" );
    out.println( "  if (name.toString() == \"okupdate\")\n" );
    out.println( "  {\n" );
    out.println( "   eval( testmfgid = \"window.document.SupplierLike.supplierid\")\n" );
    out.println( "   if ( (eval(testmfgid.toString())).value.length > 0 )\n" );
    out.println( "   { \n" );
    out.println( "    sendShipto(entered);\n" );
	if (invengrp.length() == 0)
	{
	  out.println( "    window.location.replace(loc);\n" );
	}
	else
	{
	 out.print("var suppid  =  opener.document.getElementById(\"supplierid\");\n");
	 out.print("var validsupplier  =  opener.document.getElementById(\"validsupplier\");\n");
	 out.print("if (suppid.value.trim().length > 0 && validsupplier.value.trim() == \"Yes\")\n");
	 out.print("{\n");
	 out.print("	 opener.getSupplier();\n");
	 out.print("}\n");
	 out.print("window.close();\n");
	}
    out.println( "   }\n" );
    out.println( "  }\n" );
    out.println( " else \n" );
    out.println( " {\n" );
    out.println( "  window.location.replace(loc);\n" );
    out.println( " }\n" );
    out.println( " }\n" );
    out.println( "// -->\n </SCRIPT>\n" );


    out.println( "<BODY onload=\"javascript:window.resizeTo(700,420)\">\n" );
    out.println( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" ID=\"shiptodesc\" NAME=\"shiptodesc\" VALUE=\"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" ID=\"shiptocompanyid\" NAME=\"shiptocompanyid\" VALUE=\"\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" ID=\"shipToFacilityId\" NAME=\"shipToFacilityId\" VALUE=\"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" ID=\"addline1\" NAME=\"addline1\" VALUE=\"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" ID=\"addline2\" NAME=\"addline2\" VALUE=\"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" ID=\"addline3\" NAME=\"addline3\" VALUE=\"\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" ID=\"addline4\" NAME=\"addline4\" VALUE=\"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" ID=\"HubName\" NAME=\"HubName\" VALUE=\"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" ID=\"hubFullName\" NAME=\"hubFullName\" VALUE=\"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" NAME=\"invengrp\" ID=\"invengrp\" VALUE=\"" + invengrp + "\">\n" );
    out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
    out.println( "<TR>\n" );
    out.println( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text:</B></TD>\n" );
    if ( "Search".equalsIgnoreCase( SearchFlag ) )
    {
      out.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"SearchString\" name=\"SearchString\" value=\"" + SearchString + "\" SIZE=\"40\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"Search\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n" );
    }
    else
    {
      out.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"SearchString\" name=\"SearchString\" value=\"" + MfgID1 + "\" SIZE=\"40\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"Search\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n" );
    }

    out.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Ship To Id:</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" id=\"supplierid\" name=\"supplierid\" value=\"\" SIZE=\"25\" readonly>\n" );
    out.println( "</TR>\n" );
    out.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
    out.println( "<TR>\n" );
    out.println( "<TH WIDTH=\"15%\"><B>Ship To Id</B></FONT></TH>\n" );
    out.println( "<TH WIDTH=\"35%\"><B>Ship To Description</B></FONT></TH>\n" );
    out.println( "<TH WIDTH=\"10%\"><B>Facility ID</B></FONT></TH>\n" );
    out.println( "<TH WIDTH=\"10%\"><B>Company ID</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"20%\"><B>Address</B></FONT></TH>\n" );
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
	  SearchString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
	  String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"SEARCH" );
	  query+="select x.*, fx_address(company_id,location_id) ADDRESS from ship_to_location_view x where "+wherese+"";

      /*query+="select * from ship_to_location_view where lower(LOCATION_ID||LOCATION_DESC||COMPANY_ID||APPLICATION) like  lower('%" +
         BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString ) + "%') order by LOCATION_ID asc";*/
    }
    else
    {
	  MfgID1 = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( MfgID1 );
	  String wherese=radian.web.logicalSearchhelp.doLogicM( MfgID1,"SEARCH" );

	  query+="select x.*, fx_address(company_id,location_id) ADDRESS from ship_to_location_view x where "+wherese+"";

      /*query+="select * from ship_to_location_view where lower(LOCATION_ID||LOCATION_DESC||COMPANY_ID||APPLICATION) like lower('%" +
         BothHelpObjs.changeSingleQuotetoTwoSingleQuote( MfgID1 ) + "%') order by LOCATION_ID asc ";*/
    }
    if (shipToCompanyId.length() >0)
    {
        query +=" and COMPANY_ID = '"+shipToCompanyId+"'";
    }
    query+=" order by LOCATION_ID asc";

    if ( ( ! ( MfgID1.trim().length() > 0 ) ) && ( !"Search".equalsIgnoreCase( SearchFlag ) ) )
    {

    }
    else
    {
      try
      {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();
        //System.out.print( "Finished The Querry\n " );

        while ( rs.next() )
        {
          totalrecords+=1;
          count+=1;

          String location_id=rs.getString( "LOCATION_ID" ) == null ? "" : rs.getString( "LOCATION_ID" ).trim();
          String country_abbrev=rs.getString( "COUNTRY_ABBREV" ) == null ? "" : rs.getString( "COUNTRY_ABBREV" ).trim();
          String state_abbrev=rs.getString( "STATE_ABBREV" ) == null ? "" : rs.getString( "STATE_ABBREV" ).trim();
          String address_line_1=rs.getString( "ADDRESS_LINE_1" ) == null ? "" : rs.getString( "ADDRESS_LINE_1" ).trim();
          String address_line_2=rs.getString( "ADDRESS_LINE_2" ) == null ? "" : rs.getString( "ADDRESS_LINE_2" ).trim();
          String address_line_3=rs.getString( "ADDRESS_LINE_3" ) == null ? "" : rs.getString( "ADDRESS_LINE_3" ).trim();
          String city=rs.getString( "CITY" ) == null ? "" : rs.getString( "CITY" ).trim();
          String zip=rs.getString( "ZIP" ) == null ? "" : rs.getString( "ZIP" ).trim();
          String location_desc=rs.getString( "LOCATION_DESC" ) == null ? "" : rs.getString( "LOCATION_DESC" ).trim();
          //String client_location_code = rs.getString("CLIENT_LOCATION_CODE")==null?"":rs.getString("CLIENT_LOCATION_CODE").trim();
          String facility_id=rs.getString( "FACILITY_ID" ) == null ? "" : rs.getString( "FACILITY_ID" ).trim();
          String company_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
		  String address=rs.getString( "ADDRESS" ) == null ? "" : rs.getString( "ADDRESS" ).trim();

          String location_id1=HelpObjs.findReplace( location_id,"#","%23" );
		  location_id1=HelpObjs.findReplace( location_id,"&","%26" );
          location_id1=HelpObjs.findReplace( location_id,"\\","%2F" );
          String hubName="";

          String Color=" ";
          if ( count % 2 == 0 )
          {
            Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }
          else
          {
            Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }

          out.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('" + HelpObjs.addescapesForJavascript(location_id1) + "','" + HelpObjs.addescapesForJavascript( location_desc ) + "','" +
                       HelpObjs.addescapesForJavascript( address_line_1 ) + "','" + HelpObjs.addescapesForJavascript( address_line_2 ) + "','" + HelpObjs.addescapesForJavascript( address_line_3 ) + "','" +
                       HelpObjs.addescapesForJavascript( city + ", " + state_abbrev + " " + zip + " " + country_abbrev ) + "','" + company_id + "','" +
                       branchPlant + "','" + hubName + "','"+facility_id+"')\" BORDERCOLOR=\"black\">\n" );
          out.println( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
          out.println( location_id );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + " WIDTH=\"35%\" ALIGN=\"LEFT\">" );
          out.println( location_desc );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
          out.println( facility_id );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
          if (company_id.equalsIgnoreCase("Radian"))
          {
           out.println("Haas TCM");
          }
          else
          {
          out.println(company_id);
          }
          out.println( "</TD>\n" );
		  out.println( "<TD " + Color + " WIDTH=\"20%\" ALIGN=\"LEFT\">" );
		  out.println( HelpObjs.addescapesForJavascript(address) );
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
    out.println( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"okupdate\" name=\"okupdate\" value=\"Ok\" OnClick=\"ShowSearch('okupdate',this)\" type=\"button\">\n" );
    out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"Cancel\" name=\"Cancel\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    out.println( "</FORM></BODY></HTML>\n" );

    return;
  }
}