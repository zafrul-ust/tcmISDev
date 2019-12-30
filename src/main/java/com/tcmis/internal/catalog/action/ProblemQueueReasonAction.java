package com.tcmis.internal.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ProblemQueueReasonInputBean;
import com.tcmis.internal.catalog.process.ProblemQueueReasonProcess;

public class ProblemQueueReasonAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "success";
		ProblemQueueReasonInputBean input = new ProblemQueueReasonInputBean();
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		BeanHandler.copyAttributes((DynaBean)form, input);
		ProblemQueueReasonProcess process = new ProblemQueueReasonProcess(user.getPersonnelIdBigDecimal(), getDbUser());
		if (input.isOpen()) {
			request.setAttribute("problemTypes", process.getProblemTypes(input));
		}
		else if (input.isSubmit()) {
			request.setAttribute("success", process.updateProblemQueue(input, user));
		}
		else if (input.isShowHistoryAction()) {
			request.setAttribute("problemHistoryColl", process.getProblemHistory(input));
			forward = "history";
		}
		return mapping.findForward(forward);
	}

}
