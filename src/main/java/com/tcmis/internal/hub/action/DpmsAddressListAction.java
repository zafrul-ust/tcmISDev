package com.tcmis.internal.hub.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Oct 14, 2008
 * Time: 3:30:36 PM
 * To change this template use File | Settings | File Templates.
 */
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

public class DpmsAddressListAction
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
		//forward params
		request.setAttribute("locationType", request.getParameter("locationType"));
		request.setAttribute("dodaac", request.getParameter("dodaac"));
		request.setAttribute("dodaacType", request.getParameter("dodaacType"));
		String forward = "success";
		if (form != null) {
			DpmsAddressProcess process = new DpmsAddressProcess(this.getDbUser(request));
			DpmsAddressInputBean inputBean = new DpmsAddressInputBean();
			BeanHandler.copyAttributes(form, inputBean);
			//	 log.debug("getSearchText="+inputBean.getSearchText());
			if ( ( (DynaBean) form).get("submitSearch") != null &&
					( (String) ( (DynaBean) form).get("submitSearch")).length() > 0 &&
					(inputBean.getDodaac().trim().length() > 0  || inputBean.getSearchText() != null)) {
				//this.saveTcmISToken(request);
				log.debug("searching");
				request.setAttribute("locationSearchViewBeanCollection",  process.getAddressList(inputBean));
				request.setAttribute("locationType", inputBean.getLocationType());
				//log.debug(request.getAttribute("locationSearchViewBeanCollection"));
			}

		}
		if (log.isDebugEnabled()) {
			log.debug("forward to:" + forward);
		}
		return (mapping.findForward(forward));
	}
}
