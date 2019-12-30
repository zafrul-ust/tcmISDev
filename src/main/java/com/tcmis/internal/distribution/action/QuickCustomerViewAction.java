package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
import com.tcmis.internal.distribution.process.GetInvoiceCollProcess;
import com.tcmis.internal.distribution.process.GetMRHistoryCollectionProcess;
import com.tcmis.internal.distribution.process.GetQuoteHistoryCollProcess;
import com.tcmis.internal.distribution.process.QuickQuoteProcess;

public class QuickCustomerViewAction extends TcmISBaseAction {
	private static final String CREATE_NEW_TAB = "createNewTab";
	
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		// Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "quickcustomerview");
			//	This is to save any parameters passed in the URL, so that they can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}   	

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		// Need to have the permission from the database, normally it's the pageName instead of "mrRelease"
		if (!personnelBean.getPermissionBean().hasUserPagePermission("quickCustomerView"))
		{
			return (mapping.findForward("nopermissions"));
		} 
		
		
        // copy date from dyna form to the data bean
		QuickQuoteInputBean inputBean = new QuickQuoteInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		request.setAttribute("input", inputBean);
		
		if( form == null )
	      	return (mapping.findForward("success"));

		String uAction = (String) ( (DynaBean)form).get("uAction");
		if( uAction == null ) {
			return mapping.findForward("success");
		}

		QuickQuoteProcess process = new QuickQuoteProcess(getDbUser(), getTcmISLocale());
		
		// Search
		if ("search".equals(uAction)) {

//			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//			Date date = new Date();
//			System.out.println("Start Time:"+dateFormat.format(date));

			ExecutorService exec = Executors.newCachedThreadPool();
			ArrayList<Future<String>> results = new ArrayList<Future<String>>();
			results.add(exec.submit(new GetMRHistoryCollectionProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean))); 
			
			results.add(exec.submit(new GetQuoteHistoryCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean))); 
			
			results.add(exec.submit(new GetInvoiceCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean))); 
			
			results.add(exec.submit(new GetContactCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean))); 
			
			this.saveTcmISToken(request);
			
			// Wait until all threads are done
			for(int i=0; i<4; i++)
				results.get(i).get();
		
