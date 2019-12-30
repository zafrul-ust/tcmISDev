package radian.tcmis.server7.client.fec.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.fec.dbObjs.*;
import radian.tcmis.server7.client.fec.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class FECMaterialRequest extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      FECTcmISDBModel db = null;
      try {
        db = new FECTcmISDBModel("FEC",(String)request.getHeader("logon-version"));
        MaterialRequest obj = new MaterialRequest((ServerResourceBundle) new FECServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}