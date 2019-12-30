package com.tcmis.client.cal.process;

import java.io.File;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RegexMatcher;
import org.apache.commons.digester.RegexRules;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.SimpleRegexMatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.cal.beans.FmcExtractBean;
import com.tcmis.client.cal.factory.FmcExtractBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;

import com.tcmis.common.util.ExcelHandler;


/******************************************************************************
 * Process to load FMC data from Continental.
 * @version 1.0
 *****************************************************************************/
public class FmcDataLoadProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  private static final String TO_EMAIL = "dev-notification@haastcm.com";
  public FmcDataLoadProcess(String client) {
    super(client);
  }

  /******************************************************************************
   * Looks for files to load, if there are any loads them using the 
   * load(String filePath) method and calls the P_FMC_EXTRACT_PROCESS procedure.<BR>
   *
   ****************************************************************************/
  public void load() {
    ResourceLibrary fmcLibrary = new ResourceLibrary("fmc");
    String fileName = null;
    File dir = new File(fmcLibrary.getString("homedir"));
    //check if directory to move processed files exists
    File processedDir = new File(dir.getAbsolutePath() + "/processed/");
    if (!processedDir.exists()) {
      processedDir.mkdir();
    }
    FmcFileFilter filter = new FmcFileFilter();
    try {
      log.info("Checking for FMC emails");
      MailProcess.getPopMail(fmcLibrary.getString("username"),fmcLibrary.getString("password"), true, fmcLibrary.getString("homedir"));


      log.info("Checking for FMC load file");
      File[] files = dir.listFiles(filter);
      if(files.length < 1) {
        MailProcess.sendEmail(TO_EMAIL, "", "edierror@haasgroupintl.com", "No FMC data file.", "No new FMC data file was found.");
      }
      for (int i = 0; i < files.length; i++) {
        this.load(files[i].getAbsolutePath());
        FileHandler.move(files[i].getAbsolutePath(),
                         processedDir.getAbsolutePath() + "/" +
                         files[i].getName() + "." + new Date());
        DbManager dbManager = new DbManager(getClient());
        GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
        factory.doProcedure("P_FMC_EXTRACT_PROCESS", new Vector(0));
      }
      log.info("Done with for FMC load file");
    }
    catch (Exception e) {
      log.error("Error loading fmc data", e);
      try {
        MailProcess.sendEmail("deverror@tcmis.com", null,
                              "deverror@tcmis.com",
                              "FMC data load error",
                              "See log file.");
      }
      catch (Exception ex) {
        log.error("Error sending error mail for FMC.", e);
      }
    }
  }

  /******************************************************************************
   * Parses the file in the given path and inserts the data into the 
   * FMC_EXTRACT table.<BR>
   *
   * @param filePath  path to the file to parse
   * @throws BaseException If an <code>SQLException</code> is thrown
   ****************************************************************************/
  private void load(String filePath) throws BaseException {
    try {
      Vector data = new Vector();
      File file = new File(filePath);

      RuleSet ruleSet = new FmcRuleSet(data);
      Digester digester = new Digester();
      RegexMatcher m = new SimpleRegexMatcher();
      digester.setRules(new RegexRules(m));
      digester.addRuleSet(ruleSet);
      digester.parse(file);

      DbManager dbManager = new DbManager(getClient());
      FmcExtractBeanFactory factory = new FmcExtractBeanFactory(dbManager);
      Iterator iterator = data.iterator();
      while (iterator.hasNext()) {
        FmcExtractBean bean = (FmcExtractBean) iterator.next();
        bean.setFileName(filePath);
        factory.insert(bean);
      }
    }
    catch (Exception e) {
      BaseException be = new BaseException(e);
      be.setRootCause(e);
      throw be;
    }
  }
  private void loadCSV(String filePath) throws BaseException {
	    try {
	    	System.out.println(filePath);
			Vector<Vector<String>> sv = ExcelHandler.read(filePath);
			int count = 0;
			for(Vector<String> v : sv) {
				count++;
				if( count == 1 ) continue;// line 1 is header.
				if( v == null  )  continue;
				int pos = 0;
				for(String s:v)
					System.out.print((pos++)+":"+s);
				System.out.println();

//	      DbManager dbManager = new DbManager(getClient());
//	      FmcExtractBeanFactory factory = new FmcExtractBeanFactory(dbManager);
//	      Iterator iterator = data.iterator();
//	      while (iterator.hasNext()) {
//	        FmcExtractBean bean = (FmcExtractBean) iterator.next();
//	        bean.setFileName(filePath);
//	        factory.insert(bean);
//	      }
			}
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	      BaseException be = new BaseException(e);
	      be.setRootCause(e);
//	      throw be;
	    }
  }

}
