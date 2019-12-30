package radian.tcmis.server7.client.pepsi.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.pepsi.dbObjs.PepsiTcmISDBModel;
import radian.tcmis.server7.client.pepsi.helpers.PepsiServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class PepsiWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      PepsiTcmISDBModel db = null;
      try {
        db = new PepsiTcmISDBModel("Pepsi",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new PepsiServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}