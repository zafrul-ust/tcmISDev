package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 06-22-05 - Showing the help link only if a person is logged in
 */

public class shipInfo
{
	private ServerResourceBundle bundle=null;
	private TcmISDBModel db=null;
	private PrintWriter out=null;
	private boolean completeSuccess=true;
	private boolean noneToUpd=true;
	private String Receiving_Servlet="";
	private boolean allowupdate;
private boolean intcmIsApplication = false;
	public void setupdateStatus( boolean id )
	{
		this.allowupdate=id;
	}

	private boolean getupdateStatus() throws Exception
	{
		return this.allowupdate;
	}

	public shipInfo( ServerResourceBundle b,TcmISDBModel d )
	{
		bundle=b;
		db=d;
	}

	//Process the HTTP Get request
	public void doGet( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
	{
		doPost( request,response );
	}

	//Process the HTTP Post request
	public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
	{
		out=response.getWriter();
		response.setContentType( "text/html" );
		HttpSession session=request.getSession();
		String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "&nbsp;" : session.getAttribute( "PERSONNELID" ).toString();
		Receiving_Servlet=bundle.getString( "SHIPPINGINFO_SERVLET" );
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		if (personnelBean !=null)
		{
			 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			 if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
			 {
					 intcmIsApplication = true;
			 }
		}

		String User_Search=null;
		String User_Action=null;
		String User_Session="";
		String User_Sort="";

		User_Session=request.getParameter( "session" );
		if ( User_Session == null )
		{
			User_Session="New";
		}
		User_Action=request.getParameter( "UserAction" );
		if ( User_Action == null )
		{
			User_Action="New";
		}
		User_Search=request.getParameter( "SearchField" );
		if ( User_Search == null )
		{
			User_Search="";
		}
	User_Search = User_Search.trim();

		User_Sort=request.getParameter( "SortBy" );
		if ( User_Sort == null )
		{
			User_Sort="1";
		}

	String catalogid = request.getParameter( "catalogid" );
	if ( catalogid == null )
	{
		catalogid="";
	}
	catalogid = catalogid.trim();

	try
	{
		if ( ( User_Session.equalsIgnoreCase( "New" ) ) && ( User_Action.equalsIgnoreCase( "New" ) ) )
		{
		Hashtable cat_list=this.createCatList();
		session.setAttribute( "CATALOG_LIST",cat_list );

		buildHeader( cat_list,catalogid,User_Search,out,session );
		out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
		out.close();
			}
			else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "Search" ) ) )
			{
		Hashtable cat_list  = (Hashtable)session.getAttribute("CATALOG_LIST");
				Vector openorder_data=new Vector();
				openorder_data=getResult( catalogid, User_Search );

				Hashtable sum= ( Hashtable ) ( openorder_data.elementAt( 0 ) );
				int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
				if ( 0 == count )
				{
					buildHeader( cat_list,catalogid, User_Search,out,session );
					out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );

					//clean up
					openorder_data=null;
				}
				else
				{
					session.setAttribute( "DATA",openorder_data );
					buildHeader( cat_list,catalogid, User_Search,out,session);
					out.println(buildDetails( openorder_data,"" ));

					//clean up
					openorder_data=null;
				} //when there are open orders for hub
			}
			else
			{
				out.println( radian.web.HTMLHelpObj.printError( "tcmIS Shiping Info",intcmIsApplication ) );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			out.println( radian.web.HTMLHelpObj.printError( "tcmIS Shiping Info",intcmIsApplication ) );
		}
	finally
	{
		out.close();
	}

		return;
	}

	private Vector getResult( String catalog,String searchtext ) throws Exception
	{
	Vector Data=new Vector();
	Hashtable DataH=new Hashtable();
	Hashtable summary=new Hashtable();
	DBResultSet dbrs=null;
	ResultSet searchRs=null;
	String query = "";

	int count=0;
	summary.put( "TOTAL_NUMBER",new Integer( count ) );
	Data.addElement( summary );

	try
	{
		CallableStatement cs=null;

		try
		{
		Connection connect1=db.getConnection();
		cs=connect1.prepareCall( "{call PR_SHIPPING_INFO_PAGE(?,?,?)}" );
		cs.setString( 1,catalog );
		if ( searchtext.length() > 0 )
		{
			cs.setString( 2,searchtext );
		}
		else
		{
			cs.setNull( 2,java.sql.Types.INTEGER );
		}

		cs.registerOutParameter( 3,java.sql.Types.VARCHAR );
		cs.execute();

		query=BothHelpObjs.makeBlankFromNull( cs.getString( 3 ) );
		}
		catch ( Exception e )
		{
		e.printStackTrace();
		radian.web.emailHelpObj.senddatabaseHelpemails( "Error calling Procedure PR_SHIPPING_INFO_PAGE Ship Info Screen","Error occured while running PR_SHIPPING_INFO_PAGE\n" + e.getMessage() +
														"\nFor Input Parameters catalog: " + catalog + "    searchtext      "+searchtext+"" );
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

		if ( query.trim().length() > 0 )
		{
		dbrs=db.doQuery( query );
		searchRs=dbrs.getResultSet();

		while ( searchRs.next() )
		{
			DataH=new Hashtable();
			//CATALOG_ID
			DataH.put( "CATALOG_ID",searchRs.getString( "CATALOG_ID" ) == null ? "&nbsp;" : searchRs.getString( "CATALOG_ID" ) );
//CAT_PART_NO
			DataH.put( "CAT_PART_NO",searchRs.getString( "CAT_PART_NO" ) == null ? "&nbsp;" : searchRs.getString( "CAT_PART_NO" ) );
//PART_GROUP_NO
			DataH.put( "PART_GROUP_NO",searchRs.getString( "PART_GROUP_NO" ) == null ? "&nbsp;" : searchRs.getString( "PART_GROUP_NO" ) );
//PART_DESCRIPTION
			DataH.put( "PART_DESCRIPTION",searchRs.getString( "PART_DESCRIPTION" ) == null ? "&nbsp;" : searchRs.getString( "PART_DESCRIPTION" ) );
//STATUS
			DataH.put( "STATUS",searchRs.getString( "STATUS" ) == null ? "&nbsp;" : searchRs.getString( "STATUS" ) );
//ITEM_ID
			DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "&nbsp;" : searchRs.getString( "ITEM_ID" ) );
//CASE_QTY
			DataH.put( "CASE_QTY",searchRs.getString( "CASE_QTY" ) == null ? "&nbsp;" : searchRs.getString( "CASE_QTY" ) );
//PART_ID
			DataH.put( "PART_ID",searchRs.getString( "PART_ID" ) == null ? "&nbsp;" : searchRs.getString( "PART_ID" ) );
//MATERIAL_ID
			DataH.put( "MATERIAL_ID",searchRs.getString( "MATERIAL_ID" ) == null ? "&nbsp;" : searchRs.getString( "MATERIAL_ID" ) );
//COMPONENT_DESC
			DataH.put( "COMPONENT_DESC",searchRs.getString( "COMPONENT_DESC" ) == null ? "&nbsp;" : searchRs.getString( "COMPONENT_DESC" ) );
//PACKAGING
			DataH.put( "PACKAGING",searchRs.getString( "PACKAGING" ) == null ? "&nbsp;" : searchRs.getString( "PACKAGING" ) );
//GRADE
			DataH.put( "GRADE",searchRs.getString( "GRADE" ) == null ? "&nbsp;" : searchRs.getString( "GRADE" ) );
//MFG_PART_NO
			DataH.put( "MFG_PART_NO",searchRs.getString( "MFG_PART_NO" ) == null ? "&nbsp;" : searchRs.getString( "MFG_PART_NO" ) );
//GROUND_SHIPPING_NAME
			DataH.put( "GROUND_SHIPPING_NAME",searchRs.getString( "GROUND_SHIPPING_NAME" ) == null ? "&nbsp;" : searchRs.getString( "GROUND_SHIPPING_NAME" ) );
//AIR_SHIPPING_NAME
			DataH.put( "AIR_SHIPPING_NAME",searchRs.getString( "AIR_SHIPPING_NAME" ) == null ? "&nbsp;" : searchRs.getString( "AIR_SHIPPING_NAME" ) );
//MATERIAL_DESC
			DataH.put( "MATERIAL_DESC",searchRs.getString( "MATERIAL_DESC" ) == null ? "&nbsp;" : searchRs.getString( "MATERIAL_DESC" ) );
//MANUFACTURER
			DataH.put( "MANUFACTURER",searchRs.getString( "MANUFACTURER" ) == null ? "&nbsp;" : searchRs.getString( "MANUFACTURER" ) );
//NO_OF_COMPONENTS
					DataH.put( "NO_OF_COMPONENTS",searchRs.getString( "NO_OF_COMPONENTS" ) == null ? "&nbsp;" : searchRs.getString( "NO_OF_COMPONENTS" ) );
					//DOT
					DataH.put( "DOT",searchRs.getString( "DOT" ) == null ? "&nbsp;" : searchRs.getString( "DOT" ) );

			Data.addElement( DataH );
			count++;
		}
		}
		}
		catch ( Exception e )
		{
		e.printStackTrace();
		throw e;
		}
		finally
		{
		if ( dbrs != null )
		{
			dbrs.close();
		}
		}

	Hashtable recsum=new Hashtable();
	recsum.put( "TOTAL_NUMBER",new Integer( count ) );
	Data.setElementAt( recsum,0 );
	Data.trimToSize();
	return Data;
	}

	public Hashtable createCatList()
	{
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable hub_list = new Hashtable();
		try
		{
			String query = "select distinct CATALOG_ID from catalog_facility";

			dbrs = db.doQuery(query);
			rs=dbrs.getResultSet();
			while (rs.next())
			{
			String temp1 =  rs.getString("CATALOG_ID")==null?"":rs.getString("CATALOG_ID");
			hub_list.put(temp1 , temp1);
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

		return hub_list;
	}

	private void buildHeader( Hashtable catdata,String catalogid,String search_text,PrintWriter out, HttpSession session)
	{
		//StringBuffer Msg=new StringBuffer();
		String SelectedHubName="";
		try
		{
			HeaderFooter hf=new HeaderFooter( bundle,db );
			out.print(hf.printHTMLHeader("tcmIS Shipping Info",intcmIsApplication));
			out.print( "<SCRIPT SRC=\"/clientscripts/shipinfo.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
		out.print( hf.printTopToolBar( "","tcmistcmis32.gif",session ) );

			out.print( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
			out.print( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
			out.print( "</DIV>\n" );
			out.print( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
			out.print( "<FORM method=\"POST\" NAME=\"shipinfo\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Receiving_Servlet + "\">\n" );
			//start table #1
			out.print( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"725\" CLASS=\"moveup\">\n" );
			out.print( "<TR VALIGN=\"MIDDLE\" ALIGN=\"LEFT\">\n" );

			//Hub
			out.print( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		if (catdata.size() ==1 )
		{
		out.print( "&nbsp;\n" );
		}
		else
		{
		out.print( "<B>Catalog:</B>&nbsp;\n" );
		}
			out.print( "</TD>\n" );
			out.print( "<TD WIDTH=\"40%\">\n" );
		if (catdata.size() ==1 )
		{
		Enumeration E;
		for ( E=catdata.keys(); E.hasMoreElements(); )
		{
		String key= ( String ) E.nextElement();
		catalogid=catdata.get( key ).toString();
		}
		 out.print( "<INPUT TYPE=\"hidden\" NAME=\"catalogid\" VALUE=\""+catalogid+"\">\n" );
		}
		else
		{
			out.print( "<SELECT CLASS=\"HEADER\" NAME=\"catalogid\" size=\"1\">\n" );
			out.print(radian.web.HTMLHelpObj.sorthashbyValue(catdata.entrySet(),catalogid));
			out.print( "</SELECT>\n" );
		}
			out.print( "</TD>\n" );


			out.print( "<TD WIDTH=\"50%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		out.print( "&nbsp;\n" );
			out.print( "</TD>\n" );

			out.print( "</TR>\n" );
			out.print( "<TR VALIGN=\"MIDDLE\" ALIGN=\"LEFT\">\n" );

			// Search Text
			out.print( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
			out.print( "<B>Search:</B>&nbsp;\n" );
			out.print( "</TD>\n" );

		out.print( "<TD WIDTH=\"40%\">\n" );
		out.print( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\"" + search_text + "\" size=\"19\">\n" );
		out.print( "</TD>\n" );

			//List Orders
			out.print( "<TD WIDTH=\"50%\" CLASS=\"announce\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n" );
		out.print( "<INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
			out.print( "</TD>\n" );

			out.print( "</TR></TABLE><br>\n" );
			out.print( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n" );
			out.print( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n" );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			out.print(radian.web.HTMLHelpObj.printError( "tcmIS Shiping Info",intcmIsApplication ));
		}

		return;
	}
//end of method

	private StringBuffer buildDetails( Vector data,String SubuserAction )
	{
		StringBuffer Msg=new StringBuffer();
	StringBuffer MsgTmp=new StringBuffer();
	StringBuffer MsgTmp1=new StringBuffer();
	StringBuffer MsgTmpDate=new StringBuffer();
	String Color="CLASS=\"Inwhitel";
	String intColor="CLASS=\"Inwhitel";

	try
	{
		Hashtable summary=new Hashtable();
		summary= ( Hashtable ) data.elementAt( 0 );
		int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
		Msg.append( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

		int i=0; //used for detail lines
		int ItemIdCount=0;
		int lastItemNum=1;
		int lastdate=1;
		boolean FirstRow=false;
		boolean FirstdateRow=false;

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
			Msg.append( "<tr align=\"center\">\n" );
			//Msg.append( "<TH width=\"5%\"  height=\"38\">Catalog</TH>\n" );
			Msg.append( "<TH width=\"5%\"  height=\"38\">Part</TH>\n" );
			Msg.append( "<TH width=\"15%\"  height=\"38\">Description</TH>\n" );
			//Msg.append( "<TH width=\"5%\"  height=\"38\">Items Per Part</TH>\n" );
			//Msg.append( "<TH width=\"5%\"  height=\"38\">Item</TH>\n" );
			Msg.append( "<TH width=\"10%\"  height=\"38\">Component Description</TH>\n" );
			Msg.append( "<TH width=\"10%\"  height=\"38\">Shipping Name</TH>\n" );
			Msg.append( "<TH width=\"5%\"  height=\"38\">Packaging</TH>\n" );
			//Msg.append( "<TH width=\"5%\" height=\"38\">Grade</TH>\n" );
			Msg.append( "<TH width=\"5%\" height=\"38\">Manufacturer</TH>\n" );
			Msg.append( "<TH width=\"5%\"  height=\"38\">Mfg Part No</TH>\n" );
			Msg.append( "<TH width=\"2%\"  height=\"38\">&nbsp;</TH>\n" );
					Msg.append( "</tr>\n" );
				}

				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) data.elementAt( i );

		String Next_part="";
		String Next_item="";
		Hashtable hDNext=new Hashtable();

		if ( ! ( i == total ) )
		{
			hDNext=new Hashtable();
			hDNext= ( Hashtable ) data.elementAt( i + 1 );
			Next_part=hDNext.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hDNext.get( "CAT_PART_NO" ).toString();
			Next_item=hDNext.get( "ITEM_ID" ) == null ? "&nbsp;" : hDNext.get( "ITEM_ID" ).toString();
		}
		else
		{
			Next_part=" ";
			Next_item=" ";
		}

				// get main information
		//String catalog_id=hD.get( "CATALOG_ID" ) == null ? "&nbsp;" : hD.get( "CATALOG_ID" ).toString();
		String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hD.get( "CAT_PART_NO" ).toString();
		//String part_group_no=hD.get( "PART_GROUP_NO" ) == null ? "&nbsp;" : hD.get( "PART_GROUP_NO" ).toString();
		String part_description=hD.get( "PART_DESCRIPTION" ) == null ? "&nbsp;" : hD.get( "PART_DESCRIPTION" ).toString();
		//String status=hD.get( "STATUS" ) == null ? "&nbsp;" : hD.get( "STATUS" ).toString();
		String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
		//String case_qty=hD.get( "CASE_QTY" ) == null ? "&nbsp;" : hD.get( "CASE_QTY" ).toString();
		//String part_id=hD.get( "PART_ID" ) == null ? "&nbsp;" : hD.get( "PART_ID" ).toString();
		String material_id=hD.get( "MATERIAL_ID" ) == null ? "&nbsp;" : hD.get( "MATERIAL_ID" ).toString();
		String component_desc=hD.get( "COMPONENT_DESC" ) == null ? "&nbsp;" : hD.get( "COMPONENT_DESC" ).toString();
		component_desc = radian.web.HTMLHelpObj.wrap(component_desc,"",25);
		String packaging=hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString();
		//String grade=hD.get( "GRADE" ) == null ? "&nbsp;" : hD.get( "GRADE" ).toString();
		String mfg_part_no=hD.get( "MFG_PART_NO" ) == null ? "&nbsp;" : hD.get( "MFG_PART_NO" ).toString();
		String ground_shipping_name=hD.get( "GROUND_SHIPPING_NAME" ) == null ? "&nbsp;" : hD.get( "GROUND_SHIPPING_NAME" ).toString();
		String dot=hD.get( "DOT" ) == null ? "&nbsp;" : hD.get( "DOT" ).toString();
		//String material_desc=hD.get( "MATERIAL_DESC" ) == null ? "&nbsp;" : hD.get( "MATERIAL_DESC" ).toString();
		String manufacturer=hD.get( "MANUFACTURER" ) == null ? "&nbsp;" : hD.get( "MANUFACTURER" ).toString();
		String noofcomponents =hD.get( "NO_OF_COMPONENTS" ) == null ? "&nbsp;" : hD.get( "NO_OF_COMPONENTS" ).toString();

		boolean Samepart=false;
		boolean Sameitem=false;
		intColor=Color;

		if ( cat_part_no.equalsIgnoreCase( Next_part ) )
		{
			Samepart=true;
			lastItemNum++;
			if ( Next_item.equalsIgnoreCase( item_id ) )
			{
			Sameitem=true;
			lastdate++;
			}
		}

		if ( Sameitem )
		{
			if ( lastdate == 2 )
			{
			FirstdateRow=true;
			}
			if ( !FirstdateRow )
			{
			MsgTmp1.append( "<TR align=\"left\">\n" );
			}
			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"10%\" >" + component_desc + "</td>\n" );
			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"10%\" >" + dot + "</td>\n" );
			//MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + ground_shipping_name + "</td>\n" );
			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + packaging + "</td>\n" );
			//MsgTmp1.append( "<td height=\"25\" " + intColor + "l\" width=\"5%\" >" + grade + "</td>\n" );
			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + manufacturer + "</td>\n" );
			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + mfg_part_no + "</td>\n" );

			if ( lastdate == 2)
			{
			MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"2%\" ROWSPAN=\"" + noofcomponents + "\"><BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"receiptNotes\" value=\"Details\" OnClick=\"enterdotinfo('"+item_id+"')\">Details</BUTTON></td>\n");
			}
			MsgTmp1.append( "</TR>\n" );
		}
		else if ( !Sameitem )
		{
			if ( ( !FirstdateRow ) && lastdate > 1 )
			{
			MsgTmp1.append( "<TR align=\"left\">\n" );
			}

			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"10%\" >" + component_desc + "</td>\n" );
			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"10%\" >" + dot + "</td>\n" );
			//MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + ground_shipping_name + "</td>\n" );
			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + packaging + "</td>\n" );
			//MsgTmp1.append( "<td height=\"25\" " + intColor + "l\" width=\"5%\" >" + grade + "</td>\n" );
			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + manufacturer + "</td>\n" );
			MsgTmp1.append( "<td height=\"25\" " + intColor + "\" width=\"5%\" >" + mfg_part_no + "</td>\n" );

			//if ( lastItemNum == 1 && !(lastdate > 1))
			if ( lastdate == 1 )
			{
			//System.out.print("lastItemNum     "+lastItemNum+"      lastdate   "+lastdate+"      item_id         "+item_id+"");
			MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"2%\" ROWSPAN=\"" + noofcomponents + "\"><BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"receiptNotes\" value=\"Details\" OnClick=\"enterdotinfo('"+item_id+"')\">Details</BUTTON></td>\n" );
			}

			MsgTmp1.append( "</TR>\n" );

			//MsgTmpDate.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastdate + "\">" + case_qty + "</td>\n" );
			//MsgTmpDate.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastdate + "\" >" + item_id + "</td>\n" );

			MsgTmpDate.append( MsgTmp1 );

			MsgTmp1=new StringBuffer();
			lastdate=1;
		}

		if ( Samepart )
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

			if ( !Sameitem )
			{
			MsgTmp.append( MsgTmpDate );
			}

			//MsgTmp.append( "</TR>\n" );
			Msg.append( "<TR align=\"left\">\n" );

			//Msg.append( "<td height=\"25\" " + Color + "\" width=\"10%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\">" + catalog_id + "</td>\n" );
			Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\">" + cat_part_no + "</td>\n" );
			Msg.append( "<td height=\"25\" " + Color + "\" width=\"15%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\" >" + part_description + "</td>\n" );
			Msg.append( MsgTmp );
			//Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\" >Edit Ship</td>\n" );

			MsgTmp=new StringBuffer();
			MsgTmpDate=new StringBuffer();
			lastItemNum=1;

			if ( !cat_part_no.equalsIgnoreCase( Next_part ) )
			{
			ItemIdCount++;
			}

			if ( ( ItemIdCount ) % 2 == 0 )
			{
			Color="CLASS=\"Inwhitel";
			intColor="CLASS=\"Inwhitel";
			}
			else
			{
			Color="CLASS=\"Inbluel";
			intColor="CLASS=\"Inbluel";
			}

			continue;
		}

		if ( !FirstRow )
		{
			MsgTmp.append( "<TR>\n" );
		}

		if ( !Sameitem )
		{
			MsgTmp.append( MsgTmpDate );
			MsgTmpDate=new StringBuffer();
		}

			}

			Msg.append( "</table>\n" );
			/*Msg.append( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n" );
			Msg.append( "<tr><TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n</TD></tr>" );
			Msg.append( "<tr><TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n</TD></tr>" );
			Msg.append( "<tr><TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n</TD></tr>" );
			Msg.append( "</table>\n" );*/

			Msg.append( "</form>\n" );
			Msg.append( "</body></html>\n" );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return radian.web.HTMLHelpObj.printError( "tcmIS Shiping Info",intcmIsApplication);
		}

		return Msg;
	}
}
//END OF CLASS