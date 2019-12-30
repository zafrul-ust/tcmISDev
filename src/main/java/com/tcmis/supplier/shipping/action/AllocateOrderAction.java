package com.tcmis.supplier.shipping.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.AllocateOrderInputBean;
import com.tcmis.supplier.shipping.beans.SupplierInventoryBean;
import com.tcmis.supplier.shipping.process.AllocateOrderProcess;

public class AllocateOrderAction extends TcmISBaseAction {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "allocateorder");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		ActionForward next = mapping.findForward("success");
		if (form != null) {
			request.setAttribute("startSearchTime", new Date().getTime());
			AllocateOrderProcess process = new AllocateOrderProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			AllocateOrderInputBean inputBean = new AllocateOrderInputBean(form, getLocale());
			if (inputBean.isConfirmAllocation()) {
				Collection<SupplierInventoryBean> resultBeanColl = BeanHandler.getBeans((DynaBean) form, "supplierInventoryBean", new SupplierInventoryBean(), getTcmISLocale(), "allocated");
				BigDecimal batchId = process.getBatchId();
				String confirmationResult = process.confirmAllocation(inputBean, resultBeanColl, personnelBean.getPersonnelIdBigDecimal(), batchId);
				if ("OK".equalsIgnoreCase(confirmationResult))
					request.setAttribute("batchId", batchId);
				else
					request.setAttribute("tcmISError", confirmationResult);
			} else {
				Collection<SupplierInventoryBean> searchResultsColl;
				if (StringHandler.isBlankString(process.getInventoryLevelId(inputBean.getSupplier(), inputBean.getShipFromLocationId(), inputBean.getCatPartNo()))) {
					searchResultsColl = new Vector();
					request.setAttribute("tcmISError", getResourceBundleValue(request, "error.partwithnominmax").replace("{0}", inputBean.getCatPartNo()));
				} else
					searchResultsColl = process.getSearchResult(inputBean);
				inputBean.setTotalLines(searchResultsColl.size());
				request.setAttribute("supplierInventoryBeanCollection", searchResultsColl);
			}
			request.setAttribute("searchInput", inputBean);
			request.setAttribute("endSearchTime", new Date().getTime());
		}
		
		return next;
	}
}