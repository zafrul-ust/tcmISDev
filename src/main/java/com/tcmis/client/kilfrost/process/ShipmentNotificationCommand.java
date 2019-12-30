package com.tcmis.client.kilfrost.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Oct 7, 2008
 * Time: 4:27:54 PM
 * To change this template use File | Settings | File Templates.
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.batch.Command;
import com.tcmis.common.admin.process.MailProcess;

public class ShipmentNotificationCommand
    implements Command {
  Log log = LogFactory.getLog(this.getClass());

  public ShipmentNotificationCommand() {
    super();
  }

  public Object execute(Object[] args) throws Exception {
    try {
      OrderProcess process = new OrderProcess("TCM_OPS");
      process.sendShippingNotification();
    }
    catch (Exception e) {
      logInitializationError(e);
    }
    return null;
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("Kilfrost shipment notification error", e);
    MailProcess.sendEmail("deverror@tcmis.com", null,
                          "kilfrost@haastcm.com",
                          "Kilfrost shipment notification error",
                          "See log file." + e.getMessage());
  }

}
