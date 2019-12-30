package radian.tcmis.server7.client.iai.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.iai.dbObjs.IAITcmISDBModel;
import radian.tcmis.server7.client.iai.helpers.IAIServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class IAIWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      IAITcmISDBModel db = null;
      try {
        db = new IAITcmISDBModel("IAI",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new IAIServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}