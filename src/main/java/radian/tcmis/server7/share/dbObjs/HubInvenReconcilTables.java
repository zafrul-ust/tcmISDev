package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput
 * @version
 * 07-29-03 - Putting back the unit_cost
 * 07-30-03 - No Need to break up the Item_desc it auto wraps itself
 * 08-15-03 - Sending emails through common object
 * 12-10-03 - changes so that p_update_inventory_count will work for fractional quantities like 0.8
 * 12-11-03 Added Expire Date,owner_company_id and Catpart Number to the excel sheet
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import oracle.jdbc.OracleTypes;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class HubInvenReconcilTables
{
    private TcmISDBModel db;
    private   Vector vLotSeqList = null;
    private   Vector errorMsgs = null;

    public HubInvenReconcilTables()
    {
        vLotSeqList = new Vector();
        errorMsgs = new Vector();
    }

    public HubInvenReconcilTables(TcmISDBModel db )
    {
        this.db = db;
        vLotSeqList = new Vector();
        errorMsgs = new Vector();
    }

    public void setDB(TcmISDBModel db)
    {
        this.db = db;
    }

    public void setLotSeqList( )
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

    public Vector getCountDates( String Hubnum )
    {
      String query="select distinct to_char(COUNT_DATETIME,'dd Mon yyyy hh24:mi') COUNT_DATE_TIME,COUNT_DATETIME from hub_count_date where hub = " + Hubnum + " order by COUNT_DATETIME desc";
      DBResultSet dbrs=null;
      ResultSet rs=null;
      Hashtable result=null;
      result=new Hashtable();
      Vector ResultV=new Vector();
      try
      {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();
        while ( rs.next() )
        {
          result=new Hashtable();
          String facn= ( rs.getString( "COUNT_DATE_TIME" ) == null ? "" : rs.getString( "COUNT_DATE_TIME" ) );
          result.put( facn,facn );
          ResultV.addElement( result );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
      }
      finally
      {
        dbrs.close();
      }
      return ResultV;
     }

     public Vector OrderByItem( String itemquery ) throws Exception
     {
       Vector ResultV=new Vector();
       Hashtable summary=new Hashtable();
       int num_rec=0;
       int count=0;
       summary.put( "TOTAL_NUMBER",new Integer( count ) );
       ResultV.addElement( summary );
       DBResultSet dbrs=null;
       ResultSet RsI=null;

       try
       {
         dbrs=db.doQuery( itemquery );
         RsI=dbrs.getResultSet();

         while ( RsI.next() )
         {
           num_rec++;

           Hashtable hD=new Hashtable();
           hD.put( "ITEM_ID",RsI.getString( "ITEM_ID" ) == null ? "" : RsI.getString( "ITEM_ID" ) );
           hD.put( "ITEM_DESC",RsI.getString( "ITEM_DESC" ) == null ? "" : RsI.getString( "ITEM_DESC" ) );
           hD.put( "LOT_STATUS",RsI.getString( "LOT_STATUS" ) == null ? "" : RsI.getString( "LOT_STATUS" ) );
           hD.put( "BIN",RsI.getString( "BIN" ) == null ? "" : RsI.getString( "BIN" ) );
           hD.put( "MFG_LOT",RsI.getString( "MFG_LOT" ) == null ? "" : RsI.getString( "MFG_LOT" ) );
           hD.put( "RECEIPT_ID",RsI.getString( "RECEIPT_ID" ) == null ? "" : RsI.getString( "RECEIPT_ID" ) );
           hD.put( "PACKAGING",RsI.getString( "PACKAGING" ) == null ? "" : RsI.getString( "PACKAGING" ) );
           hD.put( "MANUFACTURER",RsI.getString( "MANUFACTURER" ) == null ? "" : RsI.getString( "MANUFACTURER" ) );
           hD.put( "ON_HAND",RsI.getString( "ON_HAND" ) == null ? "" : RsI.getString( "ON_HAND" ) );
           hD.put( "ACTUAL_ON_HAND",RsI.getString( "ACTUAL_QUANTITY" ) == null ? "" : RsI.getString( "ACTUAL_QUANTITY" ) );
           hD.put( "RADIAN_PO",RsI.getString( "RADIAN_PO" ) == null ? "" : RsI.getString( "RADIAN_PO" ) );
           hD.put( "DOSTATUSUPDATE","" );
           hD.put( "EXPIRE_DATE",RsI.getString( "EXPIRE_DATE" ) == null ? " " : RsI.getString( "EXPIRE_DATE" ) );
           hD.put("UNIT_COST",RsI.getString("unit_cost")==null?"":RsI.getString("unit_cost"));
		       hD.put("cat_part_nos",RsI.getString("cat_part_nos")==null?"":RsI.getString("cat_part_nos"));
		       hD.put("owner_company_id",RsI.getString("owner_company_id")==null?"":RsI.getString("owner_company_id"));
           hD.put( "INVENTORY_GROUP",RsI.getString( "INVENTORY_GROUP" ) == null ? "" : RsI.getString( "INVENTORY_GROUP" ) );
           hD.put("CAT_PART_NO",RsI.getString("CAT_PART_NO")==null?"":RsI.getString("CAT_PART_NO"));
           hD.put("CUSTOMER_RECEIPT_ID",RsI.getString("CUSTOMER_RECEIPT_ID")==null?"":RsI.getString("CUSTOMER_RECEIPT_ID"));
           ResultV.addElement( hD );
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

       Hashtable recsum=new Hashtable();
       recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
       ResultV.setElementAt( recsum,0 );

       return ResultV;
     }

     public Vector OrderByBin( String binquery ) throws Exception
     {
       Vector ResultV=new Vector();
       Hashtable summary=new Hashtable();
       int num_rec=0;
       int count=0;
       summary.put( "TOTAL_NUMBER",new Integer( count ) );
       ResultV.addElement( summary );
       DBResultSet dbrs=null;
       ResultSet RsB=null;

       try
       {
         dbrs=db.doQuery( binquery );
         RsB=dbrs.getResultSet();

         while ( RsB.next() )
         {
           num_rec++;

           Hashtable hD=new Hashtable();
           hD.put( "ITEM_ID",RsB.getString( "ITEM_ID" ) == null ? "" : RsB.getString( "ITEM_ID" ) );
           hD.put( "ITEM_DESC",RsB.getString( "ITEM_DESC" ) == null ? "" : RsB.getString( "ITEM_DESC" ) );
           hD.put( "LOT_STATUS",RsB.getString( "LOT_STATUS" ) == null ? "" : RsB.getString( "LOT_STATUS" ) );
           hD.put( "BIN",RsB.getString( "BIN" ) == null ? "" : RsB.getString( "BIN" ) );
           hD.put( "MFG_LOT",RsB.getString( "MFG_LOT" ) == null ? "" : RsB.getString( "MFG_LOT" ) );
           hD.put( "RECEIPT_ID",RsB.getString( "RECEIPT_ID" ) == null ? "" : RsB.getString( "RECEIPT_ID" ) );
           hD.put( "PACKAGING",RsB.getString( "PACKAGING" ) == null ? "" : RsB.getString( "PACKAGING" ) );
           hD.put( "MANUFACTURER",RsB.getString( "MANUFACTURER" ) == null ? "" : RsB.getString( "MANUFACTURER" ) );
           hD.put( "ON_HAND",RsB.getString( "ON_HAND" ) == null ? "" : RsB.getString( "ON_HAND" ) );
           hD.put( "ACTUAL_ON_HAND",RsB.getString( "ACTUAL_QUANTITY" ) == null ? "" : RsB.getString( "ACTUAL_QUANTITY" ) );
           hD.put( "RADIAN_PO",RsB.getString( "RADIAN_PO" ) == null ? "" : RsB.getString( "RADIAN_PO" ) );
           hD.put( "EXPIRE_DATE",RsB.getString( "EXPIRE_DATE" ) == null ? " " : RsB.getString( "EXPIRE_DATE" ) );
           hD.put("UNIT_COST",RsB.getString("unit_cost")==null?"":RsB.getString("unit_cost"));
		       hD.put("cat_part_nos",RsB.getString("cat_part_nos")==null?"":RsB.getString("cat_part_nos"));
		       hD.put("owner_company_id",RsB.getString("owner_company_id")==null?"":RsB.getString("owner_company_id"));
           hD.put( "DOSTATUSUPDATE","" );
           hD.put( "INVENTORY_GROUP",RsB.getString( "INVENTORY_GROUP" ) == null ? "" : RsB.getString( "INVENTORY_GROUP" ) );
           hD.put("CAT_PART_NO",RsB.getString("CAT_PART_NO")==null?"":RsB.getString("CAT_PART_NO"));
           hD.put("CUSTOMER_RECEIPT_ID",RsB.getString("CUSTOMER_RECEIPT_ID")==null?"":RsB.getString("CUSTOMER_RECEIPT_ID"));

           ResultV.addElement( hD );
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

       Hashtable recsum=new Hashtable();
       recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
       ResultV.setElementAt( recsum,0 );

       return ResultV;
     }

     public Vector getOpenInventory( String Branch_id,String countDate,String showall,String usrcategory,String searchThis,
                                     String showproblemOnly,String invengrp ) throws Exception
     {
       Vector result=new Vector();
       Hashtable summary=new Hashtable();
       Connection connect1=null;
       CallableStatement cs=null;
       int count=0;
       summary.put( "TOTAL_NUMBER",new Integer( count ) );
       result.addElement( summary );
       if (invengrp.equalsIgnoreCase("All"))
       {
         invengrp = "N";
       }

       if (!invengrp.equalsIgnoreCase("N"))
       {
         invengrp = "("+invengrp+")";
       }

       try
       {
         connect1=db.getConnection();
         //System.out.println( "Here CountDate   " + SortBy + "  " + Branch_id + "    " + usrcategory + "  Type  " + showall + " searchThis  " + searchThis );

         if ( "All".equalsIgnoreCase( showall ) )
         {
           cs=connect1.prepareCall( "{? = call Pkg_query.fx_all_inventory(?,?,?,?,?)}" );
         }
         else if ( "Item".equalsIgnoreCase( showall ) )
         {
           cs=connect1.prepareCall( "{? = call Pkg_query.FX_INVENTORY_ITEM(?,?,?,?,?)}" );
         }
         else if ( "Receipts".equalsIgnoreCase( showall ) )
         {
           cs=connect1.prepareCall( "{? = call Pkg_query.FX_receipt_TO_RECONCILE(?,?,?,?)}" );
         }
         else
         {
           cs=connect1.prepareCall( "{? = call Pkg_query.fx_inventory_to_reconcile(?,?,?,?)}" );
         }

         if ( "Item".equalsIgnoreCase( showall ) )
         {
           //cs.registerOutParameter( 1,OracleTypes.CURSOR );
           cs.registerOutParameter( 1,OracleTypes.VARCHAR );
           cs.setInt( 2,Integer.parseInt( Branch_id ) );
           cs.setInt( 3,Integer.parseInt( searchThis ) );
           cs.setTimestamp( 4,this.getDateFromString( countDate ) );
           if ( usrcategory.equalsIgnoreCase( "1" ) )
           {
             cs.setString( 5,"BIN" );
           }
           else
           {
             cs.setString( 5,"ITEM_ID" );
           }
           if ( invengrp.trim().length() >0 )
           {
             cs.setString( 6,invengrp );
           }
           else
           {
             cs.setNull(6,java.sql.Types.VARCHAR);
           }
         }
         else
         {
           //cs.registerOutParameter( 1,OracleTypes.CURSOR );
           cs.registerOutParameter( 1,OracleTypes.VARCHAR );
           cs.setInt( 2,Integer.parseInt( Branch_id ) );
           cs.setTimestamp( 3,this.getDateFromString( countDate ) );
           if ( usrcategory.equalsIgnoreCase( "1" ) )
           {
             cs.setString( 4,"BIN" );
           }
           else
           {
             cs.setString( 4,"ITEM_ID" );
           }
				   if ( "All".equalsIgnoreCase( showall ) )
				   {
						 cs.setString( 5,showproblemOnly );
             if ( invengrp.trim().length() >0 )
             {
              cs.setString( 6,invengrp );
             }
             else
             {
              cs.setNull(6,java.sql.Types.VARCHAR);
             }
          }
        	else
				  {
             if ( invengrp.trim().length() >0 )
             {
              cs.setString( 5,invengrp);
             }
             else
             {
              cs.setNull(5,java.sql.Types.VARCHAR);
             }
          }
       }


         cs.execute();
         String searchquery=cs.getString( 1 );
         //System.out.println("The search query retunred   "+searchquery);
         //rs= ( ResultSet ) cs.getObject( 1 );

         if ( usrcategory.equalsIgnoreCase( "1" ) )
         {
           result=OrderByBin( searchquery );
         }
         else
         {
           result=OrderByItem( searchquery );
         }
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         throw e;
       }
       finally
       {
         cs.close();
       }

       return result;
     }

     public Vector getReceiptIdItem( String Branch_id,String ItemID,String SortBy ) throws Exception
     {
       DBResultSet dbrs=null;
       ResultSet rs=null;
       Vector result=new Vector();
       Hashtable summary=new Hashtable();
       String query="select * from issue_recon_item_view where hub =" + Branch_id + " and item_id = " + ItemID + " and COUNT_DATETIME = to_date('" + SortBy + "','dd-Mon-yyyy hh24:mi')";

       int count=0;
       summary.put( "TOTAL_NUMBER",new Integer( count ) );
       result.addElement( summary );

       int num_rec=0;
       int rev_onhand=0;
       int act_onhand=0;

       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           num_rec++;

           Hashtable hD=new Hashtable();
           hD.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? "" : rs.getString( "RECEIPT_ID" ) );
           hD.put( "EXPECTED_QUANTITY",rs.getString( "EXPECTED_QUANTITY" ) == null ? "" : rs.getString( "EXPECTED_QUANTITY" ) );
           hD.put( "ACTUAL_QUANTITY",rs.getString( "ACTUAL_QUANTITY" ) == null ? "" : rs.getString( "ACTUAL_QUANTITY" ) );

           rev_onhand+=BothHelpObjs.makeZeroFromNull( rs.getString( "EXPECTED_QUANTITY" ) );
           act_onhand+=BothHelpObjs.makeZeroFromNull( rs.getString( "ACTUAL_QUANTITY" ) );

           result.addElement( hD );
         }
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         throw e;
       }
       finally
       {
          if (dbrs != null)  { dbrs.close(); }
       }

       Hashtable recsum=new Hashtable();
       recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
       recsum.put( "TOTAL_EXP",new Integer( rev_onhand ) );
       recsum.put( "TOTAL_ACT",new Integer( act_onhand ) );

       result.setElementAt( recsum,0 );
       return result;
     }

     public Vector getPickData( String Branch_id,String PrNumber,String lineItem ) throws Exception
     {
       DBResultSet dbrs=null;
       ResultSet rs=null;
       Vector result=new Vector();
       Hashtable summary=new Hashtable();
       String query="select * from new_mr_pick_view where hub =" + Branch_id + " and PR_NUMBER =" + PrNumber + " and LINE_ITEM = " + lineItem + " ";

       int count=0;
       summary.put( "TOTAL_NUMBER",new Integer( count ) );
       result.addElement( summary );
       int num_rec=0;

       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();

         while ( rs.next() )
         {
           num_rec++;

           Hashtable hD=new Hashtable();
           hD.put( "PR_NUMBER",rs.getString( "PR_NUMBER" ) == null ? "" : rs.getString( "PR_NUMBER" ) );
           hD.put( "LINE_ITEM",rs.getString( "LINE_ITEM" ) == null ? "" : rs.getString( "LINE_ITEM" ) );
           hD.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
           hD.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? "" : rs.getString( "MFG_LOT" ) );
           hD.put( "BIN",rs.getString( "BIN" ) == null ? "" : rs.getString( "BIN" ) );
           hD.put( "QUANTITY_AVAILABLE",rs.getString( "QUANTITY_AVAILABLE" ) == null ? "" : rs.getString( "QUANTITY_AVAILABLE" ) );
           hD.put( "COMPANY_ID",rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ) );
           hD.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? "" : rs.getString( "RECEIPT_ID" ) );
           hD.put( "TRANSFER_REQUEST_ID",rs.getString( "TRANSFER_REQUEST_ID" ) == null ? "" : rs.getString( "TRANSFER_REQUEST_ID" ) );
           hD.put( "CONSIGNED_FLAG",rs.getString( "CONSIGNED_FLAG" ) == null ? "" : rs.getString( "CONSIGNED_FLAG" ) );
           hD.put( "PICK_QTY",rs.getString( "PICK_QTY" ) == null ? "" : rs.getString( "PICK_QTY" ) );
           hD.put( "PICKABLE",rs.getString( "PICKABLE" ) == null ? "" : rs.getString( "PICKABLE" ) );
           hD.put( "OLD_PICK_QTY",rs.getString( "QUANTITY_PICKED" ) == null ? "" : rs.getString( "QUANTITY_PICKED" ) );
           hD.put( "PICK_DATE","" );
           hD.put( "QUANTITY","" );
           hD.put( "DOSTATUSUPDATE","" );

           result.addElement( hD );
         }
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         throw e;
       }
       finally
       {
         dbrs.close();
       }

       Hashtable recsum=new Hashtable();
       recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
       result.setElementAt( recsum,0 );

       return result;
     }

    public Vector getIssueHistory(String Branch_id,String ItemID,String SortBy) throws Exception
    {
      DBResultSet dbrs=null;
      ResultSet rs=null;
      Vector result=new Vector();
      Hashtable summary=new Hashtable();
      Date countdate=null;
      Connection connect1=null;
      CallableStatement cs=null;
      int count=0;
      summary.put( "TOTAL_NUMBER",new Integer( count ) );
      result.addElement( summary );
      int num_rec=0;

      try
      {
        countdate=this.getDateFromString( SortBy );
        connect1=db.getConnection();

        cs=connect1.prepareCall( "{? = call Pkg_query.fx_issue_to_reconcile(?,?,?)}" );
        //cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.registerOutParameter( 1,OracleTypes.VARCHAR );
        cs.setInt( 2,Integer.parseInt( Branch_id ) );
        cs.setInt( 3,Integer.parseInt( ItemID ) );
        cs.setTimestamp( 4,this.getDateFromString( SortBy ) );

        cs.execute();

        //rs = (ResultSet) cs.getObject(1);
        String issuequery=cs.getString( 1 );

        dbrs=db.doQuery( issuequery );
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          num_rec++;

          Hashtable hD=new Hashtable();
          hD.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
          hD.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID" ) == null ? "" : rs.getString( "RECEIPT_ID" ) );
          hD.put( "TRANSACTION_DATE",rs.getString( "TRANSACTION_DATE" ) == null ? "" : rs.getString( "TRANSACTION_DATE" ) );
          hD.put( "QUANTITY_CONFIRMED",rs.getString( "QUANTITY_CONFIRMED" ) == null ? "" : rs.getString( "QUANTITY_CONFIRMED" ) );
          hD.put( "QUANTITY_RECEIVED",rs.getString( "QUANTITY_RECEIVED" ) == null ? "" : rs.getString( "QUANTITY_RECEIVED" ) );
          hD.put( "QUANTITY_ISSUED",rs.getString( "QUANTITY_ISSUED" ) == null ? "" : rs.getString( "QUANTITY_ISSUED" ) );
          hD.put( "REVISED_ISSUED",rs.getString( "QUANTITY_ISSUED" ) == null ? "" : rs.getString( "QUANTITY_ISSUED" ) );
          hD.put( "ISSUE_ID",rs.getString( "ISSUE_ID" ) == null ? "" : rs.getString( "ISSUE_ID" ) );
          hD.put( "DATE_PICKED",rs.getString( "DATE_PICKED" ) == null ? "" : rs.getString( "DATE_PICKED" ) );
          hD.put( "CONFIRMED",rs.getString( "CONFIRMED" ) == null ? "" : rs.getString( "CONFIRMED" ) );
          hD.put( "QUANTITY",rs.getString( "QUANTITY" ) == null ? "" : rs.getString( "QUANTITY" ) );
          hD.put( "PR_NUMBER",rs.getString( "PR_NUMBER" ) == null ? "" : rs.getString( "PR_NUMBER" ) );
          hD.put( "LINE_ITEM",rs.getString( "LINE_ITEM" ) == null ? "" : rs.getString( "LINE_ITEM" ) );
          hD.put( "FACILITY",rs.getString( "FACILITY_NAME" ) == null ? "" : rs.getString( "FACILITY_NAME" ) );
          hD.put( "WORK_AREA",rs.getString( "APPLICATION_DESC" ) == null ? "" : rs.getString( "APPLICATION_DESC" ) );

          String last_name=rs.getString( "LAST_NAME" ) == null ? "" : rs.getString( "LAST_NAME" );
          String first_name=rs.getString( "FIRST_NAME" ) == null ? "" : rs.getString( "FIRST_NAME" );
          String full_name=last_name + ", " + first_name;

          hD.put( "ISSUER",full_name );
          hD.put( "DOSTATUSUPDATE","" );
          hD.put( "MR_QUANTITY_PICKED",rs.getString( "MR_TOTAL_QUANTITY_ISSUED" ) == null ? "" : rs.getString( "MR_TOTAL_QUANTITY_ISSUED" ) );

          result.addElement( hD );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        throw e;
      }
      finally
      {
        rs.close();
        cs.close();
      }

      Hashtable recsum=new Hashtable();
      recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
      result.setElementAt( recsum,0 );

      return result;
    }

    public boolean confirmInventory( String Branch_plant,String CountDateS )
    {
      boolean result=false;
      Connection connect1;
      CallableStatement cs1=null;

      try
      {
        connect1=db.getConnection();
        cs1=connect1.prepareCall( "{call p_confirm_from_inventory_count(?,?)}" );
        cs1.setString( 1,Branch_plant );
        cs1.setTimestamp( 2,this.getDateFromString( CountDateS ) );
        cs1.execute();
        result=true;
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        result=false;
      }
      finally
      {
        if ( cs1 != null )
        {
          try
          {
            cs1.close();
          }
          catch ( Exception se )
          {
            se.printStackTrace();
          }
        }
      }

      return result;
    }

    public boolean UpdateLine( Hashtable hD,String PersonnelId,String CountDateS )
    {
      boolean result=false;
      Connection connect1;
      CallableStatement cs=null;

      String act_onhand= ( hD.get( "ACTUAL_ON_HAND" ) == null ? "" : hD.get( "ACTUAL_ON_HAND" ).toString().trim() );
      String exp_qty= ( hD.get( "ON_HAND" ) == null ? "" : hD.get( "ON_HAND" ).toString().trim() );
      String Oreceipt_id= ( hD.get( "RECEIPT_ID" ) == null ? " " : hD.get( "RECEIPT_ID" ).toString() );
      //String bin_name1= ( hD.get( "BIN" ) == null ? " " : hD.get( "BIN" ).toString() );
      //System.out.println( "Doing Update in Reconciliation:  Receipt Id: " + Oreceipt_id + " Date: " + CountDateS + "  Personnel_id: " + PersonnelId + " Expected Qty: " + exp_qty + " Actual Qty : " + act_onhand );
      try
      {
        connect1=db.getConnection();
        cs=connect1.prepareCall( "{call p_update_inventory_count(?,?,?,?,?)}" );
        cs.setInt( 1,Integer.parseInt( Oreceipt_id ) );
        cs.setTimestamp( 2,this.getDateFromString( CountDateS ) );
        cs.setInt( 3,Integer.parseInt( PersonnelId ) );
        cs.setFloat( 4,Float.parseFloat( exp_qty ) );
        cs.setFloat( 5,Float.parseFloat( act_onhand ) );

        cs.execute();
        connect1.commit();

        result=true;
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        errorMsgs.addElement( "Error for Receipt ID :" + Oreceipt_id + "" );
        radian.web.emailHelpObj.sendnawazemail("Error calling Procedure p_update_inventory_count in HubInvenReconcilTables","Error occured while running p_update_inventory_count  \nError Msg:\n "+e.getMessage()+ " for Receipt Id: " + Oreceipt_id + "\n CountDateS " + CountDateS + "\n PersonnelId " + PersonnelId + "\n exp_qty " + exp_qty + "\n act_onhand " + act_onhand + "" );
        result=false;
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

    public Timestamp getTimeStampForPick( String dateString ) throws Exception
    {
      Date countdate1=null;
      Timestamp CountDateTimeStamp=null;
      SimpleDateFormat countDateT=new SimpleDateFormat( "MM/dd/yyyy" );

      try
      {
        countdate1=countDateT.parse( dateString );
        CountDateTimeStamp=new Timestamp( countdate1.getTime() );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        throw e;
      }

      return CountDateTimeStamp;
    }

    public boolean NewPick( Hashtable hD,String Branch_plant, String mrNum, String lineItem,String PersonnelId )
    {
        boolean result = false;
        Connection connect1;
        CallableStatement cstmt = null;

        String Oreceipt_id      = (hD.get("RECEIPT_ID")==null?" ":hD.get("RECEIPT_ID").toString());
        String actPickval       = (hD.get("QUANTITY")==null?" ":hD.get("QUANTITY").toString());
        String item_id          = (hD.get("ITEM_ID")==null?"&nbsp;":hD.get("ITEM_ID").toString());
        String companyId        = (hD.get("COMPANY_ID")==null?" ":hD.get("COMPANY_ID").toString());
        String transferid       = (hD.get("TRANSFER_REQUEST_ID")==null?" ":hD.get("TRANSFER_REQUEST_ID").toString());
        String consigned        = (hD.get("CONSIGNED_FLAG")==null?" ":hD.get("CONSIGNED_FLAG").toString());
        String pickDate         = (hD.get("PICK_DATE")==null?" ":hD.get("PICK_DATE").toString());

        int issueId = 0;
        String errmsg = "";

        try
          {
              connect1 = db.getConnection();
              String batchno = this.getBatchNo();
              Timestamp pickdate = this.getTimeStampForPick(pickDate);
              //System.out.println("Doing NewPick in Reconciliation:  Company Id: "+companyId+"\nMR "+mrNum+"\nlineitem "+lineItem+" \nbranch plant "+Branch_plant+"\n receipt id "+Oreceipt_id+"\n item id "+item_id+"\n pick Date "+pickDate+"\n act pick "+actPickval+"\n pickdate "+pickdate+"\nbatch no "+batchno+"\n transfer id "+transferid+"\n consigned "+consigned+"\npersonnel Id "+PersonnelId+"");

              try
              {
                cstmt=connect1.prepareCall( "{call p_issue_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );

                cstmt.setString( 1,companyId );
                cstmt.setString( 2,mrNum );
                cstmt.setString( 3,lineItem );
                cstmt.setString( 4,Branch_plant );
                cstmt.setString( 5,Oreceipt_id );
                cstmt.setString( 6,item_id );
                cstmt.setTimestamp( 7,pickdate );
                cstmt.setString( 8,actPickval );
                cstmt.setTimestamp( 9,pickdate );
                cstmt.setString( 10,batchno );
                cstmt.setString( 11,transferid );
                cstmt.setString( 12,consigned );
                cstmt.setString( 13,PersonnelId );
                cstmt.registerOutParameter( 14,Types.INTEGER );
                cstmt.registerOutParameter( 15,Types.VARCHAR );
                cstmt.executeQuery();
                issueId=cstmt.getInt( 14 );
                errmsg=cstmt.getString( 15 );

                connect1.commit();
                cstmt.close();
              }
              catch (Exception e)
              {
                  e.printStackTrace();
                  errorMsgs.addElement("Error for Receipt ID :"+Oreceipt_id+"");
                  radian.web.emailHelpObj.sendnawazemail("Error calling Procedure p_issue_insert in HubInvenReconcilTables","Error occured while running p_issue_insert \nError Msg:\n "+e.getMessage()+ "  Parameters \n\n Company Id: "+companyId+"\nMR "+mrNum+"\nlineitem "+lineItem+" \nbranch plant "+Branch_plant+"\n receipt id "+Oreceipt_id+"\n item id "+item_id+"\n pick Date "+pickDate+"\n act pick "+actPickval+"\n pickdate "+pickdate+"\nbatch no "+batchno+"\n transfer id "+transferid+"\n consigned "+consigned+"\npersonnel Id "+PersonnelId+"");
                  result = false;
              }

              try
              {
                if ( issueId < 0 )
                {
                  result=false;
                  errorMsgs.addElement( errmsg );
                }
                else
                {
                  cstmt=connect1.prepareCall( "{call p_receipt_allocate(?)}" );
                  cstmt.setInt( 1,Integer.parseInt( Oreceipt_id ) );
                  cstmt.execute();
                  connect1.commit();
                  result=true;
                }
                cstmt.close();
              }
              catch (Exception e)
              {
                  e.printStackTrace();
                  errorMsgs.addElement("Error for Receipt ID :"+Oreceipt_id+"");
                  radian.web.emailHelpObj.sendnawazemail("Error calling Procedure p_receipt_allocate in HubInvenReconcilTables","Error occured while running p_receipt_allocate for receipt Id "+Oreceipt_id+"\nError Msg:\n "+e.getMessage()+ "");
                  result = false;
              }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            errorMsgs.addElement("Error for Receipt ID :"+Oreceipt_id+"");
            result = false;
        }

        return result;
    }

    public String getBatchNo() throws Exception
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
        if (dbrs != null) {dbrs.close();}
      }

      return bNo;
    }

    public boolean UpdateIssue( Hashtable hD,String PersonnelId )
    {
        boolean result = false;
        Connection connect1;
        CallableStatement cs = null;

        String rev_qty          = (hD.get("REVISED_ISSUED")==null?"":hD.get("REVISED_ISSUED").toString().trim());
        String issue_id      = (hD.get("ISSUE_ID")==null?" ":hD.get("ISSUE_ID").toString());
        //String receipt_id      = (hD.get("RECEIPT_ID")==null?" ":hD.get("RECEIPT_ID").toString());
        //System.out.println("Doing Update Issue in Reconciliation:  issue Id: "+issue_id+" rev Qty: "+rev_qty+" PersonnelId : "+PersonnelId);

        try
          {
              connect1 = db.getConnection();
              cs = connect1.prepareCall("{call p_update_issue(?,?,?,?)}");
              cs.setInt(1,Integer.parseInt(issue_id));
              cs.setInt(2,Integer.parseInt(rev_qty));
              cs.setInt(3,Integer.parseInt(PersonnelId));
              cs.registerOutParameter(4,Types.VARCHAR);
              cs.execute();

              //String rr = cs.getString(4);
              connect1.commit();
              cs.close();

              result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            errorMsgs.addElement("Error for Receipt ID :"+issue_id+"");
            radian.web.emailHelpObj.sendnawazemail("Error calling Procedure p_update_issue in HubInvenReconcilTables","Error occured while running p_update_issue  for Issue Id: "+issue_id+" and Qty "+rev_qty+"\nError Msg:\n "+e.getMessage()+ "");
            result = false;
        }
        return result;
    }

    public String getHubWhenNone( String group,String personnelid )
    {
      DBResultSet dbrs=null;
      ResultSet rs=null;
      String temp2="";
      try
      {
        String query="select BRANCH_PLANT,PREFERRED_WAREHOUSE from facility f, user_group_member u where branch_plant is not null and f.FACILITY_ID = u.FACILITY_ID ";
        query+=" and u.PERSONNEL_ID = " + personnelid + " and U.user_group_id = '" + group + "' and rownum =1";

        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();
        while ( rs.next() )
        {
          temp2=rs.getString( "BRANCH_PLANT" ) == null ? "BLANK" : rs.getString( "BRANCH_PLANT" );
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
      return temp2;
    }

    public Timestamp getDateFromString( String CountDateString )  throws Exception
    {
      Date countdate1=null;
      Timestamp CountDateTimeStamp=null;
      SimpleDateFormat countDateT=new SimpleDateFormat( "dd MMM yyyy HH:mm" );

      try
      {
        countdate1=countDateT.parse( CountDateString );
        CountDateTimeStamp=new Timestamp( countdate1.getTime() );

      }
      catch ( Exception e )
      {
        e.printStackTrace();
        throw e;
      }

      return CountDateTimeStamp;
    }

    public String getSysDateForNewCount()
    {
      DBResultSet dbrs=null;
      ResultSet rs=null;
      String temp2="";
      try
      {
        String querys="select to_char(SYSDATE,'dd Mon yyyy hh24:mi') SYS_DATE from dual ";

        dbrs=db.doQuery( querys );
        rs=dbrs.getResultSet();
        while ( rs.next() )
        {
          temp2=rs.getString( "SYS_DATE" );
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
      return temp2;
    }

    public boolean StartNewCount( String HubNum,String AlmostSysdate )
    {
      boolean result=false;
      try
      {
        String query="insert into HUB_COUNT_DATE (HUB,COUNT_DATETIME) values ('" + HubNum + "',to_date('" + AlmostSysdate + "','dd Mon yyyy hh24:mi')) ";
        db.doUpdate( query );
        result=true;
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        result=false;
      }
      finally
      {
      }

      return result;
    }
}
