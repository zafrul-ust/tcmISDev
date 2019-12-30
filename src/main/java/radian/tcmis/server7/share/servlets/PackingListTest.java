package radian.tcmis.server7.share.servlets;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.sql.ResultSet;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.both1.helpers.BothHelpObjs;

import radian.tcmis.server7.client.utc.dbObjs.UTCTcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import com.tcmis.common.creator.CodeCreator;

public class PackingListTest extends HttpServlet {
  UTCTcmISDBModel db = null;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    response.setContentType("Text/html");
    //System.out.println("\n\n------- got here PWA mares report");
    try {
      //get database connection
      db = new UTCTcmISDBModel("TCM_OPS","1");
      if (db == null) {
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        return;
      }

      //creating beans and factory
      CodeCreator codeCreator = new CodeCreator();
      codeCreator.createBean("com.tcmis.internal.hub","shipment","c:\\temp\\",db.getConnection());
      codeCreator.createFactory("com.tcmis.internal.hub","packing_list_header_view","c:\\temp\\",db.getConnection());
      /*
      PackingListReportGenerator packingListReportGenerator = new PackingListReportGenerator();
      String url = packingListReportGenerator.buildReport(getHeaderData(),getDetailData(),getLogoURL());

      System.out.println("------- Picking list DONE: "+url);
          */
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "Printing Packing List", "Error occurred while printing packing list ", 86030, false);
    } finally {
      db.close();
    }
  } //end of doGet

  Vector getHeaderData() {
    Vector v = new Vector(11);
    DBResultSet dbrs=null;
    ResultSet rs=null;
    try {
      dbrs=db.doQuery("select * from packing_list_header_view where shipment_id in (135,246)");
      rs=dbrs.getResultSet();
      Vector processID = new Vector();
      Vector processName = new Vector();
      while (rs.next()) {
        Hashtable h = new Hashtable(37);
        h.put("SHIPMENT_ID",BothHelpObjs.makeBlankFromNull(rs.getString("SHIPMENT_ID")));
        h.put("DATE_PICKED",BothHelpObjs.makeBlankFromNull(rs.getString("DATE_PICKED")));
        h.put("DATE_PRINTED",BothHelpObjs.makeBlankFromNull(rs.getString("DATE_PRINTED")));
        h.put("SOURCE_HUB",BothHelpObjs.makeBlankFromNull(rs.getString("SOURCE_HUB")));
        h.put("TAX_ID",BothHelpObjs.makeBlankFromNull(rs.getString("TAX_ID")));
        h.put("SHIP_TO_LOCATION_ID",BothHelpObjs.makeBlankFromNull(rs.getString("SHIP_TO_LOCATION_ID")));
        h.put("HUB_LOCATION_ID",BothHelpObjs.makeBlankFromNull(rs.getString("HUB_LOCATION_ID")));
        h.put("SHIP_TO_COUNTRY_ABBREV",BothHelpObjs.makeBlankFromNull(rs.getString("SHIP_TO_COUNTRY_ABBREV")));
        h.put("SHIP_TO_STATE_ABBREV",BothHelpObjs.makeBlankFromNull(rs.getString("SHIP_TO_STATE_ABBREV")));
        h.put("SHIP_TO_ADDRESS_LINE_1",BothHelpObjs.makeBlankFromNull(rs.getString("SHIP_TO_ADDRESS_LINE_1")));
        h.put("SHIP_TO_ADDRESS_LINE_2",BothHelpObjs.makeBlankFromNull(rs.getString("SHIP_TO_ADDRESS_LINE_2")));
        h.put("SHIP_TO_ADDRESS_LINE_3",BothHelpObjs.makeBlankFromNull(rs.getString("SHIP_TO_ADDRESS_LINE_3")));
        h.put("SHIP_TO_CITY",BothHelpObjs.makeBlankFromNull(rs.getString("SHIP_TO_CITY")));
        h.put("SHIP_TO_ZIP",BothHelpObjs.makeBlankFromNull(rs.getString("SHIP_TO_ZIP")));
        h.put("SHIP_TO_LOCATION_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("SHIP_TO_LOCATION_DESC")));
        h.put("SHIP_TO_PHONE","");
        h.put("SHIP_TO_FAX","");
        h.put("HUB_COUNTRY_ABBREV",BothHelpObjs.makeBlankFromNull(rs.getString("HUB_COUNTRY_ABBREV")));
        h.put("HUB_STATE_ABBREV",BothHelpObjs.makeBlankFromNull(rs.getString("HUB_STATE_ABBREV")));
        h.put("HUB_ADDRESS_LINE_1",BothHelpObjs.makeBlankFromNull(rs.getString("HUB_ADDRESS_LINE_1")));
        h.put("HUB_ADDRESS_LINE_2",BothHelpObjs.makeBlankFromNull(rs.getString("HUB_ADDRESS_LINE_2")));
        h.put("HUB_ADDRESS_LINE_3",BothHelpObjs.makeBlankFromNull(rs.getString("HUB_ADDRESS_LINE_3")));
        h.put("HUB_CITY",BothHelpObjs.makeBlankFromNull(rs.getString("HUB_CITY")));
        h.put("HUB_ZIP",BothHelpObjs.makeBlankFromNull(rs.getString("HUB_ZIP")));
        h.put("HUB_LOCATION_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("HUB_LOCATION_DESC")));
        h.put("HUB_PHONE","");
        h.put("HUB_FAX","");
        v.addElement(h);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return v;
  } //end of method

  Vector getDetailData() {
    Vector v = new Vector(11);
    DBResultSet dbrs=null;
    ResultSet rs=null;
    try {
      dbrs=db.doQuery("select * from packing_list_detail_view where shipment_id in (135,246)");
      rs=dbrs.getResultSet();
      Vector processID = new Vector();
      Vector processName = new Vector();
      while (rs.next()) {
        Hashtable h = new Hashtable(13);
        h.put("SHIPMENT_ID",BothHelpObjs.makeBlankFromNull(rs.getString("SHIPMENT_ID")));
        h.put("PR_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("PR_NUMBER")));
        h.put("LINE_ITEM",BothHelpObjs.makeBlankFromNull(rs.getString("LINE_ITEM")));
        h.put("RADIAN_PO",BothHelpObjs.makeBlankFromNull(rs.getString("RADIAN_PO")));
        h.put("PO_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("PO_NUMBER")));
        h.put("RECEIPT_ID",BothHelpObjs.makeBlankFromNull(rs.getString("RECEIPT_ID")));
        h.put("CAT_PART_NO",BothHelpObjs.makeBlankFromNull(rs.getString("CAT_PART_NO")));
        h.put("PART_SHORT_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("PART_SHORT_NAME")));
        h.put("QUANTITY_DELIVERED",BothHelpObjs.makeBlankFromNull(rs.getString("QUANTITY_DELIVERED")));
        h.put("UNIT_OF_SALE",BothHelpObjs.makeBlankFromNull(rs.getString("UNIT_OF_SALE")));
        h.put("DELIVERY_POINT",BothHelpObjs.makeBlankFromNull(rs.getString("DELIVERY_POINT")));
        h.put("DELIVERY_POINT_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("DELIVERY_POINT_DESC")));
        v.addElement(h);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return v;
  } //end of method

  String getLogoURL() {
    String logoURL = "https://www.tcmis.com/images/logo_s.gif";
    DBResultSet dbrs=null;
    ResultSet rs=null;
    try {
      dbrs=db.doQuery("select distinct hc.po_image_url from home_company hc, hub h, shipment s"+
                      " where hc.home_company_id = h.home_company_id and h.branch_plant = s.branch_plant"+
                      " and s.shipment_id in (135,246)");
      rs=dbrs.getResultSet();
      Vector processID = new Vector();
      Vector processName = new Vector();
      while (rs.next()) {
        logoURL = BothHelpObjs.makeBlankFromNull(rs.getString("po_image_url"));
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return logoURL;
  } //end of method

} //end of class