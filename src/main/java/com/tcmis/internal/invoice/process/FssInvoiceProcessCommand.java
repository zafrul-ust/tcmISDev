package com.tcmis.internal.invoice.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class FssInvoiceProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public FssInvoiceProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      FssInvoiceProcess process = new FssInvoiceProcess("TCM_OPS");
      process.createInvoices();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("Fss invoice error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "Fss invoice error",
                          "See log file. " + e.getMessage());
  }

}