package com.tcmis.client.raytheon.process;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.Timestamp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.math.BigDecimal;
import com.tcmis.common.util.FileHandler;


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
import com.tcmis.client.raytheon.beans.FtpMaterialRequisitionBean;
import com.tcmis.client.raytheon.factory.FtpMaterialRequisitionBeanFactory;

/******************************************************************************
 * Process to load ftp chemical ordering data from Raytheon.
 * @version 1.0
 *****************************************************************************/
public class FtpMaterialRequisitionProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FtpMaterialRequisitionProcess(String client) {
    super(client);
  }

  public void load(String facility) throws Exception {
    ResourceLibrary fptMaterialRequisitionLibrary = new ResourceLibrary("FtpMaterialRequisition");
    String localPath = fptMaterialRequisitionLibrary.getString(facility.toLowerCase()+".local.dir");
    String localPathProcessed = fptMaterialRequisitionLibrary.getString(facility.toLowerCase()+".local.dir.processed");
    String delimiter = fptMaterialRequisitionLibrary.getString(facility.toLowerCase()+".delimiter");
    try {
      File f2 = new File(localPath);
      String[] list = f2.list();
      String fileName = "";
      for (int k =  0; k < list.length; k++) {
        fileName = list[k];
        //only process *.htm files
        if (fileName.indexOf(".txt") < 0) {
          continue;
        }
        //check to see if file exist
        File f = new File(localPath + fileName);
        if (!f.exists()) {
          log.info("ftp chemical ordering no file found: " + localPath + fileName);
          continue;
        }
        //check to see if file contains data
        if (f.length() <= 0) {
          log.info("ftp chemical ordering file is empty: " + localPath + fileName);
          continue;
        }
        Collection c = readFile(localPath, fileName, facility, delimiter);
        Iterator dataIter = c.iterator();
        //insert data into stage table
        DbManager dbManager = new DbManager(getClient());
        FtpMaterialRequisitionBeanFactory factory = new FtpMaterialRequisitionBeanFactory(dbManager);
        while (dataIter.hasNext()) {
          FtpMaterialRequisitionBean ftpMaterialRequisitionBean = (FtpMaterialRequisitionBean) dataIter.next();
          //if no row was insert
          if (factory.insert(ftpMaterialRequisitionBean) < 1) {
            MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "Ftp Chemical Ordering inserting data error", fileName);
          }
        }
        //move file to processed directory
        File source = new File(localPath+fileName);
        File destination = new File(localPathProcessed+fileName);
        FileHandler.move(source,destination);
        //call procedure to create MR
        createMR();
      } //end of for each file
    } catch (Exception e) {
      log.error("Error loading ftp chemical ordering data", e);
      throw e;
    }
  } //end of method

  public void createMR() throws Exception {
    try {
      DbManager dbManager = new DbManager(getClient());
      FtpMaterialRequisitionBeanFactory factory = new FtpMaterialRequisitionBeanFactory(dbManager);
      factory.doProcedure("p_raytheon_ftp_order", new Vector(0));
    }catch (Exception e) {
      log.error("Error calling p_raytheon_ftp_order", e);
      throw e;
    }
  } //end of method

  public Collection readFile(String localPath,String fileName, String facility, String delimiter) throws Exception {
    Collection ftpMaterialRequisitionBeanColl = new Vector();
    final int CAT_PART_NO_COL = 0;
    final int ORDER_QUANTITY_COL = 1;
    final int APPLICATION_COL = 2;
    final int CHARGE_NO_COL = 3;
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(localPath+fileName));
      if (bufferedReader != null) {
        String line = null;
        int count = 1;
        while((line = bufferedReader.readLine())!=null) {
          String[] result = line.split(delimiter);
          if (result.length != CHARGE_NO_COL+1) {
            continue;
          }
          //fill bean
          FtpMaterialRequisitionBean ftpMaterialRequisitionBean = new FtpMaterialRequisitionBean();
          ftpMaterialRequisitionBean.setFtpFilename(fileName);
          ftpMaterialRequisitionBean.setFileLine(new BigDecimal(count));
          ftpMaterialRequisitionBean.setFacilityId(facility);
          ftpMaterialRequisitionBean.setApplication(result[APPLICATION_COL]);
          ftpMaterialRequisitionBean.setCatPartNo(result[CAT_PART_NO_COL]);
          ftpMaterialRequisitionBean.setChargeNo(result[CHARGE_NO_COL]);
          ftpMaterialRequisitionBean.setOrderQuantity(new BigDecimal(result[ORDER_QUANTITY_COL]));
          ftpMaterialRequisitionBean.setTcmLoadDate(new Date((Calendar.getInstance()).getTimeInMillis()));
          ftpMaterialRequisitionBean.setProcessedStatus("NEW");
          ftpMaterialRequisitionBeanColl.add(ftpMaterialRequisitionBean);
          count++;
        }
        bufferedReader.close();
      }
    }catch (Exception e) {
      throw e;
    }
    return ftpMaterialRequisitionBeanColl;
  } //end of method

} //end of class
