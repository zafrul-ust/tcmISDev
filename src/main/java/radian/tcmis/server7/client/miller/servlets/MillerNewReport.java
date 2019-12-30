package radian.tcmis.server7.client.miller.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.miller.dbObjs.*;
import radian.tcmis.server7.client.miller.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class MillerNewReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      MillerTcmISDBModel db = null;
      try {
        db = new MillerTcmISDBModel("Miller",(String)request.getHeader("logon-version"));
        NewReport obj = new NewReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
        obj.setResponse(response);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}