package radian.tcmis.server7.client.volvo.servlets;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.volvo.dbObjs.*;
import radian.tcmis.server7.client.volvo.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class VolvoMaterialMatrix extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      VolvoTcmISDBModel db = null;
      try {
        db = new VolvoTcmISDBModel("Volvo",(String)request.getHeader("logon-version"));
        MaterialMatrix obj = new MaterialMatrix((ServerResourceBundle) new VolvoServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}