package com.tcmis.client.swa.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.Timestamp;

import java.sql.Connection;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RegexMatcher;
import org.apache.commons.digester.RegexRules;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.SimpleRegexMatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.client.swa.beans.MatlIctStageBean;
import com.tcmis.client.swa.factory.MatlIctStageBeanFactory;
import com.tcmis.common.admin.process.MailProcess;

/******************************************************************************
 * Process to load *.ICT data from SWA.
 * @version 1.0
 *****************************************************************************/
public class IctFeedProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public IctFeedProcess(String client) {
    super(client);
  }

  private final int ISSUE_DOCUMENT_NUMBER_COL = 0;
  private final int DOCUMENT_TYPE_COL = 1;
  private final int PART_NUMBER_COL = 2;
  private final int NUMBER_REQUESTED_COL = 3;
  private final int NUMBER_ISSUED_COL = 4;
  private final int CUMULATIVE_RECEIVED_COL = 5;
  private final int REQUESTING_EMPLOYEE_ID_COL = 6;
  private final int STATUS_COL = 7;
  private final int ISSUE_CREDIT_TRANSFER_DATE_COL = 8;
  private final int ISSUE_CREDIT_TRANSFER_TIME_COL = 9;
  private final int ACN_COL = 10;
  private final int SHIPPING_STATION_COL = 11;
  private final int SHIPPING_DEPARTMENT_COL = 12;
  private final int SHIPMENT_DATE_COL = 13;
  private final int SHIPMENT_TIME_COL = 14;
  private final int RECEIVING_STATION_COL = 15;
  private final int RECEIVING_DEPARTMENT_COL = 16;

  private final int totalColumns = 17;

  public void load() throws Exception {
    DbManager dbManager = new DbManager(getClient());
    Connection connection = dbManager.getConnection();
    ResourceLibrary swaFeedLibrary = new ResourceLibrary("swafeed");
    String filePath = swaFeedLibrary.getString("ict.ftp.dir"); //"c:\\generatedjava\\swa\\";//12061501.ICT";
    String toFilePath = swaFeedLibrary.getString("ict.ftp.processed.dir"); //"c:\\generatedjava\\swa\\processed\\";
    String fileName = "test.txt";
    try {
      //get all files in diretory
      File f2 = new File(filePath);
      String[] list = f2.list();
      for (int k = 0; k < list.length; k++) {
        fileName = list[k];
        //only process *.ICT files
        if (fileName.indexOf(".ICT") < 0) {
          continue;
        }

        //check to see if file exist
        File f = new File(filePath + fileName);
        if (!f.exists()) {
          log.error("-------SWA ICT no file found: " + fileName);
          return;
        }

        //move file to it place
        FileHandler.move(filePath + fileName, toFilePath + fileName);

        connection.setAutoCommit(false);
        ReadDataFromFile readDataFromFile = new ReadDataFromFile();
        Collection c = readFile(toFilePath, fileName);
        Iterator dataIter = c.iterator();
        //insert data into stage table
        MatlIctStageBeanFactory factory = new MatlIctStageBeanFactory(dbManager);
        int count = 0;
        while (dataIter.hasNext()) {
          MatlIctStageBean matlIctStageBean = (MatlIctStageBean) dataIter.next();
//          System.out.println("count:"+ (++count));
          factory.insert(matlIctStageBean, connection);
        }
        //call procedure if there are data
        if (c.size() > 0) {
          Vector out = new Vector(1);
          out.add("");
          factory.doProcedure(connection, "p_matl_ict_load", out);
        }
      }
    } catch (Exception e) {
      log.error("Error loading SWA *.ICT data", e);
      throw e;
    } finally {
      connection.setAutoCommit(true);
    }

  } //end of method

  Collection readFile(String filePath, String fileName) throws Exception {
    Collection c = new Vector();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + fileName));
      boolean failed = false;
      if (bufferedReader != null) {
        String line = null;
        int startingIndex = 0;
        while ((line = bufferedReader.readLine()) != null) {
          MatlIctStageBean matlIctStageBean = new MatlIctStageBean();
          String[] tmp = line.split("\t"); //tab delimiter
          if (tmp.length == totalColumns) {
            matlIctStageBean.setIssueDocumentNumber(new BigDecimal(tmp[ISSUE_DOCUMENT_NUMBER_COL].trim()));
            matlIctStageBean.setDocumentType(tmp[DOCUMENT_TYPE_COL].trim());
            matlIctStageBean.setPartNumber(tmp[PART_NUMBER_COL].trim());
            matlIctStageBean.setNumberRequested(new BigDecimal(tmp[NUMBER_REQUESTED_COL].trim()));
            matlIctStageBean.setNumberIssued(new BigDecimal(tmp[NUMBER_ISSUED_COL].trim()));
            matlIctStageBean.setRequestingEmployeeId(new BigDecimal(tmp[REQUESTING_EMPLOYEE_ID_COL].trim()));
            matlIctStageBean.setStatus(new BigDecimal(tmp[STATUS_COL].trim()));
            matlIctStageBean.setIssueCreditTransferDate(DateHandler.getDateFromString("MM/dd/yyyy HH:mm", convertMMDDYYtoMMDDYYYY(tmp[ISSUE_CREDIT_TRANSFER_DATE_COL].trim()) + " " + tmp[ISSUE_CREDIT_TRANSFER_TIME_COL].trim()));
            matlIctStageBean.setAcn(tmp[ACN_COL].trim());
            matlIctStageBean.setShippingStation(tmp[SHIPPING_STATION_COL].trim());
            matlIctStageBean.setShippingDepartment(tmp[SHIPPING_DEPARTMENT_COL].trim());
            matlIctStageBean.setShipmentDate(DateHandler.getDateFromString("MM/dd/yyyy HH:mm", convertMMDDYYtoMMDDYYYY(tmp[SHIPMENT_DATE_COL].trim()) + " " + tmp[SHIPMENT_TIME_COL].trim()));
            matlIctStageBean.setReceivingStation(tmp[RECEIVING_STATION_COL].trim());
            matlIctStageBean.setReceivingDepartment(tmp[RECEIVING_DEPARTMENT_COL].trim());
            Calendar calendar = Calendar.getInstance();
            matlIctStageBean.setFileName(fileName);
            matlIctStageBean.setCumulativeReceived(new BigDecimal(tmp[CUMULATIVE_RECEIVED_COL].trim()));
            c.add(matlIctStageBean);
          } else {
            failed = true;
            break;
          }
        }
        bufferedReader.close();
      }
      //send email
      if (failed) {
        //don't process any records in file
        log.debug("FAILED");
        c = new Vector();
        MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "SWA " + fileName + " data load error", "See log file.");
      }
    } catch (Exception e) {
      throw e;
    }
    return c;
  } //end of methd

  //append "20" in front of input YY
  String convertMMDDYYtoMMDDYYYY(String dateWithYY) {
    String result = dateWithYY;
    if (dateWithYY != null ) {
      if (dateWithYY.indexOf("/") > 0) {
        String[] tmp = dateWithYY.split("/");
        result = tmp[0] + "/" + tmp[1] + "/20" + tmp[2];
      }
    }
    return result;
  } //end of method
  public static void main(String[] argv) {
	  try {
	  IctFeedProcess proc = new IctFeedProcess("TCM_OPS");
	  proc.load();
	  } catch(Exception ex) {
		  ex.printStackTrace();
	  }
  }
} //end of class
