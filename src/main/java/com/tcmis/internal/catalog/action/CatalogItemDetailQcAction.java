package com.tcmis.internal.catalog.action;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.CatalogItemDetailQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogItemQcHeaderViewBean;
import com.tcmis.internal.catalog.beans.CatalogItemQcViewBean;
import com.tcmis.internal.catalog.process.CatalogItemDetailQcProcess;

public class CatalogItemDetailQcAction extends TcmISBaseAction {

	private void doSearch(HttpServletRequest request, CatalogItemDetailQcInputBean input, CatalogItemDetailQcProcess process) throws BaseException {
		Collection<CatalogItemQcHeaderViewBean> headerInfo = process.getHeaderInfo(input);
		CatalogItemQcHeaderViewBean[] headers = new CatalogItemQcHeaderViewBean[0];
		headers = headerInfo.toArray(headers);
		Optional<CatalogQueueBean> workQueueItem = process.getCatalogQueueData(input);
		if (workQueueItem.isPresent()) {
			request.setAttribute("catalogQueueRow", workQueueItem.get());
		}

		request.setAttribute("requestHeader", headers[0]);
		request.setAttribute("itemHeader", headers[1]);
		request.setAttribute("vvShelfLifeBasisBeanCollection", process.getShelfLifeBasisCollection());
		request.setAttribute("vvNetwtUnitCollection", process.getNetwtUnitCollection());
		request.setAttribute("resultsCollection", process.search(input));
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "catalogitemqcdetails");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			next = mapping.findForward("login");
		}
		else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			if (user != null && (user.getPermissionBean().hasFacilityPermission("catalogAddItemQc", null,null) ||
					user.getPermissionBean().hasUserPagePermission("catAddVendorQueue"))) {
				Locale locale = getTcmISLocale();
				CatalogItemDetailQcInputBean input = new CatalogItemDetailQcInputBean(form, locale, "datetimeformat");
				//CatalogAddReqMsdsInputBean input = new CatalogAddReqMsdsInputBean(form, locale);
				CatalogItemDetailQcProcess process = new CatalogItemDetailQcProcess(user.getPersonnelIdBigDecimal(), getDbUser());
				process.fillInputWithQueueData(input);
				try {
					// Get the user/page input in a usable form as an input bean
					Collection<CatalogItemQcViewBean> components = BeanHandler.getBeans((DynaBean) form, "part", "datetimeformat", new CatalogItemQcViewBean(), locale);

					// Stick the bean back in the session for the hidden field tag
					request.setAttribute("inputBean", input);

					if (input.isApprove()) {
						process.save(input, components, user);
						request.setAttribute("itemApproved", process.advanceCatalogQueue(input, user));
						doSearch(request, input, process);
					}
					else if (input.isReverse()) {
						process.reverse(input, components, user);
						// this will only have the effect of closing the Item QC tab and reloading the Cat Add QC table
						// no approvals will be done because of this
						request.setAttribute("itemApproved", "approved");
					}
					else if (input.isSave()) {
						process.save(input, components, user);
						doSearch(request, input, process);
					}
					else if (input.isSubmitAction()) {
						process.save(input,  components, user);
						request.setAttribute("itemApproved", process.advanceCatalogQueue(input, user));
						doSearch(request, input, process);
					}
					else if (input.isDetails()) {
						request.setAttribute("requestHeader", process.getQcOriginalHeader(input));
						request.setAttribute("resultsCollection", process.getQcOriginalInfo(input));
						next = mapping.findForward("qcoriginal");
					}
					else if (input.isReload()) {
						Collection<CatalogItemQcHeaderViewBean> headerInfo = process.reloadHeaderInfo(input);
						CatalogItemQcHeaderViewBean[] headers = new CatalogItemQcHeaderViewBean[0];
						headers = headerInfo.toArray(headers);
						Optional<CatalogQueueBean> workQueueItem = process.getCatalogQueueData(input);
						if (workQueueItem.isPresent()) {
							request.setAttribute("catalogQueueRow", workQueueItem.get());
						}
						else {
							request.setAttribute("requestHeader", headers[0]);
						}
						request.setAttribute("itemHeader", headers[1]);
						request.setAttribute("vvShelfLifeBasisBeanCollection", process.getShelfLifeBasisCollection());
						request.setAttribute("vvNetwtUnitCollection", process.getNetwtUnitCollection());
						request.setAttribute("resultsCollection", process.reloadItemComponents(input));
					}
					else if (input.isRejectAction()) { 
						process.rejectCannotFulfill(input, user);
						// this will only have the effect of closing the Item QC tab and reloading the Cat Add QC table
						// no approvals will be done because of this
						request.setAttribute("itemRejected", "true");
					}
					else {
						doSearch(request, input, process);
					}
				}
				catch (BaseException be) {
					request.setAttribute("tcmisError", be.getMessage());
					//processExceptions(request, mapping, be);
					doSearch(request, input, process);
				}
				catch (Exception e) {
					request.setAttribute("tcmisError", e.getMessage());
					doSearch(request, input, process);
				}
			}
			else {
				next = mapping.findForward("nopermissions");
			}
		}

		return next;
	}
}