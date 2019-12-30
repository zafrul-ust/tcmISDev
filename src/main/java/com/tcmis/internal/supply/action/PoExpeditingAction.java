package com.tcmis.internal.supply.action;

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
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.internal.supply.beans.POExpeditingInputBean;
import com.tcmis.internal.supply.beans.PoLineExpediteDateBean;
import com.tcmis.internal.supply.process.PoExpeditingProcess;



/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */  

public class PoExpeditingAction
        extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            BaseException, Exception {

        if (!this.isLoggedIn(request)) {
           request.setAttribute("requestedPage", "poexpeditingmain");
           request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
           return (mapping.findForward("login"));
       }
      
       PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
       if (!personnelBean.getPermissionBean().hasUserPagePermission("openPos"))
       {
         return (mapping.findForward("nopermissions"));
       }
       
      String forward = "success";      
      
      POExpeditingInputBean inputBean = new POExpeditingInputBean();
      BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));

      PoExpeditingProcess process = new PoExpeditingProcess(this.getDbUser(request),getTcmISLocaleString(request));
      String action = (String) ((DynaBean) form).get("action");
      Collection errorMsgs = null;
      // Search
      if (action != null && "search".equals(action)) {
          request.setAttribute("poExpediteViewBeanColl", process.getSearchResult(inputBean,personnelBean));
          this.saveTcmISToken(request);
          return (mapping.findForward("success"));
      }
     //  Create Excel
      else if (action != null && "createExcel".equals(action)) {
          this.setExcel(response, "PoExpediting List");
          process.getExcelReport(inputBean,personnelBean,
                  (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
          return noForward;
      }
     //  Update
      else if ("update".equals(action)) {
          if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("BuyOrder",null,null,null))
          {
              request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
              request.setAttribute("poExpediteViewBeanColl", process.getSearchResult(inputBean,personnelBean));
              return (mapping.findForward("success"));
          }

          checkToken(request);
          PoLineExpediteDateBean bean = new PoLineExpediteDateBean();
          Collection<PoLineExpediteDateBean> beans = BeanHandler.getBeans((DynaBean) form, "poLineExpediteDateBean", bean,getTcmISLocale(request));

          errorMsgs = process.update(beans,personnelBean);
          request.setAttribute("poExpediteViewBeanColl", process.getSearchResult(inputBean,personnelBean));
          request.setAttribute("tcmISErrors", errorMsgs);
          return (mapping.findForward("success"));

      }
      else if (action != null && "searchnewpoexpedite".equals(action)) {
    	  request.setAttribute("startSearchTime", new Date().getTime());
          request.setAttribute("newPoExpediteViewBeanColl", process.getNewPoExpediteSearchResult(inputBean));
          this.saveTcmISToken(request);
          request.setAttribute("endSearchTime", new Date().getTime() ); 
          return (mapping.findForward("shownewpoexpediting"));
      }
      else if (action != null && "updatenewpoexpedite".equals(action)) {
    	  request.setAttribute("startSearchTime", new Date().getTime()); 
    	  if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("BuyOrder",null,null,null))
          {
              request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
              request.setAttribute("newPoExpediteViewBeanColl", process.getNewPoExpediteSearchResult(inputBean));
              request.setAttribute("endSearchTime", new Date().getTime() ); 
              return (mapping.findForward("shownewpoexpediting"));
          }  
    	  checkToken(request);
          PoLineExpediteDateBean bean = new PoLineExpediteDateBean();
          Collection<PoLineExpediteDateBean> beans = BeanHandler.getBeans((DynaBean) form, "newPoExpediteBean", bean,getTcmISLocale(request));

          errorMsgs = process.update(beans,personnelBean);
          request.setAttribute("newPoExpediteViewBeanColl", process.getNewPoExpediteSearchResult(inputBean));
          request.setAttribute("tcmISErrors", errorMsgs);
          request.setAttribute("endSearchTime", new Date().getTime() ); 
          return (mapping.findForward("shownewpoexpediting"));
      }     
     else if ("showUploadList".equals(action)) 
     {
    	  this.saveTcmISToken(request);
     	  return mapping.findForward("showupload");
     }
     //  Upload Update List
     else if ("uploadList".equals(action)) {
            if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("BuyOrder",null,null,null))
            {
                request.setAttribute("errorMessage",getResourceBundleValue(request,"error.noaccesstopage"));
                return (mapping.findForward("showupload"));
            }

	            checkToken(request);
	          	FileUploadBean bean = new FileUploadBean();
	            BeanHandler.copyAttributes(form, bean);
	            //check if a file was uploaded
	            if(bean.getTheFile() != null && (!bean.getTheFile().getName().endsWith("xlsx") && !bean.getTheFile().getName().endsWith("xls")))
	            {
	            	errorMsgs = new Vector();
	            	errorMsgs.add("Invalid file");
	            }
	            else if(bean.getTheFile() != null)
	            	errorMsgs = process.uploadFile(bean, personnelBean);
	            
		 if (errorMsgs == null || errorMsgs.isEmpty())
				request.setAttribute("result", "ok");
		 else
			 	request.setAttribute("errorMessage", errorMsgs);   		

          this.saveTcmISToken(request);
       	  return mapping.findForward("showupload");

        }         
      else{
           this.saveTcmISToken(request);
           request.setAttribute("buyersCollection", process.getBuyerDropDown());
      }

      return (mapping.findForward(forward));
    }
}
