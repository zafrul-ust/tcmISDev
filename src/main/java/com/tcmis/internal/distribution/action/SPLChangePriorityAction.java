package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

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
import com.tcmis.internal.distribution.process.SPLChangePriorityProcess;
import com.tcmis.internal.supply.beans.DbuyContractPriceOvBean;
 
  /******************************************************************************
   * Controller for CustomerAddressSearch
   * @version 1.0
   ******************************************************************************/
  public class SPLChangePriorityAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
   	if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "splchangepriority");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
	}
		
   	request.setAttribute("startSearchTime", new Date().getTime() );
    String forward = "success";

    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    /*    if (!personnelBean.getPermissionBean().hasUserPagePermission("openPos"))
    {
          return (mapping.findForward("nopermissions"));
    }
*/
    DbuyContractPriceOvBean inputBean = new DbuyContractPriceOvBean();
    
    SPLChangePriorityProcess process = new SPLChangePriorityProcess(this);
    
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
    
    String action = request.getParameter("uAction");
    if (action != null && "search".equals(action)) {
    	request.setAttribute("beanCollection", process.getDbuy(inputBean));
        this.saveTcmISToken(request);
    }
    else if ( "update".equals(action) ) {
    	
    	this.checkToken(request);
    	
    	if (personnelBean.getPermissionBean().hasOpsEntityPermission("supplierPriceList", inputBean.getOpsEntityId(), null)) { 
    		Collection<DbuyContractPriceOvBean> beans = BeanHandler.getBeans((DynaBean)form,"SupperPriceListBean",new DbuyContractPriceOvBean(),this.getTcmISLocale());
    		
    		String result = process.updatePriority(beans);
    		
    		request.setAttribute("beanCollection", process.getDbuy(inputBean));
    		if(result == null)
    			request.setAttribute("done", "Y");
    		else
    			request.setAttribute("tcmISErrors", result);
    	}
    }
    else if ("checkPriorityCount".equals(action)) {
    	String count = process.checkPriorityCount(inputBean);
    	request.setAttribute("count", count);
    	return (mapping.findForward("getPriorityCount"));
    }
    /*Since there is no search section we have to set end Time here as we cannot use the client side time.
    Client can be anywhere in the world.This should be done only for pages that have no search section.*/
    request.setAttribute("endSearchTime", new Date().getTime() );
    return (mapping.findForward(forward));
   }
  }