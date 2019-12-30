package com.tcmis.supplier.shipping.action;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.supplier.shipping.beans.*;
import com.tcmis.supplier.shipping.process.*;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
 ******************************************************************************/
public class PackHistoryAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "packmain");
			request.setAttribute("requestedURLWithParameters", this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		String userAction = null;
		if (form == null
				|| (userAction = ((String) ((DynaBean) form).get("userAction"))) == null) {
			return (mapping.findForward("success"));
		}
		BigDecimal personnelId = new BigDecimal(((PersonnelBean) this.getSessionObject(
				request, "personnelBean")).getPersonnelId());

		DynaBean dynaBean = (DynaBean) form;
		SupplierShippingInputBean inputbean = new SupplierShippingInputBean();
		BeanHandler.copyAttributes(dynaBean, inputbean,getTcmISLocale(request));
		inputbean.setPersonnelId(personnelId);

		//if form is not null we have to perform some action
		if (userAction.equals("display")) {
			ShipmentSelectionProcess process = new ShipmentSelectionProcess(
					this.getDbUser(request));
			request.setAttribute("supplierLocationOvBeanCollection", process.getSearchDropDown(personnelId));
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		PackProcess process = new PackProcess(this.getDbUser(request));

    if (userAction.equals("search")) {
			Object[] results = process.showResult(inputbean);		
			request.setAttribute("suppPackingViewBeanCollection", results[0]);
			request.setAttribute("rowCountPart", results[1]);
			request.setAttribute("rowCountItem", results[2]);
			this.saveTcmISToken(request);
			request.setAttribute("carrierBeanColl",process.getValidCarriers(inputbean));
			
		}
    else if (userAction.equals("printTable")) {
    this.setExcelHeader(response);
    String fileUrl = process.printTable(process.getSearchResult(inputbean));
    if (fileUrl.length() > 0){
      //this.setSessionObject(request, fileUrl, "filePath");
      request.setAttribute("filePath", fileUrl);
      return mapping.findForward("viewfile");
    }else{
      return mapping.findForward("systemerrorpage");
    }
   }
		return (mapping.findForward("success"));
	}
}
