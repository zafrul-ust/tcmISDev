package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import com.tcmis.common.admin.beans.PersonnelBean;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2002
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 03-05-03 Adding MR-Notes
 * 03-18-03 movin mrnotes to the end of the page
 * 03-21-03 Adding the check all option to check all the check boxes
 * 03-27-03 Coloring Critical orders red
 * 11-14-03 Using user group Picking instead or Inventory to get the Hub list
 * 01-27-04 No need to encode Space in the Drop Down menu
 * 02-10-04 - Sorting Drop Downs
 * 09-20-04 - Giving option to sort by item_id and part_number
 */

public class searchPicking
{
	private ServerResourceBundle bundle=null;
	private TcmISDBModel db=null;
	private PrintWriter out=null;
	private boolean completeSuccess=true;
	private boolean noneToUpd=true;
	private boolean allowupdate;
	private String thedecidingRandomNumber = null;
	private boolean intcmIsApplication = false;
	public void setupdateStatus( boolean id )
	{
		this.allowupdate=id;
	}

	private boolean getupdateStatus()
	throws Exception
	{
		return this.allowupdate;
	}

	private String dropDownJs;

	public void setdropDownJs( String compjs )
	{
		this.dropDownJs=compjs;
	}

	private String getdropDownJs()
	throws Exception
	{
		return this.dropDownJs;
	}

	public searchPicking( ServerResourceBundle b,TcmISDBModel d )
	{
		bundle=b;
		db=d;
	}

