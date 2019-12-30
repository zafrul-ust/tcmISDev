package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import oracle.jdbc.OracleTypes;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;
import java.math.BigDecimal;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2002
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 *
 *  10-29-02
 *  Added the Buy Order comments which the buyer can edit and they show up in the mr Status detail
 *
 *  Also showing MR Qty in the buy details and the edit associate pr screen and all other relted stuff
 *
 * 10-30-02
 * Added the ability to sort by last_supplier and also show the last supplier
 * Added a link in tcmbuy history to po to open it.
 *
 * inserting finalshipToCompanyId after quering the ship_to_location_view
 *
 * 11-01-02
 * If they do not chek any box and say create Po made changes to do nothing
 *
 * 11-04-02
 * Changhed stting PO_IN_JDE to 'n' from 'N'
 *
 * 11-05-02
 * Made Changes so that when they disassociate all the PR's associated with that line I do not remove the item Id if the line has been
 * confirmed atleast once. I will remove the item id if the line has never been confirmed
 *
 * 11-08-02
 * Not checking any checkboxes for specs by default. not even for color code 1 and 2
 *
 * 11-14-02
 * not updating PO_DETACH_BY for all cases only for detach
 * added the critical and delivery stuff
 *
 * 11-15-02
 * Part Number can have escape characters
 * search for Radian Po
 *
 * 11-19-02
 * adding look ahead
 *
 * 11-20-02
 * Added code to not allow edit PR is it is frozen
 *
 * 11-22-02
 * making LOOK AHEADs distinct
 *
 * 01-10-02
 * Canceled newMMs were showing up as colored when they were requested for cancelation
 *
 * 01-16-02
 * Show time the buy_order was created
 *
 * 01-17-03
 * Showing LPP for the buy orders
 *
 * 02-03-03
 * Showing only 10 Buy History showing all later if asked for
 * If status in buy_order is Cancel show black and can not create PO
 * Can not Close or Cancel PR with a PO attached
 *
 * 02-10-03
 * Make the default PAYMENT_TERMS as Net 45
 * added nvl(a.STATUS,'New') in the search so that null status values can show up on the page
 * call p_cpi_rli_allocate when lookahead is changed
 *
 * 02-13-03
 * Taking out Closed from the drop down box
 * 02-28-03
 * Sorty by in build Header fro part_id
 * 03-06-03
 * Changed Spec Coloring pattern and the spec image URL
 * 03-17-03
 * Can set a buy to cancel status only if it is black
 * 03-25-03 :Checking the ok and COC and COA boxes when the po id pulled up before confirmation and purple is number 4 now changed method is showspec
 * drawing engineering eval request purple
 * 04-02-03 : Checking the specs ok boxes when the po is created. Added company ID to the associate prs screen
 * 04-21-03 : Safe gaurding against people hitting the create PO button instead of the search button and then hitting the update button.
 * 04-28-03 : Showing up multiple selections in a drop down box as selected when returning the search
 * 05-27-03 Showing if the item is stocked or not
 * 06-27-03 Inventory group stuff
 * 07-07-03 Storing the hubs witha different name as it is causeing confilicts when people loginto a deifferent page
 * 07-15-03 When they remove all the PR's associated with the PO I am not updating the inventory group to nil.so that they can cancel it without selecting a inventory group
 * 07-30-03 Code Cleanup and added buyer Update failure comments and editassociated prs failure comments
 * 07-31-03 Populating the client po from buy order when I create a new PO. if the PR is comfirmed they can still chsnge the buyer and notes
 * 08-06-03 Adding a Legend to explain the colors on this page
 * 08-20-03 If there is a PO on a buy Order can not make the buyer on it null or Status New.
 * 09-21-03 Added an option to view all open Buy Orders
 * 09-23-03 Added a new user group which can add comments to the buy order. If radian_po is not null changed server side code to not allow them to change buyer to null
 * 10-06-03 For El Segundo and Goleta not saving the client PO from the buy order onto the PO
 * 10-07-03 Allowing to change the buyer associated with the Buy Order after a PO is created. e.g Michale can create a PO and then assign the buy order to Swan
 * 10-20-03 - Added Consigned PO to po but it is always null when creating a PO from the buy page
 * 10-29-03 Prechecking only the specs that are marked CERT_REQUIRED = 'Y'
 * 11-03-03 A PO is UNCONFIRMED whenever a line is unconfirmed and is there in po_line_draft
 * 11-13-03 Not invalidating specs and flowdowns when a PR is associated or removed from the PO screen as the specs are only dependent on ship to location
 * 11-24-03 Displaying the Requestor name as first_name last_name
 * 12-16-03 Super Spec changes
 * 12-22-03 - Making the session Keys Unique to PO pages. And Code Refactoring moved vv_stuff to different class
 * 01-30-04 - Showing only Active Payment Terms for PO pages and showing all for AP pages
 * 02-09-04 - Adding Loggin statements to Debug if session is giving me any errors. Sorting the Hub Drop Down
 * 02-16-04 - Showing a link if there are any open Blanket Orders
 * 02-22-04 - Made changes to accomodate DBuy requests
 * 03-17-04 - Giving an error message when a frozen PR is changed.
 * 03-18-04 - Checking to see a PR_number in synchdata is same as it went out. Also making a frozen PR read only
 * 03-26-04 - Giving a warning message if a DBuy order is being changed from DBuy status to a Non Dbuy Status
 * 04-07-04 - Getting vv vector for statuses that are not assignable from the buy page. or locking a PR if it is in ProblemDBuy status
 * 05-07-04 - Giving the option to search by supply Path
 * 05-10-04 - Making Search by facility possible
 * 06-04-04 - Giving the option to search for supply path on associate pr screen
 * 07-22-04 - Not setting the Hub from shipto. Getting ti from the Hub on Buy Order
 * 08-16-04 - Not allowing to disassociate PRs from confirmed POs
 * 08-24-04 - Not checking inactive specs whena PO is created.
 * 09-03-04 - Calling create_po procedure to create a PO for contracted pricing. New LPP query
 * 11-29-04 - Using Home currency when creating a PO
 * 01-13-05 - If you are a buyer you can enter buyer notes
 * 02-01-05 - Removing Micheal Shires from the not active buyer list.
 * 02-15-05 - If the Qty on a PO Line is Zero, assuming they are going to cancel the PO and hence not deleteing the Hub from the PO.
 *            Also not blanking out the qty and unit price if they are going to cancel it
 * 03-02-05 - Removing Mark Baletka
 * 04-26-05 - Not Showing Tap and Repackaging orders when they select supply path All
 * 05-09-05 - Checking to see is consolidation is allowed in the associate_pr_view instead of doing a query for each line
 * 07-12-05 - Added options for Wbuy
 * 04-24-06 - Updating Company_buyer_id
 * 06-13-06 - Added ability to search by last_supplier
 * 06-28-06 - Showing DBuy mismatch comments.
 * 02-14-07 - Show buyorders from all hubs when they don't select a Hub. Not restricting it to the buyers hub_pref anymore
 */

