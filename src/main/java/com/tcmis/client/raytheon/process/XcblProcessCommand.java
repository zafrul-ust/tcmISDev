package com.tcmis.client.raytheon.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class XcblProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public XcblProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      XcblProcess process = new XcblProcess("RAY");
      process.checkStatus();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("Raytheon xcbl checkstatus error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "raytheon_xcbl@haastcm.com",
                          "Raytheon xcbl checkstatus error",
                          "See log file." + e.getMessage());
  }

}
