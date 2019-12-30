package com.tcmis.client.seagate.process;
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

public class AmexUpdateProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public AmexUpdateProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      AmexUpdateProcess process = new AmexUpdateProcess("SEAGATE");
      process.processFiles();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("Amex update", e);
    MailProcess.sendEmail("deverror@haastcm.com", null,
                          "deverror@haastcm.com",
                          "SEAGATE - Amex update processing error",
                          "Amex update.\n" + e.getMessage());
  }

}
