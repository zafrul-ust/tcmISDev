package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
//import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringEscapeUtils;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.common.util.StringHandler;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.util.ResourceLibrary;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 10-29-02
 * changed the code to not get spec,flowdowns, item noted anf tcm history if the line is closed or cnaceled.
 * Also changed the javascript to ignore the valid spec and valid flowdown values when the form is submitted
 *
 * Also showing MR Qty in the buy details and the edit associate pr screen and all other relted stuff
 *
 * 10-30-02
 * Added a link in tcmbuy history to po to open it.
 *
 * 11-06-02
 * showing the status as unconfirmed if there is no date confirmed
 * 11-12-02
 * Passing null for Hub if they do not select any Hub in po search
 * also showing Date Created if there is no Date Confirmed
 * 11-13-02
 * Adding the critical option to the po and purchaser notes to customer on po line
 *
 * 11-15-02
 * Part Number can have escape characters
 *
 * Changing the way the cursour looks
 *
 * 11-19-02
 * ADDING LOOK aHEAD
 *
 * 11-20-02
 * Added code to not allow edit PR is it is frozen
 *
 * 11-22-02
 * making LOOK AHEADs distinct
 *
 * 01-01-03
 * Taking away the Edit Pr's Button if the line status is canceled.
 *
 * 01-13-03
 * adding the new column PROJECTED_DELIVERY_DATE
 *
 * 01-17-03
 * Showing LPP for the buy orders
 *
 *01-19-02
 *in updatePoLine promiseddate Projected Delivery date is and projdeliverydate Promised Ship Date is
 *but they are saved different in databse promiseddate is PROJECTED_DELIVERY_DATE and projdeliverydate is PROMISED_DATE
 *
 * 02-03-03
 * Showing only 10 Buy History showing all later if asked for
 *
 * 02-10-03
 * When creating a New PO based on a Blanket PO setting the ammendment numbers to 0 and removing the Qty, Ext Price and the total price
 * from the PO
 * call p_cpi_rli_allocate when lookahead is changed
 * 03-06-03
 * Changed the spec image URL
 * 03-10-03
 * Looking if the lookahead has changed
 * 03-25-03 :Checking the ok and COC and COA boxes when the po id pulled up before confirmation and purple is number 4 now changed method is showspec
 * 04-02-03 : Not checking the specs ok boxes
 * 04-08-03 : Fixed the supplier ext price
 * 05-06-06 Added supplier qualifying level to the search and to the save
 * 05-20-03 Shwoing the qty returned
 * 05-27-03 Showing if the item is stocked or not
 * 07-22-03 Code cleanup and changed the name of the cells in the table to fix the bug with POs with many lines
 * 08-01-03 Added item_Id,item_dec etc in the po search screen
 * 08-18-03 Changed the qtyreceived vlue parsing problem. The parsing it to float was working fine in Java but giving problem in javascript. eg.17406919 because 17406920 in javascript
 * 09-15-03 - Showing the Address line 3 of the ship to address
 * 10-20-03 - Added consigned PO and payment terms are diabeled if the line is vouchered
 * 12-03-03 - MAking changes so that additional charges are assinged to item_type which can have add charges. Removing the item_type MA and BG hard coding.
 * 12-05-03 - Changes to make Blanket Orders work
 * 12-16-03 - Super Spec changes
 * 12-17-03 - Some more changes to make creating PO from Blanket easy
 * 12-22-03 - Making the session Keys Unique to PO pages. And Code Refactoring moved vv_stuff to different class
 * 01-07-04 - When creating a PO from BO unlocking the date fields only for lines that can be assinged to additional charges
 * 02-16-04 - Shwoing the qty remaining on Blanket Orders. Showing Item Verified By and Verifed Date
 * 02-29-04 - Once a PO is vouchered the ship to, carrier, fob and coustomer PO information can not be changed
 * 03-08-04 - Can not change carrier after a PO is vouchered only if the carrier is a haas carrier
 * 03-17-04 - Cna not change consigned status after a PO is received. Also Showing email address of supplier and supplier contact
 * 05-17-04 - Spec and Flow Downs are always shown.
 * 09-09-04 - New LPP query.
 * 10-13-04 - Showing the catalog commetns for each part in a inventory group
 * 08-14-06 - Giving Option to enter sole source for MV and MA items and expedite notes
 * 12-20-06 - Allowing to change secondary supplier until a voucher is entered.
 * 02-05-07 - Removing date confirmed from the header section.
 */

