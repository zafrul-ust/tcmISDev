package com.tcmis.internal.hub.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.process.UploadMinMaxLevelProcess;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;

public class UploadMinMaxLevelAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "uploadminmaxlevel");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		} 
		 
		//if form is not null we have to perform some action
		if (form != null) {
			ScannerUploadInputBean bean = new ScannerUploadInputBean();
		    BeanHandler.copyAttributes(form, bean);
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
			
			if(bean.getTheFile() != null)  {
				UploadMinMaxLevelProcess process = new UploadMinMaxLevelProcess(this.getDbUser(request));				
				ArrayList returnMessage = process.saveExcelFile(bean, personnelBean); //Using excel Handler (works for .xls only)
				if (returnMessage != null && returnMessage.size() > 0) {
					String key = (String)returnMessage.get(0);
					String message = (String)returnMessage.get(1);
					if (key.equalsIgnoreCase("OK")) {
						request.setAttribute("result", message);	
					} else {
						request.setAttribute("tcmISError", message);
					}				
				}
			}
		}
		return mapping.findForward("success");
	}
}
