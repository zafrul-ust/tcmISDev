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



public class CraProcessCommand implements Command {
  Log log = LogFactory.getLog(this.getClass());
  
  public CraProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      CraProcess process = new CraProcess("SEAGATE");
      //now process new_chem_approval

      //process "Active" requests
      process.processSeagateNewChemApproval();
      //process "Inactive" requests
      process.processSeagateInactiveRequest();
      //process "Reactivated" requests
      process.processSeagateReactivatedRequest();

    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("CRA processing", e);
    MailProcess.sendEmail("deverror@haastcm.com", null,
                          "deverror@haastcm.com",
                          "SEAGATE - CRA processing error",
                          "CRA processing.\n" + e.getMessage());
  }

}
