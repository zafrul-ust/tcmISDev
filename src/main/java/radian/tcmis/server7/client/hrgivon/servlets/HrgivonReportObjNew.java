package radian.tcmis.server7.client.hrgivon.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.hrgivon.dbObjs.*;
import radian.tcmis.server7.client.hrgivon.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class HrgivonReportObjNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HrgivonTcmISDBModel db = null;
      try {
        db = new HrgivonTcmISDBModel("Hrgivon",(String)request.getHeader("logon-version"));
        ReportObjNew obj = new ReportObjNew((ServerResourceBundle) new HrgivonServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}