package radian.tcmis.server7.client.cat.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.cat.dbObjs.*;
import radian.tcmis.server7.client.cat.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class CaterpillarWastePrintLabel extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      CaterpillarTcmISDBModel db = null;
      try {
        db = new CaterpillarTcmISDBModel("Caterpillar",(String)request.getHeader("logon-version"));
        WasteLabel obj = new WasteLabel((ServerResourceBundle) new CaterpillarServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}