package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.POHistoryViewBean;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;
import com.tcmis.internal.distribution.beans.QuotationHistoryViewBean;
import com.tcmis.internal.distribution.beans.SalesSearchBean;
import com.tcmis.internal.distribution.process.QuickQuoteProcess;
import com.tcmis.internal.distribution.process.QuotationHistoryProcess;

public class QuickQuoteAction extends TcmISBaseAction {
	private static final String CREATE_NEW_TAB = "createNewTab";

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		// Login
		PersonnelBean personnelBean = null;
		
		if(request.getParameter("fromJDE") != null && request.getParameter("fromJDE").equals("Y")){
			// The user ID could change. Check permission every access.
			//if (!this.isLoggedIn(request)) {
				personnelBean = new PersonnelBean();
				personnelBean.setCompanyId(this.getClient(request));
				if (request.getParameter("userId") == null || request.getParameter("userId").length() == 0) {
					return mapping.findForward("nopermissions");
				}
				personnelBean.setLogonId(request.getParameter("userId").toLowerCase());
				personnelBean.setCompanyId("Radian"); 
				LoginProcess loginProcess = new LoginProcess(this.getDbUser(request));
				personnelBean = loginProcess.loginWeb(personnelBean, false);
				personnelBean.setDbUser(this.getDbUser(request));

	            if (!personnelBean.getPermissionBean().hasUserPagePermission("quickQuote") 
	            		|| !personnelBean.getPermissionBean().hasOpsEntityPermission("jdeLogin", "", "Radian"))
				{
					return (mapping.findForward("nopermissions"));
				} 
	            else{
					
					personnelBean.setOpsHubIgOvBeanCollection(loginProcess.getOpsHubIgData(personnelBean));
		            this.setSessionObject(request, personnelBean, "personnelBean");
		            com.tcmis.common.admin.action.LoginAction.setLocale(request, request.getSession(), request.getParameter("locale"));
	            }			
			/*}
			else 
				personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");*/
		}
		else{
			// Login
			if (!this.isLoggedIn(request)) {
				request.setAttribute("requestedPage", "quickquote");
				//	This is to save any parameters passed in the URL, so that they can be acccesed after the login
				request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
				return (mapping.findForward("login"));
			}   	
	
			personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
			
			// Need to have the permission from the database, normally it's the pageName instead of "mrRelease"
			if (!personnelBean.getPermissionBean().hasUserPagePermission("quickQuote"))
			{
				return (mapping.findForward("nopermissions"));
			} 
		}
		
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
        // copy date from dyna form to the data bean
		QuickQuoteInputBean inputBean = new QuickQuoteInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		request.setAttribute("input",inputBean);
		
		if( form == null )
	      	return (mapping.findForward("success"));

		String uAction = (String) ( (DynaBean)form).get("uAction");
		if( uAction == null ) {
			return mapping.findForward("success");
		}

		QuickQuoteProcess process = new QuickQuoteProcess(getDbUser(), getTcmISLocale());
		
