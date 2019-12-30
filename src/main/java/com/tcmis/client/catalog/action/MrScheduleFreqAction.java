package com.tcmis.client.catalog.action;

import com.tcmis.client.catalog.beans.DeliverySummaryBean;
import com.tcmis.client.catalog.beans.MrScheduleFreqInputBean;
import com.tcmis.client.catalog.process.DeliveryScheduleProcess;
import com.tcmis.client.catalog.process.MrScheduleFreqProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/******************************************************************************
 * Controller for UpdateReceiptNotes
 * @version 1.0
     ******************************************************************************/
public class MrScheduleFreqAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "mrschedulefreq");
      request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
        
//  populate fields
    MrScheduleFreqProcess process = new MrScheduleFreqProcess(this.getDbUser(request),this.getTcmISLocale(request));     
    MrScheduleFreqInputBean inputBean = new MrScheduleFreqInputBean();
//	notesProcess.getsearchResult(inputBean.getCatalogId(),inputBean.getCatPartNo());

    String action = (String) ( (DynaBean) form).get("uAction");
    BeanHandler.copyAttributes(form, inputBean,this.getLocale(request));  
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    
    if ( "update".equals(action) ) {
    	process.update(inputBean);
        request.setAttribute("bean",process.getSearchResult(inputBean).get(0));
    }
    else {
        request.setAttribute("bean",process.getSearchResult(inputBean).get(0));
        DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
        DeliverySummaryBean myInputBean = new DeliverySummaryBean();
        myInputBean.setPrNumber(inputBean.getPrNumber());
        myInputBean.setLineItem(inputBean.getLineItem());
        myInputBean.setCompanyId(inputBean.getCompanyId());                
        request.setAttribute("medianMrLeadTime", deliveryScheduleProcess.getMedianMrLeadTime(myInputBean));
    }
    
    return (mapping.findForward("success"));        
  }
}
