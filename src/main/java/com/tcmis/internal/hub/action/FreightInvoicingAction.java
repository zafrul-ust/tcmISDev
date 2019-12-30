package com.tcmis.internal.hub.action;

import java.util.Collection;

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
import com.tcmis.internal.hub.beans.FreightInvoiceStageBean;
import com.tcmis.internal.hub.beans.FreightInvoicingInputBean;
import com.tcmis.internal.hub.process.FreightInvoicingProcess;


public class FreightInvoicingAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "freightinvoicingmain");
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			
			return (mapping.findForward("login"));
		}
    
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    
		if (!personnelBean.getPermissionBean().hasUserPagePermission("freightInvoicing")) {
			return (mapping.findForward("nopermissions"));
		}
	
		// complete this action if nothing has been submitted
		if (form == null) 
			return (mapping.findForward("success"));

		// get input
		FreightInvoicingInputBean input = new FreightInvoicingInputBean();
	 	BeanHandler.copyAttributes(form, input);
	 	
	 	String[] submittedStatus = request.getParameterValues("freightInvoiceStatus");
		input.setFreightInvoiceStatus(submittedStatus);
	 		
		FreightInvoicingProcess process = new FreightInvoicingProcess(this.getDbUser(request), getTcmISLocaleString(request));
	 	
		if ("search".equalsIgnoreCase(input.getuAction())) {
			//load data
			Collection<FreightInvoiceStageBean> freightInvoiceStageBean =process.getSearchData(input);
			request.setAttribute("freightInvoiceColl",freightInvoiceStageBean);
			//set data for load id and carrier
			this.setDataForLoadIdAndCarrier(freightInvoiceStageBean, process);
		} else if ("update".equalsIgnoreCase(input.getuAction())) {
			Collection<FreightInvoiceStageBean> beans = BeanHandler.getBeans((DynaBean)form,"freightInvoiceStageBean",new FreightInvoiceStageBean(),this.getTcmISLocale(request), "ok");
					
			String errorMsg = process.updateData(beans);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				this.setErrorMessage(request, errorMsg);
			}
			//reload data
			Collection<FreightInvoiceStageBean> freightInvoiceStageBean =process.getSearchData(input);
			request.setAttribute("freightInvoiceColl",freightInvoiceStageBean);
			//set data for load id and carrier
			this.setDataForLoadIdAndCarrier(freightInvoiceStageBean, process);
		} else if ("reprocess".equalsIgnoreCase(input.getuAction())) {
			String errorMsg = process.reprocessInvoiceLines(input);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				this.setErrorMessage(request, errorMsg);
			}
			//reload data
			Collection<FreightInvoiceStageBean> freightInvoiceStageBean =process.getSearchData(input);
			request.setAttribute("freightInvoiceColl",freightInvoiceStageBean);
			//set data for load id and carrier
			this.setDataForLoadIdAndCarrier(freightInvoiceStageBean, process);
		} 
		else if ("createExcel".equalsIgnoreCase(input.getuAction())) {
			this.setExcel(response,"FreightInvoicing");
	        process.getExcelReport(input, personnelBean.getLocale()).write(response.getOutputStream());
		    return noForward;
		}
		
	 request.setAttribute("freightInvoiceStatusColl", process.getStatuses());

	 return (mapping.findForward("success"));
  }
	
  private void setErrorMessage(HttpServletRequest request, String errorMessage) {
	  request.setAttribute("tcmISError", errorMessage);
  }
  
  private void setDataForLoadIdAndCarrier(Collection<FreightInvoiceStageBean> freightInvoiceStageBean, FreightInvoicingProcess process) throws BaseException {
	//find distinct Load ID
	request.setAttribute("loadIdList",process.getDistinctLoadId(freightInvoiceStageBean));
	//and active freight radian carrier list
	request.setAttribute("radianCarrierList", process.getFreightRadianCarrier());
  }
}
