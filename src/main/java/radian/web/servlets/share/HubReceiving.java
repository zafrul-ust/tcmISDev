package radian.web.servlets.share;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
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
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-04-02
 * Trying to ADD shortcut link to get date instead of typing it in
 *
 * This class provides receiving functionality for the client's receiving servlet.
 * Clients session  are oraganized as follows
 * - New
 *  01-13-02 - 02-26-03
 *  Adding the Actual Supplier Ship Date
 *  03-18-03 - Poping up the received receipts window even for non-Chemicals
 *  03-20-03 - Showing the receipt ID for the BG received pop-up
 *  05-13-03 - prepopulating the priginal receipt id and original mfg_lot if the trans type is IT and code cleanup
 *  05-16-03 - Escaping the , for excel files
 *  07-01-03 - Sending the new parameter to the receinv jacaket link
 *  11-13-03 - Giving a link to see inventory transfer details in the PO column for trans type IT. Some more code cleanup
 *  12-12-03 - Changes to compensate the stylsheet adjustments. Showing the Bin location ordered by last location used. Block Back button
 * 02-16-04 - Sorting Drop Downs
 * 03-25-04 - Moving the Permissions to inventory Group level and adding the ability to search by inventory group
 * 04-19-04 - Changeing the inventory group drop down to show INVENTORY_GROUP_NAME in the display of drop dowm. Getting the Hub list from the HUB_PREF
 * table instead of the user_group_member table. Chaning the receiving to accomodate receiving kits with different parts with different MFG_LOT,EXP_DATE and BIN.
 * Changin the date format to allow input in the following formats mmddyyyy and mmddyyy
 * 05-07-04 - Sorting the Hub Drop Down based on the prority in hub_pref table
 * 06-01-04 - Breaking up the search to make the query faster
 * 07-22-04 - Giving the ability to enter receipt notes from the receiving page
 * 07-28-05 - Giving the option to enter delivery ticket information.
*/

public class HubReceiving
{
  private ServerResourceBundle bundle=null;
  private String thedecidingRandomNumber=null;
  private TcmISDBModel db=null;
  private String checkednon="";
  private HubReceivingTables hubObj=null;
  private PrintWriter out=null;
  private boolean completeSuccess=true;
  private boolean noneToUpd=true;
  protected int AddNewBinLineNum;
  private String Receiving_Servlet="";
  private String Label_Servlet="";
  private String paper_size="";
  private String numOldDays="30";
  protected boolean allowupdate;
  private boolean intcmIsApplication = false;
  private static org.apache.log4j.Logger reclog = org.apache.log4j.Logger.getLogger(HubReceiving.class);

  public void setupdateStatus( boolean id )
  {
    this.allowupdate=id;
  }

  private boolean getupdateStatus()
     throws Exception
  {
    return this.allowupdate;
  }

