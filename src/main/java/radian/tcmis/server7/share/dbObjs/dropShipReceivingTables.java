package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 07-30-03 - No Need to break up the Item_desc it auto wraps itself
 * 08-15-03 - Sending emails through common object
 * 10-06-03 - Adding the CUSTOMER_PO to the result set
 * 05-04-04 - Giving more options to search
 * 07-18-05 - Giving the ability to enter receipt notes
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;

public class dropShipReceivingTables
{

  private TcmISDBModel db;
  private Vector vLotSeqList=null;
  private Vector errorMsgs=null;

  public dropShipReceivingTables()
  {
    vLotSeqList=new Vector();
    errorMsgs=new Vector();
  }

  public dropShipReceivingTables( TcmISDBModel db )
  {
    this.db=db;
    vLotSeqList=new Vector();
    errorMsgs=new Vector();
  }

  public void setDB( TcmISDBModel db )
  {
    this.db=db;
  }

  public void setLotSeqList()
  {
    this.vLotSeqList=vLotSeqList;
  }

  public Vector getLotSeqList()
  {
    return this.vLotSeqList;
  }

  public Vector getErrMsgs()
  {
    return this.errorMsgs;
  }

  public Vector getAllopenOrder( String Branch_id,String SearchText,String SortBy,String noOfDays, String searchlike, String searchfor ) throws Exception
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
    else if ( SortBy.equalsIgnoreCase( "2" ) )
    {
      sort_order=" order by  LAST_SUPPLIER, EXPECTED ";
    }
    else if ( SortBy.equalsIgnoreCase( "3" ) )
    {
      sort_order=" order by EXPECTED, LAST_SUPPLIER ";
    }
    else if ( SortBy.equalsIgnoreCase( "4" ) )
    {
      sort_order=" order by MR_NUMBER, EXPECTED ";
    }

    String user_query="select distinct a.CUSTOMER_PO,a.BRANCH_PLANT,a.DELIVERY_POINT,a.REQUESTOR_NAME,a.MR_QTY_OPEN,a.MR_LINE_ITEM,a.MR_NUMBER,a.CRITICAL,to_char(sysdate,'mm/dd/yyyy') SYS_DATE, a.RADIAN_PO,a.LAST_SUPPLIER, a.QTY_OPEN ,a.ITEM_ID, a.EXPECTED, a.ITEM_DESCRIPTION , a.PACKAGING, a.TRANSFER_REQUEST_ID, a.PO_NOTES, a.TRANS_TYPE, ";
    user_query+=" a.LINE_ITEM,a.INDEFINITE_SHELF_LIFE from drop_ship_receiving_view a";
    user_query+=" where a.EXPECTED < sysdate + " + noOfDays + " and a.SHIP_TO_LOCATION_ID = '" + Branch_id + "'";

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
        String notes=rs.getString( "PO_NOTES" ) == null ? "" : rs.getString( "PO_NOTES" );
        hD.put( "SPECIFICATION",notes );
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

