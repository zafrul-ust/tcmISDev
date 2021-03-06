package radian.tcmis.server7.client.doe.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.doe.helpers.*;
import radian.tcmis.server7.client.doe.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class DOEInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DOETcmISDBModel db = null;
      try {
        db = new DOETcmISDBModel("DOE",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new DOEServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

