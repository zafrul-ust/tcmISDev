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
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.process.AllocationAnalysisProcess;
import com.tcmis.internal.hub.process.PicklistPickingProcess;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for OpenPicklist
 * @version 1.0
 ******************************************************************************/
public class OpenPicklistAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
     throws BaseException, Exception {
      
      String forward = "success";
      if (!this.isLoggedIn(request)) {
         request.setAttribute("requestedPage", "openpicklist");
         //This is to save any parameters passed in the URL, so that they
         //can be acccesed after the login
         request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
         return (mapping.findForward("login"));
      }

		  if (form == null) {
			  return (mapping.findForward("success"));
		  }

      PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
/*    PermissionBean permissionBean = personnelBean.getPermissionBean();     
      if (! permissionBean.hasInventoryGroupPermission("Picking",null,null,null)) {
         return mapping.findForward("nopermissions");
      }*/

      PicklistSelectionViewBean inputBean = new PicklistSelectionViewBean();
	    BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

      if (inputBean.getHub() != null) {
        PicklistPickingProcess process = new PicklistPickingProcess(this.getDbUser(request),getTcmISLocaleString(request));

         Collection openPicklist = process.getOpenPicklist(inputBean);         
         request.setAttribute("openPicklistColl", openPicklist);
         // request.setAttribute("hub", hubParam);
      } else {
         request.setAttribute("openPicklistError","HUB required.");
      }
/*      //if form is not null we have to perform some action
      if (form == null) {
         this.saveTcmISToken(request);
      }
      else if (form != null &&
              ( (DynaBean) form).get("submitSearch") != null &&
              ( (String) ( (DynaBean) form).get("submitSearch")).length() > 0) {

         // do something here
           
         // set the forward to something else        
         //forward = "picking";         
      }*/
      
      return mapping.findForward(forward);
   }
}
