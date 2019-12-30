package radian.tcmis.server7.client.dcx.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.dcx.dbObjs.*;
import radian.tcmis.server7.client.dcx.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class DCXMaterialRequest extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DCXTcmISDBModel db = null;
      try {
        db = new DCXTcmISDBModel("DCX",(String)request.getHeader("logon-version"));
        MaterialRequest obj = new MaterialRequest((ServerResourceBundle) new DCXServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}