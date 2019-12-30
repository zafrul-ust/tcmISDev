package radian.tcmis.server7.client.dana.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.dana.dbObjs.*;
import radian.tcmis.server7.client.dana.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class DanaCatalog extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DanaTcmISDBModel db = null;
      try {
        db = new DanaTcmISDBModel("Dana",(String)request.getHeader("logon-version"));
        Catalog obj = new Catalog((ServerResourceBundle) new DanaServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

































