package com.tcmis.client.common.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.AccountSysHeaderViewBean;
import com.tcmis.client.common.beans.ChargeNoBean;
import com.tcmis.client.common.beans.ChargeNumberSetUpInputBean;
import com.tcmis.client.common.process.ChargeNumberSetUpProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class ChargeNumberSetUpAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "chargenumbersetupmain");
			//	This is to save any parameters passed in the URL, so that they can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}   	

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		
		// Need to have the permission from the database, normally it's the pageName instead of "mrRelease"
		if (!personnelBean.getPermissionBean().hasUserPagePermission("chargeNumberSetup"))
		{
			return (mapping.findForward("nopermissions"));
		}   
		
		ChargeNumberSetUpProcess process = new ChargeNumberSetUpProcess(this.getDbUser(request),getTcmISLocaleString(request));	    
		
        // copy date from dyna form to the data bean
		ChargeNumberSetUpInputBean inputBean = new ChargeNumberSetUpInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		
		if( form == null )
	      	return (mapping.findForward("success"));
		
		String uAction = (String) ( (DynaBean)form).get("uAction");
		
		// Search
		if ("search".equals(uAction)) {
			Vector<AccountSysHeaderViewBean> headerCollection = (Vector)process.getHeaderLabels(inputBean);
			request.setAttribute("headerColl", headerCollection.get(0));
			Collection chargeNoColl = process.getSearchResult(inputBean);		
			request.setAttribute("chargeNoColl", chargeNoColl);
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));  
		}
		else if ( "update".equals(uAction)){
			if(!personnelBean.getPermissionBean().hasFacilityPermission("ChargeNumberSetup", inputBean.getFacilityId(), inputBean.getCompanyId())) {
				Vector<AccountSysHeaderViewBean> headerCollection = (Vector)process.getHeaderLabels(inputBean);
				request.setAttribute("headerColl", headerCollection.get(0));
				Collection chargeNoColl = process.getSearchResult(inputBean);		
				request.setAttribute("chargeNoColl", chargeNoColl);
				this.saveTcmISToken(request);
				return (mapping.findForward("success"));  
			}
			checkToken(request);
			// get the data from grid
			Collection<ChargeNoBean> beans = BeanHandler.getBeans((DynaBean) form, "chargeNumberBean", new ChargeNoBean(), getTcmISLocale(request));
			
			Vector errorMsgs = process.update(inputBean, beans, personnelBean);
			
			if(errorMsgs != null && errorMsgs.size() > 0)
				request.setAttribute("tcmISErrors", errorMsgs);
		
			Vector<AccountSysHeaderViewBean> headerCollection = (Vector)process.getHeaderLabels(inputBean);
			request.setAttribute("headerColl", headerCollection.get(0));
			Collection chargeNoColl = process.getSearchResult(inputBean);		
			request.setAttribute("chargeNoColl", chargeNoColl);
			return (mapping.findForward("success"));  
	    }
		else if ( "createExcel".equals(uAction)){
			AccountSysHeaderViewBean accountSysHeaderViewBean = new AccountSysHeaderViewBean();
			BeanHandler.copyAttributes(form, accountSysHeaderViewBean, getTcmISLocale(request));
			Collection chargeNoColl = process.getSearchResult(inputBean);		
	    	this.setExcel(response, "DisplayExcel");
	        process.getExcelReport(accountSysHeaderViewBean, chargeNoColl, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	        return noForward;  
	    }
		else if ("getUseCount".equals(uAction)) {
			String count = process.getInUseCount(inputBean);		
			request.setAttribute("count", count);
			return (mapping.findForward("getInUse"));  
		}
		else {
			Collection prRulesFacColl = process.getDropdown(personnelBean);
			request.setAttribute("prRulesFacColl", prRulesFacColl);
		}
		

		return mapping.findForward("success");

	}

}
