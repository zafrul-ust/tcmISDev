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
import radian.tcmis.server7.share.helpers.WorkAreaComments;

public class WorkAreaCommentsReportGenerator {
    //ACJEngine
    private ACJEngine erw = null;
    private OutputStream os = null;
    private TemplateManager tm = null;
    private ACJOutputProcessor ec = null;
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public WorkAreaCommentsReportGenerator() {
    }

  public String buildReport(Vector chemicalData, String facName, String workAreaDesc, String catalogDesc, String partNo) {
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
      erw.readTemplate(""+templatpath+"WorkAreaComments.erw");
      //modifying template
      tm = erw.getTemplateManager();
      tm.setLabel("LBL010",facName);
      tm.setLabel("LBL007",workAreaDesc);
      tm.setLabel("LBL01",catalogDesc);
      tm.setLabel("LBL014",partNo);
      //register table...
	  reoprtlog.info("buildReport    Start "+tmpReqNum.toString()+"    Size: "+chemicalData.size()+" ");
	  String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
	  erw.setCacheOption(true, ""+tempwritefilepath+"WorkAreaComments"+tmpReqNum.toString()+".joi");

      AppDataHandler ds = new AppDataHandler();
      RegisterTable[] rt = getData(chemicalData);
      for(int i=0;i<rt.length;i++) {
        Vector v1 = rt[i].getData();
        Vector v2 = rt[i].getMethods();
        ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
      }
      erw.setDataSource(ds);
      //generating report
      ec.setReportData(erw.generateReport());
      ec.setPDFProperty("FileName",""+tempwritefilepath+"WorkAreaComments"+tmpReqNum.toString()+".pdf");
      ec.setPDFProperty("ZipCompressed",new Boolean(false));
      ec.generatePDF();

      //send url back to client to open
      String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
      url = tempurlpath + "WorkAreaComments"+tmpReqNum.toString()+".pdf";
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
        rt[0] = new RegisterTable(WorkAreaComments.getVector(reportData1),"WORKAREACOMMENTS",WorkAreaComments.getFieldVector(),null);
      }catch(Exception e1) {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
  }

}


