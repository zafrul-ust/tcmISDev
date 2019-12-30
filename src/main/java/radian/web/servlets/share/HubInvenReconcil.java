package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.HubInvenReconcilTables;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;
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
 * 07-29-03 Code Cleanup
 * 02-16-04 - Sorting Drop Downs
 * 04-29-04 - Getting Hub list from Hub_pref
 * 05-07-04 - Sorting the Hub Drop Down based on the prority in hub_pref table
 */

public class HubInvenReconcil
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private Vector receiptIdstoLabel = null;
    private Vector numbersoflabels = null;
    private HubInvenReconcilTables  hubObj = null;
    //private PrintWriter out = null;
    private boolean completeSuccess = true ;
    private boolean noneToUpd = true ;
    private String Receiving_Servlet = "";
    private String Label_Servlet = "";
    private String numOldDays = "";
    private boolean allowupdate;
    private boolean intcmIsApplication = false;
	private ResourceLibrary res = null; // res means resource.
    private static org.apache.log4j.Logger invlog = org.apache.log4j.Logger.getLogger(HubInvenReconcil.class);

    public void setupdateStatus(boolean id)
    {
      this.allowupdate = id;
    }

    private boolean getupdateStatus()  throws Exception
    {
     return this.allowupdate;
    }
    public HubInvenReconcil(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    //Process the HTTP Post request
    public void doResult(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException,  IOException
    {
        hubObj = new HubInvenReconcilTables(db);
        //out = response.getWriter();
        //response.setContentType( "text/html; charset=UTF-8" );

        HttpSession session = request.getSession();
        res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));

        PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        intcmIsApplication = false;
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

        String branch_plant= session.getAttribute( "BRANCH_PLANT" ) == null ? "" : session.getAttribute( "BRANCH_PLANT" ).toString();
        String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
    		Collection hubInventoryGroupOvBeanCollection = new Vector((Collection)session.getAttribute("hubInventoryGroupOvBeanCollection"));

        Receiving_Servlet = bundle.getString("HUB_INVEN_RECONCIL");
        Label_Servlet = bundle.getString("RE_LABEL_SERVLET");

        String User_Search = null;
        String User_Action = null;
        String SubUser_Action = null;
        String User_Session = "";
        String User_Sort    = "";
        String generate_labels = "";
        String toItemRedirect = "";
        String ItemNumtoRedirect = "";

        String scategory = "";
        try{ scategory = request.getParameter("Category").toString();}catch (Exception e){ scategory = "No";}
        try{ generate_labels = request.getParameter("generate_labels").toString();}catch (Exception e){ generate_labels = "No";}
        try{ User_Session  = request.getParameter("session").toString();}catch (Exception e){ User_Session = "New";}
        try{ User_Action   = request.getParameter("UserAction").toString();}
        catch (Exception e)
        {
          User_Action = "New";
        }
        try{ User_Search   = request.getParameter("SearchField").toString();}catch (Exception e){ User_Search = "NONE"; }
        try{ User_Sort     = request.getParameter("SortBy").toString();}catch (Exception e){ User_Sort = "0"; }
        try{ toItemRedirect  = request.getParameter("toRedirectItem").toString();}catch (Exception e){ toItemRedirect = "No";}
        try{ ItemNumtoRedirect  = request.getParameter("RedirectItemValue").toString();}catch (Exception e){ ItemNumtoRedirect = "NONE";}


        String showproblemOnly=request.getParameter( "showproblemOnly" );
				if ( showproblemOnly == null )
					showproblemOnly="N";
				showproblemOnly=showproblemOnly.trim();

        /*String invengrp=request.getParameter( "invengrp" );
				if ( invengrp == null )
					invengrp="N";
				invengrp=invengrp.trim();*/

    String [] invengrpArray = {""};
    invengrpArray  = request.getParameterValues("invengrp");
    String invengrpfromArray = "";
    //Vector selectedcabs = new Vector();
    int invGroupAdded = 0;
    if (invengrpArray != null)
    {
      for (int loop  = 0 ; loop  < invengrpArray.length  ; loop++)
      {
        if (invGroupAdded > 0) {invengrpfromArray += ",";}
        if ("All".equalsIgnoreCase(invengrpArray[loop]))
        {

        }
        else
        {
        invengrpfromArray += "'"+ invengrpArray[loop] +"'";
        //System.out.println(statusStringfromArray);
        invGroupAdded++;
        }
        //selectedcabs.add(invengrpArray[loop]);
      }
    }
    if (invengrpfromArray.length() == 0)
    {
      invengrpfromArray = "N";
      //selectedcabs.add("New");
    }

        try{numOldDays = BothHelpObjs.makeBlankFromNull((String)request.getParameter("Type"));}
        catch (Exception e)
        {
          numOldDays = "";
        }

       String Type = "";
       try{Type = BothHelpObjs.makeBlankFromNull((String)request.getParameter("Type"));}catch (Exception e){Type = "";}

       if ( "Item".equalsIgnoreCase( Type ) )
       {
         scategory="2";
       }

       String searchwhat = "";
       try{searchwhat = BothHelpObjs.makeBlankFromNull((String)request.getParameter("searchstring"));}catch (Exception e){searchwhat = "";}

        if ( User_Search.equalsIgnoreCase(""))
        {
            User_Search = "NONE";
        }

        if ("NONE".equalsIgnoreCase(branch_plant))
        {
        branch_plant = hubObj.getHubWhenNone("Inventory",personnelid);
        }

        try{ SubUser_Action= request.getParameter("SubUserAction").toString();}catch (Exception e){ SubUser_Action = "JSError2";}

        if ( (User_Session.equalsIgnoreCase("Active")) && "STARTNEW".equalsIgnoreCase(SubUser_Action))
        {
           User_Sort =  hubObj.getSysDateForNewCount().trim();

           if (!(hubObj.StartNewCount(branch_plant,User_Sort)))
           {
            out.println(radian.web.HTMLHelpObj.printError("title.invrecon",intcmIsApplication,res));
            return;
           }
        }
        else if ("0".equalsIgnoreCase(User_Sort))
        {
           SubUser_Action = "New";
        }

        try
        {
		  Hashtable hub_list_set=new Hashtable();
		  hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

		  Hashtable hub_prority_list= ( Hashtable ) ( hub_list_set.get( "HUB_PRORITY_LIST" ) );
		  if ( branch_plant.trim().length() == 0 )
		  {
			branch_plant=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
		  }
		  Vector countdates=new Vector();
		  if ( ! ( branch_plant.length() < 1 ) )
		  {
			countdates=hubObj.getCountDates( branch_plant );
		  }

          if ( User_Session.equalsIgnoreCase( "New" ) )
          {
            String CompanyID= session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
            /*Hashtable hub_list_set=new Hashtable();
			if ( this.getupdateStatus() )
			{
			  hub_list_set=radian.web.HTMLHelpObj.createHubList( db,personnelid,CompanyID );
			}
			else
			{
			  hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,CompanyID );
			}*/

            Hashtable hub_list1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );

            if ( hub_list1.size() < 1 )
            {
              buildHeader( null,hubInventoryGroupOvBeanCollection,"",User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
              out.println( radian.web.HTMLHelpObj.printHTMLNoFacilities(res) );
              hub_list_set=null;
              out.close();
            }
            else
            {
              //session.setAttribute( "HUB_SET",hub_list_set );
              buildHeader( null,hubInventoryGroupOvBeanCollection,branch_plant,User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
              out.println( radian.web.HTMLHelpObj.printHTMLSelect(res) );
              out.close();
              hub_list_set=null;
            }
          }
          else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "New" ) ) )
          {
            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
            session.setAttribute( "UPDATEDONE","NO" );

            if ( "NEW".equalsIgnoreCase( SubUser_Action ) )
            {
              buildHeader( null,hubInventoryGroupOvBeanCollection,branch_plant,User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
              out.println( radian.web.HTMLHelpObj.printHTMLSelect(res) );
              out.close();
              hub_list_set=null;

              return;
            }
            Vector openorder_data=new Vector();

            String CancelAction1="";
            try
            {
              CancelAction1=request.getParameter( "CancelUserAction" ).toString();
            }
            catch ( Exception e )
            {
              CancelAction1=" ";
            }
            openorder_data=hubObj.getOpenInventory( branch_plant,User_Sort,Type,scategory,searchwhat,showproblemOnly,invengrpfromArray );

            session.setAttribute( "LAST_SEARCHHUB",branch_plant );
            session.setAttribute( "LAST_COUNTDATE",User_Sort );
            session.setAttribute( "LAST_ALL",numOldDays );
            session.setAttribute( "LAST_ORDER_BY",scategory );

            Hashtable sum= ( Hashtable ) ( openorder_data.elementAt( 0 ) );
            int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
            if ( 0 == count )
            {
              buildHeader( null,hubInventoryGroupOvBeanCollection,branch_plant,User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
              out.println( radian.web.HTMLHelpObj.printMessageinTable( res.getString("label.ssrcountdate")+" <B>" + User_Sort + "</B>" ) );
              out.println( radian.web.HTMLHelpObj.printHTMLNoData( res.getString("err.noinvfound") ) );
              out.close();
              //clean up
              openorder_data=null;
              hub_list_set=null;
            }
            else
            {
              Hashtable in_bin_set=new Hashtable();
              Hashtable out_bin_set2=new Hashtable();
              //Hashtable lot_status_set=new Hashtable();

              session.setAttribute( "DATA",openorder_data );
              session.setAttribute( "BIN_SET",out_bin_set2 );
              //session.setAttribute( "STATUS_SET",lot_status_set );

              buildHeader( openorder_data,hubInventoryGroupOvBeanCollection,branch_plant,User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
              out.println( radian.web.HTMLHelpObj.printMessageinTable( res.getString("label.ssrcountdate")+" <B>" + User_Sort + "</B>" ) );
              buildDetails( openorder_data,scategory,out,showproblemOnly );

              out.close();
              //clean up
              openorder_data=null;
              hub_list_set=null;
              in_bin_set=null;
              out_bin_set2=null;
              //lot_status_set=null;
            } //when there are open orders for hub
          }
          else
          if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "Update" ) ) )
          {
            Vector retrn_data=new Vector();
            Vector synch_data=new Vector();
            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
            String CancelAction="";

            try
            {
              CancelAction=request.getParameter( "CancelUserAction" ).toString();
            }
            catch ( Exception e )
            {
              CancelAction="Yes";
            }

            if ( "updatePick".equalsIgnoreCase( SubUser_Action ) && ! ( "Yes".equalsIgnoreCase( CancelAction ) ) )
            {
              Vector issue_data= ( Vector ) session.getAttribute( "NEWPICK_DATA" );
              synch_data=SynchPickServerData( request,issue_data );
            }
            else if ( ( "IssueUpdPage".equalsIgnoreCase( SubUser_Action ) ) || ( "updateNewPick".equalsIgnoreCase( SubUser_Action ) ) )
            {
              Vector issue_data= ( Vector ) session.getAttribute( "ITEMISSUE_DATA" );
              synch_data=SynchIssueServerData( request,issue_data,out );
            }
            else
            {
              retrn_data= ( Vector ) session.getAttribute( "DATA" );
              synch_data=SynchServerData( request,retrn_data ,out);
            }

            retrn_data=null;

            if ( SubUser_Action.equalsIgnoreCase( "JSError2" ) )
            {
              //Hashtable all_status_set_e= ( Hashtable ) session.getAttribute( "STATUS_SET" );
              //Hashtable all_bin_set_e= ( Hashtable ) session.getAttribute( "BIN_SET" );

              session.setAttribute( "DATA",synch_data );

              buildHeader( synch_data,hubInventoryGroupOvBeanCollection,branch_plant,User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
              out.println( radian.web.HTMLHelpObj.printJavaScriptError(res) );
              buildDetails( synch_data,scategory,out ,showproblemOnly);

              out.close();
              //clean up
              synch_data=null;
              //all_bin_set_e=null;
              //all_status_set_e=null;
              //
            }
            else if ( SubUser_Action.equalsIgnoreCase( "updatePick" ) )
            {
              String itemID="";


              String prnumber1="";
              String lineItem1="";

              try
              {
                itemID=request.getParameter( "NItemId" ).toString();
              }
              catch ( Exception e )
              {
                itemID="0";
              }

              try
              {
                prnumber1=request.getParameter( "pr_number" ).toString();
              }
              catch ( Exception e )
              {
                itemID="0";
              }
              try
              {
                lineItem1=request.getParameter( "line_item" ).toString();
              }
              catch ( Exception e )
              {
                itemID="0";
              }

              if ( "Yes".equalsIgnoreCase( CancelAction ) )
              {
                Vector openorder_data1=hubObj.getIssueHistory( branch_plant,itemID,User_Sort );
                Vector smallTable_data1=hubObj.getReceiptIdItem( branch_plant,itemID,User_Sort );

                session.setAttribute( "UPDATEDONE","No" );
                out.println( buildIssueReconcileHeader( openorder_data1,branch_plant,itemID,User_Sort," ",scategory,smallTable_data1,Type,searchwhat ,out) );
              }
              else
              {
                Hashtable updateresults=UpdatePickDetails( synch_data,branch_plant,prnumber1,lineItem1,personnelid );
                Vector openorder_data=hubObj.getIssueHistory( branch_plant,itemID,User_Sort );
                Vector smallTable_data=hubObj.getReceiptIdItem( branch_plant,itemID,User_Sort );
                session.setAttribute( "ITEMISSUE_DATA",openorder_data );
                session.setAttribute( "RECEIPTISSUE_DATA",smallTable_data );
                session.setAttribute( "UPDATEDONE","No" );
                Boolean result= ( Boolean ) updateresults.get( "RESULT" );

                boolean resulttotest=result.booleanValue();

                if ( false == resulttotest )
                {
                  if ( true == noneToUpd )
                  {
                    out.println( buildIssueReconcileHeader( openorder_data,branch_plant,itemID,User_Sort,"<BR><B>"+res.getString("err.nothingpicked")+"</B><BR>",scategory,smallTable_data,Type,searchwhat,out ) );
                  }
                  else
                  {
                    out.println( buildIssueReconcileHeader( openorder_data,branch_plant,itemID,User_Sort,res.getString("label.anerroroccured"),scategory,smallTable_data,Type,searchwhat,out ) );
                  }
                }
                else
                {
                  if ( true == completeSuccess )
                  {
                    out.println( buildIssueReconcileHeader( openorder_data,branch_plant,itemID,User_Sort,"",scategory,smallTable_data,Type,searchwhat,out ) );
                  }
                  else
                  {
                    //Vector errordata= ( Vector ) updateresults.get( "ERRORDATA" );
                    Vector errMsgs=hubObj.getErrMsgs();
                    out.println( radian.web.HTMLHelpObj.printHTMLPartialSuccess( errMsgs,res ) );
                    out.println( buildIssueReconcileHeader( openorder_data,branch_plant,itemID,User_Sort,res.getString("label.anerroroccured"),scategory,smallTable_data,Type,searchwhat,out ) );
                  }
                }
              }
              out.close();
            }
            else if ( SubUser_Action.equalsIgnoreCase( "updateNewPick" ) )
            {
              String newPickLine="";
              String itemID="";

              try
              {
                newPickLine=request.getParameter( "NewPickLine" ).toString();
              }
              catch ( Exception e )
              {
                newPickLine="JSError2";
              }
              try
              {
                itemID=request.getParameter( "NItemId" ).toString();
              }
              catch ( Exception e )
              {
                itemID="0";
              }

              session.setAttribute( "ITEMISSUE_DATA",synch_data );
              Hashtable updateresults=UpdateDetails( synch_data,personnelid,User_Sort,"IssueUpdPage",out );
              NewPick obj=new NewPick( bundle,db );

              int line_to_pick= ( new Integer( newPickLine ) ).intValue();
              if ( line_to_pick > 0 )
              {
                Hashtable PickLineData= ( Hashtable ) ( synch_data.elementAt( line_to_pick ) );

                String prnumber=PickLineData.get( "PR_NUMBER" ).toString();
                String lineItem=PickLineData.get( "LINE_ITEM" ).toString();
                String MrQty=PickLineData.get( "QUANTITY" ).toString();

                Vector newpick_data=hubObj.getPickData( branch_plant,prnumber,lineItem );
                Hashtable sum1= ( Hashtable ) ( newpick_data.elementAt( 0 ) );
                int count= ( ( Integer ) sum1.get( "TOTAL_NUMBER" ) ).intValue();
                if ( 0 == count )
                {
                  newpick_data=null;
                  Vector openorder_data=hubObj.getIssueHistory( branch_plant,itemID,User_Sort );
                  Vector smallTable_data=hubObj.getReceiptIdItem( branch_plant,itemID,User_Sort );

                  session.setAttribute( "ITEMISSUE_DATA",openorder_data );
                  session.setAttribute( "RECEIPTISSUE_DATA",smallTable_data );

                  out.println( buildIssueReconcileHeader( openorder_data,branch_plant,itemID,User_Sort,"<B>"+res.getString("err.noqtytopick")+ " "+res.getString("label.mr")+ ":"+prnumber + " "+res.getString("label.lineitem")+ ":"+lineItem + "<BR>"+res.getString("err.pleasetryanother")+"</B>",scategory,smallTable_data,Type,searchwhat,out ) );
                }
                else
                {
                  session.setAttribute( "NEWPICK_DATA",newpick_data );
                  out.println( obj.buildPickDetails( newpick_data,itemID,prnumber,lineItem,branch_plant,User_Sort,scategory,MrQty ) );
                }
                out.close();

              }
            }
            else if ( SubUser_Action.equalsIgnoreCase( "UpdPage" ) || SubUser_Action.equalsIgnoreCase( "IssueUpdPage" ) )
            {

              //Hashtable temp1= ( Hashtable ) session.getAttribute( "BIN_SET" );
              //Hashtable temp2= ( Hashtable ) session.getAttribute( "STATUS_SET" );
              //
              if ( "IssueUpdPage".equalsIgnoreCase( SubUser_Action ) )
              {
                session.setAttribute( "ITEMISSUE_DATA",synch_data );
              }
              else
              {
                session.setAttribute( "DATA",synch_data );
              }

			  String updatestatus= session.getAttribute( "UPDATEDONE" ) == null ? "" : session.getAttribute( "UPDATEDONE" ).toString();
              if ( "Yes".equalsIgnoreCase( updatestatus ) && ( SubUser_Action.equalsIgnoreCase( "UpdPage" ) ) )
              {
                buildHeader( null,hubInventoryGroupOvBeanCollection,branch_plant,User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray);
                out.println( radian.web.HTMLHelpObj.printMessageinTable( res.getString("label.formsubmmited" ) ) );
                return;
              }

              Hashtable updateresults=UpdateDetails( synch_data,personnelid,User_Sort,SubUser_Action,out );
              Boolean result= ( Boolean ) updateresults.get( "RESULT" );

              session.setAttribute( "UPDATEDONE","YES" );
              boolean resulttotest=result.booleanValue();
              session.setAttribute( "LABEL_SEQUENCE_NUMBERS",receiptIdstoLabel );
              session.setAttribute( "LABEL_QUANTITYS",numbersoflabels );

              if ( "yes".equalsIgnoreCase( toItemRedirect ) )
              {
                Vector openorder_data=hubObj.getIssueHistory( branch_plant,ItemNumtoRedirect,User_Sort );
                Vector smallTable_data=hubObj.getReceiptIdItem( branch_plant,ItemNumtoRedirect,User_Sort );

                session.setAttribute( "UPDATEDONE","No" );
                session.setAttribute( "ITEMISSUE_DATA",openorder_data );
                session.setAttribute( "RECEIPTISSUE_DATA",smallTable_data );

                out.println( buildIssueReconcileHeader( openorder_data,branch_plant,ItemNumtoRedirect,User_Sort,"",scategory,smallTable_data,Type,searchwhat,out ) );
              }
              else if ( false == resulttotest )
              {
                if ( receiptIdstoLabel.size() > 0 && "yes".equalsIgnoreCase( generate_labels ) )
                {
                  buildHeader( null,hubInventoryGroupOvBeanCollection,branch_plant,"1","GENERATE_LABELS",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
                }
                else
                {
                  buildHeader( null,hubInventoryGroupOvBeanCollection,branch_plant,User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
                }

                if ( true == noneToUpd )
                {
                  out.println( radian.web.HTMLHelpObj.printHTMLNoneToUpdate() );
                }
                else
                {
                  out.println( radian.web.HTMLHelpObj.printHTMLError() );
                }
                out.close();
              }
              else
              {
                //contains a list of a list of lot seq that were added to receiving table
                Vector openorder_data=hubObj.getOpenInventory( branch_plant,User_Sort,Type,scategory,searchwhat,showproblemOnly,invengrpfromArray );
                session.removeAttribute( "DATA" );
                session.setAttribute( "DATA",openorder_data );
                session.setAttribute( "LAST_SEARCHHUB",branch_plant );
                session.setAttribute( "LAST_COUNTDATE",User_Sort );
                session.setAttribute( "LAST_ALL",numOldDays );
                session.setAttribute( "LAST_ORDER_BY",scategory );
                session.setAttribute( "UPDATEDONE","No" );
                buildHeader( openorder_data,hubInventoryGroupOvBeanCollection,branch_plant,User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
                out.println( radian.web.HTMLHelpObj.printMessageinTable( res.getString("label.ssrcountdate")+" <B>" + User_Sort + "</B>" ) );
                buildDetails( openorder_data,scategory,out,showproblemOnly );
                out.close();
              }
              //clean up
              synch_data=null;
              //temp1=null;
              //temp2=null;
            }
            else
            {
              out.println( radian.web.HTMLHelpObj.printError( "title.invrecon",intcmIsApplication,res ) );
            }
          }  //End of User_session="Active" && User_Action="Update"
          else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "JSError1" ) ) )
          {
            Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
            Vector synch_data=SynchServerData( request,retrn_data,out );
            retrn_data=null;

            //Hashtable all_bin_set_e= ( Hashtable ) session.getAttribute( "BIN_SET" );
            //Hashtable all_status_set_e= ( Hashtable ) session.getAttribute( "STATUS_SET" );
            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
            //
            session.setAttribute( "DATA",synch_data );
            //session.setAttribute( "BIN_SET",all_bin_set_e );
            //session.setAttribute( "STATUS_SET",all_status_set_e );

            buildHeader( synch_data,hubInventoryGroupOvBeanCollection,branch_plant,User_Sort,"",scategory,countdates,Type,searchwhat,out,session,showproblemOnly,invengrpfromArray );
            out.println( radian.web.HTMLHelpObj.printJavaScriptError() );

            buildDetails( synch_data,scategory,out,showproblemOnly );

            out.close();
            //clean up
            synch_data=null;
            //all_bin_set_e=null;
            //all_status_set_e=null;
          }
          else
          {
            out.println( radian.web.HTMLHelpObj.printError( "title.invrecon",intcmIsApplication,res ) );
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printError( "title.invrecon",intcmIsApplication,res ) );
        }

        //clean up
        hubObj=null;
      }

      private Vector SynchServerData( HttpServletRequest request,Vector in_data,PrintWriter out )
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

            String act_on_hand="";
            try
            {
              act_on_hand=request.getParameter( ( "act_onhand" + i ) ).toString();
            }
            catch ( Exception e )
            {
              act_on_hand="";
            }

            if ( ( act_on_hand.length() > 0 ) && !act_on_hand.equalsIgnoreCase( hD.get( "ACTUAL_ON_HAND" ).toString() ) )
            {
              hD.remove( "ACTUAL_ON_HAND" );
              hD.put( "ACTUAL_ON_HAND",act_on_hand );

              hD.remove( "DOSTATUSUPDATE" );
              hD.put( "DOSTATUSUPDATE","Yes" );
            }

            new_data.addElement( hD );
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printError( "title.invrecon",intcmIsApplication,res ) );
        }
        return new_data;
    }

    private Vector SynchPickServerData( HttpServletRequest request,Vector in_data)
    {
        Vector new_data = new Vector();
        Hashtable sum = ( Hashtable)( in_data.elementAt(0));
        int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();
        new_data.addElement(sum);

        try
        {
            for ( int i = 1 ; i< count+1 ; i++)
            {
                Hashtable hD = new Hashtable();
                hD = ( Hashtable)( in_data.elementAt(i));

                String rev_qty = "";
                String date_pick = "";

                try{ rev_qty   = request.getParameter(("qty_picked"+i)).toString();}catch (Exception e){ rev_qty  = "";}
                try{ date_pick   = request.getParameter(("date_picked"+i)).toString();}catch (Exception e){ date_pick  = "";}

                if ( (rev_qty.length() > 0) && !rev_qty.equalsIgnoreCase(hD.get("QUANTITY").toString()) )
                {

                  if (date_pick.length() == 10)
                  {
                    hD.remove("QUANTITY");
                    hD.put("QUANTITY", rev_qty );

                    hD.remove("PICK_DATE");
                    hD.put("PICK_DATE", date_pick );

                    hD.remove("DOSTATUSUPDATE");
                    hD.put("DOSTATUSUPDATE","Yes");
                  }

                }
                new_data.addElement(hD);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new_data;
    }

    private Vector SynchIssueServerData( HttpServletRequest request,Vector in_data,PrintWriter out )
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

          String rev_issue="";
          try
          {
            rev_issue=request.getParameter( ( "rev_issue" + i ) ).toString();
          }
          catch ( Exception e )
          {
            rev_issue="";
          }

          if ( ( rev_issue.length() > 0 ) && !rev_issue.equalsIgnoreCase( hD.get( "REVISED_ISSUED" ).toString() ) )
          {

            invlog.debug( "Before : " + hD.get( "REVISED_ISSUED" ).toString() + " After :" + rev_issue + "   QTY :" + hD.get( "QUANTITY" ).toString() );

            if ( Integer.parseInt( rev_issue ) < Integer.parseInt( hD.get( "QUANTITY" ).toString() ) )
            {
              hD.remove( "REVISED_ISSUED" );
              hD.put( "REVISED_ISSUED",rev_issue );

              hD.remove( "DOSTATUSUPDATE" );
              hD.put( "DOSTATUSUPDATE","Yes" );
            }

          }

          new_data.addElement( hD );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "title.invrecon",intcmIsApplication,res ) );
      }
      return new_data;
    }

    private Hashtable UpdatePickDetails( Vector data,String BranchPlant,String MRnum,String LineITem,String logonid )
    {
      boolean result=false;
      Hashtable updateresult=new Hashtable();
      Vector errordata=new Vector();
      Hashtable dosomething=new Hashtable();
      int count=0;
      dosomething.put( "TOTAL_NUMBER",new Integer( count ) );
      errordata.addElement( dosomething );

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        int linecheckcount=0;
        boolean one_success=false;
        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String dostatusupdate="";
          dostatusupdate= ( hD.get( "DOSTATUSUPDATE" ) == null ? " " : hD.get( "DOSTATUSUPDATE" ).toString() );

          if ( "yes".equalsIgnoreCase( dostatusupdate ) )
          {
            noneToUpd=false;
            linecheckcount++;
            boolean line_result=false;

            line_result=hubObj.NewPick( hD,BranchPlant,MRnum,LineITem,logonid ); // update database
            if ( false == line_result )
            {
              completeSuccess=false;
              hD.remove( "USERCHK" );
              hD.put( "USERCHK"," " );
              errordata.addElement( hD );
            }
            if ( true == line_result )
            {
              one_success=true;
            }
          }
        } //end of for

        if ( linecheckcount == 1 )
        {
          result=true;
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

      }

      updateresult.put( "RESULT",new Boolean( result ) );
      updateresult.put( "ERRORDATA",errordata );

      return updateresult;
    }

    private Hashtable UpdateDetails( Vector data,String logonid,String CountDateStg,String SubUserAction,PrintWriter out )
    {
      boolean result=false;
      Hashtable updateresult=new Hashtable();
      Vector errordata=new Vector();
      receiptIdstoLabel=new Vector();
      numbersoflabels=new Vector();

      Hashtable dosomething=new Hashtable();
      int count=0;
      dosomething.put( "TOTAL_NUMBER",new Integer( count ) );
      errordata.addElement( dosomething );

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        int linecheckcount=0;
        boolean one_success=false;

        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String dostatusupdate="";
          dostatusupdate= ( hD.get( "DOSTATUSUPDATE" ) == null ? " " : hD.get( "DOSTATUSUPDATE" ).toString() );

          if ( "yes".equalsIgnoreCase( dostatusupdate ) )
          {
            noneToUpd=false;
            linecheckcount++;

            //System.out.println( "check Found at line # : " + i );
            boolean line_result=false;
            if ( "IssueUpdPage".equalsIgnoreCase( SubUserAction ) )
            {
              line_result=hubObj.UpdateIssue( hD,logonid ); // update database
            }
            else
            {
              line_result=hubObj.UpdateLine( hD,logonid,CountDateStg ); // update database
            }

            if ( false == line_result )
            {
              completeSuccess=false;
              //System.out.println( "*** Adding to Error Data" );
              hD.remove( "USERCHK" );
              hD.put( "USERCHK"," " );
              errordata.addElement( hD );
            }
            if ( true == line_result )
            {
              one_success=true;
            }
          }
        } //end of for

        if ( linecheckcount == 1 )
        {
          result=true;
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
        out.println( radian.web.HTMLHelpObj.printError( "title.invrecon",intcmIsApplication,res ) );
      }

      updateresult.put( "RESULT",new Boolean( result ) );
      updateresult.put( "ERRORDATA",errordata );

      return updateresult;
    }

    private String buildIssueReconcileHeader( Vector data,String hub_branch_id,String search_text,String sortby,String buildlabels,String category,
                                              Vector SmallTableData,String type,String searchwhat,PrintWriter out)
    {
      StringBuffer Msg=new StringBuffer();
      StringBuffer MsgTmp=new StringBuffer();
      StringBuffer Msgd2=new StringBuffer();
      String ButtonClass="buttonWhite";
      String Color="CLASS=\"Inwhite";

      Msgd2.append( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n" );
      Msgd2.append( "<!--\n" );
      Msgd2.append( "var RecIssue = {" );

      try
      {
        Msg.append( radian.web.HTMLHelpObj.printHTMLHeader( "label.issuerecon","reconciliation.js",intcmIsApplication,res ) );

        if ( "GENERATE_LABELS".equalsIgnoreCase( buildlabels ) )
        {
          Msg.append( "<BODY onLoad=\"doPopup()\">\n" );
        }
        else
        {
          Msg.append( "<BODY>\n" );
        }
        Msg.append( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
        Msg.append( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT><center>\n" );
        Msg.append( "</DIV>\n" );
        Msg.append( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

        Msg.append( radian.web.HTMLHelpObj.PrintTitleBar( "<B>"+res.getString("label.issuereconfor")+" " + search_text + " </B>\n" ) );

        Msg.append( "<BR><CENTER><FONT FACE=\"Arial\" SIZE=\"2\">"+res.getString("label.countdate")+" : <B>" + sortby + "</B></FONT></CENTER>\n" );

        if ( buildlabels.length() > 1 )
        {
          Msg.append( "<CENTER>" + buildlabels + "</CENTER>\n" );
        }

        Msg.append( "<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Receiving_Servlet + "\">\n" );
        //start table #1
        Msg.append( "<input type=\"hidden\" name=\"HubName\" value=\"" + hub_branch_id + "\">\n" );
        Msg.append( "<input type=\"hidden\" name=\"Category\" value=\"" + category + "\">\n" );
        Msg.append( "<input type=\"hidden\" name=\"SortBy\" value=\"" + sortby + "\">\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"NItemId\" VALUE=\"" + search_text + "\">\n" );
        Msg.append( "<input type=\"hidden\" name=\"Type\" value=\"" + type + "\">\n" );
        Msg.append( "<input type=\"hidden\" name=\"searchstring\" value=\"" + searchwhat + "\">\n" );
        Msg.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"10\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        Msg.append( "<TR><TD CLASS=\"announce\"VALIGN=\"Top\" WIDTH=\"80%\">\n" );

        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

        if ( 0 == total )
        {
          Msg.append( "<FONT SIZE=\"2\" FACE=\"Arial\">"+res.getString("label.norecordfound")+"<BR>"+res.getString("label.pleasegoback")+"\n" );
          Msg.append( "</TD></TR><TR>" );
          Msg.append( "<TD CLASS=\"announce\"WIDTH=\"50%\" ALIGN=\"CENTER\">\n" );
          Msg.append( "   <INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.back")+"\" onclick= \"return OldSearch(this)\" NAME=\"SearchButton\">&nbsp;\n" );
          Msg.append( "</TD>\n" );
          Msg.append( "</TR>" );
          Msg.append( "</TABLE>\n" );
          return Msg.toString();
        }

        Hashtable summary1=new Hashtable();
        summary1= ( Hashtable ) SmallTableData.elementAt( 0 );
        int total1= ( ( Integer ) ( summary1.get( "TOTAL_NUMBER" ) ) ).intValue();
        int tot_exp= ( ( Integer ) ( summary1.get( "TOTAL_EXP" ) ) ).intValue();
        int tot_act= ( ( Integer ) ( summary1.get( "TOTAL_ACT" ) ) ).intValue();

        //start table #3
        Msg.append( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

        Hashtable hDNext=new Hashtable();
        int ItemIdCount=0;
        int lastItemNum=1;
        int CellColor=0;
        int InternalColor=0;
        int recIssueTotal=0;
        int revIssueTotal=0;
        int actualonHand=0;

        boolean FirstRow=false;

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
            Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.mrline")+"</TH>\n" );
            Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.mr.qty")+"</TH>\n" );
            Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.facility")+"</TH>\n" );
            Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.workarea")+"</TH>\n" );
            Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.issuer")+"</TH>\n" );
            Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.receiptid")+"</TH>\n" );
            Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.issuedate")+"</TH>\n" );
            Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.receiptdate")+"</TH>\n" );
            Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.recorededissud")+"</TH>\n" );
            Msg.append( "<TH width=\"10%\" colspan=\"2\" height=\"38\">"+res.getString("label.revisedissud")+"</TH>\n" );
            Msg.append( "</TR>\n" );
          }

          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String Next_mr="";
          String Next_line="";

          if ( ! ( i == total ) )
          {
            hDNext=new Hashtable();
            hDNext= ( Hashtable ) data.elementAt( i + 1 );
            Next_mr= ( hDNext.get( "PR_NUMBER" ) == null ? "&nbsp;" : hDNext.get( "PR_NUMBER" ).toString() );
            Next_line= ( hDNext.get( "LINE_ITEM" ) == null ? "&nbsp;" : hDNext.get( "LINE_ITEM" ).toString() );
          }
          else
          {
            Next_mr=" ";
            Next_line=" ";
          }

          // get main information
          String receipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
          String receiptDate= ( hD.get( "TRANSACTION_DATE" ) == null ? "&nbsp;" : hD.get( "TRANSACTION_DATE" ).toString() );
          //String qtyConfirmed= ( hD.get( "QUANTITY_CONFIRMED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_CONFIRMED" ).toString() );
          //String qtyreceived= ( hD.get( "QUANTITY_RECEIVED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_RECEIVED" ).toString() );
          String qtyissued= ( hD.get( "QUANTITY_ISSUED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_ISSUED" ).toString() );
          String revisedIssue= ( hD.get( "REVISED_ISSUED" ) == null ? "&nbsp;" : hD.get( "REVISED_ISSUED" ).toString() );
          //String issueId= ( hD.get( "ISSUE_ID" ) == null ? "&nbsp;" : hD.get( "ISSUE_ID" ).toString() );
          String datePicked= ( hD.get( "DATE_PICKED" ) == null ? "&nbsp;" : hD.get( "DATE_PICKED" ).toString() );
          String confirmed= ( hD.get( "CONFIRMED" ) == null ? "&nbsp;" : hD.get( "CONFIRMED" ).toString() );
          String qty= ( hD.get( "QUANTITY" ) == null ? "&nbsp;" : hD.get( "QUANTITY" ).toString() );
          String prNumber= ( hD.get( "PR_NUMBER" ) == null ? "&nbsp;" : hD.get( "PR_NUMBER" ).toString() );
          String lineItem= ( hD.get( "LINE_ITEM" ) == null ? "&nbsp;" : hD.get( "LINE_ITEM" ).toString() );
          String facility= ( hD.get( "FACILITY" ) == null ? "&nbsp;" : hD.get( "FACILITY" ).toString() );
          String workArea= ( hD.get( "WORK_AREA" ) == null ? "&nbsp;" : hD.get( "WORK_AREA" ).toString() );
          String issuer= ( hD.get( "ISSUER" ) == null ? "&nbsp;" : hD.get( "ISSUER" ).toString() );
          String qtypicked= ( hD.get( "MR_QUANTITY_PICKED" ) == null ? "&nbsp;" : hD.get( "MR_QUANTITY_PICKED" ).toString() );
          String disabeled=" ";

          if ( Integer.parseInt( qty ) == Integer.parseInt( qtypicked ) )
          {
            disabeled="disabled";
          }

          if ( ! ( i == total ) )
          {
            Msgd2.append( "x" + i + ":" + qtyissued + " ," );
          }
          else
          {
            Msgd2.append( "x" + i + ":" + qtyissued + "};\n" );
          }

          int onHand=Integer.parseInt( qtyissued );
          recIssueTotal+=onHand;
          revIssueTotal+=actualonHand;

          boolean SameItem_id=false;
          if ( prNumber.equalsIgnoreCase( Next_mr ) && lineItem.equalsIgnoreCase( Next_line ) )
          {
            SameItem_id=true;
            lastItemNum++;
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
            CellColor++;

            if ( ( !FirstRow ) && lastItemNum > 1 )
            {
              MsgTmp.append( "<TR>\n" );
            }

            MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + issuer + "</td>\n" );
            MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + receipt_id + "</td>\n" );
            MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + datePicked + "</td>\n" );
            MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + receiptDate + "</td>\n" );
            MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + qtyissued + "</td>\n" );
            MsgTmp.append( "<td " + Color + "r\" width=\"10%\">\n" );

            if ( "Y".equalsIgnoreCase( confirmed ) )
            {
              MsgTmp.append( "" + qtyissued + "\n" );
              MsgTmp.append( "<input type=\"hidden\" name=\"act_onhandOld" + i + "\" value=\"" + revisedIssue + "\">\n" );
              MsgTmp.append( "<input type=\"hidden\" name=\"rev_issue" + i + "\" value=\"" + revisedIssue + "\"></td>\n" );
              MsgTmp.append( "<td " + Color + "l\" width=\"2%\">&nbsp;</td>\n" );
            }
            else
            {
              MsgTmp.append( "<input type=\"text\" CLASS=\"DETAILS\" name=\"rev_issue" + i + "\"  value=\"" + revisedIssue + "\" maxlength=\"30\" size=\"6\" onChange=\"CheckIssueValue(name,this,'" + i + "')\"></td>\n" );
              MsgTmp.append( "<input type=\"hidden\" name=\"act_onhandOld" + i + "\" value=\"" + revisedIssue + "\"></td>\n" );
              MsgTmp.append( "<td " + Color + "l\" width=\"2%\">\n" );
              MsgTmp.append( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"+\" NAME=\"newPick" + i + "\" onclick= \"return UpdatenewPick(name,this," + i + ")\" " + disabeled + ">&nbsp;</TD>\n" );
            }

            MsgTmp.append( "<input type=\"hidden\" name=\"confirmed" + i + "\" value=\"" + confirmed + "\">\n" );
            MsgTmp.append( "<input type=\"hidden\" name=\"prnumber" + i + "\" value=\"" + prNumber + "" + lineItem + "\">\n" );
            MsgTmp.append( "<input type=\"hidden\" name=\"Juprnumber" + i + "\" value=\"" + prNumber + "\">\n" );
            MsgTmp.append( "<input type=\"hidden\" name=\"line_num" + i + "\" value=\"" + lineItem + "\"></td>\n" );
            MsgTmp.append( "<input type=\"hidden\" name=\"MrQuantity" + i + "\" value=\"" + qty + "\">\n" );
            MsgTmp.append( "<input type=\"hidden\" name=\"receipt_id" + i + "\" value=\"" + receipt_id + "\"></td>\n" );

            if ( lastItemNum == 1 )
            {
              MsgTmp.append( "<input type=\"hidden\" name=\"Start" + prNumber + "" + lineItem + "\" value=\"" + i + "\">\n" );
            }

            MsgTmp.append( "<input type=\"hidden\" name=\"Stop" + prNumber + "" + lineItem + "\" value=\"" + i + "\">\n" );
            MsgTmp.append( "</TR>\n" );
            Msg.append( "<TR>\n" );
            Msg.append( "<td " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">\n" );
            Msg.append( "" + prNumber + " - " + lineItem + "</TD>\n" );
            Msg.append( "<td " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\" >" + qty + "</td>\n" );
            Msg.append( "</td>\n" );
            Msg.append( "<td " + Color + "l\" width=\"10%\" ROWSPAN=\"" + lastItemNum + "\">" + facility + "</td>\n" );
            Msg.append( "<td " + Color + "\" width=\"10%\" ROWSPAN=\"" + lastItemNum + "\">" + workArea + "</td>\n" );

            Msg.append( MsgTmp );
            MsgTmp=new StringBuffer();
            lastItemNum=1;
            InternalColor=0;

            ItemIdCount++;
            if ( ( ItemIdCount ) % 2 == 0 )
            {
              Color="CLASS=\"Inwhite";
            }
            else
            {
              Color="CLASS=\"Inblue";
              ButtonClass="buttonBlue";
            }

            continue;
          }

          InternalColor++;

          if ( !FirstRow )
          {
            MsgTmp.append( "<TR>\n" );
          }

          MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + issuer + "</td>\n" );
          MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + receipt_id + "</td>\n" );
          MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + datePicked + "</td>\n" );
          MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + receiptDate + "</td>\n" );
          MsgTmp.append( "<td " + Color + "\" width=\"10%\">" + qtyissued + "</td>\n" );
          MsgTmp.append( "<td " + Color + "r\" width=\"10%\">\n" );

          if ( "Y".equalsIgnoreCase( confirmed ) )
          {
            MsgTmp.append( "" + qtyissued + "\n" );
            MsgTmp.append( "<input type=\"hidden\" name=\"act_onhandOld" + i + "\" value=\"" + revisedIssue + "\">\n" );
            MsgTmp.append( "<input type=\"hidden\" name=\"rev_issue" + i + "\" value=\"" + revisedIssue + "\"></td>\n" );
            MsgTmp.append( "<td " + Color + "l\" width=\"2%\">&nbsp;</td>\n" );
          }
          else
          {
            MsgTmp.append( "<input type=\"text\" CLASS=\"DETAILS\" name=\"rev_issue" + i + "\"  value=\"" + revisedIssue + "\" maxlength=\"30\" size=\"6\" onChange=\"CheckIssueValue(name,this,'" + i + "')\"></td>\n" );
            MsgTmp.append( "<input type=\"hidden\" name=\"act_onhandOld" + i + "\" value=\"" + revisedIssue + "\"></td>\n" );
            MsgTmp.append( "<td " + Color + "l\" width=\"2%\">\n" );
            MsgTmp.append( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"+\" NAME=\"newPick" + i + "\" onclick= \"return UpdatenewPick(name,this," + i + ")\" " + disabeled + ">&nbsp;</TD>\n" );
          }

          MsgTmp.append( "<input type=\"hidden\" name=\"confirmed" + i + "\" value=\"" + confirmed + "\">\n" );
          MsgTmp.append( "<input type=\"hidden\" name=\"prnumber" + i + "\" value=\"" + prNumber + "" + lineItem + "\">\n" );
          MsgTmp.append( "<input type=\"hidden\" name=\"Juprnumber" + i + "\" value=\"" + prNumber + "\">\n" );
          MsgTmp.append( "<input type=\"hidden\" name=\"MrQuantity" + i + "\" value=\"" + qty + "\">\n" );
          MsgTmp.append( "<input type=\"hidden\" name=\"receipt_id" + i + "\" value=\"" + receipt_id + "\"></td>\n" );
          MsgTmp.append( "<input type=\"hidden\" name=\"line_num" + i + "\" value=\"" + lineItem + "\"></td>\n" );

          if ( lastItemNum == 2 )
          {
            MsgTmp.append( "<input type=\"hidden\" name=\"Start" + prNumber + "" + lineItem + "\" value=\"" + i + "\">\n" );
          }

          MsgTmp.append( "</TR>\n" );
        }

        Msg.append( "<TR ID=\"Totalsrow\" >\n" );
        Msg.append( "<td CLASS=\"greenr\" COLSPAN=\"8\">"+res.getString("label.total")+": </td>\n" );
        Msg.append( "<td CLASS=\"green\">" + recIssueTotal + "</td>\n" );
        Msg.append( "<td CLASS=\"green\"><B>" + recIssueTotal + "</B></td>\n" );
        Msg.append( "<td CLASS=\"green\">&nbsp;</td>\n" );
        Msg.append( "</TR>\n" );

        int target=recIssueTotal + ( tot_exp - tot_act );

        Msg.append( "<TR>\n" );
        Msg.append( "<td CLASS=\"greenr\" COLSPAN=\"9\">Target: </td>\n" );
        Msg.append( "<td CLASS=\"green\">" + target + "</td>\n" );
        Msg.append( "<td CLASS=\"green\">&nbsp;</td>\n" );
        Msg.append( "</TR>\n" );
        Msg.append( "<TR>\n" );
        Msg.append( "<TD CLASS=\"Inwhite\" COLSPAN=\"5\" WIDTH=\"50%\">\n" );
        Msg.append( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.ok")+"\" onclick= \"return updateIssue(this)\" NAME=\"SearchButton\">&nbsp;\n" );
        Msg.append( "</TD>\n" );
        Msg.append( "<TD CLASS=\"Inwhite\" COLSPAN=\"6\" WIDTH=\"50%\">\n" );
        Msg.append( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.cancel")+"\" onclick= \"return OldSearch(this)\" NAME=\"SearchButton\">&nbsp;\n" );
        Msg.append( "</TD>\n" );
        Msg.append( "</TR>" );
        Msg.append( "<TR><TD CLASS=\"Inwhite\" COLSPAN=\"11\" WIDTH=\"100%\" ALIGN=\"CENTER\" HEIGHT=\"1\">&nbsp;</TD>\n" );
        Msg.append( "</TR>" );
        Msg.append( "</TABLE>\n" );
        Msg.append( "<TD WIDTH=\"20%\" VALIGN=\"Top\" ALIGN=\"RIGHT\">\n" );
        Msg.append( "<TABLE  BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        Msg.append( "<TR>\n" );
        Msg.append( "<TH width=\"10%\"  height=\"38\">"+res.getString("label.receiptid")+"</TH>\n" );
        Msg.append( "<TH width=\"10%\" height=\"38\">"+res.getString("label.revisedonhand")+"</TH>\n" );
        Msg.append( "<TH width=\"10%\"  height=\"38\">"+res.getString("label.actualonhand")+"</TH>\n" );
        Msg.append( "</TR>\n" );

        i=0; //used for detail lines

        if ( total1 > 0 )
        {
          Msgd2.append( "var RevOnhand = {" );
        }

        int revOnHandTotal=0;
        int actOnHandTotal=0;
        String WhereReceipt="";

        for ( int loop=0; loop < total1; loop++ )
        {
          i++;

          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) SmallTableData.elementAt( i );

          //System.out.println( hD );
          String receipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
          String RevisedOnHand= ( hD.get( "EXPECTED_QUANTITY" ) == null ? "&nbsp;" : hD.get( "EXPECTED_QUANTITY" ).toString() );
          String ActOnHand= ( hD.get( "ACTUAL_QUANTITY" ) == null ? "&nbsp;" : hD.get( "ACTUAL_QUANTITY" ).toString() );

          revOnHandTotal+=Integer.parseInt( RevisedOnHand );
          actOnHandTotal+=Integer.parseInt( ActOnHand );

          if ( ! ( i == total1 ) )
          {
            Msgd2.append( "x" + receipt_id + ":" + RevisedOnHand + " ," );
            WhereReceipt+="" + receipt_id + ",";
          }
          else
          {
            Msgd2.append( "x" + receipt_id + ":" + RevisedOnHand + "};\n" );
            WhereReceipt+="" + receipt_id + "";
          }

          if ( ( loop ) % 2 == 0 )
          {
            Color="CLASS=\"Inblue";
          }
          else
          {
            Color="CLASS=\"Inwhite";
          }

          Msg.append( "<TR ID=\"x" + receipt_id + "\">\n" );
          Msg.append( "<td " + Color + "\" width=\"33%\" ALIGN=\"CENTER\">" + receipt_id + "</td>\n" );
          Msg.append( "<td " + Color + "\" width=\"33%\" ALIGN=\"CENTER\">" + RevisedOnHand + "</td>\n" );
          Msg.append( "<td " + Color + "\" width=\"33%\" ALIGN=\"CENTER\">" + ActOnHand + "</td>\n" );
          Msg.append( "<input type=\"hidden\" name=\"receipt_id" + receipt_id + "\" value=\"" + RevisedOnHand + "\"></td>\n" );
          Msg.append( "</TR>\n" );
        }

        Msg.append( "<TR ID=\"ReceiptTotalsrow\" >\n" );
        Msg.append( "<td CLASS=\"greenr\">Totals: </td>\n" );
        Msg.append( "<td CLASS=\"greenr\"><B>" + revOnHandTotal + "</B></td>\n" );
        Msg.append( "<td CLASS=\"green\"><B>" + actOnHandTotal + "</B></td>\n" );
        Msg.append( "<input type=\"hidden\" name=\"totalSideNumber\" value=\"" + total1 + "\"></td>\n" );
        Msg.append( "</TR>\n" );
        Msg.append( "</TD>\n" );
        Msg.append( "</TR>" );
        Msg.append( "</TABLE>\n" );
        Msg.append( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        Msg.append( "<TR><td>" );
        Msg.append( "<input type=\"hidden\" name=\"Total_number\" value=\"" + total + "\">\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"NewPickLine\" VALUE=\" \">\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"toRedirectItem\" VALUE=\"No\">\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"RedirectItemValue\" VALUE=\"\">\n" );
        Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"CancelUserAction\" VALUE=\"\">\n" );
        Msg.append( "</TD></TR>" );
        Msg.append( "</TABLE>\n" );
        Msgd2.append( " RevOnHandTotal = " + revOnHandTotal + ";\n" );
        Msgd2.append( "var RecMinConfQty = {" + arrayOFConfMinRecQTY( WhereReceipt ) + "}" );
        Msgd2.append( "//-->\n" );
        Msgd2.append( "</SCRIPT>\n" );
        Msg.append( Msgd2 );
        Msg.append( "</FORM>\n" );
        Msg.append( "</BODY></HTML>\n" );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "title.invrecon",intcmIsApplication,res ) );
      }

      return Msg.toString();
    }

    private String arrayOFConfMinRecQTY( String WhereReceipt1 )
    {
      String result="";
      DBResultSet dbrs=null;
      ResultSet rs=null;
      String ConfQuery="select receipt_id,QTY from unconfirmed_receipt_view where receipt_id in (" + WhereReceipt1 + ")";
      int countNo=0;

      try
      {
        dbrs=db.doQuery( ConfQuery );
        rs=dbrs.getResultSet();
        while ( rs.next() )
        {
          result+="x" + rs.getString( "RECEIPT_ID" ) + ":" + rs.getString( "QTY" ) + ",";
          countNo++;
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        dbrs.close();
      }

      if ( countNo == 0 )
      {
        result=" ";
      }
      else
      {
        result=result.substring( 0, ( result.length() - 1 ) );
      }

      return result;
    }


    private void buildHeader( Vector data,Collection hubInventoryGroupOvBeanCollection,String selHubnum,String sortby,String buildlabels,String category,
                                Vector countDateH,String Type,String searchstring,PrintWriter out,HttpSession session,String showproblemOnly,String selectedInventoryGroup)
    {
      //StringBuffer Msg=new StringBuffer();
      try
      {
        out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Inventory Reconciliation","reconciliation.js",intcmIsApplication ) );
        if ( "GENERATE_LABELS".equalsIgnoreCase( buildlabels ) )
        {
          out.println( "<BODY onLoad=\"doPopup()\">\n" );
        }
        else
        {
          out.println( "<BODY>\n" );
        }
        out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
        out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT><center>\n" );
        out.println( "</DIV>\n" );
        out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
        out.println( radian.web.HTMLHelpObj.PrintTitleBar( "<B>"+res.getString("title.invrecon2")+"</B>\n" ) );
		    if (hubInventoryGroupOvBeanCollection.size() < 1)
	      {
		      out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>"+res.getString("msg.noaccess")));
		      return;
	      }


	      out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
	      //out.println(radian.web.poHelpObj.createhubinvgrjs(initialinvData));
	      out.println(radian.web.poHelpObj.getHubInventoryGroupJavaScript( hubInventoryGroupOvBeanCollection,true,false));
	      out.println("// -->\n </SCRIPT>\n");

    String hubname=radian.web.HTMLHelpObj.getHubName( hubInventoryGroupOvBeanCollection,selHubnum );
		PersonnelBean personnelBean = session.getAttribute( "personnelBean" ) == null ? null : (PersonnelBean) session.getAttribute( "personnelBean" );
		if (personnelBean.getPermissionBean().hasFacilityPermission("inventoryReconciliation",hubname,null))
		{
		  this.setupdateStatus(true);
		}
		else
		{
		  this.setupdateStatus(false);
		}

        out.println( "<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Receiving_Servlet + "\">\n" );
        out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"5\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        out.println( "<TR>\n" );

        //Hub
        out.println( "<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Hub:</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\">\n" );
        out.println( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" onChange=\"RefreshPage(this)\" size=\"1\">\n" );
        if ( selHubnum.trim().length() == 0 )
		    {
		      selHubnum=radian.web.HTMLHelpObj.getFirstBranchPlant( hubInventoryGroupOvBeanCollection );
	      }
	      out.println(radian.web.HTMLHelpObj.getHubDropDown(hubInventoryGroupOvBeanCollection,selHubnum));        
        //Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		    //Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
	      //out.println(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),selHubnum,hub_list));
        out.println( "</SELECT>\n" );
        out.println( "</TD>\n" );

        // Count Start Date Time
        out.println( "<TD WIDTH=\"13%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>"+res.getString("label.countdate")+":</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"30%\">\n" );
        out.println( "<SELECT CLASS=\"HEADER\" NAME=\"SortBy\" size=\"1\">\n" );

        if ( countDateH.size() == 0 )
        {
          out.println( "<option value=\"0\">"+res.getString("label.pleasestartcount")+"</option>\n" );
        }
        else
        {
          if ( countDateH != null )
            out.println( radian.web.HTMLHelpObj.getDropDown( countDateH,sortby ) );
        }

        out.println( "</SELECT>\n" );
        out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMITW\" VALUE=\"\" onclick= \"return donothing(this)\" NAME=\"nothing\">\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">\n" );
        if ( this.getupdateStatus() )
        {
          out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.startnewcount")+"\" onclick= \"return StartNew(this)\" NAME=\"StartNewCount\">&nbsp;\n" );
        }
        else
        {
          out.println( "&nbsp;" );
        }
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
        out.println( "   <INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.search")+"\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "</TR>\n" );

        out.println( "<TR>\n" );
        out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>"+res.getString("label.inventorygroup")+":</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\">\n" );

        out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"5\" multiple>\n" );
        out.println(radian.web.HTMLHelpObj.getInventoryGroupDropDown(hubInventoryGroupOvBeanCollection,selHubnum,selectedInventoryGroup,false));     
        out.println( "</SELECT>\n" );
        out.println( "</TD>\n" );
        out.println( "</TR>\n" );
        
        out.println( "<TR>\n" );
        out.println( "<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
        out.println( "<B>"+res.getString("label.sortby")+":</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
        out.println( "<SELECT CLASS=\"HEADER\" NAME=\"Category\" size=\"1\">\n" );

        if ( category.equalsIgnoreCase( "2" ) )
        {
          out.println( "<option value=\"1\">"+res.getString("label.bin")+"</option>\n" );
          out.println( "<option selected value=\"2\">"+res.getString("label.itemid")+"</option>\n" );
        }
        else
        {
          out.println( "<option selected value=\"1\">"+res.getString("label.bin")+"</option>\n" );
          out.println( "<option value=\"2\">"+res.getString("label.itemid")+"</option>\n" );
        }
        out.println( "</SELECT>\n" );
        out.println( "</TD>\n" );

        // Search Text
        out.println( "<TD WIDTH=\"13%\" ALIGN=\"RIGHT\">\n" );
        out.println( "<B>"+res.getString("label.show")+":</B>&nbsp;</TD>\n" );
        out.println( "<TD WIDTH=\"30%\" ALIGN=\"LEFT\">\n" );
        out.println( "<SELECT CLASS=\"HEADER\" NAME=\"Type\" size=\"1\">\n" );
        if ( Type.equalsIgnoreCase( "Receipts" ) )
        {
          out.println( "<option  value=\"All\">"+res.getString("label.all")+"</option>\n" );
          out.println( "<option  value=\"Items\">"+res.getString("label.itemstoreconcile")+"</option>\n" );
          out.println( "<option  selected value=\"Receipts\">"+res.getString("label.receiptstoreconcile")+"</option>\n" );
          out.println( "<option  value=\"Item\">"+res.getString("label.item")+"</option>\n" );
          out.println( "<option  value=\"80per\">"+res.getString("label.80pctinventoryvalue")+"</option>\n" );
        }
        else if ( Type.equalsIgnoreCase( "Items" ) )
        {
          out.println( "<option value=\"All\">"+res.getString("label.all")+"</option>\n" );
          out.println( "<option selected value=\"Items\">"+res.getString("label.itemstoreconcile")+"</option>\n" );
          out.println( "<option value=\"Receipts\">"+res.getString("label.receiptstoreconcile")+"</option>\n" );
          out.println( "<option value=\"Item\">"+res.getString("label.item")+"</option>\n" );
          out.println( "<option value=\"80per\">"+res.getString("label.80pctinventoryvalue")+"</option>\n" );
        }
        else if ( Type.equalsIgnoreCase( "Item" ) )
        {
          out.println( "<option  value=\"All\">"+res.getString("label.all")+"</option>\n" );
          out.println( "<option  value=\"Items\">"+res.getString("label.itemstoreconcile")+"</option>\n" );
          out.println( "<option  value=\"Receipts\">"+res.getString("label.receiptstoreconcile")+"</option>\n" );
          out.println( "<option  selected value=\"Item\">"+res.getString("label.item")+"</option>\n" );
          out.println( "<option  value=\"80per\">"+res.getString("label.80pctinventoryvalue")+"</option>\n" );
        }
        else if ( Type.equalsIgnoreCase( "Bin" ) )
        {
          out.println( "<option  value=\"All\">"+res.getString("label.all")+"</option>\n" );
          out.println( "<option  value=\"Items\">"+res.getString("label.itemstoreconcile")+"</option>\n" );
          out.println( "<option  value=\"Receipts\">"+res.getString("label.receiptstoreconcile")+"</option>\n" );
          out.println( "<option  value=\"Item\">"+res.getString("label.item")+"</option>\n" );
          out.println( "<option  selected value=\"80per\">"+res.getString("label.80pctinventoryvalue")+"</option>\n" );
        }
        else if ( Type.equalsIgnoreCase( "80per" ) )
        {
          out.println( "<option selected value=\"All\">"+res.getString("label.all")+"</option>\n" );
          out.println( "<option  value=\"Items\">"+res.getString("label.itemstoreconcile")+"</option>\n" );
          out.println( "<option  value=\"Receipts\">"+res.getString("label.receiptstoreconcile")+"</option>\n" );
          out.println( "<option  value=\"Item\">"+res.getString("label.item")+"</option>\n" );
          out.println( "<option  selected value=\"80per\">"+res.getString("label.80pctinventoryvalue")+"</option>\n" );
        }
        else if ( Type.equalsIgnoreCase( "Receipts" ) )
        {
          out.println( "<option selected value=\"All\">"+res.getString("label.all")+"</option>\n" );
          out.println( "<option  value=\"Items\">"+res.getString("label.itemstoreconcile")+"</option>\n" );
          out.println( "<option  selected value=\"Receipts\">"+res.getString("label.receiptstoreconcile")+"</option>\n" );
          out.println( "<option  value=\"Item\">"+res.getString("label.item")+"</option>\n" );
          out.println( "<option  value=\"80per\">"+res.getString("label.80pctinventoryvalue")+"</option>\n" );
        }
        else
        {
          out.println( "<option selected value=\"All\">"+res.getString("label.all")+"</option>\n" );
          out.println( "<option  value=\"Items\">"+res.getString("label.itemstoreconcile")+"</option>\n" );
          out.println( "<option  value=\"Receipts\">"+res.getString("label.receiptstoreconcile")+"</option>\n" );
          out.println( "<option  value=\"Item\">"+res.getString("label.item")+"</option>\n" );
          out.println( "<option  value=\"80per\">"+res.getString("label.80pctinventoryvalue")+"</option>\n" );
        }
        out.println( "</SELECT>\n" );
        out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchstring\" value=\"" + searchstring + "\" size=\"10\">\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">\n" );
        out.println( "<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.createpdf")+"\" onclick= \"return createpdf(this)\" NAME=\"PdfButton\">&nbsp;\n" );
        out.println( "<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.createexcel")+"\" onclick= \"return createxls(this)\" NAME=\"XlsButton\">&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
        if ( this.getupdateStatus() )
        {
          out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.update")+"\" onclick= \"return update(this)\" NAME=\"UpdateButton\">&nbsp;\n" );
        }
        else
        {
          out.println( "&nbsp;\n" );
        }
        out.println( "</TD></TR>\n" );

     	  out.println( "<TR>\n" );
				out.println( "<TD WIDTH=\"7%\" colspan=\"3\" ALIGN=\"left\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
				out.println( "<B>"+res.getString("label.showproblemreceiptsonly")+"</B>&nbsp;\n" );
				out.println( "<INPUT type=\"checkbox\" name=\"showproblemOnly\" value=\"Y\" " + ( showproblemOnly.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + ">\n" );
				out.println( "</TD>\n" );

        out.println( "<TD WIDTH=\"7%\" colspan=\"2\" ALIGN=\"right\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
				out.println( "<B>"+res.getString("label.skipprintingreconhand")+"</B>&nbsp;\n" );
				out.println( "<INPUT type=\"checkbox\" name=\"skipreconhand\" id=\"skipreconhand\" value=\"Y\">\n" );
				out.println( "</TD>\n" );

        out.println( "</TABLE>\n" );

        //end table #1
        int total=0;
        if ( null == data )
        {
          total=0;
        }
        else
        {
          Hashtable summary=new Hashtable();
          summary= ( Hashtable ) data.elementAt( 0 );
          total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        }

        out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        out.println( "<TR><td>" );
        out.println( "<input type=\"hidden\" name=\"Total_number\" value=\"" + total + "\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"toRedirectItem\" VALUE=\"No\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"RedirectItemValue\" VALUE=\"\">\n" );
        out.println( "</TD></TR>" );
        out.println( "</TABLE>\n" );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "title.invrecon",intcmIsApplication,res ) );
      }

      return;
    }

    private void buildDetails( Vector data,String SortByLine,PrintWriter out,String showproblemOnly)
    {
      //StringBuffer Msg=new StringBuffer();

      try
      {
        if ( SortByLine.equalsIgnoreCase( "1" ) )
        {
          buildBinDetails( data,out,showproblemOnly );
        }
        else
        {
          out.println (buildItemDetails( data ) );
        }

        out.println( "</TABLE>\n" );
        out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        out.println( "<TR>" );
        out.println( "<TD HEIGHT=38 WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
        out.println( "</TD></TR>" );
        out.println( "</TABLE>\n" );
        out.println( "</form>\n" );
        out.println( "</body></html>\n" );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "title.invrecon",intcmIsApplication,res ) );
      }
      return;
    }

    private void buildBinDetails( Vector data,PrintWriter out ,String showproblemOnly)  throws Exception
    {
      //StringBuffer Msg=new StringBuffer();

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

        //start table #3
        out.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

        int i=0; //used for detail lines
				int lineCount=0; //used for detail lines
				//boolean buildHeader=true;
        for ( int loop=0; loop < total; loop++ )
        {
          i++;
          boolean createHdr=false;

          if ( (loop % 10 == 0) )
          {
            createHdr=true;
          }

          if ( createHdr )
          {
            out.println( "<tr align=\"center\">\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.inventorygroup")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.item")+"</TH>\n" );
            out.println( "<TH width=\"20%\" HEIGHT=\"38\">"+res.getString("label.description")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.packaging")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.po")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.unitcost")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.mfglot")+"</TH>\n" );
            out.println( "<TH width=\"5%\" HEIGHT=\"38\">"+res.getString("label.bin")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.lotstatus")+"</TH>\n" );
            out.println( "<TH width=\"5%\" HEIGHT=\"38\">"+res.getString("label.receiptid")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.recordedonhand")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.actualonhand")+"</TH>\n" );
            out.println( "</TR>\n" );
          }

          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          // get main information
          String Item_id= ( hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString() );
          String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString() );
          String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
          String bin= ( hD.get( "BIN" ) == null ? "&nbsp;" : hD.get( "BIN" ).toString() );
          String status= ( hD.get( "LOT_STATUS" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS" ).toString() );
          String receipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
          //String mfg= ( hD.get( "MANUFACTURER" ) == null ? "&nbsp;" : hD.get( "MANUFACTURER" ).toString() );
          String pkg= ( hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString() );
          String reconhand= ( hD.get( "ON_HAND" ) == null ? "" : hD.get( "ON_HAND" ).toString() );
          String actonHand= ( hD.get( "ACTUAL_ON_HAND" ) == null ? "" : hD.get( "ACTUAL_ON_HAND" ).toString() );
          String radianpo= ( hD.get( "RADIAN_PO" ) == null ? "&nbsp;" : hD.get( "RADIAN_PO" ).toString() );
          String unitCost= ( hD.get( "UNIT_COST" ) == null ? "&nbsp;" : hD.get( "UNIT_COST" ).toString() );
          String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : hD.get( "INVENTORY_GROUP" ).toString() );

          String Color=" ";

          if ( lineCount % 2 == 0 )
          {
            Color="CLASS=\"Inblue";
          }
          else
          {
            Color="CLASS=\"Inwhite";
          }

		      /*boolean showLine = true;
		      if (showproblemOnly.equalsIgnoreCase("Y"))
					{
					  //invlog.info(reconhand+" "+actonHand+" "+reconhand.equalsIgnoreCase(actonHand));
					  if (reconhand.equalsIgnoreCase(actonHand))
						{
						 showLine =false;
						 buildHeader = false;
		        }
						else
						{
						buildHeader = true;
						lineCount++;
		        }
       		}*/

		      //if (showLine)
					{
          out.println( "<tr align=\"center\">\n" );
          out.println( "<td " + Color + "l\" width=\"5%\">" + invengrp + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\">" + ( Item_id == null ? "&nbsp;" : Item_id ) + "</td>\n" );
          out.println( "<td " + Color + "l\" width=\"20%\">" + ( Item_desc == null ? "&nbsp;" : Item_desc ) + "</td>\n" );
          out.println( "<td " + Color + "l\" width=\"5%\">" + pkg + "</td>\n" );
          out.println( "<td " + Color + "l\" width=\"5%\">" + radianpo + "</td>\n" );
          out.println( "<td " + Color + "l\" width=\"5%\">" + unitCost + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\">" + Mfg_lot + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\">" + bin + "</td>\n" );
          out.println( "<td " + Color + "l\" width=\"5%\">" + status + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\">" + receipt_id + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\">" + reconhand + "</td>\n" );

          if ( this.getupdateStatus() )
          {
            out.println( "<td " + Color + "\" width=\"5%\">\n<input type=\"text\" CLASS=\"DETAILS\" name=\"act_onhand" + i + "\"  value=\"" + actonHand + "\" maxlength=\"30\" size=\"6\" onChange=\"CheckBinValue(name,this)\"></td>\n" );
          }
          else
          {
            out.println( "<td " + Color + "\" width=\"5%\">\n" + actonHand + "</td>\n" );
          }

          out.println( "<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + ( Item_id == null ? null : Item_id ) + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"item_id\" value=\"" + ( Item_id == null ? null : Item_id ) + "\">\n" );
          out.println( "</TR>\n" );
					}
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        throw e;
      }
      return;
    }

    private StringBuffer buildItemDetails( Vector data)  throws Exception
    {
      StringBuffer Msg=new StringBuffer();
      StringBuffer MsgTmp=new StringBuffer();
      String Color="CLASS=\"Inwhite";
      String ButtonClass="buttonWhite";

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

        Msg.append( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

        Hashtable hDNext=new Hashtable();
        int ItemIdCount=0;
        int lastItemNum=1;
        int CellColor=0;
        int InternalColor=0;
        int LineTotal=0;
        int LineActTotal=0;
        boolean FirstRow=false;
        boolean working=true;

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
            Msg.append( "<TH width=\"5%\" height=\"38\">"+res.getString("label.inventorygroup")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" height=\"38\">"+res.getString("label.item")+"</TH>\n" );
            Msg.append( "<TH width=\"20%\" HEIGHT=\"38\">"+res.getString("label.description")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" height=\"38\">"+res.getString("label.packaging")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" height=\"38\">"+res.getString("label.po")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" height=\"38\">"+res.getString("label.unitcost")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" height=\"38\">"+res.getString("label.mfglot")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" HEIGHT=\"38\">"+res.getString("label.bin")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" height=\"38\">"+res.getString("label.lotstatus")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" HEIGHT=\"38\">"+res.getString("label.receiptid")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" height=\"38\">"+res.getString("label.recordedonhand")+"</TH>\n" );
            Msg.append( "<TH width=\"5%\" height=\"38\">"+res.getString("label.actualonhand")+"</TH>\n" );
            Msg.append( "</TR>\n" );
          }

          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String Next_Item_id="";
          if ( ! ( i == total ) )
          {
            hDNext=new Hashtable();
            hDNext= ( Hashtable ) data.elementAt( i + 1 );
            Next_Item_id= ( hDNext.get( "ITEM_ID" ) == null ? "&nbsp;" : hDNext.get( "ITEM_ID" ).toString() );
          }
          else
          {
            Next_Item_id=" ";
          }

          // get main information
          String Item_id= ( hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString() );
          String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString() );
          String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
          String bin= ( hD.get( "BIN" ) == null ? "&nbsp;" : hD.get( "BIN" ).toString() );
          String status= ( hD.get( "LOT_STATUS" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS" ).toString() );
          String receipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
          //String mfg= ( hD.get( "MANUFACTURER" ) == null ? "&nbsp;" : hD.get( "MANUFACTURER" ).toString() );
          String pkg= ( hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString() );
          String reconhand= ( hD.get( "ON_HAND" ) == null ? "&nbsp;" : hD.get( "ON_HAND" ).toString() );
          String actonHand= ( hD.get( "ACTUAL_ON_HAND" ) == null ? "&nbsp;" : hD.get( "ACTUAL_ON_HAND" ).toString() );
          String radianpo= ( hD.get( "RADIAN_PO" ) == null ? "&nbsp;" : hD.get( "RADIAN_PO" ).toString() );
          String unitCost= ( hD.get( "UNIT_COST" ) == null ? "&nbsp;" : hD.get( "UNIT_COST" ).toString() );
          String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : hD.get( "INVENTORY_GROUP" ).toString() );

          int onHand=0;
          try
          {
            onHand=Integer.parseInt( reconhand );
          }
          catch ( Exception e )
          {
            onHand=0;
          }

          int actualonHand=0;

          try
          {
            actualonHand=Integer.parseInt( actonHand );
          }
          catch ( Exception e )
          {
            actualonHand=0;
          }

          boolean SameItem_id=false;
          if ( Item_id.equalsIgnoreCase( Next_Item_id ) )
          {
            SameItem_id=true;
            lastItemNum++;
            LineTotal+=onHand;
            LineActTotal+=actualonHand;
            working=true;
          }
          else
          {
            working=false;
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
            CellColor++;

            if ( ( !FirstRow ) && lastItemNum > 1 )
            {
              MsgTmp.append( "<TR>\n" );
            }

            MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + Mfg_lot + "</td>\n" );
            MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + bin + "</td>\n" );
            MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + status + "</td>\n" ); //12-17-02
            MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + receipt_id + "</td>\n" );
            MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + reconhand + "</td>\n" );

            MsgTmp.append( "<td " + Color + "\" width=\"5%\">\n" );
            if ( this.getupdateStatus() )
            {
              MsgTmp.append( "<input type=\"text\" CLASS=\"DETAILS\" name=\"act_onhand" + i + "\"  value=\"" + actonHand + "\" maxlength=\"30\" size=\"6\" onChange=\"CheckValue(name,this,'item_id" + i + "')\"></td>\n" );
            }
            else
            {
              MsgTmp.append( "" + actonHand + "</td>\n" );
            }

            MsgTmp.append( "<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + Item_id + "\">\n" );
            MsgTmp.append( "<input type=\"hidden\" name=\"item_id\" value=\"" + Item_id + "\">\n" );

            if ( lastItemNum == 1 )
            {
              MsgTmp.append( "<input type=\"hidden\" name=\"Start" + Item_id + "\" value=\"" + i + "\">\n" );
            }
            MsgTmp.append( "<input type=\"hidden\" name=\"Stop" + Item_id + "\" value=\"" + i + "\">\n" );
            MsgTmp.append( "</TR>\n" );

            Msg.append( "<TR>\n" );
            Msg.append( "<td " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + invengrp + "</td>\n" );
            Msg.append( "<td " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">\n" );
            if ( this.getupdateStatus() )
            {
              Msg.append( "<INPUT TYPE=\"submit\" VALUE=\"" + Item_id + "\" NAME=\"" + Item_id + "\" onclick= \"return updateItem(name,this,'item_id" + i + "')\" class=\"" + ButtonClass + "\">&nbsp;\n" );
            }
            else
            {
              Msg.append( "" + Item_id + "" );
            }
            Msg.append( "</td>\n" );

            Msg.append( "<td " + Color + "l\" width=\"20%\" ROWSPAN=\"" + lastItemNum + "\">" + ( Item_desc == null ? "&nbsp;" : Item_desc ) + "</td>\n" );

            Msg.append( "<td " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + pkg + "</td>\n" );
            Msg.append( "<td " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + radianpo + "</td>\n" );
            Msg.append( "<td " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + unitCost + "</td>\n" );

            Msg.append( MsgTmp );

            LineTotal+=onHand;
            LineActTotal+=actualonHand;

            Msg.append( "<TR ID=\"row" + Item_id + "\" >\n" );
						if (LineTotal == LineActTotal)
						{
						 Msg.append("<td CLASS=\"greenr\" COLSPAN=\"9\"><B>"+res.getString("label.total")+":</B></td>\n");
						 Msg.append("<td CLASS=\"green\">" + LineTotal + "</td>\n");
						 Msg.append("<td CLASS=\"greenl\"><B>" + LineActTotal + "</B></td>\n");
						}
						else
						{
						Msg.append( "<td CLASS=\"redr\" COLSPAN=\"9\"><B>"+res.getString("label.total")+":</B></td>\n" );
						Msg.append( "<td CLASS=\"red\">" + LineTotal + "</td>\n" );
						Msg.append( "<td CLASS=\"redl\"><B>" + LineActTotal + "</B></td>\n" );
						}

            Msg.append( "</TR>\n" );

            MsgTmp=new StringBuffer();
            lastItemNum=1;
            InternalColor=0;
            LineTotal=0;
            LineActTotal=0;

            ItemIdCount++;
            if ( ( ItemIdCount ) % 2 == 0 )
            {
              Color="CLASS=\"Inwhite";
              ButtonClass="buttonWhite";
            }
            else
            {
              Color="CLASS=\"Inblue";
              ButtonClass="buttonBlue";
            }

            continue;
          }

          InternalColor++;

          if ( !FirstRow )
          {
            MsgTmp.append( "<TR>\n" );
          }

          MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + Mfg_lot + "</td>\n" );
          MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + bin + "</td>\n" );
          MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + status + "</td>\n" );
          MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + receipt_id + "</td>\n" );
          MsgTmp.append( "<td " + Color + "\" width=\"5%\">" + reconhand + "</td>\n" );

          MsgTmp.append( "<td " + Color + "\" width=\"5%\">\n" );
          if ( this.getupdateStatus() )
          {
            MsgTmp.append( "<input type=\"text\" CLASS=\"DETAILS\" name=\"act_onhand" + i + "\"  value=\"" + actonHand + "\" maxlength=\"30\" size=\"6\" onChange=\"CheckValue(name,this,'item_id" + i + "')\"></td>\n" );
          }
          else
          {
            MsgTmp.append( "" + actonHand + "</td>\n" );
          }

          MsgTmp.append( "<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + Item_id + "\">\n" );
          MsgTmp.append( "<input type=\"hidden\" name=\"item_id\" value=\"" + Item_id + "\">\n" );

          if ( lastItemNum == 2 )
          {
            MsgTmp.append( "<input type=\"hidden\" name=\"Start" + Item_id + "\" value=\"" + i + "\">\n" );
          }
          MsgTmp.append( "</TR>\n" );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        throw e;
      }
      return Msg;
    }
  }
//END OF CLASS

