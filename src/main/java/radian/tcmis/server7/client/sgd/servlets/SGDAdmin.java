package radian.tcmis.server7.client.sgd.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.sgd.dbObjs.*;
import radian.tcmis.server7.client.sgd.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class SGDAdmin extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SGDTcmISDBModel db = null;
      try {
        db = new SGDTcmISDBModel("SGD",(String)request.getHeader("logon-version"));
        Admin obj = new Admin((ServerResourceBundle) new SGDServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }

}
