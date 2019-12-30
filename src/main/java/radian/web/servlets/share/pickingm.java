package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.Collection;
import java.util.Random;
import java.util.Iterator;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import radian.tcmis.both1.helpers.BothHelpObjs;
import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2002
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 10-30-02
 * Sending the message of the exception in the email I send out when somethng goes wrong in p_enter_pick
 *
 * 03-05-03
 * Adding mrnotes
 * 03-21-03 Adding the check all option to check all the check boxes
 * 03-27-03 Coloring Critical orders red
 * 07-28-03 Giving a link to the DOT shipping page + Code Cleanup
 * 08-02-03 Putting the END_user info on the delivery ticket
 * 08-15-03 - Sending emails through common object
 * 09-11-03 - Giving A warning if they entera value different from the picklist Qty
 * 11-13-03 - Not Reversing Picks if they are already ship confirmed
 * 11-24-03 - Painting the line yellow if the recipet is not pickable
 * 01-27-04 No need to encode Space in the Drop Down menu
 * 02-10-04 - Sorting Drop Downs
 * 04-05-04 - Calling a procedure to do a pick reverse
 * 09-28-04 - Closeing a Cbainet MR if the Inven Group is flagged to close it
 * 02-28-06 - Giving ability to view/add receipt documents
 * 08-04-06 - Checking to make sure the receipt Id matches before I do the updates
 */

