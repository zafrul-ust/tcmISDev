/******************************************************
 * WeeklyConfirmedEbuysAction.java
 *
 * Display a page with all dbuy and wbuy orders confirmed in the last X weeks (2 default if nothing passed in)
 ******************************************************
 */
package com.tcmis.supplier.dbuy.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.dbuy.process.SupportProcess;


public class WeeklyConfirmedEbuysAction extends TcmISBaseAction {


	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session beans (in/out)
		Collection ebuys = null;

		log.debug("begin WeeklyConfirmedEbuysAction");
		String forward = "success";

		String weeks = null;
		weeks = request.getParameter("weeks");
		if(log.isDebugEnabled()) {
			log.debug("got field weeks: " + weeks);
		}

		HttpSession session = request.getSession();

		String client = "MNAJERA"; // this.getDbUser(request);
		SupportProcess process = new SupportProcess(client);
		try {
			log.debug("finding weekly confirmed ebuy orders ");
			ebuys = process.getWeeklyConfirmedEbuys(weeks);
		} catch (BaseException be) {
			forward = "error";
		} finally {
			process = null;
		}
		session.setAttribute("EbuyConfirmedBeans",ebuys);

		return mapping.findForward(forward);
	}

}
