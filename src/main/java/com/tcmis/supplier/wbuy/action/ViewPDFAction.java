/******************************************************
 * ViewPDFAction.java
 * 
 * Prep the PDF for viewing
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.wbuy.process.PurchaseOrderPDFProcess;
import com.tcmis.supplier.wbuy.process.VvItemTypeProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Vector;


public class ViewPDFAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      String radianpo=null;
   
      String errMsg=null;    
      String po_status="1";  // 1 = new PO , 2 = confirmed PO   
      String forward = "Success";
      
      HttpSession session = request.getSession();     
      radianpo = (String) request.getParameter("po");
      //log.debug("got radian po: " + radianpo);
      
      // get personnel bean
      PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
      //log.debug("got personnel: " + personnel);
      if (personnel==null) { errMsg = "No Personnel found!"; log.error("No PersonnelBean!"); }

      String confBean = (String) session.getAttribute("ConfirmedBean");
      if (confBean!=null) {
         po_status = "2";
      }
      
      if (errMsg!=null) {
         session.setAttribute("POErrorMsgBean",errMsg);
         return mapping.findForward("Error");
      }
      
      // get URL for PDF po
      //log.debug("Creating PDF for PO");
      // get database client (so we can get a connection)
      String client = this.getDbUser(request);;           
      
      PurchaseOrderPDFProcess po_pdf_process = new PurchaseOrderPDFProcess(client);
      VvItemTypeProcess vv_item_process = new VvItemTypeProcess(client);
      
      //log.debug("getting charge types");
      Vector chargeTypes = vv_item_process.getaddchargeType();
      String po_pdf_url = null;      
      try {
         //log.debug("calling build page.");
         po_pdf_url = po_pdf_process.buildPage(radianpo, "", "cbruner", chargeTypes, po_status,personnel.getPersonnelId()+"");
      } catch (Exception ue) {
         log.error("Exception building PO PDF");      
         forward="Error";
      }
      //log.debug("po_pdf_url="+po_pdf_url);
                        
      if (po_pdf_url != null) {
         session.setAttribute("PDFURL",po_pdf_url);
      }
            
      return mapping.findForward(forward);
   }
   
}
