package com.tcmis.internal.hub.process;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserGroupMemberBean;
import com.tcmis.common.admin.beans.UserGroupMemberIgBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
//import com.tcmis.demo.beans.DisplayOnlyInputBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import com.tcmis.internal.hub.beans.ReceivingStatusInputBean;
import com.tcmis.internal.hub.beans.ReceivingStatusViewBean;
import com.tcmis.ws.tablet.beans.InboundShipmentBean;
import com.tcmis.ws.tablet.beans.InboundShipmentDetailBean;

/******************************************************************************
 * Process for allocation analysis
 * 
 * @version 1.0
 *****************************************************************************/
public class ReceivingStatusProcess extends GenericProcess {
	Log	log	= LogFactory.getLog(this.getClass());

	public ReceivingStatusProcess(String client) {
		super(client);
	}

	public ReceivingStatusProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection<UserGroupMemberIgBean> getReceivingQcPersonnelForHubInventoryGroups(ReceivingStatusInputBean input, PersonnelBean user) throws BaseException {
		StringBuilder sql = new StringBuilder("SELECT   UGMIG.*, fx_personnel_id_to_name (ugmig.personnel_id) user_name");
		sql.append(" FROM   user_group_member_ig ugmig, user_company uc, hub h");
		sql.append(" WHERE       ugmig.user_group_id = 'ReceivingQC'");
		sql.append(" AND ugmig.personnel_id NOT IN (select ugmoe.personnel_id from user_group_member_ops_entity ugmoe where ugmoe.user_group_id in ('developers', 'operationsSupport'))");
		sql.append(" AND ugmig.inventory_group in (");

		Collection<String> inventoryGroups = input.hasInventoryGroupList() ? Arrays.asList(input.getInventoryGroupList().split("\\|")) : user.getHub(input.getHub()).getInventoryGroupsForSearch();
		for (String ig : inventoryGroups) {
			sql.append("'").append(ig).append("',");
		}
		sql.deleteCharAt(sql.length() - 1);

		sql.append(")");
		sql.append(" AND ugmig.FACILITY_ID = h.hub_name AND h.branch_plant = '").append(input.getHub()).append("'");
		sql.append(" AND uc.personnel_id = ugmig.personnel_id");
		sql.append(" AND uc.company_id = ugmig.company_id");
		sql.append(" AND uc.status = 'A'");

		sql.append(" ORDER BY   inventory_group, user_name");
		return factory.setBean(new UserGroupMemberIgBean()).selectQuery(sql.toString());
	}

	public Collection<UserGroupMemberIgBean> getReceivingQcPersonnelForHub(ReceivingStatusInputBean input) throws BaseException {
		StringBuilder sql = new StringBuilder("SELECT  DISTINCT UGMIG.personnel_id, fx_personnel_id_to_name (ugmig.personnel_id) user_name");
		sql.append(" FROM   user_group_member_ig ugmig, user_company uc, hub h");
		sql.append(" WHERE       ugmig.user_group_id = 'ReceivingQC'");
		sql.append(" AND ugmig.personnel_id NOT IN (select ugmoe.personnel_id from user_group_member_ops_entity ugmoe where ugmoe.user_group_id in ('developers', 'operationsSupport'))");
		sql.append(" AND ugmig.FACILITY_ID = h.hub_name AND h.branch_plant = '").append(input.getHub()).append("'");
		sql.append(" AND uc.personnel_id = ugmig.personnel_id");
		sql.append(" AND uc.company_id = ugmig.company_id");
		sql.append(" AND uc.status = 'A'");
		sql.append(" ORDER BY user_name");
		return factory.setBean(new UserGroupMemberIgBean()).selectQuery(sql.toString());
	}

