package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import com.tcmis.common.util.FileHandler;
import com.tcmis.internal.supply.beans.ShipmentDocumentInputBean;
import com.tcmis.internal.supply.process.DeliveryDocumentProcess;

public class DeliveryDocumentAction extends TcmISBaseAction {
	 public ActionForward executeAction(ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws
				BaseException, Exception {

				if (!this.isLoggedIn(request,true)) {
				 request.setAttribute("requestedPage", "showaddshipmentdocument");
				 request.setAttribute("requestedURLWithParameters",
													 this.getRequestedURLWithParameters(request));
				 return (mapping.findForward("login"));
				}
				
				// If you need access to who is logged in
				PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

				/* Need to check if the user has permissions to view this page. if they do not have the permission
					  we need to not show them the page.*/
				if (!user.getPermissionBean().hasUserPagePermission("shipmentHistory"))
				{
				   return (mapping.findForward("nopermissions"));
				}

				ShipmentDocumentInputBean shipmentDocInputBean = new 	ShipmentDocumentInputBean();
				DeliveryDocumentProcess deliveryDocProcess = new DeliveryDocumentProcess(this.getDbUser(request),getTcmISLocaleString(request));
				if (form != null) {
				 BeanHandler.copyAttributes(form, shipmentDocInputBean, getTcmISLocale(request));
				}

				if (shipmentDocInputBean.getShipmentId() == null) {
				 return mapping.findForward("systemerrorpage");
				}
				else if (form != null && shipmentDocInputBean.getUpdateDocuments() != null &&
						shipmentDocInputBean.getUpdateDocuments().length() > 0) {
				 DynaBean dynaForm = (DynaBean) form;
				 List shipmentDocViewBeans = (List) dynaForm.get("shipmentDocumentViewBean");
				 Iterator iterator = shipmentDocViewBeans.iterator();
				 int deleteCount = 0;
				 
				 checkToken(request);
				 Collection shipDocInputBeanColl = new Vector();
				 while (iterator.hasNext()) {
					org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
					 commons.beanutils.LazyDynaBean) iterator.next();

					ShipmentDocumentInputBean shipmentDocUpdateInputBean = new
					ShipmentDocumentInputBean();
					BeanHandler.copyAttributes(lazyBean, shipmentDocUpdateInputBean, getTcmISLocale(request));
					if (shipmentDocUpdateInputBean.getOk().equalsIgnoreCase("true")) {
					 deleteCount++;
					 shipDocInputBeanColl.add(shipmentDocUpdateInputBean);
					}
				 }

				 
				 if (deleteCount > 0) {
					int documentDeleteCount = deliveryDocProcess.deleteDocument(
							shipDocInputBeanColl);
				 }
				 
				 
				 request.setAttribute("deliverDocViewBeanColl", deliveryDocProcess.getSearchResult(shipmentDocInputBean));
				 return (mapping.findForward("showShipmentDocuments"));
				}
				else if (form != null && shipmentDocInputBean.getSubmitSave() != null && shipmentDocInputBean.getSubmitSave().length() > 0) {
					if(shipmentDocInputBean.getTheFile() != null && !FileHandler.isValidUploadFile(shipmentDocInputBean.getTheFile())) {
						VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
						request.setAttribute("vvShipmentDocTypeBeanColl", deliveryDocProcess.getVvShipmentDocumentType());
						request.setAttribute("errorMessage", "File type not allowed.");
						return (mapping.findForward("success"));
					 }	
					
				 PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
				 BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
				 shipmentDocInputBean.setEnteredBy(personnelId);

				 String addNewDocumentUrl = "";
				 String errormessage = "";
				 addNewDocumentUrl = deliveryDocProcess.addNewDocument(shipmentDocInputBean);
				 if (addNewDocumentUrl != null && addNewDocumentUrl.length() > 0) {
					request.setAttribute("showdocument", "Yes");
					request.setAttribute("newDocumentUrl", addNewDocumentUrl);
					if("Y".equalsIgnoreCase(shipmentDocInputBean.getConfirmDelivery()))
					{
						errormessage = deliveryDocProcess.deliveryConfirm(shipmentDocInputBean);
						if (errormessage != null && errormessage.length() > 0)
						{
							request.setAttribute("errorMessage", errormessage);
						}
					}
				 }
				 
				 else {
					VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
					request.setAttribute("vvShipmentDocTypeBeanColl",
							deliveryDocProcess.getVvShipmentDocumentType());
					
					request.setAttribute("errorMessage", "Document not Added");
				 }

				 return (mapping.findForward("success"));
				}
				else if (shipmentDocInputBean.getShowDocuments() != null &&  shipmentDocInputBean.getShowDocuments().length() > 0) {
				       
				 request.setAttribute("deliverDocViewBeanColl",deliveryDocProcess.getSearchResult(shipmentDocInputBean));
				 this.saveTcmISToken(request);
				 return (mapping.findForward("showShipmentDocuments"));
				}
				else {
				 
				 request.setAttribute("vvShipmentDocTypeBeanColl",
						 deliveryDocProcess.getVvShipmentDocumentType());

				
				 return mapping.findForward("success");
				} //end of method
			 }
			} //end of class
