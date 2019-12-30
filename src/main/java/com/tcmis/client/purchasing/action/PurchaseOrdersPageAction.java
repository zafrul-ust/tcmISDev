package com.tcmis.client.purchasing.action;

import com.tcmis.client.purchasing.process.CustomerBuyerProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.BuyOrdersInputBean;
import com.tcmis.internal.supply.process.BuyPageProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/******************************************************************************
 * Controller for receiving
 * 
 * @version 1.0
 ******************************************************************************/
public class PurchaseOrdersPageAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "purchaseordersmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		if (!user.getPermissionBean().hasUserPagePermission("customerBuyOrders")) {
			return (mapping.findForward("nopermissions"));
		}
		
		// if form is not null we have to perform some action
		if (form != null) {
			saveTcmISToken(request);
			// copy date from dyna form to the data form
			BuyOrdersInputBean input = new BuyOrdersInputBean(form);

			CustomerBuyerProcess process = new CustomerBuyerProcess(getDbUser(request));
			// check what button was pressed and determine where to go
			if (input.isSearch()) {
				request.setAttribute("poHeaderViewBeanCollection", process.getPurchaseOrderData(input, user));
				request.setAttribute("canEditPurchaseOrder", process.canEditPurchaseOrder(input, user));
				return (mapping.findForward("success"));
			}
			else if (input.isUpdate()) {
				try {
					DynaBean dynaBean = (DynaBean) form;
					List list = (List) dynaBean.get("BuyOrdersInputBean");
					if (list != null) {
						Collection buyOrderInputBeanCollection = new Vector();
						Iterator iterator = list.iterator();
						while (iterator.hasNext()) {
							BuyOrdersInputBean inputBean = new BuyOrdersInputBean();
							LazyDynaBean lazyBean = (LazyDynaBean) iterator.next();
							BeanHandler.copyAttributes(lazyBean, inputBean);
							buyOrderInputBeanCollection.add(inputBean);
						}
						// update data
						process.updateCustomerToReceiveData(buyOrderInputBeanCollection, user);
					}
					// re-fill data and display to user
					request.setAttribute("poHeaderViewBeanCollection", process.getPurchaseOrderData(input, user));
					request.setAttribute("canEditPurchaseOrder", process.canEditPurchaseOrder(input, user));
					return (mapping.findForward("success"));
				}
				catch (Exception e) {
					BaseException be = new BaseException();
					be.setMessageKey("error.db.update");
					throw be;
				}
			}
			else if (input.isCreateExcel()) {
				setExcel(response, "PurchaseOrdersPage");
				process.getPurchaseOrderExcelReport(input, user, user.getLocale()).write(response.getOutputStream());
				return noForward;
			}
			else {
				// log initial data for dropdown
				// make sure that user has at least one inventory group
				BuyPageProcess buyOrderProcess = new BuyPageProcess(this.getDbUser(request));
				Collection myInventoryGroup = process.getMyInventoryGroup(user);
				request.setAttribute("vvInventoryGroupCollection", myInventoryGroup);
				request.setAttribute("vvBuyerCollection", buyOrderProcess.getCustomerBuyerNames());
				// coming in from link
				if ("showPreviousPo".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
					request.setAttribute("showPreviousPo", "Yes");
					request.setAttribute("previousPoNumber", input.getCustomerPoNumber());
				}
				else {
					request.setAttribute("showPreviousPo", "No");
					request.setAttribute("previousPoNumber", "");
				}
				if (myInventoryGroup == null || myInventoryGroup.size() == 0) {
					return (mapping.findForward("nopermissions"));
				}
				else {
					return (mapping.findForward("success"));
				}
			}
		}
		else {
			String action = request.getParameter("action");
			if ("showPreviousPo".equalsIgnoreCase(action)) {
				String customerPoNumber = request.getParameter("customerPoNumber");
				request.setAttribute("action", action);
				request.setAttribute("customerPoNumber", customerPoNumber);
			}
			else {
				request.setAttribute("action", "");
				request.setAttribute("customerPoNumber", "");
			}
		}
		return mapping.findForward("success");
	} // end of method
} // end of class
