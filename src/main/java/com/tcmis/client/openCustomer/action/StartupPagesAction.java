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
  import com.tcmis.common.util.StringHandler;

  import com.tcmis.common.admin.beans.UserPageAdminViewBean;
  import com.tcmis.client.openCustomer.process.UserPageAdminDataProcess;

  /******************************************************************************
   * Controller for startUpPages
   * @version 1.0
   ******************************************************************************/
  public class StartupPagesAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
  	if (!this.isLoggedIn(request)) {
  		  request.setAttribute("requestedPage", "startuppagesmain");
  		  request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
  		  return (mapping.findForward("login"));
      }

  	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	BigDecimal pid = new BigDecimal(personnelBean.getPersonnelId()); 			//modifier
  	request.setAttribute("personnelId", request.getParameter("personnelId"));	//modifiee
  	request.setAttribute("userId", pid );	            						//modifier
  	request.setAttribute("fullName", request.getParameter("fullName") );	            						//modifier
  	UserPageAdminDataProcess process = new UserPageAdminDataProcess(this.getDbUser(request),getTcmISLocaleString(request));
  	String action = (String) ( (DynaBean) form).get("action");
//main
    if ( action == null || action.equals("init") || action.equals("display")) {
    	String fullName = request.getParameter("fullName");
    	request.setAttribute("fullName", request.getParameter("fullName"));
    	request.getSession().setAttribute("MODIFIEEFULLNAME",fullName);
    	DynaBean dynaBean = (DynaBean) form;
    	UserPageAdminViewBean bean = new UserPageAdminViewBean();
    	BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
    	bean.setUserId(pid);
        request.setAttribute("UserPageAdminViewCollection",
                              process.getDistinctStartupSearchResult(bean));
    }
    else if ( action.equals("createXSL")) {
    	this.setExcel(response,"StartupPages");
		try {
	    	DynaBean dynaBean = (DynaBean) form;
	    	UserPageAdminViewBean bean = new UserPageAdminViewBean();
	    	BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
	    	bean.setUserId(pid);
	        request.setAttribute("UserPageAdminViewCollection",
	                              process.getDistinctStartupSearchResult(bean));
			process.getStartupExcelReport(bean,process.getDistinctStartupSearchResult(bean), 
					personnelBean.getLocale(),(String)request.getSession().getAttribute("MODIFIEEFULLNAME")).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
    }
// result update
    else {/* set up search page data*/
  		Collection c = new Vector();
		DynaBean dynaBean = (DynaBean) form;
		Collection<UserPageAdminViewBean> beans = BeanHandler.getBeans(dynaBean, "UserPageAdminViewBean",new UserPageAdminViewBean(), getTcmISLocale(request));
		String errorMsg = "";
		for(UserPageAdminViewBean bean:beans) {
			bean.setModified("N");
			if (!bean.getStrStartPageOrder().equals(bean.getOldPageOrder())) {
				bean.setModified("Y");
			}
			if (!bean.getStartPage().equals(bean.getOldStartPage())) {
				bean.setModified("Y");
			} 
			if (bean.getModified().equals("Y")) {
				Double startPageDouble = null;
				if (bean.getStrStartPageOrder().length() > 0) {
					try {
						startPageDouble = new Double(bean.getStrStartPageOrder());
					} catch(NumberFormatException ne) {
						errorMsg = "The start page order for page " + bean.getPageName() +" must be a positive integer.";
						break;
					}
					bean.setStartPageOrder(new BigDecimal(startPageDouble.doubleValue()).abs());			
				} else {
					bean.setStartPageOrder(null);			
				}				
				bean.setUserId(pid);
				try {
					if("Y".equals(bean.getModified()))
			//		if((!bean.getOldStartPage().equalsIgnoreCase(bean.getStartPage())) && (!StringHandler.isBlankString(bean.getOldStartPage()))  )
					{	
					errorMsg += process.updateValue(bean);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					request.setAttribute("tcmISError", e.toString() );
				}				
			}
		}
    	UserPageAdminViewBean bean = new UserPageAdminViewBean();
    	BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
    	bean.setUserId(pid);
        request.setAttribute("tcmISError", errorMsg );
        request.setAttribute("UserPageAdminViewCollection", process.getDistinctStartupSearchResult(bean) );
      }
   	return mapping.findForward("success");
   }
  }