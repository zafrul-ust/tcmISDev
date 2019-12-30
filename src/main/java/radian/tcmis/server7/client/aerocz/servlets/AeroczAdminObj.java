package radian.tcmis.server7.client.aerocz.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.aerocz.dbObjs.*;
import radian.tcmis.server7.client.aerocz.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class AeroczAdminObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AeroczTcmISDBModel db = null;
      try {
        db = new AeroczTcmISDBModel("Aerocz",(String)request.getHeader("logon-version"));
        AdminObj obj = new AdminObj((ServerResourceBundle) new AeroczServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}