  package com.tcmis.internal.hub.action;

  import java.math.BigDecimal;
  import java.util.Collection;
import java.util.Vector;

  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  import org.apache.commons.beanutils.DynaBean;
  import org.apache.struts.action.ActionForm;
  import org.apache.struts.action.ActionForward;
  import org.apache.struts.action.ActionMapping;

  import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
  import com.tcmis.common.exceptions.BaseException;
  import com.tcmis.common.framework.TcmISBaseAction;
  import com.tcmis.common.util.BeanHandler;
  import com.tcmis.common.util.ResourceLibrary;

import com.tcmis.internal.hub.beans.BatchPicklistViewBean;
import com.tcmis.internal.hub.beans.ProductionBatchViewBean;
  import com.tcmis.internal.hub.beans.RepackageBatchViewBean;
import com.tcmis.internal.hub.process.ProductionBatchViewProcess;
import com.tcmis.internal.hub.process.RepackageBatchViewProcess;

  /******************************************************************************
   * Controller for finishedgoods
   * @version 1.0
   ******************************************************************************/
  public class FinishedGoodsAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
  	if (!this.isLoggedIn(request)) {
  		  request.setAttribute("requestedPage", "finishedgoodsmain".toLowerCase());
  		  request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
  		  return (mapping.findForward("login"));
      }

	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	
	String action = (String) ( (DynaBean) form).get("_action");
	if( action == null ) return mapping.findForward("success");

//main
	if ( action.equals("finishedGoods")) {
    	request.setAttribute("batchId",request.getParameter("batchId"));
    	request.setAttribute("batchCloseDate", request.getParameter("batchCloseDate"));
    	request.setAttribute("inventoryGroup", request.getParameter("inventoryGroup"));
    } else
        if ( ( (DynaBean) form).get("_action") != null &&
                ( (String) ( (DynaBean) form).get("_action")).equals("display")) {
        	request.setAttribute("inventoryGroup", request.getParameter("inventoryGroup"));
            String teststr = request.getParameter("readonly");
        	request.setAttribute("readonly", request.getParameter("readonly"));

        RepackageBatchViewProcess process = new RepackageBatchViewProcess(this.getDbUser(request));
    	DynaBean dynaBean = (DynaBean) form;
    	RepackageBatchViewBean bean = new RepackageBatchViewBean();
    	BeanHandler.copyAttributes(dynaBean, bean);
    	Vector v = (Vector) process.getSearchResult(bean);
    	request.setAttribute("RepackageBatchViewCollection",v);
    }   else
        if ( action.equals("update")) {
       	request.setAttribute("inventoryGroup", request.getParameter("inventoryGroup"));
  		java.util.Enumeration e = request.getParameterNames();

  		request.setAttribute("readonly", request.getParameter("readonly"));
    	RepackageBatchViewProcess process = new RepackageBatchViewProcess(this.getDbUser(request));
    	DynaBean dynaBean = (DynaBean) form;
    	
		RepackageBatchViewBean[] beans = (RepackageBatchViewBean[])
				BeanHandler.getBeans(dynaBean, "RepackageBatchViewBean",new RepackageBatchViewBean()).toArray();
		BigDecimal pid = new BigDecimal(personnelBean.getPersonnelId());
		RepackageBatchViewBean bean0 = null, anybean = null,bean = null;
		String showReceipts = "";
		for(int ii = 0 ; ii< beans.length; ii++ ) {
			bean = beans[ii];
			if( anybean == null ) anybean = bean;
			if( bean.getModified().equalsIgnoreCase("Y") ) {
				if( bean0 == null ) bean0 = bean;
				BigDecimal receiptId = (BigDecimal)((Vector)process.updateValue(bean)).get(0);
				if( showReceipts.length() ==0 )
					showReceipts = receiptId.toString();
				else
					showReceipts += "," + receiptId.toString();
		}
		}
		
    	Vector v = (Vector) process.getSearchResult(anybean);
		request.setAttribute("RepackageBatchViewCollection",v);
    	request.setAttribute("batchId",request.getParameter("batchId"));
    	request.setAttribute("batchCloseDate", request.getParameter("batchCloseDate"));
    	request.setAttribute("showReceipts", showReceipts);
    }   
      return mapping.findForward("success");
    }
  }