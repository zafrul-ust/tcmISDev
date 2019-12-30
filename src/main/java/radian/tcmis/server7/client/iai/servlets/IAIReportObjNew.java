package radian.tcmis.server7.client.iai.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.iai.dbObjs.*;
import radian.tcmis.server7.client.iai.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class IAIReportObjNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      IAITcmISDBModel db = null;
      try {
        db = new IAITcmISDBModel("IAI",(String)request.getHeader("logon-version"));
        ReportObjNew obj = new ReportObjNew((ServerResourceBundle) new IAIServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}