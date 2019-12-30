package com.tcmis.client.common.action;

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
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.client.common.beans.MaterialRequestHeaderViewBean;
import com.tcmis.client.common.beans.MaterialRequestInputBean;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class MaterialRequestAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		String action = request.getParameter("action");
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "materialrequest");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//If you need access to who is logged in
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		/*Since there is no search section we consider this the start time. This should be done only for
		 * pages that have no search section.*/
		request.setAttribute("startSearchTime", new Date().getTime());

		String forward = "success";

		StringBuffer requestURL = request.getRequestURL();
		MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getDbUser(request), getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
		//copy date from dyna form to the data bean
		MaterialRequestInputBean inputBean = new MaterialRequestInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		if (action.equalsIgnoreCase("submit")) {
			log.debug("submit");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.submitMr(inputBean,beans,personnelBean);
            request.setAttribute("chargeNumberApprovalNeeded",inputBean.getChargeNumberApprovalNeeded());
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//		request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
        }else if (action.equalsIgnoreCase("approve")) {
			log.debug("approve");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.approveMr(inputBean,beans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//	   request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
        }else if (action.equalsIgnoreCase("approveChargeData")) {
			log.debug("approve charge data");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.approveChargeData(inputBean,beans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//		request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
        }else if (action.equalsIgnoreCase("release")) {
			log.debug("release");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.releaseMr(inputBean,beans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
		setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//		request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
		}else if (action.equalsIgnoreCase("reject")) {
			log.debug("reject");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.rejectMr(inputBean,beans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//			request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
		}else if (action.equalsIgnoreCase("approveUse")) {
			log.debug("approve use");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.approveUse(inputBean,beans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
		setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//		request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
		}else if (action.equalsIgnoreCase("requestCancel")) {
			log.debug("request cancel");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.requestCancel(inputBean,beans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//			request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
		}else if (action.equalsIgnoreCase("save") || action.equalsIgnoreCase("returnToCart") || action.equalsIgnoreCase("printScreen")) {
			log.debug(action);
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.saveMr(inputBean,beans,personnelBean.getCompanyId());
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//			request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
		}else if (action.equalsIgnoreCase("deleteLine")) {
			log.debug("delete line");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.deleteMrLine(inputBean,beans);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
		setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//		request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
		}else if (action.equalsIgnoreCase("delete")) {
			log.debug("delete");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			String errorMsg = mrProcess.deleteMr(inputBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
				request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
			}
        }else if (action.equalsIgnoreCase("overrideAllocateChargeNumber")) {
			log.debug("overrideAllocateChargeNumber");
			return mapping.findForward("overrideAllocateChargeNumber");
        }else if (action.equalsIgnoreCase("approveCancel")) {
        	if (log.isDebugEnabled())
        		log.debug("approve request cancel");
			//If the page is being updated I check for a valid token.
			//checkToken will also save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.approveRequestCancel(inputBean,beans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//		request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
		}else if (action.equalsIgnoreCase("rejectCancel")) {
			if (log.isDebugEnabled())
				log.debug("reject request cancel");
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.rejectRequestCancel(inputBean,beans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//		request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
		 }else if (action.equalsIgnoreCase("approveQtyChange")) {
			if (log.isDebugEnabled())
				log.debug("approve Qty Change");
			//If the page is being updated I check for a valid token.
			//checkToken will also save token for you to avoid duplicate form submissions.
			checkToken(request);
			Collection<MaterialRequestInputBean> beans = BeanHandler.getBeans((DynaBean)form,"materialRequestDivId",new MaterialRequestInputBean(),this.getTcmISLocale(request));
			String errorMsg = mrProcess.approveQtyChange(inputBean,beans);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//			request.setAttribute("materialRequestData", mrProcess.getMrData(inputBean,personnelBean));
		 }else {
			log.debug("load initial data");
			saveTcmISToken(request);
			//Do stuff to get data you are going to need to build the search options
			//or any other extra data you need, otherwise you don't need this section and let it drop through
			setData(request, mrProcess,mrProcess.getMrData(inputBean,personnelBean));
//			MaterialRequestHeaderViewBean data = mrProcess.getMrData(inputBean,personnelBean);
//	       	Object[] results = mrProcess.getExtraInfo(data.getLineItemColl());
//        	request.setAttribute("qualityIdLabelColumnHeader", results[0]);
//        	request.setAttribute("catPartAttrColumnHeader", results[1]);
//			request.setAttribute("materialRequestData", data);
		}
		/*Since there is no search section we have to set end Time here as we cannot use the client side time.
		Client can be anywhere in the world.This should be done only for pages that have no search section.*/
		request.setAttribute("endSearchTime", new Date().getTime());
		return (mapping.findForward(forward));
	}
	private void setData(HttpServletRequest request,MaterialRequestProcess mrProcess,MaterialRequestHeaderViewBean data) {
       	Object[] results = mrProcess.getExtraInfo(data.getLineItemColl());
    	request.setAttribute("qualityIdLabelColumnHeader", results[0]);
    	request.setAttribute("catPartAttrColumnHeader", results[1]);
		request.setAttribute("materialRequestData", data);
	}
}
