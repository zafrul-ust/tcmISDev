package radian.tcmis.server7.client.ray.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.client.ray.helpers.RayServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class RayWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RayTcmISDBModel db = null;
      try {
        //System.out.println("--------- RayWaste");
        //db = new RayTcmISDBModel();
        db = new RayTcmISDBModel("Ray",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new RayServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        //System.out.println("--------- Exception RayWaste");
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}