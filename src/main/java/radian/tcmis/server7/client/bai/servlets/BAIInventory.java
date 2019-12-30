package radian.tcmis.server7.client.bai.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.bai.helpers.*;
import radian.tcmis.server7.client.bai.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class BAIInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      BAITcmISDBModel db = null;
      try {
        db = new BAITcmISDBModel("BAI",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new BAIServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

