package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.TabletPickableUnitViewBean;
import com.tcmis.internal.hub.beans.PicklistReprintViewBean;
import com.tcmis.internal.hub.process.TabletPickingProcess;

/******************************************************************************
 * Controller for PicklistReprint
 * @version 1.0
 ******************************************************************************/
public class TabletPickableUnitViewAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
     throws BaseException, Exception {
      
      String forward = "success";
      if (!this.isLoggedIn(request)) {
         request.setAttribute("requestedPage", "tabletpickableunitviewmain");
         //This is to save any parameters passed in the URL, so that they
         //can be acccesed after the login
         request.setAttribute("requestedURLWithParameters",
         this.getRequestedURLWithParameters(request));
         return (mapping.findForward("login"));
      }

      String userAction = null;
		  if (form == null) {
			return (mapping.findForward("success"));
		  }

      PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
      TabletPickingProcess process = new TabletPickingProcess(this.getDbUser(request));
      
      
      String action = request.getParameter("action");
      PicklistReprintViewBean bean = new PicklistReprintViewBean();
      BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
      if ("assignPickingGroups".equals(action)) {
    	  Collection<TabletPickableUnitViewBean> pickingUnits = BeanHandler.getBeans((DynaBean) form, "picklistData", new TabletPickableUnitViewBean());
    	  process.assignPickingGroups(pickingUnits, personnelBean);
    	  action = "search";
      }
      if (bean.getPicklistId()!=null && action.equalsIgnoreCase("search")) {
         Collection<TabletPickableUnitViewBean> pickableUnits = process.getPickableUnits(bean);
         request.setAttribute("pickableUnitColl", pickableUnits);
         request.setAttribute("pickingGroupColl", process.getPickingGroups(bean));
         request.setAttribute("picklistId", bean.getPicklistId());
      }
      else if ( "createExcel".equals(action))   {
			this.setExcel(response, "Regenerated_PickList");
			process.getPickableUnitViewExcelReport(bean,(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
      }
      return mapping.findForward(forward);
   }
}
