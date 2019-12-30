package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;
import com.tcmis.internal.distribution.beans.QuotationHistoryViewBean;

public class GetQuoteHistoryCollProcess extends BaseProcess implements Callable<String> {
	HttpServletRequest request = null;
	PersonnelBean personnelBean = null;
	QuickQuoteInputBean inputBean = null;
	
	public GetQuoteHistoryCollProcess(String client,String locale, HttpServletRequest request, QuickQuoteInputBean inputBean, PersonnelBean personnelBean){
		super(client,locale);
		this.request = request;
		this.inputBean = inputBean;
		this.personnelBean = personnelBean;
	}
	
	public String call() {
		Collection results = null;
		try {
			results = searchQuoteHistory();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		request.setAttribute("quoteHistoryColl",results);
		return "done";
	}
	
	public Collection searchQuoteHistory() throws Exception {
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(new DbManager(getClient(),getLocale()));
		
		StringBuilder query = new StringBuilder("select  sq.ops_entity_id,igd.inventory_group_name, sqln.pr_number, sqln.line_item, part_description, sq.submitted_date, sqln.quantity, ");
		query.append("sqln.cat_part_no, sqln.currency_id, sqln.catalog_price unit_price,"); 
		query.append("coalesce(sqln.customer_part_no, pkg_sales_search.fx_customer_part(sqln.company_id, sqln.cat_part_no)) customer_part_no, ");
		query.append("pkg_sales_search.fx_orig_sales_quote_count(sq.pr_number) orig_sales_quote_count, ");
		query.append("pkg_intercompany_mr.FX_CAT_PART_SPEC_LIST(sqln.cat_part_no, 'SPEC_ID', ', ', sqln.catalog_company_id, sqln.catalog_id) spec_list ");
		
		query.append(" from vv_ops_region r, inventory_group_definition igd, customer.bill_to_customer btc, catalog_item ci, catalog_item_spec cis, sales_quote sq,sales_quote_line sqln ");
		query.append("where sq.status = 'Confirmed' and r.ops_company_id (+) = igd.ops_company_id and r.ops_region (+) = igd.ops_region and igd.inventory_group = sqln.inventory_group and ");
		query.append("exists (select personnel_id from customer.user_group_member_ops_entity where user_group_id = 'ViewQuotes' and personnel_id = ").append(personnelBean.getPersonnelId()).append(" and ops_entity_id = sq.ops_entity_id) and ");
		
		query.append("sq.status <> 'ScratchPad' and sq.customer_id = btc.customer_id (+) and ci.catalog_company_id = cis.catalog_company_id and ci.catalog_id = cis.catalog_id and ");
		query.append("ci.item_id = cis.item_id and cis.catalog_company_id = sqln.catalog_company_id and cis.catalog_id = sqln.catalog_id and cis.cat_part_no = sqln.cat_part_no and sq.pr_number = sqln.pr_number and ");
		
		query.append("sq.customer_id = ").append(inputBean.getCustomerId());
		query.append(" and sq.inventory_group in (select inventory_group from table (pkg_inventory_group.fx_personnel_inventory_group('").append(personnelBean.getCompanyId()).append("', ").append(personnelBean.getPersonnelId()).append(", null,null))) and ");
		query.append("sqln.catalog_company_id = 'HAAS' and sqln.catalog_id = 'Global' "); 
		query.append("and sq.submitted_date < sysdate and sq.submitted_date >= sysdate - ").append(inputBean.getDays());  
		query.append(" order by sq.submitted_date desc");
		
		Collection results = null;
		try {
			results = genericSqlFactory.setBean(new QuotationHistoryViewBean()).selectQuery(query.toString());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return results;
	}
}
