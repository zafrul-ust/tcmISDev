package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
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

public class reprintPicklist
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;

    public reprintPicklist(ServerResourceBundle b, TcmISDBModel d)
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
    String Hub = "";

    Hub = BothHelpObjs.makeSpaceFromNull(request.getParameter("HubNo"));

    //StringBuffer Msgn = new StringBuffer();
    out.println("<HTML>\n");
    out.println("<HEAD>\n");
    out.println("<TITLE>Re-Print Picklist</TITLE>\n");
    out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
    out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
    out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
    out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
    out.println("<SCRIPT SRC=\"/clientscripts/genpicklist.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
    out.println("</HEAD>\n");
    out.println("<BODY>\n");

    out.println("<FORM METHOD=\"POST\" name=\"picklistids\" action=\"\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNo\" VALUE=\""+Hub+"\">\n");

    out.println("<BR><FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Please Select From the Options Below:</B>\n");

    out.println("<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<SELECT NAME=\"picklist\" CLASS=\"HEADER\" size=\"1\">\n");
    out.println("<option Value=\"NONE\">Picklist ID : Time Generated</option>\n");

    int totalrecords = 0;
    int count = 0;

    String query = "";
    query += "select PICKLIST_ID,to_char(PICKLIST_PRINT_DATE,'mm/dd/yyyy hh:mi AM') PICKLIST_PRINT_DATE1 from reprint_picklist_id_view where HUB = "+Hub+" order by PICKLIST_PRINT_DATE desc";

    DBResultSet dbrs = null;
    ResultSet rs = null;

    try
    {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        //System.out.print("Finished The Querry\n ");

        while(rs.next())
        {
          totalrecords += 1;
          count += 1;
          out.println("<option value=\""+BothHelpObjs.makeSpaceFromNull(rs.getString("PICKLIST_ID"))+"\">PL "+BothHelpObjs.makeSpaceFromNull(rs.getString("PICKLIST_ID"))+" : "+BothHelpObjs.makeSpaceFromNull(rs.getString("PICKLIST_PRINT_DATE1"))+"</option>\n");
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
        out.println("An Error Occured While Querying the Databse");
        out.println("</BODY>\n</HTML>\n");
        //out.println(Msgn);
        return;
    }

    finally
    {
        if (dbrs!= null) {dbrs.close();}
        if (totalrecords==0)
        {out.println("<option Value=\"NONE\">No Records Found</option>\n");}
    }
      out.println("</select>\n");
      out.println("<BR><BR><P><CENTER><INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"sencklist\" value=\"OK\" OnClick=\"sendpicklist(name,this)\">\n");
      out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER></P>\n");
      out.println("</FORM></BODY></HTML>\n");

      //out.println(Msgn);

    }
}