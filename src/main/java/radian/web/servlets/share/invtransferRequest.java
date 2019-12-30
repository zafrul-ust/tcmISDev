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
 * Copyright:    Copyright (c) 2003
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 05-07-04 - Sorting the Hub Drop Down based on the prority in hub_pref table
 * 07-21-04 - Code Cleanup and modifying to suit a method decleration change in pohelpobj
 * 08-03-04 - Fixed a ClassCastException
 */

public class invtransferRequest
{
  protected boolean allowupdate;
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private boolean completeSuccess=true;
  private boolean noneToUpd=true;
  private String thedecidingRandomNumber=null;
  private static org.apache.log4j.Logger invreqlog = org.apache.log4j.Logger.getLogger(invtransferRequest.class);
  private String errormsg = "";
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

  public invtransferRequest( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

    //Process the HTTP Post request
    public void doResult(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

        String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();

        String frmHubName=request.getParameter( "HubName" );
        if ( frmHubName == null )
          frmHubName="";

	    String frminvengrp=request.getParameter( "invengrp" );
		if ( frminvengrp == null )
		  frminvengrp="";
		frminvengrp=frminvengrp.trim();

        String User_Action=request.getParameter( "UserAction" );
        if ( User_Action == null )
          User_Action="New";

        String SubUser_Action=request.getParameter( "SubUserAction" );
        if ( SubUser_Action == null )
          SubUser_Action="New";

	    String searchthis=request.getParameter( "searchthis" );
		if ( searchthis == null )
		  searchthis="";
		searchthis=searchthis.trim();

		String scategory=request.getParameter( "Category" );
		if ( scategory == null )
		  scategory="ITEM_ID";

        thedecidingRandomNumber = request.getParameter("thedecidingRandomNumber");
         if (thedecidingRandomNumber == null)
               thedecidingRandomNumber = "";
         thedecidingRandomNumber = thedecidingRandomNumber.trim();

         Random rand = new Random();
         int tmpReq = rand.nextInt();
         Integer tmpReqNum = new Integer(tmpReq);

        try
        {
		  Hashtable hub_list_set=new Hashtable();
		  hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

		    if ( thedecidingRandomNumber.length() > 0 )
            {
              String randomnumberfromsesion= session.getAttribute( "INVTRNRNDNUMCOKIE" ) == null ? "" : session.getAttribute( "INVTRNRNDNUMCOKIE" ).toString();
              if ( randomnumberfromsesion.equalsIgnoreCase( thedecidingRandomNumber ) )
              {
                thedecidingRandomNumber=tmpReqNum.toString();
                session.setAttribute( "INVTRNRNDNUMCOKIE",thedecidingRandomNumber );
              }
              else
              {
                thedecidingRandomNumber=tmpReqNum.toString();
                session.setAttribute( "INVTRNRNDNUMCOKIE",thedecidingRandomNumber );
                //Hashtable hub_list_set=new Hashtable();
                hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
				Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );

                buildHeader( hub_list_set,iniinvendata,frmHubName,frminvengrp,scategory,searchthis,out );
                out.println( radian.web.HTMLHelpObj.printMessageinTable("<CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>" ) );

                return;
              }
            }
            else
            {
              thedecidingRandomNumber=tmpReqNum.toString();
              session.setAttribute( "INVTRNRNDNUMCOKIE",thedecidingRandomNumber );
            }

            if ( User_Action.equalsIgnoreCase("New") )
            {
                String CompanyID = session.getAttribute("COMPANYID").toString();
                /*Hashtable hub_list_set = new Hashtable();
                if (this.getupdateStatus())
                {
                   hub_list_set=radian.web.HTMLHelpObj.createHubList( db,personnelid,CompanyID );
                }
                else
                {
                   hub_list_set  = radian.web.HTMLHelpObj.createAllHubList(db,CompanyID);
                }*/

				//Hashtable initialinvData= radian.web.poHelpObj.getinvgrpInitialData(db,hub_list_set,false);
				//session.setAttribute( "INVENGRP_DATA",initialinvData);

				Vector allowedinvengrps  = radian.web.HTMLHelpObj.createvgrpmemlist(db,personnelid,"InvTransfer");
				session.setAttribute( "TRANSFER_ALLOWED_INVENGRP",allowedinvengrps );

				Hashtable all_hub_list_set  = radian.web.HTMLHelpObj.createAllHubList(db,CompanyID);
				session.setAttribute("ALL_HUB_SET", all_hub_list_set );
				Hashtable allinitialinvData= radian.web.poHelpObj.getinvgrpInitialData(db,all_hub_list_set,true,false);
				session.setAttribute( "ALL_INVENGRP_DATA",allinitialinvData);
				all_hub_list_set = null;

                Hashtable hub_list1 = (Hashtable)(hub_list_set.get("HUB_LIST"));

                if (hub_list1.size() < 1)
                {
                buildHeader(hub_list_set,allinitialinvData,frmHubName,frminvengrp,scategory,searchthis,out);
                out.println(radian.web.HTMLHelpObj.printHTMLNoFacilities());
                }
                else
			    {
				  session.setAttribute("HUB_SET", hub_list_set );
                  buildHeader( hub_list_set,allinitialinvData,frmHubName,frminvengrp,scategory,searchthis,out );
                  out.println(radian.web.HTMLHelpObj.printHTMLSelect());
                }
				hub_list_set  = null;
				allinitialinvData = null;
            }
			else if (  User_Action.equalsIgnoreCase("Search") )
		    {
              //Hashtable hub_list_set  = (Hashtable)session.getAttribute("HUB_SET");
			  Hashtable all_hub_list_set  = (Hashtable)session.getAttribute("ALL_HUB_SET");
			  Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "ALL_INVENGRP_DATA" );
			  Vector allowedinvengrps= ( Vector ) session.getAttribute( "TRANSFER_ALLOWED_INVENGRP" );

              Vector openorder_data=new Vector();
              int count=0;
              openorder_data=this.getResult( frmHubName,frminvengrp,scategory,searchthis );
              Hashtable sum= ( Hashtable ) ( openorder_data.elementAt( 0 ) );
              count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

              if ( 0 == count )
              {
                buildHeader( hub_list_set,iniinvendata,frmHubName,frminvengrp,scategory,searchthis,out );
                out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
				session.removeAttribute( "INV_TRNREQ_DATA" );
              }
              else
              {
                session.setAttribute( "INV_TRNREQ_DATA",openorder_data );
                buildHeader( hub_list_set,iniinvendata,frmHubName,frminvengrp,scategory,searchthis,out );
                buildDetails( openorder_data,all_hub_list_set,iniinvendata,allowedinvengrps,out );
              }
			  openorder_data=null;
              hub_list_set=null;
            }
            else if ( User_Action.equalsIgnoreCase( "generatexls" ) )
            {
              Vector retrndata= ( Vector ) session.getAttribute( "INV_TRNREQ_DATA" );
              out.println( buildxlsDetails( retrndata,personnelid ) );
              retrndata=null;
            }
			else if ( User_Action.equalsIgnoreCase( "UPDATE" ) )
			{
			  Vector retrn_data=new Vector();
			  Vector synch_data=new Vector();
			  //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
			  Hashtable all_hub_list_set= ( Hashtable ) session.getAttribute( "ALL_HUB_SET" );
			  Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "ALL_INVENGRP_DATA" );
			  Vector allowedinvengrps= ( Vector ) session.getAttribute( "TRANSFER_ALLOWED_INVENGRP" );

			  retrn_data= ( Vector ) session.getAttribute( "INV_TRNREQ_DATA" );
			  synch_data=SynchServerData( request,retrn_data );

			  retrn_data=null;

			  Hashtable updateresults=UpdateDetails( synch_data,personnelid );
			  Boolean result= ( Boolean ) updateresults.get( "RESULT" );
			  boolean resulttotest=result.booleanValue();
			  Vector errordata= ( Vector ) updateresults.get( "ERRORDATA" );

			  buildHeader( hub_list_set,iniinvendata,frmHubName,frminvengrp,scategory,searchthis,out );
			  if ( resulttotest )
			  {
				if ( true == completeSuccess )
				{
				  out.println( radian.web.HTMLHelpObj.printMessageinTable( "All Your Selections Were Successfully Updated" ) );
				}
				else
				{
				  out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occurred.<BR>"+errormsg+"<BR>Please Check Data and try Again." ) );
				}
			  }
			  else
			  {
				if ( true == noneToUpd )
				{
				  out.println( radian.web.HTMLHelpObj.printMessageinTable( "No Transfer requests were made. Please check your data and try again." ) );
				}
				else
				{
				  out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occurred.<BR>"+errormsg+"<BR>Please Check Data and try Again." ) );
				}
			  }
			  buildDetails( errordata,all_hub_list_set,iniinvendata,allowedinvengrps,out );

		errordata=null;
		hub_list_set=null;
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Inventory Transfer Page",intcmIsApplication ) );
	}
	finally
	{
	  out.close();
	}
}

    private StringBuffer buildxlsDetails( Vector data,String personnelID ) throws Exception
    {
	  //StringBuffer Msg=new StringBuffer();
	  String url="";
	  String file="";

	  Random rand=new Random();
	  int tmpReq=rand.nextInt();
	  Integer tmpReqNum=new Integer( tmpReq );

	  String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	  String writefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();

	  file=writefilepath + personnelID + tmpReqNum.toString() + "invtransreq.csv";
	  url=wwwHome + "reports/temp/" + personnelID + tmpReqNum.toString() + "invtransreq.csv";

	  try
	  {
		PrintWriter pw=new PrintWriter( new FileOutputStream( file ) );

       Hashtable summary=new Hashtable();
       summary= ( Hashtable ) data.elementAt( 0 );
       int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
       pw.print( "Item,Description,Packaging,Hub,Inventory Group,Qty Available\n" );

       int i=0;
       for ( int loop=0; loop < total; loop++ )
       {
         i++;
         Hashtable invtranData=new Hashtable();
         invtranData= ( Hashtable ) data.elementAt( i );

		 //String pick_date=invtranData.get( "PICK_DATE" ) == null ? "&nbsp;" : invtranData.get( "PICK_DATE" ).toString();
		 String inventory_group=invtranData.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : invtranData.get( "INVENTORY_GROUP" ).toString();
		 String hub=invtranData.get( "HUB" ) == null ? "&nbsp;" : invtranData.get( "HUB" ).toString();
		 String item_id=invtranData.get( "ITEM_ID" ) == null ? "&nbsp;" : invtranData.get( "ITEM_ID" ).toString();
		 String item_desc=invtranData.get( "ITEM_DESC" ) == null ? "&nbsp;" : invtranData.get( "ITEM_DESC" ).toString();
		 String packaging=invtranData.get( "PACKAGING" ) == null ? "&nbsp;" : invtranData.get( "PACKAGING" ).toString();
		 String quantity=invtranData.get( "QUANTITY" ) == null ? "&nbsp;" : invtranData.get( "QUANTITY" ).toString();
		 //String xfer_source_originate=invtranData.get( "XFER_SOURCE_ORIGINATE" ) == null ? "&nbsp;" : invtranData.get( "XFER_SOURCE_ORIGINATE" ).toString();

         pw.print( "\"" + item_id + "\"," );
         pw.print( "\"" + item_desc + "\"," );
         pw.print( "\"" + packaging + "\"," );
		 pw.print( "\"" + hub + "\"," );
		 pw.print( "\"" + inventory_group + "\"," );
         pw.print( "\"" + quantity + "\"\n" );
       }
	   pw.close();
     }
     catch ( Exception e )
     {
       e.printStackTrace();
       return radian.web.HTMLHelpObj.printError( "tcmIS Hub Cabinet Management",intcmIsApplication );
     }

       StringBuffer MsgSB=new StringBuffer();
       if ( url.length() > 0 )
       {
         MsgSB.append( "<HTML><HEAD>\n" );
         MsgSB.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
         MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
         MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
         MsgSB.append( "<TITLE>tcmIS Hub Cabinet Management</TITLE>\n" );
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
         MsgSB.append( "<TITLE>tcmIS Hub Cabinet Management</TITLE>\n" );
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

    private Vector getResult( String hubnum,String invengrp,String sortby,String searchfor )  throws Exception
    {
      Vector Data=new Vector();
      Hashtable DataH=new Hashtable();
      Hashtable summary=new Hashtable();
      DBResultSet dbrs=null;
      ResultSet searchRs=null;
      int count=0;
      summary.put( "TOTAL_NUMBER",new Integer( count ) );
      Data.addElement( summary );
	  boolean falgforand = false;

      try
      {
        String query="select to_char(sysdate,'mm/dd/yyyy') PICK_DATE,INVENTORY_GROUP,INVENTORY_GROUP_NAME, HUB, ITEM_ID, ITEM_DESC, PACKAGING, QUANTITY,XFER_SOURCE_ORIGINATE from transferable_inventory_view where ";

		if ( hubnum.length() > 0 )
	   {
		 query+=" HUB = '" + hubnum + "' ";
		 falgforand=true;
	   }
	   else
	   {
		 query+=" rownum = 0 ";
		 falgforand=true;
	   }

	   if ( invengrp.length() > 0 && !"All".equalsIgnoreCase( invengrp ) )
	   {
		 if ( falgforand )
		   query+="and INVENTORY_GROUP = '" + invengrp + "' ";
		 else
		 {
		   query+=" INVENTORY_GROUP = '" + invengrp + "' ";
		   falgforand=true;
		 }
	  }

	  if ( searchfor.length() > 0 )
	   {
		 if ( falgforand )
		   query+="and instr(lower(ITEM_ID || ITEM_DESC) ,lower ('"+searchfor+"')) > 0 ";
		 else
		 {
		   query+=" instr(lower(ITEM_ID || ITEM_DESC) ,lower ('"+searchfor+"')) > 0 ";
		   falgforand=true;
		 }
	  }

        if ( sortby.length() > 0 )
        {
          query+=" order by " + sortby + "";
        }

        dbrs=db.doQuery( query );
        searchRs=dbrs.getResultSet();

        while ( searchRs.next() )
        {
		  DataH=new Hashtable();
		  //PICK_DATE
		  DataH.put( "PICK_DATE",searchRs.getString( "PICK_DATE" ) == null ? "" : searchRs.getString( "PICK_DATE" ) );
		  //INVENTORY_GROUP
		  DataH.put( "INVENTORY_GROUP",searchRs.getString( "INVENTORY_GROUP" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP" ) );
		  //HUB
		  DataH.put( "HUB",searchRs.getString( "HUB" ) == null ? "" : searchRs.getString( "HUB" ) );
		  //ITEM_ID
		  DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
		  //ITEM_DESC
		  DataH.put( "ITEM_DESC",searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ) );
		  //PACKAGING
		  DataH.put( "PACKAGING",searchRs.getString( "PACKAGING" ) == null ? "" : searchRs.getString( "PACKAGING" ) );
		  //QUANTITY
		  DataH.put( "QUANTITY",searchRs.getString( "QUANTITY" ) == null ? "" : searchRs.getString( "QUANTITY" ) );
		  //XFER_SOURCE_ORIGINATE
		  DataH.put( "XFER_SOURCE_ORIGINATE",searchRs.getString( "XFER_SOURCE_ORIGINATE" ) == null ? "" : searchRs.getString( "XFER_SOURCE_ORIGINATE" ) );
		  //INVENTORY_GROUP_NAME
		  DataH.put( "INVENTORY_GROUP_NAME",searchRs.getString( "INVENTORY_GROUP_NAME" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP_NAME" ) );

		  DataH.put( "UPDATESTATUS","" );
		  DataH.put( "TO_HUB","" );
		  DataH.put( "TO_INVEN_GRP","" );
		  DataH.put( "QTY_SENT","" );

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
      Data.trimToSize();
      return Data;
    }

	private Vector SynchServerData( HttpServletRequest request,Vector in_data ) throws Exception
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

		  String ssitem_id=hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString();
		  String ssupdatestatus=hD.get( "UPDATESTATUS" ) == null ? "" : hD.get( "UPDATESTATUS" ).toString();

		  String item_id=request.getParameter( ( "item_id" + i ) ).toString();
		  if ( item_id == null )
			item_id="";
		  item_id=item_id.trim();

		  if ( ssitem_id.equalsIgnoreCase( item_id ) && !"Done".equalsIgnoreCase( ssupdatestatus ) )
		  {
			String lineHubName=request.getParameter( "lineHubName" + i + "" );
			if ( lineHubName == null )
			  lineHubName="";
			lineHubName=lineHubName.trim();

			if ( ( lineHubName.length() > 0 ) )
			{
			  hD.remove( "TO_HUB" );
			  hD.put( "TO_HUB",lineHubName );
			}

			String lineinvengrp=request.getParameter( "lineinvengrp" + i + "" );
			if ( lineinvengrp == null )
			  lineinvengrp="";
			lineinvengrp=lineinvengrp.trim();

			if ( ( lineinvengrp.length() > 0 && !"All".equalsIgnoreCase(lineinvengrp)) )
			{
			  hD.remove( "TO_INVEN_GRP" );
			  hD.put( "TO_INVEN_GRP",lineinvengrp );
			}

			String qtysent=request.getParameter( "qty_sent" + i + "" );
			if ( qtysent == null )
			  qtysent="";
			qtysent=qtysent.trim();

			if ( ( qtysent.length() > 0 ) )
			{
			  hD.remove( "QTY_SENT" );
			  hD.put( "QTY_SENT",qtysent );
			}

			String pick_date=request.getParameter( "pick_date" + i + "" );
			if ( pick_date == null )
			  pick_date="";
			pick_date=pick_date.trim();

			if ( ( pick_date.length() > 0 ) )
			{
			  hD.remove( "PICK_DATE" );
			  hD.put( "PICK_DATE",pick_date );
			}
		  }
		  new_data.addElement( hD );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		throw e;
	  }
	  return new_data;
	}

    private Hashtable UpdateDetails(Vector data, String logonid )
    {
        boolean result = false;
        Hashtable updateresult = new Hashtable();
		Vector errordata=new Vector();
		Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );
		errordata.addElement( sum );

        int count =0;

        try
        {
            Hashtable summary = new Hashtable();
            summary = (Hashtable)data.elementAt(0);
            int total = ((Integer)(summary.get("TOTAL_NUMBER"))).intValue()  ;
            int linecheckcount = 0;

            for (int i = 1; i < total+1  ; i++)
            {
                Hashtable invtranData = new Hashtable();
                invtranData = (Hashtable) data.elementAt(i);
				String pick_date=invtranData.get( "PICK_DATE" ) == null ? "" : invtranData.get( "PICK_DATE" ).toString();
				String inventory_group=invtranData.get( "INVENTORY_GROUP" ) == null ? "" : invtranData.get( "INVENTORY_GROUP" ).toString();
				//String hub=invtranData.get( "HUB" ) == null ? "" : invtranData.get( "HUB" ).toString();
				String item_id=invtranData.get( "ITEM_ID" ) == null ? "" : invtranData.get( "ITEM_ID" ).toString();
				String quantity=invtranData.get( "QUANTITY" ) == null ? "" : invtranData.get( "QUANTITY" ).toString();
				String xfer_source_originate=invtranData.get( "XFER_SOURCE_ORIGINATE" ) == null ? "" : invtranData.get( "XFER_SOURCE_ORIGINATE" ).toString();
				String tosendhub=invtranData.get( "TO_HUB" ) == null ? "" : invtranData.get( "TO_HUB" ).toString();
				String tosendinvgrp=invtranData.get( "TO_INVEN_GRP" ) == null ? "" : invtranData.get( "TO_INVEN_GRP" ).toString();
				String qtysent=invtranData.get( "QTY_SENT" ) == null ? "" : invtranData.get( "QTY_SENT" ).toString();
				String updatestatus=invtranData.get( "UPDATESTATUS" ) == null ? "" : invtranData.get( "UPDATESTATUS" ).toString();

				invreqlog.info("tosendhub "+tosendhub+"   tosendinvgrp "+tosendinvgrp+"   qtysent "+qtysent+"   updatestatus "+updatestatus+"");

                if (tosendhub.length() > 0 && tosendinvgrp.length() > 0 && qtysent.length() > 0 && !"Done".equalsIgnoreCase(updatestatus) )
                {
                    noneToUpd = false;
                    linecheckcount++;
                    boolean line_result = false;

                    line_result = this.createtrnsrequest(inventory_group,tosendinvgrp,item_id,qtysent,pick_date,logonid);

                    if ( false == line_result)
                    {
                      completeSuccess = false;
                      result = false;
                      invtranData.remove("UPDATESTATUS");
                      invtranData.put("UPDATESTATUS", "NO");
                    }
                    else
                    {
					  result = true;
					  invtranData.remove("UPDATESTATUS");
                      invtranData.put("UPDATESTATUS", "Done");
                    }
                }
		        errordata.addElement(invtranData);
            } //end of for
        }
        catch (Exception e)
        {
            result = false;
            e.printStackTrace();
        }
        updateresult.put("RESULT",new Boolean(result));
        updateresult.put("ERRORDATA",errordata);

        return updateresult;
    }

    private boolean createtrnsrequest( String frminvgroup,String tosendinvgrp,String item_id,String qtyneeded,String pick_date,String personneld )
    {
      boolean result=false;
      Connection connect1;
      CallableStatement cs=null;
	  int transreqID =0;

      try
      {
        connect1=db.getConnection();
        cs=connect1.prepareCall( "{call p_inventory_transfer_request_m(?,?,?,?,?,?,?)}" );
		Timestamp pickdate=radian.web.HTMLHelpObj.getDateFromString( "MM/dd/yyyy",pick_date );

        cs.setString( 1,frminvgroup );
	    cs.setString( 2,tosendinvgrp );
		cs.setInt( 3,Integer.parseInt( item_id ) );
        cs.setInt( 4,Integer.parseInt( qtyneeded ) );
		cs.setNull( 5,java.sql.Types.DATE );
		cs.setTimestamp( 6,pickdate );
        cs.registerOutParameter( 7,java.sql.Types.NUMERIC );
        cs.execute();

        transreqID= cs.getInt( 7 );
        if ( transreqID == 0 )
        {
          result=false;
		  errormsg += "Could not create Transfer Request for Item Id : "+item_id+" ";
        }
		else
		{
		  invreqlog.info("The Created Transfer Request:  "+transreqID+"");
		  result=true;
		}

        connect1.commit();
        if ( cs != null )
        {
          cs.close();
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        radian.web.emailHelpObj.senddatabaseHelpemails( "Error calling p_inventory_transfer_request_m in Inventory Transfer Page","Error calling p_inventory_transfer_request_m\nError Msg:\n"+e.getMessage()+"\n item_id  "+item_id+"   qtyneeded  "+qtyneeded+"   Personnel ID  "+personneld+"");

        result=false;
      }

      return result;
    }

    private void buildHeader(Hashtable hub_list_set,Hashtable iniinvendata, String hub_branch_id, String selinvengrp,String category,String searchstring,PrintWriter invout )
    {
	  //StringBuffer Msg=new StringBuffer();

	  try
	  {
		invout.print( radian.web.HTMLHelpObj.printHTMLHeader( "Inventory Transfer Page","invtransferreq.js",intcmIsApplication ) );
		invout.print( "</HEAD>  \n" );
		invout.print( "<BODY>\n" );

		invout.print( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
		invout.print( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
		invout.print( "</DIV>\n" );
		invout.print( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

		invout.print( radian.web.HTMLHelpObj.PrintTitleBar( "<B>Inventory Transfer Page</B>\n" ) );
		Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		if (hub_list.size() < 1)
		{
		  invout.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
		  return;
		}
		invout.print( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n" );
		invout.print( radian.web.poHelpObj.createhubinvgrjs( iniinvendata ) );
		invout.print( "// -->\n </SCRIPT>\n" );

		String servlettocall=radian.web.tcmisResourceLoader.getinvtransferrequrl();
		invout.print( "<FORM method=\"POST\" NAME=\"invtransreq\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + servlettocall + "\">\n" );
		invout.print( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"5\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
		invout.print( "<TR>\n" );

		//Hub
		invout.print( "<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		invout.print( "<B>From Hub:</B>&nbsp;\n" );
		invout.print( "</TD>\n" );
		invout.print( "<TD WIDTH=\"10%\">\n" );
		invout.print( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" OnChange=\"hubchanged(document.invtransreq.HubName)\" size=\"1\">\n" );
		//Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
		if ( hub_branch_id.trim().length() == 0 )
        {
		  hub_branch_id=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
	    }
		invout.print(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),hub_branch_id,hub_list));
		invout.print( "</SELECT>\n" );
		invout.print( "</TD>\n" );

		invout.print( "<TD WIDTH=\"7%\" ALIGN=\"RIGHT\">\n" );
		invout.print("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchthis\" value=\""+searchstring+"\" size=\"19\">\n");
		invout.print( "</TD>\n" );

	    //Search
		invout.print( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
		invout.print( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"processButton\">&nbsp;\n" );
		invout.print( "</TD>\n" );

	    //Process
		invout.print( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\">\n" );
		if (this.getupdateStatus())
		{
		  invout.print( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Process\" onclick= \"return update(this)\" NAME=\"processButton\">&nbsp;\n" );
		}
		else
		{
		  invout.print( "&nbsp;\n" );
		}
		invout.print( "</TD>\n" );

		invout.print( "</TR>\n" );

		invout.print( "<TR>\n" );

		invout.print( "<TD HEIGHT=45 WIDTH=\"7%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		invout.print( "<B>From Inv Grp:</B>&nbsp;\n" );
		invout.print( "</TD>\n" );
		invout.print( "<TD WIDTH=\"10%\">\n" );
		invout.print( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n" );
		Hashtable fh= ( Hashtable ) iniinvendata.get( hub_branch_id );
		Vector invidV= ( Vector ) fh.get( "INVENTORY_GROUP" );
		Vector invidName = ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );

		for ( int i=0; i < invidV.size(); i++ )
		{
		  String preSelect="";
		  String wacid= ( String ) invidV.elementAt( i );
		  String invgName= ( String ) invidName.elementAt( i );

		  if ( selinvengrp.equalsIgnoreCase( wacid ) )
		  {
			preSelect="selected";
		  }
		  invout.print( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + "</option>\n" );
		}
		invout.print( "</SELECT>\n" );
		invout.print( "</TD>\n" );

		invout.print( "<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		invout.print( "<B>Sort By:</B>&nbsp;\n" );
		invout.print( "</TD>\n" );
		invout.print( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
		invout.print( "<SELECT CLASS=\"HEADER\" NAME=\"Category\" size=\"1\">\n" );

		if ( category.equalsIgnoreCase( "ITEM_DESC" ) )
		{
		  invout.print( "<option selected value=\"ITEM_DESC\">Description</option>\n" );
		  invout.print( "<option value=\"ITEM_ID\">Item ID</option>\n" );
		}
		else
		{
		  invout.print( "<option value=\"ITEM_DESC\">Description</option>\n" );
		  invout.print( "<option selected value=\"ITEM_ID\">Item ID</option>\n" );
		}

		invout.print( "</SELECT>\n" );
		invout.print( "</TD>\n" );

		invout.print( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
		invout.print( "&nbsp;\n" );
		invout.print( "</TD>\n" );

		//Create XLS
		invout.print( "<TD WIDTH=\"5%\" ALIGN=\"CENTER\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
		invout.print( "<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create XLS\" onclick= \"return createxls(this)\" NAME=\"createXlsButton\">&nbsp;\n" );
		invout.print( "</TD>\n" );
		invout.print( "</TR>\n" );

		invout.print( "</TABLE>\n" );
		invout.print( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
		invout.print( "<TR><td>" );
		invout.print( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
		invout.print( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n" );
		invout.print( "<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );
		invout.print( "</TD></TR>" );
		invout.print( "</TABLE>\n" );
	  }

	  catch ( Exception e )
	  {
		e.printStackTrace();
		invout.print(radian.web.HTMLHelpObj.printError( "tcmIS Hub Inventory Transfer Page",intcmIsApplication ));
		return;
	  }
	  return;
	}

	private void buildDetails( Vector data,Hashtable hub_list_set,Hashtable iniigData,Vector allowedingrps,PrintWriter invout )
	{
	  //StringBuffer Msg=new StringBuffer();
	  int i=0;
	  try
	  {
      Hashtable summary=new Hashtable();
      summary= ( Hashtable ) data.elementAt( 0 );
      int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
      invout.print( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

      for ( int loop=0; loop < total; loop++ )
      {
        i++;
        boolean createHdr=false;
		boolean readonlyline=false;

        if ( loop % 10 == 0 )
        {
          createHdr=true;
        }

        if ( createHdr )
        {
          invout.print( "<tr align=\"center\">\n" );
          invout.print( "<TH width=\"5%\" height=\"38\">Item</TH>\n" );
          invout.print( "<TH width=\"15%\" HEIGHT=\"38\">Description</TH>\n" );
          invout.print( "<TH width=\"5%\" height=\"38\">Packaging</TH>\n" );
		  invout.print( "<TH width=\"5%\" height=\"38\">From Inven Grp</TH>\n" );
		  invout.print( "<TH width=\"5%\" height=\"38\">To Hub</TH>\n" );
		  invout.print( "<TH width=\"5%\" height=\"38\">To Inven Grp</TH>\n" );
          invout.print( "<TH width=\"5%\" height=\"38\">Qty Available</TH>\n" );
		  invout.print( "<TH width=\"5%\" height=\"38\">Qty to Send</TH>\n" );
          invout.print( "<TH width=\"5%\" height=\"38\">Date Sent</TH>\n" );
          invout.print( "</TR>\n" );
        }

        Hashtable invtranData=new Hashtable();
        invtranData= ( Hashtable ) data.elementAt( i );

		String pick_date=invtranData.get( "PICK_DATE" ) == null ? "&nbsp;" : invtranData.get( "PICK_DATE" ).toString();
		String inventory_group=invtranData.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : invtranData.get( "INVENTORY_GROUP" ).toString();
		String inventory_group_name=invtranData.get( "INVENTORY_GROUP_NAME" ) == null ? "&nbsp;" : invtranData.get( "INVENTORY_GROUP_NAME" ).toString();
		String hub=invtranData.get( "HUB" ) == null ? "&nbsp;" : invtranData.get( "HUB" ).toString();
		String item_id=invtranData.get( "ITEM_ID" ) == null ? "&nbsp;" : invtranData.get( "ITEM_ID" ).toString();
		String item_desc=invtranData.get( "ITEM_DESC" ) == null ? "&nbsp;" : invtranData.get( "ITEM_DESC" ).toString();
		String packaging=invtranData.get( "PACKAGING" ) == null ? "&nbsp;" : invtranData.get( "PACKAGING" ).toString();
		String quantity=invtranData.get( "QUANTITY" ) == null ? "" : invtranData.get( "QUANTITY" ).toString();
		String xfer_source_originate=invtranData.get( "XFER_SOURCE_ORIGINATE" ) == null ? "&nbsp;" : invtranData.get( "XFER_SOURCE_ORIGINATE" ).toString();
		String updatestatus=invtranData.get( "UPDATESTATUS" ) == null ? "" : invtranData.get( "UPDATESTATUS" ).toString();
		String tosendhub=invtranData.get( "TO_HUB" ) == null ? "" : invtranData.get( "TO_HUB" ).toString();
		String tosendinvgrp=invtranData.get( "TO_INVEN_GRP" ) == null ? "" : invtranData.get( "TO_INVEN_GRP" ).toString();
		String qtysent=invtranData.get( "QTY_SENT" ) == null ? "" : invtranData.get( "QTY_SENT" ).toString();

        String Color=" ";
        if ( i % 2 == 0 )
        {
          Color="CLASS=\"Inblue";
        }
        else
        {
          Color="CLASS=\"Inwhite";
        }

		if ( !this.getupdateStatus() || "Done".equalsIgnoreCase(updatestatus) || !allowedingrps.contains(inventory_group) )
		{
		  readonlyline = true;
		}

        invout.print( "<tr align=\"center\">\n" );
		invout.print( "<td " + Color + "l\" width=\"5%\">" + item_id + "<INPUT TYPE=\"hidden\" NAME=\"item_id"+i+"\" VALUE=\""+item_id+"\"></td>\n" );
		invout.print( "<td " + Color + "l\" width=\"15%\">" + item_desc + "</td>\n" );
		invout.print( "<td " + Color + "l\" width=\"5%\">" + packaging + "</td>\n" );
		invout.print( "<td " + Color + "l\" width=\"5%\">" + inventory_group_name + "</td>\n" );

		if (readonlyline)
		{
		invout.print( "<td " + Color + "l\" width=\"5%\">" + tosendhub + "</td>\n" );
		invout.print( "<td " + Color + "l\" width=\"5%\">" + tosendinvgrp + "</td>\n" );
        invout.print( "<td " + Color + "l\" width=\"5%\">" + quantity + "</td>\n" );
		invout.print( "<td " + Color + "l\" width=\"5%\">" + qtysent + "</td>\n" );
		if ( "Y".equalsIgnoreCase( xfer_source_originate ) )
		{
		  invout.print( "<td " + Color + "l\" width=\"5%\">" + pick_date + "</td>\n" );
		}
		else
		{
		  invout.print( "<td " + Color + "l\" width=\"5%\">&nbsp;</td>\n" );
		}
		}
		else
		{
		  invout.print( "<TD " + Color + "l\" WIDTH=\"5%\">\n" );
		  invout.print( "<SELECT CLASS=\"HEADER\" NAME=\"lineHubName"+i+"\" OnChange=\"linehubchanged(document.invtransreq.lineHubName"+i+",'"+i+"')\" size=\"1\">\n" );
		  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		  Vector hubid= ( Vector ) iniigData.get( "HUB_ID" );
		  if ( tosendhub.trim().length() == 0 )
		  {
			tosendhub= ( String ) hubid.firstElement();
		  }
		  invout.print( radian.web.HTMLHelpObj.sorthashbyValue( hub_list.entrySet(),tosendhub ) );
		  invout.print( "</SELECT>\n" );
		  invout.print( "</TD>\n" );

		  invout.print( "<TD " + Color + "l\" WIDTH=\"5%\">\n" );
		  invout.print( "<SELECT CLASS=\"HEADER\"  NAME=\"lineinvengrp"+i+"\" size=\"1\">\n" );
		  invout.print( "<option value=\"\">Please Select</option>\n" );
		  Hashtable fh= ( Hashtable ) iniigData.get( tosendhub );
		  Vector invidV= ( Vector ) fh.get( "INVENTORY_GROUP" );
		  Vector invidName = ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );

		  for ( int klm =0; klm < invidV.size(); klm++ )
		  {
			String preSelect="";
			String wacid= ( String ) invidV.elementAt( klm );
			String invgName= ( String ) invidName.elementAt( klm );

			if (!"All".equalsIgnoreCase(wacid))
			{
			  if ( tosendinvgrp.equalsIgnoreCase( wacid ) )
			  {
				preSelect="selected";
			  }
			  invout.print( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + "</option>\n" );
			}
		  }
		  invout.print( "</SELECT>\n" );
		  invout.print( "</TD>\n" );
		  invout.print( "<td " + Color + "l\" width=\"5%\">" + quantity + "<INPUT TYPE=\"hidden\" NAME=\"avlqty"+i+"\" VALUE=\""+quantity+"\"></td>\n" );
		  invout.print("<td "+Color+"\" width=\"2%\"><input type=\"text\" name=\"qty_sent"+i+"\"  value=\""+qtysent+"\" maxlength=\"6\" size=\"3\" onChange=\"checkValue('"+i+"')\"></td>\n");

		  if ("Y".equalsIgnoreCase(xfer_source_originate))
		  {
		  invout.print("<td "+Color+"\" width=\"5%\"><input type=\"text\" name=\"pick_date"+i+"\" value=\""+pick_date+"\" maxlength=\"10\" size=\"11\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.expiry_date" + i + ");\">&diams;</a></FONT></td>\n");
		  }
		  else
		  {
			invout.print("<td "+Color+"\" width=\"5%\"><input type=\"hidden\" name=\"pick_date"+i+"\" value=\""+pick_date+"\" maxlength=\"10\" size=\"11\" readonly></td>\n");
		  }
        }
        invout.print( "</TR>\n" );
      }

      invout.print( "</TABLE>\n" );
      invout.print( "<input type=\"hidden\" name=\"Total_number\" value=\"" + i + "\">\n" );
      invout.print( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      invout.print( "<TR>" );
      invout.print( "<TD HEIGHT=38 WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
      invout.print( "</TD></TR>" );
      invout.print( "</TABLE>\n" );
      invout.print( "</form>\n" );
      invout.print( "</body></html>\n" );
    }
    catch ( Exception e )
    {
	  e.printStackTrace();
	  invout.print(radian.web.HTMLHelpObj.printError( "tcmIS Hub Inventory Transfer Page",intcmIsApplication ));
	  return;
    }

    return;
  }
}

