package radian.tcmis.server7.client.maeet.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.maeet.dbObjs.MAEETTcmISDBModel;
import radian.tcmis.server7.client.maeet.helpers.MAEETServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class MAEETWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      MAEETTcmISDBModel db = null;
      try {
        db = new MAEETTcmISDBModel("MAEET",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new MAEETServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}