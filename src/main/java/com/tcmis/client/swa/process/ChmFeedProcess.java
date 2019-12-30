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
import com.tcmis.client.swa.beans.MatlChmStageBean;
import com.tcmis.client.swa.factory.MatlChmStageBeanFactory;
import com.tcmis.common.admin.process.MailProcess;

/******************************************************************************
 * Process to load *.CHM data from SWA.
 * @version 1.0
 *****************************************************************************/
public class ChmFeedProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ChmFeedProcess(String client) {
    super(client);
  }

  private final int PART_NUMBER_COL = 0;
  private final int PART_NAME_COL = 1;
  private final int PART_DESC_COL = 2;
  private final int UNIT_OF_ISSUE_COL = 3;
  private final int INVENTORY_COL = 4;
  private final int CHEMICAL_FLAG_COL = 5;

  private final int totalColumns = 6;

  public void load() throws Exception {
    DbManager dbManager = new DbManager(getClient());
    Connection connection = dbManager.getConnection();
    ResourceLibrary swaFeedLibrary = new ResourceLibrary("swafeed");
    String filePath = swaFeedLibrary.getString("chm.ftp.dir");
    String toFilePath = swaFeedLibrary.getString("chm.ftp.processed.dir");
    String fileName = "test.txt";
    try {
      //get all files in diretory
      File f2 = new File(filePath);
      String[] list = f2.list();
      for (int k = 0; k < list.length; k++) {
        fileName = list[k];
        //only process *.CHM files
        if (fileName.indexOf(".CHM") < 0) {
          continue;
        }

        //check to see if file exist
        File f = new File(filePath + fileName);
        if (!f.exists()) {
          log.error("-------SWA CHM no file found: " + fileName);
          return;
        }

        //move file to it place
        FileHandler.move(filePath + fileName, toFilePath + fileName);

        connection.setAutoCommit(false);
        ReadDataFromFile readDataFromFile = new ReadDataFromFile();
        Collection c = readFile(toFilePath, fileName);
        Iterator dataIter = c.iterator();
        //insert data into stage table
        MatlChmStageBeanFactory factory = new MatlChmStageBeanFactory(dbManager);
        while (dataIter.hasNext()) {
          MatlChmStageBean matlChmStageBean = (MatlChmStageBean) dataIter.next();
          factory.insert(matlChmStageBean, connection);
        }
        //call procedure if there are data
        if (c.size() > 0) {
          Vector out = new Vector(1);
          out.add("");
          factory.doProcedure(connection, "p_matl_chm_load", out);
        }
      }
    } catch (Exception e) {
      log.error("Error loading SWA *.CHM data", e);
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
          MatlChmStageBean matlChmStageBean = new MatlChmStageBean();
          String[] tmp = line.split("\t"); //tab delimiter
          if (tmp.length == totalColumns) {
            matlChmStageBean.setPartNumber(tmp[PART_NUMBER_COL].trim());
            matlChmStageBean.setPartName(tmp[PART_NAME_COL].trim());
            matlChmStageBean.setPartDesc(tmp[PART_DESC_COL].trim());
            matlChmStageBean.setUnitOfIssue(tmp[UNIT_OF_ISSUE_COL].trim());
            matlChmStageBean.setInventory(new BigDecimal(tmp[INVENTORY_COL].trim()));
            matlChmStageBean.setStartDate(new Date("12/1/1999"));
            matlChmStageBean.setChemicalFlag(tmp[CHEMICAL_FLAG_COL].trim());
            Calendar calendar = Calendar.getInstance();
            matlChmStageBean.setTcmLoadDate(calendar.getTime());
            c.add(matlChmStageBean);
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
        c = new Vector();
        MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "SWA " + fileName + " data load error", "See log file.");
      }
    } catch (Exception e) {
      throw e;
    }
    return c;
  } //end of methd

} //end of class
