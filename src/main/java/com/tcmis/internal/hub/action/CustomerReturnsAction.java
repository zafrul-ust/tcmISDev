package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CatalogItemAddChargeBean;
import com.tcmis.internal.hub.beans.CustomerReturnInputBean;
import com.tcmis.internal.hub.beans.MrIssueReceiptViewBean;
import com.tcmis.internal.hub.process.AllocationAnalysisProcess;
import com.tcmis.internal.hub.process.CustomerReturnProcess;

/******************************************************************************
 * Controller for customer returns
 * @version 1.0
 ******************************************************************************/
public class CustomerReturnsAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "customerreturns");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		PermissionBean permissionBean = personnelBean.getPermissionBean();
		if (! permissionBean.hasUserPagePermission("newCustomerReturns"))  
		{
			return mapping.findForward("nopermissions");
		}
		String uAction = request.getParameter("uAction");
		
		if (null != uAction) 
		{
			CustomerReturnProcess process = new CustomerReturnProcess(this.getDbUser(request),getTcmISLocaleString(request));
			CustomerReturnInputBean inputBean = new CustomerReturnInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

			if ( uAction.equals("receiveReturns"))     	  
			{
				checkToken(request);
				Collection<MrIssueReceiptViewBean> returnBeanInputCollection = BeanHandler.getGridBeans((DynaBean)form, "beanData", new MrIssueReceiptViewBean(), getTcmISLocale(request), "ok");
//DynaBean dynaForm = (DynaBean) form;
//				List returnBeans = (List) dynaForm.get("returnsBean");
//				Iterator iterator = returnBeans.iterator();
//				Collection returnBeanInputCollection = new Vector();
//				while (iterator.hasNext()) 
//				{
//					LazyDynaBean lazyBean = (LazyDynaBean) iterator.next();
//					MrIssueReceiptViewBean receiptViewBean = new MrIssueReceiptViewBean();
//					BeanHandler.copyAttributes(lazyBean, receiptViewBean, getTcmISLocale(request));
//					returnBeanInputCollection.add(receiptViewBean);
//				}
				String hubName = request.getParameter("hubName");
				boolean forCredit = false;
				if( "Y".equals(request.getParameter("returnForCredit") ) )
						forCredit = true;
				Collection results = process.receiveSelected(returnBeanInputCollection, personnelBean, hubName,forCredit);
				Iterator resultsIter = results.iterator();
				Collection custReceiptIds = null;
				Collection errorMessages = null;
				if (resultsIter.hasNext()) 
				{
					custReceiptIds = (Collection) resultsIter.next();
				}
				if (resultsIter.hasNext()) 
				{
					errorMessages = (Collection) resultsIter.next();
				}

				//Pass the result collections in request
				request.setAttribute("custOwnedReceiptColl",custReceiptIds);         
				request.setAttribute("custOwnedErrorColl",errorMessages);    

				Collection returns = process.getReturns(inputBean,true);
				//Pass the result collection in request
				//log.debug("returnReceiptColl.size() = " + returns.size() + ";"); 

				request.setAttribute("returnReceiptColl", returns);    
				return (mapping.findForward("success"));
			}
		    else if ( uAction.equals("createExcel")) {
				try {
					DropDownNamesInputBean dBean = new DropDownNamesInputBean();
					BeanHandler.copyAttributes(form, dBean, getTcmISLocale(request));
					setExcel(response, "Customer Return Excel Report");
					process.getExcelReport(inputBean,dBean).write(response.getOutputStream());
				}
				catch (Exception ex) {
					ex.printStackTrace();
					return mapping.findForward("genericerrorpage");
				}
				return noForward;
			}

			if ( !uAction.equals("receiveReturns")) //search     	  
				{   
					Collection returns = process.getReturns(inputBean,true);
					//Pass the result collection in request
					request.setAttribute("returnReceiptColl",returns);
					this.saveTcmISToken(request);
				} 
		} 	  
		else
		{          
			//populate drop downs
			AllocationAnalysisProcess aaProcess = new AllocationAnalysisProcess(this.getDbUser(request));
			request.setAttribute("hubInventoryGroupFacOvBeanCollection", aaProcess.getHubDropDownData(personnelId));    
		}

		return (mapping.findForward("success"));
	}

}
