/******************************************************
 * EdiOrderStatusAction.java
 *
 * 
 ******************************************************
 */
package com.tcmis.internal.hub.action;

import com.tcmis.internal.hub.process.EdiOrderStatusProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
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


public class EdiOrderStatusAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
     // sesion beans (in)
     PersonnelBean user = null;
     // parameters
     String companyId = null;
     String action = null;
     // session beans (out)
     Collection statusBeanCollection = null;
     Collection shiptoBeans = null;     
     Collection validUOSBeans = null; 
     String releaseStatus = "";
     
     String searchFieldBean = "customerPoNo";
     String searchOperatorBean = "CONTAINS";
     String searchValueBean = ""; 
      
     log.debug("begin hub/EdiOrderStatusAction");
     ResourceLibrary hubLibrary = new ResourceLibrary("hub");
     String forward = "Success";
     HttpSession session = request.getSession();
     companyId = request.getParameter("edicompany");
     if (companyId==null) {
       companyId =  hubLibrary.getString("edi.status.defaultCompany");
     }
     
     user = (PersonnelBean) session.getAttribute("personnelBean");
     if (user==null) { 
        forward = "NoLogin";
        String defForward = hubLibrary.getString("edi.status.forward");
        request.setAttribute("requestedPage", defForward);
        request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
     } else {
        String client = this.getDbUser(request);
        EdiOrderStatusProcess orderstatusProcess = new EdiOrderStatusProcess(client);
        try {
           statusBeanCollection = orderstatusProcess.getStatusCollection(companyId);
           shiptoBeans = orderstatusProcess.getShiptoList(companyId);
           validUOSBeans = orderstatusProcess.getValidUnitOfSale(companyId);
           // releaseStatus = orderstatusProcess.getReleaseStatus(companyId);
        } catch (BaseException be) {
           forward = "Error";
           orderstatusProcess = null;
        }
     }
     hubLibrary = null;
     
     session.setAttribute("CompanyIdBean",companyId);
     session.setAttribute("SearchValueBean",searchValueBean);
     session.setAttribute("SearchFieldBean",searchFieldBean);
     session.setAttribute("SearchOperatorBean",searchOperatorBean);
     session.setAttribute("ShipToListBean",shiptoBeans);     
     session.setAttribute("errorviewBeanCollection",statusBeanCollection);
     session.setAttribute("validUOSBean",validUOSBeans);
     // session.setAttribute("ReleaseStatus",releaseStatus);
     
     return mapping.findForward(forward);
   }

}
