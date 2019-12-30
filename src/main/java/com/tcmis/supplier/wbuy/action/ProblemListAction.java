/******************************************************
 * ProblemListAction.java
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


public class ProblemListAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
 
      // params
      String problem = null;
      String reject = null;
      
      String forward = "Success";
      String errMsg = null;

      // log.debug("in ProblemListAction");
      
      problem = (String) request.getParameter("problem");
      reject = (String) request.getParameter("reject");
      log.debug("got problem: " + problem);
      log.debug("got reject: " + reject);
      
      HttpSession session = request.getSession();     

      // get personnel bean
      PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
      if (personnel==null) { errMsg = "No personnel found."; log.error("No PersonnelBean!"); return mapping.findForward("Error"); }
   
      String client = this.getDbUser(request);;                 
      OrderingProcess orderingProcess = new OrderingProcess(client);
      
      Collection problemBeans = orderingProcess.getProbsRejects(personnel,problem,reject);
      
      if (problemBeans!=null) { 
         session.setAttribute("ProbRejectBeans",problemBeans);
      } else {
         forward = "Error";
      }
      
      return mapping.findForward(forward);
   }
   
}
