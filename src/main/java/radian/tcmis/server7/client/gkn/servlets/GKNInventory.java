package radian.tcmis.server7.client.gkn.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.gkn.helpers.*;
import radian.tcmis.server7.client.gkn.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GKNInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GKNTcmISDBModel db = null;
      try {
        db = new GKNTcmISDBModel("GKN",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new GKNServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

