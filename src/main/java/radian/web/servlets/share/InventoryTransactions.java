package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-14-02
 * added the receipt notes
 *
 * 11-20-02
 * added lot status to the page
 * 03-31-03 - If there is nothing in olddas show the whole history
 * 08-27-03 Code cleanup and removed true from request.getsession()
 * 09-10-03 If the item_Id is not a number making it a blank
 * 11-13-03 added new methods to show the inventory transfer details
 * 12-01-03 Changes to show splash page when searching
 * 12-08-03 Made it possible to update Receipt Notes from the transactions Page. And print BOLs for ship confirmed MRs
 * 02-16-04 Showing PO_Line in the page. Sorting Drop Downs
 * 03-17-04 Making the search non case sensitive
 * 03-29-04 Making search possible on a particular date
 * 05-07-04 Sorting the Hub Drop Down based on the prority in hub_pref table
 * 07-15-04 Adding an option to search by inventory group. Also giveing them an option to sort by and search ondate looks at DOR or ship confirm date depending on
 *          what they search for.
 * 07-29-04 - Showing Storage Temp
 */

public class InventoryTransactions {
 private ServerResourceBundle bundle = null;
 private TcmISDBModel db = null;
 private PrintWriter out = null;
 private boolean allowupdate;
 private boolean intcmIsApplication = false;
 private ResourceLibrary res = null; // res means resource.
 //private static org.apache.log4j.Logger invlog = org.apache.log4j.Logger.getLogger(InventoryTransactions.class);
 public void setupdateStatus(boolean id) {
	this.allowupdate = id;
 }

 private boolean getupdateStatus() throws Exception {
	return this.allowupdate;
 }

 public InventoryTransactions(ServerResourceBundle b, TcmISDBModel d) {
	bundle = b;
	db = d;
 }

