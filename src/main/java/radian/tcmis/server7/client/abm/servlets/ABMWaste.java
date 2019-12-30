package radian.tcmis.server7.client.abm.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.abm.dbObjs.ABMTcmISDBModel;
import radian.tcmis.server7.client.abm.helpers.ABMServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class ABMWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ABMTcmISDBModel db = null;
      try {
        db = new ABMTcmISDBModel("ABM",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new ABMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}