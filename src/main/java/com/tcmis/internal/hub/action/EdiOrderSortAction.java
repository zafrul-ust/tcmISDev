/******************************************************
 * EdiOrderSortAction.java
 *
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.process.EdiOrderStatusProcess;


public class EdiOrderSortAction extends TcmISBaseAction {


	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session beans (in)
		PersonnelBean user = null;
		// parameters
		String sortField = null;
		// session beans (in/out)
		Collection statusBeanCollection = null;

		log.debug("begin hub/EdiOrderSortsAction");
		ResourceLibrary hubLibrary = new ResourceLibrary("hub");
		String forward = "Success";
		HttpSession session = request.getSession();

		sortField = request.getParameter("sortfield");

		user = (PersonnelBean) session.getAttribute("personnelBean");
		statusBeanCollection = (Collection) session.getAttribute("errorviewBeanCollection");

		if (user==null || statusBeanCollection==null) {
			forward = "NoLogin";
			String defForward = hubLibrary.getString("edi.status.forward");
			request.setAttribute("requestedPage", defForward);
		} else {
			String client = this.getDbUser(request);     // user.getClient();
			EdiOrderStatusProcess orderstatusProcess = new EdiOrderStatusProcess(client);
			try {
				if (log.isDebugEnabled()) {
					log.debug("sorting order list, field="+sortField);
				}
				statusBeanCollection = orderstatusProcess.sortOrders(statusBeanCollection, sortField);
			} catch (BaseException be) {
				forward = "Error";
			} finally {
				orderstatusProcess = null;
			}
		}
		hubLibrary = null;

		session.setAttribute("errorviewBeanCollection",statusBeanCollection);

		return mapping.findForward(forward);
	}

}
