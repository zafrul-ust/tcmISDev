package radian.web.repackaging;

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
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 05-27-04 - Showing the REPACKAGE_QUANTITY_AVAILABLE
 * 07-21-04 - Modifyed to work with a ned method decleration in pohelp obj to get indevntory groups
 * 02-28-05 Giving the option to not print Kit/Case Qty Labels.
 */

public class rePackaging
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private Vector receiptIdstoLabel = null;
    private Vector numbersoflabels = null;
    private dispensingHelpobj  hubObj = null;
    private PrintWriter out = null;
		private boolean oneSuccess = false ;
    private boolean completeSuccess = true ;
    private boolean noneToUpd = true ;
    private int AddNewBinLineNum;
    private boolean allowupdate;
    private static org.apache.log4j.Logger repacklog = org.apache.log4j.Logger.getLogger(rePackaging.class);
	private boolean intcmIsApplication = false;
	private ResourceLibrary res = null; // res means resource.


    public rePackaging(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    //Nawaz 05-02-02
   private void setAddBinLineNum(int id)
  {
    this.AddNewBinLineNum = id;
  }

  private int getAddBinLineNum()  throws Exception
  {
   return this.AddNewBinLineNum;
  }

  //Nawaz 06-24-02
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
        hubObj = new dispensingHelpobj(db);
        out = response.getWriter();
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));

        String branch_plant= session.getAttribute( "BRANCH_PLANT" ) == null ? "" : session.getAttribute( "BRANCH_PLANT" ).toString();
        String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
				PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
				Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
				if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
				{
				 intcmIsApplication = true;
				}

        Collection hubInventoryGroupOvBeanCollection = new Vector((Collection)session.getAttribute("hubInventoryGroupOvBeanCollection"));
        DBResultSet receiptdbrs = null;
        ResultSet receiptrs = null;

        String User_Search = null;
        String User_Action = null;
        String SubUser_Action = null;
        String User_Session = "";
        String User_Sort    = "";
        String generate_labels = "";

        User_Session= request.getParameter("session");
        if (User_Session == null)
          User_Session = "New";
        User_Action= request.getParameter("UserAction");
        if (User_Action == null)
          User_Action = "New";
        generate_labels = request.getParameter("generate_labels");
        if (generate_labels == null)
          generate_labels ="No";

        User_Search = request.getParameter("SearchField");
        if (User_Search == null)
              User_Search = "";
        User_Sort     = request.getParameter("SortBy");
        if (User_Sort == null)
              User_Sort = "1";

        String invengrp=request.getParameter( "invengrp" );
        if ( invengrp == null )
          invengrp="";
        invengrp=invengrp.trim();

		String paper_size=request.getParameter( "paperSize" );
		if ( paper_size == null )
		  paper_size="31";
		paper_size=paper_size.trim();

		String printKitLabels=request.getParameter( "printKitLabels" );
		if ( printKitLabels == null )
		  printKitLabels="";

        String addbin_bin_id = "";
        String addbin_item_id = "";
        String dupl_line = "";

        try
        {
		  Hashtable hub_list_set=new Hashtable();
		  hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

	        if ( User_Action.equalsIgnoreCase("New") )
            {
                Vector lot_status_set11=radian.web.HTMLHelpObj.createItemStatusSet( db );
                session.setAttribute( "STATUS_SET",lot_status_set11 );
                session.setAttribute( "HUB_COMPANY_IDS",radian.web.HTMLHelpObj.createCompanylist( db ) );

                String CompanyID = session.getAttribute("COMPANYID").toString();
                /*Hashtable hub_list_set = new Hashtable();

                if (this.getupdateStatus())
                {
                   hub_list_set  = radian.web.HTMLHelpObj.createHubList(db,"Repackaging",personnelid,CompanyID);
                }
                else
                {
                   hub_list_set  = radian.web.HTMLHelpObj.createAllHubList(db,CompanyID);
                }*/

		        Hashtable hub_list1 = (Hashtable)(hub_list_set.get("HUB_LIST"));
		        /*Hashtable initialinvData = new Hashtable ();

		        if ( hub_list1.size() > 0 )
		        {
		         initialinvData=radian.web.poHelpObj.getinvgrpInitialData( db,hub_list_set,false,false );
		         session.setAttribute( "REPACK_INVENGRP_DATA",initialinvData );
		        }*/
                //Hashtable hub_list1 = (Hashtable)(hub_list_set.get("HUB_LIST"));

                if (hubInventoryGroupOvBeanCollection.size() < 1)
                {
                buildHeader(null, hub_list_set, "", User_Search, User_Sort, "",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);
                out.println(radian.web.HTMLHelpObj.printHTMLNoFacilities(res));
                hub_list_set  = null;
                out.close();
                }
                else
                {
                //session.setAttribute("REPACK_HUB_SET", hub_list_set );
                buildHeader(null, hub_list_set, "", User_Search, User_Sort, "",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);
                out.println(radian.web.HTMLHelpObj.printHTMLSelect(res));
                out.close();
                hub_list_set  = null;
                }
            }
            else if ( User_Action.equalsIgnoreCase("search") )
            {
                //Hashtable hub_list_set  = (Hashtable)session.getAttribute("REPACK_HUB_SET");
                //Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "REPACK_INVENGRP_DATA" );
                Vector openorder_data = new Vector();
                if (branch_plant.trim().length() > 0)
                {
                  openorder_data  = hubObj.getAllopenOrder( branch_plant , User_Search, User_Sort,invengrp);

                  Vector openbinSet = new Vector();
                  openbinSet =radian.web.HTMLHelpObj.createEmptyBinSet( db,branch_plant );
                  session.setAttribute("EMPTYBIN", openbinSet );
                  session.setAttribute("REPACK_UPDATEDONE", "NO");

                  Hashtable sum = ( Hashtable)( openorder_data.elementAt(0));
                  int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();

                  if ( 0 == count )
                  {
                      buildHeader(null,hub_list_set, branch_plant,User_Search, User_Sort , "",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);
                      out.println(radian.web.HTMLHelpObj.printHTMLNoData(res.getString("msg.noitemfound")));
                      out.close();
                      //clean up
                      openorder_data  = null;
                      hub_list_set  = null;
                  }
                  else
                  {
					Hashtable in_bin_set=new Hashtable();
					Hashtable out_bin_set2=createItemBinSet( openorder_data,branch_plant );

			        session.setAttribute( "REPACKAGE_BIN_SET",out_bin_set2 );

				    session.setAttribute("REPACK_DATA", openorder_data );
                    session.setAttribute("REPACK_SEARCHED_HUB", branch_plant );
                    session.setAttribute("REPACK_SEARCHED_STRING", User_Search );
					session.setAttribute("REPACK_SEARCHED_IG", invengrp );
					session.setAttribute("REPACK_SEARCHED_SORT", User_Sort );

                    buildHeader(openorder_data, hub_list_set,branch_plant, User_Search, User_Sort, "",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);

                    out.println(buildDetails(openorder_data,out_bin_set2,branch_plant,"NONE"));

                    out.close();
                    //clean up
                    openorder_data  = null;
                    hub_list_set  = null;
                  }  //when there are open orders for hub
                }
                else
                {
                    buildHeader(null,hub_list_set, branch_plant,User_Search, User_Sort , "",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);
                    out.println(radian.web.HTMLHelpObj.printHTMLNoData(res.getString("label.pleaseselectahub")));
                    out.close();
                    //clean up
                    openorder_data  = null;
                    hub_list_set  = null;
                }
            }
            else if ( User_Action.equalsIgnoreCase("Update") )
            {
                SubUser_Action= request.getParameter("SubUserAction");
                if (SubUser_Action == null)
                  SubUser_Action = "JSError2";

                Vector retrn_data = (Vector) session.getAttribute("REPACK_DATA");
                Vector synch_data =  SynchServerData( request, retrn_data);
                retrn_data = null;

				if ( SubUser_Action.equalsIgnoreCase("AddBin"))
				{
				  String AddBin_Line_No="0";
				  addbin_item_id=request.getParameter( "AddBin_Item_Id" );
				  if ( addbin_item_id == null )
					addbin_item_id="";
				  addbin_bin_id=request.getParameter( "AddBin_Bin_Id" );
				  if ( addbin_bin_id == null )
					addbin_bin_id="";
				  AddBin_Line_No=request.getParameter( "AddBin_Line_No" );
				  if ( AddBin_Line_No == null )
					AddBin_Line_No="0";

				  setAddBinLineNum( Integer.parseInt( AddBin_Line_No ) );

				  Hashtable all_bin_set_a= ( Hashtable ) session.getAttribute( "REPACKAGE_BIN_SET" );
				  //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "REPACK_HUB_SET" );
				  //Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "REPACK_INVENGRP_DATA" );
				  Hashtable all_new_bin_set=addToItemBinSet( addbin_item_id,addbin_bin_id,all_bin_set_a );
				  //
				  session.setAttribute( "REPACK_DATA",synch_data );

				  Hashtable sum= ( Hashtable ) ( synch_data.elementAt( 0 ) );
				  int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

				  buildHeader(synch_data ,hub_list_set,branch_plant,User_Search, User_Sort,"",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);
				  out.println(buildDetails(synch_data,all_new_bin_set,branch_plant,addbin_item_id));
				  out.close();
				  //clean up
				  synch_data=null;
				  all_new_bin_set=null;
				  all_bin_set_a=null;
				  //
				}
                else if ( SubUser_Action.equalsIgnoreCase("UpdPage") || SubUser_Action.equalsIgnoreCase("generatelabels"))
                {
                    Hashtable temp1        = (Hashtable)session.getAttribute("REPACKAGE_BIN_SET");
                    //Hashtable hub_list_set = (Hashtable)session.getAttribute("REPACK_HUB_SET");
                    //Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "REPACK_INVENGRP_DATA" );
                    //
                    session.setAttribute("REPACK_DATA", synch_data );
                    Hashtable updateresults = UpdateDetails(synch_data, personnelid,SubUser_Action);
                    session.setAttribute("REPACK_UPDATEDONE", "YES");

                    Boolean resultfromup = (Boolean)updateresults.get("RESULT");
                    Vector error1data = (Vector)updateresults.get("ERRORDATA");
                    Vector errMsgs  = hubObj.getErrMsgs();

					Vector openorder_data  = hubObj.getAllopenOrder( session.getAttribute("REPACK_SEARCHED_HUB").toString() , session.getAttribute("REPACK_SEARCHED_STRING").toString(), session.getAttribute("REPACK_SEARCHED_SORT").toString(),session.getAttribute("REPACK_SEARCHED_IG").toString());

                    session.setAttribute("REPACK_DATA", openorder_data );

                    boolean resulttotest =  resultfromup.booleanValue();

                    //12-06-01
                    session.setAttribute("REPACK_LABEL_SEQUENCE_NUMBERS", receiptIdstoLabel);
                    session.setAttribute("REPACK_LABEL_QUANTITYS", numbersoflabels);

					if (oneSuccess == true)
					{
					  SubUser_Action = "generatelabels";
		            }

                    if ( false == resulttotest )
                    {
                        if (receiptIdstoLabel.size() > 0 && SubUser_Action.equalsIgnoreCase("generatelabels"))
                        {buildHeader(synch_data ,hub_list_set,branch_plant,User_Search, User_Sort,"GENERATE_LABELS",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);}
                        else
                        {
                        buildHeader(synch_data ,hub_list_set,branch_plant,User_Search, User_Sort,"",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);
                        }

                        if ( true == noneToUpd )
                        {
                           out.println(radian.web.HTMLHelpObj.printMessageinTable(res.getString("err.noupdategen")));
                        }
                        else
                        {
                           out.println(radian.web.HTMLHelpObj.printMessageinTable(res.getString("msg.tcmiserror")));
                        }
                        out.println(buildDetails(openorder_data,temp1,branch_plant,"NONE"));
                        out.close();
                    }
                    else
                    {
                       if ( true == completeSuccess )
                       {
                            if (SubUser_Action.equalsIgnoreCase("generatelabels"))
                            {buildHeader(synch_data ,hub_list_set,branch_plant,User_Search, User_Sort,"GENERATE_LABELS",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);}
                            else
                            {buildHeader(synch_data ,hub_list_set,branch_plant,User_Search, User_Sort,"",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);}

                            out.println(radian.web.HTMLHelpObj.printMessageinTable(res.getString("msg.updatesuccess")));

                            out.println(buildDetails(openorder_data,temp1,branch_plant,"NONE"));
                            out.close();
                       }
                       else
                       {
                            if (SubUser_Action.equalsIgnoreCase("generatelabels"))
                            {buildHeader(synch_data ,hub_list_set,branch_plant,User_Search, User_Sort,"GENERATE_LABELS",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);}
                            else
                            {buildHeader(synch_data ,hub_list_set,branch_plant,User_Search, User_Sort,"",hubInventoryGroupOvBeanCollection,invengrp,paper_size,session,printKitLabels);}

                            out.println(radian.web.HTMLHelpObj.printHTMLPartialSuccess(errMsgs));
                            out.println(buildDetails(openorder_data,temp1,branch_plant,"NONE"));
                            out.close();
                       }
                    }
                    //clean up
                    synch_data        = null;
                    temp1             = null;
                    //
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            out.println(radian.web.HTMLHelpObj.printError("repackaging",intcmIsApplication,res));
        }

        //clean up
        hubObj = null;
        return;
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

		   String prnumber =hD.get( "PR_NUMBER" ).toString();
		   String startmrline=BothHelpObjs.makeBlankFromNull( request.getParameter( "Start" + prnumber ) );
		   String stopmrline=BothHelpObjs.makeBlankFromNull( request.getParameter( "Stop" + prnumber ) );

		   boolean updatestatus=false;
		   for ( int j=Integer.parseInt( startmrline ); j <= Integer.parseInt( stopmrline ); j++ )
		   {
			 Hashtable hD1=new Hashtable();
			 hD1= ( Hashtable ) ( in_data.elementAt( j ) );

			 String qtypicked1="";
			 qtypicked1=BothHelpObjs.makeBlankFromNull( request.getParameter( "qty_picked" + j ) );
			 if ( qtypicked1.trim().length() > 0 )
			 {
			   updatestatus=true;
			 }
		   }

		   if ( updatestatus )
		   {
			 hD.remove( "DOUPDATE" );
			 hD.put( "DOUPDATE","Yes" );
		   }

		   String qtypicked="";
		   qtypicked=BothHelpObjs.makeBlankFromNull( request.getParameter( "qty_picked" + i ) );
		   hD.remove( "QUANTITY_PICKED" );
		   hD.put( "QUANTITY_PICKED",qtypicked.trim() );

		   String bin_name="";
		   bin_name=request.getParameter( ( "selectElementBin" + i ) );
		   if ( bin_name == null )
			 bin_name="";
		   if ( !bin_name.equalsIgnoreCase( hD.get( "BIN" ).toString() ) )
		   {
			 hD.remove( "BIN" );
			 hD.put( "BIN",bin_name );
		   }

		   new_data.addElement( hD );
		 }
	   }
	   catch ( Exception e )
	   {
		 e.printStackTrace();
		 out.println( radian.web.HTMLHelpObj.printError( "pickingqc.title",intcmIsApplication,res ) );
	   }

	   return new_data;
	 }

	 private Hashtable addToItemBinSet(String item_id, String bin_id, Hashtable in_set)
		 {
			 Hashtable bin_set = new Hashtable();
			 try
			 {
				 bin_set = (Hashtable)in_set.get(item_id);
				 int size = bin_set.size();
				 if ( ( size == 1) && bin_set.containsValue("NONE"))
				 {
				   bin_set.remove( new Integer(0));
				   bin_set.put( new Integer(0) , bin_id );
				 }
				 else
				 {
				   bin_set.put( new Integer(size) , bin_id );
				 }
				 in_set.put(item_id, bin_set);
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
				 out.println(radian.web.HTMLHelpObj.printError("repackaging",intcmIsApplication,res));
			 }
			 return in_set;
		 }

		 private Hashtable createItemBinSet(Vector data, String branch_plant )
		 {
			 String item_id ;
			 Hashtable bin_set = new Hashtable();
			 Hashtable sum = ( Hashtable)( data.elementAt(0));
			 int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();

			 try
			 {
				 for ( int i = 1 ; i< count+1 ; i++)
				 {
					 Hashtable hD = new Hashtable();
					 hD = ( Hashtable)( data.elementAt(i));
					 item_id = (String)hD.get("ITEM_ID");
					 if ( false == bin_set.containsKey(item_id))
					 {
							 Hashtable bin_for_item =radian.web.HTMLHelpObj.CreateBinForItem( db,item_id,branch_plant );
							 bin_set.put( item_id, bin_for_item);
					 }
				 }
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
				 out.println(radian.web.HTMLHelpObj.printError("repackaging",intcmIsApplication,res));
			 }

			 return bin_set;
		 }

    private Hashtable UpdateDetails(Vector data, String logonid, String subuseraction)
    {
        boolean result = false;
        Hashtable updateresult = new Hashtable();
        Vector errordata = new Vector();
        receiptIdstoLabel = new Vector();
        numbersoflabels = new Vector();

        try
        {
            Hashtable summary = new Hashtable();
            summary = (Hashtable)data.elementAt(0);
            int total = ((Integer)(summary.get("TOTAL_NUMBER"))).intValue()  ;
            errordata.addElement(summary);

            int linecheckcount = 0;
            boolean one_success = false;
            for (int i = 1; i < total+1  ; i++)
            {
                Hashtable hD = new Hashtable();
                hD = (Hashtable) data.elementAt(i);
                String Line_Check = "";
                String dostatusupdate = "";

                Line_Check      =  (hD.get("USERCHK")==null?" ":hD.get("USERCHK").toString());
                String qtypicked=hD.get( "QUANTITY_PICKED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_PICKED" ).toString();
				String receipt_id=hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString();

                if ("yes".equalsIgnoreCase(Line_Check) && (hD.get("NO_OF_LABELS").toString().length() > 0) )
                {
                    numbersoflabels.addElement(hD.get("NO_OF_LABELS").toString());
                    receiptIdstoLabel.addElement(hD.get("RECEIPT_ID").toString());
                }

                if (this.getupdateStatus() && qtypicked.length() > 0 && receipt_id.length() >0 &&  !("generatelabels".equalsIgnoreCase(subuseraction)) )
                {
                    noneToUpd = false;
                    linecheckcount++;
                    boolean line_result = false;
					Hashtable ResultH=new Hashtable();

                    ResultH = hubObj.UpdateLine( hD, logonid ); // update database
					Boolean resultline= ( Boolean ) ResultH.get( "RESULT" );
					line_result=resultline.booleanValue();
					String returnedReceiptId1=ResultH.get( "RCECIPT_ID1" ).toString();

                    if ( false == line_result)
                    {
                      completeSuccess = false;

                      hD.remove("USERCHK");
                      hD.put("USERCHK", " ");

                      hD.remove("UPDATEDONE");
                      hD.put("UPDATEDONE", "NO");

                      errordata.addElement(hD);
                    }
                    if ( true == line_result)
                    {
                    one_success = true;
					oneSuccess = true;
			        numbersoflabels.addElement(qtypicked);
					receiptIdstoLabel.addElement(returnedReceiptId1);

                    hD.remove("UPDATEDONE");
                    hD.put("UPDATEDONE", "YES");

                    errordata.addElement(hD);
                    }
              }
              else
              {
               errordata.addElement(hD);
              }
            } //end of for

            if (linecheckcount == 1){result = true;}
            if ( true == one_success )
            {
            result = true;
            }
        }
        catch (Exception e)
        {
            result = false;
            e.printStackTrace();
            out.println(radian.web.HTMLHelpObj.printError("repackaging",intcmIsApplication,res));
        }

        updateresult.put("RESULT",new Boolean(result));
        updateresult.put("ERRORDATA",errordata);

        return updateresult;
    }

	private void buildHeader( Vector data,Hashtable hub_list_set,String hub_branch_id,String search_text,String sortby,String buildlabels,
								Collection hubInventoryGroupOvBeanCollection,String selinvengrp,String paper_size,HttpSession session,String printKitLabels )
	{
        //StringBuffer Msg = new StringBuffer();
        try
        {
          out.println(radian.web.HTMLHelpObj.printHTMLHeader("repackaging","repackaging.js",intcmIsApplication,res));

          out.println("<SCRIPT SRC=\"/clientscripts/invengrpdropdown.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
		  out.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
          out.println("</HEAD>  \n");

          if ("GENERATE_LABELS".equalsIgnoreCase(buildlabels))
          {
          out.println("<BODY onLoad=\"dotaplabelsPopup()\">\n");
          }
          else
          {
          out.println("<BODY>\n");
          }

          out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
          out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT><center>\n");
          out.println("</DIV>\n");
          out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

          out.println(radian.web.HTMLHelpObj.PrintTitleBar("<B>"+res.getString("repackaging")+"</B>\n"));
		  //Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		  if (hubInventoryGroupOvBeanCollection.size() < 1)
		  {
			out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>"+res.getString("msg.noaccess")));
			return;
		  }
		  out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
		  //out.println(radian.web.poHelpObj.createhubinvgrjs(initialinvData));
      out.println(radian.web.poHelpObj.getHubInventoryGroupJavaScript( hubInventoryGroupOvBeanCollection,true,false));
      out.println("// -->\n </SCRIPT>\n");

		  /*Vector hubid = ( Vector ) initialinvData.get( "HUB_ID" );
		  if ( hub_branch_id.trim().length() == 0 )
		  {
		   hub_branch_id =  ( String ) hubid.firstElement();
		  }*/
      if ( hub_branch_id.trim().length() == 0 )
      {
       hub_branch_id=radian.web.HTMLHelpObj.getFirstBranchPlant( hubInventoryGroupOvBeanCollection );
      }

      String hubname = radian.web.HTMLHelpObj.gethubNamefromBP(hub_list_set,hub_branch_id);
		  PersonnelBean personnelBean = session.getAttribute( "personnelBean" ) == null ? null : (PersonnelBean) session.getAttribute( "personnelBean" );
		  if (personnelBean.getPermissionBean().hasFacilityPermission("Repackaging",hubname,null))
		  {
			this.setupdateStatus(true);
		  }
		  else
		  {
			this.setupdateStatus(false);
		  }

          out.println("<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+radian.web.tcmisResourceLoader.getrepackagingurl()+"\">\n");
          out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n");

          out.println("<TR>\n");

          //Hub
          out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
          out.println("<B>"+res.getString("label.hub")+":</B>&nbsp;\n");
          out.println("</TD>\n");
          out.println("<TD WIDTH=\"10%\">\n");
          //
          out.println("<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" size=\"1\" OnChange=\"hubchanged(document.receiving.HubName)\">\n");

          //Hashtable hub_list = (Hashtable)(hub_list_set.get("HUB_LIST"));
          /*String hub_selected ;
          for (Enumeration e = hub_list.keys() ; e.hasMoreElements() ;)
          {
              String branch_plant =  (String)(e.nextElement());
              String hub_name     =  (String)(hub_list.get(branch_plant));
              //
              hub_selected = "";
              if ( branch_plant.equalsIgnoreCase(hub_branch_id))
              {
                   hub_selected = "selected";
              }
              out.println("<option  "+ hub_selected + " value=\""+branch_plant+"\">"+hub_name+"</option>\n");
          }*/
    	  //Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
		  //out.println(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),hub_branch_id,hub_list));


	       out.println(radian.web.HTMLHelpObj.getHubDropDown(hubInventoryGroupOvBeanCollection,hub_branch_id));

          out.println("</SELECT>\n");
          out.println("</TD>\n");

          out.println("</TD>\n");
          out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
          out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\""+search_text+"\" size=\"19\">\n");
          out.println("</TD>\n");

		  // Search
		  out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		  out.println("   <INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.search")+"\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n");

          //Process
          out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
          if (this.getupdateStatus())
          {
          out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.process")+"\" onclick= \"return update(this)\" NAME=\"UpdateButton\">&nbsp;\n");
          }
          else
          {
          out.println("&nbsp;\n");
          }
          out.println("</TD>\n");

          /*//Close
          out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		  if (this.getupdateStatus())
          {
          //out.println("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onclick= \"return closetap(this)\" NAME=\"CloseTapButton\">&nbsp;\n");
		  }
		  else
		  {
		  out.println("&nbsp;\n");
		  }
		  out.println("</TD>\n");*/


		 out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		 out.println(""+res.getString("label.atprocessgenlabelsas")+"\n");
		 out.println("</TD>\n");

		 out.println( "<TD WIDTH=\"1%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		 out.println( "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + ( printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
		 out.println( "</TD>\n" );
		 out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		 out.println( "&nbsp;"+res.getString("receivedreceipts.label.skipkitlabels")+"\n" );
		 out.println( "</TD>\n" );

          out.println("</TR>\n");

          out.println( "<TR VALIGN=\"MIDDLE\">\n" );
          // Inventory Group
          out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
          out.println( "<B>"+res.getString("label.invgrp")+":</B>&nbsp;\n" );
          out.println( "</TD>\n" );
          out.println( "<TD WIDTH=\"10%\">\n" );
          out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n" );
          out.println(radian.web.HTMLHelpObj.getInventoryGroupDropDown(hubInventoryGroupOvBeanCollection,hub_branch_id,selinvengrp,false));

/*          Hashtable fh= ( Hashtable ) initialinvData.get( hub_branch_id );
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
            out.println( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + "</option>\n" );
          }*/
          out.println( "</SELECT>\n" );
          out.println( "</TD>\n" );

		  //Force Repackaging
          out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
		  if (this.getupdateStatus())
		  {
			out.println("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.forcerepackaging")+"\" onclick= \"return forcerepack(this)\" NAME=\"RepackageButton\">&nbsp;\n");
		  }
		  else
		  {
			out.println("&nbsp;\n");
		  }
		  out.println("</TD>\n");

          out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\">&nbsp;</TD>\n");

		  /*//Tap
		  out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		  if (this.getupdateStatus())
		  {
			out.println("<INPUT TYPE=\"BUTTON\" ID=\"genbutton4\" VALUE=\"Tap\" NAME=\"SearchButton\" onclick= \"javascript:startnewtap()\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
		  }
		  else
		  {
			out.println("&nbsp;\n");
		  }
		  out.println("</TD>\n");*/

	      //Undo Tap
		  out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		  if (this.getupdateStatus())
		  {
			out.println("<INPUT TYPE=\"BUTTON\" ID=\"genbutton5\" VALUE=\""+res.getString("label.undotap")+"\" NAME=\"undoTapButton\" onclick= \"javascript:undotap()\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
		  }
		  else
		  {
			out.println("&nbsp;\n");
		  }
		  out.println("</TD>\n");

		  out.println("<TD WIDTH=\"5%\" COLSPAN=\"3\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		  out.println("<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('31')\" value=\"31\" "+("31".equalsIgnoreCase(paper_size)?"checked":"")+">3''/1''&nbsp;\n");
		  out.println("<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('811')\" value=\"811\" "+("811".equalsIgnoreCase(paper_size)?"checked":"")+">8.5''/11''\n");
		  out.println("</TD>\n");

          out.println("</TR>\n");

          out.println("</TABLE>\n");

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

          out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n");
          out.println("<tr><td>");
          out.println("<input type=\"hidden\" name=\"Total_number\" value=\""+total+"\">\n");
          out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n");
		  out.println( "<INPUT TYPE=\"hidden\" NAME=\"DuplLineNumber\" VALUE=\" \">\n" );
		  out.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n");
		  out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Item_Id\" VALUE=\" \">\n" );
		  out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Bin_Id\" VALUE=\" \">\n" );
		  out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Line_No\" VALUE=\" \">\n" );
          out.println("<INPUT TYPE=\"hidden\" NAME=\"HubRef\" VALUE=\""+hub_branch_id+"\">\n");
		  out.println("<INPUT TYPE=\"hidden\" NAME=\"Paper\" VALUE=\""+paper_size+"\">\n");
          out.println("</TD></tr>");
          out.println("</table>\n");
    }
    catch (Exception e)
    {
        e.printStackTrace();
        out.println(radian.web.HTMLHelpObj.printError("repackaging",intcmIsApplication,res));
    }

    return;
}

  private String buildDetails( Vector data,Hashtable all_bin_set,String HubNo,String Add_Item_id )
  {
	StringBuffer Msg=new StringBuffer();
	StringBuffer MsgTmp1=new StringBuffer();
	StringBuffer MsgTmp=new StringBuffer();
	StringBuffer MsgTmpDate=new StringBuffer();
	Hashtable hDNext=new Hashtable();
	String checkednon="";
	String Color="CLASS=\"Inwhite";


	try
	{
	  Hashtable summary=new Hashtable();
	  summary= ( Hashtable ) data.elementAt( 0 );
	  int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
	  int AddBinLineNum1 = getAddBinLineNum();

	  //start table #3
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
		  /*Msg.append( "<TH width=\"2%\"  height=\"38\">Print" );
		  if ( loop == 0 )
		  {
			Msg.append( "<BR><INPUT TYPE=\"checkbox\" value=\"\" onClick=\"checkall('row_chk','1')\" NAME=\"chkall\">" );
		  }
		  Msg.append( "</TH>\n" );*/
		  Msg.append( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.ro")+"<BR>("+res.getString("label.repackageorder")+")</TH>\n" );
		  Msg.append( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.item")+"</TH>\n" );
		  //Msg.append( "<TH width=\"5%\"  height=\"38\">Parent Item</TH>\n" );
		  Msg.append( "<TH width=\"20%\"  height=\"38\">"+res.getString("label.desc")+"</TH>\n" );
		  Msg.append( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.packaging")+"</TH>\n" );
		  Msg.append( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.qtyrequested")+"</TH>\n" );
		  Msg.append( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.qtyavailable")+"</TH>\n" );
		  Msg.append( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.qtyrepackaged")+"</TH>\n" );
		  Msg.append( "<TH width=\"4%\" height=\"38\" colspan=\"2\">"+res.getString("label.bin")+"</TH>\n" );
		  Msg.append( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.source")+"</TH>\n" );
		  Msg.append( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.parentpackaging")+"</TH>\n" );
		  Msg.append( "<TH width=\"1%\"  height=\"38\">"+res.getString("label.close")+"</TH>\n" );
		  //Msg.append( "<TH width=\"5%\"  height=\"38\"># Labels</TH>\n" );
		  Msg.append( "</tr>\n" );
		  createHdr=false;
		}

		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) data.elementAt( i );

		String Next_mr="";
		String Next_item="";

		if ( ! ( i == total ) )
		{
		  hDNext=new Hashtable();
		  hDNext= ( Hashtable ) data.elementAt( i + 1 );
		  Next_mr=hDNext.get( "PR_NUMBER" ) == null ? "&nbsp;" : hDNext.get( "PR_NUMBER" ).toString();
		  Next_item=hDNext.get( "ITEM_ID" ) == null ? "&nbsp;" : hDNext.get( "ITEM_ID" ).toString();
		}
		else
		{
		  Next_mr=" ";
		  Next_item = "";
		}

		// get main information
		String prnumber=hD.get( "PR_NUMBER" ) == null ? "&nbsp;" : hD.get( "PR_NUMBER" ).toString();
		String itemid=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
		String order_quantity=hD.get( "ORDER_QUANTITY" ) == null ? "&nbsp;" : hD.get( "ORDER_QUANTITY" ).toString();
		String original_item_id=hD.get( "ORIGINAL_ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ORIGINAL_ITEM_ID" ).toString();
		String hub=hD.get( "HUB" ) == null ? "&nbsp;" : hD.get( "HUB" ).toString();
		String inventory_group=hD.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : hD.get( "INVENTORY_GROUP" ).toString();
		String receipt_id=hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString();
		String Sel_bin=hD.get( "BIN" ) == null ? "&nbsp;" : hD.get( "BIN" ).toString();
		String mfg_lot=hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString();
		String expire_date=hD.get( "EXPIRE_DATE" ) == null ? "&nbsp;" : hD.get( "EXPIRE_DATE" ).toString();
		//String quantity_available=hD.get( "QUANTITY_AVAILABLE" ) == null ? "&nbsp;" : hD.get( "QUANTITY_AVAILABLE" ).toString();
		String item_desc=hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString();
		String item_pkg=hD.get( "ITEM_PKG" ) == null ? "&nbsp;" : hD.get( "ITEM_PKG" ).toString();
		String search_text=hD.get( "SEARCH_TEXT" ) == null ? "&nbsp;" : hD.get( "SEARCH_TEXT" ).toString();
        String qtypicked=hD.get( "QUANTITY_PICKED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_PICKED" ).toString();
		String updatedone=hD.get( "UPDATEDONE" ) == null ? "&nbsp;" : hD.get( "UPDATEDONE" ).toString();
		String md_item_pkg=hD.get( "MD_ITEM_PKG" ) == null ? "&nbsp;" : hD.get( "MD_ITEM_PKG" ).toString();
		String ma_item_pkg=hD.get( "MA_ITEM_PKG" ) == null ? "&nbsp;" : hD.get( "MA_ITEM_PKG" ).toString();
		String parent_item_pkg="";
		if (ma_item_pkg.length() > 0)
		{
		  parent_item_pkg=md_item_pkg + " from " + ma_item_pkg;
		}
		String qtyavailable=hD.get( "REPACKAGE_QUANTITY_AVAILABLE" ) == null ? "&nbsp;" : hD.get( "REPACKAGE_QUANTITY_AVAILABLE" ).toString();
		String tap_available=hD.get( "TAP_AVAILABLE" ) == null ? "" : hD.get( "TAP_AVAILABLE" ).toString();
		String closable=hD.get( "CLOSEABLE" ) == null ? "" : hD.get( "CLOSEABLE" ).toString();

		String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
		if ( Line_Check.equalsIgnoreCase( "yes" ) )
		{
		  checkednon="checked";
		}
		else
		{
		  checkednon="";
		}

		/*String ismrCritical= ( hD.get( "CRITICAL" ) == null ? "&nbsp;" : hD.get( "CRITICAL" ).toString() );
		if ( "Y".equalsIgnoreCase( ismrCritical ) )
		{
		  Color="CLASS=\"red";
		}*/

		String chkbox_value="no";
		if ( checkednon.equalsIgnoreCase( "checked" ) )
		{
		  chkbox_value="yes";
		}

		boolean Samemrline=false;
		boolean Sameitemid=false;

		if ( prnumber.equalsIgnoreCase( Next_mr ) )
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
			MsgTmp1.append( "<TR>\n" );
		  }

		  MsgTmp1.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" height=\"38\">" + qtyavailable + "</td>\n" );
		  if ( !this.getupdateStatus() || "yes".equalsIgnoreCase( updatedone ) || receipt_id.length() == 0 )
		  {
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + qtypicked + "</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"1%\" height=\"38\">&nbsp;</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"3%\" height=\"38\">&nbsp;</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + receipt_id + "</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + parent_item_pkg + "</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"1%\" height=\"38\">&nbsp;</td>\n" );
		  }
		  else
		  {
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue(name,this,'" +i + "')\" name=\"qty_picked" + i + "\"  value=\"" + qtypicked + "\" maxlength=\"10\" size=\"5\"></td>\n" );


			MsgTmp1.append( "<td " + Color + "r\" width=\"1%\">\n" );
			MsgTmp1.append( "<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"addBin" + i + "\" value=\"+\" OnClick=\"showEmtyBins(" + itemid + "," + i + "," + HubNo + ")\" ></TD>\n" );
			MsgTmp1.append( "<td " + Color + "l\" width=\"3%\">\n" );
			MsgTmp1.append( "<select name=\"selectElementBin" + i + "\">\n" );

			Hashtable in_bin_set= ( Hashtable ) all_bin_set.get( itemid );
			int bin_size=in_bin_set.size();

			if ( Sel_bin.trim().length() == 0 )
			{
			  MsgTmp1.append( "<option selected value=\"\">"+res.getString("label.none")+"</option>\n" );
			}

			//Select the last bin that was added for an item
			String bin_selected="";
			if ( Add_Item_id.equalsIgnoreCase( "NONE" ) )
			{
			  for ( int j=0; j < bin_size; j++ )
			  {
				Integer key=new Integer( j );
				String bin_name= ( String ) in_bin_set.get( key );
				bin_selected="";
				if ( bin_name.equalsIgnoreCase( Sel_bin ) )
				{
				  bin_selected="selected";
				}
				MsgTmp1.append( "<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n" );
			  }
			}
			else
			{
			  for ( int m=0; m < bin_size; m++ )
			  {
				Integer key=new Integer( m );
				String bin_name= ( String ) in_bin_set.get( key );
				bin_selected="";

				if ( i == AddBinLineNum1 )
				{
				  if ( m == ( bin_size - 1 ) )
				  {
					bin_selected="selected";
				  }
				}
				else
				{
				  if ( bin_name.equalsIgnoreCase( Sel_bin ) )
				  {
					bin_selected="selected";
				  }
				}

				MsgTmp1.append( "<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n" );
			  }
			}

			MsgTmp1.append( "</select></td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + receipt_id + "</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + parent_item_pkg + "</td>\n" );
			if ("Y".equalsIgnoreCase(closable))
			{
			  MsgTmp1.append( "<TD " + Color + "l\" WIDTH=\"1%\" height=\"38\"><INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" OnClick= \"javascript:closetap('" +receipt_id + "','" + inventory_group + "')\" name=\"Button" + i + "\" value=\""+res.getString("label.close")+"\"></TD>\n" );
			}
			else
			{
			  MsgTmp1.append( "<TD " + Color + "l\" WIDTH=\"1%\" height=\"38\">&nbsp;</TD>\n" );
			}
		  }
		  //MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"label_qty" + i + "\"  value=\"\" maxlength=\"6\" size=\"5\"></td>\n" );
		  MsgTmp1.append( "</TR>\n" );
		}
		else if ( !Sameitemid )
		{
		  if ( ( !FirstdateRow ) && lastdate > 1 )
		  {
			MsgTmp1.append( "<TR>\n" );
		  }

		  MsgTmp1.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" height=\"38\">" + qtyavailable + "</td>\n" );
		  if ( !this.getupdateStatus() || "yes".equalsIgnoreCase( updatedone ) || receipt_id.length() == 0 )
		  {
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + qtypicked + "</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"1%\" height=\"38\">&nbsp;</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"3%\" height=\"38\">&nbsp;</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + receipt_id + "</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + parent_item_pkg + "</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"1%\" height=\"38\">&nbsp;</td>\n" );
		  }
		  else
		  {
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue(name,this,'" +i + "')\" name=\"qty_picked" + i + "\"  value=\"" + qtypicked + "\" maxlength=\"10\" size=\"5\"></td>\n" );

			MsgTmp1.append( "<td " + Color + "r\" width=\"1%\">\n" );
			MsgTmp1.append( "<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"addBin" + i + "\" value=\"+\" OnClick=\"showEmtyBins(" + itemid + "," + i + "," + HubNo + ")\" ></TD>\n" );
			MsgTmp1.append( "<td " + Color + "l\" width=\"3%\">\n" );
			MsgTmp1.append( "<select name=\"selectElementBin" + i + "\">\n" );

			Hashtable in_bin_set= ( Hashtable ) all_bin_set.get( itemid );
			int bin_size=in_bin_set.size();

			if ( Sel_bin.trim().length() == 0 )
			{
			  MsgTmp1.append( "<option selected value=\"\">"+res.getString("label.none")+"</option>\n" );
			}

			//Select the last bin that was added for an item
			String bin_selected="";
			if ( Add_Item_id.equalsIgnoreCase( "NONE" ) )
			{
			  for ( int j=0; j < bin_size; j++ )
			  {
				Integer key=new Integer( j );
				String bin_name= ( String ) in_bin_set.get( key );
				bin_selected="";
				if ( bin_name.equalsIgnoreCase( Sel_bin ) )
				{
				  bin_selected="selected";
				}
				MsgTmp1.append( "<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n" );
			  }
			}
			else
			{
			  for ( int m=0; m < bin_size; m++ )
			  {
				Integer key=new Integer( m );
				String bin_name= ( String ) in_bin_set.get( key );
				bin_selected="";

				if ( i == AddBinLineNum1 )
				{
				  if ( m == ( bin_size - 1 ) )
				  {
					bin_selected="selected";
				  }
				}
				else
				{
				  if ( bin_name.equalsIgnoreCase( Sel_bin ) )
				  {
					bin_selected="selected";
				  }
				}

				MsgTmp1.append( "<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n" );
			  }
			}

			MsgTmp1.append( "</select></td>\n" );

			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + receipt_id + "</td>\n" );
			MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + parent_item_pkg + "</td>\n" );

			if ("Y".equalsIgnoreCase(closable))
			{
			MsgTmp1.append( "<TD " + Color + "l\" WIDTH=\"1%\" height=\"38\"><INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" OnClick= \"javascript:closetap('" + receipt_id + "','" + inventory_group + "')\" name=\"Button" + i + "\" value=\""+res.getString("label.close")+"\"></TD>\n" );
			}
			else
			{
			  MsgTmp1.append( "<TD " + Color + "l\" WIDTH=\"1%\" height=\"38\">&nbsp;</TD>\n" );
			}
		  }
          //MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"label_qty" + i + "\"  value=\"\" maxlength=\"6\" size=\"5\"></td>\n" );
		  MsgTmp1.append( "</TR>\n" );
		  //MsgTmpDate.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastdate + "\" ><A HREF=\"javascript:enterdotinfo('" + itemid + "')\">" + itemid + "</A></td>\n\n" );
		  //MsgTmpDate.append( "<td height=\"25\" " + Color + "l\" width=\"10%\" ROWSPAN=\"" + lastdate + "\">" + partdesc + "</td>\n" );
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
			MsgTmp.append( "<input type=\"hidden\" name=\"Start" + prnumber + "\" value=\"" + i + "\">\n" );
		  }
		  MsgTmp.append( "<input type=\"hidden\" name=\"Stop" + prnumber + "\" value=\"" + i + "\">\n" );
		  Msg.append( "<TR>\n" );

		  //Msg.append( "<td height=\"25\" " + Color + "\" width=\"2%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastItemNum + "\"><INPUT TYPE=\"checkbox\" onClick=\"checkpickvalue(name,this)\" value=\"" + ( chkbox_value ) + "\" " + checkednon + " NAME=\"row_chk" + i + "\"></td>\n" );
		  Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + prnumber + "</td>\n" );

		  if (this.getupdateStatus() && "Y".equalsIgnoreCase(tap_available) )
		  {
			Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\"><A HREF=\"javascript:startnewtap('" + original_item_id + "','" + inventory_group + "')\">" + itemid + "</A></td>\n" );
		  }
		  else
		  {
			Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + itemid + "</td>\n" );
		  }
		  Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + item_desc + "</td>\n" );
		  Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + item_pkg + "</td>\n" );
		  Msg.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" ROWSPAN=\"" + lastItemNum + "\">" + order_quantity + "</td>\n" );
		  Msg.append( MsgTmp );

		  MsgTmp=new StringBuffer();
		  MsgTmpDate=new StringBuffer();
		  lastItemNum=1;

		  if ( ! ( prnumber.equalsIgnoreCase( Next_mr ) ) )
		  {
			ItemIdCount++;
		  }

		  if ( ( ItemIdCount ) % 2 == 0 )
		  {
			Color="CLASS=\"Inwhite";
		  }
		  else
		  {
			Color="CLASS=\"Inblue";
		  }
		  continue;
		}

		if ( !FirstRow )
		{
		  MsgTmp.append( "<TR>\n" );
		}

		if ( lastItemNum == 2 )
		{
		  MsgTmp.append( "<input type=\"hidden\" name=\"Start" + prnumber + "\" value=\"" + i + "\">\n" );
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
	  Msg.append( "</form>\n" );
	  Msg.append( "</body></html>\n" );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  out.println( radian.web.HTMLHelpObj.printError( "repackaging",intcmIsApplication,res) );
	}
	return Msg.toString();
  }
}
//END OF CLASS

