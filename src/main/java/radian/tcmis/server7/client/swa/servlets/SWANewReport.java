package radian.tcmis.server7.client.swa.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.swa.dbObjs.*;
import radian.tcmis.server7.client.swa.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class SWANewReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SWATcmISDBModel db = null;
      try {
        //db = new SWATcmISDBModel();
        db = new SWATcmISDBModel("SWA",(String)request.getHeader("logon-version"));
        NewReport obj = new NewReport((ServerResourceBundle) new SWAServerResourceBundle(),db);
        obj.setResponse(response);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}