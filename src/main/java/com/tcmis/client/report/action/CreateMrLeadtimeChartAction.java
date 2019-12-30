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
public class CreateMrLeadtimeChartAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
//Trong says take it out!
//    if (!this.isLoggedIn(request)) {
//      return (mapping.findForward("login"));
//    }
		ChartProcess process = new ChartProcess(getDbUser(request));
		String inventoryGroup = request.getParameter("inventoryGroup");
		String catPartNo = request.getParameter("catPartNo");
		String partGroupNo = request.getParameter("partGroupNo");
		String inventoryGroupName = request.getParameter("inventoryGroupName");
		String catalogId = request.getParameter("catalogId");
		String issueGeneration = request.getParameter("issueGeneration");
		String catalogCompanyId = request.getParameter("catalogCompanyId");
		String fileName = process.generateMrLeadtimeChart(inventoryGroup, catPartNo, partGroupNo, inventoryGroupName, catalogId, issueGeneration,catalogCompanyId);
		request.setAttribute("map", process.getMap());
		request.setAttribute("fileName", fileName);
		request.setAttribute("chartType", "Lead time for " + catPartNo + " in " + inventoryGroupName);
		if (log.isDebugEnabled()) {
			log.debug("FILENAME:" + fileName);
		}
		return (mapping.findForward("success"));
	}
}