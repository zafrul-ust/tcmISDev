package radian.tcmis.server7.client.ablaero.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ablaero.dbObjs.*;
import radian.tcmis.server7.client.ablaero.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class ABLaeroCheckLogon extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     ABLaeroTcmISDBModel db = null;
        try {
          db = new ABLaeroTcmISDBModel("ABLaero",(String)request.getHeader("logon-version"));
          CheckLogon obj = new CheckLogon((ServerResourceBundle) new ABLaeroServerResourceBundle(),db);
          obj.doPost(request,response);
        } catch (Exception e){
          e.printStackTrace();
        } finally {
          db.close();
        }
   }
}

