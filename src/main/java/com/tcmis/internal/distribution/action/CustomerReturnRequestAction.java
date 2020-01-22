package com.tcmis.internal.distribution.action;

import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.distribution.beans.CustomerReturnFeeBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestInputBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRcptShpViewBean;
import com.tcmis.internal.distribution.beans.VvReturnReasonBean;
import com.tcmis.internal.distribution.process.CustomerReturnRequestProcess;

public class CustomerReturnRequestAction extends TcmISBaseAction {
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "customerreturnrequest");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//Process search
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
		CustomerReturnRequestProcess process = new CustomerReturnRequestProcess(this.getDbUser(request));
		CustomerReturnRequestInputBean inputBean = new CustomerReturnRequestInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		// Insert
		if (null != inputBean.getAction() && "insert".equals(inputBean.getAction())) {
			inputBean.setRmaStatus("Draft");
			inputBean.setReturnMaterial("Y");
			inputBean.setCorrectItemShipped("Y");
			inputBean.setWantReplacement("Y");
			inputBean.setRequestStartDate(new Date());
			inputBean.setDistributionReturn("Y");
			
			inputBean.setRmaId(process.addCustomerReturnRequest(inputBean, personnelBean));
			request.setAttribute("customerReturnRequestResultBean", process.getCustomerReturnRequestData(inputBean, personnelBean));
			request.setAttribute("rmaId", inputBean.getRmaId());
			saveTcmISToken(request);
		}
		// Delete
		else if (null != inputBean.getAction() && ("delete".equals(inputBean.getAction()))) {

			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				return mapping.findForward("success");
			}
			
			try {
				checkToken(request);
				process.deleteCustomerReturnRequest(inputBean);
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				request.setAttribute("tcmISErrors", ex.getMessage());
			}
			request.setAttribute("rmaId", inputBean.getRmaId());
			request.setAttribute("deleted", "deleted");
			return mapping.findForward("success");
		}
		// Update
		else
		if (null != inputBean.getAction() && ("update".equals(inputBean.getAction()) || "submit".equals(inputBean.getAction()) || "approve".equals(inputBean.getAction()) || "deny".equals(inputBean.getAction()))) {
			String err = "";
            if (inputBean.getReturnQuantityRequested() == null || inputBean.getReturnQuantityRequested().compareTo(BigDecimal.ONE) == -1)
            {
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale(request));
			request.setAttribute("tcmISError", library.getString("error.returnquantityrequestedpositiveinteger"));
			request.setAttribute("customerReturnRequestResultBean", process.getCustomerReturnRequestData(inputBean, personnelBean));
			request.setAttribute("rmaId", inputBean.getRmaId());
			saveTcmISToken(request);
			return mapping.findForward("success");
		    }
            if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.noaccesstopage"));

				request.setAttribute("customerReturnRequestResultBean", process.getCustomerReturnRequestData(inputBean, personnelBean));

				request.setAttribute("rmaId", inputBean.getRmaId());

				request.setAttribute("vvReasonBeanCollection", process.getReasonList());

				request.setAttribute("originalFeeLineChargeCollection", process.getOriginalFeeLineChargeList(inputBean));

				request.setAttribute("newItemsBeanCollection", process.getNewFeesItemList());

				Collection<VvReturnReasonBean> addedReasonsBeanCollection = process.getAddedCustomerReasons(inputBean);

				int addedReasonsCollectionSize = addedReasonsBeanCollection.size();

				request.setAttribute("reasonAddLimit", addedReasonsCollectionSize);

				request.setAttribute("reasonsBeanCollection", addedReasonsBeanCollection);

				request.setAttribute("prNumber", inputBean.getPrNumber());

				request.setAttribute("lineItem", inputBean.getLineItem());

				return mapping.findForward("success");
			}

			try {
				checkToken(request);

				if (!StringHandler.isBlankString(inputBean.getCopyReasonBean()) && "Yes".equals(inputBean.getCopyReasonBean())) {
					VvReturnReasonBean bean = new VvReturnReasonBean();
					Collection<VvReturnReasonBean> beans;
					if (!StringHandler.isBlankString(inputBean.getReasonsDeleteOnly())) {
						beans = null;
					} else {
						beans = BeanHandler.getBeans((DynaBean) form, "reasonDiv", bean, getTcmISLocale(request));
					}
					process.addReturnReasons(beans, inputBean);
				}
				
				Collection<CustomerReturnRcptShpViewBean> rcptBeans = BeanHandler.getBeans((DynaBean) form, "returnItemDiv", new CustomerReturnRcptShpViewBean(), getTcmISLocale(request));
				process.updateReceiptItemShippedIncorrectly(rcptBeans, inputBean);

				if (!StringHandler.isBlankString(inputBean.getCopyNewFeesBean()) && "Yes".equals(inputBean.getCopyNewFeesBean())) {
					CustomerReturnFeeBean bean = new CustomerReturnFeeBean();
					Collection<CustomerReturnFeeBean> beans;
					if (!StringHandler.isBlankString(inputBean.getNewFeesDeleteOnly())) {
						beans = null;
					} else {
						beans = BeanHandler.getBeans((DynaBean) form, "newFeesDiv", bean, getTcmISLocale(request));
					}

					process.addCustomerReturnFees(beans, inputBean);
				}

				err = process.updateCustomerReturnRequest(inputBean, personnelBean);
				if (!StringHandler.isBlankString(err)) {
					request.setAttribute("tcmISError", err);
				}else {
					if ("approve".equals(inputBean.getAction()) || "deny".equals(inputBean.getAction())) {
						try {
							err = process.approveRejectCustomerReturnRequest(inputBean, personnelBean);
						} catch (Exception e) {
							log.error(e.getMessage(), e);
							err = e.getMessage();
						}
						if (!StringHandler.isBlankString(err))
							request.setAttribute("tcmISError", err);
					}
				}
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				request.setAttribute("tcmISErrors", ex.getMessage());
			}
			if (err != null && !("OK").equalsIgnoreCase(err) && !("").equalsIgnoreCase(err))
				request.setAttribute("tcmISErrors", err);
			request.setAttribute("customerReturnRequestResultBean", process.getCustomerReturnRequestData(inputBean, personnelBean));
			request.setAttribute("rmaId", inputBean.getRmaId());
		}
		// Search
		else if (null != inputBean.getAction() && "search".equals(inputBean.getAction())) {
			request.setAttribute("customerReturnRequestResultBean", process.getCustomerReturnRequestData(inputBean, personnelBean));
			request.setAttribute("rmaId", inputBean.getRmaId());
			saveTcmISToken(request);
		}

		request.setAttribute("vvReasonBeanCollection", process.getReasonList());

		request.setAttribute("customerReturnRcptShpViewColl", process.getCustomerReturnRcptShpViewColl(inputBean));

		request.setAttribute("originalFeeLineChargeCollection", process.getOriginalFeeLineChargeList(inputBean));

		request.setAttribute("newItemsBeanCollection", process.getNewFeesItemList());

		Collection<VvReturnReasonBean> addedReasonsBeanCollection = process.getAddedCustomerReasons(inputBean);

		int addedReasonsCollectionSize = addedReasonsBeanCollection.size();

		request.setAttribute("reasonAddLimit", addedReasonsCollectionSize);

		request.setAttribute("reasonsBeanCollection", addedReasonsBeanCollection);

		request.setAttribute("newFeesAddedBeanCollection", process.getAddedNewsFeesList(inputBean));

		request.setAttribute("prNumber", inputBean.getPrNumber());

		request.setAttribute("lineItem", inputBean.getLineItem());

		return mapping.findForward("success");
	}
}