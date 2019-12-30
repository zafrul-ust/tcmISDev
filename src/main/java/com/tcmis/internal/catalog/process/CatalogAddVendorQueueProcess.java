package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Optional;
import java.util.Vector;
import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.client.common.process.MsdsIndexingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.CatalogVendorProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.CatalogAddQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddReqMsdsInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestVendorBean;
import com.tcmis.internal.catalog.beans.CatalogAddVendorQueueInputBean;
import com.tcmis.internal.catalog.beans.CatalogItemDetailQcInputBean;
import com.tcmis.internal.catalog.factory.CatalogAddQcDataMapper;
import com.tcmis.internal.catalog.factory.CatalogAddQcDataMapperImpl;

public class CatalogAddVendorQueueProcess extends CatalogVendorProcess {
	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;
	private Connection connection = null;
	private Hashtable approvalRole = new Hashtable(3);
	private CatalogAddQcDataMapper dataMapper;

	public CatalogAddVendorQueueProcess(BigDecimal personnelId, String client) {
		super(personnelId, client);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
		//mapping between status and approval role
		approvalRole.put("Pending SDS Sourcing","Material QC");
		approvalRole.put("Pending SDS Indexing","MSDS Indexing");
		approvalRole.put("Pending Item Creation Approval","Item QC");
		approvalRole.put("Pending Item Creation","Item QC");
	}
	
	public void initDataMapper(Optional<String> factoryName) throws IllegalArgumentException {
		this.dataMapper = factoryName.flatMap(c -> {
			try {
				return Optional.ofNullable((CatalogAddQcDataMapper)Class.forName(c).newInstance());
			} catch(Exception e) {
				log.warn(e.getMessage());
			}
			return Optional.empty();
		}).orElse(new CatalogAddQcDataMapperImpl(dbManager, getLocaleObject()));
	}
	
	public void initDataMapper(Optional<String> factoryName, Optional<String> dataStore) throws IllegalArgumentException {
		this.dataMapper = factoryName.flatMap(c -> {
			try {
				CatalogAddQcDataMapper mapper = (CatalogAddQcDataMapper)Class.forName(c).getConstructor(String.class).newInstance(dataStore.orElse(""));
				return Optional.ofNullable(mapper);
			} catch(Exception e) {
				log.warn(e.getMessage());
			}
			return Optional.empty();
		}).orElse(new CatalogAddQcDataMapperImpl(dbManager, getLocaleObject()));
	}
	
	protected CatalogAddQcDataMapper getDataMapper() {
		if (dataMapper == null) {
			dataMapper = new CatalogAddQcDataMapperImpl(dbManager, getLocaleObject());
		}
		return dataMapper;
	}
	
	public String getUserSupplier(PersonnelBean user) throws BaseException {
		return getDataMapper().userSupplier(user);
	}

	private String updateWorkQueue(CatalogAddRequestVendorBean bean, PersonnelBean personnelBean) {
		String result = "";
		try {
			//record as problem
			StringBuilder query = new StringBuilder("insert into catalog_queue_problem (q_id,assigned_to,assigned_date,status,supplier,supplier_approved_by,supplier_approved_date,insert_date,reported_by,problem_type,comments)");
			query.append(" select q_id,assigned_to,assigned_date,status,supplier,supplier_approved_by,supplier_approved_date,sysdate,").append(personnelBean.getPersonnelId()).append(",'").append(bean.getProblemType()).append("'");
			query.append(",").append(SqlHandler.delimitString(bean.getComments()));
			query.append(" from catalog_queue where q_id = ").append(bean.getqId());
			factory.deleteInsertUpdate(query.toString(), connection);

			//updating catalog_queue
			query = new StringBuilder("update catalog_queue set supplier = '").append(bean.getSupplier()).append("'");
			query.append(",status = 'Open',assigned_to = null,assigned_date = null");
			query.append(",catalog_vendor_assignment_id = ").append("(select min(cva.catalog_vendor_assignment_id) from catalog_vendor_assignment cva, catalog_queue cq, catalog_queue_item cqi");
			query.append(" where cq.q_id = ").append(bean.getqId()).append(" and cva.supplier = '").append(bean.getSupplier()).append("'");
			query.append(" and cq.item_id = cqi.item_id and cq.locale_code = cqi.locale_code and cqi.catalog_queue_item_id = cva.catalog_queue_item_id and cq.task = cqi.task)");
			query.append(" where q_id = ").append(bean.getqId());
			query.append(" and status not in ('Closed', 'Approved', 'Pending Approval')");
			factory.deleteInsertUpdate(query.toString(), connection);

			//notify vendor that work item was removed from their work queue
			notifyVendor(bean);
		} catch (Exception e) {
			result = library.getString("error.db.update");
			log.error(result,e);
		}
		return result;
	}  //end of method

