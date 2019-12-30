package radian.tcmis.server7.client.hrgivon.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.hrgivon.helpers.*;
import radian.tcmis.server7.client.hrgivon.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class HrgivonInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HrgivonTcmISDBModel db = null;
      try {
        db = new HrgivonTcmISDBModel("Hrgivon",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new HrgivonServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

