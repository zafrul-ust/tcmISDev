package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class mditemInventory
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String thedecidingRandomNumber=null;
    private boolean completeSuccess = true ;
    private boolean noneToUpd = true ;
    private boolean allowupdate;
    private static org.apache.log4j.Logger invlog = org.apache.log4j.Logger.getLogger(mditemInventory.class);
    private boolean intcmIsApplication = false;

    public mditemInventory(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void setupdateStatus(boolean id)
    {
      this.allowupdate = id;
    }

    private boolean getupdateStatus()  throws Exception
    {
     return this.allowupdate;
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException
    {
	  PrintWriter out=response.getWriter();
	  response.setContentType( "text/html" );
	  HttpSession itinvsession=request.getSession();

          PersonnelBean personnelBean = (PersonnelBean) itinvsession.getAttribute("personnelBean");
          Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
          if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
          {
           intcmIsApplication = true;
          }

	  String personnelid=itinvsession.getAttribute( "PERSONNELID" ) == null ? "" : itinvsession.getAttribute( "PERSONNELID" ).toString();

	  String User_Action=request.getParameter( "UserAction" );
	  if ( User_Action == null )
		User_Action="Search";
	  User_Action = User_Action.trim();

	  String branch_plant=request.getParameter( "hubC" );
	  if ( branch_plant == null )
		branch_plant="";
	  branch_plant=branch_plant.trim();

	  String invengrp=request.getParameter( "invengrp" );
	  if ( invengrp == null )
		invengrp="";
	  invengrp=invengrp.trim();

	  String selplantid=request.getParameter( "facilityName" );
	  if ( selplantid == null )
		selplantid="";
	  selplantid=selplantid.trim();

	  String selbldgid=request.getParameter( "workAreaName" );
	  if ( selbldgid == null )
		selbldgid="";
	  selbldgid=selbldgid.trim();

	  String itemid=request.getParameter( "itemid" );
	  if ( itemid == null )
		itemid="";
	  itemid=itemid.trim();

	  String sortby=request.getParameter( "sortby" );
	  if ( sortby == null )
		sortby="";
	  sortby=sortby.trim();

	  /*thedecidingRandomNumber=request.getParameter( "thedecidingRandomNumber" );
	  if ( thedecidingRandomNumber == null )
		thedecidingRandomNumber="";
	  thedecidingRandomNumber=thedecidingRandomNumber.trim();*/
  invlog.debug("User_Action        "+User_Action+"     invengrp    "+ invengrp+"");
	  try
	  {
		/*Random rand=new Random();
		int tmpReq=rand.nextInt();
		Integer tmpReqNum=new Integer( tmpReq );*/
  	    Hashtable hub_list_set=new Hashtable();
	    hub_list_set= ( Hashtable ) itinvsession.getAttribute( "HUB_PREF_LIST" );

		String SubUser_Action=request.getParameter( "SubUserAction" );
		if ( SubUser_Action == null )
		  SubUser_Action="";

		Hashtable initialData=null;
		//Hashtable hub_list_set=new Hashtable();
		String initialDataLoaded="";
		Vector forcebuy_invengrps=new Vector();
		Vector updatezeom_invengrps=new Vector();

		initialDataLoaded=itinvsession.getAttribute( "ITEM_INVENTORY_DATA_LOADED" ) == null ? "" : itinvsession.getAttribute( "ITEM_INVENTORY_DATA_LOADED" ).toString();
		if ( initialDataLoaded.equalsIgnoreCase( "Yes" ) )
		{
		  initialData= ( Hashtable ) itinvsession.getAttribute( "INITIAL_ITEM_INVENTORY_DATA" );
		  //hub_list_set= ( Hashtable ) itinvsession.getAttribute( "ITEM_INVENTORY_HUB_SET" );
		  forcebuy_invengrps= ( Vector ) itinvsession.getAttribute( "FORCEBUY_ALLOWED_INVENGRP" );
		  updatezeom_invengrps= ( Vector ) itinvsession.getAttribute( "ZETOM_ALLOWED_INVENGRP" );
		}
		else
		{
		  String CompanyID=itinvsession.getAttribute( "COMPANYID" ).toString();
		  /*Hashtable hub_list_set1=new Hashtable();
		  if ( this.getupdateStatus() )
		  {
			hub_list_set1=radian.web.HTMLHelpObj.createHubList( db,personnelid,CompanyID );
		  }
		  else
		  {
			hub_list_set1=radian.web.HTMLHelpObj.createAllHubList( db,CompanyID );
		  }
		  itinvsession.setAttribute( "ITEM_INVENTORY_HUB_SET",hub_list_set1 );*/

		  Hashtable hub_list1 = (Hashtable)(hub_list_set.get("HUB_LIST"));
		  if (hub_list1.size() > 0)
		  {
			initialData=radian.web.dropdwnHelpObj.getgmInitialData( db,hub_list_set );
		  }
		  itinvsession.setAttribute( "INITIAL_ITEM_INVENTORY_DATA",initialData );
		  itinvsession.setAttribute( "ITEM_INVENTORY_DATA_LOADED","Yes" );
		  forcebuy_invengrps=radian.web.HTMLHelpObj.createvgrpmemlist( db,personnelid,"ForceBuy" );
		  updatezeom_invengrps=radian.web.HTMLHelpObj.createvgrpmemlist( db,personnelid,"ItemMgmt" );
		  itinvsession.setAttribute( "FORCEBUY_ALLOWED_INVENGRP",forcebuy_invengrps );
		  itinvsession.setAttribute( "ZETOM_ALLOWED_INVENGRP",updatezeom_invengrps );
		}

	  /*if ( thedecidingRandomNumber.length() > 0 )
	  {
		String randomnumberfromsesion=itinvsession.getAttribute( "LOGRNDCOOKIE" ) == null ? "" : itinvsession.getAttribute( "LOGRNDCOOKIE" ).toString();
		if ( randomnumberfromsesion.equalsIgnoreCase( thedecidingRandomNumber ) )
		{
		  thedecidingRandomNumber=tmpReqNum.toString();
		  itinvsession.setAttribute( "LOGRNDCOOKIE",thedecidingRandomNumber );
		}
		else
		{
		  thedecidingRandomNumber=tmpReqNum.toString();
		  itinvsession.setAttribute( "LOGRNDCOOKIE",thedecidingRandomNumber );
		  itinvsession.setAttribute( "ITEM_INVENTORY_DATA",new Vector() );
		  buildHeader( hub_list_set,branch_plant,selplantid,selbldgid,invengrp,itemid,sortby,initialData,out );
		  out.println( "<BR><BR><CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>" );
		  return;
		}
	  }
	  else
	  {
		thedecidingRandomNumber=tmpReqNum.toString();
		itinvsession.setAttribute( "LOGRNDCOOKIE",thedecidingRandomNumber );
	  }*/


	  /*if ( User_Action.equalsIgnoreCase( "New" ) )
	  {
		String CompanyID=itinvsession.getAttribute( "COMPANYID" ).toString();
		Hashtable hub_list1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );

		if ( hub_list1.size() < 1 )
		{
		  buildHeader( hub_list_set,branch_plant,selplantid,selbldgid,invengrp,itemid,sortby,initialData,out );
		  out.println( radian.web.HTMLHelpObj.printHTMLNoFacilities() );
		  hub_list_set=null;
		  out.close();
		}
		else
		{
		  itinvsession.setAttribute( "HUB_SET",hub_list_set );
		  buildHeader( hub_list_set,branch_plant,selplantid,selbldgid,invengrp,itemid,sortby,initialData,out );
		  out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
		  out.close();
		  hub_list_set=null;
		}
        }
		else*/ if ( User_Action.equalsIgnoreCase( "Search" ) )
		{
		  Vector openorder_data=new Vector();
		  if ( branch_plant.trim().length() > 0 )
		  {
			openorder_data=getResult( branch_plant,selplantid,selbldgid,invengrp,itemid );

			Hashtable sum= ( Hashtable ) ( openorder_data.elementAt( 0 ) );
			int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
			if ( 0 == count )
			{
			  buildHeader( hub_list_set,branch_plant,selplantid,selbldgid,invengrp,itemid,sortby,initialData,out );
			  out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
			  out.close();
			  //clean up
			  openorder_data=null;
			  hub_list_set=null;
			}
			else
			{
			  itinvsession.setAttribute( "MD_ITEM_INVENTORY_DATA",openorder_data );
			  buildHeader( hub_list_set,branch_plant,selplantid,selbldgid,invengrp,itemid,sortby,initialData,out );
			  out.println( buildDetails( openorder_data,User_Action,forcebuy_invengrps,updatezeom_invengrps,branch_plant ) );

			  out.close();
			  //clean up
			  openorder_data=null;
			  hub_list_set=null;
			} //when there are open orders for hub
		  }
		  else
		  {
			buildHeader( hub_list_set,branch_plant,selplantid,selbldgid,invengrp,itemid,sortby,initialData,out );
			out.println( radian.web.HTMLHelpObj.printHTMLNoData( "Please Select a Hub" ) );
			out.close();
			//clean up
			openorder_data=null;
			hub_list_set=null;
		  }
        }
		else if ( User_Action.equalsIgnoreCase( "Update" ) )
		{
		  Vector retrn_data= ( Vector ) itinvsession.getAttribute( "MD_ITEM_INVENTORY_DATA" );
		  Vector synch_data=SynchServerData( request,retrn_data );
		  retrn_data=null;

		  if ( SubUser_Action.equalsIgnoreCase( "UpdPage" ) )
		  {
			itinvsession.setAttribute( "MD_ITEM_INVENTORY_DATA",synch_data );
			Hashtable updateresults=UpdateDetails( synch_data,personnelid,SubUser_Action,forcebuy_invengrps );

			Boolean resultfromup= ( Boolean ) updateresults.get( "RESULT" );
			//Vector errordata= ( Vector ) updateresults.get( "ERRORDATA" );

		    //Vector errordata=getResult( branch_plant,selplantid,selbldgid,invengrp,itemid );
			//Vector errMsgs  = hubObj.getErrMsgs();
			//itinvsession.setAttribute( "MD_ITEM_INVENTORY_DATA",errordata );

			boolean resulttotest=resultfromup.booleanValue();

			out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Dispensing Sources","iteminventory.js",intcmIsApplication ) );
			out.println( "</HEAD>  \n" );
			out.println( "<BODY>  \n" );
			out.println( "<FORM method=\"POST\" NAME=\"closetap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"\">\n" );

			if ( false == resulttotest )
			{
			  if ( true == noneToUpd )
			  {
				out.println( radian.web.HTMLHelpObj.printMessageinTable( "No Buy Orders were forced." ) );
			  }
			  else
			  {
				out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occurred.<BR>Please Check Data and try Again." ) );
			  }
			}
			else
			{
				out.println( radian.web.HTMLHelpObj.printMessageinTable( "All Your Selections Were Successfully Updated" ) );
			}

			out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
			out.println( "<TR VALIGN=\"MIDDLE\">\n" );
			out.println( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );
			out.println( "<BR>Please press OK to refresh your main page." );
			out.println( "</TD>\n" );
			out.println( "</TR>" );
			out.println( "</TABLE>\n" );
			out.println( "<BR><BR>\n" );
			out.println( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Ok\" OnClick=\"submitmainpage()\" NAME=\"sendterv\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
			out.println( "</CENTER>\n" );
			out.println( "</BODY>  \n" );
			out.println( "</HTML>\n" );

			//clean up
			synch_data=null;
		  }
		  else
		  {
			out.println( radian.web.HTMLHelpObj.printError( "Inventory",intcmIsApplication ) );
		  }
		}
		else
		{
		  out.println( radian.web.HTMLHelpObj.printError( "Inventory",intcmIsApplication ) );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		out.println( radian.web.HTMLHelpObj.printError( "Inventory",intcmIsApplication ) );
	  }
	  return;
	}

  private Vector getResult( String hubname,String selplantid,String selbldgid,String invgrp,String itemsearch ) throws Exception
  {
  Vector Data=new Vector();
  Hashtable DataH=new Hashtable();
  Hashtable summary=new Hashtable();
  boolean falgforand=false;
  DBResultSet dbrs=null;
  ResultSet searchRs=null;
  int count=0;
  summary.put( "TOTAL_NUMBER",new Integer( count ) );
  Data.addElement( summary );

  try
  {
	String query="select * from Item_inventory_source_view where ";
	falgforand=false;

	if ( ! ( invgrp.trim().length() == 0 || "All".equalsIgnoreCase( invgrp ) ) )
	{
	  if ( falgforand )
		query+=" and INVENTORY_GROUP = '" + invgrp + "'  ";
	  else
		query+=" INVENTORY_GROUP = '" + invgrp + "'  ";
		falgforand=true;
	}

	if ( itemsearch.trim().length() > 0 )
	{
	  if ( falgforand )
		query+=" and ITEM_ID = '" + itemsearch + "' ";
	  else
		query+=" ITEM_ID = '" + itemsearch + "' ";
		falgforand=true;
	}

	query+=" ORDER BY ITEM_ID,INVENTORY_GROUP";

	dbrs=db.doQuery( query );
	searchRs=dbrs.getResultSet();

   /*ResultSetMetaData rsMeta1 = searchRs.getMetaData();
   System.out.println("Finished The Querry\n "+rsMeta1.getColumnCount());
   for(int i =1; i<=rsMeta1.getColumnCount(); i++)
   {
   System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");

   //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
   //System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
   //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
   }*/

	while ( searchRs.next() )
	{
	  DataH=new Hashtable();

	  DataH.put("INVENTORY_GROUP",searchRs.getString("INVENTORY_GROUP")==null?"":searchRs.getString("INVENTORY_GROUP"));
	  //DataH.put("STORAGE_AREA",searchRs.getString("STORAGE_AREA")==null?"":searchRs.getString("STORAGE_AREA"));
	  DataH.put("ITEM_ID",searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID"));
	  DataH.put("CATALOG_ID",searchRs.getString("CATALOG_ID")==null?"":searchRs.getString("CATALOG_ID"));
	  DataH.put("CAT_PART_NO",searchRs.getString("CAT_PART_NO")==null?"":searchRs.getString("CAT_PART_NO"));
	  DataH.put("PART_GROUP_NO",searchRs.getString("PART_GROUP_NO")==null?"":searchRs.getString("PART_GROUP_NO"));
	  //DataH.put("ISSUE_ON_RECEIPT",searchRs.getString("ISSUE_ON_RECEIPT")==null?"":searchRs.getString("ISSUE_ON_RECEIPT"));
	  //DataH.put("COUNT_UOM",searchRs.getString("COUNT_UOM")==null?"":searchRs.getString("COUNT_UOM"));
	  DataH.put("ITEM_DESC",searchRs.getString("ITEM_DESC")==null?"":searchRs.getString("ITEM_DESC"));
	  DataH.put("PACKAGING",searchRs.getString("PACKAGING")==null?"":searchRs.getString("PACKAGING"));
	  //DataH.put("PRICING",searchRs.getString("PRICING")==null?"":searchRs.getString("PRICING"));
	  //DataH.put("APPLICATION_DESC",searchRs.getString("APPLICATION_DESC")==null?"":searchRs.getString("APPLICATION_DESC"));
	  DataH.put("COMPANY_ID",searchRs.getString("COMPANY_ID")==null?"":searchRs.getString("COMPANY_ID"));
	  DataH.put("FACILITY_ID",searchRs.getString("FACILITY_ID")==null?"":searchRs.getString("FACILITY_ID"));
	  DataH.put("APPLICATION",searchRs.getString("APPLICATION")==null?"":searchRs.getString("APPLICATION"));
	  DataH.put("ON_HAND",searchRs.getString("ON_HAND")==null?"":searchRs.getString("ON_HAND"));
	  DataH.put("IN_PURCHASING",searchRs.getString("IN_PURCHASING")==null?"":searchRs.getString("IN_PURCHASING"));
	  //DataH.put("AREA_ID",searchRs.getString("AREA_ID")==null?"":searchRs.getString("AREA_ID"));
	  //DataH.put("PLANT_ID",searchRs.getString("PLANT_ID")==null?"":searchRs.getString("PLANT_ID"));
	  //DataH.put("BLDG_ID",searchRs.getString("BLDG_ID")==null?"":searchRs.getString("BLDG_ID"));
	  //DataH.put("DEPT_ID",searchRs.getString("DEPT_ID")==null?"":searchRs.getString("DEPT_ID"));
	  //DataH.put("PROCESS_ID",searchRs.getString("PROCESS_ID")==null?"":searchRs.getString("PROCESS_ID"));
	  //DataH.put("PERCENT",searchRs.getString("PERCENT")==null?"":searchRs.getString("PERCENT"));
	  DataH.put("ITEM_TYPE",searchRs.getString("ITEM_TYPE")==null?"":searchRs.getString("ITEM_TYPE"));
	  DataH.put("ORIGINAL_ITEM_ID",searchRs.getString("ORIGINAL_ITEM_ID")==null?"":searchRs.getString("ORIGINAL_ITEM_ID"));

	  DataH.put( "FORCE_BUY_QTY","" );
	  DataH.put( "NEED_DATE","" );
	  DataH.put( "DELETE","N" );
	  DataH.put( "UPDATEINVTYPE","N" );
	  DataH.put( "USERCHK","" );

	  Data.addElement( DataH );
	  count++;
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
	}

	Hashtable recsum=new Hashtable();
	recsum.put( "TOTAL_NUMBER",new Integer( count ) );
	Data.setElementAt( recsum,0 );

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

	  String item_id="";
	  item_id=request.getParameter( ( "item_id" + i ) );
	  if ( item_id == null )
		item_id="";

	  String invengroup="";
	  invengroup=request.getParameter( ( "inventory_group" + i ) );
	  if ( invengroup == null )
		invengroup="";

	  if ( item_id.equalsIgnoreCase( hD.get( "ORIGINAL_ITEM_ID" ).toString() ) && invengroup.equalsIgnoreCase( hD.get( "INVENTORY_GROUP" ).toString() ) )
	  {
		/*String check="";
		check=request.getParameter( ( "row_chk" + i ) );
		if ( check == null )
		  check="";
		hD.remove( "DELETE" );
		hD.put( "DELETE",check );

		String mainrow_chk="";
		mainrow_chk=request.getParameter( ( "mainrow_chk" + i ) );
		if ( mainrow_chk == null )
		  mainrow_chk="";
		hD.remove( "USERCHK" );
		hD.put( "USERCHK",check );*/

		String buy_qty="";
		buy_qty=request.getParameter( ( "buy_qty" + i ) );
		if ( buy_qty == null )
		  buy_qty="";
		hD.remove( "FORCE_BUY_QTY" );
		hD.put( "FORCE_BUY_QTY",buy_qty );

		String need_date="";
		need_date=request.getParameter( ( "need_date" + i ) );
		if ( need_date == null )
		  need_date="";
		hD.remove( "NEED_DATE" );
		hD.put( "NEED_DATE",need_date );

		/*String ior_chk="";
		ior_chk=request.getParameter( ( "ior_chk" + i ) );
		if ( ior_chk == null )
		  ior_chk="N";
		if ( ior_chk.length() == 0 )
		  ior_chk="N";

		if ( ! ( ior_chk.equalsIgnoreCase( hD.get( "ISSUE_ON_RECEIPT" ).toString() ) ) )
		{
		  hD.remove( "ISSUE_ON_RECEIPT" );
		  hD.put( "ISSUE_ON_RECEIPT",ior_chk );

		  hD.remove( "UPDATEINVTYPE" );
		  hD.put( "UPDATEINVTYPE","Yes" );
		}*/
	    }
	    new_data.addElement( hD );
	 }
   }
   catch ( Exception e )
   {
	 e.printStackTrace();
   }
   return new_data;
}

 private Hashtable UpdateDetails( Vector data,String logonid,String subuseraction,Vector allowedingrps )
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

	   String updateitemprop= ( hD.get( "UPDATEINVTYPE" ) == null ? " " : hD.get( "UPDATEINVTYPE" ).toString() );
	   String deleteiteminv= ( hD.get( "DELETE" ) == null ? "" : hD.get( "DELETE" ).toString() );
	   String forcebuyqty= ( hD.get( "FORCE_BUY_QTY" ) == null ? " " : hD.get( "FORCE_BUY_QTY" ).toString() );
	   String usercheck= ( hD.get( "USERCHK" ) == null ? " " : hD.get( "USERCHK" ).toString() );

	   if ( forcebuyqty.length() > 0 )
	   {
		 boolean line_result=false;
		 noneToUpd =false;

		 /*if ( "Y".equalsIgnoreCase( deleteiteminv ) || "Yes".equalsIgnoreCase( updateitemprop ) )
		 {
		   line_result=UpdateLine( hD,logonid );
		 }

		 if ( forcebuyqty.length() > 0 || "Y".equalsIgnoreCase( usercheck ) )*/
		 {
		   line_result=forceabuy( hD,logonid );
		 }

		 if ( !line_result )
		 {
		   completeSuccess=false;

		   //hD.remove( "LINE_STATUS" );
		   //hD.put( "LINE_STATUS","NO" );

		   errordata.addElement( hD );
		 }
		 else
		 {
		   one_success=true;
		   //hD.remove( "LINE_STATUS" );
		   //hD.put( "LINE_STATUS","YES" );

		   errordata.addElement( hD );
		 }
	   }
	   else
	   {
		 errordata.addElement( hD );
	   }
	 }

	 if ( true == one_success )
	 {
	   result=true;
	 }
   }
   catch ( Exception e )
   {
	 result=false;
	 e.printStackTrace();
	 //out.println( radian.web.HTMLHelpObj.printError( "Inventory" ) );
   }

   updateresult.put( "RESULT",new Boolean( result ) );
   updateresult.put( "ERRORDATA",errordata );

   return updateresult;
  }

  /*public boolean UpdateLine( Hashtable hD,String personnelID )
  {
  boolean result=false;
  String inventory_group=hD.get( "INVENTORY_GROUP" ) == null ? "" : hD.get( "INVENTORY_GROUP" ).toString();
  String item_id=hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString();
  String issue_on_receipt=hD.get( "ISSUE_ON_RECEIPT" ) == null ? "" : hD.get( "ISSUE_ON_RECEIPT" ).toString();
  String count_uom=hD.get( "COUNT_UOM" ) == null ? "&nbsp;" : hD.get( "COUNT_UOM" ).toString();
  String deleteline=hD.get( "DELETE" ) == null ? "" : hD.get( "DELETE" ).toString();
  String forcebuyqty=hD.get( "FORCE_BUY_QTY" ) == null ? "" : hD.get( "FORCE_BUY_QTY" ).toString();

  CallableStatement cs=null;
  try
  {
	Connection connect1=null;
	connect1=db.getConnection();

	cs=connect1.prepareCall( "{call PKG_ITEM_INVENTORY_PAGE.PR_ITEM_COUNT_MR_CREATE_UPDATE(?,?,?,?,?,?,?)}" );
	cs.setInt( 1,Integer.parseInt( item_id ) );
	cs.setString( 2,inventory_group );
	cs.setString( 3,deleteline );
	cs.setString( 4,issue_on_receipt );
	cs.setString( 5,count_uom );
	cs.setNull(6,java.sql.Types.VARCHAR);

	cs.registerOutParameter( 7,java.sql.Types.VARCHAR );
	cs.execute();

	String errorcode=cs.getString( 7 );
	invlog.info( "Result from PR_ITEM_COUNT_MR_CREATE_UPDATE procedure Error Code:  " + errorcode + "  item_id " + item_id + "  inventory_group " + inventory_group + "  deleteline " + deleteline+ "  issue_on_receipt " + issue_on_receipt+ "  deleteline " + deleteline + "" );

	if ( errorcode == null )
	{
	  result=true;
	}
	else
	{
	  result=false;
	}
  }
  catch ( Exception e )
  {
	e.printStackTrace();
	radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling PR_ITEM_COUNT_MR_CREATE_UPDATE in item inventory","Error occured while running PR_ITEM_COUNT_MR_CREATE_UPDATE \nError Msg:\n" + e.getMessage() + "\nFor Input Parameters \nitem_id " + item_id + "  inventory_group " + inventory_group + "  deleteline " + deleteline+ "  issue_on_receipt " + issue_on_receipt+ "  deleteline " + deleteline + "" );
	result=false;
  }
  finally
  {
	if ( cs != null )
	{
	  try
	  {
		cs.close();
	  }
	  catch ( SQLException se )
	  {}
	}
  }

  return result;
}*/

public boolean forceabuy( Hashtable hD,String personnelID )
{
  boolean result=false;
  String inventory_group=hD.get( "INVENTORY_GROUP" ) == null ? "" : hD.get( "INVENTORY_GROUP" ).toString();
  String item_id=hD.get( "ORIGINAL_ITEM_ID" ) == null ? "" : hD.get( "ORIGINAL_ITEM_ID" ).toString();
  String forcebuyqty=hD.get( "FORCE_BUY_QTY" ) == null ? "" : hD.get( "FORCE_BUY_QTY" ).toString();
  String catalog_id=hD.get( "CATALOG_ID" ) == null ? "" : hD.get( "CATALOG_ID" ).toString();
  String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "" : hD.get( "CAT_PART_NO" ).toString();
  String part_group_no=hD.get( "PART_GROUP_NO" ) == null ? "" : hD.get( "PART_GROUP_NO" ).toString();
  String company_id=hD.get( "COMPANY_ID" ) == null ? "" : hD.get( "COMPANY_ID" ).toString();
  String facility_id=hD.get( "FACILITY_ID" ) == null ? "" : hD.get( "FACILITY_ID" ).toString();
  String application=hD.get( "APPLICATION" ) == null ? "" : hD.get( "APPLICATION" ).toString();
  String need_date=hD.get( "NEED_DATE" ) == null ? "" : hD.get( "NEED_DATE" ).toString();


  CallableStatement cs=null;
  try
  {
	Connection connect1=null;
	connect1=db.getConnection();

	cs=connect1.prepareCall( "{call p_force_buy_item_inventory(?,?,?,?,?,?,?,?,?,?,?,?)}" );
	cs.setInt( 1,Integer.parseInt( item_id ) );
	cs.setString( 2,inventory_group );
	cs.setInt( 3,Integer.parseInt( forcebuyqty ) );
	cs.setString( 4,catalog_id );
	cs.setString( 5,cat_part_no );
	cs.setInt( 6,Integer.parseInt( part_group_no ) );
	cs.setString( 7,facility_id );
	cs.setString( 8,application );
	cs.setString( 9,company_id );
	cs.setInt( 10,Integer.parseInt( personnelID ) );
	if (need_date.length() >1){cs.setTimestamp(11,radian.web.HTMLHelpObj.getDateFromString( "",need_date));}else{cs.setNull(11,java.sql.Types.DATE);}

	cs.registerOutParameter( 12,java.sql.Types.VARCHAR );
	cs.execute();

	String errorcode=cs.getString( 12 );
	invlog.info( "Result from PR_ITEM_COUNT_MR_CREATE_UPDATE procedure Error Code:  " + errorcode + "  item_id " + item_id + "  inventory_group " + inventory_group + "  forcebuyqty " + forcebuyqty+ "  need_date "+need_date+"" );

	if ( errorcode == null )
	{
	  result=true;
	}
	else
	{
	  result=false;
	}
  }
  catch ( Exception e )
  {
	e.printStackTrace();
	radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling PR_ITEM_COUNT_MR_CREATE_UPDATE in item inventory","Error occured while running PR_ITEM_COUNT_MR_CREATE_UPDATE \nError Msg:\n" + e.getMessage() + "\nFor Input Parameters \nitem_id " + item_id + "  inventory_group " + inventory_group + "  forcebuyqty " + forcebuyqty+ " " );
	result=false;
  }
  finally
  {
	if ( cs != null )
	{
	  try
	  {
		cs.close();
	  }
	  catch ( SQLException se )
	  {}
	}
  }

  return result;
}

private void buildHeader( Hashtable hub_list_set,String selectedHub,String selplantid,String selbuilding,String invengrp,String itemserch,String sortby,
							Hashtable initialData,PrintWriter imout )
{
try
{
  imout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Dispensing Sources","iteminventory.js",intcmIsApplication ) );
  imout.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
  imout.println( "<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
  imout.println( "</HEAD>  \n" );
  imout.println( "<BODY>\n" );

  imout.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
  imout.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n" );
  imout.println( "</DIV>\n" );
  imout.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

  imout.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>Dispensing Sources for Item : "+itemserch+"</B>\n" ) );
  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
  if (hub_list.size() < 1)
  {
	imout.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
	return;
  }

  imout.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
  imout.println(radian.web.dropdwnHelpObj.createComboBoxData(initialData));
  imout.println("// -->\n </SCRIPT>\n");

  imout.println( "<FORM method=\"POST\" NAME=\"iteminventory\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"/tcmIS/hub/mditeminventory?\">\n" );
  imout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
/*
  imout.println( "<TR VALIGN=\"MIDDLE\">\n" );

  //Hub
  imout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  imout.println( "<B>Program:</B>&nbsp;\n" );
  imout.println( "</TD>\n" );
  imout.println( "<TD WIDTH=\"10%\">\n" );
  /*imout.println( "<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" size=\"1\">\n" );
  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
  Hashtable hub_prority_list= ( Hashtable ) ( hub_list_set.get( "HUB_PRORITY_LIST" ) );
  if ( hub_branch_id.trim().length() == 0 )
  {
	hub_branch_id=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
  }*/
/*
  imout.println("<SELECT CLASS=\"HEADER\" NAME=\"hubC\" ID=\"hubC\" size=\"1\" OnChange=\"hubChanged(document.iteminventory.hubC)\">\n");

  Vector hubID = (Vector)initialData.get("HUB_ID");
  Vector hubDesc = (Vector)initialData.get("HUB_DESC");

  int i = 0;
  if (selectedHub.trim().length() == 0 )
  {
	selectedHub= ( String ) hubID.firstElement(); //select the first facility if none were selected
  }
  String preSelect = "";
  for (i = 0; i < hubID.size(); i++) {
	preSelect = "";
	if (selectedHub.equalsIgnoreCase((String)hubID.elementAt(i))) {
	  preSelect = "selected";
	  selectedHub = (String)hubID.elementAt(i);
	}
	imout.println("<option "+preSelect+" value=\""+(String)hubID.elementAt(i)+"\">"+(String)hubDesc.elementAt(i)+"</option>\n");
  }

  //imout.println( radian.web.HTMLHelpObj.sorthashbyValue( hub_prority_list.entrySet(),hub_branch_id,hub_list ) );
  imout.println( "</SELECT>\n" );
  imout.println( "</TD>\n" );

  imout.println( "<TD WIDTH=\"25%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  imout.println( "&nbsp;</TD>\n" );

  imout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  imout.println( "&nbsp;</TD>\n" );

  imout.println( "</TR>\n" );

  imout.println( "<TR VALIGN=\"MIDDLE\">\n" );
  imout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
  imout.println("<B>Plant:</B>\n");
  imout.println("</TD>\n");
  imout.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
  imout.println("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.iteminventory.facilityName)\">\n");
  Hashtable fh = (Hashtable)initialData.get(selectedHub);
  //System.out.println(fh);
  Vector facID = (Vector)fh.get("PLANT_ID");
  Vector facDesc = (Vector)fh.get("PLANT_DESC");
  i = 0;
  if (selplantid.trim().length() == 0 )
  {
	selplantid= ( String ) facID.firstElement(); //select the first facility if none were selected
  }
  preSelect = "";
  for (i = 0; i < facID.size(); i++) {
	preSelect = "";
	if (selplantid.equalsIgnoreCase((String)facID.elementAt(i))) {
	  preSelect = "selected";
	  selplantid = (String)facID.elementAt(i);
	}
	imout.println("<option "+preSelect+" value=\""+(String)facID.elementAt(i)+"\">"+(String)facDesc.elementAt(i)+"</option>\n");
  }
  imout.println("</SELECT>\n");
  imout.println("</TD>\n");

  imout.println( "<TD WIDTH=\"25%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  imout.println( "&nbsp;</TD>\n" );

  // Search
  imout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
  imout.println( "   <INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n" );
  imout.println( "</TD>\n" );

  imout.println( "</TR>\n" );*/

  imout.println( "<TR VALIGN=\"MIDDLE\">\n" );
 /* // Building
  imout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  imout.println( "<B>Building:</B>&nbsp;\n" );
  imout.println( "</TD>\n" );

  imout.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
  imout.println("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\" OnChange=\"workareaChanged()\">\n");
  Hashtable h = (Hashtable)fh.get(selplantid);
  //System.out.println(h+"         "+selplantid);
  Vector application = (Vector)h.get("BLDG");
  Vector applicationDesc = (Vector)h.get("BLDG_DESC");
  for (i = 0; i < application.size(); i++) {
	preSelect = "";
	if (selbuilding.equalsIgnoreCase((String)application.elementAt(i))) {
	  preSelect = "selected";
	}
	imout.println("<option "+preSelect+" value=\""+(String)application.elementAt(i)+"\">"+(String)application.elementAt(i)+"</option>\n");
  }
  imout.println("</SELECT>\n");

  /*
  String validclass="INVALIDTEXT";
  if ( "Yes".equalsIgnoreCase( validitem ) )
  {
	validclass="HEADER";
  }
  imout.println( "<INPUT CLASS=\"" + validclass +  "\" TYPE=\"text\" NAME=\"facilityid\" ID=\"facilityid\" value=\"\" onChange=\"invalidateshipto()\" size=\"8\">\n" );
  imout.println( "<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchshiptolike\" value=\"...\" OnClick=\"getshipTo(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\"Ship To\"></BUTTON>\n" );
		 */
 /* imout.println( "</TD>\n" );

  imout.println( "<TD WIDTH=\"25%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  imout.println( "&nbsp;</TD>\n" );*/

  //Update
  imout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
  if ( this.getupdateStatus() )
  {
	imout.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Update\" onclick= \"return update(this)\" NAME=\"UpdateButton\">&nbsp;\n" );
	imout.println( "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onclick= \"cancel()\" NAME=\"otbButton\">&nbsp;\n" );
  }
  else
  {
	imout.println( "&nbsp;\n" );
	imout.println( "&nbsp;\n" );
  }
  imout.println( "</TD>\n" );
  imout.println( "</TR>\n" );
/*
  imout.println( "<TR VALIGN=\"MIDDLE\">\n" );
  // Inventory Group
  imout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  imout.println( "<B>Storage:</B>&nbsp;\n" );
  imout.println( "</TD>\n" );
  imout.println( "<TD WIDTH=\"10%\">\n" );

  imout.println( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\" OnChange=\"getbuildingaddress()\">\n" );
  imout.println( "<OPTION VALUE=\"All\">All Storage Areas</OPTION>\n" );

  if  ( ! ( selplantid.trim().length() == 0 || "All Plants".equalsIgnoreCase( selplantid ) ) )
  {
	DBResultSet dbrs=null;
	ResultSet rs=null;
	try
	{
	  dbrs=db.doQuery( "Select distinct INVENTORY_GROUP,STORAGE_AREA from item_inventory_header_view where lower(PLANT_ID) = lower('" + selplantid + "') and lower(BLDG_ID) = lower('" + selbuilding + "') " );
	  rs=dbrs.getResultSet();

	  while ( rs.next() )
	  {
		String invgrp=rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ).trim();
		String invgrpname=rs.getString( "STORAGE_AREA" ) == null ? "" : rs.getString( "STORAGE_AREA" ).trim();
		preSelect = "";
		if (invengrp.equalsIgnoreCase(invgrp)) {
		  preSelect = "selected";
		}
		imout.println("<option "+preSelect+" value=\""+invgrp+"\">"+invgrpname+"</option>\n");
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
  }
  imout.println( "</SELECT>\n" );
  imout.println( "</TD>\n" );

  imout.println( "<TD WIDTH=\"25%\" HEIGHT=\"35\" CLASS=\"announce\" ID=\"buildingaddress\" ALIGN=\"LEFT\">\n" );
  if ( ! ( invengrp.trim().length() == 0 || "All".equalsIgnoreCase( invengrp ) ) )
  {
	DBResultSet dbrs=null;
	ResultSet rs=null;

	try
	{
	  dbrs=db.doQuery( "Select distinct LOCATION_DESC || ',  Attn:' || ATTENTION || ',  ' || ADDRESS_LINE_1 || ',  ' || ADDRESS_LINE_2 || ',  ' || ADDRESS_LINE_3 || ',  ' || CITY || ',  ' || STATE_ABBREV || '-' || ZIP || ',  ' || COUNTRY_ABBREV ADDRESS from item_inventory_header_view where lower(PLANT_ID) = lower('" + selplantid + "') and lower(BLDG_ID) = lower('" + selbuilding + "') and lower(INVENTORY_GROUP) = lower('" + invengrp + "') " );
	  rs=dbrs.getResultSet();
	  String buildgaddressline = "";
	  while ( rs.next() )
	  {
		buildgaddressline=rs.getString( "ADDRESS" ) == null ? "" : rs.getString( "ADDRESS" ).trim();
	  }
	  imout.println( buildgaddressline );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ){dbrs.close();}
	}
  }
  imout.println( "&nbsp;</TD>\n" );

  //Add/Edit Work Area
  imout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
  /*if ( this.getupdateStatus() )
  {
	imout.println( "<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Add/Edit Work Area\" onclick= \"addeditworkarea()\" NAME=\"UpdateButton\">&nbsp;\n" );
  }
  else*/
		 /*
  {
	imout.println( "&nbsp;\n" );
  }
  imout.println( "</TD>\n" );
  imout.println( "</TR>\n" );
  imout.println( "<TR VALIGN=\"MIDDLE\">\n" );

  imout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  imout.println( "<B>Item:</B>&nbsp;\n" );
  imout.println( "</TD>\n" );

  imout.println( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
  imout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"itemid\" ID=\"itemid\" value=\""+itemserch+"\" size=\"8\">\n" );
  imout.println( "<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchitemidlike\" value=\"...\" OnClick=\"getitemId(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\"Item ID\"></BUTTON>\n" );
  imout.println( "</TD>\n" );

  imout.println( "<TD WIDTH=\"25%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  imout.println( "&nbsp;</TD>\n" );

  //Add/Edit Inventory Group
  imout.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
  /*if ( this.getupdateStatus() )
  {
	imout.println( "<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Add/Edit Inventory Group\" onclick= \"addeditinvgrp()\" NAME=\"UpdateButton\">&nbsp;\n" );
  }
  else*/
		  /*
  {
	imout.println( "&nbsp;\n" );
  }
  imout.println( "</TD>\n" );
  imout.println( "</TR>\n" );*/
  imout.println( "</TABLE>\n" );

  imout.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
  imout.println( "<tr><td>" );
  imout.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
  imout.println( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n" );
  imout.println( "<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );
  imout.println( "</TD></tr>" );
  imout.println( "</table>\n" );
}
catch ( Exception e )
{
  e.printStackTrace();
}
}
//end of method

private String buildDetails(Vector data,String SubuserAction,Vector allowforforcebuy, Vector allowforzeoatm,String hubname)
{
StringBuffer Msg=new StringBuffer();
StringBuffer MsgTmp1=new StringBuffer();

try
{
  Hashtable summary=new Hashtable();
  summary= ( Hashtable ) data.elementAt( 0 );
  int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
  Msg.append( "<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n" );

  int lineadded=0;
  int lastItemNum=1;
  int ItemIdCount=0;
  String Color="CLASS=\"white";
  String invcolor="CLASS=\"INVISIBLEHEADWHITE";

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
	  Msg.append( "<tr align=\"center\">\n" );
	  //Msg.append( "<TH width=\"2%\"  height=\"38\">Ok</TH>\n" );
	  //Msg.append( "<TH width=\"5%\"  height=\"38\">Catalog Part Number</TH>\n" );
	  Msg.append( "<TH width=\"5%\"  height=\"38\">Item</TH>\n" );
	  //Msg.append( "<TH width=\"5%\"  height=\"38\">Inventory Group</TH>\n" );
	  Msg.append( "<TH width=\"20%\"  height=\"38\">Description</TH>\n" );
	  Msg.append( "<TH width=\"5%\"  height=\"38\">Packaging</TH>\n" );
	  //Msg.append( "<TH width=\"5%\"  height=\"38\">Issue On Receipt</TH>\n" );
	  //Msg.append( "<TH width=\"5%\"  height=\"38\">Count UOM</TH>\n" );
	  Msg.append( "<TH width=\"5%\"  height=\"38\">On Hand</TH>\n" );
	  Msg.append( "<TH width=\"5%\"  height=\"38\">In Purchasing</TH>\n" );
	  Msg.append( "<TH width=\"5%\"  height=\"38\">Replenish Qty</TH>\n" );
	  Msg.append( "<TH width=\"5%\"  height=\"38\">Need Date</TH>\n" );
	  //Msg.append( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
	  //Msg.append( "<TH width=\"5%\"  height=\"38\">Default %</TH>\n" );
	  //Msg.append( "<TH width=\"2%\"  height=\"38\">Remove</TH>\n" );
	  Msg.append( "</tr>\n" );
	}

	  Hashtable hD=new Hashtable();
	  hD= ( Hashtable ) data.elementAt( i );

	  String Next_item="";
  	  String Next_invgrp="";
      String Next_wa="";
	  if ( ! ( i == total ) )
	  {
		Hashtable hDNext=new Hashtable();
		hDNext= ( Hashtable ) data.elementAt( i + 1 );
		Next_item=hDNext.get( "ITEM_ID" ) == null ? "" : hDNext.get( "ITEM_ID" ).toString();
		Next_invgrp=hDNext.get( "INVENTORY_GROUP" ) == null ? "" : hDNext.get( "INVENTORY_GROUP" ).toString();
		Next_wa=hDNext.get( "APPLICATION" ) == null ? "" : hDNext.get( "APPLICATION" ).toString();
	  }
	  else
	  {
		Next_item = "";
		Next_invgrp = "";
		Next_wa= "";
	  }

	  String inventory_group=hD.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : hD.get( "INVENTORY_GROUP" ).toString();
	  //String storage_area=hD.get( "STORAGE_AREA" ) == null ? "&nbsp;" : hD.get( "STORAGE_AREA" ).toString();
	  String item_id=hD.get( "ORIGINAL_ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ORIGINAL_ITEM_ID" ).toString();
	  //String catalog_id=hD.get( "CATALOG_ID" ) == null ? "&nbsp;" : hD.get( "CATALOG_ID" ).toString();
	  //String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hD.get( "CAT_PART_NO" ).toString();
	  //String part_group_no=hD.get( "PART_GROUP_NO" ) == null ? "&nbsp;" : hD.get( "PART_GROUP_NO" ).toString();
	  //String issue_on_receipt=hD.get( "ISSUE_ON_RECEIPT" ) == null ? "&nbsp;" : hD.get( "ISSUE_ON_RECEIPT" ).toString();
	  //String count_uom=hD.get( "COUNT_UOM" ) == null ? "&nbsp;" : hD.get( "COUNT_UOM" ).toString();
	  String item_desc=hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString();
	  String packaging=hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString();
	  //String pricing=hD.get( "PRICING" ) == null ? "&nbsp;" : hD.get( "PRICING" ).toString();
	  //String application_desc=hD.get( "APPLICATION_DESC" ) == null ? "&nbsp;" : hD.get( "APPLICATION_DESC" ).toString();
	  //String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
	  //String facility_id=hD.get( "FACILITY_ID" ) == null ? "&nbsp;" : hD.get( "FACILITY_ID" ).toString();
	  //String application=hD.get( "APPLICATION" ) == null ? "&nbsp;" : hD.get( "APPLICATION" ).toString();
	  String on_hand=hD.get( "ON_HAND" ) == null ? "&nbsp;" : hD.get( "ON_HAND" ).toString();
	  String in_purchasing=hD.get( "IN_PURCHASING" ) == null ? "&nbsp;" : hD.get( "IN_PURCHASING" ).toString();
	  //String area_id=hD.get( "AREA_ID" ) == null ? "&nbsp;" : hD.get( "AREA_ID" ).toString();
	  //String plant_id=hD.get( "PLANT_ID" ) == null ? "&nbsp;" : hD.get( "PLANT_ID" ).toString();
	  //String bldg_id=hD.get( "BLDG_ID" ) == null ? "&nbsp;" : hD.get( "BLDG_ID" ).toString();
	  //String dept_id=hD.get( "DEPT_ID" ) == null ? "&nbsp;" : hD.get( "DEPT_ID" ).toString();
	  //String process_id=hD.get( "PROCESS_ID" ) == null ? "&nbsp;" : hD.get( "PROCESS_ID" ).toString();
	  //String percent=hD.get( "PERCENT" ) == null ? "&nbsp;" : hD.get( "PERCENT" ).toString();
	  String LINE_STATUS = hD.get("LINE_STATUS")==null?"&nbsp;":hD.get("LINE_STATUS").toString();
	  //String deleteline = hD.get("DELETE")==null?"&nbsp;":hD.get("DELETE").toString();
	  String forcebuyqty = hD.get("FORCE_BUY_QTY")==null?"":hD.get("FORCE_BUY_QTY").toString();
	  String needdate = hD.get("NEED_DATE")==null?"":hD.get("NEED_DATE").toString();
	  //String itemtype = hD.get("ITEM_TYPE")==null?"":hD.get("ITEM_TYPE").toString();

	  String checkedmain = "";
	  String Line_Check      =  (hD.get("USERCHK")==null?"&nbsp;":hD.get("USERCHK").toString());
	  if (Line_Check.equalsIgnoreCase("yes")){checkedmain = "checked";}else{checkedmain = "";}

	  if ( SubuserAction.equalsIgnoreCase( "UpdPage" ) && "NO".equalsIgnoreCase( LINE_STATUS ) )
	  {
		Color="CLASS=\"error";
	  }

	  boolean Samepoline=false;
	  boolean FirstRow=false;
	  if ( Next_item.equalsIgnoreCase( item_id ) && Next_invgrp.equalsIgnoreCase( inventory_group ) )
	  {
		Samepoline=true;
		lastItemNum++;
	  }
	  else
	  {
		ItemIdCount++;
	  }

	  boolean forcebuyallowed=false;
	  if ( allowforforcebuy.contains( inventory_group ) )
	  {
		forcebuyallowed=true;
	  }

	  boolean invtypeallowed=false;
	  if ( allowforzeoatm.contains( inventory_group ) )
	  {
		invtypeallowed=true;
	  }

	  lineadded++;

	  if ( Samepoline )
	  {
		if ( lastItemNum == 2 )
		{
		  FirstRow=true;
		}

		if ( ( !FirstRow ) )
		{
		  MsgTmp1.append( "<TR>\n" );
		}

 	    //MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + application_desc + "</td>\n" );

		/*if (invtypeallowed)
		{
	      MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"25\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue('" + i + "')\" name=\"percentage" + i + "\"  value=\""+percentage+"\" maxlength=\"10\" size=\"5\"></td>\n" );
		  MsgTmp1.append( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"Y\"  onClick= \"alertdelete("+i+")\" NAME=\"row_chk" + i + "\"></td>\n" );
		}
		else*/
		/*{
		  MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + percent + "</td>\n" );
		  MsgTmp1.append( "<td " + Color + "\" width=\"2%\" ></td>\n" );
		}*/
		MsgTmp1.append( "<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + item_id + "\">\n" );
		MsgTmp1.append( "<input type=\"hidden\" name=\"inventory_group" + i + "\" value=\"" + inventory_group + "\">\n" );

		MsgTmp1.append( "</TR>\n" );
		if ( lastItemNum == 2 )
		{
		  MsgTmp1.append( "<input type=\"hidden\" name=\"Start" + item_id + "\" value=\"" + i + "\">\n" );
		}
	  }
	  else
	  {
		  //MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + application_desc + "</td>\n" );

		  /*if (invtypeallowed)
		  {
		    MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"25\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue('" + i + "')\" name=\"percentage" + i + "\"  value=\""+percentage+"\" maxlength=\"10\" size=\"5\"></td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"Y\"  onClick= \"alertdelete("+i+")\" NAME=\"row_chk" + i + "\"></td>\n" );
		  }
		  else*/
		  /*{
			MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + percent + "</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"2%\" ></td>\n" );
		  }*/
		  MsgTmp1.append( "<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + item_id + "\">\n" );
		  MsgTmp1.append( "<input type=\"hidden\" name=\"inventory_group" + i + "\" value=\"" + inventory_group + "\">\n" );

		  MsgTmp1.append( "</TR>\n" );
		  if ( lastItemNum == 1 )
		  {
			MsgTmp1.append( "<input type=\"hidden\" name=\"Start" + item_id + "\" value=\"" + i + "\">\n" );
		  }

	    MsgTmp1.append( "<input type=\"hidden\" name=\"Stop" + item_id + "\" value=\"" + i + "\">\n" );

		Msg.append( "<TR>\n" );

		/*if ( this.getupdateStatus() )
		{
		  Msg.append( "<td " + Color + "\" width=\"2%\" ROWSPAN=\"" + lastItemNum + "\"><INPUT TYPE=\"checkbox\" value=\"Yes\" onClick= \"checkpercentagetotal("+i+")\" " + checkedmain + "  NAME=\"mainrow_chk" + i + "\"></td>\n" );
		}
		else
		{
		  Msg.append( "<td " + Color + "\" width=\"2%\" ROWSPAN=\"" + lastItemNum + "\">&nbsp;</td>\n" );
		}*/

		//Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >" + cat_part_no + "</td>\n" );
		/*if ( invtypeallowed )
		{
		  Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" ><A HREF=\"javascript:assignnewinvegrp('" + item_id + "','"+hubname+"')\">" + item_id + "</A><input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + item_id + "\"></td>\n" );
		  Msg.append( "<td height=\"25\" " + Color + "\" width=\"2%\" ROWSPAN=\"" + lastItemNum + "\" ><A HREF=\"javascript:changeworkarea('" + item_id + "','" + inventory_group + "','"+application+"','"+facility_id+"','"+company_id+"')\">" + inventory_group + "</A></td>\n" );
		}
		else*/
		{
		  Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >" + item_id + "</td>\n" );
		  //Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >" + inventory_group + "</td>\n" );
		}

		Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >" + item_desc + "</td>\n" );
		Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >" + packaging + "</td>\n" );

		/*if (invtypeallowed)
		{
		  String checkednon="";
		  if ( "Y".equalsIgnoreCase(issue_on_receipt) )
		  {
			checkednon="checked";
		  }
		  Msg.append( "<td " + Color + "\" width=\"2%\" height=\"38\" ROWSPAN=\"" + lastItemNum + "\" ><INPUT TYPE=\"checkbox\" "+checkednon+" value=\"" +issue_on_receipt+ "\"  onClick= \"checkior("+i+")\" NAME=\"ior_chk" + i + "\"></td>\n" );
		  Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >"+count_uom+"</td>\n" );
		}
		else
		{
		  Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >"+issue_on_receipt+"</td>\n" );
		  Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >"+count_uom+"</td>\n" );
	    }*/

		Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >"+on_hand+"</td>\n" );
		Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >"+in_purchasing+"</td>\n" );
		if ( ( forcebuyallowed || invtypeallowed ) )
		{
		  Msg.append( "<td " + Color + "\" width=\"5%\" height=\"25\" ROWSPAN=\"" + lastItemNum + "\" ><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue('" + i + "')\" name=\"buy_qty" + i + "\"  value=\""+forcebuyqty+"\" maxlength=\"10\" size=\"5\"></td>\n" );
		  Msg.append( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"need_date" + i + "\" value=\"" + needdate + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.iteminventory.need_date" + i + ");\">&diams;</a></FONT></td>\n" );
		}
		else
		{
		  Msg.append( "<td " + Color + "\" width=\"5%\" height=\"25\" ROWSPAN=\"" + lastItemNum + "\">" + forcebuyqty + "</td>\n" );
		  Msg.append( "<td " + Color + "\" width=\"5%\" height=\"25\" ROWSPAN=\"" + lastItemNum + "\">&nbsp;</td>\n" );
		}

		Msg.append( MsgTmp1 );

		MsgTmp1=new StringBuffer();
		lastItemNum=1;
		if ( ( ItemIdCount ) % 2 == 0 )
		{
		  Color="CLASS=\"white";
		}
		else
		{
		  Color="CLASS=\"blue";
		}
		continue;
	  }
	  }

	  Msg.append( "</table>\n" );
	  Msg.append( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  Msg.append( "<tr>" );
	  Msg.append( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
	  Msg.append( "</TD></tr>" );
	  Msg.append( "</table>\n" );

	  Msg.append( "</form>\n" );
	  Msg.append( "</body></html>\n" );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}

    return Msg.toString();
}
//End of method
}
//END OF CLASS