//			date = new Date();
//			System.out.println("End Time:"+dateFormat.format(date));

			request.setAttribute("personnelId", personnelBean.getPersonnelId());
			
			return (mapping.findForward("success")); 
		}
		else if ( "createMRFromMR".equals(uAction)){
			BigDecimal oldPrNumber= new BigDecimal((String) ((DynaBean)form).get("oldPrNumber"));
			String lineItem = (String) ( (DynaBean)form).get("lineItem");
			
			request.setAttribute("quoteType","MR");
			request.setAttribute("newPrNumber",process.doCreateMRFromMR(personnelId, oldPrNumber, lineItem)[0]);
			
			return (mapping.findForward(CREATE_NEW_TAB));  
	    }
		else if ( "createMRFromQuote".equals(uAction)){
			BigDecimal oldPrNumber= new BigDecimal((String) ((DynaBean)form).get("oldPrNumber"));
			String lineItem = (String) ( (DynaBean)form).get("lineItem");
			
			request.setAttribute("quoteType","MR");
			request.setAttribute("newPrNumber",process.callCreateMrFromQuoteLine(personnelId, oldPrNumber, lineItem).get(0));
			
			return (mapping.findForward(CREATE_NEW_TAB));  
	    }
		else if ( "createQuoteFromMR".equals(uAction) || "createQuoteFromQuote".equals(uAction)){
			BigDecimal oldPrNumber = new BigDecimal((String) ((DynaBean)form).get("oldPrNumber"));
			BigDecimal newPrNumber = new BigDecimal((String) ((DynaBean)form).get("openedPr"));
		
			if("createQuoteFromMR".equals(uAction) || "createQuoteFromQuote".equals(uAction)) {
				Vector headerResult = process.callNewSalesQuote(personnelId, oldPrNumber);
				newPrNumber = (BigDecimal) headerResult.get(0);
			}
			
			String lineItem = (String) ( (DynaBean)form).get("lineItem");
			String error = process.callNewSalesQuoteLine(personnelId, oldPrNumber, lineItem, newPrNumber);
			
			if(error != null )
				request.setAttribute("tcmISError", error);
			
			request.setAttribute("newPrNumber", newPrNumber);
			request.setAttribute("quoteType","Q");
			
			return (mapping.findForward(CREATE_NEW_TAB));  
	    }
		else if ( "createNewMRLineFromMR".equals(uAction)){
			BigDecimal oldPrNumber = new BigDecimal((String) ((DynaBean)form).get("oldPrNumber"));
			BigDecimal newPrNumber = new BigDecimal((String) ((DynaBean)form).get("openedPr"));
			
			String lineItem = (String) ( (DynaBean)form).get("lineItem");
			String error = process.callNewMRLineFromMR(personnelId, oldPrNumber, lineItem, newPrNumber);
			
			if(error != null )
				request.setAttribute("tcmISError", error);
				
			request.setAttribute("newPrNumber", newPrNumber);
			request.setAttribute("quoteType","MR");
			
			return (mapping.findForward(CREATE_NEW_TAB));  
	    }
		else if ( "createNewMRLineFromQuote".equals(uAction)){
			BigDecimal oldPrNumber = new BigDecimal((String) ((DynaBean)form).get("oldPrNumber"));
			BigDecimal newPrNumber = new BigDecimal((String) ((DynaBean)form).get("openedPr"));
			
			String lineItem = (String) ( (DynaBean)form).get("lineItem");
			String error = process.callNewMRLineFromQuote(personnelId, oldPrNumber, lineItem, newPrNumber);
			
			if(error != null )
				request.setAttribute("tcmISError", error);
				
			request.setAttribute("newPrNumber", newPrNumber);
			request.setAttribute("quoteType","MR");
			
			return (mapping.findForward(CREATE_NEW_TAB));  
	    }
		else if ( "createNewQuoteLine".equals(uAction)){
			BigDecimal oldPrNumber = new BigDecimal((String) ((DynaBean)form).get("oldPrNumber"));
			BigDecimal newPrNumber = new BigDecimal((String) ((DynaBean)form).get("openedPr"));
			
			String lineItem = (String) ( (DynaBean)form).get("lineItem");
			String error = process.callNewQuoteLine(personnelId, oldPrNumber, lineItem, newPrNumber);
			
			if(error != null )
				request.setAttribute("tcmISError", error);
				
			request.setAttribute("newPrNumber", newPrNumber);
			request.setAttribute("quoteType","Q");
			
			return (mapping.findForward(CREATE_NEW_TAB));  
	    }
		else if ("createMRHistoryExcel".equals(uAction)){
			GetMRHistoryCollectionProcess mProcess = new GetMRHistoryCollectionProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean); 
			Collection mrHistoryColl = mProcess.searchMRHistory();
			
			setExcel(response, "QuickCustomerView");
			process.getCustomerMRHistoryExcelReport(mrHistoryColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("createInvoiceExcel".equals(uAction)){
			GetInvoiceCollProcess pprocess = new GetInvoiceCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean); 
			Collection invoiceColl = pprocess.searchInvoice();

			setExcel(response, "QuickCustomerView");
			process.getInvoiceExcelReport(invoiceColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("createPriceQuoteHistoryExcel".equals(uAction)){
			GetQuoteHistoryCollProcess qHProcess = new GetQuoteHistoryCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean, personnelBean); 
			Collection quoteHistoryColl = qHProcess.searchQuoteHistory();
			
			setExcel(response, "QuickCustomerView");
			process.getCustomerPriceQuoteHistoryExcelReport(quoteHistoryColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("createContactExcel".equals(uAction)){
			GetContactCollProcess cProcess = new GetContactCollProcess(this.getDbUser(request),getTcmISLocaleString(request),request,inputBean); 
			Collection contactColl = cProcess.searchNewCustomerContact();
					
			setExcel(response, "QuickCustomerView");
			process.getContactExcelReport(contactColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}

		return (mapping.findForward("success"));  

	}

}
