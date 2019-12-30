package radian.tcmis.server7.client.siemens.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.siemens.dbObjs.*;
import radian.tcmis.server7.client.siemens.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class SiemensLoadChoices extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SiemensTcmISDBModel db = null;
      try {
        db = new SiemensTcmISDBModel("Siemens",(String)request.getHeader("logon-version"));
        LoadChoices obj = new LoadChoices((ServerResourceBundle) new SiemensServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}