package radian.tcmis.server7.client.fec.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.fec.dbObjs.FECTcmISDBModel;
import radian.tcmis.server7.client.fec.helpers.FECServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class FECWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      FECTcmISDBModel db = null;
      try {
        db = new FECTcmISDBModel("FEC",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new FECServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}