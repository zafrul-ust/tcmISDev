package com.tcmis.client.cal.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class FmcDataLoadProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass()); 

  public FmcDataLoadProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      FmcDataLoadProcess process = new FmcDataLoadProcess("CAL");
      process.load();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("FMC data load error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "FMC data load error",
                          "See log file.");
  }
  public static void main(String[] args) {
	  try {
	  FmcDataLoadProcessCommand exe = new FmcDataLoadProcessCommand();
	  exe.execute(null);
	  }catch(Exception ex){ex.printStackTrace();}
  }

}
