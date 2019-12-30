package radian.web.servlets.dana;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
import radian.tcmis.server7.client.dana.dbObjs.DanaTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
import radian.web.servlets.share.projectManager;

public class DanaprojectManager extends HttpServlet implements SingleThreadModel
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

	public void doPost( HttpServletRequest request,HttpServletResponse response ) throws IOException,ServletException
	{
	  PrintWriter out=response.getWriter();
	  projectManager obj=null;
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


	  Connection myConnection=null;
	  DanaTcmISDBModel db=null;

	  try
	  {
		db=new DanaTcmISDBModel( "DANA" );

		if ( db == null )
		{
		  PrintWriter out2=new PrintWriter( response.getOutputStream() );
		  out2.println( "Starting DB" );
		  out2.println( "DB is null" );
		  out2.close();
		  return;
		}

		ServerResourceBundle bundlesd=new DanaServerResourceBundle();
		HeaderFooter hf=new HeaderFooter( bundlesd,db );

		Hashtable loginresult=new Hashtable();
		loginresult=radian.web.loginhelpObj.logintopage( session,request,db,out );
		String auth= ( String ) loginresult.get( "CLIENT_AUTH" );
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
		  out.println( hf.printclientLoginScreen( errormsg,"Projects","projects.gif",intcmIsApplication ) );
		  out.flush();
		}
		else
		{
		  obj = new projectManager(bundlesd,db);
		  if ( radian.web.HTMLHelpObj.hasaccessto( session,"ProjectMgmt" ) )
		  {
			obj.setupdateStatus( true );
		  }
		  else
		  {
			obj.setupdateStatus( false );
		  }

		  obj.doResult( request,response,session );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
	  }
	  finally
	  {
		obj=null;
		db.close();
		//Runtime.getRuntime().gc();
	  }
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doPost(request, response);
    }
}