public class newBuyOrder
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private PrintWriter out=null;
  private boolean unassociatedprs=false;
  private boolean associatedprs=false;
  private boolean noneToUpd=true;
  private String createdpo="";
  private String createdpoerrmesage="";
  private int noofPrsAccosiated=0;
  private String thedecidingRandomNumber=null;
  private String privatepersonnelId="";
  private boolean allowupdate;
  private boolean allowbuyernotes;
  private static org.apache.log4j.Logger buylog = org.apache.log4j.Logger.getLogger(newBuyOrder.class);
  private boolean intcmIsApplication = false;
  private ResourceLibrary res = null; // res means resource.

  public void setallowbuyernotes( boolean id1 )
  {
	this.allowbuyernotes=id1;
  }

  private boolean getallowbuyernotes()
	 throws Exception
  {
	return this.allowbuyernotes;
  }

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

  public newBuyOrder( ServerResourceBundle b,TcmISDBModel d )
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

    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));
            
    PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
    {
     intcmIsApplication = true;
    }

    String branch_plant= session.getAttribute( "BRANCH_PLANT" ) == null ? "" : session.getAttribute( "BRANCH_PLANT" ).toString();
    String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		String companyId= session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
		/*if ("Radian".equalsIgnoreCase(companyId))
		{
			 companyId = "Radian";
	  }*/

    Vector statusStringV=new Vector();
    privatepersonnelId=personnelid;

	//buylog.info(""+ session.getAttribute( "FULLNAME" ) == null ? "" : session.getAttribute( "FULLNAME" ).toString()+"");
	//buylog.info("JSESSIONID  : "+session.getId()+"");

	/*Cookie [] cookies = request.getCookies();
	Map cookieMap = new HashMap();
	if( cookies != null ) {
		for( int i =0; i < cookies.length; i++ ) {
			Cookie c = cookies[i];
			cookieMap.put(c.getName(), c);
			buylog.debug("COOKIES FOLLOW:");
			buylog.debug(c.getName()+":"+c.getValue()+":"+c.getMaxAge()+":"+c.getDomain()+":"+c.getPath());
		}
	}*/

    String User_Action=null;
    User_Action=request.getParameter( "UserAction" );
    if ( User_Action == null )
      User_Action="New";
    User_Action=User_Action.trim();
	//buylog.info("USer Action : "+User_Action+"");

    String searchlike=request.getParameter( "searchlike" );
    if ( searchlike == null )
      searchlike="";
    searchlike=searchlike.trim();

    String searchthis=request.getParameter( "searchthis" );
    if ( searchthis == null )
      searchthis="";
    searchthis=searchthis.trim();

    String showonlynopo=request.getParameter( "showonlynopo" );
    if ( showonlynopo == null )
      showonlynopo="";
    showonlynopo=showonlynopo.trim();

    String showonlyconfpo=request.getParameter( "showonlyconfpo" );
    if ( showonlyconfpo == null )
      showonlyconfpo="";
    showonlyconfpo=showonlyconfpo.trim();

    String itemnum=request.getParameter( "itemnum" );
    if ( itemnum == null )
      itemnum="";
    itemnum=itemnum.trim();

    String User_Sort=request.getParameter( "SortBy" );
    if ( User_Sort == null )
      User_Sort="";
    User_Sort=User_Sort.trim();
    if (User_Sort.length() == 0)
    {
        User_Sort = "ITEM_ID";        
    }
    if (User_Sort.equalsIgnoreCase("LAST_SUPPLIER_NAME"))
    {
        User_Sort = "LAST_SUPPLIER";
    }

    String facility=request.getParameter( "FacName" );
    if ( facility == null )
      facility="";
    facility=facility.trim();

    String mfg=request.getParameter( "mfg" );
    if ( mfg == null )
      mfg="";

    String category=request.getParameter( "category" );
    if ( category == null )
      category="";
    category=category.trim();

    String status= "";
    /*String status=request.getParameter( "status" );
    if ( status == null )
      status="New";
    status=status.trim();*/

    String invengrp=request.getParameter( "invengrp" );
    if ( invengrp == null )
      invengrp="";
    invengrp=invengrp.trim();

    String attachedpr=request.getParameter( "attachedpr" );
    if ( attachedpr == null )
      attachedpr="";
    attachedpr=attachedpr.trim();

	String showopenbuyorders=null;
	showopenbuyorders=request.getParameter( "showopenbuyorders" );
	if ( showopenbuyorders == null )
	  showopenbuyorders="";
	showopenbuyorders=showopenbuyorders.trim();

	String currencyId=null;
	currencyId=request.getParameter( "currencyId" );
	if ( currencyId == null )
	  currencyId="";
	currencyId=currencyId.trim();
        
    String[] statusaray= {"All Statuses"};
    statusaray=request.getParameterValues( "status" );
    String statusStringfromArray="";

    if ( statusaray != null )
    {
	  if ( "yes".equalsIgnoreCase(showopenbuyorders))
	  {
		statusStringfromArray+="'All Except Closed'";
		statusStringV.add( "All Except Closed");
	  }
	  else
	  {
		for ( int loop=0; loop < statusaray.length; loop++ )
		{
		  if ( loop > 0 )
		  {
			statusStringfromArray+=",";
		  }
		  statusStringfromArray+="'" + statusaray[loop] + "'";
		  statusStringV.add( statusaray[loop] );
		}
	  }
    }
    if ( statusStringfromArray.length() == 0 )
    {
      statusStringfromArray="'New'";
      statusStringV.add( "New" );
    }

    String buyer=request.getParameter( "buyer" );
    if ( buyer == null )
      buyer="";
    buyer=buyer.trim();

    if ( !buyer.equalsIgnoreCase( "All" ) && buyer.trim().length() < 1 )
    {
      buyer=personnelid;
    }

    String showall=request.getParameter( "showall" );
    if ( showall == null )
      showall="";
    showall=showall.trim();

    String radianPO=request.getParameter( "radianPO" );
    if ( radianPO == null )
      radianPO="";
    radianPO=radianPO.trim();

    String lineID=request.getParameter( "lineID" );
    if ( lineID == null )
      lineID="";
    lineID=lineID.trim();

    String shipTo=request.getParameter( "shipTo" );
    if ( shipTo == null )
      shipTo="";

    String numofHubs=request.getParameter( "numofHubs" );
    if ( numofHubs == null )
      numofHubs="";
    shipTo=shipTo.trim();

    String searchCompany=request.getParameter( "companyID" );
    if ( searchCompany == null )
      searchCompany="";
    searchCompany=searchCompany.trim();

    String searchmr=request.getParameter( "searchmr" );
    if ( searchmr == null )
      searchmr="";
    searchmr=searchmr.trim();

    String searchcustomerpo=request.getParameter( "customerpo" );
    if ( searchcustomerpo == null )
      searchcustomerpo="";
    searchcustomerpo=searchcustomerpo.trim();

    String ammendment=request.getParameter( "ammendment" );
    if ( ammendment == null )
      ammendment="";
    ammendment=ammendment.trim();

    String itemfromLine=request.getParameter( "itemfromLine" );
    if ( itemfromLine == null )
      itemfromLine="";

    String shipTofromLine=request.getParameter( "shipTofromLine" );
    if ( shipTofromLine == null )
      shipTofromLine="";
    shipTofromLine=shipTofromLine.trim();

    String shipToCompanyfromLine=request.getParameter( "shipToCompanyfromLine" );
    if ( shipToCompanyfromLine == null )
      shipToCompanyfromLine="";
    shipToCompanyfromLine=shipToCompanyfromLine.trim();

    String subuserAction=null;
    subuserAction=request.getParameter( "SubUserAction" );
    if ( subuserAction == null )
      subuserAction="New";
    subuserAction=subuserAction.trim();

	String headsupplypath=null;
	headsupplypath=request.getParameter( "headsupplypath" );
	if ( headsupplypath == null )
	  headsupplypath="";
	headsupplypath=headsupplypath.trim();

    //System.out.println( "User Action is " + User_Action + " User_Sort " + User_Sort + " " + itemnum + "  " + facility + "  " + branch_plant + " showall " + showall + "  searchCompany  " + searchCompany );

    thedecidingRandomNumber=request.getParameter( "thedecidingRandomNumber" );
    if ( thedecidingRandomNumber == null )
      thedecidingRandomNumber="";
    thedecidingRandomNumber=thedecidingRandomNumber.trim();
	//buylog.info("Random Number from Page : "+thedecidingRandomNumber+"");

    try
    {
	  //Hashtable hub_list_set=new Hashtable();
	  //hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

      String donevvstuff=session.getAttribute( "VVSUPPLYSTUFF" ) == null ? "" : session.getAttribute( "VVSUPPLYSTUFF" ).toString();
      if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
      {
        //buylog.info("Doing VV Stuff");
		Hashtable hub_list_set=new Hashtable();
        hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,"purch" );
        session.setAttribute( "PO_HUB_SET",hub_list_set );
        session.setAttribute( "PO_CATEGORY",radian.web.vvHelpObj.getcategory(db) );
        session.setAttribute( "PO_COMPANY_IDS",radian.web.vvHelpObj.getCompanyIds(db) );
        session.setAttribute( "PO_BUYERNAMES",radian.web.HTMLHelpObj.getbuyernames( db ) );
        session.setAttribute( "PO_STATUS",radian.web.vvHelpObj.getorderstatus(db,"") );
		session.setAttribute( "PO_NON_ASGN_STATUS",radian.web.vvHelpObj.nonassignablestatuses(db) );
		session.setAttribute( "PO_LOCK_STATUS",radian.web.vvHelpObj.getorderstatus(db,"Y") );
		session.setAttribute( "PO_SET_STATUS",radian.web.vvHelpObj.getorderstatus(db,"setonly") );
        session.setAttribute( "PO_SORTIT",radian.web.vvHelpObj.getsortby(db) );
        session.setAttribute( "PO_VV_FOB",radian.web.vvHelpObj.getfob(db) );
        session.setAttribute( "PO_ADD_CHARGE_ITEM_TYPE",radian.web.vvHelpObj.getaddchargeType(db) );
        session.setAttribute( "PO_VV_PAYMENT",radian.web.vvHelpObj.getPaymentTerms(db,false) );
        session.setAttribute( "PO_CONTACTTYPE",radian.web.vvHelpObj.getvvContectType(db) );
        //hopefully this will fix the error
        session.setAttribute( "PO_ADD_SECONDARY_SUPPLIER_ITEM_TYPE",radian.web.vvHelpObj.getSecondarySupplierTypes(db) );


		Vector hubcompanyFac=radian.web.vvHelpObj.gethubcompanyFacilities( db );
		//Hashtable hub_list_set=new Hashtable();
		//hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );
		Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		String companyArrayJs="";
		if ( hub_list.size() > 0 )
		{
		  companyArrayJs=radian.web.HTMLHelpObj.getHubCompanyJs( hub_list_set,hubcompanyFac,session ).toString();
		}
        this.setdropDownJs( companyArrayJs );

        session.setAttribute( "PO_CATALOGS_JS",companyArrayJs );
        session.setAttribute( "PO_HUBCOMPFACS",hubcompanyFac );
        session.setAttribute( "VVSUPPLYSTUFF","Yes" );
      }

      Random rand=new Random();
      int tmpReq=rand.nextInt();
      Integer tmpReqNum=new Integer( tmpReq );

      if ( !(User_Action.equalsIgnoreCase( "addBuyOrdersToPO" ) || User_Action.equalsIgnoreCase( "associatedPRs" ) || User_Action.equalsIgnoreCase( "editassociatedPRs" ) || User_Action.equalsIgnoreCase( "UpdateeditassociatedPRs" ) ))
      {
        if ( thedecidingRandomNumber.length() > 0 )
        {
          String randomnumberfromsesion=session.getAttribute( "BUYPGERNDCOOKIE" ) == null ? "" : session.getAttribute( "BUYPGERNDCOOKIE" ).toString();
		  //buylog.info("Random Num from Session : "+randomnumberfromsesion+"");
          if ( randomnumberfromsesion.equalsIgnoreCase( thedecidingRandomNumber ) )
          {
            thedecidingRandomNumber=tmpReqNum.toString();
            session.setAttribute( "BUYPGERNDCOOKIE",thedecidingRandomNumber );
			//buylog.info("Rand Num for Return Set : "+thedecidingRandomNumber+"");
          }
          else
          {
            thedecidingRandomNumber=tmpReqNum.toString();
            session.setAttribute( "BUYPGERNDCOOKIE",thedecidingRandomNumber );
			//buylog.info("Rand Num for Return Set : "+thedecidingRandomNumber+"");
			//buylog.info("Back Button Message");
			Hashtable hub_list_set=new Hashtable();
			hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

            String companyArrayJs=session.getAttribute( "PO_CATALOGS_JS" ) == null ? "" : session.getAttribute( "PO_CATALOGS_JS" ).toString();

            this.setdropDownJs( companyArrayJs );

            buildHeader( branch_plant,hub_list_set,User_Action,searchthis,User_Sort,facility,session,category,searchCompany,
                                      status,buyer,showall,searchlike,showonlynopo,showonlyconfpo,statusStringV,showopenbuyorders,headsupplypath);
            out.println( "<CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>" );
            return;
          }
        }
        else
        {
          thedecidingRandomNumber=tmpReqNum.toString();
          session.setAttribute( "BUYPGERNDCOOKIE",thedecidingRandomNumber );
		  //buylog.info("Rand Num for Return Set : "+thedecidingRandomNumber+"");
        }
      }

      if ( User_Action.equalsIgnoreCase( "New" ) )
      {
        Hashtable hub_list_set=new Hashtable();
        Vector compnayids=new Vector();
        Vector vvFob=new Vector();
        Vector vvPayment=new Vector();
        Vector hubcompanyFac=new Vector();
        String companyArrayJs="";

        donevvstuff=session.getAttribute( "VVSUPPLYSTUFF" ) == null ? "" : session.getAttribute( "VVSUPPLYSTUFF" ).toString();

        if ( donevvstuff.equalsIgnoreCase( "Yes" ) )
        {
          hub_list_set= ( Hashtable ) session.getAttribute( "PO_HUB_SET" );
          companyArrayJs=session.getAttribute( "PO_CATALOGS_JS" ) == null ? "" : session.getAttribute( "PO_CATALOGS_JS" ).toString();
        }
        else
        {
          hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,"purch" );
          session.setAttribute( "PO_HUB_SET",hub_list_set );
          session.setAttribute( "PO_CATEGORY",radian.web.vvHelpObj.getcategory(db) );
          compnayids=radian.web.vvHelpObj.getCompanyIds(db);
          session.setAttribute( "PO_COMPANY_IDS",compnayids );
          session.setAttribute( "PO_BUYERNAMES",radian.web.HTMLHelpObj.getbuyernames( db ) );
          session.setAttribute( "PO_STATUS",radian.web.vvHelpObj.getorderstatus(db,"") );
		  session.setAttribute( "PO_NON_ASGN_STATUS",radian.web.vvHelpObj.nonassignablestatuses(db) );
		  session.setAttribute( "PO_LOCK_STATUS",radian.web.vvHelpObj.getorderstatus(db,"Y") );
		  session.setAttribute( "PO_SET_STATUS",radian.web.vvHelpObj.getorderstatus(db,"setonly") );
          session.setAttribute( "PO_SORTIT",radian.web.vvHelpObj.getsortby(db) );
          session.setAttribute( "PO_VV_FOB",radian.web.vvHelpObj.getfob(db) );
          session.setAttribute( "PO_ADD_CHARGE_ITEM_TYPE",radian.web.vvHelpObj.getaddchargeType(db) );
          session.setAttribute( "PO_VV_PAYMENT",radian.web.vvHelpObj.getPaymentTerms(db,false) );
          session.setAttribute( "PO_CONTACTTYPE",radian.web.vvHelpObj.getvvContectType(db) );
          hubcompanyFac=radian.web.vvHelpObj.gethubcompanyFacilities(db);

		  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		  if ( hub_list.size() > 0 )
		  {
			companyArrayJs=radian.web.HTMLHelpObj.getHubCompanyJs( hub_list_set,hubcompanyFac,session ).toString();
		  }

          session.setAttribute( "PO_CATALOGS_JS",companyArrayJs );
          session.setAttribute( "PO_HUBCOMPFACS",hubcompanyFac );
          session.setAttribute( "VVSUPPLYSTUFF","Yes" );
        }

        Vector data=new Vector();
        session.setAttribute( "PO_DATA",data );
        this.setdropDownJs( companyArrayJs );

        buildHeader( branch_plant,hub_list_set,User_Action,searchthis,User_Sort,facility,session,category,searchCompany,
                                  status,buyer,showall,searchlike,showonlynopo,showonlyconfpo,statusStringV,showopenbuyorders,headsupplypath);
        out.println( radian.web.HTMLHelpObj.printHTMLSelect() );

        hub_list_set=null;
        compnayids=null;
        vvFob=null;
        vvPayment=null;
        hubcompanyFac=null;
      }
      else if ( User_Action.equalsIgnoreCase( "associatedPRs" ) )
      {
        Vector searchdata=new Vector();
		Hashtable hub_list_set=new Hashtable();
	    hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

        try
        {
          searchdata=this.getSearchData( branch_plant,itemnum,User_Sort,facility,statusStringfromArray,buyer,showall,
                                         radianPO,User_Action,shipTo,searchCompany,searchmr,searchcustomerpo,searchlike,searchthis,showonlynopo,
                                         showonlyconfpo,invengrp,attachedpr,showopenbuyorders,headsupplypath,hub_list_set );
        }
        catch ( Exception ex )
        {

        }

        buildAssociatedPRS( searchdata,User_Action,lineID,radianPO,itemnum,"",ammendment,branch_plant,itemfromLine,shipTofromLine,shipTo,"","",currencyId );
        //clean up
        searchdata=null;
      }
      else if ( User_Action.equalsIgnoreCase( "editassociatedPRs" ) || User_Action.equalsIgnoreCase( "addBuyOrdersToPO" ) )
      {
        Vector searchdata=new Vector();

        String mainshipTo=request.getParameter( "mainshipTo" );
        if ( mainshipTo == null )
          mainshipTo="";
        mainshipTo=mainshipTo.trim();

        String mainshipTocompanyId=request.getParameter( "mainshipTocompanyId" );
        if ( mainshipTocompanyId == null )
          mainshipTocompanyId="";
        mainshipTocompanyId=mainshipTocompanyId.trim();

        session.setAttribute( "MAINSHIPTO",mainshipTo );
        session.setAttribute( "MAINSHIPTOCOMPANYID",mainshipTocompanyId );

		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

        try
        {
          searchdata=this.getSearchData( branch_plant,itemnum,User_Sort,facility,statusStringfromArray,buyer,showall,radianPO,
                                         User_Action,shipTo,searchCompany,searchmr,searchcustomerpo,searchlike,searchthis,showonlynopo,
                                         showonlyconfpo,invengrp,attachedpr,showopenbuyorders,headsupplypath,hub_list_set );
        }
        catch ( Exception ex1 )
        {

        }

        session.setAttribute( "EDITPRSDATA",searchdata );
        buildEditAssociatedPRS( searchdata,session,lineID,radianPO,itemnum,branch_plant,shipTo,
                                             numofHubs,User_Sort,searchCompany,searchmr,searchcustomerpo,buyer,ammendment,invengrp,attachedpr,headsupplypath,User_Action,currencyId,searchlike,searchthis);

        //clean up
        searchdata=null;
      }
      else if ( User_Action.equalsIgnoreCase( "UpdateaddBuyOrdersToPO" ) )
      {
        Vector retrn_data= ( Vector ) session.getAttribute( "EDITPRSDATA" );
        Vector synch_data=SynchEditPrsData( request,retrn_data );
		Vector lockstatusV= ( Vector ) session.getAttribute( "PO_LOCK_STATUS" );
        Hashtable updateresults=UpdateAddPrsToPo( synch_data,personnelid,radianPO,lockstatusV,companyId,currencyId );
        Boolean resultfrmupdate = ( Boolean ) updateresults.get( "RESULT" );
        String upderrormsg = ( String ) updateresults.get( "ERRORMSG" );

		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

        if ( resultfrmupdate.booleanValue() )
        {
          buildAddPrsToPo(radianPO,branch_plant);
        }
        else
        {
          out.println( radian.web.HTMLHelpObj.printMessageinTable( "<CENTER><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>An error uccured while updating the changes. Please close this window and try again.<BR>" + upderrormsg + "</B></FONT></CENTER>" ) );
        }

        //clean up
        retrn_data=null;
        synch_data=null;
      }
      else if ( User_Action.equalsIgnoreCase( "UpdateeditassociatedPRs" ) )
      {
        Vector retrn_data= ( Vector ) session.getAttribute( "EDITPRSDATA" );
        Vector synch_data=SynchEditPrsData( request,retrn_data );
		Vector lockstatusV= ( Vector ) session.getAttribute( "PO_LOCK_STATUS" );
        Hashtable updateresults=UpdateEditPrs( synch_data,personnelid,radianPO,lockstatusV,companyId );
        Boolean resultfrmupdate = ( Boolean ) updateresults.get( "RESULT" );
        String upderrormsg = ( String ) updateresults.get( "ERRORMSG" );

		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

        if ( resultfrmupdate.booleanValue() )
        {
          Vector searchdata=new Vector();
          try
          {
            searchdata=this.getSearchData( branch_plant,itemnum,User_Sort,facility,statusStringfromArray,buyer,showall,radianPO,
                                           "associatedPRs",shipTo,searchCompany,searchmr,searchcustomerpo,searchlike,searchthis,showonlynopo,
                                           showonlyconfpo,invengrp,attachedpr,showopenbuyorders,headsupplypath,hub_list_set );
          }
          catch ( Exception ex2 )
          {
          }

          shipTo=session.getAttribute( "MAINSHIPTO" ) == null ? "" : session.getAttribute( "MAINSHIPTO" ).toString();

          String mshipTocompanyId=session.getAttribute( "MAINSHIPTOCOMPANYID" ) == null ? "" : session.getAttribute( "MAINSHIPTOCOMPANYID" ).toString();

          buildAssociatedPRS( searchdata,User_Action,lineID,radianPO,itemnum,"yes",ammendment,branch_plant,itemfromLine,shipTofromLine,shipTo,mshipTocompanyId,shipToCompanyfromLine,currencyId );
          searchdata=null;
        }
        else
        {
          out.println( radian.web.HTMLHelpObj.printMessageinTable( "<CENTER><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>An error uccured while updating the changes. Please close this window and try again.<BR>" + upderrormsg + "</B></FONT></CENTER>" ) );
        }

        //clean up
        retrn_data=null;
        synch_data=null;
      }
      else if ( User_Action.equalsIgnoreCase( "Search" ) )
      {
        //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "PO_HUB_SET" );
        String companyArrayJs=session.getAttribute( "PO_CATALOGS_JS" ) == null ? "" : session.getAttribute( "PO_CATALOGS_JS" ).toString();
        this.setdropDownJs( companyArrayJs );
        Vector searchdata=new Vector();
        session.removeAttribute( "PRINTDATA" );
        session.setAttribute( "UPDATEDONE","NO" );
		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

        try
        {
          searchdata=this.getSearchData( branch_plant,itemnum,User_Sort,facility,statusStringfromArray,buyer,showall,radianPO,
                                         User_Action,shipTo,searchCompany,searchmr,searchcustomerpo,searchlike,searchthis,showonlynopo,
                                         showonlyconfpo,invengrp,attachedpr,showopenbuyorders,headsupplypath,hub_list_set );
        }
        catch ( Exception ex3 )
        {
        }

        Hashtable sum= ( Hashtable ) ( searchdata.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
        if ( 0 == count )
        {
          buildHeader( branch_plant,hub_list_set,User_Action,searchthis,User_Sort,facility,session,category,searchCompany,
                                    status,buyer,showall,searchlike,showonlynopo,showonlyconfpo,statusStringV,showopenbuyorders,headsupplypath);
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "<CENTER><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>No Items Found</B></FONT></CENTER>" ) );

          //clean up
          searchdata=null;
          hub_list_set=null;
        }
        else
        {
          session.setAttribute( "PO_DATA",searchdata );
          session.setAttribute( "HUB_BACK",branch_plant );
          session.setAttribute( "FAC_BACK",facility );
          session.setAttribute( "BUYER_BACK",buyer );
          session.setAttribute( "CATEGORY_BACK",category );
          session.setAttribute( "MFG_BACK",mfg );
          session.setAttribute( "ITEM_BACK",searchthis );
          session.setAttribute( "STATUS_BACK",status );
          session.setAttribute( "SORT_BACK",User_Sort );
          session.setAttribute( "SEARCHLIKE_BACK",searchlike );
          session.setAttribute( "SHOWONLYNOPO_BACK",showonlynopo );
          session.setAttribute( "SHOWONLYCONFPO_BACK",showonlyconfpo );

          buildHeader( branch_plant,hub_list_set,User_Action,searchthis,User_Sort,facility,session,category,searchCompany,
                                    status,buyer,showall,searchlike,showonlynopo,showonlyconfpo,statusStringV,showopenbuyorders,headsupplypath);
          buildDetails( searchdata,session);

          //clean up
          searchdata=null;
          hub_list_set=null;
        } //when there are open orders for hub
      }
      else if ( User_Action.equalsIgnoreCase( "RefreshSearch" ) )
      {
        Vector data=null;
        session.setAttribute( "PO_DATA",data );
        session.setAttribute( "UPDATEDONE","NO" );
        //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "PO_HUB_SET" );
		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );
        buildHeader( branch_plant,hub_list_set,User_Action,searchthis,User_Sort,facility,session,category,searchCompany,
                                  status,buyer,showall,searchlike,showonlynopo,showonlyconfpo,statusStringV,showopenbuyorders,headsupplypath);
        buildDetails( data,session);
        data=null;
      }
      else if ( User_Action.equalsIgnoreCase( "createpo" ) )
      {
        Vector retrn_data= ( Vector ) session.getAttribute( "PO_DATA" );
        //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "PO_HUB_SET" );
		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );
		Vector lockstatusV= ( Vector ) session.getAttribute( "PO_LOCK_STATUS" );
        Vector synch_data=SynchServerData( request,retrn_data,lockstatusV );
        String companyArrayJs=session.getAttribute( "PO_CATALOGS_JS" ) == null ? "" : session.getAttribute( "PO_CATALOGS_JS" ).toString();
        this.setdropDownJs( companyArrayJs );
        retrn_data=null;
        Hashtable updateresults=createNewPO( synch_data,personnelid,companyId );
        session.setAttribute( "UPDATEDONE","YES" );
        Boolean resultfromup= ( Boolean ) updateresults.get( "RESULT" );
        Vector errordata= ( Vector ) updateresults.get( "ERRORDATA" );

        //10-15-02 changed from syncdata to errordata
        session.setAttribute( "PO_DATA",errordata );

        Hashtable sum= ( Hashtable ) ( synch_data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
        buildHeader( session.getAttribute( "HUB_BACK" ).toString(),hub_list_set,User_Action,session.getAttribute( "ITEM_BACK" ).toString(),
                                  session.getAttribute( "SORT_BACK" ).toString(),session.getAttribute( "FAC_BACK" ).toString(),session,
                                  session.getAttribute( "CATEGORY_BACK" ).toString(),
                                  searchCompany,session.getAttribute( "STATUS_BACK" ).toString(),session.getAttribute( "BUYER_BACK" ).toString(),showall,
                                  session.getAttribute( "SEARCHLIKE_BACK" ).toString(),session.getAttribute( "SHOWONLYNOPO_BACK" ).toString(),
                                  session.getAttribute( "SHOWONLYCONFPO_BACK" ).toString(),statusStringV,showopenbuyorders,headsupplypath);

        if ( resultfromup.booleanValue() )
        {
          out.println( radian.web.HTMLHelpObj.printMessageinTable( "<CENTER><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>New PO Was Successfully Created.</B></FONT></CENTER>" ) );
        }
        else
        {
          out.println( radian.web.HTMLHelpObj.printMessageinTable( "<CENTER><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>An Error Occured While Creating a PO. Please try again." + createdpoerrmesage + "</B></FONT></CENTER>" ) );
        }

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "<CENTER><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>No Items Found</B></FONT></CENTER>" ) );
        }
        else
        {
          buildDetails( errordata,session);
        }
        createdpo="";
        retrn_data=null;
        synch_data=null;
        errordata=null;
        sum=null;
      }
      else if ( User_Action.equalsIgnoreCase( "UpdateConfirm" ) )
      {
        Vector retrn_data= ( Vector ) session.getAttribute( "PO_DATA" );
		Vector lockstatusV= ( Vector ) session.getAttribute( "PO_LOCK_STATUS" );
        Vector synch_data=SynchServerData( request,retrn_data,lockstatusV );
        String companyArrayJs=session.getAttribute( "PO_CATALOGS_JS" ) == null ? "" : session.getAttribute( "PO_CATALOGS_JS" ).toString();
        this.setdropDownJs( companyArrayJs );
        retrn_data=null;
        //Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "PO_HUB_SET" );
		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );
        Hashtable updateresults=UpdateDetails( synch_data,companyId );

        //10-15-02 changed from syncdata to errordata
        Vector errordata= ( Vector ) updateresults.get( "ERRORDATA" );
        Boolean resultfrmupdate = ( Boolean ) updateresults.get( "RESULT" );
        String upderrormsg = ( String ) updateresults.get( "ERRORMSG" );

        session.setAttribute( "PO_DATA",errordata );
        session.setAttribute( "UPDATEDONE","YES" );
        Hashtable sum= ( Hashtable ) ( synch_data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
        buildHeader( session.getAttribute( "HUB_BACK" ).toString(),hub_list_set,User_Action,session.getAttribute( "ITEM_BACK" ).toString(),
                                  session.getAttribute( "SORT_BACK" ).toString(),session.getAttribute( "FAC_BACK" ).toString(),session,
                                  session.getAttribute( "CATEGORY_BACK" ).toString(),
                                  searchCompany,session.getAttribute( "STATUS_BACK" ).toString(),session.getAttribute( "BUYER_BACK" ).toString(),showall,
                                  session.getAttribute( "SEARCHLIKE_BACK" ).toString(),session.getAttribute( "SHOWONLYNOPO_BACK" ).toString(),
                                  session.getAttribute( "SHOWONLYCONFPO_BACK" ).toString(),statusStringV,showopenbuyorders,headsupplypath);

        if ( resultfrmupdate.booleanValue() )
        {
          out.println( radian.web.HTMLHelpObj.printMessageinTable( "<CENTER><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>All your changes were successfully updated.</B></FONT></CENTER>" ) );
        }
        else
        {
          out.println( radian.web.HTMLHelpObj.printMessageinTable( "<CENTER><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>An error occured while updating chnages.<BR>" + upderrormsg + "</B></FONT></CENTER>" ) );
        }

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "<CENTER><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>No Items Found</B></FONT></CENTER>" ) );
        }
        else
        {
          buildDetails( synch_data,session);
        }

        updateresults=null;
        retrn_data=null;
        synch_data=null;
        sum=null;
      }
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      out.println( radian.web.HTMLHelpObj.printError( "New Buy Order",intcmIsApplication ) );
    }
    finally
    {
      out.close();
    }
    return;
  }

  private Vector getSearchData( String BranchPlant,String itemNum,String sortBy,String facility,
                                String status,String bbuyer,String showall1,String radianPO,String action,
                                String shipTo,String searchCompany,String searchmr,String searchcustomerpo,String searchLike,
                                String searchthis,String showonlynopo,String showonlyconfpo,String invengrp,String attachedpr,String openbuyorders,String sersupplypath,Hashtable hublistset )
     throws Exception
  {
    Vector Data=new Vector();
    Hashtable DataH=new Hashtable();
    Hashtable summary=new Hashtable();
    boolean flagforwhere=false;
    boolean flagforassociations=false;
    flagforassociations=true;
    DBResultSet dbrs=null;
    int count=0;
    summary.put( "TOTAL_NUMBER",new Integer( count ) );
    Data.addElement( summary );
    int num_rec=0;
    if ( BranchPlant.length() == 0 )
    {
      return Data;
    }

    try
    {
      String Query_Statement="";
      Query_Statement="Select a.REMAINING_SHELF_LIFE_PERCENT,a.SPEC_LIST,a.PURCHASING_NOTE,a.SHIP_TO_COMPANY_ID,a.FACILITY_ID,a.BUYER_COMPANY_ID,a.PURCHASING_UNITS_PER_ITEM, a.PURCHASING_UNIT_OF_MEASURE,a.CONSOLIDATION_ALLOWED,a.HOME_CURRENCY_ID,a.DELIVERY_POINT_DESC SHIP_TO_DELIVERY_POINT,a.SUPPLY_PATH,a.BPO,a.INVENTORY_GROUP,a.STOCKED,to_char(sysdate,'dd Mon yyyy hh24:mi') PICK_DATE, to_char(a.DATE_ISSUED,'mm/dd/yyyy hh24:mi') DATE_ISSUED1,to_char(a.NEED_DATE,'mm/dd/yyyy') NEED_DATE1, ";
      Query_Statement+="a.CATALOG_ID,a.REQUEST_ID,a.ENGINEERING_EVALUATION,a.BUYER_NAME,a.MR_NUMBER,a.RAYTHEON_PO,a.SIZE_UNIT,a.COMMENTS,a.BUYER,a.RADIAN_PO,a.MFG_ID,a.STATUS,a.DATE_ASSIGNED, ";
      Query_Statement+="a.PART_ID,a.SHELF_LIFE_DAYS,a.OPEN_PO_QUANTITY,a.REQUEST_LINE_STATUS,a.PR_NUMBER,a.FROZEN,a.AVAILABLE_QUANTITY,a.PHONE,a.CRITICAL,a.MFG_PART_NO, ";
      Query_Statement+="a.PRIORITY,a.DATE_CHANGED,a.REQUESTOR_LAST_NAME,a.BASELINE_PRICE,a.STOCKING_LEVEL,a.NOTES,a.BUYER_ID,a.REORDER_POINT, ";
      Query_Statement+="a.MR_QUANTITY,a.DATE_PO_CREATED,a.UOM,a.EMAIL,a.FACILITY,a.CATALOG_PRICE,a.DELIVERY_TYPE,a.ITEM_DESC,a.ITEM_ID, ";
      Query_Statement+="a.CANCEL_STATUS,a.SHIP_TO_LOCATION_ID,a.ORDER_QUANTITY,a.MR_LINE_ITEM,a.REQUESTOR_FIRST_NAME,a.EVER_CONFIRMED, ";
      Query_Statement+="a.TRADE_NAME,a.LAST_SUPPLIER,a.ITEM_TYPE,a.COMPANY_ID,a.BRANCH_PLANT,a.CSR_NAME, ";

      if ( action.equalsIgnoreCase( "associatedPRs" ) || action.equalsIgnoreCase( "UpdateeditassociatedPRs" ) )
      {
        Query_Statement+="a.LPP ";
      }
      else
      {
        Query_Statement+=" '' LPP ";
      }

      Query_Statement+=" from associate_pr_view a where ";
      if ( action.equalsIgnoreCase( "associatedPRs" )
           || action.equalsIgnoreCase( "editassociatedPRs" )
           || action.equalsIgnoreCase( "addBuyOrdersToPO" )
         )
      {
        Query_Statement+=" (a.status not in ('Closed','Cancel')) ";
        flagforwhere=true;
      }

      if ( action.equalsIgnoreCase( "associatedPRs" ) )
      {
        searchthis="";
        facility="";
        status="";
        shipTo="";
        searchCompany="";
        searchmr="";
        searchcustomerpo="";
        BranchPlant="";
        bbuyer="";
      }

      if ( action.equalsIgnoreCase( "editassociatedPRs" ) || action.equalsIgnoreCase( "addBuyOrdersToPO" ))
      {
        BranchPlant="";
      }

      if ( "RADIAN_PO".equalsIgnoreCase( searchLike ) )
      {
        radianPO=searchthis;
      }

	  if (sersupplypath.length() == 0 ) {sersupplypath = "All";}
	  if ( sersupplypath.trim().length() > 0 && !"All".equalsIgnoreCase( sersupplypath ))
	  {
		if ( flagforwhere )
		  Query_Statement+="and a.SUPPLY_PATH ='" + sersupplypath + "' ";
		else
		{
		  Query_Statement+=" a.SUPPLY_PATH ='" + sersupplypath + "' ";
		  flagforwhere=true;
		}
	  }
	  else if ( "All".equalsIgnoreCase( sersupplypath ))
	  {
		if ( flagforwhere )
		  Query_Statement+="and a.SUPPLY_PATH  in ('Purchaser','Dbuy','Wbuy','Ibuy') ";
		else
		{
		  Query_Statement+=" a.SUPPLY_PATH in ('Purchaser','Dbuy','Wbuy','Ibuy') ";
		  flagforwhere=true;
		}
	  }

      if ( searchthis.trim().length() > 0 && !"RADIAN_PO".equalsIgnoreCase( searchLike ) )
      {
        if ( flagforwhere )
          Query_Statement+="and lower(a." + searchLike + ") like lower('%" + searchthis + "%') ";
        else
        {
          Query_Statement+=" lower(a." + searchLike + ") like lower('%" + searchthis + "%') ";
          flagforwhere=true;
        }
      }

      if ( itemNum.trim().length() > 0 )
      {
        if ( flagforwhere )
          Query_Statement+="and a.ITEM_ID =" + itemNum + " ";
        else
        {
          Query_Statement+=" a.ITEM_ID =" + itemNum + " ";
          flagforwhere=true;
        }
      }

      if ( facility.length() > 0 && !"All".equalsIgnoreCase( facility ) )
      {
        if ( flagforwhere )
          Query_Statement+="and a.FACILITY_ID = '" + facility + "' ";
        else
        {
          Query_Statement+=" a.FACILITY_ID = '" + facility + "' ";
          flagforwhere=true;
        }
      }

//      if ( invengrp.length() > 0 && "Y".equalsIgnoreCase( attachedpr ) )
			if ( invengrp.length() > 0 )
      {
        if ( flagforwhere )
          Query_Statement+="and a.INVENTORY_GROUP = '" + invengrp + "' ";
        else
        {
          Query_Statement+=" a.INVENTORY_GROUP = '" + invengrp + "' ";
          flagforwhere=true;
        }
      }

      if ( "'All Except Closed'".equalsIgnoreCase( status ) )
      {
        if ( flagforwhere )
          Query_Statement+="and a.STATUS !='Closed' ";
        else
        {
          Query_Statement+=" a.STATUS !='Closed' ";
          flagforwhere=true;
        }
      }
	  else if ( "'All DBuy Statuses'".equalsIgnoreCase( status ) )
	  {
		if ( flagforwhere )
		  Query_Statement+="and a.STATUS like '%DBuy%' ";
		else
		{
		  Query_Statement+=" a.STATUS like '%DBuy%' ";
		  flagforwhere=true;
		}
	  }
      else if ( status.length() > 0 && !"'All Statuses'".equalsIgnoreCase( status )
              && !(action.equalsIgnoreCase( "editassociatedPRs" ) || action.equalsIgnoreCase( "addBuyOrdersToPO" )) )
      {
        if ( flagforwhere )
          Query_Statement+="and nvl(a.STATUS,'New') in (" + status + ") ";
        else
        {
          Query_Statement+=" nvl(a.STATUS,'New') in (" + status + ") ";
          flagforwhere=true;
        }
      }

      if ( bbuyer.length() > 0 && "No Buyers".equalsIgnoreCase( bbuyer ) )
      {
        if ( flagforwhere )
          Query_Statement+="and a.BUYER_ID is null ";
        else
        {
          Query_Statement+=" a.BUYER_ID is null ";
          flagforwhere=true;
        }
      }
      else if ( bbuyer.length() > 0 && !"All".equalsIgnoreCase( bbuyer ) )
      {
        if ( action.equalsIgnoreCase( "editassociatedPRs" )  || action.equalsIgnoreCase( "addBuyOrdersToPO" ))
        {
          if ( flagforwhere )
            Query_Statement+="and (a.BUYER_ID = " + bbuyer + " or a.BUYER_ID = " + privatepersonnelId + " or a.BUYER_ID is null) ";
          else
          {
            Query_Statement+=" (a.BUYER_ID = " + bbuyer + " or a.BUYER_ID = " + privatepersonnelId + " or a.BUYER_ID is null) ";
            flagforwhere=true;
          }
        }
        else
        {
          if ( flagforwhere )
            Query_Statement+="and a.BUYER_ID = " + bbuyer + " ";
          else
          {
            Query_Statement+=" a.BUYER_ID = " + bbuyer + " ";
            flagforwhere=true;
          }
        }
      }

      if ( radianPO.length() > 0 )
      {
        if ( flagforwhere )
        {
          if ( action.equalsIgnoreCase( "editassociatedPRs" ) )
          {
            Query_Statement+=" and nvl(a.RADIAN_PO," + radianPO + ") = " + radianPO + "";
          }
          else if ( action.equalsIgnoreCase( "addBuyOrdersToPO" ) )
          {
            Query_Statement+=" and a.RADIAN_PO is null";
          }
          else
          {
            Query_Statement+=" and a.RADIAN_PO = " + radianPO + " ";
          }
        }
        else
        {
          if ( action.equalsIgnoreCase( "editassociatedPRs" ) )
          {
            Query_Statement+=" nvl(a.RADIAN_PO," + radianPO + ") = " + radianPO + "";
          }
          else if ( action.equalsIgnoreCase( "addBuyOrdersToPO" ) )
          {
            Query_Statement+=" and a.RADIAN_PO is null";
          }
          else
          {
            Query_Statement+=" a.RADIAN_PO = " + radianPO + " ";
          }
          flagforwhere=true;
        }
      }

      if ( shipTo.length() > 0 )
      {
          if ( flagforwhere )
            Query_Statement+=" and a.SHIP_TO_LOCATION_ID like '%" + shipTo + "%' ";
          else
          {
            Query_Statement+=" a.SHIP_TO_LOCATION_ID like '%" + shipTo + "%' ";
            flagforwhere=true;
          }
      }

      if ( searchCompany.length() > 0 && !"All".equalsIgnoreCase( searchCompany ) && !"Radian".equalsIgnoreCase( searchCompany ) )
      {
        if ( flagforwhere )
          Query_Statement+=" and a.COMPANY_ID = '" + searchCompany + "' ";
        else
        {
          Query_Statement+=" a.COMPANY_ID = '" + searchCompany + "' ";
          flagforwhere=true;
        }
      }

      if ( searchmr.length() > 0 )
      {
        if ( flagforwhere )
          Query_Statement+=" and a.MR_NUMBER like '%" + searchmr + "%' ";
        else
        {
          Query_Statement+=" a.MR_NUMBER like '%" + searchmr + "%' ";
          flagforwhere=true;
        }
      }

      if ( searchcustomerpo.length() > 0 )
      {
        if ( flagforwhere )
          Query_Statement+=" and a.RAYTHEON_PO like '%" + searchcustomerpo + "%' ";
        else
        {
          Query_Statement+=" a.RAYTHEON_PO like '%" + searchcustomerpo + "%' ";
          flagforwhere=true;
        }
      }

	  if ("Yes".equalsIgnoreCase(openbuyorders))
	  {
		if ( flagforwhere )
		 Query_Statement+=" and nvl(PO_IN_JDE,'n') <> 'y' and radian_po is not null and UNCONFIRMED = 'N'";
	   else
	   {
		 Query_Statement+=" nvl(PO_IN_JDE,'n') <> 'y' and radian_po is not null and UNCONFIRMED = 'N'";
		 flagforwhere=true;
	   }
	  }
      else if ( "Yes".equalsIgnoreCase( showall1 ) && "Yes".equalsIgnoreCase( showonlynopo ) && "Yes".equalsIgnoreCase( showonlyconfpo ) )
      {

      }
      else if ( "Yes".equalsIgnoreCase( showall1 ) && "Yes".equalsIgnoreCase( showonlynopo ) && !"Yes".equalsIgnoreCase( showonlyconfpo ) )
      {
        if ( flagforwhere )
//          Query_Statement+=" and a.UNCONFIRMED = 'Y' ";
		  Query_Statement+=" and (a.EVER_CONFIRMED = 'N' or a.UNCONFIRMED = 'Y') ";
        else
        {
          //Query_Statement+=" a.UNCONFIRMED = 'Y' ";
		  Query_Statement+=" (a.EVER_CONFIRMED = 'N' or a.UNCONFIRMED = 'Y') ";
          flagforwhere=true;
        }
      }
      else if ( "Yes".equalsIgnoreCase( showonlynopo ) && "Yes".equalsIgnoreCase( showonlyconfpo ) )
      {
        if ( flagforwhere )
//          Query_Statement+=" and (a.EVER_CONFIRMED = 'Y' or a.RADIAN_PO is null) ";
		  Query_Statement+=" and ((a.EVER_CONFIRMED = 'Y' and a.UNCONFIRMED != 'Y') or a.RADIAN_PO is null) ";
        else
        {
//          Query_Statement+=" (a.EVER_CONFIRMED = 'Y' or a.RADIAN_PO is null) ";
		  Query_Statement+=" ((a.EVER_CONFIRMED = 'Y' and a.UNCONFIRMED != 'Y') or a.RADIAN_PO is null) ";
          flagforwhere=true;
        }
      }
	  else if ( "Yes".equalsIgnoreCase( showall1 ) && "Yes".equalsIgnoreCase( showonlyconfpo ) )
	  {
		if ( flagforwhere )
		  Query_Statement+=" and a.RADIAN_PO is not null ";
		else
		{
		  Query_Statement+=" a.RADIAN_PO is not null ";
		  flagforwhere=true;
		}
	  }
      else if ( "Yes".equalsIgnoreCase( showonlyconfpo ) )
      {
        if ( flagforwhere )
          //Query_Statement+=" and a.EVER_CONFIRMED = 'Y'";
		  Query_Statement+=" and (a.EVER_CONFIRMED = 'Y' and a.UNCONFIRMED != 'Y')";
        else
        {
          //Query_Statement+=" a.EVER_CONFIRMED = 'Y'";
		  Query_Statement+=" (a.EVER_CONFIRMED = 'Y' and a.UNCONFIRMED != 'Y')";
          flagforwhere=true;
        }
      }
      else if ( "Yes".equalsIgnoreCase( showonlynopo ) )
      {
        if ( flagforwhere )
          Query_Statement+=" and a.RADIAN_PO is null ";
        else
        {
          Query_Statement+=" a.RADIAN_PO is null ";
          flagforwhere=true;
        }
      }
      else if ( "Yes".equalsIgnoreCase( showall1 ) )
      {
        if ( flagforwhere )
          Query_Statement+=" and a.UNCONFIRMED = 'Y'  and a.RADIAN_PO is not null ";
        else
        {
          Query_Statement+=" a.UNCONFIRMED = 'Y'  and a.RADIAN_PO is not null ";
          flagforwhere=true;
        }
      }

      if ( BranchPlant.length() > 0 && !"All".equalsIgnoreCase( BranchPlant ) && !"None".equalsIgnoreCase( BranchPlant ) )
      {
        if ( flagforwhere )
        {
          Query_Statement+=" and a.BRANCH_PLANT = " + BranchPlant + " ";
        }
        else
        {
          Query_Statement+=" a.BRANCH_PLANT = " + BranchPlant + " ";
        }
      }
	  /*else
	  {
		Hashtable hubsetdetails= ( Hashtable ) ( hublistset.get( "HUB_LIST" ) );
		String allowedhubs="";
		for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
		{
		  String branch_plant= ( String ) ( e.nextElement() );
		  allowedhubs+="'" + branch_plant + "',";
		}
		allowedhubs=allowedhubs.substring( 0,allowedhubs.length() - 1 );
		if ( flagforwhere )
		{
		  Query_Statement+=" and a.BRANCH_PLANT in (" + allowedhubs + ") ";
		}
		else
		{
		  Query_Statement+=" a.BRANCH_PLANT in (" + allowedhubs + ") ";
		}
      }*/

      Query_Statement+=" order by a." + sortBy + " ";
      dbrs=db.doQuery( Query_Statement );
      ResultSet searchRs=dbrs.getResultSet();

      while ( searchRs.next() )
      {
        DataH=new Hashtable();
        num_rec++;
        String radianPOfromRs=searchRs.getString( "RADIAN_PO" ) == null ? " " : searchRs.getString( "RADIAN_PO" );

        //PR_NUMBER
        DataH.put( "PR_NUMBER",searchRs.getString( "PR_NUMBER" ) == null ? "" : searchRs.getString( "PR_NUMBER" ) );
        //LINE_ITEM
        DataH.put( "LINE_ITEM",searchRs.getString( "MR_LINE_ITEM" ) == null ? "" : searchRs.getString( "MR_LINE_ITEM" ) );
        //CRITICAL
        DataH.put( "CRITICAL",searchRs.getString( "CRITICAL" ) == null ? "" : searchRs.getString( "CRITICAL" ) );
        //CATALOG_PRICE
        DataH.put( "CATALOG_PRICE",searchRs.getString( "CATALOG_PRICE" ) == null ? "" : searchRs.getString( "CATALOG_PRICE" ) );
        //BASELINE_PRICE
        DataH.put( "BASELINE_PRICE",searchRs.getString( "BASELINE_PRICE" ) == null ? "" : searchRs.getString( "BASELINE_PRICE" ) );
        //LPP
        DataH.put( "LPP",searchRs.getString( "LPP" ) == null ? "" : searchRs.getString( "LPP" ) );
        //NOTES
        DataH.put( "PRNOTES",searchRs.getString( "NOTES" ) == null ? "" : searchRs.getString( "NOTES" ) );
        //CANCEL_STATUS
        DataH.put( "CANCEL_STATUS",searchRs.getString( "CANCEL_STATUS" ) == null ? "" : searchRs.getString( "CANCEL_STATUS" ) );
        //REQUEST_LINE_STATUS
        DataH.put( "REQUEST_LINE_STATUS",searchRs.getString( "REQUEST_LINE_STATUS" ) == null ? "" : searchRs.getString( "REQUEST_LINE_STATUS" ) );
        //REQUESTOR_FIRST_NAME
        DataH.put( "REQUESTOR_FIRST_NAME",searchRs.getString( "REQUESTOR_FIRST_NAME" ) == null ? "" : searchRs.getString( "REQUESTOR_FIRST_NAME" ) );
        //REQUESTOR_LAST_NAME
        DataH.put( "REQUESTOR_LAST_NAME",searchRs.getString( "REQUESTOR_LAST_NAME" ) == null ? "" : searchRs.getString( "REQUESTOR_LAST_NAME" ) );
        //EMAIL
        DataH.put( "EMAIL",searchRs.getString( "EMAIL" ) == null ? "" : searchRs.getString( "EMAIL" ) );
        //PHONE
        DataH.put( "PHONE",searchRs.getString( "PHONE" ) == null ? "" : searchRs.getString( "PHONE" ) );
        //BUYER
        DataH.put( "BUYER",searchRs.getString( "BUYER" ) == null ? "" : searchRs.getString( "BUYER" ) );
        //DATE_ASSIGNED
        DataH.put( "DATE_ASSIGNED",searchRs.getString( "DATE_ASSIGNED" ) == null ? "" : searchRs.getString( "DATE_ASSIGNED" ) );
        //ITEM_ID
        DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
        //NEED_DATE
        DataH.put( "NEED_DATE",searchRs.getString( "NEED_DATE1" ) == null ? "" : searchRs.getString( "NEED_DATE1" ) );
        //PART_ID
        DataH.put( "PART_ID",searchRs.getString( "PART_ID" ) == null ? "" : searchRs.getString( "PART_ID" ) );
        //ITEM_DESC
        DataH.put( "ITEM_DESC",searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ) );
        //SHELF_LIFE_DAYS
        DataH.put( "SHELF_LIFE_DAYS",searchRs.getString( "SHELF_LIFE_DAYS" ) == null ? "" : searchRs.getString( "SHELF_LIFE_DAYS" ) );
        //TRADE_NAME
        DataH.put( "TRADE_NAME",searchRs.getString( "TRADE_NAME" ) == null ? "" : searchRs.getString( "TRADE_NAME" ) );
        //SIZE_UNIT
        DataH.put( "SIZE_UNIT",searchRs.getString( "SIZE_UNIT" ) == null ? "" : searchRs.getString( "SIZE_UNIT" ) );
        //MFG_ID
        DataH.put( "MFG_ID",searchRs.getString( "MFG_ID" ) == null ? "" : searchRs.getString( "MFG_ID" ) );
        //MFG_PART_NO
        DataH.put( "MFG_PART_NO",searchRs.getString( "MFG_PART_NO" ) == null ? "" : searchRs.getString( "MFG_PART_NO" ) );
        //ITEM_TYPE
        DataH.put( "ITEM_TYPE",searchRs.getString( "ITEM_TYPE" ) == null ? "" : searchRs.getString( "ITEM_TYPE" ) );
        //ORDER_QUANTITY
        DataH.put( "ORDER_QUANTITY",searchRs.getString( "ORDER_QUANTITY" ) == null ? "" : searchRs.getString( "ORDER_QUANTITY" ) );
        //UOM
        DataH.put( "UOM",searchRs.getString( "UOM" ) == null ? "" : searchRs.getString( "UOM" ) );
        //PRIORITY
        DataH.put( "PRIORITY",searchRs.getString( "PRIORITY" ) == null ? "" : searchRs.getString( "PRIORITY" ) );
        //RADIAN_PO
        DataH.put( "RADIAN_PO",searchRs.getString( "RADIAN_PO" ) == null ? "" : searchRs.getString( "RADIAN_PO" ) );
        //FACILITY
        DataH.put( "FACILITY",searchRs.getString( "FACILITY" ) == null ? "" : searchRs.getString( "FACILITY" ) );
        //RAYTHEON_PO
        DataH.put( "RAYTHEON_PO",searchRs.getString( "RAYTHEON_PO" ) == null ? "" : searchRs.getString( "RAYTHEON_PO" ) );
        //BRANCH_PLANT
        DataH.put( "BRANCH_PLANT",searchRs.getString( "BRANCH_PLANT" ) == null ? "" : searchRs.getString( "BRANCH_PLANT" ) );
        //DATE_ISSUED
        DataH.put( "DATE_ISSUED",searchRs.getString( "DATE_ISSUED1" ) == null ? "" : searchRs.getString( "DATE_ISSUED1" ) );
        //DATE_PO_CREATED
        DataH.put( "DATE_PO_CREATED",searchRs.getString( "DATE_PO_CREATED" ) == null ? "" : searchRs.getString( "DATE_PO_CREATED" ) );
        //STATUS
        DataH.put( "STATUS",searchRs.getString( "STATUS" ) == null ? "" : searchRs.getString( "STATUS" ) );
        //DATE_CHANGED
        DataH.put( "DATE_CHANGED",searchRs.getString( "DATE_CHANGED" ) == null ? "" : searchRs.getString( "DATE_CHANGED" ) );
        //COMMENTS
        DataH.put( "COMMENTS",searchRs.getString( "COMMENTS" ) == null ? "" : searchRs.getString( "COMMENTS" ) );
        //COMPANY_ID
        DataH.put( "COMPANY_ID",searchRs.getString( "COMPANY_ID" ) == null ? "" : searchRs.getString( "COMPANY_ID" ) );
        //MR_NUMBER
        DataH.put( "MR_NUMBER",searchRs.getString( "MR_NUMBER" ) == null ? "" : searchRs.getString( "MR_NUMBER" ) );
        //MR_LINE_ITEM
        DataH.put( "MR_LINE_ITEM",searchRs.getString( "MR_LINE_ITEM" ) == null ? "" : searchRs.getString( "MR_LINE_ITEM" ) );
        //REORDER_POINT
        DataH.put( "REORDER_POINT",searchRs.getString( "REORDER_POINT" ) == null ? "" : searchRs.getString( "REORDER_POINT" ) );
        //STOCKING_LEVEL
        DataH.put( "STOCKING_LEVEL",searchRs.getString( "STOCKING_LEVEL" ) == null ? "" : searchRs.getString( "STOCKING_LEVEL" ) );
        //AVAILABLE_QUANTITY
        DataH.put( "AVAILABLE_QUANTITY",searchRs.getString( "AVAILABLE_QUANTITY" ) == null ? "" : searchRs.getString( "AVAILABLE_QUANTITY" ) );
        //OPEN_PO_QUANTITY
        DataH.put( "OPEN_PO_QUANTITY",searchRs.getString( "OPEN_PO_QUANTITY" ) == null ? "" : searchRs.getString( "OPEN_PO_QUANTITY" ) );
        //SHIP_TO_LOCATION_ID
        DataH.put( "SHIP_TO_LOCATION_ID",searchRs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "" : searchRs.getString( "SHIP_TO_LOCATION_ID" ) );
        //BUYER_ID
        DataH.put( "BUYER_ID",searchRs.getString( "BUYER_ID" ) == null ? "" : searchRs.getString( "BUYER_ID" ) );
        //BUYER_NAME
        DataH.put( "BUYER_NAME",searchRs.getString( "BUYER_NAME" ) == null ? "" : searchRs.getString( "BUYER_NAME" ) );
        //EVER_CONFIRMED
        DataH.put( "EVER_CONFIRMED",searchRs.getString( "EVER_CONFIRMED" ) == null ? "" : searchRs.getString( "EVER_CONFIRMED" ) );
        //DELIVERY_TYPE
        DataH.put( "DELIVERY_TYPE",searchRs.getString( "DELIVERY_TYPE" ) == null ? "" : searchRs.getString( "DELIVERY_TYPE" ) );
        //MR_QUANTITY
        DataH.put( "MR_QUANTITY",searchRs.getString( "MR_QUANTITY" ) == null ? "" : searchRs.getString( "MR_QUANTITY" ) );
        //LAST_SUPPLIER
        DataH.put( "LAST_SUPPLIER",searchRs.getString( "LAST_SUPPLIER" ) == null ? "" : searchRs.getString( "LAST_SUPPLIER" ) );
        //FROZEN
        DataH.put( "FROZEN",searchRs.getString( "FROZEN" ) == null ? "" : searchRs.getString( "FROZEN" ) );
        //ENGINEERING_EVALUATION
        DataH.put( "ENGINEERING_EVALUATION",searchRs.getString( "ENGINEERING_EVALUATION" ) == null ? "" : searchRs.getString( "ENGINEERING_EVALUATION" ) );
        //REQUEST_ID
        DataH.put( "REQUEST_ID",searchRs.getString( "REQUEST_ID" ) == null ? "" : searchRs.getString( "REQUEST_ID" ) );
        //CATALOG_ID
        DataH.put( "CATALOG_ID",searchRs.getString( "CATALOG_ID" ) == null ? "" : searchRs.getString( "CATALOG_ID" ) );
        //STOCKED
        DataH.put( "STOCKED",searchRs.getString( "STOCKED" ) == null ? "" : searchRs.getString( "STOCKED" ) );
        //STOCKED
        DataH.put( "INVENTORY_GROUP",searchRs.getString( "INVENTORY_GROUP" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP" ) );
		//BPO
        DataH.put( "BPO",searchRs.getString( "BPO" ) == null ? "" : searchRs.getString( "BPO" ) );
		//SUPPLY_PATH
        DataH.put( "SUPPLY_PATH",searchRs.getString( "SUPPLY_PATH" ) == null ? "" : searchRs.getString( "SUPPLY_PATH" ) );
		//SHIP_TO_DELIVERY_POINT
        DataH.put( "SHIP_TO_DELIVERY_POINT",searchRs.getString( "SHIP_TO_DELIVERY_POINT" ) == null ? "" : searchRs.getString( "SHIP_TO_DELIVERY_POINT" ) );
		//HOME_CURRENCY_ID
		DataH.put( "HOME_CURRENCY_ID",searchRs.getString( "HOME_CURRENCY_ID" ) == null ? "" : searchRs.getString( "HOME_CURRENCY_ID" ) );
		//CONSOLIDATION_ALLOWED
		DataH.put( "CONSOLIDATION_ALLOWED",searchRs.getString( "CONSOLIDATION_ALLOWED" ) == null ? "" : searchRs.getString( "CONSOLIDATION_ALLOWED" ) );
    //PURCHASING_UNITS_PER_ITEM
		//DataH.put( "PURCHASING_UNITS_PER_ITEM",searchRs.getString( "PURCHASING_UNITS_PER_ITEM" ) == null ? "" : searchRs.getString( "PURCHASING_UNITS_PER_ITEM" ) );
    //PURCHASING_UNIT_OF_MEASURE
		//DataH.put( "PURCHASING_UNIT_OF_MEASURE",searchRs.getString( "PURCHASING_UNIT_OF_MEASURE" ) == null ? "" : searchRs.getString( "PURCHASING_UNIT_OF_MEASURE" ) );
		//BUYER_COMPANY_ID
		DataH.put( "BUYER_COMPANY_ID",searchRs.getString( "BUYER_COMPANY_ID" ) == null ? "" : searchRs.getString( "BUYER_COMPANY_ID" ) );
		//FACILITY_ID
		DataH.put( "FACILITY_ID",searchRs.getString( "FACILITY_ID" ) == null ? "" : searchRs.getString( "FACILITY_ID" ) );
    //SHIP_TO_COMPANY_ID
		DataH.put( "SHIP_TO_COMPANY_ID",searchRs.getString( "SHIP_TO_COMPANY_ID" ) == null ? "" : searchRs.getString( "SHIP_TO_COMPANY_ID" ) );
        //PURCHASING_NOTE
        DataH.put( "PURCHASING_NOTE",searchRs.getString( "PURCHASING_NOTE" ) == null ? "" : searchRs.getString( "PURCHASING_NOTE" ) );
        //SPEC_LIST
        DataH.put( "SPEC_LIST",searchRs.getString( "SPEC_LIST" ) == null ? "" : searchRs.getString( "SPEC_LIST" ) );
        //SPEC_LIST
        DataH.put( "CSR_NAME",searchRs.getString( "CSR_NAME" ) == null ? "" : searchRs.getString( "CSR_NAME" ) );
        DataH.put( "REMAINING_SHELF_LIFE_PERCENT", searchRs.getString( "REMAINING_SHELF_LIFE_PERCENT" ) == null ? "" : searchRs.getString( "REMAINING_SHELF_LIFE_PERCENT" ).trim());

        if ( flagforassociations )
        {
          if ( radianPOfromRs.trim().length() > 0 )
          {
            DataH.put( "PRASSOCIATED","Yes" );
            DataH.put( "ADDPRLINE","" );
            DataH.put( "DELETEPRLINE","" );
          }
          else
          {
            DataH.put( "PRASSOCIATED","" );
            DataH.put( "ADDPRLINE","" );
            DataH.put( "DELETEPRLINE","" );
          }
        }
        else
        {
          DataH.put( "CRITICAL",searchRs.getString( "CRITICAL" ) == null ? " " : searchRs.getString( "CRITICAL" ) );
          DataH.put( "REQUEST_LINE_STATUS",searchRs.getString( "REQUEST_LINE_STATUS" ) == null ? " " : searchRs.getString( "REQUEST_LINE_STATUS" ) );
          DataH.put( "CANCEL_STATUS",searchRs.getString( "CANCEL_STATUS" ) == null ? " " : searchRs.getString( "CANCEL_STATUS" ) );
        }

        DataH.put( "DOSTATUSUPDATE","No" );
        DataH.put( "DOBUYERUPDATE","No" );
        DataH.put( "LINE_STATUS","No" );
        DataH.put( "USERCHK","" );
        DataH.put( "DOUPDATE","" );
        Data.addElement( DataH );
      }
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      throw e;
    }
    finally
    {
      if ( dbrs != null ) {dbrs.close();}
    }

    Hashtable recsum=new Hashtable();
    recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
    Data.setElementAt( recsum,0 );

    return Data;
    }

    private Vector SynchServerData( HttpServletRequest request,Vector in_data,Vector lockstatus )
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

		  String prnumber1=hD.get( "PR_NUMBER" ).toString().trim();
		  String origstatus=hD.get( "STATUS" ).toString().trim();
		  String prnumber="";
		  prnumber=request.getParameter( ( "prnumber" + i + "" ) );
		  if ( prnumber == null )
			prnumber="";

		if ( prnumber1.equalsIgnoreCase( prnumber ) && !( lockstatus.contains( origstatus )) )
		{
		  String prassociated=hD.get( "PRASSOCIATED" ).toString();
		  String check="";
		  check=request.getParameter( ( "" + i + "" ) );
		  if ( check == null )
			check="";

		  if ( prassociated.equalsIgnoreCase( check ) )
		  {
			if ( "yes".equalsIgnoreCase( prassociated ) )
			{
			  noofPrsAccosiated++;
			}
		  }
		  else if ( "yes".equalsIgnoreCase( prassociated ) && !"yes".equalsIgnoreCase( check ) )
		  {
			hD.remove( "DELETEPRLINE" );
			hD.put( "DELETEPRLINE","Yes" );
		  }
		  else if ( !"yes".equalsIgnoreCase( prassociated ) && "yes".equalsIgnoreCase( check ) )
		  {
			hD.remove( "ADDPRLINE" );
			hD.put( "ADDPRLINE","Yes" );
			noofPrsAccosiated++;
		  }

		  String status_id="";
		  status_id=request.getParameter( ( "statusofpr" + i ) );
		  if ( status_id == null )
			status_id="";
		  status_id=status_id.trim();
		  if ( ! ( status_id.equalsIgnoreCase( hD.get( "STATUS" ).toString().trim() ) || ( "Confirmed".equalsIgnoreCase( status_id ) ) ) )
		  {
			hD.remove( "STATUS" );
			hD.put( "STATUS",status_id );

			hD.remove( "DOSTATUSUPDATE" );
			hD.put( "DOSTATUSUPDATE","Yes" );
		  }

		  String buyer_id="";
		  buyer_id=request.getParameter( ( "buyerid" + i ) );
		  if ( buyer_id == null )
			buyer_id="";
		  buyer_id=buyer_id.trim();

		  String radianpo=hD.get( "RADIAN_PO" ).toString();
		  if ( ! ( radianpo.length() > 0 && buyer_id.length() == 0 ) )
		  {
			if ( !buyer_id.equalsIgnoreCase( hD.get( "BUYER_ID" ).toString().trim() ) )
			{
			  hD.remove( "BUYER_ID" );
			  hD.put( "BUYER_ID",buyer_id );

			  hD.remove( "DOBUYERUPDATE" );
			  hD.put( "DOBUYERUPDATE","Yes" );
			}
		  }

		  String buyocomments="";
		  buyocomments=request.getParameter( ( "buyocomments" + i ) );
		  if ( buyocomments == null )
			buyocomments="";
		  buyocomments=buyocomments.trim();
		  if ( !buyocomments.equalsIgnoreCase( hD.get( "COMMENTS" ).toString().trim() ) )
		  {
			hD.remove( "COMMENTS" );
			hD.put( "COMMENTS",buyocomments );

			hD.remove( "DOSTATUSUPDATE" );
			hD.put( "DOSTATUSUPDATE","Yes" );
		  }
		  }

          new_data.addElement( hD );
        }
      }
      catch ( Exception e )
      {
        //e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "New Buy Order",intcmIsApplication ) );
      }
      return new_data;
    }

    private Vector SynchEditPrsData( HttpServletRequest request,Vector in_data )
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
          String prassociated=hD.get( "PRASSOCIATED" ).toString();
          String check="";
          check=request.getParameter( ( "" + i + "" ) );
          if ( check == null )
            check="";

          if ( prassociated.equalsIgnoreCase( check ) )
          {
            if ( "yes".equalsIgnoreCase( prassociated ) )
            {
              noofPrsAccosiated++;
            }
          }
          else if ( "yes".equalsIgnoreCase( prassociated ) && !"yes".equalsIgnoreCase( check ) )
          {
            hD.remove( "DELETEPRLINE" );
            hD.put( "DELETEPRLINE","Yes" );
          }
          else if ( !"yes".equalsIgnoreCase( prassociated ) && "yes".equalsIgnoreCase( check ) )
          {
            hD.remove( "ADDPRLINE" );
            hD.put( "ADDPRLINE","Yes" );
            noofPrsAccosiated++;
          }

          new_data.addElement( hD );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( radian.web.HTMLHelpObj.printError( "New Buy Order",intcmIsApplication ) );
      }
      return new_data;
    }

    private Hashtable UpdateDetails( Vector data,String companyId )
    {
      boolean updresult=true;
      Hashtable updateresult=new Hashtable();
      Vector errordata=new Vector();
      String upderrormsg = "";

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        errordata.addElement( summary );

        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String buyerId="";
          buyerId= ( hD.get( "BUYER_ID" ) == null ? "&nbsp;" : hD.get( "BUYER_ID" ).toString() );

          String status="";
          status= ( hD.get( "STATUS" ) == null ? "&nbsp;" : hD.get( "STATUS" ).toString() );

          String prnumber="";
          prnumber= ( hD.get( "PR_NUMBER" ) == null ? "&nbsp;" : hD.get( "PR_NUMBER" ).toString() );

          String Line_Check="";
          Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );

          String dostatusupdate="";
          dostatusupdate= ( hD.get( "DOSTATUSUPDATE" ) == null ? " " : hD.get( "DOSTATUSUPDATE" ).toString() );

          String dobuyerupdate="";
          dobuyerupdate= ( hD.get( "DOBUYERUPDATE" ) == null ? " " : hD.get( "DOBUYERUPDATE" ).toString() );

          String buyordercomments="";
          buyordercomments= ( hD.get( "COMMENTS" ) == null ? " " : hD.get( "COMMENTS" ).toString() );
          buyordercomments=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( buyordercomments );
          /*if ( buyordercomments.length() > 160 )
          {
            buyordercomments=buyordercomments.substring( 0,159 );
          }*/

		  String radianpo=hD.get( "RADIAN_PO" ).toString();
		  String frozenpr=hD.get( "FROZEN" ).toString();

		  if ( ( ( "yes".equalsIgnoreCase( dostatusupdate ) ) || ( "yes".equalsIgnoreCase( dobuyerupdate ) ) ) )
          {
            noneToUpd=false;
            try
            {
			  if ( "Y".equalsIgnoreCase( frozenpr ) )
			  {
				upderrormsg += "Buy Order : "+prnumber+" is frozen.";
				updresult=false;
			  }
			  else
			  {
				if ( buyerId.trim().length() < 1 )
				{
				  buyerId="null";
				}

				String updateQuery="update buy_order set ";
				if ( ! ( radianpo.length() > 0 && "null".equalsIgnoreCase( buyerId ) ) )
				{
				  updateQuery+="BUYER_ID=" + buyerId + ",";
				}

				if ( !"Confirmed".equalsIgnoreCase( status ) )
				{
				  updateQuery+="BUYER_COMPANY_ID = '"+companyId+"', STATUS='" + status + "',DATE_CHANGED=sysdate,COMMENTS='" + buyordercomments + "' where PR_NUMBER =" + prnumber + " and frozen = 'N' ";
				  db.doUpdate( updateQuery );
				  buylog.info("updateQuery: "+updateQuery+"");
				  //System.out.println("Did Buy Order Update");
				}
				else
				{
				  updateQuery+="BUYER_COMPANY_ID = '"+companyId+"', DATE_CHANGED=sysdate,COMMENTS='" + buyordercomments + "' where PR_NUMBER =" + prnumber + " and frozen = 'N' ";
				  db.doUpdate( updateQuery );
				  buylog.info("updateQuery: "+updateQuery+"");
				  //System.out.println("Did Buy Order Update Confirmed Status");
				}
				updresult=true;
			  }
            }
            catch ( Exception e )
            {
              //e.printStackTrace();
              upderrormsg += e.getMessage();
              updresult=false;
            }

            hD.remove( "DOSTATUSUPDATE" );
            hD.put( "DOSTATUSUPDATE","No" );

            hD.remove( "DOBUYERUPDATE" );
            hD.put( "DOBUYERUPDATE","No" );

            errordata.addElement( hD );
          }
          else
          {
            errordata.addElement( hD );
          }
        } //end of for
      }
      catch ( Exception e )
      {
        updresult=false;
        //e.printStackTrace();
      }

      updateresult.put( "RESULT",new Boolean( updresult ) );
      updateresult.put( "ERRORDATA",errordata );
      updateresult.put( "ERRORMSG",upderrormsg );

      return updateresult;
    }

    private Hashtable createNewPO( Vector data,String personnelid,String companyId )
    {
      boolean result=false;
      createdpo="";
      Hashtable updateresult=new Hashtable();
      Vector errordata=new Vector();
      String radianpo="";
      int totalitemQty=0;
      int totalnumberoflines=0;
      Timestamp testNeedDate=null;
      String finalNeedDate="";
      String finalItem="";
      String finalclientPo="";
      String finalHub="";
      String finalshipTo="";
      String finalshipToCompanyId="";
			String finalshipToFacilityId="";
      String updateQuery="";
      String updateQueryprs="";
      String finalCritical="";
      String invengrp="";
      String clientpo="";
	    String currencyid = "";
		  //String purchasingUnitsPerItem="";
      DBResultSet dbrs=null;
      ResultSet rs=null;

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        errordata.addElement( summary );

        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String addprlinestatus="";
          addprlinestatus= ( hD.get( "ADDPRLINE" ) == null ? " " : hD.get( "ADDPRLINE" ).toString() );

          if ( "yes".equalsIgnoreCase( addprlinestatus ) )
          {
            finalHub=hD.get( "BRANCH_PLANT" ).toString();
            String prnumber1=hD.get( "PR_NUMBER" ).toString().trim();
            String needdate=hD.get( "NEED_DATE" ).toString();
            String qty=hD.get( "ORDER_QUANTITY" ).toString();
            finalclientPo=hD.get( "RAYTHEON_PO" ).toString();
            finalshipTo=hD.get( "SHIP_TO_LOCATION_ID" ).toString();
						finalshipToFacilityId =hD.get( "FACILITY_ID" ).toString();
            finalItem=hD.get( "ITEM_ID" ).toString();
            finalshipToCompanyId=hD.get( "SHIP_TO_COMPANY_ID" ).toString();
            String critical=hD.get( "CRITICAL" ).toString();
            invengrp=hD.get( "INVENTORY_GROUP" ).toString();
            clientpo=hD.get( "RAYTHEON_PO" ).toString();
						currencyid=hD.get( "HOME_CURRENCY_ID" ).toString();
						//purchasingUnitsPerItem = hD.get( "PURCHASING_UNITS_PER_ITEM" ).toString();

            if ( "Y".equalsIgnoreCase( critical ) )
            {
              finalCritical="Y";
            }

						if ( "S".equalsIgnoreCase( critical ) )
						{
						 finalCritical="S";
						}

            if ( totalnumberoflines > 0 )
            {
              updateQueryprs+=",";
            }
            updateQueryprs+=prnumber1;

            totalnumberoflines++;
            if ( qty.length() > 0 )
            {
						  /*if (purchasingUnitsPerItem.trim().length() > 0)
							{
							 totalitemQty += Integer.parseInt(qty)*Integer.parseInt(purchasingUnitsPerItem);
			        }
							else*/
							{
							 totalitemQty += Integer.parseInt(qty);
							}
            }

            if ( totalnumberoflines == 1 )
            {
              finalNeedDate=needdate;
              testNeedDate=radian.web.HTMLHelpObj.getDateFromString( needdate );
            }

            Timestamp neeDate1=radian.web.HTMLHelpObj.getDateFromString( needdate );
            if ( testNeedDate.after( neeDate1 ) )
            {
              testNeedDate=neeDate1;
              finalNeedDate=needdate;
            }
          }
        }

        noneToUpd=false;
        boolean line_result=false;
        boolean radianpoexists=false;

        int testradianpoexists=1;
        if ( updateQueryprs.trim().length() > 0 )
        {
          testradianpoexists=DbHelpers.countQuery( db,"select count(*) from buy_order where radian_po is not null and pr_number in (" + updateQueryprs + ")" );
        }
        else
        {
          createdpoerrmesage+="<BR><B>No PR Was Choosen To Create a PO</B><BR>";
        }

        if ( testradianpoexists == 0 )
        {
						try
						{
						 String updateBuyerCompanyID="update buy_order set BUYER_COMPANY_ID = '"+companyId+"',DATE_CHANGED=sysdate where pr_number in (" + updateQueryprs + ") ";
							db.doUpdate( updateBuyerCompanyID );
						}
						catch ( Exception e )
						{
							//e.printStackTrace();
						}

		  CallableStatement cs=null;
		  String pofromprocedure = "";
		  try
		  {
			Connection connect1=null;
			connect1=db.getConnection();
			//buylog.info( "Calling create_po for PRs  " + updateQueryprs + " Personnel ID "+personnelid+"" );
		  cs=connect1.prepareCall( "{call create_po(?,?,?,?)}" );
		  cs.setString( 1,updateQueryprs );
		  cs.setInt( 2,Integer.parseInt( personnelid ) );
		  cs.registerOutParameter( 3,java.sql.Types.VARCHAR );
		  cs.registerOutParameter( 4,java.sql.Types.VARCHAR );

		  cs.execute();

		  pofromprocedure=cs.getString( 3 );
		  if (pofromprocedure == null) {pofromprocedure = "";}
		  String errorcode=cs.getString( 4 );
       if ( errorcode == null )
      errorcode="";
      if (pofromprocedure.length() == 0 || errorcode.length() > 0)
			{
		  buylog.info( "Result from create_po procedure Buy Orders: "+updateQueryprs+" Error Code:  " + errorcode + "  pofromprocedure " + pofromprocedure + " " );
			}
		}
		catch ( Exception e )
		{
		  //e.printStackTrace();
		  radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling create_po in Buy Page","Error occured while running create_po \nError Msg:\n" + e.getMessage() + "\nFor Input Parameters \n updateQueryprs " + updateQueryprs + "  " );
		}
		finally
		{
		  if ( cs != null ){try{cs.close();}catch ( SQLException se ){}}
		}
		radianpo=pofromprocedure;

		  if ( totalnumberoflines > 0 && pofromprocedure.length() == 0)
          {
            try
            {
              String bquery="select po_seq.nextval RADIANPO from dual";
              dbrs=db.doQuery( bquery );
              rs=dbrs.getResultSet();

              while ( rs.next() )
              {
                radianpo=rs.getString( "RADIANPO" );
              }

              String updpoquery="insert into po (RADIAN_PO,BUYER,PAYMENT_TERMS,INVENTORY_GROUP) values (" + radianpo + "," + personnelid + ",'Net 45','"+invengrp+"')";
              db.doUpdate( updpoquery );
            }
            catch ( Exception e )
            {
              buylog.info( "error inserting into PO (RADIAN_PO,BUYER,PAYMENT_TERMS,INVENTORY_GROUP) values (" + radianpo + "," + personnelid + ",'Net 45','"+invengrp+"')");
              //e.printStackTrace();
            }
            finally
            {
             if ( dbrs != null ) {dbrs.close();}
            }

            try
            {
             updateQuery="update buy_order set BUYER_COMPANY_ID = '"+companyId+"',RADIAN_PO = " + radianpo + ",BUYER_ID=" + personnelid + ",DATE_CHANGED=sysdate where item_id =" + finalItem +  " and pr_number in (" + updateQueryprs + ") and radian_po is null and frozen = 'N'";
              db.doUpdate( updateQuery );
            }
            catch ( Exception e )
            {
              //e.printStackTrace();
            }

            CallableStatement pstmt=null;
			Connection connecPo = null;
            try
            {
                try
                {
                  String shipToLocQuery ="Select * from ship_to_location_view where FACILITY_ID is not null and lower(LOCATION_ID) = lower('" + finalshipTo + "')";
                  if (finalshipToCompanyId.length() >0)
                  {
                    shipToLocQuery +=" and COMPANY_ID = '"+finalshipToCompanyId+"'";
                  }
                  dbrs=db.doQuery(shipToLocQuery);
                  rs=dbrs.getResultSet();

                  while ( rs.next() )
                  {
                    finalshipToCompanyId=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
										finalshipToFacilityId=rs.getString( "FACILITY_ID" ) == null ? "" : rs.getString( "FACILITY_ID" ).trim();
                  }
                }
                catch ( Exception ecomp )
                {
                  //ecomp.printStackTrace();
                }
                finally
                {
                 if ( dbrs != null ) {dbrs.close();}
                }

              connecPo=db.getConnection();
              connecPo.setAutoCommit( false );
              pstmt=connecPo.prepareCall( "{call pkg_po.PO_HEADER (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
							//System.out.println("finalshipTo    "+finalshipTo+"    finalshipToCompanyId   "+finalshipToCompanyId+" finalshipToFacilityId "+finalshipToFacilityId+"");
              pstmt.setBigDecimal( 1,new BigDecimal(""+ radianpo +"") );
              pstmt.setNull( 2,java.sql.Types.INTEGER );
              pstmt.setNull( 3,java.sql.Types.VARCHAR );
              pstmt.setNull( 4,java.sql.Types.INTEGER );
              pstmt.setInt( 5,Integer.parseInt( personnelid ) );
              pstmt.setString( 6,finalHub );
              pstmt.setString( 7,finalshipToCompanyId );
              pstmt.setString( 8,finalshipTo );
              pstmt.setNull( 9,java.sql.Types.VARCHAR );
              pstmt.setString( 10,"Net 45" );
              pstmt.setNull( 11,java.sql.Types.VARCHAR );
              pstmt.setNull( 12,java.sql.Types.DATE );
              pstmt.setNull( 13,java.sql.Types.DATE );
              pstmt.setNull( 14,java.sql.Types.VARCHAR );
              pstmt.setInt( 15,Integer.parseInt( personnelid ) );
			  if ( clientpo.trim().length() > 0 && !("2104".equalsIgnoreCase(finalHub) || "2117".equalsIgnoreCase(finalHub)) )
              {
                pstmt.setString( 16,clientpo );
              }
              else
              {
                pstmt.setNull( 16,java.sql.Types.VARCHAR );
              }

              if ( finalCritical.length() > 0 )
              {
                pstmt.setString( 17,finalCritical );
              }
              else
              {
                pstmt.setNull( 17,java.sql.Types.VARCHAR );
              }
              if ( invengrp.length() > 0 )
              {
                pstmt.setString( 18,invengrp );
              }
              else
              {
                pstmt.setNull( 18,java.sql.Types.VARCHAR );
              }
			  pstmt.setString( 19,"n" );
			  //pstmt.setNull( 19,java.sql.Types.VARCHAR );

              pstmt.registerOutParameter( 20,java.sql.Types.VARCHAR );
							pstmt.setNull( 21,java.sql.Types.VARCHAR );
							if ( finalshipToFacilityId.length() > 0 )
							{
							 pstmt.setString( 22,finalshipToFacilityId );
							}
							else
							{
							 pstmt.setNull( 22,java.sql.Types.VARCHAR );
							}

              pstmt.execute();
              String errorcode=pstmt.getString( 20 );

              if ( errorcode == null )
              {
                line_result=true;
              }
              else
              {
                line_result=false;
              }

              pstmt=connecPo.prepareCall( "{call pkg_po.SAVE_PO_LINE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
              pstmt.setBigDecimal( 1,new BigDecimal(""+ radianpo +"") );
              pstmt.setInt( 2,1000 );
              pstmt.setInt( 3,0 );
              if ( finalItem.length() > 0 )
              {
                pstmt.setInt( 4,Integer.parseInt( finalItem ) );
              }
              else
              {
                pstmt.setNull( 4,java.sql.Types.INTEGER );
              }
              pstmt.setNull( 5,java.sql.Types.VARCHAR );
              if ( totalitemQty > 0 )
              {
                pstmt.setInt( 6,totalitemQty );
              }
              else
              {
                pstmt.setNull( 6,java.sql.Types.INTEGER );
              }
              pstmt.setNull( 7,java.sql.Types.INTEGER );
              if ( finalNeedDate.length() == 10 )
              {
                pstmt.setTimestamp( 8,radian.web.HTMLHelpObj.getDateFromString( finalNeedDate ) );
              }
              else
              {
                pstmt.setNull( 8,java.sql.Types.DATE );
              }
              pstmt.setNull( 9,java.sql.Types.DATE );
              pstmt.setNull( 10,java.sql.Types.INTEGER );
              pstmt.setNull( 11,java.sql.Types.VARCHAR );
              pstmt.setNull( 12,java.sql.Types.VARCHAR );
              pstmt.setNull( 13,java.sql.Types.VARCHAR );
              pstmt.setNull( 14,java.sql.Types.VARCHAR );
              pstmt.setNull( 15,java.sql.Types.VARCHAR );
              pstmt.setNull( 16,java.sql.Types.VARCHAR );
              pstmt.setNull( 17,java.sql.Types.VARCHAR );
              pstmt.setInt( 18,Integer.parseInt( personnelid ) );
              pstmt.setNull( 19,java.sql.Types.INTEGER );
              pstmt.setNull( 20,java.sql.Types.VARCHAR );
              pstmt.setNull( 21,java.sql.Types.FLOAT );
              pstmt.setString( 22,"N" );
              pstmt.setString( 23,"N" );
              pstmt.setNull( 24,java.sql.Types.VARCHAR );
              pstmt.setNull( 25,java.sql.Types.INTEGER );
              pstmt.setNull( 26,java.sql.Types.VARCHAR );
              pstmt.setNull( 27,java.sql.Types.DATE );
			  pstmt.setString( 28,currencyid );
			  pstmt.setNull( 29,java.sql.Types.VARCHAR );
              pstmt.registerOutParameter( 30,java.sql.Types.VARCHAR );

              //www -finalCritical
              pstmt.execute();
              connecPo.commit();
              errorcode=pstmt.getString( 30 );

              if ( errorcode == null )
              {
                line_result=true;
              }
              else
              {
                line_result=false;
              }

              saveinitialSpecs( finalItem,radianpo,finalshipTo,finalshipToCompanyId,invengrp );
            }
            catch ( Exception e )
            {
              result=false;
              //e.printStackTrace();
            }
            finally
            {
              connecPo.setAutoCommit( true );
			  if ( pstmt != null )
              {
                try
                {
                  pstmt.close();
                }
                catch ( Exception se )
                {
                  //se.printStackTrace();
                }
              }
            }
          }
        }
        /*else
        {
          radianpoexists=true;
        }*/

        Hashtable createdprs=new Hashtable();
        try
        {
          String bquery="select RADIAN_PO,PR_NUMBER,BUYER_ID,STATUS from buy_order where PR_NUMBER in (" + updateQueryprs + ")";
          if ( updateQueryprs.trim().length() > 0 )
          {
            dbrs=db.doQuery( bquery );
            rs=dbrs.getResultSet();

            while ( rs.next() )
            {
              Hashtable createdprsins=new Hashtable();
              String radianPOfromRs=rs.getString( "RADIAN_PO" ) == null ? " " : rs.getString( "RADIAN_PO" );
              String prnumbers=rs.getString( "PR_NUMBER" ) == null ? " " : rs.getString( "PR_NUMBER" );
              String buyerids=rs.getString( "BUYER_ID" ) == null ? " " : rs.getString( "BUYER_ID" );
              String statuss=rs.getString( "STATUS" ) == null ? " " : rs.getString( "STATUS" );

              createdprsins.put( "RADIAN_PO",radianPOfromRs );
              createdprsins.put( "BUYER_ID",buyerids );
              createdprsins.put( "STATUS",statuss );
              createdprs.put( prnumbers,createdprsins );
            }
          }
        }
        catch ( Exception e )
        {
          //e.printStackTrace();
        }
        finally
        {
          if ( dbrs != null ) {dbrs.close();}
        }

        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String addline="";
          addline= ( hD.get( "ADDPRLINE" ) == null ? " " : hD.get( "ADDPRLINE" ).toString() );
          String prnumber1=hD.get( "PR_NUMBER" ).toString().trim();

          try
          {
            if ( "yes".equalsIgnoreCase( addline ) )
            {
              Hashtable createdpodata=new Hashtable();
              createdpodata= ( Hashtable ) createdprs.get( prnumber1 );

              hD.remove( "RADIAN_PO" );
              hD.put( "RADIAN_PO",createdpodata.get( "RADIAN_PO" ).toString() );

              hD.remove( "PRASSOCIATED" );
              hD.put( "PRASSOCIATED","Yes" );

              hD.remove( "STATUS" );
              hD.put( "STATUS",createdpodata.get( "STATUS" ).toString() );

              hD.remove( "BUYER_ID" );
              hD.put( "BUYER_ID",createdpodata.get( "BUYER_ID" ).toString() );

              hD.remove( "ADDPRLINE" );
              hD.put( "ADDPRLINE","" );

              hD.remove( "DELETEPRLINE" );
              hD.put( "DELETEPRLINE","" );

              errordata.addElement( hD );
            }
            else
            {
              errordata.addElement( hD );
            }
          }
          catch ( Exception e )
          {
            e.printStackTrace();
          }
        }

        if ( radianpoexists )
        {
          result=false;
          createdpo="";

          if ( updateQueryprs.trim().length() > 0 )
          {
            createdpoerrmesage+="<BR><B>This PR is Already Associated with the PO Shown Here.</B>";
          }
        }
        else
        {
          result=true;
          createdpo=radianpo;
        }
      }
      catch ( Exception e )
      {
        result=false;
        //e.printStackTrace();
      }

      updateresult.put( "RESULT",new Boolean( result ) );
      updateresult.put( "ERRORDATA",errordata );
      return updateresult;
    }

    private Hashtable UpdateEditPrs( Vector data,String personnelid,String origRadianPO, Vector lockstatus,String companyId )
    {
      boolean updresult=false;
      Hashtable updateresult=new Hashtable();
      Vector errordata=new Vector();
      String upderrormsg = "";

      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        errordata.addElement( summary );

        if (total ==0)
        {
            updresult=true;
        }

        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String prnumber1=hD.get( "PR_NUMBER" ).toString().trim();
          String radianpo=hD.get( "RADIAN_PO" ).toString();
          String itemid=hD.get( "ITEM_ID" ).toString();
          String deleteline="";
          deleteline= ( hD.get( "DELETEPRLINE" ) == null ? "&nbsp;" : hD.get( "DELETEPRLINE" ).toString() );
          String addline="";
          addline= ( hD.get( "ADDPRLINE" ) == null ? " " : hD.get( "ADDPRLINE" ).toString() );
          String updateQuery="";
		  String status="";
		  status= ( hD.get( "STATUS" ) == null ? "&nbsp;" : hD.get( "STATUS" ).toString() );
		  String branchplant=hD.get( "BRANCH_PLANT" ).toString();

          if ( ! (radianpo.trim().length() > 0 ))
          {
            radianpo=origRadianPO;
          }

          try
          {
            if ( lockstatus.contains( status ) )
			{

		    }
			else
			{
			  if ( "yes".equalsIgnoreCase( deleteline ) )
			  {
				updateQuery="update buy_order set SUPPLY_PATH = 'Purchaser', BUYER_COMPANY_ID = '"+companyId+"', RADIAN_PO=null,PO_DETACH_BY=" + personnelid +	",STATUS='In Progress',PO_IN_JDE='n',DATE_CHANGED=sysdate where item_id =" + itemid + " and radian_po = " + radianpo + " and pr_number=" + prnumber1 + " and frozen = 'N' ";
				unassociatedprs=true;

				db.doUpdate( updateQuery );
			  }
			  else if ( "yes".equalsIgnoreCase( addline ) )
			  {
				updateQuery="update buy_order set BUYER_COMPANY_ID = '"+companyId+"', RADIAN_PO = " + radianpo + ",BUYER_ID=" + personnelid + ",DATE_CHANGED=sysdate where item_id =" + itemid + " and pr_number=" + prnumber1 + " and frozen = 'N' ";
				associatedprs=true;

				db.doUpdate( updateQuery );
				String updatehubonpo = "update po set BRANCH_PLANT = "+branchplant+" where RADIAN_PO = " + radianpo + " and BRANCH_PLANT is null";
				db.doUpdate( updatehubonpo );
			  }
			}
            updresult=true;
          }
          catch ( Exception e )
          {
            //e.printStackTrace();
            upderrormsg += e.getMessage();
            updresult=false;
          }
        }
      }
      catch ( Exception e )
      {
        updresult=false;
        //e.printStackTrace();
      }

      updateresult.put( "RESULT",new Boolean( updresult ) );
      updateresult.put( "ERRORDATA",errordata );
      updateresult.put( "ERRORMSG",upderrormsg );

      return updateresult;
    }

    private Hashtable UpdateAddPrsToPo( Vector data,String personnelid,String origRadianPO, Vector lockstatus,String companyId,String currencyId )
    {
      boolean updresult=false;
      Hashtable updateresult=new Hashtable();
      Vector errordata=new Vector();
      String upderrormsg = "";
      String prNumberList = "";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      int totalLinesAdded= 0;
      try
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
        errordata.addElement( summary );
          
        if (total ==0)
        {
            updresult=true;
        }

        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( i );

          String prnumber1=hD.get( "PR_NUMBER" ).toString().trim();
          String radianpo=hD.get( "RADIAN_PO" ).toString();
          String itemid=hD.get( "ITEM_ID" ).toString();
          String deleteline="";
          deleteline= ( hD.get( "DELETEPRLINE" ) == null ? "&nbsp;" : hD.get( "DELETEPRLINE" ).toString() );
          String addline="";
          addline= ( hD.get( "ADDPRLINE" ) == null ? " " : hD.get( "ADDPRLINE" ).toString() );
          String updateQuery="";
		  String status="";
		  status= ( hD.get( "STATUS" ) == null ? "&nbsp;" : hD.get( "STATUS" ).toString() );
		  String branchplant=hD.get( "BRANCH_PLANT" ).toString();

          if ( ! (radianpo.trim().length() > 0 ))
          {
            radianpo=origRadianPO;
          }

          try
          {
            if ( lockstatus.contains( status ) )
			{

		    }
			else
			{
			  if ( "yes".equalsIgnoreCase( addline ) )
			  {
                totalLinesAdded++;
                if (totalLinesAdded >1)
                {
                    prNumberList += ",";
                }
                prNumberList += prnumber1;

                updateQuery="update buy_order set BUYER_COMPANY_ID = '"+companyId+"', RADIAN_PO = " + radianpo + ",BUYER_ID=" + personnelid + ",DATE_CHANGED=sysdate where item_id =" + itemid + " and pr_number=" + prnumber1 + " and frozen = 'N' ";
				associatedprs=true;

				db.doUpdate( updateQuery );
				String updatehubonpo = "update po set BRANCH_PLANT = "+branchplant+" where RADIAN_PO = " + radianpo + " and BRANCH_PLANT is null";
				db.doUpdate( updatehubonpo );
              }
			}
            updresult=true;
          }
          catch ( Exception e )
          {
            //e.printStackTrace();
            upderrormsg += e.getMessage();
            updresult=false;
          }
        }

        //call P_ADD_BUY_ORDERS_TO_PO
        if (prNumberList.length() > 0)
        {
          updresult = addLineToPo(prNumberList,origRadianPO,personnelid,currencyId);
        }
      }
      catch ( Exception e )
      {
        updresult=false;
        //e.printStackTrace();
      }

      updateresult.put( "RESULT",new Boolean( updresult ) );
      updateresult.put( "ERRORDATA",errordata );
      updateresult.put( "ERRORMSG",upderrormsg );

      return updateresult;
    }

