package com.tcmis.internal.hub.action;

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
import com.tcmis.internal.hub.beans.BatchProductionInputBean;
import com.tcmis.internal.hub.beans.HubInventoryGroupVesselOvBean;
import com.tcmis.internal.hub.process.BatchProductionProcess;
import com.tcmis.internal.hub.process.HubInventoryGroupVesselOvProcess;

/******************************************************************************
 * Controller for Production Batch
 * @version 1.0
	******************************************************************************/

public class BatchProductionAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "batchproductionmain");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	PermissionBean permissionBean = personnelBean.getPermissionBean();
	/*if (! permissionBean.hasFacilityPermission("BatchProduction", null, null))
	{
	 return mapping.findForward("nopermissions");
	}*/

	if (form != null) {

	 BatchProductionProcess process = new BatchProductionProcess(this.getDbUser(request));
	 BatchProductionInputBean inputBean = new BatchProductionInputBean();
	 BeanHandler.copyAttributes(form, inputBean);

	 if ( ( (DynaBean) form).get("submitSearch") != null &&
	 ( (String) ( (DynaBean) form).get("submitSearch")).length() > 0) {
		Collection productionBatchViewBeanCollection = process.getProductionBatchViewBeanCollection(inputBean);
		//Pass the result collection in request
		request.setAttribute("productionBatchViewBeanCollection",productionBatchViewBeanCollection);
		this.saveTcmISToken(request);
	 }
	 else if ( ( (DynaBean) form).get("action") != null &&
		((String) ( (DynaBean) form).get("action")).equalsIgnoreCase("createExcel")) {
		try {
			this.setExcel(response,"BatchProduction");
			process.getExcelReport(inputBean,personnelBean.getLocale()).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
	 }
	 else if (mapping.getPath().equals("/batchproductionclosebatch")) {
		String batchId = request.getParameter("batchIdToClose");
		process.closeBatch(batchId);
		return (mapping.findForward("success"));
	 }
	 else { //populate drop downs
		HubInventoryGroupVesselOvProcess hproc = new
		 HubInventoryGroupVesselOvProcess(this.getDbUser(request));
		HubInventoryGroupVesselOvBean hbean = new HubInventoryGroupVesselOvBean();
		hbean.setPersonnelId(new BigDecimal(personnelBean.getPersonnelId()));
		Collection v = hproc.getSearchResult(hbean);
		request.setAttribute("hubBean", v);
	 }
	}
	return (mapping.findForward("success"));
 }
}