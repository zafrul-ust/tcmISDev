  package com.tcmis.client.common.action;

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

  import com.tcmis.client.common.beans.CompanyMsdsLogBean;
import com.tcmis.client.common.process.PropertyLogProcess;
 
  /******************************************************************************
   * Controller for PropertyLogAction
   * @version 1.0
   ******************************************************************************/
  public class PropertyLogAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
    	
   	if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "propertylog");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
	}
		
   	request.setAttribute("startSearchTime", new Date().getTime() );
    String forward = "success";

    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    /*    if (!personnelBean.getPermissionBean().hasUserPagePermission("openPos"))
    {
          return (mapping.findForward("nopermissions"));
    }
*/
    CompanyMsdsLogBean inputBean = new CompanyMsdsLogBean();

    PropertyLogProcess process = new PropertyLogProcess(this.getDbUser(request),getTcmISLocaleString(request));
//    String action = request.getParameter("uAction");
    
//    if (action != null && "search".equals(action)) {
    	String materialId = request.getParameter("materialId");
        String revisionDate = request.getParameter("revisionDate");
        
        request.setAttribute("propertyLogColl",process.getSearchResult(materialId, revisionDate));
//    }
    /*Since there is no search section we have to set end Time here as we cannot use the client side time.
    Client can be anywhere in the world.This should be done only for pages that have no search section.*/
    request.setAttribute("endSearchTime", new Date().getTime() );
    return (mapping.findForward(forward));
   }
  }