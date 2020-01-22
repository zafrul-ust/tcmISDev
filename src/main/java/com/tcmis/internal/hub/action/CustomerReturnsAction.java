package com.tcmis.internal.hub.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.DropDownNamesInputBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CustomerReturnInputBean;
import com.tcmis.internal.hub.beans.MrIssueReceiptDetailViewBean;
import com.tcmis.internal.hub.process.CustomerReturnProcess;

public class CustomerReturnsAction extends TcmISBaseAction {
	private ResourceLibrary commonResources = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
	
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");

		String accessResultForward = validateAccessPermission(request, personnelBean.getPermissionBean());
		if (!StringHandler.isBlankString(accessResultForward)) {
			return mapping.findForward(accessResultForward);
		}
		
		CustomerReturnInputBean inputBean = new CustomerReturnInputBean(form, getTcmISLocale(request));
		
		if (!inputBean.isNoAction()) {
			CustomerReturnProcess process = new CustomerReturnProcess(getDbUser(request),
																		getTcmISLocaleString(request));
			
			if (inputBean.isSearch()) {
				doSearchAction(request, process, inputBean);
			} else if (inputBean.isCreateExcel()) {
				try {
					DropDownNamesInputBean dBean = new DropDownNamesInputBean();
					BeanHandler.copyAttributes(form, dBean, getTcmISLocale(request));
					
					setExcel(response, "Customer Return Excel Report");
					process.getExcelReport(inputBean, dBean).write(response.getOutputStream());
				}
				catch (Exception ex) {
					log.error(ex.getMessage(), ex);
					
					return mapping.findForward("genericerrorpage");
				}
				
				return noForward;
			}
		}

		return mapping.findForward("success");
	}
	
	private String validateAccessPermission(HttpServletRequest request, PermissionBean permissionBean)
			throws BaseException {
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "newCustomerReturns");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return "login";
		} else if (!permissionBean.hasUserPagePermission("newCustomerReturns")) {
			return "nopermissions";
		} else {
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	private void doSearchAction(HttpServletRequest request,
								CustomerReturnProcess process,
								CustomerReturnInputBean inputBean)
			throws BaseException {
		Collection<MrIssueReceiptDetailViewBean> returns = Collections.EMPTY_LIST;
		try {
			returns = process.getCmsReceiptsToCreateReturnRequest(inputBean);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			List<String> errorMsgs = (List<String>) request.getAttribute("tcmISErrors");
			
			if (CollectionUtils.isEmpty(errorMsgs)) {
				errorMsgs = new ArrayList<String>();
			}
			
			errorMsgs.add(commonResources.getString("generic.error"));
			request.setAttribute("tcmISErrors", errorMsgs);
		}
		
		inputBean.setTotalLines(returns == null ? 0 : returns.size());
		request.setAttribute("mrIssueReceiptDetailViewBeanColl", returns);
		request.setAttribute("searchInput", inputBean);
		
		saveTcmISToken(request);
	}
}