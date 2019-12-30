/******************************************************
 * ProblemAction.java
 * 
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.wbuy.beans.PoHeaderViewBean;
import com.tcmis.supplier.wbuy.process.OrderingProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ProblemAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      // servelet params
      String reason = null;
      String action = null;
      String cancel = null;
      
      String forward = "Success";
      String errMsg = null;
   
      // get parameters
      reason = (String) request.getParameter("Reason");
      //log.debug("got reason: " + reason);
      action = (String) request.getParameter("action");
      //log.debug("got action: " + action);
      cancel = (String) request.getParameter("cancel");
      //log.debug("got cancel:" + cancel);
      
      HttpSession session = request.getSession();     
            
      // **begin* get SESSION beans
                  
      // get personnel bean
      PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
      if (personnel==null) { errMsg = "No personnel found."; log.error("No PersonnelBean!"); }
                  
      // get the header bean frrom the session (for supplier)
      PoHeaderViewBean poHeaderBean = (PoHeaderViewBean) session.getAttribute("POHeaderBean");
      if (poHeaderBean==null) { errMsg = "No Header found!"; log.error("No POHeaderBean!"); }
      
      // get orders list (to update it if confirmed or saved)
      /*Collection orderBeans = (Collection) session.getAttribute("OrderBeans");
      if (orderBeans==null) { errMsg = "Orders List not found"; }*/
      // **end* get session beans
      
      String client = this.getDbUser(request);;              
      OrderingProcess orderProcess = new OrderingProcess(client);
      String confirmedStatus = null;

      if (action!=null && errMsg==null) {
         if (action.equalsIgnoreCase("REJECT")) {
            String rejResult = orderProcess.rejectPO(poHeaderBean, personnel.getPersonnelId(),reason);
            if (rejResult!=null) {
               errMsg = "Error rejecting PO";
            } else { 
               confirmedStatus = orderProcess.STATUS_REJ; 
            }
         } else if (action.equalsIgnoreCase("PROBLEM")) {
            String probResult = orderProcess.problemPO(poHeaderBean, personnel.getPersonnelId(),reason);            
            if (probResult!=null) {
               errMsg = "Error setting PO to problem";
            } else { 
               confirmedStatus = orderProcess.STATUS_PROB; 
               // email buyer on record                             
               /*String subject = "WBUY: PO " + poHeaderBean.getRadianPo() + " is now in Problem WBuy status.";
               String msg = "WBuy PO " + poHeaderBean.getRadianPo() + " has been set to problem status.\n";
               msg += "The following reason was provided: " + reason;*/
               //MailProcess.sendEmail(poHeaderBean.getBuyerEmail(),null,"wbuybot@tcmis.com", subject, msg);                  
            }
         }
      }
      
      //log.debug("setting session attributes (POProblem)");                       
      session.removeAttribute("POProblemMsg");
      if (errMsg!=null) {
         session.setAttribute("POErrorMsgBean",errMsg);
         forward = "Error";
      }
      if (confirmedStatus!=null) {
         session.setAttribute("ConfirmedBean",confirmedStatus);
         session.setAttribute("StatusBean",confirmedStatus);
         session.setAttribute("CommentsBean",reason);
         // change the order bean
         /*BeanHandler.updateBean(orderBeans, "radian_po", poHeaderBean.getRadianPo(), "dbuy_status", confirmedStatus);
         session.setAttribute("OrderBeans",orderBeans);*/
      }
                        
      return mapping.findForward(forward);
   }
   
}
