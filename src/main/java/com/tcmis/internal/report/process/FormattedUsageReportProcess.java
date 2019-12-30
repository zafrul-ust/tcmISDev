package com.tcmis.internal.report.process;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.fop.apps.XSLTInputHandler;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.Region;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseExcelReportProcess;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.report.beans.ReportParameterBean;
import com.tcmis.internal.report.factory.ReportParameterBeanFactory;
import com.tcmis.internal.report.factory.UsageReportFactory;

/*****************************************************************************
 * Process for creating formatted usage reports
 ****************************************************************************/
public class FormattedUsageReportProcess
    extends BaseExcelReportProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FormattedUsageReportProcess(String client) {
    super(client);
  }

      /*****************************************************************************
   * Gets parameters from REPORT_PARAMETERS table and creates a bean.
       *****************************************************************************/
  public ReportParameterBean getReportParameterBean(String id) throws
      BaseException, Exception {
    //get data
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("uniqueId",
                          SearchCriterion.EQUALS,
                          id);
    DbManager dbManager = new DbManager(getClient());
    ReportParameterBeanFactory reportParameterBeanFactory = new
        ReportParameterBeanFactory(dbManager);
    Collection reportParameterBeanCollection = reportParameterBeanFactory.
        select(criteria);
    //there should only be one bean in the collection
    ReportParameterBean bean = null;
    Iterator iterator = reportParameterBeanCollection.iterator();
    while (iterator.hasNext()) {
      bean = (ReportParameterBean) iterator.next();
    }

    return bean;
  }

      /*****************************************************************************
   * Creates a pdf document.
       *****************************************************************************/
/*
  public XSLTInputHandler getPdfReport(ReportParameterBean bean) throws
      BaseException, Exception {
    //get data in xml format
    DbManager dbManager = new DbManager(getClient());
    UsageReportFactory usageReportFactory = new UsageReportFactory(dbManager);
    ResourceLibrary resource = new ResourceLibrary("report");
    XSLTInputHandler input = null;
    if (bean.getAction() != null &&
        bean.getAction().equalsIgnoreCase("VOC_USAGE")) {
      input = new XSLTInputHandler(this.getXmlFileForVOCUsage(bean),
                                   new File(
          resource.getString("pdf.vocusage.xsl")));
    }
    else if (bean.getAction() != null &&
             bean.getAction().equalsIgnoreCase("REPORTABLE_USAGE")) {
      input = new XSLTInputHandler(this.getXmlFileForUsage(bean),
                                   new File(resource.getString("pdf.usage.xsl")));
    }
    return input;
  }
*/
      /*****************************************************************************
   * Creates a <code>HSSFWorkbook</code> excel spreedsheet.
       *****************************************************************************/
