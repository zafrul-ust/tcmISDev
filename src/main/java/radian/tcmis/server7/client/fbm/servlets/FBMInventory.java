package radian.tcmis.server7.client.fbm.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.fbm.helpers.*;
import radian.tcmis.server7.client.fbm.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class FBMInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      FBMTcmISDBModel db = null;
      try {
        db = new FBMTcmISDBModel("FBM",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new FBMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

