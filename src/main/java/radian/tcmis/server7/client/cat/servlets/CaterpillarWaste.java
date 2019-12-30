package radian.tcmis.server7.client.cat.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.cat.dbObjs.CaterpillarTcmISDBModel;
import radian.tcmis.server7.client.cat.helpers.CaterpillarServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class CaterpillarWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      CaterpillarTcmISDBModel db = null;
      try {
        db = new CaterpillarTcmISDBModel("Caterpillar",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new CaterpillarServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}