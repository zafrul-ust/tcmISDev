package com.tcmis.client.waste.process;

import org.apache.commons.logging.*;
import com.tcmis.common.framework.*;
import java.util.Collection;
import java.io.File;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.util.*;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;

import com.tcmis.internal.hub.erw.RegisterTable;
import com.tcmis.client.waste.erw.ManifestPdfReport;
import com.tcmis.client.waste.beans.ManifestReportInputBean;
import com.tcmis.client.waste.factory.WasteLocationBeanFactory;
import com.tcmis.client.waste.factory.WasteManifestViewBeanFactory;
import com.tcmis.client.waste.beans.WasteManifestViewBean;

/******************************************************************************
 * Process for TDSF Report
 * @version 1.0
 *****************************************************************************/
public class ManifestReportProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ManifestReportProcess(String client) {
    super(client);
  }

  public Collection getManifestDropdownData(int userId) throws BaseException {
    Collection manifestDropdownColl = null;
    DbManager dbManager = new DbManager(this.getClient());
    try {
      WasteLocationBeanFactory factory = new WasteLocationBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("tsdf", SearchCriterion.EQUALS,"Y");
      manifestDropdownColl = factory.select(criteria);

    } catch (Exception e) {
      log.error("Base Exception in getManifestDropdown: " + e);
    } finally {
      dbManager = null;
    }

    return manifestDropdownColl;
  } //end of method

  public Collection getManifestReportData(ManifestReportInputBean inputBean) throws BaseException {
    Collection ilmBeans = null;
    DbManager dbManager = new DbManager(this.getClient());
    try {
      WasteManifestViewBeanFactory factory = new WasteManifestViewBeanFactory(dbManager);
      ilmBeans = factory.select2(inputBean.getManifest());
    } catch (Exception e) {
      log.error("Base Exception in getTsdfReport: " + e);
    } finally {
      dbManager = null;
    }

    return ilmBeans;
  } //end of method

  public String getPdfReport(ManifestReportInputBean bean) throws NoDataException, BaseException, Exception {
    String url = "";
    url = generatePdfReport(getManifestReportData(bean),bean);
    return url;
  } //end of method

  String generatePdfReport(Collection data,ManifestReportInputBean inputBean ) throws Exception {
    String url = "";
    Random rand = new Random();
    ResourceLibrary resource = new ResourceLibrary("report");
    String fileName = "";
    String templateName = "";

    fileName = "manifestReport"+rand.nextInt();
    templateName = resource.getString("template.wastesubmanifestreport");

    File dir = new File(resource.getString("server.path"));
    File file = File.createTempFile(fileName, ".pdf", dir);

    try {
      ACJEngine en = new ACJEngine();
      en.setDebugMode(true);
      en.setX11GfxAvailibility(false);
      en.setTargetOutputDevice(ACJConstants.PDF);

      ACJOutputProcessor eClient = new ACJOutputProcessor();
      String fontMapPath = resource.getString("font.path");
      String templatPath = resource.getString("template.path");
      String writeFilePath = resource.getString("server.path");
      en.setCacheOption(true, "" + writeFilePath + file.getName() + ".joi");
      eClient.setPathForFontMapFile(fontMapPath);

      en.readTemplate(templatPath + templateName);
      TemplateManager tm = en.getTemplateManager();
      tm.setLabel("TSDFL",inputBean.getTsdf());
      tm.setLabel("MANIFESTL",inputBean.getManifest());

      try {
        AppDataHandler ds = new AppDataHandler();
        RegisterTable[] rt = new RegisterTable[1];
        rt = registerManifestReportData(data);
        for (int i = 0; i < rt.length; i++) {
          Vector v1 = rt[i].getData();
          Vector v2 = rt[i].getMethods();
          ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
        }
        en.setDataSource(ds);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }

      eClient.setReportData(en.generateReport());
      eClient.setPDFProperty("FileName", "" + writeFilePath + file.getName());
      eClient.generatePDF();
      url = resource.getString("hosturl") + resource.getString("urlpath") + file.getName();
    } catch (Exception ex1) {
      ex1.printStackTrace();
      throw ex1;
    }
    return url;
  } //end of method

  public RegisterTable[] registerManifestReportData(Collection manifestReportData) throws Exception {
    boolean result = true;
    RegisterTable[] rt = new RegisterTable[1];
    try {
      rt[0] = new RegisterTable(ManifestPdfReport.getVector(manifestReportData), "MANIFESTREPORT", ManifestPdfReport.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  } //end of method


} //end of class