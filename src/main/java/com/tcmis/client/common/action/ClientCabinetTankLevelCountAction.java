package com.tcmis.client.common.action;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.TankLevelCountBean;
import com.tcmis.client.common.process.ClientCabinetTankLevelCountProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class ClientCabinetTankLevelCountAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward next = mapping.findForward("success");
		
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientcabinettanklevelcount");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");		
		} else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			String userAction = request.getParameter("uAction");

			if (form != null && userAction != null) {
				DynaBean dynaForm = (DynaBean) form;

				if (userAction.equals("update")) {
					Collection<TankLevelCountBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "tankLevelCount", new TankLevelCountBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientCabinetTankLevelCountProcess process = new ClientCabinetTankLevelCountProcess(getDbUser());
						String uploadSequence = request.getParameter("uploadSequence");
						if (uploadSequence == null || uploadSequence.equalsIgnoreCase(""))
							uploadSequence = process.getNextUpLoadSeq().toString();
						HashMap<String, Object> returnMessage = process.insertTankLevelCount(updatedRows, user, uploadSequence);
						//need to capture the error message and save the upload sequence - in case there is an error else no need to save the upload sequence
						if (returnMessage != null && returnMessage.size() > 1 && (String)returnMessage.get("tcmISError") != null) {
							request.setAttribute("tcmISError", (String)returnMessage.get("tcmISError"));
							request.setAttribute("uploadSequence", (String)returnMessage.get("uploadSequence"));
							 if ( returnMessage.get("errTankCol") != null) {
								 request.setAttribute("tankLevelCountCol", (Collection<TankLevelCountBean>)returnMessage.get("errTankCol"));	 
							 } else {
								 request.setAttribute("tankLevelCountCol",updatedRows);	 
							 }
						} else {
							request.setAttribute("updateSuccess", "Y");
							if (returnMessage.get("lastQtyOnHand") != null)
								request.setAttribute("lastQtyOnHand", returnMessage.get("lastQtyOnHand"));
						}
					}					
				}
				else {
					ClientCabinetTankLevelCountProcess process = new ClientCabinetTankLevelCountProcess(getDbUser());
					TankLevelCountBean inputBean = new TankLevelCountBean(); 
					BeanHandler.copyAttributes(dynaForm, inputBean, user.getLocale());
					request.setAttribute("levelUnit", process.getLevelUnit(inputBean));
				}
			}
		}
		return next;
	}

}
