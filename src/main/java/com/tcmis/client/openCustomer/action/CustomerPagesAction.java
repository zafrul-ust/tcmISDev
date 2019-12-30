  package com.tcmis.client.openCustomer.action;

  import java.math.BigDecimal;
  import java.util.Collection;
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

  import com.tcmis.client.openCustomer.beans.UserCustomerAdminViewBean;
  import com.tcmis.client.openCustomer.process.UserCustomerAdminViewProcess;

  /******************************************************************************
   * Controller for CompanyPages
   * @version 1.0
   ******************************************************************************/
  public class CustomerPagesAction 
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
  	if (!this.isLoggedIn(request)) {
  		  request.setAttribute("requestedPage", "customerpermissions".toLowerCase());
  		  request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
  		  return (mapping.findForward("login"));
      }

	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	BigDecimal pid = new BigDecimal(personnelBean.getPersonnelId()); 	//modifier
  	request.setAttribute("personnelId", request.getParameter("personnelId"));	//modifiee
  	request.setAttribute("userId", pid );	            						//modifier
  	String action =  (String)( (DynaBean) form).get("action");			//
  	if(action == null ) return mapping.findForward("success");			// should not happen

  	UserCustomerAdminViewProcess process = new UserCustomerAdminViewProcess(this.getDbUser(request),getTcmISLocaleString(request));	    

//  main
    if ( action == null || action.equals("init")) {
    	String fullName = request.getParameter("fullName");
    	request.setAttribute("fullName", request.getParameter("fullName"));
    	request.getSession().setAttribute("MODIFIEEFULLNAME",fullName);
    	DynaBean dynaBean = (DynaBean) form;
    	UserCustomerAdminViewBean bean = new UserCustomerAdminViewBean();
    	BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
    	bean.setUserId(pid);
        request.setAttribute("UserCustomerAdminViewBeanCollection", process.getSearchResult(bean));
    }
    else if ( action.equals("createXSL")) {
    	DynaBean dynaBean = (DynaBean) form;
    	UserCustomerAdminViewBean bean = new UserCustomerAdminViewBean();
    	BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
    	bean.setUserId(pid);
    	
    	this.setExcel(response, "CustomerPermissionExcel");
        process.getExcelReport(process.getSearchResult(bean), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        return noForward;
    }
// result update
    else if ( action.equals("update")) {
  		Collection c = new Vector();
  	
		DynaBean dynaBean = (DynaBean) form;
		Collection<UserCustomerAdminViewBean> beans = BeanHandler.getBeans(dynaBean, "customerViewBean",new UserCustomerAdminViewBean(), getTcmISLocale(request));
		String errorMsg = "";
		for(UserCustomerAdminViewBean bean:beans) {
			if( bean.getModified().equalsIgnoreCase("Y")) {
				bean.setUserId(pid);
				bean.setPersonnelId(new BigDecimal( (String) request.getParameter("personnelId")));
				errorMsg += process.updateValue(bean);
			}
		}
		UserCustomerAdminViewBean bean = new UserCustomerAdminViewBean();
      	BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
    	bean.setUserId(pid);

        request.setAttribute("tcmISError", errorMsg );
        request.setAttribute("UserCustomerAdminViewBeanCollection", process.getSearchResult(bean) ); 
      }
    
      return mapping.findForward("success");
    }
  }