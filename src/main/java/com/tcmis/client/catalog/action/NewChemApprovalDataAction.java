package com.tcmis.client.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatAddStatusViewBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;


/******************************************************************************
 * Controller for New Chem Approval Data Action
 * @version 1.0
 ******************************************************************************/

public class NewChemApprovalDataAction extends TcmISBaseAction{

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception 
			{
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "shownewchemappdetail");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}


		String requestId = request.getParameter("requestId");
		GenericProcess process = new GenericProcess(this.getDbUser(request),this.getTcmISLocale(request));
		String headerQuery = 
			"select fx_personnel_id_to_name(carn.requestor) requestor_name,"+
			"ars.request_status_desc,carn.request_date,f.facility_name,cai.material_desc,cai.manufacturer,"+
			"cai.components_per_item||' x '||cai.part_size||' '||cai.size_unit||' in '||cai.pkg_style packaging "+ 
			"from catalog_add_request_new carn,catalog_add_item cai,facility f,vv_catalog_add_request_status ars "+ 
			"where carn.request_id = cai.request_id and  carn.request_status = ars.request_status "+
			"and carn.eng_eval_facility_id = f.facility_id and carn.request_id = "+ requestId;
		String detailQuery =
			"select * from cat_add_status_view where request_id = "+ requestId + 
			" and application in (select 'All' from dual union select work_area from catalog_add_user_group "+ 	
			" where request_id = " + requestId +") order by approval_group,approval_group_seq,approval_role,chemical_approvers";				 
		
		request.setAttribute("headerColl", process.getSearchResultObjects(headerQuery ) );
		request.setAttribute("beanCollection",process.getSearchResult(detailQuery, new CatAddStatusViewBean()));

		return (mapping.findForward("success"));
	}
}
