package radian.tcmis.server7.client.ray.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ray.dbObjs.*;
import radian.tcmis.server7.client.ray.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class RayCheckLogon extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RayTcmISDBModel db = null;
        try {
          //System.out.println("\n\n-------- check logon logon_version: "+request.getHeader("logon-version"));
          db = new RayTcmISDBModel("Ray",(String)request.getHeader("logon-version"));
          CheckLogon obj = new CheckLogon((ServerResourceBundle) new RayServerResourceBundle(),db);
          obj.doPost(request,response);
        } catch (Exception e){
          e.printStackTrace();
          //java.sql.SQLException: ORA-01012: not logged on
        } finally {
          db.close();
        }
   }
}

