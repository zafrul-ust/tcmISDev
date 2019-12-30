package com.tcmis.client.waste.process;

import javax.servlet.http.*;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;

import org.apache.commons.logging.*;
import com.tcmis.common.framework.*;
import java.util.Collection;
import java.io.File;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.util.*;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.internal.hub.erw.RegisterTable;
import com.tcmis.client.waste.erw.WasteTraveler;
import com.tcmis.client.waste.erw.HazardousWaste;
import com.tcmis.client.waste.factory.WasteTravelerViewBeanFactory;
import com.tcmis.client.waste.factory.WstContCaHazLabelViewBeanFactory;

/******************************************************************************
 * Process for print label
 * @version 1.0
 *****************************************************************************/
public class PrintWasteLabelProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PrintWasteLabelProcess(String client) {
    super(client);
  }

  public String printLabel(HttpServletRequest request) throws BaseException {
    String url = "";
    String labelType = request.getParameter("labelType");
    try {
      Integer numberOfContainer = new Integer(request.getParameter("num"));
      Vector containerId = new Vector(numberOfContainer.intValue());
      for (int i = 0; i < numberOfContainer.intValue(); i++) {
        containerId.addElement(request.getParameter("id" + i));
      }
      //type of label
      if ("hazardous".equalsIgnoreCase(labelType)) {
        url = buildHazardousLabel(containerId);
      } else {
        url = buildWasteTraveler(containerId);
      }

    } catch (Exception e) {
      log.error("Error printing waste label:"+labelType, e);
      throw new BaseException(e);
    }
    return url;
  } //end of method

  String buildHazardousLabel(Vector containerId) {
    String url = "";
    try {
      //get data and generate label
      url = generateLabel(getHazardousData(containerId),"hazardous");
    }catch (Exception e) {
      e.printStackTrace();
    }
    return url;
  } //end of method

  String buildWasteTraveler(Vector containerId) {
    String url = "";
    try {
      //get data and generate labels
      url = generateLabel(getWasteTravelerData(containerId),"traveler");
    }catch (Exception e) {
      e.printStackTrace();
    }
    return url;
  } //end of method

  Collection getHazardousData(Vector containerId)  throws Exception {
    Collection result = new Vector();
    try {
      String containers = "";
      for (int i = 0; i < containerId.size(); i++) {
        containers += containerId.elementAt(i).toString()+",";
      }
      //removing last ","
      if (containers.length() > 1) {
        containers = containers.substring(0,containers.length()-1);
        DbManager dbManager = new DbManager(getClient());
        WstContCaHazLabelViewBeanFactory factory = new WstContCaHazLabelViewBeanFactory(dbManager);
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("containerId", SearchCriterion.IN, containers);
        result = factory.select(criteria);
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return result;
  } //end of method

  Collection getWasteTravelerData(Vector containerId)  throws Exception {
    Collection result = new Vector();
    try {
      String containers = "";
      for (int i = 0; i < containerId.size(); i++) {
        containers += containerId.elementAt(i).toString()+",";
      }
      //removing last ","
      if (containers.length() > 1) {
        containers = containers.substring(0,containers.length()-1);
        DbManager dbManager = new DbManager(getClient());
        WasteTravelerViewBeanFactory factory = new WasteTravelerViewBeanFactory(dbManager);
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("containerId", SearchCriterion.IN, containers);
        result = factory.select(criteria);
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return result;
  } //end of method

  String generateLabel(Collection labelData, String labelType) throws Exception {
    String url = "";
    Random rand = new Random();
    ResourceLibrary resource = new ResourceLibrary("label");
    String fileName = "";
    String templateName = "";
    if ("traveler".equalsIgnoreCase(labelType)) {
      fileName = "wastetraveler"+rand.nextInt();
      templateName = resource.getString("label.template.wastetraveler");
    }else {
      fileName = "hazardouswaste"+rand.nextInt();
      templateName = resource.getString("label.template.hazardouswaste");
    }

    File dir = new File(resource.getString("label.serverpath"));
    File file = File.createTempFile(fileName, ".pdf", dir);

    try {
      ACJEngine en = new ACJEngine();
      en.setDebugMode(true);
      en.setX11GfxAvailibility(false);
      en.setTargetOutputDevice(ACJConstants.PDF);

      ACJOutputProcessor eClient = new ACJOutputProcessor();
      String fontMapPath = resource.getString("label.font.path");
      String templatPath = resource.getString("label.template.path");
      String writeFilePath = resource.getString("label.serverpath");
      en.setCacheOption(true, "" + writeFilePath + file.getName() + ".joi");
      eClient.setPathForFontMapFile(fontMapPath);

      en.readTemplate(templatPath + templateName);

      try {
        AppDataHandler ds = new AppDataHandler();
        RegisterTable[] rt = new RegisterTable[1];
        if ("traveler".equalsIgnoreCase(labelType)) {
          rt = getWasteTravelerData(labelData);
        }else {
          rt = getHazardousWasteData(labelData);
        }
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
      url = resource.getString("label.hosturl") + resource.getString("label.urlpath") + file.getName();
    } catch (Exception ex1) {
      ex1.printStackTrace();
      throw ex1;
    }
    return url;
  } //end of method

  public RegisterTable[] getHazardousWasteData(Collection labelData) throws Exception {
    boolean result = true;
    RegisterTable[] rt = new RegisterTable[1];
    try {
      rt[0] = new RegisterTable(HazardousWaste.getVector(labelData), "HAZARDOUSWASTE", HazardousWaste.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  } //end of method

  public RegisterTable[] getWasteTravelerData(Collection labelData) throws Exception {
    boolean result = true;
    RegisterTable[] rt = new RegisterTable[1];
    try {
      rt[0] = new RegisterTable(WasteTraveler.getVector(labelData), "WASTETRAVELER", WasteTraveler.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  } //end of method

} //end of class
