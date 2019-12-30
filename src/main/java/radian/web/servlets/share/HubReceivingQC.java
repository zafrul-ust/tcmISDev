package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.HubReceivingTables;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * This class provides receiving functionality for the client's receiving servlet.
 * Clients session  are oraganized as follows
 * - New
 * 03-12-03 added Manufacturer in the DOS Heading
 * 06-27-03 Fixed the remove receipt error
 * 07-28-03 Code cleanup- was checking for null twice which was not necessary
 * 08-15-03 - Sending emails through common object
 * 11-13-03 - Giving a link to see inventory transfer details in the PO column for trans type IT. Some more code cleanup.
 * Also cannot qc if the received qty id more than issued on the inventory transfer
 * 11-24-03 - Cannot put the recipt in a pickable status if you don't have quality control permissions for items which are quality controled
 * 12-12-03 - Not allowing Back button usage. Fixed stylesheet problems
 * 01-16-04 - QC person can change MFG_LOT and EXP_DATE for qulaity control items
 * 01-27-04 - Receiving QC permissions based on Inventory Group. User_group_membertable is used to define which Hub's they have permission
 *            and User_group_member_ig is used for inventory_groups
 * 02-16-04 - Sorting Drop Downs
 * 03-31-04 - Showing legend and coloring excess material. Can search by ponumber
 * 04-19-04 - Changeing the inventory group drop down to show INVENTORY_GROUP_NAME in the display of drop dowm. Getting the Hub list from the HUB_PREF
 * table instead of the user_group_member table. Chaning the receiving to accomodate receiving kits with different parts with different MFG_LOT,EXP_DATE and BIN.
 * Changin the date format to allow input in the following formats mmddyyyy and mmddyyy
 * 05-07-04 - Sorting the Hub Drop Down based on the prority in hub_pref table.
 * 06-01-04 - Breaking up the search to make the query faster
 * 07-22-04 - Showing notes on the receipt.
 * 07-22-04 - Giving the ability to enter receipt notes
 * 10-04-04 - Allowing to put a receipt into Incoming status only if it is not approved.
 * 11-02-04 - Making it possible to sort the page by BIN,LOT etc
 * 01-21-05 - If you are a member of onlynonPickableStatus user group, you can only place the receipt into non-pickable statuses
 * 02-28-05 - Giving the option to not print Kit/Case Qty Labels.
 * 06-22-05 - Showing MR-Line for returned receipts.
 * 07-06-05 - Allowing QC for non-pickable statuses without a exp date
 * 07-28-05 - Giving the option to enter delivery ticket information.
*/

