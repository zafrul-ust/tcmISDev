/******************************************************
 * DailyConfirmedEbuysAction.java
 *
 * Display a page with all dbuy and wbuy orders confirmed in the last 24 hours
 ******************************************************
 */
package com.tcmis.supplier.dbuy.action;

import com.tcmis.supplier.dbuy.process.SupportProcess;
import com.tcmis.supplier.dbuy.beans.DbuyAcknowledgementBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class DailyConfirmedEbuysAction extends TcmISBaseAction {
   
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      // session beans (in/out)
      Collection ebuys = null;
      
      log.debug("begin DailyConfirmedEbuysAction");
      String forward = "success";
      HttpSession session = request.getSession();
      
      String client = "MNAJERA"; // this.getDbUser(request);
      SupportProcess process = new SupportProcess(client);
      try {
         log.debug("finding daily confirmed ebuy orders ");
         ebuys = process.getDailyConfirmedEbuys();
      } catch (BaseException be) {
         forward = "error";
      } finally {
         process = null;
      }
      session.setAttribute("EbuyConfirmedBeans",ebuys);
      
      return mapping.findForward(forward);
   }
   
}
