package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

import com.tcmis.internal.distribution.beans.SalesQuoteLineViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.internal.distribution.beans.CustomerCreditBean;
import com.tcmis.internal.distribution.beans.UserOpsEntityPgViewBean;
import com.tcmis.internal.distribution.beans.OpsEntCustomerCurrencyViewBean;
import com.tcmis.internal.distribution.process.CustLookUpProcess;
import com.tcmis.internal.distribution.process.CustomerRequestProcess;
import com.tcmis.internal.distribution.process.ScratchPadProcess;

import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class ScratchPadAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
//	  login

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "scratchpadmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		HttpSession session = request.getSession();

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		if (!personnelBean.getPermissionBean().hasUserPagePermission("orderEntry")) {
			return (mapping.findForward("nopermissions"));
		}

		String tcmISError = (String) ((DynaBean) form).get("tcmISError");
		if (tcmISError != null && tcmISError.length() > 0) request.setAttribute("tcmISError", tcmISError);

		if (form == null) return (mapping.findForward("success"));

		String uAction = (String) ((DynaBean) form).get("uAction");
		if (uAction == null) {
			request.setAttribute("blank", "Y");
			return mapping.findForward("success");
		}

		ScratchPadProcess process = new ScratchPadProcess(this.getDbUser(request), getTcmISLocaleString(request));
		//CustomerRequestProcess crProcess = new CustomerRequestProcess(this.getDbUser(request), this.getLocale(request));
		//request.setAttribute("priceList", crProcess.getPriceListDropDown());

		SalesQuoteViewBean inputBean = new SalesQuoteViewBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		request.setAttribute("vvpriceListCollection", process.getSearchResult("select distinct ops_entity_id,price_group_id, price_group_name from user_ops_entity_pg_view where personnel_id = {0} and company_id = {1} order by ops_entity_id,price_group_name", new UserOpsEntityPgViewBean(), personnelBean.getPersonnelId(), personnelBean.getCompanyId()));
		request.setAttribute("csrCollection", process.getCscDropDown());
		
		String hideOrShowDiv = (String) ((DynaBean) form).get("hideOrShowDiv");
		if (hideOrShowDiv == null) hideOrShowDiv = "hide";

		request.setAttribute("closeTab", "N");
		request.setAttribute("newTab", "N");
		request.setAttribute("closeOldTab", "N");
		request.setAttribute("popUpAutoAllocate", "N");
		
