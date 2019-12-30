package radian.tcmis.server7.share.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import radian.tcmis.server7.client.seagate.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;

public class PriceQuoteProcessSched extends HttpServlet {
  SeagateTcmISDBModel db = null;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    response.setContentType("Text/html");

    //System.out.println("\n\n------- got here PriceQuoteProcessSched");
    try {
      db = new SeagateTcmISDBModel("Seagate");
      if (db == null){
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        return;
      }
      //now process price quote
      NewChemApproval newChemApproval = new NewChemApproval(db);
      newChemApproval.processSeagateNewChemApproval();

    } catch (Exception e){
      e.printStackTrace();
      HelpObjs.sendMail(db,"JRun Scheduler","Error occurred while scheduler processing price quote request ",86030,true);
    } finally {
      db.close();
    }
  } //end of doGet
} //end of class