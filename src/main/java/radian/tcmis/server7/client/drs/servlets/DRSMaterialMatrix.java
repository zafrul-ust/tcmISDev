package radian.tcmis.server7.client.drs.servlets;
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
import radian.tcmis.server7.client.drs.dbObjs.*;
import radian.tcmis.server7.client.drs.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class DRSMaterialMatrix extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DRSTcmISDBModel db = null;
      try {
        //db = new DRSTcmISDBModel();
        db = new DRSTcmISDBModel("DRS",(String)request.getHeader("logon-version"));
        MaterialMatrix obj = new MaterialMatrix((ServerResourceBundle) new DRSServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}