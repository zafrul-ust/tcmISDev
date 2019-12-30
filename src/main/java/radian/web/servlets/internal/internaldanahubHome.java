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
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class internaldanahubHome  extends HttpServlet implements SingleThreadModel
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

				String company=request.getPathInfo();
				if ( company != null )
				{
					 company=company.substring( 1 ).toUpperCase();
					 if ("Radian".equalsIgnoreCase(company))
					 {
						 company="Radian";
					 }
				}
				else
				{
					 company="";
				}

				if (!"DANA".equalsIgnoreCase(company))
				{
				radian.web.HTMLHelpObj.redirectPage(out,"tcmIS/hub/counting.do",2006,11,1);
				return;
				}

        try
        {
          db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
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
            out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Dana Hub Home" ) );
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

            session.setAttribute( "DANAPAGE","Yes" );
						PersonnelBean personnelBean=session.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) session.getAttribute( "personnelBean" );
						personnelBean.setHome("danahubhome");
						session.setAttribute( "personnelBean",personnelBean );

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
              if ( CompanyID.length() > 1 & !"Radian".equalsIgnoreCase(CompanyID) )
              {
                out.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/Hub/dana/" + CompanyID + "\">\n" );
              }
              else
              {
                out.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/Hub/dana\">\n" );
              }
              out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
              String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
              out.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
              out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
              out.println( "<TITLE>Dana Hub Home</TITLE>\n" );
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
              out.println( "<TITLE>Dana Hub Home</TITLE>\n" );
              out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
              out.println( "</HEAD>  \n" );
              out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
              out.println( radian.web.HTMLHelpObj.PrintTitleBar( "Dana Hub Home" ) );
              out.println( "<form method=\"POST\" name=\"form1\">\n" );
              out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"LOGOUT\">\n" );
              out.println( "<TABLE WIDTH=\"800\" BORDER=\"0\" CLASS=\"moveup\">\n" );
              out.println( "<TR VALIGN=\"TOP\"><TD BGCOLOR=\"#E6E8FA\" HEIGHT=\"400\">\n" );
              out.println( "<TABLE WIDTH=\"150\" BORDER=\"0\" CLASS=\"moveup\">\n" );
              out.println( "<TR><TD>&nbsp;</TD></TR><TR><TD>&nbsp;</TD></TR><tr>\n" );
              out.println( "<td width=\"10%\" align=\"CENTER\"><input CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Log Out\" name=\"Login\"></td>\n" );
              out.println( "</tr>\n" );

              if ( "Radian".equalsIgnoreCase( CompanyID ) )
              {
                out.println( "<tr>\n" );
                out.println( "<td width=\"10%\" HEIGHT=\"50\" align=\"CENTER\" valign=\"BOTTOM\"><A HREF=\"/tcmIS/Invoice/ChangePassword?whichHome=Hub\" STYLE=\"color:#e86915\">Change Password</A></td>\n" );
                out.println( "</tr>\n" );
              }

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
              out.println( "<TR><TD WIDTH=\"550\" COLSPAN=\"3\"><FONT FACE=\"Arial\" size=\"3\">Logged in as: <B>" + FullName + "</B> for Company : <B>" + psuedoCommpany + "</B></TD><TR><TD>&nbsp;</TD><TR align=\"center\">\n" );
              out.println( "<TD width=\"33%\"  height=\"38\"></TD>\n" );
              out.println( "<TD width=\"33%\"  height=\"38\"></TD>\n" );
              out.println( "<TD width=\"33%\"  height=\"38\"></TD>\n" );
              out.println( "</TR>\n" );
              out.println( "<TR>\n" );
              out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\"  height=\"30\">\n" );
              out.println( "<A HREF=\"/tcmIS/hub/receiving.do\" STYLE=\"color:#e86915\">Receiving</A></TD>\n" );
              out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println( "<A HREF=\"/tcmIS/Hub/itemcount\" STYLE=\"color:#e86915\">Item Count</A></TD>\n" );
              out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println( "<A HREF=\"/tcmIS/hub/iteminventory\" STYLE=\"color:#e86915\">Item Management</A></TD>\n" );
              out.println( "</TR>\n" );
              out.println( "<TR>\n" );
              out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println( "<A HREF=\"/tcmIS/hub/receivingqc.do\" STYLE=\"color:#e86915\">Receiving QC</A></TD>\n" );
              out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println( "<A HREF=\"/tcmIS/Hub/Transactions\" STYLE=\"color:#e86915\">Transactions</A></TD>\n" );
              out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println( "<A HREF=\"/tcmIS/Hub/Logistics\" STYLE=\"color:#e86915\">Logistics</A></TD>\n" );
              out.println( "</TR>\n" );
              out.println( "<TR>\n" );
			  out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println( "<A HREF=\"/tcmIS/Hub/LevelMgmt\" STYLE=\"color:#e86915\">Level Management</A></TD>\n" );
              out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println( "<A HREF=\"/tcmIS/hub/minmaxchg\" STYLE=\"color:#e86915\">Minmax Levels</A></TD>\n" );
              out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\"  onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
              out.println("<A HREF=\"/tcmIS/dana/Register\" STYLE=\"color:#e86915\" TARGET=\"dana\">Dana Catalog</A></TD>\n");
			  out.println( "</TR>\n" );
			  out.println( "<TR>\n" );
			  out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
			  out.println( "<A HREF=\"/tcmIS/hub/showmonthlyinventorydetail.do\" STYLE=\"color:#e86915\">Monthly Inventory Detail</A></TD>\n" );
			  out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
			  //out.println( "Cost Book Usage</TD>\n" );
			  out.println( "<A HREF=\"/tcmIS/hub/showusagetrend.do\" STYLE=\"color:#e86915\">Cost Book Usage</A></TD>\n" );
			  out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
				out.println( "<A HREF=\"/tcmIS/hub/peiprojectlist.do\" STYLE=\"color:#e86915\">PEI Projects</A>\n");
			  out.println( "</TD>\n" );
			  out.println( "</TR>\n" );

 		    /*out.println( "<TR>\n" );
			  out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\"  height=\"30\">\n" );
			  out.println( "<A HREF=\"/tcmIS/Hub/Receiving\" STYLE=\"color:#e86915\">Old Receiving</A></TD>\n" );
			  out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
        out.println( "<A HREF=\"/tcmIS/Hub/ReceivingQC\" STYLE=\"color:#e86915\">Old Receiving QC</A></TD>\n" );
			  out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
        out.println( "</TD>\n" );
			  out.println( "</TR>\n" );*/

		    out.println( "</TR>\n" );
				out.println( "<TR>\n" );
				out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
				out.println( "<A HREF=\"/tcmIS/hub/dailyinventoryreport.do\" STYLE=\"color:#e86915\">Daily Inventory Report</A></TD>\n" );
				out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
				out.println( "<A HREF=\"/tcmIS/hub/showdistributedcount.do\" STYLE=\"color:#e86915\">Distributed Counts</A>\n");
				out.println( "<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"33%\" height=\"30\">\n" );
				out.println( "</A>\n");
				out.println( "</TD>\n" );
				out.println( "</TR>\n" );

              out.println( "</TABLE>\n" );

			  if ("Radian".equalsIgnoreCase(CompanyID))
			  {
				out.println("<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
				out.println( "<TR><TH WIDTH=\"550\" COLSPAN=\"4\" ALIGN=\"CENTER\" height=\"30\">Supply Pages</TH></TR>\n" );
				out.println( "<TR>\n" );
				out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n" );
				out.println( "<A HREF=\"/tcmIS/supply/purchorder\" STYLE=\"color:#e86915\" TARGET=\"purchorder\">Purchase Order</A></TD>\n" );
				out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n" );
				out.println( "<A HREF=\"/tcmIS/supply/Buyorder\" STYLE=\"color:#e86915\" TARGET=\"buyorders\">Buy Orders</A></TD>\n" );
				out.println( "<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n" );
				out.println( "<A HREF=\"/tcmIS/purchase/popo?comingfrom=seperate&Action=searchlike&subUserAction=po\" STYLE=\"color:#e86915\" TARGET=\"searchpos\">Search POs</A></TD>\n" );
				out.println("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
				out.println( "<A HREF=\"/tcmIS/Invoice/AccountsPayable\" STYLE=\"color:#e86915\" TARGET=\"accopayab\">Accounts Payable</A></TD>\n" );
				out.println( "</TR>\n" );
				out.println( "</TABLE>\n" );
			  }
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