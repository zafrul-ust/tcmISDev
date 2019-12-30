package com.tcmis.client.seagate.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.internal.invoice.process.CreditCardInvoiceProcess;

public class InvoiceProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public InvoiceProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      CreditCardInvoiceProcess process = new CreditCardInvoiceProcess("BATCH");
      process.submit("SEAGATE", "SEAGATE");
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
                          "SEAGATE - Invoice processing error",
                          "Initializing invoices.\n" + e.getMessage());
  }

}
