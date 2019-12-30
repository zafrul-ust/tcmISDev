package com.tcmis.client.peiprojects.action;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.peiprojects.beans.ProjectDocumentInputBean;
import com.tcmis.client.peiprojects.process.PeiProjectsProcess;
import com.tcmis.client.peiprojects.process.ProjectDocumentProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
//import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.exceptions.*;

public class ProjectDocumentAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "showaddprojectdocument");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		ProjectDocumentInputBean projectDocumentInputBean = null;
		try {
			projectDocumentInputBean = new ProjectDocumentInputBean();
			if (form != null) {
				BeanHandler.copyAttributes(form, projectDocumentInputBean,getTcmISLocale(request));
			}
		} catch (BeanCopyException ex1) {
			ex1.printStackTrace();
		}

		/*
		 * if ("TCM_OPS".equalsIgnoreCase(this.getDbUser(request))) {
		 * PeiProjectsProcess peiProjectsProcess = new PeiProjectsProcess(this.
		 * getDbUser(request));
		 * request.setAttribute("facilitiesForCompanyObjBeanCollection",
		 * peiProjectsProcess.getCompanyFacilityData()); }
		 */

		if (projectDocumentInputBean.getProjectId() == null) {
			return mapping.findForward("systemerrorpage");
		} 
		else if (form != null && projectDocumentInputBean.getSubmitSave() != null && projectDocumentInputBean.getSubmitSave().length() > 0) {
			
			if(projectDocumentInputBean.getTheFile() != null && !FileHandler.isValidUploadFile(projectDocumentInputBean.getTheFile()) ) {
				request.setAttribute("errorMessage", "File type not allowed.");
				return (mapping.findForward("success"));
			}
			
			PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
			projectDocumentInputBean.setEnteredBy(personnelId);

			String addNewDocumentUrl = "";
			ProjectDocumentProcess projectDocumentProcess = new ProjectDocumentProcess(this.getDbUser(request), getTcmISLocaleString(request));

			projectDocumentInputBean.setDocumentId(projectDocumentProcess.getNewDocumentId());
			try {
				addNewDocumentUrl = projectDocumentProcess.addNewDocument(projectDocumentInputBean);
			} catch (BaseException ex) {
				ex.printStackTrace();
				addNewDocumentUrl = "";
			}

			if (addNewDocumentUrl != null && addNewDocumentUrl.length() > 0) {
				request.setAttribute("showdocument", "Yes");
				request.setAttribute("newDocumentUrl", addNewDocumentUrl);
				request.setAttribute("documentName", projectDocumentInputBean.getDocumentName());
				request.setAttribute("documentId", projectDocumentInputBean.getDocumentId());
			} else {
				/*
				 * VvDataProcess vvDataProcess = new
				 * VvDataProcess(this.getDbUser(request));
				 * request.setAttribute("vvReceiptDocumentTypeBeanCollection",
				 * vvDataProcess.getVvReceiptDocumentType());
				 * request.setAttribute("companyIdsCollection",
				 * vvDataProcess.getCompanyIds());
				 */

				request.setAttribute("errorMessage", "Yes");
			}

			return (mapping.findForward("success"));
		}
		/*
		 * else if (projectDocumentInputBean.getShowDocuments() != null &&
		 * projectDocumentInputBean.getShowDocuments().length() > 0) {
		 * ProjectDocumentProcess projectDocumentProcess = new
		 * ProjectDocumentProcess(this. getDbUser(request));
		 * 
		 * request.setAttribute("receiptDocumentViewBeanCollection",
		 * projectDocumentProcess.getSearchResult(projectDocumentInputBean));
		 * return (mapping.findForward("showDocuments")); }
		 */
		else {
			/*
			 * VvDataProcess vvDataProcess = new
			 * VvDataProcess(this.getDbUser(request));
			 * request.setAttribute("vvReceiptDocumentTypeBeanCollection",
			 * vvDataProcess.getVvReceiptDocumentType());
			 * request.setAttribute("companyIdsCollection",
			 * vvDataProcess.getCompanyIds());
			 */

			return mapping.findForward("success");
		} // end of method
	}
} // end of class