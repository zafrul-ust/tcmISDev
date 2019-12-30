package radian.tcmis.server7.client.oma.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.oma.helpers.*;
import radian.tcmis.server7.client.oma.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class OMAInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      OMATcmISDBModel db = null;
      try {
        db = new OMATcmISDBModel("OMA",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new OMAServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

