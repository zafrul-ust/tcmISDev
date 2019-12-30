package radian.tcmis.server7.client.bl.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.bl.dbObjs.BLTcmISDBModel;
import radian.tcmis.server7.client.bl.helpers.BLServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class BLWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      BLTcmISDBModel db = null;
      try {
        db = new BLTcmISDBModel("BL",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new BLServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}