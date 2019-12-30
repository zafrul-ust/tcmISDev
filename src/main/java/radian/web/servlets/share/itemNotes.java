package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
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
 * 08-08-03 - Code Cleanup
 * 10-13-04 - Showing the catalog commetns for each part in a inventory group
 */

public class itemNotes
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
    private boolean intcmIsApplication = false;
    private ResourceLibrary res = null; // res means resource.

    public itemNotes(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }
     public void doResult(HttpServletRequest request, HttpServletResponse response)
          throws ServletException,  IOException
    {

    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    HttpSession itenoteses = request.getSession();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)itenoteses.getAttribute("tcmISLocale"));

    PersonnelBean personnelBean = (PersonnelBean) itenoteses.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
    {
     intcmIsApplication = true;
    }

    String personnelid=itenoteses.getAttribute( "PERSONNELID" ) == null ? "" : itenoteses.getAttribute( "PERSONNELID" ).toString();

    String lineID=request.getParameter( "lineID" );
    if ( lineID == null )
      lineID="";
    lineID=lineID.trim();

	String invengrp=request.getParameter( "invengrp" );
	if ( invengrp == null )
	  invengrp="";
	invengrp=invengrp.trim();

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action=Action.trim();

    String itemID=request.getParameter( "itemID" );
    if ( itemID == null )
      itemID="";
    itemID=itemID.trim();

    String Comments=request.getParameter( "Comments" );
    if ( Comments == null )
      Comments="";
    Comments=Comments.trim();

    //System.out.println("Here itemID  " + itemID + " Action   "+Action+"  searchString  "+searchString+" lineID "+lineID);
	if ("partnotes".equalsIgnoreCase(Action))
	{
	buildViewpartcomm("",lineID,itemID,invengrp,out);
	}
    else if ("view".equalsIgnoreCase(Action))
    {
    buildViewitemcomm("",lineID,itemID,out);
    }
    else if ("addnew".equalsIgnoreCase(Action))
    {
     buildaddnewComm(lineID,itemID,"",out);
    }
    else if ("Update".equalsIgnoreCase(Action))
    {
     buildaddNotes(personnelid,lineID,itemID,Comments,out);
    }

    out.close();
  }

  public void buildViewpartcomm( String setlinestatus,String lineID,String itemID,String invgrp,PrintWriter poout )
 {
   //StringBuffer Msgn=new StringBuffer();
   //StringBuffer Msgbody=new StringBuffer();

   nematid_Servlet=bundle.getString( "PURCHASE_ORDER_ITEMNOTES" );
   poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Item Notes","",intcmIsApplication ) );
   printJavaScripts(poout);
   poout.println( "// -->\n </SCRIPT>\n" );

   //StringBuffer Msgtemp=new StringBuffer();
   poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
   poout.println( "<!--\n" );
   poout.println( "function sendSupplierData()\n" );
   poout.println( "{\n" );
   radian.web.poHelpObj.buildsendpartNotes( db,lineID,itemID,invgrp,setlinestatus,poout,res);
   poout.println( "window.close();\n" );
   poout.println( " }\n" );
   poout.println( "// -->\n</SCRIPT>\n" );
   //poout.println( Msgtemp );
   poout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
   poout.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
   poout.println( "<CENTER><BR><BR>\n" );
   poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
   poout.println( "</FORM></BODY></HTML>\n" );
   //poout.println( Msgbody );

   return;
 }

  public void buildaddNotes( String personnelID,String lineID,String itemID,String Comments1,PrintWriter poout )
  {
    boolean updatesucess=false;
    //StringBuffer Msgn=new StringBuffer();
    String errmsg = "An error occuered please check the length and try again <BR>";

    try
    {
      Comments1=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( Comments1 );
      //if ( Comments1.length() > 1000 ) {Comments1=Comments1.substring( 0,999 );}

      db.doQuery( "insert into item_buyer_comments (ITEM_ID,BUYER_ID,TRANSACTION_DATE,COMMENTS) values (" + itemID + "," + personnelID + ",sysdate,'" + Comments1 + "')" );
      updatesucess = true;
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      updatesucess = false;
      errmsg += e.getMessage();
    }

    if (updatesucess)
    {
      buildViewitemcomm( "yes",lineID,itemID,poout);
    }
    else
    {
      buildaddnewComm(lineID,itemID,errmsg,poout);
    }

    return;
   }

   public void buildaddnewComm( String lineID,String itemID,String message,PrintWriter poout )
   {
     //StringBuffer Msgn=new StringBuffer();
     nematid_Servlet=bundle.getString( "PURCHASE_ORDER_ITEMNOTES" );
     poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Item Notes","",intcmIsApplication ) );
     printJavaScripts(poout);
     poout.println( "// -->\n </SCRIPT>\n" );
     poout.println( "<BODY onload=\"javascript:window.resizeTo(550,250)\">\n" );
     poout.println( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
     poout.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Update\">\n" );
     poout.println( "<INPUT TYPE=\"hidden\" NAME=\"itemID\" VALUE=\"" + itemID + "\">\n" );
     poout.println( "<INPUT TYPE=\"hidden\" NAME=\"lineID\" VALUE=\"" + lineID + "\">\n" );
     poout.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n" );
     poout.println( "<TR><TD WIDTH=\"15%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Item ID:</B></TD><TD WIDTH=\"45%\">\n" );
     poout.println( "&nbsp;" + itemID + "\n" );
     poout.println( "</TD></TR>\n" );
     poout.println( "<TR>\n" );
     poout.println( "<TD COLSPAN=\"3\" CLASS=\"announce\"><BR><B>"+message+"</B><BR></TD>\n" );
     poout.println( "</TR>\n" );
     poout.println( "<TR>\n" );
     poout.println( "<TD WIDTH=\"10%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Notes:</B></TD>\n" );
     poout.println( "<TD WIDTH=\"10%\" CLASS=\"announce\">\n" );
     poout.println( "<TEXTAREA name=\"Comments\" rows=\"3\" cols=\"70\"></TEXTAREA></TD>\n" );
     poout.println( "</TD>\n" );
     poout.println( "</TR>\n" );
     poout.println( "</TABLE>\n" );
     poout.println( "<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Add\" name=\"CreateNew\">\n" );
     poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
     poout.println( "</FORM></BODY></HTML>\n" );

     return;
   }

   public void buildViewitemcomm( String setlinestatus,String lineID,String itemID,PrintWriter poout )
   {
     //StringBuffer Msgn=new StringBuffer();
     //StringBuffer Msgbody=new StringBuffer();

     nematid_Servlet=bundle.getString( "PURCHASE_ORDER_ITEMNOTES" );
     poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Item Notes","",intcmIsApplication ) );
     printJavaScripts(poout);
     poout.println( "// -->\n </SCRIPT>\n" );

     //StringBuffer Msgtemp=new StringBuffer();
     poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
     poout.println( "<!--\n" );
     poout.println( "function sendSupplierData()\n" );
     poout.println( "{\n" );
     radian.web.poHelpObj.buildsenditemNotes( db,lineID,itemID,setlinestatus,poout,res);
     poout.println( "window.close();\n" );
     poout.println( " }\n" );
     poout.println( "// -->\n</SCRIPT>\n" );
     //poout.println( Msgtemp );
     poout.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	 poout.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
     poout.println( "<CENTER><BR><BR>\n" );
     poout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
     poout.println( "</FORM></BODY></HTML>\n" );
     //poout.println( Msgbody );

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