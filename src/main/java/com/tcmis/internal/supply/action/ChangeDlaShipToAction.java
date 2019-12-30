package com.tcmis.internal.supply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.ChangeDlaShipToInputBean;
import com.tcmis.internal.supply.process.ChangeDlaShipToViewProcess;

/******************************************************************************
 * Controller for ChangeDlaShipTo
 * @version 1.0
 ******************************************************************************/
public class ChangeDlaShipToAction
extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		//  login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "ChangeDlaShipTomain".toLowerCase());
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//  main
		//  	if( form == null )
		//      	return (mapping.findForward("success"));
		//  result
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		ChangeDlaShipToViewProcess process = new ChangeDlaShipToViewProcess(this.getDbUser(request),getTcmISLocaleString(request));
		ChangeDlaShipToInputBean inputBean = new ChangeDlaShipToInputBean();
		//	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		String uAction = (String) ( (DynaBean)form).get("uAction");
		if(log.isDebugEnabled()) {
			log.debug("uAction="+uAction);
		}
		if ( uAction.equals("update") ) {
			checkToken(request);
			log.debug(inputBean);
			request.setAttribute("tcmISError",process.update(inputBean));

			request.setAttribute("changeDlaShipToViewBeanCollection", process.getSearchResult(inputBean));
			return (mapping.findForward("success"));
		}

		else {
			saveTcmISToken(request);
			request.setAttribute("changeDlaShipToViewBeanCollection", process.getSearchResult(inputBean));
			return (mapping.findForward("success"));
		}


	}

}