package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
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
 */

public class showClientBuys
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
	private PrintWriter out = null;
	private boolean intcmIsApplication = false;
  private ResourceLibrary res = null; // res means resource.

    public showClientBuys(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }
     public void doResult(HttpServletRequest request, HttpServletResponse response)
          throws ServletException,  IOException
    {
    out = response.getWriter();
    response.setContentType("text/html");
		HttpSession session=request.getSession();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));             
    PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action=Action.trim();

    String lineID=request.getParameter( "lineID" );
    if ( lineID == null )
      lineID="";
    lineID=lineID.trim();

    String itemID=request.getParameter( "itemID" );
    if ( itemID == null )
      itemID="";
    itemID=itemID.trim();

    //System.out.println("Here itemID  " + itemID + " Action   "+Action+"  searchString  "+searchString+" lineID "+lineID);
    if ("view".equalsIgnoreCase(Action))
    {
    buildsendclientbuys(lineID,itemID);
    }
    out.close();
  }

  public void buildsendclientbuys( String lineID,String itemID )
  {
    //StringBuffer Msgn=new StringBuffer();
    //StringBuffer Msgbody=new StringBuffer();

    nematid_Servlet=bundle.getString( "PURCHASE_ORDER_SHOWCLIENTBUYS" );
    out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Client Buys","",intcmIsApplication ) );
    printJavaScripts();
    out.println( "// -->\n </SCRIPT>\n" );

    //StringBuffer Msgtemp=new StringBuffer();
    //Build the Java Script Here and Finish the thing
    out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
    out.println( "<!--\n" );
    out.println( "function sendSupplierData()\n" );
    out.println( "{\n" );
    radian.web.poHelpObj.buildshowClientBuys( db,lineID,itemID,"",out,res );
    out.println( "window.close();\n" );
    out.println( " }\n" );
    out.println( "// -->\n</SCRIPT>\n" );
    //out.println( Msgtemp );
    out.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	out.println( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
    out.println( "<CENTER><BR><BR>\n" );
    out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    out.println( "</FORM></BODY></HTML>\n" );
    //out.println( Msgbody );

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