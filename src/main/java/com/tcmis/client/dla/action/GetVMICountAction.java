/*
 * GetVMICountAction.java
 *
 * Created on March 28, 2008, 12:15 PM
 */

package com.tcmis.client.dla.action;

import com.tcmis.client.dla.beans.VmiOrderBean;
import com.tcmis.client.dla.process.VMIProcess;
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


public class GetVMICountAction extends TcmISBaseAction {

   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      // session beans (in)
      // parameters      
      String catPartNo = "";
      String transactionType = null;
      String lineSeq = null;
      String changeOrderSeq = null;
      String customerPO = null;
      String poLine = null;
      String orderQty = null;
      String companyId = null;
      String unitOfSale = null;
      
      // session beans (in/out)
      Collection vmiBeanCollection = null;
      VmiOrderBean order = null;
      
      log.debug("begin dla/GetVMICountAction");
      String forward = "Success";
      HttpSession session = request.getSession();

      // required param
      catPartNo = request.getParameter("catPartNo");
      unitOfSale = request.getParameter("unitOfSale");
      // optional params
      companyId = request.getParameter("companyId");
      transactionType = request.getParameter("transactionType");
      lineSeq = request.getParameter("lineSeq");
      changeOrderSeq = request.getParameter("changeOrderSeq"); 
      customerPO = request.getParameter("customerPO");
      poLine = request.getParameter("poLine");
      orderQty = request.getParameter("orderQty");
      
      if (companyId!=null && transactionType!=null && lineSeq!=null && changeOrderSeq!=null && customerPO!=null && poLine!=null && orderQty!=null) {
         order = new VmiOrderBean(companyId, transactionType, customerPO, poLine, lineSeq, changeOrderSeq, orderQty,unitOfSale);
         log.debug("created VmiOrderBean");
      }
      
      String client = "USGOV";   // "TCM_OPS"; // this.getDbUser(request);     // user.getClient(); 
      VMIProcess vmiProcess = new VMIProcess(client);
              
      vmiBeanCollection = vmiProcess.getDLAVMICount(catPartNo, unitOfSale);      
            
      log.debug("got vmiBeanCollection");
      session.setAttribute("vmiBeanCollection",vmiBeanCollection);
 
      if (order!=null) {
         session.setAttribute("vmiOrderBean",order);
      }
      return mapping.findForward(forward);
   }

}