package com.tcmis.internal.distribution.action;

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
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.MrReleaseInputBean;
import com.tcmis.internal.distribution.beans.SalesQuoteLineViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import com.tcmis.internal.distribution.beans.UserOpsEntityPgViewBean;
import com.tcmis.internal.distribution.beans.VvReleaseStatusBean;
import com.tcmis.internal.distribution.process.MrReleaseProcess;

public class MrReleaseAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "mrreleasemain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		if (!personnelBean.getPermissionBean().hasUserPagePermission("mrRelease")) {
			return (mapping.findForward("nopermissions"));
		}

		MrReleaseProcess process = new MrReleaseProcess(this.getDbUser(request), getTcmISLocaleString(request));
		request.setAttribute("vvReleaseStatusCollection", process.getSearchResult("select distinct release_status,release_status_description from vv_release_status order by release_status_description", new VvReleaseStatusBean()));

		MrReleaseInputBean inputBean = new MrReleaseInputBean();

		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		String uAction = request.getParameter("uAction");
		if ("releasecreditnew".equals(uAction)) {
			Vector errorMsgs = new Vector();
//			test javascript first			
			errorMsgs.addAll(process.releaseCreditHold(inputBean,personnelBean));
			request.setAttribute("tcmISErrors", errorMsgs);
			return mapping.findForward("loadmrreleasecreditholdmsg");
		}
		
		if (inputBean.getAction() == null) return mapping.findForward("success");

		// Search
		if (("searchOrders").equals(inputBean.getAction())) {
//			Collection<SalesQuoteViewBean> salesOrderCollection = process.getSalesOrder(inputBean, personnelBean);		
//			request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
			
			Object obj = this.getSessionObject(request, "testData");
//			if( obj != null && "Y".equals(request.getParameter("testData"))) {
//				obj = this.getSessionObject(request, "testData");
//			}
//			else 
//			{
//				obj = process.getSalesOrderObj(inputBean, personnelBean);
//				this.setSessionObject(request, obj, "testData");
//			}
			obj = process.getSalesOrderObj(inputBean, personnelBean);
			request.setAttribute("mrReleaseViewBeanColl", obj);
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		else if ( "createExcel".equals(inputBean.getAction())){
	    	 this.setExcel(response, "DisplayExcel");
	         process.getExcelReport(process.getSalesOrderObj(inputBean, personnelBean), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	         return noForward;
	    }
		// Camcel
		else if (("cancel").equals(inputBean.getAction())) {
			if (!(personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null) || personnelBean.getPermissionBean().hasOpsEntityPermission("mrFinanceApprover", null, null) || personnelBean.getPermissionBean().hasInventoryGroupPermission("releaseOrder", null, null, null))) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
//				Collection<SalesQuoteViewBean> salesOrderCollection = process.getSalesOrder(inputBean, personnelBean);
				request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
				return (mapping.findForward("success"));
			}
			checkToken(request);
			try {

				Collection errorMsgs = process.cancelMR(personnelId, inputBean);
				request.setAttribute("tcmISErrors", errorMsgs);
			} catch (Exception e) {
				// TODO Auto-generated catch block				
				request.setAttribute("tcmISErrors", e.toString());
			}

			request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
//			Collection<SalesQuoteViewBean> salesOrderCollection = process.getSalesOrder(inputBean, personnelBean);
//			request.setAttribute("mrReleaseViewBeanColl", salesOrderCollection);

		} else if (("updateNote").equals(inputBean.getAction())) {
			request.setAttribute("tcmISError", process.updateNote(personnelId, inputBean));
			return mapping.findForward("loaddata");
		}
		// Release Credit Hold
		else if (("releaseCreditHold").equals(inputBean.getAction())) {
			if (!(personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null) || personnelBean.getPermissionBean().hasOpsEntityPermission("mrFinanceApprover", null, null) || personnelBean.getPermissionBean().hasInventoryGroupPermission("releaseOrder", null, null, null))) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
				return (mapping.findForward("success"));
			}
			checkToken(request);
			try {
				Vector errorMsgs = new Vector();
				
				Collection<MrReleaseInputBean> updateBeanCollection = BeanHandler.getGridBeans((DynaBean) form, "mrReleaseViewBean", new MrReleaseInputBean(), getTcmISLocale(request),"ok");
				for (MrReleaseInputBean bean: updateBeanCollection) {
//					errorMsgs.add("Test: "+bean.getPrNumber() +":"+bean.getCompanyId());
					errorMsgs.addAll(process.releaseCreditHold(bean,personnelBean));
				} 
//				errorMsgs.addAll(process.releaseCreditHold(inputBean,personnelBean));
				
				request.setAttribute("tcmISErrors", errorMsgs);
			} catch (Exception e) {
				// TODO Auto-generated catch block				
				request.setAttribute("tcmISErrors", e.toString());
			}
			request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
		}
		// Accept MR
		else if (("acceptMR").equals(inputBean.getAction())) {
			
			if (!personnelBean.getPermissionBean().hasOpsEntityPermission("acceptInterCompanyMr", null, null)) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
				return (mapping.findForward("success"));
			}
			
			checkToken(request);
			try {
				Vector errorMsgs = new Vector();
				
				Collection<MrReleaseInputBean> updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "mrReleaseViewBean", new MrReleaseInputBean(), getTcmISLocale(request));
