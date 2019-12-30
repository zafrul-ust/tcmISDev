package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput
 * @version
 *
 * 10-31-02
 * Stoped calling JDE_RECEIPT
 *
 * 11-14-02
 * adding receipt notes
 * 07-02-03 - Checking the lenght or roo cause notes
 * 07-30-03 - No Need to break up the Item_desc it auto wraps itself
 * 08-06-03 - Escaping the ' in root cause notes
 * 08-15-03 - Sending emails through common object
 * 09-11-03 - Showing Rootcause comments
 * 10-22-03 - Not joining seagate_bindropdown to get the history of Bins the item has been in
 * 11-24-03 - Cannot put the recipt in a pickable status if you don't have quality control permissions for items which are quality controled
 * 12-30-03 - Updating the transaction Date and showing the Owener company ID on the logistics page
 * 01-12-04 - Added Original Receipt ID, UNIT_COST to the display, also added Radian_po,Po_line to the excel sheet
 * 01-30-04 - Not updating Transaction Date
 * 02-02-04 - Showing DATE_OF_RECEIPT,DATE_OF_MANUFACTURE in the excel sheet
 * 02-16-04 - Shwoing CLIENT_PART_NOS in the excel sheet
 * 02-19-04 - Showing Only Active BINs. And Checking for Exp Date format
 * 03-02-04 - Not Overwritting LOT_STATUS_ROOT_CAUSE, LOT_STATUS_ROOT_CAUSE_NOTES, RESPONSIBLE_COMPANY_ID when a receipt is moved out of terminal lot status
 * 03-23-04 - Decoding Expire Date 01/01/3000 to Indefinite
 * 04-19-04 - Changeing the inventory group drop down to show INVENTORY_GROUP_NAME in the display of drop dowm. Getting the Hub list from the HUB_PREF
 * table instead of the user_group_member table. Changing the receiving to accomodate receiving kits with different parts with different MFG_LOT,EXP_DATE and BIN.
 * 06-01-04 - Breaking up the search to make the query faster
 * 06-23-04 - Giving an option to show Include History, so they can update exp date on receipts that have been all issued out.
 * 07-29-04 - Showing Storage Temp
 * 12-08-04 - When searching for expire date earching on receipt.expire date rather than the receipt component expire date.
 * 05-03-05 - Showing and giving the capability of updating Date of Shipment
 */

import java.sql.*;
import java.util.*;

import oracle.sql.CLOB;

import com.tcmis.common.admin.process.HubInventoryGroupProcess;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;

@SuppressWarnings({"unchecked","rawtypes"})
public class HubInvenLabelsTables
{
  protected TcmISDBModel db;
  private Vector vLotSeqList=null;
  private Vector errorMsgs=null;
  private String personnelId;
  private String companyId;
  
  private static org.apache.log4j.Logger logisticsLog = org.apache.log4j.Logger.getLogger(HubInvenLabelsTables.class);

  public HubInvenLabelsTables()
  {
    vLotSeqList=new Vector();
    errorMsgs=new Vector();
  }

  public HubInvenLabelsTables( TcmISDBModel db )
  {
    this.db=db;
    vLotSeqList=new Vector();
    errorMsgs=new Vector();
  }

  public void setDB( TcmISDBModel db )
  {
    this.db=db;
  }

  public void setLotSeqList( Vector vLotSeq )
  {
    this.vLotSeqList=vLotSeq;
  }

  public Vector getLotSeqList()
  {
    return this.vLotSeqList;
  }

  public Vector getErrMsgs()
  {
    return this.errorMsgs;
  }
  
 
  public Vector getAllopenOrder( String branchPlant,String SearchText,String SortBy,String expdays,String inventoryGroup,String showneedingprint,
			 String searchlike,String searchfor,String showissuedreceipts,Collection hubInventoryGroupOvBeanColl,String lotStatus, String transactiondate, String receivedbyid, String personnelId, String companyId) throws Exception
  {
	  this.personnelId = personnelId;
	  this.companyId = companyId;
	  return this.getAllopenOrder(branchPlant,SearchText,SortBy, expdays,inventoryGroup, showneedingprint,
				 searchlike,searchfor,showissuedreceipts,hubInventoryGroupOvBeanColl,lotStatus, transactiondate, receivedbyid);
  }

