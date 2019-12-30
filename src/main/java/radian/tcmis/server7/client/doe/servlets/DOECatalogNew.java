package radian.tcmis.server7.client.doe.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.doe.dbObjs.*;
import radian.tcmis.server7.client.doe.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class DOECatalogNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DOETcmISDBModel db = null;
      try {
        db = new DOETcmISDBModel("DOE",(String)request.getHeader("logon-version"));
        CatalogNew obj = new CatalogNew((ServerResourceBundle) new DOEServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
