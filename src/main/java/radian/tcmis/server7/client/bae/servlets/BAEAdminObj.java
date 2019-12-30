package radian.tcmis.server7.client.bae.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.bae.dbObjs.*;
import radian.tcmis.server7.client.bae.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class BAEAdminObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      BAETcmISDBModel db = null;
      try {
        //db = new BAETcmISDBModel();
        db = new BAETcmISDBModel("BAE",(String)request.getHeader("logon-version"));
        AdminObj obj = new AdminObj((ServerResourceBundle) new BAEServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}