  public Vector getAllopenOrder( String branchPlant,String SearchText,String SortBy,String expdays,String inventoryGroup,String showneedingprint,
								 String searchlike,String searchfor,String showissuedreceipts,Collection hubInventoryGroupOvBeanColl,String lotStatus,String transactiondate,String receivedbyid) throws Exception
  {

    DBResultSet dbrs=null;
    ResultSet rs=null;
    Vector result=new Vector();
    Hashtable summary=new Hashtable();
    boolean flagforwhere=false;
    int expdaysi=0;

    String user_query="select to_char(MFG_LABEL_EXPIRE_DATE,'mm/dd/yyyy') MFG_LABEL_EXPIRE_DATE, INTERNAL_RECEIPT_NOTES,INCOMING_TESTING,QA_STATEMENT,NET_PENDING_ADJ,UNIT_LABEL_CAT_PART_NO,OPS_ENTITY_ID,CURRENCY_ID,CUSTOMER_RECEIPT_ID,SPECS,DO_NUMBER_REQUIRED,POLCHEM_IG,DOT,UNIT_LABEL_PRINTED,to_char(QC_DATE,'mm/dd/yyyy') QC_DATE," +
            "PO_NUMBER,DOC_NUM,DOC_NUM_LINE," +
            "DELIVERY_TICKET,CATALOG_ID, CAT_PART_NO, CATALOG_COMPANY_ID, ITEM_TYPE, NEW_CHEM_REQUEST_ID,RECEIPT_PACKAGING," +
            "RECEIPT_DOCUMENT_AVAILABLE,to_char(DATE_OF_SHIPMENT,'mm/dd/yyyy') DATE_OF_SHIPMENT, " +
            "to_char(VENDOR_SHIP_DATE,'mm/dd/yyyy') VENDOR_SHIP_DATE, LABEL_STORAGE_TEMP,MANAGE_KITS_AS_SINGLE_UNIT," +
            "COMPONENT_ID,MATERIAL_DESC,INVENTORY_GROUP_NAME,NUMBER_OF_KITS,CLIENT_PART_NOS," +
            "to_char(DATE_OF_RECEIPT,'mm/dd/yyyy') DATE_OF_RECEIPT,to_char(DATE_OF_MANUFACTURE,'mm/dd/yyyy') DATE_OF_MANUFACTURE,";
	user_query+="PO_LINE,RADIAN_PO,UNIT_COST,ORIGINAL_RECEIPT_ID,OWNER_COMPANY_ID,PICKABLE,QUALITY_CONTROL_ITEM," +
            "QUANTITY_RECEIVED,INVENTORY_GROUP,";
	user_query+="LOT_STATUS_ROOT_CAUSE,RESPONSIBLE_COMPANY_ID,LAST_MODIFIED_BY,LOT_STATUS_ROOT_CAUSE_NOTES,LOT_STATUS_AGE,";
	user_query+="NOTES,item_id,hub,RECERT_NUMBER,to_char(LAST_PRINT_DATE,'dd Mon yyyy hh24:mi') LAST_PRINT_DATE," +
            "ITEM_DESC,SEARCH,LOT_STATUS, bin, MFG_LOT, receipt_id, quantity," +
            "no_of_labels,to_char(EXPIRE_DATE1,'mm/dd/yyyy') EXPIRE_DATE1,to_char(MINIMUM_EXPIRE_DATE,'mm/dd/yyyy') MINIMUM_EXPIRE_DATE," +
            "DECODE( EXPIRE_DATE,NULL, 'Indefinite',TO_DATE('01/01/3000', 'mm/dd/yyyy'), 'Indefinite', to_char(EXPIRE_DATE,'mm/dd/yyyy')) EXPIRE_DATE, LOCK_EXPIRE_DATE, QUALITY_TRACKING_NUMBER,HUB_NAME,SUPPLIER_CAGE_CODE,to_char(DATE_OF_REPACKAGING,'mm/dd/yyyy') DATE_OF_REPACKAGING, RECEIVING_STATUS, COUNTRY_OF_ORIGIN,COUNTRY_OF_ORIGIN_NAME, AUTOMATED_PUTAWAY from logistics_view ";
    user_query+="where ";
   

  if ( !branchPlant.trim().equalsIgnoreCase("All"))
	{
    user_query+="HUB =" + branchPlant + " ";
    flagforwhere=true;
  }

    try
    {
      expdaysi=Integer.parseInt( expdays );
    }
    catch ( Exception eee )
    {
      expdaysi=0;
    }

    if ( SearchText.trim().length() > 0 && searchlike.equalsIgnoreCase("ORIGINAL_RECEIPT_ID"))
	{
    if ( flagforwhere )
		  user_query+="and ";

    if ( "Like".equalsIgnoreCase( searchfor ) )
	  {
		user_query+=" ( lower(" + searchlike + ") like lower('%" + SearchText.trim() + "%') ";
        user_query+=" or lower(RECEIPT_ID) like lower('%" + SearchText.trim() + "%') ) ";
      }
	  else if ( "Equals".equalsIgnoreCase( searchfor ) )
	  {
		user_query+=" ( " + searchlike + " = " + SearchText.trim() + " ";
        user_query+=" or RECEIPT_ID = " + SearchText.trim() + " ) ";
      }
    flagforwhere=true;
   }
   else if ( SearchText.trim().length() > 0 && searchlike.equalsIgnoreCase("RECEIPT_ID"))
	{
    if ( flagforwhere )
		  user_query+="and ";

    if ( "Like".equalsIgnoreCase( searchfor ) )
	  {
        user_query+=" lower(RECEIPT_ID) like lower('%" + SearchText.trim() + "%') ";
      }
	  else if ( "Equals".equalsIgnoreCase( searchfor ) )
	  {
		user_query+=" RECEIPT_ID = " + SearchText.trim() + " ";
      }
    flagforwhere=true;
   }
   else if ( SearchText.trim().length() > 0 && !searchlike.equalsIgnoreCase("CAT_PART_NO"))
	{
    if ( flagforwhere )
		  user_query+="and ";

    if ( "Like".equalsIgnoreCase( searchfor ) )
	  {
		user_query+=" lower(" + searchlike + ") like lower('%" + SearchText.trim() + "%') ";
	  }
	  else if ( "Equals".equalsIgnoreCase( searchfor ) )
	  {
		user_query+=" " + searchlike + " = '" + SearchText.trim() + "' ";
	  }
    flagforwhere=true;
  }

  else if ( !branchPlant.trim().equalsIgnoreCase("All") && SearchText.trim().length() > 0 && searchlike.equalsIgnoreCase("CAT_PART_NO"))
  {
    if ( flagforwhere )
		  user_query+="and ";

   user_query+=" (item_id, inventory_group) IN (SELECT cpig_cpi.item_id,cpig_cpi.inventory_group ";
   user_query+=" FROM cpig_cpi_view cpig_cpi, inventory_group_definition igd ";
   user_query+=" WHERE cpig_cpi.inventory_group = igd.inventory_group ";
   user_query+=" AND igd.hub = '"+branchPlant+"' ";
   user_query+=" AND cpig_cpi.cpig_status in ('A','D') ";
   if ( "Like".equalsIgnoreCase( searchfor ) )
   {
   user_query+=" AND cpig_cpi.cat_part_no like '%" + SearchText.trim() + "%') ";
   }
   else
   {
     user_query+="AND cpig_cpi.cat_part_no ='" + SearchText.trim() + "') ";
   }
   flagforwhere=true;
  }
  else if ( branchPlant.trim().equalsIgnoreCase("All") && SearchText.trim().length() > 0 && searchlike.equalsIgnoreCase("CAT_PART_NO"))
  {
    if ( flagforwhere )
		  user_query+="and ";

   if ( "Like".equalsIgnoreCase( searchfor ) )
   {
   user_query+=" RECEIPT_CAT_PART_NO like '%" + SearchText.trim() + "%' ";
   }
   else
   {
     user_query+=" RECEIPT_CAT_PART_NO ='" + SearchText.trim() + "' ";
   }
   flagforwhere=true;
  }


  boolean iscollection = false;
  if (hubInventoryGroupOvBeanColl !=null)
	{
	HubInventoryGroupProcess hubInventoryGroupProcess = new
		 HubInventoryGroupProcess("hub");
	iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanColl,
																 branchPlant, inventoryGroup);
	}

