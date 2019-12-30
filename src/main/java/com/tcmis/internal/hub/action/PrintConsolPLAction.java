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
import com.tcmis.internal.hub.process.PrintConsolPLProcess;

public class PrintConsolPLAction extends TcmISBaseAction
{
	final Logger log = Logger.getLogger(PrintConsolPLAction.class
			.getName());	
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception
	{
		if (request.getParameter("shipmentId") == null)
		{
			return mapping.findForward("systemerrorpage");
		}
		else
		{
			String shipmentId =  request.getParameter("shipmentId").toString();
					
			PrintConsolPLProcess consolProcess = new PrintConsolPLProcess(
					this.getDbUser(request));
			
			consolProcess.setWarrantyDate(shipmentId);
			
			StringBuffer reportURL = RequestUtils.createServerStringBuffer(
					request.getScheme(), request.getServerName(),
					request.getServerPort());
			StringBuilder reportRequest = new StringBuilder(
					reportURL.toString());
			reportRequest.append(IHaasConstants.RPT_HAAS_REPORT_URI);
			reportRequest.append("?pr_shipment_id=");
			reportRequest.append(shipmentId);
			reportRequest.append(IHaasConstants.RPT_PARAM_BEAN_NAME);
			reportRequest.append(consolProcess.getTemplateName(shipmentId));
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