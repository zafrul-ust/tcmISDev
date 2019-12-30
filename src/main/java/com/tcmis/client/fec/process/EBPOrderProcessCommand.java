package com.tcmis.client.fec.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EBPOrderProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public EBPOrderProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    EBPOrderProcess process = null;
    try {
      process = new EBPOrderProcess("FECTest");      
      process.process((String) args[1]);      
    }
    catch (Exception e) {
      logInitializationError(e);
    } finally {
      process = null;
      System.gc();
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("EBP Order", e);
    MailProcess.sendEmail("deverror@haastcm.com", null,
                          "deverror@haastcm.com",
                          "FEC - EBP Order processing error",
                          "EBP Order.\n" + e.getMessage());
  }

}
