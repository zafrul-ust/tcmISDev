package radian.tcmis.server7.client.ray.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ray.helpers.*;
import radian.tcmis.server7.client.ray.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class RayInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RayTcmISDBModel db = null;
      try {
        //db = new RayTcmISDBModel();
        db = new RayTcmISDBModel("Ray",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new RayServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

