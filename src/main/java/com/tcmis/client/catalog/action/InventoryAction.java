package com.tcmis.client.catalog.action;

import java.io.File;
import java.util.Collection;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.beans.InventoryInputBean;
import com.tcmis.client.catalog.process.InventoryProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class InventoryAction
		extends TcmISBaseAction {

public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
		request.setAttribute("requestedPage", "inventorymain");
		request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
		return (mapping.findForward("login"));
	}
	
	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("stockedInventoryReport"))
    {
      return (mapping.findForward("nopermissions"));
    }


	if (form == null) {
		this.saveTcmISToken(request);
	}

	//if form is not null we have to perform some action
	if (form != null) {
		InventoryProcess process = new InventoryProcess(this.getDbUser(request),getTcmISLocaleString(request));

		//copy date from dyna form to the data form
		InventoryInputBean bean = new InventoryInputBean();
		BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

		if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			request.setAttribute("pkgInventoryDetailWebPrInventoryBeanCollection",process.createRelationalObject(process.getsearchResult(bean,personnelBean)));
			request.setAttribute("showPlots","Y");
		} else if (((DynaBean) form).get("action") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			this.setExcel(response,"Inventory");
            process.getExcelReport(bean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale"),personnelBean).write(response.getOutputStream());
            return noForward;
		}else {
			request.setAttribute("facilityIgViewOvBeanCollection",process.getDropDownData(personnelBean));
            request.setAttribute("showPlots","N");
		}
	}
	return mapping.findForward("success");
}
}
