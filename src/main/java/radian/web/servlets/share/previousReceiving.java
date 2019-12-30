package radian.web.servlets.share;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.io.PrintWriter;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
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
 * 04-20-04 - Calling a procedure to reverse a receipt
 */


public class previousReceiving
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
	private static org.apache.log4j.Logger prevlog = org.apache.log4j.Logger.getLogger(previousReceiving.class);

    public previousReceiving(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

     public void buildLikemfgid(String item_id, String comment, boolean approvedOnly,String Hub,PrintWriter out)

     {
      //StringBuffer Msgl = new StringBuffer();
	  DBResultSet dbrs = null;
      ResultSet rs = null;

      out.println("<HTML>\n");
      out.println("<HEAD>\n");
      if (approvedOnly)
      out.println("<TITLE>Approved Receipts for Item Id "+item_id+"</TITLE>\n");
      else
      out.println("<TITLE>Previous Receipts for Item Id "+item_id+"</TITLE>\n");
      out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
      out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
      out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
      out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");

      printJavaScripts(comment,out);

      String Receiving_Servlet = (bundle.getString("HUBRECV_SERVLET_QC"));
      //Nawaz 03-05-02
      out.println("function unReceive(receipt_id) \n");
      out.println("{\n");
      out.println("alert(receipt_id);");
      out.println(" loc = \""+Receiving_Servlet+"&unreceive=Unreceive&UserAction=previousreceiving&poline="+item_id+"&receipt_id=\";\n");
      out.println(" loc = loc + receipt_id;\n");
      out.println("window.location.replace(loc);\n");
      //Msg.append(  "openwin2(loc);\n");
      out.println("}\n");

      out.println("// -->\n </SCRIPT>\n</HEAD>\n");

      if ("".equalsIgnoreCase(comment))
      {
      out.println("<BODY>\n");
      }
      else
      {
      out.println("<BODY onload =\"showMsg()\">\n");
      }
      //out.println("<BODY>\n");

      out.println("<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\"javascript:cancel()\">\n");

      //out.println("<FONT FACE=\"Arial\" SIZE=\"3\"><B>PO:</B>&nbsp;&nbsp;&nbsp;&nbsp;"+po_line+"</FONT>\n");

      out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" >\n");
      out.println("<TR>\n");
      out.println("<TH WIDTH=\"10%\">PO-Line</TH>\n");
      out.println("<TH WIDTH=\"40%\">Supplier</TH>\n");
      out.println("<TH WIDTH=\"10%\">Item</TH>\n");
      out.println("<TH WIDTH=\"10%\">Bin</TH>\n");
      out.println("<TH WIDTH=\"10%\">Receipt Id</TH>\n");
      out.println("<TH WIDTH=\"10%\">Mfg Lot</TH>\n");
      out.println("<TH WIDTH=\"10%\">Qty</TH>\n");
      out.println("<TH WIDTH=\"10%\">DOR</TH>\n");
      if (!approvedOnly)
      out.println("<TH WIDTH=\"5%\">Confirmed</TH>\n");

      out.println("</TR>\n");

      int totalrecords = 0;
      int count = 0;

      String query = "select a.RADIAN_PO,a.RECEIPT_ID,a.PO_LINE,b.LAST_SUPPLIER,a.ITEM_ID,a.BIN,a.MFG_LOT,a.QUANTITY_RECEIVED,to_char(a.DATE_OF_RECEIPT,'mm/dd/yyyy') DATE_OF_RECEIPT1,a.DATE_OF_RECEIPT,"+
                     " a.APPROVED from receipt a,po_history_snapshot b where a.HUB ="+Hub+" and a.item_id="+item_id+" and a.radian_po=b.radian_po and a.PO_LINE = b.LINE_ITEM ";
                     if (approvedOnly)
                     query += "and a.APPROVED = 'Y' ";

                     //query += "and rownum < 11 order by RECEIPT_ID desc";
		     query += " order by RECEIPT_ID desc";

      try
      {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          //System.out.print("Finished The Querry\n ");

          while(rs.next())
          {
            totalrecords += 1;
            count += 1;

            if (count%6==0)
            {
              out.println("<TR>\n");
              out.println("<TH WIDTH=\"10%\">PO-Line</TH>\n");
              out.println("<TH WIDTH=\"40%\">Supplier</TH>\n");
              out.println("<TH WIDTH=\"10%\">Item</TH>\n");
              out.println("<TH WIDTH=\"10%\">Bin</TH>\n");
              out.println("<TH WIDTH=\"10%\">Receipt Id</TH>\n");
              out.println("<TH WIDTH=\"10%\">Mfg Lot</TH>\n");
              out.println("<TH WIDTH=\"10%\">Qty</TH>\n");
              out.println("<TH WIDTH=\"10%\">DOR</TH>\n");
              if (!approvedOnly)
              out.println("<TH WIDTH=\"5%\">Confirmed</TH>\n");

              out.println("</TR>\n");
            }

            String Color = " ";
            if (count%2==0)
            {
                Color ="CLASS=\"blue";
            }
            else
            {
                Color ="CLASS=\"white";
            }

            out.println("<TR ALIGN=\"MIDDLE\">\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("RADIAN_PO")));out.println("-");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("PO_LINE")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("LAST_SUPPLIER")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("ITEM_ID")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("BIN")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("RECEIPT_ID")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_LOT")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("QUANTITY_RECEIVED")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("DATE_OF_RECEIPT1")));out.println("</TD>\n");

            if (!approvedOnly)
            {
              if (rs.getString("APPROVED").equalsIgnoreCase("N"))
              {
              out.println("<TD "+Color+"ll\" WIDTH=\"5%\">&nbsp;</TD>\n");
              }
              else
              {
              out.println("<TD "+Color+"ll\" WIDTH=\"5%\">Confirmed</TD>\n");
              }
            }

            out.println("</TR>\n");
          }
    }
    catch (Exception e)
    {
        e.printStackTrace();
        out.println("An Error Occured While Querying the Databse");
        out.println("</BODY>\n</HTML>\n");
        return;
    }
    finally
    {
        dbrs.close();
        if (totalrecords==0)
        {out.println("<TR><TD COLSPAN =\"7\">No Records Found</TD></TR>\n");}
    }
      out.println("<TR>\n");
      if (!approvedOnly)
      out.println("<TD BGCOLOR=\"#000066\">&nbsp;</TD>\n");

      out.println("<TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD></TR>\n");
      out.println("</TABLE>\n");

      out.println("<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n");
     // out.println("<CENTER><BUTTON name=\"searchlike\" value=\"testing\" OnClick=\"cancel()\" type=\"button\">OK</BUTTON>\n");

      out.println("</FORM></BODY></HTML>\n");

      return;

    }

    public void buildoldpoline(String po_line, String purch_order, String comment, boolean approvedOnly,String Hub,PrintWriter out)
    {
      //StringBuffer Msgl = new StringBuffer();
	  DBResultSet dbrs = null;
      ResultSet rs = null;

      out.println("<HTML>\n");
      out.println("<HEAD>\n");

      if (approvedOnly)
      out.println("<TITLE>Approved Receipts for PO "+purch_order+"</TITLE>\n");
      else
      out.println("<TITLE>Previous Receipts for PO "+purch_order+"</TITLE>\n");

      out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
      out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
      out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
      out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
      printJavaScripts(comment,out);

      String Receiving_Servlet = (bundle.getString("HUBRECV_SERVLET_QC"));
      //Nawaz 03-05-02
      out.println("function unReceive(receipt_id) \n");
      out.println("{\n");
      out.println("alert(receipt_id);");
      out.println(" loc = \""+Receiving_Servlet+"&unreceive=Unreceive&UserAction=previousreceiving&&purchorder="+purch_order+"&poline="+po_line+"&receipt_id=\";\n");
      out.println(" loc = loc + receipt_id;\n");
      out.println("window.location.replace(loc);\n");
      //Msg.append(  "openwin2(loc);\n");
      out.println("}\n");

      out.println("// -->\n </SCRIPT>\n</HEAD>\n");

      if ("".equalsIgnoreCase(comment))
      {
      out.println("<BODY>\n");
      }
      else
      {
      out.println("<BODY onload =\"showMsg()\">\n");
      }

      //out.println("<BODY>\n");

      out.println("<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\"javascript:cancel()\">\n");

      //out.println("<FONT FACE=\"Arial\" SIZE=\"3\"><B>Radian PO:</B>&nbsp;&nbsp;&nbsp;&nbsp;"+po_line+"\n");

      out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" >\n");
      out.println("<TR>\n");
      out.println("<TH WIDTH=\"10%\">PO-Line</TH>\n");
      out.println("<TH WIDTH=\"40%\">Supplier</TH>\n");
      out.println("<TH WIDTH=\"10%\">Item</TH>\n");
      out.println("<TH WIDTH=\"10%\">Bin</TH>\n");
      out.println("<TH WIDTH=\"10%\">Receipt Id</TH>\n");
      out.println("<TH WIDTH=\"10%\">Mfg Lot</TH>\n");
      out.println("<TH WIDTH=\"10%\">Qty</TH>\n");
      out.println("<TH WIDTH=\"10%\">DOR</TH>\n");
      if (!approvedOnly)
      out.println("<TH WIDTH=\"5%\">Confirmed</TH>\n");

      out.println("</TR>\n");

      int totalrecords = 0;
      int count = 0;

      //This is the query to get all request ids which need catalog part number
      String query = "select a.RADIAN_PO,a.RECEIPT_ID,a.PO_LINE,b.LAST_SUPPLIER,a.ITEM_ID,a.BIN,a.MFG_LOT,a.QUANTITY_RECEIVED,to_char(a.DATE_OF_RECEIPT,'mm/dd/yyyy') DATE_OF_RECEIPT1,a.DATE_OF_RECEIPT,a.APPROVED "+
                     " from receipt a,po_history_snapshot b where a.HUB ="+Hub+" and a.radian_po="+purch_order+" and a.PO_LINE="+po_line+" and a.radian_po=b.radian_po and a.PO_LINE = b.LINE_ITEM ";
                     if (approvedOnly)
                     query += "and a.APPROVED = 'Y' ";

                     query += " order by RECEIPT_ID desc";
		     //query += "and rownum < 11 order by RECEIPT_ID desc";
      try
      {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          //System.out.print("Finished The Querry\n ");

          while(rs.next())
          {
            totalrecords += 1;
            count += 1;

            if (count%6==0)
            {
              out.println("<TR>\n");
              out.println("<TH WIDTH=\"10%\">PO-Line</TH>\n");
              out.println("<TH WIDTH=\"40%\">Supplier</TH>\n");
              out.println("<TH WIDTH=\"10%\">Item</TH>\n");
              out.println("<TH WIDTH=\"10%\">Bin</TH>\n");
              out.println("<TH WIDTH=\"10%\">Receipt Id</TH>\n");
              out.println("<TH WIDTH=\"10%\">Mfg Lot</TH>\n");
              out.println("<TH WIDTH=\"10%\">Qty</TH>\n");
              out.println("<TH WIDTH=\"10%\">DOR</TH>\n");
              if (!approvedOnly)
              out.println("<TH WIDTH=\"5%\">Confirmed</TH>\n");

              out.println("</TR>\n");
            }

            String Color = " ";
            if (count%2==0)
            {
                Color ="CLASS=\"blue";
            }
            else
            {
                Color ="CLASS=\"white";
            }
            out.println("<TR ALIGN=\"MIDDLE\">\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("RADIAN_PO")));out.println("-");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("PO_LINE")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("LAST_SUPPLIER")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("ITEM_ID")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("BIN")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("RECEIPT_ID")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_LOT")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("QUANTITY_RECEIVED")));out.println("</TD>\n");
            out.println("<TD "+Color+"ll\" WIDTH=\"10%\">");out.println(BothHelpObjs.makeSpaceFromNull(rs.getString("DATE_OF_RECEIPT1")));out.println("</TD>\n");

            if (!approvedOnly)
            {
              if (rs.getString("APPROVED").equalsIgnoreCase("N"))
              {
              out.println("<TD "+Color+"ll\" WIDTH=\"5%\">&nbsp;</TD>\n");
              }
              else
              {
              out.println("<TD "+Color+"ll\" WIDTH=\"5%\">Confirmed</TD>\n");
              }
            }

            out.println("</TR>\n");
          }
    }
    catch (Exception e)
    {
        e.printStackTrace();
        out.println("An Error Occured While Querying the Databse");
        out.println("</BODY>\n</HTML>\n");
        return;
    }
    finally
    {
        dbrs.close();
        if (totalrecords==0)
        {out.println("<TR><TD COLSPAN =\"7\">No Records Found</TD></TR>\n");}
    }
      out.println("<TR>\n");
      if (!approvedOnly)
      out.println("<TD BGCOLOR=\"#000066\">&nbsp;</TD>\n");

      out.println("<TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD></TR>\n");
      out.println("</TABLE>\n");

      out.println("<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n");
     // out.println("<CENTER><BUTTON name=\"searchlike\" value=\"testing\" OnClick=\"cancel()\" type=\"button\">OK</BUTTON>\n");

      out.println("</FORM></BODY></HTML>\n");

      return;

    }

    public boolean unreceiveReceipt( String receipt_id )
    {
        boolean result = false;
		Connection connect1;
		CallableStatement cs=null;
        if (receipt_id.length() < 1)
        {
        result = false;
        return result;
        }

		prevlog.info("Calling p_receipt_reverse from REC QC for RECEIPT_ID "+receipt_id+"");

		try
		{
		  connect1=db.getConnection();
		  try
		  {
			  cs = connect1.prepareCall("{call p_receipt_reverse(?)}");
			  cs.setInt(1,Integer.parseInt(receipt_id));

			  //cs.registerOutParameter(2,java.sql.Types.VARCHAR);
			  cs.execute();

			  //errorcode = BothHelpObjs.makeBlankFromNull(cs.getString(21));
			  result = true;
		  }
		  catch (Exception e)
		  {
			  e.printStackTrace();
			  result = false;
			  radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling Procedure call p_receipt_reverse in HubReceiving QC","Error occured while running call p_receipt_reverse \nError Msg:\n"+e.getMessage()+"\n\n Parameters passed p_receipt_reverse receiptID "+receipt_id+"\n");
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

        /*try
        {
            String query = "delete from receipt where RECEIPT_ID = "+receipt_id+"";
            db.doUpdate(query);

            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            result = false;
        }
        //System.out.println("Exit Method : "+ "HubReceivingTables.UpdateLine()" );*/
        return result;
    }

    protected void printJavaScripts(String ShowThis,PrintWriter out)
    {
    //StringBuffer Msglt = new StringBuffer();

    out.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.println("<!--\n");

    out.println("function cancel()\n");
    out.println("{\n");
    out.println("window.close();\n");
    out.println("}\n");

    out.println("function showMsg(){\n alert(\""+ShowThis+"\"); \n}\n");

    return;
    }
}