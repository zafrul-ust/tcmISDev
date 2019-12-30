package radian.tcmis.server7.client.bai.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.bai.dbObjs.BAITcmISDBModel;
import radian.tcmis.server7.client.bai.helpers.BAIServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class BAIWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      BAITcmISDBModel db = null;
      try {
        db = new BAITcmISDBModel("BAI",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new BAIServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}