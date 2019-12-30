package com.tcmis.client.report.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.report.beans.VvMaterialCategoryBean;
import com.tcmis.client.report.beans.VvMaterialSubcategoryBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;

/******************************************************************************
 * Controller for Material Category and subcategory search
 * @version 1.0
     ******************************************************************************/
public class MaterialCategoryTemplateAction extends TcmISBaseAction {

public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws BaseException, Exception {

        request.setAttribute("startSearchTime", new Date().getTime());
        String which = request.getParameter("which");
        String catalogCompanyId = request.getParameter("catalogCompanyId");
        String catalogId = request.getParameter("catalogId");
        String materialCategoryId = request.getParameter("materialCategoryId");
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");

        GenericSqlFactory genericSqlFactory = new GenericSqlFactory(new DbManager(this.getDbUser(request)), new VvMaterialCategoryBean());
        SearchCriteria criteria = new SearchCriteria();
        SortCriteria sortCriteria = new SortCriteria();

        StringBuilder subcategoryWhere = new StringBuilder("");
        //catalog_company_id
        if(!StringHandler.isBlankString(catalogCompanyId)) {
            criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, catalogCompanyId);
            if (!StringHandler.isBlankString(subcategoryWhere.toString()))
                subcategoryWhere.append(" and");
            subcategoryWhere.append(" a.catalog_company_id = ").append(SqlHandler.delimitString(catalogCompanyId));
        }
        //catalog_id
        
        if(!personnelBean.isFeatureReleased("MatlCatIdNotRequired","ALL",personnelBean.getCompanyId()) && !StringHandler.isBlankString(catalogId)) {
            criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
            if (!StringHandler.isBlankString(subcategoryWhere.toString()))
                subcategoryWhere.append(" and");
            subcategoryWhere.append(" a.catalog_id = ").append(SqlHandler.delimitString(catalogId));
        }

        if("catalogId".equalsIgnoreCase(which)) {
            sortCriteria.addCriterion("materialCategoryName");
            request.setAttribute("materialCategoryCollection", genericSqlFactory.select(criteria, sortCriteria, "vv_material_category"));
        }
        //material_category_id
        if(!StringHandler.isBlankString(materialCategoryId)){
            materialCategoryId = materialCategoryId.replaceAll("\\|", ",");
            if (!StringHandler.isBlankString(subcategoryWhere.toString()))
                subcategoryWhere.append(" and");
            subcategoryWhere.append(" a.material_category_id in (").append(materialCategoryId).append(")");
        }
        genericSqlFactory.setBean(new VvMaterialSubcategoryBean());
        StringBuilder query = new StringBuilder("");
        if(!StringHandler.isBlankString(materialCategoryId)){
            if (materialCategoryId.indexOf(",") > 0) {
                query.append("select a.material_subcategory_id,a.material_subcategory_name||' ['||b.material_category_name||']' material_subcategory_name");
            }else {
                query.append("select a.*");
            }
        }else {
            query.append("select a.material_subcategory_id,a.material_subcategory_name||' ['||b.material_category_name||']' material_subcategory_name");
        }
        query.append(" from vv_material_subcategory a, vv_material_category b");
        query.append(" where a.company_id = b.company_id and a.catalog_company_id = b.catalog_company_id and a.catalog_id = b.catalog_id");
        query.append(" and a.material_category_id = b.material_category_id");
        if (!StringHandler.isBlankString(subcategoryWhere.toString()))
            query.append(" and ").append(subcategoryWhere);
        query.append(" order by a.material_subcategory_name,b.material_category_name");

        request.setAttribute("materialSubcategoryCollection", genericSqlFactory.selectQuery(query.toString()));

        //fac_loc_app
        request.setAttribute("endSearchTime", new Date().getTime());
        return mapping.findForward("materialcategorycallback");
    }
}