public class HubReceivingQC
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String thedecidingRandomNumber=null;
    private HubReceivingTables  hubObj = null;
    private PrintWriter out = null;
    private boolean completeSuccess = true ;
    private boolean noneToUpd = true ;
    private Vector receiptIdstoLabel = null;
    private Vector numbersoflabels = null;
    private String Receiving_Servlet = "";
    private String Label_Servlet = "";
    private String paper_size = "";
    private boolean allowupdate;
    private boolean intcmIsApplication = false;
	private static org.apache.log4j.Logger reclog = org.apache.log4j.Logger.getLogger(HubReceivingQC.class);

    public void setupdateStatus(boolean id)
    {
      this.allowupdate = id;
    }

    private boolean getupdateStatus()  throws Exception
    {
     return this.allowupdate;
    }

    public HubReceivingQC(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException
    {
        doPost(request,response);
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException
    {
        hubObj = new HubReceivingTables(db);
        out = response.getWriter();
        response.setContentType("text/html");
        HttpSession session = request.getSession();

        PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        intcmIsApplication = false;
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

        String branch_plant= session.getAttribute( "BRANCH_PLANT" ) == null ? "" : session.getAttribute( "BRANCH_PLANT" ).toString();
        String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
        String CompanyID = session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
        Receiving_Servlet = bundle.getString("HUBRECV_SERVLET_QC");
        Label_Servlet = bundle.getString("LABEL_SERVLET");

        String User_Search = null;
        String User_Action = null;
        String SubUser_Action = null;
        String User_Session = "";
        String User_Sort    = "";
        String generate_labels = "";
        String remove_receipt_id = "";
        String remove_action = "";

        generate_labels = request.getParameter("generate_labels");
        if (generate_labels == null)
               generate_labels = "No";
        paper_size = request.getParameter("paperSize");
        if (paper_size == null)
              paper_size = "31";
        User_Session  = request.getParameter("session");
        if (User_Session == null)
              User_Session = "New";
        User_Action   = request.getParameter("UserAction");
        if (User_Action == null)
              User_Action = "New";
        User_Search   = request.getParameter("SearchField");
		if ( User_Search == null )
		  User_Search="";
		User_Search=User_Search.trim();
        User_Sort     = request.getParameter("SortBy");
        if (User_Sort == null)
              User_Sort = "1";
        String SortresultsBy  = request.getParameter("SortresultsBy");
        if (SortresultsBy == null)
              SortresultsBy = "BIN";

        String invengrp=request.getParameter( "invengrp" );
        if ( invengrp == null )
          invengrp="";
        invengrp=invengrp.trim();

        remove_receipt_id  = BothHelpObjs.makeSpaceFromNull(request.getParameter("remove_receipt_id"));
        remove_action = BothHelpObjs.makeSpaceFromNull(request.getParameter("removeaction"));

		thedecidingRandomNumber=request.getParameter( "thedecidingRandomNumber" );
		if ( thedecidingRandomNumber == null )
		  thedecidingRandomNumber="";
		thedecidingRandomNumber=thedecidingRandomNumber.trim();

		SubUser_Action=request.getParameter( "SubUserAction" );
		if ( SubUser_Action == null )
		  SubUser_Action="JSError2";

		String searchlike=request.getParameter( "searchlike" );
		if ( searchlike == null )
		{
		  searchlike="";
		}
		searchlike=searchlike.trim();

		String searchfor=request.getParameter( "searchfor" );
		if ( searchfor == null )
		{
		  searchfor="";
		}
		searchfor=searchfor.trim();
		if (searchlike.length() == 0 ) {searchlike = "a.RADIAN_PO";}

		String printKitLabels=request.getParameter( "printKitLabels" );
		if ( printKitLabels == null )
		  printKitLabels="";

        try
        {
		  Random rand=new Random();
		  int tmpReq=rand.nextInt();
		  Integer tmpReqNum=new Integer( tmpReq );

	      Hashtable hub_list_set=new Hashtable();
		  hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );


		  if ( ! ( SubUser_Action.equalsIgnoreCase( "removereceipt" ) || User_Action.equalsIgnoreCase( "showterminalstatus" ) || User_Action.equalsIgnoreCase( "Unreceive" ) ) )
		  {
			if ( thedecidingRandomNumber.length() > 0 )
			{
			  String randomnumberfromsesion=session.getAttribute( "RECQCRNDCOOKIE" ) == null ? "" : session.getAttribute( "RECQCRNDCOOKIE" ).toString();
			  if ( randomnumberfromsesion.equalsIgnoreCase( thedecidingRandomNumber ) )
			  {
				thedecidingRandomNumber=tmpReqNum.toString();
				session.setAttribute( "RECQCRNDCOOKIE",thedecidingRandomNumber );
			  }
			  else
			  {
				thedecidingRandomNumber=tmpReqNum.toString();
				session.setAttribute( "RECQCRNDCOOKIE",thedecidingRandomNumber );
				session.setAttribute( "DATA",new Vector() );
				//Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
				Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
				Vector lot_status_set= ( Vector ) session.getAttribute( "STATUS_SET" );
				Vector expdate_invengrps= ( Vector ) session.getAttribute( "EXP_ALLOWED_INVENGRP" );
				Vector lotstatus_invengrps= ( Vector ) session.getAttribute( "STATUS_SET_ALLOWED_INVENGRP" );
				Vector recqc_invengrps= ( Vector ) session.getAttribute( "RECQC_ALLOWED_INVENGRP" );
				Vector nonpickable_invengrps= ( Vector ) session.getAttribute( "NON_PICKABLE_INVENGRP" );

				buildHeader( null,hub_list_set,branch_plant,User_Sort,"",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
				out.println( "<BR><BR><CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>" );
				return;
			  }
			}
			else
			{
			  thedecidingRandomNumber=tmpReqNum.toString();
			  session.setAttribute( "RECQCRNDCOOKIE",thedecidingRandomNumber );
			}
	      }

          if ( User_Session.equalsIgnoreCase( "New" ) )
          {
            Vector lot_status_set11=radian.web.HTMLHelpObj.createStatusSet( db );
            session.setAttribute( "STATUS_SET",lot_status_set11 );
            session.setAttribute( "HUB_COMPANY_IDS",radian.web.HTMLHelpObj.createCompanylist( db ) );

            //Hashtable hub_list_set=new Hashtable();
			Vector expdate_invengrps = new Vector();
			Vector lotstatus_invengrps = new Vector();
			Vector recqc_invengrps = new Vector();
			Vector nonpickable_invengrps = new Vector();
			/*if ( this.getupdateStatus() )
			{
			  hub_list_set=radian.web.HTMLHelpObj.createHubList( db,personnelid,CompanyID );
			}
			else
			{
			  hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,CompanyID );
			}*/

		   Hashtable initialinvData = new Hashtable ();
		   Hashtable hub_list1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		   if (hub_list1.size() > 0)
		   {
			 initialinvData=radian.web.poHelpObj.getinvgrpInitialData( db,hub_list_set );
			 session.setAttribute( "INVENGRP_DATA",initialinvData );
		   }

			expdate_invengrps  = radian.web.HTMLHelpObj.createvgrpmemlist(db,personnelid,"ExpUpdate");
			lotstatus_invengrps  = radian.web.HTMLHelpObj.createvgrpmemlist(db,personnelid,"PickStatusUpd");
			session.setAttribute( "EXP_ALLOWED_INVENGRP",expdate_invengrps );
			session.setAttribute( "STATUS_SET_ALLOWED_INVENGRP",lotstatus_invengrps );
			recqc_invengrps  = radian.web.HTMLHelpObj.createvgrpmemlist(db,personnelid,"ReceivingQC");
			session.setAttribute( "RECQC_ALLOWED_INVENGRP",recqc_invengrps );
			nonpickable_invengrps  = radian.web.HTMLHelpObj.createvgrpmemlist(db,personnelid,"onlynonPickableStatus");
			session.setAttribute( "NON_PICKABLE_INVENGRP",nonpickable_invengrps );

            if ( hub_list1.size() < 1 )
            {
              buildHeader( null,hub_list_set,"",User_Sort,"",SortresultsBy,initialinvData,invengrp,User_Search,searchlike,searchfor,session,printKitLabels  );
              out.println( radian.web.HTMLHelpObj.printHTMLNoFacilities() );
              out.close();
              hub_list_set=null;
            }
            else
            {
              //session.setAttribute( "HUB_SET",hub_list_set );
              buildHeader( null,hub_list_set,"",User_Sort,"",SortresultsBy,initialinvData,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
              out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
              out.close();
              hub_list_set=null;
            }
          }
          else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "showterminalstatus" ) ) )
          {
            Vector temp2= ( Vector ) session.getAttribute( "HUB_COMPANY_IDS" );

            String terminallotstatus =request.getParameter( "termlotstatus" );
            if ( terminallotstatus == null )
              terminallotstatus="";

            String terlinenumber=request.getParameter( "terlinenumber" );
            if ( terlinenumber == null )
              terlinenumber="31";

            buildtermiallot(temp2,terminallotstatus,terlinenumber);
            out.close();
            temp2 = null;
          }
          else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "Unreceive" ) ) )
          {
            previousReceiving obj3=new previousReceiving( bundle,db );
            //StringBuffer MsgUn=new StringBuffer();

            if ( remove_receipt_id.length() > 0 )
            {
              out.println( "<HTML><HEAD>\n" );
              out.println( "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n" );
              out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
              out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
              out.println( "<title>Hub Receiving QC</title>\n" );
              out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
              out.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n" );
              out.println( "<!-- \n" );
              out.println( "function Unreceive()\n" );
              out.println( "{\n" );
              out.println( "opener.document.receiving.UserAction.value = \"UPDATE\";\n" );
              out.println( "opener.document.receiving.SubUserAction.value = \"removereceipt\" ;\n" );
              out.println( "opener.document.receiving.remove_receipt_id.value = \""+remove_receipt_id+"\";\n" );
              out.println( "opener.document.receiving.submit();" );
              out.println( "window.close();\n" );
              out.println( "}\n" );
              out.println( "function Search()\n" );
              out.println( "{\n" );
              out.println( "  opener.document.receiving.UserAction.value = \"NEW\";\n" );
              out.println( "  opener.document.receiving.SubUserAction.value = \"NA\";\n" );
              out.println( "  opener.document.receiving.DuplLineNumber.value = \"NA\"  ;\n" );
              out.println( "  opener.document.receiving.AddBin_Item_Id.value = \"NA\" ;\n" );
              out.println( "  opener.document.receiving.AddBin_Bin_Id.value = \"NA\" ;\n" );
              out.println( "  opener.document.receiving.submit();\n" );
              out.println( "window.close();\n" );
              out.println( "}\n" );
              out.println( "function cancel()\n" );
              out.println( "{\n" );
              out.println( "window.close();\n" );
              out.println( "}\n" );
              out.println( "//-->     \n" );
              out.println( "</SCRIPT></HEAD><BODY> \n" );

              if ( !remove_action.equalsIgnoreCase( "Yes" ) )
              {
                out.println( "<FORM METHOD=\"POST\" name=\"MfgidLike\" ACTION=\"" + Receiving_Servlet +
                              "&UserAction=Unreceive&removeaction=Yes&SubUserAction=Unreceive&remove_receipt_id=" + remove_receipt_id + "\">\n" );
                out.println( "<BR><FONT SIZE=\"2\" FACE=\"Arial\">Do you want to reverse Receipt Id: <B>" + remove_receipt_id + "</B><BR>\n" );
                out.println( "</FONT><BR><BR>\n" );
                out.println( "<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Yes\" NAME=\"SubmitButton\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
                out.println( "<INPUT TYPE=\"reset\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Cancel\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n" );
                out.println( "</FORM>\n" );
                out.println( "</BODY></HTML>\n" );
                //out.println( MsgUn );
                out.close();
              }
              else
              {
                if ( obj3.unreceiveReceipt( remove_receipt_id ) )
                {
                  out.println( "<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\"javascript:cancelUnreceive()\">\n" );
                  out.println( "<BR><FONT SIZE=\"2\" FACE=\"Arial\">Success.<BR>\n" );
                  out.println( "Receipt Id: <B>" + remove_receipt_id + "</B> reversed Successfully.\n" );
                  out.println( "<BR><BR>\n" );
                  out.println( "<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Ok\" OnClick=\"Unreceive()\" NAME=\"CloseButton\"></CENTER>\n" );
                  out.println( "</FORM>\n" );
                  out.println( "</BODY></HTML>\n" );
                  //out.println( MsgUn );
                  out.close();
                }
                else
                {
                  radian.web.emailHelpObj.senddeveloperemail("Receiving QC -receipt reverse error","Error occured while deleting receipt Id " + remove_receipt_id + "" );

                  out.println( "<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\"javascript:cancelSearch()\">\n" );
                  out.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\"><B>Error occured.</B><BR></FONT><FONT SIZE=\"2\" FACE=\"Arial\">\n" );
                  out.println( "Receipt Id: <B>" + remove_receipt_id + " not reversed.</B><BR>Contact tech support for more information.</FONT><BR><BR>\n" );
                  out.println( "<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n" );
                  out.println( "</FORM>\n" );
                  out.println( "</BODY></HTML>\n" );
                  //out.println( MsgUn );
                  out.close();
                }
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
          else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "New" ) ) )
          {
            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
            Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
            Vector lot_status_set= ( Vector ) session.getAttribute( "STATUS_SET" );
			Vector expdate_invengrps= ( Vector ) session.getAttribute( "EXP_ALLOWED_INVENGRP" );
			Vector lotstatus_invengrps= ( Vector ) session.getAttribute( "STATUS_SET_ALLOWED_INVENGRP" );
			Vector recqc_invengrps= ( Vector ) session.getAttribute( "RECQC_ALLOWED_INVENGRP" );
			Vector nonpickable_invengrps= ( Vector ) session.getAttribute( "NON_PICKABLE_INVENGRP" );
            Vector openorder_data=hubObj.getAllopenreceiptQC( branch_plant,User_Sort,SortresultsBy,invengrp,User_Search,searchlike,searchfor  );

            Hashtable sum= ( Hashtable ) ( openorder_data.elementAt( 0 ) );
            int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

            if ( 0 == count )
            {
              buildHeader( null,hub_list_set,branch_plant,User_Sort,"",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
              out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
              out.close();
              //clean up
              openorder_data=null;
              hub_list_set=null;
            }
            else
            {
              session.setAttribute( "DATA",openorder_data );
              buildHeader( openorder_data,hub_list_set,branch_plant,User_Sort,"",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
              buildDetails( openorder_data,User_Sort,branch_plant,lot_status_set,"",lotstatus_invengrps,recqc_invengrps,nonpickable_invengrps );
              out.close();
              //clean up
              openorder_data=null;
              hub_list_set=null;
            } //when there are open orders for hub
          }
          else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "Update" ) ) )
          {
            Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
            Vector synch_data=new Vector();

            SubUser_Action=request.getParameter( "SubUserAction" );
            if ( SubUser_Action == null )
              SubUser_Action="JSError2";

			Vector all_status_set_e= ( Vector ) session.getAttribute( "STATUS_SET" );
			Vector recqc_invengrps= ( Vector ) session.getAttribute( "RECQC_ALLOWED_INVENGRP" );

            if ( "1".equalsIgnoreCase( User_Sort ) )
            {
              synch_data=SynchServerData( request,retrn_data,all_status_set_e,recqc_invengrps );
            }
            else
            {
              synch_data=SynchServerNonChemData( request,retrn_data );
            }

            retrn_data=null;

            if ( SubUser_Action.equalsIgnoreCase( "JSError2" ) )
            {
              Hashtable all_bin_set_e= ( Hashtable ) session.getAttribute( "BIN_SET" );
              //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
              Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
			  Vector expdate_invengrps= ( Vector ) session.getAttribute( "EXP_ALLOWED_INVENGRP" );
			  Vector lotstatus_invengrps= ( Vector ) session.getAttribute( "STATUS_SET_ALLOWED_INVENGRP" );
			  Vector nonpickable_invengrps= ( Vector ) session.getAttribute( "NON_PICKABLE_INVENGRP" );

              session.setAttribute( "DATA",synch_data );
              buildHeader( synch_data,hub_list_set,branch_plant,User_Sort,"",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
              out.println( radian.web.HTMLHelpObj.printJavaScriptError() );
              buildDetails( synch_data,User_Sort,branch_plant,all_status_set_e,"",lotstatus_invengrps,recqc_invengrps,nonpickable_invengrps );
              out.close();
              //clean up
              synch_data=null;
              all_bin_set_e=null;
            }
            else if ( SubUser_Action.equalsIgnoreCase( "removereceipt" ) )
            {
              Vector new_data=removereceipt( remove_receipt_id,synch_data );
              session.setAttribute( "DATA",new_data );

              Hashtable all_bin_set_d= ( Hashtable ) session.getAttribute( "BIN_SET" );
              //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
              Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
			  Vector expdate_invengrps= ( Vector ) session.getAttribute( "EXP_ALLOWED_INVENGRP" );
			  Vector lotstatus_invengrps= ( Vector ) session.getAttribute( "STATUS_SET_ALLOWED_INVENGRP" );
			  //Vector recqc_invengrps= ( Vector ) session.getAttribute( "RECQC_ALLOWED_INVENGRP" );
			  Vector nonpickable_invengrps= ( Vector ) session.getAttribute( "NON_PICKABLE_INVENGRP" );

              buildHeader( new_data,hub_list_set,branch_plant,User_Sort,"",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
              buildDetails( new_data,User_Sort,branch_plant,all_status_set_e,"",lotstatus_invengrps,recqc_invengrps,nonpickable_invengrps );

              out.close();
              //clean up
              synch_data=null;
              new_data=null;
              all_bin_set_d=null;
              all_status_set_e=null;
            }
            else if ( SubUser_Action.equalsIgnoreCase( "UpdPage" ) || SubUser_Action.equalsIgnoreCase( "generatelabels" ) || SubUser_Action.equalsIgnoreCase( "generatelargelabels" ) )
            {
              Hashtable temp1= ( Hashtable ) session.getAttribute( "BIN_SET" );
              Vector temp2= ( Vector ) session.getAttribute( "STATUS_SET" );
              //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
              Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
			  Vector expdate_invengrps= ( Vector ) session.getAttribute( "EXP_ALLOWED_INVENGRP" );
			  Vector lotstatus_invengrps= ( Vector ) session.getAttribute( "STATUS_SET_ALLOWED_INVENGRP" );
			  //Vector recqc_invengrps= ( Vector ) session.getAttribute( "RECQC_ALLOWED_INVENGRP" );
			  Vector nonpickable_invengrps= ( Vector ) session.getAttribute( "NON_PICKABLE_INVENGRP" );

              session.setAttribute( "DATA",synch_data );
              Hashtable updateresults=UpdateDetails( synch_data,branch_plant,personnelid,User_Sort,CompanyID,SubUser_Action,recqc_invengrps );

              Boolean resultfromup= ( Boolean ) updateresults.get( "RESULT" );
              Vector errordata= ( Vector ) updateresults.get( "ERRORDATA" );
              Vector errMsgs=hubObj.getErrMsgs();

              session.setAttribute( "DATA",errordata );
			  if ("generatelargelabels".equalsIgnoreCase(SubUser_Action))
			  {
				session.setAttribute( "LARGE_LABEL_DATA",errordata );
   		      }
              boolean result=resultfromup.booleanValue();
              session.setAttribute( "LABEL_SEQUENCE_NUMBERS",receiptIdstoLabel );
              session.setAttribute( "LABEL_QUANTITYS",numbersoflabels );

              if ( false == result )
              {
                if ( "1".equalsIgnoreCase( User_Sort ) && receiptIdstoLabel.size() > 0 && (SubUser_Action.equalsIgnoreCase( "generatelabels" ) || "generatelargelabels".equalsIgnoreCase(SubUser_Action) ) )
                {
				  if ("generatelargelabels".equalsIgnoreCase(SubUser_Action))
				  {
					buildHeader( errordata,hub_list_set,branch_plant,User_Sort,"GENERATE_LARGE_LABELS",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
			      }
                  else
				  {
					buildHeader( errordata,hub_list_set,branch_plant,User_Sort,"GENERATE_LABELS",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
				  }
                }
                else
                {
                  buildHeader( errordata,hub_list_set,branch_plant,User_Sort,"",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
                }

                if ( true == noneToUpd )
                {
                  out.println( radian.web.HTMLHelpObj.printMessageinTable( "No Item was Choosen for Receiving QC" ) );
                  buildDetails( errordata,User_Sort,branch_plant,temp2,SubUser_Action,lotstatus_invengrps,recqc_invengrps,nonpickable_invengrps );
                }
                else
                {
                  //out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occurred.<BR>Please Check Data and try Again." ) );
				  out.println( radian.web.HTMLHelpObj.printHTMLPartialSuccess( errMsgs ) );
                  buildDetails( errordata,User_Sort,branch_plant,temp2,SubUser_Action,lotstatus_invengrps,recqc_invengrps,nonpickable_invengrps );
                }

                out.close();
              }
              else
              {
                //contains a list of a list of lot seq that were added to receiving table
                Vector vList=hubObj.getLotSeqList();
                session.setAttribute( "LABEL_SEQUENCE_NUMBERS",vList );

                if ( true == completeSuccess )
                {
                  if ( "1".equalsIgnoreCase( User_Sort ) && (SubUser_Action.equalsIgnoreCase( "generatelabels" ) || "generatelargelabels".equalsIgnoreCase(SubUser_Action) ))
                  {
					if ("generatelargelabels".equalsIgnoreCase(SubUser_Action))
					{
					  buildHeader( errordata,hub_list_set,branch_plant,User_Sort,"GENERATE_LARGE_LABELS",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
					}
					else
					{
					  buildHeader( errordata,hub_list_set,branch_plant,User_Sort,"GENERATE_LABELS",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
					}
				  }
                  else
                  {
                    buildHeader( errordata,hub_list_set,branch_plant,User_Sort,"",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
                  }
                  out.println( radian.web.HTMLHelpObj.printMessageinTable( "All Your Selections Were Successfully Updated" ) );
                  buildDetails( errordata,User_Sort,branch_plant,temp2,SubUser_Action,lotstatus_invengrps,recqc_invengrps,nonpickable_invengrps );
                  out.close();
                }
                else
                {
                  if ( "1".equalsIgnoreCase( User_Sort ) && (SubUser_Action.equalsIgnoreCase( "generatelabels" ) || "generatelargelabels".equalsIgnoreCase(SubUser_Action))  )
                  {
					if ("generatelargelabels".equalsIgnoreCase(SubUser_Action))
					{
					  buildHeader( errordata,hub_list_set,branch_plant,User_Sort,"GENERATE_LARGE_LABELS",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
					}
					else
					{
					  buildHeader( errordata,hub_list_set,branch_plant,User_Sort,"GENERATE_LABELS",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels);
					}
				  }
                  else
                  {
                    buildHeader( errordata,hub_list_set,branch_plant,User_Sort,"",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
                  }
                  out.println( radian.web.HTMLHelpObj.printHTMLPartialSuccess( errMsgs ) );
                  buildDetails( errordata,User_Sort,branch_plant,temp2,SubUser_Action,lotstatus_invengrps,recqc_invengrps,nonpickable_invengrps );
                  out.close();
                }
              }
              //clean up
              synch_data=null;
              temp1=null;
              temp2=null;
              //
            }
            else
            {
              out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
            }

          }
          else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "JSError1" ) ) )
          {
            Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
			Vector all_status_set_e= ( Vector ) session.getAttribute( "STATUS_SET" );
			Vector recqc_invengrps= ( Vector ) session.getAttribute( "RECQC_ALLOWED_INVENGRP" );
            Vector synch_data=SynchServerData( request,retrn_data,all_status_set_e,recqc_invengrps );
            Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
            retrn_data=null;

            Hashtable all_bin_set_e= ( Hashtable ) session.getAttribute( "BIN_SET" );

            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
			Vector expdate_invengrps= ( Vector ) session.getAttribute( "EXP_ALLOWED_INVENGRP" );
			Vector lotstatus_invengrps= ( Vector ) session.getAttribute( "STATUS_SET_ALLOWED_INVENGRP" );
			Vector nonpickable_invengrps= ( Vector ) session.getAttribute( "NON_PICKABLE_INVENGRP" );

            session.setAttribute( "DATA",synch_data );
            buildHeader( synch_data,hub_list_set,branch_plant,User_Sort,"",SortresultsBy,iniinvendata,invengrp,User_Search,searchlike,searchfor,session,printKitLabels );
            out.println( radian.web.HTMLHelpObj.printJavaScriptError() );
            buildDetails( synch_data,User_Sort,branch_plant,all_status_set_e,"",lotstatus_invengrps,recqc_invengrps,nonpickable_invengrps);
            out.close();
            //clean up
            synch_data=null;
            all_bin_set_e=null;
            all_status_set_e=null;

          }
          else
          {
            out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving QC",intcmIsApplication ) );
        }
        //clean up
        hubObj=null;

        return;
    }

    private Vector removereceipt( String removerecipt,Vector in_data )
    {
      Vector new_data=new Vector();
	  int lastItemNum=1;
	  boolean remrecipt=false;
	  Hashtable summary=new Hashtable();
	  summary= ( Hashtable ) in_data.elementAt( 0 );
	  //int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
	  int total= in_data.size() -1;
	  int i = 0;
	  int lineadded = 0;

	  try
	  {
		for ( int loop=0; loop < total; loop++ )
		{
		  boolean Samepoline=false;
		  i++;
		  Hashtable temp= ( Hashtable ) ( in_data.elementAt( i ) );
		  String receipt_id= ( temp.get( "RECEIPT_ID" ) == null ? "&nbsp;" : temp.get( "RECEIPT_ID" ).toString() );
		  String mngkitassingl=temp.get( "MANAGE_KITS_AS_SINGLE_UNIT" ).toString();

		  String Next_rec="";
		  if ( ! ( i == total ) )
		  {
			Hashtable hDNext=new Hashtable();
			hDNext= ( Hashtable ) in_data.elementAt( i + 1 );
			Next_rec=hDNext.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hDNext.get( "RECEIPT_ID" ).toString();
		  }
		  else
		  {
			Next_rec=" ";
		  }

		  if ( "N".equalsIgnoreCase( mngkitassingl ) && Next_rec.equalsIgnoreCase( receipt_id ) )
		  {
			Samepoline=true;
			if ( removerecipt.equalsIgnoreCase( receipt_id ) )
			{
			  remrecipt=true;
			}

			lastItemNum++;
		  }
		  else
		  {
			lastItemNum=1;
		  }

		  if ( ! ( remrecipt ) )
		  {
			new_data.addElement( temp );
			lineadded++;
		  } //end of last if

		  if ( !Samepoline )
		  {
			remrecipt=false;
		  }

		} //end of for
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving QC",intcmIsApplication ) );
	  }

	  Hashtable recsum=new Hashtable();
	  recsum.put( "TOTAL_NUMBER",new Integer( lineadded ) );
	  new_data.setElementAt( recsum,0 );
	  new_data.trimToSize();

	  return new_data;
	}

    private Vector SynchServerNonChemData( HttpServletRequest request,Vector in_data )
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

          hD.remove( "DOSTATUSUPDATE" );
          hD.put( "DOSTATUSUPDATE","Yes" );

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
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
      }
      return new_data;
    }

    private Vector SynchServerData( HttpServletRequest request,Vector in_data,Vector all_status_set,Vector allowforqc)
    {
      Vector new_data=new Vector();
      Hashtable sum= ( Hashtable ) ( in_data.elementAt( 0 ) );
      //int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
	  int count= in_data.size() -1;
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

          String label_qty="";
          label_qty=BothHelpObjs.makeBlankFromNull( request.getParameter( "label_qty" + i ) );
          hD.remove( "NO_OF_LABELS" );
          hD.put( "NO_OF_LABELS",label_qty );

		  String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString() );
		  if (allowforqc.contains(invengrp))
          {
            hD.remove( "DOSTATUSUPDATE" );
            hD.put( "DOSTATUSUPDATE","Yes" );

            String rootcause="";
            rootcause=request.getParameter( ( "rootcause" + i ) );
            if ( rootcause == null )
              rootcause="";

            String rootcausecompany="";
            rootcausecompany=request.getParameter( ( "rootcausecompany" + i ) );
            if ( rootcausecompany == null )
              rootcausecompany="";

            String rootcausenotes="";
            rootcausenotes=request.getParameter( ( "rootcausenotes" + i ) );
            if ( rootcausenotes == null )
              rootcausenotes="";

            hD.remove( "TERMINAL_ROOT_CAUSE" );
            hD.put( "TERMINAL_ROOT_CAUSE",rootcause );

            hD.remove( "TERMINAL_COMPANY" );
            hD.put( "TERMINAL_COMPANY",rootcausecompany );

            hD.remove( "TERMINAL_NOTES" );
            hD.put( "TERMINAL_NOTES",rootcausenotes );

            hD.remove( "DOSTATUSUPDATE" );
            hD.put( "DOSTATUSUPDATE","Yes" );

            String status_id="";
            try
            {
              status_id=BothHelpObjs.makeBlankFromNull( request.getParameter( ( "selectElementStatus" + i ) ));
            }
            catch ( Exception e )
            {
              status_id="";
            }
            if ( !status_id.equalsIgnoreCase( hD.get( "LOT_STATUS" ).toString() ) && status_id.length() > 0)
            {
              hD.remove( "LOT_STATUS" );
              hD.put( "LOT_STATUS",status_id );

              hD.remove( "DOSTATUSUPDATE" );
              hD.put( "DOSTATUSUPDATE","Yes" );

			String qualitycntitem= ( hD.get( "QUALITY_CONTROL_ITEM" ) == null ? " " : hD.get( "QUALITY_CONTROL_ITEM" ).toString() );
			if ( "Y".equalsIgnoreCase( qualitycntitem ) )
			{
			  for ( int h=0; h < all_status_set.size(); h++ )
			  {
				Hashtable data1= ( Hashtable ) all_status_set.elementAt( h );
				Enumeration E;
				for ( E=data1.keys(); E.hasMoreElements(); )
				{
				  String key= ( String ) E.nextElement();
				  String keyvalue=data1.get( key ).toString();

				  if ( status_id.equalsIgnoreCase( key ) && "Y".equalsIgnoreCase( keyvalue ) )
				  {
					hD.remove( "CERT_UPDATE" );
					hD.put( "CERT_UPDATE","Yes" );
				  }
				}
			  }
			}
            }

			String mfg_lot="";
			mfg_lot=BothHelpObjs.makeBlankFromNull( request.getParameter( "mfg_lot" + i ) );
			hD.remove( "MFG_LOT" );
			hD.put( "MFG_LOT",mfg_lot );

            String date_mfgd="";
            date_mfgd=BothHelpObjs.makeBlankFromNull( request.getParameter( "date_mfgd" + i ) );
            if ( !date_mfgd.equalsIgnoreCase( hD.get( "DOM" ).toString() ) )
            {
              hD.remove( "DOM" );
              hD.put( "DOM",date_mfgd );

              hD.remove( "DOSTATUSUPDATE" );
              hD.put( "DOSTATUSUPDATE","Yes" );
            }

            String date_recieved="";
            date_recieved=BothHelpObjs.makeBlankFromNull( request.getParameter( "date_recieved" + i ) );
            if ( !date_recieved.equalsIgnoreCase( hD.get( "DATE_RECIEVED" ).toString() ) )
            {
              hD.remove( "DATE_RECIEVED" );
              hD.put( "DATE_RECIEVED",date_recieved );

              hD.remove( "DOSTATUSUPDATE" );
              hD.put( "DOSTATUSUPDATE","Yes" );
            }

            String actshipDate="";
            actshipDate=BothHelpObjs.makeBlankFromNull( request.getParameter( "act_suppship_date" + i ) );
            hD.remove( "ACTUAL_SHIP_DATE" );
            hD.put( "ACTUAL_SHIP_DATE",actshipDate );

            String expiry_date="";
            expiry_date=BothHelpObjs.makeBlankFromNull( request.getParameter( "expiry_date" + i ) );
            if ( !expiry_date.equalsIgnoreCase( hD.get( "EXPIRE_DATE" ).toString() ) )
            {
              if ( "Indefinite".equalsIgnoreCase( expiry_date ) )
              {
                expiry_date="01/01/3000";
              }
              hD.remove( "EXPIRE_DATE" );
              hD.put( "EXPIRE_DATE",expiry_date );

              hD.remove( "DOSTATUSUPDATE" );
              hD.put( "DOSTATUSUPDATE","Yes" );
            }

            String ship_date="";
            ship_date=BothHelpObjs.makeBlankFromNull( request.getParameter( "ship_date" + i ) );
            if ( !ship_date.equalsIgnoreCase( hD.get( "DOS" ).toString() ) )
            {
              hD.remove( "DOS" );
              hD.put( "DOS",ship_date );

              hD.remove( "DOSTATUSUPDATE" );
              hD.put( "DOSTATUSUPDATE","Yes" );
            }

		  String recnotes="";
		  recnotes=BothHelpObjs.makeBlankFromNull( request.getParameter( "recnotes" + i ) );
		  hD.remove( "NOTES" );
		  hD.put( "NOTES",recnotes );

		  String deliveryTicket="";
		  deliveryTicket=BothHelpObjs.makeBlankFromNull( request.getParameter( "deliveryTicket" + i ) );
		  hD.remove( "DELIVERY_TICKET" );
		  hD.put( "DELIVERY_TICKET",deliveryTicket );
          }
          new_data.addElement( hD );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
      }
      return new_data;
    }

    private Hashtable UpdateDetails( Vector udatedata,String BranchPlant,String logonid,String usrcategory,String CompanyID,String subuseraction,Vector allowedingrps )
    {
      boolean result=false;
      receiptIdstoLabel=new Vector();
      numbersoflabels=new Vector();

      Hashtable updateresult=new Hashtable();
      Vector finalDataV=new Vector();

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) udatedata.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        finalDataV.addElement( summary );
		int lastItemNum=1;
        boolean one_success=false;
		boolean callreccomp=false;

        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) udatedata.elementAt( i );
          String Line_Check="";
          String dostatusupdate="";

          Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
          dostatusupdate= ( hD.get( "DOSTATUSUPDATE" ) == null ? " " : hD.get( "DOSTATUSUPDATE" ).toString() );
		  String mngkitassingl = hD.get("MANAGE_KITS_AS_SINGLE_UNIT").toString();
		  String componentid = hD.get("COMPONENT_ID").toString();
		  String receipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
		  String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString() );

		  String Next_rec="";
		  if ( ! ( i == total ) )
		  {
			Hashtable hDNext=new Hashtable();
			hDNext= ( Hashtable ) udatedata.elementAt( i + 1 );
			Next_rec=hDNext.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hDNext.get( "RECEIPT_ID" ).toString();
		  }
		  else
		  {
			Next_rec=" ";
		  }

		  boolean Samepoline=false;
		  boolean FirstRow=false;
		  if ( "N".equalsIgnoreCase(mngkitassingl) && Next_rec.equalsIgnoreCase(receipt_id) )
		   {
			Samepoline=true;
			if ( "yes".equalsIgnoreCase( Line_Check ) && ! ( "generatelabels".equalsIgnoreCase( subuseraction ) || "generatelargelabels".equalsIgnoreCase( subuseraction ) ) )
			{
			  callreccomp=true;
			}

			lastItemNum++;
		  }
		  else
		  {
			lastItemNum=1;
		  }

		  if ( Samepoline )
		  {
			if ( lastItemNum == 2 )
			{
			  FirstRow=true;
			}
		  }
		  else if ("Y".equalsIgnoreCase(mngkitassingl))
		  {
			FirstRow=true;
		  }

          if ( "yes".equalsIgnoreCase( Line_Check ) )
          {
            receiptIdstoLabel.addElement( hD.get( "RECEIPT_ID" ).toString() );
            numbersoflabels.addElement( hD.get( "NO_OF_LABELS" ).toString() );
          }

          if ( allowedingrps.contains(invengrp) && ( ("yes".equalsIgnoreCase( Line_Check ) && "yes".equalsIgnoreCase( dostatusupdate ) && ! ( "generatelabels".equalsIgnoreCase( subuseraction ) || "generatelargelabels".equalsIgnoreCase( subuseraction ) ) ) || callreccomp ) )
          {
            noneToUpd=false;
			boolean line_result=false;

			if ( FirstRow )
			{
			  line_result=hubObj.UpdateAppStatus( hD,BranchPlant,logonid,usrcategory,CompanyID ); // update database
			}

		   if ( "N".equalsIgnoreCase(mngkitassingl) && callreccomp)
		   {
			 reclog.info("Calling p_receipt_component from REC QC for COMPONENT_ID "+componentid+" RECEIPT_ID "+receipt_id+" Personnel ID  "+logonid+"");
			 line_result = radian.web.HTMLHelpObj.insreceiptcomp(db,hD,receipt_id,logonid);
		   }

            if ( !line_result )
            {
              completeSuccess=false;
              hD.remove( "USERCHK" );
              hD.put( "USERCHK"," " );

              hD.remove( "LINE_STATUS" );
              hD.put( "LINE_STATUS","NO" );

              finalDataV.addElement( hD );
            }
			else
            {
              hD.remove( "LINE_STATUS" );
              hD.put( "LINE_STATUS","YES" );

              one_success=true;
              finalDataV.addElement( hD );
            }
          }
          else
          {
            finalDataV.addElement( hD );
          }

		if ( !Samepoline )
		{
		  callreccomp=false;
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
      updateresult.put( "ERRORDATA",finalDataV );

      return updateresult;
    }

    private void buildtermiallot( Vector commpanyids,String termlotchoosen,String linenumberchos )
       {
         //StringBuffer Msg=new StringBuffer();

         Vector rootcause =radian.web.HTMLHelpObj.createRootCaauselist( db, termlotchoosen );

         try
         {
           out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Root Cause","receivingjs.js",intcmIsApplication ) );
           out.println( "</HEAD>  \n" );
           out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>Root Cause</B>\n" ) );
           out.println( "<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Receiving_Servlet + "\">\n" );
           //start table #1
           out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
           out.println( "<TR VALIGN=\"MIDDLE\">\n" );
           out.println( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
           out.println( "<B>Root Cause:</B>&nbsp;\n" );
           out.println( "</TD>\n" );
           out.println( "<TD HEIGHT=45 WIDTH=\"20%\" CLASS=\"announce\">\n" );
           out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"rootcause\" size=\"1\">\n" );
           out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
           out.println( radian.web.HTMLHelpObj.getDropDown( rootcause,"" ) );
           out.println( "</SELECT>\n" );
           out.println( "</TD>\n" );
           out.println( "</TR>" );

           out.println( "<TR VALIGN=\"MIDDLE\">\n" );
           out.println( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
           out.println( "<B>Responsible Company:</B>&nbsp;\n" );
           out.println( "</TD>\n" );
           out.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">\n" );
           out.println( "<SELECT CLASS=\"HEADER\" NAME=\"rootcausecompany\" size=\"1\">\n" );
           out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
           out.println( radian.web.HTMLHelpObj.getDropDown( commpanyids,"" ) );
           out.println( "</SELECT>\n" );
           out.println( "</TD>\n" );
           out.println( "</TR>" );

           out.println( "<TR VALIGN=\"MIDDLE\">\n" );
           out.println( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
           out.println( "<B>Justification:</B>&nbsp;\n" );
           out.println( "</TD>\n" );
           out.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">\n" );
           out.println("<TEXTAREA name=\"rootcausenotes\" rows=\"4\" cols=\"50\"></TEXTAREA></TD>\n");
           out.println( "</TD>\n" );
           out.println( "</TR>" );
           out.println( "</TABLE>\n" );

           out.println( "<input type=\"hidden\" name=\"linenumberchoos\" value=\"" + linenumberchos + "\">\n" );

           out.println( "<BR>\n" );
           out.println( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Ok\" OnClick=\"sendtervalues()\" NAME=\"sendterv\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
           out.println( "<INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Cancel\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n" );
         }
         catch ( Exception e )
         {
           e.printStackTrace();
           out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
         }
         return;
       }

	   private void buildHeader( Vector headerdata,Hashtable hub_list_set,String hub_branch_id,String sortby,String buildlabels,String SortresultsBy,
								   Hashtable initialinvData,String selinvengrp,String search_text,String searchLike,String searchfor,HttpSession session,String printKitLabels )
    {
      //StringBuffer Msg=new StringBuffer();
      String SelectedHubName="";
      try
      {
        out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Receiving QC","receivingjs.js",intcmIsApplication ) );
        out.println( "<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
        out.println("<SCRIPT SRC=\"/clientscripts/invengrpdropdown.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
        out.println( "</HEAD>  \n" );

		if ( "GENERATE_LARGE_LABELS".equalsIgnoreCase( buildlabels ) )
		{
		  out.println( "<BODY onLoad=\"dolargelabelPopup()\">\n" );
		}
        else if ( "GENERATE_LABELS".equalsIgnoreCase( buildlabels ) )
        {
          out.println( "<BODY onLoad=\"doQCPopup()\">\n" );
        }
        else
        {
          out.println( "<BODY>\n" );
        }

        out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
        out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></center>\n" );
        out.println( "</DIV>\n" );
        out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

        out.println( radian.web.HTMLHelpObj.PrintTitleBar( "<B>Receiving QC</B>\n" ) );
		Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		if (hub_list.size() < 1)
		{
		  out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
		  return;
		}
		out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
		out.println(radian.web.poHelpObj.createhubinvgrjs(initialinvData));
		out.println("// -->\n </SCRIPT>\n");

        out.println( "<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Receiving_Servlet + "\">\n" );
        //start table #1
        out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        out.println( "<TR VALIGN=\"MIDDLE\">\n" );

        //Category
        out.println( "<TD HEIGHT=\"35\" WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Category:</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
        out.println( "<SELECT CLASS=\"HEADER\" NAME=\"SortBy\" size=\"1\">\n" );
        out.println( "<option " + ( sortby.equalsIgnoreCase( "1" ) ? "selected" : "" ) + " value=\"1\">Chemicals</option>\n" );
        out.println( "<option " + ( sortby.equalsIgnoreCase( "2" ) ? "selected" : "" ) + " value=\"2\">Non-Chemicals</option>\n" );
        out.println( "</SELECT>\n" );
        out.println( "</TD>\n" );

		// Search Text
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<B>Search:</B>&nbsp;\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"right\" CLASS=\"announce\">\n" );
		out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" size=\"1\">\n" );
		Hashtable searchthese=new Hashtable();
		searchthese.put( "PO","a.RADIAN_PO" );
		searchthese.put( "Item Id","a.ITEM_ID" );
		searchthese.put( "Item Desc","a.LINE_DESC" );
		out.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( searchthese.entrySet(),searchLike ) );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );

		out.println( "<TD WIDTH=\"5%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
		out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"searchfor\" size=\"1\">\n" );
		Hashtable sortresult=new Hashtable();
		sortresult.put( "contains","Like" );
		sortresult.put( "is","Equals" );
		out.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( sortresult.entrySet(),searchfor ) );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );

		out.println( "<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		out.println( "<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\"" + search_text + "\" size=\"20\">\n" );
		out.println( "</TD>\n" );

        //Search
        out.println( "<TD HEIGHT=\"35\" WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
        out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n" );
        out.println( "</TD>\n" );

        //Generate Labels
        out.println( "<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
        if ( sortby.equalsIgnoreCase( "2" ) || !this.getupdateStatus() )
        {
          out.println( "&nbsp;\n" );
        }
        else
        {
          out.println( "1.&nbsp;<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Container Labels\" onclick= \"generatelabels(this)\" NAME=\"UpdateButton\">&nbsp;\n" );
          out.println( "<INPUT TYPE=\"hidden\" NAME=\"generate_labels\" VALUE=\"yes\">&nbsp;\n" );
        }
        out.println( "</TD>\n</TR>" );

        out.println( "<TR VALIGN=\"MIDDLE\">\n" );
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<B>Hub:</B>&nbsp;\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"10%\" HEIGHT=\"35\">\n" );
		out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" OnChange=\"hubchanged(document.receiving.HubName)\" size=\"1\">\n" );

		//Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
		if ( hub_branch_id.trim().length() == 0 )
		{
		  hub_branch_id=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
		}
		out.println(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),hub_branch_id,hub_list));
		SelectedHubName = ( String ) ( hub_list.get( hub_branch_id ) );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );

        out.println( "<TD ALIGN=\"RIGHT\" COLSPAN=\"2\">&nbsp;</TD>\n" );

		out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + ( printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		out.println( "&nbsp;Skip Kit/Case Qty Labels\n" );
		out.println( "</TD>\n" );

		out.println( "<TD ALIGN=\"RIGHT\">&nbsp;</TD>\n" );

        //Label Options
        out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
        if ( sortby.equalsIgnoreCase( "2" ) || !this.getupdateStatus() )
        {
          out.println( "&nbsp;\n" );
        }
        else
        {
          out.println( "&nbsp;&nbsp;<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('31')\" value=\"31\" checked>3''/1''&nbsp;\n" );
          out.println( "<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('811')\" value=\"811\">8.5''/11''\n" );
        }
        out.println( "</TD>\n" );
        out.println( "</TR>\n" );

		out.println( "<TR VALIGN=\"MIDDLE\">\n" );
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<B>Inv Grp:</B>&nbsp;</TD>\n" );
		out.println( "<TD WIDTH=\"10%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n" );

		Hashtable fh = (Hashtable)initialinvData.get(hub_branch_id);
		Vector invidV = ( Vector ) fh.get( "INVENTORY_GROUP" );
		Vector invidName = ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );

		for (int i=0; i < invidV.size(); i++ )
		{
		  String preSelect="";
		  String wacid= ( String ) invidV.elementAt( i );
		  String invgName= ( String ) invidName.elementAt( i );

		  if ( selinvengrp.equalsIgnoreCase( wacid ) )
		  {
			preSelect="selected";
		  }
		  out.println( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + "</option>\n" );
		}
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"35\" WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<B>Sort By:</B>&nbsp;\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" COLSPAN=\"2\">\n" );
		out.println( "<SELECT CLASS=\"HEADER\" NAME=\"SortresultsBy\" size=\"1\">\n" );

		sortresult=new Hashtable();
		sortresult.put( "PO","a.RADIAN_PO" );
		sortresult.put( "Mfg Lot","a.MFG_LOT" );
		sortresult.put( "Item Id","a.ITEM_ID" );
		sortresult.put( "Bin","a.BIN" );
		sortresult.put( "Receipt ID","a.RECEIPT_ID" );

		out.println( radian.web.HTMLHelpObj.getDropDown( sortresult,SortresultsBy ) );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );

	    //Add new Bin
		out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">&nbsp;&nbsp;&nbsp;\n" );
		String hubname=radian.web.HTMLHelpObj.gethubNamefromBP( hub_list_set,hub_branch_id );
		PersonnelBean personnelBean=session.getAttribute( "personnelBean" ) == null ? null :( PersonnelBean ) session.getAttribute( "personnelBean" );
		if ( personnelBean.getPermissionBean().hasFacilityPermission( "addNewBin",hubname,null ) )
		{
		  out.println( "<INPUT TYPE=\"BUTTON\" ID=\"genbutton1\" VALUE=\"Add New Bin\" NAME=\"SearchButton\" onclick= \"javascript:addnewBin()\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
		}
		/*if ( this.getupdateStatus() && sortby.equalsIgnoreCase( "1" ) )
		{
		  out.println( "<INPUT TYPE=\"BUTTON\" ID=\"genbutton1\" VALUE=\"Add New Bin\" NAME=\"SearchButton\" onclick= \"javascript:addnewBin()\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
		}*/
		else
		{
		  out.println( "&nbsp;\n" );
		}
		out.println( "</TD>\n" );

		//out.println( "<TD ALIGN=\"RIGHT\" WIDTH=\"5%\">&nbsp;</TD>\n" );
		//Generate Large Labels
		out.println( "<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		if ( sortby.equalsIgnoreCase( "2" ) || !this.getupdateStatus() )
		{
		  out.println( "&nbsp;\n" );
		}
		else
		{
		  out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Receiving Label\" onclick= \"generatelargelabels()\" NAME=\"UpdateButton\">&nbsp;\n" );
		  out.println( "<INPUT TYPE=\"hidden\" NAME=\"generateLargeLabels\" VALUE=\"yes\">&nbsp;\n" );
		}
		out.println( "</TD>\n" );


		//Confirm
		out.println( "<TD HEIGHT=\"35\" VALIGN=\"MIDDLE\" WIDTH=\"5%\" CLASS=\"announce\">\n" );
		if ( this.getupdateStatus() )
		{
		  if ( sortby.equalsIgnoreCase( "2" ) )
		  {
			out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Confirm\" onclick= \"return update(this)\" NAME=\"UpdateButton\">&nbsp;\n" );
		  }
		  else
		  {
			out.println( "2.&nbsp;<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Confirm\" onclick= \"return update(this)\" NAME=\"UpdateButton\">&nbsp;\n" );
		  }
		}
		else
		{
		  out.println( "&nbsp;\n" );
		}
		out.println( "</TD>\n" );
		out.println( "</TR></TABLE>\n" );

        int total=0;
        if ( null == headerdata )
        {
          total=0;
        }
        else
        {
          Hashtable summary=new Hashtable();
          summary= ( Hashtable ) headerdata.elementAt( 0 );
          total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        }

        out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        out.println( "<tr><td>" );
        out.println( "<input type=\"hidden\" name=\"Total_number\" value=\"" + total + "\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"DuplLineNumber\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Item_Id\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Bin_Id\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"remove_receipt_id\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"HubRef\" VALUE=\"" + hub_branch_id + "\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"HubFacCount\" VALUE=\"" + radian.web.HTMLHelpObj.numOfClients( db,SelectedHubName ) + "\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"paperSize1\" VALUE=\"" + paper_size + "\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"Paper\" VALUE=\"" + paper_size + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );

        out.println( "</TD></tr>" );
        out.println( "</table>\n" );
		//out.println( "<P ALIGN=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showreceivingqclegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT></P>\n" );

        sortresult = null;
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
      }
      return;
    }

  private void buildDetails( Vector detailsdata,String sortby,String HubNo,Vector lot_status,String SubuserAction,Vector allowforlotstatus,Vector allowforqc,Vector nonpickable )
  {
    //StringBuffer Msg=new StringBuffer();
    try
    {
      Hashtable summary=new Hashtable();
      summary= ( Hashtable ) detailsdata.elementAt( 0 );
      //int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
	  int total= detailsdata.size() -1;
      //start table #3

	  out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
	  out.println("var lotStatus = new Array();\n");
	  for ( int i=0; i < lot_status.size(); i++ )
	  {
		Hashtable data1= ( Hashtable ) lot_status.elementAt( i );
		Enumeration E;
		for ( E=data1.keys(); E.hasMoreElements(); )
		{
		  String key= ( String ) E.nextElement();
		  String keyvalue=data1.get( key ).toString();

		  if ("Y".equalsIgnoreCase(keyvalue))
		  {
			out.println("lotStatus[\"" + key + "\"] = new Array(\"" + key + "\");\n");
		  }
		}
	  }
	  out.println("// -->\n </SCRIPT>\n");
	  out.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showreceivingpagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n" );
      out.println( "<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveupmore\">\n" );

      String packaging="";
      String checkednon="";
      int i=0; //used for detail lines
      int lineavdded=0;
	  int lastItemNum=1;
	  int ItemIdCount=0;
	  String Color="CLASS=\"white";

      for ( int loop=0; loop < total; loop++ )
      {
        i++;
        boolean createHdr=false;

        if ( loop % 10 == 0 && lastItemNum == 1)
        {
          createHdr=true;
        }

        if ( createHdr )
        {
          out.println( "<tr align=\"center\">\n" );
          if ( sortby.equalsIgnoreCase( "2" ) )
          {
            out.println( "<TH width=\"5%\" height=\"38\">PO<BR>(Inven Grp)</TH>\n" );
          }
          else
          {
            out.println( "<TH width=\"5%\" height=\"38\">PO</TH>\n" );
          }
          out.println( "<TH width=\"5%\" height=\"38\">PO Line</TH>\n" );
		  if ( sortby.equalsIgnoreCase( "1" ) )
		  {
			out.println( "<TH width=\"8%\" height=\"38\">Item Id</TH>\n" );
		  }
          out.println( "<TH width=\"25%\"  height=\"38\">Line Desc</TH>\n" );
		  out.println( "<TH width=\"5%\" height=\"38\">OK</TH>\n" );

          if ( sortby.equalsIgnoreCase( "2" ) )
          {
            out.println( "<TH width=\"8%\" height=\"38\">Supplier Ref #</TH>\n" );
            out.println( "<TH width=\"8%\" height=\"38\">End Date</TH>\n" );
            out.println( "<TH width=\"8%\" height=\"38\">DOR</TH>\n" );
          }
          else
          {
            out.println( "<TH width=\"13%\" height=\"38\">Mfg Lot<BR>Orig Mfg Lot</TH>\n" );
            out.println( "<TH width=\"8%\" height=\"38\">Lot Status</TH>\n" );
            out.println( "<TH width=\"8%\"  height=\"38\">Act Supp Ship Date mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"8%\" height=\"38\">DOR mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"8%\" height=\"38\">DOM mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"8%\" height=\"38\">Manufacturer DOS mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"8%\" height=\"38\">Exp Date mm/dd/yyyy</TH>\n" );
          }

          out.println( "<TH width=\"8%\" height=\"38\">Bin</TH>\n" );
          out.println( "<TH width=\"13%\" height=\"38\">Receipt<BR>Trans Receipt</TH>\n" );
          out.println( "<TH width=\"8%\" height=\"38\">Quantity</TH>\n" );
          if ( sortby.equalsIgnoreCase( "2" ) )
          {
            out.println( "<TH width=\"8%\" height=\"38\">Packaging</TH>\n" );
          }
          else
          {
          out.println( "<TH WIDTH=\"5%\"># Labels</TH>\n" );
          }

		  out.println( "<TH WIDTH=\"5%\">Notes</TH>\n" );
		  out.println( "<TH width=\"5%\"  height=\"38\">Delivery Ticket</TH>\n" );
          out.println( "<TH WIDTH=\"5%\">&nbsp;</TH>\n" );

          out.println( "</tr>\n" );
        }

        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) detailsdata.elementAt( i );
		//reclog.info("total:   "+total+"  i:    "+i+"");
		String Next_rec="";
		if ( ! ( i == total ) )
		{
		  Hashtable hDNext=new Hashtable();
		  hDNext= ( Hashtable ) detailsdata.elementAt( i + 1 );
		  Next_rec=hDNext.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hDNext.get( "RECEIPT_ID" ).toString();
		}
		else
		{
		  Next_rec=" ";
		}

        // get main information
        String Purchase_order= ( hD.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER" ).toString() );
        String PO_Line= ( hD.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER_LINE" ).toString() );
        //String dor_dt= ( hD.get( "DATE_RECIEVED" ) == null ? "" : hD.get( "DATE_RECIEVED" ).toString() );
        String Item_id= ( hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString() );
        String line_desc= ( hD.get( "LINE_DESC" ) == null ? "" : hD.get( "LINE_DESC" ).toString() );
        String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "" : hD.get( "MFG_LOT" ).toString() );
        String Date_recieved= ( hD.get( "DATE_RECIEVED" ) == null ? "&nbsp;" : hD.get( "DATE_RECIEVED" ).toString() );
        String bin= ( hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString() );
        String receipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
        String quantity= ( hD.get( "QUANTITY_RECEIVED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_RECEIVED" ).toString() );
        String lotstatus= ( hD.get( "LOT_STATUS" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS" ).toString() );
        String dom= ( hD.get( "DOM" ) == null ? "" : hD.get( "DOM" ).toString() );
        String dos= ( hD.get( "DOS" ) == null ? "" : hD.get( "DOS" ).toString() );
        String expdate= ( hD.get( "EXPIRE_DATE" ) == null ? "" : hD.get( "EXPIRE_DATE" ).toString() );
        String indefshelflie= ( hD.get( "INDEFINITE_SHELF_LIFE" ) == null ? " " : hD.get( "INDEFINITE_SHELF_LIFE" ).toString() );
        String actshipDate= ( hD.get( "ACTUAL_SHIP_DATE" ) == null ? " " : hD.get( "ACTUAL_SHIP_DATE" ).toString() );
        String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
        if ( Line_Check.equalsIgnoreCase( "yes" ) )
        {
          checkednon="checked";
        }
        else
        {
          checkednon="";
        }

        if ( sortby.equalsIgnoreCase( "2" ) )
        {
          packaging= ( hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString() );
        }
        String LINE_STATUS=BothHelpObjs.makeBlankFromNull( hD.get( "LINE_STATUS" ).toString() );
        String tranreceiptid=BothHelpObjs.makeBlankFromNull( hD.get( "TRANSFER_RECEIPT_ID" ).toString() );
        String origmfglot=BothHelpObjs.makeBlankFromNull( hD.get( "ORIG_MFG_LOT" ).toString() );

        String rootcause=BothHelpObjs.makeBlankFromNull( hD.get( "TERMINAL_ROOT_CAUSE" ).toString() );
        String rootcausecompany=BothHelpObjs.makeBlankFromNull( hD.get( "TERMINAL_COMPANY" ).toString() );
        String rootcausenotes=BothHelpObjs.makeBlankFromNull( hD.get( "TERMINAL_NOTES" ).toString() );
        String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString() );
		String oktoqc = ( hD.get( "QC_OK" ) == null ? " " : hD.get( "QC_OK" ).toString() );
		String doctype = ( hD.get( "DOC_TYPE" ) == null ? " " : hD.get( "DOC_TYPE" ).toString() );
		String transreqid = ( hD.get( "TRANSFER_REQUEST_ID" ) == null ? " " : hD.get( "TRANSFER_REQUEST_ID" ).toString() );
		String qualitycntitem = ( hD.get( "QUALITY_CONTROL_ITEM" ) == null ? " " : hD.get( "QUALITY_CONTROL_ITEM" ).toString() );

		String mngkitassingl = hD.get("MANAGE_KITS_AS_SINGLE_UNIT").toString();
		String componentid = hD.get("COMPONENT_ID").toString();
		String compmatdesc = hD.get("MATERIAL_DESC").toString();
		String invgrpname = hD.get("INVENTORY_GROUP_NAME").toString();
		String numofkits = hD.get("NUMBER_OF_KITS").toString();
		String recqcnotes = hD.get("NOTES").toString();
		String criticalpo= ( hD.get( "CRITICAL" ) == null ? " " : hD.get( "CRITICAL" ).toString() );
		String returnPrNumber= hD.get("RETURN_PR_NUMBER").toString();
		String returnLineItem= hD.get("RETURN_LINE_ITEM").toString();
		String deliveryTicket = hD.get("DELIVERY_TICKET").toString();

		if ( "S".equalsIgnoreCase( criticalpo ) )
		{
		  Color="CLASS=\"pink";
		}

		if ( "Y".equalsIgnoreCase( criticalpo ) )
		{
		  Color="CLASS=\"red";
		}

        if ( "Yes".equalsIgnoreCase( ( String ) hD.get( "EXCESS_MATERIAL" ) ) )
        {
          Color="CLASS=\"orange";
        }

        String chkbox_value="no";
        if ( checkednon.equalsIgnoreCase( "checked" ) )
        {
          chkbox_value="yes";
        }

        if ( SubuserAction.equalsIgnoreCase( "UpdPage" ) && "NO".equalsIgnoreCase( LINE_STATUS ) )
        {
          Color="CLASS=\"error";
        }

		boolean recqcallowed = false;
		if (allowforqc.contains(invengrp))
		{
		  recqcallowed = true;
		}

		if ( !this.getupdateStatus() )
		{
		  recqcallowed = false;
		}

		boolean Samepoline=false;
		boolean FirstRow=false;
		if ( "N".equalsIgnoreCase(mngkitassingl) && Next_rec.equalsIgnoreCase(receipt_id) )
		 {
		  Samepoline=true;
		  lastItemNum++;
		}
		else
		{
		  ItemIdCount++;
		  lastItemNum=1;
		}

		if ( Samepoline )
		{
		  if ( lastItemNum == 2 )
		  {
			FirstRow=true;
		  }
		}
		else if ("Y".equalsIgnoreCase(mngkitassingl))
		{
		  FirstRow=true;
		  numofkits = "1";
		}

	 if ( !this.getupdateStatus() ||  !(recqcallowed) )
	 {
	  if ( !Mfg_lot.equalsIgnoreCase( origmfglot ) && ( origmfglot.trim().length() > 0 ) )
	  {
		String temp="<A HREF=\"javascript:showPreviousTransactions('" + origmfglot + "'," + HubNo + ")\">" + origmfglot + "</A>\n";
		origmfglot=temp;
	  }

	  out.println( "<tr align=\"center\">\n" );
	  if ( FirstRow )
	  {
		out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" );
		if ( "IT".equalsIgnoreCase( doctype ))
		{
		  out.println( "<A HREF=\"javascript:showrecforinvtransfr(" + transreqid + "," + HubNo + ")\">TR " +transreqid+ "</A>" );
		}
		else if ("IA".equalsIgnoreCase( doctype ))
		{
		  out.println( "MR " + returnPrNumber + "-" + returnLineItem + "\n" );
		}
		else
		{
		  out.println( "" + Purchase_order + "" );
		  if ( sortby.equalsIgnoreCase( "2" ) )
		  {
			out.println( "<BR>(" + invgrpname + ")</td>\n" );
		  }
		}
		out.println( "</td>\n" );


		out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" );
		if ( sortby.equalsIgnoreCase( "2" ) )
		{
		  out.println( "<A HREF=\"javascript:showPreviouspolineqc(" + PO_Line + "," + Purchase_order + "," + HubNo + ")\">" + PO_Line + "</A></td>\n" );
		}
		else if ( !("IT".equalsIgnoreCase( doctype ) || "IA".equalsIgnoreCase( doctype )) )
		{
		  out.println( PO_Line );
		}
		out.println( "</A></td>\n" );

		if ( sortby.equalsIgnoreCase( "1" ) )
		{
		  out.println( "<td " + Color + "\" width=\"8%\" height=\"38\" rowspan=\""+numofkits+"\">\n" );
		  out.println( "<A HREF=\"javascript:showPreviousrecqc(" + Item_id + "," + HubNo + ")\">" + Item_id + "</A><BR>("+invgrpname+")</td>\n" );
		}
	  }

	  if ( "N".equalsIgnoreCase( mngkitassingl ) )
	  {
		out.println( "<td " + Color + "l\" width=\"25%\" height=\"38\" >" + compmatdesc + "</td>\n" );
	  }
	  else
	  {
		out.println( "<td " + Color + "l\" width=\"25%\"  height=\"38\">" + line_desc + "</td>\n" );
	  }

	  if ( this.getupdateStatus() && !(recqcallowed) && ( sortby.equalsIgnoreCase( "1" ) ))
	  {
		out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" + ( chkbox_value ) + "\" onClick= \"checknonchemqc(name,this)\" " + checkednon + "  NAME=\"row_chk" + i + "\">&nbsp;</td>\n" );
	  }
	  else
	  {
		out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">&nbsp;</td>\n" );
	  }

	  out.println( "<td " + Color + "l\" width=\"13%\" height=\"38\">" + Mfg_lot + "<BR>" + origmfglot + "</td>\n" );
	  if ( sortby.equalsIgnoreCase( "2" ) )
	  {
		out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + ( hD.get( "END_DATE" ) == null ? "&nbsp;" : hD.get( "END_DATE" ).toString() ) + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + Date_recieved + "</td>\n" );
	  }
	  else
	  {
		if ( FirstRow )
 		{
		   out.println( "<td " + Color + "\"\" width=\"8%\" height=\"38\" rowspan=\""+numofkits+"\">" + lotstatus + "</td>\n" );
		}
		out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + actshipDate + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + Date_recieved + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + dom + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + dos + "</td>\n" );
		if ( "Y".equalsIgnoreCase( indefshelflie ) || "01/01/3000".equalsIgnoreCase( expdate ) )
		{
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">Indefinite</td>\n" );
		}
		else
		{
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + expdate + "</td>\n" );
		}
	  }
	  out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + bin + "</td>\n" );
	  if ( FirstRow )
 	  {
	  out.println( "<td " + Color + "\" width=\"13%\" height=\"38\" rowspan=\""+numofkits+"\">" + receipt_id + "<BR>" + tranreceiptid + "</td>\n" );
	  out.println( "<td " + Color + "\" width=\"8%\" height=\"38\" rowspan=\""+numofkits+"\">" + quantity + "</td>\n" );

	  if ( sortby.equalsIgnoreCase( "2" ) )
	  {
		out.println( "<td " + Color + "\" width=\"8%\" height=\"38\" rowspan=\""+numofkits+"\">" + packaging + "</td>\n" );
	  }
	  else
	  {
		if ( this.getupdateStatus() && !(recqcallowed) )
	    {
		 out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"label_qty" + i + "\"  value=\"" + quantity + "\" maxlength=\"30\" size=\"6\"></td>\n" );
	    }
		else
		{
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">&nbsp;</td>\n" );
		}
	  }
	  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">"+recqcnotes+"</td>\n" );
	  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">"+deliveryTicket+"</td>\n" );
	  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">&nbsp;</td>\n" );
 	  }
	  out.println( "</tr>\n" );
	}
	else
	{
        if ( ( SubuserAction.equalsIgnoreCase( "Update" ) && "YES".equalsIgnoreCase( LINE_STATUS ) ) || ( "YES".equalsIgnoreCase( LINE_STATUS ) ) )
        {
          out.println( "<INPUT TYPE=\"hidden\" value=\"no\" NAME=\"row_chk" + i + "\">\n" );
          out.println( "<INPUT TYPE=\"hidden\" value=\"" + lotstatus + "\" name=\"selectElementStatus" + i + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"act_suppship_date" + i + "\" value=\"" + actshipDate + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"date_recieved" + i + "\" value=\"" + Date_recieved + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"date_mfgd" + i + "\" value=\"" + dom + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"ship_date" + i + "\" value=\"" + dos + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"expiry_date" + i + "\" value=\"" + expdate + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"label_qty" + i + "\"  value=\"" + quantity + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"po\" value=\"" + Purchase_order + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"po_line" + i + "\" value=\"" + PO_Line + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + Item_id + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"item_id\" value=\"" + Item_id + "\">\n" );
        }
        else
        {
          out.println( "<tr align=\"center\">\n" );

		  if ( FirstRow )
		  {
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" );
		  if ( "IT".equalsIgnoreCase( doctype ))
		  {
			out.println( "<A HREF=\"javascript:showrecforinvtransfr(" + transreqid + "," + HubNo + ")\">TR " +transreqid+ "</A>" );
		  }
		  else if ("IA".equalsIgnoreCase( doctype ))
		  {
			out.println( "MR " + returnPrNumber + "-" + returnLineItem + "\n" );
		  }
		  else
		  {
			out.println( "" + Purchase_order + "" );
			if ( sortby.equalsIgnoreCase( "2" ) )
			{
			  out.println( "<BR>(" + invgrpname + ")</td>\n" );
			}
		  }
          out.println( "</td>\n" );


          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" );
          if ( sortby.equalsIgnoreCase( "2" ) )
          {
            out.println( "<A HREF=\"javascript:showPreviouspolineqc(" + PO_Line + "," + Purchase_order + "," + HubNo + ")\">" + PO_Line + "</A></td>\n" );
          }
		  else if ( !("IT".equalsIgnoreCase( doctype ) || "IA".equalsIgnoreCase( doctype )) )
          {
            out.println( PO_Line );
          }
          out.println( "</A></td>\n" );

		  if ( sortby.equalsIgnoreCase( "1" ) )
          {
			out.println( "<td " + Color + "\" width=\"8%\" height=\"38\" rowspan=\""+numofkits+"\">\n" );
			out.println( "<A HREF=\"javascript:showPreviousrecqc(" + Item_id + "," + HubNo + ")\">" + Item_id + "</A><BR>("+invgrpname+")</td>\n" );
          }
		  }

		  if ( "N".equalsIgnoreCase( mngkitassingl ) )
		  {
			out.println( "<td " + Color + "l\" width=\"25%\" height=\"38\" >" + compmatdesc + "</td>\n" );
		  }
		  else
		  {
			out.println( "<td " + Color + "l\" width=\"25%\"  height=\"38\">" + line_desc + "</td>\n" );
		  }

		  if ( ( "IT".equalsIgnoreCase( doctype ) && "N".equalsIgnoreCase( oktoqc ) ) ||
			   ( !Mfg_lot.equalsIgnoreCase( origmfglot ) && "IT".equalsIgnoreCase( doctype ) ) || ! ( recqcallowed ) )
		  {
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><INPUT TYPE=\"hidden\" value=\"no\" NAME=\"row_chk" +	i + "\">&nbsp;</td>\n" );
		  }
		  else
		  {
			if ( sortby.equalsIgnoreCase( "2" ) )
			{
			  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" + ( chkbox_value ) + "\" onClick= \"checknonchemqc(name,this)\" " + checkednon + "  NAME=\"row_chk" + i + "\"></td>\n" );
			}
			else
			{
			  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" + ( chkbox_value ) + "\" onClick= \"checkValuesqc(name,this)\" " + checkednon + "  NAME=\"row_chk" + i + "\"></td>\n" );
			  String temp="<A HREF=\"javascript:showPreviousTransactions('" + origmfglot + "'," + HubNo + ")\">" + origmfglot + "</A>\n";
			  origmfglot=temp;
			}
		  }

          if ( sortby.equalsIgnoreCase( "2" ) )
          {
			out.println( "<td " + Color + "l\" width=\"13%\" height=\"38\">"+ Mfg_lot + "<BR>" + origmfglot + "</td>\n" );
            out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + ( hD.get( "END_DATE" ) == null ? "&nbsp;" : hD.get( "END_DATE" ).toString() ) + "</td>\n" );
            out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + Date_recieved + "</td>\n" );
          }
          else
          {
			out.println( "<td " + Color + "l\" width=\"13%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"mfg_lot" + i + "\" value=\"" + Mfg_lot + "\" maxlength=\"30\" size=\"20\"><BR>" + origmfglot + "</td>\n" );

			boolean stautsupdallowed = false;
			if ( (allowforlotstatus.contains(invengrp) || !"Y".equalsIgnoreCase(qualitycntitem)) && !nonpickable.contains(invengrp) )
			{
			  stautsupdallowed = true;
			}

			if ( FirstRow )
 		    {
			out.println( "<td " + Color + "\"\" width=\"8%\" height=\"38\" rowspan=\""+numofkits+"\">\n" );
            out.println( "<select name=\"selectElementStatus" + i + "\" onChange=\"checkterminalstatus('"+i+"')\">\n" );
            out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
			if ("Incoming".equalsIgnoreCase(lotstatus))
			{
			  out.println("<OPTION VALUE=\"Incoming\" selected>Incoming</OPTION>\n");
			}
			out.println( radian.web.HTMLHelpObj.getlogisticsDropDown( lot_status,lotstatus,stautsupdallowed,null ) );
			out.println( "</select>\n" );
			out.println( "<input type=\"hidden\" name=\"origlotstatus" + i + "\" value=\"" + lotstatus + "\">\n" );
			out.println("</td>\n");
 		    }

            out.println( "<td " + Color + "\" width=\"8%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"act_suppship_date" + i + "\" value=\"" + actshipDate + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.act_suppship_date" + i + ");\">&diams;</a></FONT></td>\n" );
            out.println( "<td " + Color + "\" width=\"8%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"date_recieved" + i + "\" value=\"" + Date_recieved + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.date_recieved" + i + ");\">&diams;</a></FONT></td>\n" );
            out.println( "<td " + Color + "\" width=\"8%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"date_mfgd" + i + "\" value=\"" + dom + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.date_mfgd" + i + ");\">&diams;</a></FONT></td>\n" );
            out.println( "<td " + Color + "\" width=\"8%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_date" + i + "\" value=\"" + dos + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.ship_date" + i + ");\">&diams;</a></FONT></td>\n" );

            if ( "Y".equalsIgnoreCase( indefshelflie ) || "01/01/3000".equalsIgnoreCase( expdate ) )
            {
              out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"expiry_date" + i + "\" value=\"Indefinite\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.expiry_date" + i + ");\">&diams;</a></FONT></td>\n" );
            }
            else
            {
              out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"expiry_date" + i + "\" value=\"" + expdate + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.expiry_date" + i + ");\">&diams;</a></FONT></td>\n" );
            }
          }

          out.println( "<td " + Color + "\" width=\"8%\" height=\"38\">" + bin + "</td>\n" );

		  if ( FirstRow )
		  {
          out.println( "<td " + Color + "\" width=\"13%\" height=\"38\" rowspan=\""+numofkits+"\">" + receipt_id + "<BR>" + tranreceiptid + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"8%\" height=\"38\" rowspan=\""+numofkits+"\">" + quantity + "</td>\n" );

          if ( sortby.equalsIgnoreCase( "2" ) )
          {
            out.println( "<td " + Color + "\" width=\"8%\" height=\"38\" rowspan=\""+numofkits+"\">" + packaging + "</td>\n" );
          }
          else
          {
            out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"label_qty" + i + "\"  value=\"" + quantity + "\" maxlength=\"30\" size=\"6\"></td>\n" );
          }
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\"><TEXTAREA name=\"recnotes" + i + "\" rows=\"3\" cols=\"25\">" + recqcnotes + "</TEXTAREA></TD>\n" );
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\"><input type=\"text\" CLASS=\"DETAILS\" name=\"deliveryTicket" + i + "\"  value=\"" + deliveryTicket + "\" maxlength=\"30\" size=\"6\"></TD>\n" );
          out.println( "<TD " + Color + "l\" WIDTH=\"5%\" rowspan=\""+numofkits+"\"><INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" OnClick= \"javascript:unReceive(name,this," + ( hD.get( "RECEIPT_ID" ) == null ? "" : hD.get( "RECEIPT_ID" ) ) + ")\" name=\"Button" + i + "\" value=\"Reverse\">\n\n" );
		  }

		  out.println( "<input type=\"hidden\" name=\"po\" value=\"" + Purchase_order + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"po_line" + i + "\" value=\"" + PO_Line + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + Item_id + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"item_id\" value=\"" + Item_id + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"rootcause" + i + "\" value=\"" + rootcause + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"rootcausecompany" + i + "\" value=\"" + rootcausecompany + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"rootcausenotes" + i + "\" value=\"" + rootcausenotes + "\">\n" );
		  if ( "N".equalsIgnoreCase( mngkitassingl ) && !FirstRow )
		  {
			out.println( "<input type=\"hidden\" name=\"statusoveride" + i + "\" value=\"Y\">\n" );
		  }
		  else
		  {
			out.println( "<input type=\"hidden\" name=\"statusoveride" + i + "\" value=\"N\">\n" );
		  }

          out.println( "</tr>\n" );
        }
	  }
		if ( ( ItemIdCount ) % 2 == 0 )
		{
		  Color="CLASS=\"white";
		}
		else
		{
		  Color="CLASS=\"blue";
		}

      }

      out.println( "</table>\n" );
      out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.println( "<tr>" );
      out.println( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
      out.println( "</TD></tr>" );
      out.println( "</table>\n" );
      out.println( "</form>\n" );
      out.println( "</body></html>\n" );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
    }

    return;
  }
}
//END OF CLASS

