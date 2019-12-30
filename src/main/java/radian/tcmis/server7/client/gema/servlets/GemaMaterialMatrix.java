package radian.tcmis.server7.client.gema.servlets;

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
import radian.tcmis.server7.client.gema.dbObjs.*;
import radian.tcmis.server7.client.gema.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GemaMaterialMatrix extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GemaTcmISDBModel db = null;
      try {
        db = new GemaTcmISDBModel("GEMA",(String)request.getHeader("logon-version"));
        MaterialMatrix obj = new MaterialMatrix((ServerResourceBundle) new GemaServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}