public class soundsLikePurchaseOrder
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    //private DBResultSet dbrs = null;
    //private ResultSet rs = null;
    private Vector addchargeslist = null;
		private Hashtable vvFob = null;
    private String privatepersonnelId = "";
    private String nematid_Servlet = "";
    private boolean allowupdate;
		private boolean specOverRide;
    private static org.apache.log4j.Logger polog = org.apache.log4j.Logger.getLogger(purchaseOrderCreator.class);
    private ResourceLibrary res = null; // res means resource.
    boolean isOneUSD = false;
    BigDecimal orderTotal = new BigDecimal(0);
    Vector<String []> orderTotalColl = new Vector<String []>();
    String countryAbbrev = null;
    String requiresFinancialApproval = null;

    public soundsLikePurchaseOrder(ServerResourceBundle b, TcmISDBModel d)
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

		public void setSpecOverRide(boolean id)
		{
		 this.specOverRide = id;
		}

    private boolean getSpecOverRide()
    {
		 return this.specOverRide;
		}

    public void doResult(HttpServletRequest request, HttpServletResponse response,PrintWriter out)  throws ServletException,  IOException
    {
    //PrintWriter out = response.getWriter();
    //response.setContentType("text/html");
    HttpSession posession = request.getSession();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)posession.getAttribute("tcmISLocale"));    
    String FullName=posession.getAttribute( "FULLNAME" ) == null ? "" : posession.getAttribute( "FULLNAME" ).toString();
    String personnelid=posession.getAttribute( "PERSONNELID" ) == null ? "" : posession.getAttribute( "PERSONNELID" ).toString();
    countryAbbrev = ((PersonnelBean) posession.getAttribute("personnelBean")).getCountryAbbrev();
    privatepersonnelId = personnelid;

    String HubName = "";
    String Action = "";
    String subUserAction = "";
    String searchString = "";
    String searchString1 = "";
    String radianPO = "";
    String openOrders = "";
    String sortBy = "";
    String sortBy1 = "";
    String lineID = "";

    try{openOrders = request.getParameter("openOrders").toString().trim();}catch (Exception e1){openOrders = "";}
    String radianBPOadd = "";
    try{radianBPOadd = request.getParameter("radianBPOadd").toString().trim();}catch (Exception e1){radianBPOadd = "";}
    String onlymypos = "";
    try{onlymypos = request.getParameter("onlymypos").toString().trim();}catch (Exception e1){onlymypos = "";}
    String unconfirmedOrders = "";
    try{unconfirmedOrders = request.getParameter("unconfirmedOrders").toString().trim();}catch (Exception e1){unconfirmedOrders = "";}
    String brokenprOrders = "";
    try{brokenprOrders = request.getParameter("brokenprOrders").toString().trim();}catch (Exception e1){brokenprOrders = "";}
    try{sortBy = request.getParameter("SortBy").toString().trim();}catch (Exception e1){sortBy = "";}
    try{sortBy1 = request.getParameter("SortBy1").toString().trim();}catch (Exception e1){sortBy1 = "";}
    try{HubName = request.getParameter("HubName").toString().trim();}catch (Exception e1){HubName = "0";}
    try{Action = request.getParameter("Action").toString().trim();}catch (Exception e1){Action = "";}
    try{searchString = request.getParameter("SearchString").toString().trim();}catch (Exception e1){searchString = "";}
    try{searchString1 = request.getParameter("SearchString1").toString().trim();}catch (Exception e1){searchString1 = "";}
    try{radianPO = request.getParameter("radianPO").toString().trim();}catch (Exception e1){radianPO = "";}
    try{lineID = request.getParameter("lineID").toString().trim();}catch (Exception e1){lineID = "";}
    try{subUserAction = request.getParameter("subUserAction").toString().trim();}catch (Exception e1){subUserAction = "";}
    String ammendMent = "";
    try{ammendMent = request.getParameter("ammendment").toString();}catch (Exception e1){ammendMent = "";}

	String comingfrom=request.getParameter( "comingfrom" );
	if ( comingfrom == null )
	  comingfrom="";
	comingfrom=comingfrom.trim();

	String newpobpo=request.getParameter( "newpobpo" );
	if ( newpobpo == null )
	  newpobpo="";
	newpobpo=newpobpo.trim();

	String inventoryGroup=request.getParameter( "inventoryGroup" );
	if ( inventoryGroup == null )
		inventoryGroup="";
	inventoryGroup=inventoryGroup.trim();


    //polog.info("Here radianPO  " + radianPO + " Action   "+Action+" subUserAction  "+subUserAction+" searchString  "+searchString+"  unconfirmedOrders  "+unconfirmedOrders+" brokenprOrders  "+brokenprOrders);

	//Hashtable hub_list_set = new Hashtable();
	//hub_list_set= (Hashtable)posession.getAttribute("HUB_PREF_LIST");
	String donevvstuff=posession.getAttribute( "VVSUPPLYSTUFF" ) == null ? "" : posession.getAttribute( "VVSUPPLYSTUFF" ).toString();
	if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
	{
	  Hashtable hub_list_set=new Hashtable();
	  hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,"purch" );
	  posession.setAttribute( "PO_HUB_SET",hub_list_set);
	  posession.setAttribute( "PO_BUYERNAMES",radian.web.HTMLHelpObj.getbuyernames( db ) );
	}

    Hashtable hub_list_set  = (Hashtable)posession.getAttribute("PO_HUB_SET");
    addchargeslist = (Vector) posession.getAttribute("PO_ADD_CHARGE_ITEM_TYPE");
	vvFob= ( Hashtable ) posession.getAttribute( "PO_VV_FOB" );

    if ( "okupdate".equalsIgnoreCase( Action ) )
    {
      if ( "bpo".equalsIgnoreCase( subUserAction ) )
      {
         buildsendSupplierbpo( radianPO,out);
      }
      else
      {
         buildsendSupplierpo( radianPO,out);
      }
    }
    else if ( "refreshAddCharges".equalsIgnoreCase( Action ) )
    {
       refreshAddCharges( radianPO,lineID,ammendMent,radianBPOadd,out);
    }
    else if ( "New".equalsIgnoreCase( Action ) )
    {
       buildNewpo( subUserAction,FullName,personnelid,newpobpo,out);
    }

    else if ( "recalpo".equalsIgnoreCase( Action ) )
    {
       buildLikeSupplier( HubName,inventoryGroup,"recalpo",searchString,sortBy,radianPO,subUserAction,openOrders,searchString1,sortBy1,
                                      hub_list_set,brokenprOrders,unconfirmedOrders,personnelid,onlymypos,posession,comingfrom,out);
    }
    else if ( "Search".equalsIgnoreCase( Action ) )
    {
       buildLikeSupplier( HubName,inventoryGroup,"Search",searchString,sortBy,radianPO,subUserAction,openOrders,searchString1,sortBy1,
                                      hub_list_set,brokenprOrders,unconfirmedOrders,personnelid,onlymypos,posession,comingfrom,out);
    }
    else
    {
       buildLikeSupplier( HubName,inventoryGroup,"",searchString,sortBy,radianPO,subUserAction,openOrders,searchString1,sortBy1,
                                      hub_list_set,brokenprOrders,unconfirmedOrders,personnelid,onlymypos,posession,comingfrom,out);
    }
    out.close();
  }

   public void buildNewpo( String subuserAction, String buyerName, String buyerId, String bpo,PrintWriter poout)
   {
    //StringBuffer Msgn = new StringBuffer();
    //StringBuffer Msgbody = new StringBuffer();
	DBResultSet dbrs = null;
    ResultSet rs = null;
    nematid_Servlet = bundle.getString("PURCHASE_ORDER_PO");
    poout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Lookup","",false));
    poout.println("<script type=\"text/javascript\" src=\"/js/common/commonutil.js\"></script>");
    printJavaScripts(poout);
    poout.println("// -->\n </SCRIPT>\n");

    //StringBuffer Msgtemp = new StringBuffer();
    //Build the Java Script Here and Finish the thing
    poout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    poout.println("<!--\n");
    poout.println("function sendSupplierData()\n");
    poout.println("{\n");
    poout.println("messagerow = opener.document.getElementById(\"messagerow\");\n");
    if ("po".equalsIgnoreCase(subuserAction))
    {
      poout.println("messagerow.innerHTML = \"<FONT SIZE=\\\"3\\\" FACE=\\\"Arial\\\" COLOR=\\\"#fc0303\\\"><B>New PO</B></FONT>\";\n");
    }
    else
    {
      poout.println("messagerow.innerHTML = \"<FONT SIZE=\\\"3\\\" FACE=\\\"Arial\\\" COLOR=\\\"#fc0303\\\"><B>New Blanket PO</B></FONT>\";\n");
    }

    poout.println(" validbpo = opener.document.getElementById(\"validbpo\");\n");
    poout.println(" bponumber = opener.document.getElementById(\"bpo\");\n");
    poout.println(" totallines = opener.document.getElementById(\"totallines\");\n");
    poout.println(" if ( (validbpo.value != \"Yes\") || (bponumber.value.length == 0) )\n");
    poout.println(" {\n");
    poout.println("  opener.removeAllLines(); \n");
    poout.println("  totallines.value = \"0\";\n");
    poout.println("  totalcost = opener.document.getElementById(\"totalcost\");\n");
    poout.println("  totalcost.value = \"\";\n");
    poout.println("  searchsupplierlike = opener.document.getElementById(\"searchsupplierlike\");\n");
    poout.println("  searchsupplierlike.disabled=false;\n");
    poout.println("  searchsupplierlike.style.display=\"\";\n");
    poout.println("  supplierid = opener.document.getElementById(\"supplierid\");\n");
    poout.println("  supplierid.className = \"INVALIDTEXT\";\n");
    poout.println("  supplierid.readOnly = false;\n");
    poout.println("  supplierid.value = \"\";\n");
    poout.println("  validsupplier = opener.document.getElementById(\"validsupplier\");\n");
    poout.println("  validsupplier.value = \"No\";\n");
    poout.println("  supplierline1 = opener.document.getElementById(\"supplierline1\");\n");
    poout.println("  supplierline1.innerHTML = \"\";\n");
    poout.println("  supplierline2 = opener.document.getElementById(\"supplierline2\");\n");
    poout.println("  supplierline2.innerHTML = \"\";\n");
    poout.println("  supplierline3 = opener.document.getElementById(\"supplierline3\");\n");
    poout.println("  supplierline3.innerHTML = \"\";\n");
    poout.println("  supplierline4 = opener.document.getElementById(\"supplierline4\");\n");
    poout.println("  supplierline4.innerHTML = \"\";\n");
    poout.println("  fobd = opener.document.getElementById(\"fob\");\n");
    poout.println("  fobd.value = \"None\";\n");
		poout.println("  fobd.disabled = false;\n" );
    poout.println("  validordertaker = opener.document.getElementById(\"validordertaker\");\n");
    poout.println("  validordertaker.value = \"No\";\n");
    poout.println("  selectedRow = opener.document.getElementById(\"ordertaker\");\n");
    poout.println("  selectedRow.className = \"INVALIDTEXT\";\n");
    poout.println("  selectedRow.value = \"\";\n");
    poout.println("  selectedRow = opener.document.getElementById(\"ordertakerID\");\n");
    poout.println("  selectedRow.value = \"\";\n");
    poout.println("  selectedRow = opener.document.getElementById(\"paymentterms\");\n");
    poout.println("  selectedRow.value = \"Net 45\";\n");
	poout.println( "hubSlectName = opener.document.getElementById(\"HubName\");\n" );
	poout.println( "hubSlectName.value = \"None\";\n" );

	poout.println( "HubFullName = opener.document.getElementById(\"HubFullName\");\n" );
	poout.println( "HubFullName.value = \"\";\n" );

	poout.println("shiptoid = opener.document.getElementById(\"shiptoid\");\n");
	poout.println("shiptoid.className = \"INVALIDTEXT\";\n");
    poout.println("shiptoid.readOnly = false;\n");

	poout.println( "selectedRow = opener.document.getElementById(\"shiptocompanyid\");\n" );
	poout.println( "selectedRow.value = \"\";\n" );

	poout.println( "shipToFacilityId = opener.document.getElementById(\"shipToFacilityId\");\n" );
	poout.println( "shipToFacilityId.value = \"\";\n" );

	poout.println( "invengrp = opener.document.getElementById(\"invengrp\");\n" );
	poout.println( "invengrp.value = \"\";\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"validshipto\");\n" );
	poout.println( "selectedRow.value = \"No\";\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"shiptoline1\");\n" );
	poout.println( "selectedRow.innerHTML = \"\";\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"shiptoline2\");\n" );
	poout.println( "selectedRow.innerHTML = \"\";\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"shiptoline3\");\n" );
	poout.println( "selectedRow.innerHTML = \"\";\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"shiptoline4\");\n" );
	poout.println( "selectedRow.innerHTML = \"\";\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"shiptoline5\");\n" );
	poout.println( "selectedRow.innerHTML = \"\";\n" );

	poout.println( "carrierIDd = opener.document.getElementById(\"carrierID\");\n" );
	poout.println( "carrierIDd.className = \"INVALIDTEXT\";\n" );
	poout.println( "carrierIDd.value = \"\";\n" );
	poout.println( "carrierIDd.readOnly = false;\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"carrieraccount\");\n" );
	poout.println( "selectedRow.value = \"\";\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"validcarrier\");\n" );
	poout.println( "selectedRow.value = \"No\";\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"carrierline1\");\n" );
	poout.println( "selectedRow.innerHTML = \"\";\n" );

	poout.println( "selectedRow = opener.document.getElementById(\"carrierline2\");\n" );
	poout.println( "selectedRow.innerHTML = \"\";\n" );

	poout.println( "customerpo = opener.document.getElementById(\"customerpo\");\n" );
	poout.println( "customerpo.value = \"\";\n" );
	poout.println( "customerpo.readOnly = false;\n" );

	poout.println( "currencyid = opener.document.getElementById(\"currency\");\n" );
	poout.println( "currencyid.disabled = false;\n" );

	poout.println("searchshiptolike = opener.document.getElementById(\"searchshiptolike\");\n");
	poout.println("searchshiptolike.disabled=false;\n");
	poout.println("searchshiptolike.style.display=\"\";\n");

	poout.println( "searchcarrierlike = opener.document.getElementById(\"searchcarrierlike\");\n" );
	poout.println( "searchcarrierlike.disabled=false;\n" );
	poout.println( "searchcarrierlike.style.display=\"\";\n" );

	poout.println( "consignedpo = opener.document.getElementById(\"consignedpo\");\n" );
	poout.println( "consignedpo.disabled = false;\n" );

    poout.println(" }\n");
    poout.println(" else \n");
    poout.println(" {\n");

    poout.println("searchshiptolike = opener.document.getElementById(\"searchshiptolike\");\n");
    poout.println("searchshiptolike.disabled=false;\n");
    poout.println("searchshiptolike.style.display=\"\";\n");

	poout.println( "shiptoid = opener.document.getElementById(\"shiptoid\");\n" );
	poout.println( "if (shiptoid.value.length == 0) \n{\n");
	poout.println( "shiptoid.className = \"INVALIDTEXT\";\n" );
	poout.println( "shiptoid.value = \"\";\n" );
	poout.println( "selectedRow = opener.document.getElementById(\"validshipto\");\n" );
	poout.println( "selectedRow.value = \"No\";\n");
	poout.println( "invengrp = opener.document.getElementById(\"invengrp\");\n" );
	poout.println( "invengrp.value = \"\";}\n" );

	poout.println( "carrierID = opener.document.getElementById(\"carrierID\");\n" );
	poout.println( "if (carrierID.value.length == 0) \n{\n");
	poout.println( "carrierID.className = \"INVALIDTEXT\";\n" );
	poout.println( "carrierID.value = \"\";\n" );
	poout.println( "selectedRow = opener.document.getElementById(\"validcarrier\");\n" );
	poout.println( "selectedRow.value = \"No\";\n}\n" );

    poout.println("selectedRow = opener.document.getElementById(\"bostartdate\");\n");
    poout.println("selectedRow.className = \"INVISIBLEHEADWHITE\";\n");
    poout.println("selectedRow.readOnly = true;\n");

    poout.println("selectedRow = opener.document.getElementById(\"boenddate\");\n");
    poout.println("selectedRow.className = \"INVISIBLEHEADWHITE\";\n");
    poout.println("selectedRow.readOnly = true;\n");

    poout.println("   totalcost = opener.document.getElementById(\"totalcost\");\n");
    poout.println("   totalcost.value = \"0\";\n");

    poout.println("  for (j = 0; j<totallines.value; j++)\n");
    poout.println("  {\n");
    poout.println("   linechange = opener.document.getElementById(\"linechange\"+(j+1)+\"\");\n");
    poout.println("   linechange.value = \"Yes\";\n");

    poout.println("   ammendment = opener.document.getElementById(\"ammendment\"+(j+1)+\"\");\n");
    poout.println("   ammendment.value = \"0\";\n");

    poout.println("   lineqty = opener.document.getElementById(\"lineqty\"+(j+1)+\"\");\n");
    poout.println("   lineqty.value = \"\";\n");

    poout.println("   linenumber = opener.document.getElementById(\"linenumberammn\"+(j+1)+\"\");\n");
    poout.println("   linenumber.innerHTML = (j+1) + \"/\" + \"0\" ;\n");

    poout.println("   extPrice = opener.document.getElementById(\"extpricecell\"+(j+1)+\"\");\n");
    poout.println("   extPrice.innerHTML = \"\";\n");

    poout.println("   linetotalpriceo = opener.document.getElementById(\"linetotalprice\"+(j+1)+\"\");\n");
    poout.println("   linetotalpriceo.value = \"0\";\n");

	poout.println( "qtyreceivedcell = opener.document.getElementById(\"qtyreceivedcell\"+(j+1)+\"\");\n");
    poout.println( "qtyreceivedcell.innerHTML = \"\";\n" );

	poout.println( "qtyreceived = opener.document.getElementById(\"qtyreceived\"+(j+1)+\"\");\n");
	poout.println( "qtyreceived.value = \"0\";\n" );

	poout.println("   if ( j % 2 == 0 )\n");
	poout.println("   {	\n");
	poout.println("     itemColor=\"blue\";\n");
	poout.println("   }\n");
	poout.println("   else\n");
	poout.println("   {	\n");
	poout.println("     itemColor=\"white\";\n");
	poout.println("   }\n");

	poout.println( "canassignaddcharge = opener.document.getElementById(\"canassignaddcharge\"+(j+1)+\"\");\n" );
	poout.println( "if (canassignaddcharge.value == \"Y\")\n" );
	poout.println(" {\n");
	poout.println( "dateneeded = opener.document.getElementById(\"dateneeded\"+(j+1)+\"\");\n" );
	poout.println( "dateneeded.value = \"\";\n" );
	poout.println( "dateneeded.className = itemColor;\n" );
	poout.println( "dateneeded.readOnly = false;\n" );
	poout.println( "datelinkdateneeded = opener.document.getElementById(\"datelinkdateneeded\"+(j+1)+\"\");\n" );
	poout.println( "datelinkdateneeded.style.display=\"\";\n" );

	poout.println( "datepromised = opener.document.getElementById(\"datepromised\"+(j+1)+\"\");\n" );
	poout.println( "datepromised.value = \"\";\n" );
	poout.println( "datepromised.className = itemColor;\n" );
	poout.println( "datepromised.readOnly = false;\n" );
	poout.println( "datelinkdatepromised = opener.document.getElementById(\"datelinkdatepromised\"+(j+1)+\"\");\n" );
	poout.println( "datelinkdatepromised.style.display=\"\";\n" );

	poout.println( "projecteddelivdate = opener.document.getElementById(\"projsuppshipdate\"+(j+1)+\"\");\n" );
	poout.println( "projecteddelivdate.value = \"\";\n" );
	poout.println( "projecteddelivdate.className = itemColor;\n" );
	poout.println( "projecteddelivdate.readOnly = false;\n" );
	poout.println( "projecteddelivdate = opener.document.getElementById(\"datelinkprojsuppshipdate\"+(j+1)+\"\");\n" );
	poout.println( "projecteddelivdate.style.display=\"\";\n" );
	poout.println("  }\n");

	poout.println("   linestatus = opener.document.getElementById(\"linestatus\"+(j+1)+\"\");\n");
	poout.println("   linestatus.value = \"Draft\";\n");

	poout.println("   linestatustext = opener.document.getElementById(\"linestatustext\"+(j+1)+\"\");\n");
	poout.println("   linestatustext.innerHTML = \"Draft\";\n");

	poout.println( "  originallinestatus = opener.document.getElementById(\"originallinestatus\"+(j+1)+\"\");\n");
	poout.println( "  originallinestatus.value = \"Draft\";\n" );

    poout.println("  }\n");
    poout.println(" }\n");

    if ("bpo".equalsIgnoreCase(subuserAction))
    {
      poout.println("  opener.removeAllLines(); \n");
      poout.println("  totallines.value = \"0\";\n");
      poout.println("  selectedRow = opener.document.getElementById(\"totalcost\");\n");
      poout.println("  selectedRow.value = \"\";\n");

      poout.println("  selectedRow = opener.document.getElementById(\"ordertaker\");\n");
      poout.println("  selectedRow.className = \"INVALIDTEXT\";\n");
      poout.println("  selectedRow.value = \"\";\n");

      poout.println("  selectedRow = opener.document.getElementById(\"paymentterms\");\n");
      poout.println("  selectedRow.value = \"Net 45\";\n");
    }

    String radianpo="";
    try
    {
	  String bquery = "";
	  if ( "po".equalsIgnoreCase( subuserAction ) )
	  {
		bquery="select po_seq.nextval RADIANPO from dual";
	  }
	  else
	  {
		bquery="select blanket_po_sequence.nextval RADIANPO from dual";
	  }
      dbrs=db.doQuery( bquery );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        radianpo=rs.getString( "RADIANPO" );
      }

      if ( "po".equalsIgnoreCase( subuserAction ) )
      {
        String updpoquery="";

		/*if (bpo.length() > 0)
		{
		  updpoquery="insert into po (RADIAN_PO,BUYER,BPO) values (" + radianpo + "," + buyerId + ","+bpo+")";
		}
		else
		{
		  updpoquery="insert into po (RADIAN_PO,BUYER) values (" + radianpo + "," + buyerId + ")";
		}

    db.doUpdate( updpoquery );*/

        poout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
        poout.println( "selectedRow.className = \"HEADER\";\n" );
        poout.println( "selectedRow.value = \"" + radianpo + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"validpo\");\n" );
        poout.println( "selectedRow.value = \"Yes\";\n" );

        /*poout.println("selectedRow = opener.document.getElementById(\"validbpo\");\n");
            poout.println("selectedRow.value = \"Yes\";\n");*/

        poout.println( "selectedRow = opener.document.getElementById(\"searchbpolike\");\n" );
        poout.println( "selectedRow.disabled=true;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"Newbpo\");\n" );
        poout.println( "selectedRow.disabled=true;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"bpo\");\n" );
        poout.println( "selectedRow.className = \"INVISIBLEHEADWHITE\";\n" );
        poout.println( "selectedRow.readOnly = true;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
        poout.println( "selectedRow.className = \"HEADER\";\n" );
        poout.println( "selectedRow.readOnly = false;\n" );

        poout.println( "paymentterms = opener.document.getElementById(\"paymentterms\");\n" );
        poout.println( "paymentterms.value = \"Net 45\";\n" );

		poout.println("   qtyrechead = opener.document.getElementById(\"qtyrechead\");\n");
		poout.println("   qtyrechead.innerHTML = \"Qty Rec\";\n");

        /*poout.println("selectedRow = opener.document.getElementById(\"Newpo\");\n");
               poout.println("selectedRow.value = \"Yes\";\n");*/

      }
      else if ( "bpo".equalsIgnoreCase( subuserAction ) )
      {
        String updpoquery="insert into blanket_po (BPO,BUYER) values (" + radianpo + "," + buyerId + ")";
        db.doUpdate( updpoquery );

        poout.println( "selectedRow = opener.document.getElementById(\"bpo\");\n" );
        poout.println( "selectedRow.className = \"HEADER\";\n" );
        poout.println( "selectedRow.value = \"" + radianpo + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"validbpo\");\n" );
        poout.println( "selectedRow.value = \"Yes\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
        poout.println( "selectedRow.value = \"\";\n" );

        /*poout.println("selectedRow = opener.document.getElementById(\"validpo\");\n");
            poout.println("selectedRow.value = \"Yes\";\n");*/

        poout.println( "selectedRow = opener.document.getElementById(\"searchpolike\");\n" );
        poout.println( "selectedRow.disabled=true;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"Newpo\");\n" );
        poout.println( "selectedRow.disabled=true;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
        poout.println( "selectedRow.className = \"INVISIBLEHEADWHITE\";\n" );
        poout.println( "selectedRow.readOnly = true;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"bostartdate\");\n" );
        poout.println( "selectedRow.className = \"HEADER\";\n" );
        poout.println( "selectedRow.readOnly = false;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"boenddate\");\n" );
        poout.println( "selectedRow.className = \"HEADER\";\n" );
        poout.println( "selectedRow.readOnly = false;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"bpo\");\n" );
        poout.println( "selectedRow.className = \"HEADER\";\n" );
        poout.println( "selectedRow.readOnly = false;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"bostartdate\");\n" );
        poout.println( "selectedRow.value = \"\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"boenddate\");\n" );
        poout.println( "selectedRow.value = \"\";\n" );

		poout.println("   qtyrechead = opener.document.getElementById(\"qtyrechead\");\n");
		poout.println("   qtyrechead.innerHTML = \"Qty Rem\";\n");

        /*poout.println("selectedRow = opener.document.getElementById(\"Newbpo\");\n");
               poout.println("selectedRow.value = \"Yes\";\n");*/
      }

      poout.println( "selectedRow = opener.document.getElementById(\"buyer\");\n" );
      poout.println( "selectedRow.value = \"" + buyerName + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"buyerid\");\n" );
      poout.println( "selectedRow.value = \"" + buyerId + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"faxdate\");\n" );
      poout.println( "selectedRow.value = \"\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"accepteddate\");\n" );
      poout.println( "selectedRow.value = \"\";\n" );


    }
    catch ( Exception e )
    {
      //e.printStackTrace();
    }
    finally
    {
      if ( dbrs != null ){dbrs.close();}
    }

    poout.println("opener.makeCursorNormal();\n");
    poout.println("window.close();\n");
    poout.println(" }\n");
    poout.println("// -->\n</SCRIPT>\n");
    //poout.println(Msgtemp);
    poout.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
	poout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    poout.println("<CENTER><BR><BR>\n");
    poout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    poout.println("</FORM></BODY></HTML>\n");
    //poout.println(Msgbody);

    return;
   }

    public void buildsendSupplierpo( String radianPO,PrintWriter poout)
   {
    //StringBuffer Msgn = new StringBuffer();
    //StringBuffer Msgbody = new StringBuffer();
	DBResultSet dbrs = null;
    ResultSet rs = null;
    String invengrp = "";
    nematid_Servlet = bundle.getString("PURCHASE_ORDER_PO");
    poout.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Lookup","",false));
    poout.println("<script type=\"text/javascript\" src=\"/js/common/commonutil.js\"></script>");
    printJavaScripts(poout);
    poout.println("// -->\n </SCRIPT>\n");

    //StringBuffer Msgtemp = new StringBuffer();

    //Build the Java Script Here and Finish the thing
    poout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    poout.println("<!--\n");
    poout.println("function sendSupplierData()\n");
    poout.println("{\n");
    poout.println("opener.removeAllLines(); \n");


    String supplier = "";
    String ship_to_location_id = "";
    String carrier = "";
    String bo = "";
    String branch_plant1 = "";
    String ship_to_company_id = "";
    String opsEntityId ="";
     try
     {
	String poquery = "Select SHIP_TO_ADDRESS_LINE_1_DISPLAY, SHIP_TO_ADDRESS_LINE_2_DISPLAY, SHIP_TO_ADDRESS_LINE_3_DISPLAY, SHIP_TO_ADDRESS_LINE_4_DISPLAY, SHIP_TO_ADDRESS_LINE_5_DISPLAY, SUPPLR_ADDRESS_LINE_1_DISPLAY, SUPPLR_ADDRESS_LINE_2_DISPLAY, SUPPLR_ADDRESS_LINE_3_DISPLAY, SUPPLR_ADDRESS_LINE_4_DISPLAY, SUPPLR_ADDRESS_LINE_5_DISPLAY,INTERCOMPANY_MR,OPERATING_ENTITY_NAME, OPS_ENTITY_ID,SUPPLIER_DEFAULT_PAYMENT_TERMS,SHIP_TO_FACILITY_ID,NONINTEGER_RECEIVING,CHANGE_SUPPLIER,EMAIL, SUPPLIER_EMAIL,RECEIVED_STATUS,HAAS_CARRIER,CONSIGNED_PO,VOUCHERED,ATTACHED_PR,INVENTORY_GROUP,CRITICAL,RADIAN_PO,BO,EVER_CONFIRMED,SUPPLIER,SUPPLIER_CONTACT_ID,SUPPLIER_CONTACT_NAME,BUYER,BUYER_NAME,SHIP_TO_COMPANY_ID, ";
	poquery += "SHIP_TO_LOCATION_ID,BRANCH_PLANT,FREIGHT_ON_BOARD,PAYMENT_TERMS,CARRIER,CARRIER_ACCOUNT,CUSTOMER_PO, ";
	poquery += "to_char(DATE_SENT,'mm/dd/yyyy') DATE_SENT,to_char(DATE_ACCEPTED,'mm/dd/yyyy') DATE_ACCEPTED, ";
	poquery += "to_char(BO_START_DATE,'mm/dd/yyyy') BO_START_DATE,to_char(BO_END_DATE,'mm/dd/yyyy') BO_END_DATE,QUALIFICATION_LEVEL, ";
	poquery += "SUPPLIER_NAME,SUPPLIER_CONTACT_NAME,CARRIER_COMPANY_ID,CARRIER_ACCOUNT,CARRIER_NAME,CARRIER_HUB,SUPPLIER_COUNTRY_ABBREV,SUPPLIER_STATE_ABBREV, ";
	poquery += "SUPPLIER_ADDRESS_LINE_1,SUPPLIER_ADDRESS_LINE_2,SUPPLIER_ADDRESS_LINE_3,SUPPLIER_CITY,SUPPLIER_ZIP,SUPPLIER_LOCATION_DESC,SHIPTO_COUNTRY_ABBREV, ";
	poquery += "SUPPLIER_PHONE,SUPPLIER_CONTACT_PHONE,SUPPLIER_CONTACT_FAX,SHIPTO_STATE_ABBREV,SHIPTO_ADDRESS_LINE_1,SHIPTO_ADDRESS_LINE_2,SHIPTO_ADDRESS_LINE_3,SHIPTO_CITY,SHIPTO_ZIP,SHIPTO_LOCATION_DESC,REQUIRES_FINANCIAL_APPROVAL ";
	poquery += "from po_header_view where radian_po = "+radianPO+" ";

	dbrs = db.doQuery(poquery);
	rs=dbrs.getResultSet();

	while(rs.next())
	{
	  String radian_po = rs.getString("RADIAN_PO")==null?"":rs.getString("RADIAN_PO").trim();
	  bo = rs.getString("BO")==null?"":rs.getString("BO").trim();
	  supplier = HelpObjs.addescapesForJavascript(rs.getString("SUPPLIER")==null?"":rs.getString("SUPPLIER").trim());
	  String supplier_contact_id = rs.getString("SUPPLIER_CONTACT_ID")==null?"":rs.getString("SUPPLIER_CONTACT_ID").trim();
	  String supplier_contact_name = rs.getString("SUPPLIER_CONTACT_NAME")==null?"":rs.getString("SUPPLIER_CONTACT_NAME").trim();
	  String buyer = rs.getString("BUYER")==null?"":rs.getString("BUYER").trim();
	  String buyername = rs.getString("BUYER_NAME")==null?"":rs.getString("BUYER_NAME").trim();
	  ship_to_company_id = rs.getString("SHIP_TO_COMPANY_ID")==null?"":rs.getString("SHIP_TO_COMPANY_ID").trim();
	  ship_to_location_id = rs.getString("SHIP_TO_LOCATION_ID")==null?"":rs.getString("SHIP_TO_LOCATION_ID").trim();
	  branch_plant1 = rs.getString("BRANCH_PLANT")==null?"":rs.getString("BRANCH_PLANT").trim();
	  String freight_on_board = rs.getString("FREIGHT_ON_BOARD")==null?"":rs.getString("FREIGHT_ON_BOARD").trim();
	  String payment_term = rs.getString("PAYMENT_TERMS")==null?"":rs.getString("PAYMENT_TERMS").trim();
	  //String date_sent = rs.getString("DATE_SENT")==null?"":rs.getString("DATE_SENT").trim();
	  String date_accepted = rs.getString("DATE_ACCEPTED")==null?"":rs.getString("DATE_ACCEPTED").trim();
	  //String terms_and_conditions = rs.getString("TERMS_AND_CONDITIONS")==null?"":rs.getString("TERMS_AND_CONDITIONS").trim();
	  String bo_start_date = rs.getString("BO_START_DATE")==null?"":rs.getString("BO_START_DATE").trim();
	  String bo_end_date = rs.getString("BO_END_DATE")==null?"":rs.getString("BO_END_DATE").trim();
	  String customerpo = rs.getString("CUSTOMER_PO")==null?"":rs.getString("CUSTOMER_PO").trim();
	  String everconfirmed = rs.getString("EVER_CONFIRMED")==null?"":rs.getString("EVER_CONFIRMED").trim();
	  String totalphone = rs.getString("SUPPLIER_CONTACT_PHONE")==null?"":rs.getString("SUPPLIER_CONTACT_PHONE").trim();
	  String totalfax = rs.getString("SUPPLIER_CONTACT_FAX")==null?"":rs.getString("SUPPLIER_CONTACT_FAX").trim();
		String critical = rs.getString("CRITICAL")==null?"":rs.getString("CRITICAL").trim();
		String supprating = rs.getString("QUALIFICATION_LEVEL")==null?"":rs.getString("QUALIFICATION_LEVEL").trim();
		invengrp = rs.getString("INVENTORY_GROUP")==null?"":rs.getString("INVENTORY_GROUP").trim();
		String attachedpr = rs.getString("ATTACHED_PR")==null?"":rs.getString("ATTACHED_PR").trim();
	  String consignedpo = rs.getString("CONSIGNED_PO")==null?"":rs.getString("CONSIGNED_PO").trim();
	  String vouchered = rs.getString("VOUCHERED")==null?"":rs.getString("VOUCHERED").trim();
	  String haascarrier=rs.getString( "HAAS_CARRIER" ) == null ? "" : rs.getString( "HAAS_CARRIER" ).trim();
	  String receivedstatus = rs.getString("RECEIVED_STATUS")==null?"":rs.getString("RECEIVED_STATUS").trim();
	  String suppliercontactemail = rs.getString("EMAIL")==null?"":rs.getString("EMAIL").trim();
	  String supplieremail = rs.getString("SUPPLIER_EMAIL")==null?"":rs.getString("SUPPLIER_EMAIL").trim();
      String changeSupplier = rs.getString("CHANGE_SUPPLIER")==null?"":rs.getString("CHANGE_SUPPLIER").trim();
      String nonintegerReceiving = rs.getString("NONINTEGER_RECEIVING")==null?"":rs.getString("NONINTEGER_RECEIVING").trim();
      String supplierDefaultPaymentTerms = rs.getString("SUPPLIER_DEFAULT_PAYMENT_TERMS")==null?"":rs.getString("SUPPLIER_DEFAULT_PAYMENT_TERMS").trim();
      //String operatingEntityName = rs.getString("OPERATING_ENTITY_NAME")==null?"":rs.getString("OPERATING_ENTITY_NAME").trim();
      opsEntityId = rs.getString("OPS_ENTITY_ID")==null?"":rs.getString("OPS_ENTITY_ID").trim();
      String interCompanyMr = rs.getString("INTERCOMPANY_MR")==null?"":rs.getString("INTERCOMPANY_MR").trim();
      requiresFinancialApproval = rs.getString("REQUIRES_FINANCIAL_APPROVAL")==null?"":rs.getString("REQUIRES_FINANCIAL_APPROVAL").trim();
      

      if (supplierDefaultPaymentTerms.length() == 0 && supplier.length() >0){
        poout.println("messagerow = opener.document.getElementById(\"messagerow\");\n");
        poout.println("messagerow.innerHTML = messagerow.innerHTML +\"<FONT SIZE=\\\"3\\\" FACE=\\\"Arial\\\" COLOR=\\\"#fc0303\\\"><B>This supplier is not Active</B></FONT>\";\n");
      }

  	  poout.println( "supplierDefaultPaymentTerms = opener.document.getElementById(\"supplierDefaultPaymentTerms\");\n" );
      poout.println( "supplierDefaultPaymentTerms.value = \"" + supplierDefaultPaymentTerms + "\";\n" );

   	  poout.println( "opsEntityId = opener.document.getElementById(\"opsEntityId\");\n" );
      poout.println( "opsEntityId.value = \"" + opsEntityId + "\";\n" );

      //if (payment_term.length() == 0 ){payment_term = "Net 45";}
	  if (freight_on_board.length() == 0 ){freight_on_board = "None";}
	  if (branch_plant1.length() == 0 ){branch_plant1 = "None";}

      poout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
      poout.println( "selectedRow.className = \"HEADER\";\n" );
      poout.println( "selectedRow.value = \"" + radian_po + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"validpo\");\n" );
      poout.println( "selectedRow.value = \"Yes\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"bpo\");\n" );
      poout.println( "selectedRow.value = \"" + bo + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"suppRating\");\n" );
      poout.println( "selectedRow.value = \"" + supprating + "\";\n" );

  	  poout.println( "nonintegerReceiving = opener.document.getElementById(\"nonintegerReceiving\");\n" );
      poout.println( "nonintegerReceiving.value = \"" + nonintegerReceiving + "\";\n" );

	  //TO DO if bpo is > 0  then disable some controls on the page
	  if ( (bo.length() > 0) || ("N".equalsIgnoreCase(changeSupplier)) || interCompanyMr.length() >0)
	  {
	    poout.println("selectedRow = opener.document.getElementById(\"searchsupplierlike\");\n");
	    poout.println("selectedRow.disabled=true;\n");
	    poout.println("selectedRow.style.display=\"none\";\n");

	    poout.println("selectedRow = opener.document.getElementById(\"supplierid\");\n");
	    poout.println("selectedRow.className = \"INVISIBLEHEADWHITE\";\n");
	    poout.println("selectedRow.readOnly = true;\n");

	    poout.println("selectedRow = opener.document.getElementById(\"validbpo\");\n");
        poout.println("selectedRow.value = \"Yes\";\n");
	  }
	  else
	  {
	  poout.println("supplierid = opener.document.getElementById(\"supplierid\");\n");
	  poout.println("supplierid.className = \"HEADER\";\n");
	  poout.println("supplierid.readOnly = false;\n");
	  }

        poout.println( "selectedRow = opener.document.getElementById(\"searchbpolike\");\n" );
        poout.println( "selectedRow.disabled=true;\n" );
        poout.println( "selectedRow.style.display=\"none\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"Newbpo\");\n" );
        poout.println( "selectedRow.disabled=true;\n" );
        poout.println( "selectedRow.style.display=\"none\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"bpo\");\n" );
        poout.println( "selectedRow.className = \"INVISIBLEHEADWHITE\";\n" );
        poout.println( "selectedRow.readOnly = true;\n" );

        if ( !"N".equalsIgnoreCase( critical ) &&  critical.length() > 0)
        {
          poout.println( "criticalpo = opener.document.getElementById(\"criticalpo\");\n" );
          poout.println( "criticalpo.value=\""+critical+"\";\n" );
        }
		else
		{
		  poout.println( "criticalpo = opener.document.getElementById(\"criticalpo\");\n" );
          poout.println( "criticalpo.value=\"N\";\n" );
		}

		if ( "Y".equalsIgnoreCase( consignedpo ) )
		{
		  poout.println( "consignedpo = opener.document.getElementById(\"consignedpo\");\n" );
		  poout.println( "consignedpo.checked=true;\n" );
		}

        poout.println( "selectedRow = opener.document.getElementById(\"HubName\");\n" );
        poout.println( "selectedRow.value = \"" + branch_plant1 + "\";\n" );

		String fobvalue = vvFob.get(freight_on_board)==null?"":vvFob.get(freight_on_board).toString();
		if (fobvalue.length() > 0)
		{
		  poout.println( "selectedRow = opener.document.getElementById(\"fob\");\n" );
		  poout.println( "selectedRow.value = \"" + freight_on_board + "\";\n" );
		}
		else
		{
		  poout.println( "opener.addOptionItem(opener.document.getElementById(\"fob\"),'" + freight_on_board + "','" + freight_on_board + "');\n" );
		}

        poout.println( "selectedRow = opener.document.getElementById(\"paymentterms\");\n" );
        poout.println( "selectedRow.value = \"" + payment_term + "\";\n" );
		if ( "Y".equalsIgnoreCase( vouchered ) )
		{
		  poout.println( "selectedRow.disabled = true;\n" );
		  poout.println( "currencyid = opener.document.getElementById(\"currency\");\n" );
		  poout.println( "currencyid.disabled = true;\n" );
		}

    if ("HAASTCMUSA".equalsIgnoreCase(opsEntityId) && payment_term.equalsIgnoreCase("Credit Card"))
    {
        poout.println( "allowPOCreditConfirmaiton = opener.document.getElementById(\"allowPOCreditConfirmaiton\");\n" );        
        poout.println( "if (allowPOCreditConfirmaiton.value == \"false\")   \n" );
        poout.println( "{\n" );
        poout.println( "try {\n" );
        poout.println( "  var confirmButton = opener.document.getElementById(\"confirm\");\n" );
        poout.println( "  confirmButton.style.display = \"none\";  \n" );
        poout.println( " }\n" );
        poout.println( "catch (ex)\n{}\n" );
        poout.println( "} \n" );
    }

        poout.println( "selectedRow = opener.document.getElementById(\"buyer\");\n" );
        poout.println( "selectedRow.value = \"" + buyername + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"buyerid\");\n" );
        poout.println( "selectedRow.value = \"" + buyer + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"customerpo\");\n" );
        poout.println( "selectedRow.value = htmlDencode(\"" + HelpObjs.addescapesForJavascript(customerpo) + "\");\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"ordertaker\");\n" );
        poout.println( "selectedRow.className = \"HEADER\";\n" );
        poout.println( "selectedRow.value = \"" + supplier_contact_name + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"ordertakerID\");\n" );
        poout.println( "selectedRow.value = \"" + supplier_contact_id + "\";\n" );

        if ( supplier_contact_id.trim().length() > 0 )
        {
          poout.println( "selectedRow = opener.document.getElementById(\"validordertaker\");\n" );
          poout.println( "selectedRow.value = \"Yes\";\n" );
        }
        else
        {
          poout.println( "selectedRow = opener.document.getElementById(\"validordertaker\");\n" );
          poout.println( "selectedRow.value = \"No\";\n" );

          poout.println( "ordertaker = opener.document.getElementById(\"ordertaker\");\n" );
          poout.println( "ordertaker.className = \"INVALIDTEXT\";\n" );
        }

        poout.println( "contactphoneno = opener.document.getElementById(\"contactphoneno\");\n" );
        poout.println( "contactphoneno.value = \"" + totalphone + "\";\n" );

        poout.println( "contactfaxno11 = opener.document.getElementById(\"contactfaxno\");\n" );
        poout.println( "contactfaxno11.value = \"" + totalfax + "\";\n" );

		poout.println( "supplieremail = opener.document.getElementById(\"supplieremail\");\n" );
        poout.println( "supplieremail.innerHTML = \"" + supplieremail + "\";\n" );

		poout.println( "suppliercontactemail = opener.document.getElementById(\"suppliercontactemail\");\n" );
        poout.println( "suppliercontactemail.innerHTML = \"" + suppliercontactemail + "\";\n" );

        /*poout.println( "selectedRow = opener.document.getElementById(\"faxdate\");\n" );
        poout.println( "selectedRow.value = \"" + date_sent + "\";\n" );*/

        poout.println( "selectedRow = opener.document.getElementById(\"accepteddate\");\n" );
        poout.println( "selectedRow.value = \"" + date_accepted + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"bostartdate\");\n" );
        poout.println( "selectedRow.value = \"" + bo_start_date + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"boenddate\");\n" );
        poout.println( "selectedRow.value = \"" + bo_end_date + "\";\n" );

        poout.println( "invengrp = opener.document.getElementById(\"invengrp\");\n" );
        poout.println( "invengrp.value = \"" + invengrp + "\";\n" );

        poout.println( "attachedpr = opener.document.getElementById(\"attachedpr\");\n" );
        poout.println( "attachedpr.value = \"" + attachedpr + "\";\n" );

        String suppliername = HelpObjs.addescapesForJavascript(rs.getString("SUPPLIER_NAME")==null?"":rs.getString("SUPPLIER_NAME").trim());
	  String suppliercountryabbrev = rs.getString("SUPPLIER_COUNTRY_ABBREV")==null?"":rs.getString("SUPPLIER_COUNTRY_ABBREV").trim();
	  String supplierstateabbrev = rs.getString("SUPPLIER_STATE_ABBREV")==null?"":rs.getString("SUPPLIER_STATE_ABBREV").trim();
	  String supplieraddressline1 = rs.getString("SUPPLR_ADDRESS_LINE_1_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_1_DISPLAY").trim();
	  String supplieraddressline2 = rs.getString("SUPPLR_ADDRESS_LINE_2_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_2_DISPLAY").trim();
      String supplieraddressline3 = rs.getString("SUPPLR_ADDRESS_LINE_3_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_3_DISPLAY").trim();
      String supplieraddressline4 = rs.getString("SUPPLR_ADDRESS_LINE_4_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_4_DISPLAY").trim();
      String supplieraddressline5 = rs.getString("SUPPLR_ADDRESS_LINE_5_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_5_DISPLAY").trim();
      //String supplieraddressline3 = rs.getString("SUPPLIER_ADDRESS_LINE_3")==null?"":rs.getString("SUPPLIER_ADDRESS_LINE_3").trim();
	  String suppliercity = rs.getString("SUPPLIER_CITY")==null?"":rs.getString("SUPPLIER_CITY").trim();
	  String supplierzip = rs.getString("SUPPLIER_ZIP")==null?"":rs.getString("SUPPLIER_ZIP").trim();
	  String supplierphone = rs.getString("SUPPLIER_PHONE")==null?"":rs.getString("SUPPLIER_PHONE").trim();

	  poout.println("mfgphoneno = opener.document.getElementById(\"mfgphoneno\");\n");
	  poout.println("mfgphoneno.value = \""+supplierphone+"\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"supplierid\");\n");
      poout.println("selectedRow.value = \""+supplier+"\";\n");
      
      poout.println("supplierCountryAbbrev = opener.document.getElementById(\"supplierCountryAbbrev\");\n");
	  poout.println("supplierCountryAbbrev.value = \""+suppliercountryabbrev+"\";\n");

      if ( supplier.trim().length() > 0 )
      {
        poout.println( "selectedRow = opener.document.getElementById(\"validsupplier\");\n" );
        poout.println( "selectedRow.value = \"Yes\";\n" );
      }
      else
      {
        poout.println( "selectedRow = opener.document.getElementById(\"validsupplier\");\n" );
        poout.println( "selectedRow.value = \"No\";\n" );

        poout.println( "supplierid = opener.document.getElementById(\"supplierid\");\n" );
        poout.println( "supplierid.className = \"INVALIDTEXT\";\n" );
      }

      poout.println( "selectedRow = opener.document.getElementById(\"supplierline1\");\n" );
      poout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(suppliername) + "</FONT>\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"supplierline2\");\n" );
      poout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(supplieraddressline1) + "</FONT>\";\n" );

      poout.println("selectedRow = opener.document.getElementById(\"supplierline3\");\n");
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(supplieraddressline2)+"</FONT>\";\n");

      poout.println("selectedRow = opener.document.getElementById(\"supplierline4\");\n");
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(supplieraddressline3)+"</FONT>\";\n");

      /*if (supplieraddressline3.length() >0)
	  {
	    poout.println("selectedRow = opener.document.getElementById(\"supplierline3\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(supplieraddressline3)+"</FONT>\";\n");

	    poout.println("selectedRow = opener.document.getElementById(\"supplierline4\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+suppliercity+", "+supplierstateabbrev+" "+supplierzip+" "+suppliercountryabbrev+"</FONT>\";\n");
	  }
	  else
	  {
	    poout.println("selectedRow = opener.document.getElementById(\"supplierline3\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+suppliercity+", "+supplierstateabbrev+" "+supplierzip+" "+suppliercountryabbrev+"</FONT>\";\n");

	    poout.println("selectedRow = opener.document.getElementById(\"supplierline4\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n");
	  }*/

	  String shiptocountryabbrev = rs.getString("SHIPTO_COUNTRY_ABBREV")==null?"":rs.getString("SHIPTO_COUNTRY_ABBREV").trim();
	  String shiptostateabbrev = rs.getString("SHIPTO_STATE_ABBREV")==null?"":rs.getString("SHIPTO_STATE_ABBREV").trim();
	  String shiptoaddressline1 = rs.getString("SHIP_TO_ADDRESS_LINE_1_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_1_DISPLAY").trim();
	  String shiptoaddressline2 = rs.getString("SHIP_TO_ADDRESS_LINE_2_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_2_DISPLAY").trim();
	  String shiptoaddressline3 = rs.getString("SHIP_TO_ADDRESS_LINE_3_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_3_DISPLAY").trim();
      String shiptoaddressline4 = rs.getString("SHIP_TO_ADDRESS_LINE_4_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_4_DISPLAY").trim();
      String shiptoaddressline5 = rs.getString("SHIP_TO_ADDRESS_LINE_5_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_5_DISPLAY").trim();
      String shiptocity = rs.getString("SHIPTO_CITY")==null?"":rs.getString("SHIPTO_CITY").trim();
	  String shiptozip = rs.getString("SHIPTO_ZIP")==null?"":rs.getString("SHIPTO_ZIP").trim();
	  String shiptolocationdesc = rs.getString("SHIPTO_LOCATION_DESC")==null?"":rs.getString("SHIPTO_LOCATION_DESC").trim();
		String shipToFacilityId = rs.getString("SHIP_TO_FACILITY_ID")==null?"":rs.getString("SHIP_TO_FACILITY_ID").trim();

	  poout.println("selectedRow = opener.document.getElementById(\"shiptoid\");\n");
	  String shipto1 = HelpObjs.findReplace(ship_to_location_id,"#","%23");
      shipto1 = HelpObjs.findReplace(ship_to_location_id,"\\","%2F");

      poout.println("selectedRow.value = \""+shipto1+"\";\n");

			poout.println("selectedRow = opener.document.getElementById(\"shiptocompanyid\");\n");
			poout.println("selectedRow.value = \""+ship_to_company_id+"\";\n");

			poout.println("shipToFacilityId = opener.document.getElementById(\"shipToFacilityId\");\n");
      poout.println("shipToFacilityId.value = \""+shipToFacilityId+"\";\n");

	  if (ship_to_location_id.trim().length() > 0)
	  {
	    poout.println("selectedRow = opener.document.getElementById(\"validshipto\");\n");
	    poout.println("selectedRow.value = \"Yes\";\n");

	    poout.println("shiptoid = opener.document.getElementById(\"shiptoid\");\n");
	    poout.println("shiptoid.className = \"HEADER\";\n");
	  }
	  else
	  {
	    poout.println("selectedRow = opener.document.getElementById(\"validshipto\");\n");
	    poout.println("selectedRow.value = \"No\";\n");

	    poout.println("shiptoid = opener.document.getElementById(\"shiptoid\");\n");
	    poout.println("shiptoid.className = \"INVALIDTEXT\";\n");
	  }


	  poout.println("selectedRow = opener.document.getElementById(\"shiptoline1\");\n");
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptoaddressline1)+"</FONT>\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"shiptoline2\");\n");
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptoaddressline2)+"</FONT>\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"shiptoline3\");\n");
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptoaddressline3)+"</FONT>\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"shiptoline4\");\n");
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptoaddressline4)+"</FONT>\";\n");

	  /*if (shiptoaddressline3.length() >0)
	  {
		poout.println("selectedRow = opener.document.getElementById(\"shiptoline4\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptoaddressline3)+"</FONT>\";\n");

	    poout.println("selectedRow = opener.document.getElementById(\"shiptoline5\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+shiptocity+", "+shiptostateabbrev+" "+shiptozip+" "+shiptocountryabbrev+"</FONT>\";\n");
	  }
	  else
	  {
	    poout.println("selectedRow = opener.document.getElementById(\"shiptoline4\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+shiptocity+", "+shiptostateabbrev+" "+shiptozip+" "+shiptocountryabbrev+"</FONT>\";\n");

	    poout.println("selectedRow = opener.document.getElementById(\"shiptoline5\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n");
	  }*/

	  carrier = HelpObjs.addescapesForJavascript(rs.getString("CARRIER")==null?"":rs.getString("CARRIER").trim());
	  String carrieraccount = rs.getString("CARRIER_ACCOUNT")==null?"":rs.getString("CARRIER_ACCOUNT").trim();
	  String carriercompanyid = rs.getString("CARRIER_COMPANY_ID")==null?"":rs.getString("CARRIER_COMPANY_ID").trim();
	  String carriername =HelpObjs.addescapesForJavascript(rs.getString("CARRIER_NAME")==null?"":rs.getString("CARRIER_NAME").trim());
	  String carrierhub = rs.getString("CARRIER_HUB")==null?"":rs.getString("CARRIER_HUB").trim();

	  poout.println("selectedRow = opener.document.getElementById(\"carrierID\");\n");
	  poout.println("selectedRow.className = \"HEADER\";\n");
      poout.println("selectedRow.value = \""+carrier+"\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"carrieraccount\");\n");
      poout.println("selectedRow.value = \""+carrieraccount+"\";\n");

	  if (carrier.trim().length() > 0)
	  {
	    poout.println("selectedRow = opener.document.getElementById(\"validcarrier\");\n");
	    poout.println("selectedRow.value = \"Yes\";\n");
	  }
	  else
	  {
	    poout.println("selectedRow = opener.document.getElementById(\"validcarrier\");\n");
	    poout.println("selectedRow.value = \"No\";\n");

	    poout.println("carrierID = opener.document.getElementById(\"carrierID\");\n");
	    poout.println("carrierID.className = \"INVALIDTEXT\";\n");
	  }

	  poout.println("selectedRow = opener.document.getElementById(\"carrierline1\");\n");
    if (carriercompanyid.equalsIgnoreCase("Radian"))
    {
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+carrierhub+"/Haas TCM</FONT>\";\n");
    }
    else
    {
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+carrierhub+"/"+carriercompanyid+"</FONT>\";\n");
    }
    poout.println("selectedRow = opener.document.getElementById(\"carrierline2\");\n");
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+carriername+"/"+carrieraccount+"</FONT>\";\n");

	  if ( "Y".equalsIgnoreCase( vouchered ) )
	  {
		poout.println( "fobd = opener.document.getElementById(\"fob\");\n" );
		poout.println( "fobd.disabled = true;\n" );

		poout.println( "searchshiptolike = opener.document.getElementById(\"searchshiptolike\");\n" );
		poout.println( "searchshiptolike.disabled=true;\n" );
		poout.println( "searchshiptolike.style.display=\"none\";\n" );

		poout.println( "shiptoidd = opener.document.getElementById(\"shiptoid\");\n" );
		poout.println( "shiptoidd.className = \"INVISIBLEHEADWHITE\";\n" );
		poout.println( "shiptoidd.readOnly = true;\n" );

		//if ( "Y".equalsIgnoreCase( haascarrier ) )
		{
		  poout.println( "searchcarrierlike = opener.document.getElementById(\"searchcarrierlike\");\n" );
		  poout.println( "searchcarrierlike.disabled=true;\n" );
		  poout.println( "searchcarrierlike.style.display=\"none\";\n" );

		  poout.println( "carrierIDd = opener.document.getElementById(\"carrierID\");\n" );
		  poout.println( "carrierIDd.className = \"INVISIBLEHEADWHITE\";\n" );
		  poout.println( "carrierIDd.readOnly = true;\n" );
		}

		poout.println( "customerpos = opener.document.getElementById(\"customerpo\");\n" );
		poout.println( "customerpos.className = \"INVISIBLEHEADWHITE\";\n" );
		poout.println( "customerpos.readOnly = true;\n" );
	  }
	  if ( "Y".equalsIgnoreCase( receivedstatus ) )
	  {
		poout.println( "consignedpo = opener.document.getElementById(\"consignedpo\");\n" );
		poout.println( "consignedpo.className = \"INVISIBLEHEADWHITE\";\n" );
		poout.println( "consignedpo.disabled = true;\n" );
	  }
    }
  }
  catch ( Exception e )
  {
    //e.printStackTrace();
  }
  finally
  {
    if (dbrs != null)  { dbrs.close(); }
  }

  poout.println("   qtyrechead = opener.document.getElementById(\"qtyrechead\");\n");
  poout.println("   qtyrechead.innerHTML = \"Qty Rec\";\n");

  buildPolineDetails( radianPO,ship_to_location_id,ship_to_company_id,invengrp,opsEntityId,branch_plant1,poout);
  poout.println( "opener.makeCursorNormal();\n" );
  poout.println( "window.close();\n" );
  poout.println( " }\n" );
  poout.println( "// -->\n</SCRIPT>\n" );
  //poout.println( Msgtemp );
  poout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
  poout.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
  poout.println( "<CENTER><BR><BR>\n" );
  poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
  poout.println( "</FORM></BODY></HTML>\n" );
  //poout.println( Msgbody );

  return;
}

