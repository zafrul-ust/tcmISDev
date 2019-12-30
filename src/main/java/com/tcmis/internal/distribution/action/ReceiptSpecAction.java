package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.CustomerContactBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import com.tcmis.internal.distribution.beans.SpecsToReceiveViewBean;
import com.tcmis.internal.distribution.process.ReceiptSpecProcess;


/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */  

public class ReceiptSpecAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "receiptspec");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		/* if (!personnelBean.getPermissionBean().hasUserPagePermission("receiptSpec"))
       {
         return (mapping.findForward("nopermissions"));
       }*/

		String forward = "success";       
		request.setAttribute("startSearchTime", new Date().getTime());     


		SpecsToReceiveViewBean inputBean = new SpecsToReceiveViewBean();
		BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));

		ReceiptSpecProcess process = new ReceiptSpecProcess(this.getDbUser(request),getTcmISLocaleString(request));         

		// Search
		if ( (inputBean.getAction() == null)  || ("".equals(inputBean.getAction()) ) ) {
			request.setAttribute("specReceiptViewBeanCollection", process.getSearchResult(inputBean, personnelBean));


			this.saveTcmISToken(request);

		}
		else if ( (null!=inputBean.getAction())  && ("submit".equals(inputBean.getAction()) ) ) {

			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("Receiving",null,null,null))
			{
				request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
				request.setAttribute("specReceiptViewBeanCollection", process.getSearchResult(inputBean, personnelBean));
				return (mapping.findForward("success"));
			}
			checkToken(request);
			SpecsToReceiveViewBean bean = new SpecsToReceiveViewBean();
			Collection<SpecsToReceiveViewBean> beans;
			String errorMsgs = null;
			try {
				beans = BeanHandler.getBeans((DynaBean) form, "specReceiptViewBean", bean,getTcmISLocale(request));
				
				if(null!= bean && !beans.isEmpty())
				{

					for ( SpecsToReceiveViewBean specBean: beans)
					{
						// do insert
						if(("N".equalsIgnoreCase(specBean.getInReceiptSpec()))   && ("true".equalsIgnoreCase(specBean.getCoa()) ||
								"true".equalsIgnoreCase(specBean.getCoc())) ) 
						{
							// get string error msg.
							Object[] results = process.insertIntoReceipt(specBean, personnelBean);
							errorMsgs =  results[0].toString();
						}
						// do update
						if(("Y".equalsIgnoreCase(specBean.getInReceiptSpec()))   && ("true".equalsIgnoreCase(specBean.getCoa()) ||
								"true".equalsIgnoreCase(specBean.getCoc())) ) 
						{
							// get string error msg.
							errorMsgs = process.updateReceipt(specBean, personnelBean);
						}
						

						// call delete
						if(("Y".equalsIgnoreCase(specBean.getInReceiptSpec())) && ("false".equalsIgnoreCase(specBean.getCoa()) &&
								"false".equalsIgnoreCase(specBean.getCoc())) )
						{
							// get string error msg.
							errorMsgs =  process.deleteReceipt(specBean, personnelBean);
						}
						
						
					}
									
				}
				request.setAttribute("tcmISErrors", errorMsgs);   
				
				process.callReceiptAllocate(inputBean);
			} catch (Exception e) {
				request.setAttribute("tcmISErrors", e.toString());
			}

			request.setAttribute("specReceiptViewBeanCollection", process.getSearchResult(inputBean, personnelBean));
		}

		request.setAttribute("endSearchTime", new Date().getTime() );      
		return (mapping.findForward(forward));
	}
}
