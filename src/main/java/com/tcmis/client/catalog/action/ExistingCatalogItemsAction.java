package com.tcmis.client.catalog.action;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.process.ExistingCatalogItemsProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class ExistingCatalogItemsAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showexistingcatalogitems");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	if (request.getParameter("catPartNo") == null ||
	 request.getParameter("catalogId") == null) {
	 return mapping.findForward("systemerrorpage");
	}
	else {
	 String catPartNo = request.getParameter("catPartNo");
	 String companyId = request.getParameter("companyId");
	 String catalogId = request.getParameter("catalogId");

	 //call the process here
	 ExistingCatalogItemsProcess existingCatalogItemsProcess = new
		ExistingCatalogItemsProcess(this.getDbUser(request));

	 Collection existingCatalogItemsBeanCollection = existingCatalogItemsProcess.
		getsearchResult(catPartNo, catalogId, companyId);
	 request.setAttribute("existingCatalogItemsBeanCollection",
		existingCatalogItemsBeanCollection);
	 request.setAttribute("catPartNo", catPartNo);
	 request.setAttribute("catalogId", catalogId);
	 request.setAttribute("companyId", companyId);

	 return mapping.findForward("showExistingcatalogitems");
	}
 }
}
