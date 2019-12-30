package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.StringTokenizer;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import oracle.jdbc.OracleTypes;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.helpers.HelpObjs;
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
 *
 *
 * 10-30-02
 * Added a link in tcmbuy history to po to open it.
 *
 * 02-03-03
 * Removing order by from query of po_view
 * 08-08-03 - Code Cleanup and Showing the Original Confirmed Date
 * 02-23-04 - Sorting the TCM buys by radian PO
 * 09-09-04 - New LPP query.
 * 02-23-05 - Showing Customer PO in the TCM buy History
 */

public class showTcmBuys
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
    private boolean intcmIsApplication = false;
    private ResourceLibrary res = null; // res means resource.

    public showTcmBuys(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }
     public void doResult(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException
    {
      PrintWriter out=response.getWriter();
      response.setContentType( "text/html" );
      HttpSession session=request.getSession();
      res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));           
      PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
			Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
			{
			 intcmIsApplication = true;
			}

      String lineID=request.getParameter( "lineID" );
      if ( lineID == null )
        lineID="";
      lineID=lineID.trim();

      String Action=request.getParameter( "Action" );
      if ( Action == null )
        Action="";
      Action=Action.trim();

      String itemID=request.getParameter( "itemID" );
      if ( itemID == null )
        itemID="";
      itemID=itemID.trim();

      String opsEntityId=request.getParameter( "opsEntityId" );
      if ( opsEntityId == null )
        opsEntityId="";
      opsEntityId=opsEntityId.trim();

      String shiptoLoc=request.getParameter( "shiptoLoc" );
      if ( shiptoLoc == null )
        shiptoLoc="";
      shiptoLoc=shiptoLoc.trim();

      if ( "showschedulde".equalsIgnoreCase( Action ) )
      {
        buildsendshowschedudle( lineID,itemID,out );
      }
      else if ( "showlpprange".equalsIgnoreCase( Action ) )
      {
        showLppRange( request, opsEntityId, out );
      }
      else if ( "viewforpage".equalsIgnoreCase( Action ) )
      {
        buildsendtcmbuysforpage( lineID,itemID,shiptoLoc,out );
      }
      else if ( "view".equalsIgnoreCase( Action ) )
      {
        buildsendshowtcmbuys( lineID,itemID,shiptoLoc,opsEntityId,out );
      }
	  else if ( "buildBuyOrderTransferPage".equalsIgnoreCase( Action ) )
	  {
		buildBuyOrderTransferPage(request,out);
	  }
	  else if ( "requestTransfer".equalsIgnoreCase( Action ) )
	  {
		requestTransfer(request,out);
	  }


      out.close();
  }

  public void requestTransfer(HttpServletRequest request,PrintWriter poout) {
	try {
	  Vector args = new Vector(4);
	  args.addElement(request.getParameter("prNumber").toString());
	  args.addElement(request.getParameter("consolidatingQty").toString());
	  args.addElement(request.getParameter("consolidatingInventory").toString());
	  args.addElement("error_val");
	  String error = db.doProcedureWithErrorMsg("p_buy_order_transfer",args,4);

	  //*mn-start
	  long transferID = 0;
	  transferID = Long.parseLong(error);

	  poout.println("<HTML>\n<BODY>\n");
	  if (transferID>0) {
		poout.println("<SCRIPT>\n");
		poout.println("  openrWin = window.opener;\n");
		poout.println("  realParent = openrWin.parent;\n");
		poout.println("  realParent.search(\"blah\");");
		poout.println("  realParent.document.purchasereq.submit();\n");
		poout.println("  window.close();\n");
		poout.println("</SCRIPT>\n");
	  } else {
		poout.println("<CENTER>\n");
		poout.println("<H3>"+res.getString("msg.errortransfer")+"</H3>\n");
		poout.println("<INPUT TYPE=\"BUTTON\" VALUE=\"Close\" onClick=\"window.close();\">\n");
		poout.println("</CENTER>");
	  }
	  poout.println("</BODY>\n</HTML>\n");
	  //*mn-end

	  //System.out.println("Error Message from procedure: "+error);
	}catch (Exception ee) {
	  ee.printStackTrace();
	  poout.println("<CENTER>\n");
	  poout.println("<H3>"+res.getString("msg.errortransfer")+"</H3>\n");
	  poout.println("<INPUT TYPE=\"BUTTON\" VALUE=\"Close\" onClick=\"window.close();\">\n");
	  poout.println("</CENTER>");
	}
  } //end of method

  public void buildBuyOrderTransferPage( HttpServletRequest request,PrintWriter poout)
  {
	nematid_Servlet=bundle.getString( "PURCHASE_ORDER_REQUEST_TRANSFER" );
    poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "title.purchaseordertra","purchasereq.js",intcmIsApplication,res ) );
	poout.println("<BODY>\n");
	poout.println("<TABLE BORDER=0 WIDTH=100% >\n");
	poout.println("<TR VALIGN=\"TOP\">\n");
	poout.println("<TD WIDTH=\"200\">\n");
	poout.println("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
	poout.println("</TD>\n");
	poout.println("<TD WIDTH=\"525\" ALIGN=\"right\">\n");
	poout.println("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
	poout.println("</TD>\n");
	poout.println("</TR>\n");
	poout.println("<TR><TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#000066\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
	poout.println("<FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#FFFFFF\">\n");
	poout.println("<B>"+res.getString("transferRequest")+"</B>\n");
	poout.println("</FONT>\n");
	poout.println("</TD></TR>\n");
	poout.println("</TABLE>\n");
	poout.println( "<FORM METHOD=\"POST\" name=\"buyOrderTransfer\" action=\""+nematid_Servlet+"Action=requestTransfer\" onsubmit =\"return auditRequestTransfer(this)\">\n" );
	//header info
	buildBuyOrderTransferPageHeader(request,poout);
	//detail info
	buildBuyOrderTransferDetail(request,poout);
	//button
	poout.println("<TABLE BORDER=0 WIDTH=100% >\n");
	//add couple of blank row here
	poout.println("<TR><TD WIDTH=\"30%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	poout.println("&nbsp;\n");
	poout.println("</TD>\n");
	poout.println("</TR>\n");
	//blank row 2
	poout.println("<TR><TD WIDTH=\"30%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	poout.println("&nbsp;\n");
	poout.println("</TD>\n");
	poout.println("</TR>\n");
	//process
	poout.println("<TR><TD WIDTH=\"30%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
    poout.println("<INPUT type=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\""+res.getString("label.process")+"\" name=\"requestTransfer\">\n");
	poout.println("</TD>\n");
	//cancel
	poout.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	poout.println("<INPUT type=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"requestTransferCancel\" value=\""+res.getString("label.cancel")+"\" OnClick=\"cancel()\">\n" );
	poout.println("</TD>\n");
	poout.println("</TR>\n");
    //
	poout.println("</FORM>\n");
	poout.println("</FONT></BODY>\n</HTML>\n");
	return;
  } //end of method

  public void buildBuyOrderTransferPageHeader (HttpServletRequest request,PrintWriter poout) {
	poout.println("<TABLE BORDER=0 WIDTH=100% >\n");
	//row 1
    //purchase request
	String tmp = request.getParameter("prNumber");
	if (tmp == null) {
	  tmp = "&nbsp;";
	}
	poout.println("<TR><TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
	poout.println("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
	poout.println("<B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+res.getString("label.purchaserequest")+": </B>\n");
	poout.println("<INPUT CLASS=\"INVISIBLEHEADBLUE\" type=\"text\" name=\"prNumber\" value=\""+tmp+"\" SIZE=\"15\">");
	poout.println("</FONT>\n");
	poout.println("</TD>\n");
	//qty
	tmp = request.getParameter("qty");
	if (tmp == null) {
	  tmp = "&nbsp;";
	}
	poout.println("<TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
	poout.println("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
	poout.println("<B>"+res.getString("label.buyquantity")+": </B>\n");
	poout.println("<INPUT CLASS=\"INVISIBLEHEADBLUE\" type=\"text\" name=\"buyQty\" value=\""+tmp+"\" SIZE=\"15\">");
	poout.println("</FONT>\n");
	poout.println("</TD>\n");
	poout.println("</TR>\n");
	//row 2
	//ship to
	tmp = request.getParameter("shipTo");
	if (tmp == null) {
	  tmp = "&nbsp;";
	}
	poout.println("<TR><TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#FFFFFF\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
	poout.println("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
	poout.println("<B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+res.getString("label.shipto")+": </B>\n");
	poout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"shipTo\" value=\""+tmp+"\" SIZE=\"15\">");
	poout.println("</FONT>\n");
	poout.println("</TD>\n");
	//inventory
	tmp = request.getParameter("inventory");
	if (tmp == null) {
	  tmp = "&nbsp;";
	}
	poout.println("<TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#FFFFFF\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
	poout.println("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
	poout.println("<B>"+res.getString("label.inventorygroup")+": </B>\n");
	poout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"inventory\" value=\""+tmp+"\" SIZE=\"15\">");
	poout.println("</FONT>\n");
	poout.println("</TD>\n");
	poout.println("</TR>\n");
	//row 3
	//item
	tmp = request.getParameter("item");
	if (tmp == null) {
	  tmp = "&nbsp;";
	}
	poout.println("<TR><TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
	poout.println("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
	poout.println("<B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+res.getString("label.itemid")+": </B>\n");
	poout.println("<INPUT CLASS=\"INVISIBLEHEADBLUE\" type=\"text\" name=\"item\" value=\""+tmp+"\" SIZE=\"15\">");
	poout.println("</FONT>\n");
	poout.println("</TD>\n");
	//item type
	tmp = request.getParameter("type");
	if (tmp == null) {
	  tmp = "&nbsp;";
	}
	poout.println("<TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
	poout.println("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
	poout.println("<B>"+res.getString("label.buyordertype")+": </B>\n");
	poout.println("<INPUT CLASS=\"INVISIBLEHEADBLUE\" type=\"text\" name=\"type\" value=\""+tmp+"\" SIZE=\"15\">");
	poout.println("</FONT>\n");
	poout.println("</TD>\n");
	poout.println("</TR>\n");
	poout.println("</TABLE>\n");
  } //end of method

  public void buildBuyOrderTransferDetail (HttpServletRequest request,PrintWriter poout) {
	poout.println("<TABLE BORDER=0 WIDTH=100% >\n");
	String tmpSelect = "";
	String preSelect = "";
	//row 1
	String tmp = request.getParameter("inventory");
	if (tmp == null) {
	  tmp = "&nbsp;";
	}
	poout.println("<TR><TD WIDTH=\"30%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	poout.println("<B>"+res.getString("label.oriinvgroup")+":</B>\n");
	poout.println("</TD>\n");
	poout.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	poout.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"originalInventory\" value=\""+tmp+"\" SIZE=\"20\">");
	poout.println("</TD>\n");
    //qty
	tmpSelect = "";
	poout.println("<TD WIDTH=\"30%\" COLSPAN=\"1\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	poout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"originalQty\" ID=\"originalQty\" value=\""+tmpSelect+"\" size=\"20\">\n");
	poout.println("</TD>\n");
	poout.println("</TR>\n");
	//row 2
	poout.println("<TD WIDTH=\"30%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	poout.println("<B>"+res.getString("label.coninvgroup")+":</B>\n");
	poout.println("</TD>\n");
	poout.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	poout.println("<SELECT CLASS=\"HEADER\" NAME=\"consolidatingInventory\" ID=\"consolidatingInventory\" size=\"1\" >\n");
	poout.println("<OPTION VALUE=\"None\">"+res.getString("label.selectOne")+"</OPTION>\n");
	tmp = request.getParameter("transferRoute");
	StringTokenizer st = new StringTokenizer(tmp,",");
	while (st.hasMoreTokens()) {
	  String invent = st.nextElement().toString();
	  preSelect = "";
	  if (tmpSelect.equalsIgnoreCase(invent)) {
		preSelect = "selected";
	  }
	  poout.println("<option "+preSelect+" value=\""+invent+"\">"+invent+"</option>\n");
	}
	poout.println("</SELECT>\n");
	poout.println("</TD>\n");
	//qty
	tmpSelect = "";
	poout.println("<TD WIDTH=\"30%\" COLSPAN=\"1\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	poout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"consolidatingQty\" ID=\"consolidatingQty\" value=\""+tmpSelect+"\" size=\"20\">\n");
	poout.println("</TD>\n");
	poout.println("</TR>\n");

	poout.println("</TABLE>\n");
  } //end of method



  public void buildsendshowtcmbuys( String lineID,String itemID,String shiptoLoc1,String opsEntityId,PrintWriter poout)
  {
    //StringBuffer Msgn=new StringBuffer();
    //StringBuffer Msgbody=new StringBuffer();
    nematid_Servlet=bundle.getString( "PURCHASE_ORDER_SHOWTCMBUYS" );
    poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.purordertcmbuy","",intcmIsApplication,res ) );
    poout.println("<script type=\"text/javascript\" src=\"/js/common/commonutil.js\"></script>");
    printJavaScripts(poout);
    poout.println( "// -->\n </SCRIPT>\n" );


    //StringBuffer Msgtemp=new StringBuffer();
    poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    poout.println( "<!--\n" );
    poout.println( "function sendSupplierData()\n" );
    poout.println( "{\n" );
    radian.web.poHelpObj.buildshowTcmBuys( db,lineID,itemID,"",shiptoLoc1,true,opsEntityId,poout,res );
    poout.println( "window.close();\n" );
    poout.println( " }\n" );
    poout.println( "// -->\n</SCRIPT>\n" );
    //poout.println( Msgtemp );
    poout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	poout.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
    poout.println( "<CENTER><BR><BR>\n" );
    poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\""+res.getString("label.ok")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    poout.println( "</FORM></BODY></HTML>\n" );
    //poout.println( Msgbody );

    return;
  }

  public void showLppRange( HttpServletRequest request1, String opsEntityId, PrintWriter poout )
  {
    DBResultSet dbrs=null;
    ResultSet rs=null;

    String itemId=request1.getParameter( "itemID" );
    if ( itemId == null )
      itemId="";

    if ( "".equals(opsEntityId))
    	opsEntityId="HAASTCMUSA";

	String invengrp=request1.getParameter( "invengrp" );
	if ( invengrp == null )
	  invengrp="";

	String companyID=request1.getParameter( "companyID" );
	if ( companyID == null )
	  companyID="";

	String mrnumber=request1.getParameter( "mrnumber" );
	if ( mrnumber == null )
	  mrnumber="";

    String lineitem=request1.getParameter( "lineitem" );
    if ( lineitem == null )
      lineitem="";

	String currency=request1.getParameter( "currency" );
	if ( currency == null )
	  currency="";

    //System.out.println( "itemId     " + itemId + "   mainshipTocompanyId     " + mainshipTocompanyId + "    shipTo   " + shipTo + "" );
    //StringBuffer Msgl=new StringBuffer();
    Connection connect1=null;
    CallableStatement cs=null;

    poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.lppdetailsitem","",intcmIsApplication,res ) );
    printJavaScripts(poout);
    poout.println( "// -->\n </SCRIPT>\n" );
    poout.println( "<BODY>\n" );
    poout.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
    poout.println( "<TR><TD WIDTH=\"100%\" CLASS=\"announce\" ALIGN=\"LEFT\" COLSPAN=\"4\"><B>"+res.getString("label.lppdetailsitem")+"" + itemId + "</B></TD>\n" );
    poout.println( "</TR>\n" );
    poout.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
    poout.println( "<TR>\n" );
    poout.println( "<TH WIDTH=\"5%\">"+res.getString("label.company")+"</TH>\n" );
    poout.println( "<TH WIDTH=\"10%\">"+res.getString("label.catalog")+"</TH>\n" );
    poout.println( "<TH WIDTH=\"10%\">"+res.getString("label.partnumber")+"</TH>\n" );
	poout.println( "<TH WIDTH=\"5%\">"+res.getString("label.partgroupnumber")+"</TH>\n" );
	poout.println( "<TH WIDTH=\"5%\">"+res.getString("label.pricegroupid")+"</TH>\n" );
    poout.println( "<TH WIDTH=\"10%\">"+res.getString("label.baselineprice")+"</TH>\n" );
    poout.println( "</TR></TABLE>\n" );

    //open scrolling table
    poout.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
    poout.println( "<TBODY>\n" );
    poout.println( "<TR>\n" );
    poout.println( "<TD VALIGN=\"TOP\">\n" );
    poout.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );

    //Write code to insert rows here
    poout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

    int totalrecords=0;
    int count=0;

    try
    {
      connect1=db.getConnection();
      cs=connect1.prepareCall( "{call pkg_po.pr_bo_baseline_query(?,?,?,?,?,?,?,?)}" );

	  cs.setString( 1,companyID );

	  if ( mrnumber.length() > 0 )
	  {
		cs.setBigDecimal( 2,new BigDecimal(""+ mrnumber +"") );
	  }
	  else
	  {
		cs.setNull( 2,OracleTypes.INTEGER );
	  }

	  cs.setString( 3,lineitem );

	  if ( itemId.length() > 0 )
	  {
		cs.setInt( 4,Integer.parseInt( itemId ) );
	  }
	  else
	  {
		cs.setNull( 4,OracleTypes.INTEGER );
	  }

	  cs.setString( 5,invengrp );
	  cs.setString( 6,currency );
	  cs.setString( 7,opsEntityId );
	  cs.registerOutParameter( 7,OracleTypes.CURSOR );
	  cs.registerOutParameter( 8,OracleTypes.VARCHAR );

      cs.execute();

      String queryfromproc=cs.getObject( 8 ).toString();

      dbrs=db.doQuery( queryfromproc );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        totalrecords+=1;
        count+=1;

        String company_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
        String catalog_id=rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ).trim();
        String cat_part_no=rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ).trim();
        String part_group_no = rs.getString("PART_GROUP_NO")==null?"":rs.getString("PART_GROUP_NO").trim();
        String part_group_id = rs.getString("PRICE_GROUP_ID")==null?"":rs.getString("PRICE_GROUP_ID").trim();
        String unit_price=rs.getString( "BASELINE_PRICE" ) == null ? "" : rs.getString( "BASELINE_PRICE" ).trim();

        String Color=" ";
        if ( count % 2 == 0 )
        {
          Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
        }
        else
        {
          Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
        }

        poout.println( "<TR ALIGN=\"MIDDLE\" BORDERCOLOR=\"black\">\n" );
        poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
        poout.println( company_id );
        poout.println( "</TD>\n" );
        poout.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
        poout.println( catalog_id );
        poout.println( "</TD>\n" );
        poout.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
        poout.println( cat_part_no );
        poout.println( "</TD>\n" );
		poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
		poout.println( part_group_no );
		poout.println( "</TD>\n" );
		poout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
		poout.println( part_group_id );
		poout.println( "</TD>\n" );
        poout.println( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
        poout.println( unit_price );
        poout.println( "</TD>\n" );
        poout.println( "</TR>\n" );

      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      poout.println( res.getString("err.querydatabase") );
      poout.println( "</BODY>\n</HTML>\n" );
      return;
    }
    finally
    {
       if (dbrs != null)  { dbrs.close(); }
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

    if ( totalrecords == 0 )
    {
      poout.println( "<TR><TD>"+res.getString("label.norecordfound")+"</TD></TR>\n" );
    }
    poout.println( "</TABLE>\n" );

    //close scrolling table
    poout.println( "</DIV>\n" );
    poout.println( "</TD>\n" );
    poout.println( "</TR>\n" );
    poout.println( "</TBODY>\n" );
    poout.println( "</TABLE>\n" );
    poout.println( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\""+res.getString("label.ok")+"\" OnClick=\"cancel()\" type=\"button\">\n" );
    poout.println( "</FORM></BODY></HTML>\n" );

    return;
  }

  public void buildsendshowschedudle( String lineID,String prnumber,PrintWriter poout )
  {
    //StringBuffer Msgtemp=new StringBuffer();
    DBResultSet dbrs=null;
    ResultSet rs=null;

    poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.purordertcmbuy","",intcmIsApplication,res ) );
    poout.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );
    printJavaScripts(poout);
    poout.println( "// -->\n </SCRIPT>\n" );
    poout.println( "<BODY>\n" );
    poout.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Delivery Schedule For MR-Line : " + prnumber + "-" + lineID + "" ) );
    if (intcmIsApplication)
    {
        poout.println("<b>Delivery Schedule For MR-Line : " + prnumber + "-" + lineID + "</b>" );
    }
    int count=0;

    try
    {
      //open scrolling table
      poout.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      poout.println( "<TBODY>\n" );
      poout.println( "<TR>\n" );
      poout.println( "<TD VALIGN=\"TOP\" COLSPAN=\"17\">\n" );
      poout.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column450\">\n" );
      poout.println( "<TABLE ID=\"tcmbuytable" + lineID + "\"BORDER=\"0\" BGCOLOR=\"#999999\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );

      String itemnotesquery="select COMPANY_ID,PR_NUMBER,LINE_ITEM,DATE_TO_DELIVER,QTY,QTY_OPEN,to_char(DATE_TO_DELIVER,'mm/dd/yyyy') DATE_TO_DELIVER1,COLOR from open_schedule where PR_NUMBER = " +  prnumber + " and LINE_ITEM = " + lineID + " order by DATE_TO_DELIVER asc";
      dbrs=db.doQuery( itemnotesquery );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        //String company_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
        //String pr_number=rs.getString( "PR_NUMBER" ) == null ? "" : rs.getString( "PR_NUMBER" ).trim();
        //String line_item=rs.getString( "LINE_ITEM" ) == null ? "" : rs.getString( "LINE_ITEM" ).trim();
        String date_to_deliver=rs.getString( "DATE_TO_DELIVER1" ) == null ? "" : rs.getString( "DATE_TO_DELIVER1" ).trim();
        String qty=rs.getString( "QTY" ) == null ? "" : rs.getString( "QTY" ).trim();
        //String qty_open=rs.getString( "QTY_OPEN" ) == null ? "" : rs.getString( "QTY_OPEN" ).trim();
        String color=rs.getString( "COLOR" ) == null ? "" : rs.getString( "COLOR" ).trim();

        boolean createHdr=false;

        if ( count % 10 == 0 )
        {
          createHdr=true;
        }

        count+=1;
        if ( createHdr )
        {
          poout.println( "<TR>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.deliverydate")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.qty")+"</B></TH>\n" );
          poout.println( "</TR>\n" );
        }

        String Color=" ";
        if ( "1".equalsIgnoreCase( color ) )
        {
          Color="CLASS=\"green";
        }
        else if ( "2".equalsIgnoreCase( color ) )
        {
          Color="CLASS=\"red";
        }
        else if ( "3".equalsIgnoreCase( color ) )
        {
          Color="CLASS=\"yellow";
        }
        else
        {
          Color="CLASS=\"Inwhite";
        }

        //Buyer,Supplier,Order Date not clear,
        poout.println( "<TR>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + date_to_deliver + "</TD>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + qty + "</TD>\n" );
        poout.println( "</TR>\n" );
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

    if ( count == 0 )
    {
      poout.println( "<TR>\n" );
      poout.println( "<TD CLASS=\"Inwhite\" WIDTH=\"5%\" COLSPAN=\"11\">"+res.getString("label.norecordfound")+"</TD>\n" );
      poout.println( "</TR>\n" );
    }

    poout.println( "</TABLE>\n" );
    //close scrolling table
    poout.println( "</DIV>\n" );
    poout.println( "</TD>\n" );
    poout.println( "</TR>\n" );
    poout.println( "</TBODY>\n" );
    poout.println( "</TABLE>\n" );
    poout.println( "<BR><LEFT>&nbsp;&nbsp;Green&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;"+res.getString("label.needtobuy")+"</LEFT>\n" );
    poout.println( "<BR><LEFT>&nbsp;&nbsp;Red&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;"+res.getString("label.outsidelookaheadwin")+"</LEFT>\n" );
    poout.println( "<BR><LEFT>&nbsp;&nbsp;Yellow&nbsp;&nbsp;-&nbsp;&nbsp;"+res.getString("label.allofromstockor")+"</LEFT>\n" );
    poout.println( "<BR><CENTER>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\""+res.getString("label.ok")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    poout.println( "</BODY></HTML>\n" );

    return;
  }

  public void buildsendtcmbuysforpage( String lineID,String itemID,String shiptoLoc2,PrintWriter poout )
  {
    //StringBuffer Msgtemp=new StringBuffer();
    DBResultSet dbrs=null;
    ResultSet rs=null;

    poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.purordertcmbuy","",intcmIsApplication,res ) );
    poout.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/buypage.css\"></LINK>\n" );
    printJavaScripts(poout);
    poout.println( "// -->\n </SCRIPT>\n" );
    poout.println( "<BODY>\n" );
    poout.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( res.getString("label.haastcmbuyhistoryforitem")+": " + itemID + "" ) );
    int count=0;

    try
    {
      //open scrolling table
      poout.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      poout.println( "<TBODY>\n" );
      poout.println( "<TR>\n" );
      poout.println( "<TD VALIGN=\"TOP\" COLSPAN=\"17\">\n" );
      poout.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column450\">\n" );
      poout.println( "<TABLE ID=\"tcmbuytable" + lineID + "\"BORDER=\"0\" BGCOLOR=\"#999999\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );

      String itemnotesquery="select CUSTOMER_PO,CURRENCY_ID,to_char(ORIGINAL_DATE_CONFIRMED,'mm/dd/yyyy hh:mi AM') ORIGINAL_DATE_CONFIRMED,RADIAN_PO,CARRIER,SHIP_TO_COMPANY_ID,SHIP_TO_LOCATION_ID,SUPPLIER_CONTACT_NAME,PHONE,to_char(FIRST_TIME_RECEIVED,'mm/dd/yyyy') FIRST_TIME_RECEIVED,BUYER,BUYER_NAME,SUPPLIER,SUPPLIER_NAME,PO_LINE,BRANCH_PLANT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(DATE_SENT,'mm/dd/yyyy') DATE_SENT,DATE_CONFIRMED,to_char(DATE_CONFIRMED,'mm/dd/yyyy hh:mi AM') DATE_CONFIRMED1,QUANTITY_RECEIVED from po_view where item_id = " + itemID + " order by radian_po desc";
      dbrs=db.doQuery( itemnotesquery );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        String radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
        //String po_line=rs.getString( "PO_LINE" ) == null ? "" : rs.getString( "PO_LINE" ).trim();
        //String branch_plant=rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ).trim();
        //String item_id=rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ).trim();
        String quantity=rs.getString( "QUANTITY" ) == null ? "" : rs.getString( "QUANTITY" ).trim();
        String unit_price=rs.getString( "UNIT_PRICE" ) == null ? "" : rs.getString( "UNIT_PRICE" ).trim();

        double unitPrice=0.00;
        if ( unit_price.length() > 0 )
        {
          unitPrice=Double.valueOf( unit_price ).doubleValue();
        }

        //String date_sent=rs.getString( "DATE_SENT" ) == null ? "" : rs.getString( "DATE_SENT" ).trim();
        String date_confirmed=rs.getString( "DATE_CONFIRMED1" ) == null ? "" : rs.getString( "DATE_CONFIRMED1" ).trim();
        String orig_date_confirmed=rs.getString( "ORIGINAL_DATE_CONFIRMED" ) == null ? "" : rs.getString( "ORIGINAL_DATE_CONFIRMED" ).trim();
        String quantity_received=rs.getString( "QUANTITY_RECEIVED" ) == null ? "" : rs.getString( "QUANTITY_RECEIVED" ).trim();
        //String buyer=rs.getString( "BUYER" ) == null ? "" : rs.getString( "BUYER" ).trim();
        String buyername=rs.getString( "BUYER_NAME" ) == null ? "" : rs.getString( "BUYER_NAME" ).trim();
        String suppliername=rs.getString( "SUPPLIER_NAME" ) == null ? "" : rs.getString( "SUPPLIER_NAME" ).trim();
        //String supplier=rs.getString( "SUPPLIER" ) == null ? "" : rs.getString( "SUPPLIER" ).trim();
        String ship_to_location_id=rs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "" : rs.getString( "SHIP_TO_LOCATION_ID" ).trim();
        String supplier_contact_name=rs.getString( "SUPPLIER_CONTACT_NAME" ) == null ? "" : rs.getString( "SUPPLIER_CONTACT_NAME" ).trim();
        String first_time_received=rs.getString( "FIRST_TIME_RECEIVED" ) == null ? "" : rs.getString( "FIRST_TIME_RECEIVED" ).trim();
        //String ship_to_company_id=rs.getString( "SHIP_TO_COMPANY_ID" ) == null ? "" : rs.getString( "SHIP_TO_COMPANY_ID" ).trim();
        String phone=rs.getString( "PHONE" ) == null ? "" : rs.getString( "PHONE" ).trim();
        String carrier=rs.getString( "CARRIER" ) == null ? "" : rs.getString( "CARRIER" ).trim();
		String currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();
		String customerPo=rs.getString( "CUSTOMER_PO" ) == null ? "" : rs.getString( "CUSTOMER_PO" ).trim();

        boolean createHdr=false;

        if ( count % 10 == 0 )
        {
          createHdr=true;
        }

        count+=1;
        if ( createHdr )
        {
          poout.println( "<TR>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.buyer")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.pon")+"</B></TH>\n" );
 	      poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.cuspon")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.shipto")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.carrier")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"15%\" HEIGHT=\"25\"><B>"+res.getString("label.supplier")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.ordertakenby")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.phoneno")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.qty")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.orderdate")+"<BR>("+res.getString("label.lastconfirmed")+")</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.originalconfirmdate")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.unitprice")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.receivedate")+"</B></TH>\n" );
          poout.println( "<TH WIDTH=\"5%\" HEIGHT=\"25\"><B>"+res.getString("label.qtyrecvd")+"</B></TH>\n" );
          poout.println( "</TR>\n" );
        }

        String Color=" ";
        if ( count % 2 == 0 )
        {
          Color="CLASS=\"Inblue";
        }
        else
        {
          Color="CLASS=\"Inwhite";
        }

        if ( ship_to_location_id.equalsIgnoreCase( shiptoLoc2 ) )
        {
          Color="CLASS=\"green";
        }

        //Buyer,Supplier,Order Date not clear,
        poout.println( "<TR>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + buyername + "</TD>\n" );

        String wwwHome=bundle.getString( "WEB_HOME_PAGE" );
        String radianpourl=wwwHome + "tcmIS/supply/purchorder?po=" + radian_po + "&Action=searchlike&subUserAction=po";

        poout.println( "<td " + Color + "\" width=\"5%\"><A onclick=\"javascript:window.open('" + radianpourl + "')\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\">" + radian_po + "</A></td>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + customerPo + "</TD>\n" );
		poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + ship_to_location_id + "</TD>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + carrier + "</TD>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"15%\">" + suppliername + "</TD>\n" );
        poout.println( "<TD " + Color + "\" WIDTH=\"5%\">" + supplier_contact_name + "</TD>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + phone + "</TD>\n" );
        poout.println( "<TD " + Color + "\" WIDTH=\"5%\">" + quantity + "</TD>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + date_confirmed + "</TD>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + orig_date_confirmed + "</TD>\n" );
        poout.println( "<TD " + Color + "\" WIDTH=\"5%\">" + unit_price + " "+currencyid+"</TD>\n" );
        poout.println( "<TD " + Color + "l\" WIDTH=\"5%\">" + first_time_received + "</TD>\n" );
        poout.println( "<TD " + Color + "\" WIDTH=\"5%\">" + quantity_received + "</TD>\n" );
        poout.println( "</TR>\n" );
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

    if ( count == 0 )
    {
      poout.println( "<TR>\n" );
      poout.println( "<TD CLASS=\"Inwhite\" WIDTH=\"5%\" COLSPAN=\"14\">"+res.getString("label.norecordfound")+"</TD>\n" );
      poout.println( "</TR>\n" );
    }

    poout.println( "</TABLE>\n" );

    //close scrolling table
    poout.println( "</DIV>\n" );
    poout.println( "</TD>\n" );
    poout.println( "</TR>\n" );
    poout.println( "</TBODY>\n" );
    poout.println( "</TABLE>\n" );
    poout.println( "<BR><CENTER>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\""+res.getString("label.ok")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    poout.println( "</BODY></HTML>\n" );

    return;
  }

  protected void printJavaScripts(PrintWriter poout)
  {
    //StringBuffer Msglt=new StringBuffer();

   poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
   poout.println( "<!--\n" );
   poout.println( "function cancel()\n" );
   poout.println( "{\n" );
   poout.println( "window.close();\n" );
   poout.println( "}\n" );

    return;
  }
}