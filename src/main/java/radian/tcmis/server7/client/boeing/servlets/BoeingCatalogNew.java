package radian.tcmis.server7.client.boeing.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.boeing.dbObjs.*;
import radian.tcmis.server7.client.boeing.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class BoeingCatalogNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      BoeingTcmISDBModel db = null;
      try {
        db = new BoeingTcmISDBModel("Boeing",(String)request.getHeader("logon-version"));
        CatalogNew obj = new CatalogNew((ServerResourceBundle) new BoeingServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
