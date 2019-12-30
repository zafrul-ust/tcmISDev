package radian.tcmis.server7.client.ablaero.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ablaero.dbObjs.ABLaeroTcmISDBModel;
import radian.tcmis.server7.client.ablaero.helpers.ABLaeroServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class ABLaeroWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ABLaeroTcmISDBModel db = null;
      try {
        db = new ABLaeroTcmISDBModel("ABLaero",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new ABLaeroServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}