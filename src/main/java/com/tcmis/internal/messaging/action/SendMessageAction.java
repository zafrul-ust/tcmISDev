/******************************************************
 * EdiOrderSortAction.java
 *
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.internal.messaging.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.rtms.HaasMessaging;
import com.tcmis.rtms.instruction.BatchInstruction;

public class SendMessageAction extends TcmISBaseAction {


	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session beans (in)
		//PersonnelBean user = null;
		// parameters
		String instruction = null;
		String param1 = null;
		String param2 = null;
		String param3 = null;
		String param4 = null;
		String param5 = null;
		// session beans (in/out)

		log.debug("begin hub/SendMessageAction");
		String forward = "Success";
		HttpSession session = request.getSession();

		instruction = request.getParameter("instruction");
		param1 = request.getParameter("param1");
		param2 = request.getParameter("param2");
		param3 = request.getParameter("param3");
		param4 = request.getParameter("param4");
		param5 = request.getParameter("param5");

		if(log.isDebugEnabled()) {
			log.debug("creating new BatchInstruction("+instruction+ ", "+ param1+ "," +param2+ ","+param3+","+param4+","+param5+")");
		}
		BatchInstruction batchInstr= new BatchInstruction(instruction,param1,param2,param3,param4,param5);

		log.debug("sending Instruction");
		String result = HaasMessaging.sendMessage(batchInstr);

		if(log.isDebugEnabled()) {
			log.debug("Instruction sent: " + result);
		}
		//user = (PersonnelBean) session.getAttribute("personnelBean");

		return mapping.findForward(forward);
	}

}
