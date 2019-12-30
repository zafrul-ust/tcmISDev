package com.tcmis.internal.distribution.action;

//import java.math.BigDecimal;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.SalesSearchBean;
import com.tcmis.internal.distribution.process.AddChargeProcess;


public class ShowAllOrdersAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception
	{
		String source = "showallpreviousorders";
		if (!this.checkLoggedIn( source ) ) return mapping.findForward("login");
		request.setAttribute("source", source );
		/*Since there is no search section we consider this the start time. This should be done only for
		 * pages that have no search section.*/
		request.setAttribute("startSearchTime", new Date().getTime() );
		String forward = "success";

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		SalesSearchBean in = new SalesSearchBean();
		BeanHandler.copyAttributes(form, in);
		//    if( in.getPrNumber() == null ) return mapping.findForward("nopermission");

		AddChargeProcess process = new AddChargeProcess(this);

		String action = request.getParameter("uAction");
		if (action != null && "update".equals(action)) {
			this.checkToken(request);
			String ErrorMsg = "";
			//        Collection<MrAddChargeViewBean> beans = BeanHandler.getBeans((DynaBean)form,"MrAddChargeView",new MrAddChargeViewBean(),this.getTcmISLocale());
			//        for(MrAddChargeViewBean bean:beans) {
			//      	   if( "New".equals(bean.getChanged()) ) ErrorMsg += process.addLineCharge(bean);
			//     	   if( "Y".equals(bean.getChanged()) ) ErrorMsg += process.updateLine(bean);
			//     	   if( "Remove".equals(bean.getChanged()) ) ErrorMsg += process.removeLine(bean);
			//     	}
			request.setAttribute("tcmISError", ErrorMsg);
		}

		//		in.setEndDate(new Date());
		if ( "No Specification".equals(in.getSpecList()))
		{
			in.setSpecList("");
		}

		request.setAttribute("beanColl",process.getSearchResult("select * from table (pkg_sales_search.FX_PREVIOUS_ORDER ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}))", in,
				in.getItemId(),
				personnelBean.getCompanyId(),
				new BigDecimal(personnelBean.getPersonnelId()),
				in.getSpecList(),
				in.getInventoryGroup(),
				in.getCompanyId(),
				in.getCustomerId(),
				in.getRegion(),
				"",
				"",
				"",
				in.getSearchKey()
		));

		this.saveTcmISToken(request);

		/* Since there is no search section we have to set end Time here as we cannot use the client side time.
		 * Client can be anywhere in the world.This should be done only for pages that have no search section.
		 **/
		request.setAttribute("endSearchTime", new Date().getTime() );
		return (mapping.findForward(forward));
	}
}