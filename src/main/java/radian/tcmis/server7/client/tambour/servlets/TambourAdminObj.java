package radian.tcmis.server7.client.tambour.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.tambour.dbObjs.*;
import radian.tcmis.server7.client.tambour.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class TambourAdminObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      TambourTcmISDBModel db = null;
      try {
        db = new TambourTcmISDBModel("Tambour",(String)request.getHeader("logon-version"));
        AdminObj obj = new AdminObj((ServerResourceBundle) new TambourServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}