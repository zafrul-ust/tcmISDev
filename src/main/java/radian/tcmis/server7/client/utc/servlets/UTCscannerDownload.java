package radian.tcmis.server7.client.utc.servlets;

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

import radian.tcmis.server7.client.utc.dbObjs.UTCTcmISDBModel;
import radian.tcmis.server7.client.utc.helpers.UTCServerResourceBundle;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.servlets.scannerDownload;


public class UTCscannerDownload extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      UTCTcmISDBModel db = null;
      try {
        db = new UTCTcmISDBModel();
        scannerDownload obj = new scannerDownload((ServerResourceBundle) new UTCServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}