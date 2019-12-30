package com.tcmis.internal.hub.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.hub.process.PrinterLocationProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class AlternatePrinterLocationAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form,
   HttpServletRequest request,
   HttpServletResponse response) throws BaseException, Exception {
      
      PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
      BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
      /*Need to check if the user has permissions to view this page. if they do not have the permission
         we need to not show them the page. */
      if (!personnelBean.getPermissionBean().hasUserPagePermission("printerLocation")) {
         return (mapping.findForward("nopermissions"));
      }
      
      PrinterLocationProcess process = new PrinterLocationProcess(this.getDbUser(request),getTcmISLocaleString(request));
      //copy date from dyna form to the data bean
      //PageNameInputBean inputBean = new PageNameInputBean();
      //BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
            
      String hub = (String) ((DynaBean) form).get("hub");
      String managedPrintQueue = (String) ((DynaBean) form).get("managedPrintQueue");
      String labelStock = (String) ((DynaBean) form).get("labelStock");
      String printerLocation = (String) ((DynaBean) form).get("printerLocation");
      
      Collection c = process.getAlternatePrinterLocations(hub,managedPrintQueue,labelStock,printerLocation);
      
      //Pass the result collection in request
      request.setAttribute("altLocationLabelPrinterBeanCollection",c);
      
      //Saving Token, if its needed  and save token or checkToken is not called yet.
      //this.saveTcmISToken(request);
      return (mapping.findForward("success"));
   }
}
