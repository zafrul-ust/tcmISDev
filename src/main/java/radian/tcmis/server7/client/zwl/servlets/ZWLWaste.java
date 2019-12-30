package radian.tcmis.server7.client.zwl.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.zwl.dbObjs.ZWLTcmISDBModel;
import radian.tcmis.server7.client.zwl.helpers.ZWLServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class ZWLWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ZWLTcmISDBModel db = null;
      try {
        db = new ZWLTcmISDBModel("ZWL",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new ZWLServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}