/*				
				for (MrReleaseInputBean bean: updateBeanCollection) {
					if(bean.getPrNumber().equals(inputBean.getPrNumber()) && bean.getPromiseDate() != null && !bean.getPromiseDate().equals(bean.getOriginalPromiseDate())) 
						errorMsgs.addAll(process.updatePromiseDate(bean));
				}  */
				
				if(inputBean.getPrNumber() != null)
					errorMsgs.addAll(process.acceptPurchaseRequest(inputBean, personnelBean));
				
				request.setAttribute("tcmISErrors", errorMsgs);
			} catch (Exception e) {
				// TODO Auto-generated catch block				
				request.setAttribute("tcmISErrors", e.toString());
			}
			request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
		}
		// Release Quality Hold
		else if (("releaseQualityHold").equals(inputBean.getAction())) {
			if (!(personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null) || personnelBean.getPermissionBean().hasOpsEntityPermission("mrFinanceApprover", null, null) || personnelBean.getPermissionBean().hasInventoryGroupPermission("releaseOrder", null, null, null))) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
				return (mapping.findForward("success"));
			}
			checkToken(request);
			try {
				Vector errorMsgs = new Vector();
				
/*				if(inputBean.getPromiseDate() != null) 
					errorMsgs.addAll(process.updatePromiseDate(inputBean));  */
				
				errorMsgs.addAll(process.releaseQualityHold(inputBean,personnelBean));
				
				request.setAttribute("tcmISErrors", errorMsgs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				request.setAttribute("tcmISErrors", e.toString());
			}
			request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
		}
		//	Release Force Hold
		else if (("releaseForceHold").equals(inputBean.getAction())) {
			if (!(personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null) || personnelBean.getPermissionBean().hasOpsEntityPermission("mrFinanceApprover", null, null) || personnelBean.getPermissionBean().hasInventoryGroupPermission("releaseOrder", null, null, null))) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
				return (mapping.findForward("success"));
			}
			checkToken(request);
			try {
				Vector errorMsgs = new Vector();
				
/*				if(inputBean.getPromiseDate() != null) 
					errorMsgs.addAll(process.updatePromiseDate(inputBean));  */
				
				errorMsgs.addAll(process.releaseForceHold(inputBean,personnelBean));
				
				request.setAttribute("tcmISErrors", errorMsgs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				request.setAttribute("tcmISErrors", e.toString());
			}
			request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
		}
//		 Release Expert Review Hold
		else if (("releaseExpertReviewHold").equals(inputBean.getAction())) {
			if (!personnelBean.getPermissionBean().hasOpsEntityPermission("expertReviewer", inputBean.getOpsEntityId(), null)) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
				return (mapping.findForward("success"));
			}
			checkToken(request);
			try {
				Vector errorMsgs = new Vector();
				
/*				if(inputBean.getPromiseDate() != null) 
					errorMsgs.addAll(process.updatePromiseDate(inputBean));  */
				
				errorMsgs.addAll(process.releaseExpertReviewHold(inputBean,personnelBean));
				
				request.setAttribute("tcmISErrors", errorMsgs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				request.setAttribute("tcmISErrors", e.toString());
			}
			request.setAttribute("mrReleaseViewBeanColl", process.getSalesOrderObj(inputBean, personnelBean));
		}

		return mapping.findForward("success");

	}

}
