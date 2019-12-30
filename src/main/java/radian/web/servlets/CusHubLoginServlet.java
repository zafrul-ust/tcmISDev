/*
 *  Copyright 1999-2000
 *
 *  URS Corporation, Radian
 *  All rights reserved
 */
package radian.web.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import radian.tcmis.server7.share.dbObjs.Password;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
//import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;

/**
 * LoginServlet should always be called as the target of a redirect from another
 * servlet. This servlet presents a login form to the user and receives the
 * submitted login information. The login information is authenticated by the
 * Password class. If authentication fails then the login form is again presented
 * to the user. If authentication succeeds the request is redirected to the servlet
 * originally requested by the user.
 *
 * @author    Chuck Simpson [chuck_simpson@haastcm.com]
 * @created   December 28, 2000
 */
public class CusHubLoginServlet extends HttpServlet
{

    /**
     * Initialize global variables
     *
     * @param config                Description of Parameter
     * @exception ServletException  Description of Exception
     */
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    /**
     * Process the HTTP Get request. Defers to the doPost() method.
     *
     * @param request               Object containing the request information
     * @param response              Object to receive the servlet's response
     * @exception ServletException
     * @exception IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        //String originatingURL;
        //if( (originatingURL = request.getParameter("originatingURL")) != null ) {
        //    request.getSession().setAttribute("originatingURL", originatingURL);
        //}
        doPost(request, response);
    }

    /**
     * Process the HTTP Post request.<p>
 * First checks to see if the originating servlet, (i.e. the servlet that issued
 * a redirect to this servlet), stored a valid URL so that this servlet can
 * redirect the request once the user is authenticated. If the URL is not valid
 * then send an error response.<p>
 *
 * Next determines whether this is the first or a subsequent call to this servlet.
 * If this is the first call the session attribute 'loginState' will not exist or
 * will have a value of 'initial' and the login form will be presented to the user.
 * If this is a subsequent call (session loginState will have the value 'challenge'
 * and the login information submitted by the user will be used to authenticate the user.<p>
 *
 * If the user is authenticated then the session loginState will be set to 'authenticated'
 * and the request will be redirected to the originating servlet after saving the login
 * information in the current session. Otherwise the session loginState retains the
 * value 'challenge' and the login form is redisplayed.
     *
     * @param request               Object containing the request information
     * @param response              Object to receive the servlet's response
     * @exception ServletException
     * @exception IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String errormsg = "";

        HttpSession session = request.getSession();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // validate the originating servlet's URL
        String originatingURL = (String) session.getValue("originatingURL");

        System.out.println(originatingURL);


        try {
              new URL(originatingURL);
            } catch(MalformedURLException e) {
           System.out.println("\n\n------------ can create new URL");
                             response.sendError(HttpServletResponse.SC_CONFLICT,
                                             "LoginServlet was either directly invoked or the URL of the originating page is malformed");
        }
        String state = (String)session.getValue("loginState");
        String loginId = (String)request.getParameter("login_id");
        String passwd = (String)request.getParameter("passwd");

        //TcmISDBModel db1 = new RayTcmISDBModel("Seagate");
        TcmISDBModel db1 = (TcmISDBModel)session.getValue("TcmISDBModel");

        //String mySo = (String)session.getValue("so");
        //String myAction = (String)session.getValue("action");
        //originatingURL += "?so="+mySo+"&action="+myAction;
        if( "challenge".equals(state) && loginId != null && passwd != null )
        {
            // Authenticate the user using the submitted information
            try
            {
                Password pw = new Password(db1, loginId, passwd);

                session.setAttribute("PERSONNELID", pw.getUserId());

                //System.out.println("Personnel Id " +pw.getUserId());

                int test_number = DbHelpers.countQuery(db1,"select count(*) from user_group_member where user_group_id = 'Receiving' and personnel_id = '"+pw.getUserId()+"'");

                int test_qc_number = DbHelpers.countQuery(db1,"select count(*) from user_group_member where user_group_id = 'Receiving QC' and personnel_id = '"+pw.getUserId()+"'");

                int test_numberInven = DbHelpers.countQuery(db1,"select count(*) from user_group_member where user_group_id = 'Inventory' and personnel_id = '"+pw.getUserId()+"'");

                //System.out.println("Number of records in user_group"+test_number);

                if( (pw.isAuthenticatedWeb()) )
                {
                    // The user is authenticated, set the login state and save the Password object as loginInfo
                    session.setAttribute("loginState", "authenticated");
                    session.setAttribute("loginInfo", pw);
                    session.setAttribute("loginId", loginId);

                    if (test_number >0)
                    {
                    session.setAttribute("Receiving", "YES");
                    }
                    else
                    {
                    session.setAttribute("Receiving", "NO");
                    }

                    if (test_numberInven >0)
                    {
                    session.setAttribute("Inventory", "YES");
                    }
                    else
                    {
                    session.setAttribute("Inventory", "NO");
                    }

                    if (test_qc_number >0)
                    {
                    session.setAttribute("ReceivingQC", "YES");
                    }
                    else
                    {
                    session.setAttribute("ReceivingQC", "NO");
                    }
                    // redirect this request back to the originating servlet
                    System.out.println("If it is here the paswwords Have matched");
                    response.sendRedirect(originatingURL);
                    return;
                }
                else
                {
                  errormsg += "Authentication Error.<BR>";
                  errormsg += "Please Enter your Login_Id, Password and try again.</font>\n";
                }
            }
            catch( Exception e)
            {
                e.printStackTrace();
                errormsg = "<font face=Arial size=3>Server Authentication Error<BR>\n";
                errormsg += "Please Enter your Login-Id, Password and try again.</font>\n";

            }
            finally
            {
            //db1.close();
            }
        }

        session.setAttribute("loginState", "challenge");
        StringBuffer body = new StringBuffer("<html>");
        body.append("<head><title>LoginServlet</title></head>");
        body.append("<body>");
        body.append("<font face=Arial size=5 color=\"#000080\">\n");
        body.append("<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n");
        body.append("&nbsp;&nbsp;&nbsp;");
        body.append("<B>tcmIS Web Login</B>\n");
        body.append("</font>\n");
        body.append("<BR>\n");
        body.append(errormsg);
        body.append("<BR>\n");
        body.append("<font face=Arial size=3><table  border=\"0\" width=\"60%\">\n");
        body.append("<form method=\"POST\" name=\"form1\">\n");
        body.append("<tr>\n");
        body.append("<td width=\"10%\"><B>Logon Id : </B></td>\n");
        body.append("<td width=\"10%\"><input type=\"text\" name=\"login_id\" value=\"\" size=\"10\"></td>\n");
        body.append("</tr><tr>\n");
        body.append("<td width=\"10%\"><B>Password : </B></td>\n");
        body.append("<td width=\"10%\"><input type=\"password\" name=\"passwd\" value=\"\" size=\"10\"></td>\n");
        body.append("</tr><tr>\n");
        body.append("<td width=\"10%\" align=\"right\"><input type=\"submit\" value=\"Login\" name=\"Login\"></td>\n");
        body.append("<td width=\"10%\" align=\"left\"><input type=\"reset\" value=\"Reset\" name=\"Reset\"></td>\n");
        body.append("</tr>\n");
        body.append("</form>\n");
        body.append("</TABLE></font>\n");
        body.append("</body></html>");
        out.println(body.toString());
        out.flush();
    }

    /**
     * Clean up resources before this servlet instance is destroyed.
     */
    public void destroy()
    {
    }
}
