/******************************************************
 * MismatchAction.java
 *
 * Display a page comparing data sent and data received (via dbuy)
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


public class MismatchAction extends TcmISBaseAction {


	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// parameters
		String poNum = null;
		// session beans (in/out)
		Collection acknowledged = null;
		Collection sent = null;

		log.debug("begin MismatchAction");
		String forward = "success";
		HttpSession session = request.getSession();

		poNum = request.getParameter("po");

		if (poNum!=null) {
			String client = this.getDbUser(request);
			SupportProcess dbuyProcess = new SupportProcess(client,getTcmISLocaleString(request));
			try {
				if(log.isDebugEnabled()) {
					log.debug("finding po "+poNum);
				}
				acknowledged = dbuyProcess.getAcknowledgements(poNum);
				sent = dbuyProcess.getSent(poNum);
			} catch (BaseException be) {
				forward = "error";
			} finally {
				dbuyProcess = null;
			}

			session.setAttribute("AcknowledgeBeans",acknowledged);
			session.setAttribute("SentBeans",sent);
		} else {
			forward = "error";
		}

		return mapping.findForward(forward);
	}

}
