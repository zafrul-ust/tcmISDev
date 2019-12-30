package radian.web.servlets.miller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.miller.dbObjs.MillerTcmISDBModel;
import radian.web.servlets.share.msdsSideView;

public class MillermsdsSideViewcorp
   extends HttpServlet
{

  public void init( ServletConfig config )
     throws ServletException
  {
    super.init( config );
  }

  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
    MillerTcmISDBModel db=null;
    try
    {
      db=new MillerTcmISDBModel( "Miller" );
      if ( db == null )
      {
        PrintWriter out2=new PrintWriter( response.getOutputStream() );
        out2.println( "Starting DB" );
        out2.println( "DB is null" );
        out2.close();
        return;
      }
      msdsSideView obj = new msdsSideView(new MillerServerResourceBundle(), db);
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

  public void doGet( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    doPost( request,response );
  }
}
