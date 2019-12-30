package com.tcmis.client.catalog.process;

import java.sql.Connection;
import java.util.Collection;

import com.tcmis.client.catalog.beans.EditApprovalCodeInputBean;
import com.tcmis.client.catalog.beans.EditApprovalCodeViewBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

public class EditApprovalCodeProcess extends GenericProcess {

	public EditApprovalCodeProcess(String client, String locale) {
		super(client, locale);
	}
	
	public Collection<EditApprovalCodeViewBean> viewApprovalCodes(EditApprovalCodeInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder();
		if ( ! StringHandler.isBlankString(input.getCatPartNo())) {
			query.append("select fpuc.approved_id,fpuc.start_date,fpuc.end_date,vus.application_use_group_id,vus.usage_subcategory_name");
			query.append(" from fac_part_use_code fpuc, vv_usage_subcategory vus");
			query.append(" where fpuc.company_id = vus.company_id and fpuc.facility_id = vus.facility_id");
			query.append(" and fpuc.usage_subcategory_id = vus.usage_subcategory_id");
			query.append(" and fpuc.company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
			query.append(" and fpuc.facility_id = ").append(SqlHandler.delimitString(input.getFacilityId()));
			query.append(" and fpuc.catalog_company_id = ").append(SqlHandler.delimitString(input.getCatalogCompanyId()));
			query.append(" and fpuc.catalog_id = ").append(SqlHandler.delimitString(input.getCatalogId())); 
			query.append(" and fpuc.cat_part_no = ").append(SqlHandler.delimitString(input.getCatPartNo()));
			query.append(" and fpuc.part_group_no = ").append(input.getPartGroupNo());
			query.append(" order by vus.application_use_group_id");
		}
		else if ( ! StringHandler.isBlankString(input.getCustomerMsdsOrMixtureNo())) {
			query.append("select cmmu.msds_or_mixture_use_id,cmmu.start_date,cmmu.end_date,vus.application_use_group_id,vus.usage_subcategory_name");
			query.append(" from customer_msds_or_mixture_use cmmu, vv_usage_subcategory vus");
			query.append(" where cmmu.company_id = vus.company_id and cmmu.usage_subcategory_id = vus.usage_subcategory_id");
			query.append(" and cmmu.company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
			query.append(" and cmmu.customer_msds_db = ").append(SqlHandler.delimitString(input.getCustomerMsdsDb()));
			query.append(" and cmmu.customer_msds_or_mixture_no = ").append(SqlHandler.delimitString(input.getCustomerMsdsOrMixtureNo()));
			query.append(" order by vus.application_use_group_id"); 
		}
		Collection<EditApprovalCodeViewBean> approvalColl = null;
		if (query.length() > 0) {
			approvalColl = factory.setBean(new EditApprovalCodeViewBean()).selectQuery(query.toString());
		}
		return approvalColl;
	}
	
	public void submitChanges(EditApprovalCodeInputBean input, Collection<EditApprovalCodeViewBean> beanColl) throws BaseException {
		Connection conn = dbManager.getConnection();
		int updateCount = 0;
		for (EditApprovalCodeViewBean bean : beanColl) {
			StringBuilder updateEndDate = new StringBuilder();
			StringBuilder updateExpDate = new StringBuilder();
			if (bean.getApprovedId() != null) {
				updateEndDate.append("update fac_part_use_code fpuc ");
				updateEndDate.append(" set end_date = ").append(DateHandler.getOracleToDateFunction(bean.getEndDate()));
				updateEndDate.append(" where approved_id = ").append(bean.getApprovedId());
				
				updateExpDate.append("update use_approval set expire_date = ").append(DateHandler.getOracleToDateFunction(bean.getEndDate()));
				updateExpDate.append(" where company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
				updateExpDate.append(" and facility_id = ").append(SqlHandler.delimitString(input.getFacilityId()));
				updateExpDate.append(" and catalog_id = ").append(SqlHandler.delimitString(input.getCatalogId()));
				updateExpDate.append(" and catalog_company_id = ").append(SqlHandler.delimitString(input.getCatalogCompanyId()));
				updateExpDate.append(" and fac_part_no = ").append(SqlHandler.delimitString(input.getCatPartNo()));
				updateExpDate.append(" and part_group_no = ").append(input.getPartGroupNo());
				updateExpDate.append(" and application_use_group_id in('All',");
				updateExpDate.append(SqlHandler.delimitString(bean.getApprovalCode())).append(")");
				factory.deleteInsertUpdate(updateExpDate.toString(), conn);
			}
			else if (bean.getMsdsOrMixtureUseId() != null) {
				updateEndDate.append("update customer_msds_or_mixture_use cmmu ");
				updateEndDate.append(" set end_date = ").append(DateHandler.getOracleToDateFunction(bean.getEndDate()));
				updateEndDate.append(" where msds_or_mixture_use_id = ").append(bean.getMsdsOrMixtureUseId());
			}
			if (updateEndDate.length() > 0) {
				updateCount += factory.deleteInsertUpdate(updateEndDate.toString(), conn);
			}
		}
	}
}
