package radian.tcmis.server7.client.nalco.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.nalco.helpers.*;
import radian.tcmis.server7.client.nalco.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class NalcoInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      NalcoTcmISDBModel db = null;
      try {
        db = new NalcoTcmISDBModel("Nalco",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new NalcoServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

