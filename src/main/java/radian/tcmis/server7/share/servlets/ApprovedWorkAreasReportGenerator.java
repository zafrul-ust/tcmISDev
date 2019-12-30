package radian.tcmis.server7.share.servlets;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 * 06-25-04 Adding log statements to trace a memory usage issue
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
import radian.tcmis.server7.share.helpers.ApprovedWorkAreas;

public class ApprovedWorkAreasReportGenerator {
    //ACJEngine
    private ACJEngine erw = null;
    private OutputStream os = null;
    private TemplateManager tm = null;
    private ACJOutputProcessor ec = null;
    private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

    public ApprovedWorkAreasReportGenerator() {
    }

  public String buildReport(Vector chemicalData, String catalogID, String facName, String partNo, Boolean allCatalog) {
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
      if (allCatalog.booleanValue()) {
        erw.readTemplate(""+templatpath+"ApprovedWorkAreasAllCatalog.erw");
        //modifying template
        tm = erw.getTemplateManager();
        tm.setLabel("LBL01",catalogID);
        tm.setLabel("LBL014",partNo);
      }else {
        erw.readTemplate(""+templatpath+"ApprovedWorkAreas.erw");
        //modifying template
        tm = erw.getTemplateManager();
        tm.setLabel("LBL01",catalogID);
        tm.setLabel("LBL010",facName);
        tm.setLabel("LBL014",partNo);
      }
      //register table...
	  reoprtlog.info("buildReport    Start "+tmpReqNum.toString()+"    Size: "+chemicalData.size()+" ");

      AppDataHandler ds = new AppDataHandler();
      RegisterTable[] rt = getData(chemicalData);
      for(int i=0;i<rt.length;i++) {
        Vector v1 = rt[i].getData();
        Vector v2 = rt[i].getMethods();
        ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
      }
	  //generating report
	  String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
	  //erw.setCacheOption(true, ""+tempwritefilepath+"ApprovedWorkAreas"+tmpReqNum.toString()+".joi");

      erw.setDataSource(ds);
      ec.setReportData(erw.generateReport());
      ec.setPDFProperty("FileName",""+tempwritefilepath+"ApprovedWorkAreas"+tmpReqNum.toString()+".pdf");
      ec.setPDFProperty("ZipCompressed",new Boolean(false));
      ec.generatePDF();

      //send url back to client to open
      String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
      url = tempurlpath + "ApprovedWorkAreas"+tmpReqNum.toString()+".pdf";
    }catch (Exception e) {
      e.printStackTrace();
      url = "";
    }
	reoprtlog.info("buildReport    Done "+tmpReqNum.toString()+"");
    return url;
  }

  public RegisterTable[] getData(Vector reportData1) throws Exception{
      boolean result = true;
      RegisterTable[] rt = new RegisterTable[1];
      try {
        rt[0] = new RegisterTable(ApprovedWorkAreas.getVector(reportData1),"APPROVEDWORKAREAS",ApprovedWorkAreas.getFieldVector(),null);
      }catch(Exception e1) {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
  }

}


