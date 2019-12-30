package com.tcmis.client.utc.process;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.tcmis.internal.invoice.beans.InvoicePaymentStageBean;
import com.tcmis.internal.invoice.factory.InvoicePaymentStageBeanFactory;
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

public class InvoicesPaidProcess
    extends BaseProcess {

  private String[] divisionArray = {
      "PW", "HS", "SAC"};
  private String hsInvoiceFileName;
  private String pwInvoiceFileName;
  private String sacInvoiceFileName;
  private File dir;
  ResourceLibrary utcLibrary = null;

  public InvoicesPaidProcess(String client) {
    super(client);
    utcLibrary = new ResourceLibrary("utc");
  }

  public void processChecks() throws BaseException {
    this.setFileNames();
    dir = new File(utcLibrary.getString("homedir"));
    this.getFiles();
    //check if directory to move processed files exists
    File processedDir = new File(dir.getAbsolutePath() + "/processed/");
    if (!processedDir.exists()) {
      processedDir.mkdir();
    }
    UtcCheckFileFilter filter = new UtcCheckFileFilter();
    try {
      if (log.isInfoEnabled()) {
        log.info("Checking for UTC invoice check payment file");
      }
      File[] files = dir.listFiles(filter);
      if (log.isInfoEnabled()) {
        log.info("Found " + files.length + " UTC invoice check payment file");
      }
      for (int i = 0; i < files.length; i++) {
        this.load(files[i].getAbsolutePath());
        FileHandler.move(files[i].getAbsolutePath(),
                         processedDir.getAbsolutePath() + "/" +
                         files[i].getName() + "." + new Date());
      }
      //if everything went well we'll delete the files from the UTC server
      this.removeFiles();
      if (log.isInfoEnabled()) {
        log.info("Done with for UTC invoice check payment file");
      }
    }
    catch (Exception e) {
      log.error("Error loading UTC check payment data", e);
      try {
        MailProcess.sendEmail("deverror@tcmis.com", null,
                              "deverror@tcmis.com",
                              "UTC invoice check payment error",
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
    this.hsInvoiceFileName = "Invoices_CheckInfo_HAAS_HS_" +
        DateHandler.formatDate(new Date(), "yyyyMMdd") + ".txt";
    this.pwInvoiceFileName = "Invoices_CheckInfo_HAAS_PW_" +
        DateHandler.formatDate(new Date(), "yyyyMMdd") + ".txt";
    this.sacInvoiceFileName = "Invoices_CheckInfo_HAAS_SAC_" +
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
      log.error("Error getting invoice check payment file from UTC", e);
      MailProcess.sendEmail("deverror@tcmis.com",
                            null,
                            "deverror@tcmis.com",
                            "UTC invoice check payment error",
                            "Could not get check payment file from UTC." + e.getMessage());
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
      log.error("Error removing invoice check payment file from UTC", e);
      MailProcess.sendEmail("deverror@tcmis.com",
                            null,
                            "deverror@tcmis.com",
                            "UTC invoice check payment error",
                            "Could not remove check payment file from UTC." + e.getMessage());
      throw new BaseException(e);
    }
  }

  public void load(String filePath) throws BaseException {
    Connection connection = null;
    DbManager dbManager = null;
    try {
      File file = new File(filePath);
      //check if it's an empty file
      if (log.isDebugEnabled()) {
        log.debug("File length:" + file.length());
      }
      if (file.length() > 0) {
        dbManager = new DbManager(getClient());
        InvoicePaymentStageBeanFactory factory = new
            InvoicePaymentStageBeanFactory(dbManager);
        InvoicePaymentStageBean bean = new InvoicePaymentStageBean();
        connection = dbManager.getConnection();
        connection.setAutoCommit(false);
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;
        ArrayList arrayList = new ArrayList();
        //each line should have the following data
        //Supplier's Invoice Number, Supplier's Invoice Date, Line Total, Check No, Check Amount, Check Date, Currency
//int lineCount = 0;
        while ( (line = in.readLine()) != null) {
          bean.setPayeeCompanyId("HAAS CORPORATION");
          bean.setPayerCompanyId("UTC");
          bean.setTransactionCreationDate(new Date());
          String[] values = line.split(",");
          bean.setInvoiceNumber(values[0]);
          bean.setInvoiceDate(DateHandler.getDateFromString("yyyyMMdd", values[1]));
          bean.setInvoiceAmount(new BigDecimal(values[2]));
          bean.setTransactionReferenceNumber(values[3]);
          bean.setTotalAmountPaid(new BigDecimal(values[4]));
          bean.setCheckIssueDate(DateHandler.getDateFromString("yyyyMMdd", values[5]));
          bean.setCurrencyId(values[6]);
          //check if this check has previously been processed in another batch
          if(!arrayList.contains(values[3])) {
            if(factory.doesCheckExist(bean.getTransactionReferenceNumber())) {
              log.error("This check number has already been processed:" +
                        bean.getTransactionReferenceNumber());
              throw new BaseException("This check number has already been processed:" +
                                      bean.getTransactionReferenceNumber());
            }
            else {
              arrayList.add(values[3]);
            }
          }
          factory.insert(bean, connection);
//lineCount++;
//System.out.println("LINE:" + lineCount);
        }
        connection.commit();
      }
    }
    catch (Exception e) {
      log.error("UTC invoice check payment error:" + e.getMessage());
      try {
        if (connection != null) {
          connection.rollback();
        }
      }
      catch (SQLException sqle) {
        log.error("Error rolling back UTC invoice check payment:" +
                  sqle.getMessage());
      }
      BaseException be = new BaseException("UTC invoice check payment error.");
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
        log.error("Error returning connection for UTC invoice check payment:" +
                  sqle.getMessage());
      }
    }
  }

}