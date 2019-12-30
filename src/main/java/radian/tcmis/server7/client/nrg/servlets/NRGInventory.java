package radian.tcmis.server7.client.nrg.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.nrg.helpers.*;
import radian.tcmis.server7.client.nrg.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class NRGInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      NRGTcmISDBModel db = null;
      try {
        db = new NRGTcmISDBModel("NRG",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new NRGServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

