/******************************************************
 * EBPCompleteAction.java
 * 
 * Wait on the EBP shopping to be completed (by looking
 * for the completed records), and when done perform the
 * punchout.
 ******************************************************
 */
package com.tcmis.client.fec.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.fec.process.EBPShoppingProcess;
import com.tcmis.common.framework.TcmISBaseAction;

public class EBPCompleteAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String hookUrl = "";
		String forward = "notcomplete";
		String errMsg = null;
		boolean completed=false;
		BigDecimal prNum = null;
		Collection cart;

		HttpSession session = request.getSession();

		hookUrl = (String) session.getAttribute("HookUrlBean");

		String client = this.getDbUser(request);;
		EBPShoppingProcess shoppingProcess = new EBPShoppingProcess(client);

		if (log.isDebugEnabled()) {
			log.debug("looking for completed order for hookurl: " + hookUrl);
		}
		prNum = shoppingProcess.orderCompleted(hookUrl);
		if (log.isDebugEnabled()) {
			log.debug("completed? PR Num = " + prNum);
		}
		if (prNum != null) {
			cart = shoppingProcess.getShopppingCart(hookUrl, prNum);
			session.setAttribute("ShoppingCartBean",cart);
			shoppingProcess.updatePunchout(hookUrl,prNum);
			if (log.isDebugEnabled()) {
				log.debug("Done Shopping. set ShoppingCartBean to: " + cart );
			}
			forward="success";
		} else {
			log.debug("sleeping for a bit ...");
			for (int i=1; i<32000; i++) ;
		}

		session.setAttribute("HookUrlBean",hookUrl);
		log.debug("end of action:: set hook URL bean: " + hookUrl);

		return mapping.findForward(forward);
	}

}
