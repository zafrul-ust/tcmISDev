package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.BuyPageInputBean;
import com.tcmis.internal.supply.beans.PoLineDetailViewBean;
import com.tcmis.internal.supply.process.BuyPageProcess;
import com.tcmis.internal.supply.process.PurchaseOrderProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class PurchaseOrderPageAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showpurchaseorders");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	if (form == null) {
	 this.saveTcmISToken(request);
	}

	BuyPageInputBean inputBean = new BuyPageInputBean();
	BuyPageProcess buyOrderProcess = new BuyPageProcess(this.getDbUser(request));
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
			"personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

	Collection vvBuyerCollection = buyOrderProcess.getCustomerBuyerNames();
	request.setAttribute("vvBuyerCollection",vvBuyerCollection);

	Collection vvInventoryGroupCollection = buyOrderProcess.getMyInventoryGroups(personnelId);
	request.setAttribute("vvInventoryGroupCollection",vvInventoryGroupCollection);

	if (form == null) {
	 String[] statusaray = {"New"};
	 inputBean.setStatusCollectionSelect(statusaray);
	 request.setAttribute("searchWhat", "RADIAN_PO");
	 request.setAttribute("showOnlyOpenBuys", "Yes");
	}
	//if form is not null we have to perform some action
	if (form != null) {
	 if (this.isTcmISTokenValid(request, true) &&
		( ( (DynaBean) form).get("submitSearch") != null &&
		( (String) ( (DynaBean) form).get("submitSearch")).length() > 0)) {
		BaseException be = new BaseException("Duplicate form submission");
		be.setMessageKey("error.submit.duplicate");
		this.saveTcmISToken(request);
		throw be;
	 }
	 this.saveTcmISToken(request);

	 //copy date from dyna form to the data form
	 inputBean.setDoTrim(true);
	 BeanHandler.copyAttributes(form, inputBean);
	 //inputBean.setStatusCollection(vvBuyOrderStatusBeanCollection);
	 request.setAttribute("buyPageInputBean",inputBean);
	 //check what button was pressed and determine where to go
	 if (inputBean.getSubmitSearch() != null &&
		inputBean.getSubmitSearch().trim().length() > 0) {


		PurchaseOrderProcess poProcess = new PurchaseOrderProcess(this.getDbUser(request));
		//get search result
		Collection c = poProcess.getCustomerSearch(inputBean,true,null);
		//add result and pass it to the jsp page
		request.setAttribute("poHeaderViewBeanCollection", c);
		return (mapping.findForward("showpurchaseorders"));
	 }
	 else if (inputBean.getSubmitUpdate() != null &&
		inputBean.getSubmitUpdate().trim().length() > 0) {
		//Vector savedBeanVector = new Vector( (Collection)this.getSessionObject(request, "poHeaderViewBeanCollection"));
		PurchaseOrderProcess poProcess = new PurchaseOrderProcess(this.getDbUser(request));
		// Cast form to DynaBean
		DynaBean dynaForm = (DynaBean) form;
		List poLineDetailViewBeans = (List) dynaForm.get("poLineDetailViewBean");
		Iterator iterator = poLineDetailViewBeans.iterator();

		Collection poLineDetailViewBeanInputCollection = new Vector();
		while (iterator.hasNext()) {
		 org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
			commons.beanutils.LazyDynaBean) iterator.next();

		 PoLineDetailViewBean poLineDetailViewBean = new PoLineDetailViewBean();
		 BeanHandler.copyAttributes(lazyBean, poLineDetailViewBean);
		 poLineDetailViewBeanInputCollection.add(poLineDetailViewBean);
		}

		HashMap createPoResult = new HashMap();
		createPoResult =  poProcess.updateSelected(poLineDetailViewBeanInputCollection,personnelId);

		String errorMessage = (String) createPoResult.get("ERRORMESSAGE");
		request.setAttribute("errorMessage", errorMessage);

		/*Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)this.getSessionObject(
		 request, "hubInventoryGroupOvBeanCollection"));*/

		//get search result
		Collection c = poProcess.getCustomerSearch(inputBean,true,null);
		//add result and pass it to the jsp page
		request.setAttribute("poHeaderViewBeanCollection", c);
		return (mapping.findForward("showpurchaseorders"));
	 }
		request.setAttribute("searchWhat", "RADIAN_PO");
		request.setAttribute("showOnlyOpenBuys", "Yes");
		request.setAttribute("poHeaderViewBeanCollection", null);
		return mapping.findForward("showpurchaseorders");
	}
	else {
	 request.setAttribute("poHeaderViewBeanCollection", null);
	 return mapping.findForward("showpurchaseorders");
	}
 }
}
