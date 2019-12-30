package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

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
import com.tcmis.internal.distribution.beans.CatalogItemAddChargeBean;
import com.tcmis.internal.distribution.beans.MrAddChargeViewBean;
import com.tcmis.internal.distribution.beans.VvChargeRecurrenceBean;
import com.tcmis.internal.distribution.process.AddChargeProcess;

public class AddChargeHeaderAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		String source = "addchargeheader";
		if (!this.checkLoggedIn(source))
			return mapping.findForward("login");
		request.setAttribute("source", source);
		/*
		 * Since there is no search section we consider this the start
		 * time. This should be done only for pages that have no search
		 * section.
		 */
		request.setAttribute("startSearchTime", new Date().getTime());
		String forward = "success";

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		MrAddChargeViewBean inputBean = new MrAddChargeViewBean();
		BeanHandler.copyAttributes(form, inputBean);
		if (inputBean.getPrNumber() == null)
			return mapping.findForward("nopermission");

		AddChargeProcess process = new AddChargeProcess(this);

		String action = request.getParameter("uAction");
		log.debug(action);
		if (action != null && "update".equals(action)) {
			this.checkToken(request);
			String ErrorMsg = "";
			if (personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", request.getParameter("inventoryGroup"), null, null)
					|| personnelBean.getPermissionBean().hasInventoryGroupPermission("shipConfirm", request.getParameter("inventoryGroup"), null, null)) {
				Collection<MrAddChargeViewBean> beans = BeanHandler.getBeans((DynaBean) form, "MrAddChargeView", new MrAddChargeViewBean(), this.getTcmISLocale());
				for (MrAddChargeViewBean bean : beans) {
					if ("New".equals(bean.getChanged()))
						ErrorMsg += process.addHeaderCharge(bean, personnelId);
					else if ("Remove".equals(bean.getChanged()))
						ErrorMsg += process.removeHeader(bean);
					else
						ErrorMsg += process.updateHeader(bean);
				}
			}
			request.setAttribute("tcmISError", ErrorMsg);
		}
		if ("MR".equalsIgnoreCase(inputBean.getOrderType())) {
			request.setAttribute("beanColl", process.getSearchResult("select * from table (pkg_rli_sales_order.fx_additional_charge({0}, {1}))", inputBean, inputBean.getCompanyId(), inputBean.getPrNumber()));
		}
		else {
			request.setAttribute("beanColl", process.getSearchResult("select * from table (PKG_SALES_QUOTE.fx_additional_charge({0}, {1}))", inputBean, inputBean.getCompanyId(), inputBean.getPrNumber()));
		}
		request.setAttribute("feeDropDown", process.getSearchResult( "select * from table (pkg_catalog_item_add_charge.fx_cat_item_add_charge_data({0}, {1})) where Header_Charge={2} ORDER BY ITEM_DESCRIPTION", new CatalogItemAddChargeBean(), inputBean.getOpsEntityId(),inputBean.getCurrencyId(), "Y"));
		request.setAttribute("addChargeRecurrenceDropDown", process.getSearchResult(new VvChargeRecurrenceBean(), "vv_charge_recurrence", "headerApplicable", "Y"));

		this.saveTcmISToken(request);
		request.setAttribute("endSearchTime", new Date().getTime());
		return (mapping.findForward(forward));
	}
}