public void buildsendSupplierbpo( String radianPO,PrintWriter poout )
{
  //StringBuffer Msgn=new StringBuffer();
  //StringBuffer Msgbody=new StringBuffer();
  DBResultSet dbrs = null;
  ResultSet rs = null;
  nematid_Servlet=bundle.getString( "PURCHASE_ORDER_PO" );

  poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Lookup","",false ) );
  poout.println("<script type=\"text/javascript\" src=\"/js/common/commonutil.js\"></script>");
  printJavaScripts(poout);
  poout.println( "// -->\n </SCRIPT>\n" );

  //StringBuffer Msgtemp=new StringBuffer();

  //Build the Java Script Here and Finish the thing
  poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
  poout.println( "<!--\n" );
  poout.println( "function sendSupplierData()\n" );
  poout.println( "{\n" );
  poout.println( "opener.removeAllLines(); \n" );

  String supplier="";
  String ship_to_company_id="";
  String ship_to_location_id="";
  String invengrp ="";
  String opsEntityId ="";
  try
  {
    String poquery="Select SHIP_TO_ADDRESS_LINE_1_DISPLAY, SHIP_TO_ADDRESS_LINE_2_DISPLAY, SHIP_TO_ADDRESS_LINE_3_DISPLAY, SHIP_TO_ADDRESS_LINE_4_DISPLAY, SHIP_TO_ADDRESS_LINE_5_DISPLAY, SUPPLR_ADDRESS_LINE_1_DISPLAY, SUPPLR_ADDRESS_LINE_2_DISPLAY, SUPPLR_ADDRESS_LINE_3_DISPLAY, SUPPLR_ADDRESS_LINE_4_DISPLAY, SUPPLR_ADDRESS_LINE_5_DISPLAY,OPS_ENTITY_ID,SHIP_TO_FACILITY_ID,EMAIL,SUPPLIER_EMAIL,CARRIER_COMPANY_ID,CARRIER_ACCOUNT,CARRIER_NAME,CARRIER_HUB,CARRIER,CARRIER_ACCOUNT,CRITICAL,BRANCH_PLANT,CUSTOMER_PO,CONSIGNED_PO,INVENTORY_GROUP,BPO,SUPPLIER,EVER_CONFIRMED,SUPPLIER_CONTACT_NAME,SUPPLIER_CONTACT_ID,BUYER,BUYER_NAME,PAYMENT_TERMS,FREIGHT_ON_BOARD, ";
    poquery+="FREIGHT_ON_BOARD,SUPPLIER_COUNTRY_ABBREV,SUPPLIER_STATE_ABBREV,SUPPLIER_ADDRESS_LINE_1,SUPPLIER_ADDRESS_LINE_2,SUPPLIER_ADDRESS_LINE_3, ";
    poquery+="SUPPLIER_PHONE,SUPPLIER_CONTACT_PHONE,SUPPLIER_CONTACT_FAX,SUPPLIER_CITY,SUPPLIER_ZIP,SUPPLIER_LOCATION_DESC,SUPPLIER_NAME, ";
    poquery+="DEFAULT_SHIP_TO_COMPANY_ID,DEFAULT_SHIP_TO_LOCATION_ID,SHIPTO_COUNTRY_ABBREV,SHIPTO_STATE_ABBREV,SHIPTO_ADDRESS_LINE_1,SHIPTO_ADDRESS_LINE_2,SHIPTO_ADDRESS_LINE_3,SHIPTO_CITY,SHIPTO_ZIP,SHIPTO_LOCATION_DESC, ";
    poquery+="to_char(START_DATE,'mm/dd/yyyy') START_DATE,to_char(END_DATE,'mm/dd/yyyy') END_DATE,QUALIFICATION_LEVEL ";
    poquery+="from bpo_header_view where bpo = " + radianPO + " ";

    dbrs=db.doQuery( poquery );
    rs=dbrs.getResultSet();

    while ( rs.next() )
    {
      String bpo=rs.getString( "BPO" ) == null ? "" : rs.getString( "BPO" ).trim();
      supplier=HelpObjs.addescapesForJavascript( rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ).trim() );
      String supplier_contact_id=rs.getString( "SUPPLIER_CONTACT_ID" ) == null ? "" : rs.getString( "SUPPLIER_CONTACT_ID" ).trim();
      String buyer=rs.getString( "BUYER" ) == null ? "" : rs.getString( "BUYER" ).trim();
      String buyername=rs.getString( "BUYER_NAME" ) == null ? "" : rs.getString( "BUYER_NAME" ).trim();
      String start_date=rs.getString( "START_DATE" ) == null ? "" : rs.getString( "START_DATE" ).trim();
      String end_date=rs.getString( "END_DATE" ) == null ? "" : rs.getString( "END_DATE" ).trim();
      String payment_tern=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).trim();
      String freight_on_board=rs.getString( "FREIGHT_ON_BOARD" ) == null ? "" : rs.getString( "FREIGHT_ON_BOARD" ).trim();
      String supplier_contact_name=rs.getString( "SUPPLIER_CONTACT_NAME" ) == null ? "" : rs.getString( "SUPPLIER_CONTACT_NAME" ).trim();
      String everconfirmed=rs.getString( "EVER_CONFIRMED" ) == null ? "" : rs.getString( "EVER_CONFIRMED" ).trim();
      String supprating=rs.getString( "QUALIFICATION_LEVEL" ) == null ? "" : rs.getString( "QUALIFICATION_LEVEL" ).trim();
      String totalphone=rs.getString( "SUPPLIER_CONTACT_PHONE" ) == null ? "" : rs.getString( "SUPPLIER_CONTACT_PHONE" ).trim();
      String totalfax=rs.getString( "SUPPLIER_CONTACT_FAX" ) == null ? "" : rs.getString( "SUPPLIER_CONTACT_FAX" ).trim();
      ship_to_company_id=rs.getString( "DEFAULT_SHIP_TO_COMPANY_ID" ) == null ? "" : rs.getString( "DEFAULT_SHIP_TO_COMPANY_ID" ).trim();
      ship_to_location_id=rs.getString( "DEFAULT_SHIP_TO_LOCATION_ID" ) == null ? "" : rs.getString( "DEFAULT_SHIP_TO_LOCATION_ID" ).trim();
	  invengrp = rs.getString("INVENTORY_GROUP")==null?"":rs.getString("INVENTORY_GROUP").trim();
	  String consignedpo = rs.getString("CONSIGNED_PO")==null?"":rs.getString("CONSIGNED_PO").trim();
	  String customerpo = rs.getString("CUSTOMER_PO")==null?"":rs.getString("CUSTOMER_PO").trim();
	  String branch_plant1 = rs.getString("BRANCH_PLANT")==null?"":rs.getString("BRANCH_PLANT").trim();
	  String critical = rs.getString("CRITICAL")==null?"":rs.getString("CRITICAL").trim();
	  String suppliercontactemail = rs.getString("EMAIL")==null?"":rs.getString("EMAIL").trim();
	  String supplieremail = rs.getString("SUPPLIER_EMAIL")==null?"":rs.getString("SUPPLIER_EMAIL").trim();
      opsEntityId = rs.getString("OPS_ENTITY_ID")==null?"":rs.getString("OPS_ENTITY_ID").trim();
      if ( freight_on_board.length() == 0 )
      {
        freight_on_board="None";
      }

      poout.println( "selectedRow = opener.document.getElementById(\"bpo\");\n" );
      poout.println( "selectedRow.className = \"HEADER\";\n" );
      poout.println( "selectedRow.value = \"" + bpo + "\";\n" );
      poout.println( "selectedRow = opener.document.getElementById(\"validbpo\");\n" );
      poout.println( "selectedRow.value = \"Yes\";\n" );
      poout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
      poout.println( "selectedRow.value = \"\";\n" );
      poout.println( "selectedRow = opener.document.getElementById(\"validpo\");\n" );
      poout.println( "selectedRow.value = \"Yes\";\n" );
      poout.println( "selectedRow = opener.document.getElementById(\"suppRating\");\n" );
      poout.println( "selectedRow.value = \"" + supprating + "\";\n" );

      if ( "Y".equalsIgnoreCase( everconfirmed ) )
      {
        poout.println( "searchsupplierlike = opener.document.getElementById(\"searchsupplierlike\");\n" );
        poout.println( "searchsupplierlike.disabled=true;\n" );
        poout.println( "searchsupplierlike.style.display=\"none\";\n" );
        poout.println( "supplierid = opener.document.getElementById(\"supplierid\");\n" );
        poout.println( "supplierid.className = \"INVISIBLEHEADWHITE\";\n" );
        poout.println( "supplierid.readOnly = true;\n" );
      }
      else
      {
        poout.println( "supplierid = opener.document.getElementById(\"supplierid\");\n" );
        poout.println( "supplierid.className = \"HEADER\";\n" );
        poout.println( "supplierid.readOnly = false;\n" );
      }

      poout.println( "shiptoid = opener.document.getElementById(\"shiptoid\");\n" );
      poout.println( "shiptoid.className = \"HEADER\";\n" );
      poout.println( "shiptoid.readOnly = false;\n" );
      poout.println( "selectedRow = opener.document.getElementById(\"searchpolike\");\n" );
      poout.println( "selectedRow.disabled=true;\n" );
      poout.println( "selectedRow.style.display=\"none\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"po\");\n" );
      poout.println( "selectedRow.className = \"INVISIBLEHEADWHITE\";\n" );
      poout.println( "selectedRow.readOnly = true;\n" );

	  String fobvalue = vvFob.get(freight_on_board)==null?"":vvFob.get(freight_on_board).toString();
	  if (fobvalue.length() > 0)
	  {
		poout.println( "selectedRow = opener.document.getElementById(\"fob\");\n" );
		poout.println( "selectedRow.value = \"" + freight_on_board + "\";\n" );
	  }
	  else
	  {
		poout.println( "opener.addOptionItem(opener.document.getElementById(\"fob\"),'" + freight_on_board + "','" + freight_on_board + "');\n" );
	  }

      poout.println( "selectedRow = opener.document.getElementById(\"paymentterms\");\n" );
      poout.println( "selectedRow.value = \"" + payment_tern + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"buyer\");\n" );
      poout.println( "selectedRow.value = \"" + buyername + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"buyerid\");\n" );
      poout.println( "selectedRow.value = \"" + buyer + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"ordertaker\");\n" );
      poout.println( "selectedRow.className = \"HEADER\";\n" );
      poout.println( "selectedRow.value = \"" + supplier_contact_name + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"ordertakerID\");\n" );
      poout.println( "selectedRow.value = \"" + supplier_contact_id + "\";\n" );

      if ( supplier_contact_id.trim().length() > 0 )
      {
        poout.println( "selectedRow = opener.document.getElementById(\"validordertaker\");\n" );
        poout.println( "selectedRow.value = \"Yes\";\n" );
      }
      else
      {
        poout.println( "selectedRow = opener.document.getElementById(\"validordertaker\");\n" );
        poout.println( "selectedRow.value = \"No\";\n" );

        poout.println( "ordertaker = opener.document.getElementById(\"ordertaker\");\n" );
        poout.println( "ordertaker.className = \"INVALIDTEXT\";\n" );
      }

      poout.println( "contactphoneno = opener.document.getElementById(\"contactphoneno\");\n" );
      poout.println( "contactphoneno.value = \"" + totalphone + "\";\n" );

      poout.println( "contactfaxno11 = opener.document.getElementById(\"contactfaxno\");\n" );
      poout.println( "contactfaxno11.value = \"" + totalfax + "\";\n" );

	  poout.println( "supplieremail = opener.document.getElementById(\"supplieremail\");\n" );
	  poout.println( "supplieremail.innerHTML = \"" + supplieremail + "\";\n" );

	  poout.println( "suppliercontactemail = opener.document.getElementById(\"suppliercontactemail\");\n" );
	  poout.println( "suppliercontactemail.innerHTML = \"" + suppliercontactemail + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"faxdate\");\n" );
      poout.println( "selectedRow.value = \"\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"accepteddate\");\n" );
      poout.println( "selectedRow.value = \"\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"bostartdate\");\n" );
      poout.println( "selectedRow.value = \"" + start_date + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"boenddate\");\n" );
      poout.println( "selectedRow.value = \"" + end_date + "\";\n" );

	  poout.println( "invengrp = opener.document.getElementById(\"invengrp\");\n" );
	  poout.println( "invengrp.value = \"" + invengrp + "\";\n" );

	  if ( "Y".equalsIgnoreCase( consignedpo ) )
	  {
		poout.println( "consignedpo = opener.document.getElementById(\"consignedpo\");\n" );
		poout.println( "consignedpo.checked=true;\n" );
	  }

	  poout.println( "selectedRow = opener.document.getElementById(\"customerpo\");\n" );
      poout.println( "selectedRow.value = htmlDencode(\"" + HelpObjs.addescapesForJavascript(customerpo) + "\");\n" );

	  poout.println( "selectedRow = opener.document.getElementById(\"HubName\");\n" );
	  poout.println( "selectedRow.value = \"" + branch_plant1 + "\";\n" );

	  if ( !"N".equalsIgnoreCase( critical ) && critical.length() > 0 )
	  {
		poout.println( "criticalpo = opener.document.getElementById(\"criticalpo\");\n" );
		poout.println( "criticalpo.value=\"" + critical + "\";\n" );
	  }
	  else
	  {
		poout.println( "criticalpo = opener.document.getElementById(\"criticalpo\");\n" );
		poout.println( "criticalpo.value=\"N\";\n" );
	  }

      if ( "Y".equalsIgnoreCase( everconfirmed ) )
      {
        poout.println( "selectedRow = opener.document.getElementById(\"bostartdate\");\n" );
        poout.println( "selectedRow.className = \"INVISIBLEHEADWHITE\";\n" );
        poout.println( "selectedRow.readOnly = true;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"boenddate\");\n" );
        poout.println( "selectedRow.className = \"INVISIBLEHEADWHITE\";\n" );
        poout.println( "selectedRow.readOnly = true;\n" );

        poout.println( "boenddatelink = opener.document.getElementById(\"boenddatelink\");\n" );
        poout.println( "boenddatelink.disabled=true;\n" );
        poout.println( "boenddatelink.style.display=\"none\";\n" );

        poout.println( "bostartdatelink = opener.document.getElementById(\"bostartdatelink\");\n" );
        poout.println( "bostartdatelink.disabled=true;\n" );
        poout.println( "bostartdatelink.style.display=\"none\";\n" );

      }
      else
      {
        poout.println( "selectedRow = opener.document.getElementById(\"bostartdate\");\n" );
        poout.println( "selectedRow.className = \"HEADER\";\n" );
        poout.println( "selectedRow.readOnly = false;\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"boenddate\");\n" );
        poout.println( "selectedRow.className = \"HEADER\";\n" );
        poout.println( "selectedRow.readOnly = false;\n" );
      }

	  String carrier = HelpObjs.addescapesForJavascript(rs.getString("CARRIER")==null?"":rs.getString("CARRIER").trim());
	  String carrieraccount = rs.getString("CARRIER_ACCOUNT")==null?"":rs.getString("CARRIER_ACCOUNT").trim();
	  String carriercompanyid = rs.getString("CARRIER_COMPANY_ID")==null?"":rs.getString("CARRIER_COMPANY_ID").trim();
	  String carriername = HelpObjs.addescapesForJavascript(rs.getString("CARRIER_NAME")==null?"":rs.getString("CARRIER_NAME").trim());
	  String carrierhub = rs.getString("CARRIER_HUB")==null?"":rs.getString("CARRIER_HUB").trim();

	  poout.println("selectedRow = opener.document.getElementById(\"carrierID\");\n");
	  poout.println("selectedRow.className = \"HEADER\";\n");
	  poout.println("selectedRow.value = \""+carrier+"\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"carrieraccount\");\n");
	  poout.println("selectedRow.value = \""+carrieraccount+"\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"validcarrier\");\n");
	  poout.println("selectedRow.value = \"Yes\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"carrierline1\");\n");
    if (carriercompanyid.equalsIgnoreCase("Radian"))
    {
     poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+carrierhub+"/Haas TCM</FONT>\";\n");
    }
    else
    {
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+carrierhub+"/"+carriercompanyid+"</FONT>\";\n");
    }
    poout.println("selectedRow = opener.document.getElementById(\"carrierline2\");\n");
	  poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+carriername+"/"+carrieraccount+"</FONT>\";\n");

      String suppliername=HelpObjs.addescapesForJavascript( rs.getString( "SUPPLIER_NAME" ) == null ? "" : rs.getString( "SUPPLIER_NAME" ).trim() );
      String suppliercountryabbrev=rs.getString( "SUPPLIER_COUNTRY_ABBREV" ) == null ? "" : rs.getString( "SUPPLIER_COUNTRY_ABBREV" ).trim();
      String supplierstateabbrev=rs.getString( "SUPPLIER_STATE_ABBREV" ) == null ? "" : rs.getString( "SUPPLIER_STATE_ABBREV" ).trim();
      String supplieraddressline1 = rs.getString("SUPPLR_ADDRESS_LINE_1_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_1_DISPLAY").trim();
	  String supplieraddressline2 = rs.getString("SUPPLR_ADDRESS_LINE_2_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_2_DISPLAY").trim();
      String supplieraddressline3 = rs.getString("SUPPLR_ADDRESS_LINE_3_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_3_DISPLAY").trim();
      String supplieraddressline4 = rs.getString("SUPPLR_ADDRESS_LINE_4_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_4_DISPLAY").trim();
      String supplieraddressline5 = rs.getString("SUPPLR_ADDRESS_LINE_5_DISPLAY")==null?"":rs.getString("SUPPLR_ADDRESS_LINE_5_DISPLAY").trim();
      String suppliercity=rs.getString( "SUPPLIER_CITY" ) == null ? "" : rs.getString( "SUPPLIER_CITY" ).trim();
      String supplierzip=rs.getString( "SUPPLIER_ZIP" ) == null ? "" : rs.getString( "SUPPLIER_ZIP" ).trim();
      String supplierphone=rs.getString( "SUPPLIER_PHONE" ) == null ? "" : rs.getString( "SUPPLIER_PHONE" ).trim();
      //String supplierlocationdesc = rs.getString("SUPPLIER_LOCATION_DESC")==null?"":rs.getString("SUPPLIER_LOCATION_DESC").trim();

      poout.println( "mfgphoneno = opener.document.getElementById(\"mfgphoneno\");\n" );
      poout.println( "mfgphoneno.value = \"" + supplierphone + "\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"supplierid\");\n" );
      poout.println( "selectedRow.value = \"" + supplier + "\";\n" );

      if ( supplier.trim().length() > 0 )
      {
        poout.println( "selectedRow = opener.document.getElementById(\"validsupplier\");\n" );
        poout.println( "selectedRow.value = \"Yes\";\n" );
      }
      else
      {
        poout.println( "selectedRow = opener.document.getElementById(\"validsupplier\");\n" );
        poout.println( "selectedRow.value = \"No\";\n" );

        poout.println( "supplierid = opener.document.getElementById(\"supplierid\");\n" );
        poout.println( "supplierid.className = \"INVALIDTEXT\";\n" );
      }

      poout.println( "selectedRow = opener.document.getElementById(\"supplierline1\");\n" );
      poout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(suppliername) + "</FONT>\";\n" );

      poout.println( "selectedRow = opener.document.getElementById(\"supplierline2\");\n" );
      poout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(supplieraddressline1) + "</FONT>\";\n" );

      poout.println("selectedRow = opener.document.getElementById(\"supplierline3\");\n");
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(supplieraddressline2)+"</FONT>\";\n");

      poout.println("selectedRow = opener.document.getElementById(\"supplierline4\");\n");
      poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(supplieraddressline3)+"</FONT>\";\n");

      /*if ( supplieraddressline1.length() > 0 )
      {
        poout.println( "selectedRow = opener.document.getElementById(\"supplierline3\");\n" );
        poout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + HelpObjs.addescapesForJavascript(supplieraddressline2) + "</FONT>\";\n" );

        poout.println("selectedRow = opener.document.getElementById(\"supplierline4\");\n");
        poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+suppliercity+", "+supplierstateabbrev+" "+supplierzip+" "+suppliercountryabbrev+"</FONT>\";\n");
	  }
	  else
	  {
	    poout.println("selectedRow = opener.document.getElementById(\"supplierline3\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+suppliercity+", "+supplierstateabbrev+" "+supplierzip+" "+suppliercountryabbrev+"</FONT>\";\n");

	    poout.println("selectedRow = opener.document.getElementById(\"supplierline4\");\n");
	    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n");
	  }*/

	String shiptocountryabbrev = rs.getString("SHIPTO_COUNTRY_ABBREV")==null?"":rs.getString("SHIPTO_COUNTRY_ABBREV").trim();
	String shiptostateabbrev = rs.getString("SHIPTO_STATE_ABBREV")==null?"":rs.getString("SHIPTO_STATE_ABBREV").trim();
	String shiptoaddressline1 = rs.getString("SHIP_TO_ADDRESS_LINE_1_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_1_DISPLAY").trim();
	String shiptoaddressline2 = rs.getString("SHIP_TO_ADDRESS_LINE_2_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_2_DISPLAY").trim();
	String shiptoaddressline3 = rs.getString("SHIP_TO_ADDRESS_LINE_3_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_3_DISPLAY").trim();
    String shiptoaddressline4 = rs.getString("SHIP_TO_ADDRESS_LINE_4_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_4_DISPLAY").trim();
    String shiptoaddressline5 = rs.getString("SHIP_TO_ADDRESS_LINE_5_DISPLAY")==null?"":rs.getString("SHIP_TO_ADDRESS_LINE_5_DISPLAY").trim();
    String shiptocity = rs.getString("SHIPTO_CITY")==null?"":rs.getString("SHIPTO_CITY").trim();
	String shiptozip = rs.getString("SHIPTO_ZIP")==null?"":rs.getString("SHIPTO_ZIP").trim();
	String shiptolocationdesc = rs.getString("SHIPTO_LOCATION_DESC")==null?"":rs.getString("SHIPTO_LOCATION_DESC").trim();
	String shipToFacilityId = rs.getString("SHIP_TO_FACILITY_ID")==null?"":rs.getString("SHIP_TO_FACILITY_ID").trim();

	String shipto1 = HelpObjs.findReplace(ship_to_location_id,"#","%23");
	poout.println("selectedRow = opener.document.getElementById(\"shiptoid\");\n");
    poout.println("selectedRow.value = \""+shipto1+"\";\n");

	poout.println("selectedRow = opener.document.getElementById(\"shiptocompanyid\");\n");
    poout.println("selectedRow.value = \""+ship_to_company_id+"\";\n");

		poout.println("shipToFacilityId = opener.document.getElementById(\"shipToFacilityId\");\n");
      poout.println("shipToFacilityId.value = \""+shipToFacilityId+"\";\n");

	if (ship_to_location_id.trim().length() > 0)
	{
	  poout.println("selectedRow = opener.document.getElementById(\"validshipto\");\n");
	  poout.println("selectedRow.value = \"Yes\";\n");
	}
	else
	{
	  poout.println("selectedRow = opener.document.getElementById(\"validshipto\");\n");
	  poout.println("selectedRow.value = \"No\";\n");
	}

	poout.println("selectedRow = opener.document.getElementById(\"shiptoline1\");\n");
    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptolocationdesc)+"</FONT>\";\n");

    poout.println("selectedRow = opener.document.getElementById(\"shiptoline2\");\n");
    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptoaddressline1)+"</FONT>\";\n");

    poout.println("selectedRow = opener.document.getElementById(\"shiptoline3\");\n");
    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptoaddressline2)+"</FONT>\";\n");

    poout.println("selectedRow = opener.document.getElementById(\"shiptoline4\");\n");
    poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptoaddressline3)+"</FONT>\";\n");

	/*if (shiptoaddressline3.length() >0)
	{
      poout.println("selectedRow = opener.document.getElementById(\"shiptoline4\");\n");
	  poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+HelpObjs.addescapesForJavascript(shiptoaddressline3)+"</FONT>\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"shiptoline5\");\n");
	  poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+shiptocity+", "+shiptostateabbrev+" "+shiptozip+" "+shiptocountryabbrev+"</FONT>\";\n");
	}
	else
	{
	  poout.println("selectedRow = opener.document.getElementById(\"shiptoline4\");\n");
	  poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">"+shiptocity+", "+shiptostateabbrev+" "+shiptozip+" "+shiptocountryabbrev+"</FONT>\";\n");

	  poout.println("selectedRow = opener.document.getElementById(\"shiptoline5\");\n");
	  poout.println("selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n");
	}*/
	}
  }
  catch ( Exception e )
  {
    e.printStackTrace();
  }
  finally
  {
	if (dbrs != null)  { dbrs.close(); }
  }



  /*poout.println( "selectedRow = opener.document.getElementById(\"carrierID\");\n" );
  poout.println( "selectedRow.className = \"HEADER\";\n" );
  poout.println( "selectedRow.value = \"\";\n" );

  poout.println( "selectedRow = opener.document.getElementById(\"carrieraccount\");\n" );
  poout.println( "selectedRow.value = \"\";\n" );

  poout.println( "selectedRow = opener.document.getElementById(\"validcarrier\");\n" );
  poout.println( "selectedRow.value = \"Yes\";\n" );

  poout.println( "selectedRow = opener.document.getElementById(\"carrierline1\");\n" );
  poout.println( "selectedRow.innerHTML = \"\";\n" );

  poout.println( "selectedRow = opener.document.getElementById(\"carrierline2\");\n" );
  poout.println( "selectedRow.innerHTML = \"\";\n" );*/

  poout.println("   qtyrechead = opener.document.getElementById(\"qtyrechead\");\n");
  poout.println("   qtyrechead.innerHTML = \"Qty Rem\";\n");

  buildbPolineDetails( radianPO,ship_to_location_id,ship_to_company_id,opsEntityId,poout);

  poout.println( "opener.makeCursorNormal();\n" );
  poout.println( "window.close();\n" );
  poout.println( " }\n" );
  poout.println( "with ( milonic=new menuname(\"contactLogMenu\") ) {\n" );
  poout.println( "top=\"offset=2\";\n" );
  poout.println( "style=submenuStyle;\n" );
  poout.println( "itemheight=17;\n" );
  poout.println( "aI( \"text=Contact Log ;url=javascript:doNothing();\")\n" );
  poout.println( "}\n" );
  poout.println( "// -->\n</SCRIPT>\n" );
  //poout.println( Msgtemp );
  poout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
  poout.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
  poout.println( "<CENTER><BR><BR>\n" );
  poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
  poout.println( "</FORM></BODY></HTML>\n" );
  //poout.println( Msgbody );

  return;
}

