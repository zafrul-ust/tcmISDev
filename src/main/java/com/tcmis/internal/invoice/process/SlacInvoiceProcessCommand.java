package com.tcmis.internal.invoice.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class SlacInvoiceProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public SlacInvoiceProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      SlacInvoiceProcess process = new SlacInvoiceProcess("TCM_OPS");
      process.createInvoices();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("Slac invoice error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "Slac invoice error",
                          "See log file.");
  }

}