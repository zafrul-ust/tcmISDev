package com.tcmis.client.ups.action;

import com.tcmis.client.ups.process.ShippingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.print.beans.PrintLabelInputBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jan 22, 2008
 * Time: 4:35:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShippingAction
    extends TcmISBaseAction{

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
          BaseException, Exception {
/*
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "showallocationanalysis");
      //This is to save any parameters passed in the URL, so that they
      //can be acccesed after the login
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
*/
    PrintLabelInputBean inputBean = new PrintLabelInputBean();
    BeanHandler.copyAttributes(form, inputBean);
    LabelInputBean labelInputBean = new LabelInputBean();
    BeanHandler.copyAttributes(form, labelInputBean);
    String result = "OK";
    try {
        ShippingProcess shippingProcess = new ShippingProcess(this.getDbUser(request));
        if (form != null &&
                 ( (DynaBean) form).get("action") != null &&
                 ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("reprint")) {

         ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
         PersonnelBean personnelBean = new PersonnelBean();
         labelInputBean.setPaperSize("UPS");
         labelInputBean.setSkipKitLabels("");
         Collection locationLabelPrinterCollection = new Vector();
         locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

         String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
         inputBean.setPrinterName(printerPath);
         shippingProcess.reprint(inputBean);
        }
        else if (form != null &&
                 ( (DynaBean) form).get("action") != null &&
                 ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("void")) {
            shippingProcess.voidShipment(inputBean.getShipmentId(), null);
        }
        else {
            result = "No such action.";
        }
    }
    catch(Exception e) {
        result = "Error:"+e.getMessage();
        log.fatal("UPS error:"+e.getMessage(),e);
    }
    PrintWriter writer = response.getWriter();
    writer.write(result);
    writer.close();
    return (mapping.findForward("home"));
  }
}
