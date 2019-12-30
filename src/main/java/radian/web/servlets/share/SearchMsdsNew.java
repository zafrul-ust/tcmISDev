package radian.web.servlets.share;

import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.Catalog;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;

/**
 *
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 * 07-14-03 - Code Cleanup and added MSDS No
 * 09-01-03 - Taking out spec info in the url
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 03-12-04 Made Changes to accomodate Sikorsky MSDSs. These are MSDS with no material ID
 */
public class SearchMsdsNew extends HttpServlet implements SingleThreadModel {
  private Vector data = null;
  private boolean union = true;
  /*private String limit = "";
       private String item = "";
       private String fac = "";
       private String sortby = "";
       private String from = "";
       private String interval = "";
       private String recs = null;
       private String window = "";
       private String nonCatalogue = "";
       private String msdsDatabase = "";*/
  private ServerResourceBundle bundle = null;
  private TcmISDBModel db = null;
  private ResourceLibrary res = null; // res means resource.

  public SearchMsdsNew() {

  }

  public SearchMsdsNew(ServerResourceBundle b, TcmISDBModel d) {
    bundle = b;
    db = d;
  }

  public Hashtable GetResult(Hashtable SearchData, String TestFacCount, HttpSession SessionH, String istcmIsApplication,ResourceLibrary res) throws ServletException,
      IOException {
    String client = "";
    client = bundle.getString("DB_CLIENT");
    String Session_id = " ";
    String Second = "No";
    String recs = "";
    StringBuffer Msg = new StringBuffer();
    StringBuffer MsgBackNext = new StringBuffer();
    Hashtable ResultDdata = new Hashtable();

    this.res = res;
    ResultDdata.put("MSG_BODY", Msg.toString());
    ResultDdata.put("MSG_BACK_NEXT", MsgBackNext.toString());

    String nonCatalogue = SearchData.get("noncatalog").toString();
    String msdsDatabase = SearchData.get("msdsdataabase").toString();
    String fac = SearchData.get("fac").toString();
    String item = SearchData.get("item").toString();
    String limit = SearchData.get("limit").toString();
    String sortby = SearchData.get("sortby").toString();
    String from = SearchData.get("from").toString();
    String interval = SearchData.get("interval").toString();
    String window = SearchData.get("window").toString();
    Session_id = SearchData.get("Session_ID").toString();
    Second = (SearchData.get("Second").toString() == null ? "No" : SearchData.get("Second").toString());

    if (sortby.equalsIgnoreCase("Nu")) {
      sortby = "";
    }
    if (fac.equalsIgnoreCase("Nu")) {
      fac = "All";
    }
    if (Second == null) {
      Second = "No";
    }
    if (limit == null) {
      limit = "No";
    }
    if (nonCatalogue == null) {
      nonCatalogue = "No";
    }
    if (msdsDatabase == null) {
      msdsDatabase = "No";
    }
    fac = Remove20from(fac);

	 data = null;
    Vector data1 = new Vector();
    if ("No".equalsIgnoreCase(Second)) {
      try {
        Catalog cat = new Catalog(db);
        if (client.equalsIgnoreCase("Internal")) {
          data1 = cat.retrieveNeededData(item, sortby);
        } else if ( (limit.equalsIgnoreCase("yes")) && (nonCatalogue.equalsIgnoreCase("yes")) && (msdsDatabase.equalsIgnoreCase("yes"))) {
          union = true;
          cat.setUnion(union);
          cat.setMsdsDatabase(true);
          data1 = cat.retrieveAllCatalogPureLimited(item, (fac.equals("All") ? null : fac), sortby, client);
        } else if ( (limit.equalsIgnoreCase("yes")) && (msdsDatabase.equalsIgnoreCase("yes"))) {
          union = true;
          cat.setUnion(union);
          cat.setMsdsDatabase(true);
          data1 = cat.retrieveAllCatalogPureLimited(item, (fac.equals("All") ? null : fac), sortby, client);
        } else if ( (msdsDatabase.equalsIgnoreCase("yes")) && (nonCatalogue.equalsIgnoreCase("yes"))) {
          union = true;
          cat.setUnion(union);
          cat.setMsdsDatabase(true);
          data1 = cat.retrieveAllNonCompanyCatalog(item, (fac.equals("All") ? null : fac), sortby, client);
        } else if ( (limit.equalsIgnoreCase("yes")) && (nonCatalogue.equalsIgnoreCase("yes"))) {
          union = true;
          cat.setUnion(union);
          data1 = cat.retrieveAllCatalogPureLimited(item, (fac.equals("All") ? null : fac), sortby, client);
        } else if ( (! (limit.equalsIgnoreCase("yes"))) && (nonCatalogue.equalsIgnoreCase("yes"))) {
          data1 = cat.retrieveAllNonCompanyCatalog(item, (fac.equals("All") ? null : fac), sortby, client);
        } else if ( (limit.equalsIgnoreCase("yes")) && (! (nonCatalogue.equalsIgnoreCase("yes")))) {
          union = false;
          cat.setUnion(union);
          data1 = cat.retrieveAllCatalogPureLimited(item, (fac.equals("All") ? null : fac), sortby, client);
        } else if (msdsDatabase.equalsIgnoreCase("yes")) {
          cat.setMsdsDatabase(true);
          data1 = cat.retrieveAllNonCatalogPureLimited(item, (fac.equals("All") ? null : fac), sortby, client);
        }

        recs = data1.elementAt(data1.size() - 1).toString();
        if (Integer.parseInt(recs) == 0) {
          Msg.append(printHtmlNoData());
          ResultDdata.put("MSG_BODY", Msg.toString());
          ResultDdata.put("MSG_BACK_NEXT", MsgBackNext.toString());

          return ResultDdata;
        } else {
          SessionH.setAttribute(Session_id, data1);
        }
      } catch (Exception e) {
        e.printStackTrace();
        Msg.append(res.getString("msg.tcmiserror")+"<BR><BR><BR><BR>");
        ResultDdata.put("MSG_BODY", Msg.toString());
        ResultDdata.put("MSG_BACK_NEXT", MsgBackNext.toString());

        return ResultDdata;
      }
    }

    try {
      data = (Vector) SessionH.getAttribute(Session_id);
      recs = data.elementAt(data.size() - 1).toString();

      Hashtable hD = null;
      Msg.append(printHtmlHeader());

      int f = Integer.parseInt(from);
      int r = Integer.parseInt(recs);
      int it = 0;
      try {
        it += Integer.parseInt(interval);
      } catch (Exception e122r) {
        it = 0;
      }
      int tmp = (r < f + it ? (r) : (it + f - 1));

      for (int i = new Integer(from).intValue() - 1; i < tmp; i++) {
        if (i % 10 == 0) {
          Msg.append("<TR><TH>"+res.getString("label.material")+"</TH>\n");
          Msg.append("<TH>"+res.getString("label.msdsno")+"</TH>\n");

          if (client.equalsIgnoreCase("Internal")) {
            //Msg.append("<TR><TH>Material</STRONG></TD>\n" );
            Msg.append(" <TH>"+res.getString("label.materialdesc")+"</TH>\n");
            Msg.append(" <TH>"+res.getString("label.tradename")+"</TH>\n");
            Msg.append(" <TH>"+res.getString("label.manufacturer")+"</TH>\n");
            Msg.append("</TR>\n");
          } else {
            /*if ( client.equalsIgnoreCase( "Lockheed" ) )
                             {
              Msg.append("<TH>MSDS</TH>\n" );
                             }*/
            /*
            if (! (TestFacCount.equalsIgnoreCase("1"))) {
              if (fac.equalsIgnoreCase("All")) {
                Msg.append("<TH><STRONG>Catalog</TH>\n");
              } else {
                Msg.append("<TH>Facility</TH>\n");
              }
            }
            */
            Msg.append("<TH><STRONG>"+res.getString("label.catalog")+"</TH>\n");

            Msg.append(" <TH>"+res.getString("label.partnumber")+"</TH>\n");
            Msg.append(" <TH>"+res.getString("label.tradename")+"</TH>\n");
            Msg.append(" <TH>"+res.getString("label.manufacturer")+"</TH>\n");
            Msg.append("</TR>\n");
          }
        }

        hD = (Hashtable) data.elementAt(i);
        String itemid_cat = ( ( (hD.get("ITEM_ID") == null) || (hD.get("ITEM_ID").toString().length() <= 1)) ? "&nbsp;" : hD.get("ITEM_ID").toString());
        String facid_cat = ( ( (hD.get("FACILITY_ID") == null) || (hD.get("FACILITY_ID").toString().length() <= 1)) ? "" : hD.get("FACILITY_ID").toString());
        String catalogId = ( ( (hD.get("CATALOG_ID") == null) || (hD.get("CATALOG_ID").toString().length() <= 1)) ? "" : hD.get("CATALOG_ID").toString());
        String partid_cat = ( ( (hD.get("FAC_ITEM_ID") == null) || (hD.get("FAC_ITEM_ID").toString().length() <= 1)) ? "&nbsp;" : hD.get("FAC_ITEM_ID").toString());
        String material_desc = ( ( (hD.get("MATERIAL_DESC") == null) || (hD.get("MATERIAL_DESC").toString().length() <= 1)) ? "&nbsp;" : hD.get("MATERIAL_DESC").toString());
        String material_id = ( ( (hD.get("MATERIAL_ID") == null) || (hD.get("MATERIAL_ID").toString().length() <= 1)) ? "" : hD.get("MATERIAL_ID").toString());
        String spec_id = ( ( (hD.get("SPEC_ID") == null) || (hD.get("SPEC_ID").toString().length() <= 1)) ? "&nbsp;" : hD.get("SPEC_ID").toString());
        String fac_msds_id = ( ( (hD.get("FAC_MSDS_ID") == null) || (hD.get("FAC_MSDS_ID").toString().length() <= 1)) ? "" : hD.get("FAC_MSDS_ID").toString());
        String trade_name = ( ( (hD.get("TRADE_NAME") == null) || (hD.get("TRADE_NAME").toString().length() <= 1)) ? "&nbsp;" : hD.get("TRADE_NAME").toString());
        String mfg_desc = ( ( (hD.get("MFG_DESC") == null) || (hD.get("MFG_DESC").toString().length() <= 1)) ? "&nbsp;" : hD.get("MFG_DESC").toString());
        String msds_no = ( ( (hD.get("MSDS_NO") == null) || (hD.get("MSDS_NO").toString().length() <= 1)) ? "" : hD.get("MSDS_NO").toString());

        String[] url = new String[2];
        try {
          url = buildMsdsUrl(hD.get("MSDS_ON_LINE").toString().equalsIgnoreCase("Y"), hD.get("SPEC_ON_LINE").toString().equalsIgnoreCase("Y"), itemid_cat, facid_cat, spec_id, material_id, client, fac_msds_id);
        } catch (Exception e) {
          e.printStackTrace();
        }

        String Color = " ";
        if (i % 2 == 0) {
          Color = "bgcolor=\"#E6E8FA\"";
        }
        Msg.append("<TR>\n");
        Msg.append("<TD " + Color + " width=\"12%\">\n");

        if (url[0] != null && material_id.length() > 0) {
          if (window.equalsIgnoreCase("Same")) {
            Msg.append("<A HREF=\"javascript:getMsds('" + url[0] + "');\">\n");
          } else if (window.equalsIgnoreCase("New")) {
            Msg.append("<A HREF=\"" + url[0] + "\" TARGET=\"msds\"  STYLE=\"color:#0000ff\">\n");
          } else {
            Msg.append("<A HREF=\"" + url[0] + "\" TARGET=\"msds" + i + "\"  STYLE=\"color:#0000ff\">\n");
          }
          Msg.append(material_id);
          Msg.append("</A>\n");
        } else {
          Msg.append(material_id);
        }
        Msg.append("</TD>\n");

        if (msds_no.length() > 0) {
          Msg.append("<TD " + Color + " width=\"5%\">" + msds_no + "</TD>\n");
        } else if (fac_msds_id.length() > 0) {
          if (url[0] != null && facid_cat.length() > 0) {
            Msg.append("<TD " + Color + " width=\"5%\">\n");
            Msg.append("<A HREF=\"" + url[0] + "\" TARGET=\"msds" + i + "\"  STYLE=\"color:#0000ff\">\n");
            Msg.append(fac_msds_id);
            Msg.append("</A>\n");
          } else {
            Msg.append("<TD " + Color + " width=\"5%\">" + fac_msds_id + "</TD>\n");
          }
        } else {
          Msg.append("<TD " + Color + " width=\"5%\">&nbsp;</TD>\n");
        }

        if (client.equalsIgnoreCase("Internal")) {
          Msg.append("<TD " + Color + " width=\"19%\">" + material_desc + "</TD>\n");
        } else {
          /*if ( client.equalsIgnoreCase( "Lockheed" ) )
                         {
            Msg.append( "<TD " + Color + " width=\"12%\">" + fac_msds_id + "</TD>\n" );
                         }*/
          if (! (TestFacCount.equalsIgnoreCase("1"))) {
            //Msg.append("<TD " + Color + " width=\"12%\">" + facid_cat + "</TD>\n");
            Msg.append("<TD " + Color + " width=\"12%\">" + catalogId + "</TD>\n");
          }
          Msg.append("<TD " + Color + " width=\"19%\">" + partid_cat + "</TD>\n");
        }

        Msg.append("<TD " + Color + " width=\"26%\">" + trade_name + "</TD>\n");
        Msg.append("<TD " + Color + " width=\"19%\">" + mfg_desc + "</TD>\n");
        Msg.append("</TR>\n");
      }

      Msg.append("</TABLE>\n");
      Msg.append("<TABLE  border=\"0\" width=\"725\"><TR><TD ALIGN=\"CENTER\">\n");

      Msg.append(printBackRecsNext(recs, from, interval));
      MsgBackNext.append(printBackRecsNext(recs, from, interval));

      Msg.append("</TD></TR></TABLE>\n");

      Msg.append(buildBackForm(limit, Session_id, from, interval, sortby, fac, item, nonCatalogue, msdsDatabase, window,istcmIsApplication));
      Msg.append(buildNextForm(limit, Session_id, from, interval, sortby, fac, item, nonCatalogue, msdsDatabase, window,istcmIsApplication));

      ResultDdata.put("MSG_BODY", Msg.toString());
      ResultDdata.put("MSG_BACK_NEXT", MsgBackNext.toString());

      data = null;
      data1 = null;
      return ResultDdata;
    } catch (Exception e) {
      e.printStackTrace();
      Msg.append(res.getString("err.msdssearch"));
      ResultDdata.put("MSG_BODY", Msg.toString());
      ResultDdata.put("MSG_BACK_NEXT", MsgBackNext.toString());

      data = null;
      data1 = null;
      return ResultDdata;
    }
  }

