package radian.web.servlets.share;

import java.util.Vector;
import java.util.Hashtable;
import java.sql.ResultSet;
import java.net.URLEncoder;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.helpers.HelpObjs;
/**
 * Title:        tcmIS
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corp
 * @author Rajendra Rajput
 * @version 1.0
 */

public class CatalogAddApprovalHelper {
    private TcmISDBModel db = null;

    public CatalogAddApprovalHelper(TcmISDBModel db) {
      this.db = db;
    } //end of method

    public Vector getApprovalRoles(String personnelID, String source) throws Exception {
      Vector v = new Vector(19);
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        String query = "select distinct ca.approval_role from chemical_approver ca, vv_chemical_approval_role car"+
                       " where ca.facility_id = car.facility_id and ca.approval_role = car.approval_role and ca.catalog_id = car.catalog_id and ca.catalog_company_id = car.catalog_company_id"+
                       " and ca.active = 'y' and car.active = 'Y' and lower(car.catalog_add_process_url) like '%"+source.toLowerCase()+"%'"+
                       " and personnel_id = "+personnelID;
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
          v.addElement(rs.getString("approval_role"));
        }
      }catch (Exception e) {
        throw e;
      }finally {
        dbrs.close();
      }
      return v;
    } //end of method

    public boolean allowToApproveRequest(String personnelID, String approvalRole, String requestID) {
      boolean result = false;
      try {
        String query = "select count(*) from catalog_add_request_new carn,chemical_approver ca, vv_chemical_approval_role car,"+
                       "vv_catalog_add_request_status cars where carn.request_id = "+requestID+
                       " and carn.eng_eval_facility_id = ca.facility_id and carn.catalog_id = ca.catalog_id and carn.catalog_company_id = ca.catalog_company_id"+
							  " and ca.personnel_id = "+personnelID+" and ca.approval_role = '"+approvalRole+"'"+
                       " and ca.facility_id = car.facility_id and ca.approval_role = car.approval_role"+
							  " and ca.catalog_id = car.catalog_id and ca.catalog_company_id = car.catalog_company_id"+
							  " and ca.active = 'y' and car.active = 'Y' and carn.request_status = cars.request_status"+
                       " and cars.approval_group = car.approval_group and carn.approval_group_seq = car.approval_group_seq";
        if (HelpObjs.countQuery(db,query) > 0) {
          result = true;
        }
      }catch (Exception e) {
        e.printStackTrace();
        result = false;
      }
      return result;
    } //end of method

    public Hashtable buildRequestDetail(String requestID) throws Exception {
      DBResultSet dbrs = null;
      ResultSet rs = null;
      StringBuffer headerInfo = new StringBuffer();
      StringBuffer componentInfo = new StringBuffer();
      StringBuffer itemInfo = new StringBuffer();
      Hashtable result = new Hashtable(13);
      String client = db.getClient();
      String detailquery = "select xx.full_name,xx.phone,xx.FACILITY_NAME,xx.APPLICATION_DESC,xx.REQUEST_ID,xx.part_id,xx.process_desc,xx.part_description,"+
      "xx.CUSTOMER_REQUEST_ID,xx.MANUFACTURER,xx.MATERIAL_DESC,xx.GRADE,xx.MFG_TRADE_NAME, xx.CUSTOMER_MSDS_NUMBER,xx.MFG_CATALOG_ID,"+
      "xx.item_id,xx.item_desc,xx.item_packaging,"+
      "xx.PACKAGING,xx.SUGGESTED_VENDOR,xx.VENDOR_CONTACT_NAME, xx.VENDOR_CONTACT_PHONE,xx.VENDOR_CONTACT_FAX, xx.VENDOR_CONTACT_EMAIL,"+
      "xx.VENDOR_PART_NO, xx.CAT_PART_NO,xx.shelf_life,xx.shelf_life_unit,xx.shelf_life_basis,"+
      "xx.storage_temp, xx.catalog_id,xx.COMPONENTS_PER_ITEM,xx.SAMPLE_ONLY, xx.material_id,xx.baseline_price,xx.catalog_price,xx.facility_id"+
      " from "+
      "(select c.last_name || ', ' || c.first_name full_name,c.phone,f.FACILITY_NAME,g.APPLICATION_DESC,a.REQUEST_ID,d.part_id, "+
      "a.CUSTOMER_REQUEST_ID,d.MANUFACTURER,d.MATERIAL_DESC,d.GRADE,d.MFG_TRADE_NAME,b.process_desc,a.part_description, "+
      "d.CUSTOMER_MSDS_NUMBER,d.MFG_CATALOG_ID,d.item_id,fx_item_desc(d.item_id,'MA') item_desc,fx_kit_packaging(d.item_id) item_packaging,"+
      "fx_kit_packaging(d.item_id,d.material_id,'Y') PACKAGING, d.SUGGESTED_VENDOR,d.VENDOR_CONTACT_NAME, "+
      "d.VENDOR_CONTACT_PHONE,d.VENDOR_CONTACT_FAX, d.VENDOR_CONTACT_EMAIL,a.VENDOR_PART_NO, a.CAT_PART_NO,"+
      "d.shelf_life_days shelf_life,'days' shelf_life_unit,decode(d.shelf_life_basis,'M','Manufactured','S','Shipped','R','Received') shelf_life_basis,"+
      "d.storage_temp, a.catalog_id,d.COMPONENTS_PER_ITEM,d.SAMPLE_ONLY,d.material_id,a.baseline_price,a.catalog_price,b.facility_id "+
      "from catalog_add_request_new a, catalog_add_user_group b, personnel c, catalog_add_item d, facility f, fac_loc_app g "+
      "where "+
      "a.REQUEST_ID="+requestID+" and "+
      "b.REQUEST_ID="+requestID+" and "+
      "d.REQUEST_ID="+requestID+" and d.line_item = 1 and "+
      "a.REQUESTOR = c.personnel_id "+
      "and b.facility_id = f.facility_id and b.work_area = g.application and b.facility_id = g.facility_id) xx, "+
      "customer_msds_xref z "+
      "where "+
      "xx.CUSTOMER_MSDS_NUMBER = z.customer_msds_number(+) and "+
      "xx.catalog_id = z.customer_msds_db(+) "+
      "group by "+
      "xx.full_name,xx.phone,xx.FACILITY_NAME,xx.APPLICATION_DESC,xx.REQUEST_ID,xx.part_id,xx.process_desc,xx.part_description,"+
      "xx.CUSTOMER_REQUEST_ID,xx.MANUFACTURER,xx.MATERIAL_DESC,xx.GRADE,xx.MFG_TRADE_NAME, xx.CUSTOMER_MSDS_NUMBER,xx.MFG_CATALOG_ID,"+
      "xx.item_id,xx.item_desc,xx.item_packaging,"+
      "xx.PACKAGING,xx.SUGGESTED_VENDOR,xx.VENDOR_CONTACT_NAME,xx.VENDOR_CONTACT_PHONE,xx.VENDOR_CONTACT_FAX,xx.VENDOR_CONTACT_EMAIL,"+
      "xx.VENDOR_PART_NO,xx.CAT_PART_NO,xx.shelf_life,xx.shelf_life_unit,xx.shelf_life_basis,xx.storage_temp,xx.catalog_id,"+
      "xx.COMPONENTS_PER_ITEM,xx.SAMPLE_ONLY,xx.material_id,xx.baseline_price,xx.catalog_price,xx.facility_id";

      int totalrecords = 0;
      String itemPackaging = "";
      try {
          dbrs = db.doQuery(detailquery);
          rs=dbrs.getResultSet();
          while(rs.next()) {
            totalrecords +=1;
            //header info
            if (totalrecords == 1) {
              headerInfo.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n");
              //Requestor
              headerInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Requestor: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
              headerInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("FULL_NAME")));headerInfo.append("</FONT></TD></TR>\n");
              //Phone
              headerInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Phone: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
              headerInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PHONE")));headerInfo.append("</FONT></TD></TR>\n");
              //Facility
              headerInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Facility: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
              headerInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("FACILITY_NAME")));headerInfo.append("</FONT></TD></TR>\n");
              //Work Area
              headerInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Work Area: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
              headerInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("APPLICATION_DESC")));headerInfo.append("</FONT></TD></TR>\n");
              //Radian Request ID
              headerInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Haas Request ID: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
              headerInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_ID")));headerInfo.append("</FONT></TD></TR>\n");
              if ("Seagate".equalsIgnoreCase(client)) {
                //CUSTOMER_REQUEST_ID
                headerInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Seagate Request ID: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
                headerInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("CUSTOMER_REQUEST_ID")));headerInfo.append("</FONT></TD></TR>\n");
              }
              //Part Desc
              headerInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Part Desc: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
              headerInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("part_description")));headerInfo.append("</FONT></TD></TR>\n");
              //Process Desc
              headerInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Process Desc: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
              headerInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("process_desc")));headerInfo.append("</FONT></TD></TR>\n");
              headerInfo.append("<TR><TD ALIGN=\"CENTER\" BGCOLOR=\"#000066\" COLSPAN=\"2\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#ffffff\"><B>Component Information</B></FONT></TD></TR>\n");
            }

            //show black line between components
            if (totalrecords > 1) {
              componentInfo.append("<TR><TD COLSPAN=\"2\"><HR STYLE=\"color: 'black'; height: '3'; text-align: 'center'; width: '80%'\"></TD></TR>\n");
            }
            //PART
            componentInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Component: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
            componentInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PART_ID")));componentInfo.append("</FONT></TD></TR>\n");
            //MATERIAL_DESC
            componentInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Material Description: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
            componentInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MATERIAL_DESC")));componentInfo.append("</FONT></TD></TR>\n");
            //Grade
            componentInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Grade: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
            componentInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("GRADE")));componentInfo.append("</FONT></TD></TR>\n");
            //MFG_TRADE_NAME
            componentInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Trade Name: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
            componentInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_TRADE_NAME")));componentInfo.append("</FONT></TD></TR>\n");
            //MANUFACTURER
            componentInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>ManuFacturer: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
            componentInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MANUFACTURER")));componentInfo.append("</FONT></TD></TR>\n");
            //MFG_CATALOG_ID
            componentInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Mfg Part #: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
            componentInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_CATALOG_ID")));componentInfo.append("</FONT></TD></TR>\n");
            //Material ID
            String material_id = BothHelpObjs.makeBlankFromNull(rs.getString("material_id"));
            if (material_id.length() > 1) {
              //customer msds number
              String customerMSDS = BothHelpObjs.makeBlankFromNull(rs.getString("CUSTOMER_MSDS_NUMBER"));
              if (customerMSDS.length() > 0) {
                componentInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Your MSDS #: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
                componentInfo.append("<A HREF=\"/tcmIS/"+client.toLowerCase()+"/ViewMsds?act=msds&showspec=N&id=");
                componentInfo.append(material_id);componentInfo.append("&cl="+client.toLowerCase()+"\" TARGET=\"msds\">");
                componentInfo.append(customerMSDS);
                componentInfo.append("</A></FONT></TD></TR>\n");
              }else {
                componentInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Haas MSDS #: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
                componentInfo.append("<A HREF=\"/tcmIS/"+client.toLowerCase()+"/ViewMsds?act=msds&showspec=N&id=");
                componentInfo.append(material_id);componentInfo.append("&cl="+client.toLowerCase()+"\" TARGET=\"msds\">");
                componentInfo.append(material_id);
                componentInfo.append("</A></FONT></TD></TR>\n");
              }
            }else {
              //CUSTOMER_MSDS_NUMBER
              componentInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>MSDS #: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
              componentInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("CUSTOMER_MSDS_NUMBER")));componentInfo.append("</FONT></TD></TR>\n");
            }
            //PACKAGING
            componentInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Packaging: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
            String Packaging_display = BothHelpObjs.makeSpaceFromNull(rs.getString("PACKAGING"));
            int comp_per_item = BothHelpObjs.makeZeroFromNull(rs.getString("COMPONENTS_PER_ITEM"));
            String sample_only = BothHelpObjs.makeSpaceFromNull(rs.getString("SAMPLE_ONLY"));
            if ("y".equalsIgnoreCase(sample_only)) {
              Packaging_display += " (Sample Size) ";
            }
            componentInfo.append(Packaging_display);
            componentInfo.append("</FONT></TD></TR>\n");
            //shelf life
            String mySL = "";
            String tmpSL = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life_unit"));
            if (tmpSL.startsWith("Indefinite")) {
              mySL = tmpSL;
            }else if (tmpSL.startsWith("Select")) {
              mySL = "";
            }else {
              mySL = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life")) + " "+tmpSL;
              mySL += " from date of "+BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life_basis"));
            }
            componentInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Shelf Life: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
            if ( !( "from date of".equalsIgnoreCase(mySL.trim()) || mySL.trim().length() == 0 ) ) {
              componentInfo.append( mySL );
            }
            componentInfo.append("</FONT></TD></TR>\n");
            //storage temperature
            componentInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Storage Temp: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
            componentInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("storage_temp")));componentInfo.append("</FONT></TD></TR>\n");

            //display this info is static for all kits
            if (totalrecords == 1) {
              //suggested supplier
              itemInfo.append("<TR><TD ALIGN=\"CENTER\" BGCOLOR=\"#000066\" COLSPAN=\"2\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#ffffff\"><B>Requestor Suggested Supplier</B></FONT></TD></TR>\n");
              //SUGGESTED_VENDOR
              itemInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Supplier Name: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
              itemInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("SUGGESTED_VENDOR")));itemInfo.append("</FONT></TD></TR>\n");
              //VENDOR_CONTACT_NAME
              itemInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Contact Name: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
              itemInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("VENDOR_CONTACT_NAME")));itemInfo.append("</FONT></TD></TR>\n");
              //Suuplier Contact Phone
              itemInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Phone: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
              itemInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("VENDOR_CONTACT_PHONE")));itemInfo.append("</FONT></TD></TR>\n");
              //Suuplier Contact Fax
              itemInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Fax: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
              itemInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("VENDOR_CONTACT_FAX")));itemInfo.append("</FONT></TD></TR>\n");
              //Suuplier Contact Email
              itemInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Email: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
              itemInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("VENDOR_CONTACT_EMAIL")));itemInfo.append("</FONT></TD></TR>\n");
              //VENDOR_PART_NO
              itemInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Supplier Part #: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
              itemInfo.append(""+BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_PART_NO"))+"");itemInfo.append("</FONT></TD></TR>\n");
              //Item summary
              itemInfo.append("<TR><TD ALIGN=\"CENTER\" BGCOLOR=\"#000066\" COLSPAN=\"2\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#ffffff\"><B>Item Summary</B></FONT></TD></TR>\n");
              //item ID
              itemInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Item ID: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
              itemInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("item_id")));itemInfo.append("</FONT></TD></TR>\n");
              //item Desc
              itemInfo.append("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Item Description: </B></FONT></TD><TD BGCOLOR=\"#E6E8FA\"><FONT FACE=\"Arial\" SIZE = \"2\">");
              itemInfo.append(BothHelpObjs.makeSpaceFromNull(rs.getString("item_desc")));itemInfo.append("</FONT></TD></TR>\n");
              //item packaging
              itemInfo.append("<TR>\n<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Item Packaging: </B></FONT></TD><TD><FONT FACE=\"Arial\" SIZE = \"2\">");
              itemPackaging = BothHelpObjs.makeSpaceFromNull(rs.getString("item_packaging"));
              itemInfo.append(itemPackaging);
              itemInfo.append("</FONT></TD></TR>\n");
              result.put("CAT_PART_NO",BothHelpObjs.makeBlankFromNull(rs.getString("CAT_PART_NO")).trim());
              result.put("BASELINE_PRICE",BothHelpObjs.makeBlankFromNull(rs.getString("baseline_price")).trim());
              result.put("CATALOG_PRICE",BothHelpObjs.makeBlankFromNull(rs.getString("catalog_price")).trim());
              result.put("FACILITY_ID",rs.getString("facility_id"));
              result.put("ITEM_PACKAGING",itemPackaging);
            }  //end of static info
          }  //end of while
        }catch (Exception e1) {
          e1.printStackTrace();
          throw e1;
        }finally {
          dbrs.close();
        }
        //put it all together
        result.put("HEADER_INFO",headerInfo);
        result.put("COMPONENT_INFO",componentInfo);
        result.put("ITEM_INFO",itemInfo);
        result.put("TOTAL_RECORD",new Integer(totalrecords));
        return result;
    } //end of method

    public StringBuffer buildRequest(String Cat_Servlet, String Message, Vector approvalRoleV, String selectedApprovalRole, String personnelID) throws Exception {
      DBResultSet dbrs = null;
      ResultSet rs = null;
      StringBuffer msg = new StringBuffer();
      msg.append("<BODY>\n");
      msg.append("<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" >\n");
      if (selectedApprovalRole.length() > 0) {
        msg.append( "<TR><TD WIDTH=\"100%\" ALIGN=\"CENTER\"><B>Requests Pending "+selectedApprovalRole+"<B></TD></TR>\n" );
      }else {
        msg.append( "<TR><TD WIDTH=\"100%\" ALIGN=\"CENTER\"><B>Requests Pending Approval<B></TD></TR>\n" );
      }
      msg.append("<TR VALIGN=\"TOP\">\n");
      msg.append("<TD WIDTH=\"200\">\n");
      msg.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
      msg.append("</TD>\n");
      msg.append("<TD WIDTH=\"525\" ALIGN=\"right\">\n");
      msg.append("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
      msg.append("</TD>\n");
      msg.append("</TR>\n");
      msg.append("<TR><TD ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>"+Message+"</FONT></B></TD></TR>\n");
      msg.append("</TABLE>\n");
      //search by approval role
      msg.append("<FORM METHOD=\"POST\" name=\"MainForm\" action=\""+Cat_Servlet+"Session=Search\" onsubmit =\"return auditSearch()\">\n");
      msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n");
      msg.append("<TR VALIGN=\"TOP\">\n");
      if (approvalRoleV.size() > 1) {
        msg.append("<TD WIDTH=\"15%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
        msg.append("<B>Approval Role:</B>\n");
        msg.append("</TD>\n");
        msg.append("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        msg.append("<SELECT CLASS=\"HEADER\" NAME=\"searchBy\" ID=\"searchBy\" size=\"1\">\n");
        msg.append("<OPTION VALUE=\"None\">Select One</OPTION>\n");
      }else {
        msg.append("<TD WIDTH=\"15%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\" CLASS=\"announce\" STYLE=\"display: none;\">\n");
        msg.append("<B>Approval Role:</B>\n");
        msg.append("</TD>\n");
        msg.append("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        msg.append("<SELECT CLASS=\"HEADER\" NAME=\"searchBy\" ID=\"searchBy\" size=\"1\" STYLE=\"display: none;\">\n");
      }
      //fill approval role drop down
      for (int i = 0; i < approvalRoleV.size(); i++) {
        String preSelect = "";
        if (selectedApprovalRole.equalsIgnoreCase((String)approvalRoleV.elementAt(i))) {
          preSelect = "selected";
        }
        msg.append("<option "+preSelect+" value=\""+(String)approvalRoleV.elementAt(i)+"\">"+(String)approvalRoleV.elementAt(i)+"</option>\n");
      }
      msg.append("</SELECT>\n");
      msg.append("</TR>\n");

      //search button
      if (approvalRoleV.size() > 1) {
        msg.append("<TD WIDTH=\"70%\" CLASS=\"announce\"  ALIGN=\"LEFT\" COLSPAN=\"4\"  VALIGN=\"MIDDLE\">\n");
        msg.append("<INPUT type=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"List Requests\" name=\"Search\">\n");
      }else {
        msg.append("<TD WIDTH=\"70%\" CLASS=\"announce\"  ALIGN=\"LEFT\" COLSPAN=\"4\"  VALIGN=\"MIDDLE\" STYLE=\"display: none;\">\n");
        msg.append("<INPUT type=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"List Requests\" name=\"Search\">\n");
      }
      msg.append("</TABLE>\n");
      msg.append("</FORM>\n");
      //result table
      msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n");
      msg.append("<TR>\n");
      msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Request Id</B></FONT></TD>\n");
      if ("Seagate".equalsIgnoreCase(db.getClient()))
      {
        msg.append( "<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Seagate Request Id</B></FONT></TD>\n" );
      }
      msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Requested</B></FONT></TD>\n");
      msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Requestor</B></FONT></TD>\n");
      msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Facility</B></FONT></TD>\n");
      msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Workarea</B></FONT></TD>\n");
      msg.append("</TR>\n");

      int totalrecords = 0;
      int count = 0;
      if (selectedApprovalRole.length() > 0) {
        String query = "select * from cat_add_pending_view where personnel_id = "+personnelID+" and lower(approval_role) = '"+selectedApprovalRole.toLowerCase()+"'";
        try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while(rs.next()) {
            totalrecords += 1;
            count += 1;
            if (count%10==0) {
              msg.append("<TR>\n");
              msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Request Id</B></FONT></TD>\n");
              if ("Seagate".equalsIgnoreCase(db.getClient())) {
                msg.append( "<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Seagate Request Id</B></FONT></TD>\n" );
              }
              msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Requested</B></FONT></TD>\n");
              msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Requestor</B></FONT></TD>\n");
              msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Facility</B></FONT></TD>\n");
              msg.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Workarea</B></FONT></TD>\n");
              msg.append("</TR>\n");
            }

            String Color = " ";
            if (count%2==0) {
                Color = "bgcolor=\"#E6E8FA\"";
            }else {
               Color = "bgcolor=\"#fcfcfc\"";
            }
            msg.append("<TR>\n");
            msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
            msg.append("<A HREF=\""+Cat_Servlet+"Session=1&approvalRole="+URLEncoder.encode(selectedApprovalRole)+"&request_id="+BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_ID"))+"\">");
            msg.append(""+BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_ID"))+"</A>");
            msg.append("</FONT></TD>\n");
            if ("Seagate".equalsIgnoreCase(db.getClient())) {
              msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("CUSTOMER_REQUEST_ID")));msg.append("</FONT></TD>\n");
            }
            msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_DATE")));msg.append("</FONT></TD>\n");
            msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("FULL_NAME")));msg.append("</FONT></TD>\n");
            msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("FACILITY_NAME")));msg.append("</FONT></TD>\n");
            msg.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("APPLICATION_DESC")));msg.append("</FONT></TD>\n");
            msg.append("</TR>\n");
          }
        }catch (Exception e) {
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
          if (totalrecords==0)
          {msg.append("<TR><TD>No Records Found</TD></TR>\n");}
        }
      }else {
        msg.append("<TR><TD>Please select an approval role and click on List Requests</TD></TR>\n");
      }
      msg.append("</TABLE>\n");

      return msg;
    } //end of method

} //end of class