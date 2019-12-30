package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput,Nawaz Shaik
 * @version
 *
 * 10-29-02
 *
 * Changed the UpdateApp procedure to inser sysdate when the receipt is Qced. Also changed it to stamp the sysdate in Qc_date even when
 * there is an error in the process
 *
 * 10-30-02
 * Calling P_RECEIPT_ALLOCATE with the second parameter Y it was A
 * Sending 0 for transfer receipt id when it is null to P_receipt_INSERT
 *
 * 11-04-02
 * Changed the getDateFromString() procedure to handle Date Strings with different delimiters like "-"," ","/",".",":"
 *
 * 11-05-02
 * Made BothHelpObjs.changeSingleQuotetoTwoSingleQuote() for the MFG_LOT field
 *
 * 12-14-02
 * add excess material to getAllopenreceiptQC - display with difference color if excess material
 *
 * 01-14-03
 * Adding the new column actual ship date
 *
 * 03-05-03
 * Setting the requestor to -1 for nobuy order processing
 * Non-Chemical Duplicate. some element present for chemical were not present for non-chemical
 *
 * 03-10-03
 * Calling fx_no_buy_order_po_line before inserting into the receipt table to check if I am having a po_line
 * 03-20-03
 * Sending back the receipt id after receiving BG
 * 04-22-03 - Trong had wrongly commited the wrong version
 * 07-01-03 - Inventory Group stuff and checking the lenght of roo cause notes.
 * 07-18-03 - Not converting Non-Chemical stuff to P_CONVERT_RECEIPT_TO_CUSTOMER Customer owened material
 * 07-30-03 - No Need to break up the Item_desc it auto wraps itself
 * 08-12-03 - if P_CONVERT_RECEIPT_TO_CUSTOMER fails setting the Approved status to N
 * 08-13-03 - Sending the company_id from customer.hub_inventory_owner to P_CONVERT_RECEIPT_TO_CUSTOMER procedure
 * 08-20-03 - Sending myself emails whenever approved is set to 'N'
 * 10-06-03 - If there are no Records in no_buy_order_mr_create_view not calling the MR create process
 * 10-20-03 - Calling the P_convert_receipt_to_coustomer only if the po is not consigned
 * 10-22-03 - Not joining seagate_bindropdown to get the history of Bins the item has been in
 * 11-13-03 - Added new column to tell me if it is ok to QC the inventory transfer. Calling a procedure to QC receipt instead of a update statement
 * 11-17-03 - Calling P_receipt_INSERT for bulk gas receiving
 * 11-24-03 - Added quality control required value to the data set,Cannot put the recipt in a pickable status if you don't have quality control permissions for items which are quality controled
 * 12-12-03 - Took out a unused method
 * 12-18-03 - Getting the second Receipt_ID if the receipt is split
 * 12-22-03 - Calling P_RECEIPT_ALLOCATE after I convert the receipt to coustomer owened
 * 01-16-04 - QC person can change MFG_LOT and EXP_DATE for qulaity control items
 * 02-05-04 - Not approving the receipt if there are no records in no_buy_order_mr_create_view for Non-chemical receipts
 * 02-10-03 - Not creating a MR for no buy order PO is it is for pittsburgh Hub
 * 02-19-04 - Showing Only Active BINs
 * 03-01-04 - Not setting the QC_DATE when it is being unapproved due to an error
 * 03-25-04 - Moving the Permissions to inventory Group level and adding the ability to search by inventory group
 * 03-31-04 - Giving a search option on the Receiving QC page
 * 04-19-04 - Changeing the inventory group drop down to show INVENTORY_GROUP_NAME in the display of drop dowm. Getting the Hub list from the HUB_PREF
 * table instead of the user_group_member table. Changing the receiving to accomodate receiving kits with different parts with different MFG_LOT,EXP_DATE and BIN.
 * Changin the date format to allow input in the following formats mmddyyyy and mmddyyy
 * 05-20-04 - Not Creating No Buy Order PO MRs for Gilroy Hub
 * 06-01-04 - Breaking up the search to make the query faster
 * 07-22-04 - Giving the ability to enter receipt notes from the receiving page and Receiving QC page
 * 07-29-04 - Showing Storage Temp
 * 11-02-04 - Changed the query to make it possible to sort the page by BIN,LOT etc
 * 02-24-05 - Sending no_buy_order_mr_create erros to steve
 * 04-26-05 - not creating an MR for non_chemical receipts if the company Id is SEAGATE. I used to restrict this by hub abd we now have a hub
 * which servers more than the company seagate SLAC
 * 06-22-05 - Showing MR-Line for returned receipts.
 * 07-28-05 - Giving the option to enter delivery ticket information.
 * 09-20-05 - Formating DOR to allow working with the new struts receiving page
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Vector;
import java.math.BigDecimal;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;

public class HubReceivingTables
{
    private TcmISDBModel db;
    private   Vector vLotSeqList = null;
    private   Vector errorMsgs = null;
	private static org.apache.log4j.Logger reclog = org.apache.log4j.Logger.getLogger(HubReceivingTables.class);

    public HubReceivingTables()
    {
        vLotSeqList = new Vector();
        errorMsgs = new Vector();
    }

    public HubReceivingTables(TcmISDBModel db )
    {
        this.db = db;
        vLotSeqList = new Vector();
        errorMsgs = new Vector();
    }

    public void setDB(TcmISDBModel db)
    {
        this.db = db;
    }

    public void setLotSeqList()
    {
        this.vLotSeqList = vLotSeqList;
    }

    public Vector getLotSeqList( )
    {
        return this.vLotSeqList;
    }

    public Vector getErrMsgs( )
    {
        return this.errorMsgs;
    }

