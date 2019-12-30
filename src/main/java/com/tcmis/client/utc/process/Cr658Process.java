package com.tcmis.client.utc.process;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.*;

import com.tcmis.client.utc.beans.*;
import com.tcmis.client.utc.factory.*;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.ftp.FtpClient;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.EncryptHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

import com.tcmis.internal.invoice.beans.InvoiceSubmissionBean;
import com.tcmis.internal.invoice.factory.InvoiceSubmissionBeanFactory;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Cr658Process
    extends BaseProcess {

  ResourceLibrary utcLibrary = null;
  String invoiceFilePath = null;

  public Cr658Process(String client) {
    super(client);
    utcLibrary = new ResourceLibrary("utc");
  }

  public void createInvoice(String division) throws BaseException {
    invoiceFilePath = this.getInvoiceFileName(division);
    try {
      Collection headerCollection = this.getHeaderRecords(division);
      if(headerCollection == null || headerCollection.size() == 0) {
        log.info("No invoices for " + division);
      }
      else {
        File outFile = new File(invoiceFilePath);
        File xslFile = new File(utcLibrary.getString("cr658.xslfile." + division.toLowerCase()));
        if(log.isDebugEnabled()) {
          log.debug("Invoice file name:" + invoiceFilePath);
        }
        Collection itemCollection = this.getItemRecords(division);
        BigDecimal headerCount = new BigDecimal(headerCollection.size());
        BigDecimal itemCount = new BigDecimal(itemCollection.size());
        if(log.isDebugEnabled()) {
          log.debug("Found " + headerCount + " header records and " +
                    itemCount + " item records");
        }
        Collection c = this.mergeRecords(headerCollection,
                                         itemCollection);
        BigDecimal invoiceSum = this.getTotalSum(headerCollection);

        File xmlFile = File.createTempFile("XML", null);
        FileWriter fileWriter = new FileWriter(xmlFile);
        BeanWriter beanWriter = new BeanWriter(fileWriter);

        // Configure betwixt
        // For more details see java docs or later in the main documentation
        beanWriter.getXMLIntrospector().getConfiguration().
            setAttributesForPrimitives(false);
        beanWriter.getBindingConfiguration().setMapIDs(false);
        beanWriter.setEndOfLine("");

        beanWriter.write("invoices", c);
        fileWriter.flush();
        fileWriter.close();
        //for whatever reason I can't write everything to one file at once
        //so I'm doing it in different sections
        //now create full xml file
        File fullXmlFile = new File(utcLibrary.getString("cr658.invoicefiledir." + division.toLowerCase()) + "data.xml");
        //File fullXmlFile = File.createTempFile("XML", null);
        FileWriter fw = new FileWriter(fullXmlFile);
        fw.write("<root>");
        fw.write("<company>");
        fw.write(utcLibrary.getString("company." + division.toLowerCase()));
        fw.write("</company>");
        fw.write("<cust-prefix>");
        fw.write(utcLibrary.getString("cust.prefix." + division.toLowerCase()));
        fw.write("</cust-prefix>");
        fw.write("<current-date-time>");
        fw.write(new Date().toString());
        fw.write("</current-date-time>");
        fw.write("<collno>");
        fw.write(this.getCollectionNumber(division).toString());
        fw.write("</collno>");
        //append file
        FileReader fr = new FileReader(xmlFile);
        int readChar = 0;
        while (readChar != -1) {
          readChar = fr.read();
          if (readChar != -1) {
            fw.write(readChar);
          }
        }

        fw.write("<TRAILER>");
        fw.write("<INV_CNT>");
        fw.write(headerCount.toString());
        fw.write("</INV_CNT>");
        fw.write("<INV_HASH>");
        fw.write(invoiceSum.toString());
        fw.write("</INV_HASH>");
        fw.write("<INV_ITM_CNT>");
        fw.write(itemCount.toString());
        fw.write("</INV_ITM_CNT>");
        fw.write("<INV_ITM_HASH>");
        fw.write(invoiceSum.toString());
        fw.write("</INV_ITM_HASH>");
        fw.write("</TRAILER>");
        fw.write("</root>");
        fw.flush();
        fw.close();
        
        javax.xml.transform.stream.StreamSource source = new javax.xml.
            transform.stream.StreamSource(fullXmlFile);
        if(log.isDebugEnabled()) {
          log.debug("File path:" + outFile.getAbsolutePath());
        }
        FileWriter fileWriter2 = new FileWriter(outFile);
        javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(fileWriter2);
        //javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(outFile);
        com.tcmis.common.util.TcmisTransformer.transform(source, xslFile,
            result);

        this.encryptAndSendInvoices(division);
        //the reason for this uglyness is there is something strange going on...
        try {
            this.updateSubmissionHistory(headerCollection);
        }
        catch(Exception e) {
            log.error("There was an error updating submission history", e);
        }
        try {
            this.incrementCollectionNumber(division);
        }
        catch(Exception e) {
            log.error("There was an error incrementing the collection number", e);
        }
        try {
            this.sendNotification(division, headerCount);
        }
        catch(Exception e) {
            log.error("There was an error sending notification emails", e);
        }

      }
    }
    catch (Exception e) {
      log.error("Error creating invoices for " + division + ":" + e.getMessage(), e);
      throw new BaseException(e);
    }
  }

  private Collection getHeaderRecords(String division) throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    Cr658HeaderViewBeanFactory factory = new Cr658HeaderViewBeanFactory(
        dbManager);
    SearchCriteria criteria = new SearchCriteria("facilityId", SearchCriterion.LIKE, division);
    criteria.addCriterion("counter", SearchCriterion.LESS_THAN, "500");
    return factory.select(criteria);
  }

  private Collection getItemRecords(String division) throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    Cr658ItemViewBeanFactory factory = new Cr658ItemViewBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria("facilityId", SearchCriterion.LIKE, division);
    criteria.addCriterion("counter", SearchCriterion.LESS_THAN, "500");
    return factory.select(criteria);

  }

  private Collection mergeRecords(Collection headerCollection,
                                  Collection itemCollection) throws
      BaseException {
    try {
      Iterator headerIterator = headerCollection.iterator();
      while (headerIterator.hasNext()) {
        Cr658HeaderViewBean headerBean = (Cr658HeaderViewBean) headerIterator.
            next();
        Iterator itemIterator = itemCollection.iterator();
        while (itemIterator.hasNext()) {
          Cr658ItemViewBean itemBean = (Cr658ItemViewBean) itemIterator.next();
          if (headerBean.getXblnr().equalsIgnoreCase(itemBean.getXblnr())) {
            headerBean.addCr658ItemViewBean((Cr658ItemViewBean)itemBean.clone());
          }
        }
      }

    }
    catch (Exception e) {
      log.error("", e);
      MailProcess.sendEmail("deverror@tcmis.com",
                            null,
                            "deverror@tcmis.com",
                            "UTC error",
                            "error." + e.getMessage());
      throw new BaseException(e);
    }
    return headerCollection;
  }

  private BigDecimal getTotalSum(Collection headerCollection) throws BaseException {
    BigDecimal sum = new BigDecimal("0");
      Iterator headerIterator = headerCollection.iterator();
      while (headerIterator.hasNext()) {
        Cr658HeaderViewBean headerBean = (Cr658HeaderViewBean) headerIterator.next();
        sum = sum.add(headerBean.getWrbtr());
      }
    return sum;

  }

  private String getInvoiceFileName(String division) throws BaseException {
    //2006-08-29T05:00-MERCATOR.SAC.FTPU.TCMIS.BINVCRD
    if("PWA".equalsIgnoreCase(division)) {
        return utcLibrary.getString("cr658.invoicefiledir." + division.toLowerCase()) + utcLibrary.getString("base.filename." + division.toLowerCase()) + DateHandler.formatDate(new Date(), "yyyyMMdd");
    }
    else {
        return utcLibrary.getString("cr658.invoicefiledir." + division.toLowerCase()) + DateHandler.formatDate(new Date(), "yyyy-MM-dd'T'hh:mm") + "-" + utcLibrary.getString("base.filename." + division.toLowerCase());
    }
//return "C:\\temp\\utc\\pratt_new_format";
  }

  private BigDecimal getCollectionNumber(String division) throws BaseException {
    BigDecimal collectionNumber = null;
    DbManager dbManager = new DbManager(getClient());
    Cr658CollectionNumberBeanFactory factory = new Cr658CollectionNumberBeanFactory(dbManager);
    Iterator iterator = factory.select(new SearchCriteria("division", SearchCriterion.EQUALS, division)).iterator();
    while(iterator.hasNext()) {
      Cr658CollectionNumberBean bean = (Cr658CollectionNumberBean)iterator.next();
      collectionNumber = bean.getCollno();
    }
    return collectionNumber;
  }

  private int incrementCollectionNumber(String division) throws BaseException {
    Cr658CollectionNumberBean bean = new Cr658CollectionNumberBean();
    bean.setDivision(division);
    bean.setCollno(this.getCollectionNumber(division).add(new BigDecimal("1")));
    DbManager dbManager = new DbManager(getClient());
    Cr658CollectionNumberBeanFactory factory = new Cr658CollectionNumberBeanFactory(dbManager);
    return factory.update(bean);
  }

  private void encryptAndSendInvoices(String division) throws BaseException {
    //I need this since pwa is different now
    String fileDate = "";
    if("PWA".equalsIgnoreCase(division)) {
        fileDate = DateHandler.formatDate(new Date(), "yyyyMMdd");
    }
    try {
      File plaintext = new File(invoiceFilePath);
      File ciphertext = new File(invoiceFilePath + ".gpg");
      File feedback = new File(invoiceFilePath + ".chk");
      feedback.createNewFile();
      if("PWA".equalsIgnoreCase(division)) {
          EncryptHandler.encrypt(plaintext.toURL(), ciphertext.toURL(), utcLibrary.getString("pwa.public.key.id"));
      }
      else {
        EncryptHandler.encrypt(plaintext.toURL(), ciphertext.toURL(), utcLibrary.getString("public.key.id"));
      }
      if(log.isDebugEnabled()) {
        log.debug("Done with encryption");
      }

      FtpClient ftpClient = new FtpClient(utcLibrary.getString("ftp.host"),
                                          utcLibrary.getString("ftp.user." + division.toLowerCase()),
                                          utcLibrary.getString("ftp.password." + division.toLowerCase()));
      ftpClient.setFileType(FtpClient.BINARY_FILE_TYPE);
      int tryCount = 0;
      do {
        if(log.isDebugEnabled()) {
          log.debug("Ftp file try " + tryCount);
        }
        if(tryCount > 0) {
          //if I'm trying to ftp again I'll remove the old file
          //to get around the $filename.1 issue
          ftpClient.remove(utcLibrary.getString("base.filename." + division.toLowerCase()) + fileDate);
        }
        FileInputStream fis = new FileInputStream(ciphertext);
        ftpClient.put(utcLibrary.getString("base.filename." + division.toLowerCase()) + fileDate, fis,false);
        //fis.close();
        ftpClient.get(utcLibrary.getString("base.filename." +  division.toLowerCase()) + fileDate,
                      new FileOutputStream(feedback));
      }
      while (!FileHandler.isDuplicate(ciphertext, feedback)
             && tryCount++ < 3);

      if (tryCount >= 3) {
        throw new BaseException("Round trip FTP verification failed for: " +
                                ciphertext.getCanonicalPath());
      }
    }
    catch(Exception e) {
      log.error("Error when encrypting and sending invoices.", e);
      throw new BaseException(e);
    }
  }

  private void updateSubmissionHistory(Collection cr658HeaderViewBeanCollection) throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    InvoiceSubmissionBeanFactory factory = new InvoiceSubmissionBeanFactory(dbManager);
    Iterator it = cr658HeaderViewBeanCollection.iterator();
    while(it.hasNext()) {
      Cr658HeaderViewBean headerBean = (Cr658HeaderViewBean)it.next();
      InvoiceSubmissionBean invoiceSubmissionBean = new InvoiceSubmissionBean();
      invoiceSubmissionBean.setDateSent(new Date());
      invoiceSubmissionBean.setInvoice(headerBean.getInvoice());
      invoiceSubmissionBean.setIssueId(headerBean.getIssueId());
      invoiceSubmissionBean.setIssueCostRevision(headerBean.getIssueCostRevision());
      invoiceSubmissionBean.setFacilityId(headerBean.getFacilityId());
      invoiceSubmissionBean.setXblnr(headerBean.getXblnr());
      factory.insert(invoiceSubmissionBean);
    }
  }

  private void sendNotification(String division, BigDecimal invoiceCount) throws BaseException {
      if("PWA".equalsIgnoreCase(division)) {
        String[] to = {"kevin.dunn@pw.utc.com", "Tcmis-support@haastcm.com"};
        //String[] to = {"mwennberg@haastcm.com"};
        String[] cc = new String[0];
        String replyTo = "Tcmis-support@haastcm.com";
        String subject = invoiceCount + " Haas invoices submitted for " + division;
        String msg = "Please contact Tcmis-support@haastcm.com if you have any questions, comments, or concerns.";
        MailProcess.sendEmail(to, cc, replyTo, subject, msg);
      }
      else {
        String[] to = {"bspsupport@utc.com", "sapbatch@us.ibm.com", "Tcmis-support@haastcm.com", "BSPHUB@ITWORX.com"};
        //String[] to = {"mwennberg@haastcm.com"};
        String[] cc = new String[0];
        String replyTo = "Tcmis-support@haastcm.com";
        String subject = invoiceCount + " Haas invoices submitted for " + division;
        String msg = "Please contact Tcmis-support@haastcm.com if you have any questions, comments, or concerns.";
        MailProcess.sendEmail(to, cc, replyTo, subject, msg);
      }
  }

}