package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.servlets.NChemObj;
/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 10-01-03 - Updating the Cat_part_no in the label information table after the catpart number is entered
 * 07-19-04 - Changed query to improve performance. Fixed the MSDs link
 */
public class SCataloging extends HttpServlet
{
    private ServerResourceBundle bundle=null;
    private String request_id = null;
    private TcmISDBModel db = null;
    private String Session = null;
    private String ShowThis = "";
    private String Cat_Servlet = "";

    public SCataloging(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    boolean updatePartNo(String pn, String requestID, String approvalRole, String personnelID, String uom, String unitOfSaleQtyPerEach, String catalogID) {
      boolean result = true;
      try {
        String query = "update catalog_add_request_new set cat_part_no = '"+pn+"'";
        if (catalogID.length() > 0) {
          query += ",catalog_id = '"+catalogID+"'";
        }
        if (uom.length() > 0) {
          query += ",unit_of_sale = '"+uom+"'";
        }
        if (unitOfSaleQtyPerEach.length() > 0) {
          query += ",unit_of_sale_price = "+unitOfSaleQtyPerEach;
        }
        query += " where request_id = "+requestID;
        db.doUpdate(query);
        //update item label info
        if ("Seagate".equalsIgnoreCase(db.getClient())) {
          String facpartQuery = "update inventory_group_item_label set CAT_PART_NO='"+pn.trim()+"' where (item_id,inventory_group) in "+
                                "(select cai.item_id,f.inventory_group_default from catalog_add_request_new carn,catalog_add_item cai,facility f "+
                                "where carn.eng_eval_facility_id = f.facility_id and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and carn.request_id = "+requestID+")";
          db.doUpdate( facpartQuery );
        }
        //set approval role info
        Hashtable h = new Hashtable();
        h.put("REQ_ID",request_id);
        h.put( "USER_ID",""+personnelID+"" );
        String[] vec = new String[2];
        vec[0] = approvalRole;
        vec[1] = "";
        h.put("ROLES_DATA",vec);
        //approval request
        NChemObj NcObj = new NChemObj(bundle,db);
        String temp = NcObj.approveRequest(h);
        if ("Error".equalsIgnoreCase(temp)) {
          result = false;
        }
      }catch (Exception e) {
        e.printStackTrace();
        result = false;
      }
      return result;
    } //end of method

    public void doResponse(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String client=bundle.getString( "DB_CLIENT" );
        HttpSession reqsession = request.getSession();
        String personnelid= reqsession.getAttribute( "PERSONNELID" ) == null ? "" : reqsession.getAttribute( "PERSONNELID" ).toString();

        Vector approvalRoleV = reqsession.getAttribute( "APPROVAL_ROLES" ) == null ? new Vector(0) : (Vector)reqsession.getAttribute( "APPROVAL_ROLES" );
        Cat_Servlet = bundle.getString("CATALOGING_SERVLET");
        try{request_id = request.getParameter("request_id").toString();}catch (Exception e1){request_id = "";}
        try{Session= request.getParameter("Session").toString();}catch (Exception e1){Session = "0";}
        String csm_part_no = "";
        try{csm_part_no= request.getParameter("csm_part_no").toString();}catch (Exception e1){csm_part_no = "";}
        String selectedApprovalRole = "";
        try{selectedApprovalRole= request.getParameter("searchBy").toString();}catch (Exception e1){selectedApprovalRole = "";}
        String approvalRole = "";
        try{approvalRole= request.getParameter("approvalRole").toString();}catch (Exception e1){approvalRole = "";}
        if(Session.equalsIgnoreCase("1")) {
          if (request_id.length() < 1) {
            buildRequests(out,"",approvalRoleV,selectedApprovalRole,personnelid);
          }else {
            buildDetails(out,request_id,"",approvalRole,personnelid);
          }
        }else if (Session.equalsIgnoreCase("Search")) {
          buildRequests(out,"",approvalRoleV,selectedApprovalRole,personnelid);
        }else if (Session.equalsIgnoreCase("Update")) {
          String catalogID = "";
          try{catalogID= request.getParameter("catalogID").toString();}catch (Exception e1){catalogID = "";}
          String uom = "";
          try{uom= request.getParameter("uom").toString();}catch (Exception e1){uom = "";}
          String unitOfSaleQtyPerEach = "";
          try{unitOfSaleQtyPerEach= request.getParameter("unitOfSaleQtyPerEach").toString();}catch (Exception e1){unitOfSaleQtyPerEach = "";}
          if (updatePartNo(csm_part_no.trim(),request_id,approvalRole,personnelid,uom.trim(),unitOfSaleQtyPerEach.trim(),catalogID)) {
            buildRequests(out,"",approvalRoleV,approvalRole,personnelid);
          }else {
            buildDetails(out,request_id,"An error occured and an email has been sent to the person concerned. Please Try Again.",approvalRole,personnelid);
            out.close();
            HelpObjs.sendMail(db,"Error in Part Number Update","Error in Updating Values in Catalog Add Request New for: "+request_id,86030,false);
            return;
          }
        }else {
          //If session is not 1 just print the list of Request ID's
          buildRequests(out,"",approvalRoleV,selectedApprovalRole,personnelid);
        }
        out.close();
    } //end of method

    //This method builds the list of all request ids that require catalog part number
    public void buildDetails(PrintWriter out, String requestID, String messg, String approvalRole, String personnelID) {
      StringBuffer Msgd1 = new StringBuffer();
      String material_id = "";

      if (messg.length() >1){ShowThis = messg;}
      String client=bundle.getString( "DB_CLIENT" );
      printHTMLHeader( "Catalog Add Detail",out );
      if (messg.length() >1) {
        out.println("<BODY onload =\"showMsg()\">\n");
      }else {
        out.println("<BODY>\n");
      }
      out.println("<TABLE BORDER=0 WIDTH=100% >\n");
      out.println( "<TR><TD WIDTH=\"100%\" ALIGN=\"CENTER\"><B>Request Pending "+approvalRole+"</B></TD></TR>\n" );
      out.println("<TR VALIGN=\"TOP\">\n");
      out.println("<TD WIDTH=\"200\">\n");
      out.println("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
      out.println("</TD>\n");
      out.println("<TD WIDTH=\"525\" ALIGN=\"right\">\n");
      out.println("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
      out.println("</TD>\n");
      out.println("</TR>\n");
      out.println("<TR><TD ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>"+messg+"</B></FONT></TD></TR>\n");
      out.println("</TABLE>\n");
      out.println("<FORM METHOD=\"POST\" name=\"catAddDetail\" action=\""+Cat_Servlet+"Session=Update&approvalRole="+URLEncoder.encode(approvalRole)+"\" onsubmit =\"return auditPartNumber(this)\">\n");

      String itemPackaging = "";
      String catPartNo = "";
      int totalrecords = 0;
      CatalogAddApprovalHelper caah = new CatalogAddApprovalHelper(db);
      try {
        Hashtable h = caah.buildRequestDetail(requestID);
        totalrecords = ((Integer)h.get("TOTAL_RECORD")).intValue();
        if (totalrecords > 0) {
          out.println((StringBuffer)h.get("HEADER_INFO"));
          out.println((StringBuffer)h.get("COMPONENT_INFO"));
          out.println((StringBuffer)h.get("ITEM_INFO"));
          out.println("</TABLE>\n");
          itemPackaging = (String)h.get("ITEM_PACKAGING");
          catPartNo = (String)h.get("CAT_PART_NO");
        }else {
          out.println("<TR><TD>No Records Found</TD></TR>\n");
        }
      }catch (Exception eee) {
        eee.printStackTrace();
        printHTMLHeader("Customer Cataloging",out);
        out.println("</HEAD>\n");
        out.println("<BODY>\n");
        buildError("An Error Occured While Querying the Databse",out);
        out.println("</TABLE>\n");
        out.println("</BODY>\n</HTML>\n");
        return;
      }

      out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"95%\" CLASS=\"moveup\" >\n");
      out.println("<TR><TD ALIGN=\"CENTER\" BGCOLOR=\"#000066\" COLSPAN=\"2\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#ffffff\"><B>Part Number Information</B></FONT></TD></TR>\n");
      //displaying catalogs
      Hashtable catalogInfoH = getCatalogForRequest(out,requestID);
      String selectedCatalogID = (String)catalogInfoH.get("SELECTED_CATALOG_ID");
      Vector catalogIDV = (Vector)catalogInfoH.get("CATALOG_ID");
      Vector catalogDescV = (Vector)catalogInfoH.get("CATALOG_DESC");
      if (catalogIDV.size() > 1) {
        StringBuffer msgCatalogInfo = new StringBuffer();
        msgCatalogInfo.append("<TR><TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
        msgCatalogInfo.append("<B>Catalog:</B>\n");
        msgCatalogInfo.append("</TD>\n");
        msgCatalogInfo.append("<TD WIDTH=\"85%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        msgCatalogInfo.append("<SELECT CLASS=\"HEADER\" NAME=\"catalogID\" ID=\"catalogID\" size=\"1\">\n");
        //fill approval role drop down
        ContractPriceDetail cpd = new ContractPriceDetail(db);
        String selectedCatalogDesc = cpd.getDescFromID(catalogIDV,catalogDescV,selectedCatalogID);
        cpd.buildComboBox(catalogIDV,catalogDescV,selectedCatalogDesc,msgCatalogInfo,false);
        msgCatalogInfo.append("</SELECT>\n");
        msgCatalogInfo.append("</TD></TR>\n");
        out.println(msgCatalogInfo);
      }

      boolean showPrice = false;
      try {
        String query = "select count(*) from tcmis_feature where feature = 'newChem.customerPOPrice' and feature_mode = 1";
        showPrice = HelpObjs.countQuery(db,query) > 0;
      }catch (Exception e) {
        e.printStackTrace();
      }
      if (showPrice) {
        ContractPriceDetail cpd = new ContractPriceDetail(db);
        Hashtable h = cpd.getPricingData(request_id);
        String price = "";
        if (h.containsKey("SELLING_PRICE")) {
          price = (String)h.get("SELLING_PRICE");
          //BigDecimal tmp = new BigDecimal(price);
          //tmp = tmp.setScale(2,BigDecimal.ROUND_HALF_UP);
          //price = tmp.toString();
        }
        if (h.containsKey("PRICE_GROUP_CURRENCY")) {
          price += " "+(String)h.get("PRICE_GROUP_CURRENCY");
        }
        price += " per ["+itemPackaging+"]";
        String goodUntil = "";
        if (h.containsKey("GOOD_UNTIL")) {
          goodUntil = (String)h.get("GOOD_UNTIL");
        }
        //Price
        out.println("<TR><TD WIDTH=\"15%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
        out.println("<B>Haas Item Price:</B>\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"85%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        out.println(price);
        out.println("</TD></TR>\n");
        //Good Until
        out.println("<TR><TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
        out.println("<B>Good Until:</B>\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"85%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        out.println(goodUntil);
        out.println("</TD></TR>\n");
        //part number
        out.println("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>\n");
        if ( "Seagate".equalsIgnoreCase( client ) ) {
          out.println( "CSM Part #:\n" );
        }else {
          out.println( "Part #:\n" );
        }
        out.println("</B></FONT></TD>\n");
        out.println("<TD WIDTH=\"85%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        out.println("<INPUT type=\"text\" name=\"csm_part_no\" value=\""+catPartNo.trim()+"\" SIZE=\"30\">");
        //unit of sale info
        String unitOfSale = BothHelpObjs.makeBlankFromNull((String)h.get("UNIT_OF_SALE"));
        String unitOfSaleQtyPerEach = BothHelpObjs.makeBlankFromNull((String)h.get("UNIT_OF_SALE_QTY_PER_EACH"));
        StringBuffer MsgUnitOfSaleInfo = new StringBuffer();
        //UOM
        MsgUnitOfSaleInfo.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
        MsgUnitOfSaleInfo.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
        MsgUnitOfSaleInfo.append("<B>UOM:</B>\n");
        MsgUnitOfSaleInfo.append("<SELECT CLASS=\"HEADER\" NAME=\"uom\" ID=\"uom\" size=\"1\">\n");
        //fill approval role drop down
        Hashtable hhh = cpd.getUnitOfSaleValues();
        unitOfSale = cpd.getDescFromID((Vector)hhh.get("ID"),(Vector)hhh.get("DESC"),unitOfSale);
        cpd.buildComboBox((Vector)hhh.get("ID"),(Vector)hhh.get("DESC"),unitOfSale,MsgUnitOfSaleInfo,true);
        MsgUnitOfSaleInfo.append("</SELECT>\n");
        MsgUnitOfSaleInfo.append("</FONT>\n");
        //unit of sale qty per each
        MsgUnitOfSaleInfo.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
        MsgUnitOfSaleInfo.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
        MsgUnitOfSaleInfo.append("<B>Unit Price:</B>\n");
        MsgUnitOfSaleInfo.append("<INPUT type=\"text\" name=\"unitOfSaleQtyPerEach\" ID=\"unitOfSaleQtyPerEach\" value=\""+unitOfSaleQtyPerEach.trim()+"\" SIZE=\"15\">");
        out.print(MsgUnitOfSaleInfo);
        out.println("</TD></TR>\n");
      }else {
        //part number
        out.println("<TR>\n<TD WIDTH=\"20%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>\n");
        if ( "Seagate".equalsIgnoreCase( client ) ) {
          out.println( "CSM Part #:\n" );
        }else {
          out.println( "Part #:\n" );
        }
        out.println("</B></FONT></TD>\n");
        out.println("<TD WIDTH=\"85%\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
        out.println("<INPUT type=\"text\" name=\"csm_part_no\" value=\""+catPartNo+"\" SIZE=\"30\">");
        out.println("</TD></TR>\n");
      }
      //blank line
      out.println("<TR><TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
      out.println("<B>&nbsp;</B>\n");
      out.println("</TD></TR>\n");
      out.println("</TABLE>\n");
      //end of new session for pricing data

      if (!(totalrecords==0)) {
        if (caah.allowToApproveRequest(personnelID,approvalRole,requestID)) {
          out.println("<INPUT TYPE=\"hidden\" NAME=\"request_id\" VALUE=\""+requestID+"\">\n");
          out.println("<INPUT type=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"Approve\" name=\"UpdateCat\">\n");
        }
        out.println("</FORM>\n");
      }else {
        out.println("</FORM>\n");
      }
      out.println( "<FONT FACE=\"Arial\" SIZE=\"2\"><A HREF=\"" + Cat_Servlet + "Session=0&searchBy="+URLEncoder.encode(approvalRole)+"\">Show All Requests Pending "+approvalRole+"</A>" );
      out.println("</FONT></BODY>\n</HTML>\n");
    } //end of method

    Hashtable getCatalogForRequest(PrintWriter out, String requestID) {
      Hashtable h = new Hashtable(3);
      String query = "select carn.catalog_id selected_catalog_id,c.catalog_id,c.catalog_desc"+
                     " from catalog_add_request_new carn, catalog_facility cf, catalog c"+
                     " where carn.eng_eval_facility_id = cf.facility_id"+
                     " and cf.catalog_id = c.catalog_id and carn.request_id = "+requestID;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        Vector idV = new Vector (7);
        Vector descV = new Vector (7);
        while (rs.next()){
          h.put("SELECTED_CATALOG_ID",BothHelpObjs.makeBlankFromNull(rs.getString("selected_catalog_id")));
          idV.addElement(rs.getString("catalog_id"));
          descV.addElement(rs.getString("catalog_desc"));
        }
        h.put("CATALOG_ID",idV);
        h.put("CATALOG_DESC",descV);
      } catch (Exception e) {
        printHTMLHeader("Customer Cataloging",out);
        out.println("</HEAD>\n");
        out.println("<BODY>\n");
        buildError("An Error Occured While Querying the Databse",out);
        out.println("</TABLE>\n");
        out.println("</BODY>\n</HTML>\n");
      } finally{
        dbrs.close();
      }
      return h;
    } //end of method

    public void buildError(String errMessage,PrintWriter out)
    {
     out.println("<TABLE>\n");
     out.println("    <TR VALIGN=\"TOP\">\n");
     out.println("      <TD>\n");
     out.println("      <DIV ALIGN=\"center\"><FONT FACE=\"Arial\" SIZE=\"3\">\n");
     out.println("<B>"+errMessage+"\n<BR>\n");
     out.println("Please try again.</FONT></DIV>\n\n");
     out.println("      </TD>\n");
     out.println("    </TR>\n");
     out.println("</TABLE>\n");

     return;
    }

    public void buildSeperateScreen(PrintWriter out, String SusMessage)
    {
     StringBuffer MsgH = new StringBuffer();

      if (SusMessage.length() >1){ShowThis = SusMessage;}

      String client=bundle.getString( "DB_CLIENT" );
      if ("Seagate".equalsIgnoreCase(client))
      {
            printHTMLHeader( "Seagate Cataloging",out );
      }
      else
      {
            printHTMLHeader( "Customer Cataloging",out );
      }

      MsgH.append("</HEAD>\n");

      if (SusMessage.length() >1)
      {
      MsgH.append("<BODY onload =\"showMsg()\">\n");
      }
      else
      {
      MsgH.append("<BODY>\n");
      }
      MsgH.append("<TABLE BORDER=0 WIDTH=100% >\n");
      if ("Seagate".equalsIgnoreCase(client))
      {
            MsgH.append( "<TR><TD WIDTH=\"100%\" ALIGN=\"CENTER\"><B>Seagate Cataloging<B></TD></TR>\n" );
      }
      else
      {
            MsgH.append( "<TR><TD WIDTH=\"100%\" ALIGN=\"CENTER\"><B>Customer Cataloging<B></TD></TR>\n" );
      }

      MsgH.append("<TR VALIGN=\"TOP\">\n");
      MsgH.append("<TD WIDTH=\"200\">\n");
      MsgH.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
      MsgH.append("</TD>\n");
      MsgH.append("<TD WIDTH=\"525\" ALIGN=\"right\">\n");
      MsgH.append("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
      MsgH.append("</TD>\n");
      MsgH.append("</TR>\n");
      MsgH.append("<TR><TD ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>"+SusMessage+"</FONT></B></TD></TR>\n");
      MsgH.append("</TABLE>\n");

      MsgH.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n<BR><BR><BR>");
      if ("Seagate".equalsIgnoreCase(client))
      {
            MsgH.append( "<CENTER><A HREF=\"" + Cat_Servlet + "Session=0\">Show All Requests Needing CSM Part Number</A></CENTER>" );
      }
      else
      {
            MsgH.append( "<CENTER><A HREF=\"" + Cat_Servlet + "Session=0\">Show All Requests Needing Part Number</A></CENTER>" );
      }

      MsgH.append("</FONT>\n</BODY>\n</HTML>\n");

     out.println(MsgH);
    }

    public void buildRequests(PrintWriter out, String Message, Vector approvalRoleV, String selectedApprovalRole, String personnelID) {
      String client=bundle.getString( "DB_CLIENT" );
      if (approvalRoleV.size() == 1) {
        selectedApprovalRole = (String) approvalRoleV.firstElement();
      }
      printHTMLHeader( "Requests Pending Approval",out );
      try {
        CatalogAddApprovalHelper caah = new CatalogAddApprovalHelper(db);
        out.println(caah.buildRequest(Cat_Servlet,Message,approvalRoleV,selectedApprovalRole,personnelID));
      }catch (Exception ee) {
        printHTMLHeader("Customer Cataloging",out);
        out.println("</HEAD>\n");
        out.println("<BODY>\n");
        buildError("An Error Occured While Querying the Databse",out);
        out.println("</TABLE>\n");
        out.println("</BODY>\n</HTML>\n");
      }
      out.println("</HTML>\n");
    } //end of method

   public void printHTMLHeader(String PageTitle,PrintWriter out) {
     out.println("<HTML>\n");
     out.println("<HEAD>\n");
     out.println("<TITLE>"+PageTitle+"</TITLE>\n");
     out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
     out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
     out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");

     out.println("<SCRIPT SRC=\"/clientscripts/catalogAdd.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
     out.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
     out.println( "<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
     out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
     out.println("</HEAD>\n");
     return;
   }

    private void printHTMLError(String Error,PrintWriter out)
    {
        out.println("<TABLE><TR>\n");
        out.println("<TD width=\"5%\">\n");
        out.println("</TD>\n");
        out.println("<TD><FONT SIZE=\"2\" FACE=\"Arial\">"+Error+"<BR>\n");
        out.println("</FONT></TD>\n");
        out.println("</TR></TABLE>\n");
        out.println("</BODY></HTML>\n");
        return;
    }
} //end of class
