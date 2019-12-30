package com.tcmis.internal.catalog.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbUpdateException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.AddUserToEmailListBean;
import com.tcmis.internal.catalog.process.AddUserToEmailListProcess;

public class AddUserToEmailListAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "addusertoemaillistmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		// Get the user
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		/*// Verify view permissions from customer.user_page
		if (!user.getPermissionBean().hasUserPagePermission("hoaSamplePage")) {
			return mapping.findForward("nopermissions");
		}*/
		request.setAttribute("personnelCompanyId", user.getCompanyId());
		
		AddUserToEmailListProcess process = new AddUserToEmailListProcess(this.getDbUser(request));
		if (form != null) {
			AddUserToEmailListBean inputBean = new AddUserToEmailListBean(form, this.getTcmISLocale(request));
			String uAction = inputBean.getuAction();
			try {
				if (uAction.equalsIgnoreCase("search")) {
				} else if (uAction.equalsIgnoreCase("insert")) {
					process.insert(inputBean);
				} else if (uAction.equalsIgnoreCase("delete")) {
					Collection<AddUserToEmailListBean> beanColl = BeanHandler.getBeans((DynaBean) form, "addUserToEmailListBean", new AddUserToEmailListBean(), getTcmISLocale(), "update");
					for (AddUserToEmailListBean bean : beanColl)
						process.delete(inputBean, bean);
				}
			} catch (DbUpdateException ex) {
				request.setAttribute("tcmISError",getResourceBundleValue(request, "error.db.update"));
			} catch (Exception ex) {
				request.setAttribute("tcmISError", ex.getMessage());
			}
			this.saveTcmISToken(request);
			// stick the input form back in to the request for use by the tcmis:setHiddenFields tag
			request.setAttribute("searchInput", inputBean);
			request.setAttribute("levelOfControl", inputBean.getLevelOfControl());
			request.setAttribute("addUserToEmailListBeanCollection", process.getSearchResult(inputBean));
		} else {
			request.setAttribute("userGroupName", process.getUserGroupDisplay(request.getParameter("userGroupId")));
			request.setAttribute("userGroupId", request.getParameter("userGroupId"));
			request.setAttribute("levelOfControl", request.getParameter("levelOfControl"));

			request.setAttribute("companyCollection", process.getDropDownData(request.getParameter("levelOfControl"), user.getPersonnelIdBigDecimal().toPlainString()));
		}

		return mapping.findForward("success");
	}

}
