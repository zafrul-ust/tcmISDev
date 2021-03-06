package radian.tcmis.server7.client.cmm.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.cmm.dbObjs.*;
import radian.tcmis.server7.client.cmm.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class CMMInventoryObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      CMMTcmISDBModel db = null;
      try {
        db = new CMMTcmISDBModel("CMM",(String)request.getHeader("logon-version"));
        InventoryObj obj = new InventoryObj((ServerResourceBundle) new CMMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}