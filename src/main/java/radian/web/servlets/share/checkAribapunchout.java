package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Random;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 * 12-03-03 Added a column in punchout_order_message to know if the payload was checked out. Avoiding submitting again when they hit the back button
 */
public class checkAribapunchout extends HttpServlet {
  private ServerResourceBundle bundle = null;
  private TcmISDBModel db = null;
  private static org.apache.log4j.Logger orclog = org.apache.log4j.Logger.getLogger(checkAribapunchout.class);
  private boolean intcmIsApplication = false;

  public checkAribapunchout(ServerResourceBundle b, TcmISDBModel d) {
    bundle = b;
    db = d;
  }

  public void doResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
    HttpSession aribasession = request.getSession();

    PersonnelBean personnelBean = (PersonnelBean) aribasession.getAttribute("personnelBean");
    if (personnelBean !=null)
    {
      Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
      intcmIsApplication = false;
      if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
        intcmIsApplication = true;
      }
    }

    String payloadID = request.getParameter("payloadID");
    if (payloadID == null) {
      payloadID = "";
    }
    payloadID = payloadID.trim();

    String userAction = request.getParameter("userAction");
    if (userAction == null) {
      userAction = "";
    }
    userAction = userAction.trim();

    String refreshCountString = request.getParameter("refreshCount");
    if (refreshCountString == null) {
      refreshCountString = "";
    }
    refreshCountString = refreshCountString.trim();
    int refreshCount = 0;
    if (refreshCountString.length() >0)
    {
      try
      {
      refreshCount = new BigDecimal(refreshCountString).intValue();
      refreshCount++;
      }
      catch (Exception ee)
      {
        ee.getMessage();
        refreshCount = 0;
      }
    }

    String thedecidingRandomNumber = request.getParameter("thedecidingRandomNumber");
    if (thedecidingRandomNumber == null) {
      thedecidingRandomNumber = "";
    }
    thedecidingRandomNumber = thedecidingRandomNumber.trim();

    int testifrecordexists = 0;
    if (payloadID.length() > 0) {
      try {
        testifrecordexists = DbHelpers.countQuery(db, "select count(*) from punchout_order_message where PAYLOAD_ID = '" + payloadID + "' and PUNCHOUT = 'Y'");

        String facpartQuery = "";
        if (testifrecordexists == 0) {
        } else {
          userAction = "checkoutfinal";
        }
      } catch (Exception er) {
        er.printStackTrace();
        radian.web.emailHelpObj.sendnawazemail("Error in punchout_order_message Query",
                                               "Error in punchout_order_message Query \npayloadID\n" + payloadID + " \n\n " + er.getMessage() + "");
        return;
      }
    }

    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);

    //orclog.info("payloadID in checkAribapunchout :   " + payloadID + "     thedecidingRandomNumber   " + thedecidingRandomNumber + "");

    if (thedecidingRandomNumber.length() > 0 && !"checkoutfinal".equalsIgnoreCase(userAction)) {
      String randomnumberfromsesion = aribasession.getAttribute("CHKOUT_RANDOMNUMBERCOOKIE") == null ? "" : aribasession.getAttribute("CHKOUT_RANDOMNUMBERCOOKIE").toString();
      if (randomnumberfromsesion.equalsIgnoreCase(thedecidingRandomNumber)) {
        thedecidingRandomNumber = tmpReqNum.toString();
        aribasession.setAttribute("CHKOUT_RANDOMNUMBERCOOKIE", thedecidingRandomNumber);
      } else {
        thedecidingRandomNumber = tmpReqNum.toString();
        aribasession.setAttribute("CHKOUT_RANDOMNUMBERCOOKIE", thedecidingRandomNumber);
        out.println("<CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>");
        return;
      }
    } else {
      thedecidingRandomNumber = tmpReqNum.toString();
      aribasession.setAttribute("CHKOUT_RANDOMNUMBERCOOKIE", thedecidingRandomNumber);
    }

    //int testifrecordexists=0;
    if (payloadID.length() > 0) {
      try {
        //testifrecordexists=DbHelpers.countQuery( db,"select count(*) from punchout_order_message where PAYLOAD_ID = '" + payloadID + "' and PUNCHOUT = 'Y'" );

        String facpartQuery = "";
        if (testifrecordexists == 0) {
          buildrefreshPage(payloadID, thedecidingRandomNumber, out,refreshCount);
        } else {
          buildpostrequest(payloadID, aribasession, out);
        }
      } catch (Exception er) {
        er.printStackTrace();
        radian.web.emailHelpObj.sendnawazemail("Error in punchout_order_message Query",
                                               "Error in punchout_order_message Query \npayloadID\n" + payloadID + " \n\n " + er.getMessage() + "");
        return;
      }
    }
    out.close();
  }

  private void buildrefreshPage(String PayID, String randondecidingnum, PrintWriter regout,int refreshCount) {
    //StringBuffer Msg=new StringBuffer();
    if (refreshCount ==0 || refreshCount % 100 ==0)
    {
    orclog.info("Waiting for Checkout from tcmIS.. refreshCount-"+refreshCount+"");
    orclog.info("payloadID in checkAribapunchout :   " + PayID + "     thedecidingRandomNumber   " + randondecidingnum + "");
    }
    regout.println(radian.web.HTMLHelpObj.printHTMLHeader("Waiting for Checkout from tcmIS..", "",intcmIsApplication));
    String hosturl = radian.web.tcmisResourceLoader.gethosturl();

    if (refreshCount <500)
    {
    regout.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"10; url=" + hosturl + "tcmIS/aribacheckout?payloadID=" + PayID + "&thedecidingRandomNumber=" + randondecidingnum + "&userAction=checkforcheckout&refreshCount="+refreshCount+"\">\n\n");
    }
    regout.println("</HEAD>  \n");

    regout.println("<BODY><CENTER>\n");
    if (refreshCount <500)
    {
     regout.println(radian.web.HTMLHelpObj.printMessageinTable("<FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Waiting for Checkout from tcmIS..</B></FONT>"));
    }
    else
    {
     regout.println(radian.web.HTMLHelpObj.printMessageinTable("<FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>This session has timed out. Please try again.</B></FONT>"));
    }
    regout.println("</CENTER></BODY>\n");
    regout.println("</HTML>\n");

    return;
  }

  private void buildpostrequest(String PayID, HttpSession currsession, PrintWriter regout) {
    //StringBuffer Msg=new StringBuffer();

    regout.println(radian.web.HTMLHelpObj.printHTMLHeader("Submit Punchout", "iprocurement.js",intcmIsApplication));
    String hosturl = radian.web.tcmisResourceLoader.gethosturl();
    String posturl = "";

    int testifpostedform = 0;
    int submitcount = 0;
    try {
      testifpostedform = DbHelpers.countQuery(db, "select count(*) from punchout_order_message where PAYLOAD_ID = '" + PayID + "' and PUNCHOUT = 'Y' and POSTED_TO_ORACLE='Y'");
    } catch (Exception ex2) {
    }

    try {
      //String submitcounts=currsession.getAttribute( PayID ).toString();
      String submitcounts = currsession.getAttribute(PayID) == null ? "" : currsession.getAttribute(PayID).toString();

      submitcount = Integer.parseInt(submitcounts);
    } catch (Exception ex3) {
      submitcount = 0;
    }

    if (testifpostedform == 0 || ! (submitcount > 1)) {
      String query = "select browser_form_post from punchout_order_message where payload_id = '" + PayID + "'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        while (rs.next()) {
          posturl = rs.getString("BROWSER_FORM_POST") == null ? "" : rs.getString("BROWSER_FORM_POST");
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        dbrs.close();
      }

      String doupdate = "update punchout_order_message set POSTED_TO_ORACLE='Y' where payload_id = '" + PayID + "' ";
      try {
        db.doUpdate(doupdate);
      } catch (Exception ex1) {
      }

      regout.println("</HEAD>  \n");
      regout.println("<BODY onLoad=\"submitheform()\">\n");
      regout.println(radian.web.HTMLHelpObj.printMessageinTable("<FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please wait Checkout in Progress..</B></FONT>"));

      String postingMsg = "";
      if (testifpostedform == 0) {
        try {
/* old school
          PayID = HelpObjs.findReplace(PayID, " ", "%20");
          orclog.info("Calling https://www.tcmis.com/tcmIS/oip/punchout/checkout?payloadID=" + PayID + "");
          postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/tcmIS/oip/punchout/checkout?payloadID=" + PayID, "payloadID=" + PayID, "text/html"); //PROD
          orclog.info("Done Calling https://www.tcmis.com/tcmIS/oip/punchout/checkout?payloadID=" + PayID + "");
          currsession.setAttribute("POSTFORMMSG", "" + postingMsg + "");
*/
          orclog.info("Getting shopping cart for payloadID=" + PayID + "");
          com.tcmis.client.seagate.process.CxmlProcess process = new com.tcmis.client.seagate.process.CxmlProcess("SEAGATE");
          postingMsg = process.getPunchoutOrderMessage(PayID);
          orclog.info("Got cart for payloadID=" + PayID + ":" + postingMsg);
          currsession.setAttribute("POSTFORMMSG", "" + postingMsg + "");
        } catch (Exception ex) {
          ex.printStackTrace(System.out);
          postingMsg = "";
        }
      } else {
        //postingMsg = currsession.getAttribute( "POSTFORMMSG").toString();
        postingMsg = currsession.getAttribute("POSTFORMMSG") == null ? "" : currsession.getAttribute("POSTFORMMSG").toString();
      }

      regout.println("<form  NAME=\"puchoutiproc\" action=\"" + posturl + "\" method=\"POST\">\n");
      regout.println("<input type=\"hidden\" name=\"cxml-urlencoded\" value=\"" + postingMsg + "\"></form>");
      //regout.println("<input type=\"hidden\" name=\"cxml-urlencoded\" value=\""+postingMsg+"\"><div align=\"center\">\n<input type=\"image\" src=\"/images/continue1.gif\" alt=\"Continue\">\n\n</div>\n</form>");
      regout.println("</BODY>\n");
      regout.println("</HTML>\n");
      submitcount++;
      currsession.setAttribute(PayID, "" + submitcount + "");

      orclog.info("Submitting the form to Oracle" + PayID + "");
    } else {
      regout.println(radian.web.HTMLHelpObj.printMessageinTable("<FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>This session is already checked out.<BR></B></FONT>"));
    }

    return;
  }
}