package radian.tcmis.server7.share.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import radian.tcmis.server7.client.seagate.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;

public class NChemCRA extends HttpServlet {
  SeagateTcmISDBModel db = null;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    response.setContentType("Text/html");

    //System.out.println("\n\n------- got here NChemCRA");
    //Vector reqIDV = new Vector(20);
    //Vector startViewV = new Vector(20);
    try {
      db = new SeagateTcmISDBModel("Seagate");
      if (db == null){
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        return;
      }
      //now process new_chem_approval
      NewChemApproval newChemApproval = new NewChemApproval(db);
      //process "Active" requests
      newChemApproval.processSeagateNewChemApproval();
      //process "Inactive" requests
      newChemApproval.processSeagateInactiveRequest();
      //process "Reactivated" requests
      newChemApproval.processSeagateReactivatedRequest();

    } catch (Exception e){
      e.printStackTrace();
      HelpObjs.sendMail(db,"JRun Scheduler","Error occurred while scheduler processing CRA request ",86030,false);
    } finally {
      db.close();
    }
  } //end of doGet
} //end of class