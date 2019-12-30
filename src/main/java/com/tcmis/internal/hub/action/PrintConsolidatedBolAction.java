package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.web.IHaasConstants;

public class PrintConsolidatedBolAction extends TcmISBaseAction
{
	final Logger log = Logger.getLogger(PrintConsolidatedBolAction.class
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
			StringBuilder reportRequest = new StringBuilder(
					"/HaasReports/report/printConfigurableReport.do");
			reportRequest.append("?pr_shipment_id=");
			reportRequest.append(request.getParameter("shipmentIds"));
			reportRequest.append(IHaasConstants.RPT_PARAM_BEAN_NAME);
			reportRequest.append("consolidatedBOLReportDefinition");
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