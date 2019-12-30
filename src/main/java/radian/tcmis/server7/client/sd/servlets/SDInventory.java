package radian.tcmis.server7.client.sd.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.sd.helpers.*;
import radian.tcmis.server7.client.sd.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class SDInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SDTcmISDBModel db = null;
      try {
        db = new SDTcmISDBModel("SD",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new SDServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

