package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.concurrent.*;
import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;
import com.tcmis.internal.distribution.beans.SalesSearchBean;

public class GetMRHistoryCollectionProcess extends BaseProcess implements Callable<String> {
	HttpServletRequest request = null;
	PersonnelBean personnelBean = null;
	QuickQuoteInputBean inputBean = null;
	
	public GetMRHistoryCollectionProcess(String client,String locale, HttpServletRequest request, QuickQuoteInputBean inputBean, PersonnelBean personnelBean){
		super(client,locale);
		this.request = request;
		this.inputBean = inputBean;
		this.personnelBean = personnelBean;
	}
	
	public String call() {
		Collection results = null;
		try {
			results = searchMRHistory();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		request.setAttribute("mrHistoryColl",results);
		return "done";
	}
	
	public Collection searchMRHistory() throws Exception {
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(new DbManager(getClient(),getLocale()));
		
		StringBuilder query = new StringBuilder("select rli.company_id, pr.ops_entity_id, ci.item_id, igd.inventory_group_name, rli.pr_number, rli.line_item, ci.item_id, cat_part_no, part_description, rli.quantity, rli.release_date,");
		query.append(" rli.po_number customer_po_number, rli.currency_id, rli.catalog_price unit_price, "); 
		query.append("(SELECT NVL (quantity_delivered, 0) FROM  issue_key i1 WHERE rli.pr_number = i1.pr_number(+) AND rli.line_item = i1.line_item(+) AND rli.company_id = i1.company_id(+)) quantity_shipped, ");
		query.append("rli.sales_order, rli.date_last_delivered last_ship_date, ");
		
		query.append("coalesce(rli.customer_part_no, pkg_sales_search.fx_customer_part(rli.company_id, rli.fac_part_no)) customer_part_no, ");
        query.append(" case when rli.catalog_price is not null and rli.catalog_price > 0 then");
        query.append(" case when rli.expected_unit_cost is not null and rli.catalog_price is not null then");
        query.append("  (catalog_price - rli.expected_unit_cost)/catalog_price*100");
        query.append(" when rli.expected_unit_cost is null and rli.catalog_price is not null then");
        query.append("  (catalog_price - coalesce(pkg_sales_search.fx_expected_cost (rli.inventory_group,rli.item_id,rli.currency_id), catalog_price))/catalog_price*100");
        query.append(" else 0 end  "); 
        query.append(" else 0 ");
        query.append(" end margin, ");
        query.append(" pkg_intercompany_mr.FX_CAT_PART_SPEC_LIST (rli.fac_part_no,'SPEC_ID', ', ', rli.catalog_company_id,rli.catalog_id) spec_list ");    

		query.append(" from vv_ops_region r, inventory_group_definition igd, customer.bill_to_customer btc, catalog_item ci, catalog_item_spec cis, purchase_request pr, request_line_item rli ");
		query.append("where pr.pr_status in ('posubmit', 'compchk2') and r.ops_company_id (+) = igd.ops_company_id and r.ops_region (+) = igd.ops_region and igd.inventory_group = rli.inventory_group and ");
		query.append("exists (select personnel_id from customer.user_group_member_ops_entity where user_group_id = 'ViewSalesOrders' and personnel_id = ").append(personnelBean.getPersonnelId()).append(" and ops_entity_id =  pr.ops_entity_id) and ");
		query.append("nvl(rli.cancel_status, 'OK') <> 'canceled' and pr.customer_id = btc.customer_id (+) and ci.catalog_company_id = cis.catalog_company_id and ci.catalog_id = cis.catalog_id and ci.item_id = cis.item_id and ");
		query.append("cis.catalog_company_id = rli.catalog_company_id and cis.catalog_id = rli.catalog_id and cis.cat_part_no = rli.fac_part_no and pr.company_id = rli.company_id and pr.pr_number = rli.pr_number and ");
		query.append("pr.customer_id = ").append(inputBean.getCustomerId());
		query.append(" and rli.inventory_group in (select inventory_group from table (pkg_inventory_group.fx_personnel_inventory_group('").append(personnelBean.getCompanyId()).append("', ").append(personnelBean.getPersonnelId()).append(", null,null))) and ");
		query.append("rli.catalog_company_id = 'HAAS' and rli.catalog_id = 'Global' "); 
		query.append(" and rli.release_date < sysdate and rli.release_date >= sysdate - ").append(inputBean.getDays());  
		query.append(" and to_number(rli.line_item) >= 1 ");
		query.append(" order by rli.release_date desc");
		
		Collection results = null;
		try {
			results = genericSqlFactory.setBean(new SalesSearchBean()).selectQuery(query.toString());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return results;
	}
}
