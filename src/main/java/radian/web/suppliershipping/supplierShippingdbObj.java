package radian.web.suppliershipping;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      Haas TCM
 * @author       Nawaz Shaik
 * @version
 *
 */

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.math.BigDecimal;

public class supplierShippingdbObj
{
    private TcmISDBModel db;
    private   Vector vLotSeqList = null;
    private   Vector errorMsgs = null;
	private static org.apache.log4j.Logger reclog = org.apache.log4j.Logger.getLogger(supplierShippingdbObj.class);

    public supplierShippingdbObj()
    {
        vLotSeqList = new Vector();
        errorMsgs = new Vector();
    }

    public supplierShippingdbObj(TcmISDBModel db )
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

	public Vector getAllopenOrder( String supplier,String SearchText,String SortBy,String selshipto,String searchlike,String searchfor,String showhistory ) throws Exception
	{
	  DBResultSet dbrs=null;
	  ResultSet searchRs=null;
	  Vector result=new Vector();
	  Hashtable summary=new Hashtable();
	  String where_clause = "";
	  String finaly_query = "";

	  String user_query="select x.INVENTORY_GROUP,x.SUPPLIER_NAME,x.CONTAINER_LABEL,to_char(x.EXPIRE_DATE,'mm/dd/yyyy') EXPIRE_DATE, to_char(x.VENDOR_SHIP_DATE,'mm/dd/yyyy') VENDOR_SHIP_DATE,x.MFG_LOT,x.QTY_DELIVERED, to_char(x.DATE_DELIVERED,'mm/dd/yyyy') DATE_DELIVERED,x.SUPPLIER_PARENT,x.SUPPLIER,x.SHIP_TO_COMPANY_ID,x.SHIP_TO_LOCATION_ID,x.MR_NUMBER,x.MR_LINE_ITEM,x.CAT_PART_NO,x.REQUESTOR_NAME,x.APPLICATION, ";
	  user_query+="x.DELIVERY_POINT,x.CUSTOMER_PO,x.RADIAN_PO,x.LINE_ITEM,x.QTY_OPEN,x.QTY_DELIVERED,x.DATE_DELIVERED,x.ITEM_ID,to_char(x.EXPECTED,'mm/dd/yyyy') EXPECTED, ";
	  user_query+="x.BRANCH_PLANT,x.ITEM_DESCRIPTION,x.TRANS_TYPE,x.INDEFINITE_SHELF_LIFE,x.CRITICAL, ";
	  user_query+="x.MANAGE_KITS_AS_SINGLE_UNIT,x.COMPONENT_ID,x.SHELF_LIFE_DAYS,x.SHELF_LIFE_BASIS,x.MATERIAL_ID,x.PACKAGING,x.MATERIAL_DESC, ";
	  user_query+="x.NUMBER_OF_KITS, to_char(sysdate,'mm/dd/yyyy') SYS_DATE ";
//x.QTY,x.PO_NOTES,x.SUPPLIER_NAME,x.ORIGINAL_MFG_LOT,
//x.LOCATION_SHORT_NAME,x.LOCATION_DESC,x.ADDRESS_LINE_1,x.ADDRESS_LINE_2,x.ADDRESS_LINE_3,x.CITY,x.STATE_ABBREV,x.ZIP,
	  where_clause+=" where SUPPLIER_PARENT = '"+supplier+"'";

	  if ( SearchText.trim().length() > 0 )
	  {
		if ( "Like".equalsIgnoreCase( searchfor ) )
		{
		  where_clause+=" and lower(" + searchlike + ") like lower('%" + SearchText.trim() + "%') ";
		}
		else if ( "Equals".equalsIgnoreCase( searchfor ) )
		{
		  where_clause+=" and " + searchlike + " = '" + SearchText.trim() + "' ";
		}
	  }

	  boolean flagforand=true;
	  if ( selshipto.length() > 0 && !"All".equalsIgnoreCase( selshipto ) )
	  {
		if ( flagforand )
		  where_clause+=" and SHIP_TO_LOCATION_ID ='" + selshipto + "' ";
		else
		{
		  where_clause+=" SHIP_TO_LOCATION_ID ='" + selshipto + "' ";
		}
	  }

	  if ("Yes".equalsIgnoreCase(showhistory))
	  {
		finaly_query = user_query + ",x.DROP_SHIP,null RECEIPT_ID from supplier_shipping_view x " + where_clause + " union all " + user_query + ",'' DROP_SHIP,RECEIPT_ID from SUPPLIER_SHIPPING_history_VIEW x " + where_clause ;
	  }
	  else
	  {
		finaly_query = user_query + ",x.DROP_SHIP,null RECEIPT_ID from supplier_shipping_view x " + where_clause;
	  }
	  finaly_query+=" order by " + SortBy;

	  int count=0;
	  summary.put( "TOTAL_NUMBER",new Integer( count ) );
	  result.addElement( summary );

	  int num_rec=0;
	  try
	  {
		dbrs=db.doQuery( finaly_query );
		searchRs=dbrs.getResultSet();

		/*ResultSetMetaData rsMeta1=searchRs.getMetaData();
		System.out.println( "Finished The Querry\n " + rsMeta1.getColumnCount() );
		for ( int i=1; i <= rsMeta1.getColumnCount(); i++ )
		{
		  System.out.print("x."+rsMeta1.getColumnName(i).toString()+",");
		  //System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");
		  //System.out.println("hDupl.put( \""+rsMeta1.getColumnName(i).toString()+"\",temp.get( \""+rsMeta1.getColumnName(i).toString()+"\" ) );");
		  //System.out.println( "DataH.put(\"" + rsMeta1.getColumnName( i ).toString() + "\",searchRs.getString(\"" + rsMeta1.getColumnName( i ).toString() +  "\")==null?\"\":searchRs.getString(\"" + rsMeta1.getColumnName( i ).toString() + "\"));" );
		  //System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
		  //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
		}*/

		while ( searchRs.next() )
		{
		  num_rec++;

		  Hashtable DataH=new Hashtable();
		  DataH.put( "SUPPLIER_PARENT",searchRs.getString( "SUPPLIER_PARENT" ) == null ? "" : searchRs.getString( "SUPPLIER_PARENT" ) );
		  DataH.put( "SUPPLIER",searchRs.getString( "SUPPLIER" ) == null ? "" : searchRs.getString( "SUPPLIER" ) );
		  DataH.put( "SHIP_TO_COMPANY_ID",searchRs.getString( "SHIP_TO_COMPANY_ID" ) == null ? "" : searchRs.getString( "SHIP_TO_COMPANY_ID" ) );
		  DataH.put( "SHIP_TO_LOCATION_ID",searchRs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "" : searchRs.getString( "SHIP_TO_LOCATION_ID" ) );
		  DataH.put( "MR_NUMBER",searchRs.getString( "MR_NUMBER" ) == null ? "" : searchRs.getString( "MR_NUMBER" ) );
		  DataH.put( "MR_LINE_ITEM",searchRs.getString( "MR_LINE_ITEM" ) == null ? "" : searchRs.getString( "MR_LINE_ITEM" ) );
		  DataH.put( "REQUESTOR_NAME",searchRs.getString( "REQUESTOR_NAME" ) == null ? "" : searchRs.getString( "REQUESTOR_NAME" ) );
		  DataH.put( "APPLICATION",searchRs.getString( "APPLICATION" ) == null ? "" : searchRs.getString( "APPLICATION" ) );
		  DataH.put( "DELIVERY_POINT",searchRs.getString( "DELIVERY_POINT" ) == null ? "" : searchRs.getString( "DELIVERY_POINT" ) );
		  DataH.put( "CUSTOMER_PO",searchRs.getString( "CUSTOMER_PO" ) == null ? "" : searchRs.getString( "CUSTOMER_PO" ) );
		  DataH.put( "PURCHASE_ORDER",searchRs.getString( "RADIAN_PO" ) == null ? "" : searchRs.getString( "RADIAN_PO" ) );
		  DataH.put( "PURCHASE_ORDER_LINE",searchRs.getString( "LINE_ITEM" ) == null ? "" : searchRs.getString( "LINE_ITEM" ) );
		  DataH.put( "QTY_OPEN",searchRs.getString( "QTY_OPEN" ) == null ? "" : searchRs.getString( "QTY_OPEN" ) );
		  DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
		  DataH.put( "EXPECTED",searchRs.getString( "EXPECTED" ) == null ? "" : searchRs.getString( "EXPECTED" ) );
		  //DataH.put( "QTY",searchRs.getString( "QTY" ) == null ? "" : searchRs.getString( "QTY" ) );
		  DataH.put( "BRANCH_PLANT",searchRs.getString( "BRANCH_PLANT" ) == null ? "" : searchRs.getString( "BRANCH_PLANT" ) );
		  DataH.put( "ITEM_DESCRIPTION",searchRs.getString( "ITEM_DESCRIPTION" ) == null ? "" : searchRs.getString( "ITEM_DESCRIPTION" ) );
		  //DataH.put( "PO_NOTES",searchRs.getString( "PO_NOTES" ) == null ? "" : searchRs.getString( "PO_NOTES" ) );
		  DataH.put( "TRANS_TYPE",searchRs.getString( "TRANS_TYPE" ) == null ? "" : searchRs.getString( "TRANS_TYPE" ) );
		  DataH.put( "SUPPLIER_NAME",searchRs.getString( "SUPPLIER_NAME" ) == null ? "" : searchRs.getString( "SUPPLIER_NAME" ) );
		  DataH.put( "INDEFINITE_SHELF_LIFE",searchRs.getString( "INDEFINITE_SHELF_LIFE" ) == null ? "" : searchRs.getString( "INDEFINITE_SHELF_LIFE" ) );
		  DataH.put( "CRITICAL",searchRs.getString( "CRITICAL" ) == null ? "" : searchRs.getString( "CRITICAL" ) );
		  //DataH.put( "ORIGINAL_MFG_LOT",searchRs.getString( "ORIGINAL_MFG_LOT" ) == null ? "" : searchRs.getString( "ORIGINAL_MFG_LOT" ) );
		  DataH.put( "INVENTORY_GROUP",searchRs.getString( "INVENTORY_GROUP" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP" ) );
		  DataH.put( "MANAGE_KITS_AS_SINGLE_UNIT",searchRs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) == null ? "" : searchRs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) );
		  DataH.put( "COMPONENT_ID",searchRs.getString( "COMPONENT_ID" ) == null ? "" : searchRs.getString( "COMPONENT_ID" ) );
		  DataH.put( "MATERIAL_ID",searchRs.getString( "MATERIAL_ID" ) == null ? "" : searchRs.getString( "MATERIAL_ID" ) );
		  DataH.put( "PACKAGING",searchRs.getString( "PACKAGING" ) == null ? "" : searchRs.getString( "PACKAGING" ) );
		  DataH.put( "MATERIAL_DESC",searchRs.getString( "MATERIAL_DESC" ) == null ? "" : searchRs.getString( "MATERIAL_DESC" ) );
		  DataH.put( "NUMBER_OF_KITS",searchRs.getString( "NUMBER_OF_KITS" ) == null ? "" : searchRs.getString( "NUMBER_OF_KITS" ) );
		  /*DataH.put( "LOCATION_SHORT_NAME",searchRs.getString( "LOCATION_SHORT_NAME" ) == null ? "" : searchRs.getString( "LOCATION_SHORT_NAME" ) );
		  DataH.put( "LOCATION_DESC",searchRs.getString( "LOCATION_DESC" ) == null ? "" : searchRs.getString( "LOCATION_DESC" ) );
		  DataH.put( "ADDRESS_LINE_1",searchRs.getString( "ADDRESS_LINE_1" ) == null ? "" : searchRs.getString( "ADDRESS_LINE_1" ) );
		  DataH.put( "ADDRESS_LINE_2",searchRs.getString( "ADDRESS_LINE_2" ) == null ? "" : searchRs.getString( "ADDRESS_LINE_2" ) );
		  DataH.put( "ADDRESS_LINE_3",searchRs.getString( "ADDRESS_LINE_3" ) == null ? "" : searchRs.getString( "ADDRESS_LINE_3" ) );
		  DataH.put( "CITY",searchRs.getString( "CITY" ) == null ? "" : searchRs.getString( "CITY" ) );
		  DataH.put( "STATE_ABBREV",searchRs.getString( "STATE_ABBREV" ) == null ? "" : searchRs.getString( "STATE_ABBREV" ) );
		  DataH.put( "ZIP",searchRs.getString( "ZIP" ) == null ? "" : searchRs.getString( "ZIP" ) );*/
      DataH.put( "SHELF_LIFE_DAYS",searchRs.getString( "SHELF_LIFE_DAYS" ) == null ? "" : searchRs.getString( "SHELF_LIFE_DAYS" ) );
	    DataH.put( "SHELF_LIFE_BASIS",searchRs.getString( "SHELF_LIFE_BASIS" ) == null ? "" : searchRs.getString( "SHELF_LIFE_BASIS" ) );
		  DataH.put( "CAT_PART_NO",searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ) );
      DataH.put( "ACTUAL_SHIP_DATE",searchRs.getString( "VENDOR_SHIP_DATE" ) == null ? "" : searchRs.getString( "VENDOR_SHIP_DATE" ) );
	    DataH.put( "MFG_LOT",searchRs.getString( "MFG_LOT" ) == null ? "" : searchRs.getString( "MFG_LOT" ) );
		  DataH.put( "QTY_RECD",searchRs.getString( "QTY_DELIVERED" ) == null ? "" : searchRs.getString( "QTY_DELIVERED" ) );
		  DataH.put( "USERCHK","" );
		  DataH.put( "DATE_RECIEVED",searchRs.getString("DATE_DELIVERED")==null? "" : searchRs.getString("DATE_DELIVERED"));
		  DataH.put( "EXPIRE_DATE",searchRs.getString( "EXPIRE_DATE" ) == null ? "" : searchRs.getString( "EXPIRE_DATE" ) );
		  DataH.put( "DATE_OF_SHIPMENT","" );
		  DataH.put( "RECEIPT_ID",searchRs.getString( "RECEIPT_ID" ) == null ? "" : searchRs.getString( "RECEIPT_ID" ));
		  DataH.put( "LINE_STATUS","" );
	    DataH.put( "BASIS_DATE","" );
    	DataH.put( "LABEL_QTY","" );
			DataH.put( "CONTAINER_LABEL",searchRs.getString( "CONTAINER_LABEL" ) == null ? "" : searchRs.getString( "CONTAINER_LABEL" ) );
			DataH.put( "BIN_NAME","Virtual Hub Supplier" );
			DataH.put( "DROP_SHIP",searchRs.getString( "DROP_SHIP" ) == null ? "" : searchRs.getString( "DROP_SHIP" ) );
      DataH.put( "NOTES","" );
                
      result.addElement( DataH );
		}
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = null;
            throw e;
        }
        finally
        {
            if (dbrs != null)  { dbrs.close(); }
        }

