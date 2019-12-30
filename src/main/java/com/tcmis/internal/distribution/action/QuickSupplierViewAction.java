package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;
import com.tcmis.internal.distribution.process.GetContactCollProcess;
import com.tcmis.internal.distribution.process.GetPoCollProcess;
import com.tcmis.internal.distribution.process.GetSupplierPriceListCollProcess;
import com.tcmis.internal.distribution.process.GetSupplierInvoicesProcess;
import com.tcmis.internal.distribution.process.GetSupplierContactCollProcess;
import com.tcmis.internal.distribution.process.QuickQuoteProcess;

public class QuickSupplierViewAction extends TcmISBaseAction {
	private static final String CREATE_NEW_TAB = "createNewTab";
	public Object doneLock[] = new Object[4];
	public Boolean done = false;
	
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		// Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "quicksupplierview");
			//	This is to save any parameters passed in the URL, so that they can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}   	

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	/*	
		// Need to have the permission from the database, normally it's the pageName instead of "mrRelease"
		if (!personnelBean.getPermissionBean().hasUserPagePermission("mrRelease"))
		{
			return (mapping.findForward("nopermissions"));
		} 
	*/	
		
        // copy date from dyna form to the data bean
		QuickQuoteInputBean inputBean = new QuickQuoteInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		request.setAttribute("input", inputBean);
		
		if(inputBean.getToday() == null)
			inputBean.setToday(new Date());
		if( form == null )
	      	return (mapping.findForward("success"));

		String uAction = (String) ( (DynaBean)form).get("uAction");
		if( uAction == null ) {
			return mapping.findForward("success");
		}

		QuickQuoteProcess process = new QuickQuoteProcess(getDbUser(), getTcmISLocale());
		
		// Search
		if ("search".equals(uAction)) {

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println("Start Time:"+dateFormat.format(date));

			ExecutorService exec = Executors.newCachedThreadPool();
			ArrayList<Future<String>> results = new ArrayList<Future<String>>();
			results.add(exec.submit(new GetPoCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean))); 
			
			results.add(exec.submit(new GetSupplierPriceListCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean))); 
			
			results.add(exec.submit(new GetSupplierInvoicesProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean))); 
			
			results.add(exec.submit(new GetSupplierContactCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean))); 
			
			this.saveTcmISToken(request);
			
			// Wait until all threads are done
			results.get(0).get();
			results.get(1).get();
			results.get(2).get();
			results.get(3).get();
		
			date = new Date();
			System.out.println("End Time:"+dateFormat.format(date));

			request.setAttribute("personnelId", personnelBean.getPersonnelId());
			
			return (mapping.findForward("success")); 
		}
		else if ("createSupplierPOHistoryExcel".equals(uAction)){
			GetPoCollProcess pProcess = new GetPoCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean);
			Collection poColl = pProcess.searchPos();
			
			setExcel(response, "QuickSupplierView");
			process.getSupplierPOHistoryExcelReport(poColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("createSupplierPriceListExcel".equals(uAction)){
			GetSupplierPriceListCollProcess splProcess = new GetSupplierPriceListCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean);
			Collection supplierPriceColl = splProcess.searchSupplierPriceList();
			
			setExcel(response, "QuickSupplierView");
			process.getSupplierPriceListExcelReport(supplierPriceColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("createSupplierInvoiceExcel".equals(uAction)){
			GetSupplierInvoicesProcess siProcess = new GetSupplierInvoicesProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean);
			Collection supplierInvoiceColl = siProcess.searchSupplierInvoice();
			
			setExcel(response, "QuickSupplierView");
			process.getSupplierInvoiceHistoryExcelReport(supplierInvoiceColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("createSupplierContactExcel".equals(uAction)){
			GetSupplierContactCollProcess cProcess = new GetSupplierContactCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean); 
			Collection contactColl = cProcess.searchSupplierContact();
					
			setExcel(response, "QuickSupplierView");
			process.getSupplierContactExcelReport(contactColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}

		return (mapping.findForward("success"));  

	}

}
