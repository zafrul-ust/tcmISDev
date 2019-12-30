package com.tcmis.supplier.invoice.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.supplier.invoice.process.VendorPaymentProcess;
import com.tcmis.supplier.invoice.beans.*;
import java.io.*;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
     ******************************************************************************/
public class VendorPaymentAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    
     if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "vendorpaymentmain");
      //This is to save any parameters passed in the URL, so that they
      //can be acccesed after the login
      request.setAttribute("requestedURLWithParameters",
      this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
     }
     
    String forward = "success";
    VendorPaymentProcess process = new VendorPaymentProcess(this.getDbUser(
        request));
    //if form is not null we have to perform some action
    if (form == null) {
    }
    //if form is not null we have to perform some action
    else {
      SupplierPaymentInputBean inputBean = new SupplierPaymentInputBean();
      BeanHandler.copyAttributes(form, inputBean);
//log.debug("action:" + ( (DynaBean) form).get("action"));
//log.debug("search:" + ( (DynaBean) form).get("submitSearch"));
      if ( ( (DynaBean) form).get("action") != null &&
          ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase(
          "detail")) {
        log.debug("detail");
        request.setAttribute("supplierPaymentHeaderViewBeanCollection",
                             process.getDetailData((PersonnelBean)this.getSessionObject(request, "personnelBean"), inputBean.getPaymentNum()));
      }
      else if ( ( (DynaBean) form).get("action") != null &&
               ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase(
          "createExcelDetail")) {
        log.debug("create excel detail");
        this.setExcel(response,"VendorPaymentDetail");
        try {
          process.getExcelReportDetail((PersonnelBean)this.getSessionObject(request, "personnelBean"), inputBean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        }
        catch (Exception ex) {
          return mapping.findForward("genericerrorpage");
        }
        return noForward;
      }
      else if ( ( (DynaBean) form).get("action") != null &&
               ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase(
          "createExcel")) {
        log.debug("create excel");
        this.setExcel(response,"VendorPayment");
        try {
          process.getExcelReportHeader((PersonnelBean)this.getSessionObject(request, "personnelBean"), inputBean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        }
        catch (Exception ex) {
          return mapping.findForward("genericerrorpage");
        }
        return noForward;
      }
      else if ( ( (DynaBean) form).get("submitSearch") != null &&
               ( (String) ( (DynaBean) form).get("submitSearch")).length() > 0) {
        request.setAttribute("supplierPaymentHeaderViewBeanCollection",
                             process.getSearchData((PersonnelBean)this.getSessionObject(request, "personnelBean"), inputBean));
        forward = "results";
      }
    }
    if (log.isDebugEnabled()) {
      log.debug("forward:" + forward);
    }
    return mapping.findForward(forward);
  }
}