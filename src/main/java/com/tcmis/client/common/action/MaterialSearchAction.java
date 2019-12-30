package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogFacilityBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.common.process.MaterialSearchProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

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
			CatalogProcess cProcess = new CatalogProcess(this.getDbUser(request));
			CatalogFacilityBean inputBean = new CatalogFacilityBean();
			inputBean.setCompanyId(request.getParameter("companyId"));
			inputBean.setCatalogCompanyId(request.getParameter("catalogCompanyId"));
			inputBean.setFacilityId(request.getParameter("facilityId"));
			inputBean.setCatalogId(request.getParameter("catalogId"));
			request.setAttribute("catalogCollection", cProcess.getCatalogFacility(inputBean));
			return (mapping.findForward("success"));
		}
		
		MaterialSearchProcess process = new MaterialSearchProcess(this.getDbUser(request));
		String companyId = request.getParameter("companyId");
		String facilityId = request.getParameter("facilityId");
		String catalogIdCatalogCompanyIdString = request.getParameter("catalogIdCatalogCompanyIdString");
		String[] ids = catalogIdCatalogCompanyIdString.split(",");
		String searchField = request.getParameter("searchField");
		String searchMode = request.getParameter("searchMode");
		String searchArgument = request.getParameter("searchArgument");
		String application = request.getParameter("application");
		
		if (userAction != null && userAction.equals("search")) {
			Collection materialBeanCollection = process.getMaterialBeanCollection(companyId, facilityId, ids[0], ids[1], searchField, searchMode, searchArgument, application);
			
			request.setAttribute("materialBeanCollection", materialBeanCollection);
		}
/*		else if (userAction != null && userAction.equals("new")) {
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
		} */
		else if (userAction != null && userAction.equals("createExcel")) {
			
			this.setExcel(response, "DisplayExcel");
			process.getExcelReport(process.getMaterialBeanCollection(companyId, facilityId, ids[0], ids[1], searchField, searchMode, searchArgument, application), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	        return noForward;

		}
		
		return (mapping.findForward("success"));
	}

}