  public HubReceiving( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  private void setAddBinLineNum( int id )
  {
    this.AddNewBinLineNum=id;
  }

  private int getAddBinLineNum() throws Exception
  {
    return this.AddNewBinLineNum;
  }
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,
        IOException
    {
        doPost(request,response);
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException
    {
      hubObj=new HubReceivingTables( db );
      out=response.getWriter();
      response.setContentType( "text/html" );
      HttpSession session=request.getSession();

      PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
      Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
      intcmIsApplication = false;
      if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
      {
       intcmIsApplication = true;
      }

      String branch_plant= session.getAttribute( "BRANCH_PLANT" ) == null ? "" : session.getAttribute( "BRANCH_PLANT" ).toString();
      String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();

      Receiving_Servlet=bundle.getString( "HUBRECV_SERVLET" );
      Label_Servlet=bundle.getString( "LABEL_SERVLET" );

      String User_Search=null;
      String User_Action=null;
      String SubUser_Action=null;
      String User_Session="";
      String User_Sort="";
      String generate_labels="";

      String scategory="";
      scategory=request.getParameter( "Category" );
      if ( scategory == null )
        scategory="No";
      generate_labels=request.getParameter( "generate_labels" );
      if ( generate_labels == null )
        generate_labels="No";
      paper_size=request.getParameter( "paperSize" );
      if ( paper_size == null )
        paper_size="31";
      User_Session=request.getParameter( "session" );
      if ( User_Session == null )
        User_Session="New";
      User_Action=request.getParameter( "UserAction" );
      if ( User_Action == null )
              User_Action = "New";
      User_Search=request.getParameter( "SearchField" );
      if ( User_Search == null )
        User_Search="";

      User_Sort=request.getParameter( "SortBy" );
      if ( User_Sort == null )
        User_Sort = "1";

      numOldDays=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "nodaysold" ) );
      if ( numOldDays == null )
        numOldDays="30";

      if ( numOldDays.trim().length() < 1 )
      {
        numOldDays="30";
	  }

      if ( numOldDays.length() > 0 )
      {
        int numdays=Integer.parseInt( numOldDays );
        if ( numdays < 0 )
        {
          numOldDays="30";
        }
      }
      else
      {
        numOldDays="30";
      }

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

	  thedecidingRandomNumber=request.getParameter( "thedecidingRandomNumber" );
	  if ( thedecidingRandomNumber == null )
		thedecidingRandomNumber="";
	  thedecidingRandomNumber=thedecidingRandomNumber.trim();

      String addbin_bin_id="";
      String addbin_item_id="";
      String dupl_line = "";

	  SubUser_Action=request.getParameter( "SubUserAction" );
	  if ( SubUser_Action == null )
		SubUser_Action="JSError2";

	  String invengrp=request.getParameter( "invengrp" );
	  if ( invengrp == null )
		invengrp="";
	  invengrp=invengrp.trim();

      try
      {
		Random rand=new Random();
		int tmpReq=rand.nextInt();
		Integer tmpReqNum=new Integer( tmpReq );

		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

		if ( ! ( SubUser_Action.equalsIgnoreCase( "AddBin" ) || SubUser_Action.equalsIgnoreCase( "DuplLine" ) || User_Action.equalsIgnoreCase( "createxls" ) ) )
		{
		  if ( thedecidingRandomNumber.length() > 0 )
		  {
			String randomnumberfromsesion=session.getAttribute( "RECRNDCOOKIE" ) == null ? "" : session.getAttribute( "RECRNDCOOKIE" ).toString();
			if ( randomnumberfromsesion.equalsIgnoreCase( thedecidingRandomNumber ) )
			{
			  thedecidingRandomNumber=tmpReqNum.toString();
			  session.setAttribute( "RECRNDCOOKIE",thedecidingRandomNumber );
			}
			else
			{
			  thedecidingRandomNumber=tmpReqNum.toString();
			  session.setAttribute( "RECRNDCOOKIE",thedecidingRandomNumber );
			  session.setAttribute( "DATA",new Vector() );
			  //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
			  Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
			  Vector recallowinvengrps= ( Vector ) session.getAttribute( "REC_ALLOWED_INVENGRP" );

			  buildHeader( null,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
			  out.println( "<BR><BR><CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>" );
			  return;
			}
		  }
		  else
		  {
			thedecidingRandomNumber=tmpReqNum.toString();
			session.setAttribute( "RECRNDCOOKIE",thedecidingRandomNumber );
		  }
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

		Hashtable initialinvData = new Hashtable ();
		Hashtable hub_list1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		if (hub_list1.size() > 0)
		{
		  initialinvData= radian.web.poHelpObj.getinvgrpInitialData(db,hub_list_set);
          session.setAttribute( "INVENGRP_DATA",initialinvData);
		}

	    Vector allowedinvengrps  = radian.web.HTMLHelpObj.createvgrpmemlist(db,personnelid,"Receiving");
		session.setAttribute( "REC_ALLOWED_INVENGRP",allowedinvengrps );

		Vector lot_status_set11=radian.web.HTMLHelpObj.createStatusSet( db );
		session.setAttribute( "STATUS_SET",lot_status_set11 );

          if ( hub_list1.size() < 1 )
          {
            buildHeader( null,hub_list_set,"",User_Search,User_Sort,"",scategory,initialinvData,invengrp,searchlike,searchfor,session );
            out.println( radian.web.HTMLHelpObj.printHTMLNoFacilities() );
            hub_list_set=null;
            out.close();
          }
          else
          {
            //session.setAttribute( "HUB_SET",hub_list_set );
            buildHeader( null,hub_list_set,"",User_Search,User_Sort,"",scategory,initialinvData,invengrp,searchlike,searchfor,session );
            out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
            out.close();
            hub_list_set=null;
          }
        }
        else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "createxls" ) ) )
        {
          Vector retrndata= ( Vector ) session.getAttribute( "DATA" );
          out.println( buildxlsDetails( retrndata,personnelid ) );
          retrndata=null;
        }
        else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "New" ) ) )
        {
          //read data and build page
          //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
		  Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
		  Vector recallowinvengrps= ( Vector ) session.getAttribute( "REC_ALLOWED_INVENGRP" );
		  Vector lot_status_set= ( Vector ) session.getAttribute( "STATUS_SET" );

          Vector openorder_data=new Vector();
          Vector openbinSet=new Vector();

          if ( scategory.equalsIgnoreCase( "2" ) )
          {
            openorder_data=hubObj.getAllopenOrderBG( branch_plant,User_Search,User_Sort,invengrp,searchlike,searchfor );
            openbinSet.addElement( " " );
          }
          else
          {
            openorder_data=hubObj.getAllopenOrder( branch_plant,User_Search,User_Sort,numOldDays,invengrp,searchlike,searchfor );
            openbinSet=radian.web.HTMLHelpObj.createEmptyBinSet( db,branch_plant );
          }

          session.setAttribute( "EMPTYBIN",openbinSet );

          Hashtable sum= ( Hashtable ) ( openorder_data.elementAt( 0 ) );
          int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
          if ( 0 == count )
          {
            buildHeader( null,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
            out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
            out.close();
            //clean up
            openorder_data=null;
            hub_list_set=null;
          }
          else
          {
            Hashtable in_bin_set=new Hashtable();
            Hashtable out_bin_set2 =new Hashtable();

            if ( !scategory.equalsIgnoreCase( "2" ) )
            {
              out_bin_set2=createItemBinSet( openorder_data,branch_plant );
            }

            //Hashtable lot_status_set=new Hashtable();
            /*if ( scategory.equalsIgnoreCase( "2" ) )
            {
              lot_status_set.put( "HUB_LOT_STATUS"," " );
            }
            else
            {
              lot_status_set.put( "HUB_LOT_STATUS"," " );
            }*/

            session.setAttribute( "DATA",openorder_data );
            session.setAttribute( "BIN_SET",out_bin_set2 );
            //session.setAttribute( "STATUS_SET",lot_status_set );

            buildHeader( openorder_data,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
            if ( scategory.equalsIgnoreCase( "2" ) )
            {
              buildDetailsBG( openorder_data,branch_plant,"",recallowinvengrps );
            }
            else
            {
              buildDetails( openorder_data,out_bin_set2,"NONE",branch_plant,"showorigstuff",recallowinvengrps,lot_status_set );
            }

            out.close();
            //clean up
            openorder_data=null;
            hub_list_set=null;
            in_bin_set=null;
            out_bin_set2=null;
            //lot_status_set=null;
          } //when there are open orders for hub
        }
        else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "Update" ) ) )
        {
          Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
          Vector synch_data=SynchServerData( request,retrn_data );
		  Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
		  Vector recallowinvengrps= ( Vector ) session.getAttribute( "REC_ALLOWED_INVENGRP" );
		  Vector lot_status_set= ( Vector ) session.getAttribute( "STATUS_SET" );

          retrn_data=null;

          SubUser_Action=request.getParameter( "SubUserAction" );
          if ( SubUser_Action == null )
            SubUser_Action="JSError2";

          if ( SubUser_Action.equalsIgnoreCase( "JSError2" ) )
          {
            Hashtable all_bin_set_e= ( Hashtable ) session.getAttribute( "BIN_SET" );
            //Hashtable all_status_set_e= ( Hashtable ) session.getAttribute( "STATUS_SET" );
            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

            session.setAttribute( "DATA",synch_data );
            session.setAttribute( "BIN_SET",all_bin_set_e );
            //session.setAttribute( "STATUS_SET",all_status_set_e );

            buildHeader( synch_data,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
            out.println( radian.web.HTMLHelpObj.printJavaScriptError() );
            if ( scategory.equalsIgnoreCase( "2" ) )
            {
              buildDetailsBG( synch_data,branch_plant,"",recallowinvengrps );
            }
            else
            {
              buildDetails( synch_data,all_bin_set_e,"NONE",branch_plant,"",recallowinvengrps,lot_status_set );
            }

            out.close();
            //clean up
            synch_data=null;
            all_bin_set_e=null;
            //all_status_set_e=null;
          }
          else if ( SubUser_Action.equalsIgnoreCase( "AddBin" ) )
          {
            String AddBin_Line_No="0";
            addbin_item_id=BothHelpObjs.makeBlankFromNull( request.getParameter( "AddBin_Item_Id" ) );
            addbin_bin_id=BothHelpObjs.makeBlankFromNull( request.getParameter( "AddBin_Bin_Id" ) );
            AddBin_Line_No=BothHelpObjs.makeBlankFromNull( request.getParameter( "AddBin_Line_No" ) );
            setAddBinLineNum( Integer.parseInt( AddBin_Line_No ) );

            Hashtable all_bin_set_a= ( Hashtable ) session.getAttribute( "BIN_SET" );
            //Hashtable all_status_set_a= ( Hashtable ) session.getAttribute( "STATUS_SET" );
            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
            Hashtable all_new_bin_set=addToItemBinSet( addbin_item_id,addbin_bin_id,all_bin_set_a );

            session.setAttribute( "DATA",synch_data );
            session.setAttribute( "BIN_SET",all_new_bin_set );
            //session.setAttribute( "STATUS_SET",all_status_set_a );

            buildHeader( synch_data,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
            if ( scategory.equalsIgnoreCase( "2" ) )
            {
              buildDetailsBG( synch_data,branch_plant,"",recallowinvengrps );
            }
            else
            {
              buildDetails( synch_data,all_new_bin_set,addbin_item_id,branch_plant,"",recallowinvengrps,lot_status_set );
            }
            out.close();
            //clean up
            synch_data=null;
            all_new_bin_set=null;
            //all_status_set_a=null;
            all_bin_set_a=null;
          }
          else if ( SubUser_Action.equalsIgnoreCase( "DuplLine" ) )
          {
            dupl_line=BothHelpObjs.makeBlankFromNull( request.getParameter( "DuplLineNumber" ) );
            Vector new_data=createDuplicate( dupl_line,synch_data );
            session.setAttribute( "DATA",new_data );

            Hashtable all_bin_set_d= ( Hashtable ) session.getAttribute( "BIN_SET" );
            //Hashtable all_status_set_d= ( Hashtable ) session.getAttribute( "STATUS_SET" );
            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

            buildHeader( synch_data,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
            if ( scategory.equalsIgnoreCase( "2" ) )
            {
              buildDetailsBG( new_data,branch_plant,"",recallowinvengrps );
            }
            else
            {
              buildDetails( new_data,all_bin_set_d,"NONE",branch_plant,"",recallowinvengrps,lot_status_set );
            }
            out.close();
            //clean up
            synch_data=null;
            new_data=null;
            all_bin_set_d=null;
            //all_status_set_d=null;
          }
          else if ( SubUser_Action.equalsIgnoreCase( "UpdPage" ) )
          {
            Hashtable temp1= ( Hashtable ) session.getAttribute( "BIN_SET" );
            //Hashtable temp2= ( Hashtable ) session.getAttribute( "STATUS_SET" );
            //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );
            session.setAttribute( "DATA",synch_data );

            Hashtable updateresults=UpdateDetails( synch_data,branch_plant,personnelid,scategory,recallowinvengrps );
            Boolean result= ( Boolean ) updateresults.get( "RESULT" );
            Vector errordata= ( Vector ) updateresults.get( "ERRORDATA" );
            Vector errMsgs=hubObj.getErrMsgs();

            session.setAttribute( "DATA",errordata );
            boolean resulttotest=result.booleanValue();

            if ( false == resulttotest )
            {
              buildHeader( errordata,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
              if ( true == noneToUpd )
              {
                out.println( radian.web.HTMLHelpObj.printMessageinTable( "No Item was Choosen for Receiving" ) );
                if ( scategory.equalsIgnoreCase( "2" ) )
                {
                  buildDetailsBG( errordata,branch_plant,SubUser_Action,recallowinvengrps );
                }
                else
                {
                  buildDetails( errordata,temp1,"NONE",branch_plant,SubUser_Action,recallowinvengrps,lot_status_set );
                }
              }
              else
              {
                //out.println( radian.web.HTMLHelpObj.printMessageinTable("An Error Occurred.<BR>Please Check Data and try Again." ) );
				out.println( radian.web.HTMLHelpObj.printHTMLPartialSuccess( errMsgs ) );

                if ( scategory.equalsIgnoreCase( "2" ) )
                {
                  buildDetailsBG( errordata,branch_plant,SubUser_Action,recallowinvengrps );
                }
                else
                {
                  buildDetails( errordata,temp1,"NONE",branch_plant,SubUser_Action,recallowinvengrps,lot_status_set );
                }
              }
              out.close();
            }
            else
            {
              //contains a list of a list of lot seq that were added to receiving table
              Vector vList=hubObj.getLotSeqList();
              int size=vList.size();

              if ( size > 0 )
              {
                session.setAttribute( "REC_LABEL_SEQUENCE_NUMBERS",vList );
              }

              if ( true == completeSuccess )
              {
                if ( size > 0 )
                {
                  buildHeader( errordata,hub_list_set,branch_plant,User_Search,User_Sort,"GENERATE_LABELS",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
                }
                else
                {
                  buildHeader( errordata,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
                }

                out.println( radian.web.HTMLHelpObj.printMessageinTable( "All Your Selections Were Successfully Updated" ) );

                if ( scategory.equalsIgnoreCase( "2" ) )
                {
                  buildDetailsBG( errordata,branch_plant,SubUser_Action,recallowinvengrps );
                }
                else
                {
                  buildDetails( errordata,temp1,"NONE",branch_plant,SubUser_Action,recallowinvengrps,lot_status_set );
                }
                out.close();
              }
              else
              {
                Hashtable recsum1=new Hashtable();
                recsum1.put( "TOTAL_NUMBER",new Integer( errordata.size() - 1 ) );
                errordata.setElementAt( recsum1,0 );

                if ( size > 0 )
                {
                  buildHeader( errordata,hub_list_set,branch_plant,User_Search,User_Sort,"GENERATE_LABELS",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
                }
                else
                {
                  buildHeader( errordata,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
                }

                out.println( radian.web.HTMLHelpObj.printHTMLPartialSuccess( errMsgs ) );

                if ( scategory.equalsIgnoreCase( "2" ) )
                {
                  buildDetailsBG( errordata,branch_plant,SubUser_Action,recallowinvengrps );
                }
                else
                {
                  buildDetails( errordata,temp1,"NONE",branch_plant,SubUser_Action,recallowinvengrps,lot_status_set );
                }
                out.close();
              }
            }
            //clean up
            synch_data=null;
            temp1=null;
            //temp2=null;
            updateresults=null;
            errordata=null;
          }
          else
          {
            out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
          }

        }//End of User_session="Active" && User_Action="Update"
        else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "JSError1" ) ) )
        {
          Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
          Vector synch_data=SynchServerData( request,retrn_data );
		  Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
		  Vector recallowinvengrps= ( Vector ) session.getAttribute( "REC_ALLOWED_INVENGRP" );
		  Vector lot_status_set= ( Vector ) session.getAttribute( "STATUS_SET" );

          retrn_data=null;

          Hashtable all_bin_set_e= ( Hashtable ) session.getAttribute( "BIN_SET" );
          //Hashtable all_status_set_e= ( Hashtable ) session.getAttribute( "STATUS_SET" );
          //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_SET" );

          session.setAttribute( "DATA",synch_data );
          session.setAttribute( "BIN_SET",all_bin_set_e );
          //session.setAttribute( "STATUS_SET",all_status_set_e );

          buildHeader( synch_data,hub_list_set,branch_plant,User_Search,User_Sort,"",scategory,iniinvendata,invengrp,searchlike,searchfor,session );
          out.println( radian.web.HTMLHelpObj.printJavaScriptError() );
          if ( scategory.equalsIgnoreCase( "2" ) )
          {
            buildDetailsBG( synch_data,branch_plant,"",recallowinvengrps );
          }
          else
          {
            buildDetails( synch_data,all_bin_set_e,"NONE",branch_plant,"",recallowinvengrps,lot_status_set );
          }

          out.close();
          //clean up
          synch_data=null;
          all_bin_set_e=null;
          //all_status_set_e=null;
        }
        else
        {
          out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
      }
      //clean up
      hubObj=null;
      return;
    }

    private Vector createDuplicate( String dupl_line,Vector in_data )
    {
      int line_to_dupl= ( new Integer( dupl_line ) ).intValue();
      int size=in_data.size();
      Vector new_data=new Vector();

      try
      {
        for ( int i=0; i < size; i++ )
        {
          if ( i == 0 )
          {
            Hashtable sum= ( Hashtable ) ( in_data.elementAt( i ) );
            int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
            sum.put( "TOTAL_NUMBER",new Integer( count + 1 ) );
            new_data.addElement( sum );
          }
          else
          {
            Hashtable temp= ( Hashtable ) ( in_data.elementAt( i ) );
            new_data.addElement( temp );
            if ( i == line_to_dupl )
            {
              Hashtable hDupl=new Hashtable();
              hDupl.put( "PURCHASE_ORDER",temp.get( "PURCHASE_ORDER" ) );
              hDupl.put( "PURCHASE_ORDER_LINE",temp.get( "PURCHASE_ORDER_LINE" ) );
              hDupl.put( "EXPCTD_DATE",temp.get( "EXPCTD_DATE" ) );
              hDupl.put( "SUPPLIER",temp.get( "SUPPLIER" ) );
              hDupl.put( "ITEM_ID",temp.get( "ITEM_ID" ) );
              hDupl.put( "ITEM_DESC",temp.get( "ITEM_DESC" ) );
              hDupl.put( "QTY_OPEN",temp.get( "QTY_OPEN" ) );
              hDupl.put( "TRANS_TYPE",temp.get( "TRANS_TYPE" ) );
              hDupl.put( "NOTES",temp.get( "NOTES" ) );
              hDupl.put( "PACKING",temp.get( "PACKING" ) );
              hDupl.put( "USERCHK","" );
              hDupl.put( "MFG_LOT","" );
              hDupl.put( "DATE_MFGD","" );
              hDupl.put( "DATE_RECIEVED",temp.get( "DATE_RECIEVED" ) );
              hDupl.put( "QTY_RECD","" );
              hDupl.put( "BIN_NAME",temp.get( "BIN_NAME" ) );
              hDupl.put( "STATUS_ID",temp.get( "STATUS_ID" ) );
              hDupl.put( "XFER_REQ_ID",temp.get( "XFER_REQ_ID" ) );
              hDupl.put( "EXPIRE_DATE","" );
              hDupl.put( "DATE_OF_SHIPMENT","" );
              hDupl.put( "RECEIPT_ID",temp.get( "RECEIPT_ID" ) );
              hDupl.put( "INDEFINITE_SHELF_LIFE",temp.get( "INDEFINITE_SHELF_LIFE" ) );
              hDupl.put( "LINE_STATUS",temp.get( "LINE_STATUS" ) );
              hDupl.put( "CAT_PART_NO",temp.get( "CAT_PART_NO" ) );
              hDupl.put( "COMPANY_ID",temp.get( "COMPANY_ID" ) );
              hDupl.put( "ACTUAL_SHIP_DATE",temp.get( "ACTUAL_SHIP_DATE" ) );
              hDupl.put( "CRITICAL",temp.get( "CRITICAL" ) );
              hDupl.put( "ORIGINAL_MFG_LOT",temp.get( "ORIGINAL_MFG_LOT" ) );
              hDupl.put( "ORIGINAL_RECEIPT_ID",temp.get( "ORIGINAL_RECEIPT_ID" ) );
              hDupl.put( "INVENTORY_GROUP",temp.get( "INVENTORY_GROUP" ) );
			  hDupl.put( "MANAGE_KITS_AS_SINGLE_UNIT",temp.get( "MANAGE_KITS_AS_SINGLE_UNIT" ) );
			  hDupl.put( "COMPONENT_ID",temp.get( "COMPONENT_ID" ) );
			  hDupl.put( "MATERIAL_ID",temp.get( "MATERIAL_ID" ) );
			  hDupl.put( "MATERIAL_DESC",temp.get( "MATERIAL_DESC" ) );
			  hDupl.put( "INVENTORY_GROUP_NAME",temp.get( "INVENTORY_GROUP_NAME" ) );
			  hDupl.put( "NUMBER_OF_KITS",temp.get( "NUMBER_OF_KITS" ) );
			  hDupl.put( "LOT_STATUS",temp.get( "LOT_STATUS" ) );
			  hDupl.put( "SKIP_RECEIVING_QC",temp.get( "SKIP_RECEIVING_QC" ) );
			  hDupl.put( "DELIVERY_TICKET",temp.get( "DELIVERY_TICKET" ) );

              new_data.addElement( hDupl );
            } //end of last if
          } //end of else
        } //end of for
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }

      return new_data;
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

          String bin_name="";
          bin_name=BothHelpObjs.makeBlankFromNull( request.getParameter( "selectElementBin" + i ) );
          hD.remove( "BIN_NAME" );
          hD.put( "BIN_NAME",bin_name );

          String check="";
          check=BothHelpObjs.makeBlankFromNull( request.getParameter( "row_chk" + i ) );
          hD.remove( "USERCHK" );
          hD.put( "USERCHK",check );

          String mfg_lot="";
          mfg_lot=BothHelpObjs.makeBlankFromNull( request.getParameter( "mfg_lot" + i ) );
          hD.remove( "MFG_LOT" );
          hD.put( "MFG_LOT",mfg_lot );

          String date_mfgd="";
          date_mfgd=BothHelpObjs.makeBlankFromNull( request.getParameter( "date_mfgd" + i ) );
          hD.remove( "DATE_MFGD" );
          hD.put( "DATE_MFGD",date_mfgd );

          String date_recieved="";
          date_recieved=BothHelpObjs.makeBlankFromNull( request.getParameter( "date_recieved" + i ) );
          hD.remove( "DATE_RECIEVED" );
          hD.put( "DATE_RECIEVED",date_recieved );

          String actshipDate="";
          actshipDate=BothHelpObjs.makeBlankFromNull( request.getParameter( "act_suppship_date" + i ) );
          hD.remove( "ACTUAL_SHIP_DATE" );
          hD.put( "ACTUAL_SHIP_DATE",actshipDate );

          String qty_recd="";
          qty_recd=BothHelpObjs.makeBlankFromNull( request.getParameter( "qty_recd" + i ) );
          hD.remove( "QTY_RECD" );
          hD.put( "QTY_RECD",qty_recd );

          String expiry_date="";
          expiry_date=BothHelpObjs.makeBlankFromNull( request.getParameter( "expiry_date" + i ) );

          if ( "Indefinite".equalsIgnoreCase( expiry_date ) )
          {
            expiry_date="01/01/3000";
          }
          hD.remove( "EXPIRE_DATE" );
          hD.put( "EXPIRE_DATE",expiry_date );

          String ship_date="";
          ship_date=BothHelpObjs.makeBlankFromNull( request.getParameter( "ship_date" + i ) );

          hD.remove( "DATE_OF_SHIPMENT" );
          hD.put( "DATE_OF_SHIPMENT",ship_date );

          String receipt_id="";
          receipt_id=BothHelpObjs.makeBlankFromNull( request.getParameter( "receipt_id" + i ) );
          hD.remove( "RECEIPT_ID" );
          hD.put( "RECEIPT_ID",receipt_id );

		  String recnotes="";
		  recnotes=BothHelpObjs.makeBlankFromNull( request.getParameter( "recnotes" + i ) );
		  hD.remove( "NOTES" );
		  hD.put( "NOTES",recnotes );

		  String selectElementStatus="";
		  selectElementStatus=BothHelpObjs.makeBlankFromNull( request.getParameter( "selectElementStatus" + i ) );
		  hD.remove( "LOT_STATUS" );
		  hD.put( "LOT_STATUS",selectElementStatus );

		  String deliveryTicket="";
		  deliveryTicket=BothHelpObjs.makeBlankFromNull( request.getParameter( "deliveryTicket" + i ) );
		  hD.remove( "DELIVERY_TICKET" );
		  hD.put( "DELIVERY_TICKET",deliveryTicket );

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

    private Hashtable addToItemBinSet( String item_id,String bin_id,Hashtable in_set )
    {
      Hashtable bin_set=new Hashtable();
      try
      {
        bin_set= ( Hashtable ) in_set.get( item_id );
        int size=bin_set.size();
        if ( ( size == 1 ) && bin_set.containsValue( "NONE" ) )
        {
          bin_set.remove( new Integer( 0 ) );
          bin_set.put( new Integer( 0 ),bin_id );
        }
        else
        {
          bin_set.put( new Integer( size ),bin_id );
        }
        in_set.put( item_id,bin_set );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
      }

      return in_set;
    }

    private Hashtable createItemBinSet( Vector data,String branch_plant )
    {
      String item_id;
      Hashtable bin_set=new Hashtable();
      Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );
      int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

      try
      {
        for ( int i=1; i < count + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) ( data.elementAt( i ) );
          item_id= ( String ) hD.get( "ITEM_ID" );
          if ( false == bin_set.containsKey( item_id ) )
          {
            Hashtable bin_for_item=radian.web.HTMLHelpObj.CreateBinForItem( db,item_id,branch_plant );
            bin_set.put( item_id,bin_for_item );
          }
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
      }

      return bin_set;
    }

    private Hashtable UpdateDetails( Vector data,String BranchPlant,String logonid,String categorys,Vector allowedingrps )
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
        int linecheckcount=0;
		int lastItemNum=1;
		String returnedReceiptId1="";
		String returnedReceiptId2="";
		boolean callreccomp=false;
        boolean one_success=false;

        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String Line_Check="";
          Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
          String trans_type= ( hD.get( "TRANS_TYPE" ) == null ? " " : hD.get( "TRANS_TYPE" ).toString() );
		  String Purchase_order= ( hD.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER" ).toString() );
		  String PO_Line= ( hD.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER_LINE" ).toString() );
		  String Item_id= ( hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString() );
		  String mngkitassingl = hD.get("MANAGE_KITS_AS_SINGLE_UNIT").toString();
		  String componentid = hD.get("COMPONENT_ID").toString();
		  String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString() );

		  String Next_po="";
		  String Next_poline="";
		  String Next_item="";
		  if ( ! ( i == total ) )
		  {
			Hashtable hDNext=new Hashtable();
			hDNext= ( Hashtable ) data.elementAt( i + 1 );
			Next_po=hDNext.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hDNext.get( "PURCHASE_ORDER" ).toString();
			Next_poline=hDNext.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hDNext.get( "PURCHASE_ORDER_LINE" ).toString();
			Next_item=hDNext.get( "ITEM_ID" ) == null ? "&nbsp;" : hDNext.get( "ITEM_ID" ).toString();
		  }
		  else
		  {
			Next_po=" ";
			Next_poline="";
			Next_item=" ";
		  }

		  boolean Samepoline=false;
		  boolean FirstRow=false;
		  if ( "N".equalsIgnoreCase(mngkitassingl) && Next_po.equalsIgnoreCase(Purchase_order) && Next_poline.equalsIgnoreCase(PO_Line) && Next_item.equalsIgnoreCase(Item_id) )
		   {
			Samepoline=true;
			if ( Line_Check.equalsIgnoreCase( "yes" ) )
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

		  if ( allowedingrps.contains(invengrp) && (Line_Check.equalsIgnoreCase( "yes" ) || callreccomp) )
          {
            noneToUpd=false;
            linecheckcount++;
            Hashtable ResultH=new Hashtable();
            boolean line_result=false;

			if ( FirstRow )
			{
			  if ( categorys.equalsIgnoreCase( "2" ) )
			  {
				ResultH=hubObj.UpdateLineBG( hD,BranchPlant,logonid ); // update database
			  }
			  else
			  {
				ResultH=hubObj.UpdateLine( hD,BranchPlant,logonid ); // update database
			  }

			  Boolean resultline= ( Boolean ) ResultH.get( "RESULT" );
			  line_result=resultline.booleanValue();

			  returnedReceiptId1=ResultH.get( "RCECIPT_ID1" ).toString();
			  returnedReceiptId2=ResultH.get( "RCECIPT_ID2" ).toString();
			}

		    if ( "N".equalsIgnoreCase(mngkitassingl) && callreccomp)
			{
			  reclog.info("Calling p_receipt_component for COMPONENT_ID "+componentid+" RECEIPT_ID1 "+returnedReceiptId1+" Personnel ID  "+logonid+"");
		     line_result = radian.web.HTMLHelpObj.insreceiptcomp(db,hD,returnedReceiptId1,logonid);
			 if (!"0".equalsIgnoreCase(returnedReceiptId2))
			 {
			   reclog.info("Calling p_receipt_component for COMPONENT_ID "+componentid+" RECEIPT_ID2 "+returnedReceiptId2+" Personnel ID  "+logonid+"");
			   line_result = radian.web.HTMLHelpObj.insreceiptcomp(db,hD,returnedReceiptId2,logonid);
			 }
			}

            if ( !line_result )
            {
              completeSuccess=false;
              hD.remove( "USERCHK" );
              hD.put( "USERCHK"," " );

              hD.remove( "LINE_STATUS" );
              hD.put( "LINE_STATUS","NO" );

              errordata.addElement( hD );
            }
            else
            {
              one_success=true;
              hD.remove( "LINE_STATUS" );
              hD.put( "LINE_STATUS","YES" );

              if ( !trans_type.equalsIgnoreCase( "IT" ) )
              {
                hD.remove( "RECEIPT_ID" );
                hD.put( "RECEIPT_ID",returnedReceiptId1 );
              }
              errordata.addElement( hD );
            }
            ResultH=null;
          }
          else
          {
            errordata.addElement( hD );
          }

		  if (!Samepoline)
		  {
			returnedReceiptId1 = "";
			returnedReceiptId2 = "";
			callreccomp=false;
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
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
      }

      updateresult.put( "RESULT",new Boolean( result ) );
      updateresult.put( "ERRORDATA",errordata );
      return updateresult;
    }

    private StringBuffer buildxlsDetails( Vector data,String personnelID ) throws Exception
    {
      StringBuffer Msg=new StringBuffer();

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

        Msg.append( "PO,PO Line,Date Exptd,Supplier,Open Amt,Packaging,Item,Description,Inventory Group\n" );

        int i=0; //used for detail lines
        for ( int loop=0; loop < total; loop++ )
        {
          i++;
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          // get main information
          String Purchase_order= ( hD.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER" ).toString() );
          String PO_Line= ( hD.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER_LINE" ).toString() );
          String Expected_dt= ( hD.get( "EXPCTD_DATE" ) == null ? "" : hD.get( "EXPCTD_DATE" ).toString() );
          String supplier= ( hD.get( "SUPPLIER" ) == null ? "&nbsp;" : hD.get( "SUPPLIER" ).toString() );
          supplier = HelpObjs.findReplace(supplier,","," ");

          String Item_id= ( hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString() );
          String Item_desc = ( hD.get( "ITEM_DESC" ) == null ? "" : hD.get( "ITEM_DESC" ).toString() );
          Item_desc = HelpObjs.findReplace(Item_desc,","," ");
          Item_desc = HelpObjs.findReplace(Item_desc,"<BR>"," ");

          String Qty_open= ( hD.get( "QTY_OPEN" ) == null ? "&nbsp;" : hD.get( "QTY_OPEN" ).toString() );
          String Pkging= ( hD.get( "PACKING" ) == null ? "&nbsp;" : hD.get( "PACKING" ).toString() );
          String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString() );

          Msg.append( "\"" + Purchase_order + "\"," );
          Msg.append( "\"" + PO_Line + "\"," );
          Msg.append( "\"" + Expected_dt + "\"," );
          Msg.append( "\"" + supplier + "\"," );
          Msg.append( "\"" + Qty_open + "\"," );
          Msg.append( "\"" + Pkging + "\"," );
          Msg.append( "\"" + Item_id + "\"," );
          Msg.append( "\"" + Item_desc + "\"," );
          Msg.append( "\"" + invengrp + "\"\n" );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Inventory Reconciliation",intcmIsApplication ) );
      }

      String url="";
      String file="";
      PrintStream ps=null;

      Random rand=new Random();
      int tmpReq=rand.nextInt();
      Integer tmpReqNum=new Integer( tmpReq );

      String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	  String writefilepath=radian.web.tcmisResourceLoader.getsavelreportpath();
	  file=writefilepath + "rec" + personnelID + tmpReqNum.toString() + ".csv";
	  url=wwwHome + "reports/rec" + personnelID + tmpReqNum.toString() + ".csv";

      try
      {
        ps=new PrintStream( new FileOutputStream( file ),true );
        byte outbuf[];
        outbuf=Msg.toString().getBytes();
        ps.write( outbuf );
        ps.close();
      }
      catch ( IOException writee )
      {
        writee.printStackTrace();
        url="";
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

    private void buildHeader( Vector data,Hashtable hub_list_set,String hub_branch_id,String search_text,String sortby,String buildlabels,String category,
							  Hashtable initialinvData,String selinvengrp,String searchLike, String searchfor,HttpSession session )
    {
      //StringBuffer Msg=new StringBuffer();
      String SelectedHubName="";

      try
      {
        out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Receiving","receivingjs.js",intcmIsApplication ) );
        out.println( "<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
		out.println("<SCRIPT SRC=\"/clientscripts/invengrpdropdown.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
        out.println( "</HEAD>  \n" );

        if ( "GENERATE_LABELS".equalsIgnoreCase( buildlabels ) )
        {
          out.println( "<BODY onLoad=\"doPopup()\">\n" );
        }
        else
        {
          out.println( "<BODY>\n" );
        }

        out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
        out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n" );
        out.println( "</DIV>\n" );
        out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

        out.println( radian.web.HTMLHelpObj.PrintTitleBar( "Receiving\n" ) );
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
        //Category CLASS=\"\announce
        out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Category:</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" HEIGHT=\"35\">\n" );
        out.println( "<SELECT CLASS=\"HEADER\" id=\"Category\" NAME=\"Category\" size=\"1\">\n" );

        if ( category.equalsIgnoreCase( "2" ) )
        {
          out.println( "<option value=\"1\">Chemicals</option>\n" );
          out.println( "<option selected value=\"2\">Non-Chemicals</option>\n" );
        }
        else
        {
          out.println( "<option selected value=\"1\">Chemicals</option>\n" );
          out.println( "<option value=\"2\">Non-Chemicals</option>\n" );
        }

        out.println( "</SELECT>\n" );
        out.println( "</TD>\n" );

		// Search Text
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<B>Search:</B>&nbsp;\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"right\" CLASS=\"announce\">\n" );
		out.println( "<SELECT CLASS=\"HEADER\"  id=\"searchlike\" NAME=\"searchlike\" size=\"1\">\n" );
		Hashtable searchthese=new Hashtable();
		searchthese.put( "PO","a.RADIAN_PO" );
		searchthese.put( "Item Id","a.ITEM_ID" );
		searchthese.put( "Item Desc","a.ITEM_DESCRIPTION" );
		searchthese.put( "Supplier","a.LAST_SUPPLIER" );
		out.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( searchthese.entrySet(),searchLike ) );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );

		out.println( "<TD WIDTH=\"5%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
		out.println( "<SELECT CLASS=\"HEADER\"  id=\"searchfor\" NAME=\"searchfor\" size=\"1\">\n" );
		Hashtable sortresult=new Hashtable();
		sortresult.put( "contains","Like" );
		sortresult.put( "is","Equals" );
		out.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( sortresult.entrySet(),searchfor ) );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );

		out.println( "<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		out.println( "<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" TYPE=\"text\" id=\"SearchField\" NAME=\"SearchField\" value=\"" + search_text + "\" size=\"20\">\n" );
		out.println( "</TD>\n" );

        //List Orders
        out.println( "<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
        out.println( "   <INPUT TYPE=\"submit\" VALUE=\"List Orders\" onclick= \"return search(this)\" id=\"SearchButton\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
        out.println( "</TD>\n" );

        //Generate Labels
        out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
        if ( this.getupdateStatus() )
        {
          out.println( "<INPUT TYPE=\"BUTTON\" ID=\"genbutton1\" VALUE=\"Reprint\" NAME=\"SearchButton\" onclick= \"javascript:doUnConfPopup()\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
        }
        else
        {
          out.println( "&nbsp;\n" );
        }

        out.println( "</TD>\n" );
        out.println( "</TR>\n" );

        out.println( "<TR>\n" );
        //Hub
        out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
        out.println( "<B>Hub:</B>&nbsp;\n" );
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"10%\" HEIGHT=\"35\">\n" );
        out.println( "<SELECT CLASS=\"HEADER\" id=\"HubName\" NAME=\"HubName\" OnChange=\"hubchanged(document.receiving.HubName)\" size=\"1\">\n" );
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

		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\">&nbsp;</TD>\n" );
        //No of day expected
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\">\n" );
		out.println( "Exptd Within\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\"  COLSPAN=\"2\">\n" );
		out.println( "<INPUT CLASS=\"HEADER\" type=\"text\" id=\"nodaysold\" name=\"nodaysold\" value=\"" + ( numOldDays.length() < 1 ? "30" : numOldDays ) +	"\" SIZE=\"1\">\n" );
		out.println( "&nbsp;Days</TD>\n" );

        //Update
        out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
        if ( this.getupdateStatus() )
        {
          out.println( "<INPUT TYPE=\"submit\" VALUE=\"Receive\" onclick= \"return update(this)\" id=\"UpdateButton\" NAME=\"UpdateButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
        }
        else
        {
          out.println( "&nbsp;\n" );
        }
        out.println( "</TD>\n" );

        out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n" );
        if ( category.equalsIgnoreCase( "2" ) )
        {
          out.println( "&nbsp;\n" );
        }
        else
        {
          //Create XLS
          out.println( "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create Excel File\" onclick= \"createxls()\" id=\"SearchButton\" NAME=\"SearchButton\">&nbsp;\n" );
        }
        out.println( "</TD>\n" );
        out.println( "</TR>\n" );

	    out.println( "<TR VALIGN=\"MIDDLE\">\n" );
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<B>Inv Grp:</B>&nbsp;</TD>\n" );
		out.println( "<TD WIDTH=\"10%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		out.println( "<SELECT CLASS=\"HEADER\"  id=\"invengrp\" NAME=\"invengrp\" size=\"1\">\n" );

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

	  // Sort
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<B>Sort:</B>&nbsp;\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"10%\" COLSPAN=\"2\">\n" );
		out.println( "<SELECT CLASS=\"HEADER\"  id=\"SortBy\" NAME=\"SortBy\" size=\"1\">\n" );
		sortresult=new Hashtable();
		sortresult.put( "PO/POLine","1" );
		sortresult.put( "Supplier/Date Exptd","2" );
		sortresult.put( "Date Exptd/Supplier","3" );
		sortresult.put( "Item Id/Date Exptd","4" );

		out.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( sortresult.entrySet(),sortby ) );
		out.println( "</SELECT>\n" );
		out.println( "</TD>\n" );


		out.println( "</TD>\n" );

		out.println( "<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		out.println( "&nbsp;\n" );
		out.println( "</TD>\n" );

		out.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\">\n" );

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
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\">&nbsp;</TD>\n" );
		out.println( "</TR></TABLE>\n" );
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
        out.println( "<tr><td>" );
        out.println( "<input type=\"hidden\" id=\"Total_number\" name=\"Total_number\" value=\"" + total + "\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"UserAction\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"SubUserAction\" NAME=\"SubUserAction\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"DuplLineNumber\" NAME=\"DuplLineNumber\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"AddBin_Item_Id\" NAME=\"AddBin_Item_Id\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"AddBin_Bin_Id\" NAME=\"AddBin_Bin_Id\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"AddBin_Line_No\" NAME=\"AddBin_Line_No\" VALUE=\" \">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"HubRef\" NAME=\"HubRef\" VALUE=\"" + hub_branch_id + "\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"HubFacCount\" NAME=\"HubFacCount\" VALUE=\"" + radian.web.HTMLHelpObj.numOfClients( db,SelectedHubName ) + "\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"paperSize\" NAME=\"paperSize\" VALUE=\"" + paper_size + "\">\n" );
        out.println( "<INPUT TYPE=\"hidden\" id=\"GCategory\" NAME=\"GCategory\" VALUE=\"" + category + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" id=\"thedecidingRandomNumber\" NAME=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );

        out.println( "</TD></tr>" );
        out.println( "</table>\n" );
        //end table #2
        sortresult = null;
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println (radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ));
      }

      return;
    }

    private void buildDetails( Vector data,Hashtable all_bin_set,String Add_Item_id,String Hub,String SubuserAction, Vector allowedingrps,Vector lot_status_set )
    {
      //StringBuffer Msg=new StringBuffer();

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        int AddBinLineNum1=getAddBinLineNum();
		out.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showreceivingpagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n" );
        out.println( "<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=1 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        int i=0; //used for detail lines
        int linefadded=0;
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
            out.println( "<TH width=\"5%\"  height=\"38\">PO</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">PO Line</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Date Exptd</TH>\n" );
            out.println( "<TH width=\"7%\"  height=\"38\">Supplier</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Open Amt</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Item<BR>(Inven Grp)</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">Packaging</TH>\n" );
            out.println( "<TH width=\"12%\" height=\"38\">Description</TH>\n" );
            out.println( "<TH width=\"2%\"  height=\"38\">OK &nbsp;</TH>\n" );
            out.println( "<TH width=\"4%\" height=\"38\" colspan=\"2\">Bin</TH>\n" );
            out.println( "<TH width=\"8%\"  height=\"38\">Lot</TH>\n" );
            out.println( "<TH width=\"4%\"  height=\"38\">Receipt ID</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Act Supp Ship Date mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">DOR mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">DOM mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Manufacturer DOS mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Exp Date mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Qty Rec'd</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Receipt Status</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Notes</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Delivery Ticket</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Duplicate</TH>\n" );
            out.println( "</tr>\n" );
          }

          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

		  String Next_po="";
		  String Next_poline="";
		  String Next_item="";
		  if ( ! ( i == total ) )
		  {
			Hashtable hDNext=new Hashtable();
			hDNext= ( Hashtable ) data.elementAt( i + 1 );
			Next_po=hDNext.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hDNext.get( "PURCHASE_ORDER" ).toString();
			Next_poline=hDNext.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hDNext.get( "PURCHASE_ORDER_LINE" ).toString();
			Next_item=hDNext.get( "ITEM_ID" ) == null ? "&nbsp;" : hDNext.get( "ITEM_ID" ).toString();
		  }
		  else
		  {
			Next_po=" ";
			Next_poline="";
			Next_item=" ";
		  }

          // get main information
          String Purchase_order= ( hD.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER" ).toString() );
          String PO_Line= ( hD.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER_LINE" ).toString() );
          String Expected_dt= ( hD.get( "EXPCTD_DATE" ) == null ? "" : hD.get( "EXPCTD_DATE" ).toString() );
          String Item_id= ( hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString() );
          String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "" : hD.get( "ITEM_DESC" ).toString() );
          String Qty_open= ( hD.get( "QTY_OPEN" ) == null ? "&nbsp;" : hD.get( "QTY_OPEN" ).toString() );
          String Pkging= ( hD.get( "PACKING" ) == null ? "&nbsp;" : hD.get( "PACKING" ).toString() );
          String trans_type= ( hD.get( "TRANS_TYPE" ) == null ? "&nbsp;" : hD.get( "TRANS_TYPE" ).toString() );
          //String Specs= ( hD.get( "SPECIFICATION" ) == null ? "&nbsp;" : hD.get( "SPECIFICATION" ).toString() );
          String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
          String Date_mfgd= ( hD.get( "DATE_MFGD" ) == null ? "&nbsp;" : hD.get( "DATE_MFGD" ).toString() );
          String Qty_recd= ( hD.get( "QTY_RECD" ) == null ? "&nbsp;" : hD.get( "QTY_RECD" ).toString() );
          String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
          if ( Line_Check.equalsIgnoreCase( "yes" ) )
          {
            checkednon="checked";
          }
          else
          {
            checkednon="";
          }
          String Sel_bin= ( hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString() );
          String Oreceipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
          String indefshelflie= ( hD.get( "INDEFINITE_SHELF_LIFE" ) == null ? " " : hD.get( "INDEFINITE_SHELF_LIFE" ).toString() );
          String supplier= ( hD.get( "SUPPLIER" ) == null ? "&nbsp;" : hD.get( "SUPPLIER" ).toString() );
          String Date_recieved= ( hD.get( "DATE_RECIEVED" ) == null ? "&nbsp;" : hD.get( "DATE_RECIEVED" ).toString() );
          String Expire_date=hD.get( "EXPIRE_DATE" ).toString();
          String shipment_date=hD.get( "DATE_OF_SHIPMENT" ).toString();
          String LINE_STATUS=hD.get( "LINE_STATUS" ).toString();
          String actshipDate= ( hD.get( "ACTUAL_SHIP_DATE" ) == null ? " " : hD.get( "ACTUAL_SHIP_DATE" ).toString() );
          String criticalpo= ( hD.get( "CRITICAL" ) == null ? " " : hD.get( "CRITICAL" ).toString() );
          String origmfglot= ( hD.get( "ORIGINAL_MFG_LOT" ) == null ? " " : hD.get( "ORIGINAL_MFG_LOT" ).toString() );
          String origrecid= ( hD.get( "ORIGINAL_RECEIPT_ID" ) == null ? " " : hD.get( "ORIGINAL_RECEIPT_ID" ).toString() );
          String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString() );
		  String xferreqd = ( hD.get( "XFER_REQ_ID" ) == null ? " " : hD.get( "XFER_REQ_ID" ).toString() );

		  String mngkitassingl = hD.get("MANAGE_KITS_AS_SINGLE_UNIT").toString();
		  String componentid = hD.get("COMPONENT_ID").toString();
		  String compmatdesc = hD.get("MATERIAL_DESC").toString();
		  String invgrpname = hD.get("INVENTORY_GROUP_NAME").toString();
		  String numofkits = hD.get("NUMBER_OF_KITS").toString();
		  String recnotes = hD.get("NOTES").toString();
		  String skipReceivingQC = hD.get("SKIP_RECEIVING_QC").toString();
		  String deliveryTicket = hD.get("DELIVERY_TICKET").toString();

          if ( "IT".equalsIgnoreCase( trans_type ) && "showorigstuff".equalsIgnoreCase( SubuserAction ) )
          {
            Mfg_lot=origmfglot;
            Oreceipt_id=origrecid;
          }

          String chkbox_value="no";
          if ( checkednon.equalsIgnoreCase( "checked" ) )
          {
            chkbox_value="yes";
          }

		  if ( "S".equalsIgnoreCase( criticalpo ) )
		  {
		   Color="CLASS=\"pink";
		  }

		  if ( "Y".equalsIgnoreCase( criticalpo ) )
		  {
		   Color="CLASS=\"red";
		  }

          if ( SubuserAction.equalsIgnoreCase( "UpdPage" ) && "NO".equalsIgnoreCase( LINE_STATUS ) )
          {
            Color="CLASS=\"error";
          }

		  boolean recqcallowed = false;
		  if (allowedingrps.contains(invengrp))
		  {
			recqcallowed = true;
		  }

		  if ( !this.getupdateStatus() )
		  {
			recqcallowed = false;
		  }

		  boolean Samepoline=false;
		  boolean FirstRow=false;
		  if ( "N".equalsIgnoreCase(mngkitassingl) && Next_po.equalsIgnoreCase(Purchase_order) && Next_poline.equalsIgnoreCase(PO_Line) && Next_item.equalsIgnoreCase(Item_id) )
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

	  if ( !this.getupdateStatus() || !recqcallowed)
	  {
		out.println( "<tr align=\"center\">\n" );
		if ( FirstRow )
		{
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" + Purchase_order + "</td>\n" );
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">\n" + PO_Line + "\n</td>\n" );
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" + Expected_dt + "</td>\n" );
		  out.println( "<td " + Color + "l\" width=\"7%\" height=\"38\" rowspan=\""+numofkits+"\">" + supplier + "</td>\n" );
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" + Qty_open + "</td>\n" );
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" + Item_id  +"<BR>("+invengrp+")</td>\n" );
		}
		out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + Pkging  +"</td>\n" );
		if ( "N".equalsIgnoreCase( mngkitassingl ) )
		{
		  out.println( "<td " + Color + "l\" width=\"12%\" height=\"38\" >" + compmatdesc + "</td>\n" );
		}
		else
		{
		  out.println( "<td " + Color + "l\" width=\"12%\" height=\"38\" >" + Item_desc + "</td>\n" );
		}

		if (!recqcallowed)
		{
		   out.println( "<td " + Color + "l\" width=\"2%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"1%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"3%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"8%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"4%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"4%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
    	   out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
		   out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
		}

		out.println( "</tr>\n" );
	  }
	  else
	  {

        if ( ( SubuserAction.equalsIgnoreCase( "Update" ) && "YES".equalsIgnoreCase( LINE_STATUS ) ) || ( "YES".equalsIgnoreCase( LINE_STATUS ) ) )
        {
          out.println( "<INPUT TYPE=\"hidden\" value=\"no\" NAME=\"row_chk" + i + "\" ID=\"row_chk" + i + "\">\n" );
          out.println( "<INPUT TYPE=\"hidden\" value=\"" + Sel_bin + "\" name=\"selectElementBin" + i + "\" id=\"selectElementBin" + i + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"mfg_lot" + i + "\" id=\"mfg_lot" + i + "\" value=\"" + Mfg_lot + "\">" );
          out.println( "<input type=\"hidden\" name=\"receipt_id" + i + "\" id=\"receipt_id" + i + "\" value=\"" + Oreceipt_id + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"act_suppship_date" + i + "\" id=\"act_suppship_date" + i + "\" value=\"" + actshipDate + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"date_recieved" + i + "\" id=\"date_recieved" + i + "\" value=\"" + Date_recieved + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"date_mfgd" + i + "\" id=\"date_mfgd" + i + "\" value=\"" + Date_mfgd + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"ship_date" + i + "\" id=\"ship_date" + i + "\" value=\"" + shipment_date + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"expiry_date" + i + "\" id=\"expiry_date" + i + "\" value=\"" + Expire_date + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"qty_recd" + i + "\" id=\"qty_recd" + i + "\"  value=\"" + Qty_recd + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"po\" id=\"po\" value=\"" + Purchase_order + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"po_line" + i + "\" id=\"po_line" + i + "\" value=\"" + PO_Line + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"item_id" + i + "\" id=\"item_id" + i + "\" value=\"" + Item_id + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"item_id\" id=\"item_id\" value=\"" + Item_id + "\">\n" );
          out.println( "<input type=\"hidden\" name=\"transaction_type" + i + "\" id=\"transaction_type" + i + "\" value=\"" + trans_type + "\">\n" );
        }
        else
        {
		  out.println( "<tr align=\"center\">\n" );
		  if ( FirstRow )
		  {
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" );
			if ( "IT".equalsIgnoreCase( trans_type ) )
			{
			  out.println( "<A HREF=\"javascript:showrecforinvtransfr(" + xferreqd + "," + Hub + ")\">TR " + xferreqd + "</A>" );
			}
			else
			{
			  out.println( Purchase_order );
			}
			out.println( "</td>\n" );

			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" + PO_Line + "</td>\n" );
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" );
			if ( Purchase_order.trim().length() > 0 )
			{
			  out.println( "<A HREF=\"javascript:showReceivingJacket(" + Purchase_order + "," + Item_id + "," + PO_Line + "," + Hub + ")\">" + Expected_dt + "</A></td>\n" );
			}
			else
			{
			  out.println( "" + Expected_dt + "</td>\n" );
			}

			out.println( "<td " + Color + "l\" width=\"7%\" height=\"38\" rowspan=\""+numofkits+"\">" + supplier + "</td>\n" );
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" + Qty_open + "</td>\n" );
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">" );
			out.println( "<A HREF=\"javascript:showPreviousrec(" + Item_id + "," + Hub + ")\">" + Item_id + "</A><BR>(" + invgrpname + ")</td>\n" );
		  }

          out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + Pkging + "</td>\n" );

		  if ( "N".equalsIgnoreCase( mngkitassingl ) )
		  {
			out.println( "<td " + Color + "l\" width=\"12%\" height=\"38\" >" + compmatdesc + "</td>\n" );
		  }
		  else
		  {
			out.println( "<td " + Color + "l\" width=\"12%\" height=\"38\" >" + Item_desc + "</td>\n" );
		  }
          out.println( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" +chkbox_value+ "\"  onClick= \"checkValues(name,this)\" " + checkednon + "  id=\"row_chk" + i + "\" NAME=\"row_chk" + i + "\"></td>\n" );

          out.println( "<td " + Color + "r\" width=\"1%\" height=\"38\">\n" );
          out.println( "<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" id=\"addBin" + i + "\" name=\"addBin" + i + "\" value=\"+\" OnClick=\"showEmtyBins(" + Item_id + "," + i + "," + Hub + ")\" ></TD>\n" );
          out.println( "<td " + Color + "l\" width=\"3%\" height=\"38\" >\n" );
          out.println( "<select id=\"selectElementBin" + i + "\" name=\"selectElementBin" + i + "\">\n" );

          Hashtable in_bin_set= ( Hashtable ) all_bin_set.get( Item_id );
          int bin_size=in_bin_set.size();
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
              out.println( "<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n" );
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

              out.println( "<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n" );
            }
          }

          out.println( "</select></td>\n" );
          out.println( "<td " + Color + "\" width=\"8%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"mfg_lot" + i + "\" name=\"mfg_lot" + i + "\" value=\"" + Mfg_lot + "\" maxlength=\"30\" size=\"20\"></td>\n" );

          if ( trans_type.equalsIgnoreCase( "IT" ) )
          {
            out.println( "<td " + Color + "\" width=\"4%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"receipt_id" + i + "\" name=\"receipt_id" + i + "\" value=\"" + Oreceipt_id + "\" maxlength=\"20\" size=\"7\"></td>\n" );
          }
          else
          {
            out.println( "<td " + Color + "\" width=\"4%\" height=\"38\">&nbsp;<input type=\"hidden\" id=\"receipt_id" + i + "\" name=\"receipt_id" + i + "\" value=\" \"></td>\n" );
          }

          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"act_suppship_date" + i + "\" name=\"act_suppship_date" + i + 
        		      "\" value=\"" + actshipDate + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.act_suppship_date" +
                      i + ");\">&diams;</a></FONT></td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"date_recieved" + i + "\" name=\"date_recieved" + i + "\" value=\"" + 
                      Date_recieved + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.date_recieved" +
                      i + ");\">&diams;</a></FONT></td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"date_mfgd" + i + "\" name=\"date_mfgd" + i + "\" value=\"" +
                      Date_mfgd + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.date_mfgd" +
                      i + ");\">&diams;</a></FONT></td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"ship_date" + i + "\" name=\"ship_date" + i + "\" value=\"" +
                      shipment_date + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.ship_date" +
                      i + ");\">&diams;</a></FONT></td>\n" );

          //05-14-02 Nawaz indefenite shelf life
          if ( "Y".equalsIgnoreCase( indefshelflie ) || "01/01/3000".equalsIgnoreCase( Expire_date ) )
          {
            out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" id=\"expiry_date" + i + "\" name=\"expiry_date" + i + "\" value=\"Indefinite\">Indefinite</td>\n" );
          }
          else
          {
            out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"expiry_date" + i + "\" name=\"expiry_date" + i + "\" value=\"" +
                        Expire_date + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.expiry_date" +
                        i + ");\">&diams;</a></FONT></td>\n" );
          }

		  if ( FirstRow )
		  {
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\"><input type=\"text\" CLASS=\"DETAILS\" id=\"qty_recd" + i + "\" name=\"qty_recd" + i + "\"  value=\"" + Qty_recd + "\" maxlength=\"10\" size=\"6\"></td>\n" );
			if ("Y".equalsIgnoreCase(skipReceivingQC))
			{
			  out.println( "<td " + Color + "\"\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">\n" );
			  out.println( "<select id=\"selectElementStatus" + i + "\" name=\"selectElementStatus" + i + "\">\n" );
			  out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
			  out.println("<OPTION VALUE=\"Incoming\" selected>Incoming</OPTION>\n");
			  out.println( radian.web.HTMLHelpObj.getlogisticsDropDown( lot_status_set,"",true,null ) );
			  out.println( "</select>\n" );
			  out.println("</td>\n");
			}
			else
			{
			  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">&nbsp;</td>\n" );
			}

			out.println( "<TD WIDTH=\"5%\" " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\"><TEXTAREA name=\"recnotes" + i + "\" rows=\"3\" cols=\"25\">" + recnotes + "</TEXTAREA></TD>\n" );
			out.println( "<TD WIDTH=\"5%\" " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\"><input type=\"text\" CLASS=\"DETAILS\" id=\"deliveryTicket" + i + "\" name=\"deliveryTicket" + i + "\"  value=\"" + deliveryTicket + "\" maxlength=\"30\" size=\"6\"></TD>\n" );
			if ("Y".equalsIgnoreCase(mngkitassingl))
			{
			  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\"><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" onclick= \" return duplLine(name,this)\" value=\"Dupl\" id=\"Button" + i + "\" name=\"Button" + i + "\">\n" );
			}
			else
			{
			  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\""+numofkits+"\">&nbsp;\n" );
			}
		  }

          out.println( "<input type=\"hidden\" id=\"po\" name=\"po\" value=\"" + Purchase_order+ "\">\n" );
          out.println( "<input type=\"hidden\" id=\"po_line" + i + "\" name=\"po_line" + i + "\" value=\"" + PO_Line + "\">\n" );
          out.println( "<input type=\"hidden\" id=\"item_id" + i + "\" name=\"item_id" + i + "\" value=\"" + Item_id + "\">\n" );

		  if ( "N".equalsIgnoreCase( mngkitassingl ) && !FirstRow )
		  {
			out.println( "<input type=\"hidden\" id=\"qtyoveride" + i + "\" name=\"qtyoveride" + i + "\" value=\"Y\">\n" );
		  }
		  else
		  {
			out.println( "<input type=\"hidden\" id=\"qtyoveride" + i + "\" name=\"qtyoveride" + i + "\" value=\"N\">\n" );
		  }

          out.println( "<input type=\"hidden\" id=\"item_id\" name=\"item_id\" value=\"" + Item_id + "\">\n" );
          out.println( "<input type=\"hidden\" id=\"transaction_type" + i + "\" name=\"transaction_type" + i + "\" value=\"" + trans_type + "\"></td>\n" );
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
      out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ));
    }

    return;
  }

    private void buildDetailsBG( Vector data,String HubNo,String SubuserAction,Vector allowedingrps )
    {
      //StringBuffer Msg=new StringBuffer();

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
		out.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showreceivingpagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n" );
        out.println( "<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=1 WIDTH=\"100%\" CLASS=\"moveup\">\n" );

        int i=0; //used for detail lines
        int lineadded=0;
        for ( int loop=0; loop < total; loop++ )
        {
          i++;
          boolean createHdr=false;

          if ( lineadded % 10 == 0 )
          {
            createHdr=true;
          }

          if ( createHdr )
          {
            out.println( "<tr align=\"center\">\n" );
            out.println( "<TH width=\"2%\"  height=\"38\">OK</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">PO<BR>(Inven Grp)</TH>\n" );
            out.println( "<TH width=\"7%\" height=\"38\">Supplier</TH>\n" );
            out.println( "<TH width=\"15%\" height=\"38\">Description</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">Packaging</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Supplier Ref #</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">DOR mm/dd/yyyy</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Qty Rec'd</TH>\n" );
			out.println( "<TH width=\"2%\"  height=\"38\">Notes</TH>\n" );
            out.println( "<TH width=\"2%\"  height=\"38\">Duplicate</TH>\n" );
            out.println( "</tr>\n" );
          }

          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          // get main information
          String Purchase_order= ( hD.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER" ).toString() );
          String PO_Line= ( hD.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER_LINE" ).toString() );
          //String Expected_dt= ( hD.get( "EXPCTD_DATE" ) == null ? "" : hD.get( "EXPCTD_DATE" ).toString() );
          String Item_id= ( hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString() );
          String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "" : hD.get( "ITEM_DESC" ).toString() );
          //String Qty_open= ( hD.get( "QTY_OPEN" ) == null ? "&nbsp;" : hD.get( "QTY_OPEN" ).toString() );
          String Pkging= ( hD.get( "PACKING" ) == null ? "&nbsp;" : hD.get( "PACKING" ).toString() );
          String trans_type= ( hD.get( "TRANS_TYPE" ) == null ? "&nbsp;" : hD.get( "TRANS_TYPE" ).toString() );
          //String Specs= ( hD.get( "SPECIFICATION" ) == null ? "&nbsp;" : hD.get( "SPECIFICATION" ).toString() );
          String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
          //String Date_mfgd= ( hD.get( "DATE_MFGD" ) == null ? "&nbsp;" : hD.get( "DATE_MFGD" ).toString() );
          String Qty_recd= ( hD.get( "QTY_RECD" ) == null ? "&nbsp;" : hD.get( "QTY_RECD" ).toString() );
          String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
          if ( Line_Check.equalsIgnoreCase( "yes" ) )
          {
            checkednon="checked";
          }
          else
          {
            checkednon="";
          }
          //String Sel_bin= ( hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString() );
          //String Sel_status= ( hD.get( "STATUS_ID" ) == null ? "&nbsp;" : hD.get( "STATUS_ID" ).toString() );
          String supplier= ( hD.get( "SUPPLIER" ) == null ? "&nbsp;" : hD.get( "SUPPLIER" ).toString() );
          String Date_recieved= ( hD.get( "DATE_RECIEVED" ) == null ? "&nbsp;" : hD.get( "DATE_RECIEVED" ).toString() );
          //String Expire_date=hD.get( "EXPIRE_DATE" ).toString();
          String LINE_STATUS=hD.get( "LINE_STATUS" ).toString();
          String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString() );
		  String recnotes = hD.get("NOTES").toString();

          String Color=" ";

          if ( lineadded % 2 == 0 )
          {
            Color="CLASS=\"blue";
          }
          else
          {
            Color="CLASS=\"white";
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
		  if (allowedingrps.contains(invengrp))
		  {
			recqcallowed = true;
		  }

		  if ( !this.getupdateStatus() || !recqcallowed )
		  {
			lineadded++;
			out.println( "<tr align=\"center\">\n" );
			out.println( "<td " + Color + "l\" width=\"2%\" height=\"38\" >&nbsp;</td>\n" );
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Purchase_order + "<BR>(" + invengrp + ")</td>\n" );
			out.println( "<td " + Color + "l\" width=\"7%\" height=\"38\" >" + supplier + "</td>\n" );
			out.println( "<td " + Color + "l\" width=\"15%\" height=\"38\" >" + Item_desc + "</td>\n" );
			out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + Pkging + "</td>\n" );
			out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
			out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
			out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n" );
			out.println( "<td " + Color + "l\" width=\"2%\" height=\"38\" >&nbsp;</td>\n" );
			out.println( "<td " + Color + "l\" width=\"2%\" height=\"38\" >&nbsp;</td>\n" );
			out.println( "</tr>\n" );
          }
          else
          {
            if ( ( SubuserAction.equalsIgnoreCase( "Update" ) && "YES".equalsIgnoreCase( LINE_STATUS ) ) || ( "YES".equalsIgnoreCase( LINE_STATUS ) ) )
            {
              out.println( "<INPUT TYPE=\"hidden\" value=\"no\" id=\"row_chk" + i + "\" NAME=\"row_chk" + i + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"mfg_lot" + i + "\" name=\"mfg_lot" + i + "\" value=\"" + Mfg_lot + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"date_recieved" + i + "\" name=\"date_recieved" + i + "\" value=\"" + Date_recieved + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"qty_recd" + i + "\" name=\"qty_recd" + i + "\"  value=\"" + Qty_recd + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"po\" name=\"po\" value=\"" + Purchase_order + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"po_line" + i + "\" name=\"po_line" + i + "\" value=\"" + PO_Line + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"item_id" + i + "\" name=\"item_id" + i + "\" value=\"" + Item_id + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"item_id\" name=\"item_id\" value=\"" + Item_id + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"transaction_type" + i + "\" name=\"transaction_type" + i + "\" value=\"" + trans_type + "\">\n" );
              out.println( "</tr>\n" );
            }
            else
            {
              lineadded++;
              out.println( "<tr align=\"center\">\n" );
              out.println( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" + ( chkbox_value ) + "\"  onClick= \"checkBGValues(name,this)\" " + checkednon + "  id=\"row_chk" + i + "\" NAME=\"row_chk" + i + "\"></td>\n" );
              out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" );
              out.println( "<A HREF=\"javascript:showPreviouspoline(" + PO_Line + "," + Purchase_order + "," + HubNo + ")\">" + Purchase_order + "</A><BR>("+invengrp+")\n" );
              out.println( "</td>\n" );
              out.println( "<td " + Color + "l\" width=\"7%\" height=\"38\" >" + supplier + "</td>\n" );
              out.println( "<td " + Color + "l\" width=\"15%\" height=\"38\" >" + Item_desc + "</td>\n" );
			  out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + Pkging + "</td>\n" );
              out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"mfg_lot" + i + "\" name=\"mfg_lot" + i + "\" value=\"" + Mfg_lot + "\" maxlength=\"30\" size=\"20\"></td>\n" );
			  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"date_recieved" + i + "\" name=\"date_recieved" + i + "\" value=\"" + Date_recieved + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.receiving.date_recieved" + i + ");\">&diams;</a></FONT></td>\n" );
		      //out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"date_recieved" + i + "\" name=\"date_recieved" + i + "\" value=\"" + Date_recieved + "\" maxlength=\"10\" size=\"11\"></td>\n" );
			  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" id=\"qty_recd" + i + "\" name=\"qty_recd" + i + "\"  value=\"" + Qty_recd + "\" maxlength=\"30\" size=\"6\"></td>\n" );
			  out.println( "<TD WIDTH=\"5%\" " + Color + "\"><TEXTAREA name=\"recnotes" + i + "\" rows=\"3\" cols=\"25\">" + recnotes + "</TEXTAREA></TD>\n" );

              out.println( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" onclick= \" return duplLine(name,this)\" value=\"Dupl\" id=\"Button" + i + "\" name=\"Button" + i + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"po\" name=\"po\" value=\"" + Purchase_order + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"po_line" + i + "\" name=\"po_line" + i + "\" value=\"" + PO_Line + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"item_id" + i + "\" name=\"item_id" + i + "\" value=\"" + Item_id + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"item_id\" name=\"item_id\" value=\"" + Item_id + "\">\n" );
              out.println( "<input type=\"hidden\" id=\"transaction_type" + i + "\" name=\"transaction_type" + i + "\" value=\"" + trans_type + "\"></td>\n" );
              out.println( "</tr>\n" );
            }
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
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ));
		return;
      }
      return;
    }
  }
//END OF CLASS