        hD.put( "MR_NUMBER",rs.getString( "MR_NUMBER" ) == null ? "" : rs.getString( "MR_NUMBER" ) );
        hD.put( "MR_LINE_ITEM",rs.getString( "MR_LINE_ITEM" ) == null ? "" : rs.getString( "MR_LINE_ITEM" ) );
        hD.put( "MR_QTY_OPEN",rs.getString( "MR_QTY_OPEN" ) == null ? "" : rs.getString( "MR_QTY_OPEN" ) );
        hD.put( "REQUESTOR_NAME",rs.getString( "REQUESTOR_NAME" ) == null ? "" : rs.getString( "REQUESTOR_NAME" ) );
        hD.put( "DELIVERY_POINT",rs.getString( "DELIVERY_POINT" ) == null ? "" : rs.getString( "DELIVERY_POINT" ) );
        hD.put( "BRANCH_PLANT",rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ) );
		hD.put( "CUSTOMER_PO",rs.getString( "CUSTOMER_PO" ) == null ? "" : rs.getString( "CUSTOMER_PO" ) );
		hD.put( "NOTES", "" );

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

    return result;
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

    int RECEIPT_ID1=0;
    int Issue_id=0;
    String errorcode="";
    String batchno = "";
	String recnotes = hD.get( "NOTES" ) == null ? " " : hD.get( "NOTES" ).toString();

    Connection connect1;
	if (  mrnumber.trim().length() > 0 && mrlineitem.trim().length() > 0 )
	{
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

        cs=connect1.prepareCall( "{call P_RECEIVE_DROP_SHIP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
        cs.setInt( 1,Integer.parseInt( Radian_po ) );
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
          cs.setInt( 17,Integer.parseInt( mrnumber ) );
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
        //System.out.println( "\n\n--------- 'IT' Drop Ship Receiving Procedure Result is: \nRECEIPT_ID  : " + RECEIPT_ID1 + "\nIssue_id   :" + Issue_id + "\nerrorcode   :" + errorcode );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        radian.web.emailHelpObj.senddatabaseHelpemails("Error occured while running call P_RECEIVE_DROP_SHIP in dropShipReceivingTables","Error occured while running call P_RECEIVE_DROP_SHIP \n\nError Msg:\n " + e.getMessage() + "\n\n Parameters passed P_RECEIVE_DROP_SHIP Radian_po " + Radian_po + "\nPO_line " + PO_line +
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
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      result=false;
    }
	}
	else
	{
	  result=false;
	  radian.web.emailHelpObj.sendnawazemail("Error occured while running call P_RECEIVE_DROP_SHIP in dropShipReceivingTables","Error occured while running call P_RECEIVE_DROP_SHIP \n\nError Msg:\n No MR Line Passed\n\n Parameters passed P_RECEIVE_DROP_SHIP Radian_po " + Radian_po + "\nPO_line " + PO_line +
							   " \n Item_id " + Item_id + "\n Mfg_lot " + Mfg_lot + "\n Date_mfgd " + Date_mfgd + "\n Date_shiped " + Date_shiped + "\n Qty_recd " + Qty_recd +
							   "\n Lot Status Incoming\n transfer id " + xfer_req_id + "\n Date_Recieved " + Date_Recieved + "\nBranch_plant " + Branch_plant + "\npersonnel Id " + loginID + "\n trans_type " +
							   trans_type + "\nExpiry_Date " + Expiry_Date + "\nIT ReceiptID " + Intreceipt_id + "\nACTUAL_SHIP_DATE  "+actshipDate+"\nMR_NUMBER   "+mrnumber+"\nMR_LINE_ITEM   "+mrlineitem+"" );
	}
    resulth.put( "RCECIPT_ID","" + RECEIPT_ID1 + "" );
    resulth.put( "RESULT",new Boolean( result ) );

    return resulth;
  }

  public Hashtable createDropdownlist( String personnelid,boolean doupdate )
  {
    DBResultSet dbrs=null;
    ResultSet rs=null;
    Hashtable hub_list=new Hashtable();
    try
    {
      String query= "";

      if (doupdate)
      {
        query="select * from user_group_member_location where personnel_id = " + personnelid + "";
      }
      else
      {
        query="SELECT l.LOCATION_ID SHIP_TO_LOCATION_ID FROM location l,(select distinct facility_id,location_id from fac_loc_app WHERE location_id <> 'All' and STATUS = 'A') f where l.location_id =f.location_id ";
      }

      dbrs=db.doQuery( query );
      rs=dbrs.getResultSet();
      while ( rs.next() )
      {
        String temp1=rs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "BLANK" : rs.getString( "SHIP_TO_LOCATION_ID" );
        if ( !temp1.equalsIgnoreCase( "BLANK" ) )
        {
          hub_list.put( temp1,temp1 );
        }
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      }
    finally
    {
      dbrs.close();
    }

    return hub_list;
  }

}
