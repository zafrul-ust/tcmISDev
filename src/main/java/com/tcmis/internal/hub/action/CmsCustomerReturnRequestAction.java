package com.tcmis.internal.hub.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.distribution.beans.CustomerReturnReasonBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestInputBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestDetailBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestViewBean;
import com.tcmis.internal.distribution.beans.VvReturnReasonBean;
import com.tcmis.internal.distribution.process.CustomerReturnRequestProcess;
import com.tcmis.internal.hub.beans.MrIssueReceiptDetailViewBean;

public class CmsCustomerReturnRequestAction extends TcmISBaseAction {
	private ResourceLibrary resourceLibrary;
	private List<String> errors;
	private Locale tcmISLocale;
	private CustomerReturnRequestInputBean inputBean;
	private String reasonCollAttr = "reasonsBeanCollection";
	private String issueCollAttr = "mrIssueReceiptDetailViewBeanCollection";
	
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "cmsCustomerReturnRequest");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}
		
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
		tcmISLocale = getTcmISLocale(request);
		DynaBean userInputForm = (DynaBean) form;
		CustomerReturnRequestProcess process = new CustomerReturnRequestProcess(getDbUser(request));
		inputBean = new CustomerReturnRequestInputBean(form, tcmISLocale);
		resourceLibrary = new ResourceLibrary("com.tcmis.common.resources.CommonResources", tcmISLocale);
		
		errors = new ArrayList<String>();
		boolean isReapplyUserInput = false;
		if (inputBean.isConfirm()) {
			checkToken(request);
			
			try {
				Collection<CustomerReturnReasonBean> returnReasonBeans = 
					BeanHandler.getBeans(userInputForm,
											"returnReasonBean",
											new CustomerReturnReasonBean(),
											tcmISLocale);
				Collection<CustomerReturnRequestDetailBean> returnIssuesBeans = 
					BeanHandler.getBeans(userInputForm,
											"returnIssueSubmitBean",
											new CustomerReturnRequestDetailBean(),
											tcmISLocale,
											"returnIssue");
				
				doConfirmAction(process, personnelBean, returnReasonBeans, returnIssuesBeans);
				
				request.setAttribute("confirmSuccess", "true");
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				
				errors.add(resourceLibrary.getString("generic.error"));
				
				isReapplyUserInput = true;
			}
		}
		
		doSearchAction(request, process, personnelBean, userInputForm, isReapplyUserInput);
		
		if (!CollectionUtils.isEmpty(errors)) {
			request.setAttribute("tcmISErrors", errors);
		}

		request.setAttribute("searchInput", inputBean);
		
		return mapping.findForward("success");
	}
	
	private void doConfirmAction(CustomerReturnRequestProcess process,
									PersonnelBean personnelBean,
									Collection<CustomerReturnReasonBean> returnReasonBeans,
									Collection<CustomerReturnRequestDetailBean> returnIssuesBeans) throws Exception {
		//The process is doing auto-approve, so the quantity requested will always be the quantity approved
		inputBean.setRmaStatus("Approved");
		inputBean.setQuantityReturnAuthorized(inputBean.getReturnQuantityRequested());

		inputBean.setWantReplacement("N");
		inputBean.setRequestStartDate(new Date());
		inputBean.setApprovalDate(new Date());
		inputBean.setDataTransferStatus("OPEN");
		inputBean.setDistributionReturn("N");
		
		process.confirmCmsCustomerReturnRequest(inputBean, returnReasonBeans, returnIssuesBeans, personnelBean);
	}

	private void doSearchAction(HttpServletRequest request,
								CustomerReturnRequestProcess process,
								PersonnelBean personnelBean,
								DynaBean userInputForm,
								boolean isReapplyUserInput) {
		try {
			CustomerReturnRequestViewBean requestBean = process.getReturnRequestHeaderInfo(inputBean, personnelBean);
			
			request.setAttribute("customerReturnRequestResultBean", requestBean);
			request.setAttribute("vvReasonBeanCollection", process.getReasonList());
			
			if (isReapplyUserInput) {
				applyDataFromPageInputs(request, userInputForm, requestBean);
			} else {
				applyDataFromDatabase(request, process);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			errors.add(resourceLibrary.getString("error.db.select"));
		}
		
		saveTcmISToken(request);
	}
	
	private void applyDataFromDatabase(HttpServletRequest request,
										CustomerReturnRequestProcess process) throws BaseException, Exception {
		request.setAttribute(reasonCollAttr, process.getAddedCustomerReasons(inputBean));
		
		request.setAttribute(issueCollAttr, process.getCmsIssuesWithReturnQuantity(inputBean.getRmaId(),
																					inputBean.getCompanyId(),
																					inputBean.getPrNumber(),
																					inputBean.getLineItem()));
	}
	
	private void applyDataFromPageInputs(HttpServletRequest request,
										DynaBean userInputForm,
										CustomerReturnRequestViewBean requestBean) throws BeanCopyException {
		request.setAttribute(reasonCollAttr, BeanHandler.getBeans(userInputForm,
																	"returnReasonBean",
																	new VvReturnReasonBean(),
																	tcmISLocale));
		
		request.setAttribute(issueCollAttr, BeanHandler.getBeans(userInputForm,
																	"returnIssueSubmitBean",
																	new MrIssueReceiptDetailViewBean(),
																	tcmISLocale));
		
		requestBean.setReturnRequestorName(inputBean.getRequestorName());
		requestBean.setReturnRequestorEmail(inputBean.getRequestorEmail());
		requestBean.setReturnRequestorPhone(inputBean.getRequestorPhone());
		requestBean.setReturnMaterial(inputBean.getReturnMaterial());
		requestBean.setCorrectItemShipped(inputBean.getCorrectItemShipped());
		requestBean.setReturnNotes(inputBean.getReturnNotes());
	}
}