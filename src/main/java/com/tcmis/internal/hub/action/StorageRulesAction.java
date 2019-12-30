package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.StorageRule;
import com.tcmis.internal.hub.factory.StorageRulesDataMapper;
import com.tcmis.internal.hub.process.StorageRulesProcess;

public class StorageRulesAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "success";
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "storageRules");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			forward = "login";
		}
		else {
			try {
				StorageRule input = new StorageRule();
				BeanHandler.copyAttributes(form, input, getTcmISLocale(request));
				StorageRulesProcess process = new StorageRulesProcess(new StorageRulesDataMapper(getDbUser(), getTcmISLocaleString(request)));
				if (input.isUserActionSearch()) {
					doSearch(request, process, input);
				}
				else if (input.isUserActionUpdate()) {
					@SuppressWarnings("unchecked")
					Collection<StorageRule> inputCollection = BeanHandler.getBeans((DynaBean) form, "storageRulesData", new StorageRule(), getTcmISLocale(request));
					process.update(inputCollection);
					doSearch(request, process, input);
				}
				else if (input.isCreateXLS()) {
	                this.setExcel(response, "Item Storage Search");
	                process.createExcelReport(input, this.getLocale(request)).write(response.getOutputStream());
	                return noForward;
	            }
			} catch(Exception e) {
				request.setAttribute("tcmISError", e);
			}
		}
		
		return mapping.findForward(forward);
	}
	
	private void doSearch(HttpServletRequest request, StorageRulesProcess process, StorageRule input) throws BaseException {
		request.setAttribute("storageRules", process.search(input));
		process.assignStorageRuleValidValues(input.getBranchPlant()).forEach(request::setAttribute);
	}

}
