/******************************************************
 * ResetStatusction.java
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
import com.tcmis.internal.hub.process.EdiOrderStatusProcess;


public class EdiResetStatusAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// beans
		PersonnelBean user = null;
		Collection statusBeanCollection = null;
		// parameters
		String rowId;
		String companyId;
		String poNum;
		String lineNum;
		String txnType;
		String lineSeq;
		String catPartNo;
		String chgOrder;
		String shiptoId;
		String orderQty;
		String orderUom;
		String chgCurrency;
		String chgPrice;
		String loadId;
		String loadLine;
		String resetCheck;
		String releaseCheck;

		log.debug("begin hub/EdiResetStatusAction");
		String forward = "Success";
		HttpSession session = request.getSession();

		// get session beans
		user = (PersonnelBean) session.getAttribute("personnelBean");
		statusBeanCollection = (Collection) session.getAttribute("errorviewBeanCollection");

		if (user==null || statusBeanCollection==null) {
			forward = "Error";
			return mapping.findForward(forward);
		}
		String client = this.getDbUser(request);
		int rowcount = statusBeanCollection.size();
		EdiOrderStatusProcess orderstatusProcess = new EdiOrderStatusProcess(client);

		for (int i=1;i<=rowcount;i++) {

			resetCheck = request.getParameter("reset_sel_"+i);
			releaseCheck = request.getParameter("release_sel"+i);
			if (resetCheck!=null || releaseCheck!=null) {

				// get parameters
				companyId = request.getParameter("company_id_"+i);
				poNum = request.getParameter("customer_po_no_"+i);
				lineNum = request.getParameter("customer_po_line_no_"+i);
				txnType = request.getParameter("transaction_type_"+i);
				lineSeq = request.getParameter("line_sequence_"+i);
				catPartNo = request.getParameter("catpartno_"+i);
				chgOrder = request.getParameter("change_order_sequence_"+i);
				shiptoId = request.getParameter("shipto_party_id_"+i);
				orderQty = request.getParameter("ordered_qty_"+i);
				orderUom = request.getParameter("ordered_uom_"+i);
				chgCurrency = request.getParameter("currency_id_"+i);
				chgPrice = request.getParameter("unit_price_"+i);
				loadId = request.getParameter("load_id_"+i);
				loadLine = request.getParameter("load_line_"+i);

				if (log.isDebugEnabled()) {
					log.debug("resetCheck["+ i +"]: " + resetCheck);
					log.debug("companyId["+ i +"]: " + companyId);
					log.debug("poNum["+ i +"]: " + poNum);
					log.debug("lineNum["+ i +"]: " + lineNum);
					log.debug("txnType["+ i +"]: " + txnType);
					log.debug("lineSeq["+ i +"]: " + lineSeq);
					log.debug("chgOrder["+ i +"]: " + chgOrder);
					log.debug("shiptoId["+ i +"]: " + shiptoId);
					log.debug("loadId["+ i +"]: " + loadId);
					log.debug("loadLine["+ i +"]: " + loadLine);
					log.debug("currency["+ i +"]: " + chgCurrency);
					log.debug("price["+ i +"]: " + chgPrice);
				}

				if (releaseCheck!=null) {
					try {
						orderstatusProcess.releaseLine(companyId,poNum,lineNum,txnType,lineSeq,chgOrder,user.getPersonnelId());
					} catch (BaseException ber) {
						forward = "Error";
					}
				}

				try {
					orderstatusProcess.resetLineStatus(statusBeanCollection, companyId, poNum, lineNum, txnType, lineSeq,
							catPartNo, chgOrder, shiptoId, loadId, loadLine, orderQty, orderUom,
							chgCurrency, chgPrice, user.getPersonnelId(), i);
				} catch (BaseException be) {
					forward = "Error";
				}
			}
		}
		return mapping.findForward(forward);
	}

}
