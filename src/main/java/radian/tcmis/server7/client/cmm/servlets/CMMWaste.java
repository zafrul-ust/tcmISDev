package radian.tcmis.server7.client.cmm.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.cmm.dbObjs.CMMTcmISDBModel;
import radian.tcmis.server7.client.cmm.helpers.CMMServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class CMMWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      CMMTcmISDBModel db = null;
      try {
        db = new CMMTcmISDBModel("CMM",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new CMMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}