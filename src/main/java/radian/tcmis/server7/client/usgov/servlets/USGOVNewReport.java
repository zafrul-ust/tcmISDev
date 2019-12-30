package radian.tcmis.server7.client.usgov.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.usgov.dbObjs.*;
import radian.tcmis.server7.client.usgov.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class USGOVNewReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      USGOVTcmISDBModel db = null;
      try {
        db = new USGOVTcmISDBModel("USGOV",(String)request.getHeader("logon-version"));
        NewReport obj = new NewReport((ServerResourceBundle) new USGOVServerResourceBundle(),db);
        obj.setResponse(response);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}