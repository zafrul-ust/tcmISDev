package radian.tcmis.server7.client.pge.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.pge.dbObjs.PGETcmISDBModel;
import radian.tcmis.server7.client.pge.helpers.PGEServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class PGEWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      PGETcmISDBModel db = null;
      try {
        db = new PGETcmISDBModel("PGE",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new PGEServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}