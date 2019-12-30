package com.tcmis.internal.catalog.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogAddApprovalBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.CatalogAddItemQcBean;
import com.tcmis.internal.catalog.beans.CatalogAddQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestOvBean;

/******************************************************************************
 * CLASSNAME: CatalogAddRequestOvBeanFactory <br>
 * @version: 1.0, Aug 20, 2010 <br>
 *****************************************************************************/

public class CatalogAddRequestOvBeanFactory extends BaseBeanFactory {

	static Hashtable<String,String> tmpHash = new Hashtable();
	private String sdsPermission = "N";
	private String itemPermission = "N";
	private String specPermission = "N";

	Log log = LogFactory.getLog(this.getClass());

	//table name
	public final static String TABLE = "CATALOG_ADD_REQUEST_OV";
	
	//constructor
	public CatalogAddRequestOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column name
	@Override
	public String getColumnName(String attributeName) {
		String columnName = tmpHash.get(attributeName);
		if (columnName == null) {
			columnName = StringHandler.convertBeanPropertyToDatabaseColumn(attributeName);
			tmpHash.put(attributeName,columnName);
		}
		return columnName;
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CatalogAddRequestOvBean.class);
	}

	public Collection<CatalogAddRequestOvBean> select(SearchCriteria criteria, SortCriteria sort) throws BaseException {
		Connection connection = null;
		Collection<CatalogAddRequestOvBean> results = new Vector<CatalogAddRequestOvBean>();
		try {
			connection = getDbManager().getConnection();

			Map<String, Class<?>>  connTypeMap = connection.getTypeMap();
			connTypeMap.put(CatalogAddRequestOvBean.sqlType, Class.forName("com.tcmis.internal.catalog.beans.CatalogAddRequestOvBean"));
			connTypeMap.put(CatalogAddItemQcBean.sqlType, Class.forName("com.tcmis.internal.catalog.beans.CatalogAddItemQcBean"));

			// Uncomment the following string and delete the test query
			String query = new StringBuilder("select value(p) from ").append(TABLE).append(" p ").append(getWhereClause(criteria)).append(getOrderByClause(sort)).toString();
			
			log.debug(query);
			try {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					CatalogAddRequestOvBean row = (CatalogAddRequestOvBean) rs.getObject(1);
					results.add(row);
				}
				rs.close();
				st.close();
			}
			catch (SQLException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
		}
		catch (Exception e) {
			log.error("select error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");

			ex.setRootCause(e);
			throw ex;
		}
		finally {
			getDbManager().returnConnection(connection);
		}

		return results;
	}
	
	public Collection<CatalogAddRequestOvBean> selectAddQc(SearchCriteria criteria, SortCriteria sort, CatalogAddQcInputBean input, PersonnelBean user) throws BaseException {
		Connection connection = null;
		Collection<CatalogAddRequestOvBean> results = new Vector<CatalogAddRequestOvBean>();
		try {
			connection = getDbManager().getConnection();

			Map<String, Class<?>> connTypeMap = connection.getTypeMap();
			connTypeMap.put(CatalogAddRequestOvBean.sqlType, Class.forName("com.tcmis.internal.catalog.beans.CatalogAddRequestOvBean"));
			connTypeMap.put(CatalogAddItemQcBean.sqlType, Class.forName("com.tcmis.internal.catalog.beans.CatalogAddItemQcBean"));

			String whereClause = "";
			if (!input.isAllCompanies()) {
				if (!StringHandler.isBlankString(whereClause))
					whereClause += " and";
				whereClause += " p.company_id = " + SqlHandler.delimitString(input.getCompanyId());
			}
			if (input.hasCatalogId()) {
				if (!StringHandler.isBlankString(whereClause))
					whereClause += " and";
				whereClause += " p.catalog_id = " + SqlHandler.delimitString(input.getCatalogId());
			}
			if (input.hasAssignedTo()) {
				if (!StringHandler.isBlankString(whereClause))
					whereClause += " and";
				if (input.isAssignedToUnnassigned()) {
					whereClause += " p.assigned_to is null";
				} else {
					whereClause += " p.assigned_to = " + SqlHandler.delimitString(input.getAssignedTo());
				}
			}
			if (input.isHistoricalSearch()) {
				if (!StringHandler.isBlankString(whereClause))
					whereClause += " and";
				String tempTask = SqlHandler.delimitString(input.mapStatusToTask());
				if ("Pending SDS Sourcing".equals(input.getStatus()))
					tempTask = "'SDS Sourcing','SDS Authoring'";
				whereClause += " " + fxWqiCount(tempTask) + " = 0";
			}

			StringBuilder query1 = new StringBuilder("");
			//handling status
			query1.append(" p.company_id = car.company_id and p.catalog_company_id = car.catalog_company_id");
			query1.append(" and p.catalog_id = car.catalog_id and p.eng_eval_facility_id = car.facility_id");
			query1.append(" and p.request_status = cars.request_status and p.approval_group_seq = car.approval_group_seq");
			query1.append(" and car.company_id = cars.company_id and car.approval_group = cars.approval_group");
			query1.append(" and car.active = 'Y'");

			if (!input.isAllCompanies())
				query1.append(" and car.company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));

			if ("Pending SDS Sourcing".equals(input.getStatus())) {
				query1.append(" and car.approval_role = 'Material QC'");
				query1.append(" and qc_status in ('Pending Material QC','Pending Material','Pending MSDS','Pending [M]SDS QC','Pending SDS Sourcing')");
				getOnlyOpenQueueRequests(query1,"'SDS Sourcing','SDS Authoring'");
				if (user != null)
					sdsPermission = user.getPermissionBean().hasCatalogPermission("catalogAddMSDS",null,null,null)?"Y":"N";
			} else if ("Pending SDS Indexing".equals(input.getStatus())) {
				query1.append(" and car.approval_role = 'MSDS Indexing'");
				//the reason this is can not use qc_status is because it's running parallel with Item QC
				query1.append(" and msds_indexing_status = 'Pending'");
				getOnlyOpenQueueRequests(query1,"'SDS Indexing'");
				//user is null when called for Create Excel, otherwise called from Search and user CANNOT be null
				if (user != null)
					sdsPermission = user.getPermissionBean().hasCatalogPermission("catalogAddMSDS",null,null,null)?"Y":"N";
			} else if ("Pending SDS Custom Indexing".equals(input.getStatus())) {
				query1.append(" and car.approval_role = 'Custom Indexing'");
				query1.append(" and qc_status in ('Pending [M]SDS Custom Indexing','Pending SDS Custom Indexing')");
				if (user != null)
					sdsPermission = user.getPermissionBean().hasCatalogPermission("catalogAddMSDS",null,null,null)?"Y":"N";
			} else if ("Pending SDS QC".equals(input.getStatus())) {
				query1.append(" and car.approval_role = 'SDS QC'");
				query1.append(" and qc_status in ('Pending [M]SDS QC', '").append(input.getStatus()).append("')");
				if (user != null)
					sdsPermission = user.getPermissionBean().hasCatalogPermission("catalogAddMSDS",null,null,null)?"Y":"N";
			} else if ("Pending Item Creation".equals(input.getStatus())) {
				query1.append(" and car.approval_role = 'Item QC'");
				//the reason is because it's running parallel with MSDS Indexing
				query1.append(" and qc_status in ('Pending Item QC','Pending Item','Pending QC','Pending [M]SDS Indexing','Pending SDS Indexing')");
				getOnlyOpenQueueRequests(query1,"'Item Creation'");
				if (user != null)
					itemPermission = user.getPermissionBean().hasCatalogPermission("catalogAddQC",null,null,null)?"Y":"N";
			} else if ("Pending Spec".equals(input.getStatus())) {
				query1.append(" and car.approval_role = 'TCM Spec'");
				query1.append(" and qc_status in ('Pending Spec', 'Pending TCM Spec')");
				if (user != null)
					specPermission = user.getPermissionBean().hasCatalogPermission("catalogAddSpec",null,null,null)?"Y":"N";
			}else {
				query1.append(" and car.approval_role in ('Material QC','MSDS Indexing','Custom Indexing','SDS QC','Item QC','TCM Spec')");
				query1.append(" and (qc_status in ('Pending Material QC','Pending Material','Pending MSDS','Pending [M]SDS QC','Pending SDS Sourcing'");
				query1.append(",'Pending [M]SDS Custom Indexing','Pending SDS Custom Indexing'");
				query1.append(",'Pending SDS QC'");
				query1.append(",'Pending Item QC','Pending Item','Pending QC','Pending [M]SDS Indexing','Pending SDS Indexing'");
				query1.append(",'Pending Spec', 'Pending TCM Spec'");
				query1.append(")");
				query1.append(" or msds_indexing_status = 'Pending')");
				if (user != null) {
					sdsPermission = user.getPermissionBean().hasCatalogPermission("catalogAddMSDS", null, null, null) ? "Y" : "N";
					itemPermission = user.getPermissionBean().hasCatalogPermission("catalogAddQC", null, null, null) ? "Y" : "N";
					specPermission = user.getPermissionBean().hasCatalogPermission("catalogAddSpec", null, null, null) ? "Y" : "N";
				}
			}

			if (input.hasSearch()) {
				if ("requestId".equalsIgnoreCase(input.getSearchField())) {
					if (!StringHandler.isBlankString(whereClause))
						whereClause += " and";
					if ("is".equalsIgnoreCase(input.getSearchMode())) {
						whereClause += " p.request_id = " + input.getSearchArgument();
					} else {
						whereClause += " p.request_id like '%" + input.getSearchArgument() + "%'";
					}
				} else {
					if (!StringHandler.isBlankString(query1.toString()))
						query1.append(" and");
					if ("is".equalsIgnoreCase(input.getSearchMode())) {
						query1.append(" request_id in (select distinct request_id from CATALOG_ADD_ITEM_QC where lower(" + input.getSearchField() + ") = lower(").append(SqlHandler.delimitString(input.getSearchArgument())).append("))");
					} else {
						query1.append(" request_id in (select distinct request_id from CATALOG_ADD_ITEM_QC where lower(" + input.getSearchField() + ") like lower('%").append(SqlHandler.validQuery(input.getSearchArgument())).append("%'))");
					}
				}
			}

			if (input.isExcludeMerck())
				query1.append(" and car.company_id <> 'MERCK' ");

			if (input.hasSupplier()) {
				query1.append(" and exists (select catalog_add_request_id from catalog_queue cq where p.request_id = cq.catalog_add_request_id");
				query1.append(" and cq.supplier = '").append(input.getSupplier()).append("')");
			}

            // Uncomment the following string and delete the test query
			StringBuilder query = new StringBuilder("select value(p)")
					.append(", pkg_catalog_queue.fx_vendor_assignment_status(p.request_id, ").append(SqlHandler.delimitString(input.mapStatusToTask())).append(")");
			if (CatalogAddQcInputBean.ALL_STATUS.equals(input.getStatus())) {
				query.append(", ").append(fxTaskClosed(CatalogAddQcInputBean.INDEXING_TASK, "sds_indexing_closed"))
					.append(", ").append(fxTaskClosed(CatalogAddQcInputBean.ITEM_CREATION_TASK, "item_creation_closed"));
			}
			query.append(" from ").append(TABLE).append(" p, vv_chemical_approval_role car, vv_catalog_add_request_status cars ");
            //String whereClause = getWhereClause(criteria);
            if (StringHandler.isBlankString(whereClause)) {
                if (!StringHandler.isBlankString(query1.toString()))
                    query.append("where ");
            }else {
                query.append("where ");
                query.append(whereClause).append(" and");
            }
            query.append(" ").append(query1).append(" ").append(getOrderByClause(sort));
			log.debug(query);
			try {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(query.toString());
				//this is because the same request can show up in SDS Indexing or Item QC (these 2 approval roles are parallel)
				Vector existingRequestId = new Vector();
				while (rs.next()) {
					CatalogAddRequestOvBean row = (CatalogAddRequestOvBean) rs.getObject(1);
					String vendorAssignmentStatus = rs.getString(2);
					String sdsIndexingClosed = "";
					String itemCreationClosed = "";
					if (CatalogAddQcInputBean.ALL_STATUS.equals(input.getStatus())) {
						sdsIndexingClosed = rs.getString(3);
						itemCreationClosed = rs.getString(4);
					}
					if (!existingRequestId.contains(row.getRequestId())) {
						row.setVendorAssignmentStatus(vendorAssignmentStatus);
						row.setSdsIndexingClosed(sdsIndexingClosed);
						row.setItemCreationClosed(itemCreationClosed);
						calculateRequestDisplayStatus(row,input.getStatus(),connection);
						results.add(row);
						existingRequestId.addElement(row.getRequestId());
					}
				}
				rs.close();
				st.close();
			}
			catch (SQLException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
		}
		catch (Exception e) {
			log.error("select error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");

			ex.setRootCause(e);
			throw ex;
		}
		finally {
			getDbManager().returnConnection(connection);
		}

		return results;
	}
	
	private String fxWqiCount(String task) {
		return "(SELECT count(*) from catalog_queue where catalog_add_request_id = p.request_id and task in ("+task+"))";
	}
	
	private String fxTaskClosed(String task, String alias) {
		return "(SELECT DECODE (cq.status, 'Closed', 'Y', 'N')"
				+ " FROM catalog_queue cq"
				+ " WHERE cq.catalog_add_request_id = p.request_id AND cq.task = "
				+ SqlHandler.delimitString(task) + " and rownum = 1) " + alias;
	}

	private void calculateRequestDisplayStatus(CatalogAddRequestOvBean bean, String selectedStatus, Connection connection) {
		try {
			bean.setRequestDisplayStatus(selectedStatus);
			if ("All Statuses".equals(selectedStatus)) {
				StringBuilder query = new StringBuilder("select role_function from vv_chemical_approval_role car, vv_catalog_add_request_status cars");
				query.append(" where car.company_id = cars.company_id and car.approval_group = cars.approval_group and car.approval_group_seq = ").append(bean.getApprovalGroupSeq());
				query.append(" and car.company_id = '").append(bean.getCompanyId()).append("' and car.facility_id = '").append(bean.getEngEvalFacilityId()).append("'");
				query.append(" and car.catalog_company_id = '").append(bean.getCatalogCompanyId()).append("' and car.catalog_id = '").append(bean.getCatalogId()).append("'");
				query.append(" and cars.request_status = ").append(bean.getRequestStatus());
				query.append(" order by role_function");
				GenericSqlFactory factory = new GenericSqlFactory(getDbManager());
				factory.setBeanObject(new CatalogAddApprovalBean());
				Iterator dataIter = factory.selectQuery(query.toString(),connection).iterator();
				String tmpRequestStatus = "";
				while (dataIter.hasNext()) {
					CatalogAddApprovalBean myBean = (CatalogAddApprovalBean) dataIter.next();
					if ("Verify SDS Sourcing".equals(myBean.getRoleFunction()) || "Verify SDS Sourcing Wesco".equals(myBean.getRoleFunction())) {
						if (!StringHandler.isBlankString(tmpRequestStatus))
							tmpRequestStatus += " + ";
						tmpRequestStatus += "Pending SDS Sourcing";
						bean.setRowPermission(sdsPermission);
					}else if (("Verify SDS Indexing".equals(myBean.getRoleFunction()) || "Verify SDS Indexing Wesco".equals(myBean.getRoleFunction())) &&
							   "Pending".equals(bean.getMsdsIndexingStatus())) {
						if (!StringHandler.isBlankString(tmpRequestStatus))
							tmpRequestStatus += " + ";
						tmpRequestStatus += "Pending SDS Indexing";
						bean.setRowPermission(sdsPermission);
					}else if (("Verify Item".equals(myBean.getRoleFunction()) || "Verify Item Wesco".equals(myBean.getRoleFunction())) &&
							  ("Pending QC".equals(bean.getQcStatus()) || "Pending Item".equals(bean.getQcStatus()) || "Pending Item QC".equals(bean.getQcStatus()))) {
						if (!StringHandler.isBlankString(tmpRequestStatus))
							tmpRequestStatus += " + ";
						tmpRequestStatus += "Pending Item Creation";
						bean.setRowPermission(itemPermission);
					}else if ("Verify Custom Indexing".equals(myBean.getRoleFunction())) {
						if (!StringHandler.isBlankString(tmpRequestStatus))
							tmpRequestStatus += " + ";
						tmpRequestStatus += "Pending SDS Custom Indexing";
						bean.setRowPermission(sdsPermission);
					}else if ("Verify SDS QC".equals(myBean.getRoleFunction())) {
						if (!StringHandler.isBlankString(tmpRequestStatus))
							tmpRequestStatus += " + ";
						tmpRequestStatus += "Pending SDS QC";
						bean.setRowPermission(sdsPermission);
					}else if ("Verify Spec".equals(myBean.getRoleFunction())) {
						if (!StringHandler.isBlankString(tmpRequestStatus))
							tmpRequestStatus += " + ";
						tmpRequestStatus += "Pending Spec";
						bean.setRowPermission(specPermission);
					}
				}
				if (!StringHandler.isBlankString(tmpRequestStatus))
					bean.setRequestDisplayStatus(tmpRequestStatus);
			}else {
				if ("Y".equals(sdsPermission) || "Y".equals(itemPermission) || "Y".equals(specPermission))
					bean.setRowPermission("Y");
				else
					bean.setRowPermission("N");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getOnlyOpenQueueRequests(StringBuilder query, String tasks) {
		query.append(" and request_id not in (with cq as (select q.*,count(distinct q.status) over (partition by q.catalog_add_request_id,task) carn_ct from catalog_queue q)");
		query.append(" select cq.catalog_add_request_id from cq, catalog_add_item_qc caiqc where  cq.catalog_add_request_id = caiqc.request_id");
		query.append(" and cq.line_item = caiqc.line_item and nvl(cq.catalog_add_item_id,caiqc.catalog_add_item_id) = caiqc.catalog_add_item_id");
		query.append(" and cq.carn_ct = 1 and cq.task in (").append(tasks).append(")");
		query.append(" and cq.status  = 'Pending Approval'");
		query.append(")");
	} //end of method

}  //end of class