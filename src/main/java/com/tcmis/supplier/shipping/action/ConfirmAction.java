package com.tcmis.supplier.shipping.action;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.supplier.shipping.beans.*;
import com.tcmis.supplier.shipping.process.*;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
 ******************************************************************************/
public class ConfirmAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "confirmmain");
			request.setAttribute("requestedURLWithParameters", this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
	    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("confirm"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }

		String userAction = null;
		if (form == null
				|| (userAction = ((String) ((DynaBean) form).get("userAction"))) == null) {
			return (mapping.findForward("success"));
		}


    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		DynaBean dynaBean = (DynaBean) form;
		SupplierShippingInputBean inputbean = new SupplierShippingInputBean();
		BeanHandler.copyAttributes(dynaBean, inputbean);
		inputbean.setPersonnelId(personnelId);
    
    //	    radian.web.suppliershipping.supplierShipping abc = null;
		//if form is not null we have to perform some action
		if (userAction.equals("display")) {
			ShipmentSelectionProcess process = new ShipmentSelectionProcess(
					this.getDbUser(request));
			request.setAttribute("supplierLocationOvBeanCollection", process.getDLASearchDropDown(personnelId));
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		ConfirmProcess process = new ConfirmProcess(this.getDbUser(request));

		if (userAction.equals("search")) {
			this.saveTcmISToken(request);
			request.setAttribute("carrierBeanColl",process.getValidCarriers(inputbean));
			String transportationMode = ((String) ((DynaBean) form).get("transportationMode"));
			if( transportationMode.equals("ltltl")) {
				Object[] results = process.showResultLtltl(inputbean);		
				request.setAttribute("suppPackingViewBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				return (mapping.findForward("ltltlresult"));
			}
			else if( transportationMode.equals("parcel")) {
				Object[] results = process.showResultParcel(inputbean);		
				request.setAttribute("suppPackingViewBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				return (mapping.findForward("parcelresult"));
			}
		} else if (userAction.equals("update")) {
	    	if (!this.isTcmISTokenValid(request, true)) {
	   		 BaseException be = new BaseException("Duplicate form submission");
	   		 be.setMessageKey("error.submit.duplicate");
	   		 throw be;
	   		}
	   		this.saveTcmISToken(request);
		   		
			request.setAttribute("carrierBeanColl",process.getValidCarriers(inputbean));
			String transportationMode = ((String) ((DynaBean) form).get("transportationMode"));
			if( transportationMode.equals("ltltl")) {
				Collection beans = BeanHandler.getBeans(dynaBean,
						"supplierPackingViewBean", new GasPalletShipmentViewBean());
				String errorCode = process.shipConfirmsLtltl(beans);

				Object[] results = process.showResultLtltl(inputbean);		
				request.setAttribute("suppPackingViewBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				return (mapping.findForward("ltltlresult"));
			}
			else if( transportationMode.equals("parcel")) {
				Collection beans = BeanHandler.getBeans(dynaBean,
						"supplierPackingViewBean", new GasBoxShipmentViewBean());
				GasBoxShipmentViewBean bean = null;
				HashMap carrierMap = new HashMap();
				for (Object obj : beans) {
					if (obj == null)
						continue;
					bean = (GasBoxShipmentViewBean) obj;
					if( bean.getCurrentCarrierName() != null && bean.getCurrentCarrierName().length() > 0) 
						carrierMap.put(bean.getRadianPo()+"^^"+bean.getUsgovTcn(), bean );
				}
				for (Object obj : beans) {
					if (obj == null)
						continue;
					bean = (GasBoxShipmentViewBean) obj;
					GasBoxShipmentViewBean mapBean = (GasBoxShipmentViewBean)carrierMap.get(bean.getRadianPo()+"^^"+bean.getUsgovTcn());
					if( mapBean!= null )
							bean.setCurrentCarrierName(mapBean.getCurrentCarrierName());
					else 
							bean.setCurrentCarrierName("");
					if (personnelBean.getPermissionBean().hasSupplierLocationPermission("usGovShipConfirm",
							bean.getShipFromLocationId(),null,bean.getSupplier()))
					{
						bean.setOk("Ok");
					}
				}
				String errorCode = process.shipConfirmsParcel(beans);

				Object[] results = process.showResultParcel(inputbean);		
				request.setAttribute("suppPackingViewBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				request.setAttribute("tcmISError", errorCode);
				return (mapping.findForward("parcelresult"));
			}
		} 
/*		else if (userAction.equals("create")) {
	    	if (!this.isTcmISTokenValid(request, true)) {
	   		 BaseException be = new BaseException("Duplicate form submission");
	   		 be.setMessageKey("error.submit.duplicate");
	   		 throw be;
	   		}
	   		this.saveTcmISToken(request);
	   		
			request.setAttribute("carrierBeanColl",process.getValidCarriers(inputbean));
			String transportationMode = ((String) ((DynaBean) form).get("transportationMode"));
			if( transportationMode.equals("ltltl")) {
				Collection beans = BeanHandler.getBeans(dynaBean,
						"supplierPackingViewBean", new GasPalletShipmentViewBean());
				String errorCode = process.shipConfirmsLtltl(beans);

				Object[] results = process.showResultLtltl(inputbean);		
				request.setAttribute("suppPackingViewBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				return (mapping.findForward("ltltlresult"));
			}
			else if( transportationMode.equals("parcel")) {
				return (mapping.findForward("parcelresult"));
			}
//			request.setAttribute("tcmISError", errorCode);
		}  */
		else if (userAction.equals("confirm")) {
	   		
			String transportationMode = ((String) ((DynaBean) form).get("transportationMode"));
			if( transportationMode.equals("ltltl")) {
                request.setAttribute("packShipmentBeanCollection", process.getLtltlConfirmShipmentResult(inputbean));
			}
			else if( transportationMode.equals("parcel")) {
                request.setAttribute("packShipmentBeanCollection", process.getParcelConfirmShipmentResult(inputbean));
			}
			return (mapping.findForward("confirmshipment"));
	    }
		else if (userAction.equals("confirmshipment")) {
			String transportationMode = ((String) ((DynaBean) form).get("transportationMode"));
			SupplierPackingViewBean sbean = new SupplierPackingViewBean(); 
			BeanHandler.copyAttributes(dynaBean, sbean);
			String err = "";
			if( transportationMode.equals("ltltl") ) {
				Collection beans = BeanHandler.getBeans(dynaBean,
						"packShipConfirmInputBean", new GasPalletShipmentViewBean());
                err = process.confirmShipmentLtltl(beans,sbean);
			}
			else if( transportationMode.equals("parcel") ) {
				Collection beans = BeanHandler.getBeans(dynaBean,
						"packShipConfirmInputBean", new GasBoxShipmentViewBean());
                err = process.confirmShipmentParcel(beans,sbean);
			}
			if( err.equals("OK") )
					request.setAttribute("updateErrorFlag","Updated");
			else {
				if( transportationMode.equals("ltltl")) {
	                request.setAttribute("packShipmentBeanCollection", process.getLtltlConfirmShipmentResult(inputbean));
				}
				else if( transportationMode.equals("parcel")) {
	                request.setAttribute("packShipmentBeanCollection", process.getParcelConfirmShipmentResult(inputbean));
				}
				request.setAttribute("updateErrorFlag","Error");
				request.setAttribute("messageToUser", err);
			}
			return (mapping.findForward("confirmshipment"));
	    }
		else{
				return mapping.findForward("systemerrorpage");
	    }
		return (mapping.findForward("success"));
	}
}




