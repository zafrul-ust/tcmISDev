package radian.web.servlets.share;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.OracleTypes;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput
 * @version
 * This class provides receiving functionality for the client's receiving servlet.
 * Clients session  are oraganized as follows
 * - New
 * 11-19-02
 * Adding Supplier Name and Original Promised Date to the page
 * 01-09-02
 * Removed space between mr and line when creating Excel file and po and line have diff columns
 * 01-13-03
 * Adding red color if the mr is critical
 * 03-03-03
 * Added Sort by facility
 * 03-17-03
 * The first line if it is critical was not being painted red
 * 06-03-03
 * Clean up code and changed vv_lot_status
 * 06-27-03
 * Added inventory group to the display
 * 07-31-03 - Giving a more appropriate error message when they enter MR-Line in the MR field which causes a number format exception
 * 09-21-03 - Showing Dropship.
 * 11-26-03 - Painintg yellow receipt that are allocated but are not pickable
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 02-16-04 - Sorting Drop Downs
 * 03-01-04 - Replasing line break with a space for the excel file. Also accessing report directory from resource bundle
 * 05-07-04 - Sorting the Hub Drop Down based on the prority in hub_pref table
 * 05-13-04 - Showing Work Area in the results
 * 05-20-04 - If there is a Hub_pref selecting the default Hub as that Hub
 * 11-01-04 - Moved the OnTime date next to Needed date to give more details rather than just the earliest on time date. Also added the mr release
 *            date to the excel file.
 * 06-07-05 - Corrected a nullpointer Exception
 */

