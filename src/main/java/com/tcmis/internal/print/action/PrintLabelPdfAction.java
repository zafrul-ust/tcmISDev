package com.tcmis.internal.print.action;

import com.tcmis.client.ups.process.ShippingProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.web.IHaasConstants;
import com.tcmis.internal.hub.beans.DeliveryDocumentViewBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.print.beans.PrintLabelInputBean;
import com.tcmis.internal.print.process.HubLabelProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA. User: mathias.wennberg Date: Feb 7, 2008 Time:
 * 4:25:36 PM To change this template use File | Settings | File Templates.
 */
public class PrintLabelPdfAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception
	{
		HubLabelProcess labelProcess = new HubLabelProcess(
				this.getDbUser(request));
		String uAction  = request.getParameter("uAction");
		String packingGroupId = request.getParameter("packingGroupId");
		if( packingGroupId == null || packingGroupId.length() == 0 ) return mapping.findForward("success");

		StringBuilder reportRequest = new StringBuilder(
				"/HaasReports/report/printConfigurableReport.do");
		reportRequest
				.append("?pr_packingGroupId=");
		reportRequest
				.append(request.getParameter("packingGroupId"));
		reportRequest
				.append(IHaasConstants.RPT_PARAM_BEAN_NAME);
		reportRequest
				.append("dd1348ReportDefinition");
		reportRequest
				.append("&locale=en_US");
		response.sendRedirect(response
				.encodeRedirectURL(reportRequest
						.toString()));
		return noForward;
	}
}
