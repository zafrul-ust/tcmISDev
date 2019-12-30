package com.tcmis.client.report.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.util.Calendar;
import java.text.DateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.client.common.process.ShoppingCartProcess;
import com.tcmis.client.order.beans.RequestLineItemBean;
import com.tcmis.client.report.beans.MovementOfMaterialInputBean;
import com.tcmis.client.report.beans.WorkareaMovementPermViewBean;
import com.tcmis.client.report.process.MovementOfMaterialProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class MovementOfMaterialAction extends TcmISBaseAction
{
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
			if (!this.isLoggedIn(request)) {
			 request.setAttribute("requestedPage", "movementofmaterial");
			 request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			 return (mapping.findForward("login"));
			}
			
			
			MovementOfMaterialProcess process = new MovementOfMaterialProcess(this.getDbUser(request),getTcmISLocaleString(request));
			// copy date from dyna form to the data bean
			MovementOfMaterialInputBean inputBean = new MovementOfMaterialInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
			
			/* Need to check if the user has permissions to view this page. if they do not have the permission
	        we need to not show them the page. */
			if("new".equals(inputBean.getuAction())){
				Vector<RequestLineItemBean> lineBean = (Vector) process.getLineInfo(inputBean.getFromPrNumber(), inputBean.getFromLineItem());
				Vector<WorkareaMovementPermViewBean>  workAreaColl = (Vector) process.getWorkAreaDropdown(personnelBean, lineBean.get(0));
		//        if (!personnelBean.getPermissionBean().hasApplicationPermission("GenerateOrders", null, inputBean.getFacilityId(), null) ||
				if (workAreaColl == null || workAreaColl.size() == 0)
		        {
					request.setAttribute("closeTransitWindow", "Y");
		            return (mapping.findForward("nopermissions"));
		        }
		        else {
		        	// Create New MR
		        	ShoppingCartProcess shoppingCartProcess = new ShoppingCartProcess(this.getDbUser(request),getTcmISLocaleString(request));
		        	CatalogInputBean cBean = new CatalogInputBean();
		        	cBean.setAccountSysId(inputBean.getAccountSysId());
		        	
		        	ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
		        	shoppingCartBean.setFacilityId(inputBean.getFacilityId());
		        	shoppingCartBean.setApplication(workAreaColl.get(0).getApplication()); // default the value of Work Area to the first one
		        	shoppingCartBean.setCatPartNo(lineBean.get(0).getFacPartNo());
		        	shoppingCartBean.setStockingMethod(lineBean.get(0).getItemType()); // Item_Type or requested_item_type
		        	shoppingCartBean.setCatalogId(lineBean.get(0).getCatalogId());
		        	shoppingCartBean.setCatalogCompanyId(lineBean.get(0).getCatalogCompanyId());
		        	shoppingCartBean.setExamplePackaging(lineBean.get(0).getExamplePackaging());
		        	shoppingCartBean.setPartGroupNo(lineBean.get(0).getPartGroupNo());
		        	shoppingCartBean.setInventoryGroup(lineBean.get(0).getInventoryGroup());
                    if(lineBean.get(0).getItemId() != null)
                        shoppingCartBean.setItemId(lineBean.get(0).getItemId().toString());
		        	if(lineBean.get(0).getExampleItemId() != null)
		        		shoppingCartBean.setExampleItemId(lineBean.get(0).getExampleItemId().toString());
		        	shoppingCartBean.setCatalogPrice(lineBean.get(0).getCatalogPrice());
		        	shoppingCartBean.setBaselinePrice(lineBean.get(0).getBaselinePrice());
		        	shoppingCartBean.setDateNeeded(lineBean.get(0).getRequiredDatetime());
		        	shoppingCartBean.setNotes(lineBean.get(0).getNotes());
		        	if("y".equalsIgnoreCase(lineBean.get(0).getCritical()))
		        		shoppingCartBean.setCritical("true");
		        	else
		        		shoppingCartBean.setCritical("false");
		        	
		        	shoppingCartBean.setQuantity(BigDecimal.ONE);
		        	shoppingCartBean.setCurrencyId(lineBean.get(0).getCurrencyId());
		        	shoppingCartBean.setOrderQuantityRule(lineBean.get(0).getOrderQuantityRule());
		        	shoppingCartBean.setChargeType(lineBean.get(0).getChargeType());
		        	shoppingCartBean.setPoNumber(lineBean.get(0).getPoNumber());
		        	shoppingCartBean.setReleaseNumber(lineBean.get(0).getReleaseNumber());
		        	Collection beans = new Vector<ShoppingCartBean>();
		        	beans.add(shoppingCartBean);
		        	
		        	BigDecimal prNumber = shoppingCartProcess.buildNewRequest(cBean, beans, personnelId);
		        	
		        	// Get the data for the page
		        	MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getDbUser(request), getTcmISLocaleString(request));
		        	MaterialRequestInputBean mrBean = new MaterialRequestInputBean();
		        	mrBean.setPrNumber(prNumber);
		 
		        	request.setAttribute("materialRequestData", mrProcess.getMrData(mrBean, personnelBean));
		        	
		        	request.setAttribute("workAreaColl", workAreaColl);
		        	return (mapping.findForward("success"));
		        }
			}
			else if ("workAreaChanged".equals(inputBean.getuAction()))
	        {
				 process.updateLine(inputBean.getPrNumber(), inputBean.getToApplication(), null, null, inputBean.getFacilityId());
				 
				 MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getDbUser(request), getTcmISLocaleString(request));
		         MaterialRequestInputBean mrBean = new MaterialRequestInputBean();
		         mrBean.setPrNumber(inputBean.getPrNumber());
		 
		         request.setAttribute("materialRequestData", mrProcess.getMrData(mrBean, personnelBean));
		         
		         Vector<RequestLineItemBean> lineBean = (Vector) process.getLineInfo(inputBean.getFromPrNumber(), inputBean.getFromLineItem());
				 Vector<WorkareaMovementPermViewBean>  workAreaColl = (Vector) process.getWorkAreaDropdown(personnelBean, lineBean.get(0));	
		         request.setAttribute("workAreaColl", workAreaColl);
	             return (mapping.findForward("success"));
	        }else if ("update".equals(inputBean.getuAction())) {
				 MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getDbUser(request), getTcmISLocaleString(request));
		         MaterialRequestInputBean mrBean = new MaterialRequestInputBean();
		         
		         BeanHandler.copyAttributes(form, mrBean, getTcmISLocale(request));

                 mrBean.setRemark("Work area transfer MR");
                 mrBean.setDeliveryType("Deliver by");
		         mrBean.setOneTimeShipToChanged("N");
                 mrBean.setDefaultNeedByDate(new Date());
                 //this is so that it will run the test for charge numbers
                 mrBean.setViewType("SUBMIT");

                 Collection beans = new Vector<MaterialRequestInputBean>();
		         beans.add(mrBean);

                 Vector errorMsgs = new Vector();
				 String errorMsg = mrProcess.saveMr( mrBean, beans, null);
                 if (!"OK".equalsIgnoreCase(errorMsg)) {
					 errorMsgs.add(errorMsg);
				 }else {
                     //check to make sure that MR does not required approval
                     errorMsg = mrProcess.approvalNeeded(mrBean,beans,personnelBean);
                     if (!"OK".equalsIgnoreCase(errorMsg)) {
                        errorMsgs.add(errorMsg);
                     }else {
                         //set procedure to move material from old to new MR
                         errorMsg = process.doMoveMatlToWorkArea(inputBean.getFromPrNumber(), inputBean.getFromLineItem(), mrBean.getQty(), mrBean.getPrNumber(), mrBean.getLineItem(),personnelId);
                         if (!"OK".equalsIgnoreCase(errorMsg)) {
                             errorMsgs.add(errorMsg);
                         }else {
                            //set MR status
                            process.updateLine(mrBean.getPrNumber(), null, "Delivered", "Delivered", null);
                            process.updateMR(mrBean.getPrNumber(), "posubmit");
                         }
                     }
                 }

                 if(errorMsgs != null && errorMsgs.size() > 0) {
					 request.setAttribute("tcmISErrors", errorMsgs);
                     request.setAttribute("materialRequestData", mrProcess.getMrData(mrBean, personnelBean));
                     Vector<RequestLineItemBean> lineBean = (Vector) process.getLineInfo(inputBean.getFromPrNumber(), inputBean.getFromLineItem());
                     Vector<WorkareaMovementPermViewBean>  workAreaColl = (Vector) process.getWorkAreaDropdown(personnelBean, lineBean.get(0));
                     request.setAttribute("workAreaColl", workAreaColl);
                 }else {
                     request.setAttribute("refreshOpener", "Y");
                     request.setAttribute("done", "Y");
                 }
                 return (mapping.findForward("success"));
	        }
			else if ("delete".equals(inputBean.getuAction()))
	        {
				 process.deleteMR(inputBean.getPrNumber());
	             request.setAttribute("done", "Y");
	             return (mapping.findForward("success"));
	        }
			else{	
				
				return (mapping.findForward("success"));
			}
	}
}