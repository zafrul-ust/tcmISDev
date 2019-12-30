package radian.tcmis.server7.client.sales.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.sales.helpers.*;
import radian.tcmis.server7.client.sales.dbObjs.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class SalesInventory extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SalesTcmISDBModel db = null;
      try {
        db = new SalesTcmISDBModel("Sales",(String)request.getHeader("logon-version"));
        Inventory obj = new Inventory((ServerResourceBundle) new SalesServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

