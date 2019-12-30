package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class genericShipto {
  private ServerResourceBundle bundle = null;
  private TcmISDBModel db = null;
  private String nematid_Servlet = "";
  private boolean intcmIsApplication = false;

  public genericShipto(ServerResourceBundle b, TcmISDBModel d) {
    bundle = b;
    db = d;
  }

  public void doResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
    HttpSession session = request.getSession();

    PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    intcmIsApplication = false;
    if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
    {
     intcmIsApplication = true;
    }

    String Action = request.getParameter("Action");
    if (Action == null) {
      Action = "";
    }
    Action = Action.trim();

    String searchString = request.getParameter("SearchString");
    if (searchString == null) {
      searchString = "";
    }
    searchString = searchString.trim();

    String shiptoID = request.getParameter("shiptoID");
    if (shiptoID == null) {
      shiptoID = "";
    }
    shiptoID = shiptoID.trim();

    String whichone = request.getParameter("whichone");
    if (whichone == null) {
      whichone = "";
    }
    whichone = whichone.trim();

    if ("Search".equalsIgnoreCase(Action)) {
      out.println(buildLikeSupplier("Search", searchString, shiptoID, whichone));
    } else {
      out.println(buildLikeSupplier("", searchString, shiptoID, whichone));
    }
    out.close();
  }

  public StringBuffer buildLikeSupplier(String SearchFlag, String SearchString, String MfgID1, String whichone) {
    StringBuffer Msgl = new StringBuffer();
    MfgID1 = MfgID1.trim();
    DBResultSet dbrs = null;
    ResultSet rs = null;

    nematid_Servlet = bundle.getString("GENERIC_SHIPTO");
    Msgl.append(radian.web.HTMLHelpObj.printHTMLHeader("Generic Ship To", "genericshipto.js",intcmIsApplication));
    Msgl.append("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    Msgl.append("<!--\n");
    Msgl.append("function ShowSearch(name,entered)\n");
    Msgl.append("{\n");
    Msgl.append(" with (entered)\n");
    Msgl.append(" {\n");
    Msgl.append(" loc = \"" + nematid_Servlet + "&Action=\" + name.toString() + \"&SearchString=\";\n");
    Msgl.append(" loc = loc + window.document.SupplierLike.SearchString.value;\n");
    Msgl.append(" loc = loc + \"&shiptoID=\"; \n");
    Msgl.append(" loc = loc + \"&whichone=" + whichone + "\"; \n");
    Msgl.append(" loc = loc + window.document.SupplierLike.supplierid.value;\n");
    Msgl.append(" }\n");
    Msgl.append("  window.location.replace(loc);\n");
    Msgl.append(" }\n");
    Msgl.append("// -->\n </SCRIPT>\n");

    Msgl.append("<BODY onload=\"javascript:window.resizeTo(650,480)\">\n");
    Msgl.append("<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n");
    Msgl.append("<INPUT TYPE=\"hidden\" NAME=\"shiptodesc\" VALUE=\"\">\n");
    Msgl.append("<INPUT TYPE=\"hidden\" NAME=\"addline1\" VALUE=\"\">\n");
    Msgl.append("<INPUT TYPE=\"hidden\" NAME=\"addline2\" VALUE=\"\">\n");
    Msgl.append("<INPUT TYPE=\"hidden\" NAME=\"city\" VALUE=\"\">\n");
    Msgl.append("<INPUT TYPE=\"hidden\" NAME=\"state\" VALUE=\"\">\n");
    Msgl.append("<INPUT TYPE=\"hidden\" NAME=\"zipcode\" VALUE=\"\">\n");
    Msgl.append("<INPUT TYPE=\"hidden\" NAME=\"whichone\" VALUE=\"" + whichone + "\">\n");

    Msgl.append("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
    Msgl.append("<TR>\n");
    Msgl.append("<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text:</B></TD>\n");
    if ("Search".equalsIgnoreCase(SearchFlag)) {
      Msgl.append("<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + SearchString +
                  "\" SIZE=\"25\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
    } else {
      Msgl.append("<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + MfgID1 +
                  "\" SIZE=\"25\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
    }

    Msgl.append("<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Ship To Id:</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"supplierid\" value=\"\" SIZE=\"25\" readonly>\n");
    Msgl.append("</TR>\n");
    Msgl.append("</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
    Msgl.append("<TR>\n");
    Msgl.append("<TH WIDTH=\"15%\"><B>Ship To Id</B></FONT></TH>\n");
    Msgl.append("<TH WIDTH=\"35%\"><B>Ship To Name</B></FONT></TH>\n");
    /*Msgl.append( "<TH WIDTH=\"10%\"><B>Facility ID</B></FONT></TH>\n" );
         Msgl.append( "<TH WIDTH=\"10%\"><B>Company ID</B></FONT></TH>\n" );*/
    Msgl.append("</TR></TABLE>\n");

    //open scrolling table
    Msgl.append("<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
    Msgl.append("<TBODY>\n");
    Msgl.append("<TR>\n");
    Msgl.append("<TD VALIGN=\"TOP\">\n");
    Msgl.append("<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n");
    //Write code to insert rows here
    Msgl.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n");

    int totalrecords = 0;
    int count = 0;

    String query = "";

    if ("Search".equalsIgnoreCase(SearchFlag)) {
      query += "select * from GENERIC_LOCATION_VIEW  where lower(LOCATION_ID||LOCATION_DESC) like  lower('%" +
          BothHelpObjs.changeSingleQuotetoTwoSingleQuote(SearchString) + "%') order by LOCATION_ID asc";
    } else {
      query += "select * from GENERIC_LOCATION_VIEW  where lower(LOCATION_ID||LOCATION_DESC) like lower('%" +
          BothHelpObjs.changeSingleQuotetoTwoSingleQuote(MfgID1) + "%') order by LOCATION_ID asc ";
    }

    if ( (! (MfgID1.trim().length() > 0)) && (!"Search".equalsIgnoreCase(SearchFlag))) {

    } else {
      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();

        while (rs.next()) {
          totalrecords += 1;
          count += 1;

          String location_id = rs.getString("LOCATION_ID") == null ? "" : rs.getString("LOCATION_ID").trim();
          String country_abbrev = rs.getString("COUNTRY_ABBREV") == null ? "" : rs.getString("COUNTRY_ABBREV").trim();
          String state_abbrev = rs.getString("STATE_ABBREV") == null ? "" : rs.getString("STATE_ABBREV").trim();
          String address_line_1 = rs.getString("ADDRESS_LINE_1") == null ? "" : rs.getString("ADDRESS_LINE_1").trim();
          String address_line_2 = rs.getString("ADDRESS_LINE_2") == null ? "" : rs.getString("ADDRESS_LINE_2").trim();
              //String address_line_3=rs.getString( "ADDRESS_LINE_3" ) == null ? "" : rs.getString( "ADDRESS_LINE_3" ).trim();
          String city = rs.getString("CITY") == null ? "" : rs.getString("CITY").trim();
          String zip = rs.getString("ZIP") == null ? "" : rs.getString("ZIP").trim();
          String location_desc = rs.getString("LOCATION_DESC") == null ? "" : rs.getString("LOCATION_DESC").trim();
              //String client_location_code = rs.getString("CLIENT_LOCATION_CODE")==null?"":rs.getString("CLIENT_LOCATION_CODE").trim();
              /*String facileity_id=rs.getString( "FACILITY_ID" ) == null ? "" : rs.getString( "FACILITY_ID" ).trim();
                         String compeany_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();*/

          String location_id1 = HelpObjs.findReplace(location_id, "#", "%23");
          String hubName = "";

          String Color = " ";
          if (count % 2 == 0) {
            Color = "BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          } else {
            Color = " onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }

          Msgl.append("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('" + location_id1 + "','" + HelpObjs.addescapesForJavascript(location_desc) + "','" +
                      HelpObjs.addescapesForJavascript(address_line_1) + "','" + HelpObjs.addescapesForJavascript(address_line_2) + "','" + city + "','" + state_abbrev + "','" + zip + "')\" BORDERCOLOR=\"black\">\n");
          Msgl.append("<TD " + Color + " WIDTH=\"15%\" ALIGN=\"LEFT\">");
          Msgl.append(location_id);
          Msgl.append("</TD>\n");
          Msgl.append("<TD " + Color + " WIDTH=\"35%\" ALIGN=\"LEFT\">");
          Msgl.append(location_desc);
          Msgl.append("</TD>\n");
          /*Msgl.append( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
                     Msgl.append( facility_id );
                     Msgl.append( "</TD>\n" );
                     Msgl.append( "<TD " + Color + " WIDTH=\"10%\" ALIGN=\"LEFT\">" );
                     Msgl.append( company_id );*/
          Msgl.append("</TD>\n");

          Msgl.append("</TR>\n");
        }
      } catch (Exception e) {
        e.printStackTrace();
        Msgl.append("An Error Occured While Querying the Database");
        Msgl.append("</BODY>\n</HTML>\n");
        return Msgl;
      } finally {
        if (dbrs != null) {
          dbrs.close();
        }
      }
    }

    if (totalrecords == 0) {
      Msgl.append("<TR><TD>No Records Found</TD></TR>\n");
    }
    Msgl.append("</TABLE>\n");

    //close scrolling table
    Msgl.append("</DIV>\n");
    Msgl.append("</TD>\n");
    Msgl.append("</TR>\n");
    Msgl.append("</TBODY>\n");
    Msgl.append("</TABLE>\n");
    Msgl.append("<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" OnClick=\"sendShipto(this.form,'" + whichone + "')\" type=\"button\">\n");
    Msgl.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    Msgl.append("</FORM></BODY></HTML>\n");

    return Msgl;
  }
}