package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
//import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;

import java.util.*;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-01-02
 * Made changes to make line_note length 4000 for blanket Po
 *
 * 11-04-02
 * The Save procedure is called for even lines which are unchaged when confirming a PO is because they might have changed the
 * line and saved it previously and are confirming it now
 *
 * Calling the save procedure only if the line is Changed or the Status is Unconfirmed
 *
 * 7:00PM replaced dbrs.close with if (dbrs != null)  { dbrs.close(); }
 *
 * changed procedure updateLineAddCharges
 *
 * 11-12-02
 * if the linestatus is remove send back result true if item_id orqty is null
 * 11-13-02
 * Adding the critical option to the po and purchaser notes to customer on po line
 * 11-15-02
 * if PR_DELIVERY_DELAY_NOTIFY_LINE fails not saying that an error has occured for the whole po
 *
 * 11-19-02
 * aDDING LOOK AHEADS
 * Right now they can onl update numeric values to look ahead days. they can not update it to null
 *
 * 01-13-02
 * Adding a new column to the PO tabels
 *
 *01-19-02
 *in updatePoLine promiseddate Projected Delivery date is and projdeliverydate Promised Ship Date is
 *but they are saved different in databse promiseddate is VENDOR_SHIP_DATE and projdeliverydate is PROMISED_DATE
 *
 * 02-10-03
 * only people in user group paymentterms can change the payment terms
 * call p_cpi_rli_allocate when lookahead is changed
 *
 * 02-25-03
 * Supplier Qty can be Decimal
 *
 * 03-10-03
 * Rounding Unit_price to six digits. Update lookahead only if itis changed
 * 04-28-03
 * Showing only vv_payment_terms which are active
 * 05-06-03 Added supplier qualifying level to the search and to the save
 * 05-20-03 Shwoing the qty returned
 * 07-07-03 Storing the hubs witha different name as it is causeing confilicts when people loginto a deifferent page
 * 07-22-03 Code cleanup and removed the redundant passing of message to recal PO
 * 08-15-03 - Sending error emails
 * 09-15-03 - Showing the Address line 3 of the ship to address
 * 10-15-03 - Sending me an emial when there is an error on commit
 * 10-20-03 - Added Consigned PO to the screen and the option to choose it 'Y' or 'N'
 * 11-21-03 - Showing the scratch pad and email pad for the read only version
 * 12-05-03 - Changes to make Blanket Orders work
 * 12-16-03 - Super Spec changes. Not Saving the certreq when saving. Only update it when it is confirmed.
 * 12-22-03 - Making the session Keys Unique to PO pages. And Code Refactoring moved vv_stuff to different class
 * 01-09-04 - Converting %26 to &. Commiting stuff whihc was not commited.
 * 01-30-04 - Showing only Active Payment Terms for PO pages and showing all for AP pages
 * 02-16-04 - Changing the Table Heading for Blanket Orders
 * 03-15-04 - Made changes to accomodate DBuy requests, and to handle delete lines request
 * 03-17-04 - Showing email address of supplier and supplier contact. Making the PO readonly if it is in a lock status
 * 03-30-04 - Taking care of shiptos with # in their name
 * 04-06-04 - Not Storing Carriage Return in the database as <BR> and not trucating comments to the size of the field in the database. Also getting vv vector for statuses that are not assignable from the buy page
 * 07-01-04 - Changing Printer Path to be based on personnel ID
 * 07-21-04 - Not setting LOOK_AHEAD_DAYS to 0
 * 12-28-05 - Making sure the line qty is > 0 no -ve Qty allowed
 * 01-20-06 - Changed all Float to use BigDecimal
 * 09-06-06 - Making changes to allwo decimal qty for qty ordered
 */

