package com.tcmis.internal.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.CatalogVendorAssignmentBean;
import com.tcmis.internal.catalog.process.CatalogSupplierProcess;

/******************************************************************************
 * Controller for logistics
 * @version 1.0
 ******************************************************************************/
public class CatalogSupplierAction extends TcmISBaseAction { 

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.checkLoggedIn("catalogsupplierpricelistmain"))
			return mapping.findForward("login");
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		PermissionBean permmissionBean = personnelBean.getPermissionBean();
	    if ( !permmissionBean.hasUserPagePermission("catalogSupplierManagement") )
		    {
		      return (mapping.findForward("nopermissions"));
		    }

		//populate drop downs
	    CatalogVendorAssignmentBean inputBean = new CatalogVendorAssignmentBean();
		CatalogSupplierProcess process = new CatalogSupplierProcess(this);

		if (form == null) {
			request.setAttribute("catalogVendor", process.getCatalogVendor());
			request.setAttribute("queueItem", process.getQueueItem(inputBean));
			return (mapping.findForward("success"));
		}
		
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));

		String action = (String) ((DynaBean) form).get("uAction"); //
		if ("search".equals(action)) {
			request.setAttribute("beanCollection", process.getCatalogVendorAssignment(inputBean));
		}
		/*if ("newAssignment".equals(action)) {
			java.util.Enumeration<String> e = request.getParameterNames();

			StringBuilder errorMsg = new StringBuilder();
			String error = "";
			Object[] objs = process.getNewCatalogVendorAssignment(inputBean,personnelBean);
			request.setAttribute("newCatalogVendorAssignmen", objs[0]);
			request.setAttribute("beanCollection", objs[1]);
			request.setAttribute("tcmISError", errorMsg.toString());
		}*/
		if ("update".equals(action)) {
			java.util.Enumeration<String> e = request.getParameterNames();

			StringBuilder errorMsg = new StringBuilder();
			String error = "";
			Collection<CatalogVendorAssignmentBean> beans = BeanHandler.getGridBeans((DynaBean) form, "CatalogVendorAssignmentBean", new CatalogVendorAssignmentBean(), this.getTcmISLocale(),"ok");
			request.setAttribute("beanCollection", process.updateCatalogVendorAssignment(inputBean,beans,personnelBean));
			request.setAttribute("tcmISError", errorMsg.toString());
		}
		else if (action.equals("createXSL")) {
			this.setExcel(response, "CatalogSupplierManagement");
			try {
				process.getExcelReport(inputBean, personnelBean).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		else if ("loaddata".equals(action)) {
			return (mapping.findForward("loaddata"));
		}

		return (mapping.findForward("success"));
	}
}
