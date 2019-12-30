package com.tcmis.internal.catalog.action;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.MsdsIndexingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ManufacturerBean;
import com.tcmis.internal.catalog.beans.MsdsAuthorBean;
import com.tcmis.internal.catalog.beans.NewRevisionDateInputBean;
import com.tcmis.internal.catalog.process.MsdsAuthorSearchProcess;


/******************************************************************************
 * Controller for New Revision Date page
 * @version 1.0
 ******************************************************************************/

public class NewRevisionDateAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "newrevisiondate");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		Locale locale = getTcmISLocale();
		NewRevisionDateInputBean input = new NewRevisionDateInputBean(form,locale);
		String userAction = input.getuAction();
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");

		if (form == null || userAction == null) {
			return (mapping.findForward("success"));
		}
		else if(userAction.equals("newrevdate")){
			ManufacturerBean manufacturerBean = new ManufacturerBean();
			BeanHandler.copyAttributes(form, manufacturerBean);
			
			MsdsAuthorSearchProcess process = new MsdsAuthorSearchProcess(this.getDbUser(request));
			MsdsAuthorBean msdsAuthorBean = process.getMsdsAuthor(manufacturerBean.getMfgId());
			request.setAttribute("showDupLink",msdsAuthorBean != null ? true:false);
			request.setAttribute("dupMsdsAuthor", msdsAuthorBean);
			return (mapping.findForward("success"));
		}
		else if(userAction.equals("submit")){
			MsdsIndexingProcess catAddReqMsdsProcess = new MsdsIndexingProcess(personnelBean.getPersonnelIdBigDecimal(), getDbUser());

			Date newRevDate = catAddReqMsdsProcess.getNewRevisionDate(input.getMaterialId(), input.getRevDate(), request.getParameter("msdsAuthorId"));
			request.setAttribute("newRevDate", newRevDate);
			
			return (mapping.findForward("success"));
		}
		else if (userAction.equals("newmsdsauthor")) {
			MsdsAuthorSearchProcess process = new MsdsAuthorSearchProcess(this.getDbUser(request));
			String msdsAuthorId = process.getNextMsdsAuthorId().toString();
			request.setAttribute("msdsAuthorId", msdsAuthorId);
			return (mapping.findForward("newmsdsauthor"));
		}
		else if (userAction.equals("refresh")) {
			MsdsAuthorSearchProcess process = new MsdsAuthorSearchProcess(this.getDbUser(request));
			MsdsAuthorBean msdsAuthorBean = new MsdsAuthorBean();
			BeanHandler.copyAttributes(form, msdsAuthorBean);
			process.insertNewMsdsAuthor(msdsAuthorBean);
						
			request.setAttribute("msdsAuthor", msdsAuthorBean);

			if (msdsAuthorBean.isDirectCallSet()) {
				request.setAttribute("msdsAuthorId", msdsAuthorBean.getMsdsAuthorId());
				request.setAttribute("msdsAuthorDesc", msdsAuthorBean.getMsdsAuthorDesc());
				request.setAttribute("countryAbbrev", msdsAuthorBean.getCountryAbbrev());
				request.setAttribute("notes", msdsAuthorBean.getNotes());
				
				return (mapping.findForward("newmsdsauthor"));
			}
			
			if(input.getRevDate() != null){
				MsdsIndexingProcess catAddReqMsdsProcess = new MsdsIndexingProcess(personnelBean.getPersonnelIdBigDecimal(), getDbUser());
				Date newRevDate = catAddReqMsdsProcess.getNewRevisionDate(input.getMaterialId(), input.getRevDate(), msdsAuthorBean.getMsdsAuthorId().toString());
				request.setAttribute("newRevDate", newRevDate);
			}			
			else {
				request.setAttribute("revDate", input.getRevDate());
				request.setAttribute("mfgId", input.getMfgId());
				request.setAttribute("materialId", input.getMaterialId());
			}
			return (mapping.findForward("success"));
		}

		return (mapping.findForward("success"));
	}

}