	if ( inventoryGroup != null && inventoryGroup.length() >0 &&
		 !inventoryGroup.equalsIgnoreCase( "ALL" ) )
	{
	  if ( iscollection )
	  {
		if ( flagforwhere )
		  user_query+="and ";

		user_query += " INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_COLLECTION where HUB = '" + branchPlant +
		   "' and INVENTORY_GROUP_COLLECTION = '" + inventoryGroup +"') ";
	  }
	  else if ( inventoryGroup.length() > 0 )
	  {
		if ( flagforwhere )
				user_query+="and INVENTORY_GROUP ='" + inventoryGroup + "' ";
		else
		{

				user_query+=" INVENTORY_GROUP ='" + inventoryGroup + "' ";
		}
	  }
    flagforwhere=true;
  }
	else
	{
		String userIgHub = "";
        if ( !branchPlant.trim().equalsIgnoreCase("All"))
         userIgHub =  " hub = '"+branchPlant+"' and ";

        if (flagforwhere)	        
            user_query+=" and INVENTORY_GROUP IN ( select inventory_group from user_inventory_group where "+userIgHub+" personnel_id = " + this.personnelId + " and company_id = '" + this.companyId +"') " ;
		else
			user_query+=" INVENTORY_GROUP IN ( select inventory_group from user_inventory_group where "+userIgHub+" personnel_id = " + this.personnelId + " and company_id = '" + this.companyId + "') ";
	}

    if ( expdays.length() > 0 )
    {
      if ( flagforwhere )
        user_query+="and RECEIPT_EXPIRE_DATE < sysdate + " + expdaysi + " ";
      else
        user_query+=" RECEIPT_EXPIRE_DATE < sysdate + " + expdaysi + " ";
    }

	if ( "Yes".equalsIgnoreCase(showneedingprint) )
	{
	  if ( flagforwhere )
		user_query+="and nvl(PICKABLE,'N') <> 'Y' ";
	  else
	  {
		user_query+=" nvl(PICKABLE,'N') <> 'Y' ";
	  }
	}

    if ( lotStatus.length() > 0 )
	{
	  if ( flagforwhere )
		  user_query+="and LOT_STATUS in (" + lotStatus + ") ";
	  else
		{
		  user_query+=" LOT_STATUS in (" + lotStatus + ") ";
		}
	 }
    
    if(transactiondate.length() > 0)
    {
    	if ( flagforwhere )
			user_query+="and to_char(TRANSACTION_DATE,'MM/DD/YYYY') ='" + transactiondate + "' ";
    }
    
    if(receivedbyid.length() > 0)
    {
    	if ( flagforwhere )
			user_query+="and RECEIVER_ID ='" + receivedbyid + "' ";
    }
    

    if ( !"Yes".equalsIgnoreCase( showissuedreceipts ) )
	{
	  if ( flagforwhere )
		user_query+="and QUANTITY <> 0 ";
	  else
	  {
		user_query+=" QUANTITY <> 0 ";
	  }
	}

