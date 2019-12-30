package radian.tcmis.server7.client.gm.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.gm.dbObjs.GMTcmISDBModel;
import radian.tcmis.server7.client.gm.helpers.GMServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GMWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GMTcmISDBModel db = null;
      try {
        db = new GMTcmISDBModel("GM",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new GMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}