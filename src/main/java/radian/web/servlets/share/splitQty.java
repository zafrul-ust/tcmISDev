package radian.web.servlets.share;

//
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.math.BigDecimal;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 06-03-03 Cleanup code made changes to get vv_lot_status
 * 11-26-03 Can not split receipt to a pickable status if you don't have quality control permissions for that hub and inventory group
 * 05-02-06 - When a non certified person splits a receipt without changing the lot status I was not checking to make sure that the
 * lot status is non-pickable. This caused qulity controlled items to be split into Pickable statuses by a person who is not certified to do so
 * This has been fixed now.
 */

public class splitQty {
 private ServerResourceBundle bundle = null;
 private TcmISDBModel dbObj = null;
 private boolean allowupdate;

 public void setupdateStatus(boolean id) {
	this.allowupdate = id;
 }

 private boolean getupdateStatus() throws Exception {
	return this.allowupdate;
 }

 public splitQty(ServerResourceBundle b, TcmISDBModel d) {
	bundle = b;
	dbObj = d;
 }

 public void buildsplitQty(Hashtable all_bin_set, Vector all_status_set, String receiptId,
	String HubNo, String addBinid, String qtyToSplit, Hashtable initialinvData,
	Vector lotstatus_invengrps,
	String message,String netPendingAdj, PrintWriter out) {
	//String quantityOnHand = "";
	DBResultSet dbrs = null;
	ResultSet rs = null;
	out.println("<HTML>\n");
	out.println("<HEAD>\n");
	out.println("<TITLE>Split Quantity</TITLE>\n");
	out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
	out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
	out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
	out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
	out.println("<SCRIPT SRC=\"/clientscripts/receivingjs.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
	out.println("<SCRIPT SRC=\"/clientscripts/splitreceipt.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
	out.println("<SCRIPT SRC=\"/js/common/formchek.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
	out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
	String althubid = "var allowedinvgrps = new Array(";

	for (int h = 0; h < lotstatus_invengrps.size(); h++) {
	 String data1 = (String) lotstatus_invengrps.elementAt(h);
	 althubid += "\"" + data1 + "\"" + ",";
	}

	if (lotstatus_invengrps.size() > 0) {
	 althubid = althubid.substring(0, althubid.length() - 1) + ");\n";
	}
	else {
	 althubid += ");\n";
	}

	out.println(althubid);

	String pickablests = "var pickablestatus = new Array(";
	for (int i = 0; i < all_status_set.size(); i++) {
	 Hashtable data1 = (Hashtable) all_status_set.elementAt(i);
	 Enumeration E;
	 for (E = data1.keys(); E.hasMoreElements(); ) {
		String key = (String) E.nextElement();
		String keyvalue = data1.get(key).toString();
		if ("Y".equalsIgnoreCase(keyvalue)) {
		 pickablests += "\"" + key + "\"" + ",";
		}
	 }
	}
	pickablests = pickablests.substring(0, pickablests.length() - 1) + ");\n";
	out.println(pickablests);

	out.println("// -->\n </SCRIPT>\n");
	out.println("</HEAD>\n");
	out.println("<BODY>\n");
	out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
	out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
	out.println("</DIV>\n");
	out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");
	if (message.trim().length() > 0)
	{
	 out.println(radian.web.HTMLHelpObj.printMessageinTable("<B>" + message + "</B><BR>\n"));
  }
	out.println(radian.web.HTMLHelpObj.printMessageinTable("Original Receipt ID: <B>" + receiptId + "</B>\n"));

	String status = "";
	String Item_id = "";
	String Sel_bin = "";
	String inventorygrp = "";
	String selhub = "";
	String qualitycntitem = "";
	String quantityReceivedS = "";
	String quantityIssuedS = "";
  BigDecimal quantityReceived = new BigDecimal("0");
	BigDecimal quantityIssued = new BigDecimal("0");
	try {
	 dbrs = dbObj.doQuery("select x.*, fx_quality_control_item_id (x.item_id,x.inventory_group) QUALITY_CONTROL_ITEM from receipt x where receipt_id = " +
		receiptId + "");
	 rs = dbrs.getResultSet();
	 while (rs.next()) {
		status = rs.getString("LOT_STATUS") == null ? " " : rs.getString("LOT_STATUS");
		Sel_bin = rs.getString("BIN") == null ? " " : rs.getString("BIN");
		Item_id = rs.getString("ITEM_ID") == null ? " " : rs.getString("ITEM_ID");
		inventorygrp = rs.getString("INVENTORY_GROUP") == null ? " " :
		 rs.getString("INVENTORY_GROUP");
		selhub = rs.getString("HUB") == null ? " " : rs.getString("HUB");
		qualitycntitem = rs.getString("QUALITY_CONTROL_ITEM") == null ? "" : rs.getString("QUALITY_CONTROL_ITEM");
		quantityReceivedS = rs.getString("QUANTITY_RECEIVED") == null ? "" : rs.getString("QUANTITY_RECEIVED");
	  quantityIssuedS = rs.getString("TOTAL_QUANTITY_ISSUED") == null ? "" : rs.getString("TOTAL_QUANTITY_ISSUED");
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 return;
	}

  if (quantityIssuedS.trim().length() > 0)
	{
	 quantityIssued = new BigDecimal(quantityIssuedS);
	}
	quantityReceived = new BigDecimal(quantityReceivedS);
	BigDecimal quantityOnHand = quantityReceived.subtract(quantityIssued);

  //System.out.println("quantityIssued "+quantityIssuedS+"");
	//System.out.println("quantityReceived "+quantityReceivedS+"");

	out.println("<FORM METHOD=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\">\n");
	out.println("<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n");

	out.println("<TR VALIGN=\"MIDDLE\">\n");
	//Quantity Received
	out.println("<TD WIDTH=\"15%\"  ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	out.println("<B>Quantity on Hand: </B>&nbsp;\n");
	out.println("</TD>\n<TD  WIDTH=\"10%\">" + quantityOnHand + "</TD>\n");
	out.println("</TR>\n");

	out.println("<TR VALIGN=\"MIDDLE\">\n");
	out.println("<TD WIDTH=\"10%\"  ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	out.println("<B>Bin: </B>&nbsp;\n");
	out.println("<td width=\"10%\" ALIGN=\"LEFT\">\n");
	out.println("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"addBin\" value=\"+\" OnClick=\"showEmtyBins(" +
	 Item_id + ",''," + HubNo + ")\" >\n");
	out.println("<select name=\"selectElementBin\">\n");

     Hashtable in_bin_set = new Hashtable();
     try
     {
	  in_bin_set = radian.web.HTMLHelpObj.CreateBinForItem(dbObj, Item_id, selhub);
	 }
	 catch (Exception e) {
	   e.printStackTrace();
	 }

    //Hashtable in_bin_set = (Hashtable) all_bin_set.get(Item_id);
	int bin_size = in_bin_set.size();

	if (addBinid.length() > 0) {
	 out.println("<option selected value=\"" + addBinid + "\">" + addBinid + "</option>\n");
	 Sel_bin = addBinid;
	}
	//Select the last bin that was added for an item
	String bin_selected = "";

	for (int j = 0; j < bin_size; j++) {
	 Integer key = new Integer(j);
	 String bin_name = (String) in_bin_set.get(key);
	 bin_selected = "";
	 if (bin_name.equalsIgnoreCase(Sel_bin)) {
		bin_selected = "selected";
	 }
	 out.println("<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name +
		"</option>\n");
	}

	out.println("</TR>\n");
	out.println("<TR VALIGN=\"MIDDLE\">\n");
	out.println("<TD WIDTH=\"10%\"  ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	out.println("<B>Status: </B>&nbsp;\n");
	out.println("<td width=\"10%\">\n");
	out.println("<select name=\"selectElementStatus\" onChange=\"checkallowstatus()\">\n");
	//out.println( radian.web.HTMLHelpObj.getDropDown( all_status_set,status ) );
	out.println(radian.web.HTMLHelpObj.getlogisticsDropDown(all_status_set, status, true,null));
	out.println("</select>\n");
	out.println("<input type=\"hidden\" name=\"origlotstatus\" value=\"" + status + "\">\n");
	out.println("<input type=\"hidden\" name=\"qualitycntitem\" value=\"" + qualitycntitem + "\">\n");
	out.println("</TD>\n");
	out.println("</TR>\n");

	out.println("<TR VALIGN=\"MIDDLE\">\n");
	// Inventory Group
	out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	out.println("<B>Inv Grp:</B>&nbsp;\n");
	out.println("</TD>\n");
	out.println("<TD WIDTH=\"20%\">\n");

	out.println("<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\" onChange=\"checkallowstatus()\">\n");
	Hashtable fh = (Hashtable) initialinvData.get(selhub);
	Vector invidV = (Vector) fh.get("INVENTORY_GROUP");
    Vector invidName = ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );                           
    for (int i = 0; i < invidV.size(); i++) {
	 String preSelect = "";
	 String wacid = (String) invidV.elementAt(i);
     String invgName= ( String ) invidName.elementAt( i );
     if (inventorygrp.equalsIgnoreCase(wacid)) {
		preSelect = "selected";
	 }
	 if ("All".equalsIgnoreCase(wacid)) {
	 }
	 else {
		out.println("<option " + preSelect + " value=\"" + wacid + "\"> " + invgName +
		 "</option>\n");
	 }
	}
	out.println("</SELECT>\n");
	out.println("<input type=\"hidden\" name=\"originvgrp\" value=\"" + inventorygrp + "\">\n");
	out.println("</TD>\n");
	out.println("</TR>\n");

	out.println("<TR VALIGN=\"MIDDLE\">\n");
	//Quantity to split
	out.println("<TD WIDTH=\"10%\"  ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	out.println("<B>Qty to Split: </B>&nbsp;\n");
	out.println("</TD>\n<TD  WIDTH=\"10%\">\n");
	out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"splitqty\" id=\"splitqty\" VALUE=\"" +
	 qtyToSplit + "\" onChange=\"CheckSplitQtyValue(name,this)\" size=\"5\">\n");
	out.println("</TD>\n");
    out.println("</TR>\n");

    if (netPendingAdj.length() > 0)
    {
    out.println("<TR VALIGN=\"MIDDLE\">\n");
	//if to move the pending adjustment ot the new split receipt
	out.println("<TD WIDTH=\"10%\" COLSPAN=\"2\" ALIGN=\"Left\" CLASS=\"announce\">\n");
	out.println("<B>The original receipt has a pending adjustment, do you want to move the adjustment to the new receipt being created.</B>&nbsp;\n");
	out.println("<INPUT type=\"checkbox\" name=\"movePendingAdjustment\" value=\"Y\" checked>\n");
	out.println("</TD>\n");
    out.println("</TR>\n");
    }
    out.println("</TABLE>\n");

	out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"DuplLineNumber\" VALUE=\" \">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"AddBin_Item_Id\" VALUE=\" \">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"AddBin_Bin_Id\" VALUE=\" \">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"AddBin_Line_No\" VALUE=\" \">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNo\" VALUE=\"" + HubNo + "\">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"receiptid\" VALUE=\"" + receiptId + "\">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"QtyAvailable\" VALUE=\"" + quantityOnHand + "\">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"maxsplitqty\" id=\"maxsplitqty\" VALUE=\"" + quantityOnHand + "\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"netPendingAdj\" VALUE=\"" + netPendingAdj + "\">\n");
    out.println("<BR><BR><P><CENTER><INPUT TYPE=\"SUBMIT\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"sendBin\" value=\"OK\" OnClick=\"return sendsplitbin(name,this)\">\n");
	out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER></P>\n");
	out.println("</FORM></BODY></HTML>\n");

	return;
 }
}
