package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.function.Function;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.MfrNotificationCategoryBean;
import com.tcmis.internal.catalog.beans.MfrNotificationMgmtInputBean;
import com.tcmis.internal.catalog.beans.MfrNotificationMgmtViewBean;

public class MfrNotificationMgmtProcess extends GenericProcess {

	public MfrNotificationMgmtProcess(String client) {
		super(client);
	}
	
	public Collection<MfrNotificationCategoryBean> getNotificationCategories() throws BaseException {
		return getNotificationCategories(Collections.emptyList());
	}
	
	public String generateNotificationId() throws BaseException {
		return factory.selectSingleValue("select mfr_notification_seq.nextval from dual");
	}
	
	public Collection<MfrNotificationCategoryBean> getNotificationCategories(Collection<BigDecimal> selectedCategories) throws BaseException {
		//Collection<MfrNotificationCategoryBean> categories = getNotificationCategories();
		String query = "select mfr_req_category_id, mfr_req_category_desc"
				+ ", status, manufacturer_data, material_data, item_data, search_criteria"
				+ " from mfr_notification_category order by mfr_req_category_desc";
		@SuppressWarnings("unchecked")
		Collection<MfrNotificationCategoryBean> categories = factory.setBean(new MfrNotificationCategoryBean()).selectQuery(query);
		
		for (MfrNotificationCategoryBean category : categories) {
			for (BigDecimal categoryId : selectedCategories) {
				if (categoryId != null && categoryId.compareTo(category.getMfrReqCategoryId()) == 0) {
					category.setSelected(true);					
					break;
				}
			}
		}
		
		return categories;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<PersonnelBean> getChemicalApprovers() throws BaseException {
		String query = "select max(p.first_name) first_name, max(p.last_name) last_name, ca.personnel_id"
				+ " from personnel p, chemical_approver ca"
				+ " where ca.active = 'y' and ca.personnel_id = p.personnel_id"
				+ " and ca.approval_role = 'MSDS Indexing'"
				+ " and P.COMPANY_ID = 'Radian' group by ca.personnel_id order by 2";
		
		return factory.setBean(new PersonnelBean()).selectQuery(query);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MfrNotificationMgmtViewBean> searchNotificationRequests(MfrNotificationMgmtInputBean input) throws BaseException {
		StringBuilder criteria = new StringBuilder();
		StringBuilder tables = new StringBuilder();
		criteria.append("mnr.notification_id = mnrc2.notification_id and mnrc.notification_id = mnrc2.notification_id");
		criteria.append(" and mnrc.mfr_req_category_id = mnc.mfr_req_category_id");
		if (input.getRequester() != null) {
			criteria.append(" and mnr.inserted_by = ").append(input.getRequester());
		}
		if (input.getAssignedTo() != null) {
			if (input.getAssignedTo().intValue() == -1) {
				criteria.append(" and mnr.qc_assigned_to is null");
			}
			else {
				criteria.append(" and mnr.qc_assigned_to = ").append(input.getAssignedTo());
			}
		}
		
		// handle status
		{
			StringBuilder statusFilter = new StringBuilder();
			if ( ! StringHandler.isBlankString(input.getDraftStatus())) {
				statusFilter.append(SqlHandler.delimitString(input.getDraftStatus()));
			}
			if ( ! StringHandler.isBlankString(input.getPendingApprovalStatus())) {
				if (statusFilter.length() > 0) {
					statusFilter.append(",");
				}
				statusFilter.append(SqlHandler.delimitString(input.getPendingApprovalStatus()));
			}
			if ( ! StringHandler.isBlankString(input.getApprovedStatus())) {
				if (statusFilter.length() > 0) {
					statusFilter.append(",");
				}
				statusFilter.append(SqlHandler.delimitString(input.getApprovedStatus()));
				criteria.append(" and nvl(mnr.qc_date,sysdate) > sysdate - ").append(input.getApprovedWindow());
			}
			if ( ! StringHandler.isBlankString(input.getRejectedStatus())) {
				if (statusFilter.length() > 0) {
					statusFilter.append(",");
				}
				statusFilter.append(SqlHandler.delimitString(input.getRejectedStatus()));
			}
			
			if (statusFilter.length() > 0) {
				criteria.append(" and mnr.status in (").append(statusFilter).append(")");
			}
		}
		if ( ! StringHandler.isBlankString(input.getSelectedCategories())) {
			criteria.append(" and mnrc2.mfr_req_category_id in (");
			String[] categories = input.getSelectedCategories().split(",");
			boolean first = true;
			for (String cat : categories) {
				if ( ! first) {
					criteria.append(",");
				}
				criteria.append(SqlHandler.delimitString(cat.trim()));
				first = false;
			}
			criteria.append(")");
		}
		if ( ! StringHandler.isBlankString(input.getSearchText())) {
			Function<String, String> searchTypeFn = null;
			if ("is".equals(input.getSearchType())) {
				searchTypeFn = what -> "lower(" + what + ") = lower(" + SqlHandler.delimitString(input.getSearchText()) + ")";
			}
			else if ("startsWith".equals(input.getSearchType())) {
				searchTypeFn = what -> "lower(" + what + ") like lower(" + SqlHandler.delimitString(input.getSearchText()+"%") + ")";
			}
			else if ("endsWith".equals(input.getSearchType())) {
				searchTypeFn = what -> "lower(" + what + ") like lower(" + SqlHandler.delimitString("%"+input.getSearchText()) + ")";
			}
			else if ("contains".equals(input.getSearchType())) {
				searchTypeFn = what -> "lower(" + what + ") like lower(" + SqlHandler.delimitString("%"+input.getSearchText()+"%") + ")";
			}
			
			if (input.isMaterialIdSearch()) {
				tables.append(", mfr_notification_req_detail d");
				criteria.append(" and ").append(searchTypeFn.apply("d.material_id"));
				criteria.append(" and d.notification_category_id = mnrc.notification_category_id");
			}
			else if (input.isMaterialDescSearch()) {
				tables.append(", mfr_notification_req_detail d, global.material m");
				criteria.append(" and ").append(searchTypeFn.apply("m.material_desc"));
				criteria.append(" and d.material_id = m.material_id");
				criteria.append(" and d.notification_category_id = mnrc.notification_category_id");
			}
			else if (input.isMfrIdSearch()) {
				tables.append(", mfr_notification_req_detail d");
				criteria.append(" and ").append(searchTypeFn.apply("d.mfg_id"));
				criteria.append(" and d.notification_category_id = mnrc.notification_category_id");
			}
			else if (input.isMfrDescSearch()) {
				tables.append(", mfr_notification_req_detail d, global.manufacturer mfr");
				criteria.append(" and ").append(searchTypeFn.apply("mfr.mfg_desc"));
				criteria.append(" and d.mfg_id = mfr.mfg_id");
				criteria.append(" and d.notification_category_id = mnrc.notification_category_id");
			}
			else if (input.isItemIdSearch()) {
				tables.append(", mfr_notification_req_detail d");
				criteria.append(" and ").append(searchTypeFn.apply("d.item_id"));
				criteria.append(" and d.notification_category_id = mnrc.notification_category_id");
			}
			else if (input.isItemDescSearch()) {
				tables.append(", mfr_notification_req_detail d, global.item i");
				criteria.append(" and ").append(searchTypeFn.apply("i.item_desc"));
				criteria.append(" and d.item_id = i.item_id");
				criteria.append(" and d.notification_category_id = mnrc.notification_category_id");
			}
			else if (input.isAcquiredMfrIdSearch()) {
				criteria.append(" and ").append(searchTypeFn.apply("mnrc.acquired_mfr_id"));
			}
			else if (input.isAcquiredMfrDescSearch()) {
				tables.append(", global.manufacturer mfr");
				criteria.append(" and ").append(searchTypeFn.apply("mfr.mfg_desc"));
				criteria.append(" and mfr.mfg_id = mnrc.acquired_mfr_id");
			}
			else if (input.isNotificationIdSearch()) {
				criteria.append(" and ").append(searchTypeFn.apply("mnr.notification_id"));
			}
			else if (input.isInternalCommentsSearch()) {
				criteria.append(" and ").append(searchTypeFn.apply("mnr.internal_comments"));
			}
		}
		
		String query = "select mnr.notification_id"
				+ ", fx_personnel_id_to_name(mnr.inserted_by) requester"
				+ ", mnr.status"
				+ ", mnr.qc_assigned_to"
				+ ", mnr.date_submitted"
				+ ", mnr.internal_comments"
				+ ", mnrc.mfr_req_category_id"
				+ ", mnrc.comments"
				+ ", mnc.mfr_req_category_desc"
				+ " from mfr_notification_request mnr"
				+ ", mfr_notification_req_category mnrc"
				+ ", mfr_notification_req_category mnrc2"
				+ ", mfr_notification_category mnc"
				+ tables.toString()
				+ " where " + criteria.toString()
				+ " group by mnr.notification_id"
				+ ", mnr.inserted_by"
				+ ", mnr.status"
				+ ", mnr.qc_assigned_to"
				+ ", mnr.date_submitted"
				+ ", mnr.internal_comments"
				+ ", mnrc.mfr_req_category_id"
				+ ", mnrc.comments"
				+ ", mnc.mfr_req_category_desc"
				+ " order by mnr.notification_id";
		
		return factory.setBean(new MfrNotificationMgmtViewBean()).selectQuery(query);
	}
	
	public void updateAssignees(Collection<MfrNotificationMgmtViewBean> notificationReqs) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			BigDecimal notificationId = new BigDecimal(-1);
			for (MfrNotificationMgmtViewBean request : notificationReqs) {
				if (notificationId.compareTo(request.getNotificationId()) != 0) {
					String stmt = "update mfr_notification_request set qc_assigned_to = "
							+ request.getQcAssignedTo()
							+ " where notification_id = " + request.getNotificationId();
				
					factory.deleteInsertUpdate(stmt,conn);
				}				
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	public ExcelHandler getExcelReport(MfrNotificationMgmtInputBean bean, PersonnelBean personnelBean, Locale locale) throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<MfrNotificationMgmtViewBean> data = searchNotificationRequests(bean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		String[] headerkeys = {"label.notificationrequest","label.status","label.category","label.assignedto","label.requestor","label.datesubmitted"};

		int[] widths = {0, 0, 0, 0, 0, 0};
		int[] types = {0, 0, 0, 0, 0, pw.TYPE_DATE};
		int[] aligns = {0, 0, 0, 0, 0, 0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (MfrNotificationMgmtViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getNotificationId());
			pw.addCell(member.getStatus());
			pw.addCell(member.getMfrReqCategoryDesc());
			pw.addCell(member.getQcAssignedTo());
			pw.addCell(member.getRequester());
			pw.addCell(member.getDateSubmitted());
		}

		return pw;
	}
}
