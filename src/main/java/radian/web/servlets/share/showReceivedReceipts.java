package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import radian.tcmis.server7.share.db.DBResultSet;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 03-18-03 Showing this window for non-chemicals
 * 12-15-03 Code Cleanup
 * 04-26-04 Changes to accomodate receiving kits as seperable things
 * 08-10-04 Showing the received receipts page for drop ship receipts
 * 02-28-05 Giving the option to not print Kit/Case Qty Labels.
 * 06-22-05 - Showing MR-Line for returned receipts.
 */


public class showReceivedReceipts   extends HttpServlet   implements SingleThreadModel
{
  public void init( ServletConfig config )	 throws ServletException
  {
	super.init( config );
  }

  /**
   * HTTP POST handler.
   *
   * @param request               An object implementing the request context
   * @param response              An object implementing the response context
   * @exception ServletException  A wrapper for all exceptions thrown by the
   *      servlet.
   */
  public void doGet( HttpServletRequest request,HttpServletResponse response )	 throws ServletException,IOException
  {
	doPost( request,response );
  }

  public void doPost( HttpServletRequest request,HttpServletResponse response )
	 throws ServletException,IOException
  {
	PrintWriter out=response.getWriter();
	response.setContentType( "text/html" );
	TcmISDBModel db=null;
	HttpSession session=request.getSession();
	//StringBuffer Msgn=new StringBuffer();
	try
	{
	  db=new RayTcmISDBModel( "Tcm_Ops" );
	  if ( db == null )
	  {
		PrintWriter out2=new PrintWriter( response.getOutputStream() );
		out2.println( "Starting DB" );
		out2.println( "DB is null" );
		out2.close();
		return;
	  }

	  Hashtable loginresult=new Hashtable();
	  loginresult=radian.web.loginhelpObj.logintopage( session,request,db,out );
	  String auth= ( String ) loginresult.get( "AUTH" );
	  String errormsg= ( String ) loginresult.get( "ERROSMSG" );

	  if ( auth == null )
	  {
		auth="challenge";
	  }
	  if ( errormsg == null )
	  {
		errormsg="";
	  }

	  if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
	  {
		out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Receiving" ) );
		out.flush();
	  }
	 else {
		String receivedReceipts = request.getParameter("receivedReceipts");
		if (receivedReceipts == null)
		 receivedReceipts = "";

		String updateprintdate = "";
		Vector largeLabelData = new Vector();
		Vector receiptData=new Vector();
		if (receivedReceipts.trim().length() == 0)
		{
		Vector sequencenumbers = (Vector) session.getAttribute("REC_LABEL_SEQUENCE_NUMBERS");
		Hashtable sum = new Hashtable();
		sum.put("TOTAL_NUMBER", new Integer(sequencenumbers.size()));
		largeLabelData.addElement(sum);

		for (int ik = 0; ik < sequencenumbers.size(); ik++) {
		 String receiptidt = sequencenumbers.elementAt(ik).toString();
		 updateprintdate += receiptidt + ",";
		}
		updateprintdate = updateprintdate.substring(0,(updateprintdate.length()-1));
		}
		else
		{
		 updateprintdate = receivedReceipts;
		}

		String genLabels="";
		genLabels=BothHelpObjs.makeSpaceFromNull( request.getParameter( "genLabels" ) );

		String hubNoforlabel=BothHelpObjs.makeSpaceFromNull( request.getParameter( "HubNoforlabel" ) );
		if ( hubNoforlabel == null )
		  hubNoforlabel="";

		String showissuedrcts = request.getParameter( "showissuedrcts" );
		if ( showissuedrcts == null )
		{
		  showissuedrcts="";
		}
		showissuedrcts = showissuedrcts.trim();

		String printKitLabels=request.getParameter( "printKitLabels" );
		if ( printKitLabels == null )
		  printKitLabels = "";


		String supplierpage= session.getAttribute( "SUPPLIERPAGE" ) == null ? "" : session.getAttribute( "SUPPLIERPAGE" ).toString();

		out.println( "<HTML>\n" );
		out.println( "<HEAD>\n" );
		out.println( "<TITLE>Received Items</TITLE>\n" );
		out.println( "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n" );
		out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
		out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
		out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
		//out.println("<SCRIPT SRC=\"/clientscripts/showConfirmedReceipts.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
		out.println( "<SCRIPT SRC=\"/clientscripts/receivingjs.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n" );
		out.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n" );
		out.println( "<!-- \n" );

		out.println( "window.onbeforeprint =\n" );
		out.println( "  function () {\n" );
		out.println( "    document.body.style.marginLeft =\n" );
		out.println( "    document.body.style.marginRight = '20px';\n" );
		out.println( "    document.body.style.border = '2px solid lime';\n" );
		out.println( "    document.all.printLink.style.display = 'none';\n" );
		out.println( "    document.all.option1.style.display = 'none';\n" );
		out.println( "    document.all.option2.style.display = 'none';\n" );
		out.println( "    document.all.option3.style.display = 'none';\n" );
		out.println( "    document.all.option4.style.display = 'none';\n" );
		out.println( "    document.all.genbutton1.style.display = 'none';\n" );
		out.println( "    document.all.genbutton2.style.display = 'none';\n" );
		out.println( " };\n" );

		out.println( "window.onafterprint =\n" );
		out.println( "  function () {\n" );
		out.println( "    document.body.style.marginLeft =\n" );
		out.println( "    document.body.style.marginRight = '';\n" );
		out.println( "    document.body.style.border = '';\n" );
		out.println( "    document.all.printLink.style.display = '';\n" );
		out.println( "    document.all.option1.style.display = '';\n" );
		out.println( "    document.all.option2.style.display = '';\n" );
		out.println( "    document.all.option3.style.display = '';\n" );
		out.println( "    document.all.option4.style.display = '';\n" );
		out.println( "    document.all.genbutton1.style.display = '';\n" );
		out.println( "    document.all.genbutton2.style.display = '';\n" );
		out.println( " };\n" );

		out.println( "//-->     \n" );
		out.println( "</SCRIPT>  \n" );
		out.println( "</HEAD>\n" );
		out.println( "<BODY>\n" );

		out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
		out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
		out.println( "</DIV>\n" );
		out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

		out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Received Items\n" ) );

		out.println( "<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"/tcmIS/Hub/Receiving?session=Active&generate_labels=yes&UserAction=GenerateLabels\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"contBin\" VALUE=\" \">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"Paper\" VALUE=\"31\">\n" );

		//start table #1
		out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"600\" CLASS=\"moveup\">\n" );
		out.println( "<TR VALIGN=\"MIDDLE\">\n" );
		if ( genLabels.equalsIgnoreCase( "1" ) )
		{
		  //Options
		  out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
		  out.println( "<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('31')\" value=\"31\" checked>\n" );
		  out.println( "</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option3\">3''/1''&nbsp;</TD>\n" );

          out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
          out.println( "<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('35')\" value=\"35\" >\n" );
          out.println( "</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option3\">3''/5''&nbsp;</TD>\n" );

          out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
		  out.println( "<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('811')\" value=\"811\">\n" );
		  out.println( "</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">8.5''/11''</TD>\n\n" );
		  //Button
		  out.println( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		  out.println( "   <INPUT TYPE=\"BUTTON\" ID=\"genbutton1\" VALUE=\"Container Labels\" NAME=\"SearchButton\" onclick= \"javascript:doRecPopup(this,'CONTAINER')\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\">&nbsp;\n" );
		  out.println( "</TD>\n" );

      if (!"Yes".equalsIgnoreCase(supplierpage))
			{
			 out.println("<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
			 out.println("   <INPUT TYPE=\"BUTTON\" ID=\"genbutton2\" VALUE=\"Bin Labels\" NAME=\"SearchButton\" onclick= \"javascript:doRecPopup(this,'BIN')\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\">&nbsp;\n");
			 out.println("</TD>\n");

			 out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
			 out.println("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Receiving Labels\" onclick= \"dolargelabelPopup()\" NAME=\"UpdateButton\">&nbsp;\n");
			 out.println("</TD>\n");
			}
		}
		else
		{
		  out.println( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		  out.println( "  <INPUT TYPE=\"hidden\" NAME=\"option1\" VALUE=\"\">\n" );
		  out.println( "  <INPUT TYPE=\"hidden\" NAME=\"option2\" VALUE=\"\">\n" );
		  out.println( "  <INPUT TYPE=\"hidden\" NAME=\"option3\" VALUE=\"\">\n" );
		  out.println( "  <INPUT TYPE=\"hidden\" NAME=\"option4\" VALUE=\"\">\n" );
		  out.println( "  <INPUT TYPE=\"hidden\" NAME=\"genbutton1\" VALUE=\"\">\n" );
		  out.println( "  <INPUT TYPE=\"hidden\" NAME=\"genbutton2\" VALUE=\"\">\n" );
		  out.println( "</TD>\n" );
		}

		out.println( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		out.println( "<INPUT TYPE=\"BUTTON\" ID=\"printLink\" ONCLICK=\"javascript: if (window.print) window.print();\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" value=\"Print This Page\">\n" );
		out.println( "</TD>\n" );
		out.println("</TD></TR>\n");

		if ( genLabels.equalsIgnoreCase( "1" ) )
		{
		  out.println("<TR VALIGN=\"MIDDLE\">\n");
		  out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		  out.println( "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + ( printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) +">\n" );
		  out.println( "</TD>\n" );
		  out.println( "<TD WIDTH=\"5%\" COLSPAN=\"5\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		  out.println( "&nbsp;Skip Kit/Case Qty Labels\n" );
		  out.println( "</TD>\n" );
		  out.println("</TR>\n");
		}
		out.println("</TABLE>\n");


		out.println( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"600\" CLASS=\"moveup\">\n" );
		int i=0; //used for detail lines
		DBResultSet dbrs=null;
		ResultSet rs=null;

		String user_query="select a.BRANCH_PLANT,a.TRANSFER_REQUEST_ID,a.DOC_TYPE,a.RADIAN_PO,a.PO_LINE,a.ITEM_ID,a.MFG_LOT,a.BIN,a.RECEIPT_ID,a.QUANTITY_RECEIVED,a.LOT_STATUS, ";
		user_query+=" to_char(a.EXPIRE_DATE,'mm/dd/yyyy') EXPIRE_DATE,to_char(a.DATE_OF_SHIPMENT,'mm/dd/yyyy') DATE_OF_SHIPMENT,to_char(a.DATE_OF_MANUFACTURE,'mm/dd/yyyy') DATE_OF_MANUFACTURE, ";

		if ("Yes".equalsIgnoreCase(showissuedrcts))
		{
		  user_query+=" a.DATE_OF_RECEIPT,a.MANAGE_KITS_AS_SINGLE_UNIT, a.COMPONENT_ID,a.MATERIAL_DESC,a.INVENTORY_GROUP_NAME,a.SUPPLIER_NAME,a.PACKAGING, ";
		  user_query+=" a.LINE_DESC,a.INDEFINITE_SHELF_LIFE ";
		  user_query+=" ,a.PR_NUMBER || '-' || a.LINE_ITEM  MR_LINE from RECEIPT_DESC_DROPSHIP_VIEW a ";
		  user_query+=" where receipt_id in ("+updateprintdate+") order by RECEIPT_ID";
		}
		else if ( genLabels.equalsIgnoreCase( "1" ) )
		{
		  user_query+=" a.DATE_OF_RECEIPT,a.MANAGE_KITS_AS_SINGLE_UNIT, a.COMPONENT_ID,a.MATERIAL_DESC,a.INVENTORY_GROUP_NAME,a.SUPPLIER_NAME,a.PACKAGING, ";
		  user_query+=" a.LINE_DESC,a.INDEFINITE_SHELF_LIFE ";
		  user_query+=" ,'' MR_LINE from receipt_description_view a ";
		  user_query+=" where receipt_id in ("+updateprintdate+") order by RECEIPT_ID";
		}
		else if ( genLabels.equalsIgnoreCase( "2" ) )
		{
		  user_query+=" to_char(a.DATE_OF_RECEIPT,'mm/dd/yyyy') DATE_OF_RECEIPT,'' MR_LINE,''INDEFINITE_SHELF_LIFE ,'' SUPPLIER_NAME, '' MANAGE_KITS_AS_SINGLE_UNIT, '' COMPONENT_ID,'' MATERIAL_DESC,a.INVENTORY_GROUP INVENTORY_GROUP_NAME, b.PART_DESCRIPTION LINE_DESC ,b.PACKAGING from receipt a,no_buy_order_receivingqc_view b ";
		  user_query+=" where a.radian_po=b.radian_po and a.PO_LINE = b.LINE_ITEM and a.APPROVED = 'N' ";
		  user_query+=" and receipt_id in ("+updateprintdate+") order by RECEIPT_ID";
		}

		try
		{
		  dbrs=db.doQuery( user_query );
		  rs=dbrs.getResultSet();

		  while ( rs.next() )
		  {
			boolean createHdr=false;
			if ( i % 10 == 0 )
			{
			  createHdr=true;
			}

			i++;
			String Color=" ";
			if ( i % 2 == 0 )
			{
			  Color="CLASS=\"blue";
			}
			else
			{
			  Color="CLASS=\"white";
			}

			if ( createHdr )
			{
			  out.println( "<tr align=\"left\">\n" );
			  out.println( "<TH width=\"2%\"  height=\"38\">PO<BR>Line</TH>\n" );
			  out.println( "<TH width=\"7%\"  height=\"38\">Supplier</TH>\n" );
			  if ("Yes".equalsIgnoreCase(supplierpage))
			  {
				out.println( "<TH width=\"5%\"  height=\"38\">MR-Line</TH>\n" );
			  }
			  out.println( "<TH width=\"11%\" height=\"38\">Item<BR>Packaging</TH>\n" );
			  out.println( "<TH width=\"15%\" height=\"38\">Description</TH>\n" );
			  if ("Yes".equalsIgnoreCase(supplierpage))
			  {
				out.println( "<TH width=\"5%\"  height=\"38\">Lot<BR>Receipt ID</TH>\n" );
			  }
			  else
			  {
				out.println( "<TH width=\"5%\"  height=\"38\">Lot<BR>Bin<BR>Receipt ID</TH>\n" );
			  }
			  out.println( "<TH width=\"5%\"  height=\"38\">Dates</TH>\n" );
			  out.println( "<TH width=\"5%\"  height=\"38\">Qty Rec'd</TH>\n" );
			  out.println( "</tr>\n" );
			}

			String desc=rs.getString( "LINE_DESC" ) == null ? "" : rs.getString( "LINE_DESC" );
			String receiptId=rs.getString( "RECEIPT_ID" ) == null ? "" : rs.getString( "RECEIPT_ID" );
			receiptData.addElement( receiptId );
			String Bin=rs.getString( "BIN" ) == null ? "" : rs.getString( "BIN" );
			String mfglot=rs.getString( "MFG_LOT" ) == null ? "" : rs.getString( "MFG_LOT" );
			String dor=rs.getString( "DATE_OF_RECEIPT" ) == null ? "" : rs.getString( "DATE_OF_RECEIPT" );
			String dom=rs.getString( "DATE_OF_MANUFACTURE" ) == null ? "" : rs.getString( "DATE_OF_MANUFACTURE" );
			String dos=rs.getString( "DATE_OF_SHIPMENT" ) == null ? "" : rs.getString( "DATE_OF_SHIPMENT" );
			String expd=rs.getString( "EXPIRE_DATE" ) == null ? "" : rs.getString( "EXPIRE_DATE" );
			String po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" );
			String pol=rs.getString( "PO_LINE" ) == null ? "" : rs.getString( "PO_LINE" );
			String qty=rs.getString( "QUANTITY_RECEIVED" ) == null ? "" : rs.getString( "QUANTITY_RECEIVED" );
			String itemid=rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" );
			String sup=rs.getString( "SUPPLIER_NAME" ) == null ? "" : rs.getString( "SUPPLIER_NAME" );
			String pkg=rs.getString( "PACKAGING" ) == null ? "" : rs.getString( "PACKAGING" );
			String mngkitassingl = rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) == null ? "" : rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" );
			String compmatdesc = rs.getString( "MATERIAL_DESC" ) == null ? "" : rs.getString( "MATERIAL_DESC" );
			String mrline = rs.getString( "MR_LINE" ) == null ? "" : rs.getString( "MR_LINE" );
			String doctype = rs.getString( "DOC_TYPE" ) == null ? "" : rs.getString( "DOC_TYPE" );
			String transreqid = rs.getString( "TRANSFER_REQUEST_ID" ) == null ? "" : rs.getString( "TRANSFER_REQUEST_ID" );
      hubNoforlabel = rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" );

			Hashtable hDdata = new Hashtable();
			hDdata.put( "RECEIPT_ID",receiptId);
			hDdata.put( "LINE_DESC",desc);
			hDdata.put( "ITEM_ID",itemid);
			hDdata.put( "EXPIRE_DATE",expd);
			hDdata.put( "USERCHK","Yes");
			largeLabelData.addElement(hDdata);

			if ( "01/01/3000".equalsIgnoreCase( expd ) )
			{
			  expd="Indefinite";
			}

			out.println( "<tr align=\"left\">\n" );
			if ("IT".equalsIgnoreCase( doctype ))
			{
			  out.println( "<td " + Color + "\" width=\"10%\" height=\"38\">TR " + transreqid + "</td>\n" );
			}
			else
			{
			  out.println( "<td " + Color + "\" width=\"10%\" height=\"38\">" + po + "<BR>" + pol + "</td>\n" );
			}
			out.println( "<td " + Color + "l\" width=\"7%\" height=\"38\">" + sup + "</td>\n" );
			if ("Yes".equalsIgnoreCase(supplierpage))
			{
				out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + mrline + "</td>\n" );
			}

			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + itemid + "<BR>" + pkg + "</td>\n" );
			if ( "N".equalsIgnoreCase( mngkitassingl ) )
		    {
			  out.println( "<td " + Color + "l\" width=\"15%\" height=\"38\" >" + compmatdesc + "</td>\n" );
			}
			else
			{
			  out.println( "<td " + Color + "l\" width=\"15%\" height=\"38\" >" + desc + "</td>\n" );
			}
			if ("Yes".equalsIgnoreCase(supplierpage))
			{
			  out.println( "<td " + Color + "\" width=\"7%\" height=\"38\" >" + mfglot + "<BR>" + receiptId + "</td>\n" );
			}
			else
			{
			  out.println( "<td " + Color + "\" width=\"7%\" height=\"38\" >" + mfglot + "<BR>" + Bin + "<BR>" + receiptId + "</td>\n" );
			}

			out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">DOM:" + dom + "<BR>DOS:" + dos + "<BR>DOR:" + dor + "<BR>EXP:" + expd +
						 "</td>\n" );
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + qty + "</td>\n" );
			out.println( "</tr>\n" );
		  }
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		  out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving Show Received Receipts",false ) );
		  return;
		}

		out.println( "</TABLE>\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\"" + hubNoforlabel + "\">\n" );
		if ( i == 0 )
		{
		  out.println( radian.web.HTMLHelpObj.printMessageinTable( "<BR><BR><BR><B>No Items Received</B>" ) );
		}
		session.setAttribute( "REC_LABEL_SEQUENCE_NUMBERS",receiptData );
		session.setAttribute( "LARGE_LABEL_DATA",largeLabelData );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",false ) );
	  return;
	}
	finally
	{
	  db.close();
	}

	out.println( "</FORM></BODY></HTML>\n" );
	//out.println( Msgn );
	out.close();
  }
}