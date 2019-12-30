package radian.tcmis.server7.client.gd.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.gd.dbObjs.GDTcmISDBModel;
import radian.tcmis.server7.client.gd.helpers.GDServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GDWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GDTcmISDBModel db = null;
      try {
        db = new GDTcmISDBModel("GD",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new GDServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}