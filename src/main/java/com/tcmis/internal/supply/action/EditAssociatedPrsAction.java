package com.tcmis.internal.supply.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.AssociatePrViewBean;
import com.tcmis.internal.supply.beans.EditAssociatedPrsInputBean;
import com.tcmis.internal.supply.beans.VvBuyOrderStatusBean;
import com.tcmis.internal.supply.process.BuyPageProcess;
import com.tcmis.internal.supply.process.EditAssociatedPrsProcess;

public class EditAssociatedPrsAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "editassociatedprsmain");
			//This is to save any parameters passed in the URL, so that they  
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
        if (!personnelBean.getPermissionBean().hasUserPagePermission("newBuyOrders") &&
            !personnelBean.getPermissionBean().hasUserPagePermission("buyOrders"))
        {
            return (mapping.findForward("nopermissions"));
        }

        String forward = "success";
		EditAssociatedPrsInputBean bean = new EditAssociatedPrsInputBean();
		if (form != null) {
	        EditAssociatedPrsProcess process = new EditAssociatedPrsProcess(this.getDbUser(request),this.getTcmISLocaleString(request));
			BeanHandler.copyAttributes(form, bean);
			if ("editassociatedPRs".equalsIgnoreCase(bean.getuAction()) || "addBuyOrdersToPO".equalsIgnoreCase(bean.getuAction())) {
				request.setAttribute("buyerBeanCollection", process.getBuyerDropDown());
				request.setAttribute("companyBeanCollection", process.getCompanyDropDown());
				forward = "success";
				request.setAttribute("updateSuccess", "");//leave empty to prevent the window from closing
			} else if ("UpdateEditAssociatedPRs".equals(bean.getuAction())){
				forward = "success";
				Collection<AssociatePrViewBean> pageGridBeans = BeanHandler.getBeans((DynaBean)form,"editAssociatedPrsViewBean",new AssociatePrViewBean(),this.getTcmISLocale(request));
				Collection<VvBuyOrderStatusBean> vvBuyOrderStatusBeanCollection = (Collection<VvBuyOrderStatusBean>) new BuyPageProcess(this.getDbUser(request)).getBuyOrderStatus();
				Vector<String> buyOrderStatusToLockColl = new Vector<String>();
				for (VvBuyOrderStatusBean statusBean : vvBuyOrderStatusBeanCollection)
					if ("Y".equalsIgnoreCase(statusBean.getLockStatus()))
						buyOrderStatusToLockColl.add(statusBean.getStatus());
				try {
					request.setAttribute("isFullyDisassociated", process.UpdateEditPrs(pageGridBeans, bean, personnelBean, buyOrderStatusToLockColl));
					request.setAttribute("updateSuccess", true);
				} catch (Exception e) {
					request.setAttribute("updateSuccess", false);
					request.setAttribute("errorMessage", getResourceBundleValue(request, "error.updatetryagain"));
				}
			} else if ("UpdateaddBuyOrdersToPO".equals(bean.getuAction())) {
				forward = "success";
				Collection<AssociatePrViewBean> pageGridBeans = BeanHandler.getBeans((DynaBean)form,"editAssociatedPrsViewBean",new AssociatePrViewBean(),this.getTcmISLocale(request));
		        boolean updateResults = process.UpdateAddPrsToPo(pageGridBeans, bean, personnelBean);
		        request.setAttribute("updateSuccess", updateResults);
				if (!updateResults)
					request.setAttribute("errorMessage", getResourceBundleValue(request, "error.updatetryagain"));
			}
			request.setAttribute("editAssociatedPrsViewBeanCollection", process.getSearchResult(bean,personnelBean, bean.getuAction()));
		}
		request.setAttribute("searchInput", bean);
		
		return mapping.findForward(forward);
	}
}
