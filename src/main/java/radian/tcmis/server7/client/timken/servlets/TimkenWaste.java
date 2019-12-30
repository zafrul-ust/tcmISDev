package radian.tcmis.server7.client.timken.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.timken.dbObjs.TimkenTcmISDBModel;
import radian.tcmis.server7.client.timken.helpers.TimkenServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class TimkenWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      TimkenTcmISDBModel db = null;
      try {
        db = new TimkenTcmISDBModel("Timken",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new TimkenServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}