package radian.tcmis.server7.client.hal.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.hal.dbObjs.HALTcmISDBModel;
import radian.tcmis.server7.client.hal.helpers.HALServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class HALWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HALTcmISDBModel db = null;
      try {
        db = new HALTcmISDBModel("HAL",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new HALServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}