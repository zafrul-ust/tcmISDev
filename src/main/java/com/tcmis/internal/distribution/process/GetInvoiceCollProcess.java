package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.InvoiceSearchBean;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;

public class GetInvoiceCollProcess extends GenericProcess implements Callable<String> {
	HttpServletRequest request = null;
	PersonnelBean personnelBean = null;
	QuickQuoteInputBean inputBean = null;

	public GetInvoiceCollProcess(String client,String locale, HttpServletRequest request, QuickQuoteInputBean inputBean, PersonnelBean personnelBean){
		super(client, locale);
		this.request = request;
		this.inputBean = inputBean;
		this.personnelBean = personnelBean;
	}

	public String call()  throws BaseException{
		request.setAttribute("invoiceColl",searchInvoice());
		return "done";
	}

	public Collection<InvoiceSearchBean> searchInvoice()  throws
	BaseException {
		factory.setBeanObject(new InvoiceSearchBean());

		StringBuilder query = new StringBuilder("WITH mainResult AS (SELECT pr.customer_id, i.invoice, ");
		query.append("i.invoice_number customer_invoice, i.invoice_amount total, i.currency_id, i.po_number, i.billing_print_date, i.pr_number ");
		query.append("FROM customer.purchase_request pr,");
		query.append(" customer.invoice i,");
		query.append(" customer.invoice_group ig ");
		query.append("WHERE ig.invoice_at_shipping = 'Y'");
		
		if(inputBean.getCustomerId() != null)
			query.append(" AND pr.customer_id = ").append(inputBean.getCustomerId());
		
		query.append(" AND ig.invoice_group = i.invoice_group");
		query.append(" AND i.company_id = pr.company_id");
		query.append(" AND i.pr_number = pr.pr_number");

		query.append(" AND EXISTS ");
		query.append("       (SELECT ix.issue_id ");
		query.append("          FROM  invoice_detail id, ");
		query.append("       		  issue ix ");
		query.append("          WHERE  id.invoice = i.invoice ");
		if(StringUtils.isNotBlank(inputBean.getDays()))
			query.append("             AND ix.ship_confirm_date >= sysdate - ").append(inputBean.getDays());
		query.append("             AND ix.ship_confirm_date < sysdate ");
		query.append("             AND id.issue_id = ix.issue_id) ");

		query.append(" AND EXISTS ");
		query.append("       (SELECT rli.inventory_group ");
		query.append("       FROM   request_line_item rli, ");
		query.append("       		(SELECT igd.inventory_group ");
		query.append("       		FROM   user_inventory_group uig, ");
		query.append("       				inventory_group_definition igd, ");
		query.append("       				user_group_member_ops_entity ug ");
		query.append("       		WHERE  uig.inventory_group = igd.inventory_group ");
		query.append("       			AND    uig.company_id = ug.company_id ");
		query.append("       			AND    uig.personnel_id = ug.personnel_id ");
		query.append("       			AND    igd.inventory_group = NVL('").append(StringHandler.emptyIfNull(inputBean.getInventoryGroup())).append("', igd.inventory_group) ");
		query.append("       			AND    igd.hub = NVL('").append(StringHandler.emptyIfNull(inputBean.getHub())).append("', igd.hub) ");
		query.append("       			AND    ug.ops_entity_id = NVL('").append(StringHandler.emptyIfNull(inputBean.getOpsEntityId())).append("', ug.ops_entity_id) ");
		query.append("       			AND    igd.ops_entity_id = ug.ops_entity_id ");
		query.append("       			AND    ug.user_group_id = 'ViewSalesOrders' ");
		query.append("       			AND    ug.company_id = '").append(personnelBean.getCompanyId()).append("'");
		query.append("       			AND    ug.personnel_id = ").append(personnelBean.getPersonnelId()).append(") tf ");
		query.append("       WHERE  rli.pr_number = pr.pr_number ");
		query.append("       	AND rli.inventory_group = tf.inventory_group) ) ");
		
		query.append(" SELECT mainResult.*, pkg_invoice_print.fx_invoice_margin(mainResult.invoice) margin, ");
		query.append("(SELECT MIN(ix.ship_confirm_date) FROM invoice_detail id, issue ix WHERE id.issue_id = ix.issue_id AND id.invoice = mainResult.invoice) date_confirmed, ");
		query.append("(SELECT SUM(catalog_price * quantity) FROM invoice_detail id WHERE id.invoice = mainResult.invoice AND TO_NUMBER(line_item) >= 1) total_goods  FROM mainResult ");

		return factory.selectQuery(query.toString());
	}

}
