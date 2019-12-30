package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.process.RecipeProcess;

import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
     ******************************************************************************/
public class RecipeAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "recipemain");
      //This is to save any parameters passed in the URL, so that they
      //can be acccesed after the login
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    RecipeInputBean bean = new RecipeInputBean();
    if (form != null) {
        BeanHandler.copyAttributes(form, bean);
    }
      
    bean.setPersonnelId(new BigDecimal(this.getPersonnelId(request)));
    String forward = "success";
    RecipeProcess process = new RecipeProcess(this.getDbUser(request));
    //if form is not null we have to perform some action
    if (form == null) {
      
    }
    //if form is not null we have to perform some action
    else if (form != null &&
             ( (DynaBean) form).get("submitSearch") != null &&
             ( (String) ( (DynaBean) form).get("submitSearch")).length() > 0) {
log.debug("search");
      request.setAttribute("recipeViewBeanCollection",
                           process.getSearchData(bean));
    }
    else if (form != null &&
             ( (DynaBean) form).get("action") != null &&
             ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("detail")) {
log.debug("detail");
      request.setAttribute("recipeViewBeanCollection", process.getSearchData(bean));
      request.setAttribute("recipeIngredientViewBeanCollection", process.getIngredientDetail(bean));
      request.setAttribute("recipeTestParameterViewBeanCollection", process.getTestParameterDetail(bean));
      request.setAttribute("recipeUserGroupOvBeanCollection", process.getOwnerCatalogDropdown(new BigDecimal(((PersonnelBean)this.getSessionObject(request, "personnelBean")).getPersonnelId())));
      request.setAttribute("vvMeasurementUnitBeanCollection", process.getVvMeasurementUnitDefault());
      request.setAttribute("vvRecipeTestParameterBeanCollection", process.getVvRecipeTestParameterDropDown());
      request.setAttribute("vvSizeUnitBeanCollection", process.getVvSizeUnitDropDown());
    }
    else if (form != null &&
             ( (DynaBean) form).get("action") != null &&
             ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("new")) {
log.debug("new");
      request.setAttribute("recipeUserGroupOvBeanCollection", process.getOwnerCatalogDropdown(new BigDecimal(((PersonnelBean)this.getSessionObject(request, "personnelBean")).getPersonnelId())));
      request.setAttribute("vvMeasurementUnitBeanCollection", process.getVvMeasurementUnitDefault());
      request.setAttribute("vvRecipeTestParameterBeanCollection", process.getVvRecipeTestParameterDropDown());
      request.setAttribute("vvSizeUnitBeanCollection", process.getVvSizeUnitDropDown());
    }
    else if(form != null &&
             ( (DynaBean) form).get("action") != null &&
             ( ((String) ( (DynaBean) form).get("action")).equalsIgnoreCase("save") ||
               ((String) ( (DynaBean) form).get("action")).equalsIgnoreCase("approve"))) {

       //get ingredients
      Collection ingredientCollection = new Vector();
      DynaBean dynaBean = (DynaBean) form;
      List ingredientInputBeans = (List) dynaBean.get("ingredientInputBean");
      if (ingredientInputBeans != null) {
        Iterator iterator = ingredientInputBeans.iterator();
        while (iterator.hasNext()) {
          RecipeInputBean inputBean = new RecipeInputBean();
          org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
              commons.beanutils.LazyDynaBean) iterator.next();
          BeanHandler.copyAttributes(lazyBean, inputBean);
          //inputBean.setPersonnelId(new BigDecimal(this.getPersonnelId(request)));
          ingredientCollection.add(inputBean);
        }
      }
      //get test parameters
      Collection testParameterCollection = new Vector();
      List testParameterInputBeans = (List) dynaBean.get("testParameterInputBean");
      if (testParameterInputBeans != null) {
        Iterator iterator = testParameterInputBeans.iterator();
        while (iterator.hasNext()) {
          RecipeInputBean inputBean = new RecipeInputBean();
          org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
              commons.beanutils.LazyDynaBean) iterator.next();
          BeanHandler.copyAttributes(lazyBean, inputBean);
          //inputBean.setPersonnelId(new BigDecimal(this.getPersonnelId(request)));
          testParameterCollection.add(inputBean);
        }
      }
      process.saveRecipe(bean, ingredientCollection, testParameterCollection);
      request.setAttribute("recipeViewBeanCollection", process.getSearchData(bean));
      request.setAttribute("recipeIngredientViewBeanCollection", process.getIngredientDetail(bean));
      request.setAttribute("recipeTestParameterViewBeanCollection", process.getTestParameterDetail(bean));
      request.setAttribute("recipeUserGroupOvBeanCollection", process.getOwnerCatalogDropdown(new BigDecimal(((PersonnelBean)this.getSessionObject(request, "personnelBean")).getPersonnelId())));
      request.setAttribute("vvMeasurementUnitBeanCollection", process.getVvMeasurementUnitDefault());
      request.setAttribute("vvRecipeTestParameterBeanCollection", process.getVvRecipeTestParameterDropDown());
      request.setAttribute("vvSizeUnitBeanCollection", process.getVvSizeUnitDropDown());
    }
    else if (form != null &&
             ( (DynaBean) form).get("action") != null &&
             ( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("newversion")) {
      request.setAttribute("recipeViewBeanCollection", process.getSearchData(bean));
      request.setAttribute("recipeIngredientViewBeanCollection", process.getIngredientDetail(bean));
      request.setAttribute("recipeTestParameterViewBeanCollection", process.getTestParameterDetail(bean));
      request.setAttribute("recipeUserGroupOvBeanCollection", process.getOwnerCatalogDropdown(new BigDecimal(((PersonnelBean)this.getSessionObject(request, "personnelBean")).getPersonnelId())));
      request.setAttribute("vvMeasurementUnitBeanCollection", process.getVvMeasurementUnitDefault());
      request.setAttribute("vvRecipeTestParameterBeanCollection", process.getVvRecipeTestParameterDropDown());
      request.setAttribute("vvSizeUnitBeanCollection", process.getVvSizeUnitDropDown());
    }
    else {
log.debug("init");
      //this is the initial load of the seach section
      request.setAttribute("recipeUserGroupOvBeanCollection", process.getOwnerCatalogDropdown(new BigDecimal(((PersonnelBean)this.getSessionObject(request, "personnelBean")).getPersonnelId())));
    }

    return mapping.findForward(forward);
  }
}