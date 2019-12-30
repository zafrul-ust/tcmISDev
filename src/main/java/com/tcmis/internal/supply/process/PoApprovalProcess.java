package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PoApprovalChainViewBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbConnectionException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.PoApprovalActionInputBean;
import com.tcmis.internal.supply.beans.PoApprovalCode;
import com.tcmis.internal.supply.beans.PoApprovalInputBean;
import com.tcmis.internal.supply.beans.PoApprovalViewBean;

public class PoApprovalProcess extends GenericProcess {

	public PoApprovalProcess(String client) {
		super(client);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<PersonnelBean> getBuyerDropDown() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
		return factory.selectBuyOrdersBuyer();
	}
	
	public BigDecimal getMaxApprovedValue(BigDecimal radianPo) throws BaseException {
		String query = "select nvl(max(approver_limit), 0) from po_approval_chain_vw where radian_po = "
				+ radianPo + " and action_taken = 'Approved'";
		String maxApprovedValue = factory.selectSingleValue(query);
		return new BigDecimal(maxApprovedValue);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<PoApprovalViewBean> getSearchResult(PoApprovalInputBean input, PersonnelBean user) throws BaseException {
		StringBuilder query = new StringBuilder();
		StringBuilder groupBy = new StringBuilder();
		groupBy.append(" GROUP BY pa.radian_po, pa.branch_plant, pa.hub_name, pa.ops_entity_id")
				.append(", pa.inserted_on, pa.buyer, pa.buyer_name, pa.oe_po_total, pa.usd_po_total")
				.append(", pa.buyer_limit, pa.oe_currency_id, pa.po_approval_status, pa.last_updated_on")
				.append(", pa.status_personnel_id, pa.approver_name, pa.po_approval_code, pa.status_comment")
				.append(", pa.date_created, pa.supplier, pa.supplier_name, pa.date_confirmed, pa.inventory_group")
				.append(", pa.po_approval_note, pa.po_approval_id, pac2.action_taken");
		query.append(" where pa.po_approval_id = pac.po_approval_id");
		query.append(" and pa.ops_entity_id = ").append(SqlHandler.delimitString(input.getOpsEntityId()));
		query.append(" and pa.radian_po = pac.radian_po");
		query.append(" and pac.po_approval_id = pac2.po_approval_id(+)");
		if (input.hasInventoryGroup()) {
			query.append(" and pa.inventory_group = ").append(SqlHandler.delimitString(input.getInventoryGroupId()));
		}
		if (input.hasHub()) {
			query.append(" and pa.branch_plant = ").append(SqlHandler.delimitString(input.getBranchPlant()));
		}
		if (input.hasSupplier()) {
			query.append(" and pa.supplier = ").append(input.getSupplierId());
		}
		if ( ! input.hasAllBuyers()) {
			if (input.hasNoBuyer()) {
				query.append(" and pa.buyer is null");
			}
			else {
				query.append(" and pa.buyer = ").append(input.getBuyerId());
			}
		}
		query.append(" and pac2.approver_personnel_id(+) = ").append(user.getPersonnelId());
		if ( ! input.hasAllApprovers()) {
			if (input.hasNoApprover()) {
				groupBy.append(", pac.approver_personnel_id HAVING pac.approver_personnel_id is null");
			}
			else {
				groupBy.append(", pac.approver_personnel_id HAVING pac.approver_personnel_id = ").append(input.getApproverId());
			}
			
		}
		if (input.hasCreatedDateFloor()) {
			query.append(" and pa.date_created >= ").append(DateHandler.getOracleToDateFunction(input.getCreatedFromDate()));
		}
		if (input.hasCreatedDateCeiling()) {
			query.append(" and pa.date_created <= ").append(DateHandler.getOracleToDateFunction(input.getCreatedToDate()));
		}
		boolean itemFilter = false;
		if (input.hasSearchText()) {
			String searchWhat = StringHandler.convertBeanPropertyToDatabaseColumn(input.getSearchWhat());
			String searchType = "";
			String searchText = input.getSearchText();
			if (input.isSearchEquals()) {
				searchType = " = ";
			}
			else {
				searchType = " like ";
				if (input.isSearchContains()) {
					searchText = "%"+searchText+"%";
				}
				else if (input.isSearchStartsWith()) {
					searchText = searchText+"%";
				}
				else if (input.isSearchStartsWith()) {
					searchText = "%"+searchText;
				}
				searchText = SqlHandler.delimitString(searchText);
			}
			if (searchWhat.equalsIgnoreCase("RADIAN_PO")) {
				query.append(" and pa.").append(searchWhat).append(searchType).append(searchText);
			}
			else if (searchWhat.equalsIgnoreCase("ITEM_ID")){
				itemFilter = true;
				query.append(" and i.").append(searchWhat).append(searchType).append(searchText);
				query.append(" and pa.radian_po = i.radian_po");
			}
		}
		if (input.hasStatus()) {
			query.append(" and lower(pa.po_approval_status) = lower(").append(SqlHandler.delimitString(input.getStatus())).append(")");
		}
		
		StringBuilder select = new StringBuilder();
		select.append("select pa.*, NVL(pac2.action_taken, 'None') action_taken ");
		select.append("from po_approval_vw pa, po_approval_chain_vw pac, po_approval_chain_vw pac2");
		// if true, we know it is and item search, and that searchText != null
		if (itemFilter) {
			select.append(", (select item_id, radian_po, po_line from po_line union select item_id, radian_po, po_line from po_line_draft) i");
		}
		
		query.insert(0, select);
		query.append(groupBy);
		query.append(" order by pa.radian_po");
		
		return factory.setBean(new PoApprovalViewBean()).selectQuery(query.toString());
	}
	
	public Collection<PoApprovalChainViewBean> getPoApprovalChain(PoApprovalViewBean bean) throws BaseException {
		String query = "select * from po_approval_chain_vw where radian_po = " + bean.getRadianPo()
				+ " and po_approval_id = " + bean.getPoApprovalId()
				+ " order by approver_limit, approver_name";
		
		return factory.setBean(new PoApprovalChainViewBean()).selectQuery(query);
	}
	
	public Collection<PoApprovalCode> getPoApprovalCodes(PoApprovalInputBean input) throws BaseException {
		String query = "select * from vv_po_approval_code where ops_entity_id = " 
				+ SqlHandler.delimitString(input.getOpsEntityId());
		
		return factory.setBean(new PoApprovalCode()).selectQuery(query);
	}
	
	public void approveAll(Collection<PoApprovalViewBean> beanColl, PersonnelBean user) throws BaseException {
		try {
			Connection conn = dbManager.getConnection();
			try {
				for (PoApprovalViewBean approval : beanColl) {
					approvePo(approval, user, conn);
				}
			} finally {
				dbManager.returnConnection(conn);
			}
		} catch(DbConnectionException e) {
			throw new BaseException(e);
		}
	}
	
	private void approvePo(PoApprovalViewBean bean, PersonnelBean user, Connection conn) throws BaseException {
		List<String> inParams = new ArrayList<>();
		inParams.add(bean.getRadianPo().toPlainString());
		inParams.add(user.getPersonnelIdBigDecimal().toPlainString());
		inParams.add(bean.getPoApprovalNotes());
		factory.doProcedure(conn, "pkg_financial_approval_po.p_approve_fa_pending_po", inParams);
	}
	
	public void rejectPo(PoApprovalActionInputBean bean, PersonnelBean user) throws BaseException {
		List<String> inParams = new ArrayList<>();
		inParams.add(bean.getRadianPo().toPlainString());
		inParams.add(user.getPersonnelIdBigDecimal().toPlainString());
		inParams.add(bean.getPoApprovalCode());
		inParams.add(bean.getActionComment());
		factory.doProcedure("pkg_financial_approval_po.p_reject_po", inParams);
	}
	
	public ExcelHandler getExcelReport(PoApprovalInputBean bean, PersonnelBean personnelBean, Locale locale) throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<PoApprovalViewBean> data = getSearchResult(bean, personnelBean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();
		pw.addCellKeyBold("label.operatingentity");
		pw.addCell(bean.getOpsEntityDesc());
		
		pw.addRow();
		pw.addCellKeyBold("label.hub");
		pw.addCell(bean.getHubName());
		
		pw.addRow();
		pw.addCellKeyBold("label.inventorygroup");
		pw.addCell(bean.getInventoryGroupDesc());
		
		pw.addRow();
		pw.addCellKeyBold("label.datecreated");
		pw.addCellKeyBold("label.from");
		pw.addCell(bean.getCreatedFromDate());
		pw.addCellKeyBold("label.to");
		pw.addCell(bean.getCreatedToDate());
		
		pw.addRow();
		pw.addCellKeyBold("label.supplier");
		pw.addCell(bean.getSupplierName());
		
		pw.addRow();
		pw.addCellKeyBold("label.status");
		pw.addCell(bean.getSelectedStatus());
		
		pw.addRow();
		pw.addCellKeyBold("label.buyer");
		pw.addCell(bean.getSelectedBuyer());
		
		pw.addRow();
		pw.addCellKeyBold("label.approver");
		pw.addCell(bean.getSelectedApprover());
		
		pw.addRow();
		pw.addCellBold(bean.getSearchWhatDesc() + " " + bean.getSearchTypeDesc());
		pw.addCell(bean.getSearchText());
		
		pw.addRow();
		pw.addRow();

		String[] headerkeys = {"label.po", "label.supplier", "label.buyer", "label.datecreated", "label.povaluelocalcurrency", "label.hub", 
				"label.inventorygroup", "label.povalueusd", "label.status", "label.quantity", "label.approvedrejecteddate", "label.approvedrejectedcomment"};

		int[] widths = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10};
		int[] types = {0, 0, 0, pw.TYPE_DATE, 0, 0, 0, 0, 0, 0, pw.TYPE_DATE, pw.TYPE_PARAGRAPH};
		int[] aligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (PoApprovalViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getRadianPo());
			pw.addCell(member.getSupplierName());
			pw.addCell(member.getBuyerName());
			pw.addCell(member.getInsertedOn());
			pw.addCell(member.getOePoTotal());
			pw.addCell(member.getHubName());
			pw.addCell(member.getInventoryGroup());
			pw.addCell(member.getPoValueUSD());
			pw.addCell(member.getPoApprovalStatus());
			pw.addCell(member.getQuantity());
			pw.addCell(member.getLastUpdatedOn());
			pw.addCell(member.getActionTaken());
		}

		return pw;
	}
}
