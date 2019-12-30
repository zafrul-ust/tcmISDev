package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;
import java.util.Vector;

import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.beans.CatalogAddRequestNewBean;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.CatalogAddItemQcBean;
import com.tcmis.internal.catalog.beans.CatalogAddQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestOvBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestPendingAssignBean;
import com.tcmis.internal.catalog.beans.CatalogAddVendorQueueInputBean;
import com.tcmis.internal.catalog.factory.CatalogAddQcDataMapper;
import com.tcmis.internal.catalog.factory.CatalogAddQcDataMapperImpl;
import com.tcmis.internal.catalog.factory.CatalogAddRequestOvBeanFactory;

public class CatalogAddQcProcess extends GenericProcess {
	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;
	private CatalogAddQcDataMapper dataMapper;

	public CatalogAddQcProcess(String client, Locale locale) {
		super(client, locale);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
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
			dataMapper = new CatalogAddQcDataMapperImpl(dbManager,getLocaleObject());
		}
		return dataMapper;
	}

	public Collection getCatalogVendor() throws BaseException {
		return getDataMapper().listCatalogVendor();
	}

	@SuppressWarnings("unchecked")
	public Collection getCompanyCatalogs(int personnelId) throws BaseException {
		StringBuilder query = new StringBuilder("select * from user_catalog_view where personnel_id = ").append(personnelId);
		query.append(" order by company_name,catalog_desc");
		return getSearchResult(query.toString(), new CatalogAddQcInputBean());
	}
	
	@SuppressWarnings("unchecked")
	public Collection getCatalogUsers(CatalogAddQcInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder("SELECT personnel_id, fx_personnel_id_to_name(personnel_id) personnel_name FROM (");
		query.append("select personnel_id from chemical_approver where active = 'y'");
        if ("Pending Spec".equals(input.getStatus()))
            query.append(" and approval_role = 'TCM Spec'");
        else if ("Pending Material".equals(input.getStatus()))
            query.append(" and approval_role = 'Material QC'");
        else if ("Pending Item".equals(input.getStatus()))
            query.append(" and approval_role = 'Item QC'");
        else if ("Pending MSDS Indexing".equals(input.getStatus()))
            query.append(" and approval_role = 'MSDS Indexing'");
        else if ("Pending Custom Indexing".equals(input.getStatus())) 
            query.append(" and approval_role = 'Custom Indexing'");
        else
            query.append(" and approval_role in ('Material QC','MSDS Indexing','Item QC','Custom Indexing','TCM Spec')");
        if (!StringHandler.isBlankString(input.getCompanyId()) && !input.isAllCompanies()) {
            query.append(" and company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
        }
        if (input.hasCatalogId()) {
            query.append(" and catalog_id = ").append(SqlHandler.delimitString(input.getCatalogId()));
        }
        query.append(" group by personnel_id) Order by 2");
		return getSearchResult(query.toString(),new CatalogAddQcInputBean());
	}
	
	public Collection<CatalogAddRequestPendingAssignBean> getPendingAssnRequests(CatalogAddQcInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder("select carn.request_id, carn.company_id, carn.catalog_id, carn.eng_eval_facility_id facility_id")
				.append(", caiq.material_desc, caiq.line_item, caiq.mfg_catalog_id, caiq.mfg_trade_name trade_name, caiq.dimension, caiq.grade, caiq.part_id")
				.append(", c.company_name, cc.catalog_desc, f.facility_name, caiq.manufacturer, caiq.item_qc_status, caiq.comments")
				.append(" from catalog_add_request_new carn, catalog_add_item_qc caiq, company c, catalog cc, customer.facility f")
				.append(", vv_catalog_add_request_status cars, vv_chemical_approval_role car")
				.append(" where carn.qc_status = ").append(SqlHandler.delimitString(input.getStatus()))
				.append(" and cars.request_status = carn.request_status")
				.append(" and cars.company_id = carn.company_id")
				.append(" and cars.approval_group > 0")
				.append(" and car.facility_id = carn.eng_eval_facility_id")
				.append(" and car.approval_role = carn.qc_status")
				.append(" and car.company_id = carn.company_id")
				.append(" and car.catalog_id = carn.catalog_id")
				.append(" and car.catalog_company_id = carn.catalog_company_id")
				.append(" and car.approval_group = cars.approval_group")
				.append(" and car.approval_group_seq = carn.approval_group_seq")
				.append(" and carn.request_id = caiq.request_id")
				.append(" and c.company_id = carn.company_id")
				.append(" and cc.catalog_id = carn.catalog_id")
		        .append(" and cc.catalog_company_id = carn.catalog_company_id")
				.append(" and cc.catalog_id = carn.catalog_id")
				.append(" and cc.company_id = carn.company_id")
				.append(" and f.company_id = carn.company_id")
				.append(" and f.facility_id = carn.eng_eval_facility_id");
		if ( ! input.getCompanyId().equals("ALL")) {
			query.append(" and carn.company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
		}
		if ( ! input.getCatalogId().isEmpty()) {
			query.append(" and carn.catalog_id = ").append(SqlHandler.delimitString(input.getCatalogId()));
		}
		if ( ! input.getSearchArgument().isEmpty()) {
			String searchMode = "=";
			String searchArgument = input.getSearchArgument();
			if ("contains".equals(input.getSearchMode())) {
				searchMode = "like";
				searchArgument = "%"+searchArgument+"%";
			}
			
			if (input.getSearchField().equals("requestId")) {
				query.append(" and lower(carn.request_id) = lower(")
						.append(SqlHandler.delimitString(searchArgument)).append(")");
			}
			else if (input.getSearchField().equals("manufacturer")) {
				query.append(" and lower(caiq.manufacturer) ").append(searchMode)
						.append(" lower(").append(SqlHandler.delimitString(searchArgument)).append(")");
			}
			else if (input.getSearchField().equals("material_desc")) {
				query.append(" and lower(caiq.material_desc) ").append(searchMode)
						.append(" lower(").append(SqlHandler.delimitString(searchArgument)).append(")");
			}
		}
		query.append(" order by carn.company_id, carn.catalog_id, carn.request_id, caiq.line_item, caiq.part_id");
			
		@SuppressWarnings("unchecked")
		Collection<CatalogAddRequestPendingAssignBean> beanColl = factory.setBean(new CatalogAddRequestPendingAssignBean()).selectQuery(query.toString());
		
		ResourceLibrary resource = new ResourceLibrary("uploadFile");
		String catalogAddMsdsUrl = resource.getString("server.url") + "tcmIS/";
		EngEvalProcess engEval = new EngEvalProcess(this.getClient(), this.getLocale(), catalogAddMsdsUrl);
		
		beanColl.forEach(catadd -> {
			catadd.setSdsPresent(engEval.getUserUploadedMsdsForRequest(catadd.getRequestId()).isEmpty()?"N":"Y");
		});
		
		return beanColl;
	}
	
	public void updatePendingAssignRequest(Collection<CatalogAddRequestPendingAssignBean> catAddReqs) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			for (CatalogAddRequestPendingAssignBean bean : catAddReqs) {
				updatePendingAssignQc(bean, conn);
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	private void updatePendingAssignQc(CatalogAddRequestPendingAssignBean bean, Connection conn) throws BaseException {
		String query = "update catalog_add_item_qc set material_desc = " + SqlHandler.delimitString(bean.getMaterialDesc())
				+ ", mfg_trade_name = " + SqlHandler.delimitString(bean.getTradeName())
				+ ", mfg_catalog_id = " + SqlHandler.delimitString(bean.getMfgCatalogId())
				+ ", grade = " + SqlHandler.delimitString(bean.getGrade())
				+ ", dimension = " + SqlHandler.delimitString(bean.getDimension())
				+ " where request_id = " + bean.getRequestId()
				+ " and line_item = " + bean.getLineItem()
				+ " and part_id = " + bean.getPartId();
		
		factory.deleteInsertUpdate(query, conn);
	}
	
	private void updatePendingAssignItem(CatalogAddRequestPendingAssignBean bean, Connection conn) throws BaseException {
		String query = "update catalog_add_item set material_desc = " + SqlHandler.delimitString(bean.getMaterialDesc())
				+ ", mfg_trade_name = " + SqlHandler.delimitString(bean.getTradeName())
				+ ", mfg_catalog_id = " + SqlHandler.delimitString(bean.getMfgCatalogId())
				+ ", grade = " + SqlHandler.delimitString(bean.getGrade())
				+ ", dimension = " + SqlHandler.delimitString(bean.getDimension())
				+ " where request_id = " + bean.getRequestId()
				+ " and line_item = " + bean.getLineItem()
				+ " and part_id = " + bean.getPartId();
		
		factory.deleteInsertUpdate(query, conn);
	}
	
	private void updatePendingAssignPostScreen(CatalogAddRequestPendingAssignBean bean, Connection conn) throws BaseException {
		String query = "update catalog_add_item_orig set postscreen_material_desc = " + SqlHandler.delimitString(bean.getMaterialDesc())
				+ ", postscreen_mfg_trade_name = " + SqlHandler.delimitString(bean.getTradeName())
				+ ", postscreen_mfg_catalog_id = " + SqlHandler.delimitString(bean.getMfgCatalogId())
				+ ", postscreen_grade = " + SqlHandler.delimitString(bean.getGrade())
				+ ", postscreen_dimension = " + SqlHandler.delimitString(bean.getDimension())
				+ " where request_id = " + bean.getRequestId()
				+ " and line_item = " + bean.getLineItem()
				+ " and part_id = " + bean.getPartId();
		
		factory.deleteInsertUpdate(query, conn);
	}
	
	public String approvePendingAssignRequest(CatalogAddQcInputBean inputBean, PersonnelBean user, boolean withholdFromVendor, Collection<CatalogAddRequestPendingAssignBean> catAddReqs) throws BaseException {
		String errorMsg = "";
		Connection conn = dbManager.getConnection();
		try {
			conn.setAutoCommit(false);
			CatalogAddRequestPendingAssignBean previous = null;
			for (CatalogAddRequestPendingAssignBean bean : catAddReqs) {
				if ( ! (previous == null || bean.getRequestId().compareTo(previous.getRequestId()) == 0)) {
					errorMsg = doApprovalStep(previous, inputBean, withholdFromVendor, user, conn);
					if ( ! "OK".equalsIgnoreCase(errorMsg))
						break;
				}
				previous = bean;
				
				updatePendingAssignQc(bean, conn);
				updatePendingAssignItem(bean, conn);
				updatePendingAssignPostScreen(bean, conn);
			}
			errorMsg = doApprovalStep(previous, inputBean, withholdFromVendor, user, conn);
			conn.setAutoCommit(true);
		} catch(SQLException e){
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch(SQLException ex) {
				throw new BaseException(ex);
			}
			throw new BaseException(e);
		} finally {
			dbManager.returnConnection(conn);
		}
		return errorMsg;
	}
	
	private String doApprovalStep(CatalogAddRequestPendingAssignBean bean, CatalogAddQcInputBean inputBean, 
			boolean withholdFromVendor, PersonnelBean user, Connection conn) throws BaseException, SQLException {
		bean.setApprovalRole(inputBean.getApprovalRole());
		flagCatAddToBeWithheldFromVendor(bean, withholdFromVendor, conn);
		String errorMsg = approvePreScreening(bean, user, conn);
		if ("OK".equalsIgnoreCase(errorMsg)) {
			conn.commit();
		}
		else {
			conn.rollback();
		}
		return errorMsg;
	}
	
	private void flagCatAddToBeWithheldFromVendor(CatalogAddRequestPendingAssignBean inputBean, boolean withholdFromVendor, Connection conn) throws BaseException {
		String withhold = withholdFromVendor?"Y":"N";
		String query = "update catalog_add_request_new set md_verified = '"+withhold+"'"
				+ " where request_id = " + inputBean.getRequestId();
		
		factory.deleteInsertUpdate(query, conn);
	}
	
	private String approvePreScreening(CatalogAddRequestPendingAssignBean inputBean, PersonnelBean user, Connection conn) throws BaseException {
		String errorMsg = "";
		try {
			StringBuilder query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
			factory.setBeanObject(new CatAddHeaderViewBean());
			Vector<CatAddHeaderViewBean> dataC = (Vector)factory.selectQuery(query.toString(),conn);
			CatAddHeaderViewBean bean = dataC.get(0);
			
			CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),"");
			process.setFactoryConnection(factory, conn);
			errorMsg = process.approvalRequestFromSpecificPage(bean,inputBean.getApprovalRole(),user);
		}catch (Exception e) {
			log.error(e);
			errorMsg = "approvePreScreening failed for request: "+inputBean.getRequestId();
		}
		return errorMsg;
	}

	public void rejectPendingAssignRequest(CatalogAddQcInputBean inputBean, PersonnelBean user, Collection<CatalogAddRequestPendingAssignBean> catAddReqs) throws BaseException {
		CatalogAddRequestPendingAssignBean previous = null;
		for (CatalogAddRequestPendingAssignBean bean : catAddReqs) {
			if (previous == null || bean.getRequestId().compareTo(previous.getRequestId()) != 0) {
				bean.setApprovalRole(inputBean.getApprovalRole());
				rejectPendingAssignRequest(inputBean.getRejectionComment(), bean, user);
			}
			previous = bean;
		}
	}
	
	private void rejectPendingAssignRequest(String reason, CatalogAddRequestPendingAssignBean inputBean, PersonnelBean personnelBean) throws BaseException {
		Connection connection = dbManager.getConnection();
		try {
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),"");
			engEvalProcess.setFactoryConnection(factory,connection);
			//insert approval role into catalog_add_approval
			if (engEvalProcess.insertApprovalData(inputBean.getRequestId(),inputBean.getApprovalRole(),"Rejected",reason,new BigDecimal(personnelBean.getPersonnelId()),"N")) {
				//reject request
				StringBuilder query = new StringBuilder();
				query.append("update catalog_add_request_new set request_status = 7")
						.append(",approval_group_seq = 0,request_rejected = 'Y',qc_status=null")
						.append(",eval_status = 'Rejected',last_updated = sysdate,approval_group_seq_start_time = null where request_id = ")
						.append(inputBean.getRequestId());
				factory.deleteInsertUpdate(query.toString(),connection);
				//send user email
				engEvalProcess.sendUserConfirmedEmail(inputBean.getRequestId());
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
		}
	}  //end of method
	
	public Collection<CatalogAddRequestOvBean> getRequests(CatalogAddQcInputBean input, PersonnelBean user) throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		CatalogAddRequestOvBeanFactory factory = new CatalogAddRequestOvBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
        SortCriteria sort = new SortCriteria();
		if (input.isSortByCompanyFirst()) {
			sort.addCriterion("companyName");
			sort.addCriterion("catalogDesc");
			sort.addCriterion("submitDate");
			sort.addCriterion("qcDate");
		}else if (input.isSortByDateFirst()) {
			sort.addCriterion("submitDate");
			sort.addCriterion("qcDate");
			sort.addCriterion("companyName");
			sort.addCriterion("catalogDesc");
		}

		return factory.selectAddQc(criteria, sort, input, user);
	}

	public ExcelHandler getExcelReport(CatalogAddQcInputBean input, Locale locale) throws NoDataException, BaseException, Exception {

		Collection<CatalogAddRequestOvBean> data = getRequests(input,null);

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.requestid", "label.company", "label.catalog", "label.facility", "label.requestor",
                                "label.assignedto", "label.requesttype", "label.status", "label.item", "label.materialdescription",
                                "label.manufacturer","label.comment","label.submitdate","label.msds"};
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, pw.TYPE_DATE, 0 };

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 0, 0, 0, 0, 0,
                         0, 0, 0, 0, 0,
                         0, 0, 0, 0 };
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0,
                              0, 0, 0, 0, 0,
                              0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		for (CatalogAddRequestOvBean member : data) {
            Iterator iter = member.getCatalogAddItemData().iterator();
            while (iter.hasNext()) {
                CatalogAddItemQcBean bean = (CatalogAddItemQcBean)iter.next();
                pw.addRow();
                pw.addCell(member.getRequestId());
                pw.addCell(member.getCompanyName());
                pw.addCell(member.getCatalogDesc());
                pw.addCell(member.getFacilityName());
                pw.addCell(member.getFullName());
                pw.addCell(member.getAssignedToFullName());
                pw.addCell(member.getRequestStatusDesc());
                String tmpVal = bean.getStatus();
                if ("Pending Spec".equals(input.getStatus())) {
                    tmpVal = input.getStatus();
                }
                pw.addCell(tmpVal);
                pw.addCell(bean.getItemId());
                pw.addCell(bean.getMaterialDesc());
                pw.addCell(bean.getManufacturer());
                pw.addCell(bean.getComments());
                pw.addCell(member.getSubmitDate());
                pw.addCell(member.getQcDate());
            }
        }
		return pw;
	}

	public Collection updateData (CatalogAddQcInputBean input, Collection<CatalogAddQcInputBean> rows, PersonnelBean personnelBean) {

		Collection<String> errorMessages = new Vector<String>();

		// Group the updates by user assigned To
		HashMap<String, Collection<String>> assignments = new HashMap<String, Collection<String>>();

		for (CatalogAddQcInputBean row : rows) {
			if (row.isUpdated()) {
				//build assigned to data
				if (!StringHandler.emptyIfNull(row.getAssignedTo()).equals(StringHandler.emptyIfNull(row.getOriginalAssignedTo()))) {
					Collection<String> userAssignments = assignments.get(row.getAssignedTo());
					if (userAssignments == null) {
						userAssignments = new Vector<String>(1);
						assignments.put(row.getAssignedTo(), userAssignments);
					}
					if (!userAssignments.contains(row.getRequestId()))
						userAssignments.add(row.getRequestId());
				}
			}
		}

		//update Assigned to
		if (!assignments.isEmpty())
			getDataMapper().updateRequestAssignedTo(assignments,errorMessages);

		return errorMessages;
	}  //end of method
	
	public Collection<String> assignRequestToSelf(CatalogAddQcInputBean input, PersonnelBean user) {
		Collection<String> errorMessages = new Vector<String>();
		try {
			String query = "update catalog_add_request_new set assigned_to = "
					+ user.getPersonnelIdBigDecimal()
					+ " where request_id = " + input.getRequestId();
			factory.deleteInsertUpdate(query);
		} catch (Exception e) {
			String errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
			log.error(errorMsg, e);
		}
		return errorMessages;
	}

	private void updateAssignedTo(HashMap<String, Collection<String>> assignments, Collection<String> errorMessages) { 
		try {
			String[] format = new String[0];
			factory.setBeanObject(new CatalogAddRequestNewBean());
            for (String user : assignments.keySet()) {
				SearchCriteria criteria = new SearchCriteria();
				String[] requestIds = assignments.get(user).toArray(format);
				criteria.addCriterionArray("requestId", SearchCriterion.IN, requestIds);
				factory.deleteInsertUpdate("update catalog_add_request_new set assigned_to = " + (StringHandler.isBlankString(user) ? "null" : user) + " " + factory.getWhereClause(criteria));
			}
		}
		catch (Exception e) {
			String errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
			log.error(errorMsg, e);
		}
	}  //end of method

}  //end of class
