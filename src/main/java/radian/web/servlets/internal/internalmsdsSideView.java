package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.msdsSideView;

public class internalmsdsSideView extends HttpServlet {

  public void init( ServletConfig config )
     throws ServletException
  {
    super.init( config );
  }

   public void doPost( HttpServletRequest request,HttpServletResponse response )
      throws ServletException,IOException
   {
     RayTcmISDBModel db=null;
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
       msdsSideView obj=new msdsSideView( ( ServerResourceBundle )new InternalServerResourceBundle(),db );
       obj.doPost( request,response );
     }
     catch ( Exception e )
     {
       e.printStackTrace();
     }
     finally
     {
       db.close();
       //Runtime.getRuntime().gc();
     }
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      doPost(request,response);
   }
}
