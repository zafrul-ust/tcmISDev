package com.tcmis.internal.hub.action;

import java.util.Collection;

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
import com.tcmis.internal.hub.beans.ExpertReviewItemBean;
import com.tcmis.internal.hub.beans.ExpertReviewItemInputBean;
import com.tcmis.internal.hub.process.ExpertReviewItemProcess;


/******************************************************************************
 * Controller for shipping info
 * @version 1.0
     ******************************************************************************/
public class ExpertReviewItemAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "expertreviewitem");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// set the standalone flag to true since MSDS Maintenance should work as a standalone app
			request.setAttribute("standalone", "true");
			// Now send the user to the login page
			next = mapping.findForward("login");
		}
		else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			ExpertReviewItemInputBean input = new ExpertReviewItemInputBean(form);
			ExpertReviewItemProcess process = new ExpertReviewItemProcess(getDbUser());
			if (input.isUpdate()) {
				Collection<ExpertReviewItemBean> expertReviewList = BeanHandler.getBeans((DynaBean) form, "expertReviewItemBean", "datetimeformat", new ExpertReviewItemBean(), getTcmISLocale());
				try {
					process.update(input.getItem(), user, expertReviewList);
				}
				catch(BaseException e) {
					request.setAttribute("tcmisError", e.getMessage());
		            log.error(e);
				}
				input.setuAction("search");
			}
			if (input.isSearch()) {
				request.setAttribute("itemDesc", process.getItemDesc(input.getItem()));
				request.setAttribute("expertReviewItemColl", process.search(input.getItem()));
			}
		}
		
		return next;
	}
}