    if (SortBy !=null && SortBy.length() >0)
    {
       user_query+=" order by "+SortBy+"";
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
        Hashtable hD=new Hashtable(37);

        hD.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
        hD.put( "ITEM_DESC",rs.getString( "ITEM_DESC" ) == null ? "" : rs.getString( "ITEM_DESC" ) );
        hD.put( "LOT_STATUS",rs.getString( "LOT_STATUS" ) == null ? "" : rs.getString( "LOT_STATUS" ) );
        hD.put( "OLD_LOT_STATUS",rs.getString( "LOT_STATUS" ) == null ? "" : rs.getString( "LOT_STATUS" ) );
        hD.put( "BIN",rs.getString( "BIN" ) == null ? "" : rs.getString( "BIN" ) );
        hD.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? "" : rs.getString( "MFG_LOT" ) );
        hD.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? "" : rs.getString( "RECEIPT_ID" ) );
        hD.put( "QUANTITY",rs.getString( "QUANTITY" ) == null ? "" : rs.getString( "QUANTITY" ) );
        hD.put( "NO_OF_LABELS",rs.getString( "NO_OF_LABELS" ) == null ? "" : rs.getString( "NO_OF_LABELS" ) );
        hD.put( "RECERT_NUMBER",rs.getString( "RECERT_NUMBER" ) == null ? "" : rs.getString( "RECERT_NUMBER" ) );
        hD.put( "USERCHK","" );
        hD.put( "DOSTATUSUPDATE","" );
        hD.put( "LOTSTATUSUPDATE","" );
        hD.put( "BIN_NAME",rs.getString( "BIN" ) == null ? "" : rs.getString( "BIN" ) );
        hD.put( "EXPIRE_DATE",rs.getString( "EXPIRE_DATE" ) == null ? "" : rs.getString( "EXPIRE_DATE" ) );
        hD.put( "LAST_PRINT_DATE",rs.getString( "LAST_PRINT_DATE" ) == null ? "" : rs.getString( "LAST_PRINT_DATE" ) );
        hD.put( "LINE_STATUS","" );
        hD.put( "NOTES",rs.getString( "NOTES" ) == null ? "" : rs.getString( "NOTES" ) );
        hD.put( "LOT_STATUS_AGE",rs.getString( "LOT_STATUS_AGE" ) == null ? "" : rs.getString( "LOT_STATUS_AGE" ) );
        hD.put( "TERMINAL_ROOT_CAUSE",rs.getString( "LOT_STATUS_ROOT_CAUSE" ) == null ? "" : rs.getString( "LOT_STATUS_ROOT_CAUSE" ) );
        hD.put( "TERMINAL_COMPANY",rs.getString( "RESPONSIBLE_COMPANY_ID" ) == null ? "" : rs.getString( "RESPONSIBLE_COMPANY_ID" ) );
        hD.put( "TERMINAL_NOTES",rs.getString( "LOT_STATUS_ROOT_CAUSE_NOTES" ) == null ? "" : rs.getString( "LOT_STATUS_ROOT_CAUSE_NOTES" ) );
        hD.put( "INVENTORY_GROUP",rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ) );
        hD.put( "QUANTITY_RECEIVED",rs.getString( "QUANTITY_RECEIVED" ) == null ? "" : rs.getString( "QUANTITY_RECEIVED" ) );
        hD.put( "QUALITY_CONTROL_ITEM",rs.getString( "QUALITY_CONTROL_ITEM" ) == null ? "N" : rs.getString( "QUALITY_CONTROL_ITEM" ) );
        hD.put( "PICKABLE",rs.getString( "PICKABLE" ) == null ? "" : rs.getString( "PICKABLE" ) );
        hD.put( "OWNER_COMPANY_ID",rs.getString( "OWNER_COMPANY_ID" ) == null ? "" : rs.getString( "OWNER_COMPANY_ID" ) );
        hD.put( "CERTUPDATE","" );
        hD.put( "EXPIREDATEUPDATE","" );
        hD.put( "ORIGINAL_RECEIPT_ID",rs.getString( "ORIGINAL_RECEIPT_ID" ) == null ? "" : rs.getString( "ORIGINAL_RECEIPT_ID" ) );
        hD.put( "UNIT_COST",rs.getString( "UNIT_COST" ) == null ? "" : rs.getString( "UNIT_COST" ) );
        hD.put( "CURRENCY_ID",rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ) );
        hD.put( "RADIAN_PO",rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ) );
        hD.put( "PO_LINE",rs.getString( "PO_LINE" ) == null ? "" : rs.getString( "PO_LINE" ) );
        hD.put( "DATE_OF_MANUFACTURE",rs.getString( "DATE_OF_MANUFACTURE" ) == null ? "" : rs.getString( "DATE_OF_MANUFACTURE" ) );
        hD.put( "DATE_OF_RECEIPT",rs.getString( "DATE_OF_RECEIPT" ) == null ? "" : rs.getString( "DATE_OF_RECEIPT" ) );

        CLOB clob= (CLOB) rs.getClob("CLIENT_PART_NOS");
        hD.put( "CLIENT_PART_NOS",(clob == null || clob.isEmptyLob()) ? "" : clob.getSubString((long)1,(int)clob.length()));

        hD.put("MANAGE_KITS_AS_SINGLE_UNIT",rs.getString("MANAGE_KITS_AS_SINGLE_UNIT")==null?"":rs.getString("MANAGE_KITS_AS_SINGLE_UNIT"));
        hD.put("COMPONENT_ID",rs.getString("COMPONENT_ID")==null?"":rs.getString("COMPONENT_ID"));
        hD.put("MATERIAL_DESC",rs.getString("MATERIAL_DESC")==null?"":rs.getString("MATERIAL_DESC"));
        hD.put("INVENTORY_GROUP_NAME",rs.getString("INVENTORY_GROUP_NAME")==null?"":rs.getString("INVENTORY_GROUP_NAME"));
        hD.put("NUMBER_OF_KITS",rs.getString("NUMBER_OF_KITS")==null?"":rs.getString("NUMBER_OF_KITS"));
        hD.put("LABEL_STORAGE_TEMP",rs.getString("LABEL_STORAGE_TEMP")==null?"":rs.getString("LABEL_STORAGE_TEMP"));
        hD.put("DATE_OF_SHIPMENT",rs.getString("DATE_OF_SHIPMENT")==null?"":rs.getString("DATE_OF_SHIPMENT"));
        hD.put("VENDOR_SHIP_DATE",rs.getString("VENDOR_SHIP_DATE")==null?"":rs.getString("VENDOR_SHIP_DATE"));
        hD.put("RECEIPT_DOCUMENT_AVAILABLE",rs.getString("RECEIPT_DOCUMENT_AVAILABLE")==null?"":rs.getString("RECEIPT_DOCUMENT_AVAILABLE"));
        hD.put("RECEIPT_PACKAGING",rs.getString("RECEIPT_PACKAGING")==null?"":rs.getString("RECEIPT_PACKAGING"));

        hD.put("CATALOG_ID",rs.getString("CATALOG_ID")==null?"":rs.getString("CATALOG_ID"));
        hD.put("CAT_PART_NO",rs.getString("CAT_PART_NO")==null?"":rs.getString("CAT_PART_NO"));
        hD.put("CATALOG_COMPANY_ID",rs.getString("CATALOG_COMPANY_ID")==null?"":rs.getString("CATALOG_COMPANY_ID"));
        hD.put("ITEM_TYPE",rs.getString("ITEM_TYPE")==null?"":rs.getString("ITEM_TYPE"));
        hD.put("NEW_CHEM_REQUEST_ID",rs.getString("NEW_CHEM_REQUEST_ID")==null?"":rs.getString("NEW_CHEM_REQUEST_ID"));
        hD.put("DELIVERY_TICKET",rs.getString("DELIVERY_TICKET")==null?"":rs.getString("DELIVERY_TICKET"));
        hD.put("HUB",rs.getString("HUB")==null?"":rs.getString("HUB"));
        hD.put("PO_NUMBER",rs.getString("PO_NUMBER")==null?"":rs.getString("PO_NUMBER"));
        hD.put("DOC_NUM",rs.getString("DOC_NUM")==null?"":rs.getString("DOC_NUM"));
        hD.put("DOC_NUM_LINE",rs.getString("DOC_NUM_LINE")==null?"":rs.getString("DOC_NUM_LINE"));
        hD.put("QC_DATE",rs.getString("QC_DATE")==null?"":rs.getString("QC_DATE"));
        hD.put("UNIT_LABEL_PRINTED",rs.getString("UNIT_LABEL_PRINTED")==null?"N":rs.getString("UNIT_LABEL_PRINTED"));
        hD.put("DOT",rs.getString("DOT")==null?"N":rs.getString("DOT"));
        hD.put("DO_NUMBER_REQUIRED",rs.getString("DO_NUMBER_REQUIRED")==null?"N":rs.getString("DO_NUMBER_REQUIRED"));
        hD.put("POLCHEM_IG",rs.getString("POLCHEM_IG")==null?"N":rs.getString("POLCHEM_IG"));
        hD.put( "UNIT_LABEL_CAT_PART_NO",rs.getString("UNIT_LABEL_CAT_PART_NO")==null?"":rs.getString("UNIT_LABEL_CAT_PART_NO"));
        hD.put( "OLD_UNIT_LABEL_CAT_PART_NO",rs.getString("UNIT_LABEL_CAT_PART_NO")==null?"":rs.getString("UNIT_LABEL_CAT_PART_NO"));
        hD.put( "NEW_UNIT_LABEL_CAT_PART_NO","");
        hD.put("SPECS",rs.getString("SPECS")==null?"":rs.getString("SPECS"));
        hD.put("CUSTOMER_RECEIPT_ID",rs.getString("CUSTOMER_RECEIPT_ID")==null?"":rs.getString("CUSTOMER_RECEIPT_ID"));
        hD.put( "MINIMUM_EXPIRE_DATE",rs.getString( "MINIMUM_EXPIRE_DATE" ) == null ? "" : rs.getString( "MINIMUM_EXPIRE_DATE" ) );
        hD.put( "OPS_ENTITY_ID",rs.getString( "OPS_ENTITY_ID" ) == null ? "" : rs.getString( "OPS_ENTITY_ID" ) );
        hD.put( "NET_PENDING_ADJ",rs.getString( "NET_PENDING_ADJ" ) == null ? "" : rs.getString( "NET_PENDING_ADJ" ) );
        hD.put( "QA_STATEMENT",rs.getString( "QA_STATEMENT" ) == null ? "" : rs.getString( "QA_STATEMENT" ) );
        hD.put( "LOCK_EXPIRE_DATE",rs.getString( "LOCK_EXPIRE_DATE" ) == null ? "" : rs.getString( "LOCK_EXPIRE_DATE" ) );
        hD.put( "QUALITY_TRACKING_NUMBER",rs.getString( "QUALITY_TRACKING_NUMBER" ) == null ? "" : rs.getString( "QUALITY_TRACKING_NUMBER" ) );
        hD.put( "INCOMING_TESTING",rs.getString( "INCOMING_TESTING" ) == null ? "" : rs.getString( "INCOMING_TESTING" ) );
        hD.put( "INTERNAL_RECEIPT_NOTES",rs.getString( "INTERNAL_RECEIPT_NOTES" ) == null ? "" : rs.getString( "INTERNAL_RECEIPT_NOTES" ) );
        hD.put( "MFG_LABEL_EXPIRE_DATE",rs.getString( "MFG_LABEL_EXPIRE_DATE" ) == null ? "" : rs.getString( "MFG_LABEL_EXPIRE_DATE" ) );
        hD.put( "SUPPLIER_CAGE_CODE",rs.getString( "SUPPLIER_CAGE_CODE" ) == null ? "" : rs.getString( "SUPPLIER_CAGE_CODE" ) );
        hD.put( "DATE_OF_REPACKAGING",rs.getString( "DATE_OF_REPACKAGING" ) == null ? "" : rs.getString( "DATE_OF_REPACKAGING" ) );
        hD.put( "RECEIVING_STATUS",rs.getString( "RECEIVING_STATUS" ) == null ? "" : rs.getString( "RECEIVING_STATUS" ) );
        hD.put( "COUNTRY_OF_ORIGIN",rs.getString( "COUNTRY_OF_ORIGIN" ) == null ? "" : rs.getString( "COUNTRY_OF_ORIGIN" ) );
        hD.put( "COUNTRY_OF_ORIGIN_NAME",rs.getString( "COUNTRY_OF_ORIGIN_NAME" ) == null ? "" : rs.getString( "COUNTRY_OF_ORIGIN_NAME" ) );
        hD.put( "AUTOMATED_PUTAWAY",rs.getString( "AUTOMATED_PUTAWAY" ) == null ? "" : rs.getString( "AUTOMATED_PUTAWAY" ) );  
    
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

	result.trimToSize();
    Hashtable recsum=new Hashtable();
    recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
    result.setElementAt( recsum,0 );

    return result;
  }

  public boolean UpdateLine( Hashtable hD,String loginID,String lotupdate )
  {
	  boolean result = false;
	  String Sel_status       = (hD.get("LOT_STATUS")==null?" ":hD.get("LOT_STATUS").toString().trim());
	  String Oreceipt_id      = (hD.get("RECEIPT_ID")==null?" ":hD.get("RECEIPT_ID").toString());
	  String bin_name1      = (hD.get("BIN_NAME")==null?" ":hD.get("BIN_NAME").toString());
	  String expdate      = (hD.get("EXPIRE_DATE")==null?" ":hD.get("EXPIRE_DATE").toString());
	  String mfgLabelExpireDate      = (hD.get("MFG_LABEL_EXPIRE_DATE")==null?" ":hD.get("MFG_LABEL_EXPIRE_DATE").toString());                    
	  String recertnum      = (hD.get("RECERT_NUMBER")==null?" ":hD.get("RECERT_NUMBER").toString());
	  String mfglot      = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(hD.get("MFG_LOT")==null?" ":hD.get("MFG_LOT").toString());

	  String rootcause= hD.get("TERMINAL_ROOT_CAUSE")==null?" ":hD.get("TERMINAL_ROOT_CAUSE").toString();
	  String rootcausecompany= hD.get("TERMINAL_COMPANY")==null?" ":hD.get("TERMINAL_COMPANY").toString();
	  String rootcausenotes= hD.get("TERMINAL_NOTES")==null?" ":hD.get("TERMINAL_NOTES").toString();
	  rootcausenotes = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(rootcausenotes);
	  String qualitycntitem = ( hD.get( "QUALITY_CONTROL_ITEM" ) == null ? " " : hD.get( "QUALITY_CONTROL_ITEM" ).toString() );
	  String certupdate = (hD.get("CERTUPDATE")==null?" ":hD.get("CERTUPDATE").toString());
	  String expiredateUpdate = (hD.get("EXPIREDATEUPDATE")==null?" ":hD.get("EXPIREDATEUPDATE").toString());    
	  String lotstatusupdate =  (hD.get("LOTSTATUSUPDATE")==null?" ":hD.get("LOTSTATUSUPDATE").toString());
	  String dateOfShipment  =  (hD.get("DATE_OF_SHIPMENT")==null?"":hD.get("DATE_OF_SHIPMENT").toString());
	  String dateOfManufacture= hD.get( "DATE_OF_MANUFACTURE" )==null?"":hD.get( "DATE_OF_MANUFACTURE" ).toString().trim();
	  String deliveryTicket= BothHelpObjs.changeSingleQuotetoTwoSingleQuote(hD.get( "DELIVERY_TICKET" )==null?"":hD.get( "DELIVERY_TICKET" ).toString().trim());
	  String unitLabelPrinted = hD.get("UNIT_LABEL_PRINTED")==null?"":hD.get("UNIT_LABEL_PRINTED").toString();
	  String unitLabelCatPartNo = hD.get("UNIT_LABEL_CAT_PART_NO")==null?"":hD.get("UNIT_LABEL_CAT_PART_NO").toString();
	  String oldUnitLabelCatPartNo = hD.get("OLD_UNIT_LABEL_CAT_PART_NO")==null?"":hD.get("OLD_UNIT_LABEL_CAT_PART_NO").toString();
	  String newUnitLabelCatPartNo = hD.get("NEW_UNIT_LABEL_CAT_PART_NO")==null?"":hD.get("NEW_UNIT_LABEL_CAT_PART_NO").toString();
	  if (unitLabelCatPartNo.trim().length() ==0)
	  {
		  unitLabelPrinted = "N";
	  }
	  String doNumberRequired = hD.get("DO_NUMBER_REQUIRED")==null?"N":hD.get("DO_NUMBER_REQUIRED").toString();
	  String polchemIg = hD.get("POLCHEM_IG")==null?"N":hD.get("POLCHEM_IG").toString();
	  String oldLotStatus = hD.get("OLD_LOT_STATUS")==null?"N":hD.get("OLD_LOT_STATUS").toString();
	  String invQuantity        = (hD.get("QUANTITY")==null?"":hD.get("QUANTITY").toString());
	  String qaStatement        = (hD.get("QA_STATEMENT")==null?"":hD.get("QA_STATEMENT").toString());
	  String supplierCageCode   = (hD.get("SUPPLIER_CAGE_CODE")==null?"":hD.get("SUPPLIER_CAGE_CODE").toString());
	  String dateOfRepackaging   = (hD.get("DATE_OF_REPACKAGING")==null?"":hD.get("DATE_OF_REPACKAGING").toString());
	  String countryOfOrigin   = (hD.get("COUNTRY_OF_ORIGIN")==null?"":hD.get("COUNTRY_OF_ORIGIN").toString());

	  String qualityTrackingNumber  =BothHelpObjs.changeSingleQuotetoTwoSingleQuote(hD.get("QUALITY_TRACKING_NUMBER")==null?"":hD.get("QUALITY_TRACKING_NUMBER").toString());


    int recertintnum = 0;
    boolean updaterecert = false;

    try{recertintnum = Integer.parseInt(recertnum);updaterecert = true;}catch(Exception ee){recertintnum=0;}

    if ("Indefinite".equalsIgnoreCase(expdate))
    {
      expdate = "01/01/3000";
    }

    if ("Indefinite".equalsIgnoreCase(mfgLabelExpireDate))
    {
       mfgLabelExpireDate = "01/01/3000";
    }

	if ( (expdate.length() > 0 ) && expdate.length() != 10)
	{
	  result=false;
      return result;
	}

    CallableStatement cs=null;
    String updquery="";
    try
    {
      if ( "Customer Purchase".equalsIgnoreCase(Sel_status) || "Write Off Requested".equalsIgnoreCase(Sel_status) || "3PL Purchase".equalsIgnoreCase(Sel_status) )
      {
        if (rootcause.trim().length() == 0 || rootcausecompany.trim().length() == 0)
        {
          result=false;
          return result;
        }
      }

      updquery="update receipt set QA_STATEMENT = '"+qaStatement+"',SUPPLIER_CAGE_CODE = '"+supplierCageCode+"',UNIT_LABEL_PRINTED='"+unitLabelPrinted+"',DELIVERY_TICKET='"+deliveryTicket+"',BIN='" + bin_name1 + "',EXPIRE_DATE=to_date('" + expdate + "'" + "," +"'MM/DD/YYYY'),MFG_LABEL_EXPIRE_DATE=to_date('" + mfgLabelExpireDate + "'" + "," +"'MM/DD/YYYY'),MFG_LOT='" + mfglot + "',LAST_MODIFIED_BY = '"+loginID+"'"+ ",QUALITY_TRACKING_NUMBER = '"+ qualityTrackingNumber +"'";
	  if (!"Incoming".equalsIgnoreCase(Sel_status))
      {
        updquery+=",LOT_STATUS='" + Sel_status + "'";
      }

        if (dateOfManufacture.length() > 0)
			{
				updquery += ",DATE_OF_MANUFACTURE=to_date('" + dateOfManufacture + "'" + "," +"'MM/DD/YYYY')";
			}
			else
			{
				updquery += ",DATE_OF_MANUFACTURE=null";
	    }

			if (dateOfShipment.length() > 0)
			{
				updquery += ",DATE_OF_SHIPMENT=to_date('" + dateOfShipment + "'" + "," +"'MM/DD/YYYY')";
			}
			else
			{
				updquery += ",DATE_OF_SHIPMENT=null";
	    }
			
	   if (dateOfRepackaging.length() > 0)
					updquery += ",DATE_OF_REPACKAGING=to_date('" + dateOfRepackaging + "'" + "," +"'MM/DD/YYYY')";
	   else
					updquery += ",DATE_OF_REPACKAGING=null";
			
	  if ( "Customer Purchase".equalsIgnoreCase(Sel_status) || "Write Off Requested".equalsIgnoreCase(Sel_status) || "3PL Purchase".equalsIgnoreCase(Sel_status) )
      {
        updquery += ",LOT_STATUS_ROOT_CAUSE = '"+rootcause+"',RESPONSIBLE_COMPANY_ID = '"+rootcausecompany+"',LOT_STATUS_ROOT_CAUSE_NOTES= '"+rootcausenotes+"'";
      }

      if ( updaterecert )
      {
        updquery+=",RECERT_NUMBER=" + recertnum + "";
      }

	  if ("Y".equalsIgnoreCase(qualitycntitem) && "Yes".equalsIgnoreCase(certupdate))
	  {
		updquery+=",CERTIFICATION_DATE=SYSDATE, CERTIFIED_BY=" + loginID + "";
	  }
	  
	  updquery+=",COUNTRY_OF_ORIGIN='" + countryOfOrigin + "'";
	  
	  if(hD.get("SYNCWITHWMS") != null && (Boolean)hD.get("SYNCWITHWMS") == true)
		  updquery+=",WMS_SYNCHRONIZED='N'";

      updquery += " where RECEIPT_ID = " + Oreceipt_id + " ";

      String insDelUnitLabelPart = "";
      if (unitLabelPrinted.equalsIgnoreCase("Y") && unitLabelCatPartNo.trim().length() >0)
      {
        if (!oldUnitLabelCatPartNo.equalsIgnoreCase(newUnitLabelCatPartNo))
        {
            insDelUnitLabelPart = "insert into receipt_label_info(receipt_id, cat_part_no) values("+""+Oreceipt_id+","+unitLabelCatPartNo+""+")";
        }
      }
      else
      {
        insDelUnitLabelPart = "delete from receipt_label_info where receipt_id = "+Oreceipt_id+"";
      }

      try
      {
        Connection connect1=db.getConnection();
		    logisticsLog.debug(updquery);
        cs=connect1.prepareCall( updquery );
        cs.execute();
        connect1.commit();

        if (polchemIg.equalsIgnoreCase("Y") && doNumberRequired.equalsIgnoreCase("N") && insDelUnitLabelPart.length() > 0)
        {         
         logisticsLog.debug(insDelUnitLabelPart);
         cs=connect1.prepareCall( insDelUnitLabelPart );
         cs.execute();
         connect1.commit();
        }
      }
      catch ( Exception e )
      {
        radian.web.emailHelpObj.sendnawazemail("Error in Logistics Page Update","This Update Statement gave error "+updquery+" \n\n "+e.getMessage()+"");
        throw e;
      }
      finally
      {
        if ( cs != null )
        {
          try
          {
            cs.close();
          }
          catch ( Exception se )
          {
        	  logisticsLog.error("Logistic page error closing connection on inserting/updating receipt table: " + se.getLocalizedMessage());
          }
        }
      }

      try
        {
          Connection connect1=db.getConnection();
          cs=connect1.prepareCall( "{call PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_reset(?)}" );
          cs.setInt( 1,Integer.parseInt( Oreceipt_id ) );
          cs.execute();
          logisticsLog.info( "call PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_reset "+Oreceipt_id );
        }
        catch ( Exception e )
        {
          errorMsgs.addElement( "Error Calling PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_reset for Receipt ID :" + Oreceipt_id + "" );
          radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_reset in HubInvenLabelsTables","Error occured while running p_rec_full_sl_date_reset from logistics\n" + e.getMessage() +"\nFor Input Parameters Receipt Id: " + Oreceipt_id + "" );
          throw e;
        }
        finally
        {
          if ( cs != null )
          {
            try
            {
              cs.close();
            }
            catch ( Exception se )
            {
            	logisticsLog.error("Logistic page error closing connection on calling PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_rese: " + se.getLocalizedMessage());
            }
          }
      }

      if ( "yes".equalsIgnoreCase( lotupdate ) || "Yes".equalsIgnoreCase(expiredateUpdate))
      {
        logisticsLog.info( "Doing P_RECEIPT_ALLOCATE...for "+Oreceipt_id );
        try
        {
          Connection connect1=db.getConnection();
          cs=connect1.prepareCall( "{call P_RECEIPT_ALLOCATE(?, ?)}" );
          cs.setInt( 1,Integer.parseInt( Oreceipt_id ) );
          cs.setString( 2,"A" );
          cs.execute();
        }
        catch ( Exception e )
        {
          errorMsgs.addElement( "Error Calling P_RECEIPT_ALLOCATE for Receipt ID :" + Oreceipt_id + "" );
          radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure P_RECEIPT_ALLOCATE in HubInvenLabelsTables","Error occured while running P_RECEIPT_ALLOCATE from logistics\n" + e.getMessage() +"\nFor Input Parameters Receipt Id: " + Oreceipt_id + "" );
          throw e;
        }
        finally
        {
          if ( cs != null )
          {
            try
            {
              cs.close();
            }
            catch ( Exception se )
            {
              logisticsLog.error("Logistic page error closing connection on calling P_RECEIPT_ALLOCATE: " + se.getLocalizedMessage());
            }
          }
        }
      }

      if ( "Write Off Requested".equalsIgnoreCase(Sel_status)  && !oldLotStatus.equalsIgnoreCase("Write Off Requested") )
      {
        result = writeOffRequest(Oreceipt_id,invQuantity,""+rootcause+" "+rootcausenotes+"",loginID);
      }

      if ( !"Write Off Requested".equalsIgnoreCase(Sel_status)  && oldLotStatus.equalsIgnoreCase("Write Off Requested") )
      {
        result = cancelWriteOffRequest(Oreceipt_id,loginID);
      }
      result=true;
    }
    catch ( Exception e )
    {
      errorMsgs.addElement( "Error for Receipt ID :" + Oreceipt_id + "" );
      result=false;
    }

    return result;
  }

	  public boolean writeOffRequest(String receiptId, String quantity,String writeOffComments, String PersonnelId )
	  {
		  Connection connect1;
		  CallableStatement cs = null;
		  boolean result = false;
		  try
		  {
			  connect1 = db.getConnection();
			  cs = connect1.prepareCall("{call PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request(?,SYSDATE,?,?,?,?)}");
			  cs.setInt(1,Integer.parseInt(receiptId));
			  cs.setInt(2,Integer.parseInt(PersonnelId));
			  cs.setInt(3,Integer.parseInt(quantity));
			  cs.setString(4,writeOffComments );
			  cs.registerOutParameter(5, Types.VARCHAR);
	
			  logisticsLog.info("calling Procedure PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request: "+receiptId+" quantity "+quantity+" ");
			  cs.execute();
	
			  String errorMsg = cs.getString(5);
	
			  if( errorMsg != null && !"ok".equalsIgnoreCase(errorMsg))
			  {              
				  logisticsLog.info("Error in Procedure PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request: "+receiptId+" Error Code "+errorMsg+" ");
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

	public boolean cancelWriteOffRequest(String receiptId,String PersonnelId )
	{
		Connection connect1;
		CallableStatement cs = null;
		boolean result = false;
		try
		{
			connect1 = db.getConnection();
			cs = connect1.prepareCall("{call PKG_INVENTORY_ADJUSTMENT.p_cancel_write_off_request(?)}");
			cs.setInt(1,Integer.parseInt(receiptId));
			logisticsLog.info("calling Procedure PKG_INVENTORY_ADJUSTMENT.p_cancel_write_off_request: "+receiptId+" ");
			cs.execute();
	
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


	public Vector<Hashtable<String,String>> getVVCountries() throws Exception
	{
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Vector<Hashtable<String,String>>  result = new Vector<Hashtable<String,String>>();
		
	    try
	    {
	      dbrs=db.doQuery("select * from global.vv_country order by country");
	      rs=dbrs.getResultSet();

	      Hashtable<String,String> resultsTable = null;

	      while ( rs.next() )
	      {
	        resultsTable = new Hashtable<String,String>(2);
	        resultsTable.put( "COUNTRY_ABBREV",rs.getString( "COUNTRY_ABBREV" ) == null ? "" : rs.getString( "COUNTRY_ABBREV" ) );
	        resultsTable.put( "COUNTRY",rs.getString( "COUNTRY" ) == null ? "" : rs.getString( "COUNTRY" ) );
		    result.addElement( resultsTable );
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
	    
	    return result;

	}
}