  public String getServletInfo() {
    return "radian.web.servlets.share.SearchMsds Information";
  }

  protected String[] buildMsdsUrl(boolean msdsOn, boolean specOn, String itemid_cat, String facid_cat, String specid, String matid, String clientc, String cusmsdsno) throws Exception {
    String url = "";
    String q = "";
    String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
    wwwHome = wwwHome.substring(0, (wwwHome.length() - 1));

    String WWWhomeDirectory = "";
    String clientHttp = clientc.replace(' ', '+');
    if (matid.length() > 0) {
      if ("Internal".equals(clientHttp) || "USGOV".equals(clientHttp)) {
        WWWhomeDirectory = wwwHome + bundle.getString("VIEW_MSDS");
        q = ("id=" + matid);
        q = q + "&cl=" + clientHttp.toUpperCase();
      }else {
        WWWhomeDirectory = wwwHome + bundle.getString("VIEW_MSDS").replace("ViewMsds?","viewmsds.do?");
        q = ("materialId=" + matid);
        q = q + "&cl=" + clientHttp.toUpperCase();
      }
    } else if (cusmsdsno.length() > 0 && facid_cat.length() > 0) {
      WWWhomeDirectory = wwwHome + bundle.getString("VIEW_NON_MAT_MSDS");
      q = ("id=" + cusmsdsno);
    } else {
      msdsOn = false;
    }
    url = ("" + WWWhomeDirectory + "");

    if (facid_cat == null) {
      facid_cat = "";
    }
    if (itemid_cat == null) {
      itemid_cat = "";
    }

    q = q + "&facility=" + BothHelpObjs.addSpaceForUrl(facid_cat);
    q = q + "&itemid=" + itemid_cat;

    String qtmp = "";
    String[] rUrl = {
        null, null};

    if (msdsOn) {
      qtmp = ("act=msds&" + q);
      rUrl[0] = (url + qtmp);
    }

    return rUrl;
  }

