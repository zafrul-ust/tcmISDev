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

import com.tcmis.common.admin.beans.PermissionBean;
  import com.tcmis.common.admin.beans.PersonnelBean;
  import com.tcmis.common.exceptions.BaseException;
  import com.tcmis.common.framework.TcmISBaseAction;
  import com.tcmis.common.util.BeanHandler;
  import com.tcmis.common.util.ResourceLibrary;

  import com.tcmis.client.common.process.SecondaryLabelInformationProcess;
  import com.tcmis.client.common.process.EditTypeProcess;
import com.tcmis.client.common.beans.TypeBean;
 
  /******************************************************************************
   * Controller for CustomerAddressSearch
   * @version 1.0
   ******************************************************************************/
  public class EditTypeAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
    	
   	if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "edittype");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
	}
		
   	request.setAttribute("startSearchTime", new Date().getTime() );
    String forward = "success";

    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    
    String facilityId = request.getParameter("facilityId");
    String uAction = request.getParameter("uAction");
    PermissionBean permissionBean = personnelBean.getPermissionBean();
    
    SecondaryLabelInformationProcess process = new SecondaryLabelInformationProcess(this.getDbUser(request),getTcmISLocaleString(request));
    if ("update".equals(uAction)) {
    	EditTypeProcess updateProcess = new EditTypeProcess(this.getDbUser(request),getTcmISLocaleString(request));
    	if (!permissionBean.hasFacilityPermission("secLblInfoAdmin", facilityId, "LOCKHEED")) {
			return mapping.findForward("nopermissions");
		}

		// If the page is being updated I check for a valid token.
		//checkToken will aslo save token for you to avoid duplicate form submissions.
		checkToken(request);

		// get the data from grid
		Collection<TypeBean> beans = BeanHandler.getBeans((DynaBean) form, "typeBean", new TypeBean(), getTcmISLocale(request));

		Collection errorMsgs = updateProcess.updateTypes(personnelId,facilityId,beans);

		// After update the data, we do the re-search to refresh the window
		request.setAttribute("typeNameColl", process.getTypeNameDropdowns(facilityId));
		if(errorMsgs != null && errorMsgs.size() > 0)
			request.setAttribute("tcmISErrors", errorMsgs);
		else {
			request.setAttribute("updated", "Y");
		}
    }
    else {
    	request.setAttribute("typeNameColl", process.getTypeNameDropdowns(facilityId));
    	this.saveTcmISToken(request);
    }
    /*Since there is no search section we have to set end Time here as we cannot use the client side time.
    Client can be anywhere in the world.This should be done only for pages that have no search section.*/
    request.setAttribute("endSearchTime", new Date().getTime() );
    return (mapping.findForward(forward));
   }
  }