package com.tcmis.internal.catalog.factory;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogAddRequestNewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.CatalogAddQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestVendorBean;
import com.tcmis.internal.catalog.beans.CatalogAddVendorQueueInputBean;

public class CatalogAddQcDataMapperImpl extends GenericSqlFactory implements CatalogAddQcDataMapper {

	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;
	public static Log log = LogFactory.getLog(CatalogAddQcDataMapper.class);
	private static final String VERIFY_SDS_SOURCING = "'Verify SDS Sourcing'";
	private static final String VERIFY_SDS_INDEXING = "'Verify SDS Indexing'";
	private static final String VERIFY_ITEM = "'Verify Item'";
	private static final String VERIFY_SDS_SOURCING_WESCO = "'Verify SDS Sourcing Wesco'";
	private static final String VERIFY_SDS_INDEXING_WESCO = "'Verify SDS Indexing Wesco'";
	private static final String VERIFY_ITEM_WESCO = "'Verify Item Wesco'";
	private static final String SDS_SOURCING_TASK = "'SDS Sourcing'";
	private static final String SDS_INDEXING_TASK = "'SDS Indexing'";
	private static final String ITEM_CREATION_TASK = "'Item Creation'";
	
	public CatalogAddQcDataMapperImpl(DbManager dbManager, Locale locale) {
		super(dbManager);
		library = new ResourceLibrary(RESOURCE_BUNDLE, locale);
	}
	
	private String decodeRoleFunction() {
		return "DECODE(car.role_function, "
				+ VERIFY_SDS_SOURCING + ", " + SDS_SOURCING_TASK + ", "
				+ VERIFY_SDS_SOURCING_WESCO + ", " + SDS_SOURCING_TASK + ", "
				+ VERIFY_SDS_INDEXING + ", " + SDS_INDEXING_TASK + ","
				+ VERIFY_SDS_INDEXING_WESCO + ", " + SDS_INDEXING_TASK + ", "
				+ VERIFY_ITEM + ", " + ITEM_CREATION_TASK + ", "
				+ VERIFY_ITEM_WESCO + ", " + ITEM_CREATION_TASK + ", '')";
				
	}
	
