package com.tcmis.internal.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ManufacturerBean;
import com.tcmis.internal.catalog.beans.MsdsAuthorBean;
import com.tcmis.internal.catalog.process.ManufacturerSearchProcess;
import com.tcmis.internal.catalog.process.MsdsAuthorSearchProcess;

/******************************************************************************
 * Controller for Manufacturer Search page
 * @version 1.0
 ******************************************************************************/

public class ManufacturerSearchAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		String loginNeeded = request.getParameter("loginNeeded");
		
		if (!"N".equals(loginNeeded) && !this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "manufacturersearchmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

//		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
//		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		String userAction = request.getParameter("uAction");

		if (userAction == null) {
			return (mapping.findForward("success"));
		}
		DynaBean dynaForm = (DynaBean) form;

		if (userAction.equals("search")) {
			String searchArgument = (String) dynaForm.get("searchArgument");
			ManufacturerSearchProcess process = new ManufacturerSearchProcess(this.getDbUser(request));
			Collection manufacturerBeanCollection = process.getManufacturerBeanCollection(searchArgument);
			request.setAttribute("manufacturerBeanCollection", manufacturerBeanCollection);
			this.saveTcmISToken(request);
		}
		else if (userAction.equals("new")) {
			ManufacturerSearchProcess process = new ManufacturerSearchProcess(this.getDbUser(request));
			String mfgId = process.getNextMfgId().toString();
			request.setAttribute("mfgId", mfgId);
			return (mapping.findForward("newmanufacturer"));
		}
		else if (userAction.equals("refresh")) {
			ManufacturerSearchProcess process = new ManufacturerSearchProcess(this.getDbUser(request));
			ManufacturerBean manufacturerBean = new ManufacturerBean();
			BeanHandler.copyAttributes(form, manufacturerBean);
			process.insertNewManufacturer(manufacturerBean);
			request.setAttribute("mfg", manufacturerBean);
			
			MsdsAuthorSearchProcess process2 = new MsdsAuthorSearchProcess(this.getDbUser(request));
			process2.insertNewMsdsAuthor(manufacturerBean);
			
			return (mapping.findForward("newmanufacturer"));
		}
		else if (userAction.equals("autoCompleteSearch")) {
			ManufacturerSearchProcess process = new ManufacturerSearchProcess(this.getDbUser(request));
			AutocompleteInputBean inputBean = new AutocompleteInputBean(request);
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			
			Collection<ManufacturerBean> results = process.getManufacturerBeanCollection(inputBean);
	        
	        // Write out the data
			response.setCharacterEncoding("utf-8");	  
	        PrintWriter out = response.getWriter();
	       
	    	for(ManufacturerBean bean: results)
	    		out.println(bean.getMfgId()+":"+bean.getMfgDesc());   		
			out.close();
			return noForward;	
		}

		return (mapping.findForward("success"));
	}

}