        Hashtable recsum  = new Hashtable();
        recsum.put("TOTAL_NUMBER", new Integer(num_rec) );
        result.setElementAt(recsum, 0);
        result.trimToSize();
        return result;
    }

		public Hashtable UpdateLine( Hashtable hD,String Branch_plant,String loginID )
		 {
			 Hashtable resulth=new Hashtable();

			 boolean result=false;
			 int Intreceipt_id=0;

			 String Radian_po= ( hD.get( "PURCHASE_ORDER" ) == null ? " " : hD.get( "PURCHASE_ORDER" ).toString() );
			 String PO_line= ( hD.get( "PURCHASE_ORDER_LINE" ) == null ? " " : hD.get( "PURCHASE_ORDER_LINE" ).toString() );
			 String Item_id= ( hD.get( "ITEM_ID" ) == null ? " " : hD.get( "ITEM_ID" ).toString() );
			 String trans_type= ( hD.get( "TRANS_TYPE" ) == null ? " " : hD.get( "TRANS_TYPE" ).toString() );
			 String Mfg_lot=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( hD.get( "MFG_LOT" ) == null ? " " : hD.get( "MFG_LOT" ).toString() );
			 String Date_mfgd= ( hD.get( "DATE_MFGD" ) == null ? " " : hD.get( "DATE_MFGD" ).toString() );
			 String Date_shiped= ( hD.get( "DATE_OF_SHIPMENT" ) == null ? " " : hD.get( "DATE_OF_SHIPMENT" ).toString() );
			 String Qty_recd= ( hD.get( "QTY_RECD" ) == null ? " " : hD.get( "QTY_RECD" ).toString() );
			 String binName= ( hD.get( "BIN_NAME" ) == null ? "" : hD.get( "BIN_NAME" ).toString() );
			 if (binName.length() == 0)
			 {
				binName = "Dropship Receiving";
			 }
			 String xfer_req_id= ( hD.get( "XFER_REQ_ID" ) == null ? " " : hD.get( "XFER_REQ_ID" ).toString() );
			 String Oreceipt_id= ( hD.get( "RECEIPT_ID" ) == null ? " " : hD.get( "RECEIPT_ID" ).toString() );
			 String Date_Recieved= ( hD.get( "DATE_RECIEVED" ) == null ? " " : hD.get( "DATE_RECIEVED" ).toString() );
			 String Expiry_Date= ( hD.get( "EXPIRE_DATE" ) == null ? " " : hD.get( "EXPIRE_DATE" ).toString() );
			 String actshipDate= ( hD.get( "ACTUAL_SHIP_DATE" ) == null ? " " : hD.get( "ACTUAL_SHIP_DATE" ).toString() );

			 String mrnumber= ( hD.get( "MR_NUMBER" ) == null ? " " : hD.get( "MR_NUMBER" ).toString() );
			 String mrlineitem= ( hD.get( "MR_LINE_ITEM" ) == null ? " " : hD.get( "MR_LINE_ITEM" ).toString() );
			 Branch_plant = ( hD.get( "BRANCH_PLANT" ) == null ? " " : hD.get( "BRANCH_PLANT" ).toString() );

       String supplier= ( hD.get( "SUPPLIER" ) == null ? " " : hD.get( "SUPPLIER" ).toString() );
       String supplierName= ( hD.get( "SUPPLIER_NAME" ) == null ? " " : hD.get( "SUPPLIER_NAME" ).toString() );

       int RECEIPT_ID1=0;
			 int Issue_id=0;
			 String errorcode="";
			 String batchno = "";
		 String recnotes = hD.get( "NOTES" ) == null ? " " : hD.get( "NOTES" ).toString();

       reclog.debug("Radian_po - "+Radian_po+" PO_line- "+PO_line+" mrnumber -"+mrnumber+"- mrlineitem-"+mrlineitem+"-");

       Connection connect1;
		 //if (  mrnumber.trim().length() > 0 && mrlineitem.trim().length() > 0 )
			 try
			 {
				 connect1=db.getConnection();
				 batchno = this.getBatchNo();

				 if ( trans_type.equalsIgnoreCase( "IT" ) )
				 {
					 Intreceipt_id=Integer.parseInt( Oreceipt_id );
					 Radian_po="0";
					 PO_line="0";
				 }

				 CallableStatement cs=null;

				 try
				 {
					 cs=connect1.prepareCall( "{call p_supplier_shipping(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
					 cs.setBigDecimal( 1,new BigDecimal(""+ Radian_po +"") );
					 cs.setInt( 2,Integer.parseInt( PO_line ) );

					 if ( Item_id.trim().length() > 0 )
					 {
						 cs.setInt( 3,Integer.parseInt( Item_id ) );
					 }
					 else
					 {
						 cs.setNull( 3,java.sql.Types.INTEGER );
					 }

					 if ( Mfg_lot.trim().length() > 0 )
					 {
						 cs.setString( 4,Mfg_lot );
					 }
					 else
					 {
						 cs.setNull( 4,java.sql.Types.VARCHAR );
					 }

					 cs.setString( 5,binName );
					 cs.setNull( 6,java.sql.Types.DATE );
					 cs.setNull( 7,java.sql.Types.DATE );
					 cs.setNull( 8,java.sql.Types.DATE );

					 if ( Qty_recd.trim().length() > 0 )
					 {
						 cs.setInt( 9,Integer.parseInt( Qty_recd ) );
					 }
					 else
					 {
						 cs.setNull( 9,java.sql.Types.NUMERIC );
					 }

					 cs.setString( 10,"Available" );
					 cs.setTimestamp( 11,this.getDateFromString( Date_Recieved ) );

					 if ( Branch_plant.trim().length() > 0 )
					 {
						 cs.setString( 12,Branch_plant );
					 }
					 else
					 {
						 cs.setNull( 12,java.sql.Types.VARCHAR );
					 }

					 if ( loginID.trim().length() > 0 )
					 {
						 cs.setString( 13,loginID );
					 }
					 else
					 {
						 cs.setNull( 13,java.sql.Types.VARCHAR );
					 }

					 if ( Expiry_Date.length() > 1 )
					 {
						 cs.setTimestamp( 14,this.getDateFromString( Expiry_Date ) );
					 }
					 else
					 {
						 cs.setNull( 14,java.sql.Types.DATE );
					 }

					 if ( trans_type.equalsIgnoreCase( "IT" ) )
					 {
						 cs.setInt( 15,Intreceipt_id );
					 }
					 else
					 {
						 cs.setNull( 15,java.sql.Types.NUMERIC );
					 }

					 if ( actshipDate.length() == 10 )
					 {
						 cs.setTimestamp( 16,this.getDateFromString( actshipDate ) );
					 }
					 else
					 {
						 cs.setNull( 16,java.sql.Types.DATE );
					 }

					 if ( mrnumber.trim().length() > 0 )
					 {
						 cs.setBigDecimal( 17,new BigDecimal(""+ mrnumber +"") );
					 }
					 else
					 {
						 cs.setNull( 17,java.sql.Types.INTEGER );
					 }

					 if ( mrlineitem.trim().length() > 0 )
					 {
						 cs.setString( 18,mrlineitem );
					 }
					 else
					 {
						 cs.setNull( 18,java.sql.Types.VARCHAR );
					 }

					 cs.setInt( 19,Integer.parseInt( batchno ) );

			 if ( recnotes.trim().length() > 0 )
			 {
				 cs.setString( 20,recnotes );
			 }
			 else
			 {
				 cs.setNull( 20,java.sql.Types.VARCHAR );
			 }
					 cs.registerOutParameter( 21,java.sql.Types.NUMERIC );
					 cs.registerOutParameter( 22,java.sql.Types.NUMERIC );
					 cs.registerOutParameter( 23,java.sql.Types.VARCHAR );
					 cs.execute();

					 RECEIPT_ID1=cs.getInt( 21 );
					 Issue_id=cs.getInt( 22 );
					 errorcode=BothHelpObjs.makeBlankFromNull( cs.getString( 23 ) );

					 if ( RECEIPT_ID1 == -1 )
					 {
						 result=false;
						 errorMsgs.addElement( errorcode );
					 }
					 else
					 {
						 result=true;
						 Integer intreceipt_id=new Integer( RECEIPT_ID1 );
						 vLotSeqList.addElement( intreceipt_id );
					 }
           reclog.debug("IT Supplier Shipping Procedure Result is: Radian_po " + Radian_po + " PO_line " + PO_line +"\nRECEIPT_ID  : " + RECEIPT_ID1 + "\nIssue_id   :" + Issue_id + "\nerrorcode   :" + errorcode);
           //System.out.println( "\n\n--------- 'IT' Supplier Shipping Procedure Result is: Radian_po " + Radian_po + " PO_line " + PO_line +"\nRECEIPT_ID  : " + RECEIPT_ID1 + "\nIssue_id   :" + Issue_id + "\nerrorcode   :" + errorcode );
				 }
				 catch ( Exception e )
				 {
					 e.printStackTrace();
					 radian.web.emailHelpObj.senddatabaseHelpemails("Error occured while running call p_supplier_shipping in dropShipReceivingTables","Error occured while running call p_supplier_shipping \n\nError Msg:\n " + e.getMessage() + "\n\n Parameters passed p_supplier_shipping Radian_po " + Radian_po + "\nPO_line " + PO_line +
																	" \n Item_id " + Item_id + "\n Mfg_lot " + Mfg_lot + "\n Date_mfgd " + Date_mfgd + "\n Date_shiped " + Date_shiped + "\n Qty_recd " + Qty_recd +
																	"\n Lot Status Incoming\n transfer id " + xfer_req_id + "\n Date_Recieved " + Date_Recieved + "\nBranch_plant " + Branch_plant + "\npersonnel Id " + loginID + "\n trans_type " +
																	trans_type + "\nExpiry_Date " + Expiry_Date + "\nIT ReceiptID " + Intreceipt_id + "\nACTUAL_SHIP_DATE  "+actshipDate+"\nMR_NUMBER   "+mrnumber+"\nMR_LINE_ITEM   "+mrlineitem+"" );

					 throw e;
				 }
				 finally
				 {
					 connect1.commit();
					 if ( cs != null )
					 {
						 try
						 {
							 cs.close();
						 }
						 catch ( Exception se )
						 {
							 se.printStackTrace();
						 }
					 }
				 }

         if ( recnotes.trim().length() > 0 )
			  {
         radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"Supplier Shipping PO " + Radian_po +"-"+ PO_line +" supplier name "+supplierName+" supplier "+supplier+"","Supplier Shipping PO " + Radian_po +"-"+ PO_line +"\\n supplier name "+supplierName+" supplier "+supplier+"\\nNotes:"+recnotes+"",Integer.parseInt("3059"),true);
        }
       }
			 catch ( Exception e )
			 {
				 e.printStackTrace();
				 result=false;
			 }

		 /*else
		 {
			 result=false;
			 radian.web.emailHelpObj.sendnawazemail("Error occured while running call p_supplier_shipping in dropShipReceivingTables","Error occured while running call p_supplier_shipping \n\nError Msg:\n No MR Line Passed\n\n Parameters passed p_supplier_shipping Radian_po " + Radian_po + "\nPO_line " + PO_line +
										" \n Item_id " + Item_id + "\n Mfg_lot " + Mfg_lot + "\n Date_mfgd " + Date_mfgd + "\n Date_shiped " + Date_shiped + "\n Qty_recd " + Qty_recd +
										"\n Lot Status Incoming\n transfer id " + xfer_req_id + "\n Date_Recieved " + Date_Recieved + "\nBranch_plant " + Branch_plant + "\npersonnel Id " + loginID + "\n trans_type " +
										trans_type + "\nExpiry_Date " + Expiry_Date + "\nIT ReceiptID " + Intreceipt_id + "\nACTUAL_SHIP_DATE  "+actshipDate+"\nMR_NUMBER   "+mrnumber+"\nMR_LINE_ITEM   "+mrlineitem+"" );
		 }*/
			 resulth.put( "RCECIPT_ID","" + RECEIPT_ID1 + "" );
			 resulth.put( "RESULT",new Boolean( result ) );

			 return resulth;
		 }

		 private String getBatchNo() throws Exception
			{
				DBResultSet dbrs=null;
				ResultSet rs=null;
				String bNo="";
				String bquery="select print_batch_seq.nextval BATCHNO from dual";
				try
				{
					dbrs=db.doQuery( bquery );
					rs=dbrs.getResultSet();
					while ( rs.next() )
					{
						bNo=rs.getString( "BATCHNO" );
					}

				}
				catch ( Exception e )
				{
					e.printStackTrace();
					throw e;
				}
				finally
				{
					if ( dbrs != null )
					{
						dbrs.close();
					}
				}

				return bNo;
			}

				public Timestamp getDateFromString( String CountDateString ) throws Exception
				{
					Date countdate1=null;
					Timestamp CountDateTimeStamp=null;

					try
					{
						SimpleDateFormat countDateT=null;
						if ( CountDateString.length() == 10 )
						{
							if ( CountDateString.length() == 10 && CountDateString.substring( 2,3 ).equalsIgnoreCase( "/" ) )
							{
								countDateT=new SimpleDateFormat( "MM/dd/yyyy" );
							}
							else if ( CountDateString.length() == 10 && CountDateString.substring( 2,3 ).equalsIgnoreCase( "-" ) )
							{
								countDateT=new SimpleDateFormat( "MM-dd-yyyy" );
							}
							else if ( CountDateString.length() == 10 && CountDateString.substring( 2,3 ).equalsIgnoreCase( ":" ) )
							{
								countDateT=new SimpleDateFormat( "MM:dd:yyyy" );
							}
							else if ( CountDateString.length() == 10 && CountDateString.substring( 2,3 ).equalsIgnoreCase( " " ) )
							{
								countDateT=new SimpleDateFormat( "MM dd yyyy" );
							}
							else if ( CountDateString.length() == 10 && CountDateString.substring( 2,3 ).equalsIgnoreCase( " " ) )
							{
								countDateT=new SimpleDateFormat( "MM.dd.yyyy" );
							}

							if ( countDateT != null )
							{
								countdate1=countDateT.parse( CountDateString );
								CountDateTimeStamp=new Timestamp( countdate1.getTime() );
							}
						}
					}
					catch ( Exception e )
					{
						e.printStackTrace();
						throw e;
					}

					return CountDateTimeStamp;
				}

