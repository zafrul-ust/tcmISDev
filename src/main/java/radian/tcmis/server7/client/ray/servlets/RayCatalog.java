package radian.tcmis.server7.client.ray.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ray.dbObjs.*;
import radian.tcmis.server7.client.ray.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class RayCatalog extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RayTcmISDBModel db = null;
      try {
        //db = new RayTcmISDBModel();
        db = new RayTcmISDBModel("Ray",(String)request.getHeader("logon-version"));
        Catalog obj = new Catalog((ServerResourceBundle) new RayServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

































