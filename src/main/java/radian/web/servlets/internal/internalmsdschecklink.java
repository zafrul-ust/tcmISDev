package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.MSDSCheckLink;
import radian.web.servlets.ray.RayServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 */

public class internalmsdschecklink  extends HttpServlet implements SingleThreadModel
{
    private RayTcmISDBModel db = null;
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    /**
    * HTTP POST handler.
    *
    * @param request               An object implementing the request context
    * @param response              An object implementing the response context
    * @exception ServletException  A wrapper for all exceptions thrown by the
    *      servlet.
    */

   public void doPost( HttpServletRequest request,HttpServletResponse response )
      throws ServletException,IOException
   {
     PrintWriter out=response.getWriter();
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


     response.setContentType( "text/html" );

     try
     {
       db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
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
         out.println( radian.web.HTMLHelpObj.printCatalogLoginScreen( errormsg,"Catalog Home" ) );
         out.flush();
       }
	   else
	   {
      String action =request.getParameter( "action" );
      if ( action == null )
      action="New";
      action = action.trim();

     if (action.equalsIgnoreCase("checkMsds"))
     {
       MSDSCheckLink obj = null;
       obj=new MSDSCheckLink( (ServerResourceBundle)new RayServerResourceBundle(),db );
       obj.doPost( request,response,"Ray" );
     }
     else
     {
     //StringBuffer MsgSB=new StringBuffer();
		 out.println( radian.web.HTMLHelpObj.printHTMLHeader( "MSDS Check Online","msdschecklink.js",intcmIsApplication ) );
		 out.println( "<BODY>  \n" );
		 out.println( radian.web.HTMLHelpObj.PrintCatalogTitleBar( "MSDS Check Online" ) );

		 if ( radian.web.HTMLHelpObj.hasaccessto( session,"msdscheckonline" ) )
		 {
		   out.println( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
		   out.println( "<TR>\n" );
		   out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\"  height=\"30\">\n" );
		   out.println( "<A HREF=\"/tcmIS/msds/checklink?action=checkMsds\" STYLE=\"color:#e86915\" TARGET=\"checklinkdrs\">MSDS</A></TD>\n" );
			 out.println( "</TR>\n" );
		   out.println( "</TABLE>\n" );
		 }
		 else
		 {
		   out.println( radian.web.HTMLHelpObj.printMessageinTable( "You don't have access to this page." ) );
		 }
     out.println( "</BODY></HTML>    \n" );
     }
		 //out.println( MsgSB );
		 out.close();
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	   return;
	 }
	 finally
	 {
	   db.close();
           try {
             out.close();
           }
           catch(Exception e) {
             //ignore
           }
           //Runtime.getRuntime().gc();
	   return;
	 }
   }

   public void doGet( HttpServletRequest request,HttpServletResponse response )
      throws ServletException,IOException
   {
     doPost( request,response );
   }


 }