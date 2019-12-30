package com.tcmis.client.utc.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class PwaMaresDataLoadProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public PwaMaresDataLoadProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      PwaMaresDataLoadProcess process = new PwaMaresDataLoadProcess("UTC");
      process.load();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("PWA MARES data load error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "PWA MARES data load error",
                          "See log file.");
  }

}
