package radian.tcmis.server7.client.hal.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.hal.dbObjs.*;
import radian.tcmis.server7.client.hal.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class HALNewReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HALTcmISDBModel db = null;
      try {
        db = new HALTcmISDBModel("HAL",(String)request.getHeader("logon-version"));
        NewReport obj = new NewReport((ServerResourceBundle) new HALServerResourceBundle(),db);
        obj.setResponse(response);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}