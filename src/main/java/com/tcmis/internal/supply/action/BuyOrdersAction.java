package com.tcmis.internal.supply.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.process.AllocationAnalysisProcess;
import com.tcmis.internal.supply.beans.BuyOrdersInputBean;
import com.tcmis.internal.supply.process.BuyOrdersProcess;
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

/**
 * ***************************************************************************
 * Controller for new supplier requests
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class BuyOrdersAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "buyordersmain");
			// This is to save any parameters passed in the URL, so that they
			// can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		if (!user.getPermissionBean().hasUserPagePermission("newBuyOrders") && !user.getPermissionBean().hasUserPagePermission("buyOrders")) {
			return (mapping.findForward("nopermissions"));
		}

		// if form is not null we have to perform some action
		if (form == null) {
			return mapping.findForward("main");
		}

		String forward = "result";
		BuyOrdersInputBean input = new BuyOrdersInputBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));
		BuyOrdersProcess process = new BuyOrdersProcess(getDbUser(request), getTcmISLocaleString(request));

		String action = (String) ((DynaBean) form).get("action");
		if (input.isInit()) {
			AllocationAnalysisProcess allocationAnalysisProcess = new AllocationAnalysisProcess(getDbUser(request), getTcmISLocaleString(request));
			request.setAttribute("hubInventoryGroupFacOvBeanCollection", allocationAnalysisProcess.getHubDropDownData(user.getPersonnelIdBigDecimal()));
			request.setAttribute("buyerBeanCollection", process.getBuyerDropDown());
			request.setAttribute("companyBeanCollection", process.getCompanyDropDown());
			forward = "search";
		}
		else if (input.isConvertPurchase()) {
			Collection<BuyOrdersInputBean> selectedRows = new Vector<BuyOrdersInputBean>();
			DynaBean dynaBean = (DynaBean) form;
			
			@SuppressWarnings("unchecked")
			List<LazyDynaBean> buyOrdersInputBeans = (List<LazyDynaBean>) dynaBean.get("buyOrdersInputBean");
			for (LazyDynaBean inputRow : buyOrdersInputBeans) {
				BuyOrdersInputBean row = new BuyOrdersInputBean(inputRow);
				if (row.isSelected()) {
					row.setSupplyPath("Customer");
					selectedRows.add(row);
				}
			}

			process.update(selectedRows, user);
			request.setAttribute("tcmISError", "");
			request.setAttribute("buyPageViewBeanCollection", process.getSearchData(user, input, (Collection) getApplicationObject("hubIgBuyerCollection")));
		}
		else if (input.isUpdate()) {
			Collection<BuyOrdersInputBean> c = new Vector<BuyOrdersInputBean>();
			DynaBean dynaBean = (DynaBean) form;
			@SuppressWarnings("unchecked")
			List<LazyDynaBean> buyOrdersInputBeans = (List<LazyDynaBean>) dynaBean.get("buyOrdersInputBean");

			if (buyOrdersInputBeans != null) {
				Iterator iterator = buyOrdersInputBeans.iterator();
				while (iterator.hasNext()) {
					BuyOrdersInputBean inputBean = new BuyOrdersInputBean();
					LazyDynaBean lazyBean = (LazyDynaBean) iterator.next();
					BeanHandler.copyAttributes(lazyBean, inputBean, getTcmISLocale(request));
					c.add(inputBean);
				}
			}
			process.update(c, user);
			request.setAttribute("tcmISError", "");
			request.setAttribute("buyPageViewBeanCollection", process.getSearchData(user, input, (Collection) getApplicationObject("hubIgBuyerCollection")));
		}
		else if (input.isCreatePO()) {
			Collection c = new Vector();
			DynaBean dynaBean = (DynaBean) form;
			List buyOrdersInputBeans = (List) dynaBean.get("buyOrdersInputBean");

			if (buyOrdersInputBeans != null) {
				Iterator iterator = buyOrdersInputBeans.iterator();
				while (iterator.hasNext()) {
					BuyOrdersInputBean inputBean = new BuyOrdersInputBean();
					LazyDynaBean lazyBean = (LazyDynaBean) iterator.next();
					BeanHandler.copyAttributes(lazyBean, inputBean, getTcmISLocale(request));
					inputBean.setSelectedSupplierForPo(input.getSelectedSupplierForPo());
					c.add(inputBean);
				}
			}
			String[] createResult = process.createPo(c, user, request.getLocale());
			request.setAttribute("poNumber", createResult[0]);
			request.setAttribute("tcmISError", createResult[1]);
			request.setAttribute("buyPageViewBeanCollection", process.getSearchData(user, input, (Collection) getApplicationObject("hubIgBuyerCollection")));
		}
		else if (input.isSearch()) {
			request.setAttribute("buyPageViewBeanCollection", process.getSearchData(user, input, (Collection) getApplicationObject("hubIgBuyerCollection")));
		}
		else if (input.isCreateExcel()) {
			setExcel(response, "Buy Order Excel");
			process.getExcelReport(user, input, (Collection) getApplicationObject("hubIgBuyerCollection"), getTcmISLocale(request))
					.write(response.getOutputStream());
			return noForward;
		}
		return mapping.findForward(forward);
	}
}