/*			java.util.Enumeration e = request.getParameterNames();
			Vector<String> v = new Vector();
						while(e.hasMoreElements()) {
							String name = (String) e.nextElement();
							v.add(name);
						}
			Collections.sort(v);
			for(String name:v){
				System.out.println("Name:"+name+":value:"+request.getParameter(name));
			}  */

		if (uAction.equals("searchCustomer")) {
			String selectedCustomerId = request.getParameter("customerId");

			CustLookUpProcess customerProcess = new CustLookUpProcess(this.getDbUser(request), getTcmISLocaleString(request));

			Vector<CustomerAddressSearchViewBean> customerColl = (Vector) customerProcess.searchShipToBeanColl(selectedCustomerId);

			if (customerColl != null) request.setAttribute("customerInfo", customerColl);

			//request.setAttribute("showOverCreditLimit", "true");
			request.setAttribute("personnelId", personnelBean.getPersonnelId());
			request.setAttribute("lastName", personnelBean.getLastName());
			request.setAttribute("firstName", personnelBean.getFirstName());

			return (mapping.findForward("getjsonobj"));

		} else if (uAction.equals("searchScratchPadId")) {
			saveTcmISToken(request);
			
			Boolean noDataForward = process.doSearchScratchPadId((DynaBean) form, request);
			
			String errorFromQuickPages = request.getParameter("errorFromQuickPages");
			
			//log.debug("errorFromQuickPages:"+errorFromQuickPages);
			request.setAttribute("tcmISError", errorFromQuickPages);
			
			if (noDataForward == false) return (mapping.findForward("noDateFound"));
		} else if (uAction.equals("saveNew")) {

//	    	scratchPadId = new BigDecimal(session.getAttribute("scratchPadId").toString());
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			
			process.doSaveNew((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			saveTcmISToken(request);
			request.setAttribute("showOverCreditLimit", "true");
		} else if (uAction.equals("confirmQuote")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			checkToken(request);
			
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			process.doConfirmQuote((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			request.setAttribute("showOverCreditLimit", "false");

		} else if (uAction.equals("confirmMR")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			checkToken(request);

			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));

			process.doConfirmMR((DynaBean) form, personnelBean, inputBean, updateBeanCollection, request);
			
			request.setAttribute("showOverCreditLimit", "false");
			
		} else if (uAction.equals("acceptMR")) {
			if (!personnelBean.getPermissionBean().hasOpsEntityPermission("acceptInterCompanyMr", null, null)) {
				return mapping.findForward("nopermission");
			}
			
			checkToken(request);

			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));

			process.doAcceptMR((DynaBean) form, personnelBean, inputBean, updateBeanCollection, request);
			
			request.setAttribute("showOverCreditLimit", "false");
	
		} else if (uAction.equals("newQuoteFromScratchPad")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}

			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			process.doNewQuoteFromScratchPad((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			saveTcmISToken(request);
			request.setAttribute("showOverCreditLimit", "false");

//			request.setAttribute("blank", "N");
			if("B".equals(inputBean.getNewOrderType()))
				request.setAttribute("newOrderType", "B");
			else
				request.setAttribute("newOrderType", "Q");
			request.setAttribute("newTab", "Y");
//log.debug(inputBean.getCloseOldTab());
			if ("Y".equals(inputBean.getCloseOldTab())) request.setAttribute("closeOldTab", "Y");

		} else if (uAction.equals("newQuoteFromMR")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			
			process.doNewQuoteFromMR((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			saveTcmISToken(request);
			request.setAttribute("showOverCreditLimit", "false");

//			request.setAttribute("blank", "N");
			if("B".equals(inputBean.getNewOrderType()))
				request.setAttribute("newOrderType", "B");
			else
				request.setAttribute("newOrderType", "Q");
			request.setAttribute("newTab", "Y");

		} else if (uAction.equals("createMRfromQuote")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));

			process.doCreateMRfromQuote((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			saveTcmISToken(request);
			request.setAttribute("showOverCreditLimit", "false");

//			request.setAttribute("blank", "N");
			request.setAttribute("newOrderType", "MR");
			request.setAttribute("newTab", "Y");
//log.debug(inputBean.getCloseOldTab());
			if ("Y".equals(inputBean.getCloseOldTab())) request.setAttribute("closeOldTab", "Y");

		} else if (uAction.equals("createMRfromMR")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));

			process.doCreateMRfromMR((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			saveTcmISToken(request);
			request.setAttribute("showOverCreditLimit", "false");

//			request.setAttribute("blank", "N");
			request.setAttribute("newOrderType", "MR");
			request.setAttribute("newTab", "Y");

		} else if (uAction.equals("newScratchPadFromQuote")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}

			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			
			process.doNewScratchPadFromQuote((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			saveTcmISToken(request);
			request.setAttribute("showOverCreditLimit", "false");

//			request.setAttribute("blank", "N");
			request.setAttribute("newOrderType", "SP");
			request.setAttribute("newTab", "Y");

		} else if (uAction.equals("newScratchPadFromMR")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			
			process.doNewScratchPadFromMR((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			saveTcmISToken(request);
			request.setAttribute("showOverCreditLimit", "false");

//			request.setAttribute("blank", "N");
			request.setAttribute("newOrderType", "SP");
			request.setAttribute("newTab", "Y");
		} else if (uAction.equals("autoAllocateForMR")) {
			checkToken(request);
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			
			process.doAutoAllocateForMR((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
		} else if (uAction.equals("autoAllocateForSP")) {
			checkToken(request);
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			
			process.doAutoAllocateForSP((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
		} else if (uAction.equals("deleteQuoteLine")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			checkToken(request);
			process.doDeleteQuoteLine((DynaBean) form, personnelId, inputBean, getTcmISLocale(request), request);
			
			request.setAttribute("showOverCreditLimit", "false");
			inputBean.setSelectedRowId("1");
		} else if (uAction.equals("deleteMRLine")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			checkToken(request);
			process.doDeleteMRLine((DynaBean) form, personnelId, inputBean, getTcmISLocale(request), request);
			
			request.setAttribute("showOverCreditLimit", "false");
			inputBean.setSelectedRowId("1");
		} else if (uAction.equals("deleteQuote")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			checkToken(request);
			process.doDeleteQuote((DynaBean) form, personnelId, inputBean,request);
		
		} else if (uAction.equals("deleteMR")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			
			checkToken(request);
			process.doDeleteMR((DynaBean) form, personnelId, inputBean,request);
			
		} else if (uAction.equals("cancelQuote")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}

			checkToken(request);
			process.doCancelQuote((DynaBean) form, inputBean,request);
			
		} else if (uAction.equals("newxxx")) {
			Vector results = (Vector) process.getScratchPadId(personnelId);
			BigDecimal scratchPadId = new BigDecimal(results.toArray()[0].toString());
			request.setAttribute("blank", "Y");
			request.setAttribute("orderType", "Scratch Pad");
			request.setAttribute("scratchPadId", scratchPadId);
			if (!"OK".equals(results.get(1))) {
				request.setAttribute("tcmISError", results.get(1));
			}

			return (mapping.findForward("getjsonscratchpadid"));
		} else if (uAction.equals("saveLine") || uAction.equals("save") || uAction.equals("changeCustomer")) {

			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			
			process.doSave((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			saveTcmISToken(request);

			if (uAction.equals("changeCustomer")) request.setAttribute("showOverCreditLimit", "true");
			else request.setAttribute("showOverCreditLimit", "false");

			if (uAction.equals("saveLine")) 
			{
				 request.setAttribute("popUpAutoAllocate", "Y");
			}
		} else if (uAction.equals("cancelMRLine")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}

			checkToken(request);
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));

			process.doCancelMRLine((DynaBean) form, personnelId, inputBean, updateBeanCollection, request);
			
			request.setAttribute("showOverCreditLimit", "false");
		} else if (uAction.equals("releaseMRLine")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			checkToken(request);
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			process.doReleaseMRLine((DynaBean) form, personnelId, inputBean, updateBeanCollection, request,personnelBean);
			request.setAttribute("showOverCreditLimit", "false");
		} else if (uAction.equals("releaseMR")) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", null, null, null)) {
				return mapping.findForward("nopermission");
			}
			checkToken(request);
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), getTcmISLocale(request));
			process.doReleaseMR((DynaBean) form, personnelId, inputBean, updateBeanCollection, request,personnelBean);
			request.setAttribute("showOverCreditLimit", "false");
		} else if (uAction.equals("customerPoDupCheck")) {
			BigDecimal prNumber = new BigDecimal(request.getParameter("prNumber"));
			String poNumber = request.getParameter("poNumber");
			String companyId = request.getParameter("companyId");

			String count = process.getDupCheck(prNumber, poNumber, companyId);

			request.setAttribute("count", count);
			return (mapping.findForward("getcustomerpodupcount.jsp"));
		} else if (uAction.equals("addNewLine")) {
			
			SalesQuoteLineViewBean bean = new SalesQuoteLineViewBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			
			String toServer = request.getParameter("toServer");
			
			Object[] obj = process.doAddNewLine(personnelId,  inputBean,  bean, toServer);
			
			if(obj[0] != null && !"OK".equalsIgnoreCase((String)obj[0]))
				request.setAttribute("tcmISError", (String)obj[0]);
			else {
				request.setAttribute("lineColl", obj[1]);
			}
			
			request.setAttribute("mvItem", inputBean.getMvItem());
			return (mapping.findForward("getlinerecord"));
		}
//	  search    
		else {/* set up search page data*/

		}
		request.setAttribute("selectedRowId", inputBean.getSelectedRowId());
		request.setAttribute("hideOrShowDiv", hideOrShowDiv);
		return mapping.findForward("success");
	}

}