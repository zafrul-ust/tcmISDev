package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.POShipToInputBean;
//import com.tcmis.internal.supply.beans.ShipToLocationViewBean;

import com.tcmis.internal.supply.process.POShipToProcess;

/******************************************************************************
 * Controller for Purchase Order Supplier search page
 * @version 1.0
 ******************************************************************************/

public class LocationToIGAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,                 
                                      HttpServletResponse response) throws  BaseException, Exception {
/*                                         
      if (!this.isLoggedIn(request)) {
         request.setAttribute("requestedPage", "poshiptomain");
         request.setAttribute("requestedURLWithParameters",
         this.getRequestedURLWithParameters(request));
         return (mapping.findForward("login"));
      }
      
      PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
      BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
      
      request.setAttribute("valueElementId",request.getParameter("valueElementId"));
      request.setAttribute("displayElementId",request.getParameter("displayElementId"));
      PermissionBean permissionBean = personnelBean.getPermissionBean();
                /*
        if (! permissionBean.hasFacilityPermission("POShipTo", null, null))
        {
                return mapping.findForward("nopermissions");
        }
                 
      
      POShipToInputBean inputBean = new POShipToInputBean();
      BeanHandler.copyAttributes(form, inputBean);
      */
      String locationId = request.getParameter("locationId");                                         
      if (form == null) {
         return (mapping.findForward("success"));
      }
      
      POShipToProcess process = new POShipToProcess(this.getDbUser(request));
      
      Collection locationIGViewBeanCollection = process.getLocationToIgViewBeanCollection(locationId); 
         
      request.setAttribute("locationIGViewBeanCollection", locationIGViewBeanCollection);
         this.saveTcmISToken(request);
      
      
      return (mapping.findForward("success"));
   }
   
}
