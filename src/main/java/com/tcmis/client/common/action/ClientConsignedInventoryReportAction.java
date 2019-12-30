package com.tcmis.client.common.action;

import java.util.Collection;
import java.math.BigDecimal;
import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.catalog.beans.InventoryInputBean;
import com.tcmis.client.catalog.process.InventoryProcess;
import com.tcmis.client.common.beans.ClientConsignedInventoryReportInputBean;
import com.tcmis.client.common.process.ClientConsignedInventoryReportProcess;

/**
 * ***************************************************************************
 * Controller for Cabinet Inventory
 *
 * @version 1.0
 *          ************************************************************************
 */

public class ClientConsignedInventoryReportAction extends TcmISBaseAction

{
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientconsignedinventoryreportmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("clientConsignedInventoryReport"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }

		String forward = "success";
		if (form != null) {
			ClientConsignedInventoryReportProcess process = new ClientConsignedInventoryReportProcess(this.getDbUser(request),getTcmISLocaleString(request));

			//copy date from dyna form to the data form
			ClientConsignedInventoryReportInputBean bean = new ClientConsignedInventoryReportInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

			if (((DynaBean) form).get("uAction") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("uAction")))) {
				request.setAttribute("logisticsBeanCollection",process.getSearchResult(bean));
			} else if (((DynaBean) form).get("uAction") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("uAction")))) {
				this.setExcel(response,"Inventory");
                process.getExcelReport(bean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale"),personnelBean).write(response.getOutputStream());
				return noForward;
			}else {
				InventoryProcess iProcess = new InventoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
				Collection facilityIgViewOvBeanCollection = iProcess.getDropDownData(personnelBean);
				request.setAttribute("facilityIgViewOvBeanCollection",facilityIgViewOvBeanCollection);
			}
		}
		return mapping.findForward("success");
	}
}