/*
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
	   cs = connect1.prepareCall("{call p_receipt_qc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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
	   cs.execute();

	   String errorcode = BothHelpObjs.makeBlankFromNull(cs.getString(18));
	   //reclog.info("errorcode    "+errorcode);
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
                  radian.web.emailHelpObj.sendnawazemail("Error in P_CONVERT_RECEIPT_TO_CUSTOMER Receipt ID :" + receipt_id + "","Error in P_CONVERT_RECEIPT_TO_CUSTOMER Receipt ID :" + receipt_id + "\nError Msg:\n"+testmsg+"");

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
			if ( usrcategory.equalsIgnoreCase( "2" ) && !( "2106".equalsIgnoreCase( Branch_plant ) || "2115".equalsIgnoreCase( Branch_plant ) || "2119".equalsIgnoreCase( Branch_plant ) ) )
            {
              //Call Trong Procedure
              String[] resulthg = new String[3];
              Hashtable hDdata = new Hashtable();
			  int nbocount = 0;

              try
              {
                  String query = "select * from no_buy_order_mr_create_view where receipt_id = "+receipt_id+"";
                  dbrs = db.doQuery(query);
                  rs=dbrs.getResultSet();

                  hDdata = new Hashtable();
                  while (rs.next())
                  {
                      nbocount++;
					  hDdata.put("COMPANY_ID",rs.getString("COMPANY_ID")==null?" ":rs.getString("COMPANY_ID"));
                      hDdata.put("CATALOG_ID",rs.getString("CATALOG_ID")==null?" ":rs.getString("CATALOG_ID"));
                      hDdata.put("ITEM_ID",rs.getString("ITEM_ID")==null?" ":rs.getString("ITEM_ID"));
                      hDdata.put("CAT_PART_NO",rs.getString("CAT_PART_NO")==null?" ":rs.getString("CAT_PART_NO"));
                      hDdata.put("ORDERING_FACILITY_ID",rs.getString("ORDERING_FACILITY_ID")==null?" ":rs.getString("ORDERING_FACILITY_ID"));
                      hDdata.put("ORDERING_APPLICATION",rs.getString("ORDERING_APPLICATION")==null?" ":rs.getString("ORDERING_APPLICATION"));
                      hDdata.put("PART_GROUP_NO",rs.getString("PART_GROUP_NO")==null?" ":rs.getString("PART_GROUP_NO"));
                      hDdata.put("INVENTORY_GROUP",rs.getString("INVENTORY_GROUP")==null?" ":rs.getString("INVENTORY_GROUP"));
                      //hDdata.put("RECEIPT_ID",rs.getString("RECEIPT_ID")==null?" ":rs.getString("RECEIPT_ID"));
                      hDdata.put("QUANTITY_RECEIVED",rs.getString("QUANTITY_RECEIVED")==null?" ":rs.getString("QUANTITY_RECEIVED"));
                      hDdata.put("STOCKED",rs.getString("STOCKED")==null?" ":rs.getString("STOCKED"));
                      hDdata.put("UNIT_PRICE",rs.getString("UNIT_PRICE")==null?" ":rs.getString("UNIT_PRICE"));
                      hDdata.put("CATALOG_PRICE",rs.getString("CATALOG_PRICE")==null?" ":rs.getString("CATALOG_PRICE"));
                      hDdata.put("ACCOUNT_SYS_ID",rs.getString("ACCOUNT_SYS_ID")==null?" ":rs.getString("ACCOUNT_SYS_ID"));
                      hDdata.put("PACKAGING",rs.getString("PACKAGING")==null?" ":rs.getString("PACKAGING"));
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
                        radian.web.emailHelpObj.sendnawazemail("Something Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+" in HubReceiving QC","\nSomething Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receipt_id+" \nIssue ID is < 0");

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
          cs = connect1.prepareCall("{call P_receipt_INSERT(?,?,?,?,?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
          cs.setInt(1,Integer.parseInt(Radian_po));
          cs.setInt(2,Integer.parseInt(PO_line));
          if (Item_id.trim().length() > 0){cs.setInt(3,Integer.parseInt(Item_id));}else{cs.setNull(3,java.sql.Types.INTEGER);}
          if (Mfg_lot.trim().length() > 0){cs.setString(4,Mfg_lot);}else{cs.setNull(4,java.sql.Types.VARCHAR);}
          if (Sel_bin.trim().length() > 0){cs.setString(5,Sel_bin);}else{cs.setNull(5,java.sql.Types.VARCHAR);}
          if (Date_mfgd.length() >1){cs.setTimestamp(6,radian.web.HTMLHelpObj.getDateFromString( "",Date_mfgd));}else{cs.setNull(6,java.sql.Types.DATE);}
          if (Date_shiped.length() >1){cs.setTimestamp(7,radian.web.HTMLHelpObj.getDateFromString( "",Date_shiped));}else{cs.setNull(7,java.sql.Types.DATE);}
          if (Qty_recd.trim().length() > 0){cs.setInt(8,Integer.parseInt(Qty_recd));}else{cs.setNull(8,java.sql.Types.NUMERIC);}
          cs.setString(9,"Incoming");
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
*/

}
