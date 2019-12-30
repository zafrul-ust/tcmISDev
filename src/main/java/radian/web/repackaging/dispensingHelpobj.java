package radian.web.repackaging;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 05-27-04 Showing the REPACKAGE_QUANTITY_AVAILABLE
 * 07-07-04 Fixed the search query to lower before comparing
 * 07-08-04 -Code Cleanup and avoiding showing duplicate rows for force repackagaing
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

public class dispensingHelpobj
{
  protected TcmISDBModel db;
  private Vector vLotSeqList=null;
  private Vector errorMsgs=null;
  private static org.apache.log4j.Logger dispnslog = org.apache.log4j.Logger.getLogger(dispensingHelpobj.class);

  public dispensingHelpobj()
  {
    vLotSeqList=new Vector();
    errorMsgs=new Vector();
  }

  public dispensingHelpobj( TcmISDBModel db )
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

  public Vector getavailtotaps( String Branch_id,String SearchText,String seinvengrp ) throws Exception
  {
    DBResultSet dbrs=null;
    ResultSet searchRs=null;
    Vector result=new Vector();
    Hashtable summary=new Hashtable();

    String user_query="select * from Tappable_inventory_view where ";
    user_query+="HUB = " + Branch_id + " ";

	SearchText = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(SearchText);
    if ( SearchText.length() > 0 )
    {
      user_query+=" and lower(SEARCH_TEXT) like lower('%" + SearchText + "%') ";
    }

    if (seinvengrp.length() > 0 && !"All".equalsIgnoreCase(seinvengrp))
    {
        user_query+="and INVENTORY_GROUP ='" + seinvengrp + "' ";
    }

    int count=0;
    summary.put( "TOTAL_NUMBER",new Integer( count ) );
    result.addElement( summary );

    int num_rec=0;
    try
    {
      dbrs=db.doQuery( user_query );
      searchRs=dbrs.getResultSet();
      while ( searchRs.next() )
      {
        num_rec++;
        Hashtable DataH=new Hashtable();

		DataH.put( "HUB",searchRs.getString( "HUB" ) == null ? "" : searchRs.getString( "HUB" ) );
		DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
		DataH.put( "INVENTORY_GROUP",searchRs.getString( "INVENTORY_GROUP" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP" ) );
		DataH.put( "ORIGINAL_ITEM_ID",searchRs.getString( "ORIGINAL_ITEM_ID" ) == null ? "" : searchRs.getString( "ORIGINAL_ITEM_ID" ) );
		DataH.put( "RECEIPT_ID",searchRs.getString( "RECEIPT_ID" ) == null ? "" : searchRs.getString( "RECEIPT_ID" ) );
		DataH.put( "BIN_NAME",searchRs.getString( "BIN" ) == null ? "" : searchRs.getString( "BIN" ) );
		DataH.put( "MFG_LOT",searchRs.getString( "MFG_LOT" ) == null ? "" : searchRs.getString( "MFG_LOT" ) );
		DataH.put( "EXPIRE_DATE",searchRs.getString( "EXPIRE_DATE" ) == null ? "" : searchRs.getString( "EXPIRE_DATE" ) );
		DataH.put( "LOT_STATUS",searchRs.getString( "LOT_STATUS" ) == null ? "" : searchRs.getString( "LOT_STATUS" ) );
		DataH.put( "QUANTITY_AVAILABLE",searchRs.getString( "QUANTITY_AVAILABLE" ) == null ? "" : searchRs.getString( "QUANTITY_AVAILABLE" ) );
		DataH.put( "ITEM_DESC",searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ) );
		DataH.put( "ORIGINAL_ITEM_DESC",searchRs.getString( "ORIGINAL_ITEM_DESC" ) == null ? "" : searchRs.getString( "ORIGINAL_ITEM_DESC" ) );
		DataH.put( "ITEM_PKG",searchRs.getString( "ITEM_PKG" ) == null ? "" : searchRs.getString( "ITEM_PKG" ) );
		DataH.put( "ORIGINAL_ITEM_PKG",searchRs.getString( "ORIGINAL_ITEM_PKG" ) == null ? "" : searchRs.getString( "ORIGINAL_ITEM_PKG" ) );
		DataH.put( "SEARCH_TEXT",searchRs.getString( "SEARCH_TEXT" ) == null ? "" : searchRs.getString( "SEARCH_TEXT" ) );
		DataH.put( "BIN",searchRs.getString( "BIN" ) == null ? "" : searchRs.getString( "BIN" ) );
		DataH.put( "USERCHK","" );

        result.addElement( DataH );
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
      if ( dbrs != null ){dbrs.close();}
    }

    Hashtable recsum=new Hashtable();
    recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
    result.setElementAt( recsum,0 );

    return result;
  }

  public Vector getforcerepacklist( String Branch_id,String SearchText,String seinvengrp ) throws Exception
   {
	 DBResultSet dbrs=null;
	 ResultSet searchRs=null;
	 Vector result=new Vector();
	 Hashtable summary=new Hashtable();

	 String user_query="select distinct ITEM_ID,ITEM_DESC,ITEM_PKG,HUB,INVENTORY_GROUP from FORCE_REPACKAGE_ORDER_VIEW where ";
	 user_query+="HUB = " + Branch_id + " ";

	 SearchText = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(SearchText);
	 if ( SearchText.length() > 0 )
	 {
	   user_query+=" and lower(SEARCH_TEXT) like lower('%" + SearchText + "%') ";
	 }

	 if (seinvengrp.length() > 0 && !"All".equalsIgnoreCase(seinvengrp))
	 {
		 user_query+="and INVENTORY_GROUP ='" + seinvengrp + "' ";
	 }

	 int count=0;
	 summary.put( "TOTAL_NUMBER",new Integer( count ) );
	 result.addElement( summary );

	 int num_rec=0;
	 try
	 {
	   dbrs=db.doQuery( user_query );
	   searchRs=dbrs.getResultSet();

	   while ( searchRs.next() )
	   {
		 num_rec++;
		 Hashtable DataH=new Hashtable();

		 DataH.put("ITEM_ID",searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID"));
		 //DataH.put("ORIGINAL_ITEM_ID",searchRs.getString("ORIGINAL_ITEM_ID")==null?"":searchRs.getString("ORIGINAL_ITEM_ID"));
		 DataH.put("HUB",searchRs.getString("HUB")==null?"":searchRs.getString("HUB"));
		 DataH.put("INVENTORY_GROUP",searchRs.getString("INVENTORY_GROUP")==null?"":searchRs.getString("INVENTORY_GROUP"));
		 //DataH.put("RECEIPT_ID",searchRs.getString("RECEIPT_ID")==null?"":searchRs.getString("RECEIPT_ID"));
		 //DataH.put("BIN",searchRs.getString("BIN")==null?"":searchRs.getString("BIN"));
		 //DataH.put("MFG_LOT",searchRs.getString("MFG_LOT")==null?"":searchRs.getString("MFG_LOT"));
		 //DataH.put("EXPIRE_DATE",searchRs.getString("EXPIRE_DATE")==null?"":searchRs.getString("EXPIRE_DATE"));
		 //DataH.put("QUANTITY_AVAILABLE",searchRs.getString("QUANTITY_AVAILABLE")==null?"":searchRs.getString("QUANTITY_AVAILABLE"));
		 DataH.put("ITEM_DESC",searchRs.getString("ITEM_DESC")==null?"":searchRs.getString("ITEM_DESC"));
		 DataH.put("ITEM_PKG",searchRs.getString("ITEM_PKG")==null?"":searchRs.getString("ITEM_PKG"));
		 //DataH.put("SEARCH_TEXT",searchRs.getString("SEARCH_TEXT")==null?"":searchRs.getString("SEARCH_TEXT"));

		 DataH.put( "USERCHK","" );
		 DataH.put( "QUANTITY_PICKED","" );

		 result.addElement( DataH );
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
	   if ( dbrs != null ){dbrs.close();}
	 }

	 Hashtable recsum=new Hashtable();
	 recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
	 result.setElementAt( recsum,0 );

	 return result;
   }

  public Vector getAllopenOrder( String Branch_id,String SearchText,String SortBy,String seinvengrp ) throws Exception
	{
	  DBResultSet dbrs=null;
	  ResultSet searchRs=null;
	  Vector result=new Vector();
	  Hashtable summary=new Hashtable();

	  String user_query="select * from repackage_order_view where HUB = " + Branch_id + " ";
	  if ( SearchText.length() > 0 )
	  {
		user_query+=" and lower(SEARCH_TEXT) like lower('%" + SearchText + "%') ";
	  }

	  if (seinvengrp.length() > 0 && !"All".equalsIgnoreCase(seinvengrp))
	  {
		  user_query+="and INVENTORY_GROUP ='" + seinvengrp + "' ";
	  }

	  /*if ( SortBy.equalsIgnoreCase( "1" ) )
	  {
		user_query+=" order by x.item_id,x.lot_status,x.receipt_id ";
	  }
	  else if ( SortBy.equalsIgnoreCase( "2" ) )
	  {
		user_query+=" order by x.bin,x.receipt_id ";
	  }
	  else if ( SortBy.equalsIgnoreCase( "3" ) )
	  {
		user_query+=" order by x.owner_company_id,x.bin,x.receipt_id ";
	  }*/

	user_query+=" order by ITEM_ID,PR_NUMBER,RECEIPT_ID ";

	  int count=0;
	  summary.put( "TOTAL_NUMBER",new Integer( count ) );
	  result.addElement( summary );

	  int num_rec=0;
	  try
	  {
		dbrs=db.doQuery( user_query );
		searchRs=dbrs.getResultSet();

		while ( searchRs.next() )
		{
		  num_rec++;
		  Hashtable DataH=new Hashtable();

		  DataH.put( "PR_NUMBER",searchRs.getString( "PR_NUMBER" ) == null ? "" : searchRs.getString( "PR_NUMBER" ) );
		  DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
		  DataH.put( "ORDER_QUANTITY",searchRs.getString( "ORDER_QUANTITY" ) == null ? "" : searchRs.getString( "ORDER_QUANTITY" ) );
		  DataH.put( "ORIGINAL_ITEM_ID",searchRs.getString( "ORIGINAL_ITEM_ID" ) == null ? "" : searchRs.getString( "ORIGINAL_ITEM_ID" ) );
		  DataH.put( "HUB",searchRs.getString( "HUB" ) == null ? "" : searchRs.getString( "HUB" ) );
		  DataH.put( "INVENTORY_GROUP",searchRs.getString( "INVENTORY_GROUP" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP" ) );
		  DataH.put( "RECEIPT_ID",searchRs.getString( "RECEIPT_ID" ) == null ? "" : searchRs.getString( "RECEIPT_ID" ) );
		  DataH.put( "BIN",searchRs.getString( "BIN" ) == null ? "" : searchRs.getString( "BIN" ) );
		  DataH.put( "MFG_LOT",searchRs.getString( "MFG_LOT" ) == null ? "" : searchRs.getString( "MFG_LOT" ) );
		  DataH.put( "EXPIRE_DATE",searchRs.getString( "EXPIRE_DATE" ) == null ? "" : searchRs.getString( "EXPIRE_DATE" ) );
		  DataH.put( "QUANTITY_AVAILABLE",searchRs.getString( "QUANTITY_AVAILABLE" ) == null ? "" : searchRs.getString( "QUANTITY_AVAILABLE" ) );
		  DataH.put( "ITEM_DESC",searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ) );
		  DataH.put( "ITEM_PKG",searchRs.getString( "ITEM_PKG" ) == null ? "" : searchRs.getString( "ITEM_PKG" ) );
		  DataH.put( "SEARCH_TEXT",searchRs.getString( "SEARCH_TEXT" ) == null ? "" : searchRs.getString( "SEARCH_TEXT" ) );
		  DataH.put( "MD_ITEM_PKG",searchRs.getString( "MD_ITEM_PKG" ) == null ? "" : searchRs.getString( "MD_ITEM_PKG" ) );
		  DataH.put( "MA_ITEM_PKG",searchRs.getString( "MA_ITEM_PKG" ) == null ? "" : searchRs.getString( "MA_ITEM_PKG" ) );
		  DataH.put( "TAP_AVAILABLE",searchRs.getString( "TAP_AVAILABLE" ) == null ? "" : searchRs.getString( "TAP_AVAILABLE" ) );
		  DataH.put( "REPACKAGE_QUANTITY_AVAILABLE",searchRs.getString( "REPACKAGE_QUANTITY_AVAILABLE" ) == null ? "" : searchRs.getString( "REPACKAGE_QUANTITY_AVAILABLE" ) );
		  DataH.put( "CLOSEABLE",searchRs.getString( "CLOSEABLE" ) == null ? "" : searchRs.getString( "CLOSEABLE" ) );
		  DataH.put( "USERCHK","" );
		  DataH.put( "QUANTITY_PICKED","" );
		  DataH.put( "UPDATEDONE","" );

		  result.addElement( DataH );
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
		if ( dbrs != null ){dbrs.close();}
	  }

	  Hashtable recsum=new Hashtable();
	  recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
	  result.setElementAt( recsum,0 );

	  return result;
	}

  public Hashtable UpdateLine( Hashtable hD,String loginID )
  {
	boolean result=false;
	Hashtable resulth=new Hashtable();
	String errorcode = "";
	int RECEIPT_ID1=0;
	String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
	String receipt_id=hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString();
	String Sel_bin=hD.get( "BIN" ) == null ? "&nbsp;" : hD.get( "BIN" ).toString();
	String qtypicked=hD.get( "QUANTITY_PICKED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_PICKED" ).toString();
	String prnumber=hD.get( "PR_NUMBER" ) == null ? "&nbsp;" : hD.get( "PR_NUMBER" ).toString();

	CallableStatement cs=null;

	try
	{
	  Connection connect1=db.getConnection();
	  cs=connect1.prepareCall( "{call P_repackage(?,?,?,?,?,?,?)}" );
	  cs.setInt( 1,Integer.parseInt( receipt_id ) );
	  cs.setInt( 2,Integer.parseInt( item_id ) );
	  cs.setInt( 3,Integer.parseInt( qtypicked ) );
	  cs.setString( 4,Sel_bin );
	  cs.setInt( 5,Integer.parseInt( prnumber ) );

	  cs.registerOutParameter( 6,java.sql.Types.NUMERIC );
	  cs.registerOutParameter( 7,java.sql.Types.VARCHAR );
	  cs.execute();

	  errorcode=BothHelpObjs.makeBlankFromNull( cs.getString( 7 ) );
	  RECEIPT_ID1=cs.getInt( 6 );
	  if ( errorcode.trim().length() > 0 ||  (RECEIPT_ID1 == -1))
	  {
		result=false;
		errorMsgs.addElement( errorcode );
	  }
	  else
	  {
		result=true;
	  }
	  dispnslog.info("Receipt ID created from P_repackage for Item "+item_id+"  are  "+RECEIPT_ID1+"  Personnel ID  "+loginID+" ");

	}
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	   result=false;
	   radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure p_create_repackage_order in Force Repackage Order","Error occured while running p_create_repackage_order\n" + e.getMessage() +"\nFor Input Parameters item_id   "+item_id+"   qtypicked   "+qtypicked+"   Sel_bin   "+Sel_bin+"   receipt_id   "+receipt_id+"   prnumber   "+prnumber+" " );
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
		   se.printStackTrace();
		 }
	   }
	 }

   resulth.put( "RCECIPT_ID1","" + RECEIPT_ID1 + "" );
   resulth.put( "RESULT",new Boolean( result ) );
   return resulth;
  }

  public boolean startnewTap( Hashtable hD,String loginID )
  {
	boolean result=false;
	String errorcode = "";
	String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
	String receipt_id=hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString();
	String Sel_bin=hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString();
	int RECEIPT_ID1=0;

	CallableStatement cs=null;

	try
	{
	  Connection connect1=db.getConnection();
	  cs=connect1.prepareCall( "{call P_tap(?,?,?,?,?)}" );
	  cs.setInt( 1,Integer.parseInt( receipt_id ) );
	  cs.setInt( 2,Integer.parseInt( item_id ) );
	  cs.setString( 3,Sel_bin );
	  cs.registerOutParameter(4,java.sql.Types.NUMERIC);
      cs.registerOutParameter(5,java.sql.Types.VARCHAR);
	  cs.execute();

	  RECEIPT_ID1 = cs.getInt(4);
	  errorcode = BothHelpObjs.makeBlankFromNull(cs.getString(5));
	  if (errorcode.trim().length() >0)
	  {
		  result = false;
		  errorMsgs.addElement(errorcode);
	  }
	  else
	  {
		  result = true;
		  Integer intreceipt_id = new Integer(RECEIPT_ID1);
          vLotSeqList.addElement(intreceipt_id);
	  }
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		result=false;
		radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure P_tap in Create New Tap","Error occured while running P_tap in Create New Tap\n" + e.getMessage() +"\nFor Input Parameters Receipt Id: " + receipt_id + " item_id   "+item_id+"   bin   "+Sel_bin+" " );
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
			se.printStackTrace();
		  }
		}
	  }

  return result;
}

public boolean forcerepack( Hashtable hD,String loginID )
  {
	boolean result=false;
	String errorcode = "";
	String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
	String qtypicked=hD.get( "QUANTITY_PICKED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_PICKED" ).toString();
	String hub=hD.get( "HUB" ) == null ? "&nbsp;" : hD.get( "HUB" ).toString();
	String inventory_group=hD.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : hD.get( "INVENTORY_GROUP" ).toString();

	CallableStatement cs=null;

	try
	{
	  Connection connect1=db.getConnection();
	  cs=connect1.prepareCall( "{call p_create_repackage_order(?,?,?,?,?)}" );
	  cs.setInt( 1,Integer.parseInt( item_id ) );
	  cs.setInt( 2,Integer.parseInt( qtypicked ) );
	  cs.setString( 3,hub );
	  cs.setString( 4,inventory_group );

	  cs.registerOutParameter(5,java.sql.Types.VARCHAR);
	  cs.execute();

	  errorcode = BothHelpObjs.makeBlankFromNull(cs.getString(5));
	  dispnslog.info("Error Code from p_create_repackage_order for Item "+item_id+"  Qty   "+qtypicked+"  is  "+errorcode+"  Personnel ID  "+loginID+" ");

	  if (!"OK".equalsIgnoreCase(errorcode))
	  {
		  result = false;
		  errorMsgs.addElement(errorcode);
	  }
	  else
	  {
		  result = true;
	  }
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		result=false;
		radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure p_create_repackage_order in Force Repackage Order","Error occured while running p_create_repackage_order\n" + e.getMessage() +"\nFor Input Parameters item_id   "+item_id+"   qtypicked   "+qtypicked+"   hub   "+hub+"   inventory_group   "+inventory_group+"" );
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
			se.printStackTrace();
		  }
		}
	  }

  return result;
}

}