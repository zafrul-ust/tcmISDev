package radian.tcmis.server7.client.getrag.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.getrag.dbObjs.*;
import radian.tcmis.server7.client.getrag.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GetragLoadChoices extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GetragTcmISDBModel db = null;
      try {
        db = new GetragTcmISDBModel("Getrag",(String)request.getHeader("logon-version"));
        LoadChoices obj = new LoadChoices((ServerResourceBundle) new GetragServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}