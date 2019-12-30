package com.tcmis.client.swa.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class SwaIctFeedProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public SwaIctFeedProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
        IctFeedProcess process = new IctFeedProcess("SWA");
        process.load();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("SWA *.ICT data load error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "SWA *.ICT data load error",
                          "See log file.");
  }

}
