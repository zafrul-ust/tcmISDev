package com.tcmis.client.report.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.MaterialCategoryBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;



public class MaterialCategorySearchAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("success");

		//Login
		if (!isLoggedIn(request)) {
			// Save this page for returning after logging in
			request.setAttribute("requestedPage", "materialcategorysearch");
			//	Save any parameters passed in the URL, so that they can be accessed after the login
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			forward = mapping.findForward("login");
		}
		else {
			GenericSqlFactory factory = new GenericSqlFactory(new DbManager(this.getDbUser(request)),new MaterialCategoryBean());
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			String facilityId = request.getParameter("facilityId");
			Vector<MaterialCategoryBean> results =  (Vector<MaterialCategoryBean>)factory.selectQuery("select * from fac_material_category_view where company_id = '"+user.getCompanyId() + "'"+(facilityId.equalsIgnoreCase("All")? "" : " and facility_Id = '"+facilityId+"'") +" order by CATALOG_COMPANY_ID,CATALOG_DESC,MATERIAL_CATEGORY_NAME");
	
			boolean isMoreCat = false;
			boolean first = true;
			MaterialCategoryBean prevBean = null;
			for(MaterialCategoryBean bean:results)
			{
				if(first)
				{
					prevBean = bean;
					first = false;
				}
				else
				{
					if(!prevBean.getCatalogCompanyId().equalsIgnoreCase(bean.getCatalogCompanyId()) || !prevBean.getCatalogId().equalsIgnoreCase(bean.getCatalogId()))
						{	
							isMoreCat = true;
							break;
						}
					else
						prevBean = bean;
				}
			}
			if(isMoreCat)
				request.setAttribute("showCatDesc","Y");
			else
				request.setAttribute("showCatDesc","N");
			request.setAttribute("searchResults",results);
			
		}

		return forward;

	}

}
