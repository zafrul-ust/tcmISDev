package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.PartSearchInputBean;
import com.tcmis.internal.distribution.beans.PartSearchBean;
import com.tcmis.internal.distribution.beans.QuotationHistoryViewBean;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class QuickItemSearchAction
extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

	//		login
	
	if (!this.isLoggedIn(request)) {
		request.setAttribute("requestedPage", "quickitemsearchmain");
		request.setAttribute("requestedURLWithParameters",
				this.getRequestedURLWithParameters(request));
		return (mapping.findForward("login"));
	}
	
	//main
	//if( form == null )
	//	return (mapping.findForward("success"));
	String uAction = (String) ( (DynaBean)form).get("uAction");
	if( uAction == null ) return mapping.findForward("success");
	
	//result
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	
	PartSearchInputBean inputBean = new PartSearchInputBean();
	BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
	
	if (uAction.equals("search")) {
		GenericProcess process = new GenericProcess(this);
		
		request.setAttribute("partSearchCollection",process.getSearchResult("select * from table (pkg_sales_search.FX_QUICK_SEARCH({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}, {12}, {13}, {14}, {15})) order by warehouse_on_hand desc", 
				new QuotationHistoryViewBean(),
				inputBean.getInventoryGroup(),
				inputBean.getPartNumber(), inputBean.getSearchPartNumberMode(),
				inputBean.getSpecification(), inputBean.getSearchSpecificationMode(),
				inputBean.getCustomerPartNumber(), inputBean.getSearchCustomerPartNumberMode(),
				inputBean.getText(), inputBean.getSearchTextMode(),
				inputBean.getAlternate(), inputBean.getSearchAlternateMode(),
				inputBean.getPartDesc(), inputBean.getSearchPartDescMode(),
				inputBean.getCurrencyId(), personnelBean.getPersonnelId(), 
				inputBean.getPriceGroupId()
		));
		
		return (mapping.findForward("success"));
	}
	else if (uAction.equals("createExcel") ) {
	
		return noForward;
	}
	//search    
	else {/* set up search page data*/
	//	request.setAttribute("setUpCollection", process.getSetUpCollection(new BigDecimal(personnelBean.getPersonnelId())));
	}
		return mapping.findForward("success");
  }
}
