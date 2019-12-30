package com.tcmis.client.report.process;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.Random;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.admin.beans.KeyValuePairBean;
import com.tcmis.client.report.beans.FormattedScaqmdInputBean;
import com.tcmis.client.report.beans.TcmisConstantBean;
import com.tcmis.client.report.factory.TcmisConstantBeanFactory;
import com.tcmis.internal.hub.erw.RegisterTable;
import com.tcmis.client.report.erw.ScaqmdMasterList;
import com.tcmis.client.report.erw.ScaqmdRule109;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.admin.factory.PersonnelUserGroupViewBeanFactory;
import com.tcmis.client.report.factory.ScaqmdMasterListViewBeanFactory;
import com.tcmis.client.report.beans.ScaqmdMasterListViewBean;
import com.tcmis.client.report.beans.ScaqmdRule109ViewBean;
import com.tcmis.client.report.beans.ScaqmdLastUpdatedViewBean;
import com.tcmis.client.report.factory.ScaqmdLastUpdatedViewBeanFactory;
import com.tcmis.client.report.factory.ScaqmdRule109ViewBeanFactory;
import com.tcmis.common.util.DateHandler;

/******************************************************************************
 * Process for creating Scaqmd reports
 * @version 1.0
 *****************************************************************************/
public class FormattedScaqmdProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  private ACJEngine erw = null;
  private OutputStream os = null;
  private TemplateManager tm = null;
  private ACJOutputProcessor ec = null;

  public FormattedScaqmdProcess(String client) {
    super(client);
  }

  /*public String hasPermissionToAccessReport(BigDecimal personnelId) {
    String result = "No";
    try {
      DbManager dbManager = new DbManager(getClient());
      PersonnelUserGroupViewBeanFactory personnelUserGroupViewBeanFactory = new PersonnelUserGroupViewBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("userGroupId", SearchCriterion.EQUALS, "CreateReport");
      criteria.addCriterion("facilityId", SearchCriterion.LIKE, "LAX");
      criteria.addCriterion("personnelId", SearchCriterion.EQUALS,personnelId.toString());
      Vector personnelUserGroupViewBeanCollection = new Vector(personnelUserGroupViewBeanFactory.select(criteria));
      if (personnelUserGroupViewBeanCollection.size() > 0) {
        result = "Yes";
      }
    }catch (Exception e) {
      result = "No";
    }
    return result;
  } //end of method*/

  public String generateReport(FormattedScaqmdInputBean bean) {
    String filePath = "";
    try {
      //work group
      String tempWorkGroup = "";
      if ("GSE".equalsIgnoreCase(bean.getWorkGroup())) {
        tempWorkGroup = bean.getWorkGroup();
      }else if ("All".equalsIgnoreCase(bean.getWorkGroup())) {
        tempWorkGroup = bean.getWorkGroup();
      }else {
        tempWorkGroup = "ACMX";
      }

      if ("masterList".equalsIgnoreCase(bean.getReportType())) {
        String lastUpdateRevisedDate = "";
        DbManager dbManager = new DbManager(getClient());
        ScaqmdLastUpdatedViewBeanFactory scaqmdLastUpdatedViewBeanFactory = new ScaqmdLastUpdatedViewBeanFactory(dbManager);
        SearchCriteria criteria = new SearchCriteria();
        Vector scaqmdLastUpdatedCollection = new Vector(scaqmdLastUpdatedViewBeanFactory.select(criteria));
        for (int k = 0; k < scaqmdLastUpdatedCollection.size(); k++) {
          ScaqmdLastUpdatedViewBean scaqmdLastUpdatedViewBean = (ScaqmdLastUpdatedViewBean)scaqmdLastUpdatedCollection.elementAt(k);
          lastUpdateRevisedDate = DateHandler.formatDate(scaqmdLastUpdatedViewBean.getLastUpdated(),"MM/dd/yyyy");
        }

        if ("xls".equalsIgnoreCase(bean.getReportFormat())) {
          filePath = createXlsMasterList(bean.getIncludeCommentField(),lastUpdateRevisedDate,tempWorkGroup);
        }else {
          filePath = createPdfMasterList(bean.getIncludeCommentField(),lastUpdateRevisedDate,tempWorkGroup);
        }
      } else {
        //get contact info
        DbManager dbManager = new DbManager(getClient());
        TcmisConstantBeanFactory tcmisConstantBeanFactory = new TcmisConstantBeanFactory(dbManager);
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("constant", SearchCriterion.LIKE, "SCAQMD");
        Vector tcmisConstantBeanCollection = new Vector(tcmisConstantBeanFactory.select(criteria));
        String contactName = "";
        String contactPhone = "";
        for (int i = 0; i < tcmisConstantBeanCollection.size(); i++) {
          TcmisConstantBean tcmisConstantBean = (TcmisConstantBean)tcmisConstantBeanCollection.elementAt(i);
          if ("SCAQMD Contact".equalsIgnoreCase(tcmisConstantBean.getConstant())) {
            contactName = tcmisConstantBean.getValue();
          }else if ("SCAQMD Phone".equalsIgnoreCase(tcmisConstantBean.getConstant())) {
            contactPhone = tcmisConstantBean.getValue();
          }
        }

        if ("xls".equalsIgnoreCase(bean.getReportFormat())) {
          filePath = createXlsMonthly(bean.getBeginDate(),bean.getEndDate(),contactName,contactPhone,tempWorkGroup);
        }else {
          filePath = createPdfMonthly(bean.getBeginDate(),bean.getEndDate(),contactName,contactPhone,tempWorkGroup);
        }
      }
    }catch (Exception e) {
      filePath = "";
    }
    return filePath;
  } //end of method

  public String createPdfMonthly(String beginDate, String endDate, String contactName, String contactPhone, String workGroup) {
    String url = "";
    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);
    try {
      //first get data
      Collection monthlyData = getMonthlyData(beginDate,endDate,workGroup);

      //get template name
      String templateName = "scaqmdMonthly.erw";
      erw = new ACJEngine();
      erw.setDebugMode(true);
      erw.setX11GfxAvailibility(false);
      erw.setTargetOutputDevice(ACJConstants.PDF);
      ec = new ACJOutputProcessor();

      ResourceLibrary resource = new ResourceLibrary("report");
      String fontmappath = resource.getString("font.path");
      ec.setPathForFontMapFile(fontmappath);

      //loading template
      String templatpath = resource.getString("report.scaqmd.template");
      erw.readTemplate(""+templatpath+templateName);
      //modifying template
      tm = erw.getTemplateManager();
      tm.setLabel("STARTL",beginDate);
      tm.setLabel("ENDL",endDate);
      tm.setLabel("CONTACTL",contactName);
      tm.setLabel("TELEPHONEL",contactPhone);
      tm.setLabel("WORKGROUPL",workGroup);
      //register table...
      String tempwritefilepath = resource.getString("excel.report.serverpath");
      erw.setCacheOption(true, ""+tempwritefilepath+"ScaqmdMonthly"+tmpReqNum.toString()+".joi");

      AppDataHandler ds = new AppDataHandler();
      RegisterTable[] rt = linkMonthlyData(monthlyData);
      for(int i=0;i<rt.length;i++) {
        Vector v1 = rt[i].getData();
        Vector v2 = rt[i].getMethods();
        ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
      }
      erw.setDataSource(ds);
      //generating report
      ec.setReportData(erw.generateReport());
      ec.setPDFProperty("FileName",""+tempwritefilepath+"ScaqmdMonthly"+tmpReqNum.toString()+".pdf");
      ec.setPDFProperty("ZipCompressed",new Boolean(false));
      ec.generatePDF();

      //send url back to client to open
      String tempurlpath = resource.getString("report.hosturl") + resource.getString("excel.report.urlpath");
      url = tempurlpath + "ScaqmdMonthly"+tmpReqNum.toString()+".pdf";
    }catch (Exception e) {
      e.printStackTrace();
      url = "";
    }
    return url;
  } //end of method

  public String createXlsMonthly(String beginDate, String endDate, String contactName, String contactPhone, String workGroup) {
    String url = "";
    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);
    try {
      //first get data
      Collection monthlyData = getMonthlyData(beginDate,endDate,workGroup);
      ResourceLibrary resource = new ResourceLibrary("report");
      String tempwritefilepath = resource.getString("excel.report.serverpath");
      PrintWriter pw = new PrintWriter(new FileOutputStream(tempwritefilepath+"ScaqmdMonthly"+tmpReqNum.toString()+".xls"));
      //write column headers
      pw.println("<html>");
      //report header
      pw.println("<TABLE BORDER=\"1\" CELLPADDING=\"9\">");
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>RULE 109 MATERIAL USAGE SUMMARY LOG</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>SCAQMD FACILITY ID: 000162</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      //work group
      pw.println("<TD><CENTER>");
      pw.println("<B>WORK GROUP(S): "+workGroup+"</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      //contact
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>FACILITY CONTACT: "+contactName+"</B>");
      pw.println("</CENTER></TD>");
      //month
      pw.println("<TD><CENTER>");
      pw.println("<B>BEGIN: "+beginDate+"</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>TEL NO. "+contactPhone+"</B>");
      pw.println("</CENTER></TD>");
      //year
      pw.println("<TD><CENTER>");
      pw.println("<B>END: "+endDate+"</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      Calendar calendar = Calendar.getInstance();
      pw.println("<TD><CENTER>");
      pw.println("<B>Date of Last Update: "+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.YEAR)+"</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("</TABLE>");
      //column headers
      pw.println("<table border=\"1\">");
      pw.println("<TR>");
      pw.println("<TH width=\"5%\">Date</TH>");
      pw.println("<TH width=\"5%\">For Use By (ACMX, GSE, KZ)</TH>");
      pw.println("<TH width=\"5%\">Manufacturer ID No. / M&E Number (1)</TH>");
      pw.println("<TH width=\"5%\">Part Description</TH>");
      pw.println("<TH width=\"5%\">Amount Used</TH>");
      pw.println("<TH width=\"5%\">Units (2)</TH>");
      pw.println("<TH width=\"5%\">Permit No. (if applicable)</TH>");
      pw.println("<TH width=\"5%\">VOC coating (lb/gal) (3)</TH>");
      pw.println("<TH width=\"5%\">VOC material (lb/gal) (4)</TH>");
      pw.println("<TH width=\"5%\">VOC Emissions (lb)</TH>");
      pw.println("</TR>");
      //print rows
      Iterator i11 = monthlyData.iterator();
      while (i11.hasNext()) {
        ScaqmdRule109ViewBean scaqmdRule109ViewBean = (ScaqmdRule109ViewBean) i11.next();
        pw.println("<TR>");
        if (scaqmdRule109ViewBean.getShipmentDate() != null) {
          pw.println("<TD>" + DateHandler.formatDate(scaqmdRule109ViewBean.getShipmentDate(), "MM/dd/yyyy") + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdRule109ViewBean.getForUseBy() != null) {
          pw.println("<TD>" + scaqmdRule109ViewBean.getForUseBy() + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdRule109ViewBean.getCatPartNo() != null) {
          pw.println("<TD>" + scaqmdRule109ViewBean.getCatPartNo() + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdRule109ViewBean.getPartDescription() != null) {
          pw.println("<TD>" + scaqmdRule109ViewBean.getPartDescription() + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdRule109ViewBean.getAmountUsed() != null) {
          pw.println("<TD>" + scaqmdRule109ViewBean.getAmountUsed().toString() + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdRule109ViewBean.getUnits() != null) {
          pw.println("<TD>" + scaqmdRule109ViewBean.getUnits() + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdRule109ViewBean.getPermitNo() != null) {
          pw.println("<TD>" + scaqmdRule109ViewBean.getPermitNo() + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdRule109ViewBean.getVocCoatingLbPerGal() != null) {
          pw.println("<TD>" + scaqmdRule109ViewBean.getVocCoatingLbPerGal() + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdRule109ViewBean.getVocMatlLbPerGal() != null) {
          pw.println("<TD>" + scaqmdRule109ViewBean.getVocMatlLbPerGal() + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdRule109ViewBean.getVocEmissionsLb() != null) {
          pw.println("<TD>" + scaqmdRule109ViewBean.getVocEmissionsLb() + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        pw.println("</TR>");
      }
      pw.println("</TABLE>");
      //footer notes
      pw.println("<TABLE>");
      pw.println("<TR>");
      pw.println("<TD>(1) From Master List</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>(2) Indicate Amount as oz, gal, l (liter) or ml (milliliter)</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>(3) VOC coating: VOC as applied, less exempt solvents and water</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>(4) VOC material: VOC of material as applied, including exempt compounds and water</TD>");
      pw.println("</TR>");
      pw.println("</TABLE>");
      pw.println("</html>");
      pw.close();

      //send url back to client to open
      String tempurlpath = resource.getString("report.hosturl") + resource.getString("excel.report.urlpath");
      url = tempurlpath + "ScaqmdMonthly"+tmpReqNum.toString()+".xls";
    }catch (Exception e) {
      e.printStackTrace();
      url = "";
    }
    return url;
  } //end of method

  public String createPdfMasterList(String includeCommentField, String lastUpdateRevisedDate, String workGroup) {
    String url = "";
    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);
    try {
      //first get data
      Collection masterList = getMasterListData(workGroup);
      //get template name
      String templateName = "";
      if ("Y".equalsIgnoreCase(includeCommentField)) {
        templateName = "scaqmdMasterList.erw";
      }else {
        templateName = "scaqmdMasterListWithOutComment.erw";
      }
      erw = new ACJEngine();
      erw.setDebugMode(true);
      erw.setX11GfxAvailibility(false);
      erw.setTargetOutputDevice(ACJConstants.PDF);
      ec = new ACJOutputProcessor();

      ResourceLibrary resource = new ResourceLibrary("report");
      String fontmappath = resource.getString("font.path");
      ec.setPathForFontMapFile(fontmappath);

      //loading template
      String templatpath = resource.getString("report.scaqmd.template");
      erw.readTemplate(""+templatpath+templateName);
      //modifying template
      tm = erw.getTemplateManager();
      tm.setLabel("LASTUPDATEL",lastUpdateRevisedDate);
      tm.setLabel("REVISEDL",lastUpdateRevisedDate);
      tm.setLabel("WORKGROUPL",workGroup);
      //register table...
      String tempwritefilepath = resource.getString("excel.report.serverpath");
      erw.setCacheOption(true, ""+tempwritefilepath+"ScaqmdMasterList"+tmpReqNum.toString()+".joi");

      AppDataHandler ds = new AppDataHandler();
      RegisterTable[] rt = linkMasterData(masterList);
      for(int i=0;i<rt.length;i++) {
        Vector v1 = rt[i].getData();
        Vector v2 = rt[i].getMethods();
        ds.RegisterTable(rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere());
      }
      erw.setDataSource(ds);
      //generating report
      ec.setReportData(erw.generateReport());
      ec.setPDFProperty("FileName",""+tempwritefilepath+"ScaqmdMasterList"+tmpReqNum.toString()+".pdf");
      ec.setPDFProperty("ZipCompressed",new Boolean(false));
      ec.generatePDF();

      //send url back to client to open
      String tempurlpath = resource.getString("report.hosturl") + resource.getString("excel.report.urlpath");
      url = tempurlpath + "ScaqmdMasterList"+tmpReqNum.toString()+".pdf";
    }catch (Exception e) {
      e.printStackTrace();
      url = "";
    }
    return url;
  } //end of method

  public String createXlsMasterList(String includeCommentField, String lastUpdateRevisedDate, String workGroup) {
    String url = "";
    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);
    try {
      //first get data
      Collection masterList = getMasterListData(workGroup);
      ResourceLibrary resource = new ResourceLibrary("report");
      String tempwritefilepath = resource.getString("excel.report.serverpath");
      PrintWriter pw = new PrintWriter(new FileOutputStream(tempwritefilepath+"ScaqmdMasterList"+tmpReqNum.toString()+".xls"));
      //write column headers
      pw.println("<html>");
      //report header
      pw.println("<TABLE BORDER=\"1\" CELLPADDING=\"13\">");
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>Rule 109</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>MASTER LIST OF VOC-CONTAINING MATERIALS</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>SCAQMD FACILITY ID: 000162</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>WORK GROUP(S): "+workGroup+"</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>Date of Last Update: "+lastUpdateRevisedDate+"</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD><CENTER>");
      pw.println("<B>Revised: "+lastUpdateRevisedDate+"</B>");
      pw.println("</CENTER></TD>");
      pw.println("</TR>");
      pw.println("</TABLE>");
      //column headers
      pw.println("<table border=\"1\">");
      pw.println("<TR>");
      pw.println("<TH width=\"5%\">Date Added to Inventory</TH>");
      pw.println("<TH width=\"5%\">Manufacturer</TH>");
      pw.println("<TH width=\"5%\">Part Description</TH>");
      pw.println("<TH width=\"5%\">Material Category (primer, topcoat, etc.)</TH>");
      pw.println("<TH width=\"5%\">Product ID No./M&E No.</TH>");
      pw.println("<TH width=\"5%\">VOC coating (lb/gal) (1)</TH>");
      pw.println("<TH width=\"5%\">VOC material (lb/gal) (2)</TH>");
      pw.println("<TH width=\"5%\">Mixing Ratio (3)</TH>");
      pw.println("<TH width=\"5%\">VOC Composite Vapor Pressure (mmHg)</TH>");
      pw.println("<TH width=\"5%\">For Use By (ACMX, GSE, etc)</TH>");
      pw.println("<TH width=\"5%\">Activity or Substrate Description (painting of lockers, engine stands, etc)</TH>");
      pw.println("<TH width=\"5%\">Applicable District Rule (4)</TH>");
      if ("Y".equalsIgnoreCase(includeCommentField)) {
        pw.println("<TH width=\"5%\">Comments</TH>");
      }
      pw.println("</TR>");
      //print rows
      Iterator i11 = masterList.iterator();
      while (i11.hasNext()) {
        ScaqmdMasterListViewBean scaqmdMasterListViewBean = (ScaqmdMasterListViewBean) i11.next();
        pw.println("<TR>");
        if (scaqmdMasterListViewBean.getDateAddedToInventory() != null) {
          pw.println("<TD>" + DateHandler.formatDate(scaqmdMasterListViewBean.getDateAddedToInventory(), "MM/dd/yyyy") + "</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getManufacturer() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getManufacturer()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getPartDescription() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getPartDescription()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getMaterialCategory() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getMaterialCategory()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getProductIdMmAndEeNo() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getProductIdMmAndEeNo()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getVocCoatingLbPerGal() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getVocCoatingLbPerGal()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getVocMatlLbPerGal() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getVocMatlLbPerGal()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getMixingRatio() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getMixingRatio()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getVocCompVaporPressureMmhg() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getVocCompVaporPressureMmhg()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getForUseBy() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getForUseBy()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getActivityOrSubstrateDesc() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getActivityOrSubstrateDesc()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if (scaqmdMasterListViewBean.getApplicableDistrictRule() != null) {
          pw.println("<TD>"+scaqmdMasterListViewBean.getApplicableDistrictRule()+"</TD>");
        }else {
          pw.println("<TD>&nbsp;</TD>");
        }
        if ("Y".equalsIgnoreCase(includeCommentField)) {
          if (scaqmdMasterListViewBean.getCategoryComment() != null) {
            pw.println("<TD>" + scaqmdMasterListViewBean.getCategoryComment() + "</TD>");
          } else {
            pw.println("<TD>&nbsp;</TD>");
          }
        }
        pw.println("</TR>");
      }
      pw.println("</TABLE>");
      //foot notes
      pw.println("<TABLE>");
      pw.println("<TR>");
      pw.println("<TD>(1) VOC coating: VOC as applied, less exempt solvents and water</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>(2) VOC material: VOC of material as applied, including exempt compounds and water</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>(3) If applicable, lowest volume component = 1, other components as ratio of lowest volume component</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>(4) Applicable Rules (Completed by EAD Staff)</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>&nbsp;&nbsp;&nbsp;Rule 1107 - Coating of Metal Parts (i.e. lockers, engine stands)</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>&nbsp;&nbsp;&nbsp;Rule 1124 - Coating of Aricraft Components</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>&nbsp;&nbsp;&nbsp;Rule 1151 - Coating of Vehicles and Vehicle Parts</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>&nbsp;&nbsp;&nbsp;Rule 1168 - Adhesive and Sealant Applications</TD>");
      pw.println("</TR>");
      pw.println("<TR>");
      pw.println("<TD>&nbsp;&nbsp;&nbsp;Rule 1171 - Cleaning Solvents</TD>");
      pw.println("</TR>");
      pw.println("</TABLE>");
      pw.println("</html>");
      pw.close();

      //send url back to client to open
      String tempurlpath = resource.getString("report.hosturl") + resource.getString("excel.report.urlpath");
      url = tempurlpath + "ScaqmdMasterList"+tmpReqNum.toString()+".xls";
    }catch (Exception e) {
      e.printStackTrace();
      url = "";
    }
    return url;
  } //end of method

  public RegisterTable[] linkMonthlyData(Collection headerData) throws Exception{
      boolean result = true;
      RegisterTable[] rt = new RegisterTable[1];
      try {
        rt[0] = new RegisterTable(ScaqmdRule109.getVector(headerData),"SCAQMDMONTHLY",ScaqmdRule109.getFieldVector(),null);
      }catch(Exception e1) {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
  }

  public RegisterTable[] linkMasterData(Collection headerData) throws Exception{
      boolean result = true;
      RegisterTable[] rt = new RegisterTable[1];
      try {
        rt[0] = new RegisterTable(ScaqmdMasterList.getVector(headerData),"SCAQMDMASTERLIST",ScaqmdMasterList.getFieldVector(),null);
      }catch(Exception e1) {
        e1.printStackTrace();
        throw e1;
      }
      return rt;
  }

  Collection getMonthlyData(String beginDate, String endDate, String workGroup) throws Exception {
    Collection col = null;
    try {
      DbManager dbManager = new DbManager(getClient());
      ScaqmdRule109ViewBeanFactory factory = new ScaqmdRule109ViewBeanFactory(dbManager);
      col = factory.select(beginDate,endDate,workGroup);
    }catch (Exception e) {
      throw e;
    }
    return col;
  } //end of method


  Collection getMasterListData(String workGroup) throws Exception {
    Collection col = null;
    try {
      DbManager dbManager = new DbManager(getClient());
      SearchCriteria criteria = new SearchCriteria();
      if ("GSE".equalsIgnoreCase(workGroup) || "ACMX".equalsIgnoreCase(workGroup)) {
        criteria.addCriterion("forUseBy", SearchCriterion.LIKE, workGroup);
      }
      ScaqmdMasterListViewBeanFactory factory = new ScaqmdMasterListViewBeanFactory(dbManager);
      col = factory.select(criteria);
    }catch (Exception e) {
      throw e;
    }
    return col;
  } //end of method


  public Collection getBeginYear() throws BaseException, Exception {
    Collection yearCollection = new Vector();
    //first need to find the starting report year for company
    DbManager dbManager = new DbManager(getClient());
    TcmisConstantBeanFactory tcmisConstantBeanFactory = new TcmisConstantBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("constant", SearchCriterion.EQUALS, "Report.Year");

    Vector tcmisConstantBeanCollection = new Vector(tcmisConstantBeanFactory.select(criteria));
    Integer startingYear = new Integer(1998);
    for (int j = 0; j < tcmisConstantBeanCollection.size(); j++) {
      TcmisConstantBean tcmisConstantBean = (TcmisConstantBean)tcmisConstantBeanCollection.elementAt(j);
      startingYear = new Integer(tcmisConstantBean.getValue());
    }
    //putting it all together
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    for (int i = startingYear.intValue(); i <= currentYear; i++) {
      KeyValuePairBean keyValuePairBean = new KeyValuePairBean();
      keyValuePairBean.setKey((new Integer(i)).toString());
      keyValuePairBean.setValue((new Integer(i)).toString());
      yearCollection.add(keyValuePairBean);
    }
    return yearCollection;
  } //end of method

} //end of class