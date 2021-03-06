package radian.tcmis.server7.client.bl.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.bl.dbObjs.*;
import radian.tcmis.server7.client.bl.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class BLReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      BLTcmISDBModel db = null;
      try {
        db = new BLTcmISDBModel("BL",(String)request.getHeader("logon-version"));
        Report obj = new Report((ServerResourceBundle) new BLServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}