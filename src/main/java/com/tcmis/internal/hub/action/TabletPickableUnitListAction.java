package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.PicklistSelectionViewBean;
import com.tcmis.internal.hub.process.TabletPickingProcess;

/******************************************************************************
 * Controller for OpenPicklist
 * @version 1.0
 ******************************************************************************/
public class TabletPickableUnitListAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
     throws BaseException, Exception {
      
      String forward = "success";
      if (!this.isLoggedIn(request)) {
         request.setAttribute("requestedPage", "tabletpickableunitlistmain");
         //This is to save any parameters passed in the URL, so that they
         //can be acccesed after the login
         request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
         return (mapping.findForward("login"));
      }

		  if (form == null) {
			  return (mapping.findForward("success"));
		  }

      PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");

      PicklistSelectionViewBean inputBean = new PicklistSelectionViewBean();
	    BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

      if (inputBean.getHub() != null) {
        TabletPickingProcess process = new TabletPickingProcess(this.getDbUser(request),getTcmISLocaleString(request));

         Collection openPicklist = process.getOpenPicklist(inputBean);         
         request.setAttribute("openPicklistColl", openPicklist);
         // request.setAttribute("hub", hubParam);
      } else {
         request.setAttribute("openPicklistError","HUB required.");
      }
      
      return mapping.findForward(forward);
   }
}
