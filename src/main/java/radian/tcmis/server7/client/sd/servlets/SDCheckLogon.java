package radian.tcmis.server7.client.sd.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.sd.dbObjs.*;
import radian.tcmis.server7.client.sd.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class SDCheckLogon extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SDTcmISDBModel db = null;
      try {
        //System.out.println("\n\n-------- check logon logon_version: "+request.getHeader("logon-version"));
        db = new SDTcmISDBModel("SD",(String)request.getHeader("logon-version"));
        CheckLogon obj = new CheckLogon((ServerResourceBundle) new SDServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