	//Process the HTTP Post request passed from whoever called it
	public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession session )
	throws ServletException,IOException

	{
		out=response.getWriter();
		response.setContentType( "text/html" );
		String branch_plant= session.getAttribute( "BRANCH_PLANT" ) == null ? "" : session.getAttribute( "BRANCH_PLANT" ).toString();
		String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		String CompanyID= session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
		Collection hubInventoryGroupOvBeanCollection = new Vector((Collection)session.getAttribute("hubInventoryGroupOvBeanCollection"));
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
			intcmIsApplication = true;
		}

		String User_Action=null;
		User_Action=request.getParameter( "UserAction" );
		if ( User_Action == null )
			User_Action="New";

		String mrnum=request.getParameter( "mrnum" );
		if ( mrnum == null )
			mrnum="";

		String User_Sort=request.getParameter( "SortBy" );
		if ( User_Sort == null )
			User_Sort="FACILITY_ID,APPLICATION";

		String facility=request.getParameter( "FacName" );
		if ( facility == null )
			facility="";

		String expdays=request.getParameter( "expdays" );
		if ( expdays == null )
			expdays="30";

		if ( expdays.trim().length() == 0 )
		{
			expdays="30";
		}

		thedecidingRandomNumber = request.getParameter("thedecidingRandomNumber");
		if (thedecidingRandomNumber == null)
			thedecidingRandomNumber = "";
		thedecidingRandomNumber = thedecidingRandomNumber.trim();

		Random rand = new Random();
		int tmpReq = rand.nextInt();
		Integer tmpReqNum = new Integer(tmpReq);


		//System.out.println( "User Action is " + User_Action + " " + mrnum + "  " + facility + "  " + branch_plant + "" );
		try {
			if (thedecidingRandomNumber.length() > 0) {
				String randomnumberfromsesion = session.getAttribute("LOGRNDSERCHPICKCOOKIE") == null ? "" :
					session.getAttribute("LOGRNDSERCHPICKCOOKIE").toString();
				if (randomnumberfromsesion.equalsIgnoreCase(thedecidingRandomNumber)) {
					thedecidingRandomNumber = tmpReqNum.toString();
					session.setAttribute("LOGRNDSERCHPICKCOOKIE", thedecidingRandomNumber);
				}
				else {
					thedecidingRandomNumber = tmpReqNum.toString();
					session.setAttribute("LOGRNDSERCHPICKCOOKIE", thedecidingRandomNumber);
					session.setAttribute("DATA", new Vector());

					buildHeader(branch_plant, hubInventoryGroupOvBeanCollection, User_Action, mrnum,
							User_Sort, facility, "", session, expdays);
					out.println("<BR><BR><CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>");
					return;
				}
			}
			else {
				thedecidingRandomNumber = tmpReqNum.toString();
				session.setAttribute("LOGRNDSERCHPICKCOOKIE", thedecidingRandomNumber);
			}

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
				/*Hashtable hub_list_set=new Hashtable();
         if ( this.getupdateStatus() )
         {
           hub_list_set=radian.web.HTMLHelpObj.createHubList( db,"Picking",personnelid,CompanyID );
         }
         else
         {
           hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db );
         }

         session.setAttribute( "HUB_SET",hub_list_set );*/
				Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
				String companyArrayJs = "";
				if (hub_list.size() > 0)
				{
					companyArrayJs=radian.web.HTMLHelpObj.getHubJs( hub_list_set,session ).toString();
				}
				session.setAttribute( "CATALOGS_JS",companyArrayJs );
				this.setdropDownJs( companyArrayJs );

				buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"",session,expdays );
				out.println( radian.web.HTMLHelpObj.printHTMLSelect() );

				hub_list_set=null;

			}
			else if ( User_Action.equalsIgnoreCase( "Search" ) )
			{
				//Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

				String companyArrayJs= ( String ) session.getAttribute( "CATALOGS_JS" );
				this.setdropDownJs( companyArrayJs );

				Vector searchdata=new Vector();

				session.setAttribute( "UPDATEDONE","NO" );
				session.setAttribute( "UPDATEPICKLIST","NO" );

				searchdata=this.getSearchData( branch_plant,mrnum,User_Sort,facility,expdays );

				Hashtable sum= ( Hashtable ) ( searchdata.elementAt( 0 ) );

				int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
				if ( 0 == count )
				{
					buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"",session,expdays );
					out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );

					//clean up
					searchdata=null;
					hub_list_set=null;
				}
				else
				{
					session.setAttribute( "DATA",searchdata );

					session.setAttribute( "HUB_BACK",branch_plant );
					session.setAttribute( "MR_BACK",mrnum );
					session.setAttribute( "SORT_BACK",User_Sort );
					session.setAttribute( "FAC_BACK",facility );
					session.setAttribute( "EXPDATE_BACK",expdays );


					buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"",session,expdays );
					buildDetails( searchdata,User_Action );

					//clean up
					searchdata=null;
					hub_list_set=null;

				} //when there are open orders for hub
			}
			else if ( User_Action.equalsIgnoreCase( "Back" ) )
			{
				//Vector data = (Vector) session.getAttribute("DATA");
				//Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
				String companyArrayJs= ( String ) session.getAttribute( "CATALOGS_JS" );
				this.setdropDownJs( companyArrayJs );

				String h=BothHelpObjs.makeBlankFromNull( ( String ) session.getAttribute( "HUB_BACK" ) );
				String mr=BothHelpObjs.makeBlankFromNull( ( String ) session.getAttribute( "MR_BACK" ) );
				String so=BothHelpObjs.makeBlankFromNull( ( String ) session.getAttribute( "SORT_BACK" ) );
				String fac=BothHelpObjs.makeBlankFromNull( ( String ) session.getAttribute( "FAC_BACK" ) );
				String expafter=BothHelpObjs.makeBlankFromNull( ( String ) session.getAttribute( "EXPDATE_BACK" ) );

				Vector data=this.getSearchData( h,mr,so,fac,expafter );

				session.setAttribute( "UPDATEDONE","NO" );
				session.setAttribute( "UPDATEPICKLIST","NO" );

				Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );

				int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
				buildHeader( h,hubInventoryGroupOvBeanCollection,User_Action,mr,so,fac,"",session,expafter );

				if ( 0 == count )
				{
					out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );

				}
				else
				{
					session.setAttribute( "DATA",data );

					buildDetails( data,User_Action );

				} //when there are open orders for hub

				data=null;
				hub_list_set=null;
			}
			else if ( User_Action.equalsIgnoreCase( "PrintBOLDetail" ) )
			{

				Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
				Vector synch_data=SynchServerData( request,retrn_data );

				session.setAttribute( "DATA",synch_data );
				//Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

				buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"Yes",session,expdays );
				buildDetails( synch_data,User_Action );


			}
			else if ( User_Action.equalsIgnoreCase( "PrintBOL" ) )
			{

				Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
				Vector synch_data=SynchServerData( request,retrn_data );

				session.setAttribute( "DATA",synch_data );
				//Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

				buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"No",session,expdays );
				buildDetails( synch_data,User_Action );

			}
			else if ( User_Action.equalsIgnoreCase( "Generate" ) )
			{
				Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
				Vector synch_data=SynchServerData( request,retrn_data );

				retrn_data=null;
				//Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

				session.setAttribute( "DATA",synch_data );

				String updatestatus= ( String ) session.getAttribute( "UPDATEDONE" );

				if ( "Yes".equalsIgnoreCase( updatestatus ) )
				{
					session.removeAttribute( "DATA" );
					buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"",session,expdays );
					out.println( radian.web.HTMLHelpObj.printMessageinTable( "This Form was Already Submitted." ) );
					return;
				}

				Hashtable updateresults=UpdateDetails( synch_data );
				Boolean result= ( Boolean ) updateresults.get( "RESULT" );

				session.setAttribute( "UPDATEDONE","YES" );

				Vector errordata= ( Vector ) updateresults.get( "ERRORDATA" );

				//System.out.println( "*** The result ****" + result );

				boolean resulttotest=result.booleanValue();

				buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"",session,expdays );

				if ( false == resulttotest )

				{
					if ( true == noneToUpd )
					{
						out.println( radian.web.HTMLHelpObj.printMessageinTable( "No Item was Choosen" ) );
					}
					else
					{
						out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occurred.<BR>Please Check Data and try Again." ) );
						buildDetails( errordata,"Generate" );
					}

				}
				else
				{
					if ( true == completeSuccess )
					{
						buildDetails( errordata,"Generate" );

					}
					else
					{
						buildDetails( errordata,"Generate" );

					}
				}

				//clean up
				errordata=null;
				updateresults=null;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			out.println( radian.web.HTMLHelpObj.printError( "Picking",intcmIsApplication ) );
		}
		finally
		{
			out.close();
		}

		return;
	}

	private Vector getSearchData( String BranchPlant,String mrNum,String sortBy,String facility,String expafter )
	throws Exception
	{
		Vector Data=new Vector();
		Hashtable DataH=new Hashtable();
		Hashtable summary=new Hashtable();
		boolean flagforwhere=false;

		DBResultSet dbrs=null;
		ResultSet rs=null;

		int count=0;
		summary.put( "TOTAL_NUMBER",new Integer( count ) );
		Data.addElement( summary );

		int num_rec=0;

		if ( BranchPlant.trim().length() == 0 )
		{
			return Data;
		}

		try
		{
			String query="select min(HAZMAT_ID_MISSING) HAZMAT_ID_MISSING,ACK_SENT,INVENTORY_GROUP,ITEM_ID,CRITICAL,MR_NOTES,PR_NUMBER,LINE_ITEM,to_char(NEED_DATE,'mm/dd/yyyy') NEED_DATE1,NEED_DATE_PREFIX,sum(PICK_QTY) PICK_QTY,APPLICATION,FACILITY_ID,";
			query+="PART_DESCRIPTION,substr(PACKAGING,1,200) PACKAGING,CAT_PART_NO,REQUESTOR,STOCKING_METHOD,NEED_DATE,COMPANY_ID,to_char(RELEASE_DATE,'mm/dd/yyyy hh24:mi') RELEASE_DATE, ";
			query+="to_char(sysdate,'dd Mon yyyy hh24:mi:ss') PRINT_DATE,DELIVERY_POINT,SHIP_TO_LOCATION_ID from picklist_selection_view where ";
			query+=" HUB  = " + BranchPlant + " ";
			query += " and PACKING_GROUP_ID is null ";

			flagforwhere=true;
			if ( mrNum.trim().length() > 0 )
			{
				if ( flagforwhere )
					query+="and PR_NUMBER like '%" + mrNum.trim() + "%' ";
				else
				{
					query+=" PR_NUMBER like '%" + mrNum.trim() + "%' ";
					flagforwhere=true;
				}
			}

			if ( facility.trim().length() > 0 && !"All".equalsIgnoreCase( facility ) )
			{
				if ( flagforwhere )
					query+="and FACILITY_ID = '" + facility + "' ";
				else
				{
					query+=" FACILITY_ID = '" + facility + "' ";
					flagforwhere=true;
				}
			}

			if ( expafter.trim().length() > 0 && !"All".equalsIgnoreCase( expafter ) )
			{
				if ( flagforwhere )
					query+="and (EXPIRE_DATE > sysdate + " + expafter + " or SCRAP='y' or EXPIRE_DATE is null) ";
				else
				{
					query+=" (EXPIRE_DATE = > sysdate + " + expafter + " or SCRAP='y' or EXPIRE_DATE is null) ";
					flagforwhere=true;
				}
			}

			query+="group by ACK_SENT,INVENTORY_GROUP,ITEM_ID,CRITICAL,MR_NOTES,PR_NUMBER,LINE_ITEM,to_char(NEED_DATE,'mm/dd/yyyy'),NEED_DATE_PREFIX,APPLICATION,FACILITY_ID,";
			query+="PART_DESCRIPTION,PACKAGING,CAT_PART_NO,REQUESTOR,STOCKING_METHOD,NEED_DATE,COMPANY_ID,to_char(RELEASE_DATE,'mm/dd/yyyy hh24:mi'), ";
			query+="to_char(sysdate,'dd Mon yyyy hh24:mi:ss'),DELIVERY_POINT,SHIP_TO_LOCATION_ID ";


			if ( sortBy.trim().length() > 1 )
			{
				query+="order by " + sortBy + " ";
			}

			dbrs=db.doQuery( query );
			rs=dbrs.getResultSet();

			while ( rs.next() )
			{
				DataH=new Hashtable();
				num_rec++;
				DataH.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? "&nbsp;" : rs.getString( "CAT_PART_NO" ) );
				DataH.put( "PR_NUMBER",rs.getString( "PR_NUMBER" ) == null ? "&nbsp;" : rs.getString( "PR_NUMBER" ) );
				DataH.put( "LINE_ITEM",rs.getString( "LINE_ITEM" ) == null ? "&nbsp;" : rs.getString( "LINE_ITEM" ) );
				DataH.put( "FACILITY_ID",rs.getString( "FACILITY_ID" ) == null ? "&nbsp;" : rs.getString( "FACILITY_ID" ) );
				DataH.put( "APPLICATION",rs.getString( "APPLICATION" ) == null ? "&nbsp;" : rs.getString( "APPLICATION" ) );

				DataH.put( "NEED_DATE",rs.getString( "NEED_DATE1" ) == null ? "" : rs.getString( "NEED_DATE1" ) );
				DataH.put( "PICK_QTY",rs.getString( "PICK_QTY" ) == null ? "&nbsp;" : rs.getString( "PICK_QTY" ) );
				DataH.put( "PART_DESCRIPTION",rs.getString( "PART_DESCRIPTION" ) == null ? "&nbsp;" : rs.getString( "PART_DESCRIPTION" ) );
				DataH.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? "&nbsp;" : rs.getString( "PACKAGING" ) );
				DataH.put( "REQUESTOR",rs.getString( "REQUESTOR" ) == null ? "&nbsp;" : rs.getString( "REQUESTOR" ) );
				DataH.put( "STOCKING_METHOD",rs.getString( "STOCKING_METHOD" ) == null ? "&nbsp;" : rs.getString( "STOCKING_METHOD" ) );

				DataH.put( "COMPANY_ID",rs.getString( "COMPANY_ID" ) == null ? "&nbsp;" : rs.getString( "COMPANY_ID" ) );
				DataH.put( "RELEASE_DATE",rs.getString( "RELEASE_DATE" ) == null ? "&nbsp;" : rs.getString( "RELEASE_DATE" ) );

				DataH.put( "PRINT_DATE",rs.getString( "PRINT_DATE" ) == null ? "&nbsp;" : rs.getString( "PRINT_DATE" ) );
				//DataH.put("QUANTITY",rs.getString("QUANTITY")==null?"&nbsp;":rs.getString("QUANTITY"));


				DataH.put( "DELIVERY_POINT",rs.getString( "DELIVERY_POINT" ) == null ? "&nbsp;" : rs.getString( "DELIVERY_POINT" ) );
				DataH.put( "SHIP_TO_LOCATION_ID",rs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "&nbsp;" : rs.getString( "SHIP_TO_LOCATION_ID" ) );

				DataH.put( "NEED_DATE_PREFIX",rs.getString( "NEED_DATE_PREFIX" ) == null ? "&nbsp;" : rs.getString( "NEED_DATE_PREFIX" ) );
				DataH.put( "MR_NOTES",rs.getString( "MR_NOTES" ) == null ? "" : rs.getString( "MR_NOTES" ) );
				DataH.put("CRITICAL",rs.getString("CRITICAL")==null?"&nbsp;":rs.getString("CRITICAL"));
				DataH.put( "HAZMAT_ID_MISSING",rs.getString( "HAZMAT_ID_MISSING" ) == null ? "" : rs.getString( "HAZMAT_ID_MISSING" ) );
				DataH.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
				DataH.put( "LINE_STATUS","No" );
				DataH.put( "USERCHK","" );

				DataH.put( "ACK_SENT",rs.getString( "ACK_SENT" ) == null ? "" : rs.getString( "ACK_SENT" ) );
				DataH.put( "INVENTORY_GROUP",rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ) );
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
			dbrs.close();
		}

		Hashtable recsum=new Hashtable();
		recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
		Data.setElementAt( recsum,0 );
		Data.trimToSize();
		return Data;
	}

	private Vector SynchServerData( HttpServletRequest request,Vector in_data )
	{
		Vector new_data=new Vector();
		Hashtable sum= ( Hashtable ) ( in_data.elementAt( 0 ) );
		int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
		new_data.addElement( sum );

		try
		{
			for ( int i=1; i < count + 1; i++ )
			{
				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) ( in_data.elementAt( i ) );

				String check="";
				check=BothHelpObjs.makeBlankFromNull( request.getParameter( "row_chk" + i ) );
				hD.remove( "USERCHK" );
				hD.put( "USERCHK",check );

				new_data.addElement( hD );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			out.println( radian.web.HTMLHelpObj.printError( "Picking",intcmIsApplication ) );
		}
		return new_data;
	}

	private Hashtable UpdateDetails( Vector data )
	{
		boolean result=false;

		Hashtable updateresult=new Hashtable();
		Vector errordata=new Vector();

		try
		{
			Hashtable summary=new Hashtable();
			summary= ( Hashtable ) data.elementAt( 0 );
			int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

			errordata.addElement( summary );

			boolean one_success=false;
			for ( int i=1; i < total + 1; i++ )
			{
				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) data.elementAt( i );
				String Line_Check="";
				Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );

				String dostatusupdate="";
				dostatusupdate= ( hD.get( "DOSTATUSUPDATE" ) == null ? " " : hD.get( "DOSTATUSUPDATE" ).toString() );

				if ( "yes".equalsIgnoreCase( Line_Check ) )
				{
					noneToUpd=false;
					//System.out.println( "check Found at line # : " + i );
					boolean line_result=true;
					if ( false == line_result )
					{
						completeSuccess=false;

						hD.remove( "USERCHK" );
						hD.put( "USERCHK"," " );

						hD.remove( "LINE_STATUS" );
						hD.put( "LINE_STATUS","NO" );
						errordata.addElement( hD );
					}
					if ( true == line_result )
					{
						one_success=true;
						hD.remove( "LINE_STATUS" );
						hD.put( "LINE_STATUS","YES" );

						errordata.addElement( hD );
					}
				}
				else
				{
					errordata.addElement( hD );
				}
			} //end of for
			if ( true == one_success )
			{
				result=true;
			}
		}
		catch ( Exception e )
		{
			result=false;
			e.printStackTrace();
		}
		updateresult.put( "RESULT",new Boolean( result ) );
		updateresult.put( "ERRORDATA",errordata );
		return updateresult;
	}

	private void buildHeader( String selHubnum,Collection hubInventoryGroupOvBeanCollection,String useraction,String mrnum,String sortby,
			String facility,String boloptions,HttpSession session,String expafterthis )
	{
		//StringBuffer Msg=new StringBuffer();
		//String SelectedHubName="";
		try
		{
			out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Picking","genpicklist.js",intcmIsApplication ) );
			out.println( "</HEAD>  \n" );
			out.println( "<BODY>\n" );

			out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
			out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n" );
			out.println( "</DIV>\n" );
			out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
			out.println( radian.web.HTMLHelpObj.PrintTitleBar( "<B>Picking</B>\n" ) );

			//Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
			if (hubInventoryGroupOvBeanCollection.size() < 1)
			{
				out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
				return;
			}

			out.println( "<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n" );
			out.println( this.getdropDownJs() );
			out.println( "// -->\n </SCRIPT>\n" );

			if ( selHubnum.trim().length() == 0 )
			{
				selHubnum=radian.web.HTMLHelpObj.getFirstBranchPlant( hubInventoryGroupOvBeanCollection );
			}

			/*Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
		if ( selHubnum.trim().length() == 0 )
		{
		  selHubnum=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
		}*/

			String hubname = radian.web.HTMLHelpObj.getHubName(hubInventoryGroupOvBeanCollection,selHubnum);
			PersonnelBean personnelBean = session.getAttribute( "personnelBean" ) == null ? null : (PersonnelBean) session.getAttribute( "personnelBean" );
			if (personnelBean.getPermissionBean().hasFacilityPermission("Picking",hubname,null))
			{
				this.setupdateStatus(true);
			}
			else
			{
				this.setupdateStatus(false);
			}

			out.println( "<FORM method=\"POST\" NAME=\"genPickList\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString( "SEARCH_PICKLIST" ) + "\">\n" );

			//start table #1
			out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
			out.println( "<TR>\n" );

			if ( "Generate".equalsIgnoreCase( useraction ) )
			{
				out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
				out.println( "&nbsp;\n" );
				out.println( "</TD>\n" );
				out.println( "<TD WIDTH=\"15%\">\n" );
				out.println( "&nbsp;\n" );
				out.println( "</TD>\n" );
				out.println( "<INPUT type=\"hidden\" name=\"expdays\" value=\"" + ( expafterthis.trim().length() < 1 ? "30" : expafterthis ) + "\">\n" );

				out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
				out.println( "&nbsp;\n" );
				out.println( "</TD>\n" );
				//Return
				out.println( "<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n" );
				out.println( "<INPUT TYPE=\"submit\" VALUE=\"Return to Previous\" onclick= \"return goBack(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
				out.println( "</TD>\n" );

				//Pick List
				out.println( "<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n" );
				out.println( "<INPUT TYPE=\"submit\" VALUE=\"Print Picklist\" onclick= \"return printpicks(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
				out.println( "</TD>\n" );
				out.println( "</TR>\n" );

			}
			else
			{
				//Hub List
				out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
				out.println( "<B>Hub:</B>&nbsp;\n" );
				out.println( "</TD>\n" );
				out.println( "<TD WIDTH=\"15%\">\n" );
				out.println( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" ONCHANGE=\"reshow(document.genPickList.HubName)\" size=\"1\">\n" );
				out.println( "<OPTION VALUE=\"All\">Select A Hub</OPTION>\n" );

				//Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
				//out.println(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),selHubnum));
				//out.println(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),selHubnum,hub_list));
				out.println(radian.web.HTMLHelpObj.getHubDropDown(hubInventoryGroupOvBeanCollection,selHubnum));

				/*String hub_selected;
          for ( Enumeration e=hub_list.keys(); e.hasMoreElements(); )
          {
            String branch_plant= ( String ) ( e.nextElement() );
            String hub_name= ( String ) ( hub_list.get( branch_plant ) );
            //
            hub_selected="";
            if ( branch_plant.equalsIgnoreCase( Hubnum ) )
            {
              hub_selected="selected";
              //SelectedHubName=hub_name;
            }
            out.println( "<option  " + hub_selected + " value=\"" + branch_plant + "\">" + hub_name + "</option>\n" );
          }*/
				out.println( "</SELECT>\n" );
				out.println( "</TD>\n" );

				//Blanks
				out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
				out.println( "&nbsp;\n" );
				out.println( "</TD>\n" );
				out.println( "<TD WIDTH=\"10%\">\n" );
				out.println( "&nbsp;\n" );
				out.println( "</TD>\n" );

				//Search
				out.println( "<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n" );
				out.println(
				"   <INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
				out.println( "</TD>\n" );
				out.println( "</TR>\n" );
				out.println( "<TR>\n" );

				//Facility List
				out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
				out.println( "<B>Facility:</B>&nbsp;\n" );
				out.println( "</TD>\n" );
				out.println( "<TD WIDTH=\"15%\">\n" );
				out.println( "<SELECT CLASS=\"HEADER\" NAME=\"FacName\" size=\"1\">\n" );
				Map warehouseInfo= ( Map ) session.getAttribute( "warehouseInfo" );
				Map facilityInfo= ( Map ) warehouseInfo.get( selHubnum );
				if ( "All".equalsIgnoreCase(selHubnum) || selHubnum.trim().length() == 0 || facilityInfo == null)
				{
					out.println( "<OPTION VALUE=\"\">Choose a Hub to get Facilities</OPTION>\n" );
				}
				else
				{
					out.println( "<OPTION VALUE=\"\">All</OPTION>\n" );
					out.println(radian.web.HTMLHelpObj.sorthashbyValue(facilityInfo.entrySet(),facility));

					/*Iterator it=facilityInfo.keySet().iterator();
            while ( it.hasNext() )
            {
              String facid= ( String ) it.next();
              String facname= ( String ) facilityInfo.get( facid );
              out.println( "<OPTION " + ( facid.equalsIgnoreCase( facility ) ? "selected" : "" ) + " VALUE=\"" + facid + "\">" + facname + "</OPTION>\n" );
            }*/
				}
				out.println( "</SELECT>\n" );
				out.println( "</TD>\n" );

				// Sort
				out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\">\n" );
				out.println( "<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Sort:</B></FONT>&nbsp;\n" );
				out.println( "</TD>\n" );
				out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
				out.println( "<SELECT NAME=\"SortBy\" CLASS=\"HEADER\" size=\"1\">\n" );
				Hashtable sortresult=new Hashtable();
				sortresult.put( "Facility/Work Area","FACILITY_ID,APPLICATION" );
				//sortresult.put( "Work Area","APPLICATION" );
				//sortresult.put( "Item Id","ITEM_ID" );
				sortresult.put( "Part Number","CAT_PART_NO" );
				sortresult.put( "MR","PR_NUMBER" );
				sortresult.put( "Needed","NEED_DATE" );
				out.println( radian.web.HTMLHelpObj.getDropDown( sortresult,sortby ) );

				/*if ( sortby.equalsIgnoreCase( "FACILITY_ID" ) )
          {
            //out.println("<option value=\"bin\">Bin</option>\n");
            out.println( "<option value=\"application\">Work Area</option>\n" );
            out.println( "<option selected value=\"FACILITY_ID\">Facility</option>\n" );
            out.println( "<option value=\"pr_number\">MR</option>\n" );
            out.println( "<option value=\"NEED_DATE\">Needed</option>\n" );
          }
          else if ( sortby.equalsIgnoreCase( "pr_number" ) )
          {
            //out.println("<option value=\"bin\">Bin</option>\n");
            out.println( "<option value=\"application\">Work Area</option>\n" );
            out.println( "<option value=\"FACILITY_ID\">Facility</option>\n" );
            out.println( "<option selected value=\"pr_number\">MR</option>\n" );
            out.println( "<option value=\"NEED_DATE\">Needed</option>\n" );
          }
          else if ( sortby.equalsIgnoreCase( "Needed" ) )
          {
            //out.println("<option value=\"bin\">Bin</option>\n");
            out.println( "<option value=\"application\">Work Area</option>\n" );
            out.println( "<option value=\"FACILITY_ID\">Facility</option>\n" );
            out.println( "<option value=\"pr_number\">MR</option>\n" );
            out.println( "<option selected value=\"NEED_DATE\">Needed</option>\n" );
          }
          else if ( sortby.equalsIgnoreCase( "application" ) )
          {
            //out.println("<option value=\"bin\">Bin</option>\n");
            out.println( "<option selected value=\"application\">Work Area</option>\n" );
            out.println( "<option value=\"FACILITY_ID\">Facility</option>\n" );
            out.println( "<option value=\"pr_number\">MR</option>\n" );
            out.println( "<option value=\"NEED_DATE\">Needed</option>\n" );
          }
          else
          {
            //out.println("<option selected value=\"bin\">Bin</option>\n");
            out.println( "<option value=\"application\">Work Area</option>\n" );
            out.println( "<option value=\"FACILITY_ID\">Facility</option>\n" );
            out.println( "<option value=\"pr_number\">MR</option>\n" );
            out.println( "<option value=\"NEED_DATE\">Needed</option>\n" );
          }*/

				out.println( "</SELECT>\n" );
				out.println( "</TD>\n" );

				//Gen Pick List
				out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\">\n" );
				if ( this.getupdateStatus() )
				{
					out.println(
					"<INPUT TYPE=\"submit\" VALUE=\"Generate Picklist\" onclick= \"return generate(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
				}
				else
				{
					out.println( "&nbsp;\n" );
				}
				out.println( "</TD>\n" );
				out.println( "</TR>\n" );
				out.println( "<TR>\n" );

				// search mrnum
				out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
				out.println( "<B>MR:</B>&nbsp;\n" );
				out.println( "</TD>\n" );
				out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
				out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"mrnum\" value=\"" + mrnum + "\" size=\"19\">\n" );
				out.println( "</TD>\n" );

				//Expires Date
				out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><B>Expires After </B>&nbsp;</TD>\n" );
				out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
				out.println( "<INPUT CLASS=\"HEADER\" type=\"text\" name=\"expdays\" value=\"" + ( expafterthis.trim().length() < 1 ? "30" : expafterthis ) + "\" SIZE=\"2\" MAXLENGTH=\"4\">\n" );
				out.println( "&nbsp;Days</TD>\n" );

				//Re - Gen Pick List
				out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\">\n" );
				if ( this.getupdateStatus() )
				{
					out.println( "<INPUT TYPE=\"button\" VALUE=\"Re-Generate Picklist\" onclick= \"return regenerate('" + selHubnum +
					"')\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
				}
				else
				{
					out.println( "&nbsp;\n" );
				}
				out.println( "</TD>\n" );
				out.println( "</TR>\n" );
			}
			out.println( "</TABLE>\n" );

			out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n" );
			out.println( "<tr><td>" );
			out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
			out.println( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\"NEW\">\n" );
			out.println( "<INPUT TYPE=\"hidden\" NAME=\"regenPickid\" VALUE=\"\">\n" );
			out.println( "<INPUT TYPE=\"hidden\" NAME=\"boldetails\" VALUE=\"" + boloptions + "\">\n" );
			out.println( "<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );
			out.println( "</TD></tr>" );
			out.println( "</table>\n" );

		}
		catch ( Exception e )
		{
			e.printStackTrace();
			out.println( radian.web.HTMLHelpObj.printError( "Picking",intcmIsApplication ) );
		}

		return;
	}
	//end of method

	private void buildDetails( Vector data,String SubuserAction )
	{
		//System.out.println("Enter Method : "+ "buildDetails()" );
		//StringBuffer Msg=new StringBuffer();
		String checkednon="";

		try
		{
			Hashtable summary=new Hashtable();
			summary= ( Hashtable ) data.elementAt( 0 );
			int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
			//System.out.println( "Total Number : " + total );
			int vsize=data.size();
			//System.out.println( "Vector Size  : " + vsize );
			out.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showpickingpagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n" );
			//start table #3
			//out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");
			out.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );


			int i=0; //used for detail lines
			int lineadded=0;

			out.println( "<tr align=\"center\">\n" );
			if ( this.getupdateStatus() && !SubuserAction.equalsIgnoreCase( "Generate" ) )
			{
				out.println( "<TH width=\"2%\"  height=\"38\">OK<BR><INPUT TYPE=\"checkbox\" value=\"\" onClick=\"checkall('row_chk','1')\" NAME=\"chkall\"></TH>\n" );
			}
			out.println( "<TH width=\"5%\"  height=\"38\">Facility</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Delivery Point</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Ship To</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Requestor</TH>\n" );
			out.println( "<TH width=\"7%\"  height=\"38\">MR-Line</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Release Date</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Part No.</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Type</TH>\n" );
			out.println( "<TH width=\"30%\"  height=\"38\">Part Desc</TH>\n" );
			out.println( "<TH width=\"8%\"  height=\"38\">Packaging</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Quantity</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Needed</TH>\n" );
			out.println( "<TH width=\"7%\"  height=\"38\">MR Notes</TH>\n" );
			out.println( "</tr>\n" );

			for ( int loop=0; loop < total; loop++ )
			{
				i++;
				boolean createHdr=false;

				if ( ( lineadded % 10 == 0 ) && ( lineadded != 0 ) )
				{
					createHdr=true;
				}

				if ( createHdr )
				{
					out.println( "<tr align=\"center\">\n" );
					if ( this.getupdateStatus() && !SubuserAction.equalsIgnoreCase( "Generate" ) )
					{
						out.println( "<TH width=\"2%\"  height=\"38\">OK</TH>\n" );
					}
					out.println( "<TH width=\"5%\"  height=\"38\">Facility</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Delivery Point</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Ship To</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Requestor</TH>\n" );
					out.println( "<TH width=\"7%\"  height=\"38\">MR-Line</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Release Date</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Part No.</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Type</TH>\n" );
					out.println( "<TH width=\"35%\"  height=\"38\">Part Desc</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Packaging</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Quantity</TH>\n" );
					out.println( "<TH width=\"5%\"  height=\"38\">Needed</TH>\n" );
					out.println( "<TH width=\"7%\"  height=\"38\">MR Notes</TH>\n" );
					out.println( "</tr>\n" );
					createHdr=false;
				}

				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) data.elementAt( i );

				// get main information
				String Qty=hD.get( "PICK_QTY" ).toString();
				String facility=hD.get( "FACILITY_ID" ).toString();
				String workarea=hD.get( "APPLICATION" ).toString();
				String prnumber=hD.get( "PR_NUMBER" ).toString();
				String lineitem=hD.get( "LINE_ITEM" ).toString();
				String part=hD.get( "CAT_PART_NO" ).toString();
				String needdate=hD.get( "NEED_DATE" ).toString();
				String partdesc=hD.get( "PART_DESCRIPTION" ).toString();
				String pkg=hD.get( "PACKAGING" ).toString();
				String requestor=hD.get( "REQUESTOR" ).toString();
				String type=hD.get( "STOCKING_METHOD" ).toString();
				String reldate=hD.get( "RELEASE_DATE" ).toString();
				String deliverypt=hD.get( "DELIVERY_POINT" ).toString();
				String shipto=hD.get( "SHIP_TO_LOCATION_ID" ).toString();
				String needdateprefix=hD.get( "NEED_DATE_PREFIX" ).toString();
				String hazmatIdMissing = hD.get( "HAZMAT_ID_MISSING" ).toString();
				String itemId = hD.get( "ITEM_ID" ).toString();
				String fullOrderAckSent=hD.get( "ACK_SENT" ).toString();
				String inventoryGroup=hD.get( "INVENTORY_GROUP" ).toString();
				String companyId=hD.get( "COMPANY_ID" ).toString();
				if ("USGOV".equalsIgnoreCase(companyId) & !"0".equalsIgnoreCase(lineitem))
				{
					continue;
				}

				String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
				if ( Line_Check.equalsIgnoreCase( "yes" ) )
				{
					checkednon="checked";
				}
				else
				{
					checkednon="";
				}

				String LINE_STATUS=hD.get( "LINE_STATUS" ).toString();
				String mrnotes=hD.get( "MR_NOTES" ).toString();

				String Color=" ";
				if ( lineadded % 2 == 0 )
				{
					Color="CLASS=\"white";
				}
				else
				{

					Color="CLASS=\"blue";
				}

				String ismrCritical= ( hD.get( "CRITICAL" ) == null ? "&nbsp;" : hD.get( "CRITICAL" ).toString() );
				if ( "Y".equalsIgnoreCase( ismrCritical ) )
				{
					Color="CLASS=\"red";
				}


				if ( "S".equalsIgnoreCase( ismrCritical ) )
				{
					Color="CLASS=\"pink";
				}

				if ("MISSING".equalsIgnoreCase(hazmatIdMissing))
				{
					Color = "CLASS=\"orange";
				}

				String chkbox_value="no";
				if ( checkednon.equalsIgnoreCase( "checked" ) )
				{
					chkbox_value="yes";
				}

				if ( SubuserAction.equalsIgnoreCase( "Generate" ) && "NO".equalsIgnoreCase( LINE_STATUS ) )
				{
					Color="CLASS=\"error";
				}

				if (!fullOrderAckSent.equalsIgnoreCase("Yes") )
				{
					Color="CLASS=\"invblue";
				}

				if ( SubuserAction.equalsIgnoreCase( "Generate" ) && "YES".equalsIgnoreCase( LINE_STATUS ) )
				{
					lineadded++;
					out.println( "<tr align=\"center\" valign=\"middle\">\n" );
					out.println( "<INPUT TYPE=\"hidden\" value=\"" + ( chkbox_value ) + "\" " + checkednon + " NAME=\"row_chk" + i + "\">\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + facility + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + workarea + "</td>\n" );

					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + deliverypt + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + shipto + "</td>\n" );

					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + requestor + "</td>\n" );
					out.println( "<td " + Color + "l\" width=\"7%\" height=\"38\">" + prnumber + " - " + lineitem + "</td>\n" );
					out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + reldate + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + part + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + type + "</td>\n" );
					out.println( "<td " + Color + "l\" width=\"35%\" height=\"38\">" + partdesc + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + pkg + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Qty + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + needdateprefix + " " + needdate + "</td>\n" );
					//out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + mrnotes + "</td>\n" );
					if (mrnotes.length() > 0)
					{
						out.println( "<td height=\"25\" " + Color + "l\" width=\"5%\" ID=\"lineNotesTd"+i+"\" onMouseOver=style.cursor=\"hand\">\n" );
						out.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n" );
						out.println( "<!--\n" );
						out.println( "var rowId  =  document.getElementById(\"lineNotesTd"+i+"\");\n" );
						out.println( "rowId.attachEvent('onclick',function () {\n" );
						out.println( "	showLineNotes('"+i+"');});\n" );
						out.println( "// -->\n" );
						out.println( "</SCRIPT>\n" );
						out.println( "<P ID=\"lineNoteslink"+i+"\">+</P>\n" );
						out.println( "<DIV ID=\"lineNotes"+i+"\" CLASS=\"displaynone\" onMouseOver=style.cursor=\"hand\">\n" );
						out.println( mrnotes );
						out.println( "</DIV>\n" );
						out.println( "<INPUT TYPE=\"hidden\" NAME=\"notesVisible"+i+"\" value=\"No\" >\n" );
					}
					else
					{
						out.println( "<td height=\"25\" " + Color + "l\" width=\"5%\" ID=\"lineNotesTd"+i+"\">\n" );
					}
					out.println( "</TD>\n" );

					out.println( "</tr>\n" );
					continue;
				}
				else if ( SubuserAction.equalsIgnoreCase( "Back" ) )
				{
					lineadded++;
					out.println( "<tr align=\"center\" valign=\"middle\">\n" );
					if ( this.getupdateStatus() &&
							!"MISSING".equalsIgnoreCase(hazmatIdMissing) &&
							!("USGOV".equalsIgnoreCase(companyId) & !"0".equalsIgnoreCase(lineitem)) &&
							fullOrderAckSent.equalsIgnoreCase("Yes")
					)
					{
						out.println( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" + ( chkbox_value ) + "\" onClick=\"checkpickvalue(name,this)\" " + checkednon + " NAME=\"row_chk" + i +
						"\"></td>\n" );
					}
					else
					{
						out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">&nbsp;</td>\n" );
					}
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + facility + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + workarea + "</td>\n" );

					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + deliverypt + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + shipto + "</td>\n" );

					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + requestor + "</td>\n" );
					out.println( "<td " + Color + "l\" width=\"7%\" height=\"38\">" + prnumber + " - " + lineitem + "</td>\n" );
					out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + reldate + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + part + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + type + "</td>\n" );
					out.println( "<td " + Color + "l\" width=\"35%\" height=\"38\">" + partdesc + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + pkg + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Qty + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + needdateprefix + " " + needdate + "</td>\n" );
					//out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + mrnotes + "</td>\n" );
					if (mrnotes.length() > 0)
					{
						out.println( "<td height=\"25\" " + Color + "l\" width=\"5%\" ID=\"lineNotesTd"+i+"\" onMouseOver=style.cursor=\"hand\">\n" );
						out.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n" );
						out.println( "<!--\n" );
						out.println( "var rowId  =  document.getElementById(\"lineNotesTd"+i+"\");\n" );
						out.println( "rowId.attachEvent('onclick',function () {\n" );
						out.println( "	showLineNotes('"+i+"');});\n" );
						out.println( "// -->\n" );
						out.println( "</SCRIPT>\n" );
						out.println( "<P ID=\"lineNoteslink"+i+"\">+</P>\n" );
						out.println( "<DIV ID=\"lineNotes"+i+"\" CLASS=\"displaynone\" onMouseOver=style.cursor=\"hand\">\n" );
						out.println( mrnotes );
						out.println( "</DIV>\n" );
						out.println( "<INPUT TYPE=\"hidden\" NAME=\"notesVisible"+i+"\" value=\"No\" >\n" );
					}
					else
					{
						out.println( "<td height=\"25\" " + Color + "l\" width=\"5%\" ID=\"lineNotesTd"+i+"\">\n" );
					}
					out.println( "</TD>\n" );

					out.println( "</tr>\n" );
					continue;
				}
				else if ( SubuserAction.equalsIgnoreCase( "Search" ) && "No".equalsIgnoreCase( LINE_STATUS ) )
				{
					lineadded++;
					out.println( "<tr align=\"center\" valign=\"middle\">\n" );
					if ( this.getupdateStatus() && !SubuserAction.equalsIgnoreCase( "Generate" ) )
					{
						if (!"MISSING".equalsIgnoreCase(hazmatIdMissing) &&
								!("USGOV".equalsIgnoreCase(companyId) & !"0".equalsIgnoreCase(lineitem)) &&
								fullOrderAckSent.equalsIgnoreCase("Yes")
						)
						{
							out.println( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" + ( chkbox_value ) + "\" onClick=\"checkpickvalue(name,this)\" " + checkednon + " NAME=\"row_chk" + i + "\"></td>\n" );
						}
						else
						{
							out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">&nbsp;</td>\n" );
						}
					}
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + facility + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + workarea + "</td>\n" );

					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + deliverypt + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + shipto + "</td>\n" );

					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + requestor + "</td>\n" );
					out.println( "<td " + Color + "l\" width=\"7%\" height=\"38\">" + prnumber + " - " + lineitem + "</td>\n" );
					out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + reldate + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + part + "\n" );
					if ("MISSING".equalsIgnoreCase(hazmatIdMissing))
					{
						out.println( "<A HREF=\"javascript:enterdotinfo('" + itemId + "')\">Enter Dot Info</A>\n" );
					}
					out.println( "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + type + "</td>\n" );
					out.println( "<td " + Color + "l\" width=\"35%\" height=\"38\">" + partdesc + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + pkg + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Qty + "</td>\n" );
					out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + needdateprefix + " " + needdate + "</td>\n" );
					//out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + mrnotes + "</td>\n" );
					if (mrnotes.length() > 0)
					{
						out.println( "<td height=\"25\" " + Color + "l\" width=\"5%\" ID=\"lineNotesTd"+i+"\" onMouseOver=style.cursor=\"hand\">\n" );
						out.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n" );
						out.println( "<!--\n" );
						out.println( "var rowId  =  document.getElementById(\"lineNotesTd"+i+"\");\n" );
						out.println( "rowId.attachEvent('onclick',function () {\n" );
						out.println( "	showLineNotes('"+i+"');});\n" );
						out.println( "// -->\n" );
						out.println( "</SCRIPT>\n" );
						out.println( "<P ID=\"lineNoteslink"+i+"\">+</P>\n" );
						out.println( "<DIV ID=\"lineNotes"+i+"\" CLASS=\"displaynone\" onMouseOver=style.cursor=\"hand\">\n" );
						out.println( mrnotes );
						out.println( "</DIV>\n" );
						out.println( "<INPUT TYPE=\"hidden\" NAME=\"notesVisible"+i+"\" value=\"No\" >\n" );
					}
					else
					{
						out.println( "<td height=\"25\" " + Color + "l\" width=\"5%\" ID=\"lineNotesTd"+i+"\">\n" );
					}
					out.println( "</TD>\n" );
					out.println( "</tr>\n" );
				}
			}

			out.println( "</table>\n" );
			//
			out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
			out.println( "<tr>" );
			out.println( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
			out.println( "</TD></tr>" );
			out.println( "</table>\n" );
			out.println( "<INPUT TYPE=\"hidden\" NAME=\"totallines\" ID=\"totallines\" VALUE=\""+i+"\">\n" );
			out.println( "</form>\n" );
			out.println( "</body></html>\n" );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			out.println( radian.web.HTMLHelpObj.printError( "Picking",intcmIsApplication ) );
		}

		//System.out.println("Exit Method : "+ "buildDetails()" );
		return;
	}
	//End of method

}
