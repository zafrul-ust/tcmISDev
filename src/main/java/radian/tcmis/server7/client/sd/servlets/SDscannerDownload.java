package radian.tcmis.server7.client.sd.servlets;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.sd.dbObjs.SDTcmISDBModel;
import radian.tcmis.server7.client.sd.helpers.SDServerResourceBundle;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.servlets.scannerDownload;


public class SDscannerDownload extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SDTcmISDBModel db = null;
      try {
        db = new SDTcmISDBModel();
        scannerDownload obj = new scannerDownload((ServerResourceBundle) new SDServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}