package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

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
import com.tcmis.internal.supply.beans.ResendXBuyInputBean;
import com.tcmis.internal.supply.beans.ResendXbuyViewBean;
import com.tcmis.internal.supply.process.ResendXbuyProcess;
import com.tcmis.supplier.shipping.process.ShipmentSelectionProcess;


/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */  

public class ResendXBuyAction
        extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            BaseException, Exception {

        if (!this.isLoggedIn(request)) {
           request.setAttribute("requestedPage", "resendxbuy");
           request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
           return (mapping.findForward("login"));
       }
      
       PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
       BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
       if (!personnelBean.getPermissionBean().hasUserPagePermission("resendXBuy"))
       {
         return (mapping.findForward("nopermissions"));
       }

       String forward = "success";       
       request.setAttribute("startSearchTime", new Date().getTime());     
     
          
          ResendXBuyInputBean resendXBuyInputBean = new ResendXBuyInputBean();
          BeanHandler.copyAttributes(form, resendXBuyInputBean,getTcmISLocale(request));

          ResendXbuyProcess process = new ResendXbuyProcess(this.getDbUser(request),getTcmISLocaleString(request));         

          // Search
          if (resendXBuyInputBean.getAction() == null ) {
              request.setAttribute("resendxBuyViewBeanCollection", process.getSearchResult(resendXBuyInputBean));
              
             ShipmentSelectionProcess shippingProcess = new ShipmentSelectionProcess(this.getDbUser(request),getTcmISLocaleString(request));
      		 request.setAttribute("supplierLocationOvBeanCollection", shippingProcess.getSearchDropDown(personnelId));
      		
              this.saveTcmISToken(request);
             
          }
         //  Create Excel
        /*  else if (action != null && "createExcel".equals(action)) {
              this.setExcel(response, "PoExpediting List");
              process.getExcelReport(resendXBuyInputBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
              return noForward;
          }*/
         //  Update 
          else if ("update".equalsIgnoreCase(resendXBuyInputBean.getAction())) {
              checkToken(request);
              if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("BuyOrder",null,null,null))
              {
                  request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
                  request.setAttribute("resendxBuyViewBeanCollection", process.getSearchResult(resendXBuyInputBean));
                  return (mapping.findForward("success"));
              }

              ResendXbuyViewBean bean = new ResendXbuyViewBean();
              Collection<ResendXbuyViewBean> beans = BeanHandler.getBeans((DynaBean) form, "resendxBuyViewBean", bean,getTcmISLocale(request));

              Collection errorMsgs =  process.update(beans);
              request.setAttribute("resendxBuyViewBeanCollection", process.getSearchResult(resendXBuyInputBean));
              
              ShipmentSelectionProcess shippingProcess = new ShipmentSelectionProcess(this.getDbUser(request),getTcmISLocaleString(request));
       		  request.setAttribute("supplierLocationOvBeanCollection", shippingProcess.getSearchDropDown(personnelId));
              request.setAttribute("tcmISErrors", errorMsgs);       	  
        	 
              
          }
          else if ("confirm".equalsIgnoreCase(resendXBuyInputBean.getAction())) {
              checkToken(request);
              if ( !personnelBean.getPermissionBean().hasInventoryGroupPermission("BuyOrder",null,null,null) )
              {
                  request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
                  request.setAttribute("resendxBuyViewBeanCollection", process.getSearchResult(resendXBuyInputBean));
                  return (mapping.findForward("success"));
              }
          	 String radianPo = request.getParameter("radianPo");
          	 String airgasCu = request.getParameter("airgasCu");

              Collection errorMsgs =  process.confirm(radianPo,airgasCu);
              request.setAttribute("resendxBuyViewBeanCollection", process.getSearchResult(resendXBuyInputBean));
              
              ShipmentSelectionProcess shippingProcess = new ShipmentSelectionProcess(this.getDbUser(request),getTcmISLocaleString(request));
       		  request.setAttribute("supplierLocationOvBeanCollection", shippingProcess.getSearchDropDown(personnelId));
              request.setAttribute("tcmISErrors", errorMsgs);       	  
              
          }
          
          request.setAttribute("endSearchTime", new Date().getTime() );      
          return (mapping.findForward(forward));
    }
}
