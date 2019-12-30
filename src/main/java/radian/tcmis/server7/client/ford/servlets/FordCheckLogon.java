package radian.tcmis.server7.client.ford.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ford.dbObjs.*;
import radian.tcmis.server7.client.ford.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class FordCheckLogon extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     FordTcmISDBModel db = null;
        try {
          db = new FordTcmISDBModel("Ford",(String)request.getHeader("logon-version"));
          CheckLogon obj = new CheckLogon((ServerResourceBundle) new FordServerResourceBundle(),db);
          obj.doPost(request,response);
        } catch (Exception e){
          e.printStackTrace();
        } finally {
          db.close();
        }
   }
}

