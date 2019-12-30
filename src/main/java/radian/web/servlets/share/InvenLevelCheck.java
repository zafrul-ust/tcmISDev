package radian.web.servlets.share;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import oracle.jdbc.OracleTypes;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 09-16-03 Fixed bug to not query letters for item_id and code cleanup
 * 10-06-03 Changed the directory where the excel file is stored
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 02-16-04 - Sorting Drop Downs
 * 03-02-04 - Fixed a bug to send Hub Number when calling Min Max Page
 * 04-29-04 - Getting Hub list from Hub_pref
 * 05-07-04 - Sorting the Hub Drop Down based on the prority in hub_pref table
 * 08-31-04 - Chaged to be able to search by Inventory Group. If a user does not have permissions on this page then he can only look at hubs in his hub_pref
 * 09-20-04 - Giving more options to search
 */

public class InvenLevelCheck
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private boolean allowupdate;
  //private String SelectedHubName="";
  private boolean intcmIsApplication = false;
  private static org.apache.log4j.Logger invenlog=org.apache.log4j.Logger.getLogger( InvenLevelCheck.class );

  public void setupdateStatus( boolean id )
  {
	this.allowupdate=id;
  }

  private boolean getupdateStatus() throws Exception
  {
	return this.allowupdate;
  }

  public InvenLevelCheck( ServerResourceBundle b,TcmISDBModel d )
  {
	bundle=b;
	db=d;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
	PrintWriter out=response.getWriter();
	response.setContentType( "text/html" );

	HttpSession invsession=request.getSession();

        PersonnelBean personnelBean = (PersonnelBean) invsession.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        intcmIsApplication = false;
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

	String branch_plant=invsession.getAttribute( "BRANCH_PLANT" ) == null ? "" : invsession.getAttribute( "BRANCH_PLANT" ).toString();
	String personnelid=invsession.getAttribute( "PERSONNELID" ) == null ? "" : invsession.getAttribute( "PERSONNELID" ).toString();
	Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)
	 invsession.getAttribute("hubInventoryGroupOvBeanCollection"));

	String invengrp=request.getParameter( "invengrp" );
	if ( invengrp == null )
	 invengrp="";
	invengrp=invengrp.trim();

	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess("hub");
	boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanCollection, branch_plant, invengrp);

	String User_Search=request.getParameter( "Search" );
	if ( User_Search == null )
	  User_Search="";
	User_Search=User_Search.trim();

	String User_Action=request.getParameter( "UserAction" );
	if ( User_Action == null )
	  User_Action="New";
	User_Action=User_Action.trim();

	User_Search=request.getParameter( "SearchField" );
	if ( User_Search == null )
	  User_Search="";
	User_Search=User_Search.trim();

	String searchlike=request.getParameter( "searchlike" );
	if ( searchlike == null )
	  searchlike="";
	searchlike=searchlike.trim();

	String searchfor=request.getParameter( "searchfor" );
	if ( searchfor == null )
	  searchfor="";
	searchfor=searchfor.trim();

	String showonlyissuedin=request.getParameter( "showonlyissuedin" );
	if ( showonlyissuedin == null )
	  showonlyissuedin="";
	showonlyissuedin=showonlyissuedin.trim();

	String issuedindays=request.getParameter( "issuedindays" );
	if ( issuedindays == null )
	  issuedindays="";
	issuedindays=issuedindays.trim();

	String showonlyonhand=request.getParameter( "showonlyonhand" );
	if ( showonlyonhand == null )
	  showonlyonhand="";
	showonlyonhand=showonlyonhand.trim();

	boolean createXls=User_Action.equalsIgnoreCase( "CreateExl" );

	try
	{
	  Hashtable hub_list_set=new Hashtable();
	  hub_list_set= ( Hashtable ) invsession.getAttribute( "HUB_PREF_LIST" );

	  if ( User_Action.equalsIgnoreCase( "CreateExl" ) )
	  {
		Vector data= ( Vector ) ( invsession.getAttribute( "INVENLEVELCHEKDATA" ) );
		out.println( buildXlsDetails( createXls,personnelid,data ) );
		data=null;
	  }
	  else if ( User_Action.equalsIgnoreCase( "Search" ) )
	  {
		//Hashtable hub_list_set= ( Hashtable ) invsession.getAttribute( "MIN_MAX_HUB_SET" );
		//Hashtable iniinvendata= ( Hashtable ) invsession.getAttribute( "INVENGRP_DATA" );
		Vector lnventory_invengrps= ( Vector ) invsession.getAttribute( "MIMMAX_ALLOWED_INVENGRP" );

		buildHeader( hub_list_set,branch_plant,searchlike,searchfor,hubInventoryGroupOvBeanCollection,invengrp,User_Search,showonlyissuedin, issuedindays,showonlyonhand,out );

		Vector data=new Vector();
		try
		{
		  data=getResult( iscollection,User_Search,branch_plant,searchlike,searchfor,invengrp,showonlyissuedin, issuedindays,showonlyonhand );
		}
		catch ( Exception e1 )
		{
		  e1.printStackTrace();
		  out.println( radian.web.HTMLHelpObj.printHTMLError() );
		}

		invsession.setAttribute( "INVENLEVELCHEKDATA",data );
		buildDetails( data,lnventory_invengrps,out );
		hub_list_set=null;
		data=null;
	  }
	  else
	  {
		String CompanyID=invsession.getAttribute( "COMPANYID" ) == null ? "" : invsession.getAttribute( "COMPANYID" ).toString();
		/*Hashtable hub_list_set=new Hashtable();
		hub_list_set=radian.web.HTMLHelpObj.createHubList( db,personnelid,CompanyID );*/
		//Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		//Hashtable initialinvData= new Hashtable ();
		//if (hub_list.size() > 0)
		//{
		  //hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,CompanyID );
		  //initialinvData=radian.web.poHelpObj.getinvgrpInitialData( db,hub_list_set );
		  //invsession.setAttribute( "INVENGRP_DATA",initialinvData );
		//}

		Vector lnventory_invengrps=new Vector();
		lnventory_invengrps=radian.web.HTMLHelpObj.createvgrpmemlist( db,personnelid,"MinMaxChg" );
		invsession.setAttribute( "MIMMAX_ALLOWED_INVENGRP",lnventory_invengrps );
		//invsession.setAttribute( "MIN_MAX_HUB_SET",hub_list_set );

		invsession.setAttribute( "MIN_MAX_DATA_LOADED","Yes" );

		buildHeader( hub_list_set,branch_plant,searchlike,searchfor,hubInventoryGroupOvBeanCollection,invengrp,User_Search,showonlyissuedin, issuedindays,showonlyonhand,out );
		out.println( "</body></html>\n" );
		hub_list_set=null;
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

  protected StringBuffer getTableHeader()
  {
	StringBuffer MsgTH=new StringBuffer();
	MsgTH.append( "<tr align=\"center\" valign=\"middle\">\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Inventory Group</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Item Id</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Stocking Method</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">Reorder Point</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">Stocking Level</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">On Hand</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">On Hand Value</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">Pickable and Not-Alloc</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">Non-Pickable and Not-Alloc</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">On Order and Not-Alloc</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">In Purchasing and Not-Alloc</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">Last Received</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">Issued Last 30</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">Issued 30-60</TH>\n" );
	MsgTH.append( "<TH width=\"2%\"  height=\"38\">Issued 60-90</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Min Unit Price</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Avg Unit Price</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Max Unit Price</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Avg Reorder Point Value</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Avg Stocking Level Value</TH>\n" );
	MsgTH.append( "<TH width=\"15%\"  height=\"38\">Item Desc</TH>\n" );

	/*MsgTH.append( "<TH width=\"2%\"  height=\"38\">Quantity Issued</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Unit Cost</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">On Hand Value</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Turns To Date</TH>\n" );
	MsgTH.append( "<TH width=\"5%\"  height=\"38\">Last Received</TH>\n" );*/

	MsgTH.append( "</tr>\n" );
	return MsgTH;
  }

  private void buildDetails( Vector data,Vector allowedigforinv,PrintWriter out )
  {
	//StringBuffer Msg=new StringBuffer();
	try
	{
	  int total=data.size();
	  if ( total == 0 )
	  {
		out.println( "No Records Found " );
		return ;
	  }

 	  out.println( "<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  int i=0; //used for detail lines
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
		  out.println( getTableHeader() );
		}

		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) data.elementAt( loop );

		String Color=" ";
		if ( i % 2 == 0 )
		{
		  Color="CLASS=\"white";
		}
		else
		{
		  Color="CLASS=\"blue";
		}

		String invengrp=BothHelpObjs.makeBlankFromNull( hD.get( "INVENTORY_GROUP" ).toString() );

		boolean editallowed=false;
		if ( allowedigforinv.contains( invengrp ) )
		{
		  editallowed=true;
		}

		String invenftoryGroup=hD.get( "INVENTORY_GROUP" ) == null ? "" : hD.get( "INVENTORY_GROUP" ).toString();
		String invenftoryGroupName=hD.get( "INVENTORY_GROUP_NAME" ) == null ? "" : hD.get( "INVENTORY_GROUP_NAME" ).toString();
		String item_id=hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString();

		out.println( "<tr align=\"center\"  valign=\"middle\">\n" );
		out.println( "<td " + Color + "l\" width=\"5%\"  height=\"38\">" + invenftoryGroupName + "</td>\n" );
		//out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" + item_id + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" );
		out.println( "<A HREF=\"javascript:showcrosstab('" + item_id + "','"+invenftoryGroup+"')\">" + item_id + "</A></td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" + hD.get( "STOCKING_METHOD" ).toString() + "</td>\n" );

		out.println( "<td " + Color + "\" width=\"2%\" height=\"38\">" );
		if (editallowed)
		{
		  out.println( "<A HREF=\"javascript:showMinMax('" + item_id + "','"+invenftoryGroup+"')\">" + hD.get( "REORDER_POINT" ).toString() + "</A></td>\n" );
		}
		else
		{
		  out.println( "" + hD.get( "REORDER_POINT" ).toString() + "</td>\n" );
		}

		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "STOCKING_LEVEL" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "ON_HAND" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "ON_HAND_VALUE" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "PICKABLE_MINUS_ALLOC" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "NONPICKABLE_MINUS_ALLOC" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "ONORDER_MINUS_ALLOC" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "INPURCHASING_MINUS_ALLOC" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "LAST_RECEIVED" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "ISSUED_LAST_30" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "ISSUED_30_60" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "ISSUED_60_90" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" + hD.get( "MIN_UNIT_PRICE" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" + hD.get( "AVG_UNIT_PRICE" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" + hD.get( "MAX_UNIT_PRICE" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "AVG_RP_VALUE" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "AVG_SL_VALUE" ).toString() + "</td>\n" );

		/*out.println( "<td " + Color + "\" width=\"2%\"  height=\"38\">" + hD.get( "QUANTITY_ISSUED" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  ALIGN=\"RIGHT\" height=\"38\">" + hD.get( "UNIT_COST" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  ALIGN=\"RIGHT\" height=\"38\">" + hD.get( "ON_HAND_VALUE" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" + hD.get( "TURNS_TO_DATE" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  height=\"38\">" + hD.get( "LAST_RECEIVED" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  ALIGN=\"RIGHT\" height=\"38\">" + hD.get( "MAX_VALUE" ).toString() + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\"  ALIGN=\"RIGHT\" height=\"38\">" + hD.get( "MIN_VALUE" ).toString() + "</td>\n" );*/
		out.println( "<td " + Color + "l\" width=\"15%\"  ALIGN=\"LEFT\" height=\"38\">" + hD.get( "ITEM_DESC" ).toString() + "</td>\n" );
		out.println( "</tr>\n" );
	  }

	  out.println( "</table>\n" );
	  out.println( "</body></html>\n" );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  out.println(radian.web.HTMLHelpObj.printHTMLError());
	  return;
	}

	return;
  }

  private StringBuffer buildXlsDetails( boolean forXls,String personnelID,Vector data )
  {
	//StringBuffer Msg=new StringBuffer();
	String url="";
	String file="";

	Random rand=new Random();
	int tmpReq=rand.nextInt();
	Integer tmpReqNum=new Integer( tmpReq );

	String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	String writefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();

	file=writefilepath + personnelID + tmpReqNum.toString() + "cabmgmt.csv";
	url=wwwHome + "reports/temp/" + personnelID + tmpReqNum.toString() + "cabmgmt.csv";

	try
	{
	  PrintWriter pw=new PrintWriter( new FileOutputStream( file ) );
	  int total=data.size();

	  if ( total == 0 )
	  {
		StringBuffer Msg=new StringBuffer();
		Msg.append( "No Records Found " );
		return Msg;
	  }

	  pw.print( "Inventory Group,Item Id,Stocking Method,Reorder Point,Stocking Level,On Hand,On Hand Value,Pickable and Not-Alloc,Non-Pickable and Not-Alloc,On Order and Not-Alloc,In Purchasing and Not-Alloc,Last Received,Issued Last 30,Issued 30-60,Issued 60-90,Min Unit Price,Avg Unit Price,Max Unit Price,Avg Reorder Point Value,Avg Stocking Level Value,Item Desc\n" );

	  int i=0; //used for detail lines
	  for ( int loop=0; loop < total; loop++ )
	  {
		i++;
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) data.elementAt( loop );

        pw.print( "\"" + hD.get( "INVENTORY_GROUP" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "ITEM_ID" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "STOCKING_METHOD" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "REORDER_POINT" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "STOCKING_LEVEL" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "ON_HAND" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "ON_HAND_VALUE" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "PICKABLE_MINUS_ALLOC" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "NONPICKABLE_MINUS_ALLOC" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "ONORDER_MINUS_ALLOC" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "INPURCHASING_MINUS_ALLOC" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "LAST_RECEIVED" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "ISSUED_LAST_30" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "ISSUED_30_60" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "ISSUED_60_90" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "MIN_UNIT_PRICE" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "AVG_UNIT_PRICE" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "MAX_UNIT_PRICE" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "AVG_RP_VALUE" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "AVG_SL_VALUE" ).toString() + "\"," );
		pw.print( "\"" + hD.get( "ITEM_DESC" ).toString() + "\"\n" );
	  }
	  pw.close();
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  return radian.web.HTMLHelpObj.printHTMLError();
	}

	  StringBuffer MsgSB=new StringBuffer();

	  if ( url.length() > 0 )
	  {
		MsgSB.append( "<HTML><HEAD>\n" );
		MsgSB.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
		MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
		MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
		MsgSB.append( "<TITLE>Container Label Generator</TITLE>\n" );
		MsgSB.append( "</HEAD>  \n" );
		MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
		MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
		MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Downloading Excel File</b></font><P></P><BR>\n" );
		MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n" );
		MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait ...</b></font><P></P><BR>\n" );
		MsgSB.append( "</CENTER>\n" );
		MsgSB.append( "</BODY></HTML>    \n" );
	  }
	  else
	  {
		MsgSB.append( "<HTML><HEAD>\n" );
		MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
		MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
		MsgSB.append( "<TITLE>Container Label Generator</TITLE>\n" );
		MsgSB.append( "</HEAD>  \n" );
		MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
		MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
		MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>An Error Occured </b></font><P></P><BR>\n" );
		MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n" );
		MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please try Again or contact Tech Center.</b></font><P></P><BR>\n" );
		MsgSB.append( "</CENTER>\n" );
		MsgSB.append( "</BODY></HTML>    \n" );
	  }
	  return MsgSB;
  }

  //Build HTML Header
  private void buildHeader( Hashtable hub_list_set,String hub_branch_id,String searchLike,String searchfor,Collection hubInventoryGroupOvBeanCollection,
									String selinvengrp,String search_text,String showonlyissuedin, String issuedindays,String showonlyonhand,PrintWriter out )
  {
	out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Inventory Level Management","invenlevelmgmt.js",intcmIsApplication ) );
	//out.println("<SCRIPT SRC=\"/clientscripts/invengrpdropdown.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");

	out.println( "</HEAD>  \n" );
	out.println( "<BODY>\n" );
	out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
	out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
	out.println( "</DIV>\n" );
	out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
	out.println( radian.web.HTMLHelpObj.PrintTitleBar( "Inventory Level Management\n" ) );
	Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
	if (hub_list.size() < 1)
	{
	  out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
	  return;
	}

	out.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n" );
	try {
	 out.println(radian.web.poHelpObj.getHubInventoryGroupJavaScript(
		hubInventoryGroupOvBeanCollection, true, false));
	}
	catch (Exception ex) {
	}
	//out.println( radian.web.poHelpObj.createhubinvgrjs( initialinvData ) );
	out.println( "// -->\n </SCRIPT>\n" );

	String Search_servlet=bundle.getString( "INVENTORY_LEVEL" );
	out.println( "<FORM METHOD=\"POST\" NAME=\"iteminventory\" ACTION=\"" + Search_servlet + "\">\n" );
	out.println( "<TABLE BORDER=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	out.println( "<TR VALIGN=\"MIDDLE\">\n" );

	//Hub
	out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "<B>Hub:</B>&nbsp;\n" );
	out.println( "</TD>\n" );
	out.println( "<TD WIDTH=\"20%\">\n" );
	out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" size=\"1\" OnChange=\"hubchanged(document.iteminventory.HubName)\">\n" );
	//Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
	Hashtable hub_prority_list= ( Hashtable ) ( hub_list_set.get( "HUB_PRORITY_LIST" ) );
	if ( hub_branch_id.trim().length() == 0 )
	{
	  //hub_branch_id=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
		hub_branch_id=radian.web.HTMLHelpObj.getFirstBranchPlant( hubInventoryGroupOvBeanCollection );
	}
	//out.println( radian.web.HTMLHelpObj.sorthashbyValue( hub_prority_list.entrySet(),hub_branch_id,hub_list ) );
	out.println(radian.web.HTMLHelpObj.getHubDropDown(hubInventoryGroupOvBeanCollection,hub_branch_id));
	out.println( "</SELECT>\n" );
	out.println( "</TD>\n" );

	// Search Text
	out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "<B>Search:</B>&nbsp;\n" );
	out.println( "</TD>\n" );

	out.println( "<TD WIDTH=\"30%\" COLSPAN=\"2\">\n" );
	out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" size=\"1\">\n" );
	Hashtable sortresult=new Hashtable();
	sortresult.put( "Item Id","ITEM_ID" );
	sortresult.put( "Item Desc","ITEM_DESC" );
	sortresult.put( "Part Number","CAT_PART_NO" );

	out.println( radian.web.HTMLHelpObj.getDropDown( sortresult,searchLike ) );
	out.println( "</SELECT>\n" );

	/*out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"searchfor\" size=\"1\">\n" );
	sortresult=new Hashtable();
	sortresult.put( "Like","Like" );
	sortresult.put( "Equals","Equals" );
	out.println( radian.web.HTMLHelpObj.getDropDown( sortresult,searchfor ) );
	out.println( "</SELECT>\n" );*/

	out.println( "&nbsp;&nbsp;<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\"" + search_text +
				"\" size=\"19\">\n" );
	out.println( "</TD>\n" );

	out.println( "<TD HEIGHT=45 WIDTH=\"5%\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );
	out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick=\"return actionValue(name,this)\" NAME=\"Search\">\n" );
	out.println( "</TD>\n" );

	out.println( "<TD HEIGHT=45 WIDTH=\"5%\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );
	out.println( "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create Exl File\" onclick= \"createxls(this)\" NAME=\"CreateExl\">\n" );
	out.println( "</TD>\n" );
	out.println( "</TR>\n" );

	out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	// Inventory Group
	out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "<B>Inv Grp:</B>&nbsp;\n" );
	out.println( "</TD>\n" );
	out.println( "<TD WIDTH=\"20%\">\n" );

	out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n" );
	out.println(radian.web.HTMLHelpObj.getInventoryGroupDropDown(hubInventoryGroupOvBeanCollection,hub_branch_id,selinvengrp,false));
