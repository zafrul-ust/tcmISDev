package com.tcmis.internal.invoice.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class CreditCardInvoiceProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public CreditCardInvoiceProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      if(args.length != 2) {
        throw new Exception("This command takes company id and invoice group as arguments.");
      }
      if(log.isDebugEnabled()) {
        log.debug("Running " + args[0] + " " + args[1]);
      }
      CreditCardInvoiceProcess process = new CreditCardInvoiceProcess("BATCH");
      process.submit((String)args[0], (String)args[1]);
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("Initializing invoices", e);
    MailProcess.sendEmail("deverror@haastcm.com", null,
                          "deverror@haastcm.com",
                          "Invoice processing error",
                          "Initializing invoices.\n" + e.getMessage());
  }

}