public class AllocationAnalysis
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private boolean allowupdate;
  private boolean intcmIsApplication = false;

  public AllocationAnalysis( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  public void setupdateStatus( boolean id )
  {
    this.allowupdate=id;
  }

  private boolean getupdateStatus() throws Exception
  {
    return this.allowupdate;
  }

  //Process the HTTP Post request passed from whoever called it
  public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession session ) throws ServletException,IOException
  {
	PrintWriter out=response.getWriter();
	response.setContentType( "text/html" );

        PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        intcmIsApplication = false;
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

	//String branch_plant=session.getAttribute( "BRANCH_PLANT" ) == null ? "" : session.getAttribute( "BRANCH_PLANT" ).toString();
	String personnelid=session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
	String CompanyID=session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();

	String User_Action=null;
	User_Action=request.getParameter( "UserAction" );
	if ( User_Action == null )
	  User_Action="New";
	String lotstatus=request.getParameter( "selectElementStatus" );
	if ( lotstatus == null )
	  lotstatus="";
	String within=request.getParameter( "within" );
	if ( ( within == null ) || ( within.trim().length() == 0 ) )
	  within="7";
	within=within.trim();
	String mrnum=request.getParameter( "mrnum" );
	if ( mrnum == null )
	  mrnum="";
	mrnum=mrnum.trim();
	String progressstat=request.getParameter( "progressstat" );
	if ( progressstat == null )
	  progressstat="";
	String itemid=request.getParameter( "itemid" );
	if ( itemid == null )
	  itemid="";
	itemid=itemid.trim();
	String sortBy=request.getParameter( "sortBy" );
	if ( sortBy == null )
	  sortBy="REQUIRED_DATETIME";

	String shearchlike=request.getParameter( "itemormrsearch" );
	if ( shearchlike == null )
	  shearchlike="ITEM_ID";
	shearchlike=shearchlike.trim();

	String searchdate=request.getParameter( "searchdate" );
	if ( searchdate == null )
	  searchdate="NEEDED";
	searchdate=searchdate.trim();

	String invengrp=request.getParameter( "invengrp" );
	if ( invengrp == null )
	  invengrp="";
	invengrp=invengrp.trim();

	String facility=request.getParameter( "FacName" );
	if ( facility == null )
	  facility="";
	facility=facility.trim();

	String branch_plant=request.getParameter( "HubName" );
	if ( branch_plant == null )
	  branch_plant="";
	branch_plant=branch_plant.trim();

    try
	{
		 Map warehouseInfo= ( Map ) session.getAttribute( "warehouseInfo" );
		 if (warehouseInfo == null)
		 {
				 //PersonnelBean personnelBean=session.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) session.getAttribute( "personnelBean" );
				 radian.web.HTMLHelpObj.LoginSetup( session,db,""+personnelBean.getPersonnelId()+"",CompanyID,personnelBean.getLogonId());
	   }
	  Hashtable hub_list_set = new Hashtable();
	  hub_list_set= (Hashtable)session.getAttribute("HUB_PREF_LIST");

	  if ( User_Action.equalsIgnoreCase( "New" ) )
	  {
		//Hashtable hub_list_set=radian.web.HTMLHelpObj.createHubList( db,personnelid,CompanyID );
		Hashtable hub_list1 = (Hashtable)(hub_list_set.get("HUB_LIST"));
		Hashtable initialinvData = new Hashtable();
		String companyArrayJs = "";
		if (hub_list1.size() > 0)
		{
		  initialinvData= radian.web.poHelpObj.getinvgrpInitialData(db,hub_list_set);
		  session.setAttribute( "INVENGRP_DATA",initialinvData);

		  companyArrayJs=radian.web.HTMLHelpObj.getHubJs( hub_list_set,session ).toString();
		  session.setAttribute( "CATALOGS_JS",companyArrayJs );
		}

		Vector lot_status_set=new Vector();
		lot_status_set=radian.web.HTMLHelpObj.createItemStatusSet( db );
		session.setAttribute( "STATUS_SET",lot_status_set );
		session.setAttribute( "HUB_SET",hub_list_set );
		//session.setAttribute( "HUB_PREF_SET",hub_pref_list );

		buildHeader( branch_plant,hub_list_set,lot_status_set,lotstatus,within,mrnum,progressstat,itemid,sortBy,shearchlike,searchdate,invengrp,companyArrayJs,facility,initialinvData,out,session );
		out.println( radian.web.HTMLHelpObj.printHTMLSelect() );

		hub_list_set=null;
		lot_status_set=null;
		//hub_pref_list=null;
	  }
	  else if ( User_Action.equalsIgnoreCase( "Search" ) )
	  {
		//Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
		//Hashtable hub_pref_list= ( Hashtable ) session.getAttribute( "HUB_PREF_SET" );
		Vector lot_status_set= ( Vector ) session.getAttribute( "STATUS_SET" );
		Hashtable initialinvData= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
		String companyarray= ( String ) session.getAttribute( "CATALOGS_JS" );

		Vector searchdata=new Vector();
		Hashtable sum=new Hashtable();
		int count=0;
		String errmsg="";

		try
		{
		  searchdata=this.getSearchData( branch_plant,lotstatus,within,searchdate,progressstat,itemid,shearchlike,invengrp,facility,sortBy );
		  sum= ( Hashtable ) ( searchdata.elementAt( 0 ) );
		  count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
		}
		catch ( Exception ex )
		{
		  count=0;
		  errmsg=ex.getMessage() + "<BR>" + "Please check your input parameters. Do not enter MR-Line in the MR field.<BR>";
		}

		buildHeader( branch_plant,hub_list_set,lot_status_set,lotstatus,within,mrnum,progressstat,itemid,sortBy,shearchlike,searchdate,invengrp,companyarray,facility,initialinvData,out,session );
		if ( 0 == count )
		{
		  out.println( radian.web.HTMLHelpObj.printHTMLNoData( "" + errmsg + "<BR>No Items Found" ) );
		}
		else
		{
		  out.println( buildDetails( searchdata,"",out ) );
		} //when there are open orders for hub
		session.setAttribute( "ALLOCATION_DATA",searchdata );

		searchdata=null;
		hub_list_set=null;
		//hub_pref_list=null;
		lot_status_set=null;
	  }
	  else if ( User_Action.equalsIgnoreCase( "createxls" ) )
	  {
		//Vector searchdata=new Vector();
		Vector searchdata= ( Vector ) session.getAttribute( "ALLOCATION_DATA" );
		/*try
		{
		  searchdata=this.getSearchData( branch_plant,lotstatus,within,searchdate,progressstat,itemid,shearchlike,invengrp,facility,sortBy );
		}
		catch ( Exception ex )
		{
		}*/
		out.println( buildxlsDetails( searchdata,personnelid,out ) );
		searchdata=null;
	  }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      out.println( radian.web.HTMLHelpObj.printError( "Allocation Analysis",intcmIsApplication ) );
    }
	finally
	{
	  out.close();
	}
    return;
  }

  private Vector getSearchData( String BranchPlant,String wlotstatus,String wwithin,String searchdatelike,String wprogressstat,String itemmrnum,
								String searchlike,String invengrp,String facility,String sortby ) throws Exception
  {
    Vector Data=new Vector();
    Hashtable summary=new Hashtable();
    ResultSet rs=null;
    Connection connect1=null;
    CallableStatement cs=null;

    int count=0;
    summary.put( "TOTAL_NUMBER",new Integer( count ) );
    Data.addElement( summary );
    int num_rec=0;

  //System.out.println("BranchPlant "+BranchPlant+"  wlotstatus "+wlotstatus+"  searchdatelike "+searchdatelike+" wwithin "+wwithin+" searchlike "+searchlike+"  itemmrnum "+itemmrnum+"  wprogressstat "+wprogressstat+"  invengrp "+invengrp+"  facility "+facility+"");

  try
  {
	connect1=db.getConnection();
	cs=connect1.prepareCall( "{call pkg_open_order.pr_open_order(?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
	cs.setString( 1,BranchPlant );
	cs.setString( 2,wlotstatus );

	//cs.setInt( 3,Integer.parseInt( wwithin ) );
	if ( wwithin.trim().length() == 0 )
	{
	  cs.setNull( 3,java.sql.Types.NUMERIC );
	}
	else
	{
	  if ( "NEEDED".equalsIgnoreCase( searchdatelike ) )
	  {
		cs.setInt( 3,Integer.parseInt( wwithin.trim() ) );
	  }
	  else
	  {
		cs.setNull( 3,java.sql.Types.NUMERIC );
	  }
	}

	if ( itemmrnum.trim().length() == 0 )
	{
	  cs.setNull( 4,java.sql.Types.NUMERIC );
	}
	else
	{
	  if ( "MATERIAL_REQUEST".equalsIgnoreCase( searchlike ) )
	  {
		cs.setInt( 4,Integer.parseInt( itemmrnum.trim() ) );
	  }
	  else
	  {
		cs.setNull( 4,java.sql.Types.NUMERIC );
	  }
	}

	cs.setString( 5,wprogressstat );

	if ( itemmrnum.trim().length() == 0 )
	{
	  cs.setNull( 6,java.sql.Types.NUMERIC );
	}
	else
	{
	  if ( "ITEM_ID".equalsIgnoreCase( searchlike ) )
	  {
		cs.setInt( 6,Integer.parseInt( itemmrnum ) );
	  }
	  else
	  {
		cs.setNull( 6,java.sql.Types.NUMERIC );
	  }
	}

	if ( wwithin.trim().length() == 0 )
	{
	  cs.setNull( 7,java.sql.Types.NUMERIC );
	}
	else
	{
	  if ( "ONTIME".equalsIgnoreCase( searchdatelike ) )
	  {
		cs.setInt( 7,Integer.parseInt( wwithin.trim() ) );
	  }
	  else
	  {
		cs.setNull( 7,java.sql.Types.NUMERIC );
	  }
	}

	if ( invengrp.trim().length() > 0 && !"All".equalsIgnoreCase(invengrp) ){cs.setString( 8,invengrp );}else{cs.setNull( 8,java.sql.Types.VARCHAR );}
	if ( facility.trim().length() > 0 && !"All".equalsIgnoreCase(facility) ){cs.setString( 9,facility );}else{cs.setNull( 9,java.sql.Types.VARCHAR );}

      cs.setString( 10,sortby );
      cs.registerOutParameter( 11,OracleTypes.CURSOR );
      cs.registerOutParameter( 12,java.sql.Types.VARCHAR );
      cs.registerOutParameter( 13,java.sql.Types.VARCHAR );
      cs.execute();

      rs= ( ResultSet ) cs.getObject( 11 );

      while ( rs.next() )
      {
        num_rec++;

        Hashtable hD=new Hashtable();
        hD.put( "REQUESTOR_FIRST_NAME",rs.getString( "REQUESTOR_FIRST_NAME" ) == null ? "" : rs.getString( "REQUESTOR_FIRST_NAME" ) );
        hD.put( "REQUESTOR_LAST_NAME",rs.getString( "REQUESTOR_LAST_NAME" ) == null ? "" : rs.getString( "REQUESTOR_LAST_NAME" ) );
        hD.put( "REQUIRED_DATETIME",rs.getString( "REQUIRED_DATETIME" ) == null ? "" : rs.getString( "REQUIRED_DATETIME" ) );
        hD.put( "PR_NUMBER",rs.getString( "PR_NUMBER" ) == null ? "" : rs.getString( "PR_NUMBER" ) );
        hD.put( "LINE_ITEM",rs.getString( "LINE_ITEM" ) == null ? "" : rs.getString( "LINE_ITEM" ) );
        hD.put( "OPEN_QUANTITY",rs.getString( "OPEN_QUANTITY" ) == null ? "" : rs.getString( "OPEN_QUANTITY" ) );
        hD.put( "ALLOCATION_TYPE",rs.getString( "ALLOCATION_TYPE" ) == null ? "" : rs.getString( "ALLOCATION_TYPE" ) );
        hD.put( "REF_NO",rs.getString( "REF_NO" ) == null ? "" : rs.getString( "REF_NO" ) );
        hD.put( "REF_LINE",rs.getString( "REF_LINE" ) == null ? "" : rs.getString( "REF_LINE" ) );
        hD.put( "ALLOC_QUANTITY",rs.getString( "ALLOC_QUANTITY" ) == null ? "" : rs.getString( "ALLOC_QUANTITY" ) );
        hD.put( "REF_STATUS",rs.getString( "REF_STATUS" ) == null ? "" : rs.getString( "REF_STATUS" ) );
        hD.put( "REF_DATE",rs.getString( "REF_DATE" ) == null ? "" : rs.getString( "REF_DATE" ) );
        hD.put( "FAC_PART_NO",rs.getString( "FAC_PART_NO" ) == null ? "" : rs.getString( "FAC_PART_NO" ) );
        hD.put( "FACILITY_ID",rs.getString( "FACILITY_ID" ) == null ? "" : rs.getString( "FACILITY_ID" ) );
        hD.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
        hD.put( "ITEM_TYPE",rs.getString( "ITEM_TYPE" ) == null ? "" : rs.getString( "ITEM_TYPE" ) );
        hD.put( "QTY_ON_HAND",rs.getString( "QTY_ON_HAND" ) == null ? "" : rs.getString( "QTY_ON_HAND" ) );
        hD.put( "QTY_AVAILABLE",rs.getString( "QTY_AVAILABLE" ) == null ? "" : rs.getString( "QTY_AVAILABLE" ) );
				hD.put( "IG_QTY_ON_HAND",rs.getString( "IG_QTY_ON_HAND" ) == null ? "" : rs.getString( "IG_QTY_ON_HAND" ) );
        hD.put( "IG_QTY_AVAILABLE",rs.getString( "IG_QTY_AVAILABLE" ) == null ? "" : rs.getString( "IG_QTY_AVAILABLE" ) );
        hD.put( "SUPPLIER",rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ) );
        hD.put( "ORIGINAL_PROMISED_DATE",rs.getString( "ORIGINAL_PROMISED_DATE" ) == null ? "" : rs.getString( "ORIGINAL_PROMISED_DATE" ) );
        hD.put( "CRITICAL",rs.getString( "CRITICAL" ) == null ? "" : rs.getString( "CRITICAL" ) );
        hD.put( "NOTES",rs.getString( "NOTES" ) == null ? "" : rs.getString( "NOTES" ) );
        hD.put( "MR_NOTES",rs.getString( "MR_NOTES" ) == null ? "" : rs.getString( "MR_NOTES" ) );
        hD.put( "DELAY",rs.getString( "DELAY" ) == null ? "" : rs.getString( "DELAY" ) );
        hD.put( "SYSTEM_REQUIRED_DATETIME",rs.getString( "SYSTEM_REQUIRED_DATETIME" ) == null ? "" : rs.getString( "SYSTEM_REQUIRED_DATETIME" ) );
        hD.put( "INVENTORY_GROUP",rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ) );
				hD.put( "PICKABLE",rs.getString( "PICKABLE" ) == null ? "" : rs.getString( "PICKABLE" ) );
				hD.put( "APPLICATION",rs.getString( "APPLICATION" ) == null ? "" : rs.getString( "APPLICATION" ) );
				hD.put( "APPLICATION_DESC",rs.getString( "APPLICATION_DESC" ) == null ? "" : rs.getString( "APPLICATION_DESC" ) );
				hD.put( "RELEASE_DATE",rs.getString( "RELEASE_DATE" ) == null ? "" : rs.getString( "RELEASE_DATE" ) );
				hD.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? "" : rs.getString( "MFG_LOT" ) );
				hD.put( "HAZMAT_ID_MISSING",rs.getString( "HAZMAT_ID_MISSING" ) == null ? "" : rs.getString( "HAZMAT_ID_MISSING" ) );
				hD.put( "LOT_STATUS_AGE",rs.getString( "LOT_STATUS_AGE" ) == null ? "" : rs.getString( "LOT_STATUS_AGE" ) );

        Data.addElement( hD );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      if ( cs != null )
      {
        cs.close();
      }
      if ( rs != null )
      {
        rs.close();
      }
    }
    Hashtable recsum=new Hashtable();
    recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
    Data.setElementAt( recsum,0 );
    Data.trimToSize();
    return Data;
  }

  private void buildHeader( String hub,Hashtable hub_list_set,Vector lot_status,String slotstatus,String swithin,String smrnum,String sprogressstat,
							String itemid,String sortby,String searchLike,String searchdate,String selinvengrp,String companyArrayJs,String facility,Hashtable initialinvData,PrintWriter aout,HttpSession session )
  {
    try
    {
      aout.print( radian.web.HTMLHelpObj.printHTMLHeader( "Allocation Analysis","allocationanalysis.js",intcmIsApplication ) );
      aout.print( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );
      aout.print( "</HEAD>  \n" );
      aout.print( "<BODY>\n" );
      aout.print( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
      aout.print( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n" );
      aout.print( "</DIV>\n" );
      aout.print( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

      aout.print( radian.web.HTMLHelpObj.PrintTitleBar( "<B>Allocation Analysis</B>\n" ) );
	  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
	  if (hub_list.size() < 1)
	  {
		aout.println(radian.web.HTMLHelpObj.printMessageinTable("You don't have access to this page.<BR><BR>Contact tech support for more information."));
		return;
	  }
	  aout.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
	  aout.println( radian.web.poHelpObj.createhubinvgrjs(initialinvData) );
	  aout.println( companyArrayJs );
	  aout.println("// -->\n </SCRIPT>\n");

      aout.print( "<FORM method=\"POST\" NAME=\"allocationana\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString( "OPERATION_ANALYSIS" ) + "\">\n" );
      //start table #1
      aout.print( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      aout.print( "<TR>\n" );

      //Hub
      aout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      aout.print( "<B>Hub:</B>&nbsp;\n" );
      aout.print( "</TD>\n" );
      aout.print( "<TD WIDTH=\"15%\">\n" );
      //
      aout.print( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" OnChange=\"hubchanged(document.allocationana.HubName)\" size=\"1\">\n" );
	  Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
	  if ( hub.trim().length() == 0 )
	  {
		hub=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
	  }
	  aout.print(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),hub,hub_list));
      aout.print( "</SELECT>\n" );
      aout.print( "</TD>\n" );

      aout.print( "<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      aout.print( "<B>Lot Status:</B>&nbsp;\n" );
      aout.print( "</TD>\n" );
      aout.print( "<TD width=\"15%\" height=\"38\">\n" );
      aout.print( "<select name=\"selectElementStatus\" CLASS=\"HEADER\" size=\"1\">\n" );
      aout.print( "<option value=\"\">All</option>\n" );
      aout.print( radian.web.HTMLHelpObj.getDropDown( lot_status,slotstatus ) );
      aout.print( "</select></td>\n" );

	  //ItemID
	  aout.print( "<TD WIDTH=\"15%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  aout.println( "<SELECT CLASS=\"HEADER\"  NAME=\"itemormrsearch\" size=\"1\">\n" );
	  Hashtable searchthese=new Hashtable();
	  searchthese.put( "Item Id","ITEM_ID" );
	  searchthese.put( "MR","MATERIAL_REQUEST" );
	  aout.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( searchthese.entrySet(),searchLike ) );
	  aout.println( "</SELECT>\n" );

      //aout.print( "<B>Item ID:</B>&nbsp;\n" );
      aout.print( "&nbsp;<B>Is</B>&nbsp;</TD>\n" );
      aout.print( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      aout.print( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"itemid\" value=\"" + itemid + "\" size=\"10\">\n" );
      aout.print( "</TD>\n" );

      //Search
      aout.print( "<TD WIDTH=\"5%\" CLASS=\"announce\"  ALIGN=\"CENTER\"  VALIGN=\"MIDDLE\">\n" );
      aout.print( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n" );
      aout.print( "</TD>\n" );
      aout.print( "</TR>\n<TR>\n" );

      // Inventory Group
	  aout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  aout.println( "<B>Inv Grp:</B>&nbsp;\n" );
	  aout.println( "</TD>\n" );
	  aout.println( "<TD WIDTH=\"15%\">\n" );

	  aout.println( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n" );

	  Hashtable fh= ( Hashtable ) initialinvData.get( hub );
	  Vector invidV= ( Vector ) fh.get( "INVENTORY_GROUP" );
	  Vector invidName = ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );
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
		aout.println( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + "</option>\n" );
	  }
	  aout.println( "</SELECT>\n" );
	  aout.println( "</TD>\n" );

      /*// search material request
      aout.print( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      //aout.print( "<B>Material Request:</B>&nbsp;\n" );
      aout.print( "</TD>\n" );
      aout.print( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      //aout.print( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"mrnum\" value=\"" + smrnum + "\" size=\"10\">\n" );
      aout.print( "</TD>\n" );*/

      // Progress Status
      aout.print( "<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      aout.print( "<B>Progress Status:</B>\n" );
      aout.print( "</TD>\n" );
      aout.print( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
      aout.print( "<SELECT NAME=\"progressstat\" size=\"1\" CLASS=\"HEADER\">\n" );
      aout.print( "<option value=\"\">All</option>\n" );

      Hashtable sortresult=new Hashtable();
      sortresult.put( "In Supply","In Supply" );
      sortresult.put( "On Hand, Not Available","On Hand, Not Available" );
      sortresult.put( "Available","Available" );
      sortresult.put( "Not Allocated","Not Allocated" );

      aout.print( radian.web.HTMLHelpObj.getDropDown( sortresult,sprogressstat ) );
      aout.print( "</SELECT>\n" );
      aout.print( "</TD>\n" );

      //Needed Within
      aout.print( "<TD WIDTH=\"15%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  aout.println( "<SELECT CLASS=\"HEADER\"  NAME=\"searchdate\" size=\"1\">\n" );
	  searchthese=new Hashtable();
	  searchthese.put( "Needed","NEEDED" );
	  searchthese.put( "OnTime","ONTIME" );
	  aout.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( searchthese.entrySet(),searchdate ) );
	  aout.println( "</SELECT>\n" );

      aout.print( "<B>Within:</B>&nbsp;\n" );
      aout.print( "</TD>\n" );
      aout.print( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      aout.print( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"within\" value=\"" + swithin + "\" size=\"2\" MAXLENGTH=\"4\">\n" );
      aout.print( "&nbsp;&nbsp;&nbsp;&nbsp;Days</TD>\n" );
	  aout.print( "<TD WIDTH=\"5%\" ALIGN=\"left\" CLASS=\"announce\">&nbsp;</TD>\n" );
	  aout.print( "</TR>\n" );

	  aout.println( "<TR>\n" );

	  //Facility List
	  aout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  aout.println( "<B>Facility:</B>&nbsp;\n" );
	  aout.println( "</TD>\n" );
	  aout.println( "<TD WIDTH=\"15%\">\n" );
	  aout.println( "<SELECT CLASS=\"HEADER\" NAME=\"FacName\" size=\"1\">\n" );
	  Map warehouseInfo= ( Map ) session.getAttribute( "warehouseInfo" );
	  Map facilityInfo= ( Map ) warehouseInfo.get( hub );

	  if ( "All".equalsIgnoreCase( hub ) || hub.trim().length() == 0 || facilityInfo == null)
	  {
		aout.println( "<OPTION VALUE=\"\">Choose a Hub to get Facilities</OPTION>\n" );
	  }
	  else
	  {
		aout.println( "<OPTION VALUE=\"\">All</OPTION>\n" );
		aout.println(radian.web.HTMLHelpObj.sorthashbyValue(facilityInfo.entrySet(),facility));
	  }
	  aout.println( "</SELECT>\n" );
	  aout.println( "</TD>\n" );

      // Sort By
	  aout.print( "<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  aout.print( "<B>Sort By:</B>\n" );
	  aout.print( "</TD>\n" );
	  aout.print( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
	  aout.print( "<SELECT NAME=\"sortBy\" size=\"1\" CLASS=\"HEADER\">\n" );

	  sortresult=new Hashtable();
	  sortresult.put( "Item/Needed","ITEM_ID,REQUIRED_DATETIME" );
	  sortresult.put( "Type/Item/Needed","ITEM_TYPE,ITEM_ID,REQUIRED_DATETIME"  );
	  sortresult.put( "MR-Line/Needed","PR_NUMBER,LINE_ITEM,REQUIRED_DATETIME" );
	  sortresult.put( "Needed","REQUIRED_DATETIME" );
	  sortresult.put( "MR-Line","PR_NUMBER,LINE_ITEM" );
	  sortresult.put( "Reference No","REF_NO" );
	  sortresult.put( "Facility Id","FACILITY_ID" );
	  sortresult.put( "On Time Date","SYSTEM_REQUIRED_DATETIME" );

	  aout.print( radian.web.HTMLHelpObj.getDropDown( sortresult,sortby ) );
	  aout.print( "</SELECT>\n" );
	  aout.print( "</TD>\n" );

	  aout.print( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">&nbsp;</TD>\n" );
	  aout.print( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">&nbsp;</TD>\n" );

      //Create XLS
	  aout.print( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n" );
	  aout.print( "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create Excel File\" onclick= \"return createxls(this)\" NAME=\"SearchButton\">&nbsp;\n" );
	  aout.print( "</TD>\n" );
	  aout.print( "</TR>\n<TR>\n" );

      aout.print( "</TABLE>\n" );

      aout.print( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n" );
      aout.print( "<tr><td>" );
      aout.print( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
      aout.print( "</TD></tr>" );
      aout.print( "</table>\n" );
      //end table #2
      sortresult = null;
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      aout.print( radian.web.HTMLHelpObj.printError( "Allocation Analysis",intcmIsApplication ) );
    }
  }

  private StringBuffer buildDetails( Vector data,String SubuserAction,PrintWriter aout )  throws Exception
  {
    StringBuffer Msg=new StringBuffer();
    StringBuffer MsgTmp=new StringBuffer();
    StringBuffer MsgTmp1=new StringBuffer();
    StringBuffer MsgTmpDate=new StringBuffer();
    String Color="CLASS=\"Inwhite";
	String intColor="CLASS=\"Inwhite";
    String allnotes="";
    String earliestRequiredDate="";

    try
    {
      Hashtable summary=new Hashtable();
      summary= ( Hashtable ) data.elementAt( 0 );
      int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
	  Msg.append( "<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showallocationpagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n" );
      if ( "createxls".equalsIgnoreCase( SubuserAction ) )
      {
        Msg.append( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      }
      else
      {
        Msg.append( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      }
      Hashtable hDNext=new Hashtable();

      int ItemIdCount=0;
      int lastItemNum=1;
      int InternalColor=0;
      int lastdate=1;
      boolean FirstRow=false;
      boolean FirstdateRow=false;

      int i=0; //used for detail lines
      for ( int loop=0; loop < total; loop++ )
      {
        i++;
        boolean createHdr=false;

        if ( loop % 10 == 0 && lastItemNum == 1 )
        {
          createHdr=true;
        }

        if ( createHdr )
        {
          Msg.append( "<TR>\n" );
          Msg.append( "<TH width=\"10%\" height=\"38\">Facility</TH>\n" );
	  Msg.append( "<TH width=\"10%\" height=\"38\">Work Area</TH>\n" );
          Msg.append( "<TH width=\"5%\" height=\"38\">Requestor</TH>\n" );
          Msg.append( "<TH width=\"5%\" height=\"38\">MR-Line</TH>\n" );
          Msg.append( "<TH width=\"2%\" height=\"38\">Stocking Type</TH>\n" );
          Msg.append( "<TH width=\"10%\" height=\"38\">Part No.</TH>\n" );
          //Msg.append( "<TH width=\"5%\" height=\"38\">On Time Date</TH>\n" );
          Msg.append( "<TH width=\"2%\" height=\"38\">Qty Open</TH>\n" );
          Msg.append( "<TH width=\"5%\" height=\"38\">Needed</TH>\n" );
	  Msg.append( "<TH width=\"5%\" height=\"38\">On Time Date</TH>\n" );
          Msg.append( "<TH width=\"5%\" height=\"38\">Alloc Type</TH>\n" );
          Msg.append( "<TH width=\"5%\" height=\"38\">Ref</TH>\n" );
          Msg.append( "<TH width=\"10%\" height=\"38\">Supplier / Mfg Lot</TH>\n" );
          Msg.append( "<TH width=\"5%\" height=\"38\">Item Id<BR>(Inven Grp)</TH>\n" );
          Msg.append( "<TH width=\"2%\" height=\"38\">Qty</TH>\n" );
          Msg.append( "<TH width=\"5%\" height=\"38\">Status<BR>(Age in Days)</TH>\n" );
          Msg.append( "<TH width=\"2%\" height=\"38\">Date</TH>\n" );
          Msg.append( "<TH width=\"10%\" height=\"38\">Qty On Hand<BR>Inven Grp/Hub</TH>\n" );
          Msg.append( "<TH width=\"10%\" height=\"38\">Qty Available<BR>Inven Grp/Hub</TH>\n" );
          Msg.append( "</TR>\n" );
        }

        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) data.elementAt( i );

        String Next_mr="";
        String Next_line="";
        String Next_needed="";
        String Next_aloctype="";

        if ( ! ( i == total ) )
        {
          hDNext=new Hashtable();
          hDNext= ( Hashtable ) data.elementAt( i + 1 );
          Next_mr=hDNext.get( "PR_NUMBER" ) == null ? "&nbsp;" : hDNext.get( "PR_NUMBER" ).toString();
          Next_line=hDNext.get( "LINE_ITEM" ) == null ? "&nbsp;" : hDNext.get( "LINE_ITEM" ).toString();
          Next_needed=hDNext.get( "REQUIRED_DATETIME" ) == null ? "&nbsp;" : hDNext.get( "REQUIRED_DATETIME" ).toString();
          Next_aloctype=hDNext.get( "ALLOCATION_TYPE" ) == null ? "&nbsp;" : hDNext.get( "ALLOCATION_TYPE" ).toString();
        }
        else
        {
          Next_mr=" ";
          Next_line=" ";
          Next_needed=" ";
          Next_aloctype="";
        }

        // get main information
        String facility= ( hD.get( "FACILITY_ID" ) == null ? "&nbsp;" : hD.get( "FACILITY_ID" ).toString() );
        //String name=hD.get( "REQUESTOR_LAST_NAME" ).toString() + ", " + hD.get( "REQUESTOR_FIRST_NAME" ).toString().substring( 0,1 );
        String lastname= ( hD.get( "REQUESTOR_LAST_NAME" ) == null ? "&nbsp;" : hD.get( "REQUESTOR_LAST_NAME" ).toString() );
        String firstname= ( hD.get( "REQUESTOR_FIRST_NAME" ) == null ? "" : hD.get( "REQUESTOR_FIRST_NAME" ).toString() );
		if (firstname.length() > 0)
		{
		  firstname = ", " + firstname.substring( 0,1 );
        }
        String name = lastname + firstname;

        String prNumber= ( hD.get( "PR_NUMBER" ) == null ? "&nbsp;" : hD.get( "PR_NUMBER" ).toString() );
        String lineItem= ( hD.get( "LINE_ITEM" ) == null ? "&nbsp;" : hD.get( "LINE_ITEM" ).toString() );
        String partno= ( hD.get( "FAC_PART_NO" ) == null ? "&nbsp;" : hD.get( "FAC_PART_NO" ).toString() );
        String qtyopen= ( hD.get( "OPEN_QUANTITY" ) == null ? "&nbsp;" : hD.get( "OPEN_QUANTITY" ).toString() );
        String needed= ( hD.get( "REQUIRED_DATETIME" ) == null ? "&nbsp;" : hD.get( "REQUIRED_DATETIME" ).toString() );
        String aloctype= ( hD.get( "ALLOCATION_TYPE" ) == null ? "&nbsp;" : hD.get( "ALLOCATION_TYPE" ).toString() );
        String itemid=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
        String stocktype=hD.get( "ITEM_TYPE" ) == null ? "&nbsp;" : hD.get( "ITEM_TYPE" ).toString();
        String refno="";
        if ( "PO".equalsIgnoreCase( aloctype ) )
        {
          refno=hD.get( "REF_NO" ).toString() + "-" + hD.get( "REF_LINE" ).toString();
        }
        else
        {
          refno=hD.get( "REF_NO" ).toString();
        }

        String qty= ( hD.get( "ALLOC_QUANTITY" ) == null ? "&nbsp;" : hD.get( "ALLOC_QUANTITY" ).toString() );
        String qtyonhand= ( hD.get( "QTY_ON_HAND" ) == null ? "&nbsp;" : hD.get( "QTY_ON_HAND" ).toString() );
        String qtyavailabe= ( hD.get( "QTY_AVAILABLE" ) == null ? "&nbsp;" : hD.get( "QTY_AVAILABLE" ).toString() );
				String igQtyOnHand= ( hD.get( "IG_QTY_ON_HAND" ) == null ? "&nbsp;" : hD.get( "IG_QTY_ON_HAND" ).toString() );
        String igQtyAvailabe= ( hD.get( "IG_QTY_AVAILABLE" ) == null ? "&nbsp;" : hD.get( "IG_QTY_AVAILABLE" ).toString() );
        String refstatus= ( hD.get( "REF_STATUS" ) == null ? "&nbsp;" : hD.get( "REF_STATUS" ).toString() );
	String statusAge= ( hD.get( "LOT_STATUS_AGE" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS_AGE" ).toString() );
	if (statusAge.length() > 0)
	{
	 statusAge = "(" + statusAge + ")";
	}
        String refdate= ( hD.get( "REF_DATE" ) == null ? "&nbsp;" : hD.get( "REF_DATE" ).toString() );
        String supplierName= ( hD.get( "SUPPLIER" ) == null ? "&nbsp;" : hD.get( "SUPPLIER" ).toString() );
        String origPromisedDate= ( hD.get( "ORIGINAL_PROMISED_DATE" ) == null ? "&nbsp;" : hD.get( "ORIGINAL_PROMISED_DATE" ).toString() );
        String ismrCritical= ( hD.get( "CRITICAL" ) == null ? "&nbsp;" : hD.get( "CRITICAL" ).toString() );
        String mrnotesu=HelpObjs.addescapesForJavascript( ( hD.get( "MR_NOTES" ) == null ? "&nbsp;" : hD.get( "MR_NOTES" ).toString() ) );
        String notespo=HelpObjs.addescapesForJavascript( ( hD.get( "NOTES" ) == null ? "&nbsp;" : hD.get( "NOTES" ).toString() ) );
        String sysrequDatetime= ( hD.get( "SYSTEM_REQUIRED_DATETIME" ) == null ? "&nbsp;" : hD.get( "SYSTEM_REQUIRED_DATETIME" ).toString() );
        String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : hD.get( "INVENTORY_GROUP" ).toString() );
				String pickable= ( hD.get( "PICKABLE" ) == null ? "&nbsp;" : hD.get( "PICKABLE" ).toString() );
				//String application = hD.get("APPLICATION")==null?"&nbsp;":hD.get("APPLICATION").toString();
				String application_desc = hD.get("APPLICATION_DESC")==null?"&nbsp;":hD.get("APPLICATION_DESC").toString();
				 /*String appl_desc = "";
						if ( application_desc.trim().length() > 0 )
							{
								 appl_desc=application + " : " + application_desc;
								}
								else
												{
											appl_desc=application;
									 }*/
				String mfgLot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
				String hazmatIdMissing = hD.get( "HAZMAT_ID_MISSING" ).toString();

        boolean SameItem_id=false;
        boolean Samedate=false;

	if ( "Y".equalsIgnoreCase( ismrCritical ) )
	{
	  Color="CLASS=\"red";
	  intColor="CLASS=\"red";
	}

	if ( "S".equalsIgnoreCase( ismrCritical ) )
	{
	  Color="CLASS=\"pink";
	  intColor="CLASS=\"pink";
	}

	if ( !"Y".equalsIgnoreCase( pickable ) && "Receipt".equalsIgnoreCase( aloctype ) )
	{
	  //Color ="CLASS=\"yellow";
	  intColor="CLASS=\"yellow";
	}
	else
	{
	  intColor=Color;
	}

	 if ("MISSING".equalsIgnoreCase(hazmatIdMissing))
	 {
		intColor = "CLASS=\"orange";
	 }

        if ( lastItemNum == 1 || earliestRequiredDate.length() == 0 )
        {
          earliestRequiredDate=sysrequDatetime;
        }

        if ( prNumber.equalsIgnoreCase( Next_mr ) && lineItem.equalsIgnoreCase( Next_line ) )
        {
          SameItem_id=true;
          lastItemNum++;
          if ( Next_needed.equalsIgnoreCase( needed ) )
          {
            Samedate=true;
            lastdate++;
          }
        }

        if ( lastItemNum != 1 && sysrequDatetime.length() == 10 && earliestRequiredDate.length() == 10 )
        {
          Timestamp sysrequDatetimeTmp=radian.web.HTMLHelpObj.getDateFromString( sysrequDatetime );
          Timestamp earliestRequiredDateTmp=radian.web.HTMLHelpObj.getDateFromString( earliestRequiredDate );

          if ( !earliestRequiredDateTmp.before( sysrequDatetimeTmp ) )
          {
            earliestRequiredDate=sysrequDatetime;
          }
        }

        if ( Samedate )
        {
          if ( lastdate == 2 )
          {
            FirstdateRow=true;
          }
          if ( !FirstdateRow )
          {
            MsgTmp1.append( "<TR>\n" );
          }
					MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + sysrequDatetime + "</td>\n" );
					MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + aloctype + "</td>\n" );
					MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + refno + "</td>\n" );
					if ("Receipt".equalsIgnoreCase(aloctype))
					{
					 MsgTmp1.append("<td height=\"25\" " + intColor + "l\" width=\"10%\" >" +	mfgLot + "</td>\n");
					}
					else
					{
					 MsgTmp1.append("<td height=\"25\" " + intColor + "l\" width=\"10%\" >" +	supplierName + "</td>\n");
					}
          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + itemid + "<BR>("+invengrp+")</td>\n" );
          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"2%\" >" + qty + "</td>\n" );

          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + refstatus + "<BR>"+statusAge+"</td>\n" );
          if ( "PO".equalsIgnoreCase( aloctype ) && !origPromisedDate.equalsIgnoreCase( refdate ) )
          {
            MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"2%\" ALIGN=\"CENTER\">" + origPromisedDate + "<BR>" + refdate + "</td>\n" );
          }
          else
          {
            MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"2%\" ALIGN=\"CENTER\">" + refdate + "</td>\n" );
          }


          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"10%\" >"+igQtyOnHand+"<B>/</B>" + qtyonhand + " </td>\n" );
          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"10%\" >"+igQtyAvailabe+"<B>/</B>" + qtyavailabe + "</td>\n" );
          MsgTmp1.append( "</TR>\n" );
          allnotes+=notespo;
        }
        else if ( !Samedate )
        {
          if ( ( !FirstdateRow ) && lastdate > 1 )
          {
            MsgTmp1.append( "<TR>\n" );
          }
					MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + sysrequDatetime + "</td>\n" );
          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + aloctype + "</td>\n" );
          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + refno + "</td>\n" );
					if ("Receipt".equalsIgnoreCase(aloctype))
					{
					 MsgTmp1.append("<td height=\"25\" " + intColor + "l\" width=\"10%\" >" +	mfgLot + "</td>\n");
					}
					else
					{
					 MsgTmp1.append("<td height=\"25\" " + intColor + "l\" width=\"10%\" >" + supplierName + "</td>\n");
					}
          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + itemid + "<BR>("+invengrp+")</td>\n" );
          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"2%\" >" + qty + "</td>\n" );

          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + refstatus + "<BR>"+statusAge+"</td>\n" );
          if ( "PO".equalsIgnoreCase( aloctype ) && !origPromisedDate.equalsIgnoreCase( refdate ) )
          {
            MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"2%\" ALIGN=\"CENTER\">" + origPromisedDate + "<BR>" + refdate + "</td>\n" );
          }
          else
          {
            MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"2%\" ALIGN=\"CENTER\">" + refdate + "</td>\n" );
          }

          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"10%\" >"+igQtyOnHand+"<B>/</B>" + qtyonhand + "</td>\n" );
          MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"10%\" >"+igQtyAvailabe+"<B>/</B>" + qtyavailabe + "</td>\n" );
          MsgTmp1.append( "</TR>\n" );
          MsgTmpDate.append( "<td height=\"25\" " + Color + "\" width=\"2%\" ROWSPAN=\"" + lastdate + "\" >" + qtyopen + "</td>\n" );
          MsgTmpDate.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastdate + "\">" + needed + "</td>\n" );

          MsgTmpDate.append( MsgTmp1 );

          MsgTmp1=new StringBuffer();
          lastdate=1;
          allnotes+=notespo;
        }

        if ( SameItem_id )
        {
          if ( lastItemNum == 2 )
          {
            FirstRow=true;
          }
        }
        else
        {
          if ( ( !FirstRow ) && lastItemNum > 1 )
          {
            MsgTmp.append( "<TR>\n" );
          }

          if ( !Samedate )
          {
            MsgTmp.append( MsgTmpDate );
          }

          MsgTmp.append( "</TR>\n" );

          Msg.append( "<TR>\n" );

          allnotes+=mrnotesu;

          Msg.append( "<td height=\"25\" " + Color + "\" width=\"10%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\">" + facility + "</td>\n" );
	  Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\">" + application_desc + " </td>\n" );
          Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\">" + name + "</td>\n" );
          Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">\n" );
          if ( allnotes.length() > 0 )
          {
            Msg.append( "<A HREF=\"javascript:showallNotes('" + allnotes + "','" + prNumber + "-" + lineItem + "')\">" + prNumber + " - " + lineItem + "</A></TD>\n" );
          }
          else
          {
            Msg.append( "" + prNumber + " - " + lineItem + "</A></TD>\n" );
          }

          Msg.append( "<td height=\"25\" " + Color + "\" width=\"2%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\" >" + stocktype + "</td>\n" );
          Msg.append( "<td height=\"25\" " + Color + "\" width=\"10%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\" >" + partno + "</td>\n" );
          //Msg.append( "<td height=\"25\" " + Color + "\" width=\"10%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\" >" + earliestRequiredDate + "</td>\n" );
          Msg.append( MsgTmp );

          allnotes="";
          earliestRequiredDate="";
          MsgTmp=new StringBuffer();
          MsgTmpDate=new StringBuffer();
          lastItemNum=1;

          if ( !prNumber.equalsIgnoreCase( Next_mr ) || !lineItem.equalsIgnoreCase( Next_line ) )
          {
            ItemIdCount++;
          }

          if ( ( ItemIdCount ) % 2 == 0 )
          {
            Color="CLASS=\"Inwhite";
			intColor="CLASS=\"Inwhite";
          }
          else
          {
            Color="CLASS=\"Inblue";
			intColor="CLASS=\"Inblue";
          }

          InternalColor=0;
          continue;
        }

        InternalColor++;
        if ( !FirstRow )
        {
          MsgTmp.append( "<TR>\n" );
        }

        if ( !Samedate )
        {
          MsgTmp.append( MsgTmpDate );
          MsgTmpDate=new StringBuffer();
        }
      }

      Msg.append( "</TABLE>\n" );
      Msg.append( "</FORM>\n" );
      Msg.append( "</DIV>\n" );
      Msg.append( "</BODY></HTML>\n" );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      aout.print( radian.web.HTMLHelpObj.printError( "tcmIS Hub Inventory Reconciliation",intcmIsApplication ) );
    }

    return Msg;
  }

  private StringBuffer buildxlsDetails( Vector data,String personnelID,PrintWriter aout )  throws Exception
  {
	//StringBuffer Msg=new StringBuffer();
	String url="";
	String file="";

	Random rand=new Random();
	int tmpReq=rand.nextInt();
	Integer tmpReqNum=new Integer( tmpReq );

	String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	String writefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();

	file=writefilepath + personnelID + tmpReqNum.toString() + "allocation.csv";
	url=wwwHome + "reports/temp/" + personnelID + tmpReqNum.toString() + "allocation.csv";

	try
	{
	  PrintWriter pw=new PrintWriter( new FileOutputStream( file ) );

      Hashtable summary=new Hashtable();
      summary= ( Hashtable ) data.elementAt( 0 );
      int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

      pw.print( "Facility,Work Area,Requestor,MR-Line,Release Date,Stocking Type,Part No.,Qty Open,Needed,Alloc Type,Ref, ,Supplier,Mfg Lot,Item Id,Inven Grp,Qty,Qty On Hand,Qty Available,Status,Date, ,System Required Date(On Time Date),Notes,Critical,Inventory Goup onHand,Inventory Group Available,Status Age in Days\n" );
      int i=0; //used for detail lines
      for ( int loop=0; loop < total; loop++ )
      {
        i++;
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) data.elementAt( i );

        // get main information
        String facility= ( hD.get( "FACILITY_ID" ) == null ? "" : hD.get( "FACILITY_ID" ).toString() );
        //String name=hD.get( "REQUESTOR_LAST_NAME" ).toString() + ", " + hD.get( "REQUESTOR_FIRST_NAME" ).toString().substring( 0,1 );
        String lastname= ( hD.get( "REQUESTOR_LAST_NAME" ) == null ? "" : hD.get( "REQUESTOR_LAST_NAME" ).toString() );
        String firstname= ( hD.get( "REQUESTOR_FIRST_NAME" ) == null ? "" : hD.get( "REQUESTOR_FIRST_NAME" ).toString() );
				if (firstname.length() > 0)
				{
				 firstname = ", " + firstname.substring( 0,1 );
				}
				String name = lastname + firstname;
        String prNumber= ( hD.get( "PR_NUMBER" ) == null ? "" : hD.get( "PR_NUMBER" ).toString() );
        String lineItem= ( hD.get( "LINE_ITEM" ) == null ? "" : hD.get( "LINE_ITEM" ).toString() );
        String partno= ( hD.get( "FAC_PART_NO" ) == null ? "" : hD.get( "FAC_PART_NO" ).toString() );
        String qtyopen= ( hD.get( "OPEN_QUANTITY" ) == null ? "" : hD.get( "OPEN_QUANTITY" ).toString() );
        String needed= ( hD.get( "REQUIRED_DATETIME" ) == null ? "" : hD.get( "REQUIRED_DATETIME" ).toString() );
        String aloctype= ( hD.get( "ALLOCATION_TYPE" ) == null ? "" : hD.get( "ALLOCATION_TYPE" ).toString() );
        String refno= ( hD.get( "REF_NO" ) == null ? "" : hD.get( "REF_NO" ).toString() );
        String itemid=hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString();
        String stocktype=hD.get( "ITEM_TYPE" ) == null ? "" : hD.get( "ITEM_TYPE" ).toString();
        String qty= ( hD.get( "ALLOC_QUANTITY" ) == null ? "" : hD.get( "ALLOC_QUANTITY" ).toString() );
        String qtyonhand= ( hD.get( "QTY_ON_HAND" ) == null ? "" : hD.get( "QTY_ON_HAND" ).toString() );
        String qtyavailabe= ( hD.get( "QTY_AVAILABLE" ) == null ? "" : hD.get( "QTY_AVAILABLE" ).toString() );
        String refstatus= ( hD.get( "REF_STATUS" ) == null ? "" : hD.get( "REF_STATUS" ).toString() );
        String refdate= ( hD.get( "REF_DATE" ) == null ? "" : hD.get( "REF_DATE" ).toString() );
        String suppliername= ( hD.get( "SUPPLIER" ) == null ? "" : hD.get( "SUPPLIER" ).toString() );
        String origPromisedDate= ( hD.get( "ORIGINAL_PROMISED_DATE" ) == null ? "" : hD.get( "ORIGINAL_PROMISED_DATE" ).toString() );

        String ismrCritical= ( hD.get( "CRITICAL" ) == null ? "" : hD.get( "CRITICAL" ).toString() );
        String mrnotesu= ( hD.get( "MR_NOTES" ) == null ? "" : hD.get( "MR_NOTES" ).toString() );
        String notespo= ( hD.get( "NOTES" ) == null ? "" : hD.get( "NOTES" ).toString() );
				mrnotesu = HelpObjs.findReplace(mrnotesu,"\n"," ");
				notespo = HelpObjs.findReplace(notespo,"\n"," ");

        //String isdelayed= ( hD.get( "DELAY" ) == null ? "" : hD.get( "DELAY" ).toString() );
        String sysrequDatetime= ( hD.get( "SYSTEM_REQUIRED_DATETIME" ) == null ? "" : hD.get( "SYSTEM_REQUIRED_DATETIME" ).toString() );
        String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? "" : hD.get( "INVENTORY_GROUP" ).toString() );
	String releasedate= ( hD.get( "RELEASE_DATE" ) == null ? "" : hD.get( "RELEASE_DATE" ).toString() );
	String mfgLot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
	String application_desc = hD.get("APPLICATION_DESC")==null?"&nbsp;":hD.get("APPLICATION_DESC").toString();
	String igQtyOnHand= ( hD.get( "IG_QTY_ON_HAND" ) == null ? "&nbsp;" : hD.get( "IG_QTY_ON_HAND" ).toString() );
        String igQtyAvailabe= ( hD.get( "IG_QTY_AVAILABLE" ) == null ? "&nbsp;" : hD.get( "IG_QTY_AVAILABLE" ).toString() );
	String statusAge= ( hD.get( "LOT_STATUS_AGE" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS_AGE" ).toString() );

        pw.print( "\"" + facility + "\"," );
				pw.print( "\"" + application_desc + "\"," );
        pw.print( "\"" + name + "\"," );
        pw.print( "\"" + prNumber + "-" + lineItem + "\"," );
				pw.print( "\"" + releasedate + "\"," );
        pw.print( "\"" + stocktype + "\"," );
        pw.print( "\"" + partno + "\"," );
        pw.print( "\"" + qtyopen + "\"," );
        pw.print( "\"" + needed + "\"," );
        pw.print( "\"" + aloctype + "\"," );
        if ( "PO".equalsIgnoreCase( aloctype ) )
        {
          pw.print( "\"" + refno + "\"," );
          pw.print( "\"" + hD.get( "REF_LINE" ).toString() + "\"," );
        }
        else
        {
          pw.print( "\"" + refno + "\"," );
          pw.print( "\" \"," );
        }
        pw.print( "\"" + suppliername + "\"," );
				pw.print( "\"" + mfgLot + "\"," );
        pw.print( "\"" + itemid + "\"," );
        pw.print( "\"" + invengrp + "\"," );
        pw.print( "\"" + qty + "\"," );
        pw.print( "\"" + qtyonhand + "\"," );
        pw.print( "\"" + qtyavailabe + "\"," );
        pw.print( "\"" + refstatus + "\"," );
        if ( "PO".equalsIgnoreCase( aloctype ) && !origPromisedDate.equalsIgnoreCase( refdate ) )
        {
          pw.print( "\"" + origPromisedDate + "\"," );
          pw.print( "\"" + refdate + "\"," );
        }
        else
        {
          pw.print( "\"" + refdate + "\"," );
          pw.print( "\" \"," );
        }

        pw.print( "\"" + sysrequDatetime + "\"," );
        pw.print( "\"" + mrnotesu + notespo + "\"," );
        pw.print( "\"" + ismrCritical + "\"," );
	pw.print( "\"" + igQtyOnHand + "\"," );
	pw.print( "\"" + igQtyAvailabe + "\"," );
	pw.print( "\"" + statusAge + "\"\n" );
      }
	  pw.close();
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      aout.print( radian.web.HTMLHelpObj.printError( "tcmIS Hub Inventory Reconciliation",intcmIsApplication ) );
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
  //End of method
}
//END OF CLASS
