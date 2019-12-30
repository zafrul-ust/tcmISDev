package radian.web.servlets.ray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */


public class rayshowReceivedReceipts   extends HttpServlet   implements SingleThreadModel
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
  public void doGet( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
	doPost( request,response );
  }

   public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {

	PrintWriter out=response.getWriter();
	response.setContentType( "text/html" );
	TcmISDBModel db = null;
	HttpSession session=request.getSession();
	PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
	boolean intcmIsApplication = false;
	if (personnelBean !=null)
	{
		 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
			intcmIsApplication = true;
		 }
	}


	String personnelId="";
	StringBuffer Msgn=new StringBuffer();

	try
	{
	  db=new RayTcmISDBModel( "Ray" );
	  if ( db == null )
	  {
		PrintWriter out2=new PrintWriter( response.getOutputStream() );
		out2.println( "Starting DB" );
		out2.println( "DB is null" );
		out2.close();
		return;
	  }

	  ServerResourceBundle bundleray=new RayServerResourceBundle();
	  HeaderFooter hf=new HeaderFooter( bundleray,db );

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
		session.setAttribute( "clientloginState","challenge" );
		out.println( hf.printclientLoginScreen( errormsg,"Receiving","tcmisclientreceiving.gif",intcmIsApplication ) );
		out.flush();
	  }
	  else
	  {
		Vector data= ( Vector ) session.getAttribute( "DATA" );

		String genLabels="";
		genLabels=BothHelpObjs.makeSpaceFromNull( request.getParameter( "genLabels" ) );

		String Hub=BothHelpObjs.makeSpaceFromNull( request.getParameter( "HubNoforlabel" ) );
		if ( Hub == null )
		  Hub="0";

		Msgn.append( "<HTML>\n" );
		Msgn.append( "<HEAD>\n" );
		Msgn.append( "<TITLE>Received Items</TITLE>\n" );
		Msgn.append( "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n" );
		Msgn.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
		Msgn.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
		Msgn.append( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
		//Msgn.append("<SCRIPT SRC=\"/clientscripts/showConfirmedReceipts.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
		Msgn.append( "<SCRIPT SRC=\"/clientscripts/dropshipreceiving.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
		Msgn.append( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n" );
		Msgn.append( "<!-- \n" );

		Msgn.append( "window.onbeforeprint =\n" );
		Msgn.append( "  function () {\n" );
		Msgn.append( "    document.body.style.marginLeft =\n" );
		Msgn.append( "    document.body.style.marginRight = '20px';\n" );
		Msgn.append( "    document.body.style.border = '2px solid lime';\n" );
		Msgn.append( "    document.all.printLink.style.display = 'none';\n" );
		Msgn.append( "    document.all.option1.style.display = 'none';\n" );
		Msgn.append( "    document.all.option2.style.display = 'none';\n" );
		Msgn.append( "    document.all.option3.style.display = 'none';\n" );
		Msgn.append( "    document.all.option4.style.display = 'none';\n" );
		Msgn.append( "    document.all.genbutton1.style.display = 'none';\n" );
		Msgn.append( "    document.all.genbutton2.style.display = 'none';\n" );
		Msgn.append( " };\n" );

		Msgn.append( "window.onafterprint =\n" );
		Msgn.append( "  function () {\n" );
		Msgn.append( "    document.body.style.marginLeft =\n" );
		Msgn.append( "    document.body.style.marginRight = '';\n" );
		Msgn.append( "    document.body.style.border = '';\n" );
		Msgn.append( "    document.all.printLink.style.display = '';\n" );
		Msgn.append( "    document.all.option1.style.display = '';\n" );
		Msgn.append( "    document.all.option2.style.display = '';\n" );
		Msgn.append( "    document.all.option3.style.display = '';\n" );
		Msgn.append( "    document.all.option4.style.display = '';\n" );
		Msgn.append( "    document.all.genbutton1.style.display = '';\n" );
		Msgn.append( "    document.all.genbutton2.style.display = '';\n" );
		Msgn.append( " };\n" );

		Msgn.append( "//-->     \n" );
		Msgn.append( "</SCRIPT>  \n" );
		Msgn.append( "</HEAD>\n" );
		Msgn.append( "<BODY>\n" );

		Msgn.append( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
		Msgn.append( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
		Msgn.append( "</DIV>\n" );
		Msgn.append( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

		Msgn.append( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Received Items\n" ) );

		Msgn.append( "<FORM method=\"POST\" NAME=\"dropshipreceiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"/tcmIS/ray/Dropship?session=Active&generate_labels=yes&UserAction=GenerateLabels\">\n" );
		Msgn.append( "<INPUT TYPE=\"hidden\" NAME=\"contBin\" VALUE=\" \">\n" );
		Msgn.append( "<INPUT TYPE=\"hidden\" NAME=\"Paper\" VALUE=\"31\">\n" );
		Msgn.append( "<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\"" + Hub + "\">\n" );

		//start table #1
		Msgn.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"600\" CLASS=\"moveup\">\n" );
		Msgn.append( "<TR VALIGN=\"MIDDLE\">\n" );
		if ( genLabels.equalsIgnoreCase( "1" ) )
		{
		  //Options
		  Msgn.append( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
		  Msgn.append( "<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('31')\" value=\"31\" checked>\n" );
		  Msgn.append( "</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option3\">3''/1''&nbsp;</TD>\n" );
		  Msgn.append( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
		  Msgn.append( "<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('811')\" value=\"811\">\n" );
		  Msgn.append( "</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">8.5''/11''</TD>\n\n" );
		  //Button
		  Msgn.append( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		  Msgn.append( "   <INPUT TYPE=\"BUTTON\" ID=\"genbutton1\" VALUE=\"Container Labels\" NAME=\"SearchButton\" onclick= \"javascript:doRecPopup(this,'CONTAINER')\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\">&nbsp;\n" );
		  Msgn.append( "</TD>\n" );

		  Msgn.append( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		  Msgn.append( "   <INPUT TYPE=\"BUTTON\" ID=\"genbutton2\" VALUE=\"Bin Labels\" NAME=\"SearchButton\" onclick= \"javascript:doRecPopup(this,'BIN')\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\">&nbsp;\n" );
		  Msgn.append( "</TD>\n" );
		}

		Msgn.append( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
		Msgn.append( "<INPUT TYPE=\"BUTTON\" ID=\"printLink\" ONCLICK=\"javascript: if (window.print) window.print();\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" value=\"Print This Page\">\n" );
		Msgn.append( "</TD>\n" );
		Msgn.append( "</TR></TABLE>\n" );

		Hashtable summary=new Hashtable();
		summary= ( Hashtable ) data.elementAt( 0 );
		int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
		int vsize=data.size();

		Msgn.append( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"600\" CLASS=\"moveup\">\n" );
		Msgn.append( "<tr align=\"left\">\n" );
		Msgn.append( "<TH width=\"2%\"  height=\"38\">PO<BR>Line</TH>\n" );
		Msgn.append( "<TH width=\"7%\"  height=\"38\">Supplier</TH>\n" );
		Msgn.append( "<TH width=\"11%\" height=\"38\">Item<BR>Packaging</TH>\n" );
		Msgn.append( "<TH width=\"15%\" height=\"38\">Description</TH>\n" );
		Msgn.append( "<TH width=\"5%\"  height=\"38\">Lot<BR>Bin<BR>Receipt ID</TH>\n" );
		Msgn.append( "<TH width=\"5%\"  height=\"38\">Dates</TH>\n" );
		Msgn.append( "<TH width=\"5%\"  height=\"38\">Qty Rec'd</TH>\n" );
		Msgn.append( "</tr>\n" );

		int i=0; //used for detail lines
		int lineCount=1;

		for ( int loop=0; loop < total; loop++ )
		{
		  i++;
		  boolean createHdr=false;

		  if ( lineCount % 10 == 0 )
		  {
			createHdr=true;
		  }

		  if ( createHdr )
		  {
			Msgn.append( "<tr align=\"left\">\n" );
			Msgn.append( "<TH width=\"2%\"  height=\"38\">PO<BR>Line</TH>\n" );
			Msgn.append( "<TH width=\"7%\"  height=\"38\">Supplier</TH>\n" );
			Msgn.append( "<TH width=\"11%\" height=\"38\">Item<BR>Packaging</TH>\n" );
			Msgn.append( "<TH width=\"15%\" height=\"38\">Description</TH>\n" );
			Msgn.append( "<TH width=\"5%\"  height=\"38\">Lot<BR>Bin<BR>Receipt ID</TH>\n" );
			Msgn.append( "<TH width=\"5%\"  height=\"38\">Dates</TH>\n" );
			Msgn.append( "<TH width=\"5%\"  height=\"38\">Qty Rec'd</TH>\n" );
			Msgn.append( "</tr>\n" );
		  }

		  Hashtable hD=new Hashtable();
		  hD= ( Hashtable ) data.elementAt( i );

		  // get main information
		  String checkednon="";
		  String Purchase_order= ( hD.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER" ).toString() );
		  String PO_Line= ( hD.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER_LINE" ).toString() );
		  String Item_id= ( hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString() );
		  String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "" : hD.get( "ITEM_DESC" ).toString() );
		  String Pkging= ( hD.get( "PACKING" ) == null ? "&nbsp;" : hD.get( "PACKING" ).toString() );
		  String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
		  String Date_mfgd= ( hD.get( "DATE_MFGD" ) == null ? "&nbsp;" : hD.get( "DATE_MFGD" ).toString() );
		  String Qty_recd= ( hD.get( "QTY_RECD" ) == null ? "&nbsp;" : hD.get( "QTY_RECD" ).toString() );
		  String Sel_bin= ( hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString() );
		  String Oreceipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
		  String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
		  if ( Line_Check.equalsIgnoreCase( "yes" ) )
		  {
			checkednon="checked";
		  }
		  else
		  {
			checkednon="";
		  }
		  //05-14-02
		  String indefshelflie= ( hD.get( "INDEFINITE_SHELF_LIFE" ) == null ? " " : hD.get( "INDEFINITE_SHELF_LIFE" ).toString() );
		  //12-06-01
		  String supplier= ( hD.get( "SUPPLIER" ) == null ? "&nbsp;" : hD.get( "SUPPLIER" ).toString() );
		  String Date_recieved= ( hD.get( "DATE_RECIEVED" ) == null ? "&nbsp;" : hD.get( "DATE_RECIEVED" ).toString() );
		  //12-17-01
		  String Expire_date=hD.get( "EXPIRE_DATE" ).toString();
		  String shipment_date=hD.get( "DATE_OF_SHIPMENT" ).toString();

		  if ( "01/01/3000".equalsIgnoreCase( Expire_date ) )
		  {
			Expire_date="Indefinite";
		  }

		  String Color=" ";
		  if ( i % 2 == 0 )
		  {
			Color="CLASS=\"blue";
		  }
		  else
		  {
			Color="CLASS=\"white";
		  }

		  if ( checkednon.equalsIgnoreCase( "checked" ) )
		  {
			lineCount++;
			Msgn.append( "<tr align=\"left\">\n" );
			Msgn.append( "<td " + Color + "\" width=\"10%\" height=\"38\">" + Purchase_order + "<BR>" + PO_Line + "</td>\n" );
			Msgn.append( "<td " + Color + "l\" width=\"7%\" height=\"38\">" + supplier + "</td>\n" );
			Msgn.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Item_id + "<BR>" + Pkging + "</td>\n" );
			Msgn.append( "<td " + Color + "l\" width=\"15%\" height=\"38\" >" + Item_desc + "</td>\n" );
			Msgn.append( "<td " + Color + "\" width=\"7%\" height=\"38\" >" + Mfg_lot + "<BR>" + Sel_bin + "<BR>" + Oreceipt_id + "</td>\n" );
			Msgn.append( "<td " + Color + "l\" width=\"5%\" height=\"38\">DOM:" + Date_mfgd + "<BR>DOS:" + shipment_date + "<BR>DOR:" + Date_recieved +
						 "<BR>EXP:" + Expire_date + "<BR></td>\n" );
			Msgn.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Qty_recd + "</td>\n" );
			Msgn.append( "</tr>\n" );
		  }

		}
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  //System.out.println("*** Error on Hub Receiving Page ***");
	  out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
	  return;
	}
	//Msgn.append("<BR><P><CENTER>\n");
	//Msgn.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BUTTON name=\"searchlike\" value=\"testing\" OnClick=\"cancel()\" type=\"button\">Close</BUTTON></CENTER></P>\n");
	Msgn.append( "</FORM></BODY></HTML>\n" );
	out.println( Msgn );
	db.close();
	out.close();
  }
}