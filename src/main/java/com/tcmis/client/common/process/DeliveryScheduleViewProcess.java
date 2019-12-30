package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.client.common.beans.DeliveryScheduleChangeDataBean;
import com.tcmis.client.common.beans.DeliveryScheduleFacilityBean;
import com.tcmis.client.common.beans.DeliveryScheduleInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;

public class DeliveryScheduleViewProcess extends BaseProcess {

	public DeliveryScheduleViewProcess(String client) {
		super(client);
	}  
  
	public DeliveryScheduleViewProcess(String client,String locale) {
	    super(client,locale);
    }
	
	/**
	 * 
	 * @param reviewer - must be one of "csr" or "expedite"
	 * @param orderBy - must be one of "item" or "pr"
	 * @return
	 */
	public Collection<DeliveryScheduleChangeDataBean> showAllScheduledDeliveries(
			DeliveryScheduleInputBean input,BigDecimal personnelId) throws BaseException{
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleChangeDataBean());
	    
		StringBuilder query = new StringBuilder();

		String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
		
		if (input.getCompany() == null || input.getFacility() == null || input.getReviewer() == null) {
			throw new BaseException();
		}
             
		query.append("select pr.company_id, pr.pr_number, pr.facility_id, f.facility_name, fx_personnel_id_to_name(pr.requestor) requestor_name, ");
		query.append("rli.line_item, rli.quantity, rli.fac_part_no, fx_cat_part_desc(rli.catalog_company_id, rli.catalog_id, rli.fac_part_no) part_desc ");
		query.append("from purchase_request pr, request_line_item rli, ").append(prefix).append("facility f ");
		query.append("where pr.company_id = rli.company_id and pr.pr_number = rli.pr_number and ");
		query.append("pr.company_id = f.company_id and pr.facility_id = f.facility_id and ");
		query.append("exists(select dsc.pr_number from delivery_schedule_change dsc ");
		query.append("where dsc.company_id = rli.company_id and dsc.pr_number = rli.pr_number ");
		query.append("and dsc.line_item = rli.line_item ");
		if ("Buyer".equals(input.getReviewer()))
            input.setReviewer("expedite");
		// search by default should exclude approved MRs since they do not require any action
		if ("N".equals(input.getApprovedMrsOnly())) {
			query.append("and dsc.date_to_deliver > sysdate-120 ");
	        query.append("and (dsc.").append(input.getReviewer().toLowerCase()).append("_approval is null)) ");
		}
		// the user can search for approved MRs in which case there should be no date restriction and the approval should be non-null
		else {
			query.append("and (dsc.").append(input.getReviewer().toLowerCase()).append("_approval is not null)) ");
		}
		if ( ! StringHandler.isBlankString(input.getCompany())) {
			query.append("and pr.company_id = '").append(input.getCompany()).append("'");
			if ( ! StringHandler.isBlankString(input.getFacility())) {
				query.append("and pr.facility_id = '").append(input.getFacility()).append("'");
			}
			else {
				query.append("and pr.facility_id in ");
				query.append("(select facility_id from ").append(prefix).append("user_facility where company_id='").append(input.getCompany()).append("' and personnel_id=").append(personnelId).append(")");
			}
		}
		else {
			query.append(" and (pr.company_id, pr.facility_id) in ");
			query.append("(select company_id,facility_id from ").append(prefix).append("user_facility where personnel_id = ").append(personnelId).append(")");
		}
		
		if ( ! StringHandler.isBlankString(input.getSearchTerms())) {
			query.append(" and (lower(fx_cat_part_desc(rli.catalog_company_id, rli.catalog_id, rli.fac_part_no) || ' ' || pr.pr_number) like lower('%").append(SqlHandler.validString(input.getSearchTerms())).append("%'))");
		}
		
		query.append(" order by pr.company_id,f.facility_name, pr.pr_number, rli.line_item");
		
		Collection<DeliveryScheduleChangeDataBean> deliveryColl = factory.selectQuery(query.toString());
		
		return deliveryColl;
	}
	
	public Collection<DeliveryScheduleFacilityBean> getCompanyFacility(BigDecimal personnelId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DeliveryScheduleFacilityBean());
	    
		StringBuilder query = new StringBuilder();
		
		String prefix = "";
		if ("TCM_OPS".equals(this.getClient())) {
			prefix = "customer.";
		}
		
		query.append("select ugm.company_id, c.company_name, f.facility_id, f.facility_name, ugm.user_group_id ");
		query.append("from ").append(prefix).append("user_group_member ugm, ").append(prefix).append("facility f, company c ");
		query.append("where ugm.personnel_id = ").append(personnelId).append(" and ugm.user_group_id IN ('CSR','Buyer') ");
        query.append("and f.active = 'y' and (ugm.facility_id = 'All' or ugm.facility_id = f.facility_id) ");
        query.append("and ugm.company_id = f.company_id and c.company_id = f.company_id order by user_group_id, company_name, facility_name");
		
		Collection<DeliveryScheduleFacilityBean> facilityBeanColl = factory.selectQuery(query.toString());
		
		return facilityBeanColl;
	}
}
