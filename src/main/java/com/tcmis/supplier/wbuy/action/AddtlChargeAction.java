/******************************************************
 * AddtlChargeAction.java
 * 
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.wbuy.process.OrderingProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;


public class AddtlChargeAction extends TcmISBaseAction {
   
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      // servelet params
      String invgrp = null;
       
      String forward = "Success";   
      String errMsg = null;

      invgrp = (String) request.getParameter("invgrp");
      //log.debug("got invgrp: " + invgrp);
      
      HttpSession session = request.getSession();     
            
      // get personnel bean
      PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
      if (personnel==null) { errMsg = "No personnel found."; }
      
      // get database client (so we can get a connection)
      String client = this.getDbUser(request);;                 
      OrderingProcess orderProcess = new OrderingProcess(client);
      
      //log.debug("creating additional charge list");      
      String choice = "open";
      Collection addchargeBeans = orderProcess.listCharges(invgrp);      
      if (addchargeBeans==null) {         
         forward = "Error";      
         errMsg = "Error reading additional charges";
      }
              
      // set session attributes
      session.setAttribute("AddtlChargeBeans",addchargeBeans);                 
      if (errMsg!=null) {
         session.setAttribute("POErrorMsgBean",errMsg);
      } else {
         session.removeAttribute("POErrorMsgBean");
      }
      
      return mapping.findForward(forward);
   }
   
}
