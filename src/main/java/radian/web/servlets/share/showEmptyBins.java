package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-27-03 Code cleanup and removed true from request.getsession()
 */


public class showEmptyBins
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;

    public showEmptyBins(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }
     public void doResult(HttpServletRequest request, HttpServletResponse response)
          throws ServletException,  IOException
    {

    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    HttpSession Session = request.getSession();
    Vector emptybinset = (Vector)Session.getAttribute("EMPTYBIN");

    String itemID=request.getParameter( "itemID" );
      if ( itemID == null )
        itemID="";

    String Hub=request.getParameter( "HubNo" );
      if ( Hub == null )
        Hub="";

    String lineNum = request.getParameter("LineNo");
    if (lineNum == null)
          lineNum = "0";

    //StringBuffer Msgn = new StringBuffer();
    out.println("<HTML>\n");
    out.println("<HEAD>\n");
    out.println("<TITLE>Pick New Bin</TITLE>\n");
    out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
    out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
    out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
    out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
    //out.println("<SCRIPT SRC=\"/clientscripts/showemptybins.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
    out.println("<SCRIPT SRC=\"/clientscripts/receivingjs.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
    out.println("</HEAD>\n");
    out.println("<BODY>\n");
    out.println("<FORM METHOD=\"POST\" name=\"EmptyBins\" action=\"\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNo\" VALUE=\""+Hub+"\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"ItemID\" VALUE=\""+itemID+"\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"LineNum\" VALUE=\""+lineNum+"\">\n");
    //out.println("<BR><BR><FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Please Select from the Following Bins:</B><BR><BR>\n");
    out.println("<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<SELECT NAME=\"EmptyBin\" CLASS=\"HEADER\" size=\"1\">\n");
    out.println("<option Value=\"NONE\">Please Select From the Options Below</option>\n");

    for ( int j = 0; j < emptybinset.size(); j++)
    {
     out.println("<option value=\""+emptybinset.elementAt(j)+"\">"+emptybinset.elementAt(j)+"</option>\n");
    }

    /*int totalrecords = 0;
    int count = 0;

    String query = "";
    query += "select * from empty_bin_view where branch_plant = "+Hub+" ";

    try
    {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        System.out.print("Finished The Querry\n ");

        while(rs.next())
        {
          totalrecords += 1;
          count += 1;
          out.println("<option value=\""+BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"))+"\">"+BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"))+"</option>\n");
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
        out.println("An Error Occured While Querying the Databse");
        out.println("</BODY>\n</HTML>\n");
        out.println(Msgn);
        return;
    }

    finally
    {
        dbrs.close();
        if (totalrecords==0)
        {out.println("<option Value=\"NONE\">No Records Found</option>\n");}
    }*/
      out.println("</select>\n");
      out.println("<BR><BR><P><CENTER><INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"sendBin\" value=\"OK\" OnClick=\"sendbin(name,this)\">\n");
      out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER></P>\n");
      out.println("</FORM></BODY></HTML>\n");

      //out.println(Msgn);
	  out.close();
    }
}