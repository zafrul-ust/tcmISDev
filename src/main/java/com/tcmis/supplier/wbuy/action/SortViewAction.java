/******************************************************
 * SortViewAction.java
 * 
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.wbuy.process.OrderingProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;


public class SortViewAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      // servlet params
      String field = null;
      String path = null;

      String forward = "Success";
   
      field = (String) request.getParameter("sortfield");
      path = (String) request.getParameter("path");
      // log.debug("got field: " + field);
      // log.debug("got path: " + path);
      
      HttpSession session = request.getSession();     

      String client = this.getDbUser(request);;           
      OrderingProcess orderProcess = new OrderingProcess(client);     
      
      // get the materials bean from the session (so we can computer total number of rows)
      Collection orderBeans = (Collection) session.getAttribute("OrderBeans");
      if (orderBeans!=null) { 
         orderBeans = orderProcess.sortOrders(orderBeans,field);             
         // set the session beans                                
         session.setAttribute("OrderBeans",orderBeans);
      }
      if (path.equalsIgnoreCase("problem")) { 
         forward = "PRSuccess";
      }
      
      return mapping.findForward(forward);
   }
   
}
