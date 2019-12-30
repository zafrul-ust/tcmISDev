package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
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


public class soundsLikeCannedComments
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
	private PrintWriter out = null;
    public soundsLikeCannedComments(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResult( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
    {
      out=response.getWriter();
      response.setContentType( "text/html" );

      String lineID=request.getParameter( "lineID" );
      if ( lineID == null )
        lineID="";
      lineID=lineID.trim();

      buildLikeSupplier( lineID);
      out.close();
    }

     public void buildLikeSupplier( String polineid )
     {
      //StringBuffer Msgl = new StringBuffer();
      polineid = polineid.trim();
      DBResultSet dbrs = null;
      ResultSet rs = null;

      nematid_Servlet = bundle.getString("PURCHASE_ORDER_CANNEDCOMMENTS");

      out.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Canned Comments","",false));
      printJavaScripts(polineid);
      out.println("// -->\n </SCRIPT>\n");

      out.println("<BODY onload=\"javascript:window.resizeTo(600,420)\">\n");
      out.println("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"supplierid1\" VALUE=\""+polineid+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"contactname\" VALUE=\"\">\n");
      out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Comments:</B></FONT></TD><TD WIDTH=\"70%\" COLSPAN=\"2\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"supplierid\" value=\"\" SIZE=\"50\" readonly>\n");
      out.println("</TR>\n");
      out.println("</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR>\n");
      out.println("<TH WIDTH=\"100%\"><B>Comments</B></FONT></TH>\n");
      out.println("</TR></TABLE>\n");
      //open scrolling table
      out.println("<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      out.println("<TBODY>\n");
      out.println("<TR>\n");
      out.println("<TD VALIGN=\"TOP\">\n");
      out.println("<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n");
      //Write code to insert rows here
      out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n");

      int totalrecords = 0;
      int count = 0;

      String query = "";
      query += "select * from po_canned_comments order by COMMENTS asc  ";

      try
      {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();
        //System.out.print( "Finished The Querry\n " );

        while ( rs.next() )
        {
          totalrecords+=1;
          count+=1;

          String comments=HelpObjs.addescapesForJavascript( rs.getString( "COMMENTS" ) == null ? "" : rs.getString( "COMMENTS" ).trim() );

          String Color=" ";
          if ( count % 2 == 0 )
          {
            Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }
          else
          {
            Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }

          out.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('" + comments + "')\" BORDERCOLOR=\"black\">\n" );
          out.println( "<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
          out.println( "" + comments + "" );
          out.println( "</TD>\n" );
          out.println( "</TR>\n" );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( "An Error Occured While Querying the Database" );
        out.println( "</BODY>\n</HTML>\n" );
        return;
      }
      finally
      {
        if ( dbrs != null )
        {
          dbrs.close();
        }
      }

      if (totalrecords==0){out.println("<TR><TD>No Records Found</TD></TR>\n");}
      out.println("</TABLE>\n");

      //close scrolling table
      out.println("</DIV>\n");
      out.println("</TD>\n");
      out.println("</TR>\n");
      out.println("</TBODY>\n");
      out.println("</TABLE>\n");
      out.println("<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"sendCarrier(this.form)\" type=\"button\">\n");
      out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      out.println("</FORM></BODY></HTML>\n");

      return;
    }
    protected void printJavaScripts(String controlname)
    {
    //StringBuffer Msglt = new StringBuffer();

    out.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.println("<!--\n");
    out.println("function cancel()\n");
    out.println("{\n");
    out.println("window.close();\n");
    out.println("}\n");
    out.println("function sendCarrier(entered)\n");
    out.println("{\n");
    out.println(" with (entered)\n");
    out.println(" {\n");
    out.println(" eval( testmfgid = \"window.document.SupplierLike.supplierid\")\n");
    out.println(" if ( (eval(testmfgid.toString())).value.length > 0 )\n");
    out.println("{");
    out.println("linenotesdivrow = opener.document.getElementById(\"linenotesdivrow"+controlname+""+controlname+"\");\n");
    out.println("finalnotses = linenotesdivrow.value +\"\\n\"+ window.document.SupplierLike.contactname.value;\n");
    out.println("if (linenotesdivrow.value.length > 0) \n");
    out.println("{\n");
    //out.println("finalnotses = linenotesdivrow.value + \"\\n\" + finalnotses;\n");
    out.println("}\n");
    //out.println("finalnotses = finalnotses + window.document.SupplierLike.contactname.value; \n");
    out.println("linenotesdivrow.value = finalnotses;\n");
    out.println("opener.setlineUnconfirmed('"+controlname+"'); \n");
    /*out.println("linechange = opener.document.getElementById(\"linechange"+controlname+"\");\n");
    out.println("linechange.value = \"Yes\";\n");*/

    /*out.println("selectedRow = opener.document.getElementById(\"ordertakerID\");\n");
    out.println("selectedRow.value = window.document.SupplierLike.contactname.value;\n");

    out.println("selectedRow = opener.document.getElementById(\"validordertaker\");\n");
    out.println("selectedRow.value = \"Yes\";\n");

    out.println("selectedRow = opener.document.getElementById(\"validcarrier\");\n");
    out.println("selectedRow.value = \"Yes\";\n");*/
    out.println("window.close();\n");
    out.println(" }\n");
    /*
    out.println(" else\n");
    out.println(" {\n");
    out.println(" return;\n");
    out.println(" }\n");*/
    out.println(" }\n");
    out.println("}\n");
    out.println("function addsupplierID(matidvalue)\n");
    out.println("{\n");
    out.println("document.SupplierLike.supplierid.value = matidvalue;\n");
    out.println("document.SupplierLike.contactname.value = matidvalue;\n");
    //out.println("document.SupplierLike.addline2.value = addline2;\n");
    out.println("}\n");

    return;
    }
}