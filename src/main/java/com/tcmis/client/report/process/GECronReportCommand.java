package com.tcmis.client.report.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class GECronReportCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public GECronReportCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
    	GECronReportProcess process = new GECronReportProcess("GE");
    	process.getCronReport("GE", "Whippany");
    }
    catch (Exception e) {
    	e.printStackTrace();
    }
    return null;
  }
  public static void main(String[] args) throws Exception {
	  GECronReportCommand cmd = new GECronReportCommand();
	  cmd.execute(null);
  }
	  

}