	public Vector getAllopenOrder( String Branch_id,String SearchText,String SortBy,String noOfDays,String seinvengrp,String searchlike,String searchfor ) throws
	   Exception
	{
	  DBResultSet dbrs=null;
	  ResultSet rs=null;
	  Vector result=new Vector();
	  Hashtable summary=new Hashtable();

	  String sort_order="";
	  if ( SortBy.equalsIgnoreCase( "1" ) )
	  {
		sort_order=" order by RADIAN_PO, LINE_ITEM ";
	  }
	  else
	  if ( SortBy.equalsIgnoreCase( "2" ) )
	  {
		sort_order=" order by  LAST_SUPPLIER, EXPECTED ";
	  }
	  else
	  if ( SortBy.equalsIgnoreCase( "3" ) )
	  {
		sort_order=" order by EXPECTED, LAST_SUPPLIER ";
	  }
	  else if ( SortBy.equalsIgnoreCase( "4" ) )
	  {
		sort_order=" order by ITEM_ID, EXPECTED ";
	  }

      String user_query = "select a.SKIP_RECEIVING_QC, a.MANAGE_KITS_AS_SINGLE_UNIT, a.COMPONENT_ID, a.MATERIAL_ID, a.MATERIAL_DESC, a.INVENTORY_GROUP_NAME, ";
	  user_query += "a.NUMBER_OF_KITS,a.INVENTORY_GROUP,a.ORIGINAL_RECEIPT_ID,a.ORIGINAL_MFG_LOT,a.CRITICAL,to_char(sysdate,'mm/dd/yyyy') SYS_DATE, a.RADIAN_PO, ";
	  user_query += "a.LAST_SUPPLIER, a.QTY_OPEN ,a.ITEM_ID, a.EXPECTED, a.ITEM_DESCRIPTION , a.PACKAGING, a.TRANSFER_REQUEST_ID, a.PO_NOTES, a.TRANS_TYPE, ";
      user_query += " a.LINE_ITEM,a.INDEFINITE_SHELF_LIFE from receiving_view a";
      user_query += " where a.EXPECTED < sysdate + "+noOfDays+" and a.BRANCH_PLANT = " + Branch_id;

	  if ( SearchText.trim().length() > 0 )
	  {
		if ( "Like".equalsIgnoreCase( searchfor ) )
		{
		  user_query+=" and lower(" + searchlike + ") like lower('%" + SearchText.trim() + "%') ";
		}
		else if ( "Equals".equalsIgnoreCase( searchfor ) )
		{
		  user_query+=" and " + searchlike + " = '" + SearchText.trim() + "' ";
		}
	  }

	  boolean flagforand=true;
	  if ( seinvengrp.length() > 0 && !"All".equalsIgnoreCase( seinvengrp ) )
	  {
		if ( flagforand )
		  user_query+=" and a.INVENTORY_GROUP ='" + seinvengrp + "' ";
		else
		{
		  user_query+=" a.INVENTORY_GROUP ='" + seinvengrp + "' ";
		}
	  }

	  user_query+=sort_order;

	  int count=0;
	  summary.put( "TOTAL_NUMBER",new Integer( count ) );
	  result.addElement( summary );

	  int num_rec=0;
	  try
	  {
		dbrs=db.doQuery( user_query );
		rs=dbrs.getResultSet();

		while ( rs.next() )
		{
		  num_rec++;

		  Hashtable hD=new Hashtable();
		  hD.put( "PURCHASE_ORDER",rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ) );
		  hD.put( "PURCHASE_ORDER_LINE",rs.getString( "LINE_ITEM" ) == null ? "" : rs.getString( "LINE_ITEM" ) );

		  String temp_date=rs.getString( "EXPECTED" ) == null ? "" : rs.getString( "EXPECTED" );
		  String yy=temp_date.substring( 2,4 );
		  String mm=temp_date.substring( 5,7 );
		  String dd=temp_date.substring( 8,10 );
		  String expct_date=mm + "/" + dd + "/" + yy;
		  hD.put( "EXPCTD_DATE",expct_date );
		  hD.put( "PACKING",rs.getString( "PACKAGING" ) == null ? "" : rs.getString( "PACKAGING" ) );
		  hD.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
		  hD.put( "ITEM_DESC",rs.getString( "ITEM_DESCRIPTION" ) == null ? "" : rs.getString( "ITEM_DESCRIPTION" ) );
		  hD.put( "QTY_OPEN",rs.getString( "QTY_OPEN" ) == null ? "" : rs.getString( "QTY_OPEN" ) );
		  hD.put( "TRANS_TYPE",rs.getString( "TRANS_TYPE" ) == null ? "" : rs.getString( "TRANS_TYPE" ) );
		  hD.put( "NOTES","" );
		  hD.put( "XFER_REQ_ID",rs.getString( "TRANSFER_REQUEST_ID" ) == null ? "" : rs.getString( "TRANSFER_REQUEST_ID" ) );
		  hD.put( "MFG_LOT","" );
		  hD.put( "DATE_MFGD","" );
		  hD.put( "QTY_RECD","" );
		  hD.put( "USERCHK","" );
		  hD.put( "BIN_NAME","" );
		  hD.put( "STATUS_ID","" );
		  hD.put( "SUPPLIER",rs.getString( "LAST_SUPPLIER" ) == null ? "" : rs.getString( "LAST_SUPPLIER" ) );
		  hD.put( "DATE_RECIEVED",rs.getString( "SYS_DATE" ) == null ? "" : rs.getString( "SYS_DATE" ) );
		  hD.put( "EXPIRE_DATE","" );
		  hD.put( "DATE_OF_SHIPMENT","" );
		  hD.put( "RECEIPT_ID","" );
		  hD.put( "INDEFINITE_SHELF_LIFE",rs.getString( "INDEFINITE_SHELF_LIFE" ) == null ? "" : rs.getString( "INDEFINITE_SHELF_LIFE" ) );
		  hD.put( "LINE_STATUS","" );
		  hD.put( "COMPANY_ID","" );
		  hD.put( "CAT_PART_NO","" );
		  hD.put( "ACTUAL_SHIP_DATE","" );
		  hD.put( "CRITICAL",rs.getString( "CRITICAL" ) == null ? "" : rs.getString( "CRITICAL" ) );
		  hD.put( "ORIGINAL_RECEIPT_ID",rs.getString( "ORIGINAL_RECEIPT_ID" ) == null ? "" : rs.getString( "ORIGINAL_RECEIPT_ID" ) );
		  hD.put( "ORIGINAL_MFG_LOT",rs.getString( "ORIGINAL_MFG_LOT" ) == null ? "" : rs.getString( "ORIGINAL_MFG_LOT" ) );
		  hD.put( "INVENTORY_GROUP",rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ) );
		  hD.put( "MANAGE_KITS_AS_SINGLE_UNIT",rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) == null ? "" : rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) );
		  hD.put( "COMPONENT_ID",rs.getString( "COMPONENT_ID" ) == null ? "" : rs.getString( "COMPONENT_ID" ) );
		  hD.put( "MATERIAL_ID",rs.getString( "MATERIAL_ID" ) == null ? "" : rs.getString( "MATERIAL_ID" ) );
		  hD.put( "MATERIAL_DESC",rs.getString( "MATERIAL_DESC" ) == null ? "" : rs.getString( "MATERIAL_DESC" ) );
		  hD.put( "INVENTORY_GROUP_NAME",rs.getString( "INVENTORY_GROUP_NAME" ) == null ? "" : rs.getString( "INVENTORY_GROUP_NAME" ) );
		  hD.put( "NUMBER_OF_KITS",rs.getString( "NUMBER_OF_KITS" ) == null ? "" : rs.getString( "NUMBER_OF_KITS" ) );
		  hD.put( "LOT_STATUS","" );
		  hD.put( "SKIP_RECEIVING_QC",rs.getString( "SKIP_RECEIVING_QC" ) == null ? "" : rs.getString( "SKIP_RECEIVING_QC" ) );
		  hD.put( "DELIVERY_TICKET","" );

		  result.addElement( hD );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		result=null;
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
	  result.setElementAt( recsum,0 );
	  result.trimToSize();
	  return result;
    }

	public Vector getAllopenOrderBG( String Branch_id,String SearchText,String SortBy,String seinvengrp,String searchlike,String searchfor ) throws
	   Exception
	{
	  DBResultSet dbrs=null;
	  ResultSet rs=null;
	  Vector result=new Vector();
	  Hashtable summary=new Hashtable();

	  String sort_order="";
	  if ( SortBy.equalsIgnoreCase( "1" ) )
	  {
		sort_order=" order by RADIAN_PO, LINE_ITEM ";
	  }
	  else
	  if ( SortBy.equalsIgnoreCase( "2" ) )
	  {
		sort_order=" order by  LAST_SUPPLIER, EXPECTED ";
	  }
	  else
	  if ( SortBy.equalsIgnoreCase( "3" ) )
	  {
		sort_order=" order by EXPECTED, LAST_SUPPLIER ";
	  }
	  else if ( SortBy.equalsIgnoreCase( "4" ) )
	  {
		sort_order=" order by ITEM_ID, EXPECTED ";
	  }

	  String user_query=" select a.INVENTORY_GROUP,to_char(sysdate,'MM/DD/YYYY') SYS_DATE,a.RADIAN_PO, ";
	  user_query+="a.LAST_SUPPLIER,a.EXPECTED,a.QTY_OPEN,a.ITEM_ID,a.PART_DESCRIPTION,a.COMPANY_ID,'OV' TRANS_TYPE,a.CAT_PART_NO, ";
	  user_query+="max(a.LINE_ITEM) LINE_ITEM, a.PACKAGING from no_buy_order_po_receiving_view a where a.BRANCH_PLANT = " + Branch_id;

	  if ( SearchText.trim().length() > 0 )
	  {
		if ( "Like".equalsIgnoreCase( searchfor ) )
		{
		  user_query+=" and lower(" + searchlike + ") like lower('%" + SearchText.trim() + "%') ";
		}
		else if ( "Equals".equalsIgnoreCase( searchfor ) )
		{
		  user_query+=" and " + searchlike + " = '" + SearchText.trim() + "' ";
		}
	  }

	  boolean flagforand=true;
	  if ( seinvengrp.length() > 0 && !"All".equalsIgnoreCase( seinvengrp ) )
	  {
		if ( flagforand )
		  user_query+=" and a.INVENTORY_GROUP ='" + seinvengrp + "' ";
		else
		{
		  user_query+=" a.INVENTORY_GROUP ='" + seinvengrp + "' ";
		}
	  }

	  user_query+=" group by  ";
	  user_query+="a.RADIAN_PO, ";
	  user_query+="a.LAST_SUPPLIER,a.EXPECTED, ";
	  user_query+="a.QTY_OPEN, ";
	  user_query+="a.ITEM_ID, ";
	  user_query+="a.PART_DESCRIPTION,a.COMPANY_ID, ";
	  user_query+="'OV',a.CAT_PART_NO, ";
	  user_query+="a.LINE_ITEM, a.PACKAGING,a.INVENTORY_GROUP ";
	  user_query+=sort_order;

	  int count=0;
	  summary.put( "TOTAL_NUMBER",new Integer( count ) );
	  result.addElement( summary );

	  int num_rec=0;
	  try
	  {
		dbrs=db.doQuery( user_query );
		rs=dbrs.getResultSet();

		while ( rs.next() )
		{
		  num_rec++;
		  Hashtable hD=new Hashtable();
		  String expct_date=" ";

		  hD.put( "PURCHASE_ORDER",rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ) );
		  hD.put( "PURCHASE_ORDER_LINE",rs.getString( "LINE_ITEM" ) == null ? "" : rs.getString( "LINE_ITEM" ) );
		  hD.put( "EXPCTD_DATE",expct_date );
		  hD.put( "PACKING",rs.getString( "PACKAGING" ) == null ? "" : rs.getString( "PACKAGING" ) );
		  hD.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
		  hD.put( "ITEM_DESC",rs.getString( "PART_DESCRIPTION" ) == null ? "" : rs.getString( "PART_DESCRIPTION" ) );
		  hD.put( "QTY_OPEN",rs.getString( "QTY_OPEN" ) == null ? "" : rs.getString( "QTY_OPEN" ) );
		  hD.put( "TRANS_TYPE",rs.getString( "TRANS_TYPE" ) == null ? "" : rs.getString( "TRANS_TYPE" ) );
		  hD.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ) );
		  hD.put( "NOTES","" );
		  hD.put( "XFER_REQ_ID","" );
		  hD.put( "MFG_LOT","" );
		  hD.put( "DATE_MFGD","" );
		  hD.put( "QTY_RECD","" );
		  hD.put( "USERCHK","" );
		  hD.put( "BIN_NAME","" );
		  hD.put( "STATUS_ID","" );
		  hD.put( "SUPPLIER",rs.getString( "LAST_SUPPLIER" ) == null ? "" : rs.getString( "LAST_SUPPLIER" ) );
		  hD.put( "DATE_RECIEVED",rs.getString( "SYS_DATE" ) == null ? "" : rs.getString( "SYS_DATE" ) );
		  hD.put( "COMPANY_ID",rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ) );
		  hD.put( "EXPIRE_DATE","" );
		  hD.put( "DATE_OF_SHIPMENT","" );
		  hD.put( "RECEIPT_ID","" );
		  hD.put( "INDEFINITE_SHELF_LIFE","" );
		  hD.put( "LINE_STATUS","" );
		  hD.put( "COMPANY_ID","" );
		  hD.put( "ACTUAL_SHIP_DATE","" );
		  hD.put( "CRITICAL","" );
		  hD.put( "ORIGINAL_RECEIPT_ID","" );
		  hD.put( "ORIGINAL_MFG_LOT","" );
		  hD.put( "INVENTORY_GROUP",rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ) );
		  hD.put( "MANAGE_KITS_AS_SINGLE_UNIT","Y" );
		  hD.put( "COMPONENT_ID","" );
		  hD.put( "MATERIAL_ID","" );
		  hD.put( "MATERIAL_DESC","" );
		  hD.put( "INVENTORY_GROUP_NAME","" );
		  hD.put( "NUMBER_OF_KITS","" );
		  hD.put( "LOT_STATUS","" );
		  hD.put( "SKIP_RECEIVING_QC","" );
		  hD.put( "DELIVERY_TICKET","" );
		  result.addElement( hD );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		result=null;
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
	  result.setElementAt( recsum,0 );
	  result.trimToSize();
	  return result;
    }

  public boolean UpdateAppStatus( Hashtable hD,String Branch_plant,String loginID,String usrcategory, String CompanyID )
  {
	boolean result = false;
	DBResultSet dbrs = null;
	ResultSet rs = null;
	Connection connect1;
	CallableStatement cs = null;

	String receipt_id = hD.get("RECEIPT_ID")==null?"":hD.get("RECEIPT_ID").toString();
	String Date_mfgd   = hD.get("DOM")==null?"":hD.get("DOM").toString();
	String Date_shiped = hD.get("DOS")==null?"":hD.get("DOS").toString();
	String Date_receivied  = hD.get("DATE_RECIEVED")==null?"":hD.get("DATE_RECIEVED").toString();
	String lotStatus        = hD.get("LOT_STATUS")==null?"":hD.get("LOT_STATUS").toString();
	String exp_date         = hD.get("EXPIRE_DATE")==null?"":hD.get("EXPIRE_DATE").toString();
	String companyIDfromhash  = hD.get("COMPANY_ID")==null?"":hD.get("COMPANY_ID").toString();
	String item_id         = hD.get("ITEM_ID")==null?"":hD.get("ITEM_ID").toString();
	String qtyreceived     = hD.get("QUANTITY_RECEIVED")==null?"":hD.get("QUANTITY_RECEIVED").toString();
	String pickdatest    = hD.get("SYS_DATE")==null?"":hD.get("SYS_DATE").toString();
	String actshipDate = hD.get("ACTUAL_SHIP_DATE")==null?" ":hD.get("ACTUAL_SHIP_DATE").toString();
	String rootcause= hD.get("TERMINAL_ROOT_CAUSE")==null?" ":hD.get("TERMINAL_ROOT_CAUSE").toString().trim();
	String rootcausecompany= hD.get("TERMINAL_COMPANY")==null?" ":hD.get("TERMINAL_COMPANY").toString().trim();
	String rootcausenotes= hD.get("TERMINAL_NOTES")==null?" ":hD.get("TERMINAL_NOTES").toString().trim();
	//if (rootcausenotes.length() > 200){rootcausenotes = rootcausenotes.substring(0,199);}
	String consignedpo = hD.get("CONSIGNED_PO")==null?" ":hD.get("CONSIGNED_PO").toString();
	String qualitycntitem = hD.get( "QUALITY_CONTROL_ITEM" ) == null ? " " : hD.get( "QUALITY_CONTROL_ITEM" ).toString().trim() ;
	String certupdate = hD.get( "CERT_UPDATE" ) == null ? " " : hD.get( "CERT_UPDATE" ).toString().trim() ;
	String Mfg_lot=hD.get( "MFG_LOT" ) == null ? " " : hD.get( "MFG_LOT" ).toString();
	int IntReceipt_id = Integer.parseInt(receipt_id);
	String recnotes = hD.get("NOTES").toString();
	String purchaseOrder = hD.get( "PURCHASE_ORDER" ) == null ? " " : hD.get( "PURCHASE_ORDER" ).toString().trim() ;
	String purchaseOrderLine = hD.get( "PURCHASE_ORDER_LINE" ) == null ? " " : hD.get( "PURCHASE_ORDER_LINE" ).toString().trim() ;
	String deliveryTicket = hD.get("DELIVERY_TICKET").toString();

	try
	{
	  connect1 = db.getConnection();
	  String Updatequery = "";
	  if ( "Customer Purchase".equalsIgnoreCase(lotStatus) || "Write Off Requested".equalsIgnoreCase(lotStatus) || "3PL Purchase".equalsIgnoreCase(lotStatus) )
	  {
		if (rootcause.length() == 0 || rootcausecompany.length() == 0)
		{
		  result=false;
		  errorMsgs.addElement("Please choose the Root Cause and Root Cause Company for Receipt ID :"+receipt_id+"");
		  return result;
		}
	  }

	  try
	  {
		cs = connect1.prepareCall("{call p_receipt_qc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setInt(1,Integer.parseInt(receipt_id));
		if (loginID.trim().length() > 0){cs.setString(2,loginID);}else{cs.setNull(2,java.sql.Types.VARCHAR);}
		if (Date_shiped.length() >1){cs.setTimestamp(3,radian.web.HTMLHelpObj.getDateFromString( "",Date_shiped));}else{cs.setNull(3,java.sql.Types.DATE);}
		if (Date_mfgd.length() >1){cs.setTimestamp(4,radian.web.HTMLHelpObj.getDateFromString( "",Date_mfgd));}else{cs.setNull(4,java.sql.Types.DATE);}
		if (Date_receivied.length() >1){cs.setTimestamp(5,radian.web.HTMLHelpObj.getDateFromString( "",Date_receivied));}else{cs.setNull(5,java.sql.Types.DATE);}
		if (exp_date.length() >1){cs.setTimestamp(6,radian.web.HTMLHelpObj.getDateFromString( "",exp_date));}else{cs.setNull(6,java.sql.Types.DATE);}
		if (actshipDate.length()== 10){cs.setTimestamp(7,radian.web.HTMLHelpObj.getDateFromString( "",actshipDate));}else{cs.setNull(7,java.sql.Types.DATE);}
		if (!usrcategory.equalsIgnoreCase("2"))
		{
		  if (lotStatus.trim().length() > 0){cs.setString(8,lotStatus);}else{cs.setNull(8,java.sql.Types.VARCHAR);}
		}
		else
		{
		  cs.setString(8,"Available");
		}
		if (loginID.trim().length() > 0){cs.setString(9,loginID);}else{cs.setNull(9,java.sql.Types.VARCHAR);}
		if (rootcause.length() > 0){cs.setString(10,rootcause);}else{cs.setNull(10,java.sql.Types.VARCHAR);}
		if (rootcausecompany.length() > 0){cs.setString(11,rootcausecompany);}else{cs.setNull(11,java.sql.Types.VARCHAR);}
		if (rootcausenotes.length() > 0){cs.setString(12,rootcausenotes);}else{cs.setNull(12,java.sql.Types.VARCHAR);}
		cs.setNull(13,java.sql.Types.DATE);
		//cs.setTimestamp(13,radian.web.HTMLHelpObj.getDateFromString( "",actshipDate));
		cs.setNull(14,java.sql.Types.VARCHAR);
		//cs.setString(14,"Y");
		if ("Y".equalsIgnoreCase(qualitycntitem) && "Yes".equalsIgnoreCase(certupdate)){cs.setString(15,loginID);}else{cs.setNull(15,java.sql.Types.VARCHAR);}
		if (Mfg_lot.trim().length() > 0){cs.setString(16,Mfg_lot);}else{cs.setNull(16,java.sql.Types.VARCHAR);}
		if (recnotes.trim().length() > 0){cs.setString(17,recnotes);}else{cs.setNull(17,java.sql.Types.VARCHAR);}
		cs.registerOutParameter(18,java.sql.Types.VARCHAR);
		if (deliveryTicket.trim().length() > 0){cs.setString(19,deliveryTicket);}else{cs.setNull(19,java.sql.Types.VARCHAR);}

		cs.execute();

		String errorcode = BothHelpObjs.makeBlankFromNull(cs.getString(18));
		//reclog.info("errorcode from p_receipt_qc "+errorcode);
		if (!"OK".equalsIgnoreCase(errorcode))
		{
		  result=false;
		  errorMsgs.addElement( errorcode );
		  return result;
		}
	  }
	  catch (Exception e)
	  {
		e.printStackTrace();
		radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling Procedure call p_receipt_qc in HubReceiving QC","Error occured while running call p_receipt_qc \nError Msg:\n"+e.getMessage()+"\n\n Parameters passed receipt_id "+receipt_id+"\n Date_mfgd "+Date_mfgd+"\n Date_shiped "+Date_shiped+"\n Lot Status "+lotStatus+"\n Date_Recieved "+Date_receivied+"\npersonnel Id "+loginID+"\nExpiry_Date "+exp_date+"");
		throw e;
	  }
	  finally
	  {
		connect1.commit();
		if (cs != null) {
		  try {
			cs.close();
		  } catch (Exception se) {se.printStackTrace();}
		}
	  }
	  connect1.commit();
	  int HubCount=0;
	  String cousownedcompanyId="";
	  //int HubCount = DbHelpers.countQuery(db,"select count(*) from customer.hub_inventory_owner where HUB = "+Branch_plant+"");
	  try
	  {
		dbrs=db.doQuery( "select distinct COMPANY_ID from customer.hub_inventory_owner where HUB = " + Branch_plant + "" );
		rs=dbrs.getResultSet();

		while ( rs.next() )
		{
		  cousownedcompanyId=rs.getString( "COMPANY_ID" ) == null ? " " : rs.getString( "COMPANY_ID" );
		  HubCount++;
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
		db.doUpdate( Updatequery );

		result=false;
		radian.web.emailHelpObj.sendnawazemail("Error in customer.hub_inventory_owner query","Error in customer.hub_inventory_owner query\nError Msg:\n"+e.getMessage()+"");
		errorMsgs.addElement( "Error in quering customer.hub_inventory_owner for Receipt ID :" + receipt_id + "" );
		return result;
	  }
	  finally
	  {
		if ( dbrs != null ) {dbrs.close();}
	  }

	  if ( HubCount > 0 && ( !usrcategory.equalsIgnoreCase( "2" ) ) && "n".equalsIgnoreCase(consignedpo) )
	  {
		try
		{
		  cs=connect1.prepareCall( "{call P_CONVERT_RECEIPT_TO_CUSTOMER(?,?,?,?,?)}" );
		  cs.setString( 1,cousownedcompanyId );
		  cs.setInt( 2,Integer.parseInt( receipt_id ) );
		  cs.registerOutParameter( 3,Types.NUMERIC );
		  cs.registerOutParameter( 4,Types.NUMERIC );
		  cs.registerOutParameter( 5,Types.VARCHAR );
		  cs.execute();

		  String testmsg=cs.getString( 5 );
		  if ( testmsg == null )
			testmsg="";

		  if ( testmsg.length() > 0 )
		  {
			reclog.info( "\n----P_CONVERT_RECEIPT_TO_CUSTOMER Check Failed  for Receipt ID :" + receipt_id + "  " + testmsg + "   CompanyID    " + cousownedcompanyId );
			Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
			db.doUpdate( Updatequery );

			result=false;
			radian.web.emailHelpObj.sendsteveemail("Error in P_CONVERT_RECEIPT_TO_CUSTOMER Receipt ID :" + receipt_id + "","Error in P_CONVERT_RECEIPT_TO_CUSTOMER Receipt ID :" + receipt_id + "\nError Msg:\n"+testmsg+"");

			errorMsgs.addElement( "Convert Receipt to Customer Failed for Receipt ID :" + receipt_id + "   Msg" + testmsg + "" );
			return result;
		  }
		}
		catch ( Exception e2 )
		{
		  reclog.info( "\n\n--------- Erros in P_CONVERT_RECEIPT_TO_CUSTOMER Procedure \n\n" );
		  e2.printStackTrace();

		  Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
		  db.doUpdate( Updatequery );

		  errorMsgs.addElement( "Error Calling P_CONVERT_RECEIPT_TO_CUSTOMER for Receipt ID :" + receipt_id + "" );
		  radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling  P_CONVERT_RECEIPT_TO_CUSTOMER in HubReceiving QC for Receipt ID:" + receipt_id + ""," Error Msg \n" + e2.getMessage() + "\n\nParameter Company ID " + cousownedcompanyId + " and Receipt " + receipt_id + "" );
		  throw e2;
		}
		finally
		{
		  connect1.commit();
		}
	  }

	  if (!usrcategory.equalsIgnoreCase("2"))
	  {
		try
		 {
		   cs = connect1.prepareCall("{call P_RECEIPT_ALLOCATE(?,?)}");
		   cs.setInt(1, IntReceipt_id);
		   cs.setString(2, "Y");
		   cs.execute();
		 }
		 catch (Exception e1)
		 {
		   reclog.info("\n\n--------- Erros in Receiving P_RECEIPT_ALLOCATE Procedure 1 \n\n");
			 e1.printStackTrace();

		  Updatequery = "update receipt set APPROVED = 'N',QC_USER_ID = '"+loginID+"',QC_DATE=null where RECEIPT_ID = "+receipt_id+"";
		  reclog.info("\n\n--------- Receiving QC Setting Update Status to N : \n");
		  db.doUpdate(Updatequery);

		  errorMsgs.addElement("Error Calling P_RECEIPT_ALLOCATE for Receipt ID :"+receipt_id+"");
		  radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling Procedure P_RECEIPT_ALLOCATE in HubReceiving QC","Error occured while running P_RECEIPT_ALLOCATE  \n"+e1.getMessage()+"\n for Receipt Id: "+receipt_id+"");
			 throw e1;
		}
		finally
		{
		   connect1.commit();
		 }

	  }

	  //Hard Coded for Not Normandale Hub and Not Pittsburg Hub Not Gilroy Hub (Seagate)
	  if ( usrcategory.equalsIgnoreCase( "2" ) && !( "SEAGATE".equalsIgnoreCase(companyIDfromhash) ) )
	  {
		//Call Trong Procedure
		String[] resulthg = new String[3];
		Hashtable hDdata = new Hashtable();
		int nbocount = 0;

		try
		{
		  //this assumes there is only one row in no_buy_order_mr_create for a receipt ID
		  String query = "select * from no_buy_order_mr_create_view where receipt_id = "+receipt_id+"";
		  dbrs = db.doQuery(query);
		  rs=dbrs.getResultSet();

		  hDdata = new Hashtable();
		  while (rs.next())
		  {
			nbocount++;
			hDdata.put("COMPANY_ID",rs.getString("COMPANY_ID")==null?" ":rs.getString("COMPANY_ID"));
			hDdata.put("CATALOG_ID",rs.getString("CATALOG_ID")==null?" ":rs.getString("CATALOG_ID"));
			hDdata.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? " " : rs.getString( "ITEM_ID" ) );
			hDdata.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
			hDdata.put( "ORDERING_FACILITY_ID",rs.getString( "ORDERING_FACILITY_ID" ) == null ? " " : rs.getString( "ORDERING_FACILITY_ID" ) );
			hDdata.put( "ORDERING_APPLICATION",rs.getString( "ORDERING_APPLICATION" ) == null ? " " : rs.getString( "ORDERING_APPLICATION" ) );
			hDdata.put( "PART_GROUP_NO",rs.getString( "PART_GROUP_NO" ) == null ? " " : rs.getString( "PART_GROUP_NO" ) );
			hDdata.put( "INVENTORY_GROUP",rs.getString( "INVENTORY_GROUP" ) == null ? " " : rs.getString( "INVENTORY_GROUP" ) );
			hDdata.put( "QUANTITY_RECEIVED",rs.getString( "QUANTITY_RECEIVED" ) == null ? " " : rs.getString( "QUANTITY_RECEIVED" ) );
			hDdata.put( "STOCKED",rs.getString( "STOCKED" ) == null ? " " : rs.getString( "STOCKED" ) );
			hDdata.put( "UNIT_PRICE",rs.getString( "UNIT_PRICE" ) == null ? " " : rs.getString( "UNIT_PRICE" ) );
			hDdata.put( "CATALOG_PRICE",rs.getString( "CATALOG_PRICE" ) == null ? " " : rs.getString( "CATALOG_PRICE" ) );
			hDdata.put( "ACCOUNT_SYS_ID",rs.getString( "ACCOUNT_SYS_ID" ) == null ? " " : rs.getString( "ACCOUNT_SYS_ID" ) );
			hDdata.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? " " : rs.getString( "PACKAGING" ) );
			hDdata.put( "CURRENCY_ID",rs.getString( "currency_id" ) == null ? " " : rs.getString( "currency_id" ) );
			hDdata.put("REQUESTOR","-1");
		  }
		}
		catch (Exception e)
		{
		  e.printStackTrace();
		  Updatequery = "update receipt set APPROVED = 'N',QC_USER_ID = '"+loginID+"',QC_DATE=null where RECEIPT_ID = "+receipt_id+"";
		  //reclog.info("\n\n--------- Receiving QC Setting Update Status to N : \n");
		  db.doUpdate(Updatequery);

		  result = false;
		  radian.web.emailHelpObj.sendnawazemail("Error in no_buy_order_mr_create_view query","Error in no_buy_order_mr_create_view query\nError Msg:\n"+e.getMessage()+"");
		  errorMsgs.addElement("Error in no_buy_order_mr_create_view for Receipt ID :"+receipt_id+"");
		  return result;
		}
		finally
		{
		  if (dbrs != null)  { dbrs.close(); }
		}

		if (nbocount > 0)
		{
		  NoBuyOrderPo nbpObj = new NoBuyOrderPo();
		  try
		  {
			resulthg=nbpObj.processRequest( hDdata );
		  }
		  catch ( Exception ew )
		  {
			ew.printStackTrace();
			Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
			//reclog.info( "\n\n--------- Receiving QC Setting Update Status to N : \n" );
			db.doUpdate( Updatequery );
			radian.web.emailHelpObj.sendnawazemail("Error in nbpObj.processRequest receipt_id  "+receipt_id+"","Error in nbpObj.processRequest\nreceipt_id:   "+receipt_id+"\n\nError Msg:\n"+ew.getMessage()+"");

			result=false;
			errorMsgs.addElement("Error in Creating MR for Receipt ID :"+receipt_id+"");
			return result;
		  }
		}
		else
		{
		  Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
		  db.doUpdate( Updatequery );

		  radian.web.emailHelpObj.sendsteveemail("No Records in no_buy_order_mr_create_view for Receipt ID  "+receipt_id+" Purchase Order : "+purchaseOrder+"  Line: "+purchaseOrderLine+"","No Records in no_buy_order_mr_create_view for Receipt ID  "+receipt_id+"\n\nPurchase Order : "+purchaseOrder+"  Line: "+purchaseOrderLine+"");
		  result=false;
		  errorMsgs.addElement("Error in Creating MR for Receipt ID :"+receipt_id+"");
		  return result;
		}

		String resultok = resulthg[0].toString();
		String prnumber = resulthg[1].toString();
		String lineItem = resulthg[2].toString();

		reclog.info(" No Buy Order PO MR creastion result OK "+resultok+"   "+prnumber+"    "+lineItem+"  for receipt ID    "+receipt_id+"");
		String batchno = "";
		int issueId = 0;
		String errmsg = "";

		if ("OK".equalsIgnoreCase(resultok))
		{
		  //Call Issue Procedures
		  try
		  {
			//batchno = this.getBatchNo();
			int uid = DbHelpers.getNextVal(db,"print_batch_seq");
			batchno = ""+uid+"";
			Timestamp pickdate = radian.web.HTMLHelpObj.getDateFromString( "",pickdatest);
			//reclog.info("Doing Update Act:  Company Id: "+companyIDfromhash+"\nMR "+prnumber+"\nlineitem "+lineItem+" \nbranch plant "+Branch_plant+"\n receipt id "+receipt_id+"\n item id "+item_id+"\n pick Date "+pickdate+"\n act pick "+qtyreceived+"\n SYSDATE SYSDATE\nbatch no "+batchno+"\n transfer id \n consigned N\npersonnel Id "+loginID+"");

			CallableStatement cstmt = connect1.prepareCall("{call p_issue_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, companyIDfromhash);
			cstmt.setString(2, prnumber);
			cstmt.setString(3, lineItem);
			cstmt.setString(4, Branch_plant);
			cstmt.setString(5, receipt_id);
			cstmt.setString(6, item_id);
			cstmt.setTimestamp(7, pickdate);
			cstmt.setString(8, qtyreceived);
			cstmt.setTimestamp(9, pickdate);
			cstmt.setString(10, batchno);
			cstmt.setString(11, "");
			cstmt.setString(12, "N");
			cstmt.setString(13, loginID);
			cstmt.registerOutParameter(14, Types.INTEGER);
			cstmt.registerOutParameter(15, Types.VARCHAR);
			cstmt.executeQuery();
			issueId = cstmt.getInt(14);
			errmsg = cstmt.getString(15);

			connect1.commit();
			if (cstmt !=null){cstmt.close();}

			result = true;
		  }
		  catch (Exception e)
		  {
			e.printStackTrace();
			Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
			//reclog.info( "\n\n--------- Receiving QC Setting Update Status to N : \n" );
			db.doUpdate(Updatequery);

			errorMsgs.addElement("Error in Procedure p_issue_insert for Receipt ID :"+receipt_id+"");
			radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling Procedure p_issue_insert in HubReceiving QC ","Error occured while running p_issue_insert \nError Msg:\n"+e.getMessage()+"\nParameters \n\n Company Id: "+companyIDfromhash+"\nMR "+prnumber+"\nlineitem "+lineItem+" \nbranch plant "+Branch_plant+"\n receipt id "+receipt_id+"\n item id "+item_id+"\n pick Date "+pickdatest+"\n act pick "+qtyreceived+"\n SYSDATE \nbatch no "+batchno+"\n transfer id \n consigned N\npersonnel Id "+loginID+"");
			result = false;
			return result;
		  }

		  try
		  {
			if (issueId < 0)
			{
			  result = false;
			  errorMsgs.addElement("Error Calling p_issue_insert for Receipt ID :"+receipt_id+"");
			  Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
			  //reclog.info( "\n\n--------- Receiving QC Setting Update Status to N : \n" );
			  db.doUpdate(Updatequery);
			  errorMsgs.addElement("Something Went wrong issueId < 0 while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+"");
			  radian.web.emailHelpObj.sendnawazemail("Something Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+" in HubReceiving QC","\nSomething Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+" \nIssue ID is < 0 and errmsg "+errmsg+"");
			  radian.web.emailHelpObj.sendsteveemail("Something Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+" in HubReceiving QC","\nSomething Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+" \nIssue ID is < 0 and errmsg "+errmsg+"");

			  return result;
			}
			else
			{
			  try
			  {
				String updquery = "update issue set CONFIRMED='Y', date_delivered=to_date('"+Date_receivied+"','MM/DD/YYYY'), ship_confirm_user="+loginID+",ship_confirm_date=SYSDATE,QC_USER="+loginID+",QC_DATE=SYSDATE ";
				updquery +=" where ISSUE_ID = "+issueId+" ";
				//reclog.info(updquery);
				db.doUpdate(updquery);
				//reclog.info("Doing P_UPDATE_RLI_STATUS_SHIPPED");
				cs = connect1.prepareCall("{call P_UPDATE_RLI_STATUS_SHIPPED(?,?)}");
				cs.setInt(1,Integer.parseInt(prnumber));
				cs.setString(2,lineItem);
				cs.execute();
				result = true;
				}
				catch (Exception e)
				{
				  e.printStackTrace();
				  Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
				  //reclog.info( "\n\n--------- Receiving QC Setting Update Status to N : \n" );
				  db.doUpdate(Updatequery);
				  radian.web.emailHelpObj.sendnawazemail("Error in calling P_UPDATE_RLI_STATUS_SHIPPED","Error in calling P_UPDATE_RLI_STATUS_SHIPPED\nError Msg:\n"+e.getMessage()+"\nprnumber  "+prnumber+"   lineItem  "+lineItem+"");

				result = false;
				return result;
			  }
			}
		  }
		  catch (Exception e)
		  {
			e.printStackTrace();
			errorMsgs.addElement("Error for Receipt ID :"+receipt_id+"");
			Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
			//reclog.info( "\n\n--------- Receiving QC Setting Update Status to N : \n" );
			db.doUpdate(Updatequery);
			//radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling Procedure p_receipt_allocate  in HubReceiving QC ","Error occured while running p_receipt_allocate \nError Msg:\n"+e.getMessage()+"\nfor receipt Id "+receipt_id+"");
			result = false;
			return result;
		  }
		}
		else
		{
		  Updatequery="update receipt set APPROVED = 'N',QC_USER_ID = '" + loginID + "',QC_DATE=null where RECEIPT_ID = " + receipt_id + "";
		  //reclog.info( "\n\n--------- Receiving QC Setting Update Status to N : \n" );
		  db.doUpdate(Updatequery);
		  errorMsgs.addElement("Something Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+"");
		  radian.web.emailHelpObj.senddeveloperemail("Something Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+" in Receiving QC","\nSomething Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+"\n Result is not OK");
		  result = false;
		  return result;
		}
	  }

	  vLotSeqList.addElement(receipt_id);
	  result = true;
	}
	catch (Exception e)
    {
	  e.printStackTrace();
	  errorMsgs.addElement("Error Calling UpdateAppStatus for Receipt ID :"+receipt_id+"");
	  result = false;
	}
	finally
	{
	  if (cs != null) {
		try {
		  cs.close();
		} catch (Exception se) {se.printStackTrace();}
	  }
	}
	return result;
  }

  public Hashtable UpdateLine( Hashtable hD,String Branch_plant,String loginID)
  {
	Hashtable resulth=new Hashtable();
    boolean result=false;
    int Intreceipt_id=0;
    int RECEIPT_ID1=0;
	int RECEIPT_ID2=0;
    String errorcode="";
    Connection connect1;
    CallableStatement cs=null;

    String Radian_po= hD.get( "PURCHASE_ORDER" ) == null ? " " : hD.get( "PURCHASE_ORDER" ).toString();
    String PO_line= hD.get( "PURCHASE_ORDER_LINE" ) == null ? " " : hD.get( "PURCHASE_ORDER_LINE" ).toString();
    String Item_id= hD.get( "ITEM_ID" ) == null ? " " : hD.get( "ITEM_ID" ).toString();
    String trans_type= hD.get( "TRANS_TYPE" ) == null ? " " : hD.get( "TRANS_TYPE" ).toString();
    String Mfg_lot=hD.get( "MFG_LOT" ) == null ? " " : hD.get( "MFG_LOT" ).toString();
    String Date_mfgd= hD.get( "DATE_MFGD" ) == null ? " " : hD.get( "DATE_MFGD" ).toString();
    String Date_shiped= hD.get( "DATE_OF_SHIPMENT" ) == null ? " " : hD.get( "DATE_OF_SHIPMENT" ).toString();
    String Qty_recd= hD.get( "QTY_RECD" ) == null ? " " : hD.get( "QTY_RECD" ).toString();
    String Sel_bin= hD.get( "BIN_NAME" ) == null ? " " : hD.get( "BIN_NAME" ).toString();
    String xfer_req_id= hD.get( "XFER_REQ_ID" ) == null ? " " : hD.get( "XFER_REQ_ID" ).toString();
    String Oreceipt_id= hD.get( "RECEIPT_ID" ) == null ? " " : hD.get( "RECEIPT_ID" ).toString();
    String Date_Recieved= hD.get( "DATE_RECIEVED" ) == null ? " " : hD.get( "DATE_RECIEVED" ).toString();
    String Expiry_Date= hD.get( "EXPIRE_DATE" ) == null ? " " : hD.get( "EXPIRE_DATE" ).toString();
    String actshipDate= hD.get( "ACTUAL_SHIP_DATE" ) == null ? " " : hD.get( "ACTUAL_SHIP_DATE" ).toString();
    String invengrp= hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString();
    String numofkits = hD.get("NUMBER_OF_KITS").toString();
    String recnotes = hD.get("NOTES").toString();
	String lotStatus = hD.get("LOT_STATUS")==null?"":hD.get("LOT_STATUS").toString();
	if (lotStatus.length() == 0 ) {lotStatus = "Incoming";}
	String deliveryTicket = hD.get("DELIVERY_TICKET").toString();

    try
    {
      connect1=db.getConnection();

      if ( trans_type.equalsIgnoreCase( "IT" ) )
      {
        Intreceipt_id=Integer.parseInt( Oreceipt_id );
        Radian_po="0";
        PO_line="0";
      }

      try
      {
          cs = connect1.prepareCall("{call P_receipt_INSERT(?,?,?,?,?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
          cs.setInt(1,Integer.parseInt(Radian_po));
          cs.setInt(2,Integer.parseInt(PO_line));
          if (Item_id.trim().length() > 0){cs.setInt(3,Integer.parseInt(Item_id));}else{cs.setNull(3,java.sql.Types.INTEGER);}
          if (Mfg_lot.trim().length() > 0){cs.setString(4,Mfg_lot);}else{cs.setNull(4,java.sql.Types.VARCHAR);}
          if (Sel_bin.trim().length() > 0){cs.setString(5,Sel_bin);}else{cs.setNull(5,java.sql.Types.VARCHAR);}
          if (Date_mfgd.length() >1){cs.setTimestamp(6,radian.web.HTMLHelpObj.getDateFromString( "",Date_mfgd));}else{cs.setNull(6,java.sql.Types.DATE);}
          if (Date_shiped.length() >1){cs.setTimestamp(7,radian.web.HTMLHelpObj.getDateFromString( "",Date_shiped));}else{cs.setNull(7,java.sql.Types.DATE);}
          if (Qty_recd.trim().length() > 0){cs.setInt(8,Integer.parseInt(Qty_recd));}else{cs.setNull(8,java.sql.Types.NUMERIC);}
		  cs.setString(9,lotStatus);
          if (xfer_req_id.trim().length() > 0){cs.setInt(10,Integer.parseInt(xfer_req_id));}else{cs.setInt(10,0);}
          cs.setTimestamp(11,radian.web.HTMLHelpObj.getDateFromString( "",Date_Recieved));
          if (Branch_plant.trim().length() > 0){cs.setString(12,Branch_plant);}else{cs.setNull(12,java.sql.Types.VARCHAR);}
          if (loginID.trim().length() > 0){cs.setString(13,loginID);}else{cs.setNull(13,java.sql.Types.VARCHAR);}
          if (trans_type.trim().length() > 0){cs.setString(14,trans_type);}else{cs.setNull(14,java.sql.Types.VARCHAR);}
          if (Expiry_Date.length() >1){cs.setTimestamp(15,radian.web.HTMLHelpObj.getDateFromString( "",Expiry_Date));}else{cs.setNull(15,java.sql.Types.DATE);}
          if (trans_type.equalsIgnoreCase("IT")){cs.setInt(16,Intreceipt_id);}else{cs.setNull(16,java.sql.Types.NUMERIC);}
          if (actshipDate.length()== 10){cs.setTimestamp(17,radian.web.HTMLHelpObj.getDateFromString( "",actshipDate));}else{cs.setNull(17,java.sql.Types.DATE);}
          if (invengrp.trim().length() > 0){cs.setString(18,invengrp);}else{cs.setNull(18,java.sql.Types.VARCHAR);}
		  if (recnotes.trim().length() > 0){cs.setString(19,recnotes);}else{cs.setNull(19,java.sql.Types.VARCHAR);}

          cs.registerOutParameter(20,java.sql.Types.NUMERIC);
		  cs.registerOutParameter(21,java.sql.Types.NUMERIC);
          cs.registerOutParameter(22,java.sql.Types.VARCHAR);
		  if (deliveryTicket.trim().length() > 0){cs.setString(23,deliveryTicket);}else{cs.setNull(23,java.sql.Types.VARCHAR);}
          cs.execute();

          RECEIPT_ID1 = cs.getInt(20);
		  RECEIPT_ID2 = cs.getInt(21);
		  //if (RECEIPT_ID2 == null) {RECEIPT_ID2 = 0;}

          errorcode = BothHelpObjs.makeBlankFromNull(cs.getString(22));

          if (RECEIPT_ID1 == -1)
          {
              result = false;
              errorMsgs.addElement(errorcode);
          }
          else
          {
              result = true;
              Integer intreceipt_id = new Integer(RECEIPT_ID1);
              vLotSeqList.addElement(intreceipt_id);
			  if ( RECEIPT_ID2 > 0 )
			  {
				Integer intreceipt_id1=new Integer( RECEIPT_ID2 );
				vLotSeqList.addElement( intreceipt_id1 );
			  }
		  }
		  reclog.info("Receipt IDs created from P_receipt_insert for PO-Line "+Radian_po+"-"+PO_line+" Item "+Item_id+"  are  "+RECEIPT_ID1+"    "+RECEIPT_ID2+"    Personnel ID  "+loginID+"  Parts in Kit= "+numofkits+"");
      }
      catch (Exception e)
      {
          e.printStackTrace();
          radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling Procedure call P_receipt_INSERT in HubReceiving","Error occured while running call P_receipt_INSERT \nError Msg:\n"+e.getMessage()+"\n\n Parameters passed P_receipt_INSERT Radian_po "+Radian_po+"\nPO_line "+PO_line+" \n Item_id "+Item_id+"\n Mfg_lot "+Mfg_lot+"\n Bin "+Sel_bin+"\n Date_mfgd "+Date_mfgd+"\n Date_shiped "+Date_shiped+"\n Qty_recd "+Qty_recd+"\n Lot Status Incoming\n transfer id "+xfer_req_id+"\n Date_Recieved "+Date_Recieved+"\nBranch_plant "+Branch_plant+"\npersonnel Id "+loginID+"\n trans_type "+trans_type+"\nExpiry_Date "+Expiry_Date+"\nIT ReceiptID "+Intreceipt_id+"");
          throw e;
      }
      finally
      {
          connect1.commit();
          if (cs != null) {
            try {
                    cs.close();
            } catch (Exception se) {se.printStackTrace();}
          }
        }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      result=false;
    }

      resulth.put( "RCECIPT_ID1","" + RECEIPT_ID1 + "" );
      resulth.put( "RCECIPT_ID2","" + RECEIPT_ID2 + "" );
      resulth.put( "RESULT",new Boolean( result ) );
      return resulth;
    }

    public Hashtable UpdateLineBG( Hashtable hD,String Branch_plant,String loginID)
    {
      boolean result=false;
      Hashtable resulth=new Hashtable();
      Connection connect1;
      CallableStatement cs=null;
      int RECEIPT_ID1=0;
      int RECEIPT_ID2=0;
      String errorcode="";

      String Radian_po= hD.get( "PURCHASE_ORDER" ) == null ? " " : hD.get( "PURCHASE_ORDER" ).toString();
      //String PO_line= hD.get( "PURCHASE_ORDER_LINE" ) == null ? " " : hD.get( "PURCHASE_ORDER_LINE" ).toString();
      String Item_id= hD.get( "ITEM_ID" ) == null ? " " : hD.get( "ITEM_ID" ).toString() ;
      String trans_type= hD.get( "TRANS_TYPE" ) == null ? " " : hD.get( "TRANS_TYPE" ).toString() ;
      String Mfg_lot=hD.get( "MFG_LOT" ) == null ? " " : hD.get( "MFG_LOT" ).toString() ;
      String Qty_recd= hD.get( "QTY_RECD" ) == null ? " " : hD.get( "QTY_RECD" ).toString() ;
      String cat_part_no= hD.get( "CAT_PART_NO" ) == null ? " " : hD.get( "CAT_PART_NO" ).toString();
      String Date_Recieved= hD.get( "DATE_RECIEVED" ) == null ? " " : hD.get( "DATE_RECIEVED" ).toString();
      String invengrp= hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString();
      String Expiry_Date="01/01/2010";
      String Sel_bin="NBO";
      String polinetopass="";
      String recnotes = hD.get("NOTES").toString();
	  String deliveryTicket = hD.get("DELIVERY_TICKET").toString();

      try
      {
        //Integer LOT_UID=getNewUid();
	    connect1=db.getConnection();

        String polineQuery="select fx_no_buy_order_po_line(" + Radian_po + ",'" + cat_part_no + "',to_date('" + Date_Recieved + "','MM/DD/YYYY')) PO_LINE_VALUE from dual";
        DBResultSet dbrs=null;
        ResultSet rs=null;
        try
        {
          dbrs=db.doQuery( polineQuery );
          rs=dbrs.getResultSet();

          while ( rs.next() )
          {
            polinetopass=rs.getString( "PO_LINE_VALUE" ) == null ? "" : rs.getString( "PO_LINE_VALUE" );
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

        if ( polinetopass.trim().length() > 0 )
        {
	try
	{
	 cs = connect1.prepareCall("{call P_receipt_INSERT(?,?,?,?,?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	 cs.setInt(1,Integer.parseInt(Radian_po));
	 cs.setInt(2,Integer.parseInt(polinetopass));
	 if (Item_id.trim().length() > 0){cs.setInt(3,Integer.parseInt(Item_id));}else{cs.setNull(3,java.sql.Types.INTEGER);}
	 if (Mfg_lot.trim().length() > 0){cs.setString(4,Mfg_lot);}else{cs.setNull(4,java.sql.Types.VARCHAR);}
	 if (Sel_bin.trim().length() > 0){cs.setString(5,Sel_bin);}else{cs.setNull(5,java.sql.Types.VARCHAR);}
	 cs.setNull(6,java.sql.Types.DATE);
	 cs.setNull(7,java.sql.Types.DATE);
	 if (Qty_recd.trim().length() > 0)
	 {
	   BigDecimal bigponumber = new BigDecimal(Qty_recd);
	   cs.setBigDecimal( 8,bigponumber );
	   //cs.setInt(8,Integer.parseInt(Qty_recd));
	 }
	 else
	 {
	   cs.setNull(8,java.sql.Types.NUMERIC);
	 }
	 cs.setString(9,"Available");
	 cs.setInt(10,0);
	 cs.setTimestamp(11,radian.web.HTMLHelpObj.getDateFromString( "",Date_Recieved));
	 if (Branch_plant.trim().length() > 0){cs.setString(12,Branch_plant);}else{cs.setNull(12,java.sql.Types.VARCHAR);}
	 if (loginID.trim().length() > 0){cs.setString(13,loginID);}else{cs.setNull(13,java.sql.Types.VARCHAR);}
	 if (trans_type.trim().length() > 0){cs.setString(14,trans_type);}else{cs.setNull(14,java.sql.Types.VARCHAR);}
	 if (Expiry_Date.length() >1){cs.setTimestamp(15,radian.web.HTMLHelpObj.getDateFromString( "",Expiry_Date));}else{cs.setNull(15,java.sql.Types.DATE);}
	 cs.setNull(16,java.sql.Types.NUMERIC);
	 cs.setNull(17,java.sql.Types.DATE);
	 if (invengrp.trim().length() > 0){cs.setString(18,invengrp);}else{cs.setNull(18,java.sql.Types.VARCHAR);}
	 if (recnotes.trim().length() > 0){cs.setString(19,recnotes);}else{cs.setNull(19,java.sql.Types.VARCHAR);}

	 cs.registerOutParameter(20,java.sql.Types.NUMERIC);
	 cs.registerOutParameter(21,java.sql.Types.NUMERIC);
	 cs.registerOutParameter(22,java.sql.Types.VARCHAR);
	 if (deliveryTicket.trim().length() > 0){cs.setString(23,deliveryTicket);}else{cs.setNull(23,java.sql.Types.VARCHAR);}
	 cs.execute();

	  RECEIPT_ID1 = cs.getInt(20);
	  RECEIPT_ID2 = cs.getInt(21);
	  //if (RECEIPT_ID2 == null) {RECEIPT_ID2 = 0;}

	  errorcode = BothHelpObjs.makeBlankFromNull(cs.getString(22));

	  if (RECEIPT_ID1 == -1)
	  {
		  result = false;
		  errorMsgs.addElement(errorcode);
	  }
	  else
	  {
		  result = true;
		  Integer intreceipt_id = new Integer(RECEIPT_ID1);
		  vLotSeqList.addElement(intreceipt_id);
		  if (RECEIPT_ID2 > 0)
		  {
			Integer intreceipt_id1 = new Integer(RECEIPT_ID2);
			vLotSeqList.addElement(intreceipt_id1);
		  }
	  }
	  reclog.info("Receipt IDs created from P_receipt_insert for PO-Line "+Radian_po+"-"+polinetopass+" Item "+Item_id+"  are  "+RECEIPT_ID1+"    "+RECEIPT_ID2+"    Personnel ID  "+loginID+"");
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();
		 radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling Procedure call P_receipt_INSERT in HubReceiving for Non-Chemicals","Error occured while running call P_receipt_INSERT for Non-Chemicals\nError Msg:\n"+e.getMessage()+"\n\n Parameters passed P_receipt_INSERT Radian_po "+Radian_po+"\nPO_line "+polinetopass+" \n Item_id "+Item_id+"\n Mfg_lot "+Mfg_lot+"\n Bin "+Sel_bin+"\n Qty_recd "+Qty_recd+"\n Lot Status Incoming\n Date_Recieved "+Date_Recieved+"\nBranch_plant "+Branch_plant+"\npersonnel Id "+loginID+"\n trans_type "+trans_type+"\nExpiry_Date "+Expiry_Date+"\n");
		 throw e;
	 }
	 finally
	 {
		 connect1.commit();
		 if (cs != null) {
		   try {
				   cs.close();
		   } catch (Exception se) {se.printStackTrace();}
		 }
	 }
    }
    else
    {
      result=false;
    }
  }
  catch ( Exception e )
  {
    e.printStackTrace();
    errorMsgs.addElement( "Error for Radian PO :" + Radian_po + "" );
    result=false;
  }

  resulth.put( "RCECIPT_ID1","" + RECEIPT_ID1 + "" );
  resulth.put( "RCECIPT_ID2","" + RECEIPT_ID2 + "" );
  resulth.put( "RESULT",new Boolean( result ) );
  return resulth;
 }

 public Vector getAllopenreceiptQC( String Branch_id,String SortBy,String SortresultsBy,String invengrp,String searchtesxt,String searchlike,
									String searchfor ) throws Exception
 {
   DBResultSet dbrs=null;
   ResultSet rs=null;
   Vector result=new Vector();
   Hashtable summary=new Hashtable();

   String user_query="select a.RETURN_PR_NUMBER, a.RETURN_LINE_ITEM,a.NOTES,a.INVENTORY_GROUP,a.LOT_STATUS_ROOT_CAUSE, a.RESPONSIBLE_COMPANY_ID, a.LAST_MODIFIED_BY, a.LOT_STATUS_ROOT_CAUSE_NOTES,a.excess,a.RADIAN_PO,a.PO_LINE,a.ITEM_ID,a.MFG_LOT,a.BIN,a.RECEIPT_ID, a.QUANTITY_RECEIVED,to_char(sysdate,'mm/dd/yyyy') SYS_DATE, ";

   if ( SortBy.equalsIgnoreCase( "1" ) )
   {
	 user_query+=" a.DELIVERY_TICKET,a.CRITICAL,a.STORAGE_TEMP,ltrim(substr(a.STORAGE_TEMP,instr(a.STORAGE_TEMP,' '), vsize(a.STORAGE_TEMP)),' ') STORAGE_TEMP1,STORAGE_TEMP,a.MANAGE_KITS_AS_SINGLE_UNIT, a.COMPONENT_ID, a.MATERIAL_DESC, a.INVENTORY_GROUP_NAME, a.NUMBER_OF_KITS,a.QUALITY_CONTROL_ITEM,a.TRANSFER_REQUEST_ID,a.DOC_TYPE,";
	  user_query+=" a.QC_OK,a.CONSIGNED_PO,to_char(a.VENDOR_SHIP_DATE,'mm/dd/yyyy') VENDOR_SHIP_DATE,to_char(a.DATE_OF_RECEIPT,'mm/dd/yyyy') DATE_OF_RECEIPT,a.TRANSFER_RECEIPT_ID,a.ORIG_MFG_LOT,a.LINE_DESC,a.LOT_STATUS,";
	 user_query+=" a.INDEFINITE_SHELF_LIFE,to_char(a.EXPIRE_DATE,'mm/dd/yyyy') EXPIRE_DATE,to_char(a.DATE_OF_SHIPMENT,'mm/dd/yyyy') DATE_OF_SHIPMENT,";
	 user_query+=" to_char(a.DATE_OF_MANUFACTURE,'mm/dd/yyyy') DATE_OF_MANUFACTURE,min(" + SortresultsBy +") OVER (partition by a.RECEIPT_ID) AS sort_order from receipt_description_view a where approved = 'N' and BRANCH_PLANT=" + Branch_id + "";
   }
   else if ( SortBy.equalsIgnoreCase( "2" ) )
   {
	 user_query+=" a.DELIVERY_TICKET,'' CRITICAL,to_char(a.DATE_OF_RECEIPT,'mm/dd/yyyy') DATE_OF_RECEIPT,to_char(b.end_date,'mm/dd/yyyy') END_DATE,b.PART_DESCRIPTION,b.PACKAGING,b.COMPANY_ID from receipt a,no_buy_order_receivingqc_view b ";
	 user_query+=" where a.radian_po=b.radian_po and a.PO_LINE = b.LINE_ITEM and a.APPROVED = 'N' and HUB=" + Branch_id + "";
   }

   if ( invengrp.length() > 0 && !"All".equalsIgnoreCase( invengrp ) )
   {
	 user_query+=" and INVENTORY_GROUP = '" + invengrp + "' ";
   }

   if ( searchtesxt.trim().length() > 0 )
   {
	 if ( "Like".equalsIgnoreCase( searchfor ) )
	 {
	   user_query+=" and lower(" + searchlike + ") like lower('%" + searchtesxt.trim() + "%') ";
	 }
	 else if ( "Equals".equalsIgnoreCase( searchfor ) )
	 {
	   user_query+=" and " + searchlike + " = '" + searchtesxt.trim() + "' ";
	 }
   }

   if ( SortBy.equalsIgnoreCase( "1" ) )
   {
	 user_query+=" Order by sort_order,a.RECEIPT_ID ";
   }
   else
   {
	 user_query+=" Order by " + SortresultsBy + " ";
   }

   int count=0;
   summary.put( "TOTAL_NUMBER",new Integer( count ) );
   result.addElement( summary );

   int num_rec=0;

   try
   {
	 dbrs=db.doQuery( user_query );
	 rs=dbrs.getResultSet();

	 while ( rs.next() )
	 {
	   num_rec++;
	   Hashtable hD=new Hashtable();
	   hD.put( "PURCHASE_ORDER",rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ) );
	   hD.put( "PURCHASE_ORDER_LINE",rs.getString( "PO_LINE" ) == null ? "" : rs.getString( "PO_LINE" ) );
	   hD.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
	   hD.put( "SYS_DATE",rs.getString( "SYS_DATE" ) == null ? "" : rs.getString( "SYS_DATE" ) );
	   hD.put( "CRITICAL",rs.getString( "CRITICAL" ) == null ? "" : rs.getString( "CRITICAL" ) );
	   hD.put( "RETURN_PR_NUMBER",rs.getString( "RETURN_PR_NUMBER" ) == null ? "" : rs.getString( "RETURN_PR_NUMBER" ) );
	   hD.put( "RETURN_LINE_ITEM",rs.getString( "RETURN_LINE_ITEM" ) == null ? "" : rs.getString( "RETURN_LINE_ITEM" ) );
	   hD.put( "DELIVERY_TICKET",rs.getString( "DELIVERY_TICKET" ) == null ? "" : rs.getString( "DELIVERY_TICKET" ) );

	   String temp="";
	   if ( SortBy.equalsIgnoreCase( "2" ) )
	   {
		 temp=rs.getString( "PART_DESCRIPTION" ) == null ? "" : rs.getString( "PART_DESCRIPTION" );
		 hD.put( "END_DATE",rs.getString( "END_DATE" ) == null ? "" : rs.getString( "END_DATE" ) );
		 hD.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? "" : rs.getString( "PACKAGING" ) );
		 hD.put( "LOT_STATUS","" );
		 hD.put( "DOM","" );
		 hD.put( "DOS","" );
		 hD.put( "EXPIRE_DATE","" );
		 hD.put( "INDEFINITE_SHELF_LIFE","" );
		 hD.put( "TRANSFER_RECEIPT_ID","" );
		 hD.put( "ORIG_MFG_LOT","" );
		 hD.put( "COMPANY_ID",rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ) );
		 hD.put( "ACTUAL_SHIP_DATE","" );
		 hD.put( "CONSIGNED_PO","" );
		 hD.put( "QC_OK","" );
		 hD.put( "DOC_TYPE","" );
		 hD.put( "TRANSFER_REQUEST_ID","" );
		 hD.put( "QUALITY_CONTROL_ITEM","N" );
		 hD.put( "MANAGE_KITS_AS_SINGLE_UNIT","Y" );
		 hD.put( "COMPONENT_ID","" );
		 hD.put( "MATERIAL_DESC","" );
		 hD.put( "INVENTORY_GROUP_NAME",rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ) );
		 hD.put( "NUMBER_OF_KITS","" );
		 hD.put( "STORAGE_TEMP","" );
		 hD.put( "STORAGE_TEMP1","" );
	   }
	   else
	   {
		 temp=rs.getString( "LINE_DESC" ) == null ? "" : rs.getString( "LINE_DESC" );
		 hD.put( "END_DATE","" );
		 hD.put( "PACKAGING","" );
		 hD.put( "LOT_STATUS",rs.getString( "LOT_STATUS" ) == null ? "" : rs.getString( "LOT_STATUS" ) );
		 hD.put( "DOM",rs.getString( "DATE_OF_MANUFACTURE" ) == null ? "" : rs.getString( "DATE_OF_MANUFACTURE" ) );
		 hD.put( "DOS",rs.getString( "DATE_OF_SHIPMENT" ) == null ? "" : rs.getString( "DATE_OF_SHIPMENT" ) );
		 hD.put( "EXPIRE_DATE",rs.getString( "EXPIRE_DATE" ) == null ? "" : rs.getString( "EXPIRE_DATE" ) );
		 hD.put( "INDEFINITE_SHELF_LIFE",rs.getString( "INDEFINITE_SHELF_LIFE" ) == null ? "" : rs.getString( "INDEFINITE_SHELF_LIFE" ) );
		 hD.put( "TRANSFER_RECEIPT_ID",rs.getString( "TRANSFER_RECEIPT_ID" ) == null ? "" : rs.getString( "TRANSFER_RECEIPT_ID" ) );
		 hD.put( "ORIG_MFG_LOT",rs.getString( "ORIG_MFG_LOT" ) == null ? "" : rs.getString( "ORIG_MFG_LOT" ) );
		 hD.put( "COMPANY_ID","" );
		 hD.put( "ACTUAL_SHIP_DATE",rs.getString( "VENDOR_SHIP_DATE" ) == null ? "" : rs.getString( "VENDOR_SHIP_DATE" ) );
		 hD.put( "CONSIGNED_PO",rs.getString( "CONSIGNED_PO" ) == null ? "" : rs.getString( "CONSIGNED_PO" ) );
		 hD.put( "QC_OK",rs.getString( "QC_OK" ) == null ? "" : rs.getString( "QC_OK" ) );
		 hD.put( "DOC_TYPE",rs.getString( "DOC_TYPE" ) == null ? "" : rs.getString( "DOC_TYPE" ) );
		 hD.put( "TRANSFER_REQUEST_ID",rs.getString( "TRANSFER_REQUEST_ID" ) == null ? "" : rs.getString( "TRANSFER_REQUEST_ID" ) );
		 hD.put( "QUALITY_CONTROL_ITEM",rs.getString( "QUALITY_CONTROL_ITEM" ) == null ? "N" : rs.getString( "QUALITY_CONTROL_ITEM" ) );
		 hD.put( "MANAGE_KITS_AS_SINGLE_UNIT",rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) == null ? "" : rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) );
		 hD.put( "COMPONENT_ID",rs.getString( "COMPONENT_ID" ) == null ? "" : rs.getString( "COMPONENT_ID" ) );
		 hD.put( "MATERIAL_DESC",rs.getString( "MATERIAL_DESC" ) == null ? "" : rs.getString( "MATERIAL_DESC" ) );
		 hD.put( "INVENTORY_GROUP_NAME",rs.getString( "INVENTORY_GROUP_NAME" ) == null ? "" : rs.getString( "INVENTORY_GROUP_NAME" ) );
		 hD.put( "NUMBER_OF_KITS",rs.getString( "NUMBER_OF_KITS" ) == null ? "" : rs.getString( "NUMBER_OF_KITS" ) );
		 hD.put( "STORAGE_TEMP",rs.getString( "STORAGE_TEMP" ) == null ? "" : rs.getString( "STORAGE_TEMP" ) );
		 hD.put( "STORAGE_TEMP1",rs.getString( "STORAGE_TEMP1" ) == null ? "" : rs.getString( "STORAGE_TEMP1" ) );
	   }

	   int size=temp.length();
	   String desc;
	   if ( size > 30 )
	   {
		 String temp1=temp.substring( 0,29 );
		 String temp2=temp.substring( 30 );
		 desc=temp1 + "<BR>" + temp2;
	   }
	   else
	   {
		 desc=temp;
	   }

	   hD.put( "LINE_DESC",desc );
	   hD.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? "" : rs.getString( "MFG_LOT" ) );
	   hD.put( "DATE_RECIEVED",rs.getString( "DATE_OF_RECEIPT" ) == null ? "" : rs.getString( "DATE_OF_RECEIPT" ) );
	   hD.put( "BIN_NAME",rs.getString( "BIN" ) == null ? "" : rs.getString( "BIN" ) );
	   hD.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? "" : rs.getString( "RECEIPT_ID" ) );
	   hD.put( "QUANTITY_RECEIVED",rs.getString( "QUANTITY_RECEIVED" ) == null ? "" : rs.getString( "QUANTITY_RECEIVED" ) );
	   hD.put( "NO_OF_LABELS",rs.getString( "QUANTITY_RECEIVED" ) == null ? "" : rs.getString( "QUANTITY_RECEIVED" ) );
	   hD.put( "DOSTATUSUPDATE","" );
	   hD.put( "LINE_STATUS","" );
	   hD.put( "EXCESS_MATERIAL",rs.getString( "excess" ) == null ? "" : rs.getString( "excess" ) );
	   hD.put( "TERMINAL_ROOT_CAUSE",rs.getString( "LOT_STATUS_ROOT_CAUSE" ) == null ? "" : rs.getString( "LOT_STATUS_ROOT_CAUSE" ) );
	   hD.put( "TERMINAL_COMPANY",rs.getString( "RESPONSIBLE_COMPANY_ID" ) == null ? "" : rs.getString( "RESPONSIBLE_COMPANY_ID" ) );
	   hD.put( "TERMINAL_NOTES",rs.getString( "LOT_STATUS_ROOT_CAUSE_NOTES" ) == null ? "" : rs.getString( "LOT_STATUS_ROOT_CAUSE_NOTES" ) );
	   hD.put( "INVENTORY_GROUP",rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ) );
	   hD.put( "CERT_UPDATE","" );
	   hD.put( "NOTES",rs.getString( "NOTES" ) == null ? "" : rs.getString( "NOTES" ) );

	   result.addElement( hD );
	 }
   }
   catch ( Exception e )
   {
	 e.printStackTrace();
	 result=null;
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
   result.setElementAt( recsum,0 );
   result.trimToSize();
   return result;
 }
}