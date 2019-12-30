package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 *
 * 11-08-02
 * Not checking any checkboxes for specs by default. not even for color code 1 and 2
 * 03-25-03 :Checking the ok and COC and COA boxes when the po id pulled up before confirmation and purple is number 4 now changed method is showspec
 * 04-02-03 : Not checking the specs ok boxes
 * 08-08-03 - Code Cleanup
 */


public class showSpecs
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
    private ResourceLibrary res = null; // res means resource.

    public showSpecs(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResult( HttpServletRequest request,HttpServletResponse response )
       throws ServletException,IOException
    {
      PrintWriter out=response.getWriter();
      response.setContentType( "text/html" );
			HttpSession session = request.getSession();
      res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));

      String ammendMent=request.getParameter( "ammendMent" );
      if ( ammendMent == null )
        ammendMent="";
      ammendMent=ammendMent.trim();

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

      String shiptTo=request.getParameter( "shiptTo" );
      if ( shiptTo == null )
        shiptTo="";
      shiptTo=shiptTo.trim();

      String shiptocompanyid=request.getParameter( "shiptocompanyid" );
      if ( shiptocompanyid == null )
        shiptocompanyid="";
      shiptocompanyid=shiptocompanyid.trim();

      String radianPO=request.getParameter( "radianPO" );
      if ( radianPO == null )
        radianPO="";
      radianPO=radianPO.trim();

      String radianBPO=request.getParameter( "radianBPO" );
      if ( radianBPO == null )
        radianBPO="";
      radianBPO=radianBPO.trim();

      String invengrp=request.getParameter( "invengrp" );
      if ( invengrp == null )
        invengrp="";
      invengrp=invengrp.trim();

      if ( "view".equalsIgnoreCase( Action ) )
      {
        buildsendspecs( lineID,itemID,radianPO,radianBPO,shiptTo,ammendMent,shiptocompanyid,invengrp,out,radian.web.HTMLHelpObj.hasaccessto(session, "specOverRide") );
      }
      out.close();
    }

  public void buildsendspecs( String lineID,String itemID,String radianpo,String radianbpo,String shipToLoc,String ammenNum,String shiptocompanyid,String invengrp,PrintWriter poout,boolean specOverRide)
  {
    //StringBuffer Msgn=new StringBuffer();
    //StringBuffer Msgbody=new StringBuffer();

    nematid_Servlet=bundle.getString( "PURCHASE_ORDER_SHOWSPECS" );
    poout.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Specs","",false ) );
    printJavaScripts(poout);
    poout.println( "// -->\n </SCRIPT>\n" );

    //StringBuffer Msgtemp=new StringBuffer();
    poout.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    poout.println( "<!--\n" );
    poout.println( "function sendSupplierData()\n" );
    poout.println( "{\n" );
    radian.web.poHelpObj.buildsendSpecs( db,"",lineID,itemID,radianpo,radianbpo,shipToLoc,ammenNum,shiptocompanyid,invengrp,null,poout,specOverRide,res);
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