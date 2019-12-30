/**
 * Title:        Resoource Loader
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 * 08-06-03 Added capability to show all invoices and receipt or just the ones that need attention
 * 09-17-03 Making special provision for item_ids 430158 and 500743 so that -Ve quantities can be matched
 * 09-30-03 Calling a procedure to get the recipt matching data
 * 11-05-03 Escaping special characters in mfg_lot
 * 02-11-04 Fixing a bug to show Add Line when there is no voucher line added to the voucher and the po is recaled
 * 03-02-04 Coloring a line yellow if the unit price is -ve
 * 03-23-04 Cloroing a line yellow in receipt matching if PO unit price is -ve
 * 01-26-05 You can undo a Invocie or a mached receipt only is the invocie status is In Progress.
 */
package radian.web;

import java.io.PrintWriter;
//import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Vector;
import java.util.StringTokenizer;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import com.tcmis.common.util.ResourceLibrary;

public abstract class apHelpObj
{

  public static void builditemmatching( TcmISDBModel dbObj4,String radianPo,String itemID,String lineID,String SupplierConversion1,boolean updatestaus,boolean showall,PrintWriter apout,ResourceLibrary res )
      {
        //StringBuffer Msgtemp=new StringBuffer();
        DBResultSet dbrs=null;
        ResultSet rs=null;

        apout.println( "{\n" );
        int count=0;
        int openlinecnt = 0;
        //DecimalFormat lineTotal=new DecimalFormat( "####.0000##" );
        //DecimalFormat poTotal=new DecimalFormat( "####.00##" );

        try
        {

          String itemnotesquery="select CREDIT,VOUCHER_ID,VOUCHER_LINE,VOUCHER_STATUS,EXT_PRICE,SUPPLIER_INVOICE_ID,RADIAN_PO,ITEM_ID,QUANTITY_INVOICED,INVOICE_UNIT_PRICE,REMAINING_QTY,invoice_reference, ITEM_ID ";
          itemnotesquery+=" from po_item_invoice_view where radian_po = " + radianPo + " and item_id = " + itemID + " and VOUCHER_STATUS in ('In Progress','Approved') order by SUPPLIER_INVOICE_ID,INVOICE_REFERENCE,QUANTITY_INVOICED DESC";

          dbrs=dbObj4.doQuery( itemnotesquery );
          rs=dbrs.getResultSet();

          apout.println( "var itemmatching = opener.document.getElementById(\"itemmatching" + lineID + "\");\n" );
          apout.println( "var insidehtml =\"\";\n" );

          if ( SupplierConversion1.trim().length() > 0 )
          {
            apout.println( "insidehtml +=\"<BR>" + SupplierConversion1 + "<BR>\";\n" );
          }
          apout.println( "insidehtml +=\"<TABLE ID=\\\"itemmatchingtable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"650\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
          apout.println( "insidehtml +=\"<TR>\";\n" );
          apout.println( "insidehtml +=\"<TH WIDTH=\\\"20%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.invoice")+"</B></TH>\";\n" );
          apout.println( "insidehtml +=\"<TH WIDTH=\\\"20%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.reference")+"</B></TH>\";\n" ); //12-14-02
          apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.qty")+"</B></TH>\";\n" );
          apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.unitprice")+"</B></TH>\";\n" );
          apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.extprice")+"</B></TH>\";\n" );
          apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.qtyremtovoucher")+"</B></TH>\";\n" );
          apout.println( "insidehtml +=\"<TH WIDTH=\\\"1%\\\" HEIGHT=\\\"25\\\"></TH>\";\n" );
          apout.println( "insidehtml +=\"</TR>\";\n" );

          while ( rs.next() )
          {
            String voucher_id=rs.getString( "VOUCHER_ID" ) == null ? "" : rs.getString( "VOUCHER_ID" ).trim();
            String supplierinvocieid=rs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : rs.getString( "SUPPLIER_INVOICE_ID" ).trim();
            String voucher_line=rs.getString( "VOUCHER_LINE" ) == null ? "" : rs.getString( "VOUCHER_LINE" ).trim();
            //String radian_po = rs.getString("RADIAN_PO")==null?"":rs.getString("RADIAN_PO").trim();
            String item_id = rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID").trim();
            String quantity_invoiced=rs.getString( "QUANTITY_INVOICED" ) == null ? "" : rs.getString( "QUANTITY_INVOICED" ).trim();
            String invoice_unit_price=rs.getString( "INVOICE_UNIT_PRICE" ) == null ? "" : rs.getString( "INVOICE_UNIT_PRICE" ).trim();
            String qty_remaining_to_voucher=rs.getString( "REMAINING_QTY" ) == null ? "" : rs.getString( "REMAINING_QTY" ).trim();
            //String ext_price = rs.getString("EXT_PRICE")==null?"":rs.getString("EXT_PRICE").trim();
            String voucher_status=rs.getString( "VOUCHER_STATUS" ) == null ? "" : rs.getString( "VOUCHER_STATUS" ).trim();
            String invoiceReference=rs.getString( "invoice_reference" ) == null ? "" : rs.getString( "invoice_reference" ).trim(); ; //12-14-02
            String isitcredit=rs.getString( "CREDIT" ) == null ? "" : rs.getString( "CREDIT" ).trim(); ;

            count++;
            if ( !( "Approved".equalsIgnoreCase( voucher_status ) && "0".equalsIgnoreCase(qty_remaining_to_voucher) && !showall ) )
            {
              openlinecnt++;
              String Color=" ";
              String itemColor=" ";
              if ( openlinecnt % 2 == 0 )
              {
                Color="CLASS=\\\"Inblue";
                itemColor="INVISIBLEHEADBLUE";
              }
              else
              {
                Color="CLASS=\\\"Inwhite";
                itemColor="INVISIBLEHEADWHITE";
              }
              if ( "Y".equalsIgnoreCase( isitcredit ) )
              {
                Color="CLASS=\\\"yellow";
                itemColor="INVISIBLEHEADYELLOW";
              }

              BigDecimal total= new BigDecimal("0");
              BigDecimal unitPrice= new BigDecimal("0");
              if ( quantity_invoiced.length() > 0 && invoice_unit_price.length() > 0 )
              {
                BigDecimal qtyF=new BigDecimal( quantity_invoiced );
                unitPrice=new BigDecimal( invoice_unit_price );
                total= qtyF.multiply(unitPrice);
              }

			  if ( unitPrice.compareTo(new BigDecimal("0")) == -1 )
			  {
				Color="CLASS=\\\"yellow";
				itemColor="INVISIBLEHEADYELLOW";
			  }

              apout.println( "insidehtml +=\"<TR>\";\n" );
              apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"20%\\\">" + supplierinvocieid + "(" + voucher_line + ")</TD>\";\n" );
              apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor +"\\\" TYPE=\\\"text\\\" NAME=\\\"iteminvoicreference" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"iteminvoicreference" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + invoiceReference + "\\\" size=\\\"15\\\" readonly></TD>\";\n" );
              apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"text\\\" NAME=\\\"iteminvoicqty" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"iteminvoicqty" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + quantity_invoiced + "\\\" size=\\\"5\\\" readonly></TD>\";\n" );
              apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"text\\\" NAME=\\\"iteminvoicunitprice" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"iteminvoicunitprice" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + unitPrice.setScale(6,4) + "\\\" size=\\\"10\\\" readonly></TD>\";\n" );
              apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"text\\\" NAME=\\\"iteminvoicextprice" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"iteminvoicextprice" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + total.setScale(4,4) + "\\\" size=\\\"10\\\" readonly></TD>\";\n" );
              apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"text\\\" NAME=\\\"iteminvoicqtytovoc" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"iteminvoicqtytovoc" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + qty_remaining_to_voucher + "\\\" size=\\\"10\\\" readonly></TD>\";\n" );
              if ( !"In Progress".equalsIgnoreCase( voucher_status ) || !updatestaus )
              {
                apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"1%\\\">&nbsp;\";\n" );
              }
              else
              {
                apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"1%\\\"><INPUT TYPE=\\\"button\\\" CLASS=\\\"SUBMIT\\\" onmouseover=\\\"className='SUBMITHOVER'\\\" onMouseout=\\\"className='SUBMIT'\\\" name=\\\"undoaddlineHere\\\" id=\\\"undoaddlineHere\\\" OnClick=\\\"undoInvocieLine('" + openlinecnt + item_id + "','" + lineID + "')\\\" value=\\\""+res.getString("label.undo")+"\\\">\";\n" );
              }

              apout.println( "insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"supplierinvoiceis" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"supplierinvoiceis" + lineID + "" + openlinecnt + item_id + "\\\" VALUE=\\\"" + supplierinvocieid + "(" + voucher_line + ")\\\">\"; \n" );
              apout.println( "insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"itemvoucherId" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"itemvoucherId" + lineID + "" + openlinecnt + item_id + "\\\" VALUE=\\\"" + voucher_id + "\\\">\"; \n" );
              apout.println( "insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"itemvoucherline" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"itemvoucherline" + lineID + "" + openlinecnt + item_id + "\\\" VALUE=\\\"" + voucher_line + "\\\">\"; \n" );
              apout.println( "insidehtml +=\"</TD>\";\n" );
              apout.println( "insidehtml +=\"</TR>\";\n" );
            }
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

        if (openlinecnt == 0)
        {
          apout.println( "insidehtml +=\"<TR>\";\n" );
          apout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"7\\\">"+res.getString("label.noopeninvoicelines")+"</TD>\";\n" );
          apout.println( "insidehtml +=\"</TR>\";\n" );

          if (!showall && updatestaus)
          {
            apout.println( "addIdnvoiceLine = opener.document.getElementById(\"addIdnvoiceLine\");\n" );
            apout.println( "addIdnvoiceLine.disabled=true;\n" );
          }
		  else if (showall && updatestaus)
		  {
			apout.println( "addIdnvoiceLine = opener.document.getElementById(\"addIdnvoiceLine\");\n" );
			apout.println( "addIdnvoiceLine.disabled=false;\n" );
		  }
        }
        else if ( count == 0 )
        {
          apout.println( "insidehtml +=\"<TR>\";\n" );
          apout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"7\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
          apout.println( "insidehtml +=\"</TR>\";\n" );
        }
        else if (updatestaus)
         {
           apout.println( "addIdnvoiceLine = opener.document.getElementById(\"addIdnvoiceLine\");\n" );
           apout.println( "addIdnvoiceLine.disabled=false;\n" );
         }

        if ( showall )
        {
          apout.println( "showallInvLines = opener.document.getElementById(\"showallInvLines" + lineID + "\");\n" );
          apout.println( "showallInvLines.disabled=true;\n" );

          apout.println( "showunMatchedInv = opener.document.getElementById(\"showunMatchedInv" + lineID + "\");\n" );
          apout.println( "showunMatchedInv.disabled=false;\n" );
        }
        else
        {
          apout.println( "showunMatchedInv = opener.document.getElementById(\"showunMatchedInv" + lineID + "\");\n" );
          apout.println( "showunMatchedInv.disabled=true;\n" );

          apout.println( "showallInvLines = opener.document.getElementById(\"showallInvLines" + lineID + "\");\n" );
          apout.println( "showallInvLines.disabled=false;\n" );
        }

        apout.println( "insidehtml +=\"</TABLE>\";\n" );
        apout.println( "itemmatching.innerHTML =insidehtml;\n" );

        apout.println( "noofmatchingitem = opener.document.getElementById(\"noofmatchingitem" + lineID + "\");\n" );
        apout.println( "noofmatchingitem.value = \"" + openlinecnt + "\";\n" );
        apout.println( " }\n" );

        //return Msgtemp;
     }

  public static void buildreceiptmatching( TcmISDBModel dbObj3,String radianPo,String itemID,String lineID,boolean updatestatus,boolean showall,PrintWriter apout,ResourceLibrary res)
     {
       //StringBuffer Msgtemp=new StringBuffer();
       DBResultSet dbrs=null;
       ResultSet rs=null;

       apout.println( "{\n" );
       int count=0;
       int openlinecnt = 0;
       int creditinvcount = 0;

       //DecimalFormat lineTotal=new DecimalFormat( "####.0000##" );
       //DecimalFormat poTotal=new DecimalFormat( "####.00##" );
       //DecimalFormat unitpricemat=new DecimalFormat( "####.0000" );
       Vector dropDownData=new Vector();

       try
       {
         String itemnotesquery="select VOUCHER_ID,QUANTITY_INVOICED,VOUCHER_LINE,SUPPLIER_INVOICE_ID,INVOICE_UNIT_PRICE,REMAINING_QTY,credit  ";
         itemnotesquery+=" from po_item_invoice_view where radian_po = " + radianPo + " and item_id = " + itemID + " and VOUCHER_STATUS in ('In Progress') order by SUPPLIER_INVOICE_ID,VOUCHER_LINE desc";

         dbrs=dbObj3.doQuery( itemnotesquery );
         rs=dbrs.getResultSet();

         while ( rs.next() )
         {
           String voucherid=rs.getString( "VOUCHER_ID" ) == null ? "" : rs.getString( "VOUCHER_ID" ).trim();
           String supplierinvocieid=rs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : rs.getString( "SUPPLIER_INVOICE_ID" ).trim();
           String voucher_linedd=rs.getString( "VOUCHER_LINE" ) == null ? "" : rs.getString( "VOUCHER_LINE" ).trim();
           String invoiceitemprice=rs.getString( "INVOICE_UNIT_PRICE" ) == null ? "" : rs.getString( "INVOICE_UNIT_PRICE" ).trim();
           String qtyremaintovoucher=rs.getString( "REMAINING_QTY" ) == null ? "" : rs.getString( "REMAINING_QTY" ).trim();
           String qtyinvoiced=rs.getString( "QUANTITY_INVOICED" ) == null ? "" : rs.getString( "QUANTITY_INVOICED" ).trim();
           String credit=rs.getString( "credit" ) == null ? "" : rs.getString( "credit" ).trim();

           if ( "Y".equalsIgnoreCase( credit ) )
           {
             creditinvcount++;
           }

           Hashtable flowshD=new Hashtable();
           flowshD.put( "VOUCHER_ID",voucherid );
           flowshD.put( "VOUCHER_LINE",voucher_linedd );
           flowshD.put( "SUPPLIER_INVOICE_ID",supplierinvocieid );
           flowshD.put( "INVOICE_UNIT_PRICE",invoiceitemprice );
           flowshD.put( "REMAINING_QTY",qtyremaintovoucher );
           flowshD.put( "QUANTITY_INVOICED",qtyinvoiced );
           flowshD.put( "CREDIT",credit );

           dropDownData.addElement( flowshD );
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

	   Connection connect1=null;
	   try
	   {
		 CallableStatement cs=null;
		 String itemnotesquery = "";
		 connect1=dbObj3.getConnection();
		 connect1.setAutoCommit(false);

		 try
		 {
		   cs=connect1.prepareCall( "{call pr_PO_LINE_receipt_INVoice(?,?,?)}" );
		   cs.setBigDecimal( 1,new BigDecimal(radianPo) );
		   cs.setInt( 2,Integer.parseInt( itemID ) );
		   cs.registerOutParameter( 3,Types.VARCHAR );

		   cs.execute();
		   itemnotesquery = cs.getString( 3 );
		 }
		 catch ( Exception e )
		 {
		   //System.out.println( "\n\n--------- Erros in pr_PO_LINE_receipt_INVoice Procedure\n\n" );
		   e.printStackTrace();

			radian.web.emailHelpObj.senddatabaseHelpemails( "Error calling Procedure pr_PO_LINE_receipt_INVoice in AP Page",
														   "Error calling Procedure pr_PO_LINE_receipt_INVoice in AP Pages\n" + e.getMessage() +
														   "\nFor Input Parameters radian_po:   " + radianPo + "  item_id   "+itemID+" " );
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
			   se.printStackTrace();
			 }
		   }
		}

         /*String itemnotesquery="select RECEIVER_NAME,FROZEN,CREDIT,RADIAN_PO,SUPPLIER_INVOICE_ID,VOUCHER_EXT_PRICE,RECEIPT_EXT_PRICE,PO_UNIT_PRICE,ITEM_ID,PO_LINE,RECEIPT_ID,MFG_LOT,to_char(VOUCHER_TRANSACTION_DATE,'mm/dd/yyyy') VOUCHER_TRANSACTION_DATE1,to_char(DATE_OF_RECEIPT,'mm/dd/yyyy') DATE_OF_RECEIPT1,DATE_OF_RECEIPT,QUANTITY_RECEIVED,VOUCHER_ID, ";
         itemnotesquery+=" VOUCHER_LINE,QUANTITY_VOUCHERED,VOUCHER_STATUS,INVOICE_UNIT_PRICE,VOUCHER_EXT_PRICE,VOUCHER_TRANSACTION_DATE,to_char(sysdate,'mm/dd/yyyy') SYSTEMDATE ";
         itemnotesquery+=" from po_line_receipt_invoice_view where radian_po = " + radianPo + " and item_id = " + itemID + " order by SUPPLIER_INVOICE_ID,VOUCHER_ID,QUANTITY_VOUCHERED DESC ";
         */

         dbrs=dbObj3.doQuery( itemnotesquery );
         rs=dbrs.getResultSet();

         apout.println( "var receiptmatching = opener.document.getElementById(\"receiptmatching" + lineID + "\");\n" );
         apout.println( "var insidehtml =\"<TABLE ID=\\\"receiptmatchingtable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
         apout.println( "insidehtml +=\"<TR>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.podaskline")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.receipt")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.receiver")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("receivedreceipts.label.dor")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.reference")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.qty")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.price")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.extprice")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.invoice")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.date")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.qty")+"</B></TH>\";\n" );
         //apout.println("insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>Unit Price</B></TH>\";\n");
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.extprice")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.creditmemo")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.qty")+"</B></TH>\";\n" );
         apout.println( "insidehtml +=\"<TH WIDTH=\\\"1%\\\" HEIGHT=\\\"25\\\"></TH>\";\n" );
         apout.println( "insidehtml +=\"</TR>\";\n" );

         while ( rs.next() )
         {
           String radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
           String item_id = rs.getString("ITEM_ID")==null?"":rs.getString("ITEM_ID").trim();
           String po_line=rs.getString( "PO_LINE" ) == null ? "" : rs.getString( "PO_LINE" ).trim();
           String receipt_id=rs.getString( "RECEIPT_ID" ) == null ? "" : rs.getString( "RECEIPT_ID" ).trim();
           String mfg_lot=rs.getString( "MFG_LOT" ) == null ? "" : rs.getString( "MFG_LOT" ).trim();
		   mfg_lot = HelpObjs.addescapesForJavascript(mfg_lot);
           //String bin = rs.getString("BIN")==null?"":rs.getString("BIN").trim();
           String date_of_receipt=rs.getString( "DATE_OF_RECEIPT1" ) == null ? "" : rs.getString( "DATE_OF_RECEIPT1" ).trim();
           String quantity_received=rs.getString( "QUANTITY_RECEIVED" ) == null ? "" : rs.getString( "QUANTITY_RECEIVED" ).trim();
           //String lot_status = rs.getString("LOT_STATUS")==null?"":rs.getString("LOT_STATUS").trim();
           String voucher_id=rs.getString( "VOUCHER_ID" ) == null ? "" : rs.getString( "VOUCHER_ID" ).trim();
           String voucher_line_main=rs.getString( "VOUCHER_LINE" ) == null ? "" : rs.getString( "VOUCHER_LINE" ).trim();
           //String quantity_invoiced = rs.getString("QUANTITY_VOUCHERED")==null?"":rs.getString("QUANTITY_VOUCHERED").trim();
           String invoice_unit_price=rs.getString( "INVOICE_UNIT_PRICE" ) == null ? "" : rs.getString( "INVOICE_UNIT_PRICE" ).trim();
           String invoice_transaction_date=rs.getString( "VOUCHER_TRANSACTION_DATE1" ) == null ? "" : rs.getString( "VOUCHER_TRANSACTION_DATE1" ).trim();
           String quantity_vouchered=rs.getString( "QUANTITY_VOUCHERED" ) == null ? "" : rs.getString( "QUANTITY_VOUCHERED" ).trim();
           String unit_price_on_radian_po=rs.getString( "PO_UNIT_PRICE" ) == null ? "" : rs.getString( "PO_UNIT_PRICE" ).trim();
           //String receiptextprice = rs.getString("RECEIPT_EXT_PRICE")==null?"":rs.getString("RECEIPT_EXT_PRICE").trim();
           //String voucherextprice = rs.getString("VOUCHER_EXT_PRICE")==null?"":rs.getString("VOUCHER_EXT_PRICE").trim();
           String supplierinvocieid=rs.getString( "SUPPLIER_INVOICE_ID" ) == null ? "" : rs.getString( "SUPPLIER_INVOICE_ID" ).trim();
           String sysDate=rs.getString( "SYSTEMDATE" ) == null ? "" : rs.getString( "SYSTEMDATE" ).trim();
           String voucher_status=rs.getString( "VOUCHER_STATUS" ) == null ? "" : rs.getString( "VOUCHER_STATUS" ).trim();
           String isitcredit=rs.getString( "CREDIT" ) == null ? "" : rs.getString( "CREDIT" ).trim(); ;
           String isfrozen=rs.getString( "FROZEN" ) == null ? "" : rs.getString( "FROZEN" ).trim(); ;
           String receivername=rs.getString( "RECEIVER_NAME" ) == null ? "" : rs.getString( "RECEIVER_NAME" ).trim(); ;

           count++;
           if ( !(voucher_id.trim().length() > 0 && (creditinvcount == 0) && "Approved".equalsIgnoreCase( voucher_status ) && !showall ))
           {
             openlinecnt++;
             String Color="";
             String itemColor=" ";
             if ( openlinecnt % 2 == 0 )
             {
               Color="CLASS=\\\"Inblue";
               itemColor="INVISIBLEHEADBLUE";
             }
             else
             {
               Color="CLASS=\\\"Inwhite";
               itemColor="INVISIBLEHEADWHITE";
             }
             if ( "Y".equalsIgnoreCase( isitcredit ) )
             {
               Color="CLASS=\\\"yellow";
               itemColor="INVISIBLEHEADYELLOW";
             }

             BigDecimal total= new BigDecimal("0");
             BigDecimal unitPrice= new BigDecimal("0");
             if ( quantity_vouchered.length() > 0 && invoice_unit_price.length() > 0 )
             {
               BigDecimal qtyF=new BigDecimal( quantity_vouchered );
               unitPrice=new BigDecimal( invoice_unit_price );
               total= qtyF.multiply(unitPrice);
             }

             BigDecimal recipttotal= new BigDecimal("0");
             BigDecimal receiptunitPrice= new BigDecimal("0");
             if ( quantity_received.length() > 0 && unit_price_on_radian_po.length() > 0 )
             {
               BigDecimal qtyF=new BigDecimal( quantity_received );
               receiptunitPrice=new BigDecimal( unit_price_on_radian_po );
               recipttotal= qtyF.multiply(receiptunitPrice);
             }

			 if ( receiptunitPrice.compareTo(new BigDecimal("0")) == -1)
			 {
			  Color="CLASS=\\\"yellow";
			  itemColor="INVISIBLEHEADYELLOW";
			 }

             apout.println( "insidehtml +=\"<TR>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"10%\\\">" + radian_po + "-" + po_line + "</TD>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + receipt_id + "</TD>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + receivername + "</TD>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + date_of_receipt + "</TD>\";\n" );
             //apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + mfg_lot + "</TD>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">\";\n");
             StringTokenizer st = new StringTokenizer(mfg_lot, "\\");
             if(st.countTokens() > 1)
              {
                     while(st.hasMoreTokens())
                     {
                        String tok = st.nextToken();
                        apout.println("insidehtml +=\""+tok+"\";\n");
                     }
              }
              else
             {
              apout.println("insidehtml +=\""+mfg_lot+"\";\n");   
             }
             apout.println( "insidehtml +=\"</TD>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"text\\\" NAME=\\\"receiptqty" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"receiptqty" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + quantity_received + "\\\" size=\\\"5\\\" readonly></TD>\";\n" );
             //apout.println("insidehtml +=\"<TD "+Color+"\\\" WIDTH=\\\"5%\\\">"+quantity_received+"</TD>\";\n");
             apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"text\\\" NAME=\\\"receiptunitprice" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"receiptunitprice" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + receiptunitPrice.setScale(6,4) + "\\\" size=\\\"10\\\" readonly></TD>\";\n" );
             //apout.println("insidehtml +=\"<TD "+Color+"\\\" WIDTH=\\\"5%\\\">"+unit_price_on_radian_po+"</TD>\";\n");
             //apout.println("insidehtml +=\"<TD "+Color+"\\\" WIDTH=\\\"5%\\\">"+receiptextprice+"</TD>\";\n");
             apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"text\\\" NAME=\\\"receiptextprice" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"receiptextprice" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + recipttotal.setScale(4,4) + "\\\" size=\\\"10\\\" readonly></TD>\";\n" );

             if ( voucher_id.trim().length() > 0 || !updatestatus )
             {
               //System.out.println("------- I can't update data: "+voucher_id+"-"+this.getupdateStatus());
               apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"10%\\\">" + supplierinvocieid + "(" + voucher_line_main + ")<INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"hidden\\\" NAME=\\\"fixedinvoiceline" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"fixedinvoiceline" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + supplierinvocieid + "(" + voucher_line_main + ")\\\" size=\\\"16\\\" readonly></TD>\";\n" );
               apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + invoice_transaction_date + "</TD>\";\n" );
               apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"text\\\" NAME=\\\"receiptinvoicqtyvoc" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"receiptinvoicqtyvoc" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + quantity_vouchered + "\\\" size=\\\"5\\\" readonly></TD>\";\n" );
             }
             else
             {
               apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"10%\\\">\";\n" );
               apout.println( "insidehtml +=\"<SELECT CLASS=\\\"HEADER\\\" NAME=\\\"invoicechoosen" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"invoicechoosen" + lineID + "" + openlinecnt + item_id + "\\\" onChange=\\\"setvoucherline('" + openlinecnt + item_id + "','" + lineID + "','" + isitcredit + "')\\\" size=\\\"1\\\">\";\n" );
               apout.println( "insidehtml +=\"<OPTION VALUE=\\\"\\\">"+res.getString("label.pleaseselect")+"</OPTION>\";\n" );

               for ( int i=0; i < dropDownData.size(); i++ )
               {
                 Hashtable hD=new Hashtable();
                 hD= ( Hashtable ) dropDownData.elementAt( i );

                 String voucherunitprice=hD.get( "INVOICE_UNIT_PRICE" ).toString().trim();
                 String voucheridh=hD.get( "VOUCHER_ID" ).toString().trim();
                 String voucherlineh=hD.get( "VOUCHER_LINE" ).toString().trim();
                 String supplierinvocieh=hD.get( "SUPPLIER_INVOICE_ID" ).toString().trim();
                 String qtyremaintovoucher=hD.get( "REMAINING_QTY" ).toString().trim();
                 String qtyinvoiced=hD.get( "QUANTITY_INVOICED" ).toString().trim();
                 String tmpCredit= ( String ) hD.get( "CREDIT" );

                 BigDecimal qtyremainingtoinvo=new BigDecimal( qtyremaintovoucher );
                 BigDecimal qtyInvoiced=new BigDecimal( qtyinvoiced );
                 BigDecimal vocunitprice=new BigDecimal( voucherunitprice );

			     if ( item_id.equalsIgnoreCase("430158") || item_id.equalsIgnoreCase("500743") )
					 {
					   qtyremainingtoinvo = qtyremainingtoinvo.multiply(new BigDecimal("-1"));
					   qtyInvoiced = qtyInvoiced.multiply(new BigDecimal("-1"));
			     }
                 
                 if ( ( receiptunitPrice.setScale(6,4).equals(vocunitprice.setScale(6,4)) ) &&
              ( (qtyremainingtoinvo.compareTo(new BigDecimal("0")) == 1)  ||
							( (qtyremainingtoinvo.compareTo(new BigDecimal("0")) == -1) && "Y".equalsIgnoreCase( tmpCredit ) ) ) &&
              ( (qtyInvoiced.compareTo(new BigDecimal("0")) == 1) || ( (qtyInvoiced.compareTo(new BigDecimal("0")) == -1) && "Y".equalsIgnoreCase( tmpCredit ) ) ) )
						 {
              if ( "Y".equalsIgnoreCase( isitcredit ) )
              {
                if ( "Y".equalsIgnoreCase( tmpCredit ) )
                {
								 apout.println( "insidehtml +=\"<OPTION VALUE=\\\"" + voucheridh + "-" + voucherlineh + "\\\">" + supplierinvocieh + "(" + voucherlineh + ")</OPTION>\";\n" );
								}
							 }
							 else
               {
                if ( !"Y".equalsIgnoreCase( tmpCredit ) )
                {
                 apout.println( "insidehtml +=\"<OPTION VALUE=\\\"" + voucheridh + "-" + voucherlineh + "\\\">" + supplierinvocieh + "(" + voucherlineh + ")</OPTION>\";\n" );
                }
               }
              }
             }
             //apout.println(invoicedropdown);
             apout.println( "insidehtml +=\"</SELECT>\";\n" );
             apout.println( "insidehtml +=\"</TD>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + sysDate + "</TD>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"HEADER\\\" TYPE=\\\"text\\\" NAME=\\\"receiptinvoicqtyvoc" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"receiptinvoicqtyvoc" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + quantity_vouchered + "\\\" onChange=\\\"checkreceiptextprice('" + openlinecnt + item_id + "','" + lineID + "','" + isitcredit + "')\\\" size=\\\"5\\\"></TD>\";\n" );
           }

           //apout.println("insidehtml +=\"<TD "+Color+"\\\" WIDTH=\\\"5%\\\">"+quantity_vouchered+"</TD>\";\n");
           //apout.println("insidehtml +=\"<TD "+Color+"\\\" WIDTH=\\\"5%\\\">"+invoice_unit_price+"</TD>\";\n");
           //apout.println("insidehtml +=\"<TD "+Color+"\\\" WIDTH=\\\"5%\\\">"+voucherextprice+"</TD>\";\n");
           apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"" + itemColor + "\\\" TYPE=\\\"text\\\" NAME=\\\"receiptinvoicqtyvocextprice" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"receiptinvoicqtyvocextprice" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"" + total.setScale(4,4) + "\\\" size=\\\"10\\\" readonly></TD>\";\n" );

           if ( voucher_id.trim().length() > 0 && updatestatus )
           {
             if ( !"Y".equalsIgnoreCase( isfrozen ) )
             {
               apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">\";\n" );
               apout.println( "insidehtml +=\"<SELECT CLASS=\\\"HEADER\\\" NAME=\\\"invoicreditcechoosen" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"invoicreditcechoosen" + lineID + "" + openlinecnt + item_id + "\\\" onChange=\\\"setvouchercreditline('" + openlinecnt + item_id + "','" + lineID + "')\\\" size=\\\"1\\\">\";\n" );
               apout.println( "insidehtml +=\"<OPTION VALUE=\\\"\\\">"+res.getString("label.pleaseselect")+"</OPTION>\";\n" );

               for ( int i=0; i < dropDownData.size(); i++ )
               {
                 Hashtable hD=new Hashtable();
                 hD= ( Hashtable ) dropDownData.elementAt( i );

                 String voucherunitprice=hD.get( "INVOICE_UNIT_PRICE" ).toString().trim();
                 String voucheridh=hD.get( "VOUCHER_ID" ).toString().trim();
                 String voucherlineh=hD.get( "VOUCHER_LINE" ).toString().trim();
                 String supplierinvocieh=hD.get( "SUPPLIER_INVOICE_ID" ).toString().trim();
                 String qtyremaintovoucher=hD.get( "REMAINING_QTY" ).toString().trim();
                 String tmpCredit= ( String ) hD.get( "CREDIT" );

                 BigDecimal qtyremainingtoinvo=new BigDecimal( qtyremaintovoucher );
                 BigDecimal vocunitprice=new BigDecimal( voucherunitprice );

                 //before 12-14-02 if (voucherunitprice.equalsIgnoreCase(unit_price_on_radian_po) && qtyremainingtoinvo < 0)
                 // before 10-6-11 voucherunitprice.equalsIgnoreCase( unit_price_on_radian_po )
                 if (  vocunitprice.setScale(6,4).equals(receiptunitPrice.setScale(6,4)) && (qtyremainingtoinvo.compareTo(new BigDecimal("0")) == -1) && !"Y".equalsIgnoreCase( tmpCredit ) )
                 {
                   apout.println( "insidehtml +=\"<OPTION VALUE=\\\"" + voucheridh + "-" + voucherlineh + "\\\">" + supplierinvocieh + "(" + voucherlineh + ")</OPTION>\";\n" );
                 }
               }

               //apout.println(invoicedropdown);
               apout.println( "insidehtml +=\"</SELECT>\";\n" );
               apout.println( "insidehtml +=\"</TD>\";\n" );
               apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><INPUT CLASS=\\\"HEADER\\\" TYPE=\\\"text\\\" NAME=\\\"receiptcreditqty" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"receiptcreditqty" + lineID + "" + openlinecnt + item_id + "\\\" value=\\\"\\\" onChange=\\\"checkcreditmemoqty('" + openlinecnt + item_id + "','" + lineID + "')\\\" size=\\\"5\\\"></TD>\";\n" );
             }
             else
             {
               apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"></TD>\";\n" );
               apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"></TD>\";\n" );
             }

             if ( !"In Progress".equalsIgnoreCase( voucher_status ) )
             {
               apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"1%\\\"></TD>\";\n" );
             }
             else
             {
               apout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"1%\\\"><INPUT TYPE=\\\"button\\\" CLASS=\\\"SUBMIT\\\" onmouseover=\\\"className='SUBMITHOVER'\\\" onMouseout=\\\"className='SUBMIT'\\\" name=\\\"undoaddlineHere\\\" id=\\\"undoaddlineHere\\\" OnClick=\\\"undoReceiptInvocieLine('" + openlinecnt + item_id + "','" + lineID + "')\\\" value=\\\""+res.getString("label.undo")+"\\\">\";\n" );
             }
           }
           else
           {
             apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"></TD>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"></TD>\";\n" );
             apout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"1%\\\"></TD>\";\n" );
           }

           apout.println("insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"receiptsupplierinvoiceis"+lineID+""+openlinecnt + item_id+"\\\" ID=\\\"receiptsupplierinvoiceis"+lineID+""+openlinecnt + item_id+"\\\" VALUE=\\\""+supplierinvocieid+"("+voucher_line_main+")\\\">\"; \n");
           apout.println("insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"receiptsupplierinvoiceuitprice"+lineID+""+openlinecnt + item_id+"\\\" ID=\\\"receiptsupplierinvoiceuitprice"+lineID+""+openlinecnt + item_id+"\\\" VALUE=\\\""+invoice_unit_price+"\\\">\"; \n");
           apout.println("insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"receiptvoucherId"+lineID+""+openlinecnt + item_id+"\\\" ID=\\\"receiptvoucherId"+lineID+""+openlinecnt + item_id+"\\\" VALUE=\\\""+voucher_id+"\\\">\"; \n");
           apout.println("insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"receiptvoucherline"+lineID+""+openlinecnt + item_id+"\\\" ID=\\\"receiptvoucherline"+lineID+""+openlinecnt + item_id+"\\\" VALUE=\\\""+voucher_line_main+"\\\">\"; \n");
           apout.println("insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"vocreceiptid"+lineID+""+openlinecnt + item_id+"\\\" ID=\\\"vocreceiptid"+lineID+""+openlinecnt + item_id+"\\\" VALUE=\\\""+receipt_id+"\\\">\"; \n");
           apout.println("insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"isitcreditline"+lineID+""+openlinecnt + item_id+"\\\" ID=\\\"isitcreditline"+lineID+""+openlinecnt + item_id+"\\\" VALUE=\\\""+isitcredit+"\\\">\"; \n");
           apout.println("insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"mainlnreceiptvoucherId"+lineID+""+openlinecnt + item_id+"\\\" ID=\\\"mainlnreceiptvoucherId"+lineID+""+openlinecnt + item_id+"\\\" VALUE=\\\""+voucher_id+"\\\">\"; \n");
           apout.println("insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"mainlnreceiptvoucherline"+lineID+""+openlinecnt + item_id+"\\\" ID=\\\"mainlnreceiptvoucherline"+lineID+""+openlinecnt + item_id+"\\\" VALUE=\\\""+voucher_line_main+"\\\">\"; \n");
		   apout.println("insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"lineitemid"+lineID+""+openlinecnt + item_id+"\\\" ID=\\\"lineitemid"+lineID+""+openlinecnt + item_id+"\\\" VALUE=\\\""+item_id+"\\\">\"; \n");

           if ( ! ( voucher_id.trim().length() > 0 ) )
           {
             apout.println( "insidehtml +=\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"updatereceipt" + lineID + "" + openlinecnt + item_id + "\\\" ID=\\\"updatereceipt" + lineID + "" + openlinecnt + item_id + "\\\" VALUE=\\\"Yes\\\">\"; \n" );
           }

           apout.println( "insidehtml +=\"</TD>\";\n" );
           apout.println( "insidehtml +=\"</TR>\";\n" );
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
	   if ( connect1 != null ){try { connect1.setAutoCommit(true); } catch ( Exception se ) { se.printStackTrace(); } }
     }

     if ( openlinecnt == 0 )
     {
       apout.println( "insidehtml +=\"<TR>\";\n" );
       apout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"16\\\">"+res.getString("label.noopenreceiptsfound")+"</TD>\";\n" );
       apout.println( "insidehtml +=\"</TR>\";\n" );
     }
     else if ( count == 0 )
     {
       apout.println( "insidehtml +=\"<TR>\";\n" );
       apout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"16\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
       apout.println( "insidehtml +=\"</TR>\";\n" );
     }

     if ( showall )
     {
       apout.println( "shwallreceiptMatching = opener.document.getElementById(\"shwallreceiptMatching" + lineID + "\");\n" );
       apout.println( "shwallreceiptMatching.disabled=true;\n" );

       apout.println( "showunMatchedReceipts = opener.document.getElementById(\"showunMatchedReceipts" + lineID + "\");\n" );
       apout.println( "showunMatchedReceipts.disabled=false;\n" );
     }
     else
     {
       apout.println( "showunMatchedReceipts = opener.document.getElementById(\"showunMatchedReceipts" + lineID + "\");\n" );
       apout.println( "showunMatchedReceipts.disabled=true;\n" );

       apout.println( "shwallreceiptMatching = opener.document.getElementById(\"shwallreceiptMatching" + lineID + "\");\n" );
       apout.println( "shwallreceiptMatching.disabled=false;\n" );
     }

     apout.println( "insidehtml +=\"</TABLE>\";\n" );
     apout.println( "receiptmatching.innerHTML =insidehtml;\n" );
     apout.println( "noofreceiptmatching = opener.document.getElementById(\"noofreceiptmatching" + lineID + "\");\n" );
     apout.println( "noofreceiptmatching.value = \"" + openlinecnt  + "\";\n" );
     apout.println( " }\n" );

     //return Msgtemp;
   }
 }