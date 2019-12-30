package com.tcmis.internal.hub.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:04:03 PM
 * To change this template use File | Settings | File Templates.
 */
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.PolchemNsnVerificationBean;
import com.tcmis.internal.hub.process.ItemWeightProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Vector;
import java.math.BigDecimal;

/******************************************************************************
 * Controller for FreightAdvice
 * @version 1.0
	******************************************************************************/

public class ItemWeightAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "itemweightmain");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

  //If you need access to who is logged in
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	/*Need to check if the user has permissions to view this page. if they do not have the permission
	we need to not show them the page.*/
  if (!personnelBean.getPermissionBean().hasUserPagePermission("itemWeights"))
  {
    return (mapping.findForward("nopermissions"));
  }

  if (form == null) 	return (mapping.findForward("success"));

	 ItemWeightProcess process = new ItemWeightProcess(this.getDbUser(request),getTcmISLocaleString(request));
	 PolchemNsnVerificationBean inputBean = new PolchemNsnVerificationBean();
	 BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
	 String action = (String) ( (DynaBean) form).get("action");
	 
	 if ( ( (DynaBean) form).get("submitSearch") != null &&
		( (String) ( (DynaBean) form).get("submitSearch")).length() > 0
		|| (
				( (DynaBean) form).get("action") != null &&
	            ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("search")	)
		) {
        request.setAttribute("itemWeightCollection",  process.getSearchData(inputBean));
    }
    else if ( ( (DynaBean) form).get("action") != null &&
            ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("update") ) {
    		Vector v = (Vector) BeanHandler.getBeans( (DynaBean) form, "PolchemNsnVerificationBean",
    				new PolchemNsnVerificationBean(), getTcmISLocale(request));
    		request.setAttribute("tcmISError",process.update(v));
            request.setAttribute("itemWeightCollection",  process.getSearchData(inputBean));
   	 }
	return (mapping.findForward("success"));
	}
}