	protected String listRequestQuery(CatalogAddVendorQueueInputBean input, String fallbackStatus, boolean fullRequest) {
		StringBuilder query = new StringBuilder("");
		String fromClause = "";
		//initialize tasks according to status if this is called by catalog qc page
		if (input.isWescoApproval() || input.isWescoTask()) {
			input.initializeTasks();
			query.append("with cq as (select q.*,count(distinct q.status) over (partition by Q.CATALOG_ADD_REQUEST_ID,TASK) carn_ct");
			query.append(" from catalog_queue q");
			if (input.isWescoApproval())
				query.append(" where approved_date is null and status <> 'Reversed'");
			query.append(") ");
			fromClause = " from cq, catalog_add_item_qc caiqc, vv_locale l";
		}else
			fromClause = " from catalog_queue cq, catalog_add_item_qc caiqc, vv_locale l";
		
		if (fullRequest) {
			fromClause += ", company, customer.catalog, customer.facility";
		}
		
		fromClause = fromClause+", catalog_add_request_new carn, vv_chemical_approval_role car, vv_catalog_add_request_status cars"
				+ ", (select q_id, problem_type, reported_by, insert_date, comments, row_number() over (partition by q_id order by insert_date desc) rank from catalog_queue_problem) cp";

		String statusString = "nvl2(cq.alt_supplier, "+fallbackStatus+", cq.status)";
		
		query.append("select cq.q_id")
				.append(", cq.assigned_to, fx_personnel_id_to_name(cq.assigned_to) assigned_to_name")
				.append(", cq.task")
				.append(", cq.closed")
				.append(", cq.insert_date")
				.append(", ").append(statusString).append(" status")
				.append(", pkg_catalog_queue.FX_MATERIAL_DESC(cq.q_id) material_desc")
				.append(", min(caiqc.manufacturer) manufacturer")
				.append(", cq.locale_code")
				.append(", l.locale_display")
				.append(", cq.task_complete_date")
				.append(", cq.supplier_approved_date")
				.append(", cq.approved_date")
				.append(", cq.approved_by, fx_personnel_id_to_name(cq.approved_by) approved_by_name")
				.append(", cq.supplier")
				.append(", cq.catalog_add_request_id")
				.append(", cq.line_item")
				.append(", cq.alt_supplier")
				.append(", min(caiqc.company_id) company_id")
				.append(", cp.comments")
				.append(", cp.reported_by problem_reported_by")
				.append(", fx_personnel_id_to_name(cp.reported_by) problem_reported_by_name")
				.append(", cp.problem_type")
				.append(", cp.insert_date problem_report_date")
				.append(", max(caiqc.comments) request_comments")
				.append(", car.target_completion_day sla_turnaround_time");
		
		if (fullRequest) {
			query.append(", company.company_name, catalog.catalog_desc, facility.facility_name");
		}
		query.append(fromClause);

		StringBuilder whereClause = new StringBuilder(" cq.catalog_add_request_id = caiqc.request_id and cq.line_item = caiqc.line_item")
				.append(" and nvl(cq.catalog_add_item_id,nvl(caiqc.catalog_add_item_id,-1)) = nvl(caiqc.catalog_add_item_id,-1) ")
				.append(" and cp.q_id(+) = cq.q_id and cp.rank(+) = 1 and l.locale_code = cq.locale_code")
				.append(" and carn.request_id = cq.catalog_add_request_id")
				.append(" and cq.task = ").append(decodeRoleFunction())
				.append(" and car.company_id = carn.company_id")
				.append(" and car.facility_id = carn.eng_eval_facility_id")
				.append(" and car.catalog_id = carn.catalog_id")
				.append(" and carn.request_status = cars.request_status")
				.append(" and carn.company_id = cars.company_id")
				.append(" and cars.approval_group = car.approval_group");
		
		if ( ! input.isTaskStatusApproved()) {
			whereClause.append(" and carn.approval_group_seq = car.approval_group_seq");
		}
		if (fullRequest) {
			whereClause.append(" and carn.company_id = company.company_id")
					.append(" and carn.catalog_company_id = catalog.company_id")
					.append(" and carn.catalog_id = catalog.catalog_id")
					.append(" and carn.company_id = facility.company_id")
					.append(" and carn.eng_eval_facility_id = facility.facility_id");
		}
		if (input.hasTask() || input.hasTasks()) {
			if (whereClause.length() > 0) {
				whereClause.append(" and");
			}
			if (input.hasTask())
				whereClause.append(" cq.task = ").append(SqlHandler.delimitString(input.getTask()));
			else {
				String tmpTasks = "";
				for (int i = 0; i < input.getTasks().size(); i++) {
					if (i > 0)
						tmpTasks += ",";
					tmpTasks += SqlHandler.delimitString((String)input.getTasks().elementAt(i));
				}
				whereClause.append(" cq.task in (").append(tmpTasks).append(")");
			}
		}
		if (input.hasStatus()) {
			if (input.isWescoApproval() || ! input.isWescoTask() || input.hasTaskStatus()){
				if (whereClause.length() > 0) {
					whereClause.append(" and");
				}
				if (input.isWescoApproval()) {
					whereClause.append(" ").append(statusString).append(" = 'Pending Approval'");
					whereClause.append(" and cq.carn_ct = 1");
				}
				else {
					String taskStatus = "";
					if (input.hasTaskStatus()) {
						taskStatus = Arrays.stream(input.getTaskStatus().split(",")).collect(Collectors.mapping(s -> SqlHandler.delimitString(s), Collectors.joining(",")));
					} else {
						taskStatus = Arrays.stream(input.getStatus().split(",")).collect(Collectors.mapping(s -> SqlHandler.delimitString(s), Collectors.joining(",")));
					}
					whereClause.append(" ").append(statusString).append(" in (").append(taskStatus).append(")");
				}
			}
		}
		if (input.hasCompanyId()) {
			whereClause.append(" and carn.company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
		}
		if (input.hasCatalogId()) {
			whereClause.append(" and carn.catalog_id = ").append(SqlHandler.delimitString(input.getCatalogId()));
		}
		if (input.hasSupplier()) {
			whereClause.append(" and cq.supplier = ").append(SqlHandler.delimitString(input.getSupplier()));
		}

		if (input.isExcludeMerck())
			whereClause.append(" and carn.company_id <> 'MERCK' ");

		if (input.hasAssignedTo()) {
			if (whereClause.length() > 0) {
				whereClause.append(" and");
			}
			if (input.isUnassigned())
				whereClause.append(" cq.assigned_to is null");
			else
				whereClause.append(" cq.assigned_to = ").append(input.getAssignedTo());
		}
		if(input.hasSearchArgument()) {
			String mode = input.getSearchMode();
			String field = input.getSearchField();
			String searchText = input.getSearchArgument();
			if (whereClause.length() > 0) {
				whereClause.append(" and");
			}
			if ("q_id".equals(field))
				field = "cq."+field;
			else {
				if ("requestId".equalsIgnoreCase(input.getSearchField())) {
					field = "caiqc.request_id";
				}
				else {
					field = "caiqc."+field;
				}
			}
			if (mode.equals("contains")) {
				whereClause.append(" lower(").append(field).append(") like lower('%").append(SqlHandler.validQuery(searchText)).append("%')");
			}else if (mode.equals("startsWith")) {
				whereClause.append(" lower(").append(field).append(") like lower('").append(SqlHandler.validQuery(searchText)).append("%')");
			}else if (mode.equals("endsWith")) {
				whereClause.append(" lower(").append(field).append(") like lower('%").append(SqlHandler.validQuery(searchText)).append("')");
			} else {
				whereClause.append(" ").append(field).append(" = ").append(SqlHandler.delimitString(searchText));
			}
		}

		//put it all together
		if (whereClause.length() > 0) {
			query.append(" where").append(whereClause);
		}
		query.append(" group by cq.q_id, cq.assigned_to, cq.task,cq.status, cq.locale_code")
				.append(",l.locale_display, cq.task_complete_date, cq.supplier_approved_date")
				.append(",cq.approved_date, cq.approved_by,cq.supplier, cq.catalog_add_request_id")
				.append(",cq.line_item, cp.comments, cp.reported_by, car.target_completion_day")
				.append(",cp.problem_type, cp.insert_date, cq.alt_supplier, cq.closed, cq.insert_date");
		if (fullRequest) {
			query.append(",company.company_name, facility.facility_name, catalog.catalog_desc");
		}
		
		query.append(" order by cq.catalog_add_request_id,cq.q_id");
		
		return query.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<CatalogAddRequestVendorBean> listRequests(CatalogAddVendorQueueInputBean input,
			String fallbackStatus, boolean fullRequest) throws BaseException {
		return this.setBean(new CatalogAddRequestVendorBean())
				.selectQuery(listRequestQuery(input, fallbackStatus, fullRequest));
	}
	
	@Override
	public void updateRequestAssignedTo(HashMap<String, Collection<String>> assignments, Collection<String> errorMessages) { 
		try {
			String[] format = new String[0];
			setBeanObject(new CatalogAddRequestNewBean());
            for (String user : assignments.keySet()) {
				SearchCriteria criteria = new SearchCriteria();
				String[] requestIds = assignments.get(user).toArray(format);
				criteria.addCriterionArray("requestId", SearchCriterion.IN, requestIds);
				deleteInsertUpdate("update catalog_add_request_new set assigned_to = " + (StringHandler.isBlankString(user) ? "null" : user) + " " + getWhereClause(criteria));
			}
		}
		catch (Exception e) {
			String errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
			log.error(errorMsg, e);
		}
	}  //end of method
	
	@Override
	public String userSupplier(PersonnelBean user) throws BaseException {
		String query = "select supplier from user_supplier where personnel_id = " 
					+ user.getPersonnelId()
					+ " and company_id = " + SqlHandler.delimitString(user.getCompanyId());
		
		return selectSingleValue(query);
	}

	@Override
	public void approveWorkQueueItem(CatalogAddRequestVendorBean bean, PersonnelBean personnelBean, Connection connection) throws BaseException {
		StringBuilder query = new StringBuilder("update catalog_queue set approved_date = sysdate");
		query.append(",approved_by = ").append(personnelBean.getPersonnelId()).append(", status = 'Approved'");
		query.append(" where q_id = ").append(bean.getqId());
		deleteInsertUpdate(query.toString(),connection);
	}

	@Override
	public void updateWqiAssignedTo(CatalogAddVendorQueueInputBean input, CatalogAddRequestVendorBean row)
			throws BaseException {
		String query = "update catalog_queue set"
				+ " status = 'Assigned'"
				+ ", assigned_to = " + row.getAssignedTo()
				+ ", assigned_date = sysdate"
				+ " where q_id = " + row.getqId()
				+ " and status in ('Open', 'Assigned')";
		deleteInsertUpdate(query);
	}

	@Override
	public void unassignWqi(CatalogAddVendorQueueInputBean input, CatalogAddRequestVendorBean row)
			throws BaseException {
		String query = "update catalog_queue set"
				+ " status = 'Open'"
				+ ", assigned_to = NULL"
				+ ", assigned_date = NULL"
				+ " where q_id = " + row.getqId()
				+ " and status in ('Open', 'Assigned')";
		deleteInsertUpdate(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<CatalogAddQcInputBean> listCatalogVendor() throws BaseException {
		StringBuilder query = new StringBuilder("select distinct s.supplier,s.supplier_name from supplier s, catalog_vendor_assignment cva where s.supplier = cva.supplier");
		query.append(" and cva.status = 'A' order by supplier_name");
		return setBean(new CatalogAddQcInputBean()).selectQuery(query.toString());
	}
	
	@Override
	public String vendorNotificationEmail(CatalogAddRequestVendorBean bean) throws BaseException {
		String query = "select fx_personnel_id_to_email(cq.assigned_to) from catalog_queue cq where q_id = " + bean.getqId() 
				+ " and alt_supplier is null"
				+ " union all"
				+ " select fx_personnel_id_to_email(cqp.assigned_to) from catalog_queue_problem cqp where problem_id in (" 
				+ " select max(problem_id) from catalog_queue_problem cqp, catalog_queue cq where cq.q_id = " + bean.getqId()
				+ " and cq.q_id = cqp.q_id and cq.alt_supplier is not null)";
		
		return selectSingleValue(query);
	}
}
