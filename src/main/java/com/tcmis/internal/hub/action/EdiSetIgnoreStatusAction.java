/******************************************************
 * SetIgnoreStatusAction.java
 *
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.hub.process.EdiOrderStatusProcess;


public class EdiSetIgnoreStatusAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// beans
		PersonnelBean user = null;
		// parameters
		String companyId;
		String poNum;
		String lineNum;
		String loadId;
		String loadLine;

		log.debug("begin hub/EdiSetIgnoreStatusAction");
		String forward = "Success";
		HttpSession session = request.getSession();

		// get session beans
		user = (PersonnelBean) session.getAttribute("personnelBean");

		if (user==null) {
			forward = "Error";
			return mapping.findForward(forward);
		}
		String client = this.getDbUser(request);

		EdiOrderStatusProcess orderstatusProcess = new EdiOrderStatusProcess(client);

		// get parameters
		companyId = request.getParameter("company_id");
		poNum = request.getParameter("customer_po_no");
		lineNum = request.getParameter("customer_po_line_no");
		loadLine = request.getParameter("load_line");
		loadId = request.getParameter("load_id");

		if (log.isDebugEnabled()) {
			log.debug("companyId: " + companyId);
			log.debug("poNum: " + poNum);
			log.debug("lineNum: " + lineNum);
			log.debug("loadId: " + loadId);
			log.debug("loadLine: " + loadLine);
		}
		user.getLogonId();
		try {
			orderstatusProcess.setToIgnoreStatus(companyId, poNum, lineNum, loadId, loadLine, user.getLogonId());
		} catch (BaseException be) {
			forward = "Error";
		}

		return mapping.findForward(forward);
	}

}
