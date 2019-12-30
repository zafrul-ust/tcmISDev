package com.tcmis.internal.supply.action;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogPartInventoryBean;
import com.tcmis.client.catalog.beans.FlowDownBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.SpecBean;
import com.tcmis.internal.hub.process.SpecFlowDownProcess;
import com.tcmis.internal.supply.beans.PoHeaderInputBean;
import com.tcmis.internal.supply.beans.PoHeaderViewBean;
import com.tcmis.internal.supply.beans.PoLineDetailViewBean;
import com.tcmis.internal.supply.beans.PoLineItemListBean;
import com.tcmis.internal.supply.beans.PurchaseOrderBean;
import com.tcmis.internal.supply.process.PurchaseOrderProcess;

public class PurchaseOrderAction extends TcmISBaseAction {
	
	@SuppressWarnings("unchecked")
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		PurchaseOrderProcess process = new PurchaseOrderProcess(getDbUser());
		
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "purchasingorder");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}
		else if ( ! process.userHasAccess(personnelBean)) {
			return mapping.findForward("oldPageRedirect");
		}
		
		if (form == null) {
			return next; 
		}
		
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocale(request));
		PurchaseOrderBean input = new PurchaseOrderBean(form, getTcmISLocale());
		String poFromBO = (String) request.getParameter("po");
		//once this page no longer redirects to old page, the below can be changed to only accepts 'action'
		String actionFromBO = (String) (request.getParameter("action") != null ? request.getParameter("action") : request.getParameter("Action"));

		if (input.getRadianPo() == null && (poFromBO != null && !poFromBO.equalsIgnoreCase("")) 
				&& (actionFromBO != null && !actionFromBO.equalsIgnoreCase("") )) {
			input.setRadianPo(new BigDecimal(poFromBO));
			input.setAction(actionFromBO);
		}
		if (!personnelBean.getPermissionBean().hasUserPagePermission("purchaseOrderNew"))
        {
            return (mapping.findForward("nopermissions"));
        }
   
		BigDecimal workingPoLineRowNumber = new BigDecimal(0);
		//if (input != null && input.getuAction() != null && input.getPoLine() != null) {
		if (input != null && input.getPoLine() != null) {
			workingPoLineRowNumber = input.getPoLine()!=null?input.getPoLine():new BigDecimal(0);	
			workingPoLineRowNumber = workingPoLineRowNumber.divide(new BigDecimal("1000")).setScale(1,3);			
			request.setAttribute("poLineNumber",workingPoLineRowNumber);
		}
		
		request.setAttribute("outsideUSAFYAudit", (!"USA".equalsIgnoreCase(personnelBean.getCountryAbbrev()) ? true:false));
		request.setAttribute("newUnsavedPo", ((DynaBean) form).get("newUnsavedPo"));
		request.setAttribute("isSpecialCharge", process.isSpecialCharge(input.getRadianPo()));
		request.setAttribute("poType", process.getPoType(input.getRadianPo()));
		
		String errorMessage = "";
		if (input.getAction() == null) {
			//do nothing
		} else  if ("New".equalsIgnoreCase(input.getAction())) {
			loadPageData();
			PoHeaderViewBean newPoHeader = new PoHeaderViewBean();
			newPoHeader.setRadianPo(process.generateNewPo());
			newPoHeader.setPaymentTerms("Net 45");
			request.setAttribute("POHeaderBean", newPoHeader);
			request.setAttribute("numOfPoLines",0);
			request.setAttribute("newUnsavedPo", "Y");
			request.setAttribute("isSpecialCharge", false);
			request.setAttribute("poType", process.getPoType(input.getRadianPo()));
		} else if (input.getRadianPo() != null && input.getAction().equalsIgnoreCase("searchlike")) {
		    loadPageData();
			getPoLineSearchData(process, personnelBean, input, getPoHeaderSearchData(process, input, personnelBean));
		} else if (input.getRadianPo() != null && input.getAction().equalsIgnoreCase("reloadpoline")) {
			getPoLineSearchData(process, personnelBean, input, getPoHeaderSearchData(process, input, personnelBean));
			next = mapping.findForward("reloadPoLineData");
		} else if (input.getRadianPo() != null && input.getAction().equalsIgnoreCase("confirm")) {
			PoHeaderViewBean poHeader = new PoHeaderViewBean();
		    BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));
			Collection<PoLineDetailViewBean> poLineBeanCol = BeanHandler.getBeans((DynaBean) form, "lineItemBean", new PoLineDetailViewBean(), getTcmISLocale(request));
			HashMap result = process.updatePoDetails(poHeader, poLineBeanCol, personnelBean.getPersonnelIdBigDecimal(), input.getAction());
			boolean completeSuccess = false;
			String errorFromProcedure = "";
			if (!((boolean) result.get(PurchaseOrderProcess.LINE_RESULT))) {
				errorFromProcedure = (String) result.get(PurchaseOrderProcess.ERROR);
				request.setAttribute("errorMessage", errorFromProcedure );
				log.debug("errorFromProcedure = " + errorFromProcedure);
			} else if ((boolean) result.get(PurchaseOrderProcess.PO_APPROVAL_REQUIRED)) {
				request.setAttribute("notificationMessage", library.getString("msg.approvalrequired"));
				request.setAttribute("validApprover", process.isValidApprover(poHeader, personnelBean));
			} else { 
				completeSuccess = true;
			}
			
			loadPageData();
			PoHeaderViewBean poHeaderSearch = getPoHeaderSearchData(process, input, personnelBean);
			getPoLineSearchData(process, personnelBean, input, poHeaderSearch);
					
			if ( completeSuccess ) {
				poHeaderSearch.setBlnProblemWithBuy(process.isProblemWithBuy(input));
				String message = library.getString("label.confirmed");
				String interCompanyMr = (String) result.get(PurchaseOrderProcess.INTER_COMPANY_MR);
				if (!StringHandler.isBlankString(interCompanyMr))
					message += "\\n" + library.getString("label.intercompanymrcreated").replace("{0}", interCompanyMr);
				request.setAttribute("notificationMessage", message);
			} else if ( ! (boolean) result.get(PurchaseOrderProcess.PO_APPROVAL_REQUIRED)){ 
				String finalErrorMesage = "";
				if (errorFromProcedure.startsWith("Please Note:")) {
					finalErrorMesage = library.getString("label.confirmthe") + " " + library.getString("purchaseOrder") + " " + library.getString("label.sucfuly") + " " + errorFromProcedure;
					log.debug("finalErrorMesage1 = " + finalErrorMesage);
				}				
				// TCMISDEV-207 Catalyst B - Procedure to check for financial approval tree for POs
				else if (errorFromProcedure.indexOf("!ok") > -1) {
					finalErrorMesage = errorFromProcedure.replace("!ok", "");
					log.debug("finalErrorMesage2 = " + finalErrorMesage);
				} else {
					finalErrorMesage = library.getString("msg.errconf") + " " + errorFromProcedure;
				log.debug("finalErrorMesage3 = " + finalErrorMesage);
				}

				request.setAttribute("errorMessage", finalErrorMesage);
			}			
			request.setAttribute("readOnlyGrids", process.isReadOnly(poHeader.getRadianPo()));
		} else if (input.getRadianPo() != null && input.getAction().equalsIgnoreCase("financialApproval")) {
			
			PoHeaderInputBean poHeader = new PoHeaderInputBean();
		    BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));
		    PrintWriter out = response.getWriter();
			String msg = process.approveFinancialApprovalPendingPo(input.getRadianPo(), personnelBean.getPersonnelIdBigDecimal());
			
			out.write(msg);
			out.close();
			next = noForward;
		} else if (input.getRadianPo() != null && (input.getAction().equalsIgnoreCase("saveOnlyHeader"))) { 
			PoHeaderViewBean poHeader = new PoHeaderViewBean();
		    BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));     
		    HashMap headerResult = process.updatePoHeader(poHeader, personnelBean.getPersonnelIdBigDecimal());
		    boolean result = ((Boolean)headerResult.get(PurchaseOrderProcess.HEADER_RESULT)).booleanValue();
		    String error =  (String) headerResult.get(PurchaseOrderProcess.ERROR);
			PrintWriter out = response.getWriter();
			String msg = "";
			if (result) {
				msg = "OK";
			} else {
				if (error != null && !error.equalsIgnoreCase(""))
					msg = error;
				else 
					msg = "There was a problem saving the header information.";
			}			
			out.write(msg);
			out.close();
			next = noForward;
		} else if (input.getRadianPo() != null && (input.getAction().equalsIgnoreCase("saveOnlyLookAhead"))) { 
			PoHeaderInputBean poHeader = new PoHeaderInputBean();
		    BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));     

		    String lookAheadLoaded = (String) request.getParameter("lookAheadLoaded");
		    Collection<CatalogPartInventoryBean> lookaheadBeans = null;
			if (lookAheadLoaded != null && lookAheadLoaded.equalsIgnoreCase("Yes"))
				lookaheadBeans = BeanHandler.getBeans((DynaBean) form, "lookAheadBean", new CatalogPartInventoryBean(), getTcmISLocale(request));
		    
			boolean result = process.updateLookAheads(lookaheadBeans);
			PrintWriter out = response.getWriter();
			String msg = "";
			if (result) {
				msg = "OK";
			} else {
				msg = "There was a problem saving the Look Ahead information.";
			}
			out.write(msg);
			out.close();
			next = noForward;
		} else if (input.getRadianPo() != null && (input.getAction().equalsIgnoreCase("deletePoLine"))) { 
			PoHeaderViewBean poHeader = new PoHeaderViewBean();
		    BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));     
		    String rowNumber = request.getParameter("rowNumber");

		    PoLineDetailViewBean poLineBean = new PoLineDetailViewBean();		    //get the poline information
			Collection<PoLineDetailViewBean> poLineBeanCol = BeanHandler.getBeans((DynaBean) form, "lineItemBean", poLineBean, getTcmISLocale(request));			
			boolean result = true;
		    int iRowNumber = 0;
		    // get the poline bean that was modified
		    if (rowNumber != null && poLineBeanCol != null && poLineBeanCol.size() > 0 && Integer.parseInt(rowNumber) < poLineBeanCol.size()) {
		    	iRowNumber = Integer.parseInt(rowNumber);
		    } else {
		    	result = false;
		    }
		    if(result) {
		    	poLineBean = (PoLineDetailViewBean) poLineBeanCol.toArray()[iRowNumber];
		    	poLineBean = populatePoLineDetailViewBean(form, poLineBean);		    	
				HashMap newResult = process.updateDeleteOnlyOnePoLine(poHeader, poLineBean, personnelBean.getPersonnelIdBigDecimal(), "save");
				if (!((boolean) newResult.get(PurchaseOrderProcess.LINE_RESULT))) {
					result = false;
					errorMessage = (String) newResult.get(PurchaseOrderProcess.ERROR);
				}
		    }
			PrintWriter out = response.getWriter();
			String msg = "";
			if (result) {
				msg = "OK";
			} else {
				msg = "There was a problem deleting the selected row - " + rowNumber +".\n" + errorMessage;
			}
			out.write(msg);
			out.close();
			next = noForward;
		}  else if (input.getRadianPo() != null && input.getAction().equalsIgnoreCase("saveOnePoLine")) {
		
			PoHeaderViewBean poHeader = new PoHeaderViewBean();//get the poHeader information
		    BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));
		    String rowNumber = request.getParameter("rowNumber");
		    log.debug("poline number that is being saved - RowNumber = " + rowNumber );	
		    PoLineDetailViewBean poLineBean = new PoLineDetailViewBean();		    //get the poline information
			Collection<PoLineDetailViewBean> poLineBeanCol = BeanHandler.getBeans((DynaBean) form, "lineItemBean", poLineBean, getTcmISLocale(request));			

		    boolean result = true;
		    int iRowNumber = 0;
		    // get the poline bean that was modified
		    if (rowNumber != null && poLineBeanCol != null && poLineBeanCol.size() > 0 && Integer.parseInt(rowNumber) < poLineBeanCol.size()) {
		    	iRowNumber = Integer.parseInt(rowNumber);
		    } else {
		    	result = false;
		    }		    	
		    if(result) {
		    	poLineBean = (PoLineDetailViewBean) poLineBeanCol.toArray()[iRowNumber];
		    	poLineBean = populatePoLineDetailViewBean(form, poLineBean);		    	
				HashMap newResult = process.updatePoHeaderAndPoLine(poHeader, poLineBean, personnelBean.getPersonnelIdBigDecimal(), "save");
				if (!((boolean) newResult.get(PurchaseOrderProcess.LINE_RESULT))) {
					result = false;
					errorMessage = (String) newResult.get(PurchaseOrderProcess.ERROR);
				}
		    }			
			PrintWriter out = response.getWriter();
			String msg = "";
			if (result) {
				msg = "OK";
			} else {
				msg = "There was a problem saving information for row " + rowNumber + ".\n" + errorMessage;
			}
			out.write(msg);
			out.close();
			next = noForward;
		} else if ((input.getAction().equalsIgnoreCase("saveOnlySpecs")) 
					|| (input.getAction().equalsIgnoreCase("saveMsdsInfo")) 
					|| (input.getAction().equalsIgnoreCase("saveOnlyFlowdowns"))) {
			
			PoHeaderViewBean poHeader = new PoHeaderViewBean();//get the poHeader information
		    BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));

		    PoLineDetailViewBean poLineBean = new PoLineDetailViewBean();		    //get the poline information
			Collection<PoLineDetailViewBean> poLineBeanCol = BeanHandler.getBeans((DynaBean) form, "lineItemBean", poLineBean, getTcmISLocale(request));			
		    String rowNumber = request.getParameter("rowNumber");
		    log.debug("poline number that is being saved - RowNumber = " + rowNumber );
		    boolean result = true;
		    int iRowNumber = 0;
		    // get the poline bean that was modified
		    if (rowNumber != null && poLineBeanCol != null && poLineBeanCol.size() > 0 && Integer.parseInt(rowNumber) < poLineBeanCol.size()) {
		    	iRowNumber = Integer.parseInt(rowNumber);
		    } else {
		    	result = false;
		    }		    	
		    if(result) {
		    	poLineBean = (PoLineDetailViewBean) poLineBeanCol.toArray()[iRowNumber];
		    	poLineBean = populatePoLineDetailViewBean(form, poLineBean);		    	
		    	HashMap newResult = new HashMap();
		    	if (input.getAction().equalsIgnoreCase("saveOnlySpecs")) {
			    	newResult = process.updatePoSpecs(poHeader, poLineBean, personnelBean.getPersonnelIdBigDecimal(), "save");
		    	} else if (input.getAction().equalsIgnoreCase("saveOnlyFlowdowns")) {
			    	newResult = process.updatePoFlowdowns(poHeader, poLineBean, personnelBean.getPersonnelIdBigDecimal(), "save");
		    	} else if (input.getAction().equalsIgnoreCase("saveMsdsInfo")) {
		    		newResult = process.updatePoSds(poHeader, poLineBean, personnelBean.getPersonnelIdBigDecimal(), "save");
		    	}
		    	if (!((boolean) newResult.get(PurchaseOrderProcess.LINE_RESULT))) {
					result = false;
					errorMessage = (String) newResult.get(PurchaseOrderProcess.ERROR);
				}
		    }			
			PrintWriter out = response.getWriter();
			String msg = "";
			if (result) {
				msg = "OK";
			} else {
				msg = "There was a problem saving information for row " + rowNumber + ".\n" + errorMessage;
			}
			out.write(msg);
			out.close();
			next = noForward;
		}  else if (input.getRadianPo() != null && input.getAction().equalsIgnoreCase("getRate")) {
			PrintWriter out = response.getWriter();
			BigDecimal conversionRate = process.getConverionRate(request.getParameter( "currentCurrency"),input.getRadianPo().toPlainString());
			if (conversionRate == null) { //TODO: check what the conversion rate should be in case it comes back as null.
				conversionRate = new BigDecimal(1);
			}
			out.write(conversionRate.toString());
			out.close();			
			next = noForward;
		}
		else if (input.getRadianPo() != null
			&& (input.getAction().equalsIgnoreCase("saveNew") || input.getAction().equalsIgnoreCase("resendWBuyPo")))
		{
			loadPageData();
			PoHeaderViewBean poHeader = new PoHeaderViewBean();
		    BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));
			PoLineDetailViewBean poLineBean = new PoLineDetailViewBean();
			Collection<PoLineDetailViewBean> poLineBeanCol = BeanHandler.getBeans((DynaBean) form, "lineItemBean", poLineBean, getTcmISLocale(request));
			boolean completeSuccess = true;
			HashMap updateResults = new HashMap();
			if ((poHeader.getRadianPo() != null && !poHeader.getRadianPo().toString().equalsIgnoreCase(""))) {
				updateResults = process.updatePoDetails(poHeader, poLineBeanCol, personnelBean.getPersonnelIdBigDecimal(), input.getAction());
				if (!((boolean) updateResults.get(PurchaseOrderProcess.LINE_RESULT))) {
					//there are 2 vars used for reporting a single error to front-end, tcmISError and errorMessage.
					//need to be  careful to not override existing error
					request.setAttribute("tcmISError", updateResults.get(PurchaseOrderProcess.ERROR));
					completeSuccess = false;
				}
			} else {
				completeSuccess = false;
			}
			
			if (completeSuccess)
				request.setAttribute("readOnlyGrids", process.isReadOnly(poHeader.getRadianPo()));
			else
				request.setAttribute("readOnlyGrids", false);
			
			if ("saveNew".equalsIgnoreCase(input.getAction())) {
				if (completeSuccess) {//while saving new PO, querying database should only be done when the Save operation succeeds
					getPoLineSearchData(process, personnelBean, input, getPoHeaderSearchData(process, input, personnelBean));
				} else {
					request.setAttribute("HubName", "");
					request.setAttribute("POHeaderBean", poHeader);
				}
			} else if ("resendWBuyPo".equalsIgnoreCase(input.getAction())) {
				process.resendWBuyPo(poHeader);	
				if (completeSuccess) {
					request.setAttribute("result", "Po saved succesfully");
				} else {
					request.setAttribute("errorMessage", "There was a problem saving the PO. Try again later!");
				}
				getPoLineSearchData(process, personnelBean, input, getPoHeaderSearchData(process, input, personnelBean));
			}
		} else if (input.getRadianPo() != null && "addSpecialCharge".equalsIgnoreCase(input.getAction())) {
			PrintWriter out = response.getWriter();
			PoHeaderViewBean poHeader = new PoHeaderViewBean();
		    BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));
			String resultMessage = process.addPoToSpecialCharge(poHeader, personnelBean.getPersonnelIdBigDecimal());
			if (StringHandler.isBlankString(resultMessage))
				out.write("OK");
			else
				out.write(resultMessage);
			out.close();			
			next = noForward;
		} else if ("getTotalCost".equalsIgnoreCase(input.getAction())) {
			PrintWriter out = response.getWriter();
			BigDecimal totalCost = process.getPoLineTotal(request.getParameter("currencyTo"), request.getParameter("list"));
			String returnedResult = totalCost == null ? library.getLabel("generic.error") : totalCost.toPlainString();
			out.write(returnedResult);
			out.close();			
			next = noForward;
		} else if (input.getRadianPo() != null && "updateAmendment".equalsIgnoreCase(input.getAction())) {
			boolean result = true;
			try {
				int rowNumber = Integer.parseInt(request.getParameter("rowNumber"));
				errorMessage = "There was a problem saving information for row " + rowNumber + ".\n";
				log.debug("poline number that is being saved - RowNumber = " + rowNumber );
				PoHeaderViewBean poHeader = new PoHeaderViewBean();
				BeanHandler.copyAttributes(form, poHeader, getTcmISLocale(request));
				Collection<PoLineDetailViewBean> poLineBeanCol = BeanHandler.getBeans((DynaBean) form, "lineItemBean", new PoLineDetailViewBean(), getTcmISLocale(request));
				
				// get the poline bean that was modified
				if (poLineBeanCol != null && poLineBeanCol.size() > 0 && rowNumber < poLineBeanCol.size()) {
					PoLineDetailViewBean poLineBean = (PoLineDetailViewBean) poLineBeanCol.toArray()[rowNumber];
					//use old amendment number to query for data
					PoLineDetailViewBean oldAmendmentPoLineBean = new PoLineDetailViewBean();
					BeanHandler.copyAttributes(poLineBean, oldAmendmentPoLineBean);
					oldAmendmentPoLineBean.setAmendment(oldAmendmentPoLineBean.getOldAmendment());
					if ("Y".equalsIgnoreCase(poLineBean.getIsMaterialTypeItem())) {
						poLineBean.setLookAheadBeanCol(process.buildsendLookahead(poHeader.getRadianPo(), oldAmendmentPoLineBean.getItemId()));
						SpecFlowDownProcess specFlowDownProcess = new SpecFlowDownProcess(getDbUser(), getTcmISLocale());
						Collection<FlowDownBean> flowdownBeanCol = specFlowDownProcess.buildSendFlowdowns(poHeader, oldAmendmentPoLineBean);
						for (FlowDownBean tempBean : flowdownBeanCol)
							tempBean.setOk(StringHandler.isBlankString(tempBean.getOk()) || "N".equalsIgnoreCase(tempBean.getOk()) ? "false" : "true");
						poLineBean.setFlowdownBeanCol(flowdownBeanCol);
						Collection<SpecBean> specBeanCol = specFlowDownProcess.buildSendSpecs(poHeader, oldAmendmentPoLineBean);
						for (SpecBean tempBean : specBeanCol) {
							tempBean.setSavedCoaStr(!tempBean.getSavedCoa() ? "false" : "true");
							tempBean.setSavedCocStr(!tempBean.getSavedCoc() ? "false" : "true");
						}
						poLineBean.setSpecBeanCol(specBeanCol);
					} else {
						Collection<PoLineItemListBean> addChargeColl = new Vector<PoLineItemListBean>();
						String[] poLinesStrArr = process.getPoLineForAddCharge(poHeader.getRadianPo().toPlainString(), oldAmendmentPoLineBean.getPoLine().toPlainString(), oldAmendmentPoLineBean.getAmendment().toPlainString()).split(",");
						for (String poLineStr : poLinesStrArr) {
							PoLineItemListBean newBean = new PoLineItemListBean();
							newBean.setSelectPo(true);
							newBean.setPoLine(new BigDecimal(poLineStr));
							addChargeColl.add(newBean);
						}
						poLineBean.setChargeListBeanCol(addChargeColl);
					}
					HashMap procedureResult = process.updatePoHeaderAndPoLine(poHeader, poLineBean, personnelBean.getPersonnelIdBigDecimal(), "save");
					if (!((boolean) procedureResult.get(PurchaseOrderProcess.LINE_RESULT))) {
						result = false;
						errorMessage += (String) procedureResult.get(PurchaseOrderProcess.ERROR);
					}
				} else
					result = false;
			} catch (Exception e) {
				result = false;
			}
			
			PrintWriter out = response.getWriter();
			out.write(result ? "OK" : errorMessage);
			out.close();
			next = noForward;
		}
		
		return next;
	}
	
	private boolean isPoEditable(PoHeaderViewBean poHeader, PurchaseOrderBean input, 
			PersonnelBean personnelBean, PurchaseOrderProcess process) throws Exception {
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		boolean poPageEditable = false;
		
		boolean hasPurchasing = false;
		boolean hasSourcing = false;
		boolean isReadOnlyPo = false;
		
		boolean noPermission = false;
		boolean blnSetReadOnly = false;
		boolean updateStatus = false;
		
		if (permissionBean.hasFacilityPermission("Purchasing", poHeader.getHubName(), personnelBean.getCompanyId())) {
			hasPurchasing = true;			
		} else {
			blnSetReadOnly = true;
		}
		
	    if (permissionBean.hasFacilityPermission("Sourcing", poHeader.getHubName(), personnelBean.getCompanyId())) {
	    	hasSourcing = true;
		}
	    if (permissionBean.hasFacilityPermission("ReadOnlyPo", poHeader.getHubName(), personnelBean.getCompanyId())) {
	    	isReadOnlyPo = true;
		}	    
	    
		if ((poHeader.getHubName() == null || poHeader.getHubName().equalsIgnoreCase("") 
				|| (poHeader.getTransport() != null && poHeader.getTransport().equalsIgnoreCase("WEBPAGE"))) && !(isReadOnlyPo  || hasSourcing)) {
			noPermission = true;
		} else {
			if ( !hasPurchasing )
			{
				blnSetReadOnly = true;
			}
			//allowCurrencyupdate
			//allowPOCreditConfirmaiton
		}
		
		if(hasPurchasing) {
			updateStatus = true;
		} else {
			if (personnelBean.getCompanyId().equalsIgnoreCase("Radian")) {
				updateStatus = false;				
			} else {
				//no access to page.
			}
		}
		
		if (noPermission && updateStatus) {
			poPageEditable = true;
		} else if (noPermission && !updateStatus) {
			poPageEditable = false;
		} else if (updateStatus && !process.isReadOnly(poHeader.getRadianPo()) && !blnSetReadOnly) {
			poPageEditable = true;
		} else {
			poPageEditable = false;
		}		
		return poPageEditable;
	}
	
	@SuppressWarnings("unchecked")
	private PoLineDetailViewBean populatePoLineDetailViewBean(ActionForm form, PoLineDetailViewBean bean ) throws Exception {
		//lookaahead
		String lookAheadLoaded = (String) request.getParameter("lookAheadLoaded");
		if (lookAheadLoaded != null && lookAheadLoaded.equalsIgnoreCase("Yes")) {
			Collection<CatalogPartInventoryBean> lookahaedBeans = BeanHandler.getBeans((DynaBean) form, "lookAheadBean", new CatalogPartInventoryBean(), getTcmISLocale(request));
			bean.setLookAheadBeanCol(lookahaedBeans);
		}
		//specs
		String specsLoaded = (String) request.getParameter("specsLoaded");
		if (specsLoaded != null && specsLoaded.equalsIgnoreCase("Yes")) {
			Collection<SpecBean> specbeans = BeanHandler.getBeans((DynaBean) form, "specsBean", new SpecBean(), getTcmISLocale(request));
			bean.setSpecBeanCol(specbeans);
		}
		//flowdowns
		String flowdownsLoaded = (String) request.getParameter("flowdownsLoaded");
		if (flowdownsLoaded != null && flowdownsLoaded.equalsIgnoreCase("Yes")) {
			Collection<FlowDownBean> flowbeans = BeanHandler.getBeans((DynaBean) form, "flowdownsBean", new FlowDownBean(), getTcmISLocale(request));						
			bean.setFlowdownBeanCol(flowbeans);
		}
		//charge lines
		String chargesListLoaded = (String) request.getParameter("chargesListLoaded");
		if (chargesListLoaded != null && chargesListLoaded.equalsIgnoreCase("Yes")) {
			Collection<PoLineItemListBean> chargeLineBeans = BeanHandler.getBeans((DynaBean) form, "chargesListBean", new PoLineItemListBean(), getTcmISLocale(request));						
			bean.setChargeListBeanCol(chargeLineBeans);
		}
		return bean;
	}
	
	
	//retreive the header information
	private PoHeaderViewBean getPoHeaderSearchData (PurchaseOrderProcess process, PurchaseOrderBean input, PersonnelBean personnelBean) throws Exception {
		PoHeaderViewBean poHeader = null;
		Collection<PoHeaderViewBean> poHeaderCol = process.getPoHeader(input);
		if (poHeaderCol == null || poHeaderCol.size() == 0 || poHeaderCol.size() > 1) {
			request.setAttribute("errorMessage", "Po number not found. Try another!");
			request.setAttribute("poHeaderLoaded", "N");   
		} else {
			request.setAttribute("poHeaderLoaded", "Y"); 
			poHeader = (PoHeaderViewBean)poHeaderCol.iterator().next();
			poHeader.setBlnProblemWithBuy(process.isProblemWithBuy(input));
			request.setAttribute("POHeaderBean",poHeader);
			request.setAttribute("readOnlyGrids", !isPoEditable(poHeader, input, personnelBean, process));
			request.setAttribute("validApprover", process.isValidApprover(poHeader, personnelBean));
		}
		
		return poHeader;
	}
	
	//retrieve the po line information
	@SuppressWarnings("unchecked")
	private void getPoLineSearchData(PurchaseOrderProcess process, PersonnelBean personnelBean, 
			PurchaseOrderBean input, PoHeaderViewBean searchResultBean) throws Exception 
	{
		if (searchResultBean == null)
			request.setAttribute("HubName", process.getPoHubAndEntity(input));
		else
			request.setAttribute("HubName", searchResultBean.getHubName());
		
		Collection<PoLineItemListBean> poLineItemListBeanCol = new Vector<PoLineItemListBean>();
		Collection<PoLineDetailViewBean> poLineDetCol = process.getPoLineDetails(input);
		int innercount = 1;
		Collection chargeType = process.getAddChargeType();
		ArrayList<String> itemTypes = process.getItemTypes(chargeType);
		if (poLineDetCol != null && poLineDetCol.size() > 0)
			request.setAttribute("numOfPoLines",poLineDetCol.size());
		else 
			request.setAttribute("numOfPoLines",0);
		
		BigDecimal orderTotalInUSD = new BigDecimal(0);  // use this to store the total po amt in USD
		boolean isOneUSD = false;
		String linesData = "";
		String defaultCurrencyId = "";
		boolean foundCurrencyId = false;
		for (PoLineDetailViewBean bean : poLineDetCol) {
			//if default currency is not found, proceed with this step
			if (!foundCurrencyId)
				//if the var is empty, assign current currency to it
				if (defaultCurrencyId.isEmpty())
					defaultCurrencyId = bean.getCurrencyId();
				//if the var is not empty and not equals to current currency, use ops entity default currency and flip the default currency check.
				else
					if (!defaultCurrencyId.equalsIgnoreCase(bean.getCurrencyId())) {
						defaultCurrencyId = process.getOpsEntityHomeCurrency(searchResultBean.getOpsEntityId());
						foundCurrencyId = true;
					}
			
			if (bean.getQuantity() == null) {
				bean.setQuantity(new BigDecimal(0));
			}
	
			String polineStatus = "";
			if ( "Y".equalsIgnoreCase(bean.getEverConfirmed()) && new BigDecimal(0).compareTo(bean.getQuantity()) == 0 && bean.getDateConfirmed() != null ) {
				polineStatus = "Canceled";
				bean.setBlnPoLineReadOnly(true);
				bean.setPoLineClosed(true);//new
			} else if ( ( bean.getQuantityReceived().compareTo(bean.getQuantity()) == 0 || bean.getQuantityReceived().compareTo(bean.getQuantity()) == 1 ) 
		    		  && "Y".equalsIgnoreCase( bean.getEverConfirmed() ) && bean.getDateConfirmed() != null ) {
				polineStatus = "Closed";
				bean.setBlnPoLineReadOnly(true);
				bean.setPoLineClosed(true);//new
			} else {
		    	  polineStatus = bean.getPoLineStatus();
		    	  bean.setBlnPoLineReadOnly(false);
		    	  bean.setPoLineClosed(false);//new
			}
			bean.setPoLineStatus(polineStatus); 
			bean.setStatus(polineStatus);
			bean.setGhsCompliant(process.getGhsCompliant(bean.getItemId()));
			//this is for show/hide of the browse item id look up button
			bean.setItemIdEditable(true);
			if (( bean.getAmendment().intValue() > 0 ) || ( bean.getPrShipTo() != null && bean.getDateConfirmed() != null ) ||
					("Y".equalsIgnoreCase( bean.getEverConfirmed() )) && ( bean.getItemId() != null || 
							( bean.getQuantityReceived().compareTo(bean.getQuantity()) == 0 || bean.getQuantityReceived().compareTo(bean.getQuantity()) == 1 )))  {
				bean.setItemIdEditable(false);
			}
			
			if (bean.getUnitPrice() != null )
				bean.setUnitPrice(bean.getUnitPrice().setScale(4,4));
			if (bean.getExtendedPrice() != null )
				bean.setExtendedPrice(bean.getExtendedPrice().setScale(4,4));
				
			if ("Y".equalsIgnoreCase(bean.getEverConfirmed()))
				bean.setBlnPoLineReadOnly(true);
			
		    BigDecimal rowNumber = bean.getPoLine()!=null?bean.getPoLine():new BigDecimal(0);	
		    rowNumber = rowNumber.divide(new BigDecimal("1000"));
		    rowNumber.setScale(1,3);
		    bean.setPoLineNumber(rowNumber.intValue()); 
		    
			bean.setLineAmendmentNum(bean.getAmendment() != null  ? bean.getPoLineNumber() + "/" + bean.getAmendment().toPlainString():"");
		    
		    bean.setIsMaterialTypeItem((!itemTypes.contains(bean.getItemType()))? "Y":"N");
		    
		    if (!StringHandler.isBlankString(bean.getShelfLifeDays())) {
		    	String shelfLifeBasis = bean.getShelfLifeBasis();
		    	StringBuffer shelfLifeBasisMsg = new StringBuffer(bean.getShelfLifeDays());
		        if (shelfLifeBasis.equalsIgnoreCase("M"))
		        	shelfLifeBasisMsg.append(" Basis: Days from DOM");
		        else if (shelfLifeBasis.equalsIgnoreCase("S"))
		        	shelfLifeBasisMsg.append(" Basis: Days from DOS");
		        else if (shelfLifeBasis.equalsIgnoreCase("R"))
		        	shelfLifeBasisMsg.append(" Basis: Days from DOR");
		        else if (shelfLifeBasis.equalsIgnoreCase("P"))
		        	shelfLifeBasisMsg.append(" Basis: Days from DOP");
		        
		        bean.setShelfLifeBasisMsg(shelfLifeBasisMsg.toString());
		    } else {
		    	bean.setShelfLifeBasisMsg("");
		    }						    
		    
		    boolean validItem = false;
		    if (bean.getItemId() != null && bean.getItemId().intValue() > 0) {
		    	bean.setValidItem("Yes");
		    	validItem = true;
		    } else { 
		    	bean.setValidItem("No");
		    	validItem = false;
		    }
		      
	        if(isOneUSD || "USA".equalsIgnoreCase(personnelBean.getCountryAbbrev()) || !"USD".equalsIgnoreCase(bean.getCurrencyId())) {
	        	isOneUSD = true;
	        } 
	        	        
	        if ("USD".equalsIgnoreCase(bean.getCurrencyId()) )
	        	bean.setCurrencyConversionRate(new BigDecimal(1));
	        else 
	        	bean.setCurrencyConversionRate(process.getConverionRate(bean.getCurrencyId(), input.getRadianPo().toString()));
	        
		    if (!itemTypes.contains(bean.getItemType())) {
		    	if ((bean.getItemId() != null && bean.getItemId().intValue() > 0)  
					&& validItem 
					&& !(bean.getPoLineStatus().equals("Remove")) ) //&& (canassignaddcharge.value == "Y") -this needs to be added as well
	            {
		    		PoLineItemListBean polinebean = new PoLineItemListBean(); 
		    		polinebean.setId(innercount + "");
		    		polinebean.setType(bean.getItemType());
		    		polinebean.setPoLine(bean.getPoLine());
		    		polinebean.setPoLineNumber(bean.getPoLineNumber());
		    		polinebean.setItemId(bean.getItemId());
		    		polinebean.setDescription(bean.getItemDesc());
		    		polinebean.setQuantity(bean.getQuantity());
		    		poLineItemListBeanCol.add(polinebean);
					innercount++;
	            }
	    		bean.setCanAssignAddCharge("Y");
		    } else if (itemTypes.contains(bean.getItemType())) {
		    	String relatedPoLines = process.getPoLineForAddCharge(
		    			bean.getRadianPo().toPlainString(), 
		    			bean.getPoLine().toPlainString(), 
		    			bean.getAmendment().toPlainString());
		    	bean.setRelatedPoLines(relatedPoLines);
		    }
			if (bean.getQuantity() != null && bean.getUnitPrice() != null && bean.getCurrencyId() != null) {
				String dateToUseStr = "";
				ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocale(request));
				SimpleDateFormat sdf = new SimpleDateFormat(library.getString("java.dateformat"));
				if (bean.getLastConfirmed() != null)
					dateToUseStr = sdf.format(bean.getLastConfirmed());
				else if (bean.getDateConfirmed() != null)
					dateToUseStr = sdf.format(bean.getDateConfirmed());
				linesData += bean.getQuantity().multiply(bean.getUnitPrice()).toPlainString() + "," + bean.getCurrencyId() + "," + dateToUseStr + ";";
			}
		}
		if (isOneUSD) {  //get order total with currency conversion
			for (PoLineDetailViewBean bean : poLineDetCol) {
				BigDecimal lineAmtInUSD = new BigDecimal("0");
				if ( bean.getQuantity() != null && bean.getUnitPrice() != null )
		        {
					BigDecimal qtyF= bean.getQuantity();
		        	BigDecimal unitPrice= bean.getUnitPrice();
		        	lineAmtInUSD = qtyF.multiply(unitPrice );		        	
					orderTotalInUSD = orderTotalInUSD.add(process.recalTotWithConversion(lineAmtInUSD, bean.getCurrencyId(), input.getRadianPo().toString()));
		        }
			}
    	}
    	
		request.setAttribute("orderTotalInUSD", orderTotalInUSD);
		request.setAttribute("poLineDetailCol", poLineDetCol);
		request.setAttribute("poLineItemListCol", poLineItemListBeanCol);
		
		request.setAttribute("defaultCurrencyId", (defaultCurrencyId == "" ? "USD" : defaultCurrencyId));
		request.setAttribute("totalCost", process.getPoLineTotal(defaultCurrencyId, linesData));
	}	
	   
	private void loadPageData() throws BaseException {
		request.setAttribute("pageId", "purchaseOrder");
		PurchaseOrderProcess process = new PurchaseOrderProcess(getDbUser());
		request.setAttribute( "poCategoryCol", process.getCategory());
		request.setAttribute( "poCompanyCol", process.getCompanyDropDown());
		request.setAttribute( "poBuyerNamesCol", process.getBuyerDropDown());
		request.setAttribute( "poStatusCol", process.getOrderStatus());
		request.setAttribute( "poLockStatusCol", process.getOrderStatus("Y"));
		request.setAttribute( "poSetStatusCol", process.getOrderStatus("setonly") );
		request.setAttribute( "poFobCol", process.getFob());		
		request.setAttribute( "poAddChargeTypeCol", process.getAddChargeType());
		request.setAttribute( "poSecondarySupplierTypesCol", process.getSecondarySupplierTypes());
		request.setAttribute( "poPaymentTermsCol", process.getPaymentTerms(false));	
		request.setAttribute( "poContactTypeCol", process.getContactType());
		request.setAttribute( "poCurrency", process.getCurrencyType());
		request.setAttribute( "poHubs", process.getHubs());
	}	
}
