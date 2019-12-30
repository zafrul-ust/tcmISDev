package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.jamonapi.Monitor;
//import com.jamonapi.MonitorFactory;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.barcode.BarCodePage;
import radian.web.servlets.share.HubReceiving;
import radian.web.servlets.share.previousReceiving;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 05-12-03 - Cleanup code
 * 01-11-04 - Monitering Usage
 * 04-18-04 - People with company ID Raytheon can have readonly access to the receiving page
 * 06-30-04 Changing Printer Path to be based on personnel ID
 * 02-28-05 Giving the option to not print Kit/Case Qty Labels.
 */

public class InternalHubReceiving
    extends HttpServlet
    implements SingleThreadModel {
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * HTTP POST handler.
   *
   * @param request               An object implementing the request context
   * @param response              An object implementing the response context
   * @exception ServletException  A wrapper for all exceptions thrown by the
   *      servlet.
   */

  public void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException,
      IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    RayTcmISDBModel db = null;
    String branch_plant;
    HubReceiving obj = null;
    BarCodePage obj1 = null;

    String User_Action = "";
    String User_Session = "";

    String generate_labels = "";
    String po_line = "";
    String purchorder = "";

    Vector sequencenumbers = new Vector();
    // get the current session context (create one if needed)
    HttpSession session = request.getSession();
    //Monitor mon = MonitorFactory.start("Hub Receiving");
    //String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
    //Monitor monuser=MonitorFactory.start("Hub Receiving by "+personnelid+"");

    try {
      db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
      if (db == null) {
        PrintWriter out2 = new PrintWriter(response.getOutputStream());
        out2.println("Starting DB");
        out2.println("DB is null");
        out2.close();
        return;
      }

      Hashtable loginresult = new Hashtable();
      loginresult = radian.web.loginhelpObj.logintopage(session, request, db,
          out);
      String auth = (String) loginresult.get("AUTH");
      String errormsg = (String) loginresult.get("ERROSMSG");

      if (auth == null) {
        auth = "challenge";
      }
      if (errormsg == null) {
        errormsg = "";
      }

      if (! (auth.equalsIgnoreCase("authenticated"))) {
        out.println(radian.web.HTMLHelpObj.printLoginScreen(errormsg,
            "Hub Receiving"));
        out.flush();
      }
      else {
        branch_plant = request.getParameter("HubName");
        if (branch_plant == null) {
          branch_plant = "";
        }
        session.setAttribute("BRANCH_PLANT", branch_plant);

        obj = new HubReceiving( (ServerResourceBundle)new
                               InternalServerResourceBundle(), db);
        if (radian.web.HTMLHelpObj.hasaccessto(session, "Receiving")) {
          obj.setupdateStatus(true);
        }
        else {
          obj.setupdateStatus(false);
        }

        String PaperSize = "";
        String HubNo = "";
        HubNo = request.getParameter("HubNo");
        if (HubNo == null) {
          HubNo = "ERROR";
        }
        User_Session = request.getParameter("session");
        if (User_Session == null) {
          User_Session = "New";
        }
        User_Action = request.getParameter("UserAction");
        if (User_Action == null) {
          User_Action = "New";
        }
        generate_labels = request.getParameter("generate_labels");
        if (generate_labels == null) {
          generate_labels = "No";
        }
        po_line = request.getParameter("poline");
        if (po_line == null) {
          po_line = "";
        }
        PaperSize = request.getParameter("paperSize");
        if (PaperSize == null) {
          PaperSize = "31";
        }
        purchorder = request.getParameter("purchorder");
        if (purchorder == null) {
          purchorder = "";
        }
        String contBin = request.getParameter("contBin");
        if (contBin == null) {
          contBin = "";

          //System.out.println("Checkbok   "+generate_labels+" Size  "+PaperSize+"  User Action  :"+ User_Action+" Po Line: "+po_line+"   Purch Order :"+purchorder);
        }
        if ("GenerateLabels".equalsIgnoreCase(User_Action)) {
          sequencenumbers = (Vector) session.getAttribute(
              "REC_LABEL_SEQUENCE_NUMBERS");
          obj1 = new BarCodePage( (ServerResourceBundle)new
                                 InternalServerResourceBundle(), db, PaperSize);
          obj1.setconBin(contBin);
          String url = "";

          String HubNoforlabel = request.getParameter("HubNoforlabel");
          if (HubNoforlabel == null) {
            HubNoforlabel = "";
          }
          HubNoforlabel.trim();
          obj1.sethubNum(HubNoforlabel);

		  String printKitLabels=request.getParameter( "printKitLabels" );
		  if ( printKitLabels == null )
			printKitLabels="";

		  String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
          url = obj1.generateContainerLabel(sequencenumbers,personnelid,!"Yes".equalsIgnoreCase(printKitLabels));

          out.println(radian.web.HTMLHelpObj.labelredirection(url));
          out.close();
        }
        // Nawaz added the new previous histry 01-30-02
        else if ("previousreceiving".equalsIgnoreCase(User_Action)) {
          previousReceiving obj3 = new previousReceiving( (ServerResourceBundle)new
              InternalServerResourceBundle(), db);
          if (purchorder.length() > 0) {
            obj3.buildoldpoline(po_line, purchorder, "", false,HubNo,out);
          }
          else {
            obj3.buildLikemfgid(po_line, "", false, HubNo,out);
          }
          out.close();
        }
        else {
          obj.doPost(request, response);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      return;
    }
    finally {
      db.close();
      //mon.stop();
      //monuser.stop();
      obj = null;
      try {
        out.close();
      }
      catch(Exception e) {
        //ignore
      }
      //Runtime.getRuntime().gc();
      return;
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    doPost(request, response);
  }
}