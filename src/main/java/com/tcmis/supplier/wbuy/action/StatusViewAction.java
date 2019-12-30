/******************************************************
 * SortItemsAction.java
 * 
 * Sort the collection of items based in the given field.
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.wbuy.process.StatusProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;


public class StatusViewAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      // servlet params
      String field = null;
      String path = null;

      String forward = "success";
   
      HttpSession session = request.getSession();     

      //log.debug("in status view action");
      String client = this.getDbUser(request);
      StatusProcess statusProcess = new StatusProcess(client);     
      
      // get the materials bean from the session (so we can computer total number of rows)
      Collection statusBeans = statusProcess.getWbuyStatusBeans();                  
      if (statusBeans!=null) {
         //log.debug("got status beans");
         // set the session beans                                
         session.setAttribute("WbuyStatusBeans",statusBeans);
      } else {
         log.error("status beans are null!");
      }
      
      return mapping.findForward(forward);
   }
   
}
