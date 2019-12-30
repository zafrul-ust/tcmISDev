package radian.tcmis.server7.client.cyclone.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.cyclone.dbObjs.CycloneTcmISDBModel;
import radian.tcmis.server7.client.cyclone.helpers.CycloneServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class CycloneWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      CycloneTcmISDBModel db = null;
      try {
        db = new CycloneTcmISDBModel("Cyclone",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new CycloneServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}