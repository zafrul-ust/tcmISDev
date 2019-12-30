package radian.web.servlets.utc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.utc.dbObjs.UTCTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.noMatmsdsView;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 * 03-12-04 Made Changes to accomodate Sikorsky MSDSs. These are MSDS with no material ID
 */

public class UTCnoMatmsdsView extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
   {
	 UTCTcmISDBModel db=null;
	 try
	 {
	   db=new UTCTcmISDBModel( "UTC" );
	   if ( db == null )
	   {
		 PrintWriter out2=new PrintWriter( response.getOutputStream() );
		 out2.println( "Starting DB" );
		 out2.println( "DB is null" );
		 out2.close();
		 return;
	   }
	   noMatmsdsView obj=new noMatmsdsView( ( ServerResourceBundle )new UTCServerResourceBundle(),db );
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
