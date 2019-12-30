package com.tcmis.internal.supply.action;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.POSupplierContactInputBean;
import com.tcmis.internal.supply.process.POSupplierContactProcess;

/******************************************************************************
 * Controller for Purchase Order Supplier Contact (Order Taker) search page
 * @version 1.0
 ******************************************************************************/

public class POSupplierContactAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception 
			{
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "posuppliercontact");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		request.setAttribute("startSearchTime", new Date().getTime());  
		request.setAttribute("valueElementId",request.getParameter("valueElementId"));
		request.setAttribute("displayElementId",request.getParameter("displayElementId"));
						
		POSupplierContactInputBean inputBean = new POSupplierContactInputBean();
		BeanHandler.copyAttributes(form, inputBean);

		POSupplierContactProcess process = new POSupplierContactProcess(this.getDbUser(request));

		request.setAttribute("vvSupplierContactTypeCollection", process.getSupplierContractTypeList(inputBean));
		
		if ("search".equals(inputBean.getAction())) 
		{
			Collection pOSupplierContactBeanCollection = process.getSupplierContactBeanCollection(inputBean);
			request.setAttribute("pOSupplierContactBeanCollection", pOSupplierContactBeanCollection);
			this.saveTcmISToken(request);
		}
		// creating a new record.
		else if ("showSupplierContact".equals(inputBean.getAction())) 
		{
			return (mapping.findForward("showposuppliercontact"));
		}
		// for modifying existing record
		else if ("showSupplierContactData".equals(inputBean.getAction())) 
		{
			Collection coll = process.getSupplierContactBeanCollection(inputBean);
			if( coll.size() != 0 )
			{	
			 request.setAttribute("supplierContactBean",coll.toArray()[0]);
			}
			return (mapping.findForward("showposuppliercontact"));
		}		
		
		else if ("addAndShow".equals(inputBean.getAction())) 
		{	
			// for a new contact setting contact id to null.			
						
			inputBean.setContactId(null);
			
			Object[] results = new Object[2];
			String err = "";
			try {
				results =  process.addModifySupplierContact(inputBean, personnelBean);
				err = (String) results[0];
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				err = e.toString();
			}		
			
			inputBean.setContactId(null);
			
			request.setAttribute("supplierContactBean",inputBean );
			
			if (err != null && err.trim().length() > 0)
				request.setAttribute("tcmISError", err);
			else {
				if("Y".equals(inputBean.getFromQuickSupplierView())) {
					request.setAttribute("addToOpener", "Y");
					request.setAttribute("newContactId", results[1]);
				} else
					request.setAttribute("done", "Y");
			}
			
			return (mapping.findForward("showposuppliercontact"));
		}
		
		else if ( "modify".equals(inputBean.getAction())) 
		{	
			// for a new contact setting contact id to null.			
			
			Object[] results = new Object[2];
			String err = "";
			
			if("addAndShow".equals(inputBean.getAction()))
			inputBean.setContactId(null);
			
			try {
				results =  process.addModifySupplierContact(inputBean, personnelBean);
				err = (String) results[0];
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				err = e.toString();
			}		
			
			request.setAttribute("supplierContactBean",inputBean );
			
			if (err != null && err.trim().length() > 0) {
				request.setAttribute("tcmISError", err);
			} else {
				if("Y".equals(inputBean.getFromQuickSupplierView())) 
					request.setAttribute("modifyOpener", "Y");
				else
					request.setAttribute("done", "Y");
			}
			
			return (mapping.findForward("showposuppliercontact"));
		}
		
		
		
				

		request.setAttribute("endSearchTime", new Date().getTime() );      
		return (mapping.findForward("success"));
			}
}
