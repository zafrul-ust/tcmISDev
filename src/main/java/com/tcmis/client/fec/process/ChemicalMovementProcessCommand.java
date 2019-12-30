package com.tcmis.client.fec.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class ChemicalMovementProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public ChemicalMovementProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      ChemicalMovementProcess process = new ChemicalMovementProcess("FEC");
      process.load();
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
                          "FEC chemical movement",
                          "Error loading data.\n" + e.getMessage());
  }

}