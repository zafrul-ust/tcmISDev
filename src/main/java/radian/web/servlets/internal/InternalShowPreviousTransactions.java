package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

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
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.InventoryTransactions;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 11-13-03 - Calling a different method if the inventory transfer details are sought
 */

public class InternalShowPreviousTransactions  extends HttpServlet implements SingleThreadModel
{
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

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        PrintWriter out = response.getWriter();
				response.setContentType("text/html");
        RayTcmISDBModel db = null;
        InventoryTransactions obj = null;
        HttpSession session = request.getSession();
				PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
				boolean intcmIsApplication = false;
				if (personnelBean !=null)
				{
					 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
					 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
						intcmIsApplication = true;
					 }
				}


        try
        {
          db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
          if (db == null)
          {
              PrintWriter out2 = new PrintWriter (response.getOutputStream());
              out2.println("Starting DB");
              out2.println("DB is null");
              out2.close();
              return;
          }

        Hashtable loginresult = new Hashtable();
        loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
        String auth = (String)loginresult.get("AUTH");
        String errormsg = (String)loginresult.get("ERROSMSG");

        if (auth == null) {auth = "challenge";}
        if (errormsg == null) {errormsg = "";}

        if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
        {
          out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Inventory Transactions" ) );
          out.flush();
        }
        else
        {
          String branch_plant=request.getParameter( "HubName" );
          if ( branch_plant == null )
            branch_plant="";

		  String usactions=request.getParameter( "UserAction" );
		  if ( usactions == null )
		  usactions="";

		  String transreqid=request.getParameter( "transreqid" );
		  if ( transreqid == null )
		  transreqid="";

          obj=new InventoryTransactions( ( ServerResourceBundle )new InternalServerResourceBundle(),db );
          //StringBuffer Msg=new StringBuffer();

          out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Inventory Transactions",intcmIsApplication ) );
          out.println( "<SCRIPT SRC=\"/clientscripts/receivingjs.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n" );
          out.println( "<BODY>\n" );
		  if ("tranregeceiving".equalsIgnoreCase(usactions))
		  {
			out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Transactions for Transfer Request Id: "+transreqid+"\n" ) );
		  }
		  else
		  {
			out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Inventory Transactions\n" ) );
		  }
          String receiptid=BothHelpObjs.makeBlankFromNull( request.getParameter( "receiptid" ) );
          String mfglot=BothHelpObjs.makeBlankFromNull( request.getParameter( "mfglot" ) );

          out.println( "<BR>" );
		  if ("tranregeceiving".equalsIgnoreCase(usactions))
		  {
			obj.buildtransDetails(transreqid,out);
		  }
		  else
		  {
			obj.buildDetails( "","",branch_plant,"All",receiptid,mfglot,out);
		  }
          out.println( "<BR><BR>" );
          out.println( "<CENTER><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\"></CENTER>\n" );
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
        obj=null;
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
    //
  }
