package radian.tcmis.server7.client.goodrich.servlets;

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
import radian.tcmis.server7.client.goodrich.dbObjs.*;
import radian.tcmis.server7.client.goodrich.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GoodrichscannerDownload extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GoodrichTcmISDBModel db = null;
      try {
        db = new GoodrichTcmISDBModel("Goodrich",(String)request.getHeader("logon-version"));
        scannerDownload obj = new scannerDownload((ServerResourceBundle) new GoodrichServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}