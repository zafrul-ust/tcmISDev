package com.tcmis.client.utc.process;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.tcmis.client.utc.beans.Cr658InvoiceReceivedBean;
import com.tcmis.client.utc.factory.Cr658InvoiceReceivedBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.ftp.FtpClient;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class InvoiceReconciliationProcess
    extends BaseProcess {

  private static short invoiceNumberCell = 0;
  private static short dateReceivedCell = 1;
  private static short invoiceAmountCell = 2;
  private String[] divisionArray = {
      "PW", "HS", "SAC"};
  private String hsInvoiceFileName;
  private String pwInvoiceFileName;
  private String sacInvoiceFileName;
  private File dir;
  ResourceLibrary utcLibrary = null;

  public InvoiceReconciliationProcess(String client) {
    super(client);
    utcLibrary = new ResourceLibrary("utc");
  }

  public void processInvoices() throws BaseException {
    this.setFileNames();
    dir = new File(utcLibrary.getString("homedir"));
    this.getFiles();
    //check if directory to move processed files exists
    File processedDir = new File(dir.getAbsolutePath() + "/processed/");
    if (!processedDir.exists()) {
      processedDir.mkdir();
    }
    UtcFileFilter filter = new UtcFileFilter();
    try {
      if (log.isInfoEnabled()) {
        log.info("Checking for UTC invoice reconciliation file");
      }
      File[] files = dir.listFiles(filter);
      if (log.isInfoEnabled()) {
        log.info("Found " + files.length + " UTC invoice reconciliation file");
      }
      for (int i = 0; i < files.length; i++) {
        this.load(files[i].getAbsolutePath());
        FileHandler.move(files[i].getAbsolutePath(),
                         processedDir.getAbsolutePath() + "/" +
                         files[i].getName() + "." + new Date());
      }
      //if everything went well we'll remove the files from the UTC server
      this.removeFiles();
      if (log.isInfoEnabled()) {
        log.info("Done with for UTC invoice reconciliation file");
      }
    }
    catch (Exception e) {
      log.error("Error loading UTC reconciliation data", e);
      try {
        MailProcess.sendEmail("deverror@tcmis.com", null,
                              "deverror@tcmis.com",
                              "UTC invoice reconciliation error",
                              "See log file." + e.getMessage());
      }
      catch (Exception ex) {
        log.error("Error sending error mail for UTC.", e);
      }
    }
  }

  private void setFileNames() throws BaseException {
    if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
      log.info(
          "This process is only supposed to run on Mondays, but I'll run the process anyways");
    }
    this.hsInvoiceFileName = "Invoices_HAAS_HS_" +
        DateHandler.formatDate(new Date(), "yyyyMMdd") + ".txt";
    this.pwInvoiceFileName = "Invoices_HAAS_PW_" +
        DateHandler.formatDate(new Date(), "yyyyMMdd") + ".txt";
    this.sacInvoiceFileName = "Invoices_HAAS_SAC_" +
        DateHandler.formatDate(new Date(), "yyyyMMdd") + ".txt";
  }

  private void getFiles() throws BaseException {
    try {
      FtpClient ftpClient = new FtpClient(utcLibrary.getString("ftpserver"),
                                          utcLibrary.getString("username"),
                                          utcLibrary.getString("password"));
      //ftpClient.setFileType(FtpClient.BINARY_FILE_TYPE);
      ftpClient.get(this.hsInvoiceFileName, new FileOutputStream(dir.getAbsolutePath() +
          "/" + this.hsInvoiceFileName));
      ftpClient.get(this.pwInvoiceFileName, new FileOutputStream(dir.getAbsolutePath() +
          "/" + this.pwInvoiceFileName));
      ftpClient.get(this.sacInvoiceFileName, new FileOutputStream(dir.getAbsolutePath() +
          "/" + this.sacInvoiceFileName));
      ftpClient.disconnect();
    }
    catch (Exception e) {
      log.error("Error getting invoice reconciliation file from UTC", e);
      MailProcess.sendEmail("deverror@tcmis.com",
                            null,
                            "deverror@tcmis.com",
                            "UTC invoice reconciliation error",
                            "Could not get reconciliation file from UTC." + e.getMessage());
      throw new BaseException(e);
    }
  }

  private void removeFiles() throws BaseException {
    try {
      FtpClient ftpClient = new FtpClient(utcLibrary.getString("ftpserver"),
                                          utcLibrary.getString("username"),
                                          utcLibrary.getString("password"));
      ftpClient.remove(this.hsInvoiceFileName);
      ftpClient.remove(this.pwInvoiceFileName);
      ftpClient.remove(this.sacInvoiceFileName);
      ftpClient.disconnect();
    }
    catch (Exception e) {
      log.error("Error removing invoice reconciliation file from UTC", e);
      MailProcess.sendEmail("deverror@tcmis.com",
                            null,
                            "deverror@tcmis.com",
                            "UTC invoice reconciliation error",
                            "Could not remove reconciliation file from UTC." + e.getMessage());
      throw new BaseException(e);
    }
  }

  private void load(String filePath) throws BaseException {
    Connection connection = null;
    DbManager dbManager = null;
    try {
      File file = new File(filePath);
      //check if it's an empty file
      if (log.isDebugEnabled()) {
        log.debug("File length:" + file.length());
      }
      if (file.length() > 0) {
        /*
                HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
                dbManager = new DbManager(getClient());
                Cr658InvoiceReceivedBeanFactory factory = new
                    Cr658InvoiceReceivedBeanFactory(dbManager);
                Cr658InvoiceReceivedBean bean = new Cr658InvoiceReceivedBean();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                connection = dbManager.getConnection();
                connection.setAutoCommit(false);
                for (int j = 0; j < divisionArray.length; j++) {
                  HSSFSheet sheet = wb.getSheet(divisionArray[j]);
                  int numberOfRows = sheet.getPhysicalNumberOfRows();
                  if (log.isInfoEnabled()) {
                    log.info("Found " + numberOfRows + " rows for " + divisionArray[j]);
                  }
                  for (int i = 1; i < numberOfRows; i++) {
                    HSSFRow row = sheet.getRow(i);
                    //unfortunately I can't count on the physical number of rows all having data
                    HSSFCell cell = row.getCell(dateReceivedCell);
                    if (cell != null &&
                        row.getCell(dateReceivedCell).getStringCellValue() != null &&
             row.getCell(dateReceivedCell).getStringCellValue().trim().length() > 0) {
                      bean.setDateReceived(simpleDateFormat.parse(row.getCell(
                          dateReceivedCell).getStringCellValue()));
             bean.setInvoiceAmount(new BigDecimal(row.getCell(invoiceAmountCell).
                          getNumericCellValue()));
                      bean.setXblnr(row.getCell(invoiceNumberCell).getStringCellValue());
                      factory.insert(bean, connection);
                    }
                  }
                }
         */
        dbManager = new DbManager(getClient());
        Cr658InvoiceReceivedBeanFactory factory = new
            Cr658InvoiceReceivedBeanFactory(dbManager);
        Cr658InvoiceReceivedBean bean = new Cr658InvoiceReceivedBean();
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        connection = dbManager.getConnection();
        connection.setAutoCommit(false);
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;
        while ( (line = in.readLine()) != null) {
          String[] values = line.split(",");
          bean.setXblnr(values[0]);
          bean.setDateReceived(DateHandler.getDateFromString("yyyyMMdd", values[1]));
          bean.setInvoiceAmount(new BigDecimal(values[2]));
          factory.insert(bean, connection);
          //System.out.println(values[0] + " " + values[1] + " " + values[2]);
        }
        connection.commit();
      }
    }
    catch (Exception e) {
      log.error("UTC invoice reconciliation error:" + e.getMessage());
      try {
        if (connection != null) {
          connection.rollback();
        }
      }
      catch (SQLException sqle) {
        log.error("Error rolling back UTC invoice reconciliation:" +
                  sqle.getMessage());
      }
      BaseException be = new BaseException("UTC invoice reconciliation error.");
      be.setRootCause(e);
      throw be;
    }
    finally {
      try {
        if (connection != null) {
          connection.setAutoCommit(true);
          dbManager.returnConnection(connection);
        }
      }
      catch (SQLException sqle) {
        log.error("Error returning connection for UTC invoice reconciliation:" +
                  sqle.getMessage());
      }
    }
  }

}
