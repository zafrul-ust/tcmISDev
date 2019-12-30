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

import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-21-02
 * fixed bug which was not showinf company name for raytheon
 * 04-07-03 - Adding the cookie to put personnel_id in the cookie
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark. Setting the domain by using the request.getServerName() method
 * 06-29-04 - Printing out free memory
 */

public class InternalUserHome  extends HttpServlet implements SingleThreadModel
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

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
        PrintWriter usrout = response.getWriter();
        response.setContentType("text/html");
        //RayTcmISDBModel db = null;
        HttpSession session = request.getSession();
		//reoprtlog.info("Free Memory   "+Runtime.getRuntime().freeMemory()/1000000+" ");
        try
        {
		 String authoritzation= session.getAttribute( "loginState" ) == null ? "" : session.getAttribute( "loginState" ).toString();
		 String auth ="";
         String errormsg = "";
		 if ( ! ( authoritzation.equalsIgnoreCase( "authenticated" ) ) )
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

			 Hashtable loginresult=new Hashtable();
			 loginresult=radian.web.loginhelpObj.logintopage( session,request,db,usrout );
			 auth= ( String ) loginresult.get( "AUTH" );
			 errormsg= ( String ) loginresult.get( "ERROSMSG" );

			 if ( auth == null )
			 {
			   auth="challenge";
			 }
			 else if ("authenticated".equalsIgnoreCase(auth))
			 {
				PersonnelBean personnelBean=session.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) session.getAttribute( "personnelBean" );
				personnelBean.setHome("hubhome");
				session.setAttribute( "personnelBean",personnelBean );
			 }

			 if ( errormsg == null )
			 {
			   errormsg="";
			 }

		   }
		   catch ( IOException ex )
		   {
			 ex.printStackTrace();
		   }
		   finally
		   {
			 db.close();
		   }
		 }
		 else
		 {
		   auth="authenticated";
		 }

        if (! ( auth.equalsIgnoreCase( "authenticated" ) ) )
        {
            usrout.println(radian.web.HTMLHelpObj.printLoginScreen(errormsg, "Hub Home"));
            usrout.flush();
        }
        else
        {
          String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
          int cookiecount=0;
          Cookie[] cookies = request.getCookies();
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

					String User_Action= request.getParameter("UserAction");
					if (User_Action == null)
					 User_Action = "New";

		PersonnelBean personnelBean=session.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) session.getAttribute( "personnelBean" );
  	String hubhome= "";
		if (personnelBean !=null)
		{
		 hubhome= personnelBean.getHome() == null ? "" : personnelBean.getHome();
		 if ( hubhome == null ){ hubhome="";}
		}
		else
		{
		 reoprtlog.info("authoritzation: "+authoritzation+" personnel ID: "+personnelid+"");
		}

		String CompanyID= session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
		String FullName= session.getAttribute( "FULLNAME" ) == null ? "" : session.getAttribute( "FULLNAME" ).toString();

		/*<forward name="danahubhome" path="/dana/index.jsp" />
			 <forward name="sdhubhome" path="/sd/index.jsp" />
			 <forward name="gmhubhome" path="/gm/index.jsp" />
			 <forward name="countinghubhome" path="/counting/index.jsp" />*/



		//String sdpage= session.getAttribute( "SDPAGE" ) == null ? "" : session.getAttribute( "SDPAGE" ).toString();
		String redirectto = "Home";
		//if ( sdpage == null ){ sdpage="";}
		if ("sdhubhome".equalsIgnoreCase(hubhome)){redirectto = "sd";}

		//String gmpage= session.getAttribute( "GMPAGE" ) == null ? "" : session.getAttribute( "GMPAGE" ).toString();
		//if ( gmpage == null ){ gmpage="";}
		if ("gmhubhome".equalsIgnoreCase(hubhome)){redirectto = "gm";}

		//String danapage= session.getAttribute( "DANAPAGE" ) == null ? "" : session.getAttribute( "DANAPAGE" ).toString();
		//if ( danapage == null ){ danapage="";}
		if ("danahubhome".equalsIgnoreCase(hubhome)){redirectto = "dana";}

		//String countingPage= personnelBean.getHubHome() == null ? "" : personnelBean.getHubHome();
		//if ( countingPage == null ){ countingPage="";}
		if ("countinghubhome".equalsIgnoreCase(hubhome)){redirectto = "counting";}

		if (personnelid.trim().length() == 0) {User_Action = "LOGOUT";}

		//StringBuffer MsgSB = new StringBuffer();
		if ("LOGOUT".equalsIgnoreCase(User_Action))
		{
		 session.setAttribute("loginState", "challenge");
		 session.removeAttribute("personnelBean");
		 session.invalidate();

		 usrout.println("<html><head>\n");
		 if (CompanyID.length() > 1 & !"Radian".equalsIgnoreCase(CompanyID))
		 {
			 usrout.println("<meta http-equiv=\"Refresh\" content=\"0; url=/tcmIS/Hub/"+redirectto+"/"+CompanyID+"\">\n");
			}
			else
			{
			 if ("countinghubhome".equalsIgnoreCase(hubhome))
			 {
				usrout.println("<meta http-equiv=\"Refresh\" content=\"0; url=/tcmIS/hub/counting/home.do\">\n");
			 }
			 else
			 {
				usrout.println("<meta http-equiv=\"Refresh\" content=\"0; url=/tcmIS/Hub/"+redirectto+"\">\n");
			 }
			}
      usrout.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">\n");
      String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
      usrout.println("<link rel=\"SHORTCUT ICON\" href=\""+httphost+"images/buttons/tcmIS.ico\"></link>\n");
      usrout.println("<meta http-equiv=\"Expires\" content=\"-1\">\n");
      usrout.println("<title>Hub Home</title>\n");
      usrout.println("</head>  \n");
      usrout.println("<body BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
      usrout.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
      usrout.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Logging Out ..</b></font><P></P><BR>\n");
      usrout.println("</CENTER>\n");
      usrout.println("</body></html>    \n");
      }
      else
      {
			 usrout.println("<html><head>\n");
			 if ("sdhubhome".equalsIgnoreCase(hubhome) ||"gmhubhome".equalsIgnoreCase(hubhome) || "danahubhome".equalsIgnoreCase(hubhome) || "countinghubhome".equalsIgnoreCase(hubhome))
			 {
				usrout.println("<meta http-equiv=\"Refresh\" content=\"0; url=/tcmIS/hub/counting/home.do\">\n");
			 }
			 else
			 {
				usrout.println("<meta http-equiv=\"Refresh\" content=\"0; url=/tcmIS/hub/home.do\">\n");
			 }
			 usrout.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">\n");
			 String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
			 usrout.println("<link rel=\"SHORTCUT ICON\" href=\""+httphost+"images/buttons/tcmIS.ico\"></link>\n");
			 usrout.println("<meta http-equiv=\"Expires\" content=\"-1\">\n");
			 usrout.println("<title>Hub Home</title>\n");
			 usrout.println("</head><body></body></html>\n");
		}
  }
}
catch (Exception e)
{
  e.printStackTrace();
}
finally
{
  //db.close();
  usrout.close();
  //Runtime.getRuntime().gc();
  return;
}
}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
    {
        doPost(request,response);
    }
}