	public void notifyVendor(CatalogAddRequestVendorBean bean) {
		notifyVendor(bean, null);
	}
	
	public void notifyVendor(CatalogAddRequestVendorBean bean, PersonnelBean user) {
		StringBuilder emsg = new StringBuilder("");
		try {
			emsg.append("Your work item: ").append(bean.getqId()).append(" ").append(bean.getTask()).append("\n");
			emsg.append("was changed for for the following reason: \n");
			emsg.append("\t\t").append(bean.getComments());
			//send vendor email if task is assigned
			String toEmail = getDataMapper().vendorNotificationEmail(bean);
			String replyTo = (user==null||StringHandler.isBlankString(user.getEmail()))?"tcmis@tcmis.com":user.getEmail();
			if (!StringHandler.isBlankString(toEmail))
				MailProcess.sendEmail(toEmail, null, replyTo, "Your work queue item was changed: " + bean.getqId(), emsg.toString());
		}catch (Exception e){
			MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "Sending email to vendor failed",emsg.toString());
		}
	} //end of method

	public String updateVendorWorkQueueItems(PersonnelBean personnelBean, Collection<CatalogAddRequestVendorBean> rows) throws BaseException {
		String result = "";
		try {
			connection = dbManager.getConnection();
			for (CatalogAddRequestVendorBean row : rows) {
				if (row.supplierChanged()) {
					result += updateWorkQueue(row, personnelBean);
				}
			}
		}catch (Exception ee) {
			result = library.getString("error.db.update");
			log.error(ee);
		}finally {
			dbManager.returnConnection(connection);
		}
		return result;
	}  //end of method

	public String releaseWorkQueueItems(PersonnelBean personnelBean, CatalogAddVendorQueueInputBean input, Collection<CatalogAddRequestVendorBean> rows) throws BaseException {
		String result = "";
		try {
			connection = dbManager.getConnection();

			Collection<CatalogAddReqMsdsInputBean> catAddBeans = new Vector();
			Vector catalogAddRequestLine = new Vector();
			for (CatalogAddRequestVendorBean row : rows) {
				//keep track of catalog_add_request_id so it can be move to another queue accordingly
				if (!catalogAddRequestLine.contains(row.getCatalogAddRequestId()+""+row.getLineItem())) {
					catalogAddRequestLine.addElement(row.getCatalogAddRequestId()+""+row.getLineItem());
					CatalogAddReqMsdsInputBean carmib = new CatalogAddReqMsdsInputBean();
					carmib.setCompanyId(row.getCompanyId());
					carmib.setRequestId(row.getCatalogAddRequestId().toString());
					carmib.setLineItem(row.getLineItem().toEngineeringString());
					carmib.setApprovalRole((String)approvalRole.get(input.getStatus()));
					catAddBeans.add(carmib);
				}
				//update work queue record
				workQueueItemApprovedByWesco(row,personnelBean);
			}
			//move catalog add request to another queue
			sendCatalogAddRequestToNextQueue(personnelBean,catAddBeans);
		}catch (Exception ee) {
			result = library.getString("error.db.update");
			log.error(ee);
		}finally {
			dbManager.returnConnection(connection);
		}
		return result;
	}  //end of method

	private void sendCatalogAddRequestToNextQueue(PersonnelBean personnelBean, Collection<CatalogAddReqMsdsInputBean> rows) {
		BigDecimal catalogAddRequestId = null;
		try {
			MsdsIndexingProcess msdsIndexingProcess = new MsdsIndexingProcess(personnelBean.getPersonnelIdBigDecimal(), this.getClient());
			CatalogItemDetailQcProcess itemQcProcess = new CatalogItemDetailQcProcess(personnelBean.getPersonnelIdBigDecimal(), this.getClient());
			for (CatalogAddReqMsdsInputBean row : rows) {
				if (row.isItemQcRole()) {
					CatalogItemDetailQcInputBean itemQcBean = new CatalogItemDetailQcInputBean();
					itemQcBean.setRequestId(new BigDecimal(row.getRequestId()));
					itemQcBean.setLineItem(new BigDecimal(row.getLineItem()));
					itemQcProcess.advanceCatAddRequest(itemQcBean,personnelBean,connection);
				}else {
					msdsIndexingProcess.approveRequest(row, personnelBean, connection);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "Error occurred while trying to move catalog add request to another queue: "+catalogAddRequestId,e.getMessage());
		}
	} //end of method

	private void workQueueItemApprovedByWesco(CatalogAddRequestVendorBean bean, PersonnelBean personnelBean) {
		try {
			StringBuilder query = new StringBuilder("update catalog_queue set approved_date = sysdate");
			query.append(",approved_by = ").append(personnelBean.getPersonnelId()).append(", status = 'Approved'");
			query.append(" where q_id = ").append(bean.getqId());
			factory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			e.printStackTrace();
			MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "Error occurred while releasing q_id: "+bean.getqId(),e.getMessage());
		}
	}  //end of method

	@SuppressWarnings("unchecked")
	public Collection getCompanyCatalogs(int personnelId) throws BaseException {
		StringBuilder query = new StringBuilder("select * from user_catalog_view where personnel_id = ").append(personnelId);
		query.append(" order by company_name,catalog_desc");
		return getSearchResult(query.toString(), new CatalogAddQcInputBean());
	}

	@SuppressWarnings("unchecked")
	public Collection getCatalogUsers(CatalogAddVendorQueueInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder("SELECT personnel_id, fx_personnel_id_to_name(personnel_id) personnel_name from ");
		query.append(" user_supplier order by 2");
		return getSearchResult(query.toString(),new CatalogAddQcInputBean());
	}

	public Collection<CatalogAddRequestVendorBean> getRequests(CatalogAddVendorQueueInputBean input) throws BaseException {
		String closedStatus = input.isCalledFromWesco()?"cq.status":"'Closed'";
		String problemStatus = input.isCalledFromWesco()?"decode(cq.status, 'Open', 'Problem', cq.status)":"'Problem'";
		return getDataMapper().listRequests(input, "decode(cq.closed, 'Y', "+closedStatus+", "+problemStatus+")", "TCM_OPS".equals(getClient()));
	}
	
	public Collection<CatalogAddRequestVendorBean> getRequestDetails(CatalogAddVendorQueueInputBean input) throws BaseException {
		return getDataMapper().listRequests(input, "decode(cq.status, 'Pending QC', 'Problem', cq.status)", false);
	}
	
	public Collection<CatalogAddRequestVendorBean> getFullRequest(CatalogAddVendorQueueInputBean input) throws BaseException {
		return getDataMapper().listRequests(input, "decode(cq.closed, 'N', decode(cq.status, 'Open', 'Problem', cq.status), cq.status)", "TCM_OPS".equals(getClient()));
	}
	
	private Collection<CatalogAddRequestVendorBean> getRequests(CatalogAddVendorQueueInputBean input, String fallbackStatus, boolean fullRequest) throws BaseException {
		StringBuilder query = new StringBuilder("");
		String fromClause = "";
		//initialize tasks according to status if this is called by catalog qc page
		if (input.isWescoApproval()) {
			input.initializeTasks();
			query.append("with cq as (select q.*,count(distinct q.status) over (partition by Q.CATALOG_ADD_REQUEST_ID,TASK) carn_ct");
			query.append(" from catalog_queue q) ");
			fromClause = " from cq, catalog_add_item_qc caiqc, vv_locale l";
		}else
			fromClause = " from catalog_queue cq, catalog_add_item_qc caiqc, vv_locale l";
		
		if (fullRequest) {
			fromClause += ", catalog_add_request_new carn, company, customer.catalog, customer.facility";
		}
		
		fromClause = fromClause+", (select q_id, problem_type, reported_by, insert_date, comments, row_number() over (partition by q_id order by insert_date desc) rank from catalog_queue_problem) cp";

		String statusString = "nvl2(cq.alt_supplier, "+fallbackStatus+", cq.status)";
		
		query.append("select cq.q_id")
				.append(", cq.assigned_to, fx_personnel_id_to_name(cq.assigned_to) assigned_to_name")
				.append(", cq.task")
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
				.append(", caiqc.company_id")
				.append(", cp.comments")
				.append(", cp.reported_by problem_reported_by")
				.append(", fx_personnel_id_to_name(cp.reported_by) problem_reported_by_name")
				.append(", cp.problem_type")
				.append(", cp.insert_date problem_report_date");
		
		if (fullRequest) {
			query.append(", company.company_name, catalog.catalog_desc, facility.facility_name");
		}
		query.append(fromClause);

		StringBuilder whereClause = new StringBuilder(" cq.catalog_add_request_id = caiqc.request_id and cq.line_item = caiqc.line_item")
				.append(" and nvl(cq.catalog_add_item_id,nvl(caiqc.catalog_add_item_id,-1)) = nvl(caiqc.catalog_add_item_id,-1) ")
				.append(" and cp.q_id(+) = cq.q_id and cp.rank(+) = 1 and l.locale_code = cq.locale_code");
		if (fullRequest) {
			whereClause.append(" and carn.request_id = cq.catalog_add_request_id")
					.append(" and carn.company_id = company.company_id")
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
			if (whereClause.length() > 0) {
				whereClause.append(" and");
			}
			if (input.isWescoApproval()) {
				whereClause.append(" ").append(statusString).append(" = 'Pending Approval'");
				whereClause.append(" and cq.carn_ct = 1");
			}else
				whereClause.append(" ").append(statusString).append(" = " + SqlHandler.delimitString(input.getStatus()));
		}
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
			if (mode.equals("is")) {
				whereClause.append(" ").append(field).append(" = ").append(SqlHandler.delimitString(searchText));
			}else if (mode.equals("like")) {
				whereClause.append(" lower(").append(field).append(") like lower('%").append(SqlHandler.validQuery(searchText)).append("%')");
			}else if (mode.equals("startsWith")) {
				whereClause.append(" lower(").append(field).append(") like lower('").append(SqlHandler.validQuery(searchText)).append("%')");
			}else if (mode.equals("endsWith")) {
				whereClause.append(" lower(").append(field).append(") like lower('%").append(SqlHandler.validQuery(searchText)).append("')");
			}
		}

		//put it all together
		if (whereClause.length() > 0) {
			query.append(" where").append(whereClause);
		}
		query.append(" group by cq.q_id, cq.assigned_to, cq.task,cq.status, cq.locale_code")
				.append(",l.locale_display, cq.task_complete_date, cq.supplier_approved_date")
				.append(",cq.approved_date, cq.approved_by,cq.supplier, cq.catalog_add_request_id")
				.append(",cq.line_item, caiqc.company_id, cp.comments, cp.reported_by")
				.append(",cp.problem_type, cp.insert_date, cq.alt_supplier");
		if (fullRequest) {
			query.append(",company.company_name, facility.facility_name, catalog.catalog_desc");
		}
		query.append(" order by cq.q_id");

		return factory.setBean(new CatalogAddRequestVendorBean()).selectQuery(query.toString());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ExcelHandler getExcelReport(CatalogAddVendorQueueInputBean input, Locale locale) throws NoDataException, BaseException, Exception {
		boolean isNonVendor = "TCM_OPS".equals(getClient());
		Vector<BaseDataBean> data;
		if (isNonVendor)
			data = (Vector)getFullRequest(input);
		else
			data = (Vector)getRequests(input);

		Vector<String> setupDataArr = new Vector<String>();

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		
		if (isNonVendor)
			setupDataArr.add("label.requestid|||catalogAddRequestId");
		setupDataArr.add("label.queueid|||qId");
		if (isNonVendor)
		{
			setupDataArr.add("label.company|||companyName");
			setupDataArr.add("label.facility|||facilityName");
			setupDataArr.add("label.catalog|||catalogDesc");
		}		
		setupDataArr.add("label.supplier|||supplier");
		setupDataArr.add("label.assignedto|||assignedToName");
		setupDataArr.add("label.task|||task");
		setupDataArr.add("label.status|||status");
		setupDataArr.add("label.materialdescription|||materialDesc");
		setupDataArr.add("label.manufacturer|||manufacturer");
		setupDataArr.add("label.locale|||localeCode");
		setupDataArr.add("label.problemcomments|||comments");
		if (isNonVendor)
			setupDataArr.add("label.requestcomments|||requestComments");
		setupDataArr.add("label.datecreated|"+ExcelHandler.TYPE_DATE+"||insertDate");
		setupDataArr.add("label.submitdate|"+ExcelHandler.TYPE_DATE+"||taskCompleteDate");
		setupDataArr.add("label.approveddate|"+ExcelHandler.TYPE_DATE+"||approvedDate");
		setupDataArr.add("label.approvedby|||approvedByName");
		setupDataArr.add("label.invoicenumber|||invoiceNumber");
		setupDataArr.add("label.invoicedate|"+ExcelHandler.TYPE_DATE+"||invoicedDate");
		
		pw.fill(setupDataArr, data);
		
		return pw;
	}

	public Collection updateAssignedTo(CatalogAddVendorQueueInputBean input, Collection<CatalogAddRequestVendorBean> rows) throws BaseException, Exception {

		Collection<String> errorMessages = new Vector<String>();

		try {
			for (CatalogAddRequestVendorBean row : rows) {
				if (row.hasAssignedTo()) {
					getDataMapper().updateWqiAssignedTo(input, row);
				}
				else {
					getDataMapper().unassignWqi(input, row);
				}
			}
		}
		catch (Exception e) {
			String errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
			log.error(errorMsg, e);
		}

		return errorMessages;
	}

	public void approveRows(Collection<CatalogAddRequestVendorBean> rows, PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			for (CatalogAddRequestVendorBean row : rows) {
				String query = "update catalog_queue set"
						+ " status = 'Pending Approval'"
						+ ", supplier_approved_by = " + user.getPersonnelIdBigDecimal()
						+ ", supplier_approved_date = sysdate"
						+ " where q_id = " + row.getqId()
						+ " and status = 'Pending QC'";
				factory.deleteInsertUpdate(query, conn);
				
				updateMaterialSourcingFlag(row, conn);
            	if (row.isSourcingTask() && row.isPendingApprovalStatus()) {
					MsdsIndexingProcess sdsProcess = new MsdsIndexingProcess(user.getPersonnelIdBigDecimal(), this.getClient());
					sdsProcess.checkSetDataEntryComplete(row, user, conn);
				}
				notifyMasterDataTeam(row, conn);
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	private void updateMaterialSourcingFlag(CatalogAddRequestVendorBean input, Connection conn) throws BaseException {
		String query = "update catalog_add_item_qc caiq"
				+ " set material_id_sourced = decode(caiq.material_id_sourced, 'N', 'Y', 'X', 'X', 'Y', 'Y', 'N')"
				+ " where (caiq.request_id, catalog_add_item_id) = ("
				+ "select cq.catalog_add_request_id, cq.catalog_add_item_id"
				+ " from catalog_queue cq where q_id = " + input.getqId() + ")";
		
		factory.deleteInsertUpdate(query, conn);
	}
	
	private void notifyMasterDataTeam(CatalogAddRequestVendorBean input, Connection conn) throws BaseException {
		try {
			String query = "select count(*) from catalog_queue where catalog_add_request_id = "
					+ input.getCatalogAddRequestId()
					+ " and task = " + SqlHandler.delimitString(input.getTask())
					+ " and status <> 'Pending Approval'";
			
			String count = factory.selectSingleValue(query, conn);
			
			if (Integer.parseInt(count) == 0) {
				EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(), getLocale(), "");
				engEvalProcess.setFactoryConnection(factory, conn);
				engEvalProcess.sendApproversEmail(input.getCatalogAddRequestId(), "", Collections.emptyList(), Collections.emptyList(),false);
			}
		} catch(Exception e) {
			throw new BaseException(e);
		}
	}
	
	public void invoiceRows(Collection<CatalogAddRequestVendorBean> rows) throws BaseException {
		for (CatalogAddRequestVendorBean row : rows) {
			String query = "update catalog_queue set"
					+ " status = 'Invoiced'"
					+ " where q_id = " + row.getqId()
					+ " and status = 'Approved'";
			factory.deleteInsertUpdate(query);
		}
	}
	
	public void sendPaymentForRows(Collection<CatalogAddRequestVendorBean> rows) throws BaseException {
		for (CatalogAddRequestVendorBean row : rows) {
			String query = "update catalog_queue set"
					+ " status = 'Pending Payment'"
					+ " where q_id = " + row.getqId()
					+ " and status = 'Invoiced'";
			factory.deleteInsertUpdate(query);
		}
	}
	
	public Collection<CatalogAddVendorQueueInputBean> getCatalogQueueTasks() throws BaseException {
		String query = "select task, task_description from"
				+ " vv_catalog_queue_task";
		
		return factory.setBean(new CatalogAddVendorQueueInputBean()).selectQuery(query);
	}
	
	public Collection<CatalogAddVendorQueueInputBean> getCatalogQueueStatuses() throws BaseException {
		String query = "select status, status_description from"
				+ " vv_catalog_queue_status";
		
		return factory.setBean(new CatalogAddVendorQueueInputBean()).selectQuery(query);
	}
}
