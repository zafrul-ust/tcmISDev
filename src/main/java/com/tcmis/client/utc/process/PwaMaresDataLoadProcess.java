package com.tcmis.client.utc.process;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.Timestamp;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RegexMatcher;
import org.apache.commons.digester.RegexRules;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.SimpleRegexMatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.utc.beans.PwaMaresBean;
import com.tcmis.client.utc.factory.PwaMaresBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;

/******************************************************************************
 * Process to load MARES data from UTC.
 * @version 1.0
 *****************************************************************************/
public class PwaMaresDataLoadProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PwaMaresDataLoadProcess(String client) {
    super(client);
  }

  public void load() throws Exception {
    ResourceLibrary maresLibrary = new ResourceLibrary("PWAMaresReport");
    String fromFileName = "";
    String localFilePath = maresLibrary.getString("outbound.local.ftp.dir");
    String toFileName = "";
    String remoteFilePath = maresLibrary.getString("outbound.remote.ftp.dir");
    String baseName = maresLibrary.getString("outbound.filename");
    fromFileName = localFilePath+baseName;
    SimpleDateFormat prefixFmt = new SimpleDateFormat("yyyy-MM-dd-HH");
    String now = prefixFmt.format(new java.util.Date());
    toFileName = remoteFilePath+now+"-"+baseName;
    try {
        //check to see if file exist
        File f = new File(fromFileName);
        if (!f.exists()) {
          log.error("-------MARES no file found: "+fromFileName);
          return;
        }

		  //move file to it place
        FileHandler.move(fromFileName,toFileName);
        //load data from file
        ReadDataFromFile readDataFromFile = new ReadDataFromFile();
        Collection c = readDataFromFile.readFile(toFileName);
        Iterator dataIter = c.iterator();
        //insert data into stage table
        DbManager dbManager = new DbManager(getClient());
        PwaMaresBeanFactory factory = new PwaMaresBeanFactory(dbManager);
        while (dataIter.hasNext()) {
          PwaMaresBean pwaMaresBean = (PwaMaresBean)dataIter.next();
          factory.insert(pwaMaresBean);
        }
        //check to see if any duplicate and mark dup
        markDuplicateData();

        //process unprocessed data
        processDataFromStageTable();
    } catch (Exception e) {
      log.error("Error loading PWA MARES data", e);
      throw e;
    }
  } //end of method

  public void markDuplicateData() throws Exception {
    try {
      DbManager dbManager = new DbManager(getClient());
      PwaMaresBeanFactory factory = new PwaMaresBeanFactory(dbManager);
      factory.doProcedure("P_PWA_MARES_MARK_DUP", new Vector(0));
    }catch (Exception e) {
      log.error("Error marking dup on PWA MARES data", e);
      throw e;
    }
  } //end of method

  public void processDataFromStageTable() throws Exception {
    try {
		ResourceLibrary maresLibrary = new ResourceLibrary("PWAMaresReport");
		//documents path
		String documentFilePath = maresLibrary.getString("local.remote.ftp.dir");

		DbManager dbManager = new DbManager(getClient());
      PwaMaresBeanFactory factory = new PwaMaresBeanFactory(dbManager);
      Collection c = factory.selectUnProcessedData();
      Iterator dataIter = c.iterator();
      String emailMessage = "";
      while (dataIter.hasNext()) {
        PwaMaresBean pwaMaresBean = (PwaMaresBean)dataIter.next();
        //update receipt data
        String receiptID = pwaMaresBean.getShipmentId().trim();
        if (receiptID.length() > "yyyymmdd".length()) {
          Vector paramV = new Vector(4);
          receiptID = receiptID.substring(0,receiptID.length() - "yyyymmdd".length());
          paramV.addElement(new Integer(receiptID));
          paramV.addElement(pwaMaresBean.getMclReceivingNo().trim());
          //notes
          emailMessage += "Receipt_id: "+receiptID+"\n";
          String notes = "MARES status: "+pwaMaresBean.getDispositionCode();
          String tmp = pwaMaresBean.getRemark().trim();
          if (tmp != null) {
            if (tmp.length() > 0) {
              notes += "\n"+tmp;
            }
          }
          paramV.addElement(notes);
          //expiration date
          tmp = pwaMaresBean.getExpirationDate().trim();
          if (tmp != null) {
            if (tmp.length() > 0) {
              emailMessage += "Expiration: "+tmp+"\n";
              SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
              try {
                paramV.addElement(new Timestamp(simpleDateFormat.parse(tmp).getTime()));
              }catch (Exception ex) {
                log.error("Error processing PWA MARES data", ex);
					 paramV.addElement("");
				  }
            }else {
					paramV.addElement("");
				}
          }else {
				 paramV.addElement("");
			 }
			 String tmpLotStatus = "Passed Client Review";
			 if ("CXL".equalsIgnoreCase(pwaMaresBean.getDispositionCode()) || "REJ".equalsIgnoreCase(pwaMaresBean.getDispositionCode())) {
			 	tmpLotStatus = "Failed Client Review";
			 }
			 paramV.addElement(tmpLotStatus);
			 log.debug(paramV);
			 //call procedure to update receipt data
          factory.doProcedure("P_RECEIPT_CUSTOMER_RECEIPT_ID", paramV);
          //mark record as processed
          factory.updateStatus(pwaMaresBean);
          //email message
          emailMessage += notes+"\n\n";
			 //delete processed documents for receipt
			 File tmpFile = new File(documentFilePath);
			 String[] fileNameList = tmpFile.list();
			 String tmpFileName = "";
			 for (int j = 0; j < fileNameList.length; j++) {
			 	tmpFileName = fileNameList[j];
				if (tmpFileName.indexOf(receiptID) > -1) {
					File f = new File(documentFilePath + tmpFileName);
					f.delete();
				}
			 }
		  }
      }
      //send email to hub person
      if (emailMessage.length() > 0) {
        sendEmailToHub(emailMessage);
      }
    }catch (Exception e) {
      log.error("Error processing PWA MARES data from stage table", e);
      throw e;
    }
  } //end of method

  void sendEmailToHub(String emailMessage) throws Exception {
    try {
      DbManager dbManager = new DbManager(getClient());
      PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
      Collection c = factory.selectUsersEmailByGroup("All","MaresReleaser");
      String[] to = new String[c.size()];
      Iterator dataIter = c.iterator();
      int i = 0;
      boolean hasEmail = false;
      while (dataIter.hasNext()) {
        PersonnelBean personnelBean = (PersonnelBean) dataIter.next();
        if (personnelBean.getEmail() != null && personnelBean.getEmail().length() > 0) {
          to[i++] = personnelBean.getEmail();
          hasEmail = true;
        }
      }
      //if no one in group send email developers, otherwise send email to hub
      if (!hasEmail) {
        String[] toDev = {"deverror@tcmis.com"};
        MailProcess.sendEmail(toDev, new String[0], "PWA MARES - no member for MaresReleaser", emailMessage, false);
      }else {
        MailProcess.sendEmail(to, new String[0], "PWA MARES", emailMessage, true);
      }
    }catch (Exception ex) {
      log.error("Error sending error mail for PWA MARES (processing).", ex);
      throw ex;
    }
  } //end of method

} //end of class
