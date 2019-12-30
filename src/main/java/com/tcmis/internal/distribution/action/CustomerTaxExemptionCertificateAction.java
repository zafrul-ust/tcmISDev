  package com.tcmis.internal.distribution.action;

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
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.BillToCustomerTaxExemptionBean;
import com.tcmis.internal.distribution.process.CustTaxExempCertProcess;

  /******************************************************************************
   * Controller for CustomerAddressSearch
   * @version 1.0
   ******************************************************************************/
  public class CustomerTaxExemptionCertificateAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {


        if (!this.isLoggedIn(request)) {
           request.setAttribute("requestedPage", "customertaxexemptioncertificate");
           request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
           return (mapping.findForward("login"));
       }
      
       PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
       BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
      /* if (!personnelBean.getPermissionBean().hasUserPagePermission("customertaxexemptioncertificate"))
       {
         return (mapping.findForward("nopermissions"));
       }*/

       String forward = "success";       
       request.setAttribute("startSearchTime", new Date().getTime());     
       request.setAttribute("customerId",request.getParameter("customerId"));
          
         BillToCustomerTaxExemptionBean CustTaxExempCertInputBean = new BillToCustomerTaxExemptionBean();
          BeanHandler.copyAttributes(form, CustTaxExempCertInputBean,getTcmISLocale(request));

          CustTaxExempCertProcess process = new CustTaxExempCertProcess(this.getDbUser(request),getTcmISLocaleString(request));         
          //VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
          //request.setAttribute("vvCountryBeanCollection", vvDataProcess.getNormalizedCountryState());
          // Search
          if (CustTaxExempCertInputBean.getAction() == null ) {
              request.setAttribute("billToCustTaxExempViewBeanColl", process.getSearchResult(CustTaxExempCertInputBean));
              this.saveTcmISToken(request);
             
          } 
          else if ("update".equalsIgnoreCase(CustTaxExempCertInputBean.getAction())  ) {
              if (!personnelBean.getPermissionBean().hasOpsEntityPermission("CustomerDetailAdmin", null, null))
              {
                  request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
                  request.setAttribute("billToCustTaxExempViewBeanColl", process.getSearchResult(CustTaxExempCertInputBean));
                  return (mapping.findForward("success"));
              }

              checkToken(request);
              BillToCustomerTaxExemptionBean bean = new BillToCustomerTaxExemptionBean();
              Collection<BillToCustomerTaxExemptionBean> beans = BeanHandler.getBeans((DynaBean) form, "billToCustTaxExempViewBean", bean,getTcmISLocale(request));
              Collection errorMsgs =  process.addCustomerTaxExemptionRecord(beans, CustTaxExempCertInputBean);
              request.setAttribute("billToCustTaxExempViewBeanColl", process.getSearchResult(CustTaxExempCertInputBean));           
              request.setAttribute("tcmISErrors", errorMsgs);       	  
        	 
              
          }
          request.setAttribute("vvValidTaxCountryColl", process.getValidTaxCountry());
          request.setAttribute("endSearchTime", new Date().getTime() );      
          return (mapping.findForward(forward));
          
    }   
  }