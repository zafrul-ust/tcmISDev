package radian.tcmis.server7.client.avcorp.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.avcorp.dbObjs.*;
import radian.tcmis.server7.client.avcorp.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class AvcorpMaterialRequest extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AvcorpTcmISDBModel db = null;
      try {
        db = new AvcorpTcmISDBModel("Avcorp",(String)request.getHeader("logon-version"));
        MaterialRequest obj = new MaterialRequest((ServerResourceBundle) new AvcorpServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}