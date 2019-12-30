package com.tcmis.client.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.report.beans.UploadFileInputBean;
import com.tcmis.client.report.process.UploadCSVFileProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for upload file
 * @version 1.0
 ******************************************************************************/
public class UploadCSVFileAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "manualconsumptionimport");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		//if form is not null we have to perform some action
		if (form != null) {
			UploadFileInputBean uploadFileInputBean = new UploadFileInputBean();
			BeanHandler.copyAttributes(form, uploadFileInputBean, getTcmISLocale(request));
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
			
			if ("save".equals(uploadFileInputBean.getuAction())) {
				UploadCSVFileProcess process = new UploadCSVFileProcess(this.getDbUser(request));
				Object result[] = process.saveFile(null,//uploadFileInputBean, just to pass compilation, debug why this doesn't work on QA later.
						this.getPathCompany(request),
						request.getParameter("facilityId"),
						request.getParameter("application"),
						personnelBean.getPersonnelId()+""
						);
				request.setAttribute("done", "OK");
				request.setAttribute("tcmISError", result[0]);
				request.setAttribute("uploadId", result[1]);
			}
		}
		return mapping.findForward("success");
	}
}