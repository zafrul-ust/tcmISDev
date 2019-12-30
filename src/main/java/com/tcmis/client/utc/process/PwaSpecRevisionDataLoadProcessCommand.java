package com.tcmis.client.utc.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class PwaSpecRevisionDataLoadProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public PwaSpecRevisionDataLoadProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      PwaSpecRevisionDataLoadProcess process = new PwaSpecRevisionDataLoadProcess("UTC");
      process.load();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("PWA Spec .htm data load error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "PWA Spec .htm data load error",
                          "See log file.");
  }

}
