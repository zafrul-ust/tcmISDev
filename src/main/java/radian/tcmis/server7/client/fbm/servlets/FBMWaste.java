package radian.tcmis.server7.client.fbm.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.fbm.dbObjs.FBMTcmISDBModel;
import radian.tcmis.server7.client.fbm.helpers.FBMServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class FBMWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      FBMTcmISDBModel db = null;
      try {
        db = new FBMTcmISDBModel("FBM",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new FBMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}