package com.tcmis.supplier.dbuy.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.DbuyInputBean;
import com.tcmis.supplier.dbuy.process.EdiSupplierStatusProcess;

public class EdiSupplierStatusAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("success");

		//Login
		if (!isLoggedIn(request)) {
			// Save this page for returning after logging in
			request.setAttribute("requestedPage", "edisupplierstatusmain");
			//	Save any parameters passed in the URL, so that they can be accessed after the login
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			forward = mapping.findForward("login");
		}
		else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			// Need to have the permission from the database, normally it's the pageName instead of "mrRelease"
			/* Need to check if the user has permissions to view this page. */
			if (user.getPermissionBean().hasUserPagePermission("ediSupplierPOStatus")) 
			{

				EdiSupplierStatusProcess process = new EdiSupplierStatusProcess(getDbUser(request), getTcmISLocale());
				DbuyInputBean input = new DbuyInputBean();
				BeanHandler.copyAttributes(form, input, this.getTcmISLocale(request));

				// Search
				if (input.isSearch()) {
					request.setAttribute("searchResults", process.getSearchResult(input, user));
					saveTcmISToken(request);
				}
				else if (input.isCreateExcel()) {
					setExcel(response, "EdiSupplierStatusExcel");
					process.getExcelReport(process.getSearchResult(input, user), getTcmISLocale()).write(response.getOutputStream());
					forward = noForward;
				}
				else if("resendCorrectedPo".equalsIgnoreCase(input.getuAction()))
				{
					String openPoNoOrErr = process.resendCorrectedPo(input.getResendPoNo(),user);
					if(openPoNoOrErr.indexOf("Error attempting to Resend Dbuy") != -1)
						request.setAttribute("tcmISError",openPoNoOrErr);
					else
					{
						request.setAttribute("searchResults", process.getSearchResult(input, user));
						request.setAttribute("poToOpen",openPoNoOrErr);
						saveTcmISToken(request);
					}
				}
			}
			else 
				forward =  mapping.findForward("nopermissions");
		}

		return forward;

	}

}
