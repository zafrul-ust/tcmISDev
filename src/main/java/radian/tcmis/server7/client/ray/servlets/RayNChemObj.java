package radian.tcmis.server7.client.ray.servlets;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ray.dbObjs.*;
import radian.tcmis.server7.client.ray.helpers.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class RayNChemObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException,  IOException
  {
      doPost(request,response);
  }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RayTcmISDBModel db = null;
      try {
        //System.out.println("\n\n----------- rayNchemobj*******");
        //db = new RayTcmISDBModel();
        db = new RayTcmISDBModel("Ray",(String)request.getHeader("logon-version"));
        //System.out.println("\n\n----------- rayNchemobj after db");
        NChemObj obj = new NChemObj((ServerResourceBundle) new RayServerResourceBundle(),db);
        //System.out.println("-------- what the ****");
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}