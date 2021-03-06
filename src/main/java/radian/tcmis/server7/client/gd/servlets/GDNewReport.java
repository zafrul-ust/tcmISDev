package radian.tcmis.server7.client.gd.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.gd.dbObjs.*;
import radian.tcmis.server7.client.gd.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GDNewReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GDTcmISDBModel db = null;
      try {
        db = new GDTcmISDBModel("GD",(String)request.getHeader("logon-version"));
        NewReport obj = new NewReport((ServerResourceBundle) new GDServerResourceBundle(),db);
        obj.setResponse(response);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}