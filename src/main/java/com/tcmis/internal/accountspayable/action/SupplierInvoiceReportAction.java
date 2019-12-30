package com.tcmis.internal.accountspayable.action;

import java.math.BigDecimal;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.accountspayable.beans.VoucherReportInputBean;
import com.tcmis.internal.accountspayable.process.VoucherReportProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class SupplierInvoiceReportAction extends TcmISBaseAction {

   public ActionForward executeAction(ActionMapping mapping, ActionForm form,
   HttpServletRequest request,
   HttpServletResponse response) throws BaseException, Exception {

//login
      if (!this.isLoggedIn(request)) {
         request.setAttribute("requestedPage", "supplierinvoicereportmain");
         //This is to save any parameters passed in the URL, so that they
         //can be acccesed after the login
         request.setAttribute("requestedURLWithParameters",
         this.getRequestedURLWithParameters(request));
         return (mapping.findForward("login"));
      }

     PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
     
     if (!personnelBean.getPermissionBean().hasUserPagePermission("supplierInvoiceReport"))
     {
       return (mapping.findForward("nopermissions"));
     }

      VoucherReportProcess voucherReportProcess = new VoucherReportProcess(this.getDbUser(request),getTcmISLocaleString(request));
      VoucherReportInputBean inputBean = new VoucherReportInputBean();
      BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
      
      //If the search button was pressed the getSubmitSearch() value will be not null
      if (inputBean.getUserAction() != null && inputBean.getUserAction().equalsIgnoreCase("search")) {

    	  Collection c = voucherReportProcess.getSupplierInvoices(inputBean);
    	  request.setAttribute("voucherReportColl",c);
      }
      else if (inputBean.getUserAction() != null && inputBean.getUserAction().equalsIgnoreCase("createExcel")) {
        Collection reportColl = voucherReportProcess.getSupplierInvoices(inputBean);
          try {
			this.setExcel(response,"supplierInvoiceReport");
			voucherReportProcess.getExcelReport(reportColl,personnelBean.getLocale()).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
      }
      else {
          // The loading of the page is too slow. These values won't be preset.
      //    request.setAttribute("personnelColl",voucherReportProcess.getBuyOrderPersonnel());
    	  
          // valid values for voucher status
          request.setAttribute("voucherStatusColl",voucherReportProcess.getVoucherStatuses());
      }
	  return (mapping.findForward("success"));
   }
}
