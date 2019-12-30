package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.DynaBean;
import com.tcmis.internal.hub.beans.ListItemDetailViewBean;
import com.tcmis.internal.hub.process.ReceivingItemViewProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;

public class ReceivingItemAction extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "receivingitemsearchmain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    String action = ((String) ((DynaBean) form).get("action"));
    /*request.setAttribute("receipts", request.getParameter("receipts"));
    request.setAttribute("listItemId", request.getParameter("listItemId"));
    request.setAttribute("inventoryGroup", request.getParameter("inventoryGroup"));
    request.setAttribute("catPartNo", request.getParameter("catPartNo"));
    request.setAttribute("catalog", request.getParameter("catalog"));
    request.setAttribute("catalogCompanyId", request.getParameter("catalogCompanyId"));*/

   //if form is not null we have to perform some action
   if (form != null) {
      this.saveTcmISToken(request);
     //copy date from dyna form to the data form
     ListItemDetailViewBean bean = new ListItemDetailViewBean();
     BeanHandler.copyAttributes(form, bean);
     //check what button was pressed and determine where to go
     if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(action)) {
       ReceivingItemViewProcess process = new ReceivingItemViewProcess(this.getDbUser(request));
       request.setAttribute("receivingItemBeanCollection", process.getSearchResult(bean));       
       return (mapping.findForward("showresults"));
     }else if (((DynaBean) form).get("action") != null && "update".equalsIgnoreCase(action)){
       PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
       BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
       ReceivingItemViewProcess process = new ReceivingItemViewProcess(this.getDbUser(request));
       process.updateReceiptItem(bean,personnelId);
       return (mapping.findForward("done"));
     }
   }
   return mapping.findForward("success");
  } //end of method
} //end of class