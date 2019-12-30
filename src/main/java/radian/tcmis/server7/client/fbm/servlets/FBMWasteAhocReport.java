package radian.tcmis.server7.client.fbm.servlets;

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
import radian.tcmis.server7.client.fbm.dbObjs.*;
import radian.tcmis.server7.client.fbm.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class FBMWasteAhocReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      FBMTcmISDBModel db = null;
      try {
        db = new FBMTcmISDBModel("FBM",(String)request.getHeader("logon-version"));
        WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new FBMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}