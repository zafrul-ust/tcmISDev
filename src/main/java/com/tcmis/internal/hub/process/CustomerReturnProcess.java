package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.DropDownNamesInputBean;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CustomerReturnInputBean;
import com.tcmis.internal.hub.beans.IssueBean;
import com.tcmis.internal.hub.beans.MrIssueReceiptDetailViewBean;

public class CustomerReturnProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	private GenericSqlFactory factory;

	public CustomerReturnProcess(String client) {
		super(client);
		
		factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
	}

	public CustomerReturnProcess(String client, String locale) {
		super(client, locale);
		
		factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
	}
	
	public Collection<MrIssueReceiptDetailViewBean> getCmsReceiptsToCreateReturnRequest(CustomerReturnInputBean inputBean)
			throws BaseException {
		return getCmsReceiptsToCreateReturnRequest(getCmsIssuesWithReturnQuantity(inputBean));
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MrIssueReceiptDetailViewBean> getCmsIssuesWithReturnQuantity(CustomerReturnInputBean inputBean)
			throws BaseException {
		StringBuilder query = new StringBuilder("SELECT v.*");
		query.append(", fx_inventory_group_name(v.inventory_group) inventory_group_name");
		query.append(", crr.quantity issue_return_approved_quantity");
		query.append(", crr.rma_line_status return_status");
		
		//Get CMS Customer Owned request's returned quantity by getting quantity of receipts
		//created through P_CUSTOMER_OWNED_RECEIPT (the main procedure to receive Customer Return request)
		query.append(", (SELECT NVL(SUM(r.quantity_received), 0)");
		query.append(" FROM RECEIPT r");
		query.append(" WHERE r.return_pr_number = v.pr_number");
		query.append(" AND r.return_line_item = v.line_item");
		query.append(" AND r.return_receipt_id = v.receipt_id) co_total_returned_quantity");
		
		//Get CMS Customer Owned request's returned quantity. This is only applicable to those
		//created from the new process of creating and receiving CMS Customer Return requests.
		query.append(", (SELECT NVL(sum(b.qty_received), 0)");
		query.append(" FROM CUSTOMER_RETURN_REQUEST a, CUSTOMER_RETURN_REQUEST_DETAIL b");
		query.append(" WHERE a.customer_rma_id = b.customer_rma_id");
		query.append(" AND a.return_type = 'CO'");
		query.append(" AND b.rma_line_status IN ('Partial Return', 'Completed')");
		query.append(" AND a.company_id = v.company_id");
		query.append(" AND a.pr_number = v.pr_number");
		query.append(" AND a.line_item = v.line_item");
		query.append(" AND b.receipt_id = v.receipt_id");
		query.append(" AND b.issue_id = v.issue_id) co_new_returned_quantity");
		
		query.append(" FROM MR_ISSUE_RECEIPT_DETAIL_VIEW v");
		query.append(", (SELECT crr.company_id, crr.pr_number, crr.line_item, crr.customer_rma_id");
		query.append(", crrd.receipt_id, crrd.issue_id, crrd.quantity, crrd.rma_line_status");
		query.append(" FROM CUSTOMER_RETURN_REQUEST crr, CUSTOMER_RETURN_REQUEST_DETAIL crrd");
		query.append(" WHERE crr.customer_rma_id = crrd.customer_rma_id(+)");
		query.append(" AND crr.customer_rma_id = ").append(inputBean.getCustomerRmaId()).append(") crr");
		
		query.append(getCmsToCreateReturnRequestWhereClause(inputBean));
		
		query.append(" ORDER BY v.company_name, v.pr_number, v.line_item, v.receipt_id, v.issue_id");
		
		Collection<MrIssueReceiptDetailViewBean> result = factory.setBean(new MrIssueReceiptDetailViewBean())
																	.selectQuery(query.toString());

		if (CollectionUtils.isNotEmpty(result)) {
			findIssuesReturnedQuantity(result);
		}
		
		return result;
	}
	
	private StringBuilder getCmsToCreateReturnRequestWhereClause(CustomerReturnInputBean inputBean) {
		StringBuilder whereClause = new StringBuilder();
		
		//only get CMS data
		whereClause.append(" WHERE EXISTS (SELECT *");
		whereClause.append(" FROM PURCHASE_REQUEST");
		whereClause.append(" WHERE company_id = v.company_id");
		whereClause.append(" AND pr_number = v.pr_number");
		whereClause.append(" AND account_sys_id NOT IN ('DIST', 'Haas'))");
		
		whereClause.append(" AND v.company_id = crr.company_id(+)");
		whereClause.append(" AND v.pr_number = crr.pr_number(+)");
		whereClause.append(" AND v.line_item = crr.line_item(+)");
		whereClause.append(" AND v.receipt_id = crr.receipt_id(+)");
		whereClause.append(" AND v.issue_id = crr.issue_id(+)");

		if (!StringHandler.isBlankString(inputBean.getHub())) {
			whereClause.append(" AND v.source_hub = ").append(SqlHandler.delimitString(inputBean.getHub()));
		}
		
		if (!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
			whereClause.append(" AND v.inventory_group = ").append(SqlHandler.delimitString(inputBean.getInventoryGroup()));
		}
		
		if (!StringHandler.isBlankString(inputBean.getCompanyId())) {
			whereClause.append(" AND v.company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
		}
		
		if (inputBean.getPrNumber() != null) {
			whereClause.append(" AND v.pr_number = ").append(inputBean.getPrNumber());
		} else if (inputBean.getPrNumberByReceiptId() != null) {
			StringBuilder subQuery = new StringBuilder("SELECT DISTINCT pr_number FROM MR_ISSUE_RECEIPT_DETAIL_VIEW");
			subQuery.append(" WHERE receipt_id = ").append(inputBean.getPrNumberByReceiptId());
			
			whereClause.append(" AND v.pr_number IN (").append(subQuery.toString()).append(")");
		}

		if (!StringHandler.isBlankString(inputBean.getLineItem())) {
			whereClause.append(" AND v.line_item = ").append(SqlHandler.delimitString(inputBean.getLineItem()));
		}
		
		return whereClause;
	}
	
	@SuppressWarnings("unchecked")
	private void findIssuesReturnedQuantity(Collection<MrIssueReceiptDetailViewBean> result) throws BaseException {
		List<String> issueIds = new ArrayList<String>();
		Map<String, MrIssueReceiptDetailViewBean> issuesMap = new HashMap<String, MrIssueReceiptDetailViewBean>();
		
		for (MrIssueReceiptDetailViewBean bean : result) {
			if (!StringUtils.isBlank(bean.getNote())) {
				issueIds.add(bean.getIssueId().toPlainString());
			}
			
			issuesMap.put(bean.getIssueId().toPlainString(), bean);
			
			bean.setCrTotalReturnedQuantity(BigDecimal.ZERO);
		}
		
		if (issueIds.size() > 0) {
			//Get CMS For Credit request's returned quantity by getting quantity of issues
			//created through p_reverse_issue and the likes
			StringBuilder query = new StringBuilder("SELECT");
			query.append(" TO_NUMBER(REGEXP_SUBSTR(REGEXP_SUBSTR(program_code, '.+\\([0-9]+'), '[0-9]+')) issue_id");
			query.append(", ABS(SUM(quantity)) quantity");
			query.append(" FROM ISSUE");
			query.append(" WHERE quantity < 0");
			query.append(" AND program_code LIKE 'execute p_reverse_issue%'");
			query.append(" AND issue_id IN (").append(String.join(",", issueIds)).append(")");
			query.append(" GROUP BY REGEXP_SUBSTR(REGEXP_SUBSTR(program_code, '.+\\([0-9]+'), '[0-9]+')");
			
			Collection<IssueBean> reversedIssues = factory.setBean(new IssueBean()).selectQuery(query.toString());
			
			if (CollectionUtils.isNotEmpty(reversedIssues)) {
				for (IssueBean bean : reversedIssues) {
					issuesMap.get(bean.getIssueId().toPlainString()).setCrTotalReturnedQuantity(bean.getQuantity());
				}
			}
		}
	}
	
	private Vector<MrIssueReceiptDetailViewBean> getCmsReceiptsToCreateReturnRequest(Collection<MrIssueReceiptDetailViewBean> issuesColl)
			throws BeanCopyException {
		Iterator<MrIssueReceiptDetailViewBean> iter = issuesColl.iterator();
		Map<BigDecimal, MrIssueReceiptDetailViewBean> receiptsMap = new LinkedHashMap<BigDecimal, MrIssueReceiptDetailViewBean>();
		while (iter.hasNext()) {
			MrIssueReceiptDetailViewBean curBean = iter.next();
			
			BigDecimal curReceiptId = curBean.getReceiptId();
			if (!receiptsMap.containsKey(curReceiptId)) {
				if (curBean.getTotalShipped().compareTo(BigDecimal.ZERO) == -1) {
					curBean.setTotalShipped(BigDecimal.ZERO);
				}
				curBean.setTotalReturned(curBean.getCoTotalReturnedQuantity()
												.add(curBean.getCrTotalReturnedQuantity()));
				curBean.setTotalAvailable(curBean.getTotalShipped()
													.subtract(curBean.getTotalPendingReturn())
													.subtract(curBean.getTotalReturned()));
				
				receiptsMap.put(curReceiptId, curBean);
			} else {
				MrIssueReceiptDetailViewBean curReceiptBean = receiptsMap.get(curReceiptId);
				
				if (curBean.getTotalShipped().compareTo(BigDecimal.ZERO) == 1) {
					curReceiptBean.setTotalShipped(curReceiptBean.getTotalShipped().add(curBean.getTotalShipped()));
				}
				curReceiptBean.setTotalPendingReturn(curReceiptBean.getTotalPendingReturn()
																	.add(curBean.getTotalPendingReturn()));
				curReceiptBean.setTotalReturned(curReceiptBean.getTotalReturned()
																.add(curBean.getCrTotalReturnedQuantity()));
				curReceiptBean.setTotalAvailable(curReceiptBean.getTotalShipped()
																.subtract(curReceiptBean.getTotalPendingReturn())
																.subtract(curReceiptBean.getTotalReturned()));
			}
		}
		
		return new Vector<MrIssueReceiptDetailViewBean>(receiptsMap.values());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ExcelHandler getExcelReport(CustomerReturnInputBean inputBean, DropDownNamesInputBean dBean)
			throws NoDataException, BaseException, Exception {
		ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
		ExcelHandler pw = new ExcelHandler(res);
		pw.addTable(res.getString("newCustomerReturns"));

		pw.addRow();
		pw.addCellBold(res.getString("label.operatingentity"));
		pw.addCell(dBean.getOpsEntityName());
		pw.addRow();
		pw.addCellBold(res.getString("label.hub"));
		pw.addCell(dBean.getHubName());
		pw.addRow();
		pw.addCellBold(res.getString("label.inventorygroup"));
		pw.addCell(dBean.getInventoryGroupName());
		pw.addRow();
		pw.addCellBold(res.getString("label.prnumber"));
		pw.addCell(inputBean.getPrNumber());
		pw.addRow();
		pw.addCellBold(res.getString("label.xbyy").replace("{0}", res.getString("label.prnumber"))
													.replace("{1}", res.getString("label.receiptid")));
		pw.addCell(inputBean.getPrNumberByReceiptId());
		pw.addRow();
		pw.addRow();

		Vector<BaseDataBean> data = (Vector) getCmsReceiptsToCreateReturnRequest(inputBean);
		
		if (CollectionUtils.isEmpty(data)) {
			pw.addCell(res.getString("label.norecordfound"));
		} else {
			Vector<String> setupDataArr = new Vector<String>();
			setupDataArr.add("label.prnumber|||prNumber");
			setupDataArr.add("label.lineitem|||lineItem");
			setupDataArr.add("label.inventorygroup|||inventoryGroupName");
			setupDataArr.add("label.company|||companyName");
			setupDataArr.add("label.receiptid|||receiptId");
			setupDataArr.add("label.lotstatus|||lotStatus");
			setupDataArr.add("label.mfglot|||mfgLot");
			setupDataArr.add("label.expiredate|" + ExcelHandler.TYPE_CALENDAR + "||expireDate");
			setupDataArr.add("label.itemid|||itemId");
			setupDataArr.add("label.catpartno|||catPartNo");
			setupDataArr.add("label.shipped|||totalShipped");
			setupDataArr.add("label.returned|||totalReturned");
			setupDataArr.add("label.pendingreturn|||totalPendingReturn");
			setupDataArr.add("label.availabletorequest|||totalAvailable");
			
			pw.fill(setupDataArr, data);
		}

		return pw;
	}
}