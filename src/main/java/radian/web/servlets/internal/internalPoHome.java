package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-04-02
 *
 * Added links to Accounts Payable Pages
 * replaced dbrs.close with if (dbrs != null)  { dbrs.close(); }
 * 04-07-03 - Adding the cookie to put personnel_id in the cookie
 * 04-28-03
 * Showing only vv_payment_terms which are active
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark. Setting the domain by using the request.getServerName() method
 * 12-22-03 - Making the session Keys Unique to PO pages. And Code Refactoring moved vv_stuff to different class
 * 01-30-04 - Showing only Active Payment Terms for PO pages and showing all for AP pages
 * 03-15-04 - Changes for to support DBuy. Providing link for the printed PO List Page
 * 04-07-04 - Getting vv vector for statuses that are not assignable from the buy page.
 */

public class internalPoHome extends HttpServlet implements SingleThreadModel
{
	public void init( ServletConfig config )
		 throws ServletException
	{
		super.init( config );
	}

	private RayTcmISDBModel db=null;

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
		PrintWriter poout=response.getWriter();
		response.setContentType( "text/html" );
		HttpSession session=request.getSession();
		Cookie[] cookies=request.getCookies();

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
			loginresult=radian.web.loginhelpObj.logintopage( session,request,db,poout );
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
				poout.println( radian.web.HTMLHelpObj.printpoLoginScreen( errormsg,"Supply Home" ) );
				poout.flush();
			}
			else
			{
				String personnelid=session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
				int cookiecount=0;
				if ( cookies != null )
				{
					for ( int i=0; i < cookies.length; i++ )
					{
						Cookie cookie=cookies[i];
						if ( cookie != null )
						{
							String cookiename=cookie.getName();
							if ( "token_1".equalsIgnoreCase( cookiename ) )
							{
								cookie.setValue( personnelid );
								cookie.setPath( "/" );
								String hostdomain =request.getServerName() == null ? "" : request.getServerName();
								cookie.setDomain( hostdomain );
								cookiecount++;
							}
						}
					}
				}

				if ( cookiecount == 0 )
				{
					//System.out.println( "Adding New Cookie" );
					Cookie arg1okie=new Cookie( "token_1",personnelid );
					arg1okie.setPath( "/" );
					String hostdomain =request.getServerName() == null ? "" : request.getServerName();
					arg1okie.setDomain( hostdomain );
					response.addCookie( arg1okie );
				}

				String User_Action=request.getParameter( "UserAction" );
				if ( User_Action == null )
					User_Action="New";

				String CompanyID=session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
				String FullName=session.getAttribute( "FULLNAME" ) == null ? "" : session.getAttribute( "FULLNAME" ).toString();

				if ( personnelid.trim().length() == 0 )
				{
					User_Action="LOGOUT";
				}

				if ( "LOGOUT".equalsIgnoreCase( User_Action ) )
				{
					session.setAttribute( "loginState","challenge" );
				  session.removeAttribute("personnelBean");
					session.invalidate();

					poout.println( "<HTML><HEAD>\n" );
					if ( CompanyID.length() > 1 & !"Radian".equalsIgnoreCase(CompanyID) )
					{
						poout.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/supply/Home/" + CompanyID + "\">\n" );
					}
					else
					{
						poout.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/supply/Home\">\n" );
					}
					poout.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
					String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
					poout.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
					poout.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
					poout.println( "<TITLE>Supply Home</TITLE>\n" );
					poout.println( "</HEAD>  \n" );
					poout.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
					poout.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
					poout.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Logging Out ..</b></font><P></P><BR>\n" );
					poout.println( "</CENTER>\n" );
					poout.println( "</BODY></HTML>    \n" );
				}
				else
				{
				 poout.println("<html><head>\n");
				 poout.println("<meta http-equiv=\"Refresh\" content=\"0; url=/tcmIS/supply/home.do\">\n");
				 poout.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">\n");
				 String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
				 poout.println("<link rel=\"SHORTCUT ICON\" href=\""+httphost+"images/buttons/tcmIS.ico\"></link>\n");
				 poout.println("<meta http-equiv=\"Expires\" content=\"-1\">\n");
				 poout.println("<title>Supply Home</title>\n");
				 poout.println("</head><body></body></html>\n");
				}
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
				poout.close();
			}
			catch(Exception e) {
				//ignore
			}
			Runtime.getRuntime().gc();

			return;
		}
	}

	public void doGet( HttpServletRequest request,HttpServletResponse response )
		 throws ServletException,IOException
	{
		doPost( request,response );
	}


}
