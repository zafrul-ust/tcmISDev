package radian.tcmis.server7.client.volvo.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.volvo.dbObjs.VolvoTcmISDBModel;
import radian.tcmis.server7.client.volvo.helpers.VolvoServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class VolvoWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      VolvoTcmISDBModel db = null;
      try {
        db = new VolvoTcmISDBModel("Volvo",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new VolvoServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}