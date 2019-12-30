package com.tcmis.client.fec.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class ChemicalOnHandProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public ChemicalOnHandProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      ChemicalOnHandProcess process = new ChemicalOnHandProcess("FEC");
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
                          "FEC chemical onhand",
                          "Error loading data.\n" + e.getMessage());
  }

}