public class purchaseOrderCreator
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private Vector addchargeslist=null;
  private boolean completeSuccess=true;
  private boolean allowupdate;
  private boolean allowPaymenttermsupdate;
  private boolean allowPOCreditConfirmaiton = true;
  private boolean allowCurrencyupdate;
  private String errorFromProcedure="";
  private static org.apache.log4j.Logger polog = org.apache.log4j.Logger.getLogger(purchaseOrderCreator.class);
  private boolean intcmIsApplication = false;
  private ResourceLibrary res = null; // res means resource.
  private String allowPOFinancialConfirmaiton = null;
  private boolean outsideUSAFYAudit = false;
  private boolean readonly=false;
  private boolean nopermissions=false;

  public purchaseOrderCreator( ServerResourceBundle b,TcmISDBModel d )
  {
	bundle=b;
	db=d;
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

  public void setPaytermsupdateStatus( boolean id1 )
  {
	this.allowPaymenttermsupdate=id1;
  }

  private boolean getPaytermsupdateStatus()
	 throws Exception
  {
	return this.allowPaymenttermsupdate;
  }

  public void setallowCurrencyupdate( boolean id1 )
	{
	  this.allowCurrencyupdate=id1;
	}

	private boolean getallowCurrencyupdate()
	   throws Exception
	{
	  return this.allowCurrencyupdate;
	}

   public void setAllowPOCreditConfirmaiton( boolean id1 )
  {
	this.allowPOCreditConfirmaiton=id1;
  }

  private boolean getAllowPOCreditConfirmaiton()
	 throws Exception
  {
	return this.allowPOCreditConfirmaiton;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession posession,PrintWriter out )
	 throws ServletException,IOException
  {
	//PrintWriter out=response.getWriter();
  //PrintWriter out = new PrintWriter ( new OutputStreamWriter(response.getOutputStream (), "UTF8" ) );
  //response.setContentType( "text/html; charset=UTF-8" );
  //request.setCharacterEncoding("UTF-8");

        PersonnelBean personnelBean = (PersonnelBean) posession.getAttribute("personnelBean");
        outsideUSAFYAudit = (!"USA".equalsIgnoreCase(personnelBean.getCountryAbbrev()) ? true:false) ;
        res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)posession.getAttribute("tcmISLocale"));

        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

	String branch_plant=posession.getAttribute( "BRANCH_PLANT" ) == null ? "" : posession.getAttribute( "BRANCH_PLANT" ).toString();
	String personnelid=posession.getAttribute( "PERSONNELID" ) == null ? "" : posession.getAttribute( "PERSONNELID" ).toString();
	String User_Action=null;
	String subUserAction="";

	User_Action=request.getParameter( "Action" );
	if ( User_Action == null )
	{
	  User_Action="New";
	}
	if ( User_Action.length() == 0 )
	{
	  User_Action="New";
	}
	subUserAction=request.getParameter( "subUserAction" );
	if ( subUserAction == null )
	{
	  subUserAction="";
	}
	String radianPo=request.getParameter( "po" );
	if ( radianPo == null )
	{
	  radianPo="";
	}
	String validPo=request.getParameter( "validpo" );
	if ( validPo == null )
	{
	  validPo="";
	}
	String blanketPO=request.getParameter( "bpo" );
	if ( blanketPO == null )
	{
	  blanketPO="";
	}
	String validbPo=request.getParameter( "validbpo" );
	if ( validbPo == null )
	{
	  validbPo="";
	}
	String HubName=request.getParameter( "HubName" );
	if ( HubName == null )
	{
	  HubName="";
	}

	try
	{
	  //Hashtable hub_list_set = new Hashtable();
	  //hub_list_set= (Hashtable)posession.getAttribute("HUB_PREF_LIST");

	  String donevvstuff=posession.getAttribute( "VVSUPPLYSTUFF" ) == null ? "" : posession.getAttribute( "VVSUPPLYSTUFF" ).toString();
	  if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
	  {
		Hashtable hub_list_set=new Hashtable();
		hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,"purch" );
		posession.setAttribute( "PO_HUB_SET",hub_list_set );
		posession.setAttribute( "PO_CATEGORY",radian.web.vvHelpObj.getcategory(db) );
		posession.setAttribute( "PO_COMPANY_IDS",radian.web.vvHelpObj.getCompanyIds(db) );
		posession.setAttribute( "PO_BUYERNAMES",radian.web.HTMLHelpObj.getbuyernames( db ) );
		posession.setAttribute( "PO_STATUS",radian.web.vvHelpObj.getorderstatus(db,"") );
		posession.setAttribute( "PO_NON_ASGN_STATUS",radian.web.vvHelpObj.nonassignablestatuses(db) );
		posession.setAttribute( "PO_LOCK_STATUS",radian.web.vvHelpObj.getorderstatus(db,"Y") );
		posession.setAttribute( "PO_SET_STATUS",radian.web.vvHelpObj.getorderstatus(db,"setonly") );
		posession.setAttribute( "PO_SORTIT",radian.web.vvHelpObj.getsortby(db) );
		posession.setAttribute( "PO_VV_FOB",radian.web.vvHelpObj.getfob(db) );
		posession.setAttribute( "PO_ADD_CHARGE_ITEM_TYPE",radian.web.vvHelpObj.getaddchargeType(db) );
		posession.setAttribute( "PO_ADD_SECONDARY_SUPPLIER_ITEM_TYPE",radian.web.vvHelpObj.getSecondarySupplierTypes(db) );
		posession.setAttribute( "PO_VV_PAYMENT",radian.web.vvHelpObj.getPaymentTerms(db,false) );
		posession.setAttribute( "PO_CONTACTTYPE",radian.web.vvHelpObj.getvvContectType(db) );

		Vector hubcompanyFac=radian.web.vvHelpObj.gethubcompanyFacilities(db);
		Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		String companyArrayJs = "";
		if ( hub_list.size() > 0 )
		{
		  companyArrayJs=radian.web.HTMLHelpObj.getHubCompanyJs( hub_list_set,hubcompanyFac,posession ).toString();
		}
		posession.setAttribute( "PO_CATALOGS_JS",companyArrayJs );
		posession.setAttribute( "PO_HUBCOMPFACS",hubcompanyFac );
		posession.setAttribute( "VVSUPPLYSTUFF","Yes" );
	  }
	  
		if(outsideUSAFYAudit || personnelBean.getPermissionBean().hasFacilityPermission( "POConfirmationApprover","All","Radian" ))
			allowPOFinancialConfirmaiton = "Y";
		else
			allowPOFinancialConfirmaiton = "N";

	  if ( User_Action.equalsIgnoreCase( "New" ) )
	  {
		Hashtable hub_list_set=new Hashtable();
		Vector buyernames=new Vector();
		Hashtable vvFob=new Hashtable();
		Vector vvPayment=new Vector();
		Vector vvCurrency=new Vector();
		Vector compnayids=new Vector();

		donevvstuff=posession.getAttribute( "VVSUPPLYSTUFF" ) == null ? "" : posession.getAttribute( "VVSUPPLYSTUFF" ).toString();

		if ( donevvstuff.equalsIgnoreCase( "Yes" ) )
		{
		  hub_list_set= ( Hashtable ) posession.getAttribute( "PO_HUB_SET" );
		  addchargeslist= ( Vector ) posession.getAttribute( "PO_ADD_CHARGE_ITEM_TYPE" );
		  buyernames= ( Vector ) posession.getAttribute( "PO_BUYERNAMES" );
		  vvFob= ( Hashtable ) posession.getAttribute( "PO_VV_FOB" );
		  vvPayment= ( Vector ) posession.getAttribute( "PO_VV_PAYMENT" );
		  vvCurrency= ( Vector ) posession.getAttribute( "VV_CURRENCY" );
		}
		else
		{
		  hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,"purch" );
		  buyernames=new Vector();
		  buyernames=radian.web.HTMLHelpObj.getbuyernames( db );
		  posession.setAttribute( "PO_HUB_SET",hub_list_set );
		  posession.setAttribute( "PO_BUYERNAMES",buyernames );
		  vvFob=radian.web.vvHelpObj.getfob(db);
		  posession.setAttribute( "PO_VV_FOB",vvFob );
		  posession.setAttribute( "PO_ADD_CHARGE_ITEM_TYPE",radian.web.vvHelpObj.getaddchargeType(db) );
		  posession.setAttribute( "PO_ADD_SECONDARY_SUPPLIER_ITEM_TYPE",radian.web.vvHelpObj.getSecondarySupplierTypes(db) );
		  vvPayment=radian.web.vvHelpObj.getPaymentTerms(db,false);
		  posession.setAttribute( "PO_VV_PAYMENT",vvPayment );
		  posession.setAttribute( "PO_CONTACTTYPE",radian.web.vvHelpObj.getvvContectType(db) );
		  compnayids=radian.web.vvHelpObj.getCompanyIds(db);
		  posession.setAttribute( "PO_COMPANY_IDS",compnayids );
		  posession.setAttribute( "PO_BUYERNAMES",radian.web.HTMLHelpObj.getbuyernames( db ) );
		  posession.setAttribute( "PO_SORTIT",radian.web.vvHelpObj.getsortby(db) );
		  posession.setAttribute( "VVSUPPLYSTUFF","Yes" );

		  Vector hubcompanyFac=radian.web.vvHelpObj.gethubcompanyFacilities(db);
		  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		  String companyArrayJs = "";
		  if ( hub_list.size() > 0 )
		  {
			companyArrayJs=radian.web.HTMLHelpObj.getHubCompanyJs( hub_list_set,hubcompanyFac,posession ).toString();
		  }
		  posession.setAttribute( "PO_CATALOGS_JS",companyArrayJs );
		  posession.setAttribute( "PO_HUBCOMPFACS",hubcompanyFac );
		}

		if ( this.getupdateStatus() )
		{
		  buildHeader( branch_plant,hub_list_set,vvFob,"","",blanketPO,User_Action,subUserAction,vvPayment,vvCurrency,out ) ;
		}
		else
		{
		  buildReadonlyHeader( branch_plant,hub_list_set,vvFob,"","",blanketPO,User_Action,subUserAction,vvPayment,vvCurrency,out );
		}

		hub_list_set=null;
		vvFob=null;
		vvPayment=null;
		compnayids=null;

	  }
	  else if ( User_Action.equalsIgnoreCase( "searchlike" ) )
	  {
	  Hashtable hub_list_set= ( Hashtable ) posession.getAttribute( "PO_HUB_SET" );
	  addchargeslist= ( Vector ) posession.getAttribute( "PO_ADD_CHARGE_ITEM_TYPE" );
	  Vector buyernames= ( Vector ) posession.getAttribute( "PO_BUYERNAMES" );
	  Hashtable vvFob= ( Hashtable ) posession.getAttribute( "PO_VV_FOB" );
	  Vector vvPayment= ( Vector ) posession.getAttribute( "PO_VV_PAYMENT" );
	  Vector vvCurrency= ( Vector ) posession.getAttribute( "VV_CURRENCY" );

	  int lockstatusCount=0;
	  readonly=false;
	  nopermissions=false;
	  DBResultSet dbrs = null;
	  ResultSet rs = null;
	  /*if ( radianPo.trim().length() > 0 )
	  {
		lockstatusCount=DbHelpers.countQuery( db,"select count(*) from po_header_view where radian_po = " + radianPo + " and DBUY_LOCK_STATUS = 'Y'" );
	  }*/

	  if ( radianPo.trim().length() > 0 && "po".equalsIgnoreCase( subUserAction ) )
	  {
		String testbranchplant="";
		String dbuylockstatus="";
		String transport = "";
		String opsEntityId = "";
        String hubnum ="";
        String hubname = "";
        //String paymentTerms = "";

        try
		{
		  String query="select PAYMENT_TERMS,HUB_NAME,OPS_ENTITY_ID,TRANSPORT,BRANCH_PLANT,RADIAN_PO,DBUY_LOCK_STATUS from po_header_view where RADIAN_PO = '" + radianPo + "' ";

		  dbrs=db.doQuery( query );
		  rs=dbrs.getResultSet();
		  while ( rs.next() )
		  {
			testbranchplant= ( rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ) );
			dbuylockstatus= ( rs.getString( "DBUY_LOCK_STATUS" ) == null ? "" : rs.getString( "DBUY_LOCK_STATUS" ) );
			if ( "Y".equalsIgnoreCase( dbuylockstatus ) )
			{
			  lockstatusCount++;
			}
			transport= ( rs.getString( "TRANSPORT" ) == null ? "" : rs.getString( "TRANSPORT" ) );
			opsEntityId = rs.getString( "OPS_ENTITY_ID" ) == null ? "" : rs.getString( "OPS_ENTITY_ID" );
            hubnum = rs.getString( "HUB_NAME" ) == null ? "" : rs.getString( "HUB_NAME" );
            hubname = rs.getString( "HUB_NAME" ) == null ? "" : rs.getString( "HUB_NAME" );
            //paymentTerms = rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" );
          }
		  hubname=radian.web.HTMLHelpObj.gethubNamefromBP( hub_list_set,testbranchplant );
			
		  //Hashtable hubsetdetails= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		  //String hubnum =hubsetdetails.get( testbranchplant ) == null ? "" : hubsetdetails.get( testbranchplant ).toString();
		  //PersonnelBean personnelBean=posession.getAttribute( "personnelBean" ) == null ? null :( PersonnelBean ) posession.getAttribute( "personnelBean" );

		  //polog.info(hubnum);
		  if (testbranchplant.trim().length() > 0)
		  {
			if ( (hubnum.length() == 0 || "Web".equalsIgnoreCase(transport))  && !( personnelBean.getPermissionBean().hasFacilityPermission( "ReadOnlyPo",opsEntityId,null ) || personnelBean.getPermissionBean().hasFacilityPermission( "Sourcing","",null ))  )
		  {
			nopermissions = true;
		  }
		  else
		  {
			//String hubname=radian.web.HTMLHelpObj.gethubNamefromBP( hub_list_set,testbranchplant );
			//polog.info( "Sourcing Permissions   " + personnelBean.getPermissionBean().hasFacilityPermission( "Sourcing","",null ) + "");
			if ( !personnelBean.getPermissionBean().hasFacilityPermission( "Purchasing",hubname,null ) )
			{
			  readonly=true;
			}

			if ( personnelBean.getPermissionBean().hasFacilityPermission( "chgCurrency",hubname,null ) )
			{
			  this.setallowCurrencyupdate(true);
			}
			else
			{
			  this.setallowCurrencyupdate(false);
			}

            if ("HAASTCMUSA".equalsIgnoreCase(opsEntityId))
            {
                if ( personnelBean.getPermissionBean().hasFacilityPermission( "confPoCreditCardTerms",hubname,null ) )
			    {
			        this.setAllowPOCreditConfirmaiton(true);
			    }
			    else
			    {
			        this.setAllowPOCreditConfirmaiton(false);
			    }
            }
            //polog.info( "User Group Id: chgCurrency  Facility Id: " + hubname + "  " + personnelBean.getPermissionBean().hasFacilityPermission( "chgCurrency","" + hubname + "",null ) + "" );
		  }
		  }
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
	  else if ( blanketPO.length() > 0 && "bpo".equalsIgnoreCase( subUserAction ) )
	  {
		try
		{
		  String query="select OPS_ENTITY_ID,BRANCH_PLANT,BPO from bpo_header_view where BPO = '" + blanketPO + "' ";
		  String testbranchplant="";
		  String opsEntityId = "";
		  int test_number=0;

		  dbrs=db.doQuery( query );
		  rs=dbrs.getResultSet();
		  while ( rs.next() )
		  {
			testbranchplant= ( rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ) );
			//transport= ( rs.getString( "TRANSPORT" ) == null ? "" : rs.getString( "TRANSPORT" ) );
			opsEntityId = rs.getString( "OPS_ENTITY_ID" ) == null ? "" : rs.getString( "OPS_ENTITY_ID" );
			test_number++;
		  }

		  Hashtable hubsetdetails= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		  String hubnum =hubsetdetails.get( testbranchplant ) == null ? "" : hubsetdetails.get( testbranchplant ).toString();
		  //PersonnelBean personnelBean=posession.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) posession.getAttribute( "personnelBean" );

		  //polog.info(hubnum);
		  if (testbranchplant.trim().length() > 0)
		  {
			if ( (hubnum.length() == 0)  && !( personnelBean.getPermissionBean().hasFacilityPermission( "ReadOnlyPo",opsEntityId,null ) || personnelBean.getPermissionBean().hasFacilityPermission( "Sourcing","",null ))  )
			{
			  nopermissions=true;
			}
			else
			{
			  String hubname=radian.web.HTMLHelpObj.gethubNamefromBP( hub_list_set,testbranchplant );
			  if ( !personnelBean.getPermissionBean().hasFacilityPermission( "Purchasing",hubname,null ) )
			  {
				readonly=true;
			  }

			  if ( personnelBean.getPermissionBean().hasFacilityPermission( "chgCurrency",hubname,null ) )
			  {
				this.setallowCurrencyupdate(true);
			  }
			  else
			  {
				this.setallowCurrencyupdate(false);
			  }

			  //polog.info( "User Group Id: Purchasing  Facility Id: " + hubname + "  " + personnelBean.getPermissionBean().hasFacilityPermission( "Purchasing","" + hubname + "",null ) + "" );
			}
		  }
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

	  if ( nopermissions && this.getupdateStatus())
	  {
		buildHeader( branch_plant,hub_list_set,vvFob,radianPo,res.getString("msg.nopermviewpo"),blanketPO,"New",subUserAction,vvPayment,vvCurrency,out );
	  }
	  else if ( nopermissions && !this.getupdateStatus())
	  {
		buildReadonlyHeader( branch_plant,hub_list_set,vvFob,radianPo,res.getString("msg.nopermviewpo"),blanketPO,"New",subUserAction,vvPayment,vvCurrency,out );
	  }
	  else if ( this.getupdateStatus() && lockstatusCount == 0 && !readonly)
	  {
		buildHeader( branch_plant,hub_list_set,vvFob,radianPo,"",blanketPO,User_Action,subUserAction,vvPayment,vvCurrency,out );
	  }
	  else
	  {
		buildReadonlyHeader( branch_plant,hub_list_set,vvFob,radianPo,"",blanketPO,User_Action,subUserAction,vvPayment,vvCurrency,out );
	  }

	  buyernames=null;
	  vvFob=null;
	  vvPayment=null;
	  }
	  //TCMISDEV-778 Add resend feature to supplier for POs in status ProblemWbuy: Summary - Save buyer changes, call new procedure, redisplay PO to user in locked status
	  else if ( User_Action.equalsIgnoreCase( "save" ) || User_Action.equalsIgnoreCase("resendWBuyPo" ) || User_Action.equalsIgnoreCase("resendDBuyPo" ))
	  {
		Hashtable hub_list_set= ( Hashtable ) posession.getAttribute( "PO_HUB_SET" );
		addchargeslist= ( Vector ) posession.getAttribute( "PO_ADD_CHARGE_ITEM_TYPE" );
		Vector buyernames= ( Vector ) posession.getAttribute( "PO_BUYERNAMES" );
		Hashtable vvFob= ( Hashtable ) posession.getAttribute( "PO_VV_FOB" );
		Vector vvPayment= ( Vector ) posession.getAttribute( "PO_VV_PAYMENT" );
		Vector vvCurrency= ( Vector ) posession.getAttribute( "VV_CURRENCY" );

		Hashtable synch_data=synchServerData( request );
		String blanketorpo="";

		Hashtable updateresults=new Hashtable();
		if ( ( radianPo.length() > 0 ) && ( "Yes".equalsIgnoreCase( validPo ) ) )
		{
		  updateresults=updatePoDetails( synch_data,personnelid,"save" );
		  blanketorpo="Purchase Order";
		}
		else if ( ( blanketPO.length() > 0 ) && ( "Yes".equalsIgnoreCase( validbPo ) ) )
		{
		  blanketorpo="Blanket Order";
		  updateresults=updatebPoDetails( synch_data,personnelid,"save" );
		}
		else
		{
		  completeSuccess=false;
		}


		String hubname=radian.web.HTMLHelpObj.gethubNamefromBP( hub_list_set,branch_plant );
		if ( hubname.length() > 0 )
		{
		  //PersonnelBean personnelBean=posession.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) posession.getAttribute( "personnelBean" );

		  if ( personnelBean.getPermissionBean().hasFacilityPermission( "chgCurrency",hubname,null ) )
		  {
			this.setallowCurrencyupdate( true );
		  }
		  else
		  {
			this.setallowCurrencyupdate( false );
		  }
		}
		//polog.info( "User Group Id: chgCurrency  Facility Id: " + hubname + "  " + personnelBean.getPermissionBean().hasFacilityPermission( "chgCurrency","" + hubname + "",null ) + "" );
		if(User_Action.equalsIgnoreCase("resendDBuyPo" ))
		{
			DBResultSet dbrs = null;
			  ResultSet rs = null;
			  Connection connect1=null;
			  CallableStatement cs=null;
	
			  int totalitemQty=0;
			  try
			  {
			    connect1=db.getConnection();
			    Statement poCloneInsertStatement= connect1.createStatement();
			    poCloneInsertStatement.execute("UPDATE buy_order SET status = 'InProgressDBuy' WHERE EXISTS (SELECT radian_po FROM po_line_draft WHERE radian_po = "+radianPo+" AND quantity > 0) AND radian_po = "+radianPo+" AND status <> 'Confirmed' AND supply_path = 'Dbuy'");
			  }
			  catch ( Exception e )
			  {
			    e.printStackTrace();
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
		}
		//TCMISDEV-778 Add resend feature to supplier for POs in status ProblemWbuy: Summary - Save buyer changes, call new procedure, redisplay PO to user in locked status
		else if(User_Action.equalsIgnoreCase("resendWBuyPo" ))
		{
			 DBResultSet dbrs = null;
			  ResultSet rs = null;
			  Connection connect1=null;
			  CallableStatement cs=null;
	
			  int totalitemQty=0;
			  try
			  {
			    connect1=db.getConnection();
			    connect1.setAutoCommit(false);
			    cs=connect1.prepareCall( "{call PKG_PO.P_RESEND_WBUY_PO(?)}" );//TCMISDEV-778 New Procedure call
			    cs.setString(1,radianPo);
			    cs.execute();
			    connect1.setAutoCommit(true);
			  }
			  catch ( Exception e )
			  {
			    e.printStackTrace();
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
		}
    int dBuylockstatusCount=0;
    DBResultSet dbrs = null;
    String dbuylockstatus="";
    ResultSet rs = null;
    String opsEntityId = "";
    //String paymentTerms = "";

    try
		{
		  String query="select PAYMENT_TERMS,OPS_ENTITY_ID,TRANSPORT,BRANCH_PLANT,RADIAN_PO,DBUY_LOCK_STATUS from po_header_view where RADIAN_PO = '" + radianPo + "' ";

		  dbrs=db.doQuery( query );
		  rs=dbrs.getResultSet();
		  while ( rs.next() )
		  {
		  	dbuylockstatus= ( rs.getString( "DBUY_LOCK_STATUS" ) == null ? "" : rs.getString( "DBUY_LOCK_STATUS" ) );
		  	if ( "Y".equalsIgnoreCase( dbuylockstatus ) )
			  {
			    dBuylockstatusCount++;
			  }
            opsEntityId = rs.getString( "OPS_ENTITY_ID" ) == null ? "" : rs.getString( "OPS_ENTITY_ID" );
            //paymentTerms = rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" );
          }
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		}
		finally
		{
		  if ( dbrs != null ){dbrs.close();}
		}


       if ("HAASTCMUSA".equalsIgnoreCase(opsEntityId))
        {
            if ( personnelBean.getPermissionBean().hasFacilityPermission( "confPoCreditCardTerms",hubname,null ) )
            {
                this.setAllowPOCreditConfirmaiton(true);
            }
            else
            {
                this.setAllowPOCreditConfirmaiton(false);
            }
        }

    //TCMISDEV-778 Add resend feature to supplier for POs in status ProblemWbuy: Summary - Save buyer changes, call new procedure, redisplay PO to user in locked status
    if ( completeSuccess )
		{
      if (dBuylockstatusCount == 0)
      {
      buildHeader( branch_plant,hub_list_set,vvFob,radianPo,(User_Action.equalsIgnoreCase("resendWBuyPo" ) || User_Action.equalsIgnoreCase("resendDBuyPo" ) ? "":"Saved The " + blanketorpo + " Successfully"),blanketPO,User_Action,
									subUserAction,vvPayment,vvCurrency,out);
      }
      else
      {
      buildReadonlyHeader( branch_plant,hub_list_set,vvFob,radianPo,(User_Action.equalsIgnoreCase("resendWBuyPo" ) || User_Action.equalsIgnoreCase("resendDBuyPo" ) ? "":"Saved The " + blanketorpo + " Successfully"),blanketPO,User_Action,
									subUserAction,vvPayment,vvCurrency,out);
      }
    }
		else
		{
      if (dBuylockstatusCount == 0)
      {
      buildHeader( branch_plant,hub_list_set,vvFob,radianPo,(User_Action.equalsIgnoreCase("resendWBuyPo" ) || User_Action.equalsIgnoreCase("resendDBuyPo" )  ? "":"An Error Occured While Saving. Please try again." + errorFromProcedure + ""),
									blanketPO,User_Action,subUserAction,vvPayment,vvCurrency,out );
      }
      else
      {
      buildReadonlyHeader( branch_plant,hub_list_set,vvFob,radianPo,(User_Action.equalsIgnoreCase("resendWBuyPo" ) || User_Action.equalsIgnoreCase("resendDBuyPo" )  ? "":"An Error Occured While Saving. Please try again." + errorFromProcedure + ""),
									blanketPO,User_Action,subUserAction,vvPayment,vvCurrency,out );
      }
    }

		synch_data=null;
		updateresults=null;
		buyernames=null;
		vvFob=null;
		vvPayment=null;
	  }
	  else if ( User_Action.equalsIgnoreCase( "confirm" ) )
	  {
		Hashtable hub_list_set= ( Hashtable ) posession.getAttribute( "PO_HUB_SET" );
		addchargeslist= ( Vector ) posession.getAttribute( "PO_ADD_CHARGE_ITEM_TYPE" );
		Vector buyernames= ( Vector ) posession.getAttribute( "PO_BUYERNAMES" );
		Hashtable vvFob= ( Hashtable ) posession.getAttribute( "PO_VV_FOB" );
		Vector vvPayment= ( Vector ) posession.getAttribute( "PO_VV_PAYMENT" );
		Vector vvCurrency= ( Vector ) posession.getAttribute( "VV_CURRENCY" );

		Hashtable synch_data=synchServerData( request );
		String blanketorpo="";
		Hashtable updateresults=new Hashtable();
		if ( ( radianPo.length() > 0 ) && ( "Yes".equalsIgnoreCase( validPo ) ) )
		{
		  updateresults=updatePoDetails( synch_data,personnelid,"confirm" );
		  blanketorpo=res.getString("purchaseOrder");
		}
		else if ( ( blanketPO.length() > 0 ) && ( "Yes".equalsIgnoreCase( validbPo ) ) )
		{
		  updateresults=updatebPoDetails( synch_data,personnelid,"confirm" );
		  blanketorpo=res.getString("label.blanketorder");
		}
		else
		{
		  completeSuccess=false;
		}

		String hubname=radian.web.HTMLHelpObj.gethubNamefromBP( hub_list_set,branch_plant );
		if ( hubname.length() > 0 )
		{
		  //PersonnelBean personnelBean=posession.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) posession.getAttribute( "personnelBean" );

		  if ( personnelBean.getPermissionBean().hasFacilityPermission( "chgCurrency",hubname,null ) )
		  {
			this.setallowCurrencyupdate( true );
		  }
		  else
		  {
			this.setallowCurrencyupdate( false );
		  }
		}

    int dBuylockstatusCount=0;
    DBResultSet dbrs = null;
    String dbuylockstatus="";
    ResultSet rs = null;
    //String paymentTerms = "";
    String opsEntityId = "";

    try
		{
		  String query="select PAYMENT_TERMS,OPS_ENTITY_ID,TRANSPORT,BRANCH_PLANT,RADIAN_PO,DBUY_LOCK_STATUS from po_header_view where RADIAN_PO = '" + radianPo + "' ";

		  dbrs=db.doQuery( query );
		  rs=dbrs.getResultSet();
		  while ( rs.next() )
		  {
		  	dbuylockstatus= ( rs.getString( "DBUY_LOCK_STATUS" ) == null ? "" : rs.getString( "DBUY_LOCK_STATUS" ) );
		  	if ( "Y".equalsIgnoreCase( dbuylockstatus ) )
			  {
			    dBuylockstatusCount++;
			  }
           opsEntityId = rs.getString( "OPS_ENTITY_ID" ) == null ? "" : rs.getString( "OPS_ENTITY_ID" );
           //paymentTerms = rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" );
          }
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		}
		finally
		{
		  if ( dbrs != null ){dbrs.close();}
		}

        if ("HAASTCMUSA".equalsIgnoreCase(opsEntityId))
        {
            if ( personnelBean.getPermissionBean().hasFacilityPermission( "confPoCreditCardTerms",hubname,null ) )
            {
                this.setAllowPOCreditConfirmaiton(true);
            }
            else
            {
                this.setAllowPOCreditConfirmaiton(false);
            }
        }       
 
    if ( completeSuccess )
		{
      if (dBuylockstatusCount ==0)
      {
      String interCompMrNumber = (String) updateresults.get("INTER_COMPANY_MR");
      String interCompMrNumberMessage = "";
      if (interCompMrNumber != null && interCompMrNumber.length() > 0)
      {
         interCompMrNumberMessage = "\nAn intercompany MR was created with MR -"+interCompMrNumber+"";
      }

      buildHeader( branch_plant,hub_list_set,vvFob,radianPo,res.getString("label.confirmthe") + " "+blanketorpo +" " + res.getString("label.sucfuly")+""+interCompMrNumberMessage+"",blanketPO,User_Action,
									subUserAction,vvPayment,vvCurrency,out );
      }
      else
      {
        String interCompMrNumber = (String) updateresults.get("INTER_COMPANY_MR");
        String interCompMrNumberMessage = "";
        if (interCompMrNumber != null && interCompMrNumber.length() > 0)
        {
             interCompMrNumberMessage = "\nAn intercompany MR was created with MR -"+interCompMrNumber+"";
        }
        buildReadonlyHeader( branch_plant,hub_list_set,vvFob,radianPo,res.getString("label.confirmthe") + " " + blanketorpo +" "+ res.getString("label.sucfuly")+""+interCompMrNumberMessage+"",blanketPO,User_Action,
									subUserAction,vvPayment,vvCurrency,out );
      }
    }
		else
		{
            String finalErrorMesage = "";
            if (errorFromProcedure.startsWith("Please Note:"))
            {
               finalErrorMesage =  res.getString("label.confirmthe") + " "+blanketorpo +" " + res.getString("label.sucfuly") +" "+ errorFromProcedure;
            }
            //TCMISDEV-207 Catalyst B - Procedure to check for financial approval tree for POs  
            else if(errorFromProcedure.indexOf("!ok") > -1)
            {
            	 finalErrorMesage = errorFromProcedure.replace("!ok", "");
            }
            else
            {
              finalErrorMesage =  res.getString("msg.errconf") + " "+ errorFromProcedure;
            }
      if (dBuylockstatusCount ==0)
      {
      buildHeader( branch_plant,hub_list_set,vvFob,radianPo,
									 finalErrorMesage,blanketPO,User_Action,subUserAction,
									vvPayment,vvCurrency,out );
      }
      else
      {
      buildReadonlyHeader( branch_plant,hub_list_set,vvFob,radianPo,
									finalErrorMesage,blanketPO,User_Action,subUserAction,
									vvPayment,vvCurrency,out );
      }
    }

		synch_data=null;
		updateresults=null;
		buyernames=null;
		vvFob=null;
		vvPayment=null;
	  }
	  else if ( User_Action.equalsIgnoreCase( "financialApproval" ) )
	  {
		  String finalErrorMesage = "";
	
		  String personnelID= personnelBean.getPersonnelIdBigDecimal().toPlainString();
		  
		  Hashtable hub_list_set= ( Hashtable ) posession.getAttribute( "PO_HUB_SET" );
		  addchargeslist= ( Vector ) posession.getAttribute( "PO_ADD_CHARGE_ITEM_TYPE" );
		  Vector buyernames= ( Vector ) posession.getAttribute( "PO_BUYERNAMES" );
		  Hashtable vvFob= ( Hashtable ) posession.getAttribute( "PO_VV_FOB" );
		  Vector vvPayment= ( Vector ) posession.getAttribute( "PO_VV_PAYMENT" );
		  Vector vvCurrency= ( Vector ) posession.getAttribute( "VV_CURRENCY" );
		  String results = "";
		  Connection connecPo = null;
		  try
		  {
			  
			  connecPo=db.getConnection();
			  connecPo.setAutoCommit( false );

			  CallableStatement cs = connecPo.prepareCall("{call PKG_PO_FINANCIAL_APPROVAL.p_approve_fa_pending_po (?,?)}");
			  cs.setString(1,radianPo);
			  cs.setString(2,personnelID);
			  cs.execute();
			  connecPo.commit();
			  polog.info("pkg_dbuy.p_set_financial_approval "+personnelID+ " PO "+radianPo);
			  finalErrorMesage = res.getString("label.financialapprovalsuccess");
		  }
		  catch(Exception e)
		  {
			  finalErrorMesage =  res.getString("error.db.procedure");
		  }
		  finally
		  {
				  try
				  {
					  if(connecPo != null)
					  {
						  connecPo.commit();
						  connecPo.setAutoCommit( true );
					  }
				  }
				  catch ( SQLException ex )
				  {
					//ex.printStackTrace();
				  }
		  }
		    int dBuylockstatusCount=0;
		    DBResultSet dbrs = null;
		    String dbuylockstatus="";
		    ResultSet rs = null;
		  
		    try
			{
			  String query="select DBUY_LOCK_STATUS from po_header_view where RADIAN_PO = '" + radianPo + "' ";

			  dbrs=db.doQuery( query );
			  rs=dbrs.getResultSet();
			  while ( rs.next() )
			  {
			  	dbuylockstatus= ( rs.getString( "DBUY_LOCK_STATUS" ) == null ? "" : rs.getString( "DBUY_LOCK_STATUS" ) );
			  	if ( "Y".equalsIgnoreCase( dbuylockstatus ) )
				    dBuylockstatusCount++;
			  }


			}
			catch ( Exception e )
			{
			  e.printStackTrace();
			}
			finally
			{
			  if ( dbrs != null ){dbrs.close();}
			}

	   if (dBuylockstatusCount ==0)
		buildHeader( branch_plant,hub_list_set,vvFob,radianPo,
				finalErrorMesage,blanketPO,User_Action,subUserAction,
				vvPayment,vvCurrency,out );
	   buildReadonlyHeader( branch_plant,hub_list_set,vvFob,radianPo,
				finalErrorMesage,blanketPO,User_Action,subUserAction,
				vvPayment,vvCurrency,out );
 		  
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  out.close();
	}
  }

  private void buildHeader( String hub,Hashtable hub_list_set,Hashtable vvFob,String radianPo1,String message,String blanketPO,String userAction,
									String subUserAction,Vector vvPayment1,Vector vvCurrency,PrintWriter poout )
  {
	//StringBuffer Msg=new StringBuffer();
	String SelectedHubName="";

	try
	{
	  poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "purchaseOrder","purchaseorder.js",intcmIsApplication,res ) );
	  poout.println( "<SCRIPT SRC=\"/js/common/commonutil.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT SRC=\"/clientscripts/povalidation.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT SRC=\"/clientscripts/popopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT SRC=\"/clientscripts/associatedPrs.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT type=\"text/javascript\" src=\"/js/menu/milonic_src.js\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT type=\"text/javascript\" src=\"/js/menu/mmenudom.js\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT type=\"text/javascript\" src=\"/js/menu/mainmenudata.js\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT type=\"text/javascript\" src=\"/js/menu/contextmenu.js\"></script>\n" );
	  poout.println( "<SCRIPT type=\"text/javascript\" src=\"/js/menu/mm_menueditapi.js\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT type=\"text/javascript\" src=\"/js/jquery/jquery-1.6.4.js\"></SCRIPT>\n" );
	  poout.println( "<%@ include file=\"/common/rightclickmenudata.jsp\" %>\n");
	  poout.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );

poout.println( "<script language=\"JavaScript\" type=\"text/javascript\">\n" );
poout.println( "<!--\n" );
poout.println( "var messagesData = new Array();\n" );
poout.println( "messagesData={alert:\""+res.getString("msg.waitupdate")+"\",\n" );
poout.println( "chooseprintpo:\""+res.getString("label.chooseprintpo")+"\",\n" );
poout.println( "validpo:\""+res.getString("label.validpo")+"\",\n" );
poout.println( "checkatleastone:\""+res.getString("msg.waitupdate")+"\"};\n" );
poout.println( "with ( milonic=new menuname(\"contactLogMenu\") ) {\n" );
poout.println( "top=\"offset=2\";\n" );
poout.println( "style=submenuStyle;\n" );
poout.println( "itemheight=17;\n" );
poout.println( "aI( \"text=Contact Log ;url=javascript:doNothing();\" )\n" );
poout.println( "}\n" );
poout.println( "drawMenus();\n" );
poout.println( "// -->\n" );
poout.println( "</script>    \n" );

      poout.println( "</HEAD>  \n" );

	  if ( !userAction.equalsIgnoreCase( "New" ) )
	  {
		if ( "newbpo".equalsIgnoreCase( subUserAction ) )
		{
		  poout.println( "<BODY onLoad=\"recalnewPo('bpo')\" onmouseup=\"toggleContextMenuToNormal()\">\n" );
		}
		else if ( "newpo".equalsIgnoreCase( subUserAction ) )
		{
		  poout.println( "<BODY onLoad=\"recalnewPo('po')\" onmouseup=\"toggleContextMenuToNormal()\">\n" );
		}
		else if ( radianPo1.length() > 0 || "po".equalsIgnoreCase( subUserAction ) )
		{
		  poout.println( "<BODY onLoad=\"recalPo('" + radianPo1 + "','','po','" + hub + "')\" onmouseup=\"toggleContextMenuToNormal()\">\n" );
		}
		else if ( blanketPO.length() > 0 || "bpo".equalsIgnoreCase( subUserAction ) )
		{
		  poout.println( "<BODY onLoad=\"recalPo('" + blanketPO + "','','bpo','" + hub + "')\" onmouseup=\"toggleContextMenuToNormal()\">\n" );
		}
		else
		{
		  poout.println( "<BODY onLoad=\"recalPo('" + radianPo1 + "','','po','" + hub + "')\" onmouseup=\"toggleContextMenuToNormal()\">\n" );
		}
	  }
	  else
	  {
		poout.println( "<BODY onmouseup=\"toggleContextMenuToNormal()\">\n" );
	  }

	  poout.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
	  poout.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT></center>\n" );
	  poout.println( "</DIV>\n" );
	  poout.println( "<DIV ID=\"MAINPAGE\">\n" );

	  poout.println( radian.web.HTMLHelpObj.printPurchaseOrderTitleBar( "<B>"+res.getString("purchaseOrder")+"</B>\n" ) );
	  poout.println( "<FORM method=\"POST\" ID=\"purchaseorder\" NAME=\"purchaseorder\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString( "PURCHASE_ORDER" ) + "\">\n" );
	  poout.println( "<P ID=\"mainpara\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"Action\" NAME=\"Action\" VALUE=\"Details\">\n" );
	  poout.println( "<TABLE BORDER=\"0\" ID=\"mainheadertable\" CELLSPACING=\"0\" CELLPADDING=\"1\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  poout.println( "<TR>\n" );
	  poout.println( "<TD WIDTH=\"100%\" COLSPAN=\"6\" HEIGHT=\"5\" ALIGN=\"CENTER\" ID=\"messagerow\"><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>" + message + "</B></FONT></TD>\n" );
	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );
	  //Supplier Line 1
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.supplier")+":</B>\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" ID=\"supplierline1\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Hub
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.hub")+":</B>&nbsp;\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"26%\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"HubFullName\" ID=\"HubFullName\" value=\"\">\n" );
	  poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" ID=\"HubName\" size=\"1\" DISABLED>\n" );
	  poout.println( "<OPTION VALUE=\"None\">"+res.getString("label.pleaseselect")+"</OPTION>\n" );
	  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
	  String hub_selected;
	  for ( Enumeration e=hub_list.keys(); e.hasMoreElements(); )
	  {
		String branch_plant= ( String ) ( e.nextElement() );
		String hub_name= ( String ) ( hub_list.get( branch_plant ) );
		//
		hub_selected="";
		if ( branch_plant.equalsIgnoreCase( hub ) )
		{
		  hub_selected="selected";
		  SelectedHubName=hub_name;
		}
		poout.println( "<option  " + hub_selected + " value=\"" + branch_plant + "\">" + hub_name + "</option>\n" );
	  }
	  poout.println( "</SELECT>\n" );
	  poout.println( "</TD>\n" );

	  //PO
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>PO:</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"po\" ID=\"po\" value=\""+radianPo1+"\" onChange=\"invalidatePo()\" size=\"8\">\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchpolike\" name=\"searchpolike\" value=\"...\" OnClick=\"getPo(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.po")+"\"></BUTTON>\n" );
	  poout.println( "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"Newpo\" name=\"Newpo\" value=\""+res.getString("label.new")+"\" OnClick=\"getnewPo(this.form)\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );

	  //Supplier Line 2
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"supplierid\" ID=\"supplierid\" value=\"\" onChange=\"invalidateSupplier()\" size=\"4\">\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchsupplierlike\" name=\"searchsupplierlike\" value=\"...\" OnClick=\"getSupplier(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.supplier")+"\"></BUTTON>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" ID=\"supplierline2\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //FOB
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.tradeTerms")+":</B>&nbsp;\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"26%\">\n" );
	  poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"fob\" ID=\"fob\" size=\"1\">\n" );
	  poout.println( "<OPTION VALUE=\"None\">"+res.getString("label.pleaseselect")+"</OPTION>\n" );
	  //poout.println( radian.web.HTMLHelpObj.getDropDown( vvFob,"" ) );
	  poout.println( radian.web.HTMLHelpObj.sorthashbyValue( vvFob.entrySet(),"" ) );
	  poout.println( "</SELECT>\n" );
	  poout.println( "</TD>\n" );

	  //BPO
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.bo")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"bpo\" ID=\"bpo\" value=\""+blanketPO+"\" onChange=\"invalidatebPo()\" size=\"8\">\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchbpolike\" name=\"searchbpolike\" value=\"...\" OnClick=\"getbpo(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.bpo")+"\"></BUTTON>\n" );
	  poout.println( "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"Newbpo\" name=\"Newbpo\" value=\""+res.getString("label.new")+"\" OnClick=\"getnewbpo(this.form)\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );

	  //Supplier Line 3
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"suppRating\" ID=\"suppRating\" value=\"\" size=\"8\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" ID=\"supplierline3\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Carrier Line 1
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.shipping")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"carrierline1\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Buyer
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.buyer")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"buyer\" ID=\"buyer\" value=\"\" size=\"12\" readonly>\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"buyerid\" ID=\"buyerid\" value=\"\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );

	  //Supplier Line 4
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" ID=\"supplierline4\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Carrier Line 2
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"carrieraccount\" ID=\"carrieraccount\" VALUE=\"\">\n<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"carrierID\" ID=\"carrierID\" value=\"\" onChange=\"invalidateCarrier()\" size=\"4\">\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchcarrierlike\" name=\"searchcarrierlike\" value=\"...\" OnClick=\"getcarrier(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.carrier")+"\"></BUTTON>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"carrierline2\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Order Taker
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.ordertaker")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"ordertakerID\" ID=\"ordertakerID\" value=\"\">\n" );
	  poout.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"ordertaker\" ID=\"ordertaker\" value=\"\" onChange=\"invalidateOrderTaker()\" size=\"8\">\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchordertakerlike\" name=\"searchordertakerlike\" value=\"...\" OnClick=\"getorderTaker(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.ordertaker")+"\"></BUTTON>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );

	  poout.println( "<TR><TD  COLSPAN=\"2\" ALIGN=\"LEFT\" HEIGHT=\"5\">"+res.getString("label.phonenumber")+": <INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"mfgphoneno\" ID=\"mfgphoneno\" value=\"\" size=\"20\" readonly></TD>\n" );
	  //poout.println( "<TD ALIGN=\"LEFT\" HEIGHT=\"5\"><INPUT CLASS=\"WHITEHEADER\"  TYPE=\"checkbox\" NAME=\"criticalpo\" ID=\"criticalpo\" value=\"Yes\">&nbsp;&nbsp;<B>Critical</B></TD>\n" );
	  poout.println( "<TD ALIGN=\"LEFT\" HEIGHT=\"5\">\n" );
	  poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"criticalpo\" ID=\"criticalpo\" size=\"1\">\n" );
		poout.println( "<OPTION VALUE=\"N\">"+res.getString("label.N")+"</OPTION>\n" );
	  poout.println( "<OPTION VALUE=\"Y\">"+res.getString("label.critical")+"</OPTION>\n" );
	  poout.println( "<OPTION VALUE=\"S\">"+res.getString("label.aog")+"</OPTION>\n" );
      poout.println( "<OPTION VALUE=\"L\">"+res.getString("label.ld")+"</OPTION>\n" );
      poout.println( "</SELECT>\n" );
	  poout.println( "<B>"+res.getString("label.critical")+"</B></TD>\n" );

		poout.println( "<TD  COLSPAN=\"3\" ALIGN=\"RIGHT\" HEIGHT=\"5\">"+res.getString("label.phonenumber")+": <INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"contactphoneno\" ID=\"contactphoneno\" value=\"\" size=\"15\" readonly>\n" );
	  poout.println( res.getString("label.fax")+": <INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"contactfaxno\" ID=\"contactfaxno\" value=\"\" size=\"15\" readonly></TD></TR>\n" );

	  poout.println( "<TR>\n" );
	  //Supplier Email
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\">\n" );
	  poout.println( res.getString("label.email")+":\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"supplieremail\">\n" );
	  poout.println( "</TD>\n" );

	  //Blank
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>&nbsp;</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "&nbsp;\n" );
	  poout.println( "</TD>\n" );

	  //Supplier Contact Email
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\">\n" );
	  poout.println( res.getString("label.email")+":\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" ID=\"suppliercontactemail\">\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );
	  //Ship To Line 1
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.shipto")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline1\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Fax Date
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.dateconfirmed")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"faxdate\" ID=\"faxdate\" value=\"\" size=\"8\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  //Customer PO
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.customerpo")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"customerpo\" ID=\"customerpo\" value=\"\" size=\"20\" MAXLENGTH=\"30\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );

	  //Ship To Line 2
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"shiptocompanyid\" ID=\"shiptocompanyid\" VALUE=\"\">\n<INPUT TYPE=\"hidden\" NAME=\"shipToFacilityId\" ID=\"shipToFacilityId\" VALUE=\"\">\n<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"shiptoid\" ID=\"shiptoid\" value=\"\" onChange=\"invalidateShipTo()\" size=\"4\">\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchshiptolike\" name=\"searchshiptolike\" value=\"...\" OnClick=\"getshipTo(this.form,'')\"><IMG src=\"/images/search_small.gif\" alt=\"Ship To\"></BUTTON>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline2\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Accepted Date
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.dateaccepted")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"accepteddate\" ID=\"accepteddate\" value=\"\" size=\"8\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  //PO NOTES
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  //poout.println("<B>Total Amount:</B>\n");
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT TYPE=\"button\" style=\"width:100px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.scratchPad")+"\" onclick= \"showPonotes()\" ID=\"ponotes\" NAME=\"ponotes\">&nbsp;\n" );
	  poout.println( "<INPUT TYPE=\"button\" style=\"width:80px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.emailpad")+"\" onclick= \"showemailnotes()\" ID=\"emailponotes\" NAME=\"emailponotes\">&nbsp;\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );

	  //Ship To Line 3
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"LEFT\" VALIGN=\"BOTTOM\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.invengroup")+":<B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline3\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //BO Start Date
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.boStartDate")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  if ( ( "newbpo".equalsIgnoreCase( subUserAction ) ) ||
		   ( ( ( radianPo1.trim().length() == 0 ) && ( blanketPO.length() > 0 ) ) || "bpo".equalsIgnoreCase( subUserAction ) ) )
	  {
		poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"bostartdate\" ID=\"bostartdate\" value=\"\" size=\"8\" readonly><a href=\"javascript: void(0);\" ID=\"bostartdatelink\" onClick=\"return getCalendar(document.purchaseorder.bostartdate);\">&diams;</a>\n" );
	  }
	  else
	  {
		poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"bostartdate\" ID=\"bostartdate\" value=\"\" size=\"8\" readonly>\n" );
	  }
	  poout.println( "</TD>\n" );

	  //Payment Terms
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.paymentterms")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );

	  if ( this.getPaytermsupdateStatus() )
	  {
		//poout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"paymentterms\" ID=\"paymentterms\" value=\"\" size=\"5\">\n");
		poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"paymentterms\" ID=\"paymentterms\" OnChange=\"checkForCrediCartConf()\" size=\"1\">\n" );
		poout.println( "<OPTION VALUE=\"None\">"+res.getString("label.pleaseselect")+"</OPTION>\n" );
		poout.println( radian.web.HTMLHelpObj.getDropDown( vvPayment1,"" ) );
		poout.println( "</SELECT>\n" );
	  }
	  else
	  {
		poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"paymentterms\" ID=\"paymentterms\" value=\"\" size=\"10\" readonly>\n" );
	  }

	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );

	  //Ship To Line 4
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invengrp\" ID=\"invengrp\" value=\"\" size=\"10\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline4\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //BO End Date
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.boEndDate")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  if ( ( "newbpo".equalsIgnoreCase( subUserAction ) ) || ( ( ( radianPo1.trim().length() == 0 ) && ( blanketPO.length() > 0 ) ) || "bpo".equalsIgnoreCase( subUserAction ) ) )
	  {
		poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"boenddate\" ID=\"boenddate\" value=\"\" size=\"8\" readonly><a href=\"javascript: void(0);\" ID=\"boenddatelink\" onClick=\"return getCalendar(document.purchaseorder.boenddate);\">&diams;</a>\n" );
	  }
	  else
	  {
		poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"boenddate\" ID=\"boenddate\" value=\"\" size=\"8\" readonly>\n" );
	  }
	  poout.println( "</TD>\n" );

	  //Consigned PO
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"checkbox\" value=\"Yes\" NAME=\"consignedpo\" ID=\"consignedpo\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.consignedpo")+"</B>\n");
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );

	  //Ship To Line 5
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "&nbsp;\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline5\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.currency")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  if ( this.getallowCurrencyupdate() )
	  {
		poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"currency\" ID=\"currency\" OnChange=\"currencychanged(this.value)\" size=\"1\">\n" );
		poout.println( "<OPTION VALUE=\"None\">"+res.getString("label.pleaseselect")+"</OPTION>\n" );
		poout.println( radian.web.HTMLHelpObj.getDropDown( vvCurrency,"" ) );
		poout.println( "</SELECT>\n" );
	  }
	  else
	  {
		poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"currency\" ID=\"currency\" value=\"\" size=\"5\" readonly>\n" );
	  }
	  poout.println( "</TD>\n" );
                
    //Total Amount
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.totalamount")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"totalcost\" ID=\"totalcost\" value=\"\" size=\"10\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );
	  //Add Line
	  poout.println( "<TD WIDTH=\"10%\" CLASS=\"announce\"  ALIGN=\"CENTER\" COLSPAN=\"6\"  VALIGN=\"MIDDLE\">\n" );
      poout.println( "<INPUT TYPE=\"button\" style=\"width:120px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.addbuyorders")+"\" onclick= \"addBuyOrdersToPO(this)\" NAME=\"addBuyOrders\"  id=\"addBuyOrders\">&nbsp;\n" );
      poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" style=\"width:120px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.addMaterial")+"\" onclick= \"addLineItem(this,true)\" NAME=\"addLineButton\"  id=\"addLineButton\">&nbsp;\n" );
	  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" style=\"width:150px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.addCharge")+"\" onclick= \"addLineCharge(this,true)\" NAME=\"addChargeButton\" id=\"addChargeButton\">&nbsp;\n" );
	  poout.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMITW\" VALUE=\"\" onclick= \"return donothing(this)\" NAME=\"nothing\">\n" );
	  //Remove Line
	  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" style=\"width:120px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.removeLine")+"\" onclick= \"return removeline(name,this,true)\" NAME=\"removeLine\" id=\"removeLine\">&nbsp;\n" );
	  //Unremove line
	  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" style=\"width:170px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.undoRemove")+"\" onclick= \"return unremoveline(name,this)\" NAME=\"unremove\" id=\"unremove\">&nbsp;\n" );
	  //Save
	  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" style=\"width:70px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.save")+"\" onclick= \"return actionValue(name,this)\" NAME=\"save\" id=\"save\">&nbsp;\n" );
	  //poout.println("</TD>\n");
	  //Confirm
	  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" style=\"width:70px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.confirm")+"\" onclick= \"return actionValue(name,this)\" NAME=\"confirm\" id=\"confirm\">&nbsp;\n" );
	  //Financial Approval
	  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" style=\"display:none\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.financialapproval")+"\" onclick= \"return actionValue(name,this)\" NAME=\"financialApproval\" id=\"financialApproval\">&nbsp;\n" );
	  //Print
	  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" style=\"width:60px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.print")+"\" onclick= \"return printpo()\" NAME=\"printpobutton\" id=\"printpobutton\">&nbsp;\n" );
      poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" style=\"width:150px;display:none\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.poexpeditenotes")+"\" onclick= \"enterPoExpediteNotes()\" NAME=\"poExpediteNotesButton\" id=\"poExpediteNotesButton\">&nbsp;\n" );
	  ///TCMISDEV-778 button resend feature to supplier for POs in status ProblemWbuy
	  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" style=\"width:60px;display:none\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.resendwbuy")+"\" onclick= \"return resendPo()\" NAME=\"resendwbuypobutton\" id=\"resendwbuypobutton\">&nbsp;\n" );

	  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" style=\"width:80px;display:none\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.resenddbuy")+"\" onclick= \"return resendPo('resendDBuyPo')\" NAME=\"resenddbuypobutton\" id=\"resenddbuypobutton\">&nbsp;\n" );
      poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );
	  poout.println( "</TABLE>\n" );

	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validpo\" NAME=\"validpo\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validbpo\" NAME=\"validbpo\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validsupplier\" NAME=\"validsupplier\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validshipto\" NAME=\"validshipto\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validcarrier\" NAME=\"validcarrier\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validordertaker\" NAME=\"validordertaker\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"totallines\" NAME=\"totallines\" VALUE=\"0\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"numofHubs\" NAME=\"numofHubs\" VALUE=\"0\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"attachedpr\" NAME=\"attachedpr\" VALUE=\"N\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" name=\"nonintegerReceiving\" id=\"nonintegerReceiving\" VALUE=\"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"supplierDefaultPaymentTerms\" id=\"supplierDefaultPaymentTerms\" value=\"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"opsEntityId\" id=\"opsEntityId\" value=\"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"supplierCountryAbbrev\" id=\"supplierCountryAbbrev\" value=\"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"allowPOCreditConfirmaiton\" id=\"allowPOCreditConfirmaiton\" value=\""+allowPOCreditConfirmaiton+"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"allowPOFinancialConfirmaiton\" id=\"allowPOFinancialConfirmaiton\" value=\""+allowPOFinancialConfirmaiton+"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"currencyExchangeRate\" id=\"currencyExchangeRate\" value=\"1\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"isUSBuyer\" id=\"isUSBuyer\" value=\""+(!outsideUSAFYAudit?"Y":"N")+"\">\n");
      

      poout.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"922\" CLASS=\"moveup\">\n" );
	  poout.println( "</TABLE>\n" );

	  //open scrolling table
	  poout.println( "<TABLE CLASS=\"columnar\" WIDTH=\"900\" CELLSPACING=\"1\" CLASS=\"moveup\">\n" );
	  poout.println( "<TBODY>\n" );
	  poout.println( "<TR>\n" );
	  poout.println( "<TD VALIGN=\"TOP\">\n" );
	  poout.println( "<TR ALIGN=\"CENTER\">\n" );
	  poout.println( "<TH width=\"2%\"  height=\"38\">"+res.getString("label.lne")+"</TH>\n" );
	  poout.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.ghsBTcompliant")+"</TH>\n" );
	  poout.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.item")+"</TH>\n" );
	  poout.println( "<TH width=\"2%\"   height=\"38\">"+res.getString("label.tyBTpe")+"</TH>\n" );
	  poout.println( "<TH width=\"4%\"  height=\"38\">"+res.getString("label.needed")+"<br>("+res.getString("label.dateformat")+")</TH>\n" );
	  poout.println( "<TH width=\"6.5%\"  height=\"38\">"+res.getString("label.promisedship")+"<br>("+res.getString("label.dateformat")+")</TH>\n" );
	  poout.println( "<TH width=\"7%\"  height=\"38\">"+res.getString("label.projecteddelivery")+"<br>("+res.getString("label.dateformat")+")</TH>\n" );
	  poout.println( "<TH width=\"4%\"  height=\"38\">"+res.getString("label.qty")+"</TH>\n" );
	  poout.println( "<TH width=\"17%\" height=\"38\">"+res.getString("label.uom")+"</TH>\n" );
	  poout.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.unitprice")+"</TH>\n" );
	  poout.println( "<TH width=\"7%\" height=\"38\">"+res.getString("label.extprice")+"</TH>\n" );
	  poout.println( "<TH width=\"3%\"  NAME=\"qtyrechead\"   ID=\"qtyrechead\" height=\"38\">"+res.getString("label.qtyrec")+"</TH>\n" );
	  poout.println( "<TH width=\"3%\"  NAME=\"qtyvouchhead\" ID=\"qtyvouchhead\" height=\"38\">"+res.getString("label.qtyvoc")+"</TH>\n" );
	  poout.println( "<TH width=\"3%\"  NAME=\"qtyrtnedhead\" ID=\"qtyrtnedhead\"height=\"38\">"+res.getString("label.qtyrtn")+"</TH>\n" );
	  poout.println( "<TH width=\"4%\"  height=\"38\">"+res.getString("label.status")+"</TH>\n" );
	  poout.println( "</TR>\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );
	  poout.println( "<TD VALIGN=\"TOP\" COLSPAN=\"15\">\n" );
	  poout.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column75\">\n" );

	  //Write code to insert rows here
	  poout.println( "<TABLE ID=\"linetable\" BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"900\" CLASS=\"moveup\">\n" );

	  poout.println( "</TABLE>\n" );

	  //close scrolling table
	  poout.println( "</DIV>\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );
	  poout.println( "</TBODY>\n" );
	  poout.println( "</TABLE>\n" );


	  //Invisible Hidden elements in the page
	  poout.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  poout.println( "<tr><td>" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"UserAction\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
	  poout.println( "</TD></tr>" );
	  poout.println( "</table>\n" );
	  poout.println( "</P>\n" );
	  poout.println( "</FORM>\n" );
	  poout.println( "</BODY>\n" );
	  poout.println( "</HTML>\n" );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}

	//return Msg;
  }


  private void buildReadonlyHeader( String hub,Hashtable hub_list_set,Hashtable vvFob,String radianPo1,String message,String blanketPO,
											String userAction,String subUserAction,Vector vvPayment1,Vector vvCurrency,PrintWriter poout )
  {
	//StringBuffer Msg=new StringBuffer();
	String SelectedHubName="";

	try
	{
    poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "purchaseOrder","readonlypurchaseorder.js",intcmIsApplication,res ) );
    poout.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT SRC=\"/clientscripts/povalidation.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT SRC=\"/clientscripts/popopups.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT SRC=\"/clientscripts/associatedPrs.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  poout.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );

	  poout.println( "</HEAD>  \n" );

	  if ( !userAction.equalsIgnoreCase( "New" ) )
	  {
		if ( "newbpo".equalsIgnoreCase( subUserAction ) )
		{
		  poout.println( "<BODY onLoad=\"recalnewPo('bpo')\">\n" );
		}
		else if ( "newpo".equalsIgnoreCase( subUserAction ) )
		{
		  poout.println( "<BODY onLoad=\"recalnewPo('po')\">\n" );
		}
		else if ( radianPo1.length() > 0 || "po".equalsIgnoreCase( subUserAction ) )
		{
		  poout.println( "<BODY onLoad=\"recalPo('" + radianPo1 + "','','po','" + hub + "')\">\n" );
		}
		else if ( blanketPO.length() > 0 || "bpo".equalsIgnoreCase( subUserAction ) )
		{
		  poout.println( "<BODY onLoad=\"recalPo('" + blanketPO + "','','bpo','" + hub + "')\">\n" );
		}
		else
		{
		  poout.println( "<BODY onLoad=\"recalPo('" + radianPo1 + "','','po','" + hub + "')\">\n" );
		}
	  }
	  else
	  {
		poout.println( "<BODY>\n" );
	  }

	  poout.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
	  poout.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT></center>\n" );
	  poout.println( "</DIV>\n" );
	  poout.println( "<DIV ID=\"MAINPAGE\">\n" );

	  poout.println( radian.web.HTMLHelpObj.printPurchaseOrderTitleBar( "<B>"+res.getString("purchaseOrder")+"</B>\n" ) );
	  poout.println( "<FORM method=\"POST\" NAME=\"purchaseorder\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString( "PURCHASE_ORDER" ) + "\">\n" );
	  poout.println( "<P ID=\"mainpara\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" ID=\"Action\" VALUE=\"Details\">\n" );
	  poout.println( "<TABLE BORDER=\"0\" ID=\"mainheadertable\" CELLSPACING=\"0\" CELLPADDING=\"1\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  poout.println( "<TR>\n" );
	  poout.println( "<TD WIDTH=\"100%\" COLSPAN=\"6\" HEIGHT=\"5\" ALIGN=\"CENTER\" ID=\"messagerow\"><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>" + message + "</B></FONT></TD>\n" );
	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );
	  //Supplier Line 1
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.supplier")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" ID=\"supplierline1\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Hub
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.hub")+":</B>&nbsp;\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"26%\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"HubFullName\" ID=\"HubFullName\" value=\"\">\n" );
	  poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" ID=\"HubName\" size=\"1\" DISABLED>\n" );
	  poout.println( "<OPTION VALUE=\"None\">"+res.getString("label.pleaseselect")+"</OPTION>\n" );
	  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
	  String hub_selected;
	  for ( Enumeration e=hub_list.keys(); e.hasMoreElements(); )
	  {
		String branch_plant= ( String ) ( e.nextElement() );
		String hub_name= ( String ) ( hub_list.get( branch_plant ) );
		//
		hub_selected="";
		if ( branch_plant.equalsIgnoreCase( hub ) )
		{
		  hub_selected="selected";
		  SelectedHubName=hub_name;
		}
		poout.println( "<option  " + hub_selected + " value=\"" + branch_plant + "\">" + hub_name + "</option>\n" );
	  }
	  poout.println( "</SELECT>\n" );
	  poout.println( "</TD>\n" );

	  //PO
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.po")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"po\" ID=\"po\" value=\""+radianPo1+"\" onChange=\"invalidatePo()\" size=\"8\">\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchpolike\" name=\"searchpolike\" value=\"...\" OnClick=\"getPo(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\"PO\"></BUTTON>\n" );
	  poout.println( "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Newpo\" id=\"Newpo\" value=\""+res.getString("label.new")+"\" STYLE=\"display: none;\" OnClick=\"getnewPo(this.form)\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );

	  //Supplier Line 2
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"supplierid\" ID=\"supplierid\" value=\"\" onChange=\"invalidateSupplier()\" size=\"4\" readonly>\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchsupplierlike\" name=\"searchsupplierlike\" value=\"...\" STYLE=\"display: none;\" OnClick=\"getSupplier(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.supplier")+"\"></BUTTON>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" ID=\"supplierline2\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //FOB
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.tradeTerms")+":</B>&nbsp;\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"26%\">\n" );
	  poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"fob\" ID=\"fob\" size=\"1\" DISABLED>\n" );
	  poout.println( "<OPTION VALUE=\"None\">"+res.getString("label.pleaseselect")+"</OPTION>\n" );
	  //poout.println( radian.web.HTMLHelpObj.getDropDown( vvFob,"" ) );
	  poout.println( radian.web.HTMLHelpObj.sorthashbyValue( vvFob.entrySet(),"" ) );
	  poout.println( "</SELECT>\n" );
	  poout.println( "</TD>\n" );

	  //BPO
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.bo")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVALIDTEXT\" TYPE=\"text\" NAME=\"bpo\" ID=\"bpo\" value=\""+blanketPO+"\" onChange=\"invalidatebPo()\" size=\"8\">\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchbpolike\" name=\"searchbpolike\" value=\"...\" OnClick=\"getbpo(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.bpo")+"\"></BUTTON>\n" );
	  poout.println( "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Newbpo\" id=\"Newbpo\" value=\""+res.getString("label.new")+"\" STYLE=\"display: none;\" OnClick=\"getnewbpo(this.form)\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );

	  //Supplier Line 3
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"suppRating\" ID=\"suppRating\" value=\"\" size=\"8\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" ID=\"supplierline3\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Carrier Line 1
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.shipping")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"carrierline1\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Buyer
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.buyer")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"buyer\" ID=\"buyer\" value=\"\" size=\"12\" readonly>\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"buyerid\" ID=\"buyerid\" value=\"\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );

	  //Supplier Line 4
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" ID=\"supplierline4\" VALIGN=\"TOP\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Carrier Line 2
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"carrieraccount\" ID=\"carrieraccount\" VALUE=\"\">\n<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"carrierID\" ID=\"carrierID\" value=\"\" onChange=\"invalidateCarrier()\" size=\"4\" readonly>\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchcarrierlike\" name=\"searchcarrierlike\" value=\"...\" STYLE=\"display: none;\" OnClick=\"getcarrier(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.carrier")+"\"></BUTTON>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"carrierline2\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Order Taker
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.ordertaker")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"ordertakerID\" ID=\"ordertakerID\" value=\"\" readonly>\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"ordertaker\" ID=\"ordertaker\" value=\"\" onChange=\"invalidateOrderTaker()\" size=\"8\" readonly>\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchordertakerlike\" name=\"searchordertakerlike\" value=\"...\" STYLE=\"display: none;\" OnClick=\"getorderTaker(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\""+res.getString("label.ordertaker")+"\"></BUTTON>\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );

	  poout.println( "<TR><TD COLSPAN=\"2\" ALIGN=\"LEFT\" HEIGHT=\"5\">"+res.getString("label.phonenumber")+":&nbsp;<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"mfgphoneno\" ID=\"mfgphoneno\" value=\"\" size=\"20\" readonly></TD>\n" );
	  //poout.println( "<TD ALIGN=\"LEFT\" HEIGHT=\"5\"><INPUT CLASS=\"WHITEHEADER\"  TYPE=\"checkbox\" NAME=\"criticalpo\" ID=\"criticalpo\" value=\"Yes\" DISABLED>&nbsp;&nbsp;<B>Critical</B></TD>\n" );
	  poout.println( "<TD ALIGN=\"LEFT\" HEIGHT=\"5\">\n" );
	  poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"criticalpo\" ID=\"criticalpo\" size=\"1\" DISABLED>\n" );
	  poout.println( "<OPTION VALUE=\"N\">"+res.getString("label.N")+"</OPTION>\n" );
	  poout.println( "<OPTION VALUE=\"Y\">"+res.getString("label.Y")+"</OPTION>\n" );
	  poout.println( "<OPTION VALUE=\"S\">"+res.getString("label.S")+"</OPTION>\n" );
	  poout.println( "</SELECT>\n" );
	  poout.println( "<B>"+res.getString("label.critical")+"</B></TD>\n" );
	  poout.println( "<TD COLSPAN=\"3\" ALIGN=\"RIGHT\" HEIGHT=\"5\">"+res.getString("label.phonenumber")+":&nbsp;<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"contactphoneno\" ID=\"contactphoneno\" value=\"\" size=\"15\" readonly>\n" );
	  poout.println( res.getString("label.fax")+":&nbsp;<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"contactfaxno\" ID=\"contactfaxno\" value=\"\" size=\"15\" readonly></TD></TR>\n" );

	  poout.println( "<TR>\n" );
	  //Supplier Email
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\">\n" );
	  poout.println( res.getString("label.email")+":\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"supplieremail\">\n" );
	  poout.println( "</TD>\n" );

	  //Blank
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>&nbsp;</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "&nbsp;\n" );
	  poout.println( "</TD>\n" );

	  //Supplier Contact Email
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\">\n" );
	  poout.println( res.getString("label.email")+":\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" ID=\"suppliercontactemail\">\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );
	  //Ship To Line 1
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.shipto")+":</B>\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline1\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Fax Date
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.dateconfirmed")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"faxdate\" ID=\"faxdate\" value=\"\" size=\"8\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  //Customer PO
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.customerpo")+":</B>\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"customerpo\" ID=\"customerpo\" value=\"\" size=\"20\" readonly>\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );

	  //Ship To Line 2
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" NAME=\"shiptocompanyid\" ID=\"shiptocompanyid\" VALUE=\"\">\n<INPUT TYPE=\"hidden\" NAME=\"shipToFacilityId\" ID=\"shipToFacilityId\" VALUE=\"\">\n<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"shiptoid\" ID=\"shiptoid\" value=\"\" onChange=\"invalidateShipTo()\" size=\"4\" readonly>\n" );
	  poout.println( "<BUTTON TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchshiptolike\" value=\"...\" STYLE=\"display: none;\" OnClick=\"getshipTo(this.form,'')\"><IMG src=\"/images/search_small.gif\" alt=\"Ship To\"></BUTTON>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline2\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //Accepted Date
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.dateaccepted")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"accepteddate\" ID=\"accepteddate\" value=\"\" size=\"8\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  //PO NOTES
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT TYPE=\"button\" style=\"width:100px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.scratchPad")+"\" onclick= \"showPonotes()\" NAME=\"ponotes\" ID=\"ponotes\">&nbsp;\n" );
	  poout.println( "<INPUT TYPE=\"button\" style=\"width:80px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.emailpad")+"\" onclick= \"showemailnotes()\" NAME=\"emailponotes\" ID=\"emailponotes\">&nbsp;\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );
	  poout.println( "<TR>\n" );

	  //Ship To Line 3
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.invengroup")+":<B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline3\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //BO Start Date
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.boStartDate")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"bostartdate\" ID=\"bostartdate\" value=\"\" size=\"8\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  //Payment Terms
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.paymentterms")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  /*if ( this.getPaytermsupdateStatus() )
	  {
		poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"paymentterms\" ID=\"paymentterms\" size=\"1\">\n" );
		poout.println( "<OPTION VALUE=\"None\">Please Select</OPTION>\n" );
		poout.println( radian.web.HTMLHelpObj.getDropDown( vvPayment1,"" ) );
		poout.println( "</SELECT>\n" );
	  }
	  else*/
	  {
		poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"paymentterms\" ID=\"paymentterms\" value=\"\" size=\"10\" readonly>\n" );
	  }
	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );
	  //Ship To Line 4
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invengrp\" ID=\"invengrp\" value=\"\" size=\"10\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline4\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  //BO End Date
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.boEndDate")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"boenddate\" ID=\"boenddate\" value=\"\" size=\"8\" readonly>\n" );

	  poout.println( "</TD>\n" );

	  //Consigned PO
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"checkbox\" value=\"Yes\" NAME=\"consignedpo\" ID=\"consignedpo\" DISABLED>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.consignedpo")+"</B>\n");
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );

	  //Ship To Line 5
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" VALIGN=\"TOP\" CLASS=\"announce\">\n" );
	  poout.println( "&nbsp;\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" VALIGN=\"TOP\" ID=\"shiptoline5\" CLASS=\"texareaannounce\">\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.currency")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"26%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  /*if ( this.getallowCurrencyupdate() )
	  {
		poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"currency\" ID=\"currency\" size=\"1\">\n" );
		poout.println( "<OPTION VALUE=\"None\">Please Select</OPTION>\n" );
		poout.println( radian.web.HTMLHelpObj.getDropDown( vvCurrency,"" ) );
		poout.println( "</SELECT>\n" );
	  }
	  else*/
	  {
		poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\"  TYPE=\"text\" NAME=\"currency\" ID=\"currency\" value=\"\" size=\"5\" readonly>\n" );
	  }
	  poout.println( "</TD>\n" );


	  //Total Amount
	  poout.println( "<TD WIDTH=\"3%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  poout.println( "<B>"+res.getString("label.totalamount")+":</B>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  poout.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"totalcost\" ID=\"totalcost\" value=\"\" size=\"10\" readonly>\n" );
	  poout.println( "</TD>\n" );

	  poout.println( "</TR>\n" );

	  if(!readonly && !nopermissions)
	  {
		  //Financial Approval
		  poout.println( "<TR>\n" );
		  poout.println( "<TD WIDTH=\"10%\" CLASS=\"announce\"  ALIGN=\"CENTER\" COLSPAN=\"6\"  VALIGN=\"MIDDLE\">\n" );
		  poout.println( "&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" style=\"display:none\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.financialapproval")+"\" onclick= \"return actionValue(name,this)\" NAME=\"financialApproval\" id=\"financialApproval\">&nbsp;\n" );
		  poout.println( "</TD>\n" );
		  poout.println( "</TR>\n" );
	  }
	  
	  poout.println( "</TABLE>\n" );
	  
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validpo\" NAME=\"validpo\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validbpo\" NAME=\"validbpo\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validsupplier\" NAME=\"validsupplier\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validshipto\" NAME=\"validshipto\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validcarrier\" NAME=\"validcarrier\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"validordertaker\" NAME=\"validordertaker\" VALUE=\"No\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"totallines\" NAME=\"totallines\" VALUE=\"0\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"numofHubs\" NAME=\"numofHubs\" VALUE=\"0\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"attachedpr\" NAME=\"attachedpr\" VALUE=\"N\">\n" );
	  poout.println( "<INPUT TYPE=\"hidden\" name=\"nonintegerReceiving\" id=\"nonintegerReceiving\" VALUE=\"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"supplierDefaultPaymentTerms\" id=\"supplierDefaultPaymentTerms\" value=\"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"opsEntityId\" id=\"opsEntityId\" value=\"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"supplierCountryAbbrev\" id=\"supplierCountryAbbrev\" value=\"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"allowPOCreditConfirmaiton\" id=\"allowPOCreditConfirmaiton\" value=\""+allowPOCreditConfirmaiton+"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"allowPOFinancialConfirmaiton\" id=\"allowPOFinancialConfirmaiton\" value=\""+allowPOFinancialConfirmaiton+"\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"currencyExchangeRate\" id=\"currencyExchangeRate\" value=\"1\">\n" );
      poout.println( "<INPUT TYPE=\"hidden\" name=\"isUSBuyer\" id=\"isUSBuyer\" value=\""+(!outsideUSAFYAudit?"Y":"N")+"\">\n" );
      
      poout.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"922\" CLASS=\"moveup\">\n" );

	  poout.println( "</TABLE>\n" );

	  //open scrolling table
	  poout.println( "<TABLE CLASS=\"columnar\" WIDTH=\"900\" CELLSPACING=\"1\" CLASS=\"moveup\">\n" );
	  poout.println( "<TBODY>\n" );

	  poout.println( "<TR>\n" );
	  poout.println( "<TD VALIGN=\"TOP\">\n" );
	  poout.println( "<TR ALIGN=\"CENTER\">\n" );
	  poout.println( "<TH width=\"2%\"  height=\"38\">"+res.getString("label.lne")+"</TH>\n" );
	  poout.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.ghsBTcompliant")+"</TH>\n" );
	  poout.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.item")+"</TH>\n" );
	  poout.println( "<TH width=\"2%\"   height=\"38\">"+res.getString("label.tyBTpe")+"</TH>\n" );
	  poout.println( "<TH width=\"4%\"  height=\"38\">"+res.getString("label.needed")+"<br>("+res.getString("label.dateformat")+")</TH>\n" );
	  poout.println( "<TH width=\"6.5%\"  height=\"38\">"+res.getString("label.promisedship")+"<br>("+res.getString("label.dateformat")+")</TH>\n" );
	  poout.println( "<TH width=\"7%\"  height=\"38\">"+res.getString("label.projecteddelivery")+"<br>("+res.getString("label.dateformat")+")</TH>\n" );
	  poout.println( "<TH width=\"4%\"  height=\"38\">"+res.getString("label.qty")+"</TH>\n" );
	  poout.println( "<TH width=\"17%\" height=\"38\">"+res.getString("label.uom")+"</TH>\n" );
	  poout.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.unitprice")+"</TH>\n" );
	  poout.println( "<TH width=\"7%\" height=\"38\">"+res.getString("label.extprice")+"</TH>\n" );
	  poout.println( "<TH width=\"3%\"  NAME=\"qtyrechead\"   ID=\"qtyrechead\" height=\"38\">"+res.getString("label.qtyrec")+"</TH>\n" );
	  poout.println( "<TH width=\"3%\"  NAME=\"qtyvouchhead\" ID=\"qtyvouchhead\" height=\"38\">"+res.getString("label.qtyvoc")+"</TH>\n" );
	  poout.println( "<TH width=\"3%\"  NAME=\"qtyrtnedhead\" ID=\"qtyrtnedhead\"height=\"38\">"+res.getString("label.qtyrtn")+"</TH>\n" );
	  poout.println( "<TH width=\"4%\"  height=\"38\">"+res.getString("label.status")+"</TH>\n" );
	  poout.println( "</TR>\n" );

	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );

	  poout.println( "<TR>\n" );
	  poout.println( "<TD VALIGN=\"TOP\" COLSPAN=\"15\">\n" );
	  poout.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column75\">\n" );

	  //Write code to insert rows here
	  poout.println( "<TABLE ID=\"linetable\" BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"900\" CLASS=\"moveup\">\n" );

	  poout.println( "</TABLE>\n" );

	  //close scrolling table    
	  poout.println( "</DIV>\n" );
	  poout.println( "</TD>\n" );
	  poout.println( "</TR>\n" );
	  poout.println( "</TBODY>\n" );
	  poout.println( "</TABLE>\n" );

	  //Invisible Hidden elements in the page
	  poout.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  poout.println( "<tr><td>" );
	  poout.println( "<INPUT TYPE=\"hidden\" ID=\"UserAction\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
	  poout.println( "</TD></tr>" );
	  poout.println( "</table>\n" );
	  poout.println( "</P>\n" );
	  poout.println( "</FORM>\n" );
	  poout.println( "</BODY>\n" );
	  poout.println( "</HTML>\n" );
	}
	catch ( Exception e )
	{
	  //e.printStackTrace();
	}

	//return Msg;
  }

  private Hashtable updatePoDetails( Hashtable in_data,String personnelID,String action )
  {
	Hashtable new_data=new Hashtable();
	String po= "";
    String supplierId= "";
    String interCompMrNumber = "";
  Connection connecPo = null;
	try
	{
	  Hashtable headerData=new Hashtable();
	  connecPo=db.getConnection();
	  connecPo.setAutoCommit( false );
	  headerData= ( Hashtable ) in_data.get( "HEADERDATA" );
	  po=headerData.get( "PO" ).toString().trim();
    supplierId=headerData.get( "SUPPLIERID" ).toString().trim();
    Vector lineData=new Vector();
	  lineData= ( Vector ) in_data.get( "LINEDETAILS" );

	  boolean line_result=false;
	  line_result=updatePoHeader( headerData,personnelID,connecPo );

	  if ( false == line_result )
	  {
		completeSuccess=false;
	  }

	  for ( int i=0; i < lineData.size(); i++ )
	  {
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) lineData.elementAt( i );
        Hashtable poLineUpdateResult  = updatePoLine( hD,personnelID,po,"",connecPo );
        line_result= ((Boolean) poLineUpdateResult.get("LINE_RESULT")).booleanValue();
        interCompMrNumber = (String) poLineUpdateResult.get("INTER_COMPANY_MR");
        if ( false == line_result )
		{
		  completeSuccess=false;
		}
	  }

	  for ( int i=0; i < lineData.size(); i++ )
	  {
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) lineData.elementAt( i );

		line_result=updateLineAddCharges( hD,po,"po",connecPo );
		if ( false == line_result )
		{
		  completeSuccess=false;
		}
	  }

	  for ( int i=lineData.size(); i > 0; i-- )
	  {
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) lineData.elementAt( ( i - 1 ) );

		line_result=deletePoLine( hD,po,connecPo );
		if ( false == line_result )
		{
		  completeSuccess=false;
		}
	  }

		  if ( "confirm".equalsIgnoreCase( action ) )
		  {
			  
            //TCMISDEV-207 Catalyst B - Procedure to check for financial approval tree for POs
			String results = "";
	        CallableStatement cs = connecPo.prepareCall("{call pkg_expert_review.p_po_expert_review (?,?,?)}");
	        cs.setString(1,po);
	        cs.setString(2,personnelID);
            cs.registerOutParameter(3,java.sql.Types.VARCHAR);
	        cs.execute();
	        results = cs.getString(3);
            polog.info("p_po_expert_review  "+personnelID+ " PO "+po+" results: "+results+"");

            if(results != null && results.equalsIgnoreCase("ok"))
	        {
			  
            /*Confirm all material lines first*/
	        for ( int i=0; i < lineData.size(); i++ )
			{
			  Hashtable hD=new Hashtable();
			  hD= ( Hashtable ) lineData.elementAt( i );
	
	          String itemtype = hD.get("ITEMTYPE").toString().trim();
	          if ( !addchargeslist.contains( itemtype ))
	          {
	            //line_result=updatePoLine( hD,personnelID,po,"confirm",connecPo );
	            Hashtable poLineUpdateResult  = updatePoLine( hD,personnelID,po,"confirm",connecPo );
	            line_result= ((Boolean) poLineUpdateResult.get("LINE_RESULT")).booleanValue();
	            interCompMrNumber = (String) poLineUpdateResult.get("INTER_COMPANY_MR");
	          }
	          if ( false == line_result )
			  {
				completeSuccess=false;
			  }
			}
	        /*Confirm all additional charge lines later.*/
	        for ( int i=0; i < lineData.size(); i++ )
			{
			  Hashtable hD=new Hashtable();
			  hD= ( Hashtable ) lineData.elementAt( i );
	
	          String itemtype = hD.get("ITEMTYPE").toString().trim();
	          if ( addchargeslist.contains( itemtype ))
	          {
	            Hashtable poLineUpdateResult  = updatePoLine( hD,personnelID,po,"confirm",connecPo );
	            line_result= ((Boolean) poLineUpdateResult.get("LINE_RESULT")).booleanValue();
	            //interCompMrNumber = (String) poLineUpdateResult.get("INTER_COMPANY_MR");
	            //line_result=updatePoLine( hD,personnelID,po,"confirm",connecPo );
	          }
	          if ( false == line_result )
			  {
				completeSuccess=false;
			  }
			}
	        //TCMISDEV-207 Catalyst B - Procedure to check for financial approval tree for POs
            }
            else
            {
            	if(results != null)
            		errorFromProcedure +="!ok"+results;
            	else 
            		errorFromProcedure +="!ok"+res.getString("msg.errconf");  
               completeSuccess=false;
            }
          }

	  connecPo.commit();

	  if ( "confirm".equalsIgnoreCase( action ) )
	  {
		for ( int i=0; i < lineData.size(); i++ )
		{
		  Hashtable hD=new Hashtable();
		  hD= ( Hashtable ) lineData.elementAt( i );

		  line_result=updateDeliveryDelayEmail( hD,po,"confirm",connecPo );
		}
	  }
	  connecPo.commit();

    if ( "confirm".equalsIgnoreCase( action ) )
	  {
		  allocatePO( po,connecPo );
	  }
	  connecPo.commit();
    
    for ( int i=lineData.size(); i > 0; i-- )
	  {
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) lineData.elementAt( ( i - 1 ) );

		Vector lookaheadlines= ( Vector ) hD.get( "LOOKAHEADLINEDETAILS" );
		String lookaheadchanged=hD.get( "LOOKAHEADCHANGED" ).toString().trim();
		String nooflookaheads=hD.get( "NOOFLOOKAHEADS" ).toString().trim();
		if ( Integer.parseInt( nooflookaheads ) > 0 && "Yes".equalsIgnoreCase( lookaheadchanged ) )
		{
		  updatelookaheads( lookaheadlines,connecPo );
		}
	  }
	  connecPo.commit();
	}
	catch ( Exception e )
	{
	  completeSuccess=false;
	  //e.printStackTrace();
    String erroMessage =e.getMessage();
    if (erroMessage.indexOf("constraint") > 0)
    {
      String[] supportEmails = new String[]{"asepulveda@haastcm.com,mboze@haastcm.com,mblackburn@haastcm.com,dbertero@haastcm.com"};
      HelpObjs.javaSendMail("","Error PO Page",supportEmails,"Error Msg:\n"+e.getMessage()+"\n  radian PO  "+po+" \n personnelID "+personnelID+"");
    }
    radian.web.emailHelpObj.sendnawazemail("Error PO Page ","Error Msg:\n"+e.getMessage()+"\n  radian PO  "+po+" \n personnelID "+personnelID+"");
	}
	finally
	{
	  try
	  {
		connecPo.commit();
		connecPo.setAutoCommit( true );
	  }
	  catch ( SQLException ex )
	  {
		//ex.printStackTrace();
	  }
	}
    polog.info("interCompMrNumber Final  "+interCompMrNumber);
    new_data.put("INTER_COMPANY_MR",interCompMrNumber);         
	return new_data;
  }

  private boolean allocatePO (String radianPo,Connection con )
  {
	boolean result=false;
	CallableStatement pstmt=null;

	try
	{
		pstmt=con.prepareCall( "{call p_po_allocate (?)}" );
		BigDecimal bigponumber = new BigDecimal(radianPo);
		pstmt.setBigDecimal( 1,bigponumber );
		pstmt.execute();
		result=true;
    polog.info("call p_po_allocate - "+radianPo);
	}
	catch ( Exception e )
	{
	  result=false;
	  //e.printStackTrace();
	  radian.web.emailHelpObj.sendnawazemail("Error Calling p_po_allocate PO Page ","Error p_po_allocate\nError Msg:\n"+e.getMessage()+"\n  radian PO  "+radianPo+"");
	}
	finally
	{
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

	return result;
  }

  private Hashtable updatebPoDetails( Hashtable in_data,String personnelID,String action )
  {
	Hashtable new_data=new Hashtable();
	String bpo = "";
	Connection connecPo = null;
	try
	{
	  Hashtable headerData=new Hashtable();
	  connecPo=db.getConnection();
	  connecPo.setAutoCommit( false );
	  headerData= ( Hashtable ) in_data.get( "HEADERDATA" );
	  bpo=headerData.get( "BPO" ).toString().trim();

	  Vector lineData=new Vector();
	  lineData= ( Vector ) in_data.get( "LINEDETAILS" );

	  boolean line_result=false;
	  line_result=updatebPoHeader( headerData,personnelID,connecPo );

	  if ( false == line_result )
	  {
		completeSuccess=false;
	  }

	  for ( int i=0; i < lineData.size(); i++ )
	  {
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) lineData.elementAt( i );

		line_result=updatebPoLine( hD,bpo,"",connecPo );
		if ( false == line_result )
		{
		  completeSuccess=false;
		}
	  }

	  for ( int i=0; i < lineData.size(); i++ )
	  {
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) lineData.elementAt( i );

		line_result=updateLineAddCharges( hD,bpo,"bpo",connecPo );
		if ( false == line_result )
		{
		  completeSuccess=false;
		}
	  }

	  for ( int i=lineData.size(); i > 0; i-- )
	  {
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) lineData.elementAt( ( i - 1 ) );

		line_result=deletebPoLine( hD,bpo,connecPo );
		if ( false == line_result )
		{
		  completeSuccess=false;
		}
	  }


	  if ( "confirm".equalsIgnoreCase( action ) )
	  {
		for ( int i=0; i < lineData.size(); i++ )
		{
		  Hashtable hD=new Hashtable();
		  hD= ( Hashtable ) lineData.elementAt( i );

		  line_result=updatebPoLine( hD,bpo,"confirm",connecPo );
		  if ( false == line_result )
		  {
			completeSuccess=false;
		  }
		}
	  }

	  connecPo.commit();
	}
	catch ( Exception e )
	{
	  completeSuccess=false;
	  //e.printStackTrace();
    String erroMessage =e.getMessage();
    if (erroMessage.indexOf("constraint") > 0)
    {
      String[] supportEmails = new String[]{"asepulveda@haastcm.com,mboze@haastcm.com,mblackburn@haastcm.com,dbertero@haastcm.com"};
      HelpObjs.javaSendMail("","Error PO Page",supportEmails,"Error Msg:\n"+e.getMessage()+"\n radian PO  "+bpo+" \n personnelID "+personnelID+"");
    }
    radian.web.emailHelpObj.sendnawazemail("Error PO Page ","Error Msg:\n"+e.getMessage()+"\n radian BPO  "+bpo+" ");
	}
	finally
	{
	  try
	  {
		connecPo.commit();
		connecPo.setAutoCommit( true );
	  }
	  catch ( SQLException ex )
	  {
		//ex.printStackTrace();
	  }
	}

	return new_data;
  }

  private boolean updatePoHeader( Hashtable DataH,String personnelID,Connection con )
  {
	boolean result=false;
	CallableStatement pstmt=null;
	String po ="";

	try
	{
	  //String boenddate = DataH.get("BOENDDATE").toString().trim();
	  String customerpo=DataH.get( "CUSTOMERPO" ).toString().trim();
	  String carrierid=DataH.get( "CARRIERID" ).toString().trim();
	  String bpo=DataH.get( "BPO" ).toString().trim();
	  //String bostartdate = DataH.get("BOSTARTDATE").toString().trim();
	  String hub=DataH.get( "HUB" ).toString().trim();
	  String supplierid=DataH.get( "SUPPLIERID" ).toString().trim();
	  String shiptocompanyid=DataH.get( "SHIPTOCOMPANYID" ).toString().trim();
		String shipToFacilityId=DataH.get( "SHIPTOFACILITYID" ).toString().trim();
	  String paymentterms=DataH.get( "PAYMENTTERMS" ).toString().trim();
	  //String carrieraccount = DataH.get("CARRIERACCOUNT").toString().trim();
	  //String ordertaker = DataH.get("ORDERTAKER").toString().trim();
	  po=DataH.get( "PO" ).toString().trim();
	  String ordertakerid=DataH.get( "ORDERTAKERID" ).toString().trim();
	  String fob=DataH.get( "FOB" ).toString().trim();
	  String shipto=DataH.get( "SHIPTO" ).toString().trim();
	  if ( shipto.length() > 0 )
	  {
		shipto=HelpObjs.findReplace( shipto,"%26","&" );
		shipto=HelpObjs.findReplace( shipto,"%23","#" );
        shipto=HelpObjs.findReplace( shipto,"%2F","\\" );
      }
      polog.info("shipto  "+shipto);
      String faxdate=DataH.get( "FAXDATE" ).toString().trim();
	  String accepteddate=DataH.get( "ACCEPTEDDATE" ).toString().trim();
	  String buyerid=DataH.get( "BUYERID" ).toString().trim();
	  String criticalPo=DataH.get( "CRITICALPO" ).toString().trim();
	  String suppRating=DataH.get( "SUPPRATING" ).toString().trim();
	  String invengrp=DataH.get( "INVENTORY_GROUP" ).toString().trim();
	  String cosignedpo=DataH.get( "CONSIGNED_PO" ).toString().trim();

	  pstmt=con.prepareCall( "{call pkg_po.PO_HEADER (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );

	  BigDecimal bigponumber = new BigDecimal(po);
	  pstmt.setBigDecimal( 1,bigponumber );
	  if ( bpo.length() > 0 )
	  {
		BigDecimal bigbponumber = new BigDecimal(bpo);
		pstmt.setBigDecimal( 2,bigbponumber );
	  }
	  else
	  {
		pstmt.setNull( 2,java.sql.Types.INTEGER );
	  }

	  if ( supplierid.length() > 0 )
	  {
		pstmt.setString( 3,supplierid );
	  }
	  else
	  {
		pstmt.setNull( 3,java.sql.Types.VARCHAR );
	  }

	  if ( ordertakerid.length() > 0 )
	  {
		pstmt.setInt( 4,Integer.parseInt( ordertakerid ) );
	  }
	  else
	  {
		pstmt.setNull( 4,java.sql.Types.INTEGER );
	  }
	  if ( buyerid.length() > 0 )
	  {
		pstmt.setInt( 5,Integer.parseInt( buyerid ) );
	  }
	  else
	  {
		pstmt.setInt( 5,Integer.parseInt( personnelID ) );
	  }

	  if ( hub.length() > 0 )
	  {
		pstmt.setString( 6,hub );
	  }
	  else
	  {
		pstmt.setNull( 6,java.sql.Types.VARCHAR );
	  }

	  if ( shiptocompanyid.length() > 0 )
	  {
		pstmt.setString( 7,shiptocompanyid );
	  }
	  else
	  {
		pstmt.setNull( 7,java.sql.Types.VARCHAR );
	  }

	  if ( shipto.length() > 0 )
	  {
		pstmt.setString( 8,shipto );
	  }
	  else
	  {
		pstmt.setNull( 8,java.sql.Types.VARCHAR );
	  }

	  if ( fob.length() > 0 )
	  {
		pstmt.setString( 9,fob );
	  }
	  else
	  {
		pstmt.setNull( 9,java.sql.Types.VARCHAR );
	  }

	  if ( paymentterms.length() > 0 )
	  {
		pstmt.setString( 10,paymentterms );
	  }
	  else
	  {
		pstmt.setNull( 10,java.sql.Types.VARCHAR );
	  }

	  if ( carrierid.length() > 0 )
	  {
		pstmt.setString( 11,carrierid );
	  }
	  else
	  {
		pstmt.setNull( 11,java.sql.Types.VARCHAR );
	  }

	  if ( faxdate.length() == 10 )
	  {
		pstmt.setTimestamp( 12,radian.web.HTMLHelpObj.getDateFromString( faxdate ) );
	  }
	  else
	  {
		pstmt.setNull( 12,java.sql.Types.DATE );
	  }
	  if ( accepteddate.length() == 10 )
	  {
		pstmt.setTimestamp( 13,radian.web.HTMLHelpObj.getDateFromString( accepteddate ) );
	  }
	  else
	  {
		pstmt.setNull( 13,java.sql.Types.DATE );
	  }

	  pstmt.setNull( 14,java.sql.Types.VARCHAR );
	  pstmt.setInt( 15,Integer.parseInt( personnelID ) );
	  if ( customerpo.length() > 0 )
	  {
		pstmt.setString( 16,customerpo );
	  }
	  else
	  {
		pstmt.setNull( 16,java.sql.Types.VARCHAR );
	  }

	  if ( !"N".equalsIgnoreCase( criticalPo ) &&  criticalPo.length() > 0)
	  {
		pstmt.setString( 17,criticalPo );
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

	  if ( "Yes".equalsIgnoreCase(cosignedpo) )
	  {
		pstmt.setString( 19,"y" );
	  }
	  else
	  {
		pstmt.setString( 19,"n" );
	  }

	  pstmt.registerOutParameter( 20,java.sql.Types.VARCHAR );

	  if ( suppRating.length() > 0 )
	  {
		pstmt.setString( 21,suppRating );
	  }
	  else
	  {
		pstmt.setNull( 21,java.sql.Types.VARCHAR );
	  }

	 if ( shipToFacilityId.length() > 0 )
	 {
		pstmt.setString( 22,shipToFacilityId );
	 }
	 else
	 {
		pstmt.setNull( 22,java.sql.Types.VARCHAR );
	 }

	  pstmt.execute();

	  String errorcode=pstmt.getString( 20 );

	  if ( errorcode == null )
	  {
		result=true;
	  }
	  else
	  {
		 polog.info( "Error from pkg_po.PO_HEADER " + errorcode + "  supplierid  " + supplierid + "  customerpo  " + customerpo );
		 errorFromProcedure+=errorcode;
		 result=false;
	  }

	}
	catch ( Exception e )
	{
	  result=false;
	  //e.printStackTrace();
	  radian.web.emailHelpObj.sendnawazemail("Error Calling pkg_po.PO_HEADER in PO Page ","Error Calling pkg_po.PO_HEADER\nError Msg:\n"+e.getMessage()+"\n  radian PO  "+po+"\n personnelID "+personnelID+"");
	}
	finally
	{
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

	return result;
  }

  private boolean updatebPoHeader( Hashtable DataH,String personnelID,Connection con )
  {
	boolean result=false;
	CallableStatement pstmt=null;
	String bpo = "";

	try
	{
	  String boenddate=DataH.get( "BOENDDATE" ).toString().trim();
	  String customerpo = DataH.get("CUSTOMERPO").toString().trim();
	  String carrierid = DataH.get("CARRIERID").toString().trim();
	  bpo=DataH.get( "BPO" ).toString().trim();
	  String bostartdate=DataH.get( "BOSTARTDATE" ).toString().trim();
	  String hub = DataH.get("HUB").toString().trim();
	  String supplierid=DataH.get( "SUPPLIERID" ).toString().trim();
	  String shiptocompanyid=DataH.get( "SHIPTOCOMPANYID" ).toString().trim();
		String shipToFacilityId=DataH.get( "SHIPTOFACILITYID" ).toString().trim();
	  String paymentterms=DataH.get( "PAYMENTTERMS" ).toString().trim();
	  String carrieraccount = DataH.get("CARRIERACCOUNT").toString().trim();
	  //String ordertaker = DataH.get("ORDERTAKER").toString().trim();
	  //String po = DataH.get("PO").toString().trim();
	  String ordertakerid=DataH.get( "ORDERTAKERID" ).toString().trim();
	  String fob=DataH.get( "FOB" ).toString().trim();
	  String shipto=DataH.get( "SHIPTO" ).toString().trim();
	  if ( shipto.length() > 0 )
	  {
		shipto=HelpObjs.findReplace( shipto,"%26","&" );
		shipto=HelpObjs.findReplace( shipto,"%23","#" );
        shipto=HelpObjs.findReplace( shipto,"%2F","\\" );
      }
      polog.info("shipto  "+shipto);
      //String faxdate = DataH.get("FAXDATE").toString().trim();
	  //String accepteddate = DataH.get("ACCEPTEDDATE").toString().trim();
	  String buyerid=DataH.get( "BUYERID" ).toString().trim();
	  String suppRating=DataH.get( "SUPPRATING" ).toString().trim();
	  String invengrp=DataH.get( "INVENTORY_GROUP" ).toString().trim();
	  String cosignedpo=DataH.get( "CONSIGNED_PO" ).toString().trim();
	  String criticalPo=DataH.get( "CRITICALPO" ).toString().trim();

	  pstmt=con.prepareCall( "{call pkg_po.BPO_HEADER (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );

	  BigDecimal bigbponumber = new BigDecimal(bpo);
	  pstmt.setBigDecimal( 1,bigbponumber );
	  if ( supplierid.length() > 0 )
	  {
		pstmt.setString( 2,supplierid );
	  }
	  else
	  {
		pstmt.setNull( 2,java.sql.Types.VARCHAR );
	  }

	  if ( ordertakerid.length() > 0 )
	  {
		pstmt.setInt( 3,Integer.parseInt( ordertakerid ) );
	  }
	  else
	  {
		pstmt.setNull( 3,java.sql.Types.INTEGER );
	  }

	  if ( buyerid.length() > 0 )
	  {
		pstmt.setInt( 4,Integer.parseInt( buyerid ) );
	  }
	  else
	  {
		pstmt.setInt( 4,Integer.parseInt( personnelID ) );
	  }

	  if ( bostartdate.length() == 10 )
	  {
		pstmt.setTimestamp( 5,radian.web.HTMLHelpObj.getDateFromString( bostartdate ) );
	  }
	  else
	  {
		pstmt.setNull( 5,java.sql.Types.DATE );
	  }
	  if ( boenddate.length() == 10 )
	  {
		pstmt.setTimestamp( 6,radian.web.HTMLHelpObj.getDateFromString( boenddate ) );
	  }
	  else
	  {
		pstmt.setNull( 6,java.sql.Types.DATE );
	  }

	  if ( fob.length() > 0 )
	  {
		pstmt.setString( 7,fob );
	  }
	  else
	  {
		pstmt.setNull( 7,java.sql.Types.VARCHAR );
	  }

	  pstmt.setNull( 8,java.sql.Types.VARCHAR );
	  pstmt.setNull( 9,java.sql.Types.VARCHAR );

	  if ( paymentterms.length() > 0 )
	  {
		pstmt.setString( 10,paymentterms );
	  }
	  else
	  {
		pstmt.setNull( 10,java.sql.Types.VARCHAR );
	  }

	  if ( shiptocompanyid.length() > 0 )
	  {
		pstmt.setString( 11,shiptocompanyid );
	  }
	  else
	  {
		pstmt.setNull( 11,java.sql.Types.VARCHAR );
	  }

	  if ( shipto.length() > 0 )
	  {
		pstmt.setString( 12,shipto );
	  }
	  else
	  {
		pstmt.setNull( 12,java.sql.Types.VARCHAR );
	  }

	  if ( invengrp.length() > 0 )
	  {
		pstmt.setString( 13,invengrp );
	  }
	  else
	  {
		pstmt.setNull( 13,java.sql.Types.VARCHAR );
	  }

	  if ( hub.length() > 0 )
	  {
		pstmt.setString( 14,hub );
	  }
	  else
	  {
		pstmt.setNull( 14,java.sql.Types.VARCHAR );
	  }

	  if ( carrierid.length() > 0 )
	  {
		pstmt.setString( 15,carrierid );
	  }
	  else
	  {
		pstmt.setNull( 15,java.sql.Types.VARCHAR );
	  }

	  if ( customerpo.length() > 0 )
	  {
		pstmt.setString( 16,customerpo );
	  }
	  else
	  {
		pstmt.setNull( 16,java.sql.Types.VARCHAR );
	  }

	  if ( !"N".equalsIgnoreCase( criticalPo ) &&  criticalPo.length() > 0)
	  {
		pstmt.setString( 17,criticalPo );
	  }
	  else
	  {
		pstmt.setNull( 17,java.sql.Types.VARCHAR );
	  }


	  if ( "Yes".equalsIgnoreCase(cosignedpo) )
	  {
		pstmt.setString( 18,"y" );
	  }
	  else
	  {
		pstmt.setString( 18,"n" );
	  }

	  pstmt.registerOutParameter( 19,java.sql.Types.VARCHAR );

	  if ( suppRating.length() > 0 )
	  {
		pstmt.setString( 20,suppRating );
	  }
	  else
	  {
		pstmt.setNull( 20,java.sql.Types.VARCHAR );
	  }

	 if ( shipToFacilityId.length() > 0 )
	 {
		pstmt.setString( 21,shipToFacilityId );
	 }
	 else
	 {
		pstmt.setNull( 21,java.sql.Types.VARCHAR );
	 }

	  pstmt.execute();
	  String errorcode=pstmt.getString( 19 );

	  if ( errorcode == null )
	  {
		result=true;
	  }
	  else
	  {
		polog.info( "Error from pkg_po.BPO_HEADER " + errorcode );
		errorFromProcedure+=errorcode;
		result=false;
	  }

	}
	catch ( Exception e )
	{
	  result=false;
	  //e.printStackTrace();
	  radian.web.emailHelpObj.sendnawazemail("Error Calling pkg_po.BPO_HEADER in PO Page ","Error Calling pkg_po.BPO_HEADER\nError Msg:\n"+e.getMessage()+"\n  radian BPO  "+bpo+"\n personnelID "+personnelID+"");
	}
	finally
	{
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

	return result;
  }

  private Hashtable updatePoLine( Hashtable DataH,String personnelID,String radianPo,String action,Connection con )
  {
	boolean result=false;
	boolean specResult=true;
	boolean flowResult=true;
	CallableStatement pstmt=null;
	String rownumber = "";
	String ammendment = "";
    String interCompMrNumber = "";
    Hashtable poLineUpdResult = new Hashtable();
	try
	{
	  String cocforflow=DataH.get( "COCFORFLOW" ).toString().trim();
	  String coaforflow=DataH.get( "COAFORFLOW" ).toString().trim();
	  Vector speclines= ( Vector ) DataH.get( "SPECLINES" );
	  Vector flowlines= ( Vector ) DataH.get( "FLOWLINES" );
	  //Vector addcharges = (Vector)DataH.get("CHARGELINEDETAILS");
	  //String cocforspec = DataH.get("COCFORSPEC").toString().trim();
	  String lineunitprice=DataH.get( "LINEUNITPRICE" ).toString().trim();
	  String lineqty=DataH.get( "LINEQTY" ).toString().trim();
	  String linenotes=DataH.get( "LINENOTES" ).toString().trim();
	  if ( linenotes.length() > 0 )
	  {
		linenotes=HelpObjs.findReplace( linenotes,"&#34;","\"" );
		linenotes=HelpObjs.findReplace( linenotes,"&#40;","(" );
		linenotes=HelpObjs.findReplace( linenotes,"&#41;",")" );
		linenotes=HelpObjs.findReplace( linenotes,"<BR>"," \n " );
	  }
	  linenotes=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( linenotes );
	  /*linenotes=HelpObjs.decodeHexcodesfoUpdate( linenotes );
	  if ( linenotes.length() > 4000 )
	  {
		linenotes=linenotes.substring( 0,3999 );
	  }*/

	  //String itemtype = DataH.get("ITEMTYPE").toString().trim();
	  String datepromised=DataH.get( "DATEPROMISED" ).toString().trim();
	  String supplierpartno=DataH.get( "SUPPLIERPARTNO" ).toString().trim();
	  String promisedshipdate=DataH.get( "PROJECTEDELIVDATE" ).toString().trim();
	  String dateneeded=DataH.get( "DATENEEDED" ).toString().trim();
	  String lineitemid=DataH.get( "LINEITEMID" ).toString().trim();
	  //String coaforspec = DataH.get("COAFORSPEC").toString().trim();
	  //String manufacturerpartno=DataH.get( "MANUFACTURERPARTNO" ).toString().trim();
	  String dpas=DataH.get( "DPAS" ).toString().trim();
	  String noofspecs=DataH.get( "NOOFSPECS" ).toString().trim();
	  String noofflowdowns=DataH.get( "NOOFFLOWDOWNS" ).toString().trim();
	  //String noofaddcharges = DataH.get("NOOFLINESINADDCHARGE").toString().trim();
	  String linestatus=DataH.get( "LINESTATUS" ).toString().trim();
	  String linechange=DataH.get( "LINECHANGE" ).toString().trim();
	  String originallinestatus=DataH.get( "ORIGINALLINESTATUS" ).toString().trim();
	  rownumber=DataH.get( "ROWNUMBER" ).toString().trim();
	  ammendment=DataH.get( "AMMENDMENT" ).toString().trim();
	  String ammendmentcomments=DataH.get( "AMMENDMENTCOMMENTS" ).toString().trim();
	  ammendmentcomments=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( ammendmentcomments );
	  /*if ( ammendmentcomments.length() > 250 )
	  {
		ammendmentcomments=ammendmentcomments.substring( 0,249 );
	  }*/

	  String supplierqty=DataH.get( "SUPPLIERQTY" ).toString().trim();
	  String supplierpkg=DataH.get( "SUPPLIERPKG" ).toString().trim();
	  String supplierunitprice=DataH.get( "SUPPLIERUNITPRICE" ).toString().trim();
	  String checkformsdsasked=DataH.get( "CHECKFORMSDS" ).toString().trim();
	  String shelflife=DataH.get( "SHELFLIFE" ).toString().trim();
	  String deliverylinenotes=DataH.get( "DELIVERYLINENOTES" ).toString().trim();
	  if ( deliverylinenotes.length() > 0 )
	  {
		deliverylinenotes=HelpObjs.findReplace( deliverylinenotes,"&#34;","\"" );
		deliverylinenotes=HelpObjs.findReplace( deliverylinenotes,"&#40;","(" );
		deliverylinenotes=HelpObjs.findReplace( deliverylinenotes,"&#41;",")" );
		deliverylinenotes=HelpObjs.findReplace( deliverylinenotes,"<BR>"," \n " );
	  }
	  deliverylinenotes=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( deliverylinenotes );
	  /*deliverylinenotes=HelpObjs.decodeHexcodesfoUpdate( deliverylinenotes );
	  if ( deliverylinenotes.length() > 1000 )
	  {
		deliverylinenotes=deliverylinenotes.substring( 0,999 );
	  }*/
 	  String currencyid=DataH.get( "CURRENCY_ID" ).toString().trim();
    String secondarySupplier=DataH.get( "SECONDARY_SUPPLIER" ).toString().trim();
    String shipFromLocationId=DataH.get( "SHIP_FROM_LOCATION_ID" ).toString().trim();
    String supplierSalesOrderNo=DataH.get( "SUPPLIER_SALES_ORDER_NO" ).toString().trim();
    String lineEverConfirmed=DataH.get( "LINE_EVER_CONFIRMED" ).toString().trim();
    if (lineEverConfirmed.equalsIgnoreCase("N"))
    {
      ammendment = "0";
    }

    //int rowNumber=Integer.parseInt( rownumber ) * 1000;
    BigDecimal rowNumber= new BigDecimal(rownumber);
    rowNumber = rowNumber.multiply(new BigDecimal("1000"));
    int amendmentNumber=Integer.parseInt( ammendment ) * 1;

	  if ( "No".equalsIgnoreCase( linechange ) && ( !"confirm".equalsIgnoreCase( action ) ) )
	  {
		result=true;
	  }
	  else if ( ( "confirm".equalsIgnoreCase( action ) ) && "Remove".equalsIgnoreCase( linestatus ) )
	  {
		polog.info("Confirming Removed Line for Purchase Order: "+radianPo+" rowNumber:  "+rowNumber+"  amendmentNumber:  "+amendmentNumber+"");
		result=true;
	  }
	  else if ( "Yes".equalsIgnoreCase( linechange ) && ( "confirm".equalsIgnoreCase( action ) ) &&
				( lineitemid.trim().length() == 0 || lineqty.trim().length() == 0 ) )
	  {
		if ( "Remove".equalsIgnoreCase( linestatus ) )
		{
		  result=true;
		}
		else
		{
		  result=false;
		}
	  }
	  else if ( "Yes".equalsIgnoreCase( linechange ) || "Unconfirmed".equalsIgnoreCase( originallinestatus ) )
	  {
		pstmt=con.prepareCall( "{call pkg_po.SAVE_PO_LINE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );

        BigDecimal bigponumber = new BigDecimal(radianPo);
		pstmt.setBigDecimal( 1,bigponumber );
		pstmt.setBigDecimal( 2,rowNumber );
		pstmt.setInt( 3,amendmentNumber );
        if ( lineitemid.length() > 0 )
		{
		  pstmt.setInt( 4,Integer.parseInt( lineitemid ) );
		}
		else
		{
		  pstmt.setNull( 4,java.sql.Types.INTEGER );
		}

        if ( "confirm".equalsIgnoreCase( action ) )
		{
		  pstmt.setString( 5,"Y" );
        }
		else
		{
		  pstmt.setNull( 5,java.sql.Types.VARCHAR );
		}

        if ( lineqty.length() > 0 )
		{
		  BigDecimal lineqtyOrdered= new BigDecimal( lineqty );
		  pstmt.setBigDecimal( 6,lineqtyOrdered );
		}
		else
		{
		  pstmt.setNull( 6,java.sql.Types.INTEGER );
		}

        if ( lineunitprice.trim().length() > 0 )
		{
		  //DecimalFormat lineutprice=new DecimalFormat( "####.000###" );
		  BigDecimal unitPrice= new BigDecimal (lineunitprice);
		  pstmt.setBigDecimal( 7,unitPrice );
		}
		else
		{
		  pstmt.setNull( 7,java.sql.Types.INTEGER );
		}

        if ( dateneeded.length() == 10 )
		{
		  pstmt.setTimestamp( 8,radian.web.HTMLHelpObj.getDateFromString( dateneeded ) );
        }
		else
		{
		  pstmt.setNull( 8,java.sql.Types.DATE );
		}
		if ( datepromised.length() == 10 )
		{
		  pstmt.setTimestamp( 9,radian.web.HTMLHelpObj.getDateFromString( datepromised ) );
		}
		else
		{
		  pstmt.setNull( 9,java.sql.Types.DATE );
		}

		pstmt.setNull( 10,java.sql.Types.INTEGER );
		pstmt.setNull( 11,java.sql.Types.VARCHAR );

        if ( supplierpartno.length() > 0 )
		{
		  pstmt.setString( 12,supplierpartno );
		}
		else
		{
		  pstmt.setNull( 12,java.sql.Types.VARCHAR );
		}

		if ( dpas.length() > 0 )
		{
		  pstmt.setString( 13,dpas );
		}
		else
		{
		  pstmt.setNull( 13,java.sql.Types.VARCHAR );
		}

		pstmt.setNull( 14,java.sql.Types.VARCHAR );
		pstmt.setNull( 15,java.sql.Types.VARCHAR );

        if ( linenotes.length() > 0 )
		{
		  pstmt.setString( 16,linenotes );
		}
		else
		{
		  pstmt.setNull( 16,java.sql.Types.VARCHAR );
		}

        if ( "confirm".equalsIgnoreCase( action ) )
		{
		  pstmt.setNull( 17,java.sql.Types.VARCHAR );
		}
		else
		{
		  if ( ammendmentcomments.length() > 0 )
		  {
			pstmt.setString( 17,ammendmentcomments );
		  }
		  else
		  {
			pstmt.setNull( 17,java.sql.Types.VARCHAR );
		  }
		}

		pstmt.setInt( 18,Integer.parseInt( personnelID ) );

		if ( supplierqty.length() > 0 )
		{
		  pstmt.setBigDecimal( 19, new BigDecimal( supplierqty ) );
		}
		else
		{
		  pstmt.setNull( 19,java.sql.Types.INTEGER );
		}

		if ( supplierpkg.length() > 0 )
		{
		  pstmt.setString( 20,supplierpkg );
		}
		else
		{
		  pstmt.setNull( 20,java.sql.Types.VARCHAR );
		}

		if ( supplierunitprice.length() > 0 )
		{
		  pstmt.setBigDecimal( 21,new BigDecimal( supplierunitprice ) );
		}
		else
		{
		  pstmt.setNull( 21,java.sql.Types.FLOAT );
		}

        if ( "Yes".equalsIgnoreCase( cocforflow ) )
		{
		  pstmt.setString( 22,"Y" );
		}
		else
		{
		  pstmt.setString( 22,"N" );
		}
		if ( "Yes".equalsIgnoreCase( coaforflow ) )
		{
		  pstmt.setString( 23,"Y" );
		}
		else
		{
		  pstmt.setString( 23,"N" );
		}

		if ( "Yes".equalsIgnoreCase( checkformsdsasked ) )
		{
		  pstmt.setString( 24,"Y" );
		}
		else
		{
		  pstmt.setNull( 24,java.sql.Types.VARCHAR );
		}

        if ( shelflife.length() > 0 )
		{
		  pstmt.setInt( 25,Integer.parseInt( shelflife ) );
		}
		else
		{
		  pstmt.setNull( 25,java.sql.Types.INTEGER );
		}

		if ( deliverylinenotes.length() > 0 )
		{
		  pstmt.setString( 26,deliverylinenotes );
		}
		else
		{
		  pstmt.setNull( 26,java.sql.Types.VARCHAR );
		}

        if ( promisedshipdate.length() == 10 )
		{
		  pstmt.setTimestamp( 27,radian.web.HTMLHelpObj.getDateFromString( promisedshipdate ) );
		}
		else
		{
		  pstmt.setNull( 27,java.sql.Types.DATE );
		}
		pstmt.setString( 28,currencyid );

        if ( secondarySupplier.length() > 0 )
	    {
		 pstmt.setString( 29,secondarySupplier );
	    }
	    else
	    {
		pstmt.setNull( 29,java.sql.Types.VARCHAR );
	    }
		pstmt.registerOutParameter( 30,java.sql.Types.VARCHAR );
        pstmt.registerOutParameter( 31,java.sql.Types.VARCHAR );

       if ( shipFromLocationId.length() > 0 )
	  {
		 pstmt.setString( 32,shipFromLocationId );
	  }
	  else
	  {
		  pstmt.setNull( 32,java.sql.Types.VARCHAR );
	  }

    if ( supplierSalesOrderNo.length() > 0 )
	  {
		 pstmt.setString( 33,supplierSalesOrderNo );
	  }
	  else
	  {
		  pstmt.setNull( 33,java.sql.Types.VARCHAR );
	  }

    pstmt.execute();

		String errorcode=pstmt.getString( 30 );
        interCompMrNumber =pstmt.getString( 31 );
      if ( errorcode == null )
		{
		  result=true;
		}
		else
		{
 		  polog.info( "Error from pkg_po.SAVE_PO_LINE " + errorcode + " amendmentNumber   " + amendmentNumber + "   originallinestatus   " + originallinestatus );
		  errorFromProcedure+=errorcode;
		  result=false;
		}

		if ( lineitemid.length() > 0 )
		{
          if ( ( Integer.parseInt( noofspecs ) > 0 ) && ! ( "confirm".equalsIgnoreCase( action ) ) )
		  {
			 specResult=updateSpecs( speclines,radianPo,rowNumber,amendmentNumber,"po",con );
		  }

		  if ( ( Integer.parseInt( noofflowdowns ) > 0 ) && ! ( "confirm".equalsIgnoreCase( action ) ) )
		  {
			 flowResult=updateFlowdowns( flowlines,radianPo,rowNumber,amendmentNumber,"po",con );
		  }
		}
      }
	  else if ( "No".equalsIgnoreCase( linechange ) )
	  {
		result=true;
	  }
	}
	catch ( Exception e )
	{
	  result=false;
	  //e.printStackTrace();
    String erroMessage =e.getMessage();
    polog.info(erroMessage);
    polog.info(erroMessage.indexOf("constraint") + "");
    if (erroMessage.indexOf("constraint") > 0)
    {
      String[] supportEmails = new String[]{"asepulveda@haastcm.com,mboze@haastcm.com,mblackburn@haastcm.com,dbertero@haastcm.com"};
      HelpObjs.javaSendMail("","Error Calling pkg_po.SAVE_PO_LINE in PO Page",supportEmails,"Error Calling pkg_po.SAVE_PO_LINE\nError Msg:\n"+e.getMessage()+"\n  radian PO  "+radianPo+"  rowNumber  "+rownumber+" ammendment   "+ammendment+" \n personnelID "+personnelID+"");
    }    
    radian.web.emailHelpObj.sendnawazemail("Error Calling pkg_po.SAVE_PO_LINE in PO Page ","Error Calling pkg_po.SAVE_PO_LINE\nError Msg:\n"+e.getMessage()+"\n  radian PO  "+radianPo+"  rowNumber  "+rownumber+" ammendment   "+ammendment+" \n personnelID "+personnelID+"");
	}
	finally
	{
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

  if (!specResult || !flowResult)
	{
	  result=false;
	}

      poLineUpdResult.put("LINE_RESULT",new Boolean(""+result+""));
      if (interCompMrNumber == null) {interCompMrNumber = "";}
      poLineUpdResult.put("INTER_COMPANY_MR",interCompMrNumber);
      return poLineUpdResult;
  }

  private boolean updatebPoLine( Hashtable DataH,String radianPo,String action,Connection con )
  {
	boolean result=false;
	boolean specResult=true;
	boolean flowResult=true;
	CallableStatement pstmt=null;
	String rownumber = "";
	String ammendment = "";

	try
	{
	  String cocforflow=DataH.get( "COCFORFLOW" ).toString().trim();
	  String coaforflow=DataH.get( "COAFORFLOW" ).toString().trim();
	  Vector speclines= ( Vector ) DataH.get( "SPECLINES" );
	  Vector flowlines= ( Vector ) DataH.get( "FLOWLINES" );
	  //Vector addcharges = (Vector)DataH.get("CHARGELINEDETAILS");
	  //String cocforspec = DataH.get("COCFORSPEC").toString().trim();
	  String lineunitprice=DataH.get( "LINEUNITPRICE" ).toString().trim();
	  String lineqty=DataH.get( "LINEQTY" ).toString().trim();
	  String linenotes=DataH.get( "LINENOTES" ).toString().trim();
	  if ( linenotes.length() > 0 )
	  {
		linenotes=HelpObjs.findReplace( linenotes,"&#34;","\"" );
		linenotes=HelpObjs.findReplace( linenotes,"&#40;","(" );
		linenotes=HelpObjs.findReplace( linenotes,"&#41;",")" );
		linenotes=HelpObjs.findReplace( linenotes,"<BR>"," \n " );
	  }
	  linenotes=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( linenotes );
	  /*linenotes=HelpObjs.decodeHexcodesfoUpdate( linenotes );
	  if ( linenotes.length() > 4000 )
	  {
		linenotes=linenotes.substring( 0,3999 );
	  }*/

	  //String itemtype = DataH.get("ITEMTYPE").toString().trim();
	  //String datepromised = DataH.get("DATEPROMISED").toString().trim();
	  String supplierpartno=DataH.get( "SUPPLIERPARTNO" ).toString().trim();
	  //String dateneeded = DataH.get("DATENEEDED").toString().trim();
	  String lineitemid=DataH.get( "LINEITEMID" ).toString().trim();
	  //String coaforspec = DataH.get("COAFORSPEC").toString().trim();
	  String manufacturerpartno=DataH.get( "MANUFACTURERPARTNO" ).toString().trim();
	  //String dpas = DataH.get("DPAS").toString().trim();
	  String noofspecs=DataH.get( "NOOFSPECS" ).toString().trim();
	  String noofflowdowns=DataH.get( "NOOFFLOWDOWNS" ).toString().trim();
	  //String noofaddcharges = DataH.get("NOOFLINESINADDCHARGE").toString().trim();
	  String linestatus = DataH.get("LINESTATUS").toString().trim();
	  String linechange=DataH.get( "LINECHANGE" ).toString().trim();
	  String originallinestatus=DataH.get( "ORIGINALLINESTATUS" ).toString().trim();
	  rownumber=DataH.get( "ROWNUMBER" ).toString().trim();
	  ammendment=DataH.get( "AMMENDMENT" ).toString().trim();
	  String ammendmentcomments=DataH.get( "AMMENDMENTCOMMENTS" ).toString().trim();
	  ammendmentcomments=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( ammendmentcomments );
	  /*if ( ammendmentcomments.length() > 250 )
	  {
		ammendmentcomments=ammendmentcomments.substring( 0,249 );
	  }*/

	  String supplierqty=DataH.get( "SUPPLIERQTY" ).toString().trim();
	  String supplierpkg=DataH.get( "SUPPLIERPKG" ).toString().trim();
	  String supplierunitprice=DataH.get( "SUPPLIERUNITPRICE" ).toString().trim();
	  String checkformsdsasked=DataH.get( "CHECKFORMSDS" ).toString().trim();
	  String shelflife=DataH.get( "SHELFLIFE" ).toString().trim();
	  String currencyid=DataH.get( "CURRENCY_ID" ).toString().trim();
      String lineEverConfirmed=DataH.get( "LINE_EVER_CONFIRMED" ).toString().trim();
      if (lineEverConfirmed.equalsIgnoreCase("N"))
      {
          ammendment = "0";
      }

      //int rowNumber=Integer.parseInt( rownumber ) * 1000;
    BigDecimal rowNumber= new BigDecimal(rownumber);
    rowNumber = rowNumber.multiply(new BigDecimal("1000"));
    int amendmentNumber=Integer.parseInt( ammendment ) * 1;

	  if ( "No".equalsIgnoreCase( linechange ) && ( !"confirm".equalsIgnoreCase( action ) ) )
	  {
		result=true;
	  }
	  else if ( ( "confirm".equalsIgnoreCase( action ) ) && "Remove".equalsIgnoreCase( linestatus ) )
	  {
		polog.info("Confirming Removed Line for Blanket Order: "+radianPo+" rowNumber:  "+rowNumber+"  amendmentNumber:  "+amendmentNumber+"");
		result=true;
	  }
	  else if ( "Yes".equalsIgnoreCase( linechange ) && ( "confirm".equalsIgnoreCase( action ) ) &&
					  ( lineitemid.trim().length() == 0 || lineqty.trim().length() == 0 ) )
	  {
		if ( "Remove".equalsIgnoreCase( linestatus ) )
		{
		  result=true;
		}
		else
		{
		  result=false;
		}
	  }
	  else if ( "Yes".equalsIgnoreCase( linechange ) || "Unconfirmed".equalsIgnoreCase( originallinestatus ) )
	  {
		pstmt=con.prepareCall( "{call pkg_po.SAVE_bPO_LINE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );

		BigDecimal bigponumber = new BigDecimal(radianPo);
		pstmt.setBigDecimal( 1,bigponumber );
		pstmt.setBigDecimal( 2,rowNumber );
		pstmt.setInt( 3,amendmentNumber );

		if ( lineitemid.length() > 0 )
		{
		  pstmt.setInt( 4,Integer.parseInt( lineitemid ) );
		}
		else
		{
		  pstmt.setNull( 4,java.sql.Types.INTEGER );
		}

		if ( lineqty.length() > 0)
		{
		  BigDecimal lineqtyOrdered= new BigDecimal( lineqty );
		  pstmt.setBigDecimal( 5,lineqtyOrdered );
		}
		else
		{
		  pstmt.setNull( 5,java.sql.Types.INTEGER );
		}

		if ( lineunitprice.trim().length() > 0 )
		{
		  //DecimalFormat lineutprice=new DecimalFormat( "####.000###" );
		  BigDecimal unitPrice= new BigDecimal (lineunitprice);
		  pstmt.setBigDecimal( 6,unitPrice );
		}
		else
		{
		  pstmt.setNull( 6,java.sql.Types.INTEGER );
		}

		pstmt.setNull( 7,java.sql.Types.INTEGER );

		if ( manufacturerpartno.length() > 0 )
		{
		  pstmt.setString( 8,manufacturerpartno );
		}
		else
		{
		  pstmt.setNull( 8,java.sql.Types.VARCHAR );
		}
		if ( supplierpartno.length() > 0 )
		{
		  pstmt.setString( 9,supplierpartno );
		}
		else
		{
		  pstmt.setNull( 9,java.sql.Types.VARCHAR );
		}

		pstmt.setNull( 10,java.sql.Types.VARCHAR );
		pstmt.setNull( 11,java.sql.Types.VARCHAR );

		if ( linenotes.length() > 0 )
		{
		  pstmt.setString( 12,linenotes );
		}
		else
		{
		  pstmt.setNull( 12,java.sql.Types.VARCHAR );
		}

		if ( "confirm".equalsIgnoreCase( action ) )
		{
		  pstmt.setNull( 13,java.sql.Types.VARCHAR );
		}
		else
		{
		  if ( ammendmentcomments.length() > 0 )
		  {
			pstmt.setString( 13,ammendmentcomments );
		  }
		  else
		  {
			pstmt.setNull( 13,java.sql.Types.VARCHAR );
		  }
		}

		pstmt.setNull( 14,java.sql.Types.DATE );
		pstmt.setNull( 15,java.sql.Types.INTEGER );
		pstmt.setNull( 16,java.sql.Types.VARCHAR );

		if ( "confirm".equalsIgnoreCase( action ) )
		{
		  pstmt.setString( 17,"Y" );
		}
		else
		{
		  pstmt.setNull( 17,java.sql.Types.VARCHAR );
		}
		if ( supplierqty.length() > 0 )
		{
		  pstmt.setBigDecimal( 18,new BigDecimal( supplierqty ) );
		}
		else
		{
		  pstmt.setNull( 18,java.sql.Types.INTEGER );
		}
		pstmt.setString( 19,supplierpkg );
		if ( supplierunitprice.length() > 0 )
		{
		  pstmt.setBigDecimal( 20,new BigDecimal( supplierunitprice ) );
		}
		else
		{
		  pstmt.setNull( 20,java.sql.Types.FLOAT );
		}

		if ( "Yes".equalsIgnoreCase( checkformsdsasked ) )
		{
		  pstmt.setString( 21,"Y" );
		}
		else
		{
		  pstmt.setNull( 21,java.sql.Types.VARCHAR );
		}

		if ( "Yes".equalsIgnoreCase( cocforflow ) )
		{
		  pstmt.setString( 22,"Y" );
		}
		else
		{
		  pstmt.setString( 22,"N" );
		}
		if ( "Yes".equalsIgnoreCase( coaforflow ) )
		{
		  pstmt.setString( 23,"Y" );
		}
		else
		{
		  pstmt.setString( 23,"N" );
		}

		if ( shelflife.length() > 0 )
		{
		  pstmt.setInt( 24,Integer.parseInt( shelflife ) );
		}
		else
		{
		  pstmt.setNull( 24,java.sql.Types.INTEGER );
		}

		pstmt.setString( 25,currencyid );
		pstmt.setNull( 26,java.sql.Types.VARCHAR );

		pstmt.registerOutParameter( 27,java.sql.Types.VARCHAR );

		pstmt.execute();

		String errorcode=pstmt.getString( 27 );
		if ( errorcode == null )
		{
		  result=true;
		}
		else
		{
		  polog.info( "Error from pkg_po.SAVE_bPO_LINE  " + errorcode + " amendmentNumber  " + amendmentNumber + "  originallinestatus  " + originallinestatus );
		  errorFromProcedure+=errorcode;
		  result=false;
		}

		if ( lineitemid.length() > 0 )
		{
		  if ( ( Integer.parseInt( noofspecs ) > 0 ) && ! ( "confirm".equalsIgnoreCase( action ) ) )
		  {
			specResult=updateSpecs( speclines,radianPo,rowNumber,amendmentNumber,"bpo",con );
		  }

		  if ( ( Integer.parseInt( noofflowdowns ) > 0 ) && ! ( "confirm".equalsIgnoreCase( action ) ) )
		  {
			flowResult=updateFlowdowns( flowlines,radianPo,rowNumber,amendmentNumber,"bpo",con );
		  }
		}
	  }
	  else if ( "No".equalsIgnoreCase( linechange ) )
	  {
		result=true;
	  }
	}
	catch ( Exception e )
	{
	  result=false;
	  //e.printStackTrace();
	  radian.web.emailHelpObj.sendnawazemail("Error Calling pkg_po.SAVE_bPO_LINE in PO Page ","Error Calling pkg_po.SAVE_bPO_LINE\nError Msg:\n"+e.getMessage()+"\n  radian BPO  "+radianPo+"  rowNumber  "+rownumber+" ammendment   "+ammendment+" ");
	}
	finally
	{
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

	if (!specResult || !flowResult)
	{
	 result=false;
	}
		return result;
  }

  private boolean updateDeliveryDelayEmail( Hashtable DataH,String radianPo,String action,Connection con )
  {
	boolean result=false;
	CallableStatement pstmt=null;

	try
	{
	  //String lineunitprice = DataH.get("LINEUNITPRICE").toString().trim();
	  String lineqty=DataH.get( "LINEQTY" ).toString().trim();
	  //String itemtype = DataH.get("ITEMTYPE").toString().trim();
	  String lineitemid=DataH.get( "LINEITEMID" ).toString().trim();
	  String linestatus=DataH.get( "LINESTATUS" ).toString().trim();
	  String linechange=DataH.get( "LINECHANGE" ).toString().trim();
	  String originallinestatus=DataH.get( "ORIGINALLINESTATUS" ).toString().trim();
	  String rownumber=DataH.get( "ROWNUMBER" ).toString().trim();
	  //String ammendment = DataH.get("AMMENDMENT").toString().trim();
	  //int rowNumber=Integer.parseInt( rownumber ) * 1000;
    BigDecimal rowNumber= new BigDecimal(rownumber);
    rowNumber = rowNumber.multiply(new BigDecimal("1000"));
    //int amendmentNumber = Integer.parseInt(ammendment)*1;

	  if ( "No".equalsIgnoreCase( linechange ) && ( !"confirm".equalsIgnoreCase( action ) ) )
	  {
		result=true;
	  }
	  else if ( "Yes".equalsIgnoreCase( linechange ) && ( "confirm".equalsIgnoreCase( action ) ) &&
				( lineitemid.trim().length() == 0 || lineqty.trim().length() == 0 ) )
	  {
		if ( "Remove".equalsIgnoreCase( linestatus ) )
		{
		  result=true;
		}
		else
		{
		  result=false;
		}
	  }

	  else if ( "Yes".equalsIgnoreCase( linechange ) || "Unconfirmed".equalsIgnoreCase( originallinestatus ) )
	  {
		pstmt=con.prepareCall( "{call pkg_po.PR_DELIVERY_DELAY_NOTIFY_LINE(?,?)}" );
		BigDecimal bigponumber = new BigDecimal(radianPo);
		pstmt.setBigDecimal( 1,bigponumber );
		pstmt.setBigDecimal( 2,rowNumber );
		pstmt.execute();
		result=true;
	  }
	  else if ( "No".equalsIgnoreCase( linechange ) )
	  {
		result=true;
	  }
	}
	catch ( Exception e )
	{
	  result=false;
	  //e.printStackTrace();
	  radian.web.emailHelpObj.sendnawazemail("Error Calling pkg_po.PR_DELIVERY_DELAY_NOTIFY_LINE in PO Page ","Error Calling pkg_po.PR_DELIVERY_DELAY_NOTIFY_LINE\nError Msg:\n"+e.getMessage()+"\n  radian PO  "+radianPo+"");
	}
	finally
	{
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

	return result;
  }

  private boolean updateLineAddCharges( Hashtable DataH,String radianPo,String whichpo,Connection con )
  {
	boolean lineAddCrgresult=false;

	try
	{
	  String linestatus=DataH.get( "LINESTATUS" ).toString().trim();
	  String linechange=DataH.get( "LINECHANGE" ).toString().trim();
	  String rownumber=DataH.get( "ROWNUMBER" ).toString().trim();
	  String itemtype=DataH.get( "ITEMTYPE" ).toString().trim();
	  String noofaddcharges=DataH.get( "NOOFLINESINADDCHARGE" ).toString().trim();
	  String ammendment=DataH.get( "AMMENDMENT" ).toString().trim();
	  int amendmentNumber=Integer.parseInt( ammendment ) * 1;
	  Vector addcharges= ( Vector ) DataH.get( "CHARGELINEDETAILS" );
	  String originallinestatus=DataH.get( "ORIGINALLINESTATUS" ).toString().trim();

	  //int rowNumber=Integer.parseInt( rownumber ) * 1000;
    BigDecimal rowNumber= new BigDecimal(rownumber);
    rowNumber = rowNumber.multiply(new BigDecimal("1000"));

    if ( ( !"Remove".equalsIgnoreCase( linestatus ) && "Yes".equalsIgnoreCase( linechange ) ) || "Unconfirmed".equalsIgnoreCase( originallinestatus ) )
	  {
		if ( addchargeslist.contains( itemtype ) && ( Integer.parseInt( noofaddcharges ) > 0 ) )
		{
		  lineAddCrgresult=updateAddcharges( addcharges,radianPo,rowNumber,amendmentNumber,whichpo,con );
		}
		else
		{
		  lineAddCrgresult=true;
		}
	  }
	  else
	  {
		lineAddCrgresult=true;
	  }
	}
	catch ( Exception e )
	{
	  lineAddCrgresult=false;
	  //e.printStackTrace();
	}

	return lineAddCrgresult;
  }

  private boolean deletePoLine( Hashtable DataH,String radianPo,Connection con )
  {
	boolean deleteResult=false;
	CallableStatement pstmt=null;
	//Connection con = db.getConnection();
	String rownumber = "";
	String ammendment = "";
	try
	{
	  String linestatus=DataH.get( "LINESTATUS" ).toString().trim();
	  //String linechange = DataH.get("LINECHANGE").toString().trim();
	  rownumber=DataH.get( "ROWNUMBER" ).toString().trim();
	  ammendment=DataH.get( "AMMENDMENT" ).toString().trim();
	  //int amendmentNumber = Integer.parseInt(ammendment)*1;
	  //int rowNumber=Integer.parseInt( rownumber ) * 1000;
    BigDecimal rowNumber= new BigDecimal(rownumber);
    rowNumber = rowNumber.multiply(new BigDecimal("1000"));

    if ( "Remove".equalsIgnoreCase( linestatus ) )
	  {
		pstmt=con.prepareCall( "{ call pkg_po.DELETE_PO_LINE (?,?,?,?) }" );
		BigDecimal bigponumber = new BigDecimal(radianPo);
		pstmt.setBigDecimal( 1,bigponumber );
		pstmt.setBigDecimal( 2,rowNumber );
		pstmt.setInt( 3,Integer.parseInt( ammendment ) );
		pstmt.registerOutParameter( 4,java.sql.Types.VARCHAR );
		pstmt.execute();

		String errorcode=pstmt.getString( 4 );
		if ( errorcode == null )
		{
		  deleteResult=true;
		}
		else
		{
 		  polog.info( "Error from pkg_po.DELETE_PO_LINE " + errorcode );
		  deleteResult=false;
		}
	  }
	  else
	  {
		deleteResult=true;
	  }
	}
	catch ( Exception e )
	{
	  deleteResult=false;
	  //e.printStackTrace();
	  radian.web.emailHelpObj.sendnawazemail("Error Calling pkg_po.DELETE_PO_LINE in PO Page ","Error Calling pkg_po.DELETE_PO_LINE\nError Msg:\n"+e.getMessage()+"\n  radianPo  "+radianPo+"  rowNumber  "+rownumber+" ammendment   "+ammendment+" ");
	}
	finally
	{
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

	return deleteResult;
  }

  private boolean deletebPoLine( Hashtable DataH,String radianPo,Connection con )
  {
	boolean deleteResult=false;
	CallableStatement pstmt=null;
	String rownumber = "";
	String ammendment = "";
	try
	{
	  String linestatus=DataH.get( "LINESTATUS" ).toString().trim();
	  //String linechange = DataH.get("LINECHANGE").toString().trim();
	  rownumber=DataH.get( "ROWNUMBER" ).toString().trim();
	  ammendment=DataH.get( "AMMENDMENT" ).toString().trim();
	  //int amendmentNumber = Integer.parseInt(ammendment)*1;
	  //int rowNumber=Integer.parseInt( rownumber ) * 1000;
    BigDecimal rowNumber= new BigDecimal(rownumber);
    rowNumber = rowNumber.multiply(new BigDecimal("1000"));

    if ( "Remove".equalsIgnoreCase( linestatus ) )
	  {
		pstmt=con.prepareCall( "{ call pkg_po.DELETE_BPO_LINE (?,?,?,?) }" );
		BigDecimal bigponumber = new BigDecimal(radianPo);
		pstmt.setBigDecimal( 1,bigponumber );
		pstmt.setBigDecimal( 2,rowNumber );
		pstmt.setInt( 3,Integer.parseInt( ammendment ) );
		pstmt.registerOutParameter( 4,java.sql.Types.VARCHAR );
		pstmt.execute();

		String errorcode=pstmt.getString( 4 );
		if ( errorcode == null )
		{
		  deleteResult=true;
		}
		else
		{
 		  polog.info( "Error from P_DELETE_PO_LINE " + errorcode );
		  deleteResult=false;
		}
	  }
	  else
	  {
		deleteResult=true;
	  }
	}
	catch ( Exception e )
	{
	  deleteResult=false;
	  //e.printStackTrace();
	  radian.web.emailHelpObj.sendnawazemail("Error Calling pkg_po.DELETE_BPO_LINE in PO Page ","Error Calling pkg_po.DELETE_BPO_LINE\nError Msg:\n"+e.getMessage()+"\n  radian BPO  "+radianPo+"  rowNumber  "+rownumber+" ammendment   "+ammendment+" ");
	}
	finally
	{
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

	return deleteResult;
  }

  private boolean updateSpecs( Vector DataS,String radianPo,BigDecimal poLine,int ammendMent,String whichpo,Connection con )
  {
	boolean specresult=false;
	Statement insertstatement=null;

	try
	{
	  insertstatement=con.createStatement();

	  if ( "bpo".equalsIgnoreCase( whichpo ) )
	  {
		Statement delstatement=con.createStatement();
		delstatement.executeUpdate( "delete from bpo_line_spec_draft where BPO = " + radianPo + " and BPO_LINE = " + poLine + " " );
	  }
	  else
	  {
		Statement delstatement=con.createStatement();
		delstatement.executeUpdate( "delete from po_line_spec_draft where RADIAN_PO = " + radianPo + " and PO_LINE = " + poLine + " " );
	  }

	  for ( int i=0; i < DataS.size(); i++ )
	  {
		Hashtable specData=new Hashtable();
		specData= ( Hashtable ) DataS.elementAt( i );
		String speclibrary=specData.get( "SPECLIBRARY" ).toString().trim();
		String specid=specData.get( "SPECID" ).toString().trim();
		//String speccompanyid=specData.get( "SPECCOMPANYID" ).toString().trim();
		//String speccompliance=specData.get( "SPECCOMPLIANCE" ).toString().trim();
		//String attachspec=specData.get( "ATTACHSPEC" ).toString().trim();
		String speccoc=specData.get( "SPECCOCCMPLIANCE" ).toString().trim();
		String speccoa=specData.get( "SPECCOACMPLIANCE" ).toString().trim();
		String attachflag="";
		String cocflag="";
		String coaflag="";
		//String certreq=specData.get( "SPECCERTREQ" ).toString().trim();
		//if (!"confirm".equalsIgnoreCase(saveSpecs)){certreq = "";}
		String specdetail=specData.get( "SPECDETAIL" ).toString().trim();

		String specCurrentCocRequirement=specData.get( "SPECCERTCOCREQ" ).toString().trim();
		String specCurrentCoaRequirement=specData.get( "SPECCERTCOAREQ" ).toString().trim();
		String specColor=specData.get( "SPECCOLOR" ).toString().trim();

		if ( "1".equalsIgnoreCase( specColor ) || "2".equalsIgnoreCase( specColor ) || "Yes".equalsIgnoreCase( speccoc ) || "Yes".equalsIgnoreCase( speccoa ) )
		{
		  /*if ( "Yes".equalsIgnoreCase( attachspec ) )
		  {
			attachflag="Y";
		  }*/
		  if ( "Yes".equalsIgnoreCase( speccoc ) )
		  {
			cocflag="Y";
		  }
		  if ( "Yes".equalsIgnoreCase( speccoa ) )
		  {
			coaflag="Y";
		  }

      /*Checking to see if for some reason this spec is already in the database with the same ammendment.
      Doing this to avoid confirmation error and manual fixinf of the POs. - Nawaz 11-08-08*/
      int specExistsCount=0;
 		  specExistsCount= DbHelpers.countQuery( db,"select count(*) from po_line_spec_all where radian_po = " + radianPo
         + " and PO_LINE = "+poLine+" and AMENDMENT = "+ammendMent+" and SPEC_ID ='"+specid+"' and SPEC_LIBRARY = '"+speclibrary
         + "' and DETAIL = '"+specdetail+"' " );
      if ( specExistsCount ==0)
      {
        String insertspecs="";
        if ( "bpo".equalsIgnoreCase( whichpo ) )
        {
        insertspecs="insert into bpo_line_spec_draft (SPEC_ID,SPEC_LIBRARY,BPO,BPO_LINE,COC,COA,AMENDMENT,DETAIL,COA_REQ,COC_REQ,COLOR) values \n";
        insertspecs+="('" + specid + "','" + speclibrary + "'," + radianPo + "," + poLine + ",'" + cocflag + "','" + coaflag + "'," + ammendMent + ",'"+specdetail+"','"+specCurrentCoaRequirement+"','"+specCurrentCocRequirement+"',"+specColor+") \n";
        }
        else
        {
        insertspecs="insert into po_line_spec_draft (SPEC_ID,SPEC_LIBRARY,RADIAN_PO,PO_LINE,COC,COA,AMENDMENT,DETAIL,COA_REQ,COC_REQ,COLOR) values \n";
        insertspecs+="('" + specid + "','" + speclibrary + "'," + radianPo + "," + poLine + ",'" + cocflag + "','" + coaflag + "'," + ammendMent + ",'"+specdetail+"','"+specCurrentCoaRequirement+"','"+specCurrentCocRequirement+"',"+specColor+") \n";
        }
        //polog.info(insertspecs);
        try {
         insertstatement.executeUpdate(insertspecs);
        }
        catch (SQLException ex) {
         specresult=false;
         //ex.printStackTrace();
        }
      }
    }
	  }
	  specresult=true;
	}
	catch ( Exception e )
	{
	  specresult=false;
	  //e.printStackTrace();
	}

	return specresult;
  }

  private boolean updatelookaheads( Vector DataF,Connection con )
  {
	boolean flowresult=false;
	CallableStatement pstmt=null;

	try
	{
	  for ( int i=0; i < DataF.size(); i++ )
	  {
		Hashtable flowData=new Hashtable();
		flowData= ( Hashtable ) DataF.elementAt( i );
		String lookahdcatalogid=flowData.get( "LOOKAHDCATALOGID" ).toString().trim();
		String lookahdcatpartno=flowData.get( "LOOKAHDCATPARTNO" ).toString().trim();
		String lookahdcompanyid=flowData.get( "LOOKAHDCOMPANYID" ).toString().trim();
		String lookAheadDays=flowData.get( "LOOKAHDNODAYS" ).toString().trim();
		if (lookAheadDays.length() == 0)
		{
		 lookAheadDays = "null";
		}

		String lookaheadinventorygrp=flowData.get( "LOOKAHDINVGRP" ).toString().trim();
		String lookaheadpartgrpno=flowData.get( "LOOKAHDPARTGRPNO" ).toString().trim();

		if ( lookahdcatalogid.length() > 0 && lookahdcatpartno.length() > 0 && lookahdcompanyid.length() > 0 && lookAheadDays.length() > 0 && !"0".equalsIgnoreCase(lookAheadDays) )
		{
		  String updatelookahead="update customer.catalog_part_inventory set LOOK_AHEAD_DAYS = " + lookAheadDays + " where COMPANY_ID='" + lookahdcompanyid + "' and CATALOG_ID='" + lookahdcatalogid + "' and CAT_PART_NO ='" + lookahdcatpartno + "'";

		  Statement insertstatement=con.createStatement();
		  insertstatement.executeUpdate( updatelookahead );

		  try
		  {
			pstmt=con.prepareCall( "{call p_cpi_rli_allocate (?,?,?,?,?)}" );

			pstmt.setString( 1,lookahdcompanyid );
			pstmt.setString( 2,lookahdcatalogid );
			pstmt.setString( 3,lookahdcatpartno );
			if ( lookaheadinventorygrp.length() > 0 )
			{
			  pstmt.setString( 4,lookaheadinventorygrp );
			}
			else
			{
			  pstmt.setNull( 4,java.sql.Types.VARCHAR );
			}
			if ( lookaheadpartgrpno.length() > 0 )
			{
			  pstmt.setInt( 5,Integer.parseInt( lookaheadpartgrpno ) );
			}
			else
			{
			  pstmt.setNull( 5,java.sql.Types.INTEGER );
			}

			pstmt.execute();
		  }
		  catch ( Exception lokahead )
		  {
			//lokahead.printStackTrace();
			radian.web.emailHelpObj.sendnawazemail("Error Calling p_cpi_rli_allocate in PO Page ","Error Calling p_cpi_rli_allocate\nError Msg:\n"+lokahead.getMessage()+"\n  COMPANY_ID='" + lookahdcompanyid + "' and CATALOG_ID='" + lookahdcatalogid + "' and CAT_PART_NO ='" + lookahdcatpartno + "'");
		  }
		  finally
		  {
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

		  con.commit();
		}
	  }
	  flowresult=true;
	}
	catch ( Exception e )
	{
	  flowresult=false;
	  //e.printStackTrace();
	}

	return flowresult;
  }

  private boolean updateFlowdowns( Vector DataF,String radianPo,BigDecimal poLine,int ammendMent,String whichpo,Connection con )
  {
	boolean flowresult=false;
	try
	{
	  if ( "bpo".equalsIgnoreCase( whichpo ) )
	  {
		Statement delstatement=con.createStatement();
		delstatement.executeUpdate( "delete from bpo_line_flow_down_draft where BPO = " + radianPo + " and BPO_LINE = " + poLine + " " );
	  }
	  else
	  {
		Statement delstatement=con.createStatement();
		delstatement.executeUpdate( "delete from po_line_flow_down_draft where RADIAN_PO = " + radianPo + " and PO_LINE = " + poLine + " " );
	  }

	  for ( int i=0; i < DataF.size(); i++ )
	  {
		Hashtable flowData=new Hashtable();
		flowData= ( Hashtable ) DataF.elementAt( i );

		String flowcatalogid=flowData.get( "FLOWCATALOGID" ).toString().trim();
		//String flowcatpartno = flowData.get("FLOWCATPARTNO").toString().trim();
		String flowcompanyid=flowData.get( "FLOWCOMPANYID" ).toString().trim();
		String flowcompliance=flowData.get( "FLOWCOMPLIANCE" ).toString().trim();
		//String attachflow=flowData.get( "ATTACHFLOW" ).toString().trim();
		String flowid=flowData.get( "FLOWID" ).toString().trim();
		String attachflag="";

		if ( "Yes".equalsIgnoreCase( flowcompliance ) )
		{
		  /*if ( "Yes".equalsIgnoreCase( attachflow ) )
		  {
			attachflag="Y";
		  }*/

		  String insertflows="";
		  if ( "bpo".equalsIgnoreCase( whichpo ) )
		  {
			insertflows="insert into bpo_line_flow_down_draft (COMPANY_ID,CATALOG_ID,FLOW_DOWN,BPO,BPO_LINE,ATTACH,AMENDMENT) values \n";
			insertflows+="('" + flowcompanyid + "','" + flowcatalogid + "','" + flowid + "'," + radianPo + "," + poLine + ",'" + attachflag + "'," + ammendMent + ") \n";
		  }
		  else
		  {
			insertflows="insert into po_line_flow_down_draft (COMPANY_ID,CATALOG_ID,FLOW_DOWN,RADIAN_PO,PO_LINE,ATTACH,AMENDMENT) values \n";
			insertflows+="('" + flowcompanyid + "','" + flowcatalogid + "','" + flowid + "'," + radianPo + "," + poLine + ",'" + attachflag + "'," + ammendMent + ") \n";
		  }

		  Statement insertstatement=con.createStatement();
		  insertstatement.executeUpdate( insertflows );
		}
	  }
	  flowresult=true;
	}
	catch ( Exception e )
	{
	  flowresult=false;
	  //e.printStackTrace();
	}

	return flowresult;
  }

  private boolean updateAddcharges( Vector DataAc,String radianPo,BigDecimal poLine,int ammendMent,String whichpo,Connection con )
  {
	boolean addchargesresult=false;

	try
	{
	  if ( "bpo".equalsIgnoreCase( whichpo ) )
	  {
		Statement delstatement=con.createStatement();
		delstatement.executeUpdate( "delete from bpo_add_charge_alloc_draft where BPO = " + radianPo + " and BPO_ADD_CHARGE_LINE = " + poLine + " " );
	  }
	  else
	  {
		Statement delstatement=con.createStatement();
		delstatement.executeUpdate( "delete from po_add_charge_alloc_draft where RADIAN_PO = " + radianPo + " and PO_ADD_CHARGE_LINE = " + poLine + " " );
	  }

    int addChargeDuplicates=0;
    ResultSet rs = null;
    Statement checkStatement=con.createStatement();
    rs=checkStatement.executeQuery("Select * from po_add_charge_allocation where RADIAN_PO = " + radianPo + " and PO_ADD_CHARGE_LINE = " + poLine + " and AMENDMENT =" + ammendMent + " " );
    while ( rs.next() )
    {
      addChargeDuplicates++;
    }

    rs=checkStatement.executeQuery("Select * from po_add_charge_alloc_history where RADIAN_PO = " + radianPo + " and PO_ADD_CHARGE_LINE = " + poLine + " and AMENDMENT =" + ammendMent + " " );
    while ( rs.next() )
    {
      addChargeDuplicates++;
    }

    if (addChargeDuplicates == 0)
    {
    for ( int i=0; i < DataAc.size(); i++ )
	  {
		Hashtable addchargesData=new Hashtable();
		addchargesData= ( Hashtable ) DataAc.elementAt( i );
		String addchargecheck=addchargesData.get( "ADDCHARGECHECK" ).toString().trim();
		String chargelinenumber=addchargesData.get( "CHARGETOLINE" ).toString().trim();
		//int chargeLine=Integer.parseInt( chargelinenumber ) * 1000;
    BigDecimal chargeLine= new BigDecimal(chargelinenumber);
    chargeLine = chargeLine.multiply(new BigDecimal("1000"));

    if ( "Yes".equalsIgnoreCase( addchargecheck ) )
		{
		  String insertflows="";

		  if ( "bpo".equalsIgnoreCase( whichpo ) )
		  {
			insertflows="insert into bpo_add_charge_alloc_draft (BPO,BPO_LINE,BPO_ADD_CHARGE_LINE,AMENDMENT) values \n";
			insertflows+="(" + radianPo + "," + chargeLine + "," + poLine + "," + ammendMent + ") \n";
		  }
		  else
		  {
			insertflows="insert into po_add_charge_alloc_draft (RADIAN_PO,PO_LINE,PO_ADD_CHARGE_LINE,AMENDMENT) values \n";
			insertflows+="(" + radianPo + "," + chargeLine + "," + poLine + "," + ammendMent + ") \n";
		  }

		  Statement insertstatement=con.createStatement();
		  insertstatement.executeUpdate( insertflows );
		}
	  }   
    addchargesresult=true;
    }
  }
	catch ( Exception e )
	{
	  addchargesresult=false;
	  //e.printStackTrace();
	}

	return addchargesresult;
  }


  private Hashtable synchServerData( HttpServletRequest request )
  {
	Hashtable finalData=new Hashtable();
	Vector lineData=new Vector();

	try
	{
	  Hashtable headerData=new Hashtable();
	  String supplierid="";
	  supplierid=BothHelpObjs.makeBlankFromNull( request.getParameter( "supplierid" ) );
	  headerData.put( "SUPPLIERID",supplierid );

	  String HubName="";
	  HubName=BothHelpObjs.makeBlankFromNull( request.getParameter( "HubName" ) );
	  if ( "None".equalsIgnoreCase( HubName ) )
	  {
		HubName="";
	  }
	  headerData.put( "HUB",HubName );

	  String po="";
	  po=BothHelpObjs.makeBlankFromNull( request.getParameter( "po" ) );
	  headerData.put( "PO",po );

	  String fob="";
	  fob=BothHelpObjs.makeBlankFromNull( request.getParameter( "fob" ) );
	  if ( "None".equalsIgnoreCase( fob ) )
	  {
		fob="";
	  }
	  headerData.put( "FOB",fob );

	  String bpo="";
	  bpo=BothHelpObjs.makeBlankFromNull( request.getParameter( "bpo" ) );
	  headerData.put( "BPO",bpo );

	  String newpo="";
	  newpo=BothHelpObjs.makeBlankFromNull( request.getParameter( "Newpo" ) );
	  headerData.put( "NEWPO",newpo );

	  String newbpo="";
	  newbpo=BothHelpObjs.makeBlankFromNull( request.getParameter( "Newbpo" ) );
	  headerData.put( "NEWBPO",newbpo );

	  String buyername="";
	  buyername=BothHelpObjs.makeBlankFromNull( request.getParameter( "buyer" ) );
	  headerData.put( "BUYERNAME",buyername );

	  String buyerid="";
	  buyerid=BothHelpObjs.makeBlankFromNull( request.getParameter( "buyerid" ) );
	  headerData.put( "BUYERID",buyerid );

	  String carrierID="";
	  carrierID=BothHelpObjs.makeBlankFromNull( request.getParameter( "carrierID" ) );
	  headerData.put( "CARRIERID",carrierID );

	  String carrieraccount="";
	  carrieraccount=BothHelpObjs.makeBlankFromNull( request.getParameter( "carrieraccount" ) );
	  headerData.put( "CARRIERACCOUNT",carrieraccount );

	  String ordertaker="";
	  ordertaker=BothHelpObjs.makeBlankFromNull( request.getParameter( "ordertaker" ) );
	  headerData.put( "ORDERTAKER",ordertaker );

	  String ordertakerID="";
	  ordertakerID=BothHelpObjs.makeBlankFromNull( request.getParameter( "ordertakerID" ) );
	  headerData.put( "ORDERTAKERID",ordertakerID );

	  String shiptoid="";
	  shiptoid=BothHelpObjs.makeBlankFromNull( request.getParameter( "shiptoid" ) );
	  headerData.put( "SHIPTO",shiptoid );

	  String shiptocompanyid="";
	  shiptocompanyid=BothHelpObjs.makeBlankFromNull( request.getParameter( "shiptocompanyid" ) );
	  headerData.put( "SHIPTOCOMPANYID",shiptocompanyid );

		String shipToFacilityId="";
		shipToFacilityId=BothHelpObjs.makeBlankFromNull( request.getParameter( "shipToFacilityId" ) );
		headerData.put( "SHIPTOFACILITYID",shipToFacilityId );

	  String customerpo="";
	  customerpo=BothHelpObjs.makeBlankFromNull( request.getParameter( "customerpo" ) );
	  headerData.put( "CUSTOMERPO",customerpo );

	  String bostartdate="";
	  bostartdate=BothHelpObjs.makeBlankFromNull( request.getParameter( "bostartdate" ) );
	  headerData.put( "BOSTARTDATE",bostartdate );

	  String paymentterms="";
	  paymentterms=BothHelpObjs.makeBlankFromNull( request.getParameter( "paymentterms" ) );
	  if ( "None".equalsIgnoreCase( paymentterms ) )
	  {
		paymentterms="";
	  }
	  headerData.put( "PAYMENTTERMS",paymentterms );

	  String boenddate="";
	  boenddate=BothHelpObjs.makeBlankFromNull( request.getParameter( "boenddate" ) );
	  headerData.put( "BOENDDATE",boenddate );

	  String faxdate="";
	  faxdate=BothHelpObjs.makeBlankFromNull( request.getParameter( "faxdate" ) );
	  headerData.put( "FAXDATE",faxdate );

	  String accepteddate="";
	  accepteddate=BothHelpObjs.makeBlankFromNull( request.getParameter( "accepteddate" ) );
	  headerData.put( "ACCEPTEDDATE",accepteddate );

	  String criticalpo="";
	  criticalpo=BothHelpObjs.makeBlankFromNull( request.getParameter( "criticalpo" ) );
	  headerData.put( "CRITICALPO",criticalpo );

	  String suppRatingval="";
	  suppRatingval=BothHelpObjs.makeBlankFromNull( request.getParameter( "suppRating" ) );
	  headerData.put( "SUPPRATING",suppRatingval );

	  String invengrp="";
	  invengrp=BothHelpObjs.makeBlankFromNull( request.getParameter( "invengrp" ) );
	  headerData.put( "INVENTORY_GROUP",invengrp );

	  String consignedpo="";
	  consignedpo=BothHelpObjs.makeBlankFromNull( request.getParameter( "consignedpo" ) );
	  headerData.put( "CONSIGNED_PO",consignedpo );

	  finalData.put( "HEADERDATA",headerData );

	  String nooflines="";
	  nooflines=BothHelpObjs.makeBlankFromNull( request.getParameter( "totallines" ) );

	  int noofLine=Integer.parseInt( nooflines );

	  int i=0; //used for detail lines
	  for ( int loop=0; loop < ( noofLine ); loop++ )
	  {
		i++;
		Hashtable hD=new Hashtable();

		String linestatus="";
		linestatus=BothHelpObjs.makeBlankFromNull( request.getParameter( "linestatus" + i ) );
		hD.put( "LINESTATUS",linestatus );

		String linechange="";
		linechange=BothHelpObjs.makeBlankFromNull( request.getParameter( "linechange" + i ) );
		hD.put( "LINECHANGE",linechange );

		String rowlinenumber="";
		rowlinenumber=BothHelpObjs.makeBlankFromNull( request.getParameter( "row" + i + "linenumber" ) );
		hD.put( "ROWNUMBER",rowlinenumber );

		String ammendment="";
		ammendment=BothHelpObjs.makeBlankFromNull( request.getParameter( "ammendment" + i ) );
		hD.put( "AMMENDMENT",ammendment );

		String ammendmentcomments="";
		ammendmentcomments=BothHelpObjs.makeBlankFromNull( request.getParameter( "ammendmentcomments" + i ) );
		hD.put( "AMMENDMENTCOMMENTS",ammendmentcomments );

		String lookaheadchanged="";
		lookaheadchanged=BothHelpObjs.makeBlankFromNull( request.getParameter( "lookaheadchanged" + i ) );
		hD.put( "LOOKAHEADCHANGED",lookaheadchanged );

		String originallinestatus="";
		originallinestatus=BothHelpObjs.makeBlankFromNull( request.getParameter( "originallinestatus" + i ) );
		hD.put( "ORIGINALLINESTATUS",originallinestatus );

		String lineitemid="";
		lineitemid=BothHelpObjs.makeBlankFromNull( request.getParameter( "lineitemid" + i ) );
		hD.put( "LINEITEMID",lineitemid );

		String itemtype="";
		itemtype=BothHelpObjs.makeBlankFromNull( request.getParameter( "itemtype" + i ) );
		hD.put( "ITEMTYPE",itemtype );

		String dateneeded="";
		dateneeded=BothHelpObjs.makeBlankFromNull( request.getParameter( "dateneeded" + i ) );
		hD.put( "DATENEEDED",dateneeded );

		String datepromised="";
		datepromised=BothHelpObjs.makeBlankFromNull( request.getParameter( "datepromised" + i ) );
		hD.put( "DATEPROMISED",datepromised );

		String projsuppshipdate="";
		projsuppshipdate=BothHelpObjs.makeBlankFromNull( request.getParameter( "projsuppshipdate" + i ) );
		hD.put( "PROJECTEDELIVDATE",projsuppshipdate );

		String lineqty="";
		lineqty=BothHelpObjs.makeBlankFromNull( request.getParameter( "lineqty" + i ) );
		hD.put( "LINEQTY",lineqty );

		String lineunitprice="";
		lineunitprice=BothHelpObjs.makeBlankFromNull( request.getParameter( "lineunitprice" + i ) );
		hD.put( "LINEUNITPRICE",lineunitprice );

		String mpn="";
		mpn=BothHelpObjs.makeBlankFromNull( request.getParameter( "mpn" + i ) );
		hD.put( "MANUFACTURERPARTNO",mpn );

		String supplierpn="";
		supplierpn=BothHelpObjs.makeBlankFromNull( request.getParameter( "supplierpn" + i ) );
		hD.put( "SUPPLIERPARTNO",supplierpn );

		String supplierqty="";
		supplierqty=BothHelpObjs.makeBlankFromNull( request.getParameter( "supplierqty" + i ) );
		hD.put( "SUPPLIERQTY",supplierqty );

		String supplierpkg="";
		supplierpkg=BothHelpObjs.makeBlankFromNull( request.getParameter( "supplierpkg" + i ) );
		hD.put( "SUPPLIERPKG",supplierpkg );

		String supplierunitprice="";
		supplierunitprice=BothHelpObjs.makeBlankFromNull( request.getParameter( "supplierunitprice" + i ) );
		hD.put( "SUPPLIERUNITPRICE",supplierunitprice );

		String shelflife="";
		shelflife=BothHelpObjs.makeBlankFromNull( request.getParameter( "shelflife" + i ) );
		hD.put( "SHELFLIFE",shelflife );

		String dpas="";
		dpas=BothHelpObjs.makeBlankFromNull( request.getParameter( "dpas" + i ) );
		hD.put( "DPAS",dpas );

		String linenotesdivrow="";
		linenotesdivrow=BothHelpObjs.makeBlankFromNull( request.getParameter( "linenotesdivrow" + i + "" + i + "" ) );
		hD.put( "LINENOTES",linenotesdivrow );

		String deliverylinenotesdivrow="";
		deliverylinenotesdivrow=BothHelpObjs.makeBlankFromNull( request.getParameter( "deliverylinenotesdivrow" + i + "" + i + "" ) );
		hD.put( "DELIVERYLINENOTES",deliverylinenotesdivrow );

		String currency="";
		currency=BothHelpObjs.makeBlankFromNull( request.getParameter( "currency" ) );
		hD.put( "CURRENCY_ID",currency );

		String secondarySupplier="";
		secondarySupplier=BothHelpObjs.makeBlankFromNull( request.getParameter( "secondarysupplier" + i ) );
		hD.put( "SECONDARY_SUPPLIER",secondarySupplier );

		String shipFromLocationId="";
		shipFromLocationId=BothHelpObjs.makeBlankFromNull( request.getParameter( "shipFromLocationId" + i ) );
		hD.put( "SHIP_FROM_LOCATION_ID",shipFromLocationId );

		String supplierSalesOrderNo="";
		supplierSalesOrderNo=BothHelpObjs.makeBlankFromNull( request.getParameter( "supplierSalesOrderNo" + i ) );
		hD.put( "SUPPLIER_SALES_ORDER_NO",supplierSalesOrderNo );

		String everConfirmed="";
		everConfirmed=BothHelpObjs.makeBlankFromNull( request.getParameter( "everConfirmed" + i ) );
	    hD.put( "LINE_EVER_CONFIRMED",everConfirmed );
	    
	    String dataTransferStatusClosed="";
	    dataTransferStatusClosed=BothHelpObjs.makeBlankFromNull( request.getParameter( "dataTransferStatusClosed" + i ) );
		hD.put( "dataTransferStatusClosed",dataTransferStatusClosed );
                            
	    String noofspecs="";
		noofspecs=BothHelpObjs.makeBlankFromNull( request.getParameter( "noofspecsdivrow" + i + "" + i + "" ) );

		if ( noofspecs.trim().length() > 0 )
		{
		  hD.put( "NOOFSPECS",noofspecs );

		  int noofSpecs=Integer.parseInt( noofspecs );
		  Vector specs=new Vector();
		  for ( int loop1=0; loop1 < noofSpecs; loop1++ )
		  {
			Hashtable spechD=new Hashtable();

			/*String specattachchk="";
			specattachchk=BothHelpObjs.makeBlankFromNull( request.getParameter( "specattachchk" + i + "_" + loop1 + "" ) );
			spechD.put( "ATTACHSPEC",specattachchk );*/

			/*String specokchk="";
			specokchk=BothHelpObjs.makeBlankFromNull( request.getParameter( "specokchk" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCOMPLIANCE",specokchk );*/

			String speccocchk="";
			speccocchk=BothHelpObjs.makeBlankFromNull( request.getParameter( "speccocchk" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCOCCMPLIANCE",speccocchk );

			String speccoachk="";
			speccoachk=BothHelpObjs.makeBlankFromNull( request.getParameter( "speccoachk" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCOACMPLIANCE",speccoachk );

			String specdownid="";
			specdownid=BothHelpObjs.makeBlankFromNull( request.getParameter( "specdownid" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECID",specdownid );

			/*String specdowncompanyid="";
			specdowncompanyid=BothHelpObjs.makeBlankFromNull( request.getParameter( "specdowncompanyid" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCOMPANYID",specdowncompanyid );*/

			String specdowncatalogid="";
			specdowncatalogid=BothHelpObjs.makeBlankFromNull( request.getParameter( "specdowncatalogid" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCATALOGID",specdowncatalogid );

			String specdowncatpartno="";
			specdowncatpartno=BothHelpObjs.makeBlankFromNull( request.getParameter( "specdowncatpartno" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCATPARTNO",specdowncatpartno );

			String specdownspeclibrary="";
			specdownspeclibrary=BothHelpObjs.makeBlankFromNull( request.getParameter( "specdownspeclibrary" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECLIBRARY",specdownspeclibrary );

			/*String speccertreq="";
			speccertreq=BothHelpObjs.makeBlankFromNull( request.getParameter( "speccertreq" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCERTREQ",speccertreq );*/

			String specdowndetail="";
			specdowndetail=BothHelpObjs.makeBlankFromNull( request.getParameter( "specdowndetail" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECDETAIL",specdowndetail );

			String specCurrentCocRequirement="";
			specCurrentCocRequirement=BothHelpObjs.makeBlankFromNull( request.getParameter( "specCurrentCocRequirement" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCERTCOCREQ",specCurrentCocRequirement );

			String specCurrentCoaRequirement="";
			specCurrentCoaRequirement=BothHelpObjs.makeBlankFromNull( request.getParameter( "specCurrentCoaRequirement" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCERTCOAREQ",specCurrentCoaRequirement );

			String specColor="";
			specColor=BothHelpObjs.makeBlankFromNull( request.getParameter( "specColor" + i + "_" + loop1 + "" ) );
			spechD.put( "SPECCOLOR",specColor );

			specs.addElement( spechD );
		  }
		  hD.put( "SPECLINES",specs );
		}
		else
		{
		  hD.put( "NOOFSPECS","0" );
		}

		String noofflowdowns="";
		noofflowdowns=BothHelpObjs.makeBlankFromNull( request.getParameter( "noofflowdownsdivrow" + i + "" + i + "" ) );
		if ( noofflowdowns.trim().length() > 0 )
		{
		  hD.put( "NOOFFLOWDOWNS",noofflowdowns );

		  int noofFlowdowns=Integer.parseInt( noofflowdowns );
		  Vector flows=new Vector();
		  for ( int loop1=0; loop1 < noofFlowdowns; loop1++ )
		  {
			Hashtable flowshD=new Hashtable();

			/*String flowattachchk="";
			flowattachchk=BothHelpObjs.makeBlankFromNull( request.getParameter( "flowattachchk" + i + "_" + loop1 + "" ) );
			flowshD.put( "ATTACHFLOW",flowattachchk );*/

			String flowokchk="";
			flowokchk=BothHelpObjs.makeBlankFromNull( request.getParameter( "flowokchk" + i + "_" + loop1 + "" ) );
			flowshD.put( "FLOWCOMPLIANCE",flowokchk );

			String flowdownid="";
			flowdownid=BothHelpObjs.makeBlankFromNull( request.getParameter( "flowdownid" + i + "_" + loop1 + "" ) );
			flowshD.put( "FLOWID",flowdownid );

			String flowdowncompanyid="";
			flowdowncompanyid=BothHelpObjs.makeBlankFromNull( request.getParameter( "flowdowncompanyid" + i + "_" + loop1 + "" ) );
			flowshD.put( "FLOWCOMPANYID",flowdowncompanyid );

			String flowdowncatalogid="";
			flowdowncatalogid=BothHelpObjs.makeBlankFromNull( request.getParameter( "flowdowncatalogid" + i + "_" + loop1 + "" ) );
			flowshD.put( "FLOWCATALOGID",flowdowncatalogid );

			String flowdowncatpartno="";
			flowdowncatpartno=BothHelpObjs.makeBlankFromNull( request.getParameter( "flowdowncatpartno" + i + "_" + loop1 + "" ) );
			flowshD.put( "FLOWCATPARTNO",flowdowncatpartno );

			flows.addElement( flowshD );

		  }
		  hD.put( "FLOWLINES",flows );
		}
		else
		{
		  hD.put( "NOOFFLOWDOWNS","0" );
		}

		String cocforspec="";
		cocforspec=BothHelpObjs.makeBlankFromNull( request.getParameter( "cocforspec" + i ) );
		hD.put( "COCFORSPEC",cocforspec );

		String coaforspec="";
		coaforspec=BothHelpObjs.makeBlankFromNull( request.getParameter( "coaforspec" + i ) );
		hD.put( "COAFORSPEC",coaforspec );

		String cocforflow="";
		cocforflow=BothHelpObjs.makeBlankFromNull( request.getParameter( "cocforflow" + i ) );
		hD.put( "COCFORFLOW",cocforflow );

		String coaforflow="";
		coaforflow=BothHelpObjs.makeBlankFromNull( request.getParameter( "coaforflow" + i ) );
		hD.put( "COAFORFLOW",coaforflow );

		String checkformsdsasked="";
		checkformsdsasked=BothHelpObjs.makeBlankFromNull( request.getParameter( "checkformsdsasked" + i ) );
		hD.put( "CHECKFORMSDS",checkformsdsasked );

		String nooflinesinaddcharge="";
		nooflinesinaddcharge=BothHelpObjs.makeBlankFromNull( request.getParameter( "nooflinesinaddcharge" + i ) );

		if ( nooflinesinaddcharge.trim().length() > 0 )
		{
		  hD.put( "NOOFLINESINADDCHARGE",nooflinesinaddcharge );
		  int noofAddchargelines=Integer.parseInt( nooflinesinaddcharge );
		  Vector addchargelines=new Vector();
		  for ( int loop1=0; loop1 < noofAddchargelines; loop1++ )
		  {
			Hashtable chargehD=new Hashtable();

			String addchargecheck="";
			addchargecheck=BothHelpObjs.makeBlankFromNull( request.getParameter( "addchargecheckdivrow" + i + "" + i + "" + ( loop1 + 1 ) + "" ) );
			chargehD.put( "ADDCHARGECHECK",addchargecheck );

			String chargelinenumber="";
			chargelinenumber=BothHelpObjs.makeBlankFromNull( request.getParameter( "chargelinenumber" + ( loop1 + 1 ) + "" ) );
			chargehD.put( "CHARGETOLINE",chargelinenumber );

			String chargeAncnumber="";
			chargeAncnumber=BothHelpObjs.makeBlankFromNull( request.getParameter( "chargeAncnumber" + i + "" + chargelinenumber + "" ) );
			chargehD.put( "chargeAncnumber",chargeAncnumber );

			addchargelines.addElement( chargehD );
		  }
		  hD.put( "CHARGELINEDETAILS",addchargelines );
		}
		else
		{
		  hD.put( "NOOFLINESINADDCHARGE","0" );
		}

		String nooflookaheads="";
		nooflookaheads=BothHelpObjs.makeBlankFromNull( request.getParameter( "nooflookaheads" + i ) );

		if ( nooflookaheads.trim().length() > 0 )
		{
		  hD.put( "NOOFLOOKAHEADS",nooflookaheads );
		  int noofLookAheads=Integer.parseInt( nooflookaheads );
		  Vector lookaheadlines=new Vector();
		  for ( int loop1=0; loop1 < noofLookAheads; loop1++ )
		  {
			Hashtable lookaheadhD=new Hashtable();

			String lookaheadcompanyid="";
			lookaheadcompanyid=BothHelpObjs.makeBlankFromNull( request.getParameter( "lookaheadcompanyid" + i + "_" + loop1 + "" ) );
			lookaheadhD.put( "LOOKAHDCOMPANYID",lookaheadcompanyid );

			String lookaheadcatalogid="";
			lookaheadcatalogid=BothHelpObjs.makeBlankFromNull( request.getParameter( "lookaheadcatalogid" + i + "_" + loop1 + "" ) );
			lookaheadhD.put( "LOOKAHDCATALOGID",lookaheadcatalogid );

			String lookaheadcatpartno="";
			lookaheadcatpartno=BothHelpObjs.makeBlankFromNull( request.getParameter( "lookaheadcatpartno" + i + "_" + loop1 + "" ) );
			lookaheadhD.put( "LOOKAHDCATPARTNO",lookaheadcatpartno );

			String lookaheadinventorygrp="";
			lookaheadinventorygrp=BothHelpObjs.makeBlankFromNull( request.getParameter( "lookaheadinventorygrp" + i + "_" + loop1 + "" ) );
			lookaheadhD.put( "LOOKAHDINVGRP",lookaheadinventorygrp );

			String lookaheadpartgrpno="";
			lookaheadpartgrpno=BothHelpObjs.makeBlankFromNull( request.getParameter( "lookaheadpartgrpno" + i + "_" + loop1 + "" ) );
			lookaheadhD.put( "LOOKAHDPARTGRPNO",lookaheadpartgrpno );

			String lookaheaddays="";
			lookaheaddays=BothHelpObjs.makeBlankFromNull( request.getParameter( "lookaheaddays" + i + "_" + loop1 + "" ) );
			lookaheadhD.put( "LOOKAHDNODAYS",lookaheaddays );

			lookaheadlines.addElement( lookaheadhD );
		  }
		  hD.put( "LOOKAHEADLINEDETAILS",lookaheadlines );
		}
		else
		{
		  hD.put( "NOOFLOOKAHEADS","0" );
		}

		lineData.addElement( hD );
	  }
	}
	catch ( Exception e )
	{
	  //e.printStackTrace();
	}

	finalData.put( "LINEDETAILS",lineData );

	return finalData;
  }
}