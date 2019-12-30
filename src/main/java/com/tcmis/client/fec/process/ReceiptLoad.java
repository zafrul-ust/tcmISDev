package com.tcmis.client.fec.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.fec.beans.FecReceiptStageOvBean;
import com.tcmis.client.fec.factory.FecReceiptStageOvBeanFactory;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.ftp.FtpInputHandler;
import com.tcmis.common.ftp.SQLInputFromCSVLine;
import com.tcmis.common.types.FeedDateFormats;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

public class ReceiptLoad extends FtpInputHandler {
   
  private FecReceiptStageOvBeanFactory receiptStageBeanFactory;

  public ReceiptLoad() {
    super();
  }

  protected boolean lockIt() {
     return true;
  }
  
  protected boolean unlockIt() {
     return true;
  }
  
  protected void init() throws Exception {
    log = LogFactory.getLog(this.getClass());
    resourceLib = new ResourceLibrary("ReceiptLoad");
    client = resourceLib.getString("db.client");
    beanObjectName = resourceLib.getString("oracle.object.name");
    dbManager = new DbManager(client);
    receiptStageBeanFactory = new FecReceiptStageOvBeanFactory(dbManager);
  }
  
  protected void destroy() throws Exception {
     receiptStageBeanFactory = null;
     dbManager=null;
  }  
  
  protected void processFile(String filename) throws Exception {
     int lineNum=0;
     String line="";
     FecReceiptStageOvBean receiptStageBean = null;
     SQLInputFromCSVLine csvSQLInput = null;
     clearFeed();
     BufferedReader reader = new BufferedReader( new FileReader( new File(filename) ));
     while (reader.ready() && line!=null) {
        try {           
           receiptStageBean = new FecReceiptStageOvBean();
           csvSQLInput = new SQLInputFromCSVLine(reader.readLine(),FeedDateFormats.CYM2D_NOSEP, ++lineNum, filename);
           receiptStageBean.readSQL(csvSQLInput,beanObjectName);           
           receiptStageBeanFactory.insert(receiptStageBean);                      
        } catch (IOException ioe) {
           line=null;
        }        
     }
     try {
        reader.close();        
     } catch (IOException ioe) {
        log.error("IOException closing file " + filename);
        log.error(ioe);
     }     
  }
  
  protected void processRecords() throws Exception {
     updateRecords();
     insertRecords();
  }
  
  private void clearFeed() throws Exception {
     SearchCriteria searchCriteria = new SearchCriteria();
     searchCriteria.addCriterion("1",SearchCriterion.EQUALS,"1");
     receiptStageBeanFactory.delete(searchCriteria);
  }
  
  private void updateRecords() throws Exception {
     
  }
  
  private void insertRecords() throws Exception {
     
  }
  
  private void updateRecord() throws Exception {
     
  }
  
  private void insertRecord() throws Exception {
     
  }
  
}
