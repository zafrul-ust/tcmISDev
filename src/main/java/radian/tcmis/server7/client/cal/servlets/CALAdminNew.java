package radian.tcmis.server7.client.cal.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.cal.dbObjs.*;
import radian.tcmis.server7.client.cal.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class CALAdminNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      CALTcmISDBModel db = null;
      try {
        db = new CALTcmISDBModel("CAL",(String)request.getHeader("logon-version"));
        AdminNew obj = new AdminNew((ServerResourceBundle) new CALServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}