package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.OracleTypes;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-04-03 - Code clean up and fixed the bug with cell name I changed before
 * 12-17-03 - MAking changes so that additional charges are assinged to item_type which can have add charges. Removing the item_type MA and BG hard coding.
 * 12-22-03 - Making the session Keys Unique to PO pages.
 */


public class soundsLikpoItemId
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private String nematid_Servlet="";
  private Vector addchargeslist = null;
  private Vector secondarysupplierlist = null;
  private PrintWriter out = null;
  private static org.apache.log4j.Logger polog = org.apache.log4j.Logger.getLogger(purchaseOrderCreator.class);
  public soundsLikpoItemId( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    out=response.getWriter();
    response.setContentType( "text/html" );

    String tradeName=request.getParameter( "tradename" );
    if ( tradeName == null )
      tradeName="";
    tradeName=tradeName.trim();

    String validbpo=request.getParameter( "validbpo" );
    if ( validbpo == null )
      validbpo="";
    validbpo=validbpo.trim();

    String sortBy=request.getParameter( "sortBy" );
    if ( sortBy == null )
      sortBy="";
    sortBy=sortBy.trim();

    String shiptoid=request.getParameter( "shiptoid" );
    if ( shiptoid == null )
      shiptoid="";
    shiptoid=shiptoid.trim();

    String searchstring1=request.getParameter( "searchstring1" );
    if ( searchstring1 == null )
      searchstring1="";
    searchstring1=searchstring1.trim();

    String CompanyId=request.getParameter( "Company" );
    if ( CompanyId == null )
      CompanyId="";
    CompanyId=CompanyId.trim();

    String lineID=request.getParameter( "lineID" );
    if ( lineID == null )
      lineID="";
    lineID=lineID.trim();

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action=Action.trim();

    String subUserAction=request.getParameter( "subUserAction" );
    if ( subUserAction == null )
      subUserAction="";
    subUserAction=subUserAction.trim();

    String searchString=request.getParameter( "SearchString" );
    if ( searchString == null )
      searchString="";
    searchString=searchString.trim();

    String itemID=request.getParameter( "itemID" );
    if ( itemID == null )
      itemID="";
    itemID=itemID.trim();

    String invengrp=request.getParameter( "invengrp" );
    if ( invengrp == null )
      invengrp="";
    invengrp=invengrp.trim();

	HttpSession posession = request.getSession();
	addchargeslist = (Vector) posession.getAttribute("PO_ADD_CHARGE_ITEM_TYPE");
	secondarysupplierlist = (Vector) posession.getAttribute("PO_ADD_SECONDARY_SUPPLIER_ITEM_TYPE");

    //System.out.println("Here itemID  " + itemID + " Action   "+Action+"  searchString  "+searchString+" searchString1  "+searchstring1+" lineID "+lineID+"  validbpo "+validbpo);

    if ("okupdate".equalsIgnoreCase(Action))
    {
    buildsendSupplier("",itemID);
    }
    else if ("Search".equalsIgnoreCase(Action))
    {
    buildLikeSupplier(tradeName,lineID,"Search",searchString,CompanyId,itemID,subUserAction,searchstring1,sortBy,shiptoid,validbpo,invengrp);
    }
    else
    {
    buildLikeSupplier(tradeName,lineID,"",searchString,CompanyId,itemID,subUserAction,searchstring1,sortBy,shiptoid,validbpo,invengrp);
    }
    out.close();

  }

  public void buildsendSupplier( String lineID,String item_id )
  {
    //StringBuffer Msgn=new StringBuffer();
    //StringBuffer Msgbody=new StringBuffer();

    nematid_Servlet=bundle.getString( "PURCHASE_ORDER_ITEM" );
    out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Item Id","",false ) );
    printJavaScripts( lineID );
    out.println( "// -->\n </SCRIPT>\n" );

    //StringBuffer Msgtemp=new StringBuffer();

    //Build the Java Script Here and Finish the thing
    out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    out.println( "<!--\n" );
    out.println( "function sendSupplierData()\n" );
    out.println( "{\n" );
    radian.web.poHelpObj.buildsendItemDetails( db,lineID,item_id,false,out );
    out.println( "window.close();\n" );
    out.println( " }\n" );
    out.println( "// -->\n</SCRIPT>\n" );
    //out.println( Msgtemp );
    out.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
    out.println( "<FORM METHOD=\"POST\" id=\"revDateLike\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
    out.println( "<CENTER><BR><BR>\n" );
    out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    out.println( "</FORM></BODY></HTML>\n" );
    //out.println( Msgbody );

    return;
   }

   public void buildLikeSupplier( String materialid,String control_name,String SearchFlag,String SearchString,
                                          String CompanyId1,String MfgID1,String subUserAction,String SearchString1,
                                          String sortBy,String shipto,String validbpo,String invengrp )
   {
     //StringBuffer Msgl=new StringBuffer();
     MfgID1=MfgID1.trim();
     Connection connect1=null;
     CallableStatement cs=null;
     ResultSet rs=null;

     nematid_Servlet=bundle.getString( "PURCHASE_ORDER_ITEM" );

     out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Item Id","",false ) );
     printJavaScripts( control_name );

     out.println( "function ShowSearch(name,entered)\n" );
     out.println( "{\n" );
     out.println( " with (entered)\n" );
     out.println( " {\n" );
     out.println( " loc = \"" + nematid_Servlet + "&Action=\" + name.toString() + \"&SearchString=\";\n" );
     out.println( " loc = loc + window.document.SupplierLike.SearchString.value;\n" );
     out.println( " loc = loc + \"&searchstring1=\" + window.document.SupplierLike.searchstring1.value;\n" );
     out.println( " loc = loc + \"&lineID=" + control_name + "\"; \n" );
     out.println( " loc = loc + \"&subUserAction=" + subUserAction + "\"; \n" );
     out.println( " loc = loc + \"&itemID=\"; \n" );
     out.println( " loc = loc + window.document.SupplierLike.supplierid.value;\n" );
     out.println( " loc = loc + \"&sortBy=\"; \n" );
     out.println( " loc = loc + window.document.SupplierLike.sortBy.value;\n" );
     out.println( " loc = loc + \"&Company=" + CompanyId1 + "\"; \n" );
     out.println( " loc = loc + \"&invengrp=" + invengrp + "\"; \n" );
     String shipto1=HelpObjs.findReplace( shipto,"#","%23" );
     out.println( " loc = loc + \"&shiptoid=" + shipto1 + "\"; \n" );
     out.println( " loc = loc + \"&materialid=" + materialid + "\"; \n" );
     out.println( " loc = loc + \"&validbpo=" + validbpo + "\"; \n" );
     out.println( " }\n" );
     out.println( "  window.location.replace(loc);\n" );
     out.println( " }\n" );
     out.println( "// -->\n </SCRIPT>\n" );

     out.println( "<BODY onload=\"javascript:window.resizeTo(800,600)\">\n" );
     out.println( "<FORM METHOD=\"POST\" id=\"SupplierLike\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"subUserAction\" NAME=\"subUserAction\" VALUE=\"" + subUserAction + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"itemtype\" NAME=\"itemtype\" VALUE=\"\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"itempackaging\" NAME=\"itempackaging\" VALUE=\"\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"mpn\" NAME=\"mpn\" VALUE=\"\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"desc\" NAME=\"desc\" VALUE=\"\">\n" );
	 out.println( "<INPUT TYPE=\"hidden\" ID=\"canassigcharge\" NAME=\"canassigcharge\" VALUE=\"\">\n" );
	 out.println( "<INPUT TYPE=\"hidden\" ID=\"canassignsecondarysupplier\" NAME=\"canassignsecondarysupplier\" VALUE=\"\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"shiptoid\" NAME=\"shiptoid\" VALUE=\"" + shipto + "\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"Company\" NAME=\"Company\" VALUE=\"" + CompanyId1 + "\">\n" );

     out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
     out.println( "<TR>\n" );
     out.println( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text 1:</B></TD>\n" );
     out.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"SearchString\" name=\"SearchString\" value=\"" + SearchString + "\" SIZE=\"40\"></TD>\n" );
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\">&nbsp;</TD>\n" );
     out.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"Search\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n" );
     out.println( "<TR>\n" );
     out.println( "<TR>\n" );
     out.println( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text 2:</B></TD>\n" );
     out.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"searchstring1\" name=\"searchstring1\" value=\"" + SearchString1 + "\" SIZE=\"40\"></TD>\n" );
     if ( "lineCharge".equalsIgnoreCase( subUserAction ) )
     {
       out.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" ALIGN=\"RIGHT\">&nbsp;</TD>\n" );
     }
     else
     {
       out.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" ALIGN=\"RIGHT\"><B>Sort By:</B></TD>\n" );
     }

     out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
     if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
     {
       out.println( "<SELECT CLASS=\"HEADER\" ID=\"sortBy\" NAME=\"sortBy\" size=\"1\">\n" );
       if ( sortBy.equalsIgnoreCase( "ITEM_ID" ) )
       {
         //out.println("<option value=\"SUPPLIER\">Supplier</option>\n");
         out.println( "<option selected value=\"ITEM_ID\">Item Id</option>\n" );
         out.println( "<option value=\"CATALOG_ID,CAT_PART_NO\">Catalog,Part No</option>\n" );
         //out.println("<option value=\"ITEM_DESC\">Item Desc</option>\n");
       }
       else if ( sortBy.equalsIgnoreCase( "ITEM_DESC" ) )
       {
         //out.println("<option value=\"SUPPLIER\">Supplier</option>\n");
         out.println( "<option value=\"ITEM_ID\">Item Id</option>\n" );
         out.println( "<option value=\"CATALOG_ID,CAT_PART_NO\">Catalog,Part No</option>\n" );
         //out.println("<option selected value=\"ITEM_DESC\">Item Desc</option>\n");
       }
       else if ( sortBy.equalsIgnoreCase( "CATALOG_ID,CAT_PART_NO" ) )
       {
         //out.println("<option value=\"SUPPLIER\">Supplier</option>\n");
         out.println( "<option value=\"ITEM_ID\">Item Id</option>\n" );
         out.println( "<option selected value=\"CATALOG_ID,CAT_PART_NO\">Catalog,Part No</option>\n" );
         //out.println("<option value=\"ITEM_DESC\">Item Desc</option>\n");
       }
       else
       {
         //out.println("<option value=\"SUPPLIER\">Supplier</option>\n");
         out.println( "<option value=\"ITEM_ID\">Item Id</option>\n" );
         out.println( "<option value=\"CATALOG_ID,CAT_PART_NO\">Catalog,Part No</option>\n" );
         //out.println("<option selected value=\"ITEM_DESC\">Item Desc</option>\n");
       }
       out.println( "</SELECT>\n" );
     }
	 else
	 {
	   out.println( "<INPUT TYPE=\"hidden\" ID=\"sortBy\" NAME=\"sortBy\" VALUE=\"\">\n" );
	 }
     out.println( "</TD>\n" );

     out.println( "<TR>\n" );
     out.println( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Item ID:</B></FONT></TD><TD WIDTH=\"70%\" COLSPAN=\"3\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" id=\"supplierid\" name=\"supplierid\" value=\"\" SIZE=\"10\" readonly>\n" );
     out.println( "</TR>\n" );
     out.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
     out.println( "<TR>\n" );
     out.println( "<TH WIDTH=\"5%\"><B>Item Id</B></FONT></TH>\n" );
     out.println( "<TH WIDTH=\"3%\"><B>Type</B></FONT></TH>\n" );
     if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
     {
       out.println( "<TH WIDTH=\"10%\"><B>Catalog Id</B></FONT></TH>\n" );
       out.println( "<TH WIDTH=\"10%\"><B>Part Num</B></FONT></TH>\n" );
       out.println( "<TH WIDTH=\"5%\"><B>Priority</B></FONT></TH>\n" );
       out.println( "<TH WIDTH=\"5%\"><B>Status</B></FONT></TH>\n" );
     }
     out.println( "<TH WIDTH=\"35%\"><B>Item Desc</B></FONT></TH>\n" );
     if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
     {
       out.println( "<TH WIDTH=\"15%\"><B>Packaging</B></FONT></TH>\n" );
     }
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

     if ( !(( !"Search".equalsIgnoreCase( SearchFlag ) ) && ( !"lineCharge".equalsIgnoreCase( subUserAction ) )) )
     {
       try
       {
           connect1=db.getConnection();
           //System.out.println( "Here SearchString " + SearchString + " SearchString1 " + SearchString1 + " CompanyId1 " + CompanyId1 + " shipto " + shipto +  "  sortBy " + sortBy + "" );
           cs=connect1.prepareCall( "{call pkg_po.item_search(?,?,?,?,?,?,?,?,?,?)}" );

           if ( "lineCharge".equalsIgnoreCase( subUserAction ) )
           {
             cs.setNull( 1,OracleTypes.VARCHAR );
             cs.setNull( 2,OracleTypes.VARCHAR );
           }
           else
           {
             if ( CompanyId1.length() > 0 )
             {
               cs.setString( 1,CompanyId1 );
             }
             else
             {
               cs.setNull( 1,OracleTypes.VARCHAR );
             }
             if ( shipto.length() > 0 )
             {
               cs.setString( 2,shipto );
             }
             else
             {
               cs.setNull( 2,OracleTypes.VARCHAR );
             }
           }
           if ( SearchString.length() > 0 )
           {
             cs.setString( 3,SearchString );
           }
           else
           {
             cs.setNull( 3,OracleTypes.VARCHAR );
           }
           if ( SearchString1.length() > 0 )
           {
             cs.setString( 4,SearchString1 );
           }
           else
           {
             cs.setNull( 4,OracleTypes.VARCHAR );
           }

           if ( "lineCharge".equalsIgnoreCase( subUserAction ) )
           {
             cs.setString( 5,"Y" );
           }
           else
           {
             cs.setNull( 5,OracleTypes.VARCHAR );
           }

           if ( sortBy.length() > 0 )
           {
             cs.setString( 6,sortBy );
           }
           else
           {
             cs.setNull( 6,OracleTypes.VARCHAR );
           }

           if ( "Yes".equalsIgnoreCase( validbpo ) )
           {
             cs.setString( 7,"Y" );
           }
           else
           {
             cs.setNull( 7,OracleTypes.VARCHAR );
           }
           if ( invengrp.length() > 0 )
           {
             cs.setString( 8,invengrp );
           }
           else
           {
             cs.setNull( 8,OracleTypes.VARCHAR );
           }

           cs.registerOutParameter( 9,OracleTypes.CURSOR );
           cs.registerOutParameter( 10,OracleTypes.VARCHAR );
           cs.execute();

           String queryfromproc=cs.getObject( 10 ).toString();
           polog.debug(queryfromproc);
           //System.out.println("queryfromproc      "+queryfromproc);
           rs= ( ResultSet ) cs.getObject( 9 );

         while ( rs.next() )
         {
           totalrecords+=1;
           count+=1;

           String company_id="";
           String catalog_id="";
           String cat_part_no="";
           String priority="";
           String status="";
           String packaging="";
           String item_type="";
		   String canassigcharge = "";
		   String canassignsecondarysupplier = "";

           if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
           {
             company_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
             catalog_id=rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ).trim();
             cat_part_no=rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ).trim();
             priority=rs.getString( "PRIORITY" ) == null ? "" : rs.getString( "PRIORITY" ).trim();
             status=rs.getString( "STATUS" ) == null ? "" : rs.getString( "STATUS" ).trim();
             packaging=rs.getString( "PACKAGING" ) == null ? "" : rs.getString( "PACKAGING" ).trim();

           }

           String item_id=rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ).trim();
           String item_desc=rs.getString( "ITEM_DESC" ) == null ? "" : rs.getString( "ITEM_DESC" ).trim();
           item_type=rs.getString( "item_type" ) == null ? "" : rs.getString( "item_type" ).trim();

 		   if ( !addchargeslist.contains( item_type ) )
		   {
			 canassigcharge = "Y";
	       }

		   if ( secondarysupplierlist.contains( item_type ) )
		   {
			canassignsecondarysupplier = "Y";
		   }

           String Color=" ";
           if ( count % 2 == 0 )
           {
             Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
           }
           else
           {
             Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
           }

           out.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('" + item_id + "','" + item_type + "','" + HelpObjs.addescapesForJavascript( packaging ) + "','','" + HelpObjs.addescapesForJavascript( item_desc ) + "','"+canassigcharge+"','"+canassignsecondarysupplier+"')\" BORDERCOLOR=\"black\">\n" );
           out.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
           out.println( item_id );
           out.println( "</TD>\n" );
           out.println( "<TD " + Color + " WIDTH=\"3%\" ALIGN=\"LEFT\">" );
           out.println( item_type );
           out.println( "</TD>\n" );
           if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
           {
             out.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
             out.println( catalog_id );
             out.println( "</TD>\n" );
             out.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
             out.println( cat_part_no );
             out.println( "</TD>\n" );
             out.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
             out.println( priority );
             out.println( "</TD>\n" );
             out.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
             out.println( status );
             out.println( "</TD>\n" );
           }
           out.println( "<TD " + Color + " WIDTH=\"35%\" ALIGN=\"LEFT\">" );
           out.println( item_desc );
           out.println( "</TD>\n" );
           if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
           {
             out.println( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
             out.println( packaging );
             out.println( "</TD>\n" );
           }

           out.println( "</TR>\n" );
         }
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         out.println( "An Error Occured While Querying the Database" );
         out.println( "</BODY>\n</HTML>\n" );
         return;
       }
       finally
       {
         if ( cs != null )
         {
           try
           {
             cs.close();
           }
           catch ( SQLException se )
           {}
         }
       }
     }

     if ( totalrecords == 0 )
     {
       out.println( "<TR><TD>No Records Found</TD></TR>\n" );
     }
     out.println( "</TABLE>\n" );

     //close scrolling table
     out.println( "</DIV>\n" );
     out.println( "</TD>\n" );
     out.println( "</TR>\n" );
     out.println( "</TBODY>\n" );
     out.println( "</TABLE>\n" );

     out.println( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"okupdate\" name=\"okupdate\" value=\"Ok\" OnClick=\"sendlineItem(this.form)\" type=\"button\">\n" );
     out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
     out.println( "</FORM></BODY></HTML>\n" );

     return;
   }

   private void printJavaScripts( String controlname )
   {
     //StringBuffer Msglt=new StringBuffer();

     out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
     out.println( "<!--\n" );
     out.println( "function cancel()\n" );
     out.println( "{\n" );
     out.println( "window.close();\n" );
     out.println( "}\n" );
     out.println( "function sendlineItem(entered)\n" );
     out.println( "{\n" );
     out.println( " with (entered)\n" );
     out.println( " {\n" );
     out.println( " eval( testmfgid = \"window.document.SupplierLike.supplierid\")\n" );
     out.println( " if ( (eval(testmfgid.toString())).value.length > 0 )\n" );
     out.println( "{" );

     int compn=Integer.parseInt( controlname.trim() );
     String itemColor=" ";
     if ( compn % 2 == 0 )
     {
       itemColor="INVISIBLEHEADBLUE";
     }
     else
     {
       itemColor="INVISIBLEHEADWHITE";
     }

     out.println( "selectedRow = opener.document.getElementById(\"lineitemid" + compn + "\");\n" );
     //out.println("selectedRow.className = \"HEADER\";\n");
     out.println( "selectedRow.className = \"" + itemColor + "\";\n" );
     out.println( "selectedRow.value = window.document.SupplierLike.supplierid.value;\n" );

     out.println( "selectedRow = opener.document.getElementById(\"itemtype" + compn + "\");\n" );
     out.println( "selectedRow.value = window.document.SupplierLike.itemtype.value;\n" );

     out.println( "selectedRow = opener.document.getElementById(\"validitem" + compn + "\");\n" );
     out.println( "selectedRow.value = \"Yes\";\n" );

     out.println( "itemtypecell = opener.document.getElementById(\"itemtypecell" + compn + "\");\n" );
     out.println( "itemtypecell.innerHTML = window.document.SupplierLike.itemtype.value;\n" );

     out.println( "pakgingcell = opener.document.getElementById(\"pakgingcell" + compn + "\");\n" );
     out.println( "pakgingcell.innerHTML = window.document.SupplierLike.itempackaging.value;\n" );

     out.println( "selectedRow = opener.document.getElementById(\"itemdescdivrow" + compn + "" + compn + "\");\n" );
     out.println( "selectedRow.innerHTML = window.document.SupplierLike.desc.value;\n" );

     out.println( "if (\"Y\" == window.document.SupplierLike.canassignsecondarysupplier.value) \n" );
	 out.println( "{ \n" );
	 out.println( "secondarysuppliercelldivrow = opener.document.getElementById(\"secondarysuppliercelldivrow" + compn + "" + compn + "\");\n" );
	 out.println( "var innHtmlline = \"<input type=\\\"text\\\" size=\\\"5\\\" CLASS=\\\"HEADER\\\" name='secondarysupplier"+compn+"' id='secondarysupplier"+compn+"'>\";\n" );
	 out.println( "innHtmlline =  innHtmlline +\"<BUTTON name='secondarysupplierbutton' OnClick=getSecondarySupplier('"+compn+"') type=button><IMG src=\\\"/images/search_small.gif\\\" alt=\\\"Secondary Supplier\\\"></BUTTON>\";\n" );
	 out.println( "secondarysuppliercelldivrow.innerHTML = innHtmlline;\n" );
 	 out.println( "} \n" );

     out.println( "if (\"Y\" == window.document.SupplierLike.canassigcharge.value) \n" );
     out.println( "{ \n" );

     out.println( "opener.invalidatespecsandflow(" + compn + "); \n" );

     out.println( "selectedRow = opener.document.getElementById(\"itemdescdivrow" + compn + "" + compn + "\");\n" );
     out.println( "selectedRow.innerHTML = window.document.SupplierLike.desc.value;\n" );

     out.println( "validflowdown = opener.document.getElementById(\"validflowdown" + compn + "\");\n" );
     out.println( "validflowdown.value = \"No\";\n" );

     out.println( "validspec = opener.document.getElementById(\"validspec" + compn + "\");\n" );
     out.println( "validspec.value = \"No\";\n" );

	 out.println( "canassignaddcharge = opener.document.getElementById(\"canassignaddcharge" + compn + "\");\n" );
	 out.println( "canassignaddcharge.value = \"Y\";\n" );

     out.println( "} \n" );

     out.println( "linechange = opener.document.getElementById(\"linechange" + compn + "\");\n" );
     out.println( "linechange.value = \"Yes\";\n" );
     out.println( "window.close();\n" );
     out.println( " }\n" );
     out.println( " }\n" );
     out.println( "}\n" );

     out.println( "function addsupplierID(matidvalue,itemtype,itempackaging,mpn,desc,cancharge,canassignsecondarysupplier)\n" );
     out.println( "{\n" );
     out.println( "document.SupplierLike.supplierid.value = matidvalue;\n" );
     out.println( "document.SupplierLike.itemtype.value = itemtype;\n" );
     out.println( "document.SupplierLike.itempackaging.value = itempackaging;\n" );
     out.println( "document.SupplierLike.mpn.value = mpn;\n" );
     out.println( "document.SupplierLike.desc.value = desc;\n" );
	 out.println( "document.SupplierLike.canassigcharge.value = cancharge;\n" );
	 out.println( "document.SupplierLike.canassignsecondarysupplier.value = canassignsecondarysupplier;\n" );

     out.println( "}\n" );

     return;
   }
}