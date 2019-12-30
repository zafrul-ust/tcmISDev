  package com.tcmis.internal.distribution.action;

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

  import com.tcmis.internal.distribution.beans.AddSpecsBean;
  import com.tcmis.internal.distribution.process.NewDetailProcess;
 
  /******************************************************************************
   * Controller for CustomerAddressSearch
   * @version 1.0
   ******************************************************************************/
  public class ModifySpecDetailAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
    	
   	if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "modifyspecdetail");
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
    AddSpecsBean inputBean = new AddSpecsBean();
    BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

    NewDetailProcess process = new NewDetailProcess(this.getDbUser(request),getTcmISLocaleString(request));
    String action = request.getParameter("uAction");
    if (action != null && "search".equals(action)) {
    	request.setAttribute("specsBeanCollection", process.getSpecDetails(inputBean));
    	request.setAttribute("endSearchTime", new Date().getTime() );
		//this.saveTcmISToken(request);
		return (mapping.findForward("success"));
    }
    /*Since there is no search section we have to set end Time here as we cannot use the client side time.
    Client can be anywhere in the world.This should be done only for pages that have no search section.*/
    return (mapping.findForward(forward));
   }
  }