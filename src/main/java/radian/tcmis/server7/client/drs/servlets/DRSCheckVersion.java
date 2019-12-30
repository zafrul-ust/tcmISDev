package radian.tcmis.server7.client.drs.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.drs.dbObjs.*;
import radian.tcmis.server7.client.drs.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class DRSCheckVersion extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DRSTcmISDBModel db = null;
      try {
        //db = new DRSTcmISDBModel();
        db = new DRSTcmISDBModel("DRS",(String)request.getHeader("logon-version"));
        CheckVersion obj = new CheckVersion((ServerResourceBundle) new DRSServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

