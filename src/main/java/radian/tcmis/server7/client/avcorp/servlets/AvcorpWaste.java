package radian.tcmis.server7.client.avcorp.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.avcorp.dbObjs.AvcorpTcmISDBModel;
import radian.tcmis.server7.client.avcorp.helpers.AvcorpServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class AvcorpWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AvcorpTcmISDBModel db = null;
      try {
        db = new AvcorpTcmISDBModel("Avcorp",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new AvcorpServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}