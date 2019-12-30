package com.tcmis.client.fec.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.fec.beans.ChemicalOnHandBean;
import com.tcmis.client.fec.factory.ChemicalOnHandBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Process to load FMC data from Continental.
 * @version 1.0
 *****************************************************************************/
public class ChemicalOnHandProcess
    extends BaseProcess {

  Log log = LogFactory.getLog(this.getClass());
  private static final String TO_EMAIL = "deverror@haastcm.com";

  public ChemicalOnHandProcess(String client) {
    super(client);
  }

  /******************************************************************************
   * Looks for files to load, if there are any loads them using the 
   * load(String filePath) method and calls the P_FMC_EXTRACT_PROCESS procedure.<BR>
   *
   ****************************************************************************/
  public void load() {
    ResourceLibrary fecLibrary = new ResourceLibrary("fec");
    String fileName = null;
    File dir = new File(fecLibrary.getString("homedir"));
    //check if directory to move processed files exists
    //File processedDir = new File(dir.getAbsolutePath() + "/processed/");
    //if (!processedDir.exists()) {
    //  processedDir.mkdir();
    //}
    ChemicalOnHandFileFilter filter = new ChemicalOnHandFileFilter();
    try {
      log.info("Checking for chemical onhand file");
      File[] files = dir.listFiles(filter);
      if(files.length < 1) {
        MailProcess.sendEmail(TO_EMAIL, "", "tcmis-batch@haastcm.com", "No chemical on hand data file.", "No new chemical on hand data file was found.");
      }
      for (int i = 0; i < files.length; i++) {
        this.load(files[i].getAbsolutePath());
        //FileHandler.move(files[i].getAbsolutePath(),
        //                 processedDir.getAbsolutePath() + "/" +
        //                 files[i].getName() + "." + new Date());
      }
      log.info("Done with for chemical onhand file");
    }
    catch (Exception e) {
      log.error("Error loading chemical onhand data", e);
      try {
        MailProcess.sendEmail("deverror@tcmis.com", null,
                              "deverror@tcmis.com",
                              "Fec chemical on hand load error",
                              "See log file.");
      }
      catch (Exception ex) {
        log.error("Error sending error mail for FEC.", e);
      }
    }
  }

  /******************************************************************************
   * Parses the file in the given path and inserts the data into the 
   * CHEMICAL_ON_HAND table.<BR>
   *
   * @param filePath  path to the file to parse
   * @throws BaseException If an <code>SQLException</code> is thrown
   ****************************************************************************/
  private void load(String filePath) throws BaseException {
    try {
      //Vector data = new Vector();
      ChemicalOnHandBean bean = new ChemicalOnHandBean();
      DbManager dbManager = new DbManager(getClient());
      ChemicalOnHandBeanFactory factory = new ChemicalOnHandBeanFactory(dbManager);
      File file = new File(filePath);
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String line = "";
      int lineCount = 0;
      while(line != null) {
        line = br.readLine();
        if(line != null) {
          if (lineCount == 0) {
            //do something with the header
          }
          else {
            //load data
            String[] data = line.split(",");
            if(data[0] != null) {
              bean.setOnHandDate(data[0].trim());
            }
            if(data[1] != null) {
              bean.setPartNumber(data[1].trim());
            }
            if(data[2] != null) {
              bean.setFacility(data[2].trim());
            }
            if(data[3] != null) {
              bean.setQuantityOnhand(new BigDecimal(data[3].trim()));
            }
            if(data[4] != null) {
              bean.setUom(data[4].trim());
            }
            bean.setFilename(filePath);
            bean.setLoadDate(new Date());
            factory.insert(bean);
          }
        }
        lineCount++;
      }
    }
    catch (Exception e) {
      BaseException be = new BaseException(e);
      be.setRootCause(e);
      throw be;
    }
  }

}