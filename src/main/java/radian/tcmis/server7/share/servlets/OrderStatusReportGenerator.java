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
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.OrderStatus;
import radian.tcmis.both1.reports.RegisterTable;

public class OrderStatusReportGenerator {
    //ACJEngine
    private ACJEngine erw = null;
    private OutputStream os = null;
    private TemplateManager tm = null;
    private ACJOutputProcessor ec = null;
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public OrderStatusReportGenerator() {
    }

  public String buildOrderStatus(Vector mrAllocationInfo, String facility, String requestor) {
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
      erw.readTemplate(""+templatpath+"OrderStatus.erw");

      //modifying template
      tm = erw.getTemplateManager();
      tm.setLabel("LBL002",facility);
      tm.setLabel("LBL004",requestor);

      //merging the data myself
      String lastMRLine = "";
      for (int j = 0; j < mrAllocationInfo.size(); j++) {
        Hashtable h = (Hashtable)mrAllocationInfo.elementAt(j);
        String currentMRLine = (String)h.get("MR_LINE");
        if (lastMRLine.equalsIgnoreCase(currentMRLine)) {
          h.put("MR_LINE","");
          h.put("WORK_AREA","");
          h.put("CAT_PART_NO","");
          h.put("ITEM_TYPE","");
          if (!"Schedule".equalsIgnoreCase((String)h.get("DELIVERY_TYPE"))) {
            h.put("NEED_DATE", "");
            h.put("ORDERED_QTY", "");
          }
          h.put("DELIVERY_TYPE","");
        }
        lastMRLine = currentMRLine;
      }
	  reoprtlog.info("buildOrderStatus   Start "+tmpReqNum.toString()+"   Size   "+mrAllocationInfo.size()+"");
	  String tempwritefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
	  //erw.setCacheOption(true, ""+tempwritefilepath+"OrderStatus"+tmpReqNum.toString()+".joi");
      //register table...
      AppDataHandler ds = new AppDataHandler();
      RegisterTable[] rt = getData(mrAllocationInfo);
      for(int i=0;i<rt.length;i++) {
        Vector v1 = rt[i].getData();
        Vector v2 = rt[i].getMethods();
        ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
      }
      erw.setDataSource(ds);

      //generating report
      ec.setReportData(erw.generateReport());
      ec.setPDFProperty("FileName",""+tempwritefilepath+"OrderStatus"+tmpReqNum.toString()+".pdf");
      ec.setPDFProperty("ZipCompressed",new Boolean(false));
      ec.generatePDF();

      //send url back to client to open
	  String tempurlpath=radian.web.tcmisResourceLoader.gettempreporturl();
      url = tempurlpath + "OrderStatus"+tmpReqNum.toString()+".pdf";
    }catch (Exception e) {
      e.printStackTrace();
      url = "";
    }
	reoprtlog.info("buildOrderStatus    Done  "+tmpReqNum.toString()+"");
    return url;
  }

  public RegisterTable[] getData(Vector reportData1) throws Exception{
      boolean result = true;
      RegisterTable[] rt = new RegisterTable[1];
      try {
        rt[0] = new RegisterTable(OrderStatus.getVector(reportData1),"ORDERSTATUS",OrderStatus.getFieldVector(),null);
      }catch(Exception e1) {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
  }

}


