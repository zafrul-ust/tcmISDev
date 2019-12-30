package com.tcmis.internal.hub.process;

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
import java.util.Iterator;
import java.util.Collection;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.internal.hub.erw.ConsolidatedBolDetail;
import com.tcmis.internal.hub.erw.ConsolidatedBolHeader;
import com.tcmis.internal.hub.erw.RegisterTable;
import com.tcmis.internal.hub.factory.ConsolidatedBolDetailViewBeanFactory;
import com.tcmis.internal.hub.factory.ConsolidatedBolHeaderViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.util.CombinePdf;
import com.tcmis.common.framework.BaseProcess;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.ResourceLibrary;


public class ConsolidatedBolProcess extends BaseProcess {
    //ACJEngine
    private ACJEngine erw = null;
    private OutputStream os = null;
    private TemplateManager tm = null;
    private ACJOutputProcessor ec = null;

    Log log = LogFactory.getLog(this.getClass());

    public ConsolidatedBolProcess(String client) {
      super(client);
    }

  public String load(String[] shipmentId) {
    String url = "";
    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);
    try {
      String[] pdfFileName = new String[shipmentId.length];
      for (int i = 0; i < shipmentId.length; i++) {
        String[] tmpShipmentId = new String[1];
        tmpShipmentId[0] = shipmentId[i];
        pdfFileName[i] = createPdfFile(tmpShipmentId);
      }
      //only append pfd if user pick more than one shipment
      ResourceLibrary resource = new ResourceLibrary("report");
      String tempurlpath = resource.getString("report.hosturl") + resource.getString("report.urlpath");
      String tempwritefilepath = resource.getString("report.serverpath");
      if (pdfFileName.length > 1) {
        String baseName = "ConsolidatedBol" + tmpReqNum.toString() + ".pdf";
        String appendedFileName = tempwritefilepath+baseName;
        for (int j = 0; j < pdfFileName.length; j++) {
          pdfFileName[j] = tempwritefilepath+pdfFileName[j];
        }
        //pdfFileName[0] = "/webdata/html/reports/temp/trong3.pdf";
        //pdfFileName[1] = "/webdata/html/reports/temp/trong4.pdf";
        CombinePdf.appendPdf(pdfFileName, appendedFileName);
        url = tempurlpath+baseName;
      }else {
        url = tempurlpath + pdfFileName[0];
      }
    }catch (Exception e) {
      e.printStackTrace();
      url = "";
    }
    return url;
  } //end of method

  public String createPdfFile(String[] shipmentId) {
    String fileName = "";
    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);

    try {
      //first get data
      Collection headerData = getHeaderData(shipmentId);
      Collection detailData = getDetailData(shipmentId);
      Collection totalNetWeightForMajorHazardClass = getTotalNetWeightForMajorHazardClass(shipmentId);
      //get template name
      String templateName = "ConsolidatedBOL.erw";

      erw = new ACJEngine();
      erw.setDebugMode(true);
      erw.setX11GfxAvailibility(false);
      erw.setTargetOutputDevice(ACJConstants.PDF);
      ec = new ACJOutputProcessor();

      ResourceLibrary resource = new ResourceLibrary("report");
      String fontmappath = resource.getString("report.font.path");
      ec.setPathForFontMapFile(fontmappath);

      //loading template
      String templatpath = resource.getString("report.template.path");
      erw.readTemplate(""+templatpath+templateName);
      //modifying template
      tm = erw.getTemplateManager();

      //register table...
			String tempwritefilepath = resource.getString("report.serverpath");
      //erw.setCacheOption(true, ""+tempwritefilepath+"ConsolidatedBol"+tmpReqNum.toString()+shipmentId[0]+".joi");

      AppDataHandler ds = new AppDataHandler();
      RegisterTable[] rt = getData(headerData,detailData,totalNetWeightForMajorHazardClass);
      for(int i=0;i<rt.length;i++) {
        Vector v1 = rt[i].getData();
        Vector v2 = rt[i].getMethods();
        ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
      }
      erw.setDataSource(ds);
      //generating report
      ec.setReportData(erw.generateReport());
      ec.setPDFProperty("FileName",""+tempwritefilepath+"ConsolidatedBol"+tmpReqNum.toString()+shipmentId[0]+".pdf");
      ec.setPDFProperty("ZipCompressed",new Boolean(false));
      ec.generatePDF();

      /*send url back to client to open
      String tempurlpath = resource.getString("report.url");
      fileName = tempurlpath + "ConsolidatedBol"+tmpReqNum.toString()+shipmentId[0]+".pdf";
      */

      //because I am appending pdf files together I have to past back physical location
      fileName = "ConsolidatedBol"+tmpReqNum.toString()+shipmentId[0]+".pdf";
    }catch (Exception e) {
      e.printStackTrace();
      fileName = "";
    }finally {
      try {
        erw.disconnect();
      }catch (Exception ee) {
        ee.printStackTrace();
      }
    }
    return fileName;
  } //end of method

  Collection getHeaderData(String[] shipmentId) throws Exception {
    Collection col = null;
    try {
      DbManager dbManager = new DbManager(getClient());
      ConsolidatedBolHeaderViewBeanFactory factory = new ConsolidatedBolHeaderViewBeanFactory(dbManager);
      col = factory.selectForConsolidatedBol(shipmentId);
    }catch (Exception e) {
      throw e;
    }
    return col;
  } //end of method

  Collection getDetailData(String[] shipmentId) throws Exception {
    Collection col = null;
    try {
      DbManager dbManager = new DbManager(getClient());
      ConsolidatedBolDetailViewBeanFactory factory = new ConsolidatedBolDetailViewBeanFactory(dbManager);
      col = factory.selectForConsolidatedBol(shipmentId);
    }catch (Exception e) {
      throw e;
    }
    return col;
  } //end of method

  Collection getTotalNetWeightForMajorHazardClass(String[] shipmentId) throws Exception {
    Collection col = null;
    try {
      DbManager dbManager = new DbManager(getClient());
      ConsolidatedBolDetailViewBeanFactory factory = new ConsolidatedBolDetailViewBeanFactory(dbManager);
      col = factory.selectForTotalNetWeightMajorHazardClass(shipmentId);
    }catch (Exception e) {
      throw e;
    }
    return col;
  } //end of method


  public RegisterTable[] getData(Collection headerData,Collection detailData,Collection totalNetWeightForMajorHazardClass) throws Exception{
      boolean result = true;
      RegisterTable[] rt = new RegisterTable[3];
      try {
        rt[0] = new RegisterTable(ConsolidatedBolHeader.getVector(headerData),"CONSOLIDATEDBOLSUPERHEADER",ConsolidatedBolHeader.getFieldVector(),null);
        rt[1] = new RegisterTable(ConsolidatedBolHeader.getVector(headerData),"CONSOLIDATEDBOLHEADER",ConsolidatedBolHeader.getFieldVector(),null);
        rt[2] = new RegisterTable(ConsolidatedBolDetail.getVector(detailData,totalNetWeightForMajorHazardClass),"CONSOLIDATEDBOLDETAIL",ConsolidatedBolDetail.getFieldVector(),null);

      }catch(Exception e1) {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
  }

}

