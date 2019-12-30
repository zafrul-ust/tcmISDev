package com.tcmis.client.raytheon.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class RacOrderDataLoadProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public RacOrderDataLoadProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      RacOrderDataLoadProcess process = new RacOrderDataLoadProcess("RAY");
      process.load(args[0].toString());
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("RAC order data load error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "RAC order data load error",
                          "See log file.");
  }

}
