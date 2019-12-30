package com.tcmis.client.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.report.process.ChartProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/**
 * ***************************************************************************
 * Controller for inventory reports
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CreateReceiptsChartAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			return (mapping.findForward("login"));
		}
		ChartProcess process = new ChartProcess(getDbUser(request));
		String inventoryGroup = request.getParameter("inventoryGroup");
		String catPartNo = request.getParameter("catPartNo");
		String partGroupNo = request.getParameter("partGroupNo");
		String inventoryGroupName = request.getParameter("inventoryGroupName");
		String catalogId = request.getParameter("catalogId");
		String catalogCompanyId = request.getParameter("catalogCompanyId");
		String fileName = process.generateReceiptChart(inventoryGroup, catPartNo, partGroupNo, inventoryGroupName, catalogId,catalogCompanyId);
		request.setAttribute("map", process.getMap());
		request.setAttribute("fileName", fileName);
		request.setAttribute("chartType", "Receipts for " + catPartNo + " in " + inventoryGroupName);
		if (log.isDebugEnabled()) {
			log.debug("FILENAME:" + fileName);
		}
		return (mapping.findForward("success"));
	}
}
