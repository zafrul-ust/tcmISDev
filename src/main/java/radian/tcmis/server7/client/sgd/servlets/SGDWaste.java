package radian.tcmis.server7.client.sgd.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.sgd.dbObjs.SGDTcmISDBModel;
import radian.tcmis.server7.client.sgd.helpers.SGDServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class SGDWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SGDTcmISDBModel db = null;
      try {
        db = new SGDTcmISDBModel("SGD",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new SGDServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}