package com.tcmis.client.swa.process;

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
import com.tcmis.client.order.beans.CustomerPoPreStageBean;
import com.tcmis.client.order.factory.CustomerPoPreStageBeanFactory;

/******************************************************************************
 * Process to load order data from SWA.
 * @version 1.0
 *****************************************************************************/
public class PoFeedProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PoFeedProcess(String client) {
    super(client);
  }

  public void load(String fileName) throws Exception {
    ResourceLibrary swaOrderLibrary = new ResourceLibrary("poorder");
    String delimiter = swaOrderLibrary.getString("swa.delimiter");
    String header = swaOrderLibrary.getString("swa.header");
    String trailer = swaOrderLibrary.getString("swa.trailer");
    try {
        //check to see if file exist
        File f = new File(fileName);
        if (!f.exists()) {
          log.error("-------SWA order no file found: "+fileName);
          return;
        }

        DbManager dbManager = new DbManager(getClient());
        ReadDataFromFile readDataFromFile = new ReadDataFromFile();
        Collection c = readDataFromFile.readFile(dbManager,fileName,delimiter,header,trailer);
        Iterator dataIter = c.iterator();
        //insert data into stage table
        CustomerPoPreStageBeanFactory factory = new CustomerPoPreStageBeanFactory(dbManager);
        while (dataIter.hasNext()) {
          CustomerPoPreStageBean customerPoPreStageBean = (CustomerPoPreStageBean)dataIter.next();
          factory.insert(customerPoPreStageBean);
        }
    } catch (Exception e) {
      log.error("Error loading SWA order data", e);
      throw e;
    }
  } //end of method

} //end of class
