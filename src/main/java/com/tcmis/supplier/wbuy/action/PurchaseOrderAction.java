/******************************************************
 * PurchaseOrderAction.java
 * 
 * Prep the Purchase Order screen
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.supplier.wbuy.beans.PoHeaderViewBean;
import com.tcmis.supplier.wbuy.beans.PoLineDetailViewBean;
import com.tcmis.supplier.wbuy.beans.WbuyStatusViewBean;
import com.tcmis.supplier.wbuy.process.OrderingProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;


public class PurchaseOrderAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      // servelet params
      String radianpo = null;
      
      String forward = "Success";   
      String errMsg = null;
   
      // get parameter PO
      ResourceLibrary wbuyLibrary = new ResourceLibrary("wbuy");
      
      radianpo = (String) request.getParameter("po");
      //log.debug("PurchaseOrderAction::got radian po: " + radianpo);
      
      HttpSession session = request.getSession();     
      
      forward = "Success";
      errMsg = null;
      if (radianpo == null) {         
         radianpo = (String) session.getAttribute("TargetPO"); // only try to get "TargetPO" if po param is not present
         //log.debug("got session bean TargetPO: " + radianpo);
         if (radianpo== null) {
            errMsg = "No PO number specified";
            forward = "Error";
         }
      } else {      
         session.setAttribute("TargetPO", radianpo);  // set the target PO, in case user has not logged in yet
      }
            
      // cleanup of previous session beans
      session.removeAttribute("ConfirmedBean");
      session.removeAttribute("CommentsBean");
      session.removeAttribute("StatusBean");
      session.removeAttribute("POHeaderBean");    
      session.removeAttribute("MaterialBeans");
      session.removeAttribute("AddChargeBeans");
      session.removeAttribute("POErrorMsgBean");      
      session.removeAttribute("PORedirURL");                  
      
      // **begin* get SESSION beans
      
      // get personnel bean
      PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
      if (personnel==null) { 
         log.error("No PersonnelBean! redirecting to login");
         forward = "NoLogin";
         String defForward = wbuyLibrary.getString("wbuy.orderlist.forward");
         request.setAttribute("requestedPage", defForward);
         return (mapping.findForward("login"));
      }

      BigDecimal personnelId = new BigDecimal(personnel.getPersonnelId());           
      // get database client (so we can get a connection)      
      String client = this.getDbUser(request);;                 
      OrderingProcess orderProcess = new OrderingProcess(client);

      // get orders list view (to update it if confirmed or saved)
      //Collection orderBeans = (Collection) session.getAttribute("OrderBeans");
     // if (orderBeans==null) { errMsg = "Orders List not found!"; forward = "Error"; }      
      
      // make sure this is a WBUY po
      WbuyStatusViewBean wbuyBean = orderProcess.getWbuyPo(personnel,radianpo);
      if (wbuyBean==null) { errMsg = "Not a Web Buy PO!"; forward = "Error"; }            
      
      if (errMsg!=null) {
         //log.debug("got error message: " + errMsg);
         session.setAttribute("POErrorMsgBean",errMsg);
         return mapping.findForward(forward);
      }
      
      String statusBean = null;
      
      // **end* get SESSION beans
      
      // query for PO_header_view bean
      log.debug("getting header bean");                                         
      PoHeaderViewBean poHeaderBean = orderProcess.getPOHeader(personnel,radianpo);

      // query only MATERIALS PO_line_detail_view bean(s)
      Collection<PoLineDetailViewBean> materialBeans = orderProcess.getMaterialLines(radianpo);
      //log.debug("got material beans. ::empty?=" + materialBeans.isEmpty());

      // query only ADDITIONAL_CHARGES PO_line_detail_view bean(s)
      Collection addchargeBeans = orderProcess.getAddChargeLines(radianpo);
      //log.debug("got additional charges beans. ::empty=" + addchargeBeans.isEmpty());      
                       
      // create specs & flowdowns, merge them with their corresponding materials, and create a Material Data View               
      //log.debug("getting specs & flowdowns");      
      materialBeans = orderProcess.getAllLineData(poHeaderBean, materialBeans) ;
      
      int totalLineCount=0;      
      if (materialBeans != null) totalLineCount+=materialBeans.size();       
      if (addchargeBeans != null) totalLineCount+=addchargeBeans.size();
      //log.debug("totalLineCount: " + totalLineCount);
      
      // checking if po has been confirmed
      String confirmedStatus = null;
      String userComments = null;
      PoLineDetailViewBean line = null;
      int totalQuantity = 0;
      if(materialBeans != null) {
	      Iterator materialIter = materialBeans.iterator();
	      if (materialIter.hasNext()) {
	         line = (PoLineDetailViewBean) materialIter.next();        
	         if (line!=null) {
	            confirmedStatus = orderProcess.getConfirmedStatus(radianpo);    
	            if (confirmedStatus != null) {
	               userComments = orderProcess.getUserComments(radianpo);
	            }
	         }
	      }      
	      
	      for(PoLineDetailViewBean p:materialBeans)
	    	  totalQuantity += p.getQuantity().intValue();
	      
	      // acknowledge that this PO has been viewed
	      //log.debug("Acknowledging PO");
      }
	  session.setAttribute("TotalQuantity",new Integer(totalQuantity));
      String ackPO = orderProcess.acknowledgePO( new BigDecimal(radianpo), personnelId);
      if (ackPO != null) {
         errMsg = "error acknowledging PO";
         log.error("error acknowledging PO: " + radianpo + " :: "+ackPO);
      }  
      
      // update the order list to status=acknowbledged, unless it is already confirmed
      if (confirmedStatus==null) {
         BigDecimal ponum = new BigDecimal(radianpo);
         //BeanHandler.updateBean(orderBeans, "radian_po", ponum, "dbuy_status", orderProcess.STATUS_ACK);
         statusBean=orderProcess.STATUS_ACK;
         //log.debug("updated status on orderlist for po " + radianpo +" to ACKNOWLEDGED");
      } else {
         statusBean=confirmedStatus;
      }
      
      if (errMsg!=null) {
         forward="Error";
         session.setAttribute("POErrorMsgBean",errMsg);
         return mapping.findForward(forward);
      } else {
         session.removeAttribute("POErrorMsgBean");
      }
                                            
      session.setAttribute("POHeaderBean", poHeaderBean);
      session.setAttribute("MaterialBeans",materialBeans);
      session.setAttribute("TotalLineCount",totalLineCount+"");
      session.setAttribute("StatusBean",statusBean);
      session.setAttribute("DBClient",client);
      if (! addchargeBeans.isEmpty()) {
         session.setAttribute("AddChargeBeans", addchargeBeans);         
      } else {
         session.removeAttribute("AddChargeBeans");
      }
      if (confirmedStatus!=null) {
         session.setAttribute("ConfirmedBean",confirmedStatus);
      } else {
         session.removeAttribute("ConfirmedBean");
      }      
      if (userComments!=null) {
         session.setAttribute("CommentsBean",userComments);
      } else {
         session.removeAttribute("CommentsBean");
      }
            
      return mapping.findForward(forward);
   }
   
}
