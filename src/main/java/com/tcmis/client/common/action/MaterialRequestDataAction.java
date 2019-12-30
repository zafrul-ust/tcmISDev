package com.tcmis.client.common.action;

import com.tcmis.client.common.beans.LineItemViewBean;
import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.client.common.process.MaterialRequestDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class MaterialRequestDataAction extends TcmISBaseAction {

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
	//	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		//BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		/*Need to check if the user has permissions to view this page. if they do not have the permission
		  we need to not show them the page.*/
		/*
		if (!personnelBean.getPermissionBean().hasUserPagePermission("pageId")) {
			return (mapping.findForward("nopermissions"));
		}*/		

		String forward = "success";

		MaterialRequestDataProcess mrDataProcess = new MaterialRequestDataProcess(this.getDbUser(request), getTcmISLocaleString(request));
		//copy date from dyna form to the data bean
		MaterialRequestInputBean inputBean = new MaterialRequestInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			Object[] results = null;
		
		if (action.equals("loaddata")) {
			LineItemViewBean requstLineItemBean = new LineItemViewBean();
			BeanHandler.copyAttributes(form, requstLineItemBean, this.getTcmISLocale(request));			
			request.setAttribute("lineItemCollection", mrDataProcess.getCartData(requstLineItemBean));
		}else if (action.equals("getApproverData")) {
			LineItemViewBean requstLineItemBean = new LineItemViewBean();
			BeanHandler.copyAttributes(form, requstLineItemBean, this.getTcmISLocale(request));	
			results = mrDataProcess.getApproverData(requstLineItemBean);
			request.setAttribute("approverDataCollection",results[0] );
			request.setAttribute("hasAdmin",results[1] );
		}else if (action.equals("getFinanceApproverData")) {
			LineItemViewBean requstLineItemBean = new LineItemViewBean();
			BeanHandler.copyAttributes(form, requstLineItemBean, this.getTcmISLocale(request));	
			results = mrDataProcess.getFinanceApproverData(requstLineItemBean);			
			request.setAttribute("financeApprover","true" );
			request.setAttribute("approverDataCollection",results[0] );
			request.setAttribute("hasAdmin",results[1] );
		}else if (action.equals("delete")) {
	    	if (inputBean.getPrNumber() != null) {
	    		int requestLineItemColl = mrDataProcess.deleteFromDeliverySchedule(inputBean);
	    	}
        }else if (action.equals("getApproverDetail")) {
			LineItemViewBean requstLineItemBean = new LineItemViewBean();
			BeanHandler.copyAttributes(form, requstLineItemBean, this.getTcmISLocale(request));
			request.setAttribute("approverCollection",mrDataProcess.getApproverDetails(requstLineItemBean) );
        }else if (action.equals("getReleaserDetail")) {
			LineItemViewBean requstLineItemBean = new LineItemViewBean();
			BeanHandler.copyAttributes(form, requstLineItemBean, this.getTcmISLocale(request));	
			results = mrDataProcess.getReleaserData(requstLineItemBean);
			request.setAttribute("approverDataCollection",results[0] );
			request.setAttribute("hasAdmin",results[1] );
		}
		//Saving Token, if its needed  and save token or checkToken is not called yet.
		//this.saveTcmISToken(request);
		
		return (mapping.findForward(forward));
	}
}
