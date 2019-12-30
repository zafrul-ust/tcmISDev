/**
 * @author spiros.petratos
 * 
 * Action class used to display user's allocation.
 */
package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.web.IHaasConstants;
import com.tcmis.internal.supply.beans.AllocationsInputBean;
import com.tcmis.internal.supply.beans.MrAllocationViewBean;
import com.tcmis.internal.supply.process.AllocationsProcess;

/**
 * @author spiros.petratos
 * 
 *         Action invoked to search allocations.
 */
public class AllocationsAction extends TcmISBaseAction
{

	private static final String	ALLOCATIONS_LIST					= "Allocations_List";
	private static final String	ALLOCATIONS_VIEW_BEAN_COLLECTION	= "mrAllocationViewBeanCollection";
	private static final String	U_ACTION							= "uAction";
	private static final String ALLOCATIONS 						= "mrRelease";
	private static final String	ALLOCATIONSMAIN						= "showallocations";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcmis.common.framework.TcmISBaseAction#executeAction(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{

		if (!this.isLoggedIn(request))
		{
			request.setAttribute(IHaasConstants.CST_REQUESTED_PAGE,
					ALLOCATIONSMAIN);
			request.setAttribute(
					IHaasConstants.CST_REQUESTED_URL_WITH_PARAMETERS, this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward(IHaasConstants.CST_FORWARD_TO_LOGIN));
		}

		// If you need access to who is logged in
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(
				request, IHaasConstants.CST_PERSONNEL_BEAN);
		new BigDecimal(personnelBean.getPersonnelId());

		if (!personnelBean.getPermissionBean().hasUserPagePermission(
				ALLOCATIONS))
		{
			return (mapping
					.findForward(IHaasConstants.CST_FORWARD_TO_NOPERMISSIONS));
		}

		/*
		 * Since there is no search section we consider this the start time.
		 * This should be done only for pages that have no search section.
		 */
		request.setAttribute(IHaasConstants.CST_START_SEARCH_TIME, new Date()
		.getTime());

		AllocationsInputBean inputBean = new AllocationsInputBean();
		AllocationsProcess allocationsProcess = new AllocationsProcess(this
				.getDbUser(request), getTcmISLocaleString(request));
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		//String uAction = (String) ((DynaBean) form).get(U_ACTION);

		if (inputBean.isSearch())
		{
			Collection<MrAllocationViewBean> allocationsViewBeanCollection = allocationsProcess
			.getAllocationsViewBeanCollection(inputBean);
			request.setAttribute(ALLOCATIONS_VIEW_BEAN_COLLECTION,
					allocationsViewBeanCollection);
			this.saveTcmISToken(request);
		}
		else
		{
			if (inputBean.isCreateExcel())
			{
				this.setExcel(response, ALLOCATIONS_LIST);
				allocationsProcess.getExcelReport(
						inputBean,
						(java.util.Locale) request.getSession().getAttribute(
								IHaasConstants.CST_TCMIS_LOCALE)).write(
										response.getOutputStream());
				return noForward;
			}
		}

		request.setAttribute("allocationsInputBean", inputBean);

		/*
		 * Since there is no search section we have to set end Time here as we
		 * cannot use the client side time. Client can be anywhere in the
		 * world.This should be done only for pages that have no search section.
		 */
		request.setAttribute(IHaasConstants.CST_END_SEARCH_TIME, new Date().getTime());
		return (mapping.findForward(IHaasConstants.CST_FORWARD_TO_SUCCESS));
	}
}
