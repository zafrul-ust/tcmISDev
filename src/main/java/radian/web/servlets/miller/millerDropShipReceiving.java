package radian.web.servlets.miller;

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
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
import radian.web.servlets.share.dropShipReceiving;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 10-24-03 - Getting DONETESTCOUNT every time somebody access the page. So that people at the hub can go from one client to another
 * 12-22-03 - Using ClientAuth parameter from the session instead of of Auth. So that when people login to clinet page first and then go to Hub Home they are ok
 */

public class millerDropShipReceiving extends HttpServlet implements SingleThreadModel
{
  public void init( ServletConfig config ) throws ServletException
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

  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
    PrintWriter out=response.getWriter();
    RayTcmISDBModel db=null;
    dropShipReceiving obj=null;

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

    try
    {
      db=new RayTcmISDBModel( "Miller" );
      if ( db == null )
      {
        PrintWriter out2=new PrintWriter( response.getOutputStream() );
        out2.println( "Starting DB" );
        out2.println( "DB is null" );
        out2.close();
        return;
      }

      ServerResourceBundle bundlemiller=new MillerServerResourceBundle();
      HeaderFooter hf=new HeaderFooter( bundlemiller,db );

      Hashtable loginresult = new Hashtable();
      loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
      String auth = (String)loginresult.get("CLIENT_AUTH");
      String errormsg = (String)loginresult.get("ERROSMSG");

      if (auth == null) {auth = "challenge";}
      if (errormsg == null) {errormsg = "";}

      if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
      {
        session.setAttribute( "clientloginState","challenge" );
        out.println( hf.printclientLoginScreen( errormsg,"Dropship Receiving","tcmisclientreceiving.gif",intcmIsApplication ) );
        out.flush();
      }
      else
      {
        String donetestocunt=BothHelpObjs.makeBlankFromNull( ( String ) session.getAttribute( "DONETESTCOUNT" ) );
        if ( donetestocunt == null )
        {
          donetestocunt="No";
        }

        obj=new dropShipReceiving( bundlemiller,db );

        if ( !"yes".equalsIgnoreCase( donetestocunt ) )
        {
          String personnelid= session.getAttribute( "CLIENTPERSONNELID" ) == null ? "" : session.getAttribute( "CLIENTPERSONNELID" ).toString();

          int testCount=DbHelpers.countQuery( db,"select count(*) from user_group_member_location where personnel_id = " + personnelid + "" );
          if ( testCount > 0 )
          {
            obj.setupdateStatus( true );
            //session.setAttribute( "DONETESTCOUNT","yes" );
            session.setAttribute( "UPDATERECEIVE","yes" );
          }
          else
          {
            obj.setupdateStatus( false );
            //session.setAttribute( "DONETESTCOUNT","yes" );
            session.setAttribute( "UPDATERECEIVE","No" );
          }
        }

        String updatestatus= ( String ) session.getAttribute( "UPDATERECEIVE" );
        if ( "yes".equalsIgnoreCase( updatestatus ) )
        {
          obj.setupdateStatus( true );
        }
        else
        {
          obj.setupdateStatus( false );
        }

         obj.doPost( request,response );
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
      return;
    }
  }

  public void doGet( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    doPost( request,response );
  }
}
