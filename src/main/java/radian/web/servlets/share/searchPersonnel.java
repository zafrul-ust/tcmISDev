package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import javax.servlet.http.HttpSession;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 */

public class searchPersonnel
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
	private PrintWriter out = null;
	private boolean intcmIsApplication = false;
    public searchPersonnel(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

	public void doResult( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
	{
	  out=response.getWriter();
	  response.setContentType( "text/html" );
			HttpSession session=request.getSession();
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

	  String Action=request.getParameter( "Action" );
	  if ( Action == null )
		Action="";
	  Action=Action.trim();

	  String searchString=request.getParameter( "SearchString" );
	  if ( searchString == null )
		searchString="";
	  searchString=searchString.trim();

	  if ( "Search".equalsIgnoreCase( Action ) )
	  {
		buildLikemfgid( "Search",searchString );
	  }
	  else
	  {
		buildLikemfgid( "","" );
	  }

	  out.close();
    }

     public void buildLikemfgid(String SearchFlag,String SearchString)
     {
      //StringBuffer Msgl = new StringBuffer();

      String search_servlet = bundle.getString("SEARCH_PERSONNEL_SERVLET");
      out.println(radian.web.HTMLHelpObj.printHTMLHeader("Search Personnel","",intcmIsApplication));
      printJavaScripts(search_servlet);
      out.println("function ShowSearch(name,entered)\n");
      out.println("{\n");
      out.println(" with (entered)\n");
      out.println(" {\n");
      out.println(" loc = \""+search_servlet+"Action=\" + name.toString() + \"&SearchString=\";\n");
      out.println(" loc = loc + window.document.Personellike.SearchString.value;\n");
      out.println(" }\n");
      out.println("  window.location.replace(loc);\n");
      out.println(" }\n");
      out.println("// -->\n </SCRIPT>\n");
      out.println("<BODY>\n");
      out.println("<FORM METHOD=\"POST\" name=\"Personellike\" action=\""+search_servlet+"Session=Update\">\n");
	  //out.println("<INPUT TYPE=\"hidden\" NAME=\"personnelname\" ID=\"personnelname\" VALUE=\"\">\n");
	  out.println("<INPUT TYPE=\"hidden\" NAME=\"personnelid\" ID=\"personnelid\" VALUE=\"\">\n");

      out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\" bgcolor=\"white\">\n");
      out.println("<TR>\n");
      out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\""+HelpObjs.addescapesForJavascript(SearchString)+"\" SIZE=\"15\"></TD>\n");
	  out.println("<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD></TR>\n");
      out.println("<TR><TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Personnel Name:</B></FONT></TD><TD WIDTH=\"20%\" ALIGN=\"LEFT\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"personnelname\" value=\"\" SIZE=\"20\" readonly>\n");
      out.println("</TR>\n");
      out.println("</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR>\n");
      out.println("<TH WIDTH=\"25%\"><B>Name</B></TH>\n");
	  out.println("<TH WIDTH=\"10%\"><B>Phone</B></TH>\n");
      out.println("</TR></TABLE>\n");

      //open scrolling table
      out.println("<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      out.println("<TBODY>\n");
      out.println("<TR>\n");
      out.println("<TD VALIGN=\"TOP\">\n");
      out.println("<DIV ID=\"orderdetail\" CLASS=\"scroll_column150\">\n");

      //Write code to insert rows here
      out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"1\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n");

      int totalrecords = 0;
      int count = 0;
	  DBResultSet dbrs = null;
	  ResultSet rs = null;
      String query = "";
      SearchString=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
      String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"MFG_DESC||MFG_ID" );

      if ("Search".equalsIgnoreCase(SearchFlag))
      {
      query += "select FIRST_NAME ||' '|| LAST_NAME FULL_NAME, PHONE,PERSONNEL_ID from personnel where lower(FIRST_NAME || LAST_NAME) like lower('%" + SearchString + "%') order by LAST_NAME";
      }

      if (query.length() > 0)
      {
          try
          {
              dbrs = db.doQuery(query);
              rs=dbrs.getResultSet();

              while(rs.next())
              {
                totalrecords += 1;
                count += 1;

                String Color = " ";
                if (count%2==0)
                {
                    Color = "BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
                }
                else
                {
                   Color = " onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
                }

                String fullname = rs.getString( "FULL_NAME" ) == null ? "" : rs.getString( "FULL_NAME" ).trim();
                String phone = rs.getString( "PHONE" ) == null ? "" : rs.getString( "PHONE" ).trim();
		        String personnelid = rs.getString( "PERSONNEL_ID" ) == null ? "" : rs.getString( "PERSONNEL_ID" ).trim();

                if (phone.length() > 0)
                {
                  phone = HelpObjs.addescapesForJavascript(phone);
                }

                out.println("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addpersonnelid('"+personnelid+"','"+HelpObjs.addescapesForJavascript(fullname)+"')\">\n");
                out.println("<TD ");
                out.println(Color);
                out.println(" WIDTH=\"25%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
                out.println(fullname);
                out.println("</FONT></TD>\n");
                out.println("<TD ");
                out.println(Color);
                out.println(" WIDTH=\"10%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
                out.println(phone);
                out.println("</FONT></TD>\n");

                out.println("</TR>\n");
              }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            out.println("An Error Occured While Querying the Databse");
            out.println("</BODY>\n</HTML>\n");
            return;
        }
        finally
        {
            if ( dbrs != null ){ dbrs.close(); }
        }
      }

      if ( totalrecords==0 && "Search".equalsIgnoreCase(SearchFlag) )
            {out.println("<TR><TD>No Records Found</TD></TR>\n");}

      out.println("</TABLE>\n");

      //close scrolling table
      out.println("</DIV>\n");
      out.println("</TD>\n");
      out.println("</TR>\n");
      out.println("</TBODY>\n");
      out.println("</TABLE>\n");

      out.println("<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\"  name=\"searchlike\" value=\"Ok\" OnClick=\"sendpersonnelID(this.form)\">\n");
      out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\"  name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER>\n");
      out.println("</FORM></BODY></HTML>\n");

      return;
    }

    protected void printJavaScripts(String controlname)
    {
    //StringBuffer Msglt = new StringBuffer();

    out.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.println("<!--\n");

    out.println("function sendpersonnelID(entered)\n");
    out.println("{\n");

    out.println( "customercontact = opener.document.getElementById(\"customercontact\");\n" );
    out.println( "customercontact.value = window.document.Personellike.personnelid.value;\n");

	out.println( "customercontactname = opener.document.getElementById(\"customercontactname\");\n" );
	out.println( "customercontactname.value = window.document.Personellike.personnelname.value;\n");

    out.println("window.close();\n");
    out.println("}\n");

    out.println("function cancel()\n");
    out.println("{\n");
    out.println("window.close();\n");
    out.println("}\n");

    out.println("function addpersonnelid(personnelid,personnelname)\n");
    out.println("{\n");
    out.println("document.Personellike.personnelid.value = personnelid;\n");
    out.println("document.Personellike.personnelname.value = personnelname;\n");
    out.println("}\n");
    return;
    }
}