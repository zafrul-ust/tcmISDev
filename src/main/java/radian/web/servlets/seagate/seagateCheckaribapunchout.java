package radian.web.servlets.seagate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.seagate.dbObjs.SeagateTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.checkAribapunchout;

public class seagateCheckaribapunchout  extends HttpServlet
{
  public void init( ServletConfig config )  throws ServletException
  {
    super.init( config );
  }

  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
    SeagateTcmISDBModel db=null;
    String User_ID="";
    PrintWriter out=response.getWriter();
    response.setContentType( "text/html" );

    try
    {
      db=new SeagateTcmISDBModel( "Seagate" );
      if ( db == null )
      {
        PrintWriter out2=new PrintWriter( response.getOutputStream() );
        out2.println( "Starting DB" );
        out2.println( "DB is null" );
        out2.close();
        return;
      }

        checkAribapunchout obj=new checkAribapunchout( ( ServerResourceBundle )new SeagateServerResourceBundle(),db );
        obj.doResponse( request,response );
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

  public void doGet( HttpServletRequest request,
                     HttpServletResponse response )
     throws ServletException,
     IOException
  {
    doPost( request,response );
  }
}