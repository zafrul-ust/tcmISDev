package com.tcmis.client.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogAddFormBean;
import com.tcmis.client.catalog.process.CatalogAddFormProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class CatalogAddFormAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!this.isLoggedIn(request)) {
			request.setAttribute("tcmISError",getResourceBundleValue(request, "label.loginmsg"));
		}
		else if (form != null) {
			//copy date from dyna form to the data form
			CatalogAddFormBean bean = new CatalogAddFormBean();
			BeanHandler.copyAttributes(form, bean);

			if (bean.isUpdate()) {
				try {
					new CatalogAddFormProcess(getDbUser()).saveForm(bean);
				}
				catch (Exception e) {
					request.setAttribute("tcmISError",getResourceBundleValue(request, "error.db.update") + " : " + e.getMessage());
				}
			}
		}
		return next;
	}
}
