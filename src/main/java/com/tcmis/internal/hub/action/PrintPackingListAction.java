package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.RequestUtils;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.web.IHaasConstants;
import com.tcmis.internal.hub.process.PackingListProcess;

public class PrintPackingListAction extends TcmISBaseAction
{
	final Logger log = Logger.getLogger(PrintPackingListAction.class
			.getName());	
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception
	{
		if (request.getParameter("shipmentIds") == null)
		{
			return mapping.findForward("systemerrorpage");
		}
		else
		{
			String[] shipmentId = request.getParameter("shipmentIds")
					.split(",");

			// call the process here
			PackingListProcess packingListProcess = new PackingListProcess(
					this.getDbUser(request));
			
			packingListProcess.setWarrantyDate(shipmentId);
			packingListProcess.getReportParametersData(shipmentId);
			StringBuffer reportURL = RequestUtils.createServerStringBuffer(
					request.getScheme(), request.getServerName(),
					request.getServerPort());
			StringBuilder reportRequest = new StringBuilder(
					reportURL.toString());
			reportRequest.append(IHaasConstants.RPT_HAAS_REPORT_URI);
			reportRequest.append("?pr_shipment_id=");
			reportRequest.append(request.getParameter("shipmentIds"));
			reportRequest.append(IHaasConstants.RPT_PARAM_BEAN_NAME);
			reportRequest.append(packingListProcess.getTemplateName());
			reportRequest.append("&pr_copies=");
			reportRequest.append(packingListProcess.getNumberOfCopies());
			reportRequest.append("&locale=");
			reportRequest.append(request.getLocale());
			//
			log.info("--> Redirect URL : " + reportRequest
					.toString());
			//
			response.sendRedirect(response.encodeRedirectURL(reportRequest
					.toString()));
			return null;
		}
	} // end of method
} // end of class