 public void doResult(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
	out = response.getWriter();
	response.setContentType("text/html");
	HttpSession invsession = request.getSession();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)invsession.getAttribute("tcmISLocale"));
        PersonnelBean personnelBean = (PersonnelBean) invsession.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        intcmIsApplication = false;
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

	String personnelid = invsession.getAttribute("PERSONNELID") == null ? "" :
	 invsession.getAttribute("PERSONNELID").toString();
	Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)
	 invsession.getAttribute("hubInventoryGroupOvBeanCollection"));

	String branch_plant = request.getParameter("HubName");
	if (branch_plant == null)
	 branch_plant = "";
	branch_plant = branch_plant.trim();

	String invengrp = request.getParameter("invengrp");
	if (invengrp == null)
	 invengrp = "";
	invengrp = invengrp.trim();

	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess("hub");
	boolean iscollection = hubInventoryGroupProcess.isCollection(
	 hubInventoryGroupOvBeanCollection, branch_plant, invengrp);

	String User_Session = request.getParameter("session");
	if (User_Session == null)
	 User_Session = "New";

	String Search_Item = request.getParameter("Item");
	if (Search_Item == null)
	 Search_Item = "";
	Search_Item = Search_Item.trim();
	int itemIdint = 0;
	try {
	 itemIdint = Integer.parseInt(Search_Item);
	}
	catch (NumberFormatException ex) {
	 Search_Item = "";
	}

	String mrnumber = request.getParameter("mrnumber");
	if (mrnumber == null)
	 mrnumber = "";
	mrnumber = mrnumber.trim();

	String User_Action = request.getParameter("UserAction");
	if (User_Action == null)
	 User_Action = "New";

	String SearchType = request.getParameter("Type");
	if (SearchType == null)
	 SearchType = "";

	String receiptid = request.getParameter("receiptid");
	if (receiptid == null)
	 receiptid = "";
	receiptid = receiptid.trim();

	String mfglot = request.getParameter("mfglot");
	if (mfglot == null)
	 mfglot = "";
	mfglot = mfglot.trim();

	String radianpo = request.getParameter("radianpo");
	if (radianpo == null)
	 radianpo = "";
	radianpo = radianpo.trim();

	String order_text = request.getParameter("nodaysold");
	if (order_text == null)
	 order_text = "";
	order_text = order_text.trim();

	String trnasondate = request.getParameter("trnasondate");
	if (trnasondate == null)
	 trnasondate = "";
	trnasondate = trnasondate.trim();

	String sortby = request.getParameter("sortby");
	if (sortby == null)
	 sortby = "";
	sortby = sortby.trim();

	try {
	 Hashtable hub_list_set = new Hashtable();
	 hub_list_set = (Hashtable) invsession.getAttribute("HUB_PREF_LIST");

	 if (User_Action.equalsIgnoreCase("Search")) {
		//Hashtable iniinvendata = (Hashtable) invsession.getAttribute("INVENGRP_DATA");
		buildHeader(Search_Item, hub_list_set, branch_plant, SearchType, receiptid, mfglot,
		 mrnumber, radianpo, order_text, trnasondate, hubInventoryGroupOvBeanCollection, invengrp, sortby);
		buildDetails(iscollection,Search_Item, branch_plant, SearchType, receiptid, mfglot, mrnumber,
		 radianpo, order_text, trnasondate, invengrp, sortby);
		hub_list_set = null;
	 }
	 else if (User_Action.equalsIgnoreCase("binHistory")) {
		showBinHistory(receiptid);
	 }
	 else {
		String CompanyID = invsession.getAttribute("COMPANYID") == null ? "" :
		 invsession.getAttribute("COMPANYID").toString();
		if (order_text.trim().length() < 1) {
		 order_text = "60";
		}

		/*Hashtable initialinvData = new Hashtable();
		Hashtable hub_list1 = (Hashtable) (hub_list_set.get("HUB_LIST"));
		if (hub_list1.size() > 0) {
		 initialinvData = radian.web.poHelpObj.getinvgrpInitialData(db, hub_list_set);
		 invsession.setAttribute("INVENGRP_DATA", initialinvData);
		}*/

		buildHeader(Search_Item, hub_list_set, branch_plant, SearchType, receiptid, mfglot,
		mrnumber, radianpo, order_text, trnasondate, hubInventoryGroupOvBeanCollection, invengrp, sortby);
		out.println(radian.web.HTMLHelpObj.printHTMLSelect());
		hub_list_set = null;
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	}
	finally {
	 out.close();
	}
 }

	private void showBinHistory( String receiptId )
	{
	  out.println( radian.web.HTMLHelpObj.printHTMLHeader( "receiptbinhistory.binhistory","",intcmIsApplication,res ) );
	  out.println( "<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  out.println( "</HEAD>\n" );
	  out.println( "<BODY>\n" );
	  out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( res.getString("receiptbinhistory.binhistory") ) );

	  out.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  out.println( "<tr align=\"center\" valign=\"middle\">\n" );
	  out.println( "<TH WIDTH=\"5%\"  HEIGHT=\"25\">"+res.getString("receiptbinhistory.binhistory")+"</TH>\n" );
	  out.println( "</tr>\n" );

	  DBResultSet dbrs=null;
	  ResultSet searchRs=null;
	  int count=0;
	  try
	  {
		String query="select 'Receipt_id '||Receipt_id||' was at '||bin||' before '|| to_char(max(TIME_STAMP),'mm/dd/yyyy hh24:mi PM') bin_history ";
		query+=" from receipt_audit where receipt_id = " + receiptId + " group by bin,Receipt_id order by max(TIME_STAMP) desc";

		dbrs=db.doQuery( query );
		searchRs=dbrs.getResultSet();
		while ( searchRs.next() )
		{
		  out.println( "<tr align=\"center\" valign=\"middle\">\n" );
		  count++;
		  String Color=" ";
		  if ( count % 2 == 0 )
		  {
			Color="CLASS=\"blue";
		  }
		  else
		  {
			Color="CLASS=\"white";
		  }
		  String binHistory=searchRs.getString( "BIN_HISTORY" ) == null ? "" : searchRs.getString( "BIN_HISTORY" );
		  out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + binHistory + "</td>\n" );
		  out.println( "</tr>\n" );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
	  }
	}

    public void buildDetails(String searchString,String item_search,String branchPlant,String SearchType,
                                      String receiptid,String mfglot,PrintWriter trdout )
    {
	  out = trdout;
      buildDetails(false,item_search,branchPlant,SearchType,receiptid,mfglot,"","N","","","","RECEIPT_ID" );
      return ;
    }

    //Do Search. Searches the database and returns a vector of hashtabels containung each row.
    public void buildDetails(boolean iscollection,String item_search,String branchPlant,String SearchType,
                                      String receiptid,String mfglot,String mrnumber,String radianpo,String order_text,String trnasondate,String selinvgrp,String sortBy )
    {
      Vector data=new Vector();

      try
      {
        data=getResult( iscollection,item_search,branchPlant,SearchType,receiptid,mfglot,mrnumber,radianpo,order_text,trnasondate,selinvgrp,sortBy );
      }
      catch ( Exception e1 )
      {
        e1.printStackTrace();
        out.println(radian.web.HTMLHelpObj.printHTMLError());
        return;
      }

      try
      {
        int total=data.size();

        if ( total == 0 )
        {
          out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
          out.println( "<TR align=\"center\" valign=\"middle\">\n" );
          out.println( "<TD width=\"5%\">\n" );
          out.println( "</TD><TD ALIGN=\"CENTER\">\n" );
          out.println( "<BR><BR>"+res.getString("label.norecordfoundor")+"</TD></TR>" );
          out.println( "</table>\n" );
          out.println( "</body></html>\n" );
          return;
        }
        out.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

        int i=0; //used for detail lines
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
            out.println( "<tr align=\"center\" valign=\"middle\">\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.type")+"</TH>\n" );
			out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.inventorygroup")+"</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.receiptid")+"</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("transactions.receivername")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("transactions.source")+"</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("transactions.picklistid")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("transactions.issuername")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("transactions.transferdestination")+"</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.itemid")+"</TH>\n" );
  //        out.println("<TH width=\"5%\"  height=\"38\">Catalog Part No</TH>\n");
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.quantity")+"</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.lotstatus")+"</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.mfglot")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.bin")+"</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("receivedreceipts.label.dor")+"</TH>\n" );
			out.println("<TH width=\"5%\"  height=\"38\">"+res.getString("transactions.storagetemp")+"</TH>\n");
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("transactions.receivedpicked")+"</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.delivered")+"</TH>\n" );
            out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("label.podaskline")+"</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">"+res.getString("label.mrline")+"</TH>\n" );
			if (this.getupdateStatus() && !"N".equalsIgnoreCase( radianpo ) )
			{
			  out.println( "<TH width=\"5%\" COLSPAN =\"2\" height=\"38\">"+res.getString("transactions.receiptnotes")+"</TH>\n" );
			}
			else
			{
			  out.println( "<TH width=\"5%\" height=\"38\">"+res.getString("transactions.receiptnotes")+"</TH>\n" );
			}
            //out.println("<TH width=\"5%\"  height=\"38\">Line Item</TH>\n");
            //out.println("<TH width=\"5%\"  height=\"38\">Date Delivered</TH>\n");
            out.println( "</tr>\n" );
          }

          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) data.elementAt( loop );

          String Color=" ";
          if ( i % 2 == 0 )
          {
            Color="CLASS=\"blue";
          }
          else
          {
            Color="CLASS=\"white";
          }

          out.println( "<tr align=\"center\"  valign=\"middle\">\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "TRANSACTION_TYPE" ).toString() + "</td>\n" );
		  String inventoryGroupName = hD.get("INVENTORY_GROUP_NAME")==null?"":hD.get("INVENTORY_GROUP_NAME").toString();
		  out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + inventoryGroupName + "</td>\n" );

          if ( "N".equalsIgnoreCase( radianpo ) )
          {
            out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "RECEIPT_ID" ).toString() + "</td>\n" );
          }
          else
          {
            out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">\n" );
            out.println( "<A HREF=\"javascript:doPrintrelabel('" + hD.get( "RECEIPT_ID" ).toString() + "')\">" + hD.get( "RECEIPT_ID" ).toString() + "</A></td>\n" );
          }

          out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "RECEIVER_NAME" ).toString() + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "TRANSFERRED_FROM" ).toString() + "</td>\n" );

          String picklistid=hD.get( "PICKLIST_ID" ).toString();
		  String prbatch=hD.get( "BATCH" ).toString();

		  if (prbatch.length() < 1 && picklistid.length() > 0)
		  {
			prbatch = picklistid;
		  }

          if ( picklistid.trim().length() > 0 )
          {
            picklistid="PL " + picklistid;
          }

		  //String tmpstortmp=hD.get( "STORAGE_TEMP" ).toString();
		  String storageTemp=hD.get( "LABEL_STORAGE_TEMP" ).toString();

		  /*if ("2118".equalsIgnoreCase(branchPlant))
		  {
			storageTemp = tmpstortmp;
		  }*/

          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + picklistid + "</td>\n" );
          //out.println("<td "+Color+"\" width=\"5%\" height=\"38\">" +hD.get("ISSUE_ID").toString()+ "</td>\n");
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "ISSUER_NAME" ).toString() + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "TRANSFERRED_TO" ).toString() + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "ITEM_ID" ).toString() + "</td>\n" );
          //out.println("<td "+Color+"\" width=\"5%\" height=\"38\">" +hD.get("CAT_PART_NO").toString()+ "</td>\n");
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "QUANTITY" ).toString() + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "LOT_STATUS" ).toString() + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "MFG_LOT" ).toString() + "</td>\n" );
		  if ( "N".equalsIgnoreCase( radianpo ) || hD.get( "BIN" ).toString().length() == 0)
          {
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "BIN" ).toString() + "</td>\n" );
		  }
		  else
		  {
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><A HREF=\"javascript:showBinHistory('" + hD.get( "RECEIPT_ID" ).toString() + "')\">" + hD.get( "BIN" ).toString() + "</A></td>\n" );
		  }
          out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "DATE_OF_RECEIPT" ).toString() + "</td>\n" );
		  out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + storageTemp + "</td>\n" );
          out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "TRANSACTION_DATE" ).toString() + "</td>\n" );
          out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "DATE_DELIVERED" ).toString() + "</td>\n" );
          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "RADIAN_PO" ).toString() + "-" + hD.get( "PO_LINE" ).toString() + "</td>\n" );
          //out.println("<td "+Color+"\" width=\"5%\" height=\"38\">" +hD.get("PO_LINE").toString()+ "</td>\n");
          //out.println("<td "+Color+"\" width=\"5%\" height=\"38\">" +hD.get("RECEIVER_ID").toString()+ "</td>\n");
		  String prnumber=hD.get( "PR_NUMBER" ).toString();
		  String prlineitem=hD.get( "LINE_ITEM" ).toString();


		  if ( "N".equalsIgnoreCase( radianpo ) || !(prnumber.length() > 0 && prlineitem.length() > 0 && prbatch.length() > 0) )
          {
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + prnumber + "-" +prlineitem+"</td>\n" );
		  }
		  else
		  {
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">\n" );
			out.println( "<A HREF=\"javascript:doPrintbol('" + prnumber + "','" +prlineitem + "','" + prbatch + "')\">" + hD.get( "PR_NUMBER" ).toString() + "-" + hD.get( "LINE_ITEM" ).toString() +"</A></td>\n" );
		  }

          out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + hD.get( "NOTES" ).toString() + "</td>\n" );

		  if (this.getupdateStatus() && !"N".equalsIgnoreCase( radianpo ) )
		  {
			out.println("<TD "+Color+"\" width=\"1%\">\n");
			out.println( "<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"receiptNotes\" value=\"...\" OnClick=\"getReceiptnotes('" + hD.get( "RECEIPT_ID" ).toString() + "')\"><IMG src=\"/images/ponotes.gif\" alt=\"RECEIPT_NOTES\"></BUTTON>\n" );
   		    out.println( "</TD>\n" );
		  }

          //out.println("<td "+Color+"\" width=\"5%\" height=\"38\"><FONT FACE=\"Arial\"  SIZE=\"2\">" +hD.get("DATE_DELIVERED").toString()+ "</td>\n");
          out.println( "</tr>\n" );
        }

        out.println( "</table>\n" );
        out.println( "</body></html>\n" );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println(radian.web.HTMLHelpObj.printHTMLError(res));
        return;
      }
	  data = null;
      return;
    }

  private void buildHeader(String Item,Hashtable hub_list_set,String hub_branch_ID,String SType,String receiptid,String mfglot,String mrNumber,String radianPo,
						   String nodaysago,String trnasondate,Collection hubInventoryGroupOvBeanCollection,String selinvengrp,String sortby)
 {
  String checkedol="";
  checkedol="checked";

  out.println( radian.web.HTMLHelpObj.printHTMLHeader("title.invtranc","inventransaction.js",intcmIsApplication,res ) );
  out.println( "<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
  out.println( "</HEAD>\n" );
  out.println( "<BODY>\n" );

  out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
  out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT><center>\n" );
  out.println( "</DIV>\n" );
  out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

  String Search_servlet=bundle.getString( "INVENTORY_TRANSACTION" );
  out.println( radian.web.HTMLHelpObj.PrintTitleBar( res.getString("transfertransactions.title")) );
  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
  if (hub_list.size() < 1)
  { 
	out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>"+res.getString("msg.noaccess")));
	return;
  }

  out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
  //out.println(radian.web.poHelpObj.createhubinvgrjs(initialinvData));
	try {
	 out.println(radian.web.poHelpObj.getHubInventoryGroupJavaScript(
		hubInventoryGroupOvBeanCollection, true, false));
	}
	catch (Exception ex) {
	}
  out.println("// -->\n </SCRIPT>\n");

  out.println( "<FORM METHOD=\"POST\" NAME=\"InvenTrans\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n" );
  out.println( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
  //Hub
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>"+res.getString("label.hub")+":</B>&nbsp;\n" );
  out.println( "</TD>\n" );
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"15%\" ALIGN=\"LEFT\" >\n" );
  out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" OnChange=\"hubchanged(document.InvenTrans.HubName)\" size=\"1\">\n" );
  //Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
  Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
  if ( hub_branch_ID.trim().length() == 0 )
  {
	//hub_branch_ID=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
	hub_branch_ID=radian.web.HTMLHelpObj.getFirstBranchPlant( hubInventoryGroupOvBeanCollection );
  }
  //out.println(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),hub_branch_ID,hub_list));
	out.println(radian.web.HTMLHelpObj.getHubDropDown(hubInventoryGroupOvBeanCollection,hub_branch_ID));
  out.println( "</SELECT>\n" );
  out.println( "</TD>\n" );
  /*//part
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\"  ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>Part #:</B>&nbsp;\n" );
  out.println( "</TD>\n<TD HEIGHT=\"38\"ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"Search\" value=\"" + Search + "\" size=\"10\">\n" );*/

  out.println( "</TD>\n" );
  //receipt Id
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\"  ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>"+res.getString("label.receiptid")+":</B>&nbsp;\n" );
  out.println( "</TD>\n<TD HEIGHT=\"38\"ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"receiptid\" value=\"" + receiptid + "\" size=\"6\">\n" );
  out.println( "</TD>\n" );

  //Mr Number
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>"+res.getString("label.mr")+":</B>\n" );
  out.println( "</TD>\n<TD HEIGHT=\"38\"ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"mrnumber\" value=\"" + mrNumber + "\" size=\"6\">\n" );
  out.println( "</TD>\n" );

  out.println( "<TD WIDTH=\"8%\" ALIGN=\"RIGHT\">&nbsp;</TD>\n" );

  out.println( "<TD HEIGHT=\"38\"WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
  out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.search")+"\" NAME=\"SearchButton\">\n" );
  out.println( "</TD>\n</TR>\n" );

  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
  // Inventory Group
  out.println( "<TD WIDTH=\"8%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>"+res.getString("label.invgrp")+":</B>&nbsp;\n" );
  out.println( "</TD>\n" );
  out.println( "<TD WIDTH=\"15%\">\n" );

  out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n" );
	out.println(radian.web.HTMLHelpObj.getInventoryGroupDropDown(hubInventoryGroupOvBeanCollection,hub_branch_ID,selinvengrp,false));

