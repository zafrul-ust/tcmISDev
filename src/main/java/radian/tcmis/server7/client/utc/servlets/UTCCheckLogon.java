package radian.tcmis.server7.client.utc.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.utc.dbObjs.*;
import radian.tcmis.server7.client.utc.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class UTCCheckLogon extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      UTCTcmISDBModel db = null;
      try {
        //System.out.println("\n\n-------- check logon logon_version: "+request.getHeader("logon-version"));
        db = new UTCTcmISDBModel("UTC",(String)request.getHeader("logon-version"));
        CheckLogon obj = new CheckLogon((ServerResourceBundle) new UTCServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

