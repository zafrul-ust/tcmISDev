package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.OracleTypes;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-02-03 - Code refactoring created ap helpobj
 * 03-02-04 - Changes to allow additional charge RMA Credit
 */


public class soundsLikinvoiceItemId
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    //private DBResultSet dbrs = null;
    //private ResultSet rs = null;
    private String nematid_Servlet = "";
    private  boolean allowupdate;
    private ResourceLibrary res = null; // res means resource.

    public void setupdateStatus(boolean id)
    {
      this.allowupdate = id;
    }

    private boolean getupdateStatus()  throws Exception
    {
     return this.allowupdate;
    }

    public soundsLikinvoiceItemId(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResult(HttpServletRequest request, HttpServletResponse response)  throws ServletException,  IOException
    {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    HttpSession session=request.getSession();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));

    String validpo=request.getParameter( "po" );
    if ( validpo == null )
      validpo="";
    validpo=validpo.trim();

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
      lineID="0";
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

    //System.out.println("Here itemID  " + itemID + " Action   "+Action+"  searchString  "+searchString+" searchString1  "+searchstring1+" lineID "+lineID+"  validpo "+validpo);
    if ("okupdate".equalsIgnoreCase(Action))
    {
    buildsendSupplier(validpo,lineID,itemID,out);
    }
    else if ("Search".equalsIgnoreCase(Action))
    {
    buildLikeSupplier("Search",searchString,CompanyId,itemID,subUserAction,searchstring1,sortBy,shiptoid,validpo,out);
    }
    else
    {
    buildLikeSupplier("",searchString,CompanyId,itemID,subUserAction,searchstring1,sortBy,shiptoid,validpo,out);
    }
    out.close();

  }

 public void buildsendSupplier( String radianPo,String lineID,String item_id,PrintWriter apout )
 {
   //StringBuffer Msgn=new StringBuffer();
   //StringBuffer Msgcbody=new StringBuffer();
   nematid_Servlet=bundle.getString( "INVOICE_PURCHASE_ORDER_ITEM" );

   apout.println( radian.web.HTMLHelpObj.printHTMLHeader( "title.purorderitemid","",false,res ) );
   printJavaScripts(apout);
   apout.println( "// -->\n </SCRIPT>\n" );

   //StringBuffer Msgtemp=new StringBuffer();
   //Build the Java Script Here and Finish the thing
   apout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
   apout.println( "<!--\n" );
   apout.println( "function sendSupplierData()\n" );
   apout.println( "{\n" );
   //apout.println( builditemmatching( radianPo,item_id,lineID ) );
   //apout.println( buildreceiptmatching( radianPo,item_id,lineID ) );
   boolean updateststus=false;
   try
   {
     updateststus=this.getupdateStatus();
   }
   catch ( Exception ex )
   {
   }

   radian.web.apHelpObj.builditemmatching( db,radianPo,item_id,lineID,"",updateststus,false,apout,res );
   radian.web.apHelpObj.buildreceiptmatching( db,radianPo,item_id,lineID,updateststus,false,apout,res );

   apout.println( "window.close();\n" );
   apout.println( " }\n" );
   apout.println( "// -->\n</SCRIPT>\n" );
   //apout.println( Msgtemp );
   apout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
   apout.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
   apout.println( "<CENTER><BR><BR>\n" );
   apout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\""+res.getString("label.ok")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
   apout.println( "</FORM></BODY></HTML>\n" );
   //apout.println( Msgbody );

   return;
   }


   public void buildLikeSupplier( String SearchFlag,String SearchString,String CompanyId1,String MfgID1,String subUserAction,String SearchString1,
                                          String sortBy,String shipto,String validpo,PrintWriter apout )
   {
     //StringBuffer Msgl=new StringBuffer();
     MfgID1=MfgID1.trim();
     Connection connect1=null;
     CallableStatement cs=null;

     nematid_Servlet=bundle.getString( "INVOICE_PURCHASE_ORDER_ITEM" );

     apout.println( radian.web.HTMLHelpObj.printHTMLHeader( "title.purorderitemid","",false,res ) );
     printJavaScripts(apout);
     apout.println( "function ShowSearch(name,entered)\n" );
     apout.println( "{\n" );
     apout.println( " with (entered)\n" );
     apout.println( " {\n" );
     apout.println( " loc = \"" + nematid_Servlet + "&Action=\" + name.toString() + \"&SearchString=\";\n" );
     apout.println( " loc = loc + \"&subUserAction=" + subUserAction + "\"; \n" );
     apout.println( " loc = loc + \"&itemID=\"; \n" );
     apout.println( " loc = loc + window.document.SupplierLike.supplierid.value;\n" );
     apout.println( " loc = loc + \"&sortBy=\"; \n" );
     apout.println( " loc = loc + \"&po=" + validpo + "\"; \n" );
     apout.println( " }\n" );

     apout.println( " if (name.toString() == \"okupdate\")\n" );
     apout.println( " {\n" );
     apout.println( "  eval( testmfgid = \"window.document.SupplierLike.supplierid\")\n" );
     apout.println( "  if ( (eval(testmfgid.toString())).value.length > 0 )\n" );
     apout.println( "   { \n" );
     apout.println( "   sendlineItem(entered);\n" );
     apout.println( "   loc = loc + \"&lineID=\" + window.document.SupplierLike.itemtype.value; \n" );
     apout.println( "   window.location.replace(loc);\n" );
     apout.println( "   }\n" );
     apout.println( " }\n" );
     apout.println( " else \n" );
     apout.println( " {\n" );
     apout.println( "  window.location.replace(loc);\n" );
     apout.println( " }\n" );
     apout.println( " }\n" );
     apout.println( "// -->\n </SCRIPT>\n" );

     apout.println( "<BODY onload=\"javascript:window.resizeTo(800,600)\">\n" );
     apout.println( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
     apout.println( "<INPUT TYPE=\"hidden\" NAME=\"subUserAction\" VALUE=\"" + subUserAction + "\">\n" );
     apout.println( "<INPUT TYPE=\"hidden\" NAME=\"itemtype\" VALUE=\"\">\n" );
     apout.println( "<INPUT TYPE=\"hidden\" NAME=\"itempackaging\" VALUE=\"\">\n" );
     apout.println( "<INPUT TYPE=\"hidden\" NAME=\"mpn\" VALUE=\"\">\n" );
     apout.println( "<INPUT TYPE=\"hidden\" NAME=\"desc\" VALUE=\"\">\n" );
     apout.println( "<INPUT TYPE=\"hidden\" NAME=\"shiptoid\" VALUE=\"" + shipto + "\">\n" );
     apout.println( "<INPUT TYPE=\"hidden\" NAME=\"Company\" VALUE=\"" + CompanyId1 + "\">\n" );

     apout.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
     apout.println( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>"+res.getString("label.itemid")+":</B></FONT></TD><TD WIDTH=\"70%\" COLSPAN=\"3\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"supplierid\" value=\"\" SIZE=\"10\" readonly>\n" );
     apout.println( "</TR>\n" );

     apout.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
     apout.println( "<TR>\n" );
     apout.println( "<TH WIDTH=\"5%\"><B>"+res.getString("label.itemid")+"</B></FONT></TH>\n" );
     apout.println( "<TH WIDTH=\"3%\"><B>"+res.getString("label.type")+"</B></FONT></TH>\n" );
     if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
     {
       apout.println( "<TH WIDTH=\"10%\"><B>"+res.getString("label.catalogid")+"</B></FONT></TH>\n" );
       apout.println( "<TH WIDTH=\"10%\"><B>"+res.getString("label.partnum")+"</B></FONT></TH>\n" );
       apout.println( "<TH WIDTH=\"5%\"><B>"+res.getString("label.priority")+"</B></FONT></TH>\n" );
       apout.println( "<TH WIDTH=\"5%\"><B>"+res.getString("label.status")+"</B></FONT></TH>\n" );
     }
     apout.println( "<TH WIDTH=\"35%\"><B>"+res.getString("label.itemdesc")+"</B></FONT></TH>\n" );
     if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
     {
       apout.println( "<TH WIDTH=\"15%\"><B>"+res.getString("label.packaging")+"</B></FONT></TH>\n" );
     }
     apout.println( "</TR></TABLE>\n" );

     //open scrolling table
     apout.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
     apout.println( "<TBODY>\n" );
     apout.println( "<TR>\n" );
     apout.println( "<TD VALIGN=\"TOP\">\n" );
     apout.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );

     //Write code to insert rows here
     apout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );
     int totalrecords=0;
     int count=0;
	 DBResultSet dbrs = null;
     ResultSet rs = null;
     if (!( ( !"Search".equalsIgnoreCase( SearchFlag ) ) && ( !"lineCharge".equalsIgnoreCase( subUserAction ) ) ))
     {
       try
      {
        connect1 = db.getConnection();
        //System.out.println("Here SearchString "+SearchString+ " SearchString1 "+SearchString1+" CompanyId1 "+CompanyId1+" shipto "+shipto+"  sortBy "+sortBy+"");

        cs = connect1.prepareCall("{call pkg_po.item_search(?,?,?,?,?,?,?,?,?,?)}");

        if ("lineCharge".equalsIgnoreCase(subUserAction))
        {
          cs.setNull(1, OracleTypes.VARCHAR);
          cs.setNull(2, OracleTypes.VARCHAR);
        }
        else
        {
        if (CompanyId1.length() > 0){cs.setString(1, CompanyId1);}else{cs.setNull(1, OracleTypes.VARCHAR);}
        if (shipto.length() > 0){cs.setString(2, shipto);}else{cs.setNull(2, OracleTypes.VARCHAR);}
        }
        if (SearchString.length() > 0){cs.setString(3, SearchString);}else{cs.setNull(3, OracleTypes.VARCHAR);}
        if (SearchString1.length() > 0){cs.setString(4, SearchString1);}else{cs.setNull(4, OracleTypes.VARCHAR);}
        if ("lineCharge".equalsIgnoreCase(subUserAction)){cs.setString(5,"Y");}else{cs.setNull(5, OracleTypes.VARCHAR);}
        if (sortBy.length() > 0){cs.setString(6, sortBy);}else{cs.setNull(6, OracleTypes.VARCHAR);}
        if ("Yes".equalsIgnoreCase(validpo)){cs.setString(7, "Y");}else{cs.setNull(7, OracleTypes.VARCHAR);}
        String invengrp = "";
        if (invengrp.length() > 0){cs.setString(8, invengrp);}else{cs.setNull(8, OracleTypes.VARCHAR);}

        cs.registerOutParameter(9, OracleTypes.CURSOR);
        cs.registerOutParameter(10, OracleTypes.VARCHAR);
        cs.execute();

        rs = (ResultSet) cs.getObject(9);

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

         String Color=" ";
         if ( count % 2 == 0 )
         {
           Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
         }
         else
         {
           Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
         }

         //apout.println("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('"+item_id+"','"+item_type+"','"+HelpObjs.addescapesForJavascript(packaging)+"','','"+HelpObjs.addescapesForJavascript(item_desc)+"')\" BORDERCOLOR=\"black\">\n");
         apout.println("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('"+item_id+"','"+item_type+"','"+HelpObjs.addescapesForJavascript(packaging)+"','','"+HelpObjs.addescapesForJavascript(item_desc)+"')\" BORDERCOLOR=\"black\">\n");
         apout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
         apout.println( item_id );
         apout.println( "</TD>\n" );

         apout.println( "<TD " + Color + " WIDTH=\"3%\" ALIGN=\"LEFT\">" );
         apout.println( item_type );
         apout.println( "</TD>\n" );
         if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
         {
           apout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
           apout.println( catalog_id );
           apout.println( "</TD>\n" );
           apout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
           apout.println( cat_part_no );
           apout.println( "</TD>\n" );
           apout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
           apout.println( priority );
           apout.println( "</TD>\n" );
           apout.println( "<TD " + Color + " WIDTH=\"5%\" ALIGN=\"LEFT\">" );
           apout.println( status );
           apout.println( "</TD>\n" );
         }
         apout.println( "<TD " + Color + " WIDTH=\"35%\" ALIGN=\"LEFT\">" );
         apout.println( item_desc );
         apout.println( "</TD>\n" );
         if ( !"lineCharge".equalsIgnoreCase( subUserAction ) )
         {
           apout.println( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
           apout.println( packaging );
           apout.println( "</TD>\n" );
         }

         apout.println( "</TR>\n" );
       }
     }
     catch ( Exception e )
     {
       e.printStackTrace();
       apout.println( res.getString("err.querydatabase") );
       apout.println( "</BODY>\n</HTML>\n" );
       return ;
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
     apout.println( "<TR><TD>"+res.getString("label.norecordfound")+"</TD></TR>\n" );
   }
   apout.println( "</TABLE>\n" );

   //close scrolling table
   apout.println( "</DIV>\n" );
   apout.println( "</TD>\n" );
   apout.println( "</TR>\n" );
   apout.println( "</TBODY>\n" );
   apout.println( "</TABLE>\n" );
   apout.println( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\""+res.getString("label.ok")+"\" OnClick=\"ShowSearch('okupdate',this)\" type=\"button\">\n" );
   apout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"searchlike\" value=\""+res.getString("label.cancel")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
   apout.println( "</FORM></BODY></HTML>\n" );

   return;
   }

   protected void printJavaScripts(PrintWriter apout)
   {
     //StringBuffer Msglt=new StringBuffer();

     apout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
     apout.println( "<!--\n" );
     apout.println( "function cancel()\n" );
     apout.println( "{\n" );
     apout.println( "window.close();\n" );
     apout.println( "}\n" );
     apout.println( "function sendlineItem(entered)\n" );
     apout.println( "{\n" );
     apout.println( " with (entered)\n" );
     apout.println( " {\n" );
     apout.println( " eval( testmfgid = \"window.document.SupplierLike.supplierid\")\n" );
     apout.println( " if ( (eval(testmfgid.toString())).value.length > 0 )\n" );
     apout.println( "{" );
     apout.println( "opener.addLineItem(); \n" );
     apout.println( "mytable = opener.document.getElementById(\"linetable\");\n" );
     apout.println( "var allTRs = mytable.getElementsByTagName(\"tr\");\n" );
     apout.println( "var rownum = (allTRs.length)*1;\n" );
     apout.println( " rownum = rownum - 1;\n" );
     apout.println( "document.SupplierLike.itemtype.value = rownum;\n" );
     apout.println( "selectedRow = opener.document.getElementById(\"cell1\"+rownum);\n" );
     apout.println( "selectedRow.innerHTML = window.document.SupplierLike.supplierid.value;\n" );
     apout.println( "selectedRow = opener.document.getElementById(\"cell2\"+rownum);\n" );
     apout.println( "selectedRow.innerHTML = window.document.SupplierLike.desc.value;\n" );
     apout.println( "selectedRow = opener.document.getElementById(\"cell3\"+rownum);\n" );
     apout.println( "selectedRow.innerHTML = window.document.SupplierLike.itempackaging.value;\n" );
     apout.println( "itemidindetail = opener.document.getElementById(\"itemidindetail\"+rownum);\n" );
     apout.println( "itemidindetail.value = window.document.SupplierLike.supplierid.value;\n" );
	 apout.println( "addmaterialrma = opener.document.getElementById(\"addmaterialrma\"+rownum);\n" );
	 apout.println( "addmaterialrma.disabled=true;\n" );
	 apout.println( "addmaterialrma.style.display=\"none\";\n" );
     apout.println( " }\n" );
     apout.println( " }\n" );
     apout.println( "}\n" );
     apout.println( "function addsupplierID(matidvalue,itemtype,itempackaging,mpn,desc)\n" );
     apout.println( "{\n" );
     apout.println( "document.SupplierLike.supplierid.value = matidvalue;\n" );
     apout.println( "document.SupplierLike.itemtype.value = itemtype;\n" );
     apout.println( "document.SupplierLike.itempackaging.value = itempackaging;\n" );
     apout.println( "document.SupplierLike.mpn.value = mpn;\n" );
     apout.println( "document.SupplierLike.desc.value = desc;\n" );
     apout.println( "}\n" );

     return;
   }
}
