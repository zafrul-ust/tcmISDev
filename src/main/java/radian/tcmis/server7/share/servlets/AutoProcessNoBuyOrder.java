package radian.tcmis.server7.share.servlets;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import radian.tcmis.server7.client.ray.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;

public class AutoProcessNoBuyOrder extends HttpServlet {
  RayTcmISDBModel db;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    response.setContentType("Text/html");
    try {
      db = new RayTcmISDBModel("tcm_ops");
      if (db == null){
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        HelpObjs.sendMail(db,"AutoProcessNoBuyOrder","Unable to get a DB connection",86030,false);
        return;
      }
      NoBuyOrderPo noBuyOrderPo = new NoBuyOrderPo();
      noBuyOrderPo.autoProcessRequest(db);
    } catch (Exception e){
      e.printStackTrace();
      HelpObjs.sendMail(db,"JRun Scheduler","Error occurred while scheduler processing NBO request ",86030,false);
    } finally {
      db.close();
    }
  } //end of doGet
} //end of class