/******************************************************
 * RemoveChargeAction.java
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
import java.util.Collection;


public class RemoveChargeAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      // servlet params
      String line = null;
      String radianpo = null;

      String forward = "Success";
      String errMsg = null;
   
      line = (String) request.getParameter("line");
      radianpo = (String) request.getParameter("radianpo");      
      //log.debug("got line: " + line);
      //log.debug("got radianpo: " + radianpo);
      
      HttpSession session = request.getSession();     

      // get personnel bean
      PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
      if (personnel==null) { errMsg = "No Personnel found!"; }
                        
      // get the materials bean from the session (so we can computer total number of rows)
      Collection materialBeans = (Collection) session.getAttribute("MaterialBeans");
      if (materialBeans==null) { errMsg = "No Materials Beans found!"; }      

      // get database client (so we can get a connection)
      String client = this.getDbUser(request);;                
      OrderingProcess orderProcess = new OrderingProcess(client);
      
      if (errMsg!=null) {
         session.setAttribute("POErrorMsgBean",errMsg);
         return mapping.findForward("Error");
      }
      
      int lineNum = Integer.parseInt(line);      
      String rmvRes = orderProcess.removeLine(radianpo, lineNum, 0);
      
      // now we have to rebuild line data so the new line will show up on the screen for them to confirm.
      
      // query only ADDITIONAL_CHARGES PO_line_detail_view bean(s)
      //log.debug("getting additional charges beans");
      Collection addchargeBeans = orderProcess.getAddChargeLines(radianpo);

      // query for PO_header_view bean
      //log.debug("getting header bean");                                         
      PoHeaderViewBean poHeaderBean = orderProcess.getPOHeader(personnel,radianpo);      

      int totalLineCount=0;
      if (materialBeans!=null) 
         totalLineCount += materialBeans.size();
      if (addchargeBeans!=null)
         totalLineCount += addchargeBeans.size();

      // set the session beans
      session.setAttribute("POHeaderBean", poHeaderBean);
      session.setAttribute("AddtlChargeBeans", addchargeBeans);
      session.setAttribute("TotalLineCount",totalLineCount+"");
            
      return mapping.findForward(forward);
   }
   
}
