package radian.tcmis.server7.client.aeropia.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.aeropia.helpers.*;
import radian.tcmis.server7.client.aeropia.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class AeropiaInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AeropiaTcmISDBModel db = null;
      try {
        db = new AeropiaTcmISDBModel("Aeropia",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new AeropiaServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

