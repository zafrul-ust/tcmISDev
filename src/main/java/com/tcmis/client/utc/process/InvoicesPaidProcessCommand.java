package com.tcmis.client.utc.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class InvoicesPaidProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public InvoicesPaidProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      InvoicesPaidProcess process = new InvoicesPaidProcess(
          "TCM_OPS");
      process.processChecks();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("Invoice check payment error:", e);
    MailProcess.sendEmail("deverror@haastcm.com", null,
                          "deverror@haastcm.com",
                          "UTC - invoice check payment processing error",
                          e.getMessage());
  }

}