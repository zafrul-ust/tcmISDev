package radian.tcmis.server7.client.seagate.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.seagate.dbObjs.*;
import radian.tcmis.server7.client.seagate.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class SeagateNewReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SeagateTcmISDBModel db = null;
      try {
        db = new SeagateTcmISDBModel();
        NewReport obj = new NewReport((ServerResourceBundle) new SeagateServerResourceBundle(),db);
        obj.setResponse(response);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}