package radian.tcmis.server7.client.cal.servlets;

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
import radian.tcmis.server7.client.cal.dbObjs.*;
import radian.tcmis.server7.client.cal.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class CALscannerDownload extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      CALTcmISDBModel db = null;
      try {
        db = new CALTcmISDBModel("CAL",(String)request.getHeader("logon-version"));
        scannerDownload obj = new scannerDownload((ServerResourceBundle) new CALServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}