/*
  public HSSFWorkbook getExcelReport(ReportParameterBean bean) throws
      BaseException,
      Exception {
    if (log.isInfoEnabled()) {
      log.info("calling generic report process");
    }

    //now I have the report parameters in the bean, I'll use those for creating
    //the input sheet on the report and to create search criteria for the
    //actual data in the report requested
    DbManager dbManager = new DbManager(getClient());
    UsageReportFactory usageReportFactory = new UsageReportFactory(dbManager);
    DataSet dataSet = null;
    //now I have all the data, now it's time to create the report
    HSSFWorkbook wb = new HSSFWorkbook();
    //check if it's formatted usage or VOC usage
    if (bean.getAction() != null &&
        bean.getAction().equalsIgnoreCase("VOC_USAGE")) {
      createVOCUsageInputSheet(wb, bean);
      dataSet = usageReportFactory.getDataSetForVOCUsage(bean);
    }
    else if (bean.getAction() != null &&
             bean.getAction().equalsIgnoreCase("REPORTABLE_USAGE")) {
      createReportableUsageInputSheet(wb, bean);
      dataSet = usageReportFactory.getDataSetForUsage(bean);
    }

    this.createOutputSheet(wb, dataSet);
    return wb;
  }

  private void createVOCUsageInputSheet(HSSFWorkbook wb,
                                        ReportParameterBean bean) {
    //first create input sheet with parameters
    HSSFSheet reportSheet = wb.createSheet("Input");
    //widen the columns
    reportSheet.setDefaultColumnWidth( (short) 20);
    //remove grid
    reportSheet.setDisplayGridlines(false);
    //header row
    HSSFRow row = reportSheet.createRow( (short) 0);
    row.createCell( (short) 0).setCellValue("VOC Usage Report");
    //merge report name header cells
    reportSheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 1));
    row.getCell( (short) 0).setCellStyle(getBoldBorderCellStyle(wb));
    row.createCell( (short) 1);
    row.getCell( (short) 1).setCellStyle(getBoldBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 1);
    //date row
    row = reportSheet.createRow( (short) 2);
    row.createCell( (short) 0).setCellValue("Date:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(new Date());
    row.getCell( (short) 1).setCellStyle(getLeftBorderDateCellStyle(wb,
        "m/d/yy h:mm"));
    //sub header row
    row = reportSheet.createRow( (short) 3);
    row.createCell( (short) 0).setCellValue("Input:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1);
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    //paramter rows starts from here
    row = reportSheet.createRow( (short) 4);
    row.createCell( (short) 0).setCellValue("Chemical:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    if (bean.getSearchType() != null &&
        bean.getSearchType().equalsIgnoreCase("ALL")) {
      row.createCell( (short) 1).setCellValue(bean.getSearchType());
    }
    else if (bean.getSearchType() != null &&
             bean.getSearchType().equalsIgnoreCase("CAS_NUM")) {
      row.createCell( (short) 1).setCellValue(bean.getCasNum());
    }
    else if (bean.getSearchType() != null &&
             bean.getSearchType().equalsIgnoreCase("LIST")) {
      row.createCell( (short) 1).setCellValue(bean.getListId());
    }
    else if (bean.getSearchType() != null &&
             bean.getSearchType().equalsIgnoreCase("VOC")) {
      row.createCell( (short) 1).setCellValue(bean.getSearchType());
    }
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 5);
    row.createCell( (short) 0).setCellValue("Facility:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getFacility());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 6);
    row.createCell( (short) 0).setCellValue("Work Area:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getWorkArea());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 7);
    row.createCell( (short) 0).setCellValue("Begin:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getBegmonth() + "/" +
                                            bean.getBegyear());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 8);
    row.createCell( (short) 0).setCellValue("End:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getEndmonth() + "/" +
                                            bean.getEndyear());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
  }

  private void createReportableUsageInputSheet(HSSFWorkbook wb,
                                               ReportParameterBean bean) {
    //first create input sheet with parameters
    HSSFSheet reportSheet = wb.createSheet("Input");
    //widen the columns
    reportSheet.setDefaultColumnWidth( (short) 20);
    //remove grid
    reportSheet.setDisplayGridlines(false);
    //header row
    HSSFRow row = reportSheet.createRow( (short) 0);
    row.createCell( (short) 0).setCellValue("Formatted Usage Report");
    //merge report name header cells
    reportSheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 1));
    row.getCell( (short) 0).setCellStyle(getBoldBorderCellStyle(wb));
    row.createCell( (short) 1);
    row.getCell( (short) 1).setCellStyle(getBoldBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 1);
    //date row
    row = reportSheet.createRow( (short) 2);
    row.createCell( (short) 0).setCellValue("Date:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(new Date());
    row.getCell( (short) 1).setCellStyle(getLeftBorderDateCellStyle(wb,
        "m/d/yy h:mm"));
    //sub header row
    row = reportSheet.createRow( (short) 3);
    row.createCell( (short) 0).setCellValue("Input:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1);
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    //paramter rows starts from here
    row = reportSheet.createRow( (short) 4);
    row.createCell( (short) 0).setCellValue("Chemical:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    if (bean.getSearchType() != null &&
        bean.getSearchType().equalsIgnoreCase("ALL")) {
      row.createCell( (short) 1).setCellValue(bean.getSearchType());
    }
    else if (bean.getSearchType() != null &&
             bean.getSearchType().equalsIgnoreCase("CAS_NUM")) {
      row.createCell( (short) 1).setCellValue(bean.getCasNum());
    }
    else if (bean.getSearchType() != null &&
             bean.getSearchType().equalsIgnoreCase("LIST")) {
      row.createCell( (short) 1).setCellValue(bean.getListId());
    }
    else if (bean.getSearchType() != null &&
             bean.getSearchType().equalsIgnoreCase("VOC")) {
      row.createCell( (short) 1).setCellValue(bean.getSearchType());
    }
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 5);
    row.createCell( (short) 0).setCellValue("Facility:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getFacility());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 6);
    row.createCell( (short) 0).setCellValue("Work Area:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getWorkArea());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 7);
    row.createCell( (short) 0).setCellValue("Begin:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getBegmonth() + "/" +
                                            bean.getBegyear());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 8);
    row.createCell( (short) 0).setCellValue("End:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getEndmonth() + "/" +
                                            bean.getEndyear());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
  }

  private File getXmlFileForUsage(ReportParameterBean bean) throws
      BaseException, Exception {
    DbManager dbManager = new DbManager(getClient());
    UsageReportFactory usageReportFactory = new UsageReportFactory(dbManager);
    DataSet dataSet = usageReportFactory.getDataSetForUsage(bean);
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.newDocument();
    Element root = doc.createElement("root_element");
    //create header column
    String columnNameArray[] = dataSet.getColumnNameArray();
    Element header = doc.createElement("header");
    //add timestamp
    Element timestamp = doc.createElement("timestamp");
    timestamp.appendChild(doc.createTextNode(new Date().toString()));
    header.appendChild(timestamp);
    //create columns
    for (int i = 0; i < columnNameArray.length; i++) {
      Element column = doc.createElement("column");
      column.appendChild(doc.createTextNode(columnNameArray[i]));
      header.appendChild(column);
    }
    root.appendChild(header);
    //create data section, group by facility, work area
    Element data = doc.createElement("data");
    //add input params
    Element facilityParameter = doc.createElement("facilityparameter");
    facilityParameter.appendChild(doc.createTextNode(bean.getFacility()));
    data.appendChild(facilityParameter);
    Element workAreaParameter = doc.createElement("workareaparameter");
    workAreaParameter.appendChild(doc.createTextNode(bean.getWorkArea()));
    data.appendChild(workAreaParameter);
    Element beginYearParameter = doc.createElement("beginyearparameter");
    beginYearParameter.appendChild(doc.createTextNode(bean.getBegyear()));
    data.appendChild(beginYearParameter);
    Element beginMonthParameter = doc.createElement("beginmonthparameter");
    beginMonthParameter.appendChild(doc.createTextNode(DateHandler.
        getMonthString(bean.getBegmonth())));
    data.appendChild(beginMonthParameter);
    Element endYearParameter = doc.createElement("endyearparameter");
    endYearParameter.appendChild(doc.createTextNode(bean.getEndyear()));
    data.appendChild(endYearParameter);
    Element endMonthParameter = doc.createElement("endmonthparameter");
    endMonthParameter.appendChild(doc.createTextNode(DateHandler.getMonthString(
        bean.getEndmonth())));
    data.appendChild(endMonthParameter);

    Iterator iterator = dataSet.iterator();
    String facilityCurrent = "";
    String facilityPrevious = "";
    String workAreaCurrent = "";
    String workAreaPrevious = "";
    String casCurrent = "";
    String casPrevious = "";
    Element facilityElement = null;
    Element workAreaElement = null;
    Element casElement = null;
    Element facilitySumElement = null;
    Element workAreaSumElement = null;
    Element casSumElement = null;
    BigDecimal facilitySum = new BigDecimal("0");
    BigDecimal workAreaSum = new BigDecimal("0");
    BigDecimal casSum = new BigDecimal("0");
    while (iterator.hasNext()) {
      //check if I need to create a new facility element
      if (facilityElement == null) {
        facilityElement = doc.createElement("facility");
      }
      if (workAreaElement == null) {
        workAreaElement = doc.createElement("work_area");
      }
      if (casElement == null) {
        casElement = doc.createElement("cas");
      }

      Element row = doc.createElement("row");
      DataSetRow dataSetRow = (DataSetRow) iterator.next();
      facilityCurrent = dataSetRow.getString("FACILITY");
      workAreaCurrent = dataSetRow.getString("LOCATION");
      casCurrent = dataSetRow.getString("CAS_NUMBER");
      if (casCurrent == null) {
        casCurrent = " ";
      }
      //check if it is a new facility, if so, add old facility element
      //and create a new one
      if (!facilityPrevious.equalsIgnoreCase("") &&
          !facilityPrevious.equalsIgnoreCase(facilityCurrent)) {
        //add sum for cas
        casSumElement = doc.createElement("CAS_TOTAL");
        casSumElement.appendChild(doc.createTextNode(casSum.toString()));
        //reset cas sum
        casSum = new BigDecimal("0");
        casElement.appendChild(casSumElement);
        //add sum for work area
        workAreaSumElement = doc.createElement("WORK_AREA_WT_VOC_TOTAL");
        workAreaSumElement.appendChild(doc.createTextNode(workAreaSum.toString()));
        //reset work area sum
        workAreaSum = new BigDecimal("0");
        workAreaElement.appendChild(workAreaSumElement);
        workAreaElement.appendChild(casElement);
        //add sum for facility
        facilitySumElement = doc.createElement("FACILITY_WT_VOC_TOTAL");
        facilitySumElement.appendChild(doc.createTextNode(facilitySum.toString()));
        //reset facility sum
        facilitySum = new BigDecimal("0");
        facilityElement.appendChild(facilitySumElement);
        facilityElement.appendChild(workAreaElement);
        data.appendChild(facilityElement);
        facilityElement = doc.createElement("facility");
        workAreaElement = doc.createElement("work_area");
        casElement = doc.createElement("cas");
      }
      else if (!workAreaPrevious.equalsIgnoreCase("") &&
               !workAreaPrevious.equalsIgnoreCase(workAreaCurrent)) {
        //add sum for cas
        casSumElement = doc.createElement("CAS_TOTAL");
        casSumElement.appendChild(doc.createTextNode(casSum.toString()));
        //reset cas sum
        casSum = new BigDecimal("0");
        casElement.appendChild(casSumElement);

        workAreaSumElement = doc.createElement("WORK_AREA_WT_VOC_TOTAL");
        workAreaSumElement.appendChild(doc.createTextNode(workAreaSum.toString()));
        //reset work area sum
        workAreaSum = new BigDecimal("0");
        workAreaElement.appendChild(workAreaSumElement);
        workAreaElement.appendChild(casElement);
        facilityElement.appendChild(workAreaElement);
        workAreaElement = doc.createElement("work_area");
        casElement = doc.createElement("cas");
      }
      else if (!casPrevious.equalsIgnoreCase("") &&
               !casPrevious.equalsIgnoreCase(casCurrent)) {
        casSumElement = doc.createElement("CAS_TOTAL");
        casSumElement.appendChild(doc.createTextNode(casSum.toString()));
        //reset cas sum
        casSum = new BigDecimal("0");
        casElement.appendChild(casSumElement);
        workAreaElement.appendChild(casElement);
        casElement = doc.createElement("cas");
      }
      //add to sum
      if (dataSetRow.getString("LBS_REPORTABLE") != null &&
          !dataSetRow.getString("LBS_REPORTABLE").equalsIgnoreCase("")) {
        facilitySum = facilitySum.add(new BigDecimal(dataSetRow.getString(
            "LBS_REPORTABLE")));
        workAreaSum = workAreaSum.add(new BigDecimal(dataSetRow.getString(
            "LBS_REPORTABLE")));
        casSum = casSum.add(new BigDecimal(dataSetRow.getString(
            "LBS_REPORTABLE")));
      }
      //create column
      for (int i = 0; i < columnNameArray.length; i++) {
        Element e = doc.createElement(columnNameArray[i]);
        if (dataSetRow.getString(columnNameArray[i]) != null) {
          e.appendChild(doc.createTextNode(dataSetRow.getString(columnNameArray[
              i])));
        }
        else {
          e.appendChild(doc.createTextNode("0"));
        }
        row.appendChild(e);
      }
      facilityPrevious = facilityCurrent;
      workAreaPrevious = workAreaCurrent;
      casPrevious = casCurrent;
      casElement.appendChild(row);
      //workAreaElement.appendChild(row);
      //facilityElement.appendChild(row);
    }
    //add last elements, make sure it actually returned data
    if (dataSet.size() != 0) {
      casSumElement = doc.createElement("CAS_TOTAL");
      casSumElement.appendChild(doc.createTextNode(casSum.toString()));
      casElement.appendChild(casSumElement);
      workAreaElement.appendChild(casElement);
      workAreaSumElement = doc.createElement("WORK_AREA_WT_VOC_TOTAL");
      workAreaSumElement.appendChild(doc.createTextNode(workAreaSum.toString()));
      workAreaElement.appendChild(workAreaSumElement);
      facilityElement.appendChild(workAreaElement);
      facilitySumElement = doc.createElement("FACILITY_WT_VOC_TOTAL");
      facilitySumElement.appendChild(doc.createTextNode(facilitySum.toString()));
      facilityElement.appendChild(facilitySumElement);
      data.appendChild(facilityElement);
    }
    root.appendChild(data);
    //create footer section
    Element footer = doc.createElement("footer");
    root.appendChild(footer);
    doc.appendChild(root);
    //translate into a XML file
    TransformerFactory tmf = TransformerFactory.newInstance();
    Transformer tf = tmf.newTransformer();
    DOMSource source = new DOMSource(doc);
    //File tempFile = File.createTempFile("report", ".xml", new File("."));
    File tempFile = new File("C:\\zzfile.xml");
    //delete file when vm shuts down
    tempFile.deleteOnExit();
    StreamResult result = new StreamResult(tempFile);
    //StreamResult result = new StreamResult(System.out);
    tf.transform(source, result);

    return tempFile;
  }

  private File getXmlFileForVOCUsage(ReportParameterBean bean) throws
      BaseException, Exception {
    DbManager dbManager = new DbManager(getClient());
    UsageReportFactory usageReportFactory = new UsageReportFactory(dbManager);
    DataSet dataSet = usageReportFactory.getDataSetForVOCUsage(bean);
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.newDocument();
    Element root = doc.createElement("root_element");
    //create header column
    String columnNameArray[] = dataSet.getColumnNameArray();
    Element header = doc.createElement("header");
    //add timestamp
    Element timestamp = doc.createElement("timestamp");
    timestamp.appendChild(doc.createTextNode(new Date().toString()));
    header.appendChild(timestamp);
    //create columns
    for (int i = 0; i < columnNameArray.length; i++) {
      Element column = doc.createElement("column");
      column.appendChild(doc.createTextNode(columnNameArray[i]));
      header.appendChild(column);
    }
    root.appendChild(header);
    //create data section, group by facility, work area
    Element data = doc.createElement("data");
    //add input params
    Element facilityParameter = doc.createElement("facilityparameter");
    facilityParameter.appendChild(doc.createTextNode(bean.getFacility()));
    data.appendChild(facilityParameter);
    Element workAreaParameter = doc.createElement("workareaparameter");
    workAreaParameter.appendChild(doc.createTextNode(bean.getWorkArea()));
    data.appendChild(workAreaParameter);
    Element beginYearParameter = doc.createElement("beginyearparameter");
    beginYearParameter.appendChild(doc.createTextNode(bean.getBegyear()));
    data.appendChild(beginYearParameter);
    Element beginMonthParameter = doc.createElement("beginmonthparameter");
    beginMonthParameter.appendChild(doc.createTextNode(DateHandler.
        getMonthString(bean.getBegmonth())));
    data.appendChild(beginMonthParameter);
    Element endYearParameter = doc.createElement("endyearparameter");
    endYearParameter.appendChild(doc.createTextNode(bean.getEndyear()));
    data.appendChild(endYearParameter);
    Element endMonthParameter = doc.createElement("endmonthparameter");
    endMonthParameter.appendChild(doc.createTextNode(DateHandler.getMonthString(
        bean.getEndmonth())));
    data.appendChild(endMonthParameter);

    Iterator iterator = dataSet.iterator();
    String facilityCurrent = "";
    String facilityPrevious = "";
    String workAreaCurrent = "";
    String workAreaPrevious = "";
    Element facilityElement = null;
    Element workAreaElement = null;
    Element facilitySumElement = null;
    Element workAreaSumElement = null;
    BigDecimal facilitySum = new BigDecimal("0");
    BigDecimal workAreaSum = new BigDecimal("0");
    while (iterator.hasNext()) {
      //check if I need to create a new facility element
      if (facilityElement == null) {
        facilityElement = doc.createElement("facility");
      }
      if (workAreaElement == null) {
        workAreaElement = doc.createElement("work_area");
      }

      Element row = doc.createElement("row");
      DataSetRow dataSetRow = (DataSetRow) iterator.next();
      facilityCurrent = dataSetRow.getString("FACILITY");
      workAreaCurrent = dataSetRow.getString("LOCATION");
      //check if it is a new facility, if so, add old facility element
      //and create a new one
      if (!facilityPrevious.equalsIgnoreCase("") &&
          !facilityPrevious.equalsIgnoreCase(facilityCurrent)) {
        //add sum for work area
        facilitySumElement = doc.createElement("FACILITY_WT_VOC_TOTAL");
        facilitySumElement.appendChild(doc.createTextNode(facilitySum.toString()));
        //reset facility sum
        facilitySum = new BigDecimal("0");
        facilityElement.appendChild(facilitySumElement);
        facilityElement.appendChild(workAreaElement);
        data.appendChild(facilityElement);
        facilityElement = doc.createElement("facility");
        workAreaElement = doc.createElement("work_area");
      }
      else if (!workAreaPrevious.equalsIgnoreCase("") &&
               !workAreaPrevious.equalsIgnoreCase(workAreaCurrent)) {

        workAreaSumElement = doc.createElement("WORK_AREA_WT_VOC_TOTAL");
        workAreaSumElement.appendChild(doc.createTextNode(workAreaSum.toString()));
        //reset work area sum
        workAreaSum = new BigDecimal("0");
        workAreaElement.appendChild(workAreaSumElement);
        facilityElement.appendChild(workAreaElement);
        workAreaElement = doc.createElement("work_area");
      }
      //add to sum
      if (dataSetRow.getString("WT_VOC") != null &&
          !dataSetRow.getString("WT_VOC").equalsIgnoreCase("")) {
        facilitySum = facilitySum.add(new BigDecimal(dataSetRow.getString(
            "WT_VOC")));
        workAreaSum = workAreaSum.add(new BigDecimal(dataSetRow.getString(
            "WT_VOC")));
      }
      //create column
      for (int i = 0; i < columnNameArray.length; i++) {
        Element e = doc.createElement(columnNameArray[i]);
        if (dataSetRow.getString(columnNameArray[i]) != null) {
          e.appendChild(doc.createTextNode(dataSetRow.getString(columnNameArray[
              i])));
        }
        else {
          e.appendChild(doc.createTextNode("0"));
        }
        row.appendChild(e);
      }
      facilityPrevious = facilityCurrent;
      workAreaPrevious = workAreaCurrent;
      workAreaElement.appendChild(row);
      //facilityElement.appendChild(row);
    }
    //add last elements
    workAreaSumElement = doc.createElement("WORK_AREA_WT_VOC_TOTAL");
    workAreaSumElement.appendChild(doc.createTextNode(workAreaSum.toString()));
    workAreaElement.appendChild(workAreaSumElement);
    facilityElement.appendChild(workAreaElement);
    facilitySumElement = doc.createElement("FACILITY_WT_VOC_TOTAL");
    facilitySumElement.appendChild(doc.createTextNode(facilitySum.toString()));
    facilityElement.appendChild(facilitySumElement);
    data.appendChild(facilityElement);
    root.appendChild(data);
    //create footer section
    Element footer = doc.createElement("footer");
    root.appendChild(footer);
    doc.appendChild(root);
    //translate into a XML file
    TransformerFactory tmf = TransformerFactory.newInstance();
    Transformer tf = tmf.newTransformer();
    DOMSource source = new DOMSource(doc);
    //File tempFile = File.createTempFile("report", ".xml", new File("."));
    File tempFile = new File("C:\\zzfile.xml");
    //delete file when vm shuts down
    tempFile.deleteOnExit();
    StreamResult result = new StreamResult(tempFile);
    //StreamResult result = new StreamResult(System.out);
    tf.transform(source, result);

    return tempFile;
  }
              */
}