  protected StringBuffer printHtmlHeader() {
    StringBuffer MsgHH = new StringBuffer();
    MsgHH.append("<TABLE BORDER=\"0\" CELLPADDING=\"3\" WIDTH=\"725\" >");

    return MsgHH;
  }

  protected StringBuffer printHtmlNoData() {
    StringBuffer MsgE = new StringBuffer();
    MsgE.append(" " +res.getString("label.norecordfound") + "\n");
    MsgE.append("<P>&nbsp;</P><P>&nbsp;</P>\n");
    return MsgE;
  }

  protected StringBuffer printError() {
    StringBuffer MsgER = new StringBuffer();
    MsgER.append("<TR>\n");
    MsgER.append("<TD width=\"5%\">\n");
    MsgER.append("</TD>\n");
    MsgER.append("<TD width=\"16%\">" + res.getString("msg.anerroroccurred")+"<BR>\n");
    MsgER.append(res.getString("msg.checkcornonc") + "</TD>\n");
    MsgER.append("</TR></TABLE>\n");
    MsgER.append("</BODY></HTML>\n");
    return MsgER;
  }

  protected StringBuffer printBackRecsNext(String recs, String from, String interval) {
    StringBuffer MsgBN = new StringBuffer();

    if (recs == null || recs.length() < 1) {
      return MsgBN;
    }
    int f = Integer.parseInt(from);
    int r = Integer.parseInt(recs);
    int it = Integer.parseInt(interval);
    if (r == 0) {
      return MsgBN;
    }

    if (f > 1) {
      MsgBN.append("<A HREF=\"javascript:goBack();\" STYLE=\"color:#0000ff\">"+res.getString("label.back")+"</A>&nbsp;&nbsp;&nbsp;");
    }

    int tmp = (r < it ? (r + f - 1) : (it + f - 1));
    tmp = (tmp > Integer.parseInt(recs) ? Integer.parseInt(recs) : tmp);

    MsgBN.append("<FONT FACE=\"Arial\" color=\"Navy\">" + f + "-" + tmp + " of " + recs + "&nbsp;&nbsp;&nbsp;");

    if (r > (f + it - 1)) {
      MsgBN.append("<A href=\"javascript:goNext();\" STYLE=\"color:#0000ff\">"+res.getString("label.next")+"</A></FONT>\n");
    }
    return MsgBN;
  }

