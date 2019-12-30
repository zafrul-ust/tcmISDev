package com.tcmis.client.raytheon.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class RrfcFtpMaterialRequisitionProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public RrfcFtpMaterialRequisitionProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      FtpMaterialRequisitionProcess process = new FtpMaterialRequisitionProcess("RAY");
      process.load("RRFC");
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("RRFC Ftp Chemical Ordering data load error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "RRFC Ftp Chemical Ordering data load error",
                          "See log file.");
  }

}
