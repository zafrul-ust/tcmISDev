package com.tcmis.supplier.wbuy.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.supplier.wbuy.beans.ProblemRejectionWbuyViewBean;
import com.tcmis.supplier.wbuy.process.OrderListProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/

/**
 * Change History
 * --------------
 * 03/23/09 - Shahzad Butt - Added search functionality for a given radianPo.
 *
 */
public class POProblemDetailAction  extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "poproblemdetail");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",	this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		request.setAttribute("startSearchTime", new Date().getTime() );
		String forward = "success";
		//If you need access to who is logged in
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");	
		/*Need to check if the user has permissions to view this page. if they do not have the permission
	we need to not show them the page.*/
		if (!personnelBean.getPermissionBean().hasUserPagePermission("wBuy"))
		{
			return (mapping.findForward("nopermissions"));
		}    

		ProblemRejectionWbuyViewBean inputBean = new ProblemRejectionWbuyViewBean();

		BeanHandler.copyAttributes(form, inputBean);

		OrderListProcess orderingProcessObj  = new OrderListProcess(this.getDbUser(request));

		Collection <ProblemRejectionWbuyViewBean> c = orderingProcessObj.getProblemsRejects(inputBean);

		request.setAttribute("probRejectBeans",c);

		request.setAttribute("endSearchTime", new Date().getTime() );

		return (mapping.findForward(forward));
	}
}
