/******************************************************
 * AddLineAction.java
 * 
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.DateHandler;
import com.tcmis.supplier.wbuy.beans.PoHeaderViewBean;
import com.tcmis.supplier.wbuy.beans.PoLineDetailViewBean;
import com.tcmis.supplier.wbuy.process.OrderingProcess;


public class AddLineAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// servelet params
		String additemid = null;
		String radianpo = null;
		String addqty = null;
		String addprice = null;

		String forward = "Success";
		String errMsg = null;

		radianpo = request.getParameter("radianpo");

		// save the new additional line
		//log.debug("got radianpo: " + radianpo);

		HttpSession session = request.getSession();

		// get personnel bean
		PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
		if (personnel==null) { errMsg = "No personnel found."; }

		// get the header bean frrom the session (for supplier)
		PoHeaderViewBean poHeaderBean = (PoHeaderViewBean) session.getAttribute("POHeaderBean");
		if (poHeaderBean==null) { errMsg = "No header found."; }

		// get the material bean(s) from the session (for currency_id)
		Collection materialBeans = (Collection) session.getAttribute("MaterialBeans");
		if (materialBeans==null) return mapping.findForward("error");
		PoLineDetailViewBean poLineDetailBean = null;
		Iterator matIter = materialBeans.iterator();
		while (matIter.hasNext()) {
			poLineDetailBean = (PoLineDetailViewBean) matIter.next();
		}
		if (poLineDetailBean==null) { errMsg = "No material lines found."; }

		if (errMsg!=null) {
			session.setAttribute("POErrorMsgBean",errMsg);
			return mapping.findForward("Error");
		}

		String client = this.getDbUser(request);;
		OrderingProcess orderProcess = new OrderingProcess(client);

		// save the new additional line

		String mydate=DateHandler.formatOracleDate(new Date());
		String nullStr=null;
		int userID=personnel.getPersonnelId();
		Vector<String> names = new Vector<String>();
		Enumeration e = request.getParameterNames();
		while( e.hasMoreElements() )
			names.add((String)e.nextElement());
		int totalLine = 0 ;
		for(String name:names)
			if(name.startsWith("addItemID_")) {
				totalLine++;
			}

		String currency = poLineDetailBean.getCurrencyId();
		String supplier = poHeaderBean.getSupplier();

		BigDecimal poTotalPrice = poHeaderBean.getPoPrice();
		double totalPoPrice = poTotalPrice.doubleValue() ;
		for(int i=0;i<totalLine;i++) {
			additemid = request.getParameter("addItemID_"+i);
			addqty = request.getParameter("addQuantity_"+i);
			addprice = request.getParameter("addUnitPrice_"+i);
			String line = request.getParameter("AddLineNum_"+i);
			int lineNum= (Integer.parseInt(line));
			long itemID=Long.parseLong(additemid.trim());
			int quantity=Integer.parseInt(addqty.trim());
			String saveResult = orderProcess.saveLine(radianpo,lineNum,itemID,quantity,addprice.trim(),null,null,null,nullStr,nullStr,nullStr,nullStr,userID,nullStr,nullStr,0,nullStr,currency,supplier,nullStr,nullStr);
			double addChgUnitPrice = Double.parseDouble(addprice.trim());
			double totalAddPrice = quantity * addChgUnitPrice ;
			totalPoPrice +=  totalAddPrice;
		}

		poTotalPrice = new BigDecimal(totalPoPrice);

		// now we have to rebuild line data so the new line will show up on the screen for them to confirm.

		// query only ADDITIONAL_CHARGES PO_line_detail_view bean(s)
		//log.debug("getting additional charges beans");
		Collection addchargeBeans = orderProcess.getAddChargeLines(radianpo);

		// modify the POHeaderBean to reflect new TOTAL PRICE
		poTotalPrice = new BigDecimal(totalPoPrice);
		poTotalPrice = poTotalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);

		poHeaderBean.setPoPrice(poTotalPrice);

		int totalLineCount=0;
		if (materialBeans!=null) totalLineCount += materialBeans.size();
		if (addchargeBeans!=null) totalLineCount += addchargeBeans.size();
		//log.debug("totalLineCount: " + totalLineCount);

		// set the session beans
		if (! addchargeBeans.isEmpty()) {
			session.setAttribute("AddChargeBeans", addchargeBeans);
		}
		session.setAttribute("TotalLineCount",totalLineCount+"");
		session.setAttribute("POHeaderBean",poHeaderBean);

		String confirmedStatus = null;
		String reason = "Additional charge needs to be approved by a Haas buyer.";
		String probResult = orderProcess.problemPO(poHeaderBean, personnel.getPersonnelId(),reason);
		if (probResult!=null) {
			errMsg = "Error setting PO to problem";
			forward = "Error";
		} else {
			confirmedStatus = orderProcess.STATUS_PROB;
			// forward = "Problem";
			if(log.isDebugEnabled()) {
				log.debug("Problem: " + reason);
			}
			session.setAttribute("POProblemMsg",reason);
			session.setAttribute("POProblemReason","Once the buyer approves the charge the PO will be ready to confirm.");
			session.setAttribute("POProblemAction","PROBLEM");
			session.setAttribute("ConfirmedBean",confirmedStatus+"/REVIEW");
			session.setAttribute("StatusBean",confirmedStatus);
			session.setAttribute("CommentsBean",reason);
		}
		return mapping.findForward(forward);
	}
}