public boolean addLineToPo(String prNumberList,String radianPo, String personelId,String currencyId )
{
     Connection connect1;
     CallableStatement cs = null;
     boolean result = false;
     try
          {
              connect1 = db.getConnection();
              cs = connect1.prepareCall("{call Pkg_buy_order.P_ADD_BUY_ORDERS_TO_PO(?,?,?,?,?)}");
              cs.setBigDecimal( 1,new BigDecimal(""+ radianPo +"") );
              cs.setString(2,prNumberList );
              cs.setInt(3,Integer.parseInt(personelId));
              cs.setString(4,currencyId );

              cs.registerOutParameter(5, Types.VARCHAR);

              buylog.info("calling Procedure Pkg_buy_order.P_ADD_BUY_ORDERS_TO_PO: "+radianPo+" prNumberList "+prNumberList+" ");
              cs.execute();

              String errorMsg = cs.getString(5);

           if( errorMsg != null && !"ok".equalsIgnoreCase(errorMsg))
           {
               buylog.info("Error in Procedure Pkg_buy_order.P_ADD_BUY_ORDERS_TO_PO: "+radianPo+" Error Code "+errorMsg+" ");
           }

              connect1.commit();
              cs.close();
              result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

   private void buildHeader( String Hubnum,Hashtable hub_list_set,String useraction,String searchthis,String sortby,
                               String facility,HttpSession session,String category,String searchcompany,String status,String bbuyer,
                               String showall,String searchLike,String showonlynopo,String showonlyconfpo,Vector statusStringfromArray,String openbuyorders,String selsuppath )
   {
     //StringBuffer Msg=new StringBuffer();
     //String SelectedHubName="";

     if ( !"Search".equalsIgnoreCase( useraction ) )
     {
       showonlynopo="Yes";
       status="New";
     }

     try
     {
       out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Requests","purchasereq.js",intcmIsApplication ) );
       out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );
       out.println( "<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n" );
       out.println( this.getdropDownJs() );
       out.println( "// -->\n </SCRIPT>\n" );

       if ( useraction.equalsIgnoreCase( "createpo" ) && ( createdpo.trim().length() > 0 ) )
       {
         String wwwHome=bundle.getString( "WEB_HOME_PAGE" );
         String radianpourl=wwwHome + "tcmIS/supply/purchorder?po=" + createdpo + "&Action=searchlike&subUserAction=po";

         out.println( "<BODY onLoad=\"javascript:window.open('" + radianpourl + "')\">\n" );
       }
       else
       {
         out.println( "<BODY onload=\"resetApplicationTimer()\">\n" );
       }

       out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
       out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n" );
       out.println( "</DIV>\n" );
       out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

       out.println( radian.web.HTMLHelpObj.printPurchaseOrderTitleBar( "<B>Purchase Requests</B>\n" ) );
       out.println( "<FORM method=\"POST\" NAME=\"purchasereq\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString( "BUY_ORDER" ) + "\">\n" );

       //start table #1
       out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
       out.println( "<TR>\n" );

       //Hub List
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<B>Hub:</B>&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"15%\">\n" );

       out.println( "<SELECT CLASS=\"HEADER\" ID=\"HubName\" NAME=\"HubName\" ONCHANGE=\"reshow(document.purchasereq.HubName)\" size=\"1\">\n" );

       out.println( "<OPTION VALUE=\"All\">Select A Hub</OPTION>\n" );

       Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );

	   out.println(radian.web.HTMLHelpObj.sorthashbyValue(hub_list.entrySet(),Hubnum));

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
           SelectedHubName=hub_name;
         }
         out.println( "<option  " + hub_selected + " value=\"" + branch_plant + "\">" + hub_name + "</option>\n" );
       }*/
       out.println( "</SELECT>\n" );
       out.println( "</TD>\n" );

       //Buyer
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<B>Buyer:</B>&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
       String buyerselected="";
       String allbuyerselected="";
       if ( "All".equalsIgnoreCase( bbuyer ) )
       {
         allbuyerselected="selected";
       }
       if ( "No Buyers".equalsIgnoreCase( bbuyer ) )
       {
         buyerselected="selected";
       }
       out.println( "<SELECT CLASS=\"HEADER\" ID=\"buyer\" NAME=\"buyer\" size=\"1\">\n" );
       out.println( "<OPTION " + allbuyerselected + " VALUE=\"All\">All Buyers</OPTION>\n" );
       out.println( "<OPTION " + buyerselected + " VALUE=\"No Buyers\">No Buyer Assigned</OPTION>\n" );
       out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "PO_BUYERNAMES" ),bbuyer ) );
       out.println( "</SELECT>\n" );
       out.println("</TD>\n");

       //Search
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">&nbsp;</TD>\n" );

       //Search
       out.println( "<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\">\n" );
       out.println( "   <INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" ID=\"SearchButton\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "</TR>\n" );
       out.println( "<TR>\n" );

       //Company
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<B>Company:</B>&nbsp;\n" );
       out.println( "</TD>\n" );

       out.println( "<TD WIDTH=\"15%\">\n" );
       out.println( "<SELECT CLASS=\"HEADER\" ID=\"companyID\" NAME=\"companyID\" size=\"1\">\n" );
       out.println( "<OPTION VALUE=\"All\">Any Company</OPTION>\n" );
       out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "PO_COMPANY_IDS" ),searchcompany ) );
       out.println( "</SELECT>\n" );
       out.println( "</TD>\n" );

       // Category
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<B>Category:</B>&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"10%\" COLSPAN=\"3\" ALIGN=\"LEFT\">\n" );
       out.println( "<SELECT CLASS=\"HEADER\" ID=\"category\" NAME=\"category\" size=\"1\">\n" );
       out.println( "<OPTION VALUE=\"All\">All Categories</OPTION>\n" );
       out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "PO_CATEGORY" ),category ) );
       out.println( "</SELECT>\n" );
       out.println( "</TD>\n" );

       //Update
       out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
	   if ( this.getupdateStatus() || this.getallowbuyernotes() )
       {
         out.println( "<INPUT TYPE=\"submit\" VALUE=\"Update\" onclick= \"return confirm(this)\" ID=\"SearchButton\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
       }
       else
       {
         out.println( "&nbsp;\n" );
       }
       out.println( "</TR>\n" );

       out.println( "<TR>\n" );
       //Facility List
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<B>Facility:</B>&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"15%\">\n" );
       out.println( "<SELECT CLASS=\"HEADER\" ID=\"FacName\" NAME=\"FacName\" size=\"1\">\n" );
       if ( "All".equalsIgnoreCase( Hubnum ) || Hubnum.length() == 0 )
       {
         out.println( "<OPTION VALUE=\"\">Choose a Hub to get Facilities</OPTION>\n" );
       }
       else
       {
         out.println( "<OPTION VALUE=\"\">All</OPTION>\n" );
         Vector warehouseInfo= ( Vector ) session.getAttribute( "PO_HUBCOMPFACS" );

         for ( int i=0; i < warehouseInfo.size(); i++ )
         {
           Hashtable hD2=new Hashtable();
           hD2= ( Hashtable ) ( warehouseInfo.elementAt( i ) );

           String brachplant=hD2.get( "BRANCHPLANT" ).toString();
           if ( Hubnum.equalsIgnoreCase( brachplant ) )
           {
             String facname=hD2.get( "FACILITYID" ).toString();
             out.println( "<OPTION " + ( facname.equalsIgnoreCase( facility ) ? "selected" : "" ) + " VALUE=\"" + facname + "\">" + facname + "</OPTION>\n" );
           }
         }
       }
       out.println( "</SELECT>\n" );
       out.println( "</TD>\n" );

       //Search
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<B>Search:</B>&nbsp;&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"10%\">\n" );
       out.println( "<SELECT CLASS=\"HEADER\" ID=\"searchlike\" NAME=\"searchlike\" size=\"1\">\n" );
       Hashtable sortresult=new Hashtable();
       sortresult.put( "Customer PO","RAYTHEON_PO" );
			 sortresult.put( "Item Id","ITEM_ID" );
			 sortresult.put( "Last Supplier","LAST_SUPPLIER" );
			 sortresult.put( "Manufacturer","MFG_ID" );
			 sortresult.put( "MR","MR_NUMBER" );
			 sortresult.put( "Part Number","PART_ID" );
			 sortresult.put( "PO","RADIAN_PO" );
			 sortresult.put( "PR Number","PR_NUMBER" );
			 sortresult.put( "Type","ITEM_TYPE" );

       out.println( radian.web.HTMLHelpObj.getDropDown( sortresult,searchLike ) );
       out.println( "</SELECT>\n" );
       out.println( "</TD>\n" );

       out.println( "<TD WIDTH=\"25%\" ALIGN=\"LEFT\" COLSPAN=\"2\">&nbsp;&nbsp;<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" type=\"text\" id=\"searchthis\" name=\"searchthis\" value=\"" +  searchthis + "\" SIZE=\"25\"></TD>\n" );

       //Create PO
       out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
       if ( this.getupdateStatus() )
       {
         out.println("<INPUT TYPE=\"submit\" VALUE=\"Create PO\" onclick= \"return createpo(this)\" ID=\"SearchButton\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
       }
       else
       {
         out.println( "&nbsp;\n" );
       }
       out.println( "</TD>\n" );

       out.println( "</TR>\n" );

       out.println( "</TR>\n" );
       //Only Unconfirmed PO
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<INPUT type=\"checkbox\" onClick= \"uncheckopenbuyorders()\" id=\"showall\" name=\"showall\" value=\"Yes\" " + ( showall.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"15%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
       out.println( "Only Unconfirmed PO #s&nbsp;\n" );
       out.println( "</TD>\n" );

       // Status
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<B>Status:</B>&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
       out.println( "<SELECT CLASS=\"HEADER\" ID=\"status\" NAME=\"status\" size=\"4\" multiple>\n" );
       /*String newSelected="";
       if ( statusStringfromArray.contains( "New" ) )
       {
         newSelected="selected";
       }*/

       /*String allstatSelected="";
       if ( statusStringfromArray.contains( "All" ) )
       {
         allstatSelected="selected";
       }*/
       /*out.println( "<OPTION " + newSelected + " VALUE=\"New\">New</OPTION>\n" );
       out.println( "<OPTION " + allstatSelected + " VALUE=\"All\">All Statuses</OPTION>\n" );*/
       out.println( radian.web.HTMLHelpObj.getmultipleDropDown( ( Vector ) session.getAttribute( "PO_STATUS" ),statusStringfromArray ) );
       out.println( "</SELECT>\n" );
       out.println( "</TD>\n" );

	   //Supply Path
	   out.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	   out.println( "<B>Supply Path:</B>&nbsp;\n" );
	   out.println( "</TD>\n" );

	   out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
	   out.println( "&nbsp;&nbsp;&nbsp;<SELECT CLASS=\"HEADER\" ID=\"headsupplypath\" NAME=\"headsupplypath\" size=\"1\">\n" );
	   if (selsuppath.length() == 0 ) {selsuppath = "All";}
	   Hashtable supplypath=new Hashtable();
	   supplypath.put( "All","All" );
	   supplypath.put( "Dbuy","Dbuy" );
	   supplypath.put( "Wbuy","Wbuy" );
       supplypath.put( "Ibuy","Ibuy" );
       supplypath.put( "Purchasing","Purchaser" );
	   //supplypath.put( "Repackage","Repackage" );

	   out.println( radian.web.HTMLHelpObj.getDropDown( supplypath,selsuppath ) );
	   out.println( "</SELECT>\n" );

	   out.println( "</TD>\n" );

       //Sort
       out.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<B>Sort:</B>&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">\n" );
       out.println( "<SELECT CLASS=\"HEADER\" ID=\"SortBy\" NAME=\"SortBy\" size=\"1\">\n" );
       out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "PO_SORTIT" ),sortby ) );
       out.println( "</SELECT>\n" );
       out.println( "</TD>\n" );


       out.println( "</TR>\n" );

       out.println( "</TR>\n" );
       //Show withn no po's
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<INPUT type=\"checkbox\" onClick= \"uncheckopenbuyorders()\" id=\"showonlynopo\" name=\"showonlynopo\" value=\"Yes\" " + ( showonlynopo.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"15%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
       out.println( "Only With No assigned PO #s&nbsp;\n" );
       out.println( "</TD>\n" );

       //Show withn Confirmed po's
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<INPUT type=\"checkbox\" onClick= \"uncheckopenbuyorders()\" id=\"showonlyconfpo\" name=\"showonlyconfpo\" value=\"Yes\" " + ( showonlyconfpo.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"15%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
       out.println( "Only Confirmed PO #s&nbsp;\n" );
       out.println( "</TD>\n" );

       //Open Buy Orders
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	   out.println( "<INPUT type=\"checkbox\" onClick= \"checkopnbuyorders()\" id=\"showopenbuyorders\" name=\"showopenbuyorders\" value=\"Yes\" " + ( openbuyorders.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
       //out.println( "<INPUT TYPE=\"hidden\" NAME=\"showopenbuyorders\" VALUE=\"\">\n" );
	   out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">\n" );
	   out.println( "PO Confirmed but Association not Confirmed&nbsp;\n" );
       out.println( "</TD>\n" );

       out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\">\n" );
       out.println( "&nbsp;\n" );
       out.println( "</TD>\n" );

       out.println( "</TR>\n" );

       out.println( "</TABLE>\n" );

       out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n" );
       out.println( "<tr><td>" );
       out.println( "<INPUT TYPE=\"hidden\" ID=\"UserAction\" NAME=\"UserAction\" VALUE=\"\">\n" );
       out.println( "<INPUT TYPE=\"hidden\" ID=\"SubUserAction\" NAME=\"SubUserAction\" VALUE=\"\">\n" );
       out.println( "<INPUT TYPE=\"hidden\" ID=\"thedecidingRandomNumber\" NAME=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );

       out.println( "</TD></tr>" );
       out.println( "</table>\n" );
       //end table #2

     }
     catch ( Exception e )
     {
       //e.printStackTrace();
       out.println( radian.web.HTMLHelpObj.printError( "Purchase Requests",intcmIsApplication ) );
     }
     return;
   }

   private void buildDetails( Vector data,HttpSession session )
   {
     //StringBuffer Msg=new StringBuffer();
     String Color=" ";
	 Hashtable hub_list_set = new Hashtable();
	 hub_list_set= (Hashtable)session.getAttribute("HUB_PREF_LIST");

     out.println( "<INPUT TYPE=\"hidden\" ID=\"radianPO\" NAME=\"radianPO\" VALUE=\"\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"lineID\" NAME=\"lineID\" VALUE=\" \">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"itemnum\" NAME=\"itemnum\" VALUE=\" \">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"numofHubs\" NAME=\"numofHubs\" VALUE=\" \">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"ammendment\" NAME=\"ammendment\" VALUE=\" \">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"itemfromLine\" NAME=\"itemfromLine\" VALUE=\" \">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"shipTofromLine\" NAME=\"shipTofromLine\" VALUE=\" \">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"hubfromLine\" NAME=\"hubfromLine\" VALUE=\" \">\n" );
     out.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showbuypagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n" );
     try
     {
       Hashtable summary=new Hashtable();
       summary= ( Hashtable ) data.elementAt( 0 );
       int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
       out.println( "<INPUT TYPE=\"hidden\" NAME=\"totalRecords\" ID=\"totalRecords\" VALUE=\"" + total + "\">\n" );
       out.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

       int i=0; //used for detail lines
       Vector statusVec= ( Vector ) session.getAttribute( "PO_SET_STATUS" );
	   Vector lockstatusV= ( Vector ) session.getAttribute( "PO_LOCK_STATUS" );
	   Vector noassgnstatus= ( Vector ) session.getAttribute( "PO_NON_ASGN_STATUS" );
       Vector statusVec1=new Vector();
       Vector statusVecNoCancel=new Vector();
	   Vector statusDbuyVec1=new Vector();
	   Vector statusDbuyVecNoCancel=new Vector();

	   statusDbuyVec1 =statusVec;
	   //statusVecNoCancel =statusVec;
	   //statusVecNoCancel.remove("Cancel");

       for ( int z=0; z < statusVec.size(); z++ )
       {
         Hashtable data1= ( Hashtable ) statusVec.elementAt( z );
         Enumeration E;
         for ( E=data1.keys(); E.hasMoreElements(); )
         {
           String key= ( String ) E.nextElement();
           String keyvalue=data1.get( key ).toString();
		   if ( !( ( keyvalue.indexOf("DBuy") > -1) || ( keyvalue.indexOf("WBuy") > -1) ) )
           {
             statusVec1.addElement( data1 );
           }
         }
       }

       for ( int z=0; z < statusVec.size(); z++ )
       {
         Hashtable data1= ( Hashtable ) statusVec.elementAt( z );
         Enumeration E;
         for ( E=data1.keys(); E.hasMoreElements(); )
         {
           String key= ( String ) E.nextElement();
           String keyvalue=data1.get( key ).toString();
           if ( !( "Cancel".equalsIgnoreCase( keyvalue ) || ( keyvalue.indexOf("DBuy") > -1) || ( keyvalue.indexOf("WBuy") > -1)) )
           {
             statusVecNoCancel.addElement( data1 );
           }
         }
       }

	  for ( int z=0; z < statusVec.size(); z++ )
	   {
		 Hashtable data1= ( Hashtable ) statusVec.elementAt( z );
		 Enumeration E;
		 for ( E=data1.keys(); E.hasMoreElements(); )
		 {
		   String key= ( String ) E.nextElement();
		   String keyvalue=data1.get( key ).toString();
		   if ( !( "Cancel".equalsIgnoreCase( keyvalue ) ) )
		   {
			 statusDbuyVecNoCancel.addElement( data1 );
		   }
		 }
	   }

       statusVec=null;

	   //This is a list of people who are not allowed to buy or no longer with Haas
	   Vector excludelist = new Vector();
	   excludelist.addElement("5668");
	   excludelist.addElement("85655");
	   excludelist.addElement("5282");
	   //excludelist.addElement("86652");
	   excludelist.addElement("18789");
	   excludelist.addElement("3060");
	   excludelist.addElement("3990");
	   excludelist.addElement("354");
	   excludelist.addElement("19288");
	   //excludelist.addElement("8076"); //Mathew can buy now
	   excludelist.addElement("85909");
	   excludelist.addElement("388"); //Luppens
	   excludelist.addElement("19496"); //Lynne
	   excludelist.addElement("8735");//Jesse
	   excludelist.addElement("9255");//Kendra
	   excludelist.addElement("7078");//Kim Millspaugh
		 excludelist.addElement("19313");//Kathy Buckler
		 excludelist.addElement("6269");//Don Ainsworth
		 excludelist.addElement("8815");//Jen Watson
 		 excludelist.addElement("8873");//John Holtke
		 excludelist.addElement("8076");//Matt Hale
		 excludelist.addElement("7222");//Melisa Bolton
		 excludelist.addElement("9317");//Ronnie Gitterman
		 excludelist.addElement("12685");//Silvia Boehm
		 excludelist.addElement("9918");//Tim Mazur
		 excludelist.addElement("9898");//Diana Sluhan

       for ( int loop=0; loop < total; loop++ )
       {
         i++;
         boolean createHdr=false;

         if ( loop % 10 == 0 )
         {
           createHdr=true;
         }

         if ( createHdr )
         {
           out.println( "<tr align=\"center\">\n" );
           out.println( "<TH WIDTH=\"2%\"  HEIGHT=\"25\">Grab<BR>(BPO)</TH>\n" );
           out.println( "<TH width=\"5%\"  height=\"38\">PR<BR>Created</TH>\n" );
           out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Ship To</TH>\n" );
           out.println( "<TH width=\"3%\"  height=\"38\">Hub<BR>(Inven Grp)</TH>\n" );
           out.println( "<TH width=\"5%\"  height=\"38\">Company</TH>\n" );
           out.println( "<TH width=\"5%\"  height=\"38\">Facility</TH>\n" );
           out.println( "<TH width=\"3%\"  height=\"38\">Item</TH>\n" );
           out.println( "<TH width=\"10%\"  height=\"38\">Item Description</TH>\n" );
           out.println( "<TH WIDTH=\"3%\"  HEIGHT=\"25\">Type</TH>\n" );
           out.println( "<TH width=\"3%\"  height=\"38\">Buy Qty</TH>\n" );
					 out.println( "<TH width=\"5%\"  height=\"38\">UOM</TH>\n" );
           out.println( "<TH width=\"5%\"  height=\"38\">Needed</TH>\n" );
           out.println( "<TH width=\"5%\"  height=\"38\">Buyer</TH>\n" );
           out.println( "<TH width=\"5%\"  height=\"38\">Status</TH>\n" );
           out.println( "<TH width=\"3%\"  height=\"38\">Buy Order Notes</TH>\n" );
           out.println( "<TH width=\"3%\"  height=\"38\">PO</TH>\n" );
           out.println( "<TH width=\"7%\"  height=\"38\">Manufacturer</TH>\n" );
           out.println( "<TH width=\"7%\"  height=\"38\">Last Supplier</TH>\n" );
           out.println( "<TH WIDTH=\"3%\"  HEIGHT=\"25\">MR-Line</TH>\n" );
           out.println( "<TH width=\"3%\"  height=\"38\">MR Qty</TH>\n" );
           out.println( "<TH WIDTH=\"8%\"  HEIGHT=\"25\">Requestor<BR>Ph<BR>email</TH>\n" );
           out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Part Number</TH>\n" );
           out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Client PO</TH>\n" );
           out.println( "<TH WIDTH=\"15%\"  HEIGHT=\"25\">Notes</TH>\n" );
           out.println( "<TH WIDTH=\"3%\"  HEIGHT=\"25\">MM</TH>\n" );
           out.println( "<TH WIDTH=\"3%\"  HEIGHT=\"25\">Reorder Point</TH>\n" );
           out.println( "<TH WIDTH=\"3%\"  HEIGHT=\"25\">Inventory</TH>\n" );
           out.println( "<TH WIDTH=\"3%\"  HEIGHT=\"25\">Open PO Qty</TH>\n" );

           out.println( "</tr>\n" );
           createHdr=false;
         }

         Hashtable hD=new Hashtable();
         hD= ( Hashtable ) data.elementAt( i );

         // get main information
         String branchplant=hD.get( "BRANCH_PLANT" ).toString();
         String facility=hD.get( "FACILITY" ).toString();
         //String buyer=hD.get( "BUYER" ).toString();
         String buyerId=hD.get( "BUYER_ID" ).toString();
         String prnumber1=hD.get( "PR_NUMBER" ).toString().trim();
         String datecreated=hD.get( "DATE_ISSUED" ).toString();
         String status=hD.get( "STATUS" ).toString();
         String radianpo=hD.get( "RADIAN_PO" ).toString();
         String lineitem1=hD.get( "MR_LINE_ITEM" ).toString().trim();
         String itemId=hD.get( "ITEM_ID" ).toString();
         String part=hD.get( "PART_ID" ).toString();
         String manufacturer=hD.get( "MFG_ID" ).toString();
         String needdate=hD.get( "NEED_DATE" ).toString();
         String qty=hD.get( "ORDER_QUANTITY" ).toString();
         String companyid=hD.get( "COMPANY_ID" ).toString();
         String itemdesc=hD.get( "ITEM_DESC" ).toString();
         String priority=hD.get( "PRIORITY" ).toString();
         String critical=hD.get( "CRITICAL" ).toString();
         String rlstatus=hD.get( "REQUEST_LINE_STATUS" ).toString();
         String cancelstatus=hD.get( "CANCEL_STATUS" ).toString();
         String itemtype=hD.get( "ITEM_TYPE" ).toString();
         String mrnumber=hD.get( "MR_NUMBER" ).toString();
         String clientpo=hD.get( "RAYTHEON_PO" ).toString();
         String shiptoId=hD.get( "SHIP_TO_LOCATION_ID" ).toString();
         String reorderpoint=hD.get( "REORDER_POINT" ).toString();
         String stockinglevel=hD.get( "STOCKING_LEVEL" ).toString();
         String inventory=hD.get( "AVAILABLE_QUANTITY" ).toString();
         String prNotes=hD.get( "PRNOTES" ).toString();
         String fullname=hD.get( "REQUESTOR_FIRST_NAME" ).toString() + " " + hD.get( "REQUESTOR_LAST_NAME" ).toString();
         String reqphone=hD.get( "PHONE" ).toString();
         String reqemail=hD.get( "EMAIL" ).toString();
         String openpoqty=hD.get( "OPEN_PO_QUANTITY" ).toString();
         String deliverytype=hD.get( "DELIVERY_TYPE" ).toString();
         String prassociated=hD.get( "PRASSOCIATED" ).toString();
         String lastsupplier=hD.get( "LAST_SUPPLIER" ).toString();
         String buyordercomments=hD.get( "COMMENTS" ).toString();
         String mrQuantity=hD.get( "MR_QUANTITY" ).toString();
         String engevaluation=hD.get( "ENGINEERING_EVALUATION" ).toString();
         String reqid=hD.get( "REQUEST_ID" ).toString();
         String catid=hD.get( "CATALOG_ID" ).toString();
         String stocekd=hD.get( "STOCKED" ).toString();
         String invengrp=hD.get( "INVENTORY_GROUP" ).toString();
		 String bpodet=hD.get( "BPO" ).toString();
		 String supplypath=hD.get( "SUPPLY_PATH" ).toString();
		 String frozenpr=hD.get( "FROZEN" ).toString();
		 String consolidationAllowed=hD.get( "CONSOLIDATION_ALLOWED" ).toString();
		 String packaging=hD.get( "SIZE_UNIT" ).toString();

         String checked="";
         if ( "Yes".equalsIgnoreCase( prassociated ) )
         {
           checked=" checked";
         }

         boolean showcheckbox=true;
         if ( "1".equalsIgnoreCase( priority ) )
         {
           Color="CLASS=\"red";
         }
         if ( "2".equalsIgnoreCase( priority ) )
         {
           Color="CLASS=\"yellow";
         }
         if ( "3".equalsIgnoreCase( priority ) )
         {
           Color="CLASS=\"green";
         }

         if ( "Y".equalsIgnoreCase( critical ) )
         {
           Color="CLASS=\"red";
         }

		 if ( "S".equalsIgnoreCase( critical ) )
		 {
		   Color="CLASS=\"pink";
		 }

         if ( "y".equalsIgnoreCase( engevaluation ) )
         {
           Color="CLASS=\"purple";
         }

         if ( "rqcancel".equalsIgnoreCase( cancelstatus ) || "canceled".equalsIgnoreCase( cancelstatus ) || "Cancelled".equalsIgnoreCase( rlstatus ) || "Pending Cancellation".equalsIgnoreCase( rlstatus ) || "Closed".equalsIgnoreCase( status ) || "Cancel".equalsIgnoreCase( status ) )
         {
           Color="CLASS=\"black";
           showcheckbox=false;
         }

         if ( invengrp.trim().length() ==0)
         {
          showcheckbox=false;
         }
         
     boolean canupdateline=false;
		 boolean lockedline=false;
		 if ( this.getupdateStatus() )
		 {
		   canupdateline=true;
		   if ( lockstatusV.contains( status ) || "Y".equalsIgnoreCase(frozenpr) || "ProblemDBuy".equalsIgnoreCase(status) || "ProblemWBuy".equalsIgnoreCase(status))
		   {
			 canupdateline=false;
			 lockedline = true;
		   }
		 }

		boolean directSupplyPath = false;
		if ("DBuy".equalsIgnoreCase(supplypath) || "Wbuy".equalsIgnoreCase(supplypath) )
		{
		  directSupplyPath = true;
		}

		String hubname=radian.web.HTMLHelpObj.gethubNamefromBP( hub_list_set,branchplant );
		if ( hubname.trim().length() == 0 )
		{
		  canupdateline=false;
		}
		else
		{
		  PersonnelBean personnelBean=session.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) session.getAttribute( "personnelBean" );
		  if ( !personnelBean.getPermissionBean().hasFacilityPermission( "BuyOrder",hubname,null ) )
		  {
			canupdateline=false;
		  }
		}

         out.println( "<tr align=\"center\">\n" );
         if ( canupdateline )
         {
           if ( "Yes".equalsIgnoreCase( prassociated ) || !showcheckbox || prnumber1.trim().length() == 0 )
           {
             out.println( "<TD " + Color + "l\" WIDTH=\"2%\"><INPUT TYPE=\"checkbox\" STYLE=\"display:none\" value=\"Yes\" NAME=\"" + i + "\" ID=\"" + i + "\"></TD>\n" );
           }
		   else
		   {
			 out.println( "<TD " + Color + "l\" WIDTH=\"2%\"><INPUT TYPE=\"checkbox\" value=\"Yes\" NAME=\"" + i + "\" ID=\"" + i + "\" " + checked + "  onClick=\"checkEditPrsValues(name,this)\">\n" );
			 if ( "Y".equalsIgnoreCase( bpodet ) )
			 {
			   out.println( "<A HREF=\"javascript:showopenbpos('" + prnumber1 + "')\">(Y)</A>" );
			 }
			 out.println( "</TD>\n" );
		   }
         }
         else
         {
           out.println( "<TD " + Color + "l\" WIDTH=\"2%\">&nbsp;<INPUT TYPE=\"checkbox\" STYLE=\"display:none\" value=\"Yes\" NAME=\"" + i + "\" ID=\"" + i + "\"></TD>\n" );
         }

         out.println( "<td " + Color + "\" width=\"5%\">" + prnumber1 + "<BR>" + datecreated + "<INPUT TYPE=\"hidden\" NAME=\"openbalnket" + i + "\" ID=\"openbalnket"+ i +"\" value=\"" + bpodet + "\"><INPUT TYPE=\"hidden\" NAME=\"prnumber" + i + "\" ID=\"prnumber"+ i +"\" value=\"" + prnumber1 + "\"></td>\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + shiptoId + "<INPUT TYPE=\"hidden\" NAME=\"facility" + i + "\" ID=\"facility\" value=\"" + shiptoId + "\"></TD>\n" );

		  //TRONG 4-4-05
		  if ("Y".equalsIgnoreCase(consolidationAllowed)){
		  Vector inventoryTransferRoute = getInventoryTransferRoute(invengrp);
		  //if (inventoryTransferRoute.size() > 0) {
		   String transferRoute = "";
		   for (int ii = 0; ii < inventoryTransferRoute.size(); ii++) {
			 transferRoute += (String)inventoryTransferRoute.elementAt(ii)+",";
		   } //end of for loop
		   //removing the last commas ','
		   if (transferRoute.length() > 0) {
			 transferRoute = URLEncoder.encode(transferRoute.substring(0,transferRoute.length()-1));
		   }

		   out.println( "<TD " + Color + "\" WIDTH=\"3%\">" + branchplant +
						"<BR>(<A HREF=\"javascript:showBuyOrderTransfer('" + prnumber1 + "','" + shiptoId +"','"+invengrp+"','"+companyid+"','"+
						transferRoute+"','"+itemId+"','"+itemtype+"','"+qty+"')\">" + invengrp + "</A>)"+
						"<INPUT TYPE=\"hidden\" NAME=\"branchplant" + i + "\" ID=\"branchplant" + i + "\" value=\"" + branchplant +
						"\"><INPUT TYPE=\"hidden\" NAME=\"invengrp" + i + "\" ID=\"invengrp" + i + "\" value=\"" + invengrp + "\"></TD>" );
		 }
		 else
		 {
		   out.println( "<TD " + Color + "\" WIDTH=\"3%\">" + branchplant + "<BR>(" + invengrp + ")<INPUT TYPE=\"hidden\" NAME=\"branchplant" + i + "\" ID=\"branchplant" + i + "\" value=\"" + branchplant + "\"><INPUT TYPE=\"hidden\" NAME=\"invengrp" + i + "\" ID=\"invengrp" + i + "\" value=\"" + invengrp + "\"></TD>\n" );
		 }

		 out.println( "<td " + Color + "\" width=\"5%\">\n");
     if (companyid.equalsIgnoreCase("Radian"))
     {
      out.println("Haas TCM");
     }
     else
     {
       out.println(companyid);
     }
     out.println("<INPUT TYPE=\"hidden\" NAME=\"companyid" + i + "\" ID=\"companyid" + i + "\" value=\"" + companyid + "\"></td>\n" );

         out.println( "<td " + Color + "\" width=\"5%\">" + facility + "</td>\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"3%\">&nbsp;<INPUT TYPE=\"hidden\" NAME=\"itemid" + i + "\" ID=\"itemid" + i + "\" value=\"" + itemId + "\"><INPUT TYPE=\"hidden\" NAME=\"supplypath" + i + "\" ID=\"supplypath" + i + "\" value=\"" + supplypath + "\">" );
         if ( itemId.length() > 0 )
         {
					out.println( "<A HREF=\"javascript:showitemtcmBuys('" + itemId + "','" + shiptoId + "')\">" + itemId + "</A>" );
 				  out.println( "<input type=\"image\" id=\"itemNotes\" name=\"itemNotes\" src=\"/images/ponotes.gif\" alt=\"Item Notes\" onclick=\"editItemNotes('"+itemId+"'); return false;\" border=\"0\">");
         }

         if ( "Y".equalsIgnoreCase( stocekd ) )
         {
           out.println( "<BR>(Stocked)\n" );
         }
         out.println( "</TD>\n" );

         out.println( "<td " + Color + "l\" width=\"10%\">" + itemdesc + "</td>\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"3%\">" + itemtype + "</TD>\n" );
         out.println( "<td " + Color + "\" width=\"3%\">" + qty + "</td>\n" );
				 out.println( "<td " + Color + "\" width=\"3%\">" + packaging + "</td>\n" );

         if ( "Y".equalsIgnoreCase( deliverytype ) )
         {
           out.println( "<td " + Color + "\" width=\"5%\">" + needdate + "<BR><A HREF=\"javascript:showschedulde('" + mrnumber + "','" + lineitem1 + "')\">Schedule</A></td>\n" );
         }
         else
         {
           out.println( "<td " + Color + "\" width=\"5%\">" + needdate + "</td>\n" );
         }

         out.println( "<td " + Color + "\" width=\"5%\">\n" );
         if ( canupdateline && ! ( "Closed".equalsIgnoreCase( status ) ) )
         {
           out.println( "<SELECT CLASS=\"HEADER\" ID=\"buyerid" + i + "\" NAME=\"buyerid" + i + "\" OnChange=\"checkbuyer(" + i + ")\" size=\"1\">\n" );
           //if ( !(radianpo.length() > 0 && buyerId.length()>0))
           {
             out.println( "<OPTION VALUE=\"\"></OPTION>\n" );
           }
           out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "PO_BUYERNAMES" ),buyerId,excludelist ) );
           out.println( "</SELECT>\n" );
         }
         else
         {
           Vector testbuyer= ( Vector ) session.getAttribute( "PO_BUYERNAMES" );
           for ( int z=0; z < testbuyer.size(); z++ )
           {
             Hashtable data1= ( Hashtable ) testbuyer.elementAt( z );
             Enumeration E;
             for ( E=data1.keys(); E.hasMoreElements(); )
             {
               String key= ( String ) E.nextElement();
               if ( buyerId.equalsIgnoreCase( data1.get( key ).toString() ) )
               {
                 out.println( key );
               }
             }
             out.println( "<INPUT TYPE=\"hidden\" NAME=\"buyerid" + i + "\" ID=\"buyerid" + i + "\" value=\"" + buyerId + "\">" );
           }
           testbuyer=null;
         }

         out.println( "</TD>\n" );

         if ( "Closed".equalsIgnoreCase( status ) || ( "Confirmed".equalsIgnoreCase( status ) && radianpo.length() > 0 ) )
         {
           out.println( "<td " + Color + "\" width=\"5%\">\n" );
           out.println( status );
           out.println( "<INPUT TYPE=\"hidden\" NAME=\"statusofpr" + i + "\" ID=\"statusofpr" + i + "\" value=\"" + status + "\">" );
           out.println( "</TD>\n" );
         }
         else
         {
           out.println( "<td " + Color + "\" width=\"5%\">\n" );
           if ( canupdateline )
           {
             out.println( "<SELECT CLASS=\"HEADER\" ID=\"statusofpr" + i + "\" NAME=\"statusofpr" + i + "\" size=\"1\" OnChange=\"checkdbuystat('"+i+"')\">\n" );
             String newSelected="";
             if ( status.equalsIgnoreCase( "New" ) )
             {
               newSelected="selected";
             }
             if ( !(radianpo.length() > 0 && buyerId.length()>0 && !status.equalsIgnoreCase( "New" )))
             {
               out.println( "<OPTION " + newSelected + " VALUE=\"New\">New</OPTION>\n" );
             }

			 if (!status.equalsIgnoreCase( "New" ) && noassgnstatus.contains(status))
			 {
			   out.println( "<OPTION selected VALUE=\""+status+"\">"+status+"</OPTION>\n" );
			 }

			if ( status.equalsIgnoreCase( "DBuyReview" ) )
			{
			  out.println( "<OPTION VALUE=\"InProgressDBuy\">InProgressDBuy</OPTION>\n" );
			}

			if ( status.equalsIgnoreCase( "WBuyReview" ) )
			{
			  out.println( "<OPTION VALUE=\"InProgressWBuy\">InProgressWBuy</OPTION>\n" );
			}

             if ( radianpo.length() > 0 )
             {
               /*if ( status.equalsIgnoreCase( "Confirmed" ) )
               {
                 newSelected="selected";
                 out.println( "<OPTION " + newSelected + " VALUE=\"Confirmed\">Confirmed</OPTION>\n" );
               }
               else if ( status.equalsIgnoreCase( "Closed" ) )
               {
                 newSelected="selected";
                 out.println( "<OPTION " + newSelected + " VALUE=\"Closed\">Closed</OPTION>\n" );
               }*/
               if ( status.equalsIgnoreCase( "Cancel" ) )
               {
                 newSelected="selected";
                 out.println( "<OPTION " + newSelected + " VALUE=\"Cancel\">Cancel</OPTION>\n" );
               }

	           if (directSupplyPath)
			   {
				 out.println( radian.web.HTMLHelpObj.getDropDown( statusDbuyVecNoCancel,status ) );
			   }
			   else
			   {
				 out.println( radian.web.HTMLHelpObj.getDropDown( statusVecNoCancel,status ) );
			   }
             }
			 else if ( !showcheckbox )
			 {
			   if ( directSupplyPath )
			   {
				 out.println( radian.web.HTMLHelpObj.getDropDown( statusDbuyVec1,status ) );
			   }
			   else
			   {
				 out.println( radian.web.HTMLHelpObj.getDropDown( statusVec1,status ) );
			   }
			 }
			 else
			 {
			   if ( directSupplyPath )
			   {
				 out.println( radian.web.HTMLHelpObj.getDropDown( statusDbuyVecNoCancel,status ) );
			   }
			   else
			   {
				 out.println( radian.web.HTMLHelpObj.getDropDown( statusVecNoCancel,status ) );
			   }
				}
				out.println( "</SELECT>\n" );
			 }
			 else
			 {
				out.println( status );
				out.println( "<INPUT TYPE=\"hidden\" NAME=\"statusofpr" + i + "\" ID=\"statusofpr" + i + "\" value=\"" + status + "\">" );
			 }
			 if ("ProblemDBuy".equalsIgnoreCase(status))
			 {
				String wwwHome=bundle.getString( "WEB_HOME_PAGE" );
				String problemDBuyUrl=wwwHome + "tcmIS/supplier/mismatch.do?po=" + radianpo;
				out.println( "<BR><A onclick=\"javascript:window.open('"+ problemDBuyUrl + "')\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\">Mismatch Comments</A>");
		   }
			 out.println( "</TD>\n" );
			}

         out.println( "<TD WIDTH=\"5%\" " + Color + "\">" );
         if ( (this.getupdateStatus() || this.getallowbuyernotes()) && !lockedline )
         {
           out.println( "<TEXTAREA id=\"buyocomments" + i + "\" name=\"buyocomments" + i + "\" rows=\"3\" cols=\"25\">" + buyordercomments + "</TEXTAREA>\n" );
         }
         else
         {
           out.println( buyordercomments );
		   out.println( "<INPUT TYPE=\"hidden\" NAME=\"buyocomments" + i + "\" ID=\"buyocomments" + i + "\" value=\"" + buyordercomments + "\">" );
         }
         out.println( "\n</TD>\n" );

         if ( radianpo.trim().length() > 0 )
         {
           String wwwHome=bundle.getString( "WEB_HOME_PAGE" );
           String radianpourl=wwwHome + "tcmIS/supply/purchorder?po=" + radianpo + "&Action=searchlike&subUserAction=po";

           out.println( "<td " + Color + "\" width=\"3%\"><A onclick=\"javascript:window.open('" + radianpourl + "')\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\">" + radianpo + "</A></td>\n" );
         }
         else
         {
           out.println( "<td " + Color + "\" width=\"3%\">&nbsp;</td>\n" );
         }

	     out.println( "<td " + Color + "l\" width=\"7%\">" + manufacturer + "</td>\n" );
         out.println( "<td " + Color + "l\" width=\"7%\">" + lastsupplier + "</td>\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"3%\">" + mrnumber + "-" + lineitem1 + "</TD>\n" );
         out.println( "<td " + Color + "\" width=\"3%\">" + mrQuantity + "</td>\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"8%\">" + fullname + "<BR>" + reqphone + "<BR>" + reqemail + "</TD>\n" );

         if ( part.trim().length() > 0 )
         {
           out.println( "<TD " + Color + "\" WIDTH=\"5%\"><A HREF=\"javascript:getsuggestedSupplier('" + part + "','" + reqid + "','" + catid + "')\">" + part + "</A></TD>\n" );
         }
         else
         {
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + part + "</TD>\n" );
         }

         out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + clientpo + "<INPUT TYPE=\"hidden\" NAME=\"customerPo" + i + "\" ID=\"customerPo\" value=\"" + clientpo + "\"></TD>\n" );
         out.println( "<TD " + Color + "l\" WIDTH=\"15%\">" + prNotes + "</TD>\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"3%\">" + stockinglevel + "</TD>\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"3%\">" + reorderpoint + "</TD>\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"3%\">" + inventory + "</TD>\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"3%\">" + openpoqty + "</TD>\n" );
         out.println( "</tr>\n" );
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
       //e.printStackTrace();
       out.println( radian.web.HTMLHelpObj.printError( "Purchase Requests",intcmIsApplication ) );
     }

     return;
   }

   private Vector getInventoryTransferRoute(String inventoryGroup) {
	 Vector v = new Vector(11);
	 DBResultSet dbrs = null;
	 ResultSet rs = null;
	 try{
	   String query = "select source_inventory_group from inventory_transfer_route where inventory_group = '"+inventoryGroup+"' order by source_inventory_group";
	   dbrs=db.doQuery( query );
	   rs=dbrs.getResultSet();
	   while ( rs.next()){
		 v.addElement(rs.getString("source_inventory_group"));
	   }
	 }catch (Exception e) {
	   //e.printStackTrace();
	 }
	 return v;
   } //end of method

   private void buildAssociatedPRS( Vector data,String SubuserAction,String lineID,String radianPO,
                                            String ItemID,String setlinestatus,String ammendmeant,String hubNumber,
                                            String lineItemId,String shipTofromLine,String shipTo,String mainshiptocompanyid,String lineshiptocompanyid,
                                            String currencyId)
   {
     //StringBuffer Msgn=new StringBuffer();
     //StringBuffer Msgbody=new StringBuffer();
     String invengrp="";
     out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Supplier","",intcmIsApplication ) );
     //StringBuffer Msgtemp=new StringBuffer();

     //Build the Java Script Here and Finish the thing
     out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
     out.println( "<!--\n" );
     out.println( "function sendSupplierData()\n" );
     out.println( "{\n" );

     if ( unassociatedprs || associatedprs || SubuserAction.equalsIgnoreCase( "associatedPRs" ) )
     {
       Hashtable summary=new Hashtable();
       summary= ( Hashtable ) data.elementAt( 0 );
       int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();


       int totalitemQty=0;
       String clientpo="";

       Timestamp testNeedDate=null;
       String finalNeedDate="";
       String lineitem1="";
	   String branchplant="";
	   String finalCritical="";
       String itemid ="";
       try
       {
         out.println( "var associatedprcell = opener.document.getElementById(\"row5detaildivrow" + lineID + "" + lineID + "\");\n" );
         out.println( "var insidehtml =\"<TABLE ID=\\\"associatedprtable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
         out.println( "insidehtml +=\"<TR>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.prnumber")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.mrline")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.partnumber")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.specs")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.type")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.lpp")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\">"+res.getString("label.notes")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" HEIGHT=\\\"25\\\">"+res.getString("label.purchasingnote")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" HEIGHT=\\\"25\\\">"+res.getString("label.buyordernotes")+"</TH>\";\n" );
         //out.println("insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">% SL</TH>\";\n");
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.reuqestphemail")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.csr")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"3%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.mr.qty")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"3%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.buyquantity")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"3%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.uom")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.needed")+"<br>("+res.getString("label.dateformat")+")</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.shipto")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.facility")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.deliverypoint")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.hub")+"<BR>("+res.getString("label.invengroup")+")</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.clientpo")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.mm")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.reorderpoint")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.inventory")+"</TH>\";\n" );
         out.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.openpoquantity")+"</TH>\";\n" );        

         if ( total == 0 )
         {
           out.println( "insidehtml +=\"<TR>\";\n" );
           out.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"22\\\">No Records Found</TD>\";\n" );
           out.println( "insidehtml +=\"</TR>\";\n" );

           out.println( "attachedpr = opener.document.getElementById(\"attachedpr\");\n" );
           out.println( "attachedpr.value = \"N\";\n" );
         }
         else
         {
           int i=0;
           for ( int loop=0; loop < total; loop++ )
           {
             i++;
             Hashtable hD=new Hashtable();
             hD= ( Hashtable ) data.elementAt( i );

             // get main information
             branchplant=hD.get( "BRANCH_PLANT" ).toString();
             String facility=hD.get( "FACILITY" ).toString();
             String prnumber1=hD.get( "PR_NUMBER" ).toString().trim();
             lineitem1=hD.get( "MR_LINE_ITEM" ).toString().trim();
             String mrnumber=hD.get( "MR_NUMBER" ).toString();
             String needdate=hD.get( "NEED_DATE" ).toString();
             String qty=hD.get( "ORDER_QUANTITY" ).toString();
             clientpo=hD.get( "RAYTHEON_PO" ).toString();
             clientpo=HelpObjs.addescapesForJavascript( clientpo );

             if ( qty.length() > 0 )
             {
               totalitemQty+=Integer.parseInt( qty );
             }

             String reorderpoint=hD.get( "REORDER_POINT" ).toString();
             String stockinglevel=hD.get( "STOCKING_LEVEL" ).toString();
             String inventory=hD.get( "AVAILABLE_QUANTITY" ).toString();
             String partnumber=HelpObjs.addescapesForJavascript( hD.get( "PART_ID" ).toString() );
             String shiptoIdonpr=hD.get( "SHIP_TO_LOCATION_ID" ).toString();
             String notes=HelpObjs.addescapesForJavascript( hD.get( "PRNOTES" ).toString() );
             if ( notes.trim().length() > 0 )
             {
               notes=HelpObjs.addescapesForJavascript( notes );
             }

             String itemtype=hD.get( "ITEM_TYPE" ).toString();
             String fullname=HelpObjs.addescapesForJavascript(hD.get( "REQUESTOR_FIRST_NAME" ).toString() + " " + hD.get( "REQUESTOR_LAST_NAME" ).toString());
             String reqphone=hD.get( "PHONE" ).toString();
             String reqemail=HelpObjs.addescapesForJavascript( hD.get( "EMAIL" ).toString() );
             String openpoqty=hD.get( "OPEN_PO_QUANTITY" ).toString();
             String lppprice=hD.get( "LPP" ).toString();
             String companyid=hD.get( "COMPANY_ID" ).toString();
             String deliverytype=hD.get( "DELIVERY_TYPE" ).toString();
             String mrQuantity=hD.get( "MR_QUANTITY" ).toString();
             String reqid=hD.get( "REQUEST_ID" ).toString();
             String catid=hD.get( "CATALOG_ID" ).toString();
             String stocekd=hD.get( "STOCKED" ).toString();
             invengrp=hD.get( "INVENTORY_GROUP" ).toString();
			 itemid=hD.get( "ITEM_ID" ).toString();
			 String deliverypoint=hD.get( "SHIP_TO_DELIVERY_POINT" ).toString();
			 String critical=hD.get( "CRITICAL" ).toString();
			 if ( "Y".equalsIgnoreCase( critical ) )
			 {
			   finalCritical="Y";
			 }

			 if ( "S".equalsIgnoreCase( critical ) )
			 {
			   finalCritical="S";
			 }
			 String packaging=hD.get( "SIZE_UNIT" ).toString();
             String purchasingNote=HelpObjs.addescapesForJavascript( hD.get( "PURCHASING_NOTE" ).toString());
             String specList=HelpObjs.addescapesForJavascript(hD.get( "SPEC_LIST" ).toString());
             String csrName=HelpObjs.addescapesForJavascript(hD.get( "CSR_NAME" ).toString());
             String buyordercomments=HelpObjs.addescapesForJavascript(hD.get( "COMMENTS" ).toString());
             String remainingShelfLifePercent=hD.get("REMAINING_SHELF_LIFE_PERCENT") == null ? "" : hD.get("REMAINING_SHELF_LIFE_PERCENT").toString();

             if ( loop == 0 )
             {
               finalNeedDate=needdate;
               testNeedDate=radian.web.HTMLHelpObj.getDateFromString( needdate );
             }

             String Color=" ";
             if ( loop % 2 == 0 )
             {
               Color="CLASS=\\\"Inwhite";
             }
             else
             {
               Color="CLASS=\\\"Inblue";
             }

             out.println( "insidehtml +=\"<TR ALIGN=\\\"center\\\">\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + prnumber1 + "</TD>\";\n" );             
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"><A HREF=\\\"javascript:showMrLineAllocationReport('" +companyid+ "','"+mrnumber+"','"+lineitem1+"','"+itemid+"','"+invengrp+"')\\\">" + mrnumber + "-" + lineitem1 + "</A></TD>\";\n" );
             if ( partnumber.length() > 0 )
             {
               out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"><A HREF=\\\"javascript:getsuggestedSupplier('" + partnumber + "','" + reqid + "','" + catid + "')\\\">" + partnumber + "</A>\";\n" );
             }
             else
             {
               out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + partnumber + "\";\n" );
             }
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + specList + "\";\n" );

             if ( "Y".equalsIgnoreCase( stocekd ) )
             {
               out.println( "insidehtml +=\"<BR>(Stocked)\";\n" );
             }
             out.println( "insidehtml +=\"</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + itemtype + "</TD>\";\n" );
             out.println( "insidehtml +=\"<td " + Color + "\\\" width=\\\"5%\\\"><A HREF=\\\"javascript:showLPPranges('" +companyid+ "','"+mrnumber+"','"+lineitem1+"','"+itemid+"','"+invengrp+"')\\\">" + lppprice + "</A></td>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"15%\\\">" + notes + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"10%\\\">" + purchasingNote + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"10%\\\">" + buyordercomments + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + fullname + "<BR>" + reqphone + "<BR>" + reqemail + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"3%\\\">" + csrName + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"3%\\\">" + mrQuantity + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"3%\\\">" + qty + "</TD>\";\n" );
						 out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"3%\\\">" + HelpObjs.addescapesForJavascript(packaging) + "</TD>\";\n" );

             if ( "Y".equalsIgnoreCase( deliverytype ) )
             {
               out.println( "insidehtml +=\"<td " + Color + "\\\" width=\\\"5%\\\">" + needdate + "<BR><A HREF=\\\"javascript:showschedulde(\\\'" + mrnumber + "\\\',\\\'" + lineitem1 + "\\\')\\\">Schedule</A></td>\";\n" );
             }
             else
             {
               out.println( "insidehtml +=\"<td " + Color + "\\\" width=\\\"5%\\\">" + needdate + "</td>\";\n" );
             }

             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + shiptoIdonpr + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + facility + "</TD>\";\n" );
			 out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + deliverypoint + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + branchplant + "<BR>(" + invengrp + ")</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + clientpo + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + stockinglevel + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + reorderpoint + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + inventory + "</TD>\";\n" );
             out.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + openpoqty + "</TD>\";\n" );
             out.println( "insidehtml +=\"<input name=\\\"remainingShelfLifePercent\\\" id=\\\"remainingShelfLifePercent"+lineID+i+"\\\" type=\\\"hidden\\\" value=\\\""+remainingShelfLifePercent+"\\\"/>\";\n" );
             out.println( "insidehtml +=\"</TR>\";\n" );

             Timestamp neeDate1=radian.web.HTMLHelpObj.getDateFromString( needdate );

             if ( testNeedDate.after( neeDate1 ) )
             {
               testNeedDate=neeDate1;
               finalNeedDate=needdate;
             }
           }

		  if ( finalCritical.length() > 0 )
		  {
			out.println( "criticalpo = opener.document.getElementById(\"criticalpo\");\n" );
			out.println( "criticalpo.value = \"" + finalCritical + "\";\n" );
		  }

         }
         out.println( "insidehtml +=\"</TABLE>\";\n" );
       }
       catch ( Exception e )
       {
         //e.printStackTrace();
         out.println( radian.web.HTMLHelpObj.printError( "Purchase Requests",intcmIsApplication ) );
       }

	  out.println( "associatedprcell.innerHTML =insidehtml;\n" );

	  DBResultSet dbrs=null;
	  ResultSet rs=null;
      int newPoLine = 0;
      if ( total > 0 )
	  {
		try
		{
		  dbrs=db.doQuery( "Select BRANCH_PLANT from buy_order where RADIAN_PO = '" + radianPO + "' " );
		  rs=dbrs.getResultSet();
		  String hubnumber="";

		  while ( rs.next() )
		  {
			hubnumber=rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ).trim();
		  }

		  out.println( "HubName = opener.document.getElementById(\"HubName\");\n" );
		  out.println( "HubName.value = \"" + hubnumber + "\";\n" );
		}
		catch ( Exception e )
		{
		  //e.printStackTrace();
		}
		finally
		{
		  if (dbrs!= null) {dbrs.close();}
		}

		BigDecimal rowNumber= new BigDecimal(lineID);
        rowNumber = rowNumber.multiply(new BigDecimal("1000"));
        try
	    {
          dbrs=db.doQuery( "Select * from po_line_detail_view where RADIAN_PO = '" + radianPO + "' and po_line = "+rowNumber+"" );
		  rs=dbrs.getResultSet();
		  while ( rs.next() )
		  {
			newPoLine++;
		  }
        }
        catch ( Exception e )
        {
          //e.printStackTrace();
        }
        finally
        {
          if (dbrs!= null) {dbrs.close();}
        }

        if (newPoLine == 0)
        {
          Connection connect1;
          CallableStatement cs = null;
          try
          {
            String addDraftLine = "insert into po_line_draft (radian_po,po_line,amendment,item_id,quantity,currency_id)" +
		    "values ("+radianPO+","+rowNumber+",'0',"+itemid+","+totalitemQty+",'"+currencyId+"')";
 			  db.doUpdate( addDraftLine );

              connect1 = db.getConnection();
              cs = connect1.prepareCall("{call pkg_po.spec_to_buy(?,?)}");
              cs.setBigDecimal( 1,new BigDecimal(""+ radianPO +"") );
              cs.setBigDecimal(2,rowNumber);
              buylog.info("calling Procedure pkg_po.spec_to_buy : "+radianPO+" rowNumber "+rowNumber+" ");
              cs.execute();
              connect1.commit();

              cs = connect1.prepareCall("{call pkg_po.flowdown_to_buy(?,?)}");
              cs.setBigDecimal( 1,new BigDecimal(""+ radianPO +"") );
              cs.setBigDecimal(2,rowNumber);
              buylog.info("calling Procedure pkg_po.spec_to_buy : "+radianPO+" rowNumber "+rowNumber+" ");
              cs.execute();
              connect1.commit();

              cs.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        }        
      }
	  //if the uses click on add before clicking on the plus this part sets the image to minus and also the underlying hidden elements
	  if ( "yes".equalsIgnoreCase( setlinestatus ) )
	  {
         out.println( "var itemnotesimgae = opener.document.getElementById(\"associatedprimg" + lineID + "\");\n" );
         out.println( "itemnotesimgae.setAttribute(\"src\",'/images/minus.jpg');\n" );

         out.println( "var itemnotestatus  =  opener.document.getElementById(\"associatedprsstatus" + lineID + "\");\n" );
         out.println( "itemnotestatus.value = \"Yes\";\n" );
       }

       out.println( "var totalofassociatedprs  =  opener.document.getElementById(\"totalofassociatedprs" + lineID + "\");\n" );
       out.println( "totalofassociatedprs.value = \"" + totalitemQty + "\";\n" );

       if ( "UpdateeditassociatedPRs".equalsIgnoreCase( SubuserAction ) )
       {

         out.println( "var nofassociatedprs  =  opener.document.getElementById(\"nofassociatedprs" + lineID + "\");\n" );
         out.println( "nofassociatedprs.value = \"" + noofPrsAccosiated + "\";\n" );

         out.println( "var prshipto  =  opener.document.getElementById(\"prshipto" + lineID + "\");\n" );
         out.println( "prshipto.value = \"" + shipTofromLine + "\";\n" );

       }

       if ( ! ( ( total == 0 ) && unassociatedprs ) )
       {
         out.println( "invengrp = opener.document.getElementById(\"invengrp\");\n" );
         out.println( "invengrp.value = \"" + invengrp + "\";\n" );
       }

       if ( "UpdateeditassociatedPRs".equalsIgnoreCase( SubuserAction ) && total > 0 )
       {
         out.println( "attachedpr = opener.document.getElementById(\"attachedpr\");\n" );
         out.println( "attachedpr.value = \"Y\";\n" );
       }

       int ammendmeantno=0;
       if ( ammendmeant.length() > 0 )
       {
         ammendmeantno=Integer.parseInt( ammendmeant );
       }

       if ( ( total == 0 ) && unassociatedprs )
       {
         out.println( "integerammendmeantno = " + ammendmeantno + "\n" );
         out.println( "var originallinestatus = opener.document.getElementById(\"originallinestatus" + lineID + "\");\n" );
         out.println( "if (originallinestatus.value != \"Confirmed\" && integerammendmeantno == 0) \n" );
         out.println( "{\n" );
        /* out.println( "buttonlineitemid = opener.document.getElementById(\"buttonlineitemid" + lineID + "\");\n" );
         out.println( "buttonlineitemid.disabled=false;\n" );
         out.println( "buttonlineitemid.style.display=\"\";\n" );*/
         out.println( "lineitemid = opener.document.getElementById(\"lineitemid" + lineID + "\");\n" );
         out.println( "lineitemid.className = \"INVALIDTEXT\";\n" );
         out.println( "lineitemid.value = \"\";\n" );
         out.println( "selectedRow = opener.document.getElementById(\"validitem" + lineID + "\");\n" );
         out.println( "selectedRow.value = \"No\";\n" );
         out.println( "selectedRow = opener.document.getElementById(\"itemtype" + lineID + "\");\n" );
         out.println( "selectedRow.value = \"\";\n" );
         out.println( "}\n" );
		 out.println( "lineqty = opener.document.getElementById(\"lineqty" + lineID + "\");\n" );
		 out.println( "if (lineqty.value != \"0\")\n" );
		 out.println( "{\n" );
		 out.println( "lineqty.value = \"\";\n" );
         //out.println( "opener.invalidatespecsandflow(" + lineID + "); \n" );
         out.println( "selectedRow = opener.document.getElementById(\"itemtypecell" + lineID + "\");\n" );
         out.println( "selectedRow.innerHTML = \"\";\n" );
         out.println( "selectedRow = opener.document.getElementById(\"pakgingcell" + lineID + "\");\n" );
         out.println( "selectedRow.innerHTML = \"\";\n" );
         out.println( "selectedRow = opener.document.getElementById(\"itemdescdivrow" + lineID + "" + lineID + "\");\n" );
         out.println( "selectedRow.innerHTML = \"\";\n" );
         out.println( "lineunitprice = opener.document.getElementById(\"lineunitprice" + lineID + "\");\n" );
         out.println( "lineunitprice.value = \"\";\n" );
         out.println( "cell9 = opener.document.getElementById(\"extpricecell" + lineID + "\");\n" );
         out.println( "cell9.innerHTML = \"\";\n" );
		 out.println( "}\n");
         out.println( "linechange = opener.document.getElementById(\"linechange" + lineID + "\");\n" );
         out.println( "linechange.value = \"Yes\";\n" );
       }
       else if ( !SubuserAction.equalsIgnoreCase( "associatedPRs" ) )
       {
         if ( ( total > 0 ) )
         {
           /*out.println( "buttonlineitemid = opener.document.getElementById(\"buttonlineitemid" + lineID + "\");\n" );
           out.println( "buttonlineitemid.disabled=true;\n" );
           out.println( "buttonlineitemid.style.display=\"none\";\n" );*/

           out.println( "lineqty = opener.document.getElementById(\"lineqty" + lineID + "\");\n" );
           if ( totalitemQty < 0 )
           {
             totalitemQty=0;
           }
           out.println( "lineqty.value = \"" + totalitemQty + "\";\n" );
           out.println( "linechange = opener.document.getElementById(\"linechange" + lineID + "\");\n" );
           out.println( "linechange.value = \"Yes\";\n" );
           out.println( "dateneeded = opener.document.getElementById(\"dateneeded" + lineID + "\");\n" );
           out.println( "dateneeded.value = \"" + finalNeedDate + "\";\n" );
           out.println( "opener.changeTotalPrice(" + lineID + "); \n" );
           if ( hubNumber.equalsIgnoreCase( "2202" ) )
           {
             out.println( "var customerpo  =  opener.document.getElementById(\"customerpo\");\n" );
             out.println( "customerpo.value = \"" + clientpo + "\";\n" );
           }
         }

         String specItem="";
         String specShipto="";
         String specShiptoCompnyid="";

         if ( ItemID.trim().length() > 0 )
         {
           specItem=ItemID;
         }
         else
         {
           specItem=lineItemId;
         }
         if ( ! ( specItem.trim().length() > 0 ) )
         {
           specItem=lineitem1;
         }

         if ( shipTo.trim().length() > 0 )
         {
           specShipto=shipTo;
         }
         else
         {
           specShipto=shipTofromLine;
         }

         if ( mainshiptocompanyid.trim().length() > 0 )
         {
           specShiptoCompnyid=mainshiptocompanyid;
         }
         else
         {
           specShiptoCompnyid=lineshiptocompanyid;
         }

         if (newPoLine == 0)
         {
         radian.web.poHelpObj.buildsendSpecs( db,"yes",lineID,specItem,radianPO,"",specShipto,ammendmeant,specShiptoCompnyid,invengrp,"", out,true,res );
         out.println( "validspec = opener.document.getElementById(\"validspec" + lineID + "\");\n" );
         out.println( "validspec.value = \"Yes\";\n" );            	  
         radian.web.poHelpObj.buildsendFlowdowns( db,"yes",lineID,specItem,radianPO,"",specShipto,ammendmeant,specShiptoCompnyid,out,res );
         out.println( "validflowdown = opener.document.getElementById(\"validflowdown" + lineID + "\");\n" );
         out.println( "validflowdown.value = \"Yes\";\n" );
         }

         out.println( "searchsupplierlike = opener.document.getElementById(\"searchsupplierlike\");\n" );
         out.println( "if (searchsupplierlike.disabled) \n" );
         out.println( "{\n" );
         out.println( "}\n" );
         out.println( "else\n" );
         out.println( "{\n" );
         radian.web.poHelpObj.buildsendShipto( db,specShipto,specShiptoCompnyid,out );
         out.println( "}\n" );
         radian.web.poHelpObj.buildsendItemDetails( db,lineID,specItem,false,out );
         radian.web.poHelpObj.buildsenditemNotes( db,lineID,specItem,"yes",out,res ) ;
         //radian.web.poHelpObj.buildshowTcmBuys( db,lineID,specItem,"yes",specShipto,out,res ) ;
         boolean canupdate=false;
         try
         {
           canupdate=this.getupdateStatus();
         }
         catch ( Exception ex )
         {
         }
         //radian.web.poHelpObj.buildsendLookahead( db,"yes",lineID,specItem,radianPO,canupdate,out,res);
       }
     }

     out.println( "window.close();\n" );
     out.println( " }\n" );
     out.println( "// -->\n</SCRIPT>\n" );
     //out.println( Msgtemp );
     out.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	 out.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + bundle.getString( "BUY_ORDER" ) + "\">\n" );
     out.println( "<CENTER><BR><BR>\n" );
     out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
     out.println( "</FORM></BODY></HTML>\n" );
     //out.println( Msgbody );

     return;
   }

    private void buildAddPrsToPo( String radianPO,String hubNumber)
   {
     //StringBuffer Msgn=new StringBuffer();
     //StringBuffer Msgbody=new StringBuffer();
     String invengrp="";
     out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Supplier","",intcmIsApplication ) );
     //StringBuffer Msgtemp=new StringBuffer();

     //Build the Java Script Here and Finish the thing
     out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );

     out.println( "<!--\n" );
     out.println( "function sendSupplierData()\n" );
     out.println( "{\n" );
     //out.println( "opener.recalPo('" + radianPO + "','','po','" + hubNumber + "');\n" );
     out.println( "opener.getPo();\n" );  
     out.println( "window.close();\n" );
     out.println( " }\n" );
     out.println( "// -->\n</SCRIPT>\n" );
     //out.println( Msgtemp );
     out.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	 out.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + bundle.getString( "BUY_ORDER" ) + "\">\n" );
     out.println( "<CENTER><BR><BR>\n" );
     out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
     out.println( "</FORM></BODY></HTML>\n" );
     //out.println( Msgbody );

     return;
   }

   public void saveinitialSpecs( String itemID,String radianpo,String shipToLoc,String shiptocompanyid,String invengrp )
   {
     Connection connect1=null;
     CallableStatement cs=null;
     Vector Data=new Vector();
     Hashtable DataH=new Hashtable();
     ResultSet searchRs=null;
     DBResultSet dbrs=null;

     try
     {
       connect1=db.getConnection();
       cs=connect1.prepareCall( "{call pkg_po.Po_spec(?,?,?,?,?,?,?,?)}" );
       cs.setString( 1,shipToLoc );

       if ( shiptocompanyid.length() > 0 )
       {
         cs.setString( 2,shiptocompanyid );
       }
       else
       {
         cs.setNull( 2,OracleTypes.VARCHAR );
       }

       if ( itemID.length() > 0 )
       {
         cs.setInt( 3,Integer.parseInt( itemID ) );
       }
       else
       {
         cs.setNull( 3,OracleTypes.INTEGER );
       }

       cs.setBigDecimal( 4,new BigDecimal(""+ radianpo +"") );
       cs.setInt( 5,1000 );
       cs.setInt( 6,0 );
       if ( invengrp.length() > 0 )
       {
         cs.setString( 7,invengrp );
       }
       else
       {
         cs.setNull( 7,OracleTypes.VARCHAR );
       }

       cs.registerOutParameter( 8,OracleTypes.VARCHAR );
       cs.execute();
       String resultQuery=cs.getObject( 8 ).toString();
       dbrs=db.doQuery( resultQuery );
       searchRs=dbrs.getResultSet();

       while ( searchRs.next() )
       {
         DataH=new Hashtable();
         DataH.put( "SPEC_ID",searchRs.getString( "SPEC_ID" ) == null ? "" : searchRs.getString( "SPEC_ID" ) );
         DataH.put( "DETAIL",searchRs.getString( "DETAIL" ) == null ? "" : searchRs.getString( "DETAIL" ) );
         //DataH.put( "COMPANY_ID",searchRs.getString( "COMPANY_ID" ) == null ? "" : searchRs.getString( "COMPANY_ID" ) );
         //DataH.put( "SPEC_LIBRARY_DESC",searchRs.getString( "SPEC_LIBRARY_DESC" ) == null ? "" : searchRs.getString( "SPEC_LIBRARY_DESC" ) );
         //DataH.put( "CONTENT",searchRs.getString( "CONTENT" ) == null ? "" : searchRs.getString( "CONTENT" ) );
         //DataH.put( "ON_LINE",searchRs.getString( "ON_LINE" ) == null ? "" : searchRs.getString( "ON_LINE" ) );
         DataH.put( "CURRENT_COC_REQUIREMENT",searchRs.getString( "CURRENT_COC_REQUIREMENT" ) == null ? "" : searchRs.getString( "CURRENT_COC_REQUIREMENT" ) );
         DataH.put( "CURRENT_COA_REQUIREMENT",searchRs.getString( "CURRENT_COA_REQUIREMENT" ) == null ? "" : searchRs.getString( "CURRENT_COA_REQUIREMENT" ) );
         //DataH.put( "ATTACH",searchRs.getString( "ATTACH" ) == null ? "" : searchRs.getString( "ATTACH" ) );
         //DataH.put( "OK",searchRs.getString( "OK" ) == null ? "" : searchRs.getString( "OK" ) );
         //DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
         DataH.put( "SPEC_LIBRARY",searchRs.getString( "SPEC_LIBRARY" ) == null ? "" : searchRs.getString( "SPEC_LIBRARY" ) );
         DataH.put( "COLOR",searchRs.getString( "COLOR" ) == null ? "" : searchRs.getString( "COLOR" ) );
				 //DataH.put( "DETAIL",searchRs.getString( "DETAIL" ) == null ? "" : searchRs.getString( "DETAIL" ) );
				 //DataH.put( "CERT_REQUIRED_CURRENT",searchRs.getString( "CERT_REQUIRED_CURRENT" ) == null ? "" : searchRs.getString( "CERT_REQUIRED_CURRENT" ) );
				 //DataH.put( "STATUS",searchRs.getString( "STATUS" ) == null ? "" : searchRs.getString( "STATUS" ) );

         Data.addElement( DataH );
       }
       Data.trimToSize();
     }
     catch ( Exception e )
     {
       //e.printStackTrace();
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
       if ( dbrs != null ) {dbrs.close();}
     }

     int i=0;
     for ( int loop=0; loop < Data.size(); loop++ )
     {
       Hashtable countData=new Hashtable();
       countData= ( Hashtable ) Data.elementAt( i );

       String spec_id=countData.get( "SPEC_ID" ).toString().trim();
       //String detail=countData.get( "DETAIL" ).toString();
       //String company_id=countData.get( "COMPANY_ID" ).toString().trim();
       //String spec_library_desc=countData.get( "SPEC_LIBRARY_DESC" ).toString().trim();
       //String content=countData.get( "CONTENT" ).toString();
       //String on_line=countData.get( "ON_LINE" ).toString();
       //String coc=countData.get( "COC" ).toString();
       //String coa=countData.get( "COA" ).toString();
       //String attach=countData.get( "ATTACH" ).toString();
       //String ok=countData.get( "OK" ).toString();
       //String item_id=countData.get( "ITEM_ID" ).toString();
       String spec_library=countData.get( "SPEC_LIBRARY" ).toString();
       String color=countData.get( "COLOR" ).toString();
			 String spec_detail = countData.get( "DETAIL" ).toString();
			 String currentCocRequirement= countData.get( "CURRENT_COC_REQUIREMENT" ).toString();
			 String currentCoaRequirement=countData.get( "CURRENT_COA_REQUIREMENT" ).toString();

	   //String certreq = countData.get( "CERT_REQUIRED_CURRENT" ).toString();
	   //String status = countData.get( "STATUS" ).toString();

       if ( ( "1".equalsIgnoreCase( color ) || "2".equalsIgnoreCase( color ) ) && ("Y".equalsIgnoreCase(currentCocRequirement) || "Y".equalsIgnoreCase(currentCoaRequirement)) )
       {
         try
         {
           String insertspecs="insert into po_line_spec_draft (SPEC_ID,SPEC_LIBRARY,RADIAN_PO,PO_LINE,COC,COA,AMENDMENT,DETAIL,COA_REQ,COC_REQ,COLOR) values \n";
           insertspecs+="('" + spec_id + "','" + spec_library + "'," + radianpo + ",1000,'"+currentCocRequirement+"','"+currentCoaRequirement+"',0,'"+spec_detail+"','"+currentCoaRequirement+"','"+currentCocRequirement+"',"+color+") \n";

           db.doUpdate( insertspecs );
         }
         catch ( Exception e )
         {
           buylog.info( "error inserting into po_line_spec_draft PO " + radianpo + "");
           //e.printStackTrace();
         }
       }
       i++;
     }
   }

   private void buildEditAssociatedPRS( Vector data,HttpSession session,String lineID,String radianPO,String ItemID,
                                                String Hubname,String shipTo,String numofHubs,String sortby,
                                                String searchCompany,String searchmr,String searchcustomerpo,String bbuyer,String ammendment,
                                                String invengrpfrompo,String attachedprs,String selsuppath,String userAction,String currencyId,String searchLike,String searchthis )
   {
     //StringBuffer Msgn=new StringBuffer();
     //StringBuffer Msgbody=new StringBuffer();
     ItemID=ItemID.trim();
     shipTo=shipTo.trim();
     Hubname=Hubname.trim();

     out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Requests","purchasereq.js",intcmIsApplication ) );
     out.println( "<SCRIPT SRC=\"/clientscripts/associatedPrs.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
     out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );
     out.println( "</HEAD>  \n" );
     out.println( "<BODY>\n" );
     out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Edit PRs Associated with PO: " + radianPO + "" ) );
     out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
     out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
     out.println( "</DIV>\n" );
     out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
     out.println( "<FORM method=\"POST\" NAME=\"purchasereq\" onSubmit=\"return SubmitOnlyOnce('"+userAction+"')\" ACTION=\"" + bundle.getString( "BUY_ORDER" ) + "\">\n" );

     //start table #1
     out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
     out.println( "<TR>\n" );

     //Customer PO
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
     out.println( "<B>Customer PO:</B>&nbsp;\n" );
     out.println( "</TD>\n" );
     out.println( "<TD WIDTH=\"5%\">\n" );
     out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" ID=\"customerpo\" NAME=\"customerpo\" value=\"" + searchcustomerpo + "\" size=\"10\">\n" );
     out.println( "</TD>\n" );

     //Company
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
     out.println( "<B>Company:</B>&nbsp;\n" );
     out.println( "</TD>\n" );

     out.println( "<TD WIDTH=\"5%\">\n" );
     out.println( "<SELECT CLASS=\"HEADER\" ID=\"companyID\" NAME=\"companyID\" size=\"1\">\n" );
     out.println( "<OPTION VALUE=\"All\">Any Company</OPTION>\n" );
     out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "PO_COMPANY_IDS" ),searchCompany ) );
     out.println( "</SELECT>\n" );
     out.println( "</TD>\n" );

     //Buyer
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
     out.println( "<B>Buyer:</B>&nbsp;\n" );
     out.println( "</TD>\n" );
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\">\n" );
     out.println( "<SELECT CLASS=\"HEADER\" ID=\"buyer\" NAME=\"buyer\" size=\"1\">\n" );
     out.println( "<OPTION VALUE=\"All\">Any Buyer</OPTION>\n" );
     out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "PO_BUYERNAMES" ),bbuyer ) );
     out.println( "</SELECT>\n" );
     out.println( "</TD>\n" );

     //Search
	 out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	 out.println( "&nbsp;\n" );
	 out.println( "</TD>\n" );
     out.println( "<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\">\n" );
     out.println( "<INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return editPRsearch(this,'"+userAction+"')\" ID=\"SearchButton\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
     out.println( "</TD>\n" );
     out.println( "</TR>\n" );
     out.println( "<TR>\n" );
     //Ship TO
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
     out.println( "<B>Ship TO:</B>&nbsp;\n" );
     out.println( "</TD>\n" );
     out.println( "<TD WIDTH=\"5%\">\n" );
     out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" ID=\"shipTo\" NAME=\"shipTo\" value=\"" + shipTo + "\" size=\"10\">\n" );
     out.println( "</TD>\n" );

     // searchmr
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
     out.println( "<B>MR:</B>&nbsp;\n" );
     out.println( "</TD>\n" );
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\">\n" );
     out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" ID=\"searchmr\" NAME=\"searchmr\" value=\"" + searchmr + "\" size=\"10\">\n" );
     out.println( "</TD>\n" );

     //Sort
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
     out.println( "<B>Sort:</B>&nbsp;\n" );
     out.println( "</TD>\n" );
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\">\n" );
     out.println( "<SELECT CLASS=\"HEADER\" ID=\"SortBy\" NAME=\"SortBy\" size=\"1\">\n" );
     //out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "PO_SORTIT" ),sortby ) );
     Vector dataV =  ( Vector ) session.getAttribute( "PO_SORTIT" );
     for ( int i=0; i < dataV.size(); i++ )
     {
    Hashtable data1= ( Hashtable ) dataV.elementAt( i );
    Enumeration E;
    for ( E=data1.keys(); E.hasMoreElements(); )
    {
      String key= ( String ) E.nextElement();
      String keyvalue=data1.get( key ).toString();
			keyvalue = BothHelpObjs.makeBlankFromNull( keyvalue );
			String selected="";
			if (sortby.equalsIgnoreCase( keyvalue )){ selected = "selected"; }

      if (!keyvalue.equalsIgnoreCase("PREFERRED_SUPPLIER_NAME"))
      {
      out.println( "<OPTION " + selected + " VALUE=\"" + HelpObjs.addescapesForJavascript( keyvalue ) + "\">" + key + "</OPTION>\n" );
      }
    }
  }
     out.println( "</SELECT>\n" );
     out.println( "</TD>\n" );

	 //Supply Path
	 out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	 out.println( "<B>Supply Path:</B>&nbsp;\n" );
     out.println( "</TD>\n" );
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	 out.println( "<SELECT CLASS=\"HEADER\" ID=\"headsupplypath\" NAME=\"headsupplypath\" size=\"1\">\n" );
	 if (selsuppath.length() == 0 ) {selsuppath = "All";}
	 Hashtable supplypath=new Hashtable();
	 supplypath.put( "All","All" );
	 supplypath.put( "Dbuy","Dbuy" );
	 supplypath.put( "Wbuy","Wbuy" );
     supplypath.put( "Ibuy","Ibuy" );
     supplypath.put( "Purchasing","Purchaser" );
	 //supplypath.put( "Repackage","Repackage" );

	 out.println( radian.web.HTMLHelpObj.getDropDown( supplypath,selsuppath ) );
	 out.println( "</SELECT>\n" );

     out.println( "</TD>\n" );

     out.println( "</TR>\n" );


     out.println( "<TR>\n" );
