package radian.tcmis.server7.client.am.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.am.dbObjs.*;
import radian.tcmis.server7.client.am.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class AMInventoryObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AMTcmISDBModel db = null;
      try {
        db = new AMTcmISDBModel("AM",(String)request.getHeader("logon-version"));
        InventoryObj obj = new InventoryObj((ServerResourceBundle) new AMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}