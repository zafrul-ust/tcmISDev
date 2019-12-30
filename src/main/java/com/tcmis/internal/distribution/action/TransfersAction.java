  package com.tcmis.internal.distribution.action;

  import java.util.Collection;
import java.util.List;

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
import com.tcmis.internal.distribution.beans.TransferRequestReportViewBean;
import com.tcmis.internal.distribution.beans.TransfersInputBean;
import com.tcmis.internal.distribution.process.TransfersProcess;

  /******************************************************************************
   * Controller for CustomerAddressSearch
   * @version 1.0
   ******************************************************************************/
  public class TransfersAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

    	if (!this.isLoggedIn(request)) {
    	      request.setAttribute("requestedPage", "transfersmain");
    				request.setAttribute("requestedURLWithParameters",
    											 this.getRequestedURLWithParameters(request));
    	      return (mapping.findForward("login"));
    	}
    	
    	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    	if (!personnelBean.getPermissionBean().hasUserPagePermission("transfers"))
  	    {
  	    return (mapping.findForward("nopermissions"));
  	    }
    
    	
    	TransfersInputBean inputBean = new TransfersInputBean();
    	TransfersProcess process = new TransfersProcess(this.getDbUser(request),this.getTcmISLocale(request));

    	    if (form == null) return (mapping.findForward("success"));
    	
    	    BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
    	    String action =  (String)( (DynaBean) form).get("uAction");			//
    	    if ( "search".equals(action) ) {
    	    	Collection c =  process.getSearchResult(personnelBean,inputBean);
    	    	request.setAttribute("transferRequestReportViewCollection", c);
    	    }
    	    else if ( "createExcel".equals(action) ) {  	    	
    				this.setExcel(response,"Transfers");
    				process.getExcelReport(process.getSearchResult(personnelBean,inputBean)).write(response.getOutputStream());  			
    			return noForward;
    	    }
    	    else if( "releaseTransferRequest".equals(action) ) {  	
    	    	Collection<TransferRequestReportViewBean> beans = BeanHandler.getBeans((DynaBean) form, "transferRequestReportViewBean", new TransferRequestReportViewBean(), getTcmISLocale(), "ok");
    	    	String err = null;
    	    	Collection errColl = process.releaseTransferRequest(beans,personnelBean.getPersonnelId());
    	    	if(errColl != null)
    	    		err = getResourceBundleValue(request, "error.db.update");
    	    	request.setAttribute("tcmISError", err);
    	    	Collection c =  process.getSearchResult(personnelBean,inputBean);

    	    	request.setAttribute("transferRequestReportViewCollection", c);
    	    }
    	    else if( "closeTransfer".equals(action) ) {  	
    	    	Collection<TransferRequestReportViewBean> beans = BeanHandler.getBeans((DynaBean) form, "transferRequestReportViewBean", new TransferRequestReportViewBean(), getTcmISLocale(), "okClose");
    	    	String err = null;
    	    	List errColl = process.closeTransfer(beans, personnelBean);
    	    	if(errColl != null)
    	    		err = getResourceBundleValue(request, "error.db.update");
    	    	request.setAttribute("tcmISError", err);
    	    	Collection c =  process.getSearchResult(personnelBean,inputBean);

    	    	request.setAttribute("transferRequestReportViewCollection", c);
    	    }
    	    else {
    	    	Collection allOpsHubIgOvBeanCollection = process.getAllOpsHubIgData(personnelBean);
    	    	request.setAttribute("allOpsHubIgOvBeanCollection", allOpsHubIgOvBeanCollection);
    	    }
    	   
    	    return (mapping.findForward("success"));
    	}
  }