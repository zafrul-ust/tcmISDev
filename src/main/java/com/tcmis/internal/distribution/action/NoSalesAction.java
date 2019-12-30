  package com.tcmis.internal.distribution.action;

  import java.math.BigDecimal;
  import java.util.Collection;
  import java.util.Vector;

  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  import javax.servlet.http.HttpSession;

  import org.apache.commons.beanutils.DynaBean;
  import org.apache.struts.action.ActionForm;
  import org.apache.struts.action.ActionForward;
  import org.apache.struts.action.ActionMapping;

  import com.tcmis.common.admin.beans.PermissionBean;
  import com.tcmis.common.admin.beans.PersonnelBean;
  import com.tcmis.common.exceptions.BaseException;
  import com.tcmis.common.framework.TcmISBaseAction;
  import com.tcmis.common.util.BeanHandler;
  import com.tcmis.common.util.ResourceLibrary;

  import com.tcmis.internal.distribution.beans.NoSalesViewBean;
  import com.tcmis.internal.distribution.process.NoSalesProcess;
  import com.tcmis.internal.distribution.beans.NoSalesInputBean;


  /******************************************************************************
   * Controller for CustomerAddressSearch
   * @version 1.0
   ******************************************************************************/
  public class NoSalesAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

    	if (!this.isLoggedIn(request)) {
    	      request.setAttribute("requestedPage", "nosalesmain");
    				request.setAttribute("requestedURLWithParameters",
    											 this.getRequestedURLWithParameters(request));
    	      return (mapping.findForward("login"));
    	}
    	
    	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    	if (!personnelBean.getPermissionBean().hasUserPagePermission("noSales"))
  	    {
  	    return (mapping.findForward("nopermissions"));
  	    }
    	
    	
    	NoSalesInputBean inputBean = new NoSalesInputBean();
    	NoSalesProcess process = new NoSalesProcess(this.getDbUser(request),this.getTcmISLocale(request));

    	    if (form == null) return (mapping.findForward("success"));
    	
    	    BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
    	    String action =  (String)( (DynaBean) form).get("uAction");			//
    	    if ( "search".equals(action) ) {
    	    	request.setAttribute("noSalesViewCollection", process.getSearchResult(personnelBean,inputBean));
    	    }
    	    else if ( "createExcel".equals(action) ) {  	    	
    				this.setExcel(response,"NoSales");
    				process.getExcelReport(process.getSearchResult(personnelBean,inputBean)).write(response.getOutputStream());  			
    			return noForward;
    	    }
    	   
    	    return (mapping.findForward("success"));
    	}
  }