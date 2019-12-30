package com.tcmis.internal.distribution.action;

import java.util.Collection;

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
import com.tcmis.internal.distribution.beans.PartSearchInputBean;
import com.tcmis.internal.distribution.beans.PartSearchBean;
import com.tcmis.internal.distribution.process.PartSearchProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class PartSearchAction
extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

//		login

if (!this.isLoggedIn(request)) {
	request.setAttribute("requestedPage", "partsearchmain");
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
PartSearchProcess process = new PartSearchProcess(this.getDbUser(request),getTcmISLocaleString(request));

PartSearchInputBean inputBean = new PartSearchInputBean();
BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

if (uAction.equals("search")) {
	Collection results = process.getPartSearchResult(personnelBean, inputBean);		
	
//	Collection historyResults = orderProcess.getSalesHistoryResult();
	request.setAttribute("partSearchCollection", results);
//	request.setAttribute("salesHistoryCollection", historyResults);
//	request.setAttribute("resetArray", true);
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
