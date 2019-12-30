  package com.tcmis.client.openCustomer.action;

  import java.math.BigDecimal;
  import java.util.Collection;
  import java.util.Date;
  import java.util.Vector;

  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  import javax.servlet.http.HttpSession;

  import org.apache.commons.beanutils.DynaBean;
  import org.apache.struts.action.ActionForm;
  import org.apache.struts.action.ActionForward;
  import org.apache.struts.action.ActionMapping;

  import com.tcmis.common.admin.beans.PersonnelBean;
  import com.tcmis.common.exceptions.BaseException;
  import com.tcmis.common.framework.TcmISBaseAction;
  import com.tcmis.common.util.BeanHandler;
  import com.tcmis.common.util.ResourceLibrary;

  import com.tcmis.client.openCustomer.process.UserProfileProcess;
 
  /******************************************************************************
   * Controller for CustomerAddressSearch
   * @version 1.0
   ******************************************************************************/
  public class DefaultCustomerAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
    	
   	if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "defaultcustomer");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
	}
		
   	request.setAttribute("startSearchTime", new Date().getTime() );

    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    
    UserProfileProcess process = new UserProfileProcess(getDbUser(request), getTcmISLocaleString(request));
    request.getSession().setAttribute("defaultCustomerColl", process.getDefaultCustomerColl(""+personnelBean.getPersonnelId()));
    request.setAttribute("endSearchTime", new Date().getTime() );
    return (mapping.findForward("success"));
   }
  }