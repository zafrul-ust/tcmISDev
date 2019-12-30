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
	public class ShipmentSelectionAction extends TcmISBaseAction {

	  public ActionForward executeAction(ActionMapping mapping,
	                                     ActionForm form,
	                                     HttpServletRequest request,
	                                     HttpServletResponse response) throws
	      BaseException, Exception {
	    if (!this.isLoggedIn(request)) {
	      request.setAttribute("requestedPage", "usgovshpselmain");
	      request.setAttribute("requestedURLWithParameters",
	                           this.getRequestedURLWithParameters(request));
	      return (mapping.findForward("login"));
	    }
        PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
        BigDecimal personnelId = personnelBean.getPersonnelIdBigDecimal();
        if (!personnelBean.getPermissionBean().hasUserPagePermission("usGovShipmentSelection"))
        {
            return (mapping.findForward("nopermissions"));
        }

		String action = null;
		if (form == null || (action = ((String) ((DynaBean) form).get("_action"))) == null) {
			return (mapping.findForward("success"));
		}
	    
	    ShipmentSelectionProcess process = new ShipmentSelectionProcess(this.getDbUser(request),getTcmISLocaleString(request));
	    if ( action.equals("display")) {
	      request.setAttribute("supplierLocationOvBean",
	                           process.getDLASearchDropDown(personnelId));
	      this.saveTcmISToken(request);
	    }
	    else if ( action.equals("search")) {
		      SupplierShippingInputBean inputbean = new SupplierShippingInputBean(); 
			  BeanHandler.copyAttributes(form, inputbean, getTcmISLocale(request));
			  inputbean.setPersonnelId(personnelId);
			  Vector v = (Vector) process.getSearchResult(inputbean);
			  request.setAttribute("suppliershippingViewBeanCollection", v);
			  this.saveTcmISToken(request);
			  //request.getSession().setAttribute("SSinputbean",inputbean);
		    }
	    else if ( action.equals("createExcel")) {
		      SupplierShippingInputBean inputbean = new SupplierShippingInputBean(); 
			  BeanHandler.copyAttributes(form, inputbean, getTcmISLocale(request));
			  inputbean.setPersonnelId(personnelId);
			  Vector v = (Vector) process.getSearchResult(inputbean);
			  this.setExcel(response,"ShipmentSelection");
		      process.getExcelReport(v, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
		      return noForward;
		      }
	    else if ( action.equals("reloadAfterConfirm")) {
			if (!this.isTcmISTokenValid(request, true)) {
				BaseException be = new BaseException("Duplicate form submission");
				be.setMessageKey("error.submit.duplicate");
				throw be;
			}
			this.saveTcmISToken(request);
			SupplierShippingInputBean inputbean = new SupplierShippingInputBean();
			BeanHandler.copyAttributes(form, inputbean, getTcmISLocale(request));
			inputbean.setPersonnelId(personnelId);
			request.setAttribute("suppliershippingViewBeanCollection", process.getSearchResult(inputbean));
			request.setAttribute("picklistId", inputbean.getPicklistId());
		}
	    return (mapping.findForward("success"));
	  }
	}
