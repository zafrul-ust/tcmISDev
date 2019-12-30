package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.BatchUpdateBuyerInputBean;
import com.tcmis.internal.distribution.process.BatchUpdateBuyerProcess;

import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for supplier requests
 * @version 1.0
     ******************************************************************************/
public class BatchUpdateBuyerAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "batchupdatebuyer");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    String uAction = (String)( (DynaBean) form).get("uAction");
    
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
//	CustomerRequestProcess sprocess = new CustomerRequestProcess(getDbUser(request),getLocale(request));
//	//request.setAttribute("priceList", sprocess.getPriceListDropDown());
////	if (!personnelBean.getPermissionBean().hasUserPagePermission("newShipToAddress"))   {
////         return (mapping.findForward("nopermissions"));
////    }
//    
    BatchUpdateBuyerProcess process = new BatchUpdateBuyerProcess(getDbUser(request), getTcmISLocaleString(request));
 
    BatchUpdateBuyerInputBean inputBean = new BatchUpdateBuyerInputBean();
    BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

    if ("batchUpdateBuyer".equals(uAction) ) {
    	if (!personnelBean.getPermissionBean().hasOpsEntityPermission("supplierPriceList", inputBean.getOpsEntityId(), null)) {
			return mapping.findForward("nopermission");
		}
    	
	  	Vector results = process.doBatchUpdateBuyer(inputBean,personnelId);
	  	
	  	if("done".equals(results.get(1))) {
		  	request.setAttribute("rowCount", results.get(0));
		  	request.setAttribute("done", "Y");
	  	}
	  	else {
	  		request.setAttribute("tcmISError", results.get(0));
	  	}
    }
    return mapping.findForward("success");
  }
}