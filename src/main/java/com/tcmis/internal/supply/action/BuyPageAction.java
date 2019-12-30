package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;

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
import com.tcmis.internal.supply.beans.AssociatePrViewBean;
import com.tcmis.internal.supply.beans.BuyPageInputBean;
import com.tcmis.internal.supply.process.BuyPageProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class BuyPageAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showbuyorders");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	if (form == null) {
	 this.saveTcmISToken(request);
	}

	BuyPageInputBean inputBean = new BuyPageInputBean();
	BuyPageProcess process = new BuyPageProcess(this.getDbUser(request));
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
			"personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

	/*Collection vvBuyOrderStatusBeanCollection = process.getBuyOrderStatus();
	request.setAttribute("vvBuyOrderStatusBeanCollection",
	vvBuyOrderStatusBeanCollection);

  Collection vvBuypageSortBeanCollection = process.getBuyOrderSort();
	request.setAttribute("vvBuypageSortBeanCollection",
	vvBuypageSortBeanCollection);*/

  Collection vvBuyerCollection = process.getCustomerBuyerNames();
	request.setAttribute("vvBuyerCollection",vvBuyerCollection);

	Collection vvInventoryGroupCollection = process.getMyInventoryGroups(personnelId);
	request.setAttribute("vvInventoryGroupCollection",vvInventoryGroupCollection);

	if (form == null) {
	 String[] statusaray = {"New"};
	 inputBean.setStatusCollectionSelect(statusaray);
	 request.setAttribute("searchWhat", "RADIAN_PO");
	 request.setAttribute("showOnlyOpenBuys", "Yes");
	}
	//if form is not null we have to perform some action
	if (form != null) {
	 if (!this.isTcmISTokenValid(request, true) &&
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

		/*Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)this.getSessionObject(
		 request, "hubInventoryGroupOvBeanCollection"));*/

		 //get search result
		 Collection c = process.getSearchResult(inputBean,true,null);
		 //add result and pass it to the jsp page
		 //this.setSessionObject(request, c, "associatePrViewBeanCollection");
		 request.setAttribute("associatePrViewBeanCollection", c);
		 return (mapping.findForward("showbuyorders"));
	 }
	 else if (inputBean.getSubmitUpdate() != null &&
		inputBean.getSubmitUpdate().trim().length() > 0) {
		//Vector savedBeanVector = new Vector( (Collection)this.getSessionObject(request, "associatePrViewBeanCollection"));

		// Cast form to DynaBean
		DynaBean dynaForm = (DynaBean) form;
		List associatePrViewBeans = (List) dynaForm.get("associatePrViewBean");
		Iterator iterator = associatePrViewBeans.iterator();

		Collection associatePrViewBeanInputCollection = new Vector();
		while (iterator.hasNext()) {
		 org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
			commons.beanutils.LazyDynaBean) iterator.next();

		 AssociatePrViewBean associatePrViewBean = new AssociatePrViewBean();
		 BeanHandler.copyAttributes(lazyBean, associatePrViewBean);
		 associatePrViewBean.setBuyerCompanyId(personnelBean.getCompanyId());
		 associatePrViewBeanInputCollection.add(associatePrViewBean);
		}

		HashMap createPoResult = new HashMap();
		createPoResult =  process.updateSelected(associatePrViewBeanInputCollection,personnelId);

		String errorMessage = (String) createPoResult.get("ERRORMESSAGE");
		String createdPos = (String) createPoResult.get("CREATEDPOS");
		log.info("Here createdPos " + createdPos + "");
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("createdPos", createdPos);

		/*Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)this.getSessionObject(
		 request, "hubInventoryGroupOvBeanCollection"));*/

		//get search result
		Collection c = process.getSearchResult(inputBean,true,null);
		//add result and pass it to the jsp page
		//this.setSessionObject(request, c, "associatePrViewBeanCollection");
		request.setAttribute("associatePrViewBeanCollection", c);
		return (mapping.findForward("showbuyorders"));
	 }
	 /*else if (inputBean.getsugetSubmitCreateReport() != null &&
		inputBean.getSubmitCreateReport().trim().length() > 0) {
		ResourceLibrary resource = new ResourceLibrary("report");
		BuyPageProcess process = new BuyPageProcess(this.getDbUser(request));

		Collection c = new Vector( (Collection)this.getSessionObject(
				 request, "receivingViewBeanCollection"));

		File dir = new File(resource.getString("excel.report.serverpath"));
		File file = File.createTempFile("excel", ".xls", dir);
		process.writeExcelFile(inputBean, process.createRelationalObject(c),
		 file.getAbsolutePath());
		this.setSessionObject(request,
		 resource.getString("report.hosturl") +
		 resource.getString("excel.report.urlpath") +
		 file.getName(), "filePath");

		request.setAttribute("doexcelpopup", "Yes");
		request.setAttribute("receivingViewRelationBeanCollection",
			process.createRelationalObject(c));

		if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
		 return (mapping.findForward("showchemical"));
		}
		else {
		 return (mapping.findForward("shownonchemical"));
		}*/
		/*String[] statusaray = {"New"};
		inputBean.setStatusCollectionSelect(statusaray);*/
		request.setAttribute("searchWhat", "RADIAN_PO");
		request.setAttribute("showOnlyOpenBuys", "Yes");
		request.setAttribute("associatePrViewBeanCollection", null);
//		this.setSessionObject(request, null, "associatePrViewBeanCollection");
		return mapping.findForward("showbuyorders");
	}
	else {
//	 this.setSessionObject(request, null, "associatePrViewBeanCollection");
	 request.setAttribute("associatePrViewBeanCollection", null);
	 return mapping.findForward("showbuyorders");
	}
 }
}