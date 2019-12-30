package com.tcmis.internal.hub.action;

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
import com.tcmis.internal.hub.beans.PwcOrderLookupViewBean;
import com.tcmis.internal.hub.beans.PwcOrderNotifyErrorViewBean;
import com.tcmis.internal.hub.beans.PwcResendResponseInputBean;
import com.tcmis.internal.hub.process.PwcResendResponseProcess;


/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */  

public class PwcResendResponseAction
        extends TcmISBaseAction {
		
    public ActionForward executeAction(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            BaseException, Exception {

    	if (!this.isLoggedIn(request)) {
    		request.setAttribute("requestedPage", "pwcresendresponse");
    		request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
    		return (mapping.findForward("login"));
    	}
      
    	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    	if (!personnelBean.getPermissionBean().hasUserPagePermission("pwcResendResponse"))
    	{
    		return (mapping.findForward("nopermissions"));
    	}

    	String forward = "success";
    	request.setAttribute("startSearchTime", new Date().getTime());
       
        PwcResendResponseInputBean pwcResendResponseInputBean = new PwcResendResponseInputBean();
        BeanHandler.copyAttributes(form, pwcResendResponseInputBean,getTcmISLocale(request));

        PwcResendResponseProcess process = new PwcResendResponseProcess(this.getDbUser(request),getTcmISLocaleString(request));

        if ("resendFOA".equalsIgnoreCase(pwcResendResponseInputBean.getAction())) {
        	if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("Inventory",null,null,null))
        	{
        		request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
        		pwcResendResponseInputBean.setAction("search");
        	}
        	else {
        		Collection<PwcOrderLookupViewBean> beans = BeanHandler.getBeans((DynaBean) form, "pwcResendResponseViewBean", new PwcOrderLookupViewBean(),getTcmISLocale(request));
        		Collection<String> errorMsgs = process.resendFullOrderAck(beans);
        		if (errorMsgs.size() > 0) {
        			request.setAttribute("tcmISErrors", errorMsgs); 
        		}
        		pwcResendResponseInputBean.setAction("search");
        	}
        }
        else if ("update".equalsIgnoreCase(pwcResendResponseInputBean.getAction())) {
        	if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("Inventory",null,null,null))
        	{
        		request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
        		pwcResendResponseInputBean.setAction("search");
        	}
        	else {
        		//checkToken(request);
        		PwcOrderNotifyErrorViewBean bean = new PwcOrderNotifyErrorViewBean();
        		Collection<PwcOrderNotifyErrorViewBean> beans = BeanHandler.getBeans((DynaBean) form, "pwcResendResponseViewBean", bean,getTcmISLocale(request));
        		
        		Collection errorMsgs = process.update(beans);
        		request.setAttribute("tcmISErrors", errorMsgs);
        		pwcResendResponseInputBean.setAction("search");
        	}
        	//this.saveTcmISToken(request);
        }
        else if ("resetSendResponse".equalsIgnoreCase(pwcResendResponseInputBean.getAction())) {
        	if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("Inventory",null,null,null))
        	{
        		request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
        		pwcResendResponseInputBean.setAction("search");
        	}
        	else {
        		PwcOrderNotifyErrorViewBean bean = new PwcOrderNotifyErrorViewBean();
        		Collection<PwcOrderNotifyErrorViewBean> beans = BeanHandler.getBeans((DynaBean) form, "pwcResendResponseViewBean", bean,getTcmISLocale(request));
        		
        		Collection errorMsgs = process.resetSendResponse(beans);
        		request.setAttribute("tcmISErrors", errorMsgs);
        		pwcResendResponseInputBean.setAction("search");
        	}
        }
        // Search
        if ("search".equalsIgnoreCase(pwcResendResponseInputBean.getAction())) {
        	if ("poLookup".equalsIgnoreCase(pwcResendResponseInputBean.getLookupAction())) {
        		request.setAttribute("pwcResendResponseViewBeanCollection", process.getPoLookupResult(pwcResendResponseInputBean));
        		//this.saveTcmISToken(request);
        	}
        	else if ("notifyError".equalsIgnoreCase(pwcResendResponseInputBean.getLookupAction())){
        		request.setAttribute("pwcResendResponseViewBeanCollection", process.getNotifyErrorResult(pwcResendResponseInputBean));
        		//this.saveTcmISToken(request);
        	}
        	else {
        		request.setAttribute("pwcResendResponseViewBeanCollection", process.getOrderLookupResult(pwcResendResponseInputBean));
        	}
        }
          
        request.setAttribute("endSearchTime", new Date().getTime() ); 
        return (mapping.findForward(forward));
    }
}
