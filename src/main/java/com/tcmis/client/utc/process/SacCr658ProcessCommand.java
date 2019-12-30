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

public class SacCr658ProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public SacCr658ProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      Cr658Process process = new Cr658Process("TCM_OPS");
      process.createInvoice("SAC");
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("SAC invoice creation error:", e);
    MailProcess.sendEmail("deverror@haastcm.com", null,
                          "deverror@haastcm.com",
                          "UTC - SAC invoice error",
                          e.getMessage());
  }

}