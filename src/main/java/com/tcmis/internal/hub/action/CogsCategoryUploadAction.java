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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.internal.distribution.process.PLUploadProcess;
import com.tcmis.internal.hub.process.CogsCategoryUploadProcess;

public class CogsCategoryUploadAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	    if (!this.isLoggedIn(request)) {
	      request.setAttribute("requestedPage", "cogscategoryupload");
	      request.setAttribute("requestedURLWithParameters",
	                           this.getRequestedURLWithParameters(request));
	      return (mapping.findForward("login"));
	    }

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
	      
		FileUploadBean bean = new FileUploadBean();
	    BeanHandler.copyAttributes(form, bean);
	    //check if a file was uploaded
	    if (bean.getTheFile() != null && !bean.getTheFile().getName().endsWith("csv")) {
	    	request.setAttribute("errorMessage", "Invalid file.");
	    } else if (bean.getTheFile() != null) {
	    	CogsCategoryUploadProcess process = new CogsCategoryUploadProcess(this);
			ArrayList returnMessage = process.uploadFile(bean, personnelBean); //Using excel Handler (works for .xls only)
			if (returnMessage != null && returnMessage.size() > 0) {
				String key = (String)returnMessage.get(0);
				String message = (String)returnMessage.get(1);
				if (key.equalsIgnoreCase("OK")) {
					request.setAttribute("result", message);	
				} else {
					request.setAttribute("errorMessage", message);
				}				
			}						
		}
	    return mapping.findForward("success");
	  }
}
