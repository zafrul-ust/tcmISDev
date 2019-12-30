package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.Password;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.servlets.NChemObj;
/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Trong Ngo
 * @version 1.0
 *
 *
 */
public class CatalogSourcing extends HttpServlet
{
    private ServerResourceBundle bundle=null;
    private String request_id = null;
    private TcmISDBModel db = null;
    private String Session = null;
    private String ShowThis = "";
    private String Cat_Servlet = "";

    public CatalogSourcing(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    boolean updateContractPricing(HttpServletRequest request, String requestID, String approvalRole, String personnelID) {
      boolean result = true;
      try {
        String baseline = "";
        String selling = "";
        String carrier = "";
        try{baseline= request.getParameter("baseline").toString();}catch (Exception e1){baseline = "";}
        try{selling= request.getParameter("selling").toString();}catch (Exception e1){selling = "";}
        String query = "update catalog_add_request_new set baseline_price = "+baseline.trim()+",catalog_price = "+selling.trim()+" where REQUEST_ID = "+requestID;
        db.doUpdate(query);
        //set approval role info
        Hashtable h = new Hashtable();
        h.put("REQ_ID",request_id);
        h.put( "USER_ID",personnelID);
        String[] vec = new String[2];
        vec[0] = approvalRole;
        vec[1] = "";
        h.put("ROLES_DATA",vec);
        //approval request
        NChemObj NcObj = new NChemObj(bundle,db);
        String temp = NcObj.approveRequest(h);
        if ("Error".equalsIgnoreCase(temp)) {
          result = false;
        }else {
          ContractPriceDetail cpd = new ContractPriceDetail(db);
          String message = cpd.createContractData(request,personnelID,requestID);
          if (!"OK".equalsIgnoreCase(message)) {
            result = false;
            HelpObjs.sendMail(db,"Error in Contract Pricing Update","Error in Updating Values in Catalog Add Request New for: "+requestID+"\n"+message,86030,false);
          }
        }
      }catch (Exception e) {
        e.printStackTrace();
        result = false;
      }
      return result;
    } //end of method


    public void doResponse(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String client=bundle.getString( "DB_CLIENT" );
        HttpSession reqsession = request.getSession();
        String personnelid= reqsession.getAttribute( "PERSONNELID" ) == null ? "" : reqsession.getAttribute( "PERSONNELID" ).toString();

        Vector approvalRoleV = reqsession.getAttribute( "APPROVAL_ROLES" ) == null ? new Vector(0) : (Vector)reqsession.getAttribute( "APPROVAL_ROLES" );
        Cat_Servlet = bundle.getString("CATALOG_SOURCING_SERVLET");
        try{request_id = request.getParameter("request_id").toString();}catch (Exception e1){request_id = "";}
        try{Session= request.getParameter("Session").toString();}catch (Exception e1){Session = "0";}
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
          if (updateContractPricing(request,request_id,approvalRole,personnelid)) {
            buildRequests(out,"",approvalRoleV,approvalRole,personnelid);
          }else {
            buildDetails(out,request_id,"An error occured and an email has been sent to the person concerned. Please Try Again.",approvalRole,personnelid);
            out.close();
            return;
          }
        }else if (Session.equalsIgnoreCase("searchSupplierWin")) {
          String searchText = "";
          try{searchText = request.getParameter("searchText").toString();}catch (Exception e1){searchText = "";}
          String supplierText = "";
          try{supplierText = request.getParameter("supplierT").toString();}catch (Exception e1){supplierText = "";}
          if (supplierText.length() > 0) {
            searchText = supplierText;
          }
          ContractPriceDetail cpd = new ContractPriceDetail(db);
          out.println(cpd.buildSupplierSearchWin(Cat_Servlet,searchText));
        }else if (Session.equalsIgnoreCase("contactNameWin")) {
          String supplierID = "";
          try{supplierID= request.getParameter("supplierID").toString();}catch (Exception e1){supplierID = "";}
          String searchText = "";
          try{searchText = request.getParameter("searchText").toString();}catch (Exception e1){searchText = "";}
          String searchBy = "";
          try{searchBy = request.getParameter("searchBy").toString();}catch (Exception e1){searchBy = "";}
          ContractPriceDetail cpd = new ContractPriceDetail(db);
          out.println(cpd.buildContactNameWin(Cat_Servlet,supplierID,searchText,searchBy));
        }else if (Session.equalsIgnoreCase("newContactWin")) {
          String supplierID = "";
          try{supplierID= request.getParameter("supplierID").toString();}catch (Exception e1){supplierID = "";}
          ContractPriceDetail cpd = new ContractPriceDetail(db);
          out.println(cpd.buildNewContactWin(Cat_Servlet,supplierID,"",request));
        }else if (Session.equalsIgnoreCase("createNewContact")) {
          String supplierID = "";
          try{supplierID= request.getParameter("supplierID").toString();}catch (Exception e1){supplierID = "";}
          ContractPriceDetail cpd = new ContractPriceDetail(db);
          String[] data = cpd.createNewContact(request,personnelid);
          if ("OK".equals(data[0])) {
            //reload data
            String searchText = data[1];
            String searchBy = data[2];
            out.println(cpd.buildContactNameWin(Cat_Servlet,supplierID,searchText,searchBy));
          }else {
            out.println(cpd.buildNewContactWin(Cat_Servlet,supplierID,data[0],request));
          }
        }else if (Session.equalsIgnoreCase("paymentTermWin")) {
          ContractPriceDetail cpd = new ContractPriceDetail(db);
          out.println(cpd.buildPaymentTermWin(personnelid,Cat_Servlet));
        }else if (Session.equalsIgnoreCase("changePaymentTerm")) {
          ContractPriceDetail cpd = new ContractPriceDetail(db);
          String loginID = "";
          try{loginID= request.getParameter("loginID").toString();}catch (Exception e1){loginID = "";}
          String password = "";
          try{password= request.getParameter("password").toString();}catch (Exception e1){password = "";}
          try {
            Password pw = new Password(db, loginID, password);
            personnelid = pw.getUserId();
          }catch (Exception eee) {
            personnelid = "";
          }
          out.println(cpd.buildPaymentTermWin(personnelid,Cat_Servlet));
        }else if (Session.equalsIgnoreCase("searchCarrierWin")) {
          String searchText = "";
          try{searchText= request.getParameter("carrierT").toString();}catch (Exception e1){searchText = "";}
          String requestID = "";
          try{requestID= request.getParameter("requestID").toString();}catch (Exception e1){requestID = "";}
          ContractPriceDetail cpd = new ContractPriceDetail(db);
          out.println(cpd.buildSearchCarrierWin(personnelid,Cat_Servlet,searchText,requestID));
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
      out.println("<FORM METHOD=\"POST\" name=\"catAddDetail\" action=\""+Cat_Servlet+"Session=Update&approvalRole="+URLEncoder.encode(approvalRole)+"\" onsubmit =\"return auditPricingData(this)\">\n");

      int totalrecords = 0;
      CatalogAddApprovalHelper caah = new CatalogAddApprovalHelper(db);
      try {
        Hashtable h = caah.buildRequestDetail(requestID);
        totalrecords = ((Integer)h.get("TOTAL_RECORD")).intValue();
        if (totalrecords > 0) {
          out.println((StringBuffer)h.get("HEADER_INFO"));
          out.println((StringBuffer)h.get("COMPONENT_INFO"));
          out.println((StringBuffer)h.get("ITEM_INFO"));
          //adding baseline and catalog price
          String currency = HelpObjs.getCurrency(db,(String)h.get("FACILITY_ID"));
          //baseline
          out.println("<TR><TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
          out.println("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
          out.println("<B>Baseline Price per Item ("+currency+"):</B>\n");
          out.println("<INPUT type=\"text\" name=\"baseline\" value=\""+(String)h.get("BASELINE_PRICE")+"\" SIZE=\"15\">");
          out.println("&nbsp;&nbsp;&nbsp;&nbsp;\n");
          //Selling price
          out.println("<B>Catalog Selling Price per Item ("+currency+"):</B>\n");
          out.println("<INPUT type=\"text\" name=\"selling\" value=\""+(String)h.get("CATALOG_PRICE")+"\" SIZE=\"15\">");
          out.println("</FONT>\n");
          out.println("</TD></TR>\n");
          out.println("</TABLE>\n");
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
      ContractPriceDetail cpd = new ContractPriceDetail(db);
      out.println("<INPUT TYPE=\"hidden\" NAME=\"companyID\" VALUE=\""+client.toLowerCase()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"requestID\" VALUE=\""+requestID+"\">\n");
      out.println(cpd.buildContractPriceInfo(requestID));
      out.println("</TABLE>\n");
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
      if ("Seagate".equalsIgnoreCase(client)) {
        printHTMLHeader( "Seagate Cataloging",out );
      }else {
        printHTMLHeader( "Customer Cataloging",out );
      }

      MsgH.append("</HEAD>\n");

      if (SusMessage.length() >1) {
        MsgH.append("<BODY onload =\"showMsg()\">\n");
      }else {
        MsgH.append("<BODY>\n");
      }
      MsgH.append("<TABLE BORDER=0 WIDTH=100% >\n");
      if ("Seagate".equalsIgnoreCase(client)) {
        MsgH.append( "<TR><TD WIDTH=\"100%\" ALIGN=\"CENTER\"><B>Seagate Cataloging<B></TD></TR>\n" );
      }else {
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
      if ("Seagate".equalsIgnoreCase(client)) {
        MsgH.append( "<CENTER><A HREF=\"" + Cat_Servlet + "Session=0\">Show All Requests Needing CSM Part Number</A></CENTER>" );
      }else {
        MsgH.append( "<CENTER><A HREF=\"" + Cat_Servlet + "Session=0\">Show All Requests Needing Part Number</A></CENTER>" );
      }
      MsgH.append("</FONT>\n</BODY>\n</HTML>\n");
      out.println(MsgH);
    } //end of method

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
