package com.tcmis.internal.catalog.action;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.MaterialBean;
import com.tcmis.internal.catalog.process.MaterialSearchProcess;

/******************************************************************************
 * Controller for Material Search page
 * @version 1.0
 ******************************************************************************/

public class MaterialSearchAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "materialsearchmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		String userAction = request.getParameter("userAction");

		if (form == null || userAction == null) {
			return (mapping.findForward("success"));
		}

		if (userAction != null && userAction.equals("search")) {
			String searchArgument = request.getParameter("searchArgument");
			MaterialSearchProcess process = new MaterialSearchProcess(this.getDbUser(request));
			String mfgId = request.getParameter("mfgId");
			String excludeMaterialIds = request.getParameter("excludeMaterialIds");
			Collection materialBeanCollection = process.getMaterialBeanCollection(mfgId, searchArgument,excludeMaterialIds);

			request.setAttribute("materialBeanCollection", materialBeanCollection);
		}
		else if (userAction != null && userAction.equals("new")) {
			MaterialSearchProcess process = new MaterialSearchProcess(this.getDbUser(request));
			String mfgId = request.getParameter("mfgId");
			String materialId = process.getNextMaterialId().toString();
			request.setAttribute("materialId", materialId);
			return (mapping.findForward("newmaterial"));
		}
		else if (userAction != null && userAction.equals("refresh")) {
			MaterialSearchProcess process = new MaterialSearchProcess(this.getDbUser(request));
			MaterialBean materialBean = new MaterialBean();
			BeanHandler.copyAttributes(form, materialBean);
			process.insertNewMaterial(materialBean);
			request.setAttribute("material", materialBean);
			return (mapping.findForward("newmaterial"));
		}
		else if (userAction.equals("autoCompleteSearch")) {
			MaterialSearchProcess process = new MaterialSearchProcess(this.getDbUser(request));
			AutocompleteInputBean inputBean = new AutocompleteInputBean(request);
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			
			Collection<MaterialBean> results = process.getMaterialBeanCollection(inputBean);
	
			// Write out the data
			response.setCharacterEncoding("utf-8");	  
	        PrintWriter out = response.getWriter();
	       
	    	for(MaterialBean bean: results)
	    		out.println(bean.getMfgId()+":"+bean.getMaterialId()+":"+bean.getMaterialDesc());   		
			out.close();
			return noForward;	
		}

		return (mapping.findForward("success"));
	}

}