//Search
       out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<B>Search:</B>&nbsp;&nbsp;\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"10%\">\n" );
       out.println( "<SELECT CLASS=\"HEADER\" ID=\"searchlike\" NAME=\"searchlike\" size=\"1\">\n" );
       Hashtable sortresult=new Hashtable();
       sortresult.put( "Customer PO","RAYTHEON_PO" );
       if (userAction.equalsIgnoreCase("addBuyOrdersToPO"))
     {
       sortresult.put( "Item Id","ITEM_ID" );
     }
       sortresult.put( "Last Supplier","LAST_SUPPLIER" );
	   sortresult.put( "Manufacturer","MFG_ID" );
	   sortresult.put( "MR","MR_NUMBER" );
	   sortresult.put( "Part Number","PART_ID" );
	   //sortresult.put( "PO","RADIAN_PO" );
	   sortresult.put( "PR Number","PR_NUMBER" );
	   sortresult.put( "Type","ITEM_TYPE" );

       out.println( radian.web.HTMLHelpObj.getDropDown( sortresult,searchLike ) );
       out.println( "</SELECT>\n" );
       out.println( "</TD>\n" );

     out.println( "<TD WIDTH=\"25%\" ALIGN=\"LEFT\" COLSPAN=\"5\">&nbsp;&nbsp;<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" type=\"text\" id=\"searchthis\" name=\"searchthis\" value=\"" +  searchthis + "\" SIZE=\"25\"></TD>\n" );
     out.println( "</TR>\n" );

     out.println( "<TR>\n" );
     out.println( "<TD align=\"center\" colspan=\"8\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"okupdate\" name=\"okupdate\" value=\"Ok\" OnClick=\"submitUpdate('"+userAction+"')\" type=\"submit\">\n" );
     out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></TD>\n" );
     out.println( "</TR>\n" );
     out.println( "</TABLE>\n" );

     out.println( "<INPUT TYPE=\"hidden\" ID=\"UserAction\" NAME=\"UserAction\" VALUE=\"\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"SubUserAction\" NAME=\"SubUserAction\" VALUE=\"NEW\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"radianPO\" NAME=\"radianPO\" VALUE=\"" + radianPO + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"lineID\" NAME=\"lineID\" VALUE=\"" + lineID + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"HubName\" NAME=\"HubName\" VALUE=\"" + Hubname + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"itemnum\" NAME=\"itemnum\" VALUE=\"" + ItemID + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"currencyId\" NAME=\"currencyId\" id=\"currencyId\" VALUE=\"" + currencyId + "\">\n" );       
     out.println( "<INPUT TYPE=\"hidden\" ID=\"numofHubs\" NAME=\"numofHubs\" VALUE=\"" + numofHubs + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"ammendment\" NAME=\"ammendment\" VALUE=\"" + ammendment + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"itemfromLine\" NAME=\"itemfromLine\" VALUE=\"\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"shipTofromLine\" NAME=\"shipTofromLine\" VALUE=\"\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"shipToCompanyfromLine\" NAME=\"shipToCompanyfromLine\" VALUE=\"\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"thedecidingRandomNumber\" NAME=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"invengrp\" NAME=\"invengrp\" VALUE=\"" + invengrpfrompo + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"attachedpr\" NAME=\"attachedpr\" VALUE=\"" + attachedprs + "\">\n" );

     try
     {
       Hashtable summary=new Hashtable();
       summary= ( Hashtable ) data.elementAt( 0 );
       int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
	   int attchprcount = 0;
	   String everconfirmed = "";
       out.println( "<INPUT TYPE=\"hidden\" NAME=\"totalRecords\" ID=\"totalRecords\" VALUE=\"" + total + "\">\n" );
       out.println( "<B>" + total + "  Records Found.</B>\n" );
       //open scrolling table
       out.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
       out.println( "<TBODY>\n" );
       out.println( "<TR>\n" );
       out.println( "<TD VALIGN=\"TOP\" COLSPAN=\"17\">\n" );
       out.println( "<DIV ID=\"orderdetail\" CLASS=\"newscroll_column550\">\n" );
       out.println( "<TABLE ID=\"editassociatedprtable" + lineID + "\"BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
       if ( total == 0 )
       {
         out.println( "<TR>\n" );
         out.println( "<TD CLASS=\"Inwhite\" WIDTH=\"5%\" COLSPAN=\"18\">No Records Found</TD>\n" );
         out.println( "</TR>\n" );
       }
       else
       {
         int i=0;
         for ( int loop=0; loop < total; loop++ )
         {
           i++;

           boolean createHdr=false;

           if ( loop % 10 == 0 )
           {
             createHdr=true;
           }

           if ( createHdr )
           {
             out.println( "<TR>\n" );
             out.println( "<TH WIDTH=\"2%\"  HEIGHT=\"25\">OK</TH>\n" );
             out.println( "<TH WIDTH=\"8%\"  HEIGHT=\"25\">PR<BR>Created</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Status</TH>\n" );
             out.println( "<TH WIDTH=\"8%\"  HEIGHT=\"25\">Ship To</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Facility</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Buyer</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Item</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Item Desc</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Type</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Buy Qty</TH>\n" );
			 out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">UOM</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Needed</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">MR-Line</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">MR Qty</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Part Number</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Specs</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Purchasing Note</TH>\n" );
             out.println( "<TH width=\"7%\"  height=\"38\">Manufacturer</TH>\n" );
             out.println( "<TH width=\"7%\"  height=\"38\">Last Supplier</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Hub<BR>(Inven Grp)</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Company</TH>\n" );
             out.println( "<TH WIDTH=\"8%\"  HEIGHT=\"25\">Client PO</TH>\n" );
             out.println( "<TH WIDTH=\"10%\"  HEIGHT=\"25\">Notes</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">MM</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Reorder Point</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Inventory</TH>\n" );
             out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">Open PO Qty</TH>\n" );
             out.println( "</TR>\n" );
           }

           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

           // get main information
           String branchplant=hD.get( "BRANCH_PLANT" ).toString();
           String facility=hD.get( "FACILITY" ).toString();
           String prnumber1=hD.get( "PR_NUMBER" ).toString().trim();
           String lineitem1=hD.get( "MR_LINE_ITEM" ).toString().trim();
           String mrnumber=hD.get( "MR_NUMBER" ).toString();
           String needdate=hD.get( "NEED_DATE" ).toString();
           String qty=hD.get( "ORDER_QUANTITY" ).toString();
           String clientpo=hD.get( "RAYTHEON_PO" ).toString();
           String datecreated=hD.get( "DATE_ISSUED" ).toString();
           String status=hD.get( "STATUS" ).toString();
           //String radianpo=hD.get( "RADIAN_PO" ).toString();
           String itemid=hD.get( "ITEM_ID" ).toString();
           String part=hD.get( "PART_ID" ).toString();
           //String manufacturer=hD.get( "MFG_ID" ).toString();
           String companyid=hD.get( "COMPANY_ID" ).toString();
           //String itemdesc=hD.get( "ITEM_DESC" ).toString();
           String priority=hD.get( "PRIORITY" ).toString();
           String itemtype=hD.get( "ITEM_TYPE" ).toString();
           String shiptoId=hD.get( "SHIP_TO_LOCATION_ID" ).toString();
           //String buyerId=hD.get( "BUYER_ID" ).toString();
           String buyer=hD.get( "BUYER_NAME" ).toString();
           String reorderpoint=hD.get( "REORDER_POINT" ).toString();
           String stockinglevel=hD.get( "STOCKING_LEVEL" ).toString();
           String inventory=hD.get( "AVAILABLE_QUANTITY" ).toString();
           String openpoqty=hD.get( "OPEN_PO_QUANTITY" ).toString();
           String prNotes=hD.get( "PRNOTES" ).toString();
           String deliverytype=hD.get( "DELIVERY_TYPE" ).toString();
           String prassociated=hD.get( "PRASSOCIATED" ).toString();
           String frozenpr=hD.get( "FROZEN" ).toString();
           String critical=hD.get( "CRITICAL" ).toString();
           String engevaluation=hD.get( "ENGINEERING_EVALUATION" ).toString();
           String stocekd=hD.get( "STOCKED" ).toString();
           String invengrp=hD.get( "INVENTORY_GROUP" ).toString();
					 everconfirmed=hD.get( "EVER_CONFIRMED" ).toString();
					 String packaging=hD.get( "SIZE_UNIT" ).toString();
           String supplyPath=hD.get( "SUPPLY_PATH" ).toString();
           String lastsuppliername=hD.get( "LAST_SUPPLIER" ).toString();
           String itemdesc=hD.get( "ITEM_DESC" ).toString();
           String manufacturer=hD.get( "MFG_ID" ).toString();
           String purchasingNote=hD.get( "PURCHASING_NOTE" ).toString();
           String specList=hD.get( "SPEC_LIST" ).toString();

           String checked="";
           if ( "Yes".equalsIgnoreCase( prassociated ) )
           {
						checked=" checked";
						attchprcount++;
           }

           String mrQuantity=hD.get( "MR_QUANTITY" ).toString();

           String Color=" ";
           if ( loop % 2 == 0 )
           {
             Color="CLASS=\"Inwhite";
           }
           else
           {
             Color="CLASS=\"Inblue";
           }

           if ( "1".equalsIgnoreCase( priority ) )
           {
             Color="CLASS=\"red";
           }
           if ( "2".equalsIgnoreCase( priority ) )
           {
             Color="CLASS=\"yellow";
           }
           if ( "3".equalsIgnoreCase( priority ) )
           {
             Color="CLASS=\"green";
           }

           if ( "OOR".equalsIgnoreCase( itemtype ) && "Y".equalsIgnoreCase( critical ) )
           {
             Color="CLASS=\"red";
           }
           else if ( "MM".equalsIgnoreCase( itemtype ) && "Y".equalsIgnoreCase( critical ) )
           {
             Color="CLASS=\"red";
           }

		   if ( "S".equalsIgnoreCase( critical ) )
		   {
			 Color="CLASS=\"pink";
		   }

           if ( "y".equalsIgnoreCase( engevaluation ) )
           {
             Color="CLASS=\"purple";
           }

           out.println( "<TR ALIGN=\"center\">\n" );

		   boolean canupdateline=false;
		   Vector lockstatusV= ( Vector ) session.getAttribute( "PO_LOCK_STATUS" );

		   if ( this.getupdateStatus() )
		   {
			 canupdateline=true;
			 if ( lockstatusV.contains( status ) || "Y".equalsIgnoreCase(frozenpr))
			 {
			   canupdateline=false;
			 }
		   }

           if ( "Y".equalsIgnoreCase( frozenpr ) || !canupdateline)
           {
             out.println( "<TD " + Color + "l\" WIDTH=\"2%\"><INPUT TYPE=\"checkbox\" STYLE=\"display:none\" value=\"Yes\" NAME=\"" + i + "\" ID=\"" + i + "\"></TD>\n" );
           }
           else
           {
             out.println( "<TD " + Color + "l\" WIDTH=\"2%\"><INPUT TYPE=\"checkbox\" value=\"Yes\" NAME=\"" + i + "\" ID=\"" + i + "\" " + checked + "  onClick=\"checkEditPrsValues(name,this,'"+userAction+"')\"></TD>\n" );
           }

           out.println( "<TD " + Color + "\" WIDTH=\"8%\">" + prnumber1 + "<BR>(" + datecreated + ")</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + status + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"8%\">" + shiptoId + "<INPUT TYPE=\"hidden\" NAME=\"facility" + i + "\" ID=\"facility\" value=\"" + shiptoId + "\"></TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + facility + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + buyer + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + itemid + "\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + itemdesc + "\n" );

           if ( "Y".equalsIgnoreCase( stocekd ) )
           {
             out.println( "<BR>(Stocked)\n" );
           }
           out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemid" + i + "\" ID=\"itemid" + i + "\" value=\"" + itemid + "\"></TD>\n" );
           out.println( "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + itemtype + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + qty + "</TD>\n" );
					 out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + packaging + "</TD>\n" );

           if ( "Y".equalsIgnoreCase( deliverytype ) )
           {
             out.println( "<td " + Color + "\" width=\"5%\">" + needdate + "<BR><A HREF=\"javascript:showschedulde('" + mrnumber + "','" + lineitem1 +  "')\">Schedule</A></td>\n" );
           }
           else
           {
             out.println( "<td " + Color + "\" width=\"5%\">" + needdate + "</td>\n" );
           }

           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + mrnumber + "-" + lineitem1 + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + mrQuantity + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + part + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + specList + "</TD>\n" );                                  
           out.println( "<TD " + Color + "\" WIDTH=\"10%\">" + purchasingNote + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + manufacturer + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + lastsuppliername + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + branchplant + "<BR>(" + invengrp + ")<INPUT TYPE=\"hidden\" NAME=\"branchplant" + i + "\" ID=\"branchplant" + i + "\" value=\"" + branchplant + "\"><INPUT TYPE=\"hidden\" NAME=\"invengrp" + i +  "\" ID=\"invengrp\" value=\"" + invengrp + "\"></TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">\n");
           if (companyid.equalsIgnoreCase("Radian"))
           {
            out.println("Haas TCM");
           }
           else
           {
            out.println(companyid);
           }
           out.println("<INPUT TYPE=\"hidden\" NAME=\"companyid" + i + "\" ID=\"companyid" + i + "\" value=\"" + companyid + "\"></TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"8%\">" + clientpo + "<INPUT TYPE=\"hidden\" NAME=\"customerPo" + i + "\" ID=\"customerPo\" value=\"" + clientpo + "\"></TD>\n" );
           out.println( "<TD " + Color + "l\" WIDTH=\"10%\">" + prNotes + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + stockinglevel + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + reorderpoint + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + inventory + "</TD>\n" );
           out.println( "<TD " + Color + "\" WIDTH=\"5%\">" + openpoqty + "</TD>\n" );
           out.println( "</TR>\n" );
         }
       }
       out.println( "</TABLE>\n" );
       out.println( "<INPUT TYPE=\"hidden\" ID=\"attchprcount\" NAME=\"attchprcount\" VALUE=\"" + attchprcount + "\">\n" );
	   out.println( "<INPUT TYPE=\"hidden\" ID=\"everconfirmed\" NAME=\"everconfirmed\" VALUE=\"" + everconfirmed + "\">\n" );
       out.println( "</DIV>\n" );
       out.println( "</TD>\n" );
       out.println( "</TR>\n" );
       out.println( "</TBODY>\n" );
       out.println( "</TABLE>\n" );
     }
     catch ( Exception e )
     {
       //e.printStackTrace();
       out.println( radian.web.HTMLHelpObj.printError( "Purchase Requests",intcmIsApplication ) );
     }

     out.println( "</FORM></BODY></HTML>\n" );
     //Msgn.append( Msgbody );

     return;
   }
}