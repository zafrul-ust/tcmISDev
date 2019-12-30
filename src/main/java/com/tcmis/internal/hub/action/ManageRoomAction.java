package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.BinLabelsInputBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.OverrideApprovalBean;
import com.tcmis.internal.hub.beans.VvHubBinsBean;
import com.tcmis.internal.hub.beans.VvHubRoomBean;
import com.tcmis.internal.hub.process.BinLabelsProcess;
import com.tcmis.internal.hub.process.BinsToScanProcess;
import com.tcmis.internal.supply.beans.PoLineExpediteDateBean;

/******************************************************************************
 * Controller for viewing and generating bin labels
 * 
 * @version 1.0
 ******************************************************************************/


/**
 * Change History
 * --------------
 * 03/05/09 - Shahzad Butt - Added Update functionality, commented the unnecessary code 
 * 							 from update. Permission needs to be added for update.
 *
 */

public class ManageRoomAction extends TcmISBaseAction {


	@SuppressWarnings("unchecked")
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws BaseException, Exception {

		if (!this.isLoggedIn(request,true)) {
			request.setAttribute("requestedPage", "binlabelsmain");
			request.setAttribute("requestedURLWithParameters", this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		String uAction = request.getParameter("uAction");
		VvHubRoomBean input = new VvHubRoomBean();		
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));
		GenericProcess process = new GenericProcess(this.getDbUser(request));
		if ("update".equals(uAction) ) {
			process.deleteInsertUpdate("update vv_hub_room set route_order = {0} where hub = {1} and room = {2}",
										input.getRouteOrder(), 
										input.getHub(),
										input.getRoom()
										);
		}
		else { 
			request.setAttribute("roomBean", process.getSearchResult("select * from vv_hub_room where hub = {0} and room = {1}", new VvHubRoomBean(),
					input.getHub(),
					input.getRoom()
					));
		}
		return mapping.findForward("success");
	}
} // end of action class