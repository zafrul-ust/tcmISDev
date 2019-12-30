package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.CountInterpolationBean;
import com.tcmis.client.common.beans.TankLevelCountBean;
import com.tcmis.client.common.process.ClientCabinetCountInterpolationProcess;
import com.tcmis.client.common.process.ClientCabinetTankLevelCountProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CabinetLevelInputBean;
import com.tcmis.internal.hub.process.CabinetLevelProcess;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class ClientCabinetCountInterpolationAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		ActionForward next = mapping.findForward("success");
		
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientcabinetinterpolation");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");		
		} else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			String userAction = request.getParameter("uAction");

			if (form != null && userAction != null) {
				DynaBean dynaForm = (DynaBean) form;

				if (userAction.equals("update")) {
					Collection<CountInterpolationBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "countInterpolation", new CountInterpolationBean(), getLocale(request), "updated");
					ClientCabinetCountInterpolationProcess process = new ClientCabinetCountInterpolationProcess(this.getDbUser(request), getTcmISLocale(request));
					String binId = (String) request.getParameter("binId");
					if (updatedRows != null && !updatedRows.isEmpty()) {												
						//HashMap<String, Object> returnMessage = process.updateInterpolationCount(updatedRows, user);
						HashMap<String, Object> returnMessage = process.upsertInterpolationCount(updatedRows, user);
						//need to capture the error message and save the upload sequence - in case there is an error else no need to save the upload sequence
						if (returnMessage != null && returnMessage.size() > 0 && (String)returnMessage.get("tcmISError") != null) {
							request.setAttribute("tcmISError", (String)returnMessage.get("tcmISError"));							
						} else 
							request.setAttribute("updateSuccess", "Y");
					} else 
						request.setAttribute("gridEmpty", "Y");					
					request.setAttribute("countInterpolationCol", process.getCountInterpolation(binId));
				} else {
					ClientCabinetCountInterpolationProcess process = new ClientCabinetCountInterpolationProcess(this.getDbUser(request), getTcmISLocale(request));
					CountInterpolationBean inputBean = new CountInterpolationBean(); 
					BeanHandler.copyAttributes(dynaForm, inputBean, user.getLocale());
					request.setAttribute("countInterpolationCol", process.getCountInterpolation(inputBean));
				}
			}
		}
		return next;
	}
}