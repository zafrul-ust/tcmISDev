package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */


public class addItemtoHubCount
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
	private boolean intcmIsApplication = false;
	private ResourceLibrary res = null; // res means resource.

	public addItemtoHubCount(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }
     public void doResult(HttpServletRequest request, HttpServletResponse response)
          throws ServletException,  IOException
    {
	  PrintWriter out=response.getWriter();
	  response.setContentType( "text/html" );
	  HttpSession session=request.getSession();
	  res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));
	  String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		intcmIsApplication = false;
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    String invengrp=request.getParameter( "invengrp" );
    if ( invengrp == null )
      invengrp="";
    invengrp = invengrp.trim();

	String branch_plant=request.getParameter( "HubName" );
	if ( branch_plant == null )
	  branch_plant="";
	branch_plant=branch_plant.trim();

    String countId=request.getParameter( "countId" );
    if ( countId == null )
      countId="";
    countId = countId.trim();

   String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action = Action.trim();

	String itemId=request.getParameter( "itemId" );
	if ( itemId == null )
	  itemId="";
	itemId=itemId.trim();

	String SearchString=request.getParameter( "SearchString" );
	if ( SearchString == null )
	  SearchString="";
	SearchString=SearchString.trim();

	Hashtable hub_list_set=new Hashtable();
	hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );
	Hashtable initialinvData = new Hashtable ();

	Hashtable hub_list1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
	if (hub_list1.size() > 0)
	{
	 try {
		initialinvData = radian.web.poHelpObj.getinvgrpInitialData(db, hub_list_set);
	 }
	 catch (Exception ex) {
	 }
	 session.setAttribute( "INVENGRP_DATA",initialinvData );
	}

	Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );

    if ("okupdate".equalsIgnoreCase(Action))
    {
    updateadditemId(invengrp,countId,itemId,personnelid,out);
    }
    else if ("Search".equalsIgnoreCase(Action))
    {
    buildLikeSupplier("Search",invengrp,SearchString,out,branch_plant,iniinvendata);
    }
    else
    {
    buildLikeSupplier("",invengrp,SearchString,out,branch_plant,iniinvendata);
    }
    out.close();
  }

  public void updateadditemId( String invengrp,String countId,String itemId,String personneld,PrintWriter poout )
  {
	//StringBuffer Msgn=new StringBuffer();
	Connection connect1;
    CallableStatement cstmt=null;
	boolean result=false;

	poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.additemtocount","hubitemcount.js",intcmIsApplication,res ) );
	poout.println( "<BODY><BR><BR><BR><BR>\n" );
	String message = "";

	try
	{
	  connect1=db.getConnection();
	  cstmt=connect1.prepareCall( "{call p_ig_count_item_insert(?,?,?)}" );

	  cstmt.setString( 1,invengrp );
	  cstmt.setInt( 2,Integer.parseInt( itemId ) );

	  cstmt.registerOutParameter( 3,Types.VARCHAR );
	  cstmt.execute();

	  message=cstmt.getString( 3 );

	  if (message !=null)
	  {
		 result=false;
	  }
	  else
	  {
		 result=true;
	  }
	  connect1.commit();
	  if ( cstmt != null )
	  {
		cstmt.close();
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  radian.web.emailHelpObj.senddatabaseHelpemails( "Error calling p_ig_count_item_insert in Item Count SD Page",
													  "Error calling p_ig_count_item_insert\nError Msg:\n" + e.getMessage() + "\n invengrp:" +
													  invengrp +  "  Item ID  " + itemId + " Count Id  " + countId + "  Personnel ID  " + personneld + "" );
	  result=false;
	}

	poout.println( "<FORM METHOD=\"POST\" name=\"MaterialLike\" action=\"" +  radian.web.tcmisResourceLoader.getadditem2Hubcounturl() + "Session=Update\">\n" );
	if (result)
	{   
	poout.println( "<FONT FACE=\"Arial\" SIZE=\"3\">"+" "+res.getString("label.successaddcount")+" "+itemId+" "+"<BR><BR>"+res.getString("label.pleaserefreshsearch")+"</FONT>" );
	} 
	else
	{
	  poout.println( "<FONT FACE=\"Arial\" SIZE=\"3\">"+res.getString("label.erroradditemid")+" : "+itemId+"</FONT>" );
	  poout.println( "<FONT FACE=\"Arial\" SIZE=\"3\">" + message + "<BR><BR>"+res.getString("label.pleasetryagain")+"</FONT>" );
	}
	poout.println("\n<BR><BR><BR><BR><CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\""+res.getString("label.ok")+"\" OnClick=\"cancel()\" type=\"button\">\n");
	//poout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
	poout.println("</BODY></HTML>\n");

	return;
   }

   public void buildLikeSupplier( String SearchFlag,String selinvengrp,String SearchString,PrintWriter poout, String hub_branch_id,Hashtable iniinvendata )
   {
     //StringBuffer Msgl=new StringBuffer();
     DBResultSet dbrs=null;
     ResultSet rs=null;

     poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.additemtocount","hubitemcount.js",intcmIsApplication,res ) );
	 /*poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
     poout.println( "<!--\n" );
     poout.println( "function ShowSearch(name,entered)\n" );
     poout.println( "{\n" );
     poout.println( " with (entered)\n" );
     poout.println( " {\n" );
     poout.println( " loc = \"" +  radian.web.tcmisResourceLoader.getadditem2Hubcounturl() + "&Action=\" + name.toString() + \"&SearchString=\";\n" );
     poout.println( " loc = loc + window.document.additem2count.SearchString.value;\n" );
     poout.println( " loc = loc + \"&itemId=\"; \n" );
     poout.println( " loc = loc + window.document.additem2count.itemId.value;\n" );
	 poout.println( " loc = loc + \"&invengrp=\"; \n" );
     poout.println( " loc = loc + window.document.additem2count.invengrp.value;\n" );
     //poout.println( " loc = loc + \"&invengrp=" + invengrp + "\"; \n" );
     poout.println( " loc = loc + \"&HubName=" + hub_branch_id + "\"; \n" );
     poout.println( " }\n" );
     //poout.println(" alert(loc);\n");
     poout.println( "  window.location.replace(loc);\n" );
     poout.println( " }\n" );
     poout.println( "// -->\n </SCRIPT>\n" );*/
     poout.println( "<BODY>\n" );
     poout.println( "<FORM METHOD=\"POST\" name=\"additem2count\" action=\"" +  radian.web.tcmisResourceLoader.getadditem2Hubcounturl()  + "Session=Update\">\n" );
     //poout.println( "<INPUT TYPE=\"hidden\" NAME=\"invengrp\" VALUE=\"" + invengrp + "\">\n" );
	 poout.println( "<INPUT TYPE=\"hidden\" NAME=\"HubName\" VALUE=\"" + hub_branch_id + "\">\n" );
     poout.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	 poout.println( "<TR>\n" );
	 poout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\""+res.getString("label.search")+"\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
     poout.println("<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\""+HelpObjs.addescapesForJavascript(SearchString)+"\" SIZE=\"30\"></TD>\n");
     poout.println( "</TR>\n" );
     poout.println( "<TR><TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>"+res.getString("label.itemid")+":</B></FONT></TD><TD WIDTH=\"40%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"itemId\" value=\"\" SIZE=\"8\" readonly>\n" );

	poout.print( "<TD HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" nowrap>\n" );
	poout.print( "<B>"+res.getString("label.invgrp")+":</B>&nbsp;\n" );
	poout.print( "</TD>\n" );
	poout.print( "<TD WIDTH=\"10%\">\n" );
	poout.print( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n" );
	//out.print( "<option value=\"All\">Please Select</option>\n" );

	Hashtable fh= ( Hashtable ) iniinvendata.get( hub_branch_id );
	Vector invidV= ( Vector ) fh.get( "INVENTORY_GROUP" );
	Vector invidName= ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );

	for ( int i=0; i < invidV.size(); i++ )
	{
	  String preSelect="";
	  String wacid= ( String ) invidV.elementAt( i );
	  String invgName= ( String ) invidName.elementAt( i );

	  if ( selinvengrp.equalsIgnoreCase( wacid ) )
	  {
		preSelect="selected";
	  }
	  poout.print( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + "</option>\n" );
	}
	poout.print( "</SELECT>\n" );
	poout.print( "</TD>\n" );

	 /*poout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><B>Inventory Group:</B></TD>\n");
	 poout.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\">"+invengrp+"</TD>\n");*/
	 poout.println( "</TR>\n" );
     poout.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
     poout.println( "<TR>\n" );
     poout.println( "<TH WIDTH=\"5%\"><B>"+res.getString("label.itemid")+"</B></FONT></TH>\n" );
     poout.println( "<TH WIDTH=\"25%\"><B>"+res.getString("label.itemdescription")+"</B></FONT></TH>\n" );
     poout.println( "</TR></TABLE>\n" );

     //open scrolling table
     poout.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
     poout.println( "<TBODY>\n" );
     poout.println( "<TR>\n" );
     poout.println( "<TD VALIGN=\"TOP\">\n" );
     poout.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );

     //Write code to insert rows here
     poout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

     int totalrecords=0;
     int count=0;
     String query="";
	 if ( "Search".equalsIgnoreCase( SearchFlag ) )
	 {
	   query+="select item_id, item_desc from ig_item_search_view where search_string like lower('%" + SearchString + "%') ";
	   if ( selinvengrp.length() > 0 && !"All".equalsIgnoreCase( selinvengrp ) )
	   {
		 query+="and inventory_group = '" + selinvengrp + "'";
	   }

	   try
	   {
		 dbrs=db.doQuery( query );
		 rs=dbrs.getResultSet();

		 while ( rs.next() )
		 {
		   totalrecords+=1;
		   count+=1;

		   String item_id=rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ).trim();
		   String item_desc=rs.getString( "ITEM_DESC" ) == null ? "" : rs.getString( "ITEM_DESC" ).trim();

           String Color=" ";
           if ( count % 2 == 0 )
           {
             Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
           }
           else
           {
             Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
           }

           poout.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"additemID('" + item_id + "')\" BORDERCOLOR=\"black\">\n" );
           poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
           poout.println( item_id );
           poout.println( "</TD>\n" );
           poout.println( "<TD " + Color + " WIDTH=\"25%\" ALIGN=\"LEFT\">" );
           poout.println( item_desc );
           poout.println( "</TD>\n" );
           poout.println( "</TR>\n" );
         }
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         poout.println( res.getString("label.search") );
         poout.println( "</BODY>\n</HTML>\n" );
         return;
       }
       finally
       {
         if (dbrs!= null) {dbrs.close();}
       }

     }

     if ( totalrecords == 0 )
     {
       poout.println( "<TR><TD>"+res.getString("label.norecordfound")+"</TD></TR>\n" );
     }
     poout.println( "</TABLE>\n" );

     //close scrolling table
     poout.println( "</DIV>\n" );
     poout.println( "</TD>\n" );
     poout.println( "</TR>\n" );
     poout.println( "</TBODY>\n" );
     poout.println( "</TABLE>\n" );
     poout.println( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\""+res.getString("label.ok")+"\" OnClick=\"return ShowSearch(name,this)\" type=\"button\">\n" );
     poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"searchlike\" value=\""+res.getString("label.cancel")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
     poout.println( "</FORM></BODY></HTML>\n" );

     return;
   }
}