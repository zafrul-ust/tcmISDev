package com.tcmis.internal.hub.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Mar 14, 2008
 * Time: 10:53:31 AM
 * To change this template use File | Settings | File Templates.
 */
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.DpmsAddressInputBean;
import com.tcmis.internal.hub.process.DpmsAddressProcess;

/******************************************************************************
 * Controller for
 * @version 1.0
 ******************************************************************************/

public class DpmsAddressAction
extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			//	 request.setAttribute("requestedPage", "freightadvicemain");
			//	 request.setAttribute("requestedURLWithParameters",
			//		this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		String forward = "nododaac";
		if (form != null) {
			DpmsAddressProcess process = new DpmsAddressProcess(this.getDbUser(request));
			DpmsAddressInputBean inputBean = new DpmsAddressInputBean();
			BeanHandler.copyAttributes(form, inputBean, this.getResourceBundleValue(request, "java.datetimeformat"));
			inputBean.setPersonnelId(new BigDecimal(this.getPersonnelId(request)));
			/*
     if("dpms".equalsIgnoreCase(inputBean.getType())) {
         forward = "dpms";
     }
     else if("nol".equalsIgnoreCase(inputBean.getType())) {
         forward = "nol";
     }
     else if("nododaac".equalsIgnoreCase(inputBean.getType())) {
         forward = "nododaac";
     }
			 */
			if ( ( (DynaBean) form).get("submitSearch") != null &&
					( (String) ( (DynaBean) form).get("submitSearch")).equalsIgnoreCase("submitSearch")) {
				//this.saveTcmISToken(request);
				log.debug("search");
				request.setAttribute("locationSearchViewBeanCollection",  process.getAddressList(inputBean));
				request.setAttribute("type", inputBean.getType());
				forward = "list";
			}
			else if ( ( (DynaBean) form).get("submitUpdate") != null &&
					( (String) ( (DynaBean) form).get("submitUpdate")).length() > 0) {
				//this.saveTcmISToken(request);
				log.debug("update");
				process.updateAddress(inputBean);
			}
			else if ( ( (DynaBean) form).get("submitNew") != null &&
					( (String) ( (DynaBean) form).get("submitNew")).length() > 0) {
				//this.saveTcmISToken(request);
				log.debug("new");
				forward = "new";
				request.setAttribute("type", inputBean.getType());
				//VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request));
				//request.setAttribute("vvCountryBeanCollection", vvDataProcess.getNormalizedCountryState());
				//process.updateAddress(inputBean);
			}
			else if ( ( (DynaBean) form).get("addNewAddress") != null &&
					( (String) ( (DynaBean) form).get("addNewAddress")).length() > 0) {
				//this.saveTcmISToken(request);
				log.debug("add");
				process.addAddress(inputBean);
			}
			else if ( ( (DynaBean) form).get("addressChangeRequestId") != null) {
				if (log.isDebugEnabled()) {
					log.debug("find addressChangeRequestId:"+inputBean.getAddressChangeRequestId());
				}
				/*
        if (!this.isTcmISTokenValid(request, true)) {
		    BaseException be = new BaseException("Duplicate form submission");
		    be.setMessageKey("error.submit.duplicate");
		    throw be;
		}
        this.saveTcmISToken(request);
				 */

				request.setAttribute("custPoAddressChangeViewBeanCollection",  process.getRecord(inputBean));
				request.setAttribute("type", inputBean.getType());
				request.setAttribute("addressChangeRequestId", inputBean.getAddressChangeRequestId());
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("forward to:" + forward);
		}
		return (mapping.findForward(forward));
	}
}