public class pickingm
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private PrintWriter out = null;
    private boolean noneToUpd = true ;
    private static org.apache.log4j.Logger pickqclog = org.apache.log4j.Logger.getLogger(pickingm.class);
		private String thedecidingRandomNumber = null;
    //Nawaz 06-27-02
    private boolean allowupdate;
    private boolean intcmIsApplication = false;
    private String isWmsHub = "N";

    public void setupdateStatus(boolean id)
    {
      this.allowupdate = id;
    }

    private boolean getupdateStatus()  throws Exception
    {
     return this.allowupdate;
    }

    private String dropDownJs;
    public void setdropDownJs(String compjs)
    {
      this.dropDownJs = compjs;
    }

    private String getdropDownJs()  throws Exception
    {
     return this.dropDownJs;
    }

    public pickingm(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    //Process the HTTP Post request passed from whoever called it
    public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession session )
       throws ServletException,IOException
    {
      out=response.getWriter();
      response.setContentType( "text/html" );

      PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
      Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
      if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
      {
       intcmIsApplication = true;
      }

      String branch_plant=session.getAttribute( "BRANCH_PLANT" ) == null ? "" : session.getAttribute( "BRANCH_PLANT" ).toString();
      String personnelid=session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
      String CompanyID=session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
			Collection hubInventoryGroupOvBeanCollection = new Vector((Collection)session.getAttribute("hubInventoryGroupOvBeanCollection"));

      String User_Action=null;
      User_Action=request.getParameter( "UserAction" );
      if ( User_Action == null )
        User_Action="New";

      String mrnum=request.getParameter( "mrnum" );
      if ( mrnum == null )
        mrnum="";

      String User_Sort=request.getParameter( "SortBy" );
      if ( User_Sort == null )
        User_Sort="";

      String facility=request.getParameter( "FacName" );
      if ( facility == null )
        facility="";

      String subuserAction=null;
      subuserAction=request.getParameter( "SubUserAction" );
      if ( subuserAction == null )
        subuserAction="New";

      isWmsHub = request.getParameter( "isWmsHub" );
      if (isWmsHub == null)
          isWmsHub = "N";

      String remove_prnum=BothHelpObjs.makeSpaceFromNull( request.getParameter( "remove_receipt_id" ) );
      String remove_action=BothHelpObjs.makeSpaceFromNull( request.getParameter( "removeaction" ) );
      String remove_line=BothHelpObjs.makeSpaceFromNull( request.getParameter( "removeline" ) );
      String remove_picklistid=BothHelpObjs.makeSpaceFromNull( request.getParameter( "removepickid" ) );

			thedecidingRandomNumber = request.getParameter("thedecidingRandomNumber");
			if (thedecidingRandomNumber == null)
			 thedecidingRandomNumber = "";
			thedecidingRandomNumber = thedecidingRandomNumber.trim();

			Random rand = new Random();
			int tmpReq = rand.nextInt();
			Integer tmpReqNum = new Integer(tmpReq);

			try {
			 if (! (User_Action.equalsIgnoreCase("Unreceive")))
			 {
			 if (thedecidingRandomNumber.length() > 0) {
				String randomnumberfromsesion = session.getAttribute("LOGRNDPICKINGCOOKIE") == null ? "" :
				 session.getAttribute("LOGRNDPICKINGCOOKIE").toString();
				if (randomnumberfromsesion.equalsIgnoreCase(thedecidingRandomNumber)) {
				 thedecidingRandomNumber = tmpReqNum.toString();
				 session.setAttribute("LOGRNDPICKINGCOOKIE", thedecidingRandomNumber);
				}
				else {
				 thedecidingRandomNumber = tmpReqNum.toString();
				 session.setAttribute("LOGRNDPICKINGCOOKIE", thedecidingRandomNumber);
				 session.setAttribute("PICKQC_DATA", new Vector());

				 buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"",session );
				 out.println("<BR><BR><CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>");
				 return;
				}
			 }
			 else {
				thedecidingRandomNumber = tmpReqNum.toString();
				session.setAttribute("LOGRNDPICKINGCOOKIE", thedecidingRandomNumber);
			}
		 }
				 Map warehouseInfo= ( Map ) session.getAttribute( "warehouseInfo" );
				 if (warehouseInfo == null)
				 {
						 //PersonnelBean personnelBean=session.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) session.getAttribute( "personnelBean" );
						 radian.web.HTMLHelpObj.LoginSetup( session,db,""+personnelBean.getPersonnelId()+"",CompanyID,personnelBean.getLogonId());
				 }

		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

		if ( User_Action.equalsIgnoreCase( "New" ) )
        {
          /*Hashtable hub_list_set=new Hashtable();
          if ( this.getupdateStatus() )
          {
            hub_list_set=radian.web.HTMLHelpObj.createHubList( db,"Picking QC",personnelid,CompanyID );
          }
          else
          {
            hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,CompanyID );
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

          buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"",session );
          out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
          out.close();
          hub_list_set=null;

        }
        else if ( User_Action.equalsIgnoreCase( "Search" ) )
        {
          //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
          String companyArrayJs= ( String ) session.getAttribute( "CATALOGS_JS" );
          this.setdropDownJs( companyArrayJs );
          Vector searchdata=new Vector();

          session.removeAttribute( "PRINTDATA" );
          session.setAttribute( "UPDATEDONE","NO" );
          searchdata=this.getSearchData( branch_plant,mrnum,User_Sort,facility );

          Hashtable sum= ( Hashtable ) ( searchdata.elementAt( 0 ) );
          int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
          if ( 0 == count )
          {
            buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"",session );
            out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
            out.close();
            //clean up
            searchdata=null;
            hub_list_set=null;
          }
          else
          {
            session.setAttribute( "PICKQC_DATA",searchdata );
            session.setAttribute( "HUB_BACK",branch_plant );
            session.setAttribute( "MR_BACK",mrnum );
            session.setAttribute( "PICKLIST_BACK",User_Sort );
            session.setAttribute( "FAC_BACK",facility );

            buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"",session );
            out.println( buildDetails( searchdata ) );
            out.close();
            //clean up
            searchdata=null;
            hub_list_set=null;

          } //when there are open orders for hub
        }
        else if ( User_Action.equalsIgnoreCase( "Unreceive" ) )
        {
          //StringBuffer MsgUn=new StringBuffer();
          if ( remove_prnum.trim().length() > 0 )
          {
            out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Picking QC","picking.js",intcmIsApplication ) );
            out.println( "</HEAD><BODY> \n" );
            out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
            out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
            out.println( "</DIV>\n" );
            out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

            boolean cdatedelivered=true;
            cdatedelivered=this.checkdatedelivered( remove_prnum,remove_line,remove_picklistid );

            if ( !remove_action.equalsIgnoreCase( "Yes" ) && !cdatedelivered )
            {
              out.println( "<FORM METHOD=\"POST\" name=\"MfgidLike\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString( "PICKINGQC" ) + "?UserAction=Unreceive&removeaction=Yes&SubUserAction=Unreceive&remove_receipt_id=" + remove_prnum + "&removeline=" + remove_line + "&removepickid=" + remove_picklistid + "\">\n" );
              out.println( "<BR><FONT SIZE=\"2\" FACE=\"Arial\">Do you want to reverse MR-Line: <B>" + remove_prnum + "-" + remove_line + "</B><BR>\n" );
              out.println( "</FONT><BR><BR>\n" );
              out.println( "<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Yes\" NAME=\"SubmitButton\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
              out.println( "<INPUT TYPE=\"reset\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Cancel\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n" );
              out.println( "</FORM>\n" );
              out.println( "</BODY></HTML>\n" );
              //out.println( MsgUn );
              out.close();
            }
            else if ( remove_action.equalsIgnoreCase( "Yes" ) && !cdatedelivered )
            {
              if ( this.reversepickingqc( remove_prnum,remove_line,remove_picklistid ) )
              {
                out.println( "<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\"javascript:cancelUnreceive()\">\n" );
                out.println( "<BR><FONT SIZE=\"2\" FACE=\"Arial\">Success.<BR>\n" );
                out.println( "Mr-Line: <B>" + remove_prnum + "-" + remove_line + "</B> reversed Successfully.\n" );
                out.println( "<BR><BR>\n" );
                out.println( "<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Ok\" OnClick=\"Unreceive()\" NAME=\"CloseButton\"></CENTER>\n" );
                out.println( "</FORM>\n" );
                out.println( "</BODY></HTML>\n" );
                //out.println( MsgUn );
                out.close();
              }
              else
              {
                out.println( "<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\"javascript:cancelSearch()\">\n" );
                out.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\"><B>Error occured.</B><BR></FONT><FONT SIZE=\"2\" FACE=\"Arial\">\n" );
                out.println( "Mr-Line: <B>" + remove_prnum + "-" + remove_line + " not reversed.</B><BR>Contact tech support for more information.</FONT><BR><BR>\n" );
                out.println( "<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n" );
                out.println( "</FORM>\n" );
                out.println( "</BODY></HTML>\n" );
                //out.println( MsgUn );
                out.close();
              }
            }
            else if ( cdatedelivered )
            {
              out.println( "<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\"javascript:cancelUnreceive()\">\n" );
              out.println( "<BR><FONT SIZE=\"2\" FACE=\"Arial\">Alert.<BR>\n" );
              out.println( "Mr-Line: <B>" + remove_prnum + "-" + remove_line + "</B> is ship confirmed.\n" );
              out.println( "<BR>It Can not be Changed from here.<BR>\n" );
              out.println( "<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Ok\" OnClick=\"Unreceive()\" NAME=\"CloseButton\"></CENTER>\n" );
              out.println( "</FORM>\n" );
              out.println( "</BODY></HTML>\n" );
              //out.println( MsgUn );
              out.close();
            }
          }
          else
          {
            out.println( "<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\"javascript:cancel()\">\n" );
            out.println( "<BR><FONT SIZE=\"2\" FACE=\"Arial\">Error.<BR>\n" );
            out.println( "Receipt Not reversed. \\n Contact tech support for more information.<BR><BR>\n" );
            out.println( "<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n" );
            out.println( "</FORM>\n" );
            out.println( "</BODY></HTML>\n" );
            //out.println( MsgUn );
            out.close();
          }
        }
        else if ( User_Action.equalsIgnoreCase( "RefreshSearch" ) )
        {
          Vector data=this.getSearchData( session.getAttribute( "HUB_BACK" ).toString(),session.getAttribute( "MR_BACK" ).toString(),
                                          session.getAttribute( "PICKLIST_BACK" ).toString(),session.getAttribute( "FAC_BACK" ).toString() );
          session.setAttribute( "PICKQC_DATA",data );
          session.setAttribute( "UPDATEDONE","NO" );
          //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
          buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"No",session );
          out.println( buildDetails( data ) );

          data=null;
        }
        else if ( User_Action.equalsIgnoreCase( "printboxlabels" ) )
        {
          Vector retrn_data= ( Vector ) session.getAttribute( "PICKQC_DATA" );
          Vector print_data=SynchprintData( request,retrn_data,User_Action );
          session.setAttribute( "PRINTDATA",print_data );
          //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

          buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"No",session );
          out.println( buildDetails( retrn_data ) );
        }
        else if ( User_Action.equalsIgnoreCase( "reprintlabels" ) )
        {
          Vector retrn_data= ( Vector ) session.getAttribute( "PICKQC_DATA" );
          Vector print_data=SynchprintData( request,retrn_data,User_Action );
          session.setAttribute( "PRINTDATA",print_data );
          //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

          buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"No",session );
          out.println( buildDetails( retrn_data ) );
        }
        else if ( User_Action.equalsIgnoreCase( "printexitlabels" ) )
        {
          Vector retrn_data= ( Vector ) session.getAttribute( "PICKQC_DATA" );
          Vector print_data=SynchprintData( request,retrn_data,User_Action );
          session.setAttribute( "PRINTDATA",print_data );
          //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

          buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"No",session );
          out.println( buildDetails( retrn_data ) );
        }
        else if ( User_Action.equalsIgnoreCase( "printlargelabels" ) )
		{
		  Vector retrn_data= ( Vector ) session.getAttribute( "PICKQC_DATA" );
		  Vector print_data=SynchprintLargeLabelData( request,retrn_data,User_Action );
		  //printlargelabels
		  session.setAttribute( "LARGE_LABEL_DATA",print_data );

		  //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
		  buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"No",session );
		  out.println( buildDetails( retrn_data ) );
		}

        else if ( User_Action.equalsIgnoreCase( "deliveryticket" ) )
        {
          Vector retrn_data= ( Vector ) session.getAttribute( "PICKQC_DATA" );
          Vector print_data=SynchprintData( request,retrn_data,User_Action );
          session.setAttribute( "PRINTDATA",print_data );
          //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

          buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"No",session );
          out.println( buildDetails( retrn_data ) );
        }
        else if ( User_Action.equalsIgnoreCase( "printpicks" ) )
        {
          if ( subuserAction.equalsIgnoreCase( "PrintBOLDetail" ) )
          {
            Vector retrn_data= ( Vector ) session.getAttribute( "PICKQC_DATA" );
            Vector print_data=SynchprintData( request,retrn_data,User_Action );
            session.setAttribute( "PRINTDATA",print_data );
            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

            buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"Yes",session );
            out.println( buildDetails( retrn_data ) );
          }
          else if ( subuserAction.equalsIgnoreCase( "PrintBOL" ) )
          {
            Vector retrn_data= ( Vector ) session.getAttribute( "PICKQC_DATA" );
            Vector print_data=SynchprintData( request,retrn_data,User_Action );
            session.setAttribute( "PRINTDATA",print_data );

            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

            buildHeader( branch_plant,hubInventoryGroupOvBeanCollection,User_Action,mrnum,User_Sort,facility,"No",session );
            out.println( buildDetails( retrn_data ) );
          }
        }
        else if ( User_Action.equalsIgnoreCase( "UpdateConfirm" ) )
        {
          Vector retrn_data= ( Vector ) session.getAttribute( "PICKQC_DATA" );
          Vector synch_data=SynchServerData( request,retrn_data );

          retrn_data=null;
          //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
          session.setAttribute( "PICKQC_DATA",synch_data );

          String updatestatus= ( String ) session.getAttribute( "UPDATEDONE" );
          session.removeAttribute( "PRINTDATA" );

          if ( "Yes".equalsIgnoreCase( updatestatus ) )
          {
            buildHeader( session.getAttribute( "HUB_BACK" ).toString(),hubInventoryGroupOvBeanCollection,User_Action,session.getAttribute( "MR_BACK" ).toString(),
                                      session.getAttribute( "PICKLIST_BACK" ).toString(),session.getAttribute( "FAC_BACK" ).toString(),"",session );
            out.println( radian.web.HTMLHelpObj.printMessageinTable( "This Form was Already Submitted." ) );
            return;
          }

          Hashtable updateresults=UpdateDetails( synch_data,personnelid );

          session.setAttribute( "UPDATEDONE","YES" );
          Vector data=this.getSearchData( session.getAttribute( "HUB_BACK" ).toString(),session.getAttribute( "MR_BACK" ).toString(),
                                          session.getAttribute( "PICKLIST_BACK" ).toString(),session.getAttribute( "FAC_BACK" ).toString() );
          session.setAttribute( "UPDATEDONE","NO" );

          session.setAttribute( "PICKQC_DATA",data );

          Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );

          int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
          buildHeader( session.getAttribute( "HUB_BACK" ).toString(),hubInventoryGroupOvBeanCollection,User_Action,session.getAttribute( "MR_BACK" ).toString(),
                                    session.getAttribute( "PICKLIST_BACK" ).toString(),session.getAttribute( "FAC_BACK" ).toString(),"",session );
          if ( 0 == count )
          {
            out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
            out.close();
          }
          else
          {
            session.setAttribute( "PICKQC_DATA",data );

            out.println( buildDetails( data ) );
            out.close();
          }

          updateresults=null;
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "Picking QC",intcmIsApplication ) );
      }

      return;
    }


    private Vector getSearchData( String BranchPlant,String mrNum,String picklistId,String facility )
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
        String Query_Statement="";
        Query_Statement= "Select DEFINED_SHELF_LIFE_ITEM,CHARGE_DESC, MULTIPLE_CAT_PART_NO,RECEIPT_SPEC_NAME_LIST,RECEIPT_SPEC_VERSION,PO_NUMBER,RELEASE_NUMBER,REPORTING_ENTITY_NAME,APPLICATION_DESC,CUSTOMER_PART_NO,MATERIAL_REQUEST_ORIGIN,OPS_ENTITY_ID,MR_COMPLETE_PICKABLE,CURRENCY_ID,NONINTEGER_RECEIVING,RECEIPT_DOCUMENT_AVAILABLE,INVENTORY_GROUP,HAZMAT_ID_MISSING,CABINET_REPLENISHMENT,PICKABLE,END_USER,CRITICAL,MR_NOTES,PICKLIST_ID,HUB,PR_NUMBER,LINE_ITEM,MR_LINE,RECEIPT_ID,ITEM_ID,BIN,MFG_LOT,LOT_STATUS,DELIVERY_TYPE,NEED_DATE, ";
        Query_Statement+=" PICKLIST_QUANTITY,QUANTITY_PICKED,APPLICATION,FACILITY_ID,PART_DESCRIPTION,substr(PACKAGING,1,200) PACKAGING,INVENTORY_QUANTITY,CATALOG_ID,CAT_PART_NO, ";
        Query_Statement+=" PART_GROUP_NO,STOCKING_METHOD,to_char(NEED_DATE,'mm/dd/yyyy') NEED_DATE1,QC_DATE,COMPANY_ID,DELIVERY_POINT,REQUESTOR, ";
        Query_Statement+=" to_char(sysdate,'dd Mon yyyy hh24:mi') PICK_DATE,SHIP_TO_LOCATION_ID,TRANSFER_REQUEST_ID,to_char(EXPIRE_DATE,'mm/dd/yyyy') EXPIRE_DATE, ISSUE_ID,OWNER_SEGMENT_ID, ALLOCATE_BY_CHARGE_NUMBER_1, ALLOCATE_BY_CHARGE_NUMBER_2, ALLOCATE_BY_CHARGE_NUMBER_3, ALLOCATE_BY_CHARGE_NUMBER_4 "; //PICKLIST_ID = "+picklistID+"";

        if ( mrNum.trim().length() > 0 )
        {
            Query_Statement += " from table (pkg_pick.fx_picklist_by_mr('" + BranchPlant + "', " + mrNum.trim() + "))";
        }
        else if ( picklistId.trim().length() > 0 )
        {
            Query_Statement += " from table (pkg_pick.fx_picklist('" + BranchPlant + "', " + picklistId + "))";
        }
        else if ( facility.trim().length() > 0 && !"All".equalsIgnoreCase( facility ) )
        {
            Query_Statement += " from table (pkg_pick.fx_picklist('" + BranchPlant + "', '" + facility + "'))";
        }
        else
        {
            Query_Statement += " from table (pkg_pick.fx_picklist('" + BranchPlant + "'))";
        }

        Query_Statement+=" order by MR_LINE,PICKLIST_ID,ITEM_ID ";

        dbrs=db.doQuery( Query_Statement );
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          DataH=new Hashtable();
          num_rec++;

          DataH.put( "PICKLIST_ID",rs.getString( "PICKLIST_ID" ) == null ? " " : rs.getString( "PICKLIST_ID" ) );
          DataH.put( "MR_LINE",rs.getString( "MR_LINE" ) == null ? " " : rs.getString( "MR_LINE" ) );
          DataH.put( "FACILITY_ID",rs.getString( "FACILITY_ID" ) == null ? " " : rs.getString( "FACILITY_ID" ) );
          DataH.put( "APPLICATION",rs.getString( "APPLICATION" ) == null ? " " : rs.getString( "APPLICATION" ) );
          DataH.put( "REQUESTOR",rs.getString( "REQUESTOR" ) == null ? " " : rs.getString( "REQUESTOR" ) );
          DataH.put( "CATALOG_ID",rs.getString( "CATALOG_ID" ) == null ? " " : rs.getString( "CATALOG_ID" ) );
          DataH.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
          DataH.put( "PART_DESCRIPTION",rs.getString( "PART_DESCRIPTION" ) == null ? " " : rs.getString( "PART_DESCRIPTION" ) );
          DataH.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? " " : rs.getString( "ITEM_ID" ) );
          DataH.put( "LOT_STATUS",rs.getString( "LOT_STATUS" ) == null ? " " : rs.getString( "LOT_STATUS" ) );
          DataH.put( "EXPIRE_DATE",rs.getString( "EXPIRE_DATE" ) == null ? " " : rs.getString( "EXPIRE_DATE" ) );
          DataH.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? " " : rs.getString( "MFG_LOT" ) );
          DataH.put( "BIN",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
          DataH.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? "" : rs.getString( "RECEIPT_ID" ) );
          DataH.put( "INVENTORY_QUANTITY",rs.getString( "INVENTORY_QUANTITY" ) == null ? "" : rs.getString( "INVENTORY_QUANTITY" ) );
          DataH.put( "PICKLIST_QUANTITY",rs.getString( "PICKLIST_QUANTITY" ) == null ? "" : rs.getString( "PICKLIST_QUANTITY" ) );
          DataH.put( "QUANTITY_PICKED",rs.getString( "QUANTITY_PICKED" ) == null ? "" : rs.getString( "QUANTITY_PICKED" ) );
          DataH.put( "STOCKING_METHOD",rs.getString( "STOCKING_METHOD" ) == null ? "&nbsp;" : rs.getString( "STOCKING_METHOD" ) );
          DataH.put( "DELIVERY_POINT",rs.getString( "DELIVERY_POINT" ) == null ? "&nbsp;" : rs.getString( "DELIVERY_POINT" ) );
          DataH.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? "&nbsp;" : rs.getString( "PACKAGING" ) );
          DataH.put( "COMPANY_ID",rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ) );
          DataH.put( "QC_DATE",rs.getString( "QC_DATE" ) == null ? "" : rs.getString( "QC_DATE" ) );
          DataH.put( "HUB",rs.getString( "HUB" ) == null ? "" : rs.getString( "HUB" ) );
          DataH.put( "NEED_DATE",rs.getString( "NEED_DATE1" ) == null ? "" : rs.getString( "NEED_DATE1" ) );
          DataH.put( "PR_NUMBER",rs.getString( "PR_NUMBER" ) == null ? "" : rs.getString( "PR_NUMBER" ) );
          DataH.put( "LINE_ITEM",rs.getString( "LINE_ITEM" ) == null ? "" : rs.getString( "LINE_ITEM" ) );
          DataH.put( "PICK_DATE",rs.getString( "PICK_DATE" ) == null ? "" : rs.getString( "PICK_DATE" ) );
          DataH.put( "SHIP_TO_LOCATION_ID",rs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "&nbsp;" : rs.getString( "SHIP_TO_LOCATION_ID" ) );
          DataH.put( "MR_NOTES",rs.getString( "MR_NOTES" ) == null ? "" : rs.getString( "MR_NOTES" ) );
          DataH.put( "CRITICAL",rs.getString( "CRITICAL" ) == null ? "" : rs.getString( "CRITICAL" ) );
          DataH.put( "END_USER",rs.getString( "END_USER" ) == null ? " " : rs.getString( "END_USER" ) );
          DataH.put( "LINE_STATUS","No" );
          DataH.put( "USERCHK","" );
          DataH.put( "DOUPDATE","" );
		  DataH.put( "PICKABLE",rs.getString( "PICKABLE" ) == null ? "" : rs.getString( "PICKABLE" ) );
		  DataH.put( "CABINET_REPLENISHMENT",rs.getString( "CABINET_REPLENISHMENT" ) == null ? "" : rs.getString( "CABINET_REPLENISHMENT" ) );
		  DataH.put( "LAST_LINE","" );
		  DataH.put( "CLOSE_MR","" );
			DataH.put( "HAZMAT_ID_MISSING",rs.getString( "HAZMAT_ID_MISSING" ) == null ? "" : rs.getString( "HAZMAT_ID_MISSING" ) );
			DataH.put( "INVENTORY_GROUP",rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ) );
			DataH.put("RECEIPT_DOCUMENT_AVAILABLE",rs.getString("RECEIPT_DOCUMENT_AVAILABLE")==null?"":rs.getString("RECEIPT_DOCUMENT_AVAILABLE"));
			DataH.put("NONINTEGER_RECEIVING",rs.getString("NONINTEGER_RECEIVING")==null?"":rs.getString("NONINTEGER_RECEIVING"));
            DataH.put("CURRENCY_ID",rs.getString("CURRENCY_ID")==null?"":rs.getString("CURRENCY_ID"));
            DataH.put("MR_COMPLETE_PICKABLE",rs.getString("MR_COMPLETE_PICKABLE")==null?"":rs.getString("MR_COMPLETE_PICKABLE"));
            DataH.put("OPS_ENTITY_ID",rs.getString("OPS_ENTITY_ID")==null?"":rs.getString("OPS_ENTITY_ID"));
            DataH.put("MATERIAL_REQUEST_ORIGIN",rs.getString("MATERIAL_REQUEST_ORIGIN")==null?"":rs.getString("MATERIAL_REQUEST_ORIGIN"));
            DataH.put("CUSTOMER_PART_NO",rs.getString("CUSTOMER_PART_NO")==null?"":rs.getString("CUSTOMER_PART_NO"));
            DataH.put("APPLICATION_DESC",rs.getString("APPLICATION_DESC")==null?"":rs.getString("APPLICATION_DESC"));
            DataH.put("REPORTING_ENTITY_NAME",rs.getString("REPORTING_ENTITY_NAME")==null?"":rs.getString("REPORTING_ENTITY_NAME"));
            DataH.put("ISSUE_ID",rs.getString("ISSUE_ID")==null?"":rs.getString("ISSUE_ID"));
            // TCMISPROD-2259 Pass CID, Owner, Part from MR to the Exit label.
            DataH.put("OWNER_SEGMENT_ID",rs.getString("OWNER_SEGMENT_ID")==null?"":rs.getString("OWNER_SEGMENT_ID"));
            DataH.put("ALLOCATE_BY_CHARGE_NUMBER_1",rs.getString("ALLOCATE_BY_CHARGE_NUMBER_1")==null?"":rs.getString("ALLOCATE_BY_CHARGE_NUMBER_1"));
            DataH.put("ALLOCATE_BY_CHARGE_NUMBER_2",rs.getString("ALLOCATE_BY_CHARGE_NUMBER_2")==null?"":rs.getString("ALLOCATE_BY_CHARGE_NUMBER_2"));
            DataH.put("ALLOCATE_BY_CHARGE_NUMBER_3",rs.getString("ALLOCATE_BY_CHARGE_NUMBER_3")==null?"":rs.getString("ALLOCATE_BY_CHARGE_NUMBER_3"));
            DataH.put("ALLOCATE_BY_CHARGE_NUMBER_4",rs.getString("ALLOCATE_BY_CHARGE_NUMBER_4")==null?"":rs.getString("ALLOCATE_BY_CHARGE_NUMBER_4"));
            DataH.put("RECEIPT_SPEC_NAME_LIST",rs.getString("RECEIPT_SPEC_NAME_LIST")==null?"":rs.getString("RECEIPT_SPEC_NAME_LIST"));
            DataH.put("RECEIPT_SPEC_VERSION",rs.getString("RECEIPT_SPEC_VERSION")==null?"":rs.getString("RECEIPT_SPEC_VERSION"));
            DataH.put("PO_NUMBER",rs.getString("PO_NUMBER")==null?"":rs.getString("PO_NUMBER"));
            DataH.put("RELEASE_NUMBER",rs.getString("RELEASE_NUMBER")==null?"":rs.getString("RELEASE_NUMBER"));
            DataH.put("MULTIPLE_CAT_PART_NO",rs.getString("MULTIPLE_CAT_PART_NO")==null?"":rs.getString("MULTIPLE_CAT_PART_NO"));
            DataH.put("CHARGE_DESCRIPTION",rs.getString("CHARGE_DESC")==null?"":rs.getString("CHARGE_DESC"));
            DataH.put("DEFINED_SHELF_LIFE_ITEM",rs.getString("DEFINED_SHELF_LIFE_ITEM")==null?"":rs.getString("DEFINED_SHELF_LIFE_ITEM"));

          Data.addElement( DataH );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
         radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure pkg_pick.fx_picklist in picking QC","Error occured while running pkg_pick.fx_picklist from picking QC\n" + e.getMessage() +"\nFor Input Parameters BranchPlant: " + BranchPlant + "" );
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
      recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
      Data.setElementAt( recsum,0 );

      return Data;
    }

    private Vector SynchprintData( HttpServletRequest request,Vector in_data,String userAction )
    {
      Vector new_data=new Vector();
      Hashtable sum= ( Hashtable ) ( in_data.elementAt( 0 ) );
      int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
      Vector prnumbers=new Vector();
      Vector linenumber=new Vector();
      Vector picklistid=new Vector();
      Hashtable FResult=new Hashtable();

      try
      {
        for ( int i=1; i < count + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) ( in_data.elementAt( i ) );

          String prnumber=hD.get( "PR_NUMBER" ).toString();
          String lineitem=hD.get( "LINE_ITEM" ).toString();
          String pickid=hD.get( "PICKLIST_ID" ).toString();
          String mrline=hD.get( "MR_LINE" ).toString();

		  //String startmrline=BothHelpObjs.makeBlankFromNull( request.getParameter( "Start" + pickid + mrline ) );
          String stopmrline=BothHelpObjs.makeBlankFromNull( request.getParameter( "Stop" + pickid + mrline ) );
          String check="";
          check=BothHelpObjs.makeBlankFromNull( request.getParameter( "row_chk" + stopmrline ) );

          hD.remove( "USERCHK" );
          hD.put( "USERCHK",check );

          if ( "yes".equalsIgnoreCase( check ) )
          {
            prnumbers.addElement( prnumber );
            linenumber.addElement( lineitem );
            picklistid.addElement( pickid );
          }
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "Picking QC",intcmIsApplication ) );
      }

      FResult.put( "mr_number",prnumbers );
      FResult.put( "line_number",linenumber );
      FResult.put( "picklist_number",picklistid );

      new_data.add( FResult );
      return new_data;
    }

	private Vector SynchprintLargeLabelData( HttpServletRequest request,Vector in_data,String userAction )
	   {
		 Hashtable sum= ( Hashtable ) ( in_data.elementAt( 0 ) );
		 int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

		 Vector largeLabelData = new Vector();
		 Hashtable summary = new Hashtable();
		 int sumcount = 0;
		 int receiptCount = 0;
		 summary.put("TOTAL_NUMBER", new Integer(sumcount) );
		 largeLabelData.addElement(sum);

		 try
		 {
		   for ( int i=1; i < count + 1; i++ )
		   {
			 Hashtable hD=new Hashtable();
			 hD= ( Hashtable ) ( in_data.elementAt( i ) );

			 String prnumber=hD.get( "PR_NUMBER" ).toString();
			 String lineitem=hD.get( "LINE_ITEM" ).toString();
			 String pickid=hD.get( "PICKLIST_ID" ).toString();
			 String mrline=hD.get( "MR_LINE" ).toString();

			 String startmrline=BothHelpObjs.makeBlankFromNull( request.getParameter( "Start" + pickid + mrline ) );
			 String stopmrline=BothHelpObjs.makeBlankFromNull( request.getParameter( "Stop" + pickid + mrline ) );
			 String check="";
			 check=BothHelpObjs.makeBlankFromNull( request.getParameter( "row_chk" + stopmrline ) );

			 hD.remove( "USERCHK" );
			 hD.put( "USERCHK",check );

			 if ( "yes".equalsIgnoreCase( check ) )
			 {
			  if ( userAction.equalsIgnoreCase( "printlargelabels" ) )
			  {
				  Hashtable hD1=new Hashtable();
				  hD1= ( Hashtable ) ( in_data.elementAt( i ) );

				  receiptCount++;
				  largeLabelData.addElement( hD1 );
			  }
			 }
		   }
		 }
		 catch ( Exception e )
		 {
		   e.printStackTrace();
		   out.println( radian.web.HTMLHelpObj.printError( "Picking QC",intcmIsApplication ) );
		 }

		 Hashtable recsum  = new Hashtable();
		 recsum.put("TOTAL_NUMBER", new Integer(receiptCount) );
		 largeLabelData.setElementAt(recsum, 0);
		 largeLabelData.trimToSize();
		 //FResult.put( "LARGE_LABEL_DATA",largeLabelData );

		 return largeLabelData;
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

          String mrline=hD.get( "MR_LINE" ).toString();
          String pickid=hD.get( "PICKLIST_ID" ).toString();
          String startmrline=BothHelpObjs.makeBlankFromNull( request.getParameter( "Start" + pickid + mrline ) );
          String stopmrline=BothHelpObjs.makeBlankFromNull( request.getParameter( "Stop" + pickid + mrline ) );
					String closeMrline=BothHelpObjs.makeBlankFromNull( request.getParameter( "closeMrline" + pickid + mrline ) );
					String receiptId=hD.get( "RECEIPT_ID" ).toString();
					String cabreplenishment = hD.get( "CABINET_REPLENISHMENT" ).toString();

		  if (stopmrline.equalsIgnoreCase(""+i+"") && cabreplenishment.length() > 0)
		  {
			//pickqclog.info("This is the last line: Cab Replenishment "+cabreplenishment+" Close MR: "+closeMrline+"  Last Receipt Id :  "+hD.get( "RECEIPT_ID" ).toString()+"");
			hD.remove( "LAST_LINE" );
			hD.put( "LAST_LINE","Yes" );

		    hD.remove( "CLOSE_MR" );
 			hD.put( "CLOSE_MR",closeMrline );
		  }

          boolean updatestatus=false;
          for ( int j=Integer.parseInt( startmrline ); j <= Integer.parseInt( stopmrline ); j++ )
          {
            Hashtable hD1=new Hashtable();
            hD1= ( Hashtable ) ( in_data.elementAt( j ) );

            String qtypicked1="";
            qtypicked1=BothHelpObjs.makeBlankFromNull( request.getParameter( "qty_picked" + j ) );
            String pickqty="";
            pickqty=hD1.get( "PICKLIST_QUANTITY" ).toString();
            if ( qtypicked1.trim().length() > 0 )
            {
              updatestatus=true;
            }
          }

					String formReceiptId="";
					formReceiptId=BothHelpObjs.makeBlankFromNull( request.getParameter( "receiptId" + i ) );

  	 	    if (receiptId.equalsIgnoreCase(formReceiptId))
					{
          if ( updatestatus )
          {
            hD.remove( "DOUPDATE" );
            hD.put( "DOUPDATE","Yes" );
          }

          String qtypicked="";
          qtypicked=BothHelpObjs.makeBlankFromNull( request.getParameter( "qty_picked" + i ) );
          hD.remove( "QUANTITY_PICKED" );
          hD.put( "QUANTITY_PICKED",qtypicked.trim() );
					}
          new_data.addElement( hD );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "Picking QC",intcmIsApplication ) );
      }

      return new_data;
    }

    public boolean Updatepicks( Hashtable hD,String personnelID )
    {
      boolean result=false;
      String companyid=hD.get( "COMPANY_ID" ).toString().trim();
      String pickid=hD.get( "PICKLIST_ID" ).toString();
      String prnumber=hD.get( "PR_NUMBER" ).toString().trim();
      String lineitem=hD.get( "LINE_ITEM" ).toString().trim();
      String hub=hD.get( "HUB" ).toString().trim();
      String receiptid=hD.get( "RECEIPT_ID" ).toString();
      String qtypicked=hD.get( "QUANTITY_PICKED" ).toString();
      String needdate=hD.get( "NEED_DATE" ).toString().trim();
      String pickdate=hD.get( "PICK_DATE" ).toString().trim();
			String pickListQuantity=hD.get( "PICKLIST_QUANTITY" ).toString().trim();

      pickqclog.info("Doing P_ENTER_PICK pickid "+pickid+"  companyid "+companyid+" prnumber  "+prnumber+"-"+lineitem+"  needdate "+needdate+"  hub "+hub+"  receiptid "+receiptid+"  pickdate "+pickdate+" pickListQuantity "+pickListQuantity+" qtypicked  "+qtypicked+"  personnelID "+personnelID+" ");
			if ( qtypicked == null || qtypicked.trim().length() == 0 )
			{
				 qtypicked="0";
			}

      CallableStatement cs=null;
      try
      {
        Connection connect1=null;
        connect1=db.getConnection();

        cs=connect1.prepareCall( "{call P_ENTER_PICK(?,?,?,?,?,?,?,?,?,?,?,?)}" );
        cs.setInt( 1,Integer.parseInt( pickid ) );
        cs.setString( 2,companyid );
        cs.setBigDecimal( 3,new BigDecimal(prnumber) );
        cs.setString( 4,lineitem );
        cs.setTimestamp( 5,radian.web.HTMLHelpObj.getDateFromString( needdate ) );
        cs.setString( 6,hub );
		if (receiptid.trim().length() > 0){cs.setInt(7,Integer.parseInt(receiptid));}else{cs.setNull(7,java.sql.Types.INTEGER);}
        cs.setTimestamp( 8,radian.web.HTMLHelpObj.getDateFromString( "dd MMM yyyy HH:mm",pickdate ) );
        cs.setBigDecimal( 9,new BigDecimal(qtypicked) );
        cs.setInt( 10,Integer.parseInt( personnelID ) );

        cs.registerOutParameter( 11,java.sql.Types.NUMERIC );
        cs.registerOutParameter( 12,java.sql.Types.VARCHAR );

        cs.execute();

        int issue_id=cs.getInt( 11 );
        String errorcode=cs.getString( 12 );
        
        if ( errorcode == null )
        {
          result=true;
        }
        else
        {
          pickqclog.info( "Result from P_ENTER_PICK procedure Error Code:  " + errorcode + "  pickid " + pickid + "  hub " + hub + "  issue_id " + issue_id );
          result=false;
        }

        if ( issue_id == -1 )
        {
          result=false;
        }
        else
        {
          result=true;
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling P_ENTER_PICK in Picking QC","Error occured while running P_ENTER_PICK \nError Msg:\n" + e.getMessage() + "\nFor Input Parameters \npickid " + pickid +
                               "  \ncompanyid " + companyid + " \nprnumber  " + prnumber + "-" + lineitem + "  \nneeddate " + needdate + "  \nhub " + hub +
                               "  \nreceiptid " + receiptid + "  \npickdate " + pickdate + " \nqtypicked  " + qtypicked + "  \npersonnelID " + personnelID +
                               " " );
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

	public boolean closemrline( Hashtable hD,String personnelID )
	{
	  boolean result=false;
	  String companyid=hD.get( "COMPANY_ID" ).toString().trim();
	  String prnumber=hD.get( "PR_NUMBER" ).toString().trim();
	  String lineitem=hD.get( "LINE_ITEM" ).toString().trim();
	  /*String hub=hD.get( "HUB" ).toString().trim();
	  String receiptid=hD.get( "RECEIPT_ID" ).toString();
	  String qtypicked=hD.get( "QUANTITY_PICKED" ).toString();
	  String needdate=hD.get( "NEED_DATE" ).toString().trim();
	  String pickdate=hD.get( "PICK_DATE" ).toString().trim();*/

	  pickqclog.info("Doing p_line_close_from_pickqc companyid "+companyid+" prnumber  "+prnumber+"-"+lineitem+"   personnelID "+personnelID+" ");

	  CallableStatement cs=null;
	  try
	  {
		Connection connect1=null;
		connect1=db.getConnection();

		cs=connect1.prepareCall( "{call p_line_close_from_pickqc (?,?,?)}" );
		cs.setString( 1,companyid );
		cs.setBigDecimal( 2,new BigDecimal(prnumber) );
		cs.setString( 3,lineitem );

		cs.execute();

		result=true;

	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling p_line_close_from_pickqc in Picking QC","Error occured while running p_line_close_from_pickqc \nError Msg:\n" + e.getMessage() + "\nFor Input Parameters \ncompanyid " + companyid + " \nprnumber  " + prnumber + "-" + lineitem + "   \npersonnelID " + personnelID +" " );
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

    private Hashtable UpdateDetails( Vector data,String personnelid )
    {
      boolean result=false;

      Hashtable updateresult=new Hashtable();
      Vector errordata=new Vector();
      Vector picklistid=new Vector();
      Vector mrNumberLineV=new Vector();
      Vector mrNumberV=new Vector();
      Vector lineItemV=new Vector();
      Vector companyIdV=new Vector();
      Vector needDateV=new Vector();

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
          Line_Check= ( hD.get( "USERCHK" ) == null ? "" : hD.get( "USERCHK" ).toString() );

          String dostatusupdate="";
          dostatusupdate= ( hD.get( "DOUPDATE" ) == null ? "" : hD.get( "DOUPDATE" ).toString() );
		  String lastLine = ( hD.get( "LAST_LINE" ) == null ? "" : hD.get( "LAST_LINE" ).toString() );
		  String closemrline = ( hD.get( "CLOSE_MR" ) == null ? "" : hD.get( "CLOSE_MR" ).toString() );

          String companyid=hD.get( "COMPANY_ID" ).toString().trim();
          String prnumber=hD.get( "PR_NUMBER" ).toString().trim();
          String lineitem=hD.get( "LINE_ITEM" ).toString().trim();
          String needdate=hD.get( "NEED_DATE" ).toString().trim();

          if ( "yes".equalsIgnoreCase( dostatusupdate ) )
          {
            noneToUpd=false;
            boolean line_result=false;

            String spicklistid=hD.get( "PICKLIST_ID" ).toString();
            if ( !picklistid.contains( spicklistid ) )
            {
              picklistid.addElement( spicklistid );
            }

            String mrLine = prnumber+"-"+lineitem;
            if ( !mrNumberLineV.contains( mrLine ) )
            {
              mrNumberLineV.addElement( mrLine );
              mrNumberV.addElement( prnumber );
              lineItemV.addElement( lineitem );
              companyIdV.addElement( companyid );
              needDateV.addElement( needdate );
            }

            String qtypicked="";
            qtypicked=hD.get( "QUANTITY_PICKED" ).toString().trim();
            String pickqty="";
            pickqty=hD.get( "PICKLIST_QUANTITY" ).toString().trim();


            if ( pickqty.trim().length() == 0 && ( qtypicked.trim().length() == 0 || "0".equalsIgnoreCase(qtypicked) ) )
            {

            }
            else if ( qtypicked.trim().length() > 0 || pickqty.trim().length() > 0 )
            {
              line_result=this.Updatepicks( hD,personnelid ); // update database
            }

		     if ( "Y".equalsIgnoreCase( closemrline ) && "Yes".equalsIgnoreCase( lastLine ) )
			 {
			   line_result=this.closemrline( hD,personnelid );
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

        CallableStatement cs1=null;
        for ( int jj=0; jj < mrNumberLineV.size(); jj++ )
        {
          Connection connect1=null;
          cs1=null;
          connect1=db.getConnection();

          String mrNumber = mrNumberV.elementAt( jj ).toString();
          String lineItem = lineItemV.elementAt( jj ).toString();
          String companyId = companyIdV.elementAt( jj ).toString();
          String needDate = needDateV.elementAt( jj ).toString();

          cs1=connect1.prepareCall( "{call P_LINE_ITEM_ALLOCATE(?,?,?,?,?)}" );
          cs1.setString(1,companyId);
          cs1.setBigDecimal( 2,new BigDecimal(mrNumber) );
          cs1.setString(3,lineItem);
          cs1.setString(4,"Y");
          cs1.setTimestamp( 5,radian.web.HTMLHelpObj.getDateFromString( needDate ) );
          pickqclog.debug( "calling P_LINE_ITEM_ALLOCATE procedure Error Code:  mrNumber " + mrNumber + "  lineItem " + lineItem + "  companyId " + companyId );
          cs1.execute();
        }
        if ( cs1 != null )
        {
          try
          {
            cs1.close();
          }
          catch ( SQLException se )
          {}
        }

        CallableStatement cs=null;
        String vpickid="";
        for ( int jj=0; jj < picklistid.size(); jj++ )
        {
          Connection connect1=null;
          cs=null;
          connect1=db.getConnection();          
          vpickid=picklistid.elementAt( jj ).toString();
          pickqclog.debug( "calling P_QC_PICKLIST procedure Error Code:  picklist " + vpickid);
          cs=connect1.prepareCall( "{call P_QC_PICKLIST(?,?)}" );
          cs.setInt( 1,Integer.parseInt( vpickid ) );
          cs.setInt( 2,Integer.parseInt( personnelid ) );
          cs.execute();
        }
        if ( cs != null )
        {
          try
          {
            cs.close();
          }
          catch ( SQLException se )
          {
              radian.web.emailHelpObj.sendnawazemail("Error calling P_QC_PICKLIST","Error calling P_QC_PICKLIST -"+vpickid+"\n\n"+se.getMessage()+"\n\nPersonnel ID:   "+personnelid+"");
          }
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

    public boolean checkdatedelivered( String prnumber,String lineitem,String picklistid )
    {
      boolean result=false;
      if ( prnumber.trim().length() < 1 )
      {
        result=false;
        return result;
      }

      DBResultSet dbrs=null;
      ResultSet rs=null;
      String datedelivered=null;

      try
      {
        String query="select DATE_DELIVERED from issue where PR_NUMBER=" + prnumber + " and LINE_ITEM=" + lineitem + " and PICKLIST_ID=" + picklistid + " and quantity != 0";
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();
        while ( rs.next() )
        {
          datedelivered=rs.getString( "DATE_DELIVERED" ) == null ? "" : rs.getString( "DATE_DELIVERED" );
          if ( datedelivered.trim().trim().length() > 0 )
          {
            result=true;
          }
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        result=false;
      }
      return result;
    }

    public boolean reversepickingqc( String prnumber,String lineitem,String picklistid )
    {
      boolean result=false;
      if ( prnumber.trim().length() < 1 )
      {
        result=false;
        return result;
      }

	  CallableStatement cs=null;
	  try
	  {
		Connection connect1=null;
		connect1=db.getConnection();

		cs=connect1.prepareCall( "{call p_reverse_pick_qc(?,?,?,?)}" );
		cs.setBigDecimal( 1,new BigDecimal(""+ prnumber +"") );
		cs.setString( 2,lineitem );
		cs.setInt( 3,Integer.parseInt( picklistid ) );
		cs.registerOutParameter( 4,java.sql.Types.VARCHAR );

		cs.execute();

		String errorcode=cs.getString( 4 );
		pickqclog.info( "Result from p_reverse_pick_qc procedure Error Code:  " + errorcode + "  pickid " + picklistid + "  prnumber " + prnumber + "  lineitem " + lineitem );

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
		radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling p_reverse_pick_qc in Picking QC","Error occured while reversing MR-LINE " + prnumber + "-" + lineitem + "\nError Msg:\n" + e.getMessage() + "\nFor Input Parameters \npickid " + picklistid +	 "  prnumber " + prnumber + "  lineitem " + lineitem+"");
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

      /*try
      {
        String query="update issue set QC_DATE=null,QC_USER=null where PR_NUMBER=" + prnumber + " and LINE_ITEM=" + lineitem + " and PICKLIST_ID=" + picklistid + " and ship_confirm_date is null";
        db.doUpdate( query );

        result=true;
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        radian.web.emailHelpObj.senddeveloperemail(" Picking QC -MR-LINE reverse error","Error occured while reversing MR-LINE " + prnumber + "-" + lineitem + "  in Picklist  "+picklistid+" \nError Msg:\n"+e.getMessage()+"" );
        result=false;
      }*/
      return result;
    }

    private void buildHeader(String selHubnum,Collection hubInventoryGroupOvBeanCollection,String useraction,String mrnum, String sortby,String facility,String boloptions,HttpSession session)
    {
        //StringBuffer Msg = new StringBuffer();
        //String SelectedHubName = "";
        DBResultSet dbrs = null;
        ResultSet rs = null;

        try
        {
        out.println(radian.web.HTMLHelpObj.printHTMLHeader("Picking QC","picking.js",intcmIsApplication));

		if ( "printlargelabels".equalsIgnoreCase(useraction) )
		{
		  out.println( "<BODY onLoad=\"dolargelabelPopup()\">\n" );
		}
        else if ( "printpicks".equalsIgnoreCase(useraction) )
        {
          out.println("<BODY onLoad=\"doPrintbol()\">\n");
        }
        else if ("printboxlabels".equalsIgnoreCase(useraction) )
        {
          out.println("<BODY onLoad=\"doPrintbox()\">\n");
        }
        else if ("reprintlabels".equalsIgnoreCase(useraction) )
        {
          out.println("<BODY onLoad=\"doPrintrelabel()\">\n");
        }
        else if ("printexitlabels".equalsIgnoreCase(useraction) )
        {
          out.println("<BODY onLoad=\"doGenerateExitLabels()\">\n");
        }
        else if ("deliveryticket".equalsIgnoreCase(useraction) )
        {
          out.println("<BODY onLoad=\"dodeliveryticket()\">\n");
        }
        else
        {
          out.println("<BODY onLoad=\"isWmsHub()\">\n");
        }

        out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
        out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
        out.println("</DIV>\n");
        out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");
        out.println(radian.web.HTMLHelpObj.PrintTitleBar("<B>Picking QC</B>\n"));

		//Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		if (hubInventoryGroupOvBeanCollection.size() < 1)
		{
		  out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
		  return;
		}
		out.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
		out.println(this.getdropDownJs());
        out.println("\n"+getHubAutomatedPutAway(hubInventoryGroupOvBeanCollection).toString()+"\n");
		out.println("// -->\n </SCRIPT>\n");

		//Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
		if ( selHubnum.trim().length() == 0 )
		{
		  selHubnum=radian.web.HTMLHelpObj.getFirstBranchPlant( hubInventoryGroupOvBeanCollection );
		}

		String hubname = radian.web.HTMLHelpObj.getHubName(hubInventoryGroupOvBeanCollection,selHubnum);
		PersonnelBean personnelBean = session.getAttribute( "personnelBean" ) == null ? null : (PersonnelBean) session.getAttribute( "personnelBean" );
		if (personnelBean.getPermissionBean().hasFacilityPermission("Picking QC",hubname,null))
		{
		  this.setupdateStatus(true);
		}
		else
		{
		  this.setupdateStatus(false);
		}

        out.println("<FORM method=\"POST\" NAME=\"picking\" ID=\"picking\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+bundle.getString("PICKINGQC")+"\">\n");

        //start table #1
        out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
        out.println("<TR>\n");

        //Hub List
        out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
        out.println("<B>Hub:</B>&nbsp;\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"15%\">\n");
        out.println("<SELECT CLASS=\"HEADER\" NAME=\"HubName\" ID=\"HubName\" ONCHANGE=\"getpicklistids(this)\" size=\"1\">\n");
        out.println("<OPTION VALUE=\"All\">Select A Hub</OPTION>\n");

		out.println(radian.web.HTMLHelpObj.getHubDropDown(hubInventoryGroupOvBeanCollection,selHubnum));

        out.println("</SELECT>\n");
        out.println("</TD>\n");

         // search mrnum
        out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
        out.println("<B>MR:</B>&nbsp;\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n");
        out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"mrnum\" value=\""+mrnum+"\" ID=\"mrnum\" value=\""+mrnum+"\" size=\"19\">\n");
        out.println("</TD>\n");

        //Search
        out.println("<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n");
        out.println("   <INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
        out.println("</TD>\n");

        //Print BOL SHORT
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
        out.println("<INPUT ID=\"printBolShortButton\" TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print BOL Short\" onclick= \"return bolshort(this)\" NAME=\"printBolShortButton\">&nbsp;\n");
        out.println("</TD>\n");

        //Print BOX Labels
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
        out.println("<INPUT ID=\"deliveryLabelButton\" TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print Delivery Labels\" onclick= \"return boxlabels(this)\" NAME=\"deliveryLabelButton\">&nbsp;\n");
        out.println("</TD>\n");

        out.println("</TR>\n");
        out.println("</TR>\n");
        out.println("<TR>\n");

        //Facility List
        out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
        out.println("<B>Facility:</B>&nbsp;\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"15%\">\n");
        out.println("<SELECT CLASS=\"HEADER\" NAME=\"FacName\" ID=\"FacName\" size=\"1\">\n");
		Map warehouseInfo = (Map) session.getAttribute("warehouseInfo");
        Map facilityInfo = (Map)warehouseInfo.get(selHubnum);
        if ("All".equalsIgnoreCase(selHubnum) || selHubnum.trim().length() == 0 || facilityInfo == null)
        {
            out.println("<OPTION VALUE=\"\">Choose a Hub to get Facilities</OPTION>\n");
        }
        else
        {
            out.println("<OPTION VALUE=\"\">All</OPTION>\n");
		    out.println(radian.web.HTMLHelpObj.sorthashbyValue(facilityInfo.entrySet(),facility));
        }
        out.println("</SELECT>\n");
        out.println("</TD>\n");

        // Sort
        out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
        out.println("<B>Pick List:</B>&nbsp;\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n");
        if ("All".equalsIgnoreCase(selHubnum))
        {
          out.println("<SELECT NAME=\"SortBy\" ID=\"SortBy\" CLASS=\"HEADER\" onChange=\"getpicklistids(this)\" size=\"1\">\n");
          out.println("<option Value=\"\">Picklist ID : Time Generated</option>\n");
          out.println("<OPTION VALUE=\"\">Get Picklist IDs</OPTION>\n");
        }
        else
        {
         out.println("<SELECT NAME=\"SortBy\" CLASS=\"HEADER\" size=\"1\">\n");
         out.println("<option Value=\"\">All</option>\n");
          try
          {
              dbrs = db.doQuery("select PICKLIST_ID,to_char(PICKLIST_PRINT_DATE,'mm/dd/yyyy hh:mi AM') PICKLIST_PRINT_DATE1 from open_picklist_id_view where HUB = "+selHubnum+" order by PICKLIST_PRINT_DATE desc");
              rs=dbrs.getResultSet();
              while(rs.next())
              {
                String picklistid = BothHelpObjs.makeSpaceFromNull(rs.getString("PICKLIST_ID"));
                String selected = "";
                if ( picklistid.equalsIgnoreCase(sortby))
                {
                    selected = "selected";
                }
                out.println("<option "+selected+" value=\""+picklistid+"\">PL "+picklistid+" : "+BothHelpObjs.makeSpaceFromNull(rs.getString("PICKLIST_PRINT_DATE1"))+"</option>\n");
              }
          }
          catch (Exception e)
          {
              e.printStackTrace();
          }
          finally
          {if (dbrs != null)  { dbrs.close(); }}
        }
        out.println("</SELECT>\n");
        out.println("</TD>\n");

        //Confirm
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
        if (this.getupdateStatus())
        {
          out.println("<INPUT ID=\"shipConfirmButton\" TYPE=\"submit\" VALUE=\"Confirm\" onclick= \"return pageconfirm(this)\" NAME=\"shipConfirmButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
        }
        else
        {
        out.println("&nbsp;\n");
        }
        out.println("</TD>\n");

        //Print BOL Long
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
        out.println("<INPUT ID=\"printBolLongButton\" TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print BOL Long\" onclick= \"return bollong(this)\" NAME=\"printBolLongButton\">&nbsp;\n");
        out.println("</TD>\n");


        //Re-Print Labels
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
        out.println("<INPUT ID=\"rePrintLabelButton\" TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Re-Print Labels\" onclick= \"return reprintlabels(this)\" NAME=\"rePrintLabelButton\">&nbsp;\n");
        out.println("</TD>\n");

        out.println("</TR>\n");

        out.println("<TR>\n");

        out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
        out.println( "&nbsp;\n" );
        out.println( "</TD>\n" );

        out.println( "<TD WIDTH=\"15%\" CLASS=\"announce\">\n" );
        out.println( "&nbsp;\n" );
        out.println( "</TD>\n" );

        out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
        out.println( "&nbsp;\n" );
        out.println( "</TD>\n" );

        out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\">\n" );
        out.println( "&nbsp;\n" );
        out.println( "</TD>\n" );

        out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\">\n" );
        out.println( "<INPUT ID=\"exitLabelButton\" TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Exit Labels\" onclick= \"return generateExitLabels(this)\" NAME=\"exitLabelButton\">&nbsp;\n" );
        out.println( "</TD>\n" );

        ////Generate Large Labels
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
		out.println( "<INPUT ID=\"largeLabelButton\" TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Receiving Labels\" onclick= \"return generatelargelabels()\" NAME=\"largeLabelButton\">&nbsp;\n" );
		out.println( "</TD>\n" );
        out.println("</TD>\n");

        //Delivery Ticket
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
        out.println("<INPUT ID=\"deliveryTicketButton\" TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Delivery Ticket\" onclick= \"return deliveryticket(this)\" NAME=\"deliveryTicketButton\">&nbsp;\n");
        out.println("</TD>\n");
        out.println("</TR>\n");
        out.println("</TABLE>\n");
        out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n");
        out.println("<tr><td>");
        out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" ID=\"UserAction\" VALUE=\"NEW\">\n");
        out.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" ID=\"SubUserAction\" VALUE=\"NEW\">\n");
        out.println("<INPUT TYPE=\"hidden\" NAME=\"regenPickid\" ID=\"regenPickid\" VALUE=\"\">\n");
        out.println("<INPUT TYPE=\"hidden\" NAME=\"boldetails\" ID=\"boldetails\" VALUE=\""+boloptions+"\">\n");
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" ID=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );
		out.println("<INPUT TYPE=\"hidden\" NAME=\"isWmsHub\" ID=\"isWmsHub\" VALUE=\"N\">\n");
		out.println("</TD></tr>");
        out.println("</table>\n");
        //end table #2

    }
    catch (Exception e)
    {
        e.printStackTrace();
        out.println(radian.web.HTMLHelpObj.printError("Picking QC",intcmIsApplication));
    }

    return;
}
//end of method

private StringBuilder getHubAutomatedPutAway(Collection hubInventoryGroupOvBeanColl) {
  StringBuilder result = new StringBuilder("var altAutomatedPutaway = new Array(");
  Iterator hubIterator = hubInventoryGroupOvBeanColl.iterator();
  int count = 0;
  while (hubIterator.hasNext()) {
    HubInventoryGroupOvBean hubInventoryGroupOvBean = (HubInventoryGroupOvBean) hubIterator.next();
    if (count > 0)
      result.append(",");
    result.append("{");
    result.append("id: '").append(hubInventoryGroupOvBean.getBranchPlant()).append("',");
    result.append("automatedPutaway: '").append(hubInventoryGroupOvBean.getAutomatedPutaway()).append("'");
    result.append("}");
    count++;
  }
  result.append(");");
  return result;
}

private String buildDetails( Vector data )
{
  StringBuffer Msg=new StringBuffer();
  StringBuffer MsgTmp1=new StringBuffer();
  StringBuffer MsgTmp=new StringBuffer();
  StringBuffer MsgTmpDate=new StringBuffer();
  Hashtable hDNext=new Hashtable();
  String checkednon="";
  String Color="CLASS=\"Inwhite";
  String intColor="CLASS=\"Inwhite";

  try
  {
    Hashtable summary=new Hashtable();
    summary= ( Hashtable ) data.elementAt( 0 );
    int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

	out.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showpickingpagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n" );
    //start table #3
    Msg.append( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

    int i=0; //used for detail lines

    int ItemIdCount=0;
    int lastItemNum=1;
    int materilReqOriginCount=0;

    int lastdate=1;

    boolean FirstRow=false;
    boolean FirstdateRow=false;
    int groupNumber = 1;
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
        Msg.append( "<TH width=\"2%\"  height=\"38\">Print" );
        if ( loop == 0 )
        {
          Msg.append( "<BR><INPUT TYPE=\"checkbox\" value=\"\" onClick=\"checkall('row_chk','1')\" NAME=\"chkall\" id=\"chkall\">" );
        }
        Msg.append( "</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Picklist ID</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Facility</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Delivery Point</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Ship To</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Requestor</TH>\n" );
        Msg.append( "<TH width=\"8%\"  height=\"38\">MR-Line</TH>\n" );
        Msg.append( "<TH width=\"7%\"  height=\"38\">MR Notes</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Part</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Type</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Item</TH>\n" );
        Msg.append( "<TH width=\"25%\"  height=\"38\">Desc</TH>\n" );
				Msg.append( "<TH width=\"2%\"  height=\"38\">Close MR</TH>\n" );
        Msg.append( "<TH width=\"8%\"  height=\"38\">Packaging</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Bin</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Receipt ID</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Mfg-Lot</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Exp Date</TH>\n" );
        Msg.append( "<TH width=\"8%\"  height=\"38\">MR-Line</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Pick Qty</TH>\n" );
        Msg.append( "<TH width=\"5%\"  height=\"38\">Qty Picked</TH>\n" );
        Msg.append( "</tr>\n" );
        createHdr=false;
      }

      Hashtable hD=new Hashtable();
      hD= ( Hashtable ) data.elementAt( i );

      String Next_mr="";
      String Next_item="";
      String Next_receiptid="";
      String Next_picklist="";

      if ( ! ( i == total ) )
      {
        hDNext=new Hashtable();
        hDNext= ( Hashtable ) data.elementAt( i + 1 );
        Next_mr=hDNext.get( "MR_LINE" ) == null ? "&nbsp;" : hDNext.get( "MR_LINE" ).toString();
        Next_item=hDNext.get( "ITEM_ID" ) == null ? "&nbsp;" : hDNext.get( "ITEM_ID" ).toString();
        Next_picklist=hDNext.get( "PICKLIST_ID" ) == null ? "&nbsp;" : hDNext.get( "PICKLIST_ID" ).toString();
        Next_receiptid=hDNext.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hDNext.get( "RECEIPT_ID" ).toString();
      }
      else
      {
        Next_mr=" ";
        Next_item=" ";
        Next_receiptid="";
        Next_picklist="";
      }

      // get main information
      String facility=hD.get( "FACILITY_ID" ).toString();
      String workarea=hD.get( "APPLICATION_DESC" ).toString();
      String workareaDesc=hD.get( "APPLICATION_DESC" ).toString();
      if (!workarea.equalsIgnoreCase(workareaDesc))
      {
       workarea = workarea + ": " +workareaDesc;
      }
      String prnumber=hD.get( "MR_LINE" ).toString();
      String part=hD.get( "CAT_PART_NO" ).toString();
      String partdesc=hD.get( "PART_DESCRIPTION" ).toString();
      String pkg=hD.get( "PACKAGING" ).toString();
      String requestor=hD.get( "REQUESTOR" ).toString();
      String pickid=hD.get( "PICKLIST_ID" ).toString();
      String itemid=hD.get( "ITEM_ID" ).toString();
      //String lotstatus=hD.get( "LOT_STATUS" ).toString();
      String mfglot=hD.get( "MFG_LOT" ).toString();
      String bin=hD.get( "BIN" ).toString();
      String receiptId=hD.get( "RECEIPT_ID" ).toString();
      String inventoryqty=hD.get( "INVENTORY_QUANTITY" ).toString();
      String picklistqty=hD.get( "PICKLIST_QUANTITY" ).toString();
      String qtypicked=hD.get( "QUANTITY_PICKED" ).toString();
      String droppoint=hD.get( "DELIVERY_POINT" ).toString();
      String type=hD.get( "STOCKING_METHOD" ).toString();
      String expdate=hD.get( "EXPIRE_DATE" ).toString();
      String shipto=hD.get( "SHIP_TO_LOCATION_ID" ).toString();
			String pickable = hD.get( "PICKABLE" ).toString();
			String cabreplenishment = hD.get( "CABINET_REPLENISHMENT" ).toString();
			String hazmatIdMissing = hD.get( "HAZMAT_ID_MISSING" ).toString();
			String invengrp=BothHelpObjs.makeBlankFromNull( hD.get( "INVENTORY_GROUP" ).toString() );
			String receiptDocumentAvailable  =  hD.get("RECEIPT_DOCUMENT_AVAILABLE")==null?"":hD.get("RECEIPT_DOCUMENT_AVAILABLE").toString();
			String nonintegerReceiving  =  hD.get("NONINTEGER_RECEIVING")==null?"":hD.get("NONINTEGER_RECEIVING").toString();
        String opsEntityId  =  hD.get("OPS_ENTITY_ID")==null?"":hD.get("OPS_ENTITY_ID").toString();
        String materialRequestOrigin  =  hD.get("MATERIAL_REQUEST_ORIGIN")==null?"":hD.get("MATERIAL_REQUEST_ORIGIN").toString();
        if ("Order Entry".equalsIgnoreCase(materialRequestOrigin))
        {
            materilReqOriginCount ++;
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

      //String LINE_STATUS=hD.get( "LINE_STATUS" ).toString();
      String qcdate=hD.get( "QC_DATE" ).toString().trim();
      String mrnotes=hD.get( "MR_NOTES" ).toString();
      String prnumber1=hD.get( "PR_NUMBER" ).toString().trim();
      String lineitem1=hD.get( "LINE_ITEM" ).toString().trim();
      String companyid=hD.get( "COMPANY_ID" ).toString().trim();
      String currencyId=hD.get( "CURRENCY_ID" ).toString().trim();
      String mrCompletePickable=hD.get( "MR_COMPLETE_PICKABLE" ).toString().trim();
      String issueId=hD.get( "ISSUE_ID" ).toString();
      String multipleCatPartNo=hD.get( "MULTIPLE_CAT_PART_NO" ).toString();
      String definedShelfLifeItem=hD.get( "DEFINED_SHELF_LIFE_ITEM" ).toString().trim();

            boolean qced = false;

			String ismrCritical = (hD.get("CRITICAL") == null ? "&nbsp;" :
			 hD.get("CRITICAL").toString());
			if ("Y".equalsIgnoreCase(ismrCritical)) {
			 Color = "CLASS=\"red";
			 intColor = "CLASS=\"red";
			}

			if ("S".equalsIgnoreCase(ismrCritical)) {
			 Color = "CLASS=\"pink";
			 intColor = "CLASS=\"pink";
			}

			if (!"Y".equalsIgnoreCase(pickable) || !"Y".equalsIgnoreCase(mrCompletePickable) || "Y".equalsIgnoreCase(definedShelfLifeItem)
                || ("Y".equalsIgnoreCase(multipleCatPartNo) && !("Intercompany MR".equalsIgnoreCase(materialRequestOrigin) ||
                    "Customer Consignment".equalsIgnoreCase(materialRequestOrigin) ||
                    "Cabinet Scan".equalsIgnoreCase(materialRequestOrigin) ||
                    "Customer PO Stage".equalsIgnoreCase(materialRequestOrigin) || "Order Entry".equalsIgnoreCase(materialRequestOrigin) )) ) {
			 //Color ="CLASS=\"yellow";
			 intColor = "CLASS=\"yellow";
			}
			else {
			 intColor = Color;
			}

			if ("MISSING".equalsIgnoreCase(hazmatIdMissing))
			{
			 Color = "CLASS=\"orange";
			 intColor = "CLASS=\"orange";
			}

      if ( ! ( qcdate.trim().length() == 0 ) )
      {
        qced=true;
      }

      String chkbox_value="no";
      if ( checkednon.equalsIgnoreCase( "checked" ) )
      {
        chkbox_value="yes";
      }

      boolean Samemrline=false;
      boolean Sameitemid=false;

      if ( prnumber.equalsIgnoreCase( Next_mr ) && Next_picklist.equalsIgnoreCase( pickid ) )
      {
        Samemrline=true;
        lastItemNum++;
        if ( Next_item.equalsIgnoreCase( itemid ) )
        {
          Sameitemid=true;
          lastdate++;
        }
      }

      if ( Sameitemid )
      {
        if ( lastdate == 2 )
        {
          FirstdateRow=true;
        }
        if ( !FirstdateRow )
        {
          MsgTmp1.append( "<TR " + intColor + "\" id=\"rowId"+i+"\" onmouseup=\"selectRow('"+i+"')\" >\n" );
        }
        MsgTmp1.append( "<td " + intColor + "\" width=\"8%\" height=\"38\">" + pkg + "</td>\n" );
        MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + bin + "</td>\n" );
        MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" name=\"receiptId" +i+ "\" id=\"receiptId" +i+ "\"  value=\""  + receiptId + "\">" + receiptId + "\n" );
				if ("Y".equalsIgnoreCase(receiptDocumentAvailable))
				{
				 MsgTmp1.append("<IMG src=\"/images/buttons/document.gif\" alt=\"View/Add Receipt Documents\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:showProjectDocuments('"+receiptId+"','"+invengrp+"')\"></td>\n");
				}
				else
				{
				 MsgTmp1.append("<IMG src=\"/images/buttons/plus.gif\" alt=\"Add Receipt Documents\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:showProjectDocuments('"+receiptId+"','"+invengrp+"')\"></td>\n");
				}

				MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + mfglot + "</td>\n" );
        if ( "01/01/3000".equalsIgnoreCase( expdate ) )
        {
          MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">INDEFINITE</td>\n" );
        }
        else
        {
          MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + expdate + "</td>\n" );
        }
        MsgTmp1.append( "<td height=\"25\" " + intColor + "l\" width=\"7%\">" + prnumber + "</td>\n" );
        MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + picklistqty + "<input type=\"hidden\" name=\"hgroupNumber" +i+ "\" id=\"hgroupNumber" +i+ "\" value=\"" + groupNumber + "\">"+"<input type=\"hidden\" name=\"picklistqty" +i+ "\" id=\"picklistqty" +i+ "\" value=\"" + picklistqty+ "\">"+"<input type=\"hidden\" name=\"inventoryqty" +i+ "\" id=\"inventoryqty" +i+ "\" value=\"" + inventoryqty + "\">"+"</td>\n" );
        if ( qced || !this.getupdateStatus() || !"Y".equalsIgnoreCase(pickable) || !"Y".equalsIgnoreCase(mrCompletePickable) || "MISSING".equalsIgnoreCase(hazmatIdMissing))
        {
          MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + qtypicked + "</td>\n" );
        }else if ("Y".equals(isWmsHub)) {
          MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + qtypicked + "</td>\n" );
        }else {
          MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" name=\"nonintegerReceiving"+i+"\" id=\"nonintegerReceiving"+i+"\" value=\"" + nonintegerReceiving + "\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue(name,this,'" + i + "')\" name=\"qty_picked" + i + "\" id=\"qty_picked" + i + "\"  value=\"" + qtypicked + "\" maxlength=\"10\" size=\"5\"></td>\n" );
        }

        MsgTmp1.append( "</TR>\n" );

      }
      else if ( !Sameitemid )
      {
        if ( ( !FirstdateRow ) && lastdate > 1 )
        {
          MsgTmp1.append( "<TR id=\"rowId"+i+"\" onmouseup=\"selectRow('"+i+"')\" >\n" );
        }

        MsgTmp1.append( "<td " + intColor + "\" width=\"8%\" height=\"38\">" + pkg + "</td>\n" );
        MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + bin + "</td>\n" );
				MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" name=\"receiptId" +i+ "\" id=\"receiptId" +i+ "\" value=\"" + receiptId + "\">" + receiptId + "\n" );
				if ("Y".equalsIgnoreCase(receiptDocumentAvailable))
				{
				 MsgTmp1.append("<IMG src=\"/images/buttons/document.gif\" alt=\"View/Add Receipt Documents\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:showProjectDocuments('"+receiptId+"','"+invengrp+"')\"></td>\n");
				}
				else
				{
				 MsgTmp1.append("<IMG src=\"/images/buttons/plus.gif\" alt=\"Add Receipt Documents\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:showProjectDocuments('"+receiptId+"','"+invengrp+"')\"></td>\n");
				}

				MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + mfglot + "</td>\n" );
        if ( "01/01/3000".equalsIgnoreCase( expdate ) )
        {
          MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">INDEFINITE</td>\n" );
        }
        else
        {
          MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + expdate + "</td>\n" );
        }
        MsgTmp1.append( "<td height=\"25\" " + intColor + "l\" width=\"7%\">" + prnumber + "</td>\n" );
        MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + picklistqty + "<input type=\"hidden\" name=\"hgroupNumber" +i+ "\" id=\"hgroupNumber" +i+ "\" value=\"" + groupNumber + "\">"+"<input type=\"hidden\" name=\"picklistqty" +i+ "\" id=\"picklistqty" +i+ "\" value=\"" + picklistqty+ "\">"+"<input type=\"hidden\" name=\"inventoryqty" +i+ "\" id=\"inventoryqty" +i+ "\" value=\"" + inventoryqty + "\">"+"</td>\n" );        
        if ( qced || !this.getupdateStatus() || !"Y".equalsIgnoreCase(pickable) || !"Y".equalsIgnoreCase(mrCompletePickable) || "MISSING".equalsIgnoreCase(hazmatIdMissing) )
        {
          MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\">" + qtypicked + "</td>\n" );
        }else if ("Y".equals(isWmsHub)) {
          MsgTmp1.append("<td " + intColor + "\" width=\"5%\" height=\"38\">" + qtypicked + "</td>\n");
        }else
        {
          MsgTmp1.append( "<td " + intColor + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" name=\"nonintegerReceiving"+i+"\" id=\"nonintegerReceiving"+i+"\" value=\"" + nonintegerReceiving + "\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue(name,this,'" + i + "')\" name=\"qty_picked" + i + "\" id=\"qty_picked" + i + "\"  value=\"" + qtypicked + "\" maxlength=\"10\" size=\"5\"></td>\n" );
        }

        MsgTmp1.append( "</TR>\n" );
        MsgTmpDate.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastdate + "\" ><A HREF=\"javascript:enterdotinfo('" + itemid + "')\">" + itemid + "</A></td>\n\n" );
        MsgTmpDate.append( "<td height=\"25\" " + Color + "l\" width=\"10%\" ROWSPAN=\"" + lastdate + "\">" + partdesc + "</td>\n" );
		if (cabreplenishment.length() > 0 && !qced)
		{
		  MsgTmpDate.append( "<td height=\"25\" " + Color + "\" width=\"2%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastdate + "\"><INPUT TYPE=\"checkbox\" value=\"Y\" " + (cabreplenishment.equalsIgnoreCase("Y") ? "checked" : "") + " name=\"closeMrline" + pickid + prnumber + "\" id=\"closeMrline" + pickid + prnumber + "\"></td>\n" );
		}
		else
		{
		  MsgTmpDate.append( "<td height=\"25\" " + Color + "\" width=\"2%\" ROWSPAN=\"" + lastdate + "\" >&nbsp;</td>\n\n" );
		}

        MsgTmpDate.append( MsgTmp1 );
        MsgTmp1=new StringBuffer();
        lastdate=1;
        //continue;
      }

      if ( Samemrline )
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

        if ( !Sameitemid )
        {
          MsgTmp.append( MsgTmpDate );
        }

        MsgTmp.append( "</TR>\n" );
        if ( lastItemNum == 1 )
        {
          MsgTmp.append( "<input type=\"hidden\" name=\"Start" + pickid + prnumber + "\" id=\"Start" + pickid + prnumber + "\" value=\"" + i + "\">\n" );
        }
        MsgTmp.append( "<input type=\"hidden\" name=\"Stop" + pickid + prnumber + "\" id=\"Stop" + pickid + prnumber + "\" value=\"" + i + "\">\n" );
        Msg.append( "<TR id=\"rowId"+i+"\" onmouseup=\"selectRow('"+i+"')\" >\n" );

        if ("N".equals(isWmsHub))
          Msg.append( "<td height=\"25\" " + Color + "\" width=\"2%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\"><INPUT TYPE=\"checkbox\" onClick=\"checkpickvalue(name,this)\" value=\"" + ( chkbox_value ) + "\" " + checkednon + " NAME=\"row_chk" + i + "\" id=\"row_chk" + i + "\"></td>\n" );
        else
          Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\"></td>\n" );
        Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">PL " + pickid + "<INPUT TYPE=\"hidden\" value=\"\" NAME=\"groupCheck" + groupNumber + "\" id=\"groupCheck" + groupNumber + "\"></td>\n" );
        Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + facility + "</td>\n" );
        Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + workarea + "</td>\n" );
        Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + droppoint + "</td>\n" );
        Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + shipto + "</td>\n" );
        Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + requestor + "</td>\n" );
        groupNumber++;
        if ( this.getupdateStatus() && "N".equals(isWmsHub) )
        {
          Msg.append( "<td height=\"25\" " + Color + "l\" width=\"7%\" ROWSPAN=\"" + lastItemNum + "\"><A HREF=\"javascript:reversePick(name,this,'" + prnumber1 + "','" + lineitem1 + "','" + pickid + "')\">" + prnumber + "</A></td>\n" );
        }
        else
        {
          Msg.append( "<td height=\"25\" " + Color + "l\" width=\"7%\" ROWSPAN=\"" + lastItemNum + "\">" + prnumber + "</td>\n" );
        }

		if (mrnotes.length() > 0)
		{
		  Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ID=\"lineNotesTd"+i+"\" ROWSPAN=\"" + lastItemNum + "\" onMouseOver=style.cursor=\"hand\">\n" );
		  Msg.append( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n" );
		  Msg.append( "<!--\n" );
		  Msg.append( "var rowId  =  document.getElementById(\"lineNotesTd"+i+"\");\n" );
		  Msg.append( "rowId.attachEvent('onclick',function () {\n" );
		  Msg.append( "	showLineNotes('"+i+"');});\n" );
		  Msg.append( "// -->\n" );
		  Msg.append( "</SCRIPT>\n" );
		  Msg.append( "<P ID=\"lineNoteslink"+i+"\">+</P>\n" );
		  Msg.append( "<DIV ID=\"lineNotes"+i+"\" CLASS=\"displaynone\" onMouseOver=style.cursor=\"hand\">\n" );
		  Msg.append( mrnotes );
		  Msg.append( "</DIV>\n" );
		  Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"notesVisible"+i+"\" value=\"No\" >\n" );
		}
		else
		{
		  Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ID=\"lineNotesTd"+i+"\" ROWSPAN=\"" + lastItemNum + "\">\n" );
		}
        Msg.append( "<INPUT TYPE=\"hidden\" name=\"inventoryGroup"+i+"\" id=\"inventoryGroup"+i+"\"value=\""+invengrp+"\" >\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" name=\"colorClass"+i+"\" id=\"colorClass"+i+"\"value=\""+Color+"\" >\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" name=\"prNumber"+i+"\" id=\"prNumber"+i+"\"value=\""+prnumber1+"\" >\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" name=\"lineItem"+i+"\" id=\"lineItem"+i+"\"value=\""+lineitem1+"\" >\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" name=\"companyId"+i+"\" id=\"companyId"+i+"\"value=\""+companyid+"\" >\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" name=\"currencyId"+i+"\" id=\"currencyId"+i+"\"value=\""+currencyId+"\" >\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" name=\"opsEntityId"+i+"\" id=\"opsEntityId"+i+"\"value=\""+opsEntityId+"\" >\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" name=\"issueId"+i+"\" id=\"issueId"+i+"\"value=\""+issueId+"\" >\n" );
        
        Msg.append( "</TD>\n" );

        Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + part + "</td>\n" );
        Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + type + "</td>\n" );
        Msg.append( MsgTmp );

        MsgTmp=new StringBuffer();
        MsgTmpDate=new StringBuffer();
        lastItemNum=1;

        if ( ! ( prnumber.equalsIgnoreCase( Next_mr ) && Next_picklist.equalsIgnoreCase( pickid ) ) )
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
        continue;
      }

      if ( !FirstRow )
      {
        MsgTmp.append( "<TR>\n" );
      }

      if ( lastItemNum == 2 )
      {
        MsgTmp.append( "<input type=\"hidden\" name=\"Start" + pickid + prnumber + "\" id=\"Start" + pickid + prnumber + "\" value=\"" + i + "\">\n" );
      }

      if ( !Sameitemid )
      {
        MsgTmp.append( MsgTmpDate );
        MsgTmpDate=new StringBuffer();
      }
    }

    Msg.append( "</table>\n" );
    Msg.append( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
    Msg.append( "<tr>" );
    Msg.append( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
    Msg.append( "</TD></tr>" );
    Msg.append( "</table>\n" );
    Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"totallines\" ID=\"totallines\" VALUE=\"" + i + "\">\n" );
    Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"materilReqOriginCount\" ID=\"materilReqOriginCount\" VALUE=\"" + materilReqOriginCount + "\">\n" );      
    Msg.append( "</form>\n" );
    Msg.append( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n" );
	Msg.append( "<!--\n" );
    Msg.append( "/*TODO internationalize right click menu*/\n" );
    Msg.append( "with ( milonic=new menuname(\"showAddCharges\") ) {\n" );
    Msg.append( "top=\"offset=2\";\n" );
    Msg.append( "style=submenuStyle;\n" );
    Msg.append( "itemheight=17;\n" );
    Msg.append( "// style = contextStyle;\n" );
    Msg.append( "// margin=3;\n" );
    Msg.append( "aI( \"text=Add Header Charges;url=javascript:addHeaderCharges();\" );\n" );
    Msg.append( "aI( \"text=Add Line Charges;url=javascript:addLineCharges();\" );\n" );
    Msg.append( "}\n" );

    Msg.append( "drawMenus();\n" );
	Msg.append( "// -->\n" );
	Msg.append( "</SCRIPT>\n" );            
    Msg.append( "</body></html>\n" );
  }
  catch ( Exception e )
  {
    e.printStackTrace();
    out.println( radian.web.HTMLHelpObj.printError( "Picking QC",intcmIsApplication ) );
  }
  return Msg.toString();
}
//End of method

}
//END OF CLASS

