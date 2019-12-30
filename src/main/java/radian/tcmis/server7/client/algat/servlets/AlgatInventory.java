package radian.tcmis.server7.client.algat.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.algat.helpers.*;
import radian.tcmis.server7.client.algat.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class AlgatInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AlgatTcmISDBModel db = null;
      try {
        db = new AlgatTcmISDBModel("Algat",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new AlgatServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

