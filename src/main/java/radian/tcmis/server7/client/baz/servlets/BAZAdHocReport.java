package radian.tcmis.server7.client.baz.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.baz.dbObjs.*;
import radian.tcmis.server7.client.baz.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class BAZAdHocReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      BAZTcmISDBModel db = null;
      try {
        db = new BAZTcmISDBModel("BAZ",(String)request.getHeader("logon-version"));
        AdHocReports obj = new AdHocReports((ServerResourceBundle) new BAZServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}