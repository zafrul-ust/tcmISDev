package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

// * 06-22-05 - Showing the help link only if a person is logged in

public abstract class ClientHome extends HttpServlet implements SingleThreadModel
{
  private boolean intcmIsApplication = false;
    //
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
        IOException
    {
        doPost(request,response);
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {

      //System.out.println("Here");
      PrintWriter out = null;
      TcmISDBModel db = null;
      //generate response
      response.setContentType("text/html");
      out = new PrintWriter (response.getOutputStream());
	  HttpSession loginSession=request.getSession();
      //StringBuffer MsgER = new StringBuffer();

      String newWebApplication="";
      try
      {
        newWebApplication = getBundle().get( "NEW_WEB_APPLICATION" ).toString();
      }
      catch (Exception e)
      {

      }
      
      if ("Yes".equalsIgnoreCase(newWebApplication))
      {
      radian.web.HTMLHelpObj.redirectPage(out,"" + getBundle().get("HOME_SERVLET").toString().substring(1) + "",2008,5,1);
			return;
      }

			 out.println( "<html><head>\n" );
			 out.println( "<meta http-equiv=\"Pragma\" content=\"no-cache\">\n" );
			 out.println( "<meta http-equiv=\"Refresh\" content=\"0; url=" + getBundle().getString("WEBS_HOME_WWWS") + "" + getBundle().getString("REGISTER_SERVLET") + "\">\n" );
       out.println( "<link rel=\"SHORTCUT ICON\" href=\"" + getBundle().getString("WEBS_HOME_WWWS") +  "images/buttons/tcmIS.ico\"></link>\n" );
       out.println( "<meta http-equiv=\"Expires\" content=\"-1\">\n" );
       out.println( "<title>tcmIS Redirect Page</title>\n" );
       out.println( "<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheets/global.css\"></link>\n" );
       out.println( "<script src=\"/clientscripts/redirect.js\" language=\"JavaScript\"></script>\n" );
       out.println( "</head>  \n" );
       out.println( "<body bgcolor=\"#FFFFFF\" text=\"#000000\">  \n" );
       out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "tcmIS Redirect Page" ) );
       out.println("<P>&nbsp;</P>\n");
       out.println("<P>&nbsp;</P>\n");
       out.println("<P>&nbsp;</P>\n");
       out.println("<DIV ALIGN=\"center\"><FONT FACE=\"Arial\" SIZE=\"3\"> <IMG SRC=\"/images/tcmintro.gif\"></FONT></DIV>\n");
       out.println( "</body></html>    \n" );
       out.close();

/*      PersonnelBean personnelBean = (PersonnelBean) loginSession.getAttribute("personnelBean");
			if (personnelBean !=null)
			{
				 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
				 intcmIsApplication = false;
				 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
					intcmIsApplication = true;
				 }
			}

      HeaderFooter hf = new HeaderFooter(getBundle(),db);
      //HEAD
      out.println(hf.printHTMLHeader("New TcmIS Home",intcmIsApplication));
      //Top Tool Bars
      out.println(hf.printTopToolBar("","tcmishome.gif",loginSession));

     out.println("<TABLE cellspacing=0 border=0 width=725 height=\"400\">\n");
     out.println("    <TR VALIGN=\"TOP\">\n");
     out.println("<TD HEIGHT=\"725\" BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
     out.println("<P>&nbsp;</P>\n");
     //out.println("<P>&nbsp;</P>\n");
	 *//*out.println("<BR><FONT FACE=\"Arial\" SIZE=\"3\"><B><U>Data Center Move</U></B><BR><BR>As most of you know, tcmIS is operated from a professional hosting facility.  This facility provides us with a superb infrastructure to operate -redundant power, high level physical security, redundant switches and routers, and expandable bandwidth connected to 10 Internet providers.<BR><BR>");
	 out.println("Our hosting company is moving its operation to another location with an even better infrastructure.  Unfortunately, this will mean that our servers, routers and firewalls will need to be transferred to this new location.  <B>This move is planned for this Friday evening, December 3, 2004.  We plan to turn off all of our systems at 7:00 PM CST Friday Dec 3, 2004 and plan on being back up at the new location by 7:00 AM Saturday CST Dec 4, 2004.</B><BR><BR>");
	 out.println("We hope this does not cause any inconvenience.</FONT><BR><BR>");*//*

	 String client=getBundle().get( "DB_CLIENT_NAME" ).toString();
	 radian.web.loginhelpObj.buildcomments(client,0,out);

     out.println("<DIV ALIGN=\"center\"><FONT FACE=\"Arial\" SIZE=\"3\"> <IMG SRC=\"/images/tcmintro.gif\"></FONT></DIV>\n");
     out.println("<P>&nbsp;</P>\n");
     out.println("<P>&nbsp;</P>\n");
     out.println("<P>&nbsp;</P>\n");
     out.println("      </TD>\n");
     out.println("    </TR>\n");
     out.println("  </TABLE>\n");
     out.println(hf.printFooter());
     //out.println(MsgER);
     //System.out.println("Here");

     out.close();*/
    }

    protected abstract String  getClient();
    protected abstract ServerResourceBundle getBundle();
}