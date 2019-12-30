package radian.tcmis.server7.client.ray.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ray.dbObjs.*;
import radian.tcmis.server7.client.ray.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class RayCheckClient extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RayTcmISDBModel db = null;
      try {
        //System.out.println("\n\n------------ in checkclient new structure inside client ray.\n\n");
        //db = new RayTcmISDBModel();
        db = new RayTcmISDBModel("Ray",(String)request.getHeader("logon-version"));
        CheckClient obj = new CheckClient((ServerResourceBundle) new RayServerResourceBundle(),db);
        obj.doPost(request,response);
        //System.out.println("\n\n------------ in checkclient new structure inside client ray2.\n\n");
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     //System.out.println("FOO");
     doPost(request, response);
   }
}

