package com.tcmis.client.cal.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class UnitedUsageProcessCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass()); 

  public UnitedUsageProcessCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
    	UnitedUsageProcess process = new UnitedUsageProcess("CAL");
      process.load();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("CAL data load error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "deverror@tcmis.com",
                          "CAL data load error",
                          "See log file.");
  }
  public static void main(String[] args) {
	  try {
	  UnitedUsageProcessCommand exe = new UnitedUsageProcessCommand();
	  exe.execute(null);
	  }catch(Exception ex){ex.printStackTrace();}
  }

}
