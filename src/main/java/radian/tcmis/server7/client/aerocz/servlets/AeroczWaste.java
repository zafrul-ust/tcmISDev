package radian.tcmis.server7.client.aerocz.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.aerocz.dbObjs.AeroczTcmISDBModel;
import radian.tcmis.server7.client.aerocz.helpers.AeroczServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class AeroczWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AeroczTcmISDBModel db = null;
      try {
        db = new AeroczTcmISDBModel("Aerocz",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new AeroczServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}