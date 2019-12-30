// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 4/27/2007 1:42:51 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   DeliveryScheduleAdmin.java

package radian.web.delivery;

import java.io.*;
import java.sql.ResultSet;
import java.util.Hashtable;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DeliveryScheduleAdmin {

  private String dbClient = "";
  private String deliverySchAdminServlet = "";
  private TcmISDBModel db = null;
  private ServerResourceBundle bundle = null;
  boolean showApprovalButton;
  boolean needsCsr;
  boolean needsExpedite;
  final String DELETED = " BGCOLOR=\"#FFCCCC\"";
  final String ADDED = " BGCOLOR=\"#99FF99\"";
  final String CHANGED = " BGCOLOR=\"#FFFFCC\"";
  final String NO_CHANGE = "";

  public DeliveryScheduleAdmin(ServerResourceBundle b, TcmISDBModel d) {
    bundle = b;
    db = d;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String errMsg = "";
    boolean approved = false;
    String action = "";
    String msg = "";
    String logon = "";
    String password = "";
    needsCsr = true;
    needsExpedite = true;
    Hashtable headerInfo = new Hashtable();
    Hashtable ApprovData = new Hashtable();
    String approvalResult = "";
    showApprovalButton = true;

    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
    StringBuffer MsgMain = new StringBuffer();

    String scategory = "";
    String orderby = "";
    String prnumbner11 = "";
    String lineitem11 = "";
    String TotalQty = "";

    dbClient = bundle.getString("DB_CLIENT");
    if ("Ray".equalsIgnoreCase(dbClient)) {
      deliverySchAdminServlet = "radian.web.delivery.ray.RayDeliveryScheduleAdmin";
    } else if ("BAE".equalsIgnoreCase(dbClient)) {
      deliverySchAdminServlet = "radian.web.delivery.bae.BAEDeliveryScheduleAdmin";
    } else if ("DRS".equalsIgnoreCase(dbClient)) {
      deliverySchAdminServlet = "radian.web.delivery.drs.DRSDeliveryScheduleAdmin";
    } else if ("IAI".equalsIgnoreCase(dbClient)) {
      deliverySchAdminServlet = "radian.web.delivery.iai.IAIDeliveryScheduleAdmin";
    } else if ("L3".equalsIgnoreCase(dbClient)) {
      deliverySchAdminServlet = "radian.web.delivery.l3.L3DeliveryScheduleAdmin";
    } else if ("UTC".equalsIgnoreCase(dbClient)) {
      deliverySchAdminServlet = "radian.web.delivery.utc.UTCDeliveryScheduleAdmin";
    }

    try {
      scategory = request.getParameter("Category").toString();
    } catch (Exception e) {
      scategory = "No";
    }
    try {
      orderby = request.getParameter("Orderby").toString();
    } catch (Exception e) {
      orderby = "No";
    }
    try {
      prnumbner11 = request.getParameter("prnumber").toString();
    } catch (Exception e) {
      orderby = "No";
    }
    try {
      lineitem11 = request.getParameter("lineItem").toString();
    } catch (Exception e) {
      orderby = "No";
    }
    try {
      TotalQty = request.getParameter("TotalQty").toString();
    } catch (Exception e) {
      TotalQty = " ";
    }
    try {
      action = request.getParameter("action").toString();
    } catch (Exception e) {
      action = " ";
    }

    try {
      String errMsg1 = "";
      if (action.equalsIgnoreCase("approve")) {
        logon = request.getParameter("logon");
        password = request.getParameter("pwd");

        ApprovData = doApproval(logon, password, prnumbner11, lineitem11);
        errMsg1 = ApprovData.get("ErrorMsg").toString();
        headerInfo = getHeaderInfo(db, prnumbner11, lineitem11);
        int count = Integer.parseInt(headerInfo.get("COUNT").toString());
        MsgMain.append(buildPageHeader(headerInfo, errMsg1));
        if (count == 0) {
          out.println(MsgMain);
          out.println("</BODY></HTML>\n");
          out.flush();
          out.close();
          return;
        }
        MsgMain.append(buildBody(headerInfo, ApprovData));
        MsgMain.append(buildPageFooter(prnumbner11, lineitem11));
      } else
      if (action.equalsIgnoreCase("view")) {
        headerInfo = getHeaderInfo(db, prnumbner11, lineitem11);
        int count = Integer.parseInt(headerInfo.get("COUNT").toString());
        MsgMain.append(buildPageHeader(headerInfo, " "));
        if (count == 0) {
          out.println(MsgMain);
          out.println("</BODY></HTML>\n");
          out.flush();
          out.close();
          return;
        }
        ApprovData.put("ApprovalResult", "None");
        ApprovData.put("ErrorMsg", "None");
        MsgMain.append(buildBody(headerInfo, ApprovData));
        MsgMain.append(buildPageFooter(prnumbner11, lineitem11));
      } else
      if (action.equalsIgnoreCase("search")) {
        out.println(printSearchPage(scategory, orderby));
        out.println(printSearchresults(scategory, orderby));
        out.println("</BODY></HTML>\n");
      } else
      if (action.equalsIgnoreCase("finalSch")) {
        out.println(buildHeader());
        out.println(printFinalSch(prnumbner11, lineitem11, TotalQty));
        out.println("</BODY></HTML>\n");
      } else {
        out.println(printSearchPage(scategory, orderby));
        out.println("</BODY></HTML>\n");
      }
      out.println(MsgMain);
    } catch (Exception all) {
      all.printStackTrace();
      out.println("An Error occured please go back and try again.\n");
      out.println("<BR><FONT FACE=\"Arial\" SIZE=\"2\"><A HREF=\"/tcmIS/servlet/" + deliverySchAdminServlet + "?action=search&Category=1\">Show All Delivery Schedulde Changes Needing Review</A></FONT></CENTER>");
      out.println("</BODY></HTML>\n");
    }
    headerInfo = null;
    ApprovData = null;
    out.flush();
    out.close();
  }

  private StringBuffer buildHeader() {
    StringBuffer Msg = new StringBuffer();
    Msg.append("<HTML><HEAD>\n");
    Msg.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
    Msg.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
    Msg.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
    Msg.append("<title>Delivery Schedule Change Approval</title>\n");
    Msg.append("<BODY>\n");
    return Msg;
  }

  protected StringBuffer printFinalSch(String prnumber2, String lineitem2, String TotalQuantity) {
    StringBuffer psr = new StringBuffer();
    //psr.append(String.valueOf(String.valueOf( (new StringBuffer("Following is the schedule for PR Number: <B>")).append(prnumber2).append("</B> and Line Item: <B>").append(lineitem2).append("</B>  Total = <B>").append(TotalQuantity).append("</B><BR><BR>\n"))));
    psr.append("Following is the schedule for PR Number: <B>");
    psr.append(prnumber2);
    psr.append("</B> and Line Item: <B>");
    psr.append(lineitem2);
    psr.append("</B>  Total = <B>");
    psr.append(TotalQuantity);
    psr.append("</B><BR><BR>\n");
    psr.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"250\" >\n");
    psr.append("<TR>\n");
    psr.append("<TD BGCOLOR=\"#B0BFD0\" WIDTH=\"160\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Date To Deliver</B></FONT></TD>\n");
    psr.append("<TD BGCOLOR=\"#B0BFD0\" WIDTH=\"90\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Quantity</B></FONT></TD>\n");
    psr.append("</TR>\n");
    int totalrecords = 0;
    int count = 0;
    //String query3 = String.valueOf(String.valueOf( (new StringBuffer("select PR_NUMBER,LINE_ITEM,DATE_TO_DELIVER,QTY,to_char(date_to_deliver,'dd Month yyyy') datetodelivery  from delivery_schedule where pr_number=")).append(prnumber2).append(" and line_item=").append(lineitem2).append(
    //    " order by DATE_TO_DELIVER")));
    String query3 = "select PR_NUMBER,LINE_ITEM,DATE_TO_DELIVER,QTY,to_char(date_to_deliver,'dd Month yyyy') datetodelivery"+
                    " from delivery_schedule where pr_number="+prnumber2+" and line_item="+lineitem2+
                    " order by DATE_TO_DELIVER";
    DBResultSet dbrs2 = null;
    ResultSet rs2 = null;
    try {
      dbrs2 = db.doQuery(query3);
      rs2 = dbrs2.getResultSet();
      //System.out.print("Finished The Querry\n ");
      for (; rs2.next(); psr.append("</TR>\n")) {
        totalrecords++;
        count++;
        String Color = " ";
        if (count % 2 == 0) {
          Color = "bgcolor=\"#E6E8FA\"";
        } else {
          Color = "bgcolor=\"#fcfcfc\"";
        }
        psr.append("<TR>\n");
        psr.append("<TD ");
        psr.append(Color);
        psr.append(" WIDTH=\"160\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
        psr.append(BothHelpObjs.makeSpaceFromNull(rs2.getString("datetodelivery")));
        psr.append("</FONT></TD>\n");
        psr.append("<TD ");
        psr.append(Color);
        psr.append(" WIDTH=\"90\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
        psr.append(BothHelpObjs.makeSpaceFromNull(rs2.getString("QTY")));
        psr.append("</FONT></TD>\n");
      }

    } catch (Exception e) {
      e.printStackTrace();
      psr.append("<TR><TD>An Error Occured While Querying the Databse</TD></TR>\n");
      psr.append("</TABLE>\n");
      StringBuffer stringbuffer = psr;
      return stringbuffer;
    } finally {
      dbrs2.close();
      if (totalrecords == 0) {
        psr.append("<TR><TD>No Records Found</TD></TR>\n");
      }
    }
    psr.append("</TABLE>\n");
    return psr;
  }

  protected StringBuffer printSearchresults(String category, String OrderBy) {
    StringBuffer psr = new StringBuffer();
    psr.append("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n");
    psr.append("<TR>\n");
    psr.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>ITEM ID</B></FONT></TD>\n");
    psr.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>PR Number</B></FONT></TD>\n");
    psr.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Line Item</B></FONT></TD>\n");
    psr.append("</TR>\n");
    int totalrecords = 0;
    int count = 0;
    String query2 = "select distinct dsc.PR_NUMBER,dsc.LINE_ITEM, rli.sales_order,rli.item_id ";
    query2 = String.valueOf(String.valueOf(query2)).concat("from delivery_schedule_change dsc, request_line_item rli ");
    query2 = String.valueOf(String.valueOf(query2)).concat("where dsc.pr_number = rli.pr_number and dsc.line_item = rli.line_item and dsc.date_to_deliver > sysdate-120 and ");
    if (category.equalsIgnoreCase("2")) {
      query2 = String.valueOf(String.valueOf(query2)).concat("(dsc.expedite_approval is null or lower(dsc.expedite_approval) != 'y') ");
    } else {
      query2 = String.valueOf(String.valueOf(query2)).concat("(dsc.csr_approval is null or lower(dsc.csr_approval) != 'y') ");
    }
    if (OrderBy.equalsIgnoreCase("2")) {
      query2 = String.valueOf(String.valueOf(query2)).concat(" order by dsc.pr_number,dsc.line_item,rli.item_id ");
    } else {
      query2 = String.valueOf(String.valueOf(query2)).concat(" order by rli.item_id,dsc.pr_number,dsc.line_item ");
    }
    DBResultSet dbrs2 = null;
    ResultSet rs2 = null;
    try {
      dbrs2 = db.doQuery(query2);
      rs2 = dbrs2.getResultSet();
      //System.out.print("Finished The Querry\n ");
      for (; rs2.next(); psr.append("</TR>\n")) {
        totalrecords++;
        if (++count % 10 == 0) {
          psr.append("<TR>\n");
          psr.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>ITEM ID</B></FONT></TD>\n");
          psr.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>PR Number</B></FONT></TD>\n");
          psr.append("<TD BGCOLOR=\"#B0BFD0\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Line Item</B></FONT></TD>\n");
          psr.append("</TR>\n");
        }
        String Color = " ";
        if (count % 2 == 0) {
          Color = "bgcolor=\"#E6E8FA\"";
        } else {
          Color = "bgcolor=\"#fcfcfc\"";
        }
        psr.append("<TR>\n");
        psr.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
        psr.append("<A HREF=\"/tcmIS/servlet/" + deliverySchAdminServlet +
                   "?action=view&prnumber="+BothHelpObjs.makeSpaceFromNull(rs2.getString("pr_number"))+"&lineItem="+
                   BothHelpObjs.makeSpaceFromNull(rs2.getString("line_item"))+"\">");
        psr.append(""+BothHelpObjs.makeSpaceFromNull(rs2.getString("ITEM_ID"))+"</A>");
        psr.append("</FONT></TD>\n");
        psr.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
        psr.append(BothHelpObjs.makeSpaceFromNull(rs2.getString("PR_NUMBER")));
        psr.append("</FONT></TD>\n");
        psr.append("<TD "+Color+" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
        psr.append(BothHelpObjs.makeSpaceFromNull(rs2.getString("LINE_ITEM")));
        psr.append("</FONT></TD>\n");
      }

    } catch (Exception e) {
      e.printStackTrace();
      psr.append("<TR><TD>An Error Occured While Querying the Databse</TD></TR>\n");
      psr.append("</TABLE>\n");
      StringBuffer stringbuffer = psr;
      return stringbuffer;
    } finally {
      dbrs2.close();
      if (totalrecords == 0) {
        psr.append("<TR><TD>No Records Found</TD></TR>\n");
      }
    }
    psr.append("</TABLE>\n");
    return psr;
  }

  protected StringBuffer printSearchPage(String category, String OrderBy) {
    StringBuffer psp = new StringBuffer();
    psp.append(buildHeader());
    psp.append("<TABLE BORDER=0 WIDTH=100%>\n");
    psp.append("<TR VALIGN=\"TOP\">\n");
    psp.append("<TD WIDTH=\"200\">\n");
    psp.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
    psp.append("</TD>\n");
    psp.append("<TD WIDTH=\"525\" ALIGN=\"right\">\n");
    psp.append("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
    psp.append("</TD>\n");
    psp.append("</TR>\n");
    psp.append("</Table>\n");
    psp.append("<TABLE WIDTH=\"100%\" BORDER=\"0\">\n");
    psp.append("<TR><TD VALIGN=\"middle\" BGCOLOR=#000066 VALIGN=\"MIDDLE\" WIDTH=\"600\" HEIGHT=\"20\">\n");
    psp.append("<B><FONT FACE=\"Arial\" COLOR=\"#FFFFFF\"><I>tcm</I>IS Delivery Schedule Change</FONT></B>\n");
    psp.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    psp.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    psp.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    psp.append("</TD>\n");
    psp.append("</TR>\n");
    psp.append("</TABLE>\n");
    psp.append("<FORM method=\"POST\" NAME=\"receiving\" ACTION=\"/tcmIS/servlet/" + deliverySchAdminServlet + "?\">\n");
    psp.append("<input type=\"hidden\" name=\"action\" value=\"search\">\n");
    psp.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\">\n");
    psp.append("<TR VALIGN=\"MIDDLE\">\n");
    psp.append("<TD WIDTH=\"15%\">\n");
    psp.append("<INPUT TYPE=\"submit\" VALUE=\"List\" NAME=\"ListButton\">\n");
    psp.append("</TD>\n");
    psp.append("<TD WIDTH=\"5%\">\n");
    psp.append("&nbsp;\n");
    psp.append("</TD>\n");
    psp.append("<TD WIDTH=\"20%\" ALIGN=\"RIGHT\">\n");
    psp.append("<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Changes That Need : </B></FONT>&nbsp;\n");
    psp.append("</TD>\n");
    psp.append("<TD WIDTH=\"25%\">\n");
    psp.append("<SELECT NAME=\"Category\" size=\"1\">\n");
    if (category.equalsIgnoreCase("2")) {
      psp.append("<option value=\"1\">CSR Review</option>\n");
      psp.append("<option selected value=\"2\">Buyer Review</option>\n");
    } else {
      psp.append("<option selected value=\"1\">CSR Review</option>\n");
      psp.append("<option value=\"2\">Buyer Review</option>\n");
    }
    psp.append("</SELECT>\n");
    psp.append("</TD>\n");
    psp.append("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\">\n");
    psp.append("<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Order By : </B></FONT>&nbsp;\n");
    psp.append("</TD>\n");
    psp.append("<TD WIDTH=\"25%\">\n");
    psp.append("<SELECT NAME=\"Orderby\" size=\"1\">\n");
    if (OrderBy.equalsIgnoreCase("2")) {
      psp.append("<option value=\"1\">Item/PR Number/Line</option>\n");
      psp.append("<option selected value=\"2\">PR Number/Line/Item</option>\n");
    } else {
      psp.append("<option selected value=\"1\">Item/PR Number/Line</option>\n");
      psp.append("<option value=\"2\">PR Number/Line/Item</option>\n");
    }
    psp.append("</SELECT>\n");
    psp.append("</TD>\n");
    psp.append("</TR>\n</TABLE>\n");
    psp.append("</FORM>\n");
    return psp;
  }

  protected StringBuffer printHTMLError(String Errortxt) {
    StringBuffer MsgER = new StringBuffer();
    MsgER.append("<TABLE><TR>\n");
    MsgER.append("<TD width=\"5%\">\n");
    MsgER.append("</TD>\n");
    MsgER.append("<TD width=\"16%\"><FONT SIZE=\"2\" FACE=\"Arial\">"+Errortxt+"<BR>\n");
    MsgER.append("Please go back and try again.</FONT></TD>\n");
    MsgER.append("</TR></TABLE>\n");
    MsgER.append("</BODY></HTML>\n");
    return MsgER;
  }

  protected Hashtable getHeaderInfo(TcmISDBModel db1, String MRNum, String MRLine) {
    Hashtable headerInfoput = new Hashtable(14);
    try {
      String query = "select PR_NUMBER,LINE_ITEM,ITEM_ID,FAC_PART_NO,QUANTITY,ITEM_DESC,PACKAGING,FACILITY_NAME,APPLICATION_DISPLAY,FULL_NAME,PHONE,EMAIL"+
                     " from schedule_change_header_view where pr_number = "+MRNum+" and line_item = '"+MRLine+"'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      int RowCount = 0;
      try {
        dbrs = db.doQuery(query);
        for (rs = dbrs.getResultSet(); rs.next(); headerInfoput.put("QUANTITY", BothHelpObjs.makeBlankFromNull(rs.getString("QUANTITY")))) {
          RowCount++;
          headerInfoput.put("FAC", BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_NAME")));
          headerInfoput.put("WA", BothHelpObjs.makeBlankFromNull(rs.getString("APPLICATION_DISPLAY")));
          headerInfoput.put("PR_NUMBER", MRNum);
          headerInfoput.put("LINE_ITEM", MRLine);
          headerInfoput.put("MR_LINE", MRNum+"-"+MRLine);
          headerInfoput.put("REQ", BothHelpObjs.makeBlankFromNull(rs.getString("FULL_NAME")));
          headerInfoput.put("PHONE", BothHelpObjs.makeBlankFromNull(rs.getString("PHONE")));
          headerInfoput.put("EMAIL", BothHelpObjs.makeBlankFromNull(rs.getString("EMAIL")));
          headerInfoput.put("ITEM", BothHelpObjs.makeBlankFromNull(rs.getString("ITEM_ID")));
          headerInfoput.put("PART", BothHelpObjs.makeBlankFromNull(rs.getString("FAC_PART_NO")));
          headerInfoput.put("DESC", BothHelpObjs.makeBlankFromNull(rs.getString("ITEM_DESC")));
          headerInfoput.put("PACKAGING", BothHelpObjs.makeBlankFromNull(rs.getString("PACKAGING")));
        }

      } catch (Exception e) {
        e.printStackTrace();
        dbrs.close();
      }
      headerInfoput.put("COUNT",(new Integer(RowCount)).toString());
      needsCsr = needsCsrApproval(MRNum, MRLine);
      needsExpedite = needsExpediteApproval(MRNum, MRLine);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return headerInfoput;
  }

  protected Hashtable doApproval(String logonname, String password1, String MRNum, String MRLine) {
    Hashtable ApprovalData = new Hashtable();
    String approvalResult = "";
    String errMsg = "";
    try {
      Password pw = new Password(db, logonname, password1);
      String userId = "";
      userId = pw.getUserId();
      if (Integer.parseInt(pw.getAuthStatus()) != 0 && Integer.parseInt(pw.getAuthStatus()) != 99) {
        approvalResult = String.valueOf(String.valueOf(approvalResult)).concat("Invalid Logon ID/Password combination.\n");
        errMsg = String.valueOf(String.valueOf(errMsg)).concat("Invalid Logon ID/Password combination.\n");
        ApprovalData.put("ApprovalResult", approvalResult);
        ApprovalData.put("ErrorMsg", errMsg);
        Hashtable hashtable1 = ApprovalData;
        return hashtable1;
      }
      Integer pid = new Integer(pw.getUserId());
      String query = "select rli.PR_NUMBER,rli.LINE_ITEM,rli.ITEM_ID,rli.APPLICATION,rli.FAC_PART_NO,pr.REQUESTOR,pr.FACILITY_ID,jdei.ITEM_DESC,f.FACILITY_NAME,"+
                     "fla.APPLICATION ||'-'|| fla.APPLICATION_DESC  APP_DESC, p.last_name || ' ' || p.first_name FULL_NAME, p.PHONE, p.EMAIL,f.PREFERRED_WAREHOUSE"+
                     " from purchase_request pr, request_line_item  rli, item jdei, facility f, fac_loc_app fla, personnel p"+
                     " where  rli.pr_number = "+MRNum+" and rli.line_item = '"+MRLine+"' and rli.pr_number = pr.pr_number and"+
                     " rli.item_id = jdei.item_id and pr.facility_id = f.facility_id and pr.facility_id = fla.facility_id and"+
                     " rli.APPLICATION = fla.APPLICATION and p.personnel_id = pr.REQUESTOR";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      int prnumber1 = 0;
      int line_num1 = 0;
      int test_numebr = 0;
      String facilityIdofSo = "";
      String preffered_warehouse = "";
      try {
        dbrs = db.doQuery(query);
        for (rs = dbrs.getResultSet(); rs.next(); ) {
          test_numebr++;
          prnumber1 = BothHelpObjs.makeZeroFromNull(rs.getString("PR_NUMBER"));
          line_num1 = BothHelpObjs.makeZeroFromNull(rs.getString("LINE_ITEM"));
          facilityIdofSo = BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID"));
          preffered_warehouse = BothHelpObjs.makeBlankFromNull(rs.getString("PREFERRED_WAREHOUSE"));
        }

      } catch (Exception e) {
        e.printStackTrace();
        dbrs.close();
      }
      if (test_numebr == 0) {
        approvalResult = String.valueOf(String.valueOf(approvalResult)).concat("Invalid MR-Line.\n");
        errMsg = String.valueOf(String.valueOf(errMsg)).concat("Invalid MR-Line.\n");
        ApprovalData.put("ApprovalResult", approvalResult);
        ApprovalData.put("ErrorMsg", errMsg);
        Hashtable hashtable2 = ApprovalData;
        return hashtable2;
      }
      boolean isExpediter = isMember(pid.intValue(), preffered_warehouse, "Buyer") || isMember(pid.intValue(), preffered_warehouse, "RadianPurchaser");
      boolean isCSR = isMember(pid.intValue(), preffered_warehouse, "CSR");
      if (!isExpediter && !isCSR) {
        approvalResult = String.valueOf(String.valueOf(approvalResult)).concat("You are not authorized to approve this schedule change.\n");
        errMsg = String.valueOf(String.valueOf(errMsg)).concat("You are not authorized to approve this schedule change.\n");
        ApprovalData.put("ApprovalResult", approvalResult);
        ApprovalData.put("ErrorMsg", errMsg);
        Hashtable hashtable3 = ApprovalData;
        return hashtable3;
      }
      if (isExpediter && isCSR) {
        expediterApprove(prnumber1, line_num1);
        csrApprove(prnumber1, line_num1);
        approvalResult = "Buyer / CSR approval was successful.\n";
      } else
      if (isExpediter) {
        expediterApprove(prnumber1, line_num1);
        approvalResult = "Buyer approval was successful.\n";
      } else
      if (isCSR) {
        csrApprove(prnumber1, line_num1);
        approvalResult = "CSR approval was successful.\n";
      }
      showApprovalButton = false;
    } catch (Exception e) {
      e.printStackTrace();
      approvalResult = String.valueOf(String.valueOf(approvalResult)).concat("Error processing approval.\n");
      errMsg = String.valueOf(String.valueOf(errMsg)).concat("Error processing approval.\n");
      ApprovalData.put("ApprovalResult", approvalResult);
      ApprovalData.put("ErrorMsg", errMsg);
      Hashtable hashtable = ApprovalData;
      return hashtable;
    }
    ApprovalData.put("ApprovalResult", approvalResult);
    ApprovalData.put("ErrorMsg", errMsg);
    return ApprovalData;
  }

  protected boolean isMember(int pid, String facId, String groupID) throws Exception {
    try {
      String q = "select count(*) from user_group_member where personnel_id = "+pid+" and user_group_id = '"+groupID+"'"+
                 " and facility_id in ('"+facId+"','All')";
      boolean flag = DbHelpers.countQuery(db, q) > 0;
      return flag;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public boolean needsCsrApproval(String prNum, String lineNum) throws Exception {
    try {
      String query = "select count(*) from delivery_schedule_change where (csr_approval is null or lower(csr_approval) != 'y') and"+
                     " pr_number = "+prNum+" and line_item = '"+lineNum+"'";
      boolean flag = DbHelpers.countQuery(db, query) > 0;
      return flag;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public boolean needsExpediteApproval(String prNum, String lineNum) throws Exception {
    try {
      String query = "select count(*) from delivery_schedule_change where (expedite_approval is null or lower(expedite_approval) != 'y') and"+
                     " pr_number = "+prNum+" and line_item = '"+lineNum+"'";
      boolean flag = DbHelpers.countQuery(db, query) > 0;
      return flag;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public void expediterApprove(int prNum, int lineNum) throws Exception {
    try {
      String query = "update delivery_schedule_change set expedite_approval = 'y'"+
                     " where pr_number = "+prNum+" and line_item = '"+lineNum+"'";
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public void csrApprove(int prNum, int lineNum) throws Exception {
    try {
      String query = "update delivery_schedule_change set csr_approval = 'y'"+
                     " where pr_number = "+prNum+" and line_item = '"+lineNum+"'";
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  protected StringBuffer buildPageHeader(Hashtable AllData, String ErrorMsg) {
    StringBuffer head = new StringBuffer();
    head.append("<HTML><HEAD><TITLE>Delivery Schedule Change</TITLE>\n");
    head.append("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n");
    head.append("<!-- \n");
    head.append("function getFinalSch(prnumber,lineitem,quantity) \n");
    head.append("{\n");
    head.append(" loc = \"/tcmIS/servlet/" + deliverySchAdminServlet + "?action=finalSch&prnumber=\";\n");
    head.append(" loc = loc + prnumber;\n");
    head.append(" loc = loc + \"&lineItem=\" + lineitem;\n");
    head.append(" loc = loc + \"&TotalQty=\" + quantity;\n");
    head.append("openwin2(loc);\n");
    head.append("}\n");
    head.append("function openwin2 (str)\n");
    head.append("        {\n");
    head.append("    MSDS_Problem = window.open(str, \"MSDS_Problem\",\n");
    head.append("        \"toolbar=no,location=no,directories=no,status=no\" +\n");
    head.append("        \",menubar=no,scrollbars=yes,resizable=no,\" +\n");
    head.append("        \",width=300,height=400,top=200,left=200\");\n");
    head.append("        }\n");
    head.append("//-->     \n");
    head.append("</SCRIPT></HEAD>  \n");
    head.append("<BODY><FONT FACE=\"Arial\">\n");
    head.append("<TABLE BORDER=0 WIDTH=100%>\n");
    head.append("<TR VALIGN=\"TOP\">\n");
    head.append("<TD WIDTH=\"200\">\n");
    head.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
    head.append("</TD>\n");
    head.append("<TD WIDTH=\"525\" ALIGN=\"right\">\n");
    head.append("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
    head.append("</TD>\n");
    head.append("</TR>\n");
    head.append("</Table>\n");
    head.append("<TABLE WIDTH=\"100%\" BORDER=\"0\">\n");
    head.append("<TR><TD VALIGN=\"middle\" BGCOLOR=#000066 VALIGN=\"MIDDLE\" WIDTH=\"600\" HEIGHT=\"20\">\n");
    head.append("<B><FONT FACE=\"Arial\" COLOR=\"#FFFFFF\"><I>tcm</I>IS Delivery Schedule Change</FONT></B>\n");
    head.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    head.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    head.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    head.append("</TD>\n");
    head.append("</TR>\n");
    head.append("</TABLE>\n");
    head.append("<CENTER><BR><FONT FACE=\"Arial\" SIZE=\"4\"><B>");
    head.append("None".equalsIgnoreCase(ErrorMsg) ? "" : ErrorMsg);
    head.append("</B></FONT>\n");
    head.append("<BR><FONT FACE=\"Arial\" SIZE=\"2\"><A HREF=\"" + deliverySchAdminServlet + "?action=search&Category=1\">Show All Delivery Schedule Changes Needing Review</A></FONT></CENTER>");
    try {
      head.append("<HR WIDTH=\"100%\">\n");
      head.append("<TABLE WIDTH=\"100%\" >\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">MR-Line:</td><td><FONT SIZE=\"2\" FACE=\"Arial\">"+AllData.get("MR_LINE").toString()+"</FONT></td>\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Part:</td><td><FONT SIZE=\"2\" FACE=\"Arial\">"+AllData.get("PART").toString()+"</FONT></td>\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Requestor:</td><td><FONT SIZE=\"2\" FACE=\"Arial\">"+AllData.get("REQ").toString()+"</FONT></td>\n");
      head.append("<tr>\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Facility:</td><td><FONT SIZE=\"2\" FACE=\"Arial\">"+AllData.get("FAC").toString()+"</FONT></td>\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Item:</td><td><FONT SIZE=\"2\" FACE=\"Arial\">"+AllData.get("ITEM").toString()+"</FONT></td>\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Email:</td><td><FONT SIZE=\"2\" FACE=\"Arial\">"+AllData.get("EMAIL").toString()+"</FONT></td>\n");
      head.append("</tr>\n");
      head.append("<tr>\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Work Area:</td><td><FONT SIZE=\"2\" FACE=\"Arial\">"+AllData.get("WA").toString()+"</FONT></td>\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Packaging:</td><td><FONT SIZE=\"2\" FACE=\"Arial\">"+AllData.get("PACKAGING").toString()+"</FONT></td>\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Phone:</td><td><FONT SIZE=\"2\" FACE=\"Arial\">"+AllData.get("PHONE").toString()+"</FONT></td>\n");
      head.append("</tr>\n");
      head.append("<tr>\n");
      head.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Item Description:</FONT></td>\n");
      head.append("<td COLSPAN=\"5\"><FONT FACE=\"Arial\" SIZE=\"2\">"+AllData.get("DESC").toString()+"</FONT></td>\n");
      head.append("</tr>\n");
      head.append("</TABLE>\n");
    } catch (Exception enn) {
      enn.printStackTrace();
      head.append("<b><FONT FACE=\"Arial\" SIZE=\"3\">No Data Available for this MR-Line.</FONT></b>\n");
    } finally {
      AllData = null;
    }
    return head;
  }

  protected StringBuffer buildBody(Hashtable NeededData, Hashtable ApprovalData1) {
    StringBuffer body = new StringBuffer();
    String errMsg2 = ApprovalData1.get("ErrorMsg").toString();
    String approvalResult = ApprovalData1.get("ApprovalResult").toString();
    String prnumber = NeededData.get("PR_NUMBER").toString();
    String lineitem = NeededData.get("LINE_ITEM").toString();
    String totalquantity = NeededData.get("QUANTITY").toString();
    body.append("<HR WIDTH=\"100%\">\n");
    body.append("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
    if (needsCsr && needsExpedite) {
      body.append("<B>This schedule needs Buyer and CSR review.</B>\n");
    } else
    if (needsCsr) {
      body.append("<B>This schedule needs CSR review.</B>\n");
    } else
    if (needsExpedite) {
      body.append("<B>This schedule needs Buyer review.</B>\n");
    } else
    if (errMsg2.equalsIgnoreCase("None")) {
      body.append("<B></B>\n");
    }
    body.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BUTTON name=\"showfinalschedulde\" value=\"testing\" OnClick=\"getFinalSch('"+prnumber+"','"+lineitem+"','"+totalquantity+"')\" type=\"button\">Show Resulting Schedule</BUTTON>\n\n");
    body.append("<BR><BR><CENTER> The following table shows the changed schedule.</CENTER>\n");
    body.append("</FONT>\n");
    body.append("<CENTER><TABLE BORDER=\"0\" CELLPADDING=\"2\" WIDTH=\"50%\">\n");
    body.append("<tr>\n");
    body.append("<th ALIGN=\"LEFT\" BGCOLOR=\"#B0BFD0\"><FONT FACE=\"Arial\" SIZE=\"2\">Date</FONT></th>\n");
    body.append("<th ALIGN=\"RIGHT\" BGCOLOR=\"#B0BFD0\"><FONT FACE=\"Arial\" SIZE=\"2\">Old Qty</FONT></th>\n");
    body.append("<th ALIGN=\"RIGHT\" NOWRAP BGCOLOR=\"#B0BFD0\"><FONT FACE=\"Arial\" SIZE=\"2\">New Qty</FONT></th>\n");
    body.append("</tr>\n");
    String query1 = "select distinct x.pr_number,x.line_item,to_char(x.date_to_deliver,'dd Month yyyy') datetodelivery,x.date_to_deliver,yy.original_qty, zz.revised_qty,x.expedite_approval, x.csr_approval \n";
    query1 = String.valueOf(String.valueOf(query1)).concat("from delivery_schedule_change x, \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("(select xx.pr_number, xx.line_item, xx.original_qty, xx.date_to_deliver from delivery_schedule_change xx, \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("(select pr_number,line_item,date_to_deliver,min(updated_date) updated_date from delivery_schedule_change where \n");
    query1 = query1+" pr_number = "+prnumber+" and line_item = "+lineitem+" \n";
    query1 = String.valueOf(String.valueOf(query1)).concat("group by pr_number,line_item,date_to_deliver) y \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("where xx.pr_number=y.pr_number and xx.line_item=y.line_item and xx.updated_date=y.updated_date and xx.date_to_deliver=y.date_to_deliver) yy, \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("(select xx.pr_number, xx.line_item, xx.revised_qty, xx.date_to_deliver from delivery_schedule_change xx, \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("(select pr_number,line_item,date_to_deliver,max(updated_date) updated_date from delivery_schedule_change where \n");
    query1 = query1+" pr_number = "+prnumber+" and line_item = "+lineitem+" \n";
    query1 = String.valueOf(String.valueOf(query1)).concat("      group by pr_number,line_item, date_to_deliver) z \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("where xx.pr_number=z.pr_number and xx.line_item=z.line_item and xx.updated_date=z.updated_date and xx.date_to_deliver=z.date_to_deliver) zz \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("where \n");
    query1 = String.valueOf(String.valueOf(query1)).concat(" x.pr_number = yy.pr_number and x.line_item = yy.line_item \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("and x.pr_number = zz.pr_number and x.line_item = zz.line_item \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("and x.date_to_deliver=yy.date_to_deliver \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("and x.date_to_deliver=zz.date_to_deliver \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("and yy.original_qty <> zz.revised_qty \n");
    query1 = String.valueOf(String.valueOf(query1)).concat("order by  x.date_to_deliver");
    DBResultSet dbrs1 = null;
    ResultSet rs1 = null;
    int newQ = 0;
    int oldQ = 0;
    int rowCount1 = 0;
    try {
      dbrs1 = db.doQuery(query1);
      rs1 = dbrs1.getResultSet();
      do {
        if (!rs1.next()) {
          break;
        }
        if (!needsCsr && !needsExpedite) {
          body.append("<tr>\n");
          body.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\" COLSPAN=\"2\">No Changes to Review.</FONT></td>\n");
          body.append("</tr>\n");
          break;
        }
        newQ = BothHelpObjs.makeZeroFromNull(rs1.getString("revised_qty"));
        oldQ = BothHelpObjs.makeZeroFromNull(rs1.getString("original_qty"));
        rowCount1++;
        if (newQ != 0 || oldQ != 0) {
          String myColor;
          if (oldQ == newQ) {
            myColor = " BGCOLOR=\"#dddddd\"";
          } else
          if (newQ == 0) {
            myColor = " BGCOLOR=\"#FFCCCC\"";
          } else
          if (oldQ == 0) {
            myColor = " BGCOLOR=\"#99FF99\"";
          } else {
            myColor = " BGCOLOR=\"#FFFFCC\"";
          }
          body.append("<tr>\n");
          body.append("<td "+myColor+"><FONT FACE=\"Arial\" SIZE=\"2\">"+BothHelpObjs.makeBlankFromNull(rs1.getString("datetodelivery"))+"</FONT></td>\n");
          body.append("<td "+myColor+"ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">"+oldQ+"</FONT></td>\n");
          body.append("<td "+myColor+"ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">"+newQ+"</FONT></td>\n");
          body.append("</tr>\n");
        }
      } while (true);
    } catch (Exception e1) {
      e1.printStackTrace();
      body.append("An error occured while querying the database.\n");
      dbrs1.close();
    }
    body.append("\n");
    body.append("</TABLE><br>\n");
    body.append("<TABLE>\n");
    body.append("<tr>\n");
    body.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Deleted:</FONT></td>\n");
    body.append("<td  BGCOLOR=\"#FFCCCC\">&nbsp;&nbsp;</td>\n");
    body.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Added:</FONT></td>\n");
    body.append("<td  BGCOLOR=\"#99FF99\">&nbsp;&nbsp;</td>\n");
    body.append("<td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\">Changed:</FONT></td>\n");
    body.append("<td  BGCOLOR=\"#FFFFCC\">&nbsp;&nbsp;</td>\n");
    body.append("<td></td>\n");
    body.append("</tr>\n");
    body.append("</TABLE>\n");
    NeededData = null;
    return body;
  }

  protected StringBuffer buildPageFooter(String MRNum, String MRLine) {
    StringBuffer footer = new StringBuffer();
    footer.append("<hr WIDTH=\"100%\">\n");
    if (!showApprovalButton) {
      footer.append("<br></FORM></body></html>\n");
      return footer;
    }
    footer.append("<FORM method=\"post\" action=\"" + deliverySchAdminServlet + "\">\n");
    footer.append("<input type=\"hidden\" name = \"action\" value = \"approve\">\n");
    footer.append("<input type=\"hidden\" name = \"prnumber\" value = \""+MRNum+"\">\n");
    footer.append("<input type=\"hidden\" name = \"lineItem\" value = \""+MRLine+"\">\n");

    footer.append("<CENTER><TABLE COLS=2 WIDTH=\"75%\" >\n");
    footer.append("<tr><td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><b>tcmIS Logon Id:<b></FONT></td>\n");
    footer.append("<td><input type=\"text\" name=\"logon\" SIZE=\"10\" ></td></tr>\n");
    footer.append("<tr><td ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"3\"><b>Password:</b></FONT></td></CENTER>\n");
    footer.append("<td><input type=\"password\" name=\"pwd\" SIZE=\"10\"></td></tr></TABLE>\n");

    footer.append("<br><CENTER><input type=\"Submit\" name=\"Approve\" value=\"OK\">\n");
    footer.append("</CENTER></FORM></FONT>\n");
    footer.append("<BR><FONT FACE=\"Arial\" SIZE=\"2\"><A HREF=\"" + deliverySchAdminServlet + "?action=search&Category=1\">Show All Delivery Schedule Changes Needing Review</A></FONT>");
    footer.append("</body></html>\n");
    return footer;
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
}