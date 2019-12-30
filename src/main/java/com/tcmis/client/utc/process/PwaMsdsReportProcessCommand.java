package com.tcmis.client.utc.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class PwaMsdsReportProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public PwaMsdsReportProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      PwaMsdsReportProcess process = new PwaMsdsReportProcess("UTC");
      process.load();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("PWA MSDS report error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "PWA MSDS report error",
                          "See log file.");
  }

}
