package radian.tcmis.server7.client.verasun.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.verasun.dbObjs.VerasunTcmISDBModel;
import radian.tcmis.server7.client.verasun.helpers.VerasunServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class VerasunWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      VerasunTcmISDBModel db = null;
      try {
        db = new VerasunTcmISDBModel("Verasun",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new VerasunServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}