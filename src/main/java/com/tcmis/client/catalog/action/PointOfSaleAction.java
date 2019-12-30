package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
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

import com.tcmis.client.common.beans.*;
import com.tcmis.client.catalog.process.PointOfSaleProcess;


/**
 * ***************************************************************************
 * Controller for point of sale
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class PointOfSaleAction extends TcmISBaseAction {
   private static final String POINT_OF_DALE_INVENTORY_COLL					= "pointOfSaleInventoryColl";
	private static final String ROW_COUNT_LINE									= "rowCountLine";
	private static final String ROW_COUNT_RECEIPT								= "rowCountReceipt";
	private static final String FINANCE_APPROVER_LIST_BEAN					= "financeApproverListBean";
	private static final String USER_NAME											= "userName";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {


		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "pointofsale");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form == null) return (mapping.findForward("success"));

		String action = (String) ((DynaBean) form).get("action");
		if (action == null) return mapping.findForward("success");
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		PointOfSaleProcess process = new PointOfSaleProcess(this.getDbUser(request), getTcmISLocaleString(request));

		//copy date from dyna form to the data form
	 	PointOfSaleInventoryViewBean bean = new PointOfSaleInventoryViewBean();
	 	BeanHandler.copyAttributes(form, bean);
		String forward = "success";
		if ("search".equalsIgnoreCase(action)) {
			saveTcmISToken(request);
			log.debug("here in search");
			if (bean.getPrNumber() != null) {
				Object[] results = process.showResult(bean);
				request.setAttribute(POINT_OF_DALE_INVENTORY_COLL, results[0]);
				request.setAttribute(ROW_COUNT_LINE, results[1]);
				request.setAttribute(ROW_COUNT_RECEIPT, results[2]);
				request.setAttribute(FINANCE_APPROVER_LIST_BEAN,process.getUserOrderingLimit(bean));
				request.setAttribute(USER_NAME,process.getUserName(bean));
			}
		}else if ("submit".equalsIgnoreCase(action)) {
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			log.debug("here in submit");
			if (bean.getPrNumber() != null) {
				Collection<PointOfSaleInventoryViewBean> beans = BeanHandler.getBeans((DynaBean)form,"pointOfSaleInventoryViewBean",new PointOfSaleInventoryViewBean(),this.getTcmISLocale(request));
				String errorMsg = process.submit(personnelBean,bean,beans);
				if (!"OK".equalsIgnoreCase(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}

				Object[] results = process.showResult(bean);
				request.setAttribute(POINT_OF_DALE_INVENTORY_COLL, results[0]);
				request.setAttribute(ROW_COUNT_LINE, results[1]);
				request.setAttribute(ROW_COUNT_RECEIPT, results[2]);
				request.setAttribute(FINANCE_APPROVER_LIST_BEAN,process.getUserOrderingLimit(bean));
				request.setAttribute(USER_NAME,process.getUserName(bean));
			}
		}else if ("showPointOfSaleReceipt".equalsIgnoreCase(action)) {
			request.setAttribute("pointOfSaleReceiptColl",process.getPOSReceiptData(bean.getPrNumber()));
			forward = "pointOfSaleReceipt";
		}else if ("closeTap".equalsIgnoreCase(action)) {
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			log.debug("here in closeTap");
			if (bean.getPrNumber() != null) {
				String errorMsg = process.closeTap(bean);
				if (!"OK".equalsIgnoreCase(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}
				//reload data
				Object[] results = process.showResult(bean);
				request.setAttribute(POINT_OF_DALE_INVENTORY_COLL, results[0]);
				request.setAttribute(ROW_COUNT_LINE, results[1]);
				request.setAttribute(ROW_COUNT_RECEIPT, results[2]);
				request.setAttribute(FINANCE_APPROVER_LIST_BEAN,process.getUserOrderingLimit(bean));
				request.setAttribute(USER_NAME,process.getUserName(bean));
			}
		}else if ("tapReceipt".equalsIgnoreCase(action)) {
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			log.debug("here in tapReceipt");
			if (bean.getPrNumber() != null) {
				log.debug(bean.toString());
				String errorMsg = process.openTap(bean);
				if (!"OK".equalsIgnoreCase(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}
				//reload data
				Object[] results = process.showResult(bean);
				request.setAttribute(POINT_OF_DALE_INVENTORY_COLL, results[0]);
				request.setAttribute(ROW_COUNT_LINE, results[1]);
				request.setAttribute(ROW_COUNT_RECEIPT, results[2]);
				request.setAttribute(FINANCE_APPROVER_LIST_BEAN,process.getUserOrderingLimit(bean));
				request.setAttribute(USER_NAME,process.getUserName(bean));
			}
		}else if ("cancelMr".equalsIgnoreCase(action)) {
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			log.debug("here in cancelMR");
			process.cancelPOSRequest(bean.getPrNumber(),false);
			request.setAttribute(POINT_OF_DALE_INVENTORY_COLL, new Vector(0));
		}else {
			//do nothing for now
		}
		return mapping.findForward(forward);
	}
}

