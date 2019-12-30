package com.tcmis.internal.currency.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class CurrencyLoadProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public CurrencyLoadProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      CurrencyLoadProcess process = new CurrencyLoadProcess("BATCH");
      process.load();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("Currency data load error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "Currency data load error",
                          "See log file.");
  }

}
