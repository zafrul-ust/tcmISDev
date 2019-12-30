/******************************************************
 * SortItemsAction.java 
 * 
 * Sort the collection of items based in the given field.
 ******************************************************
 */
package com.tcmis.supplier.dbuy.action;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.dbuy.process.ContractProcess;

import java.io.IOException;
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


public class StatusViewAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      // servlet params
      String field = null;
      String path = null;

      String forward = "success";
   
      HttpSession session = request.getSession();     

      log.debug("in status view action");
      String client = "DBUY";   //this.getDbUser(request);
      ContractProcess contractProcess = new ContractProcess(client);     
      
      // get the materials bean from the session (so we can computer total number of rows)
      Collection statusBeans = contractProcess.getDbuyStatusBeans();                  
      if (statusBeans!=null) {
         log.debug("got status beans");
         // set the session beans                                
         session.setAttribute("DbuyStatusBeans",statusBeans);
      } else {
         log.debug("status beans are null!");
      }
      
      return mapping.findForward(forward);
   }
   
}
