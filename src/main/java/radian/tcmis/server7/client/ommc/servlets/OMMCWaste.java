package radian.tcmis.server7.client.ommc.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ommc.dbObjs.OMMCTcmISDBModel;
import radian.tcmis.server7.client.ommc.helpers.OMMCServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class OMMCWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      OMMCTcmISDBModel db = null;
      try {
        db = new OMMCTcmISDBModel("OMMC",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new OMMCServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}