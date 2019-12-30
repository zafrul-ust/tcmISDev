package radian.tcmis.server7.share.servlets;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

//ACJEngine
import java.io.OutputStream;
import java.util.Random;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.share.helpers.POSPickReceipt;

public class POSPickReportGenerator {
    //ACJEngine
    private ACJEngine erw = null;
    private OutputStream os = null;
    private TemplateManager tm = null;
    private ACJOutputProcessor ec = null;
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public POSPickReportGenerator() {
    }

  public String buildReport(Vector pickData, String clerkName, String customerName, String hub, String inventoryGroup, String facilityID) {
    String url = "";
	Random rand = new Random();
	int tmpReq = rand.nextInt();
	Integer tmpReqNum = new Integer(tmpReq);

    try {
      erw = new ACJEngine();
      erw.setDebugMode(true);
      erw.setX11GfxAvailibility(false);
      erw.setTargetOutputDevice(ACJConstants.PDF);
      ec = new ACJOutputProcessor();

      String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
      ec.setPathForFontMapFile(fontmappath);

      //loading template
      String templatpath=radian.web.tcmisResourceLoader.getreportstempplatepath();
      erw.readTemplate(""+templatpath+"POSPickReceipt.erw");

      //modifying template
      tm = erw.getTemplateManager();
      tm.setLabel("LBL010",hub);
      tm.setLabel("LBL014",inventoryGroup);
      tm.setLabel("LBL002",clerkName);
      tm.setLabel("LBL004",customerName);
      tm.setLabel("LBL015",facilityID);

      //register table...
	  reoprtlog.info("buildOrderStatus   Start "+tmpReqNum.toString()+"   Size   "+pickData.size()+"");
	  String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
	  //erw.setCacheOption(true, ""+tempwritefilepath+"POSPickReceipt"+tmpReqNum.toString()+".joi");
      AppDataHandler ds = new AppDataHandler();
      RegisterTable[] rt = getData(pickData);
      for(int i=0;i<rt.length;i++) {
        Vector v1 = rt[i].getData();
        Vector v2 = rt[i].getMethods();
        ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
      }
      erw.setDataSource(ds);
      //generating report
      ec.setReportData(erw.generateReport());

      ec.setPDFProperty("FileName",""+tempwritefilepath+"POSPickReceipt"+tmpReqNum.toString()+".pdf");
      ec.setPDFProperty("ZipCompressed",new Boolean(false));
      ec.generatePDF();

      //send url back to client to open
      String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
      url = tempurlpath + "POSPickReceipt"+tmpReqNum.toString()+".pdf";
    }catch (Exception e) {
      e.printStackTrace();
      url = "";
    }
    return url;
  }

  public RegisterTable[] getData(Vector reportData1) throws Exception{
      boolean result = true;
      RegisterTable[] rt = new RegisterTable[1];
      try {
        rt[0] = new RegisterTable(POSPickReceipt.getVector(reportData1),"POSPICKRECEIPT",POSPickReceipt.getFieldVector(),null);
      }catch(Exception e1) {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
  }

}


