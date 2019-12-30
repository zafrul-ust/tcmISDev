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
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.DBuyConsolidationFreqInputBean;
import com.tcmis.internal.supply.beans.DBuyConsolidationFreqViewBean;
import com.tcmis.internal.supply.process.DBuyConsolidationFreqProcess;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
     ******************************************************************************/
public class DBuyConsolidationFreqAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      Exception {
    if (!this.isLoggedIn(request,true)) {
      request.setAttribute("requestedPage", "dbuyconsolidationfreqmain");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    if ( !personnelBean.getPermissionBean().hasUserPagePermission("dBuyConsolidationFreq") )
    {
      return (mapping.findForward("nopermissions"));
    }

    DBuyConsolidationFreqInputBean bean = new DBuyConsolidationFreqInputBean();
    DBuyConsolidationFreqProcess process = new DBuyConsolidationFreqProcess(this.getDbUser(request),this.getTcmISLocaleString(request));

    if (form == null) {
        return (mapping.findForward("success"));
    }

    BeanHandler.copyAttributes(form, bean);
    String uAction = (String) ((DynaBean) form).get("uAction");
     if(uAction == null)  {
    	 return (mapping.findForward("success"));
     }
    if (uAction != null && "search".equals(uAction)) {
    	
    	request.setAttribute("vvSupplyPathBeanCollection", process.getSupplyPathType());
  	  // Pass the result collection in request
        request.setAttribute("dBuyConsFreqViewBeanColl", process.getSearchResult(bean,personnelBean));
        
        
        // save the token if update actions can be performed later.
        this.saveTcmISToken(request);
        return (mapping.findForward("success"));
    }
    else if ( uAction.equals("update")) {
        this.checkToken(request);
        if (!personnelBean.getPermissionBean().hasOpsEntityPermission("dBuyConsolidationFreq", bean.getOpsEntityId(), null)) {
			request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
			// repopulate the search results
			request.setAttribute("dBuyConsFreqViewBeanColl", process.getSearchResult(bean,personnelBean));
			return (mapping.findForward("success"));
		}
           request.setAttribute("startSearchTime", new Date().getTime() );
           
        Collection c = new Vector();
        DynaBean dynaBean = (DynaBean) form;
  			Collection beans = BeanHandler.getBeans(dynaBean,"dBuyConsFreqBean", new DBuyConsolidationFreqViewBean());
        Collection errorCollection = process.update(beans,personnelBean,bean);
        if (errorCollection.size() > 0) {
          request.setAttribute("tcmISErrors", errorCollection);
        }
        request.setAttribute("vvSupplyPathBeanCollection", process.getSupplyPathType());
  	  // Pass the result collection in request
        request.setAttribute("dBuyConsFreqViewBeanColl", process.getSearchResult(bean,personnelBean));
        return (mapping.findForward("success"));
    }
    else if ( uAction.equals("delete")) {
        this.checkToken(request);
        if (!personnelBean.getPermissionBean().hasOpsEntityPermission("dBuyConsolidationFreq", bean.getOpsEntityId(), null)) {
			request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
			// repopulate the search results
			request.setAttribute("dBuyConsFreqViewBeanColl", process.getSearchResult(bean,personnelBean));
			return (mapping.findForward("success"));
		}
           request.setAttribute("startSearchTime", new Date().getTime() );
           
        Collection c = new Vector();
        DynaBean dynaBean = (DynaBean) form;
  			Collection beans = BeanHandler.getBeans(dynaBean,"dBuyConsFreqBean", new DBuyConsolidationFreqViewBean());
        Collection errorCollection = process.delete(beans,personnelBean,bean);
        if (errorCollection.size() > 0) {
          request.setAttribute("tcmISErrors", errorCollection);
        }
        request.setAttribute("vvSupplyPathBeanCollection", process.getSupplyPathType());
  	  // Pass the result collection in request
        request.setAttribute("dBuyConsFreqViewBeanColl", process.getSearchResult(bean,personnelBean));
        
        return (mapping.findForward("success"));
    }
 
    else if ( "createExcel".equals(uAction)){
   	 this.setExcel(response, "DBuyConsolidationFreqExcel");
        process.getExcelReport( process.getSearchResult(bean,personnelBean), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        return noForward;
   }

    return (mapping.findForward("success"));
  }
}