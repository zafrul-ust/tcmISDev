package com.tcmis.internal.report.process;

import java.io.File;
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
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.report.beans.MsdsReportBean;
import com.tcmis.internal.report.factory.MsdsReportFactory;

/******************************************************************************
 * Process to create MSDS excel report
 * @version 1.0
 *****************************************************************************/
public class MsdsReportProcess
    extends BaseExcelReportProcess {
  Log log = LogFactory.getLog(this.getClass());

  public MsdsReportProcess(String client) {
    super(client);
  }

      /*****************************************************************************
   * Creates a pdf document.
       *****************************************************************************/
/*
  public XSLTInputHandler getPdfReport(MsdsReportBean bean) throws
      BaseException, Exception {
    //get data in xml format
    DbManager dbManager = new DbManager(getClient());
    MsdsReportFactory msdsReportFactory = new MsdsReportFactory(dbManager);
    ResourceLibrary resource = new ResourceLibrary("report");
    XSLTInputHandler input = new XSLTInputHandler(this.getXmlFile(bean),
                                                  new
                                                  File(resource.getString(
        "pdf.msds.xsl")));
    return input;
  }

  public HSSFWorkbook getExcelReport(MsdsReportBean bean) throws BaseException,
      Exception {
    if (log.isInfoEnabled()) {
      log.info("calling msds report process");
    }
    //get data
    DbManager dbManager = new DbManager(getClient());
    MsdsReportFactory factory = new MsdsReportFactory(dbManager);
    DataSet dataSet = factory.getDataSet(bean);

    //create workbook
    HSSFWorkbook wb = new HSSFWorkbook();
    //first create input sheet with parameters
    HSSFSheet reportSheet = wb.createSheet("Input");
    //widen the columns
    reportSheet.setDefaultColumnWidth( (short) 20);
    //remove grid
    reportSheet.setDisplayGridlines(false);
    //header row
    HSSFRow row = reportSheet.createRow( (short) 0);
    row.createCell( (short) 0).setCellValue("Formatted MSDS Revision");
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
    row.createCell( (short) 0).setCellValue("Facility:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getFacility());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    this.createOutputSheet(wb, dataSet);
    return wb;
  }
*/
  private File getXmlFile(MsdsReportBean bean) throws BaseException, Exception {
    DbManager dbManager = new DbManager(getClient());
    MsdsReportFactory factory = new MsdsReportFactory(dbManager);
    DataSet dataSet = factory.getDataSet(bean);

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.newDocument();
    Element root = doc.createElement("root_element");
    //create header column
    String columnNameArray[] = dataSet.getColumnNameArray();
    Element header = doc.createElement("header");
    Element timestamp = doc.createElement("timestamp");
    timestamp.appendChild(doc.createTextNode(new Date().toString()));
    header.appendChild(timestamp);
    for (int i = 0; i < columnNameArray.length; i++) {
      Element column = doc.createElement("column");
      column.appendChild(doc.createTextNode(columnNameArray[i]));
      header.appendChild(column);
    }
    root.appendChild(header);
    //create data section
    Element data = doc.createElement("data");
    Iterator iterator = dataSet.iterator();
    while (iterator.hasNext()) {
      Element row = doc.createElement("row");
      DataSetRow dataSetRow = (DataSetRow) iterator.next();
      for (int i = 0; i < columnNameArray.length; i++) {
        Element e = doc.createElement(columnNameArray[i]);
        e.appendChild(doc.createTextNode(dataSetRow.getString(columnNameArray[i])));
        row.appendChild(e);
      }
      data.appendChild(row);
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
    File tempFile = File.createTempFile("report", ".xml", new File("."));
    //delete file when vm shuts down
    tempFile.deleteOnExit();
    StreamResult result = new StreamResult(tempFile);
    tf.transform(source, result);

    return tempFile;
  }
}