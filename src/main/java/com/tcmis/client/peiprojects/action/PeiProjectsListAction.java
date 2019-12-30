package com.tcmis.client.peiprojects.action;

import java.io.File;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.peiprojects.beans.PeiProjectInputBean;
import com.tcmis.client.peiprojects.beans.VvPeiProjectKeywordBean;
import com.tcmis.client.peiprojects.beans.VvPeiProjectStatusBean;
import com.tcmis.client.peiprojects.beans.VvProjectCategoryBean;
import com.tcmis.client.peiprojects.process.PeiProjectsProcess;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import org.apache.struts.util.ModuleUtils;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class PeiProjectsListAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "peiprojectlistmain");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	if (form == null) 
		return mapping.findForward("success");

	PeiProjectInputBean peiProjectInputBean = new PeiProjectInputBean();
	PeiProjectsProcess peiProjectsProcess = new PeiProjectsProcess(this.getDbUser(request),getTcmISLocaleString(request));

	BeanHandler.copyAttributes(form, peiProjectInputBean, getTcmISLocale(request));

	if (peiProjectInputBean.getSubmitSearch() != null &&
			peiProjectInputBean.getSubmitSearch().trim().length() > 0) {
		 
		this.saveTcmISToken(request);
		request.setAttribute("peiProjectViewListBeanCollection",
		peiProjectsProcess.getsearchResult(peiProjectInputBean));
		request.setAttribute("peiProjectInputBean",peiProjectInputBean);
		return mapping.findForward("showresults");
	 }

	 
	VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
		
	Collection vvPriorityCollection = vvDataProcess.getVvProjectPriority();
	request.setAttribute("vvProjectPriorityCollection",vvPriorityCollection);

	Collection peiProjectKeywordCollection = new Vector();
	/*Including the option of all*/
	VvPeiProjectKeywordBean vvPeiProjectKeywordBean = new VvPeiProjectKeywordBean();
	vvPeiProjectKeywordBean.setKeywordId("All");
	vvPeiProjectKeywordBean.setKeywordDesc("All");
	peiProjectKeywordCollection.add(vvPeiProjectKeywordBean);
	
	peiProjectKeywordCollection.addAll(vvDataProcess.getVvPeiProjectKeyword());
	peiProjectInputBean.setKeywordsCollection(peiProjectKeywordCollection);
	request.setAttribute("peiProjectKeywordCollection",peiProjectKeywordCollection);


	Collection peiProjectCategoryCollection = new Vector();
			/*Including the option of all*/
	VvProjectCategoryBean vvProjectCategoryBean = new VvProjectCategoryBean();
	vvProjectCategoryBean.setProjectCategory("All");
	vvProjectCategoryBean.setProjectCategoryDesc("All");
	peiProjectCategoryCollection.add(vvProjectCategoryBean);

	peiProjectCategoryCollection.addAll(vvDataProcess.getVvProjectCategory());
	peiProjectInputBean.setCategoryCollection(peiProjectCategoryCollection);
	request.setAttribute("vvProjectCategoryCollection",peiProjectCategoryCollection);
	
	Collection peiProjectStatusCollection = new Vector();
			/*Including the option of all*/
	VvPeiProjectStatusBean vvPeiProjectStatusBean = new VvPeiProjectStatusBean();
	vvPeiProjectStatusBean.setProjectStatus("All");
	vvPeiProjectStatusBean.setProjectStatusDesc("All");
	peiProjectStatusCollection.add(vvPeiProjectStatusBean);

	peiProjectStatusCollection.addAll(vvDataProcess.getVvPeiProjectListStatus());
	peiProjectInputBean.setStatusCollection(peiProjectStatusCollection);
	request.setAttribute("vvPeiProjectStatusCollection",peiProjectStatusCollection);
	request.setAttribute("peiProjectInputBean",peiProjectInputBean);

	String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
	request.setAttribute("linkModule",module);

	if ("TCM_OPS".equalsIgnoreCase(this.getDbUser(request))) {
			 request.setAttribute("facilitiesForCompanyObjBeanCollection",
			 peiProjectsProcess.getCompanyFacilityData());
	}
	else {
			 request.setAttribute("vvProjectFacilityCollection",
			 peiProjectsProcess.getFacilityBeanCollection());
	}

	request.setAttribute("peiProjectViewListBeanCollection",null);
	request.setAttribute("peiProjectInputBean",peiProjectInputBean);
	
	if (peiProjectInputBean.getUserAction() != null &&
			 peiProjectInputBean.getUserAction().trim().equals("buttonCreateExcel") ) {
//		peiProjectInputBean.setSubmitCreateReport("buttonCreateExcel");
		Collection peiProjectViewListBeanCollection = peiProjectsProcess.getsearchResult(peiProjectInputBean);
		this.setExcel(response, "ProjectList");
		peiProjectsProcess.writeExcelFile(peiProjectViewListBeanCollection,
				(Collection)request.getAttribute("peiProjectKeywordCollection"),
				(Collection)request.getAttribute("vvPeiProjectStatusCollection"),
				(Collection)request.getAttribute("vvProjectCategoryCollection"),
				(Collection)request.getAttribute("vvProjectPriorityCollection"),
				(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        return noForward;

	 }
	 else if (	peiProjectInputBean.getUserAction() != null &&
				peiProjectInputBean.getUserAction().trim().equals("submitPrint") &&
				peiProjectInputBean.getPrintProjectIdList() != null &&
				peiProjectInputBean.getPrintProjectIdList().length() > 0) {
		 
		 peiProjectInputBean.setSubmitPrint("submitPrint");

		 String pdfUrl = peiProjectsProcess.buildProjectPdf(peiProjectInputBean,
				(Collection)request.getAttribute("peiProjectKeywordCollection"),
				(Collection)request.getAttribute("vvPeiProjectStatusCollection"),
				(Collection)request.getAttribute("vvProjectCategoryCollection"),
				(Collection)request.getAttribute("vvProjectPriorityCollection"));

		if (pdfUrl.length() > 0) {
		 //this.setSessionObject(request, pdfUrl, "filePath");
     request.setAttribute("filePath", pdfUrl);
     return mapping.findForward("viewpsfile");
		}
		else
		{
		 return mapping.findForward("systemerrorpage");
		}
	 }
//	 search
	return mapping.findForward("success");
 	}
}