		// Search
		if ("search".equals(uAction)) {
			System.out.println(new Date());
			GenericProcess gProcess = new GenericProcess(this);
			request.setAttribute("mrHistoryColl",gProcess.getSearchResult("select * from table (pkg_sales_search.FX_PREVIOUS_ORDER ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11},{12},{13},{14}))", new SalesSearchBean(),
					inputBean.getItemId(),
					personnelBean.getCompanyId(),
					personnelId,
					"",
					inputBean.getInventoryGroup(),
					null,
					inputBean.getCustomerId(),
					"",
					"",
					"",
					"",
					inputBean.getSearchKey(),
					inputBean.getCatalogCompanyId(),
					inputBean.getCatalogId(),
					inputBean.getHideInterCompany()
			));
			
			QuotationHistoryViewBean inputBean2 = new QuotationHistoryViewBean();
			QuotationHistoryProcess qHProcess = new QuotationHistoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
			BeanHandler.copyAttributes(inputBean, inputBean2);
			request.setAttribute("quoteHistoryColl",qHProcess.getSearchResult(inputBean2, personnelBean));
			
			request.setAttribute("poHistoryColl",gProcess.getSearchResult("select * from table (pkg_sales_search.FX_PREVIOUS_po_line ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}))", new POHistoryViewBean(),
					inputBean.getItemId(),
					personnelBean.getCompanyId(),
					new BigDecimal(personnelBean.getPersonnelId()),
					"",
					inputBean.getInventoryGroup(),
					null,
					365,
					null,
					inputBean.getSearchKey()
			));
			
			request.setAttribute("inventoryColl", process.getInventorySearchResult(inputBean));
			
			this.saveTcmISToken(request);
			
			System.out.println(new Date());
			return (mapping.findForward("success"));  
		}
		else if ( "getPrice".equals(uAction)){
			request.setAttribute("suggestedSellPrice", process.getSuggestedSellPrice(inputBean));
			
			if("Y".equals((String) ( (DynaBean)form).get("getItemDesc")))
				request.setAttribute("itemDesc", process.getItemDesc(inputBean));
			
			return (mapping.findForward("getPrice"));  
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
		
			Vector headerResult = process.callNewSalesQuote(personnelId, oldPrNumber);
			BigDecimal newPrNumber = (BigDecimal) headerResult.get(0);
			
			String lineItem = (String) ( (DynaBean)form).get("lineItem");
			String error = process.callNewSalesQuoteLine(personnelId, oldPrNumber, lineItem, newPrNumber);
			
			if(error != null )
				request.setAttribute("tcmISError", error);
			
			request.setAttribute("newPrNumber", newPrNumber);
			request.setAttribute("quoteType","Q");
			
			return (mapping.findForward(CREATE_NEW_TAB));  
	    }
		else if ( "createNewScratchPadforNewCustomerfromMR".equals(uAction)){
			BigDecimal oldPrNumber = new BigDecimal((String) ((DynaBean)form).get("oldPrNumber"));
			String lineItem = (String) ( (DynaBean)form).get("lineItem");
			BigDecimal newCustomerId = new BigDecimal((String) ((DynaBean)form).get("newCustomerId"));
			String newCompanyId = (String) ( (DynaBean)form).get("newCompanyId");
			String newShipToLocId = (String) ( (DynaBean)form).get("newShipToLocId");
			String quoteType = (String) ( (DynaBean)form).get("quoteType");
			
			if("MR".equals(quoteType)) {
				Vector results = process.callNewMRforNewCustomerFromMR(personnelId, oldPrNumber, lineItem, newCustomerId, newCompanyId, newShipToLocId);
				
				if (results.get(2) != null && !((String) results.get(2)).equalsIgnoreCase("OK") && !((String) results.get(2)).equalsIgnoreCase("")) {
					if (log.isDebugEnabled()) {
						log.debug("error:"+results.get(2));
					}
					request.setAttribute("tcmISError", results.get(2).toString());
				}
				request.setAttribute("newPrNumber", results.get(0));
			}
			else {
				Vector results = process.callNewQuoteforNewCustomerFromQuote(personnelId, oldPrNumber, lineItem, newCustomerId, newCompanyId, newShipToLocId);
				
				if (results.get(2) != null && !((String) results.get(2)).equalsIgnoreCase("OK") && !((String) results.get(2)).equalsIgnoreCase("")) {
					if (log.isDebugEnabled()) {
						log.debug("error:"+results.get(2));
					}
					request.setAttribute("tcmISError", results.get(2).toString());
				}
				request.setAttribute("newPrNumber", results.get(0));
			}
			
			request.setAttribute("quoteType", quoteType);
			
			return (mapping.findForward(CREATE_NEW_TAB));  
	    }
		else if ( "createNewScratchPadforNewCustomerfromQuote".equals(uAction)){
			BigDecimal oldPrNumber = new BigDecimal((String) ((DynaBean)form).get("oldPrNumber"));
			String lineItem = (String) ( (DynaBean)form).get("lineItem");
			BigDecimal newCustomerId = new BigDecimal((String) ((DynaBean)form).get("newCustomerId"));
			String newCompanyId = (String) ( (DynaBean)form).get("newCompanyId");
			String newShipToLocId = (String) ( (DynaBean)form).get("newShipToLocId");
			String quoteType = (String) ( (DynaBean)form).get("quoteType");
		
			if("MR".equals(quoteType)) {
				Vector results = process.callNewMRforNewCustomerFromQuote(personnelId, oldPrNumber, lineItem, newCustomerId, newCompanyId, newShipToLocId);
				
				if (results.get(2) != null && !((String) results.get(2)).equalsIgnoreCase("OK") && !((String) results.get(2)).equalsIgnoreCase("")) {
					if (log.isDebugEnabled()) {
						log.debug("error:"+results.get(2));
					}
					request.setAttribute("tcmISError", results.get(2).toString());
				}
				request.setAttribute("newPrNumber", results.get(0)); 
			}
			else {
				Vector results = process.callNewQuoteforNewCustomerFromQuote(personnelId, oldPrNumber, lineItem, newCustomerId, newCompanyId, newShipToLocId);
				
				if (results.get(2) != null && !((String) results.get(2)).equalsIgnoreCase("OK") && !((String) results.get(2)).equalsIgnoreCase("")) {
					if (log.isDebugEnabled()) {
						log.debug("error:"+results.get(2));
					}
					request.setAttribute("tcmISError", results.get(2).toString());
				}
				request.setAttribute("newPrNumber", results.get(0));
				
			}
			
			request.setAttribute("quoteType", quoteType);
			
			return (mapping.findForward(CREATE_NEW_TAB));  
	    }
		else if ("createMRHistoryExcel".equals(uAction)){
			GenericProcess gProcess = new GenericProcess(this);
			Vector mrHistoryColl = gProcess.getSearchResult("select * from table (pkg_sales_search.FX_PREVIOUS_ORDER ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11},{12},{13},{14}))", new SalesSearchBean(),
					inputBean.getItemId(),
					personnelBean.getCompanyId(),
					personnelId,
					"",
					inputBean.getInventoryGroup(),
					null,
					inputBean.getCustomerId(),
					"",
					"",
					"",
					"",
					inputBean.getSearchKey(),
					inputBean.getCatalogCompanyId(),
					inputBean.getCatalogId(),
					inputBean.getHideInterCompany()
			);
			setExcel(response, "QuickItemView");
			process.getItemMRHistoryExcelReport(mrHistoryColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("createPOHistoryExcel".equals(uAction)){
			GenericProcess gProcess = new GenericProcess(this);
			Vector poHistoryColl = gProcess.getSearchResult("select * from table (pkg_sales_search.FX_PREVIOUS_po_line ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}))", new POHistoryViewBean(),
					inputBean.getItemId(),
					personnelBean.getCompanyId(),
					new BigDecimal(personnelBean.getPersonnelId()),
					"",
					inputBean.getInventoryGroup(),
					null,
					365,
					null,
					inputBean.getSearchKey());

			setExcel(response, "QuickItemView");
			process.getItemPOHistoryExcelReport(poHistoryColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("createPriceQuoteHistoryExcel".equals(uAction)){
			QuotationHistoryViewBean inputBean2 = new QuotationHistoryViewBean();
			QuotationHistoryProcess qHProcess = new QuotationHistoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
			BeanHandler.copyAttributes(inputBean, inputBean2);
			Collection quoteHistoryColl = qHProcess.getSearchResult(inputBean2, personnelBean);
			
			setExcel(response, "QuickItemView");
			process.getItemPriceQuoteHistoryExcelReport(quoteHistoryColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else if ("createInventoryExcel".equals(uAction)){
			Collection inventoryColl = process.getInventorySearchResult(inputBean);
			
			setExcel(response, "QuickItemView");
			process.getInventoryExcelReport(inventoryColl, getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}

		return (mapping.findForward("success"));  

	}

}
