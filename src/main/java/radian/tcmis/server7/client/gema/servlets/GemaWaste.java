package radian.tcmis.server7.client.gema.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.gema.dbObjs.GemaTcmISDBModel;
import radian.tcmis.server7.client.gema.helpers.GemaServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GemaWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GemaTcmISDBModel db = null;
      try {
        db = new GemaTcmISDBModel("GEMA",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new GemaServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}