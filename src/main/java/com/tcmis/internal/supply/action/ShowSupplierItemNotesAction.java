package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.tcmis.internal.supply.beans.SupplierItemNotesInputBean;
import com.tcmis.internal.supply.beans.SuppEntityItemNotesViewBean;
import com.tcmis.internal.supply.process.SupplierItemNotesProcess;
import com.tcmis.internal.report.beans.ErrorBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbUpdateException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
* Controller for receiving
* @version 1.0
******************************************************************************/
public class ShowSupplierItemNotesAction
extends TcmISBaseAction {

public ActionForward executeAction(ActionMapping mapping, ActionForm form,
HttpServletRequest request,
HttpServletResponse response) throws BaseException, Exception {

if (!this.isLoggedIn(request)) {
 request.setAttribute("requestedPage", "showsupplieritemnotes");
 //This is to save any parameters passed in the URL, so that they
 //can be acccesed after the login
 request.setAttribute("requestedURLWithParameters",
    this.getRequestedURLWithParameters(request));
 return (mapping.findForward("login"));
}

//If you need access to who is logged in
PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
/*Need to check if the user has permissions to view this page. if they do not have the permission
we need to not show them the page.*/
/*if (!personnelBean.getPermissionBean().hasUserPagePermission("pageId"))
{
return (mapping.findForward("nopermissions"));
}*/

/*Since there is no search section we consider this the start time. This should be done only for
* pages that have no search section.*/
request.setAttribute("startSearchTime", new Date().getTime() );

String forward = "success";



SupplierItemNotesProcess itemNotesProcess = new SupplierItemNotesProcess(this.getDbUser(request),getTcmISLocaleString(request));
//copy date from dyna form to the data bean
SupplierItemNotesInputBean inputBean = new SupplierItemNotesInputBean();
BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

// Search
if ( (inputBean.getuAction() == null)  || ("search".equals(inputBean.getuAction()) ) )
	{
		request.setAttribute("supItemNotesCol", itemNotesProcess.getItemNotes(inputBean, personnelBean));
		
	}	

request.setAttribute("endSearchTime", new Date().getTime() );
return (mapping.findForward(forward));
 }
}
