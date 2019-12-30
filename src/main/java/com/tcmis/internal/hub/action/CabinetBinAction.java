package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Vector;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.process.*;

/******************************************************************************
 * Controller for cabinet labels
 * 
 * @version 1.0
 ******************************************************************************/
public class CabinetBinAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception
	{

		if (!this.isLoggedIn(request))
		{
			request.setAttribute("requestedPage", "showcabinetmanagement");
			request.setAttribute("requestedURLWithParameters", this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		//CabinetBinInputBean bean = new CabinetBinInputBean();
		//BeanHandler.copyAttributes(form, bean);
		//CabinetManagementProcess cabinetManagementProcess = new CabinetManagementProcess(this.getDbUser(request));
		//request.setAttribute("hubCabinetViewBeanCollection",cabinetManagementProcess.getAllLabelData((PersonnelBean) this.getSessionObject(request, "personnelBean")));
		//request.setAttribute("hubCabinetViewBeanCollection",new Vector(0));

		// Create the drop-down bean for the COUNT_TYPE
		DropDownListBean[] dropDownList = new DropDownListBean[2];
		dropDownList[0] = new DropDownListBean(this.getResourceBundleValue(request, "label.receiptid"), "RECEIPT_ID");
		dropDownList[1] = new DropDownListBean(this.getResourceBundleValue(request, "label.itemid"), "ITEM_ID");
		request.setAttribute("countTypeDropDownList", dropDownList);

		CabinetBinInputBean bean = new CabinetBinInputBean();
		BeanHandler.copyAttributes(form, bean);
		CabinetBinProcess process = new CabinetBinProcess(this.getDbUser(request));
		request.setAttribute("catalogFacilityBeanCollection", process.getCatalogs(bean));

		if (form != null) {
			if (((DynaBean) form).get("submitAdd") != null && ((String) ((DynaBean) form).get("submitAdd")).length() > 0) {
				bean.setPersonnelId(new BigDecimal(((PersonnelBean) this.getSessionObject(request, "personnelBean")).getPersonnelId()));
				request.setAttribute("tcmisError",process.addPart(bean,(PersonnelBean) this.getSessionObject(request, "personnelBean")));
			}else if (((DynaBean) form).get("submitNew") != null
						&& ((String) ((DynaBean) form).get("submitNew"))
								.length() > 0) {
					log.debug("new");
			}
		}
		return (mapping.findForward("success"));
	}
}