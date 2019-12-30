package radian.tcmis.server7.client.hal.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.hal.helpers.*;
import radian.tcmis.server7.client.hal.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class HALInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HALTcmISDBModel db = null;
      try {
        db = new HALTcmISDBModel("HAL",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new HALServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