	public Collection<ReceivingStatusViewBean> getReceivingStatus(ReceivingStatusInputBean input, PersonnelBean user) throws BaseException {
		factory.setBean(new ReceivingStatusViewBean());

		StringBuilder tableFunction = new StringBuilder("TABLE(PKG_HUB_AUTOMATION.FX_GET_RECEIPT_RECV_STATUS(");
		tableFunction.append(input.hasReceivingStatus() ? "'" + input.getReceivingStatus() + "'," : "'',");
		tableFunction.append("'',");
		tableFunction.append("'',");
		tableFunction.append("'" + input.getOpsEntityId() + "',");
		tableFunction.append(input.hasHub() ? "'" + input.getHub() + "'," : "'',");
		tableFunction.append(input.hasInventoryGroupList() ? "'" + input.getInventoryGroupList() + "', '|'" : "''");
		tableFunction.append("))");

		SearchCriteria criteria = new SearchCriteria();
		if (input.hasAssignedTo()) {
			if (input.isAssignedToUnassigned()) {
				criteria.addCriterion("assignedTo", SearchCriterion.IS, "null");
			}
			else {
				criteria.addCriterion("assignedTo", SearchCriterion.EQUALS, input.getAssignedTo());
			}
		}
		
		criteria.addCriterion("openQuantity", SearchCriterion.GREATER_THAN, "0");

		SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);
		sort.addCriterion("receivingStatusDate,inventoryGroup,carrier");
		Collection<ReceivingStatusViewBean> results = factory.select(criteria, sort, tableFunction.toString());
		Collection<UserGroupMemberIgBean> assignees = getReceivingQcPersonnelForHubInventoryGroups(input, user);
		for (ReceivingStatusViewBean row : results) {
			row.setAssignees(assignees);
		}
		return results;
	}

	public void updateAssignees(Collection<ReceivingStatusViewBean> beans) throws BaseException {
		for (ReceivingStatusViewBean bean : beans) {
			StringBuilder sql = new StringBuilder("update receipt set ASSIGNED_TO = ").append(bean.isAssingedToUnassigned() ? "null" : bean.getAssignedTo()).append(" where receipt_id = ").append(bean.getReceiptId());
			factory.deleteInsertUpdate(sql.toString());
		}

	}

	@SuppressWarnings("unchecked")
	public JSONArray getReceivingStatusJSON(ReceivingStatusInputBean input, PersonnelBean user) throws BaseException {
		JSONArray results = new JSONArray();
		Collection<ReceivingStatusViewBean> beans = getReceivingStatus(input, user);

		for (ReceivingStatusViewBean bean : beans) {
			results.put(BeanHandler.getJsonObject(bean));
		}

		return results;
	}

	public ExcelHandler getExcelReport(Collection<ReceivingStatusViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.status", "label.receipt", "label.item", "label.itemdescription", "label.quantity", "label.bin", "label.po", "label.supplier", "label.carrier", "label.trackingnumber", "label.hub", "label.inventorygroup", "label.arrivalscandate",
				"label.statusdate", "label.receivingNotes", "label.trackingnotes" };
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type. pw.TYPE_PARAGRAPH defaults to 40
		 * characters. pw.TYPE_CALENDAR set up the date with no time.
		 * pw.TYPE_DATE set up the date with time.
		 */
		int[] types = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH };

		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		// pw.setColumnDigit(6, 2);
		// pw.setColumnDigit(7, 2);

		for (ReceivingStatusViewBean bean : data) {

			pw.addRow();
			pw.addCell(bean.getReceivingStatus());
			pw.addCell(bean.getReceiptId());
			pw.addCell(bean.getItemId());
			pw.addCell(bean.getItemDesc());
			pw.addCell(bean.getQuantityReceived());
			pw.addCell(bean.getBin());
			pw.addCell(bean.getRadianPo());
			pw.addCell(bean.getSupplierName());
			pw.addCell(bean.getCarrier());
			pw.addCell(bean.getTrackingNumber());
			pw.addCell(bean.getHubName());
			pw.addCell(bean.getInventoryGroupName());
			pw.addCell(bean.getInitialScanDate());
			pw.addCell(bean.getReceivingStatusDate());
			pw.addCell(bean.getReceivingNotes());
			pw.addCell(bean.getTrackingNotes());
		}
		return pw;
	}
}
