package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;
import java.math.BigDecimal;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-11-03 Code Cleanup and showing the vendor ship date in the line history
 * 07-05-05 Showing Currency ID
 */

public class polineHistory
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
	  private PrintWriter out = null;
    private boolean intcmIsApplication = false;
    private ResourceLibrary res = null; // res means resource.
    public polineHistory(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResult( HttpServletRequest request,HttpServletResponse response )
       throws ServletException,IOException
    {

      out=response.getWriter();
      response.setContentType( "text/html" );
      HttpSession session=request.getSession();
      res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));

      PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
      Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
      if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
      {
       intcmIsApplication = true;
      }

      String ammendment=request.getParameter( "ammendment" );
      if ( ammendment == null )
        ammendment="";
      ammendment=ammendment.trim();

      String poline=request.getParameter( "poline" );
      if ( poline == null )
        poline="";
      poline=poline.trim();

      String radianPO=request.getParameter( "radianPO" );
      if ( radianPO == null )
        radianPO="";
      radianPO=radianPO.trim();

      String subUserAction=request.getParameter( "subUserAction" );
      if ( subUserAction == null )
        subUserAction="";
      subUserAction=subUserAction.trim();

      if ( "po".equalsIgnoreCase( subUserAction ) )
      {
        buildLikepoSupplier( poline,radianPO,ammendment );
      }
      else if ( "bpo".equalsIgnoreCase( subUserAction ) )
      {
       buildLikebpoSupplier( poline,radianPO,ammendment );
      }

      out.close();
    }

    public void buildLikepoSupplier( String poline1,String radianpo,String ammeandment )
    {
      //StringBuffer Msgl=new StringBuffer();
      DBResultSet dbrs=null;
      ResultSet rs=null;

      nematid_Servlet=bundle.getString( "PURCHASE_ORDER_CARRIER" );

      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Line History","",intcmIsApplication ) );
      printJavaScripts();
      out.println( "// -->\n </SCRIPT>\n" );
      out.println( "<BODY onload=\"javascript:window.resizeTo(700,420)\">\n" );
      out.println( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
      out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
      out.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>PO:</B></FONT></TD><TD WIDTH=\"70%\">" + radianpo + "</TD>\n" );
      out.println( "</TR>\n" );

      //int rowNumber=Integer.parseInt( poline1 ) * 1000;
      BigDecimal rowNumber= new BigDecimal(poline1);
      rowNumber = rowNumber.multiply(new BigDecimal("1000"));

      out.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>PO Line:</B></FONT></TD><TD WIDTH=\"70%\">" + rowNumber + "</TD>\n" );
      out.println( "</TR>\n" );
      out.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
      out.println( "<TR>\n" );
      out.println( "<TH WIDTH=\"12%\"><B>Ammendment</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"8%\"><B>Item Id</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"8%\"><B>Quantity</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"10%\"><B>Unit Price</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"10%\"><B>Need Date</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"10%\"><B>Promised Ship Date</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"10%\"><B>Projected Delivery Date</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"10%\"><B>Date Confirmed</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"15%\"><B>Notes</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"15%\"><B>Ammendmeant Remarks</B></FONT></TH>\n" );
      out.println( "</TR></TABLE>\n" );

      //open scrolling table
      out.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.println( "<TBODY>\n" );
      out.println( "<TR>\n" );
      out.println( "<TD VALIGN=\"TOP\">\n" );
      out.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );

      //Write code to insert rows here
      out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

      int totalrecords=0;
      int count=0;

      String query="";
      query+="select CURRENCY_ID,RADIAN_PO,PO_LINE,PO_LINE_NOTE,REMARKS,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(NEED_DATE,'mm/dd/yyyy') NEED_DATE, ";
      query+=" to_char(PROMISED_DATE,'mm/dd/yyyy') PROMISED_DATE,to_char(VENDOR_SHIP_DATE,'mm/dd/yyyy') VENDOR_SHIP_DATE,to_char(DATE_CONFIRMED,'mm/dd/yyyy') DATE_CONFIRMED, ";
      query+=" SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE,GENERIC_COC,GENERIC_COA from po_line_history_view where RADIAN_PO = " + radianpo + " and ";
      query+=" PO_LINE = " + rowNumber + " and AMENDMENT < " + ammeandment + " order by AMENDMENT desc";

      try
      {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          totalrecords+=1;
          count+=1;

          //String radian_po = rs.getString("RADIAN_PO")==null?"":rs.getString("RADIAN_PO").trim();
          //String po_line = rs.getString("PO_LINE")==null?"":rs.getString("PO_LINE").trim();
          String amendment=rs.getString( "AMENDMENT" ) == null ? "" : rs.getString( "AMENDMENT" ).trim();
          String item_id=rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ).trim();
          String quantity=rs.getString( "QUANTITY" ) == null ? "" : rs.getString( "QUANTITY" ).trim();
          String unit_price=rs.getString( "UNIT_PRICE" ) == null ? "" : rs.getString( "UNIT_PRICE" ).trim();
          String need_date=rs.getString( "NEED_DATE" ) == null ? "" : rs.getString( "NEED_DATE" ).trim();
          String promised_date=rs.getString( "PROMISED_DATE" ) == null ? "" : rs.getString( "PROMISED_DATE" ).trim();
          String vendor_ship_date=rs.getString( "VENDOR_SHIP_DATE" ) == null ? "" : rs.getString( "VENDOR_SHIP_DATE" ).trim();
          //String allowed_price_variance = rs.getString("ALLOWED_PRICE_VARIANCE")==null?"":rs.getString("ALLOWED_PRICE_VARIANCE").trim();
          //String mfg_part_no = rs.getString("MFG_PART_NO")==null?"":rs.getString("MFG_PART_NO").trim();
          //String supplier_part_no = rs.getString("SUPPLIER_PART_NO")==null?"":rs.getString("SUPPLIER_PART_NO").trim();
          //String customer_po = rs.getString("CUSTOMER_PO")==null?"":rs.getString("CUSTOMER_PO").trim();
          //String dpas_rating = rs.getString("DPAS_RATING")==null?"":rs.getString("DPAS_RATING").trim();
          //String quality_flow_downs = rs.getString("QUALITY_FLOW_DOWNS")==null?"":rs.getString("QUALITY_FLOW_DOWNS").trim();
          //String packaging_flow_downs = rs.getString("PACKAGING_FLOW_DOWNS")==null?"":rs.getString("PACKAGING_FLOW_DOWNS").trim();
          String po_line_note=rs.getString( "PO_LINE_NOTE" ) == null ? "" : rs.getString( "PO_LINE_NOTE" ).trim();
          //String transaction_date = rs.getString("TRANSACTION_DATE")==null?"":rs.getString("TRANSACTION_DATE").trim();
          String date_confirmed=rs.getString( "DATE_CONFIRMED" ) == null ? "" : rs.getString( "DATE_CONFIRMED" ).trim();
          String remarks=rs.getString( "REMARKS" ) == null ? "" : rs.getString( "REMARKS" ).trim();
          //String supplier_qty=rs.getString( "SUPPLIER_QTY" ) == null ? "" : rs.getString( "SUPPLIER_QTY" ).trim();
          //String supplier_pkg=rs.getString( "SUPPLIER_PKG" ) == null ? "" : rs.getString( "SUPPLIER_PKG" ).trim();
          //String supplier_unit_price=rs.getString( "SUPPLIER_UNIT_PRICE" ) == null ? "" : rs.getString( "SUPPLIER_UNIT_PRICE" ).trim();
          //String generic_coc=rs.getString( "GENERIC_COC" ) == null ? "" : rs.getString( "GENERIC_COC" ).trim();
          //String generic_coa=rs.getString( "GENERIC_COA" ) == null ? "" : rs.getString( "GENERIC_COA" ).trim();
		  String currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();

          String Color=" ";
          if ( count % 2 == 0 )
          {
            Color="CLASS=\"Inblue";
          }
          else
          {
            Color="CLASS=\"Inwhite";
          }

          out.println( "<TR>\n" );
          out.println( "<TD " + Color + "l\" WIDTH=\"12%\">" );
          out.println( amendment );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "\" WIDTH=\"8%\">" );
          out.println( item_id );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "r\" WIDTH=\"8%\">" );
          out.println( quantity );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "r\" WIDTH=\"10%\">" );
          out.println( ""+unit_price+" "+currencyid+"" );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "\" WIDTH=\"10%\">" );
          out.println( need_date );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "\" WIDTH=\"10%\">" );
          out.println( vendor_ship_date );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "\" WIDTH=\"10%\">" );
          out.println( promised_date );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "\" WIDTH=\"10%\">" );
          out.println( date_confirmed );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "l\" WIDTH=\"15%\">" );
          out.println( po_line_note );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "l\" WIDTH=\"15%\">" );
          out.println( remarks );
          out.println( "</TD>\n" );
          out.println( "</TR>\n" );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( "An Error Occured While Querying the Databse" );
        out.println( "</BODY>\n</HTML>\n" );
        return;
      }
      finally
      {
        dbrs.close();
        if ( totalrecords == 0 )
        {
          out.println( "<TR><TD>No Records Found</TD></TR>\n" );
        }
      }
      out.println( "</TABLE>\n" );

      //close scrolling table
      out.println( "</DIV>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );
      out.println( "</TBODY>\n" );
      out.println( "</TABLE>\n" );

      out.println( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\">\n" );
      out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
      out.println( "</FORM></BODY></HTML>\n" );

      return;
    }

    public void buildLikebpoSupplier( String poline1,String radianbpo,String ammeandment )
    {
      //StringBuffer Msgl=new StringBuffer();
      DBResultSet dbrs=null;
      ResultSet rs=null;
      nematid_Servlet=bundle.getString( "PURCHASE_ORDER_CARRIER" );

      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Line History","",intcmIsApplication ) );
      printJavaScripts();
      out.println( "// -->\n </SCRIPT>\n" );
      out.println( "<BODY onload=\"javascript:window.resizeTo(700,420)\">\n" );
      out.println( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"addline1\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"addline2\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"carrieraccount\" VALUE=\"\">\n" );
      out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
      out.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>PO:</B></FONT></TD><TD WIDTH=\"70%\">" + radianbpo + "</TD>\n" );
      out.println( "</TR>\n" );
      //int rowNumber=Integer.parseInt( poline1 ) * 1000;
      BigDecimal rowNumber= new BigDecimal(poline1);
      rowNumber = rowNumber.multiply(new BigDecimal("1000"));      
      out.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>PO Line:</B></FONT></TD><TD WIDTH=\"70%\">" + rowNumber + "</TD>\n" );
      out.println( "</TR>\n" );
      out.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
      out.println( "<TR>\n" );
      out.println( "<TH WIDTH=\"12%\"><B>Ammendment</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"8%\"><B>Item Id</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"8%\"><B>Quantity</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"10%\"><B>Unit Price</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"10%\"><B>Need Date</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"10%\"><B>Promised Date</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"10%\"><B>Date Confirmed</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"15%\"><B>Notes</B></FONT></TH>\n" );
      out.println( "<TH WIDTH=\"15%\"><B>Ammendmeant Remarks</B></FONT></TH>\n" );
      out.println( "</TR></TABLE>\n" );

      //open scrolling table
      out.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.println( "<TBODY>\n" );
      out.println( "<TR>\n" );
      out.println( "<TD VALIGN=\"TOP\">\n" );
      out.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );

      //Write code to insert rows here
      out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

      int totalrecords=0;
      int count=0;

      String query="";
      query+=" select BPO,BPO_LINE,AMENDMENT,ITEM_ID,QUANTITY,UNIT_PRICE,ALLOWED_PRICE_VARIANCE,MFG_PART_NO,SUPPLIER_PART_NO,QUALITY_FLOW_DOWNS,PACKAGING_FLOW_DOWNS, ";
      query+=" BPO_LINE_NOTE,to_char(TRANSACTION_DATE,'mm/dd/yyyy') TRANSACTION_DATE,to_char(DATE_CLOSED,'mm/dd/yyyy') DATE_CLOSED,MIN_BUY,MIN_BUY_BASIS, ";
      query+="to_char(DATE_CONFIRMED,'mm/dd/yyyy') DATE_CONFIRMED,SUPPLIER_QTY,SUPPLIER_PKG,SUPPLIER_UNIT_PRICE,REMARKS from bpo_line_history_view where BPO = " + radianbpo + " and BPO_LINE = " + rowNumber + " and AMENDMENT < " + ammeandment + " order by AMENDMENT desc";

      try
      {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          totalrecords+=1;
          count+=1;

          //String bpo=rs.getString( "BPO" ) == null ? "" : rs.getString( "BPO" ).trim();
          //String bpo_line=rs.getString( "BPO_LINE" ) == null ? "" : rs.getString( "BPO_LINE" ).trim();
          String amendment=rs.getString( "AMENDMENT" ) == null ? "" : rs.getString( "AMENDMENT" ).trim();
          String item_id=rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ).trim();
          String quantity=rs.getString( "QUANTITY" ) == null ? "" : rs.getString( "QUANTITY" ).trim();
          String unit_price=rs.getString( "UNIT_PRICE" ) == null ? "" : rs.getString( "UNIT_PRICE" ).trim();
          //String allowed_price_variance=rs.getString( "ALLOWED_PRICE_VARIANCE" ) == null ? "" : rs.getString( "ALLOWED_PRICE_VARIANCE" ).trim();
          //String mfg_part_no=rs.getString( "MFG_PART_NO" ) == null ? "" : rs.getString( "MFG_PART_NO" ).trim();
          //String supplier_part_no=rs.getString( "SUPPLIER_PART_NO" ) == null ? "" : rs.getString( "SUPPLIER_PART_NO" ).trim();
          //String quality_flow_downs=rs.getString( "QUALITY_FLOW_DOWNS" ) == null ? "" : rs.getString( "QUALITY_FLOW_DOWNS" ).trim();
          //String packaging_flow_downs=rs.getString( "PACKAGING_FLOW_DOWNS" ) == null ? "" : rs.getString( "PACKAGING_FLOW_DOWNS" ).trim();
          String bpo_line_note=rs.getString( "BPO_LINE_NOTE" ) == null ? "" : rs.getString( "BPO_LINE_NOTE" ).trim();
          String transaction_date=rs.getString( "TRANSACTION_DATE" ) == null ? "" : rs.getString( "TRANSACTION_DATE" ).trim();
          String date_closed=rs.getString( "DATE_CLOSED" ) == null ? "" : rs.getString( "DATE_CLOSED" ).trim();
          //String min_buy=rs.getString( "MIN_BUY" ) == null ? "" : rs.getString( "MIN_BUY" ).trim();
          //String min_buy_basis=rs.getString( "MIN_BUY_BASIS" ) == null ? "" : rs.getString( "MIN_BUY_BASIS" ).trim();
          String date_confirmed=rs.getString( "DATE_CONFIRMED" ) == null ? "" : rs.getString( "DATE_CONFIRMED" ).trim();
          //String supplier_qty=rs.getString( "SUPPLIER_QTY" ) == null ? "" : rs.getString( "SUPPLIER_QTY" ).trim();
          //String supplier_pkg=rs.getString( "SUPPLIER_PKG" ) == null ? "" : rs.getString( "SUPPLIER_PKG" ).trim();
          //String supplier_unit_price=rs.getString( "SUPPLIER_UNIT_PRICE" ) == null ? "" : rs.getString( "SUPPLIER_UNIT_PRICE" ).trim();
          String remarks=rs.getString( "REMARKS" ) == null ? "" : rs.getString( "REMARKS" ).trim();

          String Color=" ";
          if ( count % 2 == 0 )
          {
            Color="CLASS=\"Inblue";
          }
          else
          {
            Color="CLASS=\"Inwhite";
          }

          out.println( "<TR>\n" );
          out.println( "<TD " + Color + "l\" WIDTH=\"12%\">" );
          out.println( amendment );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "\" WIDTH=\"8%\">" );
          out.println( item_id );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "r\" WIDTH=\"8%\">" );
          out.println( quantity );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "r\" WIDTH=\"10%\">" );
          out.println( unit_price );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "\" WIDTH=\"10%\">" );
          out.println( date_closed );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "\" WIDTH=\"10%\">" );
          out.println( transaction_date );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "\" WIDTH=\"10%\">" );
          out.println( date_confirmed );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "l\" WIDTH=\"15%\">" );
          out.println( bpo_line_note );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + "l\" WIDTH=\"15%\">" );
          out.println( remarks );
          out.println( "</TD>\n" );
          out.println( "</TR>\n" );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( "An Error Occured While Querying the Databse" );
        out.println( "</BODY>\n</HTML>\n" );
        return;
      }
      finally
      {
        dbrs.close();
        if ( totalrecords == 0 )
        {
          out.println( "<TR><TD>No Records Found</TD></TR>\n" );
        }
      }
      out.println( "</TABLE>\n" );

      //close scrolling table
      out.println( "</DIV>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );
      out.println( "</TBODY>\n" );
      out.println( "</TABLE>\n" );

      out.println( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\">\n" );
      out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
      out.println( "</FORM></BODY></HTML>\n" );

      return;
    }

    protected void printJavaScripts()
    {
      //StringBuffer Msglt=new StringBuffer();

      out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
      out.println( "<!--\n" );
      out.println( "function cancel()\n" );
      out.println( "{\n" );
      out.println( "window.close();\n" );
      out.println( "}\n" );

      return;
    }
}