/*
	Hashtable fh= ( Hashtable ) initialinvData.get( hub_branch_id );
	Vector invidV= ( Vector ) fh.get( "INVENTORY_GROUP" );
	Vector invidName= ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );
	//System.out.println( selinvengrp );
	for ( int i=0; i < invidV.size(); i++ )
	{
	  String preSelect="";
	  String wacid= ( String ) invidV.elementAt( i );
	  String invgName= ( String ) invidName.elementAt( i );

	  if ( selinvengrp.equalsIgnoreCase( wacid ) )
	  {
		preSelect="selected";
	  }
	  out.println( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + "</option>\n" );
	}*/
	out.println( "</SELECT>\n" );
	out.println( "</TD>\n" );

    //Items issue within the last x days
	out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "<INPUT type=\"checkbox\" name=\"showonlyissuedin\" value=\"Yes\" " + ( showonlyissuedin.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
	out.println( "</TD>\n" );
	out.println( "<TD WIDTH=\"30%\" COLSPAN=\"2\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
	out.println( "Show only items issued in the last \n" );
	out.println("<INPUT CLASS=\"HEADER\" type=\"text\" name=\"issuedindays\" value=\""+(issuedindays.length()<1?"":issuedindays)+"\" SIZE=\"2\" MAXLENGTH=\"5\">&nbsp;days\n");
	out.println( "</TD>\n" );

	out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "<INPUT type=\"checkbox\" name=\"showonlyonhand\" value=\"Yes\" " + ( showonlyonhand.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
	out.println( "</TD>\n" );

	out.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
	out.println( "Show Only On Hand Items\n" );
	out.println( "</TD>\n" );

	out.println( "</TR>\n" );
	out.println( "</TABLE>\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n" );
	//out.println( "<INPUT TYPE=\"hidden\" NAME=\"HubName\" VALUE=\"" + SelectedHubName + "\">\n" );
	out.println( "</FORM>\n" );

	return;
  }

  private Vector getResult( boolean iscollection,String searchstring,String branch_plant,String searchlike,String searchfor,String selinvengrp,String showonlyissuedin, String issuedindays,String showonlyonhand ) throws Exception
  {
	Vector Data=new Vector();
	Hashtable DataH=new Hashtable();
	DBResultSet dbrs=null;
	ResultSet rs=null;
	Connection connect1=null;
	CallableStatement cs=null;

	try
	{
	  connect1=db.getConnection();
	  cs=connect1.prepareCall( "{call PKG_LEVEL_MANAGEMENT.PR_LEVEL_MANAGEMENT(?,?,?,?,?,?,?,?,?)}" );
	  if ("All".equalsIgnoreCase(selinvengrp)){cs.setString( 1,"ALL" );}else{cs.setString( 1,selinvengrp );}
		if (iscollection) {cs.setString( 2,"Y" );} else {cs.setNull(2,java.sql.Types.VARCHAR);}
	  cs.setString( 3,branch_plant );
	  if (searchstring.length() > 0)
	  {
		cs.setString( 4,searchstring );
		cs.setString( 5,searchlike );
	  }
	  else
	  {
		cs.setNull(4,java.sql.Types.VARCHAR);
		cs.setNull(5,java.sql.Types.VARCHAR);
	  }

	  if ( showonlyonhand.equalsIgnoreCase( "Yes" ) )
	  {
		cs.setString( 6,showonlyonhand );
	  }
	  else
	  {
		cs.setNull( 6,java.sql.Types.VARCHAR );
	  }

	  if ( showonlyissuedin.equalsIgnoreCase( "Yes" ) && issuedindays.length() > 0 )
	  {
		cs.setInt( 7,Integer.parseInt( issuedindays ) );
	  }
	  else
	  {
		cs.setNull( 7,java.sql.Types.INTEGER );
	  }

	  cs.registerOutParameter( 8,OracleTypes.VARCHAR );
	  cs.registerOutParameter( 9,OracleTypes.VARCHAR );
	  cs.execute();

	  String errormsg=cs.getObject( 8 ) == null ? "" :cs.getObject( 8 ).toString();

	  String resultQuery=cs.getObject( 9 ).toString();
	  dbrs=db.doQuery( resultQuery );
	  rs=dbrs.getResultSet();

	  invenlog.debug("Error Msg from Procedure PR_LEVEL_MANAGEMENT -  "+errormsg+" searchstring "+searchstring+" searchlike  "+searchlike+"");
	  /*String query="select INVENTORY_GROUP, HUB,ITEM_ID,ITEM_DESC,REORDER_POINT,STOCKING_LEVEL,ON_HAND,QUANTITY_ISSUED,MAX_UNIT_PRICE,UNIT_COST,ON_HAND_VALUE,";
	  query+="TURNS_TO_DATE,to_char(LAST_RECEIVED,'mm/dd/yyyy hh24:mi') LAST_RECEIVED,MAX_VALUE,MIN_VALUE";
	  query+=" from inventory_level_check_view where HUB = " + branch_plant + " ";

	  if ( searchstring.trim().length() > 0 )
	  {
		if ( "Like".equalsIgnoreCase( searchfor ) )
		{
		  query+=" and lower(" + searchlike + ") like lower('%" + searchstring.trim() + "%') ";
		}
		else if ( "Equals".equalsIgnoreCase( searchfor ) )
		{
		  query+=" and " + searchlike + " = '" + searchstring.trim() + "' ";
		}
	  }

	  boolean flagforand=true;
	  if ( selinvengrp.length() > 0 && !"All".equalsIgnoreCase( selinvengrp ) )
	  {
		if ( flagforand )
		  query+=" and INVENTORY_GROUP ='" + selinvengrp + "' ";
		else
		{
		  query+=" INVENTORY_GROUP ='" + selinvengrp + "' ";
		}
	  }

	  dbrs=db.doQuery( query );
	  rs=dbrs.getResultSet();*/

   /*ResultSetMetaData rsMeta1 = rs.getMetaData();
   System.out.println("Finished The Querry\n "+rsMeta1.getColumnCount());
   for(int i =1; i<=rsMeta1.getColumnCount(); i++)
   {
   System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");

   //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",rs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":rs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
   //System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
   //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
   }*/

	  while ( rs.next() )
	  {
		DataH=new Hashtable();
		DataH.put("HUB",rs.getString("HUB")==null?"":rs.getString("HUB"));
		DataH.put("INVENTORY_GROUP",rs.getString("INVENTORY_GROUP")==null?"":rs.getString("INVENTORY_GROUP"));
		DataH.put("INVENTORY_GROUP_NAME",rs.getString("INVENTORY_GROUP_NAME")==null?"":rs.getString("INVENTORY_GROUP_NAME"));
		DataH.put("ITEM_ID",rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID"));
		DataH.put("LAST_RECEIVED",rs.getString("LAST_RECEIVED")==null?"":rs.getString("LAST_RECEIVED"));
		DataH.put("ON_HAND",rs.getString("ON_HAND")==null?"":rs.getString("ON_HAND"));
		DataH.put("ON_HAND_VALUE",rs.getString("ON_HAND_VALUE")==null?"":rs.getString("ON_HAND_VALUE"));
		DataH.put("AVG_UNIT_PRICE",rs.getString("AVG_UNIT_PRICE")==null?"":rs.getString("AVG_UNIT_PRICE"));
		DataH.put("MAX_UNIT_PRICE",rs.getString("MAX_UNIT_PRICE")==null?"":rs.getString("MAX_UNIT_PRICE"));
		DataH.put("MIN_UNIT_PRICE",rs.getString("MIN_UNIT_PRICE")==null?"":rs.getString("MIN_UNIT_PRICE"));
		DataH.put("REORDER_POINT",rs.getString("REORDER_POINT")==null?"":rs.getString("REORDER_POINT"));
		DataH.put("STOCKING_LEVEL",rs.getString("STOCKING_LEVEL")==null?"":rs.getString("STOCKING_LEVEL"));
		DataH.put("ISSUED_LAST_30",rs.getString("ISSUED_LAST_30")==null?"":rs.getString("ISSUED_LAST_30"));
		DataH.put("ISSUED_30_60",rs.getString("ISSUED_30_60")==null?"":rs.getString("ISSUED_30_60"));
		DataH.put("ISSUED_60_90",rs.getString("ISSUED_60_90")==null?"":rs.getString("ISSUED_60_90"));
		DataH.put("AVG_RP_VALUE",rs.getString("AVG_RP_VALUE")==null?"":rs.getString("AVG_RP_VALUE"));
		DataH.put("AVG_SL_VALUE",rs.getString("AVG_SL_VALUE")==null?"":rs.getString("AVG_SL_VALUE"));
		DataH.put("ITEM_DESC",rs.getString("ITEM_DESC")==null?"":rs.getString("ITEM_DESC"));
		DataH.put("STOCKING_METHOD",rs.getString("STOCKING_METHOD")==null?"":rs.getString("STOCKING_METHOD"));
		DataH.put("PICKABLE_MINUS_ALLOC",rs.getString("PICKABLE_MINUS_ALLOC")==null?"":rs.getString("PICKABLE_MINUS_ALLOC"));
		DataH.put("NONPICKABLE_MINUS_ALLOC",rs.getString("NONPICKABLE_MINUS_ALLOC")==null?"":rs.getString("NONPICKABLE_MINUS_ALLOC"));
		DataH.put("ONORDER_MINUS_ALLOC",rs.getString("ONORDER_MINUS_ALLOC")==null?"":rs.getString("ONORDER_MINUS_ALLOC"));
		DataH.put("INPURCHASING_MINUS_ALLOC",rs.getString("INPURCHASING_MINUS_ALLOC")==null?"":rs.getString("INPURCHASING_MINUS_ALLOC"));

		Data.addElement( DataH );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  throw e;
	}
	finally
	{
	  if ( dbrs != null ){dbrs.close();}
	  if ( cs != null ){ try{cs.close();}catch ( SQLException se ){}
	}

	}
	return Data;
  }
}