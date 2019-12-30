package radian.tcmis.server7.client.fedco.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.fedco.dbObjs.*;
import radian.tcmis.server7.client.fedco.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class FedcoReportObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      FedcoTcmISDBModel db = null;
      try {
        db = new FedcoTcmISDBModel("Fedco",(String)request.getHeader("logon-version"));
        ReportObj obj = new ReportObj((ServerResourceBundle) new FedcoServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}