package radian.tcmis.server7.client.hexcel.servlets;

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
import radian.tcmis.server7.client.hexcel.dbObjs.*;
import radian.tcmis.server7.client.hexcel.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class HexcelWasteAhocReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HexcelTcmISDBModel db = null;
      try {
        db = new HexcelTcmISDBModel("Hexcel",(String)request.getHeader("logon-version"));
        WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new HexcelServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}