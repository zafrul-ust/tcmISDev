package com.tcmis.internal.hub.action;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.RecipeIngredientSearchViewBean;
import com.tcmis.internal.hub.process.RecipeIngredientProcess;

/******************************************************************************
 * Controller for Recipe Ingredient search page
 * @version 1.0
	******************************************************************************/

public class RecipeIngredientAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "recipeingredientmain");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	PermissionBean permissionBean = personnelBean.getPermissionBean();
	/*if (! permissionBean.hasFacilityPermission("RecipeIngredient", null, null))
	{
	 return mapping.findForward("nopermissions");
	}*/

	if (form == null) {
	 request.setAttribute("catalogCompanyId",
		request.getParameter("catalogCompanyId"));
	 request.setAttribute("catalogId", request.getParameter("catalogId"));
	 return (mapping.findForward("success"));
	}

	RecipeIngredientProcess process = new RecipeIngredientProcess(this.getDbUser(request));
	RecipeIngredientSearchViewBean inputBean = new RecipeIngredientSearchViewBean();
	BeanHandler.copyAttributes(form, inputBean);

	if ( ( (DynaBean) form).get("submitSearch") != null &&
	 ( (String) ( (DynaBean) form).get("submitSearch")).length() > 0) {

	 Collection recipeIngredientSearchViewBeanCollection = process.getRecipeIngredientSearchViewBeanCollection(inputBean);
	 //Pass the result collection in request
	 request.setAttribute("recipeIngredientSearchViewBeanCollection",
		recipeIngredientSearchViewBeanCollection);

	 //this.saveTcmISToken(request);
	}
	return (mapping.findForward("success"));
 }
}