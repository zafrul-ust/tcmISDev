package com.tcmis.client.peiprojects.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;
import com.tcmis.client.peiprojects.beans.PeiProjectDocumentBean;
import com.tcmis.client.peiprojects.beans.PeiProjectInputBean;
import com.tcmis.client.peiprojects.beans.PeiProjectSavingsBean;
import com.tcmis.client.peiprojects.process.PeiProjectsProcess;
import com.tcmis.client.peiprojects.process.ProjectDocumentProcess;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.peiprojects.beans.PeiProjectViewBean;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class PeiProjectsAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showpeiproject");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
}

	if (form == null) {
	 this.saveTcmISToken(request);
	}

	VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
	Collection vvKeywordCollection = vvDataProcess.getVvPeiProjectKeyword();
	request.setAttribute("vvPeiProjectKeywordCollection",vvKeywordCollection);
    
	Collection vvStatusCollection = vvDataProcess.getVvPeiProjectStatus();
	request.setAttribute("vvPeiProjectStatusCollection",vvStatusCollection);
	
	Collection vvCategoryCollection = vvDataProcess.getVvProjectCategory();
	request.setAttribute("vvProjectCategoryCollection",vvCategoryCollection);
	
	Collection vvPriorityCollection = vvDataProcess.getVvProjectPriority();
	request.setAttribute("vvProjectPriorityCollection",vvPriorityCollection);
	/*request.setAttribute("vvProjectManagerCollection",
	 vvDataProcess.getUsersByGroup(null, "ProjectMgmt"));*/

	PeiProjectsProcess peiProjectsProcess = new PeiProjectsProcess(this.getDbUser(request),getTcmISLocaleString(request));
	if ("TCM_OPS".equalsIgnoreCase(this.getDbUser(request)))
	{
	 request.setAttribute("facilitiesForCompanyObjBeanCollection",
		peiProjectsProcess.getCompanyFacilityData());
	}
	else
	{
	 request.setAttribute("vvProjectFacilityCollection",
		peiProjectsProcess.getFacilityBeanCollection());
	}
	String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
	request.setAttribute("linkModule",module);

	if (form != null) {
	 if (
		( ( (DynaBean) form).get("submitUpdate") != null &&
		( (String) ( (DynaBean) form).get("submitUpdate")).length() > 0)) {
    if (!this.isTcmISTokenValid(request, true))
    {
    BaseException be = new BaseException("Duplicate form submission");
		be.setMessageKey("error.submit.duplicate");
		this.saveTcmISToken(request);
		throw be;
    }
   }
	 this.saveTcmISToken(request);

	 PeiProjectInputBean peiProjectInputBean = new PeiProjectInputBean();
	 BeanHandler.copyAttributes(form, peiProjectInputBean, getTcmISLocale(request));

	 /*log.debug("SubmitNew " + peiProjectInputBean.getSubmitNew() + " SubmitSaveAsNew " + peiProjectInputBean.getSubmitSaveAsNew() + "  SubmitUpdate  "+peiProjectInputBean.getSubmitUpdate()+"");
	 DynaBean dynaForm1 = (DynaBean) form;
	 String submitSaveAsNew = (String) dynaForm1.get("submitSaveAsNew");
	 log.debug("submitSaveAsNew "+submitSaveAsNew+"");*/

	 if (peiProjectInputBean.getSubmitNew() != null &&
		peiProjectInputBean.getSubmitNew().trim().length() > 0) {
		Collection peiProjectViewBeanCollection = peiProjectsProcess.getNewProjectCollection();

		this.setSessionObject(request, peiProjectViewBeanCollection,
		 "peiProjectViewBeanCollection");
		request.setAttribute("peiProjectViewBeanCollection",
		 peiProjectViewBeanCollection);
		request.setAttribute("insertOrUpdate","Insert");

		return mapping.findForward("showresults");
	 }
	 else if ( (peiProjectInputBean.getSubmitUpdate() != null &&
		 peiProjectInputBean.getSubmitUpdate().trim().length() > 0) ||
		 (peiProjectInputBean.getSubmitSaveAsNew() != null &&
		 peiProjectInputBean.getSubmitSaveAsNew().trim().length() > 0)) {

		Collection peiProjectViewBeanCollection = (Collection)this.getSessionObject(
		 request, "peiProjectViewBeanCollection");

		if (peiProjectInputBean.getCompanyId() == null ||
		 peiProjectInputBean.getCompanyId().length() == 0) {
		 if ("TCM_OPS".equalsIgnoreCase(this.getDbUser(request))) {
			peiProjectInputBean.setCompanyId("Radian");
		 }
		 else {
			peiProjectInputBean.setCompanyId(this.getDbUser(request));
		 }
		}

		if (peiProjectInputBean.getSubmitSaveAsNew() != null &&
		 peiProjectInputBean.getSubmitSaveAsNew().trim().length() > 0) {
		 BigDecimal newProjectId = peiProjectsProcess.getNewProjectId();
		 ProjectDocumentProcess projectDocumentProcess = new ProjectDocumentProcess(this.getDbUser(request),getTcmISLocaleString(request));
		 projectDocumentProcess.copyDocuments(peiProjectInputBean.getProjectId(),newProjectId);

		 peiProjectInputBean.setProjectId(newProjectId);
		 peiProjectInputBean.setInsertOrUpdate("Insert");
		}

		// Cast form to DynaBean
		DynaBean dynaForm = (DynaBean) form;
		List peiProjectSavingsBeans = (List) dynaForm.get("peiProjectSavingsBean");
		Collection peiProjectSavingsBeanCollection = new Vector();
		if (peiProjectSavingsBeans !=null)
		{
		 Iterator savingsIterator = peiProjectSavingsBeans.iterator();
		 //int count = 0;
		 //log.debug("Size " + peiProjectSavingsBeans.size() + "");

		 while (savingsIterator.hasNext()) {
			org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
			 commons.beanutils.LazyDynaBean) savingsIterator.next();

			PeiProjectSavingsBean peiProjectSavingsBean = new PeiProjectSavingsBean();
			BeanHandler.copyAttributes(lazyBean, peiProjectSavingsBean, getTcmISLocale(request));
			peiProjectSavingsBean.setCurrencyId(peiProjectInputBean.getCurrencyId());
			peiProjectSavingsBean.setProjectId(peiProjectInputBean.getProjectId());
			peiProjectSavingsBean.setCompanyId(peiProjectInputBean.getCompanyId());
			//log.debug("Period Id  "+peiProjectSavingsBean.getPeriodId()+"");
			peiProjectSavingsBeanCollection.add(peiProjectSavingsBean);
		 }
		}
		peiProjectInputBean.setSavingsCollection(peiProjectSavingsBeanCollection);

		List peiProjectDocumentBeans = (List) dynaForm.get("peiProjectDocumentBean");
		Collection peiProjectDocumentBeanCollection = new Vector();

		if (peiProjectDocumentBeans != null)
		{
		 Iterator documentIterator = peiProjectDocumentBeans.iterator();
		 //int count = 0;
		 //log.debug("Size " + peiProjectDocumentBeans.size() + "");

		 while (documentIterator.hasNext()) {
			org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
			 commons.beanutils.LazyDynaBean) documentIterator.next();

			PeiProjectDocumentBean peiProjectDocumentBean = new PeiProjectDocumentBean();
			BeanHandler.copyAttributes(lazyBean, peiProjectDocumentBean, getTcmISLocale(request));
			peiProjectDocumentBean.setProjectId(peiProjectInputBean.getProjectId());
			//log.debug("Document Id  "+peiProjectDocumentBean.getDocumentId()+"  "+peiProjectDocumentBean.getDelete()+"");
			peiProjectDocumentBeanCollection.add(peiProjectDocumentBean);
		 }
		}
		peiProjectInputBean.setDocumentsCollection(peiProjectDocumentBeanCollection);

		peiProjectViewBeanCollection = peiProjectsProcess.copyAttributes(peiProjectInputBean,peiProjectViewBeanCollection);
    //update
		String errorMessage = peiProjectsProcess.updateProject(peiProjectViewBeanCollection);
		request.setAttribute("errorMessage",errorMessage);

		peiProjectViewBeanCollection = peiProjectsProcess.
		 getsearchResult(peiProjectInputBean);

		this.setSessionObject(request, peiProjectViewBeanCollection,
		 "peiProjectViewBeanCollection");
		request.setAttribute("peiProjectViewBeanCollection",
		 peiProjectsProcess.getsearchResult(peiProjectInputBean));

		return mapping.findForward("showresults");
	 }
	 else if (peiProjectInputBean.getSubmitPrint() != null &&
		peiProjectInputBean.getSubmitPrint().trim().length() > 0) {

		String pdfUrl = peiProjectsProcess.buildProjectPdf(peiProjectInputBean,vvKeywordCollection,vvStatusCollection,vvCategoryCollection,vvPriorityCollection);

		if (pdfUrl.length() > 0)
		{
		 //this.setSessionObject(request, pdfUrl, "filePath");
     request.setAttribute("filePath", pdfUrl);      
     return mapping.findForward("viewfile");
		}
		else
		{
		 return mapping.findForward("systemerrorpage");
		}
	 }
	 else if (peiProjectInputBean.getProjectId() != null &&
		peiProjectInputBean.getProjectId().intValue() > 0) {

		Collection peiProjectViewBeanCollection = peiProjectsProcess.getsearchResult(peiProjectInputBean);

		this.setSessionObject(request, peiProjectViewBeanCollection, "peiProjectViewBeanCollection");
		request.setAttribute("peiProjectViewBeanCollection", peiProjectViewBeanCollection);

		boolean updateProjectPermissions = false;
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		updateProjectPermissions = personnelBean.getPermissionBean().hasFacilityPermission ("ProjectMgmt", null,null);
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		Iterator iterator = peiProjectViewBeanCollection.iterator();
		while (iterator.hasNext()) {
		 PeiProjectViewBean peiProjectViewBean = (PeiProjectViewBean) iterator.next();
		 if (peiProjectViewBean.getProjectId() == personnelId) {
			updateProjectPermissions = true;
		 }
		}

		if (updateProjectPermissions)
		{
		 return mapping.findForward("showresults");
		}
		else
		{
		 return mapping.findForward("showreadonly");
		}
	 }
	 return mapping.findForward("systemerrorpage");
	}
	else {
	 this.setSessionObject(request, null, "receivingViewBeanCollection");
	 return mapping.findForward("systemerrorpage");
	}
 }
}