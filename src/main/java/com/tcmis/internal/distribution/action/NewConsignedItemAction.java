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
import com.tcmis.internal.distribution.beans.NewConsignedItemInputBean;
import com.tcmis.internal.distribution.process.NewConsignedItemProcess;

import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for supplier requests
 * @version 1.0
     ******************************************************************************/
public class NewConsignedItemAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "newconsigneditem");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    
    String uAction = (String)( (DynaBean) form).get("uAction");
    
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
////	if (!personnelBean.getPermissionBean().hasUserPagePermission("newShipToAddress"))   {
////         return (mapping.findForward("nopermissions"));
////    }
//    
	NewConsignedItemProcess process = new NewConsignedItemProcess(getDbUser(request), getTcmISLocaleString(request));
 
	NewConsignedItemInputBean inputBean = new NewConsignedItemInputBean();
    BeanHandler.copyAttributes(form, inputBean);
    
    if("Y".equalsIgnoreCase(inputBean.getFromItemCount())){
    	String result = process.getSrcInventoryGroup(inputBean);
    	if(result != null){
    	inputBean.setSourceInventoryGroup(result);
    	request.setAttribute("sourceInventoryGroup", result);
    	}
    	else
    	{
    		request.setAttribute("consign", "N");
    	}
    	return mapping.findForward("success");    	
    }
    else
    {
        request.setAttribute("sourceInventoryGroup", inputBean.getSourceInventoryGroup());
    }

    if ("addConsignedItem".equals(uAction) ) {
/*    	if (!personnelBean.getPermissionBean().hasOpsEntityPermission("supplierPriceList", inputBean.getOpsEntityId(), null)) {
			return mapping.findForward("nopermission");
		}  */
    	
    	String result = process.addNewItem(inputBean);
	  	
	  	if(result == null) {
		  	request.setAttribute("done", "Y");
	  	}
	  	else {
	  		request.setAttribute("tcmISError", result);
	  	}
    }
    return mapping.findForward("success");
  }
}