package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ExcessInventoryViewBean;
import com.tcmis.internal.hub.process.ExcessInventoryProcess;

public class ExcessInventoryAction extends TcmISBaseAction {
   public ActionForward executeAction(ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws BaseException, Exception {      
      if (!this.isLoggedIn(request)) {
         request.setAttribute("requestedPage", "excessinventorymain");
         //This is to save any parameters passed in the URL, so that they
         //can be acccesed after the login
         request.setAttribute("requestedURLWithParameters",
         this.getRequestedURLWithParameters(request));
         return (mapping.findForward("login"));
      }
      
      PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
      if (!personnelBean.getPermissionBean().hasUserPagePermission("excessInventory"))
      {
        return (mapping.findForward("nopermissions"));
      }
      
      int totalLines=0;
      ExcessInventoryViewBean excessInventoryInputBean = new ExcessInventoryViewBean();
     
      BeanHandler.copyAttributes(form, excessInventoryInputBean);      
      String action = (String) ( (DynaBean) form).get("action");
      //log.debug("got action: " + action);
      /* **no need to check tcmISToken since this is search only
       */

      if (form != null && action != null && action.length() > 0) {

         ExcessInventoryProcess eiProcess = new ExcessInventoryProcess(this.getDbUser(request));
         if (action.equalsIgnoreCase("search")) {
            String errorMessage = "";        
            Collection results = eiProcess.getSearchData(excessInventoryInputBean,personnelBean);
            
            if (results != null) {
               request.setAttribute("excessInventoryViewBeanCollection", results);
               totalLines = results.size();
               request.setAttribute("totalLines", totalLines+"");               
               this.saveTcmISToken(request);               
            }
            return (mapping.findForward("success"));
         }           
         else if (action.equalsIgnoreCase("createExcel") ) {
            this.setExcel(response,"ExcessInventory");
            try {
               eiProcess.getExcelReport(excessInventoryInputBean,
                                        eiProcess.getSearchData(excessInventoryInputBean,personnelBean),
                                        personnelBean.getLocale()).write(response.getOutputStream());
            } catch (Exception ex) {
               ex.printStackTrace();
               return mapping.findForward("genericerrorpage");
            }
            return noForward;
         }
      }      
               
      request.setAttribute("totalLines", totalLines+"");      
      return mapping.findForward("success");
   } //end of method
} //end of class