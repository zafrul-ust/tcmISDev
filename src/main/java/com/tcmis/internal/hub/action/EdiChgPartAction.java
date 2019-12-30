/******************************************************
 * EdiChgPartAction.java
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


public class EdiChgPartAction extends TcmISBaseAction {


	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session beans (in)
		PersonnelBean user = null;
		// parameters
		String partNum = null;
		String rowNum = null;
		String quantity = null;
		String company = null;
		String facility = null;

		// session beans (in/out)
		Collection partBeanCollection = null;

		log.debug("begin hub/EdiOrderSortsAction");
		ResourceLibrary hubLibrary = new ResourceLibrary("hub");
		String forward = "Success";
		HttpSession session = request.getSession();

		partNum = request.getParameter("partnum");
		rowNum = request.getParameter("rownum");
		quantity = request.getParameter("qty");
		company = request.getParameter("company");
		facility = request.getParameter("fac");

		user = (PersonnelBean) session.getAttribute("personnelBean");
		partBeanCollection = (Collection) session.getAttribute("errorviewBeanCollection");

		if (user==null || partBeanCollection==null) {
			forward = "NoLogin";
			String defForward = hubLibrary.getString("edi.status.forward");
			request.setAttribute("requestedPage", defForward);
		} else {
			String client = this.getDbUser(request);     // user.getClient();
			EdiOrderStatusProcess orderstatusProcess = new EdiOrderStatusProcess(client);
			try {
				if (log.isDebugEnabled()) {
					log.debug("getting part list, part=" + partNum + ", row="+rowNum );
				}
				partBeanCollection = orderstatusProcess.getPartList(quantity,company,facility,partNum);
			} catch (BaseException be) {
				forward = "Error";
			} finally {
				orderstatusProcess = null;
			}
		}
		hubLibrary = null;

		session.setAttribute("partBeanCollection",partBeanCollection);
		session.setAttribute("RowNum",rowNum);

		return mapping.findForward(forward);
	}

}
