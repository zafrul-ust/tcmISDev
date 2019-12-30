package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
//import com.tcmis.internal.hub.beans.PageNameInputBean;
import com.tcmis.internal.hub.process.PrinterLocationProcess;
import com.tcmis.internal.hub.beans.ItemCountInventoryViewBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterInputBean;
import com.tcmis.internal.report.beans.ErrorBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbUpdateException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class PrinterLocationAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form,
   HttpServletRequest request,
   HttpServletResponse response) throws BaseException, Exception {
      
      if (!this.isLoggedIn(request)) {
         request.setAttribute("requestedPage", "printerlocationmain");
         request.setAttribute("requestedURLWithParameters",
         this.getRequestedURLWithParameters(request));
         return (mapping.findForward("login"));
      }
      
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
      
      if( form == null ) return mapping.findForward("success");
      
      String action = (String) ( (DynaBean)form).get("action");
      
      if( action == null ) return mapping.findForward("success");
      
      String hub = (String) ((DynaBean) form).get("hub");
      if ( action.equals("search")) {
         this.saveTcmISToken(request);
         
         Collection c = process.getSearchResult(hub);         
         //Pass the result collection in request
         request.setAttribute("locationLabelPrinterBeanCollection",c);         
      }
      else if (action.equals("createExcel")) {
         Collection c = process.getSearchResult(hub);
         try {
            this.setExcel(response,"Printer Location");
            process.getExcelReport(c,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
         }
         catch (Exception ex) {
            ex.printStackTrace();
            return mapping.findForward("genericerrorpage");
         }
         return noForward;
      } else if (action.equals("update")) {
         checkToken(request);
         
         /*Need to check if the user has permissions to update this data. if they do not have the permission
            we show a error message. */
         if (!personnelBean.getPermissionBean().hasFacilityPermission("updatePrinterPath", null, null)) {
            request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
            request.setAttribute("locationLabelPrinterBeanCollection", process.getSearchResult(hub));
            log.debug("update: error.noaccesstopage");
            return (mapping.findForward("success"));
         }
         
         Collection beans = BeanHandler.getBeans((DynaBean) form,"locationLabelPrinterBean", new LocationLabelPrinterInputBean(),getTcmISLocale(request));
         try {
            int i = process.update(beans);
         } catch (DbUpdateException uex) {
            request.setAttribute("tcmISError", uex.getMessage());
         }
         
         Collection c = process.getSearchResult(hub);
         
         request.setAttribute("locationLabelPrinterBeanCollection",c);
         // request.setAttribute("tcmISErrors", errorMsgs);
      }
      else {
         //Do stuff to get data you are going to need to build the search options
         request.setAttribute("hubInventoryGroupOvBeanCollection",process.getAllHubDropdown());
      }
      //Saving Token, if its needed  and save token or checkToken is not called yet.
      //this.saveTcmISToken(request);
      return (mapping.findForward("success"));
   }
}
