package radian.web.suppliershipping;

import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Hashtable;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class supplierhome  extends HttpServlet implements SingleThreadModel
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

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
        PrintWriter out = response.getWriter();
        RayTcmISDBModel db = null;
        HttpSession session = request.getSession();
        response.setContentType( "text/html" );

        try
        {
          db = new RayTcmISDBModel("supplier");
          if (db == null)
          {
              PrintWriter out2 = new PrintWriter (response.getOutputStream());
              out2.println("Starting DB");
              out2.println("DB is null");
              out2.close();
              return;
          }

          Hashtable loginresult = new Hashtable();
          loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
          String auth = (String)loginresult.get("AUTH");
          String errormsg = (String)loginresult.get("ERROSMSG");

          if (auth == null) {auth = "challenge";}
          if (errormsg == null) {errormsg = "";}


          if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
          {
            out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Supplier Home" ) );
            out.flush();
          }
          else
          {
            Cookie[] cookies=request.getCookies();

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

            session.setAttribute( "SUPPLIERPAGE","Yes" );
			session.setAttribute( "COMPANYID","SUPPLIER" );
            String User_Action=request.getParameter( "UserAction" );
            if ( User_Action == null )
              User_Action="New";

            String CompanyID=session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
            String FullName=session.getAttribute( "FULLNAME" ) == null ? "" : session.getAttribute( "FULLNAME" ).toString();

            //StringBuffer MsgSB=new StringBuffer();
            if ( "LOGOUT".equalsIgnoreCase( User_Action ) )
            {
              session.setAttribute( "loginState","challenge" );
						  session.removeAttribute("personnelBean");
              session.invalidate();

              out.println( "<HTML><HEAD>\n" );
              if ( CompanyID.length() > 1 & !"SUPPLIER".equalsIgnoreCase(CompanyID) )
              {
                out.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/supplier/" + CompanyID + "\">\n" );
              }
              else
              {
                out.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/supplier\">\n" );
              }
              out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
              String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
              out.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
              out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
              out.println( "<TITLE>Supplier Home</TITLE>\n" );
              out.println( "</HEAD>  \n" );
              out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
              out.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
              out.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Logging Out ..</b></font><P></P><BR>\n" );
              out.println( "</CENTER>\n" );
              out.println( "</BODY></HTML>    \n" );
            }
            else
            {
              out.println( "<HTML><HEAD>\n" );
              out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
              String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
              out.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
              out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
              out.println( "<TITLE>Supplier Home</TITLE>\n" );
              out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
							/*This handels which key press events are disabeled on this page*/
							out.println("<script src=\"/js/common/disableKeys.js\" language=\"JavaScript\"></script>\n");
							/*This handels the menu style and what happens to the right click on the whole page */
							out.println("<script type=\"text/javascript\" src=\"/js/menu/milonic_src.js\"></script>\n");
							out.println("<script type=\"text/javascript\" src=\"/js/menu/mmenudom.js\"></script>\n");
							out.println("<script type=\"text/javascript\" src=\"/js/menu/mainmenudata.js\"></script>\n");
							out.println("<script type=\"text/javascript\" src=\"/js/menu/contextmenu.js\"></script>\n");

							PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
							Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
							if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
							{
			         out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/css/hidetopnavigation.css\"></LINK>\n");
							}

              out.println( "</HEAD>  \n" );
              out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
              out.println( radian.web.HTMLHelpObj.printSupplierTitleBar( "Supplier Home" ) );
              out.println( "<form method=\"POST\" name=\"form1\">\n" );
              out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"LOGOUT\">\n" );
              out.println( "<TABLE WIDTH=\"800\" BORDER=\"0\" CLASS=\"moveup\">\n" );
              out.println( "<TR VALIGN=\"TOP\"><TD BGCOLOR=\"#E6E8FA\" HEIGHT=\"400\">\n" );
              out.println( "<TABLE WIDTH=\"150\" BORDER=\"0\" CLASS=\"moveup\">\n" );
              out.println( "<TR><TD>&nbsp;</TD></TR><TR><TD>&nbsp;</TD></TR><tr>\n" );
              out.println( "<td width=\"10%\" align=\"CENTER\"><input CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Log Out\" name=\"Login\"></td>\n" );
              out.println( "</tr>\n" );


              out.println( "<tr>\n" );
              out.println( "<td width=\"10%\" HEIGHT=\"50\" align=\"CENTER\" valign=\"BOTTOM\"><A HREF=\"/tcmIS/Invoice/ChangePassword?whichHome=supplier\" STYLE=\"color:#e86915\">Change Password</A></td>\n" );
              out.println( "</tr>\n" );


              out.println( "<TR><TD>&nbsp;</TD></TR><TR><TD>&nbsp;</TD></TR><TR><TD CLASS=\"bluel\">\n" );
              out.println( "<FONT SIZE=\"2\"></FONT></TD></TR></form>\n" );
              out.println( "</TABLE>\n" );
              out.println( "</TD>\n" );
              out.println( "<TD WIDTH=\"650\" HEIGHT=\"400\" VALIGN=\"TOP\">\n" );
              out.println( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
              String psuedoCommpany="";
              if ( "Radian".equalsIgnoreCase( CompanyID ) )
              {
                psuedoCommpany="Haas TCM";
              }
              else
              {
                psuedoCommpany=CompanyID;
              }
              out.println( "<TR><TD WIDTH=\"550\" COLSPAN=\"3\"><FONT FACE=\"Arial\" size=\"3\">Logged in as: <B>" + FullName + "</B></TD><TR><TD>&nbsp;</TD><TR align=\"center\">\n" );
              out.println( "<TD width=\"33%\"  height=\"38\"></TD>\n" );
              out.println( "<TD width=\"33%\"  height=\"38\"></TD>\n" );
              out.println( "<TD width=\"33%\"  height=\"38\"></TD>\n" );
              out.println( "</TR>\n" );
              out.println( "<TR>\n" );
              out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\"  height=\"30\">\n" );
              out.println( "<A HREF=\"/tcmIS/supplier/suppliershipping\" STYLE=\"color:#e86915\">Shipping</A></TD>\n" );
              out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println( "&nbsp;</TD>\n" );
              out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println( "&nbsp;</TD>\n" );
              out.println( "</TR>\n" );
              out.println( "</TABLE>\n" );
              out.println( "</TD>\n" );
              out.println( "</TR></TABLE>\n" );
              out.println( "</BODY></HTML>    \n" );
            }
            //out.println( MsgSB );
            out.close();
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
            out.close();
          }
          catch(Exception e) {
            //ignore
          }
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