  protected StringBuffer buildBackForm(String limit, String Session_ID, String from, String interval, String sortby, String fac, String item, String nonCatalogue,
                                       String msdsDatabase, String window, String istcmIsApplication) {
    StringBuffer MsgBF = new StringBuffer();
    int f = Integer.parseInt(from);
    int it = Integer.parseInt(interval);
    f = f - it;

    MsgBF.append("<FORM METHOD=\"POST\" NAME=\"backForm\" action=\"" + bundle.getString("BUILD_SERVLET") + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"sortby\" VALUE=\"" + sortby + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"limit\" VALUE=\"" + limit + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"fac\" VALUE=\"" + fac + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"from\" VALUE=\"" + f + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"interval\" VALUE=\"" + interval.toString() + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"item\" VALUE=\"" + item + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"noncatalog\" VALUE=\"" + nonCatalogue + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"Session_ID\" VALUE=\"" + Session_ID + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"msdsdataabase\" VALUE=\"" + msdsDatabase + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"istcmIsApplication\" VALUE=\"" + istcmIsApplication + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"window\" VALUE=\"" + window + "\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"Second\" VALUE=\"Yes\">\n");
    MsgBF.append("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
    MsgBF.append("</FORM>\n");

    return MsgBF;
  }

  protected StringBuffer buildNextForm(String limit, String Session_ID, String from, String interval, String sortby, String fac, String item, String nonCatalogue,
                                       String msdsDatabase, String window, String istcmIsApplication) {
    StringBuffer MsgNF = new StringBuffer();

    int f = Integer.parseInt(from);
    int it = Integer.parseInt(interval);
    f = f + it;

    MsgNF.append("<FORM METHOD=\"POST\" NAME=\"nextForm\" action=\"" + bundle.getString("BUILD_SERVLET") + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"sortby\" VALUE=\"" + sortby + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"limit\" VALUE=\"" + limit + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"fac\" VALUE=\"" + fac + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"from\" VALUE=\"" + f + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"interval\" VALUE=\"" + it + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"item\" VALUE=\"" + item + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"window\" VALUE=\"" + window + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"noncatalog\" VALUE=\"" + nonCatalogue + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"msdsdataabase\" VALUE=\"" + msdsDatabase + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"Session_ID\" VALUE=\"" + Session_ID + "\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"Second\" VALUE=\"Yes\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
    MsgNF.append("<INPUT TYPE=\"hidden\" NAME=\"istcmIsApplication\" VALUE=\"" + istcmIsApplication + "\">\n");
    MsgNF.append("</FORM>\n");

    return MsgNF;
  }

  public static String Remove20from(String s) {
    StringTokenizer st = new StringTokenizer(s, "%20");

    String result = "";
    if (st.countTokens() > 1) {
      while (st.hasMoreTokens()) {
        String tok = st.nextToken();
        result += tok + " ";
      }
      //removing the last space
      result = result.substring(0, result.length() - 1);
    } else {
      result = s;
    }
    return result;
  }
}
