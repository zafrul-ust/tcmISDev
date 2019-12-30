package radian.web.servlets.ray;

/**
 * Title:        Web Page for Usage Report
 * Description:  This webpage will allow the courtomers to
 * create chemical reports from the web page
 * rather than loging in to tcmIS.
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corp
 * @author Nawaz Shaik
 * @version 1.0
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.UsageRptWebPage;

public class RayUsageRptWebPage extends HttpServlet
{

  public void init( ServletConfig config ) throws ServletException
  {
	super.init( config );
  }

  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
	RayTcmISDBModel db=null;
	try
	{
	  db=new RayTcmISDBModel( "Ray" );
	  if ( db == null )
	  {
		PrintWriter out2=new PrintWriter( response.getOutputStream() );
		out2.println( "Starting DB" );
		out2.println( "DB is null" );
		out2.close();
		return;
	  }
	  UsageRptWebPage obj=new UsageRptWebPage( ( ServerResourceBundle )new RayServerResourceBundle(),db );
	  obj.doPost( request,response );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  db.close();
	}
  }

  public void doGet( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
	doPost( request,response );
  }
}