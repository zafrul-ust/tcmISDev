package com.tcmis.internal.invoice.process;

import java.io.*;
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

/*
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.hssf.util.HSSFColor;
*/
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;
import com.tcmis.internal.invoice.beans.*;
import com.tcmis.internal.invoice.factory.*;

/***********************************************************
 * Process for creating Boeing Decatur invoice.
 ***********************************************************/

public class BoeingDecaturInvoiceProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public BoeingDecaturInvoiceProcess(String client) {
    super(client);
  }
/*
  public HSSFWorkbook getInvoice(InvoiceInputBean inputBean) throws
      NoDataException, BaseException, Exception {

    //File myFile = selectXmlFile(bean);
    File myFile = new File("C:\\departmentcost.xml");
    ResourceLibrary resource = new ResourceLibrary("invoice");
    DbManager dbManager = new DbManager(getClient());

    InvoiceFormatViewBdPrepBeanFactory factory = new InvoiceFormatViewBdPrepBeanFactory(dbManager);
    Collection c = factory.select(new SearchCriteria("invoicePeriod",
                                      SearchCriterion.EQUALS,
                                      inputBean.getInvoicePeriod().toString()));
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet1 = wb.createSheet("Sheet1");
    HSSFRow row0 = sheet1.createRow((short)0);
    HSSFRow row1 = sheet1.createRow((short)1);
    HSSFRow row2 = sheet1.createRow((short)2);
    HSSFRow row4 = sheet1.createRow((short)4);
    HSSFRow row5 = sheet1.createRow((short)5);
    HSSFRow row6 = sheet1.createRow((short)6);
    HSSFRow row7 = sheet1.createRow((short)7);
    HSSFRow row8 = sheet1.createRow((short)8);
    HSSFRow row10 = sheet1.createRow((short)10);
    HSSFRow row11 = sheet1.createRow((short)11);
    HSSFRow row12 = sheet1.createRow((short)12);
    HSSFRow row14 = sheet1.createRow((short)14);
    HSSFRow row15 = sheet1.createRow((short)15);
    HSSFRow row16 = sheet1.createRow((short)16);
    row0.createCell((short)0).setCellValue("Invoice");
    row1.createCell((short)0).setCellValue("Invoice Date");
    row2.createCell((short)0).setCellValue("Invoice Amount");
    row4.createCell((short)0).setCellValue("Sold To:");
    row4.createCell((short)5).setCellValue("Please Remit Payment to:");
    row5.createCell((short)0).setCellValue("McDonnell Douglas Corporation / Boeing");
    row5.createCell((short)5).setCellValue("Haas TCM");
    row6.createCell((short)0).setCellValue("Attn:  Greg Cauthen / Cindy Flack");
    row6.createCell((short)5).setCellValue("1646 West Chester Pike, Suites 4-6");
    row7.createCell((short)0).setCellValue("P.O. Box 490");
    row7.createCell((short)5).setCellValue("West Chester, PA  19382");
    row8.createCell((short)0).setCellValue("Trinity, AL 35673");
    row10.createCell((short)0).setCellValue("Purchase Order No.");
    row11.createCell((short)0).setCellValue("Terms:");
    row12.createCell((short)0).setCellValue("Charges for period ending");
    row14.createCell((short)1).setCellValue("Haas Invoice Breakdown & Cost Distribution");
    row14.createCell((short)9).setCellValue("General Ledger Disbursement & Supplier Payment Total");
    row15.createCell((short)9).setCellValue("Haas Invoice#:");
    row16.createCell((short)0).setCellValue("Invoice Item Description");
    row16.createCell((short)1).setCellValue("Chemical Invoice $");
    row16.createCell((short)2).setCellValue("% of total Chemicals Invoiced");
    row16.createCell((short)3).setCellValue("ODC");
    row16.createCell((short)4).setCellValue("NONCHEM");
    row16.createCell((short)5).setCellValue("Disbursed Other Invoiced Indirect Costs (ODC & Non chem)");
    row16.createCell((short)6).setCellValue("Disbursed Other Invoiced Indirect Costs (Labor)");
    row16.createCell((short)8).setCellValue("Journal Entry");
    row16.createCell((short)9).setCellValue("Department");
    row16.createCell((short)10).setCellValue("CCN");
    row16.createCell((short)11).setCellValue("Journal Entry");
    row16.createCell((short)12).setCellValue("Department");
    row16.createCell((short)13).setCellValue("CCN2");
    row16.createCell((short)14).setCellValue("Total Journal Entry");

    Iterator iterator = c.iterator();
    boolean first = true;
    while(iterator.hasNext()) {
      InvoiceFormatViewBdPrepBean bean = (InvoiceFormatViewBdPrepBean)iterator.next();
      if(first) {
        InvoiceBeanFactory invoiceFactory = new InvoiceBeanFactory(dbManager);
        Collection invoiceColl = invoiceFactory.select(new SearchCriteria(
            "invoice",
            SearchCriterion.EQUALS,
            bean.getInvoice().toString()));
        Iterator invoiceIterator = invoiceColl.iterator();
        InvoiceBean invoiceBean = (InvoiceBean)invoiceIterator.next();
        row0.createCell( (short) 01).setCellValue(bean.getInvoice().toString());
        row1.createCell( (short) 01).setCellValue(invoiceBean.getInvoiceDate());
        row2.createCell( (short) 01).setCellValue(invoiceBean.getInvoiceAmount().toString());
        first = false;
      }
    }



    return wb;
  }
*/
  private File selectXmlFile(InvoiceInputBean inputBean) throws
      BaseException,
      Exception {
    Collection coll = null;
    try {
      DbManager dbManager = new DbManager(getClient());
      InvoiceViewBaeBeanFactory factory = new InvoiceViewBaeBeanFactory(
          dbManager);
      coll = factory.select(new SearchCriteria("invoicePeriod",
                                               SearchCriterion.EQUALS,
                                               inputBean.
          getInvoicePeriod().toString()));
      if (coll == null || coll.size() == 0) {
        throw new NoDataException("Query returned no data");
      }
    }
    finally {
    }
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.newDocument();
    Element root = doc.createElement("root_element");
    Element header = doc.createElement("header");
    Element timestamp = doc.createElement("timestamp");
    timestamp.appendChild(doc.createTextNode(new Date().toString()));
    header.appendChild(timestamp);
    Element reportdate = doc.createElement("date");
    reportdate.appendChild(doc.createTextNode(DateHandler.formatDate(new Date(),
        "MM/yyyy")));
    header.appendChild(reportdate);
    root.appendChild(header);
    //when creating the text nodes we need to check that the bean doesn't return
    //a null, if so we need to create the node with an empty string
    Element data = doc.createElement("data");
    Iterator iterator = coll.iterator();

    while (iterator.hasNext()) {
      InvoiceViewBaeBean bean = (InvoiceViewBaeBean) iterator.next();
      Element invoice = doc.createElement("invoice");
      Element invoiceHeader = doc.createElement("invoiceheader");
      Element client = doc.createElement("client");
      client.appendChild(doc.createTextNode(inputBean.getClient()));
      invoiceHeader.appendChild(client);
      invoice.appendChild(invoiceHeader);
/*
      Collection childColl = bean.getInvoiceDetailBeanCollection();
      Iterator childIterator = childColl.iterator();

      while (childIterator.hasNext()) {
        InvoiceViewBaeDetailBean detailBean = (InvoiceViewBaeDetailBean)
            childIterator.next();
        Element invoiceDetail = doc.createElement("invoicedetail");
        Element mrNumber = doc.createElement("mrnumber");
        mrNumber.appendChild(doc.createTextNode(detailBean.getMrNumber()));
        invoiceDetail.appendChild(mrNumber);
        Element radianPo = doc.createElement("radianpo");
        radianPo.appendChild(doc.createTextNode(detailBean.
            getRadianPo().toString()));
        invoiceDetail.appendChild(radianPo);
        Element partNumber = doc.createElement("partnumber");
        partNumber.appendChild(doc.createTextNode(detailBean.getCatPartNo()));
        invoiceDetail.appendChild(partNumber);
        Element detailDate = doc.createElement("date");
        detailDate.appendChild(doc.createTextNode(detailBean.getDateDelivered().
                                                  toString()));
        invoiceDetail.appendChild(detailDate);
        Element quantity = doc.createElement("quantity");
        quantity.appendChild(doc.createTextNode(detailBean.getQuantity().
                                                toString()));
        invoiceDetail.appendChild(quantity);
        Element unitPrice = doc.createElement("unitprice");
        unitPrice.appendChild(doc.createTextNode(detailBean.getNetPrice().
                                                 toString()));
        invoiceDetail.appendChild(unitPrice);
        Element unitRebate = doc.createElement("unitrebate");
        unitRebate.appendChild(doc.createTextNode(detailBean.getUnitRebate().
                                                  toString()));
        invoiceDetail.appendChild(unitRebate);
        Element extPrice = doc.createElement("extprice");
        extPrice.appendChild(doc.createTextNode(detailBean.getNetPrice().
                                                toString()));
        invoiceDetail.appendChild(extPrice);
        Element extRebate = doc.createElement("extrebate");
        extRebate.appendChild(doc.createTextNode(detailBean.getNetAmount().
                                                 toString()));
        invoiceDetail.appendChild(extRebate);
        Element netPrice = doc.createElement("netprice");
        netPrice.appendChild(doc.createTextNode(detailBean.getNetPrice().
                                                toString()));
        invoiceDetail.appendChild(netPrice);
        Element additionalCharges = doc.createElement("additionalcharges");
        additionalCharges.appendChild(doc.createTextNode(detailBean.
            getAdditionalCharge().toString()));
        invoiceDetail.appendChild(additionalCharges);
        Element serviceFee = doc.createElement("servicefee");
        serviceFee.appendChild(doc.createTextNode(detailBean.getServiceFee().
                                                  toString()));
        invoiceDetail.appendChild(serviceFee);
        Element netLineAmount = doc.createElement("netlineamount");
        netLineAmount.appendChild(doc.createTextNode(detailBean.
            getNetLineAmount().toString()));
        invoiceDetail.appendChild(netLineAmount);
        invoice.appendChild(invoiceDetail);
      }
*/
      data.appendChild(invoice);
    }
    root.appendChild(data);
    //add footer
    Element footer = doc.createElement("footer");
    root.appendChild(footer);
    doc.appendChild(root);
    if (log.isDebugEnabled()) {
      log.debug("ready to translate to xml file");
    }
    //translate into a XML file
    TransformerFactory tmf = TransformerFactory.newInstance();
    Transformer tf = tmf.newTransformer();
    DOMSource source = new DOMSource(doc);
    File tempFile = File.createTempFile("report", ".xml", new File("."));
    //File tempFile = new File("C:\\_z.xml");
    //delete file when vm shuts down
    tempFile.deleteOnExit();
    StreamResult result = new StreamResult(tempFile);
    tf.transform(source, result);
    return tempFile;
  }
}
