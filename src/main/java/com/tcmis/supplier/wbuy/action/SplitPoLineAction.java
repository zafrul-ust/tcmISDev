/******************************************************
 * PurchaseOrderAction.java
 * 
 * Prep the Purchase Order screen
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.PoHeaderViewBean;
import com.tcmis.internal.supply.beans.PoLineDetailViewBean;
import com.tcmis.supplier.shipping.process.ShipmentSelectionProcess;
import com.tcmis.supplier.wbuy.process.SplitPoLineProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;


public class SplitPoLineAction extends TcmISBaseAction {

     public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws BaseException, Exception
    {

   	if (!this.isLoggedIn(request))
		{
			request.setAttribute("requestedPage", "splitpoline");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		/*
     	if (! permissionBean.hasFacilityPermission("POText", null, null))
     	{
        	return mapping.findForward("nopermissions");
     	}
		*/

    ShipmentSelectionProcess process = new ShipmentSelectionProcess(this.getDbUser(request),getTcmISLocaleString(request));
		request.setAttribute("supplierLocationOvBeanCollection", process.getSearchDropDown(personnelId));
      
    PoHeaderViewBean inputBean = new PoHeaderViewBean();
    BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
    
    if (inputBean.getRadianPo() == null) {
         return mapping.findForward("genericerrorpage");
    }

    SplitPoLineProcess splitProcess = new SplitPoLineProcess(this.getDbUser(request),getTcmISLocaleString(request));
    String action = "";
	  action = ((String) ( (DynaBean) form).get("action") );

    if (action.equalsIgnoreCase("confirm"))
    {
	    	if (!this.isTcmISTokenValid(request, true)) {
	   		 BaseException be = new BaseException("Duplicate form submission");
	   		 be.setMessageKey("error.submit.duplicate");
         PoHeaderViewBean poHeaderBean = splitProcess.getPOHeader(inputBean.getRadianPo());
         request.setAttribute("POHeaderBean", poHeaderBean);
         throw be;
	   		}
	   		this.saveTcmISToken(request);

        Collection beans = BeanHandler.getBeans((DynaBean) form,"poLineDetailViewBean", new PoLineDetailViewBean(), getTcmISLocale(request));
        //log.debug(beans);
        PoLineDetailViewBean bean = null;
        for (Object obj : beans) {
					if (obj == null)
						continue;
					bean = (PoLineDetailViewBean) obj;
					if (!personnelBean.getPermissionBean().hasSupplierLocationPermission("usGovShipConfirm",
							bean.getShipFromLocationId(),null,bean.getSupplier()))
					{
						bean.setLineChangeStatus("");
					}
				}

        Object[] results = splitProcess.confirmPo(inputBean,beans,personnelId);
        request.setAttribute("tcmISError", results[0]);
        Object[] results1 = splitProcess.updateExpeditenotes(beans,personnelId,false);
      
        PoHeaderViewBean poHeaderBean = splitProcess.getPOHeader(inputBean.getRadianPo());
        request.setAttribute("POHeaderBean", poHeaderBean);
    }
    else if (action.equalsIgnoreCase("updateExpeditenotes"))
    {
	    	if (!this.isTcmISTokenValid(request, true)) {
	   		 BaseException be = new BaseException("Duplicate form submission");
	   		 be.setMessageKey("error.submit.duplicate");
         PoHeaderViewBean poHeaderBean = splitProcess.getPOHeader(inputBean.getRadianPo());
         request.setAttribute("POHeaderBean", poHeaderBean);
         throw be;
	   		}
	   		this.saveTcmISToken(request);

        Collection beans = BeanHandler.getBeans((DynaBean) form,"poLineDetailViewBean", new PoLineDetailViewBean(), getTcmISLocale(request));
        //log.debug(beans);
        
        Object[] results = splitProcess.updateExpeditenotes(beans,personnelId,true);
        request.setAttribute("tcmISError", results[0]);

        PoHeaderViewBean poHeaderBean = splitProcess.getPOHeader(inputBean.getRadianPo());
        request.setAttribute("POHeaderBean", poHeaderBean);
    }
    else
    {
    PoHeaderViewBean poHeaderBean = splitProcess.getPOHeader(inputBean.getRadianPo());
    request.setAttribute("POHeaderBean", poHeaderBean);
    this.saveTcmISToken(request);
    }

    return mapping.findForward("sucess");
   }   
}