public void buildbPolineDetails( String radianpo,String ShipTo,String ship_to_company_id,String opsEntityId,PrintWriter poout )
{
  //StringBuffer MsgLine=new StringBuffer();
  Vector addChargev=new Vector();
  Hashtable addCharges=new Hashtable();
  Vector specFlowv=new Vector();
  Hashtable specflowd=new Hashtable();
  DBResultSet dbrs = null;
  ResultSet rs = null;
  String polinedetail="";
  polinedetail+="select PURCHASING_UNITS_PER_ITEM, PURCHASING_UNIT_OF_MEASURE,CURRENCY_ID,BPO_QUANTITY_REMAINING,BPO,ITEM_TYPE,BPO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(DATE_CLOSED,'mm/dd/yyyy') DATE_CLOSED,to_char(DATE_CONFIRMED,'mm/dd/yyyy') DATE_CONFIRMED, ";
  polinedetail+="ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, ";
  polinedetail+="REMAINING_SHELF_LIFE_PERCENT,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,BPO_LINE_NOTE,ITEM_DESC,PACKAGING,MIN_BUY,MIN_BUY_BASIS,PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED from bpo_line_detail_view where BPO = " + radianpo + " order by BPO_LINE";

  int count=0;

  //DecimalFormat lineTotal=new DecimalFormat( "####.0000" );
  //DecimalFormat poTotal=new DecimalFormat( "####.00" );

  try
  {
    dbrs=db.doQuery( polinedetail );
    rs=dbrs.getResultSet();

    while ( rs.next() )
    {
      count++;
      //String bpo = rs.getString("BPO")==null?"":rs.getString("BPO").trim();
      String bpo_line=rs.getString( "BPO_LINE" ) == null ? "" : rs.getString( "BPO_LINE" ).trim();
      String amendment=rs.getString( "AMENDMENT" ) == null ? "" : rs.getString( "AMENDMENT" ).trim();
      String item_id=rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ).trim();
      String quantity=rs.getString( "QUANTITY" ) == null ? "" : rs.getString( "QUANTITY" ).trim();
      String unit_price=rs.getString( "UNIT_PRICE" ) == null ? "" : rs.getString( "UNIT_PRICE" ).trim();
      //String allowed_price_variance = rs.getString("ALLOWED_PRICE_VARIANCE")==null?"":rs.getString("ALLOWED_PRICE_VARIANCE").trim();
      String mfg_part_no=HelpObjs.addescapesForJavascript( rs.getString( "MFG_PART_NO" ) == null ? "" : rs.getString( "MFG_PART_NO" ).trim() );
      String supplier_part_no=HelpObjs.addescapesForJavascript(rs.getString( "SUPPLIER_PART_NO" ) == null ? "" : rs.getString( "SUPPLIER_PART_NO" ).trim());
      //String quality_flow_downs = rs.getString("QUALITY_FLOW_DOWNS")==null?"":rs.getString("QUALITY_FLOW_DOWNS").trim();
      //String packaging_flow_downs = rs.getString("PACKAGING_FLOW_DOWNS")==null?"":rs.getString("PACKAGING_FLOW_DOWNS").trim();
      String bpo_line_note=HelpObjs.addescapesForJavascript( rs.getString( "BPO_LINE_NOTE" ) == null ? "" : rs.getString( "BPO_LINE_NOTE" ).trim() );
      if ( bpo_line_note.length() > 0 )
      {
        bpo_line_note=HelpObjs.findReplace( bpo_line_note,"<BR>","\\n" );
      }
      //String date_closed = rs.getString("DATE_CLOSED")==null?"":rs.getString("DATE_CLOSED").trim();
      //String min_buy = rs.getString("MIN_BUY")==null?"":rs.getString("MIN_BUY").trim();
      //String min_buy_basis = rs.getString("MIN_BUY_BASIS")==null?"":rs.getString("MIN_BUY_BASIS").trim();
      //String date_confirmed = rs.getString("DATE_CONFIRMED")==null?"":rs.getString("DATE_CONFIRMED").trim();
      String item_desc=HelpObjs.addescapesForJavascript( rs.getString( "ITEM_DESC" ) == null ? "" : rs.getString( "ITEM_DESC" ).trim() );
      String item_type=rs.getString( "ITEM_TYPE" ) == null ? "" : rs.getString( "ITEM_TYPE" ).trim();
      String packaging=HelpObjs.addescapesForJavascript( rs.getString( "PACKAGING" ) == null ? "" : rs.getString( "PACKAGING" ).trim() );
      String polinestatus=rs.getString( "PO_LINE_STATUS" ) == null ? "" : rs.getString( "PO_LINE_STATUS" ).trim();
      String bpoqtyremm=rs.getString( "BPO_QUANTITY_REMAINING" ) == null ? "" : rs.getString( "BPO_QUANTITY_REMAINING" ).trim();
      String qtyvouchered=rs.getString( "QUANTITY_VOUCHERED" ) == null ? "" : rs.getString( "QUANTITY_VOUCHERED" ).trim();
      String supplier_qty=rs.getString( "SUPPLIER_QTY" ) == null ? "" : rs.getString( "SUPPLIER_QTY" ).trim();
      String supplier_pkg=rs.getString( "SUPPLIER_PKG" ) == null ? "" : rs.getString( "SUPPLIER_PKG" ).trim();
      String supplier_unit_price=rs.getString( "SUPPLIER_UNIT_PRICE" ) == null ? "" : rs.getString( "SUPPLIER_UNIT_PRICE" ).trim();
      String shelflife=rs.getString( "REMAINING_SHELF_LIFE_PERCENT" ) == null ? "" : rs.getString( "REMAINING_SHELF_LIFE_PERCENT" ).trim();
      String everconfirmed=rs.getString( "EVER_CONFIRMED" ) == null ? "" : rs.getString( "EVER_CONFIRMED" ).trim();
      String currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();
      String purchasingUnitsPerItem=rs.getString( "PURCHASING_UNITS_PER_ITEM" ) == null ? "" : rs.getString( "PURCHASING_UNITS_PER_ITEM" ).trim();
      String purchasingUnitOfMeasure=rs.getString( "PURCHASING_UNIT_OF_MEASURE" ) == null ? "" : rs.getString( "PURCHASING_UNIT_OF_MEASURE" ).trim();

      int amendmentNumber=Integer.parseInt( amendment ) * 1;
      //int rownum1=Integer.parseInt( bpo_line );
      //rownum1= ( rownum1 / 1000 );
      BigDecimal rowNumer= new BigDecimal(bpo_line);
      rowNumer = rowNumer.divide(new BigDecimal("1000"));
      rowNumer.setScale(1,3);
      
      String Color=" ";
      String itemColor=" ";
      if ( count % 2 == 0 )
      {
        Color="CLASS=\"blue\"";
        itemColor="INVISIBLEHEADBLUE";
      }
      else
      {
        Color="CLASS=\"white\"";
        itemColor="INVISIBLEHEADWHITE";
      }

      if ( !addchargeslist.contains( item_type ) )
      {
        poout.println( "opener.addLineItem(); \n" );
        poout.println( "everConfirmed = opener.document.getElementById(\"everConfirmed" + count + "\");\n" );
        poout.println( "everConfirmed.value = \"" + everconfirmed + "\";\n" );

        poout.println( "linenumber = opener.document.getElementById(\"linenumberammn" + count + "\");\n" );
        poout.println( "linenumber.innerHTML = \"" + rowNumer + "/" + amendment + "\";\n" );

		poout.println( "canassignaddcharge = opener.document.getElementById(\"canassignaddcharge" + count + "\");\n" );
		poout.println( "canassignaddcharge.value = \"Y\";\n" );

		poout.println( "purchasingUnitsPerItem = opener.document.getElementById(\"purchasingUnitsPerItem" + count + "\");\n" );
		poout.println( "purchasingUnitsPerItem.value = \""+purchasingUnitsPerItem+"\";\n" );

        if ( item_id.trim().length() > 0 )
        {
          poout.println( "validitem = opener.document.getElementById(\"validitem" + count + "\");\n" );
          poout.println( "validitem.value = \"Yes\";\n" );

          poout.println( "lineitemid = opener.document.getElementById(\"lineitemid" + count + "\");\n" );
          poout.println( "lineitemid.className = \"" + itemColor + "\";\n" );
          poout.println( "lineitemid.value = \"" + item_id + "\";\n" );
        }
        else
        {
          poout.println( "validitem = opener.document.getElementById(\"validitem" + count + "\");\n" );
          poout.println( "validitem.value = \"No\";\n" );

          poout.println( "lineitemid = opener.document.getElementById(\"lineitemid" + count + "\");\n" );
          poout.println( "lineitemid.className = \"INVALIDTEXT\";\n" );
          poout.println( "lineitemid.value = \"\";\n" );
        }

        if ( ( amendmentNumber > 0 ) || ( "Y".equalsIgnoreCase( everconfirmed ) ) && ( item_id.trim().length() > 0 ) )
        {
          poout.println( "lineitemid.readOnly = true;\n" );
          poout.println( "buttonlineitemid = opener.document.getElementById(\"buttonlineitemid" + count + "\");\n" );
          poout.println( "buttonlineitemid.disabled=true;\n" );
          poout.println( "buttonlineitemid.style.display=\"none\";\n" );
        }

		poout.println( "currencyid = opener.document.getElementById(\"currency\");\n" );
		poout.println( "currencyid.value = \"" + currencyid + "\";\n" );

        poout.println( "itemtype = opener.document.getElementById(\"itemtype" + count + "\");\n" );
        poout.println( "itemtype.value = \"" + item_type + "\";\n" );

        poout.println( "itemtypecell = opener.document.getElementById(\"itemtypecell" + count + "\");\n" );
        poout.println( "itemtypecell.innerHTML = \"" + item_type + "\";\n" );

        poout.println( "pakgingcell = opener.document.getElementById(\"pakgingcell" + count + "\");\n" );
        poout.println( "pakgingcell.innerHTML = \"" + packaging + "\";\n" );

        poout.println( "dateneeded = opener.document.getElementById(\"dateneeded" + count + "\");\n" );
        poout.println( "dateneeded.value = \"\";\n" );
		poout.println( "dateneeded.className = \"" + itemColor + "\";\n" );
		poout.println( "dateneeded.readOnly = true;\n" );
		poout.println( "datelinkdateneeded = opener.document.getElementById(\"datelinkdateneeded" + count + "\");\n" );
		poout.println( "datelinkdateneeded.style.display=\"none\";\n" );

        poout.println( "datepromised = opener.document.getElementById(\"datepromised" + count + "\");\n" );
        poout.println( "datepromised.value = \"\";\n" );
		poout.println( "datepromised.className = \"" + itemColor + "\";\n" );
		poout.println( "datepromised.readOnly = true;\n" );
		poout.println( "datelinkdatepromised = opener.document.getElementById(\"datelinkdatepromised" + count + "\");\n" );
		poout.println( "datelinkdatepromised.style.display=\"none\";\n" );

		poout.println( "projecteddelivdate = opener.document.getElementById(\"projsuppshipdate" + count + "\");\n" );
		poout.println( "projecteddelivdate.value = \"\";\n" );
		poout.println( "projecteddelivdate.className = \"" + itemColor + "\";\n" );
		poout.println( "projecteddelivdate.readOnly = true;\n" );
		poout.println( "projecteddelivdate = opener.document.getElementById(\"datelinkprojsuppshipdate" + count + "\");\n" );
		poout.println( "projecteddelivdate.style.display=\"none\";\n" );

        poout.println( "lineqty = opener.document.getElementById(\"lineqty" + count + "\");\n" );
        poout.println( "lineqty.value = \"" + quantity + "\";\n" );

        poout.println( "lineunitprice = opener.document.getElementById(\"lineunitprice" + count + "\");\n" );
        poout.println( "lineunitprice.value = \"" + unit_price + "\";\n" );

        BigDecimal qtyVouchered= new BigDecimal("0");
        if ( qtyvouchered.trim().length() > 0 )
        {
          qtyVouchered= new BigDecimal( qtyvouchered );
        }
        if ( qtyVouchered.intValue() > 0 )
        {
          poout.println( "lineunitprice.className = \"" + itemColor + "\";\n" );
          poout.println( "lineunitprice.readOnly = true;\n" );
        }

        //float suptotal=0;
				BigDecimal suptotal= new BigDecimal("0");
        if ( supplier_qty.length() > 0 && supplier_unit_price.length() > 0 )
        {
          BigDecimal supqtyF= new BigDecimal( supplier_qty );
          BigDecimal supunitPrice= new BigDecimal( supplier_unit_price );
          suptotal= ( supqtyF.multiply(supunitPrice));
        }
        poout.println( "supplierextprice = opener.document.getElementById(\"supplierextprice" + count + "\");\n" );
        poout.println( "supplierextprice.value = \"" + suptotal.setScale(4,4) + " "+currencyid+"\";\n" );

		BigDecimal total = new BigDecimal("0");  
        if ( quantity.length() > 0 && unit_price.length() > 0 )
        {
          BigDecimal qtyF= new BigDecimal( quantity );
          BigDecimal unitPrice= new BigDecimal( unit_price );
          total = (qtyF.multiply(unitPrice));
          if(isOneUSD || "USA".equalsIgnoreCase(countryAbbrev) || "USD".equalsIgnoreCase(currencyid))
        	  isOneUSD = true; 
          else
        	  orderTotal = orderTotal.add(total);
          orderTotalColl.add(new String[]{unit_price,quantity,currencyid});
        }

        poout.println( "linetotalprice = opener.document.getElementById(\"linetotalprice" + count + "\");\n" );
        poout.println( "linetotalprice.value = \"" + total.setScale(4,4) + "\";\n" );
        
        poout.println( "itemghscompliantcell = opener.document.getElementById(\"itemghscompliantcell" + count + "\");\n" );
        poout.println( "itemghscompliantcell.value = \"" + total.setScale(4,4) + "\";\n" );

        poout.println( "extpricecell = opener.document.getElementById(\"extpricecell" + count + "\");\n" );
        poout.println( "extpricecell.innerHTML = \"" + total.setScale(4,4) + " "+currencyid+" \";\n" );

        poout.println( "qtyreceivedcell = opener.document.getElementById(\"qtyreceivedcell" + count + "\");\n" );
        poout.println( "qtyreceivedcell.innerHTML = \"" + bpoqtyremm + "\";\n" );

        poout.println( "qtyvouchered = opener.document.getElementById(\"qtyvouchered" + count + "\");\n" );
        poout.println( "qtyvouchered.innerHTML = \"" + qtyvouchered + "\";\n" );

        poout.println( "detaillinenumber = opener.document.getElementById(\"detaillinenumber" + count + "\");\n" );
        poout.println( "detaillinenumber.value = \"" + rowNumer + "\";\n" );

        poout.println( "mpn = opener.document.getElementById(\"mpn" + count + "\");\n" );
        poout.println( "mpn.value = \"" + mfg_part_no + "\";\n" );

        poout.println( "supplierpn = opener.document.getElementById(\"supplierpn" + count + "\");\n" );
        poout.println( "supplierpn.value = \"" + supplier_part_no + "\";\n" );

        poout.println( "supplierqty = opener.document.getElementById(\"supplierqty" + count + "\");\n" );
        poout.println( "supplierqty.value = \"" + supplier_qty + "\";\n" );

        poout.println( "supplierpkg = opener.document.getElementById(\"supplierpkg" + count + "\");\n" );
        poout.println( "supplierpkg.value = \"" + supplier_pkg + "\";\n" );

        poout.println( "supplierunitprice = opener.document.getElementById(\"supplierunitprice" + count + "\");\n" );
        poout.println( "supplierunitprice.value = \"" + supplier_unit_price + "\";\n" );

        poout.println( "shelflife = opener.document.getElementById(\"shelflife" + count + "\");\n" );
        poout.println( "shelflife.value = \"" + shelflife + "\";\n" );
        
        poout.println( "slHidden = opener.document.getElementById(\"slHidden" + count + "\");\n" );
        poout.println( "try{slHidden.value = \"" + shelflife + "\";}catch(ex){}\n" );

        poout.println( "dpas = opener.document.getElementById(\"dpas" + count + "\");\n" );
        poout.println( "dpas.value = \"\";\n" );

        poout.println( "itemdescdivrow = opener.document.getElementById(\"itemdescdivrow" + count + "" + count + "\");\n" );
        poout.println( "itemdescdivrow.innerHTML = \"" + item_desc + "\";\n" );

        poout.println( "linenotesdivrow = opener.document.getElementById(\"linenotesdivrow" + count + "" + count + "\");\n" );
        poout.println( "linenotesdivrow.value = \"" + bpo_line_note + "\";\n" );

        BigDecimal qtyonline= new BigDecimal("0");
        BigDecimal qtyintreceieved= new BigDecimal("0");

        if ( quantity.length() > 0 )
        {
          qtyonline= new BigDecimal( quantity );
        }
        if ( bpoqtyremm.length() > 0 )
        {
          qtyintreceieved= qtyonline.subtract(new BigDecimal ( bpoqtyremm ));
        }

        poout.println( "qtyreceived = opener.document.getElementById(\"qtyreceived" + count + "\");\n" );
        poout.println( "qtyreceived.value = \"" + qtyintreceieved + "\";\n" );

        poout.println( "linestatustext = opener.document.getElementById(\"linestatustext" + count + "\");\n" );
        if ( qtyintreceieved.compareTo(qtyonline) == 0 || qtyintreceieved.compareTo(qtyonline) == 1)
        {
          poout.println( "linestatustext.innerHTML = \"Closed\";\n" );
        }
        else
        {
          poout.println( "linestatustext.innerHTML = \"" + polinestatus + "\";\n" );
        }

        poout.println( "linestatus = opener.document.getElementById(\"linestatus" + count + "\");\n" );
        poout.println( "linestatus.value = \"" + polinestatus + "\";\n" );

        poout.println( "linechange = opener.document.getElementById(\"linechange" + count + "\");\n" );
        poout.println( "linechange.value = \"No\";\n" );

        poout.println( "originallinestatus = opener.document.getElementById(\"originallinestatus" + count + "\");\n" );
        poout.println( "originallinestatus.value = \"" + polinestatus + "\";\n" );

        poout.println( "rowlinenumber = opener.document.getElementById(\"row" + count + "linenumber\");\n" );
        poout.println( "rowlinenumber.value = \"" + rowNumer + "\";\n" );

        poout.println( "rowrow = opener.document.getElementById(\"row" + count + "row\");\n" );
        poout.println( "rowrow.value = \"" + count + "\";\n" );

        poout.println( "ammendment = opener.document.getElementById(\"ammendment" + count + "\");\n" );
        poout.println( "ammendment.value = \"" + amendment + "\";\n" );

        specflowd=new Hashtable();
        specflowd.put( "LINE","" + count + "" );
        specflowd.put( "ITEMID","" + item_id + "" );
        specflowd.put( "AMMENDMEANT","" + amendment + "" );
        specflowd.put( "EVERCONFIRMED","" + everconfirmed + "" );
        specFlowv.addElement( specflowd );
      }
      else //if ("MA".equalsIgnoreCase(item_type))
      {
        addCharges=new Hashtable();
        addCharges.put( "PO_LINE","" + bpo_line + "" );
        addCharges.put( "LINE","" + count + "" );
        addCharges.put( "AMENDMENT","" + amendment + "" );
        addCharges.put( "ITEMDESC","" + item_desc + "" );
        addChargev.addElement( addCharges );

        poout.println( "opener.addLineCharge(); \n" );

        poout.println( "everConfirmed = opener.document.getElementById(\"everConfirmed" + count + "\");\n" );
        poout.println( "everConfirmed.value = \"" + everconfirmed + "\";\n" );

        poout.println( "linenumber = opener.document.getElementById(\"linenumberammn" + count + "\");\n" );
        poout.println( "linenumber.innerHTML = \"" + rowNumer + "/" + amendment + "\";\n" );

        if ( item_id.trim().length() > 0 )
        {
          poout.println( "selectedRow = opener.document.getElementById(\"validitem" + count + "\");\n" );
          poout.println( "selectedRow.value = \"Yes\";\n" );

          poout.println( "lineitemid = opener.document.getElementById(\"lineitemid" + count + "\");\n" );
          poout.println( "lineitemid.className = \"" + itemColor + "\";\n" );
          poout.println( "lineitemid.value = \"" + item_id + "\";\n" );
        }
        else
        {
          poout.println( "selectedRow = opener.document.getElementById(\"validitem" + count + "\");\n" );
          poout.println( "selectedRow.value = \"No\";\n" );

          poout.println( "lineitemid = opener.document.getElementById(\"lineitemid" + count + "\");\n" );
          poout.println( "lineitemid.className = \"INVALIDTEXT\";\n" );
          poout.println( "lineitemid.value = \"\";\n" );
        }

        //TO DO chANGE THIS CONDItion to the line being confirmed atleast once
        if ( ( amendmentNumber > 0 ) || ( "Y".equalsIgnoreCase( everconfirmed ) ) && ( item_id.trim().length() > 0 ) )
        {
          poout.println( "lineitemid.readOnly = true;\n" );
          poout.println( "buttonlineitemid = opener.document.getElementById(\"buttonlineitemid" + count + "\");\n" );
          poout.println( "buttonlineitemid.disabled=true;\n" );
          poout.println( "buttonlineitemid.style.display=\"none\";\n" );
        }

        poout.println( "itemtype = opener.document.getElementById(\"itemtype" + count + "\");\n" );
        poout.println( "itemtype.value = \"" + item_type + "\";\n" );

        poout.println( "itemtypecell = opener.document.getElementById(\"itemtypecell" + count + "\");\n" );
        poout.println( "itemtypecell.innerHTML = \"" + item_type + "\";\n" );

        poout.println( "pakgingcell = opener.document.getElementById(\"pakgingcell" + count + "\");\n" );
        poout.println( "pakgingcell.innerHTML = \"\";\n" );

        poout.println( "lineqty = opener.document.getElementById(\"lineqty" + count + "\");\n" );
        poout.println( "lineqty.value = \"" + quantity + "\";\n" );

        poout.println( "lineunitprice = opener.document.getElementById(\"lineunitprice" + count + "\");\n" );
        poout.println( "lineunitprice.value = \"" + unit_price + "\";\n" );

        BigDecimal qtyVouchered= new BigDecimal("0");
        if ( qtyvouchered.trim().length() > 0 )
        {
          qtyVouchered= new BigDecimal( qtyvouchered );
        }
        if ( qtyVouchered.intValue() > 0 )
        {
          poout.println( "lineunitprice.className = \"" + itemColor + "\";\n" );
          poout.println( "lineunitprice.readOnly = true;\n" );
        }

        BigDecimal total= new BigDecimal("0");
        BigDecimal qtyF= new BigDecimal("0");
        if ( quantity.length() > 0 && unit_price.length() > 0 )
        {
          qtyF= new BigDecimal( quantity );
          BigDecimal unitPrice= new BigDecimal( unit_price );
          total= qtyF.multiply(unitPrice);
          if(isOneUSD || "USA".equalsIgnoreCase(countryAbbrev) || "USD".equalsIgnoreCase(currencyid))
        	  isOneUSD = true; 
          else
        	  orderTotal = orderTotal.add(total);
          orderTotalColl.add(new String[]{unit_price,quantity,currencyid});
        }

        poout.println( "extpricecell = opener.document.getElementById(\"extpricecell" + count + "\");\n" );
        poout.println( "extpricecell.innerHTML = \"" + total.setScale(4,4) + "  "+currencyid+" \";\n" );

        poout.println( "qtyreceivedcell = opener.document.getElementById(\"qtyreceivedcell" + count + "\");\n" );
        poout.println( "qtyreceivedcell.innerHTML = \"" + bpoqtyremm + "\";\n" );

        BigDecimal qtyintreceieved= new BigDecimal("0");
        if ( bpoqtyremm.length() > 0 )
        {
				 qtyintreceieved= qtyF.subtract(new BigDecimal( bpoqtyremm ));
        }

        poout.println( "qtyreceived = opener.document.getElementById(\"qtyreceived" + count + "\");\n" );
        poout.println( "qtyreceived.value = \"" + qtyintreceieved + "\";\n" );

        poout.println( "linestatustext = opener.document.getElementById(\"linestatustext" + count + "\");\n" );
        if ( qtyintreceieved.compareTo(qtyF) == 0 || qtyintreceieved.compareTo(qtyF) == 1 )
        {
          poout.println( "linestatustext.innerHTML = \"Closed\";\n" );
        }
        else
        {
          poout.println( "linestatustext.innerHTML = \"" + polinestatus + "\";\n" );
        }

        poout.println( "linestatus = opener.document.getElementById(\"linestatus" + count + "\");\n" );
        poout.println( "linestatus.value = \"" + polinestatus + "\";\n" );

        poout.println( "linechange = opener.document.getElementById(\"linechange" + count + "\");\n" );
        poout.println( "linechange.value = \"No\";\n" );

        poout.println( "originallinestatus = opener.document.getElementById(\"originallinestatus" + count + "\");\n" );
        poout.println( "originallinestatus.value = \"" + polinestatus + "\";\n" );

        poout.println( "rowlinenumber = opener.document.getElementById(\"row" + count + "linenumber\");\n" );
        poout.println( "rowlinenumber.value = \"" + rowNumer + "\";\n" );

        poout.println( "rowrow = opener.document.getElementById(\"row" + count + "row\");\n" );
        poout.println( "rowrow.value = \"" + count + "\";\n" );

        poout.println( "ammendment = opener.document.getElementById(\"ammendment" + count + "\");\n" );
        poout.println( "ammendment.value = \"" + amendment + "\";\n" );
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
  
  if(isOneUSD)
	  recalTotWithConversion(radianpo);

  poout.println( "selectedRow = opener.document.getElementById(\"totallines\");\n" );
  poout.println( "selectedRow.value = \"" + count + "\";\n" );

  poout.println( "totalcost = opener.document.getElementById(\"totalcost\");\n" );
  poout.println( "totalcost.value = \"" + orderTotal.setScale(2,4) + ("USA".equalsIgnoreCase(countryAbbrev) ?" USD":"") + "\";\n" );

  if ( count > 0 )
  {
    poout.println( "selectedRow1 = opener.document.getElementById(\"row1\");\n" );
    poout.println( "selectedRow1.style.backgroundColor = \"#8a8aff\";\n" );

    poout.println( "var target1 = opener.document.getElementById(\"divrow1\");\n" );
    poout.println( "target1.style.display = \"block\";\n" );
  }

  for ( int chargeline=0; chargeline < addChargev.size(); chargeline++ )
  {
    Hashtable addchargesData=new Hashtable();
    addchargesData= ( Hashtable ) addChargev.elementAt( chargeline );

    String addchargeLine=addchargesData.get( "PO_LINE" ).toString().trim();
    String linsinJs=addchargesData.get( "LINE" ).toString().trim();
    String ammenNumber=addchargesData.get( "AMENDMENT" ).toString().trim();

    //int rownum1=Integer.parseInt( addchargeLine );
    //rownum1= ( rownum1 / 1000 );
    BigDecimal rowNumer= new BigDecimal(addchargeLine);
    rowNumer = rowNumer.divide(new BigDecimal("1000"));
    rowNumer.setScale(1,3);

    String itemdesc=addchargesData.get( "ITEMDESC" ).toString().trim();
    poout.println( "opener.refreshlinecharges(" + linsinJs + ",'No'); \n" );

    poout.println( "itemdescdivrow = opener.document.getElementById(\"itemdescdivrow" + rowNumer + "" + rowNumer + "\");\n" );
    poout.println( "itemdescdivrow.innerHTML = \"" + itemdesc + "\";\n" );

    int addchargecount=0;
    try
    {
      dbrs=db.doQuery( "Select BPO_LINE from bpo_add_charge_allocation_all where BPO = " + radianpo + " and BPO_ADD_CHARGE_LINE = " + addchargeLine +
                       " and AMENDMENT = " + ammenNumber + "" );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        addchargecount++;
        String po_line=rs.getString( "BPO_LINE" ) == null ? "" : rs.getString( "BPO_LINE" ).trim();

        //int addChgrownum1=Integer.parseInt( po_line );
        //addChgrownum1= ( addChgrownum1 / 1000 );
        BigDecimal addChargerowNumer= new BigDecimal(po_line);
        addChargerowNumer = addChargerowNumer.divide(new BigDecimal("1000"));
        addChargerowNumer.setScale(1,3);

        String addChgrownum="" + addChargerowNumer + "";

        poout.println( "chargeAncnumber = opener.document.getElementById(\"chargeAncnumber" + linsinJs + "" + addChgrownum + "\");\n" );
        poout.println( "chargeAncnumber.value = \"Yes\";\n" );
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
    poout.println( "opener.checkAddChargeLines(" + linsinJs + "); \n" );
  }

  for ( int specflowLine=0; specflowLine < specFlowv.size(); specflowLine++ )
  {
    Hashtable specflowLineData=new Hashtable();
    specflowLineData= ( Hashtable ) specFlowv.elementAt( specflowLine );
    String lineItemid=specflowLineData.get( "ITEMID" ).toString().trim();
    String linsinJs=specflowLineData.get( "LINE" ).toString().trim();
    String ammenNumber=specflowLineData.get( "AMMENDMEANT" ).toString().trim();

    if ( ( lineItemid.length() > 0 ) && !lineItemid.equalsIgnoreCase( "0" ) )
    {
      radian.web.poHelpObj.buildsendSpecs( db,"yes",linsinJs,lineItemid,radianpo,"",ShipTo,ammenNumber,ship_to_company_id,"",opsEntityId, poout,this.getSpecOverRide(),res);

      poout.println( "validspec = opener.document.getElementById(\"validspec" + linsinJs + "\");\n" );
      poout.println( "validspec.value = \"Yes\";\n" );

      radian.web.poHelpObj.buildsendFlowdowns( db,"yes",linsinJs,lineItemid,radianpo,"",ShipTo,ammenNumber,ship_to_company_id,poout,res);

      poout.println( "validflowdown = opener.document.getElementById(\"validflowdown" + linsinJs + "\");\n" );
      poout.println( "validflowdown.value = \"Yes\";\n" );

      radian.web.poHelpObj.buildsenditemNotes( db,linsinJs,lineItemid,"yes",poout,res);
      radian.web.poHelpObj.buildshowTcmBuys( db,linsinJs,lineItemid,"yes",ShipTo,opsEntityId,poout,res);
    }
  }

  return;
}

public void buildPolineDetails( String radianpo,String ShipTo,String ship_to_company_id,String invengrp,String opsEntityId,String branchPlant,PrintWriter poout )
{
  //StringBuffer MsgLine=new StringBuffer();
  ResultSet linedetailRs=null;
  DBResultSet lineDetaildbrs=null;
  Vector addChargev=new Vector();
  Hashtable addCharges=new Hashtable();
  Vector specFlowv=new Vector();
  Hashtable specflowd=new Hashtable();
  Hashtable msdsData=new Hashtable();
  DBResultSet dbrs = null;
  ResultSet rs = null;
  boolean isProblemWBuy = false;
  String buyOrderStatus = "";
  
  try
  {
    lineDetaildbrs=db.doQuery("select status from buy_order where radian_po=" + radianpo);
    linedetailRs=lineDetaildbrs.getResultSet();

    while ( linedetailRs.next() )
    {
        buyOrderStatus=linedetailRs.getString( "status" ) == null ? "" : linedetailRs.getString( "status" ).trim();
    }
    if (buyOrderStatus.equalsIgnoreCase("ProblemWBuy"))
    	isProblemWBuy = true;
    	
  }
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ){dbrs.close();}
	}
  
  String polinedetail="";
  polinedetail+="select SHIP_FROM_LOCATION_ID,SUPPLIER_SALES_ORDER_NO,SHELF_LIFE_BASIS, SHELF_LIFE_DAYS,to_char(LAST_CONFIRMED,'mm/dd/yyyy hh:mi AM') LAST_CONFIRMED, CHANGE_SUPPLIER,PURCHASING_UNITS_PER_ITEM, PURCHASING_UNIT_OF_MEASURE,DIFFERENT_SUPPLIER_ON_LINE,SECONDARY_SUPPLIER_ON_PO, SUPPLIER, SECONDARY_SUPPLIER_ADDRESS,CURRENCY_ID,ITEM_VERIFIED_BY, DATE_ITEM_VERIFIED,to_char(VENDOR_SHIP_DATE,'mm/dd/yyyy') VENDOR_SHIP_DATE,archived,DELIVERY_COMMENTS,RADIAN_PO,ITEM_TYPE,PO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(NEED_DATE,'mm/dd/yyyy') NEED_DATE,to_char(PROMISED_DATE,'mm/dd/yyyy') PROMISED_DATE, ";
  polinedetail+="ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,DPAS_RATING,QUALITY_FLOW_DOWNS,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE, fx_item_ghs_compliant(ITEM_ID) GHS_COMPLIANT, ";
  polinedetail+="QUANTITY_RETURNED,DATE_CONFIRMED,REMAINING_SHELF_LIFE_PERCENT,PACKAGING_FLOW_DOWNS,EVER_CONFIRMED,PO_LINE_NOTE,ITEM_DESC,PACKAGING,PO_LINE_STATUS,QUANTITY_RECEIVED,QUANTITY_VOUCHERED,PR_SHIP_TO, DATA_TRANSFER_STATUS from po_line_detail_view where RADIAN_PO = " + radianpo + " order by PO_LINE";
  
  String polinemsdsdata="";
  polinemsdsdata+="select p.item_id, m.material_id, to_char(msds.revision_date,'dd-Mon-yyyy HH24:MI') revision_date from msds, material m, part p, po_line_detail_view pldv where pldv.RADIAN_PO = " + radianpo + " and pldv.item_id = p.item_id and p.material_id = m.material_id and msds.material_id = m.material_id and msds.revision_date = (select max (revision_date) from msds where msds.material_id = m.material_id) order by p.item_id, m.material_id ";

  try
  {
    lineDetaildbrs=db.doQuery(polinemsdsdata);
    linedetailRs=lineDetaildbrs.getResultSet();

    String currentItem = "";
    StringBuilder materials = new StringBuilder();
    while ( linedetailRs.next() )
    {
      String item = linedetailRs.getString("ITEM_ID");
      if ( ! item.equals(currentItem) && ! StringHandler.isBlankString(currentItem)) {
    	msdsData.put(currentItem, materials);
    	materials = new StringBuilder();
      }
      currentItem = item;
      String materialId=linedetailRs.getString( "MATERIAL_ID" ) == null ? "" : linedetailRs.getString( "MATERIAL_ID" ).trim();
      String revisionDate=linedetailRs.getString( "REVISION_DATE" ) == null ? "" : linedetailRs.getString( "REVISION_DATE" ).trim()+" AM";
      if (materials.length() > 0){
    	  materials.append(",");
      }
      materials.append(materialId).append(" : ").append(revisionDate);
    }
    // put the last one in
    if ( ! StringHandler.isBlankString(currentItem)) {
    	msdsData.put(currentItem, materials);
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
  
  int count=0;
  //DecimalFormat lineTotal=new DecimalFormat( "####.0000" );
  //DecimalFormat poTotal=new DecimalFormat( "####.00" );

  try
  {
    lineDetaildbrs=db.doQuery( polinedetail );
    linedetailRs=lineDetaildbrs.getResultSet();

    while ( linedetailRs.next() )
    {      
      count++;
      //String radian_po = linedetailRs.getString("RADIAN_PO")==null?"":linedetailRs.getString("RADIAN_PO").trim();
      String po_line=linedetailRs.getString( "PO_LINE" ) == null ? "" : linedetailRs.getString( "PO_LINE" ).trim();
      String ghs_compliant=linedetailRs.getString( "GHS_COMPLIANT" ) == null ? "" : linedetailRs.getString( "GHS_COMPLIANT" ).trim();
      String amendment=linedetailRs.getString( "AMENDMENT" ) == null ? "" : linedetailRs.getString( "AMENDMENT" ).trim();
      String item_id=linedetailRs.getString( "ITEM_ID" ) == null ? "" : linedetailRs.getString( "ITEM_ID" ).trim();
      String quantityOrdered=linedetailRs.getString( "QUANTITY" ) == null ? "" : linedetailRs.getString( "QUANTITY" ).trim();
      String unit_price=linedetailRs.getString( "UNIT_PRICE" ) == null ? "" : linedetailRs.getString( "UNIT_PRICE" ).trim();
      String need_date=linedetailRs.getString( "NEED_DATE" ) == null ? "" : linedetailRs.getString( "NEED_DATE" ).trim();
      String promised_date=linedetailRs.getString( "PROMISED_DATE" ) == null ? "" : linedetailRs.getString( "PROMISED_DATE" ).trim();
      //String allowed_price_variance = linedetailRs.getString("ALLOWED_PRICE_VARIANCE")==null?"":linedetailRs.getString("ALLOWED_PRICE_VARIANCE").trim();
      String mfg_part_no=HelpObjs.addescapesForJavascript( linedetailRs.getString( "MFG_PART_NO" ) == null ? "" : linedetailRs.getString( "MFG_PART_NO" ).trim() );
      String supplier_part_no=HelpObjs.addescapesForJavascript(linedetailRs.getString( "SUPPLIER_PART_NO" ) == null ? "" : linedetailRs.getString( "SUPPLIER_PART_NO" ).trim());
      //String customer_po = linedetailRs.getString("CUSTOMER_PO")==null?"":linedetailRs.getString("CUSTOMER_PO").trim();
      String dpas_rating=linedetailRs.getString( "DPAS_RATING" ) == null ? "" : linedetailRs.getString( "DPAS_RATING" ).trim();
      //String quality_flow_downs = linedetailRs.getString("QUALITY_FLOW_DOWNS")==null?"":linedetailRs.getString("QUALITY_FLOW_DOWNS").trim();
      //String packaging_flow_downs = linedetailRs.getString("PACKAGING_FLOW_DOWNS")==null?"":linedetailRs.getString("PACKAGING_FLOW_DOWNS").trim();
      String po_line_note=HelpObjs.addescapesForJavascript( linedetailRs.getString( "PO_LINE_NOTE" ) == null ? "" : linedetailRs.getString( "PO_LINE_NOTE" ).trim() );
      if ( po_line_note.length() > 0 )
      {
        po_line_note=HelpObjs.findReplace( po_line_note,"<BR>","\\n" );
      }
      String item_desc=HelpObjs.addescapesForJavascript( linedetailRs.getString( "ITEM_DESC" ) == null ? "" : linedetailRs.getString( "ITEM_DESC" ).trim() );
      String item_type=linedetailRs.getString( "ITEM_TYPE" ) == null ? "" : linedetailRs.getString( "ITEM_TYPE" ).trim();
      String packaging=HelpObjs.addescapesForJavascript( linedetailRs.getString( "PACKAGING" ) == null ? "" : linedetailRs.getString( "PACKAGING" ).trim() );
      String polinestatus=linedetailRs.getString( "PO_LINE_STATUS" ) == null ? "" : linedetailRs.getString( "PO_LINE_STATUS" ).trim();
      String qtyreceived=linedetailRs.getString( "QUANTITY_RECEIVED" ) == null ? "" : linedetailRs.getString( "QUANTITY_RECEIVED" ).trim();
      String qtyvouchered=linedetailRs.getString( "QUANTITY_VOUCHERED" ) == null ? "" : linedetailRs.getString( "QUANTITY_VOUCHERED" ).trim();
      String supplier_qty=linedetailRs.getString( "SUPPLIER_QTY" ) == null ? "" : linedetailRs.getString( "SUPPLIER_QTY" ).trim();
      String supplier_pkg=linedetailRs.getString( "SUPPLIER_PKG" ) == null ? "" : linedetailRs.getString( "SUPPLIER_PKG" ).trim();
      String supplier_unit_price=linedetailRs.getString( "SUPPLIER_UNIT_PRICE" ) == null ? "" : linedetailRs.getString( "SUPPLIER_UNIT_PRICE" ).trim();
      String prshipto=linedetailRs.getString( "PR_SHIP_TO" ) == null ? "" : linedetailRs.getString( "PR_SHIP_TO" ).trim();
      String everconfirmed=linedetailRs.getString( "EVER_CONFIRMED" ) == null ? "" : linedetailRs.getString( "EVER_CONFIRMED" ).trim();
      String shelflife=linedetailRs.getString( "REMAINING_SHELF_LIFE_PERCENT" ) == null ? "" : linedetailRs.getString( "REMAINING_SHELF_LIFE_PERCENT" ).trim();
      String dateconfirmed=linedetailRs.getString( "DATE_CONFIRMED" ) == null ? "" : linedetailRs.getString( "DATE_CONFIRMED" ).trim();
      String qtyreturned=linedetailRs.getString( "QUANTITY_RETURNED" ) == null ? "" : linedetailRs.getString( "QUANTITY_RETURNED" ).trim();
      String deliverylinenotes=HelpObjs.addescapesForJavascript( linedetailRs.getString( "DELIVERY_COMMENTS" ) == null ? "" : linedetailRs.getString( "DELIVERY_COMMENTS" ).trim() );
      if ( deliverylinenotes.length() > 0 )
      {
        deliverylinenotes=HelpObjs.findReplace( deliverylinenotes,"<BR>","\\n" );
      }

      String lineArchived=linedetailRs.getString( "archived" ) == null ? "" : linedetailRs.getString( "archived" ).trim(); //12-04-02
      String projecteddelivdate=linedetailRs.getString( "VENDOR_SHIP_DATE" ) == null ? "" : linedetailRs.getString( "VENDOR_SHIP_DATE" ).trim(); //01-13-02
      String itemveriby=linedetailRs.getString( "ITEM_VERIFIED_BY" ) == null ? "" : linedetailRs.getString( "ITEM_VERIFIED_BY" ).trim();
      String itemverifedon=linedetailRs.getString( "DATE_ITEM_VERIFIED" ) == null ? "" : linedetailRs.getString( "DATE_ITEM_VERIFIED" ).trim();
      String currencyid=linedetailRs.getString( "CURRENCY_ID" ) == null ? "" : linedetailRs.getString( "CURRENCY_ID" ).trim();
      String secondarySupplierAllowed=linedetailRs.getString( "SECONDARY_SUPPLIER_ON_PO" ) == null ? "" : linedetailRs.getString( "SECONDARY_SUPPLIER_ON_PO" ).trim();
      String supplier=linedetailRs.getString( "SUPPLIER" ) == null ? "" : linedetailRs.getString( "SUPPLIER" ).trim();
      String secondarySupplierAddress=HelpObjs.addescapesForJavascript(linedetailRs.getString( "SECONDARY_SUPPLIER_ADDRESS" ) == null ? "" : linedetailRs.getString( "SECONDARY_SUPPLIER_ADDRESS" ).trim());
      String differentSupplierOnLine=HelpObjs.addescapesForJavascript(linedetailRs.getString( "DIFFERENT_SUPPLIER_ON_LINE" ) == null ? "" : linedetailRs.getString( "DIFFERENT_SUPPLIER_ON_LINE" ).trim());
      String purchasingUnitsPerItem=linedetailRs.getString( "PURCHASING_UNITS_PER_ITEM" ) == null ? "" : linedetailRs.getString( "PURCHASING_UNITS_PER_ITEM" ).trim();
      String purchasingUnitOfMeasure=linedetailRs.getString( "PURCHASING_UNIT_OF_MEASURE" ) == null ? "" : linedetailRs.getString( "PURCHASING_UNIT_OF_MEASURE" ).trim();
			String changeSupplier = linedetailRs.getString("CHANGE_SUPPLIER")==null?"":linedetailRs.getString("CHANGE_SUPPLIER").trim();
			String lastConfirmed = linedetailRs.getString("LAST_CONFIRMED")==null?"":linedetailRs.getString("LAST_CONFIRMED").trim();
      String shelfLifeBasis = linedetailRs.getString("SHELF_LIFE_BASIS")==null?"":linedetailRs.getString("SHELF_LIFE_BASIS").trim();
      String shelfLifeDays = linedetailRs.getString("SHELF_LIFE_DAYS")==null?"":linedetailRs.getString("SHELF_LIFE_DAYS").trim();
      String shipFromLocationId=linedetailRs.getString("SHIP_FROM_LOCATION_ID")==null?"":linedetailRs.getString("SHIP_FROM_LOCATION_ID").trim();
      String supplierSalesOrderNo=linedetailRs.getString("SUPPLIER_SALES_ORDER_NO")==null?"":linedetailRs.getString("SUPPLIER_SALES_ORDER_NO").trim();
      String dataTransferStatus=linedetailRs.getString("DATA_TRANSFER_STATUS")==null?"":linedetailRs.getString("DATA_TRANSFER_STATUS").trim();
      
      int amendmentNumber=Integer.parseInt( amendment ) * 1;
      //int rownum1=Integer.parseInt( po_line );
      //rownum1= ( rownum1 / 1000 );
      BigDecimal rowNumer= new BigDecimal(po_line);
      rowNumer = rowNumer.divide(new BigDecimal("1000"));
      rowNumer.setScale(1,3);      
      String Color=" ";
      String itemColor=" ";

      if ( count % 2 == 0 )
      {
        Color="CLASS=\"blue\"";
        itemColor="INVISIBLEHEADBLUE";
      }
      else
      {
        Color="CLASS=\"white\"";
        itemColor="INVISIBLEHEADWHITE";
      }

      boolean isitClosed=false;
      boolean isDataTransferStatusClosed=false;
      if ( "Y".equalsIgnoreCase( everconfirmed ) && "0".equalsIgnoreCase( quantityOrdered ) && dateconfirmed.trim().length() > 0 )
      {
        isitClosed=true;
      }
      
      // overrides isitClosed in some logic checks
      if ( dataTransferStatus.toLowerCase().equals("closed") )
    	  isDataTransferStatusClosed=true;

      if ( !addchargeslist.contains( item_type ) )
      {
        poout.println( "opener.addLineItem(); \n" );

        poout.println( "everConfirmed = opener.document.getElementById(\"everConfirmed" + count + "\");\n" );
        poout.println( "everConfirmed.value = \"" + everconfirmed + "\";\n" );

        poout.println( "linenumber = opener.document.getElementById(\"linenumberammn" + count + "\");\n" );
        poout.println( "linenumber.innerHTML = \"" + rowNumer + "/" + amendment + "\";\n" );

		poout.println( "canassignaddcharge = opener.document.getElementById(\"canassignaddcharge" + count + "\");\n" );
		poout.println( "canassignaddcharge.value = \"Y\";\n" );

		poout.println( "purchasingUnitsPerItem = opener.document.getElementById(\"purchasingUnitsPerItem" + count + "\");\n" );
		poout.println( "purchasingUnitsPerItem.value = \""+purchasingUnitsPerItem+"\";\n" );

		poout.println( "shipFromLocationId = opener.document.getElementById(\"shipFromLocationId" + count + "\");\n" );
		poout.println( "shipFromLocationId.value = \""+shipFromLocationId+"\";\n" );

		poout.println( "supplierSalesOrderNo = opener.document.getElementById(\"supplierSalesOrderNo" + count + "\");\n" );
		poout.println( "supplierSalesOrderNo.value = \""+supplierSalesOrderNo+"\";\n" );

    if ( ("MA".equalsIgnoreCase(item_type) || "MV".equalsIgnoreCase(item_type) || "MS".equalsIgnoreCase(item_type)) && "Y".equalsIgnoreCase( everconfirmed ))
		{
		 poout.println( "enterSoleSource = opener.document.getElementById(\"enterSoleSource" + count + "\");\n" );
		 poout.println( "enterSoleSource.style.display=\"\";\n" );
		}

	  if ("Y".equalsIgnoreCase(differentSupplierOnLine) || ("Y".equalsIgnoreCase(secondarySupplierAllowed) && !"Y".equalsIgnoreCase( everconfirmed )))
		{
		poout.println( "secondarysuppliercell = opener.document.getElementById(\"secondarysuppliercelldivrow" + count + "" + count + "\");\n" );
	    poout.println( "var innHtmlline =  \"<input type=\\\"text\\\" size=\\\"5\\\" CLASS=\\\"HEADER\\\" onChange=\\\"invalidateSecondarySupplier('"+count+"')\\\" name='secondarysupplier"+count+"' id='secondarysupplier"+count+"'> \";\n" );
		poout.println( "innHtmlline =  innHtmlline +\"<BUTTON id='secondarysupplierbutton" + count + "' name='secondarysupplierbutton" + count + "' OnClick=getSecondarySupplier('"+count+"') type=button><IMG src=\\\"/images/search_small.gif\\\" alt=\\\"Item ID\\\"></BUTTON>\";\n" );
		poout.println( "innHtmlline =  innHtmlline +\""+secondarySupplierAddress+"\";\n" );
		poout.println( "secondarysuppliercell.innerHTML = innHtmlline;\n" );

		  poout.println( "secondarysupplier = opener.document.getElementById(\"secondarysupplier" + count + "\");\n" );
		  poout.println( "secondarysupplier.value = \"" + supplier + "\";\n" );
		  if ( "N".equalsIgnoreCase(changeSupplier) || !"Y".equalsIgnoreCase(secondarySupplierAllowed))
		  {
			poout.println( "secondarysupplier.readOnly = true;\n" );
			poout.println( "secondarysupplier.className = \"INVISIBLEHEADWHITE\";\n" );
			poout.println( "secondarysupplierbutton = opener.document.getElementById(\"secondarysupplierbutton" + count + "\");\n" );
			poout.println( "secondarysupplierbutton.disabled=true;\n" );
			poout.println( "secondarysupplierbutton.style.display=\"none\";\n" );
		  }
		}

		if (itemveriby.length() > 0)
		{
		  poout.println( "itemveribycell = opener.document.getElementById(\"itemverifiedbydivrow" + count + "" + count + "\");\n" );
		  poout.println( "itemveribycell.innerHTML = \"<TABLE class=\\\"moveup\\\" cellSpacing=\\\"3\\\" cellPadding=\\\"3\\\" width=\\\"50%\\\"><TR><TH>Item Verified By</TH><TH>Verified On</TH></TR><TR><TD WIDTH=\\\"20%\\\" ALIGN=\\\"center\\\">" + itemveriby + "<TD WIDTH=\\\"20%\\\" ALIGN=\\\"center\\\">" + itemverifedon + "</TR><TABLE>\";\n" );
		}

        if ( item_id.trim().length() > 0 )
        {
          poout.println( "validitem = opener.document.getElementById(\"validitem" + count + "\");\n" );
          poout.println( "validitem.value = \"Yes\";\n" );

          poout.println( "lineitemid = opener.document.getElementById(\"lineitemid" + count + "\");\n" );
          poout.println( "lineitemid.className = \"" + itemColor + "\";\n" );
          poout.println( "lineitemid.value = \"" + item_id + "\";\n" );
        }
        else
        {
          poout.println( "validitem = opener.document.getElementById(\"validitem" + count + "\");\n" );
          poout.println( "validitem.value = \"No\";\n" );

          poout.println( "lineitemid = opener.document.getElementById(\"lineitemid" + count + "\");\n" );
          poout.println( "lineitemid.className = \"INVALIDTEXT\";\n" );
          poout.println( "lineitemid.value = \"\";\n" );
        }

        //TO DO chANGE THIS CONDItion to the line being confirmed atleast once or there is a pr associated
        if ( ( amendmentNumber > 0 ) || ( prshipto.trim().length() > 0 && dateconfirmed.trim().length() > 0 ) ||
             ( "Y".equalsIgnoreCase( everconfirmed ) ) && ( ( item_id.trim().length() > 0 || "0".equalsIgnoreCase( quantityOrdered ) ) ) )
        {
          poout.println( "lineitemid.readOnly = true;\n" );
          poout.println( "buttonlineitemid = opener.document.getElementById(\"buttonlineitemid" + count + "\");\n" );
          poout.println( "buttonlineitemid.disabled=true;\n" );
          poout.println( "buttonlineitemid.style.display=\"none\";\n" );
        }

        poout.println( "ghscompliantcell = opener.document.getElementById(\"ghscompliantcell" + count + "\");\n" );
        poout.println( "ghscompliantcell.innerHTML = \"" + ghs_compliant + "\";\n" );
        
		poout.println( "currencyid = opener.document.getElementById(\"currency\");\n" );
        poout.println( "currencyid.value = \"" + currencyid + "\";\n" );

        poout.println( "itemtype = opener.document.getElementById(\"itemtype" + count + "\");\n" );
        poout.println( "itemtype.value = \"" + item_type + "\";\n" );

        poout.println( "itemtypecell = opener.document.getElementById(\"itemtypecell" + count + "\");\n" );
        poout.println( "itemtypecell.innerHTML = \"" + item_type + "\";\n" );
        
        poout.println( "materialId = opener.document.getElementById(\"materialId" + count + "\");\n" );
        poout.println( "materialId.innerHTML = \"" + StringHandler.emptyIfNull(msdsData.get(item_id)) + "\";\n" );
        
        poout.println( "pakgingcell = opener.document.getElementById(\"pakgingcell" + count + "\");\n" );
        poout.println( "pakgingcell.innerHTML = \"" + packaging + "\";\n" );

        poout.println( "dateneeded = opener.document.getElementById(\"dateneeded" + count + "\");\n" );
        poout.println( "dateneeded.value = \"" + need_date + "\";\n" );
        if ( isitClosed || isDataTransferStatusClosed || need_date.length() > 0 )
        {
          poout.println( "dateneeded.className = \"" + itemColor + "\";\n" );
          poout.println( "dateneeded.readOnly = true;\n" );
          poout.println( "datelinkdateneeded = opener.document.getElementById(\"datelinkdateneeded" + count + "\");\n" );
          poout.println( "datelinkdateneeded.style.display=\"none\";\n" );
        }

        poout.println( "projecteddelivdate = opener.document.getElementById(\"projsuppshipdate" + count + "\");\n" );
        poout.println( "projecteddelivdate.value = \"" + projecteddelivdate + "\";\n" );
        if ( isitClosed || isDataTransferStatusClosed )
        {
          poout.println( "projecteddelivdate.className = \"" + itemColor + "\";\n" );
          poout.println( "projecteddelivdate.readOnly = true;\n" );
          poout.println( "projecteddelivdate = opener.document.getElementById(\"datelinkprojsuppshipdate" + count + "\");\n" );
          poout.println( "projecteddelivdate.style.display=\"none\";\n" );
        }

        poout.println( "datepromised = opener.document.getElementById(\"datepromised" + count + "\");\n" );
        poout.println( "datepromised.value = \"" + promised_date + "\";\n" );
        if ( isitClosed || isDataTransferStatusClosed )
        {
          poout.println( "datepromised.className = \"" + itemColor + "\";\n" );
          poout.println( "datepromised.readOnly = true;\n" );
          poout.println( "datelinkdatepromised = opener.document.getElementById(\"datelinkdatepromised" + count + "\");\n" );
          poout.println( "datelinkdatepromised.style.display=\"none\";\n" );
        }

        poout.println( "lineqty = opener.document.getElementById(\"lineqty" + count + "\");\n" );
        poout.println( "lineqty.value = \"" + quantityOrdered + "\";\n" );
        if ( isitClosed || isDataTransferStatusClosed )
        {
          poout.println( "lineqty.className = \"" + itemColor + "\";\n" );
          poout.println( "lineqty.readOnly = true;\n" );
        }

        //archived
        poout.println( "lineArchived = opener.document.getElementById(\"lineArchived" + count + "\");\n" );
        poout.println( "lineArchived.value = \"" + lineArchived + "\";\n" );

        poout.println( "lineunitprice = opener.document.getElementById(\"lineunitprice" + count + "\");\n" );
        poout.println( "lineunitprice.value = \"" + unit_price + "\";\n" );
        if ( isitClosed || isDataTransferStatusClosed )
        {
          poout.println( "lineunitprice.className = \"" + itemColor + "\";\n" );
          poout.println( "lineunitprice.readOnly = true;\n" );
        }

        BigDecimal qtyVouchered= new BigDecimal("0");
        if ( qtyvouchered.trim().length() > 0 )
        {
          qtyVouchered= new BigDecimal( qtyvouchered );
        }
        if ( isDataTransferStatusClosed || qtyVouchered.intValue() > 0 )
        {
          poout.println( "lineunitprice.className = \"" + itemColor + "\";\n" );
          poout.println( "lineunitprice.readOnly = true;\n" );
        }

        BigDecimal suptotal= new BigDecimal("0");
        if ( supplier_qty.length() > 0 && supplier_unit_price.length() > 0 )
        {
          BigDecimal supqtyF= new BigDecimal( supplier_qty );
          BigDecimal supunitPrice= new BigDecimal( supplier_unit_price );
          suptotal= supqtyF.multiply(supunitPrice);
        }
        poout.println( "supplierextprice = opener.document.getElementById(\"supplierextprice" + count + "\");\n" );
        poout.println( "supplierextprice.value = \"" + suptotal.setScale(4,4) + " "+currencyid+"\";\n" );

        BigDecimal total= new BigDecimal("0");        
        if ( quantityOrdered.length() > 0 && unit_price.length() > 0 )
        {
          BigDecimal qtyF= new BigDecimal( quantityOrdered );
          BigDecimal unitPrice=new BigDecimal( unit_price );
          total= qtyF.multiply(unitPrice );
          
          if(isOneUSD || "USA".equalsIgnoreCase(countryAbbrev) || "USD".equalsIgnoreCase(currencyid))
        	  isOneUSD = true; 
          else
        	  orderTotal = orderTotal.add(total);
          orderTotalColl.add(new String[]{unit_price,quantityOrdered,currencyid});
        }


        poout.println( "linetotalprice = opener.document.getElementById(\"linetotalprice" + count + "\");\n" );
        poout.println( "linetotalprice.value = \"" + total.setScale(4,4) + "\";\n" );

        poout.println( "extpricecell = opener.document.getElementById(\"extpricecell" + count + "\");\n" );
        poout.println( "extpricecell.innerHTML = \"" + total.setScale(4,4) + "  "+currencyid+" \";\n" );

        poout.println( "qtyreceivedcell = opener.document.getElementById(\"qtyreceivedcell" + count + "\");\n" );
        poout.println( "qtyreceivedcell.innerHTML = \"" + qtyreceived + "\";\n" );

        poout.println( "qtyvouchered = opener.document.getElementById(\"qtyvouchered" + count + "\");\n" );
        poout.println( "qtyvouchered.innerHTML = \"" + qtyvouchered + "\";\n" );

        poout.println( "qtyreturned = opener.document.getElementById(\"qtyreturned" + count + "\");\n" );
        poout.println( "qtyreturned.innerHTML = \"" + qtyreturned + "\";\n" );

        poout.println( "detaillinenumber = opener.document.getElementById(\"detaillinenumber" + count + "\");\n" );
        poout.println( "detaillinenumber.value = \"" + rowNumer + "\";\n" );

        poout.println( "mpn = opener.document.getElementById(\"mpn" + count + "\");\n" );
        poout.println( "mpn.value = \"" + mfg_part_no + "\";\n" );

        poout.println( "supplierpn = opener.document.getElementById(\"supplierpn" + count + "\");\n" );
        poout.println( "supplierpn.value = \"" + supplier_part_no + "\";\n" );              
        if ( isDataTransferStatusClosed )
        {
          poout.println( "supplierpn.className = \"INVISIBLEHEADBLUE\";\n" );
          poout.println( "supplierpn.readOnly = true;\n" );
        }

        poout.println( "dpas = opener.document.getElementById(\"dpas" + count + "\");\n" );
        poout.println( "dpas.value = \"" + dpas_rating + "\";\n" );
        if ( isDataTransferStatusClosed )
        {
            poout.println( "dpas.className = \"INVISIBLEHEADBLUE\";\n" );
            poout.println( "dpas.readOnly = true;\n" );
        }

        poout.println( "supplierqty = opener.document.getElementById(\"supplierqty" + count + "\");\n" );
        poout.println( "supplierqty.value = \"" + supplier_qty + "\";\n" );

        poout.println( "supplierpkg = opener.document.getElementById(\"supplierpkg" + count + "\");\n" );
        poout.println( "supplierpkg.value = \"" + supplier_pkg + "\";\n" );

        poout.println( "supplierunitprice = opener.document.getElementById(\"supplierunitprice" + count + "\");\n" );
        poout.println( "supplierunitprice.value = \"" + supplier_unit_price + "\";\n" );

        poout.println( "shelflife = opener.document.getElementById(\"shelflife" + count + "\");\n" );
        poout.println( "shelflife.value = \"" + shelflife + "\";\n" );
        if ( isDataTransferStatusClosed )
        {
        	poout.println( "shelflife.className = \"INVISIBLEHEADBLUE\";\n" );
            poout.println( "shelflife.readOnly = true;\n" );
        }
        
        poout.println( "slHidden = opener.document.getElementById(\"slHidden" + count + "\");\n" );
        poout.println( "try{slHidden.value = \"" + shelflife + "\";}catch(ex){}\n" );
        
        poout.println( "shelflifebasis = opener.document.getElementById(\"shelflifebasis" + count + "\");\n" );
        /*if (shelfLifeDays.equalsIgnoreCase("-1"))
        {
         poout.println( "shelflifebasis.innerHTML = \"Basis: Indefinite\";\n" );
        }
        else*/ if (shelfLifeDays.length() >0)
        {
          if (shelfLifeBasis.equalsIgnoreCase("M"))
          {
            poout.println( "shelflifebasis.innerHTML = \" " + shelfLifeDays + " Basis: Days from DOM\";\n" );
          }
          else if (shelfLifeBasis.equalsIgnoreCase("S"))
          {
            poout.println( "shelflifebasis.innerHTML = \" " + shelfLifeDays + " Basis: Days from DOS\";\n" );
          }
          else if (shelfLifeBasis.equalsIgnoreCase("R"))
          {
            poout.println( "shelflifebasis.innerHTML = \" " + shelfLifeDays + " Basis: Days from DOR\";\n" );
          }
        }

        poout.println( "itemdescdivrow = opener.document.getElementById(\"itemdescdivrow" + count + "" + count + "\");\n" );
        poout.println( "itemdescdivrow.innerHTML = \"" + item_desc + "\";\n" );

		    poout.println( "lastconfirmedcell = opener.document.getElementById(\"lastconfirmedcelldivrow" + count + "" + count + "\");\n" );
        poout.println( "lastconfirmedcell.innerHTML = \"" + lastConfirmed + "\";\n" );

        poout.println( "linenotesdivrow = opener.document.getElementById(\"linenotesdivrow" + count + "" + count + "\");\n" );
        poout.println( "linenotesdivrow.value = \"" + po_line_note + "\";\n" );
        if ( isDataTransferStatusClosed )        	
            poout.println( "linenotesdivrow.readOnly = true;\n" );


        poout.println( "deliverylinenotes = opener.document.getElementById(\"deliverylinenotesdivrow" + count + "" + count + "\");\n" );
        poout.println( "deliverylinenotes.value = \"" + deliverylinenotes + "\";\n" );       
        if ( isDataTransferStatusClosed )      	
            poout.println( "deliverylinenotes.readOnly = true;\n" );              

        BigDecimal qtyonline= new BigDecimal("0");
        BigDecimal qtyintreceieved= new BigDecimal("0");

        if ( quantityOrdered.trim().length() > 0 )
        {
          qtyonline= new BigDecimal( quantityOrdered );
        }
        if ( qtyreceived.trim().length() > 0 )
        {
          qtyintreceieved= new BigDecimal( qtyreceived );
        }

        poout.println( "qtyreceived = opener.document.getElementById(\"qtyreceived" + count + "\");\n" );
        poout.println( "qtyreceived.value = \"" + qtyreceived + "\";\n" );
        
        if (isDataTransferStatusClosed) {
        	// disable buttons
            poout.println( "getcjbjkb = opener.document.getElementById(\"getcjbjkb" + count + "\");\n" );
        	poout.println( "if (getcjbjkb != null) {" );
            poout.println( "getcjbjkb.disabled=true;\n" );
            poout.println( "getcjbjkb.style.display=\"none\";\n" );
            poout.println( "}" );
            
            poout.println( "additemnotes = opener.document.getElementById(\"additemnotes" + count + "\");\n" );
        	poout.println( "if (additemnotes != null) {" );
            poout.println( "additemnotes.disabled=true;\n" );
            poout.println( "additemnotes.style.display=\"none\";\n" );
            poout.println( "}" );
            
            poout.println( "editassociatePr = opener.document.getElementById(\"editassociatePr00" + count + "\");\n" );
        	poout.println( "if (editassociatePr != null) {" );
            poout.println( "editassociatePr.disabled=true;\n" );
            poout.println( "editassociatePr.style.display=\"none\";\n" );
            poout.println( "}" );
        }

        specflowd=new Hashtable();

        poout.println( "linestatustext = opener.document.getElementById(\"linestatustext" + count + "\");\n" );

        if ( isitClosed ) 
        {
          poout.println( "linestatustext.innerHTML = \"Canceled\";\n" );
          specflowd.put( "SHOWSTUFF","No" );

          poout.println( "editassociatePr = opener.document.getElementById(\"editassociatePr00" + count + "\");\n" );
          poout.println( "editassociatePr.disabled=true;\n" );
          poout.println( "editassociatePr.style.display=\"none\";\n" );        
        }
        else if ( ( qtyintreceieved.compareTo(qtyonline) == 0 || qtyintreceieved.compareTo(qtyonline) == 1 ) && "Y".equalsIgnoreCase( everconfirmed ) && dateconfirmed.trim().length() > 0 )
        {
          poout.println( "linestatustext.innerHTML = \"Closed\";\n" );
          specflowd.put( "SHOWSTUFF","No" );
        }
        else
        {
          poout.println( "linestatustext.innerHTML = \"" + polinestatus + "\";\n" );
          specflowd.put( "SHOWSTUFF","Yes" );
					if ("Y".equalsIgnoreCase( everconfirmed ))
					{
					poout.println( "expediteNotes = opener.document.getElementById(\"expediteNotes" + count + "\");\n" );
					poout.println( "expediteNotes.style.display=\"\";\n" );

					poout.println( "try {\npoExpediteNotesButton = opener.document.getElementById(\"poExpediteNotesButton\");\n" );
					poout.println( "poExpediteNotesButton.style.display=\"\";\n}\ncatch (ex)\n{}\n" );
                    }
					if(buyOrderStatus.equalsIgnoreCase("ProblemWBuy"))//TCMISDEV-778 button resend feature to supplier for POs in status ProblemWbuy
					{
						poout.println( "try {\nconfirm= opener.document.getElementById(\"confirm\");\n" );
						poout.println( "confirm.style.display=\"none\";\n}\ncatch (ex)\n{}\n" );
						
						poout.println( "try {\nresendwbuypobutton = opener.document.getElementById(\"resendwbuypobutton\");\n" );
						poout.println( "resendwbuypobutton.style.display=\"\";\n}\ncatch (ex)\n{}\n" );
					}
					if(buyOrderStatus.equalsIgnoreCase("ProblemDBuy"))
					{
						poout.println( "try {\n confirm= opener.document.getElementById(\"confirm\");\n" );
						poout.println( "confirm.style.display=\"none\";\n}\ncatch (ex)\n{}\n" );
						
						poout.println( "try {\n resenddbuypobutton = opener.document.getElementById(\"resenddbuypobutton\");\n" );
						poout.println( "resenddbuypobutton.style.display=\"\";\n}\ncatch (ex)\n{}\n" );
					}
        }

        poout.println( "linestatus = opener.document.getElementById(\"linestatus" + count + "\");\n" );
        poout.println( "linestatus.value = \"" + polinestatus + "\";\n" );

        poout.println( "linechange = opener.document.getElementById(\"linechange" + count + "\");\n" );
        poout.println( "linechange.value = \"No\";\n" );

        poout.println( "originallinestatus = opener.document.getElementById(\"originallinestatus" + count + "\");\n" );
        poout.println( "originallinestatus.value = \"" + polinestatus + "\";\n" );

        poout.println( "rowlinenumber = opener.document.getElementById(\"row" + count + "linenumber\");\n" );
        poout.println( "rowlinenumber.value = \"" + rowNumer + "\";\n" );

        poout.println( "rowrow = opener.document.getElementById(\"row" + count + "row\");\n" );
        poout.println( "rowrow.value = \"" + count + "\";\n" );

        poout.println( "ammendment = opener.document.getElementById(\"ammendment" + count + "\");\n" );
        poout.println( "ammendment.value = \"" + amendment + "\";\n" );
        
        poout.println( "dataTransferStatusClosed = opener.document.getElementById(\"dataTransferStatusClosed" + count + "\");\n" );
        poout.println( "dataTransferStatusClosed.value = \"" + (isDataTransferStatusClosed ? "Y" : "N") + "\";\n" );

        poout.println( "var prshipto  =  opener.document.getElementById(\"prshipto" + count + "\");\n" );
        poout.println( "prshipto.value = \"" + prshipto + "\";\n" );

        specflowd.put( "LINE","" + count + "" );
        specflowd.put( "ITEMID","" + item_id + "" );
        specflowd.put( "AMMENDMEANT","" + amendment + "" );
        specflowd.put( "PRSHIPTO","" + prshipto + "" );
        specflowd.put( "EVERCONFIRMED","" + everconfirmed + "" );
        specflowd.put( "DATATRANSFERSTATUSCLOSED", isDataTransferStatusClosed ? "Y" : "N");

        specFlowv.addElement( specflowd );
      }
      else //if ("MA".equalsIgnoreCase(item_type))
      {
        addCharges=new Hashtable();

        addCharges.put( "PO_LINE","" + po_line + "" );
        addCharges.put( "LINE","" + count + "" );
        addCharges.put( "AMENDMENT","" + amendment + "" );
        addCharges.put( "ITEMDESC","" + item_desc + "" );
        addCharges.put( "LASTCONFIRMED","" + lastConfirmed + "" );
        addCharges.put( "DATATRANSFERSTATUSCLOSED", isDataTransferStatusClosed ? "Y" : "N");
        addChargev.addElement( addCharges );

        poout.println( "opener.addLineCharge(); \n" );

        poout.println( "everConfirmed = opener.document.getElementById(\"everConfirmed" + count + "\");\n" );
        poout.println( "everConfirmed.value = \"" + everconfirmed + "\";\n" );

        poout.println( "linenumberammn = opener.document.getElementById(\"linenumberammn" + count + "\");\n" );
        poout.println( "linenumberammn.innerHTML = \"" + rowNumer + "/" + amendment + "\";\n" );

        if ( item_id.trim().length() > 0 )
        {
          poout.println( "validitem = opener.document.getElementById(\"validitem" + count + "\");\n" );
          poout.println( "validitem.value = \"Yes\";\n" );

          poout.println( "lineitemid = opener.document.getElementById(\"lineitemid" + count + "\");\n" );
          poout.println( "lineitemid.className = \"" + itemColor + "\";\n" );
          poout.println( "lineitemid.value = \"" + item_id + "\";\n" );
        }
        else
        {
          poout.println( "validitem = opener.document.getElementById(\"validitem" + count + "\");\n" );
          poout.println( "validitem.value = \"No\";\n" );

          poout.println( "lineitemid = opener.document.getElementById(\"lineitemid" + count + "\");\n" );
          poout.println( "lineitemid.className = \"INVALIDTEXT\";\n" );
          poout.println( "lineitemid.value = \"\";\n" );
        }

        //TO DO chANGE THIS CONDItion to the line being confirmed atleast once
        if ( isDataTransferStatusClosed || ( amendmentNumber > 0 ) || ( "Y".equalsIgnoreCase( everconfirmed ) ) && ( ( item_id.trim().length() > 0 ) || "0".equalsIgnoreCase( quantityOrdered ) ) )
        {
          poout.println( "lineitemid.readOnly = true;\n" );
          poout.println( "buttonlineitemid = opener.document.getElementById(\"buttonlineitemid" + count + "\");\n" );
          poout.println( "buttonlineitemid.disabled=true;\n" );
          poout.println( "buttonlineitemid.style.display=\"none\";\n" );
        }
        
        poout.println( "ghscompliantcell = opener.document.getElementById(\"ghscompliantcell" + count + "\");\n" );
        poout.println( "ghscompliantcell.innerHTML = \"" + ghs_compliant + "\";\n" );

        poout.println( "itemtype = opener.document.getElementById(\"itemtype" + count + "\");\n" );
        poout.println( "itemtype.value = \"" + item_type + "\";\n" );

        poout.println( "itemtypecell = opener.document.getElementById(\"itemtypecell" + count + "\");\n" );
        poout.println( "itemtypecell.innerHTML = \"" + item_type + "\";\n" );

        poout.println( "pakgingcell = opener.document.getElementById(\"pakgingcell" + count + "\");\n" );
        poout.println( "pakgingcell.innerHTML = \"\";\n" );
        
        poout.println( "materialId = opener.document.getElementById(\"materialId" + count + "\");\n" );
        poout.println( "materialId.innerHTML = \"" + StringHandler.emptyIfNull(msdsData.get(item_id)) + "\";\n" );

        poout.println( "lineqty = opener.document.getElementById(\"lineqty" + count + "\");\n" );
        poout.println( "lineqty.value = \"" + quantityOrdered + "\";\n" );
        if ( isitClosed || isDataTransferStatusClosed )
        {
          poout.println( "lineqty.className = \"" + itemColor + "\";\n" );
          poout.println( "lineqty.readOnly = true;\n" );
        }

        //archived
        poout.println( "lineArchived = opener.document.getElementById(\"lineArchived" + count + "\");\n" );
        poout.println( "lineArchived.value = \"" + lineArchived + "\";\n" );

        poout.println( "lineunitprice = opener.document.getElementById(\"lineunitprice" + count + "\");\n" );
        poout.println( "lineunitprice.value = \"" + unit_price + "\";\n" );
        if ( isitClosed || isDataTransferStatusClosed )
        {
          poout.println( "lineunitprice.className = \"" + itemColor + "\";\n" );
          poout.println( "lineunitprice.readOnly = true;\n" );
        }

        BigDecimal qtyVouchered= new BigDecimal("0");
        if ( qtyvouchered.trim().length() > 0 )
        {
          qtyVouchered= new BigDecimal( qtyvouchered );
        }
        if ( qtyVouchered.intValue() > 0 || isDataTransferStatusClosed )
        {
          poout.println( "lineunitprice.className = \"" + itemColor + "\";\n" );
          poout.println( "lineunitprice.readOnly = true;\n" );
        }

        BigDecimal total= new BigDecimal("0");
         BigDecimal qtyF= new BigDecimal("0");
        if ( quantityOrdered.length() > 0 && unit_price.length() > 0 )
        {
          qtyF= new BigDecimal( quantityOrdered );
          BigDecimal unitPrice= new BigDecimal( unit_price );
          total= qtyF.multiply(unitPrice);
          if(isOneUSD || "USA".equalsIgnoreCase(countryAbbrev) || "USD".equalsIgnoreCase(currencyid))          
        	isOneUSD = true;
          else
        	  orderTotal = orderTotal.add(total);  
          orderTotalColl.add(new String[]{unit_price,quantityOrdered,currencyid});
       }
                  
        poout.println( "extpricecell = opener.document.getElementById(\"extpricecell" + count + "\");\n" );
        poout.println( "extpricecell.innerHTML = \"" + total.setScale(4,4) + " "+currencyid+" \";\n" );

        poout.println( "qtyreceivedcell = opener.document.getElementById(\"qtyreceivedcell" + count + "\");\n" );
        poout.println( "qtyreceivedcell.innerHTML = \"" + qtyreceived + "\";\n" );

        BigDecimal qtyintreceieved= new BigDecimal("0");
        if ( qtyreceived.length() > 0 )
        {
          qtyintreceieved= new BigDecimal( qtyreceived );
        }

        poout.println( "qtyreceived = opener.document.getElementById(\"qtyreceived" + count + "\");\n" );
        poout.println( "qtyreceived.value = \"" + qtyreceived + "\";\n" );

        poout.println( "qtyvouchered = opener.document.getElementById(\"qtyvouchered" + count + "\");\n" );
        poout.println( "qtyvouchered.innerHTML = \"" + qtyvouchered + "\";\n" );

        poout.println( "linestatustext = opener.document.getElementById(\"linestatustext" + count + "\");\n" );
        if ( isitClosed )
        {
          poout.println( "linestatustext.innerHTML = \"Canceled\";\n" );
        }
        else if ( ( qtyintreceieved.compareTo(qtyF) == 0 || qtyintreceieved.compareTo(qtyF) == 1 ) && "Y".equalsIgnoreCase( everconfirmed ) && dateconfirmed.trim().length() > 0 )
        {
          poout.println( "linestatustext.innerHTML = \"Closed\";\n" );
        }
        else
        {
          poout.println( "linestatustext.innerHTML = \"" + polinestatus + "\";\n" );
        }

        poout.println( "linestatus = opener.document.getElementById(\"linestatus" + count + "\");\n" );
        poout.println( "linestatus.value = \"" + polinestatus + "\";\n" );

        poout.println( "linechange = opener.document.getElementById(\"linechange" + count + "\");\n" );
        poout.println( "linechange.value = \"No\";\n" );

        poout.println( "originallinestatus = opener.document.getElementById(\"originallinestatus" + count + "\");\n" );
        poout.println( "originallinestatus.value = \"" + polinestatus + "\";\n" );

        poout.println( "rowlinenumber = opener.document.getElementById(\"row" + count + "linenumber\");\n" );
        poout.println( "rowlinenumber.value = \"" + rowNumer + "\";\n" );

        poout.println( "rowrow = opener.document.getElementById(\"row" + count + "row\");\n" );
        poout.println( "rowrow.value = \"" + count + "\";\n" );

        poout.println( "ammendment = opener.document.getElementById(\"ammendment" + count + "\");\n" );
        poout.println( "ammendment.value = \"" + amendment + "\";\n" );

		if ( "Y".equalsIgnoreCase(differentSupplierOnLine) || ("Y".equalsIgnoreCase(secondarySupplierAllowed) && !"Y".equalsIgnoreCase( everconfirmed )))
		{
		poout.println( "secondarysuppliercell = opener.document.getElementById(\"secondarysuppliercelldivrow" + count + "" + count + "\");\n" );
		poout.println( "var innHtmlline =  \"<input type=\\\"text\\\" size=\\\"5\\\" CLASS=\\\"HEADER\\\" onChange=\\\"invalidateSecondarySupplier('"+count+"')\\\" name='secondarysupplier"+count+"' id='secondarysupplier"+count+"'>\";\n" );
		poout.println( "innHtmlline =  innHtmlline +\"<BUTTON id='secondarysupplierbutton" + count + "' name='secondarysupplierbutton" + count + "' OnClick=getSecondarySupplier('"+count+"') type=button><IMG src=\\\"/images/search_small.gif\\\" alt=\\\"Item ID\\\"></BUTTON>\";\n" );
		poout.println( "innHtmlline =  innHtmlline +\""+secondarySupplierAddress+"\";\n" );
		poout.println( "secondarysuppliercell.innerHTML = innHtmlline;\n" );

		  poout.println( "secondarysupplier = opener.document.getElementById(\"secondarysupplier" + count + "\");\n" );
		  poout.println( "secondarysupplier.value = \"" + supplier + "\";\n" );
		  if ( "N".equalsIgnoreCase(changeSupplier) || !"Y".equalsIgnoreCase(secondarySupplierAllowed))
		  {
			poout.println( "secondarysupplier.readOnly = true;\n" );
			poout.println( "secondarysupplier.className = \"INVISIBLEHEADBLUE\";\n" );
			poout.println( "secondarysupplierbutton = opener.document.getElementById(\"secondarysupplierbutton" + count + "\");\n" );
			poout.println( "secondarysupplierbutton.disabled=true;\n" );
			poout.println( "secondarysupplierbutton.style.display=\"none\";\n" );
		  }
		}
		
		/*if ( isDataTransferStatusClosed )
        {
            poout.println( "addchargeselect = opener.document.getElementById(\"selectdivrow" + count + count + "0\");\n" );
            poout.println( "addchargeselect.innerHTML = \"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\\n" );
        }*/
      }
    }
  }
  catch ( Exception e )
  {
    e.printStackTrace();
  }
  finally
  {
    if ( lineDetaildbrs != null )
    {
      lineDetaildbrs.close();
    }
  }
  
  if(isOneUSD)
	  recalTotWithConversion(radianpo);  

  poout.println( "totallines = opener.document.getElementById(\"totallines\");\n" );
  poout.println( "totallines.value = \"" + count + "\";\n" );

  poout.println( "totalcost = opener.document.getElementById(\"totalcost\");\n" );
  poout.println( "totalcost.value = \"" + orderTotal.setScale(2,4) + ("USA".equalsIgnoreCase(countryAbbrev) ?" USD":"") + "\";\n" );

  if ( count > 0 )
  {
    poout.println( "selectedRow1 = opener.document.getElementById(\"row1\");\n" );
    poout.println( "selectedRow1.style.backgroundColor = \"#8a8aff\";\n" );

    poout.println( "var target1 = opener.document.getElementById(\"divrow1\");\n" );
    poout.println( "target1.style.display = \"block\";\n" );
  }

  for ( int chargeline=0; chargeline < addChargev.size(); chargeline++ )
  {
    Hashtable addchargesData=new Hashtable();
    addchargesData= ( Hashtable ) addChargev.elementAt( chargeline );

    String addchargeLine=addchargesData.get( "PO_LINE" ).toString().trim();
    String linsinJs=addchargesData.get( "LINE" ).toString().trim();
    String ammenNumber=addchargesData.get( "AMENDMENT" ).toString().trim();
    String itemdesc=addchargesData.get( "ITEMDESC" ).toString().trim();
	String lastConfirmed=addchargesData.get( "LASTCONFIRMED" ).toString().trim();
	boolean dataTransferStatusClosed=addchargesData.get( "DATATRANSFERSTATUSCLOSED" ).toString().trim().equals("Y") ? true : false;

    //int rownum1=Integer.parseInt( addchargeLine );
    //rownum1= ( rownum1 / 1000 );
    BigDecimal rowNumer= new BigDecimal(addchargeLine);
    rowNumer = rowNumer.divide(new BigDecimal("1000"));
    rowNumer.setScale(1,3);

    poout.println( "secondarysuppliercell = opener.document.getElementById(\"secondarysuppliercelldivrow" + rowNumer + "" + rowNumer + "\");\n" );
    poout.println( "var secondarysuppliercellset = secondarysuppliercell.innerHTML;\n" );
    poout.println( "opener.refreshlinecharges(" + linsinJs + ",'No'); \n" );
    poout.println( "itemdescdivrow = opener.document.getElementById(\"itemdescdivrow" + rowNumer + "" + rowNumer + "\");\n" );
    poout.println( "itemdescdivrow.innerHTML = \"" + itemdesc + "\";\n" );

	poout.println( "lastconfirmedcell = opener.document.getElementById(\"lastconfirmedcelldivrow" + rowNumer + "" + rowNumer + "\");\n" );
	poout.println( "lastconfirmedcell.innerHTML = \"" + lastConfirmed + "\";\n" );

    int addchargecount=0;
    try
    {
      dbrs=db.doQuery( "Select PO_LINE from po_add_charge_allocation_all where RADIAN_PO = " + radianpo + " and PO_ADD_CHARGE_LINE = " + addchargeLine + " and AMENDMENT =" + ammenNumber + " " );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        addchargecount++;
        String po_line=rs.getString( "PO_LINE" ) == null ? "" : rs.getString( "PO_LINE" ).trim();

        //int addChgrownum1=Integer.parseInt( po_line );
        //addChgrownum1= ( addChgrownum1 / 1000 );
        BigDecimal addChargerowNumer= new BigDecimal(po_line);
        addChargerowNumer = addChargerowNumer.divide(new BigDecimal("1000"));
        addChargerowNumer.setScale(1,3);
        String addChgrownum="" + addChargerowNumer + "";

        poout.println( "chargeAncnumber = opener.document.getElementById(\"chargeAncnumber" + linsinJs + "" + addChgrownum + "\");\n" );
        poout.println( "chargeAncnumber.value = \"Yes\";\n" );
      }
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
    }
    finally
    {
      if ( dbrs != null )
      {
        dbrs.close();
      }
    }

	poout.println( "secondarysuppliercell = opener.document.getElementById(\"secondarysuppliercelldivrow" + rowNumer + "" + rowNumer + "\");\n" );
    poout.println( "secondarysuppliercell.innerHTML = secondarysuppliercellset;\n" );
    poout.println( "opener.checkAddChargeLines(" + linsinJs + ", \"" + (dataTransferStatusClosed ? "Y":"N") + "\"); \n" );
  }

  Vector associatePrData =new Vector();
  associatePrData =  getAssociatedPRsData( radianpo,invengrp,opsEntityId,branchPlant);
  //Vector poSpecData =new Vector();
  //poSpecData = getSpecData(db,radianpo);

  for ( int specflowLine=0; specflowLine < specFlowv.size(); specflowLine++ )
  {
    Hashtable specflowLineData=new Hashtable();
    specflowLineData= ( Hashtable ) specFlowv.elementAt( specflowLine );

    String lineItemid=specflowLineData.get( "ITEMID" ).toString().trim();
    String linsinJs=specflowLineData.get( "LINE" ).toString().trim();
    String ammenNumber=specflowLineData.get( "AMMENDMEANT" ).toString().trim();
    String prShipto=specflowLineData.get( "PRSHIPTO" ).toString().trim();
    String showStuff=specflowLineData.get( "SHOWSTUFF" ).toString().trim();
    boolean dataTransferStatusClosed=specflowLineData.get( "DATATRANSFERSTATUSCLOSED" ).toString().trim().equals("Y") ? true : false;

    if ( prShipto.trim().length() > 0 )
    {
      ShipTo=prShipto;
    }

    if ( ( lineItemid.length() > 0 ) && !lineItemid.equalsIgnoreCase( "0" ) )
    {
      /*TODO - Doing this because PO 965822 with 43 lines is having trouble. This can be fixed with some time.*/
      //if (!(count > 10) )
      {
          radian.web.poHelpObj.buildsendSpecs( db,"yes",linsinJs,lineItemid,radianpo,"",ShipTo,ammenNumber,ship_to_company_id,invengrp,opsEntityId, poout,this.getSpecOverRide(),res, dataTransferStatusClosed);
          //radian.web.poHelpObj.buildsendSpecs( db,"yes",linsinJs,lineItemid,radianpo,"",ShipTo,ammenNumber,ship_to_company_id,invengrp,poout,this.getSpecOverRide(),poSpecData,res);
          poout.println( "validspec = opener.document.getElementById(\"validspec" + linsinJs + "\");\n" );
    	  poout.println( "validspec.value = \"Yes\";\n" );
    	  radian.web.poHelpObj.buildsendFlowdowns( db,"yes",linsinJs,lineItemid,radianpo,"",ShipTo,ammenNumber,ship_to_company_id,poout,res, dataTransferStatusClosed);
    	  poout.println( "validflowdown = opener.document.getElementById(\"validflowdown" + linsinJs + "\");\n" );
    	  poout.println( "validflowdown.value = \"Yes\";\n" );
      }

      if ( "Yes".equalsIgnoreCase( showStuff ) && ! ( count > 10 ) )
      {
        //radian.web.poHelpObj.buildsenditemNotes( db,linsinJs,lineItemid,"yes",poout,res);
		//radian.web.poHelpObj.buildsendpartNotes( db,linsinJs,lineItemid,invengrp,"yes",poout,res);
        radian.web.poHelpObj.buildshowTcmBuys( db,linsinJs,lineItemid,"yes",ShipTo,opsEntityId,poout,res);

        boolean canupdate=false;
        try
        {
          canupdate=this.getupdateStatus();
        }
        catch ( Exception ex )
        {
          canupdate=false;
        }
        //radian.web.poHelpObj.buildsendLookahead( db,"yes",linsinJs,lineItemid,radianpo,canupdate,poout,res);
      }

      //buildAssociatedPRS( linsinJs,radianpo,lineItemid,poout,res);
      buildAssociatedPRS( linsinJs,radianpo,lineItemid,invengrp,opsEntityId,associatePrData,poout,res);
    }
       
    if("Y".equalsIgnoreCase(requiresFinancialApproval))
    {
    	poout.println( "allowPOFinancialConfirmaiton = opener.document.getElementById(\"allowPOFinancialConfirmaiton\");\n" );
    	poout.println( "if(typeof(allowPOFinancialConfirmaiton) != 'undefined' && allowPOFinancialConfirmaiton != null  && (allowPOFinancialConfirmaiton.value == 'Y' || allowPOFinancialConfirmaiton.value == 'y' ))");
    	poout.println( "{autoPOConfirm = opener.document.getElementById(\"confirm\");\n" );
    	poout.println( "if(typeof(autoPOConfirm) != 'undefined' && autoPOConfirm != null)\n" );
    	poout.println( "autoPOConfirm.style.display='none';\n" );
    	poout.println( "autoPOFA = opener.document.getElementById(\"financialApproval\");\n" );
    	poout.println( "if(typeof(autoPOFA) != 'undefined' && autoPOFA != null)\n" );
    	poout.println( "autoPOFA.style.display='';}\n" );
    }
    else if (orderTotal.compareTo(new BigDecimal(15000)) == 1 )
    {
    	poout.println( "allowPOFinancialConfirmaiton = opener.document.getElementById(\"allowPOFinancialConfirmaiton\");\n" );
    	poout.println( "financialAuditHideConfirm = opener.document.getElementById(\"confirm\");\n" );
    	poout.println( "if(typeof(allowPOFinancialConfirmaiton) != 'undefined' && allowPOFinancialConfirmaiton != null && typeof(financialAuditHideConfirm) != 'undefined' && financialAuditHideConfirm != null && allowPOFinancialConfirmaiton.value != 'Y'){\n" );
    	poout.println( "financialAuditHideConfirm.style.display='none';}\n" );
    }
  	  
  }

  return;
}

private void buildAssociatedPRS( String lineID,String radianPO,String inputItemId,String inventoryGroup,String opsEntityId,Vector data,PrintWriter poout, ResourceLibrary res )
{
  //StringBuffer Msgtemp=new StringBuffer();
  DBResultSet dbrs = null;
  ResultSet rs = null;
  Connection connect1=null;
  CallableStatement cs=null;
  int count=0;
  int totalitemQty=0;

  Hashtable summary = new Hashtable();
  summary = (Hashtable) data.elementAt(0);
  int total = ((Integer) (summary.get("TOTAL_NUMBER"))).intValue();

  poout.println( "{\n" );
  try
  {
    poout.println( "var associatedprcell = opener.document.getElementById(\"row5detaildivrow" + lineID + "" + lineID + "\");\n" );
    poout.println( "var insidehtml =\"<TABLE ID=\\\"associatedprtable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
    poout.println( "insidehtml +=\"<TR>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.prnumber")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.mrline")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.partnumber")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.specs")+"</TH>\";\n" );      
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.type")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.lpp")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\">"+res.getString("label.notes")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" HEIGHT=\\\"25\\\">"+res.getString("label.purchasingnote")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" HEIGHT=\\\"25\\\">"+res.getString("label.buyordernotes")+"</TH>\";\n" );
    //poout.println("insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">% SL</TH>\";\n");
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.reuqestphemail")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.csr")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"3%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.mr.qty")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"3%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.buyquantity")+"</TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"3%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.uom")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.needed")+"<br>("+res.getString("label.dateformat")+")</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.shipto")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.facility")+"</TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.deliverypoint")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.hub")+"<BR>("+res.getString("label.invengroup")+")</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.clientpo")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.mm")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.reorderpoint")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.inventory")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\"  HEIGHT=\\\"25\\\">"+res.getString("label.openpoquantity")+"</TH>\";\n" );
    poout.println( "insidehtml +=\"</TR>\";\n" );

    /*//String associatedprQuesry = "Select a.*, to_char(sysdate,'dd Mon yyyy hh24:mi') PICK_DATE, to_char(a.DATE_ISSUED,'mm/dd/yyyy') DATE_ISSUED1,to_char(a.NEED_DATE,'mm/dd/yyyy') NEED_DATE1 ";
    String associatedprQuesry="Select a.SPEC_LIST,a.PURCHASING_NOTE,DELIVERY_POINT_DESC SHIP_TO_DELIVERY_POINT,INVENTORY_GROUP,STOCKED,to_char(sysdate,'dd Mon yyyy hh24:mi') PICK_DATE, to_char(a.DATE_ISSUED,'mm/dd/yyyy hh24:mi') DATE_ISSUED1,to_char(a.NEED_DATE,'mm/dd/yyyy') NEED_DATE1, ";
    associatedprQuesry+="a.CATALOG_ID,a.REQUEST_ID,a.ENGINEERING_EVALUATION,a.BUYER_NAME,a.MR_NUMBER,a.RAYTHEON_PO,a.SIZE_UNIT,a.COMMENTS,a.BUYER,a.RADIAN_PO,a.MFG_ID,a.STATUS,a.DATE_ASSIGNED, ";
    associatedprQuesry+="a.PART_ID,a.SHELF_LIFE_DAYS,a.OPEN_PO_QUANTITY,a.REQUEST_LINE_STATUS,a.PR_NUMBER,a.FROZEN,a.AVAILABLE_QUANTITY,a.PHONE,a.CRITICAL,a.MFG_PART_NO, ";
    associatedprQuesry+="a.PRIORITY,a.DATE_CHANGED,a.REQUESTOR_LAST_NAME,a.BASELINE_PRICE,a.STOCKING_LEVEL,a.NOTES,a.BUYER_ID,a.REORDER_POINT, ";
    associatedprQuesry+="a.MR_QUANTITY,a.DATE_PO_CREATED,a.UOM,a.EMAIL,a.FACILITY,a.CATALOG_PRICE,a.DELIVERY_TYPE,a.ITEM_DESC,a.ITEM_ID, ";
    associatedprQuesry+="a.CANCEL_STATUS,a.SHIP_TO_LOCATION_ID,a.ORDER_QUANTITY,a.MR_LINE_ITEM,a.REQUESTOR_FIRST_NAME,a.EVER_CONFIRMED, ";
    associatedprQuesry+="a.TRADE_NAME,a.LAST_SUPPLIER,a.ITEM_TYPE,a.COMPANY_ID,a.BRANCH_PLANT,a.LPP,a.CSR_NAME ";
    associatedprQuesry+=" from associate_pr_view a where a.radian_po = " + radianPO + " and a.item_id = " + ItemID + " ";

    connect1=db.getConnection();
    connect1.setAutoCommit(false);
    cs=connect1.prepareCall( "{call PKG_BUY_ORDER.p_buy_page_query(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
    cs.setString(1,"ALL");
    cs.setString(2,inventoryGroup);
    cs.setString(3,"ALL");
    cs.setString(4,"ALL");
    cs.setString(5,"ALL");
    cs.setString(6,"RADIAN_PO");
    cs.setString(7,"IS");
    cs.setString(8,radianPO);
    cs.setString(9,"All Statuses");
    cs.setString(10,"ALL");
    cs.setString(11,"");
    cs.setString(12,"NO");
    cs.setString(13,"NO");
    cs.setString(14,"NO");
    cs.setString(15,"NO");
    cs.registerOutParameter( 16,OracleTypes.VARCHAR );
    cs.setNull(17,OracleTypes.INTEGER);
    cs.setString(18,opsEntityId);
    cs.setString(19,ItemID);
    cs.execute();
    associatedprQuesry=cs.getObject( 16 ).toString();
     			
    dbrs=db.doQuery( associatedprQuesry );
    ResultSet assocprsrs=dbrs.getResultSet();
*/
    int i = 0; //used for detail lines
    //polog.info("Row number "+i+" Total  "+total+"  inputItemId  "+inputItemId);
    for (int loop = 0; loop < total; loop++)
    {
	  i++;

      Hashtable hD = new Hashtable();
      String resultItemId ="";
      hD = (Hashtable) data.elementAt(i);
      resultItemId = hD.get( "ITEM_ID")==null?"":hD.get( "ITEM_ID").toString();
      //polog.info("i "+i+" resultItemId "+resultItemId);
      if (inputItemId.equalsIgnoreCase(resultItemId))
      {
      String notes=HelpObjs.addescapesForJavascript( hD.get(  "NOTES" ) == null ? "" : hD.get(  "NOTES" ).toString() );
      //String catalog_price = hD.get( "CATALOG_PRICE")==null?"":hD.get( "CATALOG_PRICE").toString();
      //String lppprice = hD.get( "BASELINE_PRICE")==null?"":hD.get( "BASELINE_PRICE").toString();
      //01-17-03
      String lppprice=hD.get(  "LPP" ) == null ? "" : hD.get(  "LPP" ).toString();
      String requestor_first_name= HelpObjs.addescapesForJavascript(hD.get(  "REQUESTOR_FIRST_NAME" ) == null ? "" : hD.get(  "REQUESTOR_FIRST_NAME" ).toString());
      String requestor_last_name= HelpObjs.addescapesForJavascript(hD.get(  "REQUESTOR_LAST_NAME" ) == null ? "" : hD.get(  "REQUESTOR_LAST_NAME" ).toString());
      String reqemail=HelpObjs.addescapesForJavascript( hD.get(  "EMAIL" ) == null ? "" : hD.get(  "EMAIL" ).toString() );
      String reqphone=hD.get(  "PHONE" ) == null ? "" : hD.get(  "PHONE" ).toString();
      String prnumber1=hD.get(  "PR_NUMBER" ) == null ? "" : hD.get(  "PR_NUMBER" ).toString();
      //String buyer = hD.get( "BUYER")==null?"":hD.get( "BUYER").toString();
      //String date_assigned = hD.get( "DATE_ASSIGNED")==null?"":hD.get( "DATE_ASSIGNED").toString();
      String needdate=hD.get(  "NEED_DATE1" ) == null ? "" : hD.get(  "NEED_DATE1" ).toString();
      String partnumber=HelpObjs.addescapesForJavascript( hD.get(  "PART_ID" ) == null ? "" : hD.get(  "PART_ID" ).toString() );
      //String item_desc = hD.get( "ITEM_DESC")==null?"":hD.get( "ITEM_DESC").toString();
      //String shelf_life_days = hD.get( "SHELF_LIFE_DAYS")==null?"":hD.get( "SHELF_LIFE_DAYS").toString();
      //String trade_name = hD.get( "TRADE_NAME")==null?"":hD.get( "TRADE_NAME").toString();
      String packaging = hD.get( "SIZE_UNIT")==null?"":hD.get( "SIZE_UNIT").toString();
      //String mfg_id = hD.get( "MFG_ID")==null?"":hD.get( "MFG_ID").toString();
      //String mfg_part_no = hD.get( "MFG_PART_NO")==null?"":hD.get( "MFG_PART_NO").toString();
      String itemtype=hD.get(  "ITEM_TYPE" ) == null ? "" : hD.get(  "ITEM_TYPE" ).toString();
      String qty=hD.get(  "ORDER_QUANTITY" ) == null ? "" : hD.get(  "ORDER_QUANTITY" ).toString();
      //String uom = hD.get( "UOM")==null?"":hD.get( "UOM").toString();
      //String priority = hD.get( "PRIORITY")==null?"":hD.get( "PRIORITY").toString();
      //String radian_po = hD.get( "RADIAN_PO")==null?"":hD.get( "RADIAN_PO").toString();
      String facility=HelpObjs.addescapesForJavascript( hD.get(  "FACILITY" ) == null ? "" : hD.get(  "FACILITY" ).toString());
      String clientpo=StringEscapeUtils.escapeHtml((hD.get(  "RAYTHEON_PO" ) == null ? "" : hD.get(  "RAYTHEON_PO" ).toString()));
      String branchplant=hD.get(  "BRANCH_PLANT" ) == null ? "" : hD.get(  "BRANCH_PLANT" ).toString();
      //String date_issued = hD.get( "DATE_ISSUED")==null?"":hD.get( "DATE_ISSUED").toString();
      //String date_po_created = hD.get( "DATE_PO_CREATED")==null?"":hD.get( "DATE_PO_CREATED").toString();
      //String status = hD.get( "STATUS")==null?"":hD.get( "STATUS").toString();
      //String date_changed = hD.get( "DATE_CHANGED")==null?"":hD.get( "DATE_CHANGED").toString();
      String comments = HelpObjs.addescapesForJavascript(hD.get( "COMMENTS")==null?"":hD.get( "COMMENTS").toString());
      String company_id = hD.get( "COMPANY_ID")==null?"":hD.get( "COMPANY_ID").toString();
      String mrnumber=hD.get(  "MR_NUMBER" ) == null ? "" : hD.get(  "MR_NUMBER" ).toString();
      String lineitem1=hD.get(  "MR_LINE_ITEM" ) == null ? "" : hD.get(  "MR_LINE_ITEM" ).toString();
      String reorderpoint=hD.get(  "REORDER_POINT" ) == null ? "" : hD.get(  "REORDER_POINT" ).toString();
      String stockinglevel=hD.get(  "STOCKING_LEVEL" ) == null ? "" : hD.get(  "STOCKING_LEVEL" ).toString();
      String inventory=hD.get(  "AVAILABLE_QUANTITY" ) == null ? "" : hD.get(  "AVAILABLE_QUANTITY" ).toString();
      String open_po_quantity=hD.get(  "OPEN_PO_QUANTITY" ) == null ? "" : hD.get(  "OPEN_PO_QUANTITY" ).toString();
      String shiptoIdonpr=hD.get(  "SHIP_TO_LOCATION_ID" ) == null ? "" : hD.get(  "SHIP_TO_LOCATION_ID" ).toString();
      //String buyer_id = hD.get( "BUYER_ID")==null?"":hD.get( "BUYER_ID").toString();
      //String buyer_name = hD.get( "BUYER_NAME")==null?"":hD.get( "BUYER_NAME").toString();
      //String ever_confirmed = hD.get( "EVER_CONFIRMED")==null?"":hD.get( "EVER_CONFIRMED").toString();
      String deliverytype=hD.get(  "DELIVERY_TYPE" ) == null ? "" : hD.get(  "DELIVERY_TYPE" ).toString();
      String mrQuantity=hD.get(  "MR_QUANTITY" ) == null ? "" : hD.get(  "MR_QUANTITY" ).toString();
      //String engenval=hD.get(  "ENGINEERING_EVALUATION" ) == null ? "" : hD.get(  "ENGINEERING_EVALUATION" ).toString();
      String reqid=hD.get(  "REQUEST_ID" ) == null ? "" : hD.get(  "REQUEST_ID" ).toString();
      String catid=hD.get(  "CATALOG_ID" ) == null ? "" : hD.get(  "CATALOG_ID" ).toString();
      String fullname=requestor_last_name + " , " + requestor_first_name;
      String stocekd=hD.get(  "STOCKED" ) == null ? "" : hD.get(  "STOCKED" ).toString();
      String invengrp=hD.get(  "INVENTORY_GROUP" ) == null ? "" : hD.get(  "INVENTORY_GROUP" ).toString();
	  String deliverypoint=hD.get(  "SHIP_TO_DELIVERY_POINT" ) == null ? "" : hD.get(  "SHIP_TO_DELIVERY_POINT" ).toString();
      String remainingShelfLifePercent=hD.get("REMAINING_SHELF_LIFE_PERCENT") == null ? "" : hD.get("REMAINING_SHELF_LIFE_PERCENT").toString();                
      String purchasingNote=HelpObjs.addescapesForJavascript( hD.get(  "LINE_PURCHASING_NOTE" ) == null ? "" : hD.get(  "LINE_PURCHASING_NOTE" ).toString());
      String specList=HelpObjs.addescapesForJavascript( hD.get(  "SPEC_LIST" ) == null ? "" : hD.get(  "SPEC_LIST" ).toString());
      String csrName=HelpObjs.addescapesForJavascript( hD.get(  "CSR_NAME" ) == null ? "" : hD.get(  "CSR_NAME" ).toString());

      String Color=" ";
      if ( count % 2 == 0 )
      {
        Color="CLASS=\\\"Inwhite";
      }
      else
      {
        Color="CLASS=\\\"Inblue";
      }
      count++;

      if ( qty.length() > 0 )
      {
        totalitemQty+=Integer.parseInt( qty );
      }

      poout.println( "insidehtml +=\"<TR ALIGN=\\\"center\\\">\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + prnumber1 + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"><A HREF=\\\"javascript:showMrLineAllocationReport('" +company_id+ "','"+mrnumber+"','"+lineitem1+"','"+resultItemId+"','"+invengrp+"')\\\">" + mrnumber + "-" + lineitem1 + "</A></TD>\";\n" );
      if ( partnumber.length() > 0 )
      {
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"><A HREF=\\\"javascript:getsuggestedSupplier('" + partnumber + "','" + reqid + "','" + catid + "')\\\">" + partnumber + "</A>\";\n" );
      }
      else
      {
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + partnumber + "\";\n" );
      }

      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + specList + "\";\n" );

      if ( "Y".equalsIgnoreCase( stocekd ) )
      {
        poout.println( "insidehtml +=\"<BR>(Stocked)\";\n" );
      }
      poout.println( "insidehtml +=\"</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + itemtype + "</TD>\";\n" );
      //poout.println("insidehtml +=\"<TD "+Color+"\\\" WIDTH=\\\"5%\\\">"+lppprice+"</TD>\";\n");
      poout.println( "insidehtml +=\"<td " + Color + "\\\" width=\\\"5%\\\"><A HREF=\\\"javascript:showLPPranges('" +company_id+ "','"+mrnumber+"','"+lineitem1+"','"+resultItemId+"','"+invengrp+"')\\\">" + lppprice + "</A></td>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"15%\\\">" + notes + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"10%\\\">" + purchasingNote + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"10%\\\">" + comments + "</TD>\";\n" );
      //poout.println("insidehtml +=\"<TD "+Color+"\\\" WIDTH=\\\"5%\\\">"+shelflife+"</TD>\";\n");
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + fullname + "<BR>" + reqphone + "<BR>" + reqemail + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"3%\\\">" + csrName + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"3%\\\">" + mrQuantity + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"3%\\\">" + qty + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + HelpObjs.addescapesForJavascript(packaging) + "</TD>\";\n");
      if ( "Y".equalsIgnoreCase( deliverytype ) )
      {
        poout.println( "insidehtml +=\"<td " + Color + "\\\" width=\\\"5%\\\">" + needdate + "<BR><A HREF=\\\"javascript:showschedulde(\\\'" + mrnumber + "\\\',\\\'" + lineitem1 + "\\\')\\\">Schedule</A></td>\";\n" );
      }
      else
      {
        poout.println( "insidehtml +=\"<td " + Color + "\\\" width=\\\"5%\\\">" + needdate + "</td>\";\n" );
      }

      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + shiptoIdonpr + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + facility + "</TD>\";\n" );
	  poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + deliverypoint + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + branchplant + "<BR>(" + invengrp + ")</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + clientpo + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + stockinglevel + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + reorderpoint + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + inventory + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + open_po_quantity + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<input name=\\\"remainingShelfLifePercent\\\" id=\\\"remainingShelfLifePercent"+lineID+count+"\\\" type=\\\"hidden\\\" value=\\\""+remainingShelfLifePercent+"\\\"/>\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );  
      
      }
    }
    /*connect1.setAutoCommit(true);*/
  }
  catch ( Exception e )
  {
    e.printStackTrace();
  }
  finally
  {
    /*if ( cs != null )
    {
      try
      {
        cs.close();
      }
      catch ( SQLException se )
      {}
    }
    if ( dbrs != null ) {dbrs.close();}*/
  }

  if ( count == 0 )
  {
    poout.println( "insidehtml +=\"<TR>\";\n" );
    poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"24\\\">No Records Found</TD>\";\n" );
    poout.println( "insidehtml +=\"</TR>\";\n" );
  }

  poout.println( "associatedprcell.innerHTML =insidehtml;\n" );
  poout.println( "var associatedprimg = opener.document.getElementById(\"associatedprimg" + lineID + "\");\n" );
  poout.println( "associatedprimg.setAttribute(\"src\",'/images/minus.jpg');\n" );
  poout.println( "var associatedprsstatus  =  opener.document.getElementById(\"associatedprsstatus" + lineID + "\");\n" );
  poout.println( "associatedprsstatus.value = \"Yes\";\n" );
  poout.println( "var totalofassociatedprs  =  opener.document.getElementById(\"totalofassociatedprs" + lineID + "\");\n" );
  poout.println( "totalofassociatedprs.value = \"" + totalitemQty + "\";\n" );
  poout.println( "var nofassociatedprs  =  opener.document.getElementById(\"nofassociatedprs" + lineID + "\");\n" );
  poout.println( "nofassociatedprs.value = \"" + count + "\";\n" );
  poout.println( " }\n" );

  return;
}

private static Vector getSpecData( TcmISDBModel dbObj6,String radianpo)
  {
    Vector Data=new Vector();
    Hashtable DataH=new Hashtable();
    Hashtable summary=new Hashtable();
    int num_rec=0;
    int count=0;
    summary.put( "TOTAL_NUMBER",new Integer( count ) );
    Data.addElement( summary );

    DBResultSet dbrs=null;
    ResultSet specrs=null;

    try
    {
    String resultQuery= "select * from table (pkg_po.fx_po_tbl("+radianpo+"))";
    dbrs=dbObj6.doQuery( resultQuery );
    specrs=dbrs.getResultSet();

    int i=0;
    while ( specrs.next() )
    {
      num_rec++;

      DataH=new Hashtable();

      DataH.put( "SPEC_ID",specrs.getString( "SPEC_ID" ) == null ? "" : specrs.getString( "SPEC_ID" ).trim() );
      DataH.put( "SPEC_LIBRARY_DESC",specrs.getString( "SPEC_LIBRARY_DESC" ) == null ? "" : specrs.getString( "SPEC_LIBRARY_DESC" ).trim() );
      DataH.put( "CONTENT",specrs.getString( "CONTENT" ) == null ? "" : specrs.getString( "CONTENT" ).trim() );
      DataH.put( "ON_LINE",specrs.getString( "ON_LINE" ) == null ? "" : specrs.getString( "ON_LINE" ).trim() );
      DataH.put( "CURRENT_COC_REQUIREMENT",specrs.getString( "CURRENT_COC_REQUIREMENT" ) == null ? "" : specrs.getString( "CURRENT_COC_REQUIREMENT" ).trim() );
      DataH.put( "CURRENT_COA_REQUIREMENT",specrs.getString( "CURRENT_COA_REQUIREMENT" ) == null ? "" : specrs.getString( "CURRENT_COA_REQUIREMENT" ).trim() );
	  DataH.put( "SAVED_COC",specrs.getString( "SAVED_COC" ) == null ? "" : specrs.getString( "SAVED_COC" ).trim() );
      DataH.put( "SAVED_COA",specrs.getString( "SAVED_COA" ) == null ? "" : specrs.getString( "SAVED_COA" ).trim() );
	  DataH.put( "COC_REQ_AT_SAVE",specrs.getString( "COC_REQ_AT_SAVE" ) == null ? "" : specrs.getString( "COC_REQ_AT_SAVE" ).trim() );
      DataH.put( "COA_REQ_AT_SAVE",specrs.getString( "COA_REQ_AT_SAVE" ) == null ? "" : specrs.getString( "COA_REQ_AT_SAVE" ).trim() );
      DataH.put( "SPEC_LIBRARY",specrs.getString( "SPEC_LIBRARY" ) == null ? "" : specrs.getString( "SPEC_LIBRARY" ).trim() );
      DataH.put( "COLOR",specrs.getString( "COLOR" ) == null ? "" : specrs.getString( "COLOR" ).trim() );
	  DataH.put( "COLOR_AT_SAVE",specrs.getString( "COLOR_AT_SAVE" ) == null ? "" : specrs.getString( "COLOR_AT_SAVE" ).trim() );
	  DataH.put( "SPEC_ID_DISPLAY",specrs.getString( "SPEC_ID_DISPLAY" ) == null ? "" : specrs.getString( "SPEC_ID_DISPLAY" ).trim() );
	  DataH.put( "DETAIL",specrs.getString( "DETAIL" ) == null ? "" : specrs.getString( "DETAIL" ).trim() );
      DataH.put( "RADIAN_PO",specrs.getString( "RADIAN_PO" ) == null ? "" : specrs.getString( "RADIAN_PO" ).trim() );
      DataH.put( "PO_LINE",specrs.getString( "PO_LINE" ) == null ? "" : specrs.getString( "PO_LINE" ).trim() );
      DataH.put( "AMENDMENT",specrs.getString( "AMENDMENT" ) == null ? "" : specrs.getString( "AMENDMENT" ).trim() );

      Data.addElement( DataH );
      count+=1;
      i++;
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
  specrs=null;

  Hashtable recsum=new Hashtable();
  recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
  Data.setElementAt( recsum,0 );
  return Data;
  }

private Vector getAssociatedPRsData( String radianPO,String inventoryGroup,String opsEntityId, String branchPlant)
{
  Vector Data=new Vector();
  Hashtable DataH=new Hashtable();
  Hashtable summary=new Hashtable();
  int count=0;
  int num_rec=0;
  summary.put( "TOTAL_NUMBER",new Integer( count ) );
  Data.addElement( summary );

    //StringBuffer Msgtemp=new StringBuffer();
  DBResultSet dbrs = null;
  ResultSet rs = null;
  Connection connect1=null;
  CallableStatement cs=null;

  int totalitemQty=0;
  try
  {
    connect1=db.getConnection();
    connect1.setAutoCommit(false);
    cs=connect1.prepareCall( "{call PKG_BUY_ORDER.p_buy_page_query(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
    cs.setString(1,branchPlant);
    cs.setString(2,inventoryGroup);
    cs.setString(3,"ALL");
    cs.setString(4,"ALL");
    cs.setString(5,"ALL");
    cs.setString(6,"RADIAN_PO");
    cs.setString(7,"IS");
    cs.setString(8,radianPO);
    cs.setString(9,"All Statuses");
    cs.setString(10,"ALL");
    cs.setString(11,"BUYER_NAME,item_id");
    cs.setString(12,"NO");
    cs.setString(13,"NO");
    cs.setString(14,"NO");
    cs.setString(15,"NO");
    cs.registerOutParameter( 16,OracleTypes.VARCHAR );
    cs.setNull(17,OracleTypes.INTEGER);
    cs.setString(18,opsEntityId);
    //cs.setString(19,ItemID);
    cs.execute();
    String associatedprQuesry=cs.getObject( 16 ).toString();

    dbrs=db.doQuery( associatedprQuesry );
    ResultSet assocprsrs=dbrs.getResultSet();

    while ( assocprsrs.next() )
    {
      num_rec++;

      DataH=new Hashtable();
      DataH.put( "NOTES", assocprsrs.getString( "NOTES" ) == null ? "" : assocprsrs.getString( "NOTES" ).trim() );
      DataH.put( "COMMENTS", assocprsrs.getString( "COMMENTS" ) == null ? "" : assocprsrs.getString( "COMMENTS" ).trim() );
      DataH.put( "LPP", assocprsrs.getString( "LPP" ) == null ? "" : assocprsrs.getString( "LPP" ).trim());
      DataH.put( "REQUESTOR_FIRST_NAME", assocprsrs.getString( "REQUESTOR_FIRST_NAME" ) == null ? "" : assocprsrs.getString( "REQUESTOR_FIRST_NAME" ).trim());
      DataH.put( "REQUESTOR_LAST_NAME", assocprsrs.getString( "REQUESTOR_LAST_NAME" ) == null ? "" : assocprsrs.getString( "REQUESTOR_LAST_NAME" ).trim());
      DataH.put( "EMAIL", assocprsrs.getString( "EMAIL" ) == null ? "" : assocprsrs.getString( "EMAIL" ).trim() );
      DataH.put( "PHONE", assocprsrs.getString( "PHONE" ) == null ? "" : assocprsrs.getString( "PHONE" ).trim());
      DataH.put( "PR_NUMBER", assocprsrs.getString( "PR_NUMBER" ) == null ? "" : assocprsrs.getString( "PR_NUMBER" ).trim());
      DataH.put( "ITEM_ID", assocprsrs.getString("ITEM_ID")==null?"":assocprsrs.getString("ITEM_ID").trim());
      DataH.put( "NEED_DATE1", assocprsrs.getString( "NEED_DATE1" ) == null ? "" : assocprsrs.getString( "NEED_DATE1" ).trim());
      DataH.put( "PART_ID", assocprsrs.getString( "PART_ID" ) == null ? "" : assocprsrs.getString( "PART_ID" ).trim() );
      DataH.put( "SIZE_UNIT", assocprsrs.getString("SIZE_UNIT")==null?"":assocprsrs.getString("SIZE_UNIT").trim());
      DataH.put( "ITEM_TYPE", assocprsrs.getString( "ITEM_TYPE" ) == null ? "" : assocprsrs.getString( "ITEM_TYPE" ).trim());
      DataH.put( "ORDER_QUANTITY", assocprsrs.getString( "ORDER_QUANTITY" ) == null ? "" : assocprsrs.getString( "ORDER_QUANTITY" ).trim());
      DataH.put( "FACILITY", assocprsrs.getString( "FACILITY" ) == null ? "" : assocprsrs.getString( "FACILITY" ).trim());
      DataH.put( "RAYTHEON_PO", assocprsrs.getString( "RAYTHEON_PO" ) == null ? "" : assocprsrs.getString( "RAYTHEON_PO" ).trim());
      DataH.put( "BRANCH_PLANT", assocprsrs.getString( "BRANCH_PLANT" ) == null ? "" : assocprsrs.getString( "BRANCH_PLANT" ).trim());
      DataH.put( "COMPANY_ID", assocprsrs.getString("COMPANY_ID")==null?"":assocprsrs.getString("COMPANY_ID").trim());
      DataH.put( "MR_NUMBER", assocprsrs.getString( "MR_NUMBER" ) == null ? "" : assocprsrs.getString( "MR_NUMBER" ).trim());
      DataH.put( "MR_LINE_ITEM", assocprsrs.getString( "MR_LINE_ITEM" ) == null ? "" : assocprsrs.getString( "MR_LINE_ITEM" ).trim());
      DataH.put( "REORDER_POINT", assocprsrs.getString( "REORDER_POINT" ) == null ? "" : assocprsrs.getString( "REORDER_POINT" ).trim());
      DataH.put( "STOCKING_LEVEL", assocprsrs.getString( "STOCKING_LEVEL" ) == null ? "" : assocprsrs.getString( "STOCKING_LEVEL" ).trim());
      DataH.put( "AVAILABLE_QUANTITY", assocprsrs.getString( "AVAILABLE_QUANTITY" ) == null ? "" : assocprsrs.getString( "AVAILABLE_QUANTITY" ).trim());
      DataH.put( "OPEN_PO_QUANTITY", assocprsrs.getString( "OPEN_PO_QUANTITY" ) == null ? "" : assocprsrs.getString( "OPEN_PO_QUANTITY" ).trim());
      DataH.put( "SHIP_TO_LOCATION_ID", assocprsrs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "" : assocprsrs.getString( "SHIP_TO_LOCATION_ID" ).trim());
      DataH.put( "DELIVERY_TYPE", assocprsrs.getString( "DELIVERY_TYPE" ) == null ? "" : assocprsrs.getString( "DELIVERY_TYPE" ).trim());
      DataH.put( "MR_QUANTITY", assocprsrs.getString( "MR_QUANTITY" ) == null ? "" : assocprsrs.getString( "MR_QUANTITY" ).trim());
      DataH.put( "REQUEST_ID", assocprsrs.getString( "REQUEST_ID" ) == null ? "" : assocprsrs.getString( "REQUEST_ID" ).trim());
      DataH.put( "CATALOG_ID", assocprsrs.getString( "CATALOG_ID" ) == null ? "" : assocprsrs.getString( "CATALOG_ID" ).trim());
      DataH.put( "STOCKED", assocprsrs.getString( "STOCKED" ) == null ? "" : assocprsrs.getString( "STOCKED" ).trim());
      DataH.put( "INVENTORY_GROUP", assocprsrs.getString( "INVENTORY_GROUP" ) == null ? "" : assocprsrs.getString( "INVENTORY_GROUP" ).trim());
	  DataH.put( "SHIP_TO_DELIVERY_POINT", assocprsrs.getString( "SHIP_TO_DELIVERY_POINT" ) == null ? "" : assocprsrs.getString( "SHIP_TO_DELIVERY_POINT" ).trim());
      DataH.put( "LINE_PURCHASING_NOTE", assocprsrs.getString( "LINE_PURCHASING_NOTE" ) == null ? "" : assocprsrs.getString( "LINE_PURCHASING_NOTE" ).trim());
      DataH.put( "SPEC_LIST", assocprsrs.getString( "SPEC_LIST" ) == null ? "" : assocprsrs.getString( "SPEC_LIST" ).trim());
      DataH.put( "CSR_NAME", assocprsrs.getString( "CSR_NAME" ) == null ? "" : assocprsrs.getString( "CSR_NAME" ).trim());
      DataH.put( "REMAINING_SHELF_LIFE_PERCENT", assocprsrs.getString( "REMAINING_SHELF_LIFE_PERCENT" ) == null ? "" : assocprsrs.getString( "REMAINING_SHELF_LIFE_PERCENT" ).trim());
      	
      Data.addElement( DataH );
    }
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

 //polog.info("opsEntityId "+opsEntityId+" inventoryGroup  "+inventoryGroup+ "  radianPO " +radianPO+ " num_rec "+num_rec);

 Hashtable recsum=new Hashtable();
 recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
 Data.setElementAt( recsum,0 );
 return Data;
}

public void refreshAddCharges( String radianpo,String LineNum,String ammenNumber,String radianBpoA,PrintWriter poout )
{
  //StringBuffer Msgn=new StringBuffer();
  //StringBuffer Msgbody=new StringBuffer();
  nematid_Servlet=bundle.getString( "PURCHASE_ORDER_PO" );
  DBResultSet dbrs = null;
  ResultSet rs = null;
  poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Lookup","",false ) );
  printJavaScripts(poout);
  poout.println( "// -->\n </SCRIPT>\n" );

  //StringBuffer Msgtemp=new StringBuffer();

  //Build the Java Script Here and Finish the thing
  poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
  poout.println( "<!--\n" );
  poout.println( "function sendSupplierData()\n" );
  poout.println( "{\n" );
  poout.println( "itemdescdivrowset = opener.document.getElementById(\"itemdescdivrow" + LineNum + "" + LineNum + "\");\n" );
  poout.println( "var itemadddescset = itemdescdivrowset.innerHTML;\n" );
  poout.println( "secondarysuppliercell = opener.document.getElementById(\"secondarysuppliercelldivrow" + LineNum + "" + LineNum + "\");\n" );
  poout.println( "var secondarysuppliercellset = secondarysuppliercell.innerHTML;\n" );
  poout.println( "opener.refreshlinecharges(" + LineNum + "); \n" );

  //int rowNumber=Integer.parseInt( LineNum ) * 1000;
  BigDecimal rowNumber= new BigDecimal(LineNum);
  rowNumber = rowNumber.multiply(new BigDecimal("1000"));
  int addchargecount=0;
  try
  {

    if ( radianpo.trim().length() > 0 )
    {
      dbrs=db.doQuery( "Select PO_LINE from po_add_charge_allocation_all where RADIAN_PO = " + radianpo + " and PO_ADD_CHARGE_LINE = " + rowNumber + " and AMENDMENT =" + ammenNumber + " " );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        addchargecount++;
        String po_line=rs.getString( "PO_LINE" ) == null ? "" : rs.getString( "PO_LINE" ).trim();

        //int addChgrownum1=Integer.parseInt( po_line );
        //addChgrownum1= ( addChgrownum1 / 1000 );
        BigDecimal addChargerowNumer= new BigDecimal(po_line);
        addChargerowNumer = addChargerowNumer.divide(new BigDecimal("1000"));
        addChargerowNumer.setScale(1,3);
        String addChgrownum="" + addChargerowNumer + "";

        poout.println( "chargeAncnumber = opener.document.getElementById(\"chargeAncnumber" + LineNum + "" + addChgrownum + "\");\n" );
        poout.println( "chargeAncnumber.value = \"Yes\";\n" );
      }
    }
    else
    {
      dbrs=db.doQuery( "Select BPO_LINE from bpo_add_charge_allocation_all where BPO = " + radianBpoA + " and BPO_ADD_CHARGE_LINE = " + rowNumber + " and AMENDMENT = " + ammenNumber + "" );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        addchargecount++;
        String po_line=rs.getString( "BPO_LINE" ) == null ? "" : rs.getString( "BPO_LINE" ).trim();

        //int addChgrownum1=Integer.parseInt( po_line );
        //addChgrownum1= ( addChgrownum1 / 1000 );
        BigDecimal addChargerowNumer= new BigDecimal(po_line);
        addChargerowNumer = addChargerowNumer.divide(new BigDecimal("1000"));
        addChargerowNumer.setScale(1,3);
        String addChgrownum="" + addChargerowNumer + "";

        poout.println( "chargeAncnumber = opener.document.getElementById(\"chargeAncnumber" + LineNum + "" + addChgrownum + "\");\n" );
        poout.println( "chargeAncnumber.value = \"Yes\";\n" );
      }
    }

  }
  catch ( Exception e )
  {
    e.printStackTrace();
  }
  finally
  {
    if ( dbrs != null ) {dbrs.close();}
  }

  poout.println( "opener.checkAddChargeLines(" + LineNum + "); \n" );
  poout.println( "itemdescdivrowset = opener.document.getElementById(\"itemdescdivrow" + LineNum + "" + LineNum + "\");\n" );
  poout.println( "itemdescdivrowset.innerHTML = itemadddescset;\n" );
  poout.println( "secondarysuppliercell = opener.document.getElementById(\"secondarysuppliercelldivrow" + LineNum + "" + LineNum + "\");\n" );
  poout.println( "secondarysuppliercell.innerHTML = secondarysuppliercellset;\n" );
  poout.println( "opener.makeCursorNormal();\n" );
  poout.println( "window.close();\n" );
  poout.println( " }\n" );
  poout.println( "// -->\n</SCRIPT>\n" );
  //poout.println( Msgtemp );
  poout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
  poout.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
  poout.println( "<CENTER><BR><BR>\n" );
  poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
  poout.println( "</FORM></BODY></HTML>\n" );
  //poout.println( Msgbody );

  return;
}

public void buildLikeSupplier( String branchPlant,String inventoryGroup,String SearchFlag,String SearchString,String sortby,String radianPO,
                                       String subUserAction,String openOrders,String SearchString1,String sortby1,Hashtable hub_list_set,
                                       String brokenprOrders,String unconfirmedOrders,String buyerId,String showonlyMine,HttpSession session,String comingfrom,PrintWriter poout )
{
  //StringBuffer Msgl=new StringBuffer();
  Connection connect1=null;
  CallableStatement cs=null;
  radianPO=radianPO.trim();
  boolean seperatepage =false;
  if ("seperate".equalsIgnoreCase(comingfrom)) { seperatepage = true;}

  boolean foundsupplier=false;
  if ( "recalpo".equalsIgnoreCase( SearchFlag ) )
  {
    if ( "po".equalsIgnoreCase( subUserAction ) && ( !"Search".equalsIgnoreCase( SearchFlag ) ) )
    {
      if ( radianPO.trim().length() > 0 )
      {
        try
        {
          int test_number=DbHelpers.countQuery( db,"select count(*) from po_header_view where RADIAN_PO = '" + radianPO + "' " );

          if ( test_number == 1 )
          {
            foundsupplier=true;
          }
        }
        catch ( Exception e )
        {
          //e.printStackTrace();
          poout.println( "<BODY><BR><BR>\n" );
          poout.println( "<FONT FACE=\"Arial\" SIZE=\"3\">Error Occured.<BR>\n" );
          poout.println( "Please Try Again.\n" );
          poout.println( "<CENTER><BR><BR>\n" );
          poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
          poout.println( "</FORM></BODY></HTML>\n" );
          return;
        }
      }

      if ( foundsupplier )
      {
        buildsendSupplierpo( radianPO,poout);
        return;
      }
    }
    else if ( "bpo".equalsIgnoreCase( subUserAction ) && ( !"Search".equalsIgnoreCase( SearchFlag ) ) )
    {
      if ( radianPO.trim().length() > 0 )
      {
        try
        {
          int test_number=DbHelpers.countQuery( db,"select count(*) from blanket_po where BPO = '" + radianPO + "' " );

          if ( test_number == 1 )
          {
            foundsupplier=true;
          }
        }
        catch ( Exception e )
        {
          //e.printStackTrace();
          poout.println( "<BODY><BR><BR>\n" );
          poout.println( "<FONT FACE=\"Arial\" SIZE=\"3\">Error Occured.<BR>\n" );
          poout.println( "Please Try Again.\n" );
          poout.println( "<CENTER><BR><BR>\n" );
          poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
          poout.println( "</FORM></BODY></HTML>\n" );
          return;
        }
      }

      if ( foundsupplier )
      {
        buildsendSupplierbpo( radianPO,poout);
        return;
      }
    }
  }

  nematid_Servlet=bundle.getString( "PURCHASE_ORDER_PO" );
  if ( "bpo".equalsIgnoreCase( subUserAction ) )
  {
    poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Blanket Order Lookup","",false ) );
  }
  else
  {
    poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Lookup","",false ) );
  }
  //printJavaScripts(poout);
	poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
  poout.println( "<!--\n" );
  poout.println( "function ShowSearch(name,entered)\n" );
  poout.println( "{\n" );
  poout.println( " with (entered)\n" );
  poout.println( "{" );
  poout.println( " supplierid = document.getElementById(\"supplierid\");\n" );
  poout.println( " if ( (supplierid.value.length > 0) || (name.toString() == \"Search\") )\n" );
  poout.println( " {\n" );
  poout.println( "        var HubName  =  document.getElementById(\"HubName\");\n" );
  poout.println( "        loc = \"" + nematid_Servlet + "&Action=\" + name.toString() + \"&SearchString=\";\n" );
  poout.println( "        loc = loc + window.document.SupplierLike.SearchString.value;\n" );
  poout.println( "        loc = loc + \"&HubName=\"; \n" );
  poout.println( "        loc = loc + window.document.SupplierLike.HubName.value;\n" );
  poout.println( "        loc = loc + \"&inventoryGroup=\"; \n" );
  poout.println( "        loc = loc + window.document.SupplierLike.inventoryGroup.value;\n" );
  poout.println( "        loc = loc + \"&subUserAction=\"; \n" );
  poout.println( "        loc = loc + window.document.SupplierLike.subUserAction.value;\n" );
  poout.println( "        loc = loc + \"&radianPO=\"; \n" );
  poout.println( "        loc = loc + window.document.SupplierLike.supplierid.value;\n" );
  poout.println( "        loc = loc + \"&SearchString1=\"; \n" );
  poout.println( "        loc = loc + window.document.SupplierLike.SearchString1.value;\n" );
  poout.println( "        loc = loc + \"&SortBy=\"; \n" );
  poout.println( "        loc = loc + window.document.SupplierLike.SortBy.value;\n" );
  poout.println( "        loc = loc + \"&SortBy1=\"; \n" );
  poout.println( "        loc = loc + window.document.SupplierLike.SortBy1.value;\n" );
  poout.println( "        loc = loc + \"&comingfrom="+comingfrom+"\"; \n" );
  poout.println( "        openOrders = document.getElementById(\"openOrders\");\n" );
  poout.println( "        if (openOrders.checked) \n" );
  poout.println( "        {\n" );
  poout.println( "        loc = loc + \"&openOrders=Yes\"; \n" );
  poout.println( "        }\n" );
  poout.println( "        brokenprOrders = document.getElementById(\"brokenprOrders\");\n" );
  poout.println( "        if (brokenprOrders.checked) \n" );
  poout.println( "        {\n" );
  poout.println( "        loc = loc + \"&brokenprOrders=Yes\"; \n" );
  poout.println( "        }\n" );
  poout.println( "        unconfirmedOrders = document.getElementById(\"unconfirmedOrders\");\n" );
  poout.println( "        if (unconfirmedOrders.checked) \n" );
  poout.println( "        {\n" );
  poout.println( "        loc = loc + \"&unconfirmedOrders=Yes\"; \n" );
  poout.println( "        }\n" );
  poout.println( "        onlymypos = document.getElementById(\"onlymypos\");\n" );
  poout.println( "        loc = loc + \"&onlymypos=\" + onlymypos.value; \n" );
  poout.println( "        window.location.replace(loc);\n" );
  poout.println( "    }\n" );
  poout.println( "  }\n" );
  poout.println( " }\n" );
  poout.println( "// -->\n </SCRIPT>\n" );
	Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)
		 session.getAttribute("hubInventoryGroupOvBeanCollection"));
	poout.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
	try {
	 poout.println(radian.web.poHelpObj.getHubInventoryGroupJavaScript(
		hubInventoryGroupOvBeanCollection, true, false));
	}
	catch (Exception ex) {
	}
	poout.print("// -->\n </SCRIPT>\n");
  poout.println( "<SCRIPT SRC=\"/clientscripts/posearch.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );

  String checked="";
  String checkedun="";
  String checkedbro="";
  String checkedonlymine="";

  if ( "yes".equalsIgnoreCase( unconfirmedOrders ) )
  {
    checkedun="checked";
  }

  if ( "yes".equalsIgnoreCase( showonlyMine ) )
  {
    checkedonlymine="checked";
  }

  if ( "yes".equalsIgnoreCase( brokenprOrders ) )
  {
    checkedbro="checked";
  }

  if ( "yes".equalsIgnoreCase( openOrders ) )
  {
    checked="checked";
  }
  else if ( !"Search".equalsIgnoreCase( SearchFlag ) )
  {
    checked="checked";
  }

  if (seperatepage)
  {
	poout.println( "<BODY>\n" );
	poout.println( radian.web.HTMLHelpObj.printPurchaseOrderTitleBar( "<B>Purchase Order Search</B>\n" ) );
  }
  else
  {
	poout.println( "<BODY onload=\"javascript:window.resizeTo(800,600)\">\n" );
  }

	poout.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
	poout.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></center>\n" );
	poout.println( "</DIV>\n" );
	poout.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

  poout.println( "<FORM METHOD=\"POST\" id=\"SupplierLike\" name=\"SupplierLike\" onSubmit=\"return SubmitOnlyOnce()\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
  poout.println( "<INPUT TYPE=\"hidden\" ID=\"supplierdesc\" NAME=\"supplierdesc\" VALUE=\"\">\n" );
  poout.println( "<INPUT TYPE=\"hidden\" ID=\"subUserAction\" NAME=\"subUserAction\" VALUE=\"" + subUserAction + "\">\n" );
  poout.println( "<INPUT TYPE=\"hidden\" ID=\"comingfrom\" NAME=\"comingfrom\" VALUE=\"" + comingfrom + "\">\n" );
  poout.println( "<INPUT TYPE=\"hidden\" ID=\"addline1\" NAME=\"addline1\" VALUE=\"\">\n" );
  poout.println( "<INPUT TYPE=\"hidden\" ID=\"addline2\" NAME=\"addline2\" VALUE=\"\">\n" );
  poout.println( "<INPUT TYPE=\"hidden\" ID=\"addline3\" NAME=\"addline3\" VALUE=\"\">\n" );
  poout.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
  poout.println( "<TR>\n" );
  poout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><B>Hub:</B></TD>\n" );
  poout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
  poout.println( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" ID=\"HubName\" onchange=\"hubChanged()\" size=\"1\">\n" );
  poout.println( "<OPTION VALUE=\"None\">Please Select</OPTION>\n" );
	poout.println(radian.web.HTMLHelpObj.getHubDropDown(
		hubInventoryGroupOvBeanCollection, branchPlant));
  poout.println( "</SELECT>\n" );
  poout.println( "</TD>\n" );

  poout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><B>Search:</B></TD>\n" );
	poout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
  poout.println( "<SELECT CLASS=\"HEADER\" ID=\"SortBy\" NAME=\"SortBy\" size=\"1\">\n" );
	Hashtable searchthese=new Hashtable();
	searchthese.put( "Supplier","SUPPLIER" );
	searchthese.put( "Item Id","ITEM_ID" );
	searchthese.put( "Item Desc","ITEM_DESC" );
	//searchthese.put( "Ship to Location","SHIP_TO_LOCATION_ID" );
	poout.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( searchthese.entrySet(),sortby ) );
  poout.println( "</SELECT>\n" );
  poout.println( "</TD>\n" );
  poout.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">&nbsp;&nbsp;<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + SearchString + "\" SIZE=\"25\"></TD>\n" );
  poout.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n" );
  poout.println( "</TR><TR>\n" );

  //inventory Group
	poout.print("<TD HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	poout.print("<B>Inv Grp:</B>&nbsp;\n");
	poout.print("</TD>\n");
	poout.print("<TD WIDTH=\"10%\">\n");
	poout.print("<SELECT CLASS=\"HEADER\" ID=\"inventoryGroup\" NAME=\"inventoryGroup\" size=\"1\">\n");

	if ( branchPlant.equalsIgnoreCase("ERROR") || branchPlant.equalsIgnoreCase("None") || branchPlant.equalsIgnoreCase("0") || branchPlant.trim().length() == 0)
	{
	 poout.println("<OPTION VALUE=\"\">All</OPTION>\n");
  }
	poout.println(radian.web.HTMLHelpObj.getInventoryGroupDropDown(
	 hubInventoryGroupOvBeanCollection, branchPlant, inventoryGroup, false));
	poout.print("</SELECT>\n");
	poout.print("</TD>\n");

  poout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><B>Search:</B></TD>\n" );
	poout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
  poout.println( "<SELECT CLASS=\"HEADER\" ID=\"SortBy1\" NAME=\"SortBy1\" size=\"1\">\n" );
	poout.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( searchthese.entrySet(),sortby1 ) );
  poout.println( "</SELECT>\n" );
  poout.println( "</TD>\n" );
  poout.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">&nbsp;&nbsp;<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString1\" value=\"" + SearchString1 + "\" SIZE=\"25\"></TD>\n" );
  if (!seperatepage)
  {
	poout.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"New\" value=\"New\" OnClick=\"ShowSearch(name,this)\"></TD></TR>\n" );
  }
  else
  {
	poout.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\">&nbsp;</TD></TR>\n" );
  }
  poout.println( "<TR>\n" );