/*  Hashtable fh= ( Hashtable ) initialinvData.get( hub_branch_ID );
  Vector invidV= ( Vector ) fh.get( "INVENTORY_GROUP" );
  Vector invidName = ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );
  //System.out.println( selinvengrp );
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

  //Item Id
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\"  ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>"+res.getString("label.itemid")+":</B>&nbsp;\n" );
  out.println( "</TD>\n" );

  out.println( "<TD HEIGHT=\"38\"ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"Item\" value=\"" + Item + "\" size=\"10\">\n" );
  out.println( "</TD>\n" );

  //Mgf Lot
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\"  ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>"+res.getString("label.mfglot")+" :</B>&nbsp;\n" );
  out.println( "</TD>\n<TD HEIGHT=\"38\"ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"mfglot\" value=\"" + mfglot + "\" size=\"6\">\n" );
  out.println( "</TD>\n" );

  //Radian Po
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>"+res.getString("label.po")+":</B>\n" );
  out.println( "</TD>\n<TD HEIGHT=\"38\"ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"radianpo\" value=\"" + radianPo + "\" size=\"6\">\n" );
  out.println( "</TD>\n" );

  out.println( "</TR>\n" );

  out.println( "<TR>\n" );
  // Trans type
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>"+res.getString("transactions.transtype")+":</B>&nbsp;\n" );
  out.println( "</TD>\n" );
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"15%\" ALIGN=\"LEFT\">\n" );
  out.println( "<SELECT CLASS=\"HEADER\" NAME=\"Type\" size=\"1\">\n" );

  if ( SType.equalsIgnoreCase( "OV" ) )
  {
	out.println( "<option  value=\"All\">"+res.getString("label.all")+"</option>\n" );
	out.println( "<option  selected value=\"OV\">"+res.getString("graphs.label.receipts")+"</option>\n" );
	out.println( "<option  value=\"IT\">"+res.getString("label.transfers")+"</option>\n" );
	out.println( "<option  value=\"RI\">"+res.getString("graphs.label.issues")+"</option>\n" );
  }
  else if ( SType.equalsIgnoreCase( "IT" ) )
  {
	out.println( "<option value=\"All\">"+res.getString("label.all")+"</option>\n" );
	out.println( "<option value=\"OV\">"+res.getString("graphs.label.receipts")+"</option>\n" );
	out.println( "<option selected value=\"IT\">"+res.getString("label.transfers")+"</option>\n" );
	out.println( "<option value=\"RI\">"+res.getString("graphs.label.issues")+"</option>\n" );
  }
  else if ( SType.equalsIgnoreCase( "RI" ) )
  {
	out.println( "<option  value=\"All\">"+res.getString("label.all")+"</option>\n" );
	out.println( "<option  value=\"OV\">"+res.getString("graphs.label.receipts")+"</option>\n" );
	out.println( "<option  value=\"IT\">"+res.getString("label.transfers")+"</option>\n" );
	out.println( "<option  selected value=\"RI\">"+res.getString("graphs.label.issues")+"</option>\n" );
  }
  else
  {
	out.println( "<option selected value=\"All\">"+res.getString("label.all")+"</option>\n" );
	out.println( "<option  value=\"OV\">"+res.getString("graphs.label.receipts")+"</option>\n" );
	out.println( "<option  value=\"IT\">"+res.getString("label.transfers")+"</option>\n" );
	out.println( "<option  value=\"RI\">"+res.getString("graphs.label.issues")+"</option>\n" );
  }
  out.println( "</SELECT>\n" );
  out.println( "</TD>\n\n" );

  //On Date
  out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\"  ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
  out.println( "<B>"+res.getString("label.html.datebrformat")+"</B>&nbsp;\n" );
  out.println( "</TD>\n" );

  out.println( "<TD HEIGHT=\"38\"ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"trnasondate\" value=\"" + trnasondate + "\" size=\"10\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.InvenTrans.trnasondate);\">&diams;</a></FONT>\n" );
  out.println( "</TD>\n" );

  //Days
 out.println( "<TD WIDTH=\"8%\" ALIGN=\"RIGHT\">"+res.getString("label.within")+"</TD>\n" );
 out.println( "<TD HEIGHT=\"38\"WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
 out.println( "<INPUT CLASS=\"HEADER\" type=\"text\" name=\"nodaysold\" value=\"" + ( checkedol == null ? "60" : nodaysago ) + "\" SIZE=\"2\" MAXLENGTH=\"4\">\n" );
 out.println( "&nbsp;&nbsp;Days</TD>\n" );

 // Sort By
 out.println( "<TD HEIGHT=\"38\"WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
 out.println( "<B>"+res.getString("label.sortby")+":</B>&nbsp;\n" );
 out.println( "</TD>\n" );
 out.println( "<TD HEIGHT=\"38\"WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
 out.println( "<SELECT CLASS=\"HEADER\" NAME=\"sortby\" size=\"1\">\n" );
 Hashtable searchthese=new Hashtable();
 searchthese.put( res.getString("label.dor"),"DATE_OF_RECEIPT" );
 searchthese.put( res.getString("transactions.transactiondate"),"TRANSACTION_DATE" );
 searchthese.put( res.getString("transactions.expiredate"),"EXPIRE_DATE" );
 searchthese.put( res.getString("label.po"),"RADIAN_PO" );
 searchthese.put( res.getString("label.itemid"),"ITEM_ID" );
 searchthese.put( res.getString("label.receiptid"),"RECEIPT_ID" );
 out.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( searchthese.entrySet(),sortby ) );
 out.println( "</SELECT>\n" );
 out.println( "</TD>\n\n" );

  out.println( "</TR></TABLE>\n" );

  out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n" );

  return;
}

    private Vector getResult( boolean iscollection,String itemsea,String branchPlant,String sType,String receiptid,String mfglot,String mrNumber,
                              String radianpo,String nodaysago,String trnasondate,String inventoryGroup,String sortby)
       throws Exception
    {
      Vector Data=new Vector();
      Hashtable DataH=new Hashtable();
      boolean flagforwhere=false;
      DBResultSet dbrs=null;
      ResultSet rs=null;

      try
      {
        //String query = "select * from receipt_and_issue_view where CAT_PART_NO like '%"+Search_String.trim()+"%'";
        String query="select INVENTORY_GROUP_NAME,LABEL_STORAGE_TEMP,INVENTORY_GROUP,BATCH,LOT_STATUS,NOTES,ISSUER_NAME,RECEIVER_NAME,RECEIPT_ID,ISSUE_ID,ITEM_ID,QUANTITY,MFG_LOT,BIN,to_char(TRANSACTION_DATE,'mm/dd/yyyy hh24:mi') TRANSACTION_DATE,";
        query+="to_char(DATE_OF_RECEIPT,'mm/dd/yyyy') DATE_OF_RECEIPT,PICKLIST_ID,TRANSACTION_TYPE,TRANSFERRED_FROM,TRANSFERRED_TO,RADIAN_PO,PO_LINE,DATE_OF_MANUFACTURE,RECEIVER_ID,PR_NUMBER,LINE_ITEM,to_char(DATE_DELIVERED,'mm/dd/yyyy') DATE_DELIVERED";
        //query += " from receipt_and_issue_view where ";
        query+=" from transaction_tracking_view where "; //Trong 11-22-02

				if ( inventoryGroup != null && inventoryGroup.length() >0 &&
				 !inventoryGroup.equalsIgnoreCase( "ALL" ) )
				{
						if ( iscollection )
						{
						 query += " INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_COLLECTION where HUB = '" + branchPlant +
							"' and INVENTORY_GROUP_COLLECTION = '" + inventoryGroup +"') ";
						 flagforwhere=true;
						}
						else if ( inventoryGroup.length() > 0 )
						{
						 query+=" INVENTORY_GROUP ='" + inventoryGroup + "' ";
						 flagforwhere=true;
						}
				}

		/*if (selinvengrp.length() > 0 && !"All".equalsIgnoreCase(selinvengrp))
		{
		  if ( flagforwhere )
			query+="and INVENTORY_GROUP ='" + selinvengrp + "' ";
		  else
		  {
			query+=" INVENTORY_GROUP ='" + selinvengrp + "' ";
			flagforwhere=true;
		  }
		}*/

        if ( itemsea.length() > 0 )
        {
		  if ( flagforwhere )
          query+=" and ITEM_ID = " + itemsea.trim() + " ";
		  else
		  {
			query+="ITEM_ID = " + itemsea.trim() + " ";
			flagforwhere=true;
		  }
        }

		//nodaysago=order_text;
		if ( nodaysago.trim().length() > 0 )
		{
		  if ( flagforwhere )
		  {
			query+="and TRANSACTION_DATE > sysdate - " + nodaysago + " ";
			flagforwhere=true;
		  }
		  else
		  {
			query+="TRANSACTION_DATE > sysdate - " + nodaysago + " ";
			flagforwhere=true;
		  }
		}

        if ( ! ( sType.equalsIgnoreCase( "All" ) ) )
        {
          if ( flagforwhere )
            query+="and TRANSACTION_TYPE = '" + sType + "' ";
          else
          {
            query+=" TRANSACTION_TYPE = '" + sType + "' ";
            flagforwhere=true;
          }
        }

        if ( receiptid.length() > 1 )
        {
          if ( flagforwhere )
            query+="and RECEIPT_ID = " + receiptid + " ";
          else
          {
            query+=" RECEIPT_ID = " + receiptid + " ";
            flagforwhere=true;
          }
        }

        if ( mfglot.length() > 0 )
        {
          if ( flagforwhere )
            query+="and lower(MFG_LOT) like lower('%" + mfglot + "%') ";
          else
          {
            query+=" lower(MFG_LOT) like lower('%" + mfglot + "%') ";
            flagforwhere=true;
          }
        }

        if ( mrNumber.length() > 1 )
        {
          if ( flagforwhere )
            query+="and PR_NUMBER like '%" + mrNumber + "%' ";
          else
          {
            query+=" PR_NUMBER like '%" + mrNumber + "%' ";
            flagforwhere=true;
          }
        }

        if ( radianpo.length() > 1 )
        {
          if ( flagforwhere )
            query+="and RADIAN_PO = " + radianpo + " ";
          else
          {
            query+=" RADIAN_PO = " + radianpo + " ";
            flagforwhere=true;
          }
        }

		if ( trnasondate.length() > 1 )
		{
		  String transdatesearch="TRANSACTION_DATE";
		  if ( sType.equalsIgnoreCase( "OV" ) )
		  {
			transdatesearch="DATE_OF_RECEIPT";
		  }
		  if ( sType.equalsIgnoreCase( "RI" ) )
		  {
			transdatesearch="SHIP_CONFIRM_DATE";
		  }

		  if ( flagforwhere )
			query+="and to_char("+transdatesearch+",'mm/dd/yyyy') = '" + trnasondate + "' ";
		  else
		  {
			query+=" to_char("+transdatesearch+",'mm/dd/yyyy') = '" + trnasondate + "' ";
			flagforwhere=true;
		  }
		}

        if ( flagforwhere )
        {
          query+=" and HUB = " + branchPlant + " ";
        }
        else
        {
          query+=" rownum = 0";
        }

	    query+=" order by "+ sortby;
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          DataH=new Hashtable();
          DataH.put( "ISSUER_NAME",rs.getString( "ISSUER_NAME" ) == null ? "&nbsp;" : rs.getString( "ISSUER_NAME" ) );
          DataH.put( "RECEIVER_NAME",rs.getString( "RECEIVER_NAME" ) == null ? "&nbsp;" : rs.getString( "RECEIVER_NAME" ) );
          DataH.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? "&nbsp;" : rs.getString( "RECEIPT_ID" ) );
          DataH.put( "ISSUE_ID",rs.getString( "ISSUE_ID" ) == null ? "&nbsp;" : rs.getString( "ISSUE_ID" ) );
          DataH.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "&nbsp;" : rs.getString( "ITEM_ID" ) );
          //DataH.put("CAT_PART_NO",rs.getString("CAT_PART_NO")==null?"&nbsp;":rs.getString("CAT_PART_NO"));
          DataH.put( "QUANTITY",rs.getString( "QUANTITY" ) == null ? "&nbsp;" : rs.getString( "QUANTITY" ) );
          DataH.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? "&nbsp;" : rs.getString( "MFG_LOT" ) );
          DataH.put( "BIN",rs.getString( "BIN" ) == null ? "&nbsp;" : rs.getString( "BIN" ) );
          DataH.put( "TRANSACTION_DATE",rs.getString( "TRANSACTION_DATE" ) == null ? "&nbsp;" : rs.getString( "TRANSACTION_DATE" ) );
          DataH.put( "RADIAN_PO",rs.getString( "RADIAN_PO" ) == null ? "&nbsp;" : rs.getString( "RADIAN_PO" ) );
          DataH.put( "PO_LINE",rs.getString( "PO_LINE" ) == null ? "&nbsp;" : rs.getString( "PO_LINE" ) );
          DataH.put( "RECEIVER_ID",rs.getString( "RECEIVER_ID" ) == null ? "&nbsp;" : rs.getString( "RECEIVER_ID" ) );
          DataH.put( "PR_NUMBER",rs.getString( "PR_NUMBER" ) == null ? "" : rs.getString( "PR_NUMBER" ) );
          DataH.put( "LINE_ITEM",rs.getString( "LINE_ITEM" ) == null ? "" : rs.getString( "LINE_ITEM" ) );
          DataH.put( "DATE_DELIVERED",rs.getString( "DATE_DELIVERED" ) == null ? "&nbsp;" : rs.getString( "DATE_DELIVERED" ) );
          DataH.put( "TRANSACTION_TYPE",rs.getString( "TRANSACTION_TYPE" ) == null ? "&nbsp;" : rs.getString( "TRANSACTION_TYPE" ) );
          DataH.put( "TRANSFERRED_FROM",rs.getString( "TRANSFERRED_FROM" ) == null ? "&nbsp;" : rs.getString( "TRANSFERRED_FROM" ) );
          DataH.put( "TRANSFERRED_TO",rs.getString( "TRANSFERRED_TO" ) == null ? "&nbsp;" : rs.getString( "TRANSFERRED_TO" ) );
          DataH.put( "PICKLIST_ID",rs.getString( "PICKLIST_ID" ) == null ? "" : rs.getString( "PICKLIST_ID" ) );
          DataH.put( "DATE_OF_RECEIPT",rs.getString( "DATE_OF_RECEIPT" ) == null ? "&nbsp;" : rs.getString( "DATE_OF_RECEIPT" ) );
          DataH.put( "NOTES",rs.getString( "NOTES" ) == null ? "&nbsp;" : rs.getString( "NOTES" ) );
          DataH.put( "LOT_STATUS",rs.getString( "LOT_STATUS" ) == null ? "&nbsp;" : rs.getString( "LOT_STATUS" ) );
		  DataH.put( "BATCH",rs.getString( "BATCH" ) == null ? "" : rs.getString( "BATCH" ) );
		  DataH.put("LABEL_STORAGE_TEMP",rs.getString("LABEL_STORAGE_TEMP")==null?"":rs.getString("LABEL_STORAGE_TEMP"));
		  //DataH.put("STORAGE_TEMP1",rs.getString("STORAGE_TEMP1")==null?"":rs.getString("STORAGE_TEMP1"));
		  DataH.put( "INVENTORY_GROUP_NAME",rs.getString( "INVENTORY_GROUP_NAME" ) == null ? "" : rs.getString( "INVENTORY_GROUP_NAME" ) );

          Data.addElement( DataH );
        }

      }
      catch ( Exception e )
      {
        e.printStackTrace();
        throw e;
      }
      finally
      {
       if (dbrs!= null) {dbrs.close();}
      }
      return Data;
    }

	public void buildtransDetails( String transreqid,PrintWriter trdout )
	{
	  Vector data=new Vector();

	  try
	  {
		data=gettransreqResult( transreqid );
	  }
	  catch ( Exception e1 )
	  {
		e1.printStackTrace();
		trdout.println(radian.web.HTMLHelpObj.printHTMLError(res));
	  }

	  try
	  {
		int total=data.size();

		if ( total == 0 )
		{
		  trdout.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
		  trdout.println("<TR align=\"center\" valign=\"middle\">\n" );
		  trdout.println("<TD width=\"5%\">\n" );
		  trdout.println("</TD><TD ALIGN=\"CENTER\">\n" );
		  trdout.println("<BR><BR>"+res.getString("label.norecordsfound")+"</TD></TR>" );
		  trdout.println("</table>\n" );
		  trdout.println("</body></html>\n" );
		  //return Msg;
		}
		trdout.println("<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

		int i=0; //used for detail lines
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
			trdout.println("<tr align=\"center\" valign=\"middle\">\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.type")+"</TH>\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.itemid")+"</TH>\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.receiptid")+"</TH>\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.originalreceiptid")+"</TH>\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.quantity")+"</TH>\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.mfglot")+"</TH>\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.bin")+"</TH>\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.delivered")+"</TH>\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.qcdate")+"</TH>\n" );
			trdout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.hub")+"</TH>\n" );
			trdout.println("</tr>\n" );
		  }

		  Hashtable hD=new Hashtable();
		  hD= ( Hashtable ) data.elementAt( loop );

		  String transfer_request_id = hD.get("TRANSFER_REQUEST_ID")==null?"&nbsp;":hD.get("TRANSFER_REQUEST_ID").toString();
		  String doc_type = hD.get("DOC_TYPE")==null?"&nbsp;":hD.get("DOC_TYPE").toString();
		  String receipt_id = hD.get("RECEIPT_ID")==null?"&nbsp;":hD.get("RECEIPT_ID").toString();
		  String quantity = hD.get("QUANTITY")==null?"&nbsp;":hD.get("QUANTITY").toString();
		  String mfg_lot = hD.get("MFG_LOT")==null?"&nbsp;":hD.get("MFG_LOT").toString();
		  String bin = hD.get("BIN")==null?"&nbsp;":hD.get("BIN").toString();
		  String date_delivered = hD.get("DATE_DELIVERED")==null?"&nbsp;":hD.get("DATE_DELIVERED").toString();
		  String transaction_date = hD.get("TRANSACTION_DATE")==null?"&nbsp;":hD.get("TRANSACTION_DATE").toString();
		  String item_id = hD.get("ITEM_ID")==null?"&nbsp;":hD.get("ITEM_ID").toString();
		  String hub = hD.get("HUB")==null?"&nbsp;":hD.get("HUB").toString();
		  String qc_date = hD.get("QC_DATE")==null?"&nbsp;":hD.get("QC_DATE").toString();
		  String origreciptid = hD.get("ORIGINAL_RECEIPT_ID")==null?"&nbsp;":hD.get("ORIGINAL_RECEIPT_ID").toString();

		  String Color=" ";
		  if ( i % 2 == 0 )
		  {
			Color="CLASS=\"blue";
		  }
		  else
		  {
			Color="CLASS=\"white";
		  }

		  trdout.println("<tr align=\"center\"  valign=\"middle\">\n" );

		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + doc_type + "</td>\n" );
		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + item_id + "</td>\n" );
		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + receipt_id + "</td>\n" );
		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + origreciptid + "</td>\n" );
		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + quantity + "</td>\n" );
		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + mfg_lot + "</td>\n" );
		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + bin + "</td>\n" );
		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + date_delivered + "</td>\n" );
		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + qc_date + "</td>\n" );
		  trdout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + hub + "</td>\n" );

		  trdout.println("</tr>\n" );
		}

		trdout.println("</table>\n" );
		trdout.println("</body></html>\n" );
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		trdout.println(radian.web.HTMLHelpObj.printHTMLError(res));
	  }
	  return;
	}

	private Vector gettransreqResult( String transreqid )  throws Exception
	{
	  Vector Data=new Vector();
	  Hashtable DataH=new Hashtable();
	  String nodaysago="";
	  boolean flagforwhere=false;
	  DBResultSet dbrs=null;
	  ResultSet searchRs=null;

	  try
	  {
		String query="select ORIGINAL_RECEIPT_ID,TRANSFER_REQUEST_ID, DOC_TYPE, RECEIPT_ID, QUANTITY, MFG_LOT, BIN, to_char(DATE_DELIVERED,'mm/dd/yyyy') DATE_DELIVERED, to_char(TRANSACTION_DATE,'mm/dd/yyyy hh24:mi') TRANSACTION_DATE, ITEM_ID, HUB, ";
		query+="to_char(QC_DATE,'mm/dd/yyyy hh24:mi') QC_DATE";
		query+=" from inventory_transfer_detail_view where ";
		query+="TRANSFER_REQUEST_ID = " + transreqid.trim() + " ";
		query+=" order by TRANSACTION_DATE asc";

		dbrs=db.doQuery( query );
		searchRs=dbrs.getResultSet();

		while ( searchRs.next() )
		{
		  DataH=new Hashtable();
		  DataH.put("TRANSFER_REQUEST_ID",searchRs.getString("TRANSFER_REQUEST_ID")==null?"":searchRs.getString("TRANSFER_REQUEST_ID"));
		  DataH.put("DOC_TYPE",searchRs.getString("DOC_TYPE")==null?"":searchRs.getString("DOC_TYPE"));
		  DataH.put("RECEIPT_ID",searchRs.getString("RECEIPT_ID")==null?"":searchRs.getString("RECEIPT_ID"));
		  DataH.put("QUANTITY",searchRs.getString("QUANTITY")==null?"":searchRs.getString("QUANTITY"));
		  DataH.put("MFG_LOT",searchRs.getString("MFG_LOT")==null?"":searchRs.getString("MFG_LOT"));
		  DataH.put("BIN",searchRs.getString("BIN")==null?"":searchRs.getString("BIN"));
		  DataH.put("DATE_DELIVERED",searchRs.getString("DATE_DELIVERED")==null?"":searchRs.getString("DATE_DELIVERED"));
		  DataH.put("TRANSACTION_DATE",searchRs.getString("TRANSACTION_DATE")==null?"":searchRs.getString("TRANSACTION_DATE"));
		  DataH.put("ITEM_ID",searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID"));
		  DataH.put("HUB",searchRs.getString("HUB")==null?"":searchRs.getString("HUB"));
		  DataH.put("QC_DATE",searchRs.getString("QC_DATE")==null?"":searchRs.getString("QC_DATE"));
		  DataH.put("ORIGINAL_RECEIPT_ID",searchRs.getString("ORIGINAL_RECEIPT_ID")==null?"":searchRs.getString("ORIGINAL_RECEIPT_ID"));

		  Data.addElement( DataH );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		throw e;
	  }
	  finally
	  {
	   if (dbrs!= null) {dbrs.close();}
	  }
	  return Data;
	}
  }