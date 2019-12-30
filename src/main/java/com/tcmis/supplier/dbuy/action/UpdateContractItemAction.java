/******************************************************
 * ViewPricesAction.java
 * 
 * Search DbuyContractPrice table on the given id
 ******************************************************
 */
package com.tcmis.supplier.dbuy.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.dbuy.process.ContractProcess;


public class UpdateContractItemAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// servlet params
		// servlet params
		String itemId = null;
		String invGroup = null;
		String shiptoCompanyId = null;
		String shiptoLocationId = null;
		String priority = null;
		String supplier = null;
		String carrier = null;
		String buyer = null;
		String sourcer = null;
		String terms = null;
		String fob = null;
		String supplierPart = null;
		String supplierContact = null;
		String shelfLife = null;
		String criticalCarrier = null;
		String clientPart = null;
		String clientPO = null;
		String roundTo = null;
		String multiple = null;
		String consign = null;
		String leadTime = null;
		String shipOrigin = null;
		String transitTime = null;
		String contractComment = null;
		String priceType = null;
		String path = null;
		String status = null;
		String prevPriority = null;

		String forward = "success";

		itemId = request.getParameter("addItemId");
		invGroup = request.getParameter("addInvGrp");
		shiptoCompanyId = request.getParameter("addShiptoCompId");
		shiptoLocationId = request.getParameter("addShiptoLocId");
		priority = request.getParameter("addPriority");
		supplier = request.getParameter("addSupplier");
		carrier = request.getParameter("addCarrier");
		buyer = request.getParameter("addBuyer");
		sourcer = request.getParameter("addSourcer");
		terms = request.getParameter("addTerms");
		fob = request.getParameter("addFOB");
		supplierPart = request.getParameter("addSupplierPart");
		supplierContact = request.getParameter("addSupplierContact");
		shelfLife = request.getParameter("addShelfLife");
		criticalCarrier = request.getParameter("addCritCarrier");
		clientPart = request.getParameter("addClientPart");
		clientPO = request.getParameter("addClientPO");
		roundTo = request.getParameter("addRoundTo");
		multiple = request.getParameter("addMultiple");
		consign = request.getParameter("addConsign");
		leadTime = request.getParameter("addLeadTime");
		shipOrigin = request.getParameter("addShipOrigin");
		transitTime = request.getParameter("addTransitTime");
		contractComment = request.getParameter("addContractComment");
		priceType = request.getParameter("addPriceType");
		path = request.getParameter("addSupplyPath");
		status = request.getParameter("addStatus");
		prevPriority = request.getParameter("prevPriority");

		if(log.isDebugEnabled()) {
			log.debug("got item id: " + itemId);
			log.debug("got inv grp: " + invGroup);
			log.debug("got shipto co: " + shiptoCompanyId);
			log.debug("got shipto loc: " + shiptoLocationId);
			log.debug("got priority: " + priority);
			log.debug("got supplier: " + supplier);
			log.debug("got carrier: " + carrier);
			log.debug("got buyer: " + buyer);
			log.debug("got sourcer: " + sourcer);
			log.debug("got terms: " + terms);
			log.debug("got fob: " + fob);
			log.debug("got supplierPart: " + supplierPart);
			log.debug("got supplierContact: " + supplierContact);
			log.debug("got shelfLife: " + shelfLife);
			log.debug("got criticalCarrier: " + criticalCarrier);
			log.debug("got clientPart: " + clientPart);
			log.debug("got clientPO: " + clientPO);
			log.debug("got roundTo: " + roundTo);
			log.debug("got multiple: " + multiple);
			log.debug("got consign: " + consign);
			log.debug("got leadTime: " + leadTime);
			log.debug("got shipOrigin: " + shipOrigin);
			log.debug("got transitTime: " + transitTime);
			log.debug("got contractComment: " + contractComment);
			log.debug("got price:" + priceType);
			log.debug("got path: " + path);
			log.debug("got status: " + status);
			log.debug("got prev priority: " + prevPriority);
		}

		HttpSession session = request.getSession();

		String client = "TCM_OPS";   //this.getDbUser(request);
		ContractProcess contractProcess = new ContractProcess(client);

		try {
			contractProcess.updateContractItem(itemId,invGroup,shiptoCompanyId,shiptoLocationId,priority,supplier,carrier,buyer,sourcer,
					terms,fob,supplierPart,supplierContact, shelfLife, criticalCarrier, clientPart, clientPO,
					roundTo, multiple, consign, leadTime, shipOrigin, transitTime, contractComment,priceType,
					path, status, prevPriority);
			session.removeAttribute("DbuyItemBeans");
		} catch (BaseException be) {
			forward = "error";
			session.setAttribute("ErrorMessage",be.getMessage());
		}

		//         session.setAttribute("ContractPriceBeans",itemPrices);

		return mapping.findForward(forward);
	}
}
