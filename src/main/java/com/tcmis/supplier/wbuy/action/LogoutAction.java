/******************************************************
 * LogoutAction.java
 * 
 * Logout the current user (remove all memory Beans)
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


public class LogoutAction extends TcmISBaseAction {
   
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      String forward = "Success";   
      String errMsg = null;    
      
      //log.debug("in logout action");
      
      HttpSession session = request.getSession();     
      
      // cleanup of previous session beans
      session.removeAttribute("ConfirmedBean");
      session.removeAttribute("StatusBean");
      session.removeAttribute("CommentsBean");
      session.removeAttribute("POHeaderBean");
      session.removeAttribute("personnelBean");
      session.removeAttribute("MaterialBeans");
      session.removeAttribute("AddChargeBeans");
      session.removeAttribute("DBClient");
      session.removeAttribute("POErrorMsgBean");
      session.removeAttribute("PORedirURL");
      session.removeAttribute("TargetPO");
      session.removeAttribute("POProblemReason");
      session.removeAttribute("OrderBeans");
      session.removeAttribute("AddtlChargeBeans");
      session.removeAttribute("ProbRejectBeans");
      
      // navigation beans
      session.removeAttribute("ShowPO");
      session.removeAttribute(OrderingProcess.NB_STAT_NEW);
      session.removeAttribute(OrderingProcess.NB_STAT_ACK);
      session.removeAttribute(OrderingProcess.NB_STAT_PROB);
      session.removeAttribute(OrderingProcess.NB_STAT_RES);
      session.removeAttribute(OrderingProcess.NB_STAT_CONF);
      session.removeAttribute(OrderingProcess.NB_STAT_REJ);
      session.removeAttribute("NumDays");
      session.removeAttribute("POSearch");
      session.removeAttribute("ProblemOrReject");

      return mapping.findForward(forward);
   }
   
}
