/******************************************************
 * SpecDisplayAction.java
 *
 * Display a page of all contract items (based on qry)
 ******************************************************
 */
package com.tcmis.supplier.dbuy.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.dbuy.process.ContractProcess;


public class SpecDisplayAction extends TcmISBaseAction {


	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// parameters
		String itemId = null;
		String inventoryGroup = null;
		String supplierId = null;
		String fromForm = null;
		// session beans (in/out)
		Collection suppliers = null;
		Collection inventoryGroups = null;
		Collection itemSpecs = new Vector();

		log.debug("begin ItemDisplayAction");
		String forward = "success";
		HttpSession session = request.getSession();

		itemId = request.getParameter("itemid");
		inventoryGroup = request.getParameter("inventorygroup");
		supplierId = request.getParameter("supplierid");
		fromForm = request.getParameter("fromform");

		suppliers = (Collection) session.getAttribute("SupplierBeanCollection");
		inventoryGroups = (Collection) session.getAttribute("InventoryGroupBeanCollection");

		ContractProcess contractProcess = null;
		try {
			if(log.isDebugEnabled()) {
				log.debug("finding item: "+itemId);
				log.debug("finding inventoryGroup: "+inventoryGroup);
				log.debug("finding supplier id: "+supplierId);
			}
			String client = "TCM_OPS";   //this.getDbUser(request);
			contractProcess = new ContractProcess(client);
			if (suppliers==null) suppliers = contractProcess.getSuppliers();
			if (inventoryGroups==null) inventoryGroups = contractProcess.getInventoryGroups();
			if (fromForm!=null) itemSpecs = contractProcess.getDbuySpecs(itemId,supplierId,inventoryGroup);
		} catch (BaseException be) {
			log.error("Base Excpetion in DataSPECS for getSuppliers|getInvGrp|getItems");
			forward = "error";
		} finally {
			contractProcess = null;
		}

		session.setAttribute("DbuySpecBeans",itemSpecs);
		session.setAttribute("SupplierBeanCollection",suppliers);
		session.setAttribute("InventoryGroupBeanCollection",inventoryGroups);
		if (itemId!=null) session.setAttribute("SearchItemBean",itemId);
		if (supplierId!=null) session.setAttribute("SearchSupplierBean",supplierId);
		if (inventoryGroup!=null) session.setAttribute("SearchInventoryGroupBean",inventoryGroup);

		if(log.isDebugEnabled()) {
			log.debug("Set session attributes. using forward: " + forward);
		}
		return mapping.findForward(forward);
	}

}
