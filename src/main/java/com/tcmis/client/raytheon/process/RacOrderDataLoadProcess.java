package com.tcmis.client.raytheon.process;

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
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Process to load order data from SWA.
 * @version 1.0
 *****************************************************************************/
public class RacOrderDataLoadProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public RacOrderDataLoadProcess(String client) {
    super(client);
  }

  public void load(String fileName) throws Exception {
    ResourceLibrary racOrderLibrary = new ResourceLibrary("poorder");
    String delimiter = racOrderLibrary.getString("rac.delimiter");
    try {
        //check to see if file exist
        File f = new File(fileName);
        if (!f.exists()) {
          log.error("-------RAC order no file found: "+fileName);
          return;
        }
        //check to see if file contains data
        if (f.length() <= 0 ) {
          log.error("-------RAC order file is empty: "+fileName);
          return;
        }

        //testing
        RacReadDataFromFile readDataFromFile = new RacReadDataFromFile();
        Collection c = readDataFromFile.readFile(fileName,delimiter);
        //end of testing

    } catch (Exception e) {
      log.error("Error loading SWA order data", e);
      throw e;
    }
  } //end of method

} //end of class
