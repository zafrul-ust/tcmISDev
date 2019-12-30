package com.tcmis.client.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.client.catalog.beans.CatAddStatusViewBean;
import com.tcmis.client.common.process.MaterialRequestDataProcess;
import com.tcmis.client.common.beans.LineItemViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

import java.util.Vector;

/******************************************************************************
 * Controller for show mr approval detail Search page
 * @version 1.0
 ******************************************************************************/

public class ShowMrApprovalDetailAction extends TcmISBaseAction{

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception 
			{
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "showmrapprovaldetail");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}


		MaterialRequestDataProcess mrDataProcess = new MaterialRequestDataProcess(this.getDbUser(request), getTcmISLocaleString(request));
		//copy date from dyna form to the data bean
		LineItemViewBean inputBean = new LineItemViewBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if(personnelBean.getCompanyId().equals("Radian")) inputBean.setCompanyId("Radian");
		
		Object[] results = mrDataProcess.getMrApprovalDetailData(inputBean);
		request.setAttribute("financeApproverDataCollection",results[mrDataProcess.FINANCE_DATA]);
		request.setAttribute("releaserDataCollection",results[mrDataProcess.RELEASER_DATA]);
		request.setAttribute("useApproverDataCollection",results[mrDataProcess.USE_DATA]);
		request.setAttribute("listApproverCollection",results[mrDataProcess.LIST_DATA]);
        request.setAttribute("approverCollection",results[mrDataProcess.APPROVAL_DATA]);

        return (mapping.findForward("success"));
	}
}
