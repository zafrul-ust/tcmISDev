package com.tcmis.internal.msds.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.msds.beans.MsdsInputBean;
import com.tcmis.internal.msds.process.MsdsProcess;

/******************************************************************************
 * Controller for show login page
 * @version 1.0
 ******************************************************************************/
public class ViewMsdsLocationAction
extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		MsdsInputBean bean = new MsdsInputBean(true);
		BeanHandler.copyAttributes(form, bean);
		MsdsProcess process = new MsdsProcess(this.getDbUser(request));
		request.setAttribute("currentMsdsLoadViewBeanCollection",
				process.getLocations(bean));
		String forward = "success";
		if(log.isDebugEnabled()) {
			log.debug("forward:" + forward);
		}
		return (mapping.findForward(forward));
	}
}
