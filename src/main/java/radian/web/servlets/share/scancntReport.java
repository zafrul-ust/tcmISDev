package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 06-29-04 - Showing the status as picked if an MR is on a Picklist
 */

public class scancntReport
{
  private TcmISDBModel db=null;
  PrintWriter out = null;
	private boolean intcmIsApplication = false;
  public scancntReport( TcmISDBModel d )
  {
	db=d;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
	out=response.getWriter();
	response.setContentType( "text/html" );
	HttpSession session=request.getSession();
	PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
	Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
	if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
	{
	 intcmIsApplication = true;
	}

	String User_Session=request.getParameter( "session" );
	if ( User_Session == null )
	  User_Session="New";

	String uploadid=request.getParameter( "uploadid" );
	if ( uploadid == null )
	  uploadid="";
	uploadid=uploadid.trim();


	/*String mrslist=request.getParameter( "mrslist" );
	if ( mrslist == null )
	  mrslist="";
	mrslist=mrslist.trim();

	StringTokenizer st = new StringTokenizer(mrslist, ",");
	String searchmrlist = "'";
	if(st.countTokens() > 1)
	{
		while(st.hasMoreTokens())
		{
			String tok = st.nextToken();
			searchmrlist += tok + "','";
		}
		searchmrlist = searchmrlist.substring(0, searchmrlist.length() - 2);
	}
	else if (mrslist.length() > 0)
	{
	  searchmrlist = "'"+mrslist+"'";
	}
	else
	{
	  searchmrlist = "' '";
	}*/

	String User_Action=request.getParameter( "UserAction" );
	if ( User_Action == null )
	  User_Action="New";
	User_Action=User_Action.trim();

	try
	{
	  if ( User_Action.equalsIgnoreCase( "Search" ) )
	  {
		buildHeader( uploadid );
		buildDetails( uploadid );

		/*if (searchmrlist.trim().length() > 3)
		{
		  buildDetails( searchmrlist );
		}
		else
		{
		  out.println( "No MRs were created or No number was entered to search");
		}*/
		out.println( "</body></html>\n" );
	  }
	  else
	  {
		buildHeader( uploadid );
		out.println( "</body></html>\n" );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  out.close();
	}
  }

private void buildDetails( String uploadid )
{
  //StringBuffer Msg=new StringBuffer();
  DBResultSet dbrs=null;
  ResultSet searchRs=null;
  int count=0;

  out.println( "<TABLE BORDER=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
  out.println( "<TD HEIGHT=20 WIDTH=\"100%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  out.println( "<B>Receipts Found in the Wrong Bin:</B>&nbsp;\n" );
  out.println( "</TD>\n" );
  out.println( "</TR>\n" );
  out.println( "</TABLE>\n" );

  out.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"50%\" CLASS=\"moveup\" ALIGN=\"CENTER\">\n");
  String Color="CLASS=\"white";

  try
  {
	String query="select distinct RECEIPT_ID,SCANNED_BIN,EXPECTED_BIN from scanned_receipt_report_view where upload_id="+uploadid+" and EXPECTED_BIN <> SCANNED_BIN";

	dbrs=db.doQuery( query );
	searchRs=dbrs.getResultSet();

	while ( searchRs.next() )
	{
	  boolean createHdr=false;
	  if ( count % 10 == 0 )
	  {
		createHdr=true;
	  }

	  if ( createHdr )
	  {
		out.println( "<tr align=\"center\">\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Receipt ID</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Expected Bin</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Scanned Bin</TH>\n" );
		out.println( "</tr>\n" );
	  }

	  String receiptid = searchRs.getString("RECEIPT_ID")==null?"&nbsp;":searchRs.getString("RECEIPT_ID");
	  String scannedbin = searchRs.getString("SCANNED_BIN")==null?"&nbsp;":searchRs.getString("SCANNED_BIN");
	  String expectedbin = searchRs.getString("EXPECTED_BIN")==null?"&nbsp;":searchRs.getString("EXPECTED_BIN");

	  out.println( "<tr align=\"center\">\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" +receiptid +"</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + expectedbin + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + scannedbin + "</TD>\n" );
	  out.println( "</tr>\n" );

	  count++;
	  if ( ( count ) % 2 == 0 )
	  {
		Color="CLASS=\"white";
	  }
	  else
	  {
		Color="CLASS=\"blue";
	  }
	}
  }
  catch ( Exception e )
  {
	e.printStackTrace();
	out.println(radian.web.HTMLHelpObj.printHTMLError());
  }
  finally
  {
	if ( dbrs != null ){dbrs.close();}
  }

  if (count ==0)
  {
	out.println( "<TD>None.</TD>\n" );
  }
  out.println( "</TABLE>\n" );

  out.println( "<BR><BR><TABLE BORDER=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
  out.println( "<TD HEIGHT=20 WIDTH=\"100%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  out.println( "<B>Receipts where the Qty did not match:</B>\n" );
  out.println( "</TD>\n" );
  out.println( "</TR>\n" );
  out.println( "</TABLE>\n" );

  out.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"50%\" CLASS=\"moveup\" ALIGN=\"CENTER\">\n");
  count = 0;

  try
  {
	String query="select distinct RECEIPT_ID,TOTAL_QTY_COUNTED_FOR_RECEIPT,nvl(EXPECTED_QTY_FOR_RECEIPT,0) EXPECTED_QTY_FOR_RECEIPT from scanned_receipt_report_view where upload_id="+uploadid+" and TOTAL_QTY_COUNTED_FOR_RECEIPT <> nvl(EXPECTED_QTY_FOR_RECEIPT,0)";

	dbrs=db.doQuery( query );
	searchRs=dbrs.getResultSet();
	while ( searchRs.next() )
	{
	  boolean createHdr=false;
	  if ( count % 10 == 0 )
	  {
		createHdr=true;
	  }

	  if ( createHdr )
	  {
		out.println( "<tr align=\"center\">\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Receipt ID</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Expected Qty</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Scanned Qty</TH>\n" );
		out.println( "</tr>\n" );
	  }

	  String receiptid = searchRs.getString("RECEIPT_ID")==null?"&nbsp;":searchRs.getString("RECEIPT_ID");
	  String scannedqty = searchRs.getString("TOTAL_QTY_COUNTED_FOR_RECEIPT")==null?"&nbsp;":searchRs.getString("TOTAL_QTY_COUNTED_FOR_RECEIPT");
	  String expectedqty = searchRs.getString("EXPECTED_QTY_FOR_RECEIPT")==null?"&nbsp;":searchRs.getString("EXPECTED_QTY_FOR_RECEIPT");

	  out.println( "<tr align=\"center\">\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" +receiptid +"</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + expectedqty + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + scannedqty + "</TD>\n" );
	  out.println( "</tr>\n" );

	  count++;
	  if ( ( count ) % 2 == 0 )
	  {
		Color="CLASS=\"white";
	  }
	  else
	  {
		Color="CLASS=\"blue";
	  }
	}

  }
  catch ( Exception e )
  {
	e.printStackTrace();
	out.println(radian.web.HTMLHelpObj.printHTMLError());
  }
  finally
  {
	if ( dbrs != null ){dbrs.close();}
  }

  if (count ==0)
  {
	out.println( "<TD>None.</TD>\n" );
  }

  out.println( "</TABLE>\n" );

  out.println( "<BR><BR>&nbsp;<P><TABLE BORDER=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
  out.println( "<TD HEIGHT=20 WIDTH=\"100%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  out.println( "<B>Receipts that were not scanned:</B>\n" );
  out.println( "</TD>\n" );
  out.println( "</TR>\n" );
  out.println( "</TABLE>\n" );

  out.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\" ALIGN=\"CENTER\">\n");
  count = 0;

  try
  {
	String query="select RECEIPT_ID,BIN, ITEM_ID, ITEM_DESC, CURRENT_EXPECTED_QTY from scanned_bin_missing_receipt where upload_id="+uploadid+" ";

	dbrs=db.doQuery( query );
	searchRs=dbrs.getResultSet();
	while ( searchRs.next() )
	{
	  boolean createHdr=false;
	  if ( count % 10 == 0 )
	  {
		createHdr=true;
	  }

	  if ( createHdr )
	  {
		out.println( "<tr align=\"center\">\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Receipt ID</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Item ID</TH>\n" );
		out.println( "<TH width=\"15%\"  height=\"38\">Item Desc</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">BIN</TH>\n" );
		out.println( "<TH width=\"5%\"  height=\"38\">Expected Qty</TH>\n" );
		out.println( "</tr>\n" );
	  }

	  String receiptid = searchRs.getString("RECEIPT_ID")==null?"&nbsp;":searchRs.getString("RECEIPT_ID");
	  String itemid = searchRs.getString("ITEM_ID")==null?"&nbsp;":searchRs.getString("ITEM_ID");
	  String itemdesc = searchRs.getString("ITEM_DESC")==null?"&nbsp;":searchRs.getString("ITEM_DESC");
	  String expbin=searchRs.getString( "BIN" ) == null ? "&nbsp;" : searchRs.getString( "BIN" );
	  String expqty=searchRs.getString( "CURRENT_EXPECTED_QTY" ) == null ? "&nbsp;" : searchRs.getString( "CURRENT_EXPECTED_QTY" );

	  out.println( "<tr align=\"center\">\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" +receiptid +"</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + itemid + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"15%\" HEIGHT=\"20\">" + itemdesc + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + expbin + "</TD>\n" );
	  out.println( "<TD " + Color + "\" width=\"5%\" HEIGHT=\"20\">" + expqty + "</TD>\n" );
	  out.println( "</tr>\n" );

	  count++;
	  if ( ( count ) % 2 == 0 )
	  {
		Color="CLASS=\"white";
	  }
	  else
	  {
		Color="CLASS=\"blue";
	  }
	}

  }
  catch ( Exception e )
  {
	e.printStackTrace();
	out.println(radian.web.HTMLHelpObj.printHTMLError());
  }
  finally
  {
	if ( dbrs != null ){dbrs.close();}
  }

  if (count ==0)
  {
	out.println( "<TD>None.</TD>\n" );
  }

  out.println( "</TABLE>\n" );

  return;
}

//Build HTML Header
private void buildHeader( String mrnumbers)
{
  //StringBuffer Msg=new StringBuffer();
  out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Hub Scanner Count Results","cabupconfirm.js",intcmIsApplication ) );
  out.println( "</HEAD>  \n" );
  out.println( "<BODY>\n" );
  out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
  out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n" );
  out.println( "</DIV>\n" );
  out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
  out.println( radian.web.HTMLHelpObj.PrintTitleBar( "Hub Scanner Count Results\n" ) );

  out.println( "<FORM METHOD=\"POST\" NAME=\"cabupconfirm\" ACTION=\"" + radian.web.tcmisResourceLoader.getcabupconfirmurl() + "\">\n" );
  /*out.println( "<TABLE BORDER=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
  out.println( "<TR VALIGN=\"MIDDLE\">\n" );

  out.println( "<TD HEIGHT=30 WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
  out.println( "<B>MR Numbers:</B>&nbsp;\n" );
  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"mrslist\" value=\"" + mrnumbers + "\" size=\"30\">\n" );
  out.println( "</TD>\n" );

  out.println( "<TD HEIGHT=30 WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
  out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick=\"return actionValue(name,this)\" NAME=\"Search\">\n" );
  out.println( "</TD>\n" );
  out.println( "</TR>\n" );

  out.println( "<TR VALIGN=\"MIDDLE\" ALIGN=\"LEFT\">\n" );
  out.println( "<TD HEIGHT=10 WIDTH=\"30%\" ALIGN=\"LEFT\" CLASS=\"announce\" COLSPAN=\"2\">\n" );
  out.println( "You can enter multiple MR Numbers seperated by commas.\n" );
  out.println( "</TD>\n" );
  out.println( "</TR>\n" );

  out.println( "</TABLE>\n" );*/
  out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n" );
  out.println( "</FORM>\n" );

  return;
}

}
