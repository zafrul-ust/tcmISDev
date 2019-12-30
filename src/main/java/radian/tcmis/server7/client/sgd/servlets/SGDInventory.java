package radian.tcmis.server7.client.sgd.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.sgd.helpers.*;
import radian.tcmis.server7.client.sgd.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class SGDInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SGDTcmISDBModel db = null;
      try {
        db = new SGDTcmISDBModel("SGD",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new SGDServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