//Buyer
	if ( !"Search".equalsIgnoreCase( SearchFlag ) )
	{
		showonlyMine=buyerId;
	}
	poout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	poout.println( "<B>Buyer:</B>&nbsp;\n" );
	poout.println( "</TD>\n" );
	poout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
	poout.println( "<SELECT CLASS=\"HEADER\" ID=\"onlymypos\" NAME=\"onlymypos\" size=\"1\">\n" );
	poout.println( "<OPTION VALUE=\"\">Any Buyer</OPTION>\n" );
	poout.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) session.getAttribute( "PO_BUYERNAMES" ),showonlyMine ) );
	poout.println( "</SELECT>\n" );
	poout.println( "</TD>\n" );

  if ( "bpo".equalsIgnoreCase( subUserAction ) )
  {
    poout.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\"><INPUT TYPE=\"hidden\" value=\"No\" ID=\"openOrders\" NAME=\"openOrders\">&nbsp;</TD>\n" );
		poout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">&nbsp;</TD>\n" );
    poout.println( "<TD WIDTH=\"15%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><INPUT TYPE=\"checkbox\" value=\"Yes\" ID=\"unconfirmedOrders\" NAME=\"unconfirmedOrders\" " + checkedun + "></TD>\n" );
    poout.println( "<TD WIDTH=\"10%\" CLASS=\"announce\" ALIGN=\"LEFT\"><INPUT TYPE=\"hidden\" value=\"No\" ID=\"brokenprOrders\" NAME=\"brokenprOrders\">Unconfirmed</TD>\n" );
  }
  else
  {
    poout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"checkbox\" value=\"Yes\" ID=\"openOrders\" NAME=\"openOrders\" " + checked + "></TD>\n" );
		poout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\"><B>Open POs Only</B></TD>\n" );
    poout.println( "<TD WIDTH=\"15%\" CLASS=\"announce\" ALIGN=\"LEFT\"><INPUT TYPE=\"checkbox\" value=\"Yes\" ID=\"unconfirmedOrders\" NAME=\"unconfirmedOrders\" " + checkedun + "> <B>Unconfirmed</B></TD>\n" );
    poout.println( "<TD WIDTH=\"10%\" CLASS=\"announce\" ALIGN=\"LEFT\">&nbsp;</TD>\n" );
  }

  poout.println( "</TR>\n" );

  poout.println( "<TR>\n" );

  if ( "bpo".equalsIgnoreCase( subUserAction ) )
  {
    poout.println("<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Blanket PO:</B></TD>\n" );
  }
  else
  {
    poout.println("<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>PO:</B></TD>\n" );
  }

  poout.println( "<TD WIDTH=\"10%\" CLASS=\"announce\" ALIGN=\"LEFT\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" id=\"supplierid\" name=\"supplierid\" value=\"" + radianPO + "\" SIZE=\"10\" readonly></TD>\n" );
	if ( !"bpo".equalsIgnoreCase( subUserAction ) )
  {
	poout.println( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><INPUT TYPE=\"checkbox\" value=\"Yes\" ID=\"brokenprOrders\" NAME=\"brokenprOrders\" " + checkedbro + "></TD>\n" );
  poout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\"><B>Broken PR Link</B></TD>\n" );
	poout.println( "<TD CLASS=\"announce\" COLSPAN=\"2\" ALIGN=\"LEFT\">&nbsp;</TD>\n" );
  }
	else
	{
	 poout.println( "<TD CLASS=\"announce\" COLSPAN=\"4\" ALIGN=\"LEFT\">&nbsp;</TD>\n" );
	}
  poout.println( "</TR>\n" );

  poout.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
  poout.println( "<TR>\n" );
  poout.println( "<TH WIDTH=\"5%\"><B>PO</B></TH>\n" );
  poout.println( "<TH WIDTH=\"15%\"><B>Supplier</B></TH>\n" );
  poout.println( "<TH WIDTH=\"5%\"><B>Buyer</B></TH>\n" );
  poout.println( "<TH WIDTH=\"5%\"><B>Hub</B></TH>\n" );
	poout.println( "<TH WIDTH=\"5%\"><B>Inventory Group</B></TH>\n" );
  poout.println( "<TH WIDTH=\"5%\"><B>Date Created/<BR>Confirmed</B></TH>\n" );
  poout.println( "<TH WIDTH=\"5%\"><B>Item ID</B></TH>\n" );
  poout.println( "<TH WIDTH=\"15%\"><B>Item Description</B></TH>\n" );
  poout.println( "<TH WIDTH=\"5%\"><B>Unit Price</B></TH>\n" );
  poout.println( "<TH WIDTH=\"5%\"><B>Quantity</B></TH>\n" );
  poout.println( "<TH WIDTH=\"5%\"><B>Ext Price</B></TH>\n" );
  poout.println( "<TH WIDTH=\"5%\"><B>Payment Terms</B></TH>\n" );
  poout.println( "</TR></TABLE>\n" );

  if (!seperatepage)
  {
  //open scrolling table
  poout.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
  poout.println( "<TBODY>\n" );
  poout.println( "<TR>\n" );
  poout.println( "<TD VALIGN=\"TOP\">\n" );
  poout.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column450\">\n" );
  }
  //Write code to insert rows here
  poout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

  int totalrecords=0;
  int count=0;

  if ( !((!(radianPO.trim().length() > 0)) && (!"Search".equalsIgnoreCase(SearchFlag)) ))
  {
    ResultSet rs = null;
    try
		{
		 HubInventoryGroupProcess hubInventoryGroupProcess = new
		 HubInventoryGroupProcess("hub");
		 boolean iscollection = false;
		 if ("None".equalsIgnoreCase(branchPlant))
		 {
			iscollection = hubInventoryGroupProcess.isCollection(
			 hubInventoryGroupOvBeanCollection, null, inventoryGroup);
		 }
		  else
		 {
			iscollection = hubInventoryGroupProcess.isCollection(
			 hubInventoryGroupOvBeanCollection, branchPlant, inventoryGroup);
		 }

      connect1=db.getConnection();
      SearchString=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
      SearchString1=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString1 );

      //polog.info( "Here sortby " + sortby + " radianPO " + radianPO + "    openOrders " + openOrders + "   buyerId  " + showonlyMine );

      if ( "bpo".equalsIgnoreCase( subUserAction ) )
      {
        cs=connect1.prepareCall( "{call pkg_po.pr_bpo_search(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
      }
      else
      {
        cs=connect1.prepareCall( "{call pkg_po.pr_po_search(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
      }

      if ( "Search".equalsIgnoreCase( SearchFlag ) )
      {
        cs.setNull( 1,OracleTypes.INTEGER );
      }
      else
      {
			//polog.debug("radianPO  "+radianPO+"");
        if ( radianPO.length() > 0 )
        {
		  BigDecimal bigponumber = new BigDecimal(radianPO);
		  cs.setBigDecimal( 1,bigponumber );
        }
        else
        {
          cs.setNull( 1,OracleTypes.INTEGER );
        }
      }

      if ( "Yes".equalsIgnoreCase( brokenprOrders ) )
      {
        cs.setNull( 2,OracleTypes.INTEGER );
      }
      else if ( sortby.equalsIgnoreCase( "ITEM_ID" ) )
      {
        if ( SearchString.length() > 0 )
        {
          cs.setInt( 2,Integer.parseInt( SearchString ) );
        }
        else
        {
          cs.setNull( 2,OracleTypes.INTEGER );
        }
      }
      else if ( sortby1.equalsIgnoreCase( "ITEM_ID" ) )
      {
        if ( SearchString1.length() > 0 )
        {
          cs.setInt( 2,Integer.parseInt( SearchString1 ) );
        }
        else
        {
          cs.setNull( 2,OracleTypes.INTEGER );
        }
      }
      else
      {
        cs.setNull( 2,OracleTypes.INTEGER );
      }

      if ( "Yes".equalsIgnoreCase( brokenprOrders ) )
      {
        cs.setNull( 3,OracleTypes.INTEGER );
      }
      else if ( sortby.equalsIgnoreCase( "ITEM_DESC" ) )
      {
        cs.setString( 3,SearchString );
      }
      else if ( sortby1.equalsIgnoreCase( "ITEM_DESC" ) )
      {
        cs.setString( 3,SearchString1 );
      }
      else
      {
        cs.setNull( 3,OracleTypes.VARCHAR );
      }

      if ( sortby.equalsIgnoreCase( "BUYYER" ) )
      {
        cs.setString( 4,SearchString );
      }
      else if ( sortby1.equalsIgnoreCase( "BUYYER" ) )
      {
        cs.setString( 4,SearchString1 );
      }
      else
      {
        cs.setNull( 4,OracleTypes.VARCHAR );
      }

      if ( sortby.equalsIgnoreCase( "SUPPLIER" ) )
      {
        cs.setString( 5,SearchString );
      }
      else if ( sortby1.equalsIgnoreCase( "SUPPLIER" ) )
      {
        cs.setString( 5,SearchString1 );
      }
      else
      {
        cs.setNull( 5,OracleTypes.VARCHAR );
      }

      if ( !branchPlant.equalsIgnoreCase( "None" ) )
      {
        cs.setString( 6,branchPlant );
      }
      else
      {
        cs.setNull( 6,OracleTypes.VARCHAR );
      }

      if ( "Yes".equalsIgnoreCase( unconfirmedOrders ) )
      {
        cs.setString( 7,"Yes" );
      }
      else
      {
        cs.setNull( 7,OracleTypes.VARCHAR );
      }

      if ( "Yes".equalsIgnoreCase( brokenprOrders ) )
      {
        cs.setString( 8,"Yes" );
      }
      else
      {
        cs.setNull( 8,OracleTypes.VARCHAR );
      }

      cs.setNull( 9,OracleTypes.VARCHAR );

      if ( showonlyMine.length() > 0 )
      {
        cs.setInt( 10,Integer.parseInt( showonlyMine ) );
      }
      else
      {
        cs.setNull( 10,OracleTypes.INTEGER );
      }


      if ( "Yes".equalsIgnoreCase( openOrders ) )
      {
        cs.setString( 11,"Yes" );
      }
      else
      {
        cs.setNull( 11,OracleTypes.VARCHAR );
      }

			if ( inventoryGroup != null && inventoryGroup.length() > 0 &&
			 !inventoryGroup.equalsIgnoreCase( "ALL" ) )
			{
			 cs.setString(12, inventoryGroup);
			}
			else
			{
			 cs.setNull(12, Types.VARCHAR);
		  }

			if (iscollection) {
			 cs.setString(13, "y");
			}
			else {
			 cs.setNull(13, Types.VARCHAR);
			}

      cs.registerOutParameter( 14,OracleTypes.CURSOR );
      cs.registerOutParameter( 15,OracleTypes.VARCHAR );
      cs.execute();
      rs= ( ResultSet ) cs.getObject( 14 );
			String resultQuery=cs.getString( 15 );
			//polog.info("resultQuery  "+resultQuery+"");

      /*ResultSetMetaData rsMeta = rs.getMetaData();
      for(int i =1; i<=rsMeta.getColumnCount(); i++)
      {
        polog.info("String "+rsMeta.getColumnName(i).toString().toLowerCase()+" = rs.getString(\""+rsMeta.getColumnName(i).toString()+"\")==null?\"\":rs.getString(\""+rsMeta.getColumnName(i).toString()+"\").trim();");
      }*/

     while ( rs.next() )
      {
        totalrecords+=1;
        count+=1;

        String radian_po="";
        String date_sent="";
        String critical="";

        if ( "bpo".equalsIgnoreCase( subUserAction ) )
        {
          radian_po=rs.getString( "BPO" ) == null ? "" : rs.getString( "BPO" ).trim();
          date_sent=rs.getString( "END_DATE" ) == null ? "" : rs.getString( "END_DATE" ).trim();
        }
        else
        {
          radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
          date_sent=rs.getString( "DATE_SENT" ) == null ? "" : rs.getString( "DATE_SENT" ).trim();
          critical=rs.getString( "CRITICAL" ) == null ? "" : rs.getString( "CRITICAL" ).trim();
        }

        String date_created=rs.getString( "DATE_CREATED" ) == null ? "" : rs.getString( "DATE_CREATED" ).trim();
        //String supplier = rs.getString("SUPPLIER")==null?"":rs.getString("SUPPLIER").trim();
        //String buyer = rs.getString("BUYER")==null?"":rs.getString("BUYER").trim();
        //String branch_plant=rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ).trim();
        String supplier_name=rs.getString( "SUPPLIER_NAME" ) == null ? "" : rs.getString( "SUPPLIER_NAME" ).trim();
        String buyer_name=rs.getString( "BUYER_NAME" ) == null ? "" : rs.getString( "BUYER_NAME" ).trim();

        String item_id = rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID").trim();
        String item_desc = rs.getString("ITEM_DESC")==null?"":rs.getString("ITEM_DESC").trim();
        String unit_price = rs.getString("UNIT_PRICE")==null?"":rs.getString("UNIT_PRICE").trim();
        String quantity = rs.getString("QUANTITY")==null?"":rs.getString("QUANTITY").trim();
        String ext_price = rs.getString("EXT_PRICE")==null?"":rs.getString("EXT_PRICE").trim();
        String payment_terms = rs.getString("PAYMENT_TERMS")==null?"":rs.getString("PAYMENT_TERMS").trim();
        //String supplier = rs.getString("SUPPLIER")==null?"":rs.getString("SUPPLIER").trim();
        //String buyer = rs.getString("BUYER")==null?"":rs.getString("BUYER").trim();
			  //String resultInventoryGroup = rs.getString("INVENTORY_GROUP")==null?"":rs.getString("INVENTORY_GROUP").trim();
 			  String inventoryGroupName = rs.getString("INVENTORY_GROUP_NAME")==null?"":rs.getString("INVENTORY_GROUP_NAME").trim();
				String hubName = rs.getString("HUB_NAME")==null?"":rs.getString("HUB_NAME").trim();

        String Color=" ";
        if ( count % 2 == 0 )
        {
		  if ( seperatepage )
		  {
			Color="BGCOLOR=\"#E6E8FA\"";
		  }
		  else
		  {
			Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }
		}
        else
        {
		  if ( seperatepage )
		  {
			Color=" ";
		  }
		  else
		  {
			Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }
        }

        if ( "Y".equalsIgnoreCase( critical ) )
        {
		  if ( seperatepage )
		  {
			Color="BGCOLOR=\"#FF9999\"";
		  }
		  else
		  {
			Color="BGCOLOR=\"#FF9999\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		  }
        }

		if (seperatepage)
		{
		  poout.println( "<TR ALIGN=\"MIDDLE\" BORDERCOLOR=\"black\">\n" );
		}
		else
		{
		  poout.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('" + radian_po + "')\" BORDERCOLOR=\"black\">\n" );
		}

        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
		if (seperatepage)
		{
		  String wwwHome=bundle.getString( "WEB_HOME_PAGE" );
		  String radianpourl=wwwHome + "tcmIS/supply/purchorder?po=" + radian_po + "&Action=searchlike&subUserAction=po";
		  poout.println( "<A onclick=\"javascript:window.open('" + radianpourl + "')\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\">" + radian_po + "</A>\n" );
		}
		else
		{
		  poout.println( radian_po );
		}

        poout.println( "</TD>\n" );
        poout.println( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
        poout.println( supplier_name );
        poout.println( "</TD>\n" );
        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
        poout.println( buyer_name );
        poout.println( "</TD>\n" );
        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
        poout.println( hubName );
        poout.println( "</TD>\n" );
				poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
				poout.println( inventoryGroupName );
				poout.println( "</TD>\n" );
        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
        if ( date_sent.length() > 0 )
        {
          poout.println( date_sent );
        }
        else
        {
          poout.println( date_created );
        }
        poout.println( "</TD>\n" );

        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
        poout.println( item_id );
        poout.println( "</TD>\n" );

        poout.println( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
        poout.println( item_desc );
        poout.println( "</TD>\n" );

        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
        poout.println( unit_price );
        poout.println( "</TD>\n" );

        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
        poout.println( quantity );
        poout.println( "</TD>\n" );

        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
        poout.println( ext_price );
        poout.println( "</TD>\n" );

        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
        poout.println( payment_terms );
        poout.println( "</TD>\n" );

        poout.println( "</TR>\n" );
      }
    }
    catch ( Exception e )
    {
      //e.printStackTrace();
      poout.println( "An Error Occured While Querying the Database" );
      poout.println( "</BODY>\n</HTML>\n" );
      return;
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
  }

  if ( totalrecords == 0 )
  {
    poout.println( "<TR><TD>No Records Found</TD></TR>\n" );
  }
  poout.println( "</TABLE>\n" );

  if (!seperatepage)
  {
  //close scrolling table
  poout.println( "</DIV>\n" );
  poout.println( "</TD>\n" );
  poout.println( "</TR>\n" );
  poout.println( "</TBODY>\n" );
  poout.println( "</TABLE>\n" );

  poout.println( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"okupdate\" name=\"okupdate\" value=\"Ok\" OnClick=\"ShowSearch(name,this)\">\n" );
  poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER>\n" );
  }

  poout.println( "</FORM></DIV></BODY></HTML>\n" );
  return;
}

protected void printJavaScripts(PrintWriter poout)
{
  //StringBuffer Msglt=new StringBuffer();
  poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
  poout.println( "<!--\n" );
  poout.println( "function cancel()\n" );
  poout.println( "{\n" );
  poout.println( "opener.makeCursorNormal();\n" );
  poout.println( "window.close();\n" );
  poout.println( "}\n" );
  poout.println( "function sendSupplier(entered)\n" );
  poout.println( "{\n" );
  poout.println( " with (entered)\n" );
  poout.println( " {\n" );
  poout.println( " eval( testmfgid = \"window.document.SupplierLike.supplierid\")\n" );
  poout.println( " if ( (eval(testmfgid.toString())).value.length > 0 )\n" );
  poout.println( "{" );
  poout.println( "selectedRow = opener.document.getElementById(\"supplierid\");\n" );
  poout.println( "selectedRow.className = \"HEADER\";\n" );
  poout.println( "selectedRow.value = window.document.SupplierLike.supplierid.value;\n" );
  poout.println( "selectedRow = opener.document.getElementById(\"validsupplier\");\n" );
  poout.println( "selectedRow.value = \"Yes\";\n" );
  poout.println( "selectedRow = opener.document.getElementById(\"supplierline1\");\n" );
  poout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.supplierdesc.value + \"<FONT>\" ;\n" );
  poout.println( "selectedRow = opener.document.getElementById(\"supplierline2\");\n" );
  poout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.addline1.value + \"<FONT>\" ;\n" );
  poout.println( "selectedRow = opener.document.getElementById(\"supplierline3\");\n" );
  poout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.addline2.value + \"<FONT>\" ;\n" );
  poout.println( "selectedRow = opener.document.getElementById(\"supplierline4\");\n" );
  poout.println( "selectedRow.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">\" + window.document.SupplierLike.addline3.value + \"<FONT>\" ;\n" );
  poout.println( "window.close();\n" );
  poout.println( " }\n" );
  poout.println( " }\n" );
  poout.println( "}\n" );
  poout.println( "function addsupplierID(matidvalue)\n" );
  poout.println( "{\n" );
  poout.println( "document.SupplierLike.supplierid.value = matidvalue;\n" );
  poout.println( "}\n" );

  return;
}

	private void recalTotWithConversion(String thePO)
	{
		orderTotal = new BigDecimal(0);
		for(String [] sa : orderTotalColl)
		  {
			  BigDecimal unitPriFromColl = new BigDecimal(sa[0]);
			  BigDecimal quantFromColl = new BigDecimal(sa[1]);
			  String currIdFromColl = sa[2];
			  
			  if(isOneUSD && !"USD".equalsIgnoreCase(currIdFromColl))
			  {	  
				  BigDecimal conversionRate  = getConverionRate(currIdFromColl,thePO);
					if(conversionRate != null)
	      				unitPriFromColl = conversionRate.multiply(unitPriFromColl);
			  }
			  
			  orderTotal = orderTotal.add(quantFromColl.multiply(unitPriFromColl));			  
		  }
	}
	
	public BigDecimal getConverionRate(String currentCurrency, String thePO)
	{
		BigDecimal conversionRateBDec  = null;
		Date datePOCreated  = null;
		DBResultSet conversionRateDBRS = null;
  		ResultSet conversionRateRS = null;  
		try
      	{
      		
      		StringBuilder query = new StringBuilder("select DATE_CREATED from po where radian_po = ").append(thePO);
      		conversionRateDBRS=db.doQuery(query.toString());
      		conversionRateRS=conversionRateDBRS.getResultSet();
    		while ( conversionRateRS.next() )
    			datePOCreated = conversionRateRS.getDate("DATE_CREATED");
    		
      		 
      		query = new StringBuilder("select nvl(fx_conversion_rate('").append(currentCurrency);
      		query.append("','").append("USD").append("',to_date('").append(datePOCreated).append("', 'RRRR-MM-DD'), nvl((select distinct ops_entity_id from po_line where radian_po = ").append(thePO).append("), 'HAASTCMUSA')), 0) CONVERSION from dual");
      		conversionRateDBRS=db.doQuery(query.toString());
      		conversionRateRS=conversionRateDBRS.getResultSet();

      		while ( conversionRateRS.next() )
      			conversionRateBDec = conversionRateRS.getBigDecimal( "CONVERSION" );
      		   		
      		if(conversionRateBDec.equals(BigDecimal.ZERO))
      		{
         		 
          		query = new StringBuilder("select nvl(fx_conversion_rate('").append(currentCurrency);
          		query.append("','").append("USD").append("',sysdate, nvl((select distinct ops_entity_id from po_line where radian_po = ").append(thePO).append("), 'HAASTCMUSA')),1) CONVERSION from dual");
          		conversionRateDBRS=db.doQuery(query.toString());
          		conversionRateRS=conversionRateDBRS.getResultSet();

          		while ( conversionRateRS.next() )
          			conversionRateBDec = conversionRateRS.getBigDecimal( "CONVERSION" );
      		}
      	}
      	catch ( Exception e )
      	{
      		//e.printStackTrace();
      	}
	    finally
	    {
	      if ( conversionRateDBRS != null ){conversionRateDBRS.close();}
	    }
  		return conversionRateBDec;
	}

}