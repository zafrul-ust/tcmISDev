package radian.web.servlets.ba;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ba.dbObjs.BATcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;

public class BAWasteVendorUpdate extends HttpServlet
{

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
    private String orderno = "";
    private String borc = "";
    private String Status = "";
    private String Session = "";
    private String order_text = "30";
    private String order_Drop = "";
    private String order_Form = "";
    private String vendor_shp_id = "";
    private String plan_shp_dt = "";
    private String Shp_id = "";
    private String Vendor_Id = "";
    private String open_order = "";
    private String old_order = "";
    private int te = 0;

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
	 BATcmISDBModel db=null;
	 PrintWriter out=response.getWriter();
	 HttpSession session=request.getSession();
	 boolean intcmIsApplication = false;
 	try
	 {
	   db=new BATcmISDBModel( "BA" );
	   if ( db == null )
	   {
		 PrintWriter out2=new PrintWriter( response.getOutputStream() );
		 out2.println( "Starting DB" );
		 out2.println( "DB is null" );
		 out2.close();
		 return;
	   }

	   ServerResourceBundle bundleBA=new BAServerResourceBundle();
	   HeaderFooter hf=new HeaderFooter( bundleBA,db );

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
		 out.println( hf.printclientLoginScreen( errormsg,"Waste Ordes","tcmistcmis32.gif",intcmIsApplication ) );
		 out.flush();
	   }
	   else
	   {
		 WasteVendorUpdate obj=new WasteVendorUpdate( ( ServerResourceBundle )new BAServerResourceBundle(),db );
		 String loginId=BothHelpObjs.makeBlankFromNull( ( String ) session.getAttribute( "loginId" ) );
		 Status=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "Session" ) );

		 if ( ( Status == "null" )
			  || ( Status.equalsIgnoreCase( "" ) )
			  || ( Status.length() == 0 ) )
		 {
		   Status="0"; //Nawaz 04-12-01
		 }

		 session.setAttribute( "SESSION",Status );

		 try
		 {
		   orderno=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "order_no" ) );
		   order_text=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "nodaysold" ) );
		   vendor_shp_id=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "vendor_shp_id" ) );
		   plan_shp_dt=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "plan_shp_dt" ) );
		   Shp_id=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "Shp_id" ) );
		   Vendor_Id=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "vendor_id" ) );
		   open_order=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "openorders" ) );
		   old_order=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "numberofdays" ) );
		   borc=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "borc" ) );
		 }
		 catch ( Exception e )
		 {
		   e.printStackTrace();
		 }

		 {
		   try
		   {
			 int testint=Integer.parseInt( orderno );
		   }
		   catch ( Exception e )
		   {
			 //System.out.println( "ERROR!!!!! If it is here the order_no is 0 Now" );
			 orderno="0";
		   }
		   try
		   {
			 int testint=Integer.parseInt( Shp_id );
		   }
		   catch ( Exception e )
		   {
			 //System.out.println( "ERROR!!!!! If it is here the Ship_ID is 0 Now" );
			 Shp_id="0";
		   }
		 }

		 // Here is status is null the shipment details are built
		 if ( ( Status.equalsIgnoreCase( "0" ) ) )
		 {
		   Hashtable tmp1=new Hashtable();
		   tmp1.put( "SHP_ID",Shp_id );
		   tmp1.put( "ORDER_NO",orderno );
		   tmp1.put( "BORC",borc );
		   session.setAttribute( "ALL_I_NEED",tmp1 );
		   //System.out.println( "This is the hash in session 0 " + tmp1 );
		   obj.doPost( request,response );
		   return;
		 }
		 // SESSION = 2 MEANS THE REQUEST IS COMING FROM THE SEARCH BUTTOM ON THE PAGE.
		 else if ( ( Status.equalsIgnoreCase( "2" ) ) )
		 {
		   Hashtable tmp1=new Hashtable();
		   tmp1.put( "OPEN_ORDER",open_order );
		   tmp1.put( "OLD_ORDER",old_order );
		   tmp1.put( "OLD_ORDER_DAYS",order_text );
		   tmp1.put( "VENDOR_ID",Vendor_Id );
		   session.setAttribute( "ALL_I_NEED",tmp1 );
		   //System.out.println( "This is the hash in session 2 " + tmp1 );
		   obj.doPost( request,response );
		   return;
		 }
		 // Session = 4 means the request is for Updateing the records.
		 else if ( Status.equalsIgnoreCase( "4" ) )
		 {
		   Hashtable tmp1=new Hashtable();
		   tmp1.put( "VENDOR_SHP_ID",vendor_shp_id );
		   tmp1.put( "PLAN_SHP_DT",plan_shp_dt );
		   tmp1.put( "BORC",borc );
		   tmp1.put( "SHP_ID",Shp_id );
		   tmp1.put( "ORDER_NO",orderno );
		   session.setAttribute( "ALL_I_NEED",tmp1 );
		   obj.doPost( request,response );
		   return;
		 }

		 Hashtable tmp1=new Hashtable();
		 tmp1.put( "ORDER_NO",orderno );
		 tmp1.put( "SHP_ID",Shp_id );
		 session.setAttribute( "ALL_I_NEED",tmp1 );
		 session.setAttribute( "SESSION","0" );
		 obj.doPost( request,response );
		 return;
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
	 }
}

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        doPost(request,response);
    }
}
