package radian.tcmis.server7.client.ommc.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ommc.dbObjs.*;
import radian.tcmis.server7.client.ommc.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class OMMCPersonnelInfo extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      OMMCTcmISDBModel db = null;
      try {
        db = new OMMCTcmISDBModel("OMMC",(String)request.getHeader("logon-version"));
        PersonnelInfo obj = new PersonnelInfo((ServerResourceBundle) new OMMCServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

