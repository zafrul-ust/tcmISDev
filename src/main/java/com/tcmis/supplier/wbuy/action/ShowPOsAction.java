/******************************************************
 * ShowPOsAction.java
 * 
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.supplier.wbuy.beans.OrderListInputBean;
import com.tcmis.supplier.wbuy.process.OrderingProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;


public class ShowPOsAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      // servelet params
      String choice = null;
      String days = null;
      String ponum = null;
      String status_new = null;
      String status_prob = null;
      String status_res = null;
      String status_ack = null;
      String status_conf = null;
      String status_rej = null;
      
      String forward = "Success";
      String errMsg = null;
      boolean probs_rejects = false;

      choice = (String) request.getParameter("showpos");
      days = (String) request.getParameter("numdays");
      ponum = (String) request.getParameter("ponum");
      status_new = (String) request.getParameter("stat_new");
      status_prob = (String) request.getParameter("stat_prob");
      status_res = (String) request.getParameter("stat_res");
      status_ack = (String) request.getParameter("stat_ack");
      status_conf = (String) request.getParameter("stat_conf");
      status_rej = (String) request.getParameter("stat_rej");
      
      //log.debug("got choice: " + choice);
      //log.debug("got days: " + days);
      //log.debug("got ponum: "+ ponum);
      //log.debug("got stat_new: " + status_new);
      //log.debug("got stat_prob: " + status_prob);
      //log.debug("got stat_res: " + status_res);
      //log.debug("got stat_ack: " + status_ack);
      //log.debug("got stat_conf: " + status_conf);
      //log.debug("got stat_rej: " + status_rej);

      String[] stats = new String[] { status_new, status_prob, status_res, status_ack, status_conf, status_rej };      

      HttpSession session = request.getSession();                 
      // **begin* get SESSION beans
      
      // get personnel bean
      PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
      if (personnel==null) { errMsg = "No personnel found."; log.error("No PersonnelBean!"); }
                              
      if (errMsg!=null) {
         session.setAttribute("POErrorMsgBean",errMsg);
         return mapping.findForward("Error");
      }
      // **end* get session beans
                  
      //log.debug("creating order list view");      
      // orders
      String client = this.getDbUser(request);;           
      
      Collection orderBeans = null;
      OrderingProcess orderProcess = new OrderingProcess(client);
      OrderListInputBean inputbean = new OrderListInputBean(); 
	  BeanHandler.copyAttributes(form, inputbean, getTcmISLocale(request));
      if (choice.equalsIgnoreCase("bypo")) {
         orderBeans = orderProcess.selectOrder(personnel, ponum);
      } else if (choice.equalsIgnoreCase("bystatus")) {
    	  orderBeans = orderProcess.selectOrdersUsingSupplier(personnel, null, stats, inputbean);   
      }
      
      // check for problems/rejections
      if (orderBeans!=null) {
         probs_rejects = orderProcess.findProblemsOrRejections(orderBeans);
      }
      
      session.setAttribute("OrderBeans",orderBeans);      
      session.setAttribute("ShowPO",choice);
      session.setAttribute("NumDays",days);    
      session.setAttribute("POSearch",ponum);

      if (status_new!=null) {
         session.setAttribute(OrderingProcess.NB_STAT_NEW, "new");
      } else {
         session.removeAttribute(OrderingProcess.NB_STAT_NEW);         
      }
      if (status_ack!=null) {
         session.setAttribute(OrderingProcess.NB_STAT_ACK, "ack"); 
      } else {
         session.removeAttribute(OrderingProcess.NB_STAT_ACK);
      }
      if (status_prob!=null) {      
         session.setAttribute(OrderingProcess.NB_STAT_PROB, "prob");
      } else {
         session.removeAttribute(OrderingProcess.NB_STAT_PROB);         
      }
      if (status_res!=null) {
         session.setAttribute(OrderingProcess.NB_STAT_RES, "res");
      } else {
         session.removeAttribute(OrderingProcess.NB_STAT_RES);
      }
      if (status_conf!=null) {
         session.setAttribute(OrderingProcess.NB_STAT_CONF,"conf");
      } else {
         session.removeAttribute(OrderingProcess.NB_STAT_CONF);
      }
      if (status_rej!=null) {
         session.setAttribute(OrderingProcess.NB_STAT_REJ,"rej");
      } else {
         session.removeAttribute(OrderingProcess.NB_STAT_REJ);
      }
      
      if (probs_rejects) {
         session.setAttribute("ProblemOrReject","yess");
      } else {
         session.removeAttribute("ProblemOrReject");
      }
      
      if (( (DynaBean) form).get("userAction") != null &&
	  ((String)( (DynaBean) form).get("userAction")).trim().equalsIgnoreCase("buttonCreateExcel")) {

//	 OrderingProcess orderingProcess = new OrderingProcess(client);	      
	 //log.debug("getting orders");
//	 orderBeans = orderingProcess.selectOrdersUsingSupplier(personnel, null, stats, inputbean);      
	 if (orderBeans==null) {    
	     errMsg = "Error searching for orders";
	     forward = "Error";      
	 } else {
	     // check for problems/rejections
	     probs_rejects = orderProcess.findProblemsOrRejections(orderBeans); 
	 }
	 this.setExcel(response,"OrderList");
	 orderProcess.getExcelReport(orderBeans, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
//	 this.setExcelHeader(response);
//	 orderingProcess.getExcelReport(orderBeans, response.getWriter(), request.getLocale());
	   
	 return (mapping.findForward("viewfile"));
      }

      return mapping